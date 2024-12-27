package android.net.networkstack.aidl.quirks;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class IPv6ProvisioningLossQuirk {
    public final int mDetectionCount;
    public final long mQuirkExpiry;

    public IPv6ProvisioningLossQuirk(int i, long j) {
        this.mDetectionCount = i;
        this.mQuirkExpiry = j;
    }

    public static IPv6ProvisioningLossQuirk fromStableParcelable(IPv6ProvisioningLossQuirkParcelable iPv6ProvisioningLossQuirkParcelable) {
        if (iPv6ProvisioningLossQuirkParcelable == null) {
            return null;
        }
        return new IPv6ProvisioningLossQuirk(iPv6ProvisioningLossQuirkParcelable.detectionCount, iPv6ProvisioningLossQuirkParcelable.quirkExpiry);
    }

    public boolean equals(Object obj) {
        if (obj == null || IPv6ProvisioningLossQuirk.class != obj.getClass()) {
            return false;
        }
        IPv6ProvisioningLossQuirk iPv6ProvisioningLossQuirk = (IPv6ProvisioningLossQuirk) obj;
        return this.mDetectionCount == iPv6ProvisioningLossQuirk.mDetectionCount && this.mQuirkExpiry == iPv6ProvisioningLossQuirk.mQuirkExpiry;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mDetectionCount), Long.valueOf(this.mQuirkExpiry));
    }

    public IPv6ProvisioningLossQuirkParcelable toStableParcelable() {
        IPv6ProvisioningLossQuirkParcelable iPv6ProvisioningLossQuirkParcelable = new IPv6ProvisioningLossQuirkParcelable();
        iPv6ProvisioningLossQuirkParcelable.detectionCount = this.mDetectionCount;
        iPv6ProvisioningLossQuirkParcelable.quirkExpiry = this.mQuirkExpiry;
        return iPv6ProvisioningLossQuirkParcelable;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("detection count: ");
        stringBuffer.append(this.mDetectionCount);
        stringBuffer.append(", quirk expiry: ");
        stringBuffer.append(this.mQuirkExpiry);
        return stringBuffer.toString();
    }
}
