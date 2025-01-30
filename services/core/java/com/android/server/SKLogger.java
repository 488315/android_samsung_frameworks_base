package com.android.server;

import android.text.format.Time;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/* loaded from: classes.dex */
public class SKLogger {
    public static int LOG_MAX_SIZE = 20480;
    public static String LOG_NEW = "/data/misc/audit/sk.log";
    public static SKFormatter mFormatterTxt;
    public static Logger mLogger;
    public static final SKLogger mSKLogger = new SKLogger();
    public static SKHandler mSKTxt;

    public static synchronized SKLogger getLogger() {
        SKLogger sKLogger;
        synchronized (SKLogger.class) {
            if (mLogger == null) {
                try {
                    Logger logger = Logger.getLogger("SKLogger");
                    mLogger = logger;
                    logger.setUseParentHandlers(false);
                    for (Handler handler : mLogger.getHandlers()) {
                        mLogger.removeHandler(handler);
                    }
                    try {
                        mSKTxt = new SKLogger().new SKHandler(LOG_NEW, LOG_MAX_SIZE);
                        SKFormatter sKFormatter = new SKFormatter();
                        mFormatterTxt = sKFormatter;
                        mSKTxt.setFormatter(sKFormatter);
                        mLogger.addHandler(mSKTxt);
                    } catch (IllegalArgumentException unused) {
                        return null;
                    }
                } catch (Exception unused2) {
                    return null;
                }
            }
            sKLogger = mSKLogger;
        }
        return sKLogger;
    }

    public void logAll(String str, String str2) {
        Time time = new Time();
        time.set(System.currentTimeMillis());
        time.format("%d.%m.%Y %H:%M:%S");
        String str3 = (time.format("%d.%m.%Y %H:%M:%S") + " " + str + " ") + str2;
        synchronized (mSKLogger) {
            mLogger.log(Level.SEVERE, str3);
        }
    }

    public void logAll(String str, String str2, Throwable th) {
        Time time = new Time();
        time.set(System.currentTimeMillis());
        time.format("%d.%m.%Y %H:%M:%S");
        String str3 = ((time.format("%d.%m.%Y %H:%M:%S") + " " + str + " ") + str2 + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE) + getStackTraceString(th);
        synchronized (mSKLogger) {
            mLogger.log(Level.SEVERE, str3);
        }
    }

    public static String getStackTraceString(Throwable th) {
        if (th == null) {
            return "";
        }
        for (Throwable th2 = th; th2 != null; th2 = th2.getCause()) {
            if (th2 instanceof UnknownHostException) {
                return "";
            }
        }
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public class SKFormatter extends Formatter {
        public SKFormatter() {
        }

        @Override // java.util.logging.Formatter
        public String format(LogRecord logRecord) {
            return logRecord.getMessage() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE;
        }
    }

    public class SKHandler extends Handler {
        public FileOutputStream fileOutputStream;
        public long maxSize;
        public String newLogFile;
        public PrintWriter printWriter;

        public SKHandler(String str, long j) {
            this.newLogFile = str;
            this.maxSize = j;
            getPrintWriter();
        }

        public final PrintWriter getPrintWriter() {
            File file = new File(this.newLogFile);
            file.setReadable(true, false);
            file.setWritable(true, true);
            if (file.exists() && file.length() < this.maxSize) {
                try {
                    if (this.printWriter == null) {
                        this.fileOutputStream = new FileOutputStream(this.newLogFile, true);
                        this.printWriter = new PrintWriter(this.fileOutputStream);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (this.printWriter == null) {
                file.setReadable(true, false);
                file.setWritable(true, true);
                try {
                    this.fileOutputStream = new FileOutputStream(this.newLogFile);
                    this.printWriter = new PrintWriter(this.fileOutputStream);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            return this.printWriter;
        }

        @Override // java.util.logging.Handler
        public void publish(LogRecord logRecord) {
            if (isLoggable(logRecord)) {
                getPrintWriter();
                PrintWriter printWriter = this.printWriter;
                if (printWriter != null) {
                    printWriter.print(getFormatter().format(logRecord));
                    flush();
                }
            }
        }

        @Override // java.util.logging.Handler
        public void flush() {
            PrintWriter printWriter = this.printWriter;
            if (printWriter != null) {
                printWriter.flush();
            }
        }

        @Override // java.util.logging.Handler
        public void close() {
            try {
                FileOutputStream fileOutputStream = this.fileOutputStream;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            PrintWriter printWriter = this.printWriter;
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }
}
