package com.android.systemui;

import android.content.Context;
import android.widget.Toast;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SysUIToast {
    public static Toast makeText(int i, Context context, int i2) {
        return Toast.makeText(context, context.getString(i), i2);
    }
}
