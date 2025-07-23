package com.android.wm.shell.compatui;

import android.app.TaskInfo;
import android.content.Context;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.systemui.R;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.DockStateReader;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.transition.Transitions;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class LetterboxEduWindowManager extends CompatUIWindowManagerAbstract {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final DialogAnimationController mAnimationController;
    public final CompatUIConfiguration mCompatUIConfiguration;
    public final CompatUIStatusManager mCompatUIStatusManager;
    public final int mDialogVerticalMargin;
    public final DockStateReader mDockStateReader;
    public boolean mEligibleForLetterboxEducation;
    LetterboxEduDialogLayout mLayout;
    public final Consumer mOnDismissCallback;
    public final Transitions mTransitions;
    public final int mUserId;

    public LetterboxEduWindowManager(Context context, TaskInfo taskInfo, SyncTransactionQueue syncTransactionQueue, ShellTaskOrganizer.TaskListener taskListener, DisplayLayout displayLayout, Transitions transitions, Consumer<Pair<TaskInfo, ShellTaskOrganizer.TaskListener>> consumer, DockStateReader dockStateReader, CompatUIConfiguration compatUIConfiguration, CompatUIStatusManager compatUIStatusManager) {
        this(context, taskInfo, syncTransactionQueue, taskListener, displayLayout, transitions, consumer, new DialogAnimationController(context, "LetterboxEduWindowManager"), dockStateReader, compatUIConfiguration, compatUIStatusManager);
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final View createLayout() {
        this.mLayout = (LetterboxEduDialogLayout) LayoutInflater.from(this.mContext).inflate(R.layout.letterbox_education_dialog_layout, (ViewGroup) null);
        updateDialogMargins();
        CompatUIStatusManager compatUIStatusManager = this.mCompatUIStatusManager;
        if (compatUIStatusManager.mCurrentValue != 1) {
            compatUIStatusManager.mCurrentValue = 1;
            compatUIStatusManager.mWriter.accept(1);
        }
        this.mTransitions.runOnIdle(new LetterboxEduWindowManager$$ExternalSyntheticLambda0(this, 0));
        return this.mLayout;
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x001d, code lost:
    
        if (r3.mCompatUIConfiguration.mLetterboxEduSharedPreferences.getBoolean(java.lang.String.valueOf(r3.mUserId), false) == false) goto L10;
     */
    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean eligibleToShowLayout() {
        /*
            r3 = this;
            boolean r0 = r3.mEligibleForLetterboxEducation
            r1 = 0
            if (r0 == 0) goto L37
            boolean r0 = r3.isTaskbarEduShowing()
            if (r0 != 0) goto L37
            com.android.wm.shell.compatui.LetterboxEduDialogLayout r0 = r3.mLayout
            if (r0 != 0) goto L1f
            com.android.wm.shell.compatui.CompatUIConfiguration r0 = r3.mCompatUIConfiguration
            int r2 = r3.mUserId
            android.content.SharedPreferences r0 = r0.mLetterboxEduSharedPreferences
            java.lang.String r2 = java.lang.String.valueOf(r2)
            boolean r0 = r0.getBoolean(r2, r1)
            if (r0 != 0) goto L37
        L1f:
            com.android.wm.shell.common.DockStateReader r3 = r3.mDockStateReader
            android.content.Context r3 = r3.mContext
            android.content.IntentFilter r0 = com.android.wm.shell.common.DockStateReader.DOCK_INTENT_FILTER
            r2 = 0
            android.content.Intent r3 = r3.registerReceiver(r2, r0)
            if (r3 == 0) goto L35
            java.lang.String r0 = "android.intent.extra.DOCK_STATE"
            int r3 = r3.getIntExtra(r0, r1)
            if (r3 == 0) goto L35
            goto L37
        L35:
            r3 = 1
            return r3
        L37:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.compatui.LetterboxEduWindowManager.eligibleToShowLayout():boolean");
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
            if (!this.mCompatUIConfiguration.mLetterboxEduSharedPreferences.getBoolean(String.valueOf(this.mUserId), false)) {
                return true;
            }
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
        CompatUIStatusManager compatUIStatusManager = this.mCompatUIStatusManager;
        if (compatUIStatusManager.mCurrentValue != 0) {
            compatUIStatusManager.mCurrentValue = 0;
            compatUIStatusManager.mWriter.accept(0);
        }
        super.release();
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void removeLayout() {
        this.mLayout = null;
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final boolean updateCompatInfo(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener, boolean z) {
        this.mEligibleForLetterboxEducation = taskInfo.appCompatTaskInfo.eligibleForLetterboxEducation();
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
        Rect taskStableBounds = getTaskStableBounds();
        int i = taskStableBounds.top - taskBounds.top;
        int i2 = this.mDialogVerticalMargin;
        marginLayoutParams.topMargin = i + i2;
        marginLayoutParams.bottomMargin = (taskBounds.bottom - taskStableBounds.bottom) + i2;
        view.setLayoutParams(marginLayoutParams);
    }

    public LetterboxEduWindowManager(Context context, TaskInfo taskInfo, SyncTransactionQueue syncTransactionQueue, ShellTaskOrganizer.TaskListener taskListener, DisplayLayout displayLayout, Transitions transitions, Consumer<Pair<TaskInfo, ShellTaskOrganizer.TaskListener>> consumer, DialogAnimationController dialogAnimationController, DockStateReader dockStateReader, CompatUIConfiguration compatUIConfiguration, CompatUIStatusManager compatUIStatusManager) {
        super(context, taskInfo, syncTransactionQueue, taskListener, displayLayout);
        this.mTransitions = transitions;
        this.mOnDismissCallback = consumer;
        this.mAnimationController = dialogAnimationController;
        this.mUserId = taskInfo.userId;
        this.mDialogVerticalMargin = (int) this.mContext.getResources().getDimension(R.dimen.letterbox_education_dialog_margin);
        this.mDockStateReader = dockStateReader;
        this.mCompatUIConfiguration = compatUIConfiguration;
        this.mCompatUIStatusManager = compatUIStatusManager;
        this.mEligibleForLetterboxEducation = taskInfo.appCompatTaskInfo.eligibleForLetterboxEducation();
    }

    @Override // com.android.wm.shell.compatui.CompatUIWindowManagerAbstract
    public final void updateSurfacePosition() {
    }
}
