package com.android.wm.shell.desktopmode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.util.LatencyTracker;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ExitDesktopTaskTransitionHandler implements Transitions.TransitionHandler {
    public final ArrayList mAnimators;
    public final Context mContext;
    public ExitDesktopTaskTransitionHandler$$ExternalSyntheticLambda2 mFinishCallback;
    public final Handler mHandler;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public final LatencyTracker mLatencyTracker;
    public DesktopTasksController$$ExternalSyntheticLambda1 mOnAnimationFinishedCallback;
    public final List mPendingTransitionTokens;
    public Point mPosition;
    public final Supplier mTransactionSupplier;
    public final Transitions mTransitions;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.desktopmode.ExitDesktopTaskTransitionHandler$1, reason: invalid class name */
    public class AnonymousClass1 extends AnimatorListenerAdapter {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final /* synthetic */ ValueAnimator val$animator;
        public final /* synthetic */ Transitions.TransitionFinishCallback val$finishCallback;

        public AnonymousClass1(ValueAnimator valueAnimator, Transitions.TransitionFinishCallback transitionFinishCallback) {
            this.val$animator = valueAnimator;
            this.val$finishCallback = transitionFinishCallback;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            if (CoreRune.DW_SHELL_CHANGE_TRANSITION) {
                ExitDesktopTaskTransitionHandler.this.mInteractionJankMonitor.end(108);
                ExitDesktopTaskTransitionHandler.this.mTransitions.mMainExecutor.execute(new ExitDesktopTaskTransitionHandler$$ExternalSyntheticLambda2(1, this, this.val$animator));
                return;
            }
            DesktopTasksController$$ExternalSyntheticLambda1 desktopTasksController$$ExternalSyntheticLambda1 = ExitDesktopTaskTransitionHandler.this.mOnAnimationFinishedCallback;
            if (desktopTasksController$$ExternalSyntheticLambda1 != null) {
                desktopTasksController$$ExternalSyntheticLambda1.invoke();
            }
            ExitDesktopTaskTransitionHandler.this.mInteractionJankMonitor.end(108);
            ExitDesktopTaskTransitionHandler.this.mTransitions.mMainExecutor.execute(new ExitDesktopTaskTransitionHandler$$ExternalSyntheticLambda1(this.val$finishCallback, 2));
        }
    }

    public ExitDesktopTaskTransitionHandler(Transitions transitions, Context context, InteractionJankMonitor interactionJankMonitor, Handler handler) {
        this(transitions, new EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda0(), context, interactionJankMonitor, handler);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    public final void onFinish() {
        ExitDesktopTaskTransitionHandler$$ExternalSyntheticLambda2 exitDesktopTaskTransitionHandler$$ExternalSyntheticLambda2;
        Log.d("ExitDesktopTaskTransitionHandler", "onFinish: num_anim=" + this.mAnimators.size() + ", cb=" + this.mFinishCallback);
        if (this.mAnimators.isEmpty() && (exitDesktopTaskTransitionHandler$$ExternalSyntheticLambda2 = this.mFinishCallback) != null) {
            exitDesktopTaskTransitionHandler$$ExternalSyntheticLambda2.run();
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        Transitions transitions;
        ActivityManager.RunningTaskInfo taskInfo;
        Iterator it = transitionInfo.getChanges().iterator();
        int i = 0;
        boolean z = false;
        while (true) {
            boolean hasNext = it.hasNext();
            transitions = this.mTransitions;
            if (!hasNext) {
                break;
            }
            TransitionInfo.Change change = (TransitionInfo.Change) it.next();
            if ((change.getFlags() & 2) == 0 && (taskInfo = change.getTaskInfo()) != null && taskInfo.taskId != -1) {
                if (CoreRune.DW_SHELL_CHANGE_TRANSITION && ((ArrayList) this.mPendingTransitionTokens).contains(iBinder) && DesktopModeTransitionTypes.isExitDesktopModeTransition(transitionInfo.getType()) && change.getChangeLeash() != null) {
                    Log.d("ExitDesktopTaskTransitionHandler", " buildChangeTransitionIfNeeded: " + change.getLeash());
                    transitions.mChangeTransitProvider.buildChangeTransitionAnimators(this.mAnimators, change, new ExitDesktopTaskTransitionHandler$$ExternalSyntheticLambda1(this, 1), transaction, transitionInfo);
                    z = true;
                } else if (change.getMode() == 6) {
                    z = startChangeTransition(iBinder, transitionInfo.getType(), change, transaction, transaction2, transitionFinishCallback) | z;
                }
            }
        }
        if (CoreRune.DW_SHELL_CHANGE_TRANSITION && z) {
            this.mFinishCallback = new ExitDesktopTaskTransitionHandler$$ExternalSyntheticLambda2(0, this, transitionFinishCallback);
            Log.d("ExitDesktopTaskTransitionHandler", "startAllAnimators: num_anim=" + this.mAnimators.size());
            ArrayList arrayList = this.mAnimators;
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                transitions.mAnimExecutor.execute(new ExitDesktopTaskTransitionHandler$$ExternalSyntheticLambda1((Animator) obj, 0));
            }
            transaction.apply();
        }
        ((ArrayList) this.mPendingTransitionTokens).remove(iBinder);
        if (z) {
            this.mLatencyTracker.onActionEnd(32);
        }
        return z;
    }

    public boolean startChangeTransition(IBinder iBinder, int i, TransitionInfo.Change change, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        if (!((ArrayList) this.mPendingTransitionTokens).contains(iBinder)) {
            return false;
        }
        ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
        if (!DesktopModeTransitionTypes.isExitDesktopModeTransition(i) || taskInfo.getWindowingMode() != 1) {
            return false;
        }
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        int i2 = displayMetrics.widthPixels;
        int i3 = displayMetrics.heightPixels;
        final SurfaceControl leash = change.getLeash();
        Rect endAbsBounds = change.getEndAbsBounds();
        this.mInteractionJankMonitor.begin(leash, this.mContext, this.mHandler, 108);
        transaction.hide(leash).setWindowCrop(leash, endAbsBounds.width(), endAbsBounds.height()).apply();
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(0.0f, 1.0f);
        valueAnimator.setDuration(336L);
        Rect startAbsBounds = change.getStartAbsBounds();
        final float width = startAbsBounds.width() / i2;
        final float height = startAbsBounds.height() / i3;
        final SurfaceControl.Transaction transaction3 = (SurfaceControl.Transaction) this.mTransactionSupplier.get();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.desktopmode.ExitDesktopTaskTransitionHandler$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                ExitDesktopTaskTransitionHandler exitDesktopTaskTransitionHandler = ExitDesktopTaskTransitionHandler.this;
                float f = width;
                float f2 = height;
                SurfaceControl.Transaction transaction4 = transaction3;
                SurfaceControl surfaceControl = leash;
                exitDesktopTaskTransitionHandler.getClass();
                float animatedFraction = valueAnimator2.getAnimatedFraction();
                float m$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(1.0f, f, animatedFraction, f);
                float m$12 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(1.0f, f2, animatedFraction, f2);
                Point point = exitDesktopTaskTransitionHandler.mPosition;
                float f3 = 1.0f - animatedFraction;
                transaction4.setPosition(surfaceControl, point.x * f3, point.y * f3).setScale(surfaceControl, m$1, m$12).show(surfaceControl).setFrameTimeline(Choreographer.getInstance().getVsyncId()).apply();
            }
        });
        valueAnimator.addListener(new AnonymousClass1(valueAnimator, transitionFinishCallback));
        if (CoreRune.DW_SHELL_CHANGE_TRANSITION) {
            this.mAnimators.add(valueAnimator);
            return true;
        }
        valueAnimator.start();
        return true;
    }

    private ExitDesktopTaskTransitionHandler(Transitions transitions, Supplier<SurfaceControl.Transaction> supplier, Context context, InteractionJankMonitor interactionJankMonitor, Handler handler) {
        this.mPendingTransitionTokens = new ArrayList();
        this.mAnimators = new ArrayList();
        this.mTransitions = transitions;
        this.mTransactionSupplier = supplier;
        this.mContext = context;
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mLatencyTracker = LatencyTracker.getInstance(context);
        this.mHandler = handler;
    }
}
