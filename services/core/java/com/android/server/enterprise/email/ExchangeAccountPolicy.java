package com.android.server.enterprise.email;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.sec.enterprise.email.EnterpriseExchangeAccount;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import com.android.server.SystemService;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
import com.android.server.enterprise.ucm.UniversalCredentialManagerService;
import com.android.server.enterprise.utils.Utils;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.accounts.Account;
import com.samsung.android.knox.accounts.ExchangeAccount;
import com.samsung.android.knox.accounts.IEmailPolicy;
import com.samsung.android.knox.accounts.IExchangeAccountPolicy;
import com.samsung.android.knox.ucm.configurator.CredentialStorage;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class ExchangeAccountPolicy extends IExchangeAccountPolicy.Stub implements EnterpriseServiceCallback {
    public static HashMap mDeviceId = new HashMap();
    public Context mContext;
    public EdmStorageProvider mEdmStorageProvider;
    public SMIMEIntentReceiver mSMIMEIntentReceiver;
    public boolean mExchangeServiceDisabled = false;
    public boolean mRestartExtendDelay = false;
    public int preCallingUid = -1;
    public UniversalCredentialManagerService mUCMService = null;
    public EnterpriseDeviceManager mEDM = null;
    public Handler mHandler = new Handler() { // from class: com.android.server.enterprise.email.ExchangeAccountPolicy.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Log.i("ExchangeAccountPolicy", "Handler : message = " + message.what);
            if (message.what != 1) {
                return;
            }
            int i = message.arg1;
            int i2 = message.arg2;
            if (ExchangeAccountPolicy.this.mExchangeServiceDisabled) {
                PackageManager packageManager = ExchangeAccountPolicy.this.mContext.getPackageManager();
                ComponentName componentName = new ComponentName(SettingsUtils.getEasPackageName(i), ExchangeAccountPolicy.getExchangeServiceName(i));
                Log.i("ExchangeAccountPolicy", "Handler / RESTART_EMAIL_APP : Enabling EAS ExchangeService user " + i2);
                packageManager.setComponentEnabledSetting(componentName, 1, 0);
                ExchangeAccountPolicy.this.mExchangeServiceDisabled = false;
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        ExchangeAccountPolicy.this.mContext.sendBroadcastAsUser(new Intent("com.samsung.android.knox.intent.action.EAS_INTENT_INTERNAL"), new UserHandle(i2), "com.samsung.android.knox.permission.KNOX_EMAIL");
                    } catch (Exception e) {
                        Log.e("ExchangeAccountPolicy", "Handler / RESTART_EMAIL_APP : fail to restart ExchangeService. ", e);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            ExchangeAccountPolicy.this.mRestartExtendDelay = false;
        }
    };

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
    }

    public ExchangeAccountPolicy(Context context) {
        this.mSMIMEIntentReceiver = null;
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        try {
            IntentFilter intentFilter = new IntentFilter();
            this.mSMIMEIntentReceiver = new SMIMEIntentReceiver();
            intentFilter.addAction("com.samsung.edm.intent.action.EXCHANGE_SMIME_INSTALL_STATUS");
            intentFilter.addAction("com.samsung.android.knox.intent.action.ENFORCE_SMIME_ALIAS_EMAIL_INTERNAL");
            this.mContext.registerReceiverAsUser(this.mSMIMEIntentReceiver, UserHandle.ALL, intentFilter, "com.samsung.android.knox.permission.SMIME_CERTIFICATE_INTERNAL", null);
            Log.i("ExchangeAccountPolicy", "success to add receiver");
        } catch (Exception e) {
            Log.e("ExchangeAccountPolicy", "Regist BroadCast failed : ", e);
        }
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            ComponentName componentName = new ComponentName(SettingsUtils.getEasPackageName(0), getExchangeServiceName(0));
            if (packageManager == null || packageManager.getComponentEnabledSetting(componentName) != 2) {
                return;
            }
            Log.i("ExchangeAccountPolicy", "Enabling EAS ExchangeService");
            packageManager.setComponentEnabledSetting(componentName, 1, 0);
        } catch (Exception e2) {
            Log.e("ExchangeAccountPolicy", "Constructor failed : ", e2);
        }
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final ContextInfo enforceExchangeAccountPermission(ContextInfo contextInfo) {
        ContextInfo enforceActiveAdminPermissionByContext = getEDM().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_EXCHANGE")));
        int i = enforceActiveAdminPermissionByContext.mCallerUid;
        if (this.preCallingUid != i) {
            String packageNameForUid = this.mEdmStorageProvider.getPackageNameForUid(i);
            SettingsUtils.setPackageNameForUid(i, packageNameForUid);
            this.preCallingUid = i;
            Slog.d("ExchangeAccountPolicy", "Calling UID changed : " + packageNameForUid + " callingUid " + i);
        }
        return enforceActiveAdminPermissionByContext;
    }

    public final void enforceUCMPermission(ContextInfo contextInfo, String str) {
        CredentialStorage[] availableCredentialStorages;
        if (TextUtils.isEmpty(str) || getUCMService() == null || (availableCredentialStorages = this.mUCMService.getAvailableCredentialStorages(contextInfo)) == null) {
            return;
        }
        for (CredentialStorage credentialStorage : availableCredentialStorages) {
            if (credentialStorage != null && str.equals(credentialStorage.name)) {
                this.mUCMService.enforceSecurityPermission(contextInfo, credentialStorage);
                return;
            }
        }
    }

    public long createAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4, String str5) {
        Slog.d("ExchangeAccountPolicy", "addNewAccount() EX 1");
        return addNewAccount(contextInfo, null, str, str2, str3, 1, -1, false, null, "2.5", null, false, false, str4, true, false, true, str5, null);
    }

    public long addNewAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i, int i2, boolean z, String str5, String str6, String str7, boolean z2, boolean z3, String str8, boolean z4, boolean z5, boolean z6, String str9, String str10) {
        Slog.d("ExchangeAccountPolicy", "addNewAccount() EX 2");
        return addNewAccount_ex(contextInfo, str, str2, str3, str4, i, i2, z, str5, str6, str7, z2, z3, str8, z4, z5, z6, str9, str10, SystemService.PHASE_LOCK_SETTINGS_READY, 1020, 62, -2, 0, 3, 4, true, 1, 1, null, null);
    }

    public long addNewAccount_ex(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i, int i2, boolean z, String str5, String str6, String str7, boolean z2, boolean z3, String str8, boolean z4, boolean z5, boolean z6, String str9, String str10, int i3, int i4, int i5, int i6, int i7, int i8, int i9, boolean z7, int i10, int i11, byte[] bArr, String str11) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", ">>>>>>>>>>>>>>>>>\t\taddNewAccount EX ");
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission);
        int i12 = enforceExchangeAccountPermission.mContainerId;
        String validStr = SettingsUtils.getValidStr(str);
        String validStr2 = SettingsUtils.getValidStr(str2);
        String validStr3 = SettingsUtils.getValidStr(str8);
        String validStr4 = SettingsUtils.getValidStr(str6);
        SettingsUtils.getValidStr(str5);
        String validStr5 = SettingsUtils.getValidStr(str7);
        String validStr6 = SettingsUtils.getValidStr(str3);
        String validStr7 = SettingsUtils.getValidStr(str4);
        String validStr8 = SettingsUtils.getValidStr(str9);
        String validStr9 = SettingsUtils.getValidStr(str10);
        if (validStr2 == null || !SettingsUtils.isValidEmailAddress(validStr2) || validStr3 == null || validStr4 == null || validStr6 == null) {
            Log.i("ExchangeAccountPolicy", "addNewAccount() EX : Error :: Invalid input parameters.");
            return -1L;
        }
        if (!SettingsUtils.isPackageInstalled(SettingsUtils.getEmailPackageName(callingOrCurrentUserId), i12) && !SettingsUtils.isPackageInstalled("com.samsung.android.focus", i12)) {
            Log.i("ExchangeAccountPolicy", "addNewAccount() EX : Error :: Email and Focus are not installed on user " + i12);
            return -1L;
        }
        if (true == z2 && true == z3) {
            Log.i("ExchangeAccountPolicy", "addNewAccount() EX : Error :: Invalid input parameters. 'emailNotificationVibrateAlways' and 'emailNotificationVibrateWhenSilent' only one will be true at a time");
            return -1L;
        }
        if (SettingsUtils.getAccountId(enforceExchangeAccountPermission, validStr7, validStr6, validStr3, "eas", true, this.mContext) > 0) {
            Log.i("ExchangeAccountPolicy", "addNewAccount() EX : Error :: Account already exists.");
            return -1L;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Log.i("ExchangeAccountPolicy", "addNewAccount() EX : " + callingOrCurrentUserId);
                long accountEmailPassword = setAccountEmailPassword(enforceExchangeAccountPermission, validStr8);
                long accountCertificatePassword = setAccountCertificatePassword(enforceExchangeAccountPermission, str11);
                Intent intent = new Intent("com.samsung.android.knox.intent.action.CREATE_EMAILACCOUNT_INTERNAL");
                intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL", accountEmailPassword);
                intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", validStr2);
                intent.putExtra("com.samsung.android.knox.intent.extra.SERVICE_INTERNAL", "eas");
                intent.putExtra("com.samsung.android.knox.intent.extra.SERVICE_NAME_INTERNAL", validStr3);
                intent.putExtra("com.samsung.android.knox.intent.extra.EXTRA_USER_NAME_INTERNAL", validStr6);
                intent.putExtra("com.samsung.android.knox.intent.extra.USER_PASSWD_ID_INTERNAL", accountEmailPassword);
                intent.putExtra("com.samsung.android.knox.intent.extra.SERVER_PATH_PREFIX_INTERNAL", validStr9);
                intent.putExtra("com.samsung.android.knox.intent.extra.USE_SSL_INTERNAL", z4 ? 1 : 0);
                intent.putExtra("com.samsung.android.knox.intent.extra.USE_TLS_INTERNAL", z5 ? 1 : 0);
                intent.putExtra("com.samsung.android.knox.intent.extra.TRUST_ALL_INTERNAL", z6 ? 1 : 0);
                intent.putExtra("com.samsung.android.knox.intent.extra.DOMAIN_INTERNAL", validStr7);
                intent.putExtra("com.samsung.android.knox.intent.extra.PEAK_START_TIME_INTERNAL", i3);
                intent.putExtra("com.samsung.android.knox.intent.extra.PEAK_END_TIME_INTERNAL", i4);
                intent.putExtra("com.samsung.android.knox.intent.extra.PEAK_DAYS_INTERNAL", i5);
                intent.putExtra("com.samsung.android.knox.intent.extra.PEAK_INTERNAL", i2);
                intent.putExtra("com.samsung.android.knox.intent.extra.OFF_PEAK_INTERNAL", i6);
                intent.putExtra("com.samsung.android.knox.intent.extra.ROAMING_SCHEDULE_INTERNAL", i7);
                intent.putExtra("com.samsung.android.knox.intent.extra.PERIOD_EMAIL_INTERNAL", i);
                intent.putExtra("com.samsung.android.knox.intent.extra.", i8);
                intent.putExtra("com.samsung.android.knox.intent.extra.PERIOD_CALENDAR_INTERNAL", i9);
                intent.putExtra("com.samsung.android.knox.intent.extra.NOTIFY_INTERNAL", z7);
                intent.putExtra("com.samsung.android.knox.intent.extra.SYNC_CONTACTS_INTERNAL", i10);
                intent.putExtra("com.samsung.android.knox.intent.extra.SYNC_CALENDAR_INTERNAL", i11);
                intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_NAME_INTERNAL", validStr);
                intent.putExtra("com.samsung.android.knox.intent.extra.SIGNATURE_INTERNAL", validStr5);
                intent.putExtra("com.samsung.android.knox.intent.extra.VIBRATE_WHEN_SILENT_INTERNAL", z3);
                intent.putExtra("com.samsung.android.knox.intent.extra.EXTRA_VIBRATE_INTERNAL", z2);
                intent.putExtra("com.samsung.android.knox.intent.extra.IS_DEFAULT_INTERNAL", z);
                intent.putExtra("com.samsung.android.knox.intent.extra.CERTIFICATE_DATA_INTERNAL", bArr);
                intent.putExtra("com.samsung.android.knox.intent.extra.CERTIFICATE_PASSWD_ID_INTERNAL", accountCertificatePassword);
                intent.setPackage(SettingsUtils.getEmailPackageName(i12));
                this.mContext.sendBroadcastAsUser(intent, new UserHandle(callingOrCurrentUserId), "com.samsung.android.knox.permission.KNOX_EMAIL");
                intent.setPackage("com.samsung.android.focus");
                this.mContext.sendBroadcastAsUser(intent, new UserHandle(callingOrCurrentUserId), "com.samsung.android.knox.permission.KNOX_EMAIL");
                Slog.d("ExchangeAccountPolicy", "addNewAccount() EX : sent intent to Email app : " + validStr2);
            } catch (Exception e) {
                Log.e("ExchangeAccountPolicy", "addNewAccount() EX : failed. ", e);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.i("ExchangeAccountPolicy", "<<<<<<<<<<<<<<<<<\t\taddNewAccount EX : Broadcasting Email");
            return 0L;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean setSSL(ContextInfo contextInfo, boolean z, long j) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setSSL() : " + z);
        EnterpriseExchangeAccount enterpriseExchangeAccount = EmailProviderHelper.getEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, j);
        if (enterpriseExchangeAccount == null) {
            Log.i("ExchangeAccountPolicy", "setSSL_new() : No exist accId : " + j);
            return false;
        }
        enterpriseExchangeAccount.mUseSSL = z;
        return EmailProviderHelper.updateEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
    }

    public boolean setAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setAcceptAllCertificates() : " + z);
        EnterpriseExchangeAccount enterpriseExchangeAccount = EmailProviderHelper.getEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, j);
        if (enterpriseExchangeAccount == null) {
            Log.i("ExchangeAccountPolicy", "setAcceptAllCertificates_new() : No exist accId : " + j);
            return false;
        }
        enterpriseExchangeAccount.mAcceptAllCertificates = z;
        return EmailProviderHelper.updateEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
    }

    public boolean setAlwaysVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setAlwaysVibrateOnEmailNotification() : " + z);
        EnterpriseExchangeAccount enterpriseExchangeAccount = EmailProviderHelper.getEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, j);
        if (enterpriseExchangeAccount == null) {
            Log.i("ExchangeAccountPolicy", "setAlwaysVibrateOnEmailNotification_new() : No exist accId : " + j);
            return false;
        }
        enterpriseExchangeAccount.mEmailNotificationVibrateAlways = z;
        return EmailProviderHelper.updateEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
    }

    public boolean setSilentVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) {
        enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setSilentVibrateOnEmailNotification() : deprecated. " + z);
        return false;
    }

    public long setAccountBaseParameters(ContextInfo contextInfo, String str, String str2, String str3, String str4, long j) {
        enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setAccountBaseParameters() : deprecated ");
        return -1L;
    }

    public boolean setPassword(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        int i = enforceExchangeAccountPermission.mContainerId;
        Log.i("ExchangeAccountPolicy", "setPassword() : " + Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission));
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr == null) {
            Log.i("ExchangeAccountPolicy", "setPassword() : Invalid input parameter.");
            return false;
        }
        EnterpriseExchangeAccount enterpriseExchangeAccount = EmailProviderHelper.getEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, j);
        if (enterpriseExchangeAccount == null) {
            Log.i("ExchangeAccountPolicy", "setPassword_new() : No exist accId : " + j);
            return false;
        }
        enterpriseExchangeAccount.mPassword = validStr;
        return EmailProviderHelper.updateEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
    }

    public boolean setProtocolVersion(ContextInfo contextInfo, String str, long j) {
        enforceExchangeAccountPermission(contextInfo);
        return false;
    }

    public boolean setSignature(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setSignature() : " + j);
        int i = enforceExchangeAccountPermission.mContainerId;
        if (str == null) {
            Log.i("ExchangeAccountPolicy", "setSignature() : signature is null");
            return false;
        }
        EnterpriseExchangeAccount enterpriseExchangeAccount = EmailProviderHelper.getEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, j);
        if (enterpriseExchangeAccount == null) {
            Log.i("ExchangeAccountPolicy", "setSignature_new() : No exist accId : " + j);
            return false;
        }
        enterpriseExchangeAccount.mSignature = str;
        return EmailProviderHelper.updateEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
    }

    public void setClientAuthCert(ContextInfo contextInfo, byte[] bArr, String str, long j) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setClientAuthCert() : " + j);
        int i = enforceExchangeAccountPermission.mContainerId;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission);
        if (!isValidEASAccountId(enforceExchangeAccountPermission, j)) {
            Log.i("ExchangeAccountPolicy", "setClientAuthCert() : No exist accId : " + j);
            return;
        }
        if (bArr == null || str == null || j < 1) {
            Log.i("ExchangeAccountPolicy", "setClientAuthCert() : error : " + j);
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mRestartExtendDelay = true;
                this.mHandler.removeMessages(1);
                Intent intent = new Intent("com.samsung.android.knox.intent.action.EMAIL_INSTALL_CERT_INTERNAL");
                long accountCertificatePassword = setAccountCertificatePassword(enforceExchangeAccountPermission, str);
                intent.putExtra("com.samsung.android.knox.intent.extra.CERTIFICATE_DATA_INTERNAL", bArr);
                intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL", j);
                intent.putExtra("com.samsung.android.knox.intent.extra.CERTIFICATE_PASSWD_ID_INTERNAL", accountCertificatePassword);
                this.mContext.sendBroadcastAsUser(intent, new UserHandle(callingOrCurrentUserId), "com.samsung.android.knox.permission.KNOX_EMAIL");
            } catch (Exception e) {
                Log.e("ExchangeAccountPolicy", "setClientAuthCert() : failed", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean setPastDaysToSync(ContextInfo contextInfo, int i, long j) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setPastDaysToSync() : " + j);
        if (1 > i || 6 < i) {
            Log.i("ExchangeAccountPolicy", "setPastDaysToSync : Error :: Invalid input parameter.");
            return false;
        }
        EnterpriseExchangeAccount enterpriseExchangeAccount = EmailProviderHelper.getEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, j);
        if (enterpriseExchangeAccount == null) {
            Log.i("ExchangeAccountPolicy", "setPastDaysToSync_new() : No exist accId : " + j);
            return false;
        }
        enterpriseExchangeAccount.mSyncLookback = i;
        boolean updateEnterpriseExchangeAccount = EmailProviderHelper.updateEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
        Slog.d("ExchangeAccountPolicy", "setPastDaysToSync() = " + updateEnterpriseExchangeAccount + " , pastDaysToSync = " + i + " , accId = " + j);
        return updateEnterpriseExchangeAccount;
    }

    public boolean setSyncInterval(ContextInfo contextInfo, int i, long j) {
        enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setSyncInterval() : deprecated , " + j);
        return false;
    }

    public boolean setSenderName(ContextInfo contextInfo, String str, long j) {
        enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setSenderName() :  deprecated , " + str);
        return false;
    }

    public boolean setAsDefaultAccount(ContextInfo contextInfo, long j) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setAsDefaultAccount() :  " + j);
        EnterpriseExchangeAccount enterpriseExchangeAccount = EmailProviderHelper.getEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, j);
        if (enterpriseExchangeAccount == null) {
            Log.i("ExchangeAccountPolicy", "setAsDefaultAccount_new() : No exist accId : " + j);
            return false;
        }
        enterpriseExchangeAccount.mIsDefault = true;
        boolean updateEnterpriseExchangeAccount = EmailProviderHelper.updateEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
        Slog.d("ExchangeAccountPolicy", "setAsDefaultAccount() = " + updateEnterpriseExchangeAccount + " , accId = " + j);
        return updateEnterpriseExchangeAccount;
    }

    public boolean setAccountName(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setAccountName() : " + j);
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr == null) {
            Log.i("ExchangeAccountPolicy", "setAccountName() : accountName is null");
            return false;
        }
        EnterpriseExchangeAccount enterpriseExchangeAccount = EmailProviderHelper.getEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, j);
        if (enterpriseExchangeAccount == null) {
            Log.i("ExchangeAccountPolicy", "setAccountName_new() : No exist accId : " + j);
            return false;
        }
        enterpriseExchangeAccount.mDisplayName = validStr;
        boolean updateEnterpriseExchangeAccount = EmailProviderHelper.updateEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
        Slog.d("ExchangeAccountPolicy", "setAccountName() = " + updateEnterpriseExchangeAccount + ", accountName =" + validStr + " , accId = " + j);
        return updateEnterpriseExchangeAccount;
    }

    public boolean setSyncPeakTimings(ContextInfo contextInfo, int i, int i2, int i3, long j) {
        Log.i("ExchangeAccountPolicy", "setSyncPeakTimings() : " + j);
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        if (i < 0 || i > 127) {
            Log.i("ExchangeAccountPolicy", "setSyncPeakTimings() : peakDays is invalid");
            return false;
        }
        if (i2 < 0 || i2 > 1440) {
            Log.i("ExchangeAccountPolicy", "setSyncPeakTimings() : peakStartMinute is invalid");
            return false;
        }
        if (i3 < 0 || i3 > 1440) {
            Log.i("ExchangeAccountPolicy", "setSyncPeakTimings() : peakEndMinute is invalid");
            return false;
        }
        EnterpriseExchangeAccount enterpriseExchangeAccount = EmailProviderHelper.getEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, j);
        if (enterpriseExchangeAccount == null) {
            Log.i("ExchangeAccountPolicy", "setSSL_new() : No exist accId : " + j);
            return false;
        }
        enterpriseExchangeAccount.mPeakDays = i;
        enterpriseExchangeAccount.mPeakStartMinute = i2;
        enterpriseExchangeAccount.mPeakEndMinute = i3;
        boolean updateEnterpriseExchangeAccount = EmailProviderHelper.updateEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
        Slog.d("ExchangeAccountPolicy", "setSyncPeakTimings() = " + updateEnterpriseExchangeAccount + " , peakDays =" + i + ", mPeakStartMinute = " + i2 + ", peakEndMinute = " + i3 + ", accid = " + j);
        return updateEnterpriseExchangeAccount;
    }

    public boolean setSyncSchedules(ContextInfo contextInfo, int i, int i2, int i3, long j) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setSyncSchedules() :  " + j);
        EnterpriseExchangeAccount enterpriseExchangeAccount = EmailProviderHelper.getEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, j);
        if (enterpriseExchangeAccount == null) {
            Log.i("ExchangeAccountPolicy", "setSyncSchedules_new() : No exist accId : " + j);
            return false;
        }
        enterpriseExchangeAccount.mPeakSyncSchedule = i;
        enterpriseExchangeAccount.mOffPeakSyncSchedule = i2;
        enterpriseExchangeAccount.mRoamingSyncSchedule = i3;
        boolean updateEnterpriseExchangeAccount = EmailProviderHelper.updateEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
        Slog.d("ExchangeAccountPolicy", "setSyncSchedules() : = " + updateEnterpriseExchangeAccount + " ,  peakSyncSchedule = " + i + ", offPeakSyncSchedule = " + i2 + ", roamingSyncSchedule = " + i3 + ", accid = " + j);
        return updateEnterpriseExchangeAccount;
    }

    public boolean setDataSyncs(ContextInfo contextInfo, boolean z, boolean z2, boolean z3, boolean z4, long j) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        int i = enforceExchangeAccountPermission.mContainerId;
        Log.i("ExchangeAccountPolicy", "setDataSyncs() : " + j);
        EnterpriseExchangeAccount enterpriseExchangeAccount = EmailProviderHelper.getEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, j);
        if (enterpriseExchangeAccount == null) {
            Log.i("ExchangeAccountPolicy", "setSSL_new() : No exist accId : " + j);
            return false;
        }
        enterpriseExchangeAccount.mSyncContacts = z2;
        enterpriseExchangeAccount.mSyncCalendar = z;
        enterpriseExchangeAccount.mSyncTasks = z3;
        enterpriseExchangeAccount.mSyncNotes = z4;
        boolean updateEnterpriseExchangeAccount = EmailProviderHelper.updateEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
        Slog.d("ExchangeAccountPolicy", "setDataSyncs() : = " + updateEnterpriseExchangeAccount + " ,  syncCalendar = " + z + ", syncContacts = " + z2 + ", accid = " + j);
        return updateEnterpriseExchangeAccount;
    }

    public long getAccountId(ContextInfo contextInfo, String str, String str2, String str3) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "getAccountId() : " + str2);
        long accountId = SettingsUtils.getAccountId(enforceExchangeAccountPermission, str, str2, str3, "eas", true, this.mContext);
        Slog.d("ExchangeAccountPolicy", "getAccountId() : = " + accountId + " ,  easDomain = " + str + ", easUser = " + str2 + ", activeSyncHost = " + str3);
        return accountId;
    }

    public Account getAccountDetails(ContextInfo contextInfo, long j) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        int i = enforceExchangeAccountPermission.mContainerId;
        Log.i("ExchangeAccountPolicy", "getAccountDetails() : " + j + " ,  userID = " + Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission));
        EnterpriseExchangeAccount enterpriseExchangeAccount = EmailProviderHelper.getEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, j);
        if (enterpriseExchangeAccount != null) {
            return SettingsUtils.getAccountFromEnterpriseExchangeAccount(enterpriseExchangeAccount);
        }
        return null;
    }

    public Account[] getAllEASAccounts(ContextInfo contextInfo) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        int i = enforceExchangeAccountPermission.mContainerId;
        Log.i("ExchangeAccountPolicy", "getAllEASAccounts() : userId = " + Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission));
        ArrayList arrayList = new ArrayList();
        EnterpriseExchangeAccount[] allEnterpriseExchangeAccount = EmailProviderHelper.getAllEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission);
        if (allEnterpriseExchangeAccount != null) {
            for (EnterpriseExchangeAccount enterpriseExchangeAccount : allEnterpriseExchangeAccount) {
                if (enterpriseExchangeAccount != null) {
                    arrayList.add(SettingsUtils.getAccountFromEnterpriseExchangeAccount(enterpriseExchangeAccount));
                }
            }
        } else {
            Log.i("ExchangeAccountPolicy", "getAllEASAccounts_new( ): fail to get list.");
        }
        if (arrayList.size() > 0) {
            return (Account[]) arrayList.toArray(new Account[arrayList.size()]);
        }
        return null;
    }

    public boolean deleteAccount(ContextInfo contextInfo, long j) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        int i = enforceExchangeAccountPermission.mContainerId;
        Log.i("ExchangeAccountPolicy", "deleteAccount() :" + j);
        EnterpriseExchangeAccount enterpriseExchangeAccount = EmailProviderHelper.getEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, j);
        if (enterpriseExchangeAccount == null) {
            Log.i("ExchangeAccountPolicy", "deleteAccount_new() : Not valid accId : " + j);
            return false;
        }
        String str = enterpriseExchangeAccount.mEmailAddress;
        String str2 = enterpriseExchangeAccount.mServerAddress;
        if (str == null || str2 == null) {
            Log.i("ExchangeAccountPolicy", "deleteAccount() : no Inforamtion for accId = " + j);
            return false;
        }
        if (!SettingsUtils.isAccountRemovalAllowed(str, SettingsUtils.getAccountType(true, 0))) {
            Log.i("ExchangeAccountPolicy", "deleteAccount() : MDM DeviceAccountPolicy restriction - cannot delete account : " + j);
            return false;
        }
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Log.i("ExchangeAccountPolicy", "deleteAccount() : accId = " + j + " , userId = " + callingOrCurrentUserId);
            Intent intent = new Intent("com.samsung.android.knox.intent.action.DELETE_EMAILACCOUNT_INTERNAL");
            intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL", j);
            intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", str);
            intent.putExtra("com.samsung.android.knox.intent.extra.SERVICE_INTERNAL", "eas");
            intent.putExtra("com.samsung.android.knox.intent.extra.SERVICE_NAME_INTERNAL", str2);
            intent.setPackage(SettingsUtils.getEmailPackageName(i));
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(callingOrCurrentUserId), "com.samsung.android.knox.permission.KNOX_EMAIL");
            return true;
        } catch (Exception e) {
            Log.e("ExchangeAccountPolicy", "deleteAccount() : failed. ", e);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void sendAccountsChangedBroadcast(ContextInfo contextInfo) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        int i = enforceExchangeAccountPermission.mContainerId;
        int i2 = enforceExchangeAccountPermission.mCallerUid;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SettingsUtils.sendAccountsChangedBroadcast(i, this.mContext, i2);
            Message obtainMessage = this.mHandler.obtainMessage(1);
            obtainMessage.arg1 = i;
            obtainMessage.arg2 = callingOrCurrentUserId;
            if (!this.mRestartExtendDelay) {
                Log.i("ExchangeAccountPolicy", "sendAccountsChangedBroadcast()");
                this.mHandler.sendMessageDelayed(obtainMessage, 5000L);
            } else {
                Log.i("ExchangeAccountPolicy", "sendAccountsChangedBroadcast() : Delayed Broadcast");
                this.mHandler.sendMessageDelayed(obtainMessage, 15000L);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.lang.Integer, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v6, types: [long] */
    public String getDeviceId(ContextInfo contextInfo) {
        Context context;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission(contextInfo));
        Log.i("ExchangeAccountPolicy", "getDeviceId() : userID = " + callingOrCurrentUserId);
        String emailPackageName = SettingsUtils.getEmailPackageName(callingOrCurrentUserId);
        if (!SettingsUtils.isPackageInstalled(emailPackageName, callingOrCurrentUserId)) {
            Log.i("ExchangeAccountPolicy", "getDeviceId() failed : Email App is not installed. : " + emailPackageName);
            return null;
        }
        if (getEDM() != null && !getEDM().getApplicationPolicy().getApplicationStateEnabled(emailPackageName)) {
            Log.i("ExchangeAccountPolicy", "getDeviceId() failed : Email App is disabled. : " + emailPackageName);
            return null;
        }
        HashMap hashMap = mDeviceId;
        long valueOf = Integer.valueOf(callingOrCurrentUserId);
        if (hashMap.get(valueOf) != null) {
            return (String) mDeviceId.get(Integer.valueOf(callingOrCurrentUserId));
        }
        final Object obj = new Object();
        synchronized (obj) {
            try {
                valueOf = Binder.clearCallingIdentity();
                try {
                    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.email.ExchangeAccountPolicy.1
                        @Override // android.content.BroadcastReceiver
                        public void onReceive(Context context2, Intent intent) {
                            int intExtra = intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", intent.getIntExtra("userid", -1));
                            String stringExtra = intent.getStringExtra("deviceid");
                            if (stringExtra == null) {
                                stringExtra = intent.getStringExtra("com.samsung.android.knox.intent.extra.DEVICE_ID_INTERNAL");
                            }
                            Slog.d("ExchangeAccountPolicy", "getDeviceId() : receive userId = " + intExtra + " , deviceid = " + stringExtra);
                            ExchangeAccountPolicy.mDeviceId.put(Integer.valueOf(intExtra), stringExtra);
                            synchronized (obj) {
                                obj.notifyAll();
                            }
                        }
                    };
                    IntentFilter intentFilter = new IntentFilter("com.samsung.android.knox.intent.action.GET_EMAIL_DEVICEID_RESULT_INTERNAL");
                    intentFilter.addAction("edm.intent.action.internal.GET_DEVICEID_RESULT");
                    this.mContext.registerReceiverAsUser(broadcastReceiver, UserHandle.ALL, intentFilter, null, null);
                    Intent intent = new Intent("com.samsung.android.knox.intent.action.GET_EMAIL_DEVICEID_REQUEST_INTERNAL");
                    intent.setPackage(SettingsUtils.getEmailPackageName(callingOrCurrentUserId));
                    this.mContext.sendBroadcastAsUser(intent, new UserHandle(callingOrCurrentUserId), "com.samsung.android.knox.permission.KNOX_EMAIL");
                    StringBuilder sb = new StringBuilder();
                    sb.append("getDeviceId() : sendBroadcast ");
                    sb.append(callingOrCurrentUserId != 0 ? Integer.valueOf(callingOrCurrentUserId) : "");
                    Log.i("ExchangeAccountPolicy", sb.toString());
                    try {
                        try {
                            if (mDeviceId.get(Integer.valueOf(callingOrCurrentUserId)) == null) {
                                Log.i("ExchangeAccountPolicy", "getDeviceId() : Device id is still null, waiting 5 seconds...");
                                obj.wait(5000L);
                            }
                            context = this.mContext;
                        } catch (InterruptedException e) {
                            Log.e("ExchangeAccountPolicy", "getDeviceId() Inturrupted.", e);
                            context = this.mContext;
                        }
                        context.unregisterReceiver(broadcastReceiver);
                    } catch (Throwable th) {
                        this.mContext.unregisterReceiver(broadcastReceiver);
                        throw th;
                    }
                } catch (Exception e2) {
                    Log.e("ExchangeAccountPolicy", "getDeviceId() : failed. ", e2);
                }
            } finally {
                Binder.restoreCallingIdentity(valueOf);
            }
        }
        return (String) mDeviceId.get(Integer.valueOf(callingOrCurrentUserId));
    }

    public static String getExchangeServiceName(int i) {
        return SettingsUtils.getEasPackageName(i) + ".exchange.ExchangeService";
    }

    public void removePendingAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        int i = enforceExchangeAccountPermission.mContainerId;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission);
        Log.i("ExchangeAccountPolicy", "removePendingAccount() : " + str2);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Intent intent = new Intent("com.samsung.android.knox.intent.action.DELETE_NOT_VALIDATED_EMAILACCOUNT_INTERNAL");
                intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", str);
                intent.putExtra("com.samsung.android.knox.intent.extra.SERVICE_INTERNAL", "eas");
                intent.putExtra("com.samsung.android.knox.intent.extra.SERVICE_NAME_INTERNAL", str4);
                intent.putExtra("com.samsung.android.knox.intent.extra.EXTRA_USER_NAME_INTERNAL", str2);
                intent.putExtra("com.samsung.android.knox.intent.extra.DOMAIN_INTERNAL", str3);
                intent.setPackage(SettingsUtils.getEmailPackageName(i));
                this.mContext.sendBroadcastAsUser(intent, new UserHandle(callingOrCurrentUserId), "com.samsung.android.knox.permission.KNOX_EMAIL");
            } catch (Exception e) {
                Log.e("ExchangeAccountPolicy", "removePendingAccount() : fail to remove pending EAS Account. " + str2, e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean setRequireSignedSMIMEMessages(ContextInfo contextInfo, long j, boolean z) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setRequireSignedSMIMEMessages() : " + j);
        int i = enforceExchangeAccountPermission.mCallerUid;
        int i2 = enforceExchangeAccountPermission.mContainerId;
        if (!isValidEASAccountId(enforceExchangeAccountPermission, j)) {
            Log.i("ExchangeAccountPolicy", "setRequireSignedSMIMEMessages() : No exist accId : " + j);
            return false;
        }
        boolean putPolicyState = putPolicyState(i, i2, j, z, "ReqSigSmime");
        if (!putPolicyState) {
            Log.i("ExchangeAccountPolicy", "setRequireSignedSMIMEMessages() : failed.");
        }
        return putPolicyState;
    }

    public boolean getRequireSignedSMIMEMessages(ContextInfo contextInfo, long j) {
        return getPolicyState(contextInfo.mContainerId, j, "ReqSigSmime", Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean setRequireEncryptedSMIMEMessages(ContextInfo contextInfo, long j, boolean z) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setRequireEncryptedSMIMEMessages () : " + j);
        int i = enforceExchangeAccountPermission.mContainerId;
        int i2 = enforceExchangeAccountPermission.mCallerUid;
        if (!isValidEASAccountId(enforceExchangeAccountPermission, j)) {
            Log.i("ExchangeAccountPolicy", "setRequireEncryptedSMIMEMessages() : No exist accId : " + j);
            return false;
        }
        boolean putPolicyState = putPolicyState(i2, i, j, z, "ReqEncryptSmime");
        if (!putPolicyState) {
            Log.i("ExchangeAccountPolicy", "setRequireEncryptedSMIMEMessages() : failed.");
        }
        return putPolicyState;
    }

    public boolean getRequireEncryptedSMIMEMessages(ContextInfo contextInfo, long j) {
        return getPolicyState(contextInfo.mContainerId, j, "ReqEncryptSmime", Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public class SMIMEIntentReceiver extends BroadcastReceiver {
        public SMIMEIntentReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("ExchangeAccountPolicy/Receiver", "Received Intent - " + action);
            if (action == null) {
                Log.i("ExchangeAccountPolicy/Receiver", "failed. action is null.");
                return;
            }
            boolean z = false;
            if (action.endsWith("com.samsung.edm.intent.action.EXCHANGE_SMIME_INSTALL_STATUS")) {
                Log.i("ExchangeAccountPolicy/Receiver", "Received - ACTION_SMIME_INSTALL_STATUS");
                int intExtra = intent.getIntExtra("com.samsung.android.knox.intent.extra.SMIME_RESULT", 0);
                long longExtra = intent.getLongExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", -1L);
                long longExtra2 = intent.getLongExtra("com.samsung.android.knox.intent.extra.CERT_RESULT_ID_INTERNAL", -1L);
                AccountSMIMECertificate sMIMECertificate = AccountsReceiver.getSMIMECertificate("S" + longExtra2);
                if (sMIMECertificate == null) {
                    Log.i("ExchangeAccountPolicy/Receiver", "failed. no registed work. id = " + longExtra2);
                    return;
                }
                if (intExtra == -1 && longExtra > 0) {
                    int intExtra2 = intent.getIntExtra("com.samsung.android.knox.intent.extra.SMIME_INSTALL_TYPE", 1);
                    ContextInfo contextInfo = sMIMECertificate.mCxtInfo;
                    int i = contextInfo.mCallerUid;
                    int i2 = contextInfo.mContainerId;
                    if (intExtra2 == 2 || intExtra2 == 1) {
                        z = ExchangeAccountPolicy.this.putPolicyState(i, i2, longExtra, true, "ForceSmimeCertForEncryption");
                        Slog.d("ExchangeAccountPolicy/Receiver", "force SMIME Certificate for Enryption. ret = " + z);
                    }
                    if (intExtra2 == 3 || intExtra2 == 1) {
                        z = ExchangeAccountPolicy.this.putPolicyState(i, i2, longExtra, true, "ForceSmimeCertForSigning");
                        Slog.d("ExchangeAccountPolicy/Receiver", "force SMIME Certificate for Signing. ret = " + z);
                    }
                    if (z) {
                        Log.i("ExchangeAccountPolicy/Receiver", "success to SMIME Certificate ." + intExtra2);
                        return;
                    }
                    Log.i("ExchangeAccountPolicy/Receiver", "failed to SMIME Certificate ." + intExtra2);
                    return;
                }
                Log.i("ExchangeAccountPolicy/Receiver", "failed to enforce SMIME. accId = " + longExtra + ", status = " + intExtra);
                return;
            }
            if ("com.samsung.android.knox.intent.action.ENFORCE_SMIME_ALIAS_EMAIL_INTERNAL".equals(action)) {
                Log.i("ExchangeAccountPolicy", "Received - ACTION_ENFORCE_SMIME_ALIAS_EMAIL_INTERNAL");
                int intExtra3 = intent.getIntExtra("com.samsung.android.knox.intent.extra.SMIME_RESULT", 0);
                long longExtra3 = intent.getLongExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", -1L);
                int intExtra4 = intent.getIntExtra("com.samsung.android.knox.intent.extra.SMIME_INSTALL_TYPE", -1);
                long longExtra4 = intent.getLongExtra("com.samsung.android.knox.intent.extra.CERT_RESULT_ID_INTERNAL", -1L);
                try {
                    AccountSMIMECertificate sMIMECertificate2 = AccountsReceiver.getSMIMECertificate("S" + longExtra4);
                    if (sMIMECertificate2 != null) {
                        ContextInfo contextInfo2 = sMIMECertificate2.mCxtInfo;
                        int i3 = contextInfo2.mCallerUid;
                        int i4 = contextInfo2.mContainerId;
                        if (intExtra3 != -1 || longExtra3 <= 0) {
                            Log.i("ExchangeAccountPolicy/Receiver", "forcing " + longExtra4);
                        } else {
                            if (intExtra4 == 0) {
                                if (sMIMECertificate2.mPath == null) {
                                    z = ExchangeAccountPolicy.this.resetForcedSMIMECertificateInternal(contextInfo2, longExtra3, i4, 2, true);
                                    Slog.d("ExchangeAccountPolicy/Receiver", "release SMIME Certificate for Enryption (Alias) by null.");
                                } else {
                                    z = ExchangeAccountPolicy.this.putPolicyState(i3, i4, longExtra3, true, "ForceSmimeCertForEncryption");
                                    Slog.d("ExchangeAccountPolicy/Receiver", "force SMIME Certificate for Enryption (Alias). ret = " + z);
                                }
                            }
                            if (intExtra4 == 1) {
                                if (sMIMECertificate2.mPath == null) {
                                    z = ExchangeAccountPolicy.this.resetForcedSMIMECertificateInternal(sMIMECertificate2.mCxtInfo, longExtra3, i4, 3, true);
                                    Slog.d("ExchangeAccountPolicy/Receiver", "release SMIME Certificate for Signing (Alias) by null.");
                                } else {
                                    z = ExchangeAccountPolicy.this.putPolicyState(i3, i4, longExtra3, true, "ForceSmimeCertForSigning");
                                    Slog.d("ExchangeAccountPolicy/Receiver", "force SMIME Certificate for Signing (Alias). ret = " + z);
                                }
                            }
                            if (z) {
                                if (sMIMECertificate2.mPath == null) {
                                    Log.i("ExchangeAccountPolicy/Receiver", "success to release SMIME Certificate (Alias) : " + intExtra4);
                                } else {
                                    Log.i("ExchangeAccountPolicy/Receiver", "success to SMIME Certificate (Alias) : " + intExtra4);
                                }
                            } else if (sMIMECertificate2.mPath == null) {
                                Log.i("ExchangeAccountPolicy/Receiver", "failed to release SMIME Certificate (Alias) : " + intExtra4);
                            } else {
                                Log.i("ExchangeAccountPolicy/Receiver", "failed to SMIME Certificate (Alias)." + intExtra4);
                            }
                        }
                    } else {
                        Log.i("ExchangeAccountPolicy/Receiver", "force SMIME Certificate has failed. status = " + intExtra3 + ", accId=" + longExtra3);
                    }
                } catch (Exception e) {
                    Log.e("ExchangeAccountPolicy/Receiver", "Failed to apply SMIME Alis Policy on success.", e);
                }
                ExchangeAccountPolicy.this.sendSMIMEAliasResultIntent(longExtra3, intExtra3, intExtra4);
                return;
            }
            Log.i("ExchangeAccountPolicy/Receiver", "no Defined Intent.");
        }
    }

    public int setForceSMIMECertificate(ContextInfo contextInfo, long j, String str, String str2) {
        return setForceSMIMECertificateInternal(contextInfo, j, str, str2, "com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_INTERNAL");
    }

    public boolean getForceSMIMECertificate(ContextInfo contextInfo, long j) {
        return getForceSMIMECertificateForEncryption(contextInfo, j) && getForceSMIMECertificateForSigning(contextInfo, j);
    }

    public boolean setReleaseSMIMECertificate(ContextInfo contextInfo, long j) {
        return setReleaseSMIMECertificateInternal(contextInfo, j, 1);
    }

    public final void sendReleaseSMIMECertificate(int i, long j, int i2) {
        Slog.d("ExchangeAccountPolicy", "sendReleaseSMIMECertificate() : accId = " + j + ", containerId = " + i + ", userId = " + i2);
        Intent intent = new Intent("com.samsung.android.knox.intent.action.RELEASE_SMIME_CERTIFICATE_INTERNAL");
        intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", j);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(i2), "com.samsung.android.knox.permission.SMIME_CERTIFICATE_INTERNAL");
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean putPolicyState(int i, int i2, long j, boolean z, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        contentValues.put("containerID", Integer.valueOf(i2));
        contentValues.put("AccountId", Long.valueOf(j));
        contentValues.put(str, z ? "1" : "0");
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("adminUid", Integer.valueOf(i));
        contentValues2.put("containerID", Integer.valueOf(i2));
        contentValues2.put("AccountId", Long.valueOf(j));
        boolean putValues = this.mEdmStorageProvider.putValues("ExchangeAccountTable", contentValues, contentValues2);
        Slog.d("ExchangeAccountPolicy", "putPolicyState: ret = " + putValues + " , accId  =" + j + " , state  =" + z + " , policy =" + str);
        return putValues;
    }

    public final boolean putPolicyState(int i, int i2, long j, int i3, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        contentValues.put("containerID", Integer.valueOf(i2));
        contentValues.put("AccountId", Long.valueOf(j));
        contentValues.put(str, Integer.valueOf(i3));
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("adminUid", Integer.valueOf(i));
        contentValues2.put("containerID", Integer.valueOf(i2));
        contentValues2.put("AccountId", Long.valueOf(j));
        boolean putValues = this.mEdmStorageProvider.putValues("ExchangeAccountTable", contentValues, contentValues2);
        Slog.d("ExchangeAccountPolicy", "putPolicyState: ret = " + putValues + " , accId  =" + j + " , value  =" + i3 + " , policy =" + str);
        return putValues;
    }

    public final boolean getPolicyState(int i, long j, String str, int i2) {
        boolean defaultValueState = getDefaultValueState(str);
        String[] strArr = {str};
        ContentValues contentValues = new ContentValues();
        contentValues.put("containerID", Integer.valueOf(i));
        contentValues.put("userID", Integer.valueOf(i2));
        contentValues.put("AccountId", Long.valueOf(j));
        try {
            List<ContentValues> valuesList = this.mEdmStorageProvider.getValuesList("ExchangeAccountTable", strArr, contentValues);
            if (valuesList == null || valuesList.isEmpty()) {
                Slog.d("ExchangeAccountPolicy", "getPolicyState() : results is default value(null), accId = " + j + ", user id = " + i2 + ", container id = " + i);
                return defaultValueState;
            }
            Slog.d("ExchangeAccountPolicy", "getPolicyState() : results = " + valuesList + ", accId = " + j + ", user id = " + i2 + ", container id = " + i);
            boolean defaultValueState2 = getDefaultValueState(str) ^ true;
            for (ContentValues contentValues2 : valuesList) {
                if (contentValues2.getAsString(str) != null) {
                    if (contentValues2.getAsString(str).equals(defaultValueState2 ? "1" : "0")) {
                        Slog.d("ExchangeAccountPolicy", "getPolicyState: restricted. ");
                        return defaultValueState2;
                    }
                }
            }
            Slog.d("ExchangeAccountPolicy", "getPolicyState: no restrictData. ");
            return !defaultValueState2;
        } catch (Exception e) {
            Log.e("ExchangeAccountPolicy", "getPolicyState() : Exception while getValuesList from EDMStorageProvider", e);
            return defaultValueState;
        }
    }

    public final int getPolicyStateasInteger(int i, long j, String str, int i2, boolean z) {
        int defaultValueStateInteger = getDefaultValueStateInteger(str);
        String[] strArr = {str};
        ContentValues contentValues = new ContentValues();
        contentValues.put("containerID", Integer.valueOf(i));
        contentValues.put("userID", Integer.valueOf(i2));
        contentValues.put("AccountId", Long.valueOf(j));
        try {
            List valuesList = this.mEdmStorageProvider.getValuesList("ExchangeAccountTable", strArr, contentValues);
            if (valuesList != null && !valuesList.isEmpty()) {
                Log.d("ExchangeAccountPolicy", "getPolicyStateasInteger() : results = " + valuesList + ", accId = " + j + ", user id = " + i2 + ", container id = " + i);
                Iterator it = valuesList.iterator();
                int i3 = z ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                while (it.hasNext()) {
                    Integer asInteger = ((ContentValues) it.next()).getAsInteger(str);
                    if (asInteger != null && asInteger.intValue() > 0) {
                        if (z && asInteger.intValue() < i3) {
                            i3 = asInteger.intValue();
                        } else if (!z && asInteger.intValue() > i3) {
                            i3 = asInteger.intValue();
                        }
                    }
                }
                if (!z ? i3 != Integer.MIN_VALUE : i3 != Integer.MAX_VALUE) {
                    defaultValueStateInteger = i3;
                }
                Log.i("ExchangeAccountPolicy", "getPolicyStateasInteger() : result = " + defaultValueStateInteger);
            }
            return defaultValueStateInteger;
        } catch (Exception e) {
            Log.e("ExchangeAccountPolicy", "getPolicyStateasInteger() : Exception while getValuesListasInteger from EDMStorageProvider", e);
            return defaultValueStateInteger;
        }
    }

    public final boolean getDefaultValueState(String str) {
        boolean z = false;
        if (!str.equals("ReqSigSmime") && !str.equals("ReqEncryptSmime") && !str.equals("ForceSmimeCert") && (str.equals("AllowSettingChange") || str.equals("AllowNotificationChange") || (!str.equals("ForceSmimeCertForEncryption") && !str.equals("ForceSmimeCertForSigning") && str.equals("AttachmentEnable")))) {
            z = true;
        }
        Slog.d("ExchangeAccountPolicy", "getDefaultValueState() : policy = " + str + " ret = " + z);
        return z;
    }

    public final int getDefaultValueStateInteger(String str) {
        if (!str.equals("IncomingAttachmentSize") && !str.equals("MaxCalendarAgeFilter") && !str.equals("MaxEmailAgeFilter") && !str.equals("MaxEmailBodyTruncationSize")) {
            str.equals("MaxEmailHTMLBodyTruncationSize");
        }
        Slog.d("ExchangeAccountPolicy", "getDefaultValueStateInteger() : policy = " + str + " ret = 0");
        return 0;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
        Long asLong;
        int userId = UserHandle.getUserId(i);
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        List<ContentValues> valuesList = this.mEdmStorageProvider.getValuesList("ExchangeAccountTable", new String[]{"AccountId", "ForceSmimeCert"}, contentValues);
        if (valuesList != null) {
            for (ContentValues contentValues2 : valuesList) {
                if (contentValues2.getAsInteger("ForceSmimeCert").intValue() > 0 && (asLong = contentValues2.getAsLong("AccountId")) != null) {
                    sendReleaseSMIMECertificate(0, asLong.longValue(), userId);
                }
            }
        }
        Slog.d("ExchangeAccountPolicy", "onPreAdminRemoval() : ");
    }

    public boolean allowInComingAttachments(ContextInfo contextInfo, boolean z, long j) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "allowInComingAttachments() : " + j);
        int i = enforceExchangeAccountPermission.mContainerId;
        int i2 = enforceExchangeAccountPermission.mCallerUid;
        if (!isValidEASAccountId(enforceExchangeAccountPermission, j)) {
            Log.i("ExchangeAccountPolicy", "allowInComingAttachments_new() : No exist accId : " + j);
            return false;
        }
        boolean putPolicyState = putPolicyState(i2, i, j, z, "AttachmentEnable");
        Slog.d("ExchangeAccountPolicy", "allowInComingAttachments() : = " + putPolicyState + " , enable  =" + z + " , accId  =" + j);
        return putPolicyState;
    }

    public boolean isIncomingAttachmentsAllowed(ContextInfo contextInfo, long j) {
        return getPolicyState(contextInfo.mContainerId, j, "AttachmentEnable", Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean setIncomingAttachmentsSize(ContextInfo contextInfo, int i, long j) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setIncomingAttachmentsSize() : " + j);
        int i2 = enforceExchangeAccountPermission.mContainerId;
        int i3 = enforceExchangeAccountPermission.mCallerUid;
        if (!isValidEASAccountId(enforceExchangeAccountPermission, j)) {
            Log.i("ExchangeAccountPolicy", "setIncomingAttachmentsSize() : No exist accId : " + j);
            return false;
        }
        if (i < 0) {
            Log.i("ExchangeAccountPolicy", "setIncomingAttachmentsSize() : Error :: invalid parameter.");
            return false;
        }
        boolean putPolicyState = putPolicyState(i3, i2, j, i, "IncomingAttachmentSize");
        Slog.d("ExchangeAccountPolicy", "setIncomingAttachmentsSize() : = " + putPolicyState + " , size  =" + i + " , accId  =" + j);
        return putPolicyState;
    }

    public int getIncomingAttachmentsSize(ContextInfo contextInfo, long j) {
        return getPolicyStateasInteger(contextInfo.mContainerId, j, "IncomingAttachmentSize", Utils.getCallingOrCurrentUserId(contextInfo), true);
    }

    public boolean setEmailNotificationsState(ContextInfo contextInfo, long j, boolean z) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setEmailNotificationsState() : " + j);
        int i = enforceExchangeAccountPermission.mContainerId;
        int i2 = enforceExchangeAccountPermission.mCallerUid;
        if (!isValidEASAccountId(enforceExchangeAccountPermission, j)) {
            Log.i("ExchangeAccountPolicy", "allowInComingAttachments() : No exist accId : " + j);
            return false;
        }
        boolean putPolicyState = putPolicyState(i2, i, j, z, "AllowNotificationChange");
        Slog.d("ExchangeAccountPolicy", "setEmailNotificationsState() : = " + putPolicyState + " , accId  =" + j + " , enable  =" + z);
        return putPolicyState;
    }

    public boolean isEmailNotificationsEnabled(ContextInfo contextInfo, long j) {
        return getPolicyState(contextInfo.mContainerId, j, "AllowNotificationChange", Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean allowEmailSettingsChange(ContextInfo contextInfo, long j, boolean z) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "allowEmailSettingsChange() : " + j);
        int i = enforceExchangeAccountPermission.mContainerId;
        int i2 = enforceExchangeAccountPermission.mCallerUid;
        if (!isValidEASAccountId(enforceExchangeAccountPermission, j)) {
            Log.i("ExchangeAccountPolicy", "allowEmailSettingsChange() : No exist accId : " + j);
            return false;
        }
        boolean putPolicyState = putPolicyState(i2, i, j, z, "AllowSettingChange");
        Slog.d("ExchangeAccountPolicy", "allowEmailSettingsChange() : = " + putPolicyState + " , accId  =" + j + " , allow  =" + z);
        return putPolicyState;
    }

    public boolean isEmailSettingsChangeAllowed(ContextInfo contextInfo, long j) {
        return getPolicyState(contextInfo.mContainerId, j, "AllowSettingChange", Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean setMaxCalendarAgeFilter(ContextInfo contextInfo, int i, long j) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setMaxCalendarAgeFilter() : " + j);
        int i2 = enforceExchangeAccountPermission.mContainerId;
        int i3 = enforceExchangeAccountPermission.mCallerUid;
        EnterpriseExchangeAccount enterpriseExchangeAccount = EmailProviderHelper.getEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, j);
        if (enterpriseExchangeAccount == null) {
            Log.i("ExchangeAccountPolicy", "setMaxCalendarAgeFilter() : No exist accId : " + j);
            return false;
        }
        if ((i < 4 || i > 7) && i != 0) {
            Log.i("ExchangeAccountPolicy", "setMaxCalendarAgeFilter() : Error :: Invalid input parameters.");
            return false;
        }
        if (!putPolicyState(i3, i2, j, i, "MaxCalendarAgeFilter")) {
            Log.i("ExchangeAccountPolicy", "setMaxCalendarAgeFilter() : Error :: Fail to update policy.");
            return false;
        }
        int maxCalendarAgeFilter = getMaxCalendarAgeFilter(enforceExchangeAccountPermission, j);
        if (enterpriseExchangeAccount.mSyncCalendarAge <= maxCalendarAgeFilter || maxCalendarAgeFilter == 0) {
            return true;
        }
        Log.i("ExchangeAccountPolicy", "setMaxCalendarAgeFilter() : need to change Account value : " + enterpriseExchangeAccount.mSyncCalendarAge + " - " + maxCalendarAgeFilter);
        enterpriseExchangeAccount.mSyncCalendarAge = maxCalendarAgeFilter;
        return EmailProviderHelper.updateEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
    }

    public int getMaxCalendarAgeFilter(ContextInfo contextInfo, long j) {
        return getPolicyStateasInteger(contextInfo.mContainerId, j, "MaxCalendarAgeFilter", Utils.getCallingOrCurrentUserId(contextInfo), true);
    }

    public boolean setMaxEmailAgeFilter(ContextInfo contextInfo, int i, long j) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        int i2 = enforceExchangeAccountPermission.mContainerId;
        int i3 = enforceExchangeAccountPermission.mCallerUid;
        EnterpriseExchangeAccount enterpriseExchangeAccount = EmailProviderHelper.getEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, j);
        if (enterpriseExchangeAccount == null) {
            Log.i("ExchangeAccountPolicy", "setMaxEmailAgeFilter() : No exist accId : " + j);
            return false;
        }
        if (i < 0 || i > 5) {
            Log.i("ExchangeAccountPolicy", "setMaxEmailAgeFilter() : Error :: Invalid input parameters.");
            return false;
        }
        if (!putPolicyState(i3, i2, j, i, "MaxEmailAgeFilter")) {
            Log.i("ExchangeAccountPolicy", "setMaxEmailAgeFilter() : Error :: Fail to update policy.");
            return false;
        }
        int maxEmailAgeFilter = getMaxEmailAgeFilter(enforceExchangeAccountPermission, j);
        if (enterpriseExchangeAccount.mMaxEmailAgeFilter <= maxEmailAgeFilter || maxEmailAgeFilter == 0) {
            return true;
        }
        Log.i("ExchangeAccountPolicy", "setMaxEmailAgeFilter() : need to change Account value : " + enterpriseExchangeAccount.mMaxEmailAgeFilter + " - " + maxEmailAgeFilter);
        enterpriseExchangeAccount.mMaxEmailAgeFilter = maxEmailAgeFilter;
        return EmailProviderHelper.updateEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
    }

    public int getMaxEmailAgeFilter(ContextInfo contextInfo, long j) {
        return getPolicyStateasInteger(contextInfo.mContainerId, j, "MaxEmailAgeFilter", Utils.getCallingOrCurrentUserId(contextInfo), true);
    }

    public boolean setMaxEmailBodyTruncationSize(ContextInfo contextInfo, int i, long j) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setMaxEmailBodyTruncationSize() : " + j);
        int i2 = enforceExchangeAccountPermission.mContainerId;
        int i3 = enforceExchangeAccountPermission.mCallerUid;
        EnterpriseExchangeAccount enterpriseExchangeAccount = EmailProviderHelper.getEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, j);
        boolean z = false;
        if (enterpriseExchangeAccount == null) {
            Log.i("ExchangeAccountPolicy", "setMaxEmailBodyTruncationSize() : No exist accId : " + j);
            return false;
        }
        if (i < 0) {
            Log.i("ExchangeAccountPolicy", "setMaxEmailBodyTruncationSize() : Error :: invalid parameter.");
            return false;
        }
        if (!putPolicyState(i3, i2, j, i, "MaxEmailBodyTruncationSize")) {
            Log.i("ExchangeAccountPolicy", "setMaxEmailBodyTruncationSize() : Error :: Fail to update policy.");
            return false;
        }
        int maxEmailBodyTruncationSize = getMaxEmailBodyTruncationSize(enforceExchangeAccountPermission, j);
        if (!isEmailHTMLAllowed(enforceExchangeAccountPermission, enterpriseExchangeAccount.mEmailAddress) && maxEmailBodyTruncationSize != 0) {
            int i4 = enterpriseExchangeAccount.mMaxEmailBodyTruncationSize;
            if (i4 == 0 || i4 > maxEmailBodyTruncationSize * 1024) {
                StringBuilder sb = new StringBuilder();
                sb.append("setMaxEmailBodyTruncationSize() : need to change Account value1 : ");
                sb.append(enterpriseExchangeAccount.mMaxEmailBodyTruncationSize);
                sb.append(" - ");
                int i5 = maxEmailBodyTruncationSize * 1024;
                sb.append(i5);
                Slog.d("ExchangeAccountPolicy", sb.toString());
                enterpriseExchangeAccount.mMaxEmailBodyTruncationSize = i5;
                z = true;
            }
            if (z) {
                EmailProviderHelper.updateEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
                Log.i("ExchangeAccountPolicy", "setMaxEmailBodyTruncationSize() : updated account.");
            }
        }
        Log.i("ExchangeAccountPolicy", "setMaxEmailBodyTruncationSize()  = true");
        return true;
    }

    public int getMaxEmailBodyTruncationSize(ContextInfo contextInfo, long j) {
        return getPolicyStateasInteger(contextInfo.mContainerId, j, "MaxEmailBodyTruncationSize", Utils.getCallingOrCurrentUserId(contextInfo), true);
    }

    public boolean setMaxEmailHTMLBodyTruncationSize(ContextInfo contextInfo, int i, long j) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setMaxEmailHTMLBodyTruncationSize() : " + j);
        int i2 = enforceExchangeAccountPermission.mContainerId;
        int i3 = enforceExchangeAccountPermission.mCallerUid;
        EnterpriseExchangeAccount enterpriseExchangeAccount = EmailProviderHelper.getEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, j);
        boolean z = false;
        if (enterpriseExchangeAccount == null) {
            Log.i("ExchangeAccountPolicy", "setMaxEmailHTMLBodyTruncationSize() : No exist accId : " + j);
            return false;
        }
        if (i < 0) {
            Log.i("ExchangeAccountPolicy", "setMaxEmailHTMLBodyTruncationSize() : Error :: invalid parameter.");
            return false;
        }
        if (!putPolicyState(i3, i2, j, i, "MaxEmailHTMLBodyTruncationSize")) {
            Log.i("ExchangeAccountPolicy", "setMaxEmailHTMLBodyTruncationSize() : Error :: Fail to update policy.");
            return false;
        }
        int maxEmailHTMLBodyTruncationSize = getMaxEmailHTMLBodyTruncationSize(enforceExchangeAccountPermission, j);
        boolean isEmailHTMLAllowed = isEmailHTMLAllowed(enforceExchangeAccountPermission, enterpriseExchangeAccount.mEmailAddress);
        if (enterpriseExchangeAccount.mAllowHTMLEmail && isEmailHTMLAllowed && maxEmailHTMLBodyTruncationSize != 0) {
            int i4 = enterpriseExchangeAccount.mMaxEmailHtmlBodyTruncationSize;
            if (i4 == 0 || i4 > maxEmailHTMLBodyTruncationSize * 1024) {
                StringBuilder sb = new StringBuilder();
                sb.append("setMaxEmailHTMLBodyTruncationSize() : need to change Account value1 : ");
                sb.append(enterpriseExchangeAccount.mMaxEmailHtmlBodyTruncationSize);
                sb.append(" - ");
                int i5 = maxEmailHTMLBodyTruncationSize * 1024;
                sb.append(i5);
                Slog.d("ExchangeAccountPolicy", sb.toString());
                enterpriseExchangeAccount.mMaxEmailHtmlBodyTruncationSize = i5;
                z = true;
            }
            if (z) {
                EmailProviderHelper.updateEnterpriseExchangeAccount(this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
                Log.i("ExchangeAccountPolicy", "setMaxEmailHTMLBodyTruncationSize() : updated account.");
            }
        }
        Log.i("ExchangeAccountPolicy", "setMaxEmailHTMLBodyTruncationSize()  = true");
        return true;
    }

    public int getMaxEmailHTMLBodyTruncationSize(ContextInfo contextInfo, long j) {
        return getPolicyStateasInteger(contextInfo.mContainerId, j, "MaxEmailHTMLBodyTruncationSize", Utils.getCallingOrCurrentUserId(contextInfo), true);
    }

    public int setForceSMIMECertificateForSigning(ContextInfo contextInfo, long j, String str, String str2) {
        return setForceSMIMECertificateInternal(contextInfo, j, str, str2, "com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_FOR_SIGNING_INTERNAL");
    }

    public boolean getForceSMIMECertificateForSigning(ContextInfo contextInfo, long j) {
        return getPolicyState(contextInfo.mContainerId, j, "ForceSmimeCertForSigning", Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean setReleaseSMIMECertificateForSigning(ContextInfo contextInfo, long j) {
        return setReleaseSMIMECertificateInternal(contextInfo, j, 3);
    }

    public final void sendReleaseSMIMECertificateForSigning(int i, long j, int i2) {
        Slog.d("ExchangeAccountPolicy", "sendReleaseSMIMECertificateForSigning() : accId = " + j + ", containerId = " + i + ", userId = " + i2);
        Intent intent = new Intent("com.samsung.android.knox.intent.action.RELEASE_SMIME_CERTIFICATE_FOR_SIGNING_INTERNAL");
        intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", j);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(i2), "com.samsung.android.knox.permission.SMIME_CERTIFICATE_INTERNAL");
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int setForceSMIMECertificateForEncryption(ContextInfo contextInfo, long j, String str, String str2) {
        return setForceSMIMECertificateInternal(contextInfo, j, str, str2, "com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_FOR_ENCRYPTION_INTERNAL");
    }

    public boolean getForceSMIMECertificateForEncryption(ContextInfo contextInfo, long j) {
        return getPolicyState(contextInfo.mContainerId, j, "ForceSmimeCertForEncryption", Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public boolean setReleaseSMIMECertificateForEncryption(ContextInfo contextInfo, long j) {
        return setReleaseSMIMECertificateInternal(contextInfo, j, 2);
    }

    public final void sendReleaseSMIMECertificateForEncryption(int i, long j, int i2) {
        Slog.d("ExchangeAccountPolicy", "sendReleaseSMIMECertificateForEncryption() : accId = " + j + ", containerId = " + i + ", userId = " + i2);
        Intent intent = new Intent("com.samsung.android.knox.intent.action.RELEASE_SMIME_CERTIFICATE_FOR_ENCRYPTION_INTERNAL");
        intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", j);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(i2), "com.samsung.android.knox.permission.SMIME_CERTIFICATE_INTERNAL");
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public long addNewAccount_new(ContextInfo contextInfo, ExchangeAccount exchangeAccount) {
        String str;
        int i;
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission);
        int i2 = enforceExchangeAccountPermission.mContainerId;
        if (exchangeAccount == null) {
            Log.i("ExchangeAccountPolicy", "addNewAccount_new : Error :: Invalid Account.");
            return -1L;
        }
        enforceUCMPermission(enforceExchangeAccountPermission, exchangeAccount.certificateStorageName);
        SettingsUtils.getValidStr(exchangeAccount.displayName);
        String validStr = SettingsUtils.getValidStr(exchangeAccount.emailAddress);
        String validStr2 = SettingsUtils.getValidStr(exchangeAccount.serverAddress);
        String validStr3 = SettingsUtils.getValidStr(exchangeAccount.protocolVersion);
        SettingsUtils.getValidStr(exchangeAccount.senderName);
        SettingsUtils.getValidStr(exchangeAccount.signature);
        String validStr4 = SettingsUtils.getValidStr(exchangeAccount.easUser);
        String validStr5 = SettingsUtils.getValidStr(exchangeAccount.easDomain);
        String validStr6 = SettingsUtils.getValidStr(exchangeAccount.serverPassword);
        SettingsUtils.getValidStr(exchangeAccount.serverPathPrefix);
        boolean z = exchangeAccount.emailNotificationVibrateAlways;
        boolean z2 = !z;
        if (validStr == null || !SettingsUtils.isValidEmailAddress(validStr) || validStr2 == null || validStr3 == null || validStr4 == null) {
            Log.i("ExchangeAccountPolicy", "addNewAccount_new : Error :: Invalid input parameters.");
            return -1L;
        }
        if (!SettingsUtils.isPackageInstalled(SettingsUtils.getEmailPackageName(callingOrCurrentUserId), i2) && !SettingsUtils.isPackageInstalled("com.samsung.android.focus", i2)) {
            Log.i("ExchangeAccountPolicy", "addNewAccount() EX : Error :: Email and focus are not installed on user " + i2);
            return -1L;
        }
        if (true == z && true == z2) {
            Log.i("ExchangeAccountPolicy", "addNewAccount_new : Error :: Invalid input parameters. 'emailNotificationVibrateAlways' and 'emailNotificationVibrateWhenSilent' only one will be true at a time");
            return -1L;
        }
        if (SettingsUtils.getAccountId(enforceExchangeAccountPermission, validStr5, validStr4, validStr2, "eas", true, this.mContext) > 0) {
            Log.i("ExchangeAccountPolicy", "addNewAccount : Error :: Account already exists.");
            return -1L;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Log.i("ExchangeAccountPolicy", "addNewAccount() New : " + callingOrCurrentUserId);
                if (exchangeAccount.smimeCertificatePath == null || exchangeAccount.smimeCertificatePassword == null) {
                    str = validStr;
                } else {
                    Log.i("ExchangeAccountPolicy", "addNewAccount_new : SMIME Certificate at creation time");
                    int i3 = exchangeAccount.smimeCertificareMode;
                    if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION >= 17 && (i = exchangeAccount.smimeCertificateMode) >= 1 && i <= 3) {
                        i3 = i;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(i2);
                    sb.append("#");
                    str = validStr;
                    sb.append(str);
                    AccountsReceiver.pushSMIMECertificate(sb.toString(), new AccountSMIMECertificate(enforceExchangeAccountPermission, exchangeAccount.smimeCertificatePath, exchangeAccount.smimeCertificatePassword, i3));
                }
                long accountEmailPassword = setAccountEmailPassword(enforceExchangeAccountPermission, validStr6);
                long accountCertificatePassword = setAccountCertificatePassword(enforceExchangeAccountPermission, exchangeAccount.certificatePassword);
                Intent intent = new Intent("com.samsung.android.knox.intent.action.CREATE_EMAILACCOUNT_INTERNAL");
                intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL", accountEmailPassword);
                intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", str);
                String str2 = str;
                intent.putExtra("com.samsung.android.knox.intent.extra.SERVICE_INTERNAL", "eas");
                intent.putExtra("com.samsung.android.knox.intent.extra.SERVICE_NAME_INTERNAL", validStr2);
                intent.putExtra("com.samsung.android.knox.intent.extra.EXTRA_USER_NAME_INTERNAL", validStr4);
                intent.putExtra("com.samsung.android.knox.intent.extra.USER_PASSWD_ID_INTERNAL", accountEmailPassword);
                intent.putExtra("com.samsung.android.knox.intent.extra.USE_SSL_INTERNAL", exchangeAccount.useSSL ? 1 : 0);
                intent.putExtra("com.samsung.android.knox.intent.extra.USE_TLS_INTERNAL", exchangeAccount.useTLS ? 1 : 0);
                intent.putExtra("com.samsung.android.knox.intent.extra.TRUST_ALL_INTERNAL", exchangeAccount.acceptAllCertificates ? 1 : 0);
                intent.putExtra("com.samsung.android.knox.intent.extra.DOMAIN_INTERNAL", exchangeAccount.easDomain);
                intent.putExtra("com.samsung.android.knox.intent.extra.PEAK_START_TIME_INTERNAL", exchangeAccount.peakStartTime);
                intent.putExtra("com.samsung.android.knox.intent.extra.PEAK_END_TIME_INTERNAL", exchangeAccount.peakEndTime);
                intent.putExtra("com.samsung.android.knox.intent.extra.PEAK_DAYS_INTERNAL", exchangeAccount.peakDays);
                intent.putExtra("com.samsung.android.knox.intent.extra.PEAK_INTERNAL", exchangeAccount.syncInterval);
                intent.putExtra("com.samsung.android.knox.intent.extra.OFF_PEAK_INTERNAL", exchangeAccount.offPeak);
                intent.putExtra("com.samsung.android.knox.intent.extra.ROAMING_SCHEDULE_INTERNAL", exchangeAccount.roamingSchedule);
                intent.putExtra("com.samsung.android.knox.intent.extra.PERIOD_EMAIL_INTERNAL", exchangeAccount.syncLookback);
                intent.putExtra("com.samsung.android.knox.intent.extra.", exchangeAccount.retrivalSize);
                intent.putExtra("com.samsung.android.knox.intent.extra.PERIOD_CALENDAR_INTERNAL", exchangeAccount.periodCalendar);
                intent.putExtra("com.samsung.android.knox.intent.extra.NOTIFY_INTERNAL", exchangeAccount.isNotify);
                intent.putExtra("com.samsung.android.knox.intent.extra.SYNC_CONTACTS_INTERNAL", exchangeAccount.syncContacts);
                intent.putExtra("com.samsung.android.knox.intent.extra.SYNC_CALENDAR_INTERNAL", exchangeAccount.syncCalendar);
                intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_NAME_INTERNAL", exchangeAccount.displayName);
                intent.putExtra("com.samsung.android.knox.intent.extra.SIGNATURE_INTERNAL", exchangeAccount.signature);
                intent.putExtra("com.samsung.android.knox.intent.extra.VIBRATE_WHEN_SILENT_INTERNAL", exchangeAccount.emailNotificationVibrateAlways ? false : true);
                intent.putExtra("com.samsung.android.knox.intent.extra.EXTRA_VIBRATE_INTERNAL", exchangeAccount.emailNotificationVibrateAlways);
                intent.putExtra("com.samsung.android.knox.intent.extra.IS_DEFAULT_INTERNAL", exchangeAccount.isDefault);
                intent.putExtra("com.samsung.android.knox.intent.extra.CERTIFICATE_DATA_INTERNAL", exchangeAccount.certificateData);
                intent.putExtra("com.samsung.android.knox.intent.extra.CERTIFICATE_PASSWD_ID_INTERNAL", accountCertificatePassword);
                intent.setPackage(SettingsUtils.getEmailPackageName(i2));
                if (!TextUtils.isEmpty(exchangeAccount.certificateAlias)) {
                    String convertUcmUri = convertUcmUri(enforceExchangeAccountPermission, exchangeAccount.certificateStorageName, exchangeAccount.certificateAlias);
                    if (convertUcmUri == null) {
                        Log.i("ExchangeAccountPolicy", "addNewAccount : Error :: certificate storage name " + exchangeAccount.certificateStorageName + " does not exist!");
                        return -1L;
                    }
                    intent.putExtra("com.samsung.android.knox.intent.extra.CERTIFICATE_ALIAS_INTERNAL", convertUcmUri);
                    Log.i("ExchangeAccountPolicy", "addNewAccount : alias has added, alias = " + convertUcmUri);
                }
                this.mContext.sendBroadcastAsUser(intent, new UserHandle(callingOrCurrentUserId), "com.samsung.android.knox.permission.KNOX_EMAIL");
                intent.setPackage("com.samsung.android.focus");
                this.mContext.sendBroadcastAsUser(intent, new UserHandle(callingOrCurrentUserId), "com.samsung.android.knox.permission.KNOX_EMAIL");
                Slog.d("ExchangeAccountPolicy", "addNewAccount_new : sent intent to Email app : " + str2);
            } catch (Exception e) {
                Log.e("ExchangeAccountPolicy", "addNewAccount() NEW : " + e);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0L;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String convertUcmUri(ContextInfo contextInfo, String str, String str2) {
        CredentialStorage credentialStorage;
        if (TextUtils.isEmpty(str) || getUCMService() == null) {
            return str2;
        }
        CredentialStorage[] availableCredentialStorages = this.mUCMService.getAvailableCredentialStorages(contextInfo);
        if (availableCredentialStorages != null) {
            int length = availableCredentialStorages.length;
            for (int i = 0; i < length; i++) {
                credentialStorage = availableCredentialStorages[i];
                if (credentialStorage != null && str.equals(credentialStorage.name)) {
                    break;
                }
            }
        }
        credentialStorage = null;
        if (credentialStorage != null) {
            return new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name).setResourceId(1).setUid(contextInfo.mCallerUid).setAlias(str2).build();
        }
        return null;
    }

    public long setAccountEmailPassword(ContextInfo contextInfo, String str) {
        int i = getEDM().enforcePermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_EXCHANGE"))).mContainerId;
        long createIDforAccount = SettingsUtils.createIDforAccount();
        if (str == null) {
            return -1L;
        }
        try {
            String str2 = "E#" + createIDforAccount;
            SettingsUtils.setSecurityPassword(str2, str);
            SettingsUtils.setSecurityPasswordFocus(str2, str);
            Slog.d("ExchangeAccountPolicy", "setAccountEmailPassword() success");
            return createIDforAccount;
        } catch (Exception e) {
            Log.e("ExchangeAccountPolicy", "setAccountEmailPassword() failed", e);
            return -1L;
        }
    }

    public long setAccountCertificatePassword(ContextInfo contextInfo, String str) {
        int i = getEDM().enforcePermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_EXCHANGE"))).mContainerId;
        long createIDforAccount = SettingsUtils.createIDforAccount();
        if (str == null) {
            return -1L;
        }
        try {
            SettingsUtils.setSecurityPassword("C#" + createIDforAccount, str);
            Slog.d("ExchangeAccountPolicy", "setAccountCertificatePassword() success");
            return createIDforAccount;
        } catch (Exception e) {
            Log.e("ExchangeAccountPolicy", "setAccountCertificatePassword() failed", e);
            return -1L;
        }
    }

    public String getAccountEmailPassword(ContextInfo contextInfo, long j) {
        String str;
        ContextInfo enforcePermissionByContext = getEDM().enforcePermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_EXCHANGE")));
        int i = enforcePermissionByContext.mContainerId;
        String nameForUid = this.mContext.getPackageManager().getNameForUid(enforcePermissionByContext.mCallerUid);
        try {
            String str2 = "E#" + j;
            if (nameForUid.equals("com.samsung.android.focus")) {
                str = SettingsUtils.getSecurityPasswordFocus(str2);
            } else {
                str = SettingsUtils.getSecurityPassword(str2);
            }
        } catch (Exception e) {
            Log.e("ExchangeAccountPolicy", "getAccountEmailPassword() failed", e);
            str = null;
        }
        Slog.d("ExchangeAccountPolicy", "getAccountEmailPassword() success");
        return str;
    }

    public String getAccountCertificatePassword(ContextInfo contextInfo, long j) {
        String str;
        int i = getEDM().enforcePermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_EXCHANGE"))).mContainerId;
        try {
            str = SettingsUtils.getSecurityPassword("C#" + j);
        } catch (Exception e) {
            Log.e("ExchangeAccountPolicy", "getAccountCertificatePassword() failed", e);
            str = null;
        }
        Slog.d("ExchangeAccountPolicy", "getAccountCertificatePassword() success");
        return str;
    }

    public boolean setForceSMIMECertificateAlias(ContextInfo contextInfo, long j, String str, String str2, int i) {
        boolean z;
        String str3 = str2;
        Slog.d("ExchangeAccountPolicy", "setForceSMIMECertificateAlias() : accId = " + j + ", alias = " + str3 + ", type = " + i);
        if (!TextUtils.isEmpty(str)) {
            Slog.d("ExchangeAccountPolicy", "setForceSMIMECertificateAlias() : credential storage name = " + str);
        }
        Log.i("ExchangeAccountPolicy", "setForceSMIMECertificateAlias() : accId = " + j);
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        enforceUCMPermission(enforceExchangeAccountPermission, str);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission);
        if (i != 1 && i != 0) {
            Log.i("ExchangeAccountPolicy", "setForceSMIMECertificateAlias() : invalid type : " + i);
            return false;
        }
        if (!isValidEASAccountId(enforceExchangeAccountPermission, j)) {
            Log.i("ExchangeAccountPolicy", "setForceSMIMECertificateAlias() : No exist accId : " + j);
            return false;
        }
        if (!adminSatisfiesForceSMIMECertificateRules(enforceExchangeAccountPermission.mCallerUid, j, str3 != null, i == 0 ? 2 : 3)) {
            Log.i("ExchangeAccountPolicy", "setForceSMIMECertificateAlias() : either a smime certificate has already been enforced on this user by other admin or admin " + enforceExchangeAccountPermission.mCallerUid + " is trying to release a certificate which was not enforced by him");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (str3 != null) {
                try {
                    String convertUcmUri = convertUcmUri(enforceExchangeAccountPermission, str, str3);
                    if (convertUcmUri == null) {
                        Log.i("ExchangeAccountPolicy", "setForceSMIMECertificateAlias() : storage name " + str + " does not exist!");
                        return false;
                    }
                    str3 = convertUcmUri;
                } catch (Exception e) {
                    Log.e("ExchangeAccountPolicy", "setForceSMIMECertificateAlias() : failed. ", e);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    z = false;
                }
            }
            long createIDforAccount = SettingsUtils.createIDforAccount();
            Intent intent = new Intent("com.samsung.android.knox.intent.action.ENFORCE_SMIME_ALIAS_INTERNAL");
            intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", j);
            intent.putExtra("com.samsung.android.knox.intent.extra.ENFORCE_SMIME_ALIAS_NAME_INTERNAL", str3);
            intent.putExtra("com.samsung.android.knox.intent.extra.ENFORCE_SMIME_ALIAS_TYPE", i);
            intent.putExtra("com.samsung.android.knox.intent.extra.CERT_RESULT_ID_INTERNAL", createIDforAccount);
            if (!TextUtils.isEmpty(str)) {
                intent.putExtra("com.samsung.android.knox.intent.extra.ENFORCE_SMIME_ALIAS_STORAGE_INTERNAL", str);
            }
            z = AccountsReceiver.pushSMIMECertificate("S" + createIDforAccount, new AccountSMIMECertificate(enforceExchangeAccountPermission, str3, null, -1));
            if (z) {
                this.mContext.sendBroadcastAsUser(intent, new UserHandle(callingOrCurrentUserId), "com.samsung.android.knox.permission.SMIME_CERTIFICATE_INTERNAL");
            }
            Log.i("ExchangeAccountPolicy", "setForceSMIMECertificateAlias() : accId = " + j + ", ret = " + z);
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean adminSatisfiesForceSMIMECertificateRules(int i, long j, boolean z, int i2) {
        if (z) {
            String[] strArr = {"adminUid"};
            ContentValues contentValues = new ContentValues();
            contentValues.put(EdmStorageProviderBase.getAdminLUIDWhereIn(0, UserHandle.getUserId(i)), "#SelectClause#");
            contentValues.put("AccountId", Long.valueOf(j));
            if (i2 == 3) {
                contentValues.put("(ForceSmimeCertForSigning=1 OR ForceSmimeCert=1)", "#SelectClause#");
            } else if (i2 == 2) {
                contentValues.put("(ForceSmimeCertForEncryption=1 OR ForceSmimeCert=1)", "#SelectClause#");
            } else {
                contentValues.put("((ForceSmimeCertForEncryption=1 AND ForceSmimeCertForSigning=1) OR (ForceSmimeCert=1))", "#SelectClause#");
            }
            List values = this.mEdmStorageProvider.getValues("ExchangeAccountTable", strArr, contentValues);
            if (values != null && !values.isEmpty() && ((ContentValues) values.get(0)).getAsInteger("adminUid").intValue() != i) {
                return false;
            }
        } else {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("adminUid", Integer.valueOf(i));
            contentValues2.put("AccountId", Long.valueOf(j));
            List values2 = this.mEdmStorageProvider.getValues("ExchangeAccountTable", new String[]{"ForceSmimeCert", "ForceSmimeCertForEncryption", "ForceSmimeCertForSigning"}, contentValues2);
            if (values2 == null || values2.isEmpty()) {
                return false;
            }
            ContentValues contentValues3 = (ContentValues) values2.get(0);
            Integer asInteger = contentValues3.getAsInteger("ForceSmimeCert");
            Integer asInteger2 = contentValues3.getAsInteger("ForceSmimeCertForEncryption");
            Integer asInteger3 = contentValues3.getAsInteger("ForceSmimeCertForSigning");
            if (i2 == 3) {
                if ((asInteger3 == null || asInteger3.intValue() != 1) && (asInteger == null || asInteger.intValue() != 1)) {
                    return false;
                }
            } else if (i2 == 2) {
                if ((asInteger2 == null || asInteger2.intValue() != 1) && (asInteger == null || asInteger.intValue() != 1)) {
                    return false;
                }
            } else if ((asInteger2 == null || asInteger2.intValue() != 1 || asInteger3 == null || asInteger3.intValue() != 1) && (asInteger == null || asInteger.intValue() != 1)) {
                return false;
            }
        }
        return true;
    }

    public String getSMIMECertificateAlias(ContextInfo contextInfo, long j, int i) {
        if (!isValidEASAccountId(contextInfo, j)) {
            Log.i("ExchangeAccountPolicy", "getSMIMECertificateAlias() : No exist accId : " + j);
            return null;
        }
        try {
        } catch (Exception e) {
            Log.e("ExchangeAccountPolicy", "getSMIMECertificateAlias() : failed. ", e);
        }
        if (i == 0) {
            return SettingsUtils.getSMIMEAlias(contextInfo, j, this.mContext, false);
        }
        if (i == 1) {
            return SettingsUtils.getSMIMEAlias(contextInfo, j, this.mContext, true);
        }
        Slog.d("ExchangeAccountPolicy", "getSMIMECertificateAlias() : accId = " + j + ", type = " + i + ", ret = " + ((String) null));
        return null;
    }

    public final int setForceSMIMECertificateInternal(ContextInfo contextInfo, long j, String str, String str2, String str3) {
        int i;
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setForceSMIMECertificateInternal() : " + j + ", " + str3);
        int i2 = enforceExchangeAccountPermission.mContainerId;
        int i3 = enforceExchangeAccountPermission.mCallerUid;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission);
        if (!isValidEASAccountId(enforceExchangeAccountPermission, j)) {
            Log.i("ExchangeAccountPolicy", "setForceSMIMECertificateInternal() : No exist accId : " + j);
            return 3;
        }
        if (str == null || str.isEmpty()) {
            Log.i("ExchangeAccountPolicy", "setForceSMIMECertificateInternal() : certPath is null");
            return 1;
        }
        if (str2 == null || str2.isEmpty()) {
            Log.i("ExchangeAccountPolicy", "setForceSMIMECertificateInternal() : certPassword is null");
            return 2;
        }
        int i4 = 0;
        if (str3 != null) {
            if (str3.equals("com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_FOR_SIGNING_INTERNAL")) {
                i = 3;
            } else {
                i = str3.equals("com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_FOR_ENCRYPTION_INTERNAL") ? 2 : 1;
            }
            if (!adminSatisfiesForceSMIMECertificateRules(i3, j, true, i)) {
                Log.i("ExchangeAccountPolicy", "setForceSMIMECertificateInternal() : a smime certificate has already been enforced on this user");
                return 0;
            }
            synchronized (this) {
                long accountCertificatePassword = setAccountCertificatePassword(enforceExchangeAccountPermission, str2);
                long createIDforAccount = SettingsUtils.createIDforAccount();
                Intent intent = new Intent(str3);
                intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", j);
                intent.putExtra("com.samsung.android.knox.intent.extra.CERT_PATH_INTERNAL", str);
                intent.putExtra("com.samsung.android.knox.intent.extra.CERT_PASSWORD_ID_INTERNAL", accountCertificatePassword);
                intent.putExtra("com.samsung.android.knox.intent.extra.CERT_RESULT_ID_INTERNAL", createIDforAccount);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        this.mContext.sendBroadcastAsUser(intent, new UserHandle(callingOrCurrentUserId), "com.samsung.android.knox.permission.SMIME_CERTIFICATE_INTERNAL");
                        boolean pushSMIMECertificate = AccountsReceiver.pushSMIMECertificate("S" + createIDforAccount, new AccountSMIMECertificate(enforceExchangeAccountPermission, str, str2, SettingsUtils.getSMIMEModeFromAction(str3)));
                        Log.i("ExchangeAccountPolicy", "setForceSMIMECertificateInternal() : sendBroadcast success, accId = " + j);
                        if (pushSMIMECertificate) {
                            i4 = -1;
                        }
                    } catch (Exception e) {
                        Log.e("ExchangeAccountPolicy", "setForceSMIMECertificateInternal() : failed. ", e);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return i4;
        }
        Log.i("ExchangeAccountPolicy", "setForceSMIMECertificateInternal() : Action is null");
        return 0;
    }

    public final boolean setReleaseSMIMECertificateInternal(ContextInfo contextInfo, long j, int i) {
        ContextInfo enforceExchangeAccountPermission = enforceExchangeAccountPermission(contextInfo);
        int i2 = enforceExchangeAccountPermission.mContainerId;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission);
        boolean z = false;
        if (!isValidEASAccountId(enforceExchangeAccountPermission, j)) {
            Log.i("ExchangeAccountPolicy", "setReleaseSMIMECertificateInternal() : No exist accId : " + j);
            return false;
        }
        if (!adminSatisfiesForceSMIMECertificateRules(enforceExchangeAccountPermission.mCallerUid, j, false, i)) {
            Log.i("ExchangeAccountPolicy", "setReleaseSMIMECertificateInternal() : admin " + enforceExchangeAccountPermission.mCallerUid + " is trying to release a certificate which was not enforced by him");
            return false;
        }
        if (i == 1 || i == 2) {
            z = resetForcedSMIMECertificateInternal(enforceExchangeAccountPermission, j, callingOrCurrentUserId, 2, false);
            Log.i("ExchangeAccountPolicy", "setReleaseSMIMECertificateInternal() : release SMIME Encryption = " + z);
        }
        if (i != 1 && i != 3) {
            return z;
        }
        boolean resetForcedSMIMECertificateInternal = resetForcedSMIMECertificateInternal(enforceExchangeAccountPermission, j, callingOrCurrentUserId, 3, false);
        Log.i("ExchangeAccountPolicy", "setReleaseSMIMECertificateInternal() : release SMIME Signing = " + resetForcedSMIMECertificateInternal);
        return resetForcedSMIMECertificateInternal;
    }

    public final boolean resetForcedSMIMECertificateInternal(ContextInfo contextInfo, long j, int i, int i2, boolean z) {
        int i3 = contextInfo.mContainerId;
        int i4 = contextInfo.mCallerUid;
        ContentValues contentValues = new ContentValues();
        if (i2 == 2) {
            contentValues.put("ForceSmimeCertForEncryption", Boolean.FALSE);
            if (!z) {
                sendReleaseSMIMECertificateForEncryption(i3, j, i);
            }
        } else if (i2 == 3) {
            contentValues.put("ForceSmimeCertForSigning", Boolean.FALSE);
            if (!z) {
                sendReleaseSMIMECertificateForSigning(i3, j, i);
            }
        } else {
            Log.i("ExchangeAccountPolicy", "resetForcedSMIMECertificateInternal() : failed. There is unsupport type requested : " + i2);
            return false;
        }
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("adminUid", Integer.valueOf(i4));
        contentValues2.put("AccountId", Long.valueOf(j));
        return this.mEdmStorageProvider.update("ExchangeAccountTable", contentValues, contentValues2) > 0;
    }

    public final boolean isValidEASAccountId(ContextInfo contextInfo, long j) {
        return j > 0 && EmailProviderHelper.getEnterpriseExchangeAccount(this.mContext, contextInfo, j) != null;
    }

    public final UniversalCredentialManagerService getUCMService() {
        if (this.mUCMService == null) {
            this.mUCMService = (UniversalCredentialManagerService) EnterpriseService.getPolicyService("knox_ucsm_policy");
        }
        return this.mUCMService;
    }

    public final void sendSMIMEAliasResultIntent(long j, int i, int i2) {
        Intent intent = new Intent("com.samsung.android.knox.intent.action.ENFORCE_SMIME_ALIAS_RESULT");
        intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", j);
        intent.putExtra("com.samsung.android.knox.intent.extra.SMIME_RESULT", i);
        intent.putExtra("com.samsung.android.knox.intent.extra.ENFORCE_SMIME_ALIAS_TYPE", i2);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_EXCHANGE");
    }

    public final boolean isEmailHTMLAllowed(ContextInfo contextInfo, String str) {
        try {
            IEmailPolicy asInterface = IEmailPolicy.Stub.asInterface(ServiceManager.getService("email_policy"));
            if (asInterface != null) {
                return asInterface.getAllowHTMLEmail(contextInfo, str);
            }
            return true;
        } catch (RemoteException e) {
            Log.e("ExchangeAccountPolicy", "setMaxEmailBodyTruncationSize() : Failed talking with email policy", e);
            return true;
        }
    }
}
