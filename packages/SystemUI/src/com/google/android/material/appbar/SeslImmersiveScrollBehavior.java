package com.google.android.material.appbar;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowInsetsAnimationControlListener;
import android.view.WindowInsetsAnimationController;
import android.view.WindowInsetsController;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.PathInterpolator;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.content.res.SeslConfigurationReflector;
import com.android.keyguard.KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.SeslAppBarHelper;
import com.google.android.material.internal.SeslContextUtils;
import java.lang.reflect.Method;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslImmersiveScrollBehavior extends AppBarLayout.Behavior {
    public boolean isRoundedCornerHide;
    public WindowInsetsAnimationController mAnimationController;
    public final AnonymousClass1 mAnimationHandler;
    public AppBarLayout mAppBarLayout;
    public View mBottomArea;
    public boolean mCalledHideShowOnLayoutChild;
    public boolean mCanImmersiveScroll;
    public CancellationSignal mCancellationSignal;
    public CollapsingToolbarLayout mCollapsingToolbarLayout;
    public View mContentView;
    public Context mContext;
    public CoordinatorLayout mCoordinatorLayout;
    public float mCurOffset;
    public View mDecorView;
    public WindowInsets mDecorViewInset;
    public boolean mIsMultiWindow;
    public final boolean mIsSetAutoRestore;
    public View mNavigationBarBg;
    public int mNavigationBarBgHeight;
    public int mNavigationBarFrameHeight;
    public boolean mNeedRestoreAnim;
    public ValueAnimator mOffsetAnimator;
    public final AnonymousClass2 mOffsetChangedListener;
    public AnonymousClass3 mOnInsetsChangedListener;
    public int mPrevOffset;
    public int mPrevOrientation;
    public boolean mShownAtDown;
    public View mStatusBarBg;
    public int mStatusBarHeight;
    public View mTargetView;
    public boolean mToolIsMouse;
    public final AnonymousClass6 mWindowAnimationCallback;
    public final AnonymousClass5 mWindowInsetsAnimationControlListener;
    public WindowInsetsController mWindowInsetsController;

    /* JADX WARN: Type inference failed for: r0v2, types: [com.google.android.material.appbar.SeslImmersiveScrollBehavior$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.google.android.material.appbar.SeslImmersiveScrollBehavior$2] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.google.android.material.appbar.SeslImmersiveScrollBehavior$5] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.google.android.material.appbar.SeslImmersiveScrollBehavior$6] */
    public SeslImmersiveScrollBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurOffset = 0.0f;
        this.mCanImmersiveScroll = true;
        this.mWindowInsetsController = null;
        this.mOnInsetsChangedListener = null;
        this.mNeedRestoreAnim = true;
        this.mIsSetAutoRestore = true;
        this.isRoundedCornerHide = false;
        this.mCalledHideShowOnLayoutChild = false;
        this.mAnimationHandler = new Handler(Looper.getMainLooper()) { // from class: com.google.android.material.appbar.SeslImmersiveScrollBehavior.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what == 100) {
                    final SeslImmersiveScrollBehavior seslImmersiveScrollBehavior = SeslImmersiveScrollBehavior.this;
                    if (seslImmersiveScrollBehavior.isAppBarHide()) {
                        int i = -seslImmersiveScrollBehavior.mAppBarLayout.getTotalScrollRange();
                        final CoordinatorLayout coordinatorLayout = seslImmersiveScrollBehavior.mCoordinatorLayout;
                        final AppBarLayout appBarLayout = seslImmersiveScrollBehavior.mAppBarLayout;
                        seslImmersiveScrollBehavior.mPrevOffset = i;
                        PathInterpolator pathInterpolator = new PathInterpolator(0.17f, 0.17f, 0.2f, 1.0f);
                        float seslGetCollapsedHeight = (-seslImmersiveScrollBehavior.mAppBarLayout.getHeight()) + seslImmersiveScrollBehavior.mAppBarLayout.seslGetCollapsedHeight();
                        final int[] iArr = {0};
                        ValueAnimator valueAnimator = seslImmersiveScrollBehavior.mOffsetAnimator;
                        if (valueAnimator == null) {
                            ValueAnimator valueAnimator2 = new ValueAnimator();
                            seslImmersiveScrollBehavior.mOffsetAnimator = valueAnimator2;
                            valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.appbar.SeslImmersiveScrollBehavior.7
                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                                    if (SeslImmersiveScrollBehavior.this.mTargetView == null) {
                                        Log.e("SeslImmersiveScrollBehavior", "mTargetView is null");
                                        return;
                                    }
                                    int intValue = ((Integer) valueAnimator3.getAnimatedValue()).intValue();
                                    int[] iArr2 = iArr;
                                    SeslImmersiveScrollBehavior seslImmersiveScrollBehavior2 = SeslImmersiveScrollBehavior.this;
                                    int i2 = seslImmersiveScrollBehavior2.mPrevOffset - intValue;
                                    iArr2[0] = i2;
                                    seslImmersiveScrollBehavior2.mTargetView.scrollBy(0, -i2);
                                    SeslImmersiveScrollBehavior.this.setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, intValue);
                                    SeslImmersiveScrollBehavior.this.mPrevOffset = intValue;
                                }
                            });
                        } else {
                            valueAnimator.cancel();
                        }
                        seslImmersiveScrollBehavior.mOffsetAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.appbar.SeslImmersiveScrollBehavior.8
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                super.onAnimationEnd(animator);
                                View view = SeslImmersiveScrollBehavior.this.mNavigationBarBg;
                                if (view != null) {
                                    view.setTranslationY(0.0f);
                                }
                                WindowInsetsAnimationController windowInsetsAnimationController = SeslImmersiveScrollBehavior.this.mAnimationController;
                                if (windowInsetsAnimationController != null) {
                                    windowInsetsAnimationController.finish(true);
                                }
                            }
                        });
                        seslImmersiveScrollBehavior.mOffsetAnimator.setDuration(150L);
                        seslImmersiveScrollBehavior.mOffsetAnimator.setInterpolator(pathInterpolator);
                        seslImmersiveScrollBehavior.mOffsetAnimator.setStartDelay(0L);
                        seslImmersiveScrollBehavior.mOffsetAnimator.setIntValues(seslImmersiveScrollBehavior.mNeedRestoreAnim ? -seslImmersiveScrollBehavior.mAppBarLayout.getHeight() : (int) seslGetCollapsedHeight, (int) seslGetCollapsedHeight);
                        seslImmersiveScrollBehavior.mOffsetAnimator.start();
                    }
                }
            }
        };
        this.mOffsetChangedListener = new AppBarLayout.OnOffsetChangedListener() { // from class: com.google.android.material.appbar.SeslImmersiveScrollBehavior.2
            /* JADX WARN: Code restructure failed: missing block: B:86:0x0186, code lost:
            
                if (r5 == 1) goto L95;
             */
            /* JADX WARN: Removed duplicated region for block: B:82:0x019c  */
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onOffsetChanged(com.google.android.material.appbar.AppBarLayout r17, int r18) {
                /*
                    Method dump skipped, instructions count: 660
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.SeslImmersiveScrollBehavior.AnonymousClass2.onOffsetChanged(com.google.android.material.appbar.AppBarLayout, int):void");
            }
        };
        this.mWindowInsetsAnimationControlListener = new WindowInsetsAnimationControlListener() { // from class: com.google.android.material.appbar.SeslImmersiveScrollBehavior.5
            @Override // android.view.WindowInsetsAnimationControlListener
            public final void onCancelled(WindowInsetsAnimationController windowInsetsAnimationController) {
                SeslImmersiveScrollBehavior.this.cancelWindowInsetsAnimationController();
            }

            @Override // android.view.WindowInsetsAnimationControlListener
            public final void onFinished(WindowInsetsAnimationController windowInsetsAnimationController) {
                SeslImmersiveScrollBehavior seslImmersiveScrollBehavior = SeslImmersiveScrollBehavior.this;
                seslImmersiveScrollBehavior.mAnimationController = null;
                seslImmersiveScrollBehavior.mCancellationSignal = null;
                seslImmersiveScrollBehavior.mShownAtDown = false;
            }

            /* JADX WARN: Code restructure failed: missing block: B:14:0x003f, code lost:
            
                if (r1 == 1) goto L15;
             */
            @Override // android.view.WindowInsetsAnimationControlListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onReady(android.view.WindowInsetsAnimationController r6, int r7) {
                /*
                    r5 = this;
                    com.google.android.material.appbar.SeslImmersiveScrollBehavior r5 = com.google.android.material.appbar.SeslImmersiveScrollBehavior.this
                    android.view.View r7 = r5.mDecorView
                    if (r7 == 0) goto L56
                    r7 = 0
                    r5.mCancellationSignal = r7
                    r5.mAnimationController = r6
                    android.content.Context r6 = r5.mContext
                    boolean r6 = com.google.android.material.internal.SeslDisplayUtils.isPinEdgeEnabled(r6)
                    r7 = 0
                    if (r6 == 0) goto L42
                    android.view.WindowInsets r6 = r5.mDecorViewInset
                    int r0 = android.view.WindowInsets.Type.navigationBars()
                    android.graphics.Insets r6 = r6.getInsets(r0)
                    android.content.Context r0 = r5.mContext
                    int r0 = com.google.android.material.internal.SeslDisplayUtils.getPinnedEdgeWidth(r0)
                    android.content.Context r1 = r5.mContext
                    android.content.ContentResolver r1 = r1.getContentResolver()
                    java.lang.String r2 = "active_edge_area"
                    r3 = 1
                    int r1 = android.provider.Settings.System.getInt(r1, r2, r3)
                    int r2 = r6.left
                    if (r0 != r2) goto L3b
                    if (r1 != 0) goto L3b
                    r4 = r0
                    r0 = r7
                    r7 = r4
                    goto L43
                L3b:
                    int r6 = r6.right
                    if (r0 != r6) goto L42
                    if (r1 != r3) goto L42
                    goto L43
                L42:
                    r0 = r7
                L43:
                    int r6 = r5.mStatusBarHeight
                    float r6 = (float) r6
                    int r1 = r5.mNavigationBarFrameHeight
                    float r1 = (float) r1
                    android.view.WindowInsetsAnimationController r5 = r5.mAnimationController
                    int r6 = (int) r6
                    int r1 = (int) r1
                    android.graphics.Insets r6 = android.graphics.Insets.of(r7, r6, r0, r1)
                    r7 = 1065353216(0x3f800000, float:1.0)
                    r5.setInsetsAndAlpha(r6, r7, r7)
                L56:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.SeslImmersiveScrollBehavior.AnonymousClass5.onReady(android.view.WindowInsetsAnimationController, int):void");
            }
        };
        this.mWindowAnimationCallback = new WindowInsetsAnimation.Callback(1) { // from class: com.google.android.material.appbar.SeslImmersiveScrollBehavior.6
            @Override // android.view.WindowInsetsAnimation.Callback
            public final void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
                super.onEnd(windowInsetsAnimation);
                SeslImmersiveScrollBehavior seslImmersiveScrollBehavior = SeslImmersiveScrollBehavior.this;
                View view = seslImmersiveScrollBehavior.mContentView;
                if (view == null || seslImmersiveScrollBehavior.mAppBarLayout.mIsDetachedState) {
                    return;
                }
                seslImmersiveScrollBehavior.mDecorViewInset = view.getRootWindowInsets();
                SeslImmersiveScrollBehavior seslImmersiveScrollBehavior2 = SeslImmersiveScrollBehavior.this;
                WindowInsets windowInsets = seslImmersiveScrollBehavior2.mDecorViewInset;
                if (windowInsets != null) {
                    seslImmersiveScrollBehavior2.mContentView.dispatchApplyWindowInsets(windowInsets);
                }
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public final WindowInsets onProgress(WindowInsets windowInsets, List list) {
                return windowInsets;
            }
        };
        this.mContext = context;
        updateSystemBarsHeight();
        updateAppBarHeightProportion();
    }

    public static boolean isHideCameraCutout(WindowInsets windowInsets) {
        return windowInsets.getDisplayCutout() == null && windowInsets.getInsets(WindowInsets.Type.statusBars()).top == 0;
    }

    public final boolean canImmersiveScroll() {
        boolean z;
        AppBarLayout appBarLayout;
        if (this.mAppBarLayout != null && !isDexEnabled()) {
            if (this.mAppBarLayout.isMouse) {
                prepareImmersiveScroll(false, false);
                return false;
            }
            Context context = this.mContext;
            if (context == null ? false : ((AccessibilityManager) context.getSystemService("accessibility")).isTouchExplorationEnabled()) {
                Log.i("SeslImmersiveScrollBehavior", "Disable ImmersiveScroll due to accessibility enabled");
                updateOrientationState();
                prepareImmersiveScroll(false, true);
                return false;
            }
            if (this.mAppBarLayout.mIsActivatedImmersiveScroll) {
                prepareImmersiveScroll(true, false);
                try {
                    z = this.mContext.getApplicationContext().getResources().getBoolean(Resources.getSystem().getIdentifier("config_navBarCanMove", "bool", "android"));
                } catch (Exception e) {
                    KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("ERROR, e : "), "SeslImmersiveScrollBehavior");
                    z = true;
                }
                boolean updateOrientationState = z ? updateOrientationState() : true;
                Context context2 = this.mContext;
                if (context2 != null) {
                    Activity activity = SeslContextUtils.getActivity(context2);
                    if (activity == null && (appBarLayout = this.mAppBarLayout) != null) {
                        this.mContext = appBarLayout.getContext();
                        activity = SeslContextUtils.getActivity(this.mAppBarLayout.getContext());
                    }
                    if (activity != null) {
                        boolean isInMultiWindowMode = activity.isInMultiWindowMode();
                        if (this.mIsMultiWindow != isInMultiWindowMode) {
                            forceRestoreWindowInset(true);
                            cancelWindowInsetsAnimationController();
                        }
                        this.mIsMultiWindow = isInMultiWindowMode;
                        if (isInMultiWindowMode) {
                            return false;
                        }
                    }
                }
                return updateOrientationState;
            }
            prepareImmersiveScroll(false, false);
        }
        return false;
    }

    public final void cancelWindowInsetsAnimationController() {
        View view = this.mDecorView;
        if (view != null) {
            WindowInsets rootWindowInsets = view.getRootWindowInsets();
            this.mDecorViewInset = rootWindowInsets;
            if (rootWindowInsets != null) {
                this.mShownAtDown = rootWindowInsets.isVisible(WindowInsets.Type.statusBars()) || this.mDecorViewInset.isVisible(WindowInsets.Type.navigationBars());
            }
        }
        WindowInsetsAnimationController windowInsetsAnimationController = this.mAnimationController;
        if (windowInsetsAnimationController != null) {
            windowInsetsAnimationController.finish(this.mShownAtDown);
        }
        CancellationSignal cancellationSignal = this.mCancellationSignal;
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
        }
        this.mAnimationController = null;
        this.mCancellationSignal = null;
        this.mShownAtDown = false;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        boolean z = motionEvent.getToolType(0) == 3;
        if (this.mToolIsMouse != z) {
            this.mToolIsMouse = z;
            AppBarLayout appBarLayout = this.mAppBarLayout;
            if (appBarLayout != null) {
                appBarLayout.isMouse = z;
                dispatchImmersiveScrollEnabled();
            }
        }
        return false;
    }

    public final boolean dispatchImmersiveScrollEnabled() {
        AppBarLayout appBarLayout = this.mAppBarLayout;
        if (appBarLayout == null || appBarLayout.mIsDetachedState) {
            return false;
        }
        boolean canImmersiveScroll = canImmersiveScroll();
        setupDecorsFitSystemWindowState(canImmersiveScroll);
        updateAppBarHeightProportion();
        updateSystemBarsHeight();
        return canImmersiveScroll;
    }

    public final void findSystemBarsBackground() {
        View view = this.mDecorView;
        if (view == null || this.mContext == null) {
            return;
        }
        this.mDecorViewInset = view.getRootWindowInsets();
        this.mDecorView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.google.android.material.appbar.SeslImmersiveScrollBehavior.4
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                SeslImmersiveScrollBehavior.this.mDecorView.getViewTreeObserver().removeOnPreDrawListener(this);
                SeslImmersiveScrollBehavior seslImmersiveScrollBehavior = SeslImmersiveScrollBehavior.this;
                seslImmersiveScrollBehavior.mStatusBarBg = seslImmersiveScrollBehavior.mDecorView.findViewById(R.id.statusBarBackground);
                SeslImmersiveScrollBehavior seslImmersiveScrollBehavior2 = SeslImmersiveScrollBehavior.this;
                seslImmersiveScrollBehavior2.mNavigationBarBg = seslImmersiveScrollBehavior2.mDecorView.findViewById(R.id.navigationBarBackground);
                return false;
            }
        });
        updateSystemBarsHeight();
    }

    public final void forceRestoreWindowInset(boolean z) {
        if (this.mWindowInsetsController != null) {
            WindowInsets rootWindowInsets = this.mDecorView.getRootWindowInsets();
            this.mDecorViewInset = rootWindowInsets;
            if (rootWindowInsets != null) {
                boolean isVisible = rootWindowInsets.isVisible(WindowInsets.Type.statusBars());
                boolean isVisible2 = this.mDecorViewInset.isVisible(WindowInsets.Type.navigationBars());
                if (!isVisible || !isVisible2 || isAppBarHide() || z) {
                    try {
                        this.mWindowInsetsController.show(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                    } catch (IllegalStateException unused) {
                        Log.w("SeslImmersiveScrollBehavior", "forceRestoreWindowInset: mWindowInsetsController.show failed!");
                    }
                }
            }
        }
    }

    public final boolean isAppBarHide() {
        AppBarLayout appBarLayout = this.mAppBarLayout;
        if (appBarLayout != null) {
            return ((float) (this.mAppBarLayout.getPaddingBottom() + appBarLayout.getBottom())) < this.mAppBarLayout.seslGetCollapsedHeight();
        }
        return false;
    }

    public final boolean isDexEnabled() {
        Context context = this.mContext;
        if (context == null) {
            return false;
        }
        Configuration configuration = context.getResources().getConfiguration();
        Class cls = SeslConfigurationReflector.mClass;
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(cls, "hidden_semDesktopModeEnabled", new Class[0]);
        Object invoke = declaredMethod != null ? SeslBaseReflector.invoke(configuration, declaredMethod, new Object[0]) : null;
        int intValue = invoke instanceof Integer ? ((Integer) invoke).intValue() : -1;
        Method declaredMethod2 = SeslBaseReflector.getDeclaredMethod(cls, "hidden_SEM_DESKTOP_MODE_ENABLED", new Class[0]);
        Object invoke2 = declaredMethod2 != null ? SeslBaseReflector.invoke(null, declaredMethod2, new Object[0]) : null;
        return intValue == (invoke2 instanceof Integer ? ((Integer) invoke2).intValue() : 0);
    }

    public final boolean isNavigationBarBottomPosition() {
        if (this.mDecorViewInset == null) {
            if (this.mDecorView == null) {
                this.mDecorView = this.mAppBarLayout.getRootView();
            }
            this.mDecorViewInset = this.mDecorView.getRootWindowInsets();
        }
        WindowInsets windowInsets = this.mDecorViewInset;
        return windowInsets == null || windowInsets.getInsets(WindowInsets.Type.navigationBars()).bottom != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v8, types: [android.view.WindowInsetsController$OnControllableInsetsChangedListener, com.google.android.material.appbar.SeslImmersiveScrollBehavior$3] */
    @Override // com.google.android.material.appbar.ViewOffsetBehavior
    public final void layoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        AppBarLayout appBarLayout = (AppBarLayout) view;
        coordinatorLayout.onLayoutChild(appBarLayout, i);
        if (this.mWindowInsetsController != null && this.mOnInsetsChangedListener == null) {
            ?? r5 = new WindowInsetsController.OnControllableInsetsChangedListener() { // from class: com.google.android.material.appbar.SeslImmersiveScrollBehavior.3
                @Override // android.view.WindowInsetsController.OnControllableInsetsChangedListener
                public final void onControllableInsetsChanged(WindowInsetsController windowInsetsController, int i2) {
                    SeslImmersiveScrollBehavior seslImmersiveScrollBehavior = SeslImmersiveScrollBehavior.this;
                    AppBarLayout appBarLayout2 = seslImmersiveScrollBehavior.mAppBarLayout;
                    boolean z = false;
                    if (appBarLayout2 != null && appBarLayout2.mCurrentOrientation == 2) {
                        z = true;
                    }
                    if (z && !seslImmersiveScrollBehavior.isNavigationBarBottomPosition() && !SeslImmersiveScrollBehavior.this.mCalledHideShowOnLayoutChild) {
                        windowInsetsController.hide(WindowInsets.Type.navigationBars());
                        windowInsetsController.show(WindowInsets.Type.navigationBars());
                        windowInsetsController.setSystemBarsBehavior(2);
                        SeslImmersiveScrollBehavior.this.mCalledHideShowOnLayoutChild = true;
                    }
                    SeslImmersiveScrollBehavior seslImmersiveScrollBehavior2 = SeslImmersiveScrollBehavior.this;
                    if (seslImmersiveScrollBehavior2.mIsSetAutoRestore && i2 == 8) {
                        seslImmersiveScrollBehavior2.mDecorViewInset = seslImmersiveScrollBehavior2.mDecorView.getRootWindowInsets();
                        WindowInsets windowInsets = SeslImmersiveScrollBehavior.this.mDecorViewInset;
                        if (windowInsets != null && windowInsets.isVisible(WindowInsets.Type.statusBars()) && SeslImmersiveScrollBehavior.this.isAppBarHide()) {
                            SeslImmersiveScrollBehavior.this.restoreTopAndBottom(true);
                        }
                    }
                }
            };
            this.mOnInsetsChangedListener = r5;
            this.mWindowInsetsController.addOnControllableInsetsChangedListener(r5);
        }
        AppBarLayout appBarLayout2 = this.mAppBarLayout;
        if (appBarLayout2 == null || appBarLayout != appBarLayout2) {
            Log.d("SeslImmersiveScrollBehavior", "initImmViews mNeedInit=false");
            int i2 = 0;
            this.mCanImmersiveScroll = false;
            this.mAppBarLayout = appBarLayout;
            this.mCoordinatorLayout = coordinatorLayout;
            appBarLayout.addOnOffsetChangedListener(this.mOffsetChangedListener);
            this.mAppBarLayout.getClass();
            if (!isDexEnabled()) {
                AppBarLayout appBarLayout3 = this.mAppBarLayout;
                appBarLayout3.mIsActivatedImmersiveScroll = true;
                SeslImmersiveScrollBehavior immBehavior = appBarLayout3.getImmBehavior();
                if (immBehavior != null && immBehavior.isAppBarHide()) {
                    immBehavior.restoreTopAndBottom(false);
                }
            }
            View rootView = this.mAppBarLayout.getRootView();
            this.mDecorView = rootView;
            View findViewById = rootView.findViewById(R.id.content);
            this.mContentView = findViewById;
            findViewById.setWindowInsetsAnimationCallback(this.mWindowAnimationCallback);
            findSystemBarsBackground();
            dispatchImmersiveScrollEnabled();
            while (true) {
                if (i2 >= appBarLayout.getChildCount()) {
                    break;
                }
                View childAt = appBarLayout.getChildAt(i2);
                if (this.mCollapsingToolbarLayout != null) {
                    break;
                }
                if (childAt instanceof CollapsingToolbarLayout) {
                    this.mCollapsingToolbarLayout = (CollapsingToolbarLayout) childAt;
                    break;
                }
                i2++;
            }
            View findViewById2 = coordinatorLayout.findViewById(com.android.systemui.R.id.bottom_bar_overlay);
            if (this.mBottomArea == null || findViewById2 != null) {
                this.mBottomArea = findViewById2;
            }
        }
    }

    @Override // com.google.android.material.appbar.HeaderBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        AppBarLayout appBarLayout = (AppBarLayout) view;
        int toolType = motionEvent.getToolType(0);
        if (toolType == 0) {
            return super.onInterceptTouchEvent(coordinatorLayout, appBarLayout, motionEvent);
        }
        boolean z = toolType == 3;
        if (this.mToolIsMouse != z) {
            this.mToolIsMouse = z;
            appBarLayout.isMouse = z;
        }
        return super.onInterceptTouchEvent(coordinatorLayout, appBarLayout, motionEvent);
    }

    public final void prepareImmersiveScroll(boolean z, boolean z2) {
        if (this.mCanImmersiveScroll != z) {
            this.mCanImmersiveScroll = z;
            forceRestoreWindowInset(z2);
            setupDecorsFitSystemWindowState(z);
            AppBarLayout appBarLayout = this.mAppBarLayout;
            boolean z3 = appBarLayout.mIsCanScroll;
            if (z == z3 || z3 == z) {
                return;
            }
            appBarLayout.mIsCanScroll = z;
            appBarLayout.invalidateScrollRanges();
            appBarLayout.requestLayout();
        }
    }

    public final void restoreTopAndBottom(boolean z) {
        AppBarLayout appBarLayout;
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m(" Restore top and bottom areas [Animate] ", "SeslImmersiveScrollBehavior", z);
        this.mNeedRestoreAnim = z;
        AppBarLayout appBarLayout2 = this.mAppBarLayout;
        AnonymousClass1 anonymousClass1 = this.mAnimationHandler;
        if (appBarLayout2 != null && isAppBarHide()) {
            if (anonymousClass1.hasMessages(100)) {
                anonymousClass1.removeMessages(100);
            }
            anonymousClass1.sendEmptyMessageDelayed(100, 100L);
        }
        if (this.mBottomArea == null || this.mNavigationBarBg == null || anonymousClass1.hasMessages(100) || (appBarLayout = this.mAppBarLayout) == null || appBarLayout.mIsActivatedImmersiveScroll) {
            return;
        }
        this.mBottomArea.setTranslationY(0.0f);
    }

    public final void setupDecorsFitSystemWindowState(boolean z) {
        AppBarLayout appBarLayout;
        AppBarLayout appBarLayout2;
        View view;
        int i;
        AppBarLayout appBarLayout3;
        if (this.mDecorView == null || (appBarLayout = this.mAppBarLayout) == null) {
            return;
        }
        if (this.mContext == null) {
            Context context = appBarLayout.getContext();
            this.mContext = context;
            if (context == null) {
                return;
            }
        }
        Activity activity = SeslContextUtils.getActivity(this.mContext);
        if (activity == null && (appBarLayout3 = this.mAppBarLayout) != null) {
            this.mContext = appBarLayout3.getContext();
            activity = SeslContextUtils.getActivity(this.mAppBarLayout.getContext());
        }
        if (activity != null) {
            Window window = activity.getWindow();
            if (z) {
                WindowInsets windowInsets = this.mDecorViewInset;
                if (windowInsets == null || !isHideCameraCutout(windowInsets)) {
                    this.mAppBarLayout.mImmersiveTopInset = this.mStatusBarHeight;
                } else {
                    this.mAppBarLayout.mImmersiveTopInset = 0;
                }
                window.setDecorFitsSystemWindows(false);
                window.getDecorView().setFitsSystemWindows(false);
                WindowInsets windowInsets2 = this.mDecorViewInset;
                if (windowInsets2 == null || (i = windowInsets2.getInsets(WindowInsets.Type.statusBars()).top) == 0 || i == this.mStatusBarHeight) {
                    return;
                }
                this.mStatusBarHeight = i;
                this.mAppBarLayout.mImmersiveTopInset = i;
                return;
            }
            this.mAppBarLayout.mImmersiveTopInset = 0;
            window.setDecorFitsSystemWindows(true);
            window.getDecorView().setFitsSystemWindows(true);
            if (isNavigationBarBottomPosition() || (appBarLayout2 = this.mAppBarLayout) == null || appBarLayout2.mCurrentOrientation != 2) {
                return;
            }
            WindowInsetsController windowInsetsController = this.mWindowInsetsController;
            if (windowInsetsController == null && (view = this.mDecorView) != null && this.mAnimationController == null && windowInsetsController == null) {
                this.mWindowInsetsController = view.getWindowInsetsController();
            }
            WindowInsets rootWindowInsets = this.mDecorView.getRootWindowInsets();
            this.mDecorViewInset = rootWindowInsets;
            if (this.mWindowInsetsController == null || rootWindowInsets == null || rootWindowInsets.getInsets(WindowInsets.Type.statusBars()).top == 0) {
                return;
            }
            try {
                this.mWindowInsetsController.hide(WindowInsets.Type.statusBars());
            } catch (IllegalStateException unused) {
                Log.w("SeslImmersiveScrollBehavior", "setupDecorsFitSystemWindowState: mWindowInsetsController.hide failed!");
            }
        }
    }

    public final void updateAppBarHeightProportion() {
        AppBarLayout appBarLayout = this.mAppBarLayout;
        if (appBarLayout == null) {
            return;
        }
        if (this.mContext == null) {
            Context context = appBarLayout.getContext();
            this.mContext = context;
            if (context == null) {
                return;
            }
        }
        Resources resources = this.mContext.getResources();
        SeslAppBarHelper.Companion companion = SeslAppBarHelper.Companion;
        Context context2 = this.mContext;
        companion.getClass();
        float appBarProPortion = SeslAppBarHelper.Companion.getAppBarProPortion(context2);
        float f = 0.0f;
        if (appBarProPortion != 0.0f) {
            f = (this.mStatusBarHeight / resources.getDisplayMetrics().heightPixels) + appBarProPortion;
        }
        if (this.mCanImmersiveScroll) {
            AppBarLayout appBarLayout2 = this.mAppBarLayout;
            if (appBarLayout2.mUseCustomHeight || appBarLayout2.mHeightProportion == f) {
                return;
            }
            appBarLayout2.mHeightProportion = f;
            appBarLayout2.updateInternalHeight();
            return;
        }
        AppBarLayout appBarLayout3 = this.mAppBarLayout;
        if (appBarLayout3.mUseCustomHeight || appBarLayout3.mHeightProportion == appBarProPortion) {
            return;
        }
        appBarLayout3.mHeightProportion = appBarProPortion;
        appBarLayout3.updateInternalHeight();
    }

    public final boolean updateOrientationState() {
        AppBarLayout appBarLayout = this.mAppBarLayout;
        if (appBarLayout == null) {
            return false;
        }
        int i = appBarLayout.mCurrentOrientation;
        if (this.mPrevOrientation != i) {
            this.mPrevOrientation = i;
            forceRestoreWindowInset(true);
            this.mCalledHideShowOnLayoutChild = false;
        }
        if (i == 1) {
            return true;
        }
        if (i != 2) {
            Log.e("SeslImmersiveScrollBehavior", "ERROR, e : AppbarLayout Configuration is wrong");
        }
        return false;
    }

    public final void updateSystemBarsHeight() {
        Context context = this.mContext;
        if (context == null) {
            return;
        }
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            this.mStatusBarHeight = resources.getDimensionPixelSize(identifier);
        }
        int identifier2 = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (identifier2 > 0) {
            this.mNavigationBarBgHeight = resources.getDimensionPixelSize(identifier2);
        }
        View view = this.mDecorView;
        if (view != null) {
            WindowInsets rootWindowInsets = view.getRootWindowInsets();
            this.mDecorViewInset = rootWindowInsets;
            if (rootWindowInsets != null) {
                this.mStatusBarHeight = rootWindowInsets.getInsets(WindowInsets.Type.statusBars()).top;
                int i = this.mDecorViewInset.getInsets(WindowInsets.Type.navigationBars()).bottom;
                this.mNavigationBarFrameHeight = i;
                this.mNavigationBarBgHeight = i;
                if (this.mNavigationBarBg == null) {
                    this.mNavigationBarBgHeight = 0;
                }
            }
        }
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onMeasureChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2, int i3) {
        dispatchImmersiveScrollEnabled();
        return super.onMeasureChild(coordinatorLayout, appBarLayout, i, i2, i3);
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int[] iArr, int i3) {
        this.mTargetView = view;
        if (this.mCancellationSignal == null) {
            super.onNestedPreScroll(coordinatorLayout, appBarLayout, view, i, i2, iArr, i3);
        } else {
            iArr[0] = i;
            iArr[1] = i2;
        }
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        this.mTargetView = view;
        super.onNestedScroll(coordinatorLayout, appBarLayout, view, i, i2, i3, i4, i5, iArr);
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, View view2, int i, int i2) {
        WindowInsetsAnimationController windowInsetsAnimationController;
        this.mTargetView = view2;
        if (dispatchImmersiveScrollEnabled() && (windowInsetsAnimationController = this.mAnimationController) == null) {
            View view3 = this.mDecorView;
            if (view3 != null && windowInsetsAnimationController == null && this.mWindowInsetsController == null) {
                this.mWindowInsetsController = view3.getWindowInsetsController();
            }
            if (this.mCancellationSignal == null) {
                this.mCancellationSignal = new CancellationSignal();
            }
            int statusBars = WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars();
            if (!isHideCameraCutout(this.mDecorViewInset)) {
                try {
                    this.mWindowInsetsController.hide(statusBars);
                } catch (IllegalStateException unused) {
                    Log.w("SeslImmersiveScrollBehavior", "startAnimationControlRequest: mWindowInsetsController.hide failed!");
                }
            }
            this.mWindowInsetsController.setSystemBarsBehavior(2);
            this.mWindowInsetsController.controlWindowInsetsAnimation(statusBars, -1L, null, this.mCancellationSignal, this.mWindowInsetsAnimationControlListener);
        }
        return super.onStartNestedScroll(coordinatorLayout, appBarLayout, view, view2, i, i2);
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i) {
        this.mTargetView = view;
        super.onStopNestedScroll(coordinatorLayout, appBarLayout, view, i);
    }
}
