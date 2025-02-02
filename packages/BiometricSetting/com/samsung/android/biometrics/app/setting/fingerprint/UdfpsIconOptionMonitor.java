package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.database.ContentObserver;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.samsung.android.biometrics.app.setting.Utils;

/* loaded from: classes.dex */
public final class UdfpsIconOptionMonitor {

    @VisibleForTesting
    ContentObserver mContentObserver;
    private final Context mContext;
    private int mIconOptionWhenScreenOff;
    private int mViEffectType;

    public UdfpsIconOptionMonitor(Context context) {
        this.mContext = context;
    }

    public final boolean isEnabledOnAod() {
        return this.mIconOptionWhenScreenOff == 2;
    }

    public final boolean isEnabledTapToShow() {
        return this.mIconOptionWhenScreenOff == 1;
    }

    public final boolean isEnabledViEffect() {
        return this.mViEffectType != 0;
    }

    public final void start() {
        updateValue();
        if (this.mContentObserver != null) {
            return;
        }
        Context context = this.mContext;
        this.mContentObserver = new ContentObserver(context.getMainThreadHandler()) { // from class: com.samsung.android.biometrics.app.setting.fingerprint.UdfpsIconOptionMonitor.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                UdfpsIconOptionMonitor.this.updateValue();
            }
        };
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("fingerprint_screen_off_icon_aod"), false, this.mContentObserver, -2);
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("fingerprint_effect"), false, this.mContentObserver, -2);
    }

    public final void stop() {
        if (this.mContentObserver == null) {
            return;
        }
        this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
        this.mContentObserver = null;
    }

    public final void updateValue() {
        Context context = this.mContext;
        this.mIconOptionWhenScreenOff = Settings.Secure.getIntForUser(context.getContentResolver(), "fingerprint_screen_off_icon_aod", -1, -2);
        this.mViEffectType = Settings.Secure.getIntForUser(context.getContentResolver(), "fingerprint_effect", -1, -2);
        if (this.mIconOptionWhenScreenOff == -1) {
            int intDb = Utils.getIntDb(context, "fingerprint_adaptive_icon", true, -1);
            int intDb2 = Utils.getIntDb(context, "fingerprint_screen_off_icon", true, -1);
            if (intDb == 0 || intDb2 == 0) {
                this.mIconOptionWhenScreenOff = 0;
            } else if (intDb == 1 || intDb2 == 1) {
                this.mIconOptionWhenScreenOff = 1;
            } else if (Utils.Config.FP_FEATURE_FAKE_AOD) {
                this.mIconOptionWhenScreenOff = 1;
            } else {
                this.mIconOptionWhenScreenOff = 2;
            }
            Log.i("BSS_UdfpsIconOptionMonitor", "updateValue: No DB, " + intDb + ", " + intDb2 + ", " + this.mIconOptionWhenScreenOff);
            Utils.putIntDb(context, "fingerprint_screen_off_icon_aod", true, this.mIconOptionWhenScreenOff);
        }
        Log.i("BSS_UdfpsIconOptionMonitor", "updateValue: " + this.mIconOptionWhenScreenOff + ", " + this.mViEffectType);
    }
}
