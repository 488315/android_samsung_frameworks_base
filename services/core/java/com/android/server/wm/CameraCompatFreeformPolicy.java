package com.android.server.wm;

import android.content.res.Configuration;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.window.flags.Flags;

public final class CameraCompatFreeformPolicy
        implements CameraStateMonitor.CameraCompatStateListener, ActivityRefresher.Evaluator {
    public final ActivityRefresher mActivityRefresher;
    public final CameraStateMonitor mCameraStateMonitor;
    public Task mCameraTask;
    public final DisplayContent mDisplayContent;
    public boolean mIsCameraCompatTreatmentPending = false;
    public boolean mIsRunning;

    public CameraCompatFreeformPolicy(
            DisplayContent displayContent,
            CameraStateMonitor cameraStateMonitor,
            ActivityRefresher activityRefresher) {
        this.mDisplayContent = displayContent;
        this.mCameraStateMonitor = cameraStateMonitor;
        this.mActivityRefresher = activityRefresher;
    }

    public boolean isRunning() {
        return this.mIsRunning;
    }

    public final boolean isTreatmentEnabledForActivity(ActivityRecord activityRecord) {
        return (!shouldApplyFreeformTreatmentForCameraCompat(activityRecord)
                        || this.mCameraStateMonitor.getCameraIdForActivity(activityRecord) == null
                        || activityRecord.getRequestedConfigurationOrientation() == 0
                        || !activityRecord.inFreeformWindowingMode()
                        || activityRecord.getRequestedOrientation() == 5
                        || activityRecord.getRequestedOrientation() == 14
                        || activityRecord.isEmbedded())
                ? false
                : true;
    }

    @Override // com.android.server.wm.CameraStateMonitor.CameraCompatStateListener
    public final boolean onCameraClosed(String str) {
        Task task = this.mCameraTask;
        ActivityRecord topActivity = task != null ? task.getTopActivity(false, false) : null;
        if (topActivity != null) {
            if ((!isTreatmentEnabledForActivity(topActivity)
                            || str.equals(
                                    this.mCameraStateMonitor.getCameraIdForActivity(topActivity)))
                    ? false
                    : topActivity
                            .mAppCompatController
                            .mAppCompatOverrides
                            .mAppCompatCameraOverrides
                            .mAppCompatCameraOverridesState
                            .mIsRefreshRequested) {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_STATES,
                            -2283066544361882071L,
                            1,
                            null,
                            Long.valueOf(this.mDisplayContent.mDisplayId),
                            String.valueOf(str));
                }
                return false;
            }
        }
        this.mCameraTask = null;
        this.mIsCameraCompatTreatmentPending = false;
        return true;
    }

    @Override // com.android.server.wm.CameraStateMonitor.CameraCompatStateListener
    public final void onCameraOpened(ActivityRecord activityRecord) {
        int i;
        if (isTreatmentEnabledForActivity(activityRecord)) {
            int i2 =
                    activityRecord
                            .mAppCompatController
                            .mAppCompatOverrides
                            .mAppCompatCameraOverrides
                            .mAppCompatCameraOverridesState
                            .mFreeformCameraCompatMode;
            int requestedConfigurationOrientation =
                    activityRecord.getRequestedConfigurationOrientation();
            if (requestedConfigurationOrientation != 1) {
                i = 2;
                if (requestedConfigurationOrientation != 2) {
                    i = 0;
                }
            } else {
                i = 1;
            }
            if (i == i2) {
                this.mIsCameraCompatTreatmentPending = false;
                return;
            }
            this.mIsCameraCompatTreatmentPending = true;
            this.mCameraTask = activityRecord.task;
            activityRecord
                            .mAppCompatController
                            .mAppCompatOverrides
                            .mAppCompatCameraOverrides
                            .mAppCompatCameraOverridesState
                            .mFreeformCameraCompatMode =
                    i;
            activityRecord.recomputeConfiguration();
            activityRecord.updateReportedConfigurationAndSend();
            Task task = activityRecord.task;
            if (task != null) {
                task.dispatchTaskInfoChangedIfNeeded(true);
            }
        }
    }

    public boolean shouldApplyFreeformTreatmentForCameraCompat(ActivityRecord activityRecord) {
        return Flags.cameraCompatForFreeform() && !activityRecord.info.isChangeEnabled(314961188L);
    }

    @Override // com.android.server.wm.ActivityRefresher.Evaluator
    public final boolean shouldRefreshActivity(
            ActivityRecord activityRecord,
            Configuration configuration,
            Configuration configuration2) {
        return isTreatmentEnabledForActivity(activityRecord)
                && this.mIsCameraCompatTreatmentPending;
    }
}
