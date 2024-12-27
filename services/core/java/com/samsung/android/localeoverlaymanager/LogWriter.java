package com.samsung.android.localeoverlaymanager;

import android.os.Environment;
import android.os.FileUtils;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Log;

import com.android.internal.util.FastPrintWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class LogWriter {
    public static void logDebugInfoAndLogcat(String str, String str2) {
        Log.i(str, str2);
        logToFile(str2);
    }

    public static void logToFile(String str) {
        FastPrintWriter fastPrintWriter;
        File file = new File(new File(Environment.getDataDirectory(), "log"), "lom_log.txt");
        if (file.length() > 1048576) {
            File file2 = new File(file.getPath() + ".temp");
            if (!file.renameTo(file2)) {
                Log.e("LogWriter", "Error in renaming to temp log file.");
            }
            file = new File(new File(Environment.getDataDirectory(), "log"), "lom_log.txt");
            try {
                fastPrintWriter = new FastPrintWriter(new FileOutputStream(file, true));
                try {
                    BufferedReader bufferedReader =
                            new BufferedReader(new FileReader(file2, StandardCharsets.UTF_8));
                    try {
                        if (bufferedReader.skip(250000L) != 250000) {
                            fastPrintWriter.println("Error in skipping file contents.");
                        }
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            } else {
                                fastPrintWriter.println(readLine);
                            }
                        }
                        if (!file2.delete()) {
                            Log.e("LogWriter", "Error in deleting temp log file.");
                        }
                        bufferedReader.close();
                        fastPrintWriter.close();
                    } finally {
                    }
                } finally {
                }
            } catch (IOException unused) {
            }
        }
        try {
            fastPrintWriter = new FastPrintWriter(new FileOutputStream(file, true));
            try {
                fastPrintWriter.println(
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                                        .format(new Date(System.currentTimeMillis()))
                                + ": "
                                + str);
                fastPrintWriter.flush();
                FileUtils.setPermissions(file.toString(), 508, -1, -1);
                fastPrintWriter.close();
            } finally {
                try {
                    fastPrintWriter.close();
                } catch (Throwable th) {
                    th.addSuppressed(th);
                }
            }
        } catch (IOException unused2) {
        }
    }

    public static void syncLogFile() {
        File file = new File(new File(Environment.getDataDirectory(), "log"), "lom_log.txt");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            try {
                FileDescriptor fd = fileOutputStream.getFD();
                if (fd != null) {
                    try {
                        Os.fsync(fd);
                    } catch (ErrnoException e) {
                        Log.e("LogWriter", "Couldn't copy " + file + " " + e.getMessage());
                    }
                    try {
                        Os.close(fd);
                    } catch (ErrnoException e2) {
                        Log.e("LogWriter", "Couldn't copy " + file + " " + e2.getMessage());
                    }
                }
                fileOutputStream.close();
            } finally {
            }
        } catch (IOException e3) {
            Log.e("LogWriter", "Couldn't copy " + file + " " + e3.getMessage());
        }
    }
}
