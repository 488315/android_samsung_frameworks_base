package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;
import android.media.tv.tuner.TunerVersionChecker;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public class DvbsFrontendSettings extends FrontendSettings {
    public static final int MODULATION_AUTO = 1;
    public static final int MODULATION_MOD_128APSK = 2048;
    public static final int MODULATION_MOD_16APSK = 256;
    public static final int MODULATION_MOD_16PSK = 16;
    public static final int MODULATION_MOD_16QAM = 8;
    public static final int MODULATION_MOD_256APSK = 4096;
    public static final int MODULATION_MOD_32APSK = 512;
    public static final int MODULATION_MOD_32PSK = 32;
    public static final int MODULATION_MOD_64APSK = 1024;
    public static final int MODULATION_MOD_8APSK = 128;
    public static final int MODULATION_MOD_8PSK = 4;
    public static final int MODULATION_MOD_ACM = 64;
    public static final int MODULATION_MOD_QPSK = 2;
    public static final int MODULATION_MOD_RESERVED = 8192;
    public static final int MODULATION_UNDEFINED = 0;
    public static final int PILOT_AUTO = 3;
    public static final int PILOT_OFF = 2;
    public static final int PILOT_ON = 1;
    public static final int PILOT_UNDEFINED = 0;
    public static final int ROLLOFF_0_10 = 5;
    public static final int ROLLOFF_0_15 = 4;
    public static final int ROLLOFF_0_20 = 3;
    public static final int ROLLOFF_0_25 = 2;
    public static final int ROLLOFF_0_35 = 1;
    public static final int ROLLOFF_0_5 = 6;
    public static final int ROLLOFF_UNDEFINED = 0;
    public static final int SCAN_TYPE_DIRECT = 1;
    public static final int SCAN_TYPE_DISEQC = 2;
    public static final int SCAN_TYPE_JESS = 4;
    public static final int SCAN_TYPE_UNDEFINED = 0;
    public static final int SCAN_TYPE_UNICABLE = 3;
    public static final int STANDARD_AUTO = 1;
    public static final int STANDARD_S = 2;
    public static final int STANDARD_S2 = 4;
    public static final int STANDARD_S2X = 8;
    public static final int VCM_MODE_AUTO = 1;
    public static final int VCM_MODE_MANUAL = 2;
    public static final int VCM_MODE_UNDEFINED = 0;
    private final DvbsCodeRate mCodeRate;
    private final int mInputStreamId;
    private final boolean mIsDiseqcRxMessage;
    private final int mModulation;
    private final int mPilot;
    private final int mRolloff;
    private final int mScanType;
    private final int mStandard;
    private final int mSymbolRate;
    private final int mVcmMode;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Modulation {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Pilot {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Rolloff {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScanType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Standard {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VcmMode {
    }

    @Override // android.media.tv.tuner.frontend.FrontendSettings
    public int getType() {
        return 5;
    }

    private DvbsFrontendSettings(long j, int i, DvbsCodeRate dvbsCodeRate, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
        super(j);
        this.mModulation = i;
        this.mCodeRate = dvbsCodeRate;
        this.mSymbolRate = i2;
        this.mRolloff = i3;
        this.mPilot = i4;
        this.mInputStreamId = i5;
        this.mStandard = i6;
        this.mVcmMode = i7;
        this.mScanType = i8;
        this.mIsDiseqcRxMessage = z;
    }

    public int getModulation() {
        return this.mModulation;
    }

    public DvbsCodeRate getCodeRate() {
        return this.mCodeRate;
    }

    public int getSymbolRate() {
        return this.mSymbolRate;
    }

    public int getRolloff() {
        return this.mRolloff;
    }

    public int getPilot() {
        return this.mPilot;
    }

    public int getInputStreamId() {
        return this.mInputStreamId;
    }

    public int getStandard() {
        return this.mStandard;
    }

    public int getVcmMode() {
        return this.mVcmMode;
    }

    public int getScanType() {
        return this.mScanType;
    }

    public boolean canHandleDiseqcRxMessage() {
        return this.mIsDiseqcRxMessage;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private DvbsCodeRate mCodeRate;
        private long mFrequency;
        private int mInputStreamId;
        private boolean mIsDiseqcRxMessage;
        private int mModulation;
        private int mPilot;
        private int mRolloff;
        private int mScanType;
        private int mStandard;
        private int mSymbolRate;
        private int mVcmMode;

        private Builder() {
            this.mFrequency = 0L;
            this.mModulation = 0;
            this.mCodeRate = null;
            this.mSymbolRate = 0;
            this.mRolloff = 0;
            this.mPilot = 0;
            this.mInputStreamId = 65535;
            this.mStandard = 1;
            this.mVcmMode = 0;
            this.mScanType = 0;
            this.mIsDiseqcRxMessage = false;
        }

        @Deprecated
        public Builder setFrequency(int i) {
            return setFrequencyLong(i);
        }

        public Builder setFrequencyLong(long j) {
            this.mFrequency = j;
            return this;
        }

        public Builder setScanType(int i) {
            if (TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "setScanType")) {
                this.mScanType = i;
            }
            return this;
        }

        public Builder setCanHandleDiseqcRxMessage(boolean z) {
            if (TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "setCanHandleDiseqcRxMessage")) {
                this.mIsDiseqcRxMessage = z;
            }
            return this;
        }

        public Builder setModulation(int i) {
            this.mModulation = i;
            return this;
        }

        public Builder setCodeRate(DvbsCodeRate dvbsCodeRate) {
            this.mCodeRate = dvbsCodeRate;
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

        public Builder setPilot(int i) {
            this.mPilot = i;
            return this;
        }

        public Builder setInputStreamId(int i) {
            this.mInputStreamId = i;
            return this;
        }

        public Builder setStandard(int i) {
            this.mStandard = i;
            return this;
        }

        public Builder setVcmMode(int i) {
            this.mVcmMode = i;
            return this;
        }

        public DvbsFrontendSettings build() {
            return new DvbsFrontendSettings(this.mFrequency, this.mModulation, this.mCodeRate, this.mSymbolRate, this.mRolloff, this.mPilot, this.mInputStreamId, this.mStandard, this.mVcmMode, this.mScanType, this.mIsDiseqcRxMessage);
        }
    }
}
