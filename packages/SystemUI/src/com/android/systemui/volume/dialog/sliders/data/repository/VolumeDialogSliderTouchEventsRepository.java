package com.android.systemui.volume.dialog.sliders.data.repository;

import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogSliderTouchEventsRepository {
    public final StateFlowImpl mutableSliderTouchEvents;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 sliderTouchEvent;

    public VolumeDialogSliderTouchEventsRepository() {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.mutableSliderTouchEvents = MutableStateFlow;
        this.sliderTouchEvent = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(MutableStateFlow);
    }
}
