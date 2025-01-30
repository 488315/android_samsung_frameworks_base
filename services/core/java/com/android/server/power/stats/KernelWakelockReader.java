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
import com.android.server.power.stats.KernelWakelockStats;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class KernelWakelockReader {
    public static final int[] PROC_WAKELOCKS_FORMAT = {5129, 8201, 9, 9, 9, 8201};
    public static final int[] WAKEUP_SOURCES_FORMAT = {4105, 8457, FrameworkStatsLog.USER_LIFECYCLE_EVENT_OCCURRED, FrameworkStatsLog.USER_LIFECYCLE_EVENT_OCCURRED, FrameworkStatsLog.USER_LIFECYCLE_EVENT_OCCURRED, FrameworkStatsLog.USER_LIFECYCLE_EVENT_OCCURRED, 8457};
    public static int sKernelWakelockUpdateVersion;
    public final String[] mProcWakelocksName = new String[3];
    public final long[] mProcWakelocksData = new long[3];
    public ISuspendControlServiceInternal mSuspendControlService = null;
    public byte[] mKernelWakelockBuffer = new byte[32768];

    public final KernelWakelockStats readKernelWakelockStats(KernelWakelockStats kernelWakelockStats) {
        FileInputStream fileInputStream;
        boolean z;
        int i;
        KernelWakelockStats removeOldStats;
        boolean exists = new File("/sys/class/wakeup").exists();
        Slog.i("KernelWakelockReader", "readKernelWakelockStats(" + exists + ")");
        if (exists) {
            synchronized (KernelWakelockReader.class) {
                updateVersion(kernelWakelockStats);
                if (getWakelockStatsFromSystemSuspend(kernelWakelockStats) == null) {
                    Slog.w("KernelWakelockReader", "Failed to get wakelock stats from SystemSuspend");
                    return null;
                }
                return removeOldStats(kernelWakelockStats);
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
                } catch (IOException e) {
                    Slog.wtf("KernelWakelockReader", "failed to read kernel wakelocks", e);
                    return null;
                } finally {
                    StrictMode.setThreadPolicyMask(allowThreadDiskReadsMask);
                }
            } catch (FileNotFoundException unused) {
                Slog.wtf("KernelWakelockReader", "neither /proc/wakelocks nor /d/wakeup_sources exists");
                return null;
            }
        } catch (FileNotFoundException unused2) {
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
            Slog.w("KernelWakelockReader", "Reading wakelock stats took " + uptimeMillis2 + "ms");
        }
        if (i > 0) {
            if (i >= this.mKernelWakelockBuffer.length) {
                Slog.wtf("KernelWakelockReader", "Kernel wake locks exceeded mKernelWakelockBuffer size " + this.mKernelWakelockBuffer.length);
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
            updateVersion(kernelWakelockStats);
            if (getWakelockStatsFromSystemSuspend(kernelWakelockStats) == null) {
                Slog.w("KernelWakelockReader", "Failed to get Native wakelock stats from SystemSuspend");
            }
            parseProcWakelocks(this.mKernelWakelockBuffer, i, z, kernelWakelockStats);
            removeOldStats = removeOldStats(kernelWakelockStats);
        }
        return removeOldStats;
    }

    public final ISuspendControlServiceInternal waitForSuspendControlService() {
        for (int i = 0; i < 5; i++) {
            ISuspendControlServiceInternal asInterface = ISuspendControlServiceInternal.Stub.asInterface(ServiceManager.getService("suspend_control_internal"));
            this.mSuspendControlService = asInterface;
            if (asInterface != null) {
                return asInterface;
            }
        }
        throw new ServiceManager.ServiceNotFoundException("suspend_control_internal");
    }

    public final KernelWakelockStats getWakelockStatsFromSystemSuspend(KernelWakelockStats kernelWakelockStats) {
        try {
            ISuspendControlServiceInternal waitForSuspendControlService = waitForSuspendControlService();
            this.mSuspendControlService = waitForSuspendControlService;
            try {
                updateWakelockStats(waitForSuspendControlService.getWakeLockStats(), kernelWakelockStats);
                return kernelWakelockStats;
            } catch (RemoteException e) {
                Slog.wtf("KernelWakelockReader", "Failed to obtain wakelock stats from ISuspendControlService", e);
                return null;
            } catch (IllegalArgumentException e2) {
                Slog.wtf("KernelWakelockReader", "SuspendControlService got IllegalArgumentException", e2);
                return null;
            }
        } catch (ServiceManager.ServiceNotFoundException e3) {
            Slog.wtf("KernelWakelockReader", "Required service suspend_control not available", e3);
            return null;
        }
    }

    public KernelWakelockStats updateWakelockStats(WakeLockInfo[] wakeLockInfoArr, KernelWakelockStats kernelWakelockStats) {
        long j;
        for (WakeLockInfo wakeLockInfo : wakeLockInfoArr) {
            if (!kernelWakelockStats.containsKey(wakeLockInfo.name)) {
                kernelWakelockStats.put(wakeLockInfo.name, new KernelWakelockStats.Entry((int) wakeLockInfo.activeCount, wakeLockInfo.totalTime * 1000, sKernelWakelockUpdateVersion));
                j = 0;
            } else {
                KernelWakelockStats.Entry entry = (KernelWakelockStats.Entry) kernelWakelockStats.get(wakeLockInfo.name);
                j = entry.mTotalTime;
                if (!wakeLockInfo.isKernelWakelock && entry.mVersion == sKernelWakelockUpdateVersion) {
                    entry.mCount += (int) wakeLockInfo.activeCount;
                    entry.mTotalTime = (wakeLockInfo.totalTime * 1000) + j;
                } else {
                    entry.mCount = (int) wakeLockInfo.activeCount;
                    entry.mTotalTime = wakeLockInfo.totalTime * 1000;
                    entry.mVersion = sKernelWakelockUpdateVersion;
                }
            }
            if (wakeLockInfo.name.contains("PowerManager") || (wakeLockInfo.totalTime * 1000) - j >= 10800000000L) {
                Slog.d("KernelWakelockReader", "name=" + wakeLockInfo.name + "\t isActive=" + wakeLockInfo.isActive + "\t lastChange=" + wakeLockInfo.lastChange + "\t activeCount=" + wakeLockInfo.activeCount + "\t activeTime=" + wakeLockInfo.activeTime + "\t totalTime=" + wakeLockInfo.totalTime + "\t version=" + sKernelWakelockUpdateVersion + "\t time_delta=" + ((wakeLockInfo.totalTime * 1000) - j));
            }
        }
        return kernelWakelockStats;
    }

    public KernelWakelockStats parseProcWakelocks(byte[] bArr, int i, boolean z, KernelWakelockStats kernelWakelockStats) {
        int[] iArr;
        long j;
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
                    byte b2 = bArr[i5];
                    if (b2 == 10 || b2 == 0) {
                        break;
                    }
                    i5++;
                }
                if (i5 > i - 1) {
                    break;
                }
                String[] strArr = this.mProcWakelocksName;
                long[] jArr = this.mProcWakelocksData;
                for (int i6 = i4; i6 < i5; i6++) {
                    if ((bArr[i6] & 128) != 0) {
                        bArr[i6] = 63;
                    }
                }
                if (z) {
                    iArr = WAKEUP_SOURCES_FORMAT;
                } else {
                    iArr = PROC_WAKELOCKS_FORMAT;
                }
                boolean parseProcLine = Process.parseProcLine(bArr, i4, i5, iArr, strArr, jArr, null);
                String trim = strArr[0].trim();
                int i7 = (int) jArr[1];
                if (z) {
                    j = jArr[2] * 1000;
                } else {
                    j = (jArr[2] + 500) / 1000;
                }
                if (!parseProcLine || trim.length() <= 0) {
                    if (!parseProcLine) {
                        try {
                            Slog.wtf("KernelWakelockReader", "Failed to parse proc line: " + new String(bArr, i4, i5 - i4));
                        } catch (Exception unused) {
                            Slog.wtf("KernelWakelockReader", "Failed to parse proc line!");
                        }
                    }
                } else if (!kernelWakelockStats.containsKey(trim)) {
                    kernelWakelockStats.put(trim, new KernelWakelockStats.Entry(i7, j, sKernelWakelockUpdateVersion));
                } else {
                    KernelWakelockStats.Entry entry = (KernelWakelockStats.Entry) kernelWakelockStats.get(trim);
                    int i8 = entry.mVersion;
                    int i9 = sKernelWakelockUpdateVersion;
                    if (i8 == i9) {
                        entry.mCount += i7;
                        entry.mTotalTime += j;
                    } else {
                        entry.mCount = i7;
                        entry.mTotalTime = j;
                        entry.mVersion = i9;
                    }
                }
                i4 = i5 + 1;
                i3 = i5;
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

    public KernelWakelockStats removeOldStats(KernelWakelockStats kernelWakelockStats) {
        Iterator it = kernelWakelockStats.values().iterator();
        while (it.hasNext()) {
            if (((KernelWakelockStats.Entry) it.next()).mVersion != sKernelWakelockUpdateVersion) {
                it.remove();
            }
        }
        return kernelWakelockStats;
    }
}
