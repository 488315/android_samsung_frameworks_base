package kotlinx.coroutines.internal;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.AtomicArray;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicLong;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.atomicfu.TraceBase;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LockFreeTaskQueueCore {
    public static final Companion Companion = new Companion(null);
    public static final Symbol REMOVE_FROZEN = new Symbol("REMOVE_FROZEN");
    public final AtomicRef _next = AtomicFU.atomic((Object) null);
    public final AtomicLong _state = new AtomicLong(0, TraceBase.None.INSTANCE);
    public final AtomicArray array;
    public final int capacity;
    public final int mask;
    public final boolean singleConsumer;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Placeholder {
        public final int index;

        public Placeholder(int i) {
            this.index = i;
        }
    }

    public LockFreeTaskQueueCore(int i, boolean z) {
        this.capacity = i;
        this.singleConsumer = z;
        int i2 = i - 1;
        this.mask = i2;
        this.array = new AtomicArray(i);
        if (!(i2 <= 1073741823)) {
            throw new IllegalStateException("Check failed.".toString());
        }
        if (!((i & i2) == 0)) {
            throw new IllegalStateException("Check failed.".toString());
        }
    }

    public final int addLast(Object obj) {
        LockFreeTaskQueueCore lockFreeTaskQueueCore = this;
        AtomicLong atomicLong = lockFreeTaskQueueCore._state;
        while (true) {
            long j = atomicLong.value;
            if ((3458764513820540928L & j) != 0) {
                Companion.getClass();
                return (2305843009213693952L & j) != 0 ? 2 : 1;
            }
            int i = (int) ((1073741823 & j) >> 0);
            int i2 = (int) ((1152921503533105152L & j) >> 30);
            int i3 = lockFreeTaskQueueCore.mask;
            if (((i2 + 2) & i3) == (i & i3)) {
                return 1;
            }
            if (!lockFreeTaskQueueCore.singleConsumer) {
                if (lockFreeTaskQueueCore.array.array[i2 & i3].value != null) {
                    int i4 = lockFreeTaskQueueCore.capacity;
                    if (i4 < 1024 || ((i2 - i) & 1073741823) > (i4 >> 1)) {
                        break;
                    }
                }
            }
            AtomicLong atomicLong2 = lockFreeTaskQueueCore._state;
            Companion.getClass();
            if (atomicLong2.compareAndSet(j, (((i2 + 1) & 1073741823) << 30) | ((-1152921503533105153L) & j))) {
                lockFreeTaskQueueCore.array.array[i2 & i3].setValue(obj);
                while ((lockFreeTaskQueueCore._state.value & 1152921504606846976L) != 0) {
                    lockFreeTaskQueueCore = lockFreeTaskQueueCore.next();
                    Object obj2 = lockFreeTaskQueueCore.array.array[lockFreeTaskQueueCore.mask & i2].value;
                    if ((obj2 instanceof Placeholder) && ((Placeholder) obj2).index == i2) {
                        lockFreeTaskQueueCore.array.array[lockFreeTaskQueueCore.mask & i2].setValue(obj);
                    } else {
                        lockFreeTaskQueueCore = null;
                    }
                    if (lockFreeTaskQueueCore == null) {
                        return 0;
                    }
                }
                return 0;
            }
        }
        return 1;
    }

    public final boolean close() {
        long j;
        AtomicLong atomicLong = this._state;
        do {
            j = atomicLong.value;
            if ((j & 2305843009213693952L) != 0) {
                return true;
            }
            if ((1152921504606846976L & j) != 0) {
                return false;
            }
        } while (!atomicLong.compareAndSet(j, 2305843009213693952L | j));
        return true;
    }

    public final LockFreeTaskQueueCore next() {
        long j;
        AtomicLong atomicLong = this._state;
        while (true) {
            j = atomicLong.value;
            if ((j & 1152921504606846976L) != 0) {
                break;
            }
            long j2 = 1152921504606846976L | j;
            if (atomicLong.compareAndSet(j, j2)) {
                j = j2;
                break;
            }
        }
        AtomicRef atomicRef = this._next;
        while (true) {
            LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) atomicRef.value;
            if (lockFreeTaskQueueCore != null) {
                return lockFreeTaskQueueCore;
            }
            AtomicRef atomicRef2 = this._next;
            LockFreeTaskQueueCore lockFreeTaskQueueCore2 = new LockFreeTaskQueueCore(this.capacity * 2, this.singleConsumer);
            int i = (int) ((1073741823 & j) >> 0);
            int i2 = (int) ((1152921503533105152L & j) >> 30);
            while (true) {
                int i3 = this.mask;
                int i4 = i & i3;
                if (i4 == (i3 & i2)) {
                    break;
                }
                Object obj = this.array.array[i4].value;
                if (obj == null) {
                    obj = new Placeholder(i);
                }
                AtomicArray atomicArray = lockFreeTaskQueueCore2.array;
                atomicArray.array[lockFreeTaskQueueCore2.mask & i].setValue(obj);
                i++;
            }
            AtomicLong atomicLong2 = lockFreeTaskQueueCore2._state;
            Companion.getClass();
            atomicLong2.value = (-1152921504606846977L) & j;
            TraceBase traceBase = atomicLong2.trace;
            if (traceBase != TraceBase.None.INSTANCE) {
                traceBase.getClass();
            }
            atomicRef2.compareAndSet(null, lockFreeTaskQueueCore2);
        }
    }

    public final Object removeFirstOrNull() {
        AtomicLong atomicLong = this._state;
        while (true) {
            long j = atomicLong.value;
            if ((j & 1152921504606846976L) != 0) {
                return REMOVE_FROZEN;
            }
            long j2 = 1073741823;
            int i = (int) ((j & 1073741823) >> 0);
            int i2 = this.mask;
            int i3 = ((int) ((1152921503533105152L & j) >> 30)) & i2;
            int i4 = i2 & i;
            if (i3 == i4) {
                return null;
            }
            Object obj = this.array.array[i4].value;
            if (obj == null) {
                if (this.singleConsumer) {
                    return null;
                }
            } else {
                if (obj instanceof Placeholder) {
                    return null;
                }
                AtomicLong atomicLong2 = this._state;
                Companion.getClass();
                long j3 = ((i + 1) & 1073741823) << 0;
                if (atomicLong2.compareAndSet(j, j3 | (j & (-1073741824)))) {
                    this.array.array[this.mask & i].setValue(null);
                    return obj;
                }
                if (this.singleConsumer) {
                    LockFreeTaskQueueCore lockFreeTaskQueueCore = this;
                    while (true) {
                        AtomicLong atomicLong3 = lockFreeTaskQueueCore._state;
                        while (true) {
                            long j4 = atomicLong3.value;
                            int i5 = (int) ((j4 & j2) >> 0);
                            if ((j4 & 1152921504606846976L) != 0) {
                                lockFreeTaskQueueCore = lockFreeTaskQueueCore.next();
                                break;
                            }
                            AtomicLong atomicLong4 = lockFreeTaskQueueCore._state;
                            Companion.getClass();
                            if (atomicLong4.compareAndSet(j4, j3 | (j4 & (-1073741824)))) {
                                lockFreeTaskQueueCore.array.array[lockFreeTaskQueueCore.mask & i5].setValue(null);
                                lockFreeTaskQueueCore = null;
                                break;
                            }
                            j2 = 1073741823;
                        }
                        if (lockFreeTaskQueueCore == null) {
                            return obj;
                        }
                        j2 = 1073741823;
                    }
                }
            }
        }
    }
}
