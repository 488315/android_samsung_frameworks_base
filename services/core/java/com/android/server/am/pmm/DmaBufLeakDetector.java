package com.android.server.am.pmm;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.os.SystemProperties;
import android.util.Slog;

import com.android.internal.os.KernelAllocationStats;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DmaBufLeakDetector {
    public Context mContext;
    public boolean mIsListenerAppInstalled;
    public boolean mIsReporting;
    public boolean mIsTestMode;
    public long mLastCheckTime;
    public long mLastLeakTime;
    public float mLeakThreshold;
    public long mMemTotal;
    public int mReportCount;
    public static final String[] ISSUE_TRACKER_INTENT_LISTENERS_LIST = {
        "com.salab.issuetracker", "com.sec.salab.voyager"
    };
    public static final float[][] LEAK_THRESHOLD_STRATEGY = {
        new float[] {2.0f, 0.8f},
        new float[] {3.0f, 1.2f},
        new float[] {4.0f, 1.6f},
        new float[] {6.0f, 1.8f},
        new float[] {8.0f, 2.4f},
        new float[] {12.0f, 7.0f}
    };
    public static final String[] MEMINFO_FILEPATH = {"/proc/meminfo", "/proc/meminfo_extra"};
    public static final String[][] MEMINFO_CATEGORY = {
        new String[] {"system", "system-uncached", "mtk_mm", "mtk_mm-uncached"},
        new String[] {"SystemHeap", "Not-Used", "Not-Used", "Not-Used"}
    };

    public static long getDmaBufSizeKb() {
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        String[] strArr = MEMINFO_FILEPATH;
        long j = 0;
        int i = 0;
        for (int i2 = 0; i2 < 2; i2++) {
            try {
                FileReader fileReader = new FileReader(strArr[i2], StandardCharsets.UTF_8);
                try {
                    BufferedReader bufferedReader = new BufferedReader(fileReader, 2048);
                    try {
                        for (String readLine = bufferedReader.readLine();
                                readLine != null;
                                readLine = bufferedReader.readLine()) {
                            for (String str : MEMINFO_CATEGORY[i]) {
                                if (readLine.startsWith(str + ":")) {
                                    Slog.d("DmaBufLeakDetector", readLine);
                                    j += Integer.parseInt(readLine.replaceAll("\\D+", ""));
                                }
                            }
                        }
                        bufferedReader.close();
                        fileReader.close();
                    } catch (Throwable th) {
                        try {
                            bufferedReader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } finally {
                }
            } catch (Exception unused) {
                continue;
            }
            i++;
        }
        StrictMode.setThreadPolicy(allowThreadDiskReads);
        Slog.i("DmaBufLeakDetector", "DmaBuf : " + j + " KB");
        return j;
    }

    public static String getLargestDmaBufProcess() {
        KernelAllocationStats.ProcessDmabuf[] dmabufAllocations =
                KernelAllocationStats.getDmabufAllocations();
        if (dmabufAllocations == null || dmabufAllocations.length == 0) {
            return null;
        }
        Arrays.sort(
                dmabufAllocations,
                Comparator.comparingInt(new DmaBufLeakDetector$$ExternalSyntheticLambda0()));
        StringBuilder sb = new StringBuilder();
        sb.append(
                "\nheaviest process="
                        + dmabufAllocations[0].processName
                        + ", dmabuf_rss="
                        + dmabufAllocations[0].retainedSizeKb
                        + "KB");
        sb.append("\n\nName, UID, TotalRss(KB), Count, SFShareRss(KB), SFCount, ADJ");
        int length = dmabufAllocations.length;
        for (int i = 0; i < length; i++) {
            KernelAllocationStats.ProcessDmabuf processDmabuf = dmabufAllocations[i];
            sb.append(
                    "\n"
                            + processDmabuf.processName
                            + ", "
                            + processDmabuf.uid
                            + ", "
                            + processDmabuf.retainedSizeKb
                            + ", "
                            + processDmabuf.retainedBuffersCount
                            + ", "
                            + processDmabuf.surfaceFlingerSizeKb
                            + ", "
                            + processDmabuf.surfaceFlingerCount
                            + ", "
                            + processDmabuf.oomScore);
        }
        Slog.i("DmaBufLeakDetector", sb.toString());
        return dmabufAllocations[0].processName;
    }

    public static boolean isCameraRunning() {
        return !"0".equals(SystemProperties.get("service.camera.running", "0"));
    }

    public static boolean isListenerAppInstalled(Context context) {
        boolean z = false;
        for (int i = 0; i < 2; i++) {
            String str = ISSUE_TRACKER_INTENT_LISTENERS_LIST[i];
            try {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager.getApplicationInfo(str, z ? 1 : 0) != null) {
                    if (packageManager.checkSignatures(str, "android") == 0) {
                        Slog.d("DmaBufLeakDetector", "matched: " + i);
                        z = true;
                        z = true;
                        break;
                    }
                    Slog.d("DmaBufLeakDetector", "signature not matched: " + i);
                }
            } catch (PackageManager.NameNotFoundException unused) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        i, "not found app #", "DmaBufLeakDetector");
            }
        }
        return z;
    }
}
