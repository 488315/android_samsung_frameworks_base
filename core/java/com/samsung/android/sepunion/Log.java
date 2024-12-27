package com.samsung.android.sepunion;

import android.icu.util.Calendar;
import android.util.SparseArray;
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
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    public static String TAG_PREFIX = "SEP_UNION_";
    private static int mLogLevel = 2;
    private static boolean mIsPrintCodeInfo = false;
    private static HashMap<String, ArrayList<String>> mLogHistory = new HashMap<>();
    private static SparseArray<String> mStateLogMap = new SparseArray<>();
    private static int MAX_DUMP_SIZE = 200;

    public static void setLoggableLevel(int logLevel) {
        mLogLevel = logLevel;
    }

    public static void setTagPrefix(String prefix) {
        if (prefix == null) {
            TAG_PREFIX = "";
        } else {
            TAG_PREFIX = prefix;
        }
    }

    public static void setMaxDumpSize(int size) {
        if (size >= 0) {
            MAX_DUMP_SIZE = size;
        }
    }

    public static void setPrintCodeInfo(boolean isNeedToShow) {
        mIsPrintCodeInfo = isNeedToShow;
    }

    private static String getCodeInfoString() {
        StringBuffer ret = new StringBuffer(" ");
        if (mIsPrintCodeInfo) {
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            int size = Math.min(stackTraceElements.length, 5);
            for (int i = 4; i <= size; i++) {
                ret.append(stackTraceElements[i].toString()).append("\n");
            }
        }
        return ret.toString();
    }

    public static int v(String tag, String msg) {
        return 0;
    }

    public static int v(String tag, String msg, Throwable tr) {
        return 0;
    }

    public static int d(String tag, String msg) {
        if (mLogLevel <= 3) {
            return android.util.Log.d(TAG_PREFIX + tag, msg + getCodeInfoString());
        }
        return 0;
    }

    public static int d(String tag, String msg, Throwable tr) {
        if (mLogLevel <= 3) {
            return android.util.Log.d(TAG_PREFIX + tag, msg + getCodeInfoString(), tr);
        }
        return 0;
    }

    public static int i(String tag, String msg) {
        if (mLogLevel <= 4) {
            return android.util.Log.i(TAG_PREFIX + tag, msg + getCodeInfoString());
        }
        return 0;
    }

    public static int i(String tag, String msg, Throwable tr) {
        if (mLogLevel <= 4) {
            return android.util.Log.i(TAG_PREFIX + tag, msg + getCodeInfoString(), tr);
        }
        return 0;
    }

    public static int w(String tag, String msg) {
        if (mLogLevel <= 5) {
            return android.util.Log.w(TAG_PREFIX + tag, msg + getCodeInfoString());
        }
        return 0;
    }

    public static int w(String tag, String msg, Throwable tr) {
        if (mLogLevel <= 5) {
            return android.util.Log.w(TAG_PREFIX + tag, msg + getCodeInfoString(), tr);
        }
        return 0;
    }

    public static int e(String tag, String msg) {
        if (mLogLevel <= 6) {
            return android.util.Log.e(TAG_PREFIX + tag, msg + getCodeInfoString());
        }
        return 0;
    }

    public static int e(String tag, String msg, Throwable tr) {
        if (mLogLevel <= 6) {
            return android.util.Log.e(TAG_PREFIX + tag, msg + getCodeInfoString(), tr);
        }
        return 0;
    }

    public static int wtf(String tag, String msg) {
        if (mLogLevel <= 7) {
            return android.util.Log.wtf(TAG_PREFIX + tag, msg + getCodeInfoString());
        }
        return 0;
    }

    public static int wtf(String tag, String msg, Throwable tr) {
        if (mLogLevel <= 7) {
            return android.util.Log.wtf(TAG_PREFIX + tag, msg + getCodeInfoString(), tr);
        }
        return 0;
    }

    private static ArrayList getHistoryList(String tag) {
        ArrayList arrayList;
        synchronized (mLogHistory) {
            ArrayList historyList = mLogHistory.get(tag);
            if (historyList == null) {
                historyList = new ArrayList();
                mLogHistory.put(tag, historyList);
            }
            arrayList = new ArrayList(historyList);
        }
        return arrayList;
    }

    public static void addLogString(String tag, String log) {
        ArrayList history = getHistoryList(tag);
        history.add(0, toTimestampFormat(log));
        int sizeOfHistory = history.size();
        if (sizeOfHistory > MAX_DUMP_SIZE) {
            for (int i = sizeOfHistory - 1; i >= MAX_DUMP_SIZE; i--) {
                history.remove(i);
            }
        }
        synchronized (mLogHistory) {
            mLogHistory.put(tag, history);
        }
    }

    public static void setStateDumpLog(int key, String log) {
        mStateLogMap.put(key, toTimestampFormat(log));
    }

    private static void trimLogHistory(String tag) {
        ArrayList history = getHistoryList(tag);
        int sizeOfHistory = history.size();
        if (sizeOfHistory > MAX_DUMP_SIZE) {
            for (int i = sizeOfHistory - 1; i >= MAX_DUMP_SIZE; i--) {
                history.remove(i);
            }
            synchronized (mLogHistory) {
                mLogHistory.put(tag, history);
            }
        }
    }

    private static String toTimestampFormat(String msg) {
        Calendar calendar = Calendar.getInstance();
        return String.format(Locale.US, "[%02d-%02d %02d:%02d:%02d.%03d] %s", Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14)), msg);
    }

    public static void dump(String tag, FileDescriptor fd, PrintWriter writer, String[] args) {
        writer.println("[Event history]");
        ArrayList<String> historyList = getHistoryList(tag);
        Iterator<String> it = historyList.iterator();
        while (it.hasNext()) {
            String log = it.next();
            writer.println("    " + log);
        }
        writer.println("[End of event history]");
        writer.println("[State log]");
        int count = mStateLogMap.size();
        for (int i = 0; i < count; i++) {
            int id = mStateLogMap.keyAt(i);
            String log2 = mStateLogMap.valueAt(i);
            writer.println("    " + id + " " + log2);
        }
    }
}
