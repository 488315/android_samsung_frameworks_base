package com.samsung.server.wallpaper;

import android.icu.util.Calendar;
import android.os.Debug;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class Log {
    public static final boolean IS_DEV = Debug.semIsProductDev();
    public static final int mLogLevel = 2;
    public static final HashMap mLogHistory = new HashMap();
    public static final SparseArray mStateLogMap = new SparseArray();
    public static final int MAX_DUMP_SIZE = 200;

    public static void addLogString(String str, String str2) {
        ArrayList historyList = getHistoryList(str);
        Calendar calendar = Calendar.getInstance();
        historyList.add(0, String.format(Locale.US, "[%02d-%02d %02d:%02d:%02d.%03d] %s", Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14)), str2));
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
        HashMap hashMap = mLogHistory;
        synchronized (hashMap) {
            hashMap.put(str, historyList);
        }
    }

    public static void d(String str, String str2) {
        if (mLogLevel <= 3) {
            String concat = "WALLPAPER_SVC:".concat(str);
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str2);
            m.append(getCodeInfoString());
            Slog.d(concat, m.toString());
        }
    }

    public static void dump(String str, PrintWriter printWriter) {
        printWriter.println("[Event history]");
        Iterator it = getHistoryList(str).iterator();
        while (it.hasNext()) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(printWriter, "    ", (String) it.next());
        }
        printWriter.println("[End of event history]");
        printWriter.println("[State log]");
        int size = mStateLogMap.size();
        for (int i = 0; i < size; i++) {
            SparseArray sparseArray = mStateLogMap;
            printWriter.println("    " + sparseArray.keyAt(i) + " " + ((String) sparseArray.valueAt(i)));
        }
    }

    public static void e(String str, String str2) {
        if (mLogLevel <= 6) {
            String concat = "WALLPAPER_SVC:".concat(str);
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str2);
            m.append(getCodeInfoString());
            Slog.e(concat, m.toString());
        }
    }

    public static void e(String str, String str2, Throwable th) {
        if (mLogLevel <= 6) {
            String concat = "WALLPAPER_SVC:".concat(str);
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str2);
            m.append(getCodeInfoString());
            Slog.e(concat, m.toString(), th);
        }
    }

    public static String getCodeInfoString() {
        return new StringBuffer(" ").toString();
    }

    public static ArrayList getHistoryList(String str) {
        ArrayList arrayList;
        HashMap hashMap = mLogHistory;
        synchronized (hashMap) {
            try {
                ArrayList arrayList2 = (ArrayList) hashMap.get(str);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList();
                    hashMap.put(str, arrayList2);
                }
                arrayList = new ArrayList(arrayList2);
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public static void i(String str, String str2) {
        if (mLogLevel <= 4) {
            String concat = "WALLPAPER_SVC:".concat(str);
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str2);
            m.append(getCodeInfoString());
            Slog.i(concat, m.toString());
        }
    }

    public static void v(String str, String str2) {
        if (!IS_DEV || mLogLevel > 2) {
            return;
        }
        String concat = "WALLPAPER_SVC:".concat(str);
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str2);
        m.append(getCodeInfoString());
        Slog.v(concat, m.toString());
    }

    public static void w(String str, String str2) {
        if (mLogLevel <= 5) {
            String concat = "WALLPAPER_SVC:".concat(str);
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str2);
            m.append(getCodeInfoString());
            Slog.w(concat, m.toString());
        }
    }

    public static void w(String str, Throwable th) {
        if (mLogLevel <= 5) {
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str);
            m.append(getCodeInfoString());
            Slog.w("WALLPAPER_SVC:AssetFileManager", m.toString(), th);
        }
    }
}
