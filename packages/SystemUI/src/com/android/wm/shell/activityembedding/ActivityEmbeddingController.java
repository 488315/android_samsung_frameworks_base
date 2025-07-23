package com.android.wm.shell.activityembedding;

import android.animation.Animator;
import android.content.Context;
import android.os.IBinder;
import android.util.ArrayMap;
import android.util.Log;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ActivityEmbeddingController implements Transitions.TransitionHandler {
    final ActivityEmbeddingAnimationRunner mAnimationRunner;
    public final ArrayMap mTransitionCallbacks = new ArrayMap();
    final Transitions mTransitions;

    private ActivityEmbeddingController(Context context, ShellInit shellInit, Transitions transitions) {
        Objects.requireNonNull(context);
        Objects.requireNonNull(transitions);
        this.mTransitions = transitions;
        this.mAnimationRunner = new ActivityEmbeddingAnimationRunner(context, this);
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.activityembedding.ActivityEmbeddingController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ActivityEmbeddingController activityEmbeddingController = ActivityEmbeddingController.this;
                activityEmbeddingController.mTransitions.addHandler(activityEmbeddingController);
            }
        }, this);
    }

    public static ActivityEmbeddingController create(Context context, ShellInit shellInit, Transitions transitions) {
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            return new ActivityEmbeddingController(context, shellInit, transitions);
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:72:0x012e, code lost:
    
        if (r0 == false) goto L97;
     */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0171 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:108:? A[LOOP:5: B:100:0x0139->B:108:?, LOOP_END, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean shouldAnimate(android.window.TransitionInfo r9) {
        /*
            Method dump skipped, instructions count: 371
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.activityembedding.ActivityEmbeddingController.shouldAnimate(android.window.TransitionInfo):boolean");
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        Animator animator = this.mAnimationRunner.mActiveAnimator;
        if (animator == null) {
            Log.e("ActivityEmbeddingAnimR", "No active ActivityEmbedding animator running but mergeAnimation is trying to cancel one.");
        } else {
            animator.end();
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void setAnimScaleSetting(float f) {
        this.mAnimationRunner.mAnimationSpec.mTransitionAnimationScaleSetting = f;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(final IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        int i = 0;
        if (!shouldAnimate(transitionInfo)) {
            return false;
        }
        this.mTransitionCallbacks.put(iBinder, transitionFinishCallback);
        final ActivityEmbeddingAnimationRunner activityEmbeddingAnimationRunner = this.mAnimationRunner;
        activityEmbeddingAnimationRunner.getClass();
        ArrayList arrayList = new ArrayList();
        Animator createAnimator = activityEmbeddingAnimationRunner.createAnimator(transitionInfo, transaction, transaction2, new Runnable() { // from class: com.android.wm.shell.activityembedding.ActivityEmbeddingAnimationRunner$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ActivityEmbeddingAnimationRunner activityEmbeddingAnimationRunner2 = ActivityEmbeddingAnimationRunner.this;
                Transitions.TransitionFinishCallback transitionFinishCallback2 = (Transitions.TransitionFinishCallback) activityEmbeddingAnimationRunner2.mController.mTransitionCallbacks.remove(iBinder);
                if (transitionFinishCallback2 == null) {
                    throw new IllegalStateException("No finish callback found");
                }
                transitionFinishCallback2.onTransitionFinished(null);
            }
        }, arrayList);
        activityEmbeddingAnimationRunner.mActiveAnimator = createAnimator;
        if (arrayList.isEmpty()) {
            transaction.apply();
            createAnimator.start();
            return true;
        }
        transaction.apply(true);
        SurfaceControl.Transaction transaction3 = new SurfaceControl.Transaction();
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Consumer) obj).accept(transaction3);
        }
        transaction3.apply();
        createAnimator.start();
        return true;
    }
}
