package android.telephony.satellite;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.telephony.SemTelephonyUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

public final class SemSatelliteRegistrationStateResult implements Parcelable {
    public static final Parcelable.Creator<SemSatelliteRegistrationStateResult> CREATOR =
            new Parcelable.Creator<
                    SemSatelliteRegistrationStateResult>() { // from class:
                                                             // android.telephony.satellite.SemSatelliteRegistrationStateResult.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemSatelliteRegistrationStateResult createFromParcel(Parcel source) {
                    return new SemSatelliteRegistrationStateResult(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemSatelliteRegistrationStateResult[] newArray(int size) {
                    return new SemSatelliteRegistrationStateResult[size];
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
    public @interface SatelliteRegState {}

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

    public SemSatelliteRegistrationStateResult(
            int mode,
            int regState,
            int lac,
            int ci,
            int arfcn,
            int beamId,
            int bmLong,
            int bmLat,
            int rejectCause,
            int satelliteId) {
        this.mMode = mode;
        this.mRegState = regState;
        this.mLac = lac;
        this.mCi = ci;
        this.mArfcn = arfcn;
        this.mBeamId = beamId;
        this.mBmLong = bmLong;
        this.mBmLat = bmLat;
        this.mRejectCause = rejectCause;
        this.mSatelliteId = satelliteId;
    }

    private SemSatelliteRegistrationStateResult(Parcel source) {
        this.mMode = source.readInt();
        this.mRegState = source.readInt();
        this.mLac = source.readInt();
        this.mCi = source.readInt();
        this.mArfcn = source.readInt();
        this.mBeamId = source.readInt();
        this.mBmLong = source.readInt();
        this.mBmLat = source.readInt();
        this.mRejectCause = source.readInt();
        this.mSatelliteId = source.readInt();
    }

    public SemSatelliteRegistrationStateResult(SemSatelliteRegistrationStateResult srs) {
        copyFrom(srs);
    }

    protected void copyFrom(SemSatelliteRegistrationStateResult srs) {
        this.mMode = srs.mMode;
        this.mRegState = srs.mRegState;
        this.mLac = srs.mLac;
        this.mCi = srs.mCi;
        this.mArfcn = srs.mArfcn;
        this.mBeamId = srs.mBeamId;
        this.mBmLong = srs.mBmLong;
        this.mBmLat = srs.mBmLat;
        this.mRejectCause = srs.mRejectCause;
        this.mSatelliteId = srs.mSatelliteId;
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
        return this.mRegState == 1 || this.mRegState == 5;
    }

    public boolean isNetworkSearching() {
        return this.mRegState == 2;
    }

    public boolean isNetworkRoaming() {
        return this.mRegState == 5;
    }

    public static String regStateToString(int registrationState) {
        switch (registrationState) {
            case 0:
                return "NOT_REG_OR_SEARCHING";
            case 1:
                return "HOME";
            case 2:
                return "NOT_REG_SEARCHING";
            case 3:
                return "DENIED";
            case 4:
                return "UNKNOWN";
            case 5:
                return "ROAMING";
            default:
                return "Unknown reg state " + registrationState;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "SatelliteRegistrationStateResult { mode="
                + this.mMode
                + " regState="
                + regStateToString(this.mRegState)
                + " lac="
                + SemTelephonyUtils.maskPii(Integer.valueOf(this.mLac))
                + " cid="
                + SemTelephonyUtils.maskPii(Integer.valueOf(this.mCi))
                + " arfcn="
                + this.mArfcn
                + " beamId="
                + this.mBeamId
                + " bmLong="
                + SemTelephonyUtils.maskPii(Integer.valueOf(this.mBmLong))
                + " bmLat="
                + SemTelephonyUtils.maskPii(Integer.valueOf(this.mBmLat))
                + " rejectCause="
                + this.mRejectCause
                + "}";
    }

    public int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mMode),
                Integer.valueOf(this.mRegState),
                Integer.valueOf(this.mLac),
                Integer.valueOf(this.mCi),
                Integer.valueOf(this.mArfcn),
                Integer.valueOf(this.mBeamId),
                Integer.valueOf(this.mBmLong),
                Integer.valueOf(this.mBmLat),
                Integer.valueOf(this.mRejectCause));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof SemSatelliteRegistrationStateResult)) {
            return false;
        }
        SemSatelliteRegistrationStateResult other = (SemSatelliteRegistrationStateResult) o;
        if (this.mMode == other.mMode
                && this.mRegState == other.mRegState
                && this.mLac == other.mLac
                && this.mCi == other.mCi
                && this.mArfcn == other.mArfcn
                && this.mBeamId == other.mBeamId
                && this.mBmLong == other.mBmLong
                && this.mBmLat == other.mBmLat
                && this.mRejectCause == other.mRejectCause
                && this.mSatelliteId == other.mSatelliteId) {
            return true;
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mMode);
        dest.writeInt(this.mRegState);
        dest.writeInt(this.mLac);
        dest.writeInt(this.mCi);
        dest.writeInt(this.mArfcn);
        dest.writeInt(this.mBeamId);
        dest.writeInt(this.mBmLong);
        dest.writeInt(this.mBmLat);
        dest.writeInt(this.mRejectCause);
        dest.writeInt(this.mSatelliteId);
    }

    private SemSatelliteRegistrationStateResult copy() {
        Parcel p = Parcel.obtain();
        writeToParcel(p, 0);
        p.setDataPosition(0);
        SemSatelliteRegistrationStateResult result = new SemSatelliteRegistrationStateResult(p);
        p.recycle();
        return result;
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

        public Builder() {}

        public Builder(SemSatelliteRegistrationStateResult srs) {
            this.mMode = srs.mMode;
            this.mRegState = srs.mRegState;
            this.mLac = srs.mLac;
            this.mCi = srs.mCi;
            this.mArfcn = srs.mArfcn;
            this.mBeamId = srs.mBeamId;
            this.mBmLong = srs.mBmLong;
            this.mBmLat = srs.mBmLat;
            this.mRejectCause = srs.mRejectCause;
        }

        public Builder setMode(int mode) {
            this.mMode = mode;
            return this;
        }

        public Builder setRegState(int regState) {
            this.mRegState = regState;
            return this;
        }

        public Builder setLac(int lac) {
            this.mLac = lac;
            return this;
        }

        public Builder setCi(int ci) {
            this.mCi = ci;
            return this;
        }

        public Builder setArfcn(int arfcn) {
            this.mArfcn = arfcn;
            return this;
        }

        public Builder setBeamId(int beamId) {
            this.mBeamId = beamId;
            return this;
        }

        public Builder setBmLong(int bmLong) {
            this.mBmLong = bmLong;
            return this;
        }

        public Builder setBmLat(int bmLat) {
            this.mBmLat = bmLat;
            return this;
        }

        public Builder setRejCause(int rejectCause) {
            this.mRejectCause = rejectCause;
            return this;
        }

        public Builder setSatelliteId(int satelliteId) {
            this.mSatelliteId = satelliteId;
            return this;
        }

        public SemSatelliteRegistrationStateResult build() {
            return new SemSatelliteRegistrationStateResult(
                    this.mMode,
                    this.mRegState,
                    this.mLac,
                    this.mCi,
                    this.mArfcn,
                    this.mBeamId,
                    this.mBmLong,
                    this.mBmLat,
                    this.mRejectCause,
                    this.mSatelliteId);
        }
    }
}
