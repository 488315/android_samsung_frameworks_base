package com.google.android.setupdesign.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class StickyHeaderScrollView extends BottomScrollView {
    public int statusBarInset;
    public View sticky;
    public View stickyContainer;

    public StickyHeaderScrollView(Context context) {
        super(context);
        this.statusBarInset = 0;
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (!getFitsSystemWindows()) {
            return windowInsets;
        }
        this.statusBarInset = windowInsets.getSystemWindowInsetTop();
        return windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), 0, windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
    }

    @Override // com.google.android.setupdesign.view.BottomScrollView, android.widget.ScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.sticky == null) {
            this.sticky = findViewWithTag("sticky");
            this.stickyContainer = findViewWithTag("stickyContainer");
        }
        updateStickyHeaderPosition();
    }

    @Override // com.google.android.setupdesign.view.BottomScrollView, android.view.View
    public final void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        updateStickyHeaderPosition();
    }

    public final void updateStickyHeaderPosition() {
        View view = this.sticky;
        if (view != null) {
            View view2 = this.stickyContainer;
            View view3 = view2 != null ? view2 : view;
            if ((view3.getTop() - getScrollY()) + (view2 != null ? view.getTop() : 0) < this.statusBarInset || !view3.isShown()) {
                view3.setTranslationY(getScrollY() - r0);
            } else {
                view3.setTranslationY(0.0f);
            }
        }
    }

    public StickyHeaderScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.statusBarInset = 0;
    }

    public StickyHeaderScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.statusBarInset = 0;
    }
}
