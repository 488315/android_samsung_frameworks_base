package com.android.keyguard;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.internal.widget.LockPatternView;
import com.android.keyguard.KeyguardInputView;
import com.android.settingslib.animation.AppearAnimationUtils;
import com.android.settingslib.animation.DisappearAnimationUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardPatternView extends KeyguardInputView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AppearAnimationUtils mAppearAnimationUtils;
    public ConstraintLayout mContainer;
    public final DisappearAnimationUtils mDisappearAnimationUtils;
    public final DisappearAnimationUtils mDisappearAnimationUtilsLocked;
    public View mEcaView;
    public long mLastPokeTime;
    public final Rect mLockPatternScreenBounds;
    public LockPatternView mLockPatternView;
    public BouncerKeyguardMessageArea mSecurityMessageDisplay;
    public final Rect mTempRect;
    public final int[] mTmpPosition;

    public KeyguardPatternView(Context context) {
        this(context, null);
    }

    public final void createAnimation(Object obj, long j, long j2, float f, boolean z, Interpolator interpolator, Runnable runnable) {
        this.mLockPatternView.startCellStateAnimation((LockPatternView.CellState) obj, 1.0f, z ? 1.0f : 0.0f, z ? f : 0.0f, z ? 0.0f : f, z ? 0.0f : 1.0f, 1.0f, j, j2, interpolator, runnable);
        if (runnable != null) {
            this.mAppearAnimationUtils.createAnimation(this.mEcaView, j, j2, f, z, interpolator, (Runnable) null);
        }
    }

    public final void enableClipping(boolean z) {
        setClipChildren(z);
        this.mContainer.setClipToPadding(z);
        this.mContainer.setClipChildren(z);
    }

    @Override // com.android.keyguard.KeyguardInputView
    public CharSequence getTitle() {
        return getResources().getString(R.string.no_recent_tasks);
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mSecurityMessageDisplay = (BouncerKeyguardMessageArea) findViewById(com.android.systemui.R.id.bouncer_message_area);
    }

    @Override // com.android.keyguard.KeyguardInputView, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mLockPatternView = findViewById(com.android.systemui.R.id.lockPatternView);
        this.mEcaView = findViewById(com.android.systemui.R.id.keyguard_selector_fade_container);
        this.mContainer = (ConstraintLayout) findViewById(com.android.systemui.R.id.pattern_container);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mLockPatternView.getLocationOnScreen(this.mTmpPosition);
        Rect rect = this.mLockPatternScreenBounds;
        int i5 = this.mTmpPosition[0];
        rect.set(i5 - 40, r3[1] - 40, this.mLockPatternView.getWidth() + i5 + 40, this.mLockPatternView.getHeight() + this.mTmpPosition[1] + 40);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean onTouchEvent = super.onTouchEvent(motionEvent);
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.mLastPokeTime;
        if (onTouchEvent && elapsedRealtime > 6900) {
            this.mLastPokeTime = SystemClock.elapsedRealtime();
        }
        this.mTempRect.set(0, 0, 0, 0);
        offsetRectIntoDescendantCoords(this.mLockPatternView, this.mTempRect);
        Rect rect = this.mTempRect;
        motionEvent.offsetLocation(rect.left, rect.top);
        boolean z = this.mLockPatternView.dispatchTouchEvent(motionEvent) || onTouchEvent;
        Rect rect2 = this.mTempRect;
        motionEvent.offsetLocation(-rect2.left, -rect2.top);
        return z;
    }

    @Override // com.android.keyguard.KeyguardInputView
    public final void startAppearAnimation() {
        enableClipping(false);
        setAlpha(0.0f);
        setTranslationY(this.mAppearAnimationUtils.mStartTranslation);
        AppearAnimationUtils.startTranslationYAnimation(this, 0L, 500L, 0.0f, this.mAppearAnimationUtils.mInterpolator, new KeyguardInputView.C06871(18));
        this.mLockPatternView.post(new KeyguardPatternView$$ExternalSyntheticLambda1(this, 0));
        if (TextUtils.isEmpty(this.mSecurityMessageDisplay.getText())) {
            return;
        }
        AppearAnimationUtils appearAnimationUtils = this.mAppearAnimationUtils;
        appearAnimationUtils.createAnimation((View) this.mSecurityMessageDisplay, 0L, 220L, appearAnimationUtils.mStartTranslation, true, appearAnimationUtils.mInterpolator, (Runnable) null);
    }

    public KeyguardPatternView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTmpPosition = new int[2];
        this.mTempRect = new Rect();
        this.mLockPatternScreenBounds = new Rect();
        this.mLastPokeTime = -7000L;
        this.mAppearAnimationUtils = new AppearAnimationUtils(context, 220L, 1.5f, 2.0f, AnimationUtils.loadInterpolator(((LinearLayout) this).mContext, R.interpolator.linear_out_slow_in));
        this.mDisappearAnimationUtils = new DisappearAnimationUtils(context, 125L, 1.2f, 0.6f, AnimationUtils.loadInterpolator(((LinearLayout) this).mContext, R.interpolator.fast_out_linear_in));
        this.mDisappearAnimationUtilsLocked = new DisappearAnimationUtils(context, 187L, 1.2f, 0.6f, AnimationUtils.loadInterpolator(((LinearLayout) this).mContext, R.interpolator.fast_out_linear_in));
    }
}
