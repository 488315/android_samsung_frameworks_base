package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telecom.TelecomManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SemBlurInfo;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.DrawableKt;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import com.android.systemui.facewidget.plugin.FaceWidgetContainerWrapper;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.shared.model.CameraLaunchType;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import com.android.systemui.keyguardimage.WallpaperImageInjectCreator;
import com.android.systemui.shade.CameraLauncher;
import com.android.systemui.shade.NotificationPanelView;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.KeyguardSecAffordanceHelper;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.wallpaper.WallpaperUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public class KeyguardSecAffordanceView extends KeyguardAffordanceView implements KeyguardStateController.Callback, LaunchableView {
    public final LaunchableViewDelegate delegate;
    public int mAffordancePivotY;
    public List mAnimatorSet;
    public final Paint mBackgroundCirclePaint;
    public int mBlurPanelRadius;
    public FrameLayout mBlurPanelRoot;
    public View mBlurPanelView;
    public ValueAnimator mBottomIconAlphaAnimator;
    public final AnonymousClass4 mBottomIconAlphaEndListener;
    public ValueAnimator mBottomIconScaleAnimator;
    public final AnonymousClass13 mBottomIconScaleEndListener;
    public boolean mCanDismissLockScreen;
    public int mCenterX;
    public int mCenterY;
    public View mClockView;
    public boolean mDeviceInteractive;
    public final AnonymousClass1 mDisplayObserver;
    public int mDrawBackgroundColor;
    protected boolean mFling;
    public final Paint mForegroundCirclePaint;
    public final AnonymousClass14 mHandler;
    public KeyguardSecAffordanceHelper.Callback mHelperCallback;
    public float mImageScale;
    public Animator mInitialPeekAnimator;
    public final AnonymousClass12 mInitialPeekAnimatorEndListener;
    public float mInitialPeekDistance;
    public boolean mInitialPeekShowing;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public boolean mIsAnimFromNowBarRunning;
    public boolean mIsBlurBgApplied;
    public boolean mIsDown;
    public boolean mIsDrawBackgroundCircle;
    public boolean mIsLandScape;
    public boolean mIsNoUnlockNeeded;
    public boolean mIsNowBarExpanded;
    public boolean mIsNowBarVisible;
    public boolean mIsSecure;
    public boolean mIsShortcutForPhone;
    public boolean mIsShortcutLaunching;
    public boolean mIsTargetView;
    public boolean mIsTaskTypeShortcut;
    public boolean mIsTaskTypeShortcutEnabled;
    public boolean mIsTransitIconNeeded;
    public boolean mIsWhiteWallpaper;
    public boolean mJustClicked;
    public final KeyguardStateController mKeyguardStateController;
    public Rect mLastBlockingArea;
    public boolean mLaunchThresholdAchieved;
    public View mLockIconContainerView;
    public View mLockStarContainer;
    public int mMaxBackgroundAlpha;
    public float mMaxDimBackground;
    public int mMaxForegroundAlpha;
    public int mMaxTaskOnBackgroundAlpha;
    public View mMusicContainer;
    public View mNotificationPanelIconOnlyContainer;
    public NotificationPanelView mNotificationPanelView;
    public NotificationStackScrollLayout mNotificationStackScrollerView;
    public SpringAnimation mNowBarVisibilitySizeAnimation;
    public SpringAnimation mNowBarVisibilityXAnimation;
    public SpringAnimation mNowBarVisibilityYAnimation;
    public int mOldPanelBackgroundAlpha;
    public View mPanelBackground;
    public PaintDrawable mPanelBackgroundDrawable;
    public View mPanelDimView;
    public ImageView mPanelIcon;
    public Animator mRectangleAlphaAnimator;
    public final AnonymousClass11 mRectangleAlphaAnimatorEndListener;
    public final Rect mRectangleBounds;
    public int mRectangleColor;
    public float mRectangleDistanceCovered;
    public int mRectangleIconAlpha;
    public final Rect mRectangleIconBounds;
    public Drawable mRectangleIconDrawable;
    public int mRectangleIconMargin;
    public float mRectangleIconScale;
    public Animator mRectangleIconScaleAnimator;
    public final AnonymousClass10 mRectangleIconScaleAnimatorEndListener;
    public float mRectangleIconScaleStart;
    public int mRectangleIconSize;
    public final Paint mRectanglePaint;
    public Animator mRectangleScaleAnimator;
    public final AnonymousClass9 mRectangleScaleAnimatorEndListener;
    public float mRectangleScaleStart;
    public ValueAnimator mRectangleShrinkAlphaAnimator;
    public final AnonymousClass6 mRectangleShrinkAlphaAnimatorEndListener;
    public ValueAnimator mRectangleShrinkAnimator;
    public final AnonymousClass5 mRectangleShrinkAnimatorEndListener;
    public boolean mRight;
    public int mScreenHeight;
    public int mScreenWidth;
    private final SettingsHelper mSettingsHelper;
    public boolean mShortcutForCamera;
    public Animator mShortcutLaunchAlphaAnimator;
    public final AnonymousClass8 mShortcutLaunchAlphaAnimatorEndListener;
    public Animator mShortcutLaunchAnimator;
    public final AnonymousClass7 mShortcutLaunchAnimatorEndListener;
    public float mShortcutLaunchDistance;
    public final KeyguardShortcutManager mShortcutManager;
    public final Paint mTaskOnCirclePaint;
    public final TelecomManager mTelecomManager;
    public boolean mTouchCancelled;
    protected TouchHandlePolicy mTouchHandler;
    public boolean mTrusted;
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallback;
    public VelocityTracker mVelocityTracker;
    public float mVerticalScale;
    public VibrationUtil mVibrationUtil;
    public final KeyguardSecAffordanceView$$ExternalSyntheticLambda0 mVisibilityListener;
    public WallpaperImageInjectCreator mWallpaperImageCreator;
    public static final Interpolator SCALE_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
    public static final Interpolator ALPHA_INTERPOLATOR = new PathInterpolator(0.33f, 1.0f, 0.68f, 1.0f);
    public static final Interpolator NOW_BAR_INTERPOLATOR = new PathInterpolator(0.17f, 0.17f, 0.4f, 1.0f);
    public static boolean mIsShowBouncerAnimation = false;
    public static boolean mWaitForReset = false;
    public static boolean mIsLaunchPanelRunning = false;

    public class GeneralTouchHandler implements TouchHandlePolicy {
        public /* synthetic */ GeneralTouchHandler(KeyguardSecAffordanceView keyguardSecAffordanceView, int i) {
            this();
        }

        private GeneralTouchHandler() {
        }
    }

    public interface TouchHandlePolicy {
    }

    public static /* synthetic */ Unit $r8$lambda$usC3LFwY5dbTHi859Ko25i8wvTY(KeyguardSecAffordanceView keyguardSecAffordanceView, Integer num) {
        super.setVisibility(num.intValue());
        return Unit.INSTANCE;
    }

    /* renamed from: -$$Nest$mresetOnTimeout, reason: not valid java name */
    public static void m2958$$Nest$mresetOnTimeout(KeyguardSecAffordanceView keyguardSecAffordanceView) {
        keyguardSecAffordanceView.getClass();
        Log.d("KeyguardSecAffordanceView", "resetOnTimeout");
        keyguardSecAffordanceView.mIsShortcutLaunching = false;
        mIsShowBouncerAnimation = false;
        mWaitForReset = false;
        KeyguardSecAffordanceHelper keyguardSecAffordanceHelper = NotificationPanelViewController.this.mSecAffordanceHelper;
        if (keyguardSecAffordanceHelper != null) {
            keyguardSecAffordanceHelper.reset(true);
        }
    }

    public KeyguardSecAffordanceView(Context context) {
        this(context, null);
    }

    public static void cancelAnimator(Animator animator) {
        if (animator != null) {
            animator.cancel();
        }
    }

    public final void cancelAllAnimators() {
        cancelAnimator(this.mInitialPeekAnimator);
        cancelAnimator(this.mBottomIconAlphaAnimator);
        cancelAnimator(this.mRectangleShrinkAnimator);
        cancelAnimator(this.mRectangleShrinkAlphaAnimator);
    }

    public final void cancelResizeAnimators() {
        SpringAnimation springAnimation = this.mNowBarVisibilitySizeAnimation;
        if (springAnimation != null && springAnimation.mRunning) {
            springAnimation.cancel();
        }
        this.mNowBarVisibilitySizeAnimation = null;
        SpringAnimation springAnimation2 = this.mNowBarVisibilityXAnimation;
        if (springAnimation2 != null && springAnimation2.mRunning) {
            springAnimation2.cancel();
        }
        this.mNowBarVisibilityXAnimation = null;
        SpringAnimation springAnimation3 = this.mNowBarVisibilityYAnimation;
        if (springAnimation3 != null && springAnimation3.mRunning) {
            springAnimation3.cancel();
        }
        this.mNowBarVisibilityYAnimation = null;
    }

    public final int getForegroundAlpha(int i) {
        int i2 = this.mMaxForegroundAlpha;
        int i3 = (int) ((i / 255.0f) * i2);
        return i3 > i2 ? i2 : i3;
    }

    public final String getShortcutType() {
        return this.mRight ? "RightShortcut" : "LeftShortcut";
    }

    public final void init() {
        ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).registerCallback(this.mUpdateMonitorCallback);
        this.mTouchHandler = new GeneralTouchHandler(this, 0);
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup != null) {
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
        }
    }

    public final boolean isSecure$1() {
        return (!this.mIsSecure || this.mTrusted || this.mCanDismissLockScreen) ? false : true;
    }

    public final void launchShortcut(float f, float f2) {
        float fHypot;
        int i = 0;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) {
            fHypot = 0.0f;
        } else {
            velocityTracker.computeCurrentVelocity(1000);
            float xVelocity = this.mVelocityTracker.getXVelocity();
            float yVelocity = this.mVelocityTracker.getYVelocity();
            float f3 = f - this.mInitialTouchX;
            float f4 = f2 - this.mInitialTouchY;
            fHypot = ((yVelocity * f4) + (xVelocity * f3)) / ((float) Math.hypot(f3, f4));
        }
        if (fHypot <= -4000.0f) {
            this.mFling = false;
            this.mIsShortcutLaunching = false;
            cancelAllAnimators();
            startRectangleShrinkAnimation();
            this.mRectanglePaint.setAlpha(0);
            return;
        }
        this.mFling = true;
        this.mIsShortcutLaunching = true;
        boolean zIsSecure$1 = isSecure$1();
        mIsShowBouncerAnimation = zIsSecure$1 && !this.mIsNoUnlockNeeded && (!this.mIsTaskTypeShortcut || this.mShortcutManager.isUnlockWaitNeeded(this.mRight ? 1 : 0));
        mWaitForReset = (this.mIsNoUnlockNeeded || !zIsSecure$1) && this.mIsShortcutLaunching && (!this.mIsTaskTypeShortcut || this.mShortcutManager.isUnlockWaitNeeded(this.mRight ? 1 : 0));
        if (mIsShowBouncerAnimation && this.mIsShortcutForPhone) {
            mIsShowBouncerAnimation = !this.mTelecomManager.isInManagedCall();
        }
        setImageAlpha(0.0f, true);
        Log.i("KeyguardSecAffordanceView", "startShortcutLaunchAnimation");
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mRectangleDistanceCovered, this.mScreenWidth);
        this.mShortcutLaunchAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(450L);
        valueAnimatorOfFloat.setInterpolator(SCALE_INTERPOLATOR);
        valueAnimatorOfFloat.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda2(this, 0));
        valueAnimatorOfFloat.addListener(this.mShortcutLaunchAnimatorEndListener);
        this.mShortcutLaunchAnimator.start();
        Log.i("KeyguardSecAffordanceView", "startShortcutLaunchAlphaAnimation");
        cancelAnimator(this.mShortcutLaunchAlphaAnimator);
        cancelAnimator(this.mRectangleAlphaAnimator);
        int alpha = this.mRectanglePaint.getAlpha();
        if (!mIsShowBouncerAnimation && (!this.mIsTaskTypeShortcut || this.mShortcutManager.isUnlockWaitNeeded(this.mRight ? 1 : 0))) {
            i = 255;
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(alpha, i);
        this.mShortcutLaunchAlphaAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.setDuration(450L);
        valueAnimatorOfInt.setInterpolator(ALPHA_INTERPOLATOR);
        valueAnimatorOfInt.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda2(this, 8));
        valueAnimatorOfInt.addListener(this.mShortcutLaunchAlphaAnimatorEndListener);
        this.mShortcutLaunchAlphaAnimator.start();
        startRectangleScaleAnimation(0.0f);
    }

    @Override // com.android.systemui.widget.SystemUIImageView, android.widget.ImageView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (WallpaperEventNotifier.getInstance() != null) {
            WallpaperEventNotifier.getInstance().removeCallback(false, this);
        }
        ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).addObserver(this.mDisplayObserver);
        ((KeyguardVisibilityMonitor) Dependency.sDependency.getDependencyInner(KeyguardVisibilityMonitor.class)).addVisibilityChangedListener(this.mVisibilityListener);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        super.onConfigurationChanged(configuration);
        reset(true);
        this.mIsLandScape = configuration.orientation == 2;
        updateDisplayParameters();
    }

    @Override // com.android.systemui.widget.SystemUIImageView, android.widget.ImageView, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).removeObserver(this.mDisplayObserver);
        KeyguardVisibilityMonitor keyguardVisibilityMonitor = (KeyguardVisibilityMonitor) Dependency.sDependency.getDependencyInner(KeyguardVisibilityMonitor.class);
        ((ArrayList) keyguardVisibilityMonitor.visibilityChangedListeners).remove(this.mVisibilityListener);
    }

    @Override // com.android.systemui.statusbar.KeyguardAffordanceView, android.widget.ImageView, android.view.View
    public final void onDraw(Canvas canvas) {
        if (this.mIsTargetView && this.mBlurPanelView != null) {
            int alpha = this.mRectanglePaint.getAlpha();
            if (this.mOldPanelBackgroundAlpha != alpha) {
                this.mPanelBackgroundDrawable.getPaint().setAlpha(alpha);
                this.mOldPanelBackgroundAlpha = alpha;
            }
            View view = this.mPanelBackground;
            Rect rect = this.mRectangleBounds;
            view.setLeftTopRightBottom(rect.left, rect.top, rect.right, rect.bottom);
            this.mPanelIcon.setAlpha(this.mRectangleIconAlpha);
            ImageView imageView = this.mPanelIcon;
            Rect rect2 = this.mRectangleIconBounds;
            imageView.setLeftTopRightBottom(rect2.left, rect2.top, rect2.right, rect2.bottom);
            this.mBlurPanelView.semSetBlurRadius(this.mBlurPanelRadius);
        }
        boolean zIsSupportBlur = this.mShortcutManager.isSupportBlur();
        if ((!zIsSupportBlur && this.mShortcutManager.isMonotoneIcon(this.mRight ? 1 : 0)) || this.mIsDrawBackgroundCircle) {
            canvas.drawCircle(this.mCenterX, this.mCenterY, getWidth() / 2.0f, this.mBackgroundCirclePaint);
        }
        if (this.mIsTaskTypeShortcutEnabled && !zIsSupportBlur) {
            canvas.drawCircle(this.mCenterX, this.mCenterY, getWidth() / 2.0f, this.mTaskOnCirclePaint);
        }
        canvas.drawCircle(this.mCenterX, this.mCenterY, getWidth() / 2.0f, this.mForegroundCirclePaint);
        canvas.save();
        float f = this.mImageScale;
        canvas.scale(f, f, this.mCenterX, this.mCenterY);
        super.onDraw(canvas);
        canvas.restore();
    }

    @Override // com.android.systemui.statusbar.KeyguardAffordanceView, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mCenterX = getWidth() / 2;
        this.mCenterY = getHeight() / 2;
        this.mScreenHeight = getRootView().getHeight();
        this.mScreenWidth = getRootView().getWidth();
        int width = getWidth();
        KeyguardShortcutManager keyguardShortcutManager = this.mShortcutManager;
        if (width != keyguardShortcutManager.getShortcutIconSizeValue(keyguardShortcutManager.isNowBarVisible) || !this.mIsBlurBgApplied) {
            this.mIsBlurBgApplied = true;
            if (!this.mIsNowBarExpanded) {
                updateBgBlur(((KeyguardVisibilityMonitor) Dependency.sDependency.getDependencyInner(KeyguardVisibilityMonitor.class)).isVisible());
            }
        }
        postDelayed(new KeyguardSecAffordanceView$$ExternalSyntheticLambda5(this, 1), 700L);
    }

    public final void onPostFinishedWakingUp() {
        this.mDeviceInteractive = true;
        this.mIsTargetView = false;
        boolean zIsVisible = ((KeyguardVisibilityMonitor) Dependency.sDependency.getDependencyInner(KeyguardVisibilityMonitor.class)).isVisible();
        if (this.mShortcutManager.isSupportBlur() && !this.mIsNowBarExpanded && this.mShortcutManager.isMonotoneIcon(this.mRight ? 1 : 0) && zIsVisible) {
            updateBgBlur(true);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:108:0x01df  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x026b  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x026e  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0271  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0166  */
    /* JADX WARN: Type inference failed for: r6v2, types: [boolean] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        TouchHandlePolicy touchHandlePolicy;
        boolean z;
        Object[] objArr;
        VelocityTracker velocityTracker;
        if (getAlpha() == 0.0f || getImageAlpha() == 0.0f || !isEnabled() || (touchHandlePolicy = this.mTouchHandler) == null) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
        if (actionMasked != 2) {
            motionEvent.toString();
            keyguardSecAffordanceView.getClass();
        }
        int actionMasked2 = motionEvent.getActionMasked();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        View view = null;
        if (actionMasked2 == 0) {
            keyguardSecAffordanceView.cancelAllAnimators();
            Resources resources = ((ImageView) keyguardSecAffordanceView).mContext.getResources();
            if (keyguardSecAffordanceView.mShortcutManager.isMonotoneIcon(keyguardSecAffordanceView.mRight ? 1 : 0)) {
                keyguardSecAffordanceView.mRectangleIconSize = resources.getDimensionPixelSize(R.dimen.keyguard_affordance_app_icon_size_monotonic);
            } else {
                keyguardSecAffordanceView.mRectangleIconSize = resources.getDimensionPixelSize(R.dimen.keyguard_affordance_app_icon_size_non_monotonic);
            }
            keyguardSecAffordanceView.mInitialTouchX = x;
            keyguardSecAffordanceView.mInitialTouchY = y;
            keyguardSecAffordanceView.mTouchCancelled = false;
            keyguardSecAffordanceView.mJustClicked = true;
            keyguardSecAffordanceView.mIsDown = true;
            keyguardSecAffordanceView.mLaunchThresholdAchieved = false;
            mIsShowBouncerAnimation = false;
            mWaitForReset = false;
            keyguardSecAffordanceView.mIsNoUnlockNeeded = keyguardSecAffordanceView.mShortcutManager.isNoUnlockNeeded(keyguardSecAffordanceView.mRight ? 1 : 0);
            keyguardSecAffordanceView.mVerticalScale = 0.2f;
            keyguardSecAffordanceView.mRectangleIconAlpha = 102;
            if (keyguardSecAffordanceView.mIsTaskTypeShortcut) {
                KeyguardShortcutManager keyguardShortcutManager = keyguardSecAffordanceView.mShortcutManager;
                ?? r6 = keyguardSecAffordanceView.mRight;
                keyguardShortcutManager.getClass();
                if (r6 < 0 || r6 >= 2 || Intrinsics.areEqual(keyguardShortcutManager.keyguardBottomAreaShortcutTask[r6 == true ? 1 : 0], KeyguardShortcutManager.EMPTY_CONFIG)) {
                    ClockEventController$$ExternalSyntheticOutline0.m(r6 == true ? 1 : 0, "IllegalArgument : ", "KeyguardShortcutManager");
                } else {
                    if (keyguardShortcutManager.shortcutsData[r6 == true ? 1 : 0].panelTransitDrawable != null) {
                        z = true;
                    }
                    keyguardSecAffordanceView.mIsTransitIconNeeded = z;
                    boolean z2 = keyguardSecAffordanceView.mIsTaskTypeShortcutEnabled;
                    int i = !z2 ? 204 : 102;
                    keyguardSecAffordanceView.mRectangleIconAlpha = z2 ? 255 : 102;
                    i = i;
                }
                z = false;
                keyguardSecAffordanceView.mIsTransitIconNeeded = z;
                boolean z22 = keyguardSecAffordanceView.mIsTaskTypeShortcutEnabled;
                if (!z22) {
                }
                keyguardSecAffordanceView.mRectangleIconAlpha = z22 ? 255 : 102;
                i = i;
            }
            keyguardSecAffordanceView.mRectanglePaint.setAlpha(i);
            keyguardSecAffordanceView.mRectangleIconScale = 1.0f;
            keyguardSecAffordanceView.mInitialPeekShowing = true;
            PaintDrawable paintDrawable = keyguardSecAffordanceView.mPanelBackgroundDrawable;
            if (paintDrawable != null) {
                paintDrawable.setCornerRadius(keyguardSecAffordanceView.getResources().getDisplayMetrics().density * 26.0f);
            }
            View view2 = keyguardSecAffordanceView.mBlurPanelView;
            if (view2 != null) {
                mIsLaunchPanelRunning = true;
                view2.semSetBlurEnabled(true);
                keyguardSecAffordanceView.mPanelBackground.setBackground(keyguardSecAffordanceView.mPanelBackgroundDrawable);
                keyguardSecAffordanceView.mBlurPanelRoot.setVisibility(0);
            }
            keyguardSecAffordanceView.updateRectangleIconDrawable(false);
            if (keyguardSecAffordanceView.mClockView == null) {
                FaceWidgetContainerWrapper faceWidgetContainerWrapper = NotificationPanelViewController.this.mKeyguardStatusBase;
                View view3 = faceWidgetContainerWrapper.mClockContainer;
                if (view3 == null) {
                    view3 = faceWidgetContainerWrapper.mFaceWidgetContainer;
                }
                keyguardSecAffordanceView.mClockView = view3;
            }
            if (keyguardSecAffordanceView.mNotificationStackScrollerView == null) {
                keyguardSecAffordanceView.mNotificationStackScrollerView = NotificationPanelViewController.this.mNotificationStackScrollLayoutController.mView;
            }
            if (keyguardSecAffordanceView.mNotificationPanelIconOnlyContainer == null) {
                keyguardSecAffordanceView.mNotificationPanelIconOnlyContainer = NotificationPanelViewController.this.mLockscreenNotificationIconsOnlyController.getIconContainer();
            }
            if (keyguardSecAffordanceView.mLockIconContainerView == null) {
                keyguardSecAffordanceView.mLockIconContainerView = NotificationPanelViewController.this.mStatusBarKeyguardViewManager.getLockIconContainer();
            }
            if (keyguardSecAffordanceView.mMusicContainer == null) {
                List list = NotificationPanelViewController.this.mKeyguardStatusBase.mContentsContainerList;
                if (list != null && !list.isEmpty()) {
                    view = (View) list.get(1);
                }
                keyguardSecAffordanceView.mMusicContainer = view;
            }
            if (keyguardSecAffordanceView.mLockStarContainer == null) {
                keyguardSecAffordanceView.mLockStarContainer = NotificationPanelViewController.this.mPluginLockStarContainer;
            }
            NotificationPanelViewController.this.mKeyguardWallpaperController.getClass();
            if (keyguardSecAffordanceView.mNotificationPanelView == null) {
                keyguardSecAffordanceView.mNotificationPanelView = NotificationPanelViewController.this.mView;
            }
            if (keyguardSecAffordanceView.mWallpaperImageCreator == null) {
                keyguardSecAffordanceView.mWallpaperImageCreator = NotificationPanelViewController.this.mWallpaperImageCreator;
            }
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mView.getLayoutDirection();
            notificationPanelViewController.mView.requestDisallowInterceptTouchEvent(true);
            notificationPanelViewController.mOnlyAffordanceInThisMotion = true;
            KeyguardSecAffordanceHelper keyguardSecAffordanceHelper = notificationPanelViewController.mSecAffordanceHelper;
            if (keyguardSecAffordanceHelper != null) {
                keyguardSecAffordanceHelper.isShortcutPreviewSwipingInProgress = true;
            }
            Log.i("KeyguardSecAffordanceView", "startInitialPeekAnimation");
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, keyguardSecAffordanceView.mInitialPeekDistance);
            keyguardSecAffordanceView.mInitialPeekAnimator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.setDuration(300L);
            valueAnimatorOfFloat.setInterpolator(SCALE_INTERPOLATOR);
            valueAnimatorOfFloat.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda2(keyguardSecAffordanceView, 4));
            valueAnimatorOfFloat.addListener(keyguardSecAffordanceView.mInitialPeekAnimatorEndListener);
            keyguardSecAffordanceView.mInitialPeekAnimator.start();
            keyguardSecAffordanceView.setImageAlpha(0.0f, true);
            keyguardSecAffordanceView.setImageScale(0.9f, true);
            VelocityTracker velocityTracker2 = keyguardSecAffordanceView.mVelocityTracker;
            if (velocityTracker2 != null) {
                velocityTracker2.recycle();
            }
            VelocityTracker velocityTrackerObtain = VelocityTracker.obtain();
            keyguardSecAffordanceView.mVelocityTracker = velocityTrackerObtain;
            if (velocityTrackerObtain != null) {
                velocityTrackerObtain.addMovement(motionEvent);
            }
        } else if (actionMasked2 == 1) {
            mIsLaunchPanelRunning = false;
            if (!keyguardSecAffordanceView.mTouchCancelled) {
                if (keyguardSecAffordanceView.mRectangleDistanceCovered < keyguardSecAffordanceView.mShortcutLaunchDistance || !keyguardSecAffordanceView.mDeviceInteractive) {
                    KeyguardSecAffordanceHelper keyguardSecAffordanceHelper2 = NotificationPanelViewController.this.mSecAffordanceHelper;
                    if (keyguardSecAffordanceHelper2 != null) {
                        keyguardSecAffordanceHelper2.isShortcutPreviewSwipingInProgress = false;
                    }
                    keyguardSecAffordanceView.cancelAllAnimators();
                } else {
                    keyguardSecAffordanceView.launchShortcut(x, y);
                }
                VelocityTracker velocityTracker3 = keyguardSecAffordanceView.mVelocityTracker;
                if (velocityTracker3 != null) {
                    velocityTracker3.recycle();
                    keyguardSecAffordanceView.mVelocityTracker = null;
                }
                if (keyguardSecAffordanceView.mJustClicked) {
                    if (motionEvent.getEventTime() - motionEvent.getDownTime() > ViewConfiguration.getTapTimeout() * 2) {
                        keyguardSecAffordanceView.mJustClicked = false;
                    } else {
                        keyguardSecAffordanceView.mJustClicked = false;
                        keyguardSecAffordanceView.mRectangleIconBounds.set(0, 0, 0, 0);
                        keyguardSecAffordanceView.mRectangleBounds.set(0, 0, 0, 0);
                        keyguardSecAffordanceView.invalidate();
                        keyguardSecAffordanceView.resetBlurRectangleView();
                        NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                        if (!notificationPanelViewController2.mHintAnimationRunning) {
                            notificationPanelViewController2.mHintAnimationRunning = true;
                            if (notificationPanelViewController2.mSecAffordanceHelper != null) {
                                notificationPanelViewController2.mView.getLayoutDirection();
                            }
                        }
                    }
                }
                objArr = true;
                keyguardSecAffordanceView.mTouchCancelled = true;
                mIsLaunchPanelRunning = false;
                velocityTracker = keyguardSecAffordanceView.mVelocityTracker;
                if (velocityTracker != null) {
                }
                if (keyguardSecAffordanceView.mIsShortcutLaunching) {
                    keyguardSecAffordanceView.mHandler.sendEmptyMessageDelayed(1001, 1500L);
                }
                if (keyguardSecAffordanceView.mJustClicked) {
                    keyguardSecAffordanceView.cancelAllAnimators();
                    keyguardSecAffordanceView.startRectangleShrinkAnimation();
                    cancelAnimator(keyguardSecAffordanceView.mRectangleShrinkAlphaAnimator);
                    cancelAnimator(keyguardSecAffordanceView.mRectangleAlphaAnimator);
                    ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(keyguardSecAffordanceView.mRectanglePaint.getAlpha(), 0);
                    keyguardSecAffordanceView.mRectangleShrinkAlphaAnimator = valueAnimatorOfInt;
                    valueAnimatorOfInt.setDuration(200L);
                    valueAnimatorOfInt.setInterpolator(ALPHA_INTERPOLATOR);
                    valueAnimatorOfInt.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda2(keyguardSecAffordanceView, 3));
                    valueAnimatorOfInt.addListener(keyguardSecAffordanceView.mRectangleShrinkAlphaAnimatorEndListener);
                    keyguardSecAffordanceView.mRectangleShrinkAlphaAnimator.start();
                    ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).setShortcutLaunchInProgress(false);
                    if (keyguardSecAffordanceView.mIsShortcutLaunching) {
                    }
                    NotificationPanelViewController.this.mKeyguardWallpaperController.getClass();
                    return true;
                }
                keyguardSecAffordanceView.cancelAllAnimators();
                keyguardSecAffordanceView.startRectangleShrinkAnimation();
                cancelAnimator(keyguardSecAffordanceView.mRectangleShrinkAlphaAnimator);
                cancelAnimator(keyguardSecAffordanceView.mRectangleAlphaAnimator);
                ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(keyguardSecAffordanceView.mRectanglePaint.getAlpha(), 0);
                keyguardSecAffordanceView.mRectangleShrinkAlphaAnimator = valueAnimatorOfInt2;
                valueAnimatorOfInt2.setDuration(200L);
                valueAnimatorOfInt2.setInterpolator(ALPHA_INTERPOLATOR);
                valueAnimatorOfInt2.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda2(keyguardSecAffordanceView, 3));
                valueAnimatorOfInt2.addListener(keyguardSecAffordanceView.mRectangleShrinkAlphaAnimatorEndListener);
                keyguardSecAffordanceView.mRectangleShrinkAlphaAnimator.start();
                ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).setShortcutLaunchInProgress(false);
                if (keyguardSecAffordanceView.mIsShortcutLaunching) {
                }
                NotificationPanelViewController.this.mKeyguardWallpaperController.getClass();
                return true;
            }
        } else if (actionMasked2 != 2) {
            if (actionMasked2 == 3) {
                objArr = false;
                keyguardSecAffordanceView.mTouchCancelled = true;
                mIsLaunchPanelRunning = false;
                velocityTracker = keyguardSecAffordanceView.mVelocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.addMovement(motionEvent);
                }
                if (keyguardSecAffordanceView.mIsShortcutLaunching && (!keyguardSecAffordanceView.mIsTaskTypeShortcut || keyguardSecAffordanceView.mShortcutManager.isUnlockWaitNeeded(keyguardSecAffordanceView.mRight ? 1 : 0))) {
                    keyguardSecAffordanceView.mHandler.sendEmptyMessageDelayed(1001, 1500L);
                }
                if ((keyguardSecAffordanceView.mJustClicked || objArr == false) && !keyguardSecAffordanceView.mIsShortcutLaunching && !keyguardSecAffordanceView.mFling) {
                    keyguardSecAffordanceView.cancelAllAnimators();
                    keyguardSecAffordanceView.startRectangleShrinkAnimation();
                    cancelAnimator(keyguardSecAffordanceView.mRectangleShrinkAlphaAnimator);
                    cancelAnimator(keyguardSecAffordanceView.mRectangleAlphaAnimator);
                    ValueAnimator valueAnimatorOfInt22 = ValueAnimator.ofInt(keyguardSecAffordanceView.mRectanglePaint.getAlpha(), 0);
                    keyguardSecAffordanceView.mRectangleShrinkAlphaAnimator = valueAnimatorOfInt22;
                    valueAnimatorOfInt22.setDuration(200L);
                    valueAnimatorOfInt22.setInterpolator(ALPHA_INTERPOLATOR);
                    valueAnimatorOfInt22.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda2(keyguardSecAffordanceView, 3));
                    valueAnimatorOfInt22.addListener(keyguardSecAffordanceView.mRectangleShrinkAlphaAnimatorEndListener);
                    keyguardSecAffordanceView.mRectangleShrinkAlphaAnimator.start();
                    ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).setShortcutLaunchInProgress(false);
                }
                if (keyguardSecAffordanceView.mIsShortcutLaunching || keyguardSecAffordanceView.mIsTaskTypeShortcut) {
                    NotificationPanelViewController.this.mKeyguardWallpaperController.getClass();
                    return true;
                }
            } else if (actionMasked2 == 5) {
                keyguardSecAffordanceView.mJustClicked = false;
                MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
                motionEventObtain.setAction(3);
                keyguardSecAffordanceView.dispatchTouchEvent(motionEventObtain);
                motionEventObtain.recycle();
                KeyguardSecAffordanceHelper keyguardSecAffordanceHelper3 = NotificationPanelViewController.this.mSecAffordanceHelper;
                if (keyguardSecAffordanceHelper3 != null) {
                    keyguardSecAffordanceHelper3.isShortcutPreviewSwipingInProgress = false;
                    return true;
                }
            }
        } else if (!keyguardSecAffordanceView.mTouchCancelled) {
            NotificationPanelViewController.this.mCentralSurfaces.userActivity();
            VelocityTracker velocityTracker4 = keyguardSecAffordanceView.mVelocityTracker;
            if (velocityTracker4 != null) {
                velocityTracker4.addMovement(motionEvent);
            }
            float f = keyguardSecAffordanceView.mRight ? keyguardSecAffordanceView.mInitialTouchX - x : x - keyguardSecAffordanceView.mInitialTouchX;
            if (f < 0.0f) {
                f = 0.0f;
            }
            float fHypot = (float) Math.hypot(f, keyguardSecAffordanceView.mInitialTouchY - y >= 0.0f ? r0 : 0.0f);
            float f2 = 5;
            if (fHypot >= f2) {
                Animator animator = keyguardSecAffordanceView.mInitialPeekAnimator;
                if (animator != null) {
                    animator.cancel();
                    List list2 = keyguardSecAffordanceView.mAnimatorSet;
                    if (list2 != null) {
                        ArrayList arrayList = (ArrayList) list2;
                        int size = arrayList.size();
                        int i2 = 0;
                        while (i2 < size) {
                            Object obj = arrayList.get(i2);
                            i2++;
                            ((AnimatorSet) obj).cancel();
                        }
                        keyguardSecAffordanceView.mAnimatorSet = null;
                    }
                }
                float f3 = fHypot + keyguardSecAffordanceView.mInitialPeekDistance;
                float f4 = keyguardSecAffordanceView.mShortcutLaunchDistance;
                if (f3 >= f4) {
                    f3 = ((f3 - f4) * 0.2f) + f4;
                }
                keyguardSecAffordanceView.mInitialPeekShowing = false;
                keyguardSecAffordanceView.mJustClicked = false;
                keyguardSecAffordanceView.mIsDown = false;
                keyguardSecAffordanceView.updatePanelViews(f3 - f2);
                keyguardSecAffordanceView.invalidate();
                return true;
            }
        }
        return true;
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public final void onUnlockedChanged() {
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        this.mIsSecure = ((KeyguardStateControllerImpl) keyguardStateController).mSecure;
        this.mTrusted = ((KeyguardStateControllerImpl) keyguardStateController).mTrusted;
        this.mCanDismissLockScreen = ((KeyguardStateControllerImpl) keyguardStateController).mCanDismissLockScreen;
    }

    @Override // com.android.systemui.widget.SystemUIImageView, android.view.View
    public final boolean performAccessibilityAction(int i, Bundle bundle) {
        if (i != 16) {
            return super.performAccessibilityAction(i, bundle);
        }
        DisplayMetrics displayMetrics = ((ImageView) this).mContext.getResources().getDisplayMetrics();
        if (!this.mDeviceInteractive) {
            return true;
        }
        launchShortcut(displayMetrics.widthPixels, displayMetrics.heightPixels);
        return true;
    }

    @Override // com.android.systemui.statusbar.KeyguardAffordanceView, android.view.View
    public final boolean performClick() {
        if (isClickable()) {
            return super.performClick();
        }
        return false;
    }

    public final void reset(boolean z) {
        mIsLaunchPanelRunning = false;
        if ((mIsShowBouncerAnimation && this.mBlurPanelView != null) || mWaitForReset) {
            Log.d("KeyguardSecAffordanceView", "WaitForReset ".concat(getShortcutType()));
            return;
        }
        if (!z && !this.mKeyguardStateController.isVisible()) {
            Log.d("KeyguardSecAffordanceView", "Cancel reset keyguard not showing ".concat(getShortcutType()));
            resetBlurRectangleView();
            return;
        }
        if (hasMessages(1001)) {
            removeMessages(1001);
        }
        Log.d("KeyguardSecAffordanceView", "reset ".concat(getShortcutType()));
        this.mIsNoUnlockNeeded = false;
        this.mIsTransitIconNeeded = false;
        mIsShowBouncerAnimation = false;
        this.mIsShortcutLaunching = false;
        this.mIsAnimFromNowBarRunning = false;
        cancelAnimator(this.mRectangleScaleAnimator);
        cancelAnimator(this.mRectangleIconScaleAnimator);
        cancelAnimator(this.mRectangleAlphaAnimator);
        cancelAllAnimators();
        List list = this.mAnimatorSet;
        if (list != null) {
            ArrayList arrayList = (ArrayList) list;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((AnimatorSet) obj).cancel();
            }
            this.mAnimatorSet = null;
        }
        updatePanelViews(0.0f);
        this.mRectangleIconDrawable = null;
        setRectangleColor();
        this.mFling = false;
        this.mRectangleIconBounds.set(0, 0, 0, 0);
        this.mRectangleBounds.set(0, 0, 0, 0);
        invalidate();
        resetBlurRectangleView();
    }

    public final void resetBlurRectangleView() {
        View view = this.mBlurPanelView;
        if (view == null) {
            return;
        }
        if (mIsLaunchPanelRunning) {
            Log.d("KeyguardSecAffordanceView", "dont reset panel anim running");
            return;
        }
        view.semSetBlurEnabled(false);
        this.mPanelBackground.setBackground(null);
        this.mPanelIcon.setImageDrawable(null);
        this.mBlurPanelRoot.setVisibility(8);
    }

    public final void sendBlockingTouchAreaToWallpaper(Rect rect) {
        if (this.mLastBlockingArea != rect) {
            Bundle bundle = new Bundle();
            bundle.putInt("which", 2);
            bundle.putString("id", this.mRight ? "rightShortcut" : "leftShortcut");
            bundle.putInt("left", rect.left);
            bundle.putInt("right", rect.right);
            bundle.putInt("top", rect.top);
            bundle.putInt("bottom", rect.bottom);
            if (getWindowToken() != null) {
                WallpaperManager.getInstance(((ImageView) this).mContext).sendWallpaperCommand(getWindowToken(), "samsung.android.wallpaper.blocktoucharea", 0, 0, 0, bundle);
            }
            this.mLastBlockingArea = rect;
            Log.d("KeyguardSecAffordanceView", "sendBlockingTouchAreaToWallpaper rect: " + rect + ", nowBarVisible: " + this.mIsNowBarVisible + ", " + getShortcutType());
        }
    }

    @Override // android.view.View
    public final void setAlpha(float f) {
        super.setAlpha(f);
        setImageAlpha(f, false);
    }

    public final void setBackgroundCircleColor() {
        if (this.mDrawBackgroundColor == -1 && (!this.mShortcutManager.isSupportBlur() || this.mIsDrawBackgroundCircle)) {
            if (this.mShortcutManager.isReduceTransparencyEnabled) {
                this.mBackgroundCirclePaint.setColor(getContext().getColor(this.mIsWhiteWallpaper ? R.color.shortcut_bg_color_accessibility_black_tint : R.color.shortcut_bg_color_accessibility_white_tint));
                this.mMaxBackgroundAlpha = 255;
                this.mTaskOnCirclePaint.setColor(this.mIsWhiteWallpaper ? -1 : -16777216);
            } else {
                this.mBackgroundCirclePaint.setColor(getContext().getColor(this.mIsWhiteWallpaper ? R.color.shortcut_bg_color_black_tint : R.color.shortcut_bg_color_white_tint));
                boolean z = this.mIsWhiteWallpaper;
                this.mMaxBackgroundAlpha = (int) (z ? 40.800000000000004d : 127.5d);
                this.mTaskOnCirclePaint.setColor(z ? -16777216 : -1);
            }
            this.mMaxTaskOnBackgroundAlpha = 127;
            this.mTaskOnCirclePaint.setAlpha((this.mIsTaskTypeShortcut && this.mIsTaskTypeShortcutEnabled) ? 127 : 0);
            this.mBackgroundCirclePaint.setAlpha(this.mMaxBackgroundAlpha);
        }
        invalidate();
    }

    public final void setForegroundCircleColor() {
        int color = 0;
        this.mMaxForegroundAlpha = 0;
        if (this.mShortcutManager.isMonotoneIcon(this.mRight ? 1 : 0)) {
            KeyguardShortcutManager keyguardShortcutManager = this.mShortcutManager;
            int i = keyguardShortcutManager.wallpaperBrightness;
            if (keyguardShortcutManager.isReduceTransparencyEnabled) {
                color = this.mIsWhiteWallpaper ? Color.parseColor("#FFFFFF") : Color.parseColor("#979797");
                this.mMaxForegroundAlpha = (int) (this.mIsWhiteWallpaper ? 102.0d : 127.5d);
            } else if (!LsRune.LOCKUI_SHORTCUT_BLUR_BG || i == -1) {
                color = Color.parseColor(this.mIsWhiteWallpaper ? "#2D2D30" : "#E4E4E4");
                this.mMaxForegroundAlpha = (int) (this.mIsWhiteWallpaper ? 25.5d : 51.0d);
            } else if (i >= 0 && i <= 28) {
                color = Color.parseColor("#FFFFFF");
                this.mMaxForegroundAlpha = 51;
            } else if (29 > i || i <= 84) {
                color = Color.parseColor("#000000");
                this.mMaxForegroundAlpha = 25;
            } else {
                color = Color.parseColor("#000000");
                this.mMaxForegroundAlpha = 25;
            }
        }
        this.mForegroundCirclePaint.setColor(color);
        this.mForegroundCirclePaint.setAlpha(this.mMaxForegroundAlpha);
        invalidate();
    }

    public final void setImageAlpha(float f, boolean z) {
        setImageAlpha(f, z, -1L, 0L, null, false);
    }

    @Override // android.widget.ImageView
    public final void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        this.mIsTaskTypeShortcut = this.mShortcutManager.isTaskType(this.mRight ? 1 : 0);
        if (this.mShortcutManager.isMonotoneIcon(this.mRight ? 1 : 0)) {
            setScaleType(ImageView.ScaleType.CENTER);
        } else {
            setScaleType(ImageView.ScaleType.FIT_XY);
        }
        this.mIsTaskTypeShortcutEnabled = false;
        post(new KeyguardSecAffordanceView$$ExternalSyntheticLambda5(this, 0));
    }

    public final void setImageScale(float f, boolean z) {
        cancelAnimator(this.mBottomIconScaleAnimator);
        float f2 = this.mImageScale;
        if (f == f2) {
            return;
        }
        if (!z) {
            this.mImageScale = f;
            invalidate();
            return;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f2, f);
        this.mBottomIconScaleAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda2(this, 1));
        valueAnimatorOfFloat.addListener(this.mBottomIconScaleEndListener);
        valueAnimatorOfFloat.setInterpolator(SCALE_INTERPOLATOR);
        valueAnimatorOfFloat.setDuration(300L);
        valueAnimatorOfFloat.start();
    }

    public final void setIsDrawBackgroundCircle(boolean z) {
        this.mIsDrawBackgroundCircle = z;
        setBackgroundCircleColor();
        setForegroundCircleColor();
        this.mForegroundCirclePaint.setAlpha(getForegroundAlpha((int) (getAlpha() * 255.0f)));
    }

    public final void setNowBarExpandMode(boolean z) {
        this.mIsNowBarExpanded = z;
        if (this.mDeviceInteractive && this.mIsNowBarVisible) {
            if (!z) {
                updateBgBlur(this.mKeyguardStateController.isVisible());
            }
            this.mIsAnimFromNowBarRunning = true;
            boolean z2 = this.mIsNowBarExpanded;
            setImageAlpha(z2 ? 0.0f : 1.0f, true, z2 ? 200L : 300L, z2 ? 50L : 0L, NOW_BAR_INTERPOLATOR, true);
            return;
        }
        Log.d("KeyguardSecAffordanceView", "setNowBarExpandMode interactive: " + this.mDeviceInteractive + ", mIsNowBarVisible: " + this.mIsNowBarVisible + ", " + getShortcutType());
    }

    public final void setNowBarVisibility(final boolean z, final Consumer consumer, Consumer consumer2, boolean z2) {
        int i;
        final boolean zIsVisible = ((KeyguardVisibilityMonitor) Dependency.sDependency.getDependencyInner(KeyguardVisibilityMonitor.class)).isVisible();
        StringBuilder sb = new StringBuilder("setNowBarVisibility old: ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, this.mIsNowBarVisible, ", new: ", z, ", ");
        sb.append(getShortcutType());
        Log.d("KeyguardSecAffordanceView", sb.toString());
        if (this.mIsNowBarVisible == z) {
            consumer2.accept(Boolean.FALSE);
            if (getWidth() == this.mShortcutManager.getShortcutIconSizeValue(z)) {
                Log.d("KeyguardSecAffordanceView", "setNowBarVisibility same visibility skipped ".concat(getShortcutType()));
                return;
            }
            Log.d("KeyguardSecAffordanceView", "setNowBarVisibility same visibility but different size ".concat(getShortcutType()));
        }
        if (!z2 || !this.mDeviceInteractive || !zIsVisible) {
            this.mIsNowBarVisible = z;
            if (zIsVisible) {
                updateBgBlur(true);
            }
            consumer.accept(Boolean.valueOf(z));
            cancelResizeAnimators();
            StringBuilder sb2 = new StringBuilder("setNowBarVisibility animate: ");
            sb2.append(z2);
            sb2.append(", mDeviceInteractive: ");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb2, this.mDeviceInteractive, ", isKeyguardVisible: ", zIsVisible, ", ");
            sb2.append(getShortcutType());
            Log.d("KeyguardSecAffordanceView", sb2.toString());
            return;
        }
        int shortcutIconSizeValue = this.mShortcutManager.getShortcutIconSizeValue(this.mIsNowBarVisible);
        int shortcutIconSizeValue2 = this.mShortcutManager.getShortcutIconSizeValue(z);
        if (shortcutIconSizeValue == shortcutIconSizeValue2) {
            consumer2.accept(Boolean.FALSE);
            Log.d("KeyguardSecAffordanceView", "setNowBarVisibility same size ".concat(getShortcutType()));
            return;
        }
        cancelResizeAnimators();
        int shortcutSideMargin = this.mShortcutManager.getShortcutSideMargin();
        int shortcutSideMargin2 = this.mShortcutManager.getShortcutSideMargin();
        int shortcutBottomMargin = this.mShortcutManager.getShortcutBottomMargin(this.mIsNowBarVisible);
        int shortcutBottomMargin2 = this.mShortcutManager.getShortcutBottomMargin(z);
        this.mIsNowBarVisible = z;
        SpringAnimation springAnimation = new SpringAnimation(new FloatValueHolder(shortcutIconSizeValue));
        springAnimation.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(200.0f, 0.6f);
        this.mNowBarVisibilitySizeAnimation = springAnimation;
        springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda12
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z3, float f, float f2) {
                Consumer consumer3 = consumer;
                Interpolator interpolator = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                consumer3.accept(Boolean.valueOf(z));
            }
        });
        final boolean zIsMonotoneIcon = this.mShortcutManager.isMonotoneIcon(this.mRight ? 1 : 0);
        final int i2 = 0;
        this.mNowBarVisibilitySizeAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener(this) { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda13
            public final /* synthetic */ KeyguardSecAffordanceView f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                boolean z3 = zIsVisible;
                boolean z4 = zIsMonotoneIcon;
                KeyguardSecAffordanceView keyguardSecAffordanceView = this.f$0;
                switch (i2) {
                    case 0:
                        Interpolator interpolator = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        int i3 = (int) f;
                        keyguardSecAffordanceView.setLeftTopRightBottom(keyguardSecAffordanceView.getLeft(), keyguardSecAffordanceView.getTop(), keyguardSecAffordanceView.getLeft() + i3, keyguardSecAffordanceView.getTop() + i3);
                        keyguardSecAffordanceView.mCenterX = keyguardSecAffordanceView.getWidth() / 2;
                        keyguardSecAffordanceView.mCenterY = keyguardSecAffordanceView.getHeight() / 2;
                        if (z4) {
                            keyguardSecAffordanceView.updateBgBlur(z3);
                            break;
                        }
                        break;
                    case 1:
                        Interpolator interpolator2 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        int i4 = (int) f;
                        keyguardSecAffordanceView.setLeftTopRightBottom(i4, keyguardSecAffordanceView.getTop(), keyguardSecAffordanceView.getWidth() + i4, keyguardSecAffordanceView.getHeight() + keyguardSecAffordanceView.getTop());
                        keyguardSecAffordanceView.mCenterX = keyguardSecAffordanceView.getWidth() / 2;
                        keyguardSecAffordanceView.mCenterY = keyguardSecAffordanceView.getHeight() / 2;
                        if (z4) {
                            keyguardSecAffordanceView.updateBgBlur(z3);
                            break;
                        }
                        break;
                    default:
                        Interpolator interpolator3 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        int i5 = (int) f;
                        keyguardSecAffordanceView.setLeftTopRightBottom(keyguardSecAffordanceView.getLeft(), i5, keyguardSecAffordanceView.getWidth() + keyguardSecAffordanceView.getLeft(), keyguardSecAffordanceView.getHeight() + i5);
                        keyguardSecAffordanceView.mCenterX = keyguardSecAffordanceView.getWidth() / 2;
                        keyguardSecAffordanceView.mCenterY = keyguardSecAffordanceView.getHeight() / 2;
                        if (z4) {
                            keyguardSecAffordanceView.updateBgBlur(z3);
                            break;
                        }
                        break;
                }
            }
        });
        this.mNowBarVisibilitySizeAnimation.animateToFinalPosition(shortcutIconSizeValue2);
        int left = getLeft();
        int i3 = (shortcutSideMargin2 - shortcutSideMargin) + left;
        if (this.mRight) {
            i3 += shortcutIconSizeValue - shortcutIconSizeValue2;
        }
        SpringAnimation springAnimation2 = new SpringAnimation(new FloatValueHolder(left));
        springAnimation2.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(200.0f, 0.6f);
        this.mNowBarVisibilityXAnimation = springAnimation2;
        final int i4 = 1;
        springAnimation2.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener(this) { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda13
            public final /* synthetic */ KeyguardSecAffordanceView f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                boolean z3 = zIsVisible;
                boolean z4 = zIsMonotoneIcon;
                KeyguardSecAffordanceView keyguardSecAffordanceView = this.f$0;
                switch (i4) {
                    case 0:
                        Interpolator interpolator = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        int i32 = (int) f;
                        keyguardSecAffordanceView.setLeftTopRightBottom(keyguardSecAffordanceView.getLeft(), keyguardSecAffordanceView.getTop(), keyguardSecAffordanceView.getLeft() + i32, keyguardSecAffordanceView.getTop() + i32);
                        keyguardSecAffordanceView.mCenterX = keyguardSecAffordanceView.getWidth() / 2;
                        keyguardSecAffordanceView.mCenterY = keyguardSecAffordanceView.getHeight() / 2;
                        if (z4) {
                            keyguardSecAffordanceView.updateBgBlur(z3);
                            break;
                        }
                        break;
                    case 1:
                        Interpolator interpolator2 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        int i42 = (int) f;
                        keyguardSecAffordanceView.setLeftTopRightBottom(i42, keyguardSecAffordanceView.getTop(), keyguardSecAffordanceView.getWidth() + i42, keyguardSecAffordanceView.getHeight() + keyguardSecAffordanceView.getTop());
                        keyguardSecAffordanceView.mCenterX = keyguardSecAffordanceView.getWidth() / 2;
                        keyguardSecAffordanceView.mCenterY = keyguardSecAffordanceView.getHeight() / 2;
                        if (z4) {
                            keyguardSecAffordanceView.updateBgBlur(z3);
                            break;
                        }
                        break;
                    default:
                        Interpolator interpolator3 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        int i5 = (int) f;
                        keyguardSecAffordanceView.setLeftTopRightBottom(keyguardSecAffordanceView.getLeft(), i5, keyguardSecAffordanceView.getWidth() + keyguardSecAffordanceView.getLeft(), keyguardSecAffordanceView.getHeight() + i5);
                        keyguardSecAffordanceView.mCenterX = keyguardSecAffordanceView.getWidth() / 2;
                        keyguardSecAffordanceView.mCenterY = keyguardSecAffordanceView.getHeight() / 2;
                        if (z4) {
                            keyguardSecAffordanceView.updateBgBlur(z3);
                            break;
                        }
                        break;
                }
            }
        });
        this.mNowBarVisibilityXAnimation.animateToFinalPosition(i3);
        int top = getTop();
        if (z) {
            i = (shortcutIconSizeValue - shortcutIconSizeValue2) + (top - (shortcutBottomMargin2 - shortcutBottomMargin));
        } else {
            i = ((shortcutBottomMargin - shortcutBottomMargin2) + top) - (shortcutIconSizeValue2 - shortcutIconSizeValue);
        }
        SpringAnimation springAnimation3 = new SpringAnimation(new FloatValueHolder(top));
        springAnimation3.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(200.0f, 0.6f);
        this.mNowBarVisibilityYAnimation = springAnimation3;
        final int i5 = 2;
        springAnimation3.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener(this) { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda13
            public final /* synthetic */ KeyguardSecAffordanceView f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                boolean z3 = zIsVisible;
                boolean z4 = zIsMonotoneIcon;
                KeyguardSecAffordanceView keyguardSecAffordanceView = this.f$0;
                switch (i5) {
                    case 0:
                        Interpolator interpolator = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        int i32 = (int) f;
                        keyguardSecAffordanceView.setLeftTopRightBottom(keyguardSecAffordanceView.getLeft(), keyguardSecAffordanceView.getTop(), keyguardSecAffordanceView.getLeft() + i32, keyguardSecAffordanceView.getTop() + i32);
                        keyguardSecAffordanceView.mCenterX = keyguardSecAffordanceView.getWidth() / 2;
                        keyguardSecAffordanceView.mCenterY = keyguardSecAffordanceView.getHeight() / 2;
                        if (z4) {
                            keyguardSecAffordanceView.updateBgBlur(z3);
                            break;
                        }
                        break;
                    case 1:
                        Interpolator interpolator2 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        int i42 = (int) f;
                        keyguardSecAffordanceView.setLeftTopRightBottom(i42, keyguardSecAffordanceView.getTop(), keyguardSecAffordanceView.getWidth() + i42, keyguardSecAffordanceView.getHeight() + keyguardSecAffordanceView.getTop());
                        keyguardSecAffordanceView.mCenterX = keyguardSecAffordanceView.getWidth() / 2;
                        keyguardSecAffordanceView.mCenterY = keyguardSecAffordanceView.getHeight() / 2;
                        if (z4) {
                            keyguardSecAffordanceView.updateBgBlur(z3);
                            break;
                        }
                        break;
                    default:
                        Interpolator interpolator3 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        int i52 = (int) f;
                        keyguardSecAffordanceView.setLeftTopRightBottom(keyguardSecAffordanceView.getLeft(), i52, keyguardSecAffordanceView.getWidth() + keyguardSecAffordanceView.getLeft(), keyguardSecAffordanceView.getHeight() + i52);
                        keyguardSecAffordanceView.mCenterX = keyguardSecAffordanceView.getWidth() / 2;
                        keyguardSecAffordanceView.mCenterY = keyguardSecAffordanceView.getHeight() / 2;
                        if (z4) {
                            keyguardSecAffordanceView.updateBgBlur(z3);
                            break;
                        }
                        break;
                }
            }
        });
        this.mNowBarVisibilityYAnimation.animateToFinalPosition(i);
    }

    public final void setRectangleBounds(float f) {
        float f2;
        float f3;
        int i;
        float f4;
        float f5;
        int i2;
        float f6 = this.mScreenHeight;
        float f7 = this.mVerticalScale;
        float f8 = (f6 * f7) / 2.0f;
        float f9 = f6 - f8;
        float f10 = this.mRectangleIconScale;
        int i3 = this.mRectangleIconSize;
        int i4 = (int) (i3 * f10);
        int i5 = this.mRectangleIconMargin;
        int iM = i5 - ((i4 - i3) / 2);
        int i6 = (i5 * 2) + i4;
        float f11 = this.mRectangleDistanceCovered;
        if (f11 > i6) {
            iM = AbsActionBarView$$ExternalSyntheticOutline0.m((int) f11, i6, 2, iM);
        }
        int i7 = i4 / 2;
        int i8 = (int) ((((f9 - f8) / 2.0f) + f8) - i7);
        int i9 = i8 + i4;
        boolean z = this.mRight;
        if (z) {
            int i10 = this.mScreenWidth;
            float f12 = i10;
            float f13 = f12 - f;
            f5 = f13 >= 0.0f ? f13 : 0.0f;
            float f14 = f5 + f12;
            if (f14 > f12) {
                f14 = i10 + 78;
            }
            i2 = ((int) f5) + iM;
            int i11 = (i10 / 2) - i7;
            if (i2 < i11) {
                i2 = i11;
            }
            i = i2 + i4;
            if (i > i10 + i4) {
                f4 = f14;
                i = 0;
                i8 = 0;
                i2 = 0;
                i9 = 0;
            } else {
                f4 = f14;
            }
        } else {
            int i12 = this.mScreenWidth;
            float f15 = i12;
            if (f > f15) {
                f2 = 0.0f;
                f3 = f15;
            } else {
                f2 = 0.0f;
                f3 = f;
            }
            float f16 = f3 - f15;
            if (f16 < f2) {
                f16 = -78.0f;
            }
            int i13 = ((int) f3) - iM;
            int i14 = (i3 / 2) + (i12 / 2);
            i = i13 > i14 ? i14 : i13;
            int i15 = i - i4;
            if (i15 < i3 * (-1)) {
                f4 = f3;
                i = 0;
                i8 = 0;
                i9 = 0;
                f5 = f16;
                i2 = 0;
            } else {
                f4 = f3;
                f5 = f16;
                i2 = i15;
            }
        }
        float f17 = z ? this.mScreenWidth - f5 : f4;
        this.mRectangleDistanceCovered = f17;
        if (f17 < this.mScreenWidth) {
            f6 = f9;
        }
        this.mRectangleScaleStart = f7;
        this.mRectangleIconScaleStart = f10;
        float f18 = this.mShortcutLaunchDistance;
        int i16 = 102;
        if (f17 >= f18 && !this.mLaunchThresholdAchieved) {
            Log.d("KeyguardSecAffordanceView", "updateOnThreshold launch achieved ".concat(getShortcutType()));
            this.mLaunchThresholdAchieved = true;
            startRectangleScaleAnimation(0.1f);
            startRectangleIconScaleAnimation();
            this.mRectangleIconAlpha = 255;
            if (this.mIsTaskTypeShortcut) {
                boolean z2 = this.mIsTaskTypeShortcutEnabled;
                this.mRectangleIconAlpha = z2 ? 102 : 255;
                i = z2 ? 102 : 204;
                updateRectangleIconDrawable(this.mIsTransitIconNeeded);
            }
            startRectangleAlphaAnimation(i);
            this.mVibrationUtil.playVibration(108);
        } else if (f17 < f18 && this.mLaunchThresholdAchieved) {
            Log.d("KeyguardSecAffordanceView", "updateOnThreshold launch not achieved ".concat(getShortcutType()));
            this.mLaunchThresholdAchieved = false;
            startRectangleScaleAnimation(0.2f);
            startRectangleIconScaleAnimation();
            this.mRectangleIconAlpha = 102;
            if (this.mIsTaskTypeShortcut) {
                boolean z3 = this.mIsTaskTypeShortcutEnabled;
                this.mRectangleIconAlpha = z3 ? 255 : 102;
                i = z3 ? 204 : 102;
                updateRectangleIconDrawable(false);
                i16 = i;
            }
            startRectangleAlphaAnimation(i16);
            this.mVibrationUtil.playVibration(109);
        }
        this.mRectangleBounds.set((int) f5, (int) f8, (int) f4, (int) f6);
        this.mRectangleIconBounds.set(i2, i8, i, i9);
    }

    public final void setRectangleColor() {
        if (this.mShortcutManager.isDarkPanel(this.mRight ? 1 : 0)) {
            this.mRectangleColor = Color.parseColor("#262626");
        } else {
            this.mRectangleColor = Color.parseColor("#F2F2F2");
        }
        this.mRectanglePaint.setColor(this.mRectangleColor);
        PaintDrawable paintDrawable = this.mPanelBackgroundDrawable;
        if (paintDrawable != null) {
            paintDrawable.getPaint().setColor(this.mRectangleColor);
        }
    }

    @Override // com.android.systemui.animation.LaunchableView
    public final void setShouldBlockVisibilityChanges(boolean z) {
        this.delegate.setShouldBlockVisibilityChanges(z);
    }

    public final void setUScaleAnimator(View view, float f, float f2) {
        if (view == null) {
            return;
        }
        int y = (int) view.getY();
        View view2 = this.mClockView;
        if (view == view2) {
            view.setPivotX(view2.getWidth() / 2.0f);
            int i = this.mAffordancePivotY;
            if (y < i) {
                view.setPivotY(i);
            } else {
                view.setPivotY(i * (-1));
            }
        } else {
            if (view == this.mNotificationStackScrollerView) {
                view.setPivotX(r0.getWidth() / 2.0f);
                view.setPivotY(this.mAffordancePivotY);
            } else if (view == this.mNotificationPanelIconOnlyContainer) {
                int height = view2 != null ? view2.getHeight() : 0;
                view.setPivotX(this.mNotificationPanelIconOnlyContainer.getWidth() / 2.0f);
                view.setPivotY(this.mAffordancePivotY - height);
            }
        }
        view.setScaleX(f);
        view.setScaleY(f);
        view.setAlpha(f2);
    }

    @Override // com.android.systemui.widget.SystemUIImageView, android.widget.ImageView, android.view.View
    public final void setVisibility(int i) {
        this.delegate.setVisibility(i);
        if (i != 0) {
            sendBlockingTouchAreaToWallpaper(new Rect(0, 0, 0, 0));
        }
    }

    public final void startRectangleAlphaAnimation(int i) {
        cancelAnimator(this.mRectangleShrinkAlphaAnimator);
        cancelAnimator(this.mRectangleAlphaAnimator);
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.mRectanglePaint.getAlpha(), i);
        this.mRectangleAlphaAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.setDuration(50L);
        valueAnimatorOfInt.setInterpolator(ALPHA_INTERPOLATOR);
        valueAnimatorOfInt.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda2(this, 6));
        valueAnimatorOfInt.addListener(this.mRectangleAlphaAnimatorEndListener);
        this.mRectangleAlphaAnimator.start();
    }

    public final void startRectangleIconScaleAnimation() {
        cancelAnimator(this.mRectangleIconScaleAnimator);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mRectangleIconScaleStart, 1.0f);
        this.mRectangleIconScaleAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(450L);
        valueAnimatorOfFloat.setInterpolator(SCALE_INTERPOLATOR);
        valueAnimatorOfFloat.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda2(this, 2));
        valueAnimatorOfFloat.addListener(this.mRectangleIconScaleAnimatorEndListener);
        this.mRectangleIconScaleAnimator.start();
    }

    public final void startRectangleScaleAnimation(float f) {
        cancelAnimator(this.mRectangleScaleAnimator);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mRectangleScaleStart, f);
        this.mRectangleScaleAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(450L);
        valueAnimatorOfFloat.setInterpolator(SCALE_INTERPOLATOR);
        valueAnimatorOfFloat.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda2(this, 5));
        valueAnimatorOfFloat.addListener(this.mRectangleScaleAnimatorEndListener);
        this.mRectangleScaleAnimator.start();
    }

    public final void startRectangleShrinkAnimation() {
        Log.i("KeyguardSecAffordanceView", "startRectangleShrinkAnimation");
        setImageAlpha(1.0f, true);
        setImageScale(1.0f, true);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mRectangleDistanceCovered, 0.0f);
        this.mRectangleShrinkAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(this.mInitialPeekShowing ? 200L : 450L);
        valueAnimatorOfFloat.setInterpolator(SCALE_INTERPOLATOR);
        valueAnimatorOfFloat.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda2(this, 7));
        valueAnimatorOfFloat.addListener(this.mRectangleShrinkAnimatorEndListener);
        this.mRectangleShrinkAnimator.start();
    }

    public final void updateBgBlur(boolean z) {
        if (this.mShortcutManager.isSupportBlur() && this.mDeviceInteractive) {
            semSetBlurInfo((z && this.mShortcutManager.isMonotoneIcon(this.mRight ? 1 : 0)) ? new SemBlurInfo.Builder(0).setBackgroundCornerRadius(getWidth() / 2.0f).setColorCurvePreset(this.mShortcutManager.getColorCurvePreset(this.mRight ? 1 : 0)).build() : null);
        }
    }

    public final void updateDisplayParameters() throws Resources.NotFoundException {
        Resources resources = ((ImageView) this).mContext.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int dimensionPixelSize = resources.getDimensionPixelSize(android.R.dimen.select_dialog_drawable_padding_start_material);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.status_bar_height);
        if (this.mIsLandScape) {
            this.mScreenHeight = displayMetrics.heightPixels;
            this.mScreenWidth = displayMetrics.widthPixels + dimensionPixelSize + dimensionPixelSize2;
            this.mVerticalScale = 0.2f;
            this.mInitialPeekDistance = resources.getDimensionPixelSize(R.dimen.keyguard_affordance_initial_view_out_landscape);
        } else {
            this.mScreenWidth = displayMetrics.widthPixels;
            this.mScreenHeight = displayMetrics.heightPixels + dimensionPixelSize + dimensionPixelSize2;
            this.mVerticalScale = 0.2f;
            this.mInitialPeekDistance = resources.getDimensionPixelSize(R.dimen.keyguard_affordance_initial_view_out);
        }
        if (getRootView() != null) {
            this.mScreenHeight = getRootView().getHeight();
            this.mScreenWidth = getRootView().getWidth();
        }
        this.mAffordancePivotY = this.mScreenHeight / 2;
        float dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.shortcut_launch_thresold);
        this.mShortcutLaunchDistance = dimensionPixelSize3;
        this.mRectangleIconMargin = (int) (dimensionPixelSize3 / 2.0f);
        this.mForegroundCirclePaint.setStrokeWidth((float) (getResources().getDisplayMetrics().density * 0.5d));
    }

    public final void updatePanelViews(float f) {
        float f2;
        float fMax = Math.max(0.0f, f - this.mInitialPeekDistance);
        View view = this.mPanelDimView;
        if (view != null) {
            if (this.mIsDown) {
                f2 = 0.0f;
            } else {
                float f3 = fMax / this.mShortcutLaunchDistance;
                f2 = this.mMaxDimBackground;
                float f4 = f3 * f2;
                if (f4 <= f2) {
                    f2 = f4;
                }
            }
            view.setAlpha(f2);
        }
        if (this.mBlurPanelView != null) {
            this.mBlurPanelRadius = 0;
            if (!this.mIsDown) {
                int i = (int) ((fMax / this.mShortcutLaunchDistance) * 400.0f);
                this.mBlurPanelRadius = i;
                if (i > 400) {
                    this.mBlurPanelRadius = 400;
                }
            }
        }
        if (this.mIsDown) {
            setUScaleAnimator(this.mNotificationPanelView, 1.0f, 1.0f);
        } else {
            float f5 = this.mShortcutLaunchDistance;
            float f6 = 1.0f - (fMax / f5);
            if (f6 < 0.0f) {
                f6 = 0.0f;
            }
            float f7 = 1.0f - ((fMax / f5) * 0.050000012f);
            if (f7 < 0.95f) {
                f7 = 0.95f;
            }
            setUScaleAnimator(this.mNotificationPanelView, fMax != 0.0f ? f7 : 1.0f, f6);
        }
        setRectangleBounds(f);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0094 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00a4  */
    /* JADX WARN: Type inference failed for: r0v0, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateRectangleIconDrawable(boolean z) {
        Drawable drawable;
        Drawable drawable2;
        Bitmap bitmap;
        BitmapDrawable bitmapDrawable;
        KeyguardShortcutManager keyguardShortcutManager;
        boolean zIsDarkPanel;
        ?? r0 = this.mRight;
        KeyguardShortcutManager keyguardShortcutManager2 = this.mShortcutManager;
        keyguardShortcutManager2.getClass();
        String packageName = null;
        if (r0 < 0 || r0 >= 2) {
            ClockEventController$$ExternalSyntheticOutline0.m(r0 == true ? 1 : 0, "IllegalArgument : ", "KeyguardShortcutManager");
            drawable = null;
        } else {
            KeyguardShortcutManager.ShortcutData[] shortcutDataArr = keyguardShortcutManager2.shortcutsData;
            drawable = z ? shortcutDataArr[r0 == true ? 1 : 0].panelTransitDrawable : shortcutDataArr[r0 == true ? 1 : 0].panelDrawable;
        }
        this.mRectangleIconDrawable = drawable;
        if (this.mShortcutManager.isMonotoneIcon(this.mRight ? 1 : 0) && (drawable2 = this.mRectangleIconDrawable) != null) {
            if (this.mIsTaskTypeShortcut) {
                this.mRectangleIconDrawable = this.mShortcutManager.convertTaskDrawable(drawable2, !r6.isDarkPanel(r0 == true ? 1 : 0), false, true, false);
            } else {
                KeyguardShortcutManager keyguardShortcutManager3 = this.mShortcutManager;
                keyguardShortcutManager3.getClass();
                if (r0 < 0 || r0 >= 2) {
                    ClockEventController$$ExternalSyntheticOutline0.m(r0 == true ? 1 : 0, "IllegalArgument : ", "KeyguardShortcutManager");
                } else {
                    KeyguardShortcutManager.ShortcutData[] shortcutDataArr2 = keyguardShortcutManager3.shortcutsData;
                    if (z) {
                        Drawable drawable3 = shortcutDataArr2[r0 == true ? 1 : 0].panelTransitDrawable;
                        if (drawable3 != null) {
                            bitmap = DrawableKt.toBitmap(drawable3, drawable3.getIntrinsicWidth(), drawable3.getIntrinsicHeight(), null);
                        }
                        bitmapDrawable = bitmap != null ? new BitmapDrawable(((ImageView) this).mContext.getResources(), bitmap) : null;
                        keyguardShortcutManager = this.mShortcutManager;
                        zIsDarkPanel = keyguardShortcutManager.isDarkPanel(r0 == true ? 1 : 0);
                        KeyguardShortcutManager keyguardShortcutManager4 = this.mShortcutManager;
                        keyguardShortcutManager4.getClass();
                        if (r0 < 0 || r0 >= 2) {
                            ClockEventController$$ExternalSyntheticOutline0.m(r0 == true ? 1 : 0, "IllegalArgument : ", "KeyguardShortcutManager");
                        } else {
                            ComponentName componentName = keyguardShortcutManager4.shortcutsData[r0 == true ? 1 : 0].componentName;
                            if (componentName != null) {
                                packageName = componentName.getPackageName();
                            }
                        }
                        if (bitmapDrawable != null && !KeyguardShortcutManager.isARShortcutIcon(packageName)) {
                            bitmapDrawable.mutate().setColorFilter(new BlendModeColorFilter(keyguardShortcutManager.getInvertColor(zIsDarkPanel, true), BlendMode.SRC_ATOP));
                        }
                        this.mRectangleIconDrawable = bitmapDrawable;
                    } else {
                        Drawable drawable4 = shortcutDataArr2[r0 == true ? 1 : 0].panelDrawable;
                        if (drawable4 != null) {
                            bitmap = DrawableKt.toBitmap(drawable4, drawable4.getIntrinsicWidth(), drawable4.getIntrinsicHeight(), null);
                        }
                        if (bitmap != null) {
                        }
                        keyguardShortcutManager = this.mShortcutManager;
                        zIsDarkPanel = keyguardShortcutManager.isDarkPanel(r0 == true ? 1 : 0);
                        KeyguardShortcutManager keyguardShortcutManager42 = this.mShortcutManager;
                        keyguardShortcutManager42.getClass();
                        if (r0 < 0) {
                            ClockEventController$$ExternalSyntheticOutline0.m(r0 == true ? 1 : 0, "IllegalArgument : ", "KeyguardShortcutManager");
                            if (bitmapDrawable != null) {
                                bitmapDrawable.mutate().setColorFilter(new BlendModeColorFilter(keyguardShortcutManager.getInvertColor(zIsDarkPanel, true), BlendMode.SRC_ATOP));
                            }
                            this.mRectangleIconDrawable = bitmapDrawable;
                        }
                    }
                }
                bitmap = null;
                if (bitmap != null) {
                }
                keyguardShortcutManager = this.mShortcutManager;
                zIsDarkPanel = keyguardShortcutManager.isDarkPanel(r0 == true ? 1 : 0);
                KeyguardShortcutManager keyguardShortcutManager422 = this.mShortcutManager;
                keyguardShortcutManager422.getClass();
                if (r0 < 0) {
                }
            }
        }
        ImageView imageView = this.mPanelIcon;
        if (imageView != null) {
            imageView.setImageDrawable(this.mRectangleIconDrawable);
        }
    }

    public final void updateStyle() {
        boolean zIsWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper("navibar");
        if (this.mIsWhiteWallpaper != zIsWhiteKeyguardWallpaper) {
            updateBgBlur(((KeyguardVisibilityMonitor) Dependency.sDependency.getDependencyInner(KeyguardVisibilityMonitor.class)).isVisible());
        }
        this.mIsWhiteWallpaper = zIsWhiteKeyguardWallpaper;
        this.mMaxDimBackground = zIsWhiteKeyguardWallpaper ? 0.08f : 0.25f;
        setBackgroundCircleColor();
        setForegroundCircleColor();
        this.mShortcutManager.updateShortcuts();
    }

    public KeyguardSecAffordanceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final void setImageAlpha(float f, boolean z, long j, long j2, Interpolator interpolator, boolean z2) {
        if (this.mFling) {
            return;
        }
        if (!this.mIsAnimFromNowBarRunning || z2) {
            cancelAnimator(this.mBottomIconAlphaAnimator);
            int i = (int) (f * 255.0f);
            if (this.mIsNowBarExpanded) {
                i = 0;
            }
            if (i == getImageAlpha()) {
                this.mIsAnimFromNowBarRunning = false;
                return;
            }
            final Drawable background = getBackground();
            final Drawable foreground = getForeground();
            if (z) {
                ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(getImageAlpha(), i);
                this.mBottomIconAlphaAnimator = valueAnimatorOfInt;
                valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda4
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        KeyguardSecAffordanceView keyguardSecAffordanceView = this.f$0;
                        Drawable drawable = background;
                        Drawable drawable2 = foreground;
                        Interpolator interpolator2 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        keyguardSecAffordanceView.getClass();
                        int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        if (keyguardSecAffordanceView.mDrawBackgroundColor == -1) {
                            if (drawable != null) {
                                drawable.mutate().setAlpha(iIntValue);
                            }
                            if (drawable2 != null) {
                                drawable2.mutate().setAlpha(keyguardSecAffordanceView.getForegroundAlpha(iIntValue));
                            }
                            if (!keyguardSecAffordanceView.mShortcutManager.isSupportBlur() || keyguardSecAffordanceView.mIsDrawBackgroundCircle) {
                                Paint paint = keyguardSecAffordanceView.mTaskOnCirclePaint;
                                float f2 = iIntValue / 255.0f;
                                int i2 = keyguardSecAffordanceView.mMaxTaskOnBackgroundAlpha;
                                int i3 = (int) (i2 * f2);
                                if (i3 <= i2) {
                                    i2 = i3;
                                }
                                paint.setAlpha(i2);
                                Paint paint2 = keyguardSecAffordanceView.mBackgroundCirclePaint;
                                int i4 = keyguardSecAffordanceView.mMaxBackgroundAlpha;
                                int i5 = (int) (f2 * i4);
                                if (i5 <= i4) {
                                    i4 = i5;
                                }
                                paint2.setAlpha(i4);
                            }
                            keyguardSecAffordanceView.mForegroundCirclePaint.setAlpha(keyguardSecAffordanceView.getForegroundAlpha(iIntValue));
                        }
                        if (iIntValue <= 0) {
                            iIntValue = 1;
                        }
                        keyguardSecAffordanceView.setImageAlpha(iIntValue);
                    }
                });
                valueAnimatorOfInt.addListener(this.mBottomIconAlphaEndListener);
                if (interpolator == null) {
                    interpolator = ALPHA_INTERPOLATOR;
                }
                valueAnimatorOfInt.setInterpolator(interpolator);
                if (j == -1) {
                    j = 300;
                }
                valueAnimatorOfInt.setDuration(j);
                valueAnimatorOfInt.setStartDelay(j2);
                valueAnimatorOfInt.start();
                return;
            }
            int i2 = this.mDrawBackgroundColor;
            if (i2 == -1) {
                if (background != null && i2 == -1) {
                    background.mutate().setAlpha(i);
                }
                if (foreground != null) {
                    foreground.mutate().setAlpha(getForegroundAlpha(i));
                }
                if (!this.mShortcutManager.isSupportBlur() || this.mIsDrawBackgroundCircle) {
                    Paint paint = this.mTaskOnCirclePaint;
                    float f2 = i / 255.0f;
                    int i3 = this.mMaxTaskOnBackgroundAlpha;
                    int i4 = (int) (i3 * f2);
                    if (i4 <= i3) {
                        i3 = i4;
                    }
                    paint.setAlpha(i3);
                    Paint paint2 = this.mBackgroundCirclePaint;
                    int i5 = this.mMaxBackgroundAlpha;
                    int i6 = (int) (f2 * i5);
                    if (i6 <= i5) {
                        i5 = i6;
                    }
                    paint2.setAlpha(i5);
                }
                this.mForegroundCirclePaint.setAlpha(getForegroundAlpha(i));
            }
            if (i <= 0) {
                i = 1;
            }
            setImageAlpha(i);
            this.mIsAnimFromNowBarRunning = false;
        }
    }

    public KeyguardSecAffordanceView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$7] */
    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$8] */
    /* JADX WARN: Type inference failed for: r0v12, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$9] */
    /* JADX WARN: Type inference failed for: r0v13, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$10] */
    /* JADX WARN: Type inference failed for: r0v14, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$11] */
    /* JADX WARN: Type inference failed for: r0v15, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$12] */
    /* JADX WARN: Type inference failed for: r0v16, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$13] */
    /* JADX WARN: Type inference failed for: r0v17, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$14] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$1] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$4] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$5] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$6] */
    public KeyguardSecAffordanceView(Context context, AttributeSet attributeSet, int i, int i2) throws Resources.NotFoundException {
        super(context, attributeSet, i, i2);
        this.mRectangleIconBounds = new Rect();
        this.mRectangleBounds = new Rect();
        this.mLastBlockingArea = new Rect();
        this.mDeviceInteractive = true;
        this.mTouchCancelled = false;
        this.mRight = false;
        this.mIsTargetView = false;
        this.mJustClicked = false;
        this.mIsShortcutForPhone = false;
        this.mLaunchThresholdAchieved = false;
        this.mInitialPeekShowing = false;
        this.mIsDown = false;
        this.mIsTaskTypeShortcut = false;
        this.mIsTaskTypeShortcutEnabled = false;
        this.mIsTransitIconNeeded = false;
        this.mIsNoUnlockNeeded = false;
        this.mIsNowBarExpanded = false;
        this.mIsBlurBgApplied = false;
        this.mIsWhiteWallpaper = false;
        this.mIsDrawBackgroundCircle = false;
        this.mIsAnimFromNowBarRunning = false;
        this.mIsNowBarVisible = false;
        this.mRectangleIconAlpha = 255;
        this.mOldPanelBackgroundAlpha = 0;
        this.mBlurPanelRadius = 0;
        this.mMaxDimBackground = 0.25f;
        this.mDrawBackgroundColor = -1;
        this.mRectangleDistanceCovered = 0.0f;
        this.mImageScale = 1.0f;
        this.mVerticalScale = 0.2f;
        this.mRectangleIconScale = 1.0f;
        this.mVisibilityListener = new IntConsumer() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda0
            @Override // java.util.function.IntConsumer
            public final void accept(final int i3) {
                final KeyguardSecAffordanceView keyguardSecAffordanceView = this.f$0;
                Interpolator interpolator = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                if (i3 != 0 && KeyguardSecAffordanceView.mWaitForReset) {
                    KeyguardSecAffordanceView.mWaitForReset = false;
                    keyguardSecAffordanceView.reset(true);
                }
                keyguardSecAffordanceView.post(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda16
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardSecAffordanceView keyguardSecAffordanceView2 = keyguardSecAffordanceView;
                        int i4 = i3;
                        if (keyguardSecAffordanceView2.mIsNowBarExpanded) {
                            return;
                        }
                        keyguardSecAffordanceView2.updateBgBlur(i4 == 0);
                    }
                });
            }
        };
        this.mDisplayObserver = new DisplayLifecycle.Observer() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.1
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onDisplayChanged(int i3) throws Resources.NotFoundException {
                Interpolator interpolator = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                KeyguardSecAffordanceView.this.updateDisplayParameters();
            }
        };
        this.mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.2
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardBouncerStateChanged(boolean z) {
                if (z && KeyguardSecAffordanceView.mIsShowBouncerAnimation) {
                    KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                    if (keyguardSecAffordanceView.mBlurPanelView != null) {
                        KeyguardSecAffordanceView.mIsShowBouncerAnimation = false;
                        if (keyguardSecAffordanceView.mShortcutManager.isUnlockWaitNeeded(keyguardSecAffordanceView.mRight ? 1 : 0)) {
                            KeyguardSecAffordanceView.m2958$$Nest$mresetOnTimeout(keyguardSecAffordanceView);
                        } else {
                            keyguardSecAffordanceView.mHandler.sendEmptyMessageDelayed(1001, 150L);
                        }
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStartedGoingToSleep(int i3) {
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                keyguardSecAffordanceView.updateBgBlur(false);
                keyguardSecAffordanceView.mDeviceInteractive = false;
                if (!keyguardSecAffordanceView.mShortcutManager.isSupportBlur() || keyguardSecAffordanceView.mDrawBackgroundColor == -1) {
                    return;
                }
                keyguardSecAffordanceView.mDrawBackgroundColor = -1;
                keyguardSecAffordanceView.setIsDrawBackgroundCircle(false);
            }
        };
        new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                keyguardSecAffordanceView.getClass();
                if (keyguardSecAffordanceView.mDrawBackgroundColor != -1) {
                    keyguardSecAffordanceView.mDrawBackgroundColor = -1;
                    keyguardSecAffordanceView.setIsDrawBackgroundCircle(false);
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                keyguardSecAffordanceView.getClass();
                if (keyguardSecAffordanceView.mDrawBackgroundColor != -1) {
                    keyguardSecAffordanceView.mDrawBackgroundColor = -1;
                    keyguardSecAffordanceView.setIsDrawBackgroundCircle(false);
                }
            }
        };
        this.mBottomIconAlphaEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                keyguardSecAffordanceView.mBottomIconAlphaAnimator = null;
                keyguardSecAffordanceView.mIsAnimFromNowBarRunning = false;
            }
        };
        this.mRectangleShrinkAnimatorEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                keyguardSecAffordanceView.mRectangleShrinkAnimator = null;
                keyguardSecAffordanceView.mFling = false;
                keyguardSecAffordanceView.mRectangleBounds.set(0, 0, 0, 0);
                KeyguardSecAffordanceView.this.mRectangleIconBounds.set(0, 0, 0, 0);
                KeyguardSecAffordanceView.this.resetBlurRectangleView();
            }
        };
        this.mRectangleShrinkAlphaAnimatorEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView.this.mRectangleShrinkAlphaAnimator = null;
            }
        };
        this.mShortcutLaunchAnimatorEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.7
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                keyguardSecAffordanceView.mFling = false;
                KeyguardSecAffordanceHelper.Callback callback = keyguardSecAffordanceView.mHelperCallback;
                boolean z = keyguardSecAffordanceView.mRight;
                boolean zIsSecure$1 = keyguardSecAffordanceView.isSecure$1();
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                notificationPanelViewController.mIsLaunchTransitionRunning = true;
                KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_SHORTCUT);
                int i3 = (int) (0.0f / notificationPanelViewController.mCentralSurfaces.mDisplayMetrics.density);
                int iAbs = Math.abs(i3);
                int iAbs2 = Math.abs(i3);
                LockscreenGestureLogger lockscreenGestureLogger = notificationPanelViewController.mLockscreenGestureLogger;
                if (!z) {
                    lockscreenGestureLogger.write(190, iAbs, iAbs2);
                } else if (3 == notificationPanelViewController.mLastCameraLaunchSource) {
                    lockscreenGestureLogger.write(189, iAbs, iAbs2);
                }
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = notificationPanelViewController.mKeyguardSecBottomAreaViewController;
                if (keyguardSecBottomAreaViewController != null) {
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_LAUNCH_SHORTCUT, z ? "2" : "1");
                    String currentScreenID = SystemUIAnalytics.getCurrentScreenID();
                    String str = z ? SystemUIAnalytics.EID_LAUNCH_RIGHT_SHORTCUT : SystemUIAnalytics.EID_LAUNCH_LEFT_SHORTCUT;
                    KeyguardShortcutManager keyguardShortcutManager = keyguardSecBottomAreaViewController.shortcutManager;
                    SystemUIAnalytics.sendEventLog(currentScreenID, str, keyguardShortcutManager.getComponentNameForSALogging(z ? 1 : 0));
                    if (KeyguardShortcutManager.isSamsungCameraPackage(keyguardShortcutManager.shortcutsData[z ? 1 : 0].componentName)) {
                        CameraLauncher cameraLauncher = (CameraLauncher) ((CentralSurfacesImpl) ((CentralSurfaces) keyguardSecBottomAreaViewController.centralSurfacesLazy.get())).mCameraLauncherLazy.get();
                        CameraLaunchType cameraLaunchType = CameraLaunchType.QUICK_AFFORDANCE;
                        keyguardSecBottomAreaViewController.keyguardInteractor.getClass();
                        cameraLauncher.launchCamera(KeyguardInteractor.cameraLaunchSourceModelToInt(cameraLaunchType), true);
                    } else if (keyguardShortcutManager.isShortcutForPhone(z ? 1 : 0)) {
                        final TelecomManager telecomManagerFrom = TelecomManager.from(keyguardSecBottomAreaViewController.getContext());
                        if (telecomManagerFrom.isInManagedCall()) {
                            AsyncTask.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$launchPhone$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    telecomManagerFrom.showInCallScreen(false);
                                }
                            });
                        } else {
                            KeyguardShortcutManager.Companion.getClass();
                            keyguardSecBottomAreaViewController.activityStarter.startActivity(KeyguardShortcutManager.PHONE_INTENT, false);
                        }
                    } else {
                        keyguardSecBottomAreaViewController.quickAffordanceInteractor.onQuickAffordanceTriggered(((KeyguardQuickAffordanceConfig) ((ArrayList) keyguardShortcutManager.getQuickAffordanceConfigList()).get((z ? KeyguardQuickAffordancePosition.BOTTOM_END : KeyguardQuickAffordancePosition.BOTTOM_START).ordinal())).getKey(), null, z ? "bottom_end" : "bottom_start");
                    }
                }
                if (!zIsSecure$1) {
                    notificationPanelViewController.mCentralSurfaces.mMessageRouter.sendMessageDelayed(1003, 5000L);
                }
                notificationPanelViewController.mShortcut = z ? 1 : 0;
                if ((!notificationPanelViewController.mKeyguardStateController.mKeyguardGoingAway && notificationPanelViewController.mSecAffordanceHelper != null && !((KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class)).isNoUnlockNeeded(notificationPanelViewController.mShortcut)) || ((KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class)).isTaskType(notificationPanelViewController.mShortcut)) {
                    notificationPanelViewController.mSecAffordanceHelper.reset(false);
                }
                NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                notificationPanelViewController2.mIsLaunchTransitionRunning = false;
                notificationPanelViewController2.mIsLaunchTransitionFinished = true;
                KeyguardSecAffordanceHelper keyguardSecAffordanceHelper = notificationPanelViewController2.mSecAffordanceHelper;
                if (keyguardSecAffordanceHelper != null) {
                    keyguardSecAffordanceHelper.isShortcutPreviewSwipingInProgress = false;
                }
                notificationPanelViewController2.mStatusBarKeyguardViewManager.readyForKeyguardDone();
                notificationPanelViewController2.mIsLaunchTransitionFinished = !((KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class)).isTaskType(notificationPanelViewController2.mShortcut);
                KeyguardSecAffordanceView.this.mShortcutLaunchAnimator = null;
            }
        };
        this.mShortcutLaunchAlphaAnimatorEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.8
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                keyguardSecAffordanceView.mShortcutLaunchAlphaAnimator = null;
                keyguardSecAffordanceView.mIsShortcutLaunching = false;
                if (!keyguardSecAffordanceView.mIsTaskTypeShortcut || keyguardSecAffordanceView.mShortcutManager.isUnlockWaitNeeded(keyguardSecAffordanceView.mRight ? 1 : 0)) {
                    return;
                }
                KeyguardSecAffordanceView.mWaitForReset = false;
                KeyguardSecAffordanceView.this.reset(true);
            }
        };
        this.mRectangleScaleAnimatorEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.9
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView.this.mRectangleScaleAnimator = null;
            }
        };
        this.mRectangleIconScaleAnimatorEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.10
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView.this.mRectangleIconScaleAnimator = null;
            }
        };
        this.mRectangleAlphaAnimatorEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.11
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView.this.mRectangleAlphaAnimator = null;
            }
        };
        this.mInitialPeekAnimatorEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.12
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                keyguardSecAffordanceView.mInitialPeekAnimator = null;
                keyguardSecAffordanceView.mFling = false;
            }
        };
        this.mBottomIconScaleEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.13
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView.this.mBottomIconScaleAnimator = null;
            }
        };
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.14
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 1001) {
                    return;
                }
                Log.d("KeyguardSecAffordanceView", "reset timeout");
                KeyguardSecAffordanceView.m2958$$Nest$mresetOnTimeout(KeyguardSecAffordanceView.this);
            }
        };
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return KeyguardSecAffordanceView.$r8$lambda$usC3LFwY5dbTHi859Ko25i8wvTY(this.f$0, (Integer) obj);
            }
        });
        SystemUIAppComponentFactoryBase.Companion.getClass();
        SystemUIAppComponentFactoryBase.systemUIInitializer.getSysUIComponent().inject(this);
        ((ImageView) this).mContext = context;
        this.mRectanglePaint = new Paint();
        this.mBackgroundCirclePaint = new Paint();
        this.mTaskOnCirclePaint = new Paint();
        Paint paint = new Paint(1);
        this.mForegroundCirclePaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        KeyguardStateController keyguardStateController = (KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class);
        this.mKeyguardStateController = keyguardStateController;
        this.mShortcutManager = (KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class);
        this.mSettingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardStateController;
        keyguardStateControllerImpl.addCallback(this);
        this.mIsSecure = keyguardStateControllerImpl.mSecure;
        this.mTrusted = keyguardStateControllerImpl.mTrusted;
        this.mCanDismissLockScreen = keyguardStateControllerImpl.mCanDismissLockScreen;
        setRectangleColor();
        this.mIsLandScape = ((ImageView) this).mContext.getResources().getConfiguration().orientation == 2;
        updateDisplayParameters();
        this.mTelecomManager = TelecomManager.from(((ImageView) this).mContext);
        boolean zIsWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper("navibar");
        this.mIsWhiteWallpaper = zIsWhiteKeyguardWallpaper;
        this.mMaxDimBackground = zIsWhiteKeyguardWallpaper ? 0.08f : 0.25f;
        setBackgroundCircleColor();
        setForegroundCircleColor();
    }
}
