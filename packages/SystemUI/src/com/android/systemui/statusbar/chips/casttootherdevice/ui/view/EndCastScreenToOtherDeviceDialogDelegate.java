package com.android.systemui.statusbar.chips.casttootherdevice.ui.view;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import com.android.systemui.R;
import com.android.systemui.mediaprojection.data.model.MediaProjectionState;
import com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel;
import com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel;
import com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper;
import com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper$wrapStopAction$1;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class EndCastScreenToOtherDeviceDialogDelegate implements SystemUIDialog.Delegate {
    public final Context context;
    public final EndMediaProjectionDialogHelper endMediaProjectionDialogHelper;
    public final ProjectionChipModel.Projecting state;
    public final Function0 stopAction;

    public EndCastScreenToOtherDeviceDialogDelegate(EndMediaProjectionDialogHelper endMediaProjectionDialogHelper, Context context, Function0 function0, ProjectionChipModel.Projecting projecting) {
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
        CastToOtherDeviceChipViewModel.Companion.getClass();
        systemUIDialog.setIcon(CastToOtherDeviceChipViewModel.CAST_TO_OTHER_DEVICE_ICON);
        systemUIDialog.setTitle(R.string.cast_to_other_device_stop_dialog_title);
        ProjectionChipModel.Projecting projecting = this.state;
        String hostDeviceName = projecting.projectionState.getHostDeviceName();
        MediaProjectionState.Projecting projecting2 = projecting.projectionState;
        boolean z = projecting2 instanceof MediaProjectionState.Projecting.SingleTask;
        EndMediaProjectionDialogHelper endMediaProjectionDialogHelper = this.endMediaProjectionDialogHelper;
        if (z) {
            endMediaProjectionDialogHelper.getClass();
            ActivityManager.RunningTaskInfo runningTaskInfo = projecting2 instanceof MediaProjectionState.Projecting.SingleTask ? ((MediaProjectionState.Projecting.SingleTask) projecting2).task : null;
            CharSequence appName = (runningTaskInfo == null || (intent = runningTaskInfo.baseIntent) == null || (component = intent.getComponent()) == null || (packageName = component.getPackageName()) == null) ? null : endMediaProjectionDialogHelper.getAppName(packageName);
            if (appName != null && hostDeviceName != null) {
                string = this.context.getString(R.string.cast_to_other_device_stop_dialog_message_specific_app_with_device, appName, hostDeviceName);
                string.getClass();
            } else if (appName != null) {
                string = this.context.getString(R.string.cast_to_other_device_stop_dialog_message_specific_app, appName);
                string.getClass();
            } else if (hostDeviceName != null) {
                string = this.context.getString(R.string.cast_to_other_device_stop_dialog_message_generic_with_device, hostDeviceName);
                string.getClass();
            } else {
                string = this.context.getString(R.string.cast_to_other_device_stop_dialog_message_generic);
                string.getClass();
            }
        } else if (hostDeviceName != null) {
            string = this.context.getString(R.string.cast_to_other_device_stop_dialog_message_entire_screen_with_device, hostDeviceName);
            string.getClass();
        } else {
            string = this.context.getString(R.string.cast_to_other_device_stop_dialog_message_entire_screen);
            string.getClass();
        }
        systemUIDialog.setMessage(string);
        systemUIDialog.setNegativeButton(R.string.close_dialog_button, null);
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
