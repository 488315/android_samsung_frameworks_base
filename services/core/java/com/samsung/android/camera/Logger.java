package com.samsung.android.camera;

import android.util.Slog;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class Logger {
    public static final Logger[] mInstance = new Logger[ID.values().length];
    public Queue mLogQueue;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ID {
        public static final /* synthetic */ ID[] $VALUES;
        public static final ID CAMERA_APPLICATION_EVENT;
        public static final ID CAMERA_EVENT;
        public static final ID DATABASE_EVENT;
        public static final ID FOLD_EVENT;
        public static final ID POST_PROCESS_EVENT;
        public static final ID REQUEST_INJECTOR_SERVICE;
        public static final ID SHAKE_EVENT_LISTENER;

        static {
            ID id = new ID("CAMERA_EVENT", 0);
            CAMERA_EVENT = id;
            ID id2 = new ID("SHAKE_EVENT_LISTENER", 1);
            SHAKE_EVENT_LISTENER = id2;
            ID id3 = new ID("REQUEST_INJECTOR_SERVICE", 2);
            REQUEST_INJECTOR_SERVICE = id3;
            ID id4 = new ID("VISION_SERVER_RECEIVER", 3);
            ID id5 = new ID("FOLD_EVENT", 4);
            FOLD_EVENT = id5;
            ID id6 = new ID("POST_PROCESS_EVENT", 5);
            POST_PROCESS_EVENT = id6;
            ID id7 = new ID("CAMERA_APPLICATION_EVENT", 6);
            CAMERA_APPLICATION_EVENT = id7;
            ID id8 = new ID("DATABASE_EVENT", 7);
            DATABASE_EVENT = id8;
            $VALUES = new ID[] {id, id2, id3, id4, id5, id6, id7, id8};
        }

        public static ID valueOf(String str) {
            return (ID) Enum.valueOf(ID.class, str);
        }

        public static ID[] values() {
            return (ID[]) $VALUES.clone();
        }
    }

    public static void dumpLog(ID id, PrintWriter printWriter) {
        printWriter.println("\n\tDump of CameraServiceWorker " + id.name());
        Logger logger = getInstance(id);
        synchronized (logger) {
            Iterator it = logger.mLogQueue.iterator();
            while (it.hasNext()) {
                printWriter.println("\t\t" + ((String) it.next()));
            }
        }
    }

    public static synchronized Logger getInstance(ID id) {
        Logger logger;
        synchronized (Logger.class) {
            try {
                Logger[] loggerArr = mInstance;
                if (loggerArr[id.ordinal()] == null) {
                    int ordinal = id.ordinal();
                    Logger logger2 = new Logger();
                    logger2.mLogQueue = null;
                    logger2.mLogQueue = new LinkedList();
                    Slog.v("CameraService_worker/Logger", "New Logger Instance");
                    loggerArr[ordinal] = logger2;
                }
                logger = loggerArr[id.ordinal()];
            } catch (Throwable th) {
                throw th;
            }
        }
        return logger;
    }

    public static void log(ID id, String str) {
        try {
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z", Locale.getDefault());
            Logger logger = getInstance(id);
            String str2 = simpleDateFormat.format(new Date()) + ", " + str;
            synchronized (logger) {
                while (((LinkedList) logger.mLogQueue).size() >= 2000) {
                    try {
                        ((LinkedList) logger.mLogQueue).remove();
                    } finally {
                    }
                }
                ((LinkedList) logger.mLogQueue).add(str2);
            }
        } catch (Exception unused) {
        }
    }
}
