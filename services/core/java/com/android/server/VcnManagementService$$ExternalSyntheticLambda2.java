package com.android.server;

import android.os.ParcelUuid;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.util.FunctionalUtils;
import com.android.server.VcnManagementService;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class VcnManagementService$$ExternalSyntheticLambda2 implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ VcnManagementService$$ExternalSyntheticLambda2(UserManager userManager, UserHandle userHandle) {
        this.$r8$classId = 1;
        this.f$0 = userManager;
        this.f$1 = userHandle;
    }

    public /* synthetic */ VcnManagementService$$ExternalSyntheticLambda2(VcnManagementService vcnManagementService, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = vcnManagementService;
        this.f$1 = obj;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                VcnManagementService vcnManagementService = (VcnManagementService) this.f$0;
                ParcelUuid parcelUuid = (ParcelUuid) this.f$1;
                synchronized (vcnManagementService.mLock) {
                    ((ArrayMap) vcnManagementService.mConfigs).remove(parcelUuid);
                    boolean containsKey = ((ArrayMap) vcnManagementService.mVcns).containsKey(parcelUuid);
                    vcnManagementService.stopVcnLocked(parcelUuid);
                    if (containsKey) {
                        vcnManagementService.notifyAllPermissionedStatusCallbacksLocked(parcelUuid, 0);
                    }
                    vcnManagementService.writeConfigsToDiskLocked();
                }
                return;
            case 1:
                UserManager userManager = (UserManager) this.f$0;
                if (!Objects.equals(userManager.getMainUser(), (UserHandle) this.f$1)) {
                    throw new SecurityException("VcnManagementService can only be used by callers running as the main user");
                }
                return;
            default:
                VcnManagementService vcnManagementService2 = (VcnManagementService) this.f$0;
                VcnManagementService.PolicyListenerBinderDeath policyListenerBinderDeath = (VcnManagementService.PolicyListenerBinderDeath) this.f$1;
                vcnManagementService2.getClass();
                try {
                    policyListenerBinderDeath.mListener.onPolicyChanged();
                    return;
                } catch (RemoteException e) {
                    Slog.d("VcnManagementService", "VcnStatusCallback threw on VCN status change", e);
                    return;
                }
        }
    }
}
