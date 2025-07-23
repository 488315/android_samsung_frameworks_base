package com.android.systemui.statusbar.chips.sharetoapp.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import com.android.systemui.R;
import com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper;
import com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper$wrapStopAction$1;
import com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class EndGenericShareToAppDialogDelegate implements SystemUIDialog.Delegate {
    public final Context context;
    public final EndMediaProjectionDialogHelper endMediaProjectionDialogHelper;
    public final Function0 stopAction;

    public EndGenericShareToAppDialogDelegate(EndMediaProjectionDialogHelper endMediaProjectionDialogHelper, Context context, Function0 function0) {
        this.endMediaProjectionDialogHelper = endMediaProjectionDialogHelper;
        this.context = context;
        this.stopAction = function0;
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void beforeCreate(Dialog dialog) {
        View decorView;
        SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        String string = this.context.getString(R.string.share_to_app_stop_dialog_message_generic);
        ShareToAppChipViewModel.Companion.getClass();
        systemUIDialog.setIcon(ShareToAppChipViewModel.SHARE_TO_APP_ICON);
        systemUIDialog.setTitle(R.string.share_to_app_stop_dialog_title_generic);
        systemUIDialog.setMessage(string);
        systemUIDialog.setNegativeButton(R.string.close_dialog_button, null);
        EndMediaProjectionDialogHelper endMediaProjectionDialogHelper = this.endMediaProjectionDialogHelper;
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
