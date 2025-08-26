package com.samsung.android.sepunion;

import android.icu.util.Calendar;
import android.util.SparseArray;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

/* loaded from: classes6.dex */
public class Log {
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final boolean IS_DEV = false;
    public static String TAG_PREFIX = "SEP_UNION_";
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    private static boolean mIsPrintCodeInfo = false;
    private static int mLogLevel = 2;
    private static HashMap<String, ArrayList<String>> mLogHistory = new HashMap<>();
    private static SparseArray<String> mStateLogMap = new SparseArray<>();
    private static int MAX_DUMP_SIZE = 200;

    public static int v(String str, String str2) {
        return 0;
    }

    public static int v(String str, String str2, Throwable th) {
        return 0;
    }

    public static void setLoggableLevel(int i) {
        mLogLevel = i;
    }

    public static void setTagPrefix(String str) {
        if (str == null) {
            TAG_PREFIX = "";
        } else {
            TAG_PREFIX = str;
        }
    }

    public static void setMaxDumpSize(int i) {
        if (i >= 0) {
            MAX_DUMP_SIZE = i;
        }
    }

    public static void setPrintCodeInfo(boolean z) {
        mIsPrintCodeInfo = z;
    }

    private static String getCodeInfoString() {
        StringBuffer stringBuffer = new StringBuffer(" ");
        if (mIsPrintCodeInfo) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            int iMin = Math.min(stackTrace.length, 5);
            for (int i = 4; i <= iMin; i++) {
                stringBuffer.append(stackTrace[i].toString()).append(ShaderAssembler.NEWLINE);
            }
        }
        return stringBuffer.toString();
    }

    public static int d(String str, String str2) {
        if (mLogLevel > 3) {
            return 0;
        }
        return android.util.Log.d(TAG_PREFIX + str, str2 + getCodeInfoString());
    }

    public static int d(String str, String str2, Throwable th) {
        if (mLogLevel > 3) {
            return 0;
        }
        return android.util.Log.d(TAG_PREFIX + str, str2 + getCodeInfoString(), th);
    }

    public static int i(String str, String str2) {
        if (mLogLevel > 4) {
            return 0;
        }
        return android.util.Log.i(TAG_PREFIX + str, str2 + getCodeInfoString());
    }

    public static int i(String str, String str2, Throwable th) {
        if (mLogLevel > 4) {
            return 0;
        }
        return android.util.Log.i(TAG_PREFIX + str, str2 + getCodeInfoString(), th);
    }

    public static int w(String str, String str2) {
        if (mLogLevel > 5) {
            return 0;
        }
        return android.util.Log.w(TAG_PREFIX + str, str2 + getCodeInfoString());
    }

    public static int w(String str, String str2, Throwable th) {
        if (mLogLevel > 5) {
            return 0;
        }
        return android.util.Log.w(TAG_PREFIX + str, str2 + getCodeInfoString(), th);
    }

    public static int e(String str, String str2) {
        if (mLogLevel > 6) {
            return 0;
        }
        return android.util.Log.e(TAG_PREFIX + str, str2 + getCodeInfoString());
    }

    public static int e(String str, String str2, Throwable th) {
        if (mLogLevel > 6) {
            return 0;
        }
        return android.util.Log.e(TAG_PREFIX + str, str2 + getCodeInfoString(), th);
    }

    public static int wtf(String str, String str2) {
        if (mLogLevel > 7) {
            return 0;
        }
        return android.util.Log.wtf(TAG_PREFIX + str, str2 + getCodeInfoString());
    }

    public static int wtf(String str, String str2, Throwable th) {
        if (mLogLevel > 7) {
            return 0;
        }
        return android.util.Log.wtf(TAG_PREFIX + str, str2 + getCodeInfoString(), th);
    }

    private static ArrayList getHistoryList(String str) {
        ArrayList arrayList;
        synchronized (mLogHistory) {
            ArrayList<String> arrayList2 = mLogHistory.get(str);
            if (arrayList2 == null) {
                arrayList2 = new ArrayList<>();
                mLogHistory.put(str, arrayList2);
            }
            arrayList = new ArrayList(arrayList2);
        }
        return arrayList;
    }

    public static void addLogString(String str, String str2) {
        ArrayList<String> historyList = getHistoryList(str);
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

    public static void setStateDumpLog(int i, String str) {
        mStateLogMap.put(i, toTimestampFormat(str));
    }

    private static void trimLogHistory(String str) {
        ArrayList<String> historyList = getHistoryList(str);
        int size = historyList.size();
        if (size <= MAX_DUMP_SIZE) {
            return;
        }
        while (true) {
            size--;
            if (size >= MAX_DUMP_SIZE) {
                historyList.remove(size);
            } else {
                synchronized (mLogHistory) {
                    mLogHistory.put(str, historyList);
                }
                return;
            }
        }
    }

    private static String toTimestampFormat(String str) {
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
            printWriter.println("    " + mStateLogMap.keyAt(i) + " " + mStateLogMap.valueAt(i));
        }
    }
}
