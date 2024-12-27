package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.SemWallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telecom.TelecomManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SemBlurInfo;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor;
import com.android.systemui.keyguard.animator.ActionUpOrCancelHandler$$ExternalSyntheticOutline0;
import com.android.systemui.keyguardimage.WallpaperImageInjectCreator;
import com.android.systemui.shade.NotificationPanelView;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController;
import com.android.systemui.statusbar.phone.KeyguardSecAffordanceHelper;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.wallpaper.WallpaperUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class KeyguardSecAffordanceView extends KeyguardAffordanceView implements KeyguardStateController.Callback, LaunchableView {
    public final LaunchableViewDelegate delegate;
    public int mAffordancePivotY;
    public List mAnimatorSet;
    public final Paint mBackgroundCirclePaint;
    public FrameLayout mBlurPanelRoot;
    public View mBlurPanelView;
    public ValueAnimator mBottomIconAlphaAnimator;
    public final AnonymousClass3 mBottomIconAlphaEndListener;
    public ValueAnimator mBottomIconScaleAnimator;
    public final AnonymousClass12 mBottomIconScaleEndListener;
    public boolean mCanDismissLockScreen;
    public int mCenterX;
    public int mCenterY;
    public View mClockView;
    public boolean mDeviceInteractive;
    public final AnonymousClass1 mDisplayObserver;
    protected boolean mFling;
    public final Paint mForegroundCirclePaint;
    public final AnonymousClass13 mHandler;
    public KeyguardSecAffordanceHelper.Callback mHelperCallback;
    public float mImageScale;
    public Animator mInitialPeekAnimator;
    public final AnonymousClass11 mInitialPeekAnimatorEndListener;
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
    public ImageView mPanelIcon;
    public boolean mQsExpanded;
    public Animator mRectangleAlphaAnimator;
    public final AnonymousClass10 mRectangleAlphaAnimatorEndListener;
    public final Rect mRectangleBounds;
    public int mRectangleColor;
    public float mRectangleDistanceCovered;
    public int mRectangleIconAlpha;
    public final Rect mRectangleIconBounds;
    public Drawable mRectangleIconDrawable;
    public int mRectangleIconMargin;
    public float mRectangleIconScale;
    public Animator mRectangleIconScaleAnimator;
    public final AnonymousClass9 mRectangleIconScaleAnimatorEndListener;
    public float mRectangleIconScaleStart;
    public int mRectangleIconSize;
    public final Paint mRectanglePaint;
    public Animator mRectangleScaleAnimator;
    public final AnonymousClass8 mRectangleScaleAnimatorEndListener;
    public float mRectangleScaleStart;
    public ValueAnimator mRectangleShrinkAlphaAnimator;
    public ValueAnimator mRectangleShrinkAnimator;
    public final AnonymousClass4 mRectangleShrinkAnimatorEndListener;
    public boolean mRight;
    public int mScreenHeight;
    public int mScreenWidth;
    private final SettingsHelper mSettingsHelper;
    public boolean mShortcutForCamera;
    public Animator mShortcutLaunchAlphaAnimator;
    public final AnonymousClass7 mShortcutLaunchAlphaAnimatorEndListener;
    public Animator mShortcutLaunchAnimator;
    public final AnonymousClass6 mShortcutLaunchAnimatorEndListener;
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
    public int mWallpaperBrightness;
    public WallpaperImageInjectCreator mWallpaperImageCreator;
    public static final Interpolator SCALE_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
    public static final Interpolator ALPHA_INTERPOLATOR = new PathInterpolator(0.33f, 1.0f, 0.68f, 1.0f);
    public static final Interpolator NOW_BAR_INTERPOLATOR = new PathInterpolator(0.17f, 0.17f, 0.4f, 1.0f);
    public static boolean mIsShowBouncerAnimation = false;
    public static boolean mWaitForReset = false;
    public static boolean mIsLaunchPanelRunning = false;

    public final class GeneralTouchHandler implements TouchHandlePolicy {
        public /* synthetic */ GeneralTouchHandler(KeyguardSecAffordanceView keyguardSecAffordanceView, int i) {
            this();
        }

        private GeneralTouchHandler() {
        }
    }

    public interface TouchHandlePolicy {
    }

    public static void $r8$lambda$hPpMwnVD27esyCsYHNZU3Xvb_mE(KeyguardSecAffordanceView keyguardSecAffordanceView) {
        keyguardSecAffordanceView.getClass();
        Rect rect = new Rect(0, 0, 0, 0);
        if (keyguardSecAffordanceView.mKeyguardStateController.isVisible()) {
            int[] iArr = new int[2];
            keyguardSecAffordanceView.getLocationOnScreen(iArr);
            int i = iArr[0];
            rect.left = i;
            rect.top = iArr[1];
            rect.right = keyguardSecAffordanceView.getWidth() + i;
            rect.bottom = keyguardSecAffordanceView.getHeight() + iArr[1];
        }
        if (keyguardSecAffordanceView.mLastBlockingArea != rect) {
            Bundle bundle = new Bundle();
            bundle.putInt("which", 2);
            bundle.putString("id", keyguardSecAffordanceView.mRight ? "rightShortcut" : "leftShortcut");
            bundle.putInt("left", rect.left);
            bundle.putInt("right", rect.right);
            bundle.putInt("top", rect.top);
            bundle.putInt("bottom", rect.bottom);
            WallpaperManager.getInstance(((ImageView) keyguardSecAffordanceView).mContext).sendWallpaperCommand(keyguardSecAffordanceView.getWindowToken(), "samsung.android.wallpaper.blocktoucharea", 0, 0, 0, bundle);
            keyguardSecAffordanceView.mLastBlockingArea = rect;
        }
    }

    public static /* synthetic */ Unit $r8$lambda$usC3LFwY5dbTHi859Ko25i8wvTY(KeyguardSecAffordanceView keyguardSecAffordanceView, Integer num) {
        keyguardSecAffordanceView.getClass();
        super.setVisibility(num.intValue());
        return Unit.INSTANCE;
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
        float hypot;
        int i = 0;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) {
            hypot = 0.0f;
        } else {
            velocityTracker.computeCurrentVelocity(1000);
            float xVelocity = this.mVelocityTracker.getXVelocity();
            float yVelocity = this.mVelocityTracker.getYVelocity();
            float f3 = f - this.mInitialTouchX;
            float f4 = f2 - this.mInitialTouchY;
            hypot = ((yVelocity * f4) + (xVelocity * f3)) / ((float) Math.hypot(f3, f4));
        }
        if (hypot <= -4000.0f) {
            this.mFling = false;
            this.mIsShortcutLaunching = false;
            cancelAllAnimators();
            startRectangleShrinkAnimation();
            this.mRectanglePaint.setAlpha(0);
            return;
        }
        this.mFling = true;
        this.mIsShortcutLaunching = true;
        boolean isSecure$1 = isSecure$1();
        boolean z = (!isSecure$1 || this.mIsNoUnlockNeeded || this.mIsTaskTypeShortcut) ? false : true;
        mIsShowBouncerAnimation = z;
        mWaitForReset = (this.mIsNoUnlockNeeded || !isSecure$1) && this.mIsShortcutLaunching && !this.mIsTaskTypeShortcut;
        if (z && this.mIsShortcutForPhone) {
            mIsShowBouncerAnimation = !this.mTelecomManager.isInCall();
        }
        setImageAlpha(0.0f, true);
        Log.i("KeyguardSecAffordanceView", "startShortcutLaunchAnimation");
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mRectangleDistanceCovered, this.mScreenWidth);
        this.mShortcutLaunchAnimator = ofFloat;
        ofFloat.setDuration(450L);
        ofFloat.setInterpolator(SCALE_INTERPOLATOR);
        ofFloat.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda2(this, 0));
        ofFloat.addListener(this.mShortcutLaunchAnimatorEndListener);
        this.mShortcutLaunchAnimator.start();
        Log.i("KeyguardSecAffordanceView", "startShortcutLaunchAlphaAnimation");
        cancelAnimator(this.mShortcutLaunchAlphaAnimator);
        cancelAnimator(this.mRectangleAlphaAnimator);
        int alpha = this.mRectanglePaint.getAlpha();
        if (!mIsShowBouncerAnimation && !this.mIsTaskTypeShortcut) {
            i = 255;
        }
        ValueAnimator ofInt = ValueAnimator.ofInt(alpha, i);
        this.mShortcutLaunchAlphaAnimator = ofInt;
        ofInt.setDuration(450L);
        ofInt.setInterpolator(ALPHA_INTERPOLATOR);
        ofInt.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda2(this, 7));
        ofInt.addListener(this.mShortcutLaunchAlphaAnimatorEndListener);
        this.mShortcutLaunchAlphaAnimator.start();
        startRectangleScaleAnimation(0.0f);
    }

    @Override // com.android.systemui.widget.SystemUIImageView, android.widget.ImageView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        WallpaperUtils.registerSystemUIWidgetCallback(this, 256L);
        ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).addObserver(this.mDisplayObserver);
        ((KeyguardVisibilityMonitor) Dependency.sDependency.getDependencyInner(KeyguardVisibilityMonitor.class)).addVisibilityChangedListener(this.mVisibilityListener);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        reset(true);
        this.mIsLandScape = configuration.orientation == 2;
        updateDisplayParameters();
    }

    @Override // com.android.systemui.widget.SystemUIImageView, android.widget.ImageView, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        WallpaperUtils.removeSystemUIWidgetCallback(this);
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
        }
        boolean isSupportBlur = this.mShortcutManager.isSupportBlur();
        if ((!isSupportBlur && this.mShortcutManager.isMonotoneIcon(this.mRight ? 1 : 0)) || this.mIsDrawBackgroundCircle) {
            canvas.drawCircle(this.mCenterX, this.mCenterY, getWidth() / 2.0f, this.mBackgroundCirclePaint);
        }
        if (this.mIsTaskTypeShortcutEnabled && !isSupportBlur) {
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
        if (getWidth() != this.mShortcutManager.getShortcutIconSizeValue() || !this.mIsBlurBgApplied) {
            this.mIsBlurBgApplied = true;
            updateBgBlur(((KeyguardVisibilityMonitor) Dependency.sDependency.getDependencyInner(KeyguardVisibilityMonitor.class)).isVisible(), false);
        }
        postDelayed(new KeyguardSecAffordanceView$$ExternalSyntheticLambda5(this, 1), 700L);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:110:0x022c  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x022f  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0168  */
    /* JADX WARN: Type inference failed for: r7v3, types: [boolean] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onTouchEvent(android.view.MotionEvent r13) {
        /*
            Method dump skipped, instructions count: 910
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.KeyguardSecAffordanceView.onTouchEvent(android.view.MotionEvent):boolean");
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
            Iterator it = ((ArrayList) list).iterator();
            while (it.hasNext()) {
                ((AnimatorSet) it.next()).cancel();
            }
            this.mAnimatorSet = null;
        }
        if (!this.mQsExpanded) {
            updatePanelViews(0.0f);
        }
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
        view.semSetBlurInfo(null);
        this.mPanelBackground.setBackground(null);
        this.mPanelIcon.setImageDrawable(null);
        this.mBlurPanelRoot.setVisibility(8);
    }

    @Override // android.view.View
    public final void setAlpha(float f) {
        super.setAlpha(f);
        setImageAlpha(f, false);
    }

    public final void setBackgroundCircleColor() {
        if (!this.mShortcutManager.isSupportBlur() || this.mIsDrawBackgroundCircle) {
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
            this.mTaskOnCirclePaint.setAlpha(127);
            this.mBackgroundCirclePaint.setAlpha(this.mMaxBackgroundAlpha);
            invalidate();
        }
    }

    public final void setForegroundCircleColor() {
        int i = 0;
        this.mMaxForegroundAlpha = 0;
        if (this.mShortcutManager.isMonotoneIcon(this.mRight ? 1 : 0)) {
            int i2 = -1;
            if (LsRune.LOCKUI_SHORTCUT_BLUR_BG) {
                if (this.mIsWhiteWallpaper) {
                    i2 = -16777216;
                }
            } else if (this.mIsWhiteWallpaper) {
                i2 = Color.parseColor("#2D2D30");
            }
            i = i2;
            this.mMaxForegroundAlpha = (int) (this.mIsWhiteWallpaper ? 25.5d : 51.0d);
        }
        this.mForegroundCirclePaint.setColor(i);
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
        this.mIsTaskTypeShortcutEnabled = !this.mIsTaskTypeShortcut;
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
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f2, f);
        this.mBottomIconScaleAnimator = ofFloat;
        ofFloat.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda2(this, 1));
        ofFloat.addListener(this.mBottomIconScaleEndListener);
        ofFloat.setInterpolator(SCALE_INTERPOLATOR);
        ofFloat.setDuration(300L);
        ofFloat.start();
    }

    public final void setNowBarExpandMode(boolean z) {
        this.mIsNowBarExpanded = z;
        if (this.mDeviceInteractive && this.mIsNowBarVisible) {
            this.mIsAnimFromNowBarRunning = true;
            setImageAlpha(z ? 0.0f : 1.0f, true, z ? 200L : 300L, z ? 50L : 0L, NOW_BAR_INTERPOLATOR, true);
            return;
        }
        Log.d("KeyguardSecAffordanceView", "setNowBarExpandMode interactive: " + this.mDeviceInteractive + ", mIsNowBarVisible: " + this.mIsNowBarVisible + ", " + getShortcutType());
    }

    public final void setNowBarVisibility(final Consumer consumer, final boolean z, boolean z2) {
        int i;
        final boolean isVisible = ((KeyguardVisibilityMonitor) Dependency.sDependency.getDependencyInner(KeyguardVisibilityMonitor.class)).isVisible();
        StringBuilder sb = new StringBuilder("setNowBarVisibility old: ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, this.mIsNowBarVisible, ", new: ", z, ", ");
        sb.append(getShortcutType());
        Log.d("KeyguardSecAffordanceView", sb.toString());
        boolean z3 = this.mIsNowBarVisible;
        if (z3 == z) {
            return;
        }
        if (!z2 || !this.mDeviceInteractive || !isVisible) {
            this.mIsNowBarVisible = z;
            if (isVisible) {
                updateBgBlur(true, true);
            }
            consumer.accept(Boolean.valueOf(z));
            StringBuilder sb2 = new StringBuilder("setNowBarVisibility animate: ");
            sb2.append(z2);
            sb2.append(", mDeviceInteractive: ");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb2, this.mDeviceInteractive, ", isKeyguardVisible: ", isVisible, ", ");
            sb2.append(getShortcutType());
            Log.d("KeyguardSecAffordanceView", sb2.toString());
            return;
        }
        int shortcutIconSizeValue = this.mShortcutManager.getShortcutIconSizeValue(z3);
        int shortcutIconSizeValue2 = this.mShortcutManager.getShortcutIconSizeValue(z);
        if (shortcutIconSizeValue == shortcutIconSizeValue2) {
            Log.d("KeyguardSecAffordanceView", "setNowBarVisibility same size ".concat(getShortcutType()));
            return;
        }
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
        int shortcutSideMargin = this.mShortcutManager.getShortcutSideMargin(this.mIsNowBarVisible);
        int shortcutSideMargin2 = this.mShortcutManager.getShortcutSideMargin(z);
        int shortcutBottomMargin = this.mShortcutManager.getShortcutBottomMargin(this.mIsNowBarVisible);
        int shortcutBottomMargin2 = this.mShortcutManager.getShortcutBottomMargin(z);
        this.mIsNowBarVisible = z;
        SpringAnimation springAnimation4 = new SpringAnimation(new FloatValueHolder(shortcutIconSizeValue));
        springAnimation4.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m(200.0f, 0.71f);
        this.mNowBarVisibilitySizeAnimation = springAnimation4;
        springAnimation4.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda13
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z4, float f, float f2) {
                Consumer consumer2 = consumer;
                Interpolator interpolator = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                if (consumer2 != null) {
                    consumer2.accept(Boolean.valueOf(z));
                }
            }
        });
        final boolean isMonotoneIcon = this.mShortcutManager.isMonotoneIcon(this.mRight ? 1 : 0);
        final int i2 = 0;
        this.mNowBarVisibilitySizeAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener(this) { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda14
            public final /* synthetic */ KeyguardSecAffordanceView f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f, float f2) {
                boolean z4 = isVisible;
                boolean z5 = isMonotoneIcon;
                KeyguardSecAffordanceView keyguardSecAffordanceView = this.f$0;
                switch (i2) {
                    case 0:
                        Interpolator interpolator = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        int i3 = (int) f;
                        keyguardSecAffordanceView.setLeftTopRightBottom(keyguardSecAffordanceView.getLeft(), keyguardSecAffordanceView.getTop(), keyguardSecAffordanceView.getLeft() + i3, keyguardSecAffordanceView.getTop() + i3);
                        keyguardSecAffordanceView.mCenterX = keyguardSecAffordanceView.getWidth() / 2;
                        keyguardSecAffordanceView.mCenterY = keyguardSecAffordanceView.getHeight() / 2;
                        if (z5) {
                            keyguardSecAffordanceView.updateBgBlur(z4, true);
                            break;
                        }
                        break;
                    case 1:
                        Interpolator interpolator2 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        int i4 = (int) f;
                        keyguardSecAffordanceView.setLeftTopRightBottom(i4, keyguardSecAffordanceView.getTop(), keyguardSecAffordanceView.getWidth() + i4, keyguardSecAffordanceView.getHeight() + keyguardSecAffordanceView.getTop());
                        keyguardSecAffordanceView.mCenterX = keyguardSecAffordanceView.getWidth() / 2;
                        keyguardSecAffordanceView.mCenterY = keyguardSecAffordanceView.getHeight() / 2;
                        if (z5) {
                            keyguardSecAffordanceView.updateBgBlur(z4, true);
                            break;
                        }
                        break;
                    default:
                        Interpolator interpolator3 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        int i5 = (int) f;
                        keyguardSecAffordanceView.setLeftTopRightBottom(keyguardSecAffordanceView.getLeft(), i5, keyguardSecAffordanceView.getWidth() + keyguardSecAffordanceView.getLeft(), keyguardSecAffordanceView.getHeight() + i5);
                        keyguardSecAffordanceView.mCenterX = keyguardSecAffordanceView.getWidth() / 2;
                        keyguardSecAffordanceView.mCenterY = keyguardSecAffordanceView.getHeight() / 2;
                        if (z5) {
                            keyguardSecAffordanceView.updateBgBlur(z4, true);
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
        SpringAnimation springAnimation5 = new SpringAnimation(new FloatValueHolder(left));
        springAnimation5.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m(200.0f, 0.71f);
        this.mNowBarVisibilityXAnimation = springAnimation5;
        final int i4 = 1;
        springAnimation5.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener(this) { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda14
            public final /* synthetic */ KeyguardSecAffordanceView f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f, float f2) {
                boolean z4 = isVisible;
                boolean z5 = isMonotoneIcon;
                KeyguardSecAffordanceView keyguardSecAffordanceView = this.f$0;
                switch (i4) {
                    case 0:
                        Interpolator interpolator = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        int i32 = (int) f;
                        keyguardSecAffordanceView.setLeftTopRightBottom(keyguardSecAffordanceView.getLeft(), keyguardSecAffordanceView.getTop(), keyguardSecAffordanceView.getLeft() + i32, keyguardSecAffordanceView.getTop() + i32);
                        keyguardSecAffordanceView.mCenterX = keyguardSecAffordanceView.getWidth() / 2;
                        keyguardSecAffordanceView.mCenterY = keyguardSecAffordanceView.getHeight() / 2;
                        if (z5) {
                            keyguardSecAffordanceView.updateBgBlur(z4, true);
                            break;
                        }
                        break;
                    case 1:
                        Interpolator interpolator2 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        int i42 = (int) f;
                        keyguardSecAffordanceView.setLeftTopRightBottom(i42, keyguardSecAffordanceView.getTop(), keyguardSecAffordanceView.getWidth() + i42, keyguardSecAffordanceView.getHeight() + keyguardSecAffordanceView.getTop());
                        keyguardSecAffordanceView.mCenterX = keyguardSecAffordanceView.getWidth() / 2;
                        keyguardSecAffordanceView.mCenterY = keyguardSecAffordanceView.getHeight() / 2;
                        if (z5) {
                            keyguardSecAffordanceView.updateBgBlur(z4, true);
                            break;
                        }
                        break;
                    default:
                        Interpolator interpolator3 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        int i5 = (int) f;
                        keyguardSecAffordanceView.setLeftTopRightBottom(keyguardSecAffordanceView.getLeft(), i5, keyguardSecAffordanceView.getWidth() + keyguardSecAffordanceView.getLeft(), keyguardSecAffordanceView.getHeight() + i5);
                        keyguardSecAffordanceView.mCenterX = keyguardSecAffordanceView.getWidth() / 2;
                        keyguardSecAffordanceView.mCenterY = keyguardSecAffordanceView.getHeight() / 2;
                        if (z5) {
                            keyguardSecAffordanceView.updateBgBlur(z4, true);
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
        SpringAnimation springAnimation6 = new SpringAnimation(new FloatValueHolder(top));
        springAnimation6.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m(200.0f, 0.71f);
        this.mNowBarVisibilityYAnimation = springAnimation6;
        final int i5 = 2;
        springAnimation6.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener(this) { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda14
            public final /* synthetic */ KeyguardSecAffordanceView f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f, float f2) {
                boolean z4 = isVisible;
                boolean z5 = isMonotoneIcon;
                KeyguardSecAffordanceView keyguardSecAffordanceView = this.f$0;
                switch (i5) {
                    case 0:
                        Interpolator interpolator = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        int i32 = (int) f;
                        keyguardSecAffordanceView.setLeftTopRightBottom(keyguardSecAffordanceView.getLeft(), keyguardSecAffordanceView.getTop(), keyguardSecAffordanceView.getLeft() + i32, keyguardSecAffordanceView.getTop() + i32);
                        keyguardSecAffordanceView.mCenterX = keyguardSecAffordanceView.getWidth() / 2;
                        keyguardSecAffordanceView.mCenterY = keyguardSecAffordanceView.getHeight() / 2;
                        if (z5) {
                            keyguardSecAffordanceView.updateBgBlur(z4, true);
                            break;
                        }
                        break;
                    case 1:
                        Interpolator interpolator2 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        int i42 = (int) f;
                        keyguardSecAffordanceView.setLeftTopRightBottom(i42, keyguardSecAffordanceView.getTop(), keyguardSecAffordanceView.getWidth() + i42, keyguardSecAffordanceView.getHeight() + keyguardSecAffordanceView.getTop());
                        keyguardSecAffordanceView.mCenterX = keyguardSecAffordanceView.getWidth() / 2;
                        keyguardSecAffordanceView.mCenterY = keyguardSecAffordanceView.getHeight() / 2;
                        if (z5) {
                            keyguardSecAffordanceView.updateBgBlur(z4, true);
                            break;
                        }
                        break;
                    default:
                        Interpolator interpolator3 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        int i52 = (int) f;
                        keyguardSecAffordanceView.setLeftTopRightBottom(keyguardSecAffordanceView.getLeft(), i52, keyguardSecAffordanceView.getWidth() + keyguardSecAffordanceView.getLeft(), keyguardSecAffordanceView.getHeight() + i52);
                        keyguardSecAffordanceView.mCenterX = keyguardSecAffordanceView.getWidth() / 2;
                        keyguardSecAffordanceView.mCenterY = keyguardSecAffordanceView.getHeight() / 2;
                        if (z5) {
                            keyguardSecAffordanceView.updateBgBlur(z4, true);
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
        int i;
        float f3;
        int i2;
        float f4 = this.mScreenHeight;
        float f5 = this.mVerticalScale;
        float f6 = (f4 * f5) / 2.0f;
        float f7 = f4 - f6;
        float f8 = this.mRectangleIconScale;
        int i3 = this.mRectangleIconSize;
        int i4 = (int) (i3 * f8);
        int i5 = this.mRectangleIconMargin;
        int i6 = i5 - ((i4 - i3) / 2);
        int i7 = (i5 * 2) + i4;
        float f9 = this.mRectangleDistanceCovered;
        if (f9 > i7) {
            i6 = AbsActionBarView$$ExternalSyntheticOutline0.m((int) f9, i7, 2, i6);
        }
        int i8 = i4 / 2;
        int i9 = (int) ((((f7 - f6) / 2.0f) + f6) - i8);
        int i10 = i9 + i4;
        boolean z = this.mRight;
        if (z) {
            int i11 = this.mScreenWidth;
            float f10 = i11;
            float f11 = f10 - f;
            f2 = f11 >= 0.0f ? f11 : 0.0f;
            float f12 = f2 + f10;
            if (f12 > f10) {
                f12 = i11 + 78;
            }
            i2 = ((int) f2) + i6;
            int i12 = (i11 / 2) - i8;
            if (i2 < i12) {
                i2 = i12;
            }
            i = i2 + i4;
            if (i > i11 + i4) {
                f3 = f12;
                i = 0;
                i9 = 0;
                i2 = 0;
                i10 = 0;
            } else {
                f3 = f12;
            }
        } else {
            int i13 = this.mScreenWidth;
            float f13 = i13;
            float f14 = f > f13 ? f13 : f;
            float f15 = f14 - f13;
            f2 = f15 < 0.0f ? -78.0f : f15;
            int i14 = ((int) f14) - i6;
            int i15 = (i3 / 2) + (i13 / 2);
            i = i14 > i15 ? i15 : i14;
            int i16 = i - i4;
            if (i16 < i3 * (-1)) {
                f3 = f14;
                i = 0;
                i9 = 0;
                i2 = 0;
                i10 = 0;
            } else {
                f3 = f14;
                i2 = i16;
            }
        }
        float f16 = z ? this.mScreenWidth - f2 : f3;
        this.mRectangleDistanceCovered = f16;
        if (f16 < this.mScreenWidth) {
            f4 = f7;
        }
        this.mRectangleScaleStart = f5;
        this.mRectangleIconScaleStart = f8;
        float f17 = this.mShortcutLaunchDistance;
        int i17 = 102;
        if (f16 >= f17 && !this.mLaunchThresholdAchieved) {
            Log.d("KeyguardSecAffordanceView", "updateOnThreshold launch achieved ".concat(getShortcutType()));
            this.mLaunchThresholdAchieved = true;
            startRectangleScaleAnimation(0.1f);
            startRectangleIconScaleAnimation();
            this.mRectangleIconAlpha = 255;
            if (this.mIsTaskTypeShortcut) {
                boolean z2 = this.mIsTaskTypeShortcutEnabled;
                this.mRectangleIconAlpha = z2 ? 102 : 255;
                r10 = z2 ? 102 : 204;
                updateRectangleIconDrawable(this.mIsTransitIconNeeded);
            }
            startRectangleAlphaAnimation(r10);
            this.mVibrationUtil.playVibration(108);
        } else if (f16 < f17 && this.mLaunchThresholdAchieved) {
            Log.d("KeyguardSecAffordanceView", "updateOnThreshold launch not achieved ".concat(getShortcutType()));
            this.mLaunchThresholdAchieved = false;
            startRectangleScaleAnimation(0.2f);
            startRectangleIconScaleAnimation();
            this.mRectangleIconAlpha = 102;
            if (this.mIsTaskTypeShortcut) {
                boolean z3 = this.mIsTaskTypeShortcutEnabled;
                this.mRectangleIconAlpha = z3 ? 255 : 102;
                r10 = z3 ? 204 : 102;
                updateRectangleIconDrawable(false);
                i17 = r10;
            }
            startRectangleAlphaAnimation(i17);
            this.mVibrationUtil.playVibration(109);
        }
        this.mRectangleBounds.set((int) f2, (int) f6, (int) f3, (int) f4);
        this.mRectangleIconBounds.set(i2, i9, i, i10);
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
    }

    public final void startRectangleAlphaAnimation(int i) {
        cancelAnimator(this.mRectangleShrinkAlphaAnimator);
        cancelAnimator(this.mRectangleAlphaAnimator);
        ValueAnimator ofInt = ValueAnimator.ofInt(this.mRectanglePaint.getAlpha(), i);
        this.mRectangleAlphaAnimator = ofInt;
        ofInt.setDuration(50L);
        ofInt.setInterpolator(ALPHA_INTERPOLATOR);
        ofInt.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda2(this, 5));
        ofInt.addListener(this.mRectangleAlphaAnimatorEndListener);
        this.mRectangleAlphaAnimator.start();
    }

    public final void startRectangleIconScaleAnimation() {
        cancelAnimator(this.mRectangleIconScaleAnimator);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mRectangleIconScaleStart, 1.0f);
        this.mRectangleIconScaleAnimator = ofFloat;
        ofFloat.setDuration(450L);
        ofFloat.setInterpolator(SCALE_INTERPOLATOR);
        ofFloat.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda2(this, 2));
        ofFloat.addListener(this.mRectangleIconScaleAnimatorEndListener);
        this.mRectangleIconScaleAnimator.start();
    }

    public final void startRectangleScaleAnimation(float f) {
        cancelAnimator(this.mRectangleScaleAnimator);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mRectangleScaleStart, f);
        this.mRectangleScaleAnimator = ofFloat;
        ofFloat.setDuration(450L);
        ofFloat.setInterpolator(SCALE_INTERPOLATOR);
        ofFloat.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda2(this, 4));
        ofFloat.addListener(this.mRectangleScaleAnimatorEndListener);
        this.mRectangleScaleAnimator.start();
    }

    public final void startRectangleShrinkAnimation() {
        Log.i("KeyguardSecAffordanceView", "startRectangleShrinkAnimation");
        setImageAlpha(1.0f, true);
        setImageScale(1.0f, true);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mRectangleDistanceCovered, 0.0f);
        this.mRectangleShrinkAnimator = ofFloat;
        ofFloat.setDuration(this.mInitialPeekShowing ? 200L : 450L);
        ofFloat.setInterpolator(SCALE_INTERPOLATOR);
        ofFloat.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda2(this, 6));
        ofFloat.addListener(this.mRectangleShrinkAnimatorEndListener);
        this.mRectangleShrinkAnimator.start();
    }

    public final void updateBgBlur(boolean z, boolean z2) {
        SemBlurInfo semBlurInfo;
        int i;
        if (this.mShortcutManager.isSupportBlur() && this.mDeviceInteractive) {
            if (z && this.mShortcutManager.isMonotoneIcon(this.mRight ? 1 : 0)) {
                SemBlurInfo.Builder backgroundCornerRadius = new SemBlurInfo.Builder(0).setBackgroundCornerRadius((z2 ? getRight() - getLeft() : this.mShortcutManager.getShortcutIconSizeValue()) / 2.0f);
                if (this.mShortcutManager.isTaskType(this.mRight ? 1 : 0) && this.mShortcutManager.isTaskTypeEnabled(this.mRight ? 1 : 0)) {
                    i = this.mIsWhiteWallpaper ? 120 : 105;
                } else {
                    int i2 = this.mWallpaperBrightness;
                    if (i2 != -1) {
                        if (i2 < 0 || i2 > 28) {
                            i = (29 > i2 || i2 > 77) ? 122 : 121;
                        }
                        i = 106;
                    } else {
                        if (this.mIsWhiteWallpaper) {
                            i = 117;
                        }
                        i = 106;
                    }
                }
                semBlurInfo = backgroundCornerRadius.setColorCurvePreset(i).build();
            } else {
                semBlurInfo = null;
            }
            semSetBlurInfo(semBlurInfo);
        }
    }

    public final void updateDisplayParameters() {
        Resources resources = ((ImageView) this).mContext.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int dimensionPixelSize = resources.getDimensionPixelSize(android.R.dimen.resolver_max_collapsed_height);
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
        float max = Math.max(0.0f, f - this.mInitialPeekDistance);
        if (this.mIsDown) {
            setUScaleAnimator(this.mNotificationPanelView, 1.0f, 1.0f);
        } else {
            float f2 = this.mShortcutLaunchDistance;
            float f3 = 1.0f - (max / f2);
            if (f3 < 0.0f) {
                f3 = 0.0f;
            }
            float f4 = 1.0f - ((max / f2) * 0.050000012f);
            if (f4 < 0.95f) {
                f4 = 0.95f;
            }
            setUScaleAnimator(this.mNotificationPanelView, max != 0.0f ? f4 : 1.0f, f3);
        }
        setRectangleBounds(f);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x008c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007e  */
    /* JADX WARN: Type inference failed for: r0v0, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateRectangleIconDrawable(boolean r10) {
        /*
            r9 = this;
            boolean r0 = r9.mRight
            com.android.systemui.statusbar.KeyguardShortcutManager r1 = r9.mShortcutManager
            r1.getClass()
            java.lang.String r2 = "KeyguardShortcutManager"
            java.lang.String r3 = "IllegalArgument : "
            r4 = 2
            r5 = 0
            if (r0 < 0) goto L20
            if (r0 < r4) goto L12
            goto L20
        L12:
            com.android.systemui.statusbar.KeyguardShortcutManager$ShortcutData[] r1 = r1.shortcutsData
            if (r10 == 0) goto L1b
            r1 = r1[r0]
            android.graphics.drawable.Drawable r1 = r1.panelTransitDrawable
            goto L24
        L1b:
            r1 = r1[r0]
            android.graphics.drawable.Drawable r1 = r1.panelDrawable
            goto L24
        L20:
            com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(r0, r3, r2)
            r1 = r5
        L24:
            r9.mRectangleIconDrawable = r1
            com.android.systemui.statusbar.KeyguardShortcutManager r1 = r9.mShortcutManager
            boolean r6 = r9.mRight
            boolean r1 = r1.isMonotoneIcon(r6)
            if (r1 == 0) goto La4
            android.graphics.drawable.Drawable r1 = r9.mRectangleIconDrawable
            if (r1 == 0) goto La4
            boolean r6 = r9.mIsTaskTypeShortcut
            r7 = 1
            if (r6 == 0) goto L48
            com.android.systemui.statusbar.KeyguardShortcutManager r10 = r9.mShortcutManager
            boolean r0 = r10.isDarkPanel(r0)
            r0 = r0 ^ r7
            r2 = 0
            android.graphics.drawable.Drawable r10 = r10.convertTaskDrawable(r1, r0, r2, r7)
            r9.mRectangleIconDrawable = r10
            goto La4
        L48:
            com.android.systemui.statusbar.KeyguardShortcutManager r1 = r9.mShortcutManager
            r1.getClass()
            if (r0 < 0) goto L6c
            if (r0 < r4) goto L52
            goto L6c
        L52:
            com.android.systemui.statusbar.KeyguardShortcutManager$ShortcutData[] r1 = r1.shortcutsData
            if (r10 == 0) goto L61
            r10 = r1[r0]
            android.graphics.drawable.Drawable r10 = r10.panelTransitDrawable
            if (r10 == 0) goto L6f
            android.graphics.Bitmap r10 = androidx.core.graphics.drawable.DrawableKt.toBitmap$default(r10)
            goto L70
        L61:
            r10 = r1[r0]
            android.graphics.drawable.Drawable r10 = r10.panelDrawable
            if (r10 == 0) goto L6f
            android.graphics.Bitmap r10 = androidx.core.graphics.drawable.DrawableKt.toBitmap$default(r10)
            goto L70
        L6c:
            com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(r0, r3, r2)
        L6f:
            r10 = r5
        L70:
            if (r10 == 0) goto L7e
            android.graphics.drawable.BitmapDrawable r1 = new android.graphics.drawable.BitmapDrawable
            android.content.Context r6 = r9.mContext
            android.content.res.Resources r6 = r6.getResources()
            r1.<init>(r6, r10)
            goto L7f
        L7e:
            r1 = r5
        L7f:
            com.android.systemui.statusbar.KeyguardShortcutManager r10 = r9.mShortcutManager
            boolean r6 = r10.isDarkPanel(r0)
            com.android.systemui.statusbar.KeyguardShortcutManager r8 = r9.mShortcutManager
            r8.getClass()
            if (r0 < 0) goto L9c
            if (r0 < r4) goto L8f
            goto L9c
        L8f:
            com.android.systemui.statusbar.KeyguardShortcutManager$ShortcutData[] r2 = r8.shortcutsData
            r0 = r2[r0]
            android.content.ComponentName r0 = r0.componentName
            if (r0 == 0) goto L9f
            java.lang.String r5 = r0.getPackageName()
            goto L9f
        L9c:
            com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(r0, r3, r2)
        L9f:
            r10.grayInvertDrawable(r1, r6, r5, r7)
            r9.mRectangleIconDrawable = r1
        La4:
            android.widget.ImageView r10 = r9.mPanelIcon
            if (r10 == 0) goto Lad
            android.graphics.drawable.Drawable r9 = r9.mRectangleIconDrawable
            r10.setImageDrawable(r9)
        Lad:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.KeyguardSecAffordanceView.updateRectangleIconDrawable(boolean):void");
    }

    @Override // com.android.systemui.widget.SystemUIImageView, com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        super.updateStyle(j, semWallpaperColors);
        boolean isWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper("navibar");
        if (semWallpaperColors == null) {
            Log.d("KeyguardSecAffordanceView", "updateWallpaperProperties: null");
        } else {
            try {
                int HSVToColor = Color.HSVToColor(semWallpaperColors.get(256L).getHSV());
                double d = ((HSVToColor >>> 16) & 255) / 255.0d;
                double d2 = ((HSVToColor >>> 8) & 255) / 255.0d;
                double d3 = (HSVToColor & 255) / 255.0d;
                double pow = d < 0.04045d ? d / 12.92d : Math.pow((d / 1.055d) + 0.05213270142180095d, 2.4d);
                double pow2 = ((d3 < 0.04045d ? d3 / 12.92d : Math.pow((d3 / 1.055d) + 0.05213270142180095d, 2.4d)) * 0.0722d) + ((d2 < 0.04045d ? d2 / 12.92d : Math.pow((d2 / 1.055d) + 0.05213270142180095d, 2.4d)) * 0.7152d) + (pow * 0.2126d);
                this.mWallpaperBrightness = (int) Math.max(0.0d, ((pow2 > 0.008856d ? Math.cbrt(pow2) : 0.13793103448275862d + (pow2 * 7.787068965517241d)) * 116.0d) - 16.0d);
            } catch (Exception e) {
                KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("updateWallpaperProperties: "), "KeyguardSecAffordanceView");
            }
            Log.d("KeyguardSecAffordanceView", "brightness: " + this.mWallpaperBrightness + " which: " + semWallpaperColors.getWhich() + " " + getShortcutType());
        }
        if (this.mIsWhiteWallpaper != isWhiteKeyguardWallpaper) {
            updateBgBlur(((KeyguardVisibilityMonitor) Dependency.sDependency.getDependencyInner(KeyguardVisibilityMonitor.class)).isVisible(), false);
        }
        this.mIsWhiteWallpaper = isWhiteKeyguardWallpaper;
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
                ValueAnimator ofInt = ValueAnimator.ofInt(getImageAlpha(), i);
                this.mBottomIconAlphaAnimator = ofInt;
                ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda4
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                        Drawable drawable = background;
                        Drawable drawable2 = foreground;
                        Interpolator interpolator2 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        keyguardSecAffordanceView.getClass();
                        int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        if (drawable != null) {
                            drawable.mutate().setAlpha(intValue);
                        }
                        if (drawable2 != null) {
                            drawable2.mutate().setAlpha(keyguardSecAffordanceView.getForegroundAlpha(intValue));
                        }
                        if (!keyguardSecAffordanceView.mShortcutManager.isSupportBlur()) {
                            Paint paint = keyguardSecAffordanceView.mTaskOnCirclePaint;
                            float f2 = intValue / 255.0f;
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
                        keyguardSecAffordanceView.mForegroundCirclePaint.setAlpha(keyguardSecAffordanceView.getForegroundAlpha(intValue));
                        if (intValue <= 0) {
                            intValue = 1;
                        }
                        keyguardSecAffordanceView.setImageAlpha(intValue);
                    }
                });
                ofInt.addListener(this.mBottomIconAlphaEndListener);
                if (interpolator == null) {
                    interpolator = ALPHA_INTERPOLATOR;
                }
                ofInt.setInterpolator(interpolator);
                if (j == -1) {
                    j = 300;
                }
                ofInt.setDuration(j);
                ofInt.setStartDelay(j2);
                ofInt.start();
                return;
            }
            if (background != null) {
                background.mutate().setAlpha(i);
            }
            if (foreground != null) {
                foreground.mutate().setAlpha(getForegroundAlpha(i));
            }
            if (!this.mShortcutManager.isSupportBlur()) {
                Paint paint = this.mTaskOnCirclePaint;
                float f2 = i / 255.0f;
                int i2 = this.mMaxTaskOnBackgroundAlpha;
                int i3 = (int) (i2 * f2);
                if (i3 <= i2) {
                    i2 = i3;
                }
                paint.setAlpha(i2);
                Paint paint2 = this.mBackgroundCirclePaint;
                int i4 = this.mMaxBackgroundAlpha;
                int i5 = (int) (f2 * i4);
                if (i5 <= i4) {
                    i4 = i5;
                }
                paint2.setAlpha(i4);
            }
            this.mForegroundCirclePaint.setAlpha(getForegroundAlpha(i));
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

    /* JADX WARN: Type inference failed for: r5v11, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$6] */
    /* JADX WARN: Type inference failed for: r5v12, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$7] */
    /* JADX WARN: Type inference failed for: r5v13, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$8] */
    /* JADX WARN: Type inference failed for: r5v14, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$9] */
    /* JADX WARN: Type inference failed for: r5v15, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$10] */
    /* JADX WARN: Type inference failed for: r5v16, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$11] */
    /* JADX WARN: Type inference failed for: r5v17, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$12] */
    /* JADX WARN: Type inference failed for: r5v18, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$13] */
    /* JADX WARN: Type inference failed for: r5v5, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r5v6, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$1] */
    /* JADX WARN: Type inference failed for: r5v8, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$3] */
    /* JADX WARN: Type inference failed for: r5v9, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$4] */
    public KeyguardSecAffordanceView(Context context, AttributeSet attributeSet, int i, int i2) {
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
        this.mQsExpanded = false;
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
        this.mWallpaperBrightness = -1;
        this.mRectangleDistanceCovered = 0.0f;
        this.mImageScale = 1.0f;
        this.mVerticalScale = 0.2f;
        this.mRectangleIconScale = 1.0f;
        this.mVisibilityListener = new IntConsumer() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda0
            @Override // java.util.function.IntConsumer
            public final void accept(final int i3) {
                final KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                Interpolator interpolator = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView.getClass();
                if (i3 != 0 && KeyguardSecAffordanceView.mWaitForReset) {
                    KeyguardSecAffordanceView.mWaitForReset = false;
                    keyguardSecAffordanceView.reset(true);
                }
                keyguardSecAffordanceView.post(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardSecAffordanceView keyguardSecAffordanceView2 = KeyguardSecAffordanceView.this;
                        int i4 = i3;
                        Interpolator interpolator2 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                        keyguardSecAffordanceView2.getClass();
                        keyguardSecAffordanceView2.updateBgBlur(i4 == 0, false);
                    }
                });
            }
        };
        this.mDisplayObserver = new DisplayLifecycle.Observer() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.1
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onDisplayChanged(int i3) {
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
                        keyguardSecAffordanceView.mHandler.sendEmptyMessageDelayed(1001, 150L);
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStartedGoingToSleep(int i3) {
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                keyguardSecAffordanceView.mDeviceInteractive = false;
                keyguardSecAffordanceView.updateBgBlur(false, false);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStartedWakingUp() {
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                keyguardSecAffordanceView.mDeviceInteractive = true;
                keyguardSecAffordanceView.mIsTargetView = false;
                keyguardSecAffordanceView.updateBgBlur(keyguardSecAffordanceView.mKeyguardStateController.isVisible(), false);
            }
        };
        this.mBottomIconAlphaEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                keyguardSecAffordanceView.mBottomIconAlphaAnimator = null;
                keyguardSecAffordanceView.mIsAnimFromNowBarRunning = false;
            }
        };
        this.mRectangleShrinkAnimatorEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.4
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
        new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView.this.mRectangleShrinkAlphaAnimator = null;
            }
        };
        this.mShortcutLaunchAnimatorEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                keyguardSecAffordanceView.mFling = false;
                KeyguardSecAffordanceHelper.Callback callback = keyguardSecAffordanceView.mHelperCallback;
                boolean z = keyguardSecAffordanceView.mRight;
                boolean isSecure$1 = keyguardSecAffordanceView.isSecure$1();
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                notificationPanelViewController.mIsLaunchTransitionRunning = true;
                notificationPanelViewController.mLaunchAnimationEndRunnable = null;
                KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_SHORTCUT);
                int i3 = (int) (0.0f / ((CentralSurfacesImpl) notificationPanelViewController.mCentralSurfaces).mDisplayMetrics.density);
                int abs = Math.abs(i3);
                int abs2 = Math.abs(i3);
                LockscreenGestureLogger lockscreenGestureLogger = notificationPanelViewController.mLockscreenGestureLogger;
                if (!z) {
                    lockscreenGestureLogger.write(190, abs, abs2);
                } else if (3 == notificationPanelViewController.mLastCameraLaunchSource) {
                    lockscreenGestureLogger.write(189, abs, abs2);
                }
                KeyguardBottomAreaViewController keyguardBottomAreaViewController = notificationPanelViewController.mKeyguardBottomAreaViewController;
                if (keyguardBottomAreaViewController != null) {
                    keyguardBottomAreaViewController.launchAffordance(z);
                }
                if (!isSecure$1) {
                    ((CentralSurfacesImpl) notificationPanelViewController.mCentralSurfaces).mMessageRouter.sendMessageDelayed(1003, 5000L);
                }
                notificationPanelViewController.mShortcut = z ? 1 : 0;
                if ((!notificationPanelViewController.mKeyguardStateController.mKeyguardGoingAway && !((KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class)).isNoUnlockNeeded(notificationPanelViewController.mShortcut)) || ((KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class)).isTaskType(notificationPanelViewController.mShortcut)) {
                    notificationPanelViewController.mSecAffordanceHelper.reset(false);
                }
                NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                notificationPanelViewController2.mIsLaunchTransitionRunning = false;
                notificationPanelViewController2.mIsLaunchTransitionFinished = true;
                Runnable runnable = notificationPanelViewController2.mLaunchAnimationEndRunnable;
                if (runnable != null) {
                    runnable.run();
                    notificationPanelViewController2.mLaunchAnimationEndRunnable = null;
                }
                notificationPanelViewController2.mSecAffordanceHelper.isShortcutPreviewSwipingInProgress = false;
                notificationPanelViewController2.mStatusBarKeyguardViewManager.readyForKeyguardDone();
                notificationPanelViewController2.mIsLaunchTransitionFinished = !((KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class)).isTaskType(notificationPanelViewController2.mShortcut);
                KeyguardSecAffordanceView.this.mShortcutLaunchAnimator = null;
            }
        };
        this.mShortcutLaunchAlphaAnimatorEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.7
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                keyguardSecAffordanceView.mShortcutLaunchAlphaAnimator = null;
                keyguardSecAffordanceView.mIsShortcutLaunching = false;
                if (keyguardSecAffordanceView.mIsTaskTypeShortcut) {
                    KeyguardSecAffordanceView.mWaitForReset = false;
                    keyguardSecAffordanceView.reset(true);
                }
            }
        };
        this.mRectangleScaleAnimatorEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.8
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView.this.mRectangleScaleAnimator = null;
            }
        };
        this.mRectangleIconScaleAnimatorEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.9
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView.this.mRectangleIconScaleAnimator = null;
            }
        };
        this.mRectangleAlphaAnimatorEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.10
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView.this.mRectangleAlphaAnimator = null;
            }
        };
        this.mInitialPeekAnimatorEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.11
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                keyguardSecAffordanceView.mInitialPeekAnimator = null;
                keyguardSecAffordanceView.mFling = false;
            }
        };
        this.mBottomIconScaleEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.12
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView.this.mBottomIconScaleAnimator = null;
            }
        };
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.13
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 1001) {
                    return;
                }
                Log.d("KeyguardSecAffordanceView", "reset timeout");
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                keyguardSecAffordanceView.mIsShortcutLaunching = false;
                KeyguardSecAffordanceView.mIsShowBouncerAnimation = false;
                KeyguardSecAffordanceView.mWaitForReset = false;
                NotificationPanelViewController.this.mSecAffordanceHelper.reset(true);
            }
        };
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyguardSecAffordanceView.$r8$lambda$usC3LFwY5dbTHi859Ko25i8wvTY(KeyguardSecAffordanceView.this, (Integer) obj);
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
        this.mIsWhiteWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper("navibar");
        setBackgroundCircleColor();
        setForegroundCircleColor();
    }
}
