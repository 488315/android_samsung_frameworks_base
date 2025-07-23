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
    public static final Parcelable.Creator<PreciseDataConnectionState> CREATOR = new Parcelable.Creator<PreciseDataConnectionState>() { // from class: android.telephony.PreciseDataConnectionState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PreciseDataConnectionState createFromParcel(Parcel parcel) {
            return new PreciseDataConnectionState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PreciseDataConnectionState[] newArray(int i) {
            return new PreciseDataConnectionState[i];
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
    public @interface NetworkValidationStatus {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public PreciseDataConnectionState(int i, int i2, int i3, String str, LinkProperties linkProperties, int i4) {
        this(-1, -1, -1, i, i2, linkProperties, i4, new ApnSetting.Builder().setApnTypeBitmask(i3).setApnName(str).setEntryName(str).build(), null, 0);
    }

    private PreciseDataConnectionState(int i, int i2, int i3, int i4, int i5, LinkProperties linkProperties, int i6, ApnSetting apnSetting, Qos qos, int i7) {
        this.mTransportType = i;
        this.mId = i2;
        this.mNetId = i3;
        this.mState = i4;
        this.mNetworkType = i5;
        this.mLinkProperties = linkProperties;
        this.mFailCause = i6;
        this.mApnSetting = apnSetting;
        this.mDefaultQos = qos;
        this.mNetworkValidationStatus = i7;
    }

    private PreciseDataConnectionState(Parcel parcel) {
        this.mTransportType = parcel.readInt();
        this.mId = parcel.readInt();
        this.mNetId = parcel.readInt();
        this.mState = parcel.readInt();
        this.mNetworkType = parcel.readInt();
        this.mLinkProperties = (LinkProperties) parcel.readParcelable(LinkProperties.class.getClassLoader(), LinkProperties.class);
        this.mFailCause = parcel.readInt();
        this.mApnSetting = (ApnSetting) parcel.readParcelable(ApnSetting.class.getClassLoader(), ApnSetting.class);
        this.mDefaultQos = (Qos) parcel.readParcelable(Qos.class.getClassLoader(), Qos.class);
        this.mNetworkValidationStatus = parcel.readInt();
    }

    @SystemApi
    @Deprecated
    public int getDataConnectionState() {
        if (this.mState != 4 || Compatibility.isChangeEnabled(GET_DATA_CONNECTION_STATE_R_VERSION)) {
            return this.mState;
        }
        return 2;
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
        ApnSetting apnSetting = this.mApnSetting;
        if (apnSetting != null) {
            return apnSetting.getApnTypeBitmask();
        }
        return 0;
    }

    @SystemApi
    @Deprecated
    public String getDataConnectionApn() {
        ApnSetting apnSetting = this.mApnSetting;
        return apnSetting != null ? apnSetting.getApnName() : "";
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mTransportType);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mNetId);
        parcel.writeInt(this.mState);
        parcel.writeInt(this.mNetworkType);
        parcel.writeParcelable(this.mLinkProperties, i);
        parcel.writeInt(this.mFailCause);
        parcel.writeParcelable(this.mApnSetting, i);
        parcel.writeParcelable(this.mDefaultQos, i);
        parcel.writeInt(this.mNetworkValidationStatus);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mTransportType), Integer.valueOf(this.mId), Integer.valueOf(this.mNetId), Integer.valueOf(this.mState), Integer.valueOf(this.mNetworkType), Integer.valueOf(this.mFailCause), this.mLinkProperties, this.mApnSetting, this.mDefaultQos, Integer.valueOf(this.mNetworkValidationStatus));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PreciseDataConnectionState preciseDataConnectionState = (PreciseDataConnectionState) obj;
            if (this.mTransportType == preciseDataConnectionState.mTransportType && this.mId == preciseDataConnectionState.mId && this.mNetId == preciseDataConnectionState.mNetId && this.mState == preciseDataConnectionState.mState && this.mNetworkType == preciseDataConnectionState.mNetworkType && this.mFailCause == preciseDataConnectionState.mFailCause && Objects.equals(this.mLinkProperties, preciseDataConnectionState.mLinkProperties) && Objects.equals(this.mApnSetting, preciseDataConnectionState.mApnSetting) && Objects.equals(this.mDefaultQos, preciseDataConnectionState.mDefaultQos) && this.mNetworkValidationStatus == preciseDataConnectionState.mNetworkValidationStatus) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return " state: " + TelephonyUtils.dataStateToString(this.mState) + ", transport: " + AccessNetworkConstants.transportTypeToString(this.mTransportType) + ", id: " + this.mId + ", netId: " + this.mNetId + ", network type: " + TelephonyManager.getNetworkTypeName(this.mNetworkType) + ", APN Setting: " + this.mApnSetting + ", link properties: " + this.mLinkProperties + ", default QoS: " + this.mDefaultQos + ", fail cause: " + DataFailCause.toString(this.mFailCause) + ", network validation status: " + networkValidationStatusToString(this.mNetworkValidationStatus);
    }

    public static String networkValidationStatusToString(int i) {
        if (i == 0) {
            return "unsupported";
        }
        if (i == 1) {
            return "not requested";
        }
        if (i == 2) {
            return "in progress";
        }
        if (i == 3) {
            return "success";
        }
        if (i == 4) {
            return "failure";
        }
        return Integer.toString(i);
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

        public Builder setTransportType(int i) {
            this.mTransportType = i;
            return this;
        }

        public Builder setId(int i) {
            this.mId = i;
            return this;
        }

        public Builder setNetworkAgentId(int i) {
            this.mNetworkAgentId = i;
            return this;
        }

        public Builder setState(int i) {
            this.mState = i;
            return this;
        }

        public Builder setNetworkType(int i) {
            this.mNetworkType = i;
            return this;
        }

        public Builder setLinkProperties(LinkProperties linkProperties) {
            this.mLinkProperties = linkProperties;
            return this;
        }

        public Builder setFailCause(int i) {
            this.mFailCause = i;
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

        public Builder setNetworkValidationStatus(int i) {
            this.mNetworkValidationStatus = i;
            return this;
        }

        public PreciseDataConnectionState build() {
            return new PreciseDataConnectionState(this.mTransportType, this.mId, this.mNetworkAgentId, this.mState, this.mNetworkType, this.mLinkProperties, this.mFailCause, this.mApnSetting, this.mDefaultQos, this.mNetworkValidationStatus);
        }
    }
}
