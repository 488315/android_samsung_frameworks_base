package com.android.systemui.util.sensors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface ThresholdSensor {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Listener {
        void onThresholdCrossed(ThresholdSensorEvent thresholdSensorEvent);
    }

    boolean isLoaded();

    void pause();

    void register(Listener listener);

    void resume();

    void setDelay();

    void setTag(String str);

    void unregister(Listener listener);
}
