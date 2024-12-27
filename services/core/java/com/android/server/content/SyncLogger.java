package com.android.server.content;

import android.app.job.JobParameters;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemProperties;
import android.util.Log;
import android.util.Slog;
import com.android.internal.util.IntPair;
import com.android.server.IoThread;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import libcore.io.IoUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class SyncLogger {
    public static SyncLogger sInstance;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RotatingFileLogger extends SyncLogger {
        public long mCurrentLogFileDayTimestamp;
        public boolean mErrorShown;
        public Writer mLogWriter;
        public static final SimpleDateFormat sTimestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        public static final SimpleDateFormat sFilenameDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        public static final boolean DO_LOGCAT = Log.isLoggable("SyncLogger", 3);
        public final Object mLock = new Object();
        public final long mKeepAgeMs = TimeUnit.DAYS.toMillis(7);
        public final Date mCachedDate = new Date();
        public final StringBuilder mStringBuilder = new StringBuilder();
        public final File mLogPath = new File(Environment.getDataSystemDirectory(), "syncmanager-log");
        public final MyHandler mHandler = new MyHandler(IoThread.get().getLooper());

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class MyHandler extends Handler {
            public MyHandler(Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                RotatingFileLogger rotatingFileLogger = RotatingFileLogger.this;
                long of = IntPair.of(message.arg1, message.arg2);
                Object[] objArr = (Object[]) message.obj;
                synchronized (rotatingFileLogger.mLock) {
                    try {
                        rotatingFileLogger.openLogLocked(of);
                        if (rotatingFileLogger.mLogWriter == null) {
                            return;
                        }
                        rotatingFileLogger.mStringBuilder.setLength(0);
                        rotatingFileLogger.mCachedDate.setTime(of);
                        rotatingFileLogger.mStringBuilder.append(RotatingFileLogger.sTimestampFormat.format(rotatingFileLogger.mCachedDate));
                        rotatingFileLogger.mStringBuilder.append(' ');
                        rotatingFileLogger.mStringBuilder.append(Process.myTid());
                        rotatingFileLogger.mStringBuilder.append(' ');
                        int length = rotatingFileLogger.mStringBuilder.length();
                        for (Object obj : objArr) {
                            rotatingFileLogger.mStringBuilder.append(obj);
                        }
                        rotatingFileLogger.mStringBuilder.append('\n');
                        try {
                            rotatingFileLogger.mLogWriter.append((CharSequence) rotatingFileLogger.mStringBuilder);
                            rotatingFileLogger.mLogWriter.flush();
                            if (RotatingFileLogger.DO_LOGCAT) {
                                Log.d("SyncLogger", rotatingFileLogger.mStringBuilder.substring(length));
                            }
                        } catch (IOException e) {
                            if (!rotatingFileLogger.mErrorShown) {
                                Slog.e("SyncLogger", "Failed to write log", e);
                                rotatingFileLogger.mErrorShown = true;
                            }
                        }
                    } finally {
                    }
                }
            }
        }

        public static void dumpFile(File file, PrintWriter printWriter) {
            Slog.w("SyncLogger", "Dumping " + file);
            char[] cArr = new char[32768];
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                while (true) {
                    try {
                        int read = bufferedReader.read(cArr);
                        if (read < 0) {
                            bufferedReader.close();
                            return;
                        } else if (read > 0) {
                            printWriter.write(cArr, 0, read);
                        }
                    } finally {
                    }
                }
            } catch (IOException unused) {
            }
        }

        @Override // com.android.server.content.SyncLogger
        public final void dumpAll(PrintWriter printWriter) {
            synchronized (this.mLock) {
                try {
                    String[] list = this.mLogPath.list();
                    if (list != null && list.length != 0) {
                        Arrays.sort(list);
                        for (String str : list) {
                            dumpFile(new File(this.mLogPath, str), printWriter);
                        }
                    }
                } finally {
                }
            }
        }

        @Override // com.android.server.content.SyncLogger
        public final String jobParametersToString(JobParameters jobParameters) {
            return SyncJobService.jobParametersToString(jobParameters);
        }

        @Override // com.android.server.content.SyncLogger
        public final void log(Object... objArr) {
            long currentTimeMillis = System.currentTimeMillis();
            MyHandler myHandler = this.mHandler;
            myHandler.getClass();
            myHandler.obtainMessage(1, IntPair.first(currentTimeMillis), IntPair.second(currentTimeMillis), objArr).sendToTarget();
        }

        public final void openLogLocked(long j) {
            long j2 = j % BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
            Writer writer = this.mLogWriter;
            if (writer == null || j2 != this.mCurrentLogFileDayTimestamp) {
                IoUtils.closeQuietly(writer);
                this.mLogWriter = null;
                this.mCurrentLogFileDayTimestamp = j2;
                this.mCachedDate.setTime(j);
                File file = new File(this.mLogPath, "synclog-" + sFilenameDateFormat.format(this.mCachedDate) + ".log");
                file.getParentFile().mkdirs();
                try {
                    this.mLogWriter = new FileWriter(file, true);
                } catch (IOException e) {
                    String m = AccountManagerService$$ExternalSyntheticOutline0.m(file, "Failed to open log file: ");
                    if (this.mErrorShown) {
                        return;
                    }
                    Slog.e("SyncLogger", m, e);
                    this.mErrorShown = true;
                }
            }
        }

        @Override // com.android.server.content.SyncLogger
        public final void purgeOldLogs() {
            synchronized (this.mLock) {
                FileUtils.deleteOlderFiles(this.mLogPath, 1, this.mKeepAgeMs);
            }
        }
    }

    public static synchronized SyncLogger getInstance() {
        SyncLogger syncLogger;
        synchronized (SyncLogger.class) {
            try {
                if (sInstance == null) {
                    String str = SystemProperties.get("debug.synclog");
                    if (!Build.IS_DEBUGGABLE) {
                        if (!"1".equals(str)) {
                            if (Log.isLoggable("SyncLogger", 2)) {
                            }
                            sInstance = new SyncLogger();
                        }
                    }
                    if (!"0".equals(str)) {
                        sInstance = new RotatingFileLogger();
                    }
                    sInstance = new SyncLogger();
                }
                syncLogger = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return syncLogger;
    }

    public void dumpAll(PrintWriter printWriter) {
    }

    public String jobParametersToString(JobParameters jobParameters) {
        return "";
    }

    public void log(Object... objArr) {
    }

    public void purgeOldLogs() {
    }
}
