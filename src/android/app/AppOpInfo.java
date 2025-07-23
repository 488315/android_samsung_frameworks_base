package android.app;

import android.app.AppOpsManager;
import java.util.Objects;

/* loaded from: classes.dex */
class AppOpInfo {
    public final AppOpsManager.RestrictionBypass allowSystemRestrictionBypass;
    public final int code;
    public final int defaultMode;
    public final boolean disableReset;
    public final boolean forceCollectNotes;
    public final String name;
    public final String permission;
    public final boolean restrictRead;
    public final String restriction;
    public final String simpleName;
    public final int switchCode;

    AppOpInfo(int i, int i2, String str, String str2, String str3, String str4, AppOpsManager.RestrictionBypass restrictionBypass, int i3, boolean z, boolean z2, boolean z3) {
        if (i < -1) {
            throw new IllegalArgumentException();
        }
        if (i2 < -1) {
            throw new IllegalArgumentException();
        }
        Objects.requireNonNull(str);
        Objects.requireNonNull(str2);
        this.code = i;
        this.switchCode = i2;
        this.name = str;
        this.simpleName = str2;
        this.permission = str3;
        this.restriction = str4;
        this.allowSystemRestrictionBypass = restrictionBypass;
        this.defaultMode = i3;
        this.disableReset = z;
        this.restrictRead = z2;
        this.forceCollectNotes = z3;
    }

    static class Builder {
        private int mCode;
        private String mName;
        private String mSimpleName;
        private int mSwitchCode;
        private String mPermission = null;
        private String mRestriction = null;
        private AppOpsManager.RestrictionBypass mAllowSystemRestrictionBypass = null;
        private int mDefaultMode = 3;
        private boolean mDisableReset = false;
        private boolean mRestrictRead = false;
        private boolean mForceCollectNotes = false;

        Builder(int i, String str, String str2) {
            if (i < -1) {
                throw new IllegalArgumentException();
            }
            Objects.requireNonNull(str);
            Objects.requireNonNull(str2);
            this.mCode = i;
            this.mSwitchCode = i;
            this.mName = str;
            this.mSimpleName = str2;
        }

        public Builder setCode(int i) {
            this.mCode = i;
            return this;
        }

        public Builder setSwitchCode(int i) {
            this.mSwitchCode = i;
            return this;
        }

        public Builder setName(String str) {
            this.mName = str;
            return this;
        }

        public Builder setSimpleName(String str) {
            this.mSimpleName = str;
            return this;
        }

        public Builder setPermission(String str) {
            this.mPermission = str;
            return this;
        }

        public Builder setRestriction(String str) {
            this.mRestriction = str;
            return this;
        }

        public Builder setAllowSystemRestrictionBypass(AppOpsManager.RestrictionBypass restrictionBypass) {
            this.mAllowSystemRestrictionBypass = restrictionBypass;
            return this;
        }

        public Builder setDefaultMode(int i) {
            this.mDefaultMode = i;
            return this;
        }

        public Builder setDisableReset(boolean z) {
            this.mDisableReset = z;
            return this;
        }

        public Builder setRestrictRead(boolean z) {
            this.mRestrictRead = z;
            return this;
        }

        public Builder setForceCollectNotes(boolean z) {
            this.mForceCollectNotes = z;
            return this;
        }

        public AppOpInfo build() {
            return new AppOpInfo(this.mCode, this.mSwitchCode, this.mName, this.mSimpleName, this.mPermission, this.mRestriction, this.mAllowSystemRestrictionBypass, this.mDefaultMode, this.mDisableReset, this.mRestrictRead, this.mForceCollectNotes);
        }
    }
}
