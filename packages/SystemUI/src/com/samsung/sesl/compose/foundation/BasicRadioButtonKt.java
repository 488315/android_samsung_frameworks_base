package com.samsung.sesl.compose.foundation;

import androidx.compose.animation.SingleValueAnimationKt;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.AnimationState;
import androidx.compose.animation.core.CubicBezierEasing;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.SnapSpec;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.foundation.CanvasKt;
import androidx.compose.foundation.Indication;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.selection.ToggleableKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.state.ToggleableState;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.sesl.compose.foundation.SeslRadioButtonDefaults$InCircle;
import com.samsung.sesl.compose.foundation.SeslRadioButtonDefaults$OutCircle;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class BasicRadioButtonKt {
    /* renamed from: SeslBasicRadioButton-aA_HZ9I, reason: not valid java name */
    public static final void m3331SeslBasicRadioButtonaA_HZ9I(final SeslBasicRadioButtonColors seslBasicRadioButtonColors, final boolean z, final Function0 function0, final float f, final Indication indication, final Modifier modifier, final MutableInteractionSource mutableInteractionSource, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-862721154);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changed(seslBasicRadioButtonColors) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changed(z) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changedInstance(function0) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl2.changed(f) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl2.changed(indication) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl2.changed(modifier) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i2 |= composerImpl2.changed(true) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((12582912 & i) == 0) {
            i2 |= composerImpl2.changed(mutableInteractionSource) ? 8388608 : 4194304;
        }
        if ((4793491 & i2) == 4793490 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslBasicRadioButton (BasicRadioButton.kt:67)");
            }
            composerImpl = composerImpl2;
            m3332SeslBasicRadioButtonInneraA_HZ9I(seslBasicRadioButtonColors, z ? ToggleableState.On : ToggleableState.Off, function0, f, indication, modifier, mutableInteractionSource, composerImpl, i2 & 33554318);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.foundation.BasicRadioButtonKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    SeslBasicRadioButtonColors seslBasicRadioButtonColors2 = SeslBasicRadioButtonColors.this;
                    Indication indication2 = indication;
                    Modifier modifier2 = modifier;
                    MutableInteractionSource mutableInteractionSource2 = mutableInteractionSource;
                    BasicRadioButtonKt.m3331SeslBasicRadioButtonaA_HZ9I(seslBasicRadioButtonColors2, z, function0, f, indication2, modifier2, mutableInteractionSource2, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void SeslBasicRadioButtonInner(final boolean z, final SeslBasicRadioButtonColors seslBasicRadioButtonColors, final Modifier modifier, Composer composer, final int i) {
        int i2;
        int i3;
        boolean z2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1794773089);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(z) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(seslBasicRadioButtonColors) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(true) ? 2048 : 1024;
        }
        if ((i2 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslBasicRadioButtonInner (BasicRadioButton.kt:129)");
            }
            composerImpl.startReplaceGroup(-405873653);
            int i4 = i2 & 14;
            SeslRadioButtonDefaults$InCircle.ScaleTransition.INSTANCE.getClass();
            composerImpl.startReplaceGroup(847449030);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslRadioButtonDefaults.InCircle.ScaleTransition.animateScale (BasicRadioButton.kt:295)");
            }
            final State animateFloatAsState = AnimateAsStateKt.animateFloatAsState(z ? 1.0f : 0.0f, new TweenSpec(IKnoxCustomManager.Stub.TRANSACTION_removeWidget, 0, SeslRadioButtonDefaults$InCircle.ScaleTransition.easing), "", null, composerImpl, 3072, 20);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-405869488);
            SeslRadioButtonDefaults$OutCircle.ColorTransition.INSTANCE.getClass();
            composerImpl.startReplaceGroup(-570263887);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslRadioButtonDefaults.OutCircle.ColorTransition.animateColor (BasicRadioButton.kt:265)");
            }
            final State m7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(z ? seslBasicRadioButtonColors.checkedColor : seslBasicRadioButtonColors.uncheckedColor, z ? new SnapSpec(116) : new SnapSpec(183), "", composerImpl, 384, 8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-405861556);
            SeslRadioButtonDefaults$OutCircle.ScaleTransition.INSTANCE.getClass();
            composerImpl.startReplaceGroup(-976209391);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslRadioButtonDefaults.OutCircle.ScaleTransition.animateScale (BasicRadioButton.kt:234)");
            }
            composerImpl.startReplaceGroup(-361145629);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(z));
                composerImpl.updateRememberedValue(rememberedValue);
            }
            MutableState mutableState = (MutableState) rememberedValue;
            Object m = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, -361143302);
            if (m == composer$Companion$Empty$1) {
                m = AnimatableKt.Animatable(1.0f, 0.01f);
                composerImpl.updateRememberedValue(m);
            }
            Animatable animatable = (Animatable) m;
            composerImpl.end(false);
            Boolean valueOf = Boolean.valueOf(z);
            composerImpl.startReplaceGroup(-361140467);
            boolean changedInstance = composerImpl.changedInstance(animatable) | (((i4 ^ 6) > 4 && composerImpl.changed(z)) || (i2 & 6) == 4);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new SeslRadioButtonDefaults$OutCircle$ScaleTransition$animateScale$1$1(z, animatable, mutableState, null);
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, valueOf, (Function2) rememberedValue2);
            final AnimationState animationState = animatable.internalState;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-405857357);
            SeslRadioButtonDefaults$OutCircle.StrokeTransition.INSTANCE.getClass();
            composerImpl.startReplaceGroup(1428900962);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslRadioButtonDefaults.OutCircle.StrokeTransition.animateStrokeRatio (BasicRadioButton.kt:214)");
            }
            final State animateFloatAsState2 = AnimateAsStateKt.animateFloatAsState(z ? 0.0625f : 0.046875f, AnimationSpecKt.tween$default(IKnoxCustomManager.Stub.TRANSACTION_migrateApplicationRestrictions, 0, EasingKt.LinearEasing, 2), "", null, composerImpl, 3072, 20);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-405849647);
            final float f = 1.0f;
            boolean changed = composerImpl.changed(animationState) | composerImpl.changed(m7animateColorAsStateeuL9pac) | composerImpl.changed(animateFloatAsState2) | composerImpl.changed(1.0f) | composerImpl.changed(animateFloatAsState) | ((i2 & 112) == 32);
            Object rememberedValue3 = composerImpl.rememberedValue();
            if (changed || rememberedValue3 == composer$Companion$Empty$1) {
                i3 = i2;
                z2 = false;
                Function1 function1 = new Function1() { // from class: com.samsung.sesl.compose.foundation.BasicRadioButtonKt$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        long Color;
                        AnimationState animationState2 = AnimationState.this;
                        float f2 = f;
                        State state = m7animateColorAsStateeuL9pac;
                        State state2 = animateFloatAsState2;
                        State state3 = animateFloatAsState;
                        SeslBasicRadioButtonColors seslBasicRadioButtonColors2 = seslBasicRadioButtonColors;
                        DrawScope drawScope = (DrawScope) obj;
                        SeslRadioButtonDefaults$OutCircle seslRadioButtonDefaults$OutCircle = new Object() { // from class: com.samsung.sesl.compose.foundation.SeslRadioButtonDefaults$OutCircle

                            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                            public final class ColorTransition {
                                public static final ColorTransition INSTANCE = new ColorTransition();

                                private ColorTransition() {
                                }
                            }

                            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                            public final class ScaleTransition {
                                public static final ScaleTransition INSTANCE = new ScaleTransition();
                                public static final CubicBezierEasing easing = new CubicBezierEasing(0.5f, 0.82f, 0.0f, 1.0f);
                                public static final CubicBezierEasing easingReverse = new CubicBezierEasing(0.1f, 0.12f, 0.0f, 1.0f);

                                private ScaleTransition() {
                                }
                            }

                            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                            public final class StrokeTransition {
                                public static final StrokeTransition INSTANCE = new StrokeTransition();

                                private StrokeTransition() {
                                }
                            }

                            static {
                                StrokeCap.Companion.getClass();
                                StrokeJoin.Companion.getClass();
                            }
                        };
                        CanvasDrawScope$drawContext$1 drawContext = drawScope.getDrawContext();
                        long m526getSizeNHjbRc = drawContext.m526getSizeNHjbRc();
                        drawContext.getCanvas().save();
                        try {
                            r6.m530scale0AR0LA0(((Number) animationState2.getValue()).floatValue(), ((Number) animationState2.getValue()).floatValue(), SizeKt.m420getCenteruvyYCjk(((CanvasDrawScope$drawContext$1) drawContext.transform.$this_asDrawTransform).m526getSizeNHjbRc()));
                            Color = ColorKt.Color(Color.m461getRedimpl(r1), Color.m460getGreenimpl(r1), Color.m458getBlueimpl(r1), 0.8f, Color.m459getColorSpaceimpl(((Color) state.getValue()).value));
                            float m417getWidthimpl = Size.m417getWidthimpl(drawScope.mo545getSizeNHjbRc()) * 0.28125f;
                            float floatValue = ((Number) state2.getValue()).floatValue() * Size.m417getWidthimpl(drawScope.mo545getSizeNHjbRc());
                            seslRadioButtonDefaults$OutCircle.getClass();
                            DrawScope.m532drawCircleVaOC9Bg$default(drawScope, Color, m417getWidthimpl, 0L, f2, new Stroke(floatValue, 0.0f, 0, 0, null, 18, null), 0, 100);
                            BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, m526getSizeNHjbRc);
                            int i5 = SeslRadioButtonDefaults$InCircle.$r8$clinit;
                            drawContext = drawScope.getDrawContext();
                            m526getSizeNHjbRc = drawContext.m526getSizeNHjbRc();
                            drawContext.getCanvas().save();
                            try {
                                r3.m530scale0AR0LA0(((Number) state3.getValue()).floatValue(), ((Number) state3.getValue()).floatValue(), SizeKt.m420getCenteruvyYCjk(((CanvasDrawScope$drawContext$1) drawContext.transform.$this_asDrawTransform).m526getSizeNHjbRc()));
                                DrawScope.m532drawCircleVaOC9Bg$default(drawScope, seslBasicRadioButtonColors2.checkedColor, 0.15625f * Size.m417getWidthimpl(drawScope.mo545getSizeNHjbRc()), 0L, f2, null, 0, 116);
                                BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, m526getSizeNHjbRc);
                                return Unit.INSTANCE;
                            } finally {
                            }
                        } finally {
                        }
                    }
                };
                composerImpl.updateRememberedValue(function1);
                rememberedValue3 = function1;
            } else {
                i3 = i2;
                z2 = false;
            }
            composerImpl.end(z2);
            CanvasKt.Canvas(modifier, (Function1) rememberedValue3, composerImpl, (i3 >> 6) & 14);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.foundation.BasicRadioButtonKt$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    SeslBasicRadioButtonColors seslBasicRadioButtonColors2 = seslBasicRadioButtonColors;
                    Modifier modifier2 = modifier;
                    BasicRadioButtonKt.SeslBasicRadioButtonInner(z, seslBasicRadioButtonColors2, modifier2, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: SeslBasicRadioButtonInner-aA_HZ9I, reason: not valid java name */
    public static final void m3332SeslBasicRadioButtonInneraA_HZ9I(final SeslBasicRadioButtonColors seslBasicRadioButtonColors, final ToggleableState toggleableState, final Function0 function0, final float f, final Indication indication, final Modifier modifier, final MutableInteractionSource mutableInteractionSource, Composer composer, final int i) {
        int i2;
        Modifier modifier2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-160135725);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(seslBasicRadioButtonColors) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(toggleableState) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(f) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(indication) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changed(modifier) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i2 |= composerImpl.changed(true) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((12582912 & i) == 0) {
            i2 |= composerImpl.changed(mutableInteractionSource) ? 8388608 : 4194304;
        }
        int i3 = i2;
        if ((4793491 & i3) == 4793490 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslBasicRadioButtonInner (BasicRadioButton.kt:91)");
            }
            if (function0 != null) {
                Role.Companion.getClass();
                modifier2 = androidx.compose.foundation.layout.SizeKt.m139size3ABfNKs(ToggleableKt.m182triStateToggleableO2vRcR0(modifier, toggleableState, mutableInteractionSource, indication, true, Role.m713boximpl(Role.RadioButton), function0), f);
            } else {
                modifier2 = modifier;
            }
            SeslBasicRadioButtonInner(toggleableState == ToggleableState.On, seslBasicRadioButtonColors, androidx.compose.foundation.layout.SizeKt.m135requiredSize3ABfNKs(modifier2, f), composerImpl, ((i3 << 3) & 112) | ((i3 >> 9) & 7168));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.foundation.BasicRadioButtonKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    SeslBasicRadioButtonColors seslBasicRadioButtonColors2 = SeslBasicRadioButtonColors.this;
                    Indication indication2 = indication;
                    Modifier modifier3 = modifier;
                    MutableInteractionSource mutableInteractionSource2 = mutableInteractionSource;
                    BasicRadioButtonKt.m3332SeslBasicRadioButtonInneraA_HZ9I(seslBasicRadioButtonColors2, toggleableState, function0, f, indication2, modifier3, mutableInteractionSource2, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
