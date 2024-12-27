package com.android.server.power.stats;

import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StrictMode;
import android.os.SystemClock;
import android.system.suspend.internal.ISuspendControlServiceInternal;
import android.system.suspend.internal.WakeLockInfo;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class KernelWakelockReader {
    public static final int[] PROC_WAKELOCKS_FORMAT = {5129, 8201, 9, 8201, 9, 8201};
    public static final int[] WAKEUP_SOURCES_FORMAT = {
        4105,
        8457,
        FrameworkStatsLog.USER_LIFECYCLE_EVENT_OCCURRED,
        FrameworkStatsLog.USER_LIFECYCLE_EVENT_OCCURRED,
        FrameworkStatsLog.USER_LIFECYCLE_EVENT_OCCURRED,
        8457,
        8457
    };
    public static int sKernelWakelockUpdateVersion;
    public final String[] mProcWakelocksName = new String[3];
    public final long[] mProcWakelocksData = new long[4];
    public ISuspendControlServiceInternal mSuspendControlService = null;
    public final byte[] mKernelWakelockBuffer = new byte[32768];

    public final KernelWakelockStats getWakelockStatsFromSystemSuspend(
            KernelWakelockStats kernelWakelockStats) {
        if (this.mSuspendControlService == null) {
            for (int i = 0; i < 5; i++) {
                try {
                    ISuspendControlServiceInternal asInterface =
                            ISuspendControlServiceInternal.Stub.asInterface(
                                    ServiceManager.getService("suspend_control_internal"));
                    this.mSuspendControlService = asInterface;
                    if (asInterface != null) {
                        this.mSuspendControlService = asInterface;
                    }
                } catch (ServiceManager.ServiceNotFoundException e) {
                    Slog.wtf(
                            "KernelWakelockReader",
                            "Required service suspend_control not available",
                            e);
                    return null;
                }
            }
            throw new ServiceManager.ServiceNotFoundException("suspend_control_internal");
        }
        try {
            updateWakelockStats(
                    this.mSuspendControlService.getWakeLockStats(), kernelWakelockStats);
            return kernelWakelockStats;
        } catch (RemoteException e2) {
            Slog.wtf(
                    "KernelWakelockReader",
                    "Failed to obtain wakelock stats from ISuspendControlService",
                    e2);
            return null;
        } catch (IllegalArgumentException e3) {
            Slog.wtf(
                    "KernelWakelockReader",
                    "SuspendControlService got IllegalArgumentException",
                    e3);
            return null;
        }
    }

    public KernelWakelockStats parseProcWakelocks(
            byte[] bArr, int i, boolean z, KernelWakelockStats kernelWakelockStats) {
        long j;
        long j2;
        byte b;
        int i2 = 0;
        while (i2 < i && (b = bArr[i2]) != 10 && b != 0) {
            i2++;
        }
        int i3 = i2 + 1;
        synchronized (this) {
            int i4 = i3;
            while (i3 < i) {
                int i5 = i4;
                while (i5 < i) {
                    try {
                        byte b2 = bArr[i5];
                        if (b2 == 10 || b2 == 0) {
                            break;
                        }
                        i5++;
                    } finally {
                    }
                }
                if (i5 <= i - 1) {
                    String[] strArr = this.mProcWakelocksName;
                    long[] jArr = this.mProcWakelocksData;
                    for (int i6 = i4; i6 < i5; i6++) {
                        if ((bArr[i6] & 128) != 0) {
                            bArr[i6] = 63;
                        }
                    }
                    boolean parseProcLine =
                            Process.parseProcLine(
                                    bArr,
                                    i4,
                                    i5,
                                    z ? WAKEUP_SOURCES_FORMAT : PROC_WAKELOCKS_FORMAT,
                                    strArr,
                                    jArr,
                                    null);
                    String trim = strArr[0].trim();
                    int i7 = (int) jArr[1];
                    if (z) {
                        j = jArr[2] * 1000;
                        j2 = jArr[3] * 1000;
                    } else {
                        j = (jArr[2] + 500) / 1000;
                        j2 = (jArr[3] + 500) / 1000;
                    }
                    long j3 = j;
                    if (!parseProcLine || trim.length() <= 0) {
                        if (!parseProcLine) {
                            try {
                                Slog.wtf(
                                        "KernelWakelockReader",
                                        "Failed to parse proc line: "
                                                + new String(bArr, i4, i5 - i4));
                            } catch (Exception unused) {
                                Slog.wtf("KernelWakelockReader", "Failed to parse proc line!");
                            }
                        }
                    } else if (kernelWakelockStats.containsKey(trim)) {
                        KernelWakelockStats.Entry entry =
                                (KernelWakelockStats.Entry) kernelWakelockStats.get(trim);
                        int i8 = entry.version;
                        int i9 = sKernelWakelockUpdateVersion;
                        if (i8 == i9) {
                            entry.count += i7;
                            entry.totalTimeUs += j2;
                            entry.activeTimeUs = j3;
                        } else {
                            entry.count = i7;
                            entry.totalTimeUs = j2;
                            entry.activeTimeUs = j3;
                            entry.version = i9;
                        }
                    } else {
                        kernelWakelockStats.put(
                                trim,
                                new KernelWakelockStats.Entry(
                                        i7, sKernelWakelockUpdateVersion, j2, j3));
                    }
                    i4 = i5 + 1;
                    i3 = i5;
                }
            }
        }
        return kernelWakelockStats;
    }

    public final KernelWakelockStats readKernelWakelockStats(
            KernelWakelockStats kernelWakelockStats) {
        FileInputStream fileInputStream;
        boolean z;
        int i;
        KernelWakelockStats removeOldStats;
        if (BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/wakeup")) {
            synchronized (KernelWakelockReader.class) {
                try {
                    updateVersion(kernelWakelockStats);
                    if (getWakelockStatsFromSystemSuspend(kernelWakelockStats) == null) {
                        Slog.w(
                                "KernelWakelockReader",
                                "Failed to get wakelock stats from SystemSuspend");
                        return null;
                    }
                    return removeOldStats(kernelWakelockStats);
                } finally {
                }
            }
        }
        int i2 = 0;
        Arrays.fill(this.mKernelWakelockBuffer, (byte) 0);
        long uptimeMillis = SystemClock.uptimeMillis();
        int allowThreadDiskReadsMask = StrictMode.allowThreadDiskReadsMask();
        try {
            try {
                try {
                    fileInputStream = new FileInputStream("/proc/wakelocks");
                    z = false;
                    i = 0;
                } catch (FileNotFoundException unused) {
                    fileInputStream = new FileInputStream("/d/wakeup_sources");
                    z = true;
                    i = 0;
                }
                while (true) {
                    byte[] bArr = this.mKernelWakelockBuffer;
                    int read = fileInputStream.read(bArr, i, bArr.length - i);
                    if (read <= 0) {
                        break;
                    }
                    i += read;
                }
                fileInputStream.close();
                StrictMode.setThreadPolicyMask(allowThreadDiskReadsMask);
                long uptimeMillis2 = SystemClock.uptimeMillis() - uptimeMillis;
                if (uptimeMillis2 > 100) {
                    Slog.w(
                            "KernelWakelockReader",
                            "Reading wakelock stats took " + uptimeMillis2 + "ms");
                }
                if (i > 0) {
                    if (i >= this.mKernelWakelockBuffer.length) {
                        Slog.wtf(
                                "KernelWakelockReader",
                                "Kernel wake locks exceeded mKernelWakelockBuffer size "
                                        + this.mKernelWakelockBuffer.length);
                    }
                    while (true) {
                        if (i2 >= i) {
                            break;
                        }
                        if (this.mKernelWakelockBuffer[i2] == 0) {
                            i = i2;
                            break;
                        }
                        i2++;
                    }
                }
                synchronized (KernelWakelockReader.class) {
                    try {
                        updateVersion(kernelWakelockStats);
                        if (getWakelockStatsFromSystemSuspend(kernelWakelockStats) == null) {
                            Slog.w(
                                    "KernelWakelockReader",
                                    "Failed to get Native wakelock stats from SystemSuspend");
                        }
                        parseProcWakelocks(this.mKernelWakelockBuffer, i, z, kernelWakelockStats);
                        removeOldStats = removeOldStats(kernelWakelockStats);
                    } finally {
                    }
                }
                return removeOldStats;
            } catch (FileNotFoundException unused2) {
                Slog.wtf(
                        "KernelWakelockReader",
                        "neither /proc/wakelocks nor /d/wakeup_sources exists");
                return null;
            }
        } catch (IOException e) {
            Slog.wtf("KernelWakelockReader", "failed to read kernel wakelocks", e);
            return null;
        } finally {
            StrictMode.setThreadPolicyMask(allowThreadDiskReadsMask);
        }
    }

    public KernelWakelockStats removeOldStats(KernelWakelockStats kernelWakelockStats) {
        Iterator it = kernelWakelockStats.values().iterator();
        while (it.hasNext()) {
            if (((KernelWakelockStats.Entry) it.next()).version != sKernelWakelockUpdateVersion) {
                it.remove();
            }
        }
        return kernelWakelockStats;
    }

    public KernelWakelockStats updateVersion(KernelWakelockStats kernelWakelockStats) {
        int i = sKernelWakelockUpdateVersion + 1;
        sKernelWakelockUpdateVersion = i;
        kernelWakelockStats.kernelWakelockVersion = i;
        return kernelWakelockStats;
    }

    public KernelWakelockStats updateWakelockStats(
            WakeLockInfo[] wakeLockInfoArr, KernelWakelockStats kernelWakelockStats) {
        for (WakeLockInfo wakeLockInfo : wakeLockInfoArr) {
            if (kernelWakelockStats.containsKey(wakeLockInfo.name)) {
                KernelWakelockStats.Entry entry =
                        (KernelWakelockStats.Entry) kernelWakelockStats.get(wakeLockInfo.name);
                if (wakeLockInfo.isKernelWakelock
                        || entry.version != sKernelWakelockUpdateVersion) {
                    entry.count = (int) wakeLockInfo.activeCount;
                    entry.totalTimeUs = wakeLockInfo.totalTime * 1000;
                    entry.activeTimeUs =
                            wakeLockInfo.isActive ? wakeLockInfo.activeTime * 1000 : 0L;
                    entry.version = sKernelWakelockUpdateVersion;
                } else {
                    entry.count += (int) wakeLockInfo.activeCount;
                    entry.totalTimeUs = (wakeLockInfo.totalTime * 1000) + entry.totalTimeUs;
                    entry.activeTimeUs +=
                            wakeLockInfo.isActive ? wakeLockInfo.activeTime * 1000 : 0L;
                }
            } else {
                kernelWakelockStats.put(
                        wakeLockInfo.name,
                        new KernelWakelockStats.Entry(
                                (int) wakeLockInfo.activeCount,
                                sKernelWakelockUpdateVersion,
                                wakeLockInfo.totalTime * 1000,
                                wakeLockInfo.isActive ? wakeLockInfo.activeTime * 1000 : 0L));
            }
        }
        return kernelWakelockStats;
    }
}
