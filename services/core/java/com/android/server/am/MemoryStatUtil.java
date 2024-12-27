package com.android.server.am;

import android.os.FileUtils;
import android.os.SystemProperties;
import android.system.Os;
import android.system.OsConstants;
import android.util.Slog;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class MemoryStatUtil {
    public static final int PAGE_SIZE = (int) Os.sysconf(OsConstants._SC_PAGESIZE);
    public static final boolean DEVICE_HAS_PER_APP_MEMCG =
            SystemProperties.getBoolean("ro.config.per_app_memcg", false);
    public static final Pattern PGFAULT = Pattern.compile("total_pgfault (\\d+)");
    public static final Pattern PGMAJFAULT = Pattern.compile("total_pgmajfault (\\d+)");
    public static final Pattern RSS_IN_BYTES = Pattern.compile("total_rss (\\d+)");
    public static final Pattern CACHE_IN_BYTES = Pattern.compile("total_cache (\\d+)");
    public static final Pattern SWAP_IN_BYTES = Pattern.compile("total_swap (\\d+)");

    public final class MemoryStat {
        public long cacheInBytes;
        public long pgfault;
        public long pgmajfault;
        public long rssInBytes;
        public long swapInBytes;
    }

    public static MemoryStat parseMemoryStatFromMemcg(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        MemoryStat memoryStat = new MemoryStat();
        memoryStat.pgfault = tryParseLong(PGFAULT, str);
        memoryStat.pgmajfault = tryParseLong(PGMAJFAULT, str);
        memoryStat.rssInBytes = tryParseLong(RSS_IN_BYTES, str);
        memoryStat.cacheInBytes = tryParseLong(CACHE_IN_BYTES, str);
        memoryStat.swapInBytes = tryParseLong(SWAP_IN_BYTES, str);
        return memoryStat;
    }

    public static MemoryStat parseMemoryStatFromProcfs(String str) {
        if (str != null && !str.isEmpty()) {
            String[] split = str.split(" ");
            if (split.length < 24) {
                return null;
            }
            try {
                MemoryStat memoryStat = new MemoryStat();
                memoryStat.pgfault = Long.parseLong(split[9]);
                memoryStat.pgmajfault = Long.parseLong(split[11]);
                memoryStat.rssInBytes = Long.parseLong(split[23]) * PAGE_SIZE;
                return memoryStat;
            } catch (NumberFormatException e) {
                Slog.e("ActivityManager", "Failed to parse value", e);
            }
        }
        return null;
    }

    public static MemoryStat readMemoryStatFromFilesystem(int i, int i2) {
        String str = null;
        if (DEVICE_HAS_PER_APP_MEMCG) {
            Locale locale = Locale.US;
            File file =
                    new File(
                            DualAppManagerService$$ExternalSyntheticOutline0.m(
                                    i, i2, "/dev/memcg/apps/uid_", "/pid_", "/memory.stat"));
            if (file.exists()) {
                try {
                    str = FileUtils.readTextFile(file, 0, null);
                } catch (IOException e) {
                    Slog.e("ActivityManager", "Failed to read file:", e);
                }
            }
            return parseMemoryStatFromMemcg(str);
        }
        Locale locale2 = Locale.US;
        File file2 =
                new File(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                i2, "/proc/", "/stat"));
        if (file2.exists()) {
            try {
                str = FileUtils.readTextFile(file2, 0, null);
            } catch (IOException e2) {
                Slog.e("ActivityManager", "Failed to read file:", e2);
            }
        }
        return parseMemoryStatFromProcfs(str);
    }

    public static long tryParseLong(Pattern pattern, String str) {
        Matcher matcher = pattern.matcher(str);
        try {
            if (matcher.find()) {
                return Long.parseLong(matcher.group(1));
            }
            return 0L;
        } catch (NumberFormatException e) {
            Slog.e("ActivityManager", "Failed to parse value", e);
            return 0L;
        }
    }
}
