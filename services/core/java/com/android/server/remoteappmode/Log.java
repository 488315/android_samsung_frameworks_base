package com.android.server.remoteappmode;

import android.os.Process;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class Log {
    public static final DateTimeFormatter sFormatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss.SSS");
    public static final EvictingArrayQueue sSavedLogs = new EvictingArrayQueue(200);
    public static final EvictingArrayQueue sSavedStates = new EvictingArrayQueue(100);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EvictingArrayQueue {
        public final int mCapacity;
        public final Object mLock = new Object();
        public int mPointer = 0;
        public final ArrayList mElements = new ArrayList();

        public EvictingArrayQueue(int i) {
            this.mCapacity = i;
        }

        public final void dump(PrintWriter printWriter) {
            synchronized (this.mLock) {
                try {
                    int size = this.mElements.size();
                    boolean z = size == this.mCapacity;
                    for (int i = 0; i < size; i++) {
                        printWriter.println(this.mElements.get(z ? ((this.mPointer + i) + 1) % this.mCapacity : i));
                    }
                } finally {
                }
            }
        }
    }

    public static String buildLogString(char c, String str, String str2) {
        return String.format(null, "%s %5d %5d %5d %c %s: %s", LocalDateTime.now().format(sFormatter), Integer.valueOf(Process.myUid()), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()), Character.valueOf(c), str, str2);
    }

    public static void d(String str, String str2) {
        save('D', str, str2);
        android.util.Log.d(str, str2);
    }

    public static void e(String str, String str2) {
        save('E', str, str2);
        android.util.Log.e(str, str2);
    }

    public static void i(String str, String str2) {
        save('I', str, str2);
        android.util.Log.i(str, str2);
    }

    public static void save(char c, String str, String str2) {
        EvictingArrayQueue evictingArrayQueue = sSavedLogs;
        String buildLogString = buildLogString(c, str, str2);
        synchronized (evictingArrayQueue.mLock) {
            try {
                evictingArrayQueue.mPointer = (evictingArrayQueue.mPointer + 1) % evictingArrayQueue.mCapacity;
                if (evictingArrayQueue.mElements.size() == evictingArrayQueue.mCapacity) {
                    evictingArrayQueue.mElements.set(evictingArrayQueue.mPointer, buildLogString);
                } else {
                    evictingArrayQueue.mElements.add(buildLogString);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
