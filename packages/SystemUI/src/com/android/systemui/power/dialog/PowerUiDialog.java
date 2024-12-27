package com.android.systemui.power.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AlertDialog;
import com.android.systemui.power.SecBatterySnapshot;

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
