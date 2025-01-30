package com.android.server.wm;

import android.R;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.p005os.IInstalld;
import android.provider.Settings;
import android.util.Slog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import com.android.server.wm.DexActivityStartInterceptor;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public class DexRestartAppDialog extends AlertDialog implements DialogInterface.OnClickListener {
    public final ActivityTaskManagerService mAtmService;
    public final Context mContext;
    public final DexController mDexController;
    public final DexActivityStartInterceptor.DexRestartAppDialogController mDialogController;
    public final CheckBox mDoNotShowAgainCheckBox;
    public final DexRestartAppInfo mInfo;
    public final BroadcastReceiver mReceiver;
    public boolean mRun;
    public final int mTargetDisplayId;

    public DexRestartAppDialog(Context context, ActivityTaskManagerService activityTaskManagerService, DexController dexController, DexActivityStartInterceptor.DexRestartAppDialogController dexRestartAppDialogController, DexRestartAppInfo dexRestartAppInfo, int i) {
        super(context, 16975230);
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.server.wm.DexRestartAppDialog.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                    if (DexRestartAppDialog.this.mTargetDisplayId == intent.getIntExtra("displayId", 0)) {
                        Slog.d("DexRestartAppDialog", "Finish dex restart app dialog reason = " + intent.getStringExtra("reason"));
                        DexRestartAppDialog.this.cancel();
                    }
                }
            }
        };
        context.assertRuntimeOverlayThemable();
        this.mAtmService = activityTaskManagerService;
        this.mDexController = dexController;
        Context context2 = getContext();
        this.mContext = context2;
        this.mDialogController = dexRestartAppDialogController;
        this.mInfo = dexRestartAppInfo;
        this.mTargetDisplayId = i;
        setCancelable(true);
        String format = String.format(context.getString(R.string.fp_power_button_enrollment_title), dexRestartAppInfo.getAppName(context, activityTaskManagerService.mTaskSupervisor));
        setTitle(format);
        View inflate = ((LayoutInflater) context2.getSystemService("layout_inflater")).inflate(R.layout.floating_popup_container, (ViewGroup) null);
        this.mDoNotShowAgainCheckBox = (CheckBox) inflate.findViewById(R.id.email);
        setView(inflate);
        setButton(-1, context.getText(R.string.fp_power_button_enrollment_button_text), this);
        setButton(-2, context.getText(R.string.cancel), this);
        getWindow().setType(2003);
        getWindow().setFlags(IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES, IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES);
        getWindow().setGravity(81);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.setTitle(format);
        attributes.privateFlags |= 272;
        getWindow().setAttributes(attributes);
    }

    @Override // android.app.Dialog
    public void onStart() {
        if (this.mTargetDisplayId == 2) {
            this.mContext.getDisplay().getSize(new Point());
            getWindow().getAttributes().width = (int) (r0.x * 0.25f);
        }
        super.onStart();
        this.mContext.registerReceiver(this.mReceiver, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
    }

    @Override // android.app.Dialog
    public void onStop() {
        super.onStop();
        this.mContext.unregisterReceiver(this.mReceiver);
        this.mDialogController.setRestartDialogShowRequested(false);
        if (this.mRun) {
            return;
        }
        WindowManagerGlobalLock globalLock = this.mAtmService.getGlobalLock();
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (globalLock) {
            try {
                dismissSplitIfNeeded();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == -2) {
            Slog.d("DexRestartAppDialog", "onClick Negative button.");
            return;
        }
        if (i != -1) {
            return;
        }
        boolean isChecked = this.mDoNotShowAgainCheckBox.isChecked();
        if (isChecked) {
            Settings.System.putInt(this.mAtmService.mContext.getContentResolver(), "display_chooser_do_not_show_again", 1);
        }
        Slog.d("DexRestartAppDialog", "onClick Positive button. Do_not_show_again=" + isChecked);
        this.mDexController.scheduleMoveTasksBackAndStartPendingActivity(this.mInfo, this.mTargetDisplayId);
        this.mDialogController.setRestartDialogShowRequested(false);
        this.mRun = true;
    }

    public final void dismissSplitIfNeeded() {
        if (this.mTargetDisplayId == 0) {
            TaskDisplayArea defaultTaskDisplayArea = this.mAtmService.mRootWindowContainer.getDefaultTaskDisplayArea();
            Task fromWindowContainerToken = Task.fromWindowContainerToken(this.mInfo.mOptions.getLaunchRootTask());
            if (fromWindowContainerToken == null || !fromWindowContainerToken.inSplitScreenWindowingMode() || fromWindowContainerToken.hasChild()) {
                return;
            }
            if (defaultTaskDisplayArea.isSplitScreenModeActivated() || defaultTaskDisplayArea.hasChildTaskInSideStage()) {
                int stageType = fromWindowContainerToken.getStageType();
                if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageType == 4) {
                    return;
                }
                defaultTaskDisplayArea.onStageSplitScreenDismissed(defaultTaskDisplayArea.getTopRootTaskInStageType(stageType == 1 ? 2 : 1).getTopMostTask());
            }
        }
    }
}
