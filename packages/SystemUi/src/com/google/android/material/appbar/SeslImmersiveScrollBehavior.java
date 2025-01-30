package com.google.android.material.appbar;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
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
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.content.res.SeslConfigurationReflector;
import com.android.keyguard.AbstractC0790xf8f53ce8;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.internal.SeslContextUtils;
import com.google.android.material.internal.SeslDisplayUtils;
import java.lang.reflect.Method;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SeslImmersiveScrollBehavior extends AppBarLayout.Behavior {
    public boolean isRoundedCornerHide;
    public WindowInsetsAnimationController mAnimationController;
    public final HandlerC42091 mAnimationHandler;
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
    public int mNavigationBarHeight;
    public boolean mNeedRestoreAnim;
    public ValueAnimator mOffsetAnimator;
    public final C42102 mOffsetChangedListener;
    public WindowInsetsControllerOnControllableInsetsChangedListenerC42113 mOnInsetsChangedListener;
    public int mPrevOffset;
    public int mPrevOrientation;
    public boolean mShownAtDown;
    public View mStatusBarBg;
    public int mStatusBarHeight;
    public View mTargetView;
    public boolean mToolIsMouse;
    public final C42146 mWindowAnimationCallback;
    public final WindowInsetsAnimationControlListenerC42135 mWindowInsetsAnimationControlListener;
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
                        ValueAnimator valueAnimator3 = seslImmersiveScrollBehavior.mOffsetAnimator;
                        int[] iArr2 = new int[2];
                        iArr2[0] = seslImmersiveScrollBehavior.mNeedRestoreAnim ? -seslImmersiveScrollBehavior.mAppBarLayout.getHeight() : (int) seslGetCollapsedHeight;
                        iArr2[1] = (int) seslGetCollapsedHeight;
                        valueAnimator3.setIntValues(iArr2);
                        seslImmersiveScrollBehavior.mOffsetAnimator.start();
                    }
                }
            }
        };
        this.mOffsetChangedListener = new AppBarLayout.OnOffsetChangedListener() { // from class: com.google.android.material.appbar.SeslImmersiveScrollBehavior.2
            /* JADX WARN: Code restructure failed: missing block: B:86:0x0191, code lost:
            
                if (r5 == 1) goto L99;
             */
            @Override // com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                View view;
                int i2;
                SeslImmersiveScrollBehavior seslImmersiveScrollBehavior = SeslImmersiveScrollBehavior.this;
                AppBarLayout appBarLayout2 = seslImmersiveScrollBehavior.mAppBarLayout;
                if (appBarLayout2 != null && appBarLayout2.mIsDetachedState) {
                    Log.e("SeslImmersiveScrollBehavior", "AppBarLayout was DetachedState. Skip onOffsetChanged");
                    return;
                }
                if (!seslImmersiveScrollBehavior.mCanImmersiveScroll) {
                    View view2 = seslImmersiveScrollBehavior.mStatusBarBg;
                    if (view2 != null) {
                        view2.setTranslationY(0.0f);
                    }
                    View view3 = seslImmersiveScrollBehavior.mNavigationBarBg;
                    if (view3 != null) {
                        view3.setTranslationY(0.0f);
                    }
                    View view4 = seslImmersiveScrollBehavior.mBottomArea;
                    if (view4 != null) {
                        view4.setTranslationY(0.0f);
                    }
                    AppBarLayout appBarLayout3 = seslImmersiveScrollBehavior.mAppBarLayout;
                    if (appBarLayout3 == null || appBarLayout3.willNotDraw()) {
                        return;
                    }
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api16Impl.postInvalidateOnAnimation(appBarLayout3);
                    return;
                }
                View view5 = seslImmersiveScrollBehavior.mBottomArea;
                int i3 = 0;
                int height = view5 != null ? view5.getHeight() : 0;
                float seslGetCollapsedHeight = appBarLayout.seslGetCollapsedHeight();
                float f = (seslImmersiveScrollBehavior.mNavigationBarHeight + height) / (seslGetCollapsedHeight == 0.0f ? 1.0f : seslGetCollapsedHeight);
                float totalScrollRange = ((appBarLayout.getTotalScrollRange() - 0) + i) - seslGetCollapsedHeight;
                float f2 = seslImmersiveScrollBehavior.mStatusBarHeight;
                float f3 = f2 + totalScrollRange;
                float f4 = (f + 1.0f) * totalScrollRange;
                float min = Math.min(f2, f3);
                float f5 = seslImmersiveScrollBehavior.mNavigationBarHeight;
                float max = Math.max(Math.min(f5, f5 + f4), 0.0f);
                int i4 = seslImmersiveScrollBehavior.mNavigationBarHeight;
                float f6 = i4 - max;
                if (i4 == 0) {
                    i4 = 1;
                }
                float f7 = f6 / i4;
                if (appBarLayout.getBottom() > seslGetCollapsedHeight) {
                    AppBarLayout appBarLayout4 = seslImmersiveScrollBehavior.mAppBarLayout;
                    if (appBarLayout4 != null) {
                        appBarLayout4.getTotalScrollRange();
                    }
                    if (seslImmersiveScrollBehavior.mIsMultiWindow && (view = seslImmersiveScrollBehavior.mBottomArea) != null) {
                        view.setTranslationY(0.0f);
                        seslImmersiveScrollBehavior.mBottomArea.getHeight();
                    }
                    if (!seslImmersiveScrollBehavior.mIsMultiWindow && seslImmersiveScrollBehavior.mBottomArea != null && seslImmersiveScrollBehavior.mDecorViewInset != null) {
                        if (seslImmersiveScrollBehavior.isNavigationBarBottomPosition()) {
                            seslImmersiveScrollBehavior.mBottomArea.setTranslationY(-seslImmersiveScrollBehavior.mNavigationBarHeight);
                            View view6 = seslImmersiveScrollBehavior.mNavigationBarBg;
                            if (view6 != null && view6.getTranslationY() != 0.0f) {
                                seslImmersiveScrollBehavior.mNavigationBarBg.setTranslationY(0.0f);
                            }
                        } else {
                            View view7 = seslImmersiveScrollBehavior.mNavigationBarBg;
                            if (view7 != null && view7.getTranslationY() != 0.0f) {
                                seslImmersiveScrollBehavior.mBottomArea.setTranslationY(0.0f);
                            }
                        }
                        seslImmersiveScrollBehavior.mBottomArea.getHeight();
                    }
                } else if (seslImmersiveScrollBehavior.canImmersiveScroll()) {
                    View view8 = seslImmersiveScrollBehavior.mBottomArea;
                    if (view8 == null || view8.getVisibility() == 8 || height == 0) {
                        Math.max(max, 0.0f);
                        appBarLayout.getTotalScrollRange();
                    } else {
                        float min2 = Math.min(height + f4, max);
                        seslImmersiveScrollBehavior.mBottomArea.setTranslationY(-Math.round(min2));
                        if (seslImmersiveScrollBehavior.mBottomArea.getVisibility() != 0) {
                            height = 0;
                        }
                        Math.max(height + min2, 0.0f);
                        appBarLayout.getTotalScrollRange();
                    }
                    if (seslImmersiveScrollBehavior.mNavigationBarBg != null) {
                        if (SeslImmersiveScrollBehavior.isHideCameraCutout(seslImmersiveScrollBehavior.mDecorViewInset)) {
                            seslImmersiveScrollBehavior.mNavigationBarBg.setTranslationY(0.0f);
                        } else {
                            seslImmersiveScrollBehavior.mNavigationBarBg.setTranslationY(-Math.min(0, Math.round(f4)));
                        }
                    } else if (seslImmersiveScrollBehavior.mNavigationBarHeight != 0) {
                        seslImmersiveScrollBehavior.findSystemBarsBackground();
                        View view9 = seslImmersiveScrollBehavior.mNavigationBarBg;
                        if (view9 != null) {
                            view9.setTranslationY(0.0f);
                        }
                    }
                    View view10 = seslImmersiveScrollBehavior.mStatusBarBg;
                    if (view10 != null) {
                        view10.setTranslationY(Math.min(0.0f, totalScrollRange));
                    }
                    if (seslImmersiveScrollBehavior.mCurOffset != f3) {
                        seslImmersiveScrollBehavior.mCurOffset = f3;
                        WindowInsetsAnimationController windowInsetsAnimationController = seslImmersiveScrollBehavior.mAnimationController;
                        if (windowInsetsAnimationController != null) {
                            if (windowInsetsAnimationController.isFinished()) {
                                Log.e("SeslImmersiveScrollBehavior", "AnimationController is already finished by App side");
                            } else {
                                int i5 = (int) max;
                                WindowInsetsAnimationController windowInsetsAnimationController2 = seslImmersiveScrollBehavior.mAnimationController;
                                if (windowInsetsAnimationController2 != null && seslImmersiveScrollBehavior.mDecorView != null) {
                                    boolean z = i5 != windowInsetsAnimationController2.getShownStateInsets().bottom;
                                    if (z != seslImmersiveScrollBehavior.isRoundedCornerHide) {
                                        seslImmersiveScrollBehavior.isRoundedCornerHide = z;
                                        View view11 = seslImmersiveScrollBehavior.mDecorView;
                                        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(view11.getClass(), "hidden_semSetForceHideRoundedCorner", Boolean.TYPE);
                                        if (declaredMethod != null) {
                                            SeslBaseReflector.invoke(view11, declaredMethod, Boolean.valueOf(z));
                                        }
                                    }
                                }
                                if (SeslDisplayUtils.isPinEdgeEnabled(seslImmersiveScrollBehavior.mContext)) {
                                    Insets insets = seslImmersiveScrollBehavior.mDecorViewInset.getInsets(WindowInsets.Type.navigationBars());
                                    i2 = SeslDisplayUtils.getPinnedEdgeWidth(seslImmersiveScrollBehavior.mContext);
                                    int i6 = Settings.System.getInt(seslImmersiveScrollBehavior.mContext.getContentResolver(), "active_edge_area", 1);
                                    if (i2 == insets.left && i6 == 0) {
                                        i3 = i2;
                                        i2 = 0;
                                    } else if (i2 == insets.right) {
                                    }
                                    seslImmersiveScrollBehavior.mAnimationController.setInsetsAndAlpha(Insets.of(i3, (int) min, i2, i5), 1.0f, f7);
                                }
                                i2 = 0;
                                seslImmersiveScrollBehavior.mAnimationController.setInsetsAndAlpha(Insets.of(i3, (int) min, i2, i5), 1.0f, f7);
                            }
                        }
                    }
                } else {
                    View view12 = seslImmersiveScrollBehavior.mStatusBarBg;
                    if (view12 != null) {
                        view12.setTranslationY(0.0f);
                    }
                    View view13 = seslImmersiveScrollBehavior.mNavigationBarBg;
                    if (view13 != null) {
                        view13.setTranslationY(0.0f);
                    }
                    AppBarLayout appBarLayout5 = seslImmersiveScrollBehavior.mAppBarLayout;
                    if (appBarLayout5 != null) {
                        appBarLayout5.getTotalScrollRange();
                        if (seslImmersiveScrollBehavior.mBottomArea != null) {
                            float f8 = height;
                            if (seslGetCollapsedHeight == 0.0f) {
                                seslGetCollapsedHeight = 1.0f;
                            }
                            float bottom = f8 - (seslImmersiveScrollBehavior.mAppBarLayout.getBottom() * (f8 / seslGetCollapsedHeight));
                            seslImmersiveScrollBehavior.mBottomArea.setTranslationY(Math.max(bottom, 0.0f));
                            seslImmersiveScrollBehavior.mBottomArea.getHeight();
                            Math.max(bottom, 0.0f);
                        }
                    }
                    AppBarLayout appBarLayout6 = seslImmersiveScrollBehavior.mAppBarLayout;
                    if (appBarLayout6 != null) {
                        WindowInsetsAnimationController windowInsetsAnimationController3 = seslImmersiveScrollBehavior.mAnimationController;
                        if (seslImmersiveScrollBehavior.mContentView == null) {
                            View rootView = appBarLayout6.getRootView();
                            seslImmersiveScrollBehavior.mDecorView = rootView;
                            seslImmersiveScrollBehavior.mContentView = rootView.findViewById(R.id.content);
                        }
                        if (windowInsetsAnimationController3 == null) {
                            CancellationSignal cancellationSignal = seslImmersiveScrollBehavior.mCancellationSignal;
                            if (cancellationSignal != null) {
                                cancellationSignal.cancel();
                            }
                        } else {
                            int i7 = windowInsetsAnimationController3.getCurrentInsets().bottom;
                            int i8 = windowInsetsAnimationController3.getShownStateInsets().bottom;
                            int i9 = windowInsetsAnimationController3.getHiddenStateInsets().bottom;
                            if (i7 == i8) {
                                windowInsetsAnimationController3.finish(true);
                            } else if (i7 == i9) {
                                windowInsetsAnimationController3.finish(false);
                            }
                        }
                    }
                }
                AppBarLayout appBarLayout7 = seslImmersiveScrollBehavior.mAppBarLayout;
                if (appBarLayout7 == null || appBarLayout7.willNotDraw()) {
                    return;
                }
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.postInvalidateOnAnimation(appBarLayout7);
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
            */
            public final void onReady(WindowInsetsAnimationController windowInsetsAnimationController, int i) {
                int i2;
                SeslImmersiveScrollBehavior seslImmersiveScrollBehavior = SeslImmersiveScrollBehavior.this;
                if (seslImmersiveScrollBehavior.mDecorView != null) {
                    seslImmersiveScrollBehavior.mCancellationSignal = null;
                    seslImmersiveScrollBehavior.mAnimationController = windowInsetsAnimationController;
                    int i3 = 0;
                    if (SeslDisplayUtils.isPinEdgeEnabled(seslImmersiveScrollBehavior.mContext)) {
                        Insets insets = seslImmersiveScrollBehavior.mDecorViewInset.getInsets(WindowInsets.Type.navigationBars());
                        i2 = SeslDisplayUtils.getPinnedEdgeWidth(seslImmersiveScrollBehavior.mContext);
                        int i4 = Settings.System.getInt(seslImmersiveScrollBehavior.mContext.getContentResolver(), "active_edge_area", 1);
                        if (i2 == insets.left && i4 == 0) {
                            i2 = 0;
                            i3 = i2;
                        } else if (i2 == insets.right) {
                        }
                        seslImmersiveScrollBehavior.mAnimationController.setInsetsAndAlpha(Insets.of(i3, seslImmersiveScrollBehavior.mStatusBarHeight, i2, seslImmersiveScrollBehavior.mNavigationBarHeight), 1.0f, 1.0f);
                    }
                    i2 = 0;
                    seslImmersiveScrollBehavior.mAnimationController.setInsetsAndAlpha(Insets.of(i3, seslImmersiveScrollBehavior.mStatusBarHeight, i2, seslImmersiveScrollBehavior.mNavigationBarHeight), 1.0f, 1.0f);
                }
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
        return windowInsets.getDisplayCutout() == null && windowInsets.getInsets(WindowInsets.Type.systemBars()).top == 0;
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
            AppBarLayout appBarLayout2 = this.mAppBarLayout;
            if (appBarLayout2.mIsActivatedImmersiveScroll) {
                prepareImmersiveScroll(true, false);
                try {
                    z = this.mContext.getApplicationContext().getResources().getBoolean(Resources.getSystem().getIdentifier("config_navBarCanMove", "bool", "android"));
                } catch (Exception e) {
                    AbstractC0790xf8f53ce8.m80m(e, new StringBuilder("ERROR, e : "), "SeslImmersiveScrollBehavior");
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
            if (appBarLayout2 != null && appBarLayout2.mIsActivatedByUser) {
                cancelWindowInsetsAnimationController();
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
    public final void dispatchGenericMotionEvent(MotionEvent motionEvent) {
        boolean z = motionEvent.getToolType(0) == 3;
        if (this.mToolIsMouse != z) {
            this.mToolIsMouse = z;
            AppBarLayout appBarLayout = this.mAppBarLayout;
            if (appBarLayout != null) {
                appBarLayout.isMouse = z;
                dispatchImmersiveScrollEnabled();
            }
        }
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
                if (!(rootWindowInsets.isVisible(WindowInsets.Type.statusBars()) && this.mDecorViewInset.isVisible(WindowInsets.Type.navigationBars())) || isAppBarHide() || z) {
                    try {
                        this.mWindowInsetsController.show(WindowInsets.Type.systemBars());
                    } catch (IllegalStateException unused) {
                        Log.w("SeslImmersiveScrollBehavior", "forceRestoreWindowInset: mWindowInsetsController.show failed!");
                    }
                }
            }
        }
    }

    public final boolean isAppBarHide() {
        if (this.mAppBarLayout != null) {
            if (this.mAppBarLayout.getPaddingBottom() + r0.getBottom() < this.mAppBarLayout.seslGetCollapsedHeight()) {
                return true;
            }
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
            if (!this.mAppBarLayout.mIsActivatedByUser && !isDexEnabled()) {
                AppBarLayout appBarLayout3 = this.mAppBarLayout;
                appBarLayout3.mIsActivatedImmersiveScroll = true;
                appBarLayout3.mIsActivatedByUser = false;
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
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m(" Restore top and bottom areas [Animate] ", z, "SeslImmersiveScrollBehavior");
        this.mNeedRestoreAnim = z;
        AppBarLayout appBarLayout2 = this.mAppBarLayout;
        HandlerC42091 handlerC42091 = this.mAnimationHandler;
        if (appBarLayout2 != null && isAppBarHide()) {
            if (handlerC42091.hasMessages(100)) {
                handlerC42091.removeMessages(100);
            }
            handlerC42091.sendEmptyMessageDelayed(100, 100L);
        }
        if (this.mBottomArea == null || this.mNavigationBarBg == null || handlerC42091.hasMessages(100) || (appBarLayout = this.mAppBarLayout) == null || appBarLayout.mIsActivatedImmersiveScroll) {
            return;
        }
        this.mBottomArea.setTranslationY(0.0f);
    }

    public final void setupDecorsFitSystemWindowState(boolean z) {
        AppBarLayout appBarLayout;
        View view;
        int i;
        AppBarLayout appBarLayout2;
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
        if (activity == null && (appBarLayout2 = this.mAppBarLayout) != null) {
            this.mContext = appBarLayout2.getContext();
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
            if (isNavigationBarBottomPosition()) {
                return;
            }
            AppBarLayout appBarLayout3 = this.mAppBarLayout;
            if (appBarLayout3 != null && appBarLayout3.mCurrentOrientation == 2) {
                WindowInsetsController windowInsetsController = this.mWindowInsetsController;
                if (windowInsetsController == null && (view = this.mDecorView) != null && this.mAnimationController == null && windowInsetsController == null) {
                    this.mWindowInsetsController = view.getWindowInsetsController();
                }
                WindowInsets rootWindowInsets = this.mDecorView.getRootWindowInsets();
                this.mDecorViewInset = rootWindowInsets;
                if (this.mWindowInsetsController == null || rootWindowInsets == null) {
                    return;
                }
                if (rootWindowInsets.getInsets(WindowInsets.Type.statusBars()).top != 0) {
                    try {
                        this.mWindowInsetsController.hide(WindowInsets.Type.statusBars());
                    } catch (IllegalStateException unused) {
                        Log.w("SeslImmersiveScrollBehavior", "setupDecorsFitSystemWindowState: mWindowInsetsController.hide failed!");
                    }
                }
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
        ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
        float f = resources.getFloat(com.android.systemui.R.dimen.sesl_appbar_height_proportion);
        float f2 = 0.0f;
        if (f != 0.0f) {
            f2 = (this.mStatusBarHeight / resources.getDisplayMetrics().heightPixels) + f;
        }
        if (this.mCanImmersiveScroll) {
            AppBarLayout appBarLayout2 = this.mAppBarLayout;
            if (appBarLayout2.mUseCustomHeight || appBarLayout2.mHeightProportion == f2) {
                return;
            }
            appBarLayout2.mHeightProportion = f2;
            appBarLayout2.updateInternalHeight();
            return;
        }
        AppBarLayout appBarLayout3 = this.mAppBarLayout;
        if (appBarLayout3.mUseCustomHeight || appBarLayout3.mHeightProportion == f) {
            return;
        }
        appBarLayout3.mHeightProportion = f;
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
        if (i == 2) {
            return false;
        }
        Log.e("SeslImmersiveScrollBehavior", "ERROR, e : AppbarLayout Configuration is wrong");
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
            this.mNavigationBarHeight = resources.getDimensionPixelSize(identifier2);
        }
        View view = this.mDecorView;
        if (view != null) {
            WindowInsets rootWindowInsets = view.getRootWindowInsets();
            this.mDecorViewInset = rootWindowInsets;
            if (rootWindowInsets != null) {
                this.mNavigationBarHeight = rootWindowInsets.getInsets(WindowInsets.Type.navigationBars()).bottom;
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
            int systemBars = WindowInsets.Type.systemBars();
            if (!isHideCameraCutout(this.mDecorViewInset)) {
                try {
                    this.mWindowInsetsController.hide(systemBars);
                } catch (IllegalStateException unused) {
                    Log.w("SeslImmersiveScrollBehavior", "startAnimationControlRequest: mWindowInsetsController.hide failed!");
                }
            }
            this.mWindowInsetsController.setSystemBarsBehavior(2);
            this.mWindowInsetsController.controlWindowInsetsAnimation(systemBars, -1L, null, this.mCancellationSignal, this.mWindowInsetsAnimationControlListener);
        }
        return super.onStartNestedScroll(coordinatorLayout, appBarLayout, view, view2, i, i2);
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i) {
        this.mTargetView = view;
        super.onStopNestedScroll(coordinatorLayout, appBarLayout, view, i);
    }
}
