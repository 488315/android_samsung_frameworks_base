package com.android.bouncer.ui.composable;

import android.content.res.Resources;
import android.view.View;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.SingleValueAnimationKt;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.gestures.PressGestureScope;
import androidx.compose.foundation.gestures.PressGestureScopeImpl;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.AspectRatioElement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.foundation.text.BasicTextKt$$ExternalSyntheticOutline0;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.focus.FocusRequesterModifierKt;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.bouncer.ui.compose.theme.BouncerStyleKt;
import com.android.compose.animation.Easings;
import com.android.compose.animation.Easings$fromInterpolator$1;
import com.android.compose.grid.GridsKt;
import com.android.systemui.R;
import com.android.systemui.bouncer.ui.viewmodel.ActionButtonAppearance;
import com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.ui.compose.IconKt;
import com.android.systemui.media.mediaoutput.compose.theme.TypeKt;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.sesl.compose.foundation.RecoilKt;
import com.samsung.sesl.compose.theme.ThemeKt;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.reflect.KFunction;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;

/* loaded from: classes.dex */
public abstract class SecPinBouncerKt {
    public static final float pinButtonErrorShrinkFactor;
    public static final long pinButtonHoldTime;
    public static final long pinButtonPressedDuration;
    public static final Easings$fromInterpolator$1 pinButtonPressedEasing;
    public static final long pinButtonReleasedDuration;
    public static final Easings$fromInterpolator$1 pinButtonReleasedEasing;

    static {
        Dp.Companion companion = Dp.Companion;
        pinButtonErrorShrinkFactor = 67 / 60;
        Duration.Companion companion2 = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        pinButtonPressedDuration = DurationKt.toDuration(100, durationUnit);
        Easings.INSTANCE.getClass();
        pinButtonPressedEasing = Easings.Linear;
        pinButtonHoldTime = DurationKt.toDuration(33, durationUnit);
        pinButtonReleasedDuration = DurationKt.toDuration(VolteConstants.ErrorCode.BAD_EXTENSION, durationUnit);
        pinButtonReleasedEasing = Easings.Standard;
    }

    /* JADX WARN: Removed duplicated region for block: B:84:0x012d  */
    /* renamed from: ActionButton-mwpFuRA, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m903ActionButtonmwpFuRA(final Icon.Resource resource, final boolean z, final Function0 function0, Function0 function02, final ActionButtonAppearance actionButtonAppearance, final boolean z2, final long j, final float f, final PropertyReference0Impl propertyReference0Impl, Composer composer, final int i, final int i2) {
        int i3;
        Function0 function03;
        int i4;
        final Function0 function04;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1080291265);
        if ((i & 6) == 0) {
            i3 = i | (composerImpl.changed(resource) ? 4 : 2);
        } else {
            i3 = i;
        }
        int i5 = i3 | (composerImpl.changed(z) ? 32 : 16) | (composerImpl.changedInstance(function0) ? 256 : 128);
        int i6 = i2 & 8;
        if (i6 != 0) {
            i4 = i5 | 3072;
            function03 = function02;
        } else {
            function03 = function02;
            i4 = i5 | (composerImpl.changedInstance(function03) ? 2048 : 1024);
        }
        int i7 = i4 | (composerImpl.changed(actionButtonAppearance) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192);
        if ((i & 196608) == 0) {
            i7 |= composerImpl.changed(z2) ? 131072 : 65536;
        }
        int i8 = i7 | (composerImpl.changed(j) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) | (composerImpl.changed(f) ? 8388608 : 4194304) | (composerImpl.changedInstance(propertyReference0Impl) ? 67108864 : 33554432);
        if ((38347923 & i8) == 38347922 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            function04 = function03;
        } else {
            Function0 function05 = i6 != 0 ? null : function03;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.ActionButton (SecPinBouncer.kt:249)");
            }
            boolean z3 = actionButtonAppearance == ActionButtonAppearance.Hidden;
            final State stateAnimateFloatAsState = AnimateAsStateKt.animateFloatAsState(actionButtonAppearance == ActionButtonAppearance.Disable ? 0.33f : 1.0f, null, "Action button alpha", null, composerImpl, 3072, 22);
            Color.Companion.getClass();
            long j2 = Color.Transparent;
            boolean z4 = z && !z3 && z2;
            Modifier.Companion companion = Modifier.Companion;
            composerImpl.startReplaceGroup(-1931796042);
            boolean zChanged = composerImpl.changed(stateAnimateFloatAsState) | ((i8 & 234881024) == 67108864);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChanged) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function1() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$$ExternalSyntheticLambda5
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj);
                            reusableGraphicsLayerScope.setAlpha(((Number) stateAnimateFloatAsState.getValue()).floatValue());
                            float fFloatValue = ((Number) propertyReference0Impl.get()).floatValue();
                            reusableGraphicsLayerScope.setScaleX(fFloatValue);
                            reusableGraphicsLayerScope.setScaleY(fFloatValue);
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                m904PinPadButtonnWoaYo(function0, z4, j2, j, GraphicsLayerModifierKt.graphicsLayer(companion, (Function1) objRememberedValue), function05, f, ComposableLambdaKt.rememberComposableLambda(-1163453630, new Function3() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$ActionButton$2
                    /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
                    @Override // kotlin.jvm.functions.Function3
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        Function0 function06 = (Function0) obj;
                        Composer composer2 = (Composer) obj2;
                        int iIntValue = ((Number) obj3).intValue();
                        if ((iIntValue & 6) == 0) {
                            iIntValue |= ((ComposerImpl) composer2).changedInstance(function06) ? 4 : 2;
                        }
                        if ((iIntValue & 19) == 18) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.bouncer.ui.composable.ActionButton.<anonymous> (SecPinBouncer.kt:271)");
                                }
                                if (resource != null) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    composerImpl3.startReplaceGroup(1284339894);
                                    IconKt.m1074IconFNF3uiM(resource, SizeKt.m144width3ABfNKs(SizeKt.m131height3ABfNKs(Modifier.Companion, PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pin_back_button_size, composerImpl3)), PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pin_back_button_size, composerImpl3)), ((Color) function06.invoke()).value, composerImpl3, 0, 0);
                                    composerImpl3.end(false);
                                } else {
                                    ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                    composerImpl4.startReplaceGroup(1284640749);
                                    composerImpl4.startReplaceGroup(2063959662);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.bouncer.ui.compose.theme.OkTextSytle (BouncerStyle.kt:46)");
                                    }
                                    TextStyle.Companion companion2 = TextStyle.Companion;
                                    TextStyle textStyleM756copyp1EtxEg$default = TextStyle.m756copyp1EtxEg$default(TypeKt.getSecRegular(), 0L, BouncerStyleKt.getDpTextUnit(PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pin_button_ok_text_size, composerImpl4), composerImpl4), null, null, 0L, 0, 0L, null, null, 0, 16777213);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    composerImpl4.end(false);
                                    TextKt.m317Text4IGK_g("OK", null, ((Color) function06.invoke()).value, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, textStyleM756copyp1EtxEg$default, composerImpl4, 6, 0, 65530);
                                    composerImpl4.end(false);
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, ((i8 >> 6) & 14) | 102236544 | ((i8 >> 9) & 7168) | (458752 & (i8 << 6)) | (i8 & 29360128), 0);
                composerImpl = composerImpl;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                function04 = function05;
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    PropertyReference0Impl propertyReference0Impl2 = propertyReference0Impl;
                    SecPinBouncerKt.m903ActionButtonmwpFuRA(resource, z, function0, function04, actionButtonAppearance, z2, j, f, propertyReference0Impl2, (Composer) obj, iUpdateChangedFlags, i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:109:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x016c  */
    /* renamed from: PinPadButton--nWoaYo, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m904PinPadButtonnWoaYo(final Function0 function0, final boolean z, final long j, final long j2, final Modifier modifier, Function0 function02, final float f, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i, final int i2) {
        int i3;
        Function0 function03;
        Object objRememberedValue;
        Object obj;
        final MutableState mutableState;
        View view;
        boolean zChangedInstance;
        Object objRememberedValue2;
        Object objRememberedValue3;
        ComposerImpl composerImpl;
        final Function0 function04;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-40104412);
        if ((i & 6) == 0) {
            i3 = (composerImpl2.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerImpl2.changed(z) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= composerImpl2.changed(j) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i3 |= composerImpl2.changed(j2) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i3 |= composerImpl2.changed(modifier) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        int i4 = i2 & 32;
        if (i4 == 0) {
            if ((196608 & i) == 0) {
                function03 = function02;
                i3 |= composerImpl2.changedInstance(function03) ? 131072 : 65536;
            }
            if ((1572864 & i) == 0) {
                i3 |= composerImpl2.changed(false) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
            }
            if ((12582912 & i) == 0) {
                i3 |= composerImpl2.changed(f) ? 8388608 : 4194304;
            }
            if ((100663296 & i) == 0) {
                i3 |= composerImpl2.changedInstance(composableLambdaImpl) ? 67108864 : 33554432;
            }
            if ((i3 & 38347923) == 38347922 || !composerImpl2.getSkipping()) {
                final Function0 function05 = i4 == 0 ? null : function03;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.bouncer.ui.composable.PinPadButton (SecPinBouncer.kt:301)");
                }
                composerImpl2.startReplaceGroup(-2135107383);
                objRememberedValue = composerImpl2.rememberedValue();
                Composer.Companion.getClass();
                obj = Composer.Companion.Empty;
                if (objRememberedValue == obj) {
                    objRememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                    composerImpl2.updateRememberedValue(objRememberedValue);
                }
                mutableState = (MutableState) objRememberedValue;
                composerImpl2.end(false);
                view = (View) composerImpl2.consume(AndroidCompositionLocals_androidKt.LocalView);
                Boolean bool = (Boolean) mutableState.getValue();
                bool.getClass();
                int i5 = i3;
                composerImpl2.startReplaceGroup(-2135104034);
                zChangedInstance = composerImpl2.changedInstance(view);
                objRememberedValue2 = composerImpl2.rememberedValue();
                if (!zChangedInstance || objRememberedValue2 == obj) {
                    objRememberedValue2 = new SecPinBouncerKt$PinPadButton$1$1(view, mutableState, null);
                    composerImpl2.updateRememberedValue(objRememberedValue2);
                }
                composerImpl2.end(false);
                EffectsKt.LaunchedEffect(composerImpl2, bool, (Function2) objRememberedValue2);
                final float fDimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pin_button_size, composerImpl2);
                Easings$fromInterpolator$1 easings$fromInterpolator$1 = !((Boolean) mutableState.getValue()).booleanValue() ? pinButtonPressedEasing : pinButtonReleasedEasing;
                int iM3464toIntimpl = Duration.m3464toIntimpl(!((Boolean) mutableState.getValue()).booleanValue() ? pinButtonPressedDuration : pinButtonReleasedDuration, DurationUnit.MILLISECONDS);
                final float f2 = fDimensionResource / 2;
                Dp.Companion companion = Dp.Companion;
                final State stateM7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(j2, AnimationSpecKt.tween$default(iM3464toIntimpl, 0, easings$fromInterpolator$1, 2), "Pin button container color", composerImpl2, ((i5 >> 9) & 14) | 384, 8);
                objRememberedValue3 = composerImpl2.rememberedValue();
                if (objRememberedValue3 == obj) {
                    objRememberedValue3 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2);
                    composerImpl2.updateRememberedValue(objRememberedValue3);
                }
                final CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue3;
                final boolean z2 = false;
                ThemeKt.SeslTheme(false, null, ComposableLambdaKt.rememberComposableLambda(236579027, new Function2() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$PinPadButton$2
                    /* JADX WARN: Removed duplicated region for block: B:15:0x0081  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj2, Object obj3) {
                        Composer composer2 = (Composer) obj2;
                        if ((((Number) obj3).intValue() & 3) == 2) {
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            if (composerImpl3.getSkipping()) {
                                composerImpl3.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.bouncer.ui.composable.PinPadButton.<anonymous> (SecPinBouncer.kt:339)");
                                }
                                Alignment.Companion.getClass();
                                BiasAlignment biasAlignment = Alignment.Companion.Center;
                                FocusRequester.Companion.getClass();
                                Modifier modifierFocusable$default = FocusableKt.focusable$default(FocusRequesterModifierKt.focusRequester(modifier, FocusRequester.Default), false, null, 3);
                                float f3 = fDimensionResource;
                                Modifier modifierThen = SizeKt.m131height3ABfNKs(SizeKt.m144width3ABfNKs(modifierFocusable$default, f3), f3).then(new AspectRatioElement(1.0f, false, InspectableValueKt.NoInspectorInfo));
                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                composerImpl4.startReplaceGroup(1895463802);
                                boolean zChanged = composerImpl4.changed(z2);
                                float f4 = f2;
                                boolean zChanged2 = zChanged | composerImpl4.changed(f4) | composerImpl4.changed(j) | composerImpl4.changed(f);
                                Object objRememberedValue4 = composerImpl4.rememberedValue();
                                if (!zChanged2) {
                                    Composer.Companion.getClass();
                                    if (objRememberedValue4 == Composer.Companion.Empty) {
                                        final boolean z3 = z2;
                                        final float f5 = f2;
                                        final long j3 = j;
                                        final float f6 = f;
                                        Function1 function1 = new Function1() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$PinPadButton$2$$ExternalSyntheticLambda0
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj4) {
                                                DrawScope drawScope = (DrawScope) obj4;
                                                if (!z3) {
                                                    float fMo58toPx0680j_4 = drawScope.mo58toPx0680j_4(f5);
                                                    long jFloatToRawIntBits = (Float.floatToRawIntBits(fMo58toPx0680j_4) << 32) | (Float.floatToRawIntBits(fMo58toPx0680j_4) & 4294967295L);
                                                    CornerRadius.Companion companion2 = CornerRadius.Companion;
                                                    DrawScope.m543drawRoundRectuAw5IA$default(drawScope, j3, 0L, 0L, jFloatToRawIntBits, null, f6, IKnoxCustomManager.Stub.TRANSACTION_removeAutoCallNumber);
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        composerImpl4.updateRememberedValue(function1);
                                        objRememberedValue4 = function1;
                                    }
                                    composerImpl4.end(false);
                                    Modifier modifierDrawBehind = DrawModifierKt.drawBehind(modifierThen, (Function1) objRememberedValue4);
                                    boolean z4 = z;
                                    if (z4) {
                                        Modifier.Companion companion2 = Modifier.Companion;
                                        Unit unit = Unit.INSTANCE;
                                        final Function0 function06 = function05;
                                        final CoroutineScope coroutineScope2 = coroutineScope;
                                        final MutableState mutableState2 = mutableState;
                                        final Function0 function07 = function0;
                                        modifierDrawBehind = modifierDrawBehind.then(SuspendingPointerInputFilterKt.pointerInput(companion2, unit, new PointerInputEventHandler() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$PinPadButton$2$2$1

                                            /* renamed from: com.android.bouncer.ui.composable.SecPinBouncerKt$PinPadButton$2$2$1$2, reason: invalid class name */
                                            final class AnonymousClass2 extends SuspendLambda implements Function3 {
                                                final /* synthetic */ MutableState<Boolean> $isPressed$delegate;
                                                final /* synthetic */ CoroutineScope $scope;
                                                private /* synthetic */ Object L$0;
                                                int label;

                                                /* renamed from: com.android.bouncer.ui.composable.SecPinBouncerKt$PinPadButton$2$2$1$2$1, reason: invalid class name */
                                                final class AnonymousClass1 extends SuspendLambda implements Function2 {
                                                    final /* synthetic */ PressGestureScope $$this$detectTapGestures;
                                                    final /* synthetic */ MutableState<Boolean> $isPressed$delegate;
                                                    private /* synthetic */ Object L$0;
                                                    int label;

                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    public AnonymousClass1(PressGestureScope pressGestureScope, MutableState<Boolean> mutableState, Continuation continuation) {
                                                        super(2, continuation);
                                                        this.$$this$detectTapGestures = pressGestureScope;
                                                        this.$isPressed$delegate = mutableState;
                                                    }

                                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                    public final Continuation create(Object obj, Continuation continuation) {
                                                        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$$this$detectTapGestures, this.$isPressed$delegate, continuation);
                                                        anonymousClass1.L$0 = obj;
                                                        return anonymousClass1;
                                                    }

                                                    @Override // kotlin.jvm.functions.Function2
                                                    public final Object invoke(Object obj, Object obj2) {
                                                        return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                                    }

                                                    /* JADX WARN: Code restructure failed: missing block: B:14:0x0052, code lost:
                                                    
                                                        if (r1.await(r6) == r0) goto L15;
                                                     */
                                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                    /*
                                                        Code decompiled incorrectly, please refer to instructions dump.
                                                    */
                                                    public final Object invokeSuspend(Object obj) {
                                                        Deferred deferredAsync$default;
                                                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                                        int i = this.label;
                                                        if (i == 0) {
                                                            ResultKt.throwOnFailure(obj);
                                                            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                                                            MutableState<Boolean> mutableState = this.$isPressed$delegate;
                                                            float f = SecPinBouncerKt.pinButtonErrorShrinkFactor;
                                                            mutableState.setValue(Boolean.TRUE);
                                                            deferredAsync$default = BuildersKt.async$default(coroutineScope, null, new SecPinBouncerKt$PinPadButton$2$2$1$2$1$minDuration$1(null), 3);
                                                            PressGestureScope pressGestureScope = this.$$this$detectTapGestures;
                                                            this.L$0 = deferredAsync$default;
                                                            this.label = 1;
                                                            if (((PressGestureScopeImpl) pressGestureScope).tryAwaitRelease(this) != coroutineSingletons) {
                                                            }
                                                            return coroutineSingletons;
                                                        }
                                                        if (i != 1) {
                                                            if (i != 2) {
                                                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                            }
                                                            ResultKt.throwOnFailure(obj);
                                                            MutableState<Boolean> mutableState2 = this.$isPressed$delegate;
                                                            float f2 = SecPinBouncerKt.pinButtonErrorShrinkFactor;
                                                            mutableState2.setValue(Boolean.FALSE);
                                                            return Unit.INSTANCE;
                                                        }
                                                        deferredAsync$default = (Deferred) this.L$0;
                                                        ResultKt.throwOnFailure(obj);
                                                        this.L$0 = null;
                                                        this.label = 2;
                                                    }
                                                }

                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                public AnonymousClass2(CoroutineScope coroutineScope, MutableState<Boolean> mutableState, Continuation continuation) {
                                                    super(3, continuation);
                                                    this.$scope = coroutineScope;
                                                    this.$isPressed$delegate = mutableState;
                                                }

                                                @Override // kotlin.jvm.functions.Function3
                                                public final Object invoke(Object obj, Object obj2, Object obj3) {
                                                    long j = ((Offset) obj2).packedValue;
                                                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$scope, this.$isPressed$delegate, (Continuation) obj3);
                                                    anonymousClass2.L$0 = (PressGestureScope) obj;
                                                    return anonymousClass2.invokeSuspend(Unit.INSTANCE);
                                                }

                                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                public final Object invokeSuspend(Object obj) {
                                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                                    if (this.label != 0) {
                                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                    }
                                                    ResultKt.throwOnFailure(obj);
                                                    BuildersKt.launch$default(this.$scope, null, null, new AnonymousClass1((PressGestureScope) this.L$0, this.$isPressed$delegate, null), 3);
                                                    return Unit.INSTANCE;
                                                }
                                            }

                                            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                                            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                                                final Function0 function08 = function06;
                                                Function1 function12 = function08 != null ? new Function1() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$PinPadButton$2$2$1$1$1
                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final Object mo781invoke(Object obj4) {
                                                        long j4 = ((Offset) obj4).packedValue;
                                                        function08.invoke();
                                                        return Unit.INSTANCE;
                                                    }
                                                } : null;
                                                AnonymousClass2 anonymousClass2 = new AnonymousClass2(coroutineScope2, mutableState2, null);
                                                final Function0 function09 = function07;
                                                Object objDetectTapGestures$default = TapGestureDetectorKt.detectTapGestures$default(pointerInputScope, null, function12, anonymousClass2, new Function1() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$PinPadButton$2$2$1.3
                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final Object mo781invoke(Object obj4) {
                                                        long j4 = ((Offset) obj4).packedValue;
                                                        function09.invoke();
                                                        return Unit.INSTANCE;
                                                    }
                                                }, continuation, 1);
                                                return objDetectTapGestures$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objDetectTapGestures$default : Unit.INSTANCE;
                                            }
                                        }));
                                    }
                                    Modifier modifierM3351seslRecoilfWhpE4E = RecoilKt.m3351seslRecoilfWhpE4E(ClipKt.clip(modifierDrawBehind, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(f4)), z4, composerImpl4, 0);
                                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl4);
                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
                                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl4, modifierM3351seslRecoilfWhpE4E);
                                    ComposeUiNode.Companion.getClass();
                                    Function0 function08 = ComposeUiNode.Companion.Constructor;
                                    if (composerImpl4.applier == null) {
                                        ComposablesKt.invalidApplier();
                                        throw null;
                                    }
                                    composerImpl4.startReusableNode();
                                    if (composerImpl4.inserting) {
                                        composerImpl4.createNode(function08);
                                    } else {
                                        composerImpl4.useNode();
                                    }
                                    Updater.m337setimpl(composerImpl4, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                    Updater.m337setimpl(composerImpl4, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                    if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function2);
                                    }
                                    Updater.m337setimpl(composerImpl4, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                    final State state = stateM7animateColorAsStateeuL9pac;
                                    composableLambdaImpl.invoke(new PropertyReference0Impl(state) { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$PinPadButton$2$3$1
                                        @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                                        public final Object get() {
                                            return ((State) this.receiver).getValue();
                                        }
                                    }, composerImpl4, 0);
                                    composerImpl4.end(true);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl2), composerImpl2, 384, 3);
                composerImpl = composerImpl2;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                function04 = function05;
            } else {
                composerImpl2.skipToGroupEnd();
                function04 = function03;
                composerImpl = composerImpl2;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$$ExternalSyntheticLambda7
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj2, Object obj3) {
                        ((Integer) obj3).getClass();
                        int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                        ComposableLambdaImpl composableLambdaImpl2 = composableLambdaImpl;
                        SecPinBouncerKt.m904PinPadButtonnWoaYo(function0, z, j, j2, modifier, function04, f, composableLambdaImpl2, (Composer) obj2, iUpdateChangedFlags, i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i3 |= 196608;
        function03 = function02;
        if ((1572864 & i) == 0) {
        }
        if ((12582912 & i) == 0) {
        }
        if ((100663296 & i) == 0) {
        }
        if ((i3 & 38347923) == 38347922) {
            if (i4 == 0) {
            }
            if (ComposerKt.isTraceInProgress()) {
            }
            composerImpl2.startReplaceGroup(-2135107383);
            objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            obj = Composer.Companion.Empty;
            if (objRememberedValue == obj) {
            }
            mutableState = (MutableState) objRememberedValue;
            composerImpl2.end(false);
            view = (View) composerImpl2.consume(AndroidCompositionLocals_androidKt.LocalView);
            Boolean bool2 = (Boolean) mutableState.getValue();
            bool2.getClass();
            int i52 = i3;
            composerImpl2.startReplaceGroup(-2135104034);
            zChangedInstance = composerImpl2.changedInstance(view);
            objRememberedValue2 = composerImpl2.rememberedValue();
            if (!zChangedInstance) {
                objRememberedValue2 = new SecPinBouncerKt$PinPadButton$1$1(view, mutableState, null);
                composerImpl2.updateRememberedValue(objRememberedValue2);
                composerImpl2.end(false);
                EffectsKt.LaunchedEffect(composerImpl2, bool2, (Function2) objRememberedValue2);
                final float fDimensionResource2 = PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pin_button_size, composerImpl2);
                if (!((Boolean) mutableState.getValue()).booleanValue()) {
                }
                int iM3464toIntimpl2 = Duration.m3464toIntimpl(!((Boolean) mutableState.getValue()).booleanValue() ? pinButtonPressedDuration : pinButtonReleasedDuration, DurationUnit.MILLISECONDS);
                final float f22 = fDimensionResource2 / 2;
                Dp.Companion companion2 = Dp.Companion;
                final State<Color> stateM7animateColorAsStateeuL9pac2 = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(j2, AnimationSpecKt.tween$default(iM3464toIntimpl2, 0, easings$fromInterpolator$1, 2), "Pin button container color", composerImpl2, ((i52 >> 9) & 14) | 384, 8);
                objRememberedValue3 = composerImpl2.rememberedValue();
                if (objRememberedValue3 == obj) {
                }
                final CoroutineScope coroutineScope2 = (CoroutineScope) objRememberedValue3;
                final boolean z22 = false;
                ThemeKt.SeslTheme(false, null, ComposableLambdaKt.rememberComposableLambda(236579027, new Function2() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$PinPadButton$2
                    /* JADX WARN: Removed duplicated region for block: B:15:0x0081  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj2, Object obj3) {
                        Composer composer2 = (Composer) obj2;
                        if ((((Number) obj3).intValue() & 3) == 2) {
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            if (composerImpl3.getSkipping()) {
                                composerImpl3.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.bouncer.ui.composable.PinPadButton.<anonymous> (SecPinBouncer.kt:339)");
                                }
                                Alignment.Companion.getClass();
                                BiasAlignment biasAlignment = Alignment.Companion.Center;
                                FocusRequester.Companion.getClass();
                                Modifier modifierFocusable$default = FocusableKt.focusable$default(FocusRequesterModifierKt.focusRequester(modifier, FocusRequester.Default), false, null, 3);
                                float f3 = fDimensionResource2;
                                Modifier modifierThen = SizeKt.m131height3ABfNKs(SizeKt.m144width3ABfNKs(modifierFocusable$default, f3), f3).then(new AspectRatioElement(1.0f, false, InspectableValueKt.NoInspectorInfo));
                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                composerImpl4.startReplaceGroup(1895463802);
                                boolean zChanged = composerImpl4.changed(z22);
                                float f4 = f22;
                                boolean zChanged2 = zChanged | composerImpl4.changed(f4) | composerImpl4.changed(j) | composerImpl4.changed(f);
                                Object objRememberedValue4 = composerImpl4.rememberedValue();
                                if (!zChanged2) {
                                    Composer.Companion.getClass();
                                    if (objRememberedValue4 == Composer.Companion.Empty) {
                                        final boolean z3 = z22;
                                        final float f5 = f22;
                                        final long j3 = j;
                                        final float f6 = f;
                                        Function1 function1 = new Function1() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$PinPadButton$2$$ExternalSyntheticLambda0
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj4) {
                                                DrawScope drawScope = (DrawScope) obj4;
                                                if (!z3) {
                                                    float fMo58toPx0680j_4 = drawScope.mo58toPx0680j_4(f5);
                                                    long jFloatToRawIntBits = (Float.floatToRawIntBits(fMo58toPx0680j_4) << 32) | (Float.floatToRawIntBits(fMo58toPx0680j_4) & 4294967295L);
                                                    CornerRadius.Companion companion22 = CornerRadius.Companion;
                                                    DrawScope.m543drawRoundRectuAw5IA$default(drawScope, j3, 0L, 0L, jFloatToRawIntBits, null, f6, IKnoxCustomManager.Stub.TRANSACTION_removeAutoCallNumber);
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        composerImpl4.updateRememberedValue(function1);
                                        objRememberedValue4 = function1;
                                    }
                                    composerImpl4.end(false);
                                    Modifier modifierDrawBehind = DrawModifierKt.drawBehind(modifierThen, (Function1) objRememberedValue4);
                                    boolean z4 = z;
                                    if (z4) {
                                        Modifier.Companion companion22 = Modifier.Companion;
                                        Unit unit = Unit.INSTANCE;
                                        final Function0 function06 = function05;
                                        final CoroutineScope coroutineScope22 = coroutineScope2;
                                        final MutableState<Boolean> mutableState2 = mutableState;
                                        final Function0 function07 = function0;
                                        modifierDrawBehind = modifierDrawBehind.then(SuspendingPointerInputFilterKt.pointerInput(companion22, unit, new PointerInputEventHandler() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$PinPadButton$2$2$1

                                            /* renamed from: com.android.bouncer.ui.composable.SecPinBouncerKt$PinPadButton$2$2$1$2, reason: invalid class name */
                                            final class AnonymousClass2 extends SuspendLambda implements Function3 {
                                                final /* synthetic */ MutableState<Boolean> $isPressed$delegate;
                                                final /* synthetic */ CoroutineScope $scope;
                                                private /* synthetic */ Object L$0;
                                                int label;

                                                /* renamed from: com.android.bouncer.ui.composable.SecPinBouncerKt$PinPadButton$2$2$1$2$1, reason: invalid class name */
                                                final class AnonymousClass1 extends SuspendLambda implements Function2 {
                                                    final /* synthetic */ PressGestureScope $$this$detectTapGestures;
                                                    final /* synthetic */ MutableState<Boolean> $isPressed$delegate;
                                                    private /* synthetic */ Object L$0;
                                                    int label;

                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    public AnonymousClass1(PressGestureScope pressGestureScope, MutableState<Boolean> mutableState, Continuation continuation) {
                                                        super(2, continuation);
                                                        this.$$this$detectTapGestures = pressGestureScope;
                                                        this.$isPressed$delegate = mutableState;
                                                    }

                                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                    public final Continuation create(Object obj, Continuation continuation) {
                                                        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$$this$detectTapGestures, this.$isPressed$delegate, continuation);
                                                        anonymousClass1.L$0 = obj;
                                                        return anonymousClass1;
                                                    }

                                                    @Override // kotlin.jvm.functions.Function2
                                                    public final Object invoke(Object obj, Object obj2) {
                                                        return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                                    }

                                                    /* JADX WARN: Code restructure failed: missing block: B:14:0x0052, code lost:
                                                    
                                                        if (r1.await(r6) == r0) goto L15;
                                                     */
                                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                    /*
                                                        Code decompiled incorrectly, please refer to instructions dump.
                                                    */
                                                    public final Object invokeSuspend(Object obj) {
                                                        Deferred deferredAsync$default;
                                                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                                        int i = this.label;
                                                        if (i == 0) {
                                                            ResultKt.throwOnFailure(obj);
                                                            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                                                            MutableState<Boolean> mutableState = this.$isPressed$delegate;
                                                            float f = SecPinBouncerKt.pinButtonErrorShrinkFactor;
                                                            mutableState.setValue(Boolean.TRUE);
                                                            deferredAsync$default = BuildersKt.async$default(coroutineScope, null, new SecPinBouncerKt$PinPadButton$2$2$1$2$1$minDuration$1(null), 3);
                                                            PressGestureScope pressGestureScope = this.$$this$detectTapGestures;
                                                            this.L$0 = deferredAsync$default;
                                                            this.label = 1;
                                                            if (((PressGestureScopeImpl) pressGestureScope).tryAwaitRelease(this) != coroutineSingletons) {
                                                            }
                                                            return coroutineSingletons;
                                                        }
                                                        if (i != 1) {
                                                            if (i != 2) {
                                                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                            }
                                                            ResultKt.throwOnFailure(obj);
                                                            MutableState<Boolean> mutableState2 = this.$isPressed$delegate;
                                                            float f2 = SecPinBouncerKt.pinButtonErrorShrinkFactor;
                                                            mutableState2.setValue(Boolean.FALSE);
                                                            return Unit.INSTANCE;
                                                        }
                                                        deferredAsync$default = (Deferred) this.L$0;
                                                        ResultKt.throwOnFailure(obj);
                                                        this.L$0 = null;
                                                        this.label = 2;
                                                    }
                                                }

                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                public AnonymousClass2(CoroutineScope coroutineScope, MutableState<Boolean> mutableState, Continuation continuation) {
                                                    super(3, continuation);
                                                    this.$scope = coroutineScope;
                                                    this.$isPressed$delegate = mutableState;
                                                }

                                                @Override // kotlin.jvm.functions.Function3
                                                public final Object invoke(Object obj, Object obj2, Object obj3) {
                                                    long j = ((Offset) obj2).packedValue;
                                                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$scope, this.$isPressed$delegate, (Continuation) obj3);
                                                    anonymousClass2.L$0 = (PressGestureScope) obj;
                                                    return anonymousClass2.invokeSuspend(Unit.INSTANCE);
                                                }

                                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                public final Object invokeSuspend(Object obj) {
                                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                                    if (this.label != 0) {
                                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                    }
                                                    ResultKt.throwOnFailure(obj);
                                                    BuildersKt.launch$default(this.$scope, null, null, new AnonymousClass1((PressGestureScope) this.L$0, this.$isPressed$delegate, null), 3);
                                                    return Unit.INSTANCE;
                                                }
                                            }

                                            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                                            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                                                final Function0 function08 = function06;
                                                Function1 function12 = function08 != null ? new Function1() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$PinPadButton$2$2$1$1$1
                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final Object mo781invoke(Object obj4) {
                                                        long j4 = ((Offset) obj4).packedValue;
                                                        function08.invoke();
                                                        return Unit.INSTANCE;
                                                    }
                                                } : null;
                                                AnonymousClass2 anonymousClass2 = new AnonymousClass2(coroutineScope22, mutableState2, null);
                                                final Function0 function09 = function07;
                                                Object objDetectTapGestures$default = TapGestureDetectorKt.detectTapGestures$default(pointerInputScope, null, function12, anonymousClass2, new Function1() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$PinPadButton$2$2$1.3
                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final Object mo781invoke(Object obj4) {
                                                        long j4 = ((Offset) obj4).packedValue;
                                                        function09.invoke();
                                                        return Unit.INSTANCE;
                                                    }
                                                }, continuation, 1);
                                                return objDetectTapGestures$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objDetectTapGestures$default : Unit.INSTANCE;
                                            }
                                        }));
                                    }
                                    Modifier modifierM3351seslRecoilfWhpE4E = RecoilKt.m3351seslRecoilfWhpE4E(ClipKt.clip(modifierDrawBehind, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(f4)), z4, composerImpl4, 0);
                                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl4);
                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
                                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl4, modifierM3351seslRecoilfWhpE4E);
                                    ComposeUiNode.Companion.getClass();
                                    Function0 function08 = ComposeUiNode.Companion.Constructor;
                                    if (composerImpl4.applier == null) {
                                        ComposablesKt.invalidApplier();
                                        throw null;
                                    }
                                    composerImpl4.startReusableNode();
                                    if (composerImpl4.inserting) {
                                        composerImpl4.createNode(function08);
                                    } else {
                                        composerImpl4.useNode();
                                    }
                                    Updater.m337setimpl(composerImpl4, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                    Updater.m337setimpl(composerImpl4, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                    if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function2);
                                    }
                                    Updater.m337setimpl(composerImpl4, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                    final Object state = stateM7animateColorAsStateeuL9pac2;
                                    composableLambdaImpl.invoke(new PropertyReference0Impl(state) { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$PinPadButton$2$3$1
                                        @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                                        public final Object get() {
                                            return ((State) this.receiver).getValue();
                                        }
                                    }, composerImpl4, 0);
                                    composerImpl4.end(true);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl2), composerImpl2, 384, 3);
                composerImpl = composerImpl2;
                if (ComposerKt.isTraceInProgress()) {
                }
                function04 = function05;
            }
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x00be  */
    /* renamed from: SecDigitButton-wBJOh4Y, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m905SecDigitButtonwBJOh4Y(final int i, final String str, final boolean z, final Function1 function1, final long j, final long j2, final float f, Composer composer, final int i2) {
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1556522298);
        if ((i2 & 6) == 0) {
            i3 = i2 | (composerImpl.changed(i) ? 4 : 2);
        } else {
            i3 = i2;
        }
        if ((i2 & 48) == 0) {
            i3 |= composerImpl.changed(str) ? 32 : 16;
        }
        int i4 = i3 | (composerImpl.changed(z) ? 256 : 128) | (composerImpl.changedInstance(function1) ? 2048 : 1024) | (composerImpl.changed(j) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192) | (composerImpl.changed(j2) ? 131072 : 65536) | (composerImpl.changed(f) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        if ((599187 & i4) == 599186 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.SecDigitButton (SecPinBouncer.kt:198)");
            }
            composerImpl.startReplaceGroup(481935513);
            boolean z2 = ((i4 & 7168) == 2048) | ((i4 & 14) == 4);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!z2) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function0() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            function1.mo781invoke(Integer.valueOf(i));
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                Function0 function0 = (Function0) objRememberedValue;
                composerImpl.end(false);
                Modifier.Companion companion2 = Modifier.Companion;
                Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, 481941617, companion);
                if (objM == Composer.Companion.Empty) {
                    objM = new SecPinBouncerKt$$ExternalSyntheticLambda3();
                    composerImpl.updateRememberedValue(objM);
                }
                composerImpl.end(false);
                int i5 = i4 >> 6;
                m904PinPadButtonnWoaYo(function0, z, j, j2, GraphicsLayerModifierKt.graphicsLayer(companion2, (Function1) objM), null, f, ComposableLambdaKt.rememberComposableLambda(645524963, new Function3() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$SecDigitButton$3
                    /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
                    @Override // kotlin.jvm.functions.Function3
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        Function0 function02 = (Function0) obj;
                        Composer composer2 = (Composer) obj2;
                        int iIntValue = ((Number) obj3).intValue();
                        if ((iIntValue & 6) == 0) {
                            iIntValue |= ((ComposerImpl) composer2).changedInstance(function02) ? 4 : 2;
                        }
                        if ((iIntValue & 19) == 18) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.bouncer.ui.composable.SecDigitButton.<anonymous> (SecPinBouncer.kt:215)");
                                }
                                Alignment.Companion.getClass();
                                BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                                Modifier.Companion companion3 = Modifier.Companion;
                                Arrangement.INSTANCE.getClass();
                                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.Top, horizontal, composer2, 48);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, companion3);
                                ComposeUiNode.Companion.getClass();
                                Function0 function03 = ComposeUiNode.Companion.Constructor;
                                if (composerImpl3.applier == null) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl3.startReusableNode();
                                if (composerImpl3.inserting) {
                                    composerImpl3.createNode(function03);
                                } else {
                                    composerImpl3.useNode();
                                }
                                Updater.m337setimpl(composer2, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                                }
                                Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                                int i6 = i;
                                String strValueOf = String.valueOf(i6);
                                composerImpl3.startReplaceGroup(773747626);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.bouncer.ui.compose.theme.PinDigitTextSytle (BouncerStyle.kt:36)");
                                }
                                TextStyle.Companion companion4 = TextStyle.Companion;
                                TextStyle textStyleM756copyp1EtxEg$default = TextStyle.m756copyp1EtxEg$default(TypeKt.getSecRegular(), 0L, BouncerStyleKt.getDpTextUnit(PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pin_button_digit_text_size, composerImpl3), composerImpl3), null, null, 0L, 0, 0L, null, null, 0, 16777213);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                composerImpl3.end(false);
                                TextKt.m317Text4IGK_g(strValueOf, null, ((Color) function02.invoke()).value, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, textStyleM756copyp1EtxEg$default, composer2, 0, 0, 65530);
                                composerImpl3.startReplaceGroup(-1207154514);
                                if (i6 > 0) {
                                    composerImpl3.startReplaceGroup(-143676598);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.bouncer.ui.compose.theme.PinKlondikeTextSytle (BouncerStyle.kt:41)");
                                    }
                                    TextStyle textStyleM756copyp1EtxEg$default2 = TextStyle.m756copyp1EtxEg$default(TypeKt.getSecRegular(), 0L, BouncerStyleKt.getDpTextUnit(PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pin_button_klondike_text_size, composerImpl3), composerImpl3), null, null, 0L, 0, 0L, null, null, 0, 16777213);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    composerImpl3.end(false);
                                    TextKt.m317Text4IGK_g(str, null, ((Color) function02.invoke()).value, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, textStyleM756copyp1EtxEg$default2, composer2, 0, 0, 65530);
                                }
                                if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl3, false, true)) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, (i5 & 896) | ((i4 >> 3) & 112) | 102260736 | (i5 & 7168) | (29360128 & (i4 << 3)), 32);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i2 | 1);
                    long j3 = j2;
                    float f2 = f;
                    SecPinBouncerKt.m905SecDigitButtonwBJOh4Y(i, str, z, function1, j, j3, f2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x007e  */
    /* renamed from: SecPinPad-uFdPcIQ, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m906SecPinPaduFdPcIQ(final PinBouncerViewModel pinBouncerViewModel, float f, final Modifier modifier, Composer composer, final int i) {
        MutableState mutableState;
        Object obj;
        BiasAlignment.Horizontal horizontal;
        final PinBouncerViewModel pinBouncerViewModel2;
        final float f2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-2023579468);
        int i2 = (i & 6) == 0 ? (composerImpl2.changedInstance(pinBouncerViewModel) ? 4 : 2) | i : i;
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changed(f) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changed(modifier) ? 256 : 128;
        }
        int i3 = i2;
        if ((i3 & 147) == 146 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            pinBouncerViewModel2 = pinBouncerViewModel;
            f2 = f;
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.SecPinPad (SecPinBouncer.kt:76)");
            }
            Unit unit = Unit.INSTANCE;
            composerImpl2.startReplaceGroup(1307287870);
            boolean zChangedInstance = composerImpl2.changedInstance(pinBouncerViewModel);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChangedInstance) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function1() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            final PinBouncerViewModel pinBouncerViewModel3 = pinBouncerViewModel;
                            return new DisposableEffectResult() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$SecPinPad_uFdPcIQ$lambda$2$lambda$1$$inlined$onDispose$1
                                @Override // androidx.compose.runtime.DisposableEffectResult
                                public final void dispose() {
                                    pinBouncerViewModel3.clearInput();
                                }
                            };
                        }
                    };
                    composerImpl2.updateRememberedValue(objRememberedValue);
                }
                composerImpl2.end(false);
                EffectsKt.DisposableEffect(unit, (Function1) objRememberedValue, composerImpl2);
                MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(pinBouncerViewModel.isInputEnabled, composerImpl2);
                final MutableState mutableStateCollectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(pinBouncerViewModel.backspaceButtonAppearance, composerImpl2);
                final MutableState mutableStateCollectAsStateWithLifecycle3 = FlowExtKt.collectAsStateWithLifecycle(pinBouncerViewModel.confirmButtonAppearance, composerImpl2);
                MutableState mutableStateCollectAsStateWithLifecycle4 = FlowExtKt.collectAsStateWithLifecycle(pinBouncerViewModel.animateFailure, composerImpl2);
                FlowExtKt.collectAsStateWithLifecycle(pinBouncerViewModel.isDigitButtonAnimationEnabled, composerImpl2);
                final MutableState mutableStateCollectAsStateWithLifecycle5 = FlowExtKt.collectAsStateWithLifecycle(pinBouncerViewModel.confirmButtonEnabled, Boolean.FALSE, composerImpl2, 48);
                final MutableState mutableStateCollectAsStateWithLifecycle6 = FlowExtKt.collectAsStateWithLifecycle(pinBouncerViewModel.digitButtonContentColor, composerImpl2);
                final MutableState mutableStateCollectAsStateWithLifecycle7 = FlowExtKt.collectAsStateWithLifecycle(pinBouncerViewModel.digitButtonBackgroundColor, composerImpl2);
                final MutableState mutableStateCollectAsStateWithLifecycle8 = FlowExtKt.collectAsStateWithLifecycle(pinBouncerViewModel.digitButtonBackgroundColorAlpha, composerImpl2);
                Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl2, 1307327232, companion);
                if (objM == Composer.Companion.Empty) {
                    ArrayList arrayList = new ArrayList(12);
                    mutableState = mutableStateCollectAsStateWithLifecycle;
                    int i4 = 0;
                    for (int i5 = 12; i4 < i5; i5 = 12) {
                        arrayList.add(AnimatableKt.Animatable(1.0f, 0.01f));
                        i4++;
                    }
                    composerImpl2.updateRememberedValue(arrayList);
                    obj = arrayList;
                } else {
                    mutableState = mutableStateCollectAsStateWithLifecycle;
                    obj = objM;
                }
                final List list = (List) obj;
                composerImpl2.end(false);
                Boolean bool = (Boolean) mutableStateCollectAsStateWithLifecycle4.getValue();
                boolean zM = SecPasswordBouncerKt$$ExternalSyntheticOutline0.m(bool, composerImpl2, 1307329851, mutableStateCollectAsStateWithLifecycle4) | composerImpl2.changedInstance(list) | composerImpl2.changedInstance(pinBouncerViewModel);
                Object objRememberedValue2 = composerImpl2.rememberedValue();
                if (zM || objRememberedValue2 == Composer.Companion.Empty) {
                    horizontal = null;
                    objRememberedValue2 = new SecPinBouncerKt$SecPinPad$2$1(list, pinBouncerViewModel, mutableStateCollectAsStateWithLifecycle4, null);
                    composerImpl2.updateRememberedValue(objRememberedValue2);
                } else {
                    horizontal = null;
                }
                composerImpl2.end(false);
                EffectsKt.LaunchedEffect(composerImpl2, bool, (Function2) objRememberedValue2);
                float fDimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pin_horizontal_space, composerImpl2);
                Modifier modifierWrapContentWidth$default = SizeKt.wrapContentWidth$default(modifier, horizontal, 3);
                final MutableState mutableState2 = mutableState;
                Function2 function2 = new Function2() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$SecPinPad$3
                    /* JADX WARN: Removed duplicated region for block: B:24:0x0092  */
                    /* JADX WARN: Removed duplicated region for block: B:30:0x0102  */
                    /* JADX WARN: Removed duplicated region for block: B:35:0x0126  */
                    /* JADX WARN: Removed duplicated region for block: B:40:0x019e  */
                    /* JADX WARN: Removed duplicated region for block: B:45:0x01f8  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj2, Object obj3) throws Resources.NotFoundException {
                        State state;
                        State state2;
                        State state3;
                        State state4;
                        Composer.Companion companion2;
                        PinBouncerViewModel pinBouncerViewModel3;
                        Composer composer2 = (Composer) obj2;
                        if ((((Number) obj3).intValue() & 3) == 2) {
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            if (composerImpl3.getSkipping()) {
                                composerImpl3.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.bouncer.ui.composable.SecPinPad.<anonymous> (SecPinBouncer.kt:114)");
                                }
                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                composerImpl4.startReplaceGroup(-835338447);
                                int i6 = 0;
                                while (true) {
                                    state = mutableStateCollectAsStateWithLifecycle7;
                                    state2 = mutableStateCollectAsStateWithLifecycle8;
                                    state3 = mutableStateCollectAsStateWithLifecycle6;
                                    state4 = mutableState2;
                                    companion2 = Composer.Companion;
                                    pinBouncerViewModel3 = pinBouncerViewModel;
                                    if (i6 >= 9) {
                                        break;
                                    }
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.ui.res.stringArrayResource (StringResources.android.kt:58)");
                                    }
                                    String[] stringArray = ((Resources) composerImpl4.consume(AndroidCompositionLocals_androidKt.LocalResources)).getStringArray(R.array.lockscreen_num_pad_klondike);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    int i7 = i6 + 1;
                                    String str = stringArray[i7];
                                    float f3 = SecPinBouncerKt.pinButtonErrorShrinkFactor;
                                    boolean zBooleanValue = ((Boolean) state4.getValue()).booleanValue();
                                    composerImpl4.startReplaceGroup(-166782701);
                                    boolean zChangedInstance2 = composerImpl4.changedInstance(pinBouncerViewModel3);
                                    Object objRememberedValue3 = composerImpl4.rememberedValue();
                                    if (!zChangedInstance2) {
                                        companion2.getClass();
                                        if (objRememberedValue3 == Composer.Companion.Empty) {
                                            objRememberedValue3 = new SecPinBouncerKt$SecPinPad$3$1$1$1(pinBouncerViewModel3);
                                            composerImpl4.updateRememberedValue(objRememberedValue3);
                                        }
                                    }
                                    composerImpl4.end(false);
                                    ComposerImpl composerImpl5 = composerImpl4;
                                    SecPinBouncerKt.m905SecDigitButtonwBJOh4Y(i7, str, zBooleanValue, (Function1) ((KFunction) objRememberedValue3), ((Color) state.getValue()).value, ((Color) state3.getValue()).value, ((Number) state2.getValue()).floatValue(), composerImpl5, 0);
                                    i6 = i7;
                                    composerImpl4 = composerImpl5;
                                }
                                ComposerImpl composerImpl6 = composerImpl4;
                                composerImpl6.end(false);
                                Icon.Resource resource = new Icon.Resource(R.drawable.pin_lock_btn_back, new ContentDescription.Resource(R.string.keyboardview_keycode_delete));
                                float f4 = SecPinBouncerKt.pinButtonErrorShrinkFactor;
                                boolean zBooleanValue2 = ((Boolean) state4.getValue()).booleanValue();
                                composerImpl6.startReplaceGroup(-835309542);
                                boolean zChangedInstance3 = composerImpl6.changedInstance(pinBouncerViewModel3);
                                Object objRememberedValue4 = composerImpl6.rememberedValue();
                                if (!zChangedInstance3) {
                                    companion2.getClass();
                                    if (objRememberedValue4 == Composer.Companion.Empty) {
                                        objRememberedValue4 = new SecPinBouncerKt$SecPinPad$3$2$1(pinBouncerViewModel3);
                                        composerImpl6.updateRememberedValue(objRememberedValue4);
                                    }
                                    KFunction kFunction = (KFunction) objRememberedValue4;
                                    composerImpl6.end(false);
                                    composerImpl6.startReplaceGroup(-835307458);
                                    boolean zChangedInstance4 = composerImpl6.changedInstance(pinBouncerViewModel3);
                                    Object objRememberedValue5 = composerImpl6.rememberedValue();
                                    if (!zChangedInstance4) {
                                        companion2.getClass();
                                        if (objRememberedValue5 == Composer.Companion.Empty) {
                                            objRememberedValue5 = new SecPinBouncerKt$SecPinPad$3$3$1(pinBouncerViewModel3);
                                            composerImpl6.updateRememberedValue(objRememberedValue5);
                                        }
                                        composerImpl6.end(false);
                                        SecPinBouncerKt.m903ActionButtonmwpFuRA(resource, zBooleanValue2, (Function0) kFunction, (Function0) ((KFunction) objRememberedValue5), (ActionButtonAppearance) mutableStateCollectAsStateWithLifecycle2.getValue(), true, ((Color) state3.getValue()).value, ((Number) state2.getValue()).floatValue(), new PropertyReference0Impl(list.get(9)) { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$SecPinPad$3.4
                                            @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                                            public final Object get() {
                                                return ((Animatable) this.receiver).internalState.getValue();
                                            }
                                        }, composerImpl6, 196608, 0);
                                        boolean zBooleanValue3 = ((Boolean) state4.getValue()).booleanValue();
                                        composerImpl6.startReplaceGroup(-835289964);
                                        boolean zChangedInstance5 = composerImpl6.changedInstance(pinBouncerViewModel3);
                                        Object objRememberedValue6 = composerImpl6.rememberedValue();
                                        if (!zChangedInstance5) {
                                            companion2.getClass();
                                            if (objRememberedValue6 == Composer.Companion.Empty) {
                                                objRememberedValue6 = new SecPinBouncerKt$SecPinPad$3$5$1(pinBouncerViewModel3);
                                                composerImpl6.updateRememberedValue(objRememberedValue6);
                                            }
                                            composerImpl6.end(false);
                                            SecPinBouncerKt.m905SecDigitButtonwBJOh4Y(0, "", zBooleanValue3, (Function1) ((KFunction) objRememberedValue6), ((Color) state.getValue()).value, ((Color) state3.getValue()).value, ((Number) state2.getValue()).floatValue(), composerImpl6, 54);
                                            composerImpl6.startReplaceGroup(-123944639);
                                            boolean zBooleanValue4 = ((Boolean) state4.getValue()).booleanValue();
                                            composerImpl6.startReplaceGroup(-835278467);
                                            boolean zChangedInstance6 = composerImpl6.changedInstance(pinBouncerViewModel3);
                                            Object objRememberedValue7 = composerImpl6.rememberedValue();
                                            if (!zChangedInstance6) {
                                                companion2.getClass();
                                                if (objRememberedValue7 == Composer.Companion.Empty) {
                                                    objRememberedValue7 = new SecPinBouncerKt$SecPinPad$3$6$1(pinBouncerViewModel3);
                                                    composerImpl6.updateRememberedValue(objRememberedValue7);
                                                }
                                                composerImpl6.end(false);
                                                SecPinBouncerKt.m903ActionButtonmwpFuRA(null, zBooleanValue4, (Function0) ((KFunction) objRememberedValue7), null, (ActionButtonAppearance) mutableStateCollectAsStateWithLifecycle3.getValue(), ((Boolean) mutableStateCollectAsStateWithLifecycle5.getValue()).booleanValue(), ((Color) state3.getValue()).value, ((Number) state2.getValue()).floatValue(), new PropertyReference0Impl(list.get(11)) { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$SecPinPad$3.7
                                                    @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                                                    public final Object get() {
                                                        return ((Animatable) this.receiver).internalState.getValue();
                                                    }
                                                }, composerImpl6, 6, 8);
                                                composerImpl6.end(false);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                pinBouncerViewModel2 = pinBouncerViewModel;
                f2 = f;
                composerImpl = composerImpl2;
                GridsKt.m939VerticalGridvz2T9sI(3, modifierWrapContentWidth$default, f2, fDimensionResource, ComposableLambdaKt.rememberComposableLambda(664888487, function2, composerImpl2), composerImpl, ((i3 << 3) & 896) | 24582);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.bouncer.ui.composable.SecPinBouncerKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    float f3 = f2;
                    Modifier modifier2 = modifier;
                    SecPinBouncerKt.m906SecPinPaduFdPcIQ(pinBouncerViewModel2, f3, modifier2, (Composer) obj2, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
