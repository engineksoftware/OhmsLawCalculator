package enginek.ohmslawcalculator;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Joseph Kessler on 2/22/2017.
 */

public class SavedFragment extends ListFragment {

    private View view;
    private Context context;
    private DatabaseHandler db;
    private List<Calculation> calculations;
    private List list;
    private static ListAdapter adapter;
    private Button delete, select;
    private boolean choosing;
    private List<Integer> chosenList;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.saved_fragment, container, false);
        context = view.getContext();
        activity = getActivity();

        db = new DatabaseHandler(view.getContext());
        calculations = db.getCalculations();
        list = new ArrayList();
        delete = (Button) view.findViewById(R.id.delete);
        select = (Button) view.findViewById(R.id.select);
        choosing = false;
        choosing = false;
        chosenList = new ArrayList<>();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.getCalculationsCount() > 0){
                    if(!choosing){
                        createDialog();
                    }else{
                        for(int x = 0; x < chosenList.size(); x ++){
                            db.deleteCalculation(calculations.get(chosenList.get(x)).getId());
                        }

                        choosing = false;
                        delete.setText("DELETE ALL");
                        select.setText("SELECT");

                        updateAdapter();

                        chosenList.clear();

                        for(int x = 0; x < calculations.size(); x++){
                            getListView().getChildAt(x).findViewById(R.id.layout).setBackgroundResource(R.drawable.edittext_white_background);
                        }
                    }
                }else{
                    Toast.makeText(context, "There's nothing to delete.", Toast.LENGTH_SHORT).show();

                }

            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(db.getCalculationsCount() > 0){
                    if(!choosing){
                        choosing = true;
                        delete.setText("DELETE SELECTED");
                        select.setText("CANCEL");
                        Toast.makeText(context, "Choose which ones you want to delete.", Toast.LENGTH_SHORT).show();
                    }else{
                        choosing = false;
                        delete.setText("DELETE ALL");
                        select.setText("SELECT");

                        //Resets any highlighted items back to un highlighted.
                        if(chosenList.size() > 0){
                            for(int x = 0; x < chosenList.size(); x++){
                                getListView().getChildAt(chosenList.get(x)).findViewById(R.id.layout).setBackgroundResource(R.drawable.edittext_white_background);
                            }

                            chosenList.clear();
                        }
                    }
                }else{
                    Toast.makeText(context, "There's nothing to select.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        if(calculations.size() > 0){

            for(int x = 0; x < calculations.size(); x++){
                list.add(new ListItem(String.format("%.2f%n",calculations.get(x).getVoltage()),
                        String.format("%.2f%n",calculations.get(x).getCurrent()),
                        String.format("%.2f%n",calculations.get(x).getResistance()),
                        String.format("%.2f%n",calculations.get(x).getPower())));
            }

        }

        adapter = new ListAdapter(context, list);
        setListAdapter(adapter);
    }

    @Override
    public void onResume(){
        super.onResume();




    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser){
            hideKeyboard();

            if(adapter != null){
                updateAdapter();
            }

        }
    }

    public void hideKeyboard(){
        InputMethodManager inputManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) context).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if(choosing){
            if(!chosenList.contains(position)){
                chosenList.add(position);

                v.findViewById(R.id.layout).setBackgroundResource(R.drawable.item_background_highlighted);
            }else{
                chosenList.remove((Object) position);

                v.findViewById(R.id.layout).setBackgroundResource(R.drawable.edittext_white_background);
            }

        }

    }

    private void createDialog(){
        //Creates dialog using custom layout
        final Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.delete_dialog);
        dialog.setCancelable(true);

        Button delete = (Button) dialog.findViewById(R.id.delete);
        Button close = (Button) dialog.findViewById(R.id.close);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAll();
                updateAdapter();
                dialog.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void updateAdapter(){
        adapter.updateList();
        calculations.clear();
        calculations = db.getCalculations();
    }
}
