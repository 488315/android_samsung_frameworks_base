package com.android.systemui.volume.dialog.sliders.ui.viewmodel;

import android.content.Context;
import android.view.animation.PathInterpolator;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInputEventsInteractor;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogOverscrollViewModel {
    public final VolumeDialogSliderInputEventsInteractor inputEventsInteractor;
    public final float maxDeviation;
    public final PathInterpolator offsetInterpolator = new PathInterpolator(0.15f, 0.0f, 0.2f, 1.0f);
    public final ChannelFlowTransformLatest overscrollEvent;
    public final StateFlowImpl sliderValue;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OverscrollEventModel {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.sliderValue = MutableStateFlow;
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(MutableStateFlow);
        this.overscrollEvent = FlowKt.transformLatest(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L59
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel$Slider r5 = (com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel.Slider) r5
                        float r6 = r5.value
                        float r2 = r5.min
                        int r2 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
                        if (r2 != 0) goto L3f
                        r5 = 1065353216(0x3f800000, float:1.0)
                        goto L49
                    L3f:
                        float r5 = r5.max
                        int r5 = (r6 > r5 ? 1 : (r6 == r5 ? 0 : -1))
                        if (r5 != 0) goto L48
                        r5 = -1082130432(0xffffffffbf800000, float:-1.0)
                        goto L49
                    L48:
                        r5 = 0
                    L49:
                        java.lang.Float r6 = new java.lang.Float
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L59
                        return r1
                    L59:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new VolumeDialogOverscrollViewModel$special$$inlined$flatMapLatest$1(null, this));
    }
}
