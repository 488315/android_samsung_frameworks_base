package com.android.server.connectivity;

import com.android.net.module.util.BinderUtils;

import java.util.Set;

public final /* synthetic */ class Vpn$$ExternalSyntheticLambda0
        implements BinderUtils.ThrowingRunnable {
    public final /* synthetic */ Vpn f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Set f$2;

    public /* synthetic */ Vpn$$ExternalSyntheticLambda0(Vpn vpn, String str, Set set) {
        this.f$0 = vpn;
        this.f$1 = str;
        this.f$2 = set;
    }

    public final void run() {
        Vpn vpn = this.f$0;
        vpn.mConnectivityManager.setVpnDefaultForUids(this.f$1, this.f$2);
    }
}
