package com.android.wm.shell.desktopmode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.util.LatencyTracker;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.desktopmode.DesktopModeTransitionTypes;
import com.android.wm.shell.desktopmode.EnterDesktopTaskTransitionHandler;
import com.android.wm.shell.shared.desktopmode.DesktopModeTransitionSource;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class EnterDesktopTaskTransitionHandler implements Transitions.TransitionHandler {
    public final InteractionJankMonitor mInteractionJankMonitor;
    public final LatencyTracker mLatencyTracker;
    public DesktopModeWindowDecorViewModel.DesktopModeOnTaskResizeAnimationListener mOnTaskResizeAnimationListener;
    public final List mPendingTransitionTokens;
    public final Supplier mTransactionSupplier;
    public final Transitions mTransitions;

    /* renamed from: com.android.wm.shell.desktopmode.EnterDesktopTaskTransitionHandler$1, reason: invalid class name */
    public class AnonymousClass1 extends AnimatorListenerAdapter {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final /* synthetic */ Transitions.TransitionFinishCallback val$finishCallback;
        public final /* synthetic */ ActivityManager.RunningTaskInfo val$taskInfo;

        public AnonymousClass1(ActivityManager.RunningTaskInfo runningTaskInfo, Transitions.TransitionFinishCallback transitionFinishCallback) {
            this.val$taskInfo = runningTaskInfo;
            this.val$finishCallback = transitionFinishCallback;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            EnterDesktopTaskTransitionHandler.this.mOnTaskResizeAnimationListener.onAnimationEnd(this.val$taskInfo.taskId);
            ShellExecutor shellExecutor = EnterDesktopTaskTransitionHandler.this.mTransitions.mMainExecutor;
            final Transitions.TransitionFinishCallback transitionFinishCallback = this.val$finishCallback;
            shellExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.EnterDesktopTaskTransitionHandler$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Transitions.TransitionFinishCallback transitionFinishCallback2 = transitionFinishCallback;
                    int i = EnterDesktopTaskTransitionHandler.AnonymousClass1.$r8$clinit;
                    transitionFinishCallback2.onTransitionFinished(null);
                }
            });
            EnterDesktopTaskTransitionHandler.this.mInteractionJankMonitor.end(112);
        }
    }

    public EnterDesktopTaskTransitionHandler(Transitions transitions, InteractionJankMonitor interactionJankMonitor, LatencyTracker latencyTracker) {
        this(transitions, interactionJankMonitor, latencyTracker, new EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda0());
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    public final IBinder moveToDesktop(WindowContainerTransaction windowContainerTransaction, DesktopModeTransitionSource desktopModeTransitionSource) {
        int i = DesktopModeTransitionTypes.$r8$clinit;
        int i2 = DesktopModeTransitionTypes.WhenMappings.$EnumSwitchMapping$0[desktopModeTransitionSource.ordinal()];
        IBinder iBinderStartTransition = this.mTransitions.startTransition(i2 != 1 ? i2 != 2 ? i2 != 3 ? VolteConstants.ErrorCode.CALL_STATUS_CONF_START_SESSION_FAILURE : VolteConstants.ErrorCode.CALL_SESSION_TIMEOUT : VolteConstants.ErrorCode.CALL_SESSION_TERMINATED : VolteConstants.ErrorCode.CALL_SESSION_ABORT, windowContainerTransaction, this);
        ((ArrayList) this.mPendingTransitionTokens).add(iBinderStartTransition);
        return iBinderStartTransition;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0044  */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        ActivityManager.RunningTaskInfo taskInfo;
        boolean z;
        boolean z2 = false;
        for (TransitionInfo.Change change : transitionInfo.getChanges()) {
            if ((change.getFlags() & 2) == 0 && (taskInfo = change.getTaskInfo()) != null && taskInfo.taskId != -1 && change.getMode() == 6) {
                int type = transitionInfo.getType();
                if (((ArrayList) this.mPendingTransitionTokens).contains(iBinder)) {
                    ActivityManager.RunningTaskInfo taskInfo2 = change.getTaskInfo();
                    int i = DesktopModeTransitionTypes.$r8$clinit;
                    if (Arrays.asList(Integer.valueOf(VolteConstants.ErrorCode.CALL_SESSION_ABORT), Integer.valueOf(VolteConstants.ErrorCode.CALL_SESSION_TERMINATED), Integer.valueOf(VolteConstants.ErrorCode.CALL_SESSION_TIMEOUT), Integer.valueOf(VolteConstants.ErrorCode.CALL_STATUS_CONF_START_SESSION_FAILURE)).contains(Integer.valueOf(type)) && taskInfo2.getWindowingMode() == 5) {
                        final SurfaceControl leash = change.getLeash();
                        Rect startAbsBounds = change.getStartAbsBounds();
                        final ActivityManager.RunningTaskInfo taskInfo3 = change.getTaskInfo();
                        if (this.mOnTaskResizeAnimationListener == null) {
                            Slog.e("EnterDesktopTaskTransitionHandler", "onTaskResizeAnimationListener is not available for this transition");
                            z = false;
                            z2 |= z;
                        } else {
                            transaction.setPosition(leash, startAbsBounds.left, startAbsBounds.top).setWindowCrop(leash, startAbsBounds.width(), startAbsBounds.height()).show(leash);
                            this.mOnTaskResizeAnimationListener.onAnimationStart(taskInfo3.taskId, transaction, startAbsBounds);
                            final ValueAnimator valueAnimatorOfObject = ValueAnimator.ofObject(new RectEvaluator(), change.getStartAbsBounds(), change.getEndAbsBounds());
                            valueAnimatorOfObject.setDuration(336L);
                            final SurfaceControl.Transaction transaction3 = (SurfaceControl.Transaction) this.mTransactionSupplier.get();
                            valueAnimatorOfObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.desktopmode.EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda1
                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    EnterDesktopTaskTransitionHandler enterDesktopTaskTransitionHandler = this.f$0;
                                    ValueAnimator valueAnimator2 = valueAnimatorOfObject;
                                    SurfaceControl.Transaction transaction4 = transaction3;
                                    SurfaceControl surfaceControl = leash;
                                    ActivityManager.RunningTaskInfo runningTaskInfo = taskInfo3;
                                    enterDesktopTaskTransitionHandler.getClass();
                                    Rect rect = (Rect) valueAnimator2.getAnimatedValue();
                                    transaction4.setPosition(surfaceControl, rect.left, rect.top).setWindowCrop(surfaceControl, rect.width(), rect.height()).show(surfaceControl);
                                    enterDesktopTaskTransitionHandler.mOnTaskResizeAnimationListener.onBoundsChange(runningTaskInfo.taskId, transaction4, rect);
                                }
                            });
                            valueAnimatorOfObject.addListener(new AnonymousClass1(taskInfo3, transitionFinishCallback));
                            valueAnimatorOfObject.start();
                            z = true;
                            z2 |= z;
                        }
                    }
                } else {
                    z = false;
                    z2 |= z;
                }
            }
        }
        if (z2 && transitionInfo.getType() == 1101) {
            this.mLatencyTracker.onActionEnd(31);
        }
        ((ArrayList) this.mPendingTransitionTokens).remove(iBinder);
        return z2;
    }

    public EnterDesktopTaskTransitionHandler(Transitions transitions, InteractionJankMonitor interactionJankMonitor, LatencyTracker latencyTracker, Supplier<SurfaceControl.Transaction> supplier) {
        this.mPendingTransitionTokens = new ArrayList();
        this.mTransitions = transitions;
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mLatencyTracker = latencyTracker;
        this.mTransactionSupplier = supplier;
    }
}
