package com.android.wm.shell.pip;

import android.app.ActivityTaskManager;
import android.app.PictureInPictureUiState;
import android.app.TaskInfo;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipMenuController;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.DefaultMixedHandler;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class PipTransitionController implements Transitions.TransitionHandler {
    public DefaultMixedHandler mMixedHandler;
    public final PipBoundsAlgorithm mPipBoundsAlgorithm;
    public final PipBoundsState mPipBoundsState;
    public final PipMenuController mPipMenuController;
    public PipTaskOrganizer mPipOrganizer;
    public final ShellTaskOrganizer mShellTaskOrganizer;
    public final Transitions mTransitions;
    public final Map mPipTransitionCallbacks = new HashMap();
    public final AnonymousClass1 mPipAnimationCallback = new PipAnimationController.PipAnimationCallback() { // from class: com.android.wm.shell.pip.PipTransitionController.1
        @Override // com.android.wm.shell.pip.PipAnimationController.PipAnimationCallback
        public final void onPipAnimationCancel(TaskInfo taskInfo, PipAnimationController.PipTransitionAnimator pipTransitionAnimator) {
            PipTaskOrganizer pipTaskOrganizer;
            SurfaceControl surfaceControl;
            int transitionDirection = pipTransitionAnimator.getTransitionDirection();
            int i = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(transitionDirection, "[PipTransitionController] onPipAnimationCancel: direction=", ", state=");
            PipTransitionController pipTransitionController = PipTransitionController.this;
            m.append(pipTransitionController.mPipOrganizer.mPipTransitionState);
            Log.d("PipTaskOrganizer", m.toString());
            if (PipAnimationController.isInPipDirection(transitionDirection) && (surfaceControl = (pipTaskOrganizer = pipTransitionController.mPipOrganizer).mPipOverlay) != null) {
                if (pipTaskOrganizer.mPipTransitionState.mState == 5) {
                    Log.e("PipTaskOrganizer", "[PipTransitionController] onPipAnimationCancel: clearContentOverlay immediately, reason=exiting_pip");
                    PipTaskOrganizer pipTaskOrganizer2 = pipTransitionController.mPipOrganizer;
                    pipTaskOrganizer2.fadeOutAndRemoveOverlay(pipTaskOrganizer2.mPipOverlay, true, -1);
                } else {
                    pipTaskOrganizer.fadeOutAndRemoveOverlay(surfaceControl, true, -1);
                }
            }
            if (CoreRune.MW_PIP_SHELL_TRANSITION && PipAnimationController.isInPipDirection(transitionDirection)) {
                Log.d("PipTaskOrganizer", "[PipTransitionController] onPipAnimationCancel, ensure onFinishResize if entering");
                PipTransitionController.this.onFinishResize(taskInfo, pipTransitionAnimator.mDestinationBounds, new Point(pipTransitionAnimator.mLeashOffset), transitionDirection, new SurfaceControl.Transaction());
            }
            pipTransitionController.sendOnPipTransitionCancelled$1(pipTransitionAnimator.getTransitionDirection());
        }

        @Override // com.android.wm.shell.pip.PipAnimationController.PipAnimationCallback
        public final void onPipAnimationEnd(TaskInfo taskInfo, SurfaceControl.Transaction transaction, PipAnimationController.PipTransitionAnimator pipTransitionAnimator) {
            PipTaskOrganizer pipTaskOrganizer;
            SurfaceControl surfaceControl;
            int transitionDirection = pipTransitionAnimator.getTransitionDirection();
            int i = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(transitionDirection, "[PipTransitionController] onPipAnimationEnd direction=", " destination");
            m.append(pipTransitionAnimator.mDestinationBounds);
            m.append(" mState=");
            PipTransitionController pipTransitionController = PipTransitionController.this;
            RecyclerView$$ExternalSyntheticOutline0.m(pipTransitionController.mPipOrganizer.mPipTransitionState.mState, "PipTaskOrganizer", m);
            pipTransitionController.mPipBoundsState.setBounds(pipTransitionAnimator.mDestinationBounds);
            if (transitionDirection == 5) {
                return;
            }
            if (PipAnimationController.isInPipDirection(transitionDirection) && (surfaceControl = (pipTaskOrganizer = pipTransitionController.mPipOrganizer).mPipOverlay) != null) {
                pipTaskOrganizer.fadeOutAndRemoveOverlay(surfaceControl, true, -1);
            }
            PipTransitionController.this.onFinishResize(taskInfo, pipTransitionAnimator.mDestinationBounds, new Point(pipTransitionAnimator.mLeashOffset), transitionDirection, transaction);
            pipTransitionController.sendOnPipTransitionFinished(transitionDirection);
        }

        @Override // com.android.wm.shell.pip.PipAnimationController.PipAnimationCallback
        public final void onPipAnimationStart(PipAnimationController.PipTransitionAnimator pipTransitionAnimator) {
            int transitionDirection = pipTransitionAnimator.getTransitionDirection();
            int i = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
            ListPopupWindow$$ExternalSyntheticOutline0.m(transitionDirection, "[PipTransitionController] onPipAnimationStart direction=", "PipTaskOrganizer");
            PipTransitionController.this.sendOnPipTransitionStarted$1(transitionDirection);
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface PipTransitionCallback {
        void onPipTransitionCanceled(int i);

        void onPipTransitionFinished(int i);

        void onPipTransitionStarted(int i, Rect rect);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.pip.PipTransitionController$1] */
    public PipTransitionController(ShellInit shellInit, ShellTaskOrganizer shellTaskOrganizer, Transitions transitions, PipBoundsState pipBoundsState, PipMenuController pipMenuController, PipBoundsAlgorithm pipBoundsAlgorithm) {
        this.mPipBoundsState = pipBoundsState;
        this.mPipMenuController = pipMenuController;
        this.mShellTaskOrganizer = shellTaskOrganizer;
        this.mPipBoundsAlgorithm = pipBoundsAlgorithm;
        this.mTransitions = transitions;
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.pip.PipTransitionController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PipTransitionController.this.onInit();
                }
            }, this);
        }
    }

    public static TransitionInfo.Change findFixedRotationChange(TransitionInfo transitionInfo) {
        for (int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); m >= 0; m--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m);
            if (change.getEndFixedRotation() != -1) {
                return change;
            }
        }
        return null;
    }

    public void augmentRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo, WindowContainerTransaction windowContainerTransaction) {
        throw new IllegalStateException("Request isn't entering PiP");
    }

    public void end() {
    }

    public boolean handleRotateDisplay(int i, int i2, WindowContainerTransaction windowContainerTransaction) {
        return false;
    }

    public boolean isEnteringPip$1(TransitionInfo.Change change, int i) {
        return false;
    }

    public boolean isInSwipePipToHomeTransition() {
        return false;
    }

    public boolean isPackageActiveInPip(String str) {
        return false;
    }

    public void onInit() {
        this.mTransitions.addHandler(this);
    }

    public final void sendOnPipTransitionCancelled$1(int i) {
        int i2 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "sendOnPipTransitionCancelled direction=", "PipTaskOrganizer");
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -356640916490602654L, 1, Long.valueOf(i));
        }
        for (Map.Entry entry : ((HashMap) this.mPipTransitionCallbacks).entrySet()) {
            ((Executor) entry.getValue()).execute(new PipTransitionController$$ExternalSyntheticLambda1(entry, i, 1));
        }
    }

    public final void sendOnPipTransitionFinished(int i) {
        int i2 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "[PipTransitionController] sendOnPipTransitionFinished direction=", "PipTaskOrganizer");
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1875374233534254061L, 1, Long.valueOf(i));
        }
        for (Map.Entry entry : ((HashMap) this.mPipTransitionCallbacks).entrySet()) {
            ((Executor) entry.getValue()).execute(new PipTransitionController$$ExternalSyntheticLambda1(entry, i, 0));
        }
        if (PipAnimationController.isInPipDirection(i)) {
            try {
                ActivityTaskManager.getService().onPictureInPictureUiStateChanged(new PictureInPictureUiState.Builder().setTransitioningToPip(false).build());
            } catch (RemoteException | IllegalStateException unused) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[4]) {
                    ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -8189372673897383003L, 0, null);
                }
            }
        }
    }

    public final void sendOnPipTransitionStarted$1(final int i) {
        final Rect bounds = this.mPipBoundsState.getBounds();
        int i2 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
        Log.d("PipTaskOrganizer", "[PipTransitionController] sendOnPipTransitionStarted direction=" + i + " pipBounds=" + bounds);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -2546532866731920044L, 1, Long.valueOf(i), String.valueOf(bounds));
        }
        for (final Map.Entry entry : ((HashMap) this.mPipTransitionCallbacks).entrySet()) {
            ((Executor) entry.getValue()).execute(new Runnable() { // from class: com.android.wm.shell.pip.PipTransitionController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    Map.Entry entry2 = entry;
                    ((PipTransitionController.PipTransitionCallback) entry2.getKey()).onPipTransitionStarted(i, bounds);
                }
            });
        }
        if (!PipAnimationController.isInPipDirection(i) || isInSwipePipToHomeTransition()) {
            return;
        }
        try {
            ActivityTaskManager.getService().onPictureInPictureUiStateChanged(new PictureInPictureUiState.Builder().setTransitioningToPip(true).build());
        } catch (RemoteException | IllegalStateException unused) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[4]) {
                ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -8189372673897383003L, 0, null);
            }
        }
    }

    public void end(PipTaskOrganizer$$ExternalSyntheticLambda8 pipTaskOrganizer$$ExternalSyntheticLambda8) {
    }

    public void forceFinishTransition(PipTaskOrganizer$$ExternalSyntheticLambda5 pipTaskOrganizer$$ExternalSyntheticLambda5) {
    }

    public void setEnterAnimationType(int i) {
    }

    public void setStartTransactionForRemote(SurfaceControl.Transaction transaction) {
    }

    public void onFixedRotationFinished() {
    }

    public void onFixedRotationStarted() {
    }

    public void dump$2(PrintWriter printWriter, String str) {
    }

    public void onStartEnterPipFromSplit(TransitionInfo.Change change, TransitionInfo transitionInfo) {
    }

    public void startExitTransition(int i, WindowContainerTransaction windowContainerTransaction, Rect rect) {
    }

    public void syncPipSurfaceState(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
    }

    public void startEnterAnimation(TransitionInfo.Change change, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
    }

    public void onFinishResize(TaskInfo taskInfo, Rect rect, Point point, int i, SurfaceControl.Transaction transaction) {
    }
}
