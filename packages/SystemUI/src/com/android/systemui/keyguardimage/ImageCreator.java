package com.android.systemui.keyguardimage;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.view.View;
import com.android.systemui.keyguardimage.ImageOptionCreator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            float f = measuredWidth / 2;
            path.addCircle(f, measuredHeight / 2, f, Path.Direction.CCW);
            canvas.clipPath(path);
        }
        view.draw(canvas);
        float f2 = imageOption.scale;
        return (f2 <= 0.0f || f2 >= 1.0f) ? createBitmap : Bitmap.createScaledBitmap(createBitmap, (int) (measuredWidth * f2), (int) (measuredHeight * f2), true);
    }

    Bitmap createImage(ImageOptionCreator.ImageOption imageOption, Point point);
}
