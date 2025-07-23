package android.media.tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public final class MmtpFilterConfiguration extends FilterConfiguration {
    private final int mMmtpPid;

    @Override // android.media.tv.tuner.filter.FilterConfiguration
    public int getType() {
        return 2;
    }

    private MmtpFilterConfiguration(Settings settings, int i) {
        super(settings);
        this.mMmtpPid = i;
    }

    public int getMmtpPacketId() {
        return this.mMmtpPid;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private int mMmtpPid;
        private Settings mSettings;

        private Builder() {
            this.mMmtpPid = 65535;
        }

        public Builder setMmtpPacketId(int i) {
            this.mMmtpPid = i;
            return this;
        }

        public Builder setSettings(Settings settings) {
            this.mSettings = settings;
            return this;
        }

        public MmtpFilterConfiguration build() {
            return new MmtpFilterConfiguration(this.mSettings, this.mMmtpPid);
        }
    }
}
