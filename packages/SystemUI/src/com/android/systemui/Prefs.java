package com.android.systemui;

import android.content.Context;
import android.content.SharedPreferences;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class Prefs {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
