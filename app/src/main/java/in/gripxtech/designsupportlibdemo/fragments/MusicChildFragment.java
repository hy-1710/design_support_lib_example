package in.gripxtech.designsupportlibdemo.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.gripxtech.designsupportlibdemo.R;
import in.gripxtech.designsupportlibdemo.activities.MainActivity;
import in.gripxtech.designsupportlibdemo.adapters.ListAdapter;
import in.gripxtech.designsupportlibdemo.databinding.FragmentMusicChildBinding;
import in.gripxtech.designsupportlibdemo.models.ListItem;

public class MusicChildFragment extends Fragment {

    public static final String TAG;

    static {
        TAG = "MusicChildFragment";
    }

    private MainActivity activity;
    private ListAdapter adapter;
    private FragmentMusicChildBinding binding;
    private Loader<ArrayList<ListItem>> loader;

    public MusicChildFragment() {
        // Required empty public constructor
    }

    public static MusicChildFragment newInstance() {
        MusicChildFragment fragment = new MusicChildFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // if (getArguments() != null) {}
        activity = (MainActivity) getActivity();
        adapter = new ListAdapter(activity, new ArrayList<ListItem>());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_music_child, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rv.setLayoutManager(linearLayoutManager);
        binding.rv.setAdapter(adapter);

        loader = getLoaderManager().initLoader(
                0, savedInstanceState, new LoaderManager.LoaderCallbacks<ArrayList<ListItem>>() {
                    @Override
                    public Loader<ArrayList<ListItem>> onCreateLoader(int id, Bundle args) {
                        return new AsyncTaskLoader<ArrayList<ListItem>>(activity) {
                            @Override
                            public ArrayList<ListItem> loadInBackground() {
                                ArrayList<ListItem> items = new ArrayList<>();
                                for (int i = 0; i < 15; i++) {
                                    items.add(new ListItem("Title", "Subhead"));
                                }
                                return items;
                            }
                        };
                    }

                    @Override
                    public void onLoadFinished(Loader<ArrayList<ListItem>> loader, ArrayList<ListItem> data) {
                        adapter.getItems().clear();
                        adapter.getItems().addAll(data);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onLoaderReset(Loader<ArrayList<ListItem>> loader) {

                    }
                }
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        loader.forceLoad();
    }
}
