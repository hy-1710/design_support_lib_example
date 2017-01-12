package in.gripxtech.designsupportlibdemo.models;

import android.support.v4.app.Fragment;

public class ViewPagerItem {

    public static final String TAG;

    static {
        TAG = "ViewPagerItem";
    }

    private Fragment fragment;
    private String pageTitle;

    public ViewPagerItem(Fragment fragment, String pageTitle) {
        this.fragment = fragment;
        this.pageTitle = pageTitle;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public String getPageTitle() {
        return pageTitle;
    }
}
