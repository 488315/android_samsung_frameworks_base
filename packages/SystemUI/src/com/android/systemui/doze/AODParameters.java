package com.android.systemui.doze;

import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.LsRune;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.util.SettingsHelper;
import dagger.Lazy;
import java.util.Calendar;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class AODParameters {
    private final SettingsHelper.OnChangedCallback mAODSettingStateCallback;
    public boolean mDozeAlwaysOn = true;
    public boolean mDozeUiState;
    public final boolean mIsSystemUser;
    public final Lazy mPluginAODManagerLazy;
    private SettingsHelper mSettingsHelper;
    public final StatusBarStateController mStatusBarStateController;
    public final UnlockedScreenOffAnimationController mUnlockedScreenOffAnimationController;

    public AODParameters(SettingsHelper settingsHelper, Lazy lazy, StatusBarStateController statusBarStateController, boolean z, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController) {
        SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.doze.AODParameters$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                AODParameters aODParameters = AODParameters.this;
                if (!aODParameters.mIsSystemUser) {
                    Log.d("AODParameters", "onAODSettingStateChanged: return not System user");
                } else {
                    aODParameters.updateDozeAlwaysOn();
                    ((PluginAODManager) aODParameters.mPluginAODManagerLazy.get()).updateAnimateScreenOff();
                }
            }
        };
        this.mAODSettingStateCallback = onChangedCallback;
        this.mSettingsHelper = settingsHelper;
        this.mPluginAODManagerLazy = lazy;
        this.mStatusBarStateController = statusBarStateController;
        this.mIsSystemUser = z;
        this.mUnlockedScreenOffAnimationController = unlockedScreenOffAnimationController;
        settingsHelper.registerCallback(onChangedCallback, Settings.System.getUriFor(SettingsHelper.INDEX_AOD_MODE), Settings.System.getUriFor(SettingsHelper.INDEX_AOD_TAP_TO_SHOW_MODE), Settings.System.getUriFor(SettingsHelper.INDEX_AOD_MODE_START_TIME), Settings.System.getUriFor(SettingsHelper.INDEX_AOD_MODE_END_TIME), Settings.System.getUriFor(SettingsHelper.INDEX_AOD_SHOW_FOR_NEW_NOTI), Settings.System.getUriFor(SettingsHelper.INDEX_AOD_SHOW_LOCKSCREEN_WALLPAPER));
        updateDozeAlwaysOn();
        if (LsRune.AOD_FULLSCREEN) {
            statusBarStateController.addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.doze.AODParameters.1
                @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
                public final void onStatePostChange() {
                    AODParameters aODParameters = AODParameters.this;
                    if (aODParameters.mUnlockedScreenOffAnimationController.lightRevealAnimationPlaying) {
                        return;
                    }
                    ((PluginAODManager) aODParameters.mPluginAODManagerLazy.get()).updateAnimateScreenOff();
                }
            });
        }
    }

    public final void updateDozeAlwaysOn() {
        boolean isAODEnabled = this.mSettingsHelper.isAODEnabled();
        boolean isAODTapToShowModeEnabled = this.mSettingsHelper.isAODTapToShowModeEnabled();
        boolean isAODShowForNewNotiModeEnabled = this.mSettingsHelper.isAODShowForNewNotiModeEnabled();
        if (!isAODEnabled) {
            this.mDozeAlwaysOn = false;
            return;
        }
        if (isAODTapToShowModeEnabled || isAODShowForNewNotiModeEnabled) {
            this.mDozeAlwaysOn = false;
            return;
        }
        int aODStartTime = this.mSettingsHelper.getAODStartTime();
        int aODEndTime = this.mSettingsHelper.getAODEndTime();
        if (aODStartTime != aODEndTime) {
            Calendar calendar = Calendar.getInstance();
            int i = (calendar.get(11) * 60) + calendar.get(12);
            if (aODStartTime >= aODEndTime ? !(i >= aODStartTime || i < aODEndTime) : !(i >= aODStartTime && i < aODEndTime)) {
                this.mDozeAlwaysOn = false;
                return;
            }
        }
        this.mDozeAlwaysOn = true;
    }
}
