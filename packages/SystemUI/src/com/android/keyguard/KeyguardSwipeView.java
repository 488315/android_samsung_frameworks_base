package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.android.keyguard.KeyguardSwipeViewController;

/* loaded from: classes.dex */
public class KeyguardSwipeView extends KeyguardInputView {
    public KeyguardSwipeViewController.SwipeTouchListener mSwipeTouchListener;

    public KeyguardSwipeView(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        KeyguardSwipeViewController.SwipeTouchListener swipeTouchListener = this.mSwipeTouchListener;
        if (swipeTouchListener != null) {
            swipeTouchListener.getClass();
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        KeyguardSwipeViewController.SwipeTouchListener swipeTouchListener = this.mSwipeTouchListener;
        if (swipeTouchListener != null) {
            KeyguardSwipeViewController keyguardSwipeViewController = KeyguardSwipeViewController.this;
            keyguardSwipeViewController.mSwipeDetector.setIntercept(true);
            if (keyguardSwipeViewController.mSwipeDetector.onTouchEvent(motionEvent)) {
                return true;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // com.android.keyguard.KeyguardInputView
    public final void startAppearAnimation() {
        setAlpha(0.0f);
        setTranslationY(0.0f);
        animate().alpha(1.0f).withLayer().setDuration(200L);
    }

    @Override // com.android.keyguard.KeyguardInputView
    public final boolean startDisappearAnimation(Runnable runnable) {
        animate().alpha(0.0f).setDuration(100L).withEndAction(runnable);
        return false;
    }

    public KeyguardSwipeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
    }
}
