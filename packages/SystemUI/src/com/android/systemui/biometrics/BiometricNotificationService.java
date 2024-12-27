package com.android.systemui.biometrics;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.biometrics.BiometricStateListener;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.Optional;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BiometricNotificationService implements CoreStartable {
    public final BiometricNotificationBroadcastReceiver mBroadcastReceiver;
    public final Context mContext;
    public final FaceManager mFaceManager;
    public boolean mFaceNotificationQueued;
    public final FingerprintManager mFingerprintManager;
    public boolean mFingerprintNotificationQueued;
    public final FingerprintReEnrollNotificationImpl mFingerprintReEnrollNotification;
    public boolean mFingerprintReenrollRequired;
    public final Handler mHandler;
    public boolean mIsFingerprintReenrollForced;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public NotificationChannel mNotificationChannel;
    public final NotificationManager mNotificationManager;
    public final AnonymousClass1 mKeyguardStateControllerCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.biometrics.BiometricNotificationService.1
        public boolean mIsShowing = true;

        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public final void onKeyguardShowingChanged() {
            final BiometricNotificationService biometricNotificationService = BiometricNotificationService.this;
            KeyguardStateController keyguardStateController = biometricNotificationService.mKeyguardStateController;
            if (((KeyguardStateControllerImpl) keyguardStateController).mShowing || ((KeyguardStateControllerImpl) keyguardStateController).mShowing == this.mIsShowing) {
                this.mIsShowing = ((KeyguardStateControllerImpl) keyguardStateController).mShowing;
                return;
            }
            this.mIsShowing = ((KeyguardStateControllerImpl) keyguardStateController).mShowing;
            Context context = biometricNotificationService.mContext;
            biometricNotificationService.getClass();
            if (Settings.Secure.getIntForUser(context.getContentResolver(), "face_unlock_re_enroll", 0, -2) == 1 && !biometricNotificationService.mFaceNotificationQueued) {
                biometricNotificationService.getClass();
                Log.d("BiometricNotificationService", "Face re-enroll notification queued.");
                biometricNotificationService.mFaceNotificationQueued = true;
                final String string = biometricNotificationService.mContext.getString(R.string.face_re_enroll_notification_title);
                final String string2 = biometricNotificationService.mContext.getString(R.string.biometric_re_enroll_notification_content);
                final String string3 = biometricNotificationService.mContext.getString(R.string.face_re_enroll_notification_name);
                final int i = 0;
                biometricNotificationService.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.biometrics.BiometricNotificationService$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i) {
                            case 0:
                                biometricNotificationService.showNotification("face_action_show_reenroll_dialog", string, string2, string3, 1, false);
                                break;
                            default:
                                BiometricNotificationService biometricNotificationService2 = biometricNotificationService;
                                biometricNotificationService2.showNotification("fingerprint_action_show_reenroll_dialog", string, string2, string3, 2, biometricNotificationService2.mIsFingerprintReenrollForced);
                                break;
                        }
                    }
                }, 5000L);
            }
            if (!biometricNotificationService.mFingerprintReenrollRequired || biometricNotificationService.mFingerprintNotificationQueued) {
                return;
            }
            biometricNotificationService.mFingerprintReenrollRequired = false;
            biometricNotificationService.getClass();
            Log.d("BiometricNotificationService", "Fingerprint re-enroll notification queued.");
            biometricNotificationService.mFingerprintNotificationQueued = true;
            final String string4 = biometricNotificationService.mContext.getString(R.string.fingerprint_re_enroll_notification_title);
            final String string5 = biometricNotificationService.mContext.getString(R.string.biometric_re_enroll_notification_content);
            final String string6 = biometricNotificationService.mContext.getString(R.string.fingerprint_re_enroll_notification_name);
            final int i2 = 1;
            biometricNotificationService.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.biometrics.BiometricNotificationService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i2) {
                        case 0:
                            biometricNotificationService.showNotification("face_action_show_reenroll_dialog", string4, string5, string6, 1, false);
                            break;
                        default:
                            BiometricNotificationService biometricNotificationService2 = biometricNotificationService;
                            biometricNotificationService2.showNotification("fingerprint_action_show_reenroll_dialog", string4, string5, string6, 2, biometricNotificationService2.mIsFingerprintReenrollForced);
                            break;
                    }
                }
            }, 5000L);
        }
    };
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.biometrics.BiometricNotificationService.2
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricError(int i, String str, BiometricSourceType biometricSourceType) {
            if (i == 16 && biometricSourceType == BiometricSourceType.FACE) {
                Settings.Secure.putIntForUser(BiometricNotificationService.this.mContext.getContentResolver(), "face_unlock_re_enroll", 1, -2);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricHelp(int i, String str, BiometricSourceType biometricSourceType) {
            if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                BiometricNotificationService biometricNotificationService = BiometricNotificationService.this;
                biometricNotificationService.mFingerprintReEnrollNotification.getClass();
                if (i == 12 || i == 13) {
                    biometricNotificationService.mFingerprintReenrollRequired = true;
                    biometricNotificationService.mFingerprintReEnrollNotification.getClass();
                    biometricNotificationService.mIsFingerprintReenrollForced = i == 13;
                }
            }
        }
    };
    public final AnonymousClass3 mFaceStateListener = new BiometricStateListener() { // from class: com.android.systemui.biometrics.BiometricNotificationService.3
        public final void onEnrollmentsChanged(int i, int i2, boolean z) {
            BiometricNotificationService.this.mNotificationManager.cancelAsUser("BiometricNotificationService", 1, UserHandle.CURRENT);
        }
    };
    public final AnonymousClass4 mFingerprintStateListener = new BiometricStateListener() { // from class: com.android.systemui.biometrics.BiometricNotificationService.4
        public final void onEnrollmentsChanged(int i, int i2, boolean z) {
            BiometricNotificationService.this.mNotificationManager.cancelAsUser("BiometricNotificationService", 2, UserHandle.CURRENT);
        }
    };

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.biometrics.BiometricNotificationService$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.biometrics.BiometricNotificationService$3] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.biometrics.BiometricNotificationService$4] */
    public BiometricNotificationService(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, Handler handler, NotificationManager notificationManager, BiometricNotificationBroadcastReceiver biometricNotificationBroadcastReceiver, Optional<FingerprintReEnrollNotificationImpl> optional, FingerprintManager fingerprintManager, FaceManager faceManager) {
        this.mContext = context;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        this.mHandler = handler;
        this.mNotificationManager = notificationManager;
        this.mBroadcastReceiver = biometricNotificationBroadcastReceiver;
        this.mFingerprintReEnrollNotification = optional.orElse(new FingerprintReEnrollNotificationImpl());
        this.mFingerprintManager = fingerprintManager;
        this.mFaceManager = faceManager;
    }

    public final void showNotification(String str, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, boolean z) {
        if (i == 1) {
            this.mFaceNotificationQueued = false;
        } else if (i == 2) {
            this.mFingerprintNotificationQueued = false;
        }
        if (this.mNotificationManager == null) {
            Log.e("BiometricNotificationService", "Failed to show notification " + str + ". Notification manager is null!");
            return;
        }
        Intent intent = new Intent(str);
        intent.putExtra("is_reenroll_forced", z);
        Context context = this.mContext;
        UserHandle userHandle = UserHandle.CURRENT;
        Notification build = new Notification.Builder(this.mContext, "BiometricHiPriNotificationChannel").setCategory("sys").setSmallIcon(android.R.drawable.ic_lockscreen_silent_focused).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence3).setContentIntent(PendingIntent.getBroadcastAsUser(context, 0, intent, 201326592, userHandle)).setAutoCancel(true).setLocalOnly(true).setOnlyAlertOnce(true).setVisibility(-1).build();
        this.mNotificationManager.createNotificationChannel(this.mNotificationChannel);
        this.mNotificationManager.notifyAsUser("BiometricNotificationService", i, build, userHandle);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).addCallback(this.mKeyguardStateControllerCallback);
        this.mNotificationChannel = new NotificationChannel("BiometricHiPriNotificationChannel", " Biometric Unlock", 4);
        this.mContext.registerReceiver(this.mBroadcastReceiver, KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("fingerprint_action_show_reenroll_dialog", "face_action_show_reenroll_dialog"), 2);
        FingerprintManager fingerprintManager = this.mFingerprintManager;
        if (fingerprintManager != null) {
            fingerprintManager.registerBiometricStateListener(this.mFingerprintStateListener);
        }
        FaceManager faceManager = this.mFaceManager;
        if (faceManager != null) {
            faceManager.registerBiometricStateListener(this.mFaceStateListener);
        }
        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "face_unlock_re_enroll", 0, -2);
    }
}
