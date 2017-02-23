package enginek.ohmslawcalculator;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Joseph Kessler on 2/22/2017.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public TabsPagerAdapter(Context context, FragmentManager fm){
        super(fm);

        this.context = context;
    }

    @Override
    public int getCount(){
        return 2;
    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                return new CalculatorFragment();
            case 1:
                return new SavedFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return  context.getString(R.string.calculator_tab_name);
            case 1:
                return  context.getString(R.string.saved_tab_name);
            default:
                return null;
        }
    }
}
