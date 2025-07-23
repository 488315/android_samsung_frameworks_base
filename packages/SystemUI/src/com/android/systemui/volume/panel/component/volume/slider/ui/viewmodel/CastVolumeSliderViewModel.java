package com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.haptics.slider.SliderHapticFeedbackFilter;
import com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1;
import com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState;
import com.android.systemui.volume.panel.shared.VolumePanelLogger;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        CastVolumeSliderViewModel create(MediaDeviceSession mediaDeviceSession, CoroutineScope coroutineScope);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int hashCode = (this.valueRange.hashCode() + (Float.hashCode(this.value) * 31)) * 31;
            Icon.Loaded loaded = this.icon;
            return Float.hashCode(this.step) + TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((hashCode + (loaded == null ? 0 : loaded.hashCode())) * 31, 31, this.label), 31, this.isEnabled);
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
        final MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1 playbackInfo = mediaDeviceSessionInteractor.playbackInfo(mediaDeviceSession);
        Flow flow = new Flow() { // from class: com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$special$$inlined$mapNotNull$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Code restructure failed: missing block: B:19:0x008b, code lost:
                
                    if (r11.emit(r13, r0) == r1) goto L22;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:20:0x008d, code lost:
                
                    return r1;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:22:0x007e, code lost:
                
                    if (r13 == r1) goto L22;
                 */
                /* JADX WARN: Removed duplicated region for block: B:18:0x0083  */
                /* JADX WARN: Removed duplicated region for block: B:21:0x003b  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r12, kotlin.coroutines.Continuation r13) {
                    /*
                        r11 = this;
                        boolean r0 = r13 instanceof com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$special$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r13
                        com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$special$$inlined$mapNotNull$1$2$1 r0 = (com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$special$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$special$$inlined$mapNotNull$1$2$1 r0 = new com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$special$$inlined$mapNotNull$1$2$1
                        r0.<init>(r13)
                    L18:
                        java.lang.Object r13 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 2
                        r4 = 1
                        r5 = 0
                        if (r2 == 0) goto L3b
                        if (r2 == r4) goto L33
                        if (r2 != r3) goto L2b
                        kotlin.ResultKt.throwOnFailure(r13)
                        goto L8e
                    L2b:
                        java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                        java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                        r11.<init>(r12)
                        throw r11
                    L33:
                        java.lang.Object r11 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r11 = (kotlinx.coroutines.flow.FlowCollector) r11
                        kotlin.ResultKt.throwOnFailure(r13)
                        goto L81
                    L3b:
                        kotlin.ResultKt.throwOnFailure(r13)
                        android.media.session.MediaController$PlaybackInfo r12 = (android.media.session.MediaController.PlaybackInfo) r12
                        com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel r13 = r11.this$0
                        com.android.systemui.volume.panel.shared.VolumePanelLogger r2 = r13.volumePanelLogger
                        com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r6 = r13.session
                        android.media.session.MediaSession$Token r6 = r6.sessionToken
                        int r7 = r12.getCurrentVolume()
                        r2.getClass()
                        com.android.systemui.log.core.LogLevel r8 = com.android.systemui.log.core.LogLevel.DEBUG
                        com.android.systemui.volume.panel.shared.VolumePanelLogger$$ExternalSyntheticLambda0 r9 = new com.android.systemui.volume.panel.shared.VolumePanelLogger$$ExternalSyntheticLambda0
                        r10 = 2
                        r9.<init>(r10)
                        java.lang.String r10 = "SysUI_VolumePanel"
                        com.android.systemui.log.LogBuffer r2 = r2.logBuffer
                        com.android.systemui.log.core.LogMessage r8 = r2.obtain(r10, r8, r9, r5)
                        java.lang.String r6 = r6.toString()
                        r9 = r8
                        com.android.systemui.log.LogMessageImpl r9 = (com.android.systemui.log.LogMessageImpl) r9
                        r9.str1 = r6
                        r9.int1 = r7
                        r2.commit(r8)
                        kotlin.coroutines.CoroutineContext r2 = r13.uiBackgroundContext
                        com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$slider$1$1 r6 = new com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$slider$1$1
                        r6.<init>(r13, r12, r5)
                        kotlinx.coroutines.flow.FlowCollector r11 = r11.$this_unsafeFlow
                        r0.L$0 = r11
                        r0.label = r4
                        java.lang.Object r13 = kotlinx.coroutines.BuildersKt.withContext(r2, r6, r0)
                        if (r13 != r1) goto L81
                        goto L8d
                    L81:
                        if (r13 == 0) goto L8e
                        r0.L$0 = r5
                        r0.label = r3
                        java.lang.Object r11 = r11.emit(r13, r0)
                        if (r11 != r1) goto L8e
                    L8d:
                        return r1
                    L8e:
                        kotlin.Unit r11 = kotlin.Unit.INSTANCE
                        return r11
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$special$$inlined$mapNotNull$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
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
        CoroutineTracingKt.launchTraced$default(this.coroutineScope, null, null, new CastVolumeSliderViewModel$onValueChanged$1(f, this, null), 7);
    }

    @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel
    public final void toggleMuted(SliderState sliderState) {
    }

    @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel
    public final void onValueChangeFinished() {
    }
}
