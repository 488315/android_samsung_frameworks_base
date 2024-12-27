package com.android.systemui.media.mediaoutput.compose.ext;

import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationVector4D;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.animation.core.TwoWayConverterImpl;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.State;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpRect;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class DpRectExtKt {
    public static final State animateDpRectAsState(DpRect dpRect, SpringSpec springSpec, Function1 function1, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1264146584);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        int i = DpRect.$r8$clinit;
        DpRectExtKt$VectorConverter$1 dpRectExtKt$VectorConverter$1 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.ext.DpRectExtKt$VectorConverter$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                DpRect dpRect2 = (DpRect) obj;
                return new AnimationVector4D(dpRect2.left, dpRect2.top, dpRect2.right, dpRect2.bottom);
            }
        };
        DpRectExtKt$VectorConverter$2 dpRectExtKt$VectorConverter$2 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.ext.DpRectExtKt$VectorConverter$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                AnimationVector4D animationVector4D = (AnimationVector4D) obj;
                float f = animationVector4D.v1;
                Dp.Companion companion = Dp.Companion;
                return new DpRect(f, animationVector4D.v2, animationVector4D.v3, animationVector4D.v4, null);
            }
        };
        TwoWayConverterImpl twoWayConverterImpl = VectorConvertersKt.FloatToVector;
        State animateValueAsState = AnimateAsStateKt.animateValueAsState(dpRect, new TwoWayConverterImpl(dpRectExtKt$VectorConverter$1, dpRectExtKt$VectorConverter$2), springSpec, null, "RectAnimation", function1, composerImpl, 576, 8);
        composerImpl.end(false);
        return animateValueAsState;
    }
}
