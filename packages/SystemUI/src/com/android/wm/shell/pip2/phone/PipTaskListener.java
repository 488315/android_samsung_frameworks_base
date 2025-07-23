package com.android.wm.shell.pip2.phone;

import android.app.ActivityManager;
import android.app.PictureInPictureParams;
import android.app.RemoteAction;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.SurfaceControl;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.internal.util.Preconditions;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipUtils;
import com.android.wm.shell.pip2.animation.PipResizeAnimator;
import com.android.wm.shell.pip2.phone.PipTransitionState;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipTaskListener implements ShellTaskOrganizer.TaskListener, PipTransitionState.PipTransitionStateChangedListener {
    static final String ANIMATING_ASPECT_RATIO_CHANGE = "animating_aspect_ratio_change";
    public final Context mContext;
    public final PipBoundsAlgorithm mPipBoundsAlgorithm;
    public final PipBoundsState mPipBoundsState;
    public PipResizeAnimatorSupplier mPipResizeAnimatorSupplier;
    public final PipScheduler mPipScheduler;
    public final PipTransitionState mPipTransitionState;
    public PictureInPictureParams mPictureInPictureParams = new PictureInPictureParams.Builder().build();
    public boolean mWaitingForAspectRatioChange = false;
    public final List mPipParamsChangedListeners = new ArrayList();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    interface PipResizeAnimatorSupplier {
        PipResizeAnimator get(Context context, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Rect rect, Rect rect2, Rect rect3, int i);
    }

    public PipTaskListener(Context context, ShellTaskOrganizer shellTaskOrganizer, PipTransitionState pipTransitionState, PipScheduler pipScheduler, PipBoundsState pipBoundsState, PipBoundsAlgorithm pipBoundsAlgorithm, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mPipTransitionState = pipTransitionState;
        this.mPipScheduler = pipScheduler;
        this.mPipBoundsState = pipBoundsState;
        this.mPipBoundsAlgorithm = pipBoundsAlgorithm;
        pipTransitionState.addPipTransitionStateChangedListener(this);
        if (PipUtils.isPip2ExperimentEnabled()) {
            shellExecutor.execute(new PipTaskListener$$ExternalSyntheticLambda1(this, shellTaskOrganizer, 0));
        }
        this.mPipResizeAnimatorSupplier = new PipTaskListener$$ExternalSyntheticLambda2();
        pipScheduler.mPipParamsSupplier = new PipTaskListener$$ExternalSyntheticLambda3(this);
        PipBoundsState.OnPipComponentChangedListener onPipComponentChangedListener = new PipBoundsState.OnPipComponentChangedListener() { // from class: com.android.wm.shell.pip2.phone.PipTaskListener$$ExternalSyntheticLambda4
            @Override // com.android.wm.shell.common.pip.PipBoundsState.OnPipComponentChangedListener
            public final void onPipComponentChanged() {
                PipTaskListener.this.mPictureInPictureParams = new PictureInPictureParams.Builder().build();
            }
        };
        if (((ArrayList) pipBoundsState.mOnPipComponentChangedListeners).contains(onPipComponentChangedListener)) {
            return;
        }
        ((ArrayList) pipBoundsState.mOnPipComponentChangedListeners).add(onPipComponentChangedListener);
    }

    @Override // com.android.wm.shell.pip2.phone.PipTransitionState.PipTransitionStateChangedListener
    public final void onPipTransitionStateChanged(int i, int i2, Bundle bundle) {
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        if (i2 == 4) {
            boolean z = bundle.getBoolean(ANIMATING_ASPECT_RATIO_CHANGE);
            this.mWaitingForAspectRatioChange = z;
            if (z) {
                Rect bounds = pipBoundsState.getBounds();
                this.mPipScheduler.scheduleAnimateResizePip(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, false, this.mPipBoundsAlgorithm.transformBoundsToAspectRatioIfValid(pipBoundsState.mAspectRatio, bounds, true, false));
                return;
            }
            return;
        }
        if (i2 != 5) {
            return;
        }
        SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) bundle.getParcelable("pip_start_tx", SurfaceControl.Transaction.class);
        SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) bundle.getParcelable("pip_finish_tx", SurfaceControl.Transaction.class);
        Rect rect = (Rect) bundle.getParcelable("pip_dest_bounds", Rect.class);
        int i3 = bundle.getInt("animating_bounds_change_duration", 0);
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        Preconditions.checkNotNull(pipTransitionState.mPinnedTaskLeash, "Leash is null for bounds transition.");
        if (this.mWaitingForAspectRatioChange) {
            this.mWaitingForAspectRatioChange = false;
            PipResizeAnimator pipResizeAnimator = this.mPipResizeAnimatorSupplier.get(this.mContext, pipTransitionState.mPinnedTaskLeash, transaction, transaction2, rect, pipBoundsState.getBounds(), rect, i3);
            pipResizeAnimator.mAnimationEndCallback = new PipTaskListener$$ExternalSyntheticLambda1(this, rect, 1);
            pipResizeAnimator.start();
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        PictureInPictureParams pictureInPictureParams = runningTaskInfo.pictureInPictureParams;
        if (this.mPictureInPictureParams.equals(pictureInPictureParams)) {
            return;
        }
        boolean z = ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0];
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        if (z) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 8967888972749681434L, 0, String.valueOf(runningTaskInfo.topActivity), String.valueOf(pipTransitionState), String.valueOf(this.mPictureInPictureParams), String.valueOf(pictureInPictureParams));
        }
        setPictureInPictureParams(pictureInPictureParams);
        final float aspectRatioFloat = this.mPictureInPictureParams.getAspectRatioFloat();
        if (this.mPictureInPictureParams.hasSetAspectRatio() && this.mPipBoundsAlgorithm.isValidPictureInPictureAspectRatio(aspectRatioFloat)) {
            float f = this.mPipBoundsState.mAspectRatio;
            PipUtils pipUtils = PipUtils.INSTANCE;
            if (Math.abs(aspectRatioFloat - f) > 0.05f) {
                pipTransitionState.mOnIdlePipTransitionStateRunnable = new Runnable() { // from class: com.android.wm.shell.pip2.phone.PipTaskListener$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        PipTaskListener pipTaskListener = PipTaskListener.this;
                        float f2 = aspectRatioFloat;
                        PipBoundsState pipBoundsState = pipTaskListener.mPipBoundsState;
                        pipBoundsState.setAspectRatio(f2);
                        Rect bounds = pipBoundsState.getBounds();
                        if (pipTaskListener.mPipBoundsAlgorithm.transformBoundsToAspectRatioIfValid(pipBoundsState.mAspectRatio, bounds, true, false).equals(pipBoundsState.getBounds())) {
                            return;
                        }
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("animating_aspect_ratio_change", true);
                        pipTaskListener.mPipTransitionState.setState(4, bundle);
                    }
                };
                pipTransitionState.maybeRunOnIdlePipTransitionStateCallback();
            }
        }
    }

    public final void setPictureInPictureParams(PictureInPictureParams pictureInPictureParams) {
        if (this.mPictureInPictureParams.equals(pictureInPictureParams)) {
            return;
        }
        if (pictureInPictureParams != null && (PipUtils.remoteActionsChanged(pictureInPictureParams.getActions(), this.mPictureInPictureParams.getActions()) || !PipUtils.remoteActionsMatch(pictureInPictureParams.getCloseAction(), this.mPictureInPictureParams.getCloseAction()))) {
            ArrayList arrayList = (ArrayList) this.mPipParamsChangedListeners;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                List<RemoteAction> actions = pictureInPictureParams.getActions();
                RemoteAction closeAction = pictureInPictureParams.getCloseAction();
                PhonePipMenuController phonePipMenuController = PhonePipMenuController.this;
                phonePipMenuController.mAppActions = actions;
                phonePipMenuController.mCloseAction = closeAction;
                phonePipMenuController.updateMenuActions$2();
            }
        }
        if (pictureInPictureParams == null) {
            pictureInPictureParams = new PictureInPictureParams.Builder().build();
        }
        this.mPictureInPictureParams = pictureInPictureParams;
        final StringJoiner stringJoiner = new StringJoiner("|", "[", "]");
        if (pictureInPictureParams.hasSetActions()) {
            pictureInPictureParams.getActions().forEach(new Consumer() { // from class: com.android.wm.shell.pip2.phone.PipTaskListener$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj2) {
                    stringJoiner.add(((RemoteAction) obj2).getTitle());
                }
            });
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 2247363879092567951L, 0, String.valueOf(stringJoiner.toString()));
        }
    }

    public void setPipResizeAnimatorSupplier(PipResizeAnimatorSupplier pipResizeAnimatorSupplier) {
        this.mPipResizeAnimatorSupplier = pipResizeAnimatorSupplier;
    }
}
