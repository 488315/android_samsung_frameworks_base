package com.android.wm.shell.desktopmode;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.IBinder;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.transition.Transitions;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

/* loaded from: classes3.dex */
public final class DesktopModeMoveToDisplayTransitionHandler implements Transitions.TransitionHandler {
    public static final long ANIM_DURATION;
    public final SurfaceControl.Transaction animationTransaction;
    public final DisplayController displayController;
    public final InteractionJankMonitor interactionJankMonitor;
    public final Handler shellMainHandler;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        Duration.Companion companion = Duration.Companion;
        ANIM_DURATION = DurationKt.toDuration(100, DurationUnit.MILLISECONDS);
    }

    public DesktopModeMoveToDisplayTransitionHandler(SurfaceControl.Transaction transaction, InteractionJankMonitor interactionJankMonitor, Handler handler, DisplayController displayController) {
        this.animationTransaction = transaction;
        this.interactionJankMonitor = interactionJankMonitor;
        this.shellMainHandler = handler;
        this.displayController = displayController;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, final SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback) {
        List changes = transitionInfo.getChanges();
        final ArrayList arrayList = new ArrayList();
        for (Object obj : changes) {
            TransitionInfo.Change change = (TransitionInfo.Change) obj;
            if (change.getStartDisplayId() != change.getEndDisplayId()) {
                arrayList.add(obj);
            }
        }
        int i = 0;
        if (arrayList.isEmpty()) {
            return false;
        }
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj2 = arrayList.get(i2);
            i2++;
            TransitionInfo.Change change2 = (TransitionInfo.Change) obj2;
            Rect endAbsBounds = change2.getEndAbsBounds();
            Point endRelOffset = change2.getEndRelOffset();
            transaction.setPosition(change2.getLeash(), endRelOffset.x, endRelOffset.y).setWindowCrop(change2.getLeash(), endAbsBounds.width(), endAbsBounds.height());
        }
        transaction.apply();
        AnimatorSet animatorSet = new AnimatorSet();
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        int size2 = arrayList.size();
        while (i < size2) {
            Object obj3 = arrayList.get(i);
            i++;
            final TransitionInfo.Change change3 = (TransitionInfo.Change) obj3;
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat.setDuration(Duration.m3457getInWholeMillisecondsimpl(ANIM_DURATION));
            valueAnimatorOfFloat.setInterpolator(Interpolators.LINEAR);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.desktopmode.DesktopModeMoveToDisplayTransitionHandler$startAnimation$1$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.this$0.animationTransaction.setAlpha(change3.getLeash(), ((Float) valueAnimator.getAnimatedValue()).floatValue()).setFrameTimeline(Choreographer.getInstance().getVsyncId()).apply();
                }
            });
            arrayList2.add(valueAnimatorOfFloat);
        }
        animatorSet.playTogether(arrayList2);
        animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.desktopmode.DesktopModeMoveToDisplayTransitionHandler.startAnimation.2
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                transaction2.apply();
                transitionFinishCallback.onTransitionFinished(null);
                DesktopModeMoveToDisplayTransitionHandler.this.interactionJankMonitor.cancel(129);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                transaction2.apply();
                transitionFinishCallback.onTransitionFinished(null);
                DesktopModeMoveToDisplayTransitionHandler.this.interactionJankMonitor.end(129);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                Context displayContext = DesktopModeMoveToDisplayTransitionHandler.this.displayController.getDisplayContext(((TransitionInfo.Change) arrayList.get(0)).getEndDisplayId());
                if (displayContext == null) {
                    return;
                }
                DesktopModeMoveToDisplayTransitionHandler.this.interactionJankMonitor.begin(((TransitionInfo.Change) arrayList.get(0)).getLeash(), displayContext, DesktopModeMoveToDisplayTransitionHandler.this.shellMainHandler, 129);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }
        });
        animatorSet.start();
        return true;
    }
}
