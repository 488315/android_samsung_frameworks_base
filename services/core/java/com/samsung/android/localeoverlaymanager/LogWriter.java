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

/* loaded from: classes2.dex */
public abstract class LogWriter {
    public static final String TAG = "LogWriter";

    public static void logDebugInfoAndLogcat(String str, String str2) {
        Log.i(str, str2);
        logToFile("lom_log.txt", str2);
    }

    public static void logErrorToFile(String str, String str2) {
        Log.i(str, str2);
        logToFile("lom_log.txt", str2);
        syncLogFile();
    }

    public static void syncLogFile() {
        File dumpFile = getDumpFile("lom_log.txt");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(dumpFile, true);
            try {
                FileDescriptor fd = fileOutputStream.getFD();
                if (fd != null) {
                    try {
                        Os.fsync(fd);
                    } catch (ErrnoException e) {
                        Log.e(TAG, "Couldn't copy " + dumpFile + " " + e.getMessage());
                    }
                    try {
                        Os.close(fd);
                    } catch (ErrnoException e2) {
                        Log.e(TAG, "Couldn't copy " + dumpFile + " " + e2.getMessage());
                    }
                }
                fileOutputStream.close();
            } finally {
            }
        } catch (IOException e3) {
            Log.e(TAG, "Couldn't copy " + dumpFile + " " + e3.getMessage());
        }
    }

    public static void logToFile(String str, String str2) {
        FastPrintWriter fastPrintWriter;
        File dumpFile = getDumpFile(str);
        if (dumpFile != null && dumpFile.length() > 1048576) {
            File file = new File(dumpFile.getPath() + ".temp");
            if (!dumpFile.renameTo(file)) {
                Log.e(TAG, "Error in renaming to temp log file.");
            }
            dumpFile = getDumpFile(str);
            try {
                fastPrintWriter = new FastPrintWriter(new FileOutputStream(dumpFile, true));
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
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
                        if (!file.delete()) {
                            Log.e(TAG, "Error in deleting temp log file.");
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
            fastPrintWriter = new FastPrintWriter(new FileOutputStream(dumpFile, true));
            try {
                fastPrintWriter.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(System.currentTimeMillis())) + ": " + str2);
                fastPrintWriter.flush();
                FileUtils.setPermissions(dumpFile.toString(), 508, -1, -1);
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

    public static File getDumpFile(String str) {
        return new File(new File(Environment.getDataDirectory(), "log"), str);
    }
}
