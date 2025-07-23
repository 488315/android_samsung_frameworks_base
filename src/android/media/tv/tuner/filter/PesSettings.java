package android.media.tv.tuner.filter;

import android.annotation.SystemApi;
import android.media.tv.tuner.TunerUtils;

@SystemApi
/* loaded from: classes3.dex */
public class PesSettings extends Settings {
    private final boolean mIsRaw;
    private final int mStreamId;

    private PesSettings(int i, int i2, boolean z) {
        super(TunerUtils.getFilterSubtype(i, 2));
        this.mStreamId = i2;
        this.mIsRaw = z;
    }

    public int getStreamId() {
        return this.mStreamId;
    }

    public boolean isRaw() {
        return this.mIsRaw;
    }

    public static Builder builder(int i) {
        return new Builder(i);
    }

    public static class Builder {
        private boolean mIsRaw;
        private final int mMainType;
        private int mStreamId;

        private Builder(int i) {
            this.mMainType = i;
        }

        public Builder setStreamId(int i) {
            this.mStreamId = i;
            return this;
        }

        public Builder setRaw(boolean z) {
            this.mIsRaw = z;
            return this;
        }

        public PesSettings build() {
            return new PesSettings(this.mMainType, this.mStreamId, this.mIsRaw);
        }
    }
}
