package android.net.networkstack;

import android.net.INetworkMonitorCallbacks;
import android.net.INetworkStackConnector;
import android.net.Network;
import android.net.dhcp.DhcpServingParamsParcel;
import android.net.dhcp.IDhcpServerCallbacks;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class NetworkStackClientBase$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ String f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ NetworkStackClientBase$$ExternalSyntheticLambda0(Network network, String str, INetworkMonitorCallbacks iNetworkMonitorCallbacks) {
        this.f$1 = network;
        this.f$0 = str;
        this.f$2 = iNetworkMonitorCallbacks;
    }

    public /* synthetic */ NetworkStackClientBase$$ExternalSyntheticLambda0(String str, DhcpServingParamsParcel dhcpServingParamsParcel, IDhcpServerCallbacks iDhcpServerCallbacks) {
        this.f$0 = str;
        this.f$1 = dhcpServingParamsParcel;
        this.f$2 = iDhcpServerCallbacks;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                NetworkStackClientBase.lambda$makeDhcpServer$0(this.f$0, (DhcpServingParamsParcel) this.f$1, (IDhcpServerCallbacks) this.f$2, (INetworkStackConnector) obj);
                break;
            default:
                NetworkStackClientBase.lambda$makeNetworkMonitor$2((Network) this.f$1, this.f$0, (INetworkMonitorCallbacks) this.f$2, (INetworkStackConnector) obj);
                break;
        }
    }
}
