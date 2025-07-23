package android.net.metrics;

import android.annotation.SystemApi;
import android.net.metrics.IpConnectivityLog;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
@Deprecated
/* loaded from: classes3.dex */
public final class RaEvent implements IpConnectivityLog.Event {
    public static final Parcelable.Creator<RaEvent> CREATOR = new Parcelable.Creator<RaEvent>() { // from class: android.net.metrics.RaEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RaEvent createFromParcel(Parcel parcel) {
            return new RaEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RaEvent[] newArray(int i) {
            return new RaEvent[i];
        }
    };
    private static final long NO_LIFETIME = -1;
    public final long dnsslLifetime;
    public final long prefixPreferredLifetime;
    public final long prefixValidLifetime;
    public final long rdnssLifetime;
    public final long routeInfoLifetime;
    public final long routerLifetime;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RaEvent(long j, long j2, long j3, long j4, long j5, long j6) {
        this.routerLifetime = j;
        this.prefixValidLifetime = j2;
        this.prefixPreferredLifetime = j3;
        this.routeInfoLifetime = j4;
        this.rdnssLifetime = j5;
        this.dnsslLifetime = j6;
    }

    private RaEvent(Parcel parcel) {
        this.routerLifetime = parcel.readLong();
        this.prefixValidLifetime = parcel.readLong();
        this.prefixPreferredLifetime = parcel.readLong();
        this.routeInfoLifetime = parcel.readLong();
        this.rdnssLifetime = parcel.readLong();
        this.dnsslLifetime = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.routerLifetime);
        parcel.writeLong(this.prefixValidLifetime);
        parcel.writeLong(this.prefixPreferredLifetime);
        parcel.writeLong(this.routeInfoLifetime);
        parcel.writeLong(this.rdnssLifetime);
        parcel.writeLong(this.dnsslLifetime);
    }

    public String toString() {
        return "RaEvent(lifetimes: " + String.format("router=%ds, ", Long.valueOf(this.routerLifetime)) + String.format("prefix_valid=%ds, ", Long.valueOf(this.prefixValidLifetime)) + String.format("prefix_preferred=%ds, ", Long.valueOf(this.prefixPreferredLifetime)) + String.format("route_info=%ds, ", Long.valueOf(this.routeInfoLifetime)) + String.format("rdnss=%ds, ", Long.valueOf(this.rdnssLifetime)) + String.format("dnssl=%ds)", Long.valueOf(this.dnsslLifetime));
    }

    public boolean equals(Object obj) {
        if (obj != null && obj.getClass().equals(RaEvent.class)) {
            RaEvent raEvent = (RaEvent) obj;
            if (this.routerLifetime == raEvent.routerLifetime && this.prefixValidLifetime == raEvent.prefixValidLifetime && this.prefixPreferredLifetime == raEvent.prefixPreferredLifetime && this.routeInfoLifetime == raEvent.routeInfoLifetime && this.rdnssLifetime == raEvent.rdnssLifetime && this.dnsslLifetime == raEvent.dnsslLifetime) {
                return true;
            }
        }
        return false;
    }

    public static final class Builder {
        long routerLifetime = -1;
        long prefixValidLifetime = -1;
        long prefixPreferredLifetime = -1;
        long routeInfoLifetime = -1;
        long rdnssLifetime = -1;
        long dnsslLifetime = -1;

        public RaEvent build() {
            return new RaEvent(this.routerLifetime, this.prefixValidLifetime, this.prefixPreferredLifetime, this.routeInfoLifetime, this.rdnssLifetime, this.dnsslLifetime);
        }

        public Builder updateRouterLifetime(long j) {
            this.routerLifetime = updateLifetime(this.routerLifetime, j);
            return this;
        }

        public Builder updatePrefixValidLifetime(long j) {
            this.prefixValidLifetime = updateLifetime(this.prefixValidLifetime, j);
            return this;
        }

        public Builder updatePrefixPreferredLifetime(long j) {
            this.prefixPreferredLifetime = updateLifetime(this.prefixPreferredLifetime, j);
            return this;
        }

        public Builder updateRouteInfoLifetime(long j) {
            this.routeInfoLifetime = updateLifetime(this.routeInfoLifetime, j);
            return this;
        }

        public Builder updateRdnssLifetime(long j) {
            this.rdnssLifetime = updateLifetime(this.rdnssLifetime, j);
            return this;
        }

        public Builder updateDnsslLifetime(long j) {
            this.dnsslLifetime = updateLifetime(this.dnsslLifetime, j);
            return this;
        }

        private long updateLifetime(long j, long j2) {
            return j == -1 ? j2 : Math.min(j, j2);
        }
    }
}
