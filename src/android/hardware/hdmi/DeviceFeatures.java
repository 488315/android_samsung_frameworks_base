package android.hardware.hdmi;

import android.hardware.gnss.GnssSignalType;
import java.util.Objects;

/* loaded from: classes2.dex */
public class DeviceFeatures {
    public static final DeviceFeatures ALL_FEATURES_SUPPORT_UNKNOWN;
    public static final int FEATURE_NOT_SUPPORTED = 0;
    public static final int FEATURE_SUPPORTED = 1;
    public static final int FEATURE_SUPPORT_UNKNOWN = 2;
    public static final DeviceFeatures NO_FEATURES_SUPPORTED;
    private final int mArcRxSupport;
    private final int mArcTxSupport;
    private final int mDeckControlSupport;
    private final int mRecordTvScreenSupport;
    private final int mSetAudioRateSupport;
    private final int mSetAudioVolumeLevelSupport;
    private final int mSetOsdStringSupport;

    public @interface FeatureSupportStatus {
    }

    private static int bitToFeatureSupportStatus(boolean z) {
        return z ? 1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int updateFeatureSupportStatus(int i, int i2) {
        return i2 == 2 ? i : i2;
    }

    static {
        ALL_FEATURES_SUPPORT_UNKNOWN = new Builder(2).build();
        NO_FEATURES_SUPPORTED = new Builder(0).build();
    }

    private DeviceFeatures(Builder builder) {
        this.mRecordTvScreenSupport = builder.mRecordTvScreenSupport;
        this.mSetOsdStringSupport = builder.mOsdStringSupport;
        this.mDeckControlSupport = builder.mDeckControlSupport;
        this.mSetAudioRateSupport = builder.mSetAudioRateSupport;
        this.mArcTxSupport = builder.mArcTxSupport;
        this.mArcRxSupport = builder.mArcRxSupport;
        this.mSetAudioVolumeLevelSupport = builder.mSetAudioVolumeLevelSupport;
    }

    public Builder toBuilder() {
        return new Builder();
    }

    public static DeviceFeatures fromOperand(byte[] bArr) {
        Builder builder = new Builder(2);
        if (bArr.length >= 1) {
            byte b = bArr[0];
            builder.setRecordTvScreenSupport(bitToFeatureSupportStatus(((b >> 6) & 1) == 1)).setSetOsdStringSupport(bitToFeatureSupportStatus(((b >> 5) & 1) == 1)).setDeckControlSupport(bitToFeatureSupportStatus(((b >> 4) & 1) == 1)).setSetAudioRateSupport(bitToFeatureSupportStatus(((b >> 3) & 1) == 1)).setArcTxSupport(bitToFeatureSupportStatus(((b >> 2) & 1) == 1)).setArcRxSupport(bitToFeatureSupportStatus(((b >> 1) & 1) == 1)).setSetAudioVolumeLevelSupport(bitToFeatureSupportStatus((b & 1) == 1));
        }
        return builder.build();
    }

    public byte[] toOperand() {
        byte b = this.mRecordTvScreenSupport == 1 ? (byte) 64 : (byte) 0;
        if (this.mSetOsdStringSupport == 1) {
            b = (byte) (b | 32);
        }
        if (this.mDeckControlSupport == 1) {
            b = (byte) (b | 16);
        }
        if (this.mSetAudioRateSupport == 1) {
            b = (byte) (b | 8);
        }
        if (this.mArcTxSupport == 1) {
            b = (byte) (b | 4);
        }
        if (this.mArcRxSupport == 1) {
            b = (byte) (b | 2);
        }
        if (this.mSetAudioVolumeLevelSupport == 1) {
            b = (byte) (b | 1);
        }
        return new byte[]{b};
    }

    public int getRecordTvScreenSupport() {
        return this.mRecordTvScreenSupport;
    }

    public int getSetOsdStringSupport() {
        return this.mSetOsdStringSupport;
    }

    public int getDeckControlSupport() {
        return this.mDeckControlSupport;
    }

    public int getSetAudioRateSupport() {
        return this.mSetAudioRateSupport;
    }

    public int getArcTxSupport() {
        return this.mArcTxSupport;
    }

    public int getArcRxSupport() {
        return this.mArcRxSupport;
    }

    public int getSetAudioVolumeLevelSupport() {
        return this.mSetAudioVolumeLevelSupport;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DeviceFeatures)) {
            return false;
        }
        DeviceFeatures deviceFeatures = (DeviceFeatures) obj;
        return this.mRecordTvScreenSupport == deviceFeatures.mRecordTvScreenSupport && this.mSetOsdStringSupport == deviceFeatures.mSetOsdStringSupport && this.mDeckControlSupport == deviceFeatures.mDeckControlSupport && this.mSetAudioRateSupport == deviceFeatures.mSetAudioRateSupport && this.mArcTxSupport == deviceFeatures.mArcTxSupport && this.mArcRxSupport == deviceFeatures.mArcRxSupport && this.mSetAudioVolumeLevelSupport == deviceFeatures.mSetAudioVolumeLevelSupport;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mRecordTvScreenSupport), Integer.valueOf(this.mSetOsdStringSupport), Integer.valueOf(this.mDeckControlSupport), Integer.valueOf(this.mSetAudioRateSupport), Integer.valueOf(this.mArcTxSupport), Integer.valueOf(this.mArcRxSupport), Integer.valueOf(this.mSetAudioVolumeLevelSupport));
    }

    public String toString() {
        return "Device features: record_tv_screen: " + featureSupportStatusToString(this.mRecordTvScreenSupport) + " set_osd_string: " + featureSupportStatusToString(this.mSetOsdStringSupport) + " deck_control: " + featureSupportStatusToString(this.mDeckControlSupport) + " set_audio_rate: " + featureSupportStatusToString(this.mSetAudioRateSupport) + " arc_tx: " + featureSupportStatusToString(this.mArcTxSupport) + " arc_rx: " + featureSupportStatusToString(this.mArcRxSupport) + " set_audio_volume_level: " + featureSupportStatusToString(this.mSetAudioVolumeLevelSupport) + " ";
    }

    private static String featureSupportStatusToString(int i) {
        if (i == 0) {
            return GnssSignalType.CODE_TYPE_N;
        }
        if (i == 1) {
            return GnssSignalType.CODE_TYPE_Y;
        }
        return "?";
    }

    public static final class Builder {
        private int mArcRxSupport;
        private int mArcTxSupport;
        private int mDeckControlSupport;
        private int mOsdStringSupport;
        private int mRecordTvScreenSupport;
        private int mSetAudioRateSupport;
        private int mSetAudioVolumeLevelSupport;

        private Builder(int i) {
            this.mRecordTvScreenSupport = i;
            this.mOsdStringSupport = i;
            this.mDeckControlSupport = i;
            this.mSetAudioRateSupport = i;
            this.mArcTxSupport = i;
            this.mArcRxSupport = i;
            this.mSetAudioVolumeLevelSupport = i;
        }

        private Builder(DeviceFeatures deviceFeatures) {
            this.mRecordTvScreenSupport = deviceFeatures.getRecordTvScreenSupport();
            this.mOsdStringSupport = deviceFeatures.getSetOsdStringSupport();
            this.mDeckControlSupport = deviceFeatures.getDeckControlSupport();
            this.mSetAudioRateSupport = deviceFeatures.getSetAudioRateSupport();
            this.mArcTxSupport = deviceFeatures.getArcTxSupport();
            this.mArcRxSupport = deviceFeatures.getArcRxSupport();
            this.mSetAudioVolumeLevelSupport = deviceFeatures.getSetAudioVolumeLevelSupport();
        }

        public DeviceFeatures build() {
            return new DeviceFeatures(this);
        }

        public Builder setRecordTvScreenSupport(int i) {
            this.mRecordTvScreenSupport = i;
            return this;
        }

        public Builder setSetOsdStringSupport(int i) {
            this.mOsdStringSupport = i;
            return this;
        }

        public Builder setDeckControlSupport(int i) {
            this.mDeckControlSupport = i;
            return this;
        }

        public Builder setSetAudioRateSupport(int i) {
            this.mSetAudioRateSupport = i;
            return this;
        }

        public Builder setArcTxSupport(int i) {
            this.mArcTxSupport = i;
            return this;
        }

        public Builder setArcRxSupport(int i) {
            this.mArcRxSupport = i;
            return this;
        }

        public Builder setSetAudioVolumeLevelSupport(int i) {
            this.mSetAudioVolumeLevelSupport = i;
            return this;
        }

        public Builder update(DeviceFeatures deviceFeatures) {
            this.mRecordTvScreenSupport = DeviceFeatures.updateFeatureSupportStatus(this.mRecordTvScreenSupport, deviceFeatures.getRecordTvScreenSupport());
            this.mOsdStringSupport = DeviceFeatures.updateFeatureSupportStatus(this.mOsdStringSupport, deviceFeatures.getSetOsdStringSupport());
            this.mDeckControlSupport = DeviceFeatures.updateFeatureSupportStatus(this.mDeckControlSupport, deviceFeatures.getDeckControlSupport());
            this.mSetAudioRateSupport = DeviceFeatures.updateFeatureSupportStatus(this.mSetAudioRateSupport, deviceFeatures.getSetAudioRateSupport());
            this.mArcTxSupport = DeviceFeatures.updateFeatureSupportStatus(this.mArcTxSupport, deviceFeatures.getArcTxSupport());
            this.mArcRxSupport = DeviceFeatures.updateFeatureSupportStatus(this.mArcRxSupport, deviceFeatures.getArcRxSupport());
            this.mSetAudioVolumeLevelSupport = DeviceFeatures.updateFeatureSupportStatus(this.mSetAudioVolumeLevelSupport, deviceFeatures.getSetAudioVolumeLevelSupport());
            return this;
        }
    }
}
