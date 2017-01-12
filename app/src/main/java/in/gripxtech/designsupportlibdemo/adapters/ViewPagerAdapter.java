package in.gripxtech.designsupportlibdemo.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import in.gripxtech.designsupportlibdemo.models.ViewPagerItem;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<ViewPagerItem> items;

    public ViewPagerAdapter(@NonNull FragmentManager fm, @NonNull ArrayList<ViewPagerItem> items) {
        super(fm);
        this.items = items;
    }

    @Override
    public Fragment getItem(int position) {
        return items.get(position).getFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return items.get(position).getPageTitle();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public ArrayList<ViewPagerItem> getItems() {
        return items;
    }
}
