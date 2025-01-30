package com.google.android.setupdesign.view;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ListView;
import com.google.android.setupdesign.R$styleable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class StickyHeaderListView extends ListView {
    public int statusBarInset;
    public View sticky;
    public View stickyContainer;
    public final RectF stickyRect;

    public StickyHeaderListView(Context context) {
        super(context);
        this.statusBarInset = 0;
        this.stickyRect = new RectF();
        init(null, R.attr.listViewStyle);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (!this.stickyRect.contains(motionEvent.getX(), motionEvent.getY())) {
            return super.dispatchTouchEvent(motionEvent);
        }
        RectF rectF = this.stickyRect;
        motionEvent.offsetLocation(-rectF.left, -rectF.top);
        return this.stickyContainer.dispatchTouchEvent(motionEvent);
    }

    @Override // android.widget.AbsListView, android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.sticky != null) {
            int save = canvas.save();
            View view = this.stickyContainer;
            View view2 = view != null ? view : this.sticky;
            if (view2.getTop() + (view != null ? this.sticky.getTop() : 0) < this.statusBarInset || !view2.isShown()) {
                this.stickyRect.set(0.0f, (-r1) + this.statusBarInset, view2.getWidth(), (view2.getHeight() - r1) + this.statusBarInset);
                canvas.translate(0.0f, this.stickyRect.top);
                canvas.clipRect(0, 0, view2.getWidth(), view2.getHeight());
                view2.draw(canvas);
            } else {
                this.stickyRect.setEmpty();
            }
            canvas.restoreToCount(save);
        }
    }

    public final void init(AttributeSet attributeSet, int i) {
        if (isInEditMode()) {
            return;
        }
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.SudStickyHeaderListView, i, 0);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId != 0) {
            addHeaderView(LayoutInflater.from(getContext()).inflate(resourceId, (ViewGroup) this, false), null, false);
        }
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (getFitsSystemWindows()) {
            this.statusBarInset = windowInsets.getSystemWindowInsetTop();
            windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), 0, windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
        }
        return windowInsets;
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        int i = this.sticky != null ? 1 : 0;
        accessibilityEvent.setItemCount(accessibilityEvent.getItemCount() - i);
        accessibilityEvent.setFromIndex(Math.max(accessibilityEvent.getFromIndex() - i, 0));
        accessibilityEvent.setToIndex(Math.max(accessibilityEvent.getToIndex() - i, 0));
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.sticky == null) {
            this.sticky = findViewWithTag("sticky");
            this.stickyContainer = findViewWithTag("stickyContainer");
        }
    }

    public StickyHeaderListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.statusBarInset = 0;
        this.stickyRect = new RectF();
        init(attributeSet, R.attr.listViewStyle);
    }

    public StickyHeaderListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.statusBarInset = 0;
        this.stickyRect = new RectF();
        init(attributeSet, i);
    }
}
