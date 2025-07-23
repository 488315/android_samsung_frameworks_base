package com.android.systemui.util.sensors;

import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.sensors.ThresholdSensor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ProximityCheck implements Runnable {
    private final DelayableExecutor mDelayableExecutor;
    private final ThresholdSensor.Listener mListener;
    private final ProximitySensor mSensor;
    private List<Consumer<Boolean>> mCallbacks = new ArrayList();
    private final AtomicBoolean mRegistered = new AtomicBoolean();

    public ProximityCheck(ProximitySensor proximitySensor, DelayableExecutor delayableExecutor) {
        this.mSensor = proximitySensor;
        proximitySensor.setTag("prox_check");
        this.mDelayableExecutor = delayableExecutor;
        this.mListener = new ThresholdSensor.Listener() { // from class: com.android.systemui.util.sensors.ProximityCheck$$ExternalSyntheticLambda1
            @Override // com.android.systemui.util.sensors.ThresholdSensor.Listener
            public final void onThresholdCrossed(ThresholdSensorEvent thresholdSensorEvent) {
                ProximityCheck.this.onProximityEvent(thresholdSensorEvent);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onProximityEvent$0(ThresholdSensorEvent thresholdSensorEvent, Consumer consumer) {
        consumer.accept(thresholdSensorEvent == null ? null : Boolean.valueOf(thresholdSensorEvent.getBelow()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onProximityEvent(final ThresholdSensorEvent thresholdSensorEvent) {
        List<Consumer<Boolean>> list = this.mCallbacks;
        this.mCallbacks = new ArrayList();
        unregister();
        list.forEach(new Consumer() { // from class: com.android.systemui.util.sensors.ProximityCheck$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ProximityCheck.lambda$onProximityEvent$0(ThresholdSensorEvent.this, (Consumer) obj);
            }
        });
    }

    private void unregister() {
        this.mSensor.unregister(this.mListener);
        this.mRegistered.set(false);
    }

    public void check(long j, Consumer<Boolean> consumer) {
        if (!this.mSensor.isLoaded()) {
            consumer.accept(null);
            return;
        }
        this.mCallbacks.add(consumer);
        if (this.mRegistered.getAndSet(true)) {
            return;
        }
        this.mSensor.register(this.mListener);
        this.mDelayableExecutor.executeDelayed(this, j);
    }

    public void destroy() {
        this.mSensor.destroy();
    }

    @Override // java.lang.Runnable
    public void run() {
        unregister();
        onProximityEvent(null);
    }

    public void setTag(String str) {
        this.mSensor.setTag(str);
    }
}
