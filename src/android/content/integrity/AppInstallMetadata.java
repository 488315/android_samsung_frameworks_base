package android.content.integrity;

import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes.dex */
public final class AppInstallMetadata {
    private final Map<String, String> mAllowedInstallersAndCertificates;
    private final List<String> mAppCertificateLineage;
    private final List<String> mAppCertificates;
    private final List<String> mInstallerCertificates;
    private final String mInstallerName;
    private final boolean mIsPreInstalled;
    private final boolean mIsStampPresent;
    private final boolean mIsStampTrusted;
    private final boolean mIsStampVerified;
    private final String mPackageName;
    private final String mStampCertificateHash;
    private final long mVersionCode;

    private AppInstallMetadata(Builder builder) {
        this.mPackageName = builder.mPackageName;
        this.mAppCertificates = builder.mAppCertificates;
        this.mAppCertificateLineage = builder.mAppCertificateLineage;
        this.mInstallerName = builder.mInstallerName;
        this.mInstallerCertificates = builder.mInstallerCertificates;
        this.mVersionCode = builder.mVersionCode;
        this.mIsPreInstalled = builder.mIsPreInstalled;
        this.mIsStampPresent = builder.mIsStampPresent;
        this.mIsStampVerified = builder.mIsStampVerified;
        this.mIsStampTrusted = builder.mIsStampTrusted;
        this.mStampCertificateHash = builder.mStampCertificateHash;
        this.mAllowedInstallersAndCertificates = builder.mAllowedInstallersAndCertificates;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public List<String> getAppCertificates() {
        return this.mAppCertificates;
    }

    public List<String> getAppCertificateLineage() {
        return this.mAppCertificateLineage;
    }

    public String getInstallerName() {
        return this.mInstallerName;
    }

    public List<String> getInstallerCertificates() {
        return this.mInstallerCertificates;
    }

    public long getVersionCode() {
        return this.mVersionCode;
    }

    public boolean isPreInstalled() {
        return this.mIsPreInstalled;
    }

    public boolean isStampPresent() {
        return this.mIsStampPresent;
    }

    public boolean isStampVerified() {
        return this.mIsStampVerified;
    }

    public boolean isStampTrusted() {
        return this.mIsStampTrusted;
    }

    public String getStampCertificateHash() {
        return this.mStampCertificateHash;
    }

    public Map<String, String> getAllowedInstallersAndCertificates() {
        return this.mAllowedInstallersAndCertificates;
    }

    public String toString() {
        Object obj;
        Long l;
        Boolean bool;
        Boolean bool2;
        Boolean bool3;
        Boolean bool4;
        String str;
        String str2 = this.mPackageName;
        List<String> list = this.mAppCertificates;
        List<String> list2 = this.mAppCertificateLineage;
        String str3 = this.mInstallerName;
        if (str3 == null) {
            str3 = PerfettoProtoLogImpl.NULL_STRING;
        }
        Object obj2 = this.mInstallerCertificates;
        if (obj2 == null) {
            obj2 = PerfettoProtoLogImpl.NULL_STRING;
        }
        Long lValueOf = Long.valueOf(this.mVersionCode);
        Boolean boolValueOf = Boolean.valueOf(this.mIsPreInstalled);
        Boolean boolValueOf2 = Boolean.valueOf(this.mIsStampPresent);
        Boolean boolValueOf3 = Boolean.valueOf(this.mIsStampVerified);
        Boolean boolValueOf4 = Boolean.valueOf(this.mIsStampTrusted);
        String str4 = this.mStampCertificateHash;
        if (str4 == null) {
            str = PerfettoProtoLogImpl.NULL_STRING;
            obj = obj2;
            l = lValueOf;
            bool = boolValueOf;
            bool2 = boolValueOf2;
            bool3 = boolValueOf3;
            bool4 = boolValueOf4;
        } else {
            obj = obj2;
            l = lValueOf;
            bool = boolValueOf;
            bool2 = boolValueOf2;
            bool3 = boolValueOf3;
            bool4 = boolValueOf4;
            str = str4;
        }
        return String.format("AppInstallMetadata { PackageName = %s, AppCerts = %s, AppCertsLineage = %s, InstallerName = %s, InstallerCerts = %s, VersionCode = %d, PreInstalled = %b, StampPresent = %b, StampVerified = %b, StampTrusted = %b, StampCert = %s }", str2, list, list2, str3, obj, l, bool, bool2, bool3, bool4, str);
    }

    public static final class Builder {
        private Map<String, String> mAllowedInstallersAndCertificates = new HashMap();
        private List<String> mAppCertificateLineage;
        private List<String> mAppCertificates;
        private List<String> mInstallerCertificates;
        private String mInstallerName;
        private boolean mIsPreInstalled;
        private boolean mIsStampPresent;
        private boolean mIsStampTrusted;
        private boolean mIsStampVerified;
        private String mPackageName;
        private String mStampCertificateHash;
        private long mVersionCode;

        public Builder setAllowedInstallersAndCert(Map<String, String> map) {
            this.mAllowedInstallersAndCertificates = map;
            return this;
        }

        public Builder setPackageName(String str) {
            this.mPackageName = (String) Objects.requireNonNull(str);
            return this;
        }

        public Builder setAppCertificates(List<String> list) {
            this.mAppCertificates = (List) Objects.requireNonNull(list);
            return this;
        }

        public Builder setAppCertificateLineage(List<String> list) {
            this.mAppCertificateLineage = (List) Objects.requireNonNull(list);
            return this;
        }

        public Builder setInstallerName(String str) {
            this.mInstallerName = (String) Objects.requireNonNull(str);
            return this;
        }

        public Builder setInstallerCertificates(List<String> list) {
            this.mInstallerCertificates = (List) Objects.requireNonNull(list);
            return this;
        }

        public Builder setVersionCode(long j) {
            this.mVersionCode = j;
            return this;
        }

        public Builder setIsPreInstalled(boolean z) {
            this.mIsPreInstalled = z;
            return this;
        }

        public Builder setIsStampPresent(boolean z) {
            this.mIsStampPresent = z;
            return this;
        }

        public Builder setIsStampVerified(boolean z) {
            this.mIsStampVerified = z;
            return this;
        }

        public Builder setIsStampTrusted(boolean z) {
            this.mIsStampTrusted = z;
            return this;
        }

        public Builder setStampCertificateHash(String str) {
            this.mStampCertificateHash = (String) Objects.requireNonNull(str);
            return this;
        }

        public AppInstallMetadata build() {
            Objects.requireNonNull(this.mPackageName);
            Objects.requireNonNull(this.mAppCertificates);
            Objects.requireNonNull(this.mAppCertificateLineage);
            return new AppInstallMetadata(this);
        }
    }
}
