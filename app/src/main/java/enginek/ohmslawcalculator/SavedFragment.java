package enginek.ohmslawcalculator;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Joseph Kessler on 2/22/2017.
 */

public class SavedFragment extends Fragment {

    private View view;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.saved_fragment, container, false);
        context = view.getContext();

        return view;
    }
}
