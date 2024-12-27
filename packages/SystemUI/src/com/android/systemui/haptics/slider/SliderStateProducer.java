package com.android.systemui.haptics.slider;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class SliderStateProducer implements SliderEventProducer {
    public final StateFlowImpl _currentEvent = StateFlowKt.MutableStateFlow(new SliderEvent(SliderEventType.NOTHING, 0.0f));

    public final void onStartTracking(boolean z) {
        StateFlowImpl stateFlowImpl;
        Object value;
        SliderEventType sliderEventType = z ? SliderEventType.STARTED_TRACKING_TOUCH : SliderEventType.STARTED_TRACKING_PROGRAM;
        do {
            stateFlowImpl = this._currentEvent;
            value = stateFlowImpl.getValue();
        } while (!stateFlowImpl.compareAndSet(value, new SliderEvent(sliderEventType, ((SliderEvent) value).currentProgress)));
    }

    public final void onStopTracking(boolean z) {
        StateFlowImpl stateFlowImpl;
        Object value;
        SliderEventType sliderEventType = z ? SliderEventType.STOPPED_TRACKING_TOUCH : SliderEventType.STOPPED_TRACKING_PROGRAM;
        do {
            stateFlowImpl = this._currentEvent;
            value = stateFlowImpl.getValue();
        } while (!stateFlowImpl.compareAndSet(value, new SliderEvent(sliderEventType, ((SliderEvent) value).currentProgress)));
    }
}
