package com.android.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.core.content.res.ResourcesCompat;
import com.android.app.animation.Interpolators;
import com.android.keyguard.KeyguardClockFrame;
import com.android.systemui.R;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.plugins.ClockController;
import com.android.systemui.plugins.ClockFaceEvents;
import com.android.systemui.shared.clocks.DefaultClockController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardClockSwitch extends RelativeLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    boolean mAnimateOnLayout;
    boolean mChildrenAreLaidOut;
    public ClockController mClock;
    AnimatorSet mClockInAnim;
    AnimatorSet mClockOutAnim;
    public int mClockSwitchYAmount;
    public Integer mDisplayedClockSize;
    public int mDrawAlpha;
    public KeyguardClockFrame mLargeClockFrame;
    public LogBuffer mLogBuffer;
    public KeyguardClockFrame mSmallClockFrame;
    public int mSmartspaceTopOffset;
    public boolean mSplitShadeCentered;
    public KeyguardStatusAreaView mStatusArea;
    AnimatorSet mStatusAreaAnim;
    public float mWeatherClockSmartspaceScaling;
    public int mWeatherClockSmartspaceTranslateX;
    public int mWeatherClockSmartspaceTranslateY;
    public int screenOffsetYPadding;

    public static /* synthetic */ Unit $r8$lambda$in3twPDzk2GPRGUKrayRbeXoBvQ(KeyguardClockSwitch keyguardClockSwitch, Canvas canvas) {
        super.dispatchDraw(canvas);
        return Unit.INSTANCE;
    }

    public KeyguardClockSwitch(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.screenOffsetYPadding = 0;
        this.mWeatherClockSmartspaceScaling = 1.0f;
        this.mWeatherClockSmartspaceTranslateX = 0;
        this.mWeatherClockSmartspaceTranslateY = 0;
        this.mDrawAlpha = 255;
        this.mSplitShadeCentered = false;
        this.mDisplayedClockSize = null;
        this.mClockInAnim = null;
        this.mClockOutAnim = null;
        this.mStatusAreaAnim = null;
        this.mChildrenAreLaidOut = false;
        this.mAnimateOnLayout = true;
        this.mLogBuffer = null;
    }

    public static Rect getLargeClockRegion(ViewGroup viewGroup) {
        int dimensionPixelSize = viewGroup.getResources().getDimensionPixelSize(R.dimen.keyguard_large_clock_top_margin);
        int dimensionPixelSize2 = viewGroup.getResources().getDimensionPixelSize(R.dimen.large_clock_text_size) * 2;
        int height = (dimensionPixelSize / 2) + ((viewGroup.getHeight() / 2) - (dimensionPixelSize2 / 2));
        return new Rect(viewGroup.getLeft(), height, viewGroup.getRight(), dimensionPixelSize2 + height);
    }

    public static Rect getSmallClockRegion(ViewGroup viewGroup) {
        return new Rect(viewGroup.getLeft(), viewGroup.getTop(), viewGroup.getRight(), viewGroup.getTop() + viewGroup.getResources().getDimensionPixelSize(R.dimen.small_clock_text_size));
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        int i = this.mDrawAlpha;
        Function1 function1 = new Function1() { // from class: com.android.keyguard.KeyguardClockSwitch$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyguardClockSwitch.$r8$lambda$in3twPDzk2GPRGUKrayRbeXoBvQ(KeyguardClockSwitch.this, (Canvas) obj);
            }
        };
        KeyguardClockFrame.Companion.getClass();
        KeyguardClockFrame.Companion.saveCanvasAlpha(this, canvas, i, function1);
    }

    public final void onConfigChanged() {
        this.mClockSwitchYAmount = ((RelativeLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.keyguard_clock_switch_y_shift);
        this.mSmartspaceTopOffset = ((RelativeLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.keyguard_smartspace_top_offset);
        Resources resources = ((RelativeLayout) this).mContext.getResources();
        ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
        this.mWeatherClockSmartspaceScaling = resources.getFloat(R.dimen.weather_clock_smartspace_scale);
        this.mWeatherClockSmartspaceTranslateX = ((RelativeLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.weather_clock_smartspace_translateX);
        this.mWeatherClockSmartspaceTranslateY = ((RelativeLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.weather_clock_smartspace_translateY);
        updateStatusArea(false);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mSmallClockFrame = (KeyguardClockFrame) findViewById(R.id.lockscreen_clock_view);
        this.mLargeClockFrame = (KeyguardClockFrame) findViewById(R.id.lockscreen_clock_view_large);
        this.mStatusArea = (KeyguardStatusAreaView) findViewById(R.id.keyguard_status_area);
        onConfigChanged();
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            final int i5 = 0;
            post(new Runnable(this) { // from class: com.android.keyguard.KeyguardClockSwitch$$ExternalSyntheticLambda0
                public final /* synthetic */ KeyguardClockSwitch f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i5) {
                        case 0:
                            KeyguardClockSwitch keyguardClockSwitch = this.f$0;
                            int i6 = KeyguardClockSwitch.$r8$clinit;
                            keyguardClockSwitch.updateClockTargetRegions();
                            break;
                        default:
                            KeyguardClockSwitch keyguardClockSwitch2 = this.f$0;
                            keyguardClockSwitch2.updateClockViews(keyguardClockSwitch2.mDisplayedClockSize.intValue() == 0, keyguardClockSwitch2.mAnimateOnLayout);
                            break;
                    }
                }
            });
        }
        final int i6 = 1;
        if (this.mDisplayedClockSize != null && !this.mChildrenAreLaidOut) {
            post(new Runnable(this) { // from class: com.android.keyguard.KeyguardClockSwitch$$ExternalSyntheticLambda0
                public final /* synthetic */ KeyguardClockSwitch f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i6) {
                        case 0:
                            KeyguardClockSwitch keyguardClockSwitch = this.f$0;
                            int i62 = KeyguardClockSwitch.$r8$clinit;
                            keyguardClockSwitch.updateClockTargetRegions();
                            break;
                        default:
                            KeyguardClockSwitch keyguardClockSwitch2 = this.f$0;
                            keyguardClockSwitch2.updateClockViews(keyguardClockSwitch2.mDisplayedClockSize.intValue() == 0, keyguardClockSwitch2.mAnimateOnLayout);
                            break;
                    }
                }
            });
        }
        this.mChildrenAreLaidOut = true;
    }

    @Override // android.view.View
    public final boolean onSetAlpha(int i) {
        this.mDrawAlpha = i;
        return true;
    }

    public final void updateClockTargetRegions() {
        if (this.mClock != null) {
            if (this.mSmallClockFrame.isLaidOut()) {
                this.mClock.getSmallClock().getEvents().onTargetRegionChanged(getSmallClockRegion(this.mSmallClockFrame));
            }
            if (this.mLargeClockFrame.isLaidOut()) {
                Rect largeClockRegion = getLargeClockRegion(this.mLargeClockFrame);
                ClockController clockController = this.mClock;
                if (clockController instanceof DefaultClockController) {
                    clockController.getLargeClock().getEvents().onTargetRegionChanged(largeClockRegion);
                    return;
                }
                ClockFaceEvents events = clockController.getLargeClock().getEvents();
                int i = largeClockRegion.left;
                int i2 = largeClockRegion.top;
                int i3 = this.screenOffsetYPadding;
                events.onTargetRegionChanged(new Rect(i, i2 - i3, largeClockRegion.right, largeClockRegion.bottom - i3));
            }
        }
    }

    public final void updateClockViews(final boolean z, final boolean z2) {
        KeyguardClockFrame keyguardClockFrame;
        KeyguardClockFrame keyguardClockFrame2;
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        LogBuffer logBuffer = this.mLogBuffer;
        if (logBuffer != null) {
            logBuffer.log("KeyguardClockSwitch", LogLevel.DEBUG, new Function1() { // from class: com.android.keyguard.KeyguardClockSwitch$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    int i = KeyguardClockSwitch.$r8$clinit;
                    KeyguardClockSwitch keyguardClockSwitch = KeyguardClockSwitch.this;
                    keyguardClockSwitch.getClass();
                    logMessage.setBool1(z);
                    logMessage.setBool2(z2);
                    logMessage.setBool3(keyguardClockSwitch.mChildrenAreLaidOut);
                    return Unit.INSTANCE;
                }
            }, new KeyguardClockSwitch$$ExternalSyntheticLambda3());
        }
        AnimatorSet animatorSet = this.mClockInAnim;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        AnimatorSet animatorSet2 = this.mClockOutAnim;
        if (animatorSet2 != null) {
            animatorSet2.cancel();
        }
        AnimatorSet animatorSet3 = this.mStatusAreaAnim;
        if (animatorSet3 != null) {
            animatorSet3.cancel();
        }
        this.mClockInAnim = null;
        this.mClockOutAnim = null;
        this.mStatusAreaAnim = null;
        if (z) {
            keyguardClockFrame2 = this.mSmallClockFrame;
            keyguardClockFrame = this.mLargeClockFrame;
            if (indexOfChild(keyguardClockFrame) == -1) {
                addView(keyguardClockFrame, 0);
            }
            float top = (this.mSmallClockFrame.getTop() - this.mStatusArea.getTop()) + this.mSmartspaceTopOffset;
            ClockController clockController = this.mClock;
            if (clockController == null || !clockController.getLargeClock().getConfig().getHasCustomWeatherDataDisplay()) {
                f6 = 0.0f;
                f7 = 0.0f;
                f8 = 1.0f;
            } else {
                f8 = this.mWeatherClockSmartspaceScaling;
                f6 = this.mWeatherClockSmartspaceTranslateX;
                f7 = this.mWeatherClockSmartspaceTranslateY;
                if (this.mSplitShadeCentered) {
                    f6 *= 1.4f;
                }
            }
            f4 = f7;
            f3 = f6;
            f5 = f8;
            f2 = top;
            f = 0.0f;
        } else {
            keyguardClockFrame = this.mSmallClockFrame;
            keyguardClockFrame2 = this.mLargeClockFrame;
            f = this.mClockSwitchYAmount * (-1.0f);
            removeView(keyguardClockFrame2);
            f2 = 0.0f;
            f3 = 0.0f;
            f4 = 0.0f;
            f5 = 1.0f;
        }
        if (!z2) {
            keyguardClockFrame2.setAlpha(0.0f);
            keyguardClockFrame2.setTranslationY(f);
            keyguardClockFrame.setAlpha(1.0f);
            keyguardClockFrame.setTranslationY(0.0f);
            keyguardClockFrame.setVisibility(0);
            this.mStatusArea.setScaleX(f5);
            this.mStatusArea.setScaleY(f5);
            KeyguardStatusAreaView keyguardStatusAreaView = this.mStatusArea;
            keyguardStatusAreaView.translateXFromClockDesign = f3;
            keyguardStatusAreaView.setTranslationX(keyguardStatusAreaView.translateXFromAod + f3 + keyguardStatusAreaView.translateXFromUnfold);
            KeyguardStatusAreaView keyguardStatusAreaView2 = this.mStatusArea;
            keyguardStatusAreaView2.translateYFromClockDesign = f4;
            keyguardStatusAreaView2.setTranslationY(f4 + keyguardStatusAreaView2.translateYFromClockSize);
            KeyguardStatusAreaView keyguardStatusAreaView3 = this.mStatusArea;
            keyguardStatusAreaView3.translateYFromClockSize = f2;
            keyguardStatusAreaView3.setTranslationY(keyguardStatusAreaView3.translateYFromClockDesign + f2);
            this.mSmallClockFrame.setTranslationY(f2);
            return;
        }
        AnimatorSet animatorSet4 = new AnimatorSet();
        this.mClockOutAnim = animatorSet4;
        animatorSet4.setDuration(133L);
        this.mClockOutAnim.setInterpolator(Interpolators.LINEAR);
        this.mClockOutAnim.playTogether(ObjectAnimator.ofFloat(keyguardClockFrame2, (Property<KeyguardClockFrame, Float>) RelativeLayout.ALPHA, 0.0f), ObjectAnimator.ofFloat(keyguardClockFrame2, (Property<KeyguardClockFrame, Float>) RelativeLayout.TRANSLATION_Y, f));
        this.mClockOutAnim.addListener(new AnimatorListenerAdapter() { // from class: com.android.keyguard.KeyguardClockSwitch.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardClockSwitch.this.mClockOutAnim = null;
            }
        });
        keyguardClockFrame.setVisibility(0);
        AnimatorSet animatorSet5 = new AnimatorSet();
        this.mClockInAnim = animatorSet5;
        animatorSet5.setDuration(167L);
        this.mClockInAnim.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
        this.mClockInAnim.playTogether(ObjectAnimator.ofFloat(keyguardClockFrame, (Property<KeyguardClockFrame, Float>) RelativeLayout.ALPHA, 1.0f), ObjectAnimator.ofFloat(keyguardClockFrame, (Property<KeyguardClockFrame, Float>) RelativeLayout.TRANSLATION_Y, 0.0f));
        this.mClockInAnim.setStartDelay(133L);
        this.mClockInAnim.addListener(new AnimatorListenerAdapter() { // from class: com.android.keyguard.KeyguardClockSwitch.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardClockSwitch.this.mClockInAnim = null;
            }
        });
        AnimatorSet animatorSet6 = new AnimatorSet();
        this.mStatusAreaAnim = animatorSet6;
        animatorSet6.setStartDelay(0L);
        this.mStatusAreaAnim.setDuration(z ? 967L : 467L);
        this.mStatusAreaAnim.setInterpolator(Interpolators.EMPHASIZED);
        this.mStatusAreaAnim.playTogether(ObjectAnimator.ofFloat(this.mStatusArea, (Property<KeyguardStatusAreaView, Float>) KeyguardStatusAreaView.TRANSLATE_Y_CLOCK_SIZE.val$property, f2), ObjectAnimator.ofFloat(this.mSmallClockFrame, (Property<KeyguardClockFrame, Float>) RelativeLayout.TRANSLATION_Y, f2), ObjectAnimator.ofFloat(this.mStatusArea, (Property<KeyguardStatusAreaView, Float>) RelativeLayout.SCALE_X, f5), ObjectAnimator.ofFloat(this.mStatusArea, (Property<KeyguardStatusAreaView, Float>) RelativeLayout.SCALE_Y, f5), ObjectAnimator.ofFloat(this.mStatusArea, (Property<KeyguardStatusAreaView, Float>) KeyguardStatusAreaView.TRANSLATE_X_CLOCK_DESIGN.val$property, f3), ObjectAnimator.ofFloat(this.mStatusArea, (Property<KeyguardStatusAreaView, Float>) KeyguardStatusAreaView.TRANSLATE_Y_CLOCK_DESIGN.val$property, f4));
        this.mStatusAreaAnim.addListener(new AnimatorListenerAdapter() { // from class: com.android.keyguard.KeyguardClockSwitch.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardClockSwitch.this.mStatusAreaAnim = null;
            }
        });
        this.mClockInAnim.start();
        this.mClockOutAnim.start();
        this.mStatusAreaAnim.start();
    }

    public final void updateStatusArea(boolean z) {
        Integer num = this.mDisplayedClockSize;
        if (num == null || !this.mChildrenAreLaidOut) {
            return;
        }
        updateClockViews(num.intValue() == 0, z);
    }
}
