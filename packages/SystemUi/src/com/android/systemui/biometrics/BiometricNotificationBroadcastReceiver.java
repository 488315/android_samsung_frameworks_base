package com.android.systemui.biometrics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.BiometricSourceType;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BiometricNotificationBroadcastReceiver extends BroadcastReceiver {
    public final Context mContext;
    public final BiometricNotificationDialogFactory mNotificationDialogFactory;

    public BiometricNotificationBroadcastReceiver(Context context, BiometricNotificationDialogFactory biometricNotificationDialogFactory) {
        this.mContext = context;
        this.mNotificationDialogFactory = biometricNotificationDialogFactory;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        action.getClass();
        if (action.equals("fingerprint_action_show_reenroll_dialog")) {
            BiometricNotificationDialogFactory biometricNotificationDialogFactory = this.mNotificationDialogFactory;
            Context context2 = this.mContext;
            SystemUIDialog systemUIDialog = new SystemUIDialog(this.mContext);
            BiometricSourceType biometricSourceType = BiometricSourceType.FINGERPRINT;
            biometricNotificationDialogFactory.getClass();
            BiometricNotificationDialogFactory.createReenrollDialog(context2, systemUIDialog, biometricSourceType);
            systemUIDialog.show();
            return;
        }
        if (action.equals("face_action_show_reenroll_dialog")) {
            BiometricNotificationDialogFactory biometricNotificationDialogFactory2 = this.mNotificationDialogFactory;
            Context context3 = this.mContext;
            SystemUIDialog systemUIDialog2 = new SystemUIDialog(this.mContext);
            BiometricSourceType biometricSourceType2 = BiometricSourceType.FACE;
            biometricNotificationDialogFactory2.getClass();
            BiometricNotificationDialogFactory.createReenrollDialog(context3, systemUIDialog2, biometricSourceType2);
            systemUIDialog2.show();
        }
    }
}
