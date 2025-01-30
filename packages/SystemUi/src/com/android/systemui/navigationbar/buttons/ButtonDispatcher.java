package com.android.systemui.navigationbar.buttons;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda5;
import com.android.systemui.shared.rotation.RotationButtonController$$ExternalSyntheticLambda3;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ButtonDispatcher {
    public View.AccessibilityDelegate mAccessibilityDelegate;
    public Float mAlpha;
    public View.OnClickListener mClickListener;
    public View mCurrentView;
    public Float mDarkIntensity;
    public ValueAnimator mFadeAnimator;
    public final int mId;
    public KeyButtonDrawable mImageDrawable;
    public View.OnLongClickListener mLongClickListener;
    public Boolean mLongClickable;
    public View.OnHoverListener mOnHoverListener;
    public View.OnTouchListener mTouchListener;
    public boolean mVertical;
    public final ArrayList mViews = new ArrayList();
    public Integer mVisibility = 0;
    public final ButtonDispatcher$$ExternalSyntheticLambda0 mAlphaListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.navigationbar.buttons.ButtonDispatcher$$ExternalSyntheticLambda0
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            ButtonDispatcher buttonDispatcher = ButtonDispatcher.this;
            buttonDispatcher.getClass();
            buttonDispatcher.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue(), false);
        }
    };
    public final C18681 mFadeListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.navigationbar.buttons.ButtonDispatcher.1
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            ButtonDispatcher buttonDispatcher = ButtonDispatcher.this;
            buttonDispatcher.mFadeAnimator = null;
            buttonDispatcher.setVisibility(buttonDispatcher.getAlpha() == 1.0f ? 0 : 4);
        }
    };

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.navigationbar.buttons.ButtonDispatcher$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.navigationbar.buttons.ButtonDispatcher$1] */
    public ButtonDispatcher(int i) {
        this.mId = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void addView(View view) {
        this.mViews.add(view);
        view.setOnClickListener(this.mClickListener);
        view.setOnTouchListener(this.mTouchListener);
        view.setOnLongClickListener(this.mLongClickListener);
        view.setOnHoverListener(this.mOnHoverListener);
        Boolean bool = this.mLongClickable;
        if (bool != null) {
            view.setLongClickable(bool.booleanValue());
        }
        Float f = this.mAlpha;
        if (f != null) {
            view.setAlpha(f.floatValue());
        }
        Integer num = this.mVisibility;
        if (num != null) {
            view.setVisibility(num.intValue());
        }
        View.AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
        if (accessibilityDelegate != null) {
            view.setAccessibilityDelegate(accessibilityDelegate);
        }
        if (view instanceof ButtonInterface) {
            ButtonInterface buttonInterface = (ButtonInterface) view;
            Float f2 = this.mDarkIntensity;
            if (f2 != null) {
                buttonInterface.setDarkIntensity(f2.floatValue());
            }
            KeyButtonDrawable keyButtonDrawable = this.mImageDrawable;
            if (keyButtonDrawable != null) {
                buttonInterface.setImageDrawable(keyButtonDrawable);
            }
            buttonInterface.setVertical();
        }
    }

    public final float getAlpha() {
        Float f = this.mAlpha;
        if (f != null) {
            return f.floatValue();
        }
        return 1.0f;
    }

    public final View getCurrentView() {
        return this.mCurrentView;
    }

    public final int getVisibility() {
        Integer num = this.mVisibility;
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public final boolean isVisible() {
        return getVisibility() == 0;
    }

    public final void setAlpha(float f, boolean z) {
        getAlpha();
        ValueAnimator valueAnimator = this.mFadeAnimator;
        if (valueAnimator != null && z) {
            valueAnimator.cancel();
        }
        int i = (int) (f * 255.0f);
        if (((int) (getAlpha() * 255.0f)) != i) {
            this.mAlpha = Float.valueOf(i / 255.0f);
            ArrayList arrayList = this.mViews;
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((View) arrayList.get(i2)).setAlpha(this.mAlpha.floatValue());
            }
        }
    }

    public final void setDarkIntensity(float f) {
        this.mDarkIntensity = Float.valueOf(f);
        ArrayList arrayList = this.mViews;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (arrayList.get(i) instanceof ButtonInterface) {
                ((ButtonInterface) arrayList.get(i)).setDarkIntensity(f);
            }
        }
    }

    public final void setImageDrawable(KeyButtonDrawable keyButtonDrawable) {
        this.mImageDrawable = keyButtonDrawable;
        ArrayList arrayList = this.mViews;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (arrayList.get(i) instanceof ButtonInterface) {
                ((ButtonInterface) arrayList.get(i)).setImageDrawable(this.mImageDrawable);
            }
        }
        KeyButtonDrawable keyButtonDrawable2 = this.mImageDrawable;
        if (keyButtonDrawable2 != null) {
            keyButtonDrawable2.setCallback(this.mCurrentView);
        }
    }

    public final void setLongClickable(boolean z) {
        this.mLongClickable = Boolean.valueOf(z);
        ArrayList arrayList = this.mViews;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((View) arrayList.get(i)).setLongClickable(this.mLongClickable.booleanValue());
        }
    }

    public final void setOnClickListener(View.OnClickListener onClickListener) {
        this.mClickListener = onClickListener;
        ArrayList arrayList = this.mViews;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((View) arrayList.get(i)).setOnClickListener(this.mClickListener);
        }
    }

    public final void setOnHoverListener(RotationButtonController$$ExternalSyntheticLambda3 rotationButtonController$$ExternalSyntheticLambda3) {
        this.mOnHoverListener = rotationButtonController$$ExternalSyntheticLambda3;
        ArrayList arrayList = this.mViews;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((View) arrayList.get(i)).setOnHoverListener(this.mOnHoverListener);
        }
    }

    public final void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.mLongClickListener = onLongClickListener;
        ArrayList arrayList = this.mViews;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((View) arrayList.get(i)).setOnLongClickListener(this.mLongClickListener);
        }
    }

    public final void setOnTouchListener(NavigationBar$$ExternalSyntheticLambda5 navigationBar$$ExternalSyntheticLambda5) {
        this.mTouchListener = navigationBar$$ExternalSyntheticLambda5;
        ArrayList arrayList = this.mViews;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((View) arrayList.get(i)).setOnTouchListener(this.mTouchListener);
        }
    }

    public void setVisibility(int i) {
        if (this.mVisibility.intValue() == i) {
            return;
        }
        ValueAnimator valueAnimator = this.mFadeAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        this.mVisibility = Integer.valueOf(i);
        ArrayList arrayList = this.mViews;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((View) arrayList.get(i2)).setVisibility(this.mVisibility.intValue());
        }
    }
}
