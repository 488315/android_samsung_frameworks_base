package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public class Isdbs3FrontendSettings extends FrontendSettings {
    public static final int CODERATE_1_2 = 8;
    public static final int CODERATE_1_3 = 2;
    public static final int CODERATE_2_3 = 32;
    public static final int CODERATE_2_5 = 4;
    public static final int CODERATE_3_4 = 64;
    public static final int CODERATE_3_5 = 16;
    public static final int CODERATE_4_5 = 256;
    public static final int CODERATE_5_6 = 512;
    public static final int CODERATE_7_8 = 1024;
    public static final int CODERATE_7_9 = 128;
    public static final int CODERATE_9_10 = 2048;
    public static final int CODERATE_AUTO = 1;
    public static final int CODERATE_UNDEFINED = 0;
    public static final int MODULATION_AUTO = 1;
    public static final int MODULATION_MOD_16APSK = 16;
    public static final int MODULATION_MOD_32APSK = 32;
    public static final int MODULATION_MOD_8PSK = 8;
    public static final int MODULATION_MOD_BPSK = 2;
    public static final int MODULATION_MOD_QPSK = 4;
    public static final int MODULATION_UNDEFINED = 0;
    public static final int ROLLOFF_0_03 = 1;
    public static final int ROLLOFF_UNDEFINED = 0;
    private final int mCodeRate;
    private final int mModulation;
    private final int mRolloff;
    private final int mStreamId;
    private final int mStreamIdType;
    private final int mSymbolRate;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CodeRate {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Modulation {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Rolloff {
    }

    @Override // android.media.tv.tuner.frontend.FrontendSettings
    public int getType() {
        return 8;
    }

    private Isdbs3FrontendSettings(long j, int i, int i2, int i3, int i4, int i5, int i6) {
        super(j);
        this.mStreamId = i;
        this.mStreamIdType = i2;
        this.mModulation = i3;
        this.mCodeRate = i4;
        this.mSymbolRate = i5;
        this.mRolloff = i6;
    }

    public int getStreamId() {
        return this.mStreamId;
    }

    public int getStreamIdType() {
        return this.mStreamIdType;
    }

    public int getModulation() {
        return this.mModulation;
    }

    public int getCodeRate() {
        return this.mCodeRate;
    }

    public int getSymbolRate() {
        return this.mSymbolRate;
    }

    public int getRolloff() {
        return this.mRolloff;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int mCodeRate;
        private long mFrequency;
        private int mModulation;
        private int mRolloff;
        private int mStreamId;
        private int mStreamIdType;
        private int mSymbolRate;

        private Builder() {
            this.mFrequency = 0L;
            this.mStreamId = 65535;
            this.mStreamIdType = 0;
            this.mModulation = 0;
            this.mCodeRate = 0;
            this.mSymbolRate = 0;
            this.mRolloff = 0;
        }

        @Deprecated
        public Builder setFrequency(int i) {
            return setFrequencyLong(i);
        }

        public Builder setFrequencyLong(long j) {
            this.mFrequency = j;
            return this;
        }

        public Builder setStreamId(int i) {
            this.mStreamId = i;
            return this;
        }

        public Builder setStreamIdType(int i) {
            this.mStreamIdType = i;
            return this;
        }

        public Builder setModulation(int i) {
            this.mModulation = i;
            return this;
        }

        public Builder setCodeRate(int i) {
            this.mCodeRate = i;
            return this;
        }

        public Builder setSymbolRate(int i) {
            this.mSymbolRate = i;
            return this;
        }

        public Builder setRolloff(int i) {
            this.mRolloff = i;
            return this;
        }

        public Isdbs3FrontendSettings build() {
            return new Isdbs3FrontendSettings(this.mFrequency, this.mStreamId, this.mStreamIdType, this.mModulation, this.mCodeRate, this.mSymbolRate, this.mRolloff);
        }
    }
}
