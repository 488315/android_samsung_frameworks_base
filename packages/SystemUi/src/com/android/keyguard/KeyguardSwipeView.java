package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.android.keyguard.KeyguardSwipeViewController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardSwipeView extends KeyguardInputView {
    public KeyguardSwipeViewController.SwipeTouchListener mSwipeTouchListener;

    public KeyguardSwipeView(Context context) {
        this(context, null);
    }

    @Override // com.android.keyguard.KeyguardInputView
    public final CharSequence getTitle() {
        return null;
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
        if (swipeTouchListener == null || !swipeTouchListener.onTouchEvent(motionEvent)) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
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
