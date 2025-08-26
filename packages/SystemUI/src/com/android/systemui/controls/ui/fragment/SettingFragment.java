package com.android.systemui.controls.ui.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.controls.util.SALogger;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class SettingFragment extends PreferenceFragmentCompat {
    public SwitchPreferenceCompat controlDevicePreference;
    public final SALogger saLogger;
    public PreferenceScreen screen;
    public SwitchPreferenceCompat showDevicePreference;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SettingFragment(SALogger sALogger) {
        this.saLogger = sALogger;
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(String str) throws Resources.NotFoundException {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager == null) {
            throw new RuntimeException("This should be called after super.onCreate.");
        }
        PreferenceScreen preferenceScreenInflateFromResource = preferenceManager.inflateFromResource(requireContext(), R.xml.preference_setting, this.mPreferenceManager.mPreferenceScreen);
        PreferenceManager preferenceManager2 = this.mPreferenceManager;
        PreferenceScreen preferenceScreen = preferenceManager2.mPreferenceScreen;
        if (preferenceScreenInflateFromResource != preferenceScreen) {
            if (preferenceScreen != null) {
                preferenceScreen.onDetached();
            }
            preferenceManager2.mPreferenceScreen = preferenceScreenInflateFromResource;
            this.mHavePrefs = true;
            if (this.mInitDone && !hasMessages(1)) {
                obtainMessage(1).sendToTarget();
            }
        }
        PreferenceScreen preferenceScreen2 = (PreferenceScreen) findPreference(PluginLock.KEY_SCREEN);
        if (preferenceScreen2 == null) {
            return;
        }
        this.screen = preferenceScreen2;
        SwitchPreferenceCompat switchPreferenceCompat = (SwitchPreferenceCompat) findPreference("ControlsSettingsUseWhilePhoneIsLocked");
        if (switchPreferenceCompat == null) {
            return;
        }
        this.showDevicePreference = switchPreferenceCompat;
        SwitchPreferenceCompat switchPreferenceCompat2 = (SwitchPreferenceCompat) findPreference("ControlsSettingsControlWhilePhoneIsLocked");
        if (switchPreferenceCompat2 == null) {
            return;
        }
        this.controlDevicePreference = switchPreferenceCompat2;
        final SwitchPreferenceCompat switchPreferenceCompat3 = this.showDevicePreference;
        if (switchPreferenceCompat3 == null) {
            switchPreferenceCompat3 = null;
        }
        boolean z = BasicRune.CONTROLS_SAMSUNG_STYLE_TABLET;
        if (z) {
            switchPreferenceCompat3.setTitle(getResources().getString(R.string.controls_settings_show_devices_while_tablet_is_locked));
            switchPreferenceCompat3.setSummary(getResources().getString(R.string.controls_settings_show_devices_while_tablet_is_locked_description));
        }
        switchPreferenceCompat3.mOnClickListener = new Preference.OnPreferenceClickListener() { // from class: com.android.systemui.controls.ui.fragment.SettingFragment$onCreatePreferences$1$1
            @Override // androidx.preference.Preference.OnPreferenceClickListener
            public final void onPreferenceClick(Preference preference) {
                SettingFragment settingFragment = this.this$0;
                SALogger sALogger = settingFragment.saLogger;
                SwitchPreferenceCompat switchPreferenceCompat4 = switchPreferenceCompat3;
                new SALogger.Event.SettingsShowDevicesOnOff(switchPreferenceCompat4.mChecked).sendEvent(sALogger.systemUIAnalyticsWrapper);
                Settings.Secure.putInt(switchPreferenceCompat4.mContext.getContentResolver(), "lockscreen_show_controls", switchPreferenceCompat4.mChecked ? 1 : 0);
                settingFragment.updatePreference(switchPreferenceCompat4.mChecked);
            }
        };
        SwitchPreferenceCompat switchPreferenceCompat4 = this.controlDevicePreference;
        final SwitchPreferenceCompat switchPreferenceCompat5 = switchPreferenceCompat4 != null ? switchPreferenceCompat4 : null;
        if (z) {
            switchPreferenceCompat5.setTitle(getResources().getString(R.string.controls_settings_control_devices_while_tablet_is_locked));
            switchPreferenceCompat5.setSummary(getResources().getString(R.string.controls_settings_control_devices_while_tablet_is_locked_description));
        }
        switchPreferenceCompat5.mOnClickListener = new Preference.OnPreferenceClickListener() { // from class: com.android.systemui.controls.ui.fragment.SettingFragment$onCreatePreferences$2$1
            @Override // androidx.preference.Preference.OnPreferenceClickListener
            public final void onPreferenceClick(Preference preference) {
                SALogger sALogger = this.this$0.saLogger;
                SwitchPreferenceCompat switchPreferenceCompat6 = switchPreferenceCompat5;
                new SALogger.Event.SettingsControlDevicesOnOff(switchPreferenceCompat6.mChecked).sendEvent(sALogger.systemUIAnalyticsWrapper);
                Settings.Secure.putInt(switchPreferenceCompat6.mContext.getContentResolver(), "lockscreen_allow_trivial_controls", switchPreferenceCompat6.mChecked ? 1 : 0);
            }
        };
        this.saLogger.sendScreenView(SALogger.Screen.Settings.INSTANCE);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) throws Resources.NotFoundException {
        ActionBar supportActionBar;
        FragmentActivity activity = getActivity();
        AppCompatActivity appCompatActivity = activity instanceof AppCompatActivity ? (AppCompatActivity) activity : null;
        if (appCompatActivity != null && (supportActionBar = appCompatActivity.getSupportActionBar()) != null) {
            String string = appCompatActivity.getString(R.string.controls_menu_settings);
            supportActionBar.setTitle(string);
            appCompatActivity.setTitle(string);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        View viewOnCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        viewOnCreateView.setBackgroundColor(viewOnCreateView.getContext().getColor(R.color.control_settings_activity_background));
        return viewOnCreateView;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        this.mCalled = true;
        SwitchPreferenceCompat switchPreferenceCompat = this.showDevicePreference;
        if (switchPreferenceCompat == null) {
            switchPreferenceCompat = null;
        }
        switchPreferenceCompat.setChecked(Settings.Secure.getInt(switchPreferenceCompat.mContext.getContentResolver(), "lockscreen_show_controls", 0) != 0);
        updatePreference(switchPreferenceCompat.mChecked);
        SwitchPreferenceCompat switchPreferenceCompat2 = this.controlDevicePreference;
        SwitchPreferenceCompat switchPreferenceCompat3 = switchPreferenceCompat2 != null ? switchPreferenceCompat2 : null;
        switchPreferenceCompat3.setChecked(Settings.Secure.getInt(switchPreferenceCompat3.mContext.getContentResolver(), "lockscreen_allow_trivial_controls", 0) != 0);
    }

    public final void updatePreference(boolean z) {
        if (!z) {
            PreferenceScreen preferenceScreen = this.screen;
            if (preferenceScreen == null) {
                preferenceScreen = null;
            }
            SwitchPreferenceCompat switchPreferenceCompat = this.controlDevicePreference;
            preferenceScreen.removePreference(switchPreferenceCompat != null ? switchPreferenceCompat : null);
            return;
        }
        PreferenceScreen preferenceScreen2 = this.screen;
        if (preferenceScreen2 == null) {
            preferenceScreen2 = null;
        }
        SwitchPreferenceCompat switchPreferenceCompat2 = this.controlDevicePreference;
        if (switchPreferenceCompat2 == null) {
            switchPreferenceCompat2 = null;
        }
        if (preferenceScreen2.findPreference(switchPreferenceCompat2.mKey) != null) {
            return;
        }
        PreferenceScreen preferenceScreen3 = this.screen;
        if (preferenceScreen3 == null) {
            preferenceScreen3 = null;
        }
        SwitchPreferenceCompat switchPreferenceCompat3 = this.controlDevicePreference;
        preferenceScreen3.addPreference(switchPreferenceCompat3 != null ? switchPreferenceCompat3 : null);
    }
}
