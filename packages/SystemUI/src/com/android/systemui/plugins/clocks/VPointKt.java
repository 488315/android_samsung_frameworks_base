package com.android.systemui.plugins.clocks;

import kotlin.ULong;

/* loaded from: classes2.dex */
public final class VPointKt {
    private static final long X_MASK = -4294967296L;
    private static final long Y_MASK = 4294967295L;

    /* JADX INFO: Access modifiers changed from: private */
    public static final long pack(int i, int i2) {
        long j = i;
        int i3 = ULong.$r8$clinit;
        return (i2 & Y_MASK) | ((j << 32) & X_MASK);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: unpackX-VKZWuLQ, reason: not valid java name */
    public static final int m2798unpackXVKZWuLQ(long j) {
        long j2 = j & X_MASK;
        int i = ULong.$r8$clinit;
        return (int) (j2 >>> 32);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: unpackY-VKZWuLQ, reason: not valid java name */
    public static final int m2799unpackYVKZWuLQ(long j) {
        long j2 = j & Y_MASK;
        int i = ULong.$r8$clinit;
        return (int) j2;
    }
}
