package com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel;

import android.content.Context;
import android.media.AudioSystem;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.volume.data.repository.AudioRepositoryImpl;
import com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor;
import com.android.settingslib.volume.shared.model.AudioStream;
import com.android.settingslib.volume.shared.model.AudioStreamModel;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                LogMessage obtain = logBuffer.obtain("SysUI_VolumePanel", logLevel, volumePanelLogger$$ExternalSyntheticLambda0, null);
                AudioStream.Companion companion = AudioStream.Companion;
                LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                logMessageImpl.str1 = AudioSystem.streamToString(audioStreamSliderViewModel.audioStream);
                logMessageImpl.int1 = i2;
                logBuffer.commit(obtain);
                AudioStreamSliderViewModel audioStreamSliderViewModel2 = AudioStreamSliderViewModel.this;
                AudioVolumeInteractor audioVolumeInteractor = audioStreamSliderViewModel2.audioVolumeInteractor;
                this.label = 1;
                if (audioVolumeInteractor.m986setVolumeZdW0WiI(audioStreamSliderViewModel2.audioStream, i2, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        AudioStreamSliderViewModel create(FactoryAudioStreamWrapper factoryAudioStreamWrapper, CoroutineScope coroutineScope);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FactoryAudioStreamWrapper {
        public final int audioStream;

        public /* synthetic */ FactoryAudioStreamWrapper(int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(i);
        }

        private FactoryAudioStreamWrapper(int i) {
            this.audioStream = i;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int hashCode = (this.hapticFilter.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.step, (this.valueRange.hashCode() + (Float.hashCode(this.value) * 31)) * 31, 31)) * 31;
            Icon.Loaded loaded = this.icon;
            int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((hashCode + (loaded == null ? 0 : loaded.hashCode())) * 31, 31, this.label);
            String str = this.disabledMessage;
            int m2 = TransitionData$$ExternalSyntheticOutline0.m((m + (str == null ? 0 : str.hashCode())) * 31, 31, this.isEnabled);
            String str2 = this.a11yClickDescription;
            int hashCode2 = (m2 + (str2 == null ? 0 : str2.hashCode())) * 31;
            String str3 = this.a11yStateDescription;
            return this.audioStreamModel.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((hashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31, 31, this.a11yContentDescription), 31, this.isMutable);
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
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.volumeChanges = MutableStateFlow;
        int i = factoryAudioStreamWrapper.audioStream;
        this.audioStream = i;
        AudioStream.m989constructorimpl(3);
        Pair pair = new Pair(AudioStream.m988boximpl(3), Integer.valueOf(R.string.stream_music));
        AudioStream.m989constructorimpl(0);
        Pair pair2 = new Pair(AudioStream.m988boximpl(0), Integer.valueOf(R.string.stream_voice_call));
        AudioStream.m989constructorimpl(2);
        Pair pair3 = new Pair(AudioStream.m988boximpl(2), Integer.valueOf(R.string.stream_ring));
        AudioStream.m989constructorimpl(5);
        Pair pair4 = new Pair(AudioStream.m988boximpl(5), Integer.valueOf(R.string.stream_notification));
        AudioStream.m989constructorimpl(4);
        this.labelsByStream = MapsKt__MapsKt.mapOf(pair, pair2, pair3, pair4, new Pair(AudioStream.m988boximpl(4), Integer.valueOf(R.string.stream_alarm)));
        AudioStream.m989constructorimpl(3);
        Pair pair5 = new Pair(AudioStream.m988boximpl(3), VolumePanelUiEvent.VOLUME_PANEL_MUSIC_SLIDER_TOUCHED);
        AudioStream.m989constructorimpl(0);
        Pair pair6 = new Pair(AudioStream.m988boximpl(0), VolumePanelUiEvent.VOLUME_PANEL_VOICE_CALL_SLIDER_TOUCHED);
        AudioStream.m989constructorimpl(2);
        Pair pair7 = new Pair(AudioStream.m988boximpl(2), VolumePanelUiEvent.VOLUME_PANEL_RING_SLIDER_TOUCHED);
        AudioStream.m989constructorimpl(5);
        Pair pair8 = new Pair(AudioStream.m988boximpl(5), VolumePanelUiEvent.VOLUME_PANEL_NOTIFICATION_SLIDER_TOUCHED);
        AudioStream.m989constructorimpl(4);
        this.uiEventByStream = MapsKt__MapsKt.mapOf(pair5, pair6, pair7, pair8, new Pair(AudioStream.m988boximpl(4), VolumePanelUiEvent.VOLUME_PANEL_ALARM_SLIDER_TOUCHED));
        final Flow[] flowArr = {audioVolumeInteractor.m984getAudioStreamtLTdkI8(i), audioVolumeInteractor.m983canChangeVolumetLTdkI8(i), ((AudioRepositoryImpl) audioVolumeInteractor.audioRepository).ringerMode, new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(i == 5 ? context.getString(R.string.stream_notification_unavailable) : context.getString(R.string.stream_alarm_unavailable)), audioSharingInteractor.isInAudioSharing(), audioSharingInteractor.getPrimaryDevice()};
        Flow flow = new Flow() { // from class: com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioStreamSliderViewModel$special$$inlined$combine$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Code restructure failed: missing block: B:13:0x00ab, code lost:
                
                    if (r3.emit(r1, r21) == r2) goto L15;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:14:0x00ad, code lost:
                
                    return r2;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:16:0x00a0, code lost:
                
                    if (r1 == r2) goto L15;
                 */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r22) {
                    /*
                        r21 = this;
                        r0 = r21
                        r1 = 1
                        kotlin.coroutines.intrinsics.CoroutineSingletons r2 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r3 = r0.label
                        r4 = 0
                        r5 = 2
                        if (r3 == 0) goto L28
                        if (r3 == r1) goto L1c
                        if (r3 != r5) goto L14
                        kotlin.ResultKt.throwOnFailure(r22)
                        goto Lae
                    L14:
                        java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                        java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                        r0.<init>(r1)
                        throw r0
                    L1c:
                        java.lang.Object r1 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                        kotlin.ResultKt.throwOnFailure(r22)
                        r3 = r1
                        r1 = r22
                        goto La3
                    L28:
                        kotlin.ResultKt.throwOnFailure(r22)
                        java.lang.Object r3 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
                        java.lang.Object r6 = r0.L$1
                        java.lang.Object[] r6 = (java.lang.Object[]) r6
                        r7 = 0
                        r7 = r6[r7]
                        r8 = r6[r1]
                        r9 = r6[r5]
                        r10 = 3
                        r10 = r6[r10]
                        r11 = 4
                        r11 = r6[r11]
                        r12 = 5
                        r6 = r6[r12]
                        r16 = r6
                        com.android.settingslib.bluetooth.CachedBluetoothDevice r16 = (com.android.settingslib.bluetooth.CachedBluetoothDevice) r16
                        java.lang.Boolean r11 = (java.lang.Boolean) r11
                        boolean r15 = r11.booleanValue()
                        r19 = r10
                        java.lang.String r19 = (java.lang.String) r19
                        com.android.settingslib.volume.shared.model.RingerMode r9 = (com.android.settingslib.volume.shared.model.RingerMode) r9
                        int r6 = r9.value
                        java.lang.Boolean r8 = (java.lang.Boolean) r8
                        boolean r18 = r8.booleanValue()
                        r14 = r7
                        com.android.settingslib.volume.shared.model.AudioStreamModel r14 = (com.android.settingslib.volume.shared.model.AudioStreamModel) r14
                        com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioStreamSliderViewModel r7 = r0.this$0
                        com.android.systemui.volume.panel.shared.VolumePanelLogger r8 = r7.volumePanelLogger
                        int r7 = r7.audioStream
                        int r9 = r14.volume
                        r8.getClass()
                        com.android.systemui.log.core.LogLevel r10 = com.android.systemui.log.core.LogLevel.DEBUG
                        com.android.systemui.volume.panel.shared.VolumePanelLogger$$ExternalSyntheticLambda0 r11 = new com.android.systemui.volume.panel.shared.VolumePanelLogger$$ExternalSyntheticLambda0
                        r11.<init>(r1)
                        java.lang.String r12 = "SysUI_VolumePanel"
                        com.android.systemui.log.LogBuffer r8 = r8.logBuffer
                        com.android.systemui.log.core.LogMessage r10 = r8.obtain(r12, r10, r11, r4)
                        com.android.settingslib.volume.shared.model.AudioStream$Companion r11 = com.android.settingslib.volume.shared.model.AudioStream.Companion
                        java.lang.String r7 = android.media.AudioSystem.streamToString(r7)
                        r11 = r10
                        com.android.systemui.log.LogMessageImpl r11 = (com.android.systemui.log.LogMessageImpl) r11
                        r11.str1 = r7
                        r11.int1 = r9
                        r8.commit(r10)
                        com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioStreamSliderViewModel r13 = r0.this$0
                        r0.L$0 = r3
                        r0.label = r1
                        r13.getClass()
                        com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioStreamSliderViewModel$toState$2 r12 = new com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioStreamSliderViewModel$toState$2
                        r20 = 0
                        r17 = r6
                        r12.<init>(r13, r14, r15, r16, r17, r18, r19, r20)
                        kotlin.coroutines.CoroutineContext r1 = r13.uiBackgroundContext
                        java.lang.Object r1 = kotlinx.coroutines.BuildersKt.withContext(r1, r12, r0)
                        if (r1 != r2) goto La3
                        goto Lad
                    La3:
                        r0.L$0 = r4
                        r0.label = r5
                        java.lang.Object r0 = r3.emit(r1, r0)
                        if (r0 != r2) goto Lae
                    Lad:
                        return r2
                    Lae:
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioStreamSliderViewModel$special$$inlined$combine$1.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioStreamSliderViewModel$special$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.slider = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Eagerly, SliderState.Empty.INSTANCE);
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(MutableStateFlow), new AnonymousClass1(null)), coroutineScope);
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
        VolumePanelUiEvent volumePanelUiEvent = (VolumePanelUiEvent) this.uiEventByStream.get(AudioStream.m988boximpl(this.audioStream));
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
        CoroutineTracingKt.launchTraced$default(this.coroutineScope, null, null, new AudioStreamSliderViewModel$toggleMuted$1(this, state, null), 7);
    }
}
