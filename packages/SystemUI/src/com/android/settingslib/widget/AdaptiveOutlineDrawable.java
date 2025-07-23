package com.android.settingslib.widget;

import android.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.DrawableWrapper;
import androidx.core.graphics.PathParser;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AdaptiveOutlineDrawable extends DrawableWrapper {
    public Bitmap mBitmap;
    public int mInsetPx;
    Paint mOutlinePaint;
    public Path mPath;
    public int mStrokeWidth;
    public int mType;

    public AdaptiveOutlineDrawable(Resources resources, Bitmap bitmap) {
        super(new AdaptiveIconShapeDrawable(resources));
        init(resources, bitmap, 0);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        super.draw(canvas);
        Rect bounds = getBounds();
        int save = canvas.save();
        canvas.scale((bounds.right - bounds.left) / 100.0f, (bounds.bottom - bounds.top) / 100.0f);
        if (this.mType == 0) {
            canvas.drawPath(this.mPath, this.mOutlinePaint);
        } else {
            canvas.drawCircle(50.0f, 50.0f, 48.0f, this.mOutlinePaint);
        }
        canvas.restoreToCount(save);
        Bitmap bitmap = this.mBitmap;
        int i = bounds.left;
        int i2 = this.mInsetPx;
        canvas.drawBitmap(bitmap, i + i2, bounds.top + i2, (Paint) null);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return (this.mInsetPx * 2) + this.mBitmap.getHeight();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return (this.mInsetPx * 2) + this.mBitmap.getWidth();
    }

    public final void init(Resources resources, Bitmap bitmap, int i) {
        this.mType = i;
        getDrawable().setTint(-1);
        this.mPath = new Path(PathParser.createPathFromPathData(resources.getString(R.string.eventTypeAnniversary)));
        this.mStrokeWidth = resources.getDimensionPixelSize(com.android.systemui.R.dimen.adaptive_outline_stroke);
        Paint paint = new Paint();
        this.mOutlinePaint = paint;
        paint.setColor(resources.getColor(i != 1 ? com.android.systemui.R.color.bt_outline_color : com.android.systemui.R.color.advanced_outline_color, null));
        this.mOutlinePaint.setStyle(Paint.Style.STROKE);
        this.mOutlinePaint.setStrokeWidth(this.mStrokeWidth);
        this.mOutlinePaint.setAntiAlias(true);
        this.mInsetPx = resources.getDimensionPixelSize(i != 1 ? com.android.systemui.R.dimen.dashboard_tile_foreground_image_inset : com.android.systemui.R.dimen.advanced_dashboard_tile_foreground_image_inset);
        this.mBitmap = bitmap;
    }

    public AdaptiveOutlineDrawable(Resources resources, Bitmap bitmap, int i) {
        super(new AdaptiveIconShapeDrawable(resources));
        init(resources, bitmap, i);
    }
}
