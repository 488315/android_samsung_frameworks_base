package android.telephony.data;

import android.annotation.SystemApi;
import android.net.LinkAddress;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.DataFailCause;
import android.telephony.PreciseDataConnectionState;

import com.android.internal.util.Preconditions;
import com.android.internal.vibrator.persistence.XmlConstants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@SystemApi
public final class DataCallResponse implements Parcelable {
    public static final Parcelable.Creator<DataCallResponse> CREATOR =
            new Parcelable.Creator<
                    DataCallResponse>() { // from class: android.telephony.data.DataCallResponse.1
                @Override // android.os.Parcelable.Creator
                public DataCallResponse createFromParcel(Parcel source) {
                    return new DataCallResponse(source);
                }

                @Override // android.os.Parcelable.Creator
                public DataCallResponse[] newArray(int size) {
                    return new DataCallResponse[size];
                }
            };
    public static final int HANDOVER_FAILURE_MODE_DO_FALLBACK = 1;
    public static final int HANDOVER_FAILURE_MODE_LEGACY = 0;
    public static final int HANDOVER_FAILURE_MODE_NO_FALLBACK_RETRY_HANDOVER = 2;
    public static final int HANDOVER_FAILURE_MODE_NO_FALLBACK_RETRY_SETUP_NORMAL = 3;
    public static final int HANDOVER_FAILURE_MODE_UNKNOWN = -1;
    public static final int LINK_STATUS_ACTIVE = 2;
    public static final int LINK_STATUS_DORMANT = 1;
    public static final int LINK_STATUS_INACTIVE = 0;
    public static final int LINK_STATUS_UNKNOWN = -1;
    public static final int PDU_SESSION_ID_NOT_SET = 0;
    public static final int RETRY_DURATION_UNDEFINED = -1;
    private final List<LinkAddress> mAddresses;
    private final int mCause;
    private final Qos mDefaultQos;
    private final List<InetAddress> mDnsAddresses;
    private final List<InetAddress> mGatewayAddresses;
    private final int mHandoverFailureMode;
    private final int mId;
    private final String mInterfaceName;
    private final int mLinkStatus;
    private final int mMtu;
    private final int mMtuV4;
    private final int mMtuV6;
    private final int mNetworkValidationStatus;
    private final List<InetAddress> mPcscfAddresses;
    private final int mPduSessionId;
    private final int mProtocolType;
    private final List<QosBearerSession> mQosBearerSessions;
    private final NetworkSliceInfo mSliceInfo;
    private final long mSuggestedRetryTime;
    private final List<TrafficDescriptor> mTrafficDescriptors;

    @Retention(RetentionPolicy.SOURCE)
    public @interface HandoverFailureMode {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface LinkStatus {}

    public DataCallResponse(
            int cause,
            int suggestedRetryTime,
            int id,
            int linkStatus,
            int protocolType,
            String interfaceName,
            List<LinkAddress> addresses,
            List<InetAddress> dnsAddresses,
            List<InetAddress> gatewayAddresses,
            List<InetAddress> pcscfAddresses,
            int mtu) {
        this(
                cause,
                suggestedRetryTime,
                id,
                linkStatus,
                protocolType,
                interfaceName == null ? "" : interfaceName,
                addresses == null ? Collections.emptyList() : addresses,
                dnsAddresses == null ? Collections.emptyList() : dnsAddresses,
                gatewayAddresses == null ? Collections.emptyList() : gatewayAddresses,
                pcscfAddresses == null ? Collections.emptyList() : pcscfAddresses,
                mtu,
                mtu,
                mtu,
                0,
                0,
                null,
                Collections.emptyList(),
                null,
                Collections.emptyList(),
                0);
    }

    private DataCallResponse(
            int cause,
            long suggestedRetryTime,
            int id,
            int linkStatus,
            int protocolType,
            String interfaceName,
            List<LinkAddress> addresses,
            List<InetAddress> dnsAddresses,
            List<InetAddress> gatewayAddresses,
            List<InetAddress> pcscfAddresses,
            int mtu,
            int mtuV4,
            int mtuV6,
            int handoverFailureMode,
            int pduSessionId,
            Qos defaultQos,
            List<QosBearerSession> qosBearerSessions,
            NetworkSliceInfo sliceInfo,
            List<TrafficDescriptor> trafficDescriptors,
            int networkValidationStatus) {
        this.mCause = cause;
        this.mSuggestedRetryTime = suggestedRetryTime;
        this.mId = id;
        this.mLinkStatus = linkStatus;
        this.mProtocolType = protocolType;
        this.mInterfaceName = interfaceName;
        this.mAddresses = new ArrayList(addresses);
        this.mDnsAddresses = new ArrayList(dnsAddresses);
        this.mGatewayAddresses = new ArrayList(gatewayAddresses);
        this.mPcscfAddresses = new ArrayList(pcscfAddresses);
        this.mMtu = mtu;
        this.mMtuV4 = mtuV4;
        this.mMtuV6 = mtuV6;
        this.mHandoverFailureMode = handoverFailureMode;
        this.mPduSessionId = pduSessionId;
        this.mDefaultQos = defaultQos;
        this.mQosBearerSessions = new ArrayList(qosBearerSessions);
        this.mSliceInfo = sliceInfo;
        this.mTrafficDescriptors = new ArrayList(trafficDescriptors);
        this.mNetworkValidationStatus = networkValidationStatus;
        if (this.mLinkStatus == 2 || this.mLinkStatus == 1) {
            Objects.requireNonNull(
                    this.mInterfaceName, "Active data calls must be on a valid interface!");
            if (this.mCause != 0) {
                throw new IllegalStateException("Active data call must not have a failure!");
            }
        }
    }

    public DataCallResponse(Parcel source) {
        this.mCause = source.readInt();
        this.mSuggestedRetryTime = source.readLong();
        this.mId = source.readInt();
        this.mLinkStatus = source.readInt();
        this.mProtocolType = source.readInt();
        this.mInterfaceName = source.readString();
        this.mAddresses = new ArrayList();
        source.readList(this.mAddresses, LinkAddress.class.getClassLoader(), LinkAddress.class);
        this.mDnsAddresses = new ArrayList();
        source.readList(this.mDnsAddresses, InetAddress.class.getClassLoader(), InetAddress.class);
        this.mGatewayAddresses = new ArrayList();
        source.readList(
                this.mGatewayAddresses, InetAddress.class.getClassLoader(), InetAddress.class);
        this.mPcscfAddresses = new ArrayList();
        source.readList(
                this.mPcscfAddresses, InetAddress.class.getClassLoader(), InetAddress.class);
        this.mMtu = source.readInt();
        this.mMtuV4 = source.readInt();
        this.mMtuV6 = source.readInt();
        this.mHandoverFailureMode = source.readInt();
        this.mPduSessionId = source.readInt();
        this.mDefaultQos = (Qos) source.readParcelable(Qos.class.getClassLoader(), Qos.class);
        this.mQosBearerSessions = new ArrayList();
        source.readList(
                this.mQosBearerSessions,
                QosBearerSession.class.getClassLoader(),
                QosBearerSession.class);
        this.mSliceInfo =
                (NetworkSliceInfo)
                        source.readParcelable(
                                NetworkSliceInfo.class.getClassLoader(), NetworkSliceInfo.class);
        this.mTrafficDescriptors = new ArrayList();
        source.readList(
                this.mTrafficDescriptors,
                TrafficDescriptor.class.getClassLoader(),
                TrafficDescriptor.class);
        this.mNetworkValidationStatus = source.readInt();
    }

    public int getCause() {
        return this.mCause;
    }

    @Deprecated
    public int getSuggestedRetryTime() {
        if (this.mSuggestedRetryTime == -1) {
            return 0;
        }
        if (this.mSuggestedRetryTime > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) this.mSuggestedRetryTime;
    }

    public long getRetryDurationMillis() {
        return this.mSuggestedRetryTime;
    }

    public int getId() {
        return this.mId;
    }

    public int getLinkStatus() {
        return this.mLinkStatus;
    }

    public int getProtocolType() {
        return this.mProtocolType;
    }

    public String getInterfaceName() {
        return this.mInterfaceName;
    }

    public List<LinkAddress> getAddresses() {
        return Collections.unmodifiableList(this.mAddresses);
    }

    public List<InetAddress> getDnsAddresses() {
        return Collections.unmodifiableList(this.mDnsAddresses);
    }

    public List<InetAddress> getGatewayAddresses() {
        return Collections.unmodifiableList(this.mGatewayAddresses);
    }

    public List<InetAddress> getPcscfAddresses() {
        return Collections.unmodifiableList(this.mPcscfAddresses);
    }

    @Deprecated
    public int getMtu() {
        return this.mMtu;
    }

    public int getMtuV4() {
        return this.mMtuV4;
    }

    public int getMtuV6() {
        return this.mMtuV6;
    }

    public int getHandoverFailureMode() {
        return this.mHandoverFailureMode;
    }

    public int getPduSessionId() {
        return this.mPduSessionId;
    }

    public Qos getDefaultQos() {
        return this.mDefaultQos;
    }

    public List<QosBearerSession> getQosBearerSessions() {
        return Collections.unmodifiableList(this.mQosBearerSessions);
    }

    public NetworkSliceInfo getSliceInfo() {
        return this.mSliceInfo;
    }

    public List<TrafficDescriptor> getTrafficDescriptors() {
        return Collections.unmodifiableList(this.mTrafficDescriptors);
    }

    public int getNetworkValidationStatus() {
        return this.mNetworkValidationStatus;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("DataCallResponse: {")
                .append(" cause=")
                .append(DataFailCause.toString(this.mCause))
                .append(" retry=")
                .append(this.mSuggestedRetryTime)
                .append(" cid=")
                .append(this.mId)
                .append(" linkStatus=")
                .append(this.mLinkStatus)
                .append(" protocolType=")
                .append(this.mProtocolType)
                .append(" ifname=")
                .append(this.mInterfaceName)
                .append(" addresses=")
                .append(this.mAddresses)
                .append(" dnses=")
                .append(this.mDnsAddresses)
                .append(" gateways=")
                .append(this.mGatewayAddresses)
                .append(" pcscf=")
                .append(this.mPcscfAddresses)
                .append(" mtu=")
                .append(getMtu())
                .append(" mtuV4=")
                .append(getMtuV4())
                .append(" mtuV6=")
                .append(getMtuV6())
                .append(" handoverFailureMode=")
                .append(failureModeToString(this.mHandoverFailureMode))
                .append(" pduSessionId=")
                .append(getPduSessionId())
                .append(" defaultQos=")
                .append(this.mDefaultQos)
                .append(" qosBearerSessions=")
                .append(this.mQosBearerSessions)
                .append(" sliceInfo=")
                .append(this.mSliceInfo)
                .append(" trafficDescriptors=")
                .append(this.mTrafficDescriptors)
                .append(" networkValidationStatus=")
                .append(
                        PreciseDataConnectionState.networkValidationStatusToString(
                                this.mNetworkValidationStatus))
                .append("}");
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataCallResponse)) {
            return false;
        }
        DataCallResponse other = (DataCallResponse) o;
        return this.mCause == other.mCause
                && this.mSuggestedRetryTime == other.mSuggestedRetryTime
                && this.mId == other.mId
                && this.mLinkStatus == other.mLinkStatus
                && this.mProtocolType == other.mProtocolType
                && this.mInterfaceName.equals(other.mInterfaceName)
                && this.mAddresses.size() == other.mAddresses.size()
                && this.mAddresses.containsAll(other.mAddresses)
                && this.mDnsAddresses.size() == other.mDnsAddresses.size()
                && this.mDnsAddresses.containsAll(other.mDnsAddresses)
                && this.mGatewayAddresses.size() == other.mGatewayAddresses.size()
                && this.mGatewayAddresses.containsAll(other.mGatewayAddresses)
                && this.mPcscfAddresses.size() == other.mPcscfAddresses.size()
                && this.mPcscfAddresses.containsAll(other.mPcscfAddresses)
                && this.mMtu == other.mMtu
                && this.mMtuV4 == other.mMtuV4
                && this.mMtuV6 == other.mMtuV6
                && this.mHandoverFailureMode == other.mHandoverFailureMode
                && this.mPduSessionId == other.mPduSessionId
                && Objects.equals(this.mDefaultQos, other.mDefaultQos)
                && this.mQosBearerSessions.size() == other.mQosBearerSessions.size()
                && this.mQosBearerSessions.containsAll(other.mQosBearerSessions)
                && Objects.equals(this.mSliceInfo, other.mSliceInfo)
                && this.mTrafficDescriptors.size() == other.mTrafficDescriptors.size()
                && this.mTrafficDescriptors.containsAll(other.mTrafficDescriptors)
                && this.mNetworkValidationStatus == other.mNetworkValidationStatus;
    }

    public int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mCause),
                Long.valueOf(this.mSuggestedRetryTime),
                Integer.valueOf(this.mId),
                Integer.valueOf(this.mLinkStatus),
                Integer.valueOf(this.mProtocolType),
                this.mInterfaceName,
                Set.copyOf(this.mAddresses),
                Set.copyOf(this.mDnsAddresses),
                Set.copyOf(this.mGatewayAddresses),
                Set.copyOf(this.mPcscfAddresses),
                Integer.valueOf(this.mMtu),
                Integer.valueOf(this.mMtuV4),
                Integer.valueOf(this.mMtuV6),
                Integer.valueOf(this.mHandoverFailureMode),
                Integer.valueOf(this.mPduSessionId),
                this.mDefaultQos,
                Set.copyOf(this.mQosBearerSessions),
                this.mSliceInfo,
                Set.copyOf(this.mTrafficDescriptors),
                Integer.valueOf(this.mNetworkValidationStatus));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mCause);
        dest.writeLong(this.mSuggestedRetryTime);
        dest.writeInt(this.mId);
        dest.writeInt(this.mLinkStatus);
        dest.writeInt(this.mProtocolType);
        dest.writeString(this.mInterfaceName);
        dest.writeList(this.mAddresses);
        dest.writeList(this.mDnsAddresses);
        dest.writeList(this.mGatewayAddresses);
        dest.writeList(this.mPcscfAddresses);
        dest.writeInt(this.mMtu);
        dest.writeInt(this.mMtuV4);
        dest.writeInt(this.mMtuV6);
        dest.writeInt(this.mHandoverFailureMode);
        dest.writeInt(this.mPduSessionId);
        dest.writeParcelable(this.mDefaultQos, flags);
        dest.writeList(this.mQosBearerSessions);
        dest.writeParcelable(this.mSliceInfo, flags);
        dest.writeList(this.mTrafficDescriptors);
        dest.writeInt(this.mNetworkValidationStatus);
    }

    public static String failureModeToString(int handoverFailureMode) {
        switch (handoverFailureMode) {
            case -1:
                return "unknown";
            case 0:
                return "legacy";
            case 1:
                return XmlConstants.ATTRIBUTE_FALLBACK;
            case 2:
                return "retry handover";
            case 3:
                return "retry setup new one";
            default:
                return Integer.toString(handoverFailureMode);
        }
    }

    public static final class Builder {
        private int mCause;
        private Qos mDefaultQos;
        private int mId;
        private int mLinkStatus;
        private int mMtu;
        private int mMtuV4;
        private int mMtuV6;
        private int mProtocolType;
        private NetworkSliceInfo mSliceInfo;
        private long mSuggestedRetryTime = -1;
        private String mInterfaceName = "";
        private List<LinkAddress> mAddresses = Collections.emptyList();
        private List<InetAddress> mDnsAddresses = Collections.emptyList();
        private List<InetAddress> mGatewayAddresses = Collections.emptyList();
        private List<InetAddress> mPcscfAddresses = Collections.emptyList();
        private int mHandoverFailureMode = 0;
        private int mPduSessionId = 0;
        private List<QosBearerSession> mQosBearerSessions = new ArrayList();
        private List<TrafficDescriptor> mTrafficDescriptors = new ArrayList();
        private int mNetworkValidationStatus = 0;

        public Builder setCause(int cause) {
            this.mCause = cause;
            return this;
        }

        @Deprecated
        public Builder setSuggestedRetryTime(int suggestedRetryTime) {
            this.mSuggestedRetryTime = suggestedRetryTime;
            return this;
        }

        public Builder setRetryDurationMillis(long retryDurationMillis) {
            this.mSuggestedRetryTime = retryDurationMillis;
            return this;
        }

        public Builder setId(int id) {
            this.mId = id;
            return this;
        }

        public Builder setLinkStatus(int linkStatus) {
            this.mLinkStatus = linkStatus;
            return this;
        }

        public Builder setProtocolType(int protocolType) {
            this.mProtocolType = protocolType;
            return this;
        }

        public Builder setInterfaceName(String interfaceName) {
            if (interfaceName == null) {
                interfaceName = "";
            }
            this.mInterfaceName = interfaceName;
            return this;
        }

        public Builder setAddresses(List<LinkAddress> addresses) {
            Objects.requireNonNull(addresses);
            this.mAddresses = addresses;
            return this;
        }

        public Builder setDnsAddresses(List<InetAddress> dnsAddresses) {
            Objects.requireNonNull(dnsAddresses);
            this.mDnsAddresses = dnsAddresses;
            return this;
        }

        public Builder setGatewayAddresses(List<InetAddress> gatewayAddresses) {
            Objects.requireNonNull(gatewayAddresses);
            this.mGatewayAddresses = gatewayAddresses;
            return this;
        }

        public Builder setPcscfAddresses(List<InetAddress> pcscfAddresses) {
            Objects.requireNonNull(pcscfAddresses);
            this.mPcscfAddresses = pcscfAddresses;
            return this;
        }

        public Builder setMtu(int mtu) {
            this.mMtu = mtu;
            return this;
        }

        public Builder setMtuV4(int mtu) {
            this.mMtuV4 = mtu;
            return this;
        }

        public Builder setMtuV6(int mtu) {
            this.mMtuV6 = mtu;
            return this;
        }

        public Builder setHandoverFailureMode(int failureMode) {
            this.mHandoverFailureMode = failureMode;
            return this;
        }

        public Builder setPduSessionId(int pduSessionId) {
            Preconditions.checkArgument(
                    pduSessionId >= 0, "pduSessionId must be greater than or equal to0");
            Preconditions.checkArgument(
                    pduSessionId <= 15, "pduSessionId must be less than or equal to 15.");
            this.mPduSessionId = pduSessionId;
            return this;
        }

        public Builder setDefaultQos(Qos defaultQos) {
            this.mDefaultQos = defaultQos;
            return this;
        }

        public Builder setQosBearerSessions(List<QosBearerSession> qosBearerSessions) {
            Objects.requireNonNull(qosBearerSessions);
            this.mQosBearerSessions = qosBearerSessions;
            return this;
        }

        public Builder setSliceInfo(NetworkSliceInfo sliceInfo) {
            this.mSliceInfo = sliceInfo;
            return this;
        }

        public Builder setTrafficDescriptors(List<TrafficDescriptor> trafficDescriptors) {
            Objects.requireNonNull(trafficDescriptors);
            this.mTrafficDescriptors = trafficDescriptors;
            return this;
        }

        public Builder setNetworkValidationStatus(int status) {
            this.mNetworkValidationStatus = status;
            return this;
        }

        public DataCallResponse build() {
            return new DataCallResponse(
                    this.mCause,
                    this.mSuggestedRetryTime,
                    this.mId,
                    this.mLinkStatus,
                    this.mProtocolType,
                    this.mInterfaceName,
                    this.mAddresses,
                    this.mDnsAddresses,
                    this.mGatewayAddresses,
                    this.mPcscfAddresses,
                    this.mMtu,
                    this.mMtuV4,
                    this.mMtuV6,
                    this.mHandoverFailureMode,
                    this.mPduSessionId,
                    this.mDefaultQos,
                    this.mQosBearerSessions,
                    this.mSliceInfo,
                    this.mTrafficDescriptors,
                    this.mNetworkValidationStatus);
        }
    }
}
