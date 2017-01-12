package in.gripxtech.designsupportlibdemo.models;

import android.support.v7.widget.RecyclerView;

import in.gripxtech.designsupportlibdemo.databinding.ItemViewListBinding;

public class ListViewHolder extends RecyclerView.ViewHolder {

    private ItemViewListBinding binding;

    public ListViewHolder(ItemViewListBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ItemViewListBinding getBinding() {
        return binding;
    }

}
