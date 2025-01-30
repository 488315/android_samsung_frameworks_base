package com.android.wm.shell.pip;

import android.app.TaskInfo;
import android.graphics.Rect;
import android.os.IBinder;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class PipTransitionController implements Transitions.TransitionHandler {
    public boolean mDeferBoundsTransaction;
    public final PipAnimationController mPipAnimationController;
    public final PipBoundsAlgorithm mPipBoundsAlgorithm;
    public final PipBoundsState mPipBoundsState;
    public final PipMenuController mPipMenuController;
    public PipTaskOrganizer mPipOrganizer;
    public final ShellTaskOrganizer mShellTaskOrganizer;
    public final Transitions mTransitions;
    public final List mPipTransitionCallbacks = new ArrayList();
    public final C40471 mPipAnimationCallback = new PipAnimationController.PipAnimationCallback() { // from class: com.android.wm.shell.pip.PipTransitionController.1
        @Override // com.android.wm.shell.pip.PipAnimationController.PipAnimationCallback
        public final void onPipAnimationCancel(TaskInfo taskInfo, PipAnimationController.PipTransitionAnimator pipTransitionAnimator) {
            int transitionDirection = pipTransitionAnimator.getTransitionDirection();
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("[PipTransitionController] onPipAnimationCancel: direction=", transitionDirection, ", state=");
            PipTransitionController pipTransitionController = PipTransitionController.this;
            m1m.append(pipTransitionController.mPipOrganizer.mPipTransitionState);
            Log.d("PipTaskOrganizer", m1m.toString());
            if (PipAnimationController.isInPipDirection(transitionDirection) && pipTransitionAnimator.getContentOverlayLeash() != null) {
                PipTaskOrganizer pipTaskOrganizer = pipTransitionController.mPipOrganizer;
                int i = 1;
                if (pipTaskOrganizer.mPipTransitionState.mState == 5) {
                    Log.e("PipTaskOrganizer", "[PipTransitionController] onPipAnimationCancel: clearContentOverlay immediately, reason=exiting_pip");
                    pipTransitionController.mPipOrganizer.removeContentOverlay(pipTransitionAnimator.getContentOverlayLeash(), new PipTransitionController$$ExternalSyntheticLambda0(pipTransitionAnimator, i));
                } else {
                    pipTaskOrganizer.fadeOutAndRemoveOverlay(pipTransitionAnimator.getContentOverlayLeash(), new PipTransitionController$$ExternalSyntheticLambda0(pipTransitionAnimator, 2), true, -1);
                }
            }
            if (CoreRune.MW_PIP_SHELL_TRANSITION && PipAnimationController.isInPipDirection(transitionDirection)) {
                Log.d("PipTaskOrganizer", "[PipTransitionController] onPipAnimationCancel, ensure onFinishResize if entering");
                pipTransitionController.onFinishResize(taskInfo, pipTransitionAnimator.mDestinationBounds, transitionDirection, new SurfaceControl.Transaction());
            }
            pipTransitionController.sendOnPipTransitionCancelled(pipTransitionAnimator.getTransitionDirection());
        }

        @Override // com.android.wm.shell.pip.PipAnimationController.PipAnimationCallback
        public final void onPipAnimationEnd(TaskInfo taskInfo, SurfaceControl.Transaction transaction, PipAnimationController.PipTransitionAnimator pipTransitionAnimator) {
            int transitionDirection = pipTransitionAnimator.getTransitionDirection();
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("[PipTransitionController] onPipAnimationEnd direction=", transitionDirection, " destination");
            m1m.append(pipTransitionAnimator.mDestinationBounds);
            m1m.append(" mState=");
            PipTransitionController pipTransitionController = PipTransitionController.this;
            RecyclerView$$ExternalSyntheticOutline0.m46m(m1m, pipTransitionController.mPipOrganizer.mPipTransitionState.mState, "PipTaskOrganizer");
            pipTransitionController.mPipBoundsState.setBounds(pipTransitionAnimator.mDestinationBounds);
            if (transitionDirection == 5) {
                return;
            }
            if (PipAnimationController.isInPipDirection(transitionDirection) && pipTransitionAnimator.getContentOverlayLeash() != null) {
                pipTransitionController.mPipOrganizer.fadeOutAndRemoveOverlay(pipTransitionAnimator.getContentOverlayLeash(), new PipTransitionController$$ExternalSyntheticLambda0(pipTransitionAnimator, 3), true, -1);
            }
            pipTransitionController.onFinishResize(taskInfo, pipTransitionAnimator.mDestinationBounds, transitionDirection, transaction);
            pipTransitionController.sendOnPipTransitionFinished(transitionDirection);
        }

        @Override // com.android.wm.shell.pip.PipAnimationController.PipAnimationCallback
        public final void onPipAnimationStart(PipAnimationController.PipTransitionAnimator pipTransitionAnimator) {
            int transitionDirection = pipTransitionAnimator.getTransitionDirection();
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("[PipTransitionController] onPipAnimationStart direction=", transitionDirection, "PipTaskOrganizer");
            PipTransitionController.this.sendOnPipTransitionStarted(transitionDirection);
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface PipTransitionCallback {
        void onPipTransitionCanceled(int i);

        void onPipTransitionFinished(int i);

        void onPipTransitionStarted(int i, Rect rect);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.pip.PipTransitionController$1] */
    public PipTransitionController(ShellInit shellInit, ShellTaskOrganizer shellTaskOrganizer, Transitions transitions, PipBoundsState pipBoundsState, PipMenuController pipMenuController, PipBoundsAlgorithm pipBoundsAlgorithm, PipAnimationController pipAnimationController) {
        this.mPipBoundsState = pipBoundsState;
        this.mPipMenuController = pipMenuController;
        this.mShellTaskOrganizer = shellTaskOrganizer;
        this.mPipBoundsAlgorithm = pipBoundsAlgorithm;
        this.mPipAnimationController = pipAnimationController;
        this.mTransitions = transitions;
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            shellInit.addInitCallback(new PipTransitionController$$ExternalSyntheticLambda0(this, 0), this);
        }
    }

    public void augmentRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo, WindowContainerTransaction windowContainerTransaction) {
        throw new IllegalStateException("Request isn't entering PiP");
    }

    public boolean handleRotateDisplay(int i, int i2, WindowContainerTransaction windowContainerTransaction) {
        return false;
    }

    public boolean isEnteringPip(int i, TransitionInfo.Change change) {
        return false;
    }

    public abstract void onStartEnterPipFromSplit(TransitionInfo.Change change);

    public final void sendOnPipTransitionCancelled(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("sendOnPipTransitionCancelled direction=", i, "PipTaskOrganizer");
        List list = this.mPipTransitionCallbacks;
        int size = ((ArrayList) list).size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            } else {
                ((PipTransitionCallback) ((ArrayList) list).get(size)).onPipTransitionCanceled(i);
            }
        }
    }

    public final void sendOnPipTransitionFinished(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("[PipTransitionController] sendOnPipTransitionFinished direction=", i, "PipTaskOrganizer");
        List list = this.mPipTransitionCallbacks;
        int size = ((ArrayList) list).size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            } else {
                ((PipTransitionCallback) ((ArrayList) list).get(size)).onPipTransitionFinished(i);
            }
        }
    }

    public final void sendOnPipTransitionStarted(int i) {
        Rect bounds = this.mPipBoundsState.getBounds();
        Log.d("PipTaskOrganizer", "[PipTransitionController] sendOnPipTransitionStarted direction=" + i + " pipBounds=" + bounds);
        List list = this.mPipTransitionCallbacks;
        for (int size = ((ArrayList) list).size() + (-1); size >= 0; size--) {
            ((PipTransitionCallback) ((ArrayList) list).get(size)).onPipTransitionStarted(i, bounds);
        }
    }

    public void forceFinishTransition(PipTaskOrganizer$$ExternalSyntheticLambda3 pipTaskOrganizer$$ExternalSyntheticLambda3) {
    }

    public void setEnterAnimationType(int i) {
    }

    public void end() {
    }

    public void onFixedRotationFinished() {
    }

    public void onFixedRotationStarted() {
    }

    public void dump(PrintWriter printWriter, String str) {
    }

    public void startExitTransition(int i, WindowContainerTransaction windowContainerTransaction, Rect rect) {
    }

    public void syncPipSurfaceState(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
    }

    public void onFinishResize(TaskInfo taskInfo, Rect rect, int i, SurfaceControl.Transaction transaction) {
    }

    public void startEnterAnimation(TransitionInfo.Change change, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
    }
}
