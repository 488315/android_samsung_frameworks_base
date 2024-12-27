package com.android.server.am;

import android.os.Build;
import android.os.Debug;
import android.os.FileUtils;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Slog;
import android.util.SparseBooleanArray;

import com.android.internal.os.ProcessCpuTracker;
import com.android.internal.os.anr.AnrLatencyTracker;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class StackTracesDumpHelper {
    public static final SimpleDateFormat ANR_FILE_DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
    public static final int NATIVE_DUMP_TIMEOUT_MS;
    public static final int TEMP_DUMP_TIME_LIMIT;

    static {
        int i = Build.HW_TIMEOUT_MULTIPLIER;
        NATIVE_DUMP_TIMEOUT_MS = i * 2000;
        TEMP_DUMP_TIME_LIMIT = i * 10000;
    }

    public static int appendtoANRFile(String str, String str2) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str, true);
            try {
                byte[] bytes = str2.getBytes(StandardCharsets.UTF_8);
                fileOutputStream.write(bytes);
                int length = bytes.length;
                fileOutputStream.close();
                return length;
            } finally {
            }
        } catch (IOException e) {
            Slog.w("ActivityManager", "Exception writing to ANR dump file:", e);
            return 0;
        }
    }

    public static ArrayList collectPids(Future future, String str) {
        if (future == null) {
            return null;
        }
        try {
            return (ArrayList) future.get();
        } catch (InterruptedException e) {
            Slog.w("ActivityManager", "Interrupted while collecting ".concat(str), e);
            return null;
        } catch (ExecutionException e2) {
            Slog.w("ActivityManager", "Failed to collect ".concat(str), e2.getCause());
            return null;
        }
    }

    public static synchronized File createAnrDumpFile(File file) {
        File file2;
        synchronized (StackTracesDumpHelper.class) {
            file2 = new File(file, "anr_" + ANR_FILE_DATE_FORMAT.format(new Date()));
            if (!file2.createNewFile()) {
                throw new IOException("Unable to create ANR dump file: createNewFile failed");
            }
            FileUtils.setPermissions(
                    file2.getAbsolutePath(),
                    FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT,
                    -1,
                    -1);
        }
        return file2;
    }

    public static long dumpJavaTracesTombstoned(int i, String str, long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(i, "----- dumping pid: ", " at ");
        m.append(SystemClock.uptimeMillis());
        m.append("\n");
        int appendtoANRFile = appendtoANRFile(str, m.toString());
        boolean dumpJavaBacktraceToFileTimeout =
                Debug.dumpJavaBacktraceToFileTimeout(i, str, (int) (j / 1000));
        if (dumpJavaBacktraceToFileTimeout) {
            try {
            } catch (Exception e) {
                Slog.w("ActivityManager", "Unable to get ANR file size", e);
            }
            if (new File(str).length() - appendtoANRFile < 100) {
                Slog.w("ActivityManager", "Successfully created Java ANR file is empty!");
                dumpJavaBacktraceToFileTimeout = false;
            }
        }
        if (!dumpJavaBacktraceToFileTimeout) {
            Slog.w("ActivityManager", "Dumping Java threads failed, initiating native stack dump.");
            if (!Debug.dumpNativeBacktraceToFileTimeout(i, str, NATIVE_DUMP_TIMEOUT_MS / 1000)) {
                Slog.w("ActivityManager", "Native stack dump failed!");
            }
        }
        return SystemClock.elapsedRealtime() - elapsedRealtime;
    }

    /* JADX WARN: Code restructure failed: missing block: B:159:0x00b9, code lost:

       if (r24 == null) goto L32;
    */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x0097, code lost:

       if (r24 == null) goto L32;
    */
    /* JADX WARN: Not initialized variable reg: 12, insn: 0x009e: MOVE (r5 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r12 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]), block:B:174:0x009e */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00cf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static long dumpStackTraces(
            java.lang.String r19,
            java.util.ArrayList r20,
            java.util.concurrent.Future r21,
            java.util.concurrent.Future r22,
            java.util.concurrent.Future r23,
            com.android.internal.os.anr.AnrLatencyTracker r24) {
        /*
            Method dump skipped, instructions count: 618
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.StackTracesDumpHelper.dumpStackTraces(java.lang.String,"
                    + " java.util.ArrayList, java.util.concurrent.Future,"
                    + " java.util.concurrent.Future, java.util.concurrent.Future,"
                    + " com.android.internal.os.anr.AnrLatencyTracker):long");
    }

    public static File dumpStackTraces(
            ArrayList arrayList,
            final ProcessCpuTracker processCpuTracker,
            final SparseBooleanArray sparseBooleanArray,
            Future future,
            StringWriter stringWriter,
            AtomicLong atomicLong,
            String str,
            String str2,
            String str3,
            Executor executor,
            Future future2,
            final AnrLatencyTracker anrLatencyTracker) {
        String str4;
        if (anrLatencyTracker != null) {
            try {
                anrLatencyTracker.dumpStackTracesStarted();
            } catch (Throwable th) {
                if (anrLatencyTracker != null) {
                    anrLatencyTracker.dumpStackTracesEnded();
                }
                throw th;
            }
        }
        Slog.i("ActivityManager", "dumpStackTraces pids=" + sparseBooleanArray);
        Supplier supplier =
                processCpuTracker != null
                        ? new Supplier() { // from class:
                            // com.android.server.am.StackTracesDumpHelper$$ExternalSyntheticLambda0
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                ProcessCpuTracker processCpuTracker2 = processCpuTracker;
                                SparseBooleanArray sparseBooleanArray2 = sparseBooleanArray;
                                AnrLatencyTracker anrLatencyTracker2 = anrLatencyTracker;
                                if (anrLatencyTracker2 != null) {
                                    anrLatencyTracker2.processCpuTrackerMethodsCalled();
                                }
                                ArrayList arrayList2 = new ArrayList();
                                synchronized (processCpuTracker2) {
                                    processCpuTracker2.init();
                                }
                                try {
                                    Thread.sleep(200L);
                                } catch (InterruptedException unused) {
                                }
                                synchronized (processCpuTracker2) {
                                    try {
                                        processCpuTracker2.update();
                                        int countWorkingStats =
                                                processCpuTracker2.countWorkingStats();
                                        for (int i = 0;
                                                i < countWorkingStats && arrayList2.size() < 2;
                                                i++) {
                                            ProcessCpuTracker.Stats workingStats =
                                                    processCpuTracker2.getWorkingStats(i);
                                            if (sparseBooleanArray2.indexOfKey(workingStats.pid)
                                                    >= 0) {
                                                arrayList2.add(Integer.valueOf(workingStats.pid));
                                            } else {
                                                Slog.i(
                                                        "ActivityManager",
                                                        "Skipping next CPU consuming process, not a"
                                                            + " java proc: "
                                                                + workingStats.pid);
                                            }
                                        }
                                    } catch (Throwable th2) {
                                        throw th2;
                                    }
                                }
                                if (anrLatencyTracker2 != null) {
                                    anrLatencyTracker2.processCpuTrackerMethodsReturned();
                                }
                                return arrayList2;
                            }
                        }
                        : null;
        CompletableFuture supplyAsync =
                supplier != null ? CompletableFuture.supplyAsync(supplier, executor) : null;
        File file = new File("/data/anr");
        try {
            File createAnrDumpFile = createAnrDumpFile(file);
            if (str != null || str2 != null || str3 != null) {
                String absolutePath = createAnrDumpFile.getAbsolutePath();
                StringBuilder sb = new StringBuilder();
                if (str != null) {
                    str4 = "Subject: " + str + "\n";
                } else {
                    str4 = "";
                }
                sb.append(str4);
                sb.append(str3 != null ? str3.concat("\n\n") : "");
                sb.append(str2 != null ? str2 : "");
                appendtoANRFile(absolutePath, sb.toString());
            }
            long dumpStackTraces =
                    dumpStackTraces(
                            createAnrDumpFile.getAbsolutePath(),
                            arrayList,
                            future,
                            supplyAsync,
                            future2,
                            anrLatencyTracker);
            if (atomicLong != null) {
                atomicLong.set(dumpStackTraces);
            }
            maybePruneOldTraces(file);
            if (anrLatencyTracker != null) {
                anrLatencyTracker.dumpStackTracesEnded();
            }
            return createAnrDumpFile;
        } catch (IOException e) {
            Slog.w("ActivityManager", "Exception creating ANR dump file:", e);
            if (stringWriter != null) {
                stringWriter.append("----- Exception creating ANR dump file -----\n");
                e.printStackTrace(new PrintWriter(stringWriter));
            }
            if (anrLatencyTracker != null) {
                anrLatencyTracker.anrSkippedDumpStackTraces();
            }
            if (anrLatencyTracker != null) {
                anrLatencyTracker.dumpStackTracesEnded();
            }
            return null;
        }
    }

    public static void maybePruneOldTraces(File file) {
        int i;
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return;
        }
        int i2 = SystemProperties.getInt("tombstoned.max_anr_count", 64);
        long currentTimeMillis = System.currentTimeMillis();
        try {
            Arrays.sort(
                    listFiles,
                    Comparator.comparingLong(new StackTracesDumpHelper$$ExternalSyntheticLambda1())
                            .reversed());
            while (i < listFiles.length) {
                i =
                        (i <= i2
                                        && currentTimeMillis - listFiles[i].lastModified()
                                                <= BackupManagerConstants
                                                        .DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS)
                                ? i + 1
                                : 0;
                if (!listFiles[i].delete()) {
                    Slog.w("ActivityManager", "Unable to prune stale trace file: " + listFiles[i]);
                }
            }
        } catch (IllegalArgumentException e) {
            Slog.w(
                    "ActivityManager",
                    "tombstone modification times changed while sorting; not pruning",
                    e);
        }
    }
}
