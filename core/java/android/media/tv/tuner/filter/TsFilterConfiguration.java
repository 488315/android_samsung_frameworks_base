package android.media.tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
public final class TsFilterConfiguration extends FilterConfiguration {
    private final int mTpid;

    private TsFilterConfiguration(Settings settings, int tpid) {
        super(settings);
        this.mTpid = tpid;
    }

    @Override // android.media.tv.tuner.filter.FilterConfiguration
    public int getType() {
        return 1;
    }

    public int getTpid() {
        return this.mTpid;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Settings mSettings;
        private int mTpid;

        private Builder() {
            this.mTpid = 0;
        }

        public Builder setTpid(int tpid) {
            this.mTpid = tpid;
            return this;
        }

        public Builder setSettings(Settings settings) {
            this.mSettings = settings;
            return this;
        }

        public TsFilterConfiguration build() {
            return new TsFilterConfiguration(this.mSettings, this.mTpid);
        }
    }
}
