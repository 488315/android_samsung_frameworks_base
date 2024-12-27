package com.android.server.searchui;

import android.content.ComponentName;
import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.service.search.ISearchUiService;
import android.util.Slog;

import com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService;
import com.android.internal.infra.AbstractRemoteService;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RemoteSearchUiService extends AbstractMultiplePendingRequestsRemoteService {
    public final RemoteSearchUiServiceCallbacks mCallback;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface RemoteSearchUiServiceCallbacks extends AbstractRemoteService.VultureCallback {}

    public RemoteSearchUiService(
            Context context,
            ComponentName componentName,
            int i,
            RemoteSearchUiServiceCallbacks remoteSearchUiServiceCallbacks,
            boolean z,
            boolean z2) {
        super(
                context,
                "android.service.search.SearchUiService",
                componentName,
                i,
                remoteSearchUiServiceCallbacks,
                context.getMainThreadHandler(),
                z ? 4194304 : 0,
                z2,
                1);
        this.mCallback = remoteSearchUiServiceCallbacks;
    }

    public final void executeOnResolvedService(AbstractRemoteService.AsyncRequest asyncRequest) {
        executeAsyncRequest(asyncRequest);
    }

    public final long getRemoteRequestMillis() {
        return 2000L;
    }

    public final IInterface getServiceInterface(IBinder iBinder) {
        return ISearchUiService.Stub.asInterface(iBinder);
    }

    public final long getTimeoutIdleBindMillis() {
        return 0L;
    }

    public final void handleOnConnectedStateChanged(boolean z) {
        RemoteSearchUiServiceCallbacks remoteSearchUiServiceCallbacks = this.mCallback;
        if (remoteSearchUiServiceCallbacks != null) {
            SearchUiPerUserService searchUiPerUserService =
                    (SearchUiPerUserService) remoteSearchUiServiceCallbacks;
            if (searchUiPerUserService.mMaster.debug) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        "onConnectedStateChanged(): connected=", "SearchUiPerUserService", z);
            }
            if (z) {
                synchronized (searchUiPerUserService.mLock) {
                    try {
                        if (searchUiPerUserService.mZombie) {
                            if (searchUiPerUserService.mRemoteService == null) {
                                Slog.w(
                                        "SearchUiPerUserService",
                                        "Cannot resurrect sessions because remote service is null");
                            } else {
                                searchUiPerUserService.mZombie = false;
                                searchUiPerUserService.resurrectSessionsLocked$2();
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
