package com.android.wm.shell.bubbles;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.TaskInfo;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.Size;
import android.util.Slog;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.FrameLayout;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;
import androidx.core.animation.ValueAnimator;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.animation.SizeChangeAnimation;
import com.android.wm.shell.bubbles.BubbleTransitions;
import com.android.wm.shell.bubbles.BubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda2;
import com.android.wm.shell.bubbles.BubbleViewInfoTask;
import com.android.wm.shell.bubbles.bar.BubbleBarAnimationHelper;
import com.android.wm.shell.bubbles.bar.BubbleBarExpandedView;
import com.android.wm.shell.bubbles.bar.BubbleBarLayerView;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.taskview.TaskView;
import com.android.wm.shell.taskview.TaskViewRepository;
import com.android.wm.shell.taskview.TaskViewTaskController;
import com.android.wm.shell.taskview.TaskViewTransitions;
import com.android.wm.shell.transition.DefaultSurfaceAnimator;
import com.android.wm.shell.transition.Transitions;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class BubbleTransitions {
    public final BubbleData mBubbleData;
    public final Context mContext;
    public final ShellExecutor mMainExecutor;
    public final TaskViewRepository mRepository;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final TaskViewTransitions mTaskViewTransitions;
    public final Transitions mTransitions;

    public class DragData {
        public final float mCornerRadius;
        public final PointF mDragPosition;
        public final WindowContainerTransaction mPendingWct;
        public final float mTaskScale;

        public DragData(boolean z, float f, float f2, PointF pointF, WindowContainerTransaction windowContainerTransaction) {
            this.mPendingWct = windowContainerTransaction;
            this.mTaskScale = f;
            this.mCornerRadius = f2;
            this.mDragPosition = pointF == null ? new PointF(0.0f, 0.0f) : pointF;
        }
    }

    public class DraggedBubbleIconToFullscreen implements Transitions.TransitionHandler, BubbleTransition {
        public final Bubble mBubble;
        public final Point mDropLocation;
        public final TransactionProvider mTransactionProvider;
        public IBinder mTransition;

        public DraggedBubbleIconToFullscreen(BubbleTransitions bubbleTransitions, Bubble bubble, Point point) {
            this(bubble, point, new BubbleTransitions$DraggedBubbleIconToFullscreen$$ExternalSyntheticLambda1());
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
        public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
            return null;
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
        public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
            if (z) {
                this.mTransition = null;
                BubbleTransitions.this.mTaskViewTransitions.onExternalDone(iBinder);
            }
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
        public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback) {
            TransitionInfo.Change change;
            if (this.mTransition != iBinder) {
                return false;
            }
            Bubble bubble = this.mBubble;
            TaskViewTaskController taskViewTaskController = bubble.mBubbleTaskView.taskView.mTaskViewTaskController;
            BubbleTransitions bubbleTransitions = BubbleTransitions.this;
            if (taskViewTaskController == null) {
                bubbleTransitions.mTaskViewTransitions.onExternalDone(iBinder);
                transitionFinishCallback.onTransitionFinished(null);
                return true;
            }
            WindowContainerToken token = taskViewTaskController.mTaskInfo.getToken();
            int i = 0;
            while (true) {
                if (i >= transitionInfo.getChanges().size()) {
                    change = null;
                    break;
                }
                change = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
                if (change.getTaskInfo() != null && change.getMode() == 3 && token.equals(change.getTaskInfo().token)) {
                    break;
                }
                i++;
            }
            if (change == null) {
                Slog.w("BubbleTransitions", "Expected a TaskView transition to front but didn't find one, cleaning up the task view");
                taskViewTaskController.mTaskNotFound = true;
                ActivityManager.RunningTaskInfo runningTaskInfo = taskViewTaskController.mPendingInfo;
                if (runningTaskInfo != null) {
                    taskViewTaskController.notifyTaskRemovalStarted(runningTaskInfo);
                    taskViewTaskController.mTaskViewBase.getClass();
                    taskViewTaskController.mTaskViewController.removeTaskView(taskViewTaskController, runningTaskInfo.token);
                    taskViewTaskController.resetTaskInfo();
                }
                bubbleTransitions.mTaskViewTransitions.onExternalDone(iBinder);
                transitionFinishCallback.onTransitionFinished(null);
                return true;
            }
            TaskViewRepository taskViewRepository = bubbleTransitions.mRepository;
            int iFindAndPrune = taskViewRepository.findAndPrune(taskViewTaskController);
            if (iFindAndPrune >= 0) {
                taskViewRepository.mTaskViews.remove(iFindAndPrune);
            }
            final SurfaceControl leash = change.getLeash();
            Point point = this.mDropLocation;
            transaction.setPosition(leash, point.x, point.y);
            transaction.setScale(leash, 0.0f, 0.0f);
            transaction.apply();
            ((BubbleTransitions$DraggedBubbleIconToFullscreen$$ExternalSyntheticLambda1) this.mTransactionProvider).getClass();
            final SurfaceControl.Transaction transaction3 = new SurfaceControl.Transaction();
            final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat.setDuration(250L);
            valueAnimatorOfFloat.addUpdateListener(new Animator.AnimatorUpdateListener() { // from class: com.android.wm.shell.bubbles.BubbleTransitions.DraggedBubbleIconToFullscreen.1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    float f = valueAnimatorOfFloat.mCurrentFraction;
                    Point point2 = DraggedBubbleIconToFullscreen.this.mDropLocation;
                    float f2 = 1.0f - f;
                    transaction3.setPosition(leash, point2.x * f2, point2.y * f2);
                    transaction3.setScale(leash, f, f);
                    transaction3.apply();
                }
            });
            valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.wm.shell.bubbles.BubbleTransitions.DraggedBubbleIconToFullscreen.2
                @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    transaction3.close();
                    transitionFinishCallback.onTransitionFinished(null);
                }
            });
            valueAnimatorOfFloat.start(false);
            taskViewTaskController.notifyTaskRemovalStarted(bubble.mBubbleTaskView.taskView.mTaskViewTaskController.mTaskInfo);
            bubbleTransitions.mTaskViewTransitions.onExternalDone(iBinder);
            return true;
        }

        public DraggedBubbleIconToFullscreen(Bubble bubble, Point point, TransactionProvider transactionProvider) {
            this.mBubble = bubble;
            this.mDropLocation = point;
            this.mTransactionProvider = transactionProvider;
            bubble.mPreparingTransition = this;
            WindowContainerToken token = bubble.mBubbleTaskView.taskView.mTaskViewTaskController.mTaskInfo.getToken();
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.setAlwaysOnTop(token, false);
            windowContainerTransaction.setWindowingMode(token, 0);
            windowContainerTransaction.reorder(token, true);
            windowContainerTransaction.setHidden(token, false);
            BubbleTransitions.this.mTaskOrganizer.setInterceptBackPressedOnTaskRoot(token, false);
            BubbleTransitions.this.mTaskViewTransitions.enqueueExternal(bubble.mBubbleTaskView.taskView.mTaskViewTaskController, new BubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda0(this, windowContainerTransaction, 2));
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
        public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        }
    }

    public interface TransactionProvider {
    }

    public BubbleTransitions(Transitions transitions, ShellTaskOrganizer shellTaskOrganizer, TaskViewRepository taskViewRepository, BubbleData bubbleData, TaskViewTransitions taskViewTransitions, Context context) {
        this.mTransitions = transitions;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mRepository = taskViewRepository;
        this.mMainExecutor = transitions.mMainExecutor;
        this.mBubbleData = bubbleData;
        this.mTaskViewTransitions = taskViewTransitions;
        this.mContext = context;
    }

    public interface BubbleTransition {
        default void continueExpand() {
        }

        default void surfaceCreated() {
        }
    }

    class ConvertFromBubble implements Transitions.TransitionHandler, BubbleTransition {
        public final Bubble mBubble;
        public SurfaceControl mRootLeash;
        public final TaskInfo mTaskInfo;
        public SurfaceControl mTaskLeash;
        public IBinder mTransition;

        public ConvertFromBubble(Bubble bubble, TaskInfo taskInfo) {
            this.mBubble = bubble;
            this.mTaskInfo = taskInfo;
            bubble.mPreparingTransition = this;
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            WindowContainerToken token = taskInfo.getToken();
            windowContainerTransaction.setWindowingMode(token, 0);
            windowContainerTransaction.setAlwaysOnTop(token, false);
            BubbleTransitions.this.mTaskOrganizer.setInterceptBackPressedOnTaskRoot(token, false);
            BubbleTransitions.this.mTaskViewTransitions.enqueueExternal(bubble.mBubbleTaskView.taskView.mTaskViewTaskController, new BubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda0(this, windowContainerTransaction, 1));
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
        public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
            return null;
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
        public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
            if (z) {
                this.mTransition = null;
                Bubble bubble = this.mBubble;
                bubble.mPreparingTransition = null;
                TaskViewTaskController taskViewTaskController = bubble.mBubbleTaskView.taskView.mTaskViewTaskController;
                taskViewTaskController.notifyTaskRemovalStarted(taskViewTaskController.mTaskInfo);
                this.mTaskLeash = null;
                BubbleTransitions.this.mTaskViewTransitions.onExternalDone(iBinder);
            }
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
        public final boolean startAnimation(IBinder iBinder, final TransitionInfo transitionInfo, final SurfaceControl.Transaction transaction, final SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback) {
            boolean z;
            TransitionInfo.Change change;
            boolean z2;
            if (this.mTransition != iBinder) {
                return false;
            }
            Bubble bubble = this.mBubble;
            TaskViewTaskController taskViewTaskController = bubble.mBubbleTaskView.taskView.mTaskViewTaskController;
            BubbleTransitions bubbleTransitions = BubbleTransitions.this;
            if (taskViewTaskController == null) {
                bubbleTransitions.mTaskViewTransitions.onExternalDone(iBinder);
                return false;
            }
            int i = 0;
            while (true) {
                if (i >= transitionInfo.getChanges().size()) {
                    z = false;
                    change = null;
                    break;
                }
                change = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
                if (change.getTaskInfo() != null && change.getMode() == 6 && this.mTaskInfo.token.equals(change.getTaskInfo().token)) {
                    TaskViewRepository taskViewRepository = bubbleTransitions.mRepository;
                    int iFindAndPrune = taskViewRepository.findAndPrune(taskViewTaskController);
                    if (iFindAndPrune >= 0) {
                        taskViewRepository.mTaskViews.remove(iFindAndPrune);
                    }
                    z = true;
                } else {
                    i++;
                }
            }
            if (!z) {
                Slog.w("BubbleTransitions", "Expected a TaskView conversion in this transition but didn't get one, cleaning up the task view");
                taskViewTaskController.mTaskNotFound = true;
                ActivityManager.RunningTaskInfo runningTaskInfo = taskViewTaskController.mPendingInfo;
                if (runningTaskInfo != null) {
                    taskViewTaskController.notifyTaskRemovalStarted(runningTaskInfo);
                    taskViewTaskController.mTaskViewBase.getClass();
                    taskViewTaskController.mTaskViewController.removeTaskView(taskViewTaskController, runningTaskInfo.token);
                    taskViewTaskController.resetTaskInfo();
                }
                bubble.mPreparingTransition = null;
                TaskViewTaskController taskViewTaskController2 = bubble.mBubbleTaskView.taskView.mTaskViewTaskController;
                taskViewTaskController2.notifyTaskRemovalStarted(taskViewTaskController2.mTaskInfo);
                this.mTaskLeash = null;
                bubbleTransitions.mTaskViewTransitions.onExternalDone(iBinder);
                return false;
            }
            this.mTaskLeash = change.getLeash();
            this.mRootLeash = transitionInfo.getRoot(0).getLeash();
            View view = bubble.mBubbleBarExpandedView;
            if (view == null) {
                view = bubble.mExpandedView;
            }
            SurfaceControl surfaceControl = view.getViewRootImpl().getSurfaceControl();
            final BubbleTransitions$ConvertFromBubble$$ExternalSyntheticLambda1 bubbleTransitions$ConvertFromBubble$$ExternalSyntheticLambda1 = new BubbleTransitions$ConvertFromBubble$$ExternalSyntheticLambda1(this, taskViewTaskController);
            if (surfaceControl != null) {
                SurfaceControl surfaceControl2 = this.mTaskLeash;
                View view2 = bubble.mBubbleBarExpandedView;
                if (view2 == null) {
                    view2 = bubble.mExpandedView;
                }
                float f = change.getStartAbsBounds().left - transitionInfo.getRoot(0).getOffset().x;
                float f2 = change.getStartAbsBounds().top - transitionInfo.getRoot(0).getOffset().y;
                BubbleBarExpandedView bubbleBarExpandedView = bubble.mBubbleBarExpandedView;
                float f3 = bubbleBarExpandedView != null ? bubbleBarExpandedView.mCurrentCornerRadius : bubble.mExpandedView.mCornerRadius;
                bubbleTransitions.getClass();
                SurfaceControl.Transaction transaction3 = new SurfaceControl.Transaction();
                transaction3.reparent(surfaceControl2, surfaceControl);
                transaction.reparent(surfaceControl2, surfaceControl);
                transaction3.setPosition(surfaceControl2, f, f2);
                transaction.setPosition(surfaceControl2, f, f2);
                transaction3.show(surfaceControl2);
                transaction3.setAlpha(surfaceControl2, 1.0f);
                float elevation = view2.getElevation() * 2.0f;
                transaction3.setShadowRadius(surfaceControl2, elevation);
                transaction3.setCornerRadius(surfaceControl2, f3);
                transaction.setShadowRadius(surfaceControl2, elevation);
                transaction.setCornerRadius(surfaceControl2, f3);
                transaction3.addTransactionCommittedListener(bubbleTransitions.mMainExecutor, new SurfaceControl.TransactionCommittedListener() { // from class: com.android.wm.shell.bubbles.BubbleTransitions$$ExternalSyntheticLambda0
                    @Override // android.view.SurfaceControl.TransactionCommittedListener
                    public final void onTransactionCommitted() {
                        bubbleTransitions$ConvertFromBubble$$ExternalSyntheticLambda1.run();
                    }
                });
                view2.getViewRootImpl().applyTransactionOnDraw(transaction3);
                view2.setVisibility(4);
                View view3 = bubble.mBubbleBarExpandedView;
                if (view3 == null) {
                    view3 = bubble.mExpandedView;
                }
                view3.post(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleTransitions$ConvertFromBubble$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        BubbleTransitions.ConvertFromBubble convertFromBubble = this.f$0;
                        BubbleTransitions.this.mTransitions.dispatchTransition(convertFromBubble.mTransition, transitionInfo, transaction, transaction2, transitionFinishCallback, null, null);
                    }
                });
                z2 = true;
            } else {
                bubbleTransitions$ConvertFromBubble$$ExternalSyntheticLambda1.run();
                z2 = true;
                bubbleTransitions.mTransitions.dispatchTransition(this.mTransition, transitionInfo, transaction, transaction2, transitionFinishCallback, null, null);
            }
            bubbleTransitions.mTaskViewTransitions.onExternalDone(iBinder);
            return z2;
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
        public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        }
    }

    class ConvertToBubble implements Transitions.TransitionHandler, BubbleTransition {
        public final Bubble mBubble;
        public final DragData mDragData;
        public Transitions.TransitionFinishCallback mFinishCb;
        public SurfaceControl.Transaction mFinishT;
        public final BubbleBarLayerView mLayerView;
        public TaskInfo mTaskInfo;
        public SurfaceControl mTaskLeash;
        public IBinder mTransition;
        public WindowContainerTransaction mFinishWct = null;
        public final Rect mStartBounds = new Rect();
        public SurfaceControl mSnapshot = null;
        public BubbleViewProvider mPriorBubble = null;
        public final TransitionProgress mTransitionProgress = new TransitionProgress(this, 0);

        public class TransitionProgress {
            public boolean mReadyToExpand;
            public boolean mSurfaceReady;
            public boolean mTransitionReady;

            public /* synthetic */ TransitionProgress(ConvertToBubble convertToBubble, int i) {
                this();
            }

            public final void onUpdate() {
                if (this.mTransitionReady && this.mReadyToExpand && this.mSurfaceReady) {
                    ConvertToBubble.this.mBubble.mPreparingTransition = null;
                }
            }

            private TransitionProgress() {
            }
        }

        public ConvertToBubble(Bubble bubble, TaskInfo taskInfo, Context context, BubbleExpandedViewManager bubbleExpandedViewManager, BubbleTaskViewFactory bubbleTaskViewFactory, BubblePositioner bubblePositioner, BubbleStackView bubbleStackView, BubbleBarLayerView bubbleBarLayerView, BubbleIconFactory bubbleIconFactory, DragData dragData, boolean z) {
            this.mBubble = bubble;
            this.mTaskInfo = taskInfo;
            this.mLayerView = bubbleBarLayerView;
            this.mDragData = dragData;
            bubble.setInflateSynchronously(z);
            bubble.mPreparingTransition = this;
            bubble.inflate(new BubbleViewInfoTask.Callback() { // from class: com.android.wm.shell.bubbles.BubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda1
                @Override // com.android.wm.shell.bubbles.BubbleViewInfoTask.Callback
                public final void onBubbleViewsReady(Bubble bubble2) {
                    this.f$0.onInflated(bubble2);
                }
            }, context, bubbleExpandedViewManager, bubbleTaskViewFactory, bubblePositioner, bubbleStackView, bubbleBarLayerView, bubbleIconFactory, new BubbleBadgeIconFactory(BubbleTransitions.this.mContext), false);
        }

        @Override // com.android.wm.shell.bubbles.BubbleTransitions.BubbleTransition
        public final void continueExpand() {
            TransitionProgress transitionProgress = this.mTransitionProgress;
            transitionProgress.mReadyToExpand = true;
            transitionProgress.onUpdate();
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
        public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
            return null;
        }

        public void onInflated(Bubble bubble) {
            WindowContainerTransaction windowContainerTransaction;
            if (bubble != this.mBubble) {
                throw new IllegalArgumentException("inflate callback doesn't match bubble");
            }
            Rect rect = new Rect();
            BubbleBarAnimationHelper bubbleBarAnimationHelper = this.mLayerView.mAnimationHelper;
            BubblePositioner bubblePositioner = bubbleBarAnimationHelper.mPositioner;
            int i = bubblePositioner.mExpandedViewBubbleBarWidth;
            int expandedViewHeightForBubbleBar = bubblePositioner.getExpandedViewHeightForBubbleBar(false);
            Point expandedViewRestPosition = bubbleBarAnimationHelper.getExpandedViewRestPosition(new Size(i, expandedViewHeightForBubbleBar));
            int i2 = expandedViewRestPosition.x;
            int i3 = expandedViewRestPosition.y;
            rect.set(i2, i3, i + i2, expandedViewHeightForBubbleBar + i3);
            WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
            DragData dragData = this.mDragData;
            if (dragData != null && (windowContainerTransaction = dragData.mPendingWct) != null) {
                windowContainerTransaction2.merge(windowContainerTransaction, true);
            }
            if (this.mTaskInfo.getWindowingMode() == 6 && this.mTaskInfo.getParentTaskId() != -1) {
                windowContainerTransaction2.reparent(this.mTaskInfo.token, (WindowContainerToken) null, true);
            }
            windowContainerTransaction2.setAlwaysOnTop(this.mTaskInfo.token, true);
            windowContainerTransaction2.setWindowingMode(this.mTaskInfo.token, 6);
            windowContainerTransaction2.setBounds(this.mTaskInfo.token, rect);
            TaskView taskView = bubble.mBubbleTaskView.taskView;
            taskView.setSurfaceLifecycle(2);
            BubbleTransitions bubbleTransitions = BubbleTransitions.this;
            TaskViewRepository taskViewRepository = bubbleTransitions.mRepository;
            int iFindAndPrune = taskViewRepository.findAndPrune(taskView.mTaskViewTaskController);
            TaskViewRepository.TaskViewState taskViewState = iFindAndPrune >= 0 ? (TaskViewRepository.TaskViewState) taskViewRepository.mTaskViews.get(iFindAndPrune) : null;
            if (taskViewState != null) {
                taskViewState.mVisible = true;
            }
            bubbleTransitions.mTaskViewTransitions.enqueueExternal(taskView.mTaskViewTaskController, new BubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda0(this, windowContainerTransaction2, 0));
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
        public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
            if (z) {
                this.mTransition = null;
                BubbleTransitions.this.mTaskViewTransitions.onExternalDone(iBinder);
            }
        }

        public final void playAnimation(boolean z) {
            BubbleViewProvider bubbleViewProvider;
            TaskViewTaskController taskViewTaskController = this.mBubble.mBubbleTaskView.taskView.mTaskViewTaskController;
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            transaction.setPosition(this.mTaskLeash, 0.0f, 0.0f);
            BubbleTransitions.this.mTaskViewTransitions.prepareOpenAnimation(taskViewTaskController, true, transaction, this.mFinishT, (ActivityManager.RunningTaskInfo) this.mTaskInfo, this.mTaskLeash, this.mFinishWct);
            if (this.mFinishWct.isEmpty()) {
                this.mFinishWct = null;
            }
            if (!z) {
                transaction.apply();
                this.mFinishCb.onTransitionFinished(this.mFinishWct);
                this.mFinishCb = null;
                return;
            }
            DragData dragData = this.mDragData;
            float f = dragData != null ? dragData.mTaskScale : 1.0f;
            Rect rect = this.mStartBounds;
            final SurfaceControl surfaceControl = this.mSnapshot;
            SurfaceControl surfaceControl2 = this.mTaskLeash;
            final BubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda2 bubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda2 = new BubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda2(this, 1);
            BubbleBarLayerView bubbleBarLayerView = this.mLayerView;
            if (!bubbleBarLayerView.mIsExpanded || (bubbleViewProvider = bubbleBarLayerView.mExpandedBubble) == null) {
                throw new IllegalStateException("Can't animateExpand without expanded state");
            }
            final BubbleBarAnimationHelper bubbleBarAnimationHelper = bubbleBarLayerView.mAnimationHelper;
            bubbleBarAnimationHelper.mExpandedBubble = bubbleViewProvider;
            final BubbleBarExpandedView expandedView = bubbleBarAnimationHelper.getExpandedView();
            if (expandedView == null) {
                return;
            }
            TaskView taskView = expandedView.mTaskView;
            if (taskView != null) {
                taskView.setAlpha(1.0f);
            }
            expandedView.setAlpha(1.0f);
            final SurfaceControl surfaceControl3 = ((Bubble) bubbleBarAnimationHelper.mExpandedBubble).mBubbleTaskView.taskView.getSurfaceControl();
            Size expandedViewSize = bubbleBarAnimationHelper.getExpandedViewSize();
            Point expandedViewRestPosition = bubbleBarAnimationHelper.getExpandedViewRestPosition(expandedViewSize);
            int i = rect.left;
            int i2 = expandedViewRestPosition.x;
            int i3 = rect.top;
            int i4 = expandedViewRestPosition.y;
            final SizeChangeAnimation sizeChangeAnimation = new SizeChangeAnimation(new Rect(i - i2, i3 - i4, rect.right - i2, rect.bottom - i4), new Rect(0, 0, expandedViewSize.getWidth(), expandedViewSize.getHeight()), f, 1.0f);
            transaction.reparent(surfaceControl, surfaceControl2);
            transaction.setPosition(surfaceControl, 0.0f, 0.0f);
            transaction.show(surfaceControl);
            transaction.show(surfaceControl2);
            sizeChangeAnimation.apply(expandedView, transaction, surfaceControl2, surfaceControl, 0.0f);
            final Consumer consumer = new Consumer() { // from class: com.android.wm.shell.bubbles.bar.BubbleBarAnimationHelper$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    BubbleBarAnimationHelper bubbleBarAnimationHelper2 = bubbleBarAnimationHelper;
                    BubbleBarExpandedView bubbleBarExpandedView = expandedView;
                    SurfaceControl surfaceControl4 = surfaceControl;
                    BubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda2 bubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda22 = bubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda2;
                    bubbleBarAnimationHelper2.updateExpandedView(bubbleBarExpandedView);
                    surfaceControl4.release();
                    bubbleBarExpandedView.setSurfaceZOrderedOnTop(false);
                    bubbleBarExpandedView.setAnimating(false);
                    bubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda22.run();
                }
            };
            final SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
            ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.animation.SizeChangeAnimation$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                    sizeChangeAnimation.apply(expandedView, transaction2, surfaceControl3, surfaceControl, Math.clamp(valueAnimator.getAnimatedFraction(), 0.0f, 1.0f));
                }
            };
            android.animation.ValueAnimator valueAnimator = sizeChangeAnimation.mAnimator;
            Consumer consumer2 = new Consumer() { // from class: com.android.wm.shell.animation.SizeChangeAnimation$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    SurfaceControl.Transaction transaction3 = transaction2;
                    SurfaceControl surfaceControl4 = surfaceControl;
                    BubbleBarExpandedView bubbleBarExpandedView = expandedView;
                    SurfaceControl surfaceControl5 = surfaceControl3;
                    Consumer consumer3 = consumer;
                    android.animation.ValueAnimator valueAnimator2 = (android.animation.ValueAnimator) obj;
                    transaction3.reparent(surfaceControl4, null);
                    if (bubbleBarExpandedView != null) {
                        bubbleBarExpandedView.setClipBounds(null);
                        bubbleBarExpandedView.setAnimationMatrix(null);
                        transaction3.setCrop(surfaceControl5, null);
                    }
                    transaction3.apply();
                    transaction3.close();
                    consumer3.accept(valueAnimator2);
                }
            };
            valueAnimator.addUpdateListener(animatorUpdateListener);
            valueAnimator.addListener(new DefaultSurfaceAnimator.AnonymousClass1(valueAnimator, consumer2, animatorUpdateListener));
            expandedView.setSurfaceZOrderedOnTop(true);
            valueAnimator.setDuration(400L);
            valueAnimator.setInterpolator(Interpolators.EMPHASIZED);
            valueAnimator.start();
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
        public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
            BubbleViewProvider bubbleViewProvider;
            if (this.mTransition != iBinder) {
                return false;
            }
            int i = 0;
            while (true) {
                int size = transitionInfo.getChanges().size();
                Bubble bubble = this.mBubble;
                BubbleTransitions bubbleTransitions = BubbleTransitions.this;
                if (i >= size) {
                    Slog.w("BubbleTransitions", "Expected a TaskView conversion in this transition but didn't get one, cleaning up the task view");
                    TaskViewTaskController taskViewTaskController = bubble.mBubbleTaskView.taskView.mTaskViewTaskController;
                    taskViewTaskController.mTaskNotFound = true;
                    ActivityManager.RunningTaskInfo runningTaskInfo = taskViewTaskController.mPendingInfo;
                    if (runningTaskInfo != null) {
                        taskViewTaskController.notifyTaskRemovalStarted(runningTaskInfo);
                        taskViewTaskController.mTaskViewBase.getClass();
                        taskViewTaskController.mTaskViewController.removeTaskView(taskViewTaskController, runningTaskInfo.token);
                        taskViewTaskController.resetTaskInfo();
                    }
                    bubbleTransitions.mTaskViewTransitions.onExternalDone(iBinder);
                    return false;
                }
                TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
                if (change.getTaskInfo() != null && ((change.getMode() == 6 || change.getMode() == 3) && this.mTaskInfo.token.equals(change.getTaskInfo().token))) {
                    this.mStartBounds.set(change.getStartAbsBounds());
                    this.mFinishWct = new WindowContainerTransaction();
                    this.mTaskInfo = change.getTaskInfo();
                    this.mFinishT = transaction2;
                    this.mTaskLeash = change.getLeash();
                    this.mSnapshot = change.getSnapshot();
                    this.mFinishCb = transitionFinishCallback;
                    DragData dragData = this.mDragData;
                    if (dragData != null) {
                        Rect rect = this.mStartBounds;
                        PointF pointF = dragData.mDragPosition;
                        rect.offsetTo((int) pointF.x, (int) pointF.y);
                        SurfaceControl surfaceControl = this.mSnapshot;
                        float f = dragData.mTaskScale;
                        transaction.setScale(surfaceControl, f, f);
                        transaction.setCornerRadius(this.mSnapshot, dragData.mCornerRadius);
                    }
                    bubbleTransitions.mBubbleData.notificationEntryUpdated(bubble, true, false, null);
                    float f2 = this.mStartBounds.left - transitionInfo.getRoot(0).getOffset().x;
                    float f3 = this.mStartBounds.top - transitionInfo.getRoot(0).getOffset().y;
                    transaction.setPosition(this.mTaskLeash, f2, f3);
                    transaction.show(this.mSnapshot);
                    transaction.reparent(this.mSnapshot, transitionInfo.getRoot(0).getLeash());
                    transaction.setPosition(this.mSnapshot, f2, f3);
                    transaction.setLayer(this.mSnapshot, Integer.MAX_VALUE);
                    transaction.apply();
                    bubbleTransitions.mTaskViewTransitions.onExternalDone(iBinder);
                    TransitionProgress transitionProgress = this.mTransitionProgress;
                    transitionProgress.mTransitionReady = true;
                    transitionProgress.onUpdate();
                    BubbleBarLayerView bubbleBarLayerView = this.mLayerView;
                    boolean zCanExpandView = bubbleBarLayerView.canExpandView(bubble);
                    if (zCanExpandView) {
                        BubbleViewProvider bubbleViewProviderPrepareExpandedView = bubbleBarLayerView.prepareExpandedView(bubble);
                        BubbleBarExpandedView bubbleBarExpandedView = bubbleBarLayerView.mExpandedBubble.getBubbleBarExpandedView();
                        if (bubbleBarExpandedView != null) {
                            BubbleBarExpandedView bubbleBarExpandedView2 = bubbleBarLayerView.mExpandedView;
                            if (bubbleBarExpandedView2 != null && (bubbleViewProvider = bubbleBarLayerView.mExpandedBubble) != null && !bubbleBarExpandedView2.mIsAnimating) {
                                boolean zEquals = bubbleViewProvider.getKey().equals("Overflow");
                                BubblePositioner bubblePositioner = bubbleBarLayerView.mPositioner;
                                bubblePositioner.getBubbleBarExpandedViewBounds(bubbleBarLayerView.mTempRect, bubblePositioner.isBubbleBarOnLeft(), zEquals);
                                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) bubbleBarLayerView.mExpandedView.getLayoutParams();
                                layoutParams.width = bubbleBarLayerView.mTempRect.width();
                                layoutParams.height = bubbleBarLayerView.mTempRect.height();
                                bubbleBarLayerView.mExpandedView.setLayoutParams(layoutParams);
                                bubbleBarLayerView.mExpandedView.setX(bubbleBarLayerView.mTempRect.left);
                                bubbleBarLayerView.mExpandedView.setY(bubbleBarLayerView.mTempRect.top);
                                TaskView taskView = bubbleBarLayerView.mExpandedView.mTaskView;
                                if (taskView != null) {
                                    taskView.getBoundsOnScreen(taskView.mTmpRect);
                                    taskView.mTaskViewController.setTaskBounds(taskView.mTaskViewTaskController, taskView.mTmpRect);
                                }
                            }
                            bubbleBarExpandedView.setAnimating(true);
                            bubbleBarExpandedView.setContentVisibility(true);
                            bubbleBarExpandedView.setSurfaceZOrderedOnTop(true);
                            TaskView taskView2 = bubbleBarExpandedView.mTaskView;
                            if (taskView2 != null) {
                                taskView2.setAlpha(0.0f);
                            }
                            bubbleBarExpandedView.setAlpha(0.0f);
                            bubbleBarExpandedView.setVisibility(0);
                        }
                        this.mPriorBubble = bubbleViewProviderPrepareExpandedView;
                    }
                    BubbleViewProvider bubbleViewProvider2 = this.mPriorBubble;
                    if (bubbleViewProvider2 != null) {
                        bubbleBarLayerView.removeView(bubbleViewProvider2.getBubbleBarExpandedView());
                        this.mPriorBubble = null;
                    }
                    if (zCanExpandView && (!transitionProgress.mTransitionReady || !transitionProgress.mSurfaceReady)) {
                        return true;
                    }
                    playAnimation(zCanExpandView);
                    return true;
                }
                i++;
            }
        }

        @Override // com.android.wm.shell.bubbles.BubbleTransitions.BubbleTransition
        public final void surfaceCreated() {
            TransitionProgress transitionProgress = this.mTransitionProgress;
            transitionProgress.mSurfaceReady = true;
            transitionProgress.onUpdate();
            BubbleTransitions.this.mMainExecutor.execute(new BubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda2(this, 0));
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
        public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        }
    }
}
