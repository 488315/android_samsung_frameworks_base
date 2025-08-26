package com.android.wm.shell.activityembedding;

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
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.DefaultTransitionHandler;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.core.CompatSandbox;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

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
                ActivityEmbeddingController activityEmbeddingController = this.f$0;
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

    /* JADX WARN: Removed duplicated region for block: B:83:0x014b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean shouldAnimate(TransitionInfo transitionInfo) {
        boolean z;
        boolean z2;
        if (transitionInfo.getType() != 1017) {
            Iterator it = transitionInfo.getChanges().iterator();
            boolean z3 = false;
            while (true) {
                if (it.hasNext()) {
                    TransitionInfo.Change change = (TransitionInfo.Change) it.next();
                    if (!change.hasFlags(1024) && change.hasFlags(512) && (!CompatSandbox.isAppCompatOverrideEnabled(change.getConfiguration()) || change.getConfiguration().windowConfiguration.getWindowingMode() != 1)) {
                        if (change.getPopOverAnimationNeeded()) {
                            break;
                        }
                        if (CoreRune.MW_EMBED_ACTIVITY) {
                            TransitionInfo.AnimationOptions animationOptions = change.getAnimationOptions();
                            if ((animationOptions != null ? animationOptions.getType() : 0) == 0 && change.hasFlags(4) && change.hasFlags(64)) {
                                break;
                            }
                        }
                        z3 = true;
                    }
                } else if (z3) {
                    Iterator it2 = transitionInfo.getChanges().iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            z = false;
                            break;
                        }
                        if (!((TransitionInfo.Change) it2.next()).hasFlags(512)) {
                            z = true;
                            break;
                        }
                    }
                    if (z) {
                        List changes = transitionInfo.getChanges();
                        Rect rect = new Rect();
                        int size = changes.size() - 1;
                        while (true) {
                            if (size >= 0) {
                                TransitionInfo.Change change2 = (TransitionInfo.Change) changes.get(size);
                                if (!TransitionUtil.isClosingType(change2.getMode())) {
                                    if (change2.hasFlags(512)) {
                                        rect.union(change2.getEndAbsBounds());
                                    } else {
                                        if (!CoreRune.MW_EMBED_ACTIVITY || !TransitionUtil.isOpeningType(change2.getMode()) || !change2.hasFlags(4) || change2.getConfiguration().windowConfiguration.getWindowingMode() != 1) {
                                            break;
                                        }
                                        rect.union(change2.getEndAbsBounds());
                                    }
                                }
                                size--;
                            } else {
                                for (int size2 = changes.size() - 1; size2 >= 0; size2--) {
                                    TransitionInfo.Change change3 = (TransitionInfo.Change) changes.get(size2);
                                    if (change3.hasFlags(512) || rect.contains(change3.getEndAbsBounds())) {
                                    }
                                }
                                for (int size3 = changes.size() - 1; size3 >= 0; size3--) {
                                    if (!((TransitionInfo.Change) changes.get(size3)).hasFlags(512)) {
                                        changes.remove(size3);
                                    }
                                }
                                z2 = true;
                            }
                        }
                        z2 = false;
                        if (z2) {
                        }
                    }
                    Iterator it3 = transitionInfo.getChanges().iterator();
                    while (it3.hasNext()) {
                        TransitionInfo.AnimationOptions animationOptions2 = ((TransitionInfo.Change) it3.next()).getAnimationOptions();
                        if (animationOptions2 != null) {
                            boolean z4 = animationOptions2.getType() == 5 ? false : (animationOptions2.getType() == 1 || (CoreRune.MW_EMBED_ACTIVITY_ANIMATION && animationOptions2.getType() == 14)) ? true : !DefaultTransitionHandler.isSupportedOverrideAnimation(animationOptions2);
                            if (!z4) {
                            }
                        }
                    }
                }
            }
            return false;
        }
        return true;
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
        Animator animatorCreateAnimator = activityEmbeddingAnimationRunner.createAnimator(transitionInfo, transaction, transaction2, new Runnable() { // from class: com.android.wm.shell.activityembedding.ActivityEmbeddingAnimationRunner$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ActivityEmbeddingAnimationRunner activityEmbeddingAnimationRunner2 = activityEmbeddingAnimationRunner;
                Transitions.TransitionFinishCallback transitionFinishCallback2 = (Transitions.TransitionFinishCallback) activityEmbeddingAnimationRunner2.mController.mTransitionCallbacks.remove(iBinder);
                if (transitionFinishCallback2 == null) {
                    throw new IllegalStateException("No finish callback found");
                }
                transitionFinishCallback2.onTransitionFinished(null);
            }
        }, arrayList);
        activityEmbeddingAnimationRunner.mActiveAnimator = animatorCreateAnimator;
        if (arrayList.isEmpty()) {
            transaction.apply();
            animatorCreateAnimator.start();
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
        animatorCreateAnimator.start();
        return true;
    }
}
