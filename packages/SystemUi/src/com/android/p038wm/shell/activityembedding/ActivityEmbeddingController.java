package com.android.p038wm.shell.activityembedding;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.ArrayMap;
import android.util.Log;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.p038wm.shell.sysui.ShellInit;
import com.android.p038wm.shell.transition.Transitions;
import com.android.p038wm.shell.util.TransitionUtil;
import com.samsung.android.core.CompatSandbox;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ActivityEmbeddingController implements Transitions.TransitionHandler {
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

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
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

    /* JADX WARN: Code restructure failed: missing block: B:86:0x00ee, code lost:
    
        if ((r0 == 1 || r0 == 2 || r0 == 3 || r0 == 4 || r0 == 11 || r0 == 12) != false) goto L73;
     */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startAnimation(final IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        boolean z;
        List changes = transitionInfo.getChanges();
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        for (int size = changes.size() - 1; size >= 0; size--) {
            TransitionInfo.Change change = (TransitionInfo.Change) changes.get(size);
            if (!change.hasFlags(512)) {
                z3 = true;
            } else if (change.hasFlags(1024)) {
                continue;
            } else {
                if (CoreRune.MW_EMBED_ACTIVITY_ANIMATION && change.getMode() == 6) {
                    z4 = true;
                }
                if (!CompatSandbox.isBoundsCompatModeEnabled(change.getConfiguration()) || change.getConfiguration().windowConfiguration.getWindowingMode() != 1) {
                    if (change.getPopOverAnimationNeeded()) {
                        return false;
                    }
                    z2 = true;
                }
            }
        }
        if (!z2) {
            return false;
        }
        if (z3) {
            Rect rect = new Rect();
            int size2 = changes.size() - 1;
            while (true) {
                if (size2 >= 0) {
                    TransitionInfo.Change change2 = (TransitionInfo.Change) changes.get(size2);
                    if (!TransitionUtil.isClosingType(change2.getMode())) {
                        if (!change2.hasFlags(512)) {
                            break;
                        }
                        rect.union(change2.getEndAbsBounds());
                    }
                    size2--;
                } else {
                    for (int size3 = changes.size() - 1; size3 >= 0; size3--) {
                        TransitionInfo.Change change3 = (TransitionInfo.Change) changes.get(size3);
                        if (change3.hasFlags(512) || rect.contains(change3.getEndAbsBounds())) {
                        }
                    }
                    for (int size4 = changes.size() - 1; size4 >= 0; size4--) {
                        if (!((TransitionInfo.Change) changes.get(size4)).hasFlags(512)) {
                            changes.remove(size4);
                        }
                    }
                    z = true;
                }
            }
            z = false;
            if (!z) {
                return false;
            }
        }
        TransitionInfo.AnimationOptions animationOptions = transitionInfo.getAnimationOptions();
        if (animationOptions != null) {
            if (animationOptions.getType() != 5) {
                int type = animationOptions.getType();
            }
            if (!CoreRune.MW_EMBED_ACTIVITY_ANIMATION || !z4) {
                return false;
            }
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
                transitionFinishCallback2.onTransitionFinished(null, null);
            }
        }, arrayList);
        activityEmbeddingAnimationRunner.mActiveAnimator = createAnimator;
        if (arrayList.isEmpty()) {
            transaction.apply();
            createAnimator.start();
        } else {
            transaction.apply(true);
            SurfaceControl.Transaction transaction3 = new SurfaceControl.Transaction();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((Consumer) it.next()).accept(transaction3);
            }
            transaction3.apply();
            createAnimator.start();
        }
        return true;
    }
}
