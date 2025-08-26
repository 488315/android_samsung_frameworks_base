package com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel;

import android.content.Context;
import android.media.AudioSystem;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.volume.data.repository.AudioRepositoryImpl;
import com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor;
import com.android.settingslib.volume.shared.model.AudioStream;
import com.android.settingslib.volume.shared.model.AudioStreamModel;
import com.android.settingslib.volume.shared.model.RingerMode;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.haptics.slider.SliderHapticFeedbackFilter;
import com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import com.android.systemui.volume.domain.interactor.AudioSharingInteractor;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState;
import com.android.systemui.volume.panel.shared.VolumePanelLogger;
import com.android.systemui.volume.panel.shared.VolumePanelLogger$$ExternalSyntheticLambda0;
import com.android.systemui.volume.panel.ui.VolumePanelUiEvent;
import java.util.Map;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.CombineKt;

/* loaded from: classes3.dex */
public final class AudioStreamSliderViewModel implements SliderViewModel {
    public final int audioStream;
    public final AudioVolumeInteractor audioVolumeInteractor;
    public final Context context;
    public final CoroutineScope coroutineScope;
    public final SliderHapticsViewModel.Factory hapticsViewModelFactory;
    public final Map labelsByStream;
    public final ReadonlyStateFlow slider;
    public final CoroutineContext uiBackgroundContext;
    public final Map uiEventByStream;
    public final UiEventLogger uiEventLogger;
    public final StateFlowImpl volumeChanges;
    public final VolumePanelLogger volumePanelLogger;

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
                VolumePanelLogger volumePanelLogger = audioStreamSliderViewModel.volumePanelLogger;
                volumePanelLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                VolumePanelLogger$$ExternalSyntheticLambda0 volumePanelLogger$$ExternalSyntheticLambda0 = new VolumePanelLogger$$ExternalSyntheticLambda0(0);
                LogBuffer logBuffer = volumePanelLogger.logBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("SysUI_VolumePanel", logLevel, volumePanelLogger$$ExternalSyntheticLambda0, null);
                AudioStream.Companion companion = AudioStream.Companion;
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.str1 = AudioSystem.streamToString(audioStreamSliderViewModel.audioStream);
                logMessageImpl.int1 = i2;
                logBuffer.commit(logMessageObtain);
                AudioStreamSliderViewModel audioStreamSliderViewModel2 = AudioStreamSliderViewModel.this;
                AudioVolumeInteractor audioVolumeInteractor = audioStreamSliderViewModel2.audioVolumeInteractor;
                this.label = 1;
                if (audioVolumeInteractor.m988setVolumeZdW0WiI(audioStreamSliderViewModel2.audioStream, i2, this) == coroutineSingletons) {
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        AudioStreamSliderViewModel create(FactoryAudioStreamWrapper factoryAudioStreamWrapper, CoroutineScope coroutineScope);
    }

    public final class FactoryAudioStreamWrapper {
        public final int audioStream;

        public /* synthetic */ FactoryAudioStreamWrapper(int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(i);
        }

        private FactoryAudioStreamWrapper(int i) {
            this.audioStream = i;
        }
    }

    public final class State implements SliderState {
        public final String a11yClickDescription;
        public final String a11yContentDescription;
        public final String a11yStateDescription;
        public final AudioStreamModel audioStreamModel;
        public final String disabledMessage;
        public final SliderHapticFeedbackFilter hapticFilter;
        public final Icon.Loaded icon;
        public final boolean isEnabled;
        public final boolean isMutable;
        public final String label;
        public final float step;
        public final float value;
        public final ClosedFloatingPointRange valueRange;

        public State(float f, ClosedFloatingPointRange closedFloatingPointRange, float f2, SliderHapticFeedbackFilter sliderHapticFeedbackFilter, Icon.Loaded loaded, String str, String str2, boolean z, String str3, String str4, String str5, boolean z2, AudioStreamModel audioStreamModel) {
            this.value = f;
            this.valueRange = closedFloatingPointRange;
            this.step = f2;
            this.hapticFilter = sliderHapticFeedbackFilter;
            this.icon = loaded;
            this.label = str;
            this.disabledMessage = str2;
            this.isEnabled = z;
            this.a11yClickDescription = str3;
            this.a11yStateDescription = str4;
            this.a11yContentDescription = str5;
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
            return Float.compare(this.value, state.value) == 0 && Intrinsics.areEqual(this.valueRange, state.valueRange) && Float.compare(this.step, state.step) == 0 && Intrinsics.areEqual(this.hapticFilter, state.hapticFilter) && Intrinsics.areEqual(this.icon, state.icon) && Intrinsics.areEqual(this.label, state.label) && Intrinsics.areEqual(this.disabledMessage, state.disabledMessage) && this.isEnabled == state.isEnabled && Intrinsics.areEqual(this.a11yClickDescription, state.a11yClickDescription) && Intrinsics.areEqual(this.a11yStateDescription, state.a11yStateDescription) && Intrinsics.areEqual(this.a11yContentDescription, state.a11yContentDescription) && this.isMutable == state.isMutable && Intrinsics.areEqual(this.audioStreamModel, state.audioStreamModel);
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
        public final String getDisabledMessage() {
            return this.disabledMessage;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final SliderHapticFeedbackFilter getHapticFilter() {
            return this.hapticFilter;
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
            return this.step;
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
            int iHashCode = (this.hapticFilter.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.step, (this.valueRange.hashCode() + (Float.hashCode(this.value) * 31)) * 31, 31)) * 31;
            Icon.Loaded loaded = this.icon;
            int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((iHashCode + (loaded == null ? 0 : loaded.hashCode())) * 31, 31, this.label);
            String str = this.disabledMessage;
            int iM2 = TransitionData$$ExternalSyntheticOutline0.m((iM + (str == null ? 0 : str.hashCode())) * 31, 31, this.isEnabled);
            String str2 = this.a11yClickDescription;
            int iHashCode2 = (iM2 + (str2 == null ? 0 : str2.hashCode())) * 31;
            String str3 = this.a11yStateDescription;
            return this.audioStreamModel.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((iHashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31, 31, this.a11yContentDescription), 31, this.isMutable);
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
            return "State(value=" + this.value + ", valueRange=" + this.valueRange + ", step=" + this.step + ", hapticFilter=" + this.hapticFilter + ", icon=" + this.icon + ", label=" + this.label + ", disabledMessage=" + this.disabledMessage + ", isEnabled=" + this.isEnabled + ", a11yClickDescription=" + this.a11yClickDescription + ", a11yStateDescription=" + this.a11yStateDescription + ", a11yContentDescription=" + this.a11yContentDescription + ", isMutable=" + this.isMutable + ", audioStreamModel=" + this.audioStreamModel + ")";
        }
    }

    /* renamed from: com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioStreamSliderViewModel$toggleMuted$1, reason: invalid class name and case insensitive filesystem */
    final class C11901 extends SuspendLambda implements Function2 {
        final /* synthetic */ State $audioViewModel;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11901(State state, Continuation continuation) {
            super(2, continuation);
            this.$audioViewModel = state;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AudioStreamSliderViewModel.this.new C11901(this.$audioViewModel, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C11901) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                AudioStreamSliderViewModel audioStreamSliderViewModel = AudioStreamSliderViewModel.this;
                AudioVolumeInteractor audioVolumeInteractor = audioStreamSliderViewModel.audioVolumeInteractor;
                boolean z = !this.$audioViewModel.audioStreamModel.isMuted;
                this.label = 1;
                if (audioVolumeInteractor.m987setMutedZdW0WiI(audioStreamSliderViewModel.audioStream, this, z) == coroutineSingletons) {
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

    static {
        new Companion(null);
    }

    public AudioStreamSliderViewModel(FactoryAudioStreamWrapper factoryAudioStreamWrapper, CoroutineScope coroutineScope, CoroutineContext coroutineContext, Context context, AudioVolumeInteractor audioVolumeInteractor, ZenModeInteractor zenModeInteractor, AudioSharingInteractor audioSharingInteractor, UiEventLogger uiEventLogger, VolumePanelLogger volumePanelLogger, SliderHapticsViewModel.Factory factory) {
        this.coroutineScope = coroutineScope;
        this.uiBackgroundContext = coroutineContext;
        this.context = context;
        this.audioVolumeInteractor = audioVolumeInteractor;
        this.uiEventLogger = uiEventLogger;
        this.volumePanelLogger = volumePanelLogger;
        this.hapticsViewModelFactory = factory;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.volumeChanges = stateFlowImplMutableStateFlow;
        int i = factoryAudioStreamWrapper.audioStream;
        this.audioStream = i;
        AudioStream.m991constructorimpl(3);
        Pair pair = new Pair(AudioStream.m990boximpl(3), Integer.valueOf(R.string.stream_music));
        AudioStream.m991constructorimpl(0);
        Pair pair2 = new Pair(AudioStream.m990boximpl(0), Integer.valueOf(R.string.stream_voice_call));
        AudioStream.m991constructorimpl(2);
        Pair pair3 = new Pair(AudioStream.m990boximpl(2), Integer.valueOf(R.string.stream_ring));
        AudioStream.m991constructorimpl(5);
        Pair pair4 = new Pair(AudioStream.m990boximpl(5), Integer.valueOf(R.string.stream_notification));
        AudioStream.m991constructorimpl(4);
        this.labelsByStream = MapsKt__MapsKt.mapOf(pair, pair2, pair3, pair4, new Pair(AudioStream.m990boximpl(4), Integer.valueOf(R.string.stream_alarm)));
        AudioStream.m991constructorimpl(3);
        Pair pair5 = new Pair(AudioStream.m990boximpl(3), VolumePanelUiEvent.VOLUME_PANEL_MUSIC_SLIDER_TOUCHED);
        AudioStream.m991constructorimpl(0);
        Pair pair6 = new Pair(AudioStream.m990boximpl(0), VolumePanelUiEvent.VOLUME_PANEL_VOICE_CALL_SLIDER_TOUCHED);
        AudioStream.m991constructorimpl(2);
        Pair pair7 = new Pair(AudioStream.m990boximpl(2), VolumePanelUiEvent.VOLUME_PANEL_RING_SLIDER_TOUCHED);
        AudioStream.m991constructorimpl(5);
        Pair pair8 = new Pair(AudioStream.m990boximpl(5), VolumePanelUiEvent.VOLUME_PANEL_NOTIFICATION_SLIDER_TOUCHED);
        AudioStream.m991constructorimpl(4);
        this.uiEventByStream = MapsKt__MapsKt.mapOf(pair5, pair6, pair7, pair8, new Pair(AudioStream.m990boximpl(4), VolumePanelUiEvent.VOLUME_PANEL_ALARM_SLIDER_TOUCHED));
        final Flow[] flowArr = {audioVolumeInteractor.m986getAudioStreamtLTdkI8(i), audioVolumeInteractor.m985canChangeVolumetLTdkI8(i), ((AudioRepositoryImpl) audioVolumeInteractor.audioRepository).ringerMode, new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(i == 5 ? context.getString(R.string.stream_notification_unavailable) : context.getString(R.string.stream_alarm_unavailable)), audioSharingInteractor.isInAudioSharing(), audioSharingInteractor.getPrimaryDevice()};
        Flow flow = new Flow() { // from class: com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioStreamSliderViewModel$special$$inlined$combine$1

            /* renamed from: com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioStreamSliderViewModel$special$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ AudioStreamSliderViewModel this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, AudioStreamSliderViewModel audioStreamSliderViewModel) {
                    super(3, continuation);
                    this.this$0 = audioStreamSliderViewModel;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.this$0);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code restructure failed: missing block: B:14:0x00ab, code lost:
                
                    if (r3.emit(r1, r21) == r2) goto L15;
                 */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invokeSuspend(Object obj) throws Throwable {
                    FlowCollector flowCollector;
                    Object objWithContext;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        Object obj2 = objArr[0];
                        Object obj3 = objArr[1];
                        Object obj4 = objArr[2];
                        Object obj5 = objArr[3];
                        Object obj6 = objArr[4];
                        CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) objArr[5];
                        boolean zBooleanValue = ((Boolean) obj6).booleanValue();
                        String str = (String) obj5;
                        int i2 = ((RingerMode) obj4).value;
                        boolean zBooleanValue2 = ((Boolean) obj3).booleanValue();
                        AudioStreamModel audioStreamModel = (AudioStreamModel) obj2;
                        AudioStreamSliderViewModel audioStreamSliderViewModel = this.this$0;
                        VolumePanelLogger volumePanelLogger = audioStreamSliderViewModel.volumePanelLogger;
                        int i3 = audioStreamSliderViewModel.audioStream;
                        int i4 = audioStreamModel.volume;
                        volumePanelLogger.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        VolumePanelLogger$$ExternalSyntheticLambda0 volumePanelLogger$$ExternalSyntheticLambda0 = new VolumePanelLogger$$ExternalSyntheticLambda0(1);
                        LogBuffer logBuffer = volumePanelLogger.logBuffer;
                        LogMessage logMessageObtain = logBuffer.obtain("SysUI_VolumePanel", logLevel, volumePanelLogger$$ExternalSyntheticLambda0, null);
                        AudioStream.Companion companion = AudioStream.Companion;
                        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                        logMessageImpl.str1 = AudioSystem.streamToString(i3);
                        logMessageImpl.int1 = i4;
                        logBuffer.commit(logMessageObtain);
                        AudioStreamSliderViewModel audioStreamSliderViewModel2 = this.this$0;
                        this.L$0 = flowCollector;
                        this.label = 1;
                        audioStreamSliderViewModel2.getClass();
                        objWithContext = BuildersKt.withContext(audioStreamSliderViewModel2.uiBackgroundContext, new AudioStreamSliderViewModel$toState$2(audioStreamSliderViewModel2, audioStreamModel, zBooleanValue, cachedBluetoothDevice, i2, zBooleanValue2, str, null), this);
                        if (objWithContext != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    if (i != 1) {
                        if (i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    FlowCollector flowCollector2 = (FlowCollector) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    flowCollector = flowCollector2;
                    objWithContext = obj;
                    this.L$0 = null;
                    this.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioStreamSliderViewModel$special$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.slider = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Eagerly, SliderState.Empty.INSTANCE);
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
        VolumePanelUiEvent volumePanelUiEvent = (VolumePanelUiEvent) this.uiEventByStream.get(AudioStream.m990boximpl(this.audioStream));
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
        CoroutineTracingKt.launchTraced$default(this.coroutineScope, null, null, new C11901(state, null), 7);
    }
}
