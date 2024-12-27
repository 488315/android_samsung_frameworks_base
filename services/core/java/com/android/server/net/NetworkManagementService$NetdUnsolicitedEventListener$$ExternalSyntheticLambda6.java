package com.android.server.net;

import android.net.INetworkManagementEventObserver;
import android.net.LinkAddress;
import com.android.server.net.NetworkManagementService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NetworkManagementService.NetdUnsolicitedEventListener f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda6(NetworkManagementService.NetdUnsolicitedEventListener netdUnsolicitedEventListener, String str, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = netdUnsolicitedEventListener;
        this.f$1 = str;
        this.f$2 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NetworkManagementService.NetdUnsolicitedEventListener netdUnsolicitedEventListener = this.f$0;
                final String str = this.f$1;
                final LinkAddress linkAddress = (LinkAddress) this.f$2;
                NetworkManagementService networkManagementService = netdUnsolicitedEventListener.this$0;
                networkManagementService.getClass();
                final int i = 0;
                networkManagementService.invokeForAllObservers(new NetworkManagementService.NetworkManagementEventCallback() { // from class: com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda2
                    @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
                    public final void sendCallback(INetworkManagementEventObserver iNetworkManagementEventObserver) {
                        switch (i) {
                            case 0:
                                iNetworkManagementEventObserver.addressRemoved(str, (LinkAddress) linkAddress);
                                break;
                            case 1:
                                iNetworkManagementEventObserver.addressUpdated(str, (LinkAddress) linkAddress);
                                break;
                            default:
                                iNetworkManagementEventObserver.limitReached(str, (String) linkAddress);
                                break;
                        }
                    }
                });
                break;
            case 1:
                NetworkManagementService.NetdUnsolicitedEventListener netdUnsolicitedEventListener2 = this.f$0;
                final String str2 = this.f$1;
                final LinkAddress linkAddress2 = (LinkAddress) this.f$2;
                NetworkManagementService networkManagementService2 = netdUnsolicitedEventListener2.this$0;
                networkManagementService2.getClass();
                final int i2 = 1;
                networkManagementService2.invokeForAllObservers(new NetworkManagementService.NetworkManagementEventCallback() { // from class: com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda2
                    @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
                    public final void sendCallback(INetworkManagementEventObserver iNetworkManagementEventObserver) {
                        switch (i2) {
                            case 0:
                                iNetworkManagementEventObserver.addressRemoved(str2, (LinkAddress) linkAddress2);
                                break;
                            case 1:
                                iNetworkManagementEventObserver.addressUpdated(str2, (LinkAddress) linkAddress2);
                                break;
                            default:
                                iNetworkManagementEventObserver.limitReached(str2, (String) linkAddress2);
                                break;
                        }
                    }
                });
                break;
            default:
                NetworkManagementService.NetdUnsolicitedEventListener netdUnsolicitedEventListener3 = this.f$0;
                final String str3 = this.f$1;
                final String str4 = (String) this.f$2;
                NetworkManagementService networkManagementService3 = netdUnsolicitedEventListener3.this$0;
                networkManagementService3.getClass();
                final int i3 = 2;
                networkManagementService3.invokeForAllObservers(new NetworkManagementService.NetworkManagementEventCallback() { // from class: com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda2
                    @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
                    public final void sendCallback(INetworkManagementEventObserver iNetworkManagementEventObserver) {
                        switch (i3) {
                            case 0:
                                iNetworkManagementEventObserver.addressRemoved(str3, (LinkAddress) str4);
                                break;
                            case 1:
                                iNetworkManagementEventObserver.addressUpdated(str3, (LinkAddress) str4);
                                break;
                            default:
                                iNetworkManagementEventObserver.limitReached(str3, (String) str4);
                                break;
                        }
                    }
                });
                break;
        }
    }
}
