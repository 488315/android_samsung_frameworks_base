package com.android.server.power.shutdown;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.ImageView;

public final class ShutdownAnimatedImageView extends ImageView {
    public Bitmap bitmap;
    public Canvas canvas;
    public PlayerInterface.ViewSizeListener listener;

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        PlayerInterface.ViewSizeListener viewSizeListener = this.listener;
        if (viewSizeListener != null) {
            viewSizeListener.onSizeChanged(i, i2, i3, i4);
        }
    }
}
