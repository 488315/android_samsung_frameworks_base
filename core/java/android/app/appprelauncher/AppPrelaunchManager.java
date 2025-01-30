package android.app.appprelauncher;

import android.p009os.RemoteException;
import android.util.Slog;

/* loaded from: classes.dex */
public final class AppPrelaunchManager {
    private static final String TAG_PREL = "PREL_SYS";
    private final IAppPrelaunchService mService;

    public AppPrelaunchManager(IAppPrelaunchService service) {
        this.mService = service;
        if (service == null) {
            Slog.m121w(TAG_PREL, "Service is null");
        }
    }

    public boolean isPrelaunched(int uid) {
        try {
            IAppPrelaunchService iAppPrelaunchService = this.mService;
            if (iAppPrelaunchService != null) {
                return iAppPrelaunchService.isAppPrelaunched(uid);
            }
            return false;
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean stopPrelaunch(int uid, String reason) {
        try {
            IAppPrelaunchService iAppPrelaunchService = this.mService;
            if (iAppPrelaunchService != null) {
                return iAppPrelaunchService.killApp(uid, reason);
            }
            return false;
        } catch (RemoteException e) {
            return false;
        }
    }
}
