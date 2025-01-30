package com.android.systemui.power.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemProperties;
import androidx.appcompat.app.AlertDialog;
import com.android.systemui.power.SecBatteryStatsSnapshot;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class PowerUiDialog {
    public final Context mContext;
    public String mDoNotShowTag;
    public final boolean mIsFactoryBinary;
    public SharedPreferences mSharedPref;

    public PowerUiDialog(Context context) {
        this.mIsFactoryBinary = false;
        this.mContext = context;
        if ("factory".equalsIgnoreCase(SystemProperties.get("ro.factory.factory_binary", "Unknown"))) {
            this.mIsFactoryBinary = true;
        }
    }

    public abstract boolean checkCondition();

    public abstract AlertDialog getDialog();

    public abstract void setInformation(SecBatteryStatsSnapshot secBatteryStatsSnapshot);
}
