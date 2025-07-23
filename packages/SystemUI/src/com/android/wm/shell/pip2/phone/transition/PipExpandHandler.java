package com.android.wm.shell.pip2.phone.transition;

import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.pip2.animation.PipExpandAnimator;
import com.android.wm.shell.pip2.phone.PipInteractionHandler;
import com.android.wm.shell.pip2.phone.PipTransitionState;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.transition.Transitions;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipExpandHandler implements Transitions.TransitionHandler {
    public final Context mContext;
    public Transitions.TransitionFinishCallback mFinishCallback;
    public final PipBoundsAlgorithm mPipBoundsAlgorithm;
    public final PipBoundsState mPipBoundsState;
    public final PipDisplayLayoutState mPipDisplayLayoutState;
    public PipExpandAnimatorSupplier mPipExpandAnimatorSupplier = new PipExpandHandler$$ExternalSyntheticLambda0();
    public final PipInteractionHandler mPipInteractionHandler;
    public final PipTransitionState mPipTransitionState;
    public final Optional mSplitScreenControllerOptional;
    public PipExpandAnimator mTransitionAnimator;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    interface PipExpandAnimatorSupplier {
        PipExpandAnimator get(Context context, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Rect rect, Rect rect2, Rect rect3, Rect rect4, int i);
    }

    public PipExpandHandler(Context context, PipBoundsState pipBoundsState, PipBoundsAlgorithm pipBoundsAlgorithm, PipTransitionState pipTransitionState, PipDisplayLayoutState pipDisplayLayoutState, PipInteractionHandler pipInteractionHandler, Optional<SplitScreenController> optional) {
        this.mContext = context;
        this.mPipBoundsState = pipBoundsState;
        this.mPipBoundsAlgorithm = pipBoundsAlgorithm;
        this.mPipTransitionState = pipTransitionState;
        this.mPipDisplayLayoutState = pipDisplayLayoutState;
        this.mPipInteractionHandler = pipInteractionHandler;
        this.mSplitScreenControllerOptional = optional;
    }

    public final void finishTransition$1() {
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        if (pipTransitionState.mState != 7 && ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[4]) {
            ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 3464151303435924430L, 0, String.valueOf(pipTransitionState));
        }
        pipTransitionState.setState(8, null);
        Transitions.TransitionFinishCallback transitionFinishCallback = this.mFinishCallback;
        if (transitionFinishCallback != null) {
            this.mFinishCallback = null;
            transitionFinishCallback.onTransitionFinished(null);
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation() {
        PipExpandAnimator pipExpandAnimator = this.mTransitionAnimator;
        if (pipExpandAnimator == null || !pipExpandAnimator.isRunning()) {
            return;
        }
        this.mTransitionAnimator.end();
        this.mTransitionAnimator = null;
    }

    public final void saveReentryState$1() {
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        pipBoundsState.saveReentryState(this.mPipBoundsAlgorithm.getSnapFraction(pipBoundsState.getBounds()));
    }

    public void setPipExpandAnimatorSupplier(PipExpandAnimatorSupplier pipExpandAnimatorSupplier) {
        this.mPipExpandAnimatorSupplier = pipExpandAnimatorSupplier;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, final SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        int type = transitionInfo.getType();
        TransitionInfo.Change change = null;
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        if (type != 1001) {
            if (type != 1002) {
                return false;
            }
            WindowContainerToken pipTaskToken = pipTransitionState.getPipTaskToken();
            Iterator it = transitionInfo.getChanges().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                TransitionInfo.Change change2 = (TransitionInfo.Change) it.next();
                if (change2.getTaskInfo() == null && change2.getLastParent() != null && change2.getLastParent().equals(pipTaskToken)) {
                    change = change2;
                    break;
                }
            }
            if (change == null || change.getLeash() == null) {
                return false;
            }
            this.mFinishCallback = transitionFinishCallback;
            final TransitionInfo.Change changeByToken = PipTransitionUtils.getChangeByToken(transitionInfo, change.getParent());
            Rect startAbsBounds = change.getStartAbsBounds();
            Rect endAbsBounds = change.getEndAbsBounds();
            if (changeByToken != null) {
                startAbsBounds.offset(-changeByToken.getStartAbsBounds().left, -changeByToken.getStartAbsBounds().top);
                endAbsBounds.offset(-changeByToken.getEndAbsBounds().left, -changeByToken.getEndAbsBounds().top);
            }
            final SurfaceControl leash = change.getLeash();
            PipExpandAnimator pipExpandAnimator = this.mPipExpandAnimatorSupplier.get(this.mContext, leash, transaction, transaction2, endAbsBounds, startAbsBounds, endAbsBounds, null, 0);
            this.mSplitScreenControllerOptional.ifPresent(new Consumer() { // from class: com.android.wm.shell.pip2.phone.transition.PipExpandHandler$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((SplitScreenController) obj).finishEnterSplitScreen(transaction2);
                }
            });
            final int i = 0;
            pipExpandAnimator.mAnimationStartCallback = new Runnable(this) { // from class: com.android.wm.shell.pip2.phone.transition.PipExpandHandler$$ExternalSyntheticLambda2
                public final /* synthetic */ PipExpandHandler f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i) {
                        case 0:
                            PipExpandHandler pipExpandHandler = this.f$0;
                            SurfaceControl surfaceControl = leash;
                            PipInteractionHandler pipInteractionHandler = pipExpandHandler.mPipInteractionHandler;
                            pipInteractionHandler.mInteractionJankMonitor.begin(surfaceControl, pipInteractionHandler.mContext, pipInteractionHandler.mHandler, 35, "EXIT_PIP_TO_SPLIT");
                            break;
                        default:
                            PipExpandHandler pipExpandHandler2 = this.f$0;
                            SurfaceControl surfaceControl2 = leash;
                            PipInteractionHandler pipInteractionHandler2 = pipExpandHandler2.mPipInteractionHandler;
                            pipInteractionHandler2.mInteractionJankMonitor.begin(surfaceControl2, pipInteractionHandler2.mContext, pipInteractionHandler2.mHandler, 35, "EXIT_PIP");
                            break;
                    }
                }
            };
            final int i2 = 0;
            pipExpandAnimator.mAnimationEndCallback = new Runnable(this) { // from class: com.android.wm.shell.pip2.phone.transition.PipExpandHandler$$ExternalSyntheticLambda3
                public final /* synthetic */ PipExpandHandler f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i2) {
                        case 0:
                            PipExpandHandler pipExpandHandler = this.f$0;
                            TransitionInfo.Change change3 = changeByToken;
                            SurfaceControl.Transaction transaction3 = transaction2;
                            SurfaceControl surfaceControl = leash;
                            if (change3 == null) {
                                pipExpandHandler.getClass();
                                transaction3.setPosition(surfaceControl, 0.0f, 0.0f);
                            }
                            pipExpandHandler.finishTransition$1();
                            pipExpandHandler.mPipInteractionHandler.mInteractionJankMonitor.end(35);
                            break;
                        default:
                            PipExpandHandler pipExpandHandler2 = this.f$0;
                            TransitionInfo.Change change4 = changeByToken;
                            SurfaceControl.Transaction transaction4 = transaction2;
                            SurfaceControl surfaceControl2 = leash;
                            if (change4 != null) {
                                pipExpandHandler2.getClass();
                                transaction4.setCrop(surfaceControl2, null);
                            }
                            pipExpandHandler2.finishTransition$1();
                            pipExpandHandler2.mPipInteractionHandler.mInteractionJankMonitor.end(35);
                            break;
                    }
                }
            };
            this.mTransitionAnimator = pipExpandAnimator;
            pipExpandAnimator.start();
            saveReentryState$1();
            return true;
        }
        WindowContainerToken pipTaskToken2 = pipTransitionState.getPipTaskToken();
        TransitionInfo.Change changeByToken2 = PipTransitionUtils.getChangeByToken(transitionInfo, pipTaskToken2);
        if (changeByToken2 == null) {
            Iterator it2 = transitionInfo.getChanges().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                TransitionInfo.Change change3 = (TransitionInfo.Change) it2.next();
                if (change3.getTaskInfo() == null && change3.getLastParent() != null && change3.getLastParent().equals(pipTaskToken2)) {
                    changeByToken2 = change3;
                    break;
                }
            }
            if (changeByToken2 == null) {
                return false;
            }
        }
        this.mFinishCallback = transitionFinishCallback;
        final TransitionInfo.Change changeByToken3 = changeByToken2.getTaskInfo() == null ? PipTransitionUtils.getChangeByToken(transitionInfo, changeByToken2.getParent()) : null;
        if (changeByToken3 != null) {
            transaction.setLayer(changeByToken3.getLeash(), 2147483646);
        }
        Rect startAbsBounds2 = changeByToken2.getStartAbsBounds();
        Rect endAbsBounds2 = changeByToken2.getEndAbsBounds();
        final SurfaceControl leash2 = PipTransitionUtils.getLeash(changeByToken2);
        Rect validSourceHintRect = PipBoundsAlgorithm.getValidSourceHintRect(changeByToken2.getTaskInfo() != null ? PipTransitionUtils.getPipParams(changeByToken2) : (changeByToken3 == null || changeByToken3.getTaskInfo() == null) ? null : PipTransitionUtils.getPipParams(changeByToken3), endAbsBounds2);
        Rect rect = !PipBoundsAlgorithm.isSourceRectHintValidForEnterPip(validSourceHintRect, startAbsBounds2) ? null : validSourceHintRect;
        int i3 = -PipTransitionUtils.getFixedRotationDelta(transitionInfo, changeByToken2, this.mPipDisplayLayoutState);
        if (i3 != 0) {
            Rect endAbsBounds3 = changeByToken2.getEndAbsBounds();
            int width = endAbsBounds3.width();
            int height = endAbsBounds3.height();
            int i4 = endAbsBounds3.left;
            int i5 = endAbsBounds3.top;
            if (i3 == 1) {
                i4 = -(i4 + width);
            } else {
                i5 = -(i5 + height);
            }
            endAbsBounds3.set(i5, i4, height + i5, width + i4);
        }
        PipExpandAnimator pipExpandAnimator2 = this.mPipExpandAnimatorSupplier.get(this.mContext, leash2, transaction, transaction2, endAbsBounds2, startAbsBounds2, endAbsBounds2, rect, i3);
        final int i6 = 1;
        pipExpandAnimator2.mAnimationStartCallback = new Runnable(this) { // from class: com.android.wm.shell.pip2.phone.transition.PipExpandHandler$$ExternalSyntheticLambda2
            public final /* synthetic */ PipExpandHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i6) {
                    case 0:
                        PipExpandHandler pipExpandHandler = this.f$0;
                        SurfaceControl surfaceControl = leash2;
                        PipInteractionHandler pipInteractionHandler = pipExpandHandler.mPipInteractionHandler;
                        pipInteractionHandler.mInteractionJankMonitor.begin(surfaceControl, pipInteractionHandler.mContext, pipInteractionHandler.mHandler, 35, "EXIT_PIP_TO_SPLIT");
                        break;
                    default:
                        PipExpandHandler pipExpandHandler2 = this.f$0;
                        SurfaceControl surfaceControl2 = leash2;
                        PipInteractionHandler pipInteractionHandler2 = pipExpandHandler2.mPipInteractionHandler;
                        pipInteractionHandler2.mInteractionJankMonitor.begin(surfaceControl2, pipInteractionHandler2.mContext, pipInteractionHandler2.mHandler, 35, "EXIT_PIP");
                        break;
                }
            }
        };
        final int i7 = 1;
        pipExpandAnimator2.mAnimationEndCallback = new Runnable(this) { // from class: com.android.wm.shell.pip2.phone.transition.PipExpandHandler$$ExternalSyntheticLambda3
            public final /* synthetic */ PipExpandHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i7) {
                    case 0:
                        PipExpandHandler pipExpandHandler = this.f$0;
                        TransitionInfo.Change change32 = changeByToken3;
                        SurfaceControl.Transaction transaction3 = transaction2;
                        SurfaceControl surfaceControl = leash2;
                        if (change32 == null) {
                            pipExpandHandler.getClass();
                            transaction3.setPosition(surfaceControl, 0.0f, 0.0f);
                        }
                        pipExpandHandler.finishTransition$1();
                        pipExpandHandler.mPipInteractionHandler.mInteractionJankMonitor.end(35);
                        break;
                    default:
                        PipExpandHandler pipExpandHandler2 = this.f$0;
                        TransitionInfo.Change change4 = changeByToken3;
                        SurfaceControl.Transaction transaction4 = transaction2;
                        SurfaceControl surfaceControl2 = leash2;
                        if (change4 != null) {
                            pipExpandHandler2.getClass();
                            transaction4.setCrop(surfaceControl2, null);
                        }
                        pipExpandHandler2.finishTransition$1();
                        pipExpandHandler2.mPipInteractionHandler.mInteractionJankMonitor.end(35);
                        break;
                }
            }
        };
        this.mTransitionAnimator = pipExpandAnimator2;
        pipExpandAnimator2.start();
        saveReentryState$1();
        return true;
    }
}
