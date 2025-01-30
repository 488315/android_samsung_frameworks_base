package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.SemWallpaperColors;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Slog;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import com.android.systemui.facewidget.plugin.FaceWidgetContainerWrapper;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor;
import com.android.systemui.keyguardimage.ImageOptionCreator;
import com.android.systemui.keyguardimage.WallpaperImageInjectCreator;
import com.android.systemui.plugins.keyguardstatusview.PluginNotificationController;
import com.android.systemui.shade.NotificationPanelView;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController;
import com.android.systemui.statusbar.phone.KeyguardSecAffordanceHelper;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.MessageRouterImpl;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.view.SystemUIWallpaperBase;
import com.android.systemui.widget.SystemUIWidgetUtil;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntConsumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class KeyguardSecAffordanceView extends KeyguardAffordanceView implements KeyguardStateController.Callback, LaunchableView {
    public final LaunchableViewDelegate delegate;
    public int mAffordancePivotY;
    public List mAnimatorSet;
    public int mBlurPanelRadius;
    public FrameLayout mBlurPanelRoot;
    public View mBlurPanelView;
    public ValueAnimator mBottomIconAlphaAnimator;
    public final C25274 mBottomIconAlphaEndListener;
    public ValueAnimator mBottomIconScaleAnimator;
    public final C252313 mBottomIconScaleEndListener;
    public boolean mCanDismissLockScreen;
    public int mCenterX;
    public int mCenterXOnScreen;
    public int mCenterY;
    public int mCenterYOnScreen;
    public View mClockView;
    public boolean mDeviceInteractive;
    public final C25252 mDisplayObserver;
    public ImageView mFakeWallpaperView;
    protected boolean mFling;
    public final HandlerC252414 mHandler;
    public KeyguardSecAffordanceHelper.Callback mHelperCallback;
    public float mImageScale;
    public Animator mInitialPeekAnimator;
    public final C252212 mInitialPeekAnimatorEndListener;
    public float mInitialPeekDistance;
    public boolean mInitialPeekShowing;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public boolean mIsDown;
    public boolean mIsLandScape;
    public boolean mIsNoUnlockNeeded;
    public boolean mIsSecure;
    public boolean mIsShortcutForPhone;
    public boolean mIsShortcutLaunching;
    public boolean mIsTargetView;
    public boolean mIsTaskTypeShortcut;
    public boolean mIsTaskTypeShortcutEnabled;
    public boolean mIsTransitIconNeeded;
    public boolean mIsUp;
    public boolean mJustClicked;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public boolean mLaunchThresoldAcheived;
    public View mLockIconContainerView;
    public View mLockStarContainer;
    public View mLockWallpaperContainer;
    public View mMusicContainer;
    public View mNotificationPanelIconOnlyContainer;
    public NotificationPanelView mNotificationPanelView;
    public NotificationStackScrollLayout mNotificationStackScrollerView;
    public int mOldPanelBackgroundAlpha;
    public View mPanelBackground;
    public PaintDrawable mPanelBackgroundDrawable;
    public View mPanelDimView;
    public ImageView mPanelIcon;
    public boolean mQsExpanded;
    public Animator mRectangleAlphaAnimator;
    public final C252111 mRectangleAlphaAnimatorEndListener;
    public final Rect mRectangleBounds;
    public int mRectangleColor;
    public int mRectangleCornerRadius;
    public float mRectangleDistanceCovered;
    public int mRectangleIconAlpha;
    public final Rect mRectangleIconBounds;
    public Drawable mRectangleIconDrawable;
    public int mRectangleIconMargin;
    public float mRectangleIconScale;
    public Animator mRectangleIconScaleAnimator;
    public final C252010 mRectangleIconScaleAnimatorEndListener;
    public float mRectangleIconScaleStart;
    public int mRectangleIconSize;
    public final Paint mRectanglePaint;
    public Animator mRectangleScaleAnimator;
    public final C25329 mRectangleScaleAnimatorEndListener;
    public float mRectangleScaleStart;
    public ValueAnimator mRectangleShrinkAnimator;
    public final C25285 mRectangleShrinkAnimatorEndListener;
    public ValueAnimator mRectangleShrinkeAlphaAnimator;
    public final C25296 mRectangleShrinkeAlphaAnimatorEndListener;
    public boolean mRight;
    public int mScreenHeight;
    public int mScreenWidth;
    public final SettingsHelper mSettingsHelper;
    public final C25191 mShortcutCallback;
    public boolean mShortcutForCamera;
    public Animator mShortcutLaunchAlphaAnimator;
    public final C25318 mShortcutLaunchAlphaAnimatorEndListener;
    public Animator mShortcutLaunchAnimator;
    public final C25307 mShortcutLaunchAnimatorEndListener;
    public float mShortcutLaunchDistance;
    public KeyguardShortcutManager mShortcutManager;
    public final TelecomManager mTelecomManager;
    public boolean mTouchCancelled;
    protected TouchHandlePolicy mTouchHandler;
    public final int mTouchSlop;
    public boolean mTrusted;
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallback;
    public VelocityTracker mVelocityTracker;
    public float mVerticalScale;
    public VibrationUtil mVibrationUtil;
    public final KeyguardSecAffordanceView$$ExternalSyntheticLambda1 mVisibilityListener;
    public WallpaperImageInjectCreator mWallpaperImageCreator;
    public static final Interpolator SCALE_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
    public static final Interpolator ALPHA_INTERPOLATOR = new PathInterpolator(0.33f, 1.0f, 0.68f, 1.0f);
    public static boolean mIsShowBouncerAnimation = false;
    public static boolean mWaitForReset = false;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class GeneralTouchHandler implements TouchHandlePolicy {
        public /* synthetic */ GeneralTouchHandler(KeyguardSecAffordanceView keyguardSecAffordanceView, int i) {
            this();
        }

        private GeneralTouchHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface TouchHandlePolicy {
    }

    /* renamed from: $r8$lambda$4vd0Zn5d63IMpqv1U9KSTb-BDHI, reason: not valid java name */
    public static /* synthetic */ Unit m1702$r8$lambda$4vd0Zn5d63IMpqv1U9KSTbBDHI(KeyguardSecAffordanceView keyguardSecAffordanceView, Integer num) {
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
        cancelAnimator(this.mRectangleShrinkeAlphaAnimator);
    }

    public final void cancelAnimatorSet() {
        List list = this.mAnimatorSet;
        if (list == null) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((AnimatorSet) it.next()).cancel();
        }
        this.mAnimatorSet = null;
    }

    public final void init() {
        this.mKeyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
        this.mTouchHandler = new GeneralTouchHandler(this, 0);
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup != null) {
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
        }
    }

    public final boolean isSecure() {
        return (!this.mIsSecure || this.mTrusted || this.mCanDismissLockScreen) ? false : true;
    }

    public final void launchShortcut(float f, float f2) {
        float hypot;
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
        this.mFling = true;
        if (hypot <= -4000.0f) {
            this.mIsShortcutLaunching = false;
            cancelAllAnimators();
            this.mKeyguardUpdateMonitor.setShortcutLaunchInProgress(false);
            startRectangleShrinkAnimation();
            this.mRectanglePaint.setAlpha(0);
            return;
        }
        this.mIsShortcutLaunching = true;
        boolean isSecure = isSecure();
        boolean z = (!isSecure || this.mIsNoUnlockNeeded || this.mIsTaskTypeShortcut) ? false : true;
        mIsShowBouncerAnimation = z;
        mWaitForReset = (this.mIsNoUnlockNeeded || !isSecure) && this.mIsShortcutLaunching && !this.mIsTaskTypeShortcut;
        if (z && this.mIsShortcutForPhone) {
            mIsShowBouncerAnimation = !this.mTelecomManager.isInCall();
        }
        setImageAlpha(0.0f, true);
        Log.i("KeyguardSecAffordanceView", "startShortcutLaunchAnimation");
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mRectangleDistanceCovered, this.mScreenWidth);
        this.mShortcutLaunchAnimator = ofFloat;
        ofFloat.setDuration(this.mShortcutForCamera ? 300L : 450L);
        ofFloat.setInterpolator(SCALE_INTERPOLATOR);
        ofFloat.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda0(this, 6));
        ofFloat.addListener(this.mShortcutLaunchAnimatorEndListener);
        this.mShortcutLaunchAnimator.start();
        Log.i("KeyguardSecAffordanceView", "startShortcutLaunchAlphaAnimation");
        int alpha = this.mRectanglePaint.getAlpha();
        boolean z2 = mIsShowBouncerAnimation || this.mIsTaskTypeShortcut;
        int[] iArr = new int[2];
        iArr[0] = alpha;
        iArr[1] = z2 ? 0 : 255;
        ValueAnimator ofInt = ValueAnimator.ofInt(iArr);
        this.mShortcutLaunchAlphaAnimator = ofInt;
        ofInt.setDuration(this.mShortcutForCamera ? 300L : 450L);
        ofInt.setInterpolator(ALPHA_INTERPOLATOR);
        ofInt.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda0(this, 0));
        ofInt.addListener(this.mShortcutLaunchAlphaAnimatorEndListener);
        this.mShortcutLaunchAlphaAnimator.start();
        startRectangleScaleAnimation(0.0f);
    }

    @Override // com.android.systemui.widget.SystemUIImageView, android.widget.ImageView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        WallpaperUtils.registerSystemUIWidgetCallback(this, SystemUIWidgetUtil.convertFlag("bottom"));
        ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).addObserver(this.mDisplayObserver);
        ((KeyguardVisibilityMonitor) Dependency.get(KeyguardVisibilityMonitor.class)).addVisibilityChangedListener(this.mVisibilityListener);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        reset();
        this.mIsLandScape = configuration.orientation == 2;
        updateDisplayParameters();
    }

    @Override // com.android.systemui.widget.SystemUIImageView, android.widget.ImageView, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        WallpaperUtils.removeSystemUIWidgetCallback(this);
        ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).removeObserver(this.mDisplayObserver);
        KeyguardVisibilityMonitor keyguardVisibilityMonitor = (KeyguardVisibilityMonitor) Dependency.get(KeyguardVisibilityMonitor.class);
        ((ArrayList) keyguardVisibilityMonitor.visibilityChangedListeners).remove(this.mVisibilityListener);
    }

    @Override // com.android.systemui.statusbar.KeyguardAffordanceView, android.widget.ImageView, android.view.View
    public final void onDraw(Canvas canvas) {
        if (this.mIsTargetView) {
            if (this.mBlurPanelView != null) {
                int alpha = this.mRectanglePaint.getAlpha();
                if (this.mOldPanelBackgroundAlpha != alpha) {
                    this.mPanelBackgroundDrawable.getPaint().setAlpha(alpha);
                    this.mOldPanelBackgroundAlpha = alpha;
                }
                this.mPanelBackgroundDrawable.setCornerRadius(this.mRectangleCornerRadius);
                View view = this.mPanelBackground;
                Rect rect = this.mRectangleBounds;
                view.setLeftTopRightBottom(rect.left, rect.top, rect.right, rect.bottom);
                this.mPanelIcon.setAlpha(this.mRectangleIconAlpha);
                ImageView imageView = this.mPanelIcon;
                Rect rect2 = this.mRectangleIconBounds;
                imageView.setLeftTopRightBottom(rect2.left, rect2.top, rect2.right, rect2.bottom);
                this.mBlurPanelView.semSetBlurRadius(this.mBlurPanelRadius);
            } else {
                canvas.save();
                canvas.translate(-this.mCenterXOnScreen, -this.mCenterYOnScreen);
                Rect rect3 = this.mRectangleBounds;
                float f = rect3.left;
                float f2 = rect3.top;
                float f3 = rect3.right;
                float f4 = rect3.bottom;
                float f5 = this.mRectangleCornerRadius;
                canvas.drawRoundRect(f, f2, f3, f4, f5, f5, this.mRectanglePaint);
                Drawable drawable = this.mRectangleIconDrawable;
                if (drawable != null) {
                    drawable.mutate().setAlpha(this.mRectangleIconAlpha);
                    this.mRectangleIconDrawable.setBounds(this.mRectangleIconBounds);
                    this.mRectangleIconDrawable.draw(canvas);
                }
                canvas.restore();
            }
        }
        canvas.save();
        float f6 = this.mImageScale;
        canvas.scale(f6, f6, this.mCenterX, this.mCenterY);
        super.onDraw(canvas);
        canvas.restore();
    }

    @Override // com.android.systemui.statusbar.KeyguardAffordanceView, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mCenterX = getWidth() / 2;
        this.mCenterY = getHeight() / 2;
        int[] iArr = new int[2];
        getLocationOnScreen(iArr);
        this.mCenterXOnScreen = iArr[0];
        this.mCenterYOnScreen = iArr[1];
        if (getRootView() != null) {
            this.mScreenHeight = getRootView().getHeight();
            this.mScreenWidth = getRootView().getWidth();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0164  */
    /* JADX WARN: Type inference failed for: r4v1, types: [boolean] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        TouchHandlePolicy touchHandlePolicy;
        int i;
        View view;
        VelocityTracker velocityTracker;
        SystemUIWallpaperBase systemUIWallpaperBase;
        int i2;
        if (getAlpha() == 0.0f || getImageAlpha() == 0.0f || !isEnabled() || (touchHandlePolicy = this.mTouchHandler) == null) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
        if (actionMasked != 2) {
            motionEvent.toString();
            boolean z = keyguardSecAffordanceView.mRight;
        }
        keyguardSecAffordanceView.mIsUp = false;
        int actionMasked2 = motionEvent.getActionMasked();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (actionMasked2 == 0) {
            keyguardSecAffordanceView.cancelAllAnimators();
            keyguardSecAffordanceView.mInitialTouchX = x;
            keyguardSecAffordanceView.mInitialTouchY = y;
            keyguardSecAffordanceView.mTouchCancelled = false;
            keyguardSecAffordanceView.mJustClicked = true;
            keyguardSecAffordanceView.mIsDown = true;
            keyguardSecAffordanceView.mLaunchThresoldAcheived = false;
            mIsShowBouncerAnimation = false;
            mWaitForReset = false;
            KeyguardShortcutManager keyguardShortcutManager = keyguardSecAffordanceView.mShortcutManager;
            ?? r4 = keyguardSecAffordanceView.mRight;
            keyguardShortcutManager.getClass();
            if (r4 < 0 || r4 >= 2) {
                NestedScrollView$$ExternalSyntheticOutline0.m34m("getShortcutProperty wrong param: ", r4 == true ? 1 : 0, "KeyguardShortcutManager");
                i = 2;
            } else {
                KeyguardShortcutManager.ShortcutData shortcutData = keyguardShortcutManager.mShortcuts[r4 == true ? 1 : 0];
                Intrinsics.checkNotNull(shortcutData);
                i = shortcutData.shortcutProperty;
            }
            keyguardSecAffordanceView.mIsTaskTypeShortcut = i == 1;
            keyguardSecAffordanceView.mIsNoUnlockNeeded = keyguardSecAffordanceView.mShortcutManager.isNoUnlockNeeded(keyguardSecAffordanceView.mRight ? 1 : 0);
            keyguardSecAffordanceView.mVerticalScale = keyguardSecAffordanceView.mIsLandScape ? 0.1f : 0.15f;
            keyguardSecAffordanceView.mRectangleIconAlpha = 102;
            if (keyguardSecAffordanceView.mIsTaskTypeShortcut) {
                keyguardSecAffordanceView.mIsTaskTypeShortcutEnabled = keyguardSecAffordanceView.mShortcutManager.isTaskTypeEnabled(keyguardSecAffordanceView.mRight ? 1 : 0);
                keyguardSecAffordanceView.mIsTransitIconNeeded = keyguardSecAffordanceView.mShortcutManager.isPanelIconTransitionNeeded(keyguardSecAffordanceView.mRight ? 1 : 0);
                boolean z2 = keyguardSecAffordanceView.mIsTaskTypeShortcutEnabled;
                int i3 = z2 ? 204 : 102;
                keyguardSecAffordanceView.mRectangleIconAlpha = z2 ? 255 : 102;
                r0 = i3;
            }
            keyguardSecAffordanceView.mRectanglePaint.setAlpha(r0);
            keyguardSecAffordanceView.mRectangleIconScale = 1.0f;
            keyguardSecAffordanceView.mInitialPeekShowing = true;
            View view2 = keyguardSecAffordanceView.mBlurPanelView;
            if (view2 != null) {
                view2.semSetBlurEnabled(true);
                keyguardSecAffordanceView.mPanelBackground.setBackground(keyguardSecAffordanceView.mPanelBackgroundDrawable);
                keyguardSecAffordanceView.mBlurPanelRoot.setVisibility(0);
            }
            keyguardSecAffordanceView.updateRectangleIconDrawable(false);
            if (keyguardSecAffordanceView.mClockView == null) {
                FaceWidgetContainerWrapper faceWidgetContainerWrapper = NotificationPanelViewController.this.mKeyguardStatusViewController.mKeyguardStatusBase;
                if (faceWidgetContainerWrapper == null) {
                    view = null;
                } else {
                    view = faceWidgetContainerWrapper.mClockContainer;
                    if (view == null) {
                        view = faceWidgetContainerWrapper.mFaceWidgetContainer;
                    }
                }
                keyguardSecAffordanceView.mClockView = view;
            }
            if (keyguardSecAffordanceView.mNotificationStackScrollerView == null) {
                keyguardSecAffordanceView.mNotificationStackScrollerView = NotificationPanelViewController.this.mNotificationStackScrollLayoutController.mView;
            }
            if (keyguardSecAffordanceView.mNotificationPanelIconOnlyContainer == null) {
                PluginNotificationController pluginNotificationController = NotificationPanelViewController.this.mLockscreenNotificationIconsOnlyController.mNotificationControllerWrapper.mNotificationController;
                keyguardSecAffordanceView.mNotificationPanelIconOnlyContainer = pluginNotificationController != null ? pluginNotificationController.getIconContainer() : null;
            }
            if (keyguardSecAffordanceView.mLockIconContainerView == null) {
                keyguardSecAffordanceView.mLockIconContainerView = NotificationPanelViewController.this.mStatusBarKeyguardViewManager.getLockIconContainer();
            }
            if (keyguardSecAffordanceView.mMusicContainer == null) {
                FaceWidgetContainerWrapper faceWidgetContainerWrapper2 = NotificationPanelViewController.this.mKeyguardStatusViewController.mKeyguardStatusBase;
                List list = faceWidgetContainerWrapper2 == null ? null : faceWidgetContainerWrapper2.mContentsContainerList;
                keyguardSecAffordanceView.mMusicContainer = (list == null || list.isEmpty()) ? null : (View) list.get(1);
            }
            if (keyguardSecAffordanceView.mLockStarContainer == null) {
                keyguardSecAffordanceView.mLockStarContainer = NotificationPanelViewController.this.mPluginLockStarContainer;
            }
            if (keyguardSecAffordanceView.mLockWallpaperContainer == null) {
                keyguardSecAffordanceView.mLockWallpaperContainer = NotificationPanelViewController.this.mKeyguardWallpaperController.mRootView;
            }
            if (keyguardSecAffordanceView.mFakeWallpaperView == null) {
                keyguardSecAffordanceView.mFakeWallpaperView = (ImageView) ((CentralSurfacesImpl) NotificationPanelViewController.this.mCentralSurfaces).mNotificationShadeWindowView.findViewById(R.id.fake_wallpaper);
            }
            if (keyguardSecAffordanceView.mNotificationPanelView == null) {
                keyguardSecAffordanceView.mNotificationPanelView = NotificationPanelViewController.this.mView;
            }
            if (keyguardSecAffordanceView.mWallpaperImageCreator == null) {
                keyguardSecAffordanceView.mWallpaperImageCreator = NotificationPanelViewController.this.mWallpaperImageCreator;
            }
            if (keyguardSecAffordanceView.mFakeWallpaperView != null && !WallpaperUtils.isLiveWallpaperEnabled()) {
                WallpaperUtils.isSubDisplay();
                if ((WallpaperUtils.sWallpaperType[WallpaperUtils.isSubDisplay() ? 1 : 0] == 7) == false) {
                    ImageOptionCreator.ImageOption imageOption = new ImageOptionCreator.ImageOption();
                    Point realSize = ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).getRealSize();
                    imageOption.width = realSize.x;
                    imageOption.height = realSize.y;
                    keyguardSecAffordanceView.mFakeWallpaperView.setImageBitmap(keyguardSecAffordanceView.mWallpaperImageCreator.createImage(imageOption, null));
                    keyguardSecAffordanceView.mFakeWallpaperView.setVisibility(0);
                }
            }
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mFalsingCollector.getClass();
            NotificationPanelView notificationPanelView = notificationPanelViewController.mView;
            notificationPanelView.getLayoutDirection();
            notificationPanelView.requestDisallowInterceptTouchEvent(true);
            notificationPanelViewController.mOnlyAffordanceInThisMotion = true;
            notificationPanelViewController.mSecAffordanceHelper.isShortcutPreviewSwipingInProgress = true;
            Log.i("KeyguardSecAffordanceView", "startInitialPeekAnimation");
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, keyguardSecAffordanceView.mInitialPeekDistance);
            keyguardSecAffordanceView.mInitialPeekAnimator = ofFloat;
            ofFloat.setDuration(300L);
            ofFloat.setInterpolator(SCALE_INTERPOLATOR);
            ofFloat.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda0(keyguardSecAffordanceView, 7));
            ofFloat.addListener(keyguardSecAffordanceView.mInitialPeekAnimatorEndListener);
            keyguardSecAffordanceView.mInitialPeekAnimator.start();
            keyguardSecAffordanceView.setImageAlpha(0.0f, true);
            keyguardSecAffordanceView.setImageScale(0.9f, true);
            VelocityTracker velocityTracker2 = keyguardSecAffordanceView.mVelocityTracker;
            if (velocityTracker2 != null) {
                velocityTracker2.recycle();
            }
            VelocityTracker obtain = VelocityTracker.obtain();
            keyguardSecAffordanceView.mVelocityTracker = obtain;
            if (obtain != null) {
                obtain.addMovement(motionEvent);
            }
        } else if (actionMasked2 != 1) {
            if (actionMasked2 != 2) {
                if (actionMasked2 != 3) {
                    if (actionMasked2 == 5) {
                        keyguardSecAffordanceView.mJustClicked = false;
                        MotionEvent obtain2 = MotionEvent.obtain(motionEvent);
                        obtain2.setAction(3);
                        keyguardSecAffordanceView.dispatchTouchEvent(obtain2);
                        obtain2.recycle();
                        NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                        notificationPanelViewController2.mFalsingCollector.getClass();
                        notificationPanelViewController2.mSecAffordanceHelper.isShortcutPreviewSwipingInProgress = false;
                    }
                }
                keyguardSecAffordanceView.mTouchCancelled = true;
                velocityTracker = keyguardSecAffordanceView.mVelocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.addMovement(motionEvent);
                }
                if (keyguardSecAffordanceView.mIsShortcutLaunching && !keyguardSecAffordanceView.mIsTaskTypeShortcut) {
                    keyguardSecAffordanceView.mHandler.sendEmptyMessageDelayed(1001, 1500L);
                }
                if ((keyguardSecAffordanceView.mJustClicked || !keyguardSecAffordanceView.mIsUp) && !keyguardSecAffordanceView.mIsShortcutLaunching && !keyguardSecAffordanceView.mFling) {
                    keyguardSecAffordanceView.cancelAllAnimators();
                    keyguardSecAffordanceView.startRectangleShrinkAnimation();
                    ValueAnimator ofInt = ValueAnimator.ofInt(keyguardSecAffordanceView.mRectanglePaint.getAlpha(), 0);
                    keyguardSecAffordanceView.mRectangleShrinkeAlphaAnimator = ofInt;
                    ofInt.setDuration(200L);
                    ofInt.setInterpolator(ALPHA_INTERPOLATOR);
                    ofInt.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda0(keyguardSecAffordanceView, 8));
                    ofInt.addListener(keyguardSecAffordanceView.mRectangleShrinkeAlphaAnimatorEndListener);
                    keyguardSecAffordanceView.mRectangleShrinkeAlphaAnimator.start();
                    keyguardSecAffordanceView.mKeyguardUpdateMonitor.setShortcutLaunchInProgress(false);
                }
                if ((keyguardSecAffordanceView.mIsShortcutLaunching || keyguardSecAffordanceView.mIsTaskTypeShortcut) && (systemUIWallpaperBase = NotificationPanelViewController.this.mKeyguardWallpaperController.mWallpaperView) != null) {
                    systemUIWallpaperBase.updateDrawState(true);
                }
            } else if (!keyguardSecAffordanceView.mTouchCancelled) {
                ((CentralSurfacesImpl) NotificationPanelViewController.this.mCentralSurfaces).userActivity();
                VelocityTracker velocityTracker3 = keyguardSecAffordanceView.mVelocityTracker;
                if (velocityTracker3 != null) {
                    velocityTracker3.addMovement(motionEvent);
                }
                float f = keyguardSecAffordanceView.mRight ? keyguardSecAffordanceView.mInitialTouchX - x : x - keyguardSecAffordanceView.mInitialTouchX;
                if (f < 0.0f) {
                    f = 0.0f;
                }
                float hypot = (float) Math.hypot(f, keyguardSecAffordanceView.mInitialTouchY - y >= 0.0f ? r0 : 0.0f);
                if (hypot >= keyguardSecAffordanceView.mTouchSlop) {
                    Animator animator = keyguardSecAffordanceView.mInitialPeekAnimator;
                    if (animator != null) {
                        cancelAnimator(animator);
                        keyguardSecAffordanceView.cancelAnimatorSet();
                    }
                    float f2 = hypot + keyguardSecAffordanceView.mInitialPeekDistance;
                    float f3 = keyguardSecAffordanceView.mShortcutLaunchDistance;
                    if (f2 >= f3) {
                        f2 = DependencyGraph$$ExternalSyntheticOutline0.m20m(f2, f3, 0.2f, f3);
                        i2 = keyguardSecAffordanceView.mTouchSlop;
                    } else {
                        i2 = keyguardSecAffordanceView.mTouchSlop;
                    }
                    keyguardSecAffordanceView.mInitialPeekShowing = false;
                    keyguardSecAffordanceView.mJustClicked = false;
                    keyguardSecAffordanceView.mIsDown = false;
                    keyguardSecAffordanceView.updatePanelViews(f2 - i2);
                    keyguardSecAffordanceView.invalidate();
                }
            }
        } else if (!keyguardSecAffordanceView.mTouchCancelled) {
            keyguardSecAffordanceView.mIsUp = true;
            if (keyguardSecAffordanceView.mRectangleDistanceCovered < keyguardSecAffordanceView.mShortcutLaunchDistance || !keyguardSecAffordanceView.mDeviceInteractive) {
                NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
                notificationPanelViewController3.mFalsingCollector.getClass();
                notificationPanelViewController3.mSecAffordanceHelper.isShortcutPreviewSwipingInProgress = false;
                keyguardSecAffordanceView.cancelAllAnimators();
                keyguardSecAffordanceView.mKeyguardUpdateMonitor.setShortcutLaunchInProgress(false);
            } else {
                keyguardSecAffordanceView.launchShortcut(x, y);
            }
            VelocityTracker velocityTracker4 = keyguardSecAffordanceView.mVelocityTracker;
            if (velocityTracker4 != null) {
                velocityTracker4.recycle();
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
                    keyguardSecAffordanceView.resetFakeWallpaperView();
                    NotificationPanelViewController notificationPanelViewController4 = NotificationPanelViewController.this;
                    if (!notificationPanelViewController4.mHintAnimationRunning) {
                        notificationPanelViewController4.mHintAnimationRunning = true;
                        notificationPanelViewController4.mSecAffordanceHelper.getClass();
                        notificationPanelViewController4.mView.getLayoutDirection();
                    }
                }
            }
            keyguardSecAffordanceView.mTouchCancelled = true;
            velocityTracker = keyguardSecAffordanceView.mVelocityTracker;
            if (velocityTracker != null) {
            }
            if (keyguardSecAffordanceView.mIsShortcutLaunching) {
                keyguardSecAffordanceView.mHandler.sendEmptyMessageDelayed(1001, 1500L);
            }
            if (keyguardSecAffordanceView.mJustClicked) {
            }
            keyguardSecAffordanceView.cancelAllAnimators();
            keyguardSecAffordanceView.startRectangleShrinkAnimation();
            ValueAnimator ofInt2 = ValueAnimator.ofInt(keyguardSecAffordanceView.mRectanglePaint.getAlpha(), 0);
            keyguardSecAffordanceView.mRectangleShrinkeAlphaAnimator = ofInt2;
            ofInt2.setDuration(200L);
            ofInt2.setInterpolator(ALPHA_INTERPOLATOR);
            ofInt2.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda0(keyguardSecAffordanceView, 8));
            ofInt2.addListener(keyguardSecAffordanceView.mRectangleShrinkeAlphaAnimatorEndListener);
            keyguardSecAffordanceView.mRectangleShrinkeAlphaAnimator.start();
            keyguardSecAffordanceView.mKeyguardUpdateMonitor.setShortcutLaunchInProgress(false);
            if (keyguardSecAffordanceView.mIsShortcutLaunching) {
            }
            systemUIWallpaperBase.updateDrawState(true);
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

    public final void reset() {
        if ((mIsShowBouncerAnimation && this.mBlurPanelView != null) || mWaitForReset) {
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("WaitForReset right:"), this.mRight, "KeyguardSecAffordanceView");
            return;
        }
        if (hasMessages(1001)) {
            removeMessages(1001);
        }
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("reset right:"), this.mRight, "KeyguardSecAffordanceView");
        this.mIsNoUnlockNeeded = false;
        this.mIsTaskTypeShortcutEnabled = false;
        this.mIsTransitIconNeeded = false;
        this.mIsTaskTypeShortcut = false;
        mIsShowBouncerAnimation = false;
        this.mIsShortcutLaunching = false;
        cancelAnimator(this.mRectangleScaleAnimator);
        cancelAnimator(this.mRectangleIconScaleAnimator);
        cancelAnimator(this.mRectangleAlphaAnimator);
        cancelAllAnimators();
        cancelAnimatorSet();
        if (!this.mQsExpanded && !NotificationPanelViewController.this.mFullScreenModeEnabled) {
            updatePanelViews(0.0f);
        }
        this.mRectangleIconDrawable = null;
        setRectangleColor();
        this.mFling = false;
        this.mRectangleIconBounds.set(0, 0, 0, 0);
        this.mRectangleBounds.set(0, 0, 0, 0);
        invalidate();
        resetBlurRectangleView();
        resetFakeWallpaperView();
    }

    public final void resetBlurRectangleView() {
        View view = this.mBlurPanelView;
        if (view == null) {
            return;
        }
        view.semSetBlurEnabled(false);
        this.mPanelBackground.setBackground(null);
        this.mPanelIcon.setImageDrawable(null);
        this.mBlurPanelRoot.setVisibility(8);
    }

    public final void resetFakeWallpaperView() {
        ImageView imageView = this.mFakeWallpaperView;
        if (imageView == null) {
            return;
        }
        imageView.setImageBitmap(null);
        this.mFakeWallpaperView.setVisibility(8);
    }

    public final void setImageAlpha(float f, boolean z) {
        if (this.mFling) {
            return;
        }
        cancelAnimator(this.mBottomIconAlphaAnimator);
        int i = (int) (f * 255.0f);
        if (i == getImageAlpha()) {
            return;
        }
        final Drawable background = getBackground();
        if (!z) {
            if (i <= 0) {
                i = 1;
            }
            if (background != null) {
                background.mutate().setAlpha(i);
            }
            setImageAlpha(i);
            return;
        }
        ValueAnimator ofInt = ValueAnimator.ofInt(getImageAlpha(), i);
        this.mBottomIconAlphaAnimator = ofInt;
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                Drawable drawable = background;
                Interpolator interpolator = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView.getClass();
                int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                if (drawable != null) {
                    drawable.mutate().setAlpha(intValue);
                }
                if (intValue <= 0) {
                    intValue = 1;
                }
                keyguardSecAffordanceView.setImageAlpha(intValue);
            }
        });
        ofInt.addListener(this.mBottomIconAlphaEndListener);
        ofInt.setInterpolator(ALPHA_INTERPOLATOR);
        ofInt.setDuration(300L);
        ofInt.setStartDelay(0L);
        ofInt.start();
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
        ofFloat.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda0(this, 2));
        ofFloat.addListener(this.mBottomIconScaleEndListener);
        ofFloat.setInterpolator(SCALE_INTERPOLATOR);
        ofFloat.setDuration(300L);
        ofFloat.start();
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x005d, code lost:
    
        if (r4 > (r7 + r8)) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0089, code lost:
    
        r4 = 0;
        r9 = 0;
        r11 = 0;
        r14 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0087, code lost:
    
        if (r14 < (r7 * (-1))) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setRectangleBounds(float f) {
        float f2;
        float f3;
        int i;
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
            i6 = AbsActionBarView$$ExternalSyntheticOutline0.m8m((int) f9, i7, 2, i6);
        }
        int i8 = i4 / 2;
        int i9 = (int) ((((f7 - f6) / 2.0f) + f6) - i8);
        int i10 = i9 + i4;
        boolean z = this.mRight;
        if (z) {
            int i11 = this.mScreenWidth;
            float f10 = i11;
            float f11 = f10 - f;
            f3 = f11 >= 0.0f ? f11 : 0.0f;
            f2 = f10 + f3;
            if (f2 > f10) {
                f2 = this.mRectangleCornerRadius + i11;
            }
            i2 = ((int) f3) + i6;
            int i12 = (i11 / 2) - i8;
            if (i2 < i12) {
                i2 = i12;
            }
            i = i2 + i4;
        } else {
            int i13 = this.mScreenWidth;
            float f12 = i13;
            f2 = f > f12 ? f12 : f;
            float f13 = f2 - f12;
            f3 = f13 < 0.0f ? -this.mRectangleCornerRadius : f13;
            int i14 = ((int) f2) - i6;
            int i15 = (i3 / 2) + (i13 / 2);
            i = i14 > i15 ? i15 : i14;
            i2 = i - i4;
        }
        float f14 = z ? this.mScreenWidth - f3 : f2;
        this.mRectangleDistanceCovered = f14;
        if (f14 < this.mScreenWidth) {
            f4 = f7;
        }
        this.mRectangleScaleStart = f5;
        this.mRectangleIconScaleStart = f8;
        float f15 = this.mShortcutLaunchDistance;
        int i16 = 102;
        if (f14 >= f15 && !this.mLaunchThresoldAcheived) {
            this.mLaunchThresoldAcheived = true;
            startRectangleScaleAnimation(this.mIsLandScape ? 0.05f : 0.1f);
            startRectangleIconScaleAnimation(1.2f);
            this.mRectangleIconAlpha = 255;
            if (this.mIsTaskTypeShortcut) {
                boolean z2 = this.mIsTaskTypeShortcutEnabled;
                this.mRectangleIconAlpha = z2 ? 102 : 255;
                r8 = z2 ? 102 : 204;
                updateRectangleIconDrawable(this.mIsTransitIconNeeded);
            }
            startRectangleAlphaAnimation(r8);
            this.mVibrationUtil.playVibration(108);
        } else if (f14 < f15 && this.mLaunchThresoldAcheived) {
            this.mLaunchThresoldAcheived = false;
            startRectangleScaleAnimation(this.mIsLandScape ? 0.1f : 0.15f);
            startRectangleIconScaleAnimation(1.0f);
            this.mRectangleIconAlpha = 102;
            if (this.mIsTaskTypeShortcut) {
                boolean z3 = this.mIsTaskTypeShortcutEnabled;
                this.mRectangleIconAlpha = z3 ? 255 : 102;
                r8 = z3 ? 204 : 102;
                updateRectangleIconDrawable(false);
                i16 = r8;
            }
            startRectangleAlphaAnimation(i16);
            this.mVibrationUtil.playVibration(109);
        }
        this.mRectangleBounds.set((int) f3, (int) f6, (int) f2, (int) f4);
        this.mRectangleIconBounds.set(i2, i9, i, i10);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setRectangleColor() {
        PaintDrawable paintDrawable;
        if (!this.mShortcutForCamera) {
            if (!(this.mSettingsHelper.mItemLists.get("display_night_theme").getIntValue() == 1)) {
                this.mRectangleColor = -1;
                this.mRectanglePaint.setColor(this.mRectangleColor);
                paintDrawable = this.mPanelBackgroundDrawable;
                if (paintDrawable == null) {
                    paintDrawable.getPaint().setColor(this.mRectangleColor);
                    return;
                }
                return;
            }
        }
        this.mRectangleColor = EmergencyPhoneWidget.BG_COLOR;
        this.mRectanglePaint.setColor(this.mRectangleColor);
        paintDrawable = this.mPanelBackgroundDrawable;
        if (paintDrawable == null) {
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
        cancelAnimator(this.mRectangleAlphaAnimator);
        ValueAnimator ofInt = ValueAnimator.ofInt(this.mRectanglePaint.getAlpha(), i);
        this.mRectangleAlphaAnimator = ofInt;
        ofInt.setDuration(200L);
        ofInt.setInterpolator(ALPHA_INTERPOLATOR);
        ofInt.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda0(this, 5));
        ofInt.addListener(this.mRectangleAlphaAnimatorEndListener);
        this.mRectangleAlphaAnimator.start();
    }

    public final void startRectangleIconScaleAnimation(float f) {
        cancelAnimator(this.mRectangleIconScaleAnimator);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mRectangleIconScaleStart, f);
        this.mRectangleIconScaleAnimator = ofFloat;
        ofFloat.setDuration(this.mShortcutForCamera ? 300L : 450L);
        ofFloat.setInterpolator(SCALE_INTERPOLATOR);
        ofFloat.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda0(this, 4));
        ofFloat.addListener(this.mRectangleIconScaleAnimatorEndListener);
        this.mRectangleIconScaleAnimator.start();
    }

    public final void startRectangleScaleAnimation(float f) {
        cancelAnimator(this.mRectangleScaleAnimator);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mRectangleScaleStart, f);
        this.mRectangleScaleAnimator = ofFloat;
        ofFloat.setDuration(this.mShortcutForCamera ? 300L : 450L);
        ofFloat.setInterpolator(SCALE_INTERPOLATOR);
        ofFloat.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda0(this, 3));
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
        ofFloat.addUpdateListener(new KeyguardSecAffordanceView$$ExternalSyntheticLambda0(this, 1));
        ofFloat.addListener(this.mRectangleShrinkAnimatorEndListener);
        this.mRectangleShrinkAnimator.start();
    }

    public final void updateDisplayParameters() {
        Resources resources = ((ImageView) this).mContext.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int dimensionPixelSize = resources.getDimensionPixelSize(android.R.dimen.notification_custom_view_max_image_height_low_ram);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.status_bar_height);
        if (this.mIsLandScape) {
            this.mScreenHeight = displayMetrics.heightPixels;
            this.mScreenWidth = displayMetrics.widthPixels + dimensionPixelSize + dimensionPixelSize2;
            this.mVerticalScale = 0.1f;
            this.mInitialPeekDistance = resources.getDimensionPixelSize(R.dimen.keyguard_affordance_initial_view_out_landscape);
        } else {
            this.mScreenWidth = displayMetrics.widthPixels;
            this.mScreenHeight = displayMetrics.heightPixels + dimensionPixelSize + dimensionPixelSize2;
            this.mVerticalScale = 0.15f;
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
        this.mRectangleIconSize = (int) (resources.getDimensionPixelSize(R.dimen.keyguard_affordance_app_icon_size) * 0.95f);
    }

    public final void updatePanelViews(float f) {
        float f2;
        float max = Math.max(0.0f, f - this.mInitialPeekDistance);
        View view = this.mPanelDimView;
        if (view != null) {
            if (this.mIsDown) {
                f2 = 0.0f;
            } else {
                f2 = 0.3f;
                float f3 = (max / this.mShortcutLaunchDistance) * 0.3f;
                if (f3 <= 0.3f) {
                    f2 = f3;
                }
            }
            view.setAlpha(f2);
        }
        if (this.mBlurPanelView != null) {
            this.mBlurPanelRadius = 0;
            if (!this.mIsDown) {
                int i = (int) ((max / this.mShortcutLaunchDistance) * 200.0f);
                this.mBlurPanelRadius = i;
                if (i > 200) {
                    this.mBlurPanelRadius = 200;
                }
            }
        }
        if (this.mIsDown) {
            View view2 = this.mLockWallpaperContainer;
            if (view2 != null) {
                int[] iArr = new int[2];
                view2.getLocationOnScreen(iArr);
                int i2 = iArr[1];
                if (view2 == this.mClockView || view2 == this.mMusicContainer) {
                    view2.setPivotX(r9.getWidth() / 2.0f);
                    int i3 = this.mAffordancePivotY;
                    if (i2 < i3) {
                        view2.setPivotY(i3);
                    } else {
                        view2.setPivotY(i3 * (-1));
                    }
                } else {
                    if (view2 == this.mNotificationStackScrollerView) {
                        view2.setPivotX(r7.getWidth() / 2.0f);
                        view2.setPivotY(this.mAffordancePivotY);
                    } else {
                        if (view2 == this.mNotificationPanelIconOnlyContainer) {
                            view2.setPivotX(r7.getWidth() / 2.0f);
                            view2.setPivotY(this.mAffordancePivotY - (this.mClockView != null ? r7.getHeight() : 0));
                        }
                    }
                }
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "scaleX", 1.0f);
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view2, "scaleY", 1.0f);
                ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view2, "alpha", 1.0f);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3);
                animatorSet.setDuration(400L);
                animatorSet.setInterpolator(SCALE_INTERPOLATOR);
                animatorSet.start();
                if (this.mAnimatorSet == null) {
                    this.mAnimatorSet = new ArrayList();
                }
                ((ArrayList) this.mAnimatorSet).add(animatorSet);
            }
        } else {
            View view3 = this.mLockWallpaperContainer;
            float f4 = 1.0f - ((max / this.mShortcutLaunchDistance) * 0.050000012f);
            if (f4 < 0.95f) {
                f4 = 0.95f;
            }
            if (max == 0.0f) {
                f4 = 1.0f;
            }
            setUScaleAnimator(view3, f4, 1.0f);
        }
        if (this.mIsDown) {
            setUScaleAnimator(this.mNotificationPanelView, 1.0f, 1.0f);
        } else {
            float f5 = this.mShortcutLaunchDistance;
            float f6 = 1.0f - (max / f5);
            if (f6 < 0.0f) {
                f6 = 0.0f;
            }
            float f7 = 1.0f - ((max / f5) * 0.050000012f);
            setUScaleAnimator(this.mNotificationPanelView, max != 0.0f ? f7 >= 0.95f ? f7 : 0.95f : 1.0f, f6);
        }
        updateRectangleCornerRadius(-1.0f);
        setRectangleBounds(f);
    }

    public final void updateRectangleCornerRadius(float f) {
        if (this.mIsDown) {
            return;
        }
        if (f == -1.0f) {
            this.mRectangleCornerRadius = 100;
            return;
        }
        float f2 = this.mShortcutLaunchDistance;
        int sqrt = (int) ((Math.sqrt(1.0f - ((f - f2) / (this.mScreenWidth - f2))) * 85.0d) + 15.0d);
        this.mRectangleCornerRadius = sqrt;
        this.mRectangleCornerRadius = Math.max(0, sqrt);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [boolean] */
    public final void updateRectangleIconDrawable(boolean z) {
        Drawable drawable;
        boolean z2;
        Drawable drawable2;
        boolean z3;
        ?? r0 = this.mRight;
        KeyguardShortcutManager keyguardShortcutManager = this.mShortcutManager;
        keyguardShortcutManager.getClass();
        if (r0 < 0 || r0 >= 2) {
            NestedScrollView$$ExternalSyntheticOutline0.m34m("IllegalArgument : ", r0 == true ? 1 : 0, "KeyguardShortcutManager");
            drawable = null;
        } else {
            KeyguardShortcutManager.ShortcutData[] shortcutDataArr = keyguardShortcutManager.mShortcuts;
            if (z) {
                KeyguardShortcutManager.ShortcutData shortcutData = shortcutDataArr[r0 == true ? 1 : 0];
                Intrinsics.checkNotNull(shortcutData);
                drawable = shortcutData.mPanelTransitDrawable;
            } else {
                KeyguardShortcutManager.ShortcutData shortcutData2 = shortcutDataArr[r0 == true ? 1 : 0];
                Intrinsics.checkNotNull(shortcutData2);
                drawable = shortcutData2.mPanelDrawable;
            }
        }
        this.mRectangleIconDrawable = drawable;
        KeyguardShortcutManager keyguardShortcutManager2 = this.mShortcutManager;
        keyguardShortcutManager2.getClass();
        Intent intent = new Intent("android.intent.action.MAIN");
        KeyguardShortcutManager.ShortcutData shortcutData3 = keyguardShortcutManager2.mShortcuts[r0 == true ? 1 : 0];
        Intrinsics.checkNotNull(shortcutData3);
        intent.setComponent(shortcutData3.mComponentName);
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        PackageManager packageManager = keyguardShortcutManager2.mPm;
        ResolveInfo resolveActivityAsUser = packageManager.resolveActivityAsUser(intent, 129, currentUser);
        ActivityInfo activityInfo = resolveActivityAsUser.activityInfo;
        if (activityInfo != null) {
            Drawable samsungAppIconDrawable = keyguardShortcutManager2.mSettingsHelper.mItemLists.get("current_sec_appicon_theme_package").getStringValue() == null ? keyguardShortcutManager2.getSamsungAppIconDrawable(activityInfo.packageName) : null;
            if (samsungAppIconDrawable == null) {
                samsungAppIconDrawable = activityInfo.loadIcon(packageManager, true, 1);
            }
            if (samsungAppIconDrawable == null) {
                samsungAppIconDrawable = activityInfo.loadDefaultIcon(packageManager);
            }
            z2 = keyguardShortcutManager2.isblendNeeded(resolveActivityAsUser.activityInfo, samsungAppIconDrawable);
        } else {
            Slog.d("KeyguardShortcutManager", "updateShortcut : " + (r0 == true ? 1 : 0) + " activityInfo is null, resolveInfo is : " + resolveActivityAsUser + ",  return FALSE");
            z2 = false;
        }
        if (z2 && (drawable2 = this.mRectangleIconDrawable) != null) {
            Bitmap bitmap = ((BitmapDrawable) drawable2).getBitmap();
            KeyguardShortcutManager keyguardShortcutManager3 = this.mShortcutManager;
            KeyguardShortcutManager.ShortcutData shortcutData4 = keyguardShortcutManager3.mShortcuts[r0 == true ? 1 : 0];
            Intrinsics.checkNotNull(shortcutData4);
            if (!KeyguardShortcutManager.isSamsungCameraPackage(shortcutData4.mComponentName)) {
                if ((this.mSettingsHelper.mItemLists.get("display_night_theme").getIntValue() == 1) == false) {
                    z3 = true;
                    this.mRectangleIconDrawable = new BitmapDrawable(keyguardShortcutManager3.grayInvertDrawable(bitmap, z3, null, false, true));
                }
            }
            z3 = false;
            this.mRectangleIconDrawable = new BitmapDrawable(keyguardShortcutManager3.grayInvertDrawable(bitmap, z3, null, false, true));
        }
        ImageView imageView = this.mPanelIcon;
        if (imageView != null) {
            imageView.setImageDrawable(this.mRectangleIconDrawable);
        }
    }

    @Override // com.android.systemui.widget.SystemUIImageView, com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        super.updateStyle(j, semWallpaperColors);
        this.mShortcutManager.updateShortcuts();
    }

    public KeyguardSecAffordanceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KeyguardSecAffordanceView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$2] */
    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$11] */
    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$12] */
    /* JADX WARN: Type inference failed for: r0v12, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$13] */
    /* JADX WARN: Type inference failed for: r0v13, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$14] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$4] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$5] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$6] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$7] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$8] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$9] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$10] */
    /* JADX WARN: Type inference failed for: r6v6, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r6v7, types: [com.android.systemui.statusbar.KeyguardSecAffordanceView$1, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    public KeyguardSecAffordanceView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mRectangleIconBounds = new Rect();
        this.mRectangleBounds = new Rect();
        this.mDeviceInteractive = true;
        this.mTouchCancelled = false;
        this.mRight = false;
        this.mIsTargetView = false;
        this.mJustClicked = false;
        this.mIsShortcutForPhone = false;
        this.mLaunchThresoldAcheived = false;
        this.mQsExpanded = false;
        this.mIsUp = false;
        this.mInitialPeekShowing = false;
        this.mIsDown = false;
        this.mIsTaskTypeShortcut = false;
        this.mIsTaskTypeShortcutEnabled = false;
        this.mIsTransitIconNeeded = false;
        this.mIsNoUnlockNeeded = false;
        this.mTouchSlop = 5;
        this.mRectangleIconAlpha = 255;
        this.mOldPanelBackgroundAlpha = 0;
        this.mBlurPanelRadius = 0;
        this.mRectangleCornerRadius = 100;
        this.mRectangleDistanceCovered = 0.0f;
        this.mImageScale = 1.0f;
        this.mVerticalScale = 0.15f;
        this.mRectangleIconScale = 1.0f;
        this.mVisibilityListener = new IntConsumer() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda1
            @Override // java.util.function.IntConsumer
            public final void accept(int i3) {
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                Interpolator interpolator = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView.getClass();
                if (i3 != 0) {
                    if (KeyguardSecAffordanceView.mWaitForReset || (KeyguardSecAffordanceView.mIsShowBouncerAnimation && keyguardSecAffordanceView.mBlurPanelView != null)) {
                        KeyguardSecAffordanceView.mWaitForReset = false;
                        KeyguardSecAffordanceView.mIsShowBouncerAnimation = false;
                        keyguardSecAffordanceView.reset();
                    }
                }
            }
        };
        ?? r6 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (uri != null && Settings.System.getUriFor("white_lockscreen_wallpaper").equals(uri)) {
                    KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                    keyguardSecAffordanceView.mSettingsHelper.isWhiteKeyguardWallpaper();
                    keyguardSecAffordanceView.getClass();
                    keyguardSecAffordanceView.setRectangleColor();
                }
            }
        };
        this.mShortcutCallback = r6;
        this.mDisplayObserver = new DisplayLifecycle.Observer() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.2
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onDisplayChanged(int i3) {
                Interpolator interpolator = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                KeyguardSecAffordanceView.this.updateDisplayParameters();
            }
        };
        this.mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.3
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
                KeyguardSecAffordanceView.this.mDeviceInteractive = false;
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStartedWakingUp() {
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                keyguardSecAffordanceView.mDeviceInteractive = true;
                keyguardSecAffordanceView.mIsTargetView = false;
            }
        };
        this.mBottomIconAlphaEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView.this.mBottomIconAlphaAnimator = null;
            }
        };
        this.mRectangleShrinkAnimatorEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                keyguardSecAffordanceView.mRectangleShrinkAnimator = null;
                keyguardSecAffordanceView.mFling = false;
                keyguardSecAffordanceView.mRectangleIconBounds.set(0, 0, 0, 0);
                KeyguardSecAffordanceView.this.mRectangleBounds.set(0, 0, 0, 0);
                KeyguardSecAffordanceView.this.resetBlurRectangleView();
                KeyguardSecAffordanceView.this.resetFakeWallpaperView();
            }
        };
        this.mRectangleShrinkeAlphaAnimatorEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView.this.mRectangleShrinkeAlphaAnimator = null;
            }
        };
        this.mShortcutLaunchAnimatorEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.7
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                keyguardSecAffordanceView.mFling = false;
                KeyguardSecAffordanceHelper.Callback callback = keyguardSecAffordanceView.mHelperCallback;
                boolean z = keyguardSecAffordanceView.mRight;
                boolean isSecure = keyguardSecAffordanceView.isSecure();
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
                notificationPanelViewController.mShortcut = z ? 1 : 0;
                if (!isSecure && !((KeyguardShortcutManager) Dependency.get(KeyguardShortcutManager.class)).isTaskType(notificationPanelViewController.mShortcut)) {
                    ((MessageRouterImpl) ((CentralSurfacesImpl) notificationPanelViewController.mCentralSurfaces).mMessageRouter).sendMessageDelayed(1003, 5000L);
                }
                if (!notificationPanelViewController.mKeyguardStateController.mKeyguardGoingAway && !((KeyguardShortcutManager) Dependency.get(KeyguardShortcutManager.class)).isNoUnlockNeeded(notificationPanelViewController.mShortcut)) {
                    notificationPanelViewController.mSecAffordanceHelper.reset();
                }
                KeyguardSecAffordanceView keyguardSecAffordanceView2 = KeyguardSecAffordanceView.this;
                if (keyguardSecAffordanceView2.mIsTaskTypeShortcut) {
                    keyguardSecAffordanceView2.mKeyguardUpdateMonitor.setShortcutLaunchInProgress(false);
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
                ((CentralSurfacesImpl) notificationPanelViewController2.mCentralSurfaces).mStatusBarKeyguardViewManager.readyForKeyguardDone();
                notificationPanelViewController2.mIsLaunchTransitionFinished = !((KeyguardShortcutManager) Dependency.get(KeyguardShortcutManager.class)).isTaskType(notificationPanelViewController2.mShortcut);
                KeyguardSecAffordanceView.this.mShortcutLaunchAnimator = null;
            }
        };
        this.mShortcutLaunchAlphaAnimatorEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView.8
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                keyguardSecAffordanceView.mShortcutLaunchAlphaAnimator = null;
                keyguardSecAffordanceView.mIsShortcutLaunching = false;
                if (keyguardSecAffordanceView.mIsTaskTypeShortcut) {
                    KeyguardSecAffordanceView.mWaitForReset = false;
                    keyguardSecAffordanceView.reset();
                }
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
                KeyguardSecAffordanceView keyguardSecAffordanceView = KeyguardSecAffordanceView.this;
                keyguardSecAffordanceView.mIsShortcutLaunching = false;
                KeyguardSecAffordanceView.mIsShowBouncerAnimation = false;
                KeyguardSecAffordanceView.mWaitForReset = false;
                NotificationPanelViewController.this.mSecAffordanceHelper.reset();
            }
        };
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.statusbar.KeyguardSecAffordanceView$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyguardSecAffordanceView.m1702$r8$lambda$4vd0Zn5d63IMpqv1U9KSTbBDHI(KeyguardSecAffordanceView.this, (Integer) obj);
            }
        });
        SystemUIAppComponentFactoryBase.Companion.getClass();
        SystemUIAppComponentFactoryBase.systemUIInitializer.getSysUIComponent().inject(this);
        ((ImageView) this).mContext = context;
        this.mRectanglePaint = new Paint();
        KeyguardStateController keyguardStateController = (KeyguardStateController) Dependency.get(KeyguardStateController.class);
        this.mKeyguardStateController = keyguardStateController;
        this.mKeyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class);
        this.mShortcutManager = (KeyguardShortcutManager) Dependency.get(KeyguardShortcutManager.class);
        SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        this.mSettingsHelper = settingsHelper;
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardStateController;
        keyguardStateControllerImpl.addCallback(this);
        this.mIsSecure = keyguardStateControllerImpl.mSecure;
        this.mTrusted = keyguardStateControllerImpl.mTrusted;
        this.mCanDismissLockScreen = keyguardStateControllerImpl.mCanDismissLockScreen;
        settingsHelper.registerCallback(r6, Settings.System.getUriFor("white_lockscreen_wallpaper"));
        settingsHelper.isWhiteKeyguardWallpaper();
        setRectangleColor();
        this.mIsLandScape = ((ImageView) this).mContext.getResources().getConfiguration().orientation == 2;
        updateDisplayParameters();
        this.mTelecomManager = TelecomManager.from(((ImageView) this).mContext);
    }
}
