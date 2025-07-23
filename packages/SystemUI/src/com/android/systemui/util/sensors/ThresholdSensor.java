package com.android.systemui.util.sensors;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface ThresholdSensor {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Listener {
        void onThresholdCrossed(ThresholdSensorEvent thresholdSensorEvent);
    }

    String getName();

    String getType();

    boolean isLoaded();

    void pause();

    void register(Listener listener);

    void resume();

    void setDelay(int i);

    void setTag(String str);

    void unregister(Listener listener);
}
