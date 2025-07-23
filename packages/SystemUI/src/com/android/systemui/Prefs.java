package com.android.systemui;

import android.content.Context;
import android.content.SharedPreferences;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Prefs {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    @Retention(RetentionPolicy.SOURCE)
    public @interface Key {
    }

    private Prefs() {
    }

    public static SharedPreferences get(Context context) {
        return context.getSharedPreferences(context.getPackageName(), 0);
    }

    public static boolean getBoolean(Context context, String str, boolean z) {
        return get(context).getBoolean(str, z);
    }

    public static int getInt(Context context, String str, int i) {
        return get(context).getInt(str, i);
    }

    public static void putBoolean(Context context, String str, boolean z) {
        get(context).edit().putBoolean(str, z).apply();
    }

    public static void putInt(Context context, String str, int i) {
        get(context).edit().putInt(str, i).apply();
    }

    public static void putString(Context context, String str, String str2) {
        get(context).edit().putString(str, str2).apply();
    }
}
