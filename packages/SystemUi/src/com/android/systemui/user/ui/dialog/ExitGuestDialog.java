package com.android.systemui.user.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import com.android.systemui.R;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ExitGuestDialog extends SystemUIDialog {
    public final DialogLaunchAnimator dialogLaunchAnimator;
    public final FalsingManager falsingManager;
    public final int guestUserId;
    public final boolean isGuestEphemeral;
    public final OnExitGuestUserListener onExitGuestUserListener;
    public final int targetUserId;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnExitGuestUserListener {
    }

    public ExitGuestDialog(Context context, int i, boolean z, int i2, boolean z2, FalsingManager falsingManager, DialogLaunchAnimator dialogLaunchAnimator, OnExitGuestUserListener onExitGuestUserListener) {
        super(context, 2132018528);
        this.guestUserId = i;
        this.isGuestEphemeral = z;
        this.targetUserId = i2;
        this.falsingManager = falsingManager;
        this.dialogLaunchAnimator = dialogLaunchAnimator;
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
                    } else {
                        if (i3 != -1) {
                            return;
                        }
                        exitGuestDialog.dialogLaunchAnimator.dismissStack(exitGuestDialog);
                        ExitGuestDialog exitGuestDialog2 = ExitGuestDialog.this;
                        ((C3566x48b13c6a) exitGuestDialog2.onExitGuestUserListener).onExitGuestUser(exitGuestDialog2.guestUserId, exitGuestDialog2.targetUserId, false);
                        return;
                    }
                }
                if (i3 == -3) {
                    exitGuestDialog.cancel();
                    return;
                }
                if (i3 == -2) {
                    exitGuestDialog.dialogLaunchAnimator.dismissStack(exitGuestDialog);
                    ExitGuestDialog exitGuestDialog3 = ExitGuestDialog.this;
                    ((C3566x48b13c6a) exitGuestDialog3.onExitGuestUserListener).onExitGuestUser(exitGuestDialog3.guestUserId, exitGuestDialog3.targetUserId, true);
                } else {
                    if (i3 != -1) {
                        return;
                    }
                    exitGuestDialog.dialogLaunchAnimator.dismissStack(exitGuestDialog);
                    ExitGuestDialog exitGuestDialog4 = ExitGuestDialog.this;
                    ((C3566x48b13c6a) exitGuestDialog4.onExitGuestUserListener).onExitGuestUser(exitGuestDialog4.guestUserId, exitGuestDialog4.targetUserId, false);
                }
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
