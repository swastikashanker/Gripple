package codingblocks.com.gripple;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import 	androidx.core.app.NavUtils;
import android.text.TextUtils;
import android.view.MenuItem;

import java.util.List;

import androidx.appcompat.app.ActionBar;

public class SettingsActivity extends AppCompatPreferenceActivity{


//     public static final String PREF_IPCAM_URL = "com.github.niqdev.ipcam.settings.SettingsActivity.IPCAM_URL";
//     public static final String PREF_FLIP_HORIZONTAL = "com.github.niqdev.ipcam.settings.SettingsActivity.FLIP_HORIZONTAL";
//     public static final String PREF_FLIP_VERTICAL= "com.github.niqdev.ipcam.settings.SettingsActivity.FLIP_VERTICAL";
//     public static final String PREF_ROTATE_DEGREES = "com.github.niqdev.ipcam.settings.SettingsActivity.ROTATE_DEGREES";
//     public static final String PREF_AUTH_USERNAME = "com.github.niqdev.ipcam.settings.SettingsActivity.PREF_AUTH_USERNAME";
//     public static final String PREF_AUTH_PASSWORD = "com.github.niqdev.ipcam.settings.SettingsActivity.PREF_AUTH_PASSWORD";
   public static final String PREF_IPCAM_URL = "codingblocks.com.gripple.SettingsActivity.IPCAM_URL";
   public static final String PREF_FLIP_HORIZONTAL = "codingblocks.com.gripple.SettingsActivity.FLIP_HORIZONTAL";
   public static final String PREF_FLIP_VERTICAL= "codingblocks.com.gripple.SettingsActivity.FLIP_VERTICAL";
   public static final String PREF_ROTATE_DEGREES = "codingblocks.com.gripple.SettingsActivity.ROTATE_DEGREES";
   public static final String PREF_AUTH_USERNAME = "codingblocks.com.gripple.SettingsActivity.PREF_AUTH_USERNAME";
   public static final String PREF_AUTH_PASSWORD = "codingblocks.com.gripple.SettingsActivity.PREF_AUTH_PASSWORD";

    private static final boolean ALWAYS_SIMPLE_PREFS = false;

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = (preference, value) -> {
        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
            // For list preferences, look up the correct display value in
            // the preference's 'entries' list.
            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(stringValue);

            // Set the summary to reflect the new value.
            preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);

        } else if (preference.getKey().equals(PREF_AUTH_PASSWORD)) {
            if (!TextUtils.isEmpty(stringValue)) {
                preference.setSummary(R.string.pref_hidden_password);
            }
        } else {
            // For all other preferences, set the summary to the value's
            // simple string representation.
            preference.setSummary(stringValue);
        }
        return true;
    };


    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }


    private static boolean isSimplePreferences(Context context) {
        return ALWAYS_SIMPLE_PREFS
                || Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
                || !isXLargeTablet(context);
    }


    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }


    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (!super.onMenuItemSelected(featureId, item)) {

            NavUtils.navigateUpFromSameTask(this);
            }
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        setupSimplePreferencesScreen();
    }


    private void setupSimplePreferencesScreen() {
        if (!isSimplePreferences(this)) {
            return;
        }

        addPreferencesFromResource(R.xml.pref_general);

        PreferenceCategory fakeHeader = new PreferenceCategory(this);
        fakeHeader.setTitle(R.string.pref_header_authentication);
        getPreferenceScreen().addPreference(fakeHeader);
        addPreferencesFromResource(R.xml.pref_authentication);

        bindPreferenceSummaryToValue(findPreference(PREF_IPCAM_URL));
        bindPreferenceSummaryToValue(findPreference(PREF_AUTH_USERNAME));
        bindPreferenceSummaryToValue(findPreference(PREF_AUTH_PASSWORD));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this) && !isSimplePreferences(this);
    }

    /**
     * {@inheritDoc}
//     */
//    @Override
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    public void onBuildHeaders(List<Header> target) {
//        if (!isSimplePreferences(this)) {
//            loadHeadersFromResource(R.xml.pref_headers, target);
//        }
//    }


    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName)
                || AuthenticationPreferenceFragment.class.getName().equals(fragmentName);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);

            bindPreferenceSummaryToValue(findPreference(PREF_IPCAM_URL));
            bindPreferenceSummaryToValue(findPreference(PREF_FLIP_HORIZONTAL));
            bindPreferenceSummaryToValue(findPreference(PREF_FLIP_VERTICAL));
            bindPreferenceSummaryToValue(findPreference(PREF_ROTATE_DEGREES));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows authentication preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class AuthenticationPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_authentication);
            setHasOptionsMenu(true);

            bindPreferenceSummaryToValue(findPreference(PREF_AUTH_USERNAME));
            bindPreferenceSummaryToValue(findPreference(PREF_AUTH_PASSWORD));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}

