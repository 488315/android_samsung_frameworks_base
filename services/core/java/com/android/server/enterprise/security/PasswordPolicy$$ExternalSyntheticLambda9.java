package com.android.server.enterprise.security;

import android.content.ComponentName;
import android.os.UserHandle;
import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class PasswordPolicy$$ExternalSyntheticLambda9 implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PasswordPolicy f$0;
    public final /* synthetic */ ComponentName f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ PasswordPolicy$$ExternalSyntheticLambda9(PasswordPolicy passwordPolicy, int i, ComponentName componentName, int i2) {
        this.$r8$classId = i2;
        this.f$0 = passwordPolicy;
        this.f$2 = i;
        this.f$1 = componentName;
    }

    public /* synthetic */ PasswordPolicy$$ExternalSyntheticLambda9(PasswordPolicy passwordPolicy, ComponentName componentName, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = passwordPolicy;
        this.f$1 = componentName;
        this.f$2 = i;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                PasswordPolicy passwordPolicy = this.f$0;
                return Long.valueOf(passwordPolicy.mService.getMaximumTimeToLock(this.f$1, UserHandle.getUserId(this.f$2), false));
            case 1:
                PasswordPolicy passwordPolicy2 = this.f$0;
                return Integer.valueOf(passwordPolicy2.mService.getPasswordQuality(this.f$1, UserHandle.getUserId(this.f$2), false));
            case 2:
                PasswordPolicy passwordPolicy3 = this.f$0;
                int i = this.f$2;
                ComponentName componentName = this.f$1;
                passwordPolicy3.getClass();
                return Boolean.valueOf(passwordPolicy3.mService.isResetPasswordTokenActiveMDM(componentName, UserHandle.getUserId(i)));
            case 3:
                PasswordPolicy passwordPolicy4 = this.f$0;
                return Integer.valueOf(passwordPolicy4.mService.getMaximumFailedPasswordsForWipe(this.f$1, UserHandle.getUserId(this.f$2), false));
            case 4:
                PasswordPolicy passwordPolicy5 = this.f$0;
                int i2 = this.f$2;
                ComponentName componentName2 = this.f$1;
                passwordPolicy5.getClass();
                return Boolean.valueOf(passwordPolicy5.mService.clearResetPasswordTokenMDM(componentName2, UserHandle.getUserId(i2)));
            default:
                PasswordPolicy passwordPolicy6 = this.f$0;
                return Integer.valueOf(passwordPolicy6.mService.getKeyguardDisabledFeatures(this.f$1, UserHandle.getUserId(this.f$2), false));
        }
    }
}
