package com.android.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.EditText;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.res.R$styleable;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.ArrayList;

public class PasswordTextView extends BaseSecPasswordTextView {
    public final Interpolator mAppearInterpolator;
    public final int mCharPadding;
    public final Interpolator mDisappearInterpolator;
    public final int mDotSize;
    public final int mGravity;
    public final PowerManager mPM;
    public final ArrayList mTextChars;
    public int mTextHeightRaw;

    public final class CharState {
        public float currentDotSizeFactor;
        public float currentTextSizeFactor;
        public float currentTextTranslationY;
        public float currentWidthFactor;
        public boolean dotAnimationIsGrowing;
        public Animator dotAnimator;
        public final AnonymousClass2 dotFinishListener;
        public final AnonymousClass10 dotSwapperRunnable;
        public boolean isDotSwapPending;
        public final AnonymousClass6 mDotSizeUpdater;
        public final AnonymousClass7 mTextSizeUpdater;
        public final AnonymousClass8 mTextTranslationUpdater;
        public final AnonymousClass9 mWidthUpdater;
        public final AnonymousClass1 removeEndListener;
        public boolean textAnimationIsGrowing;
        public ValueAnimator textAnimator;
        public final AnonymousClass3 textFinishListener;
        public ValueAnimator textTranslateAnimator;
        public final AnonymousClass4 textTranslateFinishListener;
        public char whichChar;
        public boolean widthAnimationIsGrowing;
        public ValueAnimator widthAnimator;
        public final AnonymousClass5 widthFinishListener;

        public /* synthetic */ CharState(PasswordTextView passwordTextView, int i) {
            this();
        }

        public static void cancelAnimator(Animator animator) {
            if (animator != null) {
                animator.cancel();
            }
        }

        public final void startDotAppearAnimation(long j) {
            AnonymousClass2 anonymousClass2 = this.dotFinishListener;
            AnonymousClass6 anonymousClass6 = this.mDotSizeUpdater;
            cancelAnimator(this.dotAnimator);
            PasswordTextView passwordTextView = PasswordTextView.this;
            if (passwordTextView.mShowPassword) {
                ValueAnimator ofFloat = ValueAnimator.ofFloat(this.currentDotSizeFactor, 1.0f);
                ofFloat.addUpdateListener(anonymousClass6);
                ofFloat.setDuration((long) ((1.0f - this.currentDotSizeFactor) * 160.0f));
                ofFloat.addListener(anonymousClass2);
                ofFloat.setStartDelay(j);
                ofFloat.start();
                this.dotAnimator = ofFloat;
            } else {
                ValueAnimator ofFloat2 = ValueAnimator.ofFloat(this.currentDotSizeFactor, 1.5f);
                ofFloat2.addUpdateListener(anonymousClass6);
                ofFloat2.setInterpolator(passwordTextView.mAppearInterpolator);
                ofFloat2.setDuration(160L);
                ValueAnimator ofFloat3 = ValueAnimator.ofFloat(1.5f, 1.0f);
                ofFloat3.addUpdateListener(anonymousClass6);
                ofFloat3.setDuration(160L);
                ofFloat3.addListener(anonymousClass2);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playSequentially(ofFloat2, ofFloat3);
                animatorSet.setStartDelay(j);
                animatorSet.start();
                this.dotAnimator = animatorSet;
            }
            this.dotAnimationIsGrowing = true;
        }

        public final void startRemoveAnimation(long j, long j2) {
            boolean z = (this.currentDotSizeFactor > 0.0f && this.dotAnimator == null) || (this.dotAnimator != null && this.dotAnimationIsGrowing);
            boolean z2 = (this.currentTextSizeFactor > 0.0f && this.textAnimator == null) || (this.textAnimator != null && this.textAnimationIsGrowing);
            boolean z3 = (this.currentWidthFactor > 0.0f && this.widthAnimator == null) || (this.widthAnimator != null && this.widthAnimationIsGrowing);
            if (z) {
                cancelAnimator(this.dotAnimator);
                ValueAnimator ofFloat = ValueAnimator.ofFloat(this.currentDotSizeFactor, 0.0f);
                ofFloat.addUpdateListener(this.mDotSizeUpdater);
                ofFloat.addListener(this.dotFinishListener);
                ofFloat.setInterpolator(PasswordTextView.this.mDisappearInterpolator);
                ofFloat.setDuration((long) (Math.min(this.currentDotSizeFactor, 1.0f) * 160.0f));
                ofFloat.setStartDelay(j);
                ofFloat.start();
                this.dotAnimator = ofFloat;
                this.dotAnimationIsGrowing = false;
            }
            if (z2) {
                startTextDisappearAnimation(j);
            }
            if (z3) {
                cancelAnimator(this.widthAnimator);
                ValueAnimator ofFloat2 = ValueAnimator.ofFloat(this.currentWidthFactor, 0.0f);
                this.widthAnimator = ofFloat2;
                ofFloat2.addUpdateListener(this.mWidthUpdater);
                this.widthAnimator.addListener(this.widthFinishListener);
                this.widthAnimator.addListener(this.removeEndListener);
                this.widthAnimator.setDuration((long) (this.currentWidthFactor * 160.0f));
                this.widthAnimator.setStartDelay(j2);
                this.widthAnimator.start();
                this.widthAnimationIsGrowing = false;
            }
        }

        public final void startTextDisappearAnimation(long j) {
            cancelAnimator(this.textAnimator);
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.currentTextSizeFactor, 0.0f);
            this.textAnimator = ofFloat;
            ofFloat.addUpdateListener(this.mTextSizeUpdater);
            this.textAnimator.addListener(this.textFinishListener);
            this.textAnimator.setInterpolator(PasswordTextView.this.mDisappearInterpolator);
            this.textAnimator.setDuration((long) (this.currentTextSizeFactor * 160.0f));
            this.textAnimator.setStartDelay(j);
            this.textAnimator.start();
            this.textAnimationIsGrowing = false;
        }

        private CharState() {
            this.currentTextTranslationY = 1.0f;
            this.removeEndListener = new AnimatorListenerAdapter() { // from class: com.android.keyguard.PasswordTextView.CharState.1
                public boolean mCancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    this.mCancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    if (this.mCancelled) {
                        return;
                    }
                    CharState charState = CharState.this;
                    PasswordTextView.this.mTextChars.remove(charState);
                    CharState.cancelAnimator(CharState.this.textTranslateAnimator);
                    CharState.this.textTranslateAnimator = null;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    this.mCancelled = false;
                }
            };
            this.dotFinishListener = new AnimatorListenerAdapter() { // from class: com.android.keyguard.PasswordTextView.CharState.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    CharState.this.dotAnimator = null;
                }
            };
            this.textFinishListener = new AnimatorListenerAdapter() { // from class: com.android.keyguard.PasswordTextView.CharState.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    CharState.this.textAnimator = null;
                }
            };
            this.textTranslateFinishListener = new AnimatorListenerAdapter() { // from class: com.android.keyguard.PasswordTextView.CharState.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    CharState.this.textTranslateAnimator = null;
                }
            };
            this.widthFinishListener = new AnimatorListenerAdapter() { // from class: com.android.keyguard.PasswordTextView.CharState.5
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    CharState.this.widthAnimator = null;
                }
            };
            this.mDotSizeUpdater = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.PasswordTextView.CharState.6
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    CharState.this.currentDotSizeFactor = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    PasswordTextView.this.invalidate();
                }
            };
            this.mTextSizeUpdater = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.PasswordTextView.CharState.7
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    CharState charState = CharState.this;
                    boolean z = charState.textAnimator != null && charState.textAnimationIsGrowing;
                    float f = charState.currentTextSizeFactor;
                    boolean z2 = f > 0.0f || z;
                    charState.currentTextSizeFactor = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    CharState charState2 = CharState.this;
                    if (z2 != (charState2.currentTextSizeFactor > 0.0f || (charState2.textAnimator != null && charState2.textAnimationIsGrowing))) {
                        charState2.currentTextSizeFactor = f;
                        CharSequence transformedText = PasswordTextView.this.getTransformedText();
                        CharState.this.currentTextSizeFactor = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        CharState charState3 = CharState.this;
                        int indexOf = PasswordTextView.this.mTextChars.indexOf(charState3);
                        if (indexOf >= 0) {
                            PasswordTextView.this.sendAccessibilityEventTypeViewTextChanged(transformedText, indexOf, 1, 1);
                        }
                    }
                    PasswordTextView.this.invalidate();
                }
            };
            this.mTextTranslationUpdater = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.PasswordTextView.CharState.8
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    CharState.this.currentTextTranslationY = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    PasswordTextView.this.invalidate();
                }
            };
            this.mWidthUpdater = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.PasswordTextView.CharState.9
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    CharState.this.currentWidthFactor = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    PasswordTextView.this.invalidate();
                }
            };
            this.dotSwapperRunnable = new Runnable() { // from class: com.android.keyguard.PasswordTextView.CharState.10
                @Override // java.lang.Runnable
                public final void run() {
                    CharState charState = CharState.this;
                    charState.startTextDisappearAnimation(0L);
                    charState.startDotAppearAnimation(30L);
                    CharState.this.isDotSwapPending = false;
                }
            };
        }
    }

    public PasswordTextView(Context context) {
        this(context, null);
    }

    public Rect getCharBounds() {
        this.mDrawPaint.setTextSize(this.mTextHeightRaw * getResources().getDisplayMetrics().scaledDensity);
        Rect rect = new Rect();
        this.mDrawPaint.getTextBounds("0", 0, 1, rect);
        return rect;
    }

    @Override // com.android.keyguard.BaseSecPasswordTextView
    public final CharSequence getTransformedText() {
        int size = this.mTextChars.size();
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            CharState charState = (CharState) this.mTextChars.get(i);
            if (charState.dotAnimator == null || charState.dotAnimationIsGrowing) {
                sb.append(charState.whichChar);
            }
        }
        return sb;
    }

    public void onAppend(char c, int i) {
        CharState charState;
        int i2 = 0;
        if (i > this.mTextChars.size()) {
            charState = new CharState(this, i2);
            charState.whichChar = c;
            this.mTextChars.add(charState);
        } else {
            charState = (CharState) this.mTextChars.get(i - 1);
            charState.whichChar = c;
        }
        PasswordTextView passwordTextView = PasswordTextView.this;
        boolean z = passwordTextView.mShowPassword;
        boolean z2 = !z && (charState.dotAnimator == null || !charState.dotAnimationIsGrowing);
        boolean z3 = z && (charState.textAnimator == null || !charState.textAnimationIsGrowing);
        boolean z4 = charState.widthAnimator == null || !charState.widthAnimationIsGrowing;
        if (z2) {
            charState.startDotAppearAnimation(0L);
        }
        if (z3) {
            CharState.cancelAnimator(charState.textAnimator);
            ValueAnimator ofFloat = ValueAnimator.ofFloat(charState.currentTextSizeFactor, 1.0f);
            charState.textAnimator = ofFloat;
            ofFloat.addUpdateListener(charState.mTextSizeUpdater);
            charState.textAnimator.addListener(charState.textFinishListener);
            charState.textAnimator.setInterpolator(passwordTextView.mAppearInterpolator);
            charState.textAnimator.setDuration((long) ((1.0f - charState.currentTextSizeFactor) * 160.0f));
            charState.textAnimator.start();
            charState.textAnimationIsGrowing = true;
            if (charState.textTranslateAnimator == null) {
                ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
                charState.textTranslateAnimator = ofFloat2;
                ofFloat2.addUpdateListener(charState.mTextTranslationUpdater);
                charState.textTranslateAnimator.addListener(charState.textTranslateFinishListener);
                charState.textTranslateAnimator.setInterpolator(passwordTextView.mAppearInterpolator);
                charState.textTranslateAnimator.setDuration(160L);
                charState.textTranslateAnimator.start();
            }
        }
        if (z4) {
            CharState.cancelAnimator(charState.widthAnimator);
            ValueAnimator ofFloat3 = ValueAnimator.ofFloat(charState.currentWidthFactor, 1.0f);
            charState.widthAnimator = ofFloat3;
            ofFloat3.addUpdateListener(charState.mWidthUpdater);
            charState.widthAnimator.addListener(charState.widthFinishListener);
            charState.widthAnimator.setDuration((long) ((1.0f - charState.currentWidthFactor) * 160.0f));
            charState.widthAnimator.start();
            charState.widthAnimationIsGrowing = true;
        }
        if (passwordTextView.mShowPassword) {
            passwordTextView.removeCallbacks(charState.dotSwapperRunnable);
            charState.isDotSwapPending = false;
            passwordTextView.postDelayed(charState.dotSwapperRunnable, 1300L);
            charState.isDotSwapPending = true;
        }
        if (i > 1) {
            CharState charState2 = (CharState) this.mTextChars.get(i - 2);
            if (charState2.isDotSwapPending) {
                CharState.AnonymousClass10 anonymousClass10 = charState2.dotSwapperRunnable;
                PasswordTextView passwordTextView2 = PasswordTextView.this;
                passwordTextView2.removeCallbacks(anonymousClass10);
                charState2.isDotSwapPending = false;
                ValueAnimator valueAnimator = charState2.textAnimator;
                if (valueAnimator == null) {
                    charState2.startTextDisappearAnimation(0L);
                    charState2.startDotAppearAnimation(30L);
                    return;
                }
                long duration = (valueAnimator.getDuration() - charState2.textAnimator.getCurrentPlayTime()) + 100;
                passwordTextView2.removeCallbacks(charState2.dotSwapperRunnable);
                charState2.isDotSwapPending = false;
                passwordTextView2.postDelayed(charState2.dotSwapperRunnable, duration);
                charState2.isDotSwapPending = true;
            }
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        this.mTextHeightRaw = getContext().getResources().getInteger(R.integer.scaled_password_text_size);
    }

    @Override // com.android.keyguard.BaseSecPasswordTextView
    public final void onDelete(int i) {
        ((CharState) this.mTextChars.get(i)).startRemoveAnimation(0L, 0L);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onDraw(Canvas canvas) {
        float width;
        int size = this.mTextChars.size();
        Rect charBounds = getCharBounds();
        int i = charBounds.right - charBounds.left;
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            CharState charState = (CharState) this.mTextChars.get(i3);
            if (i3 != 0) {
                i2 = (int) ((this.mCharPadding * charState.currentWidthFactor) + i2);
            }
            i2 = (int) ((i * charState.currentWidthFactor) + i2);
        }
        float f = i2;
        int i4 = this.mGravity;
        boolean z = true;
        if ((i4 & 7) == 3) {
            width = ((i4 & QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED) == 0 || getLayoutDirection() != 1) ? getPaddingLeft() : (getWidth() - getPaddingRight()) - f;
        } else {
            width = (getWidth() - getPaddingRight()) - f;
            float width2 = (getWidth() / 2.0f) - (f / 2.0f);
            if (width2 > 0.0f) {
                width = width2;
            }
        }
        int size2 = this.mTextChars.size();
        Rect charBounds2 = getCharBounds();
        int i5 = charBounds2.bottom - charBounds2.top;
        float paddingTop = getPaddingTop() + (((getHeight() - getPaddingBottom()) - getPaddingTop()) / 2);
        canvas.clipRect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        float f2 = charBounds2.right - charBounds2.left;
        int i6 = 0;
        while (i6 < size2) {
            CharState charState2 = (CharState) this.mTextChars.get(i6);
            float f3 = charState2.currentTextSizeFactor;
            boolean z2 = f3 > 0.0f ? z : false;
            boolean z3 = charState2.currentDotSizeFactor > 0.0f ? z : false;
            float f4 = charState2.currentWidthFactor * f2;
            PasswordTextView passwordTextView = PasswordTextView.this;
            if (z2) {
                float f5 = i5;
                float f6 = (f5 * charState2.currentTextTranslationY * 0.8f) + ((f5 / 2.0f) * f3) + paddingTop;
                canvas.save();
                canvas.translate((f4 / 2.0f) + width, f6);
                float f7 = charState2.currentTextSizeFactor;
                canvas.scale(f7, f7);
                canvas.drawText(Character.toString(charState2.whichChar), 0.0f, 0.0f, passwordTextView.mDrawPaint);
                canvas.restore();
            }
            if (z3) {
                canvas.save();
                canvas.translate((f4 / 2.0f) + width, paddingTop);
                canvas.drawCircle(0.0f, 0.0f, (passwordTextView.mDotSize / 2) * charState2.currentDotSizeFactor, passwordTextView.mDrawPaint);
                canvas.restore();
            }
            width = DrawerArrowDrawable$$ExternalSyntheticOutline0.m(passwordTextView.mCharPadding, charState2.currentWidthFactor, f4, width);
            i6++;
            z = true;
        }
    }

    @Override // com.android.keyguard.BaseSecPasswordTextView
    public final void onReset(boolean z) {
        if (!z) {
            this.mTextChars.clear();
            return;
        }
        int size = this.mTextChars.size();
        int i = size - 1;
        int i2 = i / 2;
        int i3 = 0;
        while (i3 < size) {
            CharState charState = (CharState) this.mTextChars.get(i3);
            charState.startRemoveAnimation(Math.min((i3 <= i2 ? i3 * 2 : i - (((i3 - i2) - 1) * 2)) * 40, 200L), Math.min(40 * i, 200L) + 160);
            PasswordTextView.this.removeCallbacks(charState.dotSwapperRunnable);
            charState.isDotSwapPending = false;
            i3++;
        }
    }

    @Override // com.android.keyguard.BaseSecPasswordTextView
    public final void onUserActivity() {
        this.mPM.userActivity(SystemClock.uptimeMillis(), false);
        super.onUserActivity();
    }

    public PasswordTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PasswordTextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public PasswordTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTextChars = new ArrayList();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, android.R.styleable.View);
        try {
            boolean z = obtainStyledAttributes.getBoolean(19, true);
            boolean z2 = obtainStyledAttributes.getBoolean(20, true);
            setFocusable(z);
            setFocusableInTouchMode(z2);
            obtainStyledAttributes.recycle();
            obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.PasswordTextView);
            try {
                this.mTextHeightRaw = obtainStyledAttributes.getInt(4, 0);
                this.mGravity = obtainStyledAttributes.getInt(1, 17);
                this.mDotSize = obtainStyledAttributes.getDimensionPixelSize(3, getContext().getResources().getDimensionPixelSize(R.dimen.password_dot_size));
                this.mCharPadding = obtainStyledAttributes.getDimensionPixelSize(2, getContext().getResources().getDimensionPixelSize(R.dimen.password_char_padding));
                this.mDrawPaint.setColor(obtainStyledAttributes.getColor(0, -1));
                obtainStyledAttributes.recycle();
                this.mDrawPaint.setFlags(129);
                this.mDrawPaint.setTextAlign(Paint.Align.CENTER);
                this.mDrawPaint.setTypeface(Typeface.create(context.getString(android.R.string.dynamic_mode_notification_summary_v2), 0));
                this.mAppearInterpolator = AnimationUtils.loadInterpolator(((EditText) this).mContext, android.R.interpolator.linear_out_slow_in);
                this.mDisappearInterpolator = AnimationUtils.loadInterpolator(((EditText) this).mContext, android.R.interpolator.fast_out_linear_in);
                AnimationUtils.loadInterpolator(((EditText) this).mContext, android.R.interpolator.fast_out_slow_in);
                this.mPM = (PowerManager) ((EditText) this).mContext.getSystemService("power");
                setWillNotDraw(false);
            } finally {
            }
        } finally {
        }
    }
}
