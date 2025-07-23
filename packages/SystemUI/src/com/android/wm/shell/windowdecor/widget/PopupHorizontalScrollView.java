package com.android.wm.shell.windowdecor.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PopupHorizontalScrollView extends HorizontalScrollView {
    public DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener mEventListener;

    public PopupHorizontalScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setHorizontalScrollBarEnabled(false);
        setSmoothScrollingEnabled(true);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener desktopModeTouchEventListener;
        if (motionEvent.getActionMasked() == 0 && (desktopModeTouchEventListener = this.mEventListener) != null) {
            desktopModeTouchEventListener.unschedulePopupDismiss();
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        scrollTo(getMaxScrollAmount(), getScrollY());
    }

    @Override // android.widget.HorizontalScrollView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener desktopModeTouchEventListener;
        if ((motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3) && (desktopModeTouchEventListener = this.mEventListener) != null) {
            desktopModeTouchEventListener.schedulePopupDismiss();
        }
        return super.onTouchEvent(motionEvent);
    }
}
