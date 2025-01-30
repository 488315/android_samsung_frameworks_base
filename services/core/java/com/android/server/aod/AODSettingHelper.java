package com.android.server.aod;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

/* loaded from: classes.dex */
public class AODSettingHelper {
    public int mAODDefaultSetting;
    public Context mContext;
    public ContentResolver mResolver;

    public AODSettingHelper(Context context) {
        this.mAODDefaultSetting = 1;
        this.mContext = context;
        ContentResolver contentResolver = context.getContentResolver();
        this.mResolver = contentResolver;
        int intForUser = Settings.System.getIntForUser(contentResolver, "aod_mode", -1, -2);
        if (!AODManagerService.SUPPORT_AOD) {
            Settings.System.putIntForUser(this.mResolver, "aod_mode", 0, -2);
        } else if (intForUser == -1) {
            boolean isAODDefaultOn = AODConfig.isAODDefaultOn();
            this.mAODDefaultSetting = isAODDefaultOn ? 1 : 0;
            Settings.System.putIntForUser(this.mResolver, "aod_mode", isAODDefaultOn ? 1 : 0, -2);
        }
    }

    public boolean isAODEnabled() {
        return Settings.System.getIntForUser(this.mResolver, "aod_mode", this.mAODDefaultSetting, -2) == 1;
    }

    public boolean isEdgeShowWhenScreenOff() {
        return Settings.System.getIntForUser(this.mResolver, "edge_lighting_show_condition", 0, -2) != 1;
    }

    public void setAODEnabled(boolean z) {
        Settings.System.putIntForUser(this.mResolver, "aod_mode", z ? 1 : 0, -2);
    }

    public boolean isAODShowState() {
        return Settings.System.getIntForUser(this.mResolver, "aod_show_state", 0, -2) == 1;
    }

    public void clearAODShowState() {
        Settings.System.putIntForUser(this.mResolver, "aod_show_state", 0, -2);
    }

    public boolean isAODTapToShow() {
        return !AODConfig.isTapToShowDisabled && Settings.System.getIntForUser(this.mResolver, "aod_tap_to_show_mode", 1, -2) == 1;
    }

    public boolean isFingerScreenLock() {
        return Settings.Secure.getIntForUser(this.mResolver, "fingerprint_screen_lock", 0, -2) == 1;
    }

    public boolean isDozeAlwaysOn() {
        return Settings.Secure.getIntForUser(this.mResolver, "doze_always_on", -2) == 1;
    }

    public boolean isAODChargingMode() {
        return Settings.System.getIntForUser(this.mResolver, "aod_charging_mode", 1, -2) == 1;
    }

    public void setAODChargingMode(boolean z) {
        Settings.System.putIntForUser(this.mResolver, "aod_charging_mode", z ? 1 : 0, -2);
    }

    public boolean isFingerScreenOffIconAOD() {
        return Settings.Secure.getIntForUser(this.mResolver, "fingerprint_screen_off_icon_aod", -1, -2) == 1;
    }

    public boolean isMPSMEnabled() {
        return Settings.Global.getInt(this.mResolver, "low_power", 0) == 1;
    }

    public boolean isUPSMEnabled() {
        return Settings.System.getIntForUser(this.mResolver, "ultra_powersaving_mode", 0, -2) == 1;
    }

    public boolean isAODAuto() {
        return Settings.System.getIntForUser(this.mResolver, "aod_display_mode_auto", 0, -2) == 1;
    }

    public boolean isAODShowForNewNoti() {
        return Settings.System.getIntForUser(this.mResolver, "aod_show_for_new_noti", 0, -2) == 1;
    }

    public int getAODStartTime() {
        return Settings.System.getIntForUser(this.mResolver, "aod_mode_start_time", 0, -2);
    }

    public int getAODEndTime() {
        return Settings.System.getIntForUser(this.mResolver, "aod_mode_end_time", 0, -2);
    }
}
