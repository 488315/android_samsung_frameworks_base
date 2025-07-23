package com.android.systemui.volume.panel.component.volume.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.EnterTransition;
import androidx.compose.animation.ExitTransition;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.semantics.CustomAccessibilityAction;
import androidx.compose.ui.semantics.ProgressBarRangeInfo;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Dp;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.compose.PlatformSliderColors;
import com.android.compose.PlatformSliderKt;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.ui.compose.IconKt;
import com.android.systemui.compose.modifiers.SysuiTestTagKt;
import com.android.systemui.haptics.slider.SliderHapticFeedbackFilter;
import com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel;
import com.android.systemui.lifecycle.SysUiViewModelKt;
import com.android.systemui.volume.haptics.ui.VolumeHapticsConfigsProvider;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.Collections;
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
/* loaded from: classes3.dex */
public abstract class VolumeSliderKt {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v14, types: [androidx.compose.ui.Modifier] */
    public static final void LegacySliderIcon(final Icon.Loaded loaded, final Function0 function0, final boolean z, Modifier.Companion companion, Composer composer, final int i) {
        Modifier.Companion companion2;
        final Modifier.Companion companion3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1776881417);
        int i2 = i | (composerImpl.changed(loaded) ? 4 : 2) | (composerImpl.changedInstance(function0) ? 32 : 16) | (composerImpl.changed(z) ? 256 : 128) | 3072;
        if ((i2 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion3 = companion;
        } else {
            Modifier.Companion companion4 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.LegacySliderIcon (VolumeSlider.kt:338)");
            }
            if (z) {
                companion2 = companion4;
                companion4 = ClickableKt.m34clickableO2vRcR0$default(companion4, null, null, false, null, null, function0, 28);
            } else {
                companion2 = companion4;
            }
            Modifier fillMaxSize = SizeKt.fillMaxSize(companion4, 1.0f);
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, fillMaxSize);
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
            Dp.Companion companion5 = Dp.Companion;
            IconKt.m1072IconFNF3uiM(loaded, SizeKt.m139size3ABfNKs(companion2, 24), 0L, composerImpl, (i2 & 14) | 48, 4);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            companion3 = companion2;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(function0, z, companion3, i) { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$$ExternalSyntheticLambda9
                public final /* synthetic */ Function0 f$1;
                public final /* synthetic */ boolean f$2;
                public final /* synthetic */ Modifier.Companion f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    Icon.Loaded loaded2 = Icon.Loaded.this;
                    boolean z2 = this.f$2;
                    Modifier.Companion companion6 = this.f$3;
                    VolumeSliderKt.LegacySliderIcon(loaded2, this.f$1, z2, companion6, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void LegacyVolumeSlider(final SliderState sliderState, final Function1 function1, final Function0 function0, final PlatformSliderColors platformSliderColors, final SliderHapticsViewModel.Factory factory, final Modifier modifier, final Function0 function02, Composer composer, final int i) {
        int i2;
        MutableState mutableState;
        State state;
        final State state2;
        MutableInteractionSource mutableInteractionSource;
        boolean z;
        final SliderHapticsViewModel sliderHapticsViewModel;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1974027200);
        if ((i & 6) == 0) {
            i2 = ((i & 8) == 0 ? composerImpl.changed(sliderState) : composerImpl.changedInstance(sliderState) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function1) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= (i & 4096) == 0 ? composerImpl.changed(platformSliderColors) : composerImpl.changedInstance(platformSliderColors) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= (32768 & i) == 0 ? composerImpl.changed(factory) : composerImpl.changedInstance(factory) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changed(modifier) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i2 |= composerImpl.changedInstance(function02) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((599187 & i2) == 599186 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.LegacyVolumeSlider (VolumeSlider.kt:229)");
            }
            int i3 = i2 & 14;
            composerImpl.startReplaceGroup(-225683963);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.valueState (VolumeSlider.kt:320)");
            }
            composerImpl.startReplaceGroup(-159863603);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = SnapshotStateKt.mutableStateOf$default(sliderState);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            MutableState mutableState2 = (MutableState) rememberedValue;
            composerImpl.end(false);
            if ((((SliderState) mutableState2.getValue()) instanceof SliderState.Empty) || ((SliderState) mutableState2.getValue()).isEnabled() != sliderState.isEnabled()) {
                mutableState = mutableState2;
                composerImpl.startReplaceGroup(-660514390);
                composerImpl.startReplaceGroup(-159854248);
                Object rememberedValue2 = composerImpl.rememberedValue();
                if (rememberedValue2 == composer$Companion$Empty$1) {
                    rememberedValue2 = PrimitiveSnapshotStateKt.mutableFloatStateOf(sliderState.getValue());
                    composerImpl.updateRememberedValue(rememberedValue2);
                }
                state = (MutableFloatState) rememberedValue2;
                composerImpl.end(false);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(-159852321);
                mutableState = mutableState2;
                state = AnimateAsStateKt.animateFloatAsState(sliderState.getValue(), null, "VolumeSliderValueAnimation", null, composerImpl, 3072, 22);
                composerImpl = composerImpl;
                composerImpl.end(false);
            }
            mutableState.setValue(sliderState);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            Object m = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, -1104846250);
            if (m == composer$Companion$Empty$1) {
                m = InteractionSourceKt.MutableInteractionSource();
                composerImpl.updateRememberedValue(m);
            }
            final MutableInteractionSource mutableInteractionSource2 = (MutableInteractionSource) m;
            composerImpl.end(false);
            float floatValue = ((Number) state.getValue()).floatValue();
            final ClosedFloatingPointRange valueRange = sliderState.getValueRange();
            final SliderHapticFeedbackFilter hapticFilter = sliderState.getHapticFilter();
            composerImpl.startReplaceGroup(-1009755159);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.setUpHapticsViewModel (VolumeSlider.kt:364)");
            }
            if (factory == null) {
                state2 = state;
                mutableInteractionSource = mutableInteractionSource2;
                z = false;
                sliderHapticsViewModel = null;
            } else {
                composerImpl.startReplaceGroup(262373222);
                composerImpl.startReplaceGroup(1285083812);
                boolean changedInstance = composerImpl.changedInstance(factory) | composerImpl.changed(valueRange) | composerImpl.changedInstance(hapticFilter);
                Object rememberedValue3 = composerImpl.rememberedValue();
                if (changedInstance || rememberedValue3 == composer$Companion$Empty$1) {
                    rememberedValue3 = new Function0() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$$ExternalSyntheticLambda6
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Orientation orientation = Orientation.Horizontal;
                            VolumeHapticsConfigsProvider.INSTANCE.getClass();
                            ClosedFloatingPointRange closedFloatingPointRange = valueRange;
                            return SliderHapticsViewModel.Factory.this.create(mutableInteractionSource2, closedFloatingPointRange, orientation, VolumeHapticsConfigsProvider.sliderHapticFeedbackConfig(closedFloatingPointRange, hapticFilter), VolumeHapticsConfigsProvider.seekableSliderTrackerConfig);
                        }
                    };
                    composerImpl.updateRememberedValue(rememberedValue3);
                }
                composerImpl.end(false);
                state2 = state;
                SliderHapticsViewModel sliderHapticsViewModel2 = (SliderHapticsViewModel) SysUiViewModelKt.rememberViewModel("SliderHapticsViewModel", null, (Function0) rememberedValue3, composerImpl, 6, 2);
                composerImpl.startReplaceGroup(-132967130);
                Object rememberedValue4 = composerImpl.rememberedValue();
                if (rememberedValue4 == composer$Companion$Empty$1) {
                    rememberedValue4 = PrimitiveSnapshotStateKt.mutableFloatStateOf((float) Math.rint(floatValue));
                    composerImpl.updateRememberedValue(rememberedValue4);
                }
                MutableFloatState mutableFloatState = (MutableFloatState) rememberedValue4;
                composerImpl.end(false);
                Float valueOf = Float.valueOf(floatValue);
                composerImpl.startReplaceGroup(-132964034);
                boolean changed = composerImpl.changed(floatValue) | composerImpl.changedInstance(sliderHapticsViewModel2);
                Object rememberedValue5 = composerImpl.rememberedValue();
                if (changed || rememberedValue5 == composer$Companion$Empty$1) {
                    mutableInteractionSource = mutableInteractionSource2;
                    rememberedValue5 = new VolumeSliderKt$setUpHapticsViewModel$1$2$1$1(floatValue, mutableFloatState, sliderHapticsViewModel2, null);
                    composerImpl.updateRememberedValue(rememberedValue5);
                } else {
                    mutableInteractionSource = mutableInteractionSource2;
                }
                z = false;
                composerImpl.end(false);
                EffectsKt.LaunchedEffect(composerImpl, valueOf, (Function2) rememberedValue5);
                composerImpl.end(false);
                sliderHapticsViewModel = sliderHapticsViewModel2;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(z);
            Modifier sysuiResTag = SysuiTestTagKt.sysuiResTag(modifier, sliderState.getLabel());
            composerImpl.startReplaceGroup(-1104832359);
            int i4 = i2 & 112;
            boolean changed2 = (i3 == 4 || ((i2 & 8) != 0 && composerImpl.changedInstance(sliderState))) | ((i2 & 896) == 256) | composerImpl.changed(state2) | (i4 == 32);
            Object rememberedValue6 = composerImpl.rememberedValue();
            if (changed2 || rememberedValue6 == composer$Companion$Empty$1) {
                rememberedValue6 = new Function1() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        String label;
                        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj;
                        final SliderState sliderState2 = SliderState.this;
                        if (sliderState2.isEnabled()) {
                            SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, sliderState2.getLabel());
                            String a11yClickDescription = sliderState2.getA11yClickDescription();
                            if (a11yClickDescription != null) {
                                final Function0 function03 = function0;
                                SemanticsPropertiesKt.setCustomActions(semanticsPropertyReceiver, Collections.singletonList(new CustomAccessibilityAction(a11yClickDescription, new Function0() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$$ExternalSyntheticLambda7
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        Function0.this.invoke();
                                        return Boolean.TRUE;
                                    }
                                })));
                            }
                            String a11yStateDescription = sliderState2.getA11yStateDescription();
                            if (a11yStateDescription != null) {
                                SemanticsPropertiesKt.setStateDescription(semanticsPropertyReceiver, a11yStateDescription);
                            }
                            SemanticsPropertiesKt.setProgressBarRangeInfo(semanticsPropertyReceiver, new ProgressBarRangeInfo(sliderState2.getValue(), sliderState2.getValueRange(), 0, 4, null));
                        } else {
                            SemanticsPropertiesKt.disabled(semanticsPropertyReceiver);
                            String disabledMessage = sliderState2.getDisabledMessage();
                            if (disabledMessage == null || (label = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sliderState2.getLabel(), ", ", disabledMessage)) == null) {
                                label = sliderState2.getLabel();
                            }
                            SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, label);
                        }
                        final State state3 = state2;
                        final Function1 function12 = function1;
                        SemanticsPropertiesKt.setProgress$default(semanticsPropertyReceiver, new Function1() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$$ExternalSyntheticLambda8
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo779invoke(Object obj2) {
                                float floatValue2 = ((Float) obj2).floatValue();
                                State state4 = state3;
                                int i5 = floatValue2 > ((Number) state4.getValue()).floatValue() ? 1 : floatValue2 < ((Number) state4.getValue()).floatValue() ? -1 : 0;
                                float floatValue3 = ((Number) state4.getValue()).floatValue();
                                SliderState sliderState3 = SliderState.this;
                                function12.mo779invoke(Float.valueOf(RangesKt___RangesKt.coerceIn((sliderState3.getStep() * i5) + floatValue3, ((ClosedFloatRange) sliderState3.getValueRange())._start, ((ClosedFloatRange) sliderState3.getValueRange())._endInclusive)));
                                return Boolean.TRUE;
                            }
                        });
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue6);
            }
            composerImpl.end(false);
            Modifier clearAndSetSemantics = SemanticsModifierKt.clearAndSetSemantics(sysuiResTag, (Function1) rememberedValue6);
            float floatValue2 = ((Number) state2.getValue()).floatValue();
            ClosedFloatingPointRange valueRange2 = sliderState.getValueRange();
            boolean isEnabled = sliderState.isEnabled();
            composerImpl.startReplaceGroup(-1104783481);
            boolean changedInstance2 = (i4 == 32) | composerImpl.changedInstance(sliderHapticsViewModel);
            Object rememberedValue7 = composerImpl.rememberedValue();
            if (changedInstance2 || rememberedValue7 == composer$Companion$Empty$1) {
                rememberedValue7 = new Function1() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$$ExternalSyntheticLambda3
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        Float f = (Float) obj;
                        float floatValue3 = f.floatValue();
                        SliderHapticsViewModel sliderHapticsViewModel3 = SliderHapticsViewModel.this;
                        if (sliderHapticsViewModel3 != null) {
                            sliderHapticsViewModel3.addVelocityDataPoint(floatValue3);
                        }
                        function1.mo779invoke(f);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue7);
            }
            Function1 function12 = (Function1) rememberedValue7;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-1104778567);
            boolean changedInstance3 = composerImpl.changedInstance(sliderHapticsViewModel) | ((3670016 & i2) == 1048576);
            Object rememberedValue8 = composerImpl.rememberedValue();
            if (changedInstance3 || rememberedValue8 == composer$Companion$Empty$1) {
                rememberedValue8 = new Function0() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$$ExternalSyntheticLambda4
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        SliderHapticsViewModel sliderHapticsViewModel3 = SliderHapticsViewModel.this;
                        if (sliderHapticsViewModel3 != null) {
                            sliderHapticsViewModel3.onValueChangeEnded();
                        }
                        Function0 function03 = function02;
                        if (function03 != null) {
                            function03.invoke();
                        }
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue8);
            }
            composerImpl.end(false);
            PlatformSliderKt.m905PlatformSliderWu8B24Y(floatValue2, function12, clearAndSetSemantics, (Function0) rememberedValue8, valueRange2, isEnabled, mutableInteractionSource, platformSliderColors, 0.0f, ComposableLambdaKt.rememberComposableLambda(59388120, new Function3() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$LegacyVolumeSlider$4
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    ((Boolean) obj).getClass();
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 17) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.LegacyVolumeSlider.<anonymous> (VolumeSlider.kt:292)");
                    }
                    SliderState sliderState2 = SliderState.this;
                    Icon.Loaded icon = sliderState2.getIcon();
                    if (icon != null) {
                        VolumeSliderKt.LegacySliderIcon(icon, function0, sliderState2.isMutable(), null, composer2, 0);
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), ComposableLambdaKt.rememberComposableLambda(-561916982, new Function3() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$LegacyVolumeSlider$5
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    Composer composer2 = (Composer) obj2;
                    int intValue = ((Number) obj3).intValue();
                    if ((intValue & 6) == 0) {
                        intValue |= ((ComposerImpl) composer2).changed(booleanValue) ? 4 : 2;
                    }
                    if ((intValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.LegacyVolumeSlider.<anonymous> (VolumeSlider.kt:302)");
                    }
                    EnterTransition fadeIn$default = EnterExitTransitionKt.fadeIn$default(AnimationSpecKt.tween$default(150, 0, null, 6), 2);
                    ExitTransition fadeOut$default = EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(150, 0, null, 6), 2);
                    final SliderState sliderState2 = SliderState.this;
                    AnimatedVisibilityKt.AnimatedVisibility(!booleanValue, null, fadeIn$default, fadeOut$default, null, ComposableLambdaKt.rememberComposableLambda(-1000495966, new Function3() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$LegacyVolumeSlider$5.1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj4, Object obj5, Object obj6) {
                            Composer composer3 = (Composer) obj5;
                            ((Number) obj6).intValue();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.LegacyVolumeSlider.<anonymous>.<anonymous> (VolumeSlider.kt:307)");
                            }
                            Modifier.Companion companion = Modifier.Companion;
                            SliderState sliderState3 = SliderState.this;
                            VolumeSliderContentKt.VolumeSliderContent(sliderState3.getLabel(), sliderState3.isEnabled(), sliderState3.getDisabledMessage(), companion, composer3, 3072);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    }, composer2), composer2, 200064, 18);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, ((i2 << 12) & 29360128) | 806879232);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    Modifier modifier2 = modifier;
                    Function0 function03 = function02;
                    VolumeSliderKt.LegacyVolumeSlider(SliderState.this, function1, function0, platformSliderColors, factory, modifier2, function03, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:71:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:82:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00f0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void VolumeSlider(final com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState r14, final kotlin.jvm.functions.Function1 r15, final kotlin.jvm.functions.Function0 r16, final com.android.compose.PlatformSliderColors r17, final androidx.compose.ui.Modifier r18, final com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel.Factory r19, final kotlin.jvm.functions.Function0 r20, final kotlin.jvm.functions.Function3 r21, androidx.compose.runtime.Composer r22, final int r23, final int r24) {
        /*
            Method dump skipped, instructions count: 339
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt.VolumeSlider(com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function0, com.android.compose.PlatformSliderColors, androidx.compose.ui.Modifier, com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel$Factory, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int, int):void");
    }
}
