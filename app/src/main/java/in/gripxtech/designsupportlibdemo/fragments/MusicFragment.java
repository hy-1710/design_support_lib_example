package in.gripxtech.designsupportlibdemo.fragments;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.gripxtech.designsupportlibdemo.R;
import in.gripxtech.designsupportlibdemo.activities.MainActivity;
import in.gripxtech.designsupportlibdemo.adapters.ViewPagerAdapter;
import in.gripxtech.designsupportlibdemo.databinding.FragmentMusicBinding;
import in.gripxtech.designsupportlibdemo.models.ViewPagerItem;

public class MusicFragment extends Fragment {

    public static final String TAG;

    static {
        TAG = "MusicFragment";
    }

    private MainActivity activity;
    private ActionBarDrawerToggle drawerToggle;
    private ViewPagerAdapter adapter;
    private FragmentMusicBinding binding;

    public MusicFragment() {
        // Required empty public constructor
    }

    public static MusicFragment newInstance() {
        MusicFragment fragment = new MusicFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // if (getArguments() != null) {}
        activity = (MainActivity) getActivity();
        adapter = new ViewPagerAdapter(getChildFragmentManager(), new ArrayList<ViewPagerItem>());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_music, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.setSupportActionBar(binding.toolbar);

        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        activity.setTitle(R.string.action_music);

        drawerToggle = new ActionBarDrawerToggle(activity, activity.getDrawerLayout(),
                binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        activity.getDrawerLayout().addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        binding.tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        adapter.getItems().addAll(new ArrayList<ViewPagerItem>() {{
            add(new ViewPagerItem(MusicChildFragment.newInstance(), "TAB 1"));
            add(new ViewPagerItem(MusicChildFragment.newInstance(), "TAB 2"));
            add(new ViewPagerItem(MusicChildFragment.newInstance(), "TAB 3"));
        }});

        binding.vp.setAdapter(adapter);
        binding.tl.setupWithViewPager(binding.vp);

        // binding.tl.setTabGravity(TabLayout.GRAVITY_FILL);
        // binding.tl.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
