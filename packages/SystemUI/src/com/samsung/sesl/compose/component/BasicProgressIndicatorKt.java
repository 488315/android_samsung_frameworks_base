package com.samsung.sesl.compose.component;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.CubicBezierEasing;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.InfiniteTransition;
import androidx.compose.animation.core.InfiniteTransitionKt;
import androidx.compose.animation.core.KeyframesSpec;
import androidx.compose.animation.core.RepeatMode;
import androidx.compose.foundation.CanvasKt;
import androidx.compose.foundation.ProgressSemanticsKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes4.dex */
public abstract class BasicProgressIndicatorKt {
    /* renamed from: SeslBasicIndeterminateCircularProgressIndicator-ZO3OeZo, reason: not valid java name */
    public static final void m3337SeslBasicIndeterminateCircularProgressIndicatorZO3OeZo(final float f, final int i, final long j, final long j2, Composer composer, final Modifier.Companion companion) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(188250181);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(j) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(j2) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(f) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(companion) ? 2048 : 1024;
        }
        if ((i2 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslBasicIndeterminateCircularProgressIndicator (BasicProgressIndicator.kt:65)");
            }
            m3338SeslBasicIndeterminateCircularProgressIndicatoryA8G38M(j, j2, f, companion, 0.0f, 0.0f, 0.0f, composerImpl, i2 & 8190);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.BasicProgressIndicatorKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    float f2 = f;
                    BasicProgressIndicatorKt.m3337SeslBasicIndeterminateCircularProgressIndicatorZO3OeZo(f2, iUpdateChangedFlags, j, j2, (Composer) obj, companion);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01d1  */
    /* renamed from: SeslBasicIndeterminateCircularProgressIndicator-yA8G38M, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m3338SeslBasicIndeterminateCircularProgressIndicatoryA8G38M(final long j, final long j2, final float f, final Modifier.Companion companion, float f2, float f3, float f4, Composer composer, final int i) {
        int i2;
        Modifier.Companion companion2;
        float f5;
        int i3;
        float f6;
        float f7;
        final float f8;
        ComposerImpl composerImpl;
        final float f9;
        final float f10;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(358399905);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changed(j) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changed(j2) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changed(f) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            companion2 = companion;
            i2 |= composerImpl2.changed(companion2) ? 2048 : 1024;
        } else {
            companion2 = companion;
        }
        if ((i & 24576) == 0) {
            i2 |= 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= 65536;
        }
        if ((1572864 & i) == 0) {
            i2 |= NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((599187 & i2) == 599186 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            f8 = f2;
            f10 = f3;
            f9 = f4;
            composerImpl = composerImpl2;
        } else {
            composerImpl2.startDefaults();
            if ((i & 1) == 0 || composerImpl2.getDefaultsInvalid()) {
                Dp.Companion companion3 = Dp.Companion;
                f5 = 0.36458f * f;
                i3 = i2 & (-4186113);
                f6 = 0.09375f * f;
                f7 = 0.20833f * f;
            } else {
                composerImpl2.skipToGroupEnd();
                f5 = f3;
                f7 = f4;
                i3 = i2 & (-4186113);
                f6 = f2;
            }
            composerImpl2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslBasicIndeterminateCircularProgressIndicator (BasicProgressIndicator.kt:84)");
            }
            InfiniteTransition infiniteTransitionRememberInfiniteTransition = InfiniteTransitionKt.rememberInfiniteTransition("", composerImpl2, 0);
            float f11 = f5;
            InfiniteTransition.TransitionAnimationState transitionAnimationStateAnimateFloat = InfiniteTransitionKt.animateFloat(infiniteTransitionRememberInfiniteTransition, 0.0f, 360.0f, AnimationSpecKt.m9infiniteRepeatable9IiC70o$default(6, AnimationSpecKt.tween$default(1000, 0, EasingKt.LinearEasing, 2), null, 0L), "", composerImpl2, 29112, 0);
            StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionLocalsKt.LocalDensity;
            final float fMo58toPx0680j_4 = ((Density) composerImpl2.consume(staticProvidableCompositionLocal)).mo58toPx0680j_4(f11);
            Dp.Companion companion4 = Dp.Companion;
            final float fMo58toPx0680j_42 = ((Density) composerImpl2.consume(staticProvidableCompositionLocal)).mo58toPx0680j_4(f11 - f7);
            final float fMo58toPx0680j_43 = ((Density) composerImpl2.consume(staticProvidableCompositionLocal)).mo58toPx0680j_4(f6);
            composerImpl2.startReplaceGroup(634091407);
            boolean zChanged = composerImpl2.changed(fMo58toPx0680j_4) | composerImpl2.changed(fMo58toPx0680j_42);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion companion5 = Composer.Companion;
            if (!zChanged) {
                companion5.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function1() { // from class: com.samsung.sesl.compose.component.BasicProgressIndicatorKt$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            KeyframesSpec.KeyframesSpecConfig keyframesSpecConfig = (KeyframesSpec.KeyframesSpecConfig) obj;
                            keyframesSpecConfig.durationMillis = 1000;
                            float f12 = fMo58toPx0680j_4;
                            KeyframesSpec.KeyframeEntity keyframeEntityAt = keyframesSpecConfig.at(0, Float.valueOf(f12));
                            CubicBezierEasing cubicBezierEasing = com.samsung.sesl.compose.foundation.theme.EasingKt.sineInOut70;
                            keyframeEntityAt.easing = cubicBezierEasing;
                            keyframesSpecConfig.at(500, Float.valueOf(fMo58toPx0680j_42)).easing = cubicBezierEasing;
                            keyframesSpecConfig.at(1000, Float.valueOf(f12)).easing = cubicBezierEasing;
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(objRememberedValue);
                }
                composerImpl2.end(false);
                final InfiniteTransition.TransitionAnimationState transitionAnimationStateAnimateFloat2 = InfiniteTransitionKt.animateFloat(infiniteTransitionRememberInfiniteTransition, fMo58toPx0680j_4, fMo58toPx0680j_42, AnimationSpecKt.m9infiniteRepeatable9IiC70o$default(4, AnimationSpecKt.keyframes((Function1) objRememberedValue), RepeatMode.Restart, 0L), "", composerImpl2, 28680, 0);
                Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(ProgressSemanticsKt.progressSemantics(companion2), f);
                float fFloatValue = ((Number) ((SnapshotMutableStateImpl) transitionAnimationStateAnimateFloat.value$delegate).getValue()).floatValue();
                if (fFloatValue != 0.0f) {
                    modifierM140size3ABfNKs = GraphicsLayerModifierKt.m479graphicsLayer_6ThJ44$default(modifierM140size3ABfNKs, 0.0f, 0.0f, 0.0f, 0.0f, fFloatValue, null, false, 0, 524031);
                }
                Modifier modifier = modifierM140size3ABfNKs;
                composerImpl2.startReplaceGroup(634109030);
                boolean zChanged2 = composerImpl2.changed(fMo58toPx0680j_43) | ((i3 & 14) == 4) | composerImpl2.changed(transitionAnimationStateAnimateFloat2) | ((i3 & 112) == 32);
                Object objRememberedValue2 = composerImpl2.rememberedValue();
                if (!zChanged2) {
                    companion5.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        Function1 function1 = new Function1() { // from class: com.samsung.sesl.compose.component.BasicProgressIndicatorKt$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                DrawScope drawScope = (DrawScope) obj;
                                long jM422getCenteruvyYCjk = androidx.compose.ui.geometry.SizeKt.m422getCenteruvyYCjk(drawScope.mo547getSizeNHjbRc());
                                InfiniteTransition.TransitionAnimationState transitionAnimationState = transitionAnimationStateAnimateFloat2;
                                long jM403plusMKHz9U = Offset.m403plusMKHz9U(jM422getCenteruvyYCjk, OffsetKt.Offset(0.0f, ((Number) transitionAnimationState.getValue()).floatValue()));
                                long j3 = j;
                                float f12 = fMo58toPx0680j_43;
                                DrawScope.m534drawCircleVaOC9Bg$default(drawScope, j3, f12, jM403plusMKHz9U, 0.0f, null, 0, 120);
                                DrawScope.m534drawCircleVaOC9Bg$default(drawScope, j3, f12, Offset.m403plusMKHz9U(androidx.compose.ui.geometry.SizeKt.m422getCenteruvyYCjk(drawScope.mo547getSizeNHjbRc()), OffsetKt.Offset(0.0f, -((Number) transitionAnimationState.getValue()).floatValue())), 0.0f, null, 0, 120);
                                DrawScope.m534drawCircleVaOC9Bg$default(drawScope, j2, f12, Offset.m403plusMKHz9U(androidx.compose.ui.geometry.SizeKt.m422getCenteruvyYCjk(drawScope.mo547getSizeNHjbRc()), OffsetKt.Offset(((Number) transitionAnimationState.getValue()).floatValue(), 0.0f)), 0.0f, null, 0, 120);
                                DrawScope.m534drawCircleVaOC9Bg$default(drawScope, j3, f12, Offset.m403plusMKHz9U(androidx.compose.ui.geometry.SizeKt.m422getCenteruvyYCjk(drawScope.mo547getSizeNHjbRc()), OffsetKt.Offset(-((Number) transitionAnimationState.getValue()).floatValue(), 0.0f)), 0.0f, null, 0, 120);
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl2.updateRememberedValue(function1);
                        objRememberedValue2 = function1;
                    }
                    composerImpl2.end(false);
                    CanvasKt.Canvas(modifier, (Function1) objRememberedValue2, composerImpl2, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    f8 = f6;
                    composerImpl = composerImpl2;
                    f9 = f7;
                    f10 = f11;
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.BasicProgressIndicatorKt$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    float f12 = f10;
                    float f13 = f9;
                    BasicProgressIndicatorKt.m3338SeslBasicIndeterminateCircularProgressIndicatoryA8G38M(j, j2, f, companion, f8, f12, f13, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
