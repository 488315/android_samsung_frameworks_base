package com.android.p038wm.shell.desktopmode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.transition.Transitions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class EnterDesktopTaskTransitionHandler implements Transitions.TransitionHandler {
    public Consumer mOnAnimationFinishedCallback;
    public final List mPendingTransitionTokens;
    public Point mPosition;
    public final Supplier mTransactionSupplier;
    public final Transitions mTransitions;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.desktopmode.EnterDesktopTaskTransitionHandler$1 */
    public final class C39581 extends AnimatorListenerAdapter {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final /* synthetic */ Transitions.TransitionFinishCallback val$finishCallback;
        public final /* synthetic */ SurfaceControl.Transaction val$finishT;

        public C39581(SurfaceControl.Transaction transaction, Transitions.TransitionFinishCallback transitionFinishCallback) {
            this.val$finishT = transaction;
            this.val$finishCallback = transitionFinishCallback;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            Consumer consumer = EnterDesktopTaskTransitionHandler.this.mOnAnimationFinishedCallback;
            if (consumer != null) {
                consumer.accept(this.val$finishT);
            }
            ((HandlerExecutor) EnterDesktopTaskTransitionHandler.this.mTransitions.mMainExecutor).execute(new EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda1(this.val$finishCallback, 1));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.desktopmode.EnterDesktopTaskTransitionHandler$2 */
    public final class C39592 extends AnimatorListenerAdapter {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final /* synthetic */ Transitions.TransitionFinishCallback val$finishCallback;
        public final /* synthetic */ SurfaceControl.Transaction val$finishT;

        public C39592(SurfaceControl.Transaction transaction, Transitions.TransitionFinishCallback transitionFinishCallback) {
            this.val$finishT = transaction;
            this.val$finishCallback = transitionFinishCallback;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            Consumer consumer = EnterDesktopTaskTransitionHandler.this.mOnAnimationFinishedCallback;
            if (consumer != null) {
                consumer.accept(this.val$finishT);
            }
            ((HandlerExecutor) EnterDesktopTaskTransitionHandler.this.mTransitions.mMainExecutor).execute(new EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda1(this.val$finishCallback, 2));
        }
    }

    public EnterDesktopTaskTransitionHandler(Transitions transitions) {
        this(transitions, new EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda0());
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v2 */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        ActivityManager.RunningTaskInfo taskInfo;
        int i;
        Iterator it = transitionInfo.getChanges().iterator();
        int i2 = 0;
        boolean z = 0;
        while (true) {
            boolean hasNext = it.hasNext();
            List list = this.mPendingTransitionTokens;
            if (!hasNext) {
                ((ArrayList) list).remove(iBinder);
                return z;
            }
            TransitionInfo.Change change = (TransitionInfo.Change) it.next();
            if ((change.getFlags() & 2) == 0 && (taskInfo = change.getTaskInfo()) != null && taskInfo.taskId != -1 && change.getMode() == 6) {
                int type = transitionInfo.getType();
                if (((ArrayList) list).contains(iBinder)) {
                    ActivityManager.RunningTaskInfo taskInfo2 = change.getTaskInfo();
                    i = 1;
                    if (type == 1010 && taskInfo2.getWindowingMode() == 5) {
                        transaction.setWindowCrop(change.getLeash(), null);
                        transaction.apply();
                        ((HandlerExecutor) this.mTransitions.mMainExecutor).execute(new EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda1(transitionFinishCallback, i2));
                    } else {
                        Rect endAbsBounds = change.getEndAbsBounds();
                        Supplier supplier = this.mTransactionSupplier;
                        if (type == 1011 && taskInfo2.getWindowingMode() == 5 && !endAbsBounds.isEmpty()) {
                            SurfaceControl leash = change.getLeash();
                            transaction.setWindowCrop(leash, endAbsBounds.width(), endAbsBounds.height());
                            transaction.apply();
                            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.6666666f, 1.0f);
                            ofFloat.setDuration(336L);
                            ofFloat.addUpdateListener(new EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda2(endAbsBounds, (SurfaceControl.Transaction) supplier.get(), leash));
                            ofFloat.addListener(new C39581(transaction2, transitionFinishCallback));
                            ofFloat.start();
                        } else if (type == 1013 && taskInfo2.getWindowingMode() == 1 && this.mPosition != null) {
                            SurfaceControl leash2 = change.getLeash();
                            transaction.hide(leash2).setWindowCrop(leash2, endAbsBounds.width(), endAbsBounds.height()).apply();
                            ValueAnimator valueAnimator = new ValueAnimator();
                            valueAnimator.setFloatValues(0.4f, 1.0f);
                            valueAnimator.setDuration(336L);
                            valueAnimator.addUpdateListener(new EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda2(this, (SurfaceControl.Transaction) supplier.get(), leash2));
                            valueAnimator.addListener(new C39592(transaction2, transitionFinishCallback));
                            valueAnimator.start();
                        } else {
                            i = 0;
                        }
                    }
                } else {
                    i = i2;
                }
                z = (z ? 1 : 0) | i;
            }
            i2 = 0;
            z = z;
        }
    }

    public EnterDesktopTaskTransitionHandler(Transitions transitions, Supplier<SurfaceControl.Transaction> supplier) {
        this.mPendingTransitionTokens = new ArrayList();
        this.mTransitions = transitions;
        this.mTransactionSupplier = supplier;
    }
}
