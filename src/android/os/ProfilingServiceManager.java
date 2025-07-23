package android.os;

import android.annotation.SystemApi;
import android.os.ServiceManager;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public class ProfilingServiceManager {

    public static final class ServiceRegisterer {
        private final String mServiceName;

        public ServiceRegisterer(String str) {
            this.mServiceName = str;
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
    }

    public static class ServiceNotFoundException extends ServiceManager.ServiceNotFoundException {
        public ServiceNotFoundException(String str) {
            super(str);
        }
    }

    public ServiceRegisterer getProfilingServiceRegisterer() {
        return new ServiceRegisterer("profiling_service");
    }
}
