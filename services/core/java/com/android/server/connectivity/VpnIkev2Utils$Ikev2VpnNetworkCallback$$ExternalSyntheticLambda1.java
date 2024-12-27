package com.android.server.connectivity;

import android.net.Network;
import android.net.NetworkInfo;
import android.net.ipsec.ike.exceptions.IkeNetworkLostException;
import android.util.Log;

import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class VpnIkev2Utils$Ikev2VpnNetworkCallback$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ VpnIkev2Utils.Ikev2VpnNetworkCallback f$0;
    public final /* synthetic */ Network f$1;

    public /* synthetic */ VpnIkev2Utils$Ikev2VpnNetworkCallback$$ExternalSyntheticLambda1(
            VpnIkev2Utils.Ikev2VpnNetworkCallback ikev2VpnNetworkCallback, Network network, int i) {
        this.$r8$classId = i;
        this.f$0 = ikev2VpnNetworkCallback;
        this.f$1 = network;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                VpnIkev2Utils.Ikev2VpnNetworkCallback ikev2VpnNetworkCallback = this.f$0;
                Network network = this.f$1;
                Vpn.IkeV2VpnRunner ikeV2VpnRunner =
                        (Vpn.IkeV2VpnRunner) ikev2VpnNetworkCallback.mCallback;
                Vpn.this.mEventChanges.log("[UnderlyingNW] Default network changed to " + network);
                Log.d("IkeV2VpnRunner", "onDefaultNetworkChanged: " + network);
                if (ikeV2VpnRunner.mScheduledHandleRetryIkeSessionFuture != null) {
                    Log.d("IkeV2VpnRunner", "Cancel the task for handling new ike session timeout");
                    ikeV2VpnRunner.mScheduledHandleRetryIkeSessionFuture.cancel(false);
                    ikeV2VpnRunner.mScheduledHandleRetryIkeSessionFuture = null;
                }
                if (ikeV2VpnRunner.mScheduledHandleNetworkLostFuture != null) {
                    Log.d("IkeV2VpnRunner", "Cancel the task for handling network lost timeout");
                    ikeV2VpnRunner.mScheduledHandleNetworkLostFuture.cancel(false);
                    ikeV2VpnRunner.mScheduledHandleNetworkLostFuture = null;
                }
                if (!ikeV2VpnRunner.mIsRunning) {
                    Log.d("IkeV2VpnRunner", "onDefaultNetworkChanged after exit");
                    break;
                } else {
                    ikeV2VpnRunner.mActiveNetwork = network;
                    ikeV2VpnRunner.mUnderlyingLinkProperties = null;
                    ikeV2VpnRunner.mUnderlyingNetworkCapabilities = null;
                    ikeV2VpnRunner.mRetryCount = 0;
                    break;
                }
            default:
                VpnIkev2Utils.Ikev2VpnNetworkCallback ikev2VpnNetworkCallback2 = this.f$0;
                final Network network2 = this.f$1;
                final Vpn.IkeV2VpnRunner ikeV2VpnRunner2 =
                        (Vpn.IkeV2VpnRunner) ikev2VpnNetworkCallback2.mCallback;
                Vpn.this.mEventChanges.log("[UnderlyingNW] Network lost " + network2);
                if (ikeV2VpnRunner2.mScheduledHandleRetryIkeSessionFuture != null) {
                    Log.d("IkeV2VpnRunner", "Cancel the task for handling new ike session timeout");
                    ikeV2VpnRunner2.mScheduledHandleRetryIkeSessionFuture.cancel(false);
                    ikeV2VpnRunner2.mScheduledHandleRetryIkeSessionFuture = null;
                }
                if (!Objects.equals(ikeV2VpnRunner2.mActiveNetwork, network2)
                        || !ikeV2VpnRunner2.mIsRunning) {
                    Log.d(
                            "IkeV2VpnRunner",
                            "onDefaultNetworkLost called for obsolete network " + network2);
                    break;
                } else {
                    ikeV2VpnRunner2.mActiveNetwork = null;
                    ikeV2VpnRunner2.mUnderlyingNetworkCapabilities = null;
                    ikeV2VpnRunner2.mUnderlyingLinkProperties = null;
                    if (ikeV2VpnRunner2.mScheduledHandleNetworkLostFuture == null) {
                        StringBuilder sb =
                                new StringBuilder(
                                        "Schedule a delay handleSessionLost for losing network ");
                        sb.append(network2);
                        sb.append(" on session with token ");
                        GestureWakeup$$ExternalSyntheticOutline0.m(
                                sb, ikeV2VpnRunner2.mCurrentToken, "IkeV2VpnRunner");
                        final int i = ikeV2VpnRunner2.mCurrentToken;
                        ikeV2VpnRunner2.mScheduledHandleNetworkLostFuture =
                                ikeV2VpnRunner2.mExecutor.schedule(
                                        new Runnable() { // from class:
                                            // com.android.server.connectivity.Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda2
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                Vpn.IkeV2VpnRunner ikeV2VpnRunner3 =
                                                        Vpn.IkeV2VpnRunner.this;
                                                int i2 = i;
                                                Network network3 = network2;
                                                if (ikeV2VpnRunner3.isActiveToken(i2)) {
                                                    ikeV2VpnRunner3.handleSessionLost(
                                                            new IkeNetworkLostException(network3));
                                                    synchronized (Vpn.this) {
                                                        try {
                                                            Vpn vpn = Vpn.this;
                                                            if (vpn.mVpnRunner != ikeV2VpnRunner3) {
                                                                return;
                                                            } else {
                                                                vpn.updateState(
                                                                        NetworkInfo.DetailedState
                                                                                .DISCONNECTED,
                                                                        "Network lost");
                                                            }
                                                        } finally {
                                                        }
                                                    }
                                                } else {
                                                    NetworkScorerAppManager$$ExternalSyntheticOutline0
                                                            .m(
                                                                    i2,
                                                                    "Scheduled handleSessionLost"
                                                                        + " fired for obsolete"
                                                                        + " token ",
                                                                    "IkeV2VpnRunner");
                                                }
                                                ikeV2VpnRunner3.mScheduledHandleNetworkLostFuture =
                                                        null;
                                            }
                                        },
                                        5000L,
                                        TimeUnit.MILLISECONDS);
                        break;
                    } else {
                        IllegalStateException illegalStateException =
                                new IllegalStateException(
                                        "Found a pending mScheduledHandleNetworkLostFuture");
                        Log.i(
                                "IkeV2VpnRunner",
                                "Unexpected error in onDefaultNetworkLost. Tear down session",
                                illegalStateException);
                        ikeV2VpnRunner2.handleSessionLost(illegalStateException);
                        break;
                    }
                }
                break;
        }
    }
}
