package androidx.room.concurrent;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class ExclusiveLock {
    public static final Companion Companion = new Companion(null);
    public static final Map threadLocksMap = new LinkedHashMap();
    public final FileLock fileLock;
    public final ReentrantLock threadLock;

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
                Object reentrantLock2 = linkedHashMap.get(str);
                if (reentrantLock2 == null) {
                    reentrantLock2 = new ReentrantLock();
                    linkedHashMap.put(str, reentrantLock2);
                }
                reentrantLock = (ReentrantLock) reentrantLock2;
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
