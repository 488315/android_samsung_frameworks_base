package com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel;

import android.content.Context;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1;
import com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CastVolumeSliderViewModel implements SliderViewModel {
    public final Context context;
    public final CoroutineScope coroutineScope;
    public final MediaDeviceSessionInteractor mediaDeviceSessionInteractor;
    public final MediaDeviceSession session;
    public final ReadonlyStateFlow slider;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        CastVolumeSliderViewModel create(MediaDeviceSession mediaDeviceSession, CoroutineScope coroutineScope);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class State implements SliderState {
        public final int a11yStep;
        public final Icon icon;
        public final boolean isEnabled;
        public final String label;
        public final float value;
        public final ClosedFloatingPointRange valueRange;

        public State(float f, ClosedFloatingPointRange closedFloatingPointRange, Icon icon, String str, boolean z, int i) {
            this.value = f;
            this.valueRange = closedFloatingPointRange;
            this.icon = icon;
            this.label = str;
            this.isEnabled = z;
            this.a11yStep = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof State)) {
                return false;
            }
            State state = (State) obj;
            return Float.compare(this.value, state.value) == 0 && Intrinsics.areEqual(this.valueRange, state.valueRange) && Intrinsics.areEqual(this.icon, state.icon) && Intrinsics.areEqual(this.label, state.label) && this.isEnabled == state.isEnabled && this.a11yStep == state.a11yStep;
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
        public final int getA11yStep() {
            return this.a11yStep;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final String getDisabledMessage() {
            return null;
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
            return Integer.hashCode(this.a11yStep) + TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((this.icon.hashCode() + ((this.valueRange.hashCode() + (Float.hashCode(this.value) * 31)) * 31)) * 31, 31, this.label), 31, this.isEnabled);
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
            return "State(value=" + this.value + ", valueRange=" + this.valueRange + ", icon=" + this.icon + ", label=" + this.label + ", isEnabled=" + this.isEnabled + ", a11yStep=" + this.a11yStep + ")";
        }
    }

    public CastVolumeSliderViewModel(MediaDeviceSession mediaDeviceSession, CoroutineScope coroutineScope, Context context, MediaDeviceSessionInteractor mediaDeviceSessionInteractor) {
        this.session = mediaDeviceSession;
        this.coroutineScope = coroutineScope;
        this.context = context;
        this.mediaDeviceSessionInteractor = mediaDeviceSessionInteractor;
        final MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1 playbackInfo = mediaDeviceSessionInteractor.playbackInfo(mediaDeviceSession);
        Flow flow = new Flow() { // from class: com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$special$$inlined$mapNotNull$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$special$$inlined$mapNotNull$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CastVolumeSliderViewModel this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r18, kotlin.coroutines.Continuation r19) {
                    /*
                        r17 = this;
                        r0 = r17
                        r1 = r19
                        boolean r2 = r1 instanceof com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$special$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1
                        if (r2 == 0) goto L17
                        r2 = r1
                        com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$special$$inlined$mapNotNull$1$2$1 r2 = (com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$special$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1) r2
                        int r3 = r2.label
                        r4 = -2147483648(0xffffffff80000000, float:-0.0)
                        r5 = r3 & r4
                        if (r5 == 0) goto L17
                        int r3 = r3 - r4
                        r2.label = r3
                        goto L1c
                    L17:
                        com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$special$$inlined$mapNotNull$1$2$1 r2 = new com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$special$$inlined$mapNotNull$1$2$1
                        r2.<init>(r1)
                    L1c:
                        java.lang.Object r1 = r2.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r3 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r4 = r2.label
                        r5 = 1
                        if (r4 == 0) goto L33
                        if (r4 != r5) goto L2b
                        kotlin.ResultKt.throwOnFailure(r1)
                        goto L84
                    L2b:
                        java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                        java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                        r0.<init>(r1)
                        throw r0
                    L33:
                        kotlin.ResultKt.throwOnFailure(r1)
                        r1 = r18
                        android.media.session.MediaController$PlaybackInfo r1 = (android.media.session.MediaController.PlaybackInfo) r1
                        r4 = 0
                        if (r1 == 0) goto L77
                        com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel r6 = r0.this$0
                        r6.getClass()
                        kotlin.ranges.IntRange r7 = new kotlin.ranges.IntRange
                        int r8 = r1.getMaxVolume()
                        r9 = 0
                        r7.<init>(r9, r8)
                        com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$State r8 = new com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.CastVolumeSliderViewModel$State
                        int r1 = r1.getCurrentVolume()
                        float r11 = (float) r1
                        int r1 = r7.first
                        float r1 = (float) r1
                        int r7 = r7.last
                        float r7 = (float) r7
                        kotlin.ranges.ClosedFloatRange r12 = new kotlin.ranges.ClosedFloatRange
                        r12.<init>(r1, r7)
                        com.android.systemui.common.shared.model.Icon$Resource r13 = new com.android.systemui.common.shared.model.Icon$Resource
                        r1 = 2131232847(0x7f08084f, float:1.8081815E38)
                        r13.<init>(r1, r4)
                        android.content.Context r1 = r6.context
                        r4 = 2131954601(0x7f130ba9, float:1.9545706E38)
                        java.lang.String r14 = r1.getString(r4)
                        r15 = 1
                        r16 = 1
                        r10 = r8
                        r10.<init>(r11, r12, r13, r14, r15, r16)
                        r4 = r8
                    L77:
                        if (r4 == 0) goto L84
                        r2.label = r5
                        kotlinx.coroutines.flow.FlowCollector r0 = r0.$this_unsafeFlow
                        java.lang.Object r0 = r0.emit(r4, r2)
                        if (r0 != r3) goto L84
                        return r3
                    L84:
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
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
    public final void onValueChanged(SliderState sliderState, float f) {
        BuildersKt.launch$default(this.coroutineScope, null, null, new CastVolumeSliderViewModel$onValueChanged$1(this, f, null), 3);
    }

    @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel
    public final void onValueChangeFinished() {
    }

    @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel
    public final void toggleMuted(SliderState sliderState) {
    }
}
