package com.android.server.enterprise.email;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.UserHandle;
import android.sec.enterprise.email.EnterpriseEmailAccount;
import android.sec.enterprise.email.EnterpriseExchangeAccount;
import android.sec.enterprise.email.EnterpriseLDAPAccount;
import android.util.Log;
import android.util.secutil.Slog;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public abstract class EmailProviderHelper {
    public static Map mAccountObjectMap = new HashMap();

    public static Cursor createEmailContentProviderCursor(Context context, ContextInfo contextInfo, Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Context createPackageContextAsUser;
        if (context == null) {
            return null;
        }
        int userId = UserHandle.getUserId(contextInfo.mCallerUid);
        int i = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (SettingsUtils.isPersona(i, context)) {
                createPackageContextAsUser = context.createPackageContextAsUser(SettingsUtils.getEmailPackageName(i), 0, new UserHandle(i));
            } else {
                createPackageContextAsUser = context.createPackageContextAsUser(SettingsUtils.getEmailPackageName(userId), 0, new UserHandle(userId));
            }
            return createPackageContextAsUser.getContentResolver().query(uri, strArr, str, strArr2, str2);
        } catch (Exception e) {
            Log.e("EmailProviderHelperService", "createEmailContentProviderCursor() : Failed, Exception occurs. ", e);
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x005a, code lost:
    
        if (r10.isClosed() == false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0043, code lost:
    
        if (r10.isClosed() == false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0045, code lost:
    
        r10.close();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static long[] getAllAccountIDS(Context context, ContextInfo contextInfo) {
        Cursor cursor;
        int i = 0;
        Cursor cursor2 = null;
        r2 = null;
        r2 = null;
        r2 = null;
        r2 = null;
        long[] jArr = null;
        try {
            cursor = createEmailContentProviderCursor(context, contextInfo, SettingsUtils.getAccountContentUri(0), new String[]{KnoxCustomManagerService.f1773ID}, null, null, null);
            if (cursor != null) {
                try {
                    try {
                        if (cursor.getCount() > 0) {
                            long[] jArr2 = new long[cursor.getCount()];
                            while (cursor.moveToNext()) {
                                int i2 = i + 1;
                                jArr2[i] = cursor.getInt(cursor.getColumnIndex(KnoxCustomManagerService.f1773ID));
                                i = i2;
                            }
                            jArr = jArr2;
                        }
                    } catch (Exception e) {
                        e = e;
                        Log.e("EmailProviderHelperService", "getAllAccountIDS() : Failed, Exception occurs. ", e);
                        if (cursor != null) {
                        }
                        return jArr;
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor2 = cursor;
                    if (cursor2 != null && !cursor2.isClosed()) {
                        cursor2.close();
                    }
                    throw th;
                }
            }
            if (cursor != null) {
            }
        } catch (Exception e2) {
            e = e2;
            cursor = null;
        } catch (Throwable th2) {
            th = th2;
            if (cursor2 != null) {
                cursor2.close();
            }
            throw th;
        }
        return jArr;
    }

    public static EnterpriseExchangeAccount getExchangeAccountObject(ContextInfo contextInfo, long j) {
        Object obj = mAccountObjectMap.get("A" + j);
        if (obj != null && (obj instanceof EnterpriseExchangeAccount)) {
            mAccountObjectMap.remove("A" + j);
            return (EnterpriseExchangeAccount) obj;
        }
        Log.i("EmailProviderHelperService", "getExchangeAccountObject() : failed. ");
        return null;
    }

    public static EnterpriseEmailAccount getEmailAccountObject(ContextInfo contextInfo, long j) {
        Object obj = mAccountObjectMap.get("A" + j);
        if (obj != null && (obj instanceof EnterpriseEmailAccount)) {
            mAccountObjectMap.remove("A" + j);
            return (EnterpriseEmailAccount) obj;
        }
        Log.i("EmailProviderHelperService", "getEmailAccountObject() : failed. ");
        return null;
    }

    public static long setEnterpriseExchangeAccountObject(ContextInfo contextInfo, EnterpriseExchangeAccount enterpriseExchangeAccount) {
        if (enterpriseExchangeAccount == null) {
            Log.i("EmailProviderHelperService", "setEnterpriseExchangeAccountObject() : failed with invalid object. ");
            return -1L;
        }
        long createID = createID();
        mAccountObjectMap.put("A" + createID, enterpriseExchangeAccount);
        return createID;
    }

    public static long setEnterpriseEmailAccountObject(ContextInfo contextInfo, EnterpriseEmailAccount enterpriseEmailAccount) {
        if (enterpriseEmailAccount == null) {
            Log.i("EmailProviderHelperService", "setEnterpriseEmailAccountObject() : failed with invalid object. ");
            return -1L;
        }
        long createID = createID();
        mAccountObjectMap.put("A" + createID, enterpriseEmailAccount);
        return createID;
    }

    public static long createID() {
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis < 0 ? currentTimeMillis * (-1) : currentTimeMillis;
    }

    public static Uri getEmailContentUri() {
        return Uri.parse("content://com.samsung.android.email.mdm.provider");
    }

    public static Uri getLDAPContentUri() {
        return Uri.parse("content://com.samsung.android.email.ldap.provider");
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0061, code lost:
    
        if (r10.isClosed() == false) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0063, code lost:
    
        r10.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0076, code lost:
    
        if (r10.isClosed() == false) goto L22;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:23:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0090  */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static EnterpriseEmailAccount getEnterpriseEmailAccount(Context context, ContextInfo contextInfo, long j) {
        Cursor cursor;
        ?? r2 = 0;
        r2 = null;
        r2 = null;
        r2 = null;
        r2 = null;
        r2 = null;
        EnterpriseEmailAccount enterpriseEmailAccount = null;
        try {
            if (j <= 0) {
                Log.d("EmailProviderHelperService", "getEnterpriseEmailAccount() : Failed, invalid account Id = " + j);
                return null;
            }
            try {
                cursor = createEmailContentProviderCursor(context, contextInfo, getEmailContentUri(), null, "getAccountInfo", new String[]{String.valueOf(j)}, null);
                try {
                    if (cursor != null) {
                        Bundle extras = cursor.getExtras();
                        if (extras != null) {
                            Parcelable parcelable = extras.getParcelable("email.account");
                            if (parcelable != null && (parcelable instanceof EnterpriseEmailAccount)) {
                                enterpriseEmailAccount = (EnterpriseEmailAccount) parcelable;
                            }
                        } else {
                            Log.i("EmailProviderHelperService", "getEnterpriseEmailAccount() : Fail to get Data from Email. ");
                        }
                    } else {
                        Log.i("EmailProviderHelperService", "getEnterpriseEmailAccount() : cannot get cursor from EmailProvider.");
                    }
                    if (cursor != null) {
                    }
                } catch (Exception e) {
                    e = e;
                    Log.e("EmailProviderHelperService", "getEnterpriseEmailAccount() : Failed, Exception occurs. ", e);
                    if (cursor != null) {
                    }
                    if (enterpriseEmailAccount != null) {
                    }
                    return enterpriseEmailAccount;
                }
            } catch (Exception e2) {
                e = e2;
                cursor = null;
            } catch (Throwable th) {
                th = th;
                if (r2 != 0 && !r2.isClosed()) {
                    r2.close();
                }
                throw th;
            }
            if (enterpriseEmailAccount != null) {
                Slog.d("EmailProviderHelperService", "getEnterpriseEmailAccount() : Failed, accId = " + j);
            } else {
                Slog.d("EmailProviderHelperService", "getEnterpriseEmailAccount() : successfully get Data from Email, accId = " + j);
            }
            return enterpriseEmailAccount;
        } catch (Throwable th2) {
            th = th2;
            r2 = context;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x005e, code lost:
    
        if (r4.isClosed() == false) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean updateEnterpriseEmailAccount(Context context, ContextInfo contextInfo, EnterpriseEmailAccount enterpriseEmailAccount) {
        boolean z = false;
        Cursor cursor = null;
        try {
            try {
                cursor = createEmailContentProviderCursor(context, contextInfo, getEmailContentUri(), null, "updateEmailAccount", new String[]{String.valueOf(setEnterpriseEmailAccountObject(contextInfo, enterpriseEmailAccount)), enterpriseEmailAccount.mInComingProtocol}, null);
                if (cursor != null && cursor.moveToFirst()) {
                    z = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("updateEmailAccount")));
                } else {
                    Log.i("EmailProviderHelperService", "updateEnterpriseEmailAccount() : cannot get cursor from EmailProvider.");
                }
            } catch (Exception e) {
                Log.e("EmailProviderHelperService", "updateEnterpriseEmailAccount() : Failed, Exception occurs. ", e);
                if (0 != 0) {
                }
            }
            Log.i("EmailProviderHelperService", "updateEnterpriseEmailAccount() : ret = " + z);
            return z;
        } finally {
            if (0 != 0 && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    public static EnterpriseEmailAccount[] getAllEnterpriseEmailAccount(Context context, ContextInfo contextInfo) {
        ArrayList arrayList = new ArrayList();
        long[] allAccountIDS = getAllAccountIDS(context, contextInfo);
        int i = 0;
        if (allAccountIDS != null) {
            for (long j : allAccountIDS) {
                EnterpriseEmailAccount enterpriseEmailAccount = getEnterpriseEmailAccount(context, contextInfo, j);
                if (enterpriseEmailAccount != null) {
                    arrayList.add(enterpriseEmailAccount);
                }
            }
        }
        if (arrayList.size() <= 0) {
            return null;
        }
        EnterpriseEmailAccount[] enterpriseEmailAccountArr = new EnterpriseEmailAccount[arrayList.size()];
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            enterpriseEmailAccountArr[i] = (EnterpriseEmailAccount) it.next();
            i++;
        }
        return enterpriseEmailAccountArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0061, code lost:
    
        if (r10.isClosed() == false) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0063, code lost:
    
        r10.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0076, code lost:
    
        if (r10.isClosed() == false) goto L22;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:23:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0090  */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static EnterpriseExchangeAccount getEnterpriseExchangeAccount(Context context, ContextInfo contextInfo, long j) {
        Cursor cursor;
        ?? r2 = 0;
        r2 = null;
        r2 = null;
        r2 = null;
        r2 = null;
        r2 = null;
        EnterpriseExchangeAccount enterpriseExchangeAccount = null;
        try {
            if (j <= 0) {
                Log.d("EmailProviderHelperService", "getEnterpriseExchangeAccount() : Failed, invalid account Id = " + j);
                return null;
            }
            try {
                cursor = createEmailContentProviderCursor(context, contextInfo, getEmailContentUri(), null, "getAccountInfo", new String[]{String.valueOf(j)}, null);
                try {
                    if (cursor != null) {
                        Bundle extras = cursor.getExtras();
                        if (extras != null) {
                            Parcelable parcelable = extras.getParcelable("eas.account");
                            if (parcelable != null && (parcelable instanceof EnterpriseExchangeAccount)) {
                                enterpriseExchangeAccount = (EnterpriseExchangeAccount) parcelable;
                            }
                        } else {
                            Log.i("EmailProviderHelperService", "getEnterpriseExchangeAccount() : Fail to get Data from Email. ");
                        }
                    } else {
                        Log.i("EmailProviderHelperService", "getEnterpriseExchangeAccount() : cannot get cursor from EmailProvider.");
                    }
                    if (cursor != null) {
                    }
                } catch (Exception e) {
                    e = e;
                    Log.e("EmailProviderHelperService", "getEnterpriseExchangeAccount() : Failed, Exception occurs. ", e);
                    if (cursor != null) {
                    }
                    if (enterpriseExchangeAccount != null) {
                    }
                    return enterpriseExchangeAccount;
                }
            } catch (Exception e2) {
                e = e2;
                cursor = null;
            } catch (Throwable th) {
                th = th;
                if (r2 != 0 && !r2.isClosed()) {
                    r2.close();
                }
                throw th;
            }
            if (enterpriseExchangeAccount != null) {
                Slog.d("EmailProviderHelperService", "getEnterpriseExchangeAccount() : Failed, accId = " + j);
            } else {
                Slog.d("EmailProviderHelperService", "getEnterpriseExchangeAccount() : successfully get Data from Email. accId = " + j);
            }
            return enterpriseExchangeAccount;
        } catch (Throwable th2) {
            th = th2;
            r2 = context;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x005e, code lost:
    
        if (r3.isClosed() == false) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean updateEnterpriseExchangeAccount(Context context, ContextInfo contextInfo, EnterpriseExchangeAccount enterpriseExchangeAccount) {
        long enterpriseExchangeAccountObject = setEnterpriseExchangeAccountObject(contextInfo, enterpriseExchangeAccount);
        boolean z = false;
        Cursor cursor = null;
        try {
            try {
                cursor = createEmailContentProviderCursor(context, contextInfo, getEmailContentUri(), null, "updateEmailAccount", new String[]{String.valueOf(enterpriseExchangeAccountObject), "eas"}, null);
                if (cursor != null && cursor.moveToFirst()) {
                    z = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("updateEmailAccount")));
                } else {
                    Log.i("EmailProviderHelperService", "updateEnterpriseExchangeAccount() : cannot get cursor from EmailProvider.");
                }
            } catch (Exception e) {
                Log.e("EmailProviderHelperService", "updateEnterpriseExchangeAccount() : Failed, Exception occurs. ", e);
                if (0 != 0) {
                }
            }
            Log.i("EmailProviderHelperService", "updateEnterpriseExchangeAccount() : ret = " + z);
            return z;
        } finally {
            if (0 != 0 && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    public static EnterpriseExchangeAccount[] getAllEnterpriseExchangeAccount(Context context, ContextInfo contextInfo) {
        ArrayList arrayList = new ArrayList();
        long[] allAccountIDS = getAllAccountIDS(context, contextInfo);
        int i = 0;
        if (allAccountIDS != null) {
            for (long j : allAccountIDS) {
                EnterpriseExchangeAccount enterpriseExchangeAccount = getEnterpriseExchangeAccount(context, contextInfo, j);
                if (enterpriseExchangeAccount != null) {
                    arrayList.add(enterpriseExchangeAccount);
                }
            }
        }
        if (arrayList.size() <= 0) {
            return null;
        }
        EnterpriseExchangeAccount[] enterpriseExchangeAccountArr = new EnterpriseExchangeAccount[arrayList.size()];
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            enterpriseExchangeAccountArr[i] = (EnterpriseExchangeAccount) it.next();
            i++;
        }
        return enterpriseExchangeAccountArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x004b, code lost:
    
        if (r10 == false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x004d, code lost:
    
        r9.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0060, code lost:
    
        if (r10 == false) goto L19;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v4, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r9v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static EnterpriseLDAPAccount getEnterpriseLDAPAccount(Context context, ContextInfo contextInfo, long j) {
        Cursor cursor;
        EnterpriseLDAPAccount enterpriseLDAPAccount = null;
        try {
            try {
                Cursor createEmailContentProviderCursor = createEmailContentProviderCursor(context, contextInfo, getLDAPContentUri(), null, "getLDAPAccount", new String[]{String.valueOf(j)}, null);
                try {
                    if (createEmailContentProviderCursor != null) {
                        Bundle extras = createEmailContentProviderCursor.getExtras();
                        if (extras != null) {
                            Parcelable parcelable = extras.getParcelable("email.ldap.account");
                            if (parcelable != null && (parcelable instanceof EnterpriseLDAPAccount)) {
                                Slog.d("EmailProviderHelperService", "getEnterpriseLDAPAccount() : successfully get Data from Email. ");
                                enterpriseLDAPAccount = (EnterpriseLDAPAccount) parcelable;
                            }
                        } else {
                            Log.i("EmailProviderHelperService", "getEnterpriseLDAPAccount() : Fail to get Data from Email. ");
                        }
                    } else {
                        Log.i("EmailProviderHelperService", "getEnterpriseLDAPAccount() : cannot get cursor from EmailProvider.");
                    }
                    if (createEmailContentProviderCursor != null) {
                        boolean isClosed = createEmailContentProviderCursor.isClosed();
                        cursor = createEmailContentProviderCursor;
                    }
                } catch (Exception e) {
                    e = e;
                    context = createEmailContentProviderCursor;
                    Log.e("EmailProviderHelperService", "getEnterpriseExchangeAccount() : Failed, Exception occurs. ", e);
                    if (context != 0) {
                        boolean isClosed2 = context.isClosed();
                        cursor = context;
                    }
                    return enterpriseLDAPAccount;
                }
            } catch (Throwable th) {
                th = th;
                enterpriseLDAPAccount = context;
                if (enterpriseLDAPAccount != null && !enterpriseLDAPAccount.isClosed()) {
                    enterpriseLDAPAccount.close();
                }
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            context = 0;
        } catch (Throwable th2) {
            th = th2;
            if (enterpriseLDAPAccount != null) {
                enterpriseLDAPAccount.close();
            }
            throw th;
        }
        return enterpriseLDAPAccount;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x003c, code lost:
    
        if (r9.isClosed() == false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x003e, code lost:
    
        r9.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0051, code lost:
    
        if (r9.isClosed() == false) goto L18;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static List getAllLDAPAccount(Context context, ContextInfo contextInfo) {
        Cursor cursor;
        ?? r1 = 0;
        r1 = null;
        r1 = null;
        r1 = null;
        r1 = null;
        ArrayList arrayList = null;
        try {
            try {
                cursor = createEmailContentProviderCursor(context, contextInfo, getLDAPContentUri(), null, "getAllLDAPAccounts", null, null);
                try {
                    if (cursor != null) {
                        Bundle extras = cursor.getExtras();
                        if (extras != null) {
                            ArrayList parcelableArrayList = extras.getParcelableArrayList("email.ldap.all.account");
                            if (parcelableArrayList == null) {
                                parcelableArrayList = new ArrayList();
                            }
                            arrayList = parcelableArrayList;
                        } else {
                            Log.i("EmailProviderHelperService", "getAllLDAPAccount() : Fail to get Data from Email. ");
                        }
                    } else {
                        Log.i("EmailProviderHelperService", "getAllLDAPAccount() : cannot get cursor from EmailProvider.");
                    }
                    if (cursor != null) {
                    }
                } catch (Exception e) {
                    e = e;
                    Log.e("EmailProviderHelperService", "getAllLDAPAccount() : Failed, Exception occurs. ", e);
                    if (cursor != null) {
                    }
                    return arrayList;
                }
            } catch (Throwable th) {
                th = th;
                r1 = context;
                if (r1 != 0 && !r1.isClosed()) {
                    r1.close();
                }
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            cursor = null;
        } catch (Throwable th2) {
            th = th2;
            if (r1 != 0) {
                r1.close();
            }
            throw th;
        }
        return arrayList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0066, code lost:
    
        if (r2.isClosed() == false) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean deleteLDAPAccount(Context context, ContextInfo contextInfo, long j) {
        boolean z = false;
        Cursor cursor = null;
        try {
            try {
                cursor = createEmailContentProviderCursor(context, contextInfo, getLDAPContentUri(), null, "deleteLDAPAccount", new String[]{String.valueOf(j)}, null);
                if (cursor != null && cursor.moveToFirst()) {
                    boolean parseBoolean = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("deleteLDAPAccount")));
                    Slog.d("EmailProviderHelperService", "deleteLDAPAccount() : ret = " + parseBoolean);
                    z = parseBoolean;
                } else {
                    Slog.d("EmailProviderHelperService", "deleteLDAPAccount() : cannot get cursor from EmailProvider.");
                }
            } catch (Exception e) {
                Log.e("EmailProviderHelperService", "deleteLDAPAccount() : Failed, Exception occurs. ", e);
                if (0 != 0) {
                }
            }
            return z;
        } finally {
            if (0 != 0 && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }
}
