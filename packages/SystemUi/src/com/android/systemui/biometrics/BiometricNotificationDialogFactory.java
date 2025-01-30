package com.android.systemui.biometrics;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.face.Face;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintManager;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BiometricNotificationDialogFactory {
    public static void createReenrollDialog(final Context context, SystemUIDialog systemUIDialog, final BiometricSourceType biometricSourceType) {
        if (biometricSourceType == BiometricSourceType.FACE) {
            systemUIDialog.setTitle(context.getString(R.string.face_re_enroll_dialog_title));
            systemUIDialog.setMessage(context.getString(R.string.face_re_enroll_dialog_content));
        } else if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
            FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(FingerprintManager.class);
            systemUIDialog.setTitle(context.getString(R.string.fingerprint_re_enroll_dialog_title));
            if (fingerprintManager.getEnrolledFingerprints().size() == 1) {
                systemUIDialog.setMessage(context.getString(R.string.fingerprint_re_enroll_dialog_content_singular));
            } else {
                systemUIDialog.setMessage(context.getString(R.string.fingerprint_re_enroll_dialog_content));
            }
        }
        systemUIDialog.setPositiveButton(R.string.biometric_re_enroll_dialog_confirm, new DialogInterface.OnClickListener() { // from class: com.android.systemui.biometrics.BiometricNotificationDialogFactory$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                final Context context2 = context;
                BiometricSourceType biometricSourceType2 = biometricSourceType;
                if (biometricSourceType2 == BiometricSourceType.FACE) {
                    FaceManager faceManager = (FaceManager) context2.getSystemService(FaceManager.class);
                    if (faceManager == null) {
                        Log.e("BiometricNotificationDialogFactory", "Not launching enrollment. Face manager was null!");
                        BiometricNotificationDialogFactory.createReenrollFailureDialog(context2, BiometricSourceType.FACE).show();
                        return;
                    } else if (faceManager.hasEnrolledTemplates(context2.getUserId())) {
                        faceManager.removeAll(context2.getUserId(), new FaceManager.RemovalCallback() { // from class: com.android.systemui.biometrics.BiometricNotificationDialogFactory.2
                            public boolean mDidShowFailureDialog;

                            public final void onRemovalError(Face face, int i2, CharSequence charSequence) {
                                Log.e("BiometricNotificationDialogFactory", "Not launching enrollment.Failed to remove existing face(s).");
                                if (this.mDidShowFailureDialog) {
                                    return;
                                }
                                this.mDidShowFailureDialog = true;
                                BiometricNotificationDialogFactory.createReenrollFailureDialog(context2, BiometricSourceType.FACE).show();
                            }

                            public final void onRemovalSucceeded(Face face, int i2) {
                                if (this.mDidShowFailureDialog || i2 != 0) {
                                    return;
                                }
                                Intent intent = new Intent("android.settings.FACE_ENROLL");
                                intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
                                intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                                context2.startActivity(intent);
                            }
                        });
                        return;
                    } else {
                        BiometricNotificationDialogFactory.createReenrollFailureDialog(context2, BiometricSourceType.FACE).show();
                        return;
                    }
                }
                if (biometricSourceType2 == BiometricSourceType.FINGERPRINT) {
                    FingerprintManager fingerprintManager2 = (FingerprintManager) context2.getSystemService(FingerprintManager.class);
                    if (fingerprintManager2 == null) {
                        Log.e("BiometricNotificationDialogFactory", "Not launching enrollment. Fingerprint manager was null!");
                        BiometricNotificationDialogFactory.createReenrollFailureDialog(context2, BiometricSourceType.FINGERPRINT).show();
                    } else if (fingerprintManager2.hasEnrolledTemplates(context2.getUserId())) {
                        fingerprintManager2.removeAll(context2.getUserId(), new FingerprintManager.RemovalCallback() { // from class: com.android.systemui.biometrics.BiometricNotificationDialogFactory.1
                            public boolean mDidShowFailureDialog;

                            public final void onRemovalError(Fingerprint fingerprint, int i2, CharSequence charSequence) {
                                Log.e("BiometricNotificationDialogFactory", "Not launching enrollment.Failed to remove existing face(s).");
                                if (this.mDidShowFailureDialog) {
                                    return;
                                }
                                this.mDidShowFailureDialog = true;
                                BiometricNotificationDialogFactory.createReenrollFailureDialog(context2, BiometricSourceType.FINGERPRINT).show();
                            }

                            public final void onRemovalSucceeded(Fingerprint fingerprint, int i2) {
                                if (this.mDidShowFailureDialog || i2 != 0) {
                                    return;
                                }
                                Intent intent = new Intent("android.settings.FINGERPRINT_ENROLL");
                                intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
                                intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                                context2.startActivity(intent);
                            }
                        });
                    } else {
                        BiometricNotificationDialogFactory.createReenrollFailureDialog(context2, BiometricSourceType.FINGERPRINT).show();
                    }
                }
            }
        });
        systemUIDialog.setNegativeButton(R.string.biometric_re_enroll_dialog_cancel, new BiometricNotificationDialogFactory$$ExternalSyntheticLambda1(0));
    }

    public static SystemUIDialog createReenrollFailureDialog(Context context, BiometricSourceType biometricSourceType) {
        SystemUIDialog systemUIDialog = new SystemUIDialog(context);
        if (biometricSourceType == BiometricSourceType.FACE) {
            systemUIDialog.setMessage(context.getString(R.string.face_reenroll_failure_dialog_content));
        } else if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
            systemUIDialog.setMessage(context.getString(R.string.fingerprint_reenroll_failure_dialog_content));
        }
        systemUIDialog.setPositiveButton(R.string.f789ok, new BiometricNotificationDialogFactory$$ExternalSyntheticLambda1(1));
        return systemUIDialog;
    }
}
