package com.android.settingslib.qrcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class QrDecorateView extends View {
    public final int mBackgroundColor;
    public final Paint mBackgroundPaint;
    public final int mCornerColor;
    public RectF mInnerFrame;
    public final float mInnerRadius;
    public Bitmap mMaskBitmap;
    public Canvas mMaskCanvas;
    public RectF mOuterFrame;
    public final float mRadius;
    public final Paint mStrokePaint;
    public final Paint mTransparentPaint;

    public QrDecorateView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        if (this.mMaskCanvas != null && this.mMaskBitmap != null) {
            this.mStrokePaint.setColor(this.mCornerColor);
            this.mMaskCanvas.drawColor(this.mBackgroundColor);
            Canvas canvas2 = this.mMaskCanvas;
            RectF rectF = this.mOuterFrame;
            float f = this.mRadius;
            canvas2.drawRoundRect(rectF, f, f, this.mStrokePaint);
            Canvas canvas3 = this.mMaskCanvas;
            RectF rectF2 = this.mInnerFrame;
            float f2 = this.mInnerRadius;
            canvas3.drawRoundRect(rectF2, f2, f2, this.mTransparentPaint);
            canvas.drawBitmap(this.mMaskBitmap, 0.0f, 0.0f, this.mBackgroundPaint);
        }
        super.onDraw(canvas);
    }

    @Override // android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mMaskBitmap == null) {
            this.mMaskBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            this.mMaskCanvas = new Canvas(this.mMaskBitmap);
        }
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        float applyDimension = TypedValue.applyDimension(1, 264.0f, getResources().getDisplayMetrics()) / 2.0f;
        float applyDimension2 = TypedValue.applyDimension(1, 4.0f, getResources().getDisplayMetrics());
        float f = width;
        float f2 = height;
        this.mOuterFrame = new RectF(f - applyDimension, f2 - applyDimension, f + applyDimension, f2 + applyDimension);
        RectF rectF = this.mOuterFrame;
        this.mInnerFrame = new RectF(rectF.left + applyDimension2, rectF.top + applyDimension2, rectF.right - applyDimension2, rectF.bottom - applyDimension2);
    }

    public QrDecorateView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QrDecorateView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public QrDecorateView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mRadius = TypedValue.applyDimension(1, 16.0f, getResources().getDisplayMetrics());
        this.mInnerRadius = TypedValue.applyDimension(1, 12.0f, getResources().getDisplayMetrics());
        this.mCornerColor = context.getResources().getColor(R.color.qr_corner_line_color);
        context.getResources().getColor(R.color.qr_focused_corner_line_color);
        int color = context.getResources().getColor(R.color.qr_background_color);
        this.mBackgroundColor = color;
        Paint paint = new Paint();
        this.mStrokePaint = paint;
        paint.setAntiAlias(true);
        Paint paint2 = new Paint();
        this.mTransparentPaint = paint2;
        paint2.setAntiAlias(true);
        paint2.setColor(getResources().getColor(android.R.color.transparent));
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Paint paint3 = new Paint();
        this.mBackgroundPaint = paint3;
        paint3.setColor(color);
    }
}
