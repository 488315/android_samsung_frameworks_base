package com.android.systemui.tuner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.hardware.display.AmbientDisplayConfiguration;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.samsung.android.knox.custom.IKnoxCustomManager;

public final class TunerFragment extends PreferenceFragment {
    public static final String[] DEBUG_ONLY = {"nav_bar", BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD, "picture_in_picture"};
    public final TunerService mTunerService;

    public final class TunerWarningFragment extends DialogFragment {
        @Override // android.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            return new AlertDialog.Builder(getContext()).setTitle(R.string.tuner_warning_title).setMessage(R.string.tuner_warning).setPositiveButton(R.string.got_it, new DialogInterface.OnClickListener() { // from class: com.android.systemui.tuner.TunerFragment.TunerWarningFragment.1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    Settings.Secure.putInt(TunerWarningFragment.this.getContext().getContentResolver(), "seen_tuner_warning", 1);
                }
            }).show();
        }
    }

    public TunerFragment(TunerService tunerService) {
        this.mTunerService = tunerService;
    }

    @Override // android.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override // androidx.preference.PreferenceFragment, android.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    @Override // android.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.add(0, 2, 0, R.string.remove_from_settings);
    }

    @Override // androidx.preference.PreferenceFragment
    public final void onCreatePreferences(String str) {
        addPreferencesFromResource(R.xml.tuner_prefs);
        if (!getContext().getSharedPreferences("plugin_prefs", 0).getBoolean("plugins", false)) {
            this.mPreferenceManager.mPreferenceScreen.removePreference(findPreference("plugins"));
        }
        if (!new AmbientDisplayConfiguration(getContext()).alwaysOnAvailable()) {
            this.mPreferenceManager.mPreferenceScreen.removePreference(findPreference("doze"));
        }
        if (!Build.IS_DEBUGGABLE) {
            for (int i = 0; i < 3; i++) {
                Preference findPreference = findPreference(DEBUG_ONLY[i]);
                if (findPreference != null) {
                    this.mPreferenceManager.mPreferenceScreen.removePreference(findPreference);
                }
            }
        }
        if (Settings.Secure.getInt(getContext().getContentResolver(), "seen_tuner_warning", 0) == 0 && getFragmentManager().findFragmentByTag("tuner_warning") == null) {
            new TunerWarningFragment().show(getFragmentManager(), "tuner_warning");
        }
    }

    @Override // android.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 2) {
            this.mTunerService.showResetRequest(new Runnable() { // from class: com.android.systemui.tuner.TunerFragment$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TunerFragment tunerFragment = TunerFragment.this;
                    if (tunerFragment.getActivity() != null) {
                        tunerFragment.getActivity().finish();
                    }
                }
            });
            return true;
        }
        if (itemId != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        getActivity().finish();
        return true;
    }

    @Override // android.app.Fragment
    public final void onPause() {
        super.onPause();
        MetricsLogger.visibility(getContext(), IKnoxCustomManager.Stub.TRANSACTION_setForceAutoShutDownState, false);
    }

    @Override // android.app.Fragment
    public final void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.system_ui_tuner);
        MetricsLogger.visibility(getContext(), IKnoxCustomManager.Stub.TRANSACTION_setForceAutoShutDownState, true);
    }
}
