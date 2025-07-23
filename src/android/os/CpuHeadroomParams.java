package android.os;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class CpuHeadroomParams {
    public static final int CPU_HEADROOM_CALCULATION_TYPE_AVERAGE = 1;
    public static final int CPU_HEADROOM_CALCULATION_TYPE_MIN = 0;
    public final CpuHeadroomParamsInternal mInternal;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CpuHeadroomCalculationType {
    }

    private CpuHeadroomParams() {
        this.mInternal = new CpuHeadroomParamsInternal();
    }

    public static final class Builder {
        private int mCalculationType;
        private int mCalculationWindowMillis;
        private int[] mTids;

        public Builder() {
            this.mCalculationType = -1;
            this.mCalculationWindowMillis = -1;
            this.mTids = null;
        }

        public Builder(CpuHeadroomParams cpuHeadroomParams) {
            this.mCalculationType = -1;
            this.mCalculationWindowMillis = -1;
            this.mTids = null;
            if (cpuHeadroomParams.mInternal.calculationType >= 0) {
                this.mCalculationType = cpuHeadroomParams.mInternal.calculationType;
            }
            if (cpuHeadroomParams.mInternal.calculationWindowMillis >= 0) {
                this.mCalculationWindowMillis = cpuHeadroomParams.mInternal.calculationWindowMillis;
            }
            if (cpuHeadroomParams.mInternal.tids != null) {
                this.mTids = Arrays.copyOf(cpuHeadroomParams.mInternal.tids, cpuHeadroomParams.mInternal.tids.length);
            }
        }

        public Builder setCalculationType(int i) {
            if (i == 0 || i == 1) {
                this.mCalculationType = i;
                return this;
            }
            throw new IllegalArgumentException("Invalid calculation type: " + i);
        }

        public Builder setCalculationWindowMillis(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException("Invalid calculation window: " + i);
            }
            this.mCalculationWindowMillis = i;
            return this;
        }

        public Builder setTids(int... iArr) {
            for (int i : iArr) {
                if (i <= 0) {
                    throw new IllegalArgumentException("Invalid TID: " + i);
                }
            }
            this.mTids = iArr;
            return this;
        }

        public CpuHeadroomParams build() {
            CpuHeadroomParams cpuHeadroomParams = new CpuHeadroomParams();
            if (this.mCalculationType >= 0) {
                cpuHeadroomParams.mInternal.calculationType = (byte) this.mCalculationType;
            }
            if (this.mCalculationWindowMillis >= 0) {
                cpuHeadroomParams.mInternal.calculationWindowMillis = this.mCalculationWindowMillis;
            }
            if (this.mTids != null) {
                cpuHeadroomParams.mInternal.tids = this.mTids;
            }
            return cpuHeadroomParams;
        }
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public int getCalculationType() {
        byte b = this.mInternal.calculationType;
        if (b == 0 || b == 1) {
            return this.mInternal.calculationType;
        }
        return 0;
    }

    public long getCalculationWindowMillis() {
        return this.mInternal.calculationWindowMillis;
    }

    public int[] getTids() {
        if (this.mInternal.tids == null) {
            return null;
        }
        return Arrays.copyOf(this.mInternal.tids, this.mInternal.tids.length);
    }

    public String toString() {
        return "CpuHeadroomParams{calculationType=" + ((int) this.mInternal.calculationType) + ", calculationWindowMillis=" + this.mInternal.calculationWindowMillis + ", tids=" + Arrays.toString(this.mInternal.tids) + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mInternal.equals(((CpuHeadroomParams) obj).mInternal);
    }

    public int hashCode() {
        return this.mInternal.hashCode();
    }
}
