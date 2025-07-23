package android.media.tv.tuner.filter;

import android.annotation.SystemApi;
import android.media.tv.tuner.filter.SectionSettings;

@SystemApi
/* loaded from: classes3.dex */
public class SectionSettingsWithSectionBits extends SectionSettings {
    private final byte[] mFilter;
    private final byte[] mMask;
    private final byte[] mMode;

    private SectionSettingsWithSectionBits(int i, boolean z, boolean z2, boolean z3, int i2, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        super(i, z, z2, z3, i2);
        this.mFilter = bArr;
        this.mMask = bArr2;
        this.mMode = bArr3;
    }

    public byte[] getFilterBytes() {
        return this.mFilter;
    }

    public byte[] getMask() {
        return this.mMask;
    }

    public byte[] getMode() {
        return this.mMode;
    }

    public static Builder builder(int i) {
        return new Builder(i);
    }

    public static class Builder extends SectionSettings.Builder<Builder> {
        private byte[] mFilter;
        private byte[] mMask;
        private byte[] mMode;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // android.media.tv.tuner.filter.SectionSettings.Builder
        public Builder self() {
            return this;
        }

        private Builder(int i) {
            super(i);
            this.mFilter = new byte[0];
            this.mMask = new byte[0];
            this.mMode = new byte[0];
        }

        public Builder setFilter(byte[] bArr) {
            this.mFilter = bArr;
            return this;
        }

        public Builder setMask(byte[] bArr) {
            this.mMask = bArr;
            return this;
        }

        public Builder setMode(byte[] bArr) {
            this.mMode = bArr;
            return this;
        }

        public SectionSettingsWithSectionBits build() {
            return new SectionSettingsWithSectionBits(this.mMainType, this.mCrcEnabled, this.mIsRepeat, this.mIsRaw, this.mBitWidthOfLengthField, this.mFilter, this.mMask, this.mMode);
        }
    }
}
