package com.samsung.systemui.splugins;

import android.content.Context;
import android.view.View;

/* loaded from: classes4.dex */
public class SPluginUtils {
    public static void setId(Context context, View view, String str) {
        view.setId(context.getResources().getIdentifier(str, "id", context.getPackageName()));
    }
}
