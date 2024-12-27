package com.android.server.enterprise.email;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.UserHandle;
import android.sec.enterprise.email.EnterpriseEmailAccount;
import android.util.Log;
import android.util.Slog;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.chimera.genie.GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.security.DeviceAccountPolicy;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.Utils;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.accounts.Account;
import com.samsung.android.knox.accounts.EmailAccount;
import com.samsung.android.knox.accounts.IEmailAccountPolicy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public final class EmailAccountPolicy extends IEmailAccountPolicy.Stub
        implements EnterpriseServiceCallback {
    public Context mContext;
    public EnterpriseDeviceManager mEDM;
    public EdmStorageProvider mEdmStorageProvider;
    public int preCallingUid;

    public static String getValidIncomingProtocol(String str) {
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr != null) {
            validStr = validStr.toLowerCase();
            if (!validStr.equals("pop3") && !validStr.equals("imap")) {
                validStr = null;
            }
        }
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                "getValidIncomingProtocol returned  protocol : ", validStr, "EmailAccountPolicy");
        return validStr;
    }

    public static String getValidOutgoingProtocol(String str) {
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr != null) {
            validStr = validStr.toLowerCase();
            if (!validStr.equals("smtp")) {
                validStr = null;
            }
        }
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                "getValidOutgoingProtocol returned  protocol : ", validStr, "EmailAccountPolicy");
        return validStr;
    }

    public final long addNewAccount(
            ContextInfo contextInfo,
            String str,
            String str2,
            String str3,
            int i,
            String str4,
            String str5,
            String str6,
            String str7,
            int i2,
            String str8,
            String str9) {
        Slog.d("EmailAccountPolicy", "addNewAccount() EX");
        return addNewAccount_ex(
                contextInfo,
                str,
                str2,
                str3,
                i,
                str4,
                str5,
                str6,
                str7,
                i2,
                str8,
                str9,
                true,
                false,
                false,
                true,
                false,
                false,
                "Send from SamsungMobile",
                true);
    }

    public final long addNewAccount_ex(
            ContextInfo contextInfo,
            String str,
            String str2,
            String str3,
            int i,
            String str4,
            String str5,
            String str6,
            String str7,
            int i2,
            String str8,
            String str9,
            boolean z,
            boolean z2,
            boolean z3,
            boolean z4,
            boolean z5,
            boolean z6,
            String str10,
            boolean z7) {
        long j;
        Intent intent;
        long securityOutGoingServerPassword;
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
        if (validStr == null
                || !SettingsUtils.isValidEmailAddress(validStr)
                || validStr2 == null
                || validStr3 == null
                || validIncomingProtocol == null
                || validOutgoingProtocol == null
                || 1 > i
                || 1 > i2
                || validStr4 == null
                || validStr5 == null) {
            Log.i("EmailAccountPolicy", "addNewAccount() EX : Error :: Invalid input parameters.");
            return -1L;
        }
        if (!SettingsUtils.isPackageInstalled(i3, "com.samsung.android.email.provider")) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i3,
                    "addNewAccount() EX : Error :: Email app is not installed on user ",
                    "EmailAccountPolicy");
            return -1L;
        }
        if (SettingsUtils.getAccountId(
                        enforceEmailAccountPermission,
                        null,
                        validStr,
                        validStr2,
                        validIncomingProtocol,
                        false,
                        this.mContext)
                > 0) {
            Log.i("EmailAccountPolicy", "addNewAccount() EX : Error :: Account already exists.");
            return -1L;
        }
        String str11 = (!z || z2 || z3) ? "none" : "ssl";
        if (!z && z2 && !z3) {
            str11 = "tls";
        }
        if (z && !z2 && z3) {
            str11 = "ssl+trustallcerts";
        }
        if (!z && z2 && z3) {
            str11 = "tls+trustallcerts";
        }
        String str12 =
                (z4 && !z5 && z6)
                        ? "ssl+trustallcerts"
                        : (z4 || !z5 || z6) ? (!z4 || z5 || z6) ? "none" : "ssl" : "tls";
        if (!z4 && z5 && z6) {
            str12 = "tls+trustallcerts";
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Log.i("EmailAccountPolicy", "addNewAccount() EX : " + callingOrCurrentUserId);
                intent =
                        new Intent(
                                "com.samsung.android.knox.intent.action.CREATE_EMAILACCOUNT_INTERNAL");
                securityOutGoingServerPassword =
                        setSecurityOutGoingServerPassword(enforceEmailAccountPermission, validStr6);
                j = clearCallingIdentity;
            } catch (Exception e) {
                e = e;
                j = clearCallingIdentity;
            } catch (Throwable th) {
                th = th;
                str4 = clearCallingIdentity;
                Binder.restoreCallingIdentity(str4);
                throw th;
            }
            try {
                long securityInComingServerPassword =
                        setSecurityInComingServerPassword(enforceEmailAccountPermission, validStr7);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL",
                        securityInComingServerPassword);
                intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", validStr);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.USER_PASSWD_ID_INTERNAL",
                        securityInComingServerPassword);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.OUTGOING_USER_PASSWD_ID_INTERNAL",
                        securityOutGoingServerPassword);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.SERVICE_INTERNAL",
                        validIncomingProtocol);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.OUTGOING_SERVICE_INTERNAL",
                        validOutgoingProtocol);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.RECEIVE_HOST_INTERNAL", validStr2);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.RECEIVE_SECURITY_INTERNAL", str12);
                intent.putExtra("com.samsung.android.knox.intent.extra.RECEIVE_PORT_INTERNAL", i);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.SEND_HOST_INTERNAL", validStr3);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.SEND_SECURITY_INTERNAL", str11);
                intent.putExtra("com.samsung.android.knox.intent.extra.SEND_PORT_INTERNAL", i2);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.SENDER_NAME_INTERNAL", validStr5);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.OUTGOING_SENDER_NAME_INTERNAL",
                        validStr4);
                intent.putExtra("com.samsung.android.knox.intent.extra.SIGNATURE_INTERNAL", str10);
                intent.putExtra("com.samsung.android.knox.intent.extra.EXTRA_NOTIFY_INTERNAL", z7);
                intent.setPackage("com.samsung.android.email.provider");
                this.mContext.sendBroadcastAsUser(
                        intent,
                        new UserHandle(callingOrCurrentUserId),
                        "com.samsung.android.knox.permission.KNOX_EMAIL");
                Slog.d(
                        "EmailAccountPolicy",
                        " addNewAccount_ex() : sent intent to Email app : ".concat(validStr));
                str4 = j;
            } catch (Exception e2) {
                e = e2;
                Log.e("EmailAccountPolicy", "addNewAccount_ex() : ", e);
                str4 = j;
                Binder.restoreCallingIdentity(str4);
                Log.i(
                        "EmailAccountPolicy",
                        "<<<<<<<<<<<<<<<<<\t\taddNewAccount EX : Broadcasting Email");
                Slog.d(
                        "EmailAccountPolicy",
                        "<<<<<<<<<<<<<<<<<\t\taddNewAccount EX : Broadcasting Email + recvSecurityStr "
                                .concat(str12));
                return 0L;
            }
            Binder.restoreCallingIdentity(str4);
            Log.i(
                    "EmailAccountPolicy",
                    "<<<<<<<<<<<<<<<<<\t\taddNewAccount EX : Broadcasting Email");
            Slog.d(
                    "EmailAccountPolicy",
                    "<<<<<<<<<<<<<<<<<\t\taddNewAccount EX : Broadcasting Email + recvSecurityStr "
                            .concat(str12));
            return 0L;
        } catch (Throwable th2) {
            th = th2;
            Binder.restoreCallingIdentity(str4);
            throw th;
        }
    }

    public final long addNewAccount_new(ContextInfo contextInfo, EmailAccount emailAccount) {
        long j;
        Intent intent;
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
        if (validStr == null
                || !SettingsUtils.isValidEmailAddress(validStr)
                || validStr2 == null
                || validStr3 == null
                || validIncomingProtocol == null
                || validOutgoingProtocol == null
                || 1 > i2
                || 1 > i3
                || validStr5 == null
                || validStr4 == null) {
            Log.i("EmailAccountPolicy", "addNewAccount() NEW : Error :: Invalid input parameters.");
            return -1L;
        }
        if (!SettingsUtils.isPackageInstalled(i, "com.samsung.android.email.provider")) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i,
                    "addNewAccount() EX : Error :: Email app is not installed on user ",
                    "EmailAccountPolicy");
            return -1L;
        }
        if (SettingsUtils.getAccountId(
                        enforceEmailAccountPermission,
                        null,
                        validStr,
                        validStr2,
                        validIncomingProtocol,
                        false,
                        this.mContext)
                > 0) {
            Log.i("EmailAccountPolicy", "addNewAccount() NEW : Error :: Account already exists.");
            return -1L;
        }
        boolean z = emailAccount.outgoingServerUseSSL;
        String str =
                (!z
                                || emailAccount.outgoingServerUseTLS
                                || emailAccount.outgoingServerAcceptAllCertificates)
                        ? "none"
                        : "ssl";
        if (!z
                && emailAccount.outgoingServerUseTLS
                && !emailAccount.outgoingServerAcceptAllCertificates) {
            str = "tls";
        }
        if (z
                && !emailAccount.outgoingServerUseTLS
                && emailAccount.outgoingServerAcceptAllCertificates) {
            str = "ssl+trustallcerts";
        }
        if (!z
                && emailAccount.outgoingServerUseTLS
                && emailAccount.outgoingServerAcceptAllCertificates) {
            str = "tls+trustallcerts";
        }
        boolean z2 = emailAccount.incomingServerUseSSL;
        String str2 =
                (!z2
                                && emailAccount.incomingServerUseTLS
                                && emailAccount.incomingServerAcceptAllCertificates)
                        ? "tls+trustallcerts"
                        : (z2
                                        && !emailAccount.incomingServerUseTLS
                                        && emailAccount.incomingServerAcceptAllCertificates)
                                ? "ssl+trustallcerts"
                                : (z2
                                                || !emailAccount.incomingServerUseTLS
                                                || emailAccount.incomingServerAcceptAllCertificates)
                                        ? (!z2
                                                        || emailAccount.incomingServerUseTLS
                                                        || emailAccount
                                                                .incomingServerAcceptAllCertificates)
                                                ? "none"
                                                : "ssl"
                                        : "tls";
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            intent =
                    new Intent(
                            "com.samsung.android.knox.intent.action.CREATE_EMAILACCOUNT_INTERNAL");
            j = clearCallingIdentity;
        } catch (Exception e) {
            e = e;
            j = clearCallingIdentity;
        } catch (Throwable th) {
            th = th;
            j = clearCallingIdentity;
            Binder.restoreCallingIdentity(j);
            throw th;
        }
        try {
            try {
                long securityOutGoingServerPassword =
                        setSecurityOutGoingServerPassword(enforceEmailAccountPermission, validStr7);
                long securityInComingServerPassword =
                        setSecurityInComingServerPassword(enforceEmailAccountPermission, validStr6);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL",
                        securityInComingServerPassword);
                intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", validStr);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.USER_PASSWD_ID_INTERNAL",
                        securityInComingServerPassword);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.OUTGOING_USER_PASSWD_ID_INTERNAL",
                        securityOutGoingServerPassword);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.SERVICE_INTERNAL",
                        validIncomingProtocol);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.OUTGOING_SERVICE_INTERNAL",
                        validOutgoingProtocol);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.RECEIVE_HOST_INTERNAL", validStr2);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.RECEIVE_SECURITY_INTERNAL", str2);
                intent.putExtra("com.samsung.android.knox.intent.extra.RECEIVE_PORT_INTERNAL", i2);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.SEND_HOST_INTERNAL", validStr3);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.SEND_SECURITY_INTERNAL", str);
                intent.putExtra("com.samsung.android.knox.intent.extra.SEND_PORT_INTERNAL", i3);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.SENDER_NAME_INTERNAL", validStr4);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.OUTGOING_SENDER_NAME_INTERNAL",
                        validStr5);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.SIGNATURE_INTERNAL",
                        emailAccount.signature);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.EXTRA_NOTIFY_INTERNAL",
                        emailAccount.isNotify);
                intent.setPackage("com.samsung.android.email.provider");
                this.mContext.sendBroadcastAsUser(
                        intent,
                        new UserHandle(callingOrCurrentUserId),
                        "com.samsung.android.knox.permission.KNOX_EMAIL");
                Slog.d(
                        "EmailAccountPolicy",
                        " addNewAccount() NEW : sent intent to Email app : ".concat(validStr));
            } catch (Throwable th2) {
                th = th2;
                Binder.restoreCallingIdentity(j);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            Log.e("EmailAccountPolicy", "addNewAccount() NEW :  failed. ", e);
            Binder.restoreCallingIdentity(j);
            Log.i(
                    "EmailAccountPolicy",
                    "<<<<<<<<<<<<<<<<<\t\taddNewAccount NEW : Broadcasting Email");
            Slog.d(
                    "EmailAccountPolicy",
                    "<<<<<<<<<<<<<<<<<\t\taddNewAccount NEW : Broadcasting Email +  recvSecurityStr "
                            .concat(str2));
            return 0L;
        }
        Binder.restoreCallingIdentity(j);
        Log.i("EmailAccountPolicy", "<<<<<<<<<<<<<<<<<\t\taddNewAccount NEW : Broadcasting Email");
        Slog.d(
                "EmailAccountPolicy",
                "<<<<<<<<<<<<<<<<<\t\taddNewAccount NEW : Broadcasting Email +  recvSecurityStr "
                        .concat(str2));
        return 0L;
    }

    public final boolean deleteAccount(ContextInfo contextInfo, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceEmailAccountPermission);
        EnterpriseEmailAccount enterpriseEmailAccount =
                EmailProviderHelper.getEnterpriseEmailAccount(
                        j, this.mContext, enforceEmailAccountPermission);
        boolean z = false;
        if (enterpriseEmailAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "deleteAccount_new() : No exist accId : ", j, "EmailAccountPolicy");
            return false;
        }
        String str = enterpriseEmailAccount.mEmailAddress;
        Context context = SettingsUtils.emails;
        DeviceAccountPolicy deviceAccountPolicy =
                (DeviceAccountPolicy) EnterpriseService.getPolicyService("device_account_policy");
        if (deviceAccountPolicy != null
                && !deviceAccountPolicy.isAccountRemovalAllowed(
                        "com.samsung.android.email", str, false)) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "deleteAccount_new() : MDM DeviceAccountPolicy restriction - cannot delete"
                        + " account : ",
                    j,
                    "EmailAccountPolicy");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Intent intent =
                        new Intent(
                                "com.samsung.android.knox.intent.action.DELETE_EMAILACCOUNT_INTERNAL");
                intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL", j);
                intent.setPackage("com.samsung.android.email.provider");
                this.mContext.sendBroadcastAsUser(
                        intent,
                        new UserHandle(callingOrCurrentUserId),
                        "com.samsung.android.knox.permission.KNOX_EMAIL");
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

    public final ContextInfo enforceEmailAccountPermission(ContextInfo contextInfo) {
        int i = contextInfo.mContainerId;
        ContextInfo enforceActiveAdminPermissionByContext =
                getEDM$11()
                        .enforceActiveAdminPermissionByContext(
                                contextInfo,
                                new ArrayList(
                                        Arrays.asList(
                                                "com.samsung.android.knox.permission.KNOX_EMAIL")));
        int i2 = enforceActiveAdminPermissionByContext.mCallerUid;
        if (this.preCallingUid != i2) {
            String packageNameForUid = this.mEdmStorageProvider.getPackageNameForUid(i2);
            SettingsUtils.preCallingUid = i2;
            SettingsUtils.adminPkg = packageNameForUid;
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    new StringBuilder("setPackageNameForUid :   "),
                    SettingsUtils.adminPkg,
                    "SettingsUtils");
            this.preCallingUid = i2;
            Slog.d(
                    "EmailAccountPolicy",
                    "Calling UID changed :    " + packageNameForUid + "  callingUid   " + i2);
        }
        return enforceActiveAdminPermissionByContext;
    }

    public final Account getAccountDetails(ContextInfo contextInfo, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                "getAccountDetails() : ", j, "EmailAccountPolicy");
        EnterpriseEmailAccount enterpriseEmailAccount =
                EmailProviderHelper.getEnterpriseEmailAccount(
                        j, this.mContext, enforceEmailAccountPermission);
        if (enterpriseEmailAccount != null) {
            return SettingsUtils.getAccountFromEnterpriseEmailAccount(enterpriseEmailAccount);
        }
        return null;
    }

    public final long getAccountId(ContextInfo contextInfo, String str, String str2, String str3) {
        enforceEmailAccountPermission(contextInfo);
        return SettingsUtils.getAccountId(contextInfo, null, str, str2, str3, false, this.mContext);
    }

    public final Account[] getAllEmailAccounts(ContextInfo contextInfo) {
        EnterpriseEmailAccount[] enterpriseEmailAccountArr;
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "getAllEmailAccounts()");
        ArrayList arrayList = new ArrayList();
        Context context = this.mContext;
        Map map = EmailProviderHelper.mAccountObjectMap;
        ArrayList arrayList2 = new ArrayList();
        long[] allAccountIDS =
                EmailProviderHelper.getAllAccountIDS(context, enforceEmailAccountPermission);
        if (allAccountIDS != null) {
            for (long j : allAccountIDS) {
                EnterpriseEmailAccount enterpriseEmailAccount =
                        EmailProviderHelper.getEnterpriseEmailAccount(
                                j, context, enforceEmailAccountPermission);
                if (enterpriseEmailAccount != null) {
                    arrayList2.add(enterpriseEmailAccount);
                }
            }
        }
        if (arrayList2.size() > 0) {
            enterpriseEmailAccountArr = new EnterpriseEmailAccount[arrayList2.size()];
            Iterator it = arrayList2.iterator();
            int i = 0;
            while (it.hasNext()) {
                enterpriseEmailAccountArr[i] = (EnterpriseEmailAccount) it.next();
                i++;
            }
        } else {
            enterpriseEmailAccountArr = null;
        }
        if (enterpriseEmailAccountArr != null) {
            for (EnterpriseEmailAccount enterpriseEmailAccount2 : enterpriseEmailAccountArr) {
                if (enterpriseEmailAccount2 != null) {
                    arrayList.add(
                            SettingsUtils.getAccountFromEnterpriseEmailAccount(
                                    enterpriseEmailAccount2));
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

    public final EnterpriseDeviceManager getEDM$11() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final String getSecurityInComingServerPassword(ContextInfo contextInfo, long j) {
        int i =
                getEDM$11()
                        .enforcePermissionByContext(
                                contextInfo, "com.samsung.android.knox.permission.KNOX_EMAIL")
                        .mContainerId;
        try {
            String securityPassword = SettingsUtils.getSecurityPassword("I#" + j);
            Slog.d("EmailAccountPolicy", "getSecurityInComingServerPassword() success");
            return securityPassword;
        } catch (Exception e) {
            Log.e("EmailAccountPolicy", "getSecurityInComingServerPassword() failed ", e);
            return null;
        }
    }

    public final String getSecurityOutGoingServerPassword(ContextInfo contextInfo, long j) {
        int i =
                getEDM$11()
                        .enforcePermissionByContext(
                                contextInfo, "com.samsung.android.knox.permission.KNOX_EMAIL")
                        .mContainerId;
        try {
            String securityPassword = SettingsUtils.getSecurityPassword("O#" + j);
            Slog.d("EmailAccountPolicy", "getSecurityOutGoingServerPassword() success");
            return securityPassword;
        } catch (Exception e) {
            Log.e("EmailAccountPolicy", "getSecurityOutGoingServerPassword() failed", e);
            return null;
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {}

    public final void removePendingAccount(
            ContextInfo contextInfo, String str, String str2, String str3) {
        int callingOrCurrentUserId =
                Utils.getCallingOrCurrentUserId(enforceEmailAccountPermission(contextInfo));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Intent intent =
                        new Intent(
                                "com.samsung.android.knox.intent.action.DELETE_NOT_VALIDATED_EMAILACCOUNT_INTERNAL");
                intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", str);
                intent.putExtra("com.samsung.android.knox.intent.extra.SERVICE_INTERNAL", str2);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.RECEIVE_HOST_INTERNAL", str3);
                Context context = SettingsUtils.emails;
                intent.setPackage("com.samsung.android.email.provider");
                this.mContext.sendBroadcastAsUser(
                        intent,
                        new UserHandle(callingOrCurrentUserId),
                        "com.samsung.android.knox.permission.KNOX_EMAIL");
            } catch (Exception e) {
                Log.e("EmailAccountPolicy", "removePendingAccount() : failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void sendAccountsChangedBroadcast(ContextInfo contextInfo) {
        int i = enforceEmailAccountPermission(contextInfo).mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        SettingsUtils.sendAccountsChangedBroadcast(this.mContext, i);
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final boolean setAccountName(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        String validStr = SettingsUtils.getValidStr(str);
        EnterpriseEmailAccount enterpriseEmailAccount =
                EmailProviderHelper.getEnterpriseEmailAccount(
                        j, this.mContext, enforceEmailAccountPermission);
        if (enterpriseEmailAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setAccountName_new() : No exist accId : ", j, "EmailAccountPolicy");
            return false;
        }
        if (validStr == null) {
            Log.i("EmailAccountPolicy", "setAccountName_new() : accountName is null");
            return false;
        }
        enterpriseEmailAccount.mDisplayName = validStr;
        boolean updateEnterpriseEmailAccount =
                EmailProviderHelper.updateEnterpriseEmailAccount(
                        this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
        StringBuilder sb = new StringBuilder("setAccountName (");
        sb.append(validStr);
        sb.append(", ");
        sb.append(j);
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, " )", "EmailAccountPolicy");
        return updateEnterpriseEmailAccount;
    }

    public final boolean setAlwaysVibrateOnEmailNotification(
            ContextInfo contextInfo, boolean z, long j) {
        Log.i("EmailAccountPolicy", "setAlwaysVibrateOnEmailNotification(" + z + ")");
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        EnterpriseEmailAccount enterpriseEmailAccount =
                EmailProviderHelper.getEnterpriseEmailAccount(
                        j, this.mContext, enforceEmailAccountPermission);
        if (enterpriseEmailAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setAlwaysVibrateOnEmailNotification_new() : No exist accId : ",
                    j,
                    "EmailAccountPolicy");
            return false;
        }
        if (z) {
            enterpriseEmailAccount.mEmailNotificationVibrateAlways = true;
            enterpriseEmailAccount.mEmailNotificationVibrateWhenSilent = false;
        } else {
            enterpriseEmailAccount.mEmailNotificationVibrateAlways = false;
        }
        return EmailProviderHelper.updateEnterpriseEmailAccount(
                this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public final boolean setAsDefaultAccount(ContextInfo contextInfo, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Slog.d(
                "EmailAccountPolicy",
                "setAsDefaultAccount :  userID ="
                        + enforceEmailAccountPermission.mCallerUid
                        + ",   accId ="
                        + j);
        EnterpriseEmailAccount enterpriseEmailAccount =
                EmailProviderHelper.getEnterpriseEmailAccount(
                        j, this.mContext, enforceEmailAccountPermission);
        if (enterpriseEmailAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setAsDefaultAccount_new() : No exist accId : ", j, "EmailAccountPolicy");
            return false;
        }
        enterpriseEmailAccount.mIsDefault = true;
        return EmailProviderHelper.updateEnterpriseEmailAccount(
                this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public final long setEmailAddress(ContextInfo contextInfo, String str, long j) {
        enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setEmailAddress() : deprecated.");
        return -1L;
    }

    public final boolean setInComingProtocol(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setInComingProtocol()");
        String validIncomingProtocol = getValidIncomingProtocol(str);
        if (validIncomingProtocol == null) {
            Log.i(
                    "EmailAccountPolicy",
                    "setInComingProtocol() : Error :: Invalid input parameter.");
            return false;
        }
        EnterpriseEmailAccount enterpriseEmailAccount =
                EmailProviderHelper.getEnterpriseEmailAccount(
                        j, this.mContext, enforceEmailAccountPermission);
        if (enterpriseEmailAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setInComingProtocol_new() : No exist accId : ", j, "EmailAccountPolicy");
            return false;
        }
        enterpriseEmailAccount.mInComingProtocol = validIncomingProtocol;
        Slog.d(
                "EmailAccountPolicy",
                "setInComingProtocol returned  protocol : " + validIncomingProtocol + ", " + j);
        return EmailProviderHelper.updateEnterpriseEmailAccount(
                this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public final boolean setInComingServerAcceptAllCertificates(
            ContextInfo contextInfo, boolean z, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setInComingServerAcceptAllCertificates()");
        EnterpriseEmailAccount enterpriseEmailAccount =
                EmailProviderHelper.getEnterpriseEmailAccount(
                        j, this.mContext, enforceEmailAccountPermission);
        if (enterpriseEmailAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setInComingServerAcceptAllCertificates_new() : No exist accId : ",
                    j,
                    "EmailAccountPolicy");
            return false;
        }
        enterpriseEmailAccount.mInComingAcceptAllCertificates = z;
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "setInComingServerAcceptAllCertificates returned  acceptAllCertificates : ",
                "EmailAccountPolicy",
                z);
        return EmailProviderHelper.updateEnterpriseEmailAccount(
                this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public final long setInComingServerAddress(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setInComingServerAddress()");
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr == null) {
            Log.i(
                    "EmailAccountPolicy",
                    "setInComingServerAddress() : Error :: Invalid input parameter.");
            return -1L;
        }
        EnterpriseEmailAccount enterpriseEmailAccount =
                EmailProviderHelper.getEnterpriseEmailAccount(
                        j, this.mContext, enforceEmailAccountPermission);
        if (enterpriseEmailAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setInComingServerAddress_new() : No exist accId : ", j, "EmailAccountPolicy");
            return -1L;
        }
        enterpriseEmailAccount.mInComingServerAddress = validStr;
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                "setInComingServerAddress returned  serverAddress : ",
                validStr,
                "EmailAccountPolicy");
        if (EmailProviderHelper.updateEnterpriseEmailAccount(
                this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount)) {
            return enterpriseEmailAccount.mId;
        }
        return -1L;
    }

    public final long setInComingServerLogin(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setInComingServerLogin() : deprecated. ");
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr == null) {
            Log.i(
                    "EmailAccountPolicy",
                    "setInComingServerLogin() : Error :: Invalid input parameter.");
            return -1L;
        }
        AccountMetaData accountDetails =
                SettingsUtils.getAccountDetails(j, this.mContext, enforceEmailAccountPermission);
        if (accountDetails == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setInComingServerLogin() : No exist accId : ", j, "EmailAccountPolicy");
            return -1L;
        }
        Slog.d("EmailAccountPolicy", "setInComingServerLogin deprecated ");
        accountDetails.mInComingServerLogin = validStr;
        return -1L;
    }

    public final boolean setInComingServerPassword(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr == null) {
            Log.i(
                    "EmailAccountPolicy",
                    "setInComingServerPassword : Error :: Invalid input parameter.");
            return false;
        }
        EnterpriseEmailAccount enterpriseEmailAccount =
                EmailProviderHelper.getEnterpriseEmailAccount(
                        j, this.mContext, enforceEmailAccountPermission);
        if (enterpriseEmailAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setInComingServerPassword_new() : No exist accId : ", j, "EmailAccountPolicy");
            return false;
        }
        enterpriseEmailAccount.mInComingPassword = validStr;
        return EmailProviderHelper.updateEnterpriseEmailAccount(
                this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public final boolean setInComingServerPathPrefix(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setInComingServerPathPrefix() : deprecated");
        SettingsUtils.getValidStr(str);
        if (SettingsUtils.getAccountDetails(j, this.mContext, enforceEmailAccountPermission)
                == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setInComingServerPathPrefix() : No exist accId : ", j, "EmailAccountPolicy");
        }
        return false;
    }

    public final boolean setInComingServerPort(ContextInfo contextInfo, int i, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setInComingServerPort()");
        if (i < 0) {
            Log.i(
                    "EmailAccountPolicy",
                    "setInComingServerPort() : Error :: Invalid input parameter.");
            return false;
        }
        EnterpriseEmailAccount enterpriseEmailAccount =
                EmailProviderHelper.getEnterpriseEmailAccount(
                        j, this.mContext, enforceEmailAccountPermission);
        if (enterpriseEmailAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setInComingServerPort_new() : No exist accId : ", j, "EmailAccountPolicy");
            return false;
        }
        enterpriseEmailAccount.mInComingServerPort = i;
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                i, "setInComingServerPort returned  port : ", "EmailAccountPolicy");
        return EmailProviderHelper.updateEnterpriseEmailAccount(
                this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public final boolean setInComingServerSSL(ContextInfo contextInfo, boolean z, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setInComingServerSSL()");
        EnterpriseEmailAccount enterpriseEmailAccount =
                EmailProviderHelper.getEnterpriseEmailAccount(
                        j, this.mContext, enforceEmailAccountPermission);
        if (enterpriseEmailAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setInComingServerSSL_new() : No exist accId : ", j, "EmailAccountPolicy");
            return false;
        }
        enterpriseEmailAccount.mInComingUseSSL = z;
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "setInComingServerSSL returned  enableSSL : ", "EmailAccountPolicy", z);
        return EmailProviderHelper.updateEnterpriseEmailAccount(
                this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public final boolean setOutGoingProtocol(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setOutGoingProtocol : deprecated.");
        if (getValidOutgoingProtocol(str) == null) {
            Log.i("EmailAccountPolicy", "setOutGoingProtocol : Error :: Invalid input parameter.");
            return false;
        }
        if (SettingsUtils.getAccountDetails(j, this.mContext, enforceEmailAccountPermission)
                == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setOutGoingProtocol() : No exist accId : ", j, "EmailAccountPolicy");
        }
        return false;
    }

    public final boolean setOutGoingServerAcceptAllCertificates(
            ContextInfo contextInfo, boolean z, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "setOutGoingServerAcceptAllCertificates() : ", "EmailAccountPolicy", z);
        EnterpriseEmailAccount enterpriseEmailAccount =
                EmailProviderHelper.getEnterpriseEmailAccount(
                        j, this.mContext, enforceEmailAccountPermission);
        if (enterpriseEmailAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setOutGoingServerAcceptAllCertificates_new() : No exist accId : ",
                    j,
                    "EmailAccountPolicy");
            return false;
        }
        enterpriseEmailAccount.mOutgoingAcceptAllCertificates = z;
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "setOutGoingServerAcceptAllCertificates returned  port : ",
                "EmailAccountPolicy",
                z);
        return EmailProviderHelper.updateEnterpriseEmailAccount(
                this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public final long setOutGoingServerAddress(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr == null) {
            Log.i(
                    "EmailAccountPolicy",
                    "setOutGoingServerAddress() : Error :: Invalid input parameter.");
            return -1L;
        }
        EnterpriseEmailAccount enterpriseEmailAccount =
                EmailProviderHelper.getEnterpriseEmailAccount(
                        j, this.mContext, enforceEmailAccountPermission);
        if (enterpriseEmailAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setOutGoingServerAddress_new() : No exist accId : ", j, "EmailAccountPolicy");
            return -1L;
        }
        enterpriseEmailAccount.mOutgoingServerAddress = validStr;
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                "setOutGoingServerAddress() returned  serverAddress : ",
                validStr,
                "EmailAccountPolicy");
        if (EmailProviderHelper.updateEnterpriseEmailAccount(
                this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount)) {
            return enterpriseEmailAccount.mId;
        }
        return -1L;
    }

    public final long setOutGoingServerLogin(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        if (SettingsUtils.getValidStr(str) == null) {
            Log.i(
                    "EmailAccountPolicy",
                    "setOutGoingServerLogin() : Error :: Invalid input parameter.");
            return -1L;
        }
        if (SettingsUtils.getAccountDetails(j, this.mContext, enforceEmailAccountPermission)
                == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setOutGoingServerLogin() : No exist accId : ", j, "EmailAccountPolicy");
        }
        return -1L;
    }

    public final boolean setOutGoingServerPassword(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr == null) {
            Log.i(
                    "EmailAccountPolicy",
                    "setOutGoingServerPassword : Error :: Invalid input parameter.");
            return false;
        }
        EnterpriseEmailAccount enterpriseEmailAccount =
                EmailProviderHelper.getEnterpriseEmailAccount(
                        j, this.mContext, enforceEmailAccountPermission);
        if (enterpriseEmailAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setOutGoingServerPassword_new() : No exist accId : ", j, "EmailAccountPolicy");
            return false;
        }
        enterpriseEmailAccount.mOutgoingPassword = validStr;
        return EmailProviderHelper.updateEnterpriseEmailAccount(
                this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public final boolean setOutGoingServerPathPrefix(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setOutGoingServerPathPrefix() : deprecated. ");
        SettingsUtils.getValidStr(str);
        if (SettingsUtils.getAccountDetails(j, this.mContext, enforceEmailAccountPermission)
                == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setOutGoingServerPathPrefix() : No exist accId : ", j, "EmailAccountPolicy");
        }
        return false;
    }

    public final boolean setOutGoingServerPort(ContextInfo contextInfo, int i, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        if (i < 0) {
            Log.i(
                    "EmailAccountPolicy",
                    "setOutGoingServerPort : Error :: Invalid input parameter.");
            return false;
        }
        EnterpriseEmailAccount enterpriseEmailAccount =
                EmailProviderHelper.getEnterpriseEmailAccount(
                        j, this.mContext, enforceEmailAccountPermission);
        if (enterpriseEmailAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setOutGoingServerPort_new() : No exist accId : ", j, "EmailAccountPolicy");
            return false;
        }
        enterpriseEmailAccount.mOutgoingServerPort = i;
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                i, "setOutGoingServerPort returned  port : ", "EmailAccountPolicy");
        return EmailProviderHelper.updateEnterpriseEmailAccount(
                this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public final boolean setOutGoingServerSSL(ContextInfo contextInfo, boolean z, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "setOutGoingServerSSL() : ", "EmailAccountPolicy", z);
        EnterpriseEmailAccount enterpriseEmailAccount =
                EmailProviderHelper.getEnterpriseEmailAccount(
                        j, this.mContext, enforceEmailAccountPermission);
        if (enterpriseEmailAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setOutGoingServerSSL_new() : No exist accId : ", j, "EmailAccountPolicy");
            return false;
        }
        enterpriseEmailAccount.mOutgoingUseSSL = z;
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "setOutGoingServerSSL returned  port : ", "EmailAccountPolicy", z);
        return EmailProviderHelper.updateEnterpriseEmailAccount(
                this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public final long setSecurityInComingServerPassword(ContextInfo contextInfo, String str) {
        getEDM$11()
                .enforcePermissionByContext(
                        contextInfo, "com.samsung.android.knox.permission.KNOX_EMAIL");
        long createIDforAccount = SettingsUtils.createIDforAccount();
        try {
            if (str == null) {
                Log.i(
                        "EmailAccountPolicy",
                        "setSecurityInComingServerPassword() : password is null");
                return -1L;
            }
            try {
                SettingsUtils.setSecurityPassword("I#" + createIDforAccount, str);
                Slog.d("EmailAccountPolicy", "setSecurityInComingServerPassword() success");
                return createIDforAccount;
            } catch (Exception e) {
                Log.e("EmailAccountPolicy", "setSecurityInComingServerPassword() failed", e);
                return createIDforAccount;
            }
        } catch (Throwable unused) {
        }
    }

    public final long setSecurityOutGoingServerPassword(ContextInfo contextInfo, String str) {
        getEDM$11()
                .enforcePermissionByContext(
                        contextInfo, "com.samsung.android.knox.permission.KNOX_EMAIL");
        long createIDforAccount = SettingsUtils.createIDforAccount();
        try {
            if (str == null) {
                Log.i(
                        "EmailAccountPolicy",
                        "setSecurityOutGoingServerPassword() : password is null");
                return -1L;
            }
            try {
                SettingsUtils.setSecurityPassword("O#" + createIDforAccount, str);
                Slog.d("EmailAccountPolicy", "setSecurityOutGoingServerPassword() success");
                return createIDforAccount;
            } catch (Exception e) {
                Log.e("EmailAccountPolicy", "setSecurityOutGoingServerPassword() failed", e);
                return createIDforAccount;
            }
        } catch (Throwable unused) {
        }
    }

    public final boolean setSenderName(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr == null) {
            Log.i("EmailAccountPolicy", "setSenderName() : senderName is null");
            return false;
        }
        EnterpriseEmailAccount enterpriseEmailAccount =
                EmailProviderHelper.getEnterpriseEmailAccount(
                        j, this.mContext, enforceEmailAccountPermission);
        if (enterpriseEmailAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setEmailAddress_new() : No exist accId : ", j, "EmailAccountPolicy");
            return false;
        }
        enterpriseEmailAccount.mSenderName = validStr;
        return EmailProviderHelper.updateEnterpriseEmailAccount(
                this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public final boolean setSignature(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceEmailAccountPermission = enforceEmailAccountPermission(contextInfo);
        if (str == null) {
            Log.i("EmailAccountPolicy", "setSignature() : signature is null");
            return false;
        }
        EnterpriseEmailAccount enterpriseEmailAccount =
                EmailProviderHelper.getEnterpriseEmailAccount(
                        j, this.mContext, enforceEmailAccountPermission);
        if (enterpriseEmailAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setSignature_new() : No exist accId : ", j, "EmailAccountPolicy");
            return false;
        }
        enterpriseEmailAccount.mSignature = str;
        return EmailProviderHelper.updateEnterpriseEmailAccount(
                this.mContext, enforceEmailAccountPermission, enterpriseEmailAccount);
    }

    public final boolean setSilentVibrateOnEmailNotification(
            ContextInfo contextInfo, boolean z, long j) {
        enforceEmailAccountPermission(contextInfo);
        Log.i("EmailAccountPolicy", "setSilentVibrateOnEmailNotification() : deprecated.");
        return false;
    }

    public final boolean setSyncInterval(ContextInfo contextInfo, int i, long j) {
        if (SettingsUtils.getAccountDetails(
                        j, this.mContext, enforceEmailAccountPermission(contextInfo))
                == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setSyncInterval() : No exist accId : ", j, "EmailAccountPolicy");
            return false;
        }
        Log.i("EmailAccountPolicy", "setSyncInterval() : deprecated.");
        return false;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {}
}
