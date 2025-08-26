package com.android.systemui.topwindoweffects.ui.compose;

import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.foundation.CanvasKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.State;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Matrix;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScopeKt$asDrawTransform$1;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.VectorPainter;
import androidx.compose.ui.graphics.vector.VectorPainterKt;
import androidx.compose.ui.res.VectorResources_androidKt;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;
import com.android.systemui.lifecycle.SysUiViewModelKt;
import com.android.systemui.topwindoweffects.ui.viewmodel.SqueezeEffectViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public abstract class SqueezeEffectKt {
    public static final long SqueezeColor;
    public static final float SqueezeEffectMaxThickness;

    static {
        Dp.Companion companion = Dp.Companion;
        SqueezeEffectMaxThickness = 12;
        Color.Companion.getClass();
        SqueezeColor = Color.Black;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0122  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SqueezeEffect(final SqueezeEffectViewModel.Factory factory, final Function0 function0, Modifier.Companion companion, Composer composer, final int i) {
        final Modifier.Companion companion2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-864114813);
        int i2 = (composerImpl.changed(factory) ? 4 : 2) | i | (composerImpl.changedInstance(function0) ? 32 : 16) | 384;
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion2 = companion;
        } else {
            companion2 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.topwindoweffects.ui.compose.SqueezeEffect (SqueezeEffect.kt:48)");
            }
            composerImpl.startReplaceGroup(-10756980);
            boolean z = (i2 & 14) == 4;
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion3 = Composer.Companion;
            if (!z) {
                companion3.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function0() { // from class: com.android.systemui.topwindoweffects.ui.compose.SqueezeEffectKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return factory.create();
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                SqueezeEffectViewModel squeezeEffectViewModel = (SqueezeEffectViewModel) SysUiViewModelKt.rememberViewModel("SqueezeEffect", null, (Function0) objRememberedValue, composerImpl, 6, 2);
                boolean zBooleanValue = ((Boolean) ((SnapshotMutableStateImpl) squeezeEffectViewModel.isPowerButtonPressed$delegate).getValue()).booleanValue();
                boolean zBooleanValue2 = ((Boolean) ((SnapshotMutableStateImpl) squeezeEffectViewModel.isPowerButtonLongPressed$delegate).getValue()).booleanValue();
                ImageVector.Companion companion4 = ImageVector.Companion;
                final VectorPainter vectorPainterRememberVectorPainter = VectorPainterKt.rememberVectorPainter(VectorResources_androidKt.vectorResource(R.drawable.rounded_corner_top, composerImpl), composerImpl);
                final VectorPainter vectorPainterRememberVectorPainter2 = VectorPainterKt.rememberVectorPainter(VectorResources_androidKt.vectorResource(R.drawable.rounded_corner_bottom, composerImpl), composerImpl);
                float f = (!zBooleanValue || zBooleanValue2) ? 0.0f : 1.0f;
                TweenSpec tweenSpecTween$default = AnimationSpecKt.tween$default(400, 0, null, 6);
                composerImpl.startReplaceGroup(-10735483);
                boolean z2 = (i2 & 112) == 32;
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!z2) {
                    companion3.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        objRememberedValue2 = new Function1() { // from class: com.android.systemui.topwindoweffects.ui.compose.SqueezeEffectKt$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                ((Float) obj).floatValue();
                                function0.invoke();
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    composerImpl.end(false);
                    final State stateAnimateFloatAsState = AnimateAsStateKt.animateFloatAsState(f, tweenSpecTween$default, null, (Function1) objRememberedValue2, composerImpl, 48, 12);
                    composerImpl = composerImpl;
                    Modifier modifierFillMaxSize = SizeKt.fillMaxSize(companion2, 1.0f);
                    composerImpl.startReplaceGroup(-10731689);
                    boolean zChanged = composerImpl.changed(stateAnimateFloatAsState) | composerImpl.changedInstance(vectorPainterRememberVectorPainter) | composerImpl.changedInstance(vectorPainterRememberVectorPainter2);
                    Object objRememberedValue3 = composerImpl.rememberedValue();
                    if (!zChanged) {
                        companion3.getClass();
                        if (objRememberedValue3 == Composer.Companion.Empty) {
                            objRememberedValue3 = new Function1() { // from class: com.android.systemui.topwindoweffects.ui.compose.SqueezeEffectKt$$ExternalSyntheticLambda2
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj) {
                                    DrawScope drawScope = (DrawScope) obj;
                                    State state = stateAnimateFloatAsState;
                                    if (((Number) state.getValue()).floatValue() <= 0.0f) {
                                        return Unit.INSTANCE;
                                    }
                                    float fFloatValue = ((Number) state.getValue()).floatValue() * drawScope.mo58toPx0680j_4(SqueezeEffectKt.SqueezeEffectMaxThickness);
                                    Size.Companion companion5 = Size.Companion;
                                    long j = SqueezeEffectKt.SqueezeColor;
                                    DrawScope.m541drawRectnJ9OG0$default(drawScope, j, 0L, (Float.floatToRawIntBits(Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() >> 32))) << 32) | (Float.floatToRawIntBits(fFloatValue) & 4294967295L), 0.0f, null, null, 0, 122);
                                    float fIntBitsToFloat = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() & 4294967295L)) - fFloatValue;
                                    Offset.Companion companion6 = Offset.Companion;
                                    DrawScope.m541drawRectnJ9OG0$default(drawScope, j, (Float.floatToRawIntBits(0.0f) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat) & 4294967295L), (Float.floatToRawIntBits(Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() >> 32))) << 32) | (Float.floatToRawIntBits(fFloatValue) & 4294967295L), 0.0f, null, null, 0, 120);
                                    float fIntBitsToFloat2 = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() & 4294967295L));
                                    DrawScope.m541drawRectnJ9OG0$default(drawScope, j, 0L, (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(fFloatValue) << 32), 0.0f, null, null, 0, 122);
                                    float fIntBitsToFloat3 = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() & 4294967295L));
                                    DrawScope.m541drawRectnJ9OG0$default(drawScope, j, (Float.floatToRawIntBits(Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() >> 32)) - fFloatValue) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L), (Float.floatToRawIntBits(fFloatValue) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat3) & 4294967295L), 0.0f, null, null, 0, 120);
                                    VectorPainter vectorPainter = vectorPainterRememberVectorPainter;
                                    SqueezeEffectKt.drawTransform(drawScope, fFloatValue, fFloatValue, 0.0f, vectorPainter);
                                    SqueezeEffectKt.drawTransform(drawScope, Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() >> 32)) - fFloatValue, fFloatValue, 90.0f, vectorPainter);
                                    float fIntBitsToFloat4 = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() & 4294967295L)) - fFloatValue;
                                    VectorPainter vectorPainter2 = vectorPainterRememberVectorPainter2;
                                    SqueezeEffectKt.drawTransform(drawScope, fFloatValue, fIntBitsToFloat4, 270.0f, vectorPainter2);
                                    SqueezeEffectKt.drawTransform(drawScope, Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() >> 32)) - fFloatValue, Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() & 4294967295L)) - fFloatValue, 180.0f, vectorPainter2);
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue3);
                        }
                        composerImpl.end(false);
                        CanvasKt.Canvas(modifierFillMaxSize, (Function1) objRememberedValue3, composerImpl, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(function0, companion2, i) { // from class: com.android.systemui.topwindoweffects.ui.compose.SqueezeEffectKt$$ExternalSyntheticLambda3
                public final /* synthetic */ Function0 f$1;
                public final /* synthetic */ Modifier.Companion f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    Function0 function02 = this.f$1;
                    Modifier.Companion companion5 = this.f$2;
                    SqueezeEffectKt.SqueezeEffect(this.f$0, function02, companion5, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void drawTransform(DrawScope drawScope, float f, float f2, float f3, VectorPainter vectorPainter) {
        CanvasDrawScope$drawContext$1 drawContext = drawScope.getDrawContext();
        long jM528getSizeNHjbRc = drawContext.m528getSizeNHjbRc();
        drawContext.getCanvas().save();
        try {
            CanvasDrawScopeKt$asDrawTransform$1 canvasDrawScopeKt$asDrawTransform$1 = drawContext.transform;
            float[] fArrM483constructorimpl$default = Matrix.m483constructorimpl$default();
            Matrix.m490translateimpl(f, f2, fArrM483constructorimpl$default);
            if (f3 != 0.0f) {
                Matrix.m487rotateZimpl(f3, fArrM483constructorimpl$default);
            }
            ((CanvasDrawScope$drawContext$1) canvasDrawScopeKt$asDrawTransform$1.$this_asDrawTransform).getCanvas().mo427concat58bKbWc(fArrM483constructorimpl$default);
            vectorPainter.m564drawx_KDEd0(drawScope, vectorPainter.mo563getIntrinsicSizeNHjbRc(), 1.0f, null);
        } finally {
            BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, jM528getSizeNHjbRc);
        }
    }
}
