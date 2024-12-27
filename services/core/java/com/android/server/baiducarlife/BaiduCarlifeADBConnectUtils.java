package com.android.server.baiducarlife;

import android.os.SystemProperties;

public abstract class BaiduCarlifeADBConnectUtils {
    public static boolean isCarlifeForceConnect() {
        return "true".equals(SystemProperties.get("persist.sys.adb.config.carlife_force"));
    }
}
