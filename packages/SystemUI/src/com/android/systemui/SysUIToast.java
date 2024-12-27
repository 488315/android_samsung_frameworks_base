package com.android.systemui;

import android.content.Context;
import android.widget.Toast;

public final class SysUIToast {
    public static Toast makeText(Context context, int i, int i2) {
        return Toast.makeText(context, context.getString(i), i2);
    }
}
