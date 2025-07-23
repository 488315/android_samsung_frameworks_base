package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class ExtendedRegistrationInfo implements Parcelable {
    private final boolean mIsPsOnlyReg;
    private final int mMobileOptionalRadioTech;
    private final int mSnapShotStatus;
    private final int mUnprocessedDataRadioTechnology;
    private final int mUnprocessedDataRegState;
    private final int mUnprocessedVoiceRegState;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ExtendedRegistrationInfo() {
        this.mSnapShotStatus = 0;
        this.mUnprocessedDataRegState = 0;
        this.mUnprocessedDataRadioTechnology = 0;
        this.mMobileOptionalRadioTech = 0;
        this.mUnprocessedVoiceRegState = 0;
        this.mIsPsOnlyReg = false;
    }

    public ExtendedRegistrationInfo(int i, int i2, int i3, int i4, int i5, boolean z) {
        this.mSnapShotStatus = i;
        this.mUnprocessedDataRegState = i2;
        this.mUnprocessedDataRadioTechnology = i3;
        this.mMobileOptionalRadioTech = i4;
        this.mUnprocessedVoiceRegState = i5;
        this.mIsPsOnlyReg = z;
    }

    private ExtendedRegistrationInfo(Parcel parcel) {
        this.mSnapShotStatus = parcel.readInt();
        this.mUnprocessedDataRegState = parcel.readInt();
        this.mUnprocessedDataRadioTechnology = parcel.readInt();
        this.mMobileOptionalRadioTech = parcel.readInt();
        this.mUnprocessedVoiceRegState = parcel.readInt();
        this.mIsPsOnlyReg = parcel.readBoolean();
    }

    public int getSnapShotStatus() {
        return this.mSnapShotStatus;
    }

    public int getDataMobileRegState() {
        return this.mUnprocessedDataRegState;
    }

    public int getDataMobileRadioTechnology() {
        return this.mUnprocessedDataRadioTechnology;
    }

    public int getMobileOptionalRadioTechnology() {
        return this.mMobileOptionalRadioTech;
    }

    public int getVoiceMobileRegState() {
        return this.mUnprocessedVoiceRegState;
    }

    public boolean isPsOnlyReg() {
        return this.mIsPsOnlyReg;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(256);
        sb.append("ExtendedRegistrationInfo{ snapshotStatus=");
        sb.append(this.mSnapShotStatus);
        sb.append(" unprocessedDataRegState=");
        sb.append(NetworkRegistrationInfo.registrationStateToString(this.mUnprocessedDataRegState));
        sb.append(" unprocessedDataRat=");
        sb.append(this.mUnprocessedDataRadioTechnology);
        sb.append(" mobileOptionalRat=");
        sb.append(this.mMobileOptionalRadioTech);
        sb.append(" unprocessedVoiceRegState=");
        sb.append(NetworkRegistrationInfo.registrationStateToString(this.mUnprocessedVoiceRegState));
        sb.append(" isPsOnlyReg=");
        sb.append(this.mIsPsOnlyReg);
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mSnapShotStatus), Integer.valueOf(this.mUnprocessedDataRegState), Integer.valueOf(this.mUnprocessedDataRadioTechnology), Integer.valueOf(this.mMobileOptionalRadioTech), Integer.valueOf(this.mUnprocessedVoiceRegState), Boolean.valueOf(this.mIsPsOnlyReg));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ExtendedRegistrationInfo)) {
            return false;
        }
        ExtendedRegistrationInfo extendedRegistrationInfo = (ExtendedRegistrationInfo) obj;
        return this.mSnapShotStatus == extendedRegistrationInfo.mSnapShotStatus && this.mUnprocessedDataRegState == extendedRegistrationInfo.mUnprocessedDataRegState && this.mUnprocessedDataRadioTechnology == extendedRegistrationInfo.mUnprocessedDataRadioTechnology && this.mMobileOptionalRadioTech == extendedRegistrationInfo.mMobileOptionalRadioTech && this.mUnprocessedVoiceRegState == extendedRegistrationInfo.mUnprocessedVoiceRegState && this.mIsPsOnlyReg == extendedRegistrationInfo.mIsPsOnlyReg;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSnapShotStatus);
        parcel.writeInt(this.mUnprocessedDataRegState);
        parcel.writeInt(this.mUnprocessedDataRadioTechnology);
        parcel.writeInt(this.mMobileOptionalRadioTech);
        parcel.writeInt(this.mUnprocessedVoiceRegState);
        parcel.writeBoolean(this.mIsPsOnlyReg);
    }
}
