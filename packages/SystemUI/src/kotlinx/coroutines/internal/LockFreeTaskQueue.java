package kotlinx.coroutines.internal;

import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LockFreeTaskQueue {
    public final AtomicRef _cur;

    public LockFreeTaskQueue(boolean z) {
        this._cur = AtomicFU.atomic(new LockFreeTaskQueueCore(8, z));
    }

    public final boolean addLast(Object obj) {
        AtomicRef atomicRef = this._cur;
        while (true) {
            LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) atomicRef.value;
            int addLast = lockFreeTaskQueueCore.addLast(obj);
            if (addLast == 0) {
                return true;
            }
            if (addLast == 1) {
                this._cur.compareAndSet(lockFreeTaskQueueCore, lockFreeTaskQueueCore.next());
            } else if (addLast == 2) {
                return false;
            }
        }
    }

    public final int getSize() {
        long j = ((LockFreeTaskQueueCore) this._cur.value)._state.value;
        return 1073741823 & (((int) ((j & 1152921503533105152L) >> 30)) - ((int) (1073741823 & j)));
    }

    public final Object removeFirstOrNull() {
        AtomicRef atomicRef = this._cur;
        while (true) {
            LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) atomicRef.value;
            Object removeFirstOrNull = lockFreeTaskQueueCore.removeFirstOrNull();
            if (removeFirstOrNull != LockFreeTaskQueueCore.REMOVE_FROZEN) {
                return removeFirstOrNull;
            }
            this._cur.compareAndSet(lockFreeTaskQueueCore, lockFreeTaskQueueCore.next());
        }
    }
}
