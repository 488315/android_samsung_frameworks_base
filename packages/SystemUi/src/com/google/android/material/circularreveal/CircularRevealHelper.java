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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CircularRevealHelper {
    public final Delegate delegate;
    public Drawable overlayDrawable;
    public CircularRevealWidget.RevealInfo revealInfo;
    public final Paint scrimPaint;
    public final View view;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Removed duplicated region for block: B:10:0x0021  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void draw(Canvas canvas) {
        boolean z;
        boolean z2;
        Drawable drawable;
        CircularRevealWidget.RevealInfo revealInfo = this.revealInfo;
        boolean z3 = false;
        if (revealInfo != null) {
            if (!(revealInfo.radius == Float.MAX_VALUE)) {
                z = false;
                z2 = !z;
                Paint paint = this.scrimPaint;
                Delegate delegate = this.delegate;
                View view = this.view;
                if (z2) {
                    delegate.actualDraw(canvas);
                    if (Color.alpha(paint.getColor()) != 0) {
                        canvas.drawRect(0.0f, 0.0f, view.getWidth(), view.getHeight(), paint);
                    }
                } else {
                    delegate.actualDraw(canvas);
                    if (Color.alpha(paint.getColor()) != 0) {
                        canvas.drawRect(0.0f, 0.0f, view.getWidth(), view.getHeight(), paint);
                    }
                }
                drawable = this.overlayDrawable;
                if (drawable != null && this.revealInfo != null) {
                    z3 = true;
                }
                if (z3) {
                    return;
                }
                Rect bounds = drawable.getBounds();
                float width = this.revealInfo.centerX - (bounds.width() / 2.0f);
                float height = this.revealInfo.centerY - (bounds.height() / 2.0f);
                canvas.translate(width, height);
                this.overlayDrawable.draw(canvas);
                canvas.translate(-width, -height);
                return;
            }
        }
        z = true;
        z2 = !z;
        Paint paint2 = this.scrimPaint;
        Delegate delegate2 = this.delegate;
        View view2 = this.view;
        if (z2) {
        }
        drawable = this.overlayDrawable;
        if (drawable != null) {
            z3 = true;
        }
        if (z3) {
        }
    }

    public final int getCircularRevealScrimColor() {
        return this.scrimPaint.getColor();
    }

    public final CircularRevealWidget.RevealInfo getRevealInfo() {
        CircularRevealWidget.RevealInfo revealInfo = this.revealInfo;
        if (revealInfo == null) {
            return null;
        }
        CircularRevealWidget.RevealInfo revealInfo2 = new CircularRevealWidget.RevealInfo(revealInfo);
        if (revealInfo2.radius == Float.MAX_VALUE) {
            float f = revealInfo2.centerX;
            float f2 = revealInfo2.centerY;
            View view = this.view;
            revealInfo2.radius = MathUtils.distanceToFurthestCorner(f, f2, view.getWidth(), view.getHeight());
        }
        return revealInfo2;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0023  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isOpaque() {
        boolean z;
        if (!this.delegate.actualIsOpaque()) {
            return false;
        }
        CircularRevealWidget.RevealInfo revealInfo = this.revealInfo;
        if (revealInfo != null) {
            if (!(revealInfo.radius == Float.MAX_VALUE)) {
                z = false;
                return z ^ true;
            }
        }
        z = true;
        if (z ^ true) {
        }
    }

    public final void setCircularRevealOverlayDrawable(Drawable drawable) {
        this.overlayDrawable = drawable;
        this.view.invalidate();
    }

    public final void setCircularRevealScrimColor(int i) {
        this.scrimPaint.setColor(i);
        this.view.invalidate();
    }

    public final void setRevealInfo(CircularRevealWidget.RevealInfo revealInfo) {
        View view = this.view;
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
            if (revealInfo.radius + 1.0E-4f >= MathUtils.distanceToFurthestCorner(revealInfo.centerX, revealInfo.centerY, (float) view.getWidth(), (float) view.getHeight())) {
                this.revealInfo.radius = Float.MAX_VALUE;
            }
        }
        view.invalidate();
    }
}
