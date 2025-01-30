package android.telephony;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class ExtendedRegistrationInfo implements Parcelable {
    private final boolean mIsPsOnlyReg;
    private final int mMobileOptionalRadioTech;
    private final int mSnapShotStatus;
    private final int mUnprocessedDataRadioTechnology;
    private final int mUnprocessedDataRegState;
    private final int mUnprocessedVoiceRegState;

    public ExtendedRegistrationInfo() {
        this.mSnapShotStatus = 0;
        this.mUnprocessedDataRegState = 0;
        this.mUnprocessedDataRadioTechnology = 0;
        this.mMobileOptionalRadioTech = 0;
        this.mUnprocessedVoiceRegState = 0;
        this.mIsPsOnlyReg = false;
    }

    public ExtendedRegistrationInfo(int snapShotStatus, int unprocessedDataRegState, int unprocessedDataRadioTechnology, int mobileOptionalRadioTech, int unprocessedVoiceRegState, boolean isPsOnlyReg) {
        this.mSnapShotStatus = snapShotStatus;
        this.mUnprocessedDataRegState = unprocessedDataRegState;
        this.mUnprocessedDataRadioTechnology = unprocessedDataRadioTechnology;
        this.mMobileOptionalRadioTech = mobileOptionalRadioTech;
        this.mUnprocessedVoiceRegState = unprocessedVoiceRegState;
        this.mIsPsOnlyReg = isPsOnlyReg;
    }

    private ExtendedRegistrationInfo(Parcel source) {
        this.mSnapShotStatus = source.readInt();
        this.mUnprocessedDataRegState = source.readInt();
        this.mUnprocessedDataRadioTechnology = source.readInt();
        this.mMobileOptionalRadioTech = source.readInt();
        this.mUnprocessedVoiceRegState = source.readInt();
        this.mIsPsOnlyReg = source.readBoolean();
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

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "ExtendedRegistrationInfo{ snapshotStatus=" + this.mSnapShotStatus + " unprocessedDataRegState=" + NetworkRegistrationInfo.registrationStateToString(this.mUnprocessedDataRegState) + " unprocessedDataRat=" + this.mUnprocessedDataRadioTechnology + " mobileOptionalRat=" + this.mMobileOptionalRadioTech + " unprocessedVoiceRegState=" + NetworkRegistrationInfo.registrationStateToString(this.mUnprocessedVoiceRegState) + " isPsOnlyReg=" + this.mIsPsOnlyReg + "}";
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mSnapShotStatus), Integer.valueOf(this.mUnprocessedDataRegState), Integer.valueOf(this.mUnprocessedDataRadioTechnology), Integer.valueOf(this.mMobileOptionalRadioTech), Integer.valueOf(this.mUnprocessedVoiceRegState), Boolean.valueOf(this.mIsPsOnlyReg));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExtendedRegistrationInfo)) {
            return false;
        }
        ExtendedRegistrationInfo other = (ExtendedRegistrationInfo) o;
        return this.mSnapShotStatus == other.mSnapShotStatus && this.mUnprocessedDataRegState == other.mUnprocessedDataRegState && this.mUnprocessedDataRadioTechnology == other.mUnprocessedDataRadioTechnology && this.mMobileOptionalRadioTech == other.mMobileOptionalRadioTech && this.mUnprocessedVoiceRegState == other.mUnprocessedVoiceRegState && this.mIsPsOnlyReg == other.mIsPsOnlyReg;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mSnapShotStatus);
        dest.writeInt(this.mUnprocessedDataRegState);
        dest.writeInt(this.mUnprocessedDataRadioTechnology);
        dest.writeInt(this.mMobileOptionalRadioTech);
        dest.writeInt(this.mUnprocessedVoiceRegState);
        dest.writeBoolean(this.mIsPsOnlyReg);
    }
}
