package android.inputmethodservice.navigationbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import java.util.ArrayList;

/* loaded from: classes2.dex */
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
    private final ValueAnimator.AnimatorUpdateListener mAlphaListener = new ValueAnimator.AnimatorUpdateListener() { // from class: android.inputmethodservice.navigationbar.ButtonDispatcher$$ExternalSyntheticLambda0
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            ButtonDispatcher.this.lambda$new$0(valueAnimator);
        }
    };
    private final AnimatorListenerAdapter mFadeListener = new AnimatorListenerAdapter() { // from class: android.inputmethodservice.navigationbar.ButtonDispatcher.1
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            ButtonDispatcher.this.mFadeAnimator = null;
            ButtonDispatcher buttonDispatcher = ButtonDispatcher.this;
            buttonDispatcher.setVisibility(buttonDispatcher.getAlpha() == 1.0f ? 0 : 4);
        }
    };

    public void onDestroy() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ValueAnimator valueAnimator) {
        setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue(), false, false);
    }

    ButtonDispatcher(int i) {
        this.mId = i;
    }

    public void clear() {
        this.mViews.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void addView(View view) {
        this.mViews.add(view);
        view.setOnClickListener(this.mClickListener);
        view.setOnTouchListener(this.mTouchListener);
        view.setOnLongClickListener(this.mLongClickListener);
        view.setOnHoverListener(this.mOnHoverListener);
        Boolean bool = this.mLongClickable;
        if (bool != null) {
            view.setLongClickable(bool.booleanValue());
        }
        view.setAlpha(this.mAlpha);
        view.setVisibility(this.mVisibility);
        View.AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
        if (accessibilityDelegate != null) {
            view.setAccessibilityDelegate(accessibilityDelegate);
        }
        if (view instanceof ButtonInterface) {
            ButtonInterface buttonInterface = (ButtonInterface) view;
            Float f = this.mDarkIntensity;
            if (f != null) {
                buttonInterface.setDarkIntensity(f.floatValue());
            }
            KeyButtonDrawable keyButtonDrawable = this.mImageDrawable;
            if (keyButtonDrawable != null) {
                buttonInterface.setImageDrawable(keyButtonDrawable);
            }
            Boolean bool2 = this.mDelayTouchFeedback;
            if (bool2 != null) {
                buttonInterface.setDelayTouchFeedback(bool2.booleanValue());
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

    public void setImageDrawable(KeyButtonDrawable keyButtonDrawable) {
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

    public void setVisibility(int i) {
        if (this.mVisibility == i) {
            return;
        }
        ValueAnimator valueAnimator = this.mFadeAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        this.mVisibility = i;
        int size = this.mViews.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mViews.get(i2).setVisibility(this.mVisibility);
        }
    }

    public void setAlpha(float f) {
        setAlpha(f, false);
    }

    public void setAlpha(float f, boolean z) {
        setAlpha(f, z, true);
    }

    public void setAlpha(float f, boolean z, long j) {
        setAlpha(f, z, j, true);
    }

    public void setAlpha(float f, boolean z, boolean z2) {
        setAlpha(f, z, getAlpha() < f ? 150L : 250L, z2);
    }

    public void setAlpha(float f, boolean z, long j, boolean z2) {
        ValueAnimator valueAnimator = this.mFadeAnimator;
        if (valueAnimator != null && (z2 || z)) {
            valueAnimator.cancel();
        }
        if (z) {
            setVisibility(0);
            ValueAnimator ofFloat = ValueAnimator.ofFloat(getAlpha(), f);
            this.mFadeAnimator = ofFloat;
            ofFloat.setDuration(j);
            this.mFadeAnimator.setInterpolator(LINEAR);
            this.mFadeAnimator.addListener(this.mFadeListener);
            this.mFadeAnimator.addUpdateListener(this.mAlphaListener);
            this.mFadeAnimator.start();
            return;
        }
        int i = (int) (f * 255.0f);
        if (((int) (getAlpha() * 255.0f)) != i) {
            this.mAlpha = i / 255.0f;
            int size = this.mViews.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.mViews.get(i2).setAlpha(this.mAlpha);
            }
        }
    }

    public void setDarkIntensity(float f) {
        this.mDarkIntensity = Float.valueOf(f);
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            if (this.mViews.get(i) instanceof ButtonInterface) {
                ((ButtonInterface) this.mViews.get(i)).setDarkIntensity(f);
            }
        }
    }

    public void setDelayTouchFeedback(boolean z) {
        this.mDelayTouchFeedback = Boolean.valueOf(z);
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            if (this.mViews.get(i) instanceof ButtonInterface) {
                ((ButtonInterface) this.mViews.get(i)).setDelayTouchFeedback(z);
            }
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mClickListener = onClickListener;
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            this.mViews.get(i).setOnClickListener(this.mClickListener);
        }
    }

    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        this.mTouchListener = onTouchListener;
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            this.mViews.get(i).setOnTouchListener(this.mTouchListener);
        }
    }

    public void setLongClickable(boolean z) {
        this.mLongClickable = Boolean.valueOf(z);
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            this.mViews.get(i).setLongClickable(this.mLongClickable.booleanValue());
        }
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.mLongClickListener = onLongClickListener;
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            this.mViews.get(i).setOnLongClickListener(this.mLongClickListener);
        }
    }

    public void setOnHoverListener(View.OnHoverListener onHoverListener) {
        this.mOnHoverListener = onHoverListener;
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            this.mViews.get(i).setOnHoverListener(this.mOnHoverListener);
        }
    }

    public void setAccessibilityDelegate(View.AccessibilityDelegate accessibilityDelegate) {
        this.mAccessibilityDelegate = accessibilityDelegate;
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            this.mViews.get(i).setAccessibilityDelegate(accessibilityDelegate);
        }
    }

    public void setTranslation(int i, int i2, int i3) {
        int size = this.mViews.size();
        for (int i4 = 0; i4 < size; i4++) {
            View view = this.mViews.get(i4);
            view.setTranslationX(i);
            view.setTranslationY(i2);
            view.setTranslationZ(i3);
        }
    }

    public ArrayList<View> getViews() {
        return this.mViews;
    }

    public View getCurrentView() {
        return this.mCurrentView;
    }

    public void setCurrentView(View view) {
        View findViewById = view.findViewById(this.mId);
        this.mCurrentView = findViewById;
        KeyButtonDrawable keyButtonDrawable = this.mImageDrawable;
        if (keyButtonDrawable != null) {
            keyButtonDrawable.setCallback(findViewById);
        }
        View view2 = this.mCurrentView;
        if (view2 != null) {
            view2.setTranslationX(0.0f);
            this.mCurrentView.setTranslationY(0.0f);
            this.mCurrentView.setTranslationZ(0.0f);
        }
    }
}
