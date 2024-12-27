package com.android.systemui.statusbar.window;

import android.content.Context;
import android.graphics.Insets;
import android.util.AttributeSet;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import com.android.systemui.log.QuickPanelLogger;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class StatusBarWindowView extends FrameLayout {
    public int mLeftInset;
    public final QuickPanelLogger mQuickPanelLogger;
    public int mRightInset;
    public int mTopInset;
    public float mTouchDownY;

    public StatusBarWindowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLeftInset = 0;
        this.mRightInset = 0;
        this.mTopInset = 0;
        this.mTouchDownY = 0.0f;
        this.mQuickPanelLogger = new QuickPanelLogger("SBWV");
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
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (quickPanelLogger != null) {
            quickPanelLogger.quickPanelLoggerHelper.dispatchTouchEventLogger.log(motionEvent, quickPanelLogger.tag, "");
        }
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

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setRequestedFrameRate(-2.0f);
    }
}
