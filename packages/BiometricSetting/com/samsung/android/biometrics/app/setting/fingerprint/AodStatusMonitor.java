package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.samsung.android.biometrics.app.setting.Utils;
import java.util.Calendar;
import java.util.Set;

/* loaded from: classes.dex */
public final class AodStatusMonitor {

    @VisibleForTesting
    static final String KEY_AOD_SHOW_STATUS = "aod_show_state";
    private int mAodEndTime;
    private int mAodShowState;
    private int mAodStartTime;

    @VisibleForTesting
    ContentObserver mContentObserver;
    private final Context mContext;
    private boolean mIsDisabledInPsm;
    private boolean mIsEnabledAod;
    private boolean mIsEnabledAodTapToShow;

    @VisibleForTesting
    Set<Callback> mCallbacks = new ArraySet();
    private final Uri mAodShowStatusUri = Settings.System.getUriFor(KEY_AOD_SHOW_STATUS);

    public interface Callback {
        void onAodStart();

        void onAodStop();
    }

    public AodStatusMonitor(Context context) {
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateValue() {
        Context context = this.mContext;
        this.mAodShowState = Settings.System.getIntForUser(context.getContentResolver(), KEY_AOD_SHOW_STATUS, 0, -2);
        this.mIsEnabledAod = Settings.System.getIntForUser(context.getContentResolver(), "aod_mode", 0, -2) == 1;
        this.mIsEnabledAodTapToShow = Settings.System.getIntForUser(context.getContentResolver(), "aod_tap_to_show_mode", 0, -2) == 1;
        this.mAodStartTime = Settings.System.getIntForUser(context.getContentResolver(), "aod_mode_start_time", 0, -2);
        this.mAodEndTime = Settings.System.getIntForUser(context.getContentResolver(), "aod_mode_end_time", 0, -2);
        String stringForUser = Settings.Global.getStringForUser(context.getContentResolver(), "psm_always_on_display_mode", -2);
        if (stringForUser != null) {
            String[] split = stringForUser.split(",");
            this.mIsDisabledInPsm = split.length == 0 || "1".equals(split[0]);
        }
        Log.i("BSS_AodStatusMonitor", "updateValue: " + this.mIsEnabledAod + ", " + this.mAodShowState + ", " + this.mIsEnabledAodTapToShow + ", " + this.mAodStartTime + ", " + this.mAodEndTime + ", " + this.mIsDisabledInPsm);
    }

    public final void addCallback(Callback callback) {
        this.mCallbacks.add(callback);
    }

    public final boolean isAodTransitionAnimationEnabled() {
        if (!Utils.Config.FEATURE_SUPPORT_AOD_TRANSITION_ANIMATION) {
            return false;
        }
        updateValue();
        return isEnabledShowAlways() || isInAodScheduleTime();
    }

    public final boolean isDisabledInPSM() {
        return this.mIsDisabledInPsm;
    }

    public final boolean isEnabled() {
        return this.mIsEnabledAod;
    }

    public final boolean isEnabledShowAlways() {
        return this.mIsEnabledAod && !this.mIsEnabledAodTapToShow && this.mAodStartTime == this.mAodEndTime;
    }

    public final boolean isEnabledTapToShow() {
        return this.mIsEnabledAod && this.mIsEnabledAodTapToShow;
    }

    public final boolean isInAodScheduleTime() {
        if (!((!this.mIsEnabledAod || this.mIsEnabledAodTapToShow || this.mAodStartTime == this.mAodEndTime) ? false : true)) {
            return false;
        }
        int i = this.mAodStartTime;
        int i2 = this.mAodEndTime;
        if (i > i2) {
            i2 += 1440;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int i3 = calendar.get(12) + (calendar.get(11) * 60);
        return i <= i3 && i3 <= i2;
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
        Context context = this.mContext;
        this.mContentObserver = new ContentObserver(context.getMainThreadHandler()) { // from class: com.samsung.android.biometrics.app.setting.fingerprint.AodStatusMonitor.1
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
        context.getContentResolver().registerContentObserver(this.mAodShowStatusUri, false, this.mContentObserver, -2);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("psm_always_on_display_mode"), false, this.mContentObserver, -2);
        context.getContentResolver().registerContentObserver(Settings.System.getUriFor("aod_mode"), false, this.mContentObserver, -2);
        updateValue();
    }

    public final void stop() {
        if (this.mContentObserver == null) {
            return;
        }
        this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
        this.mContentObserver = null;
    }
}
