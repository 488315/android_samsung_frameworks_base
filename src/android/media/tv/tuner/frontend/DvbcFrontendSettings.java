package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;
import android.media.tv.tuner.TunerVersionChecker;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public class DvbcFrontendSettings extends FrontendSettings {
    public static final int ANNEX_A = 1;
    public static final int ANNEX_B = 2;
    public static final int ANNEX_C = 4;
    public static final int ANNEX_UNDEFINED = 0;
    public static final int BANDWIDTH_5MHZ = 1;
    public static final int BANDWIDTH_6MHZ = 2;
    public static final int BANDWIDTH_7MHZ = 4;
    public static final int BANDWIDTH_8MHZ = 8;
    public static final int BANDWIDTH_UNDEFINED = 0;
    public static final int MODULATION_AUTO = 1;
    public static final int MODULATION_MOD_128QAM = 16;
    public static final int MODULATION_MOD_16QAM = 2;
    public static final int MODULATION_MOD_256QAM = 32;
    public static final int MODULATION_MOD_32QAM = 4;
    public static final int MODULATION_MOD_64QAM = 8;
    public static final int MODULATION_UNDEFINED = 0;
    public static final int OUTER_FEC_OUTER_FEC_NONE = 1;
    public static final int OUTER_FEC_OUTER_FEC_RS = 2;
    public static final int OUTER_FEC_UNDEFINED = 0;

    @Deprecated
    public static final int SPECTRAL_INVERSION_INVERTED = 2;

    @Deprecated
    public static final int SPECTRAL_INVERSION_NORMAL = 1;

    @Deprecated
    public static final int SPECTRAL_INVERSION_UNDEFINED = 0;
    public static final int TIME_INTERLEAVE_MODE_128_1_0 = 2;
    public static final int TIME_INTERLEAVE_MODE_128_1_1 = 4;
    public static final int TIME_INTERLEAVE_MODE_128_2 = 128;
    public static final int TIME_INTERLEAVE_MODE_128_3 = 256;
    public static final int TIME_INTERLEAVE_MODE_128_4 = 512;
    public static final int TIME_INTERLEAVE_MODE_16_8 = 32;
    public static final int TIME_INTERLEAVE_MODE_32_4 = 16;
    public static final int TIME_INTERLEAVE_MODE_64_2 = 8;
    public static final int TIME_INTERLEAVE_MODE_8_16 = 64;
    public static final int TIME_INTERLEAVE_MODE_AUTO = 1;
    public static final int TIME_INTERLEAVE_MODE_UNDEFINED = 0;
    private final int mAnnex;
    private final int mBandwidth;
    private final long mInnerFec;
    private final int mInterleaveMode;
    private final int mModulation;
    private final int mOuterFec;
    private final int mSpectralInversion;
    private final int mSymbolRate;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Annex {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Bandwidth {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Modulation {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OuterFec {
    }

    @Retention(RetentionPolicy.SOURCE)
    @Deprecated
    public @interface SpectralInversion {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TimeInterleaveMode {
    }

    @Override // android.media.tv.tuner.frontend.FrontendSettings
    public int getType() {
        return 4;
    }

    private DvbcFrontendSettings(long j, int i, long j2, int i2, int i3, int i4, int i5, int i6, int i7) {
        super(j);
        this.mModulation = i;
        this.mInnerFec = j2;
        this.mSymbolRate = i2;
        this.mOuterFec = i3;
        this.mAnnex = i4;
        this.mSpectralInversion = i5;
        this.mInterleaveMode = i6;
        this.mBandwidth = i7;
    }

    public int getModulation() {
        return this.mModulation;
    }

    public long getInnerFec() {
        return this.mInnerFec;
    }

    public int getSymbolRate() {
        return this.mSymbolRate;
    }

    public int getOuterFec() {
        return this.mOuterFec;
    }

    public int getAnnex() {
        return this.mAnnex;
    }

    public int getSpectralInversion() {
        return this.mSpectralInversion;
    }

    public int getTimeInterleaveMode() {
        return this.mInterleaveMode;
    }

    public int getBandwidth() {
        return this.mBandwidth;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int mAnnex;
        private int mBandwidth;
        private long mFrequency;
        private long mInnerFec;
        private int mInterleaveMode;
        private int mModulation;
        private int mOuterFec;
        private int mSpectralInversion;
        private int mSymbolRate;

        private Builder() {
            this.mFrequency = 0L;
            this.mModulation = 0;
            this.mInnerFec = 0L;
            this.mSymbolRate = 0;
            this.mOuterFec = 0;
            this.mAnnex = 0;
            this.mSpectralInversion = 0;
            this.mInterleaveMode = 0;
            this.mBandwidth = 0;
        }

        @Deprecated
        public Builder setFrequency(int i) {
            return setFrequencyLong(i);
        }

        public Builder setFrequencyLong(long j) {
            this.mFrequency = j;
            return this;
        }

        public Builder setModulation(int i) {
            this.mModulation = i;
            return this;
        }

        public Builder setInnerFec(long j) {
            this.mInnerFec = j;
            return this;
        }

        public Builder setSymbolRate(int i) {
            this.mSymbolRate = i;
            return this;
        }

        public Builder setOuterFec(int i) {
            this.mOuterFec = i;
            return this;
        }

        public Builder setAnnex(int i) {
            this.mAnnex = i;
            return this;
        }

        public Builder setSpectralInversion(int i) {
            this.mSpectralInversion = i;
            return this;
        }

        public Builder setTimeInterleaveMode(int i) {
            if (TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "setTimeInterleaveMode")) {
                this.mInterleaveMode = i;
            }
            return this;
        }

        public Builder setBandwidth(int i) {
            if (TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "setBandwidth")) {
                this.mBandwidth = i;
            }
            return this;
        }

        public DvbcFrontendSettings build() {
            return new DvbcFrontendSettings(this.mFrequency, this.mModulation, this.mInnerFec, this.mSymbolRate, this.mOuterFec, this.mAnnex, this.mSpectralInversion, this.mInterleaveMode, this.mBandwidth);
        }
    }
}
