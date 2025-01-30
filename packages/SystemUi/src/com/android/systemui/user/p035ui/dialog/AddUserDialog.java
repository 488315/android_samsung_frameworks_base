package com.android.systemui.user.p035ui.dialog;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.UserHandle;
import com.android.systemui.R;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.user.CreateUserActivity;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AddUserDialog extends SystemUIDialog {
    public final BroadcastSender broadcastSender;
    public final DialogLaunchAnimator dialogLaunchAnimator;
    public final FalsingManager falsingManager;

    public AddUserDialog(final Context context, final UserHandle userHandle, final boolean z, boolean z2, FalsingManager falsingManager, BroadcastSender broadcastSender, DialogLaunchAnimator dialogLaunchAnimator) {
        super(context);
        this.falsingManager = falsingManager;
        this.broadcastSender = broadcastSender;
        this.dialogLaunchAnimator = dialogLaunchAnimator;
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.android.systemui.user.ui.dialog.AddUserDialog$onClickListener$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                if (AddUserDialog.this.falsingManager.isFalseTap(i == -2 ? 0 : 2)) {
                    return;
                }
                if (i == -3) {
                    AddUserDialog.this.cancel();
                    return;
                }
                AddUserDialog addUserDialog = AddUserDialog.this;
                addUserDialog.dialogLaunchAnimator.dismissStack(addUserDialog);
                if (ActivityManager.isUserAMonkey()) {
                    return;
                }
                AddUserDialog.this.broadcastSender.sendBroadcastAsUser(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"), userHandle);
                Context context2 = context;
                boolean z3 = z;
                int i2 = CreateUserActivity.$r8$clinit;
                Intent intent = new Intent(context2, (Class<?>) CreateUserActivity.class);
                intent.addFlags(335544320);
                intent.putExtra("extra_is_keyguard_showing", z3);
                context2.startActivityAsUser(intent, userHandle);
            }
        };
        setTitle(R.string.user_add_user_title);
        setMessage(context.getString(R.string.user_add_user_message_short) + (z2 ? context.getString(R.string.user_add_user_message_guest_remove) : ""));
        setButton(-3, context.getString(android.R.string.cancel), onClickListener);
        setButton(-1, context.getString(android.R.string.ok), onClickListener);
        SystemUIDialog.setWindowOnTop(this, z);
    }
}
