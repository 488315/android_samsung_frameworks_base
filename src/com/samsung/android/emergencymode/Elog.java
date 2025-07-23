package com.samsung.android.emergencymode;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;

/* loaded from: classes6.dex */
public final class Elog {
    private static final boolean DEBUG = true;
    private static final String M_TAG = "EmergencyMode";

    public static void d(String str, String str2) {
        Log.d(M_TAG, NavigationBarInflaterView.SIZE_MOD_START + str + "] " + str2);
    }

    public static void v(String str, String str2) {
        Log.v(M_TAG, NavigationBarInflaterView.SIZE_MOD_START + str + "] " + str2);
    }
}
