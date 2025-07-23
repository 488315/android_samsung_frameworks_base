package com.android.systemui.navigationbar.views.buttons;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.navigationbar.views.NavigationBarView;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ButtonDispatcher {
    public NavigationBarView.AnonymousClass1 mAccessibilityDelegate;
    public Float mAlpha;
    public View.OnClickListener mClickListener;
    public View mCurrentView;
    public Float mDarkIntensity;
    public ValueAnimator mFadeAnimator;
    public final int mId;
    public KeyButtonDrawable mImageDrawable;
    public View.OnLongClickListener mLongClickListener;
    public Boolean mLongClickable;
    public NavBarButtonClickLogger mNavBarButtonClickLogger;
    public View.OnHoverListener mOnHoverListener;
    public View.OnTouchListener mTouchListener;
    public final ArrayList mViews = new ArrayList();
    public Integer mVisibility = 0;
    public final ButtonDispatcher$$ExternalSyntheticLambda0 mAlphaListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.navigationbar.views.buttons.ButtonDispatcher$$ExternalSyntheticLambda0
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            ButtonDispatcher.this.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue(), false);
        }
    };
    public final AnonymousClass1 mFadeListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.navigationbar.views.buttons.ButtonDispatcher.1
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            ButtonDispatcher buttonDispatcher = ButtonDispatcher.this;
            buttonDispatcher.mFadeAnimator = null;
            buttonDispatcher.setVisibility(buttonDispatcher.getAlpha() == 1.0f ? 0 : 4);
        }
    };

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.navigationbar.views.buttons.ButtonDispatcher$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.navigationbar.views.buttons.ButtonDispatcher$1] */
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
        NavigationBarView.AnonymousClass1 anonymousClass1 = this.mAccessibilityDelegate;
        if (anonymousClass1 != null) {
            view.setAccessibilityDelegate(anonymousClass1);
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
        }
    }

    public final float getAlpha() {
        Float f = this.mAlpha;
        if (f != null) {
            return f.floatValue();
        }
        return 1.0f;
    }

    public final int getVisibility() {
        Integer num = this.mVisibility;
        if (num != null) {
            return num.intValue();
        }
        return 0;
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
            int size = this.mViews.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((View) this.mViews.get(i2)).setAlpha(this.mAlpha.floatValue());
            }
        }
    }

    public final void setImageDrawable(KeyButtonDrawable keyButtonDrawable) {
        this.mImageDrawable = keyButtonDrawable;
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            if (this.mViews.get(i) instanceof ButtonInterface) {
                ((ButtonInterface) this.mViews.get(i)).setImageDrawable(this.mImageDrawable);
            }
        }
        KeyButtonDrawable keyButtonDrawable2 = this.mImageDrawable;
        if (keyButtonDrawable2 != null) {
            keyButtonDrawable2.setCallback(this.mCurrentView);
        }
    }

    public final void setLongClickable(boolean z) {
        this.mLongClickable = Boolean.valueOf(z);
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            ((View) this.mViews.get(i)).setLongClickable(this.mLongClickable.booleanValue());
        }
    }

    public final void setNavBarButtonClickLoggerForViewChildren(View view) {
        if (view instanceof KeyButtonView) {
            ((KeyButtonView) view).mNavBarButtonClickLogger = this.mNavBarButtonClickLogger;
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                setNavBarButtonClickLoggerForViewChildren(viewGroup.getChildAt(i));
            }
        }
    }

    public final void setOnClickListener(View.OnClickListener onClickListener) {
        this.mClickListener = onClickListener;
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            ((View) this.mViews.get(i)).setOnClickListener(this.mClickListener);
        }
    }

    public final void setOnHoverListener(View.OnHoverListener onHoverListener) {
        this.mOnHoverListener = onHoverListener;
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            ((View) this.mViews.get(i)).setOnHoverListener(this.mOnHoverListener);
        }
    }

    public final void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.mLongClickListener = onLongClickListener;
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            ((View) this.mViews.get(i)).setOnLongClickListener(this.mLongClickListener);
        }
    }

    public final void setOnTouchListener(View.OnTouchListener onTouchListener) {
        this.mTouchListener = onTouchListener;
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            ((View) this.mViews.get(i)).setOnTouchListener(this.mTouchListener);
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
        int size = this.mViews.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((View) this.mViews.get(i2)).setVisibility(this.mVisibility.intValue());
        }
    }
}
