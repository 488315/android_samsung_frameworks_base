package com.android.server.credentials;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.credentials.CredentialProviderInfo;
import android.service.credentials.CredentialProviderInfoFactory;
import android.util.Slog;
import com.android.server.infra.AbstractPerUserSystemService;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class CredentialManagerServiceImpl extends AbstractPerUserSystemService {
    public CredentialProviderInfo mInfo;

    public CredentialManagerServiceImpl(CredentialManagerService credentialManagerService, Object obj, int i, String str) {
        super(credentialManagerService, obj, i);
        Slog.i("CredManSysServiceImpl", "CredentialManagerServiceImpl constructed for: " + str);
        synchronized (this.mLock) {
            newServiceInfoLocked(ComponentName.unflattenFromString(str));
        }
    }

    public ComponentName getComponentName() {
        return this.mInfo.getServiceInfo().getComponentName();
    }

    public CredentialManagerServiceImpl(CredentialManagerService credentialManagerService, Object obj, int i, CredentialProviderInfo credentialProviderInfo) {
        super(credentialManagerService, obj, i);
        Slog.i("CredManSysServiceImpl", "CredentialManagerServiceImpl constructed for: " + credentialProviderInfo.getServiceInfo().getComponentName().flattenToString());
        this.mInfo = credentialProviderInfo;
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public ServiceInfo newServiceInfoLocked(ComponentName componentName) {
        if (this.mInfo != null) {
            Slog.i("CredManSysServiceImpl", "newServiceInfoLocked, mInfo not null : " + this.mInfo.getServiceInfo().getComponentName().flattenToString() + " , " + componentName.flattenToString());
        } else {
            Slog.i("CredManSysServiceImpl", "newServiceInfoLocked, mInfo null, " + componentName.flattenToString());
        }
        CredentialProviderInfo create = CredentialProviderInfoFactory.create(getContext(), componentName, this.mUserId, false);
        this.mInfo = create;
        return create.getServiceInfo();
    }

    public ProviderSession initiateProviderSessionForRequestLocked(RequestSession requestSession, List list) {
        if (!list.isEmpty() && !isServiceCapableLocked(list)) {
            Slog.i("CredManSysServiceImpl", "Service does not have the required capabilities");
            return null;
        }
        if (this.mInfo == null) {
            Slog.w("CredManSysServiceImpl", "Initiating provider session for request but mInfo is null. This shouldn't happen");
            return null;
        }
        return requestSession.initiateProviderSession(this.mInfo, new RemoteCredentialService(getContext(), this.mInfo.getServiceInfo().getComponentName(), this.mUserId));
    }

    public boolean isServiceCapableLocked(List list) {
        if (this.mInfo == null) {
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (this.mInfo.hasCapability((String) it.next())) {
                return true;
            }
        }
        return false;
    }

    public CredentialProviderInfo getCredentialProviderInfo() {
        return this.mInfo;
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public void handlePackageUpdateLocked(String str) {
        CredentialProviderInfo credentialProviderInfo = this.mInfo;
        if (credentialProviderInfo == null || credentialProviderInfo.getServiceInfo() == null || !this.mInfo.getServiceInfo().getComponentName().getPackageName().equals(str)) {
            return;
        }
        try {
            newServiceInfoLocked(this.mInfo.getServiceInfo().getComponentName());
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e("CredManSysServiceImpl", "Issue while updating serviceInfo: " + e.getMessage());
        }
    }
}
