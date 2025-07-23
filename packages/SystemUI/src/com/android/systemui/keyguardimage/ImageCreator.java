package com.android.systemui.keyguardimage;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.view.View;
import com.android.systemui.keyguardimage.ImageOptionCreator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface ImageCreator {
    static Bitmap getViewImage(View view, ImageOptionCreator.ImageOption imageOption, boolean z) {
        view.measure(View.MeasureSpec.makeMeasureSpec(imageOption.realWidth, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(imageOption.realHeight, Integer.MIN_VALUE));
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        if (measuredWidth == 0 || measuredHeight == 0) {
            return null;
        }
        view.layout(0, 0, measuredWidth, measuredHeight);
        Bitmap createBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        if (z) {
            Path path = new Path();
            float f = measuredWidth / 2.0f;
            path.addCircle(f, measuredHeight / 2.0f, f, Path.Direction.CCW);
            canvas.clipPath(path);
        }
        view.draw(canvas);
        float f2 = imageOption.scale;
        return (f2 <= 0.0f || f2 >= 1.0f) ? createBitmap : Bitmap.createScaledBitmap(createBitmap, (int) (measuredWidth * f2), (int) (measuredHeight * f2), true);
    }

    Bitmap createImage(ImageOptionCreator.ImageOption imageOption, Point point);
}
