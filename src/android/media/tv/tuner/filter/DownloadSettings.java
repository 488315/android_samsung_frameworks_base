package android.media.tv.tuner.filter;

import android.annotation.SystemApi;
import android.media.tv.tuner.TunerUtils;
import android.media.tv.tuner.TunerVersionChecker;

@SystemApi
/* loaded from: classes3.dex */
public class DownloadSettings extends Settings {
    private final int mDownloadId;
    private final boolean mUseDownloadId;

    private DownloadSettings(int i, boolean z, int i2) {
        super(TunerUtils.getFilterSubtype(i, 5));
        this.mUseDownloadId = z;
        this.mDownloadId = i2;
    }

    public int getDownloadId() {
        return this.mDownloadId;
    }

    public boolean useDownloadId() {
        return this.mUseDownloadId;
    }

    public static Builder builder(int i) {
        return new Builder(i);
    }

    public static class Builder {
        private int mDownloadId;
        private final int mMainType;
        private boolean mUseDownloadId;

        private Builder(int i) {
            this.mUseDownloadId = false;
            this.mMainType = i;
        }

        public Builder setUseDownloadId(boolean z) {
            if (TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "setUseDownloadId")) {
                this.mUseDownloadId = z;
            }
            return this;
        }

        public Builder setDownloadId(int i) {
            this.mDownloadId = i;
            return this;
        }

        public DownloadSettings build() {
            return new DownloadSettings(this.mMainType, this.mUseDownloadId, this.mDownloadId);
        }
    }
}
