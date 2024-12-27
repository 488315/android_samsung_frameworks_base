package android.app;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.pm.IBackgroundInstallControlService;
import android.content.pm.PackageInfo;
import android.os.RemoteException;
import android.os.ServiceManager;

import java.util.List;

@SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
public final class BackgroundInstallControlManager {
    private static final String TAG = "BackgroundInstallControlManager";
    private static IBackgroundInstallControlService sService;
    private final Context mContext;

    BackgroundInstallControlManager(Context context) {
        this.mContext = context;
    }

    private static IBackgroundInstallControlService getService() {
        if (sService == null) {
            sService =
                    IBackgroundInstallControlService.Stub.asInterface(
                            ServiceManager.getService(Context.BACKGROUND_INSTALL_CONTROL_SERVICE));
        }
        return sService;
    }

    @SystemApi
    public List<PackageInfo> getBackgroundInstalledPackages(long flags) {
        try {
            return getService()
                    .getBackgroundInstalledPackages(flags, this.mContext.getUserId())
                    .getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
