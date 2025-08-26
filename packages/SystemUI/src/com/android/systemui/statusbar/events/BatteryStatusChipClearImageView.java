package com.android.systemui.statusbar.events;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class BatteryStatusChipClearImageView extends ImageView {
    public final Paint clearPaint;
    public boolean isClear;
    public final Paint paint;

    public BatteryStatusChipClearImageView(Context context) {
        this(context, null, 0, 6, null);
    }

    public final void draw(Canvas canvas, Paint paint) {
        canvas.save();
        Drawable drawable = getDrawable();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas2.getWidth(), canvas2.getHeight());
        drawable.draw(canvas2);
        float measuredWidth = getMeasuredWidth() / getDrawable().getIntrinsicWidth();
        float measuredHeight = getMeasuredHeight() / getDrawable().getIntrinsicHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(measuredWidth, measuredHeight);
        canvas.drawBitmap(bitmapCreateBitmap, matrix, paint);
        canvas.restore();
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onDraw(Canvas canvas) {
        if (this.isClear) {
            draw(canvas, this.clearPaint);
        }
        draw(canvas, this.paint);
    }

    @Override // android.view.View
    public final void setAlpha(float f) {
        this.paint.setAlpha((int) (255 * f));
        invalidate();
    }

    public BatteryStatusChipClearImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ BatteryStatusChipClearImageView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public BatteryStatusChipClearImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        paint.setColor(-16777216);
        this.clearPaint = paint;
        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        this.paint = paint2;
    }
}
