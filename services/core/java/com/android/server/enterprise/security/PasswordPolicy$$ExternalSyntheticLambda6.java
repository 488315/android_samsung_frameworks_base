package com.android.server.enterprise.security;

import android.content.ComponentName;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class PasswordPolicy$$ExternalSyntheticLambda6
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PasswordPolicy f$0;
    public final /* synthetic */ ComponentName f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ PasswordPolicy$$ExternalSyntheticLambda6(
            PasswordPolicy passwordPolicy, ComponentName componentName, int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = passwordPolicy;
        this.f$1 = componentName;
        this.f$2 = i;
        this.f$3 = i2;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                PasswordPolicy passwordPolicy = this.f$0;
                passwordPolicy.mService.setMaximumFailedPasswordsForWipeMDM(
                        this.f$1, this.f$2, UserHandle.getUserId(this.f$3));
                break;
            case 1:
                PasswordPolicy passwordPolicy2 = this.f$0;
                ComponentName componentName = this.f$1;
                int i = this.f$2;
                int i2 = this.f$3;
                passwordPolicy2.getClass();
                try {
                    passwordPolicy2.mService.setPasswordQualityMDM(
                            componentName, i, UserHandle.getUserId(i2));
                    break;
                } catch (RemoteException e) {
                    Log.w("PasswordPolicy", "Failed talking with device policy service", e);
                    return;
                }
            case 2:
                PasswordPolicy passwordPolicy3 = this.f$0;
                ComponentName componentName2 = this.f$1;
                int i3 = this.f$2;
                int i4 = this.f$3;
                passwordPolicy3.getClass();
                try {
                    passwordPolicy3.mService.setPasswordMinimumLowerCaseMDM(
                            componentName2, i3, UserHandle.getUserId(i4));
                    break;
                } catch (RemoteException e2) {
                    Log.w("PasswordPolicy", "Failed talking with device policy service", e2);
                    return;
                }
            case 3:
                PasswordPolicy passwordPolicy4 = this.f$0;
                ComponentName componentName3 = this.f$1;
                int i5 = this.f$2;
                int i6 = this.f$3;
                passwordPolicy4.getClass();
                try {
                    passwordPolicy4.mService.setPasswordHistoryLengthMDM(
                            componentName3, i5, UserHandle.getUserId(i6));
                    break;
                } catch (RemoteException e3) {
                    Log.w("PasswordPolicy", "Failed talking with device policy service", e3);
                    return;
                }
            case 4:
                PasswordPolicy passwordPolicy5 = this.f$0;
                ComponentName componentName4 = this.f$1;
                int i7 = this.f$2;
                int i8 = this.f$3;
                passwordPolicy5.getClass();
                try {
                    passwordPolicy5.mService.setPasswordMinimumNonLetterMDM(
                            componentName4, i7, UserHandle.getUserId(i8));
                    break;
                } catch (RemoteException e4) {
                    Log.w("PasswordPolicy", "Failed talking with device policy service", e4);
                    return;
                }
            case 5:
                PasswordPolicy passwordPolicy6 = this.f$0;
                ComponentName componentName5 = this.f$1;
                int i9 = this.f$2;
                int i10 = this.f$3;
                passwordPolicy6.getClass();
                try {
                    passwordPolicy6.mService.setPasswordMinimumUpperCaseMDM(
                            componentName5, i9, UserHandle.getUserId(i10));
                    break;
                } catch (RemoteException e5) {
                    Log.w("PasswordPolicy", "Failed talking with device policy service", e5);
                    return;
                }
            case 6:
                PasswordPolicy passwordPolicy7 = this.f$0;
                ComponentName componentName6 = this.f$1;
                int i11 = this.f$2;
                int i12 = this.f$3;
                passwordPolicy7.getClass();
                try {
                    passwordPolicy7.mService.setPasswordMinimumLettersMDM(
                            componentName6, i11, UserHandle.getUserId(i12));
                    break;
                } catch (RemoteException e6) {
                    Log.w("PasswordPolicy", "Failed talking with device policy service", e6);
                    return;
                }
            case 7:
                PasswordPolicy passwordPolicy8 = this.f$0;
                ComponentName componentName7 = this.f$1;
                int i13 = this.f$2;
                int i14 = this.f$3;
                passwordPolicy8.getClass();
                try {
                    passwordPolicy8.mService.setPasswordMinimumLengthMDM(
                            componentName7, i13, UserHandle.getUserId(i14));
                    break;
                } catch (RemoteException e7) {
                    Log.w("PasswordPolicy", "Failed talking with device policy service", e7);
                    return;
                }
            case 8:
                PasswordPolicy passwordPolicy9 = this.f$0;
                ComponentName componentName8 = this.f$1;
                int i15 = this.f$2;
                int i16 = this.f$3;
                passwordPolicy9.getClass();
                try {
                    passwordPolicy9.mService.setPasswordMinimumNumericMDM(
                            componentName8, i15, UserHandle.getUserId(i16));
                    break;
                } catch (RemoteException e8) {
                    Log.w("PasswordPolicy", "Failed talking with device policy service", e8);
                    return;
                }
            case 9:
                PasswordPolicy passwordPolicy10 = this.f$0;
                passwordPolicy10.mService.setKeyguardDisabledFeaturesMDM(
                        this.f$1, this.f$2, UserHandle.getUserId(this.f$3));
                break;
            default:
                PasswordPolicy passwordPolicy11 = this.f$0;
                ComponentName componentName9 = this.f$1;
                int i17 = this.f$2;
                int i18 = this.f$3;
                passwordPolicy11.getClass();
                try {
                    passwordPolicy11.mService.setPasswordMinimumSymbolsMDM(
                            componentName9, i17, UserHandle.getUserId(i18));
                    break;
                } catch (RemoteException e9) {
                    Log.w("PasswordPolicy", "Failed talking with device policy service", e9);
                }
        }
    }
}
