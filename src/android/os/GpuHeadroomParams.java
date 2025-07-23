package android.os;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class GpuHeadroomParams {
    public static final int GPU_HEADROOM_CALCULATION_TYPE_AVERAGE = 1;
    public static final int GPU_HEADROOM_CALCULATION_TYPE_MIN = 0;
    public static final int GPU_HEADROOM_CALCULATION_WINDOW_MILLIS_MAX = 10000;
    public static final int GPU_HEADROOM_CALCULATION_WINDOW_MILLIS_MIN = 50;
    public final GpuHeadroomParamsInternal mInternal;

    @Retention(RetentionPolicy.SOURCE)
    public @interface GpuHeadroomCalculationType {
    }

    private GpuHeadroomParams() {
        this.mInternal = new GpuHeadroomParamsInternal();
    }

    public static final class Builder {
        private int mCalculationType;
        private int mCalculationWindowMillis;

        public Builder() {
            this.mCalculationType = -1;
            this.mCalculationWindowMillis = -1;
        }

        public Builder(GpuHeadroomParams gpuHeadroomParams) {
            this.mCalculationType = -1;
            this.mCalculationWindowMillis = -1;
            if (gpuHeadroomParams.mInternal.calculationType >= 0) {
                this.mCalculationType = gpuHeadroomParams.mInternal.calculationType;
            }
            if (gpuHeadroomParams.mInternal.calculationWindowMillis >= 0) {
                this.mCalculationWindowMillis = gpuHeadroomParams.mInternal.calculationWindowMillis;
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

        public GpuHeadroomParams build() {
            GpuHeadroomParams gpuHeadroomParams = new GpuHeadroomParams();
            if (this.mCalculationType >= 0) {
                gpuHeadroomParams.mInternal.calculationType = (byte) this.mCalculationType;
            }
            if (this.mCalculationWindowMillis >= 0) {
                gpuHeadroomParams.mInternal.calculationWindowMillis = this.mCalculationWindowMillis;
            }
            return gpuHeadroomParams;
        }
    }

    public int getCalculationType() {
        byte b = this.mInternal.calculationType;
        if (b == 0 || b == 1) {
            return this.mInternal.calculationType;
        }
        return 0;
    }

    public int getCalculationWindowMillis() {
        return this.mInternal.calculationWindowMillis;
    }

    public String toString() {
        return "GpuHeadroomParams{calculationType=" + ((int) this.mInternal.calculationType) + ", calculationWindowMillis=" + this.mInternal.calculationWindowMillis + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mInternal.equals(((GpuHeadroomParams) obj).mInternal);
    }

    public int hashCode() {
        return this.mInternal.hashCode();
    }
}
