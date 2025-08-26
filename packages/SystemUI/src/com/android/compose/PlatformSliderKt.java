package com.android.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.SingleValueAnimationKt;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.foundation.CanvasKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.OffsetKt;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.SliderKt;
import androidx.compose.material3.SliderState;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.geometry.RoundRectKt;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.ClipOp;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.ClosedFloatRange;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public abstract class PlatformSliderKt {
    /* renamed from: PlatformSlider-Wu8B24Y, reason: not valid java name */
    public static final void m907PlatformSliderWu8B24Y(final float f, final Function1 function1, final Modifier modifier, final Function0 function0, final ClosedFloatingPointRange closedFloatingPointRange, final boolean z, final MutableInteractionSource mutableInteractionSource, final PlatformSliderColors platformSliderColors, float f2, final ComposableLambdaImpl composableLambdaImpl, final ComposableLambdaImpl composableLambdaImpl2, Composer composer, final int i) {
        int i2;
        final ComposableLambdaImpl composableLambdaImpl3;
        float f3;
        PlatformSliderColors platformSliderColors2;
        final float f4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1021066434);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(f) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function1) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(closedFloatingPointRange) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changed(z) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i2 |= composerImpl.changed(mutableInteractionSource) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((12582912 & i) == 0) {
            i2 |= composerImpl.changed(platformSliderColors) ? 8388608 : 4194304;
        }
        int i3 = i2 | 100663296;
        if ((805306368 & i) == 0) {
            composableLambdaImpl3 = composableLambdaImpl;
            i3 |= composerImpl.changedInstance(composableLambdaImpl3) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
        } else {
            composableLambdaImpl3 = composableLambdaImpl;
        }
        int i4 = i3;
        if ((i4 & 306783379) == 306783378 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            f4 = f2;
            platformSliderColors2 = platformSliderColors;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                PlatformSliderDefaults.INSTANCE.getClass();
                f3 = PlatformSliderDefaults.DefaultPlatformSliderDraggingCornerRadius;
            } else {
                composerImpl.skipToGroupEnd();
                f3 = f2;
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.compose.PlatformSlider (PlatformSlider.kt:100)");
            }
            final float f5 = 64;
            Dp.Companion companion = Dp.Companion;
            composerImpl.startReplaceGroup(1855796574);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            final MutableState mutableState = (MutableState) objRememberedValue;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(1855799251);
            int i5 = i4 & 3670016;
            boolean z2 = i5 == 1048576;
            Object objRememberedValue2 = composerImpl.rememberedValue();
            final float f6 = f3;
            if (z2 || objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = new PlatformSliderKt$PlatformSlider$2$1(mutableInteractionSource, mutableState, null);
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, mutableInteractionSource, (Function2) objRememberedValue2);
            Modifier modifierM131height3ABfNKs = SizeKt.m131height3ABfNKs(modifier, f5);
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM131height3ABfNKs);
            ComposeUiNode.Companion.getClass();
            Function0 function02 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function02);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            Modifier.Companion companion2 = Modifier.Companion;
            platformSliderColors2 = platformSliderColors;
            SliderKt.Slider(f, function1, SizeKt.fillMaxSize(companion2, 1.0f), z, function0, null, mutableInteractionSource, 0, ComposableLambdaKt.rememberComposableLambda(673011712, new Function3() { // from class: com.android.compose.PlatformSliderKt$PlatformSlider$3$1
                /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 17) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.compose.PlatformSlider.<anonymous>.<anonymous> (PlatformSlider.kt:141)");
                            }
                            SpacerKt.Spacer(composer2, SizeKt.m140size3ABfNKs(Modifier.Companion, f5));
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), ComposableLambdaKt.rememberComposableLambda(-1677983679, new Function3() { // from class: com.android.compose.PlatformSliderKt$PlatformSlider$3$2
                /* JADX WARN: Removed duplicated region for block: B:18:0x003d  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    SliderState sliderState = (SliderState) obj;
                    Composer composer2 = (Composer) obj2;
                    int iIntValue = ((Number) obj3).intValue();
                    if ((iIntValue & 6) == 0) {
                        iIntValue |= (iIntValue & 8) == 0 ? ((ComposerImpl) composer2).changed(sliderState) : ((ComposerImpl) composer2).changedInstance(sliderState) ? 4 : 2;
                    }
                    if ((iIntValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.compose.PlatformSlider.<anonymous>.<anonymous> (PlatformSlider.kt:128)");
                            }
                            PlatformSliderKt.m908TrackpKeW4W0(sliderState, z, platformSliderColors, f6, f5, f5, ((Boolean) mutableState.getValue()).booleanValue(), composableLambdaImpl3, composableLambdaImpl2, SizeKt.fillMaxSize(Modifier.Companion, 1.0f), composer2, 805527560 | (iIntValue & 14));
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), closedFloatingPointRange, composerImpl, (i4 & 14) | 905970048 | (i4 & 112) | ((i4 >> 6) & 7168) | ((i4 << 3) & 57344) | i5, (i4 >> 12) & 14, 160);
            composerImpl.startReplaceGroup(-1156637088);
            if (z) {
                SpacerKt.Spacer(composerImpl, BackgroundKt.m26backgroundbw27NRU(boxScopeInstance.align(SizeKt.m140size3ABfNKs(PaddingKt.m125padding3ABfNKs(companion2, 8), 4), Alignment.Companion.CenterEnd), platformSliderColors2.indicatorColor, RoundedCornerShapeKt.CircleShape));
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                ComposerKt.traceEventEnd();
            }
            f4 = f6;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final PlatformSliderColors platformSliderColors3 = platformSliderColors2;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.compose.PlatformSliderKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ComposableLambdaImpl composableLambdaImpl4 = composableLambdaImpl;
                    ComposableLambdaImpl composableLambdaImpl5 = composableLambdaImpl2;
                    PlatformSliderKt.m907PlatformSliderWu8B24Y(f, function1, modifier, function0, closedFloatingPointRange, z, mutableInteractionSource, platformSliderColors3, f4, composableLambdaImpl4, composableLambdaImpl5, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:112:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x02ac  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0408  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0412  */
    /* renamed from: Track-pKeW4W0, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m908TrackpKeW4W0(final SliderState sliderState, final boolean z, PlatformSliderColors platformSliderColors, final float f, final float f2, final float f3, boolean z2, final Function3 function3, Function3 function32, final Modifier modifier, Composer composer, final int i) {
        int i2;
        boolean z3;
        Function2 function2;
        final boolean z4;
        ComposerImpl composerImpl;
        PlatformSliderColors platformSliderColors2;
        final Function3 function33;
        boolean z5;
        boolean z6;
        float fMo58toPx0680j_4;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1606435428);
        if ((i & 6) == 0) {
            i2 = ((i & 8) == 0 ? composerImpl2.changed(sliderState) : composerImpl2.changedInstance(sliderState) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changed(z) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changed(platformSliderColors) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl2.changed(f) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl2.changed(f2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl2.changed(f3) ? 131072 : 65536;
        }
        if ((i & 1572864) == 0) {
            i2 |= composerImpl2.changed(z2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((12582912 & i) == 0) {
            i2 |= composerImpl2.changedInstance(function3) ? 8388608 : 4194304;
        }
        if ((100663296 & i) == 0) {
            i2 |= composerImpl2.changedInstance(function32) ? 67108864 : 33554432;
        }
        if ((805306368 & i) == 0) {
            i2 |= composerImpl2.changed(modifier) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
        }
        if ((306783379 & i2) == 306783378 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
            z4 = z2;
            function33 = function32;
            platformSliderColors2 = platformSliderColors;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.compose.Track (PlatformSlider.kt:173)");
            }
            boolean z7 = composerImpl2.consume(CompositionLocalsKt.LocalLayoutDirection) == LayoutDirection.Rtl;
            composerImpl2.startReplaceGroup(-1886981554);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(new DrawingState(false, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 511, null));
                composerImpl2.updateRememberedValue(objRememberedValue);
            }
            final MutableState mutableState = (MutableState) objRememberedValue;
            composerImpl2.end(false);
            StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionLocalsKt.LocalDensity;
            int iMo52roundToPx0680j_4 = ((Density) composerImpl2.consume(staticProvidableCompositionLocal)).mo52roundToPx0680j_4(f3);
            composerImpl2.startReplaceGroup(-1886883048);
            Object objRememberedValue2 = composerImpl2.rememberedValue();
            if (objRememberedValue2 == composer$Companion$Empty$1) {
                z3 = false;
                final Object[] objArr = 0 == true ? 1 : 0;
                objRememberedValue2 = new Function1() { // from class: com.android.compose.PlatformSliderKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        switch (objArr) {
                            case 0:
                                ((MutableState) mutableState).setValue((DrawingState) obj);
                                return Unit.INSTANCE;
                            default:
                                return IntOffset.m849boximpl((((int) ((Number) mutableState.getValue()).floatValue()) << 32) | (0 & 4294967295L));
                        }
                    }
                };
                composerImpl2.updateRememberedValue(objRememberedValue2);
            } else {
                z3 = false;
            }
            composerImpl2.end(z3);
            int i3 = i2;
            TrackMeasurePolicy trackMeasurePolicy = new TrackMeasurePolicy(sliderState, z, iMo52roundToPx0680j_4, z7, (Function1) objRememberedValue2);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifier);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl2.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function0);
            } else {
                composerImpl2.useNode();
            }
            Function2 function22 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m337setimpl(composerImpl2, trackMeasurePolicy, function22);
            Function2 function23 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, function23);
            Function2 function24 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting) {
                function2 = function23;
            } else {
                function2 = function23;
                if (!Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                }
                Function2 function25 = ComposeUiNode.Companion.SetModifier;
                Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, function25);
                Modifier.Companion companion = Modifier.Companion;
                Modifier modifierLayoutId = LayoutIdKt.layoutId(companion, TrackComponent.Background);
                Dp.Companion companion2 = Dp.Companion;
                Function2 function26 = function2;
                m909TrackBackgroundYlGCr2M((DrawingState) mutableState.getValue(), z, platformSliderColors, f, f2 / 2, z2, modifierLayoutId, composerImpl2, ((i3 >> 3) & 458752) | (i3 & 112) | 1572864 | (i3 & 896) | (i3 & 7168));
                z4 = z2;
                composerImpl = composerImpl2;
                platformSliderColors2 = platformSliderColors;
                composerImpl.startReplaceGroup(1898830432);
                if (function3 != null) {
                    Modifier modifierClip = ClipKt.clip(LayoutIdKt.layoutId(companion, TrackComponent.Icon), RoundedCornerShapeKt.CircleShape);
                    Alignment.Companion.getClass();
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                    int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierClip);
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function0);
                    } else {
                        composerImpl.useNode();
                    }
                    Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function22);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function26);
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function24);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function25);
                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                    CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(z ? platformSliderColors2.iconColor : platformSliderColors2.disabledIconColor)), ComposableLambdaKt.rememberComposableLambda(2075584388, new Function2() { // from class: com.android.compose.PlatformSliderKt$Track$3$1$1
                        /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj, Object obj2) {
                            Composer composer2 = (Composer) obj;
                            if ((((Number) obj2).intValue() & 3) == 2) {
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                if (composerImpl3.getSkipping()) {
                                    composerImpl3.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.compose.Track.<anonymous>.<anonymous>.<anonymous> (PlatformSlider.kt:197)");
                                    }
                                    function3.invoke(Boolean.valueOf(z4), composer2, 0);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl), composerImpl, 56);
                    composerImpl.end(true);
                }
                composerImpl.end(false);
                composerImpl.startReplaceGroup(1898848398);
                if (function32 == null) {
                    composerImpl.startReplaceGroup(1898852476);
                    if (z) {
                        DrawingState drawingState = (DrawingState) mutableState.getValue();
                        if (drawingState.labelWidth < (drawingState.indicatorRight - drawingState.indicatorLeft) - drawingState.iconWidth) {
                            composerImpl.startReplaceGroup(-1265042629);
                            fMo58toPx0680j_4 = ((DrawingState) mutableState.getValue()).iconWidth;
                            float fMo58toPx0680j_42 = ((Density) composerImpl.consume(staticProvidableCompositionLocal)).mo58toPx0680j_4(16);
                            if (fMo58toPx0680j_4 < fMo58toPx0680j_42) {
                                fMo58toPx0680j_4 = fMo58toPx0680j_42;
                            }
                            composerImpl.end(false);
                            z6 = false;
                        } else {
                            composerImpl.startReplaceGroup(-1264807804);
                            fMo58toPx0680j_4 = (((DrawingState) mutableState.getValue()).indicatorRight - ((DrawingState) mutableState.getValue()).indicatorLeft) + ((Density) composerImpl.consume(staticProvidableCompositionLocal)).mo58toPx0680j_4(16);
                            z6 = false;
                            composerImpl.end(false);
                        }
                    } else {
                        z6 = false;
                        fMo58toPx0680j_4 = ((DrawingState) mutableState.getValue()).iconWidth;
                    }
                    composerImpl.end(z6);
                    final State stateAnimateFloatAsState = AnimateAsStateKt.animateFloatAsState(fMo58toPx0680j_4, null, "LabelIconSpacingAnimation", null, composerImpl, 3072, 22);
                    Modifier modifierLayoutId2 = LayoutIdKt.layoutId(companion, TrackComponent.Label);
                    composerImpl.startReplaceGroup(1898882691);
                    boolean zChanged = composerImpl.changed(stateAnimateFloatAsState);
                    Object objRememberedValue3 = composerImpl.rememberedValue();
                    if (zChanged || objRememberedValue3 == composer$Companion$Empty$1) {
                        final int i4 = 1;
                        objRememberedValue3 = new Function1() { // from class: com.android.compose.PlatformSliderKt$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                switch (i4) {
                                    case 0:
                                        ((MutableState) stateAnimateFloatAsState).setValue((DrawingState) obj);
                                        return Unit.INSTANCE;
                                    default:
                                        return IntOffset.m849boximpl((((int) ((Number) stateAnimateFloatAsState.getValue()).floatValue()) << 32) | (0 & 4294967295L));
                                }
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue3);
                    }
                    composerImpl.end(false);
                    Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(OffsetKt.offset(modifierLayoutId2, (Function1) objRememberedValue3), 0.0f, 0.0f, 16, 0.0f, 11);
                    Alignment.Companion.getClass();
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.CenterStart, false);
                    int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierM129paddingqDBjuR0$default);
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function0);
                    } else {
                        composerImpl.useNode();
                    }
                    Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy2, function22);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, function26);
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function24);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, function25);
                    BoxScopeInstance boxScopeInstance2 = BoxScopeInstance.INSTANCE;
                    DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = ContentColorKt.LocalContentColor;
                    DrawingState drawingState2 = (DrawingState) mutableState.getValue();
                    function33 = function32;
                    CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m456boximpl(z ? (drawingState2.labelWidth > ((drawingState2.indicatorRight - drawingState2.indicatorLeft) - drawingState2.iconWidth) ? 1 : (drawingState2.labelWidth == ((drawingState2.indicatorRight - drawingState2.indicatorLeft) - drawingState2.iconWidth) ? 0 : -1)) < 0 ? platformSliderColors2.labelColorOnIndicator : platformSliderColors2.labelColorOnTrack : platformSliderColors2.disabledLabelColor)), ComposableLambdaKt.rememberComposableLambda(-909551315, new Function2() { // from class: com.android.compose.PlatformSliderKt$Track$3$3$1
                        /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj, Object obj2) {
                            Composer composer2 = (Composer) obj;
                            if ((((Number) obj2).intValue() & 3) == 2) {
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                if (composerImpl3.getSkipping()) {
                                    composerImpl3.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.compose.Track.<anonymous>.<anonymous>.<anonymous> (PlatformSlider.kt:234)");
                                    }
                                    function33.invoke(Boolean.valueOf(z4), composer2, 0);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl), composerImpl, 56);
                    z5 = true;
                    composerImpl.end(true);
                } else {
                    function33 = function32;
                    z5 = true;
                }
                if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, z5)) {
                    ComposerKt.traceEventEnd();
                }
            }
            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function24);
            Function2 function252 = ComposeUiNode.Companion.SetModifier;
            Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, function252);
            Modifier.Companion companion3 = Modifier.Companion;
            Modifier modifierLayoutId3 = LayoutIdKt.layoutId(companion3, TrackComponent.Background);
            Dp.Companion companion22 = Dp.Companion;
            Function2 function262 = function2;
            m909TrackBackgroundYlGCr2M((DrawingState) mutableState.getValue(), z, platformSliderColors, f, f2 / 2, z2, modifierLayoutId3, composerImpl2, ((i3 >> 3) & 458752) | (i3 & 112) | 1572864 | (i3 & 896) | (i3 & 7168));
            z4 = z2;
            composerImpl = composerImpl2;
            platformSliderColors2 = platformSliderColors;
            composerImpl.startReplaceGroup(1898830432);
            if (function3 != null) {
            }
            composerImpl.end(false);
            composerImpl.startReplaceGroup(1898848398);
            if (function32 == null) {
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, z5)) {
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final PlatformSliderColors platformSliderColors3 = platformSliderColors2;
            final boolean z8 = z4;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.compose.PlatformSliderKt$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    Function3 function34 = function33;
                    Modifier modifier2 = modifier;
                    PlatformSliderKt.m908TrackpKeW4W0(sliderState, z, platformSliderColors3, f, f2, f3, z8, function3, function34, modifier2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:82:0x0130  */
    /* renamed from: TrackBackground-YlGCr2M, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m909TrackBackgroundYlGCr2M(final DrawingState drawingState, final boolean z, final PlatformSliderColors platformSliderColors, float f, final float f2, final boolean z2, final Modifier modifier, Composer composer, final int i) {
        int i2;
        float f3;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(705662017);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changed(drawingState) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changed(z) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changed(platformSliderColors) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            f3 = f;
            i2 |= composerImpl2.changed(f3) ? 2048 : 1024;
        } else {
            f3 = f;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl2.changed(f2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl2.changed(z2) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i2 |= composerImpl2.changed(modifier) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((599187 & i2) == 599186 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.compose.TrackBackground (PlatformSlider.kt:259)");
            }
            final State stateM8animateDpAsStateAjpBEmI = AnimateAsStateKt.m8animateDpAsStateAjpBEmI(z2 ? f3 : f2, null, "PlatformSliderCornersAnimation", composerImpl2, 384, 10);
            final State stateM7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(z ? platformSliderColors.trackColor : platformSliderColors.disabledTrackColor, null, "PlatformSliderTrackColorAnimation", composerImpl2, 384, 10);
            final State stateM7animateColorAsStateeuL9pac2 = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(z ? platformSliderColors.indicatorColor : platformSliderColors.disabledIndicatorColor, null, "PlatformSliderIndicatorColorAnimation", composerImpl2, 384, 10);
            composerImpl = composerImpl2;
            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(modifier, 1.0f);
            composerImpl.startReplaceGroup(-1607945898);
            boolean zChanged = ((i2 & 14) == 4) | composerImpl.changed(stateM7animateColorAsStateeuL9pac) | composerImpl.changed(stateM8animateDpAsStateAjpBEmI) | composerImpl.changed(stateM7animateColorAsStateeuL9pac2);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChanged) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function1() { // from class: com.android.compose.PlatformSliderKt$$ExternalSyntheticLambda4
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            State state = stateM7animateColorAsStateeuL9pac2;
                            DrawScope drawScope = (DrawScope) obj;
                            float f4 = 2;
                            float fIntBitsToFloat = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() & 4294967295L)) / f4;
                            float fIntBitsToFloat2 = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() & 4294967295L)) / f4;
                            long jFloatToRawIntBits = (Float.floatToRawIntBits(fIntBitsToFloat) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L);
                            CornerRadius.Companion companion = CornerRadius.Companion;
                            AndroidPath androidPathPath = AndroidPath_androidKt.Path();
                            DrawingState drawingState2 = drawingState;
                            Path.addRoundRect$default(androidPathPath, RoundRectKt.m414RoundRectgG7oq9Y(0.0f, 0.0f, drawingState2.totalWidth, drawingState2.totalHeight, jFloatToRawIntBits));
                            DrawScope.m539drawPathLG529CI$default(drawScope, androidPathPath, ((Color) stateM7animateColorAsStateeuL9pac.getValue()).value, 60);
                            State state2 = stateM8animateDpAsStateAjpBEmI;
                            float fMo58toPx0680j_4 = drawScope.mo58toPx0680j_4(((Dp) state2.getValue()).value);
                            float fMo58toPx0680j_42 = drawScope.mo58toPx0680j_4(((Dp) state2.getValue()).value);
                            long jFloatToRawIntBits2 = (Float.floatToRawIntBits(fMo58toPx0680j_4) << 32) | (4294967295L & Float.floatToRawIntBits(fMo58toPx0680j_42));
                            ClipOp.Companion.getClass();
                            int i3 = ClipOp.Intersect;
                            CanvasDrawScope$drawContext$1 drawContext = drawScope.getDrawContext();
                            long jM528getSizeNHjbRc = drawContext.m528getSizeNHjbRc();
                            drawContext.getCanvas().save();
                            try {
                                ((CanvasDrawScope$drawContext$1) drawContext.transform.$this_asDrawTransform).getCanvas().mo425clipPathmtrdDE(androidPathPath, i3);
                                AndroidPath androidPathPath2 = AndroidPath_androidKt.Path();
                                Path.addRoundRect$default(androidPathPath2, new RoundRect(drawingState2.indicatorLeft, drawingState2.indicatorTop, drawingState2.indicatorRight, drawingState2.indicatorBottom, jFloatToRawIntBits, jFloatToRawIntBits2, jFloatToRawIntBits2, jFloatToRawIntBits, null));
                                DrawScope.m539drawPathLG529CI$default(drawScope, androidPathPath2, ((Color) state.getValue()).value, 60);
                                BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, jM528getSizeNHjbRc);
                                return Unit.INSTANCE;
                            } catch (Throwable th) {
                                BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, jM528getSizeNHjbRc);
                                throw th;
                            }
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                CanvasKt.Canvas(modifierFillMaxSize, (Function1) objRememberedValue, composerImpl, 0);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final float f4 = f3;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.compose.PlatformSliderKt$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    boolean z3 = z2;
                    Modifier modifier2 = modifier;
                    PlatformSliderKt.m909TrackBackgroundYlGCr2M(drawingState, z, platformSliderColors, f4, f2, z3, modifier2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final float access$getCoercedNormalizedValue(SliderState sliderState) {
        float f = ((ClosedFloatRange) sliderState.valueRange)._endInclusive;
        ClosedFloatRange closedFloatRange = (ClosedFloatRange) sliderState.valueRange;
        float f2 = f - closedFloatRange._start;
        if (f2 == 0.0f) {
            return 0.0f;
        }
        return (RangesKt___RangesKt.coerceIn(sliderState.getValue(), closedFloatRange._start, closedFloatRange._endInclusive) - closedFloatRange._start) / f2;
    }
}
