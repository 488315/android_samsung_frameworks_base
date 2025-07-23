package com.android.wm.shell.pip2.phone;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.PictureInPictureParams;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.DesktopExperienceFlags;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLog;
import com.android.internal.util.Preconditions;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.ComponentUtils;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDesktopState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipMenuController;
import com.android.wm.shell.common.pip.PipSnapAlgorithm;
import com.android.wm.shell.common.pip.PipUtils;
import com.android.wm.shell.desktopmode.DesktopDisplayEventHandler$$ExternalSyntheticOutline0;
import com.android.wm.shell.desktopmode.DesktopPipTransitionController;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.DesktopTasksController$$ExternalSyntheticLambda5;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.pip2.PipSurfaceTransactionHelper;
import com.android.wm.shell.pip2.phone.PipTransitionState;
import com.android.wm.shell.pip2.phone.transition.PipExpandHandler;
import com.android.wm.shell.pip2.phone.transition.PipTransitionUtils;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipTransition extends PipTransitionController implements PipTransitionState.PipTransitionStateChangedListener {
    public int mBoundsChangeDuration;
    public final Context mContext;
    public final Optional mDesktopPipTransitionController;
    public int mEnterAnimationType;
    public IBinder mEnterTransition;
    public IBinder mExitViaExpandTransition;
    public final PipExpandHandler mExpandHandler;
    public Transitions.TransitionFinishCallback mFinishCallback;
    public boolean mPendingRemoveWithFadeout;
    public final PipDesktopState mPipDesktopState;
    public final PipDisplayLayoutState mPipDisplayLayoutState;
    public final PipScheduler mPipScheduler;
    public final PipSurfaceTransactionHelper mPipSurfaceTransactionHelper;
    public final PipTaskListener mPipTaskListener;
    public final PipTransitionState mPipTransitionState;
    public IBinder mResizeTransition;
    public ValueAnimator mTransitionAnimator;

    public PipTransition(Context context, ShellInit shellInit, ShellTaskOrganizer shellTaskOrganizer, Transitions transitions, PipBoundsState pipBoundsState, PipMenuController pipMenuController, PipBoundsAlgorithm pipBoundsAlgorithm, PipTaskListener pipTaskListener, PipScheduler pipScheduler, PipTransitionState pipTransitionState, PipDisplayLayoutState pipDisplayLayoutState, PipUiStateChangeController pipUiStateChangeController, DisplayController displayController, Optional<SplitScreenController> optional, PipDesktopState pipDesktopState, Optional<DesktopPipTransitionController> optional2, PipInteractionHandler pipInteractionHandler) {
        super(shellInit, shellTaskOrganizer, transitions, pipBoundsState, pipMenuController, pipBoundsAlgorithm);
        this.mBoundsChangeDuration = 0;
        this.mEnterAnimationType = 0;
        this.mContext = context;
        this.mPipTaskListener = pipTaskListener;
        this.mPipScheduler = pipScheduler;
        pipScheduler.mPipTransitionController = this;
        this.mPipTransitionState = pipTransitionState;
        pipTransitionState.addPipTransitionStateChangedListener(this);
        this.mPipDisplayLayoutState = pipDisplayLayoutState;
        this.mPipSurfaceTransactionHelper = new PipSurfaceTransactionHelper(context);
        this.mPipDesktopState = pipDesktopState;
        this.mDesktopPipTransitionController = optional2;
        this.mExpandHandler = new PipExpandHandler(context, pipBoundsState, pipBoundsAlgorithm, pipTransitionState, pipDisplayLayoutState, pipInteractionHandler, optional);
    }

    public static void prepareConfigAtEndActivity(SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, TransitionInfo.Change change, TransitionInfo.Change change2) {
        PictureInPictureParams pictureInPictureParams;
        PointF pointF = new PointF();
        PointF pointF2 = new PointF();
        PipUtils pipUtils = PipUtils.INSTANCE;
        Rect startAbsBounds = change2.getStartAbsBounds();
        Rect endAbsBounds = change2.getEndAbsBounds();
        Rect endAbsBounds2 = change.getEndAbsBounds();
        ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
        Rect sourceRectHint = (taskInfo == null || (pictureInPictureParams = taskInfo.pictureInPictureParams) == null) ? null : pictureInPictureParams.getSourceRectHint();
        if (sourceRectHint == null) {
            sourceRectHint = new Rect(startAbsBounds);
            sourceRectHint.offsetTo(0, 0);
        }
        float width = endAbsBounds.width() / sourceRectHint.width();
        float height = endAbsBounds.height() / sourceRectHint.height();
        pointF.set(width, height);
        pointF2.set((endAbsBounds.left - endAbsBounds2.left) - (sourceRectHint.left * width), (endAbsBounds.top - endAbsBounds2.top) - (sourceRectHint.top * height));
        if (change2.getLeash() != null) {
            transaction.setCrop(change2.getLeash(), null);
            transaction.setScale(change2.getLeash(), pointF.x, pointF.y);
            transaction.setPosition(change2.getLeash(), pointF2.x, pointF2.y);
            transaction2.setCrop(change2.getLeash(), null);
            transaction2.setScale(change2.getLeash(), pointF.x, pointF.y);
            transaction2.setPosition(change2.getLeash(), pointF2.x, pointF2.y);
        }
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void augmentRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo, WindowContainerTransaction windowContainerTransaction) {
        if (isAutoEnterInButtonNavigation(transitionRequestInfo) || transitionRequestInfo.getType() == 10) {
            windowContainerTransaction.merge(getEnterPipTransaction(transitionRequestInfo.getPipChange()), true);
            this.mEnterTransition = iBinder;
        }
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void end() {
        ValueAnimator valueAnimator = this.mTransitionAnimator;
        if (valueAnimator == null || !valueAnimator.isRunning()) {
            return;
        }
        this.mTransitionAnimator.end();
        this.mTransitionAnimator = null;
    }

    public final void finishTransition() {
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        int i = pipTransitionState.mState;
        pipTransitionState.setState(i != 2 ? i != 5 ? i != 7 ? 0 : 8 : 6 : 3, null);
        Transitions.TransitionFinishCallback transitionFinishCallback = this.mFinishCallback;
        if (transitionFinishCallback != null) {
            this.mFinishCallback = null;
            transitionFinishCallback.onTransitionFinished(null);
        }
    }

    public final Rect getAdjustedSourceRectHint(TransitionInfo transitionInfo, TransitionInfo.Change change, TransitionInfo.Change change2) {
        float f;
        Rect startAbsBounds = change.getStartAbsBounds();
        Rect endAbsBounds = change.getEndAbsBounds();
        PictureInPictureParams pictureInPictureParams = change.getTaskInfo().pictureInPictureParams;
        Rect validSourceHintRect = PipBoundsAlgorithm.getValidSourceHintRect(pictureInPictureParams, startAbsBounds);
        if (!PipBoundsAlgorithm.isSourceRectHintValidForEnterPip(validSourceHintRect, endAbsBounds)) {
            validSourceHintRect = null;
        }
        Rect rect = new Rect();
        if (validSourceHintRect != null) {
            rect.set(validSourceHintRect);
            TransitionInfo.Change changeByToken = change2.getLastParent() != null ? PipTransitionUtils.getChangeByToken(transitionInfo, change2.getLastParent()) : null;
            Rect rect2 = changeByToken != null ? changeByToken.getTaskInfo().displayCutoutInsets : change.getTaskInfo().displayCutoutInsets;
            if (rect2 != null && PipTransitionUtils.getFixedRotationDelta(transitionInfo, change, this.mPipDisplayLayoutState) == 1) {
                rect.offset(rect2.left, rect2.top);
            }
            if (this.mPipDesktopState.isDesktopWindowingPipEnabled()) {
                rect.offset(-change2.getStartAbsBounds().left, -change2.getStartAbsBounds().top);
            }
            return rect;
        }
        PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
        if (pictureInPictureParams != null) {
            pipBoundsAlgorithm.getClass();
            if (pictureInPictureParams.hasSetAspectRatio()) {
                f = pictureInPictureParams.getAspectRatioFloat();
                rect.set(PipUtils.getEnterPipWithOverlaySrcRectHint(startAbsBounds, f));
                return rect;
            }
        }
        f = pipBoundsAlgorithm.mDefaultAspectRatio;
        rect.set(PipUtils.getEnterPipWithOverlaySrcRectHint(startAbsBounds, f));
        return rect;
    }

    public final WindowContainerTransaction getEnterPipTransaction(TransitionRequestInfo.PipChange pipChange) {
        ActivityManager.RunningTaskInfo taskInfo = pipChange.getTaskInfo();
        PictureInPictureParams pictureInPictureParams = taskInfo.pictureInPictureParams;
        this.mPipTaskListener.setPictureInPictureParams(pictureInPictureParams);
        ComponentName componentName = taskInfo.topActivity;
        ActivityInfo activityInfo = taskInfo.topActivityInfo;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
        pipBoundsState.setBoundsStateForEntry(componentName, activityInfo, pictureInPictureParams, pipBoundsAlgorithm);
        this.mPipDesktopState.getClass();
        DesktopExperienceFlags.ENABLE_CONNECTED_DISPLAYS_PIP.isTrue();
        if (!this.mPipTransitionState.mInSwipePipToHomeTransition) {
            pipBoundsState.updateMinMaxSize(pipBoundsState.mAspectRatio);
        }
        Rect entryDestinationBounds = pipBoundsAlgorithm.getEntryDestinationBounds();
        pipBoundsState.setBounds(entryDestinationBounds);
        WindowContainerToken taskFragmentToken = pipChange.getTaskFragmentToken();
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.movePipActivityToPinnedRootTask(taskFragmentToken, entryDestinationBounds);
        windowContainerTransaction.deferConfigToTransitionEnd(taskFragmentToken);
        return windowContainerTransaction;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(final IBinder iBinder, final TransitionRequestInfo transitionRequestInfo) {
        if (!isAutoEnterInButtonNavigation(transitionRequestInfo) && transitionRequestInfo.getType() != 10) {
            return null;
        }
        this.mEnterTransition = iBinder;
        final WindowContainerTransaction enterPipTransaction = getEnterPipTransaction(transitionRequestInfo.getPipChange());
        this.mDesktopPipTransitionController.ifPresent(new Consumer() { // from class: com.android.wm.shell.pip2.phone.PipTransition$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int intValue;
                WindowContainerTransaction windowContainerTransaction = enterPipTransaction;
                IBinder iBinder2 = iBinder;
                DesktopPipTransitionController desktopPipTransitionController = (DesktopPipTransitionController) obj;
                ActivityManager.RunningTaskInfo taskInfo = transitionRequestInfo.getPipChange().getTaskInfo();
                if (desktopPipTransitionController.pipDesktopState.isDesktopWindowingPipEnabled()) {
                    if (Intrinsics.areEqual(iBinder2, DesktopTasksController.SYNTHETIC_TRANSITION)) {
                        DesktopPipTransitionController.logD("handlePipTransitionIfInDesktop: SYNTHETIC_TRANSITION, not a true transition", new Object[0]);
                        return;
                    }
                    int i = taskInfo.taskId;
                    int i2 = taskInfo.displayId;
                    DesktopRepository profile = desktopPipTransitionController.desktopUserRepositories.getProfile(taskInfo.userId);
                    if (!profile.isAnyDeskActive(i2)) {
                        DesktopPipTransitionController.logD("handlePipTransitionIfInDesktop: PiP transition is not in Desktop session", new Object[0]);
                        return;
                    }
                    Integer activeDeskId = profile.getActiveDeskId(i2);
                    if (activeDeskId != null) {
                        intValue = activeDeskId.intValue();
                    } else {
                        if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                            Object[] objArr = {Integer.valueOf(i2)};
                            ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
                            SpreadBuilder m = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopPipTransitionController", objArr);
                            ProtoLog.w(shellProtoLogGroup, "%s: handlePipTransitionIfInDesktop: Active desk not found for display id %d", m.list.toArray(new Object[m.list.size()]));
                            return;
                        }
                        Integer defaultDeskId = profile.getDefaultDeskId(i2);
                        if (defaultDeskId == null) {
                            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "DesktopPipTransitionController: handlePipTransitionIfInDesktop: Expected a default desk to exist in display with id ").toString());
                        }
                        intValue = defaultDeskId.intValue();
                    }
                    if (!profile.isOnlyVisibleNonClosingTaskInDesk(i, intValue)) {
                        DesktopPipTransitionController.logD("handlePipTransitionIfInDesktop: PiP task is not last visible task in Desk", new Object[0]);
                        return;
                    }
                    DesktopTasksController$$ExternalSyntheticLambda5 performDesktopExitCleanUp$default = DesktopTasksController.performDesktopExitCleanUp$default(desktopPipTransitionController.desktopTasksController, windowContainerTransaction, Integer.valueOf(intValue), i2, true, false, 48);
                    if (performDesktopExitCleanUp$default != null) {
                        performDesktopExitCleanUp$default.mo779invoke(iBinder2);
                    }
                }
            }
        });
        return enterPipTransaction;
    }

    public final boolean isAutoEnterInButtonNavigation(TransitionRequestInfo transitionRequestInfo) {
        ActivityManager.RunningTaskInfo taskInfo = transitionRequestInfo.getPipChange() != null ? transitionRequestInfo.getPipChange().getTaskInfo() : null;
        return (taskInfo == null || taskInfo.pictureInPictureParams == null || this.mPipDesktopState.isPipInDesktopMode() || transitionRequestInfo.getType() != 1 || !taskInfo.pictureInPictureParams.isAutoEnterEnabled()) ? false : true;
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final boolean isEnteringPip$1(TransitionInfo.Change change, int i) {
        if (change.getTaskInfo() == null || change.getTaskInfo().getWindowingMode() != 2) {
            return false;
        }
        if (i == 10 || i == 1 || i == 3 || i == 6) {
            return true;
        }
        Slog.e("PipTransition", "Found new PIP in transition with mis-matched type=" + Transitions.transitTypeToString(i), new Throwable());
        return false;
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final boolean isInSwipePipToHomeTransition() {
        return this.mPipTransitionState.mInSwipePipToHomeTransition;
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final boolean isPackageActiveInPip(String str) {
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        TaskInfo taskInfo = pipTransitionState.mPipTaskInfo;
        return str != null && taskInfo != null && pipTransitionState.isInPip() && str.equals(ComponentUtils.getPackageName(taskInfo.baseIntent));
    }

    public final boolean isPipClosing(TransitionInfo transitionInfo) {
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        if (pipTransitionState.getPipTaskToken() != null) {
            TransitionInfo.Change change = transitionInfo.getChange(pipTransitionState.getPipTaskToken());
            TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().stream().filter(new Predicate() { // from class: com.android.wm.shell.pip2.phone.PipTransition$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    PipTransition pipTransition = PipTransition.this;
                    TransitionInfo.Change change3 = (TransitionInfo.Change) obj;
                    pipTransition.getClass();
                    return change3.getTaskInfo() == null && change3.getParent() != null && change3.getParent() == pipTransition.mPipTransitionState.getPipTaskToken();
                }
            }).findFirst().orElse(null);
            boolean z = change != null && change.getMode() == 2;
            boolean z2 = change2 != null && change2.getMode() == 2;
            if (z || z2) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        if (transitionInfo.getType() == 1001) {
            end();
        }
        this.mExpandHandler.mergeAnimation();
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void onInit() {
        if (PipUtils.isPip2ExperimentEnabled()) {
            this.mTransitions.addHandler(this);
        }
    }

    @Override // com.android.wm.shell.pip2.phone.PipTransitionState.PipTransitionStateChangedListener
    public final void onPipTransitionStateChanged(int i, int i2, Bundle bundle) {
        boolean z = false;
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        if (i2 != 2) {
            if (i2 != 8) {
                return;
            }
            pipTransitionState.mPinnedTaskLeash = null;
            pipTransitionState.mPipTaskInfo = null;
            this.mPendingRemoveWithFadeout = false;
            return;
        }
        Preconditions.checkState(bundle != null, "No extra bundle for " + pipTransitionState);
        pipTransitionState.mPinnedTaskLeash = (SurfaceControl) bundle.getParcelable("pip_task_leash", SurfaceControl.class);
        pipTransitionState.mPipTaskInfo = (TaskInfo) bundle.getParcelable("pip_task_info", TaskInfo.class);
        if (pipTransitionState.getPipTaskToken() != null && pipTransitionState.mPinnedTaskLeash != null) {
            z = true;
        }
        Preconditions.checkState(z, "Unexpected bundle for " + pipTransitionState);
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void setEnterAnimationType(int i) {
        this.mEnterAnimationType = i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:113:0x0275, code lost:
    
        if (com.android.wm.shell.pip2.phone.transition.PipTransitionUtils.getDeferConfigActivityChange(r24, r12.getContainer()) == null) goto L100;
     */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean startAnimation(android.os.IBinder r23, android.window.TransitionInfo r24, android.view.SurfaceControl.Transaction r25, android.view.SurfaceControl.Transaction r26, com.android.wm.shell.transition.Transitions.TransitionFinishCallback r27) {
        /*
            Method dump skipped, instructions count: 959
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.pip2.phone.PipTransition.startAnimation(android.os.IBinder, android.window.TransitionInfo, android.view.SurfaceControl$Transaction, android.view.SurfaceControl$Transaction, com.android.wm.shell.transition.Transitions$TransitionFinishCallback):boolean");
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void syncPipSurfaceState(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        float f;
        TransitionInfo.Change pipChange = PipTransitionUtils.getPipChange(transitionInfo);
        if (pipChange == null) {
            return;
        }
        SurfaceControl leash = pipChange.getLeash();
        boolean isInPip = this.mPipTransitionState.isInPip();
        PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mPipSurfaceTransactionHelper;
        if (isInPip) {
            f = pipSurfaceTransactionHelper.mCornerRadius;
        } else {
            pipSurfaceTransactionHelper.getClass();
            f = 0.0f;
        }
        transaction.setCornerRadius(leash, f);
        int i = pipSurfaceTransactionHelper.mShadowRadius;
        transaction.setShadowRadius(leash, isInPip ? i : 0.0f);
        transaction2.setCornerRadius(leash, isInPip ? pipSurfaceTransactionHelper.mCornerRadius : 0.0f);
        transaction2.setShadowRadius(leash, isInPip ? i : 0.0f);
    }

    public final void updatePipChangesForFixedRotation(TransitionInfo transitionInfo, TransitionInfo.Change change, TransitionInfo.Change change2) {
        TransitionInfo.Change findFixedRotationChange = PipTransitionController.findFixedRotationChange(transitionInfo);
        Rect endAbsBounds = change.getEndAbsBounds();
        Rect endAbsBounds2 = change2.getEndAbsBounds();
        int startRotation = change.getStartRotation();
        PipDisplayLayoutState pipDisplayLayoutState = this.mPipDisplayLayoutState;
        int endFixedRotation = findFixedRotationChange != null ? findFixedRotationChange.getEndFixedRotation() : pipDisplayLayoutState.mDisplayLayout.mRotation;
        if (startRotation == endFixedRotation) {
            return;
        }
        this.mPipTransitionState.mInFixedRotation = true;
        Point point = new Point(endAbsBounds2.left - endAbsBounds.left, endAbsBounds2.top - endAbsBounds.top);
        pipDisplayLayoutState.rotateTo(endFixedRotation);
        PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
        float snapFraction = pipBoundsAlgorithm.getSnapFraction(pipBoundsAlgorithm.getEntryDestinationBounds());
        Rect movementBounds = pipBoundsAlgorithm.getMovementBounds(endAbsBounds, true);
        pipBoundsAlgorithm.mSnapAlgorithm.getClass();
        PipSnapAlgorithm.applySnapFraction(endAbsBounds, movementBounds, snapFraction);
        this.mPipBoundsState.setBounds(endAbsBounds);
        boolean z = endFixedRotation - startRotation == -3;
        Rect displayBounds = pipDisplayLayoutState.getDisplayBounds();
        endAbsBounds.offset(z ? 0 : -displayBounds.width(), z ? -displayBounds.height() : 0);
        endAbsBounds2.offsetTo(endAbsBounds.left + point.x, endAbsBounds.top + point.y);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
    }
}
