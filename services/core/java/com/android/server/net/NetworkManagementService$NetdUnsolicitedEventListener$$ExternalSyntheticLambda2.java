package com.android.server.net;

import android.net.INetworkManagementEventObserver;
import android.net.RouteInfo;
import android.os.RemoteException;

public final /* synthetic */
class NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NetworkManagementService.NetdUnsolicitedEventListener f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */
    NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda2(
            NetworkManagementService.NetdUnsolicitedEventListener netdUnsolicitedEventListener,
            String str,
            boolean z,
            int i) {
        this.$r8$classId = i;
        this.f$0 = netdUnsolicitedEventListener;
        this.f$1 = str;
        this.f$2 = z;
    }

    public /* synthetic */
    NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda2(
            NetworkManagementService.NetdUnsolicitedEventListener netdUnsolicitedEventListener,
            boolean z,
            RouteInfo routeInfo) {
        this.$r8$classId = 2;
        this.f$0 = netdUnsolicitedEventListener;
        this.f$2 = z;
        this.f$1 = routeInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NetworkManagementService.NetdUnsolicitedEventListener netdUnsolicitedEventListener =
                        this.f$0;
                final String str = (String) this.f$1;
                final boolean z = this.f$2;
                NetworkManagementService networkManagementService =
                        netdUnsolicitedEventListener.this$0;
                networkManagementService.getClass();
                final int i = 1;
                networkManagementService.invokeForAllObservers(
                        new NetworkManagementService
                                .NetworkManagementEventCallback() { // from class:
                                                                    // com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda1
                            @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
                            public final void sendCallback(
                                    INetworkManagementEventObserver
                                            iNetworkManagementEventObserver) {
                                switch (i) {
                                    case 0:
                                        iNetworkManagementEventObserver.interfaceStatusChanged(
                                                str, z);
                                        break;
                                    default:
                                        iNetworkManagementEventObserver.interfaceLinkStateChanged(
                                                str, z);
                                        break;
                                }
                            }
                        });
                return;
            case 1:
                NetworkManagementService.NetdUnsolicitedEventListener
                        netdUnsolicitedEventListener2 = this.f$0;
                final String str2 = (String) this.f$1;
                final boolean z2 = this.f$2;
                NetworkManagementService networkManagementService2 =
                        netdUnsolicitedEventListener2.this$0;
                networkManagementService2.getClass();
                final int i2 = 0;
                networkManagementService2.invokeForAllObservers(
                        new NetworkManagementService
                                .NetworkManagementEventCallback() { // from class:
                                                                    // com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda1
                            @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
                            public final void sendCallback(
                                    INetworkManagementEventObserver
                                            iNetworkManagementEventObserver) {
                                switch (i2) {
                                    case 0:
                                        iNetworkManagementEventObserver.interfaceStatusChanged(
                                                str2, z2);
                                        break;
                                    default:
                                        iNetworkManagementEventObserver.interfaceLinkStateChanged(
                                                str2, z2);
                                        break;
                                }
                            }
                        });
                return;
            default:
                NetworkManagementService.NetdUnsolicitedEventListener
                        netdUnsolicitedEventListener3 = this.f$0;
                boolean z3 = this.f$2;
                RouteInfo routeInfo = (RouteInfo) this.f$1;
                NetworkManagementService networkManagementService3 =
                        netdUnsolicitedEventListener3.this$0;
                int i3 = 0;
                if (z3) {
                    int beginBroadcast = networkManagementService3.mObservers.beginBroadcast();
                    while (i3 < beginBroadcast) {
                        try {
                            networkManagementService3
                                    .mObservers
                                    .getBroadcastItem(i3)
                                    .routeUpdated(routeInfo);
                        } catch (RemoteException | RuntimeException unused) {
                        } catch (Throwable th) {
                            networkManagementService3.mObservers.finishBroadcast();
                            throw th;
                        }
                        i3++;
                    }
                    networkManagementService3.mObservers.finishBroadcast();
                    return;
                }
                int beginBroadcast2 = networkManagementService3.mObservers.beginBroadcast();
                while (i3 < beginBroadcast2) {
                    try {
                        networkManagementService3
                                .mObservers
                                .getBroadcastItem(i3)
                                .routeRemoved(routeInfo);
                    } catch (RemoteException | RuntimeException unused2) {
                    } catch (Throwable th2) {
                        networkManagementService3.mObservers.finishBroadcast();
                        throw th2;
                    }
                    i3++;
                }
                networkManagementService3.mObservers.finishBroadcast();
                return;
        }
    }
}
