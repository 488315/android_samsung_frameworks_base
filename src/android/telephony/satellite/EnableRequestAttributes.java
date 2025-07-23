package android.telephony.satellite;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes4.dex */
public class EnableRequestAttributes {
    private boolean mIsDemoMode;
    private boolean mIsEmergencyMode;
    private boolean mIsEnabled;

    private EnableRequestAttributes(Builder builder) {
        this.mIsEnabled = builder.mIsEnabled;
        this.mIsDemoMode = builder.mIsDemoMode;
        this.mIsEmergencyMode = builder.mIsEmergencyMode;
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public boolean isDemoMode() {
        return this.mIsDemoMode;
    }

    public boolean isEmergencyMode() {
        return this.mIsEmergencyMode;
    }

    public static final class Builder {
        private boolean mIsDemoMode = false;
        private boolean mIsEmergencyMode = false;
        private boolean mIsEnabled;

        public Builder(boolean z) {
            this.mIsEnabled = z;
        }

        public Builder setDemoMode(boolean z) {
            if (this.mIsEnabled) {
                this.mIsDemoMode = z;
            }
            return this;
        }

        public Builder setEmergencyMode(boolean z) {
            if (this.mIsEnabled) {
                this.mIsEmergencyMode = z;
            }
            return this;
        }

        public EnableRequestAttributes build() {
            return new EnableRequestAttributes(this);
        }
    }
}
