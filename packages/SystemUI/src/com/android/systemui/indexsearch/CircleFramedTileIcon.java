package com.android.systemui.indexsearch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.android.systemui.R;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.ScalingDrawableWrapper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CircleFramedTileIcon extends Drawable {
    public static Context mContext;
    public final Bitmap mBitmap;
    public final RectF mDstRect;
    public final float mScale;
    public final int mSize;
    public final Rect mSrcRect;

    public CircleFramedTileIcon(Drawable drawable, int i) {
        int i2;
        this.mSize = i;
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        this.mBitmap = createBitmap;
        Canvas canvas = new Canvas(createBitmap);
        Bitmap tileIconBitmap = QSTileImpl.getTileIconBitmap(drawable, mContext);
        if (drawable instanceof ScalingDrawableWrapper) {
            i2 = (int) ((mContext.getResources().getFloat(R.dimen.qs_non_sec_customtile_icon_resize_ratio) * i) / 2.0f);
        } else {
            i2 = 0;
        }
        int width = tileIconBitmap.getWidth();
        int height = tileIconBitmap.getHeight();
        int min = Math.min(width, height);
        int i3 = ((width - min) / 2) - i2;
        int i4 = ((height - min) / 2) - i2;
        int i5 = min + i2;
        Rect rect = new Rect(i3, i4, i5, i5);
        float f = i;
        RectF rectF = new RectF(0.0f, 0.0f, f, f);
        Path path = new Path();
        path.addArc(rectF, 0.0f, 360.0f);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mContext.getResources().getColor(R.color.qs_tile_round_background_on, mContext.getTheme()));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);
        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setColorFilter(new PorterDuffColorFilter(mContext.getResources().getColor(R.color.qs_tile_icon_on_dim_tint_color, mContext.getTheme()), PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(tileIconBitmap, rect, rectF, paint2);
        this.mScale = 1.0f;
        this.mSrcRect = new Rect(0, 0, i, i);
        this.mDstRect = new RectF(0.0f, 0.0f, f, f);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        float f = this.mScale;
        int i = this.mSize;
        float f2 = (i - (f * i)) / 2.0f;
        this.mDstRect.set(f2, f2, i - f2, i - f2);
        canvas.drawBitmap(this.mBitmap, this.mSrcRect, this.mDstRect, (Paint) null);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.mSize;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.mSize;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
    }
}
