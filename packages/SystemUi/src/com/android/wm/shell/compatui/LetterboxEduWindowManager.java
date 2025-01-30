package com.android.wm.shell.compatui;

import android.app.TaskInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.DockStateReader;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.transition.Transitions;
import com.android.systemui.R;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LetterboxEduWindowManager extends CompatUIWindowManagerAbstract {
    public final DialogAnimationController mAnimationController;
    public final CompatUIConfiguration mCompatUIConfiguration;
    public final int mDialogVerticalMargin;
    public final DockStateReader mDockStateReader;
    public boolean mEligibleForLetterboxEducation;
    LetterboxEduDialogLayout mLayout;
    public final Consumer mOnDismissCallback;
    public final Transitions mTransitions;
    public final int mUserId;

    public LetterboxEduWindowManager(Context context, TaskInfo taskInfo, SyncTransactionQueue syncTransactionQueue, ShellTaskOrganizer.TaskListener taskListener, DisplayLayout displayLayout, Transitions transitions, Consumer<Pair<TaskInfo, ShellTaskOrganizer.TaskListener>> consumer, DockStateReader dockStateReader, CompatUIConfiguration compatUIConfiguration) {
        this(context, taskInfo, syncTransactionQueue, taskListener, displayLayout, transitions, consumer, new DialogAnimationController(context, "LetterboxEduWindowManager"), dockStateReader, compatUIConfiguration);
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View createLayout() {
        this.mLayout = (LetterboxEduDialogLayout) LayoutInflater.from(this.mContext).inflate(R.layout.letterbox_education_dialog_layout, (ViewGroup) null);
        updateDialogMargins();
        Transitions transitions = this.mTransitions;
        int i = 0;
        LetterboxEduWindowManager$$ExternalSyntheticLambda0 letterboxEduWindowManager$$ExternalSyntheticLambda0 = new LetterboxEduWindowManager$$ExternalSyntheticLambda0(this, i);
        if (transitions.mPendingTransitions.isEmpty() && !transitions.isAnimating()) {
            i = 1;
        }
        if (i != 0) {
            letterboxEduWindowManager$$ExternalSyntheticLambda0.run();
        } else {
            transitions.mRunWhenIdleQueue.add(letterboxEduWindowManager$$ExternalSyntheticLambda0);
        }
        return this.mLayout;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean eligibleToShowLayout() {
        if (!this.mEligibleForLetterboxEducation || isTaskbarEduShowing()) {
            return false;
        }
        if (this.mLayout == null) {
            if (this.mCompatUIConfiguration.mLetterboxEduSharedPreferences.getBoolean(String.valueOf(this.mUserId), false)) {
                return false;
            }
        }
        Intent registerReceiver = this.mDockStateReader.mContext.registerReceiver(null, DockStateReader.DOCK_INTENT_FILTER, 2);
        return !(registerReceiver != null && registerReceiver.getIntExtra("android.intent.extra.DOCK_STATE", 0) != 0);
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View getLayout() {
        return this.mLayout;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final WindowManager.LayoutParams getWindowLayoutParams() {
        Rect taskBounds = getTaskBounds();
        return getWindowLayoutParams(taskBounds.width(), taskBounds.height());
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final int getZOrder() {
        return 10002;
    }

    public boolean isTaskbarEduShowing() {
        return Settings.Secure.getInt(this.mContext.getContentResolver(), "launcher_taskbar_education_showing", 0) == 1;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean needsToBeRecreated(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener) {
        if (super.needsToBeRecreated(taskInfo, taskListener)) {
            return !this.mCompatUIConfiguration.mLetterboxEduSharedPreferences.getBoolean(String.valueOf(this.mUserId), false);
        }
        return false;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
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

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void release() {
        this.mAnimationController.cancelAnimation();
        super.release();
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void removeLayout() {
        this.mLayout = null;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean updateCompatInfo(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener, boolean z) {
        this.mEligibleForLetterboxEducation = taskInfo.topActivityEligibleForLetterboxEducation;
        return super.updateCompatInfo(taskInfo, taskListener, z);
    }

    public final void updateDialogMargins() {
        LetterboxEduDialogLayout letterboxEduDialogLayout = this.mLayout;
        if (letterboxEduDialogLayout == null) {
            return;
        }
        View view = letterboxEduDialogLayout.mDialogContainer;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        Rect taskBounds = getTaskBounds();
        Rect rect = new Rect(this.mStableBounds);
        rect.intersect(getTaskBounds());
        int i = rect.top - taskBounds.top;
        int i2 = this.mDialogVerticalMargin;
        marginLayoutParams.topMargin = i + i2;
        marginLayoutParams.bottomMargin = (taskBounds.bottom - rect.bottom) + i2;
        view.setLayoutParams(marginLayoutParams);
    }

    public LetterboxEduWindowManager(Context context, TaskInfo taskInfo, SyncTransactionQueue syncTransactionQueue, ShellTaskOrganizer.TaskListener taskListener, DisplayLayout displayLayout, Transitions transitions, Consumer<Pair<TaskInfo, ShellTaskOrganizer.TaskListener>> consumer, DialogAnimationController dialogAnimationController, DockStateReader dockStateReader, CompatUIConfiguration compatUIConfiguration) {
        super(context, taskInfo, syncTransactionQueue, taskListener, displayLayout);
        this.mTransitions = transitions;
        this.mOnDismissCallback = consumer;
        this.mAnimationController = dialogAnimationController;
        this.mUserId = taskInfo.userId;
        this.mDialogVerticalMargin = (int) this.mContext.getResources().getDimension(R.dimen.letterbox_education_dialog_margin);
        this.mDockStateReader = dockStateReader;
        this.mCompatUIConfiguration = compatUIConfiguration;
        this.mEligibleForLetterboxEducation = taskInfo.topActivityEligibleForLetterboxEducation;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void updateSurfacePosition() {
    }
}
