package com.android.systemui.haptics.slider;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class SliderTracker {
    public SliderState currentState;
    public final SliderEventProducer eventProducer;
    public StandaloneCoroutine job;
    public final CoroutineScope scope;
    public final SliderStateListener sliderListener;

    public /* synthetic */ SliderTracker(CoroutineScope coroutineScope, SliderStateListener sliderStateListener, SliderEventProducer sliderEventProducer, DefaultConstructorMarker defaultConstructorMarker) {
        this(coroutineScope, sliderStateListener, sliderEventProducer);
    }

    public abstract void executeOnState(SliderState sliderState);

    public abstract Unit iterateState(SliderEvent sliderEvent);

    public final void startTracking() {
        this.job = CoroutineTracingKt.launchTraced$default(this.scope, null, null, new SliderTracker$startTracking$1(this, null), 7);
    }

    private SliderTracker(CoroutineScope coroutineScope, SliderStateListener sliderStateListener, SliderEventProducer sliderEventProducer) {
        this.scope = coroutineScope;
        this.sliderListener = sliderStateListener;
        this.eventProducer = sliderEventProducer;
        this.currentState = SliderState.IDLE;
    }
}
