package in.gripxtech.designsupportlibdemo.models;

public class ListItem {

    public static final String TAG;

    static {
        TAG = "ListItem";
    }

    private String title;
    private String subhead;

    public ListItem(String title, String subhead) {
        this.title = title;
        this.subhead = subhead;
    }

    public String getTitle() {
        return title;
    }

    public String getSubhead() {
        return subhead;
    }

    @Override
    public String toString() {
        return TAG + "(title=" + title + ", subhead=" + subhead + ")";
    }
}
