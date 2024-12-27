package com.android.server.knox.dar.ddar;

import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;

import com.samsung.android.knox.dar.ddar.proxy.IProxyAgentService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class DDLog {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CountingOutputStream extends FilterOutputStream {
        public long count;

        @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable,
                  // java.lang.AutoCloseable
        public final synchronized void close() {
            ((FilterOutputStream) this).out.close();
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public final synchronized void write(int i) {
            ((FilterOutputStream) this).out.write(i);
            this.count++;
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public final synchronized void write(byte[] bArr, int i, int i2) {
            ((FilterOutputStream) this).out.write(bArr, i, i2);
            this.count += i2;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Logger {
        public static Logger mInstance;
        public final BlockingQueue storeQ = new LinkedBlockingQueue();
        public final File storageDir = new File("/data/log");
        public File currentFile = null;
        public CountingOutputStream cos = null;
        public OutputStreamWriter fos = null;
        public boolean loggerRunning = false;
        public final AnonymousClass2 logWorker =
                new Thread() { // from class: com.android.server.knox.dar.ddar.DDLog.Logger.2
                    @Override // java.lang.Thread, java.lang.Runnable
                    public final void run() {
                        OutputStreamWriter outputStreamWriter;
                        Log.d("DualDAR:DDLog:Logger", "DDAR Logger started running");
                        while (true) {
                            Logger logger = Logger.this;
                            if (!logger.loggerRunning) {
                                return;
                            }
                            try {
                                if (((LinkedBlockingQueue) logger.storeQ).size() == 0
                                        && (outputStreamWriter = Logger.this.fos) != null) {
                                    outputStreamWriter.flush();
                                }
                                Logger.m631$$Nest$mrealStore(
                                        Logger.this,
                                        (String) ((LinkedBlockingQueue) Logger.this.storeQ).take());
                            } catch (Exception e) {
                                Log.w(
                                        "DualDAR:DDLog:Logger",
                                        "Caught exception in log worker: " + e);
                                e.printStackTrace();
                            }
                        }
                    }
                };

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.knox.dar.ddar.DDLog$Logger$1, reason: invalid class name */
        public final class AnonymousClass1 implements FilenameFilter {
            @Override // java.io.FilenameFilter
            public final boolean accept(File file, String str) {
                return str.startsWith("ddar_fw_log")
                        && str.endsWith(".txt")
                        && !str.equalsIgnoreCase("ddar_fw_log.txt");
            }
        }

        /* renamed from: -$$Nest$mrealStore, reason: not valid java name */
        public static void m631$$Nest$mrealStore(Logger logger, String str) {
            long j;
            synchronized (logger) {
                try {
                    try {
                        if (logger.currentFile == null) {
                            logger.openCurrentFile();
                            if (logger.currentFile == null) {
                                throw new IllegalStateException(
                                        "No current file set in realStore!");
                            }
                        }
                        CountingOutputStream countingOutputStream = logger.cos;
                        synchronized (countingOutputStream) {
                            j = countingOutputStream.count;
                        }
                        if (j > 4194304) {
                            Log.d(
                                    "DualDAR:DDLog:Logger",
                                    "File '"
                                            + logger.currentFile.getAbsolutePath()
                                            + "' is larger than 4194304 bytes. Rotating file.");
                            logger.roll();
                        }
                        logger.fos.write(str);
                    } catch (Exception e) {
                        Log.e(
                                "DualDAR:DDLog:Logger",
                                "Caught exception while writing to stream! " + e);
                        e.printStackTrace();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public static void enqueLog(String str) {
            Logger logger;
            synchronized (Logger.class) {
                logger = mInstance;
            }
            if (logger == null) {
                Log.d("", str);
                return;
            }
            if (logger.loggerRunning) {
                try {
                    logger.storeQ.add(
                            new SimpleDateFormat("MM-dd HH:mm:ss.SSS")
                                            .format((Object) new Date(System.currentTimeMillis()))
                                    + " "
                                    + str);
                } catch (Exception e) {
                    Log.e(
                            "DualDAR:DDLog:Logger",
                            "Caught exception while adding to store queue! " + e);
                    e.printStackTrace();
                }
            }
        }

        public final void cleanupBackupFiles() {
            File[] listFiles = this.storageDir.listFiles(new AnonymousClass1());
            for (int i = 0; i < listFiles.length; i++) {
                if (!listFiles[i].delete()) {
                    Log.w(
                            "DualDAR:DDLog:Logger",
                            String.format("Failed to delete file: %s", listFiles[i]));
                }
                Log.d(
                        "DualDAR:DDLog:Logger",
                        "Log File "
                                + listFiles[i].getAbsolutePath()
                                + "is removed as next backup log file is ready");
            }
        }

        public final void moveCurrentToBackup() {
            File file =
                    new File(
                            this.storageDir,
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    "ddar_fw_log",
                                    new SimpleDateFormat("-yyyy-MM-dd-HH-mm-ss").format(new Date()),
                                    ".txt"));
            Log.d(
                    "DualDAR:DDLog:Logger",
                    "Rename Log File "
                            + this.currentFile.getAbsolutePath()
                            + " to "
                            + file.getAbsolutePath());
            if (this.currentFile.renameTo(file)) {
                return;
            }
            Log.w(
                    "DualDAR:DDLog:Logger",
                    String.format("Failed to renameTo file: %s to %s", this.currentFile, file));
        }

        public final void openCurrentFile() {
            File file = new File(this.storageDir, "ddar_fw_log.txt");
            this.currentFile = file;
            if (!file.exists() && !this.currentFile.createNewFile()) {
                throw new IOException("Cannot create file " + this.currentFile.getAbsolutePath());
            }
            Log.d(
                    "DualDAR:DDLog:Logger",
                    "Opened Existing or New Log file "
                            + this.currentFile.getAbsolutePath()
                            + " of length "
                            + this.currentFile.length());
            CountingOutputStream countingOutputStream =
                    new CountingOutputStream(
                            new BufferedOutputStream(new FileOutputStream(this.currentFile, true)));
            this.cos = countingOutputStream;
            long length = this.currentFile.length();
            synchronized (countingOutputStream) {
                countingOutputStream.count += length;
            }
            this.fos = new OutputStreamWriter(this.cos, Charset.forName("UTF-8"));
        }

        public final void roll() {
            try {
                OutputStreamWriter outputStreamWriter = this.fos;
                if (outputStreamWriter != null) {
                    try {
                        outputStreamWriter.flush();
                        this.fos.close();
                        this.fos = null;
                    } catch (IOException e) {
                        Log.e(
                                "DualDAR:DDLog:Logger",
                                "Caught exception while closing stream! " + e);
                        e.printStackTrace();
                    }
                }
                cleanupBackupFiles();
                moveCurrentToBackup();
                openCurrentFile();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

        public final void start() {
            this.loggerRunning = true;
            setDaemon(true);
            start();
            String str = SystemProperties.get("ro.build.PDA", "Unknown");
            if (str != null && str.indexOf(95) != -1) {
                str = str.substring(0, str.indexOf(95));
            }
            enqueLog(String.format("DDAR Logging Started [DeviceVersion : %s]%n", str));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LoggerProxy extends IProxyAgentService {
        public static LoggerProxy mInstance;

        public final Bundle onMessage(int i, String str, Bundle bundle) {
            try {
                Bundle bundle2 = new Bundle();
                if (str.hashCode() == 1835875250 && str.equals("LOG_MSG_COMMAND")) {
                    Logger.enqueLog(bundle.getString("LOG_MSG"));
                }
                return bundle2;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static void d(String str, String str2, Object... objArr) {
        String format = format(str2, objArr);
        Logger.enqueLog(str + ": " + format + "\n");
        Log.d(str, format);
    }

    public static void e(String str, String str2, Object... objArr) {
        String format = format(str2, objArr);
        Logger.enqueLog(str + ": " + format + "\n");
        Log.e(str, format);
    }

    public static String format(String str, Object... objArr) {
        try {
            return String.format(str == null ? "" : str, objArr);
        } catch (Exception e) {
            Log.w("DDLog", "format error. reason = " + e.getMessage() + ", format = " + str);
            return "";
        }
    }

    public static void i(String str, String str2, Object... objArr) {
        String format = format(str2, objArr);
        Logger.enqueLog(str + ": " + format + "\n");
        Log.i(str, format);
    }

    public static void v(String str, String str2, Object... objArr) {
        String format = format(str2, objArr);
        Logger.enqueLog(str + ": " + format + "\n");
        Log.d(str, format);
    }
}
