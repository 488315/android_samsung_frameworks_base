package kotlin.random;

import java.io.Serializable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class XorWowRandom extends Random implements Serializable {

    @Deprecated
    private static final long serialVersionUID = 0;
    private int addend;

    /* renamed from: v */
    private int f657v;

    /* renamed from: w */
    private int f658w;

    /* renamed from: x */
    private int f659x;

    /* renamed from: y */
    private int f660y;

    /* renamed from: z */
    private int f661z;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public XorWowRandom(int i, int i2, int i3, int i4, int i5, int i6) {
        this.f659x = i;
        this.f660y = i2;
        this.f661z = i3;
        this.f658w = i4;
        this.f657v = i5;
        this.addend = i6;
        int i7 = i | i2 | i3 | i4 | i5;
        if (!(i7 != 0)) {
            throw new IllegalArgumentException("Initial state must have at least one non-zero element.".toString());
        }
        for (int i8 = 0; i8 < 64; i8++) {
            nextInt();
        }
    }

    @Override // kotlin.random.Random
    public final int nextBits() {
        return (nextInt() >>> 0) & (-1);
    }

    @Override // kotlin.random.Random
    public final int nextInt() {
        int i = this.f659x;
        int i2 = i ^ (i >>> 2);
        this.f659x = this.f660y;
        this.f660y = this.f661z;
        this.f661z = this.f658w;
        int i3 = this.f657v;
        this.f658w = i3;
        int i4 = ((i2 ^ (i2 << 1)) ^ i3) ^ (i3 << 4);
        this.f657v = i4;
        int i5 = this.addend + 362437;
        this.addend = i5;
        return i4 + i5;
    }

    public XorWowRandom(int i, int i2) {
        this(i, i2, 0, 0, ~i, (i << 10) ^ (i2 >>> 4));
    }
}
