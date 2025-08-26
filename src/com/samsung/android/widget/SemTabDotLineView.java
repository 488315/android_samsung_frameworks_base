package com.samsung.android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import com.android.internal.R;

/* loaded from: classes6.dex */
public class SemTabDotLineView extends View {
    private static final float CIRCLE_INTERVAL = 2.5f;
    private static final float DIAMETER_SIZE = 2.5f;
    private static final int SEM_TAB_ANIM_PRESS_DURATION = 0;
    private int mDiameter;
    public boolean mDrawDot;
    private Paint mPaint;

    public void onReleased() {
    }

    public SemTabDotLineView(Context context) {
        this(context, null);
    }

    public SemTabDotLineView(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
        super(context, attributeSet);
        this.mDiameter = 1;
        this.mDrawDot = true;
        this.mDiameter = (int) TypedValue.applyDimension(1, 2.5f, context.getResources().getDisplayMetrics());
        int color = context.getResources().getColor(R.color.sem_maintab_indicator_color, context.getTheme());
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(color);
        this.mPaint.setFlags(1);
    }

    public void setDrawState(boolean z) {
        this.mDrawDot = z;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mDrawDot && (getBackground() instanceof ColorDrawable)) {
            if (isSelected() || isPressed()) {
                int width = getWidth();
                float height = getHeight() / 2.0f;
                int i = this.mDiameter;
                float f = i / 2.0f;
                canvas.drawRoundRect(0.0f, height - f, width, height + f, i, i, this.mPaint);
            }
        }
    }

    @Override // android.view.View
    public void setPressed(boolean z) {
        super.setPressed(z);
        if (z) {
            this.mDrawDot = true;
            onPressed();
        } else {
            onReleased();
        }
    }

    public void onPressed() {
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.setFillAfter(true);
        if (!isSelected()) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(0L);
            alphaAnimation.setFillAfter(true);
            animationSet.addAnimation(alphaAnimation);
        }
        startAnimation(animationSet);
    }
}
