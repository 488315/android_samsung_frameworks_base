package android.credentials.selection;

import android.content.Intent;

/* loaded from: classes.dex */
public final class IntentCreationResult {
    private final String mFallbackUiPackageName;
    private final Intent mIntent;
    private final String mOemUiPackageName;
    private final OemUiUsageStatus mOemUiUsageStatus;

    public enum OemUiUsageStatus {
        UNKNOWN,
        SUCCESS,
        OEM_UI_CONFIG_NOT_SPECIFIED,
        OEM_UI_CONFIG_SPECIFIED_BUT_NOT_FOUND,
        OEM_UI_CONFIG_SPECIFIED_FOUND_BUT_NOT_ENABLED
    }

    private IntentCreationResult(Intent intent, String fallbackUiPackageName, String oemUiPackageName, OemUiUsageStatus oemUiUsageStatus) {
        this.mIntent = intent;
        this.mFallbackUiPackageName = fallbackUiPackageName;
        this.mOemUiPackageName = oemUiPackageName;
        this.mOemUiUsageStatus = oemUiUsageStatus;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public OemUiUsageStatus getOemUiUsageStatus() {
        return this.mOemUiUsageStatus;
    }

    public String getFallbackUiPackageName() {
        return this.mFallbackUiPackageName;
    }

    public String getOemUiPackageName() {
        return this.mOemUiPackageName;
    }

    public static final class Builder {
        private Intent mIntent;
        private String mFallbackUiPackageName = null;
        private String mOemUiPackageName = null;
        private OemUiUsageStatus mOemUiUsageStatus = OemUiUsageStatus.UNKNOWN;

        public Builder(Intent intent) {
            this.mIntent = intent;
        }

        public Builder setFallbackUiPackageName(String fallbackUiPackageName) {
            this.mFallbackUiPackageName = fallbackUiPackageName;
            return this;
        }

        public Builder setOemUiPackageName(String oemUiPackageName) {
            this.mOemUiPackageName = oemUiPackageName;
            return this;
        }

        public Builder setOemUiUsageStatus(OemUiUsageStatus oemUiUsageStatus) {
            this.mOemUiUsageStatus = oemUiUsageStatus;
            return this;
        }

        public IntentCreationResult build() {
            return new IntentCreationResult(this.mIntent, this.mFallbackUiPackageName, this.mOemUiPackageName, this.mOemUiUsageStatus);
        }
    }
}
