package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class DataSpecificRegistrationInfo implements Parcelable {
    public static final Parcelable.Creator<DataSpecificRegistrationInfo> CREATOR = new Parcelable.Creator<DataSpecificRegistrationInfo>() { // from class: android.telephony.DataSpecificRegistrationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataSpecificRegistrationInfo createFromParcel(Parcel parcel) {
            return new DataSpecificRegistrationInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataSpecificRegistrationInfo[] newArray(int i) {
            return new DataSpecificRegistrationInfo[i];
        }
    };
    public static final int LTE_ATTACH_EXTRA_INFO_CSFB_NOT_PREFERRED = 1;
    public static final int LTE_ATTACH_EXTRA_INFO_NONE = 0;
    public static final int LTE_ATTACH_EXTRA_INFO_SMS_ONLY = 2;
    public static final int LTE_ATTACH_TYPE_COMBINED = 2;
    public static final int LTE_ATTACH_TYPE_EPS_ONLY = 1;
    public static final int LTE_ATTACH_TYPE_UNKNOWN = 0;
    public final boolean isDcNrRestricted;
    public final boolean isEnDcAvailable;
    public final boolean isNrAvailable;
    private final int mLteAttachExtraInfo;
    private final int mLteAttachResultType;
    private final VopsSupportInfo mVopsSupportInfo;
    public final int maxDataCalls;

    @Retention(RetentionPolicy.SOURCE)
    public @interface LteAttachExtraInfo {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LteAttachResultType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private DataSpecificRegistrationInfo(Builder builder) {
        this.maxDataCalls = builder.mMaxDataCalls;
        this.isDcNrRestricted = builder.mIsDcNrRestricted;
        this.isNrAvailable = builder.mIsNrAvailable;
        this.isEnDcAvailable = builder.mIsEnDcAvailable;
        this.mVopsSupportInfo = builder.mVopsSupportInfo;
        this.mLteAttachResultType = builder.mLteAttachResultType;
        this.mLteAttachExtraInfo = builder.mLteAttachExtraInfo;
    }

    public DataSpecificRegistrationInfo(int i, boolean z, boolean z2, boolean z3, VopsSupportInfo vopsSupportInfo) {
        this.maxDataCalls = i;
        this.isDcNrRestricted = z;
        this.isNrAvailable = z2;
        this.isEnDcAvailable = z3;
        this.mVopsSupportInfo = vopsSupportInfo;
        this.mLteAttachResultType = 0;
        this.mLteAttachExtraInfo = 0;
    }

    DataSpecificRegistrationInfo(DataSpecificRegistrationInfo dataSpecificRegistrationInfo) {
        this.maxDataCalls = dataSpecificRegistrationInfo.maxDataCalls;
        this.isDcNrRestricted = dataSpecificRegistrationInfo.isDcNrRestricted;
        this.isNrAvailable = dataSpecificRegistrationInfo.isNrAvailable;
        this.isEnDcAvailable = dataSpecificRegistrationInfo.isEnDcAvailable;
        this.mVopsSupportInfo = dataSpecificRegistrationInfo.mVopsSupportInfo;
        this.mLteAttachResultType = dataSpecificRegistrationInfo.mLteAttachResultType;
        this.mLteAttachExtraInfo = dataSpecificRegistrationInfo.mLteAttachExtraInfo;
    }

    private DataSpecificRegistrationInfo(Parcel parcel) {
        this.maxDataCalls = parcel.readInt();
        this.isDcNrRestricted = parcel.readBoolean();
        this.isNrAvailable = parcel.readBoolean();
        this.isEnDcAvailable = parcel.readBoolean();
        this.mVopsSupportInfo = (VopsSupportInfo) parcel.readParcelable(VopsSupportInfo.class.getClassLoader(), VopsSupportInfo.class);
        this.mLteAttachResultType = parcel.readInt();
        this.mLteAttachExtraInfo = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.maxDataCalls);
        parcel.writeBoolean(this.isDcNrRestricted);
        parcel.writeBoolean(this.isNrAvailable);
        parcel.writeBoolean(this.isEnDcAvailable);
        parcel.writeParcelable(this.mVopsSupportInfo, i);
        parcel.writeInt(this.mLteAttachResultType);
        parcel.writeInt(this.mLteAttachExtraInfo);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(" :{");
        sb.append(" maxDataCalls = " + this.maxDataCalls);
        sb.append(" isDcNrRestricted = " + this.isDcNrRestricted);
        sb.append(" isNrAvailable = " + this.isNrAvailable);
        sb.append(" isEnDcAvailable = " + this.isEnDcAvailable);
        sb.append(" mLteAttachResultType = " + this.mLteAttachResultType);
        sb.append(" mLteAttachExtraInfo = " + this.mLteAttachExtraInfo);
        sb.append(" " + this.mVopsSupportInfo);
        sb.append(" }");
        return sb.toString();
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.maxDataCalls), Boolean.valueOf(this.isDcNrRestricted), Boolean.valueOf(this.isNrAvailable), Boolean.valueOf(this.isEnDcAvailable), this.mVopsSupportInfo, Integer.valueOf(this.mLteAttachResultType), Integer.valueOf(this.mLteAttachExtraInfo));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DataSpecificRegistrationInfo)) {
            return false;
        }
        DataSpecificRegistrationInfo dataSpecificRegistrationInfo = (DataSpecificRegistrationInfo) obj;
        return this.maxDataCalls == dataSpecificRegistrationInfo.maxDataCalls && this.isDcNrRestricted == dataSpecificRegistrationInfo.isDcNrRestricted && this.isNrAvailable == dataSpecificRegistrationInfo.isNrAvailable && this.isEnDcAvailable == dataSpecificRegistrationInfo.isEnDcAvailable && Objects.equals(this.mVopsSupportInfo, dataSpecificRegistrationInfo.mVopsSupportInfo) && this.mLteAttachResultType == dataSpecificRegistrationInfo.mLteAttachResultType && this.mLteAttachExtraInfo == dataSpecificRegistrationInfo.mLteAttachExtraInfo;
    }

    @Deprecated
    public LteVopsSupportInfo getLteVopsSupportInfo() {
        VopsSupportInfo vopsSupportInfo = this.mVopsSupportInfo;
        if (vopsSupportInfo instanceof LteVopsSupportInfo) {
            return (LteVopsSupportInfo) vopsSupportInfo;
        }
        return new LteVopsSupportInfo(1, 1);
    }

    public VopsSupportInfo getVopsSupportInfo() {
        return this.mVopsSupportInfo;
    }

    public int getLteAttachResultType() {
        return this.mLteAttachResultType;
    }

    public int getLteAttachExtraInfo() {
        return this.mLteAttachExtraInfo;
    }

    public static final class Builder {
        private boolean mIsDcNrRestricted;
        private boolean mIsEnDcAvailable;
        private boolean mIsNrAvailable;
        private final int mMaxDataCalls;
        private VopsSupportInfo mVopsSupportInfo;
        private int mLteAttachResultType = 0;
        private int mLteAttachExtraInfo = 0;

        public Builder(int i) {
            this.mMaxDataCalls = i;
        }

        public Builder setDcNrRestricted(boolean z) {
            this.mIsDcNrRestricted = z;
            return this;
        }

        public Builder setNrAvailable(boolean z) {
            this.mIsNrAvailable = z;
            return this;
        }

        public Builder setEnDcAvailable(boolean z) {
            this.mIsEnDcAvailable = z;
            return this;
        }

        public Builder setVopsSupportInfo(VopsSupportInfo vopsSupportInfo) {
            this.mVopsSupportInfo = vopsSupportInfo;
            return this;
        }

        public Builder setLteAttachResultType(int i) {
            this.mLteAttachResultType = i;
            return this;
        }

        public Builder setLteAttachExtraInfo(int i) {
            this.mLteAttachExtraInfo = i;
            return this;
        }

        public DataSpecificRegistrationInfo build() {
            return new DataSpecificRegistrationInfo(this);
        }
    }
}
