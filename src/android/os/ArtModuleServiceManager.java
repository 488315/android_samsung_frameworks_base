package android.os;

import android.annotation.SystemApi;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public class ArtModuleServiceManager {

    public static final class ServiceRegisterer {
        private final boolean mRetry;
        private final String mServiceName;

        public ServiceRegisterer(String str, boolean z) {
            this.mServiceName = str;
            this.mRetry = z;
        }

        public IBinder waitForService() throws InterruptedException {
            if (this.mRetry) {
                return ServiceManager.waitForService(this.mServiceName);
            }
            IBinder service = ServiceManager.getService(this.mServiceName);
            for (int i = 5000; service == null && i > 0; i -= 100) {
                SystemProperties.set("ctl.start", this.mServiceName);
                SystemClock.sleep(100L);
                service = ServiceManager.getService(this.mServiceName);
            }
            return service;
        }
    }

    public ServiceRegisterer getArtdServiceRegisterer() {
        return new ServiceRegisterer("artd", true);
    }

    public ServiceRegisterer getArtdPreRebootServiceRegisterer() {
        return new ServiceRegisterer("artd_pre_reboot", false);
    }

    public ServiceRegisterer getDexoptChrootSetupServiceRegisterer() {
        return new ServiceRegisterer("dexopt_chroot_setup", true);
    }
}
