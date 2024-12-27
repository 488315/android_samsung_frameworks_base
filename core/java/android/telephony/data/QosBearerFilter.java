package android.telephony.data;

import android.net.LinkAddress;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class QosBearerFilter implements Parcelable {
    public static final Parcelable.Creator<QosBearerFilter> CREATOR =
            new Parcelable.Creator<
                    QosBearerFilter>() { // from class: android.telephony.data.QosBearerFilter.1
                @Override // android.os.Parcelable.Creator
                public QosBearerFilter createFromParcel(Parcel source) {
                    return new QosBearerFilter(source);
                }

                @Override // android.os.Parcelable.Creator
                public QosBearerFilter[] newArray(int size) {
                    return new QosBearerFilter[size];
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
    public @interface QosBearerFilterDirection {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface QosProtocol {}

    public QosBearerFilter(
            List<LinkAddress> localAddresses,
            List<LinkAddress> remoteAddresses,
            PortRange localPort,
            PortRange remotePort,
            int protocol,
            int tos,
            long flowLabel,
            long spi,
            int direction,
            int precedence) {
        this.localAddresses = new ArrayList();
        this.localAddresses.addAll(localAddresses);
        this.remoteAddresses = new ArrayList();
        this.remoteAddresses.addAll(remoteAddresses);
        this.localPort = localPort;
        this.remotePort = remotePort;
        this.protocol = protocol;
        this.typeOfServiceMask = tos;
        this.flowLabel = flowLabel;
        this.securityParameterIndex = spi;
        this.filterDirection = direction;
        this.precedence = precedence;
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
        public static final Parcelable.Creator<PortRange> CREATOR =
                new Parcelable.Creator<
                        PortRange>() { // from class:
                                       // android.telephony.data.QosBearerFilter.PortRange.1
                    @Override // android.os.Parcelable.Creator
                    public PortRange createFromParcel(Parcel source) {
                        return new PortRange(source);
                    }

                    @Override // android.os.Parcelable.Creator
                    public PortRange[] newArray(int size) {
                        return new PortRange[size];
                    }
                };
        int end;
        int start;

        private PortRange(Parcel source) {
            this.start = source.readInt();
            this.end = source.readInt();
        }

        public PortRange(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return this.start;
        }

        public int getEnd() {
            return this.end;
        }

        public boolean isValid() {
            return this.start >= 20
                    && this.start <= 65535
                    && this.end >= 20
                    && this.end <= 65535
                    && this.start <= this.end;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.start);
            dest.writeInt(this.end);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String toString() {
            return "PortRange { start=" + this.start + " end=" + this.end + "}";
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || !(o instanceof PortRange)) {
                return false;
            }
            PortRange other = (PortRange) o;
            if (this.start == other.start && this.end == other.end) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.start), Integer.valueOf(this.end));
        }
    }

    public String toString() {
        return "QosBearerFilter { localAddresses="
                + this.localAddresses
                + " remoteAddresses="
                + this.remoteAddresses
                + " localPort="
                + this.localPort
                + " remotePort="
                + this.remotePort
                + " protocol="
                + this.protocol
                + " typeOfServiceMask="
                + this.typeOfServiceMask
                + " flowLabel="
                + this.flowLabel
                + " securityParameterIndex="
                + this.securityParameterIndex
                + " filterDirection="
                + this.filterDirection
                + " precedence="
                + this.precedence
                + "}";
    }

    public int hashCode() {
        return Objects.hash(
                this.localAddresses,
                this.remoteAddresses,
                this.localPort,
                this.remotePort,
                Integer.valueOf(this.protocol),
                Integer.valueOf(this.typeOfServiceMask),
                Long.valueOf(this.flowLabel),
                Long.valueOf(this.securityParameterIndex),
                Integer.valueOf(this.filterDirection),
                Integer.valueOf(this.precedence));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof QosBearerFilter)) {
            return false;
        }
        QosBearerFilter other = (QosBearerFilter) o;
        if (this.localAddresses.size() == other.localAddresses.size()
                && this.localAddresses.containsAll(other.localAddresses)
                && this.remoteAddresses.size() == other.remoteAddresses.size()
                && this.remoteAddresses.containsAll(other.remoteAddresses)
                && Objects.equals(this.localPort, other.localPort)
                && Objects.equals(this.remotePort, other.remotePort)
                && this.protocol == other.protocol
                && this.typeOfServiceMask == other.typeOfServiceMask
                && this.flowLabel == other.flowLabel
                && this.securityParameterIndex == other.securityParameterIndex
                && this.filterDirection == other.filterDirection
                && this.precedence == other.precedence) {
            return true;
        }
        return false;
    }

    private QosBearerFilter(Parcel source) {
        this.localAddresses = new ArrayList();
        source.readList(this.localAddresses, LinkAddress.class.getClassLoader(), LinkAddress.class);
        this.remoteAddresses = new ArrayList();
        source.readList(
                this.remoteAddresses, LinkAddress.class.getClassLoader(), LinkAddress.class);
        this.localPort =
                (PortRange)
                        source.readParcelable(PortRange.class.getClassLoader(), PortRange.class);
        this.remotePort =
                (PortRange)
                        source.readParcelable(PortRange.class.getClassLoader(), PortRange.class);
        this.protocol = source.readInt();
        this.typeOfServiceMask = source.readInt();
        this.flowLabel = source.readLong();
        this.securityParameterIndex = source.readLong();
        this.filterDirection = source.readInt();
        this.precedence = source.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.localAddresses);
        dest.writeList(this.remoteAddresses);
        dest.writeParcelable(this.localPort, flags);
        dest.writeParcelable(this.remotePort, flags);
        dest.writeInt(this.protocol);
        dest.writeInt(this.typeOfServiceMask);
        dest.writeLong(this.flowLabel);
        dest.writeLong(this.securityParameterIndex);
        dest.writeInt(this.filterDirection);
        dest.writeInt(this.precedence);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
