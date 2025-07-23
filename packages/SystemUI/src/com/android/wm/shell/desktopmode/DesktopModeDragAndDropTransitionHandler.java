package com.android.wm.shell.desktopmode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.graphics.Rect;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.transition.Transitions;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopModeDragAndDropTransitionHandler implements Transitions.TransitionHandler {
    public final List pendingTransitionTokens = new ArrayList();
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

    public DesktopModeDragAndDropTransitionHandler(Transitions transitions) {
        this.transitions = transitions;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback) {
        ActivityManager.RunningTaskInfo taskInfo;
        if (!((ArrayList) this.pendingTransitionTokens).contains(iBinder)) {
            return false;
        }
        List changes = transitionInfo.getChanges();
        ArrayList arrayList = new ArrayList();
        for (Object obj : changes) {
            TransitionInfo.Change change = (TransitionInfo.Change) obj;
            change.getClass();
            if (change.getTaskInfo() != null && ((taskInfo = change.getTaskInfo()) == null || taskInfo.taskId != -1)) {
                if (change.getMode() == 1) {
                    arrayList.add(obj);
                }
            }
        }
        if (arrayList.size() != 1) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(arrayList.size(), "Expected 1 relevant change but found: "));
        }
        TransitionInfo.Change change2 = (TransitionInfo.Change) CollectionsKt___CollectionsKt.first((List) arrayList);
        final SurfaceControl leash = change2.getLeash();
        Rect endAbsBounds = change2.getEndAbsBounds();
        transaction.hide(leash).setWindowCrop(leash, endAbsBounds.width(), endAbsBounds.height()).apply();
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(0.0f, 1.0f);
        valueAnimator.setDuration(300L);
        final SurfaceControl.Transaction transaction3 = new SurfaceControl.Transaction();
        valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.desktopmode.DesktopModeDragAndDropTransitionHandler$startAnimation$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                transitionFinishCallback.onTransitionFinished(null);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                transaction3.show(leash);
                transaction3.apply();
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.desktopmode.DesktopModeDragAndDropTransitionHandler$startAnimation$2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                transaction3.setAlpha(leash, valueAnimator2.getAnimatedFraction());
                transaction3.apply();
            }
        });
        valueAnimator.start();
        ((ArrayList) this.pendingTransitionTokens).remove(iBinder);
        return true;
    }
}
