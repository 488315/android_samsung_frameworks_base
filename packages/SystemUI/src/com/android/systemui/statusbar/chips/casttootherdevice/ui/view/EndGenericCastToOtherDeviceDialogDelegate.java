package com.android.systemui.statusbar.chips.casttootherdevice.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import com.android.systemui.R;
import com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel;
import com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper;
import com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper$wrapStopAction$1;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final class EndGenericCastToOtherDeviceDialogDelegate implements SystemUIDialog.Delegate {
    public final Context context;
    public final String deviceName;
    public final EndMediaProjectionDialogHelper endMediaProjectionDialogHelper;
    public final Function0 stopAction;

    public EndGenericCastToOtherDeviceDialogDelegate(EndMediaProjectionDialogHelper endMediaProjectionDialogHelper, Context context, String str, Function0 function0) {
        this.endMediaProjectionDialogHelper = endMediaProjectionDialogHelper;
        this.context = context;
        this.deviceName = str;
        this.stopAction = function0;
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void beforeCreate(Dialog dialog) {
        View decorView;
        SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        String str = this.deviceName;
        String string = str != null ? this.context.getString(R.string.cast_to_other_device_stop_dialog_message_generic_with_device, str) : this.context.getString(R.string.cast_to_other_device_stop_dialog_message_generic);
        string.getClass();
        CastToOtherDeviceChipViewModel.Companion.getClass();
        systemUIDialog.setIcon(CastToOtherDeviceChipViewModel.CAST_TO_OTHER_DEVICE_ICON);
        systemUIDialog.setTitle(R.string.cast_to_other_device_stop_dialog_title);
        systemUIDialog.setMessage(string);
        systemUIDialog.setNegativeButton(R.string.close_dialog_button, null);
        EndMediaProjectionDialogHelper endMediaProjectionDialogHelper = this.endMediaProjectionDialogHelper;
        endMediaProjectionDialogHelper.getClass();
        systemUIDialog.setPositiveButton(R.string.cast_to_other_device_stop_dialog_button, new EndMediaProjectionDialogHelper$wrapStopAction$1(endMediaProjectionDialogHelper, this.stopAction));
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
