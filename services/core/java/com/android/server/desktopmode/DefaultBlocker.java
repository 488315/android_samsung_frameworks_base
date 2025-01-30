package com.android.server.desktopmode;

import android.R;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.hardware.display.DisplayManager;
import android.hardware.display.SemWifiDisplayStatus;
import android.provider.Settings;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.IDesktopModeBlocker;
import com.samsung.android.knox.custom.ProKioskManager;

/* loaded from: classes2.dex */
public class DefaultBlocker {
    public final ActivityManager mActivityManager;
    public final BlockerImpl mBlocker = new BlockerImpl(0);
    public final Context mContext;
    public final DisplayManager mDisplayManager;

    public class BlockerImpl extends IDesktopModeBlocker.Stub {
        public int reasonCode;

        public BlockerImpl(int i) {
            this.reasonCode = i;
        }

        public String onBlocked() {
            int i = this.reasonCode;
            if (i == 3) {
                return DefaultBlocker.this.mContext.getString(R.string.global_action_voice_assist);
            }
            if (i == 4) {
                return DefaultBlocker.this.mContext.getString(R.string.global_action_settings);
            }
            if (i == 6) {
                return DefaultBlocker.this.mContext.getString(R.string.global_action_bug_report, DefaultBlocker.this.mContext.getString(17042504));
            }
            if (i != 7) {
                return DefaultBlocker.this.mContext.getString(R.string.global_actions_toggle_airplane_mode);
            }
            return null;
        }
    }

    public DefaultBlocker(Context context, ActivityManager activityManager, DisplayManager displayManager) {
        this.mContext = context;
        this.mActivityManager = activityManager;
        this.mDisplayManager = displayManager;
    }

    public BlockerImpl getBlocker(State state) {
        if (isLockTaskMode()) {
            this.mBlocker.reasonCode = 1;
        } else if (isProKioskMode()) {
            this.mBlocker.reasonCode = 2;
        } else if (this.mContext.getPackageManager().isSafeMode()) {
            this.mBlocker.reasonCode = 3;
        } else if (isUnavailableUser(state)) {
            this.mBlocker.reasonCode = 4;
        } else if (isKidsLauncherMode()) {
            this.mBlocker.reasonCode = 5;
        } else if (isSmartViewConnected()) {
            this.mBlocker.reasonCode = 6;
        } else {
            if (!isNewDeXEnabled()) {
                return null;
            }
            this.mBlocker.reasonCode = 7;
        }
        return this.mBlocker;
    }

    public final boolean isLockTaskMode() {
        return this.mActivityManager.getLockTaskModeState() == 1;
    }

    public final boolean isProKioskMode() {
        ProKioskManager proKioskManager = ProKioskManager.getInstance();
        return proKioskManager != null && proKioskManager.getProKioskState();
    }

    public final boolean isUnavailableUser(State state) {
        return state.getCurrentUserId() != 0;
    }

    public final boolean isKidsLauncherMode() {
        ResolveInfo resolveActivity = this.mContext.getPackageManager().resolveActivity(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME"), 65536);
        return resolveActivity != null && resolveActivity.activityInfo.name.contains("com.sec.android.app.kidshome");
    }

    public final boolean isSmartViewConnected() {
        SemWifiDisplayStatus semGetWifiDisplayStatus = this.mDisplayManager.semGetWifiDisplayStatus();
        if (semGetWifiDisplayStatus != null) {
            return !(semGetWifiDisplayStatus.getActiveDisplayState() != 2 || semGetWifiDisplayStatus.getConnectedState() == 3 || (DesktopModeFeature.SUPPORT_WIRELESS_DEX && semGetWifiDisplayStatus.getConnectedState() == 2)) || isLeboCastConnectionEnabled();
        }
        return false;
    }

    public final boolean isLeboCastConnectionEnabled() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "lelink_cast_on", 0) == 1;
    }

    public final boolean isNewDeXEnabled() {
        return DesktopModeFeature.SUPPORT_NEW_DEX && "new".equals(DesktopModeSettings.getSettings(this.mContext.getContentResolver(), "dex_mode", DesktopModeSettings.DEX_MODE_DEFAULT_VALUE));
    }

    public static String reasonToString(int i) {
        switch (i) {
            case 0:
                return "REASON_NOT_BLOCKED";
            case 1:
                return "REASON_LOCK_TASK_MODE";
            case 2:
                return "REASON_PRO_KIOSK_MODE";
            case 3:
                return "REASON_SAFE_MODE";
            case 4:
                return "REASON_UNAVAILABLE_USER";
            case 5:
                return "REASON_KIDS_MODE";
            case 6:
                return "REASON_SMART_VIEW";
            case 7:
                return "REASON_NEW_DEX";
            default:
                return "Unknown=" + i;
        }
    }
}
