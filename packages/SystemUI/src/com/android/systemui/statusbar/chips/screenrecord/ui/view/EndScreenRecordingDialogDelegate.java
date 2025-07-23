package com.android.systemui.statusbar.chips.screenrecord.ui.view;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import com.android.systemui.R;
import com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper;
import com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper$wrapStopAction$1;
import com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class EndScreenRecordingDialogDelegate implements SystemUIDialog.Delegate {
    public final Context context;
    public final EndMediaProjectionDialogHelper endMediaProjectionDialogHelper;
    public final ActivityManager.RunningTaskInfo recordedTask;
    public final Function0 stopAction;

    public EndScreenRecordingDialogDelegate(EndMediaProjectionDialogHelper endMediaProjectionDialogHelper, Context context, Function0 function0, ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.endMediaProjectionDialogHelper = endMediaProjectionDialogHelper;
        this.context = context;
        this.stopAction = function0;
        this.recordedTask = runningTaskInfo;
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void beforeCreate(Dialog dialog) {
        View decorView;
        Intent intent;
        ComponentName component;
        String packageName;
        SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.recordedTask;
        EndMediaProjectionDialogHelper endMediaProjectionDialogHelper = this.endMediaProjectionDialogHelper;
        endMediaProjectionDialogHelper.getClass();
        CharSequence appName = (runningTaskInfo == null || (intent = runningTaskInfo.baseIntent) == null || (component = intent.getComponent()) == null || (packageName = component.getPackageName()) == null) ? null : endMediaProjectionDialogHelper.getAppName(packageName);
        String string = appName != null ? this.context.getString(R.string.screenrecord_stop_dialog_message_specific_app, appName) : this.context.getString(R.string.screenrecord_stop_dialog_message);
        string.getClass();
        ScreenRecordChipViewModel.Companion.getClass();
        systemUIDialog.setIcon(ScreenRecordChipViewModel.ICON);
        systemUIDialog.setTitle(R.string.screenrecord_stop_dialog_title);
        systemUIDialog.setMessage(string);
        systemUIDialog.setNegativeButton(R.string.close_dialog_button, null);
        systemUIDialog.setPositiveButton(R.string.screenrecord_stop_dialog_button, new EndMediaProjectionDialogHelper$wrapStopAction$1(endMediaProjectionDialogHelper, this.stopAction));
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
