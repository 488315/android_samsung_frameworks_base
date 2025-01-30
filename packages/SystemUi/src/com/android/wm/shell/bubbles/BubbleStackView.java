package com.android.wm.shell.bubbles;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.view.Choreographer;
import android.view.InputMonitor;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.wm.shell.animation.Interpolators;
import com.android.wm.shell.animation.PhysicsAnimator;
import com.android.wm.shell.bubbles.BadgedImageView;
import com.android.wm.shell.bubbles.Bubble;
import com.android.wm.shell.bubbles.BubbleLogger;
import com.android.wm.shell.bubbles.BubbleStackView;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.bubbles.BubblesNavBarMotionEventHandler;
import com.android.wm.shell.bubbles.animation.AnimatableScaleMatrix;
import com.android.wm.shell.bubbles.animation.ExpandedAnimationController;
import com.android.wm.shell.bubbles.animation.ExpandedAnimationController$$ExternalSyntheticLambda0;
import com.android.wm.shell.bubbles.animation.ExpandedViewAnimationControllerImpl;
import com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout;
import com.android.wm.shell.bubbles.animation.StackAnimationController;
import com.android.wm.shell.common.FloatingContentCoordinator;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.magnetictarget.MagnetizedObject;
import com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget$updateLocationOnScreen$1;
import com.android.wm.shell.taskview.TaskView;
import com.android.wm.shell.taskview.TaskViewTaskController;
import com.android.systemui.R;
import com.android.systemui.keyguard.animator.ActionUpOrCancelHandler$$ExternalSyntheticOutline0;
import com.android.systemui.wmshell.BubblesManager$$ExternalSyntheticLambda1;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BubbleStackView extends FrameLayout implements ViewTreeObserver.OnComputeInternalInsetsListener {
    public static final C38071 DEFAULT_SURFACE_SYNCHRONIZER;
    static final int FLYOUT_HIDE_AFTER = 5000;
    public static final PhysicsAnimator.SpringConfig FLYOUT_IME_ANIMATION_SPRING_CONFIG;
    public BubbleStackView$$ExternalSyntheticLambda1 mAfterFlyoutHidden;
    public final BubbleStackView$$ExternalSyntheticLambda6 mAfterFlyoutTransitionSpring;
    public BubbleStackView$$ExternalSyntheticLambda3 mAnimateInFlyout;
    public final BubbleStackView$$ExternalSyntheticLambda3 mAnimateTemporarilyInvisibleImmediate;
    public final ValueAnimator mAnimatingOutSurfaceAlphaAnimator;
    public final FrameLayout mAnimatingOutSurfaceContainer;
    public final SurfaceView mAnimatingOutSurfaceView;
    public final ViewOnClickListenerC38216 mBubbleClickListener;
    public final PhysicsAnimationLayout mBubbleContainer;
    public final BubbleController mBubbleController;
    public final BubbleData mBubbleData;
    public final int mBubbleElevation;
    public final BubbleOverflow mBubbleOverflow;
    public final BubblesManager$$ExternalSyntheticLambda1 mBubbleSALogger;
    public int mBubbleSize;
    public final int mBubbleStackOff;
    public BubbleViewProvider mBubbleToExpandAfterFlyoutCollapse;
    public final C38227 mBubbleTouchListener;
    public final int mBubbleTouchPadding;
    public BubblesNavBarGestureTracker mBubblesNavBarGestureTracker;
    public final C38238 mContainerSwipeListener;
    public BubbleStackView$$ExternalSyntheticLambda16 mDelayedAnimation;
    public final ValueAnimator mDismissBubbleAnimator;
    public DismissView mDismissView;
    public Bubbles.BubbleExpandListener mExpandListener;
    public final ExpandedAnimationController mExpandedAnimationController;
    public BubbleViewProvider mExpandedBubble;
    public final ValueAnimator mExpandedViewAlphaAnimator;
    public final ExpandedViewAnimationControllerImpl mExpandedViewAnimationController;
    public final FrameLayout mExpandedViewContainer;
    public final AnimatableScaleMatrix mExpandedViewContainerMatrix;
    public final int mExpandedViewPadding;
    public boolean mExpandedViewTemporarilyHidden;
    public BubbleFlyoutView mFlyout;
    public final ViewOnClickListenerC380810 mFlyoutClickListener;
    public final C38183 mFlyoutCollapseProperty;
    public float mFlyoutDragDeltaX;
    public final C380911 mFlyoutTouchListener;
    public final SpringAnimation mFlyoutTransitionSpring;
    public final BubbleStackView$$ExternalSyntheticLambda3 mHideFlyout;
    public final C38194 mIndividualBubbleMagnetListener;
    public boolean mIsBubbleSwitchAnimating;
    public boolean mIsDraggingStack;
    public boolean mIsExpanded;
    public boolean mIsExpansionAnimating;
    public boolean mIsGestureInProgress;
    public MagnetizedObject.MagneticTarget mMagneticTarget;
    public MagnetizedObject mMagnetizedObject;
    public final ShellExecutor mMainExecutor;
    public ManageEducationView mManageEduView;
    public final View mManageMenuScrim;
    public final BubbleStackView$$ExternalSyntheticLambda9 mOrientationChangedListener;
    public int mPointerIndexDown;
    public final BubblePositioner mPositioner;
    public RelativeStackPosition mRelativeStackPositionBeforeRotation;
    public final PhysicsAnimator.SpringConfig mScaleInSpringConfig;
    public final PhysicsAnimator.SpringConfig mScaleOutSpringConfig;
    public final View mScrim;
    public boolean mScrimAnimating;
    public boolean mShowedUserEducationInTouchListenerActive;
    public final StackAnimationController mStackAnimationController;
    public StackEducationView mStackEduView;
    public final C38205 mStackMagnetListener;
    public boolean mStackOnLeftOrWillBe;
    public final StackViewState mStackViewState;
    public final SurfaceSynchronizer mSurfaceSynchronizer;
    public final C38249 mSwipeUpListener;
    public final BubbleStackView$$ExternalSyntheticLambda5 mSystemGestureExcludeUpdater;
    public final List mSystemGestureExclusionRects;
    public final Rect mTempRect;
    public boolean mTemporarilyInvisible;
    public View mViewBeingDismissed;
    public boolean mViewUpdatedRequested;
    public final ViewTreeObserverOnPreDrawListenerC38172 mViewUpdater;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.bubbles.BubbleStackView$4 */
    public final class C38194 {
        public C38194() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.bubbles.BubbleStackView$5 */
    public final class C38205 {
        public C38205() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.bubbles.BubbleStackView$6 */
    public final class ViewOnClickListenerC38216 implements View.OnClickListener {
        public ViewOnClickListenerC38216() {
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            final Bubble bubbleWithView;
            BubbleStackView bubbleStackView = BubbleStackView.this;
            bubbleStackView.mIsDraggingStack = false;
            if (bubbleStackView.mIsExpansionAnimating || bubbleStackView.mIsBubbleSwitchAnimating || (bubbleWithView = bubbleStackView.mBubbleData.getBubbleWithView(view)) == null) {
                return;
            }
            boolean equals = bubbleWithView.mKey.equals(BubbleStackView.this.mExpandedBubble.getKey());
            BubbleStackView bubbleStackView2 = BubbleStackView.this;
            if (bubbleStackView2.mIsExpanded) {
                ExpandedAnimationController expandedAnimationController = bubbleStackView2.mExpandedAnimationController;
                expandedAnimationController.mBubbleDraggedOutEnough = false;
                expandedAnimationController.mMagnetizedBubbleDraggingOut = null;
                expandedAnimationController.updateBubblePositions();
            }
            BubbleStackView bubbleStackView3 = BubbleStackView.this;
            if (!bubbleStackView3.mIsExpanded || equals) {
                if (!bubbleStackView3.mShowedUserEducationInTouchListenerActive) {
                    bubbleStackView3.mBubbleData.setExpanded(!r0.mExpanded);
                }
                BubbleStackView.this.mShowedUserEducationInTouchListenerActive = false;
            } else {
                BubbleData bubbleData = bubbleStackView3.mBubbleData;
                if (bubbleWithView != bubbleData.mSelectedBubble) {
                    bubbleData.setSelectedBubble(bubbleWithView);
                } else {
                    bubbleStackView3.setSelectedBubble(bubbleWithView);
                }
            }
            Optional.ofNullable(bubbleWithView).ifPresent(new Consumer() { // from class: com.android.wm.shell.bubbles.BubbleStackView$6$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    BubbleStackView.this.mBubbleSALogger.sendEventCDLog("QPNE0100", "app", bubbleWithView.mPackageName);
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.bubbles.BubbleStackView$9 */
    public final class C38249 implements BubblesNavBarMotionEventHandler.MotionEventListener {
        public C38249() {
        }

        public final void onMove(float f) {
            int i;
            int height;
            int i2;
            BubbleStackView bubbleStackView = BubbleStackView.this;
            if (bubbleStackView.isManageEduVisible() || bubbleStackView.isStackEduVisible()) {
                return;
            }
            float f2 = -Math.min(f, 0.0f);
            bubbleStackView.mExpandedViewAnimationController.getClass();
            if (bubbleStackView.mScrimAnimating) {
                return;
            }
            View view = bubbleStackView.mScrim;
            BubbleViewProvider bubbleViewProvider = bubbleStackView.mExpandedBubble;
            float f3 = 0.6f;
            if (bubbleViewProvider != null && bubbleViewProvider.getExpandedView() != null) {
                BubbleExpandedView expandedView = bubbleStackView.mExpandedBubble.getExpandedView();
                if (expandedView.mIsOverflow) {
                    height = expandedView.mOverflowView.getHeight() - expandedView.mTopClip;
                    i2 = expandedView.mBottomClip;
                } else {
                    TaskView taskView = expandedView.mTaskView;
                    if (taskView != null) {
                        height = taskView.getHeight() - expandedView.mTopClip;
                        i2 = expandedView.mBottomClip;
                    } else {
                        i = 0;
                        f3 = Math.max(0.6f - ((f2 / i) * 0.40000004f), 0.2f);
                    }
                }
                i = height - i2;
                f3 = Math.max(0.6f - ((f2 / i) * 0.40000004f), 0.2f);
            }
            view.setAlpha(f3);
        }

        public final void onUp(float f) {
            BubbleStackView bubbleStackView = BubbleStackView.this;
            ExpandedViewAnimationControllerImpl expandedViewAnimationControllerImpl = bubbleStackView.mExpandedViewAnimationController;
            if (f < 0.0f) {
                expandedViewAnimationControllerImpl.getClass();
                expandedViewAnimationControllerImpl.mSwipeUpVelocity = Math.abs(f);
                expandedViewAnimationControllerImpl.mSwipeDownVelocity = 0.0f;
            } else {
                expandedViewAnimationControllerImpl.mSwipeUpVelocity = 0.0f;
                expandedViewAnimationControllerImpl.mSwipeDownVelocity = f;
            }
            ExpandedViewAnimationControllerImpl expandedViewAnimationControllerImpl2 = bubbleStackView.mExpandedViewAnimationController;
            float f2 = expandedViewAnimationControllerImpl2.mSwipeDownVelocity;
            float f3 = expandedViewAnimationControllerImpl2.mMinFlingVelocity;
            if (f2 <= f3 && expandedViewAnimationControllerImpl2.mSwipeUpVelocity > f3) {
                bubbleStackView.mBubbleData.setExpanded(false);
                return;
            }
            expandedViewAnimationControllerImpl2.getClass();
            if (bubbleStackView.mScrimAnimating) {
                return;
            }
            bubbleStackView.showScrim(true);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StackViewState {
        public int numberOfBubbles;
        public boolean onLeft;
        public int selectedIndex;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface SurfaceSynchronizer {
    }

    public static void $r8$lambda$Cn_cFsmoCiAxFL8cmUmWnqiwXJk(BubbleStackView bubbleStackView) {
        BubbleData bubbleData = bubbleStackView.mBubbleData;
        BubbleViewProvider bubbleViewProvider = bubbleData.mSelectedBubble;
        if (bubbleViewProvider == null || !bubbleData.hasBubbleInStackWithKey(bubbleViewProvider.getKey())) {
            return;
        }
        Bubble bubble = (Bubble) bubbleViewProvider;
        Context context = ((FrameLayout) bubbleStackView).mContext;
        Intent intent = new Intent("android.settings.APP_NOTIFICATION_BUBBLE_SETTINGS");
        String str = bubble.mChannelId;
        if (str == null || str.equals("miscellaneous")) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
        } else {
            intent.setAction("android.settings.CHANNEL_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.CHANNEL_ID", bubble.mChannelId);
        }
        intent.putExtra("android.provider.extra.APP_PACKAGE", bubble.mPackageName);
        int i = bubble.mAppUid;
        if (i == -1) {
            PackageManager packageManagerForUser = BubbleController.getPackageManagerForUser(bubble.mUser.getIdentifier(), context);
            if (packageManagerForUser != null) {
                try {
                    i = packageManagerForUser.getApplicationInfo(bubble.mShortcutInfo.getPackage(), 0).uid;
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("Bubble", "cannot find uid", e);
                }
            }
            i = -1;
        }
        if (i != -1) {
            intent.putExtra("app_uid", i);
        }
        intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        intent.addFlags(QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT);
        bubbleStackView.mBubbleData.setExpanded(false);
        ((FrameLayout) bubbleStackView).mContext.startActivityAsUser(intent, bubble.mUser);
        bubbleStackView.logBubbleEvent(bubbleViewProvider, 9);
        bubbleStackView.mBubbleSALogger.sendEventCDLog("QPNE0102", "app", bubble.mPackageName);
    }

    /* renamed from: -$$Nest$mdismissMagnetizedObject, reason: not valid java name */
    public static void m2737$$Nest$mdismissMagnetizedObject(BubbleStackView bubbleStackView) {
        if (!bubbleStackView.mIsExpanded) {
            bubbleStackView.mBubbleData.dismissAll(1);
            bubbleStackView.mBubbleSALogger.sendEventCDLog("QPNE0101", "type", "group");
            return;
        }
        Bubble bubbleWithView = bubbleStackView.mBubbleData.getBubbleWithView((View) bubbleStackView.mMagnetizedObject.underlyingObject);
        if (bubbleWithView != null && bubbleStackView.mBubbleData.hasBubbleInStackWithKey(bubbleWithView.getKey())) {
            if (bubbleStackView.mIsExpanded && bubbleStackView.mBubbleData.getBubbles().size() > 1 && bubbleWithView.equals(bubbleStackView.mExpandedBubble)) {
                bubbleStackView.mIsBubbleSwitchAnimating = true;
            }
            bubbleStackView.mBubbleData.dismissBubbleWithKey(1, bubbleWithView.getKey());
        }
        bubbleStackView.mBubbleSALogger.sendEventCDLog("QPNE0101", "type", "single");
    }

    /* renamed from: -$$Nest$mshowExpandedViewIfNeeded, reason: not valid java name */
    public static void m2738$$Nest$mshowExpandedViewIfNeeded(BubbleStackView bubbleStackView) {
        if (bubbleStackView.mExpandedViewTemporarilyHidden) {
            bubbleStackView.mExpandedViewTemporarilyHidden = false;
            PhysicsAnimator physicsAnimator = PhysicsAnimator.getInstance(bubbleStackView.mExpandedViewContainerMatrix);
            physicsAnimator.spring(AnimatableScaleMatrix.SCALE_X, 499.99997f, 0.0f, bubbleStackView.mScaleOutSpringConfig);
            physicsAnimator.spring(AnimatableScaleMatrix.SCALE_Y, 499.99997f, 0.0f, bubbleStackView.mScaleOutSpringConfig);
            physicsAnimator.updateListeners.add(new BubbleStackView$$ExternalSyntheticLambda11(bubbleStackView, 1));
            physicsAnimator.start();
            bubbleStackView.mExpandedViewAlphaAnimator.start();
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.wm.shell.bubbles.BubbleStackView$1] */
    static {
        SystemProperties.getBoolean("persist.wm.debug.fling_to_dismiss_bubble", true);
        FLYOUT_IME_ANIMATION_SPRING_CONFIG = new PhysicsAnimator.SpringConfig(200.0f, 0.9f);
        DEFAULT_SURFACE_SYNCHRONIZER = new SurfaceSynchronizer() { // from class: com.android.wm.shell.bubbles.BubbleStackView.1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.wm.shell.bubbles.BubbleStackView$1$1, reason: invalid class name */
            public final class AnonymousClass1 implements Choreographer.FrameCallback {
                public int mFrameWait = 2;
                public final /* synthetic */ Runnable val$callback;

                public AnonymousClass1(C38071 c38071, Runnable runnable) {
                    this.val$callback = runnable;
                }

                @Override // android.view.Choreographer.FrameCallback
                public final void doFrame(long j) {
                    int i = this.mFrameWait - 1;
                    this.mFrameWait = i;
                    if (i > 0) {
                        Choreographer.getInstance().postFrameCallback(this);
                    } else {
                        this.val$callback.run();
                    }
                }
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v3, types: [com.android.wm.shell.bubbles.BubbleStackView$2] */
    /* JADX WARN: Type inference failed for: r10v4, types: [com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda5] */
    /* JADX WARN: Type inference failed for: r10v5, types: [androidx.dynamicanimation.animation.FloatPropertyCompat, com.android.wm.shell.bubbles.BubbleStackView$3] */
    /* JADX WARN: Type inference failed for: r12v0, types: [androidx.dynamicanimation.animation.DynamicAnimation$OnAnimationEndListener, com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda6] */
    /* JADX WARN: Type inference failed for: r13v3, types: [com.android.wm.shell.bubbles.BubbleStackView$7] */
    /* JADX WARN: Type inference failed for: r13v4, types: [com.android.wm.shell.bubbles.BubbleStackView$8] */
    /* JADX WARN: Type inference failed for: r13v6, types: [com.android.wm.shell.bubbles.BubbleStackView$10] */
    /* JADX WARN: Type inference failed for: r13v7, types: [com.android.wm.shell.bubbles.BubbleStackView$11] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda9] */
    public BubbleStackView(Context context, BubbleController bubbleController, BubbleData bubbleData, SurfaceSynchronizer surfaceSynchronizer, FloatingContentCoordinator floatingContentCoordinator, ShellExecutor shellExecutor) {
        super(context);
        this.mScaleInSpringConfig = new PhysicsAnimator.SpringConfig(300.0f, 0.9f);
        this.mScaleOutSpringConfig = new PhysicsAnimator.SpringConfig(900.0f, 1.0f);
        new PhysicsAnimator.SpringConfig(50.0f, 1.0f);
        this.mStackViewState = new StackViewState();
        this.mExpandedViewContainerMatrix = new AnimatableScaleMatrix();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mAnimatingOutSurfaceAlphaAnimator = ofFloat;
        this.mHideFlyout = new BubbleStackView$$ExternalSyntheticLambda3(this, 0);
        this.mBubbleToExpandAfterFlyoutCollapse = null;
        this.mStackOnLeftOrWillBe = true;
        this.mIsGestureInProgress = false;
        this.mTemporarilyInvisible = false;
        this.mIsDraggingStack = false;
        this.mExpandedViewTemporarilyHidden = false;
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mExpandedViewAlphaAnimator = ofFloat2;
        this.mPointerIndexDown = -1;
        this.mViewUpdatedRequested = false;
        this.mIsExpansionAnimating = false;
        this.mIsBubbleSwitchAnimating = false;
        this.mTempRect = new Rect();
        this.mSystemGestureExclusionRects = Collections.singletonList(new Rect());
        this.mViewUpdater = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.wm.shell.bubbles.BubbleStackView.2
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                BubbleStackView.this.getViewTreeObserver().removeOnPreDrawListener(BubbleStackView.this.mViewUpdater);
                BubbleViewProvider bubbleViewProvider = BubbleStackView.this.mExpandedBubble;
                if (bubbleViewProvider != null && bubbleViewProvider.getKey().equals("Overflow")) {
                    BubbleStackView.this.updateExpandedView();
                } else {
                    BubbleStackView bubbleStackView = BubbleStackView.this;
                    int[] expandedViewContainerPadding = bubbleStackView.mPositioner.getExpandedViewContainerPadding(bubbleStackView.mStackAnimationController.isStackOnLeftSide());
                    bubbleStackView.mExpandedViewContainer.setPadding(expandedViewContainerPadding[0], expandedViewContainerPadding[1], expandedViewContainerPadding[2], expandedViewContainerPadding[3]);
                    BubbleViewProvider bubbleViewProvider2 = bubbleStackView.mExpandedBubble;
                    if (bubbleViewProvider2 != null && bubbleViewProvider2.getExpandedView() != null) {
                        PointF expandedBubbleXY = bubbleStackView.mPositioner.getExpandedBubbleXY(bubbleStackView.getBubbleIndex(bubbleStackView.mExpandedBubble), bubbleStackView.getState());
                        FrameLayout frameLayout = bubbleStackView.mExpandedViewContainer;
                        BubblePositioner bubblePositioner = bubbleStackView.mPositioner;
                        frameLayout.setTranslationY(bubblePositioner.getExpandedViewY(bubbleStackView.mExpandedBubble, bubblePositioner.showBubblesVertically() ? expandedBubbleXY.y : expandedBubbleXY.x));
                        bubbleStackView.mExpandedViewContainer.setTranslationX(bubbleStackView.getInitialTranslationX());
                        bubbleStackView.mExpandedBubble.getExpandedView().updateView(bubbleStackView.mExpandedViewContainer.getLocationOnScreen());
                        bubbleStackView.updatePointerPosition();
                    }
                    bubbleStackView.mStackOnLeftOrWillBe = bubbleStackView.mStackAnimationController.isStackOnLeftSide();
                }
                BubbleStackView.this.mViewUpdatedRequested = false;
                return true;
            }
        };
        this.mSystemGestureExcludeUpdater = new ViewTreeObserver.OnDrawListener() { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda5
            @Override // android.view.ViewTreeObserver.OnDrawListener
            public final void onDraw() {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                Rect rect = (Rect) bubbleStackView.mSystemGestureExclusionRects.get(0);
                if (bubbleStackView.getBubbleCount() <= 0) {
                    rect.setEmpty();
                    bubbleStackView.mBubbleContainer.setSystemGestureExclusionRects(Collections.emptyList());
                } else {
                    View childAt = bubbleStackView.mBubbleContainer.getChildAt(0);
                    rect.set(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom());
                    rect.offset((int) (childAt.getTranslationX() + 0.5f), (int) (childAt.getTranslationY() + 0.5f));
                    bubbleStackView.mBubbleContainer.setSystemGestureExclusionRects(bubbleStackView.mSystemGestureExclusionRects);
                }
            }
        };
        ?? r10 = new FloatPropertyCompat("FlyoutCollapseSpring") { // from class: com.android.wm.shell.bubbles.BubbleStackView.3
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final float getValue(Object obj) {
                return BubbleStackView.this.mFlyoutDragDeltaX;
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final void setValue(Object obj, float f) {
                BubbleStackView.this.setFlyoutStateForDragLength(f);
            }
        };
        this.mFlyoutCollapseProperty = r10;
        SpringAnimation springAnimation = new SpringAnimation(this, (FloatPropertyCompat) r10);
        this.mFlyoutTransitionSpring = springAnimation;
        this.mFlyoutDragDeltaX = 0.0f;
        ?? r12 = new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda6
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                if (bubbleStackView.mFlyoutDragDeltaX == 0.0f) {
                    bubbleStackView.mFlyout.postDelayed(bubbleStackView.mHideFlyout, 5000L);
                    return;
                }
                BubbleFlyoutView bubbleFlyoutView = bubbleStackView.mFlyout;
                Runnable runnable = bubbleFlyoutView.mOnHide;
                if (runnable != null) {
                    runnable.run();
                    bubbleFlyoutView.mOnHide = null;
                }
                bubbleFlyoutView.setVisibility(8);
            }
        };
        this.mAfterFlyoutTransitionSpring = r12;
        this.mIndividualBubbleMagnetListener = new C38194();
        this.mStackMagnetListener = new C38205();
        this.mBubbleClickListener = new ViewOnClickListenerC38216();
        this.mBubbleTouchListener = new RelativeTouchListener() { // from class: com.android.wm.shell.bubbles.BubbleStackView.7
            /* JADX WARN: Type inference failed for: r7v1, types: [com.android.wm.shell.bubbles.animation.ExpandedAnimationController$1] */
            /* JADX WARN: Type inference failed for: r9v0, types: [com.android.wm.shell.bubbles.animation.StackAnimationController$2] */
            @Override // com.android.wm.shell.bubbles.RelativeTouchListener
            public final void onDown(final View view, MotionEvent motionEvent) {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                if (bubbleStackView.mIsExpansionAnimating) {
                    return;
                }
                int i = 0;
                bubbleStackView.mShowedUserEducationInTouchListenerActive = false;
                if (bubbleStackView.isStackEduVisible()) {
                    BubbleStackView.this.mStackEduView.hide(false);
                }
                BubbleStackView.this.getClass();
                BubbleStackView bubbleStackView2 = BubbleStackView.this;
                if (bubbleStackView2.mBubbleData.mExpanded) {
                    ManageEducationView manageEducationView = bubbleStackView2.mManageEduView;
                    if (manageEducationView != null) {
                        manageEducationView.hide();
                    }
                    final ExpandedAnimationController expandedAnimationController = BubbleStackView.this.mExpandedAnimationController;
                    expandedAnimationController.mLayout.cancelAnimationsOnView(view);
                    view.setTranslationZ(32767.0f);
                    final Context context2 = expandedAnimationController.mLayout.getContext();
                    final DynamicAnimation.C01841 c01841 = DynamicAnimation.TRANSLATION_X;
                    final DynamicAnimation.C01912 c01912 = DynamicAnimation.TRANSLATION_Y;
                    expandedAnimationController.mMagnetizedBubbleDraggingOut = new MagnetizedObject(context2, view, c01841, c01912) { // from class: com.android.wm.shell.bubbles.animation.ExpandedAnimationController.1
                        @Override // com.android.wm.shell.common.magnetictarget.MagnetizedObject
                        public final float getHeight(Object obj) {
                            return ExpandedAnimationController.this.mBubbleSizePx;
                        }

                        @Override // com.android.wm.shell.common.magnetictarget.MagnetizedObject
                        public final void getLocationOnScreen(Object obj, int[] iArr) {
                            View view2 = view;
                            iArr[0] = (int) view2.getTranslationX();
                            iArr[1] = (int) view2.getTranslationY();
                        }

                        @Override // com.android.wm.shell.common.magnetictarget.MagnetizedObject
                        public final float getWidth(Object obj) {
                            return ExpandedAnimationController.this.mBubbleSizePx;
                        }
                    };
                    BubbleStackView.this.hideCurrentInputMethod();
                    BubbleStackView bubbleStackView3 = BubbleStackView.this;
                    bubbleStackView3.mMagnetizedObject = bubbleStackView3.mExpandedAnimationController.mMagnetizedBubbleDraggingOut;
                } else {
                    StackAnimationController stackAnimationController = bubbleStackView2.mStackAnimationController;
                    stackAnimationController.getClass();
                    DynamicAnimation.C01841 c018412 = DynamicAnimation.TRANSLATION_X;
                    stackAnimationController.cancelStackPositionAnimation(c018412);
                    DynamicAnimation.C01912 c019122 = DynamicAnimation.TRANSLATION_Y;
                    stackAnimationController.cancelStackPositionAnimation(c019122);
                    stackAnimationController.mLayout.mEndActionForProperty.remove(c018412);
                    stackAnimationController.mLayout.mEndActionForProperty.remove(c019122);
                    BubbleStackView bubbleStackView4 = BubbleStackView.this;
                    bubbleStackView4.mBubbleContainer.setActiveController(bubbleStackView4.mStackAnimationController);
                    BubbleStackView.this.hideFlyoutImmediate();
                    BubbleStackView bubbleStackView5 = BubbleStackView.this;
                    final StackAnimationController stackAnimationController2 = bubbleStackView5.mStackAnimationController;
                    if (stackAnimationController2.mMagnetizedStack == null) {
                        final Context context3 = stackAnimationController2.mLayout.getContext();
                        final StackAnimationController.StackPositionProperty stackPositionProperty = new StackAnimationController.StackPositionProperty(stackAnimationController2, c018412, i);
                        final StackAnimationController.StackPositionProperty stackPositionProperty2 = new StackAnimationController.StackPositionProperty(stackAnimationController2, c019122, i);
                        stackAnimationController2.mMagnetizedStack = new MagnetizedObject(context3, stackAnimationController2, stackPositionProperty, stackPositionProperty2) { // from class: com.android.wm.shell.bubbles.animation.StackAnimationController.2
                            public C38442(final Context context32, final StackAnimationController stackAnimationController22, final FloatPropertyCompat stackPositionProperty3, final FloatPropertyCompat stackPositionProperty22) {
                                super(context32, stackAnimationController22, stackPositionProperty3, stackPositionProperty22);
                            }

                            @Override // com.android.wm.shell.common.magnetictarget.MagnetizedObject
                            public final float getHeight(Object obj) {
                                return StackAnimationController.this.mBubbleSize;
                            }

                            @Override // com.android.wm.shell.common.magnetictarget.MagnetizedObject
                            public final void getLocationOnScreen(Object obj, int[] iArr) {
                                PointF pointF = StackAnimationController.this.mStackPosition;
                                iArr[0] = (int) pointF.x;
                                iArr[1] = (int) pointF.y;
                            }

                            @Override // com.android.wm.shell.common.magnetictarget.MagnetizedObject
                            public final float getWidth(Object obj) {
                                return StackAnimationController.this.mBubbleSize;
                            }
                        };
                    }
                    ContentResolver contentResolver = stackAnimationController22.mLayout.getContext().getContentResolver();
                    float f = Settings.Secure.getFloat(contentResolver, "bubble_dismiss_fling_min_velocity", stackAnimationController22.mMagnetizedStack.flingToTargetMinVelocity);
                    float f2 = Settings.Secure.getFloat(contentResolver, "bubble_dismiss_stick_max_velocity", stackAnimationController22.mMagnetizedStack.stickToTargetMaxXVelocity);
                    float f3 = Settings.Secure.getFloat(contentResolver, "bubble_dismiss_target_width_percent", stackAnimationController22.mMagnetizedStack.flingToTargetWidthPercent);
                    StackAnimationController.C38442 c38442 = stackAnimationController22.mMagnetizedStack;
                    c38442.flingToTargetMinVelocity = f;
                    c38442.stickToTargetMaxXVelocity = f2;
                    c38442.flingToTargetWidthPercent = f3;
                    bubbleStackView5.mMagnetizedObject = c38442;
                    BubbleStackView.this.mMagnetizedObject.associatedTargets.clear();
                    BubbleStackView bubbleStackView6 = BubbleStackView.this;
                    MagnetizedObject magnetizedObject = bubbleStackView6.mMagnetizedObject;
                    MagnetizedObject.MagneticTarget magneticTarget = bubbleStackView6.mMagneticTarget;
                    magnetizedObject.associatedTargets.add(magneticTarget);
                    magneticTarget.getClass();
                    magneticTarget.targetView.post(new MagnetizedObject$MagneticTarget$updateLocationOnScreen$1(magneticTarget));
                    BubbleStackView bubbleStackView7 = BubbleStackView.this;
                    bubbleStackView7.mIsDraggingStack = true;
                    bubbleStackView7.updateTemporarilyInvisibleAnimation(false);
                }
                BubbleStackView.this.getClass();
            }

            @Override // com.android.wm.shell.bubbles.RelativeTouchListener
            public final void onMove(View view, MotionEvent motionEvent, float f, float f2, float f3, float f4) {
                boolean z;
                boolean z2;
                BubbleViewProvider bubbleViewProvider;
                BubbleStackView bubbleStackView = BubbleStackView.this;
                if (bubbleStackView.mIsExpansionAnimating || bubbleStackView.mShowedUserEducationInTouchListenerActive) {
                    return;
                }
                final DismissView dismissView = bubbleStackView.mDismissView;
                boolean z3 = true;
                int i = 0;
                if (!dismissView.isShowing) {
                    dismissView.isShowing = true;
                    dismissView.setVisibility(0);
                    dismissView.resetCircle();
                    GradientDrawable gradientDrawable = dismissView.gradientDrawable;
                    ObjectAnimator ofInt = ObjectAnimator.ofInt(gradientDrawable, dismissView.GRADIENT_ALPHA, gradientDrawable.getAlpha(), 255);
                    ofInt.setDuration(dismissView.DISMISS_SCRIM_FADE_MS);
                    ofInt.start();
                    dismissView.animator.cancel();
                    PhysicsAnimator physicsAnimator = dismissView.animator;
                    physicsAnimator.endActions.addAll(ArraysKt___ArraysKt.filterNotNull(new Function0[]{new Function0() { // from class: com.android.wm.shell.bubbles.DismissView$show$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            DismissView dismissView2 = DismissView.this;
                            dismissView2.circle.getGlobalVisibleRect(dismissView2.dismissArea);
                            return Unit.INSTANCE;
                        }
                    }}));
                    physicsAnimator.spring(DynamicAnimation.TRANSLATION_Y, 0.0f, 0.0f, dismissView.spring);
                    physicsAnimator.start();
                }
                BubbleStackView bubbleStackView2 = BubbleStackView.this;
                if (bubbleStackView2.mIsExpanded && (bubbleViewProvider = bubbleStackView2.mExpandedBubble) != null && !bubbleStackView2.mExpandedViewTemporarilyHidden && bubbleViewProvider.getExpandedView() != null) {
                    bubbleStackView2.mExpandedViewTemporarilyHidden = true;
                    PhysicsAnimator physicsAnimator2 = PhysicsAnimator.getInstance(bubbleStackView2.mExpandedViewContainerMatrix);
                    physicsAnimator2.spring(AnimatableScaleMatrix.SCALE_X, 449.99997f, 0.0f, bubbleStackView2.mScaleOutSpringConfig);
                    physicsAnimator2.spring(AnimatableScaleMatrix.SCALE_Y, 449.99997f, 0.0f, bubbleStackView2.mScaleOutSpringConfig);
                    physicsAnimator2.updateListeners.add(new BubbleStackView$$ExternalSyntheticLambda11(bubbleStackView2, i));
                    physicsAnimator2.start();
                    bubbleStackView2.mExpandedViewAlphaAnimator.reverse();
                }
                BubbleStackView bubbleStackView3 = BubbleStackView.this;
                float rawX = motionEvent.getRawX();
                float rawY = motionEvent.getRawY();
                DismissView dismissView2 = bubbleStackView3.mDismissView;
                Rect rect = dismissView2.dismissArea;
                boolean z4 = ((float) rect.left) < rawX && ((float) rect.right) > rawX && ((float) rect.top) < rawY && ((float) rect.bottom) > rawY;
                if (dismissView2.isBeingEntered != z4) {
                    dismissView2.isBeingEntered = z4;
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    bubbleStackView3.animateDismissBubble(view, dismissView2.isBeingEntered);
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z2) {
                    return;
                }
                BubbleStackView.this.getClass();
                BubbleStackView.this.updateBubbleShadows(true);
                BubbleStackView bubbleStackView4 = BubbleStackView.this;
                if (!bubbleStackView4.mBubbleData.mExpanded) {
                    if (bubbleStackView4.isStackEduVisible()) {
                        BubbleStackView.this.mStackEduView.hide(false);
                    }
                    StackAnimationController stackAnimationController = BubbleStackView.this.mStackAnimationController;
                    float f5 = f + f3;
                    float f6 = f2 + f4;
                    stackAnimationController.getClass();
                    if (stackAnimationController.mFirstBubbleSpringingToTouch) {
                        HashMap hashMap = stackAnimationController.mStackPositionAnimations;
                        SpringAnimation springAnimation2 = (SpringAnimation) hashMap.get(DynamicAnimation.TRANSLATION_X);
                        SpringAnimation springAnimation3 = (SpringAnimation) hashMap.get(DynamicAnimation.TRANSLATION_Y);
                        if (springAnimation2.mRunning || springAnimation3.mRunning) {
                            springAnimation2.animateToFinalPosition(f5);
                            springAnimation3.animateToFinalPosition(f6);
                        } else {
                            stackAnimationController.mFirstBubbleSpringingToTouch = false;
                        }
                    }
                    if (stackAnimationController.mFirstBubbleSpringingToTouch) {
                        return;
                    }
                    stackAnimationController.mAnimatingToBounds.setEmpty();
                    stackAnimationController.mPreImeY = -1.4E-45f;
                    stackAnimationController.moveFirstBubbleWithStackFollowing(DynamicAnimation.TRANSLATION_X, f5);
                    stackAnimationController.moveFirstBubbleWithStackFollowing(DynamicAnimation.TRANSLATION_Y, f6);
                    stackAnimationController.mIsMovingFromFlinging = false;
                    return;
                }
                ExpandedAnimationController expandedAnimationController = bubbleStackView4.mExpandedAnimationController;
                float f7 = f + f3;
                float f8 = f2 + f4;
                if (expandedAnimationController.mMagnetizedBubbleDraggingOut == null) {
                    return;
                }
                if (expandedAnimationController.mSpringingBubbleToTouch) {
                    PhysicsAnimationLayout physicsAnimationLayout = expandedAnimationController.mLayout;
                    DynamicAnimation.C01841 c01841 = DynamicAnimation.TRANSLATION_X;
                    DynamicAnimation.ViewProperty[] viewPropertyArr = {c01841, DynamicAnimation.TRANSLATION_Y};
                    physicsAnimationLayout.getClass();
                    if (PhysicsAnimationLayout.arePropertiesAnimatingOnView(view, viewPropertyArr)) {
                        PhysicsAnimationLayout.PhysicsPropertyAnimator animationForChild = expandedAnimationController.animationForChild((View) expandedAnimationController.mMagnetizedBubbleDraggingOut.underlyingObject);
                        animationForChild.mPathAnimator = null;
                        animationForChild.property(c01841, f7, new Runnable[0]);
                        animationForChild.translationY(f8, new Runnable[0]);
                        animationForChild.mStiffness = 10000.0f;
                        animationForChild.start(new Runnable[0]);
                    } else {
                        expandedAnimationController.mSpringingBubbleToTouch = false;
                    }
                }
                if (!expandedAnimationController.mSpringingBubbleToTouch) {
                    expandedAnimationController.mMagnetizedBubbleDraggingOut.getClass();
                    view.setTranslationX(f7);
                    view.setTranslationY(f8);
                }
                float expandedViewYTopAligned = expandedAnimationController.mPositioner.getExpandedViewYTopAligned();
                float f9 = expandedAnimationController.mBubbleSizePx;
                if (f8 <= expandedViewYTopAligned + f9 && f8 >= expandedViewYTopAligned - f9) {
                    z3 = false;
                }
                if (z3 != expandedAnimationController.mBubbleDraggedOutEnough) {
                    expandedAnimationController.updateBubblePositions();
                    expandedAnimationController.mBubbleDraggedOutEnough = z3;
                }
            }

            @Override // com.android.wm.shell.bubbles.RelativeTouchListener
            public final void onUp(View view, float f, float f2, float f3, float f4) {
                boolean z;
                boolean z2;
                BubbleStackView bubbleStackView = BubbleStackView.this;
                if (bubbleStackView.mIsExpansionAnimating) {
                    return;
                }
                if (bubbleStackView.mShowedUserEducationInTouchListenerActive) {
                    bubbleStackView.mShowedUserEducationInTouchListenerActive = false;
                    return;
                }
                bubbleStackView.animateDismissBubble(view, false);
                if (bubbleStackView.mDismissView.isBeingEntered) {
                    BubbleViewProvider bubbleViewProvider = bubbleStackView.mExpandedBubble;
                    bubbleStackView.mExpandedBubble = bubbleStackView.mBubbleData.getBubbleWithView(view);
                    if (bubbleStackView.mIsExpanded) {
                        BubbleStackView bubbleStackView2 = BubbleStackView.this;
                        ExpandedAnimationController expandedAnimationController = bubbleStackView2.mExpandedAnimationController;
                        ExpandedAnimationController.C38311 c38311 = expandedAnimationController.mMagnetizedBubbleDraggingOut;
                        if ((c38311 == null ? null : (View) c38311.underlyingObject) != null) {
                            View view2 = c38311 == null ? null : (View) c38311.underlyingObject;
                            float height = bubbleStackView2.mDismissView.getHeight();
                            BubbleStackView$$ExternalSyntheticLambda3 bubbleStackView$$ExternalSyntheticLambda3 = new BubbleStackView$$ExternalSyntheticLambda3(bubbleStackView2, 15);
                            if (view2 != null) {
                                PhysicsAnimationLayout.PhysicsPropertyAnimator animationForChild = expandedAnimationController.animationForChild(view2);
                                animationForChild.mStiffness = 10000.0f;
                                animationForChild.property(DynamicAnimation.SCALE_X, 0.0f, new Runnable[0]);
                                animationForChild.property(DynamicAnimation.SCALE_Y, 0.0f, new Runnable[0]);
                                animationForChild.translationY(view2.getTranslationY() + height, new Runnable[0]);
                                animationForChild.property(DynamicAnimation.ALPHA, 0.0f, bubbleStackView$$ExternalSyntheticLambda3);
                                animationForChild.start(new Runnable[0]);
                                expandedAnimationController.updateBubblePositions();
                            }
                            bubbleStackView2.mDismissView.hide();
                        }
                        bubbleStackView.setSelectedBubble(bubbleViewProvider);
                    } else {
                        C38205 c38205 = bubbleStackView.mStackMagnetListener;
                        BubbleStackView bubbleStackView3 = BubbleStackView.this;
                        final StackAnimationController stackAnimationController = bubbleStackView3.mStackAnimationController;
                        final float height2 = bubbleStackView3.mDismissView.getHeight();
                        BubbleStackView$$ExternalSyntheticLambda3 bubbleStackView$$ExternalSyntheticLambda32 = new BubbleStackView$$ExternalSyntheticLambda3(c38205, 16);
                        stackAnimationController.getClass();
                        stackAnimationController.animationsForChildrenFromIndex(new PhysicsAnimationLayout.PhysicsAnimationController.ChildAnimationConfigurator() { // from class: com.android.wm.shell.bubbles.animation.StackAnimationController$$ExternalSyntheticLambda3
                            @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController.ChildAnimationConfigurator
                            public final void configureAnimationForChildAtIndex(int i, PhysicsAnimationLayout.PhysicsPropertyAnimator physicsPropertyAnimator) {
                                StackAnimationController stackAnimationController2 = StackAnimationController.this;
                                stackAnimationController2.getClass();
                                physicsPropertyAnimator.property(DynamicAnimation.SCALE_X, 0.0f, new Runnable[0]);
                                physicsPropertyAnimator.property(DynamicAnimation.SCALE_Y, 0.0f, new Runnable[0]);
                                physicsPropertyAnimator.property(DynamicAnimation.ALPHA, 0.0f, new Runnable[0]);
                                physicsPropertyAnimator.translationY(stackAnimationController2.mLayout.getChildAt(i).getTranslationY() + height2, new Runnable[0]);
                                physicsPropertyAnimator.mStiffness = 10000.0f;
                            }
                        }).startAll(new Runnable[]{bubbleStackView$$ExternalSyntheticLambda32});
                        bubbleStackView3.mDismissView.hide();
                    }
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    return;
                }
                BubbleStackView.m2738$$Nest$mshowExpandedViewIfNeeded(BubbleStackView.this);
                BubbleStackView.this.getClass();
                BubbleStackView bubbleStackView4 = BubbleStackView.this;
                if (bubbleStackView4.mBubbleData.mExpanded) {
                    ExpandedAnimationController expandedAnimationController2 = bubbleStackView4.mExpandedAnimationController;
                    PhysicsAnimationLayout physicsAnimationLayout = expandedAnimationController2.mLayout;
                    if (physicsAnimationLayout != null) {
                        int indexOfChild = physicsAnimationLayout.indexOfChild(view);
                        PointF expandedBubbleXY = expandedAnimationController2.mPositioner.getExpandedBubbleXY(indexOfChild, expandedAnimationController2.mBubbleStackView.getState());
                        PhysicsAnimationLayout.PhysicsPropertyAnimator animationForChild2 = expandedAnimationController2.animationForChild(expandedAnimationController2.mLayout.getChildAt(indexOfChild));
                        float f5 = expandedBubbleXY.x;
                        float f6 = expandedBubbleXY.y;
                        animationForChild2.mPositionEndActions = new Runnable[0];
                        animationForChild2.mPathAnimator = null;
                        DynamicAnimation.C01841 c01841 = DynamicAnimation.TRANSLATION_X;
                        animationForChild2.property(c01841, f5, new Runnable[0]);
                        animationForChild2.translationY(f6, new Runnable[0]);
                        HashMap hashMap = (HashMap) animationForChild2.mPositionStartVelocities;
                        hashMap.put(c01841, Float.valueOf(f3));
                        hashMap.put(DynamicAnimation.TRANSLATION_Y, Float.valueOf(f4));
                        animationForChild2.start(new ExpandedAnimationController$$ExternalSyntheticLambda0(view, 3));
                        expandedAnimationController2.mMagnetizedBubbleDraggingOut = null;
                        expandedAnimationController2.updateBubblePositions();
                    }
                    BubbleStackView.m2738$$Nest$mshowExpandedViewIfNeeded(BubbleStackView.this);
                } else {
                    boolean z3 = bubbleStackView4.mStackOnLeftOrWillBe;
                    StackAnimationController stackAnimationController2 = bubbleStackView4.mStackAnimationController;
                    float f7 = f + f2;
                    boolean z4 = !(((f7 - ((float) (stackAnimationController2.mBubbleSize / 2))) > ((float) (stackAnimationController2.mLayout.getWidth() / 2)) ? 1 : ((f7 - ((float) (stackAnimationController2.mBubbleSize / 2))) == ((float) (stackAnimationController2.mLayout.getWidth() / 2)) ? 0 : -1)) < 0) ? f3 >= -750.0f : f3 >= 750.0f;
                    RectF allowableStackPositionRegion = stackAnimationController2.mPositioner.getAllowableStackPositionRegion(stackAnimationController2.getBubbleCount());
                    float f8 = z4 ? allowableStackPositionRegion.left : allowableStackPositionRegion.right;
                    PhysicsAnimationLayout physicsAnimationLayout2 = stackAnimationController2.mLayout;
                    if (physicsAnimationLayout2 == null || physicsAnimationLayout2.getChildCount() == 0) {
                        z2 = true;
                    } else {
                        ContentResolver contentResolver = stackAnimationController2.mLayout.getContext().getContentResolver();
                        float f9 = Settings.Secure.getFloat(contentResolver, "bubble_stiffness", 700.0f);
                        float f10 = Settings.Secure.getFloat(contentResolver, "bubble_damping", 0.85f);
                        float f11 = Settings.Secure.getFloat(contentResolver, "bubble_friction", 1.9f);
                        float f12 = 4.2f * f11 * (f8 - f7);
                        stackAnimationController2.notifyFloatingCoordinatorStackAnimatingTo(f8, PhysicsAnimator.estimateFlingEndValue(stackAnimationController2.mStackPosition.y, f4, new PhysicsAnimator.FlingConfig(f11, allowableStackPositionRegion.top, allowableStackPositionRegion.bottom)));
                        stackAnimationController2.flingThenSpringFirstBubbleWithStackFollowing(DynamicAnimation.TRANSLATION_X, z4 ? Math.min(f12, f3) : Math.max(f12, f3), f11, ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(f9, f10), Float.valueOf(f8));
                        stackAnimationController2.flingThenSpringFirstBubbleWithStackFollowing(DynamicAnimation.TRANSLATION_Y, f4, f11, ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(f9, f10), null);
                        stackAnimationController2.mFirstBubbleSpringingToTouch = false;
                        z2 = true;
                        stackAnimationController2.mIsMovingFromFlinging = true;
                    }
                    bubbleStackView4.mStackOnLeftOrWillBe = f8 <= ((float) (BubbleStackView.this.getWidth() / 2)) ? z2 : false;
                    BubbleStackView bubbleStackView5 = BubbleStackView.this;
                    if (z3 == bubbleStackView5.mStackOnLeftOrWillBe) {
                        z2 = false;
                    }
                    bubbleStackView5.updateBadges(z2);
                    BubbleStackView.this.logBubbleEvent(null, 7);
                }
                BubbleStackView.this.mDismissView.hide();
                BubbleStackView bubbleStackView6 = BubbleStackView.this;
                bubbleStackView6.mIsDraggingStack = false;
                bubbleStackView6.updateTemporarilyInvisibleAnimation(false);
            }
        };
        this.mContainerSwipeListener = new RelativeTouchListener() { // from class: com.android.wm.shell.bubbles.BubbleStackView.8
            @Override // com.android.wm.shell.bubbles.RelativeTouchListener
            public final void onDown(View view, MotionEvent motionEvent) {
                C38249 c38249 = BubbleStackView.this.mSwipeUpListener;
                motionEvent.getX();
                motionEvent.getY();
                c38249.getClass();
            }

            @Override // com.android.wm.shell.bubbles.RelativeTouchListener
            public final void onMove(View view, MotionEvent motionEvent, float f, float f2, float f3, float f4) {
                BubbleStackView.this.mSwipeUpListener.onMove(f4);
            }

            @Override // com.android.wm.shell.bubbles.RelativeTouchListener
            public final void onUp(View view, float f, float f2, float f3, float f4) {
                BubbleStackView.this.mSwipeUpListener.onUp(f4);
            }
        };
        this.mSwipeUpListener = new C38249();
        this.mFlyoutClickListener = new View.OnClickListener() { // from class: com.android.wm.shell.bubbles.BubbleStackView.10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                bubbleStackView.getClass();
                BubbleStackView bubbleStackView2 = BubbleStackView.this;
                bubbleStackView2.mBubbleToExpandAfterFlyoutCollapse = bubbleStackView2.mBubbleData.mSelectedBubble;
                bubbleStackView2.mFlyout.removeCallbacks(bubbleStackView2.mHideFlyout);
                BubbleStackView.this.mHideFlyout.run();
            }
        };
        this.mFlyoutTouchListener = new RelativeTouchListener() { // from class: com.android.wm.shell.bubbles.BubbleStackView.11
            @Override // com.android.wm.shell.bubbles.RelativeTouchListener
            public final void onDown(View view, MotionEvent motionEvent) {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                bubbleStackView.mFlyout.removeCallbacks(bubbleStackView.mHideFlyout);
            }

            @Override // com.android.wm.shell.bubbles.RelativeTouchListener
            public final void onMove(View view, MotionEvent motionEvent, float f, float f2, float f3, float f4) {
                BubbleStackView.this.setFlyoutStateForDragLength(f3);
            }

            @Override // com.android.wm.shell.bubbles.RelativeTouchListener
            public final void onUp(View view, float f, float f2, float f3, float f4) {
                boolean isStackOnLeftSide = BubbleStackView.this.mStackAnimationController.isStackOnLeftSide();
                boolean z = true;
                boolean z2 = !isStackOnLeftSide ? f3 <= 2000.0f : f3 >= -2000.0f;
                boolean z3 = !isStackOnLeftSide ? f2 <= ((float) BubbleStackView.this.mFlyout.getWidth()) * 0.25f : f2 >= ((float) (-BubbleStackView.this.mFlyout.getWidth())) * 0.25f;
                boolean z4 = !isStackOnLeftSide ? f3 >= 0.0f : f3 <= 0.0f;
                if (!z2 && (!z3 || z4)) {
                    z = false;
                }
                BubbleStackView bubbleStackView = BubbleStackView.this;
                bubbleStackView.mFlyout.removeCallbacks(bubbleStackView.mHideFlyout);
                BubbleStackView.this.animateFlyoutCollapsed(f3, z);
                BubbleStackView.this.getClass();
            }
        };
        this.mShowedUserEducationInTouchListenerActive = false;
        new PhysicsAnimator.SpringConfig(1500.0f, 0.75f);
        this.mAnimateTemporarilyInvisibleImmediate = new BubbleStackView$$ExternalSyntheticLambda3(this, 1);
        this.mMainExecutor = shellExecutor;
        this.mBubbleController = bubbleController;
        this.mBubbleData = bubbleData;
        Resources resources = getResources();
        this.mBubbleSize = resources.getDimensionPixelSize(R.dimen.bubble_size);
        this.mBubbleElevation = resources.getDimensionPixelSize(R.dimen.bubble_elevation);
        this.mBubbleTouchPadding = resources.getDimensionPixelSize(R.dimen.bubble_touch_padding);
        this.mExpandedViewPadding = resources.getDimensionPixelSize(R.dimen.bubble_expanded_view_padding);
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.bubble_elevation);
        BubblePositioner positioner = bubbleController.getPositioner();
        this.mPositioner = positioner;
        TypedArray obtainStyledAttributes = ((FrameLayout) this).mContext.obtainStyledAttributes(new int[]{android.R.attr.dialogCornerRadius});
        int dimensionPixelSize2 = getContext().getResources().getDimensionPixelSize(R.dimen.sec_noti_bubble_expand_view_radius);
        this.mBubbleStackOff = resources.getDimensionPixelSize(R.dimen.bubble_stack_offset);
        obtainStyledAttributes.recycle();
        BubbleStackView$$ExternalSyntheticLambda3 bubbleStackView$$ExternalSyntheticLambda3 = new BubbleStackView$$ExternalSyntheticLambda3(this, 2);
        StackAnimationController stackAnimationController = new StackAnimationController(floatingContentCoordinator, new IntSupplier() { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda7
            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                return BubbleStackView.this.getBubbleCount();
            }
        }, bubbleStackView$$ExternalSyntheticLambda3, new BubbleStackView$$ExternalSyntheticLambda3(this, 3), positioner);
        this.mStackAnimationController = stackAnimationController;
        this.mExpandedAnimationController = new ExpandedAnimationController(positioner, bubbleStackView$$ExternalSyntheticLambda3, this);
        this.mExpandedViewAnimationController = new ExpandedViewAnimationControllerImpl(context, positioner);
        this.mSurfaceSynchronizer = surfaceSynchronizer != null ? surfaceSynchronizer : DEFAULT_SURFACE_SYNCHRONIZER;
        setLayoutDirection(0);
        PhysicsAnimationLayout physicsAnimationLayout = new PhysicsAnimationLayout(context);
        this.mBubbleContainer = physicsAnimationLayout;
        physicsAnimationLayout.setActiveController(stackAnimationController);
        float f = dimensionPixelSize;
        physicsAnimationLayout.setElevation(f);
        physicsAnimationLayout.setClipChildren(false);
        addView(physicsAnimationLayout, new FrameLayout.LayoutParams(-1, -1));
        FrameLayout frameLayout = new FrameLayout(context);
        this.mExpandedViewContainer = frameLayout;
        frameLayout.setElevation(f);
        frameLayout.setClipChildren(false);
        addView(frameLayout);
        FrameLayout frameLayout2 = new FrameLayout(getContext());
        this.mAnimatingOutSurfaceContainer = frameLayout2;
        frameLayout2.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        addView(frameLayout2);
        SurfaceView surfaceView = new SurfaceView(getContext());
        this.mAnimatingOutSurfaceView = surfaceView;
        surfaceView.setZOrderOnTop(true);
        surfaceView.setCornerRadius(ScreenDecorationsUtils.supportsRoundedCornersOnWindows(((FrameLayout) this).mContext.getResources()) ? dimensionPixelSize2 : 0.0f);
        surfaceView.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() { // from class: com.android.wm.shell.bubbles.BubbleStackView.12
            @Override // android.view.SurfaceHolder.Callback
            public final void surfaceCreated(SurfaceHolder surfaceHolder) {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                bubbleStackView.getClass();
            }

            @Override // android.view.SurfaceHolder.Callback
            public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                bubbleStackView.getClass();
            }

            @Override // android.view.SurfaceHolder.Callback
            public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            }
        });
        frameLayout2.addView(surfaceView);
        frameLayout2.setPadding(frameLayout.getPaddingLeft(), frameLayout.getPaddingTop(), frameLayout.getPaddingRight(), frameLayout.getPaddingBottom());
        setUpFlyout();
        SpringForce springForce = new SpringForce();
        springForce.setStiffness(200.0f);
        springForce.setDampingRatio(0.75f);
        springAnimation.mSpring = springForce;
        springAnimation.addEndListener(r12);
        setUpDismissView();
        setClipChildren(false);
        setFocusable(true);
        physicsAnimationLayout.bringToFront();
        BubbleOverflow bubbleOverflow = bubbleData.mOverflow;
        this.mBubbleOverflow = bubbleOverflow;
        BadgedImageView iconView$1 = bubbleOverflow.getIconView$1();
        int childCount = physicsAnimationLayout.getChildCount();
        int i = positioner.mBubbleSize;
        physicsAnimationLayout.addViewInternal(iconView$1, childCount, new FrameLayout.LayoutParams(i, i), false);
        updateOverflow();
        bubbleOverflow.getIconView$1().setOnClickListener(new BubbleStackView$$ExternalSyntheticLambda8(this, 0));
        View view = new View(getContext());
        this.mScrim = view;
        view.setImportantForAccessibility(2);
        view.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.system_neutral1_1000)));
        addView(view);
        view.setAlpha(0.0f);
        View view2 = new View(getContext());
        this.mManageMenuScrim = view2;
        view2.setImportantForAccessibility(2);
        view2.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.system_neutral1_1000)));
        addView(view2, new FrameLayout.LayoutParams(-1, -1));
        view2.setAlpha(0.0f);
        view2.setVisibility(4);
        this.mOrientationChangedListener = new View.OnLayoutChangeListener() { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda9
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view3, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                BubbleStackView.RelativeStackPosition relativeStackPosition;
                BubbleStackView bubbleStackView = BubbleStackView.this;
                bubbleStackView.mPositioner.update();
                bubbleStackView.mExpandedAnimationController.updateResources();
                StackAnimationController stackAnimationController2 = bubbleStackView.mStackAnimationController;
                PhysicsAnimationLayout physicsAnimationLayout2 = stackAnimationController2.mLayout;
                if (physicsAnimationLayout2 != null) {
                    stackAnimationController2.mBubblePaddingTop = physicsAnimationLayout2.getContext().getResources().getDimensionPixelSize(R.dimen.bubble_padding_top);
                }
                bubbleStackView.mBubbleOverflow.updateResources();
                if (bubbleStackView.mTemporarilyInvisible) {
                    bubbleStackView.updateTemporarilyInvisibleAnimation(true);
                }
                if (!bubbleStackView.isStackEduVisible() && (relativeStackPosition = bubbleStackView.mRelativeStackPositionBeforeRotation) != null) {
                    StackAnimationController stackAnimationController3 = bubbleStackView.mStackAnimationController;
                    RectF allowableStackPositionRegion = stackAnimationController3.mPositioner.getAllowableStackPositionRegion(stackAnimationController3.getBubbleCount());
                    stackAnimationController3.setStackPosition(new PointF(relativeStackPosition.mOnLeft ? allowableStackPositionRegion.left : allowableStackPositionRegion.right, (allowableStackPositionRegion.height() * relativeStackPosition.mVerticalOffsetPercent) + allowableStackPositionRegion.top));
                    bubbleStackView.mRelativeStackPositionBeforeRotation = null;
                }
                if (bubbleStackView.mIsExpanded) {
                    BubbleViewProvider bubbleViewProvider = bubbleStackView.mExpandedBubble;
                    if (bubbleViewProvider != null && bubbleViewProvider.getExpandedView() != null && bubbleStackView.mExpandedBubble.getExpandedView().mTaskView != null) {
                        TaskViewTaskController taskViewTaskController = bubbleStackView.mExpandedBubble.getExpandedView().mTaskViewTaskController;
                        Executor executor = taskViewTaskController.mShellExecutor;
                        ShellExecutor shellExecutor2 = executor instanceof ShellExecutor ? (ShellExecutor) executor : null;
                        if (shellExecutor2 == null) {
                            Log.w("TaskViewTaskController", "startRecreateSurface: failed, cannot find shellExecutor");
                        } else if (!taskViewTaskController.mWaitingForSurfaceCreated) {
                            taskViewTaskController.mWaitingForSurfaceCreated = true;
                            ((HandlerExecutor) shellExecutor2).executeDelayed(2000L, taskViewTaskController.mRecreateSurfaceTimeoutRunnable);
                            Log.d("TaskViewTaskController", "startRecreateSurface: " + taskViewTaskController);
                        }
                    }
                    bubbleStackView.updateOverflowVisibility();
                    bubbleStackView.updatePointerPosition();
                    bubbleStackView.mExpandedAnimationController.expandFromStack(new BubbleStackView$$ExternalSyntheticLambda3(bubbleStackView, 5));
                    PointF expandedBubbleXY = bubbleStackView.mPositioner.getExpandedBubbleXY(bubbleStackView.getBubbleIndex(bubbleStackView.mExpandedBubble), bubbleStackView.getState());
                    BubblePositioner bubblePositioner = bubbleStackView.mPositioner;
                    float expandedViewY = bubblePositioner.getExpandedViewY(bubbleStackView.mExpandedBubble, bubblePositioner.showBubblesVertically() ? expandedBubbleXY.y : expandedBubbleXY.x);
                    bubbleStackView.mExpandedViewContainer.setTranslationX(bubbleStackView.getInitialTranslationX());
                    bubbleStackView.mExpandedViewContainer.setTranslationY(expandedViewY);
                    bubbleStackView.mExpandedViewContainer.setAlpha(1.0f);
                }
                bubbleStackView.removeOnLayoutChangeListener(bubbleStackView.mOrientationChangedListener);
                bubbleStackView.onDisplaySizeChanged();
            }
        };
        final float dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.dismiss_circle_small) / getResources().getDimensionPixelSize(R.dimen.dismiss_circle_size);
        ValueAnimator ofFloat3 = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.mDismissBubbleAnimator = ofFloat3;
        ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(dimensionPixelSize3) { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda10
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                bubbleStackView.getClass();
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                DismissView dismissView = bubbleStackView.mDismissView;
                if (dismissView != null) {
                    dismissView.circle.setScaleX(1.15f);
                    bubbleStackView.mDismissView.circle.setScaleY(1.15f);
                    bubbleStackView.mDismissView.circle.setBackgroundResource(R.drawable.bubble_delete_ic_drop);
                }
                View view3 = bubbleStackView.mViewBeingDismissed;
                if (view3 != null) {
                    view3.setAlpha(Math.max(floatValue, 0.7f));
                }
            }
        });
        setOnClickListener(new BubbleStackView$$ExternalSyntheticLambda8(this, 1));
        ViewPropertyAnimator animate = animate();
        TimeInterpolator timeInterpolator = Interpolators.PANEL_CLOSE_ACCELERATED;
        animate.setInterpolator(timeInterpolator).setDuration(320L);
        ofFloat2.setDuration(150L);
        ofFloat2.setInterpolator(timeInterpolator);
        ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.bubbles.BubbleStackView.13
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                BubbleViewProvider bubbleViewProvider = BubbleStackView.this.mExpandedBubble;
                if (bubbleViewProvider == null || bubbleViewProvider.getExpandedView() == null) {
                    return;
                }
                BubbleStackView bubbleStackView = BubbleStackView.this;
                if (bubbleStackView.mExpandedViewTemporarilyHidden) {
                    return;
                }
                TaskView taskView = bubbleStackView.mExpandedBubble.getExpandedView().mTaskView;
                if (taskView != null) {
                    taskView.setZOrderedOnTop(true, true);
                }
                BubbleExpandedView expandedView = BubbleStackView.this.mExpandedBubble.getExpandedView();
                expandedView.mIsAnimating = false;
                expandedView.setContentVisibility(expandedView.mIsContentVisible);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                BubbleViewProvider bubbleViewProvider = BubbleStackView.this.mExpandedBubble;
                if (bubbleViewProvider == null || bubbleViewProvider.getExpandedView() == null) {
                    return;
                }
                TaskView taskView = BubbleStackView.this.mExpandedBubble.getExpandedView().mTaskView;
                if (taskView != null) {
                    taskView.setZOrderedOnTop(true, true);
                }
                BubbleStackView.this.mExpandedBubble.getExpandedView().mIsAnimating = true;
            }
        });
        final int i2 = 0;
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda4
            public final /* synthetic */ BubbleStackView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i2) {
                    case 0:
                        BubbleStackView bubbleStackView = this.f$0;
                        BubbleViewProvider bubbleViewProvider = bubbleStackView.mExpandedBubble;
                        if (bubbleViewProvider != null && bubbleViewProvider.getExpandedView() != null) {
                            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                            bubbleStackView.mExpandedBubble.getExpandedView().setContentAlpha(floatValue);
                            BubbleExpandedView expandedView = bubbleStackView.mExpandedBubble.getExpandedView();
                            expandedView.mPointerView.setAlpha(floatValue);
                            expandedView.setAlpha(floatValue);
                            break;
                        }
                        break;
                    default:
                        BubbleStackView bubbleStackView2 = this.f$0;
                        if (!bubbleStackView2.mExpandedViewTemporarilyHidden) {
                            bubbleStackView2.mAnimatingOutSurfaceView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            break;
                        }
                        break;
                }
            }
        });
        ofFloat.setDuration(150L);
        ofFloat.setInterpolator(timeInterpolator);
        final int i3 = 1;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda4
            public final /* synthetic */ BubbleStackView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i3) {
                    case 0:
                        BubbleStackView bubbleStackView = this.f$0;
                        BubbleViewProvider bubbleViewProvider = bubbleStackView.mExpandedBubble;
                        if (bubbleViewProvider != null && bubbleViewProvider.getExpandedView() != null) {
                            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                            bubbleStackView.mExpandedBubble.getExpandedView().setContentAlpha(floatValue);
                            BubbleExpandedView expandedView = bubbleStackView.mExpandedBubble.getExpandedView();
                            expandedView.mPointerView.setAlpha(floatValue);
                            expandedView.setAlpha(floatValue);
                            break;
                        }
                        break;
                    default:
                        BubbleStackView bubbleStackView2 = this.f$0;
                        if (!bubbleStackView2.mExpandedViewTemporarilyHidden) {
                            bubbleStackView2.mAnimatingOutSurfaceView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            break;
                        }
                        break;
                }
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.bubbles.BubbleStackView.14
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                bubbleStackView.getClass();
            }
        });
        this.mBubbleSALogger = bubbleController.mBubbleSALogger;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0049, code lost:
    
        if ((android.provider.Settings.Secure.getInt(((android.widget.FrameLayout) r7).mContext.getContentResolver(), "force_show_bubbles_user_education", 0) != 0) != false) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void addBubble(Bubble bubble) {
        boolean z;
        boolean z2;
        boolean z3 = getBubbleCount() == 0;
        if (z3) {
            BubbleViewProvider bubbleViewProvider = this.mBubbleData.mSelectedBubble;
            if (bubbleViewProvider instanceof Bubble) {
                if (((Bubble) bubbleViewProvider).mShortcutInfo != null) {
                    z = true;
                    if (z) {
                        Context context = ((FrameLayout) this).mContext;
                        if (context.getSharedPreferences(context.getPackageName(), 0).getBoolean("HasSeenBubblesOnboarding", false)) {
                        }
                        z2 = true;
                        if (z2) {
                            this.mStackAnimationController.setStackPosition(this.mPositioner.getRestingPosition());
                        }
                    }
                    z2 = false;
                    if (z2) {
                    }
                }
            }
            z = false;
            if (z) {
            }
            z2 = false;
            if (z2) {
            }
        }
        BadgedImageView badgedImageView = bubble.mIconView;
        if (badgedImageView == null) {
            return;
        }
        PhysicsAnimationLayout physicsAnimationLayout = this.mBubbleContainer;
        int i = this.mPositioner.mBubbleSize;
        physicsAnimationLayout.addViewInternal(badgedImageView, 0, new FrameLayout.LayoutParams(i, i), false);
        if (z3) {
            this.mStackOnLeftOrWillBe = this.mStackAnimationController.isStackOnLeftSide();
        }
        BadgedImageView badgedImageView2 = bubble.mIconView;
        badgedImageView2.mOnLeft = !this.mStackOnLeftOrWillBe;
        badgedImageView2.invalidate();
        bubble.mIconView.setOnClickListener(this.mBubbleClickListener);
        bubble.mIconView.setOnTouchListener(this.mBubbleTouchListener);
        updateBubbleShadows(false);
        animateInFlyoutForBubble(bubble);
        requestUpdate();
        logBubbleEvent(bubble, 1);
    }

    public final void animateDismissBubble(View view, boolean z) {
        this.mViewBeingDismissed = view;
        if (view == null) {
            return;
        }
        if (z) {
            this.mDismissBubbleAnimator.removeAllListeners();
            this.mDismissBubbleAnimator.start();
        } else {
            this.mDismissBubbleAnimator.removeAllListeners();
            this.mDismissBubbleAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.bubbles.BubbleStackView.19
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    super.onAnimationCancel(animator);
                    BubbleStackView bubbleStackView = BubbleStackView.this;
                    PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                    bubbleStackView.resetDismissAnimator();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    BubbleStackView bubbleStackView = BubbleStackView.this;
                    PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                    bubbleStackView.resetDismissAnimator();
                }
            });
            this.mDismissBubbleAnimator.end();
        }
    }

    public final void animateFlyoutCollapsed(float f, boolean z) {
        float f2;
        boolean isStackOnLeftSide = this.mStackAnimationController.isStackOnLeftSide();
        this.mFlyoutTransitionSpring.mSpring.setStiffness(this.mBubbleToExpandAfterFlyoutCollapse != null ? 1500.0f : 200.0f);
        SpringAnimation springAnimation = this.mFlyoutTransitionSpring;
        springAnimation.mValue = this.mFlyoutDragDeltaX;
        springAnimation.mStartValueIsSet = true;
        springAnimation.mVelocity = f;
        if (z) {
            int width = this.mFlyout.getWidth();
            if (isStackOnLeftSide) {
                width = -width;
            }
            f2 = width;
        } else {
            f2 = 0.0f;
        }
        springAnimation.animateToFinalPosition(f2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void animateInFlyoutForBubble(Bubble bubble) {
        Object[] objArr;
        Bubble.FlyoutMessage flyoutMessage = bubble.mFlyoutMessage;
        BadgedImageView badgedImageView = bubble.mIconView;
        int i = 1;
        Object[] objArr2 = 0;
        if (flyoutMessage == null || flyoutMessage.message == null || !bubble.showFlyout() || isStackEduVisible() || this.mIsExpanded || this.mIsExpansionAnimating || this.mIsGestureInProgress || this.mBubbleToExpandAfterFlyoutCollapse != null || badgedImageView == null) {
            if (badgedImageView != null && this.mFlyout.getVisibility() != 0) {
                badgedImageView.removeDotSuppressionFlag(BadgedImageView.SuppressionFlag.FLYOUT_VISIBLE);
            }
            objArr = false;
        } else {
            objArr = true;
        }
        if (objArr == true) {
            this.mFlyoutDragDeltaX = 0.0f;
            this.mFlyout.removeCallbacks(this.mAnimateInFlyout);
            BubbleStackView$$ExternalSyntheticLambda1 bubbleStackView$$ExternalSyntheticLambda1 = this.mAfterFlyoutHidden;
            if (bubbleStackView$$ExternalSyntheticLambda1 != null) {
                bubbleStackView$$ExternalSyntheticLambda1.run();
                this.mAfterFlyoutHidden = null;
            }
            this.mAfterFlyoutHidden = new BubbleStackView$$ExternalSyntheticLambda1(this, bubble, objArr2 == true ? 1 : 0);
            BadgedImageView badgedImageView2 = bubble.mIconView;
            BadgedImageView.SuppressionFlag suppressionFlag = BadgedImageView.SuppressionFlag.FLYOUT_VISIBLE;
            if (badgedImageView2.mDotSuppressionFlags.add(suppressionFlag)) {
                badgedImageView2.updateDotVisibility(suppressionFlag == BadgedImageView.SuppressionFlag.BEHIND_STACK);
            }
            post(new BubbleStackView$$ExternalSyntheticLambda1(this, bubble, i));
            this.mFlyout.removeCallbacks(this.mHideFlyout);
            this.mFlyout.postDelayed(this.mHideFlyout, 5000L);
            logBubbleEvent(bubble, 16);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean z = false;
        if (motionEvent.getAction() != 0 && motionEvent.getActionIndex() != this.mPointerIndexDown) {
            return false;
        }
        if (motionEvent.getAction() == 0) {
            this.mPointerIndexDown = motionEvent.getActionIndex();
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            this.mPointerIndexDown = -1;
        }
        boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        if (!dispatchTouchEvent && !this.mIsExpanded && this.mIsGestureInProgress) {
            onTouch(this, motionEvent);
            dispatchTouchEvent = true;
        }
        if (motionEvent.getAction() != 1 && motionEvent.getAction() != 3) {
            z = true;
        }
        this.mIsGestureInProgress = z;
        return dispatchTouchEvent;
    }

    public final int getBubbleCount() {
        return this.mBubbleContainer.getChildCount() - 1;
    }

    public final int getBubbleIndex(BubbleViewProvider bubbleViewProvider) {
        if (bubbleViewProvider == null) {
            return 0;
        }
        return this.mBubbleContainer.indexOfChild(bubbleViewProvider.getIconView$1());
    }

    public BubbleViewProvider getExpandedBubble() {
        return this.mExpandedBubble;
    }

    public final float getInitialTranslationX() {
        if (this.mPositioner.showBubblesVertically()) {
            return this.mBubbleStackOff * (this.mStackAnimationController.isStackOnLeftSide() ? 1.0f : -1.0f);
        }
        return 0.0f;
    }

    public final StackViewState getState() {
        this.mStackViewState.numberOfBubbles = this.mBubbleContainer.getChildCount();
        this.mStackViewState.selectedIndex = getBubbleIndex(this.mExpandedBubble);
        StackViewState stackViewState = this.mStackViewState;
        stackViewState.onLeft = this.mStackOnLeftOrWillBe;
        return stackViewState;
    }

    public final void hideCurrentInputMethod() {
        BubblePositioner bubblePositioner = this.mPositioner;
        bubblePositioner.mImeVisible = false;
        bubblePositioner.mImeHeight = 0;
        BubbleController bubbleController = this.mBubbleController;
        bubbleController.getClass();
        try {
            bubbleController.mBarService.hideCurrentInputMethodForBubbles();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final void hideFlyoutImmediate() {
        this.mFlyout.removeCallbacks(this.mAnimateInFlyout);
        BubbleStackView$$ExternalSyntheticLambda1 bubbleStackView$$ExternalSyntheticLambda1 = this.mAfterFlyoutHidden;
        if (bubbleStackView$$ExternalSyntheticLambda1 != null) {
            bubbleStackView$$ExternalSyntheticLambda1.run();
            this.mAfterFlyoutHidden = null;
        }
        this.mFlyout.removeCallbacks(this.mAnimateInFlyout);
        this.mFlyout.removeCallbacks(this.mHideFlyout);
        BubbleFlyoutView bubbleFlyoutView = this.mFlyout;
        Runnable runnable = bubbleFlyoutView.mOnHide;
        if (runnable != null) {
            runnable.run();
            bubbleFlyoutView.mOnHide = null;
        }
        bubbleFlyoutView.setVisibility(8);
    }

    public boolean isManageEduVisible() {
        ManageEducationView manageEducationView = this.mManageEduView;
        return manageEducationView != null && manageEducationView.getVisibility() == 0;
    }

    public boolean isManageMenuDontBubbleVisible() {
        return false;
    }

    public boolean isManageMenuSettingsVisible() {
        return false;
    }

    public boolean isStackEduVisible() {
        StackEducationView stackEducationView = this.mStackEduView;
        return stackEducationView != null && stackEducationView.getVisibility() == 0;
    }

    public final void logBubbleEvent(BubbleViewProvider bubbleViewProvider, int i) {
        String str = (bubbleViewProvider == null || !(bubbleViewProvider instanceof Bubble)) ? "null" : ((Bubble) bubbleViewProvider).mPackageName;
        BubbleData bubbleData = this.mBubbleData;
        int bubbleCount = getBubbleCount();
        int bubbleIndex = getBubbleIndex(bubbleViewProvider);
        BigDecimal bigDecimal = new BigDecimal(this.mPositioner.mPositionRect.width() > 0 ? this.mStackAnimationController.mStackPosition.x / r1 : 0.0f);
        RoundingMode roundingMode = RoundingMode.CEILING;
        float floatValue = bigDecimal.setScale(4, RoundingMode.HALF_UP).floatValue();
        BigDecimal bigDecimal2 = new BigDecimal(this.mPositioner.mPositionRect.height() > 0 ? this.mStackAnimationController.mStackPosition.y / r1 : 0.0f);
        RoundingMode roundingMode2 = RoundingMode.CEILING;
        float floatValue2 = bigDecimal2.setScale(4, RoundingMode.HALF_UP).floatValue();
        BubbleLogger bubbleLogger = bubbleData.mLogger;
        if (bubbleViewProvider == null) {
            bubbleLogger.getClass();
            FrameworkStatsLog.write(149, str, (String) null, 0, 0, bubbleCount, i, floatValue, floatValue2, false, false, false);
        } else if (bubbleViewProvider.getKey().equals("Overflow")) {
            if (i == 3) {
                bubbleLogger.mUiEventLogger.log(BubbleLogger.Event.BUBBLE_OVERFLOW_SELECTED, bubbleData.mCurrentUserId, str);
            }
        } else {
            Bubble bubble = (Bubble) bubbleViewProvider;
            bubbleLogger.getClass();
            FrameworkStatsLog.write(149, str, bubble.mChannelId, bubble.mNotificationId, bubbleIndex, bubbleCount, i, floatValue, floatValue2, bubble.showInShade(), false, false);
        }
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        addOnLayoutChangeListener(this.mOrientationChangedListener);
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mPositioner.update();
        getViewTreeObserver().addOnComputeInternalInsetsListener(this);
        getViewTreeObserver().addOnDrawListener(this.mSystemGestureExcludeUpdater);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0035, code lost:
    
        if ((r1.mShowingOverflow && r1.mExpanded) != false) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        internalInsetsInfo.setTouchableInsets(3);
        this.mTempRect.setEmpty();
        Rect rect = this.mTempRect;
        if (isStackEduVisible()) {
            rect.set(0, 0, getWidth(), getHeight());
        } else {
            if (this.mIsExpanded) {
                this.mBubbleContainer.getBoundsOnScreen(rect);
                int i = rect.bottom;
                BubblePositioner bubblePositioner = this.mPositioner;
                rect.bottom = i - (bubblePositioner.mImeVisible ? bubblePositioner.mImeHeight : 0);
            } else {
                if (getBubbleCount() <= 0) {
                    BubbleData bubbleData = this.mBubbleData;
                }
                this.mBubbleContainer.getChildAt(0).getBoundsOnScreen(rect);
                int i2 = rect.top;
                int i3 = this.mBubbleTouchPadding;
                rect.top = i2 - i3;
                rect.left -= i3;
                rect.right += i3;
                rect.bottom += i3;
            }
            if (this.mFlyout.getVisibility() == 0) {
                Rect rect2 = new Rect();
                this.mFlyout.getBoundsOnScreen(rect2);
                rect.union(rect2);
            }
        }
        internalInsetsInfo.touchableRegion.set(this.mTempRect);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnPreDrawListener(this.mViewUpdater);
        getViewTreeObserver().removeOnDrawListener(this.mSystemGestureExcludeUpdater);
        getViewTreeObserver().removeOnComputeInternalInsetsListener(this);
        BubbleOverflow bubbleOverflow = this.mBubbleOverflow;
        if (bubbleOverflow != null) {
            BubbleExpandedView bubbleExpandedView = bubbleOverflow.expandedView;
            if (bubbleExpandedView != null) {
                bubbleExpandedView.cleanUpExpandedState();
            }
            bubbleOverflow.expandedView = null;
        }
        removeOnLayoutChangeListener(this.mOrientationChangedListener);
    }

    public final void onDisplaySizeChanged() {
        updateOverflow();
        setUpFlyout();
        setUpDismissView();
        updateUserEdu();
        this.mBubbleSize = this.mPositioner.mBubbleSize;
        for (Bubble bubble : this.mBubbleData.getBubbles()) {
            BadgedImageView badgedImageView = bubble.mIconView;
            if (badgedImageView == null) {
                Log.d("Bubbles", "Display size changed. Icon null: " + bubble);
            } else {
                int i = this.mBubbleSize;
                badgedImageView.setLayoutParams(new FrameLayout.LayoutParams(i, i));
            }
        }
        BadgedImageView iconView$1 = this.mBubbleOverflow.getIconView$1();
        int i2 = this.mBubbleSize;
        iconView$1.setLayoutParams(new FrameLayout.LayoutParams(i2, i2));
        this.mExpandedAnimationController.updateResources();
        StackAnimationController stackAnimationController = this.mStackAnimationController;
        PhysicsAnimationLayout physicsAnimationLayout = stackAnimationController.mLayout;
        if (physicsAnimationLayout != null) {
            stackAnimationController.mBubblePaddingTop = physicsAnimationLayout.getContext().getResources().getDimensionPixelSize(R.dimen.bubble_padding_top);
        }
        this.mDismissView.getClass();
        this.mMagneticTarget.getClass();
        if (!isStackEduVisible()) {
            StackAnimationController stackAnimationController2 = this.mStackAnimationController;
            RelativeStackPosition relativeStackPosition = new RelativeStackPosition(this.mPositioner.getRestingPosition(), this.mPositioner.getAllowableStackPositionRegion(getBubbleCount()));
            RectF allowableStackPositionRegion = stackAnimationController2.mPositioner.getAllowableStackPositionRegion(stackAnimationController2.getBubbleCount());
            stackAnimationController2.setStackPosition(new PointF(relativeStackPosition.mOnLeft ? allowableStackPositionRegion.left : allowableStackPositionRegion.right, (allowableStackPositionRegion.height() * relativeStackPosition.mVerticalOffsetPercent) + allowableStackPositionRegion.top));
        }
        if (this.mIsExpanded) {
            updateExpandedView();
        }
    }

    public final void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        setupLocalMenu(accessibilityNodeInfo);
    }

    public final void onOrientationChanged() {
        this.mRelativeStackPositionBeforeRotation = new RelativeStackPosition(this.mPositioner.getRestingPosition(), this.mPositioner.getAllowableStackPositionRegion(getBubbleCount()));
        addOnLayoutChangeListener(this.mOrientationChangedListener);
        hideFlyoutImmediate();
    }

    public final boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        RectF allowableStackPositionRegion = this.mPositioner.getAllowableStackPositionRegion(getBubbleCount());
        if (i == 1048576) {
            this.mBubbleData.dismissAll(6);
            announceForAccessibility(getResources().getString(R.string.accessibility_bubble_dismissed));
            return true;
        }
        if (i == 524288) {
            this.mBubbleData.setExpanded(false);
            return true;
        }
        if (i == 262144) {
            this.mBubbleData.setExpanded(true);
            return true;
        }
        if (i == R.id.action_move_top_left) {
            this.mStackAnimationController.springStack(allowableStackPositionRegion.left, allowableStackPositionRegion.top, 700.0f);
            return true;
        }
        if (i == R.id.action_move_top_right) {
            this.mStackAnimationController.springStack(allowableStackPositionRegion.right, allowableStackPositionRegion.top, 700.0f);
            return true;
        }
        if (i == R.id.action_move_bottom_left) {
            this.mStackAnimationController.springStack(allowableStackPositionRegion.left, allowableStackPositionRegion.bottom, 700.0f);
            return true;
        }
        if (i != R.id.action_move_bottom_right) {
            return false;
        }
        this.mStackAnimationController.springStack(allowableStackPositionRegion.right, allowableStackPositionRegion.bottom, 700.0f);
        return true;
    }

    public final void requestUpdate() {
        if (this.mViewUpdatedRequested || this.mIsExpansionAnimating) {
            return;
        }
        this.mViewUpdatedRequested = true;
        getViewTreeObserver().addOnPreDrawListener(this.mViewUpdater);
        invalidate();
    }

    public final void resetDismissAnimator() {
        this.mDismissBubbleAnimator.removeAllListeners();
        this.mDismissBubbleAnimator.cancel();
        View view = this.mViewBeingDismissed;
        if (view != null) {
            view.setAlpha(1.0f);
            this.mViewBeingDismissed = null;
        }
        DismissView dismissView = this.mDismissView;
        if (dismissView != null) {
            dismissView.circle.setScaleX(1.0f);
            this.mDismissView.circle.setScaleY(1.0f);
            this.mDismissView.resetCircle();
        }
    }

    public final void setBubbleSuppressed(Bubble bubble, boolean z) {
        if (z) {
            this.mBubbleContainer.removeViewAt(getBubbleIndex(bubble));
            updateExpandedView();
            return;
        }
        BadgedImageView badgedImageView = bubble.mIconView;
        if (badgedImageView == null) {
            return;
        }
        if (badgedImageView.getParent() != null) {
            Log.e("Bubbles", "Bubble is already added to parent. Can't unsuppress: " + bubble);
            return;
        }
        int indexOf = this.mBubbleData.getBubbles().indexOf(bubble);
        PhysicsAnimationLayout physicsAnimationLayout = this.mBubbleContainer;
        BadgedImageView badgedImageView2 = bubble.mIconView;
        int i = this.mPositioner.mBubbleSize;
        physicsAnimationLayout.addViewInternal(badgedImageView2, indexOf, new FrameLayout.LayoutParams(i, i), false);
        updateBubbleShadows(false);
        requestUpdate();
    }

    public final void setFlyoutStateForDragLength(float f) {
        if (this.mFlyout.getWidth() <= 0) {
            return;
        }
        boolean isStackOnLeftSide = this.mStackAnimationController.isStackOnLeftSide();
        this.mFlyoutDragDeltaX = f;
        if (isStackOnLeftSide) {
            f = -f;
        }
        float width = f / this.mFlyout.getWidth();
        float f2 = 0.0f;
        this.mFlyout.setCollapsePercent(Math.min(1.0f, Math.max(0.0f, width)));
        if (width < 0.0f || width > 1.0f) {
            boolean z = false;
            boolean z2 = width > 1.0f;
            if ((isStackOnLeftSide && width > 1.0f) || (!isStackOnLeftSide && width < 0.0f)) {
                z = true;
            }
            f2 = (this.mFlyout.getWidth() / (8.0f / (z2 ? 2 : 1))) * (z2 ? width - 1.0f : width * (-1.0f)) * (z ? -1 : 1);
        }
        BubbleFlyoutView bubbleFlyoutView = this.mFlyout;
        bubbleFlyoutView.setTranslationX(bubbleFlyoutView.mRestingTranslationX + f2);
    }

    public final void setSelectedBubble(final BubbleViewProvider bubbleViewProvider) {
        if (bubbleViewProvider == null) {
            this.mBubbleData.mShowingOverflow = false;
            return;
        }
        if (this.mExpandedBubble == bubbleViewProvider) {
            return;
        }
        if (bubbleViewProvider.getKey().equals("Overflow")) {
            this.mBubbleData.mShowingOverflow = true;
        } else {
            this.mBubbleData.mShowingOverflow = false;
        }
        if (this.mIsExpanded && this.mIsExpansionAnimating) {
            ((HandlerExecutor) this.mMainExecutor).removeCallbacks(this.mDelayedAnimation);
            this.mIsExpansionAnimating = false;
            this.mIsBubbleSwitchAnimating = false;
            PhysicsAnimator.getInstance(this.mAnimatingOutSurfaceView).cancel();
            PhysicsAnimator.getInstance(this.mExpandedViewContainerMatrix).cancel();
            this.mExpandedViewContainer.setAnimationMatrix(null);
        }
        showManageMenu(false);
        final BubbleViewProvider bubbleViewProvider2 = this.mExpandedBubble;
        this.mExpandedBubble = bubbleViewProvider;
        if (this.mIsExpanded) {
            hideCurrentInputMethod();
            this.mExpandedViewContainer.setAlpha(0.0f);
            SurfaceSynchronizer surfaceSynchronizer = this.mSurfaceSynchronizer;
            Runnable runnable = new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    BubbleStackView bubbleStackView = BubbleStackView.this;
                    BubbleViewProvider bubbleViewProvider3 = bubbleViewProvider2;
                    BubbleViewProvider bubbleViewProvider4 = bubbleViewProvider;
                    if (bubbleViewProvider3 != null) {
                        bubbleStackView.getClass();
                        bubbleViewProvider3.setTaskViewVisibility();
                    }
                    bubbleStackView.updateExpandedBubble();
                    bubbleStackView.requestUpdate();
                    bubbleStackView.logBubbleEvent(bubbleViewProvider3, 4);
                    bubbleStackView.logBubbleEvent(bubbleViewProvider4, 3);
                    Bubbles.BubbleExpandListener bubbleExpandListener = bubbleStackView.mExpandListener;
                    if (bubbleExpandListener != null && bubbleViewProvider3 != null) {
                        bubbleExpandListener.onBubbleExpandChanged(bubbleViewProvider3.getKey(), false);
                    }
                    Bubbles.BubbleExpandListener bubbleExpandListener2 = bubbleStackView.mExpandListener;
                    if (bubbleExpandListener2 == null || bubbleViewProvider4 == null) {
                        return;
                    }
                    bubbleExpandListener2.onBubbleExpandChanged(bubbleViewProvider4.getKey(), true);
                }
            };
            C38071 c38071 = (C38071) surfaceSynchronizer;
            c38071.getClass();
            Choreographer.getInstance().postFrameCallback(new C38071.AnonymousClass1(c38071, runnable));
        }
    }

    public final void setUpDismissView() {
        DismissView dismissView = this.mDismissView;
        if (dismissView != null) {
            removeView(dismissView);
        }
        this.mDismissView = new DismissView(getContext());
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.bubble_elevation);
        addView(this.mDismissView);
        this.mDismissView.setElevation(dimensionPixelSize);
        this.mMagneticTarget = new MagnetizedObject.MagneticTarget(this.mDismissView.circle, Settings.Secure.getInt(getContext().getContentResolver(), "bubble_dismiss_radius", this.mBubbleSize * 2));
        this.mBubbleContainer.bringToFront();
    }

    public final void setUpFlyout() {
        BubbleFlyoutView bubbleFlyoutView = this.mFlyout;
        if (bubbleFlyoutView != null) {
            removeView(bubbleFlyoutView);
        }
        BubbleFlyoutView bubbleFlyoutView2 = new BubbleFlyoutView(getContext(), this.mPositioner);
        this.mFlyout = bubbleFlyoutView2;
        bubbleFlyoutView2.setVisibility(8);
        this.mFlyout.setOnClickListener(this.mFlyoutClickListener);
        this.mFlyout.setOnTouchListener(this.mFlyoutTouchListener);
        addView(this.mFlyout, new FrameLayout.LayoutParams(-2, -2));
    }

    public final void setupLocalMenu(AccessibilityNodeInfo accessibilityNodeInfo) {
        Resources resources = ((FrameLayout) this).mContext.getResources();
        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_top_left, resources.getString(R.string.bubble_accessibility_action_move_top_left)));
        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_top_right, resources.getString(R.string.bubble_accessibility_action_move_top_right)));
        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_bottom_left, resources.getString(R.string.bubble_accessibility_action_move_bottom_left)));
        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_bottom_right, resources.getString(R.string.bubble_accessibility_action_move_bottom_right)));
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_DISMISS);
        if (this.mIsExpanded) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
        } else {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
        }
    }

    public final void showScrim(boolean z) {
        final Runnable runnable = null;
        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.android.wm.shell.bubbles.BubbleStackView.18
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                BubbleStackView.this.mScrimAnimating = false;
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                BubbleStackView.this.mScrimAnimating = true;
            }
        };
        if (z) {
            this.mScrim.animate().setInterpolator(Interpolators.ALPHA_IN).alpha(0.6f).setListener(animatorListenerAdapter).start();
        } else {
            this.mScrim.animate().alpha(0.0f).setInterpolator(Interpolators.ALPHA_OUT).setListener(animatorListenerAdapter).start();
        }
    }

    public final void startMonitoringSwipeUpGesture() {
        stopMonitoringSwipeUpGestureInternal();
        if (((FrameLayout) this).mContext.getResources().getInteger(android.R.integer.config_notificationsBatteryFullARGB) == 2) {
            final BubblesNavBarGestureTracker bubblesNavBarGestureTracker = new BubblesNavBarGestureTracker(((FrameLayout) this).mContext, this.mPositioner);
            this.mBubblesNavBarGestureTracker = bubblesNavBarGestureTracker;
            C38249 c38249 = this.mSwipeUpListener;
            BubblesNavBarInputEventReceiver bubblesNavBarInputEventReceiver = bubblesNavBarGestureTracker.mInputEventReceiver;
            if (bubblesNavBarInputEventReceiver != null) {
                bubblesNavBarInputEventReceiver.dispose();
                bubblesNavBarGestureTracker.mInputEventReceiver = null;
            }
            InputMonitor inputMonitor = bubblesNavBarGestureTracker.mInputMonitor;
            if (inputMonitor != null) {
                inputMonitor.dispose();
                bubblesNavBarGestureTracker.mInputMonitor = null;
            }
            Context context = bubblesNavBarGestureTracker.mContext;
            InputMonitor monitorGestureInput = ((InputManager) context.getSystemService(InputManager.class)).monitorGestureInput("bubbles-gesture", context.getDisplayId());
            bubblesNavBarGestureTracker.mInputMonitor = monitorGestureInput;
            bubblesNavBarGestureTracker.mInputEventReceiver = new BubblesNavBarInputEventReceiver(monitorGestureInput.getInputChannel(), Choreographer.getInstance(), new BubblesNavBarMotionEventHandler(context, bubblesNavBarGestureTracker.mPositioner, new Runnable() { // from class: com.android.wm.shell.bubbles.BubblesNavBarGestureTracker$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    InputMonitor inputMonitor2 = BubblesNavBarGestureTracker.this.mInputMonitor;
                    if (inputMonitor2 != null) {
                        inputMonitor2.pilferPointers();
                    }
                }
            }, c38249));
            setOnTouchListener(this.mContainerSwipeListener);
        }
    }

    public final void stopMonitoringSwipeUpGestureInternal() {
        BubblesNavBarGestureTracker bubblesNavBarGestureTracker = this.mBubblesNavBarGestureTracker;
        if (bubblesNavBarGestureTracker != null) {
            BubblesNavBarInputEventReceiver bubblesNavBarInputEventReceiver = bubblesNavBarGestureTracker.mInputEventReceiver;
            if (bubblesNavBarInputEventReceiver != null) {
                bubblesNavBarInputEventReceiver.dispose();
                bubblesNavBarGestureTracker.mInputEventReceiver = null;
            }
            InputMonitor inputMonitor = bubblesNavBarGestureTracker.mInputMonitor;
            if (inputMonitor != null) {
                inputMonitor.dispose();
                bubblesNavBarGestureTracker.mInputMonitor = null;
            }
            this.mBubblesNavBarGestureTracker = null;
            setOnTouchListener(null);
        }
    }

    public final void updateBadges(boolean z) {
        int bubbleCount = getBubbleCount();
        for (int i = 0; i < bubbleCount; i++) {
            BadgedImageView badgedImageView = (BadgedImageView) this.mBubbleContainer.getChildAt(i);
            if (this.mIsExpanded) {
                badgedImageView.showDotAndBadge(this.mPositioner.showBubblesVertically() && !this.mStackOnLeftOrWillBe);
            } else if (z) {
                if (i == 0) {
                    badgedImageView.showDotAndBadge(!this.mStackOnLeftOrWillBe);
                } else {
                    badgedImageView.hideDotAndBadge(!this.mStackOnLeftOrWillBe);
                }
            }
        }
    }

    public final void updateBubbleShadows(boolean z) {
        int bubbleCount = getBubbleCount();
        for (int i = 0; i < bubbleCount; i++) {
            float f = (this.mPositioner.mMaxBubbles * this.mBubbleElevation) - i;
            BadgedImageView badgedImageView = (BadgedImageView) this.mBubbleContainer.getChildAt(i);
            MagnetizedObject magnetizedObject = this.mMagnetizedObject;
            boolean z2 = magnetizedObject != null && magnetizedObject.underlyingObject.equals(badgedImageView);
            if (z || z2) {
                badgedImageView.setZ(f);
            } else {
                if (i >= 2) {
                    f = 0.0f;
                }
                badgedImageView.setZ(f);
            }
        }
    }

    public final void updateBubblesAcessibillityStates() {
        final BadgedImageView iconView$1;
        final BadgedImageView badgedImageView;
        int i = 0;
        while (true) {
            if (i >= this.mBubbleData.getBubbles().size()) {
                break;
            }
            Bubble bubble = i > 0 ? this.mBubbleData.getBubbles().get(i - 1) : null;
            BadgedImageView badgedImageView2 = this.mBubbleData.getBubbles().get(i).mIconView;
            if (badgedImageView2 != null) {
                if (this.mIsExpanded) {
                    badgedImageView2.setImportantForAccessibility(1);
                    iconView$1 = bubble != null ? bubble.mIconView : null;
                    if (iconView$1 != null) {
                        badgedImageView2.setAccessibilityDelegate(new View.AccessibilityDelegate(this) { // from class: com.android.wm.shell.bubbles.BubbleStackView.16
                            @Override // android.view.View.AccessibilityDelegate
                            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                                accessibilityNodeInfo.setTraversalAfter(iconView$1);
                            }
                        });
                    }
                } else {
                    badgedImageView2.setImportantForAccessibility(i != 0 ? 2 : 1);
                }
            }
            i++;
        }
        if (this.mIsExpanded) {
            BubbleOverflow bubbleOverflow = this.mBubbleOverflow;
            iconView$1 = bubbleOverflow != null ? bubbleOverflow.getIconView$1() : null;
            if (iconView$1 == null || this.mBubbleData.getBubbles().isEmpty() || (badgedImageView = this.mBubbleData.getBubbles().get(this.mBubbleData.getBubbles().size() - 1).mIconView) == null) {
                return;
            }
            iconView$1.setAccessibilityDelegate(new View.AccessibilityDelegate(this) { // from class: com.android.wm.shell.bubbles.BubbleStackView.17
                @Override // android.view.View.AccessibilityDelegate
                public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                    accessibilityNodeInfo.setTraversalAfter(badgedImageView);
                }
            });
        }
    }

    public final void updateContentDescription() {
        if (this.mBubbleData.getBubbles().isEmpty()) {
            return;
        }
        for (int i = 0; i < this.mBubbleData.getBubbles().size(); i++) {
            Bubble bubble = this.mBubbleData.getBubbles().get(i);
            String str = bubble.mAppName;
            String str2 = bubble.mTitle;
            if (str2 == null) {
                str2 = getResources().getString(R.string.notification_bubble_title);
            }
            BadgedImageView badgedImageView = bubble.mIconView;
            if (badgedImageView != null) {
                if (this.mIsExpanded || i > 0) {
                    badgedImageView.setContentDescription(getResources().getString(R.string.bubble_content_description_single, str2, str));
                } else {
                    bubble.mIconView.setContentDescription(getResources().getString(R.string.bubble_content_description_stack, str2, str, Integer.valueOf(this.mBubbleContainer.getChildCount() - 1)));
                }
            }
        }
    }

    public final void updateExpandedBubble() {
        BubbleViewProvider bubbleViewProvider;
        this.mExpandedViewContainer.removeAllViews();
        if (!this.mIsExpanded || (bubbleViewProvider = this.mExpandedBubble) == null || bubbleViewProvider.getExpandedView() == null) {
            return;
        }
        BubbleExpandedView expandedView = this.mExpandedBubble.getExpandedView();
        expandedView.setContentVisibility(false);
        boolean z = !this.mIsExpansionAnimating;
        expandedView.mIsAnimating = z;
        if (!z) {
            expandedView.setContentVisibility(expandedView.mIsContentVisible);
        }
        this.mExpandedViewContainerMatrix.setScaleX(0.0f);
        this.mExpandedViewContainerMatrix.setScaleY(0.0f);
        this.mExpandedViewContainerMatrix.setTranslate(0.0f, 0.0f);
        this.mExpandedViewContainer.setVisibility(4);
        this.mExpandedViewContainer.setAlpha(0.0f);
        this.mExpandedViewContainer.addView(expandedView);
        BubbleStackView$$ExternalSyntheticLambda8 bubbleStackView$$ExternalSyntheticLambda8 = new BubbleStackView$$ExternalSyntheticLambda8(this, 2);
        expandedView.mManageClickListener = bubbleStackView$$ExternalSyntheticLambda8;
        expandedView.mManageButton.setOnClickListener(bubbleStackView$$ExternalSyntheticLambda8);
        if (this.mIsExpansionAnimating) {
            return;
        }
        this.mIsBubbleSwitchAnimating = true;
        SurfaceSynchronizer surfaceSynchronizer = this.mSurfaceSynchronizer;
        BubbleStackView$$ExternalSyntheticLambda3 bubbleStackView$$ExternalSyntheticLambda3 = new BubbleStackView$$ExternalSyntheticLambda3(this, 7);
        C38071 c38071 = (C38071) surfaceSynchronizer;
        c38071.getClass();
        Choreographer.getInstance().postFrameCallback(new C38071.AnonymousClass1(c38071, bubbleStackView$$ExternalSyntheticLambda3));
    }

    public final void updateExpandedView() {
        BubbleViewProvider bubbleViewProvider = this.mExpandedBubble;
        if (bubbleViewProvider != null) {
            "Overflow".equals(bubbleViewProvider.getKey());
        }
        int[] expandedViewContainerPadding = this.mPositioner.getExpandedViewContainerPadding(this.mStackAnimationController.isStackOnLeftSide());
        this.mExpandedViewContainer.setPadding(expandedViewContainerPadding[0], expandedViewContainerPadding[1], expandedViewContainerPadding[2], expandedViewContainerPadding[3]);
        if (this.mIsExpansionAnimating) {
            this.mExpandedViewContainer.setVisibility(this.mIsExpanded ? 0 : 8);
        }
        BubbleViewProvider bubbleViewProvider2 = this.mExpandedBubble;
        if (bubbleViewProvider2 != null && bubbleViewProvider2.getExpandedView() != null) {
            PointF expandedBubbleXY = this.mPositioner.getExpandedBubbleXY(getBubbleIndex(this.mExpandedBubble), getState());
            FrameLayout frameLayout = this.mExpandedViewContainer;
            BubblePositioner bubblePositioner = this.mPositioner;
            frameLayout.setTranslationY(bubblePositioner.getExpandedViewY(this.mExpandedBubble, bubblePositioner.showBubblesVertically() ? expandedBubbleXY.y : expandedBubbleXY.x));
            this.mExpandedViewContainer.setTranslationX(getInitialTranslationX());
            this.mExpandedBubble.getExpandedView().updateView(this.mExpandedViewContainer.getLocationOnScreen());
            updatePointerPosition();
        }
        this.mStackOnLeftOrWillBe = this.mStackAnimationController.isStackOnLeftSide();
    }

    public final void updateOverflow() {
        BubbleOverflow bubbleOverflow = this.mBubbleOverflow;
        bubbleOverflow.updateResources();
        BubbleExpandedView bubbleExpandedView = bubbleOverflow.expandedView;
        if (bubbleExpandedView != null) {
            bubbleExpandedView.applyThemeAttrs();
        }
        BadgedImageView iconView$1 = bubbleOverflow.getIconView$1();
        if (iconView$1 != null) {
            iconView$1.mBubbleIcon.setImageResource(R.drawable.sec_bubble_tw_ic_fad_add_mtrl);
        }
        bubbleOverflow.updateBtnTheme();
        this.mBubbleContainer.reorderView(this.mBubbleOverflow.getIconView$1(), this.mBubbleContainer.getChildCount() - 1);
        updateOverflowVisibility();
    }

    public final void updateOverflowVisibility() {
        BubbleOverflow bubbleOverflow = this.mBubbleOverflow;
        int i = 0;
        if (!this.mIsExpanded) {
            BubbleData bubbleData = this.mBubbleData;
            if (!(bubbleData.mShowingOverflow && bubbleData.mExpanded)) {
                i = 8;
            }
        }
        BadgedImageView badgedImageView = bubbleOverflow.overflowBtn;
        if (badgedImageView == null) {
            return;
        }
        badgedImageView.setVisibility(i);
    }

    public final void updatePointerPosition() {
        int bubbleIndex;
        BubbleViewProvider bubbleViewProvider = this.mExpandedBubble;
        if (bubbleViewProvider == null || bubbleViewProvider.getExpandedView() == null || (bubbleIndex = getBubbleIndex(this.mExpandedBubble)) == -1) {
            return;
        }
        PointF expandedBubbleXY = this.mPositioner.getExpandedBubbleXY(bubbleIndex, getState());
        this.mExpandedBubble.getExpandedView().setPointerPosition(this.mPositioner.showBubblesVertically() ? expandedBubbleXY.y : expandedBubbleXY.x, this.mStackOnLeftOrWillBe);
    }

    public final void updateTemporarilyInvisibleAnimation(boolean z) {
        removeCallbacks(this.mAnimateTemporarilyInvisibleImmediate);
        if (this.mIsDraggingStack) {
            return;
        }
        postDelayed(this.mAnimateTemporarilyInvisibleImmediate, (!(this.mTemporarilyInvisible && this.mFlyout.getVisibility() != 0) || z) ? 0L : 1000L);
    }

    public final void updateUserEdu() {
        if (isStackEduVisible()) {
            StackEducationView stackEducationView = this.mStackEduView;
            if (!stackEducationView.isHiding) {
                removeView(stackEducationView);
                StackEducationView stackEducationView2 = new StackEducationView(((FrameLayout) this).mContext, this.mPositioner, this.mBubbleController);
                this.mStackEduView = stackEducationView2;
                addView(stackEducationView2);
                this.mBubbleContainer.bringToFront();
                this.mStackAnimationController.setStackPosition(this.mPositioner.getDefaultStartPosition());
                final StackEducationView stackEducationView3 = this.mStackEduView;
                final PointF defaultStartPosition = this.mPositioner.getDefaultStartPosition();
                stackEducationView3.isHiding = false;
                if (stackEducationView3.getVisibility() != 0) {
                    stackEducationView3.controller.updateWindowFlagsForBackpress(true);
                    ViewGroup.LayoutParams layoutParams = stackEducationView3.getLayoutParams();
                    stackEducationView3.positioner.getClass();
                    layoutParams.width = stackEducationView3.positioner.isLandscape() ? stackEducationView3.getContext().getResources().getDimensionPixelSize(R.dimen.bubbles_user_education_width) : -1;
                    final int dimensionPixelSize = stackEducationView3.getContext().getResources().getDimensionPixelSize(R.dimen.bubble_user_education_stack_padding);
                    stackEducationView3.setAlpha(0.0f);
                    stackEducationView3.setVisibility(0);
                    stackEducationView3.post(new Runnable() { // from class: com.android.wm.shell.bubbles.StackEducationView$show$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            StackEducationView.this.requestFocus();
                            View view = (View) StackEducationView.this.view$delegate.getValue();
                            StackEducationView stackEducationView4 = StackEducationView.this;
                            int i = dimensionPixelSize;
                            PointF pointF = defaultStartPosition;
                            if (view.getResources().getConfiguration().getLayoutDirection() == 0) {
                                view.setPadding(stackEducationView4.positioner.mBubbleSize + i, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
                            } else {
                                view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), stackEducationView4.positioner.mBubbleSize + i, view.getPaddingBottom());
                                stackEducationView4.positioner.getClass();
                                if (stackEducationView4.positioner.isLandscape()) {
                                    view.setTranslationX((stackEducationView4.positioner.mScreenRect.right - view.getWidth()) - i);
                                } else {
                                    view.setTranslationX(0.0f);
                                }
                            }
                            view.setTranslationY((pointF.y + (stackEducationView4.positioner.mBubbleSize / 2)) - (view.getHeight() / 2));
                            StackEducationView.this.animate().setDuration(StackEducationView.this.ANIMATE_DURATION).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).alpha(1.0f);
                        }
                    });
                    stackEducationView3.getContext().getSharedPreferences(stackEducationView3.getContext().getPackageName(), 0).edit().putBoolean("HasSeenBubblesOnboarding", true).apply();
                }
            }
        }
        if (isManageEduVisible()) {
            removeView(this.mManageEduView);
            ManageEducationView manageEducationView = new ManageEducationView(((FrameLayout) this).mContext, this.mPositioner);
            this.mManageEduView = manageEducationView;
            addView(manageEducationView);
            this.mManageEduView.show(this.mExpandedBubble.getExpandedView());
        }
    }

    public final void updateZOrder() {
        int bubbleCount = getBubbleCount();
        int i = 0;
        while (i < bubbleCount) {
            ((BadgedImageView) this.mBubbleContainer.getChildAt(i)).setZ(i < 2 ? (this.mPositioner.mMaxBubbles * this.mBubbleElevation) - i : 0.0f);
            i++;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RelativeStackPosition {
        public final boolean mOnLeft;
        public final float mVerticalOffsetPercent;

        public RelativeStackPosition(boolean z, float f) {
            this.mOnLeft = z;
            this.mVerticalOffsetPercent = Math.max(0.0f, Math.min(1.0f, f));
        }

        public RelativeStackPosition(PointF pointF, RectF rectF) {
            this.mOnLeft = pointF.x < rectF.width() / 2.0f;
            this.mVerticalOffsetPercent = Math.max(0.0f, Math.min(1.0f, (pointF.y - rectF.top) / rectF.height()));
        }
    }

    public void showManageMenu(boolean z) {
    }
}
