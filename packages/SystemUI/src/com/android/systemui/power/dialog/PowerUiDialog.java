package com.android.systemui.power.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AlertDialog;
import com.android.systemui.power.SecBatterySnapshot;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class PowerUiDialog {
    public final Context mContext;
    public String mDoNotShowTag;
    public SharedPreferences mSharedPref;

    public PowerUiDialog(Context context) {
        this.mContext = context;
    }

    public abstract boolean checkCondition();

    public abstract AlertDialog getDialog();

    public abstract void setInformation(SecBatterySnapshot secBatterySnapshot);
}
