package com.android.systemui.plugins;

import android.content.Context;
import android.view.View;

/* loaded from: classes2.dex */
public class PluginUtils {
    public static void setId(Context context, View view, String str) {
        view.setId(context.getResources().getIdentifier(str, "id", context.getPackageName()));
    }
}
