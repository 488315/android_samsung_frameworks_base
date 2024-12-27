package com.android.server;

import android.net.vcn.IVcnUnderlyingNetworkPolicyListener;
import android.os.RemoteException;
import android.util.ArrayMap;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class VcnManagementService$$ExternalSyntheticLambda1
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ VcnManagementService f$0;
    public final /* synthetic */ IVcnUnderlyingNetworkPolicyListener f$1;

    public /* synthetic */ VcnManagementService$$ExternalSyntheticLambda1(
            VcnManagementService vcnManagementService,
            IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener,
            int i) {
        this.$r8$classId = i;
        this.f$0 = vcnManagementService;
        this.f$1 = iVcnUnderlyingNetworkPolicyListener;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                VcnManagementService vcnManagementService = this.f$0;
                IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener = this.f$1;
                synchronized (vcnManagementService.mLock) {
                    try {
                        VcnManagementService.PolicyListenerBinderDeath policyListenerBinderDeath =
                                (VcnManagementService.PolicyListenerBinderDeath)
                                        ((ArrayMap) vcnManagementService.mRegisteredPolicyListeners)
                                                .remove(
                                                        iVcnUnderlyingNetworkPolicyListener
                                                                .asBinder());
                        if (policyListenerBinderDeath != null) {
                            iVcnUnderlyingNetworkPolicyListener
                                    .asBinder()
                                    .unlinkToDeath(policyListenerBinderDeath, 0);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
            default:
                VcnManagementService vcnManagementService2 = this.f$0;
                IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener2 = this.f$1;
                vcnManagementService2.getClass();
                VcnManagementService.PolicyListenerBinderDeath policyListenerBinderDeath2 =
                        new VcnManagementService.PolicyListenerBinderDeath(
                                vcnManagementService2, iVcnUnderlyingNetworkPolicyListener2);
                synchronized (vcnManagementService2.mLock) {
                    ((ArrayMap) vcnManagementService2.mRegisteredPolicyListeners)
                            .put(
                                    iVcnUnderlyingNetworkPolicyListener2.asBinder(),
                                    policyListenerBinderDeath2);
                    try {
                        iVcnUnderlyingNetworkPolicyListener2
                                .asBinder()
                                .linkToDeath(policyListenerBinderDeath2, 0);
                    } catch (RemoteException unused) {
                        policyListenerBinderDeath2.binderDied();
                    }
                }
                return;
        }
    }
}
