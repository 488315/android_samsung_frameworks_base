package com.android.server.stats.pull.netstats;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SubInfo {
    public final int carrierId;
    public final boolean isOpportunistic;
    public final String mcc;
    public final String mnc;
    public final int subId;
    public final String subscriberId;

    public SubInfo(String str, int i, String str2, String str3, int i2, boolean z) {
        this.subId = i;
        this.carrierId = i2;
        this.mcc = str;
        this.mnc = str2;
        this.subscriberId = str3;
        this.isOpportunistic = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || SubInfo.class != obj.getClass()) {
            return false;
        }
        SubInfo subInfo = (SubInfo) obj;
        return this.subId == subInfo.subId
                && this.carrierId == subInfo.carrierId
                && this.isOpportunistic == subInfo.isOpportunistic
                && this.mcc.equals(subInfo.mcc)
                && this.mnc.equals(subInfo.mnc)
                && this.subscriberId.equals(subInfo.subscriberId);
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.subId),
                this.mcc,
                this.mnc,
                Integer.valueOf(this.carrierId),
                this.subscriberId,
                Boolean.valueOf(this.isOpportunistic));
    }
}
