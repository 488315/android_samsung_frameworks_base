package com.android.server.display;

import android.annotation.SystemApi;
import android.hardware.display.BrightnessChangeEvent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.MathUtils;
import android.util.Slog;
import android.util.Spline;

import com.android.internal.util.Preconditions;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.mars.MARsFreezeStateRecord$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

@SystemApi
public final class AdaptiveBrightnessWeightStats implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public final float[] mBucketBoundaries;
    public final ContinuityStatsCollector mContinuityStatsCollector;
    public final BrightnessWeights[] mStats;
    public final TimeStatsCollector mTimeStatsCollector;

    /* renamed from: com.android.server.display.AdaptiveBrightnessWeightStats$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new AdaptiveBrightnessWeightStats(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new AdaptiveBrightnessWeightStats[i];
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

        public BrightnessWeights copy() {
            return new BrightnessWeights(
                    this.mLux, this.mBrightness, this.mWeight, this.mLastUserBrightnessTime);
        }

        public float getBrightness() {
            return this.mBrightness;
        }

        public long getLastUserBrightnessTime() {
            return this.mLastUserBrightnessTime;
        }

        public float getLux() {
            return this.mLux;
        }

        public float getWeight() {
            return this.mWeight;
        }

        public void readFromParcel(Parcel parcel) {
            this.mLux = parcel.readFloat();
            this.mBrightness = parcel.readFloat();
            this.mWeight = parcel.readFloat();
            this.mLastUserBrightnessTime = parcel.readLong();
        }

        public void set(float f, float f2, float f3) {
            this.mLux = f;
            this.mBrightness = f2;
            this.mWeight = f3;
        }

        public void setBrightness(float f) {
            this.mBrightness = f;
        }

        public void setLastUserBrightnessTime(long j) {
            this.mLastUserBrightnessTime = j;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            float weight = getWeight();
            sb.append(
                    String.format(
                            "%d:%s",
                            Integer.valueOf((int) this.mBrightness),
                            weight < 10.0f
                                    ? String.format("%.1f", Float.valueOf(weight))
                                    : weight < 100.0f
                                            ? String.format("%d", Integer.valueOf((int) weight))
                                            : weight < 1000.0f
                                                    ? String.format(
                                                            "%d", Integer.valueOf((int) weight))
                                                    : String.format(
                                                            "%.1fh",
                                                            Float.valueOf(weight / 3600.0f))));
            return sb.toString();
        }

        public void writeToParcel(Parcel parcel) {
            parcel.writeFloat(this.mLux);
            parcel.writeFloat(this.mBrightness);
            parcel.writeFloat(this.mWeight);
            parcel.writeLong(this.mLastUserBrightnessTime);
        }
    }

    public final class ContinuityStatsCollector {
        public final BrightnessMappingStrategy mBrightnessMapper;
        public final float[] mBucketBoundaries;
        public final WeightStat[] mContinuityStats;
        public final ArrayList mCurrentUserBrightnessStats;
        public final ArrayList mPrevUserBrightnessStats;
        public final TimeStatsCollector mTimeStatsCollector;
        public final BrightnessWeights[] mTotalStats;

        public final class UserBrightnessStat {
            public final float mAdjustment;
            public final float mBrightness;
            public final float mLowerBoundary;
            public final float mLux;
            public float mPostTimeDuration = FullScreenMagnificationGestureHandler.MAX_SCALE;
            public final float mPreTimeDuration;
            public final Spline mSpline;
            public final float mUpperBoundary;

            public UserBrightnessStat(float f, float f2, float f3, Spline spline, float f4) {
                this.mLux = f;
                this.mBrightness = f2;
                this.mSpline = spline;
                this.mAdjustment = f3;
                Parcelable.Creator creator = AdaptiveBrightnessWeightStats.CREATOR;
                this.mLowerBoundary = Math.min(f / 2.5f, f - 10.0f);
                this.mUpperBoundary = Math.max(2.5f * f, f + 10.0f);
                this.mPreTimeDuration = f4;
            }

            public final String toString() {
                float f = this.mBrightness;
                float f2 = this.mAdjustment;
                return String.format(
                        "%5.1f -> %5.1f (%+6.1f) @ [%6.1f < %6.1f < %6.1f]  (%.1fs : %.1fs)",
                        Float.valueOf(f - f2),
                        Float.valueOf(f),
                        Float.valueOf(f2),
                        Float.valueOf(this.mLowerBoundary),
                        Float.valueOf(this.mLux),
                        Float.valueOf(this.mUpperBoundary),
                        Float.valueOf(this.mPreTimeDuration),
                        Float.valueOf(this.mPostTimeDuration));
            }
        }

        public ContinuityStatsCollector(
                float[] fArr,
                BrightnessMappingStrategy brightnessMappingStrategy,
                TimeStatsCollector timeStatsCollector,
                BrightnessWeights[] brightnessWeightsArr) {
            this.mBucketBoundaries = fArr;
            this.mContinuityStats = new WeightStat[fArr.length];
            int i = 0;
            while (true) {
                WeightStat[] weightStatArr = this.mContinuityStats;
                if (i >= weightStatArr.length) {
                    this.mCurrentUserBrightnessStats = new ArrayList();
                    this.mPrevUserBrightnessStats = new ArrayList();
                    this.mBrightnessMapper = brightnessMappingStrategy;
                    this.mTimeStatsCollector = timeStatsCollector;
                    this.mTotalStats = brightnessWeightsArr;
                    return;
                }
                weightStatArr[i] =
                        new WeightStat(
                                FullScreenMagnificationGestureHandler.MAX_SCALE,
                                FullScreenMagnificationGestureHandler.MAX_SCALE);
                i++;
            }
        }

        public final void addUserBrightnessStat(
                float f, float f2, float f3, Spline spline, ArrayList arrayList, boolean z) {
            float f4;
            if (z) {
                int bucketIndex =
                        AdaptiveBrightnessWeightStats.getBucketIndex(f, this.mBucketBoundaries);
                TimeStatsCollector timeStatsCollector = this.mTimeStatsCollector;
                timeStatsCollector.summarize();
                f4 = timeStatsCollector.mTimeStats[bucketIndex].getWeight();
            } else {
                f4 = Float.NaN;
            }
            UserBrightnessStat userBrightnessStat = new UserBrightnessStat(f, f2, f3, spline, f4);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                float f5 = ((UserBrightnessStat) it.next()).mLux;
                if (userBrightnessStat.mLowerBoundary <= f5
                        && f5 <= userBrightnessStat.mUpperBoundary) {
                    it.remove();
                }
            }
            arrayList.add(userBrightnessStat);
            for (int i = 0; i < arrayList.size(); i++) {
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                i, "addUserBrightnessStat: [", "] ");
                m.append(z ? "userInitiated " : "");
                m.append(arrayList.get(i));
                Slog.d("AdaptiveBrightnessWeightStats", m.toString());
            }
        }
    }

    public final class TimeStatsCollector {
        public final BrightnessMappingStrategy mBrightnessMapper;
        public final float[] mBucketBoundaries;
        public final WeightStat[] mTimeStats;
        public final ArrayList mTransientStats = new ArrayList();

        public TimeStatsCollector(
                float[] fArr, BrightnessMappingStrategy brightnessMappingStrategy) {
            this.mBucketBoundaries = fArr;
            this.mTimeStats = new WeightStat[fArr.length];
            int i = 0;
            while (true) {
                WeightStat[] weightStatArr = this.mTimeStats;
                if (i >= weightStatArr.length) {
                    this.mBrightnessMapper = brightnessMappingStrategy;
                    return;
                } else {
                    weightStatArr[i] =
                            new WeightStat(
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    FullScreenMagnificationGestureHandler.MAX_SCALE);
                    i++;
                }
            }
        }

        public final void summarize() {
            for (int i = 0; i < this.mTransientStats.size(); i++) {
                Iterator it = ((ArrayList) this.mTransientStats.get(i)).iterator();
                float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
                float f2 = 0.0f;
                while (it.hasNext()) {
                    WeightStat weightStat = (WeightStat) it.next();
                    f2 += weightStat.getWeight();
                    f += weightStat.getWeight() * weightStat.getBrightness();
                }
                this.mTimeStats[i].set(f / f2, f2);
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            int i = 0;
            while (true) {
                float[] fArr = this.mBucketBoundaries;
                if (i >= fArr.length) {
                    break;
                }
                i =
                        MARsFreezeStateRecord$$ExternalSyntheticOutline0.m(
                                "%9d", new Object[] {Integer.valueOf((int) fArr[i])}, sb, i, 1);
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
                        sb2.append(
                                String.format(
                                        "%9s", PackageManagerShellCommandDataLoader.STDIN_PATH));
                    }
                }
                sb2.append(System.lineSeparator());
            }
            return System.lineSeparator()
                    + ((CharSequence) sb)
                    + System.lineSeparator()
                    + ((CharSequence) sb2);
        }
    }

    public class WeightStat {
        public float mBrightness;
        public float mWeight;

        public WeightStat(float f, float f2) {
            this.mBrightness = f;
            this.mWeight = f2;
        }

        public float getBrightness() {
            return this.mBrightness;
        }

        public float getWeight() {
            return this.mWeight;
        }

        public void set(float f, float f2) {
            this.mBrightness = f;
            this.mWeight = f2;
        }

        public String toString() {
            float f = this.mWeight;
            return String.format(
                    Integer.toString((int) this.mBrightness)
                            + ":"
                            + (f < 10.0f
                                    ? String.format("%.1f", Float.valueOf(f))
                                    : f < 100.0f
                                            ? String.format("%d", Integer.valueOf((int) f))
                                            : f < 1000.0f
                                                    ? String.format("%d", Integer.valueOf((int) f))
                                                    : String.format(
                                                            "%.1fh", Float.valueOf(f / 3600.0f))),
                    new Object[0]);
        }

        public void updateWeight(float f) {
            this.mWeight += f;
        }

        public boolean valid() {
            return (Float.isNaN(this.mBrightness) || Float.isNaN(this.mWeight)) ? false : true;
        }
    }

    /* renamed from: -$$Nest$smgetDistributionRatio, reason: not valid java name */
    public static float m434$$Nest$smgetDistributionRatio(float f, float f2, float f3, float f4) {
        if (Float.compare(f, f3) == 0) {
            return 1.0f;
        }
        if (f2 >= f3 || f3 >= f4) {
            Slog.e(
                    "AdaptiveBrightnessWeightStats",
                    "getDistributionRatio: wrong boundary "
                            + f
                            + ": "
                            + f2
                            + " < "
                            + f3
                            + " < "
                            + f4);
        } else {
            if (f < f3) {
                return (f - f2) / (f3 - f2);
            }
            if (f > f3) {
                return (f4 - f) / (f4 - f3);
            }
        }
        return Float.NaN;
    }

    public AdaptiveBrightnessWeightStats(Parcel parcel) {
        this.mBucketBoundaries = parcel.createFloatArray();
        this.mStats = new BrightnessWeights[parcel.readInt()];
        int i = 0;
        while (true) {
            BrightnessWeights[] brightnessWeightsArr = this.mStats;
            if (i >= brightnessWeightsArr.length) {
                Slog.d(
                        "AdaptiveBrightnessWeightStats",
                        "AdaptiveBrightnessWeightStats (parcel): mBrightnessMapper: null");
                this.mContinuityStatsCollector = null;
                this.mTimeStatsCollector = null;
                return;
            }
            brightnessWeightsArr[i].readFromParcel(parcel);
            i++;
        }
    }

    public AdaptiveBrightnessWeightStats(
            float[] fArr, BrightnessMappingStrategy brightnessMappingStrategy) {
        this(fArr, null, brightnessMappingStrategy);
    }

    public AdaptiveBrightnessWeightStats(
            float[] fArr,
            BrightnessWeights[] brightnessWeightsArr,
            BrightnessMappingStrategy brightnessMappingStrategy) {
        Objects.requireNonNull(fArr);
        Preconditions.checkArrayElementsInRange(
                fArr,
                FullScreenMagnificationGestureHandler.MAX_SCALE,
                Float.MAX_VALUE,
                "bucketBoundaries");
        if (fArr.length < 1) {
            throw new IllegalArgumentException("Bucket boundaries must contain at least 1 value");
        }
        if (fArr.length > 1) {
            float f = fArr[0];
            for (int i = 1; i < fArr.length; i++) {
                Preconditions.checkState(f < fArr[i]);
                f = fArr[i];
            }
        }
        if (brightnessWeightsArr == null) {
            Slog.d(
                    "AdaptiveBrightnessWeightStats",
                    "AdaptiveBrightnessWeightStats: stats is null. default!");
            brightnessWeightsArr = new BrightnessWeights[fArr.length];
            for (int i2 = 0; i2 < fArr.length; i2++) {
                brightnessWeightsArr[i2] =
                        new BrightnessWeights(
                                fArr[i2],
                                brightnessMappingStrategy.convertToNits(
                                        brightnessMappingStrategy.getBrightness(
                                                null, fArr[i2], -1)),
                                60.0f,
                                0L);
            }
        } else {
            if (fArr.length != brightnessWeightsArr.length) {
                throw new IllegalArgumentException(
                        "Bucket boundaries and stats must be of same size.");
            }
            printCurrentStats(
                    "AdaptiveBrightnessWeightStats - orig stats", brightnessWeightsArr, fArr);
            for (int i3 = 0; i3 < fArr.length; i3++) {
                brightnessWeightsArr[i3].setBrightness(
                        brightnessMappingStrategy.convertToNits(
                                brightnessMappingStrategy.getBrightness(null, fArr[i3], -1)));
            }
        }
        this.mBucketBoundaries = fArr;
        this.mStats = brightnessWeightsArr;
        printCurrentStats("AdaptiveBrightnessWeightStats - mStats", brightnessWeightsArr, fArr);
        TimeStatsCollector timeStatsCollector =
                new TimeStatsCollector(fArr, brightnessMappingStrategy);
        this.mTimeStatsCollector = timeStatsCollector;
        this.mContinuityStatsCollector =
                new ContinuityStatsCollector(
                        fArr, brightnessMappingStrategy, timeStatsCollector, brightnessWeightsArr);
        timeStatsCollector.mTransientStats.clear();
        for (int i4 = 0; i4 < timeStatsCollector.mBucketBoundaries.length; i4++) {
            timeStatsCollector.mTransientStats.add(new ArrayList());
        }
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

    public static void printCurrentStats(String str, Object[] objArr, float[] fArr) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        sb.append(str);
        int i = 0;
        int i2 = 0;
        while (i2 < fArr.length) {
            i2 =
                    MARsFreezeStateRecord$$ExternalSyntheticOutline0.m(
                            "%9d", new Object[] {Integer.valueOf((int) fArr[i2])}, sb2, i2, 1);
        }
        int length = objArr.length;
        while (i < length) {
            i =
                    MARsFreezeStateRecord$$ExternalSyntheticOutline0.m(
                            "%9s", new Object[] {objArr[i]}, sb3, i, 1);
        }
        sb.append(System.lineSeparator());
        sb.append((CharSequence) sb2);
        sb.append(System.lineSeparator());
        sb.append((CharSequence) sb3);
        Slog.d("AdaptiveBrightnessWeightStats", sb.toString());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || AdaptiveBrightnessWeightStats.class != obj.getClass()) {
            return false;
        }
        AdaptiveBrightnessWeightStats adaptiveBrightnessWeightStats =
                (AdaptiveBrightnessWeightStats) obj;
        return Arrays.equals(
                        this.mBucketBoundaries, adaptiveBrightnessWeightStats.mBucketBoundaries)
                && Arrays.equals(this.mStats, adaptiveBrightnessWeightStats.mStats);
    }

    public float[] getBucketBoundaries() {
        return this.mBucketBoundaries;
    }

    public WeightStat[] getContinuityCollectorStats() {
        ContinuityStatsCollector continuityStatsCollector = this.mContinuityStatsCollector;
        if (continuityStatsCollector != null) {
            return continuityStatsCollector.mContinuityStats;
        }
        Slog.d("AdaptiveBrightnessWeightStats", "mContinuityStatsCollector is null");
        return null;
    }

    public BrightnessWeights[] getStats() {
        return this.mStats;
    }

    public WeightStat[] getTimeCollectorStats() {
        TimeStatsCollector timeStatsCollector = this.mTimeStatsCollector;
        if (timeStatsCollector != null) {
            return timeStatsCollector.mTimeStats;
        }
        Slog.d("AdaptiveBrightnessWeightStats", "mTimeStatsCollector is null");
        return null;
    }

    public int hashCode() {
        return Arrays.hashCode(this.mStats) + ((Arrays.hashCode(this.mBucketBoundaries) + 31) * 31);
    }

    public void log(
            float f,
            float f2,
            float f3,
            Spline spline,
            BrightnessChangeEvent brightnessChangeEvent,
            Spline spline2,
            boolean z) {
        int i;
        String str;
        float f4 = f2;
        Spline spline3 = spline;
        StringBuilder sb = new StringBuilder();
        if (z) {
            StringBuilder sb2 = new StringBuilder("(");
            if (brightnessChangeEvent != null) {
                str =
                        brightnessChangeEvent.lastBrightness
                                + "->"
                                + brightnessChangeEvent.brightness;
            } else {
                str = "null";
            }
            sb2.append(str);
            sb2.append(")");
            sb.append(sb2.toString());
        }
        StringBuilder sb3 = new StringBuilder("log: l:");
        sb3.append(f);
        sb3.append(" b:");
        sb3.append(f4);
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
        int bucketIndex = getBucketIndex(f, this.mBucketBoundaries);
        if (bucketIndex < 0
                || f4 < FullScreenMagnificationGestureHandler.MAX_SCALE
                || spline3 == null) {
            return;
        }
        TimeStatsCollector timeStatsCollector = this.mTimeStatsCollector;
        float[] fArr = timeStatsCollector.mBucketBoundaries;
        int bucketIndex2 = getBucketIndex(f, fArr);
        float f5 = fArr[bucketIndex2];
        float min = Math.min(f5 / 2.5f, f5 - 10.0f);
        float max = Math.max(2.5f * f5, f5 + 10.0f);
        int constrain = MathUtils.constrain(getBucketIndex(min, fArr), 0, fArr.length - 1);
        int constrain2 = MathUtils.constrain(getBucketIndex(max, fArr), 0, fArr.length - 1);
        if (fArr[constrain] < min && constrain <= fArr.length - 2) {
            constrain++;
        }
        if (fArr[constrain2] > max && constrain2 > 0) {
            constrain2--;
        }
        Slog.d(
                "AdaptiveBrightnessWeightStats",
                "updateTransientStats: "
                        + min
                        + " < "
                        + f
                        + " < "
                        + max
                        + "  b:"
                        + f4
                        + " t:"
                        + f3
                        + "s");
        while (constrain <= constrain2) {
            ArrayList arrayList = (ArrayList) timeStatsCollector.mTransientStats.get(constrain);
            BrightnessMappingStrategy brightnessMappingStrategy =
                    timeStatsCollector.mBrightnessMapper;
            if (brightnessMappingStrategy != null) {
                float f6 = fArr[constrain];
                i = constrain2;
                float convertToNits =
                        brightnessMappingStrategy.convertToNits(
                                brightnessMappingStrategy.getBrightnessForSpline(f6, spline3));
                if (bucketIndex2 == constrain) {
                    Slog.d(
                            "AdaptiveBrightnessWeightStats",
                            "updateTransientStats: b: "
                                    + f4
                                    + " ambientLux:"
                                    + f
                                    + " currentBucketLux:"
                                    + f5
                                    + " mBrightnessMapper: "
                                    + convertToNits);
                }
                float m434$$Nest$smgetDistributionRatio =
                        m434$$Nest$smgetDistributionRatio(f6, min, f5, max);
                int size = arrayList.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        i2 = -1;
                        break;
                    } else if (Float.compare(
                                    convertToNits, ((WeightStat) arrayList.get(i2)).getBrightness())
                            == 0) {
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i2 >= 0) {
                    ((WeightStat) arrayList.get(i2))
                            .updateWeight(m434$$Nest$smgetDistributionRatio * f3);
                } else {
                    arrayList.add(
                            new WeightStat(convertToNits, m434$$Nest$smgetDistributionRatio * f3));
                }
            } else {
                i = constrain2;
            }
            constrain++;
            f4 = f2;
            spline3 = spline;
            constrain2 = i;
        }
        if (!z || brightnessChangeEvent == null) {
            return;
        }
        float f7 = brightnessChangeEvent.brightness;
        float f8 = f7 - brightnessChangeEvent.lastBrightness;
        ContinuityStatsCollector continuityStatsCollector = this.mContinuityStatsCollector;
        continuityStatsCollector.addUserBrightnessStat(
                f, f7, f8, spline2, continuityStatsCollector.mCurrentUserBrightnessStats, true);
        this.mStats[bucketIndex].setLastUserBrightnessTime(System.currentTimeMillis());
    }

    public void setMaxWeight() {
        Slog.d("AdaptiveBrightnessWeightStats", "setMaxWeight");
        for (BrightnessWeights brightnessWeights : this.mStats) {
            brightnessWeights.set(
                    brightnessWeights.getLux(), brightnessWeights.getBrightness(), 28800.0f);
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void summarizeStats() {
        /*
            Method dump skipped, instructions count: 1103
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.AdaptiveBrightnessWeightStats.summarizeStats():void");
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
}
