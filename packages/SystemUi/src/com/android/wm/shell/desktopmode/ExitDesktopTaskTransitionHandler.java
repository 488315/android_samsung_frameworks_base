package com.android.wm.shell.desktopmode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.transition.Transitions;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ExitDesktopTaskTransitionHandler implements Transitions.TransitionHandler {
    public final Context mContext;
    public Consumer mOnAnimationFinishedCallback;
    public final List mPendingTransitionTokens;
    public Point mPosition;
    public final Supplier mTransactionSupplier;
    public final Transitions mTransitions;

    public ExitDesktopTaskTransitionHandler(Transitions transitions, Context context) {
        this(transitions, new EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda0(), context);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        ActivityManager.RunningTaskInfo taskInfo;
        boolean z = false;
        for (TransitionInfo.Change change : transitionInfo.getChanges()) {
            if ((change.getFlags() & 2) == 0 && (taskInfo = change.getTaskInfo()) != null && taskInfo.taskId != -1 && change.getMode() == 6) {
                z |= startChangeTransition(iBinder, transitionInfo.getType(), change, transaction, transaction2, transitionFinishCallback);
            }
        }
        ((ArrayList) this.mPendingTransitionTokens).remove(iBinder);
        return z;
    }

    public boolean startChangeTransition(IBinder iBinder, int i, TransitionInfo.Change change, SurfaceControl.Transaction transaction, final SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback) {
        if (!((ArrayList) this.mPendingTransitionTokens).contains(iBinder)) {
            return false;
        }
        ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
        if (i != 1012 || taskInfo.getWindowingMode() != 1) {
            return false;
        }
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        int i2 = displayMetrics.widthPixels;
        int i3 = displayMetrics.heightPixels;
        final SurfaceControl leash = change.getLeash();
        Rect endAbsBounds = change.getEndAbsBounds();
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
                float m20m = DependencyGraph$$ExternalSyntheticOutline0.m20m(1.0f, f, animatedFraction, f);
                float m20m2 = DependencyGraph$$ExternalSyntheticOutline0.m20m(1.0f, f2, animatedFraction, f2);
                Point point = exitDesktopTaskTransitionHandler.mPosition;
                float f3 = 1.0f - animatedFraction;
                transaction4.setPosition(surfaceControl, point.x * f3, point.y * f3).setScale(surfaceControl, m20m, m20m2).show(surfaceControl).apply();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.desktopmode.ExitDesktopTaskTransitionHandler.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                Consumer consumer = ExitDesktopTaskTransitionHandler.this.mOnAnimationFinishedCallback;
                if (consumer != null) {
                    consumer.accept(transaction2);
                }
                ShellExecutor shellExecutor = ExitDesktopTaskTransitionHandler.this.mTransitions.mMainExecutor;
                final Transitions.TransitionFinishCallback transitionFinishCallback2 = transitionFinishCallback;
                ((HandlerExecutor) shellExecutor).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.ExitDesktopTaskTransitionHandler$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        Transitions.TransitionFinishCallback.this.onTransitionFinished(null, null);
                    }
                });
            }
        });
        valueAnimator.start();
        return true;
    }

    private ExitDesktopTaskTransitionHandler(Transitions transitions, Supplier<SurfaceControl.Transaction> supplier, Context context) {
        this.mPendingTransitionTokens = new ArrayList();
        this.mTransitions = transitions;
        this.mTransactionSupplier = supplier;
        this.mContext = context;
    }
}
