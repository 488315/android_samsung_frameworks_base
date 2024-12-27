package android.telecom;

import android.content.ComponentName;
import android.os.RemoteException;
import com.android.internal.telecom.IConnectionService;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class RemoteConnectionManager {
    private final ConnectionService mOurConnectionServiceImpl;
    private final Map<ComponentName, RemoteConnectionService> mRemoteConnectionServices = new HashMap();

    public RemoteConnectionManager(ConnectionService ourConnectionServiceImpl) {
        this.mOurConnectionServiceImpl = ourConnectionServiceImpl;
    }

    void addConnectionService(final ComponentName componentName, final IConnectionService outgoingConnectionServiceRpc) {
        this.mRemoteConnectionServices.computeIfAbsent(componentName, new Function() { // from class: android.telecom.RemoteConnectionManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                RemoteConnectionService lambda$addConnectionService$0;
                lambda$addConnectionService$0 = RemoteConnectionManager.this.lambda$addConnectionService$0(outgoingConnectionServiceRpc, componentName, (ComponentName) obj);
                return lambda$addConnectionService$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ RemoteConnectionService lambda$addConnectionService$0(IConnectionService outgoingConnectionServiceRpc, ComponentName componentName, ComponentName key) {
        try {
            return new RemoteConnectionService(outgoingConnectionServiceRpc, this.mOurConnectionServiceImpl);
        } catch (RemoteException e) {
            Log.w(this, "error when addConnectionService of %s: %s", componentName, e.toString());
            return null;
        }
    }

    public RemoteConnection createRemoteConnection(PhoneAccountHandle connectionManagerPhoneAccount, ConnectionRequest request, boolean isIncoming) {
        PhoneAccountHandle accountHandle = request.getAccountHandle();
        if (accountHandle == null) {
            throw new IllegalArgumentException("accountHandle must be specified.");
        }
        ComponentName componentName = request.getAccountHandle().getComponentName();
        RemoteConnectionService remoteService = this.mRemoteConnectionServices.get(componentName);
        if (remoteService == null) {
            throw new UnsupportedOperationException("accountHandle not supported: " + componentName);
        }
        return remoteService.createRemoteConnection(connectionManagerPhoneAccount, request, isIncoming);
    }

    public RemoteConference createRemoteConference(PhoneAccountHandle connectionManagerPhoneAccount, ConnectionRequest request, boolean isIncoming) {
        PhoneAccountHandle accountHandle = request.getAccountHandle();
        if (accountHandle == null) {
            throw new IllegalArgumentException("accountHandle must be specified.");
        }
        ComponentName componentName = request.getAccountHandle().getComponentName();
        RemoteConnectionService remoteService = this.mRemoteConnectionServices.get(componentName);
        if (remoteService == null) {
            throw new UnsupportedOperationException("accountHandle not supported: " + componentName);
        }
        return remoteService.createRemoteConference(connectionManagerPhoneAccount, request, isIncoming);
    }

    public void conferenceRemoteConnections(RemoteConnection a, RemoteConnection b) {
        if (a.getConnectionService() == b.getConnectionService()) {
            try {
                a.getConnectionService().conference(a.getId(), b.getId(), null);
            } catch (RemoteException e) {
            }
        } else {
            Log.w(this, "Request to conference incompatible remote connections (%s,%s) (%s,%s)", a.getConnectionService(), a.getId(), b.getConnectionService(), b.getId());
        }
    }
}
