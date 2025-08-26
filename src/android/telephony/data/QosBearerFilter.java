package android.telephony.data;

import android.net.LinkAddress;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class QosBearerFilter implements Parcelable {
    public static final Parcelable.Creator<QosBearerFilter> CREATOR = new Parcelable.Creator<QosBearerFilter>() { // from class: android.telephony.data.QosBearerFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QosBearerFilter createFromParcel(Parcel parcel) {
            return new QosBearerFilter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QosBearerFilter[] newArray(int i) {
            return new QosBearerFilter[i];
        }
    };
    public static final int QOS_FILTER_DIRECTION_BIDIRECTIONAL = 2;
    public static final int QOS_FILTER_DIRECTION_DOWNLINK = 0;
    public static final int QOS_FILTER_DIRECTION_UPLINK = 1;
    public static final int QOS_MAX_PORT = 65535;
    public static final int QOS_MIN_PORT = 20;
    public static final int QOS_PROTOCOL_AH = 51;
    public static final int QOS_PROTOCOL_ESP = 50;
    public static final int QOS_PROTOCOL_TCP = 6;
    public static final int QOS_PROTOCOL_UDP = 17;
    public static final int QOS_PROTOCOL_UNSPECIFIED = -1;
    private int filterDirection;
    private long flowLabel;
    private List<LinkAddress> localAddresses;
    private PortRange localPort;
    private int precedence;
    private int protocol;
    private List<LinkAddress> remoteAddresses;
    private PortRange remotePort;
    private long securityParameterIndex;
    private int typeOfServiceMask;

    @Retention(RetentionPolicy.SOURCE)
    public @interface QosBearerFilterDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface QosProtocol {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public QosBearerFilter(List<LinkAddress> list, List<LinkAddress> list2, PortRange portRange, PortRange portRange2, int i, int i2, long j, long j2, int i3, int i4) {
        ArrayList arrayList = new ArrayList();
        this.localAddresses = arrayList;
        arrayList.addAll(list);
        ArrayList arrayList2 = new ArrayList();
        this.remoteAddresses = arrayList2;
        arrayList2.addAll(list2);
        this.localPort = portRange;
        this.remotePort = portRange2;
        this.protocol = i;
        this.typeOfServiceMask = i2;
        this.flowLabel = j;
        this.securityParameterIndex = j2;
        this.filterDirection = i3;
        this.precedence = i4;
    }

    public List<LinkAddress> getLocalAddresses() {
        return this.localAddresses;
    }

    public List<LinkAddress> getRemoteAddresses() {
        return this.remoteAddresses;
    }

    public PortRange getLocalPortRange() {
        return this.localPort;
    }

    public PortRange getRemotePortRange() {
        return this.remotePort;
    }

    public int getPrecedence() {
        return this.precedence;
    }

    public int getProtocol() {
        return this.protocol;
    }

    public static class PortRange implements Parcelable {
        public static final Parcelable.Creator<PortRange> CREATOR = new Parcelable.Creator<PortRange>() { // from class: android.telephony.data.QosBearerFilter.PortRange.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PortRange createFromParcel(Parcel parcel) {
                return new PortRange(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PortRange[] newArray(int i) {
                return new PortRange[i];
            }
        };
        int end;
        int start;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private PortRange(Parcel parcel) {
            this.start = parcel.readInt();
            this.end = parcel.readInt();
        }

        public PortRange(int i, int i2) {
            this.start = i;
            this.end = i2;
        }

        public int getStart() {
            return this.start;
        }

        public int getEnd() {
            return this.end;
        }

        public boolean isValid() {
            int i;
            int i2 = this.start;
            return i2 >= 20 && i2 <= 65535 && (i = this.end) >= 20 && i <= 65535 && i2 <= i;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.start);
            parcel.writeInt(this.end);
        }

        public String toString() {
            return "PortRange { start=" + this.start + " end=" + this.end + "}";
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && (obj instanceof PortRange)) {
                PortRange portRange = (PortRange) obj;
                if (this.start == portRange.start && this.end == portRange.end) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.start), Integer.valueOf(this.end));
        }
    }

    public String toString() {
        return "QosBearerFilter { localAddresses=" + this.localAddresses + " remoteAddresses=" + this.remoteAddresses + " localPort=" + this.localPort + " remotePort=" + this.remotePort + " protocol=" + this.protocol + " typeOfServiceMask=" + this.typeOfServiceMask + " flowLabel=" + this.flowLabel + " securityParameterIndex=" + this.securityParameterIndex + " filterDirection=" + this.filterDirection + " precedence=" + this.precedence + "}";
    }

    public int hashCode() {
        return Objects.hash(this.localAddresses, this.remoteAddresses, this.localPort, this.remotePort, Integer.valueOf(this.protocol), Integer.valueOf(this.typeOfServiceMask), Long.valueOf(this.flowLabel), Long.valueOf(this.securityParameterIndex), Integer.valueOf(this.filterDirection), Integer.valueOf(this.precedence));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof QosBearerFilter)) {
            QosBearerFilter qosBearerFilter = (QosBearerFilter) obj;
            if (this.localAddresses.size() == qosBearerFilter.localAddresses.size() && this.localAddresses.containsAll(qosBearerFilter.localAddresses) && this.remoteAddresses.size() == qosBearerFilter.remoteAddresses.size() && this.remoteAddresses.containsAll(qosBearerFilter.remoteAddresses) && Objects.equals(this.localPort, qosBearerFilter.localPort) && Objects.equals(this.remotePort, qosBearerFilter.remotePort) && this.protocol == qosBearerFilter.protocol && this.typeOfServiceMask == qosBearerFilter.typeOfServiceMask && this.flowLabel == qosBearerFilter.flowLabel && this.securityParameterIndex == qosBearerFilter.securityParameterIndex && this.filterDirection == qosBearerFilter.filterDirection && this.precedence == qosBearerFilter.precedence) {
                return true;
            }
        }
        return false;
    }

    private QosBearerFilter(Parcel parcel) throws ClassNotFoundException, IOException {
        ArrayList arrayList = new ArrayList();
        this.localAddresses = arrayList;
        parcel.readList(arrayList, LinkAddress.class.getClassLoader(), LinkAddress.class);
        ArrayList arrayList2 = new ArrayList();
        this.remoteAddresses = arrayList2;
        parcel.readList(arrayList2, LinkAddress.class.getClassLoader(), LinkAddress.class);
        this.localPort = (PortRange) parcel.readParcelable(PortRange.class.getClassLoader(), PortRange.class);
        this.remotePort = (PortRange) parcel.readParcelable(PortRange.class.getClassLoader(), PortRange.class);
        this.protocol = parcel.readInt();
        this.typeOfServiceMask = parcel.readInt();
        this.flowLabel = parcel.readLong();
        this.securityParameterIndex = parcel.readLong();
        this.filterDirection = parcel.readInt();
        this.precedence = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.localAddresses);
        parcel.writeList(this.remoteAddresses);
        parcel.writeParcelable(this.localPort, i);
        parcel.writeParcelable(this.remotePort, i);
        parcel.writeInt(this.protocol);
        parcel.writeInt(this.typeOfServiceMask);
        parcel.writeLong(this.flowLabel);
        parcel.writeLong(this.securityParameterIndex);
        parcel.writeInt(this.filterDirection);
        parcel.writeInt(this.precedence);
    }
}
