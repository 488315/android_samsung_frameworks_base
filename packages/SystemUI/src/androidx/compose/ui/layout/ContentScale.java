package androidx.compose.ui.layout;

/* loaded from: classes.dex */
public interface ContentScale {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final ContentScale$Companion$FillBounds$1 FillBounds;
        public static final ContentScale$Companion$FillWidth$1 FillWidth = null;
        public static final ContentScale$Companion$Inside$1 Inside;
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final ContentScale$Companion$Crop$1 Crop = new ContentScale() { // from class: androidx.compose.ui.layout.ContentScale$Companion$Crop$1
            @Override // androidx.compose.ui.layout.ContentScale
            /* renamed from: computeScaleFactor-H7hwNQA */
            public final long mo608computeScaleFactorH7hwNQA(long j, long j2) {
                float fMax = Math.max(Float.intBitsToFloat((int) (j2 >> 32)) / Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j2 & 4294967295L)) / Float.intBitsToFloat((int) (j & 4294967295L)));
                long jFloatToRawIntBits = (Float.floatToRawIntBits(fMax) << 32) | (Float.floatToRawIntBits(fMax) & 4294967295L);
                int i = ScaleFactor.$r8$clinit;
                return jFloatToRawIntBits;
            }
        };
        public static final ContentScale$Companion$Fit$1 Fit = new ContentScale() { // from class: androidx.compose.ui.layout.ContentScale$Companion$Fit$1
            @Override // androidx.compose.ui.layout.ContentScale
            /* renamed from: computeScaleFactor-H7hwNQA */
            public final long mo608computeScaleFactorH7hwNQA(long j, long j2) {
                float fMin = Math.min(Float.intBitsToFloat((int) (j2 >> 32)) / Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j2 & 4294967295L)) / Float.intBitsToFloat((int) (j & 4294967295L)));
                long jFloatToRawIntBits = (Float.floatToRawIntBits(fMin) << 32) | (Float.floatToRawIntBits(fMin) & 4294967295L);
                int i = ScaleFactor.$r8$clinit;
                return jFloatToRawIntBits;
            }
        };

        /* JADX WARN: Type inference failed for: r0v1, types: [androidx.compose.ui.layout.ContentScale$Companion$Crop$1] */
        /* JADX WARN: Type inference failed for: r0v2, types: [androidx.compose.ui.layout.ContentScale$Companion$Fit$1] */
        /* JADX WARN: Type inference failed for: r0v5, types: [androidx.compose.ui.layout.ContentScale$Companion$Inside$1] */
        /* JADX WARN: Type inference failed for: r0v7, types: [androidx.compose.ui.layout.ContentScale$Companion$FillBounds$1] */
        static {
            new ContentScale() { // from class: androidx.compose.ui.layout.ContentScale$Companion$FillHeight$1
                @Override // androidx.compose.ui.layout.ContentScale
                /* renamed from: computeScaleFactor-H7hwNQA */
                public final long mo608computeScaleFactorH7hwNQA(long j, long j2) {
                    float fIntBitsToFloat = Float.intBitsToFloat((int) (j2 & 4294967295L)) / Float.intBitsToFloat((int) (j & 4294967295L));
                    long jFloatToRawIntBits = (Float.floatToRawIntBits(fIntBitsToFloat) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat) & 4294967295L);
                    int i = ScaleFactor.$r8$clinit;
                    return jFloatToRawIntBits;
                }
            };
            new ContentScale() { // from class: androidx.compose.ui.layout.ContentScale$Companion$FillWidth$1
                @Override // androidx.compose.ui.layout.ContentScale
                /* renamed from: computeScaleFactor-H7hwNQA */
                public final long mo608computeScaleFactorH7hwNQA(long j, long j2) {
                    float fIntBitsToFloat = Float.intBitsToFloat((int) (j2 >> 32)) / Float.intBitsToFloat((int) (j >> 32));
                    long jFloatToRawIntBits = (Float.floatToRawIntBits(fIntBitsToFloat) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat) & 4294967295L);
                    int i = ScaleFactor.$r8$clinit;
                    return jFloatToRawIntBits;
                }
            };
            Inside = new ContentScale() { // from class: androidx.compose.ui.layout.ContentScale$Companion$Inside$1
                @Override // androidx.compose.ui.layout.ContentScale
                /* renamed from: computeScaleFactor-H7hwNQA */
                public final long mo608computeScaleFactorH7hwNQA(long j, long j2) {
                    if (Float.intBitsToFloat((int) (j >> 32)) <= Float.intBitsToFloat((int) (j2 >> 32)) && Float.intBitsToFloat((int) (j & 4294967295L)) <= Float.intBitsToFloat((int) (j2 & 4294967295L))) {
                        long jFloatToRawIntBits = (Float.floatToRawIntBits(1.0f) << 32) | (Float.floatToRawIntBits(1.0f) & 4294967295L);
                        int i = ScaleFactor.$r8$clinit;
                        return jFloatToRawIntBits;
                    }
                    float fMin = Math.min(Float.intBitsToFloat((int) (j2 >> 32)) / Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j2 & 4294967295L)) / Float.intBitsToFloat((int) (j & 4294967295L)));
                    long jFloatToRawIntBits2 = (Float.floatToRawIntBits(fMin) << 32) | (Float.floatToRawIntBits(fMin) & 4294967295L);
                    int i2 = ScaleFactor.$r8$clinit;
                    return jFloatToRawIntBits2;
                }
            };
            new FixedScale(1.0f);
            FillBounds = new ContentScale() { // from class: androidx.compose.ui.layout.ContentScale$Companion$FillBounds$1
                @Override // androidx.compose.ui.layout.ContentScale
                /* renamed from: computeScaleFactor-H7hwNQA */
                public final long mo608computeScaleFactorH7hwNQA(long j, long j2) {
                    float fIntBitsToFloat = Float.intBitsToFloat((int) (j2 >> 32)) / Float.intBitsToFloat((int) (j >> 32));
                    float fIntBitsToFloat2 = Float.intBitsToFloat((int) (j2 & 4294967295L)) / Float.intBitsToFloat((int) (j & 4294967295L));
                    long jFloatToRawIntBits = (Float.floatToRawIntBits(fIntBitsToFloat) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L);
                    int i = ScaleFactor.$r8$clinit;
                    return jFloatToRawIntBits;
                }
            };
        }

        private Companion() {
        }
    }

    /* renamed from: computeScaleFactor-H7hwNQA, reason: not valid java name */
    long mo608computeScaleFactorH7hwNQA(long j, long j2);
}
