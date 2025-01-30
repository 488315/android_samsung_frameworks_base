package com.android.server.knox.dar.ddar;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.knox.dar.ddar.proxy.IProxyAgentService;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes2.dex */
public class DDLog {
    /* renamed from: v */
    public static int m36v(String str, String str2, Object... objArr) {
        String format = format(str2, objArr);
        Logger.enqueLog(str + ": " + format + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        return Log.d(str, format);
    }

    /* renamed from: d */
    public static int m33d(String str, String str2, Object... objArr) {
        String format = format(str2, objArr);
        Logger.enqueLog(str + ": " + format + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        return Log.d(str, format);
    }

    /* renamed from: w */
    public static int m37w(String str, String str2, Object... objArr) {
        String format = format(str2, objArr);
        Logger.enqueLog(str + ": " + format + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        return Log.w(str, format);
    }

    /* renamed from: i */
    public static int m35i(String str, String str2, Object... objArr) {
        String format = format(str2, objArr);
        Logger.enqueLog(str + ": " + format + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        return Log.i(str, format);
    }

    /* renamed from: e */
    public static int m34e(String str, String str2, Object... objArr) {
        String format = format(str2, objArr);
        Logger.enqueLog(str + ": " + format + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        return Log.e(str, format);
    }

    public static String format(String str, Object... objArr) {
        try {
            return String.format(str == null ? "" : str, objArr);
        } catch (Exception e) {
            Log.w("DDLog", "format error. reason = " + e.getMessage() + ", format = " + str);
            return String.format("", str);
        }
    }

    public class LoggerProxy extends IProxyAgentService {
        public static LoggerProxy mInstance;
        public final Context mContext;

        public static synchronized LoggerProxy getInstance(Context context) {
            LoggerProxy loggerProxy;
            synchronized (LoggerProxy.class) {
                if (mInstance == null) {
                    mInstance = new LoggerProxy(context);
                }
                loggerProxy = mInstance;
            }
            return loggerProxy;
        }

        public LoggerProxy(Context context) {
            Log.d("DDLog", "Logger ready");
            this.mContext = context;
            Logger.createInstance(context);
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x001d A[Catch: Exception -> 0x0027, TRY_LEAVE, TryCatch #0 {Exception -> 0x0027, blocks: (B:2:0x0000, B:10:0x001d, B:12:0x000f), top: B:1:0x0000 }] */
        /* JADX WARN: Removed duplicated region for block: B:7:0x001c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public Bundle onMessage(int i, String str, Bundle bundle) {
            char c;
            try {
                Bundle bundle2 = new Bundle();
                if (str.hashCode() == 1835875250 && str.equals("LOG_MSG_COMMAND")) {
                    c = 0;
                    if (c != 0) {
                        Logger.enqueLog(bundle.getString("LOG_MSG"));
                    }
                    return bundle2;
                }
                c = 65535;
                if (c != 0) {
                }
                return bundle2;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public class Logger {
        public static Logger mInstance;
        public Context mCtx;
        public BlockingQueue storeQ = new LinkedBlockingQueue();
        public final File storageDir = new File("/data/log");
        public File currentFile = null;
        public CountingOutputStream cos = null;
        public OutputStreamWriter fos = null;
        public boolean loggerRunning = false;
        public Thread logWorker = new Thread("Log Worker") { // from class: com.android.server.knox.dar.ddar.DDLog.Logger.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Log.d("DualDAR:DDLog:Logger", "DDAR Logger started running");
                while (Logger.this.loggerRunning) {
                    try {
                        if (Logger.this.storeQ.size() == 0 && Logger.this.fos != null) {
                            Logger.this.fos.flush();
                        }
                        Logger.this.realStore((String) Logger.this.storeQ.take());
                    } catch (Exception e) {
                        Log.w("DualDAR:DDLog:Logger", "Caught exception in log worker: " + e);
                        e.printStackTrace();
                    }
                }
            }
        };

        public static synchronized Logger createInstance(Context context) {
            Logger logger;
            synchronized (Logger.class) {
                if (mInstance == null) {
                    Logger logger2 = new Logger(context);
                    mInstance = logger2;
                    logger2.start();
                }
                logger = mInstance;
            }
            return logger;
        }

        public static synchronized Logger getInstance() {
            Logger logger;
            synchronized (Logger.class) {
                logger = mInstance;
            }
            return logger;
        }

        public Logger(Context context) {
            this.mCtx = null;
            this.mCtx = context;
        }

        public static void enqueLog(String str) {
            Logger logger = getInstance();
            if (logger == null) {
                Log.d("", str);
            } else {
                logger.log(str);
            }
        }

        public void log(String str) {
            if (this.loggerRunning) {
                try {
                    this.storeQ.add(getCurrentTime() + " " + str);
                } catch (Exception e) {
                    Log.e("DualDAR:DDLog:Logger", "Caught exception while adding to store queue! " + e);
                    e.printStackTrace();
                }
            }
        }

        public void start() {
            this.loggerRunning = true;
            this.logWorker.setDaemon(true);
            this.logWorker.start();
            enqueLog(String.format("DDAR Logging Started [DeviceVersion : %s]%n", getDeviceVersion()));
        }

        public final String getDeviceVersion() {
            String str = SystemProperties.get("ro.build.PDA", "Unknown");
            return (str == null || str.indexOf(95) == -1) ? str : str.substring(0, str.indexOf(95));
        }

        public String getCurrentTime() {
            return new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format((Object) new Date(System.currentTimeMillis()));
        }

        public final boolean roll() {
            try {
                internalClose();
                cleanupBackupFiles();
                moveCurrentToBackup();
                openCurrentFile();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        public final void internalClose() {
            OutputStreamWriter outputStreamWriter = this.fos;
            if (outputStreamWriter == null) {
                return;
            }
            try {
                outputStreamWriter.flush();
                this.fos.close();
                this.fos = null;
            } catch (IOException e) {
                Log.e("DualDAR:DDLog:Logger", "Caught exception while closing stream! " + e);
                e.printStackTrace();
            }
        }

        public final synchronized void realStore(String str) {
            try {
                if (this.currentFile == null) {
                    openCurrentFile();
                    if (this.currentFile == null) {
                        throw new IllegalStateException("No current file set in realStore!");
                    }
                }
                if (this.cos.getByteCount() > 4194304) {
                    Log.d("DualDAR:DDLog:Logger", "File '" + this.currentFile.getAbsolutePath() + "' is larger than 4194304 bytes. Rotating file.");
                    roll();
                }
                this.fos.write(str);
            } catch (Exception e) {
                Log.e("DualDAR:DDLog:Logger", "Caught exception while writing to stream! " + e);
                e.printStackTrace();
            }
        }

        public final void cleanupBackupFiles() {
            File[] listFiles = this.storageDir.listFiles(new FilenameFilter() { // from class: com.android.server.knox.dar.ddar.DDLog.Logger.1
                @Override // java.io.FilenameFilter
                public boolean accept(File file, String str) {
                    return str.startsWith("ddar_fw_log") && str.endsWith(".txt") && !str.equalsIgnoreCase("ddar_fw_log.txt");
                }
            });
            for (int i = 0; i < listFiles.length; i++) {
                if (!listFiles[i].delete()) {
                    Log.w("DualDAR:DDLog:Logger", String.format("Failed to delete file: %s", listFiles[i]));
                }
                Log.d("DualDAR:DDLog:Logger", "Log File " + listFiles[i].getAbsolutePath() + "is removed as next backup log file is ready");
            }
        }

        public final void openCurrentFile() {
            File file = new File(this.storageDir, "ddar_fw_log.txt");
            this.currentFile = file;
            if (!file.exists() && !this.currentFile.createNewFile()) {
                throw new IOException("Cannot create file " + this.currentFile.getAbsolutePath());
            }
            Log.d("DualDAR:DDLog:Logger", "Opened Existing or New Log file " + this.currentFile.getAbsolutePath() + " of length " + this.currentFile.length());
            CountingOutputStream countingOutputStream = new CountingOutputStream(new BufferedOutputStream(new FileOutputStream(this.currentFile, true)));
            this.cos = countingOutputStream;
            countingOutputStream.beforeWrite(this.currentFile.length());
            this.fos = new OutputStreamWriter(this.cos, Charset.forName("UTF-8"));
        }

        public final void moveCurrentToBackup() {
            File file = new File(this.storageDir, "ddar_fw_log" + new SimpleDateFormat("-yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".txt");
            Log.d("DualDAR:DDLog:Logger", "Rename Log File " + this.currentFile.getAbsolutePath() + " to " + file.getAbsolutePath());
            if (this.currentFile.renameTo(file)) {
                return;
            }
            Log.w("DualDAR:DDLog:Logger", String.format("Failed to renameTo file: %s to %s", this.currentFile, file));
        }
    }

    public final class CountingOutputStream extends FilterOutputStream {
        public long count;

        public CountingOutputStream(OutputStream outputStream) {
            super(outputStream);
        }

        public synchronized void beforeWrite(long j) {
            this.count += j;
        }

        public synchronized long getByteCount() {
            return this.count;
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public synchronized void write(byte[] bArr, int i, int i2) {
            ((FilterOutputStream) this).out.write(bArr, i, i2);
            this.count += i2;
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public synchronized void write(int i) {
            ((FilterOutputStream) this).out.write(i);
            this.count++;
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public synchronized void close() {
            ((FilterOutputStream) this).out.close();
        }
    }
}
