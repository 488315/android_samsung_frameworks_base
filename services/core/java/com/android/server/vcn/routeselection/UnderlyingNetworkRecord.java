package com.android.server.vcn.routeselection;

import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UnderlyingNetworkRecord {
    public final boolean isBlocked;
    public final LinkProperties linkProperties;
    public final Network network;
    public final NetworkCapabilities networkCapabilities;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Builder {
        public boolean mIsBlocked;
        public LinkProperties mLinkProperties;
        public final Network mNetwork;
        public NetworkCapabilities mNetworkCapabilities;
        public boolean mWasIsBlockedSet;

        public Builder(Network network) {
            this.mNetwork = network;
        }

        public final UnderlyingNetworkRecord build() {
            if (isValid()) {
                return new UnderlyingNetworkRecord(
                        this.mNetwork,
                        this.mNetworkCapabilities,
                        this.mLinkProperties,
                        this.mIsBlocked);
            }
            throw new IllegalArgumentException(
                    "Called build before UnderlyingNetworkRecord was valid");
        }

        public final boolean isValid() {
            return (this.mNetworkCapabilities == null
                            || this.mLinkProperties == null
                            || !this.mWasIsBlockedSet)
                    ? false
                    : true;
        }
    }

    public UnderlyingNetworkRecord(
            Network network,
            NetworkCapabilities networkCapabilities,
            LinkProperties linkProperties,
            boolean z) {
        this.network = network;
        this.networkCapabilities = networkCapabilities;
        this.linkProperties = linkProperties;
        this.isBlocked = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UnderlyingNetworkRecord)) {
            return false;
        }
        UnderlyingNetworkRecord underlyingNetworkRecord = (UnderlyingNetworkRecord) obj;
        return this.network.equals(underlyingNetworkRecord.network)
                && this.networkCapabilities.equals(underlyingNetworkRecord.networkCapabilities)
                && this.linkProperties.equals(underlyingNetworkRecord.linkProperties)
                && this.isBlocked == underlyingNetworkRecord.isBlocked;
    }

    public final int hashCode() {
        return Objects.hash(
                this.network,
                this.networkCapabilities,
                this.linkProperties,
                Boolean.valueOf(this.isBlocked));
    }
}
