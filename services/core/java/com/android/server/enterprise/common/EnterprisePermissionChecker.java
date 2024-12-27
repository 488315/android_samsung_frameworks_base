package com.android.server.enterprise.common;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;

import com.android.server.ServiceKeeper;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;

import com.samsung.android.knox.SemPersonaManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class EnterprisePermissionChecker {
    public static EnterprisePermissionChecker sInstance;
    public Context mContext;
    public IPersonaManagerAdapter mPersonaManagerAdapter;

    public final void enforceAuthorization() {
        if (this.mPersonaManagerAdapter == null) {
            this.mPersonaManagerAdapter =
                    (IPersonaManagerAdapter)
                            AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class);
        }
        ((PersonaManagerAdapter) this.mPersonaManagerAdapter).getClass();
        Bundle knoxInfo = SemPersonaManager.getKnoxInfo();
        if (knoxInfo == null || !"2.0".equals(knoxInfo.getString("version"))) {
            return;
        }
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        if (!ServiceKeeper.isActive) {
            ServiceKeeper.authorizeLoadProcedure();
        }
        if (ServiceKeeper.isAuthorized(
                        callingPid, callingUid, this.mContext, "AuditLogService", "AuditLogger")
                != 0) {
            throw new SecurityException(
                    "Method AuditLogger from service AuditLogService is not authorized to be"
                        + " called!!!");
        }
    }
}
