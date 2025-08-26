package android.telephony.data;

import android.annotation.SystemApi;
import android.net.LinkAddress;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.DataFailCause;
import android.telephony.PreciseDataConnectionState;
import com.android.internal.util.Preconditions;
import com.android.internal.vibrator.persistence.XmlConstants;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@SystemApi
/* loaded from: classes4.dex */
public final class DataCallResponse implements Parcelable {
    public static final Parcelable.Creator<DataCallResponse> CREATOR = new Parcelable.Creator<DataCallResponse>() { // from class: android.telephony.data.DataCallResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataCallResponse createFromParcel(Parcel parcel) {
            return new DataCallResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataCallResponse[] newArray(int i) {
            return new DataCallResponse[i];
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
    public @interface HandoverFailureMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LinkStatus {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DataCallResponse(int i, int i2, int i3, int i4, int i5, String str, List<LinkAddress> list, List<InetAddress> list2, List<InetAddress> list3, List<InetAddress> list4, int i6) {
        this(i, i2, i3, i4, i5, str == null ? "" : str, list == null ? Collections.EMPTY_LIST : list, list2 == null ? Collections.EMPTY_LIST : list2, list3 == null ? Collections.EMPTY_LIST : list3, list4 == null ? Collections.EMPTY_LIST : list4, i6, i6, i6, 0, 0, null, Collections.EMPTY_LIST, null, Collections.EMPTY_LIST, 0);
    }

    private DataCallResponse(int i, long j, int i2, int i3, int i4, String str, List<LinkAddress> list, List<InetAddress> list2, List<InetAddress> list3, List<InetAddress> list4, int i5, int i6, int i7, int i8, int i9, Qos qos, List<QosBearerSession> list5, NetworkSliceInfo networkSliceInfo, List<TrafficDescriptor> list6, int i10) {
        this.mCause = i;
        this.mSuggestedRetryTime = j;
        this.mId = i2;
        this.mLinkStatus = i3;
        this.mProtocolType = i4;
        this.mInterfaceName = str;
        this.mAddresses = new ArrayList(list);
        this.mDnsAddresses = new ArrayList(list2);
        this.mGatewayAddresses = new ArrayList(list3);
        this.mPcscfAddresses = new ArrayList(list4);
        this.mMtu = i5;
        this.mMtuV4 = i6;
        this.mMtuV6 = i7;
        this.mHandoverFailureMode = i8;
        this.mPduSessionId = i9;
        this.mDefaultQos = qos;
        this.mQosBearerSessions = new ArrayList(list5);
        this.mSliceInfo = networkSliceInfo;
        this.mTrafficDescriptors = new ArrayList(list6);
        this.mNetworkValidationStatus = i10;
        if (i3 == 2 || i3 == 1) {
            Objects.requireNonNull(str, "Active data calls must be on a valid interface!");
            if (i != 0) {
                throw new IllegalStateException("Active data call must not have a failure!");
            }
        }
    }

    public DataCallResponse(Parcel parcel) throws ClassNotFoundException, IOException {
        this.mCause = parcel.readInt();
        this.mSuggestedRetryTime = parcel.readLong();
        this.mId = parcel.readInt();
        this.mLinkStatus = parcel.readInt();
        this.mProtocolType = parcel.readInt();
        this.mInterfaceName = parcel.readString();
        ArrayList arrayList = new ArrayList();
        this.mAddresses = arrayList;
        parcel.readList(arrayList, LinkAddress.class.getClassLoader(), LinkAddress.class);
        ArrayList arrayList2 = new ArrayList();
        this.mDnsAddresses = arrayList2;
        parcel.readList(arrayList2, InetAddress.class.getClassLoader(), InetAddress.class);
        ArrayList arrayList3 = new ArrayList();
        this.mGatewayAddresses = arrayList3;
        parcel.readList(arrayList3, InetAddress.class.getClassLoader(), InetAddress.class);
        ArrayList arrayList4 = new ArrayList();
        this.mPcscfAddresses = arrayList4;
        parcel.readList(arrayList4, InetAddress.class.getClassLoader(), InetAddress.class);
        this.mMtu = parcel.readInt();
        this.mMtuV4 = parcel.readInt();
        this.mMtuV6 = parcel.readInt();
        this.mHandoverFailureMode = parcel.readInt();
        this.mPduSessionId = parcel.readInt();
        this.mDefaultQos = (Qos) parcel.readParcelable(Qos.class.getClassLoader(), Qos.class);
        ArrayList arrayList5 = new ArrayList();
        this.mQosBearerSessions = arrayList5;
        parcel.readList(arrayList5, QosBearerSession.class.getClassLoader(), QosBearerSession.class);
        this.mSliceInfo = (NetworkSliceInfo) parcel.readParcelable(NetworkSliceInfo.class.getClassLoader(), NetworkSliceInfo.class);
        ArrayList arrayList6 = new ArrayList();
        this.mTrafficDescriptors = arrayList6;
        parcel.readList(arrayList6, TrafficDescriptor.class.getClassLoader(), TrafficDescriptor.class);
        this.mNetworkValidationStatus = parcel.readInt();
    }

    public int getCause() {
        return this.mCause;
    }

    @Deprecated
    public int getSuggestedRetryTime() {
        long j = this.mSuggestedRetryTime;
        if (j == -1) {
            return 0;
        }
        if (j > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) j;
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
        StringBuffer stringBuffer = new StringBuffer("DataCallResponse: { cause=");
        stringBuffer.append(DataFailCause.toString(this.mCause)).append(" retry=").append(this.mSuggestedRetryTime).append(" cid=").append(this.mId).append(" linkStatus=").append(this.mLinkStatus).append(" protocolType=").append(this.mProtocolType).append(" ifname=").append(this.mInterfaceName).append(" addresses=").append(this.mAddresses).append(" dnses=").append(this.mDnsAddresses).append(" gateways=").append(this.mGatewayAddresses).append(" pcscf=").append(this.mPcscfAddresses).append(" mtu=").append(getMtu()).append(" mtuV4=").append(getMtuV4()).append(" mtuV6=").append(getMtuV6()).append(" handoverFailureMode=").append(failureModeToString(this.mHandoverFailureMode)).append(" pduSessionId=").append(getPduSessionId()).append(" defaultQos=").append(this.mDefaultQos).append(" qosBearerSessions=").append(this.mQosBearerSessions).append(" sliceInfo=").append(this.mSliceInfo).append(" trafficDescriptors=").append(this.mTrafficDescriptors).append(" networkValidationStatus=").append(PreciseDataConnectionState.networkValidationStatusToString(this.mNetworkValidationStatus)).append("}");
        return stringBuffer.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DataCallResponse)) {
            return false;
        }
        DataCallResponse dataCallResponse = (DataCallResponse) obj;
        return this.mCause == dataCallResponse.mCause && this.mSuggestedRetryTime == dataCallResponse.mSuggestedRetryTime && this.mId == dataCallResponse.mId && this.mLinkStatus == dataCallResponse.mLinkStatus && this.mProtocolType == dataCallResponse.mProtocolType && this.mInterfaceName.equals(dataCallResponse.mInterfaceName) && this.mAddresses.size() == dataCallResponse.mAddresses.size() && this.mAddresses.containsAll(dataCallResponse.mAddresses) && this.mDnsAddresses.size() == dataCallResponse.mDnsAddresses.size() && this.mDnsAddresses.containsAll(dataCallResponse.mDnsAddresses) && this.mGatewayAddresses.size() == dataCallResponse.mGatewayAddresses.size() && this.mGatewayAddresses.containsAll(dataCallResponse.mGatewayAddresses) && this.mPcscfAddresses.size() == dataCallResponse.mPcscfAddresses.size() && this.mPcscfAddresses.containsAll(dataCallResponse.mPcscfAddresses) && this.mMtu == dataCallResponse.mMtu && this.mMtuV4 == dataCallResponse.mMtuV4 && this.mMtuV6 == dataCallResponse.mMtuV6 && this.mHandoverFailureMode == dataCallResponse.mHandoverFailureMode && this.mPduSessionId == dataCallResponse.mPduSessionId && Objects.equals(this.mDefaultQos, dataCallResponse.mDefaultQos) && this.mQosBearerSessions.size() == dataCallResponse.mQosBearerSessions.size() && this.mQosBearerSessions.containsAll(dataCallResponse.mQosBearerSessions) && Objects.equals(this.mSliceInfo, dataCallResponse.mSliceInfo) && this.mTrafficDescriptors.size() == dataCallResponse.mTrafficDescriptors.size() && this.mTrafficDescriptors.containsAll(dataCallResponse.mTrafficDescriptors) && this.mNetworkValidationStatus == dataCallResponse.mNetworkValidationStatus;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mCause), Long.valueOf(this.mSuggestedRetryTime), Integer.valueOf(this.mId), Integer.valueOf(this.mLinkStatus), Integer.valueOf(this.mProtocolType), this.mInterfaceName, Set.copyOf(this.mAddresses), Set.copyOf(this.mDnsAddresses), Set.copyOf(this.mGatewayAddresses), Set.copyOf(this.mPcscfAddresses), Integer.valueOf(this.mMtu), Integer.valueOf(this.mMtuV4), Integer.valueOf(this.mMtuV6), Integer.valueOf(this.mHandoverFailureMode), Integer.valueOf(this.mPduSessionId), this.mDefaultQos, Set.copyOf(this.mQosBearerSessions), this.mSliceInfo, Set.copyOf(this.mTrafficDescriptors), Integer.valueOf(this.mNetworkValidationStatus));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCause);
        parcel.writeLong(this.mSuggestedRetryTime);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mLinkStatus);
        parcel.writeInt(this.mProtocolType);
        parcel.writeString(this.mInterfaceName);
        parcel.writeList(this.mAddresses);
        parcel.writeList(this.mDnsAddresses);
        parcel.writeList(this.mGatewayAddresses);
        parcel.writeList(this.mPcscfAddresses);
        parcel.writeInt(this.mMtu);
        parcel.writeInt(this.mMtuV4);
        parcel.writeInt(this.mMtuV6);
        parcel.writeInt(this.mHandoverFailureMode);
        parcel.writeInt(this.mPduSessionId);
        parcel.writeParcelable(this.mDefaultQos, i);
        parcel.writeList(this.mQosBearerSessions);
        parcel.writeParcelable(this.mSliceInfo, i);
        parcel.writeList(this.mTrafficDescriptors);
        parcel.writeInt(this.mNetworkValidationStatus);
    }

    public static String failureModeToString(int i) {
        if (i == -1) {
            return "unknown";
        }
        if (i == 0) {
            return "legacy";
        }
        if (i == 1) {
            return XmlConstants.ATTRIBUTE_FALLBACK;
        }
        if (i == 2) {
            return "retry handover";
        }
        if (i == 3) {
            return "retry setup new one";
        }
        return Integer.toString(i);
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
        private List<LinkAddress> mAddresses = Collections.EMPTY_LIST;
        private List<InetAddress> mDnsAddresses = Collections.EMPTY_LIST;
        private List<InetAddress> mGatewayAddresses = Collections.EMPTY_LIST;
        private List<InetAddress> mPcscfAddresses = Collections.EMPTY_LIST;
        private int mHandoverFailureMode = 0;
        private int mPduSessionId = 0;
        private List<QosBearerSession> mQosBearerSessions = new ArrayList();
        private List<TrafficDescriptor> mTrafficDescriptors = new ArrayList();
        private int mNetworkValidationStatus = 0;

        public Builder setCause(int i) {
            this.mCause = i;
            return this;
        }

        @Deprecated
        public Builder setSuggestedRetryTime(int i) {
            this.mSuggestedRetryTime = i;
            return this;
        }

        public Builder setRetryDurationMillis(long j) {
            this.mSuggestedRetryTime = j;
            return this;
        }

        public Builder setId(int i) {
            this.mId = i;
            return this;
        }

        public Builder setLinkStatus(int i) {
            this.mLinkStatus = i;
            return this;
        }

        public Builder setProtocolType(int i) {
            this.mProtocolType = i;
            return this;
        }

        public Builder setInterfaceName(String str) {
            if (str == null) {
                str = "";
            }
            this.mInterfaceName = str;
            return this;
        }

        public Builder setAddresses(List<LinkAddress> list) {
            Objects.requireNonNull(list);
            this.mAddresses = list;
            return this;
        }

        public Builder setDnsAddresses(List<InetAddress> list) {
            Objects.requireNonNull(list);
            this.mDnsAddresses = list;
            return this;
        }

        public Builder setGatewayAddresses(List<InetAddress> list) {
            Objects.requireNonNull(list);
            this.mGatewayAddresses = list;
            return this;
        }

        public Builder setPcscfAddresses(List<InetAddress> list) {
            Objects.requireNonNull(list);
            this.mPcscfAddresses = list;
            return this;
        }

        public Builder setMtu(int i) {
            this.mMtu = i;
            return this;
        }

        public Builder setMtuV4(int i) {
            this.mMtuV4 = i;
            return this;
        }

        public Builder setMtuV6(int i) {
            this.mMtuV6 = i;
            return this;
        }

        public Builder setHandoverFailureMode(int i) {
            this.mHandoverFailureMode = i;
            return this;
        }

        public Builder setPduSessionId(int i) {
            Preconditions.checkArgument(i >= 0, "pduSessionId must be greater than or equal to0");
            Preconditions.checkArgument(i <= 15, "pduSessionId must be less than or equal to 15.");
            this.mPduSessionId = i;
            return this;
        }

        public Builder setDefaultQos(Qos qos) {
            this.mDefaultQos = qos;
            return this;
        }

        public Builder setQosBearerSessions(List<QosBearerSession> list) {
            Objects.requireNonNull(list);
            this.mQosBearerSessions = list;
            return this;
        }

        public Builder setSliceInfo(NetworkSliceInfo networkSliceInfo) {
            this.mSliceInfo = networkSliceInfo;
            return this;
        }

        public Builder setTrafficDescriptors(List<TrafficDescriptor> list) {
            Objects.requireNonNull(list);
            this.mTrafficDescriptors = list;
            return this;
        }

        public Builder setNetworkValidationStatus(int i) {
            this.mNetworkValidationStatus = i;
            return this;
        }

        public DataCallResponse build() {
            return new DataCallResponse(this.mCause, this.mSuggestedRetryTime, this.mId, this.mLinkStatus, this.mProtocolType, this.mInterfaceName, this.mAddresses, this.mDnsAddresses, this.mGatewayAddresses, this.mPcscfAddresses, this.mMtu, this.mMtuV4, this.mMtuV6, this.mHandoverFailureMode, this.mPduSessionId, this.mDefaultQos, this.mQosBearerSessions, this.mSliceInfo, this.mTrafficDescriptors, this.mNetworkValidationStatus);
        }
    }
}
