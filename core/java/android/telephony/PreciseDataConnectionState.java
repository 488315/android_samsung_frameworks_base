package android.telephony;

import android.annotation.SystemApi;
import android.compat.Compatibility;
import android.net.LinkProperties;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.data.ApnSetting;
import android.telephony.data.Qos;

import com.android.internal.telephony.util.TelephonyUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class PreciseDataConnectionState implements Parcelable {
    public static final Parcelable.Creator<PreciseDataConnectionState> CREATOR =
            new Parcelable.Creator<
                    PreciseDataConnectionState>() { // from class:
                                                    // android.telephony.PreciseDataConnectionState.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PreciseDataConnectionState createFromParcel(Parcel in) {
                    return new PreciseDataConnectionState(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PreciseDataConnectionState[] newArray(int size) {
                    return new PreciseDataConnectionState[size];
                }
            };
    private static final long GET_DATA_CONNECTION_STATE_R_VERSION = 148535736;
    public static final int NETWORK_VALIDATION_FAILURE = 4;
    public static final int NETWORK_VALIDATION_IN_PROGRESS = 2;
    public static final int NETWORK_VALIDATION_NOT_REQUESTED = 1;
    public static final int NETWORK_VALIDATION_SUCCESS = 3;
    public static final int NETWORK_VALIDATION_UNSUPPORTED = 0;
    private final ApnSetting mApnSetting;
    private final Qos mDefaultQos;
    private final int mFailCause;
    private final int mId;
    private final LinkProperties mLinkProperties;
    private final int mNetId;
    private final int mNetworkType;
    private final int mNetworkValidationStatus;
    private final int mState;
    private final int mTransportType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface NetworkValidationStatus {}

    @Deprecated
    public PreciseDataConnectionState(
            int state,
            int networkType,
            int apnTypes,
            String apn,
            LinkProperties linkProperties,
            int failCause) {
        this(
                -1,
                -1,
                -1,
                state,
                networkType,
                linkProperties,
                failCause,
                new ApnSetting.Builder()
                        .setApnTypeBitmask(apnTypes)
                        .setApnName(apn)
                        .setEntryName(apn)
                        .build(),
                null,
                0);
    }

    private PreciseDataConnectionState(
            int transportType,
            int id,
            int netId,
            int state,
            int networkType,
            LinkProperties linkProperties,
            int failCause,
            ApnSetting apnSetting,
            Qos defaultQos,
            int networkValidationStatus) {
        this.mTransportType = transportType;
        this.mId = id;
        this.mNetId = netId;
        this.mState = state;
        this.mNetworkType = networkType;
        this.mLinkProperties = linkProperties;
        this.mFailCause = failCause;
        this.mApnSetting = apnSetting;
        this.mDefaultQos = defaultQos;
        this.mNetworkValidationStatus = networkValidationStatus;
    }

    private PreciseDataConnectionState(Parcel in) {
        this.mTransportType = in.readInt();
        this.mId = in.readInt();
        this.mNetId = in.readInt();
        this.mState = in.readInt();
        this.mNetworkType = in.readInt();
        this.mLinkProperties =
                (LinkProperties)
                        in.readParcelable(
                                LinkProperties.class.getClassLoader(), LinkProperties.class);
        this.mFailCause = in.readInt();
        this.mApnSetting =
                (ApnSetting) in.readParcelable(ApnSetting.class.getClassLoader(), ApnSetting.class);
        this.mDefaultQos = (Qos) in.readParcelable(Qos.class.getClassLoader(), Qos.class);
        this.mNetworkValidationStatus = in.readInt();
    }

    @SystemApi
    @Deprecated
    public int getDataConnectionState() {
        if (this.mState == 4
                && !Compatibility.isChangeEnabled(GET_DATA_CONNECTION_STATE_R_VERSION)) {
            return 2;
        }
        return this.mState;
    }

    public int getTransportType() {
        return this.mTransportType;
    }

    public int getId() {
        return this.mId;
    }

    public int getNetId() {
        return this.mNetId;
    }

    public int getState() {
        return this.mState;
    }

    public int getNetworkType() {
        return this.mNetworkType;
    }

    @SystemApi
    @Deprecated
    public int getDataConnectionApnTypeBitMask() {
        if (this.mApnSetting != null) {
            return this.mApnSetting.getApnTypeBitmask();
        }
        return 0;
    }

    @SystemApi
    @Deprecated
    public String getDataConnectionApn() {
        return this.mApnSetting != null ? this.mApnSetting.getApnName() : "";
    }

    public LinkProperties getLinkProperties() {
        return this.mLinkProperties;
    }

    @SystemApi
    @Deprecated
    public int getDataConnectionFailCause() {
        return this.mFailCause;
    }

    public int getLastCauseCode() {
        return this.mFailCause;
    }

    public ApnSetting getApnSetting() {
        return this.mApnSetting;
    }

    public Qos getDefaultQos() {
        return this.mDefaultQos;
    }

    public int getNetworkValidationStatus() {
        return this.mNetworkValidationStatus;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mTransportType);
        out.writeInt(this.mId);
        out.writeInt(this.mNetId);
        out.writeInt(this.mState);
        out.writeInt(this.mNetworkType);
        out.writeParcelable(this.mLinkProperties, flags);
        out.writeInt(this.mFailCause);
        out.writeParcelable(this.mApnSetting, flags);
        out.writeParcelable(this.mDefaultQos, flags);
        out.writeInt(this.mNetworkValidationStatus);
    }

    public int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mTransportType),
                Integer.valueOf(this.mId),
                Integer.valueOf(this.mNetId),
                Integer.valueOf(this.mState),
                Integer.valueOf(this.mNetworkType),
                Integer.valueOf(this.mFailCause),
                this.mLinkProperties,
                this.mApnSetting,
                this.mDefaultQos,
                Integer.valueOf(this.mNetworkValidationStatus));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PreciseDataConnectionState that = (PreciseDataConnectionState) o;
        if (this.mTransportType == that.mTransportType
                && this.mId == that.mId
                && this.mNetId == that.mNetId
                && this.mState == that.mState
                && this.mNetworkType == that.mNetworkType
                && this.mFailCause == that.mFailCause
                && Objects.equals(this.mLinkProperties, that.mLinkProperties)
                && Objects.equals(this.mApnSetting, that.mApnSetting)
                && Objects.equals(this.mDefaultQos, that.mDefaultQos)
                && this.mNetworkValidationStatus == that.mNetworkValidationStatus) {
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" state: ").append(TelephonyUtils.dataStateToString(this.mState));
        sb.append(", transport: ")
                .append(AccessNetworkConstants.transportTypeToString(this.mTransportType));
        sb.append(", id: ").append(this.mId);
        sb.append(", netId: ").append(this.mNetId);
        sb.append(", network type: ")
                .append(TelephonyManager.getNetworkTypeName(this.mNetworkType));
        sb.append(", APN Setting: ").append(this.mApnSetting);
        sb.append(", link properties: ").append(this.mLinkProperties);
        sb.append(", default QoS: ").append(this.mDefaultQos);
        sb.append(", fail cause: ").append(DataFailCause.toString(this.mFailCause));
        sb.append(", network validation status: ")
                .append(networkValidationStatusToString(this.mNetworkValidationStatus));
        return sb.toString();
    }

    public static String networkValidationStatusToString(int networkValidationStatus) {
        switch (networkValidationStatus) {
            case 0:
                return "unsupported";
            case 1:
                return "not requested";
            case 2:
                return "in progress";
            case 3:
                return "success";
            case 4:
                return "failure";
            default:
                return Integer.toString(networkValidationStatus);
        }
    }

    public static final class Builder {
        private ApnSetting mApnSetting;
        private Qos mDefaultQos;
        private LinkProperties mLinkProperties;
        private int mTransportType = -1;
        private int mId = -1;
        private int mNetworkAgentId = -1;
        private int mState = -1;
        private int mNetworkType = 0;
        private int mFailCause = 0;
        private int mNetworkValidationStatus = 0;

        public Builder setTransportType(int transportType) {
            this.mTransportType = transportType;
            return this;
        }

        public Builder setId(int id) {
            this.mId = id;
            return this;
        }

        public Builder setNetworkAgentId(int agentId) {
            this.mNetworkAgentId = agentId;
            return this;
        }

        public Builder setState(int state) {
            this.mState = state;
            return this;
        }

        public Builder setNetworkType(int networkType) {
            this.mNetworkType = networkType;
            return this;
        }

        public Builder setLinkProperties(LinkProperties linkProperties) {
            this.mLinkProperties = linkProperties;
            return this;
        }

        public Builder setFailCause(int failCause) {
            this.mFailCause = failCause;
            return this;
        }

        public Builder setApnSetting(ApnSetting apnSetting) {
            this.mApnSetting = apnSetting;
            return this;
        }

        public Builder setDefaultQos(Qos qos) {
            this.mDefaultQos = qos;
            return this;
        }

        public Builder setNetworkValidationStatus(int networkValidationStatus) {
            this.mNetworkValidationStatus = networkValidationStatus;
            return this;
        }

        public PreciseDataConnectionState build() {
            return new PreciseDataConnectionState(
                    this.mTransportType,
                    this.mId,
                    this.mNetworkAgentId,
                    this.mState,
                    this.mNetworkType,
                    this.mLinkProperties,
                    this.mFailCause,
                    this.mApnSetting,
                    this.mDefaultQos,
                    this.mNetworkValidationStatus);
        }
    }
}
