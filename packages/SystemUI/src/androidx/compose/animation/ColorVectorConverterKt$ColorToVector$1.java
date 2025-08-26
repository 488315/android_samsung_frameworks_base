package androidx.compose.animation;

import androidx.compose.animation.core.AnimationVector4D;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.colorspace.ColorSpace;
import androidx.compose.ui.graphics.colorspace.ColorSpaces;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* loaded from: classes.dex */
final class ColorVectorConverterKt$ColorToVector$1 extends Lambda implements Function1 {
    public static final ColorVectorConverterKt$ColorToVector$1 INSTANCE = new ColorVectorConverterKt$ColorToVector$1();

    public ColorVectorConverterKt$ColorToVector$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        final ColorSpace colorSpace = (ColorSpace) obj;
        return VectorConvertersKt.TwoWayConverter(new Function1() { // from class: androidx.compose.animation.ColorVectorConverterKt$ColorToVector$1.1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj2) {
                long j = ((Color) obj2).value;
                ColorSpaces.INSTANCE.getClass();
                long jM457convertvNxB06k = Color.m457convertvNxB06k(j, ColorSpaces.Oklab);
                return new AnimationVector4D(Color.m459getAlphaimpl(jM457convertvNxB06k), Color.m463getRedimpl(jM457convertvNxB06k), Color.m462getGreenimpl(jM457convertvNxB06k), Color.m460getBlueimpl(jM457convertvNxB06k));
            }
        }, new Function1() { // from class: androidx.compose.animation.ColorVectorConverterKt$ColorToVector$1.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj2) {
                AnimationVector4D animationVector4D = (AnimationVector4D) obj2;
                float f = animationVector4D.v2;
                if (f < 0.0f) {
                    f = 0.0f;
                }
                if (f > 1.0f) {
                    f = 1.0f;
                }
                float f2 = animationVector4D.v3;
                if (f2 < -0.5f) {
                    f2 = -0.5f;
                }
                if (f2 > 0.5f) {
                    f2 = 0.5f;
                }
                float f3 = animationVector4D.v4;
                float f4 = f3 >= -0.5f ? f3 : -0.5f;
                float f5 = f4 <= 0.5f ? f4 : 0.5f;
                float f6 = animationVector4D.v1;
                float f7 = f6 >= 0.0f ? f6 : 0.0f;
                float f8 = f7 <= 1.0f ? f7 : 1.0f;
                ColorSpaces.INSTANCE.getClass();
                return Color.m456boximpl(Color.m457convertvNxB06k(ColorKt.Color(f, f2, f5, f8, ColorSpaces.Oklab), colorSpace));
            }
        });
    }
}
