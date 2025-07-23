package com.samsung.sesl.compose.foundation;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.AnimationState;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.KeyframesSpec;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.foundation.CanvasKt;
import androidx.compose.foundation.Indication;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.SizeKt;
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
import androidx.compose.ui.draw.ScaleKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.Matrix;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.graphics.vector.PathParserKt;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.state.ToggleableState;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class BasicCheckboxKt {
    /* JADX WARN: Code restructure failed: missing block: B:84:0x00fb, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L86;
     */
    /* renamed from: SeslBasicCheckbox-aA_HZ9I, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m3328SeslBasicCheckboxaA_HZ9I(final com.samsung.sesl.compose.foundation.SeslBasicCheckboxColors r20, final boolean r21, final kotlin.jvm.functions.Function1 r22, final float r23, final androidx.compose.foundation.Indication r24, final androidx.compose.ui.Modifier r25, final boolean r26, final androidx.compose.foundation.interaction.MutableInteractionSource r27, androidx.compose.runtime.Composer r28, final int r29) {
        /*
            Method dump skipped, instructions count: 344
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.sesl.compose.foundation.BasicCheckboxKt.m3328SeslBasicCheckboxaA_HZ9I(com.samsung.sesl.compose.foundation.SeslBasicCheckboxColors, boolean, kotlin.jvm.functions.Function1, float, androidx.compose.foundation.Indication, androidx.compose.ui.Modifier, boolean, androidx.compose.foundation.interaction.MutableInteractionSource, androidx.compose.runtime.Composer, int):void");
    }

    /* renamed from: SeslBasicCheckboxInner-GHTll3U, reason: not valid java name */
    public static final void m3329SeslBasicCheckboxInnerGHTll3U(final SeslBasicCheckboxColors seslBasicCheckboxColors, ToggleableState toggleableState, final Function0 function0, final Indication indication, final float f, final Modifier modifier, final boolean z, final MutableInteractionSource mutableInteractionSource, Composer composer, final int i) {
        int i2;
        Indication indication2;
        MutableInteractionSource mutableInteractionSource2;
        Modifier modifier2;
        ComposerImpl composerImpl;
        final ToggleableState toggleableState2 = toggleableState;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-119866493);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changed(seslBasicCheckboxColors) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changed(toggleableState2) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changedInstance(function0) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            indication2 = indication;
            i2 |= composerImpl2.changed(indication2) ? 2048 : 1024;
        } else {
            indication2 = indication;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl2.changed(f) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl2.changed(modifier) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i2 |= composerImpl2.changed(z) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((12582912 & i) == 0) {
            mutableInteractionSource2 = mutableInteractionSource;
            i2 |= composerImpl2.changed(mutableInteractionSource2) ? 8388608 : 4194304;
        } else {
            mutableInteractionSource2 = mutableInteractionSource;
        }
        int i3 = i2;
        if ((4793491 & i3) == 4793490 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslBasicCheckboxInner (BasicCheckbox.kt:97)");
            }
            if (function0 != null) {
                Role.Companion.getClass();
                Modifier m182triStateToggleableO2vRcR0 = ToggleableKt.m182triStateToggleableO2vRcR0(modifier, toggleableState2, mutableInteractionSource2, indication2, z, Role.m713boximpl(Role.Checkbox), function0);
                toggleableState2 = toggleableState2;
                modifier2 = SizeKt.m139size3ABfNKs(m182triStateToggleableO2vRcR0, f);
            } else {
                modifier2 = modifier;
            }
            Modifier m139size3ABfNKs = SizeKt.m139size3ABfNKs(modifier2, f);
            boolean z2 = toggleableState2 == ToggleableState.On;
            composerImpl = composerImpl2;
            m3330SeslBasicCheckboxInnervc5YOHI(seslBasicCheckboxColors.checkColor, seslBasicCheckboxColors.circleStrokeColor, z2, m139size3ABfNKs, z, composerImpl, (i3 >> 6) & 57344);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.foundation.BasicCheckboxKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    SeslBasicCheckboxColors seslBasicCheckboxColors2 = SeslBasicCheckboxColors.this;
                    Indication indication3 = indication;
                    boolean z3 = z;
                    MutableInteractionSource mutableInteractionSource3 = mutableInteractionSource;
                    BasicCheckboxKt.m3329SeslBasicCheckboxInnerGHTll3U(seslBasicCheckboxColors2, toggleableState2, function0, indication3, f, modifier, z3, mutableInteractionSource3, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: SeslBasicCheckboxInner-vc5YOHI, reason: not valid java name */
    public static final void m3330SeslBasicCheckboxInnervc5YOHI(final long j, final long j2, final boolean z, final Modifier modifier, final boolean z2, Composer composer, final int i) {
        int i2;
        Object obj;
        KeyframesSpec keyframes;
        Continuation continuation;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(526511459);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changed(j) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changed(j2) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changed(z) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl2.changed(modifier) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl2.changed(z2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((i2 & 9363) == 9362 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslBasicCheckboxInner (BasicCheckbox.kt:130)");
            }
            composerImpl2.startReplaceGroup(886365038);
            int i3 = i2 >> 6;
            int i4 = i3 & 14;
            SeslCheckboxDefaults$Common$ScaleTransition.INSTANCE.getClass();
            composerImpl2.startReplaceGroup(112150296);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslCheckboxDefaults.Common.ScaleTransition.animateScale (BasicCheckbox.kt:230)");
            }
            composerImpl2.startReplaceGroup(1387514222);
            Object rememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(z));
                composerImpl2.updateRememberedValue(rememberedValue);
            }
            MutableState mutableState = (MutableState) rememberedValue;
            Object m = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, 1387516549);
            if (m == composer$Companion$Empty$1) {
                m = AnimatableKt.Animatable(1.0f, 0.01f);
                composerImpl2.updateRememberedValue(m);
            }
            Animatable animatable = (Animatable) m;
            composerImpl2.end(false);
            Boolean valueOf = Boolean.valueOf(z);
            composerImpl2.startReplaceGroup(1387519404);
            int i5 = i4 ^ 6;
            boolean changedInstance = ((i5 > 4 && composerImpl2.changed(z)) || (i3 & 6) == 4) | composerImpl2.changedInstance(animatable);
            Object rememberedValue2 = composerImpl2.rememberedValue();
            if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new SeslCheckboxDefaults$Common$ScaleTransition$animateScale$1$1(z, animatable, mutableState, null);
                composerImpl2.updateRememberedValue(rememberedValue2);
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, valueOf, (Function2) rememberedValue2);
            AnimationState animationState = animatable.internalState;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(886369038);
            SeslCheckboxDefaults$RightCheck$PathDataTransition.INSTANCE.getClass();
            composerImpl2.startReplaceGroup(748567003);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslCheckboxDefaults.RightCheck.PathDataTransition.animatePathData (BasicCheckbox.kt:448)");
            }
            composerImpl2.startReplaceGroup(772943272);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslCheckboxDefaults.RightCheck.PathDataTransition.animateFraction (BasicCheckbox.kt:470)");
            }
            State animateFloatAsState = AnimateAsStateKt.animateFloatAsState(z ? 1.0f : 0.0f, z ? new TweenSpec(134, 149, EasingKt.LinearEasing) : new TweenSpec(300, 16, EasingKt.LinearEasing), "", null, composerImpl2, 3072, 20);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            float floatValue = ((Number) animateFloatAsState.getValue()).floatValue();
            composerImpl2.startReplaceGroup(1976278951);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslCheckboxDefaults.RightCheck.PathDataTransition.animatePathData (BasicCheckbox.kt:454)");
            }
            composerImpl2.startReplaceGroup(1611338602);
            Object rememberedValue3 = composerImpl2.rememberedValue();
            if (rememberedValue3 == composer$Companion$Empty$1) {
                rememberedValue3 = SnapshotStateKt.mutableStateOf$default(EmptyList.INSTANCE);
                composerImpl2.updateRememberedValue(rememberedValue3);
            }
            final MutableState mutableState2 = (MutableState) rememberedValue3;
            composerImpl2.end(false);
            Float valueOf2 = Float.valueOf(floatValue);
            composerImpl2.startReplaceGroup(1611342758);
            boolean changed = composerImpl2.changed(floatValue);
            Object rememberedValue4 = composerImpl2.rememberedValue();
            if (changed || rememberedValue4 == composer$Companion$Empty$1) {
                obj = null;
                rememberedValue4 = new SeslCheckboxDefaults$RightCheck$PathDataTransition$animatePathData$1$1(mutableState2, floatValue, null);
                composerImpl2.updateRememberedValue(rememberedValue4);
            } else {
                obj = null;
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, valueOf2, (Function2) rememberedValue4);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(886372872);
            SeslCheckboxDefaults$RightCheck$AlphaTransition.INSTANCE.getClass();
            composerImpl2.startReplaceGroup(-2051587073);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslCheckboxDefaults.RightCheck.AlphaTransition.animateAlpha (BasicCheckbox.kt:389)");
            }
            float f = z ? 1.0f : 0.0f;
            if (z) {
                composerImpl2.startReplaceGroup(1649753621);
                composerImpl2.startReplaceGroup(-1747896365);
                Object rememberedValue5 = composerImpl2.rememberedValue();
                if (rememberedValue5 == composer$Companion$Empty$1) {
                    final int i6 = 0;
                    rememberedValue5 = new Function1() { // from class: com.samsung.sesl.compose.foundation.SeslCheckboxDefaults$RightCheck$AlphaTransition$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj2) {
                            KeyframesSpec.KeyframesSpecConfig keyframesSpecConfig = (KeyframesSpec.KeyframesSpecConfig) obj2;
                            switch (i6) {
                                case 0:
                                    SeslCheckboxDefaults$RightCheck$AlphaTransition seslCheckboxDefaults$RightCheck$AlphaTransition = SeslCheckboxDefaults$RightCheck$AlphaTransition.INSTANCE;
                                    keyframesSpecConfig.durationMillis = IKnoxCustomManager.Stub.TRANSACTION_migrateApplicationRestrictions;
                                    keyframesSpecConfig.at(0, Float.valueOf(0.0f));
                                    keyframesSpecConfig.at(115, Float.valueOf(0.0f));
                                    keyframesSpecConfig.at(116, Float.valueOf(1.0f));
                                    break;
                                default:
                                    SeslCheckboxDefaults$RightCheck$AlphaTransition seslCheckboxDefaults$RightCheck$AlphaTransition2 = SeslCheckboxDefaults$RightCheck$AlphaTransition.INSTANCE;
                                    keyframesSpecConfig.durationMillis = IKnoxCustomManager.Stub.TRANSACTION_migrateApplicationRestrictions;
                                    keyframesSpecConfig.at(0, Float.valueOf(1.0f));
                                    keyframesSpecConfig.at(183, Float.valueOf(1.0f));
                                    keyframesSpecConfig.at(184, Float.valueOf(0.0f));
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(rememberedValue5);
                }
                composerImpl2.end(false);
                keyframes = AnimationSpecKt.keyframes((Function1) rememberedValue5);
                composerImpl2.end(false);
            } else {
                composerImpl2.startReplaceGroup(1650080981);
                composerImpl2.startReplaceGroup(-1747885805);
                Object rememberedValue6 = composerImpl2.rememberedValue();
                if (rememberedValue6 == composer$Companion$Empty$1) {
                    final int i7 = 1;
                    rememberedValue6 = new Function1() { // from class: com.samsung.sesl.compose.foundation.SeslCheckboxDefaults$RightCheck$AlphaTransition$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj2) {
                            KeyframesSpec.KeyframesSpecConfig keyframesSpecConfig = (KeyframesSpec.KeyframesSpecConfig) obj2;
                            switch (i7) {
                                case 0:
                                    SeslCheckboxDefaults$RightCheck$AlphaTransition seslCheckboxDefaults$RightCheck$AlphaTransition = SeslCheckboxDefaults$RightCheck$AlphaTransition.INSTANCE;
                                    keyframesSpecConfig.durationMillis = IKnoxCustomManager.Stub.TRANSACTION_migrateApplicationRestrictions;
                                    keyframesSpecConfig.at(0, Float.valueOf(0.0f));
                                    keyframesSpecConfig.at(115, Float.valueOf(0.0f));
                                    keyframesSpecConfig.at(116, Float.valueOf(1.0f));
                                    break;
                                default:
                                    SeslCheckboxDefaults$RightCheck$AlphaTransition seslCheckboxDefaults$RightCheck$AlphaTransition2 = SeslCheckboxDefaults$RightCheck$AlphaTransition.INSTANCE;
                                    keyframesSpecConfig.durationMillis = IKnoxCustomManager.Stub.TRANSACTION_migrateApplicationRestrictions;
                                    keyframesSpecConfig.at(0, Float.valueOf(1.0f));
                                    keyframesSpecConfig.at(183, Float.valueOf(1.0f));
                                    keyframesSpecConfig.at(184, Float.valueOf(0.0f));
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(rememberedValue6);
                }
                composerImpl2.end(false);
                keyframes = AnimationSpecKt.keyframes((Function1) rememberedValue6);
                composerImpl2.end(false);
            }
            final State animateFloatAsState2 = AnimateAsStateKt.animateFloatAsState(f, keyframes, "", null, composerImpl2, 3072, 20);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(886376653);
            SeslCheckboxDefaults$LeftCheck$PathDataTransition.INSTANCE.getClass();
            composerImpl2.startReplaceGroup(-1282332846);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslCheckboxDefaults.LeftCheck.PathDataTransition.animatePathData (BasicCheckbox.kt:334)");
            }
            composerImpl2.startReplaceGroup(-1558641179);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslCheckboxDefaults.LeftCheck.PathDataTransition.animateFraction (BasicCheckbox.kt:359)");
            }
            State animateFloatAsState3 = AnimateAsStateKt.animateFloatAsState(z ? 1.0f : 0.0f, z ? new TweenSpec(IKnoxCustomManager.Stub.TRANSACTION_setAutoCallPickupState, 82, EasingKt.LinearEasing) : new TweenSpec(116, 184, EasingKt.LinearEasing), "", null, composerImpl2, 3072, 20);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            float floatValue2 = ((Number) animateFloatAsState3.getValue()).floatValue();
            composerImpl2.startReplaceGroup(-411445242);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslCheckboxDefaults.LeftCheck.PathDataTransition.animatePathData (BasicCheckbox.kt:344)");
            }
            composerImpl2.startReplaceGroup(-1677576265);
            Object rememberedValue7 = composerImpl2.rememberedValue();
            if (rememberedValue7 == composer$Companion$Empty$1) {
                rememberedValue7 = SnapshotStateKt.mutableStateOf$default(EmptyList.INSTANCE);
                composerImpl2.updateRememberedValue(rememberedValue7);
            }
            final MutableState mutableState3 = (MutableState) rememberedValue7;
            composerImpl2.end(false);
            Float valueOf3 = Float.valueOf(floatValue2);
            composerImpl2.startReplaceGroup(-1677572109);
            boolean changed2 = composerImpl2.changed(floatValue2);
            Object rememberedValue8 = composerImpl2.rememberedValue();
            if (changed2 || rememberedValue8 == composer$Companion$Empty$1) {
                continuation = null;
                rememberedValue8 = new SeslCheckboxDefaults$LeftCheck$PathDataTransition$animatePathData$1$1(mutableState3, floatValue2, null);
                composerImpl2.updateRememberedValue(rememberedValue8);
            } else {
                continuation = null;
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, valueOf3, (Function2) rememberedValue8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(886380423);
            SeslCheckboxDefaults$LeftCheck$AlphaTransition.INSTANCE.getClass();
            composerImpl2.startReplaceGroup(1675380910);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslCheckboxDefaults.LeftCheck.AlphaTransition.animateAlpha (BasicCheckbox.kt:266)");
            }
            composerImpl2.startReplaceGroup(270137370);
            Object rememberedValue9 = composerImpl2.rememberedValue();
            if (rememberedValue9 == composer$Companion$Empty$1) {
                rememberedValue9 = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(z));
                composerImpl2.updateRememberedValue(rememberedValue9);
            }
            MutableState mutableState4 = (MutableState) rememberedValue9;
            Object m2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, 270139697);
            if (m2 == composer$Companion$Empty$1) {
                m2 = AnimatableKt.Animatable(0.0f, 0.01f);
                composerImpl2.updateRememberedValue(m2);
            }
            Animatable animatable2 = (Animatable) m2;
            composerImpl2.end(false);
            Boolean valueOf4 = Boolean.valueOf(z);
            composerImpl2.startReplaceGroup(270143277);
            boolean changedInstance2 = ((i5 > 4 && composerImpl2.changed(z)) || (i3 & 6) == 4) | composerImpl2.changedInstance(animatable2);
            Object rememberedValue10 = composerImpl2.rememberedValue();
            if (changedInstance2 || rememberedValue10 == composer$Companion$Empty$1) {
                rememberedValue10 = new SeslCheckboxDefaults$LeftCheck$AlphaTransition$animateAlpha$1$1(z, animatable2, mutableState4, continuation);
                composerImpl2.updateRememberedValue(rememberedValue10);
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, valueOf4, (Function2) rememberedValue10);
            final AnimationState animationState2 = animatable2.internalState;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            composerImpl2.end(false);
            float f2 = z2 ? 1.0f : 0.4f;
            float floatValue3 = ((Number) animationState.getValue()).floatValue();
            Modifier scale = ScaleKt.scale(modifier, floatValue3, floatValue3);
            composerImpl2.startReplaceGroup(886389523);
            boolean changed3 = composerImpl2.changed(f2) | ((i2 & 112) == 32) | ((57344 & i2) == 16384) | composerImpl2.changed(mutableState2) | ((i2 & 14) == 4) | composerImpl2.changed(animateFloatAsState2) | composerImpl2.changed(mutableState3) | composerImpl2.changed(animationState2);
            Object rememberedValue11 = composerImpl2.rememberedValue();
            if (changed3 || rememberedValue11 == composer$Companion$Empty$1) {
                composerImpl = composerImpl2;
                final float f3 = f2;
                Function1 function1 = new Function1() { // from class: com.samsung.sesl.compose.foundation.BasicCheckboxKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        long Color;
                        AndroidPath path;
                        long Color2;
                        AndroidPath path2;
                        long Color3;
                        DrawScope drawScope = (DrawScope) obj2;
                        SeslCheckboxDefaults$OutCircle seslCheckboxDefaults$OutCircle = new Object() { // from class: com.samsung.sesl.compose.foundation.SeslCheckboxDefaults$OutCircle
                            static {
                                StrokeCap.Companion.getClass();
                                StrokeJoin.Companion.getClass();
                            }
                        };
                        long j3 = j2;
                        float m457getAlphaimpl = Color.m457getAlphaimpl(j3) * 0.8f;
                        float f4 = f3;
                        Color = ColorKt.Color(Color.m461getRedimpl(j3), Color.m460getGreenimpl(j3), Color.m458getBlueimpl(j3), m457getAlphaimpl * f4, Color.m459getColorSpaceimpl(j3));
                        float m417getWidthimpl = Size.m417getWidthimpl(drawScope.mo545getSizeNHjbRc()) * 0.28125f;
                        float m417getWidthimpl2 = Size.m417getWidthimpl(drawScope.mo545getSizeNHjbRc()) * 0.046875f;
                        seslCheckboxDefaults$OutCircle.getClass();
                        DrawScope.m532drawCircleVaOC9Bg$default(drawScope, Color, m417getWidthimpl, 0L, z2 ? 1.0f : 0.4f, new Stroke(m417getWidthimpl2, 0.0f, 0, 0, null, 18, null), 0, 100);
                        path = PathParserKt.toPath((List) mutableState2.getValue(), AndroidPath_androidKt.Path());
                        float[] m481constructorimpl$default = Matrix.m481constructorimpl$default();
                        Matrix.m486scaleimpl(Size.m417getWidthimpl(drawScope.mo545getSizeNHjbRc()), Size.m415getHeightimpl(drawScope.mo545getSizeNHjbRc()), m481constructorimpl$default);
                        path.m445transform58bKbWc(m481constructorimpl$default);
                        float floatValue4 = ((Number) animateFloatAsState2.getValue()).floatValue() * f4;
                        long j4 = j;
                        Color2 = ColorKt.Color(Color.m461getRedimpl(j4), Color.m460getGreenimpl(j4), Color.m458getBlueimpl(j4), floatValue4, Color.m459getColorSpaceimpl(j4));
                        DrawScope.m537drawPathLG529CI$default(drawScope, path, Color2, 60);
                        path2 = PathParserKt.toPath((List) mutableState3.getValue(), AndroidPath_androidKt.Path());
                        float[] m481constructorimpl$default2 = Matrix.m481constructorimpl$default();
                        Matrix.m486scaleimpl(Size.m417getWidthimpl(drawScope.mo545getSizeNHjbRc()), Size.m415getHeightimpl(drawScope.mo545getSizeNHjbRc()), m481constructorimpl$default2);
                        path2.m445transform58bKbWc(m481constructorimpl$default2);
                        Color3 = ColorKt.Color(Color.m461getRedimpl(j4), Color.m460getGreenimpl(j4), Color.m458getBlueimpl(j4), ((Number) animationState2.getValue()).floatValue() * f4, Color.m459getColorSpaceimpl(j4));
                        DrawScope.m537drawPathLG529CI$default(drawScope, path2, Color3, 60);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(function1);
                rememberedValue11 = function1;
            } else {
                composerImpl = composerImpl2;
            }
            composerImpl.end(false);
            CanvasKt.Canvas(scale, (Function1) rememberedValue11, composerImpl, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.foundation.BasicCheckboxKt$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    Modifier modifier2 = modifier;
                    boolean z3 = z2;
                    BasicCheckboxKt.m3330SeslBasicCheckboxInnervc5YOHI(j, j2, z, modifier2, z3, (Composer) obj2, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
