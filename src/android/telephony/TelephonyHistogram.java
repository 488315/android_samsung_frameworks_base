package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

@SystemApi
/* loaded from: classes4.dex */
public final class TelephonyHistogram implements Parcelable {
    private static final int ABSENT = 0;
    public static final Parcelable.Creator<TelephonyHistogram> CREATOR = new Parcelable.Creator<TelephonyHistogram>() { // from class: android.telephony.TelephonyHistogram.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TelephonyHistogram createFromParcel(Parcel parcel) {
            return new TelephonyHistogram(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TelephonyHistogram[] newArray(int i) {
            return new TelephonyHistogram[i];
        }
    };
    private static final int PRESENT = 1;
    private static final int RANGE_CALCULATION_COUNT = 10;
    public static final int TELEPHONY_CATEGORY_RIL = 1;
    private int mAverageTimeMs;
    private final int mBucketCount;
    private final int[] mBucketCounters;
    private final int[] mBucketEndPoints;
    private final int mCategory;
    private final int mId;
    private int[] mInitialTimings;
    private int mMaxTimeMs;
    private int mMinTimeMs;
    private int mSampleCount;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TelephonyHistogram(int i, int i2, int i3) {
        if (i3 <= 1) {
            throw new IllegalArgumentException("Invalid number of buckets");
        }
        this.mCategory = i;
        this.mId = i2;
        this.mMinTimeMs = Integer.MAX_VALUE;
        this.mMaxTimeMs = 0;
        this.mAverageTimeMs = 0;
        this.mSampleCount = 0;
        this.mInitialTimings = new int[10];
        this.mBucketCount = i3;
        this.mBucketEndPoints = new int[i3 - 1];
        this.mBucketCounters = new int[i3];
    }

    public TelephonyHistogram(TelephonyHistogram telephonyHistogram) {
        this.mCategory = telephonyHistogram.getCategory();
        this.mId = telephonyHistogram.getId();
        this.mMinTimeMs = telephonyHistogram.getMinTime();
        this.mMaxTimeMs = telephonyHistogram.getMaxTime();
        this.mAverageTimeMs = telephonyHistogram.getAverageTime();
        this.mSampleCount = telephonyHistogram.getSampleCount();
        this.mInitialTimings = telephonyHistogram.getInitialTimings();
        this.mBucketCount = telephonyHistogram.getBucketCount();
        this.mBucketEndPoints = telephonyHistogram.getBucketEndPoints();
        this.mBucketCounters = telephonyHistogram.getBucketCounters();
    }

    public int getCategory() {
        return this.mCategory;
    }

    public int getId() {
        return this.mId;
    }

    public int getMinTime() {
        return this.mMinTimeMs;
    }

    public int getMaxTime() {
        return this.mMaxTimeMs;
    }

    public int getAverageTime() {
        return this.mAverageTimeMs;
    }

    public int getSampleCount() {
        return this.mSampleCount;
    }

    private int[] getInitialTimings() {
        return this.mInitialTimings;
    }

    public int getBucketCount() {
        return this.mBucketCount;
    }

    public int[] getBucketEndPoints() {
        int i = this.mSampleCount;
        if (i > 1 && i < 10) {
            int[] iArr = new int[this.mBucketCount - 1];
            calculateBucketEndPoints(iArr);
            return iArr;
        }
        return getDeepCopyOfArray(this.mBucketEndPoints);
    }

    public int[] getBucketCounters() {
        int i = this.mSampleCount;
        if (i > 1 && i < 10) {
            int i2 = this.mBucketCount;
            int[] iArr = new int[i2 - 1];
            int[] iArr2 = new int[i2];
            calculateBucketEndPoints(iArr);
            for (int i3 = 0; i3 < this.mSampleCount; i3++) {
                addToBucketCounter(iArr, iArr2, this.mInitialTimings[i3]);
            }
            return iArr2;
        }
        return getDeepCopyOfArray(this.mBucketCounters);
    }

    private int[] getDeepCopyOfArray(int[] iArr) {
        int[] iArr2 = new int[iArr.length];
        System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
        return iArr2;
    }

    private void addToBucketCounter(int[] iArr, int[] iArr2, int i) {
        int i2 = 0;
        while (i2 < iArr.length) {
            if (i <= iArr[i2]) {
                iArr2[i2] = iArr2[i2] + 1;
                return;
            }
            i2++;
        }
        iArr2[i2] = iArr2[i2] + 1;
    }

    private void calculateBucketEndPoints(int[] iArr) {
        int i = 1;
        while (true) {
            int i2 = this.mBucketCount;
            if (i >= i2) {
                return;
            }
            int i3 = this.mMinTimeMs;
            iArr[i - 1] = i3 + (((this.mMaxTimeMs - i3) * i) / i2);
            i++;
        }
    }

    public void addTimeTaken(int i) {
        int i2 = this.mSampleCount;
        if (i2 == 0 || i2 == Integer.MAX_VALUE) {
            if (i2 == 0) {
                this.mMinTimeMs = i;
                this.mMaxTimeMs = i;
                this.mAverageTimeMs = i;
            } else {
                this.mInitialTimings = new int[10];
            }
            this.mSampleCount = 1;
            Arrays.fill(this.mInitialTimings, 0);
            this.mInitialTimings[0] = i;
            Arrays.fill(this.mBucketEndPoints, 0);
            Arrays.fill(this.mBucketCounters, 0);
            return;
        }
        if (i < this.mMinTimeMs) {
            this.mMinTimeMs = i;
        }
        if (i > this.mMaxTimeMs) {
            this.mMaxTimeMs = i;
        }
        int i3 = i2 + 1;
        this.mSampleCount = i3;
        this.mAverageTimeMs = (int) (((this.mAverageTimeMs * i2) + i) / i3);
        if (i3 < 10) {
            this.mInitialTimings[i2] = i;
            return;
        }
        if (i3 == 10) {
            this.mInitialTimings[i2] = i;
            calculateBucketEndPoints(this.mBucketEndPoints);
            for (int i4 = 0; i4 < 10; i4++) {
                addToBucketCounter(this.mBucketEndPoints, this.mBucketCounters, this.mInitialTimings[i4]);
            }
            this.mInitialTimings = null;
            return;
        }
        addToBucketCounter(this.mBucketEndPoints, this.mBucketCounters, i);
    }

    public String toString() {
        String str = " Histogram id = " + this.mId + " Time(ms): min = " + this.mMinTimeMs + " max = " + this.mMaxTimeMs + " avg = " + this.mAverageTimeMs + " Count = " + this.mSampleCount;
        if (this.mSampleCount < 10) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(" Interval Endpoints:");
        for (int i = 0; i < this.mBucketEndPoints.length; i++) {
            stringBuffer.append(" " + this.mBucketEndPoints[i]);
        }
        stringBuffer.append(" Interval counters:");
        for (int i2 = 0; i2 < this.mBucketCounters.length; i2++) {
            stringBuffer.append(" " + this.mBucketCounters[i2]);
        }
        return str + ((Object) stringBuffer);
    }

    public TelephonyHistogram(Parcel parcel) {
        this.mCategory = parcel.readInt();
        this.mId = parcel.readInt();
        this.mMinTimeMs = parcel.readInt();
        this.mMaxTimeMs = parcel.readInt();
        this.mAverageTimeMs = parcel.readInt();
        this.mSampleCount = parcel.readInt();
        if (parcel.readInt() == 1) {
            int[] iArr = new int[10];
            this.mInitialTimings = iArr;
            parcel.readIntArray(iArr);
        }
        int i = parcel.readInt();
        this.mBucketCount = i;
        int[] iArr2 = new int[i - 1];
        this.mBucketEndPoints = iArr2;
        parcel.readIntArray(iArr2);
        int[] iArr3 = new int[i];
        this.mBucketCounters = iArr3;
        parcel.readIntArray(iArr3);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCategory);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mMinTimeMs);
        parcel.writeInt(this.mMaxTimeMs);
        parcel.writeInt(this.mAverageTimeMs);
        parcel.writeInt(this.mSampleCount);
        if (this.mInitialTimings == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeIntArray(this.mInitialTimings);
        }
        parcel.writeInt(this.mBucketCount);
        parcel.writeIntArray(this.mBucketEndPoints);
        parcel.writeIntArray(this.mBucketCounters);
    }
}
