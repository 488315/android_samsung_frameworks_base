package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.WindowManager;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SystemUIDialog;

public final class SubscreenNotificationDialog {
    public final SystemUIDialog mDialog;

    public SubscreenNotificationDialog(Context context, String str) {
        SystemUIDialog systemUIDialog = new SystemUIDialog(context, R.style.Theme_SystemUI_Dialog_Alert);
        this.mDialog = systemUIDialog;
        systemUIDialog.setMessage(str);
        final int i = 1;
        this.mDialog.setPositiveButton(R.string.subscreen_notification_dialog_ok, new DialogInterface.OnClickListener(this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationDialog$$ExternalSyntheticLambda2
            public final /* synthetic */ SubscreenNotificationDialog f$0;

            {
                this.f$0 = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                int i3 = i;
                SubscreenNotificationDialog subscreenNotificationDialog = this.f$0;
                switch (i3) {
                    case 0:
                        subscreenNotificationDialog.dismiss();
                        break;
                    case 1:
                        subscreenNotificationDialog.dismiss();
                        break;
                    case 2:
                        subscreenNotificationDialog.dismiss();
                        break;
                    default:
                        subscreenNotificationDialog.dismiss();
                        break;
                }
            }
        });
        if (this.mDialog.getWindow() != null) {
            this.mDialog.getWindow().setGravity(81);
        }
    }

    public final void dismiss() {
        SystemUIDialog systemUIDialog = this.mDialog;
        if (systemUIDialog == null || !systemUIDialog.isShowing()) {
            return;
        }
        this.mDialog.dismiss();
    }

    public final void show() {
        try {
            if (this.mDialog != null) {
                dismiss();
                this.mDialog.show();
            }
        } catch (WindowManager.BadTokenException unused) {
        }
    }

    public SubscreenNotificationDialog(Context context, String str, final Runnable runnable) {
        SystemUIDialog systemUIDialog = new SystemUIDialog(context, R.style.Theme_SystemUI_Dialog_Alert);
        this.mDialog = systemUIDialog;
        systemUIDialog.setMessage(str);
        final int i = 0;
        this.mDialog.setNegativeButton(R.string.subscreen_notification_smart_reply_turn_on_cancel_button, new DialogInterface.OnClickListener(this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationDialog$$ExternalSyntheticLambda2
            public final /* synthetic */ SubscreenNotificationDialog f$0;

            {
                this.f$0 = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                int i3 = i;
                SubscreenNotificationDialog subscreenNotificationDialog = this.f$0;
                switch (i3) {
                    case 0:
                        subscreenNotificationDialog.dismiss();
                        break;
                    case 1:
                        subscreenNotificationDialog.dismiss();
                        break;
                    case 2:
                        subscreenNotificationDialog.dismiss();
                        break;
                    default:
                        subscreenNotificationDialog.dismiss();
                        break;
                }
            }
        });
        final int i2 = 0;
        this.mDialog.setPositiveButton(R.string.subscreen_notification_smart_reply_turn_on_button, new DialogInterface.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationDialog$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i3) {
                int i4 = i2;
                Runnable runnable2 = runnable;
                switch (i4) {
                    case 0:
                        if (runnable2 != null) {
                            runnable2.run();
                            break;
                        }
                        break;
                    default:
                        if (runnable2 != null) {
                            runnable2.run();
                            break;
                        }
                        break;
                }
            }
        });
        if (this.mDialog.getWindow() != null) {
            this.mDialog.getWindow().setGravity(81);
        }
    }

    public SubscreenNotificationDialog(Context context, String str, String str2, final Runnable runnable) {
        this.mDialog = new SystemUIDialog(context, R.style.Theme_SystemUI_Dialog_Alert);
        if (str != null && !str.isEmpty()) {
            this.mDialog.setTitle(str);
        }
        this.mDialog.setMessage(str2);
        final int i = 2;
        this.mDialog.setNegativeButton(R.string.subscreen_online_processing_cancel, new DialogInterface.OnClickListener(this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationDialog$$ExternalSyntheticLambda2
            public final /* synthetic */ SubscreenNotificationDialog f$0;

            {
                this.f$0 = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                int i3 = i;
                SubscreenNotificationDialog subscreenNotificationDialog = this.f$0;
                switch (i3) {
                    case 0:
                        subscreenNotificationDialog.dismiss();
                        break;
                    case 1:
                        subscreenNotificationDialog.dismiss();
                        break;
                    case 2:
                        subscreenNotificationDialog.dismiss();
                        break;
                    default:
                        subscreenNotificationDialog.dismiss();
                        break;
                }
            }
        });
        final int i2 = 1;
        this.mDialog.setPositiveButton(R.string.subscreen_online_processing_settings, new DialogInterface.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationDialog$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i3) {
                int i4 = i2;
                Runnable runnable2 = runnable;
                switch (i4) {
                    case 0:
                        if (runnable2 != null) {
                            runnable2.run();
                            break;
                        }
                        break;
                    default:
                        if (runnable2 != null) {
                            runnable2.run();
                            break;
                        }
                        break;
                }
            }
        });
        if (this.mDialog.getWindow() != null) {
            this.mDialog.getWindow().setGravity(81);
        }
    }

    public SubscreenNotificationDialog(Context context, View view) {
        SystemUIDialog systemUIDialog = new SystemUIDialog(context, R.style.Theme_SystemUI_Dialog_Alert);
        this.mDialog = systemUIDialog;
        systemUIDialog.setView(view);
        final int i = 3;
        this.mDialog.setPositiveButton(R.string.subscreen_notification_dialog_ok, new DialogInterface.OnClickListener(this) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationDialog$$ExternalSyntheticLambda2
            public final /* synthetic */ SubscreenNotificationDialog f$0;

            {
                this.f$0 = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                int i3 = i;
                SubscreenNotificationDialog subscreenNotificationDialog = this.f$0;
                switch (i3) {
                    case 0:
                        subscreenNotificationDialog.dismiss();
                        break;
                    case 1:
                        subscreenNotificationDialog.dismiss();
                        break;
                    case 2:
                        subscreenNotificationDialog.dismiss();
                        break;
                    default:
                        subscreenNotificationDialog.dismiss();
                        break;
                }
            }
        });
        if (this.mDialog.getWindow() != null) {
            this.mDialog.getWindow().setGravity(81);
        }
    }

    public SubscreenNotificationDialog(Context context, View view, final Runnable runnable, final Runnable runnable2) {
        SubscreenNotificationFullscreenDialog subscreenNotificationFullscreenDialog = new SubscreenNotificationFullscreenDialog(context);
        this.mDialog = subscreenNotificationFullscreenDialog;
        subscreenNotificationFullscreenDialog.setView(view);
        this.mDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                Runnable runnable3 = runnable;
                if (runnable3 != null) {
                    runnable3.run();
                }
            }
        });
        this.mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationDialog$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                Runnable runnable3 = runnable2;
                if (runnable3 != null) {
                    runnable3.run();
                }
            }
        });
    }
}
