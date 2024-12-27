package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.database.ContentObserver;
import android.provider.Settings;
import android.util.Log;

import com.samsung.android.biometrics.app.setting.Utils;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class UdfpsIconOptionMonitor {
    ContentObserver mContentObserver;
    public final Context mContext;
    public int mIconOptionWhenScreenOff;
    public int mIconOptionWhenScreenOn;
    public int mViEffectType;

    public UdfpsIconOptionMonitor(Context context) {
        this.mContext = context;
    }

    public final boolean isEnabledOnAod() {
        return this.mIconOptionWhenScreenOff == 2;
    }

    public final boolean isEnabledScreenOn() {
        return this.mIconOptionWhenScreenOn == 2;
    }

    public final void start() {
        updateValue();
        if (this.mContentObserver != null) {
            return;
        }
        this.mContentObserver =
                new ContentObserver(
                        this.mContext
                                .getMainThreadHandler()) { // from class:
                                                           // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsIconOptionMonitor.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        UdfpsIconOptionMonitor.this.updateValue();
                    }
                };
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("fingerprint_screen_off_icon_aod"),
                        false,
                        this.mContentObserver,
                        -2);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("fingerprint_screen_on_icon_lock"),
                        false,
                        this.mContentObserver,
                        -2);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("fingerprint_effect"),
                        false,
                        this.mContentObserver,
                        -2);
    }

    public final void stop() {
        if (this.mContentObserver == null) {
            return;
        }
        this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
        this.mContentObserver = null;
    }

    public final void updateValue() {
        this.mIconOptionWhenScreenOff =
                Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(),
                        "fingerprint_screen_off_icon_aod",
                        -1,
                        -2);
        this.mIconOptionWhenScreenOn =
                Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(),
                        "fingerprint_screen_on_icon_lock",
                        -1,
                        -2);
        this.mViEffectType =
                Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(), "fingerprint_effect", -1, -2);
        if (this.mIconOptionWhenScreenOff == -1) {
            int intDb = Utils.getIntDb(this.mContext, "fingerprint_adaptive_icon", true, -1);
            int intDb2 = Utils.getIntDb(this.mContext, "fingerprint_screen_off_icon", true, -1);
            if (intDb == 0 || intDb2 == 0) {
                this.mIconOptionWhenScreenOff = 0;
            } else if (intDb == 1 || intDb2 == 1) {
                this.mIconOptionWhenScreenOff = 1;
            } else {
                boolean z = Utils.Config.FEATURE_FACE_HAL;
                this.mIconOptionWhenScreenOff = 2;
            }
            Log.i(
                    "BSS_UdfpsIconOptionMonitor",
                    "updateValue: No DB, "
                            + intDb
                            + ", "
                            + intDb2
                            + ", "
                            + this.mIconOptionWhenScreenOff);
            Utils.putIntDb(
                    this.mContext,
                    "fingerprint_screen_off_icon_aod",
                    true,
                    this.mIconOptionWhenScreenOff);
        }
        if (this.mIconOptionWhenScreenOn == -1) {
            this.mIconOptionWhenScreenOn = 2;
        }
        Log.i(
                "BSS_UdfpsIconOptionMonitor",
                "updateValue: "
                        + this.mIconOptionWhenScreenOff
                        + ", "
                        + this.mIconOptionWhenScreenOn
                        + ", "
                        + this.mViEffectType);
    }
}
