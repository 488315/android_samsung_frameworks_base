package com.android.server.bgslotmanager;

import android.os.Process;
import android.os.SystemProperties;
import com.android.server.am.DynamicHiddenApp;
import com.android.server.am.ProcessList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BgAppPropManager {
    public DynamicHiddenApp mDynamicHiddenApp;
    public ProcessList mProcessList;
    public static final long mTotalMemMb = Process.getTotalMemory() / 1048576;
    public static int TOTAL_MEMORY_2ND = Integer.parseInt(SystemProperties.get("ro.slmk.dha_2ndprop_thMB", "4096"));
    public static final int TOTAL_MEMORY_3RD = Integer.parseInt(SystemProperties.get("ro.slmk.3rd.over_thMB", "9999999"));

    public static boolean getSlmkPropertyBool(String str, String str2) {
        return Boolean.parseBoolean(getSlmkPropertyString(str, str2));
    }

    public static int getSlmkPropertyInt(String str, String str2) {
        return Integer.parseInt(getSlmkPropertyString(str, str2));
    }

    public static String getSlmkPropertyString(String str, String str2) {
        String str3 = SystemProperties.get("ro.slmk.".concat(str), str2);
        long j = TOTAL_MEMORY_2ND;
        long j2 = mTotalMemMb;
        if (j2 > j) {
            str3 = SystemProperties.get("ro.slmk.2nd.".concat(str), str3);
        }
        return j2 > ((long) TOTAL_MEMORY_3RD) ? SystemProperties.get("ro.slmk.3rd.".concat(str), str3) : str3;
    }
}
