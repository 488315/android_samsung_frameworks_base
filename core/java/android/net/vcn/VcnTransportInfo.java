package android.net.vcn;

import android.net.TransportInfo;
import android.net.wifi.WifiInfo;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class VcnTransportInfo implements TransportInfo, Parcelable {
    public static final Parcelable.Creator<VcnTransportInfo> CREATOR =
            new Parcelable.Creator<
                    VcnTransportInfo>() { // from class: android.net.vcn.VcnTransportInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VcnTransportInfo createFromParcel(Parcel in) {
                    int subId = in.readInt();
                    WifiInfo wifiInfo = (WifiInfo) in.readParcelable(null, WifiInfo.class);
                    int minUdpPort4500NatTimeoutSeconds = in.readInt();
                    if (wifiInfo == null && subId == -1 && minUdpPort4500NatTimeoutSeconds == -1) {
                        return null;
                    }
                    return new VcnTransportInfo(wifiInfo, subId, minUdpPort4500NatTimeoutSeconds);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VcnTransportInfo[] newArray(int size) {
                    return new VcnTransportInfo[size];
                }
            };
    private final int mMinUdpPort4500NatTimeoutSeconds;
    private final int mSubId;
    private final WifiInfo mWifiInfo;

    public VcnTransportInfo(WifiInfo wifiInfo) {
        this(wifiInfo, -1, -1);
    }

    public VcnTransportInfo(WifiInfo wifiInfo, int minUdpPort4500NatTimeoutSeconds) {
        this(wifiInfo, -1, minUdpPort4500NatTimeoutSeconds);
    }

    public VcnTransportInfo(int subId) {
        this(null, subId, -1);
    }

    public VcnTransportInfo(int subId, int minUdpPort4500NatTimeoutSeconds) {
        this(null, subId, minUdpPort4500NatTimeoutSeconds);
    }

    private VcnTransportInfo(WifiInfo wifiInfo, int subId, int minUdpPort4500NatTimeoutSeconds) {
        this.mWifiInfo = wifiInfo;
        this.mSubId = subId;
        this.mMinUdpPort4500NatTimeoutSeconds = minUdpPort4500NatTimeoutSeconds;
    }

    public WifiInfo getWifiInfo() {
        return this.mWifiInfo;
    }

    public int getSubId() {
        return this.mSubId;
    }

    public int getMinUdpPort4500NatTimeoutSeconds() {
        return this.mMinUdpPort4500NatTimeoutSeconds;
    }

    public int hashCode() {
        return Objects.hash(
                this.mWifiInfo,
                Integer.valueOf(this.mSubId),
                Integer.valueOf(this.mMinUdpPort4500NatTimeoutSeconds));
    }

    public boolean equals(Object o) {
        if (!(o instanceof VcnTransportInfo)) {
            return false;
        }
        VcnTransportInfo that = (VcnTransportInfo) o;
        return Objects.equals(this.mWifiInfo, that.mWifiInfo)
                && this.mSubId == that.mSubId
                && this.mMinUdpPort4500NatTimeoutSeconds == that.mMinUdpPort4500NatTimeoutSeconds;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TransportInfo makeCopy(long redactions) {
        if ((4 & redactions) != 0) {
            return new VcnTransportInfo(null, -1, -1);
        }
        return new VcnTransportInfo(
                this.mWifiInfo != null ? this.mWifiInfo.makeCopy(redactions) : null,
                this.mSubId,
                this.mMinUdpPort4500NatTimeoutSeconds);
    }

    public long getApplicableRedactions() {
        if (this.mWifiInfo != null) {
            long redactions = 4 | this.mWifiInfo.getApplicableRedactions();
            return redactions;
        }
        return 4L;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mSubId);
        dest.writeParcelable(this.mWifiInfo, flags);
        dest.writeInt(this.mMinUdpPort4500NatTimeoutSeconds);
    }

    public String toString() {
        return "VcnTransportInfo { mWifiInfo = "
                + this.mWifiInfo
                + ", mSubId = "
                + this.mSubId
                + " }";
    }
}
