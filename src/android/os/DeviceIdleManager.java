package android.os;

import android.annotation.SystemApi;
import android.content.Context;

@SystemApi
/* loaded from: classes3.dex */
public class DeviceIdleManager {
    private final Context mContext;
    private final IDeviceIdleController mService;

    public DeviceIdleManager(Context context, IDeviceIdleController iDeviceIdleController) {
        this.mContext = context;
        this.mService = iDeviceIdleController;
    }

    IDeviceIdleController getService() {
        return this.mService;
    }

    public void endIdle(String str) {
        try {
            this.mService.exitIdle(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String[] getSystemPowerWhitelistExceptIdle() {
        try {
            return this.mService.getSystemPowerWhitelistExceptIdle();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String[] getSystemPowerWhitelist() {
        try {
            return this.mService.getSystemPowerWhitelist();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
