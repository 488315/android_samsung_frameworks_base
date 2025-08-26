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

/* loaded from: classes4.dex */
public abstract class BasicCheckboxKt {
    /* JADX WARN: Removed duplicated region for block: B:86:0x00fd  */
    /* renamed from: SeslBasicCheckbox-aA_HZ9I, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m3346SeslBasicCheckboxaA_HZ9I(final SeslBasicCheckboxColors seslBasicCheckboxColors, final boolean z, final Function1 function1, final float f, final SeslRecoilNodeFactory seslRecoilNodeFactory, final Modifier modifier, final boolean z2, final MutableInteractionSource mutableInteractionSource, Composer composer, final int i) {
        int i2;
        Function0 function0;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1168628931);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changed(seslBasicCheckboxColors) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changed(z) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changedInstance(function1) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl2.changed(f) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl2.changed(seslRecoilNodeFactory) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl2.changed(modifier) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i2 |= composerImpl2.changed(z2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((12582912 & i) == 0) {
            i2 |= composerImpl2.changed(mutableInteractionSource) ? 8388608 : 4194304;
        }
        if ((4793491 & i2) == 4793490 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslBasicCheckbox (BasicCheckbox.kt:69)");
            }
            ToggleableState toggleableState = z ? ToggleableState.On : ToggleableState.Off;
            composerImpl2.startReplaceGroup(1520304509);
            if (function1 != null) {
                composerImpl2.startReplaceGroup(1520305798);
                boolean z3 = ((i2 & 896) == 256) | ((i2 & 112) == 32);
                Object objRememberedValue = composerImpl2.rememberedValue();
                if (!z3) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new Function0() { // from class: com.samsung.sesl.compose.foundation.BasicCheckboxKt$$ExternalSyntheticLambda3
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                function1.mo781invoke(Boolean.valueOf(!z));
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl2.updateRememberedValue(objRememberedValue);
                    }
                    function0 = (Function0) objRememberedValue;
                    composerImpl2.end(false);
                }
            } else {
                function0 = null;
            }
            Function0 function02 = function0;
            composerImpl2.end(false);
            composerImpl = composerImpl2;
            m3347SeslBasicCheckboxInnerGHTll3U(seslBasicCheckboxColors, toggleableState, function02, seslRecoilNodeFactory, f, modifier, z2, mutableInteractionSource, composerImpl, (i2 & 14) | ((i2 >> 3) & 7168) | (57344 & (i2 << 3)) | (458752 & i2) | (3670016 & i2) | (i2 & 29360128));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.foundation.BasicCheckboxKt$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    SeslBasicCheckboxColors seslBasicCheckboxColors2 = seslBasicCheckboxColors;
                    SeslRecoilNodeFactory seslRecoilNodeFactory2 = seslRecoilNodeFactory;
                    boolean z4 = z2;
                    MutableInteractionSource mutableInteractionSource2 = mutableInteractionSource;
                    BasicCheckboxKt.m3346SeslBasicCheckboxaA_HZ9I(seslBasicCheckboxColors2, z, function1, f, seslRecoilNodeFactory2, modifier, z4, mutableInteractionSource2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: SeslBasicCheckboxInner-GHTll3U, reason: not valid java name */
    public static final void m3347SeslBasicCheckboxInnerGHTll3U(final SeslBasicCheckboxColors seslBasicCheckboxColors, ToggleableState toggleableState, final Function0 function0, final SeslRecoilNodeFactory seslRecoilNodeFactory, final float f, final Modifier modifier, final boolean z, final MutableInteractionSource mutableInteractionSource, Composer composer, final int i) {
        int i2;
        SeslRecoilNodeFactory seslRecoilNodeFactory2;
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
            seslRecoilNodeFactory2 = seslRecoilNodeFactory;
            i2 |= composerImpl2.changed(seslRecoilNodeFactory2) ? 2048 : 1024;
        } else {
            seslRecoilNodeFactory2 = seslRecoilNodeFactory;
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
                Modifier modifierM183triStateToggleableO2vRcR0 = ToggleableKt.m183triStateToggleableO2vRcR0(modifier, toggleableState2, mutableInteractionSource2, seslRecoilNodeFactory2, z, Role.m715boximpl(Role.Checkbox), function0);
                toggleableState2 = toggleableState2;
                modifier2 = modifierM183triStateToggleableO2vRcR0;
            } else {
                modifier2 = modifier;
            }
            Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(modifier2, f);
            boolean z2 = toggleableState2 == ToggleableState.On;
            composerImpl = composerImpl2;
            m3348SeslBasicCheckboxInnervc5YOHI(seslBasicCheckboxColors.checkColor, seslBasicCheckboxColors.circleStrokeColor, z2, modifierM140size3ABfNKs, z, composerImpl, (i3 >> 6) & 57344);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.foundation.BasicCheckboxKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    SeslBasicCheckboxColors seslBasicCheckboxColors2 = seslBasicCheckboxColors;
                    SeslRecoilNodeFactory seslRecoilNodeFactory3 = seslRecoilNodeFactory;
                    boolean z3 = z;
                    MutableInteractionSource mutableInteractionSource3 = mutableInteractionSource;
                    BasicCheckboxKt.m3347SeslBasicCheckboxInnerGHTll3U(seslBasicCheckboxColors2, toggleableState2, function0, seslRecoilNodeFactory3, f, modifier, z3, mutableInteractionSource3, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: SeslBasicCheckboxInner-vc5YOHI, reason: not valid java name */
    public static final void m3348SeslBasicCheckboxInnervc5YOHI(final long j, final long j2, final boolean z, final Modifier modifier, final boolean z2, Composer composer, final int i) {
        int i2;
        Object obj;
        KeyframesSpec keyframesSpecKeyframes;
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
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslBasicCheckboxInner (BasicCheckbox.kt:129)");
            }
            composerImpl2.startReplaceGroup(886364014);
            int i3 = i2 >> 6;
            int i4 = i3 & 14;
            SeslCheckboxDefaults$Common$ScaleTransition.INSTANCE.getClass();
            composerImpl2.startReplaceGroup(112150296);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslCheckboxDefaults.Common.ScaleTransition.animateScale (BasicCheckbox.kt:229)");
            }
            composerImpl2.startReplaceGroup(1387513198);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(z));
                composerImpl2.updateRememberedValue(objRememberedValue);
            }
            MutableState mutableState = (MutableState) objRememberedValue;
            Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, 1387515525);
            if (objM == composer$Companion$Empty$1) {
                objM = AnimatableKt.Animatable(1.0f, 0.01f);
                composerImpl2.updateRememberedValue(objM);
            }
            Animatable animatable = (Animatable) objM;
            composerImpl2.end(false);
            Boolean boolValueOf = Boolean.valueOf(z);
            composerImpl2.startReplaceGroup(1387518380);
            int i5 = i4 ^ 6;
            boolean zChangedInstance = ((i5 > 4 && composerImpl2.changed(z)) || (i3 & 6) == 4) | composerImpl2.changedInstance(animatable);
            Object objRememberedValue2 = composerImpl2.rememberedValue();
            if (zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = new SeslCheckboxDefaults$Common$ScaleTransition$animateScale$1$1(z, animatable, mutableState, null);
                composerImpl2.updateRememberedValue(objRememberedValue2);
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, boolValueOf, (Function2) objRememberedValue2);
            AnimationState animationState = animatable.internalState;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(886368014);
            SeslCheckboxDefaults$RightCheck$PathDataTransition.INSTANCE.getClass();
            composerImpl2.startReplaceGroup(748567003);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslCheckboxDefaults.RightCheck.PathDataTransition.animatePathData (BasicCheckbox.kt:447)");
            }
            composerImpl2.startReplaceGroup(772943272);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslCheckboxDefaults.RightCheck.PathDataTransition.animateFraction (BasicCheckbox.kt:469)");
            }
            State stateAnimateFloatAsState = AnimateAsStateKt.animateFloatAsState(z ? 1.0f : 0.0f, z ? new TweenSpec(134, 149, EasingKt.LinearEasing) : new TweenSpec(300, 16, EasingKt.LinearEasing), "", null, composerImpl2, 3072, 20);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            float fFloatValue = ((Number) stateAnimateFloatAsState.getValue()).floatValue();
            composerImpl2.startReplaceGroup(1976278951);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslCheckboxDefaults.RightCheck.PathDataTransition.animatePathData (BasicCheckbox.kt:453)");
            }
            composerImpl2.startReplaceGroup(1611337578);
            Object objRememberedValue3 = composerImpl2.rememberedValue();
            if (objRememberedValue3 == composer$Companion$Empty$1) {
                objRememberedValue3 = SnapshotStateKt.mutableStateOf$default(EmptyList.INSTANCE);
                composerImpl2.updateRememberedValue(objRememberedValue3);
            }
            final MutableState mutableState2 = (MutableState) objRememberedValue3;
            composerImpl2.end(false);
            Float fValueOf = Float.valueOf(fFloatValue);
            composerImpl2.startReplaceGroup(1611341734);
            boolean zChanged = composerImpl2.changed(fFloatValue);
            Object objRememberedValue4 = composerImpl2.rememberedValue();
            if (zChanged || objRememberedValue4 == composer$Companion$Empty$1) {
                obj = null;
                objRememberedValue4 = new SeslCheckboxDefaults$RightCheck$PathDataTransition$animatePathData$1$1(mutableState2, fFloatValue, null);
                composerImpl2.updateRememberedValue(objRememberedValue4);
            } else {
                obj = null;
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, fValueOf, (Function2) objRememberedValue4);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(886371848);
            SeslCheckboxDefaults$RightCheck$AlphaTransition.INSTANCE.getClass();
            composerImpl2.startReplaceGroup(-2051587073);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslCheckboxDefaults.RightCheck.AlphaTransition.animateAlpha (BasicCheckbox.kt:388)");
            }
            float f = z ? 1.0f : 0.0f;
            if (z) {
                composerImpl2.startReplaceGroup(1649721877);
                composerImpl2.startReplaceGroup(-1747897389);
                Object objRememberedValue5 = composerImpl2.rememberedValue();
                if (objRememberedValue5 == composer$Companion$Empty$1) {
                    final int i6 = 0;
                    objRememberedValue5 = new Function1() { // from class: com.samsung.sesl.compose.foundation.SeslCheckboxDefaults$RightCheck$AlphaTransition$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
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
                    composerImpl2.updateRememberedValue(objRememberedValue5);
                }
                composerImpl2.end(false);
                keyframesSpecKeyframes = AnimationSpecKt.keyframes((Function1) objRememberedValue5);
                composerImpl2.end(false);
            } else {
                composerImpl2.startReplaceGroup(1650049237);
                composerImpl2.startReplaceGroup(-1747886829);
                Object objRememberedValue6 = composerImpl2.rememberedValue();
                if (objRememberedValue6 == composer$Companion$Empty$1) {
                    final int i7 = 1;
                    objRememberedValue6 = new Function1() { // from class: com.samsung.sesl.compose.foundation.SeslCheckboxDefaults$RightCheck$AlphaTransition$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
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
                    composerImpl2.updateRememberedValue(objRememberedValue6);
                }
                composerImpl2.end(false);
                keyframesSpecKeyframes = AnimationSpecKt.keyframes((Function1) objRememberedValue6);
                composerImpl2.end(false);
            }
            final State stateAnimateFloatAsState2 = AnimateAsStateKt.animateFloatAsState(f, keyframesSpecKeyframes, "", null, composerImpl2, 3072, 20);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(886375629);
            SeslCheckboxDefaults$LeftCheck$PathDataTransition.INSTANCE.getClass();
            composerImpl2.startReplaceGroup(-1282332846);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslCheckboxDefaults.LeftCheck.PathDataTransition.animatePathData (BasicCheckbox.kt:333)");
            }
            composerImpl2.startReplaceGroup(-1558641179);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslCheckboxDefaults.LeftCheck.PathDataTransition.animateFraction (BasicCheckbox.kt:358)");
            }
            State stateAnimateFloatAsState3 = AnimateAsStateKt.animateFloatAsState(z ? 1.0f : 0.0f, z ? new TweenSpec(IKnoxCustomManager.Stub.TRANSACTION_setAutoCallPickupState, 82, EasingKt.LinearEasing) : new TweenSpec(116, 184, EasingKt.LinearEasing), "", null, composerImpl2, 3072, 20);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            float fFloatValue2 = ((Number) stateAnimateFloatAsState3.getValue()).floatValue();
            composerImpl2.startReplaceGroup(-411445242);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslCheckboxDefaults.LeftCheck.PathDataTransition.animatePathData (BasicCheckbox.kt:343)");
            }
            composerImpl2.startReplaceGroup(-1677577289);
            Object objRememberedValue7 = composerImpl2.rememberedValue();
            if (objRememberedValue7 == composer$Companion$Empty$1) {
                objRememberedValue7 = SnapshotStateKt.mutableStateOf$default(EmptyList.INSTANCE);
                composerImpl2.updateRememberedValue(objRememberedValue7);
            }
            final MutableState mutableState3 = (MutableState) objRememberedValue7;
            composerImpl2.end(false);
            Float fValueOf2 = Float.valueOf(fFloatValue2);
            composerImpl2.startReplaceGroup(-1677573133);
            boolean zChanged2 = composerImpl2.changed(fFloatValue2);
            Object objRememberedValue8 = composerImpl2.rememberedValue();
            if (zChanged2 || objRememberedValue8 == composer$Companion$Empty$1) {
                continuation = null;
                objRememberedValue8 = new SeslCheckboxDefaults$LeftCheck$PathDataTransition$animatePathData$1$1(mutableState3, fFloatValue2, null);
                composerImpl2.updateRememberedValue(objRememberedValue8);
            } else {
                continuation = null;
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, fValueOf2, (Function2) objRememberedValue8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(886379399);
            SeslCheckboxDefaults$LeftCheck$AlphaTransition.INSTANCE.getClass();
            composerImpl2.startReplaceGroup(1675380910);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslCheckboxDefaults.LeftCheck.AlphaTransition.animateAlpha (BasicCheckbox.kt:265)");
            }
            composerImpl2.startReplaceGroup(270136346);
            Object objRememberedValue9 = composerImpl2.rememberedValue();
            if (objRememberedValue9 == composer$Companion$Empty$1) {
                objRememberedValue9 = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(z));
                composerImpl2.updateRememberedValue(objRememberedValue9);
            }
            MutableState mutableState4 = (MutableState) objRememberedValue9;
            Object objM2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, 270138673);
            if (objM2 == composer$Companion$Empty$1) {
                objM2 = AnimatableKt.Animatable(0.0f, 0.01f);
                composerImpl2.updateRememberedValue(objM2);
            }
            Animatable animatable2 = (Animatable) objM2;
            composerImpl2.end(false);
            Boolean boolValueOf2 = Boolean.valueOf(z);
            composerImpl2.startReplaceGroup(270142253);
            boolean zChangedInstance2 = ((i5 > 4 && composerImpl2.changed(z)) || (i3 & 6) == 4) | composerImpl2.changedInstance(animatable2);
            Object objRememberedValue10 = composerImpl2.rememberedValue();
            if (zChangedInstance2 || objRememberedValue10 == composer$Companion$Empty$1) {
                objRememberedValue10 = new SeslCheckboxDefaults$LeftCheck$AlphaTransition$animateAlpha$1$1(z, animatable2, mutableState4, continuation);
                composerImpl2.updateRememberedValue(objRememberedValue10);
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, boolValueOf2, (Function2) objRememberedValue10);
            final AnimationState animationState2 = animatable2.internalState;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
            composerImpl2.end(false);
            float f2 = z2 ? 1.0f : 0.4f;
            float fFloatValue3 = ((Number) animationState.getValue()).floatValue();
            Modifier modifierScale = ScaleKt.scale(modifier, fFloatValue3, fFloatValue3);
            composerImpl2.startReplaceGroup(886388499);
            boolean zChanged3 = composerImpl2.changed(f2) | ((i2 & 112) == 32) | ((57344 & i2) == 16384) | composerImpl2.changed(mutableState2) | ((i2 & 14) == 4) | composerImpl2.changed(stateAnimateFloatAsState2) | composerImpl2.changed(mutableState3) | composerImpl2.changed(animationState2);
            Object objRememberedValue11 = composerImpl2.rememberedValue();
            if (zChanged3 || objRememberedValue11 == composer$Companion$Empty$1) {
                composerImpl = composerImpl2;
                final float f3 = f2;
                Function1 function1 = new Function1() { // from class: com.samsung.sesl.compose.foundation.BasicCheckboxKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        DrawScope drawScope = (DrawScope) obj2;
                        SeslCheckboxDefaults$OutCircle seslCheckboxDefaults$OutCircle = new Object() { // from class: com.samsung.sesl.compose.foundation.SeslCheckboxDefaults$OutCircle
                            static {
                                StrokeCap.Companion.getClass();
                                StrokeJoin.Companion.getClass();
                            }
                        };
                        long j3 = j2;
                        float fM459getAlphaimpl = Color.m459getAlphaimpl(j3) * 0.8f;
                        float f4 = f3;
                        long jColor = ColorKt.Color(Color.m463getRedimpl(j3), Color.m462getGreenimpl(j3), Color.m460getBlueimpl(j3), fM459getAlphaimpl * f4, Color.m461getColorSpaceimpl(j3));
                        float fM419getWidthimpl = Size.m419getWidthimpl(drawScope.mo547getSizeNHjbRc()) * 0.28125f;
                        float fM419getWidthimpl2 = Size.m419getWidthimpl(drawScope.mo547getSizeNHjbRc()) * 0.046875f;
                        seslCheckboxDefaults$OutCircle.getClass();
                        DrawScope.m534drawCircleVaOC9Bg$default(drawScope, jColor, fM419getWidthimpl, 0L, z2 ? 1.0f : 0.4f, new Stroke(fM419getWidthimpl2, 0.0f, 0, 0, null, 18, null), 0, 100);
                        AndroidPath path = PathParserKt.toPath((List) mutableState2.getValue(), AndroidPath_androidKt.Path());
                        float[] fArrM483constructorimpl$default = Matrix.m483constructorimpl$default();
                        Matrix.m488scaleimpl(Size.m419getWidthimpl(drawScope.mo547getSizeNHjbRc()), Size.m417getHeightimpl(drawScope.mo547getSizeNHjbRc()), fArrM483constructorimpl$default);
                        path.m447transform58bKbWc(fArrM483constructorimpl$default);
                        float fFloatValue4 = ((Number) stateAnimateFloatAsState2.getValue()).floatValue() * f4;
                        long j4 = j;
                        DrawScope.m539drawPathLG529CI$default(drawScope, path, ColorKt.Color(Color.m463getRedimpl(j4), Color.m462getGreenimpl(j4), Color.m460getBlueimpl(j4), fFloatValue4, Color.m461getColorSpaceimpl(j4)), 60);
                        AndroidPath path2 = PathParserKt.toPath((List) mutableState3.getValue(), AndroidPath_androidKt.Path());
                        float[] fArrM483constructorimpl$default2 = Matrix.m483constructorimpl$default();
                        Matrix.m488scaleimpl(Size.m419getWidthimpl(drawScope.mo547getSizeNHjbRc()), Size.m417getHeightimpl(drawScope.mo547getSizeNHjbRc()), fArrM483constructorimpl$default2);
                        path2.m447transform58bKbWc(fArrM483constructorimpl$default2);
                        DrawScope.m539drawPathLG529CI$default(drawScope, path2, ColorKt.Color(Color.m463getRedimpl(j4), Color.m462getGreenimpl(j4), Color.m460getBlueimpl(j4), ((Number) animationState2.getValue()).floatValue() * f4, Color.m461getColorSpaceimpl(j4)), 60);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(function1);
                objRememberedValue11 = function1;
            } else {
                composerImpl = composerImpl2;
            }
            composerImpl.end(false);
            CanvasKt.Canvas(modifierScale, (Function1) objRememberedValue11, composerImpl, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.foundation.BasicCheckboxKt$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    Modifier modifier2 = modifier;
                    boolean z3 = z2;
                    BasicCheckboxKt.m3348SeslBasicCheckboxInnervc5YOHI(j, j2, z, modifier2, z3, (Composer) obj2, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
