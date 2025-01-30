package com.android.server.notification.edgelighting;

import android.os.Debug;
import android.util.Slog;
import com.samsung.android.feature.SemFloatingFeature;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

/* loaded from: classes2.dex */
public class EdgeLightingHistory {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final boolean IS_DEV_DEBUG;
    public static EdgeLightingHistory mInstance;
    public final Object mLock = new Object();
    public ArrayList mHostHistory = new ArrayList();
    public ArrayList mListenerHistory = new ArrayList();
    public ArrayList mEdgeLightingHistory = new ArrayList();
    public ArrayList mEventHistory = new ArrayList();
    public ArrayList mRejectHistory = new ArrayList();

    static {
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EDGE");
        IS_DEV_DEBUG = string != null && string.contains("debug");
    }

    public static synchronized EdgeLightingHistory getInstance() {
        EdgeLightingHistory edgeLightingHistory;
        synchronized (EdgeLightingHistory.class) {
            edgeLightingHistory = mInstance;
            if (edgeLightingHistory == null) {
                edgeLightingHistory = new EdgeLightingHistory();
                mInstance = edgeLightingHistory;
            }
        }
        return edgeLightingHistory;
    }

    public void updateHostHistory(String str, String str2) {
        synchronized (this.mLock) {
            this.mHostHistory.add(str + ':' + toTimestampFormat(str2));
            while (this.mHostHistory.size() > 20) {
                this.mHostHistory.remove(0);
            }
        }
    }

    public void updateListenerHistory(String str, String str2) {
        synchronized (this.mLock) {
            this.mListenerHistory.add(str + ':' + toTimestampFormat(str2));
            while (this.mListenerHistory.size() > 20) {
                this.mListenerHistory.remove(0);
            }
        }
    }

    public void updateEdgeLightingHistory(String str) {
        if (IS_DEV_DEBUG || DEBUG) {
            if (DEBUG) {
                Slog.d("EdgeLightingHistory", "updateEdgeLightingHistory log = " + str);
            }
            String timestampFormat = toTimestampFormat(str);
            synchronized (this.mLock) {
                this.mEdgeLightingHistory.add(timestampFormat);
                while (this.mEdgeLightingHistory.size() > 30) {
                    this.mEdgeLightingHistory.remove(0);
                }
            }
        }
    }

    public void updateEventHistory(String str) {
        if (IS_DEV_DEBUG || DEBUG) {
            if (DEBUG) {
                Slog.d("EdgeLightingHistory", "updateEventHistory log = " + str);
            }
            String timestampFormat = toTimestampFormat(str);
            synchronized (this.mLock) {
                this.mEventHistory.add(timestampFormat);
                while (this.mEventHistory.size() > 20) {
                    this.mEventHistory.remove(0);
                }
            }
        }
    }

    public void updateRejectHistory(String str) {
        if (IS_DEV_DEBUG || DEBUG) {
            if (DEBUG) {
                Slog.d("EdgeLightingHistory", "updateRejectHistory log = " + str);
            }
            String timestampFormat = toTimestampFormat(str);
            synchronized (this.mLock) {
                this.mRejectHistory.add(timestampFormat);
                while (this.mRejectHistory.size() > 30) {
                    this.mRejectHistory.remove(0);
                }
            }
        }
    }

    public final String toTimestampFormat(String str) {
        Calendar calendar = Calendar.getInstance();
        return String.format(Locale.US, "[%02d-%02d %02d:%02d:%02d.%03d] %s", Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14)), str);
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        synchronized (this.mLock) {
            printWriter.println("-EdgeLightingHistory");
            printWriter.println("  [Host History] :");
            Iterator it = this.mHostHistory.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (str != null) {
                    printWriter.println("   " + str);
                }
            }
            printWriter.println("");
            printWriter.println("  [Listener History] :");
            Iterator it2 = this.mListenerHistory.iterator();
            while (it2.hasNext()) {
                String str2 = (String) it2.next();
                if (str2 != null) {
                    printWriter.println("   " + str2);
                }
            }
            if (IS_DEV_DEBUG || DEBUG) {
                printWriter.println("");
                printWriter.println("  [EL History] :");
                Iterator it3 = this.mEdgeLightingHistory.iterator();
                while (it3.hasNext()) {
                    String str3 = (String) it3.next();
                    if (str3 != null) {
                        printWriter.println("   " + str3);
                    }
                }
                printWriter.println("");
                printWriter.println("  [Event History] :");
                Iterator it4 = this.mEventHistory.iterator();
                while (it4.hasNext()) {
                    String str4 = (String) it4.next();
                    if (str4 != null) {
                        printWriter.println("   " + str4);
                    }
                }
                printWriter.println("");
                printWriter.println("  [Reject History] :");
                Iterator it5 = this.mRejectHistory.iterator();
                while (it5.hasNext()) {
                    String str5 = (String) it5.next();
                    if (str5 != null) {
                        printWriter.println("   " + str5);
                    }
                }
            }
        }
    }
}
