package com.android.server.desktopmode;

import android.os.Process;
import com.android.server.desktopmode.BlockerManager;
import com.samsung.android.desktopmode.DesktopModeFeature;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public abstract class Log {
    public static final int CAPACITY_LOG;
    public static final DateTimeFormatter FORMATTER;
    public static final EvictingArrayQueue SAVED_LOGS;
    public static final EvictingArrayQueue SAVED_STATES;

    static {
        int i = DesktopModeFeature.DEBUG ? 300 : 200;
        CAPACITY_LOG = i;
        FORMATTER = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss.SSS");
        SAVED_LOGS = new EvictingArrayQueue(i);
        SAVED_STATES = new EvictingArrayQueue(100);
    }

    public static void dump(PrintWriter printWriter) {
        printWriter.println(buildLogString('V', "[DMS]StateStart", "=========================================================================="));
        SAVED_STATES.dump(printWriter);
        printWriter.println(buildLogString('V', "[DMS]StateEnd", "=========================================================================="));
        printWriter.println(buildLogString('V', "[DMS]SavedLogsStart", "=========================================================================="));
        SAVED_LOGS.dump(printWriter);
        printWriter.println(buildLogString('V', "[DMS]SavedLogsEnd", "=========================================================================="));
    }

    /* renamed from: d */
    public static int m7d(String str, String str2) {
        save('D', str, str2);
        return android.util.Log.d(str, str2);
    }

    /* renamed from: e */
    public static int m8e(String str, String str2) {
        save('E', str, str2);
        return android.util.Log.e(str, str2);
    }

    /* renamed from: e */
    public static int m9e(String str, String str2, Throwable th) {
        save('E', str, str2);
        return android.util.Log.e(str, str2, th);
    }

    /* renamed from: i */
    public static int m10i(String str, String str2) {
        save('I', str, str2);
        return android.util.Log.i(str, str2);
    }

    /* renamed from: v */
    public static int m11v(String str, String str2) {
        save('V', str, str2);
        return android.util.Log.v(str, str2);
    }

    /* renamed from: w */
    public static int m12w(String str, String str2) {
        save('W', str, str2);
        return android.util.Log.w(str, str2);
    }

    public static void saveState(State state, boolean z, boolean z2) {
        EvictingArrayQueue evictingArrayQueue = SAVED_STATES;
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(z ? "enter" : "exit");
        sb.append(z2 ? ", successful)" : ")");
        sb.append(" State=");
        sb.append(state);
        evictingArrayQueue.add(buildLogString('D', "[DMS]State", sb.toString()));
    }

    public static void saveState(State state, BlockerManager.DesktopModeBlockerInfo desktopModeBlockerInfo) {
        SAVED_STATES.add(buildLogString('W', "[DMS]State", "(enter) State=" + state + ", blocked by " + desktopModeBlockerInfo));
    }

    public static void save(char c, String str, String str2) {
        SAVED_LOGS.add(buildLogString(c, str, str2));
    }

    public static String buildLogString(char c, String str, String str2) {
        return String.format(null, "%s %5d %5d %5d %c %s: %s", LocalDateTime.now().format(FORMATTER), Integer.valueOf(Process.myUid()), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()), Character.valueOf(c), str, str2);
    }

    public class EvictingArrayQueue {
        public final int mCapacity;
        public final ArrayDeque mElements;
        public final Object mLock = new Object();

        public EvictingArrayQueue(int i) {
            this.mCapacity = i;
            this.mElements = new ArrayDeque(i);
        }

        public void add(Object obj) {
            synchronized (this.mLock) {
                if (this.mElements.size() == this.mCapacity) {
                    this.mElements.removeFirst();
                }
                this.mElements.addLast(obj);
            }
        }

        public void dump(final PrintWriter printWriter) {
            synchronized (this.mLock) {
                ArrayDeque arrayDeque = this.mElements;
                Objects.requireNonNull(printWriter);
                arrayDeque.forEach(new Consumer() { // from class: com.android.server.desktopmode.Log$EvictingArrayQueue$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        printWriter.println(obj);
                    }
                });
            }
        }
    }
}
