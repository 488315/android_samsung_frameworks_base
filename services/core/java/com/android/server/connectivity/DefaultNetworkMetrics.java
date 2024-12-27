package com.android.server.connectivity;

import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.metrics.DefaultNetworkEvent;
import android.os.SystemClock;

import com.android.internal.util.BitUtils;
import com.android.internal.util.RingBuffer;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DefaultNetworkMetrics {
    public DefaultNetworkEvent mCurrentDefaultNetwork;
    public final List mEvents;
    public final RingBuffer mEventsLog;
    public boolean mIsCurrentlyValid;
    public int mLastTransports;
    public long mLastValidationTimeMs;

    public DefaultNetworkMetrics() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mEvents = new ArrayList();
        this.mEventsLog = new RingBuffer(DefaultNetworkEvent.class, 64);
        DefaultNetworkEvent defaultNetworkEvent = new DefaultNetworkEvent(elapsedRealtime);
        defaultNetworkEvent.durationMs = elapsedRealtime;
        this.mIsCurrentlyValid = false;
        this.mCurrentDefaultNetwork = defaultNetworkEvent;
    }

    public static void fillLinkInfo(
            DefaultNetworkEvent defaultNetworkEvent,
            Network network,
            LinkProperties linkProperties,
            NetworkCapabilities networkCapabilities) {
        defaultNetworkEvent.netId = network.getNetId();
        defaultNetworkEvent.transports =
                (int)
                        (defaultNetworkEvent.transports
                                | BitUtils.packBits(networkCapabilities.getTransportTypes()));
        boolean z = false;
        defaultNetworkEvent.ipv4 |=
                linkProperties.hasIpv4Address() && linkProperties.hasIpv4DefaultRoute();
        boolean z2 = defaultNetworkEvent.ipv6;
        if (linkProperties.hasGlobalIpv6Address() && linkProperties.hasIpv6DefaultRoute()) {
            z = true;
        }
        defaultNetworkEvent.ipv6 = z2 | z;
    }

    public final void logCurrentDefaultNetwork(
            long j,
            Network network,
            int i,
            LinkProperties linkProperties,
            NetworkCapabilities networkCapabilities) {
        if (this.mIsCurrentlyValid) {
            DefaultNetworkEvent defaultNetworkEvent = this.mCurrentDefaultNetwork;
            defaultNetworkEvent.validatedMs =
                    (j - this.mLastValidationTimeMs) + defaultNetworkEvent.validatedMs;
        }
        DefaultNetworkEvent defaultNetworkEvent2 = this.mCurrentDefaultNetwork;
        defaultNetworkEvent2.updateDuration(j);
        defaultNetworkEvent2.previousTransports = this.mLastTransports;
        if (network != null) {
            fillLinkInfo(defaultNetworkEvent2, network, linkProperties, networkCapabilities);
            defaultNetworkEvent2.finalScore = i;
        }
        int i2 = defaultNetworkEvent2.transports;
        if (i2 != 0) {
            this.mLastTransports = i2;
        }
        ((ArrayList) this.mEvents).add(defaultNetworkEvent2);
        this.mEventsLog.append(defaultNetworkEvent2);
    }
}
