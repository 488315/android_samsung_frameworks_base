package com.android.server.enterprise.license;

import android.os.Bundle;
import com.samsung.android.knox.ContextInfo;

/* loaded from: classes2.dex */
public abstract class LicenseLog {
    public static void log(ContextInfo contextInfo, String str, boolean z, boolean z2) {
        LicenseLogService.log(contextInfo, str, z, z2);
    }

    public static Bundle getLog(String str) {
        return LicenseLogService.getLog(str);
    }

    public static boolean deleteLog(String str) {
        return LicenseLogService.deleteLog(str);
    }

    public static boolean deleteAllLog() {
        return LicenseLogService.deleteAllLog();
    }
}
