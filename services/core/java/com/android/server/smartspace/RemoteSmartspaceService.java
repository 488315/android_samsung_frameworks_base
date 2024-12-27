package com.android.server.smartspace;

import android.content.ComponentName;
import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.service.smartspace.ISmartspaceService;
import android.util.Slog;

import com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService;
import com.android.internal.infra.AbstractRemoteService;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RemoteSmartspaceService extends AbstractMultiplePendingRequestsRemoteService {
    public final RemoteSmartspaceServiceCallbacks mCallback;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface RemoteSmartspaceServiceCallbacks
            extends AbstractRemoteService.VultureCallback {}

    public RemoteSmartspaceService(
            Context context,
            ComponentName componentName,
            int i,
            RemoteSmartspaceServiceCallbacks remoteSmartspaceServiceCallbacks,
            boolean z,
            boolean z2) {
        super(
                context,
                "android.service.smartspace.SmartspaceService",
                componentName,
                i,
                remoteSmartspaceServiceCallbacks,
                context.getMainThreadHandler(),
                z ? 4194304 : 0,
                z2,
                1);
        this.mCallback = remoteSmartspaceServiceCallbacks;
    }

    public final void executeOnResolvedService(AbstractRemoteService.AsyncRequest asyncRequest) {
        executeAsyncRequest(asyncRequest);
    }

    public final long getRemoteRequestMillis() {
        return 2000L;
    }

    public final IInterface getServiceInterface(IBinder iBinder) {
        return ISmartspaceService.Stub.asInterface(iBinder);
    }

    public final long getTimeoutIdleBindMillis() {
        return 0L;
    }

    public final void handleOnConnectedStateChanged(boolean z) {
        RemoteSmartspaceServiceCallbacks remoteSmartspaceServiceCallbacks = this.mCallback;
        if (remoteSmartspaceServiceCallbacks != null) {
            SmartspacePerUserService smartspacePerUserService =
                    (SmartspacePerUserService) remoteSmartspaceServiceCallbacks;
            if (smartspacePerUserService.mMaster.debug) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        "onConnectedStateChanged(): connected=", "SmartspacePerUserService", z);
            }
            if (z) {
                synchronized (smartspacePerUserService.mLock) {
                    try {
                        if (smartspacePerUserService.mZombie) {
                            if (smartspacePerUserService.mRemoteService == null) {
                                Slog.w(
                                        "SmartspacePerUserService",
                                        "Cannot resurrect sessions because remote service is null");
                            } else {
                                smartspacePerUserService.mZombie = false;
                                smartspacePerUserService.resurrectSessionsLocked$3();
                            }
                        }
                    } finally {
                    }
                }
            }
        }
    }

    public final void reconnect() {
        scheduleBind();
    }
}
