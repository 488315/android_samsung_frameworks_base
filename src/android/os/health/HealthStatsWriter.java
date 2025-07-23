package android.os.health;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.health.HealthKeys;
import android.util.ArrayMap;

/* loaded from: classes3.dex */
public class HealthStatsWriter {
    private final HealthKeys.Constants mConstants;
    private final boolean[] mMeasurementFields;
    private final long[] mMeasurementValues;
    private final ArrayMap<String, Long>[] mMeasurementsValues;
    private final ArrayMap<String, HealthStatsWriter>[] mStatsValues;
    private final int[] mTimerCounts;
    private final boolean[] mTimerFields;
    private final long[] mTimerTimes;
    private final ArrayMap<String, TimerStat>[] mTimersValues;

    public HealthStatsWriter(HealthKeys.Constants constants) {
        this.mConstants = constants;
        int size = constants.getSize(0);
        this.mTimerFields = new boolean[size];
        this.mTimerCounts = new int[size];
        this.mTimerTimes = new long[size];
        int size2 = constants.getSize(1);
        this.mMeasurementFields = new boolean[size2];
        this.mMeasurementValues = new long[size2];
        this.mStatsValues = new ArrayMap[constants.getSize(2)];
        this.mTimersValues = new ArrayMap[constants.getSize(3)];
        this.mMeasurementsValues = new ArrayMap[constants.getSize(4)];
    }

    public void addTimer(int i, int i2, long j) {
        int index = this.mConstants.getIndex(0, i);
        this.mTimerFields[index] = true;
        this.mTimerCounts[index] = i2;
        this.mTimerTimes[index] = j;
    }

    public void addMeasurement(int i, long j) {
        int index = this.mConstants.getIndex(1, i);
        this.mMeasurementFields[index] = true;
        this.mMeasurementValues[index] = j;
    }

    public void addStats(int i, String str, HealthStatsWriter healthStatsWriter) {
        int index = this.mConstants.getIndex(2, i);
        ArrayMap<String, HealthStatsWriter>[] arrayMapArr = this.mStatsValues;
        ArrayMap<String, HealthStatsWriter> arrayMap = arrayMapArr[index];
        if (arrayMap == null) {
            arrayMap = new ArrayMap<>(1);
            arrayMapArr[index] = arrayMap;
        }
        arrayMap.put(str, healthStatsWriter);
    }

    public void addTimers(int i, String str, TimerStat timerStat) {
        int index = this.mConstants.getIndex(3, i);
        ArrayMap<String, TimerStat>[] arrayMapArr = this.mTimersValues;
        ArrayMap<String, TimerStat> arrayMap = arrayMapArr[index];
        if (arrayMap == null) {
            arrayMap = new ArrayMap<>(1);
            arrayMapArr[index] = arrayMap;
        }
        arrayMap.put(str, timerStat);
    }

    public void addMeasurements(int i, String str, long j) {
        int index = this.mConstants.getIndex(4, i);
        ArrayMap<String, Long>[] arrayMapArr = this.mMeasurementsValues;
        ArrayMap<String, Long> arrayMap = arrayMapArr[index];
        if (arrayMap == null) {
            arrayMap = new ArrayMap<>(1);
            arrayMapArr[index] = arrayMap;
        }
        arrayMap.put(str, Long.valueOf(j));
    }

    public void flattenToParcel(Parcel parcel) {
        parcel.writeString(this.mConstants.getDataType());
        parcel.writeInt(countBooleanArray(this.mTimerFields));
        int[] keys = this.mConstants.getKeys(0);
        for (int i = 0; i < keys.length; i++) {
            if (this.mTimerFields[i]) {
                parcel.writeInt(keys[i]);
                parcel.writeInt(this.mTimerCounts[i]);
                parcel.writeLong(this.mTimerTimes[i]);
            }
        }
        parcel.writeInt(countBooleanArray(this.mMeasurementFields));
        int[] keys2 = this.mConstants.getKeys(1);
        for (int i2 = 0; i2 < keys2.length; i2++) {
            if (this.mMeasurementFields[i2]) {
                parcel.writeInt(keys2[i2]);
                parcel.writeLong(this.mMeasurementValues[i2]);
            }
        }
        parcel.writeInt(countObjectArray(this.mStatsValues));
        int[] keys3 = this.mConstants.getKeys(2);
        for (int i3 = 0; i3 < keys3.length; i3++) {
            if (this.mStatsValues[i3] != null) {
                parcel.writeInt(keys3[i3]);
                writeHealthStatsWriterMap(parcel, this.mStatsValues[i3]);
            }
        }
        parcel.writeInt(countObjectArray(this.mTimersValues));
        int[] keys4 = this.mConstants.getKeys(3);
        for (int i4 = 0; i4 < keys4.length; i4++) {
            if (this.mTimersValues[i4] != null) {
                parcel.writeInt(keys4[i4]);
                writeParcelableMap(parcel, this.mTimersValues[i4]);
            }
        }
        parcel.writeInt(countObjectArray(this.mMeasurementsValues));
        int[] keys5 = this.mConstants.getKeys(4);
        for (int i5 = 0; i5 < keys5.length; i5++) {
            if (this.mMeasurementsValues[i5] != null) {
                parcel.writeInt(keys5[i5]);
                writeLongsMap(parcel, this.mMeasurementsValues[i5]);
            }
        }
    }

    private static int countBooleanArray(boolean[] zArr) {
        int i = 0;
        for (boolean z : zArr) {
            if (z) {
                i++;
            }
        }
        return i;
    }

    private static <T> int countObjectArray(T[] tArr) {
        int i = 0;
        for (T t : tArr) {
            if (t != null) {
                i++;
            }
        }
        return i;
    }

    private static void writeHealthStatsWriterMap(Parcel parcel, ArrayMap<String, HealthStatsWriter> arrayMap) {
        int size = arrayMap.size();
        parcel.writeInt(size);
        for (int i = 0; i < size; i++) {
            parcel.writeString(arrayMap.keyAt(i));
            arrayMap.valueAt(i).flattenToParcel(parcel);
        }
    }

    private static <T extends Parcelable> void writeParcelableMap(Parcel parcel, ArrayMap<String, T> arrayMap) {
        int size = arrayMap.size();
        parcel.writeInt(size);
        for (int i = 0; i < size; i++) {
            parcel.writeString(arrayMap.keyAt(i));
            arrayMap.valueAt(i).writeToParcel(parcel, 0);
        }
    }

    private static void writeLongsMap(Parcel parcel, ArrayMap<String, Long> arrayMap) {
        int size = arrayMap.size();
        parcel.writeInt(size);
        for (int i = 0; i < size; i++) {
            parcel.writeString(arrayMap.keyAt(i));
            parcel.writeLong(arrayMap.valueAt(i).longValue());
        }
    }
}
