package android.security.identity;

import java.security.cert.X509Certificate;

public class AccessControlProfile {
    private AccessControlProfileId mAccessControlProfileId;
    private X509Certificate mReaderCertificate;
    private boolean mUserAuthenticationRequired;
    private long mUserAuthenticationTimeout;

    private AccessControlProfile() {
        this.mAccessControlProfileId = new AccessControlProfileId(0);
        this.mReaderCertificate = null;
        this.mUserAuthenticationRequired = true;
        this.mUserAuthenticationTimeout = 0L;
    }

    AccessControlProfileId getAccessControlProfileId() {
        return this.mAccessControlProfileId;
    }

    long getUserAuthenticationTimeout() {
        return this.mUserAuthenticationTimeout;
    }

    boolean isUserAuthenticationRequired() {
        return this.mUserAuthenticationRequired;
    }

    X509Certificate getReaderCertificate() {
        return this.mReaderCertificate;
    }

    public static final class Builder {
        private AccessControlProfile mProfile = new AccessControlProfile();

        public Builder(AccessControlProfileId accessControlProfileId) {
            this.mProfile.mAccessControlProfileId = accessControlProfileId;
        }

        public Builder setUserAuthenticationRequired(boolean userAuthenticationRequired) {
            this.mProfile.mUserAuthenticationRequired = userAuthenticationRequired;
            return this;
        }

        public Builder setUserAuthenticationTimeout(long userAuthenticationTimeoutMillis) {
            this.mProfile.mUserAuthenticationTimeout = userAuthenticationTimeoutMillis;
            return this;
        }

        public Builder setReaderCertificate(X509Certificate readerCertificate) {
            this.mProfile.mReaderCertificate = readerCertificate;
            return this;
        }

        public AccessControlProfile build() {
            return this.mProfile;
        }
    }
}
