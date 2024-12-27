package com.android.server.wm;

import android.app.compat.CompatChanges;
import android.view.InputApplicationHandle;
import android.view.InputWindowHandle;
import android.view.SurfaceControl;

import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ActivityRecordInputSink {
    public final ActivityRecord mActivityRecord;
    public InputWindowHandleWrapper mInputWindowHandleWrapper;
    public final boolean mIsCompatEnabled;
    public final String mName;
    public SurfaceControl mSurfaceControl;

    public ActivityRecordInputSink(ActivityRecord activityRecord, ActivityRecord activityRecord2) {
        this.mActivityRecord = activityRecord;
        this.mIsCompatEnabled = CompatChanges.isChangeEnabled(194480991L, activityRecord.getUid());
        this.mName =
                Integer.toHexString(System.identityHashCode(this))
                        + " ActivityRecordInputSink "
                        + activityRecord.mActivityComponent.flattenToShortString();
        if (activityRecord2 != null) {
            activityRecord2.mAllowedTouchUid = activityRecord.getUid();
        }
    }

    public final void applyChangesToSurfaceIfChanged(SurfaceControl.Transaction transaction) {
        WindowState findMainWindow;
        Task task;
        Task rootTask;
        InputWindowHandleWrapper inputWindowHandleWrapper = this.mInputWindowHandleWrapper;
        String str = this.mName;
        ActivityRecord activityRecord = this.mActivityRecord;
        if (inputWindowHandleWrapper == null) {
            InputWindowHandle inputWindowHandle =
                    new InputWindowHandle(
                            (InputApplicationHandle) null, activityRecord.getDisplayId());
            inputWindowHandle.replaceTouchableRegionWithCrop = true;
            inputWindowHandle.name = str;
            inputWindowHandle.layoutParamsType = 2022;
            inputWindowHandle.ownerPid = WindowManagerService.MY_PID;
            inputWindowHandle.ownerUid = WindowManagerService.MY_UID;
            inputWindowHandle.inputConfig = 5;
            this.mInputWindowHandleWrapper = new InputWindowHandleWrapper(inputWindowHandle);
        }
        Task task2 = activityRecord.task;
        ActivityRecord activityBelow =
                task2 != null ? task2.getActivityBelow(activityRecord) : null;
        if ((activityBelow != null
                        && (activityBelow.mAllowedTouchUid == activityRecord.getUid()
                                || activityBelow.isUid(activityRecord.getUid())))
                || !this.mIsCompatEnabled
                || activityRecord.inTransitionSelfOrParent()
                || !activityRecord.mActivityRecordInputSinkEnabled
                || activityRecord.inFreeformWindowingMode()
                || activityRecord.mPopOverState.mIsActivated
                || ((CoreRune.MW_EMBED_ACTIVITY && activityRecord.isSplitEmbedded())
                        || (((findMainWindow = activityRecord.findMainWindow(true)) != null
                                        && findMainWindow.isVisible()
                                        && findMainWindow.mWindowFrames.mFrame.isEmpty())
                                || ((task = activityRecord.task) != null
                                        && task.getConfiguration()
                                                        .windowConfiguration
                                                        .getWindowingMode()
                                                == 6
                                        && task.isAlwaysOnTop()
                                        && (rootTask =
                                                        activityRecord.mRootWindowContainer
                                                                .getRootTask(
                                                                        task.mTaskViewTaskOrganizerTaskId))
                                                != null
                                        && rootTask.isFreeformForceHidden())))) {
            this.mInputWindowHandleWrapper.setInputConfigMasked(8, 8);
        } else {
            this.mInputWindowHandleWrapper.setInputConfigMasked(0, 8);
        }
        InputWindowHandleWrapper inputWindowHandleWrapper2 = this.mInputWindowHandleWrapper;
        int displayId = activityRecord.getDisplayId();
        InputWindowHandle inputWindowHandle2 = inputWindowHandleWrapper2.mHandle;
        if (inputWindowHandle2.displayId != displayId) {
            inputWindowHandle2.displayId = displayId;
            inputWindowHandleWrapper2.mChanged = true;
        }
        InputWindowHandleWrapper inputWindowHandleWrapper3 = this.mInputWindowHandleWrapper;
        if (this.mSurfaceControl == null) {
            SurfaceControl build =
                    activityRecord
                            .makeChildSurface(null)
                            .setName(str)
                            .setHidden(false)
                            .setCallsite("ActivityRecordInputSink.createSurface")
                            .build();
            transaction.setLayer(build, Integer.MIN_VALUE);
            this.mSurfaceControl = build;
        }
        if (inputWindowHandleWrapper3.mChanged) {
            transaction.setInputWindowInfo(this.mSurfaceControl, inputWindowHandleWrapper3.mHandle);
            inputWindowHandleWrapper3.mChanged = false;
        }
    }
}
