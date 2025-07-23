package com.google.android.material.internal;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SeslDisplayUtils {
    public static int getPinnedEdgeWidth(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(), "pinned_edge_width");
        } catch (Settings.SettingNotFoundException e) {
            Log.w("SeslDisplayUtils", "Failed get EdgeWidth " + e.toString());
            return 0;
        }
    }

    public static boolean isPinEdgeEnabled(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(), "panel_mode", 0) == 1;
        } catch (Exception e) {
            Log.w("SeslDisplayUtils", "Failed get panel mode " + e.toString());
            return false;
        }
    }
}
