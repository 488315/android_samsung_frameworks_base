package com.android.systemui;

import android.content.Context;
import android.widget.Toast;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SysUIToast {
    public static Toast makeText(Context context, int i, int i2) {
        return Toast.makeText(context, context.getString(i), i2);
    }
}
