package com.android.systemui.biometrics;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BiometricNotificationService implements CoreStartable {
    public final BiometricNotificationBroadcastReceiver mBroadcastReceiver;
    public final Context mContext;
    public boolean mFaceNotificationQueued;
    public boolean mFingerprintNotificationQueued;
    public final FingerprintReEnrollNotificationImpl mFingerprintReEnrollNotification;
    public boolean mFingerprintReenrollRequired;
    public final Handler mHandler;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public NotificationChannel mNotificationChannel;
    public final NotificationManager mNotificationManager;
    public final C10641 mKeyguardStateControllerCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.biometrics.BiometricNotificationService.1
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
            if ((Settings.Secure.getIntForUser(context.getContentResolver(), "face_unlock_re_enroll", 0, -2) == 1) && !biometricNotificationService.mFaceNotificationQueued) {
                biometricNotificationService.mFaceNotificationQueued = true;
                Context context2 = biometricNotificationService.mContext;
                final String string = context2.getString(R.string.face_re_enroll_notification_title);
                final String string2 = context2.getString(R.string.biometric_re_enroll_notification_content);
                final String string3 = context2.getString(R.string.face_re_enroll_notification_name);
                final int i = 0;
                biometricNotificationService.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.biometrics.BiometricNotificationService$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i) {
                            case 0:
                                biometricNotificationService.showNotification("face_action_show_reenroll_dialog", string, string2, string3, 1);
                                break;
                            default:
                                biometricNotificationService.showNotification("fingerprint_action_show_reenroll_dialog", string, string2, string3, 2);
                                break;
                        }
                    }
                }, 5000L);
            }
            if (!biometricNotificationService.mFingerprintReenrollRequired || biometricNotificationService.mFingerprintNotificationQueued) {
                return;
            }
            biometricNotificationService.mFingerprintReenrollRequired = false;
            biometricNotificationService.mFingerprintNotificationQueued = true;
            Context context3 = biometricNotificationService.mContext;
            final String string4 = context3.getString(R.string.fingerprint_re_enroll_notification_title);
            final String string5 = context3.getString(R.string.biometric_re_enroll_notification_content);
            final String string6 = context3.getString(R.string.fingerprint_re_enroll_notification_name);
            final int i2 = 1;
            biometricNotificationService.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.biometrics.BiometricNotificationService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i2) {
                        case 0:
                            biometricNotificationService.showNotification("face_action_show_reenroll_dialog", string4, string5, string6, 1);
                            break;
                        default:
                            biometricNotificationService.showNotification("fingerprint_action_show_reenroll_dialog", string4, string5, string6, 2);
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
                if (i == 12) {
                    biometricNotificationService.mFingerprintReenrollRequired = true;
                }
            }
        }
    };

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.biometrics.BiometricNotificationService$1] */
    public BiometricNotificationService(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, Handler handler, NotificationManager notificationManager, BiometricNotificationBroadcastReceiver biometricNotificationBroadcastReceiver, Optional<FingerprintReEnrollNotificationImpl> optional) {
        this.mContext = context;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        this.mHandler = handler;
        this.mNotificationManager = notificationManager;
        this.mBroadcastReceiver = biometricNotificationBroadcastReceiver;
        this.mFingerprintReEnrollNotification = optional.orElse(new FingerprintReEnrollNotificationImpl());
    }

    public final void showNotification(String str, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i) {
        if (i == 1) {
            this.mFaceNotificationQueued = false;
        } else if (i == 2) {
            this.mFingerprintNotificationQueued = false;
        }
        NotificationManager notificationManager = this.mNotificationManager;
        if (notificationManager == null) {
            Log.e("BiometricNotificationService", "Failed to show notification " + str + ". Notification manager is null!");
            return;
        }
        Intent intent = new Intent(str);
        UserHandle userHandle = UserHandle.CURRENT;
        Context context = this.mContext;
        Notification build = new Notification.Builder(context, "BiometricHiPriNotificationChannel").setCategory("sys").setSmallIcon(android.R.drawable.ic_ime_switcher).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence3).setContentIntent(PendingIntent.getBroadcastAsUser(context, 0, intent, QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY, userHandle)).setAutoCancel(true).setLocalOnly(true).setOnlyAlertOnce(true).setVisibility(-1).build();
        notificationManager.createNotificationChannel(this.mNotificationChannel);
        notificationManager.notifyAsUser("BiometricNotificationService", i, build, UserHandle.CURRENT);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).addCallback(this.mKeyguardStateControllerCallback);
        this.mNotificationChannel = new NotificationChannel("BiometricHiPriNotificationChannel", " Biometric Unlock", 4);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("fingerprint_action_show_reenroll_dialog");
        intentFilter.addAction("face_action_show_reenroll_dialog");
        this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter, 2);
    }
}
