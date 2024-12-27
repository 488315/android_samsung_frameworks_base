package com.android.systemui.tuner;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.MenuItem;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.demomode.DemoModeAvailabilityTracker;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.util.settings.GlobalSettings;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.sec.ims.IMSParameter;
import com.sec.ims.configuration.DATA;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DemoModeFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
    public static final String[] STATUS_ICONS = {"volume", "bluetooth", "location", "alarm", "zen", "sync", "tty", "eri", "mute", "speakerphone", "managed_profile"};
    public final DemoModeController mDemoModeController;
    public Tracker mDemoModeTracker;
    public SwitchPreference mEnabledSwitch;
    public final GlobalSettings mGlobalSettings;
    public SwitchPreference mOnSwitch;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Tracker extends DemoModeAvailabilityTracker {
        public Tracker(Context context, GlobalSettings globalSettings) {
            super(context, globalSettings);
        }

        @Override // com.android.systemui.demomode.DemoModeAvailabilityTracker
        public final void onDemoModeAvailabilityChanged() {
            String[] strArr = DemoModeFragment.STATUS_ICONS;
            DemoModeFragment demoModeFragment = DemoModeFragment.this;
            demoModeFragment.mEnabledSwitch.setChecked(demoModeFragment.mDemoModeTracker.isDemoModeAvailable);
            demoModeFragment.mOnSwitch.setEnabled(demoModeFragment.mDemoModeTracker.isDemoModeAvailable);
            demoModeFragment.mOnSwitch.setChecked(demoModeFragment.mDemoModeTracker.isInDemoMode);
        }

        @Override // com.android.systemui.demomode.DemoModeAvailabilityTracker
        public final void onDemoModeFinished() {
            String[] strArr = DemoModeFragment.STATUS_ICONS;
            DemoModeFragment demoModeFragment = DemoModeFragment.this;
            demoModeFragment.mOnSwitch.setChecked(demoModeFragment.mDemoModeTracker.isInDemoMode);
        }

        @Override // com.android.systemui.demomode.DemoModeAvailabilityTracker
        public final void onDemoModeStarted() {
            String[] strArr = DemoModeFragment.STATUS_ICONS;
            DemoModeFragment demoModeFragment = DemoModeFragment.this;
            demoModeFragment.mOnSwitch.setChecked(demoModeFragment.mDemoModeTracker.isInDemoMode);
        }
    }

    public DemoModeFragment(DemoModeController demoModeController, GlobalSettings globalSettings) {
        this.mDemoModeController = demoModeController;
        this.mGlobalSettings = globalSettings;
    }

    @Override // androidx.preference.PreferenceFragment
    public final void onCreatePreferences(String str) {
        Context context = getContext();
        SwitchPreference switchPreference = new SwitchPreference(context);
        this.mEnabledSwitch = switchPreference;
        switchPreference.setTitle(R.string.enable_demo_mode);
        this.mEnabledSwitch.mOnChangeListener = this;
        SwitchPreference switchPreference2 = new SwitchPreference(context);
        this.mOnSwitch = switchPreference2;
        switchPreference2.setTitle(R.string.show_demo_mode);
        this.mOnSwitch.setEnabled(false);
        this.mOnSwitch.mOnChangeListener = this;
        PreferenceManager preferenceManager = this.mPreferenceManager;
        preferenceManager.getClass();
        PreferenceScreen preferenceScreen = new PreferenceScreen(context, null);
        preferenceScreen.onAttachedToHierarchy(preferenceManager);
        preferenceScreen.addPreference(this.mEnabledSwitch);
        preferenceScreen.addPreference(this.mOnSwitch);
        setPreferenceScreen(preferenceScreen);
        Tracker tracker = new Tracker(context, this.mGlobalSettings);
        this.mDemoModeTracker = tracker;
        tracker.startTracking();
        this.mEnabledSwitch.setChecked(this.mDemoModeTracker.isDemoModeAvailable);
        this.mOnSwitch.setEnabled(this.mDemoModeTracker.isDemoModeAvailable);
        this.mOnSwitch.setChecked(this.mDemoModeTracker.isInDemoMode);
        setHasOptionsMenu(true);
    }

    @Override // android.app.Fragment
    public final void onDestroy() {
        Tracker tracker = this.mDemoModeTracker;
        ContentResolver contentResolver = tracker.context.getContentResolver();
        contentResolver.unregisterContentObserver(tracker.allowedObserver);
        contentResolver.unregisterContentObserver(tracker.onObserver);
        super.onDestroy();
    }

    @Override // android.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            getFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // android.app.Fragment
    public final void onPause() {
        super.onPause();
        MetricsLogger.visibility(getContext(), IKnoxCustomManager.Stub.TRANSACTION_setBrightness, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v38 */
    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        String str;
        ?? r8 = obj == Boolean.TRUE ? 1 : 0;
        if (preference == this.mEnabledSwitch) {
            if (r8 == 0) {
                this.mOnSwitch.setChecked(false);
                this.mDemoModeController.globalSettings.putInt("sysui_tuner_demo_on", 0);
            }
            MetricsLogger.action(getContext(), IKnoxCustomManager.Stub.TRANSACTION_setAppsButtonState, (boolean) r8);
            this.mDemoModeController.globalSettings.putInt("sysui_demo_allowed", r8);
        } else {
            if (preference != this.mOnSwitch) {
                return false;
            }
            MetricsLogger.action(getContext(), IKnoxCustomManager.Stub.TRANSACTION_getAppsButtonState, (boolean) r8);
            if (r8 != 0) {
                Intent intent = new Intent("com.android.systemui.demo");
                this.mDemoModeController.globalSettings.putInt("sysui_tuner_demo_on", 1);
                intent.putExtra("command", SubRoom.EXTRA_VALUE_CLOCK);
                try {
                    str = String.format("%02d00", Integer.valueOf(Integer.valueOf(Build.VERSION.RELEASE_OR_CODENAME.split("\\.")[0]).intValue() % 24));
                } catch (IllegalArgumentException unused) {
                    str = "1010";
                }
                intent.putExtra("hhmm", str);
                getContext().sendBroadcast(intent);
                intent.putExtra("command", "network");
                intent.putExtra(ImsProfile.PDN_WIFI, "show");
                intent.putExtra("mobile", "show");
                intent.putExtra("sims", "1");
                intent.putExtra("nosim", "false");
                intent.putExtra(ActionResults.RESULT_SET_VOLUME_SUCCESS, "4");
                intent.putExtra("datatype", "lte");
                getContext().sendBroadcast(intent);
                intent.putExtra("fully", "true");
                getContext().sendBroadcast(intent);
                intent.putExtra("command", "battery");
                intent.putExtra(ActionResults.RESULT_SET_VOLUME_SUCCESS, DATA.DM_FIELD_INDEX.UT_PDN);
                intent.putExtra("plugged", "false");
                getContext().sendBroadcast(intent);
                intent.putExtra("command", IMSParameter.CALL.STATUS);
                String[] strArr = STATUS_ICONS;
                for (int i = 0; i < 11; i++) {
                    intent.putExtra(strArr[i], "hide");
                }
                getContext().sendBroadcast(intent);
                intent.putExtra("command", "notifications");
                intent.putExtra("visible", "false");
                getContext().sendBroadcast(intent);
            } else {
                this.mDemoModeController.globalSettings.putInt("sysui_tuner_demo_on", 0);
            }
        }
        return true;
    }

    @Override // android.app.Fragment
    public final void onResume() {
        super.onResume();
        MetricsLogger.visibility(getContext(), IKnoxCustomManager.Stub.TRANSACTION_setBrightness, true);
    }
}
