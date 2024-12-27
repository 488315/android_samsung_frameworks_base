package com.android.server.chimera;

import android.os.Debug;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class ChimeraCommonUtil {
    public static final int[] ADJ_LEVELS = {999, FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT, FrameworkStatsLog.VPN_CONNECTION_STATE_CHANGED, 800, 700, 600, 500, 400, 300, FrameworkStatsLog.CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_MACRO_RAW_SR_MERGE, 200, 100, 0};
    public static final Map mAppLaunchInfoMap = new ConcurrentHashMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum PsiDataType {
        AVG10("avg10"),
        TOTAL("total");

        private String mPath;

        PsiDataType(String str) {
            this.mPath = str;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PsiFile {
        public boolean mIsEmptyFile;
        public long mPsiFullTotal;
        public long mPsiSomeTotal;

        public static String getItem(int i, String str, String str2) {
            String[] split = str.split(" ");
            String concat = str2.concat("=");
            return (split.length <= i || !split[i].startsWith(concat)) ? "0" : split[i].replace(concat, "");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum PsiFileType {
        /* JADX INFO: Fake field, exist only in values array */
        EF0("/proc/pressure/io"),
        MEMORY("/proc/pressure/memory"),
        /* JADX INFO: Fake field, exist only in values array */
        EF29("/proc/pressure/cpu");

        private String mPath;

        PsiFileType(String str) {
            this.mPath = str;
        }

        public final String getPath() {
            return this.mPath;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum TriggerSource {
        TRIGGER_SOURCE_LMKD("LMKD"),
        TRIGGER_SOURCE_BOTTLENECK_HINT("BottleneckHint"),
        TRIGGER_SOURCE_HOME_IDLE("HomeIdle"),
        TRIGGER_SOURCE_PMM_CRITICAL("PMMCritical"),
        TRIGGER_SOURCE_DEVICE_IDLE("DeviceIdle"),
        TRIGGER_SOURCE_APP_LAUNCH_INTENT("AppLaunchIntent"),
        TRIGGER_SOURCE_QUOTA("Quota"),
        TRIGGER_SOURCE_LMKD_GENIE("LMKDGenie");

        String name;

        TriggerSource(String str) {
            this.name = str;
        }
    }

    public static long[] getAnonSizeAndZramSize(int i) {
        return new long[]{Math.max(getSmapsRollupParams(i, new String[]{"Pss_Anon:", "Swap:", "SwapShared:", "Writeback:"})[0], 0L), Math.max((long) (((r11[1] - r11[2]) - r11[3]) * 0.33d), 0L)};
    }

    public static long getAvailableMemoryKb(SystemRepository systemRepository) {
        return systemRepository.getAvailableMemory() / 1024;
    }

    public static long getDRAMUsed(int i, long j) {
        boolean z;
        if (j <= 0) {
            j = Debug.getPss(i, null, null);
            z = true;
        } else {
            z = false;
        }
        long[] smapsRollupParams = getSmapsRollupParams(i, new String[]{"SwapPss:", "Writeback:"});
        if (!z && j - smapsRollupParams[0] < 0) {
            j = Debug.getPss(i, null, null);
        }
        long j2 = smapsRollupParams[0];
        return Math.max((long) (((j2 - smapsRollupParams[1]) * 0.33d) + (j - j2)), 0L);
    }

    public static int getRamSizeGb() {
        return (int) Math.ceil(getTotalMemorySizeKb() / 1048576.0f);
    }

    public static long[] getSmapsRollupParams(int i, String[] strArr) {
        int length = strArr.length;
        long[] jArr = new long[length];
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "/proc/", "/smaps_rollup"), StandardCharsets.UTF_8));
            int i2 = 0;
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    if (readLine.startsWith(strArr[i2])) {
                        String[] split = readLine.split("\\s+");
                        if (split.length == 3) {
                            jArr[i2] = Long.parseLong(split[1]);
                        }
                        i2++;
                        if (i2 == length) {
                            break;
                        }
                    }
                } finally {
                }
            }
            bufferedReader.close();
            return jArr;
        } catch (IOException unused) {
            return new long[length];
        }
    }

    public static long getTotalMemorySizeKb() {
        try {
            FileReader fileReader = new FileReader("/proc/meminfo");
            try {
                BufferedReader bufferedReader = new BufferedReader(fileReader, 2048);
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        bufferedReader.close();
                        fileReader.close();
                        return 0L;
                    }
                    long parseInt = Integer.parseInt(readLine.substring(readLine.indexOf("MemTotal:")).replaceAll("\\D+", ""));
                    bufferedReader.close();
                    fileReader.close();
                    return parseInt;
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
