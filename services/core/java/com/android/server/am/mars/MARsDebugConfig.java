package com.android.server.am.mars;

import android.os.SystemProperties;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class MARsDebugConfig {
    public static boolean DEBUG_DATABASE;
    public static final boolean DEBUG_ENG;
    public static boolean DEBUG_FILTER;
    public static boolean DEBUG_FREECESS;
    public static boolean DEBUG_MARs;
    public static boolean DEBUG_NETLINK;
    public static boolean DEBUG_OLAF;
    public static final boolean ENABLE_ALARM_WAKEUP_BLOCK;
    public static final boolean DEBUG_MID =
            SystemProperties.get("ro.boot.debug_level", "").equals("0x494d");
    public static final boolean DEBUG_HIGH =
            SystemProperties.get("ro.boot.debug_level", "").equals("0x4948");

    static {
        boolean z =
                (SystemProperties.get("ro.build.type", "user").equals("user")
                                && SystemProperties.get("ro.boot.debug_level", "0x4f4c")
                                        .equals("0x4f4c"))
                        ? false
                        : true;
        DEBUG_ENG = z;
        ENABLE_ALARM_WAKEUP_BLOCK = true;
        DEBUG_MARs = z;
        DEBUG_OLAF = false;
        DEBUG_FREECESS = false;
        DEBUG_DATABASE = false;
        DEBUG_FILTER = false;
        DEBUG_NETLINK = false;
    }
}
