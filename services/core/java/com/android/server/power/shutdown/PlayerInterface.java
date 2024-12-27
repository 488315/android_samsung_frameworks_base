package com.android.server.power.shutdown;

public interface PlayerInterface {

    public interface ViewSizeListener {
        void onSizeChanged(int i, int i2, int i3, int i4);
    }

    boolean isPlaying();

    void prepare();

    void start();
}
