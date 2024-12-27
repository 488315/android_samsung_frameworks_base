package com.android.server.power.shutdown;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.ImageView;
import com.android.server.power.shutdown.PlayerInterface;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
