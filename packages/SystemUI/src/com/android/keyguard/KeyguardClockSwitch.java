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
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.core.content.res.ResourcesCompat;
import com.android.app.animation.Interpolators;
import com.android.keyguard.KeyguardClockFrame;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.keyguard.MigrateClocksToBlueprint;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockFaceEvents;
import com.android.systemui.shared.clocks.DefaultClockController;
import java.io.PrintWriter;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    public View mSmartspace;
    public int mSmartspaceTop;
    public int mSmartspaceTopOffset;
    public boolean mSplitShadeCentered;
    public KeyguardStatusAreaView mStatusArea;
    AnimatorSet mStatusAreaAnim;
    public int mStatusBarHeight;
    public float mWeatherClockSmartspaceScaling;
    public int mWeatherClockSmartspaceTranslateX;
    public int mWeatherClockSmartspaceTranslateY;
    public int screenOffsetYPadding;

    public static /* synthetic */ Unit $r8$lambda$3P88DAVnKHu_rRbO61gA5cZYa4g(KeyguardClockSwitch keyguardClockSwitch, Canvas canvas) {
        super.dispatchDraw(canvas);
        return Unit.INSTANCE;
    }

    public KeyguardClockSwitch(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.screenOffsetYPadding = 0;
        this.mSmartspaceTop = -1;
        this.mWeatherClockSmartspaceScaling = 1.0f;
        this.mWeatherClockSmartspaceTranslateX = 0;
        this.mWeatherClockSmartspaceTranslateY = 0;
        this.mDrawAlpha = 255;
        this.mStatusBarHeight = 0;
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
                return KeyguardClockSwitch.$r8$lambda$3P88DAVnKHu_rRbO61gA5cZYa4g(KeyguardClockSwitch.this, (Canvas) obj);
            }
        };
        KeyguardClockFrame.Companion.getClass();
        KeyguardClockFrame.Companion.saveCanvasAlpha(this, canvas, i, function1);
    }

    public final void dump(PrintWriter printWriter) {
        StringBuilder m = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "KeyguardClockSwitch:", "  mSmallClockFrame = ");
        m.append(this.mSmallClockFrame);
        printWriter.println(m.toString());
        if (this.mSmallClockFrame != null) {
            printWriter.println("  mSmallClockFrame.alpha = " + this.mSmallClockFrame.getAlpha());
        }
        printWriter.println("  mLargeClockFrame = " + this.mLargeClockFrame);
        if (this.mLargeClockFrame != null) {
            printWriter.println("  mLargeClockFrame.alpha = " + this.mLargeClockFrame.getAlpha());
        }
        printWriter.println("  mStatusArea = " + this.mStatusArea);
        printWriter.println("  mDisplayedClockSize = " + this.mDisplayedClockSize);
    }

    public final void onConfigChanged() {
        this.mClockSwitchYAmount = ((RelativeLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.keyguard_clock_switch_y_shift);
        this.mSmartspaceTopOffset = (int) (((((RelativeLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.keyguard_smartspace_top_offset) * ((RelativeLayout) this).mContext.getResources().getConfiguration().fontScale) / ((RelativeLayout) this).mContext.getResources().getDisplayMetrics().density) * 2.625f);
        Resources resources = ((RelativeLayout) this).mContext.getResources();
        ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
        this.mWeatherClockSmartspaceScaling = resources.getFloat(R.dimen.weather_clock_smartspace_scale);
        this.mWeatherClockSmartspaceTranslateX = ((RelativeLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.weather_clock_smartspace_translateX);
        this.mWeatherClockSmartspaceTranslateY = ((RelativeLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.weather_clock_smartspace_translateY);
        this.mStatusBarHeight = ((RelativeLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.status_bar_height);
        updateStatusArea(false);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        this.mSmallClockFrame = (KeyguardClockFrame) findViewById(R.id.lockscreen_clock_view);
        this.mLargeClockFrame = (KeyguardClockFrame) findViewById(R.id.lockscreen_clock_view_large);
        this.mStatusArea = (KeyguardStatusAreaView) findViewById(R.id.keyguard_status_area);
        onConfigChanged();
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        ClockController clockController = this.mClock;
        if (clockController != null && clockController.getConfig().getId().equals("DIGITAL_CLOCK_METRO")) {
            this.mClock.getEvents().onColorPaletteChanged(((RelativeLayout) this).mContext.getResources());
        }
        if (z) {
            final int i5 = 2;
            post(new Runnable(this) { // from class: com.android.keyguard.KeyguardClockSwitch$$ExternalSyntheticLambda3
                public final /* synthetic */ KeyguardClockSwitch f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i6 = i5;
                    KeyguardClockSwitch keyguardClockSwitch = this.f$0;
                    switch (i6) {
                        case 0:
                            keyguardClockSwitch.updateClockViews(keyguardClockSwitch.mDisplayedClockSize.intValue() == 0, keyguardClockSwitch.mAnimateOnLayout);
                            break;
                        case 1:
                            keyguardClockSwitch.updateClockViews(keyguardClockSwitch.mDisplayedClockSize.intValue() == 0, keyguardClockSwitch.mAnimateOnLayout);
                            break;
                        default:
                            int i7 = KeyguardClockSwitch.$r8$clinit;
                            keyguardClockSwitch.updateClockTargetRegions();
                            break;
                    }
                }
            });
        }
        View view = this.mSmartspace;
        if (view != null && this.mSmartspaceTop != view.getTop() && this.mDisplayedClockSize != null) {
            this.mSmartspaceTop = this.mSmartspace.getTop();
            final int i6 = 0;
            post(new Runnable(this) { // from class: com.android.keyguard.KeyguardClockSwitch$$ExternalSyntheticLambda3
                public final /* synthetic */ KeyguardClockSwitch f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i62 = i6;
                    KeyguardClockSwitch keyguardClockSwitch = this.f$0;
                    switch (i62) {
                        case 0:
                            keyguardClockSwitch.updateClockViews(keyguardClockSwitch.mDisplayedClockSize.intValue() == 0, keyguardClockSwitch.mAnimateOnLayout);
                            break;
                        case 1:
                            keyguardClockSwitch.updateClockViews(keyguardClockSwitch.mDisplayedClockSize.intValue() == 0, keyguardClockSwitch.mAnimateOnLayout);
                            break;
                        default:
                            int i7 = KeyguardClockSwitch.$r8$clinit;
                            keyguardClockSwitch.updateClockTargetRegions();
                            break;
                    }
                }
            });
        }
        if (this.mDisplayedClockSize != null && !this.mChildrenAreLaidOut) {
            final int i7 = 1;
            post(new Runnable(this) { // from class: com.android.keyguard.KeyguardClockSwitch$$ExternalSyntheticLambda3
                public final /* synthetic */ KeyguardClockSwitch f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i62 = i7;
                    KeyguardClockSwitch keyguardClockSwitch = this.f$0;
                    switch (i62) {
                        case 0:
                            keyguardClockSwitch.updateClockViews(keyguardClockSwitch.mDisplayedClockSize.intValue() == 0, keyguardClockSwitch.mAnimateOnLayout);
                            break;
                        case 1:
                            keyguardClockSwitch.updateClockViews(keyguardClockSwitch.mDisplayedClockSize.intValue() == 0, keyguardClockSwitch.mAnimateOnLayout);
                            break;
                        default:
                            int i72 = KeyguardClockSwitch.$r8$clinit;
                            keyguardClockSwitch.updateClockTargetRegions();
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
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        if (this.mClock != null) {
            if (this.mSmallClockFrame.isLaidOut()) {
                this.mClock.getSmallClock().getEvents().onTargetRegionChanged(getSmallClockRegion(this.mSmallClockFrame));
            }
            if (this.mLargeClockFrame.isLaidOut()) {
                Rect largeClockRegion = getLargeClockRegion(this.mLargeClockFrame);
                ClockController clockController = this.mClock;
                if (clockController instanceof DefaultClockController) {
                    ((DefaultClockController) clockController).largeClock.events.onTargetRegionChanged(largeClockRegion);
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

    public final void updateClockViews(boolean z, boolean z2) {
        KeyguardClockFrame keyguardClockFrame;
        final KeyguardClockFrame keyguardClockFrame2;
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
            LogMessage obtain = logBuffer.obtain("KeyguardClockSwitch", LogLevel.DEBUG, new KeyguardClockSwitch$$ExternalSyntheticLambda0(), null);
            ((LogMessageImpl) obtain).bool1 = z;
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.bool2 = z2;
            logMessageImpl.bool3 = this.mChildrenAreLaidOut;
            Unit unit = Unit.INSTANCE;
            logBuffer.commit(obtain);
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
                f6 = 1.0f;
                f7 = 0.0f;
                f8 = 0.0f;
            } else {
                f6 = this.mWeatherClockSmartspaceScaling;
                f7 = this.mWeatherClockSmartspaceTranslateX;
                if (this.mSplitShadeCentered) {
                    f7 *= 1.4f;
                }
                f8 = ((((this.mStatusBarHeight - (this.mSmallClockFrame.getHeight() * 0.6f)) - this.mSmartspaceTop) - this.screenOffsetYPadding) - top) + this.mWeatherClockSmartspaceTranslateY;
            }
            f5 = f8;
            f4 = f7;
            f2 = f6;
            f3 = top;
            f = 0.0f;
        } else {
            keyguardClockFrame = this.mSmallClockFrame;
            keyguardClockFrame2 = this.mLargeClockFrame;
            f = this.mClockSwitchYAmount * (-1.0f);
            removeView(keyguardClockFrame2);
            f2 = 1.0f;
            f3 = 0.0f;
            f4 = 0.0f;
            f5 = 0.0f;
        }
        if (!z2) {
            keyguardClockFrame2.setAlpha(0.0f);
            keyguardClockFrame2.setTranslationY(f);
            keyguardClockFrame2.setVisibility(4);
            keyguardClockFrame.setAlpha(1.0f);
            keyguardClockFrame.setTranslationY(0.0f);
            keyguardClockFrame.setVisibility(0);
            this.mStatusArea.setScaleX(f2);
            this.mStatusArea.setScaleY(f2);
            KeyguardStatusAreaView keyguardStatusAreaView = this.mStatusArea;
            keyguardStatusAreaView.translateXFromClockDesign = f4;
            keyguardStatusAreaView.setTranslationX(keyguardStatusAreaView.translateXFromAod + f4 + keyguardStatusAreaView.translateXFromUnfold);
            KeyguardStatusAreaView keyguardStatusAreaView2 = this.mStatusArea;
            keyguardStatusAreaView2.translateYFromClockDesign = f5;
            keyguardStatusAreaView2.setTranslationY(f5 + keyguardStatusAreaView2.translateYFromClockSize);
            KeyguardStatusAreaView keyguardStatusAreaView3 = this.mStatusArea;
            keyguardStatusAreaView3.translateYFromClockSize = f3;
            keyguardStatusAreaView3.setTranslationY(keyguardStatusAreaView3.translateYFromClockDesign + f3);
            this.mSmallClockFrame.setTranslationY(f3);
            return;
        }
        AnimatorSet animatorSet4 = new AnimatorSet();
        this.mClockOutAnim = animatorSet4;
        animatorSet4.setDuration(133L);
        this.mClockOutAnim.setInterpolator(Interpolators.LINEAR);
        AnimatorSet animatorSet5 = this.mClockOutAnim;
        Property property = RelativeLayout.ALPHA;
        Property property2 = RelativeLayout.TRANSLATION_Y;
        animatorSet5.playTogether(ObjectAnimator.ofFloat(keyguardClockFrame2, (Property<KeyguardClockFrame, Float>) property, 0.0f), ObjectAnimator.ofFloat(keyguardClockFrame2, (Property<KeyguardClockFrame, Float>) property2, f));
        this.mClockOutAnim.addListener(new AnimatorListenerAdapter() { // from class: com.android.keyguard.KeyguardClockSwitch.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (KeyguardClockSwitch.this.mClockOutAnim == animator) {
                    keyguardClockFrame2.setVisibility(4);
                    KeyguardClockSwitch.this.mClockOutAnim = null;
                }
            }
        });
        keyguardClockFrame.setVisibility(0);
        AnimatorSet animatorSet6 = new AnimatorSet();
        this.mClockInAnim = animatorSet6;
        animatorSet6.setDuration(167L);
        this.mClockInAnim.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
        this.mClockInAnim.playTogether(ObjectAnimator.ofFloat(keyguardClockFrame, (Property<KeyguardClockFrame, Float>) property, 1.0f), ObjectAnimator.ofFloat(keyguardClockFrame, (Property<KeyguardClockFrame, Float>) property2, 0.0f));
        this.mClockInAnim.setStartDelay(133L);
        this.mClockInAnim.addListener(new AnimatorListenerAdapter() { // from class: com.android.keyguard.KeyguardClockSwitch.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardClockSwitch keyguardClockSwitch = KeyguardClockSwitch.this;
                if (keyguardClockSwitch.mClockInAnim == animator) {
                    keyguardClockSwitch.mClockInAnim = null;
                }
            }
        });
        AnimatorSet animatorSet7 = new AnimatorSet();
        this.mStatusAreaAnim = animatorSet7;
        animatorSet7.setStartDelay(0L);
        this.mStatusAreaAnim.setDuration(z ? 967L : 467L);
        this.mStatusAreaAnim.setInterpolator(Interpolators.EMPHASIZED);
        this.mStatusAreaAnim.playTogether(ObjectAnimator.ofFloat(this.mStatusArea, (Property<KeyguardStatusAreaView, Float>) KeyguardStatusAreaView.TRANSLATE_Y_CLOCK_SIZE.val$property, f3), ObjectAnimator.ofFloat(this.mSmallClockFrame, (Property<KeyguardClockFrame, Float>) property2, f3), ObjectAnimator.ofFloat(this.mStatusArea, (Property<KeyguardStatusAreaView, Float>) RelativeLayout.SCALE_X, f2), ObjectAnimator.ofFloat(this.mStatusArea, (Property<KeyguardStatusAreaView, Float>) RelativeLayout.SCALE_Y, f2), ObjectAnimator.ofFloat(this.mStatusArea, (Property<KeyguardStatusAreaView, Float>) KeyguardStatusAreaView.TRANSLATE_X_CLOCK_DESIGN.val$property, f4), ObjectAnimator.ofFloat(this.mStatusArea, (Property<KeyguardStatusAreaView, Float>) KeyguardStatusAreaView.TRANSLATE_Y_CLOCK_DESIGN.val$property, f5));
        this.mStatusAreaAnim.addListener(new AnimatorListenerAdapter() { // from class: com.android.keyguard.KeyguardClockSwitch.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardClockSwitch keyguardClockSwitch = KeyguardClockSwitch.this;
                if (keyguardClockSwitch.mStatusAreaAnim == animator) {
                    keyguardClockSwitch.mStatusAreaAnim = null;
                }
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
