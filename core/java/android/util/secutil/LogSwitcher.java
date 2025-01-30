package android.util.secutil;

import android.p009os.SystemProperties;

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
            boolean equals = "1".equals(SystemProperties.get("persist.log.seclevel", "0"));
            isShowingGlobalLog = equals;
            isShowingSecVLog = equals;
            isShowingSecDLog = equals;
            isShowingSecILog = equals;
            isShowingSecWLog = equals;
            isShowingSecELog = equals;
            isShowingSecWtfLog = equals;
        } catch (Exception e) {
        }
    }
}
