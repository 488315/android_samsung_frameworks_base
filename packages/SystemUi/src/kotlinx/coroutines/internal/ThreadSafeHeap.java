package kotlinx.coroutines.internal;

import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.coroutines.EventLoopImplBase;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ThreadSafeHeap {
    public final AtomicInt _size = AtomicFU.atomic();

    /* renamed from: a */
    public EventLoopImplBase.DelayedTask[] f672a;

    public final void addImpl(EventLoopImplBase.DelayedTask delayedTask) {
        delayedTask.setHeap((EventLoopImplBase.DelayedTaskQueue) this);
        EventLoopImplBase.DelayedTask[] delayedTaskArr = this.f672a;
        if (delayedTaskArr == null) {
            delayedTaskArr = new EventLoopImplBase.DelayedTask[4];
            this.f672a = delayedTaskArr;
        } else if (this._size.value >= delayedTaskArr.length) {
            delayedTaskArr = (EventLoopImplBase.DelayedTask[]) Arrays.copyOf(delayedTaskArr, this._size.value * 2);
            this.f672a = delayedTaskArr;
        }
        int i = this._size.value;
        this._size.setValue(i + 1);
        delayedTaskArr[i] = delayedTask;
        delayedTask.index = i;
        siftUpFrom(i);
    }

    public final EventLoopImplBase.DelayedTask removeAtImpl(int i) {
        Object[] objArr = this.f672a;
        Intrinsics.checkNotNull(objArr);
        this._size.setValue(this._size.value - 1);
        if (i < this._size.value) {
            swap(i, this._size.value);
            int i2 = (i - 1) / 2;
            if (i > 0) {
                EventLoopImplBase.DelayedTask delayedTask = objArr[i];
                Intrinsics.checkNotNull(delayedTask);
                Object obj = objArr[i2];
                Intrinsics.checkNotNull(obj);
                if (delayedTask.compareTo(obj) < 0) {
                    swap(i, i2);
                    siftUpFrom(i2);
                }
            }
            while (true) {
                int i3 = (i * 2) + 1;
                if (i3 >= this._size.value) {
                    break;
                }
                Object[] objArr2 = this.f672a;
                Intrinsics.checkNotNull(objArr2);
                int i4 = i3 + 1;
                if (i4 < this._size.value) {
                    Comparable comparable = objArr2[i4];
                    Intrinsics.checkNotNull(comparable);
                    Object obj2 = objArr2[i3];
                    Intrinsics.checkNotNull(obj2);
                    if (comparable.compareTo(obj2) < 0) {
                        i3 = i4;
                    }
                }
                Comparable comparable2 = objArr2[i];
                Intrinsics.checkNotNull(comparable2);
                Comparable comparable3 = objArr2[i3];
                Intrinsics.checkNotNull(comparable3);
                if (comparable2.compareTo(comparable3) <= 0) {
                    break;
                }
                swap(i, i3);
                i = i3;
            }
        }
        EventLoopImplBase.DelayedTask delayedTask2 = objArr[this._size.value];
        Intrinsics.checkNotNull(delayedTask2);
        delayedTask2.setHeap(null);
        delayedTask2.index = -1;
        objArr[this._size.value] = null;
        return delayedTask2;
    }

    public final void siftUpFrom(int i) {
        while (i > 0) {
            EventLoopImplBase.DelayedTask[] delayedTaskArr = this.f672a;
            Intrinsics.checkNotNull(delayedTaskArr);
            int i2 = (i - 1) / 2;
            EventLoopImplBase.DelayedTask delayedTask = delayedTaskArr[i2];
            Intrinsics.checkNotNull(delayedTask);
            EventLoopImplBase.DelayedTask delayedTask2 = delayedTaskArr[i];
            Intrinsics.checkNotNull(delayedTask2);
            if (delayedTask.compareTo(delayedTask2) <= 0) {
                return;
            }
            swap(i, i2);
            i = i2;
        }
    }

    public final void swap(int i, int i2) {
        EventLoopImplBase.DelayedTask[] delayedTaskArr = this.f672a;
        Intrinsics.checkNotNull(delayedTaskArr);
        EventLoopImplBase.DelayedTask delayedTask = delayedTaskArr[i2];
        Intrinsics.checkNotNull(delayedTask);
        EventLoopImplBase.DelayedTask delayedTask2 = delayedTaskArr[i];
        Intrinsics.checkNotNull(delayedTask2);
        delayedTaskArr[i] = delayedTask;
        delayedTaskArr[i2] = delayedTask2;
        delayedTask.index = i;
        delayedTask2.index = i2;
    }
}
