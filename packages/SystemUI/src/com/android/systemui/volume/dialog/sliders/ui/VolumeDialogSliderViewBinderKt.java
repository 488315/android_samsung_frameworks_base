package com.android.systemui.volume.dialog.sliders.ui;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SliderColors;
import androidx.compose.material3.SliderDefaults;
import androidx.compose.material3.SliderState;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerEventType;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.ui.compose.IconKt;
import com.android.systemui.haptics.slider.SliderHapticFeedbackFilter;
import com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.volume.dialog.shared.VolumeDialogLogger;
import com.android.systemui.volume.dialog.shared.VolumeDialogLogger$$ExternalSyntheticLambda0;
import com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInputEventsInteractor;
import com.android.systemui.volume.dialog.sliders.shared.model.SliderInputEvent;
import com.android.systemui.volume.dialog.sliders.ui.compose.SliderIconsState;
import com.android.systemui.volume.dialog.sliders.ui.compose.TrackMeasurePolicy;
import com.android.systemui.volume.dialog.sliders.ui.compose.VolumeDialogSliderTrackKt;
import com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel;
import com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSliderStateModel;
import com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSliderViewModel;
import com.android.systemui.volume.ui.compose.slider.AccessibilityParams;
import com.android.systemui.volume.ui.compose.slider.Haptics;
import com.android.systemui.volume.ui.compose.slider.SliderIconKt;
import com.android.systemui.volume.ui.compose.slider.SliderKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.ClosedFloatRange;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.JobKt;

/* loaded from: classes3.dex */
public abstract class VolumeDialogSliderViewBinderKt {
    public static final void VolumeDialogSlider(final VolumeDialogSliderViewModel volumeDialogSliderViewModel, final VolumeDialogOverscrollViewModel volumeDialogOverscrollViewModel, final SliderHapticsViewModel.Factory factory, Modifier modifier, Composer composer, final int i) {
        final Modifier modifier2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(510520555);
        if (((i | (composerImpl.changedInstance(volumeDialogSliderViewModel) ? 4 : 2) | (composerImpl.changedInstance(volumeDialogOverscrollViewModel) ? 32 : 16) | (composerImpl.changed(factory) ? 256 : 128) | 3072) & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
        } else {
            final Modifier.Companion companion = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSlider (VolumeDialogSliderViewBinder.kt:89)");
            }
            SliderDefaults sliderDefaults = SliderDefaults.INSTANCE;
            MaterialTheme materialTheme = MaterialTheme.INSTANCE;
            materialTheme.getClass();
            long j = MaterialTheme.getColorScheme(composerImpl).surfaceContainerHighest;
            long j2 = MaterialTheme.getColorScheme(composerImpl).surfaceContainerHighest;
            long j3 = MaterialTheme.getColorScheme(composerImpl).surfaceContainerHighest;
            long j4 = MaterialTheme.getColorScheme(composerImpl).surfaceContainerHighest;
            sliderDefaults.getClass();
            Color.Companion.getClass();
            long j5 = Color.Unspecified;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.SliderDefaults.colors (Slider.kt:1122)");
            }
            materialTheme.getClass();
            final SliderColors sliderColorsM291copyK518z4 = SliderDefaults.getDefaultSliderColors$material3_release(MaterialTheme.getColorScheme(composerImpl)).m291copyK518z4(j5, j5, j, j2, j5, j5, j5, j3, j4, j5);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            final VolumeDialogSliderStateModel volumeDialogSliderStateModel = (VolumeDialogSliderStateModel) FlowExtKt.collectAsStateWithLifecycle(volumeDialogSliderViewModel.state, null, composerImpl, 48).getValue();
            if (volumeDialogSliderStateModel == null) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    final int i2 = 0;
                    recomposeScopeImplEndRestartGroup.block = new Function2(volumeDialogSliderViewModel, volumeDialogOverscrollViewModel, factory, companion, i, i2) { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt$$ExternalSyntheticLambda0
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ VolumeDialogSliderViewModel f$0;
                        public final /* synthetic */ VolumeDialogOverscrollViewModel f$1;
                        public final /* synthetic */ SliderHapticsViewModel.Factory f$2;
                        public final /* synthetic */ Modifier f$3;

                        {
                            this.$r8$classId = i2;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            switch (this.$r8$classId) {
                                case 0:
                                    ((Integer) obj2).getClass();
                                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                                    SliderHapticsViewModel.Factory factory2 = this.f$2;
                                    Modifier modifier3 = this.f$3;
                                    VolumeDialogSliderViewBinderKt.VolumeDialogSlider(this.f$0, this.f$1, factory2, modifier3, (Composer) obj, iUpdateChangedFlags);
                                    break;
                                default:
                                    ((Integer) obj2).getClass();
                                    int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(1);
                                    SliderHapticsViewModel.Factory factory3 = this.f$2;
                                    Modifier modifier4 = this.f$3;
                                    VolumeDialogSliderViewBinderKt.VolumeDialogSlider(this.f$0, this.f$1, factory3, modifier4, (Composer) obj, iUpdateChangedFlags2);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            composerImpl.startReplaceGroup(1007827447);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            MutableInteractionSource mutableInteractionSource = (MutableInteractionSource) objRememberedValue;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(1007830270);
            boolean zChangedInstance = composerImpl.changedInstance(volumeDialogSliderViewModel);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = new VolumeDialogSliderViewBinderKt$VolumeDialogSlider$1$1(mutableInteractionSource, volumeDialogSliderViewModel, null);
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, mutableInteractionSource, (Function2) objRememberedValue2);
            boolean z = !volumeDialogSliderStateModel.isDisabled;
            Haptics enabled = factory != null ? new Haptics.Enabled(factory, new SliderHapticFeedbackFilter(false, false, 3, null), Orientation.Vertical) : Haptics.Disabled.INSTANCE;
            AccessibilityParams accessibilityParams = new AccessibilityParams(volumeDialogSliderStateModel.label, null, 2, null);
            Unit unit = Unit.INSTANCE;
            composerImpl.startReplaceGroup(1007920052);
            boolean zChangedInstance2 = composerImpl.changedInstance(volumeDialogSliderViewModel);
            Object objRememberedValue3 = composerImpl.rememberedValue();
            if (zChangedInstance2 || objRememberedValue3 == composer$Companion$Empty$1) {
                objRememberedValue3 = new PointerInputEventHandler() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt$VolumeDialogSlider$3$1

                    /* renamed from: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt$VolumeDialogSlider$3$1$1, reason: invalid class name */
                    final class AnonymousClass1 extends SuspendLambda implements Function2 {
                        final /* synthetic */ PointerInputScope $this_pointerInput;
                        final /* synthetic */ VolumeDialogSliderViewModel $viewModel;
                        int label;

                        /* renamed from: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt$VolumeDialogSlider$3$1$1$1, reason: invalid class name and collision with other inner class name */
                        final class C06491 extends RestrictedSuspendLambda implements Function2 {
                            final /* synthetic */ CoroutineContext $currentContext;
                            final /* synthetic */ VolumeDialogSliderViewModel $viewModel;
                            private /* synthetic */ Object L$0;
                            Object L$1;
                            int label;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            public C06491(CoroutineContext coroutineContext, VolumeDialogSliderViewModel volumeDialogSliderViewModel, Continuation continuation) {
                                super(2, continuation);
                                this.$currentContext = coroutineContext;
                                this.$viewModel = volumeDialogSliderViewModel;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Continuation create(Object obj, Continuation continuation) {
                                C06491 c06491 = new C06491(this.$currentContext, this.$viewModel, continuation);
                                c06491.L$0 = obj;
                                return c06491;
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                return ((C06491) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                            }

                            /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
                            /* JADX WARN: Removed duplicated region for block: B:16:0x0060  */
                            /* JADX WARN: Removed duplicated region for block: B:17:0x007a  */
                            /* JADX WARN: Removed duplicated region for block: B:26:0x00d6  */
                            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:12:0x0039 -> B:14:0x003c). Please report as a decompilation issue!!! */
                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invokeSuspend(Object obj) {
                                AwaitPointerEventScope awaitPointerEventScope;
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                int i = this.label;
                                if (i == 0) {
                                    ResultKt.throwOnFailure(obj);
                                    awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                                    if (JobKt.isActive(this.$currentContext)) {
                                    }
                                } else {
                                    if (i != 1) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    VolumeDialogSliderViewModel volumeDialogSliderViewModel = (VolumeDialogSliderViewModel) this.L$1;
                                    awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                                    ResultKt.throwOnFailure(obj);
                                    PointerEvent pointerEvent = (PointerEvent) obj;
                                    volumeDialogSliderViewModel.getClass();
                                    long j = ((PointerInputChange) CollectionsKt___CollectionsKt.first(pointerEvent.changes)).position;
                                    int i2 = pointerEvent.type;
                                    PointerEventType.Companion.getClass();
                                    int i3 = PointerEventType.Press;
                                    VolumeDialogSliderInputEventsInteractor volumeDialogSliderInputEventsInteractor = volumeDialogSliderViewModel.inputEventsInteractor;
                                    if (i2 == i3) {
                                        volumeDialogSliderInputEventsInteractor.repository.mutableSliderTouchEvents.updateState(null, new SliderInputEvent.Touch.Start(Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L))));
                                    } else if (i2 == PointerEventType.Move) {
                                        volumeDialogSliderInputEventsInteractor.repository.mutableSliderTouchEvents.updateState(null, new SliderInputEvent.Touch.Move(Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L))));
                                    } else if (i2 == PointerEventType.Scroll) {
                                        volumeDialogSliderInputEventsInteractor.repository.mutableSliderTouchEvents.updateState(null, new SliderInputEvent.Touch.Move(Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L))));
                                    } else if (i2 == PointerEventType.Release) {
                                        volumeDialogSliderInputEventsInteractor.repository.mutableSliderTouchEvents.updateState(null, new SliderInputEvent.Touch.End(Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L))));
                                    }
                                    if (JobKt.isActive(this.$currentContext)) {
                                        volumeDialogSliderViewModel = this.$viewModel;
                                        this.L$0 = awaitPointerEventScope;
                                        this.L$1 = volumeDialogSliderViewModel;
                                        this.label = 1;
                                        obj = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope).awaitPointerEvent(PointerEventPass.Main, this);
                                        if (obj == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                        PointerEvent pointerEvent2 = (PointerEvent) obj;
                                        volumeDialogSliderViewModel.getClass();
                                        long j2 = ((PointerInputChange) CollectionsKt___CollectionsKt.first(pointerEvent2.changes)).position;
                                        int i22 = pointerEvent2.type;
                                        PointerEventType.Companion.getClass();
                                        int i32 = PointerEventType.Press;
                                        VolumeDialogSliderInputEventsInteractor volumeDialogSliderInputEventsInteractor2 = volumeDialogSliderViewModel.inputEventsInteractor;
                                        if (i22 == i32) {
                                        }
                                        if (JobKt.isActive(this.$currentContext)) {
                                            return Unit.INSTANCE;
                                        }
                                    }
                                }
                            }
                        }

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass1(PointerInputScope pointerInputScope, VolumeDialogSliderViewModel volumeDialogSliderViewModel, Continuation continuation) {
                            super(2, continuation);
                            this.$this_pointerInput = pointerInputScope;
                            this.$viewModel = volumeDialogSliderViewModel;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new AnonymousClass1(this.$this_pointerInput, this.$viewModel, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i = this.label;
                            if (i == 0) {
                                ResultKt.throwOnFailure(obj);
                                CoroutineContext context = getContext();
                                PointerInputScope pointerInputScope = this.$this_pointerInput;
                                C06491 c06491 = new C06491(context, this.$viewModel, null);
                                this.label = 1;
                                if (pointerInputScope.awaitPointerEventScope(c06491, this) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj);
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                    public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass1(pointerInputScope, volumeDialogSliderViewModel, null), continuation);
                        return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue3);
            }
            composerImpl.end(false);
            Modifier modifierPointerInput = SuspendingPointerInputFilterKt.pointerInput(companion, unit, (PointerInputEventHandler) objRememberedValue3);
            composerImpl.startReplaceGroup(1007845032);
            boolean zChangedInstance3 = composerImpl.changedInstance(volumeDialogOverscrollViewModel) | composerImpl.changed(volumeDialogSliderStateModel) | composerImpl.changedInstance(volumeDialogSliderViewModel);
            Object objRememberedValue4 = composerImpl.rememberedValue();
            if (zChangedInstance3 || objRememberedValue4 == composer$Companion$Empty$1) {
                objRememberedValue4 = new Function1() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        float fFloatValue = ((Float) obj).floatValue();
                        ClosedFloatRange closedFloatRange = (ClosedFloatRange) volumeDialogSliderStateModel.valueRange;
                        volumeDialogOverscrollViewModel.sliderValue.updateState(null, new VolumeDialogOverscrollViewModel.Slider(fFloatValue, closedFloatRange._start, closedFloatRange._endInclusive));
                        VolumeDialogSliderViewModel volumeDialogSliderViewModel2 = volumeDialogSliderViewModel;
                        volumeDialogSliderViewModel2.visibilityInteractor.resetDismissTimeout();
                        volumeDialogSliderViewModel2.userVolumeUpdates.updateState(null, new VolumeDialogSliderViewModel.VolumeUpdate(MathKt__MathJVMKt.roundToInt(fFloatValue), volumeDialogSliderViewModel2.systemClock.uptimeMillis()));
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue4);
            }
            Function1 function1 = (Function1) objRememberedValue4;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(1007854840);
            boolean zChangedInstance4 = composerImpl.changedInstance(volumeDialogSliderViewModel);
            Object objRememberedValue5 = composerImpl.rememberedValue();
            if (zChangedInstance4 || objRememberedValue5 == composer$Companion$Empty$1) {
                objRememberedValue5 = new Function1() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        float fFloatValue = ((Float) obj).floatValue();
                        VolumeDialogSliderViewModel volumeDialogSliderViewModel2 = volumeDialogSliderViewModel;
                        volumeDialogSliderViewModel2.getClass();
                        int iRoundToInt = MathKt__MathJVMKt.roundToInt(fFloatValue);
                        int audioStream = volumeDialogSliderViewModel2.sliderType.getAudioStream();
                        VolumeDialogLogger volumeDialogLogger = volumeDialogSliderViewModel2.logger;
                        volumeDialogLogger.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        VolumeDialogLogger$$ExternalSyntheticLambda0 volumeDialogLogger$$ExternalSyntheticLambda0 = new VolumeDialogLogger$$ExternalSyntheticLambda0(8);
                        LogBuffer logBuffer = volumeDialogLogger.logBuffer;
                        LogMessage logMessageObtain = logBuffer.obtain("SysUI_VolumeDialog", logLevel, volumeDialogLogger$$ExternalSyntheticLambda0, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                        logMessageImpl.int1 = iRoundToInt;
                        logMessageImpl.int2 = audioStream;
                        logBuffer.commit(logMessageObtain);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue5);
            }
            composerImpl.end(false);
            SliderKt.Slider(volumeDialogSliderStateModel.value, volumeDialogSliderStateModel.valueRange, function1, (Function1) objRememberedValue5, z, accessibilityParams, modifierPointerInput, 1.0f, sliderColorsM291copyK518z4, mutableInteractionSource, enabled, true, true, ComposableLambdaKt.rememberComposableLambda(-559232083, new Function3() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt.VolumeDialogSlider.6
                /* JADX WARN: Removed duplicated region for block: B:18:0x0041  */
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
                                ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSlider.<anonymous> (VolumeDialogSliderViewBinder.kt:138)");
                            }
                            final VolumeDialogSliderStateModel volumeDialogSliderStateModel2 = volumeDialogSliderStateModel;
                            VolumeDialogSliderTrackKt.m3219SliderTrackq58E_xs(sliderState, !volumeDialogSliderStateModel2.isDisabled, null, sliderColorsM291copyK518z4, 0.0f, 0.0f, 0.0f, 0.0f, true, ComposableLambdaKt.rememberComposableLambda(1218107908, new Function4() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt.VolumeDialogSlider.6.1
                                /* JADX WARN: Removed duplicated region for block: B:18:0x003f  */
                                @Override // kotlin.jvm.functions.Function4
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object invoke(Object obj4, Object obj5, Object obj6, Object obj7) {
                                    SliderIconsState sliderIconsState = (SliderIconsState) obj5;
                                    Composer composer3 = (Composer) obj6;
                                    int iIntValue2 = ((Number) obj7).intValue();
                                    if ((iIntValue2 & 48) == 0) {
                                        iIntValue2 |= (iIntValue2 & 64) == 0 ? ((ComposerImpl) composer3).changed(sliderIconsState) : ((ComposerImpl) composer3).changedInstance(sliderIconsState) ? 32 : 16;
                                    }
                                    if ((iIntValue2 & 145) == 144) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSlider.<anonymous>.<anonymous> (VolumeDialogSliderViewBinder.kt:144)");
                                            }
                                            final VolumeDialogSliderStateModel volumeDialogSliderStateModel3 = volumeDialogSliderStateModel2;
                                            SliderIconKt.SliderIcon(ComposableLambdaKt.rememberComposableLambda(-987571870, new Function3() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt.VolumeDialogSlider.6.1.1
                                                /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
                                                @Override // kotlin.jvm.functions.Function3
                                                /*
                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                */
                                                public final Object invoke(Object obj8, Object obj9, Object obj10) {
                                                    Composer composer4 = (Composer) obj9;
                                                    if ((((Number) obj10).intValue() & 17) == 16) {
                                                        ComposerImpl composerImpl4 = (ComposerImpl) composer4;
                                                        if (composerImpl4.getSkipping()) {
                                                            composerImpl4.skipToGroupEnd();
                                                        } else {
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSlider.<anonymous>.<anonymous>.<anonymous> (VolumeDialogSliderViewBinder.kt:146)");
                                                            }
                                                            Icon.Loaded loaded = volumeDialogSliderStateModel3.icon;
                                                            Dp.Companion companion2 = Dp.Companion;
                                                            IconKt.m1074IconFNF3uiM(loaded, SizeKt.m140size3ABfNKs(Modifier.Companion, 20), 0L, composer4, 48, 4);
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventEnd();
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }, composer3), ((TrackMeasurePolicy) sliderIconsState).isActiveTrackStartIconVisible(), null, composer3, 6);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composer2), ComposableLambdaKt.rememberComposableLambda(-212353485, new Function4() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt.VolumeDialogSlider.6.2
                                /* JADX WARN: Removed duplicated region for block: B:18:0x003f  */
                                @Override // kotlin.jvm.functions.Function4
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object invoke(Object obj4, Object obj5, Object obj6, Object obj7) {
                                    SliderIconsState sliderIconsState = (SliderIconsState) obj5;
                                    Composer composer3 = (Composer) obj6;
                                    int iIntValue2 = ((Number) obj7).intValue();
                                    if ((iIntValue2 & 48) == 0) {
                                        iIntValue2 |= (iIntValue2 & 64) == 0 ? ((ComposerImpl) composer3).changed(sliderIconsState) : ((ComposerImpl) composer3).changedInstance(sliderIconsState) ? 32 : 16;
                                    }
                                    if ((iIntValue2 & 145) == 144) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSlider.<anonymous>.<anonymous> (VolumeDialogSliderViewBinder.kt:152)");
                                            }
                                            final VolumeDialogSliderStateModel volumeDialogSliderStateModel3 = volumeDialogSliderStateModel2;
                                            SliderIconKt.SliderIcon(ComposableLambdaKt.rememberComposableLambda(131050133, new Function3() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt.VolumeDialogSlider.6.2.1
                                                /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
                                                @Override // kotlin.jvm.functions.Function3
                                                /*
                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                */
                                                public final Object invoke(Object obj8, Object obj9, Object obj10) {
                                                    Composer composer4 = (Composer) obj9;
                                                    if ((((Number) obj10).intValue() & 17) == 16) {
                                                        ComposerImpl composerImpl4 = (ComposerImpl) composer4;
                                                        if (composerImpl4.getSkipping()) {
                                                            composerImpl4.skipToGroupEnd();
                                                        } else {
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSlider.<anonymous>.<anonymous>.<anonymous> (VolumeDialogSliderViewBinder.kt:154)");
                                                            }
                                                            Icon.Loaded loaded = volumeDialogSliderStateModel3.icon;
                                                            Dp.Companion companion2 = Dp.Companion;
                                                            IconKt.m1074IconFNF3uiM(loaded, SizeKt.m140size3ABfNKs(Modifier.Companion, 20), 0L, composer4, 48, 4);
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventEnd();
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }, composer3), !((TrackMeasurePolicy) sliderIconsState).isActiveTrackStartIconVisible(), null, composer3, 6);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composer2), composer2, 905969672 | (iIntValue & 14), 48, 5364);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), ComposableLambdaKt.rememberComposableLambda(313421367, new Function4() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt.VolumeDialogSlider.7
                /* JADX WARN: Removed duplicated region for block: B:26:0x0056  */
                @Override // kotlin.jvm.functions.Function4
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                    int i3;
                    SliderState sliderState = (SliderState) obj;
                    MutableInteractionSource mutableInteractionSource2 = (MutableInteractionSource) obj2;
                    Composer composer2 = (Composer) obj3;
                    int iIntValue = ((Number) obj4).intValue();
                    if ((iIntValue & 6) == 0) {
                        i3 = ((iIntValue & 8) == 0 ? ((ComposerImpl) composer2).changed(sliderState) : ((ComposerImpl) composer2).changedInstance(sliderState) ? 4 : 2) | iIntValue;
                    } else {
                        i3 = iIntValue;
                    }
                    if ((iIntValue & 48) == 0) {
                        i3 |= ((ComposerImpl) composer2).changed(mutableInteractionSource2) ? 32 : 16;
                    }
                    if ((i3 & 147) == 146) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSlider.<anonymous> (VolumeDialogSliderViewBinder.kt:162)");
                            }
                            SliderDefaults sliderDefaults2 = SliderDefaults.INSTANCE;
                            boolean z2 = !volumeDialogSliderStateModel.isDisabled;
                            Dp.Companion companion2 = Dp.Companion;
                            sliderDefaults2.m297ThumbHwbPF3A(mutableInteractionSource2, sliderState, null, sliderColorsM291copyK518z4, z2, DpKt.m840DpSizeYgX7TsA(52, 4), composer2, ((i3 >> 3) & 14) | 1769536 | ((i3 << 3) & 112), 4);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 817889280);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            modifier2 = companion;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 != null) {
            final int i3 = 1;
            recomposeScopeImplEndRestartGroup2.block = new Function2(volumeDialogSliderViewModel, volumeDialogOverscrollViewModel, factory, modifier2, i, i3) { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinderKt$$ExternalSyntheticLambda0
                public final /* synthetic */ int $r8$classId;
                public final /* synthetic */ VolumeDialogSliderViewModel f$0;
                public final /* synthetic */ VolumeDialogOverscrollViewModel f$1;
                public final /* synthetic */ SliderHapticsViewModel.Factory f$2;
                public final /* synthetic */ Modifier f$3;

                {
                    this.$r8$classId = i3;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    switch (this.$r8$classId) {
                        case 0:
                            ((Integer) obj2).getClass();
                            int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                            SliderHapticsViewModel.Factory factory2 = this.f$2;
                            Modifier modifier3 = this.f$3;
                            VolumeDialogSliderViewBinderKt.VolumeDialogSlider(this.f$0, this.f$1, factory2, modifier3, (Composer) obj, iUpdateChangedFlags);
                            break;
                        default:
                            ((Integer) obj2).getClass();
                            int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(1);
                            SliderHapticsViewModel.Factory factory3 = this.f$2;
                            Modifier modifier4 = this.f$3;
                            VolumeDialogSliderViewBinderKt.VolumeDialogSlider(this.f$0, this.f$1, factory3, modifier4, (Composer) obj, iUpdateChangedFlags2);
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
