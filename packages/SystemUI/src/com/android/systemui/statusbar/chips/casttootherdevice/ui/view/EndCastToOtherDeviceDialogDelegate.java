package com.android.systemui.statusbar.chips.casttootherdevice.ui.view;

import android.app.Dialog;
import android.content.DialogInterface;
import com.android.systemui.R;
import com.android.systemui.mediaprojection.data.model.MediaProjectionState;
import com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel;
import com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel;
import com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class EndCastToOtherDeviceDialogDelegate implements SystemUIDialog.Delegate {
    public final EndMediaProjectionDialogHelper endMediaProjectionDialogHelper;
    public final ProjectionChipModel.Projecting state;
    public final Function0 stopAction;

    public EndCastToOtherDeviceDialogDelegate(EndMediaProjectionDialogHelper endMediaProjectionDialogHelper, Function0 function0, ProjectionChipModel.Projecting projecting) {
        this.endMediaProjectionDialogHelper = endMediaProjectionDialogHelper;
        this.stopAction = function0;
        this.state = projecting;
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void beforeCreate(Dialog dialog) {
        SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        CastToOtherDeviceChipViewModel.Companion.getClass();
        systemUIDialog.setIcon(CastToOtherDeviceChipViewModel.CAST_TO_OTHER_DEVICE_ICON);
        systemUIDialog.setTitle(R.string.cast_to_other_device_stop_dialog_title);
        MediaProjectionState.Projecting projecting = this.state.projectionState;
        EndMediaProjectionDialogHelper endMediaProjectionDialogHelper = this.endMediaProjectionDialogHelper;
        endMediaProjectionDialogHelper.getClass();
        systemUIDialog.setMessage(endMediaProjectionDialogHelper.getDialogMessage(projecting instanceof MediaProjectionState.Projecting.SingleTask ? ((MediaProjectionState.Projecting.SingleTask) projecting).task : null, R.string.cast_to_other_device_stop_dialog_message, R.string.cast_to_other_device_stop_dialog_message_specific_app));
        systemUIDialog.setNegativeButton(R.string.close_dialog_button, null);
        systemUIDialog.setPositiveButton(R.string.cast_to_other_device_stop_dialog_button, new DialogInterface.OnClickListener() { // from class: com.android.systemui.statusbar.chips.casttootherdevice.ui.view.EndCastToOtherDeviceDialogDelegate$beforeCreate$1$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EndCastToOtherDeviceDialogDelegate.this.stopAction.invoke();
            }
        });
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        SystemUIDialog.Factory factory = this.endMediaProjectionDialogHelper.dialogFactory;
        return factory.create(this, factory.mContext);
    }
}
