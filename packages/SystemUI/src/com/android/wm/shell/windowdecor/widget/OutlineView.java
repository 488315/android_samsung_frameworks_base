package com.android.wm.shell.windowdecor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.R;
import com.samsung.android.util.SemViewUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class OutlineView extends View {
    public final Paint mBackgroundPaint;
    public float mCaptionHeight;
    public boolean mIsClosing;
    public boolean mIsOpening;
    public int mRadius;
    public final Paint mStrokePaint;

    public OutlineView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint();
        this.mStrokePaint = paint;
        Paint paint2 = new Paint();
        this.mBackgroundPaint = paint2;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setStrokeJoin(Paint.Join.MITER);
        paint2.setStrokeCap(Paint.Cap.BUTT);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawPath(SemViewUtils.getSmoothCornerRectPath(this.mRadius, 0.0f, 0.0f, getWidth(), getHeight()), this.mStrokePaint);
        if (this.mCaptionHeight != 0.0f) {
            canvas.drawRect(0.0f, 0.0f, getWidth(), this.mCaptionHeight, this.mBackgroundPaint);
        }
    }

    public final void setOutlineInfo(int i, int i2, int i3, boolean z) {
        this.mRadius = i;
        this.mStrokePaint.setColor(((View) this).mContext.getColor(z ? R.color.mw_caption_outline_stroke_color_dark : R.color.mw_caption_outline_stroke_color_light));
        this.mStrokePaint.setStrokeWidth(i2);
        this.mCaptionHeight = i3;
        this.mBackgroundPaint.setColor(((View) this).mContext.getColor(z ? R.color.mw_caption_background_color_dark : R.color.mw_caption_background_color_light));
    }
}
