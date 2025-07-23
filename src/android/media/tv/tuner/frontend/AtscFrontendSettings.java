package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public class AtscFrontendSettings extends FrontendSettings {
    public static final int MODULATION_AUTO = 1;
    public static final int MODULATION_MOD_16VSB = 8;
    public static final int MODULATION_MOD_8VSB = 4;
    public static final int MODULATION_UNDEFINED = 0;
    private final int mModulation;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Modulation {
    }

    @Override // android.media.tv.tuner.frontend.FrontendSettings
    public int getType() {
        return 2;
    }

    private AtscFrontendSettings(long j, int i) {
        super(j);
        this.mModulation = i;
    }

    public int getModulation() {
        return this.mModulation;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long mFrequency;
        private int mModulation;

        private Builder() {
            this.mFrequency = 0L;
            this.mModulation = 0;
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

        public AtscFrontendSettings build() {
            return new AtscFrontendSettings(this.mFrequency, this.mModulation);
        }
    }
}
