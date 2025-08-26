package com.android.systemui.volume.dialog.sliders.data.repository;

import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class VolumeDialogSliderTouchEventsRepository {
    public final StateFlowImpl mutableSliderTouchEvents;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 sliderTouchEvent;

    public VolumeDialogSliderTouchEventsRepository() {
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.mutableSliderTouchEvents = stateFlowImplMutableStateFlow;
        this.sliderTouchEvent = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(stateFlowImplMutableStateFlow);
    }
}
