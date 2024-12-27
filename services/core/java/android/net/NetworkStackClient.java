package android.net;

import android.net.ConnectivityModuleConnector;
import android.net.INetworkStackConnector;
import android.net.dhcp.DhcpServingParamsParcel;
import android.net.dhcp.IDhcpServerCallbacks;
import android.net.ip.IIpClientCallbacks;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class NetworkStackClient {
    private static final int NETWORKSTACK_TIMEOUT_MS = 10000;
    private static final String TAG = "NetworkStackClient";
    private static NetworkStackClient sInstance;
    private INetworkStackConnector mConnector;
    private final Dependencies mDependencies;
    private final ArrayList mPendingNetStackRequests;
    private volatile boolean mWasSystemServerInitialized;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Dependencies {
        void addToServiceManager(IBinder iBinder);

        void checkCallerUid();

        ConnectivityModuleConnector getConnectivityModuleConnector();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DependenciesImpl implements Dependencies {
        @Override // android.net.NetworkStackClient.Dependencies
        public final void addToServiceManager(IBinder iBinder) {
            ServiceManager.addService("network_stack", iBinder, false, 6);
        }

        @Override // android.net.NetworkStackClient.Dependencies
        public final void checkCallerUid() {
            int callingUid = Binder.getCallingUid();
            if (callingUid != 1000 && callingUid != 1073 && UserHandle.getAppId(callingUid) != 1002) {
                throw new SecurityException("Only the system server should try to bind to the network stack.");
            }
        }

        @Override // android.net.NetworkStackClient.Dependencies
        public final ConnectivityModuleConnector getConnectivityModuleConnector() {
            return ConnectivityModuleConnector.getInstance();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface NetworkStackCallback {
        void onNetworkStackConnected(INetworkStackConnector iNetworkStackConnector);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NetworkStackConnection implements ConnectivityModuleConnector.ModuleServiceCallback {
        public NetworkStackConnection() {
        }

        @Override // android.net.ConnectivityModuleConnector.ModuleServiceCallback
        public final void onModuleServiceConnected(IBinder iBinder) {
            NetworkStackClient networkStackClient = NetworkStackClient.this;
            networkStackClient.logi("Network stack service connected");
            networkStackClient.registerNetworkStackService(iBinder);
        }
    }

    private NetworkStackClient() {
        this(new DependenciesImpl());
    }

    public NetworkStackClient(Dependencies dependencies) {
        this.mPendingNetStackRequests = new ArrayList();
        this.mWasSystemServerInitialized = false;
        this.mDependencies = dependencies;
    }

    public static synchronized NetworkStackClient getInstance() {
        NetworkStackClient networkStackClient;
        synchronized (NetworkStackClient.class) {
            try {
                if (sInstance == null) {
                    sInstance = new NetworkStackClient();
                }
                networkStackClient = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return networkStackClient;
    }

    private INetworkStackConnector getRemoteConnector() {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            do {
                IBinder service = ServiceManager.getService("network_stack");
                if (service != null) {
                    return INetworkStackConnector.Stub.asInterface(service);
                }
                Thread.sleep(20L);
            } while (System.currentTimeMillis() - currentTimeMillis <= 10000);
            loge("Timeout waiting for NetworkStack connector", null);
            return null;
        } catch (InterruptedException e) {
            loge("Error waiting for NetworkStack connector", e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$fetchIpMemoryStore$3(IIpMemoryStoreCallbacks iIpMemoryStoreCallbacks, INetworkStackConnector iNetworkStackConnector) {
        try {
            iNetworkStackConnector.fetchIpMemoryStore(iIpMemoryStoreCallbacks);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$makeDhcpServer$0(String str, DhcpServingParamsParcel dhcpServingParamsParcel, IDhcpServerCallbacks iDhcpServerCallbacks, INetworkStackConnector iNetworkStackConnector) {
        try {
            iNetworkStackConnector.makeDhcpServer(str, dhcpServingParamsParcel, iDhcpServerCallbacks);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$makeIpClient$1(String str, IIpClientCallbacks iIpClientCallbacks, INetworkStackConnector iNetworkStackConnector) {
        try {
            iNetworkStackConnector.makeIpClient(str, iIpClientCallbacks);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$makeNetworkMonitor$2(Network network, String str, INetworkMonitorCallbacks iNetworkMonitorCallbacks, INetworkStackConnector iNetworkStackConnector) {
        try {
            iNetworkStackConnector.makeNetworkMonitor(network, str, iNetworkMonitorCallbacks);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    private void log(String str) {
        Log.d(TAG, str);
    }

    private void logWtf(String str, Throwable th) {
        String str2 = TAG;
        Slog.wtf(str2, str);
        Log.e(str2, str, th);
    }

    private void loge(String str, Throwable th) {
        Log.e(TAG, str, th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logi(String str) {
        Log.i(TAG, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerNetworkStackService(IBinder iBinder) {
        ArrayList arrayList;
        INetworkStackConnector asInterface = INetworkStackConnector.Stub.asInterface(iBinder);
        this.mDependencies.addToServiceManager(iBinder);
        log("Network stack service registered");
        synchronized (this.mPendingNetStackRequests) {
            arrayList = new ArrayList(this.mPendingNetStackRequests);
            this.mPendingNetStackRequests.clear();
            this.mConnector = asInterface;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((NetworkStackCallback) it.next()).onNetworkStackConnected(asInterface);
        }
    }

    private void requestConnector(NetworkStackCallback networkStackCallback) {
        this.mDependencies.checkCallerUid();
        if (!this.mWasSystemServerInitialized) {
            INetworkStackConnector remoteConnector = getRemoteConnector();
            synchronized (this.mPendingNetStackRequests) {
                this.mConnector = remoteConnector;
            }
            networkStackCallback.onNetworkStackConnected(remoteConnector);
            return;
        }
        synchronized (this.mPendingNetStackRequests) {
            try {
                INetworkStackConnector iNetworkStackConnector = this.mConnector;
                if (iNetworkStackConnector == null) {
                    this.mPendingNetStackRequests.add(networkStackCallback);
                } else {
                    networkStackCallback.onNetworkStackConnected(iNetworkStackConnector);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void fetchIpMemoryStore(final IIpMemoryStoreCallbacks iIpMemoryStoreCallbacks) {
        requestConnector(new NetworkStackCallback() { // from class: android.net.NetworkStackClient$$ExternalSyntheticLambda2
            @Override // android.net.NetworkStackClient.NetworkStackCallback
            public final void onNetworkStackConnected(INetworkStackConnector iNetworkStackConnector) {
                NetworkStackClient.lambda$fetchIpMemoryStore$3(IIpMemoryStoreCallbacks.this, iNetworkStackConnector);
            }
        });
    }

    public void init() {
        log("Network stack init");
        this.mWasSystemServerInitialized = true;
    }

    public void makeDhcpServer(String str, DhcpServingParamsParcel dhcpServingParamsParcel, IDhcpServerCallbacks iDhcpServerCallbacks) {
        requestConnector(new NetworkStackClient$$ExternalSyntheticLambda1(str, dhcpServingParamsParcel, iDhcpServerCallbacks));
    }

    public void makeIpClient(final String str, final IIpClientCallbacks iIpClientCallbacks) {
        requestConnector(new NetworkStackCallback() { // from class: android.net.NetworkStackClient$$ExternalSyntheticLambda0
            @Override // android.net.NetworkStackClient.NetworkStackCallback
            public final void onNetworkStackConnected(INetworkStackConnector iNetworkStackConnector) {
                NetworkStackClient.lambda$makeIpClient$1(str, iIpClientCallbacks, iNetworkStackConnector);
            }
        });
    }

    public void makeNetworkMonitor(Network network, String str, INetworkMonitorCallbacks iNetworkMonitorCallbacks) {
        requestConnector(new NetworkStackClient$$ExternalSyntheticLambda1(network, str, iNetworkMonitorCallbacks));
    }

    public void start() {
        this.mDependencies.getConnectivityModuleConnector().startModuleService(INetworkStackConnector.class.getName(), "android.permission.MAINLINE_NETWORK_STACK", new NetworkStackConnection());
        log("Network stack service start requested");
    }
}
