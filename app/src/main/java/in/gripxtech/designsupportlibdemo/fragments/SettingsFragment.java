package in.gripxtech.designsupportlibdemo.fragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.MailTo;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;

import org.jsoup.Jsoup;

import in.gripxtech.designsupportlibdemo.R;
import in.gripxtech.designsupportlibdemo.activities.SettingsActivity;

public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String TAG;

    static {
        TAG = "SettingsFragment";
    }

    private SettingsActivity activity;
    private Preference build;
    private Preference organization;
    private Preference contact;
    private Preference share;
    private Preference rate;
    private Preference moreApps;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        activity = (SettingsActivity) getActivity();
        addPreferencesFromResource(R.xml.settings);

        build = findPreference(getString(R.string.build_key));
        organization = findPreference(getString(R.string.organization_key));
        contact = findPreference(getString(R.string.contact_key));
        share = findPreference(getString(R.string.share_key));
        rate = findPreference(getString(R.string.rate_key));
        moreApps = findPreference(getString(R.string.more_apps_key));

        build.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                checkForVersion();
                return true;
            }
        });

        organization.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showAddressOnMap();
                return true;
            }
        });

        contact.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                sendMail();
                return true;
            }
        });

        share.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                shareApp();
                return true;
            }
        });

        rate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                rateApp();
                return true;
            }
        });

        moreApps.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                moreApps("GRIPXTECH+EDUCATION");
                return true;
            }
        });
    }

    /**
     * @return boolean indicating whether network is connected or not <br /><br />
     * <p/>
     * <strong>Note:</strong><br/>
     * <p>This method requires android.permission.ACCESS_NETWORK_STATE permission </p>
     */
    public boolean isDeviceOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void rateApp() {
        try {
            activity.startActivity(
                    new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="
                            + activity.getPackageName())));
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "rateApp: ", e);
            try {
                activity.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id="
                                + activity.getPackageName())));
            } catch (ActivityNotFoundException e2) {
                Log.e(TAG, "rateApp: ", e2);
            }
        }
    }

    public void shareApp() {
        Intent share_que = new Intent(Intent.ACTION_SEND);
        share_que.setType("text/plain");
        share_que.putExtra(Intent.EXTRA_TEXT,
                "Hey friends,\nI just discovered an amazing app called "
                        + activity.getResources().getString(R.string.app_name)
                        + ". Get it from https://play.google.com/store/apps/details?id="
                        + activity.getPackageName());
        activity.startActivityForResult(Intent.createChooser(share_que,
                "Share " + activity.getResources().getString(R.string.app_name) + " Using"), 123);
    }

    public void checkForVersion() {
        // Requires JSOUP dependency
        // compile 'org.jsoup:jsoup:1.10.2'
        AsyncTaskCompat.executeParallel(new AsyncTask<Void, Void, String>() {

            String currentVersion;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                try {
                    currentVersion = activity.getPackageManager().getPackageInfo(
                            activity.getPackageName(), 0
                    ).versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e(TAG, "checkForVersion: ", e);
                }
            }

            @Override
            protected String doInBackground(Void... params) {
                String newVersion = currentVersion;
                try {
                    newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id="
                            + activity.getPackageName() + "&hl=it")
                            .timeout(30000)
                            .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                            .referrer("http://www.google.com")
                            .get()
                            .select("div[itemprop=softwareVersion]")
                            .first()
                            .ownText();
                    return newVersion;
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground: ", e);
                    return newVersion;
                }
            }

            @Override
            protected void onPostExecute(String onlineVersion) {
                super.onPostExecute(onlineVersion);
                if (onlineVersion != null && !onlineVersion.isEmpty()) {
                    if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion)) {
                        showMessage(
                                "Information",
                                "New version of "
                                        + activity.getResources().getString(R.string.app_name)
                                        + " is available",
                                "OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        rateApp();
                                    }
                                }
                        );
                    } else {
                        showMessage(
                                "Information",
                                "you have latest version installed on your device",
                                "OK", null
                        );
                    }
                }
            }
        });
    }

    public void showAddressOnMap() {
        Uri gmmIntentUri = Uri.parse("geo:21.7506202,72.1605996?q=Diamond Chowk, Mahila College Area, Krishna Nagar, Bhavnagar, Gujarat, India");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(mapIntent);
        } else {
            activity.startActivity(new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://gripxtech.in")
            ));
        }
    }

    public void sendMail() {
        String url = "mailto:" + activity.getString(R.string.contact_summary)
                + "?subject=Contact by "
                + activity.getString(R.string.app_name)
                + " user " + "";
        try {
            MailTo mt = MailTo.parse(url);
            Intent i = newEmailIntent(mt.getTo(), mt.getSubject(),
                    mt.getBody(), new String[]{mt.getCc()});
            activity.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(
                    activity.getBinding().getRoot(),
                    "There's no application found to open email links",
                    Snackbar.LENGTH_LONG
            ).show();
        }

    }

    private Intent newEmailIntent(String address, String subject, String body, String[] cc) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
        intent.putExtra(Intent.EXTRA_TEXT, body);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_CC, cc);
        return Intent.createChooser(intent, "Send Email Using");
    }

    public void moreApps(@NonNull String developerName) {
        activity.startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/developer?id=" + developerName)));
    }

    public AlertDialog showMessage(@Nullable String title,
                                   @NonNull String message,
                                   @NonNull String positiveButton,
                                   @Nullable DialogInterface.OnClickListener positiveButtonListener) {
        return new AlertDialog.Builder(activity, R.style.AppAlertDialogTheme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButton, positiveButtonListener)
                .setCancelable(false)
                .show();
    }
}
