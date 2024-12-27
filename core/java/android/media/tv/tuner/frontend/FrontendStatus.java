package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;
import android.media.tv.tuner.TunerVersionChecker;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SystemApi
/* loaded from: classes3.dex */
public class FrontendStatus {
    public static final int FRONTEND_STATUS_TYPE_AGC = 14;
    public static final int FRONTEND_STATUS_TYPE_ATSC3_ALL_PLP_INFO = 41;
    public static final int FRONTEND_STATUS_TYPE_ATSC3_PLP_INFO = 21;
    public static final int FRONTEND_STATUS_TYPE_BANDWIDTH = 25;
    public static final int FRONTEND_STATUS_TYPE_BER = 2;
    public static final int FRONTEND_STATUS_TYPE_BERS = 23;
    public static final int FRONTEND_STATUS_TYPE_CODERATES = 24;
    public static final int FRONTEND_STATUS_TYPE_DEMOD_LOCK = 0;
    public static final int FRONTEND_STATUS_TYPE_DVBT_CELL_IDS = 40;
    public static final int FRONTEND_STATUS_TYPE_EWBS = 13;
    public static final int FRONTEND_STATUS_TYPE_FEC = 8;
    public static final int FRONTEND_STATUS_TYPE_FREQ_OFFSET = 18;
    public static final int FRONTEND_STATUS_TYPE_GUARD_INTERVAL = 26;
    public static final int FRONTEND_STATUS_TYPE_HIERARCHY = 19;
    public static final int FRONTEND_STATUS_TYPE_INTERLEAVINGS = 30;
    public static final int FRONTEND_STATUS_TYPE_IPTV_AVERAGE_JITTER_MS = 46;
    public static final int FRONTEND_STATUS_TYPE_IPTV_CONTENT_URL = 42;
    public static final int FRONTEND_STATUS_TYPE_IPTV_PACKETS_LOST = 43;
    public static final int FRONTEND_STATUS_TYPE_IPTV_PACKETS_RECEIVED = 44;
    public static final int FRONTEND_STATUS_TYPE_IPTV_WORST_JITTER_MS = 45;
    public static final int FRONTEND_STATUS_TYPE_ISDBT_MODE = 37;
    public static final int FRONTEND_STATUS_TYPE_ISDBT_PARTIAL_RECEPTION_FLAG = 38;
    public static final int FRONTEND_STATUS_TYPE_ISDBT_SEGMENTS = 31;
    public static final int FRONTEND_STATUS_TYPE_IS_LINEAR = 35;
    public static final int FRONTEND_STATUS_TYPE_IS_MISO_ENABLED = 34;
    public static final int FRONTEND_STATUS_TYPE_IS_SHORT_FRAMES_ENABLED = 36;
    public static final int FRONTEND_STATUS_TYPE_LAYER_ERROR = 16;
    public static final int FRONTEND_STATUS_TYPE_LNA = 15;
    public static final int FRONTEND_STATUS_TYPE_LNB_VOLTAGE = 11;
    public static final int FRONTEND_STATUS_TYPE_MER = 17;
    public static final int FRONTEND_STATUS_TYPE_MODULATION = 9;
    public static final int FRONTEND_STATUS_TYPE_MODULATIONS_EXT = 22;
    public static final int FRONTEND_STATUS_TYPE_PER = 3;
    public static final int FRONTEND_STATUS_TYPE_PLP_ID = 12;
    public static final int FRONTEND_STATUS_TYPE_PRE_BER = 4;
    public static final int FRONTEND_STATUS_TYPE_RF_LOCK = 20;
    public static final int FRONTEND_STATUS_TYPE_ROLL_OFF = 33;
    public static final int FRONTEND_STATUS_TYPE_SIGNAL_QUALITY = 5;
    public static final int FRONTEND_STATUS_TYPE_SIGNAL_STRENGTH = 6;
    public static final int FRONTEND_STATUS_TYPE_SNR = 1;
    public static final int FRONTEND_STATUS_TYPE_SPECTRAL = 10;
    public static final int FRONTEND_STATUS_TYPE_STREAM_IDS = 39;
    public static final int FRONTEND_STATUS_TYPE_SYMBOL_RATE = 7;
    public static final int FRONTEND_STATUS_TYPE_T2_SYSTEM_ID = 29;
    public static final int FRONTEND_STATUS_TYPE_TRANSMISSION_MODE = 27;
    public static final int FRONTEND_STATUS_TYPE_TS_DATA_RATES = 32;
    public static final int FRONTEND_STATUS_TYPE_UEC = 28;
    private Integer mAgc;
    private Atsc3PlpInfo[] mAllPlpInfo;
    private Integer mBandwidth;
    private Integer mBer;
    private int[] mBers;
    private int[] mCodeRates;
    private int[] mDvbtCellIds;
    private Long mFreqOffset;
    private Integer mGuardInterval;
    private Integer mHierarchy;
    private Long mInnerFec;
    private int[] mInterleaving;
    private Integer mInversion;
    private Integer mIptvAverageJitterMs;
    private String mIptvContentUrl;
    private Long mIptvPacketsLost;
    private Long mIptvPacketsReceived;
    private Integer mIptvWorstJitterMs;
    private Boolean mIsDemodLocked;
    private Boolean mIsEwbs;
    private boolean[] mIsLayerErrors;
    private Boolean mIsLinear;
    private Boolean mIsLnaOn;
    private Boolean mIsMisoEnabled;
    private Boolean mIsRfLocked;
    private Boolean mIsShortFrames;
    private Integer mIsdbtMode;
    private Integer mIsdbtPartialReceptionFlag;
    private int[] mIsdbtSegment;
    private Integer mLnbVoltage;
    private Integer mMer;
    private Integer mModulation;
    private int[] mModulationsExt;
    private Integer mPer;
    private Integer mPerBer;
    private Integer mPlpId;
    private Atsc3PlpTuningInfo[] mPlpInfo;
    private Integer mRollOff;
    private Integer mSignalQuality;
    private Integer mSignalStrength;
    private Integer mSnr;
    private int[] mStreamIds;
    private Integer mSymbolRate;
    private Integer mSystemId;
    private Integer mTransmissionMode;
    private int[] mTsDataRate;
    private Integer mUec;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FrontendBandwidth {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FrontendGuardInterval {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FrontendInterleaveMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FrontendModulation {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FrontendRollOff {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FrontendStatusType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FrontendTransmissionMode {
    }

    private FrontendStatus() {
    }

    public boolean isDemodLocked() {
        if (this.mIsDemodLocked == null) {
            throw new IllegalStateException("DemodLocked status is empty");
        }
        return this.mIsDemodLocked.booleanValue();
    }

    public int getSnr() {
        if (this.mSnr == null) {
            throw new IllegalStateException("Snr status is empty");
        }
        return this.mSnr.intValue();
    }

    public int getBer() {
        if (this.mBer == null) {
            throw new IllegalStateException("Ber status is empty");
        }
        return this.mBer.intValue();
    }

    public int getPer() {
        if (this.mPer == null) {
            throw new IllegalStateException("Per status is empty");
        }
        return this.mPer.intValue();
    }

    public int getPerBer() {
        if (this.mPerBer == null) {
            throw new IllegalStateException("PerBer status is empty");
        }
        return this.mPerBer.intValue();
    }

    public int getSignalQuality() {
        if (this.mSignalQuality == null) {
            throw new IllegalStateException("SignalQuality status is empty");
        }
        return this.mSignalQuality.intValue();
    }

    public int getSignalStrength() {
        if (this.mSignalStrength == null) {
            throw new IllegalStateException("SignalStrength status is empty");
        }
        return this.mSignalStrength.intValue();
    }

    public int getSymbolRate() {
        if (this.mSymbolRate == null) {
            throw new IllegalStateException("SymbolRate status is empty");
        }
        return this.mSymbolRate.intValue();
    }

    public long getInnerFec() {
        if (this.mInnerFec == null) {
            throw new IllegalStateException("InnerFec status is empty");
        }
        return this.mInnerFec.longValue();
    }

    public int getModulation() {
        if (this.mModulation == null) {
            throw new IllegalStateException("Modulation status is empty");
        }
        return this.mModulation.intValue();
    }

    public int getSpectralInversion() {
        if (this.mInversion == null) {
            throw new IllegalStateException("SpectralInversion status is empty");
        }
        return this.mInversion.intValue();
    }

    public int getLnbVoltage() {
        if (this.mLnbVoltage == null) {
            throw new IllegalStateException("LnbVoltage status is empty");
        }
        return this.mLnbVoltage.intValue();
    }

    public int getPlpId() {
        if (this.mPlpId == null) {
            throw new IllegalStateException("PlpId status is empty");
        }
        return this.mPlpId.intValue();
    }

    public boolean isEwbs() {
        if (this.mIsEwbs == null) {
            throw new IllegalStateException("Ewbs status is empty");
        }
        return this.mIsEwbs.booleanValue();
    }

    public int getAgc() {
        if (this.mAgc == null) {
            throw new IllegalStateException("Agc status is empty");
        }
        return this.mAgc.intValue();
    }

    public boolean isLnaOn() {
        if (this.mIsLnaOn == null) {
            throw new IllegalStateException("LnaOn status is empty");
        }
        return this.mIsLnaOn.booleanValue();
    }

    public boolean[] getLayerErrors() {
        if (this.mIsLayerErrors == null) {
            throw new IllegalStateException("LayerErrors status is empty");
        }
        return this.mIsLayerErrors;
    }

    public int getMer() {
        if (this.mMer == null) {
            throw new IllegalStateException("Mer status is empty");
        }
        return this.mMer.intValue();
    }

    @Deprecated
    public int getFreqOffset() {
        return (int) getFreqOffsetLong();
    }

    public long getFreqOffsetLong() {
        if (this.mFreqOffset == null) {
            throw new IllegalStateException("FreqOffset status is empty");
        }
        return this.mFreqOffset.longValue();
    }

    public int getHierarchy() {
        if (this.mHierarchy == null) {
            throw new IllegalStateException("Hierarchy status is empty");
        }
        return this.mHierarchy.intValue();
    }

    public boolean isRfLocked() {
        if (this.mIsRfLocked == null) {
            throw new IllegalStateException("isRfLocked status is empty");
        }
        return this.mIsRfLocked.booleanValue();
    }

    public Atsc3PlpTuningInfo[] getAtsc3PlpTuningInfo() {
        if (this.mPlpInfo == null) {
            throw new IllegalStateException("Atsc3PlpTuningInfo status is empty");
        }
        return this.mPlpInfo;
    }

    public int[] getBers() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getBers status");
        if (this.mBers == null) {
            throw new IllegalStateException("Bers status is empty");
        }
        return this.mBers;
    }

    public int[] getCodeRates() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getCodeRates status");
        if (this.mCodeRates == null) {
            throw new IllegalStateException("CodeRates status is empty");
        }
        return this.mCodeRates;
    }

    public int getBandwidth() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getBandwidth status");
        if (this.mBandwidth == null) {
            throw new IllegalStateException("Bandwidth status is empty");
        }
        return this.mBandwidth.intValue();
    }

    public int getGuardInterval() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getGuardInterval status");
        if (this.mGuardInterval == null) {
            throw new IllegalStateException("GuardInterval status is empty");
        }
        return this.mGuardInterval.intValue();
    }

    public int getTransmissionMode() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getTransmissionMode status");
        if (this.mTransmissionMode == null) {
            throw new IllegalStateException("TransmissionMode status is empty");
        }
        return this.mTransmissionMode.intValue();
    }

    public int getUec() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getUec status");
        if (this.mUec == null) {
            throw new IllegalStateException("Uec status is empty");
        }
        return this.mUec.intValue();
    }

    public int getSystemId() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getSystemId status");
        if (this.mSystemId == null) {
            throw new IllegalStateException("SystemId status is empty");
        }
        return this.mSystemId.intValue();
    }

    public int[] getInterleaving() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getInterleaving status");
        if (this.mInterleaving == null) {
            throw new IllegalStateException("Interleaving status is empty");
        }
        return this.mInterleaving;
    }

    public int[] getIsdbtSegment() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getIsdbtSegment status");
        if (this.mIsdbtSegment == null) {
            throw new IllegalStateException("IsdbtSegment status is empty");
        }
        return this.mIsdbtSegment;
    }

    public int[] getTsDataRate() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getTsDataRate status");
        if (this.mTsDataRate == null) {
            throw new IllegalStateException("TsDataRate status is empty");
        }
        return this.mTsDataRate;
    }

    public int[] getExtendedModulations() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getExtendedModulations status");
        if (this.mModulationsExt == null) {
            throw new IllegalStateException("ExtendedModulations status is empty");
        }
        return this.mModulationsExt;
    }

    public int getRollOff() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "getRollOff status");
        if (this.mRollOff == null) {
            throw new IllegalStateException("RollOff status is empty");
        }
        return this.mRollOff.intValue();
    }

    public boolean isMisoEnabled() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "isMisoEnabled status");
        if (this.mIsMisoEnabled == null) {
            throw new IllegalStateException("isMisoEnabled status is empty");
        }
        return this.mIsMisoEnabled.booleanValue();
    }

    public boolean isLinear() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "isLinear status");
        if (this.mIsLinear == null) {
            throw new IllegalStateException("isLinear status is empty");
        }
        return this.mIsLinear.booleanValue();
    }

    public boolean isShortFramesEnabled() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "isShortFramesEnabled status");
        if (this.mIsShortFrames == null) {
            throw new IllegalStateException("isShortFramesEnabled status is empty");
        }
        return this.mIsShortFrames.booleanValue();
    }

    public int getIsdbtMode() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "IsdbtMode status");
        if (this.mIsdbtMode == null) {
            throw new IllegalStateException("IsdbtMode status is empty");
        }
        return this.mIsdbtMode.intValue();
    }

    public int getIsdbtPartialReceptionFlag() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "IsdbtPartialReceptionFlag status");
        if (this.mIsdbtPartialReceptionFlag == null) {
            throw new IllegalStateException("IsdbtPartialReceptionFlag status is empty");
        }
        return this.mIsdbtPartialReceptionFlag.intValue();
    }

    public int[] getStreamIds() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "stream ids status");
        if (this.mStreamIds == null) {
            throw new IllegalStateException("stream ids are empty");
        }
        return this.mStreamIds;
    }

    public int[] getDvbtCellIds() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "dvbt cell ids status");
        if (this.mDvbtCellIds == null) {
            throw new IllegalStateException("dvbt cell ids are empty");
        }
        return this.mDvbtCellIds;
    }

    public List<Atsc3PlpInfo> getAllAtsc3PlpInfo() {
        if (!TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "Atsc3PlpInfo all status")) {
            throw new UnsupportedOperationException("Atsc3PlpInfo all status is empty");
        }
        if (this.mAllPlpInfo == null) {
            return Collections.EMPTY_LIST;
        }
        return Arrays.asList(this.mAllPlpInfo);
    }

    public static class Atsc3PlpTuningInfo {
        private final boolean mIsLocked;
        private final int mPlpId;
        private final int mUec;

        private Atsc3PlpTuningInfo(int plpId, boolean isLocked, int uec) {
            this.mPlpId = plpId;
            this.mIsLocked = isLocked;
            this.mUec = uec;
        }

        public int getPlpId() {
            return this.mPlpId;
        }

        public boolean isLocked() {
            return this.mIsLocked;
        }

        public int getUec() {
            return this.mUec;
        }
    }

    public String getIptvContentUrl() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "IptvContentUrl status");
        if (this.mIptvContentUrl == null) {
            throw new IllegalStateException("IptvContentUrl status is empty");
        }
        return this.mIptvContentUrl;
    }

    public long getIptvPacketsLost() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "IptvPacketsLost status");
        if (this.mIptvPacketsLost == null) {
            throw new IllegalStateException("IptvPacketsLost status is empty");
        }
        return this.mIptvPacketsLost.longValue();
    }

    public long getIptvPacketsReceived() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "IptvPacketsReceived status");
        if (this.mIptvPacketsReceived == null) {
            throw new IllegalStateException("IptvPacketsReceived status is empty");
        }
        return this.mIptvPacketsReceived.longValue();
    }

    public int getIptvWorstJitterMillis() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "IptvWorstJitterMs status");
        if (this.mIptvWorstJitterMs == null) {
            throw new IllegalStateException("IptvWorstJitterMs status is empty");
        }
        return this.mIptvWorstJitterMs.intValue();
    }

    public int getIptvAverageJitterMillis() {
        TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "IptvAverageJitterMs status");
        if (this.mIptvAverageJitterMs == null) {
            throw new IllegalStateException("IptvAverageJitterMs status is empty");
        }
        return this.mIptvAverageJitterMs.intValue();
    }
}
