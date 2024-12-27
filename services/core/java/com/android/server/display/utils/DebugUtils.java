package com.android.server.display.utils;

import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class DebugUtils {
    public static final boolean DEBUG_ALL = Log.isLoggable("DisplayManager_All", 3);

    public static boolean isDebuggable(String str) {
        return Log.isLoggable(str, 3) || DEBUG_ALL;
    }
}
