package com.android.systemui.haptics.slider;

import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class SliderTracker {
    public SliderState currentState;
    public final SliderEventProducer eventProducer;
    public Job job;
    public final CoroutineScope scope;
    public final SliderStateListener sliderListener;

    public /* synthetic */ SliderTracker(CoroutineScope coroutineScope, SliderStateListener sliderStateListener, SliderEventProducer sliderEventProducer, DefaultConstructorMarker defaultConstructorMarker) {
        this(coroutineScope, sliderStateListener, sliderEventProducer);
    }

    public abstract void executeOnState(SliderState sliderState);

    public abstract Unit iterateState(SliderEvent sliderEvent);

    private SliderTracker(CoroutineScope coroutineScope, SliderStateListener sliderStateListener, SliderEventProducer sliderEventProducer) {
        this.scope = coroutineScope;
        this.sliderListener = sliderStateListener;
        this.eventProducer = sliderEventProducer;
        this.currentState = SliderState.IDLE;
    }
}
