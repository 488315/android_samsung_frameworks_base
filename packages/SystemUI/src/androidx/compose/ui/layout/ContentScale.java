package androidx.compose.ui.layout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface ContentScale {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final ContentScale$Companion$FillBounds$1 FillBounds;
        public static final ContentScale$Companion$FillWidth$1 FillWidth = null;
        public static final ContentScale$Companion$Inside$1 Inside;
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final ContentScale$Companion$Crop$1 Crop = new ContentScale() { // from class: androidx.compose.ui.layout.ContentScale$Companion$Crop$1
            @Override // androidx.compose.ui.layout.ContentScale
            /* renamed from: computeScaleFactor-H7hwNQA */
            public final long mo606computeScaleFactorH7hwNQA(long j, long j2) {
                float max = Math.max(Float.intBitsToFloat((int) (j2 >> 32)) / Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j2 & 4294967295L)) / Float.intBitsToFloat((int) (j & 4294967295L)));
                long floatToRawIntBits = (Float.floatToRawIntBits(max) << 32) | (Float.floatToRawIntBits(max) & 4294967295L);
                int i = ScaleFactor.$r8$clinit;
                return floatToRawIntBits;
            }
        };
        public static final ContentScale$Companion$Fit$1 Fit = new ContentScale() { // from class: androidx.compose.ui.layout.ContentScale$Companion$Fit$1
            @Override // androidx.compose.ui.layout.ContentScale
            /* renamed from: computeScaleFactor-H7hwNQA */
            public final long mo606computeScaleFactorH7hwNQA(long j, long j2) {
                float min;
                min = Math.min(Float.intBitsToFloat((int) (j2 >> 32)) / Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j2 & 4294967295L)) / Float.intBitsToFloat((int) (j & 4294967295L)));
                long floatToRawIntBits = (Float.floatToRawIntBits(min) << 32) | (Float.floatToRawIntBits(min) & 4294967295L);
                int i = ScaleFactor.$r8$clinit;
                return floatToRawIntBits;
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
                public final long mo606computeScaleFactorH7hwNQA(long j, long j2) {
                    float intBitsToFloat = Float.intBitsToFloat((int) (j2 & 4294967295L)) / Float.intBitsToFloat((int) (j & 4294967295L));
                    long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat) << 32) | (Float.floatToRawIntBits(intBitsToFloat) & 4294967295L);
                    int i = ScaleFactor.$r8$clinit;
                    return floatToRawIntBits;
                }
            };
            new ContentScale() { // from class: androidx.compose.ui.layout.ContentScale$Companion$FillWidth$1
                @Override // androidx.compose.ui.layout.ContentScale
                /* renamed from: computeScaleFactor-H7hwNQA */
                public final long mo606computeScaleFactorH7hwNQA(long j, long j2) {
                    float intBitsToFloat = Float.intBitsToFloat((int) (j2 >> 32)) / Float.intBitsToFloat((int) (j >> 32));
                    long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat) << 32) | (Float.floatToRawIntBits(intBitsToFloat) & 4294967295L);
                    int i = ScaleFactor.$r8$clinit;
                    return floatToRawIntBits;
                }
            };
            Inside = new ContentScale() { // from class: androidx.compose.ui.layout.ContentScale$Companion$Inside$1
                @Override // androidx.compose.ui.layout.ContentScale
                /* renamed from: computeScaleFactor-H7hwNQA */
                public final long mo606computeScaleFactorH7hwNQA(long j, long j2) {
                    float min;
                    if (Float.intBitsToFloat((int) (j >> 32)) <= Float.intBitsToFloat((int) (j2 >> 32)) && Float.intBitsToFloat((int) (j & 4294967295L)) <= Float.intBitsToFloat((int) (j2 & 4294967295L))) {
                        long floatToRawIntBits = (Float.floatToRawIntBits(1.0f) << 32) | (Float.floatToRawIntBits(1.0f) & 4294967295L);
                        int i = ScaleFactor.$r8$clinit;
                        return floatToRawIntBits;
                    }
                    min = Math.min(Float.intBitsToFloat((int) (j2 >> 32)) / Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j2 & 4294967295L)) / Float.intBitsToFloat((int) (j & 4294967295L)));
                    long floatToRawIntBits2 = (Float.floatToRawIntBits(min) << 32) | (Float.floatToRawIntBits(min) & 4294967295L);
                    int i2 = ScaleFactor.$r8$clinit;
                    return floatToRawIntBits2;
                }
            };
            new FixedScale(1.0f);
            FillBounds = new ContentScale() { // from class: androidx.compose.ui.layout.ContentScale$Companion$FillBounds$1
                @Override // androidx.compose.ui.layout.ContentScale
                /* renamed from: computeScaleFactor-H7hwNQA */
                public final long mo606computeScaleFactorH7hwNQA(long j, long j2) {
                    float intBitsToFloat = Float.intBitsToFloat((int) (j2 >> 32)) / Float.intBitsToFloat((int) (j >> 32));
                    float intBitsToFloat2 = Float.intBitsToFloat((int) (j2 & 4294967295L)) / Float.intBitsToFloat((int) (j & 4294967295L));
                    long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat) << 32) | (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L);
                    int i = ScaleFactor.$r8$clinit;
                    return floatToRawIntBits;
                }
            };
        }

        private Companion() {
        }
    }

    /* renamed from: computeScaleFactor-H7hwNQA, reason: not valid java name */
    long mo606computeScaleFactorH7hwNQA(long j, long j2);
}
