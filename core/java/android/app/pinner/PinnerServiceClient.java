package android.app.pinner;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;

import java.util.ArrayList;
import java.util.List;

public class PinnerServiceClient {
    private static String TAG = "PinnerServiceClient";

    public List<PinnedFileStat> getPinnerStats() {
        IBinder binder = ServiceManager.getService("pinner");
        if (binder == null) {
            Slog.w(
                    TAG,
                    "Failed to retrieve PinnerService. A common failure reason is due to a lack of"
                            + " selinux permissions.");
            return new ArrayList();
        }
        IPinnerService pinnerService = IPinnerService.Stub.asInterface(binder);
        if (pinnerService == null) {
            Slog.w(TAG, "Failed to cast PinnerService.");
            return new ArrayList();
        }
        try {
            List<PinnedFileStat> stats = pinnerService.getPinnerStats();
            return stats;
        } catch (RemoteException e) {
            throw new RuntimeException("Failed to retrieve stats from PinnerService");
        }
    }
}
