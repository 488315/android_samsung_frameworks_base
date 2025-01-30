package com.android.systemui.statusbar.window;

import android.content.Context;
import android.graphics.Insets;
import android.util.AttributeSet;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import com.android.systemui.log.SecTouchLogHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class StatusBarWindowView extends FrameLayout {
    public int mLeftInset;
    public int mRightInset;
    public int mTopInset;
    public float mTouchDownY;
    public final SecTouchLogHelper mTouchLogHelper;

    public StatusBarWindowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLeftInset = 0;
        this.mRightInset = 0;
        this.mTopInset = 0;
        this.mTouchDownY = 0.0f;
        this.mTouchLogHelper = new SecTouchLogHelper();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 && motionEvent.getRawY() > getHeight()) {
            this.mTouchDownY = motionEvent.getRawY();
            motionEvent.setLocation(motionEvent.getRawX(), this.mTopInset);
        } else if (motionEvent.getAction() == 2 && this.mTouchDownY != 0.0f) {
            motionEvent.setLocation(motionEvent.getRawX(), (motionEvent.getRawY() + this.mTopInset) - this.mTouchDownY);
        } else if (motionEvent.getAction() == 1) {
            this.mTouchDownY = 0.0f;
        }
        this.mTouchLogHelper.printDispatchTouchEvent(motionEvent, "SBWV");
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        Insets insetsIgnoringVisibility = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
        this.mLeftInset = insetsIgnoringVisibility.left;
        this.mRightInset = insetsIgnoringVisibility.right;
        this.mTopInset = 0;
        DisplayCutout displayCutout = getRootWindowInsets().getDisplayCutout();
        if (displayCutout != null) {
            this.mTopInset = displayCutout.getWaterfallInsets().top;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getLayoutParams() instanceof FrameLayout.LayoutParams) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                int i2 = layoutParams.rightMargin;
                int i3 = this.mRightInset;
                if (i2 != i3 || layoutParams.leftMargin != this.mLeftInset || layoutParams.topMargin != this.mTopInset) {
                    layoutParams.rightMargin = i3;
                    layoutParams.leftMargin = this.mLeftInset;
                    layoutParams.topMargin = this.mTopInset;
                    childAt.requestLayout();
                }
            }
        }
        return windowInsets;
    }
}
