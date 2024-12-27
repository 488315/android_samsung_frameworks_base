package android.security;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.service.gatekeeper.IGateKeeperService;

public abstract class GateKeeper {
    public static final long INVALID_SECURE_USER_ID = 0;

    private GateKeeper() {}

    public static IGateKeeperService getService() {
        IGateKeeperService service =
                IGateKeeperService.Stub.asInterface(
                        ServiceManager.getService("android.service.gatekeeper.IGateKeeperService"));
        if (service == null) {
            throw new IllegalStateException("Gatekeeper service not available");
        }
        return service;
    }

    public static long getSecureUserId() throws IllegalStateException {
        return getSecureUserId(UserHandle.myUserId());
    }

    public static long getSecureUserId(int userId) throws IllegalStateException {
        try {
            return getService().getSecureUserId(userId);
        } catch (RemoteException e) {
            throw new IllegalStateException("Failed to obtain secure user ID from gatekeeper", e);
        }
    }
}
