package com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel;

import android.content.Context;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.volume.data.repository.AudioRepositoryImpl;
import com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor;
import com.android.settingslib.volume.shared.model.AudioStream;
import com.android.settingslib.volume.shared.model.AudioStreamModel;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState;
import com.android.systemui.volume.panel.ui.VolumePanelUiEvent;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AudioStreamSliderViewModel implements SliderViewModel {
    public final int audioStream;
    public final AudioVolumeInteractor audioVolumeInteractor;
    public final Context context;
    public final CoroutineScope coroutineScope;
    public final Map disabledTextByStream;
    public final Map iconsByStream;
    public final Map labelsByStream;
    public final ReadonlyStateFlow slider;
    public final Set streamsAffectedByRing;
    public final Map uiEventByStream;
    public final UiEventLogger uiEventLogger;
    public final StateFlowImpl volumeChanges;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioStreamSliderViewModel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ int I$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = AudioStreamSliderViewModel.this.new AnonymousClass1(continuation);
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
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                int i2 = this.I$0;
                AudioStreamSliderViewModel audioStreamSliderViewModel = AudioStreamSliderViewModel.this;
                AudioVolumeInteractor audioVolumeInteractor = audioStreamSliderViewModel.audioVolumeInteractor;
                int i3 = audioStreamSliderViewModel.audioStream;
                this.label = 1;
                if (audioVolumeInteractor.m868setVolumeZdW0WiI(i3, i2, this) == coroutineSingletons) {
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        AudioStreamSliderViewModel create(FactoryAudioStreamWrapper factoryAudioStreamWrapper, CoroutineScope coroutineScope);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class FactoryAudioStreamWrapper {
        public final int audioStream;

        public /* synthetic */ FactoryAudioStreamWrapper(int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(i);
        }

        private FactoryAudioStreamWrapper(int i) {
            this.audioStream = i;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class State implements SliderState {
        public final String a11yClickDescription;
        public final String a11yStateDescription;
        public final int a11yStep;
        public final AudioStreamModel audioStreamModel;
        public final String disabledMessage;
        public final Icon icon;
        public final boolean isEnabled;
        public final boolean isMutable;
        public final String label;
        public final float value;
        public final ClosedFloatingPointRange valueRange;

        public State(float f, ClosedFloatingPointRange closedFloatingPointRange, Icon icon, String str, String str2, boolean z, int i, String str3, String str4, boolean z2, AudioStreamModel audioStreamModel) {
            this.value = f;
            this.valueRange = closedFloatingPointRange;
            this.icon = icon;
            this.label = str;
            this.disabledMessage = str2;
            this.isEnabled = z;
            this.a11yStep = i;
            this.a11yClickDescription = str3;
            this.a11yStateDescription = str4;
            this.isMutable = z2;
            this.audioStreamModel = audioStreamModel;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof State)) {
                return false;
            }
            State state = (State) obj;
            return Float.compare(this.value, state.value) == 0 && Intrinsics.areEqual(this.valueRange, state.valueRange) && Intrinsics.areEqual(this.icon, state.icon) && Intrinsics.areEqual(this.label, state.label) && Intrinsics.areEqual(this.disabledMessage, state.disabledMessage) && this.isEnabled == state.isEnabled && this.a11yStep == state.a11yStep && Intrinsics.areEqual(this.a11yClickDescription, state.a11yClickDescription) && Intrinsics.areEqual(this.a11yStateDescription, state.a11yStateDescription) && this.isMutable == state.isMutable && Intrinsics.areEqual(this.audioStreamModel, state.audioStreamModel);
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final String getA11yClickDescription() {
            return this.a11yClickDescription;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final String getA11yStateDescription() {
            return this.a11yStateDescription;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final int getA11yStep() {
            return this.a11yStep;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final String getDisabledMessage() {
            return this.disabledMessage;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final Icon getIcon() {
            return this.icon;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final String getLabel() {
            return this.label;
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
            int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((this.icon.hashCode() + ((this.valueRange.hashCode() + (Float.hashCode(this.value) * 31)) * 31)) * 31, 31, this.label);
            String str = this.disabledMessage;
            int m2 = KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.a11yStep, TransitionData$$ExternalSyntheticOutline0.m((m + (str == null ? 0 : str.hashCode())) * 31, 31, this.isEnabled), 31);
            String str2 = this.a11yClickDescription;
            int hashCode = (m2 + (str2 == null ? 0 : str2.hashCode())) * 31;
            String str3 = this.a11yStateDescription;
            return this.audioStreamModel.hashCode() + TransitionData$$ExternalSyntheticOutline0.m((hashCode + (str3 != null ? str3.hashCode() : 0)) * 31, 31, this.isMutable);
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final boolean isEnabled() {
            return this.isEnabled;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final boolean isMutable() {
            return this.isMutable;
        }

        public final String toString() {
            return "State(value=" + this.value + ", valueRange=" + this.valueRange + ", icon=" + this.icon + ", label=" + this.label + ", disabledMessage=" + this.disabledMessage + ", isEnabled=" + this.isEnabled + ", a11yStep=" + this.a11yStep + ", a11yClickDescription=" + this.a11yClickDescription + ", a11yStateDescription=" + this.a11yStateDescription + ", isMutable=" + this.isMutable + ", audioStreamModel=" + this.audioStreamModel + ")";
        }
    }

    static {
        new Companion(null);
    }

    public AudioStreamSliderViewModel(FactoryAudioStreamWrapper factoryAudioStreamWrapper, CoroutineScope coroutineScope, Context context, AudioVolumeInteractor audioVolumeInteractor, UiEventLogger uiEventLogger) {
        this.coroutineScope = coroutineScope;
        this.context = context;
        this.audioVolumeInteractor = audioVolumeInteractor;
        this.uiEventLogger = uiEventLogger;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.volumeChanges = MutableStateFlow;
        this.streamsAffectedByRing = SetsKt__SetsKt.setOf(2, 5);
        int i = factoryAudioStreamWrapper.audioStream;
        this.audioStream = i;
        AudioStream.m871constructorimpl(3);
        Pair pair = new Pair(AudioStream.m870boximpl(3), Integer.valueOf(R.drawable.ic_music_note));
        AudioStream.m871constructorimpl(0);
        Pair pair2 = new Pair(AudioStream.m870boximpl(0), Integer.valueOf(R.drawable.ic_call));
        AudioStream.m871constructorimpl(6);
        Pair pair3 = new Pair(AudioStream.m870boximpl(6), Integer.valueOf(R.drawable.ic_call));
        AudioStream.m871constructorimpl(2);
        Pair pair4 = new Pair(AudioStream.m870boximpl(2), Integer.valueOf(R.drawable.ic_ring_volume));
        AudioStream.m871constructorimpl(5);
        Pair pair5 = new Pair(AudioStream.m870boximpl(5), Integer.valueOf(R.drawable.ic_volume_ringer));
        AudioStream.m871constructorimpl(4);
        this.iconsByStream = MapsKt__MapsKt.mapOf(pair, pair2, pair3, pair4, pair5, new Pair(AudioStream.m870boximpl(4), Integer.valueOf(R.drawable.ic_volume_alarm)));
        AudioStream.m871constructorimpl(3);
        Pair pair6 = new Pair(AudioStream.m870boximpl(3), Integer.valueOf(R.string.stream_music));
        AudioStream.m871constructorimpl(0);
        Pair pair7 = new Pair(AudioStream.m870boximpl(0), Integer.valueOf(R.string.stream_voice_call));
        AudioStream.m871constructorimpl(6);
        Pair pair8 = new Pair(AudioStream.m870boximpl(6), Integer.valueOf(R.string.stream_voice_call));
        AudioStream.m871constructorimpl(2);
        Pair pair9 = new Pair(AudioStream.m870boximpl(2), Integer.valueOf(R.string.stream_ring));
        AudioStream.m871constructorimpl(5);
        Pair pair10 = new Pair(AudioStream.m870boximpl(5), Integer.valueOf(R.string.stream_notification));
        AudioStream.m871constructorimpl(4);
        this.labelsByStream = MapsKt__MapsKt.mapOf(pair6, pair7, pair8, pair9, pair10, new Pair(AudioStream.m870boximpl(4), Integer.valueOf(R.string.stream_alarm)));
        AudioStream.m871constructorimpl(5);
        this.disabledTextByStream = MapsKt__MapsJVMKt.mapOf(new Pair(AudioStream.m870boximpl(5), Integer.valueOf(R.string.stream_notification_unavailable)));
        AudioStream.m871constructorimpl(3);
        Pair pair11 = new Pair(AudioStream.m870boximpl(3), VolumePanelUiEvent.VOLUME_PANEL_MUSIC_SLIDER_TOUCHED);
        AudioStream.m871constructorimpl(0);
        AudioStream m870boximpl = AudioStream.m870boximpl(0);
        VolumePanelUiEvent volumePanelUiEvent = VolumePanelUiEvent.VOLUME_PANEL_VOICE_CALL_SLIDER_TOUCHED;
        Pair pair12 = new Pair(m870boximpl, volumePanelUiEvent);
        AudioStream.m871constructorimpl(6);
        Pair pair13 = new Pair(AudioStream.m870boximpl(6), volumePanelUiEvent);
        AudioStream.m871constructorimpl(2);
        Pair pair14 = new Pair(AudioStream.m870boximpl(2), VolumePanelUiEvent.VOLUME_PANEL_RING_SLIDER_TOUCHED);
        AudioStream.m871constructorimpl(5);
        Pair pair15 = new Pair(AudioStream.m870boximpl(5), VolumePanelUiEvent.VOLUME_PANEL_NOTIFICATION_SLIDER_TOUCHED);
        AudioStream.m871constructorimpl(4);
        this.uiEventByStream = MapsKt__MapsKt.mapOf(pair11, pair12, pair13, pair14, pair15, new Pair(AudioStream.m870boximpl(4), VolumePanelUiEvent.VOLUME_PANEL_ALARM_SLIDER_TOUCHED));
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine = FlowKt.combine(audioVolumeInteractor.m866getAudioStreamtLTdkI8(i), audioVolumeInteractor.m865canChangeVolumetLTdkI8(i), ((AudioRepositoryImpl) audioVolumeInteractor.audioRepository).ringerMode, new AudioStreamSliderViewModel$slider$1(this, null));
        SharingStarted.Companion.getClass();
        this.slider = FlowKt.stateIn(combine, coroutineScope, SharingStarted.Companion.Eagerly, SliderState.Empty.INSTANCE);
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(MutableStateFlow), new AnonymousClass1(null)), coroutineScope);
    }

    @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel
    public final ReadonlyStateFlow getSlider() {
        return this.slider;
    }

    @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel
    public final void onValueChangeFinished() {
        VolumePanelUiEvent volumePanelUiEvent = (VolumePanelUiEvent) this.uiEventByStream.get(AudioStream.m870boximpl(this.audioStream));
        if (volumePanelUiEvent != null) {
            this.uiEventLogger.log(volumePanelUiEvent);
        }
    }

    @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel
    public final void onValueChanged(SliderState sliderState, float f) {
        if ((sliderState instanceof State ? (State) sliderState : null) == null) {
            return;
        }
        this.volumeChanges.updateState(null, Integer.valueOf(MathKt__MathJVMKt.roundToInt(f)));
    }

    @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel
    public final void toggleMuted(SliderState sliderState) {
        State state = sliderState instanceof State ? (State) sliderState : null;
        if (state == null) {
            return;
        }
        BuildersKt.launch$default(this.coroutineScope, null, null, new AudioStreamSliderViewModel$toggleMuted$1(this, state, null), 3);
    }
}
