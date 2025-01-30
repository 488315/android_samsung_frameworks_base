package com.google.android.setupdesign.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class StickyHeaderRecyclerView extends HeaderRecyclerView {
    public int statusBarInset;
    public View sticky;
    public final RectF stickyRect;

    public StickyHeaderRecyclerView(Context context) {
        super(context);
        this.statusBarInset = 0;
        this.stickyRect = new RectF();
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (!this.stickyRect.contains(motionEvent.getX(), motionEvent.getY())) {
            return super.dispatchTouchEvent(motionEvent);
        }
        RectF rectF = this.stickyRect;
        motionEvent.offsetLocation(-rectF.left, -rectF.top);
        return this.header.dispatchTouchEvent(motionEvent);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.sticky != null) {
            View view = this.header;
            int save = canvas.save();
            View view2 = view != null ? view : this.sticky;
            if (view2.getTop() + (view != null ? this.sticky.getTop() : 0) < this.statusBarInset || !view2.isShown()) {
                this.stickyRect.set(0.0f, (-r0) + this.statusBarInset, view2.getWidth(), (view2.getHeight() - r0) + this.statusBarInset);
                canvas.translate(0.0f, this.stickyRect.top);
                canvas.clipRect(0, 0, view2.getWidth(), view2.getHeight());
                view2.draw(canvas);
            } else {
                this.stickyRect.setEmpty();
            }
            canvas.restoreToCount(save);
        }
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (getFitsSystemWindows()) {
            this.statusBarInset = windowInsets.getSystemWindowInsetTop();
            windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), 0, windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
        }
        return windowInsets;
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        View view;
        View view2;
        super.onLayout(z, i, i2, i3, i4);
        if (this.sticky == null && (view2 = this.header) != null) {
            this.sticky = view2.findViewWithTag("sticky");
        }
        if (this.sticky == null || (view = this.header) == null || view.getHeight() != 0) {
            return;
        }
        view.layout(0, -view.getMeasuredHeight(), view.getMeasuredWidth(), 0);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.sticky != null) {
            measureChild(this.header, i, i2);
        }
    }

    public StickyHeaderRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.statusBarInset = 0;
        this.stickyRect = new RectF();
    }

    public StickyHeaderRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.statusBarInset = 0;
        this.stickyRect = new RectF();
    }
}
