package android.media.tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
public class SectionSettingsWithTableInfo extends SectionSettings {
    public static final int INVALID_TABLE_INFO_VERSION = -1;
    private final int mTableId;
    private final int mVersion;

    private SectionSettingsWithTableInfo(
            int mainType,
            boolean isCheckCrc,
            boolean isRepeat,
            boolean isRaw,
            int bitWidthOfLengthField,
            int tableId,
            int version) {
        super(mainType, isCheckCrc, isRepeat, isRaw, bitWidthOfLengthField);
        this.mTableId = tableId;
        this.mVersion = version;
    }

    public int getTableId() {
        return this.mTableId;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public static Builder builder(int mainType) {
        return new Builder(mainType);
    }

    public static class Builder extends SectionSettings.Builder<Builder> {
        private int mTableId;
        private int mVersion;

        private Builder(int mainType) {
            super(mainType);
            this.mVersion = -1;
        }

        public Builder setTableId(int tableId) {
            this.mTableId = tableId;
            return this;
        }

        public Builder setVersion(int version) {
            this.mVersion = version;
            return this;
        }

        public SectionSettingsWithTableInfo build() {
            return new SectionSettingsWithTableInfo(
                    this.mMainType,
                    this.mCrcEnabled,
                    this.mIsRepeat,
                    this.mIsRaw,
                    this.mBitWidthOfLengthField,
                    this.mTableId,
                    this.mVersion);
        }

        @Override // android.media.tv.tuner.filter.SectionSettings.Builder
        public Builder self() {
            return this;
        }
    }
}
