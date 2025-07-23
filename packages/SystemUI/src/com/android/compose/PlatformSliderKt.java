package com.android.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.SliderKt;
import androidx.compose.material3.SliderState;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Dp;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PlatformSliderKt {
    /* renamed from: PlatformSlider-Wu8B24Y, reason: not valid java name */
    public static final void m905PlatformSliderWu8B24Y(final float f, final Function1 function1, final Modifier modifier, final Function0 function0, final ClosedFloatingPointRange closedFloatingPointRange, final boolean z, final MutableInteractionSource mutableInteractionSource, final PlatformSliderColors platformSliderColors, float f2, final ComposableLambdaImpl composableLambdaImpl, final ComposableLambdaImpl composableLambdaImpl2, Composer composer, final int i) {
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
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            final MutableState mutableState = (MutableState) rememberedValue;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(1855799251);
            int i5 = i4 & 3670016;
            boolean z2 = i5 == 1048576;
            Object rememberedValue2 = composerImpl.rememberedValue();
            final float f6 = f3;
            if (z2 || rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new PlatformSliderKt$PlatformSlider$2$1(mutableInteractionSource, mutableState, null);
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, mutableInteractionSource, (Function2) rememberedValue2);
            Modifier m130height3ABfNKs = SizeKt.m130height3ABfNKs(modifier, f5);
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m130height3ABfNKs);
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
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            Modifier.Companion companion2 = Modifier.Companion;
            platformSliderColors2 = platformSliderColors;
            SliderKt.Slider(f, function1, SizeKt.fillMaxSize(companion2, 1.0f), z, function0, null, mutableInteractionSource, 0, ComposableLambdaKt.rememberComposableLambda(673011712, new Function3() { // from class: com.android.compose.PlatformSliderKt$PlatformSlider$3$1
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 17) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.compose.PlatformSlider.<anonymous>.<anonymous> (PlatformSlider.kt:141)");
                    }
                    SpacerKt.Spacer(composer2, SizeKt.m139size3ABfNKs(Modifier.Companion, f5));
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), ComposableLambdaKt.rememberComposableLambda(-1677983679, new Function3() { // from class: com.android.compose.PlatformSliderKt$PlatformSlider$3$2
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    SliderState sliderState = (SliderState) obj;
                    Composer composer2 = (Composer) obj2;
                    int intValue = ((Number) obj3).intValue();
                    if ((intValue & 6) == 0) {
                        intValue |= (intValue & 8) == 0 ? ((ComposerImpl) composer2).changed(sliderState) : ((ComposerImpl) composer2).changedInstance(sliderState) ? 4 : 2;
                    }
                    if ((intValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.compose.PlatformSlider.<anonymous>.<anonymous> (PlatformSlider.kt:128)");
                    }
                    PlatformSliderKt.m906TrackpKeW4W0(sliderState, z, platformSliderColors, f6, f5, f5, ((Boolean) mutableState.getValue()).booleanValue(), composableLambdaImpl3, composableLambdaImpl2, SizeKt.fillMaxSize(Modifier.Companion, 1.0f), composer2, 805527560 | (intValue & 14));
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), closedFloatingPointRange, composerImpl, (i4 & 14) | 905970048 | (i4 & 112) | ((i4 >> 6) & 7168) | ((i4 << 3) & 57344) | i5, (i4 >> 12) & 14, 160);
            composerImpl.startReplaceGroup(-1156637088);
            if (z) {
                SpacerKt.Spacer(composerImpl, BackgroundKt.m26backgroundbw27NRU(boxScopeInstance.align(SizeKt.m139size3ABfNKs(PaddingKt.m124padding3ABfNKs(companion2, 8), 4), Alignment.Companion.CenterEnd), platformSliderColors2.indicatorColor, RoundedCornerShapeKt.CircleShape));
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                ComposerKt.traceEventEnd();
            }
            f4 = f6;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final PlatformSliderColors platformSliderColors3 = platformSliderColors2;
            endRestartGroup.block = new Function2() { // from class: com.android.compose.PlatformSliderKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ComposableLambdaImpl composableLambdaImpl4 = composableLambdaImpl;
                    ComposableLambdaImpl composableLambdaImpl5 = composableLambdaImpl2;
                    PlatformSliderKt.m905PlatformSliderWu8B24Y(f, function1, modifier, function0, closedFloatingPointRange, z, mutableInteractionSource, platformSliderColors3, f4, composableLambdaImpl4, composableLambdaImpl5, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:102:0x01c3, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r0.rememberedValue(), java.lang.Integer.valueOf(r1)) == false) goto L109;
     */
    /* renamed from: Track-pKeW4W0, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m906TrackpKeW4W0(final androidx.compose.material3.SliderState r32, final boolean r33, com.android.compose.PlatformSliderColors r34, final float r35, final float r36, final float r37, boolean r38, final kotlin.jvm.functions.Function3 r39, kotlin.jvm.functions.Function3 r40, final androidx.compose.ui.Modifier r41, androidx.compose.runtime.Composer r42, final int r43) {
        /*
            Method dump skipped, instructions count: 1082
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.PlatformSliderKt.m906TrackpKeW4W0(androidx.compose.material3.SliderState, boolean, com.android.compose.PlatformSliderColors, float, float, float, boolean, kotlin.jvm.functions.Function3, kotlin.jvm.functions.Function3, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:77:0x012e, code lost:
    
        if (r13 == androidx.compose.runtime.Composer.Companion.Empty) goto L82;
     */
    /* renamed from: TrackBackground-YlGCr2M, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m907TrackBackgroundYlGCr2M(final com.android.compose.DrawingState r19, final boolean r20, final com.android.compose.PlatformSliderColors r21, float r22, final float r23, final boolean r24, final androidx.compose.ui.Modifier r25, androidx.compose.runtime.Composer r26, final int r27) {
        /*
            Method dump skipped, instructions count: 346
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.PlatformSliderKt.m907TrackBackgroundYlGCr2M(com.android.compose.DrawingState, boolean, com.android.compose.PlatformSliderColors, float, float, boolean, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
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
