package com.android.server;

public interface CommonTuningParamters {
    int getCntThreshold();

    int getRssiThreshold();

    int getSnrThreshold();

    void setCntThreshold(int i);

    void setRssiThreshold(int i);

    void setSnrThreshold(int i);
}
