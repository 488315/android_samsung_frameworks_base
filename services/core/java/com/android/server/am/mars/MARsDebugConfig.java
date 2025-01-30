package com.android.server.am.mars;

import android.os.SystemProperties;

/* loaded from: classes.dex */
public abstract class MARsDebugConfig {
  public static final boolean DEBUG_ALL;
  public static boolean DEBUG_DATABASE;
  public static boolean DEBUG_ENG;
  public static boolean DEBUG_FILTER;
  public static boolean DEBUG_FREECESS;
  public static boolean DEBUG_MARs;
  public static boolean DEBUG_NETLINK;
  public static boolean DEBUG_OLAF;
  public static boolean ENABLE_ALARM_WAKEUP_BLOCK;
  public static boolean DEBUG_MID =
      SystemProperties.get("ro.boot.debug_level", "").equals("0x494d");
  public static boolean DEBUG_HIGH =
      SystemProperties.get("ro.boot.debug_level", "").equals("0x4948");

  static {
    boolean z =
        (SystemProperties.get("ro.build.type", "user").equals("user")
                && SystemProperties.get("ro.boot.debug_level", "0x4f4c").equals("0x4f4c"))
            ? false
            : true;
    DEBUG_ENG = z;
    ENABLE_ALARM_WAKEUP_BLOCK = true;
    DEBUG_ALL = false;
    DEBUG_MARs = z;
    DEBUG_OLAF = false;
    DEBUG_FREECESS = false;
    DEBUG_DATABASE = false;
    DEBUG_FILTER = false;
    DEBUG_NETLINK = false;
  }
}
