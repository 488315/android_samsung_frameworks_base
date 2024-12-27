package android.net;

import android.net.NetworkStackClient;
import android.net.dhcp.DhcpServingParamsParcel;
import android.net.dhcp.IDhcpServerCallbacks;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class NetworkStackClient$$ExternalSyntheticLambda1 implements NetworkStackClient.NetworkStackCallback {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ NetworkStackClient$$ExternalSyntheticLambda1(Network network, String str, INetworkMonitorCallbacks iNetworkMonitorCallbacks) {
        this.f$0 = network;
        this.f$1 = str;
        this.f$2 = iNetworkMonitorCallbacks;
    }

    public /* synthetic */ NetworkStackClient$$ExternalSyntheticLambda1(String str, DhcpServingParamsParcel dhcpServingParamsParcel, IDhcpServerCallbacks iDhcpServerCallbacks) {
        this.f$1 = str;
        this.f$0 = dhcpServingParamsParcel;
        this.f$2 = iDhcpServerCallbacks;
    }

    @Override // android.net.NetworkStackClient.NetworkStackCallback
    public final void onNetworkStackConnected(INetworkStackConnector iNetworkStackConnector) {
        switch (this.$r8$classId) {
            case 0:
                NetworkStackClient.lambda$makeNetworkMonitor$2((Network) this.f$0, this.f$1, (INetworkMonitorCallbacks) this.f$2, iNetworkStackConnector);
                break;
            default:
                NetworkStackClient.lambda$makeDhcpServer$0(this.f$1, (DhcpServingParamsParcel) this.f$0, (IDhcpServerCallbacks) this.f$2, iNetworkStackConnector);
                break;
        }
    }
}
