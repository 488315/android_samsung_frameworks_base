package com.android.systemui.tuner;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.Iterator;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class TunerActivity extends Activity implements PreferenceFragment.OnPreferenceStartFragmentCallback, PreferenceFragment.OnPreferenceStartScreenCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final DemoModeController mDemoModeController;
    public final GlobalSettings mGlobalSettings;
    public final TunerService mTunerService;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SubSettingsFragment extends PreferenceFragment {
        public PreferenceScreen mParentScreen;

        @Override // androidx.preference.PreferenceFragment
        public final void onCreatePreferences(String str) {
            this.mParentScreen = (PreferenceScreen) ((PreferenceFragment) getTargetFragment()).mPreferenceManager.mPreferenceScreen.findPreference(str);
            PreferenceManager preferenceManager = this.mPreferenceManager;
            PreferenceScreen preferenceScreen = new PreferenceScreen(preferenceManager.mContext, null);
            preferenceScreen.onAttachedToHierarchy(preferenceManager);
            setPreferenceScreen(preferenceScreen);
            while (this.mParentScreen.getPreferenceCount() > 0) {
                Preference preference = this.mParentScreen.getPreference(0);
                this.mParentScreen.removePreference(preference);
                preferenceScreen.addPreference(preference);
            }
        }

        @Override // android.app.Fragment
        public final void onDestroy() {
            super.onDestroy();
            PreferenceScreen preferenceScreen = this.mPreferenceManager.mPreferenceScreen;
            while (preferenceScreen.getPreferenceCount() > 0) {
                Preference preference = preferenceScreen.getPreference(0);
                preferenceScreen.removePreference(preference);
                this.mParentScreen.addPreference(preference);
            }
        }
    }

    public TunerActivity(DemoModeController demoModeController, TunerService tunerService, GlobalSettings globalSettings) {
        this.mDemoModeController = demoModeController;
        this.mTunerService = tunerService;
        this.mGlobalSettings = globalSettings;
    }

    @Override // android.app.Activity
    public final void onBackPressed() {
        if (getFragmentManager().popBackStackImmediate()) {
            return;
        }
        super.onBackPressed();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTheme(2132018522);
        String action = getIntent().getAction();
        Toast.makeText(this, (action != null && action.equals("com.android.settings.action.DEMO_MODE") ? "Demo mode" : "SystemUI tuner").concat(" isn't supported on this device"), 0).show();
        finish();
        getWindow().addFlags(Integer.MIN_VALUE);
        requestWindowFeature(1);
        setContentView(R.layout.tuner_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        if (toolbar != null) {
            setActionBar(toolbar);
        }
        if (getFragmentManager().findFragmentByTag("tuner") == null) {
            String action2 = getIntent().getAction();
            getFragmentManager().beginTransaction().replace(R.id.content_frame, (action2 == null || !action2.equals("com.android.settings.action.DEMO_MODE")) ? new TunerFragment(this.mTunerService) : new DemoModeFragment(this.mDemoModeController, this.mGlobalSettings), "tuner").commit();
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        final int i = 0;
        Dependency.destroy(FragmentService.class, new Consumer() { // from class: com.android.systemui.tuner.TunerActivity$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i) {
                    case 0:
                        int i2 = TunerActivity.$r8$clinit;
                        Iterator it = ((FragmentService) obj).mHosts.values().iterator();
                        while (it.hasNext()) {
                            ((FragmentService.FragmentHostState) it.next()).mFragmentHostManager.mFragments.dispatchDestroy();
                        }
                        break;
                    default:
                        DisplayLifecycle displayLifecycle = (DisplayLifecycle) obj;
                        displayLifecycle.mDisplayManager.unregisterDisplayListener(displayLifecycle.mDisplayListener);
                        break;
                }
            }
        });
        final int i2 = 1;
        Dependency.destroy(DisplayLifecycle.class, new Consumer() { // from class: com.android.systemui.tuner.TunerActivity$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i2) {
                    case 0:
                        int i22 = TunerActivity.$r8$clinit;
                        Iterator it = ((FragmentService) obj).mHosts.values().iterator();
                        while (it.hasNext()) {
                            ((FragmentService.FragmentHostState) it.next()).mFragmentHostManager.mFragments.dispatchDestroy();
                        }
                        break;
                    default:
                        DisplayLifecycle displayLifecycle = (DisplayLifecycle) obj;
                        displayLifecycle.mDisplayManager.unregisterDisplayListener(displayLifecycle.mDisplayListener);
                        break;
                }
            }
        });
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean onMenuItemSelected(int i, MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onMenuItemSelected(i, menuItem);
        }
        onBackPressed();
        return true;
    }
}
