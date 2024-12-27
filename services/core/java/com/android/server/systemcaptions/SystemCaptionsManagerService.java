package com.android.server.systemcaptions;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.util.Slog;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.infra.FrameworkResourcesServiceNameResolver;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SystemCaptionsManagerService extends AbstractMasterSystemService {
    public SystemCaptionsManagerService(Context context) {
        super(context, new FrameworkResourcesServiceNameResolver(context, R.string.deprecated_target_sdk_message), null, 68);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final AbstractPerUserSystemService newServiceLocked(int i, boolean z) {
        RemoteSystemCaptionsManagerService remoteSystemCaptionsManagerService;
        SystemCaptionsManagerPerUserService systemCaptionsManagerPerUserService = new SystemCaptionsManagerPerUserService(this, this.mLock, i);
        if (this.verbose) {
            Slog.v("SystemCaptionsManagerPerUserService", "initialize()");
        }
        if (systemCaptionsManagerPerUserService.mRemoteService == null) {
            String componentNameLocked = systemCaptionsManagerPerUserService.getComponentNameLocked();
            if (componentNameLocked == null) {
                if (this.verbose) {
                    Slog.v("SystemCaptionsManagerPerUserService", "getRemoteServiceLocked(): Not set");
                }
                remoteSystemCaptionsManagerService = null;
                if (remoteSystemCaptionsManagerService == null && this.verbose) {
                    Slog.v("SystemCaptionsManagerPerUserService", "initialize(): Failed to init remote server");
                }
                return systemCaptionsManagerPerUserService;
            }
            systemCaptionsManagerPerUserService.mRemoteService = new RemoteSystemCaptionsManagerService(getContext(), ComponentName.unflattenFromString(componentNameLocked), this.verbose, i);
            if (this.verbose) {
                ProxyManager$$ExternalSyntheticOutline0.m(i, "getRemoteServiceLocked(): initialize for user ", "SystemCaptionsManagerPerUserService");
            }
            RemoteSystemCaptionsManagerService remoteSystemCaptionsManagerService2 = systemCaptionsManagerPerUserService.mRemoteService;
            boolean z2 = remoteSystemCaptionsManagerService2.mVerbose;
            if (z2) {
                Slog.v("RemoteSystemCaptionsManagerService", "initialize()");
            }
            Handler handler = remoteSystemCaptionsManagerService2.mHandler;
            if (!handler.hasMessages(1)) {
                handler.sendMessage(PooledLambda.obtainMessage(new RemoteSystemCaptionsManagerService$$ExternalSyntheticLambda0(0), remoteSystemCaptionsManagerService2).setWhat(1));
            } else if (z2) {
                Slog.v("RemoteSystemCaptionsManagerService", "scheduleBind(): already scheduled");
            }
        }
        remoteSystemCaptionsManagerService = systemCaptionsManagerPerUserService.mRemoteService;
        if (remoteSystemCaptionsManagerService == null) {
            Slog.v("SystemCaptionsManagerPerUserService", "initialize(): Failed to init remote server");
        }
        return systemCaptionsManagerPerUserService;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServiceRemoved(AbstractPerUserSystemService abstractPerUserSystemService, int i) {
        SystemCaptionsManagerPerUserService systemCaptionsManagerPerUserService = (SystemCaptionsManagerPerUserService) abstractPerUserSystemService;
        synchronized (this.mLock) {
            if (((SystemCaptionsManagerService) systemCaptionsManagerPerUserService.mMaster).verbose) {
                Slog.v("SystemCaptionsManagerPerUserService", "destroyLocked()");
            }
            RemoteSystemCaptionsManagerService remoteSystemCaptionsManagerService = systemCaptionsManagerPerUserService.mRemoteService;
            if (remoteSystemCaptionsManagerService != null) {
                remoteSystemCaptionsManagerService.mHandler.sendMessage(PooledLambda.obtainMessage(new RemoteSystemCaptionsManagerService$$ExternalSyntheticLambda0(1), remoteSystemCaptionsManagerService));
                systemCaptionsManagerPerUserService.mRemoteService = null;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
    }
}
