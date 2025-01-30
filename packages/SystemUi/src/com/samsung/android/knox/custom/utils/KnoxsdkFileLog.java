package com.samsung.android.knox.custom.utils;

import android.util.Log;
import com.samsung.android.util.SemLog;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class KnoxsdkFileLog {
    public static final int LOG_FILE_MAX_COUNT = 2;
    public static final int LOG_FILE_SIZE_LIMIT = 500000;
    public static String TAG = "knoxsdk/filelog";
    public static Logger sLogger;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class KnoxsdkFileLogHolder {
        public static final KnoxsdkFileLog INSTANCE = new KnoxsdkFileLog(0);

        private KnoxsdkFileLogHolder() {
        }
    }

    public /* synthetic */ KnoxsdkFileLog(int i) {
        this();
    }

    /* renamed from: d */
    public static void m245d(String str, String str2) {
        fileLog(str, str2);
        SemLog.d(str, str2);
    }

    /* renamed from: e */
    public static void m247e(String str, String str2) {
        fileLog(str, str2);
        SemLog.e(str, str2);
    }

    public static void fileLog(String str, String str2) {
        KnoxsdkFileLog knoxsdkFileLog = KnoxsdkFileLogHolder.INSTANCE;
        if (sLogger != null) {
            sLogger.log(Level.INFO, String.format(" %s : %s\n", str, str2));
        }
    }

    public static KnoxsdkFileLog getInstance() {
        return KnoxsdkFileLogHolder.INSTANCE;
    }

    /* renamed from: i */
    public static void m248i(String str, String str2) {
        fileLog(str, str2);
        SemLog.i(str, str2);
    }

    public static void init() {
        try {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault());
            FileHandler fileHandler = new FileHandler("/data/log/knoxsdk.log", LOG_FILE_SIZE_LIMIT, 2, true);
            fileHandler.setFormatter(new Formatter() { // from class: com.samsung.android.knox.custom.utils.KnoxsdkFileLog.1
                @Override // java.util.logging.Formatter
                public final String format(LogRecord logRecord) {
                    Date date = new Date();
                    date.setTime(System.currentTimeMillis());
                    StringBuilder sb = new StringBuilder(80);
                    sb.append(simpleDateFormat.format(date));
                    sb.append(logRecord.getMessage());
                    return sb.toString();
                }
            });
            Logger logger = Logger.getLogger(KnoxsdkFileLog.class.getName());
            sLogger = logger;
            logger.addHandler(fileHandler);
            sLogger.setLevel(Level.ALL);
            sLogger.setUseParentHandlers(false);
            Log.d(TAG, "init success");
        } catch (IOException e) {
            Log.d(TAG, "init failure : " + e.getMessage());
        }
    }

    /* renamed from: v */
    public static void m249v(String str, String str2) {
        fileLog(str, str2);
        SemLog.v(str, str2);
    }

    /* renamed from: w */
    public static void m250w(String str, String str2) {
        fileLog(str, str2);
        SemLog.w(str, str2);
    }

    private KnoxsdkFileLog() {
        init();
    }

    /* renamed from: d */
    public static void m246d(String str, String str2, Throwable th) {
        fileLog(str, str2, th);
        SemLog.d(str, str2, th);
    }

    public static void fileLog(String str, String str2, Throwable th) {
        KnoxsdkFileLog knoxsdkFileLog = KnoxsdkFileLogHolder.INSTANCE;
        if (sLogger != null) {
            sLogger.log(Level.INFO, String.format(" %s : %s\n", str, str2), th);
        }
    }
}
