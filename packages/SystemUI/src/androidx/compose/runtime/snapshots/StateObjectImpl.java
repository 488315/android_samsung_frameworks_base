package androidx.compose.runtime.snapshots;

import androidx.compose.runtime.internal.AtomicInt;

/* loaded from: classes.dex */
public abstract class StateObjectImpl implements StateObject {
    public final AtomicInt readerKind = new AtomicInt(0);

    /* renamed from: isReadIn-h_f27i8$runtime_release, reason: not valid java name */
    public final boolean m351isReadInh_f27i8$runtime_release(int i) {
        int i2 = this.readerKind.get();
        int i3 = ReaderKind.$r8$clinit;
        return (i2 & i) != 0;
    }

    /* renamed from: recordReadIn-h_f27i8$runtime_release, reason: not valid java name */
    public final void m352recordReadInh_f27i8$runtime_release(int i) {
        AtomicInt atomicInt;
        int i2;
        do {
            atomicInt = this.readerKind;
            i2 = atomicInt.get();
            int i3 = ReaderKind.$r8$clinit;
            if ((i2 & i) != 0) {
                return;
            }
        } while (!atomicInt.compareAndSet(i2, i2 | i));
    }
}
