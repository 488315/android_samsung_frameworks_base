package androidx.compose.foundation.text.selection;

import androidx.compose.animation.core.AnimationVector2D;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.animation.core.TwoWayConverter;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.ui.geometry.Offset;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SelectionMagnifierKt {
    public static final SpringSpec MagnifierSpringSpec;
    public static final long OffsetDisplacementThreshold;
    public static final AnimationVector2D UnspecifiedAnimationVector2D = new AnimationVector2D(Float.NaN, Float.NaN);
    public static final TwoWayConverter UnspecifiedSafeOffsetVectorConverter = VectorConvertersKt.TwoWayConverter(new Function1() { // from class: androidx.compose.foundation.text.selection.SelectionMagnifierKt$UnspecifiedSafeOffsetVectorConverter$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            long j = ((Offset) obj).packedValue;
            return (9223372034707292159L & j) != 9205357640488583168L ? new AnimationVector2D(Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L))) : SelectionMagnifierKt.UnspecifiedAnimationVector2D;
        }
    }, new Function1() { // from class: androidx.compose.foundation.text.selection.SelectionMagnifierKt$UnspecifiedSafeOffsetVectorConverter$2
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            AnimationVector2D animationVector2D = (AnimationVector2D) obj;
            float f = animationVector2D.v1;
            float f2 = animationVector2D.v2;
            return Offset.m393boximpl((Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32));
        }
    });

    static {
        long floatToRawIntBits = (Float.floatToRawIntBits(0.01f) << 32) | (Float.floatToRawIntBits(0.01f) & 4294967295L);
        Offset.Companion companion = Offset.Companion;
        OffsetDisplacementThreshold = floatToRawIntBits;
        MagnifierSpringSpec = new SpringSpec(0.0f, 0.0f, Offset.m393boximpl(floatToRawIntBits), 3, null);
    }
}
