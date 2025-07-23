package com.google.android.material.circularreveal;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.material.circularreveal.CircularRevealWidget;
import com.google.android.material.math.MathUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CircularRevealHelper {
    public final Delegate delegate;
    public Drawable overlayDrawable;
    public CircularRevealWidget.RevealInfo revealInfo;
    public final Paint scrimPaint;
    public final View view;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Delegate {
        void actualDraw(Canvas canvas);

        boolean actualIsOpaque();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CircularRevealHelper(Delegate delegate) {
        this.delegate = delegate;
        View view = (View) delegate;
        this.view = view;
        view.setWillNotDraw(false);
        new Path();
        new Paint(7);
        Paint paint = new Paint(1);
        this.scrimPaint = paint;
        paint.setColor(0);
    }

    public final void draw(Canvas canvas) {
        Canvas canvas2;
        CircularRevealWidget.RevealInfo revealInfo = this.revealInfo;
        boolean z = revealInfo == null || revealInfo.radius == Float.MAX_VALUE;
        Delegate delegate = this.delegate;
        if (z) {
            canvas2 = canvas;
            delegate.actualDraw(canvas2);
            if (Color.alpha(this.scrimPaint.getColor()) != 0) {
                canvas2.drawRect(0.0f, 0.0f, this.view.getWidth(), this.view.getHeight(), this.scrimPaint);
            }
        } else {
            delegate.actualDraw(canvas);
            if (Color.alpha(this.scrimPaint.getColor()) != 0) {
                canvas.drawRect(0.0f, 0.0f, this.view.getWidth(), this.view.getHeight(), this.scrimPaint);
                canvas2 = canvas;
            } else {
                canvas2 = canvas;
            }
        }
        Drawable drawable = this.overlayDrawable;
        if (drawable == null || this.revealInfo == null) {
            return;
        }
        Rect bounds = drawable.getBounds();
        float width = this.revealInfo.centerX - (bounds.width() / 2.0f);
        float height = this.revealInfo.centerY - (bounds.height() / 2.0f);
        canvas2.translate(width, height);
        this.overlayDrawable.draw(canvas2);
        canvas2.translate(-width, -height);
    }

    public final void setRevealInfo(CircularRevealWidget.RevealInfo revealInfo) {
        if (revealInfo == null) {
            this.revealInfo = null;
        } else {
            CircularRevealWidget.RevealInfo revealInfo2 = this.revealInfo;
            if (revealInfo2 == null) {
                this.revealInfo = new CircularRevealWidget.RevealInfo(revealInfo);
            } else {
                float f = revealInfo.centerX;
                float f2 = revealInfo.centerY;
                float f3 = revealInfo.radius;
                revealInfo2.centerX = f;
                revealInfo2.centerY = f2;
                revealInfo2.radius = f3;
            }
            if (revealInfo.radius + 1.0E-4f >= MathUtils.distanceToFurthestCorner(revealInfo.centerX, revealInfo.centerY, this.view.getWidth(), this.view.getHeight())) {
                this.revealInfo.radius = Float.MAX_VALUE;
            }
        }
        this.view.invalidate();
    }
}
