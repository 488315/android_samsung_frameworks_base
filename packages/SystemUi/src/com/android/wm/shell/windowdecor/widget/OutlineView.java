package com.android.wm.shell.windowdecor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.SystemProperties;
import android.util.AttributeSet;
import android.view.View;
import com.airbnb.lottie.animation.LPaint;
import com.android.systemui.R;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class OutlineView extends View {
    public static final boolean DEBUG = SystemProperties.getBoolean("persist.debug.caption.outline", false);
    public static final boolean SAFE_DEBUG = CoreRune.SAFE_DEBUG;
    public float mAlpha;
    public int mCaptionHeight;
    public final LPaint mClearPaint;
    public int mFillColor;
    public final Paint mFillPaint;
    public boolean mIsClosing;
    public boolean mIsOpening;
    public int mRadius;
    public int mRadiusForShadow;
    public int mStrokeColor;
    public final Paint mStrokePaint;
    public final Rect mTmpTransparentRect;

    public OutlineView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mStrokeColor = 0;
        this.mFillColor = 0;
        Paint paint = new Paint();
        this.mStrokePaint = paint;
        Paint paint2 = new Paint();
        this.mFillPaint = paint2;
        LPaint lPaint = new LPaint(PorterDuff.Mode.CLEAR);
        this.mClearPaint = lPaint;
        this.mTmpTransparentRect = new Rect();
        this.mAlpha = 1.0f;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        lPaint.setStyle(Paint.Style.STROKE);
        lPaint.setStrokeJoin(Paint.Join.ROUND);
        lPaint.setStrokeCap(Paint.Cap.ROUND);
        lPaint.setAntiAlias(true);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setStrokeJoin(Paint.Join.ROUND);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        paint2.setAntiAlias(true);
        setBackgroundResource(R.drawable.decor_freeform_background_for_shadow);
        if (DEBUG) {
            setBackgroundColor(855703296);
        }
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mAlpha < 1.0f) {
            Path path = new Path();
            float width = getWidth();
            float height = getHeight();
            int i = this.mRadiusForShadow;
            path.addRoundRect(0.0f, 0.0f, width, height, i, i, Path.Direction.CW);
            canvas.save();
            canvas.clipPath(path);
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            canvas.restore();
        }
        updatePaintColor(this.mFillColor, this.mFillPaint);
        updatePaintColor(this.mStrokeColor, this.mStrokePaint);
        int strokeWidth = (int) this.mStrokePaint.getStrokeWidth();
        int i2 = strokeWidth / 2;
        float f = i2;
        float width2 = getWidth() - i2;
        float height2 = getHeight() - i2;
        int i3 = this.mRadius;
        canvas.drawRoundRect(f, f, width2, height2, i3, i3, this.mStrokePaint);
        int i4 = !this.mIsOpening && !this.mIsClosing && (this.mAlpha > 1.0f ? 1 : (this.mAlpha == 1.0f ? 0 : -1)) == 0 ? strokeWidth + 1 : strokeWidth;
        this.mTmpTransparentRect.set(0, 0, getWidth(), getHeight());
        this.mTmpTransparentRect.inset(i4, i4);
        canvas.save();
        canvas.clipRect(this.mTmpTransparentRect);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        canvas.restore();
        float f2 = strokeWidth;
        canvas.drawRect(f2, f2, getWidth() - strokeWidth, this.mCaptionHeight + i4, this.mFillPaint);
    }

    public final void updatePaintColor(int i, Paint paint) {
        int argb = Color.argb(Math.round(Color.alpha(i) * this.mAlpha), Color.red(i), Color.green(i), Color.blue(i));
        if (paint.getColor() != argb) {
            paint.setColor(argb);
        }
    }
}
