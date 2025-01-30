package com.google.android.material.internal;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SeslDisplayUtils {
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
