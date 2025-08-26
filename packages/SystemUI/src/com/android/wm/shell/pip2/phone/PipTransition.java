package com.android.wm.shell.pip2.phone;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.PictureInPictureParams;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
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
import com.android.wm.shell.desktopmode.DesktopTasksController$$ExternalSyntheticLambda3;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.pip2.PipSurfaceTransactionHelper;
import com.android.wm.shell.pip2.animation.PipAlphaAnimator;
import com.android.wm.shell.pip2.animation.PipEnterAnimator;
import com.android.wm.shell.pip2.phone.PipTransitionState;
import com.android.wm.shell.pip2.phone.transition.PipExpandHandler;
import com.android.wm.shell.pip2.phone.transition.PipTransitionUtils;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;

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
        float fWidth = endAbsBounds.width() / sourceRectHint.width();
        float fHeight = endAbsBounds.height() / sourceRectHint.height();
        pointF.set(fWidth, fHeight);
        pointF2.set((endAbsBounds.left - endAbsBounds2.left) - (sourceRectHint.left * fWidth), (endAbsBounds.top - endAbsBounds2.top) - (sourceRectHint.top * fHeight));
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

    /* JADX WARN: Removed duplicated region for block: B:26:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Rect getAdjustedSourceRectHint(TransitionInfo transitionInfo, TransitionInfo.Change change, TransitionInfo.Change change2) {
        float aspectRatioFloat;
        Rect startAbsBounds = change.getStartAbsBounds();
        Rect endAbsBounds = change.getEndAbsBounds();
        PictureInPictureParams pictureInPictureParams = change.getTaskInfo().pictureInPictureParams;
        Rect validSourceHintRect = PipBoundsAlgorithm.getValidSourceHintRect(pictureInPictureParams, startAbsBounds);
        if (!PipBoundsAlgorithm.isSourceRectHintValidForEnterPip(validSourceHintRect, endAbsBounds)) {
            validSourceHintRect = null;
        }
        Rect rect = new Rect();
        if (validSourceHintRect == null) {
            PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
            if (pictureInPictureParams != null) {
                pipBoundsAlgorithm.getClass();
                aspectRatioFloat = pictureInPictureParams.hasSetAspectRatio() ? pictureInPictureParams.getAspectRatioFloat() : pipBoundsAlgorithm.mDefaultAspectRatio;
            }
            rect.set(PipUtils.getEnterPipWithOverlaySrcRectHint(startAbsBounds, aspectRatioFloat));
            return rect;
        }
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
            public final void accept(Object obj) throws Resources.NotFoundException {
                int iIntValue;
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
                        iIntValue = activeDeskId.intValue();
                    } else {
                        if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                            Object[] objArr = {Integer.valueOf(i2)};
                            ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
                            SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopPipTransitionController", objArr);
                            ProtoLog.w(shellProtoLogGroup, "%s: handlePipTransitionIfInDesktop: Active desk not found for display id %d", spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
                            return;
                        }
                        Integer defaultDeskId = profile.getDefaultDeskId(i2);
                        if (defaultDeskId == null) {
                            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "DesktopPipTransitionController: handlePipTransitionIfInDesktop: Expected a default desk to exist in display with id ").toString());
                        }
                        iIntValue = defaultDeskId.intValue();
                    }
                    if (!profile.isOnlyVisibleNonClosingTaskInDesk(i, iIntValue)) {
                        DesktopPipTransitionController.logD("handlePipTransitionIfInDesktop: PiP task is not last visible task in Desk", new Object[0]);
                        return;
                    }
                    DesktopTasksController$$ExternalSyntheticLambda3 desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default = DesktopTasksController.performDesktopExitCleanUp$default(desktopPipTransitionController.desktopTasksController, windowContainerTransaction, Integer.valueOf(iIntValue), i2, true, false, 48);
                    if (desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default != null) {
                        desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default.mo781invoke(iBinder2);
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
                    PipTransition pipTransition = this.f$0;
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

    /* JADX WARN: Removed duplicated region for block: B:115:0x02f3  */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) throws Resources.NotFoundException {
        TransitionInfo.Change deferConfigActivityChange;
        PipEnterAnimator pipEnterAnimator;
        PictureInPictureParams pictureInPictureParams;
        TransitionInfo.Change deferConfigActivityChange2;
        TransitionInfo.Change change;
        IBinder iBinder2 = this.mEnterTransition;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        if (iBinder == iBinder2 || transitionInfo.getType() == 10) {
            this.mEnterTransition = null;
            TransitionInfo.Change pipChange = PipTransitionUtils.getPipChange(transitionInfo);
            if (pipChange != null) {
                if (TransitionUtil.isOpeningType(transitionInfo.getType())) {
                    for (TransitionInfo.Change change2 : transitionInfo.getChanges()) {
                        if (change2.getLeash() != null && TransitionUtil.isOpeningMode(change2.getMode())) {
                            transaction.setAlpha(change2.getLeash(), 1.0f);
                        }
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable("pip_task_leash", pipChange.getLeash());
                bundle.putParcelable("pip_task_info", pipChange.getTaskInfo());
                pipTransitionState.setState(2, bundle);
                boolean z = pipTransitionState.mInSwipePipToHomeTransition;
                PipDisplayLayoutState pipDisplayLayoutState = this.mPipDisplayLayoutState;
                if (z) {
                    TransitionInfo.Change pipChange2 = PipTransitionUtils.getPipChange(transitionInfo);
                    if (pipChange2 != null && (deferConfigActivityChange2 = PipTransitionUtils.getDeferConfigActivityChange(transitionInfo, pipChange2.getTaskInfo().getToken())) != null) {
                        this.mFinishCallback = transitionFinishCallback;
                        SurfaceControl leash = PipTransitionUtils.getLeash(pipChange2);
                        Rect endAbsBounds = pipChange2.getEndAbsBounds();
                        SurfaceControl surfaceControl = pipTransitionState.mSwipePipToHomeOverlay;
                        if (surfaceControl != null) {
                            Rect rect = pipTransitionState.mSwipePipToHomeAppBounds;
                            String str = PipAppIconOverlay.TAG;
                            int iMax = Math.max(Math.max(rect.width(), rect.height()), Math.max(endAbsBounds.width(), endAbsBounds.height())) + 1;
                            transaction.reparent(surfaceControl, leash).setLayer(surfaceControl, Integer.MAX_VALUE).setScale(surfaceControl, 1.0f, 1.0f).setPosition(surfaceControl, (endAbsBounds.width() - iMax) / 2.0f, (endAbsBounds.height() - iMax) / 2.0f);
                        }
                        int fixedRotationDelta = PipTransitionUtils.getFixedRotationDelta(transitionInfo, pipChange2, pipDisplayLayoutState);
                        if (fixedRotationDelta != 0) {
                            updatePipChangesForFixedRotation(transitionInfo, pipChange2, deferConfigActivityChange2);
                        }
                        PipTransitionUtils.getPipParams(pipChange2).copyOnlySet(new PictureInPictureParams.Builder().setSourceRectHint(getAdjustedSourceRectHint(transitionInfo, pipChange2, deferConfigActivityChange2)).build());
                        prepareConfigAtEndActivity(transaction, transaction2, pipChange2, deferConfigActivityChange2);
                        transaction.merge(transaction2);
                        PipEnterAnimator pipEnterAnimator2 = new PipEnterAnimator(this.mContext, leash, transaction, transaction2, endAbsBounds, fixedRotationDelta);
                        pipEnterAnimator2.setEnterStartState(pipChange2);
                        pipEnterAnimator2.onEnterAnimationUpdate(1.0f, transaction);
                        transaction.apply();
                        if (surfaceControl != null) {
                            this.mPipScheduler.startOverlayFadeoutAnimation(surfaceControl, true, new PipTransition$$ExternalSyntheticLambda1(surfaceControl, 1));
                        }
                        finishTransition();
                        return true;
                    }
                } else {
                    TransitionInfo.Change pipChange3 = PipTransitionUtils.getPipChange(transitionInfo);
                    if (pipChange3 != null) {
                        if (this.mEnterAnimationType == 1) {
                            this.mEnterAnimationType = 0;
                        } else if (!TransitionUtil.isOpeningMode(pipChange3.getMode()) || PipTransitionUtils.getDeferConfigActivityChange(transitionInfo, pipChange3.getContainer()) != null) {
                        }
                        TransitionInfo.Change pipChange4 = PipTransitionUtils.getPipChange(transitionInfo);
                        if (pipChange4 != null) {
                            this.mFinishCallback = transitionFinishCallback;
                            Rect endAbsBounds2 = pipChange4.getEndAbsBounds();
                            SurfaceControl surfaceControl2 = pipTransitionState.mPinnedTaskLeash;
                            Preconditions.checkNotNull(surfaceControl2, "Leash is null for alpha transition.");
                            int fixedRotationDelta2 = PipTransitionUtils.getFixedRotationDelta(transitionInfo, pipChange4, pipDisplayLayoutState);
                            if (fixedRotationDelta2 != 0) {
                                updatePipChangesForFixedRotation(transitionInfo, pipChange4, new TransitionInfo.Change((WindowContainerToken) null, new SurfaceControl()));
                            }
                            transaction.setWindowCrop(surfaceControl2, endAbsBounds2.width(), endAbsBounds2.height());
                            if (fixedRotationDelta2 != 0) {
                                if (fixedRotationDelta2 == 3) {
                                    fixedRotationDelta2 = -1;
                                }
                                Matrix matrix = new Matrix();
                                float[] fArr = new float[9];
                                matrix.setTranslate(endAbsBounds2.left, endAbsBounds2.top);
                                matrix.postRotate((-fixedRotationDelta2) * 90.0f);
                                transaction.setMatrix(surfaceControl2, matrix, fArr);
                                transaction2.setMatrix(surfaceControl2, matrix, fArr);
                            } else {
                                transaction.setPosition(surfaceControl2, endAbsBounds2.left, endAbsBounds2.top);
                            }
                            PipAlphaAnimator pipAlphaAnimator = new PipAlphaAnimator(this.mContext, surfaceControl2, transaction, transaction2, 0);
                            pipAlphaAnimator.mAnimationEndCallback = new PipTransition$$ExternalSyntheticLambda1(this, 0);
                            this.mTransitionAnimator = pipAlphaAnimator;
                            pipAlphaAnimator.start();
                            return true;
                        }
                    } else {
                        if (PipTransitionUtils.getDeferConfigActivityChange(transitionInfo, pipChange.getTaskInfo().getToken()) == null) {
                            Log.wtf("PipTransition", "PipTransition.startAnimation didn't handle a scheduled PiP entry\ntransitionInfo=" + transitionInfo + ",\ncallers=" + Debug.getCallers(4));
                            return false;
                        }
                        TransitionInfo.Change pipChange5 = PipTransitionUtils.getPipChange(transitionInfo);
                        if (pipChange5 != null && (deferConfigActivityChange = PipTransitionUtils.getDeferConfigActivityChange(transitionInfo, pipChange5.getTaskInfo().getToken())) != null) {
                            this.mFinishCallback = transitionFinishCallback;
                            SurfaceControl leash2 = PipTransitionUtils.getLeash(pipChange5);
                            Rect startAbsBounds = pipChange5.getStartAbsBounds();
                            Rect endAbsBounds3 = pipChange5.getEndAbsBounds();
                            PictureInPictureParams pipParams = PipTransitionUtils.getPipParams(pipChange5);
                            Rect adjustedSourceRectHint = getAdjustedSourceRectHint(transitionInfo, pipChange5, deferConfigActivityChange);
                            int fixedRotationDelta3 = PipTransitionUtils.getFixedRotationDelta(transitionInfo, pipChange5, pipDisplayLayoutState);
                            if (fixedRotationDelta3 != 0) {
                                updatePipChangesForFixedRotation(transitionInfo, pipChange5, deferConfigActivityChange);
                            }
                            PipEnterAnimator pipEnterAnimator3 = new PipEnterAnimator(this.mContext, leash2, transaction, transaction2, endAbsBounds3, fixedRotationDelta3);
                            Rect validSourceHintRect = PipBoundsAlgorithm.getValidSourceHintRect(pipParams, startAbsBounds);
                            if ((PipBoundsAlgorithm.isSourceRectHintValidForEnterPip(validSourceHintRect, endAbsBounds3) ? validSourceHintRect : null) == null) {
                                pipEnterAnimator = pipEnterAnimator3;
                                pictureInPictureParams = pipParams;
                                pipEnterAnimator.setAppIconContentOverlay(this.mContext, startAbsBounds, endAbsBounds3, pipChange5.getTaskInfo().topActivityInfo, pipBoundsState.mLauncherState.mAppIconSizePx);
                            } else {
                                pipEnterAnimator = pipEnterAnimator3;
                                pictureInPictureParams = pipParams;
                            }
                            pictureInPictureParams.copyOnlySet(new PictureInPictureParams.Builder().setSourceRectHint(adjustedSourceRectHint).build());
                            prepareConfigAtEndActivity(transaction, transaction2, pipChange5, deferConfigActivityChange);
                            pipEnterAnimator.mAnimationStartCallback = new PipTransition$$ExternalSyntheticLambda4(pipEnterAnimator, pipChange5);
                            pipEnterAnimator.mAnimationEndCallback = new PipTransition$$ExternalSyntheticLambda4(this, pipEnterAnimator);
                            this.mTransitionAnimator = pipEnterAnimator;
                            pipEnterAnimator.start();
                            return true;
                        }
                    }
                }
            }
        } else {
            if (iBinder == this.mExitViaExpandTransition) {
                this.mExitViaExpandTransition = null;
                return this.mExpandHandler.startAnimation(iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
            }
            if (iBinder != this.mResizeTransition) {
                if (pipTransitionState.getPipTaskToken() != null && (change = transitionInfo.getChange(pipTransitionState.getPipTaskToken())) != null) {
                    boolean z2 = transitionInfo.getType() == 4 && change.getMode() == 4;
                    boolean z3 = transitionInfo.getType() == 1003 && change.getMode() == 4;
                    if (z2 || isPipClosing(transitionInfo) || z3) {
                        pipTransitionState.setState(7, null);
                        TransitionInfo.Change changeByToken = PipTransitionUtils.getChangeByToken(transitionInfo, pipTransitionState.getPipTaskToken());
                        this.mFinishCallback = transitionFinishCallback;
                        if (isPipClosing(transitionInfo)) {
                            pipBoundsState.setLastPipComponentName(null);
                        }
                        transaction2.setAlpha(changeByToken.getLeash(), 0.0f);
                        if (this.mPendingRemoveWithFadeout) {
                            PipAlphaAnimator pipAlphaAnimator2 = new PipAlphaAnimator(this.mContext, changeByToken.getLeash(), transaction, transaction2, 1);
                            pipAlphaAnimator2.mAnimationEndCallback = new PipTransition$$ExternalSyntheticLambda1(this, 0);
                            pipAlphaAnimator2.start();
                            return true;
                        }
                        transaction.setAlpha(changeByToken.getLeash(), 0.0f);
                        transaction.apply();
                        finishTransition();
                        return true;
                    }
                }
                syncPipSurfaceState(transitionInfo, transaction, transaction2);
                return false;
            }
            this.mResizeTransition = null;
            TransitionInfo.Change pipChange6 = PipTransitionUtils.getPipChange(transitionInfo);
            if (pipChange6 != null) {
                this.mFinishCallback = transitionFinishCallback;
                TransitionInfo.Change deferConfigActivityChange3 = PipTransitionUtils.getDeferConfigActivityChange(transitionInfo, pipChange6.getTaskInfo().getToken());
                if (deferConfigActivityChange3 != null) {
                    pipChange6.getTaskInfo().pictureInPictureParams = null;
                    prepareConfigAtEndActivity(transaction, transaction2, pipChange6, deferConfigActivityChange3);
                }
                transaction.setWindowCrop(pipChange6.getLeash(), pipChange6.getEndAbsBounds().width(), pipChange6.getEndAbsBounds().height());
                Bundle bundle2 = new Bundle();
                bundle2.putParcelable("pip_start_tx", transaction);
                bundle2.putParcelable("pip_finish_tx", transaction2);
                bundle2.putParcelable("pip_dest_bounds", pipChange6.getEndAbsBounds());
                int i = this.mBoundsChangeDuration;
                if (i > 0) {
                    bundle2.putInt("animating_bounds_change_duration", i);
                    this.mBoundsChangeDuration = 0;
                }
                pipTransitionState.setState(5, bundle2);
                return true;
            }
        }
        return false;
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void syncPipSurfaceState(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        float f;
        TransitionInfo.Change pipChange = PipTransitionUtils.getPipChange(transitionInfo);
        if (pipChange == null) {
            return;
        }
        SurfaceControl leash = pipChange.getLeash();
        boolean zIsInPip = this.mPipTransitionState.isInPip();
        PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mPipSurfaceTransactionHelper;
        if (zIsInPip) {
            f = pipSurfaceTransactionHelper.mCornerRadius;
        } else {
            pipSurfaceTransactionHelper.getClass();
            f = 0.0f;
        }
        transaction.setCornerRadius(leash, f);
        int i = pipSurfaceTransactionHelper.mShadowRadius;
        transaction.setShadowRadius(leash, zIsInPip ? i : 0.0f);
        transaction2.setCornerRadius(leash, zIsInPip ? pipSurfaceTransactionHelper.mCornerRadius : 0.0f);
        transaction2.setShadowRadius(leash, zIsInPip ? i : 0.0f);
    }

    public final void updatePipChangesForFixedRotation(TransitionInfo transitionInfo, TransitionInfo.Change change, TransitionInfo.Change change2) throws Resources.NotFoundException {
        TransitionInfo.Change changeFindFixedRotationChange = PipTransitionController.findFixedRotationChange(transitionInfo);
        Rect endAbsBounds = change.getEndAbsBounds();
        Rect endAbsBounds2 = change2.getEndAbsBounds();
        int startRotation = change.getStartRotation();
        PipDisplayLayoutState pipDisplayLayoutState = this.mPipDisplayLayoutState;
        int endFixedRotation = changeFindFixedRotationChange != null ? changeFindFixedRotationChange.getEndFixedRotation() : pipDisplayLayoutState.mDisplayLayout.mRotation;
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
