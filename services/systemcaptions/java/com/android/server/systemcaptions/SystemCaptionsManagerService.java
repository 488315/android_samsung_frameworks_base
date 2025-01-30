package com.android.server.systemcaptions;

import android.R;
import android.content.Context;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.FrameworkResourcesServiceNameResolver;

/* loaded from: classes3.dex */
public final class SystemCaptionsManagerService extends AbstractMasterSystemService {
    @Override // com.android.server.SystemService
    public void onStart() {
    }

    public SystemCaptionsManagerService(Context context) {
        super(context, new FrameworkResourcesServiceNameResolver(context, R.string.decline), null, 68);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public SystemCaptionsManagerPerUserService newServiceLocked(int i, boolean z) {
        SystemCaptionsManagerPerUserService systemCaptionsManagerPerUserService = new SystemCaptionsManagerPerUserService(this, this.mLock, z, i);
        systemCaptionsManagerPerUserService.initializeLocked();
        return systemCaptionsManagerPerUserService;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public void onServiceRemoved(SystemCaptionsManagerPerUserService systemCaptionsManagerPerUserService, int i) {
        synchronized (this.mLock) {
            systemCaptionsManagerPerUserService.destroyLocked();
        }
    }
}
