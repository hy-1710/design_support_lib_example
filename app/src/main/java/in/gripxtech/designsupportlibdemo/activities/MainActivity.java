package in.gripxtech.designsupportlibdemo.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import in.gripxtech.designsupportlibdemo.R;
import in.gripxtech.designsupportlibdemo.databinding.ActivityMainBinding;
import in.gripxtech.designsupportlibdemo.fragments.GamesFragment;
import in.gripxtech.designsupportlibdemo.fragments.MoviesFragment;
import in.gripxtech.designsupportlibdemo.fragments.MusicFragment;
import in.gripxtech.designsupportlibdemo.prefs.NavPrefs;

public class MainActivity extends AppCompatActivity {

    public static final String TAG;

    static {
        TAG = "MainActivity";
    }

    private ActivityMainBinding binding;
    private NavPrefs navPrefs;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        navPrefs = new NavPrefs(this);
        setupView();
        setUpNavView();
    }

    private void setupView() {
        binding.navMain.getMenu().findItem(R.id.action_games).setChecked(true);
        loadFragment(R.id.action_games);
    }

    private void setUpNavView() {
        final View view = binding.navMain.getHeaderView(0);
        final CircleImageView profile = (CircleImageView) view.findViewById(R.id.ivNavProfile);
        final TextView displayName = (TextView) view.findViewById(R.id.tvNavDisplayName);
        final TextView email = (TextView) view.findViewById(R.id.tvNavEmail);


        binding.navMain.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                binding.drawerLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                    }
                });
                int currentDrawerItemID = navPrefs.getCurrentDrawerItemID();
                switch (menuItem.getItemId()) {
                    case R.id.action_games:
                        if (currentDrawerItemID != R.id.action_games) {
                            loadFragment(R.id.action_games);
                        }
                        break;
                    case R.id.action_music:
                        if (currentDrawerItemID != R.id.action_music) {
                            loadFragment(R.id.action_music);
                        }
                        break;
                    case R.id.action_movies:
                        if (currentDrawerItemID != R.id.action_movies) {
                            loadFragment(R.id.action_movies);
                        }
                        break;
                    case R.id.action_settings:
                        startActivity(new Intent(
                                MainActivity.this,
                                SettingsActivity.class
                        ));
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void loadFragment(int currentDrawerItemID) {
        clearBackStack();
        navPrefs.setCurrentDrawerItemID(currentDrawerItemID);
        if (currentDrawerItemID == R.id.action_games) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flMain, GamesFragment.newInstance(),
                            GamesFragment.TAG)
                    .commit();
        } else if (currentDrawerItemID == R.id.action_music) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flMain, MusicFragment.newInstance(),
                            MusicFragment.TAG)
                    .commit();
        } else if (currentDrawerItemID == R.id.action_movies) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flMain, MoviesFragment.newInstance(),
                            MoviesFragment.TAG)
                    .commit();
        }
    }

    private void clearBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStackImmediate();
        }
    }

    public DrawerLayout getDrawerLayout() {
        return binding.drawerLayout;
    }
}
