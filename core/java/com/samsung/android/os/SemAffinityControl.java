package com.samsung.android.os;

import android.hardware.gnss.GnssSignalType;
import android.os.Build;
import android.os.SystemProperties;
import android.util.Slog;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/* loaded from: classes6.dex */
public class SemAffinityControl {
    private static final int HMP_CORE_FRONT = 0;
    private static final int HMP_CORE_REAR = 1;
    private static final String TAG = "SemAffinityControl";
    private int core_num;
    public static final boolean DEBUG = !"user".equals(Build.TYPE);
    private static final String HMP_PROPERTY = SystemProperties.get("sys.perf.hmp", "4:4");
    private static String[] strHmpCore = null;
    private static int[] nLittle = null;
    private static int[] nBig = null;
    private static int littleIndex = -1;
    private static int bigIndex = -1;

    private native int native_set_affinity(int i, int[] iArr);

    public SemAffinityControl() {
        this.core_num = -1;
        logOnEng(TAG, "[Java Side], SemAffinityControl Class Initialized");
        if (HMP_PROPERTY != null && HMP_PROPERTY.length() > 0) {
            initializeHmpCore();
            this.core_num =
                    (Integer.parseInt(strHmpCore[littleIndex])
                                    + Integer.parseInt(strHmpCore[bigIndex]))
                            - 1;
            logOnEng(
                    TAG,
                    "[Java Side], SemAffinityControl Class Initialized core_num : "
                            + this.core_num);
            int offsetLittle = 0;
            int offsetBig = nLittle.length;
            if (littleIndex == 1) {
                offsetLittle = nBig.length;
                offsetBig = 0;
            }
            for (int i = 0; i < nLittle.length; i++) {
                nLittle[i] = i + offsetLittle;
            }
            for (int j = 0; j < nBig.length; j++) {
                nBig[j] = j + offsetBig;
            }
        }
    }

    private static void initializeHmpCore() {
        strHmpCore = HMP_PROPERTY.split(":");
        if (strHmpCore.length > 2 && GnssSignalType.CODE_TYPE_B.equals(strHmpCore[2])) {
            littleIndex = 1;
            bigIndex = 0;
        } else {
            littleIndex = 0;
            bigIndex = 1;
        }
        nLittle = new int[Integer.parseInt(strHmpCore[littleIndex])];
        nBig = new int[Integer.parseInt(strHmpCore[bigIndex])];
    }

    public int setAffinity(int pid, int... cpu_list) {
        if (native_set_affinity(pid, cpu_list) == 1) {
            logOnEng(TAG, "sched_set_affinity_failed");
            return 1;
        }
        logOnEng(TAG, "sched_set_affinity_success");
        return 0;
    }

    public int setAffinityForLittle(int pid) {
        if (HMP_PROPERTY == null || HMP_PROPERTY.length() <= 0) {
            return 1;
        }
        if (native_set_affinity(pid, nLittle) == 1) {
            logOnEng(TAG, "sched_set_affinity_failed");
            return 1;
        }
        logOnEng(TAG, "sched_set_affinity_success");
        return 0;
    }

    public int setAffinityForBig(int pid) {
        if (HMP_PROPERTY == null || HMP_PROPERTY.length() <= 0) {
            return 1;
        }
        if (native_set_affinity(pid, nBig) == 1) {
            logOnEng(TAG, "sched_set_affinity_failed");
            return 1;
        }
        logOnEng(TAG, "sched_set_affinity_success");
        return 0;
    }

    public int clearAffinity(int pid) {
        if (this.core_num < 0) {
            int numCore = -1;
            String[] splitHmp = SystemProperties.get("sys.perf.hmp", "4:4").split(":");
            if (splitHmp.length >= 2) {
                numCore = Integer.parseInt(splitHmp[0]) + Integer.parseInt(splitHmp[1]);
            }
            if (numCore >= 0) {
                this.core_num = numCore;
                logOnEng(
                        TAG,
                        "[Java Side], clearAffinity numCore : "
                                + numCore
                                + ", core_num : "
                                + this.core_num);
            } else {
                logOnEng(TAG, "clear_affinity_failed. It can't read the num of core");
                return -1;
            }
        }
        int numCore2 = this.core_num;
        if (numCore2 > 0) {
            int[] input_cpu_list = new int[this.core_num + 1];
            for (int i = 0; i <= this.core_num; i++) {
                input_cpu_list[i] = i;
            }
            int i2 = native_set_affinity(pid, input_cpu_list);
            if (i2 == 1) {
                logOnEng(TAG, "clear_affinity_failed");
                return 1;
            }
            logOnEng(TAG, "clear_affinity_success");
            return 0;
        }
        logOnEng(TAG, "clear_affinity_failed");
        return 1;
    }

    public static void logOnEng(String tag, String msg) {
        if (DEBUG) {
            Slog.d(tag, msg);
        }
    }

    public static String readSysfs(String TAG2, String path) {
        StringBuilder sb;
        String strTemp = null;
        BufferedReader buf = null;
        try {
            try {
                buf = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
                strTemp = buf.readLine();
                logOnEng(TAG2, "readSysfs:: path = " + path + ", strTemp result = " + strTemp);
            } catch (IOException e) {
                logOnEng(TAG2, "e = " + e.getMessage());
                if (buf != null) {
                    try {
                        buf.close();
                    } catch (IOException e2) {
                        e = e2;
                        sb = new StringBuilder();
                        logOnEng(TAG2, sb.append("e = ").append(e.getMessage()).toString());
                        return strTemp;
                    }
                }
            }
            try {
                buf.close();
            } catch (IOException e3) {
                e = e3;
                sb = new StringBuilder();
                logOnEng(TAG2, sb.append("e = ").append(e.getMessage()).toString());
                return strTemp;
            }
            return strTemp;
        } catch (Throwable th) {
            if (buf != null) {
                try {
                    buf.close();
                } catch (IOException e4) {
                    logOnEng(TAG2, "e = " + e4.getMessage());
                }
            }
            throw th;
        }
    }
}
