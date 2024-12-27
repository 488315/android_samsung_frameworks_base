package com.android.server.enterprise.security;

import android.content.ComponentName;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class PasswordPolicy$$ExternalSyntheticLambda5
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PasswordPolicy f$0;
    public final /* synthetic */ ComponentName f$1;
    public final /* synthetic */ long f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ PasswordPolicy$$ExternalSyntheticLambda5(
            PasswordPolicy passwordPolicy, ComponentName componentName, long j, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = passwordPolicy;
        this.f$1 = componentName;
        this.f$2 = j;
        this.f$3 = i;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                PasswordPolicy passwordPolicy = this.f$0;
                passwordPolicy.mService.setMaximumTimeToLockMDM(
                        this.f$1, this.f$2, UserHandle.getUserId(this.f$3));
                break;
            default:
                PasswordPolicy passwordPolicy2 = this.f$0;
                ComponentName componentName = this.f$1;
                long j = this.f$2;
                int i = this.f$3;
                passwordPolicy2.getClass();
                try {
                    passwordPolicy2.mService.setPasswordExpirationTimeoutMDM(
                            componentName, j, UserHandle.getUserId(i));
                    break;
                } catch (RemoteException e) {
                    Log.w("PasswordPolicy", "Failed talking with device policy service", e);
                }
        }
    }
}
