package com.android.systemui.volume.dialog.sliders.ui.viewmodel;

import android.content.Context;
import android.view.animation.PathInterpolator;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInputEventsInteractor;
import com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes3.dex */
public final class VolumeDialogOverscrollViewModel {
    public final VolumeDialogSliderInputEventsInteractor inputEventsInteractor;
    public final float maxDeviation;
    public final PathInterpolator offsetInterpolator = new PathInterpolator(0.15f, 0.0f, 0.2f, 1.0f);
    public final ChannelFlowTransformLatest overscrollEvent;
    public final StateFlowImpl sliderValue;

    public interface OverscrollEventModel {

        public final class Animate implements OverscrollEventModel {
            public final float targetOffsetPx;

            public Animate(float f) {
                this.targetOffsetPx = f;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Animate) && Float.compare(this.targetOffsetPx, ((Animate) obj).targetOffsetPx) == 0;
            }

            public final int hashCode() {
                return Float.hashCode(this.targetOffsetPx);
            }

            public final String toString() {
                return "Animate(targetOffsetPx=" + this.targetOffsetPx + ")";
            }
        }

        public final class Move implements OverscrollEventModel {
            public final float touchOffsetPx;

            public Move(float f) {
                this.touchOffsetPx = f;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Move) && Float.compare(this.touchOffsetPx, ((Move) obj).touchOffsetPx) == 0;
            }

            public final int hashCode() {
                return Float.hashCode(this.touchOffsetPx);
            }

            public final String toString() {
                return "Move(touchOffsetPx=" + this.touchOffsetPx + ")";
            }
        }
    }

    public final class Slider {
        public final float max;
        public final float min;
        public final float value;

        public Slider(float f, float f2, float f3) {
            this.value = f;
            this.min = f2;
            this.max = f3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Slider)) {
                return false;
            }
            Slider slider = (Slider) obj;
            return Float.compare(this.value, slider.value) == 0 && Float.compare(this.min, slider.min) == 0 && Float.compare(this.max, slider.max) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.max) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.min, Float.hashCode(this.value) * 31, 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Slider(value=");
            sb.append(this.value);
            sb.append(", min=");
            sb.append(this.min);
            sb.append(", max=");
            return DpCornerSize$$ExternalSyntheticOutline0.m(this.max, ")", sb);
        }
    }

    public VolumeDialogOverscrollViewModel(Context context, VolumeDialogSliderInputEventsInteractor volumeDialogSliderInputEventsInteractor) {
        this.inputEventsInteractor = volumeDialogSliderInputEventsInteractor;
        this.maxDeviation = context.getResources().getDimensionPixelSize(R.dimen.volume_dialog_slider_max_deviation);
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.sliderValue = stateFlowImplMutableStateFlow;
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(stateFlowImplMutableStateFlow);
        this.overscrollEvent = FlowKt.transformLatest(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        VolumeDialogOverscrollViewModel.Slider slider = (VolumeDialogOverscrollViewModel.Slider) obj;
                        float f = slider.value;
                        Float f2 = new Float(f == slider.min ? 1.0f : f == slider.max ? -1.0f : 0.0f);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(f2, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), new VolumeDialogOverscrollViewModel$special$$inlined$flatMapLatest$1(null, this));
    }
}
