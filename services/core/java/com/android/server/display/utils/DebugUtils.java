package com.android.server.display.utils;

import android.util.Log;

public abstract class DebugUtils {
    public static final boolean DEBUG_ALL = Log.isLoggable("DisplayManager_All", 3);

    public static boolean isDebuggable(String str) {
        return Log.isLoggable(str, 3) || DEBUG_ALL;
    }
}
