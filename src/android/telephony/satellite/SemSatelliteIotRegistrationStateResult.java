package android.telephony.satellite;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class SemSatelliteIotRegistrationStateResult implements Parcelable {
    public static final Parcelable.Creator<SemSatelliteIotRegistrationStateResult> CREATOR = new Parcelable.Creator<SemSatelliteIotRegistrationStateResult>() { // from class: android.telephony.satellite.SemSatelliteIotRegistrationStateResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSatelliteIotRegistrationStateResult createFromParcel(Parcel parcel) {
            return new SemSatelliteIotRegistrationStateResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSatelliteIotRegistrationStateResult[] newArray(int i) {
            return new SemSatelliteIotRegistrationStateResult[i];
        }
    };
    static final boolean DBG = true;
    static final String LOG_TAG = "SatelliteIotRegState";
    public static final int SATELLITE_IOT_NOT_REGISTERED = 0;
    public static final int SATELLITE_IOT_REGISTERED = 1;
    public static final int SATELLITE_REGISTERED_TYPE_AM = 1;
    public static final int SATELLITE_REGISTERED_TYPE_NONE = 0;
    public static final int SATELLITE_REGISTERED_TYPE_TM = 2;
    private int mRegType;
    private int mState;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemSatelliteIotRegistrationStateResult() {
        this.mState = 0;
        this.mRegType = 0;
    }

    public SemSatelliteIotRegistrationStateResult(int i, int i2) {
        this.mState = i;
        this.mRegType = i2;
    }

    private SemSatelliteIotRegistrationStateResult(Parcel parcel) {
        this.mState = parcel.readInt();
        this.mRegType = parcel.readInt();
    }

    public SemSatelliteIotRegistrationStateResult(SemSatelliteIotRegistrationStateResult semSatelliteIotRegistrationStateResult) {
        copyFrom(semSatelliteIotRegistrationStateResult);
    }

    protected void copyFrom(SemSatelliteIotRegistrationStateResult semSatelliteIotRegistrationStateResult) {
        this.mState = semSatelliteIotRegistrationStateResult.mState;
        this.mRegType = semSatelliteIotRegistrationStateResult.mRegType;
    }

    public int getState() {
        return this.mState;
    }

    public int getRegType() {
        return this.mRegType;
    }

    public static String stateToString(int i) {
        if (i == 0) {
            return "IOT_NOT_REGISTERED";
        }
        if (i == 1) {
            return "IOT_REGISTERED";
        }
        return "Unknown iot reg state " + i;
    }

    public static String regTypeToString(int i) {
        if (i == 0) {
            return "IOT_REG_TYPE_NONE";
        }
        if (i == 1) {
            return "IOT_REG_TYPE_AM";
        }
        if (i == 2) {
            return "IOT_REG_TYPE_TM";
        }
        return "Unknown iot reg type " + i;
    }

    public String toString() {
        return "SatelliteIotRegistrationStateResult { state=" + stateToString(this.mState) + " regType=" + regTypeToString(this.mRegType) + "}";
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mState), Integer.valueOf(this.mRegType));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof SemSatelliteIotRegistrationStateResult)) {
            SemSatelliteIotRegistrationStateResult semSatelliteIotRegistrationStateResult = (SemSatelliteIotRegistrationStateResult) obj;
            if (this.mState == semSatelliteIotRegistrationStateResult.mState && this.mRegType == semSatelliteIotRegistrationStateResult.mRegType) {
                return true;
            }
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mState);
        parcel.writeInt(this.mRegType);
    }

    private SemSatelliteIotRegistrationStateResult copy() {
        Parcel parcelObtain = Parcel.obtain();
        writeToParcel(parcelObtain, 0);
        parcelObtain.setDataPosition(0);
        SemSatelliteIotRegistrationStateResult semSatelliteIotRegistrationStateResult = new SemSatelliteIotRegistrationStateResult(parcelObtain);
        parcelObtain.recycle();
        return semSatelliteIotRegistrationStateResult;
    }

    public static final class Builder {
        private int mRegType;
        private int mState;

        public Builder() {
        }

        public Builder(SemSatelliteIotRegistrationStateResult semSatelliteIotRegistrationStateResult) {
            this.mState = semSatelliteIotRegistrationStateResult.mState;
            this.mRegType = semSatelliteIotRegistrationStateResult.mRegType;
        }

        public Builder setState(int i) {
            this.mState = i;
            return this;
        }

        public Builder setRegType(int i) {
            this.mRegType = i;
            return this;
        }

        public SemSatelliteIotRegistrationStateResult build() {
            return new SemSatelliteIotRegistrationStateResult(this.mState, this.mRegType);
        }
    }
}
