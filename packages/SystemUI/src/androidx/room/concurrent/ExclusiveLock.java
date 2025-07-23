package androidx.room.concurrent;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ExclusiveLock {
    public static final Companion Companion = new Companion(null);
    public static final Map threadLocksMap = new LinkedHashMap();
    public final FileLock fileLock;
    public final ReentrantLock threadLock;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public ExclusiveLock(String str, boolean z) {
        ReentrantLock reentrantLock;
        FileLock fileLock;
        Companion companion = Companion;
        synchronized (companion) {
            try {
                LinkedHashMap linkedHashMap = (LinkedHashMap) threadLocksMap;
                Object obj = linkedHashMap.get(str);
                if (obj == null) {
                    obj = new ReentrantLock();
                    linkedHashMap.put(str, obj);
                }
                reentrantLock = (ReentrantLock) obj;
            } catch (Throwable th) {
                throw th;
            }
        }
        this.threadLock = reentrantLock;
        if (z) {
            companion.getClass();
            fileLock = new FileLock(str);
        } else {
            fileLock = null;
        }
        this.fileLock = fileLock;
    }
}
