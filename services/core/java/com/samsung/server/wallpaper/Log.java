package com.samsung.server.wallpaper;

import android.icu.util.Calendar;
import android.os.Debug;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

/* loaded from: classes2.dex */
public abstract class Log {
    public static String TAG_PREFIX = "WALLPAPER_SVC:";
    public static final boolean IS_DEV = Debug.semIsProductDev();
    public static int mLogLevel = 2;
    public static boolean mIsPrintCodeInfo = false;
    public static HashMap mLogHistory = new HashMap();
    public static SparseArray mStateLogMap = new SparseArray();
    public static int MAX_DUMP_SIZE = 200;

    public static String getCodeInfoString() {
        StringBuffer stringBuffer = new StringBuffer(" ");
        if (mIsPrintCodeInfo) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            int min = Math.min(stackTrace.length, 5);
            for (int i = 4; i <= min; i++) {
                stringBuffer.append(stackTrace[i].toString());
                stringBuffer.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            }
        }
        return stringBuffer.toString();
    }

    /* renamed from: v */
    public static int m133v(String str, String str2) {
        if (!IS_DEV || mLogLevel > 2) {
            return 0;
        }
        return Slog.v(TAG_PREFIX + str, str2 + getCodeInfoString());
    }

    /* renamed from: d */
    public static int m129d(String str, String str2) {
        if (mLogLevel > 3) {
            return 0;
        }
        return Slog.d(TAG_PREFIX + str, str2 + getCodeInfoString());
    }

    /* renamed from: i */
    public static int m132i(String str, String str2) {
        if (mLogLevel > 4) {
            return 0;
        }
        return Slog.i(TAG_PREFIX + str, str2 + getCodeInfoString());
    }

    /* renamed from: w */
    public static int m134w(String str, String str2) {
        if (mLogLevel > 5) {
            return 0;
        }
        return Slog.w(TAG_PREFIX + str, str2 + getCodeInfoString());
    }

    /* renamed from: w */
    public static int m135w(String str, String str2, Throwable th) {
        if (mLogLevel > 5) {
            return 0;
        }
        return Slog.w(TAG_PREFIX + str, str2 + getCodeInfoString(), th);
    }

    /* renamed from: e */
    public static int m130e(String str, String str2) {
        if (mLogLevel > 6) {
            return 0;
        }
        return Slog.e(TAG_PREFIX + str, str2 + getCodeInfoString());
    }

    /* renamed from: e */
    public static int m131e(String str, String str2, Throwable th) {
        if (mLogLevel > 6) {
            return 0;
        }
        return Slog.e(TAG_PREFIX + str, str2 + getCodeInfoString(), th);
    }

    public static ArrayList getHistoryList(String str) {
        ArrayList arrayList;
        synchronized (mLogHistory) {
            ArrayList arrayList2 = (ArrayList) mLogHistory.get(str);
            if (arrayList2 == null) {
                arrayList2 = new ArrayList();
                mLogHistory.put(str, arrayList2);
            }
            arrayList = new ArrayList(arrayList2);
        }
        return arrayList;
    }

    public static void addLogString(String str, String str2) {
        ArrayList historyList = getHistoryList(str);
        historyList.add(0, toTimestampFormat(str2));
        int size = historyList.size();
        if (size > MAX_DUMP_SIZE) {
            while (true) {
                size--;
                if (size < MAX_DUMP_SIZE) {
                    break;
                } else {
                    historyList.remove(size);
                }
            }
        }
        synchronized (mLogHistory) {
            mLogHistory.put(str, historyList);
        }
    }

    public static String toTimestampFormat(String str) {
        Calendar calendar = Calendar.getInstance();
        return String.format(Locale.US, "[%02d-%02d %02d:%02d:%02d.%03d] %s", Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14)), str);
    }

    public static void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("[Event history]");
        Iterator it = getHistoryList(str).iterator();
        while (it.hasNext()) {
            printWriter.println("    " + ((String) it.next()));
        }
        printWriter.println("[End of event history]");
        printWriter.println("[State log]");
        int size = mStateLogMap.size();
        for (int i = 0; i < size; i++) {
            printWriter.println("    " + mStateLogMap.keyAt(i) + " " + ((String) mStateLogMap.valueAt(i)));
        }
    }
}
