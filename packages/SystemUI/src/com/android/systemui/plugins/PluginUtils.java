package com.android.systemui.plugins;

import android.content.Context;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class PluginUtils {
    public static void setId(Context context, View view, String str) {
        view.setId(context.getResources().getIdentifier(str, "id", context.getPackageName()));
    }
}
