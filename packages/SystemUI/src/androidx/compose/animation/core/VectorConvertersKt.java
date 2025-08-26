package androidx.compose.animation.core;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpOffset;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class VectorConvertersKt {
    public static final TwoWayConverter FloatToVector = new TwoWayConverterImpl(new Function1() { // from class: androidx.compose.animation.core.VectorConvertersKt$FloatToVector$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return new AnimationVector1D(((Number) obj).floatValue());
        }
    }, new Function1() { // from class: androidx.compose.animation.core.VectorConvertersKt$FloatToVector$2
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return Float.valueOf(((AnimationVector1D) obj).value);
        }
    });
    public static final TwoWayConverter IntToVector = new TwoWayConverterImpl(new Function1() { // from class: androidx.compose.animation.core.VectorConvertersKt$IntToVector$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return new AnimationVector1D(((Number) obj).intValue());
        }
    }, new Function1() { // from class: androidx.compose.animation.core.VectorConvertersKt$IntToVector$2
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return Integer.valueOf((int) ((AnimationVector1D) obj).value);
        }
    });
    public static final TwoWayConverter DpToVector = new TwoWayConverterImpl(new Function1() { // from class: androidx.compose.animation.core.VectorConvertersKt$DpToVector$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return new AnimationVector1D(((Dp) obj).value);
        }
    }, new Function1() { // from class: androidx.compose.animation.core.VectorConvertersKt$DpToVector$2
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return Dp.m837boximpl(((AnimationVector1D) obj).value);
        }
    });
    public static final TwoWayConverter DpOffsetToVector = new TwoWayConverterImpl(new Function1() { // from class: androidx.compose.animation.core.VectorConvertersKt$DpOffsetToVector$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            long j = ((DpOffset) obj).packedValue;
            return new AnimationVector2D(DpOffset.m842getXD9Ej5fM(j), DpOffset.m843getYD9Ej5fM(j));
        }
    }, new Function1() { // from class: androidx.compose.animation.core.VectorConvertersKt$DpOffsetToVector$2
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            AnimationVector2D animationVector2D = (AnimationVector2D) obj;
            float f = animationVector2D.v1;
            Dp.Companion companion = Dp.Companion;
            float f2 = animationVector2D.v2;
            return DpOffset.m841boximpl((Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32));
        }
    });
    public static final TwoWayConverter SizeToVector = new TwoWayConverterImpl(new Function1() { // from class: androidx.compose.animation.core.VectorConvertersKt$SizeToVector$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            long j = ((Size) obj).packedValue;
            return new AnimationVector2D(Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)));
        }
    }, new Function1() { // from class: androidx.compose.animation.core.VectorConvertersKt$SizeToVector$2
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            AnimationVector2D animationVector2D = (AnimationVector2D) obj;
            float f = animationVector2D.v1;
            float f2 = animationVector2D.v2;
            return Size.m415boximpl((Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32));
        }
    });
    public static final TwoWayConverter OffsetToVector = new TwoWayConverterImpl(new Function1() { // from class: androidx.compose.animation.core.VectorConvertersKt$OffsetToVector$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            long j = ((Offset) obj).packedValue;
            return new AnimationVector2D(Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)));
        }
    }, new Function1() { // from class: androidx.compose.animation.core.VectorConvertersKt$OffsetToVector$2
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            AnimationVector2D animationVector2D = (AnimationVector2D) obj;
            float f = animationVector2D.v1;
            float f2 = animationVector2D.v2;
            return Offset.m395boximpl((Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32));
        }
    });
    public static final TwoWayConverter IntOffsetToVector = new TwoWayConverterImpl(new Function1() { // from class: androidx.compose.animation.core.VectorConvertersKt$IntOffsetToVector$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            long j = ((IntOffset) obj).packedValue;
            IntOffset.Companion companion = IntOffset.Companion;
            return new AnimationVector2D((int) (j >> 32), (int) (j & 4294967295L));
        }
    }, new Function1() { // from class: androidx.compose.animation.core.VectorConvertersKt$IntOffsetToVector$2
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            AnimationVector2D animationVector2D = (AnimationVector2D) obj;
            return IntOffset.m849boximpl((Math.round(animationVector2D.v2) & 4294967295L) | (Math.round(animationVector2D.v1) << 32));
        }
    });
    public static final TwoWayConverter IntSizeToVector = new TwoWayConverterImpl(new Function1() { // from class: androidx.compose.animation.core.VectorConvertersKt$IntSizeToVector$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            long j = ((IntSize) obj).packedValue;
            return new AnimationVector2D((int) (j >> 32), (int) (j & 4294967295L));
        }
    }, new Function1() { // from class: androidx.compose.animation.core.VectorConvertersKt$IntSizeToVector$2
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            AnimationVector2D animationVector2D = (AnimationVector2D) obj;
            int iRound = Math.round(animationVector2D.v1);
            if (iRound < 0) {
                iRound = 0;
            }
            return IntSize.m861boximpl((iRound << 32) | ((Math.round(animationVector2D.v2) >= 0 ? r5 : 0) & 4294967295L));
        }
    });
    public static final TwoWayConverter RectToVector = new TwoWayConverterImpl(new Function1() { // from class: androidx.compose.animation.core.VectorConvertersKt$RectToVector$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            Rect rect = (Rect) obj;
            return new AnimationVector4D(rect.left, rect.top, rect.right, rect.bottom);
        }
    }, new Function1() { // from class: androidx.compose.animation.core.VectorConvertersKt$RectToVector$2
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            AnimationVector4D animationVector4D = (AnimationVector4D) obj;
            return new Rect(animationVector4D.v1, animationVector4D.v2, animationVector4D.v3, animationVector4D.v4);
        }
    });

    public static final TwoWayConverter TwoWayConverter(Function1 function1, Function1 function12) {
        return new TwoWayConverterImpl(function1, function12);
    }
}
