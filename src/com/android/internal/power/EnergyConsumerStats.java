package com.android.internal.power;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.text.TextUtils;
import android.util.DebugUtils;
import android.util.Slog;
import android.view.Display;
import com.android.internal.os.LongMultiStateCounter;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/* loaded from: classes5.dex */
public class EnergyConsumerStats {
    private static final int INVALID_STATE = -1;
    public static final int NUMBER_STANDARD_POWER_BUCKETS = 10;
    public static final int POWER_BUCKET_BLUETOOTH = 5;
    public static final int POWER_BUCKET_CAMERA = 8;
    public static final int POWER_BUCKET_CPU = 3;
    public static final int POWER_BUCKET_GNSS = 6;
    public static final int POWER_BUCKET_MOBILE_RADIO = 7;
    public static final int POWER_BUCKET_PHONE = 9;
    public static final int POWER_BUCKET_SCREEN_DOZE = 1;
    public static final int POWER_BUCKET_SCREEN_ON = 0;
    public static final int POWER_BUCKET_SCREEN_OTHER = 2;
    public static final int POWER_BUCKET_UNKNOWN = -1;
    public static final int POWER_BUCKET_WIFI = 4;
    private static final String TAG = "MeasuredEnergyStats";
    private final long[] mAccumulatedChargeMicroCoulomb;
    private LongMultiStateCounter[] mAccumulatedMultiStateChargeMicroCoulomb;
    private final Config mConfig;
    private int mState = -1;
    private long mStateChangeTimestampMs;

    @Retention(RetentionPolicy.SOURCE)
    public @interface StandardPowerBucket {
    }

    private static int customBucketToIndex(int i) {
        return i + 10;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int indexToCustomBucket(int i) {
        return i - 10;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidStandardBucket(int i) {
        return i >= 0 && i < 10;
    }

    public static class Config {
        private final String[] mCustomBucketNames;
        private final String[] mStateNames;
        private final boolean[] mSupportedMultiStateBuckets;
        private final boolean[] mSupportedStandardBuckets;

        public Config(boolean[] zArr, String[] strArr, int[] iArr, String[] strArr2) {
            this.mSupportedStandardBuckets = zArr;
            strArr = strArr == null ? new String[0] : strArr;
            this.mCustomBucketNames = strArr;
            this.mSupportedMultiStateBuckets = new boolean[zArr.length + strArr.length];
            for (int i : iArr) {
                if (this.mSupportedStandardBuckets[i]) {
                    this.mSupportedMultiStateBuckets[i] = true;
                }
            }
            this.mStateNames = strArr2 == null ? new String[]{""} : strArr2;
        }

        public boolean isCompatible(Config config) {
            return Arrays.equals(this.mSupportedStandardBuckets, config.mSupportedStandardBuckets) && Arrays.equals(this.mCustomBucketNames, config.mCustomBucketNames) && Arrays.equals(this.mSupportedMultiStateBuckets, config.mSupportedMultiStateBuckets) && Arrays.equals(this.mStateNames, config.mStateNames);
        }

        public static void writeToParcel(Config config, Parcel parcel) {
            int i = 0;
            if (config == null) {
                parcel.writeBoolean(false);
                return;
            }
            parcel.writeBoolean(true);
            parcel.writeInt(config.mSupportedStandardBuckets.length);
            parcel.writeBooleanArray(config.mSupportedStandardBuckets);
            parcel.writeStringArray(config.mCustomBucketNames);
            int i2 = 0;
            for (boolean z : config.mSupportedMultiStateBuckets) {
                if (z) {
                    i2++;
                }
            }
            int[] iArr = new int[i2];
            int i3 = 0;
            while (true) {
                boolean[] zArr = config.mSupportedMultiStateBuckets;
                if (i < zArr.length) {
                    if (zArr[i]) {
                        iArr[i3] = i;
                        i3++;
                    }
                    i++;
                } else {
                    parcel.writeInt(i2);
                    parcel.writeIntArray(iArr);
                    parcel.writeStringArray(config.mStateNames);
                    return;
                }
            }
        }

        public static Config createFromParcel(Parcel parcel) {
            if (!parcel.readBoolean()) {
                return null;
            }
            boolean[] zArr = new boolean[parcel.readInt()];
            parcel.readBooleanArray(zArr);
            String[] stringArray = parcel.readStringArray();
            int[] iArr = new int[parcel.readInt()];
            parcel.readIntArray(iArr);
            return new Config(zArr, stringArray, iArr, parcel.readStringArray());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getNumberOfBuckets() {
            return this.mSupportedStandardBuckets.length + this.mCustomBucketNames.length;
        }

        public boolean isSupportedBucket(int i) {
            return this.mSupportedStandardBuckets[i];
        }

        public String[] getCustomBucketNames() {
            return this.mCustomBucketNames;
        }

        public boolean isSupportedMultiStateBucket(int i) {
            return this.mSupportedMultiStateBuckets[i];
        }

        public String[] getStateNames() {
            return this.mStateNames;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getBucketName(int i) {
            if (EnergyConsumerStats.isValidStandardBucket(i)) {
                return DebugUtils.valueToString(EnergyConsumerStats.class, "POWER_BUCKET_", i);
            }
            int iIndexToCustomBucket = EnergyConsumerStats.indexToCustomBucket(i);
            StringBuilder sb = new StringBuilder("CUSTOM_");
            sb.append(iIndexToCustomBucket);
            if (!TextUtils.isEmpty(this.mCustomBucketNames[iIndexToCustomBucket])) {
                sb.append('(');
                sb.append(this.mCustomBucketNames[iIndexToCustomBucket]);
                sb.append(')');
            }
            return sb.toString();
        }
    }

    public EnergyConsumerStats(Config config) {
        this.mConfig = config;
        this.mAccumulatedChargeMicroCoulomb = new long[config.getNumberOfBuckets()];
        for (int i = 0; i < 10; i++) {
            if (!this.mConfig.mSupportedStandardBuckets[i]) {
                this.mAccumulatedChargeMicroCoulomb[i] = -1;
            }
        }
    }

    public static EnergyConsumerStats createFromParcel(Config config, Parcel parcel) {
        if (parcel.readBoolean()) {
            return new EnergyConsumerStats(config, parcel);
        }
        return null;
    }

    public EnergyConsumerStats(Config config, Parcel parcel) {
        this.mConfig = config;
        int i = parcel.readInt();
        long[] jArr = new long[i];
        this.mAccumulatedChargeMicroCoulomb = jArr;
        parcel.readLongArray(jArr);
        if (parcel.readBoolean()) {
            this.mAccumulatedMultiStateChargeMicroCoulomb = new LongMultiStateCounter[i];
            for (int i2 = 0; i2 < i; i2++) {
                if (parcel.readBoolean()) {
                    this.mAccumulatedMultiStateChargeMicroCoulomb[i2] = LongMultiStateCounter.CREATOR.createFromParcel(parcel);
                }
            }
            return;
        }
        this.mAccumulatedMultiStateChargeMicroCoulomb = null;
    }

    public void writeToParcel(Parcel parcel) {
        parcel.writeInt(this.mAccumulatedChargeMicroCoulomb.length);
        parcel.writeLongArray(this.mAccumulatedChargeMicroCoulomb);
        if (this.mAccumulatedMultiStateChargeMicroCoulomb != null) {
            parcel.writeBoolean(true);
            for (LongMultiStateCounter longMultiStateCounter : this.mAccumulatedMultiStateChargeMicroCoulomb) {
                if (longMultiStateCounter != null) {
                    parcel.writeBoolean(true);
                    longMultiStateCounter.writeToParcel(parcel, 0);
                } else {
                    parcel.writeBoolean(false);
                }
            }
            return;
        }
        parcel.writeBoolean(false);
    }

    private void readSummaryFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = parcel.readInt();
            long j = parcel.readLong();
            LongMultiStateCounter longMultiStateCounter = null;
            if (parcel.readBoolean()) {
                LongMultiStateCounter longMultiStateCounterCreateFromParcel = LongMultiStateCounter.CREATOR.createFromParcel(parcel);
                if (this.mConfig != null && longMultiStateCounterCreateFromParcel.getStateCount() == this.mConfig.getStateNames().length) {
                    longMultiStateCounter = longMultiStateCounterCreateFromParcel;
                }
            }
            if (i3 < this.mAccumulatedChargeMicroCoulomb.length) {
                setValueIfSupported(i3, j);
                if (longMultiStateCounter != null) {
                    if (this.mAccumulatedMultiStateChargeMicroCoulomb == null) {
                        this.mAccumulatedMultiStateChargeMicroCoulomb = new LongMultiStateCounter[this.mAccumulatedChargeMicroCoulomb.length];
                    }
                    this.mAccumulatedMultiStateChargeMicroCoulomb[i3] = longMultiStateCounter;
                }
            }
        }
    }

    private void writeSummaryToParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        int i = 0;
        int i2 = 0;
        while (true) {
            long[] jArr = this.mAccumulatedChargeMicroCoulomb;
            if (i < jArr.length) {
                long j = jArr[i];
                if (j > 0) {
                    parcel.writeInt(i);
                    parcel.writeLong(j);
                    LongMultiStateCounter[] longMultiStateCounterArr = this.mAccumulatedMultiStateChargeMicroCoulomb;
                    if (longMultiStateCounterArr != null && longMultiStateCounterArr[i] != null) {
                        parcel.writeBoolean(true);
                        this.mAccumulatedMultiStateChargeMicroCoulomb[i].writeToParcel(parcel, 0);
                    } else {
                        parcel.writeBoolean(false);
                    }
                    i2++;
                }
                i++;
            } else {
                int iDataPosition2 = parcel.dataPosition();
                parcel.setDataPosition(iDataPosition);
                parcel.writeInt(i2);
                parcel.setDataPosition(iDataPosition2);
                return;
            }
        }
    }

    public void updateStandardBucket(int i, long j) {
        updateStandardBucket(i, j, 0L);
    }

    public void updateStandardBucket(int i, long j, long j2) {
        checkValidStandardBucket(i);
        updateEntry(i, j, j2);
    }

    public void updateCustomBucket(int i, long j) {
        updateCustomBucket(i, j, 0L);
    }

    public void updateCustomBucket(int i, long j, long j2) {
        if (!isValidCustomBucket(i)) {
            Slog.e(TAG, "Attempted to update invalid custom bucket " + i);
            return;
        }
        updateEntry(customBucketToIndex(i), j, j2);
    }

    private void updateEntry(int i, long j, long j2) {
        long[] jArr = this.mAccumulatedChargeMicroCoulomb;
        long j3 = jArr[i];
        if (j3 >= 0) {
            jArr[i] = j3 + j;
            if (this.mState == -1 || !this.mConfig.isSupportedMultiStateBucket(i)) {
                return;
            }
            if (this.mAccumulatedMultiStateChargeMicroCoulomb == null) {
                this.mAccumulatedMultiStateChargeMicroCoulomb = new LongMultiStateCounter[this.mAccumulatedChargeMicroCoulomb.length];
            }
            LongMultiStateCounter longMultiStateCounter = this.mAccumulatedMultiStateChargeMicroCoulomb[i];
            if (longMultiStateCounter == null) {
                longMultiStateCounter = new LongMultiStateCounter(this.mConfig.mStateNames.length);
                this.mAccumulatedMultiStateChargeMicroCoulomb[i] = longMultiStateCounter;
                longMultiStateCounter.setState(this.mState, this.mStateChangeTimestampMs);
                longMultiStateCounter.updateValue(0L, this.mStateChangeTimestampMs);
            }
            longMultiStateCounter.updateValue(this.mAccumulatedChargeMicroCoulomb[i], j2);
            return;
        }
        Slog.wtf(TAG, "Attempting to add " + j + " to unavailable bucket " + this.mConfig.getBucketName(i) + " whose value was " + this.mAccumulatedChargeMicroCoulomb[i]);
    }

    public void setState(int i, long j) {
        this.mState = i;
        this.mStateChangeTimestampMs = j;
        if (this.mAccumulatedMultiStateChargeMicroCoulomb == null) {
            this.mAccumulatedMultiStateChargeMicroCoulomb = new LongMultiStateCounter[this.mAccumulatedChargeMicroCoulomb.length];
        }
        int i2 = 0;
        while (true) {
            LongMultiStateCounter[] longMultiStateCounterArr = this.mAccumulatedMultiStateChargeMicroCoulomb;
            if (i2 >= longMultiStateCounterArr.length) {
                return;
            }
            LongMultiStateCounter longMultiStateCounter = longMultiStateCounterArr[i2];
            if (longMultiStateCounter == null && this.mConfig.isSupportedMultiStateBucket(i2)) {
                longMultiStateCounter = new LongMultiStateCounter(this.mConfig.mStateNames.length);
                longMultiStateCounter.updateValue(0L, j);
                this.mAccumulatedMultiStateChargeMicroCoulomb[i2] = longMultiStateCounter;
            }
            if (longMultiStateCounter != null) {
                longMultiStateCounter.setState(i, j);
            }
            i2++;
        }
    }

    public long getAccumulatedStandardBucketCharge(int i) {
        checkValidStandardBucket(i);
        return this.mAccumulatedChargeMicroCoulomb[i];
    }

    public long getAccumulatedStandardBucketCharge(int i, int i2) {
        LongMultiStateCounter longMultiStateCounter;
        if (!this.mConfig.isSupportedMultiStateBucket(i)) {
            return -1L;
        }
        LongMultiStateCounter[] longMultiStateCounterArr = this.mAccumulatedMultiStateChargeMicroCoulomb;
        if (longMultiStateCounterArr == null || (longMultiStateCounter = longMultiStateCounterArr[i]) == null) {
            return 0L;
        }
        return longMultiStateCounter.getCount(i2);
    }

    public long getAccumulatedCustomBucketCharge(int i) {
        if (isValidCustomBucket(i)) {
            return this.mAccumulatedChargeMicroCoulomb[customBucketToIndex(i)];
        }
        return -1L;
    }

    public long[] getAccumulatedCustomBucketCharges() {
        int numberCustomPowerBuckets = getNumberCustomPowerBuckets();
        long[] jArr = new long[numberCustomPowerBuckets];
        for (int i = 0; i < numberCustomPowerBuckets; i++) {
            jArr[i] = this.mAccumulatedChargeMicroCoulomb[customBucketToIndex(i)];
        }
        return jArr;
    }

    public static int getDisplayPowerBucket(int i) {
        if (Display.isOnState(i)) {
            return 0;
        }
        return Display.isDozeState(i) ? 1 : 2;
    }

    public static EnergyConsumerStats createAndReadSummaryFromParcel(Config config, Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            return null;
        }
        if (config == null) {
            new EnergyConsumerStats(new Config(new boolean[i], null, new int[0], new String[]{""})).readSummaryFromParcel(parcel);
            return null;
        }
        if (i != config.getNumberOfBuckets()) {
            Slog.wtf(TAG, "Size of MeasuredEnergyStats parcel (" + i + ") does not match config (" + config.getNumberOfBuckets() + ").");
            new EnergyConsumerStats(config).readSummaryFromParcel(parcel);
            return null;
        }
        EnergyConsumerStats energyConsumerStats = new EnergyConsumerStats(config);
        energyConsumerStats.readSummaryFromParcel(parcel);
        if (energyConsumerStats.containsInterestingData()) {
            return energyConsumerStats;
        }
        return null;
    }

    private boolean containsInterestingData() {
        int i = 0;
        while (true) {
            long[] jArr = this.mAccumulatedChargeMicroCoulomb;
            if (i >= jArr.length) {
                return false;
            }
            if (jArr[i] > 0) {
                return true;
            }
            i++;
        }
    }

    public static void writeSummaryToParcel(EnergyConsumerStats energyConsumerStats, Parcel parcel) {
        if (energyConsumerStats == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(energyConsumerStats.mConfig.getNumberOfBuckets());
            energyConsumerStats.writeSummaryToParcel(parcel);
        }
    }

    private void reset() {
        LongMultiStateCounter longMultiStateCounter;
        int numberOfBuckets = this.mConfig.getNumberOfBuckets();
        for (int i = 0; i < numberOfBuckets; i++) {
            setValueIfSupported(i, 0L);
            LongMultiStateCounter[] longMultiStateCounterArr = this.mAccumulatedMultiStateChargeMicroCoulomb;
            if (longMultiStateCounterArr != null && (longMultiStateCounter = longMultiStateCounterArr[i]) != null) {
                longMultiStateCounter.reset();
            }
        }
    }

    public static void resetIfNotNull(EnergyConsumerStats energyConsumerStats) {
        if (energyConsumerStats != null) {
            energyConsumerStats.reset();
        }
    }

    private void setValueIfSupported(int i, long j) {
        long[] jArr = this.mAccumulatedChargeMicroCoulomb;
        if (jArr[i] != -1) {
            jArr[i] = j;
        }
    }

    public boolean isStandardBucketSupported(int i) {
        checkValidStandardBucket(i);
        return isIndexSupported(i);
    }

    private boolean isIndexSupported(int i) {
        return this.mAccumulatedChargeMicroCoulomb[i] != -1;
    }

    public void dump(PrintWriter printWriter) {
        LongMultiStateCounter longMultiStateCounter;
        printWriter.print("   ");
        for (int i = 0; i < this.mAccumulatedChargeMicroCoulomb.length; i++) {
            printWriter.print(this.mConfig.getBucketName(i));
            printWriter.print(" : ");
            printWriter.print(this.mAccumulatedChargeMicroCoulomb[i]);
            if (!isIndexSupported(i)) {
                printWriter.print(" (unsupported)");
            }
            LongMultiStateCounter[] longMultiStateCounterArr = this.mAccumulatedMultiStateChargeMicroCoulomb;
            if (longMultiStateCounterArr != null && (longMultiStateCounter = longMultiStateCounterArr[i]) != null) {
                printWriter.print(" [");
                for (int i2 = 0; i2 < this.mConfig.mStateNames.length; i2++) {
                    if (i2 != 0) {
                        printWriter.print(" ");
                    }
                    printWriter.print(this.mConfig.mStateNames[i2]);
                    printWriter.print(": ");
                    printWriter.print(longMultiStateCounter.getCount(i2));
                }
                printWriter.print(NavigationBarInflaterView.SIZE_MOD_END);
            }
            if (i != this.mAccumulatedChargeMicroCoulomb.length - 1) {
                printWriter.print(", ");
            }
        }
        printWriter.println();
    }

    public int getNumberCustomPowerBuckets() {
        return this.mAccumulatedChargeMicroCoulomb.length - 10;
    }

    private static void checkValidStandardBucket(int i) {
        if (isValidStandardBucket(i)) {
            return;
        }
        throw new IllegalArgumentException("Illegal StandardPowerBucket " + i);
    }

    public boolean isValidCustomBucket(int i) {
        return i >= 0 && customBucketToIndex(i) < this.mAccumulatedChargeMicroCoulomb.length;
    }
}
