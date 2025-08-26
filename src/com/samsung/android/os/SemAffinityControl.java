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
        int i;
        int length;
        this.core_num = -1;
        logOnEng(TAG, "[Java Side], SemAffinityControl Class Initialized");
        String str = HMP_PROPERTY;
        if (str == null || str.length() <= 0) {
            return;
        }
        initializeHmpCore();
        this.core_num = (Integer.parseInt(strHmpCore[littleIndex]) + Integer.parseInt(strHmpCore[bigIndex])) - 1;
        logOnEng(TAG, "[Java Side], SemAffinityControl Class Initialized core_num : " + this.core_num);
        int length2 = nLittle.length;
        int i2 = 0;
        if (littleIndex == 1) {
            length = nBig.length;
            i = 0;
        } else {
            i = length2;
            length = 0;
        }
        int i3 = 0;
        while (true) {
            int[] iArr = nLittle;
            if (i3 >= iArr.length) {
                break;
            }
            iArr[i3] = i3 + length;
            i3++;
        }
        while (true) {
            int[] iArr2 = nBig;
            if (i2 >= iArr2.length) {
                return;
            }
            iArr2[i2] = i2 + i;
            i2++;
        }
    }

    private static void initializeHmpCore() {
        String[] strArrSplit = HMP_PROPERTY.split(":");
        strHmpCore = strArrSplit;
        if (strArrSplit.length > 2 && GnssSignalType.CODE_TYPE_B.equals(strArrSplit[2])) {
            littleIndex = 1;
            bigIndex = 0;
        } else {
            littleIndex = 0;
            bigIndex = 1;
        }
        nLittle = new int[Integer.parseInt(strHmpCore[littleIndex])];
        nBig = new int[Integer.parseInt(strHmpCore[bigIndex])];
    }

    public int setAffinity(int i, int... iArr) {
        if (native_set_affinity(i, iArr) == 1) {
            logOnEng(TAG, "sched_set_affinity_failed");
            return 1;
        }
        logOnEng(TAG, "sched_set_affinity_success");
        return 0;
    }

    public int setAffinityForLittle(int i) {
        String str = HMP_PROPERTY;
        if (str == null || str.length() <= 0) {
            return 1;
        }
        if (native_set_affinity(i, nLittle) == 1) {
            logOnEng(TAG, "sched_set_affinity_failed");
            return 1;
        }
        logOnEng(TAG, "sched_set_affinity_success");
        return 0;
    }

    public int setAffinityForBig(int i) {
        String str = HMP_PROPERTY;
        if (str == null || str.length() <= 0) {
            return 1;
        }
        if (native_set_affinity(i, nBig) == 1) {
            logOnEng(TAG, "sched_set_affinity_failed");
            return 1;
        }
        logOnEng(TAG, "sched_set_affinity_success");
        return 0;
    }

    public int clearAffinity(int i) {
        if (this.core_num < 0) {
            String[] strArrSplit = SystemProperties.get("sys.perf.hmp", "4:4").split(":");
            int i2 = strArrSplit.length >= 2 ? Integer.parseInt(strArrSplit[0]) + Integer.parseInt(strArrSplit[1]) : -1;
            if (i2 >= 0) {
                this.core_num = i2;
                logOnEng(TAG, "[Java Side], clearAffinity numCore : " + i2 + ", core_num : " + this.core_num);
            } else {
                logOnEng(TAG, "clear_affinity_failed. It can't read the num of core");
                return -1;
            }
        }
        int i3 = this.core_num;
        if (i3 > 0) {
            int[] iArr = new int[i3 + 1];
            for (int i4 = 0; i4 <= this.core_num; i4++) {
                iArr[i4] = i4;
            }
            if (native_set_affinity(i, iArr) == 1) {
                logOnEng(TAG, "clear_affinity_failed");
                return 1;
            }
            logOnEng(TAG, "clear_affinity_success");
            return 0;
        }
        logOnEng(TAG, "clear_affinity_failed");
        return 1;
    }

    public static void logOnEng(String str, String str2) {
        if (DEBUG) {
            Slog.d(str, str2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v8 */
    public static String readSysfs(String str, String str2) throws Throwable {
        String str3;
        BufferedReader bufferedReader;
        ?? r2 = 0;
        String line = null;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(str2), "UTF-8"));
            } catch (Throwable th) {
                th = th;
            }
        } catch (IOException e) {
            e = e;
            str3 = null;
        }
        try {
            line = bufferedReader.readLine();
            logOnEng(str, "readSysfs:: path = " + str2 + ", strTemp result = " + line);
            try {
                bufferedReader.close();
                r2 = line;
            } catch (IOException e2) {
                logOnEng(str, "e = " + e2.getMessage());
                r2 = line;
            }
        } catch (IOException e3) {
            e = e3;
            str3 = line;
            bufferedReader2 = bufferedReader;
            logOnEng(str, "e = " + e.getMessage());
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close();
                } catch (IOException e4) {
                    logOnEng(str, "e = " + e4.getMessage());
                }
            }
            r2 = str3;
            return r2;
        } catch (Throwable th2) {
            th = th2;
            r2 = bufferedReader;
            if (r2 != 0) {
                try {
                    r2.close();
                } catch (IOException e5) {
                    logOnEng(str, "e = " + e5.getMessage());
                }
            }
            throw th;
        }
        return r2;
    }
}
