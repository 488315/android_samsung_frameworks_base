package com.android.server.smartspace;

import android.content.ComponentName;
import android.content.Context;
import android.os.IBinder;
import android.service.smartspace.ISmartspaceService;
import com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService;
import com.android.internal.infra.AbstractRemoteService;

/* loaded from: classes3.dex */
public class RemoteSmartspaceService extends AbstractMultiplePendingRequestsRemoteService {
    public final RemoteSmartspaceServiceCallbacks mCallback;

    public interface RemoteSmartspaceServiceCallbacks extends AbstractRemoteService.VultureCallback {
        void onConnectedStateChanged(boolean z);
    }

    public long getRemoteRequestMillis() {
        return 2000L;
    }

    public long getTimeoutIdleBindMillis() {
        return 0L;
    }

    public RemoteSmartspaceService(Context context, String str, ComponentName componentName, int i, RemoteSmartspaceServiceCallbacks remoteSmartspaceServiceCallbacks, boolean z, boolean z2) {
        super(context, str, componentName, i, remoteSmartspaceServiceCallbacks, context.getMainThreadHandler(), z ? 4194304 : 0, z2, 1);
        this.mCallback = remoteSmartspaceServiceCallbacks;
    }

    public ISmartspaceService getServiceInterface(IBinder iBinder) {
        return ISmartspaceService.Stub.asInterface(iBinder);
    }

    public void reconnect() {
        super.scheduleBind();
    }

    public void executeOnResolvedService(AbstractRemoteService.AsyncRequest asyncRequest) {
        executeAsyncRequest(asyncRequest);
    }

    public void handleOnConnectedStateChanged(boolean z) {
        RemoteSmartspaceServiceCallbacks remoteSmartspaceServiceCallbacks = this.mCallback;
        if (remoteSmartspaceServiceCallbacks != null) {
            remoteSmartspaceServiceCallbacks.onConnectedStateChanged(z);
        }
    }
}
