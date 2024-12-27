package com.android.server.power.shutdown;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface PlayerInterface {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ViewSizeListener {
        void onSizeChanged(int i, int i2, int i3, int i4);
    }

    boolean isPlaying();

    void prepare();

    void start();
}
