package com.android.launcher3.icons;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.ColorDrawable;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.PathParser;
import com.android.systemui.R;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PlaceHolderIconDrawable extends FastBitmapDrawable {
    public final Path mProgressPath;

    public PlaceHolderIconDrawable(BitmapInfo bitmapInfo, Context context) {
        super(bitmapInfo);
        Path path;
        GraphicsUtils$$ExternalSyntheticLambda0 graphicsUtils$$ExternalSyntheticLambda0 = GraphicsUtils.sOnNewBitmapRunnable;
        int i = IconProvider.CONFIG_ICON_MASK_RES_ID;
        if (i == 0 || (path = PathParser.createPathFromPathData(context.getString(i))) == null) {
            AdaptiveIconDrawable adaptiveIconDrawable = new AdaptiveIconDrawable(new ColorDrawable(EmergencyPhoneWidget.BG_COLOR), new ColorDrawable(EmergencyPhoneWidget.BG_COLOR));
            adaptiveIconDrawable.setBounds(0, 0, 100, 100);
            path = new Path(adaptiveIconDrawable.getIconMask());
        } else {
            float f = 100;
            if (f != 100.0f) {
                Matrix matrix = new Matrix();
                float f2 = f / 100.0f;
                matrix.setScale(f2, f2);
                path.transform(matrix);
            }
        }
        this.mProgressPath = path;
        Paint paint = this.mPaint;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.loadingIconColor});
        int color = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        paint.setColor(ColorUtils.compositeColors(color, bitmapInfo.color));
    }

    @Override // com.android.launcher3.icons.FastBitmapDrawable
    public final void drawInternal(Canvas canvas, Rect rect) {
        int save = canvas.save();
        canvas.translate(rect.left, rect.top);
        canvas.scale(rect.width() / 100.0f, rect.height() / 100.0f);
        canvas.drawPath(this.mProgressPath, this.mPaint);
        canvas.restoreToCount(save);
    }
}
