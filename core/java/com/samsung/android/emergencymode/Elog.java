package com.samsung.android.emergencymode;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;

public final class Elog {
    private static final boolean DEBUG = true;
    private static final String M_TAG = "EmergencyMode";

    public static void d(String moduleTag, String log) {
        Log.d(M_TAG, NavigationBarInflaterView.SIZE_MOD_START + moduleTag + "] " + log);
    }

    public static void v(String moduleTag, String log) {
        Log.v(M_TAG, NavigationBarInflaterView.SIZE_MOD_START + moduleTag + "] " + log);
    }
}
