package com.android.launcher3.icons;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.ColorDrawable;
import androidx.core.graphics.ColorUtils;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class PlaceHolderIconDrawable extends FastBitmapDrawable {
    public final Path mProgressPath;

    public PlaceHolderIconDrawable(BitmapInfo bitmapInfo, Context context) {
        super(bitmapInfo);
        AdaptiveIconDrawable adaptiveIconDrawable = new AdaptiveIconDrawable(new ColorDrawable(-16777216), new ColorDrawable(-16777216));
        adaptiveIconDrawable.setBounds(0, 0, 100, 100);
        this.mProgressPath = new Path(adaptiveIconDrawable.getIconMask());
        Paint paint = this.mPaint;
        GraphicsUtils$$ExternalSyntheticLambda0 graphicsUtils$$ExternalSyntheticLambda0 = GraphicsUtils.sOnNewBitmapRunnable;
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
