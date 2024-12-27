package com.android.systemui.statusbar.chips.screenrecord.ui.view;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.DialogInterface;
import com.android.systemui.R;
import com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper;
import com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class EndScreenRecordingDialogDelegate implements SystemUIDialog.Delegate {
    public final EndMediaProjectionDialogHelper endMediaProjectionDialogHelper;
    public final ActivityManager.RunningTaskInfo recordedTask;
    public final Function0 stopAction;

    public EndScreenRecordingDialogDelegate(EndMediaProjectionDialogHelper endMediaProjectionDialogHelper, Function0 function0, ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.endMediaProjectionDialogHelper = endMediaProjectionDialogHelper;
        this.stopAction = function0;
        this.recordedTask = runningTaskInfo;
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void beforeCreate(Dialog dialog) {
        SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        ScreenRecordChipViewModel.Companion.getClass();
        systemUIDialog.setIcon(ScreenRecordChipViewModel.ICON);
        systemUIDialog.setTitle(R.string.screenrecord_stop_dialog_title);
        systemUIDialog.setMessage(this.endMediaProjectionDialogHelper.getDialogMessage(this.recordedTask, R.string.screenrecord_stop_dialog_message, R.string.screenrecord_stop_dialog_message_specific_app));
        systemUIDialog.setNegativeButton(R.string.close_dialog_button, null);
        systemUIDialog.setPositiveButton(R.string.screenrecord_stop_dialog_button, new DialogInterface.OnClickListener() { // from class: com.android.systemui.statusbar.chips.screenrecord.ui.view.EndScreenRecordingDialogDelegate$beforeCreate$1$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EndScreenRecordingDialogDelegate.this.stopAction.invoke();
            }
        });
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        SystemUIDialog.Factory factory = this.endMediaProjectionDialogHelper.dialogFactory;
        return factory.create(this, factory.mContext);
    }
}
