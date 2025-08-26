package com.android.wm.shell.freeform;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.WindowConfiguration;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.IBinder;
import android.util.ArrayMap;
import android.util.Log;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.shared.animation.MinimizeAnimator;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public class FreeformTaskTransitionHandler implements Transitions.TransitionHandler, FreeformTaskTransitionStarter {
    public final ShellExecutor mAnimExecutor;
    public final Handler mAnimHandler;
    public final DisplayController mDisplayController;
    public final ShellExecutor mMainExecutor;
    public final Transitions mTransitions;
    public final List mPendingTransitionTokens = new ArrayList();
    public final ArrayMap mAnimations = new ArrayMap();

    /* renamed from: com.android.wm.shell.freeform.FreeformTaskTransitionHandler$1, reason: invalid class name */
    public class AnonymousClass1 extends AnimatorListenerAdapter {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final /* synthetic */ ArrayList val$animations;
        public final /* synthetic */ ValueAnimator val$animator;
        public final /* synthetic */ Runnable val$onAnimFinish;

        public AnonymousClass1(ArrayList arrayList, ValueAnimator valueAnimator, Runnable runnable) {
            this.val$animations = arrayList;
            this.val$animator = valueAnimator;
            this.val$onAnimFinish = runnable;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            FreeformTaskTransitionHandler.this.mMainExecutor.execute(new FreeformTaskTransitionHandler$$ExternalSyntheticLambda6(this.val$animations, this.val$animator, this.val$onAnimFinish));
        }
    }

    public FreeformTaskTransitionHandler(Transitions transitions, DisplayController displayController, ShellExecutor shellExecutor, ShellExecutor shellExecutor2, Handler handler) {
        this.mTransitions = transitions;
        this.mDisplayController = displayController;
        this.mMainExecutor = shellExecutor;
        this.mAnimExecutor = shellExecutor2;
        this.mAnimHandler = handler;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        ArrayList arrayList = (ArrayList) this.mAnimations.get(iBinder2);
        if (arrayList == null) {
            return;
        }
        this.mAnimExecutor.execute(new FreeformTaskTransitionHandler$$ExternalSyntheticLambda0(0, arrayList));
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        ActivityManager.RunningTaskInfo taskInfo;
        boolean z;
        boolean z2;
        final ArrayList arrayList = new ArrayList();
        final FreeformTaskTransitionHandler$$ExternalSyntheticLambda1 freeformTaskTransitionHandler$$ExternalSyntheticLambda1 = new FreeformTaskTransitionHandler$$ExternalSyntheticLambda1(this, arrayList, iBinder, transitionFinishCallback, 0);
        boolean z3 = false;
        for (TransitionInfo.Change change : transitionInfo.getChanges()) {
            if ((change.getFlags() & 2) == 0 && (taskInfo = change.getTaskInfo()) != null && taskInfo.taskId != -1) {
                int mode = change.getMode();
                DisplayController displayController = this.mDisplayController;
                if (mode != 2) {
                    if (mode == 4) {
                        int type = transitionInfo.getType();
                        if (((ArrayList) this.mPendingTransitionTokens).contains(iBinder)) {
                            ActivityManager.RunningTaskInfo taskInfo2 = change.getTaskInfo();
                            if (type == 1020) {
                                SurfaceControl.Transaction transaction3 = new SurfaceControl.Transaction();
                                transaction2.hide(change.getLeash());
                                Context displayContext = displayController.getDisplayContext(taskInfo2.displayId);
                                if (displayContext == null) {
                                    Log.w("FreeformTaskTransitionHandler", "No displayContext for displayId=" + taskInfo2.displayId);
                                } else {
                                    arrayList.add(MinimizeAnimator.create(displayContext, change, transaction3, new Function1() { // from class: com.android.wm.shell.freeform.FreeformTaskTransitionHandler$$ExternalSyntheticLambda4
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj) {
                                            ArrayList arrayList2 = arrayList;
                                            FreeformTaskTransitionHandler$$ExternalSyntheticLambda1 freeformTaskTransitionHandler$$ExternalSyntheticLambda12 = freeformTaskTransitionHandler$$ExternalSyntheticLambda1;
                                            FreeformTaskTransitionHandler freeformTaskTransitionHandler = this.f$0;
                                            freeformTaskTransitionHandler.getClass();
                                            freeformTaskTransitionHandler.mMainExecutor.execute(new FreeformTaskTransitionHandler$$ExternalSyntheticLambda6(arrayList2, (Animator) obj, freeformTaskTransitionHandler$$ExternalSyntheticLambda12));
                                            return null;
                                        }
                                    }, InteractionJankMonitor.getInstance(), this.mAnimHandler));
                                    z2 = true;
                                }
                            }
                        }
                        z2 = false;
                    } else if (mode == 6) {
                        int type2 = transitionInfo.getType();
                        if (!CoreRune.MW_SHELL_CHANGE_TRANSITION && ((ArrayList) this.mPendingTransitionTokens).contains(iBinder)) {
                            ActivityManager.RunningTaskInfo taskInfo3 = change.getTaskInfo();
                            z = type2 == 1008 && taskInfo3.getWindowingMode() == 1;
                            if (type2 == 1009 && taskInfo3.getWindowingMode() == 5) {
                                z = true;
                            }
                        } else {
                            z = false;
                        }
                        z3 |= z;
                    }
                } else if (change.getTaskInfo().getWindowingMode() == 5 && !CoreRune.MW_FREEFORM_SHELL_TRANSITION) {
                    if (((ArrayList) this.mPendingTransitionTokens).contains(iBinder)) {
                        final int i = displayController.getDisplayLayout(change.getTaskInfo().displayId).mHeight;
                        ValueAnimator valueAnimator = new ValueAnimator();
                        valueAnimator.setDuration(400L).setFloatValues(0.0f, 1.0f);
                        final SurfaceControl.Transaction transaction4 = new SurfaceControl.Transaction();
                        final SurfaceControl leash = change.getLeash();
                        transaction2.hide(leash);
                        final Rect rect = new Rect(change.getStartAbsBounds());
                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.freeform.FreeformTaskTransitionHandler$$ExternalSyntheticLambda3
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                                Rect rect2 = rect;
                                int i2 = i;
                                SurfaceControl.Transaction transaction5 = transaction4;
                                SurfaceControl surfaceControl = leash;
                                float f = i2;
                                float animatedFraction = (valueAnimator2.getAnimatedFraction() * f) + rect2.top;
                                transaction5.setPosition(surfaceControl, rect2.left, animatedFraction);
                                if (animatedFraction > f) {
                                    transaction5.hide(surfaceControl);
                                }
                                transaction5.apply();
                            }
                        });
                        valueAnimator.addListener(new AnonymousClass1(arrayList, valueAnimator, freeformTaskTransitionHandler$$ExternalSyntheticLambda1));
                        arrayList.add(valueAnimator);
                        z2 = true;
                    }
                    z2 = false;
                }
                z3 |= z2;
            }
        }
        if (!z3) {
            return false;
        }
        this.mAnimations.put(iBinder, arrayList);
        transaction.apply();
        this.mAnimExecutor.execute(new FreeformTaskTransitionHandler$$ExternalSyntheticLambda0(1, arrayList));
        freeformTaskTransitionHandler$$ExternalSyntheticLambda1.run();
        ((ArrayList) this.mPendingTransitionTokens).remove(iBinder);
        return true;
    }

    @Override // com.android.wm.shell.freeform.FreeformTaskTransitionStarter
    public final IBinder startMinimizeAllTransition(WindowContainerTransaction windowContainerTransaction, int i) {
        return null;
    }

    @Override // com.android.wm.shell.freeform.FreeformTaskTransitionStarter
    public final IBinder startMinimizedModeTransition(int i, WindowContainerTransaction windowContainerTransaction, boolean z) {
        IBinder iBinderStartTransition = this.mTransitions.startTransition(1020, windowContainerTransaction, this);
        ((ArrayList) this.mPendingTransitionTokens).add(iBinderStartTransition);
        return iBinderStartTransition;
    }

    @Override // com.android.wm.shell.freeform.FreeformTaskTransitionStarter
    public final IBinder startPipTransition(WindowContainerTransaction windowContainerTransaction) {
        IBinder iBinderStartTransition = this.mTransitions.startTransition(10, windowContainerTransaction, null);
        ((ArrayList) this.mPendingTransitionTokens).add(iBinderStartTransition);
        return iBinderStartTransition;
    }

    @Override // com.android.wm.shell.freeform.FreeformTaskTransitionStarter
    public final IBinder startRemoveTransition(WindowContainerTransaction windowContainerTransaction) {
        IBinder iBinderStartTransition = this.mTransitions.startTransition(2, windowContainerTransaction, this);
        ((ArrayList) this.mPendingTransitionTokens).add(iBinderStartTransition);
        return iBinderStartTransition;
    }

    @Override // com.android.wm.shell.freeform.FreeformTaskTransitionStarter
    public final void startWindowingModeTransition(WindowContainerTransaction windowContainerTransaction, int i) {
        int i2;
        boolean z = CoreRune.MW_SHELL_CHANGE_TRANSITION;
        Transitions transitions = this.mTransitions;
        if (z) {
            transitions.mChangeTransitProvider.startChangeTransition(windowContainerTransaction);
            return;
        }
        if (i == 1) {
            i2 = EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS;
        } else {
            if (i != 5) {
                throw new IllegalArgumentException("Unexpected target windowing mode " + WindowConfiguration.windowingModeToString(i));
            }
            i2 = EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE;
        }
        ((ArrayList) this.mPendingTransitionTokens).add(transitions.startTransition(i2, windowContainerTransaction, this));
    }
}
