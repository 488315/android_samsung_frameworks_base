package androidx.room;

import java.util.concurrent.locks.ReentrantLock;
import kotlin.enums.EnumEntriesKt;

/* loaded from: classes.dex */
public final class ObservedTableStates {
    public final ReentrantLock lock = new ReentrantLock();
    public boolean needsSync;
    public final boolean[] tableObservedState;
    public final long[] tableObserversCount;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class ObserveOp {
        public static final /* synthetic */ ObserveOp[] $VALUES;
        public static final ObserveOp ADD;
        public static final ObserveOp NO_OP;
        public static final ObserveOp REMOVE;

        static {
            ObserveOp observeOp = new ObserveOp("NO_OP", 0);
            NO_OP = observeOp;
            ObserveOp observeOp2 = new ObserveOp("ADD", 1);
            ADD = observeOp2;
            ObserveOp observeOp3 = new ObserveOp("REMOVE", 2);
            REMOVE = observeOp3;
            ObserveOp[] observeOpArr = {observeOp, observeOp2, observeOp3};
            $VALUES = observeOpArr;
            EnumEntriesKt.enumEntries(observeOpArr);
        }

        private ObserveOp(String str, int i) {
        }

        public static ObserveOp valueOf(String str) {
            return (ObserveOp) Enum.valueOf(ObserveOp.class, str);
        }

        public static ObserveOp[] values() {
            return (ObserveOp[]) $VALUES.clone();
        }
    }

    public ObservedTableStates(int i) {
        this.tableObserversCount = new long[i];
        this.tableObservedState = new boolean[i];
    }

    public final boolean onObserverAdded$room_runtime_release(int[] iArr) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            boolean z = false;
            for (int i : iArr) {
                long[] jArr = this.tableObserversCount;
                long j = jArr[i];
                jArr[i] = 1 + j;
                if (j == 0) {
                    z = true;
                    this.needsSync = true;
                }
            }
            return z;
        } finally {
            reentrantLock.unlock();
        }
    }

    public final boolean onObserverRemoved$room_runtime_release(int[] iArr) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            boolean z = false;
            for (int i : iArr) {
                long[] jArr = this.tableObserversCount;
                long j = jArr[i];
                jArr[i] = j - 1;
                if (j == 1) {
                    z = true;
                    this.needsSync = true;
                }
            }
            return z;
        } finally {
            reentrantLock.unlock();
        }
    }
}
