package com.android.systemui.user.ui.dialog;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.UserHandle;
import com.android.systemui.R;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.user.CreateUserActivity;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AddUserDialog extends SystemUIDialog {
    public final BroadcastSender broadcastSender;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final FalsingManager falsingManager;

    public AddUserDialog(final Context context, final UserHandle userHandle, final boolean z, boolean z2, FalsingManager falsingManager, BroadcastSender broadcastSender, DialogTransitionAnimator dialogTransitionAnimator) {
        super(context);
        this.falsingManager = falsingManager;
        this.broadcastSender = broadcastSender;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
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
                addUserDialog.dialogTransitionAnimator.dismissStack(addUserDialog);
                if (ActivityManager.isUserAMonkey()) {
                    return;
                }
                final BroadcastSender broadcastSender2 = AddUserDialog.this.broadcastSender;
                final Intent intent = new Intent(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
                final UserHandle userHandle2 = userHandle;
                broadcastSender2.getClass();
                broadcastSender2.sendInBackground(String.valueOf(intent), new Function0() { // from class: com.android.systemui.broadcast.BroadcastSender$$ExternalSyntheticLambda3
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        BroadcastSender.this.context.sendBroadcastAsUser(intent, userHandle2);
                        return Unit.INSTANCE;
                    }
                });
                Context context2 = context;
                boolean z3 = z;
                int i2 = CreateUserActivity.$r8$clinit;
                Intent intent2 = new Intent(context2, (Class<?>) CreateUserActivity.class);
                intent2.addFlags(335544320);
                intent2.putExtra("extra_is_keyguard_showing", z3);
                context2.startActivityAsUser(intent2, userHandle);
            }
        };
        setTitle(R.string.user_add_user_title);
        setMessage(context.getString(R.string.user_add_user_message_short) + (z2 ? context.getString(R.string.user_add_user_message_guest_remove) : ""));
        setButton(-3, context.getString(android.R.string.cancel), onClickListener);
        setButton(-1, context.getString(android.R.string.ok), onClickListener);
        SystemUIDialog.setWindowOnTop(this, z);
    }
}
