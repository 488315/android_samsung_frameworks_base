package com.android.server;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.text.format.Time;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SKLogger {
    public static Logger mLogger;
    public static final SKLogger mSKLogger = new SKLogger();
    public static SKHandler mSKTxt;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SKFormatter extends Formatter {
        @Override // java.util.logging.Formatter
        public final String format(LogRecord logRecord) {
            return logRecord.getMessage() + "\n";
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SKHandler extends Handler {
        public FileOutputStream fileOutputStream;
        public final long maxSize;
        public final String newLogFile = "/data/misc/audit/sk.log";
        public PrintWriter printWriter;

        public SKHandler(long j) {
            this.maxSize = j;
            getPrintWriter();
        }

        @Override // java.util.logging.Handler
        public final void close() {
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

        @Override // java.util.logging.Handler
        public final void flush() {
            PrintWriter printWriter = this.printWriter;
            if (printWriter != null) {
                printWriter.flush();
            }
        }

        public final void getPrintWriter() {
            File file = new File(this.newLogFile);
            file.setReadable(true, false);
            file.setWritable(true, true);
            if (file.exists() && file.length() < this.maxSize) {
                try {
                    if (this.printWriter == null) {
                        this.fileOutputStream = new FileOutputStream(this.newLogFile, true);
                        this.printWriter =
                                new PrintWriter(
                                        this.fileOutputStream, false, StandardCharsets.UTF_8);
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
                    this.printWriter =
                            new PrintWriter(this.fileOutputStream, false, StandardCharsets.UTF_8);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override // java.util.logging.Handler
        public final void publish(LogRecord logRecord) {
            if (isLoggable(logRecord)) {
                getPrintWriter();
                PrintWriter printWriter = this.printWriter;
                if (printWriter != null) {
                    printWriter.print(getFormatter().format(logRecord));
                    flush();
                }
            }
        }
    }

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
                        mSKTxt = new SKHandler(20480);
                        mSKTxt.setFormatter(new SKFormatter());
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

    public static void logAll(String str, String str2) {
        Time time = new Time();
        time.set(System.currentTimeMillis());
        time.format("%d.%m.%Y %H:%M:%S");
        String m$1 =
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                        time.format("%d.%m.%Y %H:%M:%S") + " " + str + " ", str2);
        synchronized (mSKLogger) {
            mLogger.log(Level.SEVERE, m$1);
        }
    }

    public static void logAll(String str, Throwable th) {
        Time time = new Time();
        time.set(System.currentTimeMillis());
        time.format("%d.%m.%Y %H:%M:%S");
        StringBuilder m =
                BootReceiver$$ExternalSyntheticOutline0.m(
                        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                        time.format("%d.%m.%Y %H:%M:%S"), " ServiceKeeper "),
                                str,
                                "\n"));
        String str2 = "";
        Throwable th2 = th;
        while (true) {
            if (th2 == null) {
                StringWriter stringWriter = new StringWriter();
                th.printStackTrace(new PrintWriter(stringWriter));
                str2 = stringWriter.toString();
                break;
            } else if (th2 instanceof UnknownHostException) {
                break;
            } else {
                th2 = th2.getCause();
            }
        }
        m.append(str2);
        String sb = m.toString();
        synchronized (mSKLogger) {
            mLogger.log(Level.SEVERE, sb);
        }
    }
}
