package android.net.networkstack;

import android.content.Context;
import android.net.INetworkStackConnector;
import android.net.NetworkStack;
import android.os.IBinder;
import android.util.Log;

public class ModuleNetworkStackClient extends NetworkStackClientBase {
    private static final String TAG = "ModuleNetworkStackClient";
    private static ModuleNetworkStackClient sInstance;

    public final class PollingRunner implements Runnable {
        public PollingRunner() {}

        @Override // java.lang.Runnable
        public final void run() {
            while (true) {
                IBinder service = NetworkStack.getService();
                if (service != null) {
                    ModuleNetworkStackClient.this.onNetworkStackConnected(
                            INetworkStackConnector.Stub.asInterface(service));
                    return;
                } else {
                    try {
                        Thread.sleep(200L);
                    } catch (InterruptedException e) {
                        Log.e(
                                ModuleNetworkStackClient.TAG,
                                "Interrupted while waiting for NetworkStack connector",
                                e);
                    }
                }
            }
        }
    }

    private ModuleNetworkStackClient() {}

    public static synchronized ModuleNetworkStackClient getInstance(Context context) {
        ModuleNetworkStackClient moduleNetworkStackClient;
        synchronized (ModuleNetworkStackClient.class) {
            try {
                if (sInstance == null) {
                    ModuleNetworkStackClient moduleNetworkStackClient2 =
                            new ModuleNetworkStackClient();
                    sInstance = moduleNetworkStackClient2;
                    moduleNetworkStackClient2.startPolling();
                }
                moduleNetworkStackClient = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return moduleNetworkStackClient;
    }

    public static synchronized void resetInstanceForTest() {
        synchronized (ModuleNetworkStackClient.class) {
            sInstance = null;
        }
    }

    private void startPolling() {
        IBinder service = NetworkStack.getService();
        if (service != null) {
            onNetworkStackConnected(INetworkStackConnector.Stub.asInterface(service));
        } else {
            new Thread(new PollingRunner()).start();
        }
    }
}
