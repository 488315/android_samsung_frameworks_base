package com.android.server.power;

import android.content.Context;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;

import com.android.internal.util.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class PreRebootLogger {
    public static final String[] BUFFERS_TO_DUMP = {"system"};
    public static final String[] SERVICES_TO_DUMP = {"rollback", "package"};
    public static final Object sLock = new Object();
    public static final long MAX_DUMP_TIME = TimeUnit.SECONDS.toMillis(20);

    public static void dump(final File file, long j) {
        android.util.Slog.d("PreRebootLogger", "Dumping pre-reboot information...");
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        Thread thread =
                new Thread(
                        new Runnable() { // from class:
                                         // com.android.server.power.PreRebootLogger$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                File file2 = file;
                                AtomicBoolean atomicBoolean2 = atomicBoolean;
                                synchronized (PreRebootLogger.sLock) {
                                    try {
                                        PreRebootLogger.dumpLogsLocked(
                                                file2, PreRebootLogger.BUFFERS_TO_DUMP[0]);
                                        String[] strArr = PreRebootLogger.SERVICES_TO_DUMP;
                                        for (int i = 0; i < 2; i++) {
                                            PreRebootLogger.dumpServiceLocked(file2, strArr[i]);
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                                atomicBoolean2.set(true);
                            }
                        });
        thread.start();
        try {
            thread.join(j);
        } catch (InterruptedException e) {
            android.util.Slog.e(
                    "PreRebootLogger",
                    "Failed to dump pre-reboot information due to interrupted",
                    e);
        }
        if (atomicBoolean.get()) {
            return;
        }
        android.util.Slog.w(
                "PreRebootLogger", "Failed to dump pre-reboot information due to timeout");
    }

    public static void dumpLogsLocked(File file, String str) {
        try {
            File file2 = new File(file, str);
            if (file2.createNewFile()) {
                file2.setWritable(true, true);
            } else {
                new FileWriter(file2, false).flush();
            }
            Runtime.getRuntime()
                    .exec(new String[] {"logcat", "-d", "-b", str, "-f", file2.getAbsolutePath()})
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            android.util.Slog.e(
                    "PreRebootLogger", "Failed to dump system log buffer before reboot", e);
        }
    }

    public static void dumpServiceLocked(File file, String str) {
        IBinder checkService = ServiceManager.checkService(str);
        if (checkService == null) {
            return;
        }
        try {
            checkService.dump(
                    ParcelFileDescriptor.open(new File(file, str), 738197504).getFileDescriptor(),
                    (String[]) ArrayUtils.emptyArray(String.class));
        } catch (RemoteException | FileNotFoundException e) {
            android.util.Slog.e(
                    "PreRebootLogger", "Failed to dump " + str + " service before reboot", e);
        }
    }

    public static void log(Context context, File file) {
        if (Settings.Global.getInt(context.getContentResolver(), "adb_enabled", 0) == 1
                && !context.getPackageManager()
                        .getPackageInstaller()
                        .getActiveStagedSessions()
                        .isEmpty()) {
            dump(file, MAX_DUMP_TIME);
            return;
        }
        android.util.Slog.d("PreRebootLogger", "Wiping pre-reboot information...");
        synchronized (sLock) {
            try {
                for (File file2 : file.listFiles()) {
                    file2.delete();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
