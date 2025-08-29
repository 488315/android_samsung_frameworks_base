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
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.selection.SelectableKt;
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
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScopeKt$asDrawTransform$1;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.semantics.Role;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.sesl.compose.foundation.SeslRadioButtonDefaults$InCircle;
import com.samsung.sesl.compose.foundation.SeslRadioButtonDefaults$OutCircle;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes4.dex */
public abstract class BasicRadioButtonKt {
    /* renamed from: SeslBasicRadioButton-aA_HZ9I, reason: not valid java name */
    public static final void m3348SeslBasicRadioButtonaA_HZ9I(final SeslBasicRadioButtonColors seslBasicRadioButtonColors, final boolean z, final Function0 function0, final float f, final SeslRecoilNodeFactory seslRecoilNodeFactory, final Modifier modifier, final MutableInteractionSource mutableInteractionSource, Composer composer, final int i) {
        int i2;
        Modifier modifierM140size3ABfNKs;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-862721154);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(seslBasicRadioButtonColors) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(z) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(f) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(seslRecoilNodeFactory) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
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
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslBasicRadioButton (BasicRadioButton.kt:66)");
            }
            if (function0 != null) {
                Role.Companion.getClass();
                modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(SelectableKt.m182selectableO2vRcR0(modifier, z, mutableInteractionSource, seslRecoilNodeFactory, true, Role.m715boximpl(Role.RadioButton), function0), f);
            } else {
                modifierM140size3ABfNKs = modifier;
            }
            SeslBasicRadioButtonInner(z, seslBasicRadioButtonColors, SizeKt.m136requiredSize3ABfNKs(modifierM140size3ABfNKs, f), composerImpl, ((i3 >> 3) & 14) | ((i3 << 3) & 112) | ((i3 >> 9) & 7168));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.foundation.BasicRadioButtonKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    SeslBasicRadioButtonColors seslBasicRadioButtonColors2 = seslBasicRadioButtonColors;
                    SeslRecoilNodeFactory seslRecoilNodeFactory2 = seslRecoilNodeFactory;
                    Modifier modifier2 = modifier;
                    MutableInteractionSource mutableInteractionSource2 = mutableInteractionSource;
                    BasicRadioButtonKt.m3348SeslBasicRadioButtonaA_HZ9I(seslBasicRadioButtonColors2, z, function0, f, seslRecoilNodeFactory2, modifier2, mutableInteractionSource2, (Composer) obj, iUpdateChangedFlags);
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
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslBasicRadioButtonInner (BasicRadioButton.kt:104)");
            }
            composerImpl.startReplaceGroup(-405898645);
            int i4 = i2 & 14;
            SeslRadioButtonDefaults$InCircle.ScaleTransition.INSTANCE.getClass();
            composerImpl.startReplaceGroup(847449030);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslRadioButtonDefaults.InCircle.ScaleTransition.animateScale (BasicRadioButton.kt:270)");
            }
            final State stateAnimateFloatAsState = AnimateAsStateKt.animateFloatAsState(z ? 1.0f : 0.0f, new TweenSpec(IKnoxCustomManager.Stub.TRANSACTION_removeWidget, 0, SeslRadioButtonDefaults$InCircle.ScaleTransition.easing), "", null, composerImpl, 3072, 20);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-405894480);
            SeslRadioButtonDefaults$OutCircle.ColorTransition.INSTANCE.getClass();
            composerImpl.startReplaceGroup(-570263887);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslRadioButtonDefaults.OutCircle.ColorTransition.animateColor (BasicRadioButton.kt:240)");
            }
            final State stateM7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(z ? seslBasicRadioButtonColors.checkedColor : seslBasicRadioButtonColors.uncheckedColor, z ? new SnapSpec(116) : new SnapSpec(183), "", composerImpl, 384, 8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-405886548);
            SeslRadioButtonDefaults$OutCircle.ScaleTransition.INSTANCE.getClass();
            composerImpl.startReplaceGroup(-976209391);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslRadioButtonDefaults.OutCircle.ScaleTransition.animateScale (BasicRadioButton.kt:209)");
            }
            composerImpl.startReplaceGroup(-361170621);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(z));
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            MutableState mutableState = (MutableState) objRememberedValue;
            Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, -361168294);
            if (objM == composer$Companion$Empty$1) {
                objM = AnimatableKt.Animatable(1.0f, 0.01f);
                composerImpl.updateRememberedValue(objM);
            }
            Animatable animatable = (Animatable) objM;
            composerImpl.end(false);
            Boolean boolValueOf = Boolean.valueOf(z);
            composerImpl.startReplaceGroup(-361165459);
            boolean zChangedInstance = composerImpl.changedInstance(animatable) | (((i4 ^ 6) > 4 && composerImpl.changed(z)) || (i2 & 6) == 4);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = new SeslRadioButtonDefaults$OutCircle$ScaleTransition$animateScale$1$1(z, animatable, mutableState, null);
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, boolValueOf, (Function2) objRememberedValue2);
            final AnimationState animationState = animatable.internalState;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-405882349);
            SeslRadioButtonDefaults$OutCircle.StrokeTransition.INSTANCE.getClass();
            composerImpl.startReplaceGroup(1428900962);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslRadioButtonDefaults.OutCircle.StrokeTransition.animateStrokeRatio (BasicRadioButton.kt:189)");
            }
            final State stateAnimateFloatAsState2 = AnimateAsStateKt.animateFloatAsState(z ? 0.0625f : 0.046875f, AnimationSpecKt.tween$default(IKnoxCustomManager.Stub.TRANSACTION_migrateApplicationRestrictions, 0, EasingKt.LinearEasing, 2), "", null, composerImpl, 3072, 20);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-405874639);
            final float f = 1.0f;
            boolean zChanged = composerImpl.changed(animationState) | composerImpl.changed(stateM7animateColorAsStateeuL9pac) | composerImpl.changed(stateAnimateFloatAsState2) | composerImpl.changed(1.0f) | composerImpl.changed(stateAnimateFloatAsState) | ((i2 & 112) == 32);
            Object objRememberedValue3 = composerImpl.rememberedValue();
            if (zChanged || objRememberedValue3 == composer$Companion$Empty$1) {
                i3 = i2;
                z2 = false;
                Function1 function1 = new Function1() { // from class: com.samsung.sesl.compose.foundation.BasicRadioButtonKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        AnimationState animationState2 = animationState;
                        float f2 = f;
                        State state = stateM7animateColorAsStateeuL9pac;
                        State state2 = stateAnimateFloatAsState2;
                        State state3 = stateAnimateFloatAsState;
                        SeslBasicRadioButtonColors seslBasicRadioButtonColors2 = seslBasicRadioButtonColors;
                        DrawScope drawScope = (DrawScope) obj;
                        SeslRadioButtonDefaults$OutCircle seslRadioButtonDefaults$OutCircle = new Object() { // from class: com.samsung.sesl.compose.foundation.SeslRadioButtonDefaults$OutCircle

                            public final class ColorTransition {
                                public static final ColorTransition INSTANCE = new ColorTransition();

                                private ColorTransition() {
                                }
                            }

                            public final class ScaleTransition {
                                public static final ScaleTransition INSTANCE = new ScaleTransition();
                                public static final CubicBezierEasing easing = new CubicBezierEasing(0.5f, 0.82f, 0.0f, 1.0f);
                                public static final CubicBezierEasing easingReverse = new CubicBezierEasing(0.1f, 0.12f, 0.0f, 1.0f);

                                private ScaleTransition() {
                                }
                            }

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
                        long jM528getSizeNHjbRc = drawContext.m528getSizeNHjbRc();
                        drawContext.getCanvas().save();
                        try {
                            CanvasDrawScopeKt$asDrawTransform$1 canvasDrawScopeKt$asDrawTransform$1 = drawContext.transform;
                            canvasDrawScopeKt$asDrawTransform$1.m532scale0AR0LA0(((Number) animationState2.getValue()).floatValue(), ((Number) animationState2.getValue()).floatValue(), androidx.compose.ui.geometry.SizeKt.m422getCenteruvyYCjk(((CanvasDrawScope$drawContext$1) canvasDrawScopeKt$asDrawTransform$1.$this_asDrawTransform).m528getSizeNHjbRc()));
                            long j = ((Color) state.getValue()).value;
                            long jColor = ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), 0.8f, Color.m461getColorSpaceimpl(j));
                            float fM419getWidthimpl = Size.m419getWidthimpl(drawScope.mo547getSizeNHjbRc()) * 0.28125f;
                            float fFloatValue = ((Number) state2.getValue()).floatValue() * Size.m419getWidthimpl(drawScope.mo547getSizeNHjbRc());
                            seslRadioButtonDefaults$OutCircle.getClass();
                            DrawScope.m534drawCircleVaOC9Bg$default(drawScope, jColor, fM419getWidthimpl, 0L, f2, new Stroke(fFloatValue, 0.0f, 0, 0, null, 18, null), 0, 100);
                            BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, jM528getSizeNHjbRc);
                            int i5 = SeslRadioButtonDefaults$InCircle.$r8$clinit;
                            drawContext = drawScope.getDrawContext();
                            jM528getSizeNHjbRc = drawContext.m528getSizeNHjbRc();
                            drawContext.getCanvas().save();
                            try {
                                CanvasDrawScopeKt$asDrawTransform$1 canvasDrawScopeKt$asDrawTransform$12 = drawContext.transform;
                                canvasDrawScopeKt$asDrawTransform$12.m532scale0AR0LA0(((Number) state3.getValue()).floatValue(), ((Number) state3.getValue()).floatValue(), androidx.compose.ui.geometry.SizeKt.m422getCenteruvyYCjk(((CanvasDrawScope$drawContext$1) canvasDrawScopeKt$asDrawTransform$12.$this_asDrawTransform).m528getSizeNHjbRc()));
                                DrawScope.m534drawCircleVaOC9Bg$default(drawScope, seslBasicRadioButtonColors2.checkedColor, 0.15625f * Size.m419getWidthimpl(drawScope.mo547getSizeNHjbRc()), 0L, f2, null, 0, 116);
                                BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, jM528getSizeNHjbRc);
                                return Unit.INSTANCE;
                            } finally {
                            }
                        } finally {
                        }
                    }
                };
                composerImpl.updateRememberedValue(function1);
                objRememberedValue3 = function1;
            } else {
                i3 = i2;
                z2 = false;
            }
            composerImpl.end(z2);
            CanvasKt.Canvas(modifier, (Function1) objRememberedValue3, composerImpl, (i3 >> 6) & 14);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.foundation.BasicRadioButtonKt$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    SeslBasicRadioButtonColors seslBasicRadioButtonColors2 = seslBasicRadioButtonColors;
                    Modifier modifier2 = modifier;
                    BasicRadioButtonKt.SeslBasicRadioButtonInner(z, seslBasicRadioButtonColors2, modifier2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
