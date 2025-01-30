package com.android.systemui.doze;

import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.LsRune;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.util.SettingsHelper;
import dagger.Lazy;
import java.util.Calendar;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AODParameters {
    public final AODParameters$$ExternalSyntheticLambda0 mAODSettingStateCallback;
    public boolean mDozeAlwaysOn = true;
    public boolean mDozeUiState;
    public final Lazy mPluginAODManagerLazy;
    public final SettingsHelper mSettingsHelper;
    public final StatusBarStateController mStatusBarStateController;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.doze.AODParameters$$ExternalSyntheticLambda0, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    public AODParameters(SettingsHelper settingsHelper, Lazy lazy, StatusBarStateController statusBarStateController) {
        ?? r0 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.doze.AODParameters$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                AODParameters aODParameters = AODParameters.this;
                aODParameters.updateDozeAlwaysOn();
                ((PluginAODManager) aODParameters.mPluginAODManagerLazy.get()).updateAnimateScreenOff();
            }
        };
        this.mAODSettingStateCallback = r0;
        this.mSettingsHelper = settingsHelper;
        this.mPluginAODManagerLazy = lazy;
        this.mStatusBarStateController = statusBarStateController;
        settingsHelper.registerCallback(r0, Settings.System.getUriFor("aod_mode"), Settings.System.getUriFor("aod_tap_to_show_mode"), Settings.System.getUriFor("aod_mode_start_time"), Settings.System.getUriFor("aod_mode_end_time"), Settings.System.getUriFor("aod_show_for_new_noti"), Settings.System.getUriFor("aod_show_lockscreen_wallpaper"));
        updateDozeAlwaysOn();
        if (LsRune.AOD_FULLSCREEN) {
            statusBarStateController.addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.doze.AODParameters.1
                @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
                public final void onStatePostChange() {
                    ((PluginAODManager) AODParameters.this.mPluginAODManagerLazy.get()).updateAnimateScreenOff();
                }
            });
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0075  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateDozeAlwaysOn() {
        boolean z;
        SettingsHelper settingsHelper = this.mSettingsHelper;
        boolean isAODEnabled = settingsHelper.isAODEnabled();
        boolean z2 = settingsHelper.mItemLists.get("aod_tap_to_show_mode").getIntValue() != 0;
        boolean z3 = settingsHelper.mItemLists.get("aod_show_for_new_noti").getIntValue() != 0;
        if (!isAODEnabled) {
            this.mDozeAlwaysOn = false;
            return;
        }
        if (z2 || z3) {
            this.mDozeAlwaysOn = false;
            return;
        }
        int intValue = settingsHelper.mItemLists.get("aod_mode_start_time").getIntValue();
        int intValue2 = settingsHelper.mItemLists.get("aod_mode_end_time").getIntValue();
        if (intValue != intValue2) {
            Calendar calendar = Calendar.getInstance();
            int i = (calendar.get(11) * 60) + calendar.get(12);
            if (intValue >= intValue2 ? !(i >= intValue || i < intValue2) : !(i >= intValue && i < intValue2)) {
                z = false;
                if (z) {
                    this.mDozeAlwaysOn = false;
                    return;
                } else {
                    this.mDozeAlwaysOn = true;
                    return;
                }
            }
        }
        z = true;
        if (z) {
        }
    }
}
