package com.android.server.ibs.sleepmode;

import android.content.Context;
import android.content.SharedPreferences;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class SharePrefUtils {
    public static boolean getBoolean(Context context, String str) {
        try {
            return context.getSharedPreferences("sleep_mode_pref", 0).getBoolean(str, false);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static long getLong(Context context, String str, long j) {
        try {
            return context.getSharedPreferences("sleep_mode_pref", 0).getLong(str, j);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return j;
        }
    }

    public static void putBoolean(Context context, String str, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences("sleep_mode_pref", 0).edit();
        edit.putBoolean(str, z);
        edit.apply();
    }

    public static void putInt(Context context, String str, int i) {
        SharedPreferences.Editor edit = context.getSharedPreferences("sleep_mode_pref", 0).edit();
        edit.putInt(str, i);
        edit.apply();
    }

    public static void putLong(Context context, String str, long j) {
        SharedPreferences.Editor edit = context.getSharedPreferences("sleep_mode_pref", 0).edit();
        edit.putLong(str, j);
        edit.apply();
    }
}
