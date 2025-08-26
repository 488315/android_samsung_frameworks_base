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
import com.android.wm.shell.R;

/* loaded from: classes3.dex */
public class CustomWheelView extends View {
    public final float[] mIntervals;
    public final Paint mPaint;
    public final int mSplitOrientation;

    public CustomWheelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.DividerView, 0, 0);
        try {
            int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 5);
            int dimensionPixelSize2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(1, 5);
            int dimensionPixelSize3 = typedArrayObtainStyledAttributes.getDimensionPixelSize(2, 3);
            this.mSplitOrientation = typedArrayObtainStyledAttributes.getInt(4, 0);
            typedArrayObtainStyledAttributes.recycle();
            float f = dimensionPixelSize2;
            float f2 = dimensionPixelSize;
            float f3 = dimensionPixelSize3;
            this.mIntervals = new float[]{f, f2, f3};
            Paint paint = new Paint();
            this.mPaint = paint;
            Path path = new Path();
            RectF rectF = new RectF(0.0f, 0.0f, f, f3);
            float f4 = f / 2.0f;
            path.addRoundRect(rectF, f4, f4, Path.Direction.CW);
            paint.setPathEffect(new PathDashPathEffect(path, f2, 0.0f, PathDashPathEffect.Style.MORPH));
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override // android.view.View
    public final void invalidate() {
        super.invalidate();
        startAnimation(AnimationUtils.loadAnimation(((View) this).mContext, com.android.systemui.R.anim.fadein));
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mPaint.setShader(new LinearGradient(getWidth() / 2.0f, 0.0f, getWidth() / 2.0f, getHeight(), new int[]{getResources().getColor(com.android.systemui.R.color.flex_scroll_wheel_start), getResources().getColor(com.android.systemui.R.color.flex_scroll_wheel_center), getResources().getColor(com.android.systemui.R.color.flex_scroll_wheel_end)}, (float[]) null, Shader.TileMode.CLAMP));
        if (this.mSplitOrientation == 0) {
            float height = (getHeight() + this.mIntervals[2]) / 2.0f;
            canvas.drawLine(0.0f, height, getWidth(), height, this.mPaint);
        } else {
            float width = (getWidth() + this.mIntervals[2]) / 2.0f;
            canvas.drawLine(width, 0.0f, width, getHeight(), this.mPaint);
        }
    }

    public CustomWheelView(Context context) {
        this(context, null);
    }
}
