package android.net.networkstack;

import android.net.IIpMemoryStoreCallbacks;
import android.net.INetworkMonitorCallbacks;
import android.net.INetworkStackConnector;
import android.net.Network;
import android.net.dhcp.DhcpServingParamsParcel;
import android.net.dhcp.IDhcpServerCallbacks;
import android.net.ip.IIpClientCallbacks;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class NetworkStackClientBase {
    private INetworkStackConnector mConnector;
    private final ArrayList mPendingNetStackRequests = new ArrayList();

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$fetchIpMemoryStore$3(IIpMemoryStoreCallbacks iIpMemoryStoreCallbacks, INetworkStackConnector iNetworkStackConnector) {
        try {
            iNetworkStackConnector.fetchIpMemoryStore(iIpMemoryStoreCallbacks);
        } catch (RemoteException e) {
            throw new IllegalStateException("Could not fetch IpMemoryStore", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$makeDhcpServer$0(String str, DhcpServingParamsParcel dhcpServingParamsParcel, IDhcpServerCallbacks iDhcpServerCallbacks, INetworkStackConnector iNetworkStackConnector) {
        try {
            iNetworkStackConnector.makeDhcpServer(str, dhcpServingParamsParcel, iDhcpServerCallbacks);
        } catch (RemoteException e) {
            throw new IllegalStateException("Could not create DhcpServer", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$makeIpClient$1(String str, IIpClientCallbacks iIpClientCallbacks, INetworkStackConnector iNetworkStackConnector) {
        try {
            iNetworkStackConnector.makeIpClient(str, iIpClientCallbacks);
        } catch (RemoteException e) {
            throw new IllegalStateException("Could not create IpClient", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$makeNetworkMonitor$2(Network network, String str, INetworkMonitorCallbacks iNetworkMonitorCallbacks, INetworkStackConnector iNetworkStackConnector) {
        try {
            iNetworkStackConnector.makeNetworkMonitor(network, str, iNetworkMonitorCallbacks);
        } catch (RemoteException e) {
            throw new IllegalStateException("Could not create NetworkMonitor", e);
        }
    }

    public void fetchIpMemoryStore(final IIpMemoryStoreCallbacks iIpMemoryStoreCallbacks) {
        requestConnector(new Consumer() { // from class: android.net.networkstack.NetworkStackClientBase$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NetworkStackClientBase.lambda$fetchIpMemoryStore$3(IIpMemoryStoreCallbacks.this, (INetworkStackConnector) obj);
            }
        });
    }

    public int getQueueLength() {
        int size;
        synchronized (this.mPendingNetStackRequests) {
            size = this.mPendingNetStackRequests.size();
        }
        return size;
    }

    public void makeDhcpServer(String str, DhcpServingParamsParcel dhcpServingParamsParcel, IDhcpServerCallbacks iDhcpServerCallbacks) {
        requestConnector(new NetworkStackClientBase$$ExternalSyntheticLambda0(str, dhcpServingParamsParcel, iDhcpServerCallbacks));
    }

    public void makeIpClient(final String str, final IIpClientCallbacks iIpClientCallbacks) {
        requestConnector(new Consumer() { // from class: android.net.networkstack.NetworkStackClientBase$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NetworkStackClientBase.lambda$makeIpClient$1(str, iIpClientCallbacks, (INetworkStackConnector) obj);
            }
        });
    }

    public void makeNetworkMonitor(Network network, String str, INetworkMonitorCallbacks iNetworkMonitorCallbacks) {
        requestConnector(new NetworkStackClientBase$$ExternalSyntheticLambda0(network, str, iNetworkMonitorCallbacks));
    }

    public void onNetworkStackConnected(INetworkStackConnector iNetworkStackConnector) {
        ArrayList arrayList;
        while (true) {
            synchronized (this.mPendingNetStackRequests) {
                arrayList = new ArrayList(this.mPendingNetStackRequests);
                this.mPendingNetStackRequests.clear();
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((Consumer) it.next()).accept(iNetworkStackConnector);
            }
            synchronized (this.mPendingNetStackRequests) {
                try {
                    if (this.mPendingNetStackRequests.size() == 0) {
                        this.mConnector = iNetworkStackConnector;
                        return;
                    }
                } finally {
                }
            }
        }
    }

    public void requestConnector(Consumer consumer) {
        synchronized (this.mPendingNetStackRequests) {
            try {
                INetworkStackConnector iNetworkStackConnector = this.mConnector;
                if (iNetworkStackConnector == null) {
                    this.mPendingNetStackRequests.add(consumer);
                } else {
                    consumer.accept(iNetworkStackConnector);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
