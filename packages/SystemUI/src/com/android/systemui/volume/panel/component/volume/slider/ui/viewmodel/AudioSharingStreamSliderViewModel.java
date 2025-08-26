package com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.haptics.slider.SliderHapticFeedbackFilter;
import com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.volume.domain.interactor.AudioSharingInteractor;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState;
import com.android.systemui.volume.panel.shared.VolumePanelLogger;
import com.android.systemui.volume.panel.shared.VolumePanelLogger$$ExternalSyntheticLambda0;
import com.android.systemui.volume.panel.ui.VolumePanelUiEvent;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class AudioSharingStreamSliderViewModel implements SliderViewModel {
    public final Icon.Loaded audioSharingIcon;
    public final AudioSharingInteractor audioSharingInteractor;
    public final SliderHapticsViewModel.Factory hapticsViewModelFactory;
    public final ReadonlyStateFlow slider;
    public final CoroutineContext uiBackgroundContext;
    public final UiEventLogger uiEventLogger;
    public final StateFlowImpl volumeChanges;
    public final VolumePanelLogger volumePanelLogger;

    /* renamed from: com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioSharingStreamSliderViewModel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ int I$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = AudioSharingStreamSliderViewModel.this.new AnonymousClass1(continuation);
            anonymousClass1.I$0 = ((Number) obj).intValue();
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            int i = this.I$0;
            VolumePanelLogger volumePanelLogger = AudioSharingStreamSliderViewModel.this.volumePanelLogger;
            volumePanelLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            VolumePanelLogger$$ExternalSyntheticLambda0 volumePanelLogger$$ExternalSyntheticLambda0 = new VolumePanelLogger$$ExternalSyntheticLambda0(5);
            LogBuffer logBuffer = volumePanelLogger.logBuffer;
            LogMessage logMessageObtain = logBuffer.obtain("SysUI_VolumePanel", logLevel, volumePanelLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).int1 = i;
            logBuffer.commit(logMessageObtain);
            AudioSharingStreamSliderViewModel.this.audioSharingInteractor.setStreamVolume(i);
            return Unit.INSTANCE;
        }
    }

    public interface Factory {
        AudioSharingStreamSliderViewModel create(CoroutineScope coroutineScope);
    }

    public final class State implements SliderState {
        public final Icon.Loaded icon;
        public final String label;
        public final float value;
        public final ClosedFloatingPointRange valueRange;

        public State(float f, ClosedFloatingPointRange closedFloatingPointRange, Icon.Loaded loaded, String str) {
            this.value = f;
            this.valueRange = closedFloatingPointRange;
            this.icon = loaded;
            this.label = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof State)) {
                return false;
            }
            State state = (State) obj;
            return Float.compare(this.value, state.value) == 0 && Intrinsics.areEqual(this.valueRange, state.valueRange) && Intrinsics.areEqual(this.icon, state.icon) && Intrinsics.areEqual(this.label, state.label);
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final String getA11yClickDescription() {
            return null;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final String getA11yStateDescription() {
            return null;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final String getDisabledMessage() {
            return null;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final SliderHapticFeedbackFilter getHapticFilter() {
            return new SliderHapticFeedbackFilter(false, false, 3, null);
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final Icon.Loaded getIcon() {
            return this.icon;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final String getLabel() {
            return this.label;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final float getStep() {
            return 1.0f;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final float getValue() {
            return this.value;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final ClosedFloatingPointRange getValueRange() {
            return this.valueRange;
        }

        public final int hashCode() {
            int iHashCode = (this.valueRange.hashCode() + (Float.hashCode(this.value) * 31)) * 31;
            Icon.Loaded loaded = this.icon;
            return this.label.hashCode() + ((iHashCode + (loaded == null ? 0 : loaded.hashCode())) * 31);
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final boolean isEnabled() {
            return true;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final boolean isMutable() {
            return false;
        }

        public final String toString() {
            return "State(value=" + this.value + ", valueRange=" + this.valueRange + ", icon=" + this.icon + ", label=" + this.label + ")";
        }
    }

    public AudioSharingStreamSliderViewModel(Context context, CoroutineScope coroutineScope, CoroutineContext coroutineContext, AudioSharingInteractor audioSharingInteractor, UiEventLogger uiEventLogger, SliderHapticsViewModel.Factory factory, VolumePanelLogger volumePanelLogger) {
        this.uiBackgroundContext = coroutineContext;
        this.audioSharingInteractor = audioSharingInteractor;
        this.uiEventLogger = uiEventLogger;
        this.hapticsViewModelFactory = factory;
        this.volumePanelLogger = volumePanelLogger;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.volumeChanges = stateFlowImplMutableStateFlow;
        Drawable drawable = context.getDrawable(R.drawable.ic_volume_media_bt);
        drawable.getClass();
        this.audioSharingIcon = new Icon.Loaded(drawable, null, Integer.valueOf(R.drawable.ic_volume_media_bt));
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(audioSharingInteractor.getVolume()), new AudioSharingStreamSliderViewModel$slider$1(this, null)), audioSharingInteractor.getSecondaryDevice(), new AudioSharingStreamSliderViewModel$slider$2(this, null));
        SharingStarted.Companion.getClass();
        this.slider = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.Eagerly, SliderState.Empty.INSTANCE);
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(stateFlowImplMutableStateFlow), new AnonymousClass1(null)), coroutineScope);
    }

    @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel
    public final ReadonlyStateFlow getSlider() {
        return this.slider;
    }

    @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel
    public final SliderHapticsViewModel.Factory getSliderHapticsViewModelFactory() {
        if (Intrinsics.areEqual(this.slider.$$delegate_0.getValue(), SliderState.Empty.INSTANCE)) {
            return null;
        }
        return this.hapticsViewModelFactory;
    }

    @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel
    public final void onValueChangeFinished() {
        this.uiEventLogger.log(VolumePanelUiEvent.VOLUME_PANEL_AUDIO_SHARING_SLIDER_TOUCHED);
    }

    @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel
    public final void onValueChanged(SliderState sliderState, float f) {
        if (sliderState instanceof State) {
            this.volumeChanges.updateState(null, Integer.valueOf(MathKt__MathJVMKt.roundToInt(f)));
        }
    }

    @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel
    public final void toggleMuted(SliderState sliderState) {
    }
}
