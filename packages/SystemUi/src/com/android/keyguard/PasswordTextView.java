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
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.EditText;
import androidx.constraintlayout.motion.widget.MotionPaths$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.R$styleable;
import com.android.systemui.widget.SystemUIEditText;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.sec.ims.configuration.DATA;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class PasswordTextView extends SystemUIEditText {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Interpolator mAppearInterpolator;
    public final int mCharPadding;
    public final Interpolator mDisappearInterpolator;
    public final int mDotSize;
    public final int mGravity;
    public final PowerManager mPM;
    public boolean mShowPassword;
    public String mText;
    public final ArrayList mTextChars;
    public int mTextHeightRaw;
    public UserActivityListener mUserActivityListener;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CharState {
        public float currentDotSizeFactor;
        public float currentTextSizeFactor;
        public float currentTextTranslationY;
        public float currentWidthFactor;
        public boolean dotAnimationIsGrowing;
        public Animator dotAnimator;
        public final C08292 dotFinishListener;
        public final C08336 dotSizeUpdater;
        public final RunnableC082810 dotSwapperRunnable;
        public boolean isDotSwapPending;
        public final C08271 removeEndListener;
        public boolean textAnimationIsGrowing;
        public ValueAnimator textAnimator;
        public final C08303 textFinishListener;
        public final C08347 textSizeUpdater;
        public ValueAnimator textTranslateAnimator;
        public final C08314 textTranslateFinishListener;
        public final C08358 textTranslationUpdater;
        public char whichChar;
        public boolean widthAnimationIsGrowing;
        public ValueAnimator widthAnimator;
        public final C08325 widthFinishListener;
        public final C08369 widthUpdater;

        public /* synthetic */ CharState(PasswordTextView passwordTextView, int i) {
            this();
        }

        public static void cancelAnimator(Animator animator) {
            if (animator != null) {
                animator.cancel();
            }
        }

        public final boolean isCharVisibleForA11y() {
            return this.currentTextSizeFactor > 0.0f || (this.textAnimator != null && this.textAnimationIsGrowing);
        }

        public final void startDotAppearAnimation(long j) {
            cancelAnimator(this.dotAnimator);
            PasswordTextView passwordTextView = PasswordTextView.this;
            boolean z = passwordTextView.mShowPassword;
            C08292 c08292 = this.dotFinishListener;
            C08336 c08336 = this.dotSizeUpdater;
            if (z) {
                ValueAnimator ofFloat = ValueAnimator.ofFloat(this.currentDotSizeFactor, 1.0f);
                ofFloat.addUpdateListener(c08336);
                ofFloat.setDuration((long) ((1.0f - this.currentDotSizeFactor) * 160.0f));
                ofFloat.addListener(c08292);
                ofFloat.setStartDelay(j);
                ofFloat.start();
                this.dotAnimator = ofFloat;
            } else {
                ValueAnimator ofFloat2 = ValueAnimator.ofFloat(this.currentDotSizeFactor, 1.5f);
                ofFloat2.addUpdateListener(c08336);
                ofFloat2.setInterpolator(passwordTextView.mAppearInterpolator);
                ofFloat2.setDuration(160L);
                ValueAnimator ofFloat3 = ValueAnimator.ofFloat(1.5f, 1.0f);
                ofFloat3.addUpdateListener(c08336);
                ofFloat3.setDuration(160L);
                ofFloat3.addListener(c08292);
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
                ofFloat.addUpdateListener(this.dotSizeUpdater);
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
                ofFloat2.addUpdateListener(this.widthUpdater);
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
            ofFloat.addUpdateListener(this.textSizeUpdater);
            this.textAnimator.addListener(this.textFinishListener);
            this.textAnimator.setInterpolator(PasswordTextView.this.mDisappearInterpolator);
            this.textAnimator.setDuration((long) (this.currentTextSizeFactor * 160.0f));
            this.textAnimator.setStartDelay(j);
            this.textAnimator.start();
            this.textAnimationIsGrowing = false;
        }

        /* JADX WARN: Type inference failed for: r1v10, types: [com.android.keyguard.PasswordTextView$CharState$9] */
        /* JADX WARN: Type inference failed for: r1v11, types: [com.android.keyguard.PasswordTextView$CharState$10] */
        /* JADX WARN: Type inference failed for: r1v2, types: [com.android.keyguard.PasswordTextView$CharState$1] */
        /* JADX WARN: Type inference failed for: r1v3, types: [com.android.keyguard.PasswordTextView$CharState$2] */
        /* JADX WARN: Type inference failed for: r1v4, types: [com.android.keyguard.PasswordTextView$CharState$3] */
        /* JADX WARN: Type inference failed for: r1v5, types: [com.android.keyguard.PasswordTextView$CharState$4] */
        /* JADX WARN: Type inference failed for: r1v6, types: [com.android.keyguard.PasswordTextView$CharState$5] */
        /* JADX WARN: Type inference failed for: r1v7, types: [com.android.keyguard.PasswordTextView$CharState$6] */
        /* JADX WARN: Type inference failed for: r1v8, types: [com.android.keyguard.PasswordTextView$CharState$7] */
        /* JADX WARN: Type inference failed for: r1v9, types: [com.android.keyguard.PasswordTextView$CharState$8] */
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
            this.dotSizeUpdater = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.PasswordTextView.CharState.6
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    CharState.this.currentDotSizeFactor = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    PasswordTextView.this.invalidate();
                }
            };
            this.textSizeUpdater = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.PasswordTextView.CharState.7
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    boolean isCharVisibleForA11y = CharState.this.isCharVisibleForA11y();
                    CharState charState = CharState.this;
                    float f = charState.currentTextSizeFactor;
                    charState.currentTextSizeFactor = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    if (isCharVisibleForA11y != CharState.this.isCharVisibleForA11y()) {
                        CharState charState2 = CharState.this;
                        charState2.currentTextSizeFactor = f;
                        PasswordTextView passwordTextView = PasswordTextView.this;
                        int i = PasswordTextView.$r8$clinit;
                        CharSequence transformedText = passwordTextView.getTransformedText();
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
            this.textTranslationUpdater = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.PasswordTextView.CharState.8
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    CharState.this.currentTextTranslationY = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    PasswordTextView.this.invalidate();
                }
            };
            this.widthUpdater = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.PasswordTextView.CharState.9
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface UserActivityListener {
        void onUserActivity();
    }

    public PasswordTextView(Context context) {
        this(context, null);
    }

    public void append(char c) {
        CharState charState;
        int size = this.mTextChars.size();
        CharSequence transformedText = getTransformedText();
        String str = this.mText + c;
        this.mText = str;
        int length = str.length();
        int i = 0;
        if (length > size) {
            charState = new CharState(this, i);
            charState.whichChar = c;
            this.mTextChars.add(charState);
        } else {
            charState = (CharState) this.mTextChars.get(length - 1);
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
            ofFloat.addUpdateListener(charState.textSizeUpdater);
            charState.textAnimator.addListener(charState.textFinishListener);
            charState.textAnimator.setInterpolator(passwordTextView.mAppearInterpolator);
            charState.textAnimator.setDuration((long) ((1.0f - charState.currentTextSizeFactor) * 160.0f));
            charState.textAnimator.start();
            charState.textAnimationIsGrowing = true;
            if (charState.textTranslateAnimator == null) {
                ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
                charState.textTranslateAnimator = ofFloat2;
                ofFloat2.addUpdateListener(charState.textTranslationUpdater);
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
            ofFloat3.addUpdateListener(charState.widthUpdater);
            charState.widthAnimator.addListener(charState.widthFinishListener);
            charState.widthAnimator.setDuration((long) ((1.0f - charState.currentWidthFactor) * 160.0f));
            charState.widthAnimator.start();
            charState.widthAnimationIsGrowing = true;
        }
        if (passwordTextView.mShowPassword) {
            PasswordTextView passwordTextView2 = PasswordTextView.this;
            CharState.RunnableC082810 runnableC082810 = charState.dotSwapperRunnable;
            passwordTextView2.removeCallbacks(runnableC082810);
            charState.isDotSwapPending = false;
            passwordTextView2.postDelayed(runnableC082810, 1300L);
            charState.isDotSwapPending = true;
        }
        if (length > 1) {
            CharState charState2 = (CharState) this.mTextChars.get(length - 2);
            if (charState2.isDotSwapPending) {
                PasswordTextView passwordTextView3 = PasswordTextView.this;
                CharState.RunnableC082810 runnableC0828102 = charState2.dotSwapperRunnable;
                passwordTextView3.removeCallbacks(runnableC0828102);
                charState2.isDotSwapPending = false;
                ValueAnimator valueAnimator = charState2.textAnimator;
                if (valueAnimator != null) {
                    long duration = (valueAnimator.getDuration() - charState2.textAnimator.getCurrentPlayTime()) + 100;
                    passwordTextView3.removeCallbacks(runnableC0828102);
                    charState2.isDotSwapPending = false;
                    passwordTextView3.postDelayed(runnableC0828102, duration);
                    charState2.isDotSwapPending = true;
                } else {
                    charState2.startTextDisappearAnimation(0L);
                    charState2.startDotAppearAnimation(30L);
                }
            }
        }
        this.mPM.userActivity(SystemClock.uptimeMillis(), false);
        UserActivityListener userActivityListener = this.mUserActivityListener;
        if (userActivityListener != null) {
            userActivityListener.onUserActivity();
        }
        sendAccessibilityEventTypeViewTextChanged(transformedText, ((StringBuilder) transformedText).length(), 0, 1);
    }

    public final void deleteLastChar() {
        int length = this.mText.length();
        CharSequence transformedText = getTransformedText();
        if (length > 0) {
            int i = length - 1;
            this.mText = this.mText.substring(0, i);
            ((CharState) this.mTextChars.get(i)).startRemoveAnimation(0L, 0L);
            sendAccessibilityEventTypeViewTextChanged(transformedText, ((StringBuilder) transformedText).length() - 1, 1, 0);
        }
        this.mPM.userActivity(SystemClock.uptimeMillis(), false);
        UserActivityListener userActivityListener = this.mUserActivityListener;
        if (userActivityListener != null) {
            userActivityListener.onUserActivity();
        }
    }

    public Rect getCharBounds() {
        this.mDrawPaint.setTextSize(this.mTextHeightRaw * getResources().getDisplayMetrics().scaledDensity);
        Rect rect = new Rect();
        this.mDrawPaint.getTextBounds(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, 0, 1, rect);
        return rect;
    }

    public final CharSequence getTransformedText() {
        int size = this.mTextChars.size();
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            CharState charState = (CharState) this.mTextChars.get(i);
            if (charState.dotAnimator == null || charState.dotAnimationIsGrowing) {
                sb.append(charState.isCharVisibleForA11y() ? charState.whichChar : (char) 8226);
            }
        }
        return sb;
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        this.mTextHeightRaw = getContext().getResources().getInteger(R.integer.scaled_password_text_size);
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
            if (charState2.currentDotSizeFactor <= 0.0f) {
                z = false;
            }
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
            if (z) {
                canvas.save();
                canvas.translate((f4 / 2.0f) + width, paddingTop);
                canvas.drawCircle(0.0f, 0.0f, (passwordTextView.mDotSize / 2) * charState2.currentDotSizeFactor, passwordTextView.mDrawPaint);
                canvas.restore();
            }
            width = MotionPaths$$ExternalSyntheticOutline0.m24m(passwordTextView.mCharPadding, charState2.currentWidthFactor, f4, width);
            i6++;
            z = true;
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(EditText.class.getName());
        accessibilityEvent.setPassword(true);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(EditText.class.getName());
        accessibilityNodeInfo.setPassword(true);
        accessibilityNodeInfo.setText(getTransformedText());
        accessibilityNodeInfo.setEditable(true);
        accessibilityNodeInfo.setInputType(16);
    }

    public void reset(boolean z, boolean z2) {
        CharSequence transformedText = getTransformedText();
        this.mText = "";
        int size = this.mTextChars.size();
        int i = size - 1;
        int i2 = i / 2;
        int i3 = 0;
        while (i3 < size) {
            CharState charState = (CharState) this.mTextChars.get(i3);
            if (z) {
                charState.startRemoveAnimation(Math.min((i3 <= i2 ? i3 * 2 : i - (((i3 - i2) - 1) * 2)) * 40, 200L), Math.min(i * 40, 200L) + 160);
                PasswordTextView.this.removeCallbacks(charState.dotSwapperRunnable);
                charState.isDotSwapPending = false;
            }
            i3++;
        }
        if (!z) {
            this.mTextChars.clear();
        }
        if (z2) {
            sendAccessibilityEventTypeViewTextChanged(transformedText, 0, ((StringBuilder) transformedText).length(), 0);
        }
    }

    public final void sendAccessibilityEventTypeViewTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (AccessibilityManager.getInstance(((EditText) this).mContext).isTouchExplorationEnabled() && isShown()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain(16);
            obtain.setFromIndex(i);
            obtain.setRemovedCount(i2);
            obtain.setAddedCount(i3);
            obtain.setBeforeText(charSequence);
            CharSequence transformedText = getTransformedText();
            if (!TextUtils.isEmpty(transformedText)) {
                obtain.getText().add(transformedText);
            }
            obtain.setPassword(true);
            sendAccessibilityEventUnchecked(obtain);
        }
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
        this.mText = "";
        this.mShowPassword = true;
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
                this.mDrawPaint.setTypeface(Typeface.create(context.getString(android.R.string.dream_accessibility_action_click), 0));
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
