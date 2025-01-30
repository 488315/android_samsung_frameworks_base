package com.google.android.material.timepicker;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
class ClockHandView extends View {
    public final float centerDotRadius;
    public boolean changedDuringTouch;
    public int circleRadius;
    public double degRad;
    public float downX;
    public float downY;
    public final List listeners;
    public float originalDeg;
    public final Paint paint;
    public ValueAnimator rotationAnimator;
    public final int scaledTouchSlop;
    public final RectF selectorBox;
    public final int selectorRadius;
    public final int selectorStrokeWidth;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnRotateListener {
    }

    public ClockHandView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight() / 2;
        float width = getWidth() / 2;
        float cos = (this.circleRadius * ((float) Math.cos(this.degRad))) + width;
        float f = height;
        float sin = (this.circleRadius * ((float) Math.sin(this.degRad))) + f;
        this.paint.setStrokeWidth(0.0f);
        canvas.drawCircle(cos, sin, this.selectorRadius, this.paint);
        double sin2 = Math.sin(this.degRad);
        double cos2 = Math.cos(this.degRad);
        this.paint.setStrokeWidth(this.selectorStrokeWidth);
        canvas.drawLine(width, f, r1 + ((int) (cos2 * r6)), height + ((int) (r6 * sin2)), this.paint);
        canvas.drawCircle(width, f, this.centerDotRadius, this.paint);
    }

    @Override // android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        setHandRotation(this.originalDeg);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        int actionMasked = motionEvent.getActionMasked();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        boolean z3 = false;
        if (actionMasked == 0) {
            this.downX = x;
            this.downY = y;
            this.changedDuringTouch = false;
            z = true;
            z2 = false;
        } else if (actionMasked == 1 || actionMasked == 2) {
            z2 = this.changedDuringTouch;
            z = false;
        } else {
            z2 = false;
            z = false;
        }
        boolean z4 = this.changedDuringTouch;
        int degrees = ((int) Math.toDegrees(Math.atan2(y - (getHeight() / 2), x - (getWidth() / 2)))) + 90;
        if (degrees < 0) {
            degrees += 360;
        }
        float f = degrees;
        boolean z5 = this.originalDeg != f;
        if (!z || !z5) {
            if (z5 || z2) {
                setHandRotation(f);
            }
            this.changedDuringTouch = z4 | z3;
            return true;
        }
        z3 = true;
        this.changedDuringTouch = z4 | z3;
        return true;
    }

    public final void setHandRotation(float f) {
        ValueAnimator valueAnimator = this.rotationAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        setHandRotationInternal(f);
    }

    public final void setHandRotationInternal(float f) {
        float f2 = f % 360.0f;
        this.originalDeg = f2;
        this.degRad = Math.toRadians(f2 - 90.0f);
        int height = getHeight() / 2;
        float cos = (this.circleRadius * ((float) Math.cos(this.degRad))) + (getWidth() / 2);
        float sin = (this.circleRadius * ((float) Math.sin(this.degRad))) + height;
        RectF rectF = this.selectorBox;
        float f3 = this.selectorRadius;
        rectF.set(cos - f3, sin - f3, cos + f3, sin + f3);
        Iterator it = ((ArrayList) this.listeners).iterator();
        while (it.hasNext()) {
            ClockFaceView clockFaceView = (ClockFaceView) ((OnRotateListener) it.next());
            if (Math.abs(clockFaceView.currentHandRotation - f2) > 0.001f) {
                clockFaceView.currentHandRotation = f2;
                clockFaceView.findIntersectingTextView();
            }
        }
        invalidate();
    }

    public ClockHandView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialClockStyle);
    }

    public ClockHandView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.listeners = new ArrayList();
        Paint paint = new Paint();
        this.paint = paint;
        this.selectorBox = new RectF();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ClockHandView, i, 2132019175);
        this.circleRadius = obtainStyledAttributes.getDimensionPixelSize(1, 0);
        this.selectorRadius = obtainStyledAttributes.getDimensionPixelSize(2, 0);
        this.selectorStrokeWidth = getResources().getDimensionPixelSize(R.dimen.material_clock_hand_stroke_width);
        this.centerDotRadius = r3.getDimensionPixelSize(R.dimen.material_clock_hand_center_dot_radius);
        int color = obtainStyledAttributes.getColor(0, 0);
        paint.setAntiAlias(true);
        paint.setColor(color);
        setHandRotation(0.0f);
        this.scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setImportantForAccessibility(this, 2);
        obtainStyledAttributes.recycle();
    }
}
