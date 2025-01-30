package com.samsung.android.knox.container;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AuthenticationConfig implements Parcelable {
    public static String AUTHENTICATION_STATUS = "auth_status";
    public static final String AUTHENTICATOR_PACKAGE_NAME = "servicepackagename";
    public static final String AUTHENTICATOR_PACKAGE_SIGNATURE = "servicepackagesignature";
    public static final String BIND_ACTION_AUTHENTICATOR = ".genericssoconnection";
    public static final int CHANGE_PASSWORD_ENTERPRISE_IDENTITY = 4;
    public static final Parcelable.Creator<AuthenticationConfig> CREATOR = new Parcelable.Creator<AuthenticationConfig>() { // from class: com.samsung.android.knox.container.AuthenticationConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final AuthenticationConfig createFromParcel(Parcel parcel) {
            return new AuthenticationConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final AuthenticationConfig[] newArray(int i) {
            return new AuthenticationConfig[i];
        }

        @Override // android.os.Parcelable.Creator
        public final AuthenticationConfig createFromParcel(Parcel parcel) {
            return new AuthenticationConfig(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final AuthenticationConfig[] newArray(int i) {
            return new AuthenticationConfig[i];
        }
    };
    public static final String ENFORCE_REMOTE_AUTH_ALWAYS = "enforceRemoteAuthAlways";
    public static String ENTERPRISEID_OLD_PASSWORD = "enterprise_old_password";
    public static String ENTERPRISEID_PASSWORD = "enterprise_password";
    public static String ENTERPRISEID_USERNAME = "enterprise_username";
    public static final int ERROR_AUTHENTICATOR_PACKAGE_NOT_INSTALLED = -4;
    public static final int ERROR_AUTHENTICATOR_SIGNATURE_MISMATCH = -13;
    public static final int ERROR_INTERNAL_FAIL = -1;
    public static final int ERROR_INVALID_INPUT = -3;
    public static final int ERROR_NETWORK_NOT_AVAILABLE = -15;
    public static final int ERROR_PASSWORD_EXPIRED = -18;
    public static final int ERROR_REMOTE_PROCESSING = -16;
    public static final int ERROR_USER_CANCELLED = -17;
    public static final int ERROR_USER_NOT_AUTHORIZED = -2;
    public static final String FORCE_ENTERPRISE_IDENTITY_LOCK = "forceEnterpriseIDLock";
    public static final String HIDE_ENTERPRISE_ID_LOCK = "hideEnterpriseIDLock";
    public static String OPERATION_MODE = "operation_mode";
    public static final String SAMSUNG_KERBEROS_AUTHENTICATOR = "com.sec.android.service.singlesignon";
    public static final int SETUP_ENTERPRISE_IDENTITY = 2;
    public static final int SUCCESS = 0;
    public static final int UNLOCK_ENTERPRISE_IDENTITY = 3;
    public Bundle authenticatorConfig;
    public String authenticatorPkgName;
    public String authenticatorPkgSignature;
    public boolean enforceEnterpriseIdentityLock;
    public boolean enforceRemoteAuthAlways;
    public boolean hideEnterpriseIdentityLock;
    public boolean isConfiguredByMDM;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class AuthenticationRequestKeys {
        public static final String ADMIN_SERVER = "REALMS_ADMIN_SERVER";
        public static final String CANONICALIZE = "LIBDEFAULTS_CANONICALIZE";
        public static final String DEFAULT_DOMAIN = "REALMS_DEFAULT_DOMAIN";
        public static final String DEFAULT_REALM = "LIBDEFAULTS_DEFAULT_REALM";
        public static final String DNS_CANONICALIZE_HOSTNAME = "LIBDEFAULTS_DNS_CANONICALIZE_HOSTNAME";
        public static final String DNS_LOOKUP_KDC = "LIBDEFAULTS_DNS_LOOKUP_KDC";
        public static final String DNS_LOOKUP_REALM = "LIBDEFAULTS_DNS_LOOKUP_REALM";
        public static final String FEDERATION_SERVER_URL = "FEDERATION_SERVER_URL";
        public static final String FORWARDABLE = "LIBDEFAULTS_FORWARDABLE";
        public static final String KDC = "REALMS_KDC";
        public static final String KPASSWD_SERVER = "REALMS_KPASSWD_SERVER";
        public static final String KRB5_CONFIG_DATA = "KRB5_CONFIG_DATA";
        public static final String MASTER_KDC = "REALMS_MASTER_KDC";
        public static final String NOADDRESSES = "LIBDEFAULTS_NOADDRESSES";
        public static final String PKINIT_ANCHORS = "REALMS_PKINIT_ANCHORS";
        public static final String PKINIT_IDENTITIES = "REALMS_PKINIT_IDENTITIES";
        public static final String RDNS = "LIBDEFAULTS_RDNS";
        public static final String RENEW_LIFETIME = "LIBDEFAULTS_RENEW_LIFETIME";
        public static final String TICKET_LIFETIME = "LIBDEFAULTS_TICKET_LIFETIME";
        public static final String UDP_PREFERENCE_LIMIT = "LIBDEFAULTS_UDP_PREFERENCE_LIMIT";
    }

    public AuthenticationConfig(boolean z, boolean z2, boolean z3, String str, String str2) {
        this.hideEnterpriseIdentityLock = z3;
        this.enforceEnterpriseIdentityLock = z2;
        this.enforceRemoteAuthAlways = z;
        this.authenticatorPkgName = str;
        this.authenticatorPkgSignature = str2;
        this.authenticatorConfig = null;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final Bundle getAuthenticatorConfig() {
        return this.authenticatorConfig;
    }

    public final String getAuthenticatorPkgName() {
        return this.authenticatorPkgName;
    }

    public final String getAuthenticatorPkgSignature() {
        return this.authenticatorPkgSignature;
    }

    public final boolean getEnforceEnterpriseIdentityLock() {
        return this.enforceEnterpriseIdentityLock;
    }

    public final boolean getEnforceRemoteAuthAlways() {
        return this.enforceRemoteAuthAlways;
    }

    public final boolean getHideEnterpriseIdentityLock() {
        return this.hideEnterpriseIdentityLock;
    }

    public final boolean isConfiguredByMDM() {
        return this.isConfiguredByMDM;
    }

    public final void readFromParcel(Parcel parcel) {
        try {
            boolean[] zArr = new boolean[4];
            parcel.readBooleanArray(zArr);
            this.enforceRemoteAuthAlways = zArr[0];
            this.enforceEnterpriseIdentityLock = zArr[1];
            this.hideEnterpriseIdentityLock = zArr[2];
            this.isConfiguredByMDM = zArr[3];
            this.authenticatorPkgName = parcel.readString();
            this.authenticatorPkgSignature = parcel.readString();
            this.authenticatorConfig = parcel.readBundle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void setAuthenticatorConfig(Bundle bundle) {
        if (this.authenticatorConfig != null) {
            this.authenticatorConfig = null;
        }
        this.authenticatorConfig = bundle;
    }

    public final void setAuthenticatorPkgName(String str) {
        this.authenticatorPkgName = str;
    }

    public final void setAuthenticatorPkgSignature(String str) {
        this.authenticatorPkgSignature = str;
    }

    public final void setConfiguredByMDM(boolean z) {
        this.isConfiguredByMDM = z;
    }

    public final void setEnforceRemoteAuthAlways(boolean z) {
        this.enforceRemoteAuthAlways = z;
    }

    public final void setForceEnterpriseIdentityLock(boolean z) {
        this.enforceEnterpriseIdentityLock = z;
    }

    public final void setHideEnterpriseIdentityLock(boolean z) {
        this.hideEnterpriseIdentityLock = z;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        try {
            parcel.writeBooleanArray(new boolean[]{this.enforceRemoteAuthAlways, this.enforceEnterpriseIdentityLock, this.hideEnterpriseIdentityLock, this.isConfiguredByMDM});
            parcel.writeString(this.authenticatorPkgName);
            parcel.writeString(this.authenticatorPkgSignature);
            parcel.writeBundle(this.authenticatorConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AuthenticationConfig(boolean z, boolean z2, boolean z3, boolean z4, String str, String str2) {
        this.hideEnterpriseIdentityLock = z3;
        this.enforceEnterpriseIdentityLock = z2;
        this.enforceRemoteAuthAlways = z;
        this.isConfiguredByMDM = z4;
        this.authenticatorPkgName = str;
        this.authenticatorPkgSignature = str2;
        this.authenticatorConfig = null;
    }

    public AuthenticationConfig(boolean z, boolean z2, boolean z3, String str, String str2, Bundle bundle) {
        this.hideEnterpriseIdentityLock = z3;
        this.enforceEnterpriseIdentityLock = z2;
        this.enforceRemoteAuthAlways = z;
        this.authenticatorPkgName = str;
        this.authenticatorPkgSignature = str2;
        this.authenticatorConfig = bundle;
    }

    public AuthenticationConfig(boolean z, boolean z2, boolean z3, boolean z4, String str, String str2, Bundle bundle) {
        this.hideEnterpriseIdentityLock = z3;
        this.enforceEnterpriseIdentityLock = z2;
        this.enforceRemoteAuthAlways = z;
        this.isConfiguredByMDM = z4;
        this.authenticatorPkgName = str;
        this.authenticatorPkgSignature = str2;
        this.authenticatorConfig = bundle;
    }

    public AuthenticationConfig() {
        this.hideEnterpriseIdentityLock = false;
        this.enforceEnterpriseIdentityLock = false;
        this.enforceRemoteAuthAlways = false;
        this.isConfiguredByMDM = false;
    }

    public AuthenticationConfig(Parcel parcel) {
        readFromParcel(parcel);
    }
}
