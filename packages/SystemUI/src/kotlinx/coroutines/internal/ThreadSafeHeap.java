package kotlinx.coroutines.internal;

import java.util.Arrays;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.atomicfu.TraceBase;
import kotlinx.coroutines.EventLoopImplBase;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0068, code lost:
    
        if (r5.compareTo(r6) < 0) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final kotlinx.coroutines.EventLoopImplBase.DelayedTask removeAtImpl(int r8) {
        /*
            r7 = this;
            kotlinx.coroutines.EventLoopImplBase$DelayedTask[] r0 = r7.a
            r0.getClass()
            kotlinx.atomicfu.AtomicInt r1 = r7._size
            int r1 = r1.value
            r2 = -1
            int r1 = r1 + r2
            kotlinx.atomicfu.AtomicInt r3 = r7._size
            r3.value = r1
            kotlinx.atomicfu.TraceBase r1 = r3.trace
            kotlinx.atomicfu.TraceBase$None r3 = kotlinx.atomicfu.TraceBase.None.INSTANCE
            if (r1 == r3) goto L18
            r1.getClass()
        L18:
            kotlinx.atomicfu.AtomicInt r1 = r7._size
            int r1 = r1.value
            if (r8 >= r1) goto L82
            kotlinx.atomicfu.AtomicInt r1 = r7._size
            int r1 = r1.value
            r7.swap(r8, r1)
            int r1 = r8 + (-1)
            int r1 = r1 / 2
            if (r8 <= 0) goto L42
            r3 = r0[r8]
            r3.getClass()
            r4 = r0[r1]
            r4.getClass()
            int r3 = r3.compareTo(r4)
            if (r3 >= 0) goto L42
            r7.swap(r8, r1)
            r7.siftUpFrom(r1)
            goto L82
        L42:
            int r1 = r8 * 2
            int r3 = r1 + 1
            kotlinx.atomicfu.AtomicInt r4 = r7._size
            int r4 = r4.value
            if (r3 < r4) goto L4d
            goto L82
        L4d:
            kotlinx.coroutines.EventLoopImplBase$DelayedTask[] r4 = r7.a
            r4.getClass()
            int r1 = r1 + 2
            kotlinx.atomicfu.AtomicInt r5 = r7._size
            int r5 = r5.value
            if (r1 >= r5) goto L6b
            r5 = r4[r1]
            r5.getClass()
            r6 = r4[r3]
            r6.getClass()
            int r5 = r5.compareTo(r6)
            if (r5 >= 0) goto L6b
            goto L6c
        L6b:
            r1 = r3
        L6c:
            r3 = r4[r8]
            r3.getClass()
            r4 = r4[r1]
            r4.getClass()
            int r3 = r3.compareTo(r4)
            if (r3 > 0) goto L7d
            goto L82
        L7d:
            r7.swap(r8, r1)
            r8 = r1
            goto L42
        L82:
            kotlinx.atomicfu.AtomicInt r8 = r7._size
            int r8 = r8.value
            r8 = r0[r8]
            r8.getClass()
            r1 = 0
            r8.setHeap(r1)
            r8.index = r2
            kotlinx.atomicfu.AtomicInt r7 = r7._size
            int r7 = r7.value
            r0[r7] = r1
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.ThreadSafeHeap.removeAtImpl(int):kotlinx.coroutines.EventLoopImplBase$DelayedTask");
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
