package android.security;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.service.gatekeeper.IGateKeeperService;

/* loaded from: classes3.dex */
public final class GateKeeper {
    public static final long INVALID_SECURE_USER_ID = 0;

    private GateKeeper() {
    }

    public static IGateKeeperService getService() {
        IGateKeeperService iGateKeeperServiceAsInterface = IGateKeeperService.Stub.asInterface(ServiceManager.getService("android.service.gatekeeper.IGateKeeperService"));
        if (iGateKeeperServiceAsInterface != null) {
            return iGateKeeperServiceAsInterface;
        }
        throw new IllegalStateException("Gatekeeper service not available");
    }

    public static long getSecureUserId() throws IllegalStateException {
        return getSecureUserId(UserHandle.myUserId());
    }

    public static long getSecureUserId(int i) throws IllegalStateException {
        try {
            return getService().getSecureUserId(i);
        } catch (RemoteException e) {
            throw new IllegalStateException("Failed to obtain secure user ID from gatekeeper", e);
        }
    }
}
