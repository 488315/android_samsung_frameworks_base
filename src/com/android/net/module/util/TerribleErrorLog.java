package com.android.net.module.util;

import android.util.Log;
import java.util.function.BiConsumer;

/* loaded from: classes6.dex */
public class TerribleErrorLog {
    private static final String TAG = "TerribleErrorLog";

    public static void logTerribleError(BiConsumer<Integer, Integer> biConsumer, String str, int i, int i2) {
        biConsumer.accept(Integer.valueOf(i), Integer.valueOf(i2));
        Log.wtf(TAG, str);
    }
}
