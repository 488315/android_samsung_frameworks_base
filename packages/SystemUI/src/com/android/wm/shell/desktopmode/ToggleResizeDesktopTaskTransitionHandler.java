package com.android.wm.shell.desktopmode;

import android.animation.Animator;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.graphics.Rect;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingWindowDecoration$$ExternalSyntheticLambda2;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ToggleResizeDesktopTaskTransitionHandler implements Transitions.TransitionHandler {
    public Animator boundsAnimator;
    public DesktopTilingWindowDecoration$$ExternalSyntheticLambda2 callback;
    public Rect initialBounds;
    public final InteractionJankMonitor interactionJankMonitor;
    public DesktopModeWindowDecorViewModel.DesktopModeOnTaskResizeAnimationListener onTaskResizeAnimationListener;
    public final RectEvaluator rectEvaluator;
    public final Supplier transactionSupplier;
    public final Transitions transitions;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public ToggleResizeDesktopTaskTransitionHandler(Transitions transitions, Supplier<SurfaceControl.Transaction> supplier, InteractionJankMonitor interactionJankMonitor) {
        this.transitions = transitions;
        this.transactionSupplier = supplier;
        this.interactionJankMonitor = interactionJankMonitor;
        this.rectEvaluator = new RectEvaluator(new Rect());
    }

    public static /* synthetic */ void startTransition$default(ToggleResizeDesktopTaskTransitionHandler toggleResizeDesktopTaskTransitionHandler, WindowContainerTransaction windowContainerTransaction, Rect rect, int i) {
        if ((i & 2) != 0) {
            rect = null;
        }
        toggleResizeDesktopTaskTransitionHandler.startTransition(windowContainerTransaction, rect, null);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, final SurfaceControl.Transaction transaction, final SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback) {
        ActivityManager.RunningTaskInfo taskInfo;
        List changes = transitionInfo.getChanges();
        ArrayList arrayList = new ArrayList();
        for (Object obj : changes) {
            TransitionInfo.Change change = (TransitionInfo.Change) obj;
            change.getClass();
            if ((change.getFlags() & 2) == 0 && change.getTaskInfo() != null && ((taskInfo = change.getTaskInfo()) == null || taskInfo.taskId != -1)) {
                if (change.getMode() == 6) {
                    arrayList.add(obj);
                }
            }
        }
        if (arrayList.size() != 1) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(arrayList.size(), "Expected 1 relevant change but found: "));
        }
        TransitionInfo.Change change2 = (TransitionInfo.Change) CollectionsKt___CollectionsKt.first((List) arrayList);
        final SurfaceControl leash = change2.getLeash();
        ActivityManager.RunningTaskInfo taskInfo2 = change2.getTaskInfo();
        if (taskInfo2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        final int i = taskInfo2.taskId;
        Rect rect = this.initialBounds;
        if (rect == null) {
            rect = change2.getStartAbsBounds();
        }
        final Rect rect2 = rect;
        final Rect endAbsBounds = change2.getEndAbsBounds();
        final SurfaceControl.Transaction transaction3 = (SurfaceControl.Transaction) this.transactionSupplier.get();
        Animator animator = this.boundsAnimator;
        if (animator != null) {
            animator.cancel();
        }
        ValueAnimator duration = ValueAnimator.ofObject(this.rectEvaluator, rect2, endAbsBounds).setDuration(300L);
        duration.getClass();
        duration.addListener(new Animator.AnimatorListener(transaction2, leash, endAbsBounds, this, i, transitionFinishCallback, transaction, leash, rect2, this, i) { // from class: com.android.wm.shell.desktopmode.ToggleResizeDesktopTaskTransitionHandler$startAnimation$lambda$2$$inlined$addListener$default$1
            public final /* synthetic */ Rect $endBounds$inlined;
            public final /* synthetic */ Transitions.TransitionFinishCallback $finishCallback$inlined;
            public final /* synthetic */ SurfaceControl.Transaction $finishTransaction$inlined;
            public final /* synthetic */ SurfaceControl $leash$inlined;
            public final /* synthetic */ SurfaceControl $leash$inlined$1;
            public final /* synthetic */ Rect $startBounds$inlined;
            public final /* synthetic */ SurfaceControl.Transaction $startTransaction$inlined;
            public final /* synthetic */ int $taskId$inlined;
            public final /* synthetic */ int $taskId$inlined$1;
            public final /* synthetic */ ToggleResizeDesktopTaskTransitionHandler this$0;

            {
                this.$taskId$inlined$1 = i;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                SurfaceControl.Transaction transaction4 = this.$finishTransaction$inlined;
                SurfaceControl surfaceControl = this.$leash$inlined;
                Rect rect3 = this.$endBounds$inlined;
                transaction4.setPosition(surfaceControl, rect3.left, rect3.top).setWindowCrop(this.$leash$inlined, this.$endBounds$inlined.width(), this.$endBounds$inlined.height()).show(this.$leash$inlined);
                DesktopModeWindowDecorViewModel.DesktopModeOnTaskResizeAnimationListener desktopModeOnTaskResizeAnimationListener = this.this$0.onTaskResizeAnimationListener;
                if (desktopModeOnTaskResizeAnimationListener == null) {
                    desktopModeOnTaskResizeAnimationListener = null;
                }
                desktopModeOnTaskResizeAnimationListener.onAnimationEnd(this.$taskId$inlined);
                this.$finishCallback$inlined.onTransitionFinished(null);
                ToggleResizeDesktopTaskTransitionHandler toggleResizeDesktopTaskTransitionHandler = this.this$0;
                toggleResizeDesktopTaskTransitionHandler.initialBounds = null;
                toggleResizeDesktopTaskTransitionHandler.boundsAnimator = null;
                toggleResizeDesktopTaskTransitionHandler.interactionJankMonitor.end(104);
                this.this$0.interactionJankMonitor.end(119);
                this.this$0.interactionJankMonitor.end(118);
                DesktopTilingWindowDecoration$$ExternalSyntheticLambda2 desktopTilingWindowDecoration$$ExternalSyntheticLambda2 = this.this$0.callback;
                if (desktopTilingWindowDecoration$$ExternalSyntheticLambda2 != null) {
                    desktopTilingWindowDecoration$$ExternalSyntheticLambda2.invoke();
                }
                this.this$0.callback = null;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator2) {
                SurfaceControl.Transaction transaction4 = this.$startTransaction$inlined;
                SurfaceControl surfaceControl = this.$leash$inlined$1;
                Rect rect3 = this.$startBounds$inlined;
                transaction4.setPosition(surfaceControl, rect3.left, rect3.top).setWindowCrop(this.$leash$inlined$1, this.$startBounds$inlined.width(), this.$startBounds$inlined.height()).show(this.$leash$inlined$1);
                DesktopModeWindowDecorViewModel.DesktopModeOnTaskResizeAnimationListener desktopModeOnTaskResizeAnimationListener = this.this$0.onTaskResizeAnimationListener;
                if (desktopModeOnTaskResizeAnimationListener == null) {
                    desktopModeOnTaskResizeAnimationListener = null;
                }
                desktopModeOnTaskResizeAnimationListener.onAnimationStart(this.$taskId$inlined$1, this.$startTransaction$inlined, this.$startBounds$inlined);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator2) {
            }
        });
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.desktopmode.ToggleResizeDesktopTaskTransitionHandler$startAnimation$1$3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                Rect rect3 = (Rect) valueAnimator.getAnimatedValue();
                transaction3.setPosition(leash, rect3.left, rect3.top).setWindowCrop(leash, rect3.width(), rect3.height()).show(leash).setFrameTimeline(Choreographer.getInstance().getVsyncId());
                DesktopModeWindowDecorViewModel.DesktopModeOnTaskResizeAnimationListener desktopModeOnTaskResizeAnimationListener = this.onTaskResizeAnimationListener;
                if (desktopModeOnTaskResizeAnimationListener == null) {
                    desktopModeOnTaskResizeAnimationListener = null;
                }
                desktopModeOnTaskResizeAnimationListener.onBoundsChange(i, transaction3, rect3);
            }
        });
        duration.start();
        this.boundsAnimator = duration;
        return true;
    }

    public final void startTransition(WindowContainerTransaction windowContainerTransaction, Rect rect, DesktopTilingWindowDecoration$$ExternalSyntheticLambda2 desktopTilingWindowDecoration$$ExternalSyntheticLambda2) {
        boolean z = CoreRune.DW_SHELL_CHANGE_TRANSITION;
        Transitions transitions = this.transitions;
        if (!z || !windowContainerTransaction.hasChangeTransitMode()) {
            transitions.startTransition(VolteConstants.ErrorCode.CALL_RESUME_FAILED, windowContainerTransaction, this);
            this.initialBounds = rect;
            this.callback = desktopTilingWindowDecoration$$ExternalSyntheticLambda2;
        } else {
            transitions.mChangeTransitProvider.startChangeTransition(windowContainerTransaction);
            if (desktopTilingWindowDecoration$$ExternalSyntheticLambda2 != null) {
                desktopTilingWindowDecoration$$ExternalSyntheticLambda2.invoke();
            }
        }
    }

    public ToggleResizeDesktopTaskTransitionHandler(Transitions transitions, InteractionJankMonitor interactionJankMonitor) {
        this(transitions, new Supplier() { // from class: com.android.wm.shell.desktopmode.ToggleResizeDesktopTaskTransitionHandler.1
            @Override // java.util.function.Supplier
            public final Object get() {
                return new SurfaceControl.Transaction();
            }
        }, interactionJankMonitor);
    }
}
