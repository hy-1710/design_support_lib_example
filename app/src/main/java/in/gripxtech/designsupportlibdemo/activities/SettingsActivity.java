package in.gripxtech.designsupportlibdemo.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import in.gripxtech.designsupportlibdemo.R;
import in.gripxtech.designsupportlibdemo.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    public static final String TAG;

    static {
        TAG = "SettingsActivity";
    }

    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public ActivitySettingsBinding getBinding() {
        return binding;
    }
}
