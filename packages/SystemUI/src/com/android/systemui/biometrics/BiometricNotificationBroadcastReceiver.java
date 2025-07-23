package com.android.systemui.biometrics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.BiometricSourceType;
import com.android.systemui.biometrics.BiometricNotificationDialogFactory;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class BiometricNotificationBroadcastReceiver extends BroadcastReceiver {
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
            int userId = this.mContext.getUserId();
            final Context context2 = this.mContext;
            Objects.requireNonNull(context2);
            biometricNotificationDialogFactory.createReenrollDialog(userId, new BiometricNotificationDialogFactory.ActivityStarter() { // from class: com.android.systemui.biometrics.BiometricNotificationBroadcastReceiver$$ExternalSyntheticLambda0
                @Override // com.android.systemui.biometrics.BiometricNotificationDialogFactory.ActivityStarter
                public final void startActivity(Intent intent2) {
                    context2.startActivity(intent2);
                }
            }, BiometricSourceType.FINGERPRINT, intent.getBooleanExtra("is_reenroll_forced", false)).show();
            return;
        }
        if (action.equals("face_action_show_reenroll_dialog")) {
            BiometricNotificationDialogFactory biometricNotificationDialogFactory2 = this.mNotificationDialogFactory;
            int userId2 = this.mContext.getUserId();
            final Context context3 = this.mContext;
            Objects.requireNonNull(context3);
            biometricNotificationDialogFactory2.createReenrollDialog(userId2, new BiometricNotificationDialogFactory.ActivityStarter() { // from class: com.android.systemui.biometrics.BiometricNotificationBroadcastReceiver$$ExternalSyntheticLambda0
                @Override // com.android.systemui.biometrics.BiometricNotificationDialogFactory.ActivityStarter
                public final void startActivity(Intent intent2) {
                    context3.startActivity(intent2);
                }
            }, BiometricSourceType.FACE, false).show();
        }
    }
}
