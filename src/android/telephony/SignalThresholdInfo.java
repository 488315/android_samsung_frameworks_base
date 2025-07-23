package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class SignalThresholdInfo implements Parcelable {
    public static final Parcelable.Creator<SignalThresholdInfo> CREATOR = new Parcelable.Creator<SignalThresholdInfo>() { // from class: android.telephony.SignalThresholdInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SignalThresholdInfo createFromParcel(Parcel parcel) {
            return new SignalThresholdInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SignalThresholdInfo[] newArray(int i) {
            return new SignalThresholdInfo[i];
        }
    };
    private static final int HYSTERESIS_DB_DEFAULT = 2;
    public static final int HYSTERESIS_DB_MINIMUM = 0;
    public static final int HYSTERESIS_MS_DISABLED = 0;
    public static final int MAXIMUM_NUMBER_OF_THRESHOLDS_ALLOWED = 4;
    public static final int MINIMUM_NUMBER_OF_THRESHOLDS_ALLOWED = 1;
    public static final int SIGNAL_ECNO_MAX_VALUE = 1;
    public static final int SIGNAL_ECNO_MIN_VALUE = -24;
    public static final int SIGNAL_MEASUREMENT_TYPE_ECNO = 9;
    public static final int SIGNAL_MEASUREMENT_TYPE_RSCP = 2;
    public static final int SIGNAL_MEASUREMENT_TYPE_RSRP = 3;
    public static final int SIGNAL_MEASUREMENT_TYPE_RSRQ = 4;
    public static final int SIGNAL_MEASUREMENT_TYPE_RSSI = 1;
    public static final int SIGNAL_MEASUREMENT_TYPE_RSSNR = 5;
    public static final int SIGNAL_MEASUREMENT_TYPE_SSRSRP = 6;
    public static final int SIGNAL_MEASUREMENT_TYPE_SSRSRQ = 7;
    public static final int SIGNAL_MEASUREMENT_TYPE_SSSINR = 8;
    public static final int SIGNAL_MEASUREMENT_TYPE_UNKNOWN = 0;
    public static final int SIGNAL_RSCP_MAX_VALUE = -25;
    public static final int SIGNAL_RSCP_MIN_VALUE = -120;
    public static final int SIGNAL_RSRP_MAX_VALUE = -44;
    public static final int SIGNAL_RSRP_MIN_VALUE = -140;
    public static final int SIGNAL_RSRQ_MAX_VALUE = 3;
    public static final int SIGNAL_RSRQ_MIN_VALUE = -34;
    public static final int SIGNAL_RSSI_MAX_VALUE = -51;
    public static final int SIGNAL_RSSI_MIN_VALUE = -113;
    public static final int SIGNAL_RSSNR_MAX_VALUE = 30;
    public static final int SIGNAL_RSSNR_MIN_VALUE = -20;
    public static final int SIGNAL_SSRSRP_MAX_VALUE = -44;
    public static final int SIGNAL_SSRSRP_MIN_VALUE = -140;
    public static final int SIGNAL_SSRSRQ_MAX_VALUE = 20;
    public static final int SIGNAL_SSRSRQ_MIN_VALUE = -43;
    public static final int SIGNAL_SSSINR_MAX_VALUE = 40;
    public static final int SIGNAL_SSSINR_MIN_VALUE = -23;
    private final int mHysteresisDb;
    private final int mHysteresisMs;
    private final boolean mIsEnabled;
    private final int mRan;
    private final int mSignalMeasurementType;
    private final int[] mThresholds;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SignalMeasurementType {
    }

    public static int getMaximumNumberOfThresholdsAllowed() {
        return 4;
    }

    public static int getMinimumNumberOfThresholdsAllowed() {
        return 1;
    }

    private static boolean isValidRanWithMeasurementType(int i, int i2) {
        switch (i2) {
            case 1:
                return i == 1 || i == 4;
            case 2:
            case 9:
                return i == 2;
            case 3:
            case 4:
            case 5:
                return i == 3;
            case 6:
            case 7:
            case 8:
                return i == 6;
            default:
                return false;
        }
    }

    private static boolean isValidThreshold(int i, int i2) {
        switch (i) {
            case 1:
                return i2 >= -113 && i2 <= -51;
            case 2:
                return i2 >= -120 && i2 <= -25;
            case 3:
                return i2 >= -140 && i2 <= -44;
            case 4:
                return i2 >= -34 && i2 <= 3;
            case 5:
                return i2 >= -20 && i2 <= 30;
            case 6:
                return i2 >= -140 && i2 <= -44;
            case 7:
                return i2 >= -43 && i2 <= 20;
            case 8:
                return i2 >= -23 && i2 <= 40;
            case 9:
                return i2 >= -24 && i2 <= 1;
            default:
                return false;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SignalThresholdInfo(int i, int i2, int i3, int i4, int[] iArr, boolean z) {
        Objects.requireNonNull(iArr, "thresholds must not be null");
        validateRanWithMeasurementType(i, i2);
        validateThresholdRange(i2, iArr);
        this.mRan = i;
        this.mSignalMeasurementType = i2;
        this.mHysteresisMs = i3 < 0 ? 0 : i3;
        this.mHysteresisDb = i4;
        this.mThresholds = iArr;
        this.mIsEnabled = z;
    }

    public static final class Builder {
        private int mRan = 0;
        private int mSignalMeasurementType = 0;
        private int mHysteresisMs = 0;
        private int mHysteresisDb = 2;
        private int[] mThresholds = null;
        private boolean mIsEnabled = false;

        public Builder setRadioAccessNetworkType(int i) {
            this.mRan = i;
            return this;
        }

        public Builder setSignalMeasurementType(int i) {
            this.mSignalMeasurementType = i;
            return this;
        }

        public Builder setHysteresisMs(int i) {
            this.mHysteresisMs = i;
            return this;
        }

        public Builder setHysteresisDb(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("hysteresis db value should not be less than 0");
            }
            this.mHysteresisDb = i;
            return this;
        }

        public Builder setThresholds(int[] iArr) {
            return setThresholds(iArr, false);
        }

        public Builder setThresholds(int[] iArr, boolean z) {
            Objects.requireNonNull(iArr, "thresholds must not be null");
            if (!z && (iArr.length < 1 || iArr.length > 4)) {
                throw new IllegalArgumentException("thresholds length must between 1 and 4");
            }
            int[] iArr2 = (int[]) iArr.clone();
            this.mThresholds = iArr2;
            Arrays.sort(iArr2);
            return this;
        }

        public Builder setIsEnabled(boolean z) {
            this.mIsEnabled = z;
            return this;
        }

        public SignalThresholdInfo build() {
            return new SignalThresholdInfo(this.mRan, this.mSignalMeasurementType, this.mHysteresisMs, this.mHysteresisDb, this.mThresholds, this.mIsEnabled);
        }
    }

    public int getRadioAccessNetworkType() {
        return this.mRan;
    }

    public int getSignalMeasurementType() {
        return this.mSignalMeasurementType;
    }

    public int getHysteresisMs() {
        return this.mHysteresisMs;
    }

    public int getHysteresisDb() {
        return this.mHysteresisDb;
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public int[] getThresholds() {
        return (int[]) this.mThresholds.clone();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRan);
        parcel.writeInt(this.mSignalMeasurementType);
        parcel.writeInt(this.mHysteresisMs);
        parcel.writeInt(this.mHysteresisDb);
        parcel.writeIntArray(this.mThresholds);
        parcel.writeBoolean(this.mIsEnabled);
    }

    private SignalThresholdInfo(Parcel parcel) {
        this.mRan = parcel.readInt();
        this.mSignalMeasurementType = parcel.readInt();
        this.mHysteresisMs = parcel.readInt();
        this.mHysteresisDb = parcel.readInt();
        this.mThresholds = parcel.createIntArray();
        this.mIsEnabled = parcel.readBoolean();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SignalThresholdInfo)) {
            return false;
        }
        SignalThresholdInfo signalThresholdInfo = (SignalThresholdInfo) obj;
        return this.mRan == signalThresholdInfo.mRan && this.mSignalMeasurementType == signalThresholdInfo.mSignalMeasurementType && this.mHysteresisMs == signalThresholdInfo.mHysteresisMs && this.mHysteresisDb == signalThresholdInfo.mHysteresisDb && Arrays.equals(this.mThresholds, signalThresholdInfo.mThresholds) && this.mIsEnabled == signalThresholdInfo.mIsEnabled;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mRan), Integer.valueOf(this.mSignalMeasurementType), Integer.valueOf(this.mHysteresisMs), Integer.valueOf(this.mHysteresisDb), Integer.valueOf(Arrays.hashCode(this.mThresholds)), Boolean.valueOf(this.mIsEnabled));
    }

    public String toString() {
        return "SignalThresholdInfo{mRan=" + this.mRan + " mSignalMeasurementType=" + this.mSignalMeasurementType + " mHysteresisMs=" + this.mHysteresisMs + " mHysteresisDb=" + this.mHysteresisDb + " mThresholds=" + Arrays.toString(this.mThresholds) + " mIsEnabled=" + this.mIsEnabled + "}";
    }

    private void validateRanWithMeasurementType(int i, int i2) {
        if (isValidRanWithMeasurementType(i, i2)) {
            return;
        }
        throw new IllegalArgumentException("invalid RAN: " + i + " with signal measurement type: " + i2);
    }

    private void validateThresholdRange(int i, int[] iArr) {
        for (int i2 : iArr) {
            if (!isValidThreshold(i, i2)) {
                throw new IllegalArgumentException("invalid signal measurement type: " + i + " with threshold: " + i2);
            }
        }
    }
}
