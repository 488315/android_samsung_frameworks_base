package com.android.systemui.util.sensors;

public interface ThresholdSensor {

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
