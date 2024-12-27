package com.android.server.enterprise.vpn.knoxvpn.storage;

import android.content.ContentValues;
import android.content.Context;
import com.android.server.enterprise.storage.EdmStorageProvider;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KnoxVpnStorageProvider {
    public static KnoxVpnStorageProvider mDefaultProvider;
    public static EdmStorageProvider mEDM;
    public static final Object mSynObj = new Object();

    public static synchronized KnoxVpnStorageProvider getInstance(Context context) {
        KnoxVpnStorageProvider knoxVpnStorageProvider;
        synchronized (KnoxVpnStorageProvider.class) {
            if (mDefaultProvider == null) {
                KnoxVpnStorageProvider knoxVpnStorageProvider2 = new KnoxVpnStorageProvider();
                synchronized (mSynObj) {
                    try {
                        if (mEDM == null) {
                            mEDM = new EdmStorageProvider(context);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                mDefaultProvider = knoxVpnStorageProvider2;
            }
            knoxVpnStorageProvider = mDefaultProvider;
        }
        return knoxVpnStorageProvider;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0069, code lost:
    
        if (r4 == null) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0050, code lost:
    
        if (r4 == null) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0052, code lost:
    
        r4.close();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getProfileId() {
        /*
            java.lang.String r0 = "Exception = "
            com.android.server.enterprise.storage.EdmStorageProvider r1 = com.android.server.enterprise.vpn.knoxvpn.storage.KnoxVpnStorageProvider.mEDM
            java.lang.String r2 = "KnoxVpnStorageProvider"
            r3 = -1
            if (r1 == 0) goto L72
            com.android.server.enterprise.storage.EdmStorageHelper r1 = r1.mEdmDbHelper
            android.database.sqlite.SQLiteDatabase r4 = r1.getReadableDatabase()
            java.lang.String r1 = "profileCount"
            java.lang.String[] r6 = new java.lang.String[]{r1}
            r8 = 0
            r9 = 0
            java.lang.String r5 = "VpnAnalyticsTable"
            r7 = 0
            r10 = 0
            r11 = 0
            android.database.Cursor r4 = r4.query(r5, r6, r7, r8, r9, r10, r11)
            r5 = 0
            if (r4 == 0) goto L3d
            int r6 = r4.getCount()     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3b
            if (r6 > 0) goto L2b
            goto L3d
        L2b:
            int r1 = r4.getCount()     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3b
            if (r1 <= 0) goto L50
            r4.moveToFirst()     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3b
            int r3 = r4.getInt(r5)     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3b
            goto L50
        L39:
            r0 = move-exception
            goto L6c
        L3b:
            r1 = move-exception
            goto L56
        L3d:
            android.content.ContentValues r6 = new android.content.ContentValues     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3b
            r6.<init>()     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3b
            java.lang.Integer r7 = java.lang.Integer.valueOf(r5)     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3b
            r6.put(r1, r7)     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3b
            java.lang.String r1 = "VpnAnalyticsTable"
            r7 = 0
            putDataByFields(r1, r7, r7, r6)     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3b
            r3 = r5
        L50:
            if (r4 == 0) goto L72
        L52:
            r4.close()
            goto L72
        L56:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L39
            r5.<init>(r0)     // Catch: java.lang.Throwable -> L39
            java.lang.String r0 = android.util.Log.getStackTraceString(r1)     // Catch: java.lang.Throwable -> L39
            r5.append(r0)     // Catch: java.lang.Throwable -> L39
            java.lang.String r0 = r5.toString()     // Catch: java.lang.Throwable -> L39
            android.util.Log.e(r2, r0)     // Catch: java.lang.Throwable -> L39
            if (r4 == 0) goto L72
            goto L52
        L6c:
            if (r4 == 0) goto L71
            r4.close()
        L71:
            throw r0
        L72:
            java.lang.String r0 = "profile id : "
            com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0.m(r3, r0, r2)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.vpn.knoxvpn.storage.KnoxVpnStorageProvider.getProfileId():int");
    }

    public static void putDataByFields(String str, String[] strArr, String[] strArr2, ContentValues contentValues) {
        mEDM.putDataByFields(str, strArr, strArr2, contentValues);
    }
}
