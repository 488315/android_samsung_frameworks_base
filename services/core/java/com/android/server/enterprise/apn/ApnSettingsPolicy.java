package com.android.server.enterprise.apn;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemProperties;
import android.provider.Settings;
import android.provider.Telephony;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseServiceCallback;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.apn.IApnSettingsPolicy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ApnSettingsPolicy extends IApnSettingsPolicy.Stub
        implements EnterpriseServiceCallback {
    public Context mContext;
    public EnterpriseDeviceManager mEDM;
    public int mMDMConfigVersion;
    public HashMap mPendingGetApnList;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Carriers {
        public static final Uri CONTENT_URI = Uri.parse("content://telephony/carriers");
        public static final Uri PREFERAPN_URI = Uri.parse("content://telephony/carriers/preferapn");
    }

    public static String getColumnValue(String str, Cursor cursor) {
        try {
            String string = cursor.getString(cursor.getColumnIndexOrThrow(str));
            return string == null ? "" : string;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getTelephonyProperty(int i, String str) {
        String str2 = SystemProperties.get("ril.ICC_TYPE0");
        String str3 = SystemProperties.get("ril.ICC_TYPE1");
        if (str.equals("ril.ICC_TYPE0")) {
            if (str2 == null || str2.length() <= 0) {
                str2 = "0";
            }
        } else if (str.equals("ril.ICC_TYPE1")) {
            if (str3 == null || str3.length() <= 0) {
                str3 = "0";
            }
            str2 = str3;
        } else {
            str2 = null;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = TelephonyManager.getTelephonyProperty(i, str, "0");
        }
        DualAppManagerService$$ExternalSyntheticOutline0.m(
                "getTelephonyProperty : ", str2, "ApnSettingsPolicyService");
        return str2;
    }

    public static void setColumnValue(
            boolean z, ContentValues contentValues, String str, String str2) {
        if (z) {
            contentValues.put(str, str2 == null ? "" : str2.trim());
        } else if (str2 != null) {
            contentValues.put(str, str2.trim());
        }
        Log.w("ApnSettingsPolicyService", "setColumnValue: key=" + str + " value=" + str2);
    }

    public static String validateString(String str) {
        return str == null ? str : str.trim();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0277 A[Catch: all -> 0x0203, Exception -> 0x0206, TryCatch #3 {Exception -> 0x0206, blocks: (B:119:0x01de, B:121:0x01ef, B:123:0x01f8, B:124:0x020d, B:126:0x0277, B:127:0x027e, B:134:0x02a2, B:135:0x02be, B:138:0x02ca, B:140:0x02d2, B:142:0x02da, B:144:0x0304, B:145:0x0315, B:147:0x0326, B:148:0x0336, B:151:0x034e, B:153:0x035c, B:156:0x0368, B:158:0x037d, B:159:0x0389, B:167:0x02b4), top: B:118:0x01de, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0295  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0304 A[Catch: all -> 0x0203, Exception -> 0x0206, TryCatch #3 {Exception -> 0x0206, blocks: (B:119:0x01de, B:121:0x01ef, B:123:0x01f8, B:124:0x020d, B:126:0x0277, B:127:0x027e, B:134:0x02a2, B:135:0x02be, B:138:0x02ca, B:140:0x02d2, B:142:0x02da, B:144:0x0304, B:145:0x0315, B:147:0x0326, B:148:0x0336, B:151:0x034e, B:153:0x035c, B:156:0x0368, B:158:0x037d, B:159:0x0389, B:167:0x02b4), top: B:118:0x01de, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0326 A[Catch: all -> 0x0203, Exception -> 0x0206, TryCatch #3 {Exception -> 0x0206, blocks: (B:119:0x01de, B:121:0x01ef, B:123:0x01f8, B:124:0x020d, B:126:0x0277, B:127:0x027e, B:134:0x02a2, B:135:0x02be, B:138:0x02ca, B:140:0x02d2, B:142:0x02da, B:144:0x0304, B:145:0x0315, B:147:0x0326, B:148:0x0336, B:151:0x034e, B:153:0x035c, B:156:0x0368, B:158:0x037d, B:159:0x0389, B:167:0x02b4), top: B:118:0x01de, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:150:0x034c  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0389 A[Catch: all -> 0x0203, Exception -> 0x0206, TRY_LEAVE, TryCatch #3 {Exception -> 0x0206, blocks: (B:119:0x01de, B:121:0x01ef, B:123:0x01f8, B:124:0x020d, B:126:0x0277, B:127:0x027e, B:134:0x02a2, B:135:0x02be, B:138:0x02ca, B:140:0x02d2, B:142:0x02da, B:144:0x0304, B:145:0x0315, B:147:0x0326, B:148:0x0336, B:151:0x034e, B:153:0x035c, B:156:0x0368, B:158:0x037d, B:159:0x0389, B:167:0x02b4), top: B:118:0x01de, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:161:0x02aa  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x027c  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x03c2 A[Catch: all -> 0x0077, Exception -> 0x01a0, TryCatch #0 {Exception -> 0x01a0, blocks: (B:56:0x0187, B:58:0x0193, B:60:0x019b, B:70:0x03a9, B:71:0x03bc, B:73:0x03c2, B:74:0x03c9, B:76:0x03cf, B:78:0x03d8, B:81:0x03e7, B:86:0x03fa, B:88:0x0404, B:96:0x0411, B:98:0x03f0, B:99:0x03de, B:101:0x01b4, B:104:0x01bc, B:106:0x01c2, B:108:0x01c8), top: B:55:0x0187, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x03cf A[Catch: all -> 0x0077, Exception -> 0x01a0, TryCatch #0 {Exception -> 0x01a0, blocks: (B:56:0x0187, B:58:0x0193, B:60:0x019b, B:70:0x03a9, B:71:0x03bc, B:73:0x03c2, B:74:0x03c9, B:76:0x03cf, B:78:0x03d8, B:81:0x03e7, B:86:0x03fa, B:88:0x0404, B:96:0x0411, B:98:0x03f0, B:99:0x03de, B:101:0x01b4, B:104:0x01bc, B:106:0x01c2, B:108:0x01c8), top: B:55:0x0187, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x03e7 A[Catch: all -> 0x0077, Exception -> 0x01a0, TryCatch #0 {Exception -> 0x01a0, blocks: (B:56:0x0187, B:58:0x0193, B:60:0x019b, B:70:0x03a9, B:71:0x03bc, B:73:0x03c2, B:74:0x03c9, B:76:0x03cf, B:78:0x03d8, B:81:0x03e7, B:86:0x03fa, B:88:0x0404, B:96:0x0411, B:98:0x03f0, B:99:0x03de, B:101:0x01b4, B:104:0x01bc, B:106:0x01c2, B:108:0x01c8), top: B:55:0x0187, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x03fa A[Catch: all -> 0x0077, Exception -> 0x01a0, TryCatch #0 {Exception -> 0x01a0, blocks: (B:56:0x0187, B:58:0x0193, B:60:0x019b, B:70:0x03a9, B:71:0x03bc, B:73:0x03c2, B:74:0x03c9, B:76:0x03cf, B:78:0x03d8, B:81:0x03e7, B:86:0x03fa, B:88:0x0404, B:96:0x0411, B:98:0x03f0, B:99:0x03de, B:101:0x01b4, B:104:0x01bc, B:106:0x01c2, B:108:0x01c8), top: B:55:0x0187, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0404 A[Catch: all -> 0x0077, Exception -> 0x01a0, TryCatch #0 {Exception -> 0x01a0, blocks: (B:56:0x0187, B:58:0x0193, B:60:0x019b, B:70:0x03a9, B:71:0x03bc, B:73:0x03c2, B:74:0x03c9, B:76:0x03cf, B:78:0x03d8, B:81:0x03e7, B:86:0x03fa, B:88:0x0404, B:96:0x0411, B:98:0x03f0, B:99:0x03de, B:101:0x01b4, B:104:0x01bc, B:106:0x01c2, B:108:0x01c8), top: B:55:0x0187, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x040e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized long addUpdateApn(
            com.samsung.android.knox.ContextInfo r30,
            boolean r31,
            com.samsung.android.knox.net.apn.ApnSettings r32) {
        /*
            Method dump skipped, instructions count: 1156
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.apn.ApnSettingsPolicy.addUpdateApn(com.samsung.android.knox.ContextInfo,"
                    + " boolean, com.samsung.android.knox.net.apn.ApnSettings):long");
    }

    public final synchronized boolean deleteApn(ContextInfo contextInfo, long j) {
        enforceOwnerOnlyAndApnPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (1 > j) {
            Log.w("ApnSettingsPolicyService", "deleteAp : apId is invalid");
            return false;
        }
        int i = -1;
        try {
            try {
                i =
                        this.mContext
                                .getContentResolver()
                                .delete(
                                        ContentUris.withAppendedId(Carriers.CONTENT_URI, j),
                                        null,
                                        null);
                Log.w(
                        "ApnSettingsPolicyService",
                        "deleteAp : deleted rowCount from ApTable : " + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return i > 0;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void enforceOwnerOnlyAndApnPermission(ContextInfo contextInfo) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        this.mEDM.enforceOwnerOnlyAndActiveAdminPermission(
                contextInfo,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_APN")));
    }

    public final List getApnList(ContextInfo contextInfo, int i) {
        enforceOwnerOnlyAndApnPermission(contextInfo);
        ArrayList arrayList = new ArrayList();
        if (this.mPendingGetApnList.containsKey(Integer.valueOf(i))) {
            arrayList.addAll((Collection) this.mPendingGetApnList.get(Integer.valueOf(i)));
        } else {
            Log.d("ApnSettingsPolicyService", "retrieveApnListFromDatabase");
            ArrayList arrayList2 = new ArrayList();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Cursor cursor = null;
            try {
                String[] strArr = {
                    KnoxCustomManagerService.ID,
                    "name",
                    "mcc",
                    "mnc",
                    "apn",
                    "user",
                    "server",
                    "password",
                    "proxy",
                    "port",
                    "mmsproxy",
                    "mmsport",
                    "mmsc",
                    "authtype",
                    "type",
                    "protocol",
                    "roaming_protocol",
                    "mvno_type",
                    "mvno_match_data"
                };
                cursor =
                        this.mContext
                                .getContentResolver()
                                .query(
                                        getApnListUri(getFirstSlotIndex()),
                                        strArr,
                                        getWhereClause(),
                                        null,
                                        null);
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        ApnSettings apnSettings = new ApnSettings();
                        apnSettings.setId(
                                Long.parseLong(
                                        getColumnValue(KnoxCustomManagerService.ID, cursor)));
                        apnSettings.setName(getColumnValue("name", cursor));
                        apnSettings.setMcc(getColumnValue("mcc", cursor));
                        apnSettings.setMnc(getColumnValue("mnc", cursor));
                        apnSettings.setApn(getColumnValue("apn", cursor));
                        apnSettings.setUser(getColumnValue("user", cursor));
                        apnSettings.setServer(getColumnValue("server", cursor));
                        apnSettings.setPassword(getColumnValue("password", cursor));
                        apnSettings.setProxy(getColumnValue("proxy", cursor));
                        String columnValue = getColumnValue("port", cursor);
                        if (!columnValue.isEmpty()) {
                            apnSettings.setPort(Integer.parseInt(columnValue));
                        }
                        apnSettings.setMmsProxy(getColumnValue("mmsproxy", cursor));
                        apnSettings.setMmsPort(getColumnValue("mmsport", cursor));
                        apnSettings.setMmsc(getColumnValue("mmsc", cursor));
                        String columnValue2 = getColumnValue("authtype", cursor);
                        if (!columnValue2.isEmpty()) {
                            apnSettings.setAuthType(Integer.parseInt(columnValue2));
                        }
                        apnSettings.setType(getColumnValue("type", cursor));
                        if (this.mMDMConfigVersion >= 17) {
                            apnSettings.protocol = getColumnValue("protocol", cursor);
                            apnSettings.roamingProtocol =
                                    getColumnValue("roaming_protocol", cursor);
                        }
                        String columnValue3 = getColumnValue("mvno_type", cursor);
                        if (!columnValue3.isEmpty()) {
                            apnSettings.mvno_type = columnValue3;
                        }
                        String columnValue4 = getColumnValue("mvno_match_data", cursor);
                        if (!columnValue4.isEmpty()) {
                            apnSettings.mvno_value = columnValue4;
                        }
                        arrayList2.add(apnSettings);
                    } while (cursor.moveToNext());
                }
                if (cursor != null) {
                    cursor.close();
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                arrayList.addAll(arrayList2);
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        if (arrayList.size()
                >= com.samsung.android.knox.net.apn.ApnSettingsPolicy.MAXIMUM_APNS_OVER_IPC) {
            this.mPendingGetApnList.put(
                    Integer.valueOf(i),
                    arrayList.subList(
                            com.samsung.android.knox.net.apn.ApnSettingsPolicy
                                    .MAXIMUM_APNS_OVER_IPC,
                            arrayList.size()));
            return arrayList.subList(
                    0, com.samsung.android.knox.net.apn.ApnSettingsPolicy.MAXIMUM_APNS_OVER_IPC);
        }
        this.mPendingGetApnList.remove(Integer.valueOf(i));
        return arrayList.subList(0, arrayList.size());
    }

    public final Uri getApnListUri(int i) {
        Log.d("ApnSettingsPolicyService", "getApnListUri");
        TelephonyManager telephonyManager = TelephonyManager.getDefault();
        if (telephonyManager.getSimState() == 0 || telephonyManager.getSimState() == 1) {
            Log.w("ApnSettingsPolicyService", "No SIM ");
            return Carriers.CONTENT_URI;
        }
        SubscriptionInfo activeSubscriptionInfo =
                SubscriptionManager.from(this.mContext)
                        .getActiveSubscriptionInfo(
                                (i == -1 || SubscriptionManager.getSubId(i) == null)
                                        ? -1
                                        : SubscriptionManager.getSubId(i)[0]);
        return Uri.withAppendedPath(
                Telephony.Carriers.SIM_APN_URI,
                String.valueOf(
                        activeSubscriptionInfo != null
                                ? activeSubscriptionInfo.getSubscriptionId()
                                : -1));
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x012a, code lost:

       if (r10 != null) goto L33;
    */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x012c, code lost:

       r10.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x012f, code lost:

       android.os.Binder.restoreCallingIdentity(r1);
    */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x013d, code lost:

       return null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x013a, code lost:

       if (r10 == null) goto L34;
    */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0140  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.knox.net.apn.ApnSettings getApnSettings(
            com.samsung.android.knox.ContextInfo r10, long r11) {
        /*
            Method dump skipped, instructions count: 327
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.apn.ApnSettingsPolicy.getApnSettings(com.samsung.android.knox.ContextInfo,"
                    + " long):com.samsung.android.knox.net.apn.ApnSettings");
    }

    public final int getFirstSlotIndex() {
        int i =
                (!(Settings.Global.getInt(this.mContext.getContentResolver(), "phone1_on", 1) == 1)
                                || "0".equals(getTelephonyProperty(0, "ril.ICC_TYPE0")))
                        ? (!(Settings.Global.getInt(
                                                        this.mContext.getContentResolver(),
                                                        "phone2_on",
                                                        1)
                                                == 1)
                                        || "0".equals(getTelephonyProperty(1, "ril.ICC_TYPE1")))
                                ? -1
                                : 1
                        : 0;
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                i, "getFirstSlotIndex : ", "ApnSettingsPolicyService");
        return i;
    }

    /* JADX WARN: Not initialized variable reg: 5, insn: 0x0063: MOVE (r4 I:??[OBJECT, ARRAY]) = (r5 I:??[OBJECT, ARRAY]), block:B:28:0x0063 */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0092 A[Catch: all -> 0x006d, TRY_ENTER, TryCatch #4 {all -> 0x006d, blocks: (B:4:0x0007, B:10:0x0069, B:11:0x006f, B:30:0x0092, B:31:0x0095, B:32:0x0098, B:25:0x008a), top: B:3:0x0007 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized com.samsung.android.knox.net.apn.ApnSettings getPreferredApn(
            com.samsung.android.knox.ContextInfo r13) {
        /*
            r12 = this;
            java.lang.String r0 = "getPreferredApn Ex: "
            java.lang.String r1 = "getPreferredApn():"
            monitor-enter(r12)
            r12.enforceOwnerOnlyAndApnPermission(r13)     // Catch: java.lang.Throwable -> L6d
            java.lang.String r2 = "ApnSettingsPolicyService"
            java.lang.String r3 = "getPreferredApn():"
            android.util.Log.d(r2, r3)     // Catch: java.lang.Throwable -> L6d
            long r2 = android.os.Binder.clearCallingIdentity()     // Catch: java.lang.Throwable -> L6d
            r4 = 0
            android.content.Context r5 = r12.mContext     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            android.content.ContentResolver r6 = r5.getContentResolver()     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            android.net.Uri r7 = com.android.server.enterprise.apn.ApnSettingsPolicy.Carriers.PREFERAPN_URI     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            java.lang.String r5 = "_id"
            java.lang.String r8 = "name"
            java.lang.String r9 = "apn"
            java.lang.String[] r8 = new java.lang.String[]{r5, r8, r9}     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            java.lang.String r11 = "name ASC"
            r9 = 0
            r10 = 0
            android.database.Cursor r5 = r6.query(r7, r8, r9, r10, r11)     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            if (r5 == 0) goto L67
            boolean r6 = r5.moveToFirst()     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L65
            if (r6 == 0) goto L67
            java.lang.String r6 = "_id"
            int r6 = r5.getColumnIndexOrThrow(r6)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L65
            long r6 = r5.getLong(r6)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L65
            java.lang.String r8 = "ApnSettingsPolicyService"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L65
            r9.<init>(r1)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L65
            r9.append(r6)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L65
            java.lang.String r1 = r9.toString()     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L65
            android.util.Log.w(r8, r1)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L65
            r8 = 0
            int r1 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r1 <= 0) goto L67
            com.samsung.android.knox.net.apn.ApnSettings r4 = r12.getApnSettings(r13, r6)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L65
            goto L67
        L62:
            r13 = move-exception
            r4 = r5
            goto L90
        L65:
            r13 = move-exception
            goto L77
        L67:
            if (r5 == 0) goto L6f
            r5.close()     // Catch: java.lang.Throwable -> L6d
            goto L6f
        L6d:
            r13 = move-exception
            goto L99
        L6f:
            android.os.Binder.restoreCallingIdentity(r2)     // Catch: java.lang.Throwable -> L6d
            goto L8e
        L73:
            r13 = move-exception
            goto L90
        L75:
            r13 = move-exception
            r5 = r4
        L77:
            java.lang.String r1 = "ApnSettingsPolicyService"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L62
            r6.<init>(r0)     // Catch: java.lang.Throwable -> L62
            r6.append(r13)     // Catch: java.lang.Throwable -> L62
            java.lang.String r13 = r6.toString()     // Catch: java.lang.Throwable -> L62
            android.util.Log.w(r1, r13)     // Catch: java.lang.Throwable -> L62
            if (r5 == 0) goto L6f
            r5.close()     // Catch: java.lang.Throwable -> L6d
            goto L6f
        L8e:
            monitor-exit(r12)
            return r4
        L90:
            if (r4 == 0) goto L95
            r4.close()     // Catch: java.lang.Throwable -> L6d
        L95:
            android.os.Binder.restoreCallingIdentity(r2)     // Catch: java.lang.Throwable -> L6d
            throw r13     // Catch: java.lang.Throwable -> L6d
        L99:
            monitor-exit(r12)
            throw r13
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.apn.ApnSettingsPolicy.getPreferredApn(com.samsung.android.knox.ContextInfo):com.samsung.android.knox.net.apn.ApnSettings");
    }

    public final String getWhereClause() {
        Log.d("ApnSettingsPolicyService", "getWhereClause");
        StringBuilder sb = new StringBuilder("NOT (type='ia' AND (apn=\"\" OR apn IS NULL))");
        int slotIndex =
                SubscriptionManager.getSlotIndex(
                        SubscriptionManager.getDefaultDataSubscriptionId());
        boolean z = false;
        boolean z2 =
                Settings.Global.getInt(this.mContext.getContentResolver(), "phone1_on", 1) == 1
                        && !"0".equals(getTelephonyProperty(0, "ril.ICC_TYPE0"));
        int phoneCount = TelephonyManager.getDefault().getPhoneCount();
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                phoneCount, "simSlotCnt : ", "ApnSettingsPolicyService");
        if (phoneCount > 1) {
            String telephonyProperty = getTelephonyProperty(1, "ril.ICC_TYPE1");
            if (Settings.System.getInt(this.mContext.getContentResolver(), "phone2_on", 1) == 1
                    && !"0".equals(telephonyProperty)) {
                z = true;
            }
            if (z2 && slotIndex == 0) {
                sb.append(" AND current = 1 ");
            } else if (z && slotIndex == 1) {
                sb.append(" AND current1 = 1 ");
            }
        } else if (z2 && slotIndex == 0) {
            sb.append(" AND current = 1");
        }
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x00f7, code lost:

       if (r6.getString(2).equals("mms") != false) goto L35;
    */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00fe, code lost:

       if (r6 == null) goto L44;
    */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0100, code lost:

       r6.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0107, code lost:

       if (r6 == null) goto L44;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isValidApnId(long r18) {
        /*
            Method dump skipped, instructions count: 291
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.apn.ApnSettingsPolicy.isValidApnId(long):boolean");
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {}

    public final synchronized boolean setPreferredApn(ContextInfo contextInfo, long j) {
        boolean z;
        enforceOwnerOnlyAndApnPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (0 != j && 0 > j) {
                    Log.w("ApnSettingsPolicyService", "setPreferedAp() : invalid ap Id " + j);
                } else if (isValidApnId(j)) {
                    ContentValues contentValues = new ContentValues();
                    if (0 == j) {
                        j = -1;
                    }
                    contentValues.put("apn_id", Long.valueOf(j));
                    int update =
                            this.mContext
                                    .getContentResolver()
                                    .update(Carriers.PREFERAPN_URI, contentValues, null, null);
                    z = update > 0;
                    Log.w("ApnSettingsPolicyService", "setPreferedAp() row count : " + update);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        return z;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {}
}
