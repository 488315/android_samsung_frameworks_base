package android.provider;

import android.annotation.SystemApi;
import android.os.IBinder;
import android.os.ServiceManager;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public class DeviceConfigServiceManager {

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final class ServiceRegisterer {
        private final String mServiceName;

        public ServiceRegisterer(String str) {
            this.mServiceName = str;
        }

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public void register(IBinder iBinder) {
            ServiceManager.addService(this.mServiceName, iBinder);
        }

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public IBinder get() {
            return ServiceManager.getService(this.mServiceName);
        }

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public IBinder getOrThrow() throws ServiceNotFoundException {
            try {
                return ServiceManager.getServiceOrThrow(this.mServiceName);
            } catch (ServiceManager.ServiceNotFoundException unused) {
                throw new ServiceNotFoundException(this.mServiceName);
            }
        }

        @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
        public IBinder tryGet() {
            return ServiceManager.checkService(this.mServiceName);
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static class ServiceNotFoundException extends ServiceManager.ServiceNotFoundException {
        public ServiceNotFoundException(String str) {
            super(str);
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public ServiceRegisterer getDeviceConfigUpdatableServiceRegisterer() {
        return new ServiceRegisterer("device_config_updatable");
    }
}
