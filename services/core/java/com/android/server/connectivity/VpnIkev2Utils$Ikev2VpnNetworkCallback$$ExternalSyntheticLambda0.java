package com.android.server.connectivity;

import android.net.LinkProperties;
import android.net.NetworkCapabilities;

import com.android.net.module.util.LinkPropertiesUtils;

import java.util.Arrays;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class VpnIkev2Utils$Ikev2VpnNetworkCallback$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ VpnIkev2Utils.Ikev2VpnNetworkCallback f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ VpnIkev2Utils$Ikev2VpnNetworkCallback$$ExternalSyntheticLambda0(
            VpnIkev2Utils.Ikev2VpnNetworkCallback ikev2VpnNetworkCallback,
            LinkProperties linkProperties) {
        this.f$0 = ikev2VpnNetworkCallback;
        this.f$1 = linkProperties;
    }

    public /* synthetic */ VpnIkev2Utils$Ikev2VpnNetworkCallback$$ExternalSyntheticLambda0(
            VpnIkev2Utils.Ikev2VpnNetworkCallback ikev2VpnNetworkCallback,
            NetworkCapabilities networkCapabilities) {
        this.f$0 = ikev2VpnNetworkCallback;
        this.f$1 = networkCapabilities;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                VpnIkev2Utils.Ikev2VpnNetworkCallback ikev2VpnNetworkCallback = this.f$0;
                LinkProperties linkProperties = (LinkProperties) this.f$1;
                Vpn.IkeV2VpnRunner ikeV2VpnRunner =
                        (Vpn.IkeV2VpnRunner) ikev2VpnNetworkCallback.mCallback;
                LinkProperties linkProperties2 = ikeV2VpnRunner.mUnderlyingLinkProperties;
                Vpn.this.mEventChanges.log(
                        "[UnderlyingNW] Lp changed from "
                                + linkProperties2
                                + " to "
                                + linkProperties);
                ikeV2VpnRunner.mUnderlyingLinkProperties = linkProperties;
                if (linkProperties2 == null
                        || !LinkPropertiesUtils.isIdenticalAllLinkAddresses(
                                linkProperties2, linkProperties)) {
                    ikeV2VpnRunner.scheduleStartIkeSession(300L);
                    break;
                }
                break;
            default:
                VpnIkev2Utils.Ikev2VpnNetworkCallback ikev2VpnNetworkCallback2 = this.f$0;
                NetworkCapabilities networkCapabilities = (NetworkCapabilities) this.f$1;
                Vpn.IkeV2VpnRunner ikeV2VpnRunner2 =
                        (Vpn.IkeV2VpnRunner) ikev2VpnNetworkCallback2.mCallback;
                NetworkCapabilities networkCapabilities2 =
                        ikeV2VpnRunner2.mUnderlyingNetworkCapabilities;
                if (networkCapabilities2 != networkCapabilities
                        && (networkCapabilities2 == null
                                || networkCapabilities == null
                                || !Arrays.equals(
                                        networkCapabilities2.getTransportTypes(),
                                        networkCapabilities.getTransportTypes())
                                || !Arrays.equals(
                                        networkCapabilities2.getCapabilities(),
                                        networkCapabilities.getCapabilities())
                                || !Arrays.equals(
                                        networkCapabilities2.getEnterpriseIds(),
                                        networkCapabilities.getEnterpriseIds())
                                || !Objects.equals(
                                        networkCapabilities2.getTransportInfo(),
                                        networkCapabilities.getTransportInfo())
                                || !Objects.equals(
                                        networkCapabilities2.getAllowedUids(),
                                        networkCapabilities.getAllowedUids())
                                || !Objects.equals(
                                        networkCapabilities2.getUnderlyingNetworks(),
                                        networkCapabilities.getUnderlyingNetworks())
                                || !Objects.equals(
                                        networkCapabilities2.getNetworkSpecifier(),
                                        networkCapabilities.getNetworkSpecifier()))) {
                    Vpn.this.mEventChanges.log(
                            "[UnderlyingNW] Cap changed from "
                                    + ikeV2VpnRunner2.mUnderlyingNetworkCapabilities
                                    + " to "
                                    + networkCapabilities);
                }
                NetworkCapabilities networkCapabilities3 =
                        ikeV2VpnRunner2.mUnderlyingNetworkCapabilities;
                ikeV2VpnRunner2.mUnderlyingNetworkCapabilities = networkCapabilities;
                if (networkCapabilities3 == null
                        || !networkCapabilities
                                .getSubscriptionIds()
                                .equals(networkCapabilities3.getSubscriptionIds())) {
                    ikeV2VpnRunner2.scheduleStartIkeSession(300L);
                    break;
                }
                break;
        }
    }
}
