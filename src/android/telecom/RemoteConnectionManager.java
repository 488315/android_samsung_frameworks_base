package android.telecom;

import android.content.ComponentName;
import android.os.RemoteException;
import com.android.internal.telecom.IConnectionService;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/* loaded from: classes4.dex */
public class RemoteConnectionManager {
    private final ConnectionService mOurConnectionServiceImpl;
    private final Map<ComponentName, RemoteConnectionService> mRemoteConnectionServices = new HashMap();

    public RemoteConnectionManager(ConnectionService connectionService) {
        this.mOurConnectionServiceImpl = connectionService;
    }

    void addConnectionService(final ComponentName componentName, final IConnectionService iConnectionService) {
        this.mRemoteConnectionServices.computeIfAbsent(componentName, new Function() { // from class: android.telecom.RemoteConnectionManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.lambda$addConnectionService$0(iConnectionService, componentName, (ComponentName) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ RemoteConnectionService lambda$addConnectionService$0(IConnectionService iConnectionService, ComponentName componentName, ComponentName componentName2) {
        try {
            return new RemoteConnectionService(iConnectionService, this.mOurConnectionServiceImpl);
        } catch (RemoteException e) {
            Log.w(this, "error when addConnectionService of %s: %s", componentName, e.toString());
            return null;
        }
    }

    public RemoteConnection createRemoteConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest, boolean z) {
        if (connectionRequest.getAccountHandle() == null) {
            throw new IllegalArgumentException("accountHandle must be specified.");
        }
        ComponentName componentName = connectionRequest.getAccountHandle().getComponentName();
        RemoteConnectionService remoteConnectionService = this.mRemoteConnectionServices.get(componentName);
        if (remoteConnectionService == null) {
            throw new UnsupportedOperationException("accountHandle not supported: " + componentName);
        }
        return remoteConnectionService.createRemoteConnection(phoneAccountHandle, connectionRequest, z);
    }

    public RemoteConference createRemoteConference(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest, boolean z) {
        if (connectionRequest.getAccountHandle() == null) {
            throw new IllegalArgumentException("accountHandle must be specified.");
        }
        ComponentName componentName = connectionRequest.getAccountHandle().getComponentName();
        RemoteConnectionService remoteConnectionService = this.mRemoteConnectionServices.get(componentName);
        if (remoteConnectionService == null) {
            throw new UnsupportedOperationException("accountHandle not supported: " + componentName);
        }
        return remoteConnectionService.createRemoteConference(phoneAccountHandle, connectionRequest, z);
    }

    public void conferenceRemoteConnections(RemoteConnection remoteConnection, RemoteConnection remoteConnection2) {
        if (remoteConnection.getConnectionService() == remoteConnection2.getConnectionService()) {
            try {
                remoteConnection.getConnectionService().conference(remoteConnection.getId(), remoteConnection2.getId(), null);
            } catch (RemoteException unused) {
            }
        } else {
            Log.w(this, "Request to conference incompatible remote connections (%s,%s) (%s,%s)", remoteConnection.getConnectionService(), remoteConnection.getId(), remoteConnection2.getConnectionService(), remoteConnection2.getId());
        }
    }
}
