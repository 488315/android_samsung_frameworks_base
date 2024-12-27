package com.android.server.power;

import android.os.FileUtils;
import android.os.Process;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class Slog {
    public static final /* synthetic */ int $r8$clinit = 0;
    static ArrayList sLogList = new ArrayList(500);

    public abstract class TimeUtil {
        public static final DateTimeFormatter FORMATTER =
                DateTimeFormatter.ofPattern("MM-dd HH:mm:ss.SSS");
    }

    public static synchronized void addPMSLogList(String str) {
        synchronized (Slog.class) {
            if (sLogList.size() >= 500) {
                return;
            }
            ArrayList arrayList = sLogList;
            StringBuilder sb = new StringBuilder();
            DateTimeFormatter dateTimeFormatter = TimeUtil.FORMATTER;
            sb.append(LocalDateTime.now().format(TimeUtil.FORMATTER));
            sb.append("  ");
            sb.append(Process.myTid());
            sb.append(" ");
            sb.append(str);
            arrayList.add(sb.toString());
        }
    }

    public static int d(String str, String str2) {
        if (PowerManagerUtil.SEC_FEATURE_USE_PMS_LOG) {
            addPMSLogList("D " + str + ": " + str2);
        }
        return android.util.Slog.d(str, str2);
    }

    public static void dk(String str) {
        d("PowerManagerService", "!@" + str);
    }

    public static synchronized void dump(PrintWriter printWriter) {
        synchronized (Slog.class) {
            try {
                if (PowerManagerUtil.SEC_FEATURE_USE_PMS_LOG) {
                    printWriter.println(String.join("\n", sLogList));
                    printWriter.println("size : " + sLogList.size());
                } else {
                    printWriter.println("Pass savePMSLog");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void e(String str, String str2) {
        if (PowerManagerUtil.SEC_FEATURE_USE_PMS_LOG) {
            addPMSLogList("E " + str + ": " + str2);
        }
        android.util.Slog.e(str, str2);
    }

    public static void e(String str, String str2, Throwable th) {
        if (PowerManagerUtil.SEC_FEATURE_USE_PMS_LOG) {
            addPMSLogList("E " + str + ": " + str2);
        }
        android.util.Slog.e(str, str2, th);
    }

    public static int i(String str, String str2) {
        if (PowerManagerUtil.SEC_FEATURE_USE_PMS_LOG) {
            addPMSLogList("I " + str + ": " + str2);
        }
        return android.util.Slog.i(str, str2);
    }

    public static synchronized void saveLogAsFile() {
        synchronized (Slog.class) {
            if (!PowerManagerUtil.SEC_FEATURE_USE_PMS_LOG) {
                i("PMS_SLog", "Pass savePMSLog");
            } else if (sLogList.isEmpty()) {
                i("PMS_SLog", "Normal_log size is zero");
            } else {
                try {
                    FileUtils.stringToFile(
                            new File("/data/log/Last.kpsn"), String.join("\n", sLogList));
                } catch (IOException unused) {
                }
            }
        }
    }

    public static void v(String str, String str2) {
        if (PowerManagerUtil.SEC_FEATURE_USE_PMS_LOG) {
            addPMSLogList("V " + str + ": " + str2);
        }
        android.util.Slog.v(str, str2);
    }

    public static int w(String str, String str2) {
        if (PowerManagerUtil.SEC_FEATURE_USE_PMS_LOG) {
            addPMSLogList("W " + str + ": " + str2);
        }
        return android.util.Slog.w(str, str2);
    }

    public static void w(String str, Throwable th) {
        if (PowerManagerUtil.SEC_FEATURE_USE_PMS_LOG) {
            addPMSLogList("W DisplayManagerService: " + str);
        }
        android.util.Slog.w("DisplayManagerService", str, th);
    }

    public static void wk(String str, String str2) {
        w(str, "!@" + str2);
    }
}
