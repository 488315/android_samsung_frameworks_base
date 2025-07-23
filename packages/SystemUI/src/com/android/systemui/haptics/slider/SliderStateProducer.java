package com.android.systemui.haptics.slider;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SliderStateProducer implements SliderEventProducer {
    public final StateFlowImpl _currentEvent = StateFlowKt.MutableStateFlow(new SliderEvent(SliderEventType.NOTHING, 0.0f));

    public final void onProgressChanged(float f, boolean z) {
        this._currentEvent.updateState(null, new SliderEvent(z ? SliderEventType.PROGRESS_CHANGE_BY_USER : SliderEventType.PROGRESS_CHANGE_BY_PROGRAM, f));
    }

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
