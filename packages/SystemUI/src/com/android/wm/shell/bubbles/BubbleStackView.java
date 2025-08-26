package com.android.wm.shell.bubbles;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Property;
import android.view.Choreographer;
import android.view.InputMonitor;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.internal.util.FrameworkStatsLog;
import com.android.systemui.R;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wmshell.BubblesManager$$ExternalSyntheticLambda1;
import com.android.wm.shell.bubbles.BadgedImageView;
import com.android.wm.shell.bubbles.Bubble;
import com.android.wm.shell.bubbles.BubbleLogger;
import com.android.wm.shell.bubbles.BubblePositioner;
import com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda1;
import com.android.wm.shell.bubbles.BubblesNavBarMotionEventHandler;
import com.android.wm.shell.bubbles.animation.AnimatableScaleMatrix;
import com.android.wm.shell.bubbles.animation.ExpandedAnimationController;
import com.android.wm.shell.bubbles.animation.ExpandedViewAnimationControllerImpl;
import com.android.wm.shell.bubbles.animation.FlingToDismissUtils;
import com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout;
import com.android.wm.shell.bubbles.animation.StackAnimationController;
import com.android.wm.shell.bubbles.animation.StackAnimationController$$ExternalSyntheticLambda3;
import com.android.wm.shell.bubbles.bar.BubbleBarExpandedView;
import com.android.wm.shell.common.FloatingContentCoordinator;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.shared.bubbles.ContextUtils;
import com.android.wm.shell.shared.bubbles.DeviceConfig;
import com.android.wm.shell.shared.bubbles.DismissView;
import com.android.wm.shell.shared.bubbles.RelativeTouchListener;
import com.android.wm.shell.shared.magnetictarget.MagnetizedObject;
import com.android.wm.shell.taskview.TaskView;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;

/* loaded from: classes3.dex */
public class BubbleStackView extends FrameLayout implements ViewTreeObserver.OnComputeInternalInsetsListener {
    static final int FLYOUT_HIDE_AFTER = 5000;
    public BubbleStackView$$ExternalSyntheticLambda3 mAfterFlyoutHidden;
    public final BubbleStackView$$ExternalSyntheticLambda10 mAfterFlyoutTransitionSpring;
    public BubbleStackView$$ExternalSyntheticLambda5 mAnimateInFlyout;
    public final BubbleStackView$$ExternalSyntheticLambda5 mAnimateStashedState;
    public final BubbleStackView$$ExternalSyntheticLambda5 mAnimateTemporarilyInvisibleImmediate;
    public final ValueAnimator mAnimatingOutSurfaceAlphaAnimator;
    public final FrameLayout mAnimatingOutSurfaceContainer;
    public final SurfaceView mAnimatingOutSurfaceView;
    public final AnonymousClass6 mBubbleClickListener;
    public final PhysicsAnimationLayout mBubbleContainer;
    public final BubbleData mBubbleData;
    public final int mBubbleElevation;
    public final BubbleOverflow mBubbleOverflow;
    public final BubblesManager$$ExternalSyntheticLambda1 mBubbleSALogger;
    public int mBubbleSize;
    public BubbleViewProvider mBubbleToExpandAfterFlyoutCollapse;
    public final AnonymousClass7 mBubbleTouchListener;
    public final int mBubbleTouchPadding;
    public BubblesNavBarGestureTracker mBubblesNavBarGestureTracker;
    public final AnonymousClass8 mContainerSwipeListener;
    public BubbleStackView$$ExternalSyntheticLambda37 mDelayedAnimation;
    public final ValueAnimator mDismissBubbleAnimator;
    public DismissView mDismissView;
    public BubbleController$$ExternalSyntheticLambda5 mExpandListener;
    public final ExpandedAnimationController mExpandedAnimationController;
    public BubbleViewProvider mExpandedBubble;
    public final ValueAnimator mExpandedViewAlphaAnimator;
    public final ExpandedViewAnimationControllerImpl mExpandedViewAnimationController;
    public final FrameLayout mExpandedViewContainer;
    public final AnimatableScaleMatrix mExpandedViewContainerMatrix;
    public final int mExpandedViewPadding;
    public boolean mExpandedViewTemporarilyHidden;
    public BubbleFlyoutView mFlyout;
    public final AnonymousClass10 mFlyoutClickListener;
    public final AnonymousClass3 mFlyoutCollapseProperty;
    public float mFlyoutDragDeltaX;
    public final AnonymousClass11 mFlyoutTouchListener;
    public final SpringAnimation mFlyoutTransitionSpring;
    public final BubbleStackView$$ExternalSyntheticLambda5 mHideFlyout;
    public final AnonymousClass4 mIndividualBubbleMagnetListener;
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
    public final BubbleStackViewManager mManager;
    public final BubbleStackView$$ExternalSyntheticLambda16 mOrientationChangedListener;
    public int mPointerIndexDown;
    public final BubblePositioner mPositioner;
    public RelativeStackPosition mRelativeStackPositionBeforeRotation;
    public boolean mRemovingLastBubbleWhileExpanded;
    public final PhysicsAnimator.SpringConfig mScaleInSpringConfig;
    public final PhysicsAnimator.SpringConfig mScaleOutSpringConfig;
    public final View mScrim;
    public ViewPropertyAnimator mScrimAnimation;
    public boolean mSensitiveNotificationProtectionActive;
    public boolean mShouldReorderBubblesAfterGestureCompletes;
    public final boolean mShowingOverflow;
    public final StackAnimationController mStackAnimationController;
    public StackEducationView mStackEduView;
    public BubbleStackView$$ExternalSyntheticLambda18 mStackEducationViewManager;
    public final AnonymousClass5 mStackMagnetListener;
    public boolean mStackOnLeftOrWillBe;
    public final StackViewState mStackViewState;
    public final SurfaceSynchronizer mSurfaceSynchronizer;
    public final AnonymousClass9 mSwipeUpListener;
    public final BubbleStackView$$ExternalSyntheticLambda9 mSystemGestureExcludeUpdater;
    public final List mSystemGestureExclusionRects;
    public final Bubbles$SysuiProxy$Provider mSysuiProxyProvider;
    public final Rect mTempRect;
    public boolean mTemporarilyInvisible;
    public View mViewBeingDismissed;
    public boolean mViewUpdatedRequested;
    public final AnonymousClass2 mViewUpdater;
    public static final PhysicsAnimator.SpringConfig FLYOUT_IME_ANIMATION_SPRING_CONFIG = new PhysicsAnimator.SpringConfig(200.0f, 0.9f);
    public static final AnonymousClass1 DEFAULT_SURFACE_SYNCHRONIZER = new SurfaceSynchronizer() { // from class: com.android.wm.shell.bubbles.BubbleStackView.1

        /* renamed from: com.android.wm.shell.bubbles.BubbleStackView$1$1, reason: invalid class name and collision with other inner class name */
        public class ChoreographerFrameCallbackC06551 implements Choreographer.FrameCallback {
            public int mFrameWait = 2;
            public final /* synthetic */ Runnable val$callback;

            public ChoreographerFrameCallbackC06551(AnonymousClass1 anonymousClass1, Runnable runnable) {
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

    /* renamed from: com.android.wm.shell.bubbles.BubbleStackView$4, reason: invalid class name */
    public class AnonymousClass4 implements MagnetizedObject.MagnetListener {
        public AnonymousClass4() {
        }

        @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject.MagnetListener
        public final void onReleasedInTarget(MagnetizedObject magnetizedObject) {
            Object obj = magnetizedObject.underlyingObject;
            boolean z = obj instanceof View;
            BubbleStackView bubbleStackView = BubbleStackView.this;
            if (z) {
                View view = (View) obj;
                ExpandedAnimationController expandedAnimationController = bubbleStackView.mExpandedAnimationController;
                float height = bubbleStackView.mDismissView.getHeight();
                BubbleStackView$$ExternalSyntheticLambda1 bubbleStackView$$ExternalSyntheticLambda1 = new BubbleStackView$$ExternalSyntheticLambda1(2, this, view);
                if (view == null) {
                    expandedAnimationController.getClass();
                } else {
                    PhysicsAnimationLayout.PhysicsPropertyAnimator physicsPropertyAnimatorAnimationForChild = expandedAnimationController.animationForChild(view);
                    physicsPropertyAnimatorAnimationForChild.mStiffness = 10000.0f;
                    physicsPropertyAnimatorAnimationForChild.property(DynamicAnimation.SCALE_X, 0.0f, new Runnable[0]);
                    physicsPropertyAnimatorAnimationForChild.property(DynamicAnimation.SCALE_Y, 0.0f, new Runnable[0]);
                    physicsPropertyAnimatorAnimationForChild.translationY(view.getTranslationY() + height, new Runnable[0]);
                    physicsPropertyAnimatorAnimationForChild.property(DynamicAnimation.ALPHA, 0.0f, bubbleStackView$$ExternalSyntheticLambda1);
                    physicsPropertyAnimatorAnimationForChild.start(new Runnable[0]);
                    expandedAnimationController.updateBubblePositions();
                }
            }
            bubbleStackView.mDismissView.hide();
        }

        @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject.MagnetListener
        public final void onStuckToTarget(MagnetizedObject magnetizedObject) {
            Object obj = magnetizedObject.underlyingObject;
            if (obj instanceof View) {
                PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                BubbleStackView.this.animateDismissBubble((View) obj, true);
            }
        }

        @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject.MagnetListener
        public final void onUnstuckFromTarget(MagnetizedObject.MagneticTarget magneticTarget, MagnetizedObject magnetizedObject, float f, float f2, boolean z) {
            Object obj = magnetizedObject.underlyingObject;
            if (obj instanceof View) {
                View view = (View) obj;
                PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                BubbleStackView bubbleStackView = BubbleStackView.this;
                bubbleStackView.animateDismissBubble(view, false);
                if (!z) {
                    bubbleStackView.mExpandedAnimationController.mSpringToTouchOnNextMotionEvent = true;
                } else {
                    bubbleStackView.mExpandedAnimationController.snapBubbleBack(view, f, f2);
                    bubbleStackView.mDismissView.hide();
                }
            }
        }
    }

    /* renamed from: com.android.wm.shell.bubbles.BubbleStackView$5, reason: invalid class name */
    public class AnonymousClass5 implements MagnetizedObject.MagnetListener {
        public AnonymousClass5() {
        }

        @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject.MagnetListener
        public final void onReleasedInTarget(MagnetizedObject magnetizedObject) {
            BubbleStackView bubbleStackView = BubbleStackView.this;
            final StackAnimationController stackAnimationController = bubbleStackView.mStackAnimationController;
            final float height = bubbleStackView.mDismissView.getHeight();
            BubbleStackView$$ExternalSyntheticLambda5 bubbleStackView$$ExternalSyntheticLambda5 = new BubbleStackView$$ExternalSyntheticLambda5(this, 17);
            stackAnimationController.getClass();
            stackAnimationController.animationsForChildrenFromIndex(false, new PhysicsAnimationLayout.PhysicsAnimationController.ChildAnimationConfigurator() { // from class: com.android.wm.shell.bubbles.animation.StackAnimationController$$ExternalSyntheticLambda7
                @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController.ChildAnimationConfigurator
                public final void configureAnimationForChildAtIndex(int i, PhysicsAnimationLayout.PhysicsPropertyAnimator physicsPropertyAnimator) {
                    StackAnimationController stackAnimationController2 = stackAnimationController;
                    stackAnimationController2.getClass();
                    physicsPropertyAnimator.property(DynamicAnimation.SCALE_X, 0.0f, new Runnable[0]);
                    physicsPropertyAnimator.property(DynamicAnimation.SCALE_Y, 0.0f, new Runnable[0]);
                    physicsPropertyAnimator.property(DynamicAnimation.ALPHA, 0.0f, new Runnable[0]);
                    physicsPropertyAnimator.translationY(stackAnimationController2.mLayout.getChildAt(i).getTranslationY() + height, new Runnable[0]);
                    physicsPropertyAnimator.mStiffness = 10000.0f;
                }
            }).startAll(new Runnable[]{bubbleStackView$$ExternalSyntheticLambda5});
            bubbleStackView.mDismissView.hide();
        }

        @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject.MagnetListener
        public final void onStuckToTarget(MagnetizedObject magnetizedObject) {
            BubbleStackView bubbleStackView = BubbleStackView.this;
            bubbleStackView.animateDismissBubble(bubbleStackView.mBubbleContainer, true);
        }

        @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject.MagnetListener
        public final void onUnstuckFromTarget(MagnetizedObject.MagneticTarget magneticTarget, MagnetizedObject magnetizedObject, float f, float f2, boolean z) {
            BubbleStackView bubbleStackView = BubbleStackView.this;
            bubbleStackView.animateDismissBubble(bubbleStackView.mBubbleContainer, false);
            if (!z) {
                bubbleStackView.mStackAnimationController.mSpringToTouchOnNextMotionEvent = true;
                return;
            }
            StackAnimationController stackAnimationController = bubbleStackView.mStackAnimationController;
            stackAnimationController.flingStackThenSpringToEdge(stackAnimationController.mStackPosition.x, f, f2);
            bubbleStackView.mDismissView.hide();
        }
    }

    /* renamed from: com.android.wm.shell.bubbles.BubbleStackView$6, reason: invalid class name */
    public class AnonymousClass6 implements View.OnClickListener {
        public AnonymousClass6() {
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x003a  */
        @Override // android.view.View.OnClickListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onClick(View view) {
            final Bubble bubbleWithPredicate;
            boolean z;
            BubbleStackView bubbleStackView = BubbleStackView.this;
            PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
            bubbleStackView.mIsDraggingStack = false;
            bubbleStackView.mMagnetizedObject = null;
            if (bubbleStackView.mIsExpansionAnimating || bubbleStackView.mIsBubbleSwitchAnimating || (bubbleWithPredicate = BubbleData.getBubbleWithPredicate(bubbleStackView.mBubbleData.mBubbles, new BubbleData$$ExternalSyntheticLambda7(view, 2))) == null) {
                return;
            }
            BubbleViewProvider bubbleViewProvider = BubbleStackView.this.mExpandedBubble;
            if (bubbleViewProvider != null) {
                z = bubbleWithPredicate.mKey.equals(bubbleViewProvider.getKey());
            }
            BubbleStackView bubbleStackView2 = BubbleStackView.this;
            if (bubbleStackView2.mIsExpanded) {
                ExpandedAnimationController expandedAnimationController = bubbleStackView2.mExpandedAnimationController;
                expandedAnimationController.mBubbleDraggedOutEnough = false;
                expandedAnimationController.mMagnetizedBubbleDraggingOut = null;
                expandedAnimationController.updateBubblePositions();
            }
            BubbleStackView bubbleStackView3 = BubbleStackView.this;
            if (!bubbleStackView3.mIsExpanded || z) {
                bubbleStackView3.getClass();
                bubbleStackView3.mBubbleData.setExpanded(!r0.mExpanded);
                BubbleStackView.this.getClass();
            } else {
                BubbleData bubbleData = bubbleStackView3.mBubbleData;
                if (bubbleWithPredicate != bubbleData.mSelectedBubble) {
                    bubbleData.setSelectedBubbleInternal(bubbleWithPredicate);
                    bubbleData.dispatchPendingChanges();
                } else {
                    bubbleStackView3.setSelectedBubble(bubbleWithPredicate);
                }
            }
            Optional.ofNullable(bubbleWithPredicate).ifPresent(new Consumer() { // from class: com.android.wm.shell.bubbles.BubbleStackView$6$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    BubbleStackView.this.mBubbleSALogger.sendEventCDLog("QPNE0100", SystemUIAnalytics.QPNE_KEY_APP, bubbleWithPredicate.mPackageName);
                }
            });
        }
    }

    /* renamed from: com.android.wm.shell.bubbles.BubbleStackView$9, reason: invalid class name */
    public class AnonymousClass9 implements BubblesNavBarMotionEventHandler.MotionEventListener {
        public AnonymousClass9() {
        }

        public final void onMove(float f) {
            int iRound;
            BubbleExpandedView bubbleExpandedView;
            BubbleStackView bubbleStackView = BubbleStackView.this;
            if (bubbleStackView.isManageEduVisible() || bubbleStackView.isStackEduVisible()) {
                return;
            }
            float f2 = -Math.min(f, 0.0f);
            ExpandedViewAnimationControllerImpl expandedViewAnimationControllerImpl = bubbleStackView.mExpandedViewAnimationController;
            float f3 = (int) f2;
            BubbleExpandedView bubbleExpandedView2 = expandedViewAnimationControllerImpl.mExpandedView;
            if (bubbleExpandedView2 != null) {
                int contentHeight = bubbleExpandedView2.getContentHeight();
                if (Float.compare(f3, 0.0f) == 0) {
                    iRound = 0;
                } else {
                    float f4 = contentHeight;
                    float f5 = f3 / f4;
                    float fAbs = f5 / Math.abs(f5);
                    float fAbs2 = Math.abs(f5) - 1.0f;
                    float fAbs3 = ((fAbs2 * fAbs2 * fAbs2) + 1.0f) * fAbs;
                    if (Math.abs(fAbs3) >= 1.0f) {
                        fAbs3 /= Math.abs(fAbs3);
                    }
                    iRound = Math.round(fAbs3 * 0.07f * f4);
                }
                expandedViewAnimationControllerImpl.mDraggedAmount = iRound;
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -8974127699338313944L, 6, Double.valueOf(f3), Long.valueOf(iRound));
                }
                expandedViewAnimationControllerImpl.setCollapsedAmount(expandedViewAnimationControllerImpl.mDraggedAmount);
                if (!expandedViewAnimationControllerImpl.mNotifiedAboutThreshold && (bubbleExpandedView = expandedViewAnimationControllerImpl.mExpandedView) != null && expandedViewAnimationControllerImpl.mDraggedAmount > bubbleExpandedView.getContentHeight() * 0.02f) {
                    expandedViewAnimationControllerImpl.mNotifiedAboutThreshold = true;
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -287698628829432196L, 0, null);
                    }
                    BubbleExpandedView bubbleExpandedView3 = expandedViewAnimationControllerImpl.mExpandedView;
                    if (bubbleExpandedView3 != null) {
                        bubbleExpandedView3.performHapticFeedback(11);
                    }
                }
            }
            if (bubbleStackView.mScrimAnimation == null) {
                View view = bubbleStackView.mScrim;
                PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                view.setAlpha(bubbleStackView.getExpandedView() != null ? Math.max(0.32f - ((f2 / r8.getContentHeight()) * 0.11999999f), 0.2f) : 0.32f);
            }
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
            int i = expandedViewAnimationControllerImpl2.mMinFlingVelocity;
            float f3 = i;
            if (f2 <= f3) {
                float f4 = expandedViewAnimationControllerImpl2.mSwipeUpVelocity;
                if (f4 <= f3) {
                    if (expandedViewAnimationControllerImpl2.mExpandedView == null || expandedViewAnimationControllerImpl2.mDraggedAmount <= r0.getContentHeight() * 0.02f) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -4284314353285188051L, 0, null);
                        }
                    } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -8066306625404848681L, 1, Long.valueOf(expandedViewAnimationControllerImpl2.mDraggedAmount));
                    }
                } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 5757023429076269063L, 6, Double.valueOf(f4), Long.valueOf(i));
                }
                bubbleStackView.mBubbleData.setExpanded(false);
                return;
            }
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 4717174592761508124L, 6, Double.valueOf(f2), Long.valueOf(i));
            }
            bubbleStackView.mExpandedViewAnimationController.animateBackToExpanded();
            if (bubbleStackView.mScrimAnimation == null) {
                bubbleStackView.showScrim(true, null);
            }
        }
    }

    public class StackViewState {
        public int numberOfBubbles;
        public boolean onLeft;
        public int selectedIndex;
    }

    public interface SurfaceSynchronizer {
    }

    /* renamed from: $r8$lambda$H8d4Ep0eU-hn88VAHYBVbgvu58M, reason: not valid java name */
    public static void m3232$r8$lambda$H8d4Ep0eUhn88VAHYBVbgvu58M(BubbleStackView bubbleStackView) {
        BubbleData bubbleData = bubbleStackView.mBubbleData;
        BubbleViewProvider bubbleViewProvider = bubbleData.mSelectedBubble;
        if (bubbleViewProvider == null || !bubbleData.hasBubbleInStackWithKey(bubbleViewProvider.getKey())) {
            return;
        }
        Bubble bubble = (Bubble) bubbleViewProvider;
        Intent settingsIntent = bubble.getSettingsIntent(((FrameLayout) bubbleStackView).mContext);
        bubbleStackView.mBubbleData.setExpanded(false);
        ((FrameLayout) bubbleStackView).mContext.startActivityAsUser(settingsIntent, bubble.mUser);
        bubbleStackView.logBubbleEvent(bubbleViewProvider, 9);
        bubbleStackView.mBubbleSALogger.sendEventCDLog("QPNE0102", SystemUIAnalytics.QPNE_KEY_APP, bubble.mPackageName);
    }

    public static void $r8$lambda$ecS6BCO608AQvEBSbEaZ37rK_08(BubbleStackView bubbleStackView) throws Resources.NotFoundException {
        RelativeStackPosition relativeStackPosition;
        BubblePositioner bubblePositioner = bubbleStackView.mPositioner;
        Context context = ((FrameLayout) bubbleStackView).mContext;
        bubblePositioner.update(DeviceConfig.create(context, (WindowManager) context.getSystemService(WindowManager.class), bubbleStackView.mPositioner.mDeviceConfig.displayCutout));
        bubbleStackView.onDisplaySizeChanged();
        bubbleStackView.mExpandedAnimationController.updateResources();
        ExpandedAnimationController expandedAnimationController = bubbleStackView.mExpandedAnimationController;
        if (expandedAnimationController.mLayout != null) {
            expandedAnimationController.updateBubblePositions();
        }
        bubbleStackView.mStackAnimationController.updateResources();
        bubbleStackView.mBubbleOverflow.updateResources();
        if (!bubbleStackView.isStackEduVisible() && (relativeStackPosition = bubbleStackView.mRelativeStackPositionBeforeRotation) != null) {
            StackAnimationController stackAnimationController = bubbleStackView.mStackAnimationController;
            stackAnimationController.setStackPosition(relativeStackPosition.getAbsolutePositionInRegion(stackAnimationController.mPositioner.getAllowableStackPositionRegion(stackAnimationController.mBubbleCountSupplier.getAsInt())));
            bubbleStackView.mRelativeStackPositionBeforeRotation = null;
        }
        if (bubbleStackView.mIsExpanded) {
            bubbleStackView.hideFlyoutImmediate();
            bubbleStackView.mExpandedViewContainer.setAlpha(0.0f);
            bubbleStackView.updateExpandedView();
            bubbleStackView.updateOverflowVisibility();
            bubbleStackView.updatePointerPosition(false);
            bubbleStackView.requestUpdate();
            bubbleStackView.showManageMenu(false);
            bubbleStackView.mExpandedAnimationController.expandFromStack(new BubbleStackView$$ExternalSyntheticLambda5(bubbleStackView, 5));
            PointF expandedBubbleXY = bubbleStackView.mPositioner.getExpandedBubbleXY(bubbleStackView.getBubbleIndex(bubbleStackView.mExpandedBubble), bubbleStackView.getState());
            BubblePositioner bubblePositioner2 = bubbleStackView.mPositioner;
            float expandedViewY = bubblePositioner2.getExpandedViewY(bubbleStackView.mExpandedBubble, bubblePositioner2.showBubblesVertically() ? expandedBubbleXY.y : expandedBubbleXY.x);
            bubbleStackView.mExpandedViewContainer.setTranslationX(0.0f);
            bubbleStackView.mExpandedViewContainer.setTranslationY(expandedViewY);
            bubbleStackView.mExpandedViewContainer.setAlpha(1.0f);
        }
        bubbleStackView.removeOnLayoutChangeListener(bubbleStackView.mOrientationChangedListener);
    }

    /* renamed from: -$$Nest$mshowExpandedViewIfNeeded, reason: not valid java name */
    public static void m3233$$Nest$mshowExpandedViewIfNeeded(BubbleStackView bubbleStackView) {
        if (bubbleStackView.mExpandedViewTemporarilyHidden) {
            bubbleStackView.mExpandedViewTemporarilyHidden = false;
            AnimatableScaleMatrix animatableScaleMatrix = bubbleStackView.mExpandedViewContainerMatrix;
            PhysicsAnimator.Companion.getClass();
            PhysicsAnimator companion = PhysicsAnimator.Companion.getInstance(animatableScaleMatrix);
            companion.spring(AnimatableScaleMatrix.SCALE_X, 499.99997f, 0.0f, bubbleStackView.mScaleOutSpringConfig);
            companion.spring(AnimatableScaleMatrix.SCALE_Y, 499.99997f, 0.0f, bubbleStackView.mScaleOutSpringConfig);
            companion.updateListeners.add(new BubbleStackView$$ExternalSyntheticLambda23(bubbleStackView, 0));
            companion.start();
            bubbleStackView.mExpandedViewAlphaAnimator.start();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v12, types: [com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda16] */
    /* JADX WARN: Type inference failed for: r3v6, types: [com.android.wm.shell.bubbles.BubbleStackView$2] */
    /* JADX WARN: Type inference failed for: r3v7, types: [com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda9] */
    /* JADX WARN: Type inference failed for: r3v8, types: [androidx.dynamicanimation.animation.FloatPropertyCompat, com.android.wm.shell.bubbles.BubbleStackView$3] */
    /* JADX WARN: Type inference failed for: r3v9, types: [androidx.dynamicanimation.animation.DynamicAnimation$OnAnimationEndListener, com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda10] */
    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.wm.shell.bubbles.BubbleStackView$7] */
    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.wm.shell.bubbles.BubbleStackView$8] */
    /* JADX WARN: Type inference failed for: r5v6, types: [com.android.wm.shell.bubbles.BubbleStackView$10] */
    /* JADX WARN: Type inference failed for: r5v7, types: [com.android.wm.shell.bubbles.BubbleStackView$11] */
    public BubbleStackView(Context context, BubbleController bubbleController, BubbleStackViewManager bubbleStackViewManager, BubblePositioner bubblePositioner, BubbleData bubbleData, SurfaceSynchronizer surfaceSynchronizer, FloatingContentCoordinator floatingContentCoordinator, Bubbles$SysuiProxy$Provider bubbles$SysuiProxy$Provider, ShellExecutor shellExecutor) throws Resources.NotFoundException {
        PhysicsAnimationLayout physicsAnimationLayout;
        super(context);
        this.mScaleInSpringConfig = new PhysicsAnimator.SpringConfig(300.0f, 0.9f);
        this.mScaleOutSpringConfig = new PhysicsAnimator.SpringConfig(900.0f, 1.0f);
        new PhysicsAnimator.SpringConfig(50.0f, 1.0f);
        this.mStackViewState = new StackViewState();
        this.mExpandedViewContainerMatrix = new AnimatableScaleMatrix();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mAnimatingOutSurfaceAlphaAnimator = valueAnimatorOfFloat;
        int i = 0;
        this.mHideFlyout = new BubbleStackView$$ExternalSyntheticLambda5(this, i);
        this.mBubbleToExpandAfterFlyoutCollapse = null;
        int i2 = 1;
        this.mStackOnLeftOrWillBe = true;
        this.mIsGestureInProgress = false;
        this.mTemporarilyInvisible = false;
        this.mIsDraggingStack = false;
        this.mExpandedViewTemporarilyHidden = false;
        this.mRemovingLastBubbleWhileExpanded = false;
        this.mSensitiveNotificationProtectionActive = false;
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mExpandedViewAlphaAnimator = valueAnimatorOfFloat2;
        this.mPointerIndexDown = -1;
        this.mShouldReorderBubblesAfterGestureCompletes = false;
        this.mViewUpdatedRequested = false;
        this.mIsExpansionAnimating = false;
        this.mIsBubbleSwitchAnimating = false;
        this.mTempRect = new Rect();
        this.mSystemGestureExclusionRects = Collections.singletonList(new Rect());
        this.mViewUpdater = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.wm.shell.bubbles.BubbleStackView.2
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                BubbleStackView.this.getViewTreeObserver().removeOnPreDrawListener(BubbleStackView.this.mViewUpdater);
                BubbleStackView.this.updateExpandedView();
                BubbleStackView.this.mViewUpdatedRequested = false;
                return true;
            }
        };
        this.mSystemGestureExcludeUpdater = new ViewTreeObserver.OnDrawListener() { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda9
            @Override // android.view.ViewTreeObserver.OnDrawListener
            public final void onDraw() {
                BubbleStackView bubbleStackView = this.f$0;
                Rect rect = (Rect) bubbleStackView.mSystemGestureExclusionRects.get(0);
                if (bubbleStackView.getBubbleCount() <= 0) {
                    rect.setEmpty();
                    bubbleStackView.mBubbleContainer.setSystemGestureExclusionRects(Collections.EMPTY_LIST);
                } else {
                    View childAt = bubbleStackView.mBubbleContainer.getChildAt(0);
                    rect.set(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom());
                    rect.offset((int) (childAt.getTranslationX() + 0.5f), (int) (childAt.getTranslationY() + 0.5f));
                    bubbleStackView.mBubbleContainer.setSystemGestureExclusionRects(bubbleStackView.mSystemGestureExclusionRects);
                }
            }
        };
        ?? r3 = new FloatPropertyCompat("FlyoutCollapseSpring") { // from class: com.android.wm.shell.bubbles.BubbleStackView.3
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final float getValue(Object obj) {
                return BubbleStackView.this.mFlyoutDragDeltaX;
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final void setValue(Object obj, float f) {
                BubbleStackView.this.setFlyoutStateForDragLength(f);
            }
        };
        this.mFlyoutCollapseProperty = r3;
        SpringAnimation springAnimation = new SpringAnimation(this, (FloatPropertyCompat) r3);
        this.mFlyoutTransitionSpring = springAnimation;
        this.mFlyoutDragDeltaX = 0.0f;
        ?? r32 = new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda10
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
                BubbleStackView bubbleStackView = this.f$0;
                if (bubbleStackView.mFlyoutDragDeltaX == 0.0f) {
                    bubbleStackView.mFlyout.postDelayed(bubbleStackView.mHideFlyout, 5000L);
                    return;
                }
                BubbleFlyoutView bubbleFlyoutView = bubbleStackView.mFlyout;
                BubbleStackView$$ExternalSyntheticLambda3 bubbleStackView$$ExternalSyntheticLambda3 = bubbleFlyoutView.mOnHide;
                if (bubbleStackView$$ExternalSyntheticLambda3 != null) {
                    bubbleStackView$$ExternalSyntheticLambda3.run();
                    bubbleFlyoutView.mOnHide = null;
                }
                bubbleFlyoutView.setVisibility(8);
            }
        };
        this.mAfterFlyoutTransitionSpring = r32;
        this.mIndividualBubbleMagnetListener = new AnonymousClass4();
        this.mStackMagnetListener = new AnonymousClass5();
        this.mBubbleClickListener = new AnonymousClass6();
        this.mBubbleTouchListener = new RelativeTouchListener() { // from class: com.android.wm.shell.bubbles.BubbleStackView.7
            @Override // com.android.wm.shell.shared.bubbles.RelativeTouchListener
            public final void onCancel(View view) {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                bubbleStackView.getClass();
            }

            /* JADX WARN: Type inference failed for: r2v4, types: [com.android.wm.shell.bubbles.animation.ExpandedAnimationController$1, com.android.wm.shell.shared.magnetictarget.MagnetizedObject] */
            /* JADX WARN: Type inference failed for: r3v4, types: [com.android.wm.shell.bubbles.animation.StackAnimationController$2] */
            @Override // com.android.wm.shell.shared.bubbles.RelativeTouchListener
            public final boolean onDown(final View view, MotionEvent motionEvent) {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                if (bubbleStackView.mIsExpansionAnimating) {
                    return true;
                }
                int i3 = 0;
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
                    final Context context2 = expandedAnimationController.mLayout.getContext();
                    final DynamicAnimation.AnonymousClass1 anonymousClass1 = DynamicAnimation.TRANSLATION_X;
                    final DynamicAnimation.AnonymousClass2 anonymousClass2 = DynamicAnimation.TRANSLATION_Y;
                    ?? r2 = new MagnetizedObject(context2, view, anonymousClass1, anonymousClass2) { // from class: com.android.wm.shell.bubbles.animation.ExpandedAnimationController.1
                        @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject
                        public final float getHeight(Object obj) {
                            return ExpandedAnimationController.this.mBubbleSizePx;
                        }

                        @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject
                        public final void getLocationOnScreen(Object obj, int[] iArr) {
                            iArr[0] = (int) view.getTranslationX();
                            iArr[1] = (int) view.getTranslationY();
                        }

                        @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject
                        public final float getWidth(Object obj) {
                            return ExpandedAnimationController.this.mBubbleSizePx;
                        }
                    };
                    expandedAnimationController.mMagnetizedBubbleDraggingOut = r2;
                    r2.flingToTargetMinVelocity = 6000.0f;
                    int i4 = expandedAnimationController.mLayout.getContext().getResources().getDisplayMetrics().widthPixels;
                    ExpandedAnimationController.AnonymousClass1 anonymousClass12 = expandedAnimationController.mMagnetizedBubbleDraggingOut;
                    int i5 = FlingToDismissUtils.$r8$clinit;
                    anonymousClass12.flingToTargetWidthPercent = i4 >= 2000 ? 6.0f : i4 >= 1500 ? 4.5f : 3.0f;
                    BubbleStackView bubbleStackView3 = BubbleStackView.this;
                    bubbleStackView3.mMagnetizedObject = bubbleStackView3.mExpandedAnimationController.mMagnetizedBubbleDraggingOut;
                } else {
                    StackAnimationController stackAnimationController = bubbleStackView2.mStackAnimationController;
                    stackAnimationController.getClass();
                    DynamicAnimation.AnonymousClass1 anonymousClass13 = DynamicAnimation.TRANSLATION_X;
                    stackAnimationController.cancelStackPositionAnimation(anonymousClass13);
                    DynamicAnimation.AnonymousClass2 anonymousClass22 = DynamicAnimation.TRANSLATION_Y;
                    stackAnimationController.cancelStackPositionAnimation(anonymousClass22);
                    stackAnimationController.mLayout.mEndActionForProperty.remove(anonymousClass13);
                    stackAnimationController.mLayout.mEndActionForProperty.remove(anonymousClass22);
                    BubbleStackView bubbleStackView4 = BubbleStackView.this;
                    bubbleStackView4.mBubbleContainer.setActiveController(bubbleStackView4.mStackAnimationController);
                    BubbleStackView.this.hideFlyoutImmediate();
                    BubbleStackView bubbleStackView5 = BubbleStackView.this;
                    final StackAnimationController stackAnimationController2 = bubbleStackView5.mStackAnimationController;
                    if (stackAnimationController2.mMagnetizedStack == null) {
                        final Context context3 = stackAnimationController2.mLayout.getContext();
                        final StackAnimationController.StackPositionProperty stackPositionProperty = new StackAnimationController.StackPositionProperty(stackAnimationController2, anonymousClass13, i3);
                        final StackAnimationController.StackPositionProperty stackPositionProperty2 = new StackAnimationController.StackPositionProperty(stackAnimationController2, anonymousClass22, i3);
                        stackAnimationController2.mMagnetizedStack = new MagnetizedObject(context3, stackAnimationController2, stackPositionProperty, stackPositionProperty2) { // from class: com.android.wm.shell.bubbles.animation.StackAnimationController.2
                            @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject
                            public final float getHeight(Object obj) {
                                return StackAnimationController.this.mBubbleSize;
                            }

                            @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject
                            public final void getLocationOnScreen(Object obj, int[] iArr) {
                                PointF pointF = StackAnimationController.this.mStackPosition;
                                iArr[0] = (int) pointF.x;
                                iArr[1] = (int) pointF.y;
                            }

                            @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject
                            public final float getWidth(Object obj) {
                                return StackAnimationController.this.mBubbleSize;
                            }
                        };
                    }
                    bubbleStackView5.mMagnetizedObject = stackAnimationController2.mMagnetizedStack;
                    BubbleStackView.this.mMagnetizedObject.associatedTargets.clear();
                    BubbleStackView bubbleStackView6 = BubbleStackView.this;
                    MagnetizedObject magnetizedObject = bubbleStackView6.mMagnetizedObject;
                    MagnetizedObject.MagneticTarget magneticTarget = bubbleStackView6.mMagneticTarget;
                    magnetizedObject.associatedTargets.add(magneticTarget);
                    magneticTarget.updateLocationOnScreen();
                    BubbleStackView bubbleStackView7 = BubbleStackView.this;
                    bubbleStackView7.mMagnetizedObject.magnetListener = bubbleStackView7.mStackMagnetListener;
                    bubbleStackView7.mIsDraggingStack = true;
                    bubbleStackView7.updateTemporarilyInvisibleAnimation(false);
                }
                BubbleStackView.this.getClass();
                return true;
            }

            @Override // com.android.wm.shell.shared.bubbles.RelativeTouchListener
            public final void onMove(View view, MotionEvent motionEvent, float f, float f2, float f3, float f4) {
                BubbleViewProvider bubbleViewProvider;
                BubbleStackView bubbleStackView = BubbleStackView.this;
                if (bubbleStackView.mIsExpansionAnimating) {
                    return;
                }
                if (bubbleStackView.mPositioner.mImeVisible) {
                    ((BubbleStackViewManager$Companion$fromBubbleController$1) bubbleStackView.mManager).$controller.hideCurrentInputMethod(null);
                }
                BubbleStackView.this.mDismissView.show();
                BubbleStackView bubbleStackView2 = BubbleStackView.this;
                boolean z = true;
                if (bubbleStackView2.mIsExpanded && (bubbleViewProvider = bubbleStackView2.mExpandedBubble) != null && !bubbleStackView2.mExpandedViewTemporarilyHidden && bubbleViewProvider.getExpandedView() != null) {
                    bubbleStackView2.mExpandedViewTemporarilyHidden = true;
                    AnimatableScaleMatrix animatableScaleMatrix = bubbleStackView2.mExpandedViewContainerMatrix;
                    PhysicsAnimator.Companion.getClass();
                    PhysicsAnimator companion = PhysicsAnimator.Companion.getInstance(animatableScaleMatrix);
                    companion.spring(AnimatableScaleMatrix.SCALE_X, 449.99997f, 0.0f, bubbleStackView2.mScaleOutSpringConfig);
                    companion.spring(AnimatableScaleMatrix.SCALE_Y, 449.99997f, 0.0f, bubbleStackView2.mScaleOutSpringConfig);
                    companion.updateListeners.add(new BubbleStackView$$ExternalSyntheticLambda23(bubbleStackView2, 1));
                    companion.start();
                    bubbleStackView2.mExpandedViewAlphaAnimator.reverse();
                }
                BubbleStackView bubbleStackView3 = BubbleStackView.this;
                float rawX = motionEvent.getRawX();
                float rawY = motionEvent.getRawY();
                DismissView dismissView = bubbleStackView3.mDismissView;
                Rect rect = dismissView.dismissArea;
                boolean z2 = ((float) rect.left) < rawX && ((float) rect.right) > rawX && ((float) rect.top) < rawY && ((float) rect.bottom) > rawY;
                if (dismissView.isBeingEntered != z2) {
                    dismissView.isBeingEntered = z2;
                    bubbleStackView3.animateDismissBubble(view, z2);
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
                    if (stackAnimationController.mSpringToTouchOnNextMotionEvent) {
                        stackAnimationController.springStack(f5, f6, 12000.0f);
                        stackAnimationController.mSpringToTouchOnNextMotionEvent = false;
                        stackAnimationController.mFirstBubbleSpringingToTouch = true;
                    } else if (stackAnimationController.mFirstBubbleSpringingToTouch) {
                        SpringAnimation springAnimation2 = (SpringAnimation) stackAnimationController.mStackPositionAnimations.get(DynamicAnimation.TRANSLATION_X);
                        SpringAnimation springAnimation3 = (SpringAnimation) stackAnimationController.mStackPositionAnimations.get(DynamicAnimation.TRANSLATION_Y);
                        if (springAnimation2.mRunning || springAnimation3.mRunning) {
                            springAnimation2.animateToFinalPosition(f5);
                            springAnimation3.animateToFinalPosition(f6);
                        } else {
                            stackAnimationController.mFirstBubbleSpringingToTouch = false;
                        }
                    }
                    if (stackAnimationController.mFirstBubbleSpringingToTouch || stackAnimationController.isStackStuckToTarget()) {
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
                ExpandedAnimationController.AnonymousClass1 anonymousClass1 = expandedAnimationController.mMagnetizedBubbleDraggingOut;
                if (anonymousClass1 == null) {
                    return;
                }
                if (expandedAnimationController.mSpringToTouchOnNextMotionEvent) {
                    PhysicsAnimationLayout.PhysicsPropertyAnimator physicsPropertyAnimatorAnimationForChild = expandedAnimationController.animationForChild((View) anonymousClass1.underlyingObject);
                    physicsPropertyAnimatorAnimationForChild.mPathAnimator = null;
                    physicsPropertyAnimatorAnimationForChild.property(DynamicAnimation.TRANSLATION_X, f7, new Runnable[0]);
                    physicsPropertyAnimatorAnimationForChild.translationY(f8, new Runnable[0]);
                    physicsPropertyAnimatorAnimationForChild.mStiffness = 10000.0f;
                    physicsPropertyAnimatorAnimationForChild.start(new Runnable[0]);
                    expandedAnimationController.mSpringToTouchOnNextMotionEvent = false;
                    expandedAnimationController.mSpringingBubbleToTouch = true;
                } else if (expandedAnimationController.mSpringingBubbleToTouch) {
                    PhysicsAnimationLayout physicsAnimationLayout2 = expandedAnimationController.mLayout;
                    DynamicAnimation.AnonymousClass1 anonymousClass12 = DynamicAnimation.TRANSLATION_X;
                    DynamicAnimation.ViewProperty[] viewPropertyArr = {anonymousClass12, DynamicAnimation.TRANSLATION_Y};
                    physicsAnimationLayout2.getClass();
                    if (PhysicsAnimationLayout.arePropertiesAnimatingOnView(view, viewPropertyArr)) {
                        PhysicsAnimationLayout.PhysicsPropertyAnimator physicsPropertyAnimatorAnimationForChild2 = expandedAnimationController.animationForChild((View) expandedAnimationController.mMagnetizedBubbleDraggingOut.underlyingObject);
                        physicsPropertyAnimatorAnimationForChild2.mPathAnimator = null;
                        physicsPropertyAnimatorAnimationForChild2.property(anonymousClass12, f7, new Runnable[0]);
                        physicsPropertyAnimatorAnimationForChild2.translationY(f8, new Runnable[0]);
                        physicsPropertyAnimatorAnimationForChild2.mStiffness = 10000.0f;
                        physicsPropertyAnimatorAnimationForChild2.start(new Runnable[0]);
                    } else {
                        expandedAnimationController.mSpringingBubbleToTouch = false;
                    }
                }
                if (!expandedAnimationController.mSpringingBubbleToTouch && !expandedAnimationController.mMagnetizedBubbleDraggingOut.getObjectStuckToTarget()) {
                    view.setTranslationX(f7);
                    view.setTranslationY(f8);
                }
                float expandedViewYTopAligned = expandedAnimationController.mPositioner.getExpandedViewYTopAligned();
                float f9 = expandedAnimationController.mBubbleSizePx;
                if (f8 <= expandedViewYTopAligned + f9 && f8 >= expandedViewYTopAligned - f9) {
                    z = false;
                }
                if (z != expandedAnimationController.mBubbleDraggedOutEnough) {
                    expandedAnimationController.updateBubblePositions();
                    expandedAnimationController.mBubbleDraggedOutEnough = z;
                }
            }

            @Override // com.android.wm.shell.shared.bubbles.RelativeTouchListener
            public final void onUp(View view, float f, float f2, float f3, float f4) {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                if (bubbleStackView.mIsExpansionAnimating) {
                    return;
                }
                bubbleStackView.animateDismissBubble(view, false);
                if (bubbleStackView.mDismissView.isBeingEntered) {
                    BubbleViewProvider bubbleViewProvider = bubbleStackView.mExpandedBubble;
                    bubbleStackView.mExpandedBubble = BubbleData.getBubbleWithPredicate(bubbleStackView.mBubbleData.mBubbles, new BubbleData$$ExternalSyntheticLambda7(view, 2));
                    if (!bubbleStackView.mIsExpanded) {
                        bubbleStackView.mStackMagnetListener.onReleasedInTarget(bubbleStackView.mMagnetizedObject);
                        return;
                    } else {
                        bubbleStackView.mIndividualBubbleMagnetListener.onReleasedInTarget(bubbleStackView.mMagnetizedObject);
                        bubbleStackView.setSelectedBubble(bubbleViewProvider);
                        return;
                    }
                }
                BubbleStackView.m3233$$Nest$mshowExpandedViewIfNeeded(BubbleStackView.this);
                BubbleStackView.this.getClass();
                BubbleStackView bubbleStackView2 = BubbleStackView.this;
                if (bubbleStackView2.mBubbleData.mExpanded) {
                    bubbleStackView2.mExpandedAnimationController.snapBubbleBack(view, f3, f4);
                    BubbleStackView.m3233$$Nest$mshowExpandedViewIfNeeded(BubbleStackView.this);
                } else {
                    boolean z = bubbleStackView2.mStackOnLeftOrWillBe;
                    bubbleStackView2.mStackOnLeftOrWillBe = bubbleStackView2.mStackAnimationController.flingStackThenSpringToEdge(f + f2, f3, f4) <= ((float) (BubbleStackView.this.getWidth() / 2));
                    BubbleStackView bubbleStackView3 = BubbleStackView.this;
                    bubbleStackView3.updateBadges(z != bubbleStackView3.mStackOnLeftOrWillBe);
                    BubbleStackView.this.logBubbleEvent(null, 7);
                }
                BubbleStackView.this.mDismissView.hide();
                BubbleStackView bubbleStackView4 = BubbleStackView.this;
                bubbleStackView4.mIsDraggingStack = false;
                bubbleStackView4.mMagnetizedObject = null;
                bubbleStackView4.updateTemporarilyInvisibleAnimation(false);
                BubbleStackView.this.getClass();
            }
        };
        this.mContainerSwipeListener = new RelativeTouchListener() { // from class: com.android.wm.shell.bubbles.BubbleStackView.8
            @Override // com.android.wm.shell.shared.bubbles.RelativeTouchListener
            public final boolean onDown(View view, MotionEvent motionEvent) {
                AnonymousClass9 anonymousClass9 = BubbleStackView.this.mSwipeUpListener;
                motionEvent.getX();
                motionEvent.getY();
                anonymousClass9.getClass();
                return true;
            }

            @Override // com.android.wm.shell.shared.bubbles.RelativeTouchListener
            public final void onMove(View view, MotionEvent motionEvent, float f, float f2, float f3, float f4) {
                BubbleStackView.this.mSwipeUpListener.onMove(f4);
            }

            @Override // com.android.wm.shell.shared.bubbles.RelativeTouchListener
            public final void onUp(View view, float f, float f2, float f3, float f4) {
                BubbleStackView.this.mSwipeUpListener.onUp(f4);
            }
        };
        this.mSwipeUpListener = new AnonymousClass9();
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
            @Override // com.android.wm.shell.shared.bubbles.RelativeTouchListener
            public final boolean onDown(View view, MotionEvent motionEvent) {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                bubbleStackView.mFlyout.removeCallbacks(bubbleStackView.mHideFlyout);
                return true;
            }

            @Override // com.android.wm.shell.shared.bubbles.RelativeTouchListener
            public final void onMove(View view, MotionEvent motionEvent, float f, float f2, float f3, float f4) {
                BubbleStackView.this.setFlyoutStateForDragLength(f3);
            }

            @Override // com.android.wm.shell.shared.bubbles.RelativeTouchListener
            public final void onUp(View view, float f, float f2, float f3, float f4) {
                boolean zIsStackOnLeftSide = BubbleStackView.this.mStackAnimationController.isStackOnLeftSide();
                boolean z = false;
                boolean z2 = !zIsStackOnLeftSide ? f3 <= 2000.0f : f3 >= -2000.0f;
                boolean z3 = !zIsStackOnLeftSide ? f2 <= ((float) BubbleStackView.this.mFlyout.getWidth()) * 0.25f : f2 >= ((float) (-BubbleStackView.this.mFlyout.getWidth())) * 0.25f;
                boolean z4 = !zIsStackOnLeftSide ? f3 >= 0.0f : f3 <= 0.0f;
                if (z2 || (z3 && !z4)) {
                    z = true;
                }
                BubbleStackView bubbleStackView = BubbleStackView.this;
                bubbleStackView.mFlyout.removeCallbacks(bubbleStackView.mHideFlyout);
                BubbleStackView.this.animateFlyoutCollapsed(f3, z);
                BubbleStackView.this.getClass();
            }
        };
        new PhysicsAnimator.SpringConfig(1500.0f, 0.75f);
        this.mAnimateTemporarilyInvisibleImmediate = new BubbleStackView$$ExternalSyntheticLambda5(this, i2);
        this.mAnimateStashedState = new BubbleStackView$$ExternalSyntheticLambda5(this, 2);
        this.mMainExecutor = shellExecutor;
        this.mManager = bubbleStackViewManager;
        this.mPositioner = bubblePositioner;
        this.mBubbleData = bubbleData;
        this.mSysuiProxyProvider = bubbles$SysuiProxy$Provider;
        Resources resources = getResources();
        this.mBubbleSize = resources.getDimensionPixelSize(R.dimen.bubble_size);
        int i3 = bubblePositioner.mBubbleElevation;
        this.mBubbleElevation = i3;
        this.mBubbleTouchPadding = resources.getDimensionPixelSize(R.dimen.bubble_touch_padding);
        this.mExpandedViewPadding = resources.getDimensionPixelSize(R.dimen.bubble_expanded_view_padding);
        TypedArray typedArrayObtainStyledAttributes = ((FrameLayout) this).mContext.obtainStyledAttributes(new int[]{android.R.attr.dialogCornerRadius});
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.sec_noti_bubble_expand_view_radius);
        typedArrayObtainStyledAttributes.recycle();
        BubbleStackView$$ExternalSyntheticLambda5 bubbleStackView$$ExternalSyntheticLambda5 = new BubbleStackView$$ExternalSyntheticLambda5(this, 3);
        StackAnimationController stackAnimationController = new StackAnimationController(floatingContentCoordinator, new IntSupplier() { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda14
            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                return this.f$0.getBubbleCount();
            }
        }, bubbleStackView$$ExternalSyntheticLambda5, new BubbleStackView$$ExternalSyntheticLambda5(this, 4), bubblePositioner);
        this.mStackAnimationController = stackAnimationController;
        this.mExpandedAnimationController = new ExpandedAnimationController(bubblePositioner, bubbleStackView$$ExternalSyntheticLambda5, this);
        this.mExpandedViewAnimationController = new ExpandedViewAnimationControllerImpl(context, bubblePositioner);
        this.mSurfaceSynchronizer = surfaceSynchronizer != null ? surfaceSynchronizer : DEFAULT_SURFACE_SYNCHRONIZER;
        setLayoutDirection(0);
        PhysicsAnimationLayout physicsAnimationLayout2 = new PhysicsAnimationLayout(context);
        this.mBubbleContainer = physicsAnimationLayout2;
        physicsAnimationLayout2.setActiveController(stackAnimationController);
        float f = i3;
        physicsAnimationLayout2.setElevation(f);
        physicsAnimationLayout2.setClipChildren(false);
        addView(physicsAnimationLayout2, new FrameLayout.LayoutParams(-1, -1));
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
        surfaceView.setCornerRadius(ScreenDecorationsUtils.supportsRoundedCornersOnWindows(((FrameLayout) this).mContext.getResources()) ? dimensionPixelSize : 0.0f);
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
            public final void surfaceChanged(SurfaceHolder surfaceHolder, int i4, int i5, int i6) {
            }
        });
        frameLayout2.addView(surfaceView);
        frameLayout2.setPadding(frameLayout.getPaddingLeft(), frameLayout.getPaddingTop(), frameLayout.getPaddingRight(), frameLayout.getPaddingBottom());
        setUpFlyout();
        SpringForce springForce = new SpringForce();
        springForce.setStiffness(200.0f);
        springForce.setDampingRatio(0.75f);
        springAnimation.mSpring = springForce;
        springAnimation.addEndListener(r32);
        setUpDismissView();
        setClipChildren(false);
        setFocusable(true);
        physicsAnimationLayout2.bringToFront();
        BubbleOverflow bubbleOverflow = bubbleData.mOverflow;
        this.mBubbleOverflow = bubbleOverflow;
        BubblesManager$$ExternalSyntheticLambda1 bubblesManager$$ExternalSyntheticLambda1 = bubbleController.mBubbleSALogger;
        this.mBubbleSALogger = bubblesManager$$ExternalSyntheticLambda1;
        this.mShowingOverflow = true;
        BadgedImageView iconView = bubbleOverflow.getIconView$1();
        if (iconView != null && (physicsAnimationLayout = (PhysicsAnimationLayout) iconView.getParent()) != null) {
            physicsAnimationLayout.removeViewNoAnimation(iconView);
        }
        PhysicsAnimationLayout physicsAnimationLayout3 = this.mBubbleContainer;
        BadgedImageView iconView2 = this.mBubbleOverflow.getIconView$1();
        int childCount = this.mBubbleContainer.getChildCount();
        int i4 = this.mBubbleSize;
        physicsAnimationLayout3.addViewInternal(iconView2, childCount, new FrameLayout.LayoutParams(i4, i4), false);
        updateOverflow();
        this.mBubbleOverflow.getIconView$1().setOnClickListener(new BubbleStackView$$ExternalSyntheticLambda6(this, i2));
        bubblesManager$$ExternalSyntheticLambda1.sendEventCDLog("QPNE0100", SystemUIAnalytics.QPNE_KEY_APP, "overflow bubble");
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
        this.mOrientationChangedListener = new View.OnLayoutChangeListener() { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda16
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view3, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12) throws Resources.NotFoundException {
                BubbleStackView.$r8$lambda$ecS6BCO608AQvEBSbEaZ37rK_08(this.f$0);
            }
        };
        ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.mDismissBubbleAnimator = valueAnimatorOfFloat3;
        valueAnimatorOfFloat3.addUpdateListener(new BubbleStackView$$ExternalSyntheticLambda7(this, getResources().getDimensionPixelSize(R.dimen.dismiss_circle_small) / getResources().getDimensionPixelSize(R.dimen.dismiss_circle_size)));
        setOnClickListener(new BubbleStackView$$ExternalSyntheticLambda6(this, i));
        ViewPropertyAnimator viewPropertyAnimatorAnimate = animate();
        TimeInterpolator timeInterpolator = Interpolators.PANEL_CLOSE_ACCELERATED;
        viewPropertyAnimatorAnimate.setInterpolator(timeInterpolator).setDuration(320L);
        valueAnimatorOfFloat2.setDuration(150L);
        valueAnimatorOfFloat2.setInterpolator(timeInterpolator);
        valueAnimatorOfFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.bubbles.BubbleStackView.13
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                BubbleExpandedView expandedView = bubbleStackView.getExpandedView();
                if (expandedView == null || BubbleStackView.this.mExpandedViewTemporarilyHidden) {
                    return;
                }
                expandedView.setSurfaceZOrderedOnTop(false);
                expandedView.setSurfaceZOrderedOnTop(false);
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 8146331188896153203L, 0, String.valueOf(expandedView.getBubbleKey()));
                }
                expandedView.setSurfaceZOrderedOnTop(true);
                expandedView.setAnimating(false);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                BubbleExpandedView expandedView = bubbleStackView.getExpandedView();
                if (expandedView != null) {
                    expandedView.setSurfaceZOrderedOnTop(true);
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 1990180620272481653L, 0, String.valueOf(expandedView.getBubbleKey()));
                    }
                    expandedView.mIsAnimating = true;
                    BubbleStackView.this.mExpandedViewContainer.setVisibility(0);
                }
            }
        });
        valueAnimatorOfFloat2.addUpdateListener(new BubbleStackView$$ExternalSyntheticLambda7(this, i));
        valueAnimatorOfFloat.setDuration(150L);
        valueAnimatorOfFloat.setInterpolator(timeInterpolator);
        valueAnimatorOfFloat.addUpdateListener(new BubbleStackView$$ExternalSyntheticLambda7(this, 2));
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.bubbles.BubbleStackView.14
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                BubbleStackView bubbleStackView = BubbleStackView.this;
                PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                bubbleStackView.getClass();
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00b9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void addBubble(Bubble bubble) {
        boolean z;
        boolean z2 = getBubbleCount() == 0;
        if (z2) {
            BubbleViewProvider bubbleViewProvider = this.mBubbleData.mSelectedBubble;
            if ((bubbleViewProvider instanceof Bubble) && ((Bubble) bubbleViewProvider).isChat()) {
                Context context = ((FrameLayout) this).mContext;
                z = (context.getSharedPreferences(context.getPackageName(), 0).getBoolean("HasSeenBubblesOnboarding", false) && Settings.Secure.getInt(((FrameLayout) this).mContext.getContentResolver(), "force_show_bubbles_user_education", 0) == 0) ? false : true;
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -2015221824233916174L, 3, Boolean.valueOf(z));
                }
                if (z && Settings.Secure.getInt(((FrameLayout) this).mContext.getContentResolver(), "force_hide_bubbles_user_education", 0) != 0) {
                    Log.w("Bubbles", "Want to show stack edu, but it is forced hidden");
                    z = false;
                }
                if (z) {
                }
            } else {
                z = false;
                if (z) {
                    this.mStackAnimationController.setStackPosition(this.mPositioner.getRestingPosition());
                }
            }
        }
        if (bubble.mIconView == null) {
            return;
        }
        if (z2 && bubble.isNote()) {
            BubblePositioner bubblePositioner = this.mPositioner;
            PointF defaultStartPosition = bubblePositioner.getDefaultStartPosition(false);
            PointF pointF = bubblePositioner.mRestingStackPosition;
            if (!((pointF == null || pointF.equals(defaultStartPosition)) ? false : true)) {
                PointF defaultStartPosition2 = this.mPositioner.getDefaultStartPosition(true);
                this.mStackOnLeftOrWillBe = this.mPositioner.isStackOnLeft(defaultStartPosition2);
                this.mStackAnimationController.setStackPosition(defaultStartPosition2);
                this.mExpandedAnimationController.mCollapsePoint = defaultStartPosition2;
            }
        } else if (z2) {
            this.mStackOnLeftOrWillBe = this.mStackAnimationController.isStackOnLeftSide();
        }
        bubble.mIconView.setTranslationX(this.mStackAnimationController.mStackPosition.x);
        PhysicsAnimationLayout physicsAnimationLayout = this.mBubbleContainer;
        BadgedImageView badgedImageView = bubble.mIconView;
        int i = this.mPositioner.mBubbleSize;
        physicsAnimationLayout.addViewInternal(badgedImageView, 0, new FrameLayout.LayoutParams(i, i), false);
        bubble.mIconView.getClass();
        bubble.mIconView.setOnClickListener(this.mBubbleClickListener);
        bubble.mIconView.setOnTouchListener(this.mBubbleTouchListener);
        updateBubbleShadows(this.mIsExpanded);
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
                public final void onAnimationCancel(Animator animator) throws Resources.NotFoundException {
                    super.onAnimationCancel(animator);
                    BubbleStackView bubbleStackView = BubbleStackView.this;
                    PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                    bubbleStackView.resetDismissAnimator();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) throws Resources.NotFoundException {
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
        boolean zIsStackOnLeftSide = this.mStackAnimationController.isStackOnLeftSide();
        this.mFlyoutTransitionSpring.mSpring.setStiffness(this.mBubbleToExpandAfterFlyoutCollapse != null ? 1500.0f : 200.0f);
        SpringAnimation springAnimation = this.mFlyoutTransitionSpring;
        springAnimation.mValue = this.mFlyoutDragDeltaX;
        springAnimation.mStartValueIsSet = true;
        springAnimation.mVelocity = f;
        if (z) {
            int width = this.mFlyout.getWidth();
            if (zIsStackOnLeftSide) {
                width = -width;
            }
            f2 = width;
        } else {
            f2 = 0.0f;
        }
        springAnimation.animateToFinalPosition(f2);
    }

    public void animateInFlyoutForBubble(Bubble bubble) {
        Bubble.FlyoutMessage flyoutMessage = bubble.mFlyoutMessage;
        BadgedImageView badgedImageView = bubble.mIconView;
        if (flyoutMessage == null || flyoutMessage.message == null || !bubble.showFlyout() || isStackEduVisible() || this.mIsExpanded || this.mIsExpansionAnimating || this.mIsGestureInProgress || this.mSensitiveNotificationProtectionActive || this.mBubbleToExpandAfterFlyoutCollapse != null || badgedImageView == null) {
            if (badgedImageView == null || this.mFlyout.getVisibility() == 0) {
                return;
            }
            badgedImageView.removeDotSuppressionFlag(BadgedImageView.SuppressionFlag.FLYOUT_VISIBLE);
            return;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -5321455237929157307L, 0, String.valueOf(bubble.mKey));
        }
        this.mFlyoutDragDeltaX = 0.0f;
        this.mFlyout.removeCallbacks(this.mAnimateInFlyout);
        BubbleStackView$$ExternalSyntheticLambda3 bubbleStackView$$ExternalSyntheticLambda3 = this.mAfterFlyoutHidden;
        if (bubbleStackView$$ExternalSyntheticLambda3 != null) {
            bubbleStackView$$ExternalSyntheticLambda3.run();
            this.mAfterFlyoutHidden = null;
        }
        this.mAfterFlyoutHidden = new BubbleStackView$$ExternalSyntheticLambda3(this, bubble, 0);
        BadgedImageView badgedImageView2 = bubble.mIconView;
        BadgedImageView.SuppressionFlag suppressionFlag = BadgedImageView.SuppressionFlag.FLYOUT_VISIBLE;
        if (badgedImageView2.mDotSuppressionFlags.add(suppressionFlag)) {
            badgedImageView2.updateDotVisibility(suppressionFlag == BadgedImageView.SuppressionFlag.BEHIND_STACK);
        }
        post(new BubbleStackView$$ExternalSyntheticLambda3(this, bubble, 1));
        this.mFlyout.removeCallbacks(this.mHideFlyout);
        this.mFlyout.postDelayed(this.mHideFlyout, 5000L);
        logBubbleEvent(bubble, 16);
    }

    public final void animateShadows() {
        int bubbleCount = getBubbleCount();
        for (int i = 0; i < bubbleCount; i++) {
            BadgedImageView badgedImageView = (BadgedImageView) this.mBubbleContainer.getChildAt(i);
            if (i >= 2) {
                badgedImageView.animate().translationZ(0.0f).start();
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0 && motionEvent.getActionIndex() != this.mPointerIndexDown) {
            return false;
        }
        if (motionEvent.getAction() == 0) {
            this.mPointerIndexDown = motionEvent.getActionIndex();
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            this.mPointerIndexDown = -1;
        }
        boolean zDispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        if (!zDispatchTouchEvent && !this.mIsExpanded && this.mIsGestureInProgress) {
            zDispatchTouchEvent = onTouch(this, motionEvent);
        }
        boolean z = (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) ? false : true;
        this.mIsGestureInProgress = z;
        if (this.mShouldReorderBubblesAfterGestureCompletes && !z) {
            this.mShouldReorderBubblesAfterGestureCompletes = false;
            updateBubbleOrderInternal(Collections.unmodifiableList(this.mBubbleData.mBubbles), false);
        }
        return zDispatchTouchEvent;
    }

    public final int getBubbleCount() {
        int childCount = this.mBubbleContainer.getChildCount();
        return this.mShowingOverflow ? childCount - 1 : childCount;
    }

    public final int getBubbleIndex(BubbleViewProvider bubbleViewProvider) {
        if (bubbleViewProvider == null) {
            return -1;
        }
        return this.mBubbleContainer.indexOfChild(bubbleViewProvider.getIconView$1());
    }

    public BubbleViewProvider getExpandedBubble() {
        return this.mExpandedBubble;
    }

    public final BubbleExpandedView getExpandedView() {
        BubbleViewProvider bubbleViewProvider = this.mExpandedBubble;
        if (bubbleViewProvider != null) {
            return bubbleViewProvider.getExpandedView();
        }
        return null;
    }

    public final StackViewState getState() {
        this.mStackViewState.numberOfBubbles = this.mBubbleContainer.getChildCount();
        this.mStackViewState.selectedIndex = getBubbleIndex(this.mExpandedBubble);
        StackViewState stackViewState = this.mStackViewState;
        stackViewState.onLeft = this.mStackOnLeftOrWillBe;
        return stackViewState;
    }

    public final void hideFlyoutImmediate() {
        this.mFlyout.removeCallbacks(this.mAnimateInFlyout);
        BubbleStackView$$ExternalSyntheticLambda3 bubbleStackView$$ExternalSyntheticLambda3 = this.mAfterFlyoutHidden;
        if (bubbleStackView$$ExternalSyntheticLambda3 != null) {
            bubbleStackView$$ExternalSyntheticLambda3.run();
            this.mAfterFlyoutHidden = null;
        }
        this.mFlyout.removeCallbacks(this.mAnimateInFlyout);
        this.mFlyout.removeCallbacks(this.mHideFlyout);
        BubbleFlyoutView bubbleFlyoutView = this.mFlyout;
        BubbleStackView$$ExternalSyntheticLambda3 bubbleStackView$$ExternalSyntheticLambda32 = bubbleFlyoutView.mOnHide;
        if (bubbleStackView$$ExternalSyntheticLambda32 != null) {
            bubbleStackView$$ExternalSyntheticLambda32.run();
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
        RoundingMode roundingMode2 = RoundingMode.HALF_UP;
        float fFloatValue = bigDecimal.setScale(4, roundingMode2).floatValue();
        float fFloatValue2 = new BigDecimal(this.mPositioner.mPositionRect.height() > 0 ? this.mStackAnimationController.mStackPosition.y / r1 : 0.0f).setScale(4, roundingMode2).floatValue();
        BubbleLogger bubbleLogger = bubbleData.mLogger;
        if (bubbleViewProvider == null) {
            bubbleLogger.getClass();
            FrameworkStatsLog.write(149, str, (String) null, 0, 0, bubbleCount, i, fFloatValue, fFloatValue2, false, false, false);
        } else if (bubbleViewProvider.getKey().equals("Overflow")) {
            if (i == 3) {
                bubbleLogger.mUiEventLogger.log(BubbleLogger.Event.BUBBLE_OVERFLOW_SELECTED, bubbleData.mCurrentUserId, str);
            }
        } else {
            Bubble bubble = (Bubble) bubbleViewProvider;
            bubbleLogger.getClass();
            FrameworkStatsLog.write(149, str, bubble.mChannelId, bubble.mNotificationId, bubbleIndex, bubbleCount, i, fFloatValue, fFloatValue2, bubble.showInShade(), false, false);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() throws Resources.NotFoundException {
        super.onAttachedToWindow();
        WindowManager windowManager = (WindowManager) ((FrameLayout) this).mContext.getSystemService(WindowManager.class);
        BubblePositioner bubblePositioner = this.mPositioner;
        Context context = ((FrameLayout) this).mContext;
        Objects.requireNonNull(windowManager);
        bubblePositioner.update(DeviceConfig.create(context, windowManager, this.mPositioner.mDeviceConfig.displayCutout));
        getViewTreeObserver().addOnComputeInternalInsetsListener(this);
        getViewTreeObserver().addOnDrawListener(this.mSystemGestureExcludeUpdater);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0032  */
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
            } else if (getBubbleCount() <= 0) {
                BubbleData bubbleData = this.mBubbleData;
                if (bubbleData.mShowingOverflow && bubbleData.mExpanded) {
                    this.mBubbleContainer.getChildAt(0).getBoundsOnScreen(rect);
                    int i2 = rect.top;
                    int i3 = this.mBubbleTouchPadding;
                    rect.top = i2 - i3;
                    rect.left -= i3;
                    rect.right += i3;
                    rect.bottom += i3;
                }
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
        stopMonitoringSwipeUpGesture();
        removeOnLayoutChangeListener(this.mOrientationChangedListener);
        removeOnLayoutChangeListener(this.mOrientationChangedListener);
    }

    public final void onDisplaySizeChanged() throws Resources.NotFoundException {
        updateOverflow();
        setUpFlyout();
        setUpDismissView();
        updateUserEdu();
        this.mBubbleSize = this.mPositioner.mBubbleSize;
        for (Bubble bubble : Collections.unmodifiableList(this.mBubbleData.mBubbles)) {
            BadgedImageView badgedImageView = bubble.mIconView;
            if (badgedImageView == null) {
                Log.w("Bubbles", "Display size changed. Icon null: " + bubble);
            } else {
                int i = this.mBubbleSize;
                badgedImageView.setLayoutParams(new FrameLayout.LayoutParams(i, i));
            }
        }
        if (this.mShowingOverflow) {
            BadgedImageView iconView = this.mBubbleOverflow.getIconView$1();
            int i2 = this.mBubbleSize;
            iconView.setLayoutParams(new FrameLayout.LayoutParams(i2, i2));
        }
        this.mExpandedAnimationController.updateResources();
        this.mStackAnimationController.updateResources();
        this.mDismissView.getClass();
        this.mMagneticTarget.magneticFieldRadiusPx = this.mBubbleSize * 2;
        if (!isStackEduVisible()) {
            StackAnimationController stackAnimationController = this.mStackAnimationController;
            stackAnimationController.setStackPosition(new RelativeStackPosition(this.mPositioner.getRestingPosition(), this.mPositioner.getAllowableStackPositionRegion(getBubbleCount())).getAbsolutePositionInRegion(stackAnimationController.mPositioner.getAllowableStackPositionRegion(stackAnimationController.mBubbleCountSupplier.getAsInt())));
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

    public final void resetCircle() throws Resources.NotFoundException {
        if ((Resources.getSystem().getConfiguration().uiMode & 48) == 32) {
            this.mDismissView.circle.setup(R.drawable.dismiss_circle_background, R.drawable.bubble_delete_ic_d, R.dimen.sec_noti_bubble_dismiss_button_width);
        } else {
            this.mDismissView.circle.setup(R.drawable.dismiss_circle_background, R.drawable.bubble_delete_ic, R.dimen.sec_noti_bubble_dismiss_button_width);
        }
    }

    public final void resetDismissAnimator() throws Resources.NotFoundException {
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
            resetCircle();
        }
    }

    public final void setFlyoutStateForDragLength(float f) {
        if (this.mFlyout.getWidth() <= 0) {
            return;
        }
        boolean zIsStackOnLeftSide = this.mStackAnimationController.isStackOnLeftSide();
        this.mFlyoutDragDeltaX = f;
        if (zIsStackOnLeftSide) {
            f = -f;
        }
        float width = f / this.mFlyout.getWidth();
        float width2 = 0.0f;
        this.mFlyout.setCollapsePercent(Math.min(1.0f, Math.max(0.0f, width)));
        if (width < 0.0f || width > 1.0f) {
            boolean z = false;
            boolean z2 = width > 1.0f;
            if ((zIsStackOnLeftSide && width > 1.0f) || (!zIsStackOnLeftSide && width < 0.0f)) {
                z = true;
            }
            width2 = (this.mFlyout.getWidth() / (8.0f / (z2 ? 2 : 1))) * (z2 ? width - 1.0f : width * (-1.0f)) * (z ? -1 : 1);
        }
        BubbleFlyoutView bubbleFlyoutView = this.mFlyout;
        bubbleFlyoutView.setTranslationX(bubbleFlyoutView.mRestingTranslationX + width2);
    }

    public final void setImeVisible(boolean z) throws Resources.NotFoundException {
        float f;
        int i = 0;
        if ((this.mIsExpansionAnimating || this.mIsBubbleSwitchAnimating) && this.mIsExpanded) {
            this.mExpandedAnimationController.expandFromStack(new BubbleStackView$$ExternalSyntheticLambda30(this, z, i));
            return;
        }
        if (!this.mIsExpanded && getBubbleCount() > 0) {
            StackAnimationController stackAnimationController = this.mStackAnimationController;
            float f2 = stackAnimationController.mPositioner.getAllowableStackPositionRegion(stackAnimationController.mBubbleCountSupplier.getAsInt()).bottom;
            if (z) {
                float f3 = stackAnimationController.mStackPosition.y;
                if (f3 > f2 && stackAnimationController.mPreImeY == -1.4E-45f) {
                    stackAnimationController.mPreImeY = f3;
                    f = f2;
                }
                f = -1.4E-45f;
            } else {
                f2 = stackAnimationController.mPreImeY;
                if (f2 != -1.4E-45f) {
                    stackAnimationController.mPreImeY = -1.4E-45f;
                    f = f2;
                }
                f = -1.4E-45f;
            }
            if (f != -1.4E-45f) {
                DynamicAnimation.AnonymousClass2 anonymousClass2 = DynamicAnimation.TRANSLATION_Y;
                SpringForce springForce = stackAnimationController.getSpringForce(null);
                springForce.setStiffness(200.0f);
                stackAnimationController.springFirstBubbleWithStackFollowing(anonymousClass2, springForce, 0.0f, f, new Runnable[0]);
                stackAnimationController.notifyFloatingCoordinatorStackAnimatingTo(stackAnimationController.mStackPosition.x, f);
            }
            if (f == -1.4E-45f) {
                f = stackAnimationController.mStackPosition.y;
            }
            float f4 = f - this.mStackAnimationController.mStackPosition.y;
            if (this.mFlyout.getVisibility() == 0) {
                BubbleFlyoutView bubbleFlyoutView = this.mFlyout;
                PhysicsAnimator.Companion.getClass();
                PhysicsAnimator companion = PhysicsAnimator.Companion.getInstance(bubbleFlyoutView);
                companion.spring(DynamicAnimation.TRANSLATION_Y, this.mFlyout.getTranslationY() + f4, 0.0f, FLYOUT_IME_ANIMATION_SPRING_CONFIG);
                companion.start();
            }
        }
        if (this.mIsExpanded) {
            this.mExpandedViewAnimationController.animateForImeVisibilityChange(z);
            BubbleExpandedView expandedView = getExpandedView();
            if (expandedView != null) {
                expandedView.mImeVisible = z;
                if (!z && expandedView.mNeedsNewHeight) {
                    expandedView.updateHeight();
                }
            }
            if (!this.mPositioner.showBubblesVertically() || expandedView == null) {
                return;
            }
            float expandedViewY = this.mPositioner.getExpandedViewY(this.mExpandedBubble, this.mPositioner.getExpandedBubbleXY(getState().selectedIndex, getState()).y);
            if (!expandedView.mUsingMaxHeight) {
                this.mExpandedViewContainer.animate().translationY(expandedViewY);
            }
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.mBubbleContainer.getChildCount(); i2++) {
                arrayList.add(ObjectAnimator.ofFloat(this.mBubbleContainer.getChildAt(i2), (Property<View, Float>) FrameLayout.TRANSLATION_Y, this.mPositioner.getExpandedBubbleXY(i2, getState()).y));
            }
            updatePointerPosition(true);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(arrayList);
            animatorSet.start();
        }
    }

    public final void setSelectedBubble(BubbleViewProvider bubbleViewProvider) {
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
            SurfaceView surfaceView = this.mAnimatingOutSurfaceView;
            PhysicsAnimator.Companion.getClass();
            PhysicsAnimator.Companion.getInstance(surfaceView).cancel();
            PhysicsAnimator.Companion.getInstance(this.mExpandedViewContainerMatrix).cancel();
            this.mExpandedViewContainer.setAnimationMatrix(null);
        }
        showManageMenu(false);
        BubbleViewProvider bubbleViewProvider2 = this.mExpandedBubble;
        this.mExpandedBubble = bubbleViewProvider;
        ExpandedViewAnimationControllerImpl expandedViewAnimationControllerImpl = this.mExpandedViewAnimationController;
        BubbleExpandedView expandedView = getExpandedView();
        if (expandedViewAnimationControllerImpl.mExpandedView != null) {
            AnimatorSet animatorSet = expandedViewAnimationControllerImpl.mCollapseAnimation;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            SpringAnimation springAnimation = expandedViewAnimationControllerImpl.mBackToExpandedAnimation;
            if (springAnimation != null) {
                springAnimation.cancel();
            }
            expandedViewAnimationControllerImpl.reset();
        }
        expandedViewAnimationControllerImpl.mExpandedView = expandedView;
        String key = bubbleViewProvider2 != null ? bubbleViewProvider2.getKey() : "null";
        BubbleViewProvider bubbleViewProvider3 = this.mExpandedBubble;
        String key2 = bubbleViewProvider3 != null ? bubbleViewProvider3.getKey() : "null";
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 6917798123885378812L, 48, String.valueOf(key2), String.valueOf(key), Boolean.valueOf(this.mIsExpanded));
        }
        if (this.mIsExpanded) {
            BubbleStackView$$ExternalSyntheticLambda24 bubbleStackView$$ExternalSyntheticLambda24 = new BubbleStackView$$ExternalSyntheticLambda24(this, bubbleViewProvider2, bubbleViewProvider, 0);
            if (this.mPositioner.mImeVisible) {
                ((BubbleStackViewManager$Companion$fromBubbleController$1) this.mManager).$controller.hideCurrentInputMethod(bubbleStackView$$ExternalSyntheticLambda24);
            } else {
                bubbleStackView$$ExternalSyntheticLambda24.run();
            }
        }
    }

    public final void setUpDismissView() throws Resources.NotFoundException {
        DismissView dismissView = this.mDismissView;
        if (dismissView != null) {
            removeView(dismissView);
        }
        DismissView dismissView2 = new DismissView(getContext());
        this.mDismissView = dismissView2;
        DismissViewUtils.setup(dismissView2);
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

    public final void showScrim(boolean z, final BubbleStackView$$ExternalSyntheticLambda28 bubbleStackView$$ExternalSyntheticLambda28) {
        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.android.wm.shell.bubbles.BubbleStackView.18
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                BubbleStackView.this.mScrimAnimation = null;
                Runnable runnable = bubbleStackView$$ExternalSyntheticLambda28;
                if (runnable != null) {
                    runnable.run();
                }
            }
        };
        ViewPropertyAnimator viewPropertyAnimator = this.mScrimAnimation;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
        if (z) {
            ViewPropertyAnimator viewPropertyAnimatorAnimate = this.mScrim.animate();
            this.mScrimAnimation = viewPropertyAnimatorAnimate;
            viewPropertyAnimatorAnimate.setInterpolator(Interpolators.ALPHA_IN).alpha(0.32f).setListener(animatorListenerAdapter).start();
        } else {
            ViewPropertyAnimator viewPropertyAnimatorAnimate2 = this.mScrim.animate();
            this.mScrimAnimation = viewPropertyAnimatorAnimate2;
            viewPropertyAnimatorAnimate2.alpha(0.0f).setInterpolator(Interpolators.ALPHA_OUT).setListener(animatorListenerAdapter).start();
        }
    }

    public final void startMonitoringSwipeUpGesture() {
        stopMonitoringSwipeUpGestureInternal();
        Context context = ((FrameLayout) this).mContext;
        int i = ContextUtils.$r8$clinit;
        if (context.getResources().getInteger(android.R.integer.config_screenTimeoutOverride) == 2) {
            final BubblesNavBarGestureTracker bubblesNavBarGestureTracker = new BubblesNavBarGestureTracker(((FrameLayout) this).mContext, this.mPositioner);
            this.mBubblesNavBarGestureTracker = bubblesNavBarGestureTracker;
            AnonymousClass9 anonymousClass9 = this.mSwipeUpListener;
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 6702023880190540565L, 0, null);
            }
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
            InputMonitor inputMonitorMonitorGestureInput = ((InputManager) bubblesNavBarGestureTracker.mContext.getSystemService(InputManager.class)).monitorGestureInput("bubbles-gesture", bubblesNavBarGestureTracker.mContext.getDisplayId());
            bubblesNavBarGestureTracker.mInputMonitor = inputMonitorMonitorGestureInput;
            bubblesNavBarGestureTracker.mInputEventReceiver = new BubblesNavBarInputEventReceiver(inputMonitorMonitorGestureInput.getInputChannel(), Choreographer.getInstance(), new BubblesNavBarMotionEventHandler(bubblesNavBarGestureTracker.mContext, bubblesNavBarGestureTracker.mPositioner, new Runnable() { // from class: com.android.wm.shell.bubbles.BubblesNavBarGestureTracker$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BubblesNavBarGestureTracker bubblesNavBarGestureTracker2 = bubblesNavBarGestureTracker;
                    bubblesNavBarGestureTracker2.getClass();
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -4230696345096735024L, 0, null);
                    }
                    InputMonitor inputMonitor2 = bubblesNavBarGestureTracker2.mInputMonitor;
                    if (inputMonitor2 != null) {
                        inputMonitor2.pilferPointers();
                    }
                }
            }, anonymousClass9));
            setOnTouchListener(this.mContainerSwipeListener);
        }
    }

    public void stopMonitoringSwipeUpGesture() {
        stopMonitoringSwipeUpGestureInternal();
    }

    public final void stopMonitoringSwipeUpGestureInternal() {
        BubblesNavBarGestureTracker bubblesNavBarGestureTracker = this.mBubblesNavBarGestureTracker;
        if (bubblesNavBarGestureTracker != null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -3631850868647903454L, 0, null);
            }
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

    public final void updateBubbleOrderInternal(List list, boolean z) {
        boolean z2;
        final BubbleStackView$$ExternalSyntheticLambda1 bubbleStackView$$ExternalSyntheticLambda1 = new BubbleStackView$$ExternalSyntheticLambda1(0, this, list);
        if (this.mIsExpanded || this.mIsExpansionAnimating) {
            bubbleStackView$$ExternalSyntheticLambda1.run();
            updateBadges(false);
            updateBubbleShadows(true);
        } else {
            List list2 = (List) list.stream().map(new BubbleStackView$$ExternalSyntheticLambda2()).collect(Collectors.toList());
            final StackAnimationController stackAnimationController = this.mStackAnimationController;
            stackAnimationController.getClass();
            final StackAnimationController$$ExternalSyntheticLambda3 stackAnimationController$$ExternalSyntheticLambda3 = new StackAnimationController$$ExternalSyntheticLambda3(0, stackAnimationController, list2);
            boolean z3 = false;
            for (int i = 0; i < list2.size(); i++) {
                final View view = (View) list2.get(i);
                if (view != null) {
                    if (i == stackAnimationController.mLayout.indexOfChild(view)) {
                        stackAnimationController.moveToFinalIndex(view, i, bubbleStackView$$ExternalSyntheticLambda1);
                        z2 = false;
                    } else {
                        if (i == 0) {
                            view.setTag(R.id.reorder_animator_tag, view.animate().translationY(stackAnimationController.mStackPosition.y - stackAnimationController.mSwapAnimationOffset).setDuration(300L).withEndAction(new Runnable() { // from class: com.android.wm.shell.bubbles.animation.StackAnimationController$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    StackAnimationController stackAnimationController2 = stackAnimationController;
                                    StackAnimationController$$ExternalSyntheticLambda3 stackAnimationController$$ExternalSyntheticLambda32 = stackAnimationController$$ExternalSyntheticLambda3;
                                    View view2 = view;
                                    BubbleStackView$$ExternalSyntheticLambda1 bubbleStackView$$ExternalSyntheticLambda12 = bubbleStackView$$ExternalSyntheticLambda1;
                                    stackAnimationController2.getClass();
                                    stackAnimationController$$ExternalSyntheticLambda32.run();
                                    stackAnimationController2.moveToFinalIndex(view2, 0, bubbleStackView$$ExternalSyntheticLambda12);
                                }
                            }));
                        } else {
                            stackAnimationController.moveToFinalIndex(view, i, bubbleStackView$$ExternalSyntheticLambda1);
                        }
                        z2 = true;
                    }
                    z3 |= z2;
                }
            }
            if (!z3) {
                stackAnimationController$$ExternalSyntheticLambda3.run();
            }
        }
        if (z) {
            updatePointerPosition(false);
        }
    }

    public final void updateBubbleShadows(boolean z) {
        float f;
        int i;
        int childCount = this.mBubbleContainer.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            BadgedImageView badgedImageView = (BadgedImageView) this.mBubbleContainer.getChildAt(i2);
            BubbleViewProvider bubbleViewProvider = badgedImageView.mBubble;
            boolean zEquals = "Overflow".equals(bubbleViewProvider != null ? bubbleViewProvider.getKey() : null);
            MagnetizedObject magnetizedObject = this.mMagnetizedObject;
            if (magnetizedObject == null || !magnetizedObject.underlyingObject.equals(badgedImageView)) {
                BubblePositioner bubblePositioner = this.mPositioner;
                bubblePositioner.getClass();
                if (zEquals) {
                    f = 0.0f;
                    badgedImageView.setZ(f);
                } else {
                    if (z) {
                        i = bubblePositioner.mMaxBubbles;
                    } else {
                        if (i2 < 2) {
                            i = bubblePositioner.mMaxBubbles * bubblePositioner.mBubbleElevation;
                        }
                        f = 0.0f;
                        badgedImageView.setZ(f);
                    }
                    f = i - i2;
                    badgedImageView.setZ(f);
                }
            } else {
                badgedImageView.setZ((this.mPositioner.mMaxBubbles * this.mBubbleElevation) + 1);
            }
        }
    }

    public final void updateExpandedBubble() {
        this.mExpandedViewContainer.removeAllViews();
        BubbleExpandedView expandedView = getExpandedView();
        if (!this.mIsExpanded || expandedView == null) {
            return;
        }
        expandedView.setContentVisibility(false);
        expandedView.setAnimating(!this.mIsExpansionAnimating);
        this.mExpandedViewContainerMatrix.setScaleX(0.0f);
        this.mExpandedViewContainerMatrix.setScaleY(0.0f);
        this.mExpandedViewContainerMatrix.setTranslate(0.0f, 0.0f);
        this.mExpandedViewContainer.setVisibility(4);
        this.mExpandedViewContainer.setAlpha(0.0f);
        this.mExpandedViewContainer.addView(expandedView);
        postDelayed(new BubbleStackView$$ExternalSyntheticLambda5(this, 8), 0L);
        BubbleStackView$$ExternalSyntheticLambda6 bubbleStackView$$ExternalSyntheticLambda6 = new BubbleStackView$$ExternalSyntheticLambda6(this, 2);
        expandedView.mManageClickListener = bubbleStackView$$ExternalSyntheticLambda6;
        expandedView.mManageButton.setOnClickListener(bubbleStackView$$ExternalSyntheticLambda6);
        if (this.mIsExpansionAnimating) {
            return;
        }
        this.mIsBubbleSwitchAnimating = true;
        SurfaceSynchronizer surfaceSynchronizer = this.mSurfaceSynchronizer;
        BubbleStackView$$ExternalSyntheticLambda5 bubbleStackView$$ExternalSyntheticLambda5 = new BubbleStackView$$ExternalSyntheticLambda5(this, 9);
        AnonymousClass1 anonymousClass1 = (AnonymousClass1) surfaceSynchronizer;
        anonymousClass1.getClass();
        Choreographer.getInstance().postFrameCallback(new AnonymousClass1.ChoreographerFrameCallbackC06551(anonymousClass1, bubbleStackView$$ExternalSyntheticLambda5));
    }

    public final void updateExpandedView() {
        BubbleViewProvider bubbleViewProvider = this.mExpandedBubble;
        int[] expandedViewContainerPadding = this.mPositioner.getExpandedViewContainerPadding(this.mStackAnimationController.isStackOnLeftSide(), bubbleViewProvider != null && "Overflow".equals(bubbleViewProvider.getKey()));
        this.mExpandedViewContainer.setPadding(expandedViewContainerPadding[0], expandedViewContainerPadding[1], expandedViewContainerPadding[2], expandedViewContainerPadding[3]);
        BubbleExpandedView expandedView = getExpandedView();
        if (expandedView != null) {
            PointF expandedBubbleXY = this.mPositioner.getExpandedBubbleXY(getBubbleIndex(this.mExpandedBubble), getState());
            FrameLayout frameLayout = this.mExpandedViewContainer;
            BubblePositioner bubblePositioner = this.mPositioner;
            frameLayout.setTranslationY(bubblePositioner.getExpandedViewY(this.mExpandedBubble, bubblePositioner.showBubblesVertically() ? expandedBubbleXY.y : expandedBubbleXY.x));
            this.mExpandedViewContainer.setTranslationX(0.0f);
            if (expandedView.mTaskView != null) {
                boolean zIsStackOnLeft = expandedView.mPositioner.isStackOnLeft(expandedView.mStackView.mStackAnimationController.mStackPosition);
                BubblePositioner bubblePositioner2 = expandedView.mPositioner;
                int[] expandedViewContainerPadding2 = bubblePositioner2.getExpandedViewContainerPadding(zIsStackOnLeft, false);
                if (expandedView.mTaskView.getWidth() != ((bubblePositioner2.mScreenRect.width() - expandedViewContainerPadding2[0]) - expandedViewContainerPadding2[2]) - (bubblePositioner2.showBubblesVertically() ? bubblePositioner2.mPointerHeight - bubblePositioner2.mPointerOverlap : 0)) {
                    expandedView.mTaskView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                }
            }
            expandedView.mExpandedViewContainerLocation = this.mExpandedViewContainer.getLocationOnScreen();
            expandedView.updateDimensions();
            expandedView.updateHeight();
            TaskView taskView = expandedView.mTaskView;
            if (taskView != null && taskView.getVisibility() == 0 && expandedView.mTaskView.isAttachedToWindow()) {
                expandedView.post(new BubbleExpandedView$$ExternalSyntheticLambda1(expandedView, 1));
            }
            if (expandedView.mIsOverflow) {
                expandedView.post(new BubbleExpandedView$$ExternalSyntheticLambda1(expandedView, 2));
            }
            updatePointerPosition(false);
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
        BubbleBarExpandedView bubbleBarExpandedView = bubbleOverflow.bubbleBarExpandedView;
        if (bubbleBarExpandedView != null) {
            bubbleBarExpandedView.applyThemeAttrs();
        }
        BadgedImageView iconView = bubbleOverflow.getIconView$1();
        if (iconView != null) {
            iconView.mBubbleIcon.setImageResource(R.drawable.sec_bubble_tw_ic_fad_add_mtrl);
        }
        bubbleOverflow.updateBtnTheme();
        if (this.mShowingOverflow) {
            this.mBubbleContainer.reorderView(this.mBubbleOverflow.getIconView$1(), this.mBubbleContainer.getChildCount() - 1);
        }
        updateOverflowVisibility();
    }

    public final void updateOverflowDotVisibility(boolean z) {
        if (this.mShowingOverflow) {
            BubbleOverflow bubbleOverflow = this.mBubbleOverflow;
            if (bubbleOverflow.showDot) {
                bubbleOverflow.getIconView$1().animateDotScale(z ? 1.0f : 0.0f, new BubbleStackView$$ExternalSyntheticLambda30(this, z, 1));
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateOverflowVisibility() {
        int i;
        if (this.mShowingOverflow) {
            if (!this.mIsExpanded) {
                BubbleData bubbleData = this.mBubbleData;
                if (!bubbleData.mShowingOverflow || !bubbleData.mExpanded) {
                }
            }
            i = 0;
        } else {
            i = 8;
        }
        BadgedImageView badgedImageView = this.mBubbleOverflow.overflowBtn;
        if (badgedImageView != null) {
            badgedImageView.setVisibility(i);
        }
    }

    public final void updatePointerPosition(boolean z) {
        int bubbleIndex;
        BubbleExpandedView expandedView = getExpandedView();
        BubbleViewProvider bubbleViewProvider = this.mExpandedBubble;
        if (bubbleViewProvider == null || expandedView == null || (bubbleIndex = getBubbleIndex(bubbleViewProvider)) == -1) {
            return;
        }
        PointF expandedBubbleXY = this.mPositioner.getExpandedBubbleXY(bubbleIndex, getState());
        expandedView.setPointerPosition(this.mPositioner.showBubblesVertically() ? expandedBubbleXY.y : expandedBubbleXY.x, this.mStackOnLeftOrWillBe, z);
    }

    public final void updateTemporarilyInvisibleAnimation(boolean z) {
        removeCallbacks(this.mAnimateTemporarilyInvisibleImmediate);
        if (this.mIsDraggingStack) {
            return;
        }
        postDelayed(this.mAnimateTemporarilyInvisibleImmediate, (!(this.mTemporarilyInvisible && this.mFlyout.getVisibility() != 0) || z) ? 0L : 1000L);
    }

    public final void updateUserEdu() throws Resources.NotFoundException {
        if (isStackEduVisible()) {
            StackEducationView stackEducationView = this.mStackEduView;
            if (!stackEducationView.isHiding) {
                removeView(stackEducationView);
                BubbleStackViewManager bubbleStackViewManager = this.mManager;
                Objects.requireNonNull(bubbleStackViewManager);
                this.mStackEducationViewManager = new BubbleStackView$$ExternalSyntheticLambda18(bubbleStackViewManager);
                StackEducationView stackEducationView2 = new StackEducationView(((FrameLayout) this).mContext, this.mPositioner, this.mStackEducationViewManager);
                this.mStackEduView = stackEducationView2;
                addView(stackEducationView2);
                this.mBubbleContainer.bringToFront();
                final PointF startPosition = this.mPositioner.getStartPosition(this.mStackAnimationController.isStackOnLeftSide() ? BubblePositioner.StackPinnedEdge.LEFT : BubblePositioner.StackPinnedEdge.RIGHT);
                this.mStackAnimationController.springStack(startPosition.x, startPosition.y, 700.0f);
                final StackEducationView stackEducationView3 = this.mStackEduView;
                stackEducationView3.isHiding = false;
                if (stackEducationView3.getVisibility() != 0) {
                    ((BubbleStackViewManager$Companion$fromBubbleController$1) ((BubbleStackView$$ExternalSyntheticLambda18) stackEducationView3.manager).f$0).$controller.updateWindowFlagsForBackpress(true);
                    ViewGroup.LayoutParams layoutParams = stackEducationView3.getLayoutParams();
                    DeviceConfig deviceConfig = stackEducationView3.positioner.mDeviceConfig;
                    layoutParams.width = (deviceConfig.isLargeScreen || deviceConfig.isLandscape) ? stackEducationView3.getContext().getResources().getDimensionPixelSize(R.dimen.bubbles_user_education_width) : -1;
                    final boolean zIsStackOnLeft = stackEducationView3.positioner.isStackOnLeft(startPosition);
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) ((View) stackEducationView3.view$delegate.getValue()).getLayoutParams();
                    int dimensionPixelSize = stackEducationView3.getResources().getDimensionPixelSize(R.dimen.bubble_user_education_margin_horizontal);
                    marginLayoutParams.leftMargin = zIsStackOnLeft ? 0 : dimensionPixelSize;
                    if (!zIsStackOnLeft) {
                        dimensionPixelSize = 0;
                    }
                    marginLayoutParams.rightMargin = dimensionPixelSize;
                    final int dimensionPixelSize2 = stackEducationView3.getContext().getResources().getDimensionPixelSize(R.dimen.bubble_user_education_stack_padding);
                    stackEducationView3.setAlpha(0.0f);
                    stackEducationView3.setVisibility(0);
                    ((View) stackEducationView3.view$delegate.getValue()).setBackgroundResource(zIsStackOnLeft ? R.drawable.bubble_stack_user_education_bg : R.drawable.bubble_stack_user_education_bg_rtl);
                    stackEducationView3.post(new Runnable() { // from class: com.android.wm.shell.bubbles.StackEducationView$show$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            stackEducationView3.requestFocus();
                            View view = (View) stackEducationView3.view$delegate.getValue();
                            boolean z = zIsStackOnLeft;
                            StackEducationView stackEducationView4 = stackEducationView3;
                            int i = dimensionPixelSize2;
                            PointF pointF = startPosition;
                            if (z) {
                                view.setPadding(stackEducationView4.positioner.mBubbleSize + i, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
                                view.setTranslationX(0.0f);
                            } else {
                                view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), stackEducationView4.positioner.mBubbleSize + i, view.getPaddingBottom());
                                view.setTranslationX((stackEducationView4.positioner.mScreenRect.right - view.getWidth()) - i);
                            }
                            view.setTranslationY((pointF.y + (stackEducationView4.positioner.mBubbleSize / 2)) - (view.getHeight() / 2));
                            stackEducationView3.animate().setDuration(200L).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).alpha(1.0f);
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
            BubbleExpandedView expandedView = getExpandedView();
            if (expandedView == null) {
                return;
            }
            this.mManageEduView.show(expandedView, this.mStackAnimationController.isStackOnLeftSide());
        }
    }

    public class RelativeStackPosition {
        public final boolean mOnLeft;
        public final float mVerticalOffsetPercent;

        public RelativeStackPosition(boolean z, float f) {
            this.mOnLeft = z;
            this.mVerticalOffsetPercent = Math.max(0.0f, Math.min(1.0f, f));
        }

        public final PointF getAbsolutePositionInRegion(RectF rectF) {
            return new PointF(this.mOnLeft ? rectF.left : rectF.right, (rectF.height() * this.mVerticalOffsetPercent) + rectF.top);
        }

        public RelativeStackPosition(PointF pointF, RectF rectF) {
            this.mOnLeft = pointF.x < rectF.width() / 2.0f;
            this.mVerticalOffsetPercent = Math.max(0.0f, Math.min(1.0f, (pointF.y - rectF.top) / rectF.height()));
        }
    }

    public void showManageMenu(boolean z) {
    }
}
