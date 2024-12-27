package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.android.systemui.doze.DozeReceiver;

public final class UdfpsView extends FrameLayout implements DozeReceiver {
    public final Paint debugTextPaint;
    public final Rect sensorRect;

    public UdfpsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        new Rect();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(-16776961);
        paint.setTextSize(32.0f);
        new UdfpsOverlayParams(null, null, 0, 0, 0.0f, 0, 0, 127, null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    @Override // com.android.systemui.doze.DozeReceiver
    public final void dozeTimeTick() {
    }
}
