package android.inputmethodservice.navigationbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;

final class ButtonDispatcher {
    private static final int FADE_DURATION_IN = 150;
    private static final int FADE_DURATION_OUT = 250;
    public static final Interpolator LINEAR = new LinearInterpolator();
    private View.AccessibilityDelegate mAccessibilityDelegate;
    private View.OnClickListener mClickListener;
    private View mCurrentView;
    private Float mDarkIntensity;
    private Boolean mDelayTouchFeedback;
    private ValueAnimator mFadeAnimator;
    private final int mId;
    private KeyButtonDrawable mImageDrawable;
    private View.OnLongClickListener mLongClickListener;
    private Boolean mLongClickable;
    private View.OnHoverListener mOnHoverListener;
    private View.OnTouchListener mTouchListener;
    private final ArrayList<View> mViews = new ArrayList<>();
    private float mAlpha = 1.0f;
    private int mVisibility = 0;
    private final ValueAnimator.AnimatorUpdateListener mAlphaListener =
            new ValueAnimator.AnimatorUpdateListener() { // from class:
                // android.inputmethodservice.navigationbar.ButtonDispatcher$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ButtonDispatcher.this.lambda$new$0(valueAnimator);
                }
            };
    private final AnimatorListenerAdapter mFadeListener =
            new AnimatorListenerAdapter() { // from class:
                // android.inputmethodservice.navigationbar.ButtonDispatcher.1
                @Override // android.animation.AnimatorListenerAdapter,
                // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    ButtonDispatcher.this.mFadeAnimator = null;
                    ButtonDispatcher.this.setVisibility(
                            ButtonDispatcher.this.getAlpha() == 1.0f ? 0 : 4);
                }
            };

    public /* synthetic */ void lambda$new$0(ValueAnimator animation) {
        setAlpha(((Float) animation.getAnimatedValue()).floatValue(), false, false);
    }

    ButtonDispatcher(int id) {
        this.mId = id;
    }

    public void clear() {
        this.mViews.clear();
    }

    public void addView(View view) {
        this.mViews.add(view);
        view.setOnClickListener(this.mClickListener);
        view.setOnTouchListener(this.mTouchListener);
        view.setOnLongClickListener(this.mLongClickListener);
        view.setOnHoverListener(this.mOnHoverListener);
        if (this.mLongClickable != null) {
            view.setLongClickable(this.mLongClickable.booleanValue());
        }
        view.setAlpha(this.mAlpha);
        view.setVisibility(this.mVisibility);
        if (this.mAccessibilityDelegate != null) {
            view.setAccessibilityDelegate(this.mAccessibilityDelegate);
        }
        if (view instanceof ButtonInterface) {
            ButtonInterface button = (ButtonInterface) view;
            if (this.mDarkIntensity != null) {
                button.setDarkIntensity(this.mDarkIntensity.floatValue());
            }
            if (this.mImageDrawable != null) {
                button.setImageDrawable(this.mImageDrawable);
            }
            if (this.mDelayTouchFeedback != null) {
                button.setDelayTouchFeedback(this.mDelayTouchFeedback.booleanValue());
            }
        }
    }

    public int getId() {
        return this.mId;
    }

    public int getVisibility() {
        return this.mVisibility;
    }

    public boolean isVisible() {
        return getVisibility() == 0;
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public KeyButtonDrawable getImageDrawable() {
        return this.mImageDrawable;
    }

    public void setImageDrawable(KeyButtonDrawable drawable) {
        this.mImageDrawable = drawable;
        int numViews = this.mViews.size();
        for (int i = 0; i < numViews; i++) {
            if (this.mViews.get(i) instanceof ButtonInterface) {
                ((ButtonInterface) this.mViews.get(i)).setImageDrawable(this.mImageDrawable);
            }
        }
        if (this.mImageDrawable != null) {
            this.mImageDrawable.setCallback(this.mCurrentView);
        }
    }

    public void setVisibility(int visibility) {
        if (this.mVisibility == visibility) {
            return;
        }
        if (this.mFadeAnimator != null) {
            this.mFadeAnimator.cancel();
        }
        this.mVisibility = visibility;
        int numViews = this.mViews.size();
        for (int i = 0; i < numViews; i++) {
            this.mViews.get(i).setVisibility(this.mVisibility);
        }
    }

    public void setAlpha(float alpha) {
        setAlpha(alpha, false);
    }

    public void setAlpha(float alpha, boolean animate) {
        setAlpha(alpha, animate, true);
    }

    public void setAlpha(float alpha, boolean animate, long duration) {
        setAlpha(alpha, animate, duration, true);
    }

    public void setAlpha(float alpha, boolean animate, boolean cancelAnimator) {
        setAlpha(alpha, animate, getAlpha() < alpha ? 150L : 250L, cancelAnimator);
    }

    public void setAlpha(float alpha, boolean animate, long duration, boolean cancelAnimator) {
        if (this.mFadeAnimator != null && (cancelAnimator || animate)) {
            this.mFadeAnimator.cancel();
        }
        if (animate) {
            setVisibility(0);
            this.mFadeAnimator = ValueAnimator.ofFloat(getAlpha(), alpha);
            this.mFadeAnimator.setDuration(duration);
            this.mFadeAnimator.setInterpolator(LINEAR);
            this.mFadeAnimator.addListener(this.mFadeListener);
            this.mFadeAnimator.addUpdateListener(this.mAlphaListener);
            this.mFadeAnimator.start();
            return;
        }
        int prevAlpha = (int) (getAlpha() * 255.0f);
        int nextAlpha = (int) (alpha * 255.0f);
        if (prevAlpha != nextAlpha) {
            this.mAlpha = nextAlpha / 255.0f;
            int numViews = this.mViews.size();
            for (int i = 0; i < numViews; i++) {
                this.mViews.get(i).setAlpha(this.mAlpha);
            }
        }
    }

    public void setDarkIntensity(float darkIntensity) {
        this.mDarkIntensity = Float.valueOf(darkIntensity);
        int numViews = this.mViews.size();
        for (int i = 0; i < numViews; i++) {
            if (this.mViews.get(i) instanceof ButtonInterface) {
                ((ButtonInterface) this.mViews.get(i)).setDarkIntensity(darkIntensity);
            }
        }
    }

    public void setDelayTouchFeedback(boolean delay) {
        this.mDelayTouchFeedback = Boolean.valueOf(delay);
        int numViews = this.mViews.size();
        for (int i = 0; i < numViews; i++) {
            if (this.mViews.get(i) instanceof ButtonInterface) {
                ((ButtonInterface) this.mViews.get(i)).setDelayTouchFeedback(delay);
            }
        }
    }

    public void setOnClickListener(View.OnClickListener clickListener) {
        this.mClickListener = clickListener;
        int numViews = this.mViews.size();
        for (int i = 0; i < numViews; i++) {
            this.mViews.get(i).setOnClickListener(this.mClickListener);
        }
    }

    public void setOnTouchListener(View.OnTouchListener touchListener) {
        this.mTouchListener = touchListener;
        int numViews = this.mViews.size();
        for (int i = 0; i < numViews; i++) {
            this.mViews.get(i).setOnTouchListener(this.mTouchListener);
        }
    }

    public void setLongClickable(boolean isLongClickable) {
        this.mLongClickable = Boolean.valueOf(isLongClickable);
        int numViews = this.mViews.size();
        for (int i = 0; i < numViews; i++) {
            this.mViews.get(i).setLongClickable(this.mLongClickable.booleanValue());
        }
    }

    public void setOnLongClickListener(View.OnLongClickListener longClickListener) {
        this.mLongClickListener = longClickListener;
        int numViews = this.mViews.size();
        for (int i = 0; i < numViews; i++) {
            this.mViews.get(i).setOnLongClickListener(this.mLongClickListener);
        }
    }

    public void setOnHoverListener(View.OnHoverListener hoverListener) {
        this.mOnHoverListener = hoverListener;
        int numViews = this.mViews.size();
        for (int i = 0; i < numViews; i++) {
            this.mViews.get(i).setOnHoverListener(this.mOnHoverListener);
        }
    }

    public void setAccessibilityDelegate(View.AccessibilityDelegate delegate) {
        this.mAccessibilityDelegate = delegate;
        int numViews = this.mViews.size();
        for (int i = 0; i < numViews; i++) {
            this.mViews.get(i).setAccessibilityDelegate(delegate);
        }
    }

    public void setTranslation(int x, int y, int z) {
        int numViews = this.mViews.size();
        for (int i = 0; i < numViews; i++) {
            View view = this.mViews.get(i);
            view.setTranslationX(x);
            view.setTranslationY(y);
            view.setTranslationZ(z);
        }
    }

    public ArrayList<View> getViews() {
        return this.mViews;
    }

    public View getCurrentView() {
        return this.mCurrentView;
    }

    public void setCurrentView(View currentView) {
        this.mCurrentView = currentView.findViewById(this.mId);
        if (this.mImageDrawable != null) {
            this.mImageDrawable.setCallback(this.mCurrentView);
        }
        if (this.mCurrentView != null) {
            this.mCurrentView.setTranslationX(0.0f);
            this.mCurrentView.setTranslationY(0.0f);
            this.mCurrentView.setTranslationZ(0.0f);
        }
    }

    public void onDestroy() {}
}
