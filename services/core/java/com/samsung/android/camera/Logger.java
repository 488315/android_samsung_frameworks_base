package com.samsung.android.camera;

import android.util.Slog;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

/* loaded from: classes2.dex */
public class Logger {
    public static Logger[] mInstance = new Logger[EnumC3085ID.values().length];
    public Queue mLogQueue;

    /* renamed from: com.samsung.android.camera.Logger$ID */
    public enum EnumC3085ID {
        CAMERA_EVENT,
        SHAKE_EVENT_LISTENER,
        REQUEST_INJECTOR_SERVICE,
        VISION_SERVER_RECEIVER,
        FOLD_EVENT,
        POST_PROCESS_EVENT,
        CAMERA_APPLICATION_EVENT,
        DATABASE_EVENT
    }

    public Logger() {
        this.mLogQueue = null;
        this.mLogQueue = new LinkedList();
        Slog.v("CameraService_worker/Logger", "New Logger Instance");
    }

    public static synchronized Logger getInstance(EnumC3085ID enumC3085ID) {
        Logger logger;
        synchronized (Logger.class) {
            if (mInstance[enumC3085ID.ordinal()] == null) {
                mInstance[enumC3085ID.ordinal()] = new Logger();
            }
            logger = mInstance[enumC3085ID.ordinal()];
        }
        return logger;
    }

    public static void log(EnumC3085ID enumC3085ID, String str) {
        try {
            getInstance(enumC3085ID).addLogInternal(String.format("%s, %s", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z", Locale.getDefault()).format(new Date()), str));
        } catch (Exception unused) {
        }
    }

    public final synchronized void addLogInternal(String str) {
        while (this.mLogQueue.size() >= 2000) {
            this.mLogQueue.remove();
        }
        this.mLogQueue.add(str);
    }

    public static void dumpLog(EnumC3085ID enumC3085ID, PrintWriter printWriter) {
        printWriter.println("\n\tDump of CameraServiceWorker " + enumC3085ID.name());
        getInstance(enumC3085ID).dumpLogInternal(printWriter);
    }

    public final synchronized void dumpLogInternal(PrintWriter printWriter) {
        Iterator it = this.mLogQueue.iterator();
        while (it.hasNext()) {
            printWriter.println("\t\t" + ((String) it.next()));
        }
    }
}
