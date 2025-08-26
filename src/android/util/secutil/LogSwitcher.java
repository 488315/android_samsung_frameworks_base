package android.util.secutil;

import android.os.SystemProperties;

/* loaded from: classes4.dex */
public final class LogSwitcher {
    public static boolean isShowingGlobalLog = false;
    public static boolean isShowingSecDLog = false;
    public static boolean isShowingSecELog = false;
    public static boolean isShowingSecILog = false;
    public static boolean isShowingSecVLog = false;
    public static boolean isShowingSecWLog = false;
    public static boolean isShowingSecWtfLog = false;

    static {
        try {
            boolean zEquals = "1".equals(SystemProperties.get("persist.log.seclevel", "0"));
            isShowingGlobalLog = zEquals;
            isShowingSecVLog = zEquals;
            isShowingSecDLog = zEquals;
            isShowingSecILog = zEquals;
            isShowingSecWLog = zEquals;
            isShowingSecELog = zEquals;
            isShowingSecWtfLog = zEquals;
        } catch (Exception unused) {
        }
    }
}
