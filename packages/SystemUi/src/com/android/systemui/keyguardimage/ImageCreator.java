package com.android.systemui.keyguardimage;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.View;
import com.android.systemui.keyguardimage.ImageOptionCreator;
import com.samsung.android.nexus.video.VideoPlayer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface ImageCreator {
    static Bitmap getViewImage(View view, ImageOptionCreator.ImageOption imageOption) {
        view.measure(View.MeasureSpec.makeMeasureSpec(imageOption.realWidth, VideoPlayer.MEDIA_ERROR_SYSTEM), View.MeasureSpec.makeMeasureSpec(imageOption.realHeight, VideoPlayer.MEDIA_ERROR_SYSTEM));
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        if (measuredWidth == 0 || measuredHeight == 0) {
            return null;
        }
        view.layout(0, 0, measuredWidth, measuredHeight);
        Bitmap createBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(createBitmap));
        float f = imageOption.scale;
        if (f <= 0.0f || f >= 1.0f) {
            return createBitmap;
        }
        int i = (int) (measuredWidth * f);
        int i2 = (int) (measuredHeight * f);
        if (i == 0 || i2 == 0) {
            return null;
        }
        return Bitmap.createScaledBitmap(createBitmap, i, i2, true);
    }

    Bitmap createImage(ImageOptionCreator.ImageOption imageOption, Point point);
}
