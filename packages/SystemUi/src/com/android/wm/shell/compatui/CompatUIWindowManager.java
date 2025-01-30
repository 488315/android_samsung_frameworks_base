package com.android.wm.shell.compatui;

import android.app.TaskInfo;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.window.TaskAppearedInfo;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import com.android.internal.util.FrameworkStatsLog;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.compatui.CompatUIController;
import com.android.systemui.R;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CompatUIWindowManager extends CompatUIWindowManagerAbstract {
    public final CompatUIController.CompatUICallback mCallback;
    int mCameraCompatControlState;
    CompatUIHintsState mCompatUIHintsState;
    boolean mHasSizeCompat;
    CompatUILayout mLayout;
    public final Consumer mOnRestartButtonClicked;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CompatUIHintsState {
        boolean mHasShownCameraCompatHint;
        boolean mHasShownSizeCompatHint;
    }

    public CompatUIWindowManager(Context context, TaskInfo taskInfo, SyncTransactionQueue syncTransactionQueue, CompatUIController.CompatUICallback compatUICallback, ShellTaskOrganizer.TaskListener taskListener, DisplayLayout displayLayout, CompatUIHintsState compatUIHintsState, CompatUIConfiguration compatUIConfiguration, Consumer<Pair<TaskInfo, ShellTaskOrganizer.TaskListener>> consumer) {
        super(context, taskInfo, syncTransactionQueue, taskListener, displayLayout);
        this.mCameraCompatControlState = 0;
        this.mCallback = compatUICallback;
        this.mHasSizeCompat = taskInfo.topActivityInSizeCompat;
        this.mCameraCompatControlState = taskInfo.cameraCompatControlState;
        this.mCompatUIHintsState = compatUIHintsState;
        this.mOnRestartButtonClicked = consumer;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View createLayout() {
        TaskAppearedInfo taskAppearedInfo;
        ActivityInfo activityInfo;
        CompatUILayout inflateLayout = inflateLayout();
        this.mLayout = inflateLayout;
        inflateLayout.mWindowManager = this;
        updateVisibilityOfViews();
        if (this.mHasSizeCompat) {
            CompatUIController.CompatUICallback compatUICallback = this.mCallback;
            int i = this.mTaskId;
            ShellTaskOrganizer shellTaskOrganizer = (ShellTaskOrganizer) compatUICallback;
            synchronized (shellTaskOrganizer.mLock) {
                taskAppearedInfo = (TaskAppearedInfo) shellTaskOrganizer.mTasks.get(i);
            }
            if (taskAppearedInfo != null && (activityInfo = taskAppearedInfo.getTaskInfo().topActivityInfo) != null) {
                FrameworkStatsLog.write(387, activityInfo.applicationInfo.uid, 1);
            }
        }
        return this.mLayout;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean eligibleToShowLayout() {
        return this.mHasSizeCompat || shouldShowCameraControl();
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View getLayout() {
        return this.mLayout;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final int getZOrder() {
        return 10001;
    }

    public CompatUILayout inflateLayout() {
        return (CompatUILayout) LayoutInflater.from(this.mContext).inflate(R.layout.compat_ui_layout, (ViewGroup) null);
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void removeLayout() {
        this.mLayout = null;
    }

    public final boolean shouldShowCameraControl() {
        int i = this.mCameraCompatControlState;
        return (i == 0 || i == 3) ? false : true;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean updateCompatInfo(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener, boolean z) {
        boolean z2 = this.mHasSizeCompat;
        int i = this.mCameraCompatControlState;
        this.mHasSizeCompat = taskInfo.topActivityInSizeCompat;
        this.mCameraCompatControlState = taskInfo.cameraCompatControlState;
        if (!super.updateCompatInfo(taskInfo, taskListener, z)) {
            return false;
        }
        if (z2 == this.mHasSizeCompat && i == this.mCameraCompatControlState) {
            return true;
        }
        updateVisibilityOfViews();
        return true;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public void updateSurfacePosition() {
        int i;
        int measuredWidth;
        if (this.mLayout == null) {
            return;
        }
        Rect taskBounds = getTaskBounds();
        Rect rect = new Rect(this.mStableBounds);
        rect.intersect(getTaskBounds());
        if (MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m11m(this.mContext) == 1) {
            i = rect.left;
            measuredWidth = taskBounds.left;
        } else {
            i = rect.right - taskBounds.left;
            measuredWidth = this.mLayout.getMeasuredWidth();
        }
        int i2 = i - measuredWidth;
        int measuredHeight = (rect.bottom - taskBounds.top) - this.mLayout.getMeasuredHeight();
        if (this.mLeash == null) {
            return;
        }
        this.mSyncQueue.runInSync(new CompatUIWindowManagerAbstract$$ExternalSyntheticLambda0(this, i2, measuredHeight));
    }

    public final void updateVisibilityOfViews() {
        CompatUILayout compatUILayout = this.mLayout;
        if (compatUILayout == null) {
            return;
        }
        boolean z = this.mHasSizeCompat;
        compatUILayout.setViewVisibility(R.id.size_compat_restart_button, z);
        if (!z) {
            compatUILayout.setViewVisibility(R.id.size_compat_hint, false);
        }
        if (this.mHasSizeCompat && !this.mCompatUIHintsState.mHasShownSizeCompatHint) {
            this.mLayout.setViewVisibility(R.id.size_compat_hint, true);
            this.mCompatUIHintsState.mHasShownSizeCompatHint = true;
        }
        CompatUILayout compatUILayout2 = this.mLayout;
        boolean shouldShowCameraControl = shouldShowCameraControl();
        compatUILayout2.setViewVisibility(R.id.camera_compat_control, shouldShowCameraControl);
        if (!shouldShowCameraControl) {
            compatUILayout2.setViewVisibility(R.id.camera_compat_hint, false);
        }
        if (shouldShowCameraControl() && !this.mCompatUIHintsState.mHasShownCameraCompatHint) {
            this.mLayout.setViewVisibility(R.id.camera_compat_hint, true);
            this.mCompatUIHintsState.mHasShownCameraCompatHint = true;
        }
        if (shouldShowCameraControl()) {
            this.mLayout.updateCameraTreatmentButton(this.mCameraCompatControlState);
        }
    }
}
