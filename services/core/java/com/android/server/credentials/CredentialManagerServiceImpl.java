package com.android.server.credentials;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.credentials.CredentialProviderInfo;
import android.service.credentials.CredentialProviderInfoFactory;
import android.util.Slog;

import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CredentialManagerServiceImpl extends AbstractPerUserSystemService {
    public CredentialProviderInfo mInfo;

    public CredentialManagerServiceImpl(
            CredentialManagerService credentialManagerService, Object obj, int i, String str) {
        super(credentialManagerService, obj, i);
        Slog.i("CredentialManager", "CredentialManagerServiceImpl constructed for: " + str);
        synchronized (obj) {
            newServiceInfoLocked(ComponentName.unflattenFromString(str));
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final void handlePackageUpdateLocked(String str) {
        CredentialProviderInfo credentialProviderInfo = this.mInfo;
        if (credentialProviderInfo == null
                || credentialProviderInfo.getServiceInfo() == null
                || !this.mInfo.getServiceInfo().getComponentName().getPackageName().equals(str)) {
            return;
        }
        try {
            newServiceInfoLocked(this.mInfo.getServiceInfo().getComponentName());
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e("CredentialManager", "Issue while updating serviceInfo: " + e.getMessage());
        }
    }

    public final ProviderSession initiateProviderSessionForRequestLocked(
            RequestSession requestSession, List list) {
        if (!list.isEmpty()) {
            if (this.mInfo != null) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (this.mInfo.hasCapability((String) it.next())) {}
                }
            }
            if (this.mInfo != null) {
                Slog.i(
                        "CredentialManager",
                        "Service does not have the required capabilities: "
                                + this.mInfo.getComponentName());
            }
            return null;
        }
        if (this.mInfo == null) {
            Slog.w(
                    "CredentialManager",
                    "Initiating provider session for request but mInfo is null. This shouldn't"
                        + " happen");
            return null;
        }
        return requestSession.initiateProviderSession(
                this.mInfo,
                new RemoteCredentialService(
                        this.mMaster.getContext(),
                        this.mInfo.getServiceInfo().getComponentName(),
                        this.mUserId));
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final ServiceInfo newServiceInfoLocked(ComponentName componentName) {
        if (this.mInfo != null) {
            Slog.i(
                    "CredentialManager",
                    "newServiceInfoLocked, mInfo not null : "
                            + this.mInfo.getServiceInfo().getComponentName().flattenToString()
                            + " , "
                            + componentName.flattenToString());
        } else {
            Slog.i(
                    "CredentialManager",
                    "newServiceInfoLocked, mInfo null, " + componentName.flattenToString());
        }
        AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
        Context context = ((CredentialManagerService) abstractMasterSystemService).getContext();
        int i = this.mUserId;
        CredentialProviderInfo create =
                CredentialProviderInfoFactory.create(
                        abstractMasterSystemService.getContext(),
                        componentName,
                        i,
                        false,
                        ((HashSet)
                                        CredentialManagerService.getPrimaryProvidersForUserId(
                                                context, i))
                                .contains(componentName));
        this.mInfo = create;
        return create.getServiceInfo();
    }
}
