package com.android.systemui.plugins.clocks;

import kotlin.ULong;

/* loaded from: classes2.dex */
public final class VRectKt {
    private static final long BOTTOM_MASK = 65535;
    private static final long LEFT_MASK = -281474976710656L;
    private static final long RIGHT_MASK = 4294901760L;
    private static final long TOP_MASK = 281470681743360L;

    /* JADX INFO: Access modifiers changed from: private */
    public static final long pack(short s, short s2, short s3, short s4) {
        long j = s;
        int i = ULong.$r8$clinit;
        return ((s2 << 32) & TOP_MASK) | ((j << 48) & LEFT_MASK) | ((s3 << 16) & RIGHT_MASK) | (s4 & BOTTOM_MASK);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: unpackBottom-VKZWuLQ, reason: not valid java name */
    public static final short m2856unpackBottomVKZWuLQ(long j) {
        long j2 = j & BOTTOM_MASK;
        int i = ULong.$r8$clinit;
        return (short) j2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: unpackLeft-VKZWuLQ, reason: not valid java name */
    public static final short m2857unpackLeftVKZWuLQ(long j) {
        long j2 = j & LEFT_MASK;
        int i = ULong.$r8$clinit;
        return (short) (j2 >>> 48);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: unpackRight-VKZWuLQ, reason: not valid java name */
    public static final short m2858unpackRightVKZWuLQ(long j) {
        long j2 = j & RIGHT_MASK;
        int i = ULong.$r8$clinit;
        return (short) (j2 >>> 16);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: unpackTop-VKZWuLQ, reason: not valid java name */
    public static final short m2859unpackTopVKZWuLQ(long j) {
        long j2 = j & TOP_MASK;
        int i = ULong.$r8$clinit;
        return (short) (j2 >>> 32);
    }
}
