package com.android.server.connectivity;

import com.android.net.module.util.BinderUtils;

import java.util.Collections;

public final /* synthetic */ class Vpn$$ExternalSyntheticLambda1
        implements BinderUtils.ThrowingRunnable {
    public final /* synthetic */ Vpn f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ Vpn$$ExternalSyntheticLambda1(Vpn vpn, String str) {
        this.f$0 = vpn;
        this.f$1 = str;
    }

    public final void run() {
        Vpn vpn = this.f$0;
        vpn.mConnectivityManager.setVpnDefaultForUids(this.f$1, Collections.EMPTY_LIST);
    }
}
