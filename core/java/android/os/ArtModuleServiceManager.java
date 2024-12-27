package android.os;

import android.annotation.SystemApi;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
public class ArtModuleServiceManager {

    public static final class ServiceRegisterer {
        private final String mServiceName;

        public ServiceRegisterer(String serviceName) {
            this.mServiceName = serviceName;
        }

        public IBinder waitForService() {
            return ServiceManager.waitForService(this.mServiceName);
        }
    }

    public ServiceRegisterer getArtdServiceRegisterer() {
        return new ServiceRegisterer("artd");
    }

    public ServiceRegisterer getArtdPreRebootServiceRegisterer() {
        return new ServiceRegisterer("artd_pre_reboot");
    }

    public ServiceRegisterer getDexoptChrootSetupServiceRegisterer() {
        return new ServiceRegisterer("dexopt_chroot_setup");
    }
}
