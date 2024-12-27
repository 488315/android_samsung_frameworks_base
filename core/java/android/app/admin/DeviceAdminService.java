package android.app.admin;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DeviceAdminService extends Service {
    private final IDeviceAdminServiceImpl mImpl = new IDeviceAdminServiceImpl();

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mImpl.asBinder();
    }

    private class IDeviceAdminServiceImpl extends IDeviceAdminService.Stub {
        private IDeviceAdminServiceImpl() {}
    }
}
