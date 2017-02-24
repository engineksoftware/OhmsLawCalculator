package enginek.ohmslawcalculator;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joseph Kessler on 2/22/2017.
 */

public class SavedFragment extends ListFragment {

    private View view;
    private Context context;
    private DatabaseHandler db;
    private List<Calculation> calculations;
    private List list;
    private ListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.saved_fragment, container, false);
        context = view.getContext();

        db = new DatabaseHandler(view.getContext());
        calculations = db.getCalculations();
        list = new ArrayList();


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

            adapter = new ListAdapter(context, list);
            setListAdapter(adapter);
        }
    }

    @Override
    public void onResume(){
        super.onResume();




    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser){
            //Hides the keyboard when pressed
            InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
}
