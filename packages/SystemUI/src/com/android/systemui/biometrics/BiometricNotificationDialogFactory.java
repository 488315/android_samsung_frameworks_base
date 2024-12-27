package com.android.systemui.biometrics;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.face.Face;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintManager;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.biometrics.BiometricNotificationDialogFactory;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BiometricNotificationDialogFactory {
    public final FaceManager mFaceManager;
    public final FingerprintManager mFingerprintManager;
    public final Resources mResources;
    public final SystemUIDialog.Factory mSystemUIDialogFactory;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface ActivityStarter {
        void startActivity(Intent intent);
    }

    public BiometricNotificationDialogFactory(Resources resources, SystemUIDialog.Factory factory, FingerprintManager fingerprintManager, FaceManager faceManager) {
        this.mResources = resources;
        this.mSystemUIDialogFactory = factory;
        this.mFingerprintManager = fingerprintManager;
        this.mFaceManager = faceManager;
    }

    public final SystemUIDialog createReenrollDialog(final int i, final ActivityStarter activityStarter, final BiometricSourceType biometricSourceType, boolean z) {
        SystemUIDialog create = this.mSystemUIDialogFactory.create();
        if (biometricSourceType == BiometricSourceType.FACE) {
            create.setTitle(this.mResources.getString(R.string.face_re_enroll_dialog_title));
            create.setMessage(this.mResources.getString(R.string.face_re_enroll_dialog_content));
        } else if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
            create.setTitle(this.mResources.getString(R.string.fingerprint_re_enroll_dialog_title));
            if (this.mFingerprintManager.getEnrolledFingerprints().size() == 1) {
                create.setMessage(this.mResources.getString(R.string.fingerprint_re_enroll_dialog_content_singular));
            } else {
                create.setMessage(this.mResources.getString(R.string.fingerprint_re_enroll_dialog_content));
            }
        }
        create.setPositiveButton(R.string.biometric_re_enroll_dialog_confirm, new DialogInterface.OnClickListener() { // from class: com.android.systemui.biometrics.BiometricNotificationDialogFactory$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                final BiometricNotificationDialogFactory biometricNotificationDialogFactory = BiometricNotificationDialogFactory.this;
                int i3 = i;
                BiometricSourceType biometricSourceType2 = biometricSourceType;
                final BiometricNotificationDialogFactory.ActivityStarter activityStarter2 = activityStarter;
                biometricNotificationDialogFactory.getClass();
                BiometricSourceType biometricSourceType3 = BiometricSourceType.FACE;
                if (biometricSourceType2 == biometricSourceType3) {
                    FaceManager faceManager = biometricNotificationDialogFactory.mFaceManager;
                    if (faceManager == null) {
                        Log.e("BiometricNotificationDialogFactory", "Not launching enrollment. Face manager was null!");
                        biometricNotificationDialogFactory.createReenrollFailureDialog(biometricSourceType3).show();
                        return;
                    } else if (faceManager.hasEnrolledTemplates(i3)) {
                        biometricNotificationDialogFactory.mFaceManager.removeAll(i3, new FaceManager.RemovalCallback() { // from class: com.android.systemui.biometrics.BiometricNotificationDialogFactory.2
                            public boolean mDidShowFailureDialog;

                            public final void onRemovalError(Face face, int i4, CharSequence charSequence) {
                                Log.e("BiometricNotificationDialogFactory", "Not launching enrollment.Failed to remove existing face(s).");
                                if (this.mDidShowFailureDialog) {
                                    return;
                                }
                                this.mDidShowFailureDialog = true;
                                BiometricNotificationDialogFactory.this.createReenrollFailureDialog(BiometricSourceType.FACE).show();
                            }

                            public final void onRemovalSucceeded(Face face, int i4) {
                                if (this.mDidShowFailureDialog || i4 != 0) {
                                    return;
                                }
                                Intent intent = new Intent("android.settings.FACE_ENROLL");
                                intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
                                intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                                activityStarter2.startActivity(intent);
                            }
                        });
                        return;
                    } else {
                        biometricNotificationDialogFactory.createReenrollFailureDialog(biometricSourceType3).show();
                        return;
                    }
                }
                BiometricSourceType biometricSourceType4 = BiometricSourceType.FINGERPRINT;
                if (biometricSourceType2 == biometricSourceType4) {
                    FingerprintManager fingerprintManager = biometricNotificationDialogFactory.mFingerprintManager;
                    if (fingerprintManager == null) {
                        Log.e("BiometricNotificationDialogFactory", "Not launching enrollment. Fingerprint manager was null!");
                        biometricNotificationDialogFactory.createReenrollFailureDialog(biometricSourceType4).show();
                    } else if (fingerprintManager.hasEnrolledTemplates(i3)) {
                        biometricNotificationDialogFactory.mFingerprintManager.removeAll(i3, new FingerprintManager.RemovalCallback() { // from class: com.android.systemui.biometrics.BiometricNotificationDialogFactory.1
                            public boolean mDidShowFailureDialog;

                            public final void onRemovalError(Fingerprint fingerprint, int i4, CharSequence charSequence) {
                                Log.e("BiometricNotificationDialogFactory", "Not launching enrollment.Failed to remove existing face(s).");
                                if (this.mDidShowFailureDialog) {
                                    return;
                                }
                                this.mDidShowFailureDialog = true;
                                BiometricNotificationDialogFactory.this.createReenrollFailureDialog(BiometricSourceType.FINGERPRINT).show();
                            }

                            public final void onRemovalSucceeded(Fingerprint fingerprint, int i4) {
                                if (this.mDidShowFailureDialog || i4 != 0) {
                                    return;
                                }
                                Intent intent = new Intent("android.settings.FINGERPRINT_ENROLL");
                                intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
                                intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                                activityStarter2.startActivity(intent);
                            }
                        });
                    } else {
                        biometricNotificationDialogFactory.createReenrollFailureDialog(biometricSourceType4).show();
                    }
                }
            }
        });
        if (!z) {
            create.setNegativeButton(R.string.biometric_re_enroll_dialog_cancel, new BiometricNotificationDialogFactory$$ExternalSyntheticLambda1());
        }
        create.setCanceledOnTouchOutside(!z);
        return create;
    }

    public final SystemUIDialog createReenrollFailureDialog(BiometricSourceType biometricSourceType) {
        SystemUIDialog create = this.mSystemUIDialogFactory.create();
        if (biometricSourceType == BiometricSourceType.FACE) {
            create.setMessage(this.mResources.getString(R.string.face_reenroll_failure_dialog_content));
        } else if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
            create.setMessage(this.mResources.getString(R.string.fingerprint_reenroll_failure_dialog_content));
        }
        create.setPositiveButton(R.string.ok, new BiometricNotificationDialogFactory$$ExternalSyntheticLambda1());
        return create;
    }
}
