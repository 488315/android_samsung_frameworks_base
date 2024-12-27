package com.android.server.baiducarlife;

import android.os.SystemProperties;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BaiduCarlifeADBConnectUtils {
    public static boolean isCarlifeForceConnect() {
        return "true".equals(SystemProperties.get("persist.sys.adb.config.carlife_force"));
    }
}
