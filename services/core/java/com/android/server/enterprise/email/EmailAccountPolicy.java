package com.android.server.enterprise.email;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.UserHandle;
import android.sec.enterprise.email.EnterpriseEmailAccount;
import android.util.Log;
import android.util.Slog;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.Utils;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.accounts.Account;
import com.samsung.android.knox.accounts.EmailAccount;
import com.samsung.android.knox.accounts.IEmailAccountPolicy;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class EmailAccountPolicy extends IEmailAccountPolicy.Stub implements EnterpriseServiceCallback {
    public Context mContext;
    public EdmStorageProvider mEdmStorageProvider;
    public EnterpriseDeviceManager mEDM = null;
    public int preCallingUid = -1;

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
    public void onPreAdminRemoval(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final ContextInfo enforceEmailAccountPermission(ContextInfo contextInfo) {
        int i = contextInfo.mContainerId;
        ContextInfo enforceActiveAdminPermissionByContext = getEDM().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_EMAIL")));
        int i2 = enforceActiveAdminPermissionByContext.mCallerUid;
        if (this.preCallingUid != i2) {
            String packageNameForUid = this.mEdmStorageProvider.getPackageNameForUid(i2);
            SettingsUtils.setPackageNameForUid(i2, packageNameForUid);
            this.preCallingUid = i2;
            Slog.d("EmailAccountPolicy", "Calling UID changed :    " + packageNameForUid + "  callingUid   " + i2);
        }
        return enforceActiveAdminPermissionByContext;
    }

    public EmailAccountPolicy(Context context) {
        this.mEdmStorageProvider = null;
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
    }

    public long addNewAccount(ContextInfo contextInfo, String str, String str2, String str3, int i, String str4, String str5, String str6, String str7, int i2, String str8, String str9) {
        Slog.d("EmailAccountPolicy", "addNewAccount() EX");
        return addNewAccount_ex(contextInfo, str, str2, str3, i, str4, str5, str6, str7, i2, str8, str9, true, false, false, true, false, false, "Send from SamsungMobile", true);
    }

    public long addNewAccount_ex(ContextInfo contextInfo, String str, String str2, String str3, int i, String str4, String str5, String str6, String str7, int i2, String str8, String str9, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String str10, boolean z7) {
        long j;
        String str11;
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", ">>>>>>>>>>>>>>>>>\t\taddNewAccount EX ");
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceEmailAccountPermission);
        int i3 = enforceEmailAccountPermission.mContainerId;
        String validStr = SettingsUtils.getValidStr(str);
        String validStr2 = SettingsUtils.getValidStr(str3);
        String validStr3 = SettingsUtils.getValidStr(str7);
        String validIncomingProtocol = getValidIncomingProtocol(str2);
        String validOutgoingProtocol = getValidOutgoingProtocol(str6);
        String validStr4 = SettingsUtils.getValidStr(str8);
        String validStr5 = SettingsUtils.getValidStr(str4);
        String validStr6 = SettingsUtils.getValidStr(str9);
        String validStr7 = SettingsUtils.getValidStr(str5);
        if (validStr == null || !SettingsUtils.isValidEmailAddress(validStr) || validStr2 == null || validStr3 == null || validIncomingProtocol == null || validOutgoingProtocol == null || 1 > i || 1 > i2 || validStr4 == null || validStr5 == null) {
            Log.i("EmailAccountPolicy", "addNewAccount() EX : Error :: Invalid input parameters.");
            return -1L;
        }
        if (!SettingsUtils.isPackageInstalled(SettingsUtils.getEmailPackageName(callingOrCurrentUserId), i3)) {
            Log.i("EmailAccountPolicy", "addNewAccount() EX : Error :: Email app is not installed on user " + i3);
            return -1L;
        }
        if (SettingsUtils.getAccountId(enforceEmailAccountPermission, null, validStr, validStr2, validIncomingProtocol, false, this.mContext) > 0) {
            Log.i("EmailAccountPolicy", "addNewAccount() EX : Error :: Account already exists.");
            return -1L;
        }
        String str12 = (!z || z2 || z3) ? "none" : "ssl";
        if (!z && z2 && !z3) {
            str12 = "tls";
        }
        if (z && !z2 && z3) {
            str12 = "ssl+trustallcerts";
        }
        if (!z && z2 && z3) {
            str12 = "tls+trustallcerts";
        }
        String str13 = (z4 && !z5 && z6) ? "ssl+trustallcerts" : (z4 || !z5 || z6) ? (!z4 || z5 || z6) ? "none" : "ssl" : "tls";
        if (!z4 && z5 && z6) {
            str13 = "tls+trustallcerts";
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Log.i("EmailAccountPolicy", "addNewAccount() EX : " + callingOrCurrentUserId);
            Intent intent = new Intent("com.samsung.android.knox.intent.action.CREATE_EMAILACCOUNT_INTERNAL");
            j = clearCallingIdentity;
            try {
                try {
                    long securityOutGoingServerPassword = setSecurityOutGoingServerPassword(enforceEmailAccountPermission, validStr6);
                    try {
                        long securityInComingServerPassword = setSecurityInComingServerPassword(enforceEmailAccountPermission, validStr7);
                        intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL", securityInComingServerPassword);
                        intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", validStr);
                        intent.putExtra("com.samsung.android.knox.intent.extra.USER_PASSWD_ID_INTERNAL", securityInComingServerPassword);
                        intent.putExtra("com.samsung.android.knox.intent.extra.OUTGOING_USER_PASSWD_ID_INTERNAL", securityOutGoingServerPassword);
                        intent.putExtra("com.samsung.android.knox.intent.extra.SERVICE_INTERNAL", validIncomingProtocol);
                        intent.putExtra("com.samsung.android.knox.intent.extra.OUTGOING_SERVICE_INTERNAL", validOutgoingProtocol);
                        intent.putExtra("com.samsung.android.knox.intent.extra.RECEIVE_HOST_INTERNAL", validStr2);
                        intent.putExtra("com.samsung.android.knox.intent.extra.RECEIVE_SECURITY_INTERNAL", str13);
                        intent.putExtra("com.samsung.android.knox.intent.extra.RECEIVE_PORT_INTERNAL", i);
                        intent.putExtra("com.samsung.android.knox.intent.extra.SEND_HOST_INTERNAL", validStr3);
                        intent.putExtra("com.samsung.android.knox.intent.extra.SEND_SECURITY_INTERNAL", str12);
                        intent.putExtra("com.samsung.android.knox.intent.extra.SEND_PORT_INTERNAL", i2);
                        intent.putExtra("com.samsung.android.knox.intent.extra.SENDER_NAME_INTERNAL", validStr5);
                        intent.putExtra("com.samsung.android.knox.intent.extra.OUTGOING_SENDER_NAME_INTERNAL", validStr4);
                        intent.putExtra("com.samsung.android.knox.intent.extra.SIGNATURE_INTERNAL", str10);
                        intent.putExtra("com.samsung.android.knox.intent.extra.EXTRA_NOTIFY_INTERNAL", z7);
                        intent.setPackage(SettingsUtils.getEmailPackageName(i3));
                        this.mContext.sendBroadcastAsUser(intent, new UserHandle(callingOrCurrentUserId), "com.samsung.android.knox.permission.KNOX_EMAIL");
                        str11 = "EmailAccountPolicy";
                        try {
                            Slog.d(str11, " addNewAccount_ex() : sent intent to Email app : " + validStr);
                        } catch (Exception e) {
                            e = e;
                            Log.e(str11, "addNewAccount_ex() : ", e);
                            Binder.restoreCallingIdentity(j);
                            Log.i(str11, "<<<<<<<<<<<<<<<<<\t\taddNewAccount EX : Broadcasting Email");
                            Slog.d(str11, "<<<<<<<<<<<<<<<<<\t\taddNewAccount EX : Broadcasting Email + recvSecurityStr " + str13);
                            return 0L;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        str11 = "EmailAccountPolicy";
                    }
                } catch (Throwable th) {
                    th = th;
                    Binder.restoreCallingIdentity(j);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                str11 = "EmailAccountPolicy";
                Log.e(str11, "addNewAccount_ex() : ", e);
                Binder.restoreCallingIdentity(j);
                Log.i(str11, "<<<<<<<<<<<<<<<<<\t\taddNewAccount EX : Broadcasting Email");
                Slog.d(str11, "<<<<<<<<<<<<<<<<<\t\taddNewAccount EX : Broadcasting Email + recvSecurityStr " + str13);
                return 0L;
            }
        } catch (Exception e4) {
            e = e4;
            j = clearCallingIdentity;
        } catch (Throwable th2) {
            th = th2;
            j = clearCallingIdentity;
            Binder.restoreCallingIdentity(j);
            throw th;
        }
        Binder.restoreCallingIdentity(j);
        Log.i(str11, "<<<<<<<<<<<<<<<<<\t\taddNewAccount EX : Broadcasting Email");
        Slog.d(str11, "<<<<<<<<<<<<<<<<<\t\taddNewAccount EX : Broadcasting Email + recvSecurityStr " + str13);
        return 0L;
    }

    public boolean setAccountName(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        String validStr = SettingsUtils.getValidStr(str);
        EnterpriseEmailAccount enterpriseEmailAccount = EmailProviderHelper.getEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, j);
        if (enterpriseEmailAccount == null) {
            Log.i("EmailAccountPolicy", "setAccountName_new() : No exist accId : " + j);
            return false;
        }
        if (validStr == null) {
            Log.i("EmailAccountPolicy", "setAccountName_new() : accountName is null");
            return false;
        }
        enterpriseEmailAccount.mDisplayName = validStr;
        boolean updateEnterpriseEmailAccount = EmailProviderHelper.updateEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
        Log.i("EmailAccountPolicy", "setAccountName (" + validStr + ", " + j + " )");
        return updateEnterpriseEmailAccount;
    }

    public long setEmailAddress(ContextInfo contextInfo, String str, long j) {
        enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setEmailAddress() : deprecated.");
        return -1L;
    }

    public boolean setSyncInterval(ContextInfo contextInfo, int i, long j) {
        AccountMetaData accountDetails = SettingsUtils.getAccountDetails(enforceEmailAccountPermission(contextInfo), j, this.mContext);
        if (accountDetails == null) {
            Log.i("EmailAccountPolicy", "setSyncInterval() : No exist accId : " + j);
            return false;
        }
        accountDetails.mSyncInterval = i;
        Log.i("EmailAccountPolicy", "setSyncInterval() : deprecated.");
        return false;
    }

    public boolean setSenderName(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr == null) {
            Log.i("EmailAccountPolicy", "setSenderName() : senderName is null");
            return false;
        }
        EnterpriseEmailAccount enterpriseEmailAccount = EmailProviderHelper.getEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, j);
        if (enterpriseEmailAccount == null) {
            Log.i("EmailAccountPolicy", "setEmailAddress_new() : No exist accId : " + j);
            return false;
        }
        enterpriseEmailAccount.mSenderName = validStr;
        return EmailProviderHelper.updateEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public boolean setSignature(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        if (str == null) {
            Log.i("EmailAccountPolicy", "setSignature() : signature is null");
            return false;
        }
        EnterpriseEmailAccount enterpriseEmailAccount = EmailProviderHelper.getEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, j);
        if (enterpriseEmailAccount == null) {
            Log.i("EmailAccountPolicy", "setSignature_new() : No exist accId : " + j);
            return false;
        }
        enterpriseEmailAccount.mSignature = str;
        return EmailProviderHelper.updateEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public boolean setAlwaysVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) {
        Log.i("EmailAccountPolicy", "setAlwaysVibrateOnEmailNotification(" + z + ")");
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        EnterpriseEmailAccount enterpriseEmailAccount = EmailProviderHelper.getEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, j);
        if (enterpriseEmailAccount == null) {
            Log.i("EmailAccountPolicy", "setAlwaysVibrateOnEmailNotification_new() : No exist accId : " + j);
            return false;
        }
        if (z) {
            enterpriseEmailAccount.mEmailNotificationVibrateAlways = true;
            enterpriseEmailAccount.mEmailNotificationVibrateWhenSilent = false;
        } else {
            enterpriseEmailAccount.mEmailNotificationVibrateAlways = false;
        }
        return EmailProviderHelper.updateEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public boolean setSilentVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) {
        enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setSilentVibrateOnEmailNotification() : deprecated.");
        return false;
    }

    public final String getValidIncomingProtocol(String str) {
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr != null) {
            validStr = validStr.toLowerCase();
            if (!validStr.equals("pop3") && !validStr.equals("imap")) {
                validStr = null;
            }
        }
        Slog.d("EmailAccountPolicy", "getValidIncomingProtocol returned  protocol : " + validStr);
        return validStr;
    }

    public boolean setInComingProtocol(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setInComingProtocol()");
        String validIncomingProtocol = getValidIncomingProtocol(str);
        if (validIncomingProtocol == null) {
            Log.i("EmailAccountPolicy", "setInComingProtocol() : Error :: Invalid input parameter.");
            return false;
        }
        EnterpriseEmailAccount enterpriseEmailAccount = EmailProviderHelper.getEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, j);
        if (enterpriseEmailAccount == null) {
            Log.i("EmailAccountPolicy", "setInComingProtocol_new() : No exist accId : " + j);
            return false;
        }
        enterpriseEmailAccount.mInComingProtocol = validIncomingProtocol;
        Slog.d("EmailAccountPolicy", "setInComingProtocol returned  protocol : " + validIncomingProtocol + ", " + j);
        return EmailProviderHelper.updateEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public long setInComingServerAddress(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setInComingServerAddress()");
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr == null) {
            Log.i("EmailAccountPolicy", "setInComingServerAddress() : Error :: Invalid input parameter.");
            return -1L;
        }
        EnterpriseEmailAccount enterpriseEmailAccount = EmailProviderHelper.getEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, j);
        if (enterpriseEmailAccount == null) {
            Log.i("EmailAccountPolicy", "setInComingServerAddress_new() : No exist accId : " + j);
            return -1L;
        }
        enterpriseEmailAccount.mInComingServerAddress = validStr;
        Slog.d("EmailAccountPolicy", "setInComingServerAddress returned  serverAddress : " + validStr);
        if (EmailProviderHelper.updateEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount)) {
            return enterpriseEmailAccount.mId;
        }
        return -1L;
    }

    public boolean setInComingServerPort(ContextInfo contextInfo, int i, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setInComingServerPort()");
        if (i < 0) {
            Log.i("EmailAccountPolicy", "setInComingServerPort() : Error :: Invalid input parameter.");
            return false;
        }
        EnterpriseEmailAccount enterpriseEmailAccount = EmailProviderHelper.getEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, j);
        if (enterpriseEmailAccount == null) {
            Log.i("EmailAccountPolicy", "setInComingServerPort_new() : No exist accId : " + j);
            return false;
        }
        enterpriseEmailAccount.mInComingServerPort = i;
        Slog.d("EmailAccountPolicy", "setInComingServerPort returned  port : " + i);
        return EmailProviderHelper.updateEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public boolean setInComingServerSSL(ContextInfo contextInfo, boolean z, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setInComingServerSSL()");
        EnterpriseEmailAccount enterpriseEmailAccount = EmailProviderHelper.getEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, j);
        if (enterpriseEmailAccount == null) {
            Log.i("EmailAccountPolicy", "setInComingServerSSL_new() : No exist accId : " + j);
            return false;
        }
        enterpriseEmailAccount.mInComingUseSSL = z;
        Slog.d("EmailAccountPolicy", "setInComingServerSSL returned  enableSSL : " + z);
        return EmailProviderHelper.updateEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public boolean setInComingServerAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setInComingServerAcceptAllCertificates()");
        EnterpriseEmailAccount enterpriseEmailAccount = EmailProviderHelper.getEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, j);
        if (enterpriseEmailAccount == null) {
            Log.i("EmailAccountPolicy", "setInComingServerAcceptAllCertificates_new() : No exist accId : " + j);
            return false;
        }
        enterpriseEmailAccount.mInComingAcceptAllCertificates = z;
        Slog.d("EmailAccountPolicy", "setInComingServerAcceptAllCertificates returned  acceptAllCertificates : " + z);
        return EmailProviderHelper.updateEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public long setInComingServerLogin(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setInComingServerLogin() : deprecated. ");
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr == null) {
            Log.i("EmailAccountPolicy", "setInComingServerLogin() : Error :: Invalid input parameter.");
            return -1L;
        }
        AccountMetaData accountDetails = SettingsUtils.getAccountDetails(enforceEmailAccountPermission, j, this.mContext);
        if (accountDetails == null) {
            Log.i("EmailAccountPolicy", "setInComingServerLogin() : No exist accId : " + j);
            return -1L;
        }
        Slog.d("EmailAccountPolicy", "setInComingServerLogin deprecated ");
        accountDetails.mInComingServerLogin = validStr;
        return -1L;
    }

    public boolean setInComingServerPassword(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        int i = enforceEmailAccountPermission.mContainerId;
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr == null) {
            Log.i("EmailAccountPolicy", "setInComingServerPassword : Error :: Invalid input parameter.");
            return false;
        }
        EnterpriseEmailAccount enterpriseEmailAccount = EmailProviderHelper.getEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, j);
        if (enterpriseEmailAccount == null) {
            Log.i("EmailAccountPolicy", "setInComingServerPassword_new() : No exist accId : " + j);
            return false;
        }
        enterpriseEmailAccount.mInComingPassword = validStr;
        return EmailProviderHelper.updateEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public boolean setInComingServerPathPrefix(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setInComingServerPathPrefix() : deprecated");
        String validStr = SettingsUtils.getValidStr(str);
        AccountMetaData accountDetails = SettingsUtils.getAccountDetails(enforceEmailAccountPermission, j, this.mContext);
        if (accountDetails == null) {
            Log.i("EmailAccountPolicy", "setInComingServerPathPrefix() : No exist accId : " + j);
            return false;
        }
        accountDetails.mInComingServerPathPrefix = validStr;
        return false;
    }

    public final String getValidOutgoingProtocol(String str) {
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr != null) {
            validStr = validStr.toLowerCase();
            if (!validStr.equals("smtp")) {
                validStr = null;
            }
        }
        Slog.d("EmailAccountPolicy", "getValidOutgoingProtocol returned  protocol : " + validStr);
        return validStr;
    }

    public boolean setOutGoingProtocol(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setOutGoingProtocol : deprecated.");
        String validOutgoingProtocol = getValidOutgoingProtocol(str);
        if (validOutgoingProtocol == null) {
            Log.i("EmailAccountPolicy", "setOutGoingProtocol : Error :: Invalid input parameter.");
            return false;
        }
        AccountMetaData accountDetails = SettingsUtils.getAccountDetails(enforceEmailAccountPermission, j, this.mContext);
        if (accountDetails == null) {
            Log.i("EmailAccountPolicy", "setOutGoingProtocol() : No exist accId : " + j);
            return false;
        }
        accountDetails.mOutGoingProtocol = validOutgoingProtocol;
        return false;
    }

    public long setOutGoingServerAddress(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr == null) {
            Log.i("EmailAccountPolicy", "setOutGoingServerAddress() : Error :: Invalid input parameter.");
            return -1L;
        }
        EnterpriseEmailAccount enterpriseEmailAccount = EmailProviderHelper.getEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, j);
        if (enterpriseEmailAccount == null) {
            Log.i("EmailAccountPolicy", "setOutGoingServerAddress_new() : No exist accId : " + j);
            return -1L;
        }
        enterpriseEmailAccount.mOutgoingServerAddress = validStr;
        Slog.d("EmailAccountPolicy", "setOutGoingServerAddress() returned  serverAddress : " + validStr);
        if (EmailProviderHelper.updateEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount)) {
            return enterpriseEmailAccount.mId;
        }
        return -1L;
    }

    public boolean setOutGoingServerPort(ContextInfo contextInfo, int i, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        if (i < 0) {
            Log.i("EmailAccountPolicy", "setOutGoingServerPort : Error :: Invalid input parameter.");
            return false;
        }
        EnterpriseEmailAccount enterpriseEmailAccount = EmailProviderHelper.getEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, j);
        if (enterpriseEmailAccount == null) {
            Log.i("EmailAccountPolicy", "setOutGoingServerPort_new() : No exist accId : " + j);
            return false;
        }
        enterpriseEmailAccount.mOutgoingServerPort = i;
        Slog.d("EmailAccountPolicy", "setOutGoingServerPort returned  port : " + i);
        return EmailProviderHelper.updateEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public boolean setOutGoingServerSSL(ContextInfo contextInfo, boolean z, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setOutGoingServerSSL() : " + z);
        EnterpriseEmailAccount enterpriseEmailAccount = EmailProviderHelper.getEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, j);
        if (enterpriseEmailAccount == null) {
            Log.i("EmailAccountPolicy", "setOutGoingServerSSL_new() : No exist accId : " + j);
            return false;
        }
        enterpriseEmailAccount.mOutgoingUseSSL = z;
        Slog.d("EmailAccountPolicy", "setOutGoingServerSSL returned  port : " + z);
        return EmailProviderHelper.updateEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public boolean setOutGoingServerAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setOutGoingServerAcceptAllCertificates() : " + z);
        EnterpriseEmailAccount enterpriseEmailAccount = EmailProviderHelper.getEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, j);
        if (enterpriseEmailAccount == null) {
            Log.i("EmailAccountPolicy", "setOutGoingServerAcceptAllCertificates_new() : No exist accId : " + j);
            return false;
        }
        enterpriseEmailAccount.mOutgoingAcceptAllCertificates = z;
        Slog.d("EmailAccountPolicy", "setOutGoingServerAcceptAllCertificates returned  port : " + z);
        return EmailProviderHelper.updateEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public long setOutGoingServerLogin(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr == null) {
            Log.i("EmailAccountPolicy", "setOutGoingServerLogin() : Error :: Invalid input parameter.");
            return -1L;
        }
        AccountMetaData accountDetails = SettingsUtils.getAccountDetails(enforceEmailAccountPermission, j, this.mContext);
        if (accountDetails == null) {
            Log.i("EmailAccountPolicy", "setOutGoingServerLogin() : No exist accId : " + j);
            return -1L;
        }
        accountDetails.mOutGoingServerLogin = validStr;
        return -1L;
    }

    public boolean setOutGoingServerPassword(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        int i = enforceEmailAccountPermission.mContainerId;
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr == null) {
            Log.i("EmailAccountPolicy", "setOutGoingServerPassword : Error :: Invalid input parameter.");
            return false;
        }
        EnterpriseEmailAccount enterpriseEmailAccount = EmailProviderHelper.getEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, j);
        if (enterpriseEmailAccount == null) {
            Log.i("EmailAccountPolicy", "setOutGoingServerPassword_new() : No exist accId : " + j);
            return false;
        }
        enterpriseEmailAccount.mOutgoingPassword = validStr;
        return EmailProviderHelper.updateEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public boolean setOutGoingServerPathPrefix(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setOutGoingServerPathPrefix() : deprecated. ");
        String validStr = SettingsUtils.getValidStr(str);
        AccountMetaData accountDetails = SettingsUtils.getAccountDetails(enforceEmailAccountPermission, j, this.mContext);
        if (accountDetails == null) {
            Log.i("EmailAccountPolicy", "setOutGoingServerPathPrefix() : No exist accId : " + j);
            return false;
        }
        accountDetails.mOutGoingServerPathPrefix = validStr;
        return false;
    }

    public boolean setAsDefaultAccount(ContextInfo contextInfo, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Slog.d("EmailAccountPolicy", "setAsDefaultAccount :  userID =" + enforceEmailAccountPermission.mCallerUid + ",   accId =" + j);
        EnterpriseEmailAccount enterpriseEmailAccount = EmailProviderHelper.getEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, j);
        if (enterpriseEmailAccount == null) {
            Log.i("EmailAccountPolicy", "setAsDefaultAccount_new() : No exist accId : " + j);
            return false;
        }
        enterpriseEmailAccount.mIsDefault = true;
        return EmailProviderHelper.updateEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public long getAccountId(ContextInfo contextInfo, String str, String str2, String str3) {
        enforceEmailAccountPermission(contextInfo);
        return SettingsUtils.getAccountId(contextInfo, null, str, str2, str3, false, this.mContext);
    }

    public Account getAccountDetails(ContextInfo contextInfo, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "getAccountDetails() : " + j);
        EnterpriseEmailAccount enterpriseEmailAccount = EmailProviderHelper.getEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, j);
        if (enterpriseEmailAccount != null) {
            return SettingsUtils.getAccountFromEnterpriseEmailAccount(enterpriseEmailAccount);
        }
        return null;
    }

    public boolean deleteAccount(ContextInfo contextInfo, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        int i = enforceEmailAccountPermission.mCallerUid;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceEmailAccountPermission);
        int i2 = enforceEmailAccountPermission.mContainerId;
        EnterpriseEmailAccount enterpriseEmailAccount = EmailProviderHelper.getEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission, j);
        boolean z = false;
        if (enterpriseEmailAccount == null) {
            Log.i("EmailAccountPolicy", "deleteAccount_new() : No exist accId : " + j);
            return false;
        }
        if (!SettingsUtils.isAccountRemovalAllowed(enterpriseEmailAccount.mEmailAddress, SettingsUtils.getAccountType(false, 0))) {
            Log.i("EmailAccountPolicy", "deleteAccount_new() : MDM DeviceAccountPolicy restriction - cannot delete account : " + j);
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Intent intent = new Intent("com.samsung.android.knox.intent.action.DELETE_EMAILACCOUNT_INTERNAL");
                intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL", j);
                intent.setPackage(SettingsUtils.getEmailPackageName(i2));
                this.mContext.sendBroadcastAsUser(intent, new UserHandle(callingOrCurrentUserId), "com.samsung.android.knox.permission.KNOX_EMAIL");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z = true;
            } catch (Exception e) {
                Log.e("EmailAccountPolicy", "deleteAccount_new() : failed. ", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
            return z;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public void sendAccountsChangedBroadcast(ContextInfo contextInfo) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        int i = enforceEmailAccountPermission.mCallerUid;
        int i2 = enforceEmailAccountPermission.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        SettingsUtils.sendAccountsChangedBroadcast(i2, this.mContext, i);
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public Account[] getAllEmailAccounts(ContextInfo contextInfo) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "getAllEmailAccounts()");
        ArrayList arrayList = new ArrayList();
        EnterpriseEmailAccount[] allEnterpriseEmailAccount = EmailProviderHelper.getAllEnterpriseEmailAccount(this.mContext, enforceEmailAccountPermission);
        if (allEnterpriseEmailAccount != null) {
            for (EnterpriseEmailAccount enterpriseEmailAccount : allEnterpriseEmailAccount) {
                if (enterpriseEmailAccount != null) {
                    arrayList.add(SettingsUtils.getAccountFromEnterpriseEmailAccount(enterpriseEmailAccount));
                }
            }
        } else {
            Log.i("EmailAccountPolicy", "getAllEmailAccounts_new( ): ids is empty ");
        }
        if (arrayList.size() > 0) {
            return (Account[]) arrayList.toArray(new Account[arrayList.size()]);
        }
        return null;
    }

    public void removePendingAccount(ContextInfo contextInfo, String str, String str2, String str3) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        int i = enforceEmailAccountPermission.mCallerUid;
        int i2 = enforceEmailAccountPermission.mContainerId;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceEmailAccountPermission);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Intent intent = new Intent("com.samsung.android.knox.intent.action.DELETE_NOT_VALIDATED_EMAILACCOUNT_INTERNAL");
                intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", str);
                intent.putExtra("com.samsung.android.knox.intent.extra.SERVICE_INTERNAL", str2);
                intent.putExtra("com.samsung.android.knox.intent.extra.RECEIVE_HOST_INTERNAL", str3);
                intent.setPackage(SettingsUtils.getEmailPackageName(i2));
                this.mContext.sendBroadcastAsUser(intent, new UserHandle(callingOrCurrentUserId), "com.samsung.android.knox.permission.KNOX_EMAIL");
            } catch (Exception e) {
                Log.e("EmailAccountPolicy", "removePendingAccount() : failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public long addNewAccount_new(ContextInfo contextInfo, EmailAccount emailAccount) {
        long j;
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", ">>>>>>>>>>>>>>>>>\t\taddNewAccount NEW ");
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceEmailAccountPermission);
        int i = enforceEmailAccountPermission.mContainerId;
        if (emailAccount == null) {
            Log.i("EmailAccountPolicy", "addNewAccount() NEW : Error :: Invalid Account.");
            return -1L;
        }
        String validStr = SettingsUtils.getValidStr(emailAccount.emailAddress);
        String validStr2 = SettingsUtils.getValidStr(emailAccount.incomingServerAddress);
        String validStr3 = SettingsUtils.getValidStr(emailAccount.outgoingServerAddress);
        String validIncomingProtocol = getValidIncomingProtocol(emailAccount.incomingProtocol);
        String validOutgoingProtocol = getValidOutgoingProtocol(emailAccount.outgoingProtocol);
        String validStr4 = SettingsUtils.getValidStr(emailAccount.incomingServerLogin);
        String validStr5 = SettingsUtils.getValidStr(emailAccount.outgoingServerLogin);
        String validStr6 = SettingsUtils.getValidStr(emailAccount.incomingServerPassword);
        String validStr7 = SettingsUtils.getValidStr(emailAccount.outgoingServerPassword);
        int i2 = emailAccount.incomingServerPort;
        int i3 = emailAccount.outgoingServerPort;
        if (validStr == null || !SettingsUtils.isValidEmailAddress(validStr) || validStr2 == null || validStr3 == null || validIncomingProtocol == null || validOutgoingProtocol == null || 1 > i2 || 1 > i3 || validStr5 == null || validStr4 == null) {
            Log.i("EmailAccountPolicy", "addNewAccount() NEW : Error :: Invalid input parameters.");
            return -1L;
        }
        if (!SettingsUtils.isPackageInstalled(SettingsUtils.getEmailPackageName(callingOrCurrentUserId), i)) {
            Log.i("EmailAccountPolicy", "addNewAccount() EX : Error :: Email app is not installed on user " + i);
            return -1L;
        }
        if (SettingsUtils.getAccountId(enforceEmailAccountPermission, null, validStr, validStr2, validIncomingProtocol, false, this.mContext) > 0) {
            Log.i("EmailAccountPolicy", "addNewAccount() NEW : Error :: Account already exists.");
            return -1L;
        }
        boolean z = emailAccount.outgoingServerUseSSL;
        String str = (!z || emailAccount.outgoingServerUseTLS || emailAccount.outgoingServerAcceptAllCertificates) ? "none" : "ssl";
        if (!z && emailAccount.outgoingServerUseTLS && !emailAccount.outgoingServerAcceptAllCertificates) {
            str = "tls";
        }
        if (z && !emailAccount.outgoingServerUseTLS && emailAccount.outgoingServerAcceptAllCertificates) {
            str = "ssl+trustallcerts";
        }
        if (!z && emailAccount.outgoingServerUseTLS && emailAccount.outgoingServerAcceptAllCertificates) {
            str = "tls+trustallcerts";
        }
        boolean z2 = emailAccount.incomingServerUseSSL;
        String str2 = (!z2 && emailAccount.incomingServerUseTLS && emailAccount.incomingServerAcceptAllCertificates) ? "tls+trustallcerts" : (z2 && !emailAccount.incomingServerUseTLS && emailAccount.incomingServerAcceptAllCertificates) ? "ssl+trustallcerts" : (z2 || !emailAccount.incomingServerUseTLS || emailAccount.incomingServerAcceptAllCertificates) ? (!z2 || emailAccount.incomingServerUseTLS || emailAccount.incomingServerAcceptAllCertificates) ? "none" : "ssl" : "tls";
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Intent intent = new Intent("com.samsung.android.knox.intent.action.CREATE_EMAILACCOUNT_INTERNAL");
            j = clearCallingIdentity;
            try {
                try {
                    long securityOutGoingServerPassword = setSecurityOutGoingServerPassword(enforceEmailAccountPermission, validStr7);
                    long securityInComingServerPassword = setSecurityInComingServerPassword(enforceEmailAccountPermission, validStr6);
                    intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL", securityInComingServerPassword);
                    intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", validStr);
                    intent.putExtra("com.samsung.android.knox.intent.extra.USER_PASSWD_ID_INTERNAL", securityInComingServerPassword);
                    intent.putExtra("com.samsung.android.knox.intent.extra.OUTGOING_USER_PASSWD_ID_INTERNAL", securityOutGoingServerPassword);
                    intent.putExtra("com.samsung.android.knox.intent.extra.SERVICE_INTERNAL", validIncomingProtocol);
                    intent.putExtra("com.samsung.android.knox.intent.extra.OUTGOING_SERVICE_INTERNAL", validOutgoingProtocol);
                    intent.putExtra("com.samsung.android.knox.intent.extra.RECEIVE_HOST_INTERNAL", validStr2);
                    intent.putExtra("com.samsung.android.knox.intent.extra.RECEIVE_SECURITY_INTERNAL", str2);
                    intent.putExtra("com.samsung.android.knox.intent.extra.RECEIVE_PORT_INTERNAL", i2);
                    intent.putExtra("com.samsung.android.knox.intent.extra.SEND_HOST_INTERNAL", validStr3);
                    intent.putExtra("com.samsung.android.knox.intent.extra.SEND_SECURITY_INTERNAL", str);
                    intent.putExtra("com.samsung.android.knox.intent.extra.SEND_PORT_INTERNAL", i3);
                    intent.putExtra("com.samsung.android.knox.intent.extra.SENDER_NAME_INTERNAL", validStr4);
                    intent.putExtra("com.samsung.android.knox.intent.extra.OUTGOING_SENDER_NAME_INTERNAL", validStr5);
                    intent.putExtra("com.samsung.android.knox.intent.extra.SIGNATURE_INTERNAL", emailAccount.signature);
                    intent.putExtra("com.samsung.android.knox.intent.extra.EXTRA_NOTIFY_INTERNAL", emailAccount.isNotify);
                    intent.setPackage(SettingsUtils.getEmailPackageName(i));
                    this.mContext.sendBroadcastAsUser(intent, new UserHandle(callingOrCurrentUserId), "com.samsung.android.knox.permission.KNOX_EMAIL");
                    Slog.d("EmailAccountPolicy", " addNewAccount() NEW : sent intent to Email app : " + validStr);
                } catch (Exception e) {
                    e = e;
                    Log.e("EmailAccountPolicy", "addNewAccount() NEW :  failed. ", e);
                    Binder.restoreCallingIdentity(j);
                    Log.i("EmailAccountPolicy", "<<<<<<<<<<<<<<<<<\t\taddNewAccount NEW : Broadcasting Email");
                    Slog.d("EmailAccountPolicy", "<<<<<<<<<<<<<<<<<\t\taddNewAccount NEW : Broadcasting Email +  recvSecurityStr " + str2);
                    return 0L;
                }
            } catch (Throwable th) {
                th = th;
                Binder.restoreCallingIdentity(j);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            j = clearCallingIdentity;
        } catch (Throwable th2) {
            th = th2;
            j = clearCallingIdentity;
            Binder.restoreCallingIdentity(j);
            throw th;
        }
        Binder.restoreCallingIdentity(j);
        Log.i("EmailAccountPolicy", "<<<<<<<<<<<<<<<<<\t\taddNewAccount NEW : Broadcasting Email");
        Slog.d("EmailAccountPolicy", "<<<<<<<<<<<<<<<<<\t\taddNewAccount NEW : Broadcasting Email +  recvSecurityStr " + str2);
        return 0L;
    }

    public String getSecurityOutGoingServerPassword(ContextInfo contextInfo, long j) {
        int i = getEDM().enforcePermissionByContext(contextInfo, "com.samsung.android.knox.permission.KNOX_EMAIL").mContainerId;
        try {
            String securityPassword = SettingsUtils.getSecurityPassword("O#" + j);
            Slog.d("EmailAccountPolicy", "getSecurityOutGoingServerPassword() success");
            return securityPassword;
        } catch (Exception e) {
            Log.e("EmailAccountPolicy", "getSecurityOutGoingServerPassword() failed", e);
            return null;
        }
    }

    public String getSecurityInComingServerPassword(ContextInfo contextInfo, long j) {
        int i = getEDM().enforcePermissionByContext(contextInfo, "com.samsung.android.knox.permission.KNOX_EMAIL").mContainerId;
        try {
            String securityPassword = SettingsUtils.getSecurityPassword("I#" + j);
            Slog.d("EmailAccountPolicy", "getSecurityInComingServerPassword() success");
            return securityPassword;
        } catch (Exception e) {
            Log.e("EmailAccountPolicy", "getSecurityInComingServerPassword() failed ", e);
            return null;
        }
    }

    public long setSecurityOutGoingServerPassword(ContextInfo contextInfo, String str) {
        getEDM().enforcePermissionByContext(contextInfo, "com.samsung.android.knox.permission.KNOX_EMAIL");
        long createIDforAccount = SettingsUtils.createIDforAccount();
        if (str == null) {
            Log.i("EmailAccountPolicy", "setSecurityOutGoingServerPassword() : password is null");
            return -1L;
        }
        try {
            try {
                SettingsUtils.setSecurityPassword("O#" + createIDforAccount, str);
                Slog.d("EmailAccountPolicy", "setSecurityOutGoingServerPassword() success");
                return createIDforAccount;
            } catch (Exception e) {
                Log.e("EmailAccountPolicy", "setSecurityOutGoingServerPassword() failed", e);
                return createIDforAccount;
            }
        } catch (Throwable unused) {
            return createIDforAccount;
        }
    }

    public long setSecurityInComingServerPassword(ContextInfo contextInfo, String str) {
        getEDM().enforcePermissionByContext(contextInfo, "com.samsung.android.knox.permission.KNOX_EMAIL");
        long createIDforAccount = SettingsUtils.createIDforAccount();
        if (str == null) {
            Log.i("EmailAccountPolicy", "setSecurityInComingServerPassword() : password is null");
            return -1L;
        }
        try {
            try {
                SettingsUtils.setSecurityPassword("I#" + createIDforAccount, str);
                Slog.d("EmailAccountPolicy", "setSecurityInComingServerPassword() success");
                return createIDforAccount;
            } catch (Exception e) {
                Log.e("EmailAccountPolicy", "setSecurityInComingServerPassword() failed", e);
                return createIDforAccount;
            }
        } catch (Throwable unused) {
            return createIDforAccount;
        }
    }
}
