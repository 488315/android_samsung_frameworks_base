package android.telephony.satellite;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.SemTelephonyUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class SemSatelliteRegistrationStateResult implements Parcelable {
    public static final Parcelable.Creator<SemSatelliteRegistrationStateResult> CREATOR = new Parcelable.Creator<SemSatelliteRegistrationStateResult>() { // from class: android.telephony.satellite.SemSatelliteRegistrationStateResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSatelliteRegistrationStateResult createFromParcel(Parcel parcel) {
            return new SemSatelliteRegistrationStateResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSatelliteRegistrationStateResult[] newArray(int i) {
            return new SemSatelliteRegistrationStateResult[i];
        }
    };
    static final boolean DBG = true;
    static final String LOG_TAG = "SatelliteRegStateResult";
    public static final int SATELLITE_REG_STATE_DENIED = 3;
    public static final int SATELLITE_REG_STATE_HOME = 1;
    public static final int SATELLITE_REG_STATE_NOT_REGISTERED_OR_SEARCHING = 0;
    public static final int SATELLITE_REG_STATE_NOT_REGISTERED_SEARCHING = 2;
    public static final int SATELLITE_REG_STATE_ROAMING = 5;
    public static final int SATELLITE_REG_STATE_UNKNOWN = 4;
    private int mArfcn;
    private int mBeamId;
    private int mBmLat;
    private int mBmLong;
    private int mCi;
    private int mLac;
    private int mMode;
    private int mRegState;
    private int mRejectCause;
    private int mSatelliteId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SatelliteRegState {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemSatelliteRegistrationStateResult() {
        this.mMode = 0;
        this.mRegState = 4;
        this.mLac = Integer.MAX_VALUE;
        this.mCi = Integer.MAX_VALUE;
        this.mArfcn = Integer.MAX_VALUE;
        this.mBeamId = Integer.MAX_VALUE;
        this.mBmLong = Integer.MAX_VALUE;
        this.mBmLat = Integer.MAX_VALUE;
        this.mRejectCause = -1;
        this.mSatelliteId = -1;
    }

    public SemSatelliteRegistrationStateResult(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        this.mMode = i;
        this.mRegState = i2;
        this.mLac = i3;
        this.mCi = i4;
        this.mArfcn = i5;
        this.mBeamId = i6;
        this.mBmLong = i7;
        this.mBmLat = i8;
        this.mRejectCause = i9;
        this.mSatelliteId = i10;
    }

    private SemSatelliteRegistrationStateResult(Parcel parcel) {
        this.mMode = parcel.readInt();
        this.mRegState = parcel.readInt();
        this.mLac = parcel.readInt();
        this.mCi = parcel.readInt();
        this.mArfcn = parcel.readInt();
        this.mBeamId = parcel.readInt();
        this.mBmLong = parcel.readInt();
        this.mBmLat = parcel.readInt();
        this.mRejectCause = parcel.readInt();
        this.mSatelliteId = parcel.readInt();
    }

    public SemSatelliteRegistrationStateResult(SemSatelliteRegistrationStateResult semSatelliteRegistrationStateResult) {
        copyFrom(semSatelliteRegistrationStateResult);
    }

    protected void copyFrom(SemSatelliteRegistrationStateResult semSatelliteRegistrationStateResult) {
        this.mMode = semSatelliteRegistrationStateResult.mMode;
        this.mRegState = semSatelliteRegistrationStateResult.mRegState;
        this.mLac = semSatelliteRegistrationStateResult.mLac;
        this.mCi = semSatelliteRegistrationStateResult.mCi;
        this.mArfcn = semSatelliteRegistrationStateResult.mArfcn;
        this.mBeamId = semSatelliteRegistrationStateResult.mBeamId;
        this.mBmLong = semSatelliteRegistrationStateResult.mBmLong;
        this.mBmLat = semSatelliteRegistrationStateResult.mBmLat;
        this.mRejectCause = semSatelliteRegistrationStateResult.mRejectCause;
        this.mSatelliteId = semSatelliteRegistrationStateResult.mSatelliteId;
    }

    public int getMode() {
        return this.mMode;
    }

    public int getRegState() {
        return this.mRegState;
    }

    public int getLac() {
        return this.mLac;
    }

    public int getCi() {
        return this.mCi;
    }

    public int getArfcn() {
        return this.mArfcn;
    }

    public int getBeamId() {
        return this.mBeamId;
    }

    public int getBmLong() {
        return this.mBmLong;
    }

    public int getBmLat() {
        return this.mBmLat;
    }

    public int getRejectCause() {
        return this.mRejectCause;
    }

    public int getSatelliteId() {
        return this.mSatelliteId;
    }

    public boolean isNetworkRegistered() {
        int i = this.mRegState;
        return i == 1 || i == 5;
    }

    public boolean isNetworkSearching() {
        return this.mRegState == 2;
    }

    public boolean isNetworkRoaming() {
        return this.mRegState == 5;
    }

    public static String regStateToString(int i) {
        if (i == 0) {
            return "NOT_REG_OR_SEARCHING";
        }
        if (i == 1) {
            return "HOME";
        }
        if (i == 2) {
            return "NOT_REG_SEARCHING";
        }
        if (i == 3) {
            return "DENIED";
        }
        if (i == 4) {
            return "UNKNOWN";
        }
        if (i == 5) {
            return "ROAMING";
        }
        return "Unknown reg state " + i;
    }

    public String toString() {
        return "SatelliteRegistrationStateResult { mode=" + this.mMode + " regState=" + regStateToString(this.mRegState) + " lac=" + SemTelephonyUtils.maskPii(Integer.valueOf(this.mLac)) + " cid=" + SemTelephonyUtils.maskPii(Integer.valueOf(this.mCi)) + " arfcn=" + this.mArfcn + " beamId=" + this.mBeamId + " bmLong=" + SemTelephonyUtils.maskPii(Integer.valueOf(this.mBmLong)) + " bmLat=" + SemTelephonyUtils.maskPii(Integer.valueOf(this.mBmLat)) + " rejectCause=" + this.mRejectCause + "}";
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mMode), Integer.valueOf(this.mRegState), Integer.valueOf(this.mLac), Integer.valueOf(this.mCi), Integer.valueOf(this.mArfcn), Integer.valueOf(this.mBeamId), Integer.valueOf(this.mBmLong), Integer.valueOf(this.mBmLat), Integer.valueOf(this.mRejectCause));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof SemSatelliteRegistrationStateResult)) {
            SemSatelliteRegistrationStateResult semSatelliteRegistrationStateResult = (SemSatelliteRegistrationStateResult) obj;
            if (this.mMode == semSatelliteRegistrationStateResult.mMode && this.mRegState == semSatelliteRegistrationStateResult.mRegState && this.mLac == semSatelliteRegistrationStateResult.mLac && this.mCi == semSatelliteRegistrationStateResult.mCi && this.mArfcn == semSatelliteRegistrationStateResult.mArfcn && this.mBeamId == semSatelliteRegistrationStateResult.mBeamId && this.mBmLong == semSatelliteRegistrationStateResult.mBmLong && this.mBmLat == semSatelliteRegistrationStateResult.mBmLat && this.mRejectCause == semSatelliteRegistrationStateResult.mRejectCause && this.mSatelliteId == semSatelliteRegistrationStateResult.mSatelliteId) {
                return true;
            }
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mMode);
        parcel.writeInt(this.mRegState);
        parcel.writeInt(this.mLac);
        parcel.writeInt(this.mCi);
        parcel.writeInt(this.mArfcn);
        parcel.writeInt(this.mBeamId);
        parcel.writeInt(this.mBmLong);
        parcel.writeInt(this.mBmLat);
        parcel.writeInt(this.mRejectCause);
        parcel.writeInt(this.mSatelliteId);
    }

    private SemSatelliteRegistrationStateResult copy() {
        Parcel parcelObtain = Parcel.obtain();
        writeToParcel(parcelObtain, 0);
        parcelObtain.setDataPosition(0);
        SemSatelliteRegistrationStateResult semSatelliteRegistrationStateResult = new SemSatelliteRegistrationStateResult(parcelObtain);
        parcelObtain.recycle();
        return semSatelliteRegistrationStateResult;
    }

    public static final class Builder {
        private int mArfcn;
        private int mBeamId;
        private int mBmLat;
        private int mBmLong;
        private int mCi;
        private int mLac;
        private int mMode;
        private int mRegState;
        private int mRejectCause;
        private int mSatelliteId;

        public Builder() {
        }

        public Builder(SemSatelliteRegistrationStateResult semSatelliteRegistrationStateResult) {
            this.mMode = semSatelliteRegistrationStateResult.mMode;
            this.mRegState = semSatelliteRegistrationStateResult.mRegState;
            this.mLac = semSatelliteRegistrationStateResult.mLac;
            this.mCi = semSatelliteRegistrationStateResult.mCi;
            this.mArfcn = semSatelliteRegistrationStateResult.mArfcn;
            this.mBeamId = semSatelliteRegistrationStateResult.mBeamId;
            this.mBmLong = semSatelliteRegistrationStateResult.mBmLong;
            this.mBmLat = semSatelliteRegistrationStateResult.mBmLat;
            this.mRejectCause = semSatelliteRegistrationStateResult.mRejectCause;
        }

        public Builder setMode(int i) {
            this.mMode = i;
            return this;
        }

        public Builder setRegState(int i) {
            this.mRegState = i;
            return this;
        }

        public Builder setLac(int i) {
            this.mLac = i;
            return this;
        }

        public Builder setCi(int i) {
            this.mCi = i;
            return this;
        }

        public Builder setArfcn(int i) {
            this.mArfcn = i;
            return this;
        }

        public Builder setBeamId(int i) {
            this.mBeamId = i;
            return this;
        }

        public Builder setBmLong(int i) {
            this.mBmLong = i;
            return this;
        }

        public Builder setBmLat(int i) {
            this.mBmLat = i;
            return this;
        }

        public Builder setRejCause(int i) {
            this.mRejectCause = i;
            return this;
        }

        public Builder setSatelliteId(int i) {
            this.mSatelliteId = i;
            return this;
        }

        public SemSatelliteRegistrationStateResult build() {
            return new SemSatelliteRegistrationStateResult(this.mMode, this.mRegState, this.mLac, this.mCi, this.mArfcn, this.mBeamId, this.mBmLong, this.mBmLat, this.mRejectCause, this.mSatelliteId);
        }
    }
}
