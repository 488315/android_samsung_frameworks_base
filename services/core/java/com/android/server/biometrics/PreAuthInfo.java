package com.android.server.biometrics;

import android.hardware.biometrics.PromptInfo;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.util.Pair;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PreAuthInfo {
    public final boolean confirmationRequested;
    public final boolean credentialAvailable;
    public final boolean credentialRequested;
    public final List eligibleSensors;
    public final List ineligibleSensors;
    public final BiometricCameraManagerImpl mBiometricCameraManager;
    public final boolean mBiometricRequested;
    public final int mBiometricStrengthRequested;
    public final boolean mIsMandatoryBiometricsAuthentication;
    public final boolean mOnlyMandatoryBiometricsRequested;

    public PreAuthInfo(boolean z, int i, boolean z2, List list, List list2, boolean z3, PromptInfo promptInfo, BiometricCameraManagerImpl biometricCameraManagerImpl, boolean z4, boolean z5) {
        this.mBiometricRequested = z;
        this.mBiometricStrengthRequested = i;
        this.mBiometricCameraManager = biometricCameraManagerImpl;
        this.credentialRequested = z2;
        this.eligibleSensors = list;
        this.ineligibleSensors = list2;
        this.credentialAvailable = z3;
        this.confirmationRequested = promptInfo.isConfirmationRequested();
        promptInfo.isIgnoreEnrollmentState();
        this.mOnlyMandatoryBiometricsRequested = z4;
        this.mIsMandatoryBiometricsAuthentication = z5;
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x014a, code lost:
    
        if (((java.lang.Boolean) ((java.util.HashMap) r0.mFaceEnrolledForUser).getOrDefault(java.lang.Integer.valueOf(r22), r5)).booleanValue() != false) goto L44;
     */
    /* JADX WARN: Removed duplicated region for block: B:119:0x02a9  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0153  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x015e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01e1  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0347  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x034b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.biometrics.PreAuthInfo create(android.app.trust.ITrustManager r18, android.app.admin.DevicePolicyManager r19, com.android.server.biometrics.BiometricService.SettingObserver r20, java.util.List r21, int r22, android.hardware.biometrics.PromptInfo r23, java.lang.String r24, boolean r25, android.content.Context r26, com.android.server.biometrics.BiometricCameraManagerImpl r27) {
        /*
            Method dump skipped, instructions count: 890
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.biometrics.PreAuthInfo.create(android.app.trust.ITrustManager, android.app.admin.DevicePolicyManager, com.android.server.biometrics.BiometricService$SettingObserver, java.util.List, int, android.hardware.biometrics.PromptInfo, java.lang.String, boolean, android.content.Context, com.android.server.biometrics.BiometricCameraManagerImpl):com.android.server.biometrics.PreAuthInfo");
    }

    public final Pair calculateErrorByPriority() {
        Pair pair = null;
        Pair pair2 = null;
        Pair pair3 = null;
        Pair pair4 = null;
        for (Pair pair5 : this.ineligibleSensors) {
            int intValue = ((Integer) pair5.second).intValue();
            if (intValue == 10 || intValue == 11) {
                pair = pair5;
            }
            if (intValue == 7) {
                pair4 = pair5;
            }
            if (intValue == 6) {
                pair2 = pair5;
            }
            if (intValue == 8) {
                pair3 = pair5;
            }
        }
        return pair != null ? pair : pair2 != null ? pair2 : (!com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.Flags.mandatoryBiometrics() || pair3 == null) ? pair4 != null ? pair4 : (Pair) this.ineligibleSensors.get(0) : pair3;
    }

    public final int getEligibleModalities() {
        Iterator it = this.eligibleSensors.iterator();
        int i = 0;
        while (it.hasNext()) {
            i |= ((BiometricSensor) it.next()).modality;
        }
        return (this.credentialRequested && this.credentialAvailable) ? i | 1 : i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0070, code lost:
    
        if (r3 != false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0072, code lost:
    
        r1 = r2;
        r0 = 12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x009b, code lost:
    
        if (r3 != false) goto L29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.util.Pair getInternalStatus() {
        /*
            Method dump skipped, instructions count: 254
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.biometrics.PreAuthInfo.getInternalStatus():android.util.Pair");
    }

    public final Pair getPreAuthenticateStatus() {
        Pair internalStatus = getInternalStatus();
        int authenticatorStatusToBiometricConstant = Utils.authenticatorStatusToBiometricConstant(((Integer) internalStatus.second).intValue());
        int intValue = ((Integer) internalStatus.first).intValue();
        switch (((Integer) internalStatus.second).intValue()) {
            case 1:
            case 2:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
                break;
            case 3:
            case 4:
            case 8:
            default:
                intValue = 0;
                break;
        }
        return new Pair(Integer.valueOf(intValue), Integer.valueOf(authenticatorStatusToBiometricConstant));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BiometricRequested: " + this.mBiometricRequested + ", StrengthRequested: " + this.mBiometricStrengthRequested + ", CredentialRequested: " + this.credentialRequested);
        sb.append(", Eligible:{");
        Iterator it = this.eligibleSensors.iterator();
        while (it.hasNext()) {
            sb.append(((BiometricSensor) it.next()).id);
            sb.append(" ");
        }
        sb.append("}, Ineligible:{");
        for (Pair pair : this.ineligibleSensors) {
            sb.append(pair.first);
            sb.append(":");
            sb.append(pair.second);
            sb.append(" ");
        }
        sb.append("}, CredentialAvailable: ");
        return OptionalBool$$ExternalSyntheticOutline0.m(", ", sb, this.credentialAvailable);
    }
}
