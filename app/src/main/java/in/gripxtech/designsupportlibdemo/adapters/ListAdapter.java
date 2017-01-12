package in.gripxtech.designsupportlibdemo.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.gripxtech.designsupportlibdemo.R;
import in.gripxtech.designsupportlibdemo.activities.MainActivity;
import in.gripxtech.designsupportlibdemo.databinding.ItemViewListBinding;
import in.gripxtech.designsupportlibdemo.models.ListItem;
import in.gripxtech.designsupportlibdemo.models.ListViewHolder;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    public static final String TAG;

    static {
        TAG = "ListAdapter";
    }

    private MainActivity activity;
    private ArrayList<ListItem> items;

    public ListAdapter(MainActivity activity, ArrayList<ListItem> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemViewListBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_view_list,
                parent,
                false
        );
        return new ListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        ListItem item = items.get(position);
        holder.getBinding().tvTitle.setText(item.getTitle());
        holder.getBinding().tvSubhead.setText(item.getSubhead());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<ListItem> getItems() {
        return items;
    }
}
