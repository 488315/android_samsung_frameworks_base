package com.android.server.enterprise.email;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.UserHandle;
import android.sec.enterprise.email.EnterpriseEmailAccount;
import android.sec.enterprise.email.EnterpriseExchangeAccount;
import android.util.Log;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

import com.samsung.android.knox.ContextInfo;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class EmailProviderHelper {
    public static final Map mAccountObjectMap = new HashMap();

    public static Cursor createEmailContentProviderCursor(
            Context context,
            ContextInfo contextInfo,
            Uri uri,
            String[] strArr,
            String str,
            String[] strArr2) {
        if (context == null) {
            return null;
        }
        int userId = UserHandle.getUserId(contextInfo.mCallerUid);
        int i = contextInfo.mContainerId;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Cursor query =
                        (SettingsUtils.isPersona(i)
                                        ? context.createPackageContextAsUser(
                                                "com.samsung.android.email.provider",
                                                0,
                                                new UserHandle(i))
                                        : context.createPackageContextAsUser(
                                                "com.samsung.android.email.provider",
                                                0,
                                                new UserHandle(userId)))
                                .getContentResolver()
                                .query(uri, strArr, str, strArr2, null);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return query;
            } catch (Exception e) {
                Log.e(
                        "EmailProviderHelperService",
                        "createEmailContentProviderCursor() : Failed, Exception occurs. ",
                        e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0058, code lost:

       if (r3.isClosed() == false) goto L16;
    */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x005a, code lost:

       r3.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x006a, code lost:

       if (r3.isClosed() == false) goto L16;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean deleteLDAPAccount(
            long r11, android.content.Context r13, com.samsung.android.knox.ContextInfo r14) {
        /*
            java.lang.String r0 = "EmailProviderHelperService"
            java.lang.String r1 = "deleteLDAPAccount() : ret = "
            r2 = 0
            r3 = 0
            java.lang.String r4 = "content://com.samsung.android.email.ldap.provider"
            android.net.Uri r7 = android.net.Uri.parse(r4)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            java.lang.String r9 = "deleteLDAPAccount"
            java.lang.String r11 = java.lang.String.valueOf(r11)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            java.lang.String[] r10 = new java.lang.String[]{r11}     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            r8 = 0
            r5 = r13
            r6 = r14
            android.database.Cursor r3 = createEmailContentProviderCursor(r5, r6, r7, r8, r9, r10)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            if (r3 == 0) goto L4c
            boolean r11 = r3.moveToFirst()     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            if (r11 == 0) goto L4c
            java.lang.String r11 = "deleteLDAPAccount"
            int r11 = r3.getColumnIndex(r11)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            java.lang.String r11 = r3.getString(r11)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            boolean r11 = java.lang.Boolean.parseBoolean(r11)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            r12.<init>(r1)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            r12.append(r11)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            java.lang.String r12 = r12.toString()     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            android.util.secutil.Slog.d(r0, r12)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            r2 = r11
            goto L52
        L48:
            r11 = move-exception
            goto L6e
        L4a:
            r11 = move-exception
            goto L5e
        L4c:
            java.lang.String r11 = "deleteLDAPAccount() : cannot get cursor from EmailProvider."
            android.util.secutil.Slog.d(r0, r11)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
        L52:
            if (r3 == 0) goto L6d
            boolean r11 = r3.isClosed()
            if (r11 != 0) goto L6d
        L5a:
            r3.close()
            goto L6d
        L5e:
            java.lang.String r12 = "deleteLDAPAccount() : Failed, Exception occurs. "
            android.util.Log.e(r0, r12, r11)     // Catch: java.lang.Throwable -> L48
            if (r3 == 0) goto L6d
            boolean r11 = r3.isClosed()
            if (r11 != 0) goto L6d
            goto L5a
        L6d:
            return r2
        L6e:
            if (r3 == 0) goto L79
            boolean r12 = r3.isClosed()
            if (r12 != 0) goto L79
            r3.close()
        L79:
            throw r11
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.email.EmailProviderHelper.deleteLDAPAccount(long,"
                    + " android.content.Context, com.samsung.android.knox.ContextInfo):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x004a, code lost:

       r9.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0069, code lost:

       if (r9.isClosed() == false) goto L21;
    */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0048, code lost:

       if (r9.isClosed() == false) goto L21;
    */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0065  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static long[] getAllAccountIDS(
            android.content.Context r9, com.samsung.android.knox.ContextInfo r10) {
        /*
            java.lang.String r0 = "_id"
            r1 = 0
            android.content.Context r2 = com.android.server.enterprise.email.SettingsUtils.emails     // Catch: java.lang.Throwable -> L57 java.lang.Exception -> L59
            java.lang.String r2 = "content://com.samsung.android.email.provider/account"
            android.net.Uri r5 = android.net.Uri.parse(r2)     // Catch: java.lang.Throwable -> L57 java.lang.Exception -> L59
            java.lang.String[] r6 = new java.lang.String[]{r0}     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
            r8 = 0
            r7 = 0
            r3 = r9
            r4 = r10
            android.database.Cursor r9 = createEmailContentProviderCursor(r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
            if (r9 == 0) goto L42
            int r10 = r9.getCount()     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            if (r10 <= 0) goto L42
            int r10 = r9.getCount()     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            long[] r10 = new long[r10]     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            r2 = 0
        L27:
            boolean r3 = r9.moveToNext()     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            if (r3 == 0) goto L41
            int r3 = r2 + 1
            int r4 = r9.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            int r4 = r9.getInt(r4)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            long r4 = (long) r4     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            r10[r2] = r4     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            r2 = r3
            goto L27
        L3c:
            r10 = move-exception
            r1 = r9
            goto L6d
        L3f:
            r10 = move-exception
            goto L5b
        L41:
            r1 = r10
        L42:
            if (r9 == 0) goto L6c
            boolean r10 = r9.isClosed()
            if (r10 != 0) goto L6c
        L4a:
            r9.close()
            goto L6c
        L4e:
            r10 = move-exception
            goto L6d
        L50:
            r10 = move-exception
        L51:
            r9 = r1
            goto L5b
        L53:
            r10 = r9
            goto L6d
        L55:
            r10 = r9
            goto L51
        L57:
            r9 = move-exception
            goto L53
        L59:
            r9 = move-exception
            goto L55
        L5b:
            java.lang.String r0 = "EmailProviderHelperService"
            java.lang.String r2 = "getAllAccountIDS() : Failed, Exception occurs. "
            android.util.Log.e(r0, r2, r10)     // Catch: java.lang.Throwable -> L3c
            if (r9 == 0) goto L6c
            boolean r10 = r9.isClosed()
            if (r10 != 0) goto L6c
            goto L4a
        L6c:
            return r1
        L6d:
            if (r1 == 0) goto L78
            boolean r9 = r1.isClosed()
            if (r9 != 0) goto L78
            r1.close()
        L78:
            throw r10
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.email.EmailProviderHelper.getAllAccountIDS(android.content.Context,"
                    + " com.samsung.android.knox.ContextInfo):long[]");
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0045, code lost:

       if (r9.isClosed() == false) goto L21;
    */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0047, code lost:

       r9.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0064, code lost:

       if (r9.isClosed() == false) goto L21;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0060  */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v2, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List getAllLDAPAccount(
            android.content.Context r9, com.samsung.android.knox.ContextInfo r10) {
        /*
            java.lang.String r0 = "EmailProviderHelperService"
            r1 = 0
            java.lang.String r2 = "content://com.samsung.android.email.ldap.provider"
            android.net.Uri r5 = android.net.Uri.parse(r2)     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            java.lang.String r7 = "getAllLDAPAccounts"
            r8 = 0
            r6 = 0
            r3 = r9
            r4 = r10
            android.database.Cursor r9 = createEmailContentProviderCursor(r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L4b java.lang.Exception -> L4d
            if (r9 == 0) goto L39
            android.os.Bundle r10 = r9.getExtras()     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L30
            if (r10 == 0) goto L32
            java.lang.String r2 = "email.ldap.all.account"
            java.util.ArrayList r10 = r10.getParcelableArrayList(r2)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L30
            if (r10 != 0) goto L2b
            java.util.ArrayList r10 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L30
            r10.<init>()     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L30
        L2b:
            r1 = r10
            goto L3f
        L2d:
            r10 = move-exception
            r1 = r9
            goto L68
        L30:
            r10 = move-exception
            goto L58
        L32:
            java.lang.String r10 = "getAllLDAPAccount() : Fail to get Data from Email. "
            android.util.Log.i(r0, r10)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L30
            goto L3f
        L39:
            java.lang.String r10 = "getAllLDAPAccount() : cannot get cursor from EmailProvider."
            android.util.Log.i(r0, r10)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L30
        L3f:
            if (r9 == 0) goto L67
            boolean r10 = r9.isClosed()
            if (r10 != 0) goto L67
        L47:
            r9.close()
            goto L67
        L4b:
            r10 = move-exception
            goto L68
        L4d:
            r10 = move-exception
        L4e:
            r9 = r1
            goto L58
        L50:
            r10 = r9
            goto L68
        L52:
            r10 = r9
            goto L4e
        L54:
            r9 = move-exception
            goto L50
        L56:
            r9 = move-exception
            goto L52
        L58:
            java.lang.String r2 = "getAllLDAPAccount() : Failed, Exception occurs. "
            android.util.Log.e(r0, r2, r10)     // Catch: java.lang.Throwable -> L2d
            if (r9 == 0) goto L67
            boolean r10 = r9.isClosed()
            if (r10 != 0) goto L67
            goto L47
        L67:
            return r1
        L68:
            if (r1 == 0) goto L73
            boolean r9 = r1.isClosed()
            if (r9 != 0) goto L73
            r1.close()
        L73:
            throw r10
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.email.EmailProviderHelper.getAllLDAPAccount(android.content.Context,"
                    + " com.samsung.android.knox.ContextInfo):java.util.List");
    }

    public static EnterpriseEmailAccount getEmailAccountObject(long j) {
        HashMap hashMap = (HashMap) mAccountObjectMap;
        Object obj = hashMap.get(DeviceIdleController$$ExternalSyntheticOutline0.m(j, "A"));
        if (obj == null || !(obj instanceof EnterpriseEmailAccount)) {
            Log.i("EmailProviderHelperService", "getEmailAccountObject() : failed. ");
            return null;
        }
        hashMap.remove("A" + j);
        return (EnterpriseEmailAccount) obj;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0066, code lost:

       if (r11.isClosed() == false) goto L25;
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0068, code lost:

       r11.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0081, code lost:

       if (r11.isClosed() == false) goto L25;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x007d  */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.sec.enterprise.email.EnterpriseEmailAccount getEnterpriseEmailAccount(
            long r9, android.content.Context r11, com.samsung.android.knox.ContextInfo r12) {
        /*
            r0 = 0
            int r0 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            java.lang.String r1 = "EmailProviderHelperService"
            r2 = 0
            if (r0 > 0) goto L1c
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "getEnterpriseEmailAccount() : Failed, invalid account Id = "
            r11.<init>(r12)
            r11.append(r9)
            java.lang.String r9 = r11.toString()
            android.util.Log.d(r1, r9)
            return r2
        L1c:
            java.lang.String r0 = "content://com.samsung.android.email.mdm.provider"
            android.net.Uri r5 = android.net.Uri.parse(r0)     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L73
            java.lang.String r7 = "getAccountInfo"
            java.lang.String r0 = java.lang.String.valueOf(r9)     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L6e
            java.lang.String[] r8 = new java.lang.String[]{r0}     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L6e
            r6 = 0
            r3 = r11
            r4 = r12
            android.database.Cursor r11 = createEmailContentProviderCursor(r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L6e
            if (r11 == 0) goto L5a
            android.os.Bundle r12 = r11.getExtras()     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            if (r12 == 0) goto L53
            java.lang.String r0 = "email.account"
            android.os.Parcelable r12 = r12.getParcelable(r0)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            if (r12 == 0) goto L60
            boolean r0 = r12 instanceof android.sec.enterprise.email.EnterpriseEmailAccount     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            if (r0 == 0) goto L60
            android.sec.enterprise.email.EnterpriseEmailAccount r12 = (android.sec.enterprise.email.EnterpriseEmailAccount) r12     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            r2 = r12
            goto L60
        L4e:
            r9 = move-exception
            r2 = r11
            goto Lac
        L51:
            r12 = move-exception
            goto L75
        L53:
            java.lang.String r12 = "getEnterpriseEmailAccount() : Fail to get Data from Email. "
            android.util.Log.i(r1, r12)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            goto L60
        L5a:
            java.lang.String r12 = "getEnterpriseEmailAccount() : cannot get cursor from EmailProvider."
            android.util.Log.i(r1, r12)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
        L60:
            if (r11 == 0) goto L84
            boolean r12 = r11.isClosed()
            if (r12 != 0) goto L84
        L68:
            r11.close()
            goto L84
        L6c:
            r9 = move-exception
            goto Lac
        L6e:
            r12 = move-exception
        L6f:
            r11 = r2
            goto L75
        L71:
            r12 = r11
            goto L6f
        L73:
            r11 = move-exception
            goto L71
        L75:
            java.lang.String r0 = "getEnterpriseEmailAccount() : Failed, Exception occurs. "
            android.util.Log.e(r1, r0, r12)     // Catch: java.lang.Throwable -> L4e
            if (r11 == 0) goto L84
            boolean r12 = r11.isClosed()
            if (r12 != 0) goto L84
            goto L68
        L84:
            if (r2 != 0) goto L99
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "getEnterpriseEmailAccount() : Failed, accId = "
            r11.<init>(r12)
            r11.append(r9)
            java.lang.String r9 = r11.toString()
            android.util.secutil.Slog.d(r1, r9)
            goto Lab
        L99:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "getEnterpriseEmailAccount() : successfully get Data from Email, accId = "
            r11.<init>(r12)
            r11.append(r9)
            java.lang.String r9 = r11.toString()
            android.util.secutil.Slog.d(r1, r9)
        Lab:
            return r2
        Lac:
            if (r2 == 0) goto Lb7
            boolean r10 = r2.isClosed()
            if (r10 != 0) goto Lb7
            r2.close()
        Lb7:
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.email.EmailProviderHelper.getEnterpriseEmailAccount(long,"
                    + " android.content.Context,"
                    + " com.samsung.android.knox.ContextInfo):android.sec.enterprise.email.EnterpriseEmailAccount");
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0066, code lost:

       if (r11.isClosed() == false) goto L25;
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0068, code lost:

       r11.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0081, code lost:

       if (r11.isClosed() == false) goto L25;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x007d  */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.sec.enterprise.email.EnterpriseExchangeAccount
            getEnterpriseExchangeAccount(
                    long r9,
                    android.content.Context r11,
                    com.samsung.android.knox.ContextInfo r12) {
        /*
            r0 = 0
            int r0 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            java.lang.String r1 = "EmailProviderHelperService"
            r2 = 0
            if (r0 > 0) goto L1c
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "getEnterpriseExchangeAccount() : Failed, invalid account Id = "
            r11.<init>(r12)
            r11.append(r9)
            java.lang.String r9 = r11.toString()
            android.util.Log.d(r1, r9)
            return r2
        L1c:
            java.lang.String r0 = "content://com.samsung.android.email.mdm.provider"
            android.net.Uri r5 = android.net.Uri.parse(r0)     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L73
            java.lang.String r7 = "getAccountInfo"
            java.lang.String r0 = java.lang.String.valueOf(r9)     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L6e
            java.lang.String[] r8 = new java.lang.String[]{r0}     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L6e
            r6 = 0
            r3 = r11
            r4 = r12
            android.database.Cursor r11 = createEmailContentProviderCursor(r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L6c java.lang.Exception -> L6e
            if (r11 == 0) goto L5a
            android.os.Bundle r12 = r11.getExtras()     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            if (r12 == 0) goto L53
            java.lang.String r0 = "eas.account"
            android.os.Parcelable r12 = r12.getParcelable(r0)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            if (r12 == 0) goto L60
            boolean r0 = r12 instanceof android.sec.enterprise.email.EnterpriseExchangeAccount     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            if (r0 == 0) goto L60
            android.sec.enterprise.email.EnterpriseExchangeAccount r12 = (android.sec.enterprise.email.EnterpriseExchangeAccount) r12     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            r2 = r12
            goto L60
        L4e:
            r9 = move-exception
            r2 = r11
            goto Lac
        L51:
            r12 = move-exception
            goto L75
        L53:
            java.lang.String r12 = "getEnterpriseExchangeAccount() : Fail to get Data from Email. "
            android.util.Log.i(r1, r12)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            goto L60
        L5a:
            java.lang.String r12 = "getEnterpriseExchangeAccount() : cannot get cursor from EmailProvider."
            android.util.Log.i(r1, r12)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
        L60:
            if (r11 == 0) goto L84
            boolean r12 = r11.isClosed()
            if (r12 != 0) goto L84
        L68:
            r11.close()
            goto L84
        L6c:
            r9 = move-exception
            goto Lac
        L6e:
            r12 = move-exception
        L6f:
            r11 = r2
            goto L75
        L71:
            r12 = r11
            goto L6f
        L73:
            r11 = move-exception
            goto L71
        L75:
            java.lang.String r0 = "getEnterpriseExchangeAccount() : Failed, Exception occurs. "
            android.util.Log.e(r1, r0, r12)     // Catch: java.lang.Throwable -> L4e
            if (r11 == 0) goto L84
            boolean r12 = r11.isClosed()
            if (r12 != 0) goto L84
            goto L68
        L84:
            if (r2 != 0) goto L99
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "getEnterpriseExchangeAccount() : Failed, accId = "
            r11.<init>(r12)
            r11.append(r9)
            java.lang.String r9 = r11.toString()
            android.util.secutil.Slog.d(r1, r9)
            goto Lab
        L99:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "getEnterpriseExchangeAccount() : successfully get Data from Email. accId = "
            r11.<init>(r12)
            r11.append(r9)
            java.lang.String r9 = r11.toString()
            android.util.secutil.Slog.d(r1, r9)
        Lab:
            return r2
        Lac:
            if (r2 == 0) goto Lb7
            boolean r10 = r2.isClosed()
            if (r10 != 0) goto Lb7
            r2.close()
        Lb7:
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.email.EmailProviderHelper.getEnterpriseExchangeAccount(long,"
                    + " android.content.Context,"
                    + " com.samsung.android.knox.ContextInfo):android.sec.enterprise.email.EnterpriseExchangeAccount");
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0053, code lost:

       if (r10 == false) goto L22;
    */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0055, code lost:

       r9.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0072, code lost:

       if (r10 == false) goto L22;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x006e  */
    /* JADX WARN: Type inference failed for: r9v0, types: [long] */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v6, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.sec.enterprise.email.EnterpriseLDAPAccount getEnterpriseLDAPAccount(
            long r9, android.content.Context r11, com.samsung.android.knox.ContextInfo r12) {
        /*
            java.lang.String r0 = "EmailProviderHelperService"
            r1 = 0
            java.lang.String r2 = "content://com.samsung.android.email.ldap.provider"
            android.net.Uri r5 = android.net.Uri.parse(r2)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64
            java.lang.String r7 = "getLDAPAccount"
            java.lang.String r9 = java.lang.String.valueOf(r9)     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b
            java.lang.String[] r8 = new java.lang.String[]{r9}     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b
            r6 = 0
            r3 = r11
            r4 = r12
            android.database.Cursor r9 = createEmailContentProviderCursor(r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L59 java.lang.Exception -> L5b
            if (r9 == 0) goto L47
            android.os.Bundle r10 = r9.getExtras()     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3e
            if (r10 == 0) goto L40
            java.lang.String r11 = "email.ldap.account"
            android.os.Parcelable r10 = r10.getParcelable(r11)     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3e
            if (r10 == 0) goto L4d
            boolean r11 = r10 instanceof android.sec.enterprise.email.EnterpriseLDAPAccount     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3e
            if (r11 == 0) goto L4d
            android.sec.enterprise.email.EnterpriseLDAPAccount r10 = (android.sec.enterprise.email.EnterpriseLDAPAccount) r10     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3e
            java.lang.String r11 = "getEnterpriseLDAPAccount() : successfully get Data from Email. "
            android.util.secutil.Slog.d(r0, r11)     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3e
            r1 = r10
            goto L4d
        L3b:
            r10 = move-exception
            r1 = r9
            goto L76
        L3e:
            r10 = move-exception
            goto L66
        L40:
            java.lang.String r10 = "getEnterpriseLDAPAccount() : Fail to get Data from Email. "
            android.util.Log.i(r0, r10)     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3e
            goto L4d
        L47:
            java.lang.String r10 = "getEnterpriseLDAPAccount() : cannot get cursor from EmailProvider."
            android.util.Log.i(r0, r10)     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3e
        L4d:
            if (r9 == 0) goto L75
            boolean r10 = r9.isClosed()
            if (r10 != 0) goto L75
        L55:
            r9.close()
            goto L75
        L59:
            r10 = move-exception
            goto L76
        L5b:
            r10 = move-exception
        L5c:
            r9 = r1
            goto L66
        L5e:
            r10 = r9
            goto L76
        L60:
            r10 = r9
            goto L5c
        L62:
            r9 = move-exception
            goto L5e
        L64:
            r9 = move-exception
            goto L60
        L66:
            java.lang.String r11 = "getEnterpriseExchangeAccount() : Failed, Exception occurs. "
            android.util.Log.e(r0, r11, r10)     // Catch: java.lang.Throwable -> L3b
            if (r9 == 0) goto L75
            boolean r10 = r9.isClosed()
            if (r10 != 0) goto L75
            goto L55
        L75:
            return r1
        L76:
            if (r1 == 0) goto L81
            boolean r9 = r1.isClosed()
            if (r9 != 0) goto L81
            r1.close()
        L81:
            throw r10
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.email.EmailProviderHelper.getEnterpriseLDAPAccount(long,"
                    + " android.content.Context,"
                    + " com.samsung.android.knox.ContextInfo):android.sec.enterprise.email.EnterpriseLDAPAccount");
    }

    public static EnterpriseExchangeAccount getExchangeAccountObject(long j) {
        HashMap hashMap = (HashMap) mAccountObjectMap;
        Object obj = hashMap.get(DeviceIdleController$$ExternalSyntheticOutline0.m(j, "A"));
        if (obj == null || !(obj instanceof EnterpriseExchangeAccount)) {
            Log.i("EmailProviderHelperService", "getExchangeAccountObject() : failed. ");
            return null;
        }
        hashMap.remove("A" + j);
        return (EnterpriseExchangeAccount) obj;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0061, code lost:

       if (r4.isClosed() == false) goto L19;
    */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0063, code lost:

       r4.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0073, code lost:

       if (r4.isClosed() == false) goto L19;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean updateEnterpriseEmailAccount(
            android.content.Context r12,
            com.samsung.android.knox.ContextInfo r13,
            android.sec.enterprise.email.EnterpriseEmailAccount r14) {
        /*
            java.lang.String r0 = "EmailProviderHelperService"
            long r1 = java.lang.System.currentTimeMillis()
            r3 = 0
            int r3 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r3 >= 0) goto Lf
            r3 = -1
            long r1 = r1 * r3
        Lf:
            java.util.Map r3 = com.android.server.enterprise.email.EmailProviderHelper.mAccountObjectMap
            java.lang.String r4 = "A"
            java.lang.String r4 = com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m(r1, r4)
            java.util.HashMap r3 = (java.util.HashMap) r3
            r3.put(r4, r14)
            r3 = 0
            r4 = 0
            java.lang.String r5 = "content://com.samsung.android.email.mdm.provider"
            android.net.Uri r8 = android.net.Uri.parse(r5)     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            java.lang.String r10 = "updateEmailAccount"
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            java.lang.String r14 = r14.mInComingProtocol     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            java.lang.String[] r11 = new java.lang.String[]{r1, r14}     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            r9 = 0
            r6 = r12
            r7 = r13
            android.database.Cursor r4 = createEmailContentProviderCursor(r6, r7, r8, r9, r10, r11)     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            if (r4 == 0) goto L55
            boolean r12 = r4.moveToFirst()     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            if (r12 == 0) goto L55
            java.lang.String r12 = "updateEmailAccount"
            int r12 = r4.getColumnIndex(r12)     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            java.lang.String r12 = r4.getString(r12)     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            boolean r3 = java.lang.Boolean.parseBoolean(r12)     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            goto L5b
        L51:
            r12 = move-exception
            goto L7d
        L53:
            r12 = move-exception
            goto L67
        L55:
            java.lang.String r12 = "updateEnterpriseEmailAccount() : cannot get cursor from EmailProvider."
            android.util.Log.i(r0, r12)     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
        L5b:
            if (r4 == 0) goto L76
            boolean r12 = r4.isClosed()
            if (r12 != 0) goto L76
        L63:
            r4.close()
            goto L76
        L67:
            java.lang.String r13 = "updateEnterpriseEmailAccount() : Failed, Exception occurs. "
            android.util.Log.e(r0, r13, r12)     // Catch: java.lang.Throwable -> L51
            if (r4 == 0) goto L76
            boolean r12 = r4.isClosed()
            if (r12 != 0) goto L76
            goto L63
        L76:
            java.lang.String r12 = "updateEnterpriseEmailAccount() : ret = "
            com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(r12, r0, r3)
            return r3
        L7d:
            if (r4 == 0) goto L88
            boolean r13 = r4.isClosed()
            if (r13 != 0) goto L88
            r4.close()
        L88:
            throw r12
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.email.EmailProviderHelper.updateEnterpriseEmailAccount(android.content.Context,"
                    + " com.samsung.android.knox.ContextInfo,"
                    + " android.sec.enterprise.email.EnterpriseEmailAccount):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0062, code lost:

       if (r3.isClosed() == false) goto L19;
    */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0064, code lost:

       r3.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0074, code lost:

       if (r3.isClosed() == false) goto L19;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean updateEnterpriseExchangeAccount(
            android.content.Context r11,
            com.samsung.android.knox.ContextInfo r12,
            android.sec.enterprise.email.EnterpriseExchangeAccount r13) {
        /*
            java.lang.String r0 = "EmailProviderHelperService"
            long r1 = java.lang.System.currentTimeMillis()
            r3 = 0
            int r3 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r3 >= 0) goto Lf
            r3 = -1
            long r1 = r1 * r3
        Lf:
            java.util.Map r3 = com.android.server.enterprise.email.EmailProviderHelper.mAccountObjectMap
            java.lang.String r4 = "A"
            java.lang.String r4 = com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m(r1, r4)
            java.util.HashMap r3 = (java.util.HashMap) r3
            r3.put(r4, r13)
            r13 = 0
            r3 = 0
            java.lang.String r4 = "content://com.samsung.android.email.mdm.provider"
            android.net.Uri r7 = android.net.Uri.parse(r4)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            java.lang.String r9 = "updateEmailAccount"
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            java.lang.String r2 = "eas"
            java.lang.String[] r10 = new java.lang.String[]{r1, r2}     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            r8 = 0
            r5 = r11
            r6 = r12
            android.database.Cursor r3 = createEmailContentProviderCursor(r5, r6, r7, r8, r9, r10)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            if (r3 == 0) goto L56
            boolean r11 = r3.moveToFirst()     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            if (r11 == 0) goto L56
            java.lang.String r11 = "updateEmailAccount"
            int r11 = r3.getColumnIndex(r11)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            java.lang.String r11 = r3.getString(r11)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            boolean r13 = java.lang.Boolean.parseBoolean(r11)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            goto L5c
        L52:
            r11 = move-exception
            goto L7e
        L54:
            r11 = move-exception
            goto L68
        L56:
            java.lang.String r11 = "updateEnterpriseExchangeAccount() : cannot get cursor from EmailProvider."
            android.util.Log.i(r0, r11)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
        L5c:
            if (r3 == 0) goto L77
            boolean r11 = r3.isClosed()
            if (r11 != 0) goto L77
        L64:
            r3.close()
            goto L77
        L68:
            java.lang.String r12 = "updateEnterpriseExchangeAccount() : Failed, Exception occurs. "
            android.util.Log.e(r0, r12, r11)     // Catch: java.lang.Throwable -> L52
            if (r3 == 0) goto L77
            boolean r11 = r3.isClosed()
            if (r11 != 0) goto L77
            goto L64
        L77:
            java.lang.String r11 = "updateEnterpriseExchangeAccount() : ret = "
            com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(r11, r0, r13)
            return r13
        L7e:
            if (r3 == 0) goto L89
            boolean r12 = r3.isClosed()
            if (r12 != 0) goto L89
            r3.close()
        L89:
            throw r11
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.email.EmailProviderHelper.updateEnterpriseExchangeAccount(android.content.Context,"
                    + " com.samsung.android.knox.ContextInfo,"
                    + " android.sec.enterprise.email.EnterpriseExchangeAccount):boolean");
    }
}
