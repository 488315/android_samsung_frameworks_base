package com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.haptics.slider.SliderHapticFeedbackFilter;
import com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1;
import com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState;
import com.android.systemui.volume.panel.shared.VolumePanelLogger;
import com.android.systemui.volume.panel.shared.VolumePanelLogger$$ExternalSyntheticLambda0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes3.dex */
public final class CastVolumeSliderViewModel implements SliderViewModel {
    public final Icon.Loaded castIcon;
    public final String castLabel;
    public final CoroutineScope coroutineScope;
    public final SliderHapticsViewModel.Factory hapticsViewModelFactory;
    public final MediaDeviceSessionInteractor mediaDeviceSessionInteractor;
    public final MediaDeviceSession session;
    public final ReadonlyStateFlow slider;
    public final CoroutineContext uiBackgroundContext;
    public final VolumePanelLogger volumePanelLogger;

    public interface Factory {
        CastVolumeSliderViewModel create(MediaDeviceSession mediaDeviceSession, CoroutineScope coroutineScope);
    }

    public final class State implements SliderState {
        public final Icon.Loaded icon;
        public final boolean isEnabled;
        public final String label;
        public final float step;
        public final float value;
        public final ClosedFloatingPointRange valueRange;

        public State(float f, ClosedFloatingPointRange closedFloatingPointRange, Icon.Loaded loaded, String str, boolean z, float f2) {
            this.value = f;
            this.valueRange = closedFloatingPointRange;
            this.icon = loaded;
            this.label = str;
            this.isEnabled = z;
            this.step = f2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof State)) {
                return false;
            }
            State state = (State) obj;
            return Float.compare(this.value, state.value) == 0 && Intrinsics.areEqual(this.valueRange, state.valueRange) && Intrinsics.areEqual(this.icon, state.icon) && Intrinsics.areEqual(this.label, state.label) && this.isEnabled == state.isEnabled && Float.compare(this.step, state.step) == 0;
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
            int iHashCode = (this.valueRange.hashCode() + (Float.hashCode(this.value) * 31)) * 31;
            Icon.Loaded loaded = this.icon;
            return Float.hashCode(this.step) + TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((iHashCode + (loaded == null ? 0 : loaded.hashCode())) * 31, 31, this.label), 31, this.isEnabled);
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final boolean isEnabled() {
            return this.isEnabled;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final boolean isMutable() {
            return false;
        }

        public final String toString() {
            return "State(value=" + this.value + ", valueRange=" + this.valueRange + ", icon=" + this.icon + ", label=" + this.label + ", isEnabled=" + this.isEnabled + ", step=" + this.step + ")";
        }
    }

    /* renamed from: com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$onValueChanged$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ float $newValue;
        int label;
        final /* synthetic */ CastVolumeSliderViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(float f, CastVolumeSliderViewModel castVolumeSliderViewModel, Continuation continuation) {
            super(2, continuation);
            this.$newValue = f;
            this.this$0 = castVolumeSliderViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$newValue, this.this$0, continuation);
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
                int iRoundToInt = MathKt__MathJVMKt.roundToInt(this.$newValue);
                CastVolumeSliderViewModel castVolumeSliderViewModel = this.this$0;
                VolumePanelLogger volumePanelLogger = castVolumeSliderViewModel.volumePanelLogger;
                MediaSession.Token token = castVolumeSliderViewModel.session.sessionToken;
                volumePanelLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                VolumePanelLogger$$ExternalSyntheticLambda0 volumePanelLogger$$ExternalSyntheticLambda0 = new VolumePanelLogger$$ExternalSyntheticLambda0(8);
                LogBuffer logBuffer = volumePanelLogger.logBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("SysUI_VolumePanel", logLevel, volumePanelLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.str1 = token.toString();
                logMessageImpl.int1 = iRoundToInt;
                logBuffer.commit(logMessageObtain);
                CastVolumeSliderViewModel castVolumeSliderViewModel2 = this.this$0;
                MediaDeviceSessionInteractor mediaDeviceSessionInteractor = castVolumeSliderViewModel2.mediaDeviceSessionInteractor;
                this.label = 1;
                if (mediaDeviceSessionInteractor.setSessionVolume(castVolumeSliderViewModel2.session, iRoundToInt, this) == coroutineSingletons) {
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

    public CastVolumeSliderViewModel(MediaDeviceSession mediaDeviceSession, CoroutineScope coroutineScope, CoroutineContext coroutineContext, Context context, MediaDeviceSessionInteractor mediaDeviceSessionInteractor, SliderHapticsViewModel.Factory factory, VolumePanelLogger volumePanelLogger) {
        this.session = mediaDeviceSession;
        this.coroutineScope = coroutineScope;
        this.uiBackgroundContext = coroutineContext;
        this.mediaDeviceSessionInteractor = mediaDeviceSessionInteractor;
        this.hapticsViewModelFactory = factory;
        this.volumePanelLogger = volumePanelLogger;
        this.castLabel = context.getString(R.string.media_device_cast);
        Drawable drawable = context.getDrawable(R.drawable.ic_cast);
        drawable.getClass();
        this.castIcon = new Icon.Loaded(drawable, null, Integer.valueOf(R.drawable.ic_cast));
        final MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1 mediaDeviceSessionInteractor$playbackInfo$$inlined$map$1PlaybackInfo = mediaDeviceSessionInteractor.playbackInfo(mediaDeviceSession);
        Flow flow = new Flow() { // from class: com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$special$$inlined$mapNotNull$1

            /* renamed from: com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$special$$inlined$mapNotNull$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CastVolumeSliderViewModel this$0;

                /* renamed from: com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$special$$inlined$mapNotNull$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, CastVolumeSliderViewModel castVolumeSliderViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = castVolumeSliderViewModel;
                }

                /* JADX WARN: Code restructure failed: missing block: B:21:0x008b, code lost:
                
                    if (r11.emit(r13, r0) == r1) goto L22;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Throwable {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object objWithContext = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(objWithContext);
                        MediaController.PlaybackInfo playbackInfo = (MediaController.PlaybackInfo) obj;
                        CastVolumeSliderViewModel castVolumeSliderViewModel = this.this$0;
                        VolumePanelLogger volumePanelLogger = castVolumeSliderViewModel.volumePanelLogger;
                        MediaSession.Token token = castVolumeSliderViewModel.session.sessionToken;
                        int currentVolume = playbackInfo.getCurrentVolume();
                        volumePanelLogger.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        VolumePanelLogger$$ExternalSyntheticLambda0 volumePanelLogger$$ExternalSyntheticLambda0 = new VolumePanelLogger$$ExternalSyntheticLambda0(2);
                        LogBuffer logBuffer = volumePanelLogger.logBuffer;
                        LogMessage logMessageObtain = logBuffer.obtain("SysUI_VolumePanel", logLevel, volumePanelLogger$$ExternalSyntheticLambda0, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                        logMessageImpl.str1 = token.toString();
                        logMessageImpl.int1 = currentVolume;
                        logBuffer.commit(logMessageObtain);
                        CoroutineContext coroutineContext = castVolumeSliderViewModel.uiBackgroundContext;
                        CastVolumeSliderViewModel$slider$1$1 castVolumeSliderViewModel$slider$1$1 = new CastVolumeSliderViewModel$slider$1$1(castVolumeSliderViewModel, playbackInfo, null);
                        flowCollector = this.$this_unsafeFlow;
                        anonymousClass1.L$0 = flowCollector;
                        anonymousClass1.label = 1;
                        objWithContext = BuildersKt.withContext(coroutineContext, castVolumeSliderViewModel$slider$1$1, anonymousClass1);
                        if (objWithContext != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(objWithContext);
                        return Unit.INSTANCE;
                    }
                    flowCollector = (FlowCollector) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(objWithContext);
                    if (objWithContext != null) {
                        anonymousClass1.L$0 = null;
                        anonymousClass1.label = 2;
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = mediaDeviceSessionInteractor$playbackInfo$$inlined$map$1PlaybackInfo.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.slider = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Eagerly, SliderState.Empty.INSTANCE);
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
    public final void onValueChanged(SliderState sliderState, float f) {
        CoroutineTracingKt.launchTraced$default(this.coroutineScope, null, null, new AnonymousClass1(f, this, null), 7);
    }

    @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel
    public final void toggleMuted(SliderState sliderState) {
    }

    @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel
    public final void onValueChangeFinished() {
    }
}
