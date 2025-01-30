package com.android.server.display;

import android.annotation.SystemApi;
import android.hardware.display.BrightnessChangeEvent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.MathUtils;
import android.util.Slog;
import android.util.Spline;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

@SystemApi
/* loaded from: classes2.dex */
public final class AdaptiveBrightnessWeightStats implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.android.server.display.AdaptiveBrightnessWeightStats.1
        @Override // android.os.Parcelable.Creator
        public AdaptiveBrightnessWeightStats createFromParcel(Parcel parcel) {
            return new AdaptiveBrightnessWeightStats(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public AdaptiveBrightnessWeightStats[] newArray(int i) {
            return new AdaptiveBrightnessWeightStats[i];
        }
    };
    public final BrightnessMappingStrategy mBrightnessMapper;
    public final float[] mBucketBoundaries;
    public final ContinuityStatsCollector mContinuityStatsCollector;
    public BrightnessWeights[] mStats;
    public final TimeStatsCollector mTimeStatsCollector;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AdaptiveBrightnessWeightStats(float[] fArr, BrightnessMappingStrategy brightnessMappingStrategy) {
        this(fArr, null, brightnessMappingStrategy);
    }

    public AdaptiveBrightnessWeightStats(float[] fArr, BrightnessWeights[] brightnessWeightsArr, BrightnessMappingStrategy brightnessMappingStrategy) {
        Objects.requireNonNull(fArr);
        Preconditions.checkArrayElementsInRange(fArr, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, Float.MAX_VALUE, "bucketBoundaries");
        if (fArr.length < 1) {
            throw new IllegalArgumentException("Bucket boundaries must contain at least 1 value");
        }
        checkSorted(fArr);
        int i = 0;
        if (brightnessWeightsArr == null) {
            Slog.d("AdaptiveBrightnessWeightStats", "AdaptiveBrightnessWeightStats: stats is null. default!");
            brightnessWeightsArr = new BrightnessWeights[fArr.length];
            while (i < fArr.length) {
                brightnessWeightsArr[i] = new BrightnessWeights(fArr[i], brightnessMappingStrategy.convertToNits(brightnessMappingStrategy.getBrightness(fArr[i])), 60.0f, 0L);
                i++;
            }
        } else {
            if (fArr.length != brightnessWeightsArr.length) {
                throw new IllegalArgumentException("Bucket boundaries and stats must be of same size.");
            }
            printCurrentStats("AdaptiveBrightnessWeightStats - orig stats", brightnessWeightsArr, fArr);
            while (i < fArr.length) {
                brightnessWeightsArr[i].setBrightness(brightnessMappingStrategy.convertToNits(brightnessMappingStrategy.getBrightness(fArr[i])));
                i++;
            }
        }
        this.mBucketBoundaries = fArr;
        this.mStats = brightnessWeightsArr;
        printCurrentStats("AdaptiveBrightnessWeightStats - mStats", brightnessWeightsArr, fArr);
        this.mBrightnessMapper = brightnessMappingStrategy;
        TimeStatsCollector timeStatsCollector = new TimeStatsCollector(fArr, brightnessMappingStrategy);
        this.mTimeStatsCollector = timeStatsCollector;
        this.mContinuityStatsCollector = new ContinuityStatsCollector(fArr, brightnessMappingStrategy, timeStatsCollector, this.mStats);
        timeStatsCollector.initTransientStats();
    }

    public BrightnessWeights[] getStats() {
        return this.mStats;
    }

    public float[] getBucketBoundaries() {
        return this.mBucketBoundaries;
    }

    public AdaptiveBrightnessWeightStats(Parcel parcel) {
        this.mBucketBoundaries = parcel.createFloatArray();
        this.mStats = new BrightnessWeights[parcel.readInt()];
        int i = 0;
        while (true) {
            BrightnessWeights[] brightnessWeightsArr = this.mStats;
            if (i < brightnessWeightsArr.length) {
                brightnessWeightsArr[i].readFromParcel(parcel);
                i++;
            } else {
                this.mBrightnessMapper = null;
                Slog.d("AdaptiveBrightnessWeightStats", "AdaptiveBrightnessWeightStats (parcel): mBrightnessMapper: " + ((Object) null));
                this.mContinuityStatsCollector = null;
                this.mTimeStatsCollector = null;
                return;
            }
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || AdaptiveBrightnessWeightStats.class != obj.getClass()) {
            return false;
        }
        AdaptiveBrightnessWeightStats adaptiveBrightnessWeightStats = (AdaptiveBrightnessWeightStats) obj;
        return Arrays.equals(this.mBucketBoundaries, adaptiveBrightnessWeightStats.mBucketBoundaries) && Arrays.equals(this.mStats, adaptiveBrightnessWeightStats.mStats);
    }

    public int hashCode() {
        return ((Arrays.hashCode(this.mBucketBoundaries) + 31) * 31) + Arrays.hashCode(this.mStats);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloatArray(this.mBucketBoundaries);
        parcel.writeInt(this.mStats.length);
        int i2 = 0;
        while (true) {
            BrightnessWeights[] brightnessWeightsArr = this.mStats;
            if (i2 >= brightnessWeightsArr.length) {
                return;
            }
            brightnessWeightsArr[i2].writeToParcel(parcel);
            i2++;
        }
    }

    public void log(float f, float f2, float f3, Spline spline, BrightnessChangeEvent brightnessChangeEvent, Spline spline2, boolean z) {
        String str;
        StringBuilder sb = new StringBuilder();
        if (z) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("(");
            if (brightnessChangeEvent != null) {
                str = brightnessChangeEvent.lastBrightness + "->" + brightnessChangeEvent.brightness;
            } else {
                str = "null";
            }
            sb2.append(str);
            sb2.append(")");
            sb.append(sb2.toString());
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("log: l:");
        sb3.append(f);
        sb3.append(" b:");
        sb3.append(f2);
        sb3.append(" t:");
        sb3.append(f3);
        sb3.append(" u:");
        sb3.append(z);
        sb3.append(" ");
        CharSequence charSequence = sb;
        if (!z) {
            charSequence = "";
        }
        sb3.append((Object) charSequence);
        Slog.d("AdaptiveBrightnessWeightStats", sb3.toString());
        int bucketIndex = getBucketIndex(f);
        if (bucketIndex < 0 || f2 < DisplayPowerController2.RATE_FROM_DOZE_TO_ON || spline == null) {
            return;
        }
        this.mTimeStatsCollector.updateTransientStats(f, f2, f3, spline);
        if (!z || brightnessChangeEvent == null) {
            return;
        }
        float f4 = brightnessChangeEvent.brightness;
        this.mContinuityStatsCollector.addUserBrightnessStat(f, f4, f4 - brightnessChangeEvent.lastBrightness, spline2);
        this.mStats[bucketIndex].setLastUserBrightnessTime(System.currentTimeMillis());
    }

    public static float getLowerBoundary(float f) {
        return Math.min(f / 2.5f, f - 10.0f);
    }

    public static float getUpperBoundary(float f) {
        return Math.max(2.5f * f, f + 10.0f);
    }

    public static int findBrightnessIndex(float f, ArrayList arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (Float.compare(f, ((WeightStat) arrayList.get(i)).getBrightness()) == 0) {
                return i;
            }
        }
        return -1;
    }

    public static float getDistributionRatio(float f, float f2, float f3, float f4) {
        if (Float.compare(f, f3) == 0) {
            return 1.0f;
        }
        if (f2 < f3 && f3 < f4) {
            if (f < f3) {
                return (f - f2) / (f3 - f2);
            }
            if (f > f3) {
                return (f4 - f) / (f4 - f3);
            }
            return Float.NaN;
        }
        Slog.e("AdaptiveBrightnessWeightStats", "getDistributionRatio: wrong boundary " + f + ": " + f2 + " < " + f3 + " < " + f4);
        return Float.NaN;
    }

    public static float getBrightnessForSpline(float f, Spline spline, BrightnessMappingStrategy brightnessMappingStrategy) {
        if (brightnessMappingStrategy != null) {
            return brightnessMappingStrategy.convertToNits(brightnessMappingStrategy.getBrightnessForSpline(f, spline));
        }
        return Float.NaN;
    }

    public final int getBucketIndex(float f) {
        return getBucketIndex(f, this.mBucketBoundaries);
    }

    public static int getBucketIndex(float f, float[] fArr) {
        int i = 0;
        if (f < fArr[0] || f > fArr[fArr.length - 1]) {
            return -1;
        }
        int length = fArr.length - 1;
        while (true) {
            if (i >= length) {
                break;
            }
            int i2 = (i + length) / 2;
            float f2 = fArr[i2];
            if (f2 <= f) {
                int i3 = i2 + 1;
                if (f < fArr[i3]) {
                    i = i2;
                    length = i3;
                    break;
                }
            }
            if (f2 < f) {
                i = i2 + 1;
            } else if (f2 > f) {
                length = i2;
            }
        }
        if (f < 10.0f) {
            if (f - fArr[i] <= fArr[length] - f) {
                return i;
            }
        } else if (f / fArr[i] <= fArr[length] / f) {
            return i;
        }
        return length;
    }

    public void summarizeStats() {
        this.mTimeStatsCollector.summarize();
        WeightStat[] stats = this.mTimeStatsCollector.getStats();
        this.mContinuityStatsCollector.summarize();
        WeightStat[] stats2 = this.mContinuityStatsCollector.getStats();
        BrightnessWeights[] brightnessWeightsArr = this.mStats;
        printCurrentStats("summarizeStats(): newTimeStats[]:", stats, this.mBucketBoundaries);
        printCurrentStats("summarizeStats(): newContinuityStats[]:", stats2, this.mBucketBoundaries);
        printCurrentStats("summarizeStats(): lastStats[]:", brightnessWeightsArr, this.mBucketBoundaries);
        for (int i = 0; i < this.mStats.length; i++) {
            float weight = stats2[i].getWeight() + stats[i].getWeight();
            float brightness = Float.compare(weight, DisplayPowerController2.RATE_FROM_DOZE_TO_ON) == 0 ? 0.0f : ((stats2[i].getBrightness() * stats2[i].getWeight()) + (stats[i].getBrightness() * stats[i].getWeight())) / weight;
            float weight2 = brightnessWeightsArr[i].getWeight();
            float brightness2 = brightnessWeightsArr[i].getBrightness();
            float constrain = MathUtils.constrain(brightness, brightness2 - 30.0f, 30.0f + brightness2);
            float f = 28800.0f;
            if (weight <= 28800.0f) {
                float f2 = weight2 + weight;
                if (f2 > 28800.0f) {
                    weight2 = 28800.0f - weight;
                } else {
                    f = f2;
                }
                constrain = Float.compare(f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON) == 0 ? 0.0f : ((brightness2 * weight2) + (constrain * weight)) / f;
            }
            if (Float.compare(f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON) != 0) {
                this.mStats[i].set(this.mBucketBoundaries[i], constrain, f);
            }
        }
        this.mTimeStatsCollector.initTransientStats();
        printCurrentStats("summarizeStats(): mStats:", this.mStats, this.mBucketBoundaries);
    }

    public static void printCurrentStats(String str, Object[] objArr, float[] fArr) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        sb.append(str);
        for (float f : fArr) {
            sb2.append(String.format("%9d", Integer.valueOf((int) f)));
        }
        for (Object obj : objArr) {
            sb3.append(String.format("%9s", obj));
        }
        sb.append(System.lineSeparator());
        sb.append((CharSequence) sb2);
        sb.append(System.lineSeparator());
        sb.append((CharSequence) sb3);
        Slog.d("AdaptiveBrightnessWeightStats", sb.toString());
    }

    public static void checkSorted(float[] fArr) {
        if (fArr.length <= 1) {
            return;
        }
        float f = fArr[0];
        for (int i = 1; i < fArr.length; i++) {
            Preconditions.checkState(f < fArr[i]);
            f = fArr[i];
        }
    }

    public WeightStat[] getTimeCollectorStats() {
        TimeStatsCollector timeStatsCollector = this.mTimeStatsCollector;
        if (timeStatsCollector != null) {
            return timeStatsCollector.getStats();
        }
        Slog.d("AdaptiveBrightnessWeightStats", "mTimeStatsCollector is null");
        return null;
    }

    public WeightStat[] getContinuityCollectorStats() {
        ContinuityStatsCollector continuityStatsCollector = this.mContinuityStatsCollector;
        if (continuityStatsCollector != null) {
            return continuityStatsCollector.getStats();
        }
        Slog.d("AdaptiveBrightnessWeightStats", "mContinuityStatsCollector is null");
        return null;
    }

    public void setMaxWeight() {
        Slog.d("AdaptiveBrightnessWeightStats", "setMaxWeight");
        for (BrightnessWeights brightnessWeights : this.mStats) {
            brightnessWeights.set(brightnessWeights.getLux(), brightnessWeights.getBrightness(), 28800.0f);
        }
    }

    public class BrightnessWeights {
        public float mBrightness;
        public long mLastUserBrightnessTime;
        public float mLux;
        public float mWeight;

        public BrightnessWeights(float f, float f2, float f3, long j) {
            this.mLux = f;
            this.mBrightness = f2;
            this.mWeight = f3;
            this.mLastUserBrightnessTime = j;
        }

        public void set(float f, float f2, float f3) {
            this.mLux = f;
            this.mBrightness = f2;
            this.mWeight = f3;
        }

        public void setLastUserBrightnessTime(long j) {
            this.mLastUserBrightnessTime = j;
        }

        public long getLastUserBrightnessTime() {
            return this.mLastUserBrightnessTime;
        }

        public void readFromParcel(Parcel parcel) {
            this.mLux = parcel.readFloat();
            this.mBrightness = parcel.readFloat();
            this.mWeight = parcel.readFloat();
            this.mLastUserBrightnessTime = parcel.readLong();
        }

        public void writeToParcel(Parcel parcel) {
            parcel.writeFloat(this.mLux);
            parcel.writeFloat(this.mBrightness);
            parcel.writeFloat(this.mWeight);
            parcel.writeLong(this.mLastUserBrightnessTime);
        }

        public float getLux() {
            return this.mLux;
        }

        public void setBrightness(float f) {
            this.mBrightness = f;
        }

        public float getBrightness() {
            return this.mBrightness;
        }

        public float getWeight() {
            return this.mWeight;
        }

        public BrightnessWeights copy() {
            return new BrightnessWeights(this.mLux, this.mBrightness, this.mWeight, this.mLastUserBrightnessTime);
        }

        public String toString() {
            String format;
            StringBuilder sb = new StringBuilder();
            float weight = getWeight();
            if (weight < 10.0f) {
                format = String.format("%.1f", Float.valueOf(weight));
            } else if (weight < 100.0f) {
                format = String.format("%d", Integer.valueOf((int) weight));
            } else if (weight < 1000.0f) {
                format = String.format("%d", Integer.valueOf((int) weight));
            } else {
                format = String.format("%.1fh", Float.valueOf(weight / 3600.0f));
            }
            sb.append(String.format("%d:%s", Integer.valueOf((int) this.mBrightness), format));
            return sb.toString();
        }
    }

    public class WeightStat {
        public float mBrightness;
        public float mWeight;

        public WeightStat(float f, float f2) {
            this.mBrightness = f;
            this.mWeight = f2;
        }

        public void updateWeight(float f) {
            this.mWeight += f;
        }

        public void set(float f, float f2) {
            this.mBrightness = f;
            this.mWeight = f2;
        }

        public float getBrightness() {
            return this.mBrightness;
        }

        public float getWeight() {
            return this.mWeight;
        }

        public boolean valid() {
            return (Float.isNaN(this.mBrightness) || Float.isNaN(this.mWeight)) ? false : true;
        }

        public String toString() {
            String format;
            float f = this.mWeight;
            if (f < 10.0f) {
                format = String.format("%.1f", Float.valueOf(f));
            } else if (f < 100.0f) {
                format = String.format("%d", Integer.valueOf((int) f));
            } else if (f < 1000.0f) {
                format = String.format("%d", Integer.valueOf((int) f));
            } else {
                format = String.format("%.1fh", Float.valueOf(f / 3600.0f));
            }
            return String.format(Integer.toString((int) this.mBrightness) + XmlUtils.STRING_ARRAY_SEPARATOR + format, new Object[0]);
        }
    }

    public class TimeStatsCollector {
        public final BrightnessMappingStrategy mBrightnessMapper;
        public final float[] mBucketBoundaries;
        public final WeightStat[] mTimeStats;
        public final ArrayList mTransientStats = new ArrayList();

        public TimeStatsCollector(float[] fArr, BrightnessMappingStrategy brightnessMappingStrategy) {
            this.mBucketBoundaries = fArr;
            this.mTimeStats = new WeightStat[fArr.length];
            int i = 0;
            while (true) {
                WeightStat[] weightStatArr = this.mTimeStats;
                if (i < weightStatArr.length) {
                    weightStatArr[i] = new WeightStat(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                    i++;
                } else {
                    this.mBrightnessMapper = brightnessMappingStrategy;
                    return;
                }
            }
        }

        public void initTransientStats() {
            this.mTransientStats.clear();
            for (int i = 0; i < this.mBucketBoundaries.length; i++) {
                this.mTransientStats.add(new ArrayList());
            }
        }

        public void updateTransientStats(float f, float f2, float f3, Spline spline) {
            TimeStatsCollector timeStatsCollector = this;
            int bucketIndex = AdaptiveBrightnessWeightStats.getBucketIndex(f, timeStatsCollector.mBucketBoundaries);
            float f4 = timeStatsCollector.mBucketBoundaries[bucketIndex];
            float lowerBoundary = AdaptiveBrightnessWeightStats.getLowerBoundary(f4);
            float upperBoundary = AdaptiveBrightnessWeightStats.getUpperBoundary(f4);
            int constrain = MathUtils.constrain(AdaptiveBrightnessWeightStats.getBucketIndex(lowerBoundary, timeStatsCollector.mBucketBoundaries), 0, timeStatsCollector.mBucketBoundaries.length - 1);
            int constrain2 = MathUtils.constrain(AdaptiveBrightnessWeightStats.getBucketIndex(upperBoundary, timeStatsCollector.mBucketBoundaries), 0, timeStatsCollector.mBucketBoundaries.length - 1);
            float[] fArr = timeStatsCollector.mBucketBoundaries;
            if (fArr[constrain] < lowerBoundary && constrain <= fArr.length - 2) {
                constrain++;
            }
            if (fArr[constrain2] > upperBoundary && constrain2 > 0) {
                constrain2--;
            }
            Slog.d("AdaptiveBrightnessWeightStats", "updateTransientStats: " + lowerBoundary + " < " + f + " < " + upperBoundary + "  b:" + f2 + " t:" + f3 + "s");
            while (constrain <= constrain2) {
                ArrayList arrayList = (ArrayList) timeStatsCollector.mTransientStats.get(constrain);
                BrightnessMappingStrategy brightnessMappingStrategy = timeStatsCollector.mBrightnessMapper;
                if (brightnessMappingStrategy != null) {
                    float f5 = timeStatsCollector.mBucketBoundaries[constrain];
                    float brightnessForSpline = AdaptiveBrightnessWeightStats.getBrightnessForSpline(f5, spline, brightnessMappingStrategy);
                    if (bucketIndex == constrain) {
                        Slog.d("AdaptiveBrightnessWeightStats", "updateTransientStats: b: " + f2 + " ambientLux:" + f + " currentBucketLux:" + f4 + " mBrightnessMapper: " + brightnessForSpline);
                    }
                    float distributionRatio = AdaptiveBrightnessWeightStats.getDistributionRatio(f5, lowerBoundary, f4, upperBoundary);
                    int findBrightnessIndex = AdaptiveBrightnessWeightStats.findBrightnessIndex(brightnessForSpline, arrayList);
                    if (findBrightnessIndex >= 0) {
                        ((WeightStat) arrayList.get(findBrightnessIndex)).updateWeight(distributionRatio * f3);
                    } else {
                        arrayList.add(new WeightStat(brightnessForSpline, distributionRatio * f3));
                    }
                }
                constrain++;
                timeStatsCollector = this;
            }
        }

        public void summarize() {
            for (int i = 0; i < this.mTransientStats.size(); i++) {
                Iterator it = ((ArrayList) this.mTransientStats.get(i)).iterator();
                float f = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                float f2 = 0.0f;
                while (it.hasNext()) {
                    WeightStat weightStat = (WeightStat) it.next();
                    f2 += weightStat.getWeight();
                    f += weightStat.getBrightness() * weightStat.getWeight();
                }
                this.mTimeStats[i].set(f / f2, f2);
            }
        }

        public WeightStat[] getStats() {
            return this.mTimeStats;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            int i = 0;
            while (true) {
                float[] fArr = this.mBucketBoundaries;
                if (i >= fArr.length) {
                    break;
                }
                sb.append(String.format("%9d", Integer.valueOf((int) fArr[i])));
                i++;
            }
            int i2 = 0;
            for (int i3 = 0; i3 < this.mTransientStats.size(); i3++) {
                int size = ((ArrayList) this.mTransientStats.get(i3)).size();
                if (size > i2) {
                    i2 = size;
                }
            }
            for (int i4 = 0; i4 < i2; i4++) {
                for (int i5 = 0; i5 < this.mTransientStats.size(); i5++) {
                    ArrayList arrayList = (ArrayList) this.mTransientStats.get(i5);
                    if (i4 < arrayList.size()) {
                        sb2.append(String.format("%9s", arrayList.get(i4)));
                    } else {
                        sb2.append(String.format("%9s", PackageManagerShellCommandDataLoader.STDIN_PATH));
                    }
                }
                sb2.append(System.lineSeparator());
            }
            return System.lineSeparator() + ((CharSequence) sb) + System.lineSeparator() + ((CharSequence) sb2);
        }
    }

    public class ContinuityStatsCollector {
        public final BrightnessMappingStrategy mBrightnessMapper;
        public final float[] mBucketBoundaries;
        public final WeightStat[] mContinuityStats;
        public final ArrayList mCurrentUserBrightnessStats;
        public final ArrayList mPrevUserBrightnessStats;
        public final TimeStatsCollector mTimeStatsCollector;
        public final BrightnessWeights[] mTotalStats;

        public ContinuityStatsCollector(float[] fArr, BrightnessMappingStrategy brightnessMappingStrategy, TimeStatsCollector timeStatsCollector, BrightnessWeights[] brightnessWeightsArr) {
            this.mBucketBoundaries = fArr;
            this.mContinuityStats = new WeightStat[fArr.length];
            int i = 0;
            while (true) {
                WeightStat[] weightStatArr = this.mContinuityStats;
                if (i < weightStatArr.length) {
                    weightStatArr[i] = new WeightStat(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                    i++;
                } else {
                    this.mCurrentUserBrightnessStats = new ArrayList();
                    this.mPrevUserBrightnessStats = new ArrayList();
                    this.mBrightnessMapper = brightnessMappingStrategy;
                    this.mTimeStatsCollector = timeStatsCollector;
                    this.mTotalStats = brightnessWeightsArr;
                    return;
                }
            }
        }

        public void addUserBrightnessStat(float f, float f2, float f3, Spline spline) {
            addUserBrightnessStat(f, f2, f3, spline, this.mCurrentUserBrightnessStats, true);
        }

        public final void addUserBrightnessStat(float f, float f2, float f3, Spline spline, ArrayList arrayList, boolean z) {
            UserBrightnessStat userBrightnessStat = new UserBrightnessStat(f, f2, f3, spline, z ? getTimeDurationForBucketLux(f) : Float.NaN);
            removeRedundantUserBrightnessStats(userBrightnessStat, arrayList);
            arrayList.add(userBrightnessStat);
            for (int i = 0; i < arrayList.size(); i++) {
                StringBuilder sb = new StringBuilder();
                sb.append("addUserBrightnessStat: [");
                sb.append(i);
                sb.append("] ");
                sb.append(z ? "userInitiated " : "");
                sb.append(arrayList.get(i));
                Slog.d("AdaptiveBrightnessWeightStats", sb.toString());
            }
        }

        public WeightStat[] getStats() {
            return this.mContinuityStats;
        }

        public void summarize() {
            updateContinuityStats();
            updateTimeDurationPrevUserBrightnessStats();
            addCurrentUserBrightnessStatsToPrevious();
        }

        public final void updateContinuityStats() {
            int i = 0;
            int i2 = 0;
            while (true) {
                WeightStat[] weightStatArr = this.mContinuityStats;
                if (i2 >= weightStatArr.length) {
                    break;
                }
                weightStatArr[i2].set(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                i2++;
            }
            WeightStat[] weightStatArr2 = new WeightStat[this.mBucketBoundaries.length];
            Iterator it = this.mCurrentUserBrightnessStats.iterator();
            while (it.hasNext()) {
                UserBrightnessStat userBrightnessStat = (UserBrightnessStat) it.next();
                WeightStat calculateContinuity = calculateContinuity(userBrightnessStat, this.mPrevUserBrightnessStats);
                if (calculateContinuity != null && calculateContinuity.valid()) {
                    distributeToBucketBoundaries(userBrightnessStat, calculateContinuity, weightStatArr2);
                }
            }
            while (true) {
                WeightStat[] weightStatArr3 = this.mContinuityStats;
                if (i >= weightStatArr3.length) {
                    return;
                }
                WeightStat weightStat = weightStatArr2[i];
                if (weightStat != null) {
                    weightStatArr3[i].set(weightStat.getBrightness(), weightStatArr2[i].getWeight());
                }
                i++;
            }
        }

        public final void updateTimeDurationPrevUserBrightnessStats() {
            Iterator it = this.mPrevUserBrightnessStats.iterator();
            while (it.hasNext()) {
                UserBrightnessStat userBrightnessStat = (UserBrightnessStat) it.next();
                float lux = userBrightnessStat.getLux();
                float timeDurationForBucketLux = getTimeDurationForBucketLux(lux);
                userBrightnessStat.updatePostTimeDuration(timeDurationForBucketLux);
                Slog.d("AdaptiveBrightnessWeightStats", "updateTimeDurationPrevUserBrightnessStats: lux: " + lux + " timeDuration: " + timeDurationForBucketLux);
            }
        }

        public final float getTimeDurationForBucketLux(float f) {
            int bucketIndex = AdaptiveBrightnessWeightStats.getBucketIndex(f, this.mBucketBoundaries);
            this.mTimeStatsCollector.summarize();
            return this.mTimeStatsCollector.getStats()[bucketIndex].getWeight();
        }

        public UserBrightnessStat getPrevUserBrightnessStatForContinuity(UserBrightnessStat userBrightnessStat, ArrayList arrayList) {
            float lux = userBrightnessStat.getLux();
            Iterator it = arrayList.iterator();
            UserBrightnessStat userBrightnessStat2 = null;
            float f = Float.MAX_VALUE;
            while (it.hasNext()) {
                UserBrightnessStat userBrightnessStat3 = (UserBrightnessStat) it.next();
                if (userBrightnessStat.isInSameBoundary(userBrightnessStat3)) {
                    if (lux < 10.0f) {
                        return userBrightnessStat3;
                    }
                    float abs = Math.abs((float) Math.log10(lux / userBrightnessStat3.getLux()));
                    if (abs < f) {
                        userBrightnessStat2 = userBrightnessStat3;
                        f = abs;
                    }
                }
            }
            return userBrightnessStat2;
        }

        public final void getElapsedTimeSinceFirstShortTermReset(UserBrightnessStat userBrightnessStat, UserBrightnessStat userBrightnessStat2, ContinuityCalculator continuityCalculator) {
            float lux = userBrightnessStat.getLux();
            float postTimeDuration = userBrightnessStat2.getPostTimeDuration();
            float preTimeDuration = userBrightnessStat.getPreTimeDuration();
            continuityCalculator.mTimeDuration = postTimeDuration + preTimeDuration;
            continuityCalculator.mSb.append(String.format(" cl:%.1f pl:%.1f t(%d + %d)", Float.valueOf(lux), Float.valueOf(userBrightnessStat2.getLux()), Integer.valueOf((int) postTimeDuration), Integer.valueOf((int) preTimeDuration)));
        }

        public final void getSimilarity(UserBrightnessStat userBrightnessStat, UserBrightnessStat userBrightnessStat2, ContinuityCalculator continuityCalculator) {
            float brightness = userBrightnessStat.getBrightness();
            float brightness2 = userBrightnessStat2.getBrightness();
            float adjustment = userBrightnessStat.getAdjustment();
            float adjustment2 = userBrightnessStat2.getAdjustment();
            float abs = Math.abs(brightness - brightness2);
            float f = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
            float f2 = Float.NaN;
            float f3 = (brightness <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON || brightness2 <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) ? Float.NaN : brightness > brightness2 ? brightness2 / brightness : brightness / brightness2;
            if (adjustment > DisplayPowerController2.RATE_FROM_DOZE_TO_ON && adjustment2 > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                f2 = adjustment > adjustment2 ? adjustment2 / adjustment : adjustment / adjustment2;
            }
            boolean z = adjustment * adjustment2 > DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
            if (z && (abs < 50.0f || f3 > 0.6f || f2 > 0.6f)) {
                f = 1.0f;
            }
            continuityCalculator.mSimilarity = f;
            continuityCalculator.mLux = userBrightnessStat.getLux();
            continuityCalculator.mBrightness = brightness;
            continuityCalculator.mSb.append(String.format(" cb:%.1f pb:%.1f (%s,%d,%.2f,%.2f)", Float.valueOf(brightness), Float.valueOf(brightness2), Boolean.valueOf(z), Integer.valueOf((int) abs), Float.valueOf(f3), Float.valueOf(f2)));
        }

        public final WeightStat calculateContinuity(UserBrightnessStat userBrightnessStat, ArrayList arrayList) {
            UserBrightnessStat prevUserBrightnessStatForContinuity = getPrevUserBrightnessStatForContinuity(userBrightnessStat, arrayList);
            if (prevUserBrightnessStatForContinuity == null) {
                return null;
            }
            ContinuityCalculator continuityCalculator = new ContinuityCalculator();
            getElapsedTimeSinceFirstShortTermReset(userBrightnessStat, prevUserBrightnessStatForContinuity, continuityCalculator);
            getSimilarity(userBrightnessStat, prevUserBrightnessStatForContinuity, continuityCalculator);
            continuityCalculator.calculate();
            if (Float.isNaN(continuityCalculator.mContinuity)) {
                return null;
            }
            return new WeightStat(continuityCalculator.mBrightness, continuityCalculator.mContinuity);
        }

        public final void distributeToBucketBoundaries(UserBrightnessStat userBrightnessStat, WeightStat weightStat, WeightStat[] weightStatArr) {
            int bucketIndex = AdaptiveBrightnessWeightStats.getBucketIndex(userBrightnessStat.getLux(), this.mBucketBoundaries);
            float f = this.mBucketBoundaries[bucketIndex];
            float lowerBoundary = AdaptiveBrightnessWeightStats.getLowerBoundary(f);
            float upperBoundary = AdaptiveBrightnessWeightStats.getUpperBoundary(f);
            int constrain = MathUtils.constrain(AdaptiveBrightnessWeightStats.getBucketIndex(lowerBoundary, this.mBucketBoundaries), 0, this.mBucketBoundaries.length - 1);
            int constrain2 = MathUtils.constrain(AdaptiveBrightnessWeightStats.getBucketIndex(upperBoundary, this.mBucketBoundaries), 0, this.mBucketBoundaries.length - 1);
            float[] fArr = this.mBucketBoundaries;
            if (fArr[constrain] < lowerBoundary && constrain <= fArr.length - 2) {
                constrain++;
            }
            if (fArr[constrain2] > upperBoundary && constrain2 > 0) {
                constrain2--;
            }
            while (constrain <= constrain2) {
                if (this.mBrightnessMapper != null) {
                    float f2 = this.mBucketBoundaries[constrain];
                    float brightnessForSpline = AdaptiveBrightnessWeightStats.getBrightnessForSpline(f2, userBrightnessStat.getSpline(), this.mBrightnessMapper);
                    if (bucketIndex == constrain) {
                        Slog.d("AdaptiveBrightnessWeightStats", "distributeToBucketBoundaries: continuityStat: b: " + AdaptiveBrightnessWeightStats.getBrightnessForSpline(f2, userBrightnessStat.getSpline(), this.mBrightnessMapper) + weightStat + " ambientLux:" + userBrightnessStat.getLux() + " currentBucketLux:" + f + " mBrightnessMapper: " + brightnessForSpline);
                    }
                    float distributionRatio = AdaptiveBrightnessWeightStats.getDistributionRatio(f2, lowerBoundary, f, upperBoundary);
                    WeightStat weightStat2 = weightStatArr[constrain];
                    if (weightStat2 != null) {
                        float weight = weightStat2.getWeight() + (weightStat.getWeight() * distributionRatio);
                        weightStatArr[constrain].set(((weightStatArr[constrain].getBrightness() * weightStatArr[constrain].getWeight()) + ((brightnessForSpline * weightStat.getWeight()) * distributionRatio)) / weight, weight);
                    } else {
                        weightStatArr[constrain] = new WeightStat(brightnessForSpline, weightStat.getWeight() * distributionRatio);
                    }
                }
                constrain++;
            }
            AdaptiveBrightnessWeightStats.printCurrentStats("distributeToBucketBoundaries() rawContinuityStats: ", weightStatArr, this.mBucketBoundaries);
        }

        public final void addCurrentUserBrightnessStatsToPrevious() {
            Iterator it = this.mCurrentUserBrightnessStats.iterator();
            while (it.hasNext()) {
                UserBrightnessStat userBrightnessStat = (UserBrightnessStat) it.next();
                addUserBrightnessStat(userBrightnessStat.getLux(), userBrightnessStat.getBrightness(), userBrightnessStat.getAdjustment(), userBrightnessStat.getSpline(), this.mPrevUserBrightnessStats, false);
            }
            for (int i = 0; i < this.mPrevUserBrightnessStats.size(); i++) {
                Slog.d("AdaptiveBrightnessWeightStats", "addCurrentUserBrightnessStatsToPrevious: [" + i + "] " + this.mPrevUserBrightnessStats.get(i));
            }
            this.mCurrentUserBrightnessStats.clear();
        }

        public static void removeRedundantUserBrightnessStats(UserBrightnessStat userBrightnessStat, ArrayList arrayList) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (userBrightnessStat.isInSameBoundary((UserBrightnessStat) it.next())) {
                    it.remove();
                }
            }
        }

        public class ContinuityCalculator {
            public float mBrightness;
            public float mContinuity;
            public float mLux;
            public StringBuilder mSb;
            public float mSimilarity;
            public float mTimeDuration;

            public ContinuityCalculator() {
                this.mTimeDuration = Float.NaN;
                this.mSimilarity = Float.NaN;
                this.mLux = Float.NaN;
                this.mBrightness = Float.NaN;
                this.mContinuity = Float.NaN;
                this.mSb = new StringBuilder();
            }

            public void calculate() {
                if (Float.isNaN(this.mTimeDuration) || Float.isNaN(this.mSimilarity)) {
                    return;
                }
                float f = this.mSimilarity;
                float f2 = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                if (Float.compare(f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON) == 0 || Float.isNaN(this.mLux) || Float.isNaN(this.mBrightness)) {
                    return;
                }
                float f3 = this.mTimeDuration;
                if (DisplayPowerController2.RATE_FROM_DOZE_TO_ON < f3 && f3 < 3600.0f) {
                    f2 = 1.0f + (f3 * (-1.3888889E-4f));
                }
                this.mContinuity = Math.min(((float) Math.sqrt(this.mSimilarity * f2)) * 14400.0f, ContinuityStatsCollector.this.mTotalStats[AdaptiveBrightnessWeightStats.getBucketIndex(this.mLux, ContinuityStatsCollector.this.mBucketBoundaries)].getWeight() * 3.0f);
                Slog.d("AdaptiveBrightnessWeightStats", "ContinuityCalculator.calculate(): mContinuity: " + this.mContinuity + " mLux: " + this.mLux + " mBrightness: " + this.mBrightness + " mSimilarity: " + this.mSimilarity + " timeFactor: " + f2 + this.mSb.toString());
            }
        }

        public class UserBrightnessStat {
            public final float mAdjustment;
            public final float mBrightness;
            public final float mLowerBoundary;
            public final float mLux;
            public float mPostTimeDuration = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
            public final float mPreTimeDuration;
            public final Spline mSpline;
            public final float mUpperBoundary;

            public UserBrightnessStat(float f, float f2, float f3, Spline spline, float f4) {
                this.mLux = f;
                this.mBrightness = f2;
                this.mSpline = spline;
                this.mAdjustment = f3;
                this.mLowerBoundary = AdaptiveBrightnessWeightStats.getLowerBoundary(f);
                this.mUpperBoundary = AdaptiveBrightnessWeightStats.getUpperBoundary(f);
                this.mPreTimeDuration = f4;
            }

            public boolean isInSameBoundary(UserBrightnessStat userBrightnessStat) {
                float f = this.mLowerBoundary;
                float f2 = userBrightnessStat.mLux;
                return f <= f2 && f2 <= this.mUpperBoundary;
            }

            public void updatePostTimeDuration(float f) {
                this.mPostTimeDuration += f;
            }

            public float getLux() {
                return this.mLux;
            }

            public float getBrightness() {
                return this.mBrightness;
            }

            public float getPreTimeDuration() {
                return this.mPreTimeDuration;
            }

            public float getPostTimeDuration() {
                return this.mPostTimeDuration;
            }

            public Spline getSpline() {
                return this.mSpline;
            }

            public float getAdjustment() {
                return this.mAdjustment;
            }

            public String toString() {
                return String.format("%5.1f -> %5.1f (%+6.1f) @ [%6.1f < %6.1f < %6.1f]  (%.1fs : %.1fs)", Float.valueOf(this.mBrightness - this.mAdjustment), Float.valueOf(this.mBrightness), Float.valueOf(this.mAdjustment), Float.valueOf(this.mLowerBoundary), Float.valueOf(this.mLux), Float.valueOf(this.mUpperBoundary), Float.valueOf(this.mPreTimeDuration), Float.valueOf(this.mPostTimeDuration));
            }
        }
    }
}
