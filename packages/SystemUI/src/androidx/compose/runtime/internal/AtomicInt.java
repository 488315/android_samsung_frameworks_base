package androidx.compose.runtime.internal;

import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public final class AtomicInt extends AtomicInteger {
    public AtomicInt(int i) {
        super(i);
    }

    @Override // java.lang.Number
    public final byte byteValue() {
        return (byte) super.intValue();
    }

    @Override // java.lang.Number
    public final short shortValue() {
        return (short) super.intValue();
    }
}
