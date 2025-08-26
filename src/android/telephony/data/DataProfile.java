package android.telephony.data;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.TelephonyManager;
import android.telephony.data.ApnSetting;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class DataProfile implements Parcelable {
    public static final Parcelable.Creator<DataProfile> CREATOR = new Parcelable.Creator<DataProfile>() { // from class: android.telephony.data.DataProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataProfile createFromParcel(Parcel parcel) {
            return new DataProfile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataProfile[] newArray(int i) {
            return new DataProfile[i];
        }
    };
    public static final int TYPE_3GPP = 1;
    public static final int TYPE_3GPP2 = 2;
    public static final int TYPE_COMMON = 0;
    private boolean hasFailure;
    private final ApnSetting mApnSetting;
    private int mCid;
    private boolean mPreferred;
    private int mProfileId;
    private long mSetupTimestamp;
    private final TrafficDescriptor mTrafficDescriptor;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    private static int networkCapabilityToApnType(int i) {
        if (i == 0) {
            return 2;
        }
        if (i == 1) {
            return 4;
        }
        if (i == 2) {
            return 8;
        }
        if (i == 3) {
            return 32;
        }
        if (i == 4) {
            return 64;
        }
        if (i == 5) {
            return 128;
        }
        if (i == 12) {
            return 17;
        }
        if (i == 23) {
            return 1024;
        }
        switch (i) {
            case 7:
                return 256;
            case 8:
                return 32768;
            case 9:
                return 2048;
            case 10:
                return 512;
            default:
                switch (i) {
                    case 29:
                        return 16384;
                    case 30:
                        return 4096;
                    case 31:
                        return 8192;
                    default:
                        return 0;
                }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private DataProfile(Builder builder) {
        int networkTypeBitmask;
        this.hasFailure = false;
        ApnSetting apnSetting = builder.mApnSetting;
        this.mApnSetting = apnSetting;
        this.mTrafficDescriptor = builder.mTrafficDescriptor;
        this.mPreferred = builder.mPreferred;
        if (builder.mType != -1) {
            this.mType = builder.mType;
        } else if (apnSetting == null || (networkTypeBitmask = apnSetting.getNetworkTypeBitmask()) == 0) {
            this.mType = 0;
        } else {
            long j = networkTypeBitmask;
            if ((TelephonyManager.NETWORK_STANDARDS_FAMILY_BITMASK_3GPP2 & j) == j) {
                this.mType = 2;
            } else if ((TelephonyManager.NETWORK_STANDARDS_FAMILY_BITMASK_3GPP & j) == j) {
                this.mType = 1;
            } else {
                this.mType = 0;
            }
        }
        this.mCid = builder.mCid;
        this.mProfileId = builder.mProfileId;
    }

    private DataProfile(Parcel parcel) {
        this.hasFailure = false;
        this.mType = parcel.readInt();
        this.mApnSetting = (ApnSetting) parcel.readParcelable(ApnSetting.class.getClassLoader(), ApnSetting.class);
        this.mTrafficDescriptor = (TrafficDescriptor) parcel.readParcelable(TrafficDescriptor.class.getClassLoader(), TrafficDescriptor.class);
        this.mPreferred = parcel.readBoolean();
        this.mSetupTimestamp = parcel.readLong();
        this.mCid = parcel.readInt();
        this.mProfileId = parcel.readInt();
    }

    @Deprecated
    public int getProfileId() {
        int i = this.mProfileId;
        if (i >= 0) {
            return i;
        }
        ApnSetting apnSetting = this.mApnSetting;
        if (apnSetting != null) {
            return apnSetting.getProfileId();
        }
        return 0;
    }

    public void setProfileId(int i) {
        this.mProfileId = i;
    }

    @Deprecated
    public String getApn() {
        ApnSetting apnSetting = this.mApnSetting;
        if (apnSetting != null) {
            return TextUtils.emptyIfNull(apnSetting.getApnName());
        }
        return "";
    }

    @Deprecated
    public int getProtocolType() {
        ApnSetting apnSetting = this.mApnSetting;
        if (apnSetting != null) {
            return apnSetting.getProtocol();
        }
        return 2;
    }

    @Deprecated
    public int getAuthType() {
        ApnSetting apnSetting = this.mApnSetting;
        if (apnSetting != null) {
            return apnSetting.getAuthType();
        }
        return 0;
    }

    @Deprecated
    public String getUserName() {
        ApnSetting apnSetting = this.mApnSetting;
        if (apnSetting != null) {
            return apnSetting.getUser();
        }
        return null;
    }

    @Deprecated
    public String getPassword() {
        ApnSetting apnSetting = this.mApnSetting;
        if (apnSetting != null) {
            return apnSetting.getPassword();
        }
        return null;
    }

    public int getType() {
        return this.mType;
    }

    public int getMaxConnectionsTime() {
        ApnSetting apnSetting = this.mApnSetting;
        if (apnSetting != null) {
            return apnSetting.getMaxConnsTime();
        }
        return 0;
    }

    public int getMaxConnections() {
        ApnSetting apnSetting = this.mApnSetting;
        if (apnSetting != null) {
            return apnSetting.getMaxConns();
        }
        return 0;
    }

    public int getWaitTime() {
        ApnSetting apnSetting = this.mApnSetting;
        if (apnSetting != null) {
            return apnSetting.getWaitTime();
        }
        return 0;
    }

    public boolean isEnabled() {
        ApnSetting apnSetting = this.mApnSetting;
        if (apnSetting != null) {
            return apnSetting.isEnabled();
        }
        return true;
    }

    @Deprecated
    public int getSupportedApnTypesBitmask() {
        ApnSetting apnSetting = this.mApnSetting;
        if (apnSetting != null) {
            return apnSetting.getApnTypeBitmask();
        }
        return 0;
    }

    @Deprecated
    public int getRoamingProtocolType() {
        ApnSetting apnSetting = this.mApnSetting;
        if (apnSetting != null) {
            return apnSetting.getRoamingProtocol();
        }
        return 0;
    }

    @Deprecated
    public int getBearerBitmask() {
        ApnSetting apnSetting = this.mApnSetting;
        if (apnSetting != null) {
            return apnSetting.getNetworkTypeBitmask();
        }
        return 0;
    }

    @Deprecated
    public int getMtu() {
        return getMtuV4();
    }

    @Deprecated
    public int getMtuV4() {
        ApnSetting apnSetting = this.mApnSetting;
        if (apnSetting != null) {
            return apnSetting.getMtuV4();
        }
        return 0;
    }

    @Deprecated
    public int getMtuV6() {
        ApnSetting apnSetting = this.mApnSetting;
        if (apnSetting != null) {
            return apnSetting.getMtuV6();
        }
        return 0;
    }

    @Deprecated
    public boolean isPersistent() {
        ApnSetting apnSetting = this.mApnSetting;
        if (apnSetting != null) {
            return apnSetting.isPersistent();
        }
        return false;
    }

    public void setPreferred(boolean z) {
        this.mPreferred = z;
    }

    public boolean isPreferred() {
        return this.mPreferred;
    }

    public int getCid() {
        return this.mCid;
    }

    public void setCid(int i) {
        this.mCid = i;
    }

    public ApnSetting getApnSetting() {
        return this.mApnSetting;
    }

    public TrafficDescriptor getTrafficDescriptor() {
        return this.mTrafficDescriptor;
    }

    public boolean canSatisfy(int[] iArr) {
        if (this.mApnSetting == null) {
            return false;
        }
        for (int i : iArr) {
            if (!canSatisfy(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean canSatisfy(int i) {
        ApnSetting apnSetting = this.mApnSetting;
        return apnSetting != null && apnSetting.canHandleType(networkCapabilityToApnType(i));
    }

    public void setLastSetupTimestamp(long j) {
        this.mSetupTimestamp = j;
    }

    public long getLastSetupTimestamp() {
        return this.mSetupTimestamp;
    }

    public String toString() {
        return "[DataProfile=" + this.mApnSetting + ", " + this.mTrafficDescriptor + ", preferred=" + this.mPreferred + ", cid=" + this.mCid + ", profileId=" + this.mProfileId + ", mSetupTimestamp: " + this.mSetupTimestamp + ", hasFailure=" + this.hasFailure + NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeParcelable(this.mApnSetting, i);
        parcel.writeParcelable(this.mTrafficDescriptor, i);
        parcel.writeBoolean(this.mPreferred);
        parcel.writeLong(this.mSetupTimestamp);
        parcel.writeInt(this.mCid);
        parcel.writeInt(this.mProfileId);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            DataProfile dataProfile = (DataProfile) obj;
            if (this.mType == dataProfile.mType && Objects.equals(this.mApnSetting, dataProfile.mApnSetting) && Objects.equals(this.mTrafficDescriptor, dataProfile.mTrafficDescriptor)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mType), this.mApnSetting, this.mTrafficDescriptor);
    }

    public static final class Builder {
        private String mApn;
        private ApnSetting mApnSetting;
        private int mAuthType;
        private int mBearerBitmask;
        private int mMtuV4;
        private int mMtuV6;
        private String mPassword;
        private boolean mPersistent;
        private boolean mPreferred;
        private int mProtocolType;
        private int mRoamingProtocolType;
        private int mSupportedApnTypesBitmask;
        private TrafficDescriptor mTrafficDescriptor;
        private String mUserName;
        private int mProfileId = -1;
        private int mType = -1;
        private boolean mEnabled = true;
        private int mCid = -1;

        @Deprecated
        public Builder setProfileId(int i) {
            this.mProfileId = i;
            return this;
        }

        @Deprecated
        public Builder setApn(String str) {
            this.mApn = str;
            return this;
        }

        @Deprecated
        public Builder setProtocolType(int i) {
            this.mProtocolType = i;
            return this;
        }

        @Deprecated
        public Builder setAuthType(int i) {
            this.mAuthType = i;
            return this;
        }

        @Deprecated
        public Builder setUserName(String str) {
            this.mUserName = str;
            return this;
        }

        @Deprecated
        public Builder setPassword(String str) {
            this.mPassword = str;
            return this;
        }

        public Builder setType(int i) {
            this.mType = i;
            return this;
        }

        public Builder enable(boolean z) {
            this.mEnabled = z;
            return this;
        }

        @Deprecated
        public Builder setSupportedApnTypesBitmask(int i) {
            this.mSupportedApnTypesBitmask = i;
            return this;
        }

        @Deprecated
        public Builder setRoamingProtocolType(int i) {
            this.mRoamingProtocolType = i;
            return this;
        }

        @Deprecated
        public Builder setBearerBitmask(int i) {
            this.mBearerBitmask = i;
            return this;
        }

        @Deprecated
        public Builder setMtu(int i) {
            this.mMtuV6 = i;
            this.mMtuV4 = i;
            return this;
        }

        @Deprecated
        public Builder setMtuV4(int i) {
            this.mMtuV4 = i;
            return this;
        }

        @Deprecated
        public Builder setMtuV6(int i) {
            this.mMtuV6 = i;
            return this;
        }

        public Builder setPreferred(boolean z) {
            this.mPreferred = z;
            return this;
        }

        @Deprecated
        public Builder setPersistent(boolean z) {
            this.mPersistent = z;
            return this;
        }

        public Builder setCid(int i) {
            this.mCid = i;
            return this;
        }

        public Builder setApnSetting(ApnSetting apnSetting) {
            this.mApnSetting = apnSetting;
            return this;
        }

        public Builder setTrafficDescriptor(TrafficDescriptor trafficDescriptor) {
            this.mTrafficDescriptor = trafficDescriptor;
            return this;
        }

        public DataProfile build() {
            if (this.mApnSetting == null && this.mApn != null) {
                this.mApnSetting = new ApnSetting.Builder().setEntryName(this.mApn).setApnName(this.mApn).setApnTypeBitmask(this.mSupportedApnTypesBitmask).setAuthType(this.mAuthType).setCarrierEnabled(this.mEnabled).setModemCognitive(this.mPersistent).setMtuV4(this.mMtuV4).setMtuV6(this.mMtuV6).setNetworkTypeBitmask(this.mBearerBitmask).setProfileId(this.mProfileId).setPassword(this.mPassword).setProtocol(this.mProtocolType).setRoamingProtocol(this.mRoamingProtocolType).setUser(this.mUserName).build();
            }
            if (this.mApnSetting == null && this.mTrafficDescriptor == null) {
                throw new IllegalArgumentException("APN setting and traffic descriptor can't be both null.");
            }
            return new DataProfile(this);
        }
    }

    public boolean getHasFailure() {
        return this.hasFailure;
    }

    public void setHasFailure(boolean z) {
        this.hasFailure = z;
    }
}
