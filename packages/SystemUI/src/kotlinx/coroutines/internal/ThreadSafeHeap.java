package kotlinx.coroutines.internal;

import java.util.Arrays;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.atomicfu.TraceBase;
import kotlinx.coroutines.EventLoopImplBase;

/* loaded from: classes4.dex */
public class ThreadSafeHeap {
    public final AtomicInt _size = AtomicFU.atomic(0);
    public EventLoopImplBase.DelayedTask[] a;

    public final void addImpl(EventLoopImplBase.DelayedTask delayedTask) {
        delayedTask.setHeap((EventLoopImplBase.DelayedTaskQueue) this);
        EventLoopImplBase.DelayedTask[] delayedTaskArr = this.a;
        if (delayedTaskArr == null) {
            delayedTaskArr = new EventLoopImplBase.DelayedTask[4];
            this.a = delayedTaskArr;
        } else if (this._size.value >= delayedTaskArr.length) {
            delayedTaskArr = (EventLoopImplBase.DelayedTask[]) Arrays.copyOf(delayedTaskArr, this._size.value * 2);
            this.a = delayedTaskArr;
        }
        int i = this._size.value;
        AtomicInt atomicInt = this._size;
        atomicInt.value = i + 1;
        TraceBase traceBase = atomicInt.trace;
        if (traceBase != TraceBase.None.INSTANCE) {
            traceBase.getClass();
        }
        delayedTaskArr[i] = delayedTask;
        delayedTask.index = i;
        siftUpFrom(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:29:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final EventLoopImplBase.DelayedTask removeAtImpl(int i) {
        EventLoopImplBase.DelayedTask[] delayedTaskArr = this.a;
        delayedTaskArr.getClass();
        int i2 = this._size.value - 1;
        AtomicInt atomicInt = this._size;
        atomicInt.value = i2;
        TraceBase traceBase = atomicInt.trace;
        if (traceBase != TraceBase.None.INSTANCE) {
            traceBase.getClass();
        }
        if (i < this._size.value) {
            swap(i, this._size.value);
            int i3 = (i - 1) / 2;
            if (i > 0) {
                EventLoopImplBase.DelayedTask delayedTask = delayedTaskArr[i];
                delayedTask.getClass();
                EventLoopImplBase.DelayedTask delayedTask2 = delayedTaskArr[i3];
                delayedTask2.getClass();
                if (delayedTask.compareTo(delayedTask2) < 0) {
                    swap(i, i3);
                    siftUpFrom(i3);
                } else {
                    while (true) {
                        int i4 = i * 2;
                        int i5 = i4 + 1;
                        if (i5 >= this._size.value) {
                            break;
                        }
                        EventLoopImplBase.DelayedTask[] delayedTaskArr2 = this.a;
                        delayedTaskArr2.getClass();
                        int i6 = i4 + 2;
                        if (i6 < this._size.value) {
                            EventLoopImplBase.DelayedTask delayedTask3 = delayedTaskArr2[i6];
                            delayedTask3.getClass();
                            EventLoopImplBase.DelayedTask delayedTask4 = delayedTaskArr2[i5];
                            delayedTask4.getClass();
                            if (delayedTask3.compareTo(delayedTask4) >= 0) {
                                i6 = i5;
                            }
                            EventLoopImplBase.DelayedTask delayedTask5 = delayedTaskArr2[i];
                            delayedTask5.getClass();
                            EventLoopImplBase.DelayedTask delayedTask6 = delayedTaskArr2[i6];
                            delayedTask6.getClass();
                            if (delayedTask5.compareTo(delayedTask6) <= 0) {
                                break;
                            }
                            swap(i, i6);
                            i = i6;
                        }
                    }
                }
            }
        }
        EventLoopImplBase.DelayedTask delayedTask7 = delayedTaskArr[this._size.value];
        delayedTask7.getClass();
        delayedTask7.setHeap(null);
        delayedTask7.index = -1;
        delayedTaskArr[this._size.value] = null;
        return delayedTask7;
    }

    public final void siftUpFrom(int i) {
        while (i > 0) {
            EventLoopImplBase.DelayedTask[] delayedTaskArr = this.a;
            delayedTaskArr.getClass();
            int i2 = (i - 1) / 2;
            EventLoopImplBase.DelayedTask delayedTask = delayedTaskArr[i2];
            delayedTask.getClass();
            EventLoopImplBase.DelayedTask delayedTask2 = delayedTaskArr[i];
            delayedTask2.getClass();
            if (delayedTask.compareTo(delayedTask2) <= 0) {
                return;
            }
            swap(i, i2);
            i = i2;
        }
    }

    public final void swap(int i, int i2) {
        EventLoopImplBase.DelayedTask[] delayedTaskArr = this.a;
        delayedTaskArr.getClass();
        EventLoopImplBase.DelayedTask delayedTask = delayedTaskArr[i2];
        delayedTask.getClass();
        EventLoopImplBase.DelayedTask delayedTask2 = delayedTaskArr[i];
        delayedTask2.getClass();
        delayedTaskArr[i] = delayedTask;
        delayedTaskArr[i2] = delayedTask2;
        delayedTask.index = i;
        delayedTask2.index = i2;
    }
}
