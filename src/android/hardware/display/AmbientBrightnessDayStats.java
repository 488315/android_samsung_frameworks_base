package android.hardware.display;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

@SystemApi
/* loaded from: classes2.dex */
public final class AmbientBrightnessDayStats implements Parcelable {
    public static final Parcelable.Creator<AmbientBrightnessDayStats> CREATOR = new Parcelable.Creator<AmbientBrightnessDayStats>() { // from class: android.hardware.display.AmbientBrightnessDayStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AmbientBrightnessDayStats createFromParcel(Parcel parcel) {
            return new AmbientBrightnessDayStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AmbientBrightnessDayStats[] newArray(int i) {
            return new AmbientBrightnessDayStats[i];
        }
    };
    private final float[] mBucketBoundaries;
    private final LocalDate mLocalDate;
    private final float[] mStats;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AmbientBrightnessDayStats(LocalDate localDate, float[] fArr) {
        this(localDate, fArr, null);
    }

    public AmbientBrightnessDayStats(LocalDate localDate, float[] fArr, float[] fArr2) {
        Objects.requireNonNull(localDate);
        Objects.requireNonNull(fArr);
        Preconditions.checkArrayElementsInRange(fArr, 0.0f, Float.MAX_VALUE, "bucketBoundaries");
        if (fArr.length < 1) {
            throw new IllegalArgumentException("Bucket boundaries must contain at least 1 value");
        }
        checkSorted(fArr);
        if (fArr2 == null) {
            fArr2 = new float[fArr.length];
        } else {
            Preconditions.checkArrayElementsInRange(fArr2, 0.0f, Float.MAX_VALUE, Context.STATS_MANAGER);
            if (fArr.length != fArr2.length) {
                throw new IllegalArgumentException("Bucket boundaries and stats must be of same size.");
            }
        }
        this.mLocalDate = localDate;
        this.mBucketBoundaries = fArr;
        this.mStats = fArr2;
    }

    public LocalDate getLocalDate() {
        return this.mLocalDate;
    }

    public float[] getStats() {
        return this.mStats;
    }

    public float[] getBucketBoundaries() {
        return this.mBucketBoundaries;
    }

    private AmbientBrightnessDayStats(Parcel parcel) {
        this.mLocalDate = LocalDate.parse(parcel.readString());
        this.mBucketBoundaries = parcel.createFloatArray();
        this.mStats = parcel.createFloatArray();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AmbientBrightnessDayStats ambientBrightnessDayStats = (AmbientBrightnessDayStats) obj;
        return this.mLocalDate.equals(ambientBrightnessDayStats.mLocalDate) && Arrays.equals(this.mBucketBoundaries, ambientBrightnessDayStats.mBucketBoundaries) && Arrays.equals(this.mStats, ambientBrightnessDayStats.mStats);
    }

    public int hashCode() {
        return ((((this.mLocalDate.hashCode() + 31) * 31) + Arrays.hashCode(this.mBucketBoundaries)) * 31) + Arrays.hashCode(this.mStats);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < this.mBucketBoundaries.length; i++) {
            if (i != 0) {
                sb.append(", ");
                sb2.append(", ");
            }
            sb.append(this.mBucketBoundaries[i]);
            sb2.append(this.mStats[i]);
        }
        return this.mLocalDate + " {" + ((CharSequence) sb) + "} {" + ((CharSequence) sb2) + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mLocalDate.toString());
        parcel.writeFloatArray(this.mBucketBoundaries);
        parcel.writeFloatArray(this.mStats);
    }

    public void log(float f, float f2) {
        int bucketIndex = getBucketIndex(f);
        if (bucketIndex >= 0) {
            float[] fArr = this.mStats;
            fArr[bucketIndex] = fArr[bucketIndex] + f2;
        }
    }

    private int getBucketIndex(float f) {
        int i = 0;
        if (f < this.mBucketBoundaries[0] || Float.isNaN(f)) {
            return -1;
        }
        int length = this.mBucketBoundaries.length - 1;
        while (i < length) {
            int i2 = (i + length) / 2;
            float[] fArr = this.mBucketBoundaries;
            float f2 = fArr[i2];
            if (f2 <= f && f < fArr[i2 + 1]) {
                return i2;
            }
            if (f2 < f) {
                i = i2 + 1;
            } else if (f2 > f) {
                length = i2 - 1;
            }
        }
        return i;
    }

    private static void checkSorted(float[] fArr) {
        if (fArr.length <= 1) {
            return;
        }
        float f = fArr[0];
        for (int i = 1; i < fArr.length; i++) {
            Preconditions.checkState(f < fArr[i]);
            f = fArr[i];
        }
    }
}
