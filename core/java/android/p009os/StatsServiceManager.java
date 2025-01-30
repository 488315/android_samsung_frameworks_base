package android.p009os;

import android.annotation.SystemApi;
import android.content.Context;
import android.p009os.ServiceManager;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public class StatsServiceManager {

    public static final class ServiceRegisterer {
        private final String mServiceName;

        public ServiceRegisterer(String serviceName) {
            this.mServiceName = serviceName;
        }

        public IBinder get() {
            return ServiceManager.getService(this.mServiceName);
        }

        public IBinder getOrThrow() throws ServiceNotFoundException {
            try {
                return ServiceManager.getServiceOrThrow(this.mServiceName);
            } catch (ServiceManager.ServiceNotFoundException e) {
                throw new ServiceNotFoundException(this.mServiceName);
            }
        }

        private IBinder tryGet() {
            return ServiceManager.checkService(this.mServiceName);
        }
    }

    public static class ServiceNotFoundException extends ServiceManager.ServiceNotFoundException {
        public ServiceNotFoundException(String name) {
            super(name);
        }
    }

    public ServiceRegisterer getStatsCompanionServiceRegisterer() {
        return new ServiceRegisterer(Context.STATS_COMPANION_SERVICE);
    }

    public ServiceRegisterer getStatsManagerServiceRegisterer() {
        return new ServiceRegisterer(Context.STATS_MANAGER_SERVICE);
    }

    public ServiceRegisterer getStatsdServiceRegisterer() {
        return new ServiceRegisterer(Context.STATS_MANAGER);
    }
}
