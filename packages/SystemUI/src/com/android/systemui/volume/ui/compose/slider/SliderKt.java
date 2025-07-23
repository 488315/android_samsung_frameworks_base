package com.android.systemui.volume.ui.compose.slider;

import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.material3.SliderColors;
import androidx.compose.material3.SliderState;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.semantics.ProgressBarRangeInfo;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel;
import com.android.systemui.lifecycle.SysUiViewModelKt;
import com.android.systemui.volume.haptics.ui.VolumeHapticsConfigsProvider;
import com.android.systemui.volume.ui.compose.slider.Haptics;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.ranges.ClosedFloatRange;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class SliderKt {
    public static final void Slider(final float f, final ClosedFloatingPointRange closedFloatingPointRange, final Function1 function1, final Function1 function12, final boolean z, final AccessibilityParams accessibilityParams, Modifier modifier, final float f2, final SliderColors sliderColors, final MutableInteractionSource mutableInteractionSource, final Haptics haptics, final boolean z2, final boolean z3, final ComposableLambdaImpl composableLambdaImpl, final ComposableLambdaImpl composableLambdaImpl2, Composer composer, final int i) {
        int i2;
        Composer$Companion$Empty$1 composer$Companion$Empty$1;
        MutableState mutableState;
        int i3;
        State animateFloatAsState;
        float f3;
        ComposerImpl composerImpl;
        State state;
        ComposerImpl composerImpl2;
        boolean z4;
        final SliderHapticsViewModel sliderHapticsViewModel;
        ComposerImpl composerImpl3;
        final Modifier modifier2;
        final ComposableLambdaImpl composableLambdaImpl3;
        ComposerImpl composerImpl4 = (ComposerImpl) composer;
        composerImpl4.startRestartGroup(436381249);
        int i4 = i | (composerImpl4.changed(f) ? 4 : 2) | (composerImpl4.changed(closedFloatingPointRange) ? 32 : 16) | (composerImpl4.changedInstance(function1) ? 256 : 128) | (composerImpl4.changedInstance(function12) ? 2048 : 1024) | (composerImpl4.changed(z) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192) | (composerImpl4.changed(accessibilityParams) ? 131072 : 65536) | (composerImpl4.changed(modifier) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) | (composerImpl4.changed(sliderColors) ? 67108864 : 33554432);
        int i5 = 28080 | (composerImpl4.changed(haptics) ? 4 : 2);
        if ((i4 & 306783379) == 306783378 && (i5 & 9363) == 9362 && composerImpl4.getSkipping()) {
            composerImpl4.skipToGroupEnd();
            composableLambdaImpl3 = composableLambdaImpl2;
            modifier2 = modifier;
        } else {
            composerImpl4.startDefaults();
            if ((i & 1) != 0 && !composerImpl4.getDefaultsInvalid()) {
                composerImpl4.skipToGroupEnd();
            }
            composerImpl4.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.ui.compose.slider.Slider (Slider.kt:82)");
            }
            if (f2 < 0.0f) {
                throw new IllegalArgumentException("stepDistance must not be negative");
            }
            int i6 = (i4 & 14) | ((i4 >> 9) & 112);
            composerImpl4.startReplaceGroup(-45655492);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.ui.compose.slider.valueState (Slider.kt:135)");
            }
            composerImpl4.startReplaceGroup(670584729);
            Object rememberedValue = composerImpl4.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$12 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$12) {
                rememberedValue = PrimitiveSnapshotStateKt.mutableFloatStateOf(f);
                composerImpl4.updateRememberedValue(rememberedValue);
            }
            MutableFloatState mutableFloatState = (MutableFloatState) rememberedValue;
            Object m = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl4, false, 670586930);
            if (m == composer$Companion$Empty$12) {
                m = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(z));
                composerImpl4.updateRememberedValue(m);
            }
            MutableState mutableState2 = (MutableState) m;
            composerImpl4.end(false);
            if (((Boolean) mutableState2.getValue()).booleanValue() != z) {
                animateFloatAsState = PrimitiveSnapshotStateKt.mutableFloatStateOf(f);
                mutableState = mutableState2;
                i3 = i4;
                i2 = i5;
                composer$Companion$Empty$1 = composer$Companion$Empty$12;
                composerImpl = composerImpl4;
                f3 = f;
            } else {
                i2 = i5;
                composer$Companion$Empty$1 = composer$Companion$Empty$12;
                mutableState = mutableState2;
                i3 = i4;
                animateFloatAsState = AnimateAsStateKt.animateFloatAsState(f, AnimationSpecKt.spring$default(1.0f, 1500.0f, null, 4), "VolumeSliderValueAnimation", null, composerImpl4, (i6 & 14) | 3120, 20);
                f3 = f;
                composerImpl = composerImpl4;
            }
            ((SnapshotMutableFloatStateImpl) mutableFloatState).setFloatValue(f3);
            mutableState.setValue(Boolean.valueOf(z));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            float floatValue = ((Number) animateFloatAsState.getValue()).floatValue();
            int i7 = (i2 & 14) | ((i3 << 3) & 896) | 3072;
            composerImpl.startReplaceGroup(331219540);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.ui.compose.slider.createViewModel (Slider.kt:200)");
            }
            if (haptics instanceof Haptics.Disabled) {
                composerImpl2 = composerImpl;
                state = animateFloatAsState;
                z4 = false;
                sliderHapticsViewModel = null;
            } else {
                if (!(haptics instanceof Haptics.Enabled)) {
                    throw new NoWhenBranchMatchedException();
                }
                final SliderHapticsViewModel.Factory factory = ((Haptics.Enabled) haptics).hapticsViewModelFactory;
                composerImpl.startReplaceGroup(-1651328159);
                composerImpl.startReplaceGroup(-1273302797);
                boolean changedInstance = ((((i7 & 14) ^ 6) > 4 && composerImpl.changedInstance(haptics)) || (i7 & 6) == 4) | composerImpl.changedInstance(factory) | ((((i7 & 896) ^ 384) > 256 && composerImpl.changed(closedFloatingPointRange)) || (i7 & 384) == 256);
                Object rememberedValue2 = composerImpl.rememberedValue();
                if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
                    final Haptics.Enabled enabled = (Haptics.Enabled) haptics;
                    rememberedValue2 = new Function0() { // from class: com.android.systemui.volume.ui.compose.slider.SliderKt$$ExternalSyntheticLambda3
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Haptics.Enabled enabled2 = enabled;
                            Orientation orientation = enabled2.orientation;
                            VolumeHapticsConfigsProvider.INSTANCE.getClass();
                            ClosedFloatingPointRange closedFloatingPointRange2 = closedFloatingPointRange;
                            return SliderHapticsViewModel.Factory.this.create(mutableInteractionSource, closedFloatingPointRange2, orientation, VolumeHapticsConfigsProvider.sliderHapticFeedbackConfig(closedFloatingPointRange2, enabled2.hapticFilter), VolumeHapticsConfigsProvider.seekableSliderTrackerConfig);
                        }
                    };
                    composerImpl.updateRememberedValue(rememberedValue2);
                }
                composerImpl.end(false);
                state = animateFloatAsState;
                composerImpl2 = composerImpl;
                SliderHapticsViewModel sliderHapticsViewModel2 = (SliderHapticsViewModel) SysUiViewModelKt.rememberViewModel("SliderHapticsViewModel", null, (Function0) rememberedValue2, composerImpl, 6, 2);
                composerImpl2.startReplaceGroup(-1154700351);
                Object rememberedValue3 = composerImpl2.rememberedValue();
                if (rememberedValue3 == composer$Companion$Empty$1) {
                    rememberedValue3 = PrimitiveSnapshotStateKt.mutableFloatStateOf(floatValue);
                    composerImpl2.updateRememberedValue(rememberedValue3);
                }
                MutableFloatState mutableFloatState2 = (MutableFloatState) rememberedValue3;
                composerImpl2.end(false);
                Float valueOf = Float.valueOf(floatValue);
                composerImpl2.startReplaceGroup(-1154697144);
                boolean changed = composerImpl2.changed(floatValue) | composerImpl2.changedInstance(sliderHapticsViewModel2);
                Object rememberedValue4 = composerImpl2.rememberedValue();
                if (changed || rememberedValue4 == composer$Companion$Empty$1) {
                    rememberedValue4 = new SliderKt$createViewModel$1$2$1$1(floatValue, mutableFloatState2, sliderHapticsViewModel2, null);
                    composerImpl2.updateRememberedValue(rememberedValue4);
                }
                z4 = false;
                composerImpl2.end(false);
                EffectsKt.LaunchedEffect(composerImpl2, valueOf, (Function2) rememberedValue4);
                composerImpl2.end(false);
                sliderHapticsViewModel = sliderHapticsViewModel2;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(z4);
            composerImpl2.startReplaceGroup(-655415552);
            boolean z5 = (i3 & 112) == 32;
            Object rememberedValue5 = composerImpl2.rememberedValue();
            if (z5 || rememberedValue5 == composer$Companion$Empty$1) {
                composerImpl3 = composerImpl2;
                SliderState sliderState = new SliderState(((Number) state.getValue()).floatValue(), 0, null, closedFloatingPointRange, 6, null);
                composerImpl3.updateRememberedValue(sliderState);
                rememberedValue5 = sliderState;
            } else {
                composerImpl3 = composerImpl2;
            }
            SliderState sliderState2 = (SliderState) rememberedValue5;
            composerImpl3.end(false);
            composerImpl3.startReplaceGroup(-655411597);
            boolean changedInstance2 = composerImpl3.changedInstance(sliderHapticsViewModel) | ((i3 & 896) == 256);
            Object rememberedValue6 = composerImpl3.rememberedValue();
            if (changedInstance2 || rememberedValue6 == composer$Companion$Empty$1) {
                rememberedValue6 = new Function1() { // from class: com.android.systemui.volume.ui.compose.slider.SliderKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        Float f4 = (Float) obj;
                        float floatValue2 = f4.floatValue();
                        SliderHapticsViewModel sliderHapticsViewModel3 = SliderHapticsViewModel.this;
                        if (sliderHapticsViewModel3 != null) {
                            sliderHapticsViewModel3.onValueChange(floatValue2);
                        }
                        function1.mo779invoke(f4);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl3.updateRememberedValue(rememberedValue6);
            }
            final Function1 function13 = (Function1) rememberedValue6;
            composerImpl3.end(false);
            final float floatValue2 = ((Number) state.getValue()).floatValue();
            Function1 function14 = new Function1() { // from class: com.android.systemui.volume.ui.compose.slider.SliderKt$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj;
                    AccessibilityParams accessibilityParams2 = AccessibilityParams.this;
                    SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, accessibilityParams2.contentDescription);
                    boolean z6 = z;
                    final float f4 = floatValue2;
                    final ClosedFloatingPointRange closedFloatingPointRange2 = closedFloatingPointRange;
                    if (z6) {
                        String str = accessibilityParams2.stateDescription;
                        if (str != null) {
                            SemanticsPropertiesKt.setStateDescription(semanticsPropertyReceiver, str);
                        }
                        SemanticsPropertiesKt.setProgressBarRangeInfo(semanticsPropertyReceiver, new ProgressBarRangeInfo(f4, closedFloatingPointRange2, 0, 4, null));
                    } else {
                        SemanticsPropertiesKt.disabled(semanticsPropertyReceiver);
                    }
                    final float f5 = f2;
                    final Function1 function15 = function13;
                    SemanticsPropertiesKt.setProgress$default(semanticsPropertyReceiver, new Function1() { // from class: com.android.systemui.volume.ui.compose.slider.SliderKt$$ExternalSyntheticLambda5
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj2) {
                            float floatValue3 = ((Float) obj2).floatValue();
                            float f6 = f4;
                            float f7 = floatValue3 > f6 ? 1.0f : floatValue3 < f6 ? -1.0f : 0.0f;
                            float f8 = f5;
                            float f9 = f8 > 0.0f ? f7 * f8 : floatValue3 - f6;
                            ClosedFloatRange closedFloatRange = (ClosedFloatRange) closedFloatingPointRange2;
                            function15.mo779invoke(Float.valueOf(RangesKt___RangesKt.coerceIn(f6 + f9, closedFloatRange._start, closedFloatRange._endInclusive)));
                            return Boolean.TRUE;
                        }
                    });
                    return Unit.INSTANCE;
                }
            };
            composerImpl3.startReplaceGroup(-655400201);
            final State state2 = state;
            boolean changedInstance3 = composerImpl3.changedInstance(sliderHapticsViewModel) | ((i3 & 7168) == 2048) | composerImpl3.changed(state2);
            Object rememberedValue7 = composerImpl3.rememberedValue();
            if (changedInstance3 || rememberedValue7 == composer$Companion$Empty$1) {
                rememberedValue7 = new Function0() { // from class: com.android.systemui.volume.ui.compose.slider.SliderKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        SliderHapticsViewModel sliderHapticsViewModel3 = SliderHapticsViewModel.this;
                        if (sliderHapticsViewModel3 != null) {
                            sliderHapticsViewModel3.onValueChangeEnded();
                        }
                        Function1 function15 = function12;
                        if (function15 != null) {
                            function15.mo779invoke(Float.valueOf(((Number) state2.getValue()).floatValue()));
                        }
                        return Unit.INSTANCE;
                    }
                };
                composerImpl3.updateRememberedValue(rememberedValue7);
            }
            composerImpl3.end(false);
            sliderState2.onValueChangeFinished = (Function0) rememberedValue7;
            sliderState2.onValueChange = function13;
            sliderState2.setValue(((Number) state2.getValue()).floatValue());
            if (z2) {
                composerImpl3.startReplaceGroup(1157646197);
                ComposerImpl composerImpl5 = composerImpl3;
                modifier2 = modifier;
                composableLambdaImpl3 = composableLambdaImpl2;
                androidx.compose.material3.SliderKt.VerticalSlider(sliderState2, SemanticsModifierKt.clearAndSetSemantics(modifier, function14), z, z3, sliderColors, mutableInteractionSource, ComposableLambdaKt.rememberComposableLambda(-1963600842, new Function3() { // from class: com.android.systemui.volume.ui.compose.slider.SliderKt$Slider$5
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        SliderState sliderState3 = (SliderState) obj;
                        Composer composer2 = (Composer) obj2;
                        int intValue = ((Number) obj3).intValue();
                        if ((intValue & 6) == 0) {
                            intValue |= (intValue & 8) == 0 ? ((ComposerImpl) composer2).changed(sliderState3) : ((ComposerImpl) composer2).changedInstance(sliderState3) ? 4 : 2;
                        }
                        if ((intValue & 19) == 18) {
                            ComposerImpl composerImpl6 = (ComposerImpl) composer2;
                            if (composerImpl6.getSkipping()) {
                                composerImpl6.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.volume.ui.compose.slider.Slider.<anonymous> (Slider.kt:118)");
                        }
                        Function4.this.invoke(sliderState3, mutableInteractionSource, composer2, Integer.valueOf((intValue & 14) | 8));
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl3), composableLambdaImpl, composerImpl5, ((i3 >> 6) & 896) | 1575944 | ((i3 >> 12) & 57344) | 12779520, 0);
                composerImpl4 = composerImpl5;
                composerImpl4.end(false);
            } else {
                composerImpl4 = composerImpl3;
                modifier2 = modifier;
                composableLambdaImpl3 = composableLambdaImpl2;
                composerImpl4.startReplaceGroup(1158031248);
                androidx.compose.material3.SliderKt.Slider(sliderState2, SemanticsModifierKt.clearAndSetSemantics(modifier2, function14), z, sliderColors, mutableInteractionSource, ComposableLambdaKt.rememberComposableLambda(-1054852860, new Function3() { // from class: com.android.systemui.volume.ui.compose.slider.SliderKt$Slider$6
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        SliderState sliderState3 = (SliderState) obj;
                        Composer composer2 = (Composer) obj2;
                        int intValue = ((Number) obj3).intValue();
                        if ((intValue & 6) == 0) {
                            intValue |= (intValue & 8) == 0 ? ((ComposerImpl) composer2).changed(sliderState3) : ((ComposerImpl) composer2).changedInstance(sliderState3) ? 4 : 2;
                        }
                        if ((intValue & 19) == 18) {
                            ComposerImpl composerImpl6 = (ComposerImpl) composer2;
                            if (composerImpl6.getSkipping()) {
                                composerImpl6.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.volume.ui.compose.slider.Slider.<anonymous> (Slider.kt:128)");
                        }
                        Function4.this.invoke(sliderState3, mutableInteractionSource, composer2, Integer.valueOf((intValue & 14) | 8));
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl4), composableLambdaImpl, composerImpl4, ((i3 >> 6) & 896) | 196616 | ((i3 >> 15) & 7168) | 1597440, 0);
                composerImpl4.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl4.endRestartGroup();
        if (endRestartGroup != null) {
            final ComposableLambdaImpl composableLambdaImpl4 = composableLambdaImpl3;
            endRestartGroup.block = new Function2(f, closedFloatingPointRange, function1, function12, z, accessibilityParams, modifier2, f2, sliderColors, mutableInteractionSource, haptics, z2, z3, composableLambdaImpl, composableLambdaImpl4, i) { // from class: com.android.systemui.volume.ui.compose.slider.SliderKt$$ExternalSyntheticLambda2
                public final /* synthetic */ float f$0;
                public final /* synthetic */ ClosedFloatingPointRange f$1;
                public final /* synthetic */ Haptics f$10;
                public final /* synthetic */ boolean f$11;
                public final /* synthetic */ boolean f$12;
                public final /* synthetic */ ComposableLambdaImpl f$13;
                public final /* synthetic */ ComposableLambdaImpl f$14;
                public final /* synthetic */ Function1 f$2;
                public final /* synthetic */ Function1 f$3;
                public final /* synthetic */ boolean f$4;
                public final /* synthetic */ AccessibilityParams f$5;
                public final /* synthetic */ Modifier f$6;
                public final /* synthetic */ float f$7;
                public final /* synthetic */ SliderColors f$8;
                public final /* synthetic */ MutableInteractionSource f$9;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(817889281);
                    AccessibilityParams accessibilityParams2 = this.f$5;
                    SliderColors sliderColors2 = this.f$8;
                    ComposableLambdaImpl composableLambdaImpl5 = this.f$14;
                    SliderKt.Slider(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, accessibilityParams2, this.f$6, this.f$7, sliderColors2, this.f$9, this.f$10, this.f$11, this.f$12, this.f$13, composableLambdaImpl5, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
