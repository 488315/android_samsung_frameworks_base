package com.android.server.biometrics;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.util.Slog;

import com.android.server.biometrics.sensors.BiometricNotificationUtils;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

public final class BiometricDanglingReceiver extends BroadcastReceiver {
    public BiometricDanglingReceiver(Context context, int i) {
        IntentFilter intentFilter = new IntentFilter();
        if (i == 1) {
            intentFilter.addAction("action_fingerprint_re_enroll_launch");
            intentFilter.addAction("action_fingerprint_re_enroll_dismiss");
        } else if (i == 4) {
            intentFilter.addAction("action_face_re_enroll_launch");
            intentFilter.addAction("action_face_re_enroll_dismiss");
        }
        context.registerReceiver(this, intentFilter, 4);
    }

    public static void launchBiometricEnrollActivity(Context context, String str) {
        context.sendBroadcast(
                new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS").setFlags(268435456));
        Intent intent = new Intent(str);
        intent.setPackage(KnoxCustomManagerService.SETTING_PKG_NAME);
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Slog.d("BiometricDanglingReceiver", "Received: " + intent.getAction());
        if ("action_fingerprint_re_enroll_launch".equals(intent.getAction())) {
            launchBiometricEnrollActivity(context, "android.settings.FINGERPRINT_ENROLL");
            Intent intent2 = BiometricNotificationUtils.DISMISS_FRR_INTENT;
            ((NotificationManager) context.getSystemService(NotificationManager.class))
                    .cancelAsUser("FingerprintReEnroll", 1, UserHandle.CURRENT);
        } else if ("action_fingerprint_re_enroll_dismiss".equals(intent.getAction())) {
            Intent intent3 = BiometricNotificationUtils.DISMISS_FRR_INTENT;
            ((NotificationManager) context.getSystemService(NotificationManager.class))
                    .cancelAsUser("FingerprintReEnroll", 1, UserHandle.CURRENT);
        } else if ("action_face_re_enroll_launch".equals(intent.getAction())) {
            launchBiometricEnrollActivity(context, "android.settings.FACE_SETTINGS");
            Intent intent4 = BiometricNotificationUtils.DISMISS_FRR_INTENT;
            ((NotificationManager) context.getSystemService(NotificationManager.class))
                    .cancelAsUser("FaceReEnroll", 1, UserHandle.CURRENT);
        } else if ("action_face_re_enroll_dismiss".equals(intent.getAction())) {
            Intent intent5 = BiometricNotificationUtils.DISMISS_FRR_INTENT;
            ((NotificationManager) context.getSystemService(NotificationManager.class))
                    .cancelAsUser("FaceReEnroll", 1, UserHandle.CURRENT);
        }
        context.unregisterReceiver(this);
    }
}
