package com.android.server.devicepolicy;

import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.security.AppUriAuthenticationPolicy;
import android.security.KeyChain;
import com.android.internal.util.FunctionalUtils;
import com.android.server.utils.Slogf;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda10 implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ CallerIdentity f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda10(DevicePolicyManagerService devicePolicyManagerService, CallerIdentity callerIdentity, String str, int i) {
        this.$r8$classId = i;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = callerIdentity;
        this.f$2 = str;
    }

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda10(DevicePolicyManagerService devicePolicyManagerService, String str, CallerIdentity callerIdentity, int i) {
        this.$r8$classId = i;
        this.f$0 = devicePolicyManagerService;
        this.f$2 = str;
        this.f$1 = callerIdentity;
    }

    public final Object getOrThrow() {
        KeyChain.KeyChainConnection bindAsUser;
        Boolean bool;
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                CallerIdentity callerIdentity = this.f$1;
                String str = this.f$2;
                devicePolicyManagerService.getClass();
                try {
                    bindAsUser = KeyChain.bindAsUser(devicePolicyManagerService.mContext, UserHandle.getUserHandleForUid(callerIdentity.mUid));
                    try {
                        new ArrayList();
                        int[] grants = bindAsUser.getService().getGrants(str);
                        int length = grants.length;
                        int i = 0;
                        while (true) {
                            if (i >= length) {
                                bool = Boolean.FALSE;
                            } else if (grants[i] == 1010) {
                                bool = Boolean.TRUE;
                            } else {
                                i++;
                            }
                        }
                        bindAsUser.close();
                        return bool;
                    } finally {
                        if (bindAsUser != null) {
                            try {
                                bindAsUser.close();
                            } catch (Throwable th) {
                                th.addSuppressed(th);
                            }
                        }
                    }
                } catch (RemoteException | AssertionError e) {
                    Slogf.e("DevicePolicyManager", "Querying grant to wifi auth.", e);
                    return Boolean.FALSE;
                }
            case 1:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                CallerIdentity callerIdentity2 = this.f$1;
                String str2 = this.f$2;
                devicePolicyManagerService2.getClass();
                try {
                    KeyChain.KeyChainConnection bindAsUser2 = KeyChain.bindAsUser(devicePolicyManagerService2.mContext, UserHandle.getUserHandleForUid(callerIdentity2.mUid));
                    try {
                        AppUriAuthenticationPolicy credentialManagementAppPolicy = bindAsUser2.getService().getCredentialManagementAppPolicy();
                        Boolean valueOf = Boolean.valueOf((credentialManagementAppPolicy == null || credentialManagementAppPolicy.getAppAndUriMappings().isEmpty() || !DevicePolicyManagerService.containsAlias(credentialManagementAppPolicy, str2)) ? false : true);
                        bindAsUser2.close();
                        return valueOf;
                    } catch (Throwable th2) {
                        throw th2;
                    }
                } catch (RemoteException | AssertionError | InterruptedException unused) {
                    return Boolean.FALSE;
                }
            case 2:
                Bundle applicationRestrictions = this.f$0.mUserManager.getApplicationRestrictions(this.f$2, UserHandle.getUserHandleForUid(this.f$1.mUid));
                return applicationRestrictions != null ? applicationRestrictions : Bundle.EMPTY;
            case 3:
                Bundle applicationRestrictions2 = this.f$0.mUserManager.getApplicationRestrictions(this.f$2, UserHandle.getUserHandleForUid(this.f$1.mUid));
                return applicationRestrictions2 != null ? applicationRestrictions2 : Bundle.EMPTY;
            default:
                DevicePolicyManagerService devicePolicyManagerService3 = this.f$0;
                CallerIdentity callerIdentity3 = this.f$1;
                String str3 = this.f$2;
                devicePolicyManagerService3.getClass();
                try {
                    try {
                        bindAsUser = KeyChain.bindAsUser(devicePolicyManagerService3.mContext, UserHandle.getUserHandleForUid(callerIdentity3.mUid));
                        try {
                            Boolean valueOf2 = Boolean.valueOf(bindAsUser.getService().containsKeyPair(str3));
                            bindAsUser.close();
                            return valueOf2;
                        } finally {
                        }
                    } catch (InterruptedException e2) {
                        Slogf.w("DevicePolicyManager", "Interrupted while querying keypair", e2);
                        Thread.currentThread().interrupt();
                        return Boolean.FALSE;
                    }
                } catch (RemoteException | AssertionError e3) {
                    Slogf.e("DevicePolicyManager", "Querying keypair", e3);
                    return Boolean.FALSE;
                }
        }
    }
}
