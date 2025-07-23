package com.android.systemui.statusbar.chips.sharetoapp.ui.view;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import com.android.systemui.R;
import com.android.systemui.mediaprojection.data.model.MediaProjectionState;
import com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel;
import com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper;
import com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper$wrapStopAction$1;
import com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class EndShareScreenToAppDialogDelegate implements SystemUIDialog.Delegate {
    public final Context context;
    public final EndMediaProjectionDialogHelper endMediaProjectionDialogHelper;
    public final ProjectionChipModel.Projecting state;
    public final Function0 stopAction;

    public EndShareScreenToAppDialogDelegate(EndMediaProjectionDialogHelper endMediaProjectionDialogHelper, Context context, Function0 function0, ProjectionChipModel.Projecting projecting) {
        this.endMediaProjectionDialogHelper = endMediaProjectionDialogHelper;
        this.context = context;
        this.stopAction = function0;
        this.state = projecting;
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void beforeCreate(Dialog dialog) {
        String string;
        View decorView;
        Intent intent;
        ComponentName component;
        String packageName;
        SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        ShareToAppChipViewModel.Companion.getClass();
        systemUIDialog.setIcon(ShareToAppChipViewModel.SHARE_TO_APP_ICON);
        systemUIDialog.setTitle(R.string.share_to_app_stop_dialog_title);
        MediaProjectionState.Projecting projecting = this.state.projectionState;
        boolean z = projecting instanceof MediaProjectionState.Projecting.SingleTask;
        EndMediaProjectionDialogHelper endMediaProjectionDialogHelper = this.endMediaProjectionDialogHelper;
        if (z) {
            endMediaProjectionDialogHelper.getClass();
            ActivityManager.RunningTaskInfo runningTaskInfo = projecting instanceof MediaProjectionState.Projecting.SingleTask ? ((MediaProjectionState.Projecting.SingleTask) projecting).task : null;
            CharSequence appName = (runningTaskInfo == null || (intent = runningTaskInfo.baseIntent) == null || (component = intent.getComponent()) == null || (packageName = component.getPackageName()) == null) ? null : endMediaProjectionDialogHelper.getAppName(packageName);
            if (appName != null) {
                string = this.context.getString(R.string.share_to_app_stop_dialog_message_single_app_specific, appName);
                string.getClass();
            } else {
                string = this.context.getString(R.string.share_to_app_stop_dialog_message_single_app_generic);
                string.getClass();
            }
        } else {
            CharSequence appName2 = endMediaProjectionDialogHelper.getAppName(projecting.getHostPackage());
            if (appName2 != null) {
                string = this.context.getString(R.string.share_to_app_stop_dialog_message_entire_screen_with_host_app, appName2);
                string.getClass();
            } else {
                string = this.context.getString(R.string.share_to_app_stop_dialog_message_entire_screen);
                string.getClass();
            }
        }
        systemUIDialog.setMessage(string);
        systemUIDialog.setNegativeButton(R.string.close_dialog_button, null);
        endMediaProjectionDialogHelper.getClass();
        systemUIDialog.setPositiveButton(R.string.share_to_app_stop_dialog_button, new EndMediaProjectionDialogHelper$wrapStopAction$1(endMediaProjectionDialogHelper, this.stopAction));
        Window window = systemUIDialog.getWindow();
        if (window == null || (decorView = window.getDecorView()) == null) {
            return;
        }
        decorView.setAccessibilityDataSensitive(1);
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        SystemUIDialog.Factory factory = this.endMediaProjectionDialogHelper.dialogFactory;
        return factory.create(this, factory.mContext);
    }
}
