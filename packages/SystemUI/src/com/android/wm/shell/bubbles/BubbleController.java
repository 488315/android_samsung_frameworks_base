package com.android.wm.shell.bubbles;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.TaskInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.res.Configuration;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.service.notification.NotificationListenerService;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.IWindowManager;
import android.view.InsetsSourceControl;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.inputmethod.ImeTracker;
import android.widget.FrameLayout;
import android.window.WindowContainerToken;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.dynamicanimation.animation.DynamicAnimation;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.R;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.wmshell.BubblesManager;
import com.android.systemui.wmshell.BubblesManager$$ExternalSyntheticLambda1;
import com.android.systemui.wmshell.BubblesManager$$ExternalSyntheticLambda2;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda1;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda3;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.bubbles.BubbleData;
import com.android.wm.shell.bubbles.BubbleExpandedView;
import com.android.wm.shell.bubbles.BubbleLogger;
import com.android.wm.shell.bubbles.BubbleOverflowContainerView;
import com.android.wm.shell.bubbles.BubbleStackView;
import com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda1;
import com.android.wm.shell.bubbles.BubbleTransitions.ConvertFromBubble;
import com.android.wm.shell.bubbles.BubbleViewInfoTask;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.bubbles.animation.AnimatableScaleMatrix;
import com.android.wm.shell.bubbles.animation.ExpandedViewAnimationControllerImpl;
import com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout;
import com.android.wm.shell.bubbles.animation.StackAnimationController;
import com.android.wm.shell.bubbles.bar.BubbleBarLayerView;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayImeController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.common.FloatingContentCoordinator;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ImeListener;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.common.TaskStackListenerImpl;
import com.android.wm.shell.draganddrop.DragAndDropController;
import com.android.wm.shell.onehanded.OneHandedController;
import com.android.wm.shell.onehanded.OneHandedTransitionCallback;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.shared.bubbles.BubbleBarLocation;
import com.android.wm.shell.shared.bubbles.DeviceConfig;
import com.android.wm.shell.sysui.ConfigurationChangeListener;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.taskview.TaskViewController;
import com.android.wm.shell.taskview.TaskViewRepository;
import com.android.wm.shell.taskview.TaskViewTaskController;
import com.android.wm.shell.taskview.TaskViewTransitions;
import com.android.wm.shell.transition.Transitions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BubbleController implements ConfigurationChangeListener, RemoteCallable, Bubbles$SysuiProxy$Provider {
    public final ShellExecutor mBackgroundExecutor;
    public final IStatusBarService mBarService;
    public final AnonymousClass6 mBroadcastReceiver;
    public BubbleBadgeIconFactory mBubbleBadgeIconFactory;
    public final AnonymousClass9 mBubbleBarViewCallback;
    public final BubbleData mBubbleData;
    public final AnonymousClass10 mBubbleDataListener;
    public BubbleIconFactory mBubbleIconFactory;
    public final BubblePositioner mBubblePositioner;
    public BubblesManager$$ExternalSyntheticLambda1 mBubbleSALogger;
    public final AnonymousClass8 mBubbleStackViewCallback;
    public final AnonymousClass1 mBubbleTaskViewFactory;
    public final BubbleTransitions mBubbleTransitions;
    public AnonymousClass8 mBubbleViewCallback;
    public final Context mContext;
    public SparseArray mCurrentProfiles;
    public int mCurrentUserId;
    public final BubbleDataRepository mDataRepository;
    public final DisplayController mDisplayController;
    public final DisplayImeController mDisplayImeController;
    public final DisplayInsetsController mDisplayInsetsController;
    public final DragAndDropController mDragAndDropController;
    public BubbleController$$ExternalSyntheticLambda5 mExpandListener;
    public final BubbleExpandedViewManager$Companion$fromBubbleController$1 mExpandedViewManager;
    public final FloatingContentCoordinator mFloatingContentCoordinator;
    public boolean mInflateSynchronously;
    public final LauncherApps mLauncherApps;
    public final BubbleLogger mLogger;
    public final ShellExecutor mMainExecutor;
    public final Handler mMainHandler;
    public BubbleEntry mNotifEntryToExpandOnShadeUnlock;
    public Runnable mOnImeHidden;
    public final Optional mOneHandedOptional;
    public final ResizabilityChecker mResizabilityChecker;
    public final SparseArray mSavedUserBubbleData;
    public final ShellCommandHandler mShellCommandHandler;
    public final ShellController mShellController;
    public final AnonymousClass7 mShortcutBroadcastReceiver;
    public BubbleStackView mStackView;
    public final BubbleStackView.SurfaceSynchronizer mSurfaceSynchronizer;
    public BubblesManager.AnonymousClass5 mSysuiProxy;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final TaskStackListenerImpl mTaskStackListener;
    public final BubbleTaskViewController mTaskViewController;
    public NotificationListenerService.Ranking mTmpRanking;
    public final Transitions mTransitions;
    public final UserManager mUserManager;
    public WindowInsets mWindowInsets;
    public final WindowManager mWindowManager;
    public WindowManager.LayoutParams mWmLayoutParams;
    public final IWindowManager mWmService;
    public final BubblesImpl mImpl = new BubblesImpl(this, 0);
    public BubbleOverflowContainerView.AnonymousClass2 mOverflowListener = null;
    public boolean mOverflowDataLoadNeeded = true;
    public boolean mAddedToWindowManager = false;
    public int mDensityDpi = 0;
    public final Rect mScreenBounds = new Rect();
    public float mFontScale = 0.0f;
    public Locale mLocale = null;
    public int mLayoutDirection = -1;
    public boolean mIsStatusBarShade = true;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.bubbles.BubbleController$1, reason: invalid class name */
    public class AnonymousClass1 implements BubbleTaskViewFactory {
        public final /* synthetic */ Context val$context;
        public final /* synthetic */ ShellExecutor val$mainExecutor;
        public final /* synthetic */ ShellTaskOrganizer val$organizer;
        public final /* synthetic */ SyncTransactionQueue val$syncQueue;

        public AnonymousClass1(Context context, ShellTaskOrganizer shellTaskOrganizer, SyncTransactionQueue syncTransactionQueue, ShellExecutor shellExecutor) {
            this.val$context = context;
            this.val$organizer = shellTaskOrganizer;
            this.val$syncQueue = syncTransactionQueue;
            this.val$mainExecutor = shellExecutor;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.bubbles.BubbleController$10, reason: invalid class name */
    public class AnonymousClass10 {
        public AnonymousClass10() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.bubbles.BubbleController$2, reason: invalid class name */
    public class AnonymousClass2 implements OneHandedTransitionCallback {
        public AnonymousClass2() {
        }

        @Override // com.android.wm.shell.onehanded.OneHandedTransitionCallback
        public final void onStartFinished(Rect rect) {
            BubbleController.this.mMainExecutor.execute(new BubbleController$2$$ExternalSyntheticLambda0(this, rect, 1));
        }

        @Override // com.android.wm.shell.onehanded.OneHandedTransitionCallback
        public final void onStopFinished(Rect rect) {
            BubbleController.this.mMainExecutor.execute(new BubbleController$2$$ExternalSyntheticLambda0(this, rect, 0));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.bubbles.BubbleController$6, reason: invalid class name */
    public class AnonymousClass6 extends BroadcastReceiver {
        public AnonymousClass6() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (BubbleController.this.mBubbleData.mExpanded) {
                String action = intent.getAction();
                String stringExtra = intent.getStringExtra("reason");
                boolean z = "recentapps".equals(stringExtra) || "homekey".equals(stringExtra) || "gestureNav".equals(stringExtra);
                if ((PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action) && z) || "android.intent.action.SCREEN_OFF".equals(action)) {
                    BubbleController.this.mMainExecutor.execute(new BubbleController$6$$ExternalSyntheticLambda0(this, 0));
                }
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.bubbles.BubbleController$7, reason: invalid class name */
    public class AnonymousClass7 extends BroadcastReceiver {
        public AnonymousClass7() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_BUBBLES, -925670237100425792L, 0, String.valueOf(intent.getAction()));
            }
            if ("com.android.wm.shell.bubbles.action.SHOW_BUBBLES".equals(intent.getAction())) {
                BubbleController.this.mMainExecutor.execute(new BubbleController$6$$ExternalSyntheticLambda0(this));
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.bubbles.BubbleController$8, reason: invalid class name */
    public class AnonymousClass8 {
        public AnonymousClass8() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v20, types: [com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda37, java.lang.Runnable] */
        public final void expansionChanged(boolean z) {
            boolean z2;
            boolean z3;
            float f;
            boolean z4;
            int i = 1;
            final BubbleStackView bubbleStackView = BubbleController.this.mStackView;
            if (bubbleStackView == null || z == (z2 = bubbleStackView.mIsExpanded)) {
                return;
            }
            ((BubbleStackViewManager$Companion$fromBubbleController$1) bubbleStackView.mManager).$controller.hideCurrentInputMethod(null);
            BubblesManager.AnonymousClass5 anonymousClass5 = ((BubbleController) bubbleStackView.mSysuiProxyProvider).mSysuiProxy;
            anonymousClass5.val$sysuiMainExecutor.execute(new BubblesManager$5$$ExternalSyntheticLambda3(anonymousClass5, anonymousClass5.val$sysUiState, z));
            if (z2) {
                bubbleStackView.stopMonitoringSwipeUpGesture();
                ((HandlerExecutor) bubbleStackView.mMainExecutor).removeCallbacks(bubbleStackView.mDelayedAnimation);
                bubbleStackView.mIsExpansionAnimating = false;
                bubbleStackView.mIsBubbleSwitchAnimating = false;
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 813173073428952041L, 0, null);
                }
                if (bubbleStackView.isManageEduVisible()) {
                    bubbleStackView.mManageEduView.hide();
                }
                bubbleStackView.mIsExpanded = false;
                bubbleStackView.mIsExpansionAnimating = true;
                if (!bubbleStackView.mRemovingLastBubbleWhileExpanded) {
                    bubbleStackView.showScrim(false, null);
                }
                PhysicsAnimationLayout physicsAnimationLayout = bubbleStackView.mBubbleContainer;
                PhysicsAnimationLayout.PhysicsAnimationController physicsAnimationController = physicsAnimationLayout.mController;
                if (physicsAnimationController != null) {
                    physicsAnimationLayout.cancelAllAnimationsOfProperties((DynamicAnimation.ViewProperty[]) physicsAnimationController.getAnimatedProperties().toArray(new DynamicAnimation.ViewProperty[0]));
                }
                FrameLayout frameLayout = bubbleStackView.mAnimatingOutSurfaceContainer;
                PhysicsAnimator.Companion.getClass();
                PhysicsAnimator.Companion.getInstance(frameLayout).cancel();
                bubbleStackView.mAnimatingOutSurfaceContainer.setScaleX(0.0f);
                bubbleStackView.mAnimatingOutSurfaceContainer.setScaleY(0.0f);
                bubbleStackView.mExpandedAnimationController.mPreparingToCollapse = true;
                StackAnimationController stackAnimationController = bubbleStackView.mStackAnimationController;
                PointF pointF = stackAnimationController.mStackPosition;
                boolean isFirstChildXLeftOfCenter = stackAnimationController.mLayout.isFirstChildXLeftOfCenter(pointF.x);
                RectF allowableStackPositionRegion = stackAnimationController.mPositioner.getAllowableStackPositionRegion(stackAnimationController.mBubbleCountSupplier.getAsInt());
                pointF.x = isFirstChildXLeftOfCenter ? allowableStackPositionRegion.left : allowableStackPositionRegion.right;
                bubbleStackView.updateOverflowDotVisibility(false);
                final BubbleStackView$$ExternalSyntheticLambda1 bubbleStackView$$ExternalSyntheticLambda1 = new BubbleStackView$$ExternalSyntheticLambda1(i, bubbleStackView, pointF);
                BubbleStackView$$ExternalSyntheticLambda5 bubbleStackView$$ExternalSyntheticLambda5 = new BubbleStackView$$ExternalSyntheticLambda5(bubbleStackView, 10);
                final ExpandedViewAnimationControllerImpl expandedViewAnimationControllerImpl = bubbleStackView.mExpandedViewAnimationController;
                expandedViewAnimationControllerImpl.getClass();
                boolean z5 = ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0];
                int i2 = expandedViewAnimationControllerImpl.mMinFlingVelocity;
                if (z5) {
                    f = 0.0f;
                    z4 = false;
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 327956261255593019L, 166, Double.valueOf(expandedViewAnimationControllerImpl.mSwipeUpVelocity), Long.valueOf(i2), Double.valueOf(pointF.x), Double.valueOf(pointF.y));
                } else {
                    f = 0.0f;
                    z4 = false;
                }
                BubbleExpandedView bubbleExpandedView = expandedViewAnimationControllerImpl.mExpandedView;
                if (bubbleExpandedView != null) {
                    bubbleExpandedView.mIsAnimating = true;
                    AnimatorSet animatorSet = expandedViewAnimationControllerImpl.mCollapseAnimation;
                    if (animatorSet != null) {
                        animatorSet.cancel();
                    }
                    BubbleExpandedView bubbleExpandedView2 = expandedViewAnimationControllerImpl.mExpandedView;
                    ArrayList arrayList = new ArrayList();
                    ValueAnimator ofInt = ValueAnimator.ofInt((int) expandedViewAnimationControllerImpl.mCollapsedAmount, bubbleExpandedView2.getContentHeight());
                    ofInt.setInterpolator(Interpolators.EMPHASIZED_ACCELERATE);
                    ofInt.setDuration(250L);
                    ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.bubbles.animation.ExpandedViewAnimationControllerImpl$$ExternalSyntheticLambda1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            ExpandedViewAnimationControllerImpl expandedViewAnimationControllerImpl2 = ExpandedViewAnimationControllerImpl.this;
                            ExpandedViewAnimationControllerImpl.AnonymousClass1 anonymousClass1 = ExpandedViewAnimationControllerImpl.COLLAPSE_HEIGHT_PROPERTY;
                            expandedViewAnimationControllerImpl2.getClass();
                            expandedViewAnimationControllerImpl2.setCollapsedAmount(((Integer) valueAnimator.getAnimatedValue()).intValue());
                        }
                    });
                    arrayList.add(ofInt);
                    BubbleExpandedView bubbleExpandedView3 = expandedViewAnimationControllerImpl.mExpandedView;
                    BubbleExpandedView.AnonymousClass4 anonymousClass4 = BubbleExpandedView.MANAGE_BUTTON_ALPHA;
                    float[] fArr = new float[1];
                    fArr[z4 ? 1 : 0] = f;
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(bubbleExpandedView3, anonymousClass4, fArr);
                    ofFloat.setDuration(78L);
                    Interpolator interpolator = Interpolators.LINEAR;
                    ofFloat.setInterpolator(interpolator);
                    arrayList.add(ofFloat);
                    BubbleExpandedView bubbleExpandedView4 = expandedViewAnimationControllerImpl.mExpandedView;
                    BubbleExpandedView.AnonymousClass2 anonymousClass2 = BubbleExpandedView.CONTENT_ALPHA;
                    float[] fArr2 = new float[1];
                    fArr2[z4 ? 1 : 0] = f;
                    ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(bubbleExpandedView4, anonymousClass2, fArr2);
                    ofFloat2.setDuration(78L);
                    ofFloat2.setInterpolator(interpolator);
                    ofFloat2.setStartDelay(93L);
                    final boolean[] zArr = new boolean[1];
                    zArr[z4 ? 1 : 0] = z4;
                    ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.bubbles.animation.ExpandedViewAnimationControllerImpl$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            boolean[] zArr2 = zArr;
                            BubbleStackView$$ExternalSyntheticLambda1 bubbleStackView$$ExternalSyntheticLambda12 = bubbleStackView$$ExternalSyntheticLambda1;
                            ExpandedViewAnimationControllerImpl.AnonymousClass1 anonymousClass1 = ExpandedViewAnimationControllerImpl.COLLAPSE_HEIGHT_PROPERTY;
                            if (zArr2[0] || valueAnimator.getAnimatedFraction() <= 0.5f) {
                                return;
                            }
                            zArr2[0] = true;
                            bubbleStackView$$ExternalSyntheticLambda12.run();
                        }
                    });
                    arrayList.add(ofFloat2);
                    BubbleExpandedView bubbleExpandedView5 = expandedViewAnimationControllerImpl.mExpandedView;
                    BubbleExpandedView.AnonymousClass3 anonymousClass3 = BubbleExpandedView.BACKGROUND_ALPHA;
                    float[] fArr3 = new float[1];
                    fArr3[z4 ? 1 : 0] = f;
                    ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(bubbleExpandedView5, anonymousClass3, fArr3);
                    ofFloat3.setDuration(78L);
                    ofFloat3.setInterpolator(interpolator);
                    ofFloat3.setStartDelay(172L);
                    arrayList.add(ofFloat3);
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    animatorSet2.addListener(new AnimatorListenerAdapter(expandedViewAnimationControllerImpl, bubbleStackView$$ExternalSyntheticLambda5) { // from class: com.android.wm.shell.bubbles.animation.ExpandedViewAnimationControllerImpl.4
                        public final /* synthetic */ Runnable val$after;

                        public AnonymousClass4(final ExpandedViewAnimationControllerImpl expandedViewAnimationControllerImpl2, Runnable bubbleStackView$$ExternalSyntheticLambda52) {
                            this.val$after = bubbleStackView$$ExternalSyntheticLambda52;
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            this.val$after.run();
                        }
                    });
                    animatorSet2.playTogether(arrayList);
                    expandedViewAnimationControllerImpl2.mCollapseAnimation = animatorSet2;
                    if (expandedViewAnimationControllerImpl2.mSwipeUpVelocity >= i2) {
                        int contentHeight = expandedViewAnimationControllerImpl2.mExpandedView.getContentHeight();
                        ValueAnimator valueAnimator = new ValueAnimator();
                        float f2 = expandedViewAnimationControllerImpl2.mCollapsedAmount;
                        float f3 = contentHeight;
                        expandedViewAnimationControllerImpl2.mFlingAnimationUtils.applyDismissing(valueAnimator, f2, f3, expandedViewAnimationControllerImpl2.mSwipeUpVelocity, f3 - f2);
                        float duration = valueAnimator.getDuration() / 250.0f;
                        ArrayList<Animator> childAnimations = expandedViewAnimationControllerImpl2.mCollapseAnimation.getChildAnimations();
                        int size = childAnimations.size();
                        int i3 = z4 ? 1 : 0;
                        while (i3 < size) {
                            Animator animator = childAnimations.get(i3);
                            i3++;
                            Animator animator2 = animator;
                            animator2.setStartDelay((long) (animator2.getStartDelay() * duration));
                            animator2.setDuration((long) (animator2.getDuration() * duration));
                        }
                        expandedViewAnimationControllerImpl2.mCollapseAnimation.setInterpolator(valueAnimator.getInterpolator());
                    }
                    expandedViewAnimationControllerImpl2.mCollapseAnimation.start();
                }
                BubbleExpandedView expandedView = bubbleStackView.getExpandedView();
                if (expandedView != null) {
                    expandedView.setContentVisibility(z4);
                }
                bubbleStackView.showManageMenu(z4);
                bubbleStackView.logBubbleEvent(bubbleStackView.mExpandedBubble, 4);
            } else {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                    BubbleViewProvider bubbleViewProvider = bubbleStackView.mExpandedBubble;
                    z3 = false;
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -394240243237309085L, 0, String.valueOf(bubbleViewProvider != null ? bubbleViewProvider.getKey() : "null"));
                } else {
                    z3 = false;
                }
                ((HandlerExecutor) bubbleStackView.mMainExecutor).removeCallbacks(bubbleStackView.mDelayedAnimation);
                bubbleStackView.mIsExpansionAnimating = z3;
                bubbleStackView.mIsBubbleSwitchAnimating = z3;
                bubbleStackView.mIsExpanded = true;
                if (bubbleStackView.isStackEduVisible()) {
                    bubbleStackView.mStackEduView.hide(true);
                }
                bubbleStackView.mIsExpansionAnimating = true;
                bubbleStackView.hideFlyoutImmediate();
                bubbleStackView.updateExpandedBubble();
                bubbleStackView.updateExpandedView();
                bubbleStackView.showScrim(true, null);
                bubbleStackView.updateBubbleShadows(bubbleStackView.mIsExpanded);
                bubbleStackView.mBubbleContainer.setActiveController(bubbleStackView.mExpandedAnimationController);
                bubbleStackView.updateOverflowVisibility();
                bubbleStackView.updateBadges(false);
                bubbleStackView.updatePointerPosition(false);
                bubbleStackView.mExpandedAnimationController.expandFromStack(new BubbleStackView$$ExternalSyntheticLambda5(bubbleStackView, 11));
                BubbleViewProvider bubbleViewProvider2 = bubbleStackView.mExpandedBubble;
                PointF expandedBubbleXY = bubbleStackView.mPositioner.getExpandedBubbleXY((bubbleViewProvider2 == null || !"Overflow".equals(bubbleViewProvider2.getKey())) ? bubbleStackView.getBubbleIndex(bubbleStackView.mExpandedBubble) : Collections.unmodifiableList(bubbleStackView.mBubbleData.mBubbles).size(), bubbleStackView.getState());
                BubblePositioner bubblePositioner = bubbleStackView.mPositioner;
                float expandedViewY = bubblePositioner.getExpandedViewY(bubbleStackView.mExpandedBubble, bubblePositioner.showBubblesVertically() ? expandedBubbleXY.y : expandedBubbleXY.x);
                bubbleStackView.mExpandedViewContainer.setTranslationX(0.0f);
                bubbleStackView.mExpandedViewContainer.setTranslationY(expandedViewY);
                bubbleStackView.mExpandedViewContainer.setAlpha(1.0f);
                final boolean showBubblesVertically = bubbleStackView.mPositioner.showBubblesVertically();
                float f4 = showBubblesVertically ? bubbleStackView.mStackAnimationController.mStackPosition.y : bubbleStackView.mStackAnimationController.mStackPosition.x;
                final float f5 = showBubblesVertically ? expandedBubbleXY.y : expandedBubbleXY.x;
                long abs = bubbleStackView.getWidth() > 0 ? (long) (((Math.abs(f5 - f4) / bubbleStackView.getWidth()) * 30.0f) + 210.00002f) : 0L;
                if (showBubblesVertically) {
                    bubbleStackView.mExpandedViewContainerMatrix.setScale(0.9f, 0.9f, bubbleStackView.mStackOnLeftOrWillBe ? expandedBubbleXY.x + bubbleStackView.mBubbleSize + bubbleStackView.mExpandedViewPadding : expandedBubbleXY.x - bubbleStackView.mExpandedViewPadding, (bubbleStackView.mBubbleSize / 2.0f) + expandedBubbleXY.y);
                } else {
                    AnimatableScaleMatrix animatableScaleMatrix = bubbleStackView.mExpandedViewContainerMatrix;
                    float f6 = expandedBubbleXY.x;
                    float f7 = bubbleStackView.mBubbleSize;
                    animatableScaleMatrix.setScale(0.9f, 0.9f, (f7 / 2.0f) + f6, expandedBubbleXY.y + f7 + bubbleStackView.mExpandedViewPadding);
                }
                bubbleStackView.mExpandedViewContainer.setAnimationMatrix(bubbleStackView.mExpandedViewContainerMatrix);
                BubbleExpandedView expandedView2 = bubbleStackView.getExpandedView();
                if (expandedView2 != null) {
                    expandedView2.setContentAlpha(0.0f);
                    expandedView2.mPointerView.setAlpha(0.0f);
                    expandedView2.setAlpha(0.0f);
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 7323766367734739256L, 0, String.valueOf(expandedView2.getBubbleKey()));
                    }
                    expandedView2.mIsAnimating = true;
                }
                ?? r0 = new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda37
                    @Override // java.lang.Runnable
                    public final void run() {
                        final BubbleStackView bubbleStackView2 = BubbleStackView.this;
                        final boolean z6 = showBubblesVertically;
                        final float f8 = f5;
                        bubbleStackView2.mExpandedViewAlphaAnimator.start();
                        AnimatableScaleMatrix animatableScaleMatrix2 = bubbleStackView2.mExpandedViewContainerMatrix;
                        PhysicsAnimator.Companion.getClass();
                        PhysicsAnimator.Companion.getInstance(animatableScaleMatrix2).cancel();
                        PhysicsAnimator companion = PhysicsAnimator.Companion.getInstance(bubbleStackView2.mExpandedViewContainerMatrix);
                        companion.spring(AnimatableScaleMatrix.SCALE_X, 499.99997f, 0.0f, bubbleStackView2.mScaleInSpringConfig);
                        companion.spring(AnimatableScaleMatrix.SCALE_Y, 499.99997f, 0.0f, bubbleStackView2.mScaleInSpringConfig);
                        companion.updateListeners.add(new PhysicsAnimator.UpdateListener() { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda38
                            @Override // com.android.wm.shell.shared.animation.PhysicsAnimator.UpdateListener
                            public final void onAnimationUpdateForProperty(Object obj) {
                                BubbleStackView bubbleStackView3 = BubbleStackView.this;
                                BubbleViewProvider bubbleViewProvider3 = bubbleStackView3.mExpandedBubble;
                                if (bubbleViewProvider3 == null || bubbleViewProvider3.getIconView$1() == null) {
                                    return;
                                }
                                bubbleStackView3.mExpandedViewContainerMatrix.postTranslate((z6 ? bubbleStackView3.mExpandedBubble.getIconView$1().getTranslationY() : bubbleStackView3.mExpandedBubble.getIconView$1().getTranslationX()) - f8, 0.0f);
                                bubbleStackView3.mExpandedViewContainer.setAnimationMatrix(bubbleStackView3.mExpandedViewContainerMatrix);
                            }
                        });
                        companion.withEndActions(new BubbleStackView$$ExternalSyntheticLambda5(bubbleStackView2, 12));
                        companion.start();
                    }
                };
                bubbleStackView.mDelayedAnimation = r0;
                ((HandlerExecutor) bubbleStackView.mMainExecutor).executeDelayed(r0, abs);
                bubbleStackView.logBubbleEvent(bubbleStackView.mExpandedBubble, 3);
                bubbleStackView.logBubbleEvent(bubbleStackView.mExpandedBubble, 15);
                BubbleStackViewManager bubbleStackViewManager = bubbleStackView.mManager;
                BubbleStackView$$ExternalSyntheticLambda29 bubbleStackView$$ExternalSyntheticLambda29 = new BubbleStackView$$ExternalSyntheticLambda29(bubbleStackView);
                BubbleController bubbleController = ((BubbleStackViewManager$Companion$fromBubbleController$1) bubbleStackViewManager).$controller;
                BubblesManager.AnonymousClass5 anonymousClass52 = bubbleController.mSysuiProxy;
                anonymousClass52.val$sysuiMainExecutor.execute(new BubblesManager$5$$ExternalSyntheticLambda1(anonymousClass52, new BubbleController$$ExternalSyntheticLambda4(bubbleController, bubbleStackView$$ExternalSyntheticLambda29, 1), 4));
            }
            BubbleViewProvider bubbleViewProvider3 = bubbleStackView.mExpandedBubble;
            boolean z6 = bubbleStackView.mIsExpanded;
            BubbleController$$ExternalSyntheticLambda5 bubbleController$$ExternalSyntheticLambda5 = bubbleStackView.mExpandListener;
            if (bubbleController$$ExternalSyntheticLambda5 != null && bubbleViewProvider3 != null) {
                bubbleController$$ExternalSyntheticLambda5.onBubbleExpandChanged(bubbleViewProvider3.getKey(), z6);
            }
            BubbleViewProvider bubbleViewProvider4 = bubbleStackView.mExpandedBubble;
            boolean z7 = bubbleStackView.mIsExpanded;
            if (bubbleViewProvider4 instanceof Bubble) {
                Bubble bubble = (Bubble) bubbleViewProvider4;
                String str = bubble.mAppName;
                String str2 = bubble.mTitle;
                if (str2 == null) {
                    str2 = bubbleStackView.getResources().getString(R.string.notification_bubble_title);
                }
                if (str != null && !str2.equals(str)) {
                    str2 = bubbleStackView.getResources().getString(R.string.bubble_content_description_single, str2, str);
                }
                bubbleStackView.announceForAccessibility(bubbleStackView.getResources().getString(z7 ? R.string.bubble_accessibility_announce_expand : R.string.bubble_accessibility_announce_collapse, str2));
            }
        }

        public final void suppressionChanged(Bubble bubble, boolean z) {
            BubbleStackView bubbleStackView = BubbleController.this.mStackView;
            if (bubbleStackView != null) {
                if (z) {
                    bubbleStackView.mBubbleContainer.removeViewAt(bubbleStackView.getBubbleIndex(bubble));
                    bubbleStackView.updateExpandedView();
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
                int indexOf = Collections.unmodifiableList(bubbleStackView.mBubbleData.mBubbles).indexOf(bubble);
                PhysicsAnimationLayout physicsAnimationLayout = bubbleStackView.mBubbleContainer;
                BadgedImageView badgedImageView2 = bubble.mIconView;
                int i = bubbleStackView.mPositioner.mBubbleSize;
                physicsAnimationLayout.addViewInternal(badgedImageView2, indexOf, new FrameLayout.LayoutParams(i, i), false);
                bubbleStackView.updateBubbleShadows(bubbleStackView.mIsExpanded);
                bubbleStackView.requestUpdate();
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BubbleTaskViewController implements TaskViewController {
        public final TaskViewTransitions mBaseTransitions;

        public BubbleTaskViewController(TaskViewTransitions taskViewTransitions) {
            this.mBaseTransitions = taskViewTransitions;
        }

        @Override // com.android.wm.shell.taskview.TaskViewController
        public final boolean isUsingShellTransitions() {
            return this.mBaseTransitions.mTransitions.mIsRegistered;
        }

        @Override // com.android.wm.shell.taskview.TaskViewController
        public final void moveTaskViewToFullscreen(TaskViewTaskController taskViewTaskController) {
            Bubble bubble;
            ActivityManager.RunningTaskInfo runningTaskInfo = taskViewTaskController.mTaskInfo;
            if (runningTaskInfo == null) {
                return;
            }
            BubbleController bubbleController = BubbleController.this;
            Iterator it = Collections.unmodifiableList(bubbleController.mBubbleData.mBubbles).iterator();
            while (true) {
                if (!it.hasNext()) {
                    bubble = null;
                    break;
                } else {
                    bubble = (Bubble) it.next();
                    if (bubble.getTaskId() == ((TaskInfo) runningTaskInfo).taskId) {
                        break;
                    }
                }
            }
            if (bubble == null) {
                return;
            }
            BubbleTransitions bubbleTransitions = bubbleController.mBubbleTransitions;
            bubbleTransitions.getClass();
            bubbleTransitions.new ConvertFromBubble(bubble, runningTaskInfo);
        }

        @Override // com.android.wm.shell.taskview.TaskViewController
        public final void registerTaskView(TaskViewTaskController taskViewTaskController) {
            this.mBaseTransitions.registerTaskView(taskViewTaskController);
        }

        @Override // com.android.wm.shell.taskview.TaskViewController
        public final void removeTaskView(TaskViewTaskController taskViewTaskController, WindowContainerToken windowContainerToken) {
            this.mBaseTransitions.removeTaskView(taskViewTaskController, windowContainerToken);
        }

        @Override // com.android.wm.shell.taskview.TaskViewController
        public final void setTaskBounds(TaskViewTaskController taskViewTaskController, Rect rect) {
            this.mBaseTransitions.setTaskBounds(taskViewTaskController, rect);
        }

        @Override // com.android.wm.shell.taskview.TaskViewController
        public final void setTaskViewVisible(TaskViewTaskController taskViewTaskController, boolean z) {
            this.mBaseTransitions.setTaskViewVisible(taskViewTaskController, z);
        }

        @Override // com.android.wm.shell.taskview.TaskViewController
        public final void startActivity(TaskViewTaskController taskViewTaskController, PendingIntent pendingIntent, Intent intent, ActivityOptions activityOptions, Rect rect) {
            this.mBaseTransitions.startActivity(taskViewTaskController, pendingIntent, intent, activityOptions, rect);
        }

        @Override // com.android.wm.shell.taskview.TaskViewController
        public final void startShortcutActivity(TaskViewTaskController taskViewTaskController, ShortcutInfo shortcutInfo, ActivityOptions activityOptions, Rect rect) {
            this.mBaseTransitions.startShortcutActivity(taskViewTaskController, shortcutInfo, activityOptions, rect);
        }

        @Override // com.android.wm.shell.taskview.TaskViewController
        public final void unregisterTaskView(TaskViewTaskController taskViewTaskController) {
            this.mBaseTransitions.unregisterTaskView(taskViewTaskController);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BubblesImeListener extends ImeListener implements DisplayImeController.ImePositionProcessor {
        public BubblesImeListener(DisplayController displayController, int i) {
            super(displayController, i);
        }

        @Override // com.android.wm.shell.common.DisplayImeController.ImePositionProcessor
        public final void onImePositionChanged(int i, int i2, SurfaceControl.Transaction transaction) {
            BubbleController.this.mContext.getDisplayId();
        }

        @Override // com.android.wm.shell.common.DisplayImeController.ImePositionProcessor
        public final int onImeStartPositioning(int i, int i2, boolean z, boolean z2, int i3) {
            BubbleController bubbleController = BubbleController.this;
            if (bubbleController.mContext.getDisplayId() == i) {
                BubblePositioner bubblePositioner = bubbleController.mBubblePositioner;
                if (z) {
                    bubblePositioner.setImeVisible(i2 - i3, true);
                } else {
                    bubblePositioner.setImeVisible(0, false);
                }
                BubbleStackView bubbleStackView = bubbleController.mStackView;
                if (bubbleStackView != null) {
                    bubbleStackView.setImeVisible(z);
                }
            }
            return 0;
        }

        @Override // com.android.wm.shell.common.ImeListener
        public final void onImeVisibilityChanged(boolean z, int i) {
            Runnable runnable;
            BubbleController bubbleController = BubbleController.this;
            if (this.displayId != bubbleController.mContext.getDisplayId()) {
                return;
            }
            BubblePositioner bubblePositioner = bubbleController.mBubblePositioner;
            bubblePositioner.setImeVisible(i + bubblePositioner.mInsets.bottom, z);
            BubbleStackView bubbleStackView = bubbleController.mStackView;
            if (bubbleStackView != null) {
                bubbleStackView.setImeVisible(z);
                if (z || (runnable = bubbleController.mOnImeHidden) == null) {
                    return;
                }
                runnable.run();
                bubbleController.mOnImeHidden = null;
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BubblesImpl implements Bubbles {
        public final CachedState mCachedState;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class CachedState {
            public boolean mIsStackExpanded;
            public String mSelectedBubbleKey;
            public final HashSet mSuppressedBubbleKeys = new HashSet();
            public final HashMap mSuppressedGroupToNotifKeys = new HashMap();
            public final HashMap mShortcutIdToBubble = new HashMap();
            public final HashMap mNoteBubbleTaskIds = new HashMap();
            public final ArrayList mTmpBubbles = new ArrayList();

            public CachedState() {
            }

            public final synchronized void updateBubbleSuppressedState(Bubble bubble) {
                try {
                    if (bubble.showInShade()) {
                        this.mSuppressedBubbleKeys.remove(bubble.mKey);
                    } else {
                        this.mSuppressedBubbleKeys.add(bubble.mKey);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public /* synthetic */ BubblesImpl(BubbleController bubbleController, int i) {
            this();
        }

        public final boolean isBubbleExpanded(String str) {
            boolean z;
            CachedState cachedState = this.mCachedState;
            synchronized (cachedState) {
                if (cachedState.mIsStackExpanded) {
                    z = str.equals(cachedState.mSelectedBubbleKey);
                }
            }
            return z;
        }

        /* JADX WARN: Code restructure failed: missing block: B:9:0x001d, code lost:
        
            if (r2.equals(r1.mSuppressedGroupToNotifKeys.get(r3)) != false) goto L14;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean isBubbleNotificationSuppressedFromShade(java.lang.String r2, java.lang.String r3) {
            /*
                r1 = this;
                com.android.wm.shell.bubbles.BubbleController$BubblesImpl$CachedState r1 = r1.mCachedState
                monitor-enter(r1)
                java.util.HashSet r0 = r1.mSuppressedBubbleKeys     // Catch: java.lang.Throwable -> L20
                boolean r0 = r0.contains(r2)     // Catch: java.lang.Throwable -> L20
                if (r0 != 0) goto L24
                java.util.HashMap r0 = r1.mSuppressedGroupToNotifKeys     // Catch: java.lang.Throwable -> L20
                boolean r0 = r0.containsKey(r3)     // Catch: java.lang.Throwable -> L20
                if (r0 == 0) goto L22
                java.util.HashMap r0 = r1.mSuppressedGroupToNotifKeys     // Catch: java.lang.Throwable -> L20
                java.lang.Object r3 = r0.get(r3)     // Catch: java.lang.Throwable -> L20
                boolean r2 = r2.equals(r3)     // Catch: java.lang.Throwable -> L20
                if (r2 == 0) goto L22
                goto L24
            L20:
                r2 = move-exception
                goto L27
            L22:
                r2 = 0
                goto L25
            L24:
                r2 = 1
            L25:
                monitor-exit(r1)
                return r2
            L27:
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L20
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.bubbles.BubbleController.BubblesImpl.isBubbleNotificationSuppressedFromShade(java.lang.String, java.lang.String):boolean");
        }

        private BubblesImpl() {
            this.mCachedState = new CachedState();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class IBubblesImpl extends IBubbles$Stub implements ExternalInterfaceBinder {
        public static final /* synthetic */ int $r8$clinit = 0;
        public BubbleController mController;
        public final SingleInstanceRemoteListener mListener;

        public IBubblesImpl(BubbleController bubbleController) {
            new Object() { // from class: com.android.wm.shell.bubbles.BubbleController.IBubblesImpl.1
            };
            this.mController = bubbleController;
            this.mListener = new SingleInstanceRemoteListener(bubbleController, new BubbleController$$ExternalSyntheticLambda9(this, 2), new BubbleController$IBubblesImpl$$ExternalSyntheticLambda11());
        }

        @Override // com.android.wm.shell.common.ExternalInterfaceBinder
        public final void invalidate() {
            this.mController = null;
            this.mListener.unregister();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class UserBubbleData {
        public final Map mKeyToShownInShadeMap;

        public /* synthetic */ UserBubbleData(int i) {
            this();
        }

        private UserBubbleData() {
            this.mKeyToShownInShadeMap = new HashMap();
        }
    }

    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.wm.shell.bubbles.BubbleController$9] */
    public BubbleController(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, ShellController shellController, BubbleData bubbleData, BubbleStackView.SurfaceSynchronizer surfaceSynchronizer, FloatingContentCoordinator floatingContentCoordinator, BubbleDataRepository bubbleDataRepository, IStatusBarService iStatusBarService, WindowManager windowManager, DisplayInsetsController displayInsetsController, DisplayImeController displayImeController, UserManager userManager, LauncherApps launcherApps, BubbleLogger bubbleLogger, TaskStackListenerImpl taskStackListenerImpl, ShellTaskOrganizer shellTaskOrganizer, BubblePositioner bubblePositioner, DisplayController displayController, Optional<OneHandedController> optional, DragAndDropController dragAndDropController, ShellExecutor shellExecutor, Handler handler, ShellExecutor shellExecutor2, TaskViewRepository taskViewRepository, TaskViewTransitions taskViewTransitions, Transitions transitions, SyncTransactionQueue syncTransactionQueue, IWindowManager iWindowManager, ResizabilityChecker resizabilityChecker) {
        new HashSet();
        this.mBroadcastReceiver = new AnonymousClass6();
        this.mShortcutBroadcastReceiver = new AnonymousClass7();
        this.mBubbleStackViewCallback = new AnonymousClass8();
        this.mBubbleBarViewCallback = new Object() { // from class: com.android.wm.shell.bubbles.BubbleController.9
        };
        this.mBubbleDataListener = new AnonymousClass10();
        this.mContext = context;
        this.mShellCommandHandler = shellCommandHandler;
        this.mShellController = shellController;
        this.mLauncherApps = launcherApps;
        this.mBarService = iStatusBarService == null ? IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar")) : iStatusBarService;
        this.mWindowManager = windowManager;
        this.mDisplayInsetsController = displayInsetsController;
        this.mDisplayImeController = displayImeController;
        this.mUserManager = userManager;
        this.mFloatingContentCoordinator = floatingContentCoordinator;
        this.mDataRepository = bubbleDataRepository;
        this.mLogger = bubbleLogger;
        this.mMainExecutor = shellExecutor;
        this.mMainHandler = handler;
        this.mBackgroundExecutor = shellExecutor2;
        this.mTaskStackListener = taskStackListenerImpl;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mSurfaceSynchronizer = surfaceSynchronizer;
        this.mCurrentUserId = ActivityManager.getCurrentUser();
        this.mBubblePositioner = bubblePositioner;
        this.mBubbleData = bubbleData;
        this.mSavedUserBubbleData = new SparseArray();
        this.mBubbleIconFactory = new BubbleIconFactory(context);
        this.mBubbleBadgeIconFactory = new BubbleBadgeIconFactory(context);
        this.mDisplayController = displayController;
        this.mTaskViewController = new BubbleTaskViewController(taskViewTransitions);
        this.mBubbleTransitions = new BubbleTransitions(transitions, shellTaskOrganizer, taskViewRepository, bubbleData, taskViewTransitions, context);
        this.mTransitions = transitions;
        this.mOneHandedOptional = optional;
        this.mDragAndDropController = dragAndDropController;
        this.mWmService = iWindowManager;
        shellInit.addInitCallback(new BubbleController$$ExternalSyntheticLambda1(this, 0), this);
        this.mBubbleTaskViewFactory = new AnonymousClass1(context, shellTaskOrganizer, syncTransactionQueue, shellExecutor);
        BubbleExpandedViewManager.Companion.getClass();
        this.mExpandedViewManager = new BubbleExpandedViewManager$Companion$fromBubbleController$1(this);
        this.mResizabilityChecker = resizabilityChecker;
    }

    public static PackageManager getPackageManagerForUser(int i, Context context) {
        if (i >= 0) {
            try {
                context = context.createPackageContextAsUser(context.getPackageName(), 4, new UserHandle(i));
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return context.getPackageManager();
    }

    public Bubbles asBubbles() {
        return this.mImpl;
    }

    public final boolean canLaunchInTaskView(Context context, BubbleEntry bubbleEntry) {
        PendingIntent intent = bubbleEntry.getBubbleMetadata() != null ? bubbleEntry.getBubbleMetadata().getIntent() : null;
        if (bubbleEntry.getBubbleMetadata() != null && bubbleEntry.getBubbleMetadata().getShortcutId() != null) {
            return true;
        }
        if (intent != null) {
            return ((BubbleResizabilityChecker) this.mResizabilityChecker).isResizableActivity(intent.getIntent(), getPackageManagerForUser(bubbleEntry.mSbn.getUser().getIdentifier(), context), bubbleEntry.mSbn.getKey());
        }
        Log.w("Bubbles", "Unable to create bubble -- no intent: " + bubbleEntry.mSbn.getKey());
        return false;
    }

    public final void collapseStack() {
        this.mBubbleData.setExpanded(false);
    }

    public final void ensureBubbleViewsAndWindowCreated() {
        final BubbleController bubbleController;
        this.mBubblePositioner.getClass();
        if (this.mStackView == null) {
            BubbleStackViewManager.Companion.getClass();
            BubbleStackViewManager$Companion$fromBubbleController$1 bubbleStackViewManager$Companion$fromBubbleController$1 = new BubbleStackViewManager$Companion$fromBubbleController$1(this);
            bubbleController = this;
            BubbleStackView bubbleStackView = new BubbleStackView(this.mContext, bubbleController, bubbleStackViewManager$Companion$fromBubbleController$1, this.mBubblePositioner, this.mBubbleData, this.mSurfaceSynchronizer, this.mFloatingContentCoordinator, this, this.mMainExecutor);
            bubbleController.mStackView = bubbleStackView;
            bubbleStackView.onOrientationChanged();
            BubbleController$$ExternalSyntheticLambda5 bubbleController$$ExternalSyntheticLambda5 = bubbleController.mExpandListener;
            if (bubbleController$$ExternalSyntheticLambda5 != null) {
                bubbleController.mStackView.mExpandListener = bubbleController$$ExternalSyntheticLambda5;
            }
            BubbleStackView bubbleStackView2 = bubbleController.mStackView;
            Objects.requireNonNull(bubbleController.mSysuiProxy);
            bubbleStackView2.getClass();
        } else {
            bubbleController = this;
        }
        if (bubbleController.mAddedToWindowManager || bubbleController.mStackView == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2038, 25165864, -3);
        bubbleController.mWmLayoutParams = layoutParams;
        layoutParams.setTrustedOverlay();
        bubbleController.mWmLayoutParams.setFitInsetsTypes(0);
        WindowManager.LayoutParams layoutParams2 = bubbleController.mWmLayoutParams;
        layoutParams2.softInputMode = 16;
        layoutParams2.token = new Binder();
        bubbleController.mWmLayoutParams.setTitle("Bubbles!");
        bubbleController.mWmLayoutParams.packageName = bubbleController.mContext.getPackageName();
        WindowManager.LayoutParams layoutParams3 = bubbleController.mWmLayoutParams;
        layoutParams3.layoutInDisplayCutoutMode = 3;
        layoutParams3.privateFlags = 16 | layoutParams3.privateFlags;
        try {
            bubbleController.mAddedToWindowManager = true;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            bubbleController.mContext.registerReceiver(bubbleController.mBroadcastReceiver, intentFilter, 2);
            BubbleOverflow bubbleOverflow = bubbleController.mBubbleData.mOverflow;
            BubbleExpandedViewManager$Companion$fromBubbleController$1 bubbleExpandedViewManager$Companion$fromBubbleController$1 = bubbleController.mExpandedViewManager;
            BubbleStackView bubbleStackView3 = bubbleController.mStackView;
            BubblePositioner bubblePositioner = bubbleController.mBubblePositioner;
            BubbleExpandedView bubbleExpandedView = (BubbleExpandedView) bubbleOverflow.inflater.inflate(R.layout.bubble_expanded_view, (ViewGroup) null, false);
            bubbleExpandedView.applyThemeAttrs();
            bubbleOverflow.expandedView = bubbleExpandedView;
            bubbleOverflow.updateResources();
            bubbleExpandedView.initialize(bubbleExpandedViewManager$Companion$fromBubbleController$1, bubbleStackView3, bubblePositioner, true, null);
            bubbleController.mWindowManager.addView(bubbleController.mStackView, bubbleController.mWmLayoutParams);
            bubbleController.mStackView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda16
                @Override // android.view.View.OnApplyWindowInsetsListener
                public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    BubbleController bubbleController2 = BubbleController.this;
                    if (!windowInsets.equals(bubbleController2.mWindowInsets) && bubbleController2.mStackView != null) {
                        bubbleController2.mWindowInsets = windowInsets;
                        bubbleController2.mBubblePositioner.update(DeviceConfig.create(bubbleController2.mContext, bubbleController2.mWindowManager));
                        bubbleController2.mStackView.onDisplaySizeChanged();
                    }
                    return windowInsets;
                }
            });
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public final void expandStackAndSelectBubble(BubbleEntry bubbleEntry) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -2934966420708968428L, 12, String.valueOf(bubbleEntry.mSbn.getKey()), Boolean.valueOf(this.mIsStatusBarShade));
        }
        if (!this.mIsStatusBarShade) {
            this.mNotifEntryToExpandOnShadeUnlock = bubbleEntry;
            return;
        }
        this.mNotifEntryToExpandOnShadeUnlock = null;
        String key = bubbleEntry.mSbn.getKey();
        BubbleData bubbleData = this.mBubbleData;
        Bubble bubbleInStackWithKey = bubbleData.getBubbleInStackWithKey(key);
        if (bubbleInStackWithKey != null) {
            bubbleData.setSelectedBubbleAndExpandStack(bubbleInStackWithKey);
            return;
        }
        Bubble overflowBubbleWithKey = bubbleData.getOverflowBubbleWithKey(key);
        if (overflowBubbleWithKey != null) {
            promoteBubbleFromOverflow(overflowBubbleWithKey);
        } else if (bubbleEntry.mRanking.canBubble()) {
            setIsBubble(bubbleEntry, true, true);
        }
    }

    public void expandStackAndSelectBubbleFromLauncher(String str, int i) {
        this.mBubblePositioner.mBubbleBarTopOnScreen = i;
        boolean equals = "Overflow".equals(str);
        BubbleData bubbleData = this.mBubbleData;
        if (equals) {
            bubbleData.setSelectedBubbleFromLauncher(bubbleData.mOverflow);
            throw null;
        }
        Bubble anyBubbleWithKey = bubbleData.getAnyBubbleWithKey(str);
        if (anyBubbleWithKey == null) {
            return;
        }
        String str2 = anyBubbleWithKey.mKey;
        if (bubbleData.hasBubbleInStackWithKey(str2)) {
            bubbleData.setSelectedBubbleFromLauncher(anyBubbleWithKey);
            throw null;
        }
        if (bubbleData.hasOverflowBubbleWithKey(str2)) {
            return;
        }
        MotionLayout$$ExternalSyntheticOutline0.m("didn't add bubble from launcher: ", str, "Bubbles");
    }

    public final ArrayList getBubblesInGroup(String str) {
        ArrayList arrayList = new ArrayList();
        if (str != null) {
            for (Bubble bubble : Collections.unmodifiableList(this.mBubbleData.mBubbles)) {
                String str2 = bubble.mGroupKey;
                if (str2 != null && str.equals(str2)) {
                    arrayList.add(bubble);
                }
            }
        }
        return arrayList;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final Context getContext() {
        return this.mContext;
    }

    public BubblesImpl.CachedState getImplCachedState() {
        return this.mImpl.mCachedState;
    }

    public BubbleBarLayerView getLayerView() {
        return null;
    }

    public BubblePositioner getPositioner() {
        return this.mBubblePositioner;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final ShellExecutor getRemoteCallExecutor() {
        return this.mMainExecutor;
    }

    public BubbleStackView getStackView() {
        return this.mStackView;
    }

    public boolean hasBubbles() {
        if (this.mStackView == null) {
            return false;
        }
        BubbleData bubbleData = this.mBubbleData;
        if (((ArrayList) bubbleData.mBubbles).isEmpty()) {
            return bubbleData.mShowingOverflow && bubbleData.mExpanded;
        }
        return true;
    }

    public final void hideCurrentInputMethod(Runnable runnable) {
        ImeTracker.Token imeStatsToken;
        this.mOnImeHidden = runnable;
        this.mBubblePositioner.setImeVisible(0, false);
        int displayId = this.mWindowManager.getDefaultDisplay().getDisplayId();
        if (this.mIsStatusBarShade) {
            try {
                this.mBarService.hideCurrentInputMethodForBubbles(displayId);
                return;
            } catch (RemoteException e) {
                Log.e("Bubbles", "Failed to hide IME", e);
                return;
            }
        }
        DisplayImeController.PerDisplay perDisplay = (DisplayImeController.PerDisplay) this.mDisplayImeController.mImePerDisplay.get(displayId);
        InsetsSourceControl imeSourceControl = perDisplay.getImeSourceControl();
        if (imeSourceControl == null || (imeStatsToken = imeSourceControl.getImeStatsToken()) == null) {
            return;
        }
        perDisplay.setImeInputTargetRequestedVisibility(false, imeStatsToken);
    }

    public void inflateAndAdd(Bubble bubble, boolean z, boolean z2) {
        inflateAndAdd(bubble, z, z2, null);
    }

    public boolean isBubbleNotificationSuppressedFromShade(String str, String str2) {
        BubbleData bubbleData = this.mBubbleData;
        return (str.equals((String) bubbleData.mSuppressedGroupKeys.get(str2)) && bubbleData.isSummarySuppressed(str2)) || (bubbleData.hasAnyBubbleWithKey(str) && !bubbleData.getAnyBubbleWithKey(str).showInShade());
    }

    public final boolean isSummaryOfBubbles(BubbleEntry bubbleEntry) {
        String groupKey = bubbleEntry.mSbn.getGroupKey();
        ArrayList bubblesInGroup = getBubblesInGroup(groupKey);
        BubbleData bubbleData = this.mBubbleData;
        return ((bubbleData.isSummarySuppressed(groupKey) && ((String) bubbleData.mSuppressedGroupKeys.get(groupKey)).equals(bubbleEntry.mSbn.getKey())) || bubbleEntry.mSbn.getNotification().isGroupSummary()) && !bubblesInGroup.isEmpty();
    }

    public final void loadOverflowBubblesFromDisk() {
        if (this.mOverflowDataLoadNeeded) {
            this.mOverflowDataLoadNeeded = false;
            List list = this.mUserManager.getAliveUsers().stream().map(new BubbleController$$ExternalSyntheticLambda18()).toList();
            int i = this.mCurrentUserId;
            Function1 function1 = new Function1() { // from class: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda19
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    BubbleController bubbleController = BubbleController.this;
                    bubbleController.getClass();
                    ((List) obj).forEach(new BubbleController$$ExternalSyntheticLambda9(bubbleController, 1));
                    return null;
                }
            };
            BubbleDataRepository bubbleDataRepository = this.mDataRepository;
            BuildersKt.launch$default(bubbleDataRepository.coroutineScope, null, null, new BubbleDataRepository$loadBubbles$1(bubbleDataRepository, list, i, function1, null), 3);
        }
    }

    public void onBubbleMetadataFlagChanged(Bubble bubble) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 2312076315504885239L, 4, String.valueOf(bubble.mKey), Long.valueOf(bubble.mFlags));
        }
        try {
            this.mBarService.onBubbleMetadataFlagChanged(bubble.mKey, bubble.mFlags);
        } catch (RemoteException unused) {
        }
        this.mImpl.mCachedState.updateBubbleSuppressedState(bubble);
    }

    @Override // com.android.wm.shell.sysui.ConfigurationChangeListener
    public final void onConfigurationChanged(Configuration configuration) {
        BubbleOverflow bubbleOverflow;
        BubbleExpandedView bubbleExpandedView;
        BubbleOverflow bubbleOverflow2;
        BubbleExpandedView bubbleExpandedView2;
        BubblePositioner bubblePositioner = this.mBubblePositioner;
        if (bubblePositioner != null) {
            bubblePositioner.update(DeviceConfig.create(this.mContext, this.mWindowManager));
        }
        if (this.mStackView != null) {
            if (configuration.densityDpi != this.mDensityDpi || !configuration.windowConfiguration.getBounds().equals(this.mScreenBounds)) {
                this.mDensityDpi = configuration.densityDpi;
                this.mScreenBounds.set(configuration.windowConfiguration.getBounds());
                BubbleData bubbleData = this.mBubbleData;
                bubbleData.mMaxBubbles = bubbleData.mPositioner.mMaxBubbles;
                if (bubbleData.mExpanded) {
                    bubbleData.mNeedsTrimming = true;
                } else {
                    bubbleData.trim();
                    bubbleData.dispatchPendingChanges();
                }
                this.mBubbleIconFactory = new BubbleIconFactory(this.mContext);
                this.mBubbleBadgeIconFactory = new BubbleBadgeIconFactory(this.mContext);
                this.mStackView.onDisplaySizeChanged();
            }
            float f = configuration.fontScale;
            if (f != this.mFontScale) {
                this.mFontScale = f;
                BubbleStackView bubbleStackView = this.mStackView;
                bubbleStackView.mFlyout.updateFontSize();
                Iterator it = Collections.unmodifiableList(bubbleStackView.mBubbleData.mBubbles).iterator();
                while (it.hasNext()) {
                    BubbleExpandedView bubbleExpandedView3 = ((Bubble) it.next()).mExpandedView;
                }
                if (bubbleStackView.mShowingOverflow && (bubbleOverflow2 = bubbleStackView.mBubbleOverflow) != null && (bubbleExpandedView2 = bubbleOverflow2.expandedView) != null) {
                    bubbleExpandedView2.updateFontSize();
                }
            }
            if (configuration.getLayoutDirection() != this.mLayoutDirection) {
                final int layoutDirection = configuration.getLayoutDirection();
                this.mLayoutDirection = layoutDirection;
                BubbleStackView bubbleStackView2 = this.mStackView;
                bubbleStackView2.mFlyout.setLayoutDirection(layoutDirection);
                StackEducationView stackEducationView = bubbleStackView2.mStackEduView;
                if (stackEducationView != null) {
                    stackEducationView.setLayoutDirection(layoutDirection);
                }
                ManageEducationView manageEducationView = bubbleStackView2.mManageEduView;
                if (manageEducationView != null) {
                    manageEducationView.setLayoutDirection(layoutDirection);
                }
                List unmodifiableList = Collections.unmodifiableList(bubbleStackView2.mBubbleData.mBubbles);
                if (!unmodifiableList.isEmpty()) {
                    unmodifiableList.forEach(new Consumer() { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i = layoutDirection;
                            PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                            BubbleExpandedView bubbleExpandedView4 = ((Bubble) obj).mExpandedView;
                            if (bubbleExpandedView4 != null) {
                                bubbleExpandedView4.setLayoutDirection(i);
                            }
                        }
                    });
                }
            }
            Locale locale = configuration.locale;
            if (locale == null || locale.equals(this.mLocale)) {
                return;
            }
            this.mLocale = locale;
            BubbleStackView bubbleStackView3 = this.mStackView;
            if (!bubbleStackView3.mShowingOverflow || (bubbleOverflow = bubbleStackView3.mBubbleOverflow) == null || (bubbleExpandedView = bubbleOverflow.expandedView) == null) {
                return;
            }
            bubbleExpandedView.updateLocale();
        }
    }

    public void onEntryUpdated(BubbleEntry bubbleEntry, boolean z, boolean z2) {
        if (z2) {
            boolean z3 = z && canLaunchInTaskView(this.mContext, bubbleEntry);
            BubbleData bubbleData = this.mBubbleData;
            if (!z3 && bubbleData.hasAnyBubbleWithKey(bubbleEntry.mSbn.getKey())) {
                removeBubble(7, bubbleEntry.mSbn.getKey());
            } else if (z3 && bubbleEntry.isBubble()) {
                updateBubble(bubbleEntry);
            }
            String groupKey = bubbleEntry.mSbn.getGroupKey();
            if (isSummaryOfBubbles(bubbleEntry) && bubbleData.isSummarySuppressed(groupKey)) {
                bubbleData.mSuppressedGroupKeys.remove(groupKey);
                BubbleData.Update update = bubbleData.mStateChange;
                update.suppressedSummaryChanged = true;
                update.suppressedSummaryGroup = groupKey;
                bubbleData.dispatchPendingChanges();
            }
        }
    }

    public void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        BubbleData bubbleData = this.mBubbleData;
        ArrayList arrayList = new ArrayList(bubbleData.getOverflowBubbles());
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            Bubble bubble = (Bubble) arrayList.get(i2);
            ShortcutInfo shortcutInfo = bubble.mShortcutInfo;
            if (Objects.equals(shortcutInfo != null ? shortcutInfo.getId() : bubble.mMetadataShortcutId, notificationChannel.getConversationId()) && bubble.mPackageName.equals(str) && bubble.mUser.getIdentifier() == userHandle.getIdentifier() && (!notificationChannel.canBubble() || notificationChannel.isDeleted())) {
                bubbleData.dismissBubbleWithKey(7, bubble.mKey);
            }
        }
    }

    public void onRankingUpdated(NotificationListenerService.RankingMap rankingMap, HashMap<String, Pair<BubbleEntry, Boolean>> hashMap) {
        int identifier;
        SparseArray sparseArray;
        if (this.mTmpRanking == null) {
            this.mTmpRanking = new NotificationListenerService.Ranking();
        }
        for (String str : rankingMap.getOrderedKeys()) {
            Pair<BubbleEntry, Boolean> pair = hashMap.get(str);
            BubbleEntry bubbleEntry = (BubbleEntry) pair.first;
            boolean booleanValue = ((Boolean) pair.second).booleanValue();
            if (bubbleEntry != null && (identifier = bubbleEntry.mSbn.getUser().getIdentifier()) != -1 && ((sparseArray = this.mCurrentProfiles) == null || sparseArray.get(identifier) == null)) {
                return;
            }
            if (bubbleEntry != null && (bubbleEntry.mShouldSuppressNotificationList || bubbleEntry.mRanking.isSuspended())) {
                booleanValue = false;
            }
            rankingMap.getRanking(str, this.mTmpRanking);
            BubbleData bubbleData = this.mBubbleData;
            boolean hasAnyBubbleWithKey = bubbleData.hasAnyBubbleWithKey(str);
            bubbleData.hasBubbleInStackWithKey(str);
            if (hasAnyBubbleWithKey && !this.mTmpRanking.canBubble()) {
                bubbleData.dismissBubbleWithKey(4, str);
            } else if (hasAnyBubbleWithKey && !booleanValue) {
                bubbleData.dismissBubbleWithKey(14, str);
            } else if (bubbleEntry != null && this.mTmpRanking.isBubble() && !hasAnyBubbleWithKey) {
                bubbleEntry.setFlagBubble(true);
                onEntryUpdated(bubbleEntry, booleanValue, true);
            }
        }
    }

    public void onStatusBarStateChanged(boolean z) {
        boolean z2 = this.mIsStatusBarShade != z;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
            BubbleEntry bubbleEntry = this.mNotifEntryToExpandOnShadeUnlock;
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -6204015995872987500L, 15, Boolean.valueOf(z), Boolean.valueOf(z2), String.valueOf(bubbleEntry != null ? bubbleEntry.mSbn.getKey() : "null"));
        }
        this.mIsStatusBarShade = z;
        if (!z && z2) {
            if (!this.mBubbleData.mExpanded) {
                Runnable runnable = this.mOnImeHidden;
                if (runnable != null) {
                    hideCurrentInputMethod(runnable);
                }
            } else if (this.mBubblePositioner.mImeVisible) {
                hideCurrentInputMethod(new BubbleController$$ExternalSyntheticLambda1(this, 2));
            } else {
                collapseStack();
            }
            if (this.mStackView != null) {
                MotionEvent.obtain(0L, 0L, 0, 0.0f, 0.0f, 0);
                BubbleStackView bubbleStackView = this.mStackView;
                bubbleStackView.mStackAnimationController.setStackPosition(bubbleStackView.mPositioner.getRestingPosition());
                bubbleStackView.mDismissView.hide();
                this.mStackView.resetDismissAnimator();
            }
        }
        BubbleEntry bubbleEntry2 = this.mNotifEntryToExpandOnShadeUnlock;
        if (bubbleEntry2 != null) {
            expandStackAndSelectBubble(bubbleEntry2);
        }
        updateBubbleViews();
    }

    @Override // com.android.wm.shell.sysui.ConfigurationChangeListener
    public final void onThemeChanged() {
        AnonymousClass1 anonymousClass1;
        BubbleExpandedViewManager$Companion$fromBubbleController$1 bubbleExpandedViewManager$Companion$fromBubbleController$1;
        BubbleStackView bubbleStackView = this.mStackView;
        if (bubbleStackView != null) {
            bubbleStackView.setUpFlyout();
            bubbleStackView.setUpDismissView();
            bubbleStackView.updateOverflow();
            bubbleStackView.updateUserEdu();
            List unmodifiableList = Collections.unmodifiableList(bubbleStackView.mBubbleData.mBubbles);
            if (!unmodifiableList.isEmpty()) {
                unmodifiableList.forEach(new BubbleStackView$$ExternalSyntheticLambda22());
            }
            bubbleStackView.updateExpandedView();
            bubbleStackView.mScrim.setBackgroundDrawable(new ColorDrawable(bubbleStackView.getResources().getColor(android.R.color.system_neutral1_1000)));
            bubbleStackView.mManageMenuScrim.setBackgroundDrawable(new ColorDrawable(bubbleStackView.getResources().getColor(android.R.color.system_neutral1_1000)));
        }
        this.mBubbleIconFactory = new BubbleIconFactory(this.mContext);
        this.mBubbleBadgeIconFactory = new BubbleBadgeIconFactory(this.mContext);
        BubbleData bubbleData = this.mBubbleData;
        Iterator it = Collections.unmodifiableList(bubbleData.mBubbles).iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            anonymousClass1 = this.mBubbleTaskViewFactory;
            bubbleExpandedViewManager$Companion$fromBubbleController$1 = this.mExpandedViewManager;
            if (!hasNext) {
                break;
            }
            ((Bubble) it.next()).inflate(null, this.mContext, bubbleExpandedViewManager$Companion$fromBubbleController$1, anonymousClass1, this.mBubblePositioner, this.mStackView, null, this.mBubbleIconFactory, this.mBubbleBadgeIconFactory, false);
        }
        Iterator<Bubble> it2 = bubbleData.getOverflowBubbles().iterator();
        while (it2.hasNext()) {
            it2.next().inflate(null, this.mContext, bubbleExpandedViewManager$Companion$fromBubbleController$1, anonymousClass1, this.mBubblePositioner, this.mStackView, null, this.mBubbleIconFactory, this.mBubbleBadgeIconFactory, false);
        }
    }

    public void onUserChanged(int i) {
        int i2 = 0;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -8003463218618614664L, 5, Long.valueOf(this.mCurrentUserId), Long.valueOf(i));
        }
        int i3 = this.mCurrentUserId;
        this.mSavedUserBubbleData.remove(i3);
        UserBubbleData userBubbleData = new UserBubbleData(i2);
        BubbleData bubbleData = this.mBubbleData;
        for (Bubble bubble : Collections.unmodifiableList(bubbleData.mBubbles)) {
            ((HashMap) userBubbleData.mKeyToShownInShadeMap).put(bubble.mKey, Boolean.valueOf(bubble.showInShade()));
        }
        this.mSavedUserBubbleData.put(i3, userBubbleData);
        this.mCurrentUserId = i;
        bubbleData.dismissAll(8);
        while (!((ArrayList) bubbleData.mOverflowBubbles).isEmpty()) {
            bubbleData.doRemove(8, ((Bubble) ((ArrayList) bubbleData.mOverflowBubbles).get(0)).mKey);
        }
        bubbleData.dispatchPendingChanges();
        this.mOverflowDataLoadNeeded = true;
        UserBubbleData userBubbleData2 = (UserBubbleData) this.mSavedUserBubbleData.get(i);
        if (userBubbleData2 != null) {
            BubblesManager.AnonymousClass5 anonymousClass5 = this.mSysuiProxy;
            anonymousClass5.val$sysuiMainExecutor.execute(new BubblesManager$$ExternalSyntheticLambda2(anonymousClass5, ((HashMap) userBubbleData2.mKeyToShownInShadeMap).keySet(), 1, new BubbleController$$ExternalSyntheticLambda4(this, userBubbleData2, 0)));
            this.mSavedUserBubbleData.remove(i);
        }
        bubbleData.mCurrentUserId = i;
    }

    public final void promoteBubbleFromOverflow(Bubble bubble) {
        this.mLogger.log(bubble, BubbleLogger.Event.BUBBLE_OVERFLOW_REMOVE_BACK_TO_STACK);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 1535426235779658681L, 0, String.valueOf(bubble.mKey));
        }
        bubble.setInflateSynchronously(this.mInflateSynchronously);
        bubble.setShouldAutoExpand(true);
        bubble.mLastAccessed = System.currentTimeMillis();
        bubble.setSuppressNotification(true);
        bubble.setShowDot(false);
        setIsBubble(bubble, true);
    }

    public void removeAllBubbles(int i) {
        this.mBubbleData.dismissAll(i);
        if (i == 1) {
            this.mLogger.mUiEventLogger.log(BubbleLogger.Event.BUBBLE_BAR_DISMISSED_DRAG_BAR);
        }
    }

    public final void removeBubble(int i, String str) {
        BubbleData bubbleData = this.mBubbleData;
        if (bubbleData.hasAnyBubbleWithKey(str)) {
            bubbleData.dismissBubbleWithKey(i, str);
        }
    }

    public void setExpandListener(Bubbles.BubbleExpandListener bubbleExpandListener) {
        BubbleController$$ExternalSyntheticLambda5 bubbleController$$ExternalSyntheticLambda5 = new BubbleController$$ExternalSyntheticLambda5(bubbleExpandListener);
        this.mExpandListener = bubbleController$$ExternalSyntheticLambda5;
        BubbleStackView bubbleStackView = this.mStackView;
        if (bubbleStackView != null) {
            bubbleStackView.mExpandListener = bubbleController$$ExternalSyntheticLambda5;
        }
    }

    public void setInflateSynchronously(boolean z) {
        this.mInflateSynchronously = z;
    }

    public final void setIsBubble(BubbleEntry bubbleEntry, boolean z, boolean z2) {
        Objects.requireNonNull(bubbleEntry);
        bubbleEntry.setFlagBubble(z);
        BubblesManager.AnonymousClass5 anonymousClass5 = this.mSysuiProxy;
        anonymousClass5.val$sysuiMainExecutor.execute(new BubblesManager$5$$ExternalSyntheticLambda1(anonymousClass5, bubbleEntry.mSbn.getKey(), 0));
        try {
            this.mBarService.onNotificationBubbleChanged(bubbleEntry.mSbn.getKey(), z, z2 ? 3 : 0);
        } catch (RemoteException unused) {
        }
    }

    public void updateBubble(BubbleEntry bubbleEntry) {
        SparseArray sparseArray;
        int userId = bubbleEntry.mSbn.getUserId();
        int i = 0;
        if (userId == -1 || !((sparseArray = this.mCurrentProfiles) == null || sparseArray.get(userId) == null)) {
            updateBubble(bubbleEntry, false, true);
            return;
        }
        ((HashMap) ((UserBubbleData) this.mSavedUserBubbleData.get(userId, new UserBubbleData(i))).mKeyToShownInShadeMap).put(bubbleEntry.mSbn.getKey(), Boolean.TRUE);
        Log.w("Bubbles", "updateBubble, ignore update for non-active user=" + userId + " currentUser=" + this.mCurrentUserId);
    }

    public final void updateBubbleViews() {
        BubbleStackView bubbleStackView;
        BadgedImageView iconView$1;
        BadgedImageView badgedImageView;
        if (this.mStackView == null) {
            return;
        }
        int i = 0;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_BUBBLES, -5180604918216641258L, 0, String.valueOf(this.mIsStatusBarShade), String.valueOf(hasBubbles()));
        }
        if (!this.mIsStatusBarShade) {
            BubbleStackView bubbleStackView2 = this.mStackView;
            if (bubbleStackView2 != null) {
                bubbleStackView2.setVisibility(4);
            }
        } else if (hasBubbles() && (bubbleStackView = this.mStackView) != null) {
            bubbleStackView.setVisibility(0);
        }
        BubbleStackView bubbleStackView3 = this.mStackView;
        if (bubbleStackView3 != null) {
            if (!Collections.unmodifiableList(bubbleStackView3.mBubbleData.mBubbles).isEmpty()) {
                for (int i2 = 0; i2 < Collections.unmodifiableList(bubbleStackView3.mBubbleData.mBubbles).size(); i2++) {
                    Bubble bubble = (Bubble) Collections.unmodifiableList(bubbleStackView3.mBubbleData.mBubbles).get(i2);
                    String str = bubble.mAppName;
                    String str2 = bubble.mTitle;
                    if (str2 == null) {
                        str2 = bubbleStackView3.getResources().getString(R.string.notification_bubble_title);
                    }
                    BadgedImageView badgedImageView2 = bubble.mIconView;
                    if (badgedImageView2 != null) {
                        if (bubbleStackView3.mIsExpanded || i2 > 0) {
                            badgedImageView2.setContentDescription(bubbleStackView3.getResources().getString(R.string.bubble_content_description_single, str2, str));
                        } else {
                            bubble.mIconView.setContentDescription(bubbleStackView3.getResources().getString(R.string.bubble_content_description_stack, str2, str, Integer.valueOf(bubbleStackView3.getBubbleCount())));
                        }
                    }
                }
            }
            BubbleStackView bubbleStackView4 = this.mStackView;
            while (true) {
                if (i >= Collections.unmodifiableList(bubbleStackView4.mBubbleData.mBubbles).size()) {
                    break;
                }
                Bubble bubble2 = i > 0 ? (Bubble) Collections.unmodifiableList(bubbleStackView4.mBubbleData.mBubbles).get(i - 1) : null;
                BadgedImageView badgedImageView3 = ((Bubble) Collections.unmodifiableList(bubbleStackView4.mBubbleData.mBubbles).get(i)).mIconView;
                if (badgedImageView3 != null) {
                    if (bubbleStackView4.mIsExpanded) {
                        badgedImageView3.setImportantForAccessibility(1);
                        iconView$1 = bubble2 != null ? bubble2.mIconView : null;
                        if (iconView$1 != null) {
                            badgedImageView3.setAccessibilityDelegate(new View.AccessibilityDelegate(bubbleStackView4, iconView$1) { // from class: com.android.wm.shell.bubbles.BubbleStackView.16
                                public final /* synthetic */ View val$prevBubbleIconView;

                                public AnonymousClass16(BubbleStackView bubbleStackView42, View iconView$12) {
                                    this.val$prevBubbleIconView = iconView$12;
                                }

                                @Override // android.view.View.AccessibilityDelegate
                                public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                                    accessibilityNodeInfo.setTraversalAfter(this.val$prevBubbleIconView);
                                }
                            });
                        }
                    } else {
                        badgedImageView3.setImportantForAccessibility(i == 0 ? 1 : 2);
                    }
                }
                i++;
            }
            if (bubbleStackView42.mIsExpanded) {
                BubbleOverflow bubbleOverflow = bubbleStackView42.mBubbleOverflow;
                iconView$12 = bubbleOverflow != null ? bubbleOverflow.getIconView$1() : null;
                if (!bubbleStackView42.mShowingOverflow || iconView$12 == null || Collections.unmodifiableList(bubbleStackView42.mBubbleData.mBubbles).isEmpty() || (badgedImageView = ((Bubble) Collections.unmodifiableList(bubbleStackView42.mBubbleData.mBubbles).get(Collections.unmodifiableList(bubbleStackView42.mBubbleData.mBubbles).size() - 1)).mIconView) == null) {
                    return;
                }
                iconView$12.setAccessibilityDelegate(new View.AccessibilityDelegate(bubbleStackView42, badgedImageView) { // from class: com.android.wm.shell.bubbles.BubbleStackView.17
                    public final /* synthetic */ View val$lastBubbleIconView;

                    public AnonymousClass17(BubbleStackView bubbleStackView42, View badgedImageView4) {
                        this.val$lastBubbleIconView = badgedImageView4;
                    }

                    @Override // android.view.View.AccessibilityDelegate
                    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                        accessibilityNodeInfo.setTraversalAfter(this.val$lastBubbleIconView);
                    }
                });
            }
        }
    }

    public final void updateNotNotifyingEntry(Bubble bubble, BubbleEntry bubbleEntry, boolean z) {
        boolean showInShade = bubble.showInShade();
        BubbleData bubbleData = this.mBubbleData;
        boolean z2 = bubbleData.mExpanded && bubble.equals(bubbleData.mSelectedBubble);
        bubble.setEntry(bubbleEntry);
        bubble.setSuppressNotification((!z2 && z && bubble.showInShade()) ? false : true);
        bubble.setShowDot(!z2);
        if (showInShade != bubble.showInShade()) {
            this.mImpl.mCachedState.updateBubbleSuppressedState(bubble);
        }
    }

    public final void updateWindowFlagsForBackpress(boolean z) {
        if (this.mAddedToWindowManager) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -1228140070930271613L, 3, Boolean.valueOf(z));
            }
            WindowManager.LayoutParams layoutParams = this.mWmLayoutParams;
            layoutParams.flags = 16777216 | (z ? 0 : 40);
            BubbleStackView bubbleStackView = this.mStackView;
            if (bubbleStackView != null) {
                this.mWindowManager.updateViewLayout(bubbleStackView, layoutParams);
            }
        }
    }

    public void inflateAndAdd(Bubble bubble, final boolean z, final boolean z2, final BubbleBarLocation bubbleBarLocation) {
        ensureBubbleViewsAndWindowCreated();
        bubble.setInflateSynchronously(this.mInflateSynchronously);
        BubbleViewInfoTask.Callback callback = new BubbleViewInfoTask.Callback() { // from class: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda2
            @Override // com.android.wm.shell.bubbles.BubbleViewInfoTask.Callback
            public final void onBubbleViewsReady(Bubble bubble2) {
                BubbleController.this.mBubbleData.notificationEntryUpdated(bubble2, z, z2, bubbleBarLocation);
            }
        };
        Context context = this.mContext;
        AnonymousClass1 anonymousClass1 = this.mBubbleTaskViewFactory;
        BubbleStackView bubbleStackView = this.mStackView;
        BubbleIconFactory bubbleIconFactory = this.mBubbleIconFactory;
        BubbleBadgeIconFactory bubbleBadgeIconFactory = this.mBubbleBadgeIconFactory;
        bubble.inflate(callback, context, this.mExpandedViewManager, anonymousClass1, this.mBubblePositioner, bubbleStackView, null, bubbleIconFactory, bubbleBadgeIconFactory, false);
    }

    public final void setIsBubble(Bubble bubble, boolean z) {
        Objects.requireNonNull(bubble);
        bubble.mIsBubble = z;
        BubblesManager.AnonymousClass5 anonymousClass5 = this.mSysuiProxy;
        anonymousClass5.val$sysuiMainExecutor.execute(new BubblesManager$$ExternalSyntheticLambda2(anonymousClass5, bubble.mKey, new BubbleController$$ExternalSyntheticLambda12(this, z, bubble)));
    }

    public void updateBubble(BubbleEntry bubbleEntry, boolean z, boolean z2) {
        BubblesManager.AnonymousClass5 anonymousClass5 = this.mSysuiProxy;
        anonymousClass5.val$sysuiMainExecutor.execute(new BubblesManager$5$$ExternalSyntheticLambda1(anonymousClass5, bubbleEntry.mSbn.getKey(), 1));
        boolean z3 = (bubbleEntry.mRanking.isTextChanged() || bubbleEntry.getBubbleMetadata() == null || bubbleEntry.getBubbleMetadata().getAutoExpandBubble()) ? false : true;
        BubbleData bubbleData = this.mBubbleData;
        if (z3 && bubbleData.hasOverflowBubbleWithKey(bubbleEntry.mSbn.getKey())) {
            Bubble overflowBubbleWithKey = bubbleData.getOverflowBubbleWithKey(bubbleEntry.mSbn.getKey());
            if (bubbleEntry.isBubble()) {
                bubbleEntry.setFlagBubble(false);
            }
            updateNotNotifyingEntry(overflowBubbleWithKey, bubbleEntry, z2);
            return;
        }
        if (bubbleData.hasAnyBubbleWithKey(bubbleEntry.mSbn.getKey()) && z3) {
            Bubble anyBubbleWithKey = bubbleData.getAnyBubbleWithKey(bubbleEntry.mSbn.getKey());
            if (anyBubbleWithKey != null) {
                updateNotNotifyingEntry(anyBubbleWithKey, bubbleEntry, z2);
                return;
            }
            return;
        }
        if (bubbleData.mSuppressedBubbles.get(bubbleEntry.mSbn.getNotification().getLocusId()) != null) {
            Bubble suppressedBubbleWithKey = bubbleData.getSuppressedBubbleWithKey(bubbleEntry.mSbn.getKey());
            if (suppressedBubbleWithKey != null) {
                updateNotNotifyingEntry(suppressedBubbleWithKey, bubbleEntry, z2);
                return;
            }
            return;
        }
        Bubble orCreateBubble = bubbleData.getOrCreateBubble(bubbleEntry, null);
        if (bubbleEntry.mShouldSuppressNotificationList) {
            if (orCreateBubble.isEnabled(1)) {
                orCreateBubble.setShouldAutoExpand(false);
            }
            this.mImpl.mCachedState.updateBubbleSuppressedState(orCreateBubble);
            return;
        }
        inflateAndAdd(orCreateBubble, z, z2);
    }
}
