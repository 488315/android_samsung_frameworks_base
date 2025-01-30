package com.android.systemui.plugins;

import android.content.Context;
import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class PluginUtils {
    public static void setId(Context context, View view, String str) {
        view.setId(context.getResources().getIdentifier(str, "id", context.getPackageName()));
    }
}
