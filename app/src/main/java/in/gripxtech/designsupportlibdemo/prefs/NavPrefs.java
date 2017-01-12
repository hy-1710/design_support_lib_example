package in.gripxtech.designsupportlibdemo.prefs;

import android.content.Context;
import android.support.annotation.NonNull;

import in.gripxtech.designsupportlibdemo.R;

public class NavPrefs extends BasePrefs {

    public static final String TAG;
    private static final String CurrentDrawerItemID;

    static {
        TAG = "NavPrefs";
        CurrentDrawerItemID = "CurrentDrawerItemID";
    }

    /**
     * @param context context
     */
    public NavPrefs(@NonNull Context context) {
        super(TAG, context);
    }

    public int getCurrentDrawerItemID() {
        return getInt(CurrentDrawerItemID, R.id.action_games);
    }

    public void setCurrentDrawerItemID(int currentDrawerItemID) {
        putInt(CurrentDrawerItemID, currentDrawerItemID);
    }
}
