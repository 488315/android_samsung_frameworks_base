package com.android.p038wm.shell.compatui;

import android.app.TaskInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.common.DisplayLayout;
import com.android.p038wm.shell.common.SyncTransactionQueue;
import com.android.p038wm.shell.transition.Transitions;
import com.android.systemui.R;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RestartDialogWindowManager extends CompatUIWindowManagerAbstract {
    public final DialogAnimationController mAnimationController;
    public final CompatUIConfiguration mCompatUIConfiguration;
    public final int mDialogVerticalMargin;
    RestartDialogLayout mLayout;
    public final Consumer mOnDismissCallback;
    public final Consumer mOnRestartCallback;
    public boolean mRequestRestartDialog;
    public final Transitions mTransitions;

    public RestartDialogWindowManager(Context context, TaskInfo taskInfo, SyncTransactionQueue syncTransactionQueue, ShellTaskOrganizer.TaskListener taskListener, DisplayLayout displayLayout, Transitions transitions, Consumer<Pair<TaskInfo, ShellTaskOrganizer.TaskListener>> consumer, Consumer<Pair<TaskInfo, ShellTaskOrganizer.TaskListener>> consumer2, CompatUIConfiguration compatUIConfiguration) {
        this(context, taskInfo, syncTransactionQueue, taskListener, displayLayout, transitions, consumer, consumer2, new DialogAnimationController(context, "RestartDialogWindowManager"), compatUIConfiguration);
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View createLayout() {
        this.mLayout = (RestartDialogLayout) LayoutInflater.from(this.mContext).inflate(R.layout.letterbox_restart_dialog_layout, (ViewGroup) null);
        updateDialogMargins();
        Transitions transitions = this.mTransitions;
        int i = 0;
        RestartDialogWindowManager$$ExternalSyntheticLambda0 restartDialogWindowManager$$ExternalSyntheticLambda0 = new RestartDialogWindowManager$$ExternalSyntheticLambda0(this, i);
        if (transitions.mPendingTransitions.isEmpty() && !transitions.isAnimating()) {
            i = 1;
        }
        if (i != 0) {
            restartDialogWindowManager$$ExternalSyntheticLambda0.run();
        } else {
            transitions.mRunWhenIdleQueue.add(restartDialogWindowManager$$ExternalSyntheticLambda0);
        }
        return this.mLayout;
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean eligibleToShowLayout() {
        if (!this.mRequestRestartDialog || isTaskbarEduShowing()) {
            return false;
        }
        if (this.mLayout == null) {
            CompatUIConfiguration compatUIConfiguration = this.mCompatUIConfiguration;
            TaskInfo taskInfo = this.mTaskInfo;
            SharedPreferences sharedPreferences = compatUIConfiguration.mCompatUISharedPreferences;
            int i = taskInfo.userId;
            String packageName = taskInfo.topActivity.getPackageName();
            if (!(!sharedPreferences.getBoolean(packageName + "@" + i, false))) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View getLayout() {
        return this.mLayout;
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final WindowManager.LayoutParams getWindowLayoutParams() {
        Rect taskBounds = getTaskBounds();
        return getWindowLayoutParams(taskBounds.width(), taskBounds.height());
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final int getZOrder() {
        return 10002;
    }

    public boolean isTaskbarEduShowing() {
        return Settings.Secure.getInt(this.mContext.getContentResolver(), "launcher_taskbar_education_showing", 0) == 1;
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void onParentBoundsChanged() {
        if (this.mLayout == null) {
            return;
        }
        WindowManager.LayoutParams windowLayoutParams = getWindowLayoutParams();
        this.mLayout.setLayoutParams(windowLayoutParams);
        updateDialogMargins();
        SurfaceControlViewHost surfaceControlViewHost = this.mViewHost;
        if (surfaceControlViewHost == null) {
            return;
        }
        surfaceControlViewHost.relayout(windowLayoutParams);
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void release() {
        this.mAnimationController.cancelAnimation();
        super.release();
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void removeLayout() {
        this.mLayout = null;
    }

    public final void updateDialogMargins() {
        RestartDialogLayout restartDialogLayout = this.mLayout;
        if (restartDialogLayout == null) {
            return;
        }
        View view = restartDialogLayout.mDialogContainer;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        Rect taskBounds = getTaskBounds();
        Rect rect = new Rect(this.mStableBounds);
        rect.intersect(getTaskBounds());
        int i = this.mDialogVerticalMargin;
        marginLayoutParams.topMargin = i;
        marginLayoutParams.bottomMargin = (taskBounds.bottom - rect.bottom) + i;
        view.setLayoutParams(marginLayoutParams);
    }

    public RestartDialogWindowManager(Context context, TaskInfo taskInfo, SyncTransactionQueue syncTransactionQueue, ShellTaskOrganizer.TaskListener taskListener, DisplayLayout displayLayout, Transitions transitions, Consumer<Pair<TaskInfo, ShellTaskOrganizer.TaskListener>> consumer, Consumer<Pair<TaskInfo, ShellTaskOrganizer.TaskListener>> consumer2, DialogAnimationController dialogAnimationController, CompatUIConfiguration compatUIConfiguration) {
        super(context, taskInfo, syncTransactionQueue, taskListener, displayLayout);
        this.mTransitions = transitions;
        this.mOnDismissCallback = consumer2;
        this.mOnRestartCallback = consumer;
        this.mAnimationController = dialogAnimationController;
        this.mDialogVerticalMargin = (int) this.mContext.getResources().getDimension(R.dimen.letterbox_restart_dialog_margin);
        this.mCompatUIConfiguration = compatUIConfiguration;
    }

    @Override // com.android.p038wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void updateSurfacePosition() {
    }
}
