package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Range;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class ModemActivityInfo implements Parcelable {
    private static final int TX_POWER_LEVELS = 5;
    public static final int TX_POWER_LEVEL_0 = 0;
    public static final int TX_POWER_LEVEL_1 = 1;
    public static final int TX_POWER_LEVEL_2 = 2;
    public static final int TX_POWER_LEVEL_3 = 3;
    public static final int TX_POWER_LEVEL_4 = 4;
    private ActivityStatsTechSpecificInfo[] mActivityStatsTechSpecificInfo;
    private int mIdleTimeMs;
    private int mSizeOfSpecificInfo;
    private int mSleepTimeMs;
    private long mTimestamp;
    private int mTotalRxTimeMs;
    private int[] mTotalTxTimeMs;
    private static final Range<Integer>[] TX_POWER_RANGES = {new Range<>(Integer.MIN_VALUE, 0), new Range<>(0, 5), new Range<>(5, 15), new Range<>(15, 20), new Range<>(20, Integer.MAX_VALUE)};
    public static final Parcelable.Creator<ModemActivityInfo> CREATOR = new Parcelable.Creator<ModemActivityInfo>() { // from class: android.telephony.ModemActivityInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ModemActivityInfo createFromParcel(Parcel parcel) {
            long readLong = parcel.readLong();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            Parcelable[] parcelableArr = (Parcelable[]) parcel.createTypedArray(ActivityStatsTechSpecificInfo.CREATOR);
            ActivityStatsTechSpecificInfo[] activityStatsTechSpecificInfoArr = new ActivityStatsTechSpecificInfo[parcelableArr.length];
            for (int i = 0; i < parcelableArr.length; i++) {
                activityStatsTechSpecificInfoArr[i] = (ActivityStatsTechSpecificInfo) parcelableArr[i];
            }
            return new ModemActivityInfo(readLong, readInt, readInt2, activityStatsTechSpecificInfoArr);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ModemActivityInfo[] newArray(int i) {
            return new ModemActivityInfo[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface TxPowerLevel {
    }

    public static int getNumTxPowerLevels() {
        return 5;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ModemActivityInfo(long j, int i, int i2, int[] iArr, int i3) {
        Objects.requireNonNull(iArr);
        if (iArr.length != 5) {
            throw new IllegalArgumentException("txTimeMs must have length == TX_POWER_LEVELS");
        }
        this.mTimestamp = j;
        this.mSleepTimeMs = i;
        this.mIdleTimeMs = i2;
        this.mTotalTxTimeMs = iArr;
        this.mTotalRxTimeMs = i3;
        ActivityStatsTechSpecificInfo[] activityStatsTechSpecificInfoArr = {new ActivityStatsTechSpecificInfo(0, 0, iArr, i3)};
        this.mActivityStatsTechSpecificInfo = activityStatsTechSpecificInfoArr;
        this.mSizeOfSpecificInfo = activityStatsTechSpecificInfoArr.length;
    }

    public ModemActivityInfo(long j, long j2, long j3, int[] iArr, long j4) {
        this(j, (int) j2, (int) j3, iArr, (int) j4);
    }

    public ModemActivityInfo(long j, int i, int i2, ActivityStatsTechSpecificInfo[] activityStatsTechSpecificInfoArr) {
        this.mTimestamp = j;
        this.mSleepTimeMs = i;
        this.mIdleTimeMs = i2;
        this.mActivityStatsTechSpecificInfo = activityStatsTechSpecificInfoArr;
        this.mSizeOfSpecificInfo = activityStatsTechSpecificInfoArr.length;
        this.mTotalTxTimeMs = new int[5];
        for (int i3 = 0; i3 < getNumTxPowerLevels(); i3++) {
            for (int i4 = 0; i4 < getSpecificInfoLength(); i4++) {
                int[] iArr = this.mTotalTxTimeMs;
                iArr[i3] = iArr[i3] + ((int) this.mActivityStatsTechSpecificInfo[i4].getTransmitTimeMillis(i3));
            }
        }
        this.mTotalRxTimeMs = 0;
        for (int i5 = 0; i5 < getSpecificInfoLength(); i5++) {
            this.mTotalRxTimeMs += (int) this.mActivityStatsTechSpecificInfo[i5].getReceiveTimeMillis();
        }
    }

    public ModemActivityInfo(long j, long j2, long j3, ActivityStatsTechSpecificInfo[] activityStatsTechSpecificInfoArr) {
        this(j, (int) j2, (int) j3, activityStatsTechSpecificInfoArr);
    }

    public String toString() {
        return "ModemActivityInfo{ mTimestamp=" + this.mTimestamp + " mSleepTimeMs=" + this.mSleepTimeMs + " mIdleTimeMs=" + this.mIdleTimeMs + " mActivityStatsTechSpecificInfo=" + Arrays.toString(this.mActivityStatsTechSpecificInfo) + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mTimestamp);
        parcel.writeInt(this.mSleepTimeMs);
        parcel.writeInt(this.mIdleTimeMs);
        parcel.writeTypedArray(this.mActivityStatsTechSpecificInfo, i);
    }

    public long getTimestampMillis() {
        return this.mTimestamp;
    }

    public void setTimestamp(long j) {
        this.mTimestamp = j;
    }

    public long getTransmitDurationMillisAtPowerLevel(int i) {
        long j = 0;
        for (int i2 = 0; i2 < getSpecificInfoLength(); i2++) {
            j += this.mActivityStatsTechSpecificInfo[i2].getTransmitTimeMillis(i);
        }
        return j;
    }

    public long getTransmitDurationMillisAtPowerLevel(int i, int i2) {
        for (int i3 = 0; i3 < getSpecificInfoLength(); i3++) {
            if (this.mActivityStatsTechSpecificInfo[i3].getRat() == i2) {
                return this.mActivityStatsTechSpecificInfo[i3].getTransmitTimeMillis(i);
            }
        }
        return 0L;
    }

    public long getTransmitDurationMillisAtPowerLevel(int i, int i2, int i3) {
        for (int i4 = 0; i4 < getSpecificInfoLength(); i4++) {
            if (this.mActivityStatsTechSpecificInfo[i4].getRat() == i2 && this.mActivityStatsTechSpecificInfo[i4].getFrequencyRange() == i3) {
                return this.mActivityStatsTechSpecificInfo[i4].getTransmitTimeMillis(i);
            }
        }
        return 0L;
    }

    public Range<Integer> getTransmitPowerRange(int i) {
        return TX_POWER_RANGES[i];
    }

    public int getSpecificInfoRat(int i) {
        return this.mActivityStatsTechSpecificInfo[i].getRat();
    }

    public int getSpecificInfoFrequencyRange(int i) {
        return this.mActivityStatsTechSpecificInfo[i].getFrequencyRange();
    }

    public void setTransmitTimeMillis(int[] iArr) {
        this.mTotalTxTimeMs = Arrays.copyOf(iArr, 5);
    }

    public void setTransmitTimeMillis(int i, int[] iArr) {
        for (int i2 = 0; i2 < getSpecificInfoLength(); i2++) {
            if (this.mActivityStatsTechSpecificInfo[i2].getRat() == i) {
                this.mActivityStatsTechSpecificInfo[i2].setTransmitTimeMillis(iArr);
            }
        }
    }

    public void setTransmitTimeMillis(int i, int i2, int[] iArr) {
        for (int i3 = 0; i3 < getSpecificInfoLength(); i3++) {
            if (this.mActivityStatsTechSpecificInfo[i3].getRat() == i && this.mActivityStatsTechSpecificInfo[i3].getFrequencyRange() == i2) {
                this.mActivityStatsTechSpecificInfo[i3].setTransmitTimeMillis(iArr);
            }
        }
    }

    public int[] getTransmitTimeMillis() {
        return this.mTotalTxTimeMs;
    }

    public int[] getTransmitTimeMillis(int i) {
        int i2 = 0;
        while (true) {
            ActivityStatsTechSpecificInfo[] activityStatsTechSpecificInfoArr = this.mActivityStatsTechSpecificInfo;
            if (i2 < activityStatsTechSpecificInfoArr.length) {
                if (activityStatsTechSpecificInfoArr[i2].getRat() == i) {
                    return this.mActivityStatsTechSpecificInfo[i2].getTransmitTimeMillis();
                }
                i2++;
            } else {
                return new int[5];
            }
        }
    }

    public int[] getTransmitTimeMillis(int i, int i2) {
        int i3 = 0;
        while (true) {
            ActivityStatsTechSpecificInfo[] activityStatsTechSpecificInfoArr = this.mActivityStatsTechSpecificInfo;
            if (i3 < activityStatsTechSpecificInfoArr.length) {
                if (activityStatsTechSpecificInfoArr[i3].getRat() == i && this.mActivityStatsTechSpecificInfo[i3].getFrequencyRange() == i2) {
                    return this.mActivityStatsTechSpecificInfo[i3].getTransmitTimeMillis();
                }
                i3++;
            } else {
                return new int[5];
            }
        }
    }

    public long getSleepTimeMillis() {
        return this.mSleepTimeMs;
    }

    public void setSleepTimeMillis(int i) {
        this.mSleepTimeMs = i;
    }

    public void setSleepTimeMillis(long j) {
        this.mSleepTimeMs = (int) j;
    }

    public ModemActivityInfo getDelta(ModemActivityInfo modemActivityInfo) {
        ActivityStatsTechSpecificInfo[] activityStatsTechSpecificInfoArr = new ActivityStatsTechSpecificInfo[modemActivityInfo.getSpecificInfoLength()];
        for (int i = 0; i < modemActivityInfo.getSpecificInfoLength(); i++) {
            boolean z = false;
            for (int i2 = 0; i2 < getSpecificInfoLength(); i2++) {
                int rat = this.mActivityStatsTechSpecificInfo[i2].getRat();
                if (rat == modemActivityInfo.mActivityStatsTechSpecificInfo[i].getRat() && !z) {
                    if (this.mActivityStatsTechSpecificInfo[i2].getRat() == 6) {
                        if (modemActivityInfo.mActivityStatsTechSpecificInfo[i].getFrequencyRange() == this.mActivityStatsTechSpecificInfo[i2].getFrequencyRange()) {
                            int frequencyRange = this.mActivityStatsTechSpecificInfo[i2].getFrequencyRange();
                            int[] iArr = new int[5];
                            for (int i3 = 0; i3 < 5; i3++) {
                                iArr[i3] = (int) (modemActivityInfo.getTransmitDurationMillisAtPowerLevel(i3, rat, frequencyRange) - getTransmitDurationMillisAtPowerLevel(i3, rat, frequencyRange));
                            }
                            activityStatsTechSpecificInfoArr[i] = new ActivityStatsTechSpecificInfo(rat, frequencyRange, iArr, (int) (modemActivityInfo.getReceiveTimeMillis(rat, frequencyRange) - getReceiveTimeMillis(rat, frequencyRange)));
                        }
                    } else {
                        int[] iArr2 = new int[5];
                        for (int i4 = 0; i4 < 5; i4++) {
                            iArr2[i4] = (int) (modemActivityInfo.getTransmitDurationMillisAtPowerLevel(i4, rat) - getTransmitDurationMillisAtPowerLevel(i4, rat));
                        }
                        activityStatsTechSpecificInfoArr[i] = new ActivityStatsTechSpecificInfo(rat, 0, iArr2, (int) (modemActivityInfo.getReceiveTimeMillis(rat) - getReceiveTimeMillis(rat)));
                    }
                    z = true;
                }
            }
            if (!z) {
                activityStatsTechSpecificInfoArr[i] = modemActivityInfo.mActivityStatsTechSpecificInfo[i];
            }
        }
        return new ModemActivityInfo(modemActivityInfo.getTimestampMillis(), modemActivityInfo.getSleepTimeMillis() - getSleepTimeMillis(), modemActivityInfo.getIdleTimeMillis() - getIdleTimeMillis(), activityStatsTechSpecificInfoArr);
    }

    public long getIdleTimeMillis() {
        return this.mIdleTimeMs;
    }

    public void setIdleTimeMillis(int i) {
        this.mIdleTimeMs = i;
    }

    public void setIdleTimeMillis(long j) {
        this.mIdleTimeMs = (int) j;
    }

    public long getReceiveTimeMillis() {
        return this.mTotalRxTimeMs;
    }

    public long getReceiveTimeMillis(int i) {
        int i2 = 0;
        while (true) {
            ActivityStatsTechSpecificInfo[] activityStatsTechSpecificInfoArr = this.mActivityStatsTechSpecificInfo;
            if (i2 >= activityStatsTechSpecificInfoArr.length) {
                return 0L;
            }
            if (activityStatsTechSpecificInfoArr[i2].getRat() == i) {
                return this.mActivityStatsTechSpecificInfo[i2].getReceiveTimeMillis();
            }
            i2++;
        }
    }

    public long getReceiveTimeMillis(int i, int i2) {
        int i3 = 0;
        while (true) {
            ActivityStatsTechSpecificInfo[] activityStatsTechSpecificInfoArr = this.mActivityStatsTechSpecificInfo;
            if (i3 >= activityStatsTechSpecificInfoArr.length) {
                return 0L;
            }
            if (activityStatsTechSpecificInfoArr[i3].getRat() == i && this.mActivityStatsTechSpecificInfo[i3].getFrequencyRange() == i2) {
                return this.mActivityStatsTechSpecificInfo[i3].getReceiveTimeMillis();
            }
            i3++;
        }
    }

    public void setReceiveTimeMillis(int i) {
        this.mTotalRxTimeMs = i;
    }

    public void setReceiveTimeMillis(long j) {
        this.mTotalRxTimeMs = (int) j;
    }

    public void setReceiveTimeMillis(int i, long j) {
        int i2 = 0;
        while (true) {
            ActivityStatsTechSpecificInfo[] activityStatsTechSpecificInfoArr = this.mActivityStatsTechSpecificInfo;
            if (i2 >= activityStatsTechSpecificInfoArr.length) {
                return;
            }
            if (activityStatsTechSpecificInfoArr[i2].getRat() == i) {
                this.mActivityStatsTechSpecificInfo[i2].setReceiveTimeMillis(j);
            }
            i2++;
        }
    }

    public void setReceiveTimeMillis(int i, int i2, long j) {
        int i3 = 0;
        while (true) {
            ActivityStatsTechSpecificInfo[] activityStatsTechSpecificInfoArr = this.mActivityStatsTechSpecificInfo;
            if (i3 >= activityStatsTechSpecificInfoArr.length) {
                return;
            }
            if (activityStatsTechSpecificInfoArr[i3].getRat() == i && this.mActivityStatsTechSpecificInfo[i3].getFrequencyRange() == i2) {
                this.mActivityStatsTechSpecificInfo[i3].setReceiveTimeMillis(j);
            }
            i3++;
        }
    }

    public int getSpecificInfoLength() {
        return this.mSizeOfSpecificInfo;
    }

    public boolean isValid() {
        if (this.mActivityStatsTechSpecificInfo == null) {
            return false;
        }
        boolean z = true;
        boolean z2 = true;
        for (int i = 0; i < getSpecificInfoLength(); i++) {
            if (!this.mActivityStatsTechSpecificInfo[i].isTxPowerValid()) {
                z = false;
            }
            if (!this.mActivityStatsTechSpecificInfo[i].isRxPowerValid()) {
                z2 = false;
            }
        }
        return z && z2 && getIdleTimeMillis() >= 0 && getSleepTimeMillis() >= 0 && !isEmpty();
    }

    public boolean isEmpty() {
        boolean z = true;
        boolean z2 = true;
        for (int i = 0; i < getSpecificInfoLength(); i++) {
            if (!this.mActivityStatsTechSpecificInfo[i].isTxPowerEmpty()) {
                z = false;
            }
            if (!this.mActivityStatsTechSpecificInfo[i].isRxPowerEmpty()) {
                z2 = false;
            }
        }
        return z && getIdleTimeMillis() == 0 && getSleepTimeMillis() == 0 && z2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ModemActivityInfo modemActivityInfo = (ModemActivityInfo) obj;
            if (this.mTimestamp == modemActivityInfo.mTimestamp && this.mSleepTimeMs == modemActivityInfo.mSleepTimeMs && this.mIdleTimeMs == modemActivityInfo.mIdleTimeMs && this.mSizeOfSpecificInfo == modemActivityInfo.mSizeOfSpecificInfo && Arrays.equals(this.mActivityStatsTechSpecificInfo, modemActivityInfo.mActivityStatsTechSpecificInfo)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (Objects.hash(Long.valueOf(this.mTimestamp), Integer.valueOf(this.mSleepTimeMs), Integer.valueOf(this.mIdleTimeMs), Integer.valueOf(this.mTotalRxTimeMs)) * 31) + Arrays.hashCode(this.mTotalTxTimeMs);
    }
}
