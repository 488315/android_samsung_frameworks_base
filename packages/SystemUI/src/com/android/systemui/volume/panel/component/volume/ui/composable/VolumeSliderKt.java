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
            Modifier.Companion companionM34clickableO2vRcR0$default = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.LegacySliderIcon (VolumeSlider.kt:338)");
            }
            if (z) {
                companion2 = companionM34clickableO2vRcR0$default;
                companionM34clickableO2vRcR0$default = ClickableKt.m34clickableO2vRcR0$default(companionM34clickableO2vRcR0$default, null, null, false, null, null, function0, 28);
            } else {
                companion2 = companionM34clickableO2vRcR0$default;
            }
            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(companionM34clickableO2vRcR0$default, 1.0f);
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierFillMaxSize);
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
            Dp.Companion companion4 = Dp.Companion;
            IconKt.m1074IconFNF3uiM(loaded, SizeKt.m140size3ABfNKs(companion2, 24), 0L, composerImpl, (i2 & 14) | 48, 4);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            companion3 = companion2;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(function0, z, companion3, i) { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$$ExternalSyntheticLambda9
                public final /* synthetic */ Function0 f$1;
                public final /* synthetic */ boolean f$2;
                public final /* synthetic */ Modifier.Companion f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    Icon.Loaded loaded2 = this.f$0;
                    boolean z2 = this.f$2;
                    Modifier.Companion companion5 = this.f$3;
                    VolumeSliderKt.LegacySliderIcon(loaded2, this.f$1, z2, companion5, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void LegacyVolumeSlider(final SliderState sliderState, final Function1 function1, final Function0 function0, final PlatformSliderColors platformSliderColors, final SliderHapticsViewModel.Factory factory, final Modifier modifier, final Function0 function02, Composer composer, final int i) {
        int i2;
        MutableState mutableState;
        State stateAnimateFloatAsState;
        final State state;
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
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(sliderState);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            MutableState mutableState2 = (MutableState) objRememberedValue;
            composerImpl.end(false);
            if ((((SliderState) mutableState2.getValue()) instanceof SliderState.Empty) || ((SliderState) mutableState2.getValue()).isEnabled() != sliderState.isEnabled()) {
                mutableState = mutableState2;
                composerImpl.startReplaceGroup(-660514390);
                composerImpl.startReplaceGroup(-159854248);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (objRememberedValue2 == composer$Companion$Empty$1) {
                    objRememberedValue2 = PrimitiveSnapshotStateKt.mutableFloatStateOf(sliderState.getValue());
                    composerImpl.updateRememberedValue(objRememberedValue2);
                }
                stateAnimateFloatAsState = (MutableFloatState) objRememberedValue2;
                composerImpl.end(false);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(-159852321);
                mutableState = mutableState2;
                stateAnimateFloatAsState = AnimateAsStateKt.animateFloatAsState(sliderState.getValue(), null, "VolumeSliderValueAnimation", null, composerImpl, 3072, 22);
                composerImpl = composerImpl;
                composerImpl.end(false);
            }
            mutableState.setValue(sliderState);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, -1104846250);
            if (objM == composer$Companion$Empty$1) {
                objM = InteractionSourceKt.MutableInteractionSource();
                composerImpl.updateRememberedValue(objM);
            }
            final MutableInteractionSource mutableInteractionSource2 = (MutableInteractionSource) objM;
            composerImpl.end(false);
            float fFloatValue = ((Number) stateAnimateFloatAsState.getValue()).floatValue();
            final ClosedFloatingPointRange valueRange = sliderState.getValueRange();
            final SliderHapticFeedbackFilter hapticFilter = sliderState.getHapticFilter();
            composerImpl.startReplaceGroup(-1009755159);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.setUpHapticsViewModel (VolumeSlider.kt:364)");
            }
            if (factory == null) {
                state = stateAnimateFloatAsState;
                mutableInteractionSource = mutableInteractionSource2;
                z = false;
                sliderHapticsViewModel = null;
            } else {
                composerImpl.startReplaceGroup(262373222);
                composerImpl.startReplaceGroup(1285083812);
                boolean zChangedInstance = composerImpl.changedInstance(factory) | composerImpl.changed(valueRange) | composerImpl.changedInstance(hapticFilter);
                Object objRememberedValue3 = composerImpl.rememberedValue();
                if (zChangedInstance || objRememberedValue3 == composer$Companion$Empty$1) {
                    objRememberedValue3 = new Function0() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$$ExternalSyntheticLambda6
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Orientation orientation = Orientation.Horizontal;
                            VolumeHapticsConfigsProvider.INSTANCE.getClass();
                            ClosedFloatingPointRange closedFloatingPointRange = valueRange;
                            return factory.create(mutableInteractionSource2, closedFloatingPointRange, orientation, VolumeHapticsConfigsProvider.sliderHapticFeedbackConfig(closedFloatingPointRange, hapticFilter), VolumeHapticsConfigsProvider.seekableSliderTrackerConfig);
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue3);
                }
                composerImpl.end(false);
                state = stateAnimateFloatAsState;
                SliderHapticsViewModel sliderHapticsViewModel2 = (SliderHapticsViewModel) SysUiViewModelKt.rememberViewModel("SliderHapticsViewModel", null, (Function0) objRememberedValue3, composerImpl, 6, 2);
                composerImpl.startReplaceGroup(-132967130);
                Object objRememberedValue4 = composerImpl.rememberedValue();
                if (objRememberedValue4 == composer$Companion$Empty$1) {
                    objRememberedValue4 = PrimitiveSnapshotStateKt.mutableFloatStateOf((float) Math.rint(fFloatValue));
                    composerImpl.updateRememberedValue(objRememberedValue4);
                }
                MutableFloatState mutableFloatState = (MutableFloatState) objRememberedValue4;
                composerImpl.end(false);
                Float fValueOf = Float.valueOf(fFloatValue);
                composerImpl.startReplaceGroup(-132964034);
                boolean zChanged = composerImpl.changed(fFloatValue) | composerImpl.changedInstance(sliderHapticsViewModel2);
                Object objRememberedValue5 = composerImpl.rememberedValue();
                if (zChanged || objRememberedValue5 == composer$Companion$Empty$1) {
                    mutableInteractionSource = mutableInteractionSource2;
                    objRememberedValue5 = new VolumeSliderKt$setUpHapticsViewModel$1$2$1$1(fFloatValue, mutableFloatState, sliderHapticsViewModel2, null);
                    composerImpl.updateRememberedValue(objRememberedValue5);
                } else {
                    mutableInteractionSource = mutableInteractionSource2;
                }
                z = false;
                composerImpl.end(false);
                EffectsKt.LaunchedEffect(composerImpl, fValueOf, (Function2) objRememberedValue5);
                composerImpl.end(false);
                sliderHapticsViewModel = sliderHapticsViewModel2;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(z);
            Modifier modifierSysuiResTag = SysuiTestTagKt.sysuiResTag(modifier, sliderState.getLabel());
            composerImpl.startReplaceGroup(-1104832359);
            int i4 = i2 & 112;
            boolean zChanged2 = (i3 == 4 || ((i2 & 8) != 0 && composerImpl.changedInstance(sliderState))) | ((i2 & 896) == 256) | composerImpl.changed(state) | (i4 == 32);
            Object objRememberedValue6 = composerImpl.rememberedValue();
            if (zChanged2 || objRememberedValue6 == composer$Companion$Empty$1) {
                objRememberedValue6 = new Function1() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        String label;
                        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj;
                        final SliderState sliderState2 = sliderState;
                        if (sliderState2.isEnabled()) {
                            SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, sliderState2.getLabel());
                            String a11yClickDescription = sliderState2.getA11yClickDescription();
                            if (a11yClickDescription != null) {
                                final Function0 function03 = function0;
                                SemanticsPropertiesKt.setCustomActions(semanticsPropertyReceiver, Collections.singletonList(new CustomAccessibilityAction(a11yClickDescription, new Function0() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$$ExternalSyntheticLambda7
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        function03.invoke();
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
                        final State state2 = state;
                        final Function1 function12 = function1;
                        SemanticsPropertiesKt.setProgress$default(semanticsPropertyReceiver, new Function1() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$$ExternalSyntheticLambda8
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                float fFloatValue2 = ((Float) obj2).floatValue();
                                State state3 = state2;
                                int i5 = fFloatValue2 > ((Number) state3.getValue()).floatValue() ? 1 : fFloatValue2 < ((Number) state3.getValue()).floatValue() ? -1 : 0;
                                float fFloatValue3 = ((Number) state3.getValue()).floatValue();
                                SliderState sliderState3 = sliderState2;
                                function12.mo781invoke(Float.valueOf(RangesKt___RangesKt.coerceIn((sliderState3.getStep() * i5) + fFloatValue3, ((ClosedFloatRange) sliderState3.getValueRange())._start, ((ClosedFloatRange) sliderState3.getValueRange())._endInclusive)));
                                return Boolean.TRUE;
                            }
                        });
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue6);
            }
            composerImpl.end(false);
            Modifier modifierClearAndSetSemantics = SemanticsModifierKt.clearAndSetSemantics(modifierSysuiResTag, (Function1) objRememberedValue6);
            float fFloatValue2 = ((Number) state.getValue()).floatValue();
            ClosedFloatingPointRange valueRange2 = sliderState.getValueRange();
            boolean zIsEnabled = sliderState.isEnabled();
            composerImpl.startReplaceGroup(-1104783481);
            boolean zChangedInstance2 = (i4 == 32) | composerImpl.changedInstance(sliderHapticsViewModel);
            Object objRememberedValue7 = composerImpl.rememberedValue();
            if (zChangedInstance2 || objRememberedValue7 == composer$Companion$Empty$1) {
                objRememberedValue7 = new Function1() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$$ExternalSyntheticLambda3
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        Float f = (Float) obj;
                        float fFloatValue3 = f.floatValue();
                        SliderHapticsViewModel sliderHapticsViewModel3 = sliderHapticsViewModel;
                        if (sliderHapticsViewModel3 != null) {
                            sliderHapticsViewModel3.addVelocityDataPoint(fFloatValue3);
                        }
                        function1.mo781invoke(f);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue7);
            }
            Function1 function12 = (Function1) objRememberedValue7;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-1104778567);
            boolean zChangedInstance3 = composerImpl.changedInstance(sliderHapticsViewModel) | ((3670016 & i2) == 1048576);
            Object objRememberedValue8 = composerImpl.rememberedValue();
            if (zChangedInstance3 || objRememberedValue8 == composer$Companion$Empty$1) {
                objRememberedValue8 = new Function0() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$$ExternalSyntheticLambda4
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        SliderHapticsViewModel sliderHapticsViewModel3 = sliderHapticsViewModel;
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
                composerImpl.updateRememberedValue(objRememberedValue8);
            }
            composerImpl.end(false);
            PlatformSliderKt.m907PlatformSliderWu8B24Y(fFloatValue2, function12, modifierClearAndSetSemantics, (Function0) objRememberedValue8, valueRange2, zIsEnabled, mutableInteractionSource, platformSliderColors, 0.0f, ComposableLambdaKt.rememberComposableLambda(59388120, new Function3() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt.LegacyVolumeSlider.4
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    ((Boolean) obj).getClass();
                    Composer composer2 = (Composer) obj2;
                    if ((((Number) obj3).intValue() & 17) == 16) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.LegacyVolumeSlider.<anonymous> (VolumeSlider.kt:292)");
                            }
                            SliderState sliderState2 = sliderState;
                            Icon.Loaded icon = sliderState2.getIcon();
                            if (icon != null) {
                                VolumeSliderKt.LegacySliderIcon(icon, function0, sliderState2.isMutable(), null, composer2, 0);
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), ComposableLambdaKt.rememberComposableLambda(-561916982, new Function3() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt.LegacyVolumeSlider.5
                /* JADX WARN: Removed duplicated region for block: B:15:0x0035  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    boolean zBooleanValue = ((Boolean) obj).booleanValue();
                    Composer composer2 = (Composer) obj2;
                    int iIntValue = ((Number) obj3).intValue();
                    if ((iIntValue & 6) == 0) {
                        iIntValue |= ((ComposerImpl) composer2).changed(zBooleanValue) ? 4 : 2;
                    }
                    if ((iIntValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.LegacyVolumeSlider.<anonymous> (VolumeSlider.kt:302)");
                            }
                            EnterTransition enterTransitionFadeIn$default = EnterExitTransitionKt.fadeIn$default(AnimationSpecKt.tween$default(150, 0, null, 6), 2);
                            ExitTransition exitTransitionFadeOut$default = EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(150, 0, null, 6), 2);
                            final SliderState sliderState2 = sliderState;
                            AnimatedVisibilityKt.AnimatedVisibility(!zBooleanValue, null, enterTransitionFadeIn$default, exitTransitionFadeOut$default, null, ComposableLambdaKt.rememberComposableLambda(-1000495966, new Function3() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt.LegacyVolumeSlider.5.1
                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj4, Object obj5, Object obj6) {
                                    Composer composer3 = (Composer) obj5;
                                    ((Number) obj6).intValue();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.LegacyVolumeSlider.<anonymous>.<anonymous> (VolumeSlider.kt:307)");
                                    }
                                    Modifier.Companion companion = Modifier.Companion;
                                    SliderState sliderState3 = sliderState2;
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
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, ((i2 << 12) & 29360128) | 806879232);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    Modifier modifier2 = modifier;
                    Function0 function03 = function02;
                    VolumeSliderKt.LegacyVolumeSlider(sliderState, function1, function0, platformSliderColors, factory, modifier2, function03, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:83:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:96:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void VolumeSlider(final SliderState sliderState, final Function1 function1, final Function0 function0, final PlatformSliderColors platformSliderColors, final Modifier modifier, final SliderHapticsViewModel.Factory factory, final Function0 function02, final Function3 function3, Composer composer, final int i, final int i2) {
        int i3;
        Function0 function03;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1994029453);
        if ((i & 6) == 0) {
            i3 = ((i & 8) == 0 ? composerImpl.changed(sliderState) : composerImpl.changedInstance(sliderState) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerImpl.changedInstance(function1) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= composerImpl.changedInstance(function0) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i3 |= (i & 4096) == 0 ? composerImpl.changed(platformSliderColors) : composerImpl.changedInstance(platformSliderColors) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i3 |= composerImpl.changed(modifier) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i3 |= (262144 & i) == 0 ? composerImpl.changed(factory) : composerImpl.changedInstance(factory) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            function03 = function02;
            i3 |= composerImpl.changedInstance(function03) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        } else {
            function03 = function02;
        }
        int i4 = i2 & 128;
        if (i4 == 0) {
            if ((12582912 & i) == 0) {
                i3 |= composerImpl.changedInstance(function3) ? 8388608 : 4194304;
            }
            if ((4793491 & i3) != 4793490 && composerImpl.getSkipping()) {
                composerImpl.skipToGroupEnd();
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup2 != null) {
                    final int i5 = 1;
                    final Function0 function04 = function03;
                    recomposeScopeImplEndRestartGroup2.block = new Function2() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            switch (i5) {
                                case 0:
                                    ((Integer) obj2).getClass();
                                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                    Function3 function32 = function3;
                                    VolumeSliderKt.VolumeSlider(sliderState, function1, function0, platformSliderColors, modifier, factory, function04, function32, (Composer) obj, iUpdateChangedFlags, i2);
                                    break;
                                default:
                                    ((Integer) obj2).getClass();
                                    int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                    Function3 function33 = function3;
                                    VolumeSliderKt.VolumeSlider(sliderState, function1, function0, platformSliderColors, modifier, factory, function04, function33, (Composer) obj, iUpdateChangedFlags2, i2);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            Function3 function32 = i4 == 0 ? null : function3;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSlider (VolumeSlider.kt:99)");
            }
            composerImpl.startReplaceGroup(-810326638);
            LegacyVolumeSlider(sliderState, function1, function0, platformSliderColors, factory, modifier, function02, composerImpl, (i3 & 8190) | ((i3 >> 3) & 57344) | (458752 & (i3 << 3)) | (i3 & 3670016));
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                final int i6 = 0;
                final Function3 function33 = function32;
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        switch (i6) {
                            case 0:
                                ((Integer) obj2).getClass();
                                int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                Function3 function322 = function33;
                                VolumeSliderKt.VolumeSlider(sliderState, function1, function0, platformSliderColors, modifier, factory, function02, function322, (Composer) obj, iUpdateChangedFlags, i2);
                                break;
                            default:
                                ((Integer) obj2).getClass();
                                int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                Function3 function332 = function33;
                                VolumeSliderKt.VolumeSlider(sliderState, function1, function0, platformSliderColors, modifier, factory, function02, function332, (Composer) obj, iUpdateChangedFlags2, i2);
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i3 |= 12582912;
        if ((4793491 & i3) != 4793490) {
        }
        if (i4 == 0) {
        }
        if (ComposerKt.isTraceInProgress()) {
        }
        composerImpl.startReplaceGroup(-810326638);
        LegacyVolumeSlider(sliderState, function1, function0, platformSliderColors, factory, modifier, function02, composerImpl, (i3 & 8190) | ((i3 >> 3) & 57344) | (458752 & (i3 << 3)) | (i3 & 3670016));
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
