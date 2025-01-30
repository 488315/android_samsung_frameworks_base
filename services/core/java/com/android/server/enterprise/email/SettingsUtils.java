package com.android.server.enterprise.email;

import android.app.AppGlobals;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.ConditionVariable;
import android.os.UserHandle;
import android.sec.enterprise.email.EnterpriseEmailAccount;
import android.sec.enterprise.email.EnterpriseExchangeAccount;
import android.sec.enterprise.email.EnterpriseLDAPAccount;
import android.util.Log;
import android.util.secutil.Slog;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.security.DeviceAccountPolicy;
import com.android.server.enterprise.utils.Utils;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.accounts.Account;
import com.samsung.android.knox.accounts.HostAuth;
import com.samsung.android.knox.accounts.LDAPAccount;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/* loaded from: classes2.dex */
public abstract class SettingsUtils {
    public static final Uri ACCOUNT_CONTENT_URI = Uri.parse("content://com.android.email.provider/account");
    public static final Uri HOST_AUTH_CONTENT_URI = Uri.parse("content://com.android.email.provider/hostauth");
    public static final Uri POLICIES_CONTENT_URI = Uri.parse("content://com.android.email.provider/policies");
    public static Context emails = null;
    public static String adminPkg = null;
    public static int preCallingUid = -1;
    public static Map mServerPassword = new HashMap();
    public static Map mServerPasswordFocus = new HashMap();

    public static String getAccountType(boolean z, int i) {
        return z ? "com.samsung.android.exchange" : "com.samsung.android.email";
    }

    public static String getEasPackageName(int i) {
        return "com.samsung.android.email.provider";
    }

    public static String getEmailPackageName(int i) {
        return "com.samsung.android.email.provider";
    }

    public static synchronized void sendAccountsChangedBroadcast(int i, Context context, int i2) {
        synchronized (SettingsUtils.class) {
            int userId = UserHandle.getUserId(i2);
            try {
                Intent intent = new Intent("android.accounts.LOGIN_ACCOUNTS_CHANGED");
                intent.setFlags(268435456);
                context.sendBroadcastAsUser(intent, new UserHandle(userId));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (userId != 0) {
                    if (isPersona(userId, context)) {
                        emails = context.createPackageContextAsUser(getEmailPackageName(i), 0, new UserHandle(userId));
                        Log.i("SettingsUtils", "sendAccountsChangedBroadcast : USER space   " + getEmailPackageName(i));
                    } else {
                        emails = context.createPackageContextAsUser(getPackageNameForUid(i2), 0, new UserHandle(userId));
                        Log.i("SettingsUtils", "sendAccountsChangedBroadcast : USER space");
                    }
                } else {
                    emails = context;
                }
                emails.getContentResolver().notifyChange(getHostAuthContentUri(i), (ContentObserver) null, true);
                emails.getContentResolver().notifyChange(getAccountContentUri(i), (ContentObserver) null, true);
                Log.i("SettingsUtils", "sendAccountsChangedBroadcast()");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
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

    public static String getPackageNameForUid(int i) {
        if (i == preCallingUid) {
            Log.i("SettingsUtils", "getPackageNameForUid :   " + adminPkg);
            return adminPkg;
        }
        return getEmailPackageName(i);
    }

    public static void setPackageNameForUid(int i, String str) {
        preCallingUid = i;
        adminPkg = str;
        Log.i("SettingsUtils", "setPackageNameForUid :   " + adminPkg);
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

    public static synchronized long getAccountId(ContextInfo contextInfo, String str, String str2, String str3, String str4, boolean z, Context context) {
        long accountIdInternal;
        synchronized (SettingsUtils.class) {
            int i = contextInfo.mContainerId;
            Uri hostAuthContentUri = getHostAuthContentUri(i);
            Uri accountContentUri = getAccountContentUri(i);
            Log.i("SettingsUtils", "getAccountId : USER space    UserHandle.myUserId() " + UserHandle.myUserId());
            accountIdInternal = getAccountIdInternal(hostAuthContentUri, accountContentUri, str, str2, str3, str4, z, context, contextInfo);
        }
        return accountIdInternal;
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x019c, code lost:
    
        if (r1 == null) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0116, code lost:
    
        if (r3 != null) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01ad  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static long getAccountIdInternal(Uri uri, Uri uri2, String str, String str2, String str3, String str4, boolean z, Context context, ContextInfo contextInfo) {
        Cursor cursor;
        long j;
        String validStr = getValidStr(str);
        String validStr2 = getValidStr(str2);
        String validStr3 = getValidStr(str3);
        String validStr4 = getValidStr(str4);
        long j2 = -1;
        if (validStr2 == null || validStr3 == null || validStr4 == null) {
            Log.i("SettingsUtils", "getAccountId : Error :: Invalid input parameters.");
            return -1L;
        }
        if (z && validStr != null) {
            validStr2 = validStr + "\\" + validStr2;
        }
        String[] strArr = {KnoxCustomManagerService.f1773ID};
        String[] strArr2 = {validStr4, validStr3, validStr2};
        int i = contextInfo.mCallerUid;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (callingOrCurrentUserId != 0) {
                if (isPersona(callingOrCurrentUserId, context)) {
                    emails = context.createPackageContextAsUser(getEmailPackageName(contextInfo.mContainerId), 0, new UserHandle(callingOrCurrentUserId));
                    Log.i("SettingsUtils", "getAccountIdInternal : USER space   " + getEmailPackageName(contextInfo.mContainerId));
                } else {
                    emails = context.createPackageContextAsUser(getPackageNameForUid(contextInfo.mCallerUid), 0, new UserHandle(callingOrCurrentUserId));
                    Log.i("SettingsUtils", "getAccountIdInternal : USER space   " + getPackageNameForUid(contextInfo.mCallerUid));
                }
            } else {
                emails = context;
            }
            Cursor cursor2 = null;
            try {
                cursor = emails.getContentResolver().query(uri, strArr, "protocol=? AND address=? AND login=? ", strArr2, null);
                try {
                    try {
                        Log.i("SettingsUtils", "getAccountId for HOST_AUTH row count : " + cursor.getCount());
                        if (cursor.moveToNext()) {
                            j = cursor.getInt(cursor.getColumnIndex(KnoxCustomManagerService.f1773ID));
                            try {
                                Slog.d("SettingsUtils", " getAccountIdInternal : hostAuthId " + j);
                            } catch (Exception e) {
                                e = e;
                                e.printStackTrace();
                            }
                        } else {
                            j = -1;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        j = -1;
                        e.printStackTrace();
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor2 = cursor;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                cursor = null;
            } catch (Throwable th2) {
                th = th2;
                if (cursor2 != null) {
                }
                throw th;
            }
            cursor.close();
            if (-1 != j) {
                try {
                    try {
                        cursor2 = emails.getContentResolver().query(uri2, new String[]{KnoxCustomManagerService.f1773ID}, "hostAuthKeyRecv=? OR hostAuthKeySend=? ", new String[]{Long.toString(j), Long.toString(j)}, null);
                        if (cursor2 != null) {
                            Log.i("SettingsUtils", "getAccountId for ACCOUNT row count : " + cursor2.getCount());
                        } else {
                            Log.i("SettingsUtils", "getAccountId for ACCOUNT row count : Email cursor is invalid");
                        }
                        if (cursor2 != null && cursor2.moveToNext()) {
                            long j3 = cursor2.getInt(cursor2.getColumnIndex(KnoxCustomManagerService.f1773ID));
                            try {
                                Slog.d("SettingsUtils", " getAccountIdInternal : ACCOUNT " + j3);
                                j2 = j3;
                            } catch (Exception e4) {
                                e = e4;
                                j2 = j3;
                                e.printStackTrace();
                            }
                        }
                    } finally {
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                    }
                } catch (Exception e5) {
                    e = e5;
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return j2;
        } catch (Exception e6) {
            e6.printStackTrace();
            return -1L;
        }
    }

    public static synchronized AccountMetaData getAccountDetails(ContextInfo contextInfo, long j, Context context) {
        AccountMetaData account;
        synchronized (SettingsUtils.class) {
            int i = contextInfo.mContainerId;
            account = getAccount(getHostAuthContentUri(i), getAccountContentUri(i), getPoliciesContentUri(i), j, context, false, contextInfo);
        }
        return account;
    }

    public static synchronized AccountMetaData getAccountDetails(ContextInfo contextInfo, long j, Context context, boolean z) {
        AccountMetaData account;
        synchronized (SettingsUtils.class) {
            int i = contextInfo.mContainerId;
            Log.d("SettingsUtils", "getAccountDetails() :  with P");
            account = getAccount(getHostAuthContentUri(i), getAccountContentUri(i), getPoliciesContentUri(i), j, context, z, contextInfo);
        }
        return account;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x038e  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0393  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x039d  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x03a2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static AccountMetaData getAccount(Uri uri, Uri uri2, Uri uri3, long j, Context context, boolean z, ContextInfo contextInfo) {
        Cursor cursor;
        long j2;
        Cursor cursor2;
        AccountMetaData accountMetaData;
        Cursor cursor3 = null;
        if (j <= 0) {
            Log.d("SettingsUtils", "getEASAccounts fail : invalid account Id" + j);
            return null;
        }
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        int i = contextInfo.mContainerId;
        Log.d("SettingsUtils", "getEASAccounts() :    " + callingOrCurrentUserId);
        String[] strArr = {KnoxCustomManagerService.f1773ID, "displayName", "emailAddress", "syncLookback", "syncInterval", "peakDays", "peakStartMinute", "peakEndMinute", "peakSchedule", "offPeakSchedule", "roamingSchedule", "hostAuthKeyRecv", "hostAuthKeySend", "flags", "isDefault", "compatibilityUuid", "senderName", "ringtoneUri", "protocolVersion", "signature", "calendarSyncLookback", "emailsize", "roamingemailsize"};
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (callingOrCurrentUserId != 0) {
                if (isPersona(callingOrCurrentUserId, context)) {
                    emails = context.createPackageContextAsUser(getEmailPackageName(i), 0, new UserHandle(callingOrCurrentUserId));
                    Log.i("SettingsUtils", "getAccountDetails for Persona : USER space   " + getEmailPackageName(i));
                } else {
                    emails = context.createPackageContextAsUser(getPackageNameForUid(contextInfo.mCallerUid), 0, new UserHandle(callingOrCurrentUserId));
                    Log.i("SettingsUtils", "getAccountDetails : USER space   " + getPackageNameForUid(contextInfo.mCallerUid));
                }
            } else {
                emails = context;
            }
            try {
                Cursor query = emails.getContentResolver().query(uri2, strArr, "_id = " + j, null, null);
                if (query != null) {
                    try {
                        try {
                            Log.i("SettingsUtils", "getAccount row count : " + query.getCount());
                        } catch (Throwable th) {
                            th = th;
                            cursor2 = cursor3;
                            cursor = query;
                            if (cursor != null) {
                                cursor.close();
                            }
                            if (cursor2 != null) {
                                cursor2.close();
                            }
                            throw th;
                        }
                    } catch (Exception e) {
                        e = e;
                        accountMetaData = null;
                        cursor2 = null;
                        j2 = clearCallingIdentity;
                        cursor = query;
                        try {
                            e.printStackTrace();
                            if (cursor != null) {
                            }
                            if (cursor2 != null) {
                            }
                            Binder.restoreCallingIdentity(j2);
                            return accountMetaData;
                        } catch (Throwable th2) {
                            th = th2;
                            if (cursor != null) {
                            }
                            if (cursor2 != null) {
                            }
                            throw th;
                        }
                    }
                } else {
                    try {
                        try {
                            Log.i("SettingsUtils", "getAccount row count : Email cursor is invalid");
                        } catch (Throwable th3) {
                            th = th3;
                            cursor = query;
                            cursor2 = null;
                            if (cursor != null) {
                            }
                            if (cursor2 != null) {
                            }
                            throw th;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        j2 = clearCallingIdentity;
                        cursor = query;
                        accountMetaData = null;
                        cursor2 = null;
                        e.printStackTrace();
                        if (cursor != null) {
                        }
                        if (cursor2 != null) {
                        }
                        Binder.restoreCallingIdentity(j2);
                        return accountMetaData;
                    }
                }
                if (query == null || query.getCount() <= 0) {
                    j2 = clearCallingIdentity;
                    cursor3 = null;
                    accountMetaData = null;
                } else {
                    query.moveToFirst();
                    accountMetaData = new AccountMetaData();
                    try {
                        accountMetaData.mId = query.getInt(query.getColumnIndex(KnoxCustomManagerService.f1773ID));
                        accountMetaData.mDisplayName = query.getString(query.getColumnIndex("displayName"));
                        accountMetaData.mEmailAddress = query.getString(query.getColumnIndex("emailAddress"));
                        accountMetaData.mSyncLookback = query.getInt(query.getColumnIndex("syncLookback"));
                        accountMetaData.mSyncInterval = query.getInt(query.getColumnIndex("syncInterval"));
                        accountMetaData.mPeakDays = query.getInt(query.getColumnIndex("peakDays"));
                        accountMetaData.mPeakStartMinute = query.getInt(query.getColumnIndex("peakStartMinute"));
                        accountMetaData.mPeakEndMinute = query.getInt(query.getColumnIndex("peakEndMinute"));
                        accountMetaData.mPeakSyncSchedule = query.getInt(query.getColumnIndex("peakSchedule"));
                        accountMetaData.mOffPeakSyncSchedule = query.getInt(query.getColumnIndex("offPeakSchedule"));
                        accountMetaData.mRoamingSyncSchedule = query.getInt(query.getColumnIndex("roamingSchedule"));
                        accountMetaData.mHostAuthKeyRecv = query.getLong(query.getColumnIndex("hostAuthKeyRecv"));
                        accountMetaData.mHostAuthKeySend = query.getLong(query.getColumnIndex("hostAuthKeySend"));
                        int i2 = query.getInt(query.getColumnIndex("flags"));
                        accountMetaData.mFlags = i2;
                        accountMetaData.mEmailNotificationVibrateAlways = 2 == (i2 & 2);
                        accountMetaData.mEmailNotificationVibrateWhenSilent = 64 == (i2 & 64);
                        accountMetaData.mIsDefault = query.getInt(query.getColumnIndex("isDefault")) == 1;
                        accountMetaData.mCompatibilityUuid = query.getString(query.getColumnIndex("compatibilityUuid"));
                        accountMetaData.mSenderName = query.getString(query.getColumnIndex("senderName"));
                        accountMetaData.mRingtoneUri = query.getString(query.getColumnIndex("ringtoneUri"));
                        accountMetaData.mProtocolVersion = query.getString(query.getColumnIndex("protocolVersion"));
                        accountMetaData.mSignature = query.getString(query.getColumnIndex("signature"));
                        accountMetaData.mSyncCalendarAge = query.getInt(query.getColumnIndex("calendarSyncLookback"));
                        accountMetaData.mEmailBodyTruncationSize = query.getInt(query.getColumnIndex("emailsize"));
                        accountMetaData.mEmailRoamingBodyTruncationSize = query.getInt(query.getColumnIndex("roamingemailsize"));
                        boolean loadHostAuth = loadHostAuth(uri, accountMetaData.mHostAuthKeyRecv, accountMetaData, true, emails);
                        j2 = clearCallingIdentity;
                        try {
                            boolean loadHostAuth2 = loadHostAuth(uri, accountMetaData.mHostAuthKeySend, accountMetaData, false, emails);
                            if (loadHostAuth && loadHostAuth2) {
                                if (z) {
                                    String[] password = getPassword(i, j, context, callingOrCurrentUserId);
                                    accountMetaData.mInComingServerPassword = password[0];
                                    accountMetaData.mOutGoingServerPassword = password[1];
                                }
                                accountMetaData.mIsEAS = false;
                                if (accountMetaData.mInComingProtocol.equalsIgnoreCase("eas")) {
                                    String[] easDomainAndUserFromLogin = getEasDomainAndUserFromLogin(accountMetaData.mInComingServerLogin);
                                    accountMetaData.mIsEAS = true;
                                    accountMetaData.mEasDomain = easDomainAndUserFromLogin[0];
                                    String str = easDomainAndUserFromLogin[1];
                                    accountMetaData.mInComingServerLogin = str;
                                    accountMetaData.mOutGoingServerLogin = str;
                                }
                                accountMetaData.mAllowHTMLEmail = true;
                                String[] strArr2 = {KnoxCustomManagerService.f1773ID, "name", "type", "value", "account_id"};
                                cursor3 = emails.getContentResolver().query(uri3, strArr2, "account_id = " + j, null, null);
                                if (cursor3 != null) {
                                    try {
                                        if (cursor3.getCount() > 0 && cursor3.moveToFirst()) {
                                            do {
                                                String string = cursor3.getString(cursor3.getColumnIndex("value"));
                                                String string2 = cursor3.getString(cursor3.getColumnIndex("name"));
                                                if (string2 != null && string != null && string2.equals("AllowHTMLEmail")) {
                                                    accountMetaData.mAllowHTMLEmail = "true".equalsIgnoreCase(string);
                                                }
                                            } while (cursor3.moveToNext());
                                        }
                                    } catch (Exception e3) {
                                        e = e3;
                                        cursor2 = cursor3;
                                        cursor = query;
                                        e.printStackTrace();
                                        if (cursor != null) {
                                        }
                                        if (cursor2 != null) {
                                        }
                                        Binder.restoreCallingIdentity(j2);
                                        return accountMetaData;
                                    }
                                }
                            }
                        } catch (Exception e4) {
                            e = e4;
                            cursor = query;
                            cursor2 = null;
                            e.printStackTrace();
                            if (cursor != null) {
                                cursor.close();
                            }
                            if (cursor2 != null) {
                                cursor2.close();
                            }
                            Binder.restoreCallingIdentity(j2);
                            return accountMetaData;
                        }
                        try {
                            Log.i("SettingsUtils", "getAccountIds : null ");
                            query.close();
                            return null;
                        } catch (Exception e5) {
                            e = e5;
                            cursor = query;
                            accountMetaData = null;
                            cursor2 = null;
                            e.printStackTrace();
                            if (cursor != null) {
                            }
                            if (cursor2 != null) {
                            }
                            Binder.restoreCallingIdentity(j2);
                            return accountMetaData;
                        }
                    } catch (Exception e6) {
                        e = e6;
                        j2 = clearCallingIdentity;
                    }
                }
                if (query != null) {
                    query.close();
                }
                if (cursor3 != null) {
                    cursor3.close();
                }
            } catch (Exception e7) {
                e = e7;
                j2 = clearCallingIdentity;
                cursor = null;
            } catch (Throwable th4) {
                th = th4;
                cursor = null;
            }
            Binder.restoreCallingIdentity(j2);
            return accountMetaData;
        } catch (Exception e8) {
            e8.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x0150, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x014d, code lost:
    
        if (0 == 0) goto L47;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean loadHostAuth(Uri uri, long j, AccountMetaData accountMetaData, boolean z, Context context) {
        String[] strArr = {KnoxCustomManagerService.f1773ID, "protocol", "address", "port", "flags", "login", "password", "domain"};
        boolean z2 = false;
        Cursor cursor = null;
        try {
            try {
                cursor = context.getContentResolver().query(uri, strArr, "_id = " + j, null, null);
                if (cursor != null) {
                    Log.i("SettingsUtils", "loadHostAuth row count : " + cursor.getCount());
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        if (z) {
                            accountMetaData.mInComingProtocol = cursor.getString(cursor.getColumnIndex("protocol"));
                            accountMetaData.mInComingServerAddress = cursor.getString(cursor.getColumnIndex("address"));
                            accountMetaData.mInComingServerPort = cursor.getInt(cursor.getColumnIndex("port"));
                            int i = cursor.getInt(cursor.getColumnIndex("flags"));
                            accountMetaData.mInComingServerFlags = i;
                            accountMetaData.mInComingServerUseSSL = 1 == (i & 1);
                            accountMetaData.mInComingServerUseTLS = 2 == (i & 2);
                            accountMetaData.mInComingServerAcceptAllCertificates = 8 == (i & 8);
                            accountMetaData.mInComingServerLogin = cursor.getString(cursor.getColumnIndex("login"));
                            accountMetaData.mInComingServerPassword = cursor.getString(cursor.getColumnIndex("password"));
                            accountMetaData.mInComingServerPathPrefix = cursor.getString(cursor.getColumnIndex("domain"));
                        } else {
                            accountMetaData.mOutGoingProtocol = cursor.getString(cursor.getColumnIndex("protocol"));
                            accountMetaData.mOutGoingServerAddress = cursor.getString(cursor.getColumnIndex("address"));
                            accountMetaData.mOutGoingServerPort = cursor.getInt(cursor.getColumnIndex("port"));
                            int i2 = cursor.getInt(cursor.getColumnIndex("flags"));
                            accountMetaData.mOutGoingServerFlags = i2;
                            accountMetaData.mOutGoingServerUseSSL = 1 == (i2 & 1);
                            accountMetaData.mOutGoingServerUseTLS = 2 == (i2 & 2);
                            accountMetaData.mOutGoingServerAcceptAllCertificates = 8 == (i2 & 8);
                            accountMetaData.mOutGoingServerLogin = cursor.getString(cursor.getColumnIndex("login"));
                            accountMetaData.mOutGoingServerPassword = cursor.getString(cursor.getColumnIndex("password"));
                            accountMetaData.mOutGoingServerPathPrefix = cursor.getString(cursor.getColumnIndex("domain"));
                        }
                        z2 = true;
                    }
                } else {
                    Log.i("SettingsUtils", "loadHostAuth row count : Email cursor is invalid");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            if (0 != 0) {
                cursor.close();
            }
        }
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

    /* JADX WARN: Code restructure failed: missing block: B:17:0x00ec, code lost:
    
        if (r1 != null) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00ee, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0100, code lost:
    
        if (r1 == null) goto L40;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:42:0x010c A[Catch: all -> 0x0110, TryCatch #0 {, blocks: (B:4:0x0006, B:7:0x0021, B:16:0x00e9, B:18:0x00ee, B:33:0x00fd, B:40:0x0107, B:42:0x010c, B:43:0x010f), top: B:3:0x0006 }] */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r7v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static synchronized String getSMIMEAlias(ContextInfo contextInfo, long j, Context context, boolean z) {
        Cursor cursor;
        String str;
        synchronized (SettingsUtils.class) {
            int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
            int i = contextInfo.mContainerId;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            String[] strArr = new String[2];
            strArr[0] = KnoxCustomManagerService.f1773ID;
            strArr[1] = z ? "smimeOwnSignCertAlias" : "smimeOwnCertificateAlias";
            ?? r7 = 0;
            str = null;
            str = null;
            str = null;
            str = null;
            try {
                try {
                    if (callingOrCurrentUserId != 0) {
                        if (isPersona(callingOrCurrentUserId, context)) {
                            emails = context.createPackageContextAsUser(getEmailPackageName(i), 0, new UserHandle(callingOrCurrentUserId));
                            Log.i("SettingsUtils", "getSMIMEAlias : USER space   " + getEmailPackageName(i));
                        } else {
                            emails = context.createPackageContextAsUser(getPackageNameForUid(contextInfo.mCallerUid), 0, new UserHandle(callingOrCurrentUserId));
                            Log.i("SettingsUtils", "getSMIMEAlias : USER space   " + getPackageNameForUid(contextInfo.mCallerUid));
                        }
                    } else {
                        emails = context;
                        Slog.d("SettingsUtils", "getSMIMEAlias : get Context with no MUM Enviroment.");
                    }
                    cursor = emails.getContentResolver().query(getAccountContentUri(i), strArr, "_id = " + j, null, null);
                    if (cursor != null) {
                        try {
                            Log.i("SettingsUtils", "getAccount row count : " + cursor.getCount());
                            if (cursor.getCount() > 0) {
                                cursor.moveToFirst();
                                str = cursor.getString(cursor.getColumnIndex(z ? "smimeOwnSignCertAlias" : "smimeOwnCertificateAlias"));
                            }
                        } catch (Exception e) {
                            e = e;
                            Log.e("SettingsUtils", "getSMIMEAlias() : failed. ", e);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (Throwable th) {
                    th = th;
                    r7 = context;
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (r7 != 0) {
                        r7.close();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                cursor = null;
            } catch (Throwable th2) {
                th = th2;
                Binder.restoreCallingIdentity(clearCallingIdentity);
                if (r7 != 0) {
                }
                throw th;
            }
        }
        return str;
    }

    public static Uri getAccountContentUri(int i) {
        return Uri.parse("content://" + getEmailPackageName(0) + "/account");
    }

    public static Uri getHostAuthContentUri(int i) {
        return Uri.parse("content://" + getEmailPackageName(0) + "/hostauth");
    }

    public static Uri getPoliciesContentUri(int i) {
        return Uri.parse("content://" + getEmailPackageName(0) + "/policies");
    }

    public static String[] getPassword(int i, long j, Context context, int i2) {
        final ConditionVariable conditionVariable = new ConditionVariable();
        IntentFilter intentFilter = new IntentFilter("com.samsung.android.knox.intent.action.RESULT_EMAILACCOUNT_PASSWORD_INTERNAL");
        final String[] strArr = new String[2];
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.email.SettingsUtils.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String stringExtra = intent.getStringExtra("eas_account");
                if (stringExtra == null) {
                    stringExtra = intent.getStringExtra("com.samsung.android.knox.intent.extra.ACCOUNT_EAS_INTERNAL");
                }
                long longExtra = intent.getLongExtra("com.samsung.android.knox.intent.extra.USER_PASSWD_ID_INTERNAL", intent.getLongExtra("user_passwd_id", -1L));
                long longExtra2 = intent.getLongExtra("com.samsung.android.knox.intent.extra.OUTGOING_USER_PASSWD_ID_INTERNAL", intent.getLongExtra("outgoing_user_passwd_id", -1L));
                if (stringExtra != null && stringExtra.equals("eas")) {
                    strArr[0] = SettingsUtils.getSecurityPassword("E#" + longExtra);
                    strArr[1] = null;
                } else {
                    strArr[0] = SettingsUtils.getSecurityPassword("I#" + longExtra);
                    strArr[1] = SettingsUtils.getSecurityPassword("O#" + longExtra2);
                }
                conditionVariable.open();
            }
        };
        context.registerReceiverAsUser(broadcastReceiver, new UserHandle(i2), intentFilter, "com.samsung.android.knox.permission.KNOX_EXCHANGE", null);
        Intent intent = new Intent("com.samsung.android.knox.intent.action.REQUEST_EMAILACCOUNT_PASSWORD_INTERNAL");
        intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL", j);
        context.sendBroadcastAsUser(intent, new UserHandle(i2), "com.samsung.android.knox.permission.KNOX_EMAIL");
        if (conditionVariable.block(10000L)) {
            Log.i("SettingsUtils", "password obtained");
        } else {
            Log.i("SettingsUtils", "timeout while obtaining password");
        }
        context.unregisterReceiver(broadcastReceiver);
        return strArr;
    }

    public static boolean setSecurityPassword(String str, String str2) {
        if (str == null || str2 == null) {
            Log.w("SettingsUtils", "setSecurityPassword() failed : invalid parameter");
            return false;
        }
        try {
            mServerPassword.put(str, str2);
            Log.w("SettingsUtils", "setSecurityPassword() success");
            return true;
        } catch (Exception unused) {
            Log.w("SettingsUtils", "setSecurityPassword() failed");
            return false;
        }
    }

    public static String getSecurityPassword(String str) {
        try {
            String str2 = (String) mServerPassword.get(str);
            mServerPassword.remove(str);
            return str2;
        } catch (Exception unused) {
            Log.w("SettingsUtils", "getSecurityPassword() failed");
            return null;
        }
    }

    public static boolean setSecurityPasswordFocus(String str, String str2) {
        if (str == null || str2 == null) {
            Log.w("SettingsUtils", "setSecurityPasswordFocus() failed : invalid parameter");
            return false;
        }
        try {
            mServerPasswordFocus.put(str, str2);
            Log.w("SettingsUtils", "setSecurityPasswordFocus() success");
            return true;
        } catch (Exception unused) {
            Log.w("SettingsUtils", "setSecurityPasswordFocus() failed");
            return false;
        }
    }

    public static String getSecurityPasswordFocus(String str) {
        try {
            String str2 = (String) mServerPasswordFocus.get(str);
            mServerPasswordFocus.remove(str);
            return str2;
        } catch (Exception unused) {
            Log.w("SettingsUtils", "getSecurityPasswordFocus() failed");
            return null;
        }
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

    public static boolean isPersona(int i, Context context) {
        if (i != 0) {
            return ((IPersonaManagerAdapter) AdapterRegistry.getAdapter(IPersonaManagerAdapter.class)).isValidKnoxId(i);
        }
        return false;
    }

    public static int getSMIMEModeFromAction(String str) {
        if (str != null) {
            if (str.equals("com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_INTERNAL")) {
                return 1;
            }
            if (str.equals("com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_FOR_ENCRYPTION_INTERNAL")) {
                return 2;
            }
            if (str.equals("com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_FOR_SIGNING_INTERNAL")) {
                return 3;
            }
        }
        return -1;
    }

    public static boolean isPackageInstalled(String str, int i) {
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

    public static boolean isAccountRemovalAllowed(String str, String str2) {
        DeviceAccountPolicy deviceAccountPolicy = (DeviceAccountPolicy) EnterpriseService.getPolicyService("device_account_policy");
        return deviceAccountPolicy == null || deviceAccountPolicy.isAccountRemovalAllowed(getAccountType(false, 0), str, false);
    }

    public static Account getAccountFromEnterpriseEmailAccount(EnterpriseEmailAccount enterpriseEmailAccount) {
        Slog.i("SettingsUtils", "getAccountFromEnterpriseEmailAccount() : " + enterpriseEmailAccount);
        if (enterpriseEmailAccount == null) {
            return null;
        }
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
        if (enterpriseExchangeAccount == null) {
            return null;
        }
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
}
