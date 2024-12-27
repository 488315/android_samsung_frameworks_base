package android.util.secutil;

import android.os.SystemProperties;

/* loaded from: classes4.dex */
public final class LogSwitcher {
    public static boolean isShowingGlobalLog;
    public static boolean isShowingSecDLog;
    public static boolean isShowingSecELog;
    public static boolean isShowingSecILog;
    public static boolean isShowingSecVLog;
    public static boolean isShowingSecWLog;
    public static boolean isShowingSecWtfLog;

    static {
        isShowingGlobalLog = false;
        isShowingSecVLog = false;
        isShowingSecDLog = false;
        isShowingSecILog = false;
        isShowingSecWLog = false;
        isShowingSecELog = false;
        isShowingSecWtfLog = false;
        try {
            isShowingGlobalLog = "1".equals(SystemProperties.get("persist.log.seclevel", "0"));
            isShowingSecVLog = isShowingGlobalLog;
            isShowingSecDLog = isShowingGlobalLog;
            isShowingSecILog = isShowingGlobalLog;
            isShowingSecWLog = isShowingGlobalLog;
            isShowingSecELog = isShowingGlobalLog;
            isShowingSecWtfLog = isShowingGlobalLog;
        } catch (Exception e) {
        }
    }
}
