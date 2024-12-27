package com.android.systemui.user.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import com.android.systemui.R;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ExitGuestDialog extends SystemUIDialog {
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final FalsingManager falsingManager;
    public final int guestUserId;
    public final boolean isGuestEphemeral;
    public final OnExitGuestUserListener onExitGuestUserListener;
    public final int targetUserId;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface OnExitGuestUserListener {
    }

    public ExitGuestDialog(Context context, int i, boolean z, int i2, boolean z2, FalsingManager falsingManager, DialogTransitionAnimator dialogTransitionAnimator, OnExitGuestUserListener onExitGuestUserListener) {
        super(context, R.style.Theme_SystemUI_Dialog_Alert);
        this.guestUserId = i;
        this.isGuestEphemeral = z;
        this.targetUserId = i2;
        this.falsingManager = falsingManager;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.onExitGuestUserListener = onExitGuestUserListener;
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.android.systemui.user.ui.dialog.ExitGuestDialog$onClickListener$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i3) {
                if (ExitGuestDialog.this.falsingManager.isFalseTap(i3 == -2 ? 0 : 2)) {
                    return;
                }
                ExitGuestDialog exitGuestDialog = ExitGuestDialog.this;
                if (exitGuestDialog.isGuestEphemeral) {
                    if (i3 == -2) {
                        exitGuestDialog.cancel();
                        return;
                    }
                    if (i3 != -1) {
                        return;
                    }
                    exitGuestDialog.dialogTransitionAnimator.dismissStack(exitGuestDialog);
                    ExitGuestDialog exitGuestDialog2 = ExitGuestDialog.this;
                    ((UserSwitcherDialogCoordinator$sam$com_android_systemui_user_ui_dialog_ExitGuestDialog_OnExitGuestUserListener$0) exitGuestDialog2.onExitGuestUserListener).function.invoke(Integer.valueOf(exitGuestDialog2.guestUserId), Integer.valueOf(exitGuestDialog2.targetUserId), Boolean.FALSE);
                    return;
                }
                if (i3 == -3) {
                    exitGuestDialog.cancel();
                    return;
                }
                if (i3 == -2) {
                    exitGuestDialog.dialogTransitionAnimator.dismissStack(exitGuestDialog);
                    ExitGuestDialog exitGuestDialog3 = ExitGuestDialog.this;
                    ((UserSwitcherDialogCoordinator$sam$com_android_systemui_user_ui_dialog_ExitGuestDialog_OnExitGuestUserListener$0) exitGuestDialog3.onExitGuestUserListener).function.invoke(Integer.valueOf(exitGuestDialog3.guestUserId), Integer.valueOf(exitGuestDialog3.targetUserId), Boolean.TRUE);
                    return;
                }
                if (i3 != -1) {
                    return;
                }
                exitGuestDialog.dialogTransitionAnimator.dismissStack(exitGuestDialog);
                ExitGuestDialog exitGuestDialog4 = ExitGuestDialog.this;
                ((UserSwitcherDialogCoordinator$sam$com_android_systemui_user_ui_dialog_ExitGuestDialog_OnExitGuestUserListener$0) exitGuestDialog4.onExitGuestUserListener).function.invoke(Integer.valueOf(exitGuestDialog4.guestUserId), Integer.valueOf(exitGuestDialog4.targetUserId), Boolean.FALSE);
            }
        };
        if (z) {
            setTitle(context.getString(R.string.guest_exit_dialog_title));
            setMessage(context.getString(R.string.guest_exit_dialog_message));
            setButton(-3, context.getString(android.R.string.cancel), onClickListener);
            setButton(-1, context.getString(R.string.guest_exit_dialog_button), onClickListener);
        } else {
            setTitle(context.getString(R.string.guest_exit_dialog_title_non_ephemeral));
            setMessage(context.getString(R.string.guest_exit_dialog_message_non_ephemeral));
            setButton(-3, context.getString(android.R.string.cancel), onClickListener);
            setButton(-2, context.getString(R.string.guest_exit_clear_data_button), onClickListener);
            setButton(-1, context.getString(R.string.guest_exit_save_data_button), onClickListener);
        }
        SystemUIDialog.setWindowOnTop(this, z2);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        if (window != null) {
            window.setGravity(81);
        }
    }
}
