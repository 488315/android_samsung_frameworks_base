package com.android.wm.shell.controlpanel.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.android.wm.shell.C3767R;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class CustomWheelView extends View {
    public final float[] mIntervals;
    public final Paint mPaint;
    public final Path mShape;
    public final int mSplitOrientation;

    public CustomWheelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C3767R.styleable.DividerView, 0, 0);
        try {
            int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(0, 5);
            int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(1, 5);
            int dimensionPixelSize3 = obtainStyledAttributes.getDimensionPixelSize(2, 3);
            this.mSplitOrientation = obtainStyledAttributes.getInt(4, 0);
            obtainStyledAttributes.recycle();
            float f = dimensionPixelSize2;
            float f2 = dimensionPixelSize;
            float f3 = dimensionPixelSize3;
            this.mIntervals = new float[]{f, f2, f3};
            Paint paint = new Paint();
            this.mPaint = paint;
            Path path = new Path();
            this.mShape = path;
            float f4 = dimensionPixelSize2 / 2;
            path.addRoundRect(new RectF(0.0f, 0.0f, f, f3), f4, f4, Path.Direction.CW);
            paint.setPathEffect(new PathDashPathEffect(path, f2, 0.0f, PathDashPathEffect.Style.MORPH));
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override // android.view.View
    public final void invalidate() {
        super.invalidate();
        startAnimation(AnimationUtils.loadAnimation(((View) this).mContext, R.anim.fadein));
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mPaint.setShader(new LinearGradient(getWidth() / 2, 0.0f, getWidth() / 2, getHeight(), new int[]{getResources().getColor(R.color.flex_scroll_wheel_start), getResources().getColor(R.color.flex_scroll_wheel_center), getResources().getColor(R.color.flex_scroll_wheel_end)}, (float[]) null, Shader.TileMode.CLAMP));
        if (this.mSplitOrientation == 0) {
            float height = (getHeight() + this.mIntervals[2]) / 2.0f;
            canvas.drawLine(0.0f, height, getWidth(), height, this.mPaint);
        } else {
            float width = (getWidth() + this.mIntervals[2]) / 2.0f;
            canvas.drawLine(width, 0.0f, width, getHeight(), this.mPaint);
        }
    }

    public final void onFadeOutAnimation() {
        startAnimation(AnimationUtils.loadAnimation(((View) this).mContext, R.anim.fadeout));
    }

    public final void updateScrollView(int i) {
        this.mPaint.setPathEffect(new PathDashPathEffect(this.mShape, this.mIntervals[1], i, PathDashPathEffect.Style.MORPH));
        super.invalidate();
    }

    public CustomWheelView(Context context) {
        this(context, null);
    }
}
