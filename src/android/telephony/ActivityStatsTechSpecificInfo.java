package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntPredicate;

/* loaded from: classes4.dex */
public final class ActivityStatsTechSpecificInfo implements Parcelable {
    public static final Parcelable.Creator<ActivityStatsTechSpecificInfo> CREATOR = new Parcelable.Creator<ActivityStatsTechSpecificInfo>() { // from class: android.telephony.ActivityStatsTechSpecificInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityStatsTechSpecificInfo createFromParcel(Parcel parcel) {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int[] iArr = new int[5];
            parcel.readIntArray(iArr);
            return new ActivityStatsTechSpecificInfo(readInt, readInt2, iArr, parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityStatsTechSpecificInfo[] newArray(int i) {
            return new ActivityStatsTechSpecificInfo[i];
        }
    };
    private static final int TX_POWER_LEVELS = 5;
    private int mFrequencyRange;
    private int mRat;
    private int mRxTimeMs;
    private int[] mTxTimeMs;

    static /* synthetic */ boolean lambda$isTxPowerEmpty$1(int i) {
        return i == 0;
    }

    static /* synthetic */ boolean lambda$isTxPowerValid$0(int i) {
        return i >= 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ActivityStatsTechSpecificInfo(int i, int i2, int[] iArr, int i3) {
        Objects.requireNonNull(iArr);
        if (iArr.length != 5) {
            throw new IllegalArgumentException("txTimeMs must have length == TX_POWER_LEVELS");
        }
        this.mRat = i;
        this.mFrequencyRange = i2;
        this.mTxTimeMs = iArr;
        this.mRxTimeMs = i3;
    }

    public int getRat() {
        return this.mRat;
    }

    public int getFrequencyRange() {
        return this.mFrequencyRange;
    }

    public long getTransmitTimeMillis(int i) {
        return this.mTxTimeMs[i];
    }

    public int[] getTransmitTimeMillis() {
        return this.mTxTimeMs;
    }

    public long getReceiveTimeMillis() {
        return this.mRxTimeMs;
    }

    public void setRat(int i) {
        this.mRat = i;
    }

    public void setFrequencyRange(int i) {
        this.mFrequencyRange = i;
    }

    public void setReceiveTimeMillis(int i) {
        this.mRxTimeMs = i;
    }

    public void setReceiveTimeMillis(long j) {
        this.mRxTimeMs = (int) j;
    }

    public void setTransmitTimeMillis(int[] iArr) {
        this.mTxTimeMs = Arrays.copyOf(iArr, 5);
    }

    public boolean isTxPowerValid() {
        return Arrays.stream(this.mTxTimeMs).allMatch(new IntPredicate() { // from class: android.telephony.ActivityStatsTechSpecificInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return ActivityStatsTechSpecificInfo.lambda$isTxPowerValid$0(i);
            }
        });
    }

    public boolean isRxPowerValid() {
        return getReceiveTimeMillis() >= 0;
    }

    public boolean isTxPowerEmpty() {
        int[] iArr = this.mTxTimeMs;
        return iArr == null || iArr.length == 0 || Arrays.stream(iArr).allMatch(new IntPredicate() { // from class: android.telephony.ActivityStatsTechSpecificInfo$$ExternalSyntheticLambda1
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return ActivityStatsTechSpecificInfo.lambda$isTxPowerEmpty$1(i);
            }
        });
    }

    public boolean isRxPowerEmpty() {
        return getReceiveTimeMillis() == 0;
    }

    public int hashCode() {
        return (Objects.hash(Integer.valueOf(this.mRat), Integer.valueOf(this.mFrequencyRange), Integer.valueOf(this.mRxTimeMs)) * 31) + Arrays.hashCode(this.mTxTimeMs);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActivityStatsTechSpecificInfo)) {
            return false;
        }
        ActivityStatsTechSpecificInfo activityStatsTechSpecificInfo = (ActivityStatsTechSpecificInfo) obj;
        return this.mRat == activityStatsTechSpecificInfo.mRat && this.mFrequencyRange == activityStatsTechSpecificInfo.mFrequencyRange && Arrays.equals(this.mTxTimeMs, activityStatsTechSpecificInfo.mTxTimeMs) && this.mRxTimeMs == activityStatsTechSpecificInfo.mRxTimeMs;
    }

    private static String ratToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "GERAN";
            case 2:
                return "UTRAN";
            case 3:
                return "EUTRAN";
            case 4:
                return "CDMA2000";
            case 5:
                return "IWLAN";
            case 6:
                return "NGRAN";
            default:
                return Integer.toString(i);
        }
    }

    public String toString() {
        return "{mRat=" + ratToString(this.mRat) + ",mFrequencyRange=" + ServiceState.frequencyRangeToString(this.mFrequencyRange) + ",mTxTimeMs[]=" + Arrays.toString(this.mTxTimeMs) + ",mRxTimeMs=" + this.mRxTimeMs + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRat);
        parcel.writeInt(this.mFrequencyRange);
        parcel.writeIntArray(this.mTxTimeMs);
        parcel.writeInt(this.mRxTimeMs);
    }
}
