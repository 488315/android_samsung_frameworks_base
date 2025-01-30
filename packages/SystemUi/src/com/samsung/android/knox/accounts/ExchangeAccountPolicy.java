package com.samsung.android.knox.accounts;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.accounts.IExchangeAccountPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ExchangeAccountPolicy {
    public static final String ACTION_CBA_INSTALL_STATUS = "com.samsung.android.knox.intent.action.CBA_INSTALL_STATUS";
    public static final String ACTION_CBA_INSTALL_STATUS_INTERNAL = "com.samsung.android.knox.intent.action.CBA_INSTALL_STATUS_INTERNAL";
    public static final String ACTION_CBA_INSTALL_STATUS_OLD = "android.intent.action.sec.CBA_INSTALL_STATUS";
    public static final String ACTION_CREATE_EMAILACCOUNT_INTERNAL = "com.samsung.android.knox.intent.action.CREATE_EMAILACCOUNT_INTERNAL";
    public static final String ACTION_DELETE_EMAILACCOUNT_INTERNAL = "com.samsung.android.knox.intent.action.DELETE_EMAILACCOUNT_INTERNAL";
    public static final String ACTION_DELETE_NOT_VALIDATED_EMAILACCOUNT_INTERNAL = "com.samsung.android.knox.intent.action.DELETE_NOT_VALIDATED_EMAILACCOUNT_INTERNAL";
    public static final String ACTION_EAS_INTENT_INTERNAL = "com.samsung.android.knox.intent.action.EAS_INTENT_INTERNAL";
    public static final String ACTION_EMAIL_DEVICEID_RESULT_NEWEMAIL = "edm.intent.action.internal.GET_DEVICEID_RESULT";
    public static final String ACTION_EMAIL_ENABLE_MSG_COMPOSE_INTERNAL = "com.samsung.android.knox.intent.action.EMAIL_ENABLE_MSG_COMPOSE_INTERNAL";
    public static final String ACTION_EMAIL_INSTALL_CERT_INTERNAL = "com.samsung.android.knox.intent.action.EMAIL_INSTALL_CERT_INTERNAL";
    public static final String ACTION_EMAIL_RENAME_CERTIFICATE_INTERNAL = "com.samsung.android.knox.intent.action.EMAIL_RENAME_CERTIFICATE_INTERNAL";
    public static final String ACTION_ENFORCE_SMIME_ALIAS_EMAIL_INTERNAL = "com.samsung.android.knox.intent.action.ENFORCE_SMIME_ALIAS_EMAIL_INTERNAL";
    public static final String ACTION_ENFORCE_SMIME_ALIAS_INTERNAL = "com.samsung.android.knox.intent.action.ENFORCE_SMIME_ALIAS_INTERNAL";
    public static final String ACTION_ENFORCE_SMIME_ALIAS_RESULT = "com.samsung.android.knox.intent.action.ENFORCE_SMIME_ALIAS_RESULT";
    public static final String ACTION_EXCHANGE_ACCOUNT_ADD_RESULT = "com.samsung.android.knox.intent.action.EXCHANGE_ACCOUNT_ADD_RESULT";
    public static final String ACTION_EXCHANGE_ACCOUNT_DELETE_RESULT = "com.samsung.android.knox.intent.action.EXCHANGE_ACCOUNT_DELETE_RESULT";
    public static final String ACTION_FORCE_SMIME_CERTIFICATE_FOR_ENCRYPTION_INTERNAL = "com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_FOR_ENCRYPTION_INTERNAL";
    public static final String ACTION_FORCE_SMIME_CERTIFICATE_FOR_SIGNING_INTERNAL = "com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_FOR_SIGNING_INTERNAL";
    public static final String ACTION_FORCE_SMIME_CERTIFICATE_INTERNAL = "com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_INTERNAL";
    public static final String ACTION_GET_EMAIL_DEVICEID_REQUEST_INTERNAL = "com.samsung.android.knox.intent.action.GET_EMAIL_DEVICEID_REQUEST_INTERNAL";
    public static final String ACTION_GET_EMAIL_DEVICEID_RESULT_INTERNAL = "com.samsung.android.knox.intent.action.GET_EMAIL_DEVICEID_RESULT_INTERNAL";
    public static final String ACTION_RELEASE_SMIME_CERTIFICATE_FOR_ENCRYPTION_INTERNAL = "com.samsung.android.knox.intent.action.RELEASE_SMIME_CERTIFICATE_FOR_ENCRYPTION_INTERNAL";
    public static final String ACTION_RELEASE_SMIME_CERTIFICATE_FOR_SIGNING_INTERNAL = "com.samsung.android.knox.intent.action.RELEASE_SMIME_CERTIFICATE_FOR_SIGNING_INTERNAL";
    public static final String ACTION_RELEASE_SMIME_CERTIFICATE_INTERNAL = "com.samsung.android.knox.intent.action.RELEASE_SMIME_CERTIFICATE_INTERNAL";
    public static final String ACTION_REQUEST_EMAILACCOUNT_PASSWORD_INTERNAL = "com.samsung.android.knox.intent.action.REQUEST_EMAILACCOUNT_PASSWORD_INTERNAL";
    public static final String ACTION_RESULT_EMAILACCOUNT_PASSWORD_INTERNAL = "com.samsung.android.knox.intent.action.RESULT_EMAILACCOUNT_PASSWORD_INTERNAL";
    public static final String ACTION_SET_EMAILACCOUNT_PASSWORD_INTERNAL = "com.samsung.android.knox.intent.action.SET_EMAILACCOUNT_PASSWORD_INTERNAL";
    public static final int ENFORCE_SMIME_ALIAS_TYPE_ENCRYPT = 0;
    public static final int ENFORCE_SMIME_ALIAS_TYPE_SIGN = 1;
    public static final int ERROR_SET_SMIME_CERTIFICATE_FAIL_BIND_PASSWORD = 4;
    public static final int ERROR_SET_SMIME_CERTIFICATE_NONE = -1;
    public static final int ERROR_SET_SMIME_CERTIFICATE_NOT_FOUND = 1;
    public static final int ERROR_SET_SMIME_CERTIFICATE_UNKNOWN = 0;
    public static final String EXTRA_ACCOUNT_ID = "com.samsung.android.knox.intent.extra.ACCOUNT_ID";
    public static final String EXTRA_CERT_PASSWORD_ID = "cert_password_id";
    public static final String EXTRA_CERT_PASSWORD_ID_INTERNAL = "com.samsung.android.knox.intent.extra.CERT_PASSWORD_ID_INTERNAL";
    public static final String EXTRA_CERT_PASSWORD_INTERNAL = "com.samsung.android.knox.intent.extra.CERT_PASSWORD_INTERNAL";
    public static final String EXTRA_CERT_PATH = "cert_path";
    public static final String EXTRA_CERT_PATH_INTERNAL = "com.samsung.android.knox.intent.extra.CERT_PATH_INTERNAL";
    public static final String EXTRA_CERT_RESULT_ID = "certificate_result_id";
    public static final String EXTRA_CERT_RESULT_ID_INTERNAL = "com.samsung.android.knox.intent.extra.CERT_RESULT_ID_INTERNAL";
    public static final String EXTRA_EMAIL_ADDRESS = "com.samsung.android.knox.intent.extra.EMAIL_ADDRESS";
    public static final String EXTRA_ENFORCE_SMIME_ALIAS_NAME = "com.samsung.edm.intent.extra.ENFORCE_SMIME_ALIAS_NAME";
    public static final String EXTRA_ENFORCE_SMIME_ALIAS_NAME_INTERNAL = "com.samsung.android.knox.intent.extra.ENFORCE_SMIME_ALIAS_NAME_INTERNAL";
    public static final String EXTRA_ENFORCE_SMIME_ALIAS_STORAGE = "com.samsung.edm.intent.extra.ENFORCE_SMIME_ALIAS_STORAGE";
    public static final String EXTRA_ENFORCE_SMIME_ALIAS_STORAGE_INTERNAL = "com.samsung.android.knox.intent.extra.ENFORCE_SMIME_ALIAS_STORAGE_INTERNAL";
    public static final String EXTRA_ENFORCE_SMIME_ALIAS_TYPE = "com.samsung.android.knox.intent.extra.ENFORCE_SMIME_ALIAS_TYPE";
    public static final String EXTRA_RESULT = "com.samsung.android.knox.intent.extra.RESULT";
    public static final String EXTRA_SERVER_ADDRESS = "com.samsung.android.knox.intent.extra.SERVER_ADDRESS";
    public static final String EXTRA_SMIME_INSTALL_TYPE = "smime_type";
    public static final String EXTRA_SMIME_INSTALL_TYPE_INTERNAL = "com.samsung.android.knox.intent.extra.SMIME_INSTALL_TYPE";
    public static final String EXTRA_SMIME_RESULT = "com.samsung.android.knox.intent.extra.SMIME_RESULT";
    public static final String EXTRA_STATUS = "com.samsung.android.knox.intent.extra.STATUS";
    public static final int SET_SMIME_CERTIFICATE_INVALID_ACCOUNT_ID = 3;
    public static final int SET_SMIME_CERTIFICATE_INVALID_PASSWORD = 2;
    public static final int SET_SMIME_CERTIFICATE_NOT_FOUND = 1;
    public static final int SET_SMIME_CERTIFICATE_OK = -1;
    public static final int SET_SMIME_CERTIFICATE_UNKNOWN_ERROR = 0;
    public static String TAG = "ExchangeAccountPolicy";
    public long mAccId;
    public ContextInfo mContextInfo;
    public String mDomain;
    public String mEmailAddress;
    public String mHost;
    public IExchangeAccountPolicy mService;
    public String mUser;

    public ExchangeAccountPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public final long addNewAccount(ExchangeAccount exchangeAccount) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.addNewAccount");
        if ((KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION >= 17 || exchangeAccount == null || (TextUtils.isEmpty(exchangeAccount.certificateAlias) && TextUtils.isEmpty(exchangeAccount.certificateStorageName))) && getService() != null) {
            try {
                return this.mService.addNewAccount_new(this.mContextInfo, exchangeAccount);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with exchange account policy", e);
            }
        }
        return -1L;
    }

    public final boolean allowInComingAttachments(boolean z, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.allowInComingAttachments");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowInComingAttachments(this.mContextInfo, z, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final boolean deleteAccount(long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.deleteAccount");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.deleteAccount(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final String getAccountCertificatePassword(long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.getAccountCertificatePassword", true);
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getAccountCertificatePassword(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed getAccountCertificatePassword()", e);
            return null;
        }
    }

    public final Account getAccountDetails(long j) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getAccountDetails(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return null;
        }
    }

    public final String getAccountEmailPassword(long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.getAccountEmailPassword", true);
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getAccountEmailPassword(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed getAccountEmailPassword()", e);
            return null;
        }
    }

    public final long getAccountId(String str, String str2, String str3) {
        if (getService() == null) {
            return -1L;
        }
        try {
            return this.mService.getAccountId(this.mContextInfo, str, str2, str3);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return -1L;
        }
    }

    public final Account[] getAllEASAccounts() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getAllEASAccounts(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return null;
        }
    }

    public final String getDeviceId() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getDeviceId(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return null;
        }
    }

    public final int getIncomingAttachmentsSize(long j) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getIncomingAttachmentsSize(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return 0;
        }
    }

    public final int getMaxCalendarAgeFilter(long j) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getMaxCalendarAgeFilter(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return 0;
        }
    }

    public final int getMaxEmailAgeFilter(long j) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getMaxEmailAgeFilter(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return 0;
        }
    }

    public final int getMaxEmailBodyTruncationSize(long j) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getMaxEmailBodyTruncationSize(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return 0;
        }
    }

    public final int getMaxEmailHTMLBodyTruncationSize(long j) {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getMaxEmailHTMLBodyTruncationSize(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return 0;
        }
    }

    public final boolean getRequireEncryptedSMIMEMessages(long j) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getRequireEncryptedSMIMEMessages(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final boolean getRequireSignedSMIMEMessages(long j) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getRequireSignedSMIMEMessages(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final String getSMIMECertificateAlias(long j, int i) {
        try {
            if (getService() != null) {
                return this.mService.getSMIMECertificateAlias(this.mContextInfo, j, i);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed getSMIMECertificateAlias() ", e);
            return null;
        }
    }

    public final IExchangeAccountPolicy getService() {
        if (this.mService == null) {
            this.mService = IExchangeAccountPolicy.Stub.asInterface(ServiceManager.getService("eas_account_policy"));
        }
        return this.mService;
    }

    public final boolean isIncomingAttachmentsAllowed(long j) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isIncomingAttachmentsAllowed(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return true;
        }
    }

    public final void removePendingAccount(String str, String str2, String str3, String str4) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.removePendingAccount");
        if (getService() != null) {
            try {
                this.mService.removePendingAccount(this.mContextInfo, str, str2, str3, str4);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with exchange account policy", e);
            }
        }
    }

    public final void sendAccountsChangedBroadcast() {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.sendAccountsChangedBroadcast");
        if (getService() != null) {
            try {
                if (this.mAccId != 0) {
                    Log.i(TAG, "setAccountBaseParameters : " + this.mUser);
                    Log.i(TAG, "setAccountBaseParameters : " + this.mDomain);
                    Log.i(TAG, "setAccountBaseParameters : " + this.mEmailAddress);
                    Log.i(TAG, "setAccountBaseParameters : " + this.mHost);
                    Log.i(TAG, "setAccountBaseParameters : " + this.mAccId);
                    setAccountBaseParameters(this.mUser, this.mDomain, this.mEmailAddress, this.mHost, this.mAccId);
                    this.mHost = null;
                    this.mEmailAddress = null;
                    this.mDomain = null;
                    this.mUser = null;
                    this.mAccId = 0L;
                }
                this.mService.sendAccountsChangedBroadcast(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with exchange account policy", e);
            }
        }
    }

    public final boolean setAcceptAllCertificates(boolean z, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setAcceptAllCertificates");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setAcceptAllCertificates(this.mContextInfo, z, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final long setAccountBaseParameters(String str, String str2, String str3, String str4, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setAccountBaseParameters");
        if (getService() == null) {
            return -1L;
        }
        try {
            return this.mService.setAccountBaseParameters(this.mContextInfo, str, str2, str3, str4, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return -1L;
        }
    }

    public final long setAccountCertificatePassword(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setAccountCertificatePassword");
        if (getService() == null) {
            return -1L;
        }
        try {
            return this.mService.setAccountCertificatePassword(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed setAccountCertificatePassword()", e);
            return -1L;
        }
    }

    public final long setAccountEmailPassword(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setAccountEmailPassword");
        if (getService() == null) {
            return -1L;
        }
        try {
            return this.mService.setAccountEmailPassword(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed setAccountEmailPassword()", e);
            return -1L;
        }
    }

    public final boolean setAccountName(String str, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setAccountName");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setAccountName(this.mContextInfo, str, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final boolean setAlwaysVibrateOnEmailNotification(boolean z, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setAlwaysVibrateOnEmailNotification");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setAlwaysVibrateOnEmailNotification(this.mContextInfo, z, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final boolean setAsDefaultAccount(long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setAsDefaultAccount");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setAsDefaultAccount(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final void setClientAuthCert(byte[] bArr, String str, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setClientAuthCert");
        if (getService() != null) {
            try {
                this.mService.setClientAuthCert(this.mContextInfo, bArr, str, j);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with exchange account policy", e);
            }
        }
    }

    public final boolean setDataSyncs(boolean z, boolean z2, boolean z3, boolean z4, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setDataSyncs");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setDataSyncs(this.mContextInfo, z, z2, z3, z4, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final boolean setForceSMIMECertificateAlias(long j, String str, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setForceSMIMECertificateAlias(long, String, int)");
        try {
            if (getService() != null) {
                return this.mService.setForceSMIMECertificateAlias(this.mContextInfo, j, null, str, i);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed setForceSMIMECertificateAlias() ", e);
            return false;
        }
    }

    public final boolean setIncomingAttachmentsSize(int i, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setIncomingAttachmentsSize");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setIncomingAttachmentsSize(this.mContextInfo, i, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final boolean setMaxCalendarAgeFilter(int i, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setMaxCalendarAgeFilter");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setMaxCalendarAgeFilter(this.mContextInfo, i, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final boolean setMaxEmailAgeFilter(int i, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setMaxEmailAgeFilter");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setMaxEmailAgeFilter(this.mContextInfo, i, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final boolean setMaxEmailBodyTruncationSize(int i, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setMaxEmailBodyTruncationSize");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setMaxEmailBodyTruncationSize(this.mContextInfo, i, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final boolean setMaxEmailHTMLBodyTruncationSize(int i, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setMaxEmailHTMLBodyTruncationSize");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setMaxEmailHTMLBodyTruncationSize(this.mContextInfo, i, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final boolean setPassword(String str, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setPassword");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setPassword(this.mContextInfo, str, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final boolean setPastDaysToSync(int i, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setPastDaysToSync");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setPastDaysToSync(this.mContextInfo, i, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final boolean setRequireEncryptedSMIMEMessages(long j, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setRequireEncryptedSMIMEMessages");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setRequireEncryptedSMIMEMessages(this.mContextInfo, j, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final boolean setRequireSignedSMIMEMessages(long j, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setRequireSignedSMIMEMessages");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setRequireSignedSMIMEMessages(this.mContextInfo, j, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final boolean setSSL(boolean z, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setSSL");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setSSL(this.mContextInfo, z, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final boolean setSignature(String str, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setSignature");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setSignature(this.mContextInfo, str, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final boolean setSyncPeakTimings(int i, int i2, int i3, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setSyncPeakTimings");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setSyncPeakTimings(this.mContextInfo, i, i2, i3, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final boolean setSyncSchedules(int i, int i2, int i3, long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setSyncSchedules");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setSyncSchedules(this.mContextInfo, i, i2, i3, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with exchange account policy", e);
            return false;
        }
    }

    public final boolean setForceSMIMECertificateAlias(long j, String str, String str2, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ExchangeAccountPolicy.setForceSMIMECertificateAlias(long, String, String, int)");
        if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 16) {
            return false;
        }
        try {
            if (getService() != null) {
                return this.mService.setForceSMIMECertificateAlias(this.mContextInfo, j, str, str2, i);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed setForceSMIMECertificateAlias() ", e);
            return false;
        }
    }
}
