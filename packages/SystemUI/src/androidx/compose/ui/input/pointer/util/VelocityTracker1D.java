package androidx.compose.ui.input.pointer.util;

import androidx.compose.ui.internal.InlineClassHelperKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class VelocityTracker1D {
    public int index;
    public final boolean isDataDifferential;
    public final int minSampleSize;
    public final float[] reusableDataPointsArray;
    public final float[] reusableTimeArray;
    public final float[] reusableVelocityCoefficients;
    public final DataPointAtTime[] samples;
    public final Strategy strategy;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class Strategy {
        public static final /* synthetic */ Strategy[] $VALUES;
        public static final Strategy Impulse;
        public static final Strategy Lsq2;

        static {
            Strategy strategy = new Strategy("Lsq2", 0);
            Lsq2 = strategy;
            Strategy strategy2 = new Strategy("Impulse", 1);
            Impulse = strategy2;
            Strategy[] strategyArr = {strategy, strategy2};
            $VALUES = strategyArr;
            EnumEntriesKt.enumEntries(strategyArr);
        }

        private Strategy(String str, int i) {
        }

        public static Strategy valueOf(String str) {
            return (Strategy) Enum.valueOf(Strategy.class, str);
        }

        public static Strategy[] values() {
            return (Strategy[]) $VALUES.clone();
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Strategy.values().length];
            try {
                iArr[Strategy.Impulse.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Strategy.Lsq2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public VelocityTracker1D() {
        this(false, null, 3, 0 == true ? 1 : 0);
    }

    public final void addDataPoint(float f, long j) {
        int i = (this.index + 1) % 20;
        this.index = i;
        DataPointAtTime[] dataPointAtTimeArr = this.samples;
        DataPointAtTime dataPointAtTime = dataPointAtTimeArr[i];
        if (dataPointAtTime == null) {
            dataPointAtTimeArr[i] = new DataPointAtTime(j, f);
        } else {
            dataPointAtTime.time = j;
            dataPointAtTime.dataPoint = f;
        }
    }

    public final float calculateVelocity() {
        Strategy strategy;
        float[] fArr;
        float[] fArr2;
        int i;
        boolean z;
        int i2;
        float f;
        float fSignum;
        int i3 = this.index;
        DataPointAtTime[] dataPointAtTimeArr = this.samples;
        DataPointAtTime dataPointAtTime = dataPointAtTimeArr[i3];
        if (dataPointAtTime == null) {
            return 0.0f;
        }
        int i4 = 0;
        DataPointAtTime dataPointAtTime2 = dataPointAtTime;
        do {
            DataPointAtTime dataPointAtTime3 = dataPointAtTimeArr[i3];
            boolean z2 = this.isDataDifferential;
            strategy = this.strategy;
            fArr = this.reusableDataPointsArray;
            fArr2 = this.reusableTimeArray;
            if (dataPointAtTime3 == null) {
                i = i4;
                z = z2;
                i2 = 1;
                f = 0.0f;
            } else {
                long j = dataPointAtTime.time;
                i = i4;
                f = 0.0f;
                long j2 = dataPointAtTime3.time;
                float f2 = j - j2;
                z = z2;
                i2 = 1;
                float fAbs = Math.abs(j2 - dataPointAtTime2.time);
                dataPointAtTime2 = (strategy == Strategy.Lsq2 || z) ? dataPointAtTime3 : dataPointAtTime;
                if (f2 <= 100.0f && fAbs <= 40.0f) {
                    fArr[i] = dataPointAtTime3.dataPoint;
                    fArr2[i] = -f2;
                    if (i3 == 0) {
                        i3 = 20;
                    }
                    i3--;
                    i4 = i + 1;
                }
            }
            i4 = i;
            break;
        } while (i4 < 20);
        if (i4 < this.minSampleSize) {
            return f;
        }
        int i5 = WhenMappings.$EnumSwitchMapping$0[strategy.ordinal()];
        if (i5 == i2) {
            int i6 = i4 - i2;
            float f3 = fArr2[i6];
            int i7 = i6;
            float f4 = f;
            while (i7 > 0) {
                int i8 = i7 - 1;
                float f5 = fArr2[i8];
                if (f3 != f5) {
                    float f6 = (z ? -fArr[i8] : fArr[i7] - fArr[i8]) / (f3 - f5);
                    float fAbs2 = (Math.abs(f6) * (f6 - (Math.signum(f4) * ((float) Math.sqrt(Math.abs(f4) * 2))))) + f4;
                    if (i7 == i6) {
                        fAbs2 *= 0.5f;
                    }
                    f4 = fAbs2;
                }
                i7--;
                f3 = f5;
            }
            fSignum = Math.signum(f4) * ((float) Math.sqrt(Math.abs(f4) * 2));
        } else {
            if (i5 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            try {
                float[] fArr3 = this.reusableVelocityCoefficients;
                VelocityTrackerKt.polyFitLeastSquares(fArr2, fArr, i4, fArr3);
                fSignum = fArr3[i2];
            } catch (IllegalArgumentException unused) {
                fSignum = f;
            }
        }
        return fSignum * 1000;
    }

    public VelocityTracker1D(boolean z, Strategy strategy) {
        this.isDataDifferential = z;
        this.strategy = strategy;
        if (z && strategy.equals(Strategy.Lsq2)) {
            throw new IllegalStateException("Lsq2 not (yet) supported for differential axes");
        }
        int i = WhenMappings.$EnumSwitchMapping$0[strategy.ordinal()];
        int i2 = 2;
        if (i != 1) {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            i2 = 3;
        }
        this.minSampleSize = i2;
        this.samples = new DataPointAtTime[20];
        this.reusableDataPointsArray = new float[20];
        this.reusableTimeArray = new float[20];
        this.reusableVelocityCoefficients = new float[3];
    }

    public /* synthetic */ VelocityTracker1D(boolean z, Strategy strategy, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? Strategy.Lsq2 : strategy);
    }

    public VelocityTracker1D(boolean z) {
        this(z, Strategy.Impulse);
    }

    public final float calculateVelocity(float f) {
        if (f <= 0.0f) {
            InlineClassHelperKt.throwIllegalStateException("maximumVelocity should be a positive value. You specified=" + f);
        }
        float fCalculateVelocity = calculateVelocity();
        if (fCalculateVelocity == 0.0f || Float.isNaN(fCalculateVelocity)) {
            return 0.0f;
        }
        if (fCalculateVelocity <= 0.0f) {
            float f2 = -f;
            if (fCalculateVelocity < f2) {
                return f2;
            }
        } else if (fCalculateVelocity > f) {
            return f;
        }
        return fCalculateVelocity;
    }
}
