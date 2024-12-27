package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Log;

import java.util.Calendar;
import java.util.Set;

public final class AodStatusMonitor {
    static final String KEY_AOD_SHOW_STATUS = "aod_show_state";
    public int mAodEndTime;
    public int mAodShowState;
    public int mAodStartTime;
    ContentObserver mContentObserver;
    public final Context mContext;
    public boolean mIsDisabledInPsm;
    public boolean mIsEnabledAod;
    public boolean mIsEnabledAodTapToShow;
    Set mCallbacks = new ArraySet();
    public final Uri mAodShowStatusUri = Settings.System.getUriFor(KEY_AOD_SHOW_STATUS);

    public interface Callback {
        void onAodStart();

        void onAodStop();
    }

    public AodStatusMonitor(Context context) {
        this.mContext = context;
    }

    public final void addCallback(Callback callback) {
        this.mCallbacks.add(callback);
    }

    public final boolean isInAodScheduleTime() {
        int i;
        int i2;
        if (this.mIsEnabledAod
                && !this.mIsEnabledAodTapToShow
                && (i = this.mAodStartTime) != (i2 = this.mAodEndTime)) {
            if (i > i2) {
                i2 += 1440;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            int i3 = calendar.get(12) + (calendar.get(11) * 60);
            if (i <= i3 && i3 <= i2) {
                return true;
            }
        }
        return false;
    }

    public final boolean isShowing() {
        return this.mAodShowState == 1;
    }

    public final void removeCallback(Callback callback) {
        this.mCallbacks.remove(callback);
    }

    public final void start() {
        if (this.mContentObserver != null) {
            return;
        }
        this.mContentObserver =
                new ContentObserver(
                        this.mContext
                                .getMainThreadHandler()) { // from class:
                                                           // com.samsung.android.biometrics.app.setting.fingerprint.AodStatusMonitor.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        AodStatusMonitor.this.updateValue();
                        if (AodStatusMonitor.this.mAodShowStatusUri.equals(uri)) {
                            AodStatusMonitor aodStatusMonitor = AodStatusMonitor.this;
                            for (Callback callback : aodStatusMonitor.mCallbacks) {
                                if (aodStatusMonitor.isShowing()) {
                                    callback.onAodStart();
                                } else {
                                    callback.onAodStop();
                                }
                            }
                        }
                    }
                };
        this.mContext
                .getContentResolver()
                .registerContentObserver(this.mAodShowStatusUri, false, this.mContentObserver, -2);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("psm_always_on_display_mode"),
                        false,
                        this.mContentObserver,
                        -2);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("aod_mode"), false, this.mContentObserver, -2);
        updateValue();
    }

    public final void stop() {
        if (this.mContentObserver == null) {
            return;
        }
        this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
        this.mContentObserver = null;
    }

    public final void updateValue() {
        this.mAodShowState =
                Settings.System.getIntForUser(
                        this.mContext.getContentResolver(), KEY_AOD_SHOW_STATUS, 0, -2);
        this.mIsEnabledAod =
                Settings.System.getIntForUser(this.mContext.getContentResolver(), "aod_mode", 0, -2)
                        == 1;
        this.mIsEnabledAodTapToShow =
                Settings.System.getIntForUser(
                                this.mContext.getContentResolver(), "aod_tap_to_show_mode", 0, -2)
                        == 1;
        this.mAodStartTime =
                Settings.System.getIntForUser(
                        this.mContext.getContentResolver(), "aod_mode_start_time", 0, -2);
        this.mAodEndTime =
                Settings.System.getIntForUser(
                        this.mContext.getContentResolver(), "aod_mode_end_time", 0, -2);
        String stringForUser =
                Settings.Global.getStringForUser(
                        this.mContext.getContentResolver(), "psm_always_on_display_mode", -2);
        if (stringForUser != null) {
            String[] split = stringForUser.split(",");
            this.mIsDisabledInPsm = split.length == 0 || "1".equals(split[0]);
        }
        Log.i(
                "BSS_AodStatusMonitor",
                "updateValue: "
                        + this.mIsEnabledAod
                        + ", "
                        + this.mAodShowState
                        + ", "
                        + this.mIsEnabledAodTapToShow
                        + ", "
                        + this.mAodStartTime
                        + ", "
                        + this.mAodEndTime
                        + ", "
                        + this.mIsDisabledInPsm);
    }
}
