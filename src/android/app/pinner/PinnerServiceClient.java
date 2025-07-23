package android.app.pinner;

import android.app.pinner.IPinnerService;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class PinnerServiceClient {
    private static String TAG = "PinnerServiceClient";

    public List<PinnedFileStat> getPinnerStats() {
        IBinder service = ServiceManager.getService("pinner");
        if (service == null) {
            Slog.w(TAG, "Failed to retrieve PinnerService. A common failure reason is due to a lack of selinux permissions.");
            return new ArrayList();
        }
        IPinnerService asInterface = IPinnerService.Stub.asInterface(service);
        if (asInterface == null) {
            Slog.w(TAG, "Failed to cast PinnerService.");
            return new ArrayList();
        }
        try {
            return asInterface.getPinnerStats();
        } catch (RemoteException unused) {
            throw new RuntimeException("Failed to retrieve stats from PinnerService");
        }
    }
}
