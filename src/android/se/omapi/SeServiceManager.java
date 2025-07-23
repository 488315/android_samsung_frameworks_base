package android.se.omapi;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.IBinder;
import android.os.ServiceManager;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public class SeServiceManager {

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final class ServiceRegisterer {
        private final String mServiceName;

        public ServiceRegisterer(String str) {
            this.mServiceName = str;
        }

        public void register(IBinder iBinder) {
            ServiceManager.addService(this.mServiceName, iBinder);
        }

        public IBinder get() {
            return ServiceManager.getService(this.mServiceName);
        }

        public IBinder getOrThrow() throws ServiceNotFoundException {
            try {
                return ServiceManager.getServiceOrThrow(this.mServiceName);
            } catch (ServiceManager.ServiceNotFoundException unused) {
                throw new ServiceNotFoundException(this.mServiceName);
            }
        }

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

    public ServiceRegisterer getSeManagerServiceRegisterer() {
        return new ServiceRegisterer(Context.SECURE_ELEMENT_SERVICE);
    }
}
