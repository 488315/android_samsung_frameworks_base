package com.android.server.appprediction;

import android.content.ComponentName;
import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.service.appprediction.IPredictionService;
import android.util.Slog;

import com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService;
import com.android.internal.infra.AbstractRemoteService;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RemoteAppPredictionService extends AbstractMultiplePendingRequestsRemoteService {
    public final RemoteAppPredictionServiceCallbacks mCallback;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface RemoteAppPredictionServiceCallbacks
            extends AbstractRemoteService.VultureCallback {}

    public RemoteAppPredictionService(
            Context context,
            ComponentName componentName,
            int i,
            RemoteAppPredictionServiceCallbacks remoteAppPredictionServiceCallbacks,
            boolean z,
            boolean z2) {
        super(
                context,
                "android.service.appprediction.AppPredictionService",
                componentName,
                i,
                remoteAppPredictionServiceCallbacks,
                context.getMainThreadHandler(),
                z ? 4194304 : 0,
                z2,
                1);
        this.mCallback = remoteAppPredictionServiceCallbacks;
    }

    public final void executeOnResolvedService(AbstractRemoteService.AsyncRequest asyncRequest) {
        executeAsyncRequest(asyncRequest);
    }

    public final long getRemoteRequestMillis() {
        return 2000L;
    }

    public final IInterface getServiceInterface(IBinder iBinder) {
        return IPredictionService.Stub.asInterface(iBinder);
    }

    public final long getTimeoutIdleBindMillis() {
        return 0L;
    }

    public final void handleOnConnectedStateChanged(boolean z) {
        RemoteAppPredictionServiceCallbacks remoteAppPredictionServiceCallbacks = this.mCallback;
        if (remoteAppPredictionServiceCallbacks != null) {
            AppPredictionPerUserService appPredictionPerUserService =
                    (AppPredictionPerUserService) remoteAppPredictionServiceCallbacks;
            if (appPredictionPerUserService.mMaster.debug) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        "onConnectedStateChanged(): connected=", "AppPredictionPerUserService", z);
            }
            if (z) {
                synchronized (appPredictionPerUserService.mLock) {
                    try {
                        if (appPredictionPerUserService.mZombie) {
                            if (appPredictionPerUserService.mRemoteService == null) {
                                Slog.w(
                                        "AppPredictionPerUserService",
                                        "Cannot resurrect sessions because remote service is null");
                            } else {
                                appPredictionPerUserService.mZombie = false;
                                appPredictionPerUserService.resurrectSessionsLocked();
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

    public final void scheduleOnResolvedService(AbstractRemoteService.AsyncRequest asyncRequest) {
        scheduleAsyncRequest(asyncRequest);
    }
}
