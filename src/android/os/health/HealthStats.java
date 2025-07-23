package android.os.health;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import java.util.Arrays;
import java.util.Map;

/* loaded from: classes3.dex */
public class HealthStats {
    private String mDataType;
    private int[] mMeasurementKeys;
    private long[] mMeasurementValues;
    private int[] mMeasurementsKeys;
    private ArrayMap<String, Long>[] mMeasurementsValues;
    private int[] mStatsKeys;
    private ArrayMap<String, HealthStats>[] mStatsValues;
    private int[] mTimerCounts;
    private int[] mTimerKeys;
    private long[] mTimerTimes;
    private int[] mTimersKeys;
    private ArrayMap<String, TimerStat>[] mTimersValues;

    private HealthStats() {
        throw new RuntimeException("unsupported");
    }

    public HealthStats(Parcel parcel) {
        this.mDataType = parcel.readString();
        int readInt = parcel.readInt();
        this.mTimerKeys = new int[readInt];
        this.mTimerCounts = new int[readInt];
        this.mTimerTimes = new long[readInt];
        for (int i = 0; i < readInt; i++) {
            this.mTimerKeys[i] = parcel.readInt();
            this.mTimerCounts[i] = parcel.readInt();
            this.mTimerTimes[i] = parcel.readLong();
        }
        int readInt2 = parcel.readInt();
        this.mMeasurementKeys = new int[readInt2];
        this.mMeasurementValues = new long[readInt2];
        for (int i2 = 0; i2 < readInt2; i2++) {
            this.mMeasurementKeys[i2] = parcel.readInt();
            this.mMeasurementValues[i2] = parcel.readLong();
        }
        int readInt3 = parcel.readInt();
        this.mStatsKeys = new int[readInt3];
        this.mStatsValues = new ArrayMap[readInt3];
        for (int i3 = 0; i3 < readInt3; i3++) {
            this.mStatsKeys[i3] = parcel.readInt();
            this.mStatsValues[i3] = createHealthStatsMap(parcel);
        }
        int readInt4 = parcel.readInt();
        this.mTimersKeys = new int[readInt4];
        this.mTimersValues = new ArrayMap[readInt4];
        for (int i4 = 0; i4 < readInt4; i4++) {
            this.mTimersKeys[i4] = parcel.readInt();
            this.mTimersValues[i4] = createParcelableMap(parcel, TimerStat.CREATOR);
        }
        int readInt5 = parcel.readInt();
        this.mMeasurementsKeys = new int[readInt5];
        this.mMeasurementsValues = new ArrayMap[readInt5];
        for (int i5 = 0; i5 < readInt5; i5++) {
            this.mMeasurementsKeys[i5] = parcel.readInt();
            this.mMeasurementsValues[i5] = createLongsMap(parcel);
        }
    }

    public String getDataType() {
        return this.mDataType;
    }

    public boolean hasTimer(int i) {
        return getIndex(this.mTimerKeys, i) >= 0;
    }

    public TimerStat getTimer(int i) {
        int index = getIndex(this.mTimerKeys, i);
        if (index < 0) {
            throw new IndexOutOfBoundsException("Bad timer key dataType=" + this.mDataType + " key=" + i);
        }
        return new TimerStat(this.mTimerCounts[index], this.mTimerTimes[index]);
    }

    public int getTimerCount(int i) {
        int index = getIndex(this.mTimerKeys, i);
        if (index < 0) {
            throw new IndexOutOfBoundsException("Bad timer key dataType=" + this.mDataType + " key=" + i);
        }
        return this.mTimerCounts[index];
    }

    public long getTimerTime(int i) {
        int index = getIndex(this.mTimerKeys, i);
        if (index < 0) {
            throw new IndexOutOfBoundsException("Bad timer key dataType=" + this.mDataType + " key=" + i);
        }
        return this.mTimerTimes[index];
    }

    public int getTimerKeyCount() {
        return this.mTimerKeys.length;
    }

    public int getTimerKeyAt(int i) {
        return this.mTimerKeys[i];
    }

    public boolean hasMeasurement(int i) {
        return getIndex(this.mMeasurementKeys, i) >= 0;
    }

    public long getMeasurement(int i) {
        int index = getIndex(this.mMeasurementKeys, i);
        if (index < 0) {
            throw new IndexOutOfBoundsException("Bad measurement key dataType=" + this.mDataType + " key=" + i);
        }
        return this.mMeasurementValues[index];
    }

    public int getMeasurementKeyCount() {
        return this.mMeasurementKeys.length;
    }

    public int getMeasurementKeyAt(int i) {
        return this.mMeasurementKeys[i];
    }

    public boolean hasStats(int i) {
        return getIndex(this.mStatsKeys, i) >= 0;
    }

    public Map<String, HealthStats> getStats(int i) {
        int index = getIndex(this.mStatsKeys, i);
        if (index < 0) {
            throw new IndexOutOfBoundsException("Bad stats key dataType=" + this.mDataType + " key=" + i);
        }
        return this.mStatsValues[index];
    }

    public int getStatsKeyCount() {
        return this.mStatsKeys.length;
    }

    public int getStatsKeyAt(int i) {
        return this.mStatsKeys[i];
    }

    public boolean hasTimers(int i) {
        return getIndex(this.mTimersKeys, i) >= 0;
    }

    public Map<String, TimerStat> getTimers(int i) {
        int index = getIndex(this.mTimersKeys, i);
        if (index < 0) {
            throw new IndexOutOfBoundsException("Bad timers key dataType=" + this.mDataType + " key=" + i);
        }
        return this.mTimersValues[index];
    }

    public int getTimersKeyCount() {
        return this.mTimersKeys.length;
    }

    public int getTimersKeyAt(int i) {
        return this.mTimersKeys[i];
    }

    public boolean hasMeasurements(int i) {
        return getIndex(this.mMeasurementsKeys, i) >= 0;
    }

    public Map<String, Long> getMeasurements(int i) {
        int index = getIndex(this.mMeasurementsKeys, i);
        if (index < 0) {
            throw new IndexOutOfBoundsException("Bad measurements key dataType=" + this.mDataType + " key=" + i);
        }
        return this.mMeasurementsValues[index];
    }

    public int getMeasurementsKeyCount() {
        return this.mMeasurementsKeys.length;
    }

    public int getMeasurementsKeyAt(int i) {
        return this.mMeasurementsKeys[i];
    }

    private static int getIndex(int[] iArr, int i) {
        return Arrays.binarySearch(iArr, i);
    }

    private static ArrayMap<String, HealthStats> createHealthStatsMap(Parcel parcel) {
        int readInt = parcel.readInt();
        ArrayMap<String, HealthStats> arrayMap = new ArrayMap<>(readInt);
        for (int i = 0; i < readInt; i++) {
            arrayMap.put(parcel.readString(), new HealthStats(parcel));
        }
        return arrayMap;
    }

    private static <T extends Parcelable> ArrayMap<String, T> createParcelableMap(Parcel parcel, Parcelable.Creator<T> creator) {
        int readInt = parcel.readInt();
        ArrayMap<String, T> arrayMap = new ArrayMap<>(readInt);
        for (int i = 0; i < readInt; i++) {
            arrayMap.put(parcel.readString(), creator.createFromParcel(parcel));
        }
        return arrayMap;
    }

    private static ArrayMap<String, Long> createLongsMap(Parcel parcel) {
        int readInt = parcel.readInt();
        ArrayMap<String, Long> arrayMap = new ArrayMap<>(readInt);
        for (int i = 0; i < readInt; i++) {
            arrayMap.put(parcel.readString(), Long.valueOf(parcel.readLong()));
        }
        return arrayMap;
    }
}
