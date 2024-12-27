package com.android.systemui.power.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AlertDialog;
import com.android.systemui.power.SecBatterySnapshot;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
