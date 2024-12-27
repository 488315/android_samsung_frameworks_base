package com.android.server.desktopmode;

import android.os.Process;
import com.samsung.android.desktopmode.DesktopModeFeature;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class Log {
    public static final DateTimeFormatter FORMATTER;
    public static final EvictingArrayQueue SAVED_LOGS;
    public static final EvictingArrayQueue SAVED_STATES;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EvictingArrayQueue {
        public final int mCapacity;
        public final ArrayDeque mElements;
        public final Object mLock = new Object();

        public EvictingArrayQueue(int i) {
            this.mCapacity = i;
            this.mElements = new ArrayDeque(i);
        }

        public final void add(Object obj) {
            synchronized (this.mLock) {
                try {
                    if (this.mElements.size() == this.mCapacity) {
                        this.mElements.removeFirst();
                    }
                    this.mElements.addLast(obj);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void dump(final PrintWriter printWriter) {
            synchronized (this.mLock) {
                this.mElements.forEach(new Consumer() { // from class: com.android.server.desktopmode.Log$EvictingArrayQueue$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        printWriter.println(obj);
                    }
                });
            }
        }
    }

    static {
        int i = DesktopModeFeature.DEBUG ? 300 : 200;
        FORMATTER = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss.SSS");
        SAVED_LOGS = new EvictingArrayQueue(i);
        SAVED_STATES = new EvictingArrayQueue(100);
    }

    public static String buildLogString(char c, String str, String str2) {
        return String.format(null, "%s %5d %5d %5d %c %s: %s", LocalDateTime.now().format(FORMATTER), Integer.valueOf(Process.myUid()), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()), Character.valueOf(c), str, str2);
    }

    public static void d(String str, String str2) {
        save('D', str, str2);
        android.util.Log.d(str, str2);
    }

    public static void e(String str, String str2) {
        save('E', str, str2);
        android.util.Log.e(str, str2);
    }

    public static void e(String str, String str2, Throwable th) {
        save('E', str, str2);
        android.util.Log.e(str, str2, th);
    }

    public static void i(String str, String str2) {
        save('I', str, str2);
        android.util.Log.i(str, str2);
    }

    public static void save(char c, String str, String str2) {
        SAVED_LOGS.add(buildLogString(c, str, str2));
    }

    public static void v(String str, String str2) {
        save('V', str, str2);
        android.util.Log.v(str, str2);
    }

    public static void w(String str, String str2) {
        save('W', str, str2);
        android.util.Log.w(str, str2);
    }
}
