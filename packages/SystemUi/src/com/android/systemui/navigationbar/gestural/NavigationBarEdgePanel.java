package com.android.systemui.navigationbar.gestural;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.SystemClock;
import android.util.MathUtils;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import androidx.core.graphics.ColorUtils;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.app.animation.Interpolators;
import com.android.internal.util.LatencyTracker;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.keyguard.animator.ActionUpOrCancelHandler$$ExternalSyntheticOutline0;
import com.android.systemui.navigationbar.SamsungNavigationBarProxy;
import com.android.systemui.navigationbar.util.OneHandModeUtil;
import com.android.systemui.plugins.NavigationEdgeBackPlugin;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.shared.navigationbar.RegionSamplingHelper;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.SettingsHelper;
import java.io.PrintWriter;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NavigationBarEdgePanel extends View implements NavigationEdgeBackPlugin {
    public static final float ARROW_ANGLE_WHEN_EXTENDED_DEGREES;
    public static final float ARROW_LENGTH_DP;
    public static final float ARROW_THICKNESS_DP;
    public static final C18902 CURRENT_ANGLE;
    public static final C18913 CURRENT_TRANSLATION;
    public static final C18924 CURRENT_VERTICAL_TRANSLATION;
    public static final Interpolator RUBBER_BAND_INTERPOLATOR;
    public static final Interpolator RUBBER_BAND_INTERPOLATOR_APPEAR;
    public final SpringAnimation mAngleAnimation;
    public final SpringForce mAngleAppearForce;
    public final SpringForce mAngleDisappearForce;
    public float mAngleOffset;
    public int mArrowColor;
    public final ValueAnimator mArrowColorAnimator;
    public int mArrowColorDark;
    public int mArrowColorLight;
    public final ValueAnimator mArrowDisappearAnimation;
    public final float mArrowLength;
    public int mArrowPaddingEnd;
    public final Path mArrowPath;
    public int mArrowStartColor;
    public final float mArrowThickness;
    public boolean mArrowsPointLeft;
    public NavigationEdgeBackPlugin.BackCallback mBackCallback;
    public final float mBaseTranslation;
    public float mCurrentAngle;
    public int mCurrentArrowColor;
    public float mCurrentTranslation;
    public final float mDensity;
    public float mDesiredAngle;
    public float mDesiredTranslation;
    public float mDesiredVerticalTranslation;
    public float mDisappearAmount;
    public final Point mDisplaySize;
    public boolean mDragSlopPassed;
    public final NavigationBarEdgePanel$$ExternalSyntheticLambda0 mFailsafeRunnable;
    public int mFingerOffset;
    public final Handler mHandler;
    public boolean mIsDark;
    public boolean mIsLeftPanel;
    public final LatencyTracker mLatencyTracker;
    public WindowManager.LayoutParams mLayoutParams;
    public int mLeftInset;
    public float mMaxTranslation;
    public int mMinArrowPosition;
    public final float mMinDeltaForSwitch;
    public final OneHandModeUtil mOneHandModeUtil;
    public final Paint mPaint;
    public float mPreviousTouchTranslation;
    public int mProtectionColorDark;
    public int mProtectionColorLight;
    public final Paint mProtectionPaint;
    public RegionSamplingHelper mRegionSamplingHelper;
    public final SpringForce mRegularTranslationSpring;
    public int mRightInset;
    public final Rect mSamplingRect;
    public int mScreenSize;
    public final C18891 mSetGoneEndListener;
    public final boolean mShowProtection;
    public float mStartX;
    public float mStartY;
    public final float mSwipeTriggerThreshold;
    public float mTotalTouchDelta;
    public boolean mTrackingBackArrowLatency;
    public final SpringAnimation mTranslationAnimation;
    public boolean mTriggerBack;
    public final SpringForce mTriggerBackSpring;
    public VelocityTracker mVelocityTracker;
    public float mVerticalTranslation;
    public final SpringAnimation mVerticalTranslationAnimation;
    public long mVibrationTime;
    public final VibratorHelper mVibratorHelper;
    public final WindowManager mWindowManager;

    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel$2] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel$3] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel$4] */
    static {
        boolean z = BasicRune.NAVBAR_GESTURE;
        ARROW_LENGTH_DP = z ? 12.75f : 18.0f;
        ARROW_ANGLE_WHEN_EXTENDED_DEGREES = z ? 43.5f : 56.0f;
        ARROW_THICKNESS_DP = z ? 2.2f : 2.5f;
        RUBBER_BAND_INTERPOLATOR = new PathInterpolator(0.2f, 1.0f, 1.0f, 1.0f);
        RUBBER_BAND_INTERPOLATOR_APPEAR = new PathInterpolator(0.25f, 1.0f, 1.0f, 1.0f);
        CURRENT_ANGLE = new FloatPropertyCompat("currentAngle") { // from class: com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel.2
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final float getValue(Object obj) {
                return ((NavigationBarEdgePanel) obj).mCurrentAngle;
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final void setValue(Object obj, float f) {
                NavigationBarEdgePanel navigationBarEdgePanel = (NavigationBarEdgePanel) obj;
                float f2 = NavigationBarEdgePanel.ARROW_LENGTH_DP;
                navigationBarEdgePanel.mCurrentAngle = f;
                navigationBarEdgePanel.invalidate();
            }
        };
        CURRENT_TRANSLATION = new FloatPropertyCompat("currentTranslation") { // from class: com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel.3
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final float getValue(Object obj) {
                return ((NavigationBarEdgePanel) obj).mCurrentTranslation;
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final void setValue(Object obj, float f) {
                NavigationBarEdgePanel navigationBarEdgePanel = (NavigationBarEdgePanel) obj;
                float f2 = NavigationBarEdgePanel.ARROW_LENGTH_DP;
                navigationBarEdgePanel.mCurrentTranslation = f;
                navigationBarEdgePanel.invalidate();
            }
        };
        CURRENT_VERTICAL_TRANSLATION = new FloatPropertyCompat("verticalTranslation") { // from class: com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel.4
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final float getValue(Object obj) {
                return ((NavigationBarEdgePanel) obj).mVerticalTranslation;
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final void setValue(Object obj, float f) {
                NavigationBarEdgePanel navigationBarEdgePanel = (NavigationBarEdgePanel) obj;
                float f2 = NavigationBarEdgePanel.ARROW_LENGTH_DP;
                navigationBarEdgePanel.mVerticalTranslation = f;
                navigationBarEdgePanel.invalidate();
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel$1] */
    public NavigationBarEdgePanel(Context context, LatencyTracker latencyTracker, VibratorHelper vibratorHelper, Executor executor, DisplayTracker displayTracker) {
        super(context);
        Paint paint = new Paint();
        this.mPaint = paint;
        this.mArrowPath = new Path();
        this.mDisplaySize = new Point();
        this.mIsDark = false;
        this.mShowProtection = false;
        this.mSamplingRect = new Rect();
        this.mTrackingBackArrowLatency = false;
        this.mHandler = new Handler();
        this.mFailsafeRunnable = new NavigationBarEdgePanel$$ExternalSyntheticLambda0(this, 0);
        this.mSetGoneEndListener = new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel.1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
                dynamicAnimation.removeEndListener(this);
                if (z) {
                    return;
                }
                NavigationBarEdgePanel.this.setVisibility(8);
            }
        };
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mVibratorHelper = vibratorHelper;
        float f = context.getResources().getDisplayMetrics().density;
        this.mDensity = f;
        float f2 = 32.0f * f;
        this.mBaseTranslation = f2;
        this.mArrowLength = ARROW_LENGTH_DP * f;
        float f3 = ARROW_THICKNESS_DP * f;
        this.mArrowThickness = f3;
        this.mMinDeltaForSwitch = f2;
        paint.setStrokeWidth(f3);
        paint.setStrokeCap(Paint.Cap.ROUND);
        final int i = 1;
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mArrowColorAnimator = ofFloat;
        ofFloat.setDuration(120L);
        final Object[] objArr = 0 == true ? 1 : 0;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel$$ExternalSyntheticLambda1
            public final /* synthetic */ NavigationBarEdgePanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (objArr) {
                    case 0:
                        NavigationBarEdgePanel navigationBarEdgePanel = this.f$0;
                        int blendARGB = ColorUtils.blendARGB(valueAnimator.getAnimatedFraction(), navigationBarEdgePanel.mArrowStartColor, navigationBarEdgePanel.mArrowColor);
                        navigationBarEdgePanel.mCurrentArrowColor = blendARGB;
                        navigationBarEdgePanel.mPaint.setColor(blendARGB);
                        navigationBarEdgePanel.invalidate();
                        break;
                    default:
                        NavigationBarEdgePanel navigationBarEdgePanel2 = this.f$0;
                        navigationBarEdgePanel2.getClass();
                        navigationBarEdgePanel2.mDisappearAmount = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        navigationBarEdgePanel2.invalidate();
                        break;
                }
            }
        });
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mArrowDisappearAnimation = ofFloat2;
        ofFloat2.setDuration(100L);
        ofFloat2.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel$$ExternalSyntheticLambda1
            public final /* synthetic */ NavigationBarEdgePanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i) {
                    case 0:
                        NavigationBarEdgePanel navigationBarEdgePanel = this.f$0;
                        int blendARGB = ColorUtils.blendARGB(valueAnimator.getAnimatedFraction(), navigationBarEdgePanel.mArrowStartColor, navigationBarEdgePanel.mArrowColor);
                        navigationBarEdgePanel.mCurrentArrowColor = blendARGB;
                        navigationBarEdgePanel.mPaint.setColor(blendARGB);
                        navigationBarEdgePanel.invalidate();
                        break;
                    default:
                        NavigationBarEdgePanel navigationBarEdgePanel2 = this.f$0;
                        navigationBarEdgePanel2.getClass();
                        navigationBarEdgePanel2.mDisappearAmount = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        navigationBarEdgePanel2.invalidate();
                        break;
                }
            }
        });
        SpringAnimation springAnimation = new SpringAnimation(this, CURRENT_ANGLE);
        this.mAngleAnimation = springAnimation;
        SpringForce m145m = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(500.0f, 0.5f);
        this.mAngleAppearForce = m145m;
        SpringForce m145m2 = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(1500.0f, 0.5f);
        m145m2.mFinalPosition = 90.0f;
        this.mAngleDisappearForce = m145m2;
        springAnimation.mSpring = m145m;
        springAnimation.mMaxValue = 90.0f;
        SpringAnimation springAnimation2 = new SpringAnimation(this, CURRENT_TRANSLATION);
        this.mTranslationAnimation = springAnimation2;
        SpringForce m145m3 = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(1500.0f, 0.75f);
        this.mRegularTranslationSpring = m145m3;
        this.mTriggerBackSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(450.0f, 0.75f);
        springAnimation2.mSpring = m145m3;
        SpringAnimation springAnimation3 = new SpringAnimation(this, CURRENT_VERTICAL_TRANSLATION);
        this.mVerticalTranslationAnimation = springAnimation3;
        springAnimation3.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(1500.0f, 0.75f);
        Paint paint2 = new Paint(paint);
        this.mProtectionPaint = paint2;
        paint2.setStrokeWidth(f3 + 2.0f);
        loadDimens();
        int themeAttr = Utils.getThemeAttr(R.attr.darkIconTheme, context);
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, Utils.getThemeAttr(R.attr.lightIconTheme, context));
        ContextThemeWrapper contextThemeWrapper2 = new ContextThemeWrapper(context, themeAttr);
        boolean z = BasicRune.NAVBAR_GESTURE;
        this.mArrowColorLight = z ? getContext().getColor(R.color.edge_back_light_icon_color) : Utils.getColorAttrDefaultColor(R.attr.singleToneColor, contextThemeWrapper, 0);
        int color = z ? getContext().getColor(R.color.edge_back_dark_icon_color) : Utils.getColorAttrDefaultColor(R.attr.singleToneColor, contextThemeWrapper2, 0);
        this.mArrowColorDark = color;
        this.mProtectionColorDark = this.mArrowColorLight;
        this.mProtectionColorLight = color;
        updateIsDark(false);
        this.mArrowsPointLeft = getLayoutDirection() == 0;
        invalidate();
        this.mSwipeTriggerThreshold = context.getResources().getDimension(R.dimen.navigation_edge_action_drag_threshold);
        context.getResources().getDimension(R.dimen.navigation_edge_action_progress_threshold);
        setVisibility(8);
        int displayId = ((View) this).mContext.getDisplayId();
        displayTracker.getClass();
        final boolean z2 = displayId == 0;
        RegionSamplingHelper regionSamplingHelper = new RegionSamplingHelper(this, new RegionSamplingHelper.SamplingCallback() { // from class: com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel.5
            @Override // com.android.systemui.shared.navigationbar.RegionSamplingHelper.SamplingCallback
            public final Rect getSampledRegion() {
                boolean z3 = BasicRune.NAVBAR_GESTURE;
                NavigationBarEdgePanel navigationBarEdgePanel = NavigationBarEdgePanel.this;
                if (z3) {
                    Rect rect = navigationBarEdgePanel.mSamplingRect;
                    rect.set(navigationBarEdgePanel.mOneHandModeUtil.getRegionSamplingBounds(rect));
                }
                return navigationBarEdgePanel.mSamplingRect;
            }

            @Override // com.android.systemui.shared.navigationbar.RegionSamplingHelper.SamplingCallback
            public final boolean isSamplingEnabled() {
                if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && ((View) NavigationBarEdgePanel.this).mContext.getDisplayId() == 1) {
                    return true;
                }
                return z2;
            }

            @Override // com.android.systemui.shared.navigationbar.RegionSamplingHelper.SamplingCallback
            public final void onRegionDarknessChanged(boolean z3) {
                NavigationBarEdgePanel navigationBarEdgePanel = NavigationBarEdgePanel.this;
                navigationBarEdgePanel.mIsDark = !z3;
                navigationBarEdgePanel.updateIsDark(true);
            }
        }, executor);
        this.mRegionSamplingHelper = regionSamplingHelper;
        regionSamplingHelper.mWindowVisible = true;
        regionSamplingHelper.updateSamplingListener();
        this.mShowProtection = !z2;
        this.mLatencyTracker = latencyTracker;
        if (z) {
            this.mOneHandModeUtil = new OneHandModeUtil((SettingsHelper) Dependency.get(SettingsHelper.class));
            RegionSamplingHelper regionSamplingHelper2 = this.mRegionSamplingHelper;
            SamsungNavigationBarProxy.Companion.getClass();
            SamsungNavigationBarProxy samsungNavigationBarProxy = SamsungNavigationBarProxy.INSTANCE;
            if (samsungNavigationBarProxy == null) {
                samsungNavigationBarProxy = new SamsungNavigationBarProxy();
                SamsungNavigationBarProxy.INSTANCE = samsungNavigationBarProxy;
            }
            regionSamplingHelper2.mBarProxy = samsungNavigationBarProxy;
        }
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void dump(PrintWriter printWriter) {
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(LockIconView$$ExternalSyntheticOutline0.m81m(LockIconView$$ExternalSyntheticOutline0.m81m(LockIconView$$ExternalSyntheticOutline0.m81m(LockIconView$$ExternalSyntheticOutline0.m81m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "NavigationBarEdgePanel:", "  mIsLeftPanel="), this.mIsLeftPanel, printWriter, "  mTriggerBack="), this.mTriggerBack, printWriter, "  mDragSlopPassed="), this.mDragSlopPassed, printWriter, "  mCurrentAngle="), this.mCurrentAngle, printWriter, "  mDesiredAngle="), this.mDesiredAngle, printWriter, "  mCurrentTranslation="), this.mCurrentTranslation, printWriter, "  mDesiredTranslation="), this.mDesiredTranslation, printWriter, "  mTranslationAnimation running="), this.mTranslationAnimation.mRunning, printWriter);
        this.mRegionSamplingHelper.dump(printWriter);
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public final void loadDimens() {
        Resources resources = getResources();
        this.mArrowPaddingEnd = resources.getDimensionPixelSize(R.dimen.navigation_edge_panel_padding);
        this.mMinArrowPosition = resources.getDimensionPixelSize(R.dimen.navigation_edge_arrow_min_y);
        this.mFingerOffset = resources.getDimensionPixelSize(R.dimen.navigation_edge_finger_offset);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mArrowsPointLeft = getLayoutDirection() == 0;
        invalidate();
        loadDimens();
    }

    @Override // com.android.systemui.plugins.Plugin
    public final void onDestroy() {
        this.mHandler.removeCallbacks(this.mFailsafeRunnable);
        this.mWindowManager.removeView(this);
        this.mRegionSamplingHelper.stop();
        this.mRegionSamplingHelper = null;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        float f = this.mCurrentTranslation - (this.mArrowThickness / 2.0f);
        canvas.save();
        if (!this.mIsLeftPanel) {
            f = getWidth() - f;
        }
        canvas.translate(f, (getHeight() * 0.5f) + this.mVerticalTranslation);
        float cos = ((float) Math.cos(Math.toRadians(this.mCurrentAngle))) * this.mArrowLength;
        float sin = ((float) Math.sin(Math.toRadians(this.mCurrentAngle))) * this.mArrowLength;
        if (!this.mArrowsPointLeft) {
            cos = -cos;
        }
        float lerp = MathUtils.lerp(1.0f, 0.75f, this.mDisappearAmount);
        float f2 = cos * lerp;
        float f3 = sin * lerp;
        this.mArrowPath.reset();
        this.mArrowPath.moveTo(f2, f3);
        this.mArrowPath.lineTo(0.0f, 0.0f);
        this.mArrowPath.lineTo(f2, -f3);
        Path path = this.mArrowPath;
        if (this.mShowProtection) {
            canvas.drawPath(path, this.mProtectionPaint);
        }
        canvas.drawPath(path, this.mPaint);
        canvas.restore();
        if (this.mTrackingBackArrowLatency) {
            this.mLatencyTracker.onActionEnd(15);
            this.mTrackingBackArrowLatency = false;
        }
    }

    @Override // android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mMaxTranslation = getWidth() - this.mArrowPaddingEnd;
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void onMotionEvent(MotionEvent motionEvent) {
        float f;
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mDragSlopPassed = false;
            animate().cancel();
            this.mAngleAnimation.cancel();
            this.mTranslationAnimation.cancel();
            this.mVerticalTranslationAnimation.cancel();
            this.mArrowDisappearAnimation.cancel();
            this.mAngleOffset = 0.0f;
            this.mTranslationAnimation.mSpring = this.mRegularTranslationSpring;
            setTriggerBack(false, false);
            setDesiredTranslation(0.0f, false);
            this.mCurrentTranslation = 0.0f;
            invalidate();
            updateAngle(false);
            this.mPreviousTouchTranslation = 0.0f;
            this.mTotalTouchDelta = 0.0f;
            this.mVibrationTime = 0L;
            if (this.mDesiredVerticalTranslation != 0.0f) {
                this.mDesiredVerticalTranslation = 0.0f;
                this.mVerticalTranslation = 0.0f;
                invalidate();
                invalidate();
            }
            this.mHandler.removeCallbacks(this.mFailsafeRunnable);
            this.mStartX = motionEvent.getX();
            this.mStartY = motionEvent.getY();
            setVisibility(0);
            float max = Math.max(motionEvent.getY() - this.mFingerOffset, this.mMinArrowPosition);
            this.mLayoutParams.y = MathUtils.constrain((int) (max - (r0.height / 2.0f)), 0, this.mDisplaySize.y);
            updateSamplingRect();
            this.mRegionSamplingHelper.start(this.mSamplingRect);
            this.mWindowManager.updateViewLayout(this, this.mLayoutParams);
            this.mLatencyTracker.onActionStart(15);
            this.mTrackingBackArrowLatency = true;
            return;
        }
        if (actionMasked == 1) {
            if (this.mTriggerBack) {
                this.mBackCallback.triggerBack();
                if (this.mVelocityTracker == null) {
                    this.mVelocityTracker = VelocityTracker.obtain();
                }
                this.mVelocityTracker.computeCurrentVelocity(1000);
                if (BasicRune.NAVBAR_GESTURE) {
                    this.mVibratorHelper.vibrateGesture();
                } else {
                    if ((Math.abs(this.mVelocityTracker.getXVelocity()) < 500.0f) || SystemClock.uptimeMillis() - this.mVibrationTime >= 400) {
                        this.mVibratorHelper.vibrate(0);
                    }
                }
                float f2 = this.mAngleOffset;
                if (f2 > -4.0f) {
                    this.mAngleOffset = Math.max(-8.0f, f2 - 8.0f);
                    updateAngle(true);
                }
                final NavigationBarEdgePanel$$ExternalSyntheticLambda0 navigationBarEdgePanel$$ExternalSyntheticLambda0 = new NavigationBarEdgePanel$$ExternalSyntheticLambda0(this, 1);
                SpringAnimation springAnimation = this.mTranslationAnimation;
                if (springAnimation.mRunning) {
                    springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener(this) { // from class: com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel.6
                        @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
                        public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f3, float f4) {
                            dynamicAnimation.removeEndListener(this);
                            if (z) {
                                return;
                            }
                            navigationBarEdgePanel$$ExternalSyntheticLambda0.run();
                        }
                    });
                    this.mHandler.removeCallbacks(this.mFailsafeRunnable);
                    this.mHandler.postDelayed(this.mFailsafeRunnable, 200L);
                } else {
                    navigationBarEdgePanel$$ExternalSyntheticLambda0.run();
                }
            } else {
                this.mBackCallback.cancelBack();
                SpringAnimation springAnimation2 = this.mTranslationAnimation;
                if (springAnimation2.mRunning) {
                    springAnimation2.addEndListener(this.mSetGoneEndListener);
                    this.mHandler.removeCallbacks(this.mFailsafeRunnable);
                    this.mHandler.postDelayed(this.mFailsafeRunnable, 200L);
                } else {
                    setVisibility(8);
                }
            }
            this.mRegionSamplingHelper.stop();
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
            return;
        }
        if (actionMasked != 2) {
            if (actionMasked != 3) {
                return;
            }
            this.mBackCallback.cancelBack();
            SpringAnimation springAnimation3 = this.mTranslationAnimation;
            if (springAnimation3.mRunning) {
                springAnimation3.addEndListener(this.mSetGoneEndListener);
                this.mHandler.removeCallbacks(this.mFailsafeRunnable);
                this.mHandler.postDelayed(this.mFailsafeRunnable, 200L);
            } else {
                setVisibility(8);
            }
            this.mRegionSamplingHelper.stop();
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
            return;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        float abs = MathUtils.abs(x - this.mStartX);
        float f3 = y - this.mStartY;
        float f4 = abs - this.mPreviousTouchTranslation;
        if (Math.abs(f4) > 0.0f) {
            if (Math.signum(f4) == Math.signum(this.mTotalTouchDelta)) {
                this.mTotalTouchDelta += f4;
            } else {
                this.mTotalTouchDelta = f4;
            }
        }
        this.mPreviousTouchTranslation = abs;
        if (!this.mDragSlopPassed && abs > this.mSwipeTriggerThreshold) {
            this.mDragSlopPassed = true;
            this.mVibratorHelper.vibrate(2);
            this.mVibrationTime = SystemClock.uptimeMillis();
            this.mDisappearAmount = 0.0f;
            setAlpha(1.0f);
            setTriggerBack(true, true);
        }
        float f5 = this.mBaseTranslation;
        if (abs > f5) {
            float interpolation = ((PathInterpolator) RUBBER_BAND_INTERPOLATOR).getInterpolation(MathUtils.saturate((abs - f5) / (this.mScreenSize - f5)));
            float f6 = this.mMaxTranslation;
            float f7 = this.mBaseTranslation;
            f = DependencyGraph$$ExternalSyntheticOutline0.m20m(f6, f7, interpolation, f7);
        } else {
            float interpolation2 = ((PathInterpolator) RUBBER_BAND_INTERPOLATOR_APPEAR).getInterpolation(MathUtils.saturate((f5 - abs) / f5));
            float f8 = this.mBaseTranslation;
            f = f8 - ((f8 / 4.0f) * interpolation2);
        }
        boolean z = this.mTriggerBack;
        if (Math.abs(this.mTotalTouchDelta) > this.mMinDeltaForSwitch) {
            z = this.mTotalTouchDelta > 0.0f;
        }
        this.mVelocityTracker.computeCurrentVelocity(1000);
        float xVelocity = this.mVelocityTracker.getXVelocity();
        float signum = Math.signum(xVelocity) * Math.min((MathUtils.mag(xVelocity, this.mVelocityTracker.getYVelocity()) / 1000.0f) * 4.0f, 4.0f);
        this.mAngleOffset = signum;
        boolean z2 = this.mIsLeftPanel;
        if ((z2 && this.mArrowsPointLeft) || (!z2 && !this.mArrowsPointLeft)) {
            this.mAngleOffset = signum * (-1.0f);
        }
        setTriggerBack(Math.abs(f3) <= Math.abs(x - this.mStartX) * 2.0f ? z : false, true);
        if (this.mTriggerBack) {
            boolean z3 = this.mIsLeftPanel;
            if ((z3 && this.mArrowsPointLeft) || (!z3 && !this.mArrowsPointLeft)) {
                f -= ((float) Math.cos(Math.toRadians(ARROW_ANGLE_WHEN_EXTENDED_DEGREES))) * this.mArrowLength;
            }
        } else {
            f = 0.0f;
        }
        setDesiredTranslation(f, true);
        updateAngle(true);
        float height = (getHeight() / 2.0f) - this.mArrowLength;
        float signum2 = Math.signum(f3) * ((PathInterpolator) RUBBER_BAND_INTERPOLATOR).getInterpolation(MathUtils.constrain(Math.abs(f3) / (15.0f * height), 0.0f, 1.0f)) * height;
        if (this.mDesiredVerticalTranslation != signum2) {
            this.mDesiredVerticalTranslation = signum2;
            this.mVerticalTranslationAnimation.animateToFinalPosition(signum2);
            invalidate();
        }
        updateSamplingRect();
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void setBackCallback(NavigationEdgeBackPlugin.BackCallback backCallback) {
        this.mBackCallback = backCallback;
    }

    public final void setDesiredTranslation(float f, boolean z) {
        if (this.mDesiredTranslation != f) {
            this.mDesiredTranslation = f;
            if (z) {
                this.mTranslationAnimation.animateToFinalPosition(f);
            } else {
                this.mCurrentTranslation = f;
                invalidate();
            }
        }
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void setDisplaySize(Point point) {
        this.mDisplaySize.set(point.x, point.y);
        Point point2 = this.mDisplaySize;
        this.mScreenSize = Math.min(point2.x, point2.y);
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void setInsets(int i, int i2) {
        this.mLeftInset = i;
        this.mRightInset = i2;
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void setIsLeftPanel(boolean z) {
        this.mIsLeftPanel = z;
        this.mLayoutParams.gravity = z ? 51 : 53;
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public final void setLayoutParams(WindowManager.LayoutParams layoutParams) {
        this.mLayoutParams = layoutParams;
        this.mWindowManager.addView(this, layoutParams);
    }

    public final void setTriggerBack(boolean z, boolean z2) {
        if (this.mTriggerBack != z) {
            this.mTriggerBack = z;
            this.mAngleAnimation.cancel();
            updateAngle(z2);
            this.mTranslationAnimation.cancel();
            this.mBackCallback.setTriggerBack(this.mTriggerBack);
        }
    }

    public final void updateAngle(boolean z) {
        boolean z2 = this.mTriggerBack;
        float f = z2 ? ARROW_ANGLE_WHEN_EXTENDED_DEGREES + this.mAngleOffset : 90.0f;
        if (f != this.mDesiredAngle) {
            if (z) {
                SpringAnimation springAnimation = this.mAngleAnimation;
                springAnimation.mSpring = z2 ? this.mAngleAppearForce : this.mAngleDisappearForce;
                springAnimation.animateToFinalPosition(f);
            } else {
                this.mCurrentAngle = f;
                invalidate();
            }
            this.mDesiredAngle = f;
        }
    }

    public final void updateIsDark(boolean z) {
        this.mProtectionPaint.setColor(this.mIsDark ? this.mProtectionColorDark : this.mProtectionColorLight);
        this.mArrowColor = this.mIsDark ? this.mArrowColorDark : this.mArrowColorLight;
        this.mArrowColorAnimator.cancel();
        if (z) {
            this.mArrowStartColor = this.mCurrentArrowColor;
            this.mArrowColorAnimator.start();
        } else {
            int i = this.mArrowColor;
            this.mCurrentArrowColor = i;
            this.mPaint.setColor(i);
            invalidate();
        }
    }

    public final void updateSamplingRect() {
        WindowManager.LayoutParams layoutParams = this.mLayoutParams;
        int i = layoutParams.y;
        int i2 = this.mIsLeftPanel ? this.mLeftInset : (this.mDisplaySize.x - this.mRightInset) - layoutParams.width;
        this.mSamplingRect.set(i2, i, layoutParams.width + i2, layoutParams.height + i);
        float f = this.mDesiredTranslation;
        if (!this.mTriggerBack) {
            f = this.mBaseTranslation;
            boolean z = this.mIsLeftPanel;
            if ((z && this.mArrowsPointLeft) || (!z && !this.mArrowsPointLeft)) {
                f -= ((float) Math.cos(Math.toRadians(ARROW_ANGLE_WHEN_EXTENDED_DEGREES))) * this.mArrowLength;
            }
        }
        float f2 = f - (this.mArrowThickness / 2.0f);
        if (!this.mIsLeftPanel) {
            f2 = this.mSamplingRect.width() - f2;
        }
        float f3 = ARROW_ANGLE_WHEN_EXTENDED_DEGREES;
        float cos = ((float) Math.cos(Math.toRadians(f3))) * this.mArrowLength;
        float sin = ((float) Math.sin(Math.toRadians(f3))) * this.mArrowLength * 2.0f;
        if (!this.mArrowsPointLeft) {
            f2 -= cos;
        }
        this.mSamplingRect.offset((int) f2, (int) (((getHeight() * 0.5f) + this.mDesiredVerticalTranslation) - (sin / 2.0f)));
        Rect rect = this.mSamplingRect;
        int i3 = rect.left;
        int i4 = rect.top;
        rect.set(i3, i4, (int) (i3 + cos), (int) (i4 + sin));
        this.mRegionSamplingHelper.updateSamplingRect();
    }
}
