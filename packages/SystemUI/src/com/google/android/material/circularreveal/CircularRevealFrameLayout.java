package com.google.android.material.circularreveal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.google.android.material.circularreveal.CircularRevealWidget;
import com.google.android.material.math.MathUtils;

/* loaded from: classes4.dex */
public class CircularRevealFrameLayout extends FrameLayout implements CircularRevealWidget {
    public final CircularRevealHelper helper;

    public CircularRevealFrameLayout(Context context) {
        this(context, null);
    }

    @Override // com.google.android.material.circularreveal.CircularRevealHelper.Delegate
    public final void actualDraw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override // com.google.android.material.circularreveal.CircularRevealHelper.Delegate
    public final boolean actualIsOpaque() {
        return super.isOpaque();
    }

    @Override // com.google.android.material.circularreveal.CircularRevealWidget
    public final void buildCircularRevealCache() {
        this.helper.getClass();
    }

    @Override // com.google.android.material.circularreveal.CircularRevealWidget
    public final void destroyCircularRevealCache() {
        this.helper.getClass();
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        CircularRevealHelper circularRevealHelper = this.helper;
        if (circularRevealHelper != null) {
            circularRevealHelper.draw(canvas);
        } else {
            super.draw(canvas);
        }
    }

    @Override // com.google.android.material.circularreveal.CircularRevealWidget
    public final int getCircularRevealScrimColor() {
        return this.helper.scrimPaint.getColor();
    }

    @Override // com.google.android.material.circularreveal.CircularRevealWidget
    public final CircularRevealWidget.RevealInfo getRevealInfo() {
        CircularRevealWidget.RevealInfo revealInfo = this.helper.revealInfo;
        if (revealInfo == null) {
            return null;
        }
        CircularRevealWidget.RevealInfo revealInfo2 = new CircularRevealWidget.RevealInfo(revealInfo);
        if (revealInfo2.radius == Float.MAX_VALUE) {
            revealInfo2.radius = MathUtils.distanceToFurthestCorner(revealInfo2.centerX, revealInfo2.centerY, r4.view.getWidth(), r4.view.getHeight());
        }
        return revealInfo2;
    }

    @Override // android.view.View
    public final boolean isOpaque() {
        CircularRevealHelper circularRevealHelper = this.helper;
        if (circularRevealHelper == null) {
            return super.isOpaque();
        }
        if (!circularRevealHelper.delegate.actualIsOpaque()) {
            return false;
        }
        CircularRevealWidget.RevealInfo revealInfo = circularRevealHelper.revealInfo;
        return revealInfo == null || revealInfo.radius == Float.MAX_VALUE;
    }

    @Override // com.google.android.material.circularreveal.CircularRevealWidget
    public final void setCircularRevealOverlayDrawable(Drawable drawable) {
        CircularRevealHelper circularRevealHelper = this.helper;
        circularRevealHelper.overlayDrawable = drawable;
        circularRevealHelper.view.invalidate();
    }

    @Override // com.google.android.material.circularreveal.CircularRevealWidget
    public final void setCircularRevealScrimColor(int i) {
        CircularRevealHelper circularRevealHelper = this.helper;
        circularRevealHelper.scrimPaint.setColor(i);
        circularRevealHelper.view.invalidate();
    }

    @Override // com.google.android.material.circularreveal.CircularRevealWidget
    public final void setRevealInfo(CircularRevealWidget.RevealInfo revealInfo) {
        this.helper.setRevealInfo(revealInfo);
    }

    public CircularRevealFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.helper = new CircularRevealHelper(this);
    }
}
