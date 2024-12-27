package com.android.server.enterprise.security;

import android.os.UserHandle;
import android.util.Log;

import com.android.internal.util.FunctionalUtils;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;

public final /* synthetic */ class PasswordPolicy$$ExternalSyntheticLambda2
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PasswordPolicy f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ PasswordPolicy$$ExternalSyntheticLambda2(
            PasswordPolicy passwordPolicy, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = passwordPolicy;
        this.f$1 = i;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                return Integer.valueOf(
                        this.f$0.mService.getCurrentFailedPasswordAttempts(
                                (String) null, UserHandle.getUserId(this.f$1), false));
            case 1:
                PasswordPolicy passwordPolicy = this.f$0;
                int i = this.f$1;
                passwordPolicy.getClass();
                Integer valueOf =
                        Integer.valueOf(
                                new LockPatternUtils(passwordPolicy.mContext)
                                        .getActivePasswordQuality(i));
                Log.d("PasswordPolicy", "UCS enabled for user = " + i);
                StringBuilder sb = new StringBuilder("current quality = ");
                sb.append(valueOf);
                VpnManagerService$$ExternalSyntheticOutline0.m(
                        sb, ", SMART CARD Quality = 458752", "PasswordPolicy");
                return valueOf;
            case 2:
                PasswordPolicy passwordPolicy2 = this.f$0;
                int i2 = this.f$1;
                passwordPolicy2.getClass();
                Integer valueOf2 =
                        Integer.valueOf(
                                new LockPatternUtils(passwordPolicy2.mContext)
                                        .getActivePasswordQuality(i2));
                Log.d("PasswordPolicy", "UCS enabled for user = " + i2);
                StringBuilder sb2 = new StringBuilder("current quality = ");
                sb2.append(valueOf2);
                VpnManagerService$$ExternalSyntheticOutline0.m(
                        sb2, ", SMART CARD Quality = 458752", "PasswordPolicy");
                return valueOf2;
            case 3:
                return Boolean.valueOf(
                        this.f$0.mService.isActivePasswordSufficient(
                                (String) null, UserHandle.getUserId(this.f$1), false));
            default:
                return Integer.valueOf(
                        this.f$0.mService.getCurrentFailedPasswordAttempts(
                                (String) null, UserHandle.getUserId(this.f$1), false));
        }
    }
}
