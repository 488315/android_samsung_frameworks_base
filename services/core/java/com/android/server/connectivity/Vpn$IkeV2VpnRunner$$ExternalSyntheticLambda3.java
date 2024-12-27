package com.android.server.connectivity;

import android.net.Network;
import android.net.NetworkAgent;
import android.net.NetworkCapabilities;
import android.util.Log;
import com.android.internal.net.VpnConfig;
import com.android.net.module.util.BinderUtils;
import com.android.server.connectivity.Vpn;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Vpn.IkeV2VpnRunner f$0;

    public /* synthetic */ Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda3(Vpn.IkeV2VpnRunner ikeV2VpnRunner, int i) {
        this.$r8$classId = i;
        this.f$0 = ikeV2VpnRunner;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Vpn.IkeV2VpnRunner ikeV2VpnRunner = this.f$0;
        switch (i) {
            case 0:
                Network network = ikeV2VpnRunner.mActiveNetwork;
                synchronized (ikeV2VpnRunner.this$0) {
                    try {
                        Vpn vpn = ikeV2VpnRunner.this$0;
                        if (vpn.mVpnRunner == ikeV2VpnRunner) {
                            String str = ikeV2VpnRunner.mSessionKey;
                            int i2 = vpn.mUserId;
                            VpnConfig vpnConfig = vpn.mConfig;
                            BinderUtils.withCleanCallingIdentity(new Vpn$$ExternalSyntheticLambda0(vpn, str, vpn.createUserAndRestrictedProfilesRanges(i2, vpnConfig.allowedApplications, vpnConfig.disallowedApplications)));
                            if (network == null) {
                                Log.d("IkeV2VpnRunner", "There is no active network for starting an IKE session");
                            } else {
                                List singletonList = Collections.singletonList(network);
                                if (!singletonList.equals(ikeV2VpnRunner.this$0.mNetworkCapabilities.getUnderlyingNetworks())) {
                                    ikeV2VpnRunner.this$0.mNetworkCapabilities = new NetworkCapabilities.Builder(ikeV2VpnRunner.this$0.mNetworkCapabilities).setUnderlyingNetworks(singletonList).build();
                                    Vpn vpn2 = ikeV2VpnRunner.this$0;
                                    NetworkAgent networkAgent = vpn2.mNetworkAgent;
                                    if (networkAgent != null) {
                                        vpn2.logUnderlyNetworkChanges(singletonList);
                                        if (networkAgent instanceof Vpn.VpnNetworkAgentWrapper) {
                                            ((Vpn.VpnNetworkAgentWrapper) networkAgent).setUnderlyingNetworks(singletonList);
                                        } else {
                                            networkAgent.setUnderlyingNetworks(singletonList);
                                        }
                                    }
                                }
                                if (!ikeV2VpnRunner.maybeMigrateIkeSessionAndUpdateVpnTransportInfo(network)) {
                                    ikeV2VpnRunner.startIkeSession(network);
                                }
                            }
                        }
                    } finally {
                    }
                }
                ikeV2VpnRunner.mScheduledHandleRetryIkeSessionFuture = null;
                return;
            case 1:
                ikeV2VpnRunner.mValidationFailRetryCount = 0;
                if (ikeV2VpnRunner.mScheduledHandleDataStallFuture != null) {
                    Log.d("IkeV2VpnRunner", "Recovered from stall. Cancel pending reset action.");
                    ikeV2VpnRunner.mScheduledHandleDataStallFuture.cancel(false);
                    ikeV2VpnRunner.mScheduledHandleDataStallFuture = null;
                    return;
                }
                return;
            case 2:
                if (ikeV2VpnRunner.mValidationFailRetryCount > 0) {
                    Log.d("IkeV2VpnRunner", "Reset session to recover stalled network");
                    ikeV2VpnRunner.startIkeSession(ikeV2VpnRunner.mActiveNetwork);
                }
                ikeV2VpnRunner.mScheduledHandleDataStallFuture = null;
                return;
            case 3:
                ikeV2VpnRunner.disconnectVpnRunner();
                return;
            default:
                ikeV2VpnRunner.maybeMigrateIkeSessionAndUpdateVpnTransportInfo(ikeV2VpnRunner.mActiveNetwork);
                return;
        }
    }
}
