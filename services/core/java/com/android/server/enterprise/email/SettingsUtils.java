package com.android.server.enterprise.email;

import android.app.AppGlobals;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.UserHandle;
import android.sec.enterprise.email.EnterpriseEmailAccount;
import android.sec.enterprise.email.EnterpriseExchangeAccount;
import android.sec.enterprise.email.EnterpriseLDAPAccount;
import android.util.Log;
import android.util.secutil.Slog;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.accounts.Account;
import com.samsung.android.knox.accounts.HostAuth;
import com.samsung.android.knox.accounts.LDAPAccount;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class SettingsUtils {
    public static String adminPkg;
    public static Context emails;
    public static final Map mServerPassword;
    public static final Map mServerPasswordFocus;
    public static int preCallingUid;

    static {
        Uri.parse("content://com.android.email.provider/account");
        Uri.parse("content://com.android.email.provider/hostauth");
        Uri.parse("content://com.android.email.provider/policies");
        emails = null;
        adminPkg = null;
        preCallingUid = -1;
        mServerPassword = new HashMap();
        mServerPasswordFocus = new HashMap();
    }

    public static long createIDforAccount() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis >= 0) {
            currentTimeMillis = (currentTimeMillis - 1) * (-1);
        }
        Log.d("SettingsUtils", ">>>>  createIDforAccount   <<<<");
        Slog.d("SettingsUtils", ">>>>  createIDforAccount   <<<<" + currentTimeMillis);
        return currentTimeMillis;
    }

    /* JADX WARN: Removed duplicated region for block: B:60:0x02f2  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x02f7  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0303  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0308  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.enterprise.email.AccountMetaData getAccount(android.net.Uri r44, android.net.Uri r45, android.net.Uri r46, long r47, android.content.Context r49, com.samsung.android.knox.ContextInfo r50) {
        /*
            Method dump skipped, instructions count: 785
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.email.SettingsUtils.getAccount(android.net.Uri, android.net.Uri, android.net.Uri, long, android.content.Context, com.samsung.android.knox.ContextInfo):com.android.server.enterprise.email.AccountMetaData");
    }

    public static synchronized AccountMetaData getAccountDetails(long j, Context context, ContextInfo contextInfo) {
        AccountMetaData account;
        synchronized (SettingsUtils.class) {
            account = getAccount(Uri.parse("content://com.samsung.android.email.provider/hostauth"), Uri.parse("content://com.samsung.android.email.provider/account"), Uri.parse("content://com.samsung.android.email.provider/policies"), j, context, contextInfo);
        }
        return account;
    }

    public static synchronized AccountMetaData getAccountDetails$1(long j, Context context, ContextInfo contextInfo) {
        AccountMetaData account;
        synchronized (SettingsUtils.class) {
            int i = contextInfo.mContainerId;
            Log.d("SettingsUtils", "getAccountDetails() :  with P");
            account = getAccount(Uri.parse("content://com.samsung.android.email.provider/hostauth"), Uri.parse("content://com.samsung.android.email.provider/account"), Uri.parse("content://com.samsung.android.email.provider/policies"), j, context, contextInfo);
        }
        return account;
    }

    public static Account getAccountFromEnterpriseEmailAccount(EnterpriseEmailAccount enterpriseEmailAccount) {
        Slog.i("SettingsUtils", "getAccountFromEnterpriseEmailAccount() : " + enterpriseEmailAccount);
        Account account = new Account();
        account.id = (int) enterpriseEmailAccount.mId;
        account.displayName = enterpriseEmailAccount.mDisplayName;
        account.emailAddress = enterpriseEmailAccount.mEmailAddress;
        account.syncKey = "0";
        account.syncLookback = enterpriseEmailAccount.mSyncLookback;
        account.syncInterval = enterpriseEmailAccount.mSyncInterval;
        account.hostAuthKeyRecv = -1L;
        account.hostAuthKeySend = -1L;
        account.flags = -1;
        account.isDefault = enterpriseEmailAccount.mIsDefault;
        account.compatibilityUuid = null;
        account.senderName = enterpriseEmailAccount.mSenderName;
        account.ringtoneUri = null;
        account.protocolVersion = null;
        account.securitySyncKey = null;
        account.signature = enterpriseEmailAccount.mSignature;
        account.emailNotificationVibrateAlways = enterpriseEmailAccount.mEmailNotificationVibrateAlways;
        account.emailNotificationVibrateWhenSilent = false;
        HostAuth hostAuth = new HostAuth();
        account.hostAuthRecv = hostAuth;
        hostAuth.id = -1;
        hostAuth.protocol = enterpriseEmailAccount.mInComingProtocol;
        hostAuth.address = enterpriseEmailAccount.mInComingServerAddress;
        hostAuth.port = enterpriseEmailAccount.mInComingServerPort;
        hostAuth.flags = -1;
        hostAuth.login = enterpriseEmailAccount.mInComingUserName;
        hostAuth.password = enterpriseEmailAccount.mInComingPassword;
        hostAuth.domain = null;
        hostAuth.accountKey = -1L;
        hostAuth.useSSL = enterpriseEmailAccount.mInComingUseSSL;
        hostAuth.useTLS = enterpriseEmailAccount.mInComingUseTLS;
        hostAuth.acceptAllCertificates = enterpriseEmailAccount.mInComingAcceptAllCertificates;
        HostAuth hostAuth2 = new HostAuth();
        account.hostAuthSend = hostAuth2;
        hostAuth2.id = -1;
        hostAuth2.protocol = enterpriseEmailAccount.mOutgoingProtocol;
        hostAuth2.address = enterpriseEmailAccount.mOutgoingServerAddress;
        hostAuth2.port = enterpriseEmailAccount.mOutgoingServerPort;
        hostAuth2.flags = -1;
        hostAuth2.login = enterpriseEmailAccount.mOutgoingUserName;
        hostAuth2.password = enterpriseEmailAccount.mOutgoingPassword;
        hostAuth2.domain = null;
        hostAuth2.accountKey = -1L;
        hostAuth2.useSSL = enterpriseEmailAccount.mOutgoingUseSSL;
        hostAuth2.useTLS = enterpriseEmailAccount.mOutgoingUseTLS;
        hostAuth2.acceptAllCertificates = enterpriseEmailAccount.mOutgoingAcceptAllCertificates;
        return account;
    }

    public static Account getAccountFromEnterpriseExchangeAccount(EnterpriseExchangeAccount enterpriseExchangeAccount) {
        Slog.i("SettingsUtils", "getAccountFromEnterpriseExchangeAccount() : " + enterpriseExchangeAccount);
        Account account = new Account();
        account.id = (int) enterpriseExchangeAccount.mId;
        account.displayName = enterpriseExchangeAccount.mDisplayName;
        account.emailAddress = enterpriseExchangeAccount.mEmailAddress;
        account.syncKey = "0";
        account.syncLookback = enterpriseExchangeAccount.mSyncLookback;
        account.syncInterval = enterpriseExchangeAccount.mSyncInterval;
        account.hostAuthKeyRecv = -1L;
        account.hostAuthKeySend = -1L;
        account.flags = -1;
        account.isDefault = enterpriseExchangeAccount.mIsDefault;
        account.compatibilityUuid = null;
        account.senderName = enterpriseExchangeAccount.mSenderName;
        account.ringtoneUri = null;
        account.protocolVersion = enterpriseExchangeAccount.mProtocolVersion;
        account.securitySyncKey = null;
        account.signature = enterpriseExchangeAccount.mSignature;
        account.peakDays = enterpriseExchangeAccount.mPeakDays;
        account.peakStartMinute = enterpriseExchangeAccount.mPeakStartMinute;
        account.peakEndMinute = enterpriseExchangeAccount.mPeakEndMinute;
        account.peakSyncSchedule = enterpriseExchangeAccount.mPeakSyncSchedule;
        account.offPeakSyncSchedule = enterpriseExchangeAccount.mOffPeakSyncSchedule;
        account.roamingSyncSchedule = enterpriseExchangeAccount.mRoamingSyncSchedule;
        account.syncCalendarAge = enterpriseExchangeAccount.mSyncCalendarAge;
        account.emailBodyTruncationSize = enterpriseExchangeAccount.mEmailBodyTruncationSize;
        account.emailRoamingBodyTruncationSize = enterpriseExchangeAccount.mEmailRoamingBodyTruncationSize;
        account.syncContacts = enterpriseExchangeAccount.mSyncContacts;
        account.syncCalendar = enterpriseExchangeAccount.mSyncCalendar;
        account.syncTasks = enterpriseExchangeAccount.mSyncTasks;
        account.syncNotes = enterpriseExchangeAccount.mSyncNotes;
        account.emailNotificationVibrateAlways = enterpriseExchangeAccount.mEmailNotificationVibrateAlways;
        account.emailNotificationVibrateWhenSilent = false;
        HostAuth hostAuth = new HostAuth();
        account.hostAuthRecv = hostAuth;
        hostAuth.id = -1;
        hostAuth.protocol = "eas";
        hostAuth.address = enterpriseExchangeAccount.mServerAddress;
        hostAuth.port = 0;
        hostAuth.flags = -1;
        hostAuth.login = enterpriseExchangeAccount.mEasUser;
        hostAuth.password = enterpriseExchangeAccount.mPassword;
        hostAuth.domain = null;
        hostAuth.accountKey = -1L;
        hostAuth.useSSL = enterpriseExchangeAccount.mUseSSL;
        hostAuth.useTLS = enterpriseExchangeAccount.mUseTLS;
        hostAuth.acceptAllCertificates = enterpriseExchangeAccount.mAcceptAllCertificates;
        HostAuth hostAuth2 = new HostAuth();
        account.hostAuthSend = hostAuth2;
        hostAuth2.id = -1;
        hostAuth2.protocol = "eas";
        hostAuth2.address = enterpriseExchangeAccount.mServerAddress;
        hostAuth2.port = 0;
        hostAuth2.flags = -1;
        hostAuth2.login = enterpriseExchangeAccount.mEasUser;
        hostAuth2.password = enterpriseExchangeAccount.mPassword;
        hostAuth2.domain = null;
        hostAuth2.accountKey = -1L;
        hostAuth2.useSSL = enterpriseExchangeAccount.mUseSSL;
        hostAuth2.useTLS = enterpriseExchangeAccount.mUseTLS;
        hostAuth2.acceptAllCertificates = enterpriseExchangeAccount.mAcceptAllCertificates;
        return account;
    }

    public static synchronized long getAccountId(ContextInfo contextInfo, String str, String str2, String str3, String str4, boolean z, Context context) {
        long accountIdInternal;
        synchronized (SettingsUtils.class) {
            int i = contextInfo.mContainerId;
            Uri parse = Uri.parse("content://com.samsung.android.email.provider/hostauth");
            Uri parse2 = Uri.parse("content://com.samsung.android.email.provider/account");
            Log.i("SettingsUtils", "getAccountId : USER space    UserHandle.myUserId() " + UserHandle.myUserId());
            accountIdInternal = getAccountIdInternal(parse, parse2, str, str2, str3, str4, z, context, contextInfo);
        }
        return accountIdInternal;
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0175, code lost:
    
        if (r1 != null) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0177, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x017e, code lost:
    
        if (r1 == null) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00fd, code lost:
    
        if (r6 != null) goto L34;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:73:0x018f  */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v7, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static long getAccountIdInternal(android.net.Uri r17, android.net.Uri r18, java.lang.String r19, java.lang.String r20, java.lang.String r21, java.lang.String r22, boolean r23, android.content.Context r24, com.samsung.android.knox.ContextInfo r25) {
        /*
            Method dump skipped, instructions count: 416
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.email.SettingsUtils.getAccountIdInternal(android.net.Uri, android.net.Uri, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, android.content.Context, com.samsung.android.knox.ContextInfo):long");
    }

    public static String[] getEasDomainAndUserFromLogin(String str) {
        String[] strArr = {null, null};
        String validStr = getValidStr(str);
        if (validStr != null) {
            if (validStr.contains("\\")) {
                String[] split = validStr.split(Matcher.quoteReplacement("\\"));
                if (split != null && split.length > 0) {
                    if (2 == split.length) {
                        strArr[0] = split[0].trim();
                        strArr[1] = split[1].trim();
                    } else if (1 == split.length) {
                        strArr[1] = split[0].trim();
                    }
                }
            } else {
                strArr[1] = validStr;
            }
        }
        return strArr;
    }

    public static LDAPAccount getLDAPAccountFromEnterpriseLDAPAccount(EnterpriseLDAPAccount enterpriseLDAPAccount) {
        if (enterpriseLDAPAccount == null) {
            return null;
        }
        LDAPAccount lDAPAccount = new LDAPAccount();
        lDAPAccount.id = enterpriseLDAPAccount.mId;
        lDAPAccount.userName = enterpriseLDAPAccount.mUserName;
        lDAPAccount.password = enterpriseLDAPAccount.mPassword;
        lDAPAccount.port = enterpriseLDAPAccount.mPort;
        lDAPAccount.host = enterpriseLDAPAccount.mHost;
        lDAPAccount.isSSL = enterpriseLDAPAccount.mUseSSL;
        lDAPAccount.isAnonymous = enterpriseLDAPAccount.mIsAnonymous;
        lDAPAccount.baseDN = enterpriseLDAPAccount.mBaseDN;
        lDAPAccount.trustAll = enterpriseLDAPAccount.mTrustAll;
        return lDAPAccount;
    }

    public static String getPackageNameForUid(int i) {
        if (i != preCallingUid) {
            return "com.samsung.android.email.provider";
        }
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(new StringBuilder("getPackageNameForUid :   "), adminPkg, "SettingsUtils");
        return adminPkg;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x00e1, code lost:
    
        if (r1 != null) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00e3, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00f2, code lost:
    
        if (r1 == null) goto L43;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00fc A[Catch: all -> 0x0021, TryCatch #1 {all -> 0x0021, blocks: (B:4:0x000f, B:7:0x0027, B:16:0x00de, B:18:0x00e3, B:41:0x00f7, B:43:0x00fc, B:44:0x00ff, B:34:0x00ef), top: B:3:0x000f }] */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v2, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r9v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized java.lang.String getSMIMEAlias(com.samsung.android.knox.ContextInfo r17, long r18, android.content.Context r20, boolean r21) {
        /*
            Method dump skipped, instructions count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.email.SettingsUtils.getSMIMEAlias(com.samsung.android.knox.ContextInfo, long, android.content.Context, boolean):java.lang.String");
    }

    public static String getSecurityPassword(String str) {
        try {
            Map map = mServerPassword;
            String str2 = (String) ((HashMap) map).get(str);
            ((HashMap) map).remove(str);
            return str2;
        } catch (Exception unused) {
            Log.w("SettingsUtils", "getSecurityPassword() failed");
            return null;
        }
    }

    public static String getValidStr(String str) {
        if (str == null) {
            return null;
        }
        try {
            String trim = str.trim();
            if (trim.length() > 0) {
                return trim;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isPackageInstalled(int i, String str) {
        IPackageManager packageManager = AppGlobals.getPackageManager();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (packageManager != null) {
            try {
                if (packageManager.getApplicationInfo(str, 0L, i) == null) {
                    return false;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (Exception e) {
                Log.e("SettingsUtils", "Exception in isPackageInstalled()", e);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return false;
    }

    public static boolean isPersona(int i) {
        if (i == 0) {
            return false;
        }
        ((PersonaManagerAdapter) ((IPersonaManagerAdapter) AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class))).getClass();
        return SemPersonaManager.isKnoxId(i);
    }

    public static boolean isValidEmailAddress(String str) {
        String validStr = getValidStr(str);
        if (validStr == null) {
            return false;
        }
        try {
            int length = validStr.length();
            int indexOf = validStr.indexOf(64);
            int lastIndexOf = validStr.lastIndexOf(64);
            int i = lastIndexOf + 1;
            int indexOf2 = validStr.indexOf(46, i);
            int lastIndexOf2 = validStr.lastIndexOf(46);
            return indexOf > 0 && indexOf == lastIndexOf && i < indexOf2 && indexOf2 <= lastIndexOf2 && lastIndexOf2 < length - 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x00f5, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00f2, code lost:
    
        if (0 == 0) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean loadHostAuth(android.net.Uri r17, long r18, com.android.server.enterprise.email.AccountMetaData r20, boolean r21, android.content.Context r22) {
        /*
            r0 = r20
            java.lang.String r1 = "loadHostAuth row count : "
            java.lang.String r2 = "_id = "
            java.lang.String r7 = "flags"
            java.lang.String r8 = "login"
            java.lang.String r3 = "_id"
            java.lang.String r4 = "protocol"
            java.lang.String r5 = "address"
            java.lang.String r6 = "port"
            java.lang.String r9 = "password"
            java.lang.String r10 = "domain"
            java.lang.String[] r13 = new java.lang.String[]{r3, r4, r5, r6, r7, r8, r9, r10}
            r3 = 0
            r4 = 0
            android.content.ContentResolver r11 = r22.getContentResolver()     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r5.<init>(r2)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r6 = r18
            r5.append(r6)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            java.lang.String r14 = r5.toString()     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r15 = 0
            r16 = 0
            r12 = r17
            android.database.Cursor r4 = r11.query(r12, r13, r14, r15, r16)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            java.lang.String r2 = "SettingsUtils"
            if (r4 == 0) goto Le3
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r5.<init>(r1)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            int r1 = r4.getCount()     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r5.append(r1)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            java.lang.String r1 = r5.toString()     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            android.util.Log.i(r2, r1)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            int r1 = r4.getCount()     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            if (r1 <= 0) goto Le9
            r4.moveToFirst()     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            java.lang.String r1 = "domain"
            java.lang.String r2 = "password"
            java.lang.String r5 = "login"
            java.lang.String r6 = "flags"
            java.lang.String r7 = "port"
            java.lang.String r8 = "address"
            java.lang.String r9 = "protocol"
            if (r21 == 0) goto Lb0
            int r9 = r4.getColumnIndex(r9)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            java.lang.String r9 = r4.getString(r9)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r0.mInComingProtocol = r9     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            int r8 = r4.getColumnIndex(r8)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r4.getString(r8)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            int r7 = r4.getColumnIndex(r7)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r4.getInt(r7)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            int r6 = r4.getColumnIndex(r6)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r4.getInt(r6)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            int r5 = r4.getColumnIndex(r5)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            java.lang.String r5 = r4.getString(r5)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r0.mInComingServerLogin = r5     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            int r0 = r4.getColumnIndex(r2)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r4.getString(r0)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            int r0 = r4.getColumnIndex(r1)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r4.getString(r0)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            goto Le1
        Lac:
            r0 = move-exception
            goto Lf6
        Lae:
            r0 = move-exception
            goto Lef
        Lb0:
            int r0 = r4.getColumnIndex(r9)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r4.getString(r0)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            int r0 = r4.getColumnIndex(r8)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r4.getString(r0)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            int r0 = r4.getColumnIndex(r7)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r4.getInt(r0)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            int r0 = r4.getColumnIndex(r6)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r4.getInt(r0)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            int r0 = r4.getColumnIndex(r5)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r4.getString(r0)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            int r0 = r4.getColumnIndex(r2)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r4.getString(r0)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            int r0 = r4.getColumnIndex(r1)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
            r4.getString(r0)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
        Le1:
            r3 = 1
            goto Le9
        Le3:
            java.lang.String r0 = "loadHostAuth row count : Email cursor is invalid"
            android.util.Log.i(r2, r0)     // Catch: java.lang.Throwable -> Lac java.lang.Exception -> Lae
        Le9:
            if (r4 == 0) goto Lf5
        Leb:
            r4.close()
            goto Lf5
        Lef:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> Lac
            if (r4 == 0) goto Lf5
            goto Leb
        Lf5:
            return r3
        Lf6:
            if (r4 == 0) goto Lfb
            r4.close()
        Lfb:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.email.SettingsUtils.loadHostAuth(android.net.Uri, long, com.android.server.enterprise.email.AccountMetaData, boolean, android.content.Context):boolean");
    }

    public static synchronized void sendAccountsChangedBroadcast(Context context, int i) {
        synchronized (SettingsUtils.class) {
            try {
                int userId = UserHandle.getUserId(i);
                try {
                    Intent intent = new Intent("android.accounts.LOGIN_ACCOUNTS_CHANGED");
                    intent.setFlags(268435456);
                    context.sendBroadcastAsUser(intent, new UserHandle(userId));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (userId == 0) {
                        emails = context;
                    } else if (isPersona(userId)) {
                        emails = context.createPackageContextAsUser("com.samsung.android.email.provider", 0, new UserHandle(userId));
                        Log.i("SettingsUtils", "sendAccountsChangedBroadcast : USER space   com.samsung.android.email.provider");
                    } else {
                        emails = context.createPackageContextAsUser(getPackageNameForUid(i), 0, new UserHandle(userId));
                        Log.i("SettingsUtils", "sendAccountsChangedBroadcast : USER space");
                    }
                    emails.getContentResolver().notifyChange(Uri.parse("content://com.samsung.android.email.provider/hostauth"), (ContentObserver) null, true);
                    emails.getContentResolver().notifyChange(Uri.parse("content://com.samsung.android.email.provider/account"), (ContentObserver) null, true);
                    Log.i("SettingsUtils", "sendAccountsChangedBroadcast()");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void setSecurityPassword(String str, String str2) {
        if (str == null || str2 == null) {
            Log.w("SettingsUtils", "setSecurityPassword() failed : invalid parameter");
            return;
        }
        try {
            ((HashMap) mServerPassword).put(str, str2);
            Log.w("SettingsUtils", "setSecurityPassword() success");
        } catch (Exception unused) {
            Log.w("SettingsUtils", "setSecurityPassword() failed");
        }
    }
}
