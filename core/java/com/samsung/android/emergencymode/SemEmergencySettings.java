package com.samsung.android.emergencymode;

import android.app.ActivityThread;
import android.content.ContentResolver;
import android.content.Context;
import android.os.SemSystemProperties;
import android.telephony.PhoneNumberUtils;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.android.internal.telephony.TelephonyProperties;

import java.util.Arrays;

public class SemEmergencySettings {
    private static final String TAG = "SemEmergencySettings";

    private SemEmergencySettings() {}

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0068, code lost:

       return r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0065, code lost:

       if (r1 == null) goto L19;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String get(android.content.ContentResolver r9, java.lang.String r10) {
        /*
            if (r9 == 0) goto L6f
            if (r10 != 0) goto L5
            goto L6f
        L5:
            r0 = 0
            r1 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            r2.<init>()     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            java.lang.String r3 = "pref='"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            java.lang.StringBuilder r2 = r2.append(r10)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            java.lang.String r3 = "'"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            java.lang.String r6 = r2.toString()     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            android.net.Uri r4 = com.samsung.android.emergencymode.SemEmergencyConstants.URI_PREFSETTINGS     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            r7 = 0
            r8 = 0
            r5 = 0
            r3 = r9
            android.database.Cursor r2 = r3.query(r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            r1 = r2
            if (r1 == 0) goto L44
            int r2 = r1.getCount()     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            if (r2 <= 0) goto L44
            r1.moveToFirst()     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            java.lang.String r2 = "value"
            int r2 = r1.getColumnIndex(r2)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            java.lang.String r2 = r1.getString(r2)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            r0 = r2
            r1.close()     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
        L44:
            if (r1 == 0) goto L68
        L46:
            r1.close()
            goto L68
        L4a:
            r2 = move-exception
            goto L69
        L4c:
            r2 = move-exception
            java.lang.String r3 = "SemEmergencySettings"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4a
            r4.<init>()     // Catch: java.lang.Throwable -> L4a
            java.lang.String r5 = "Exception "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch: java.lang.Throwable -> L4a
            java.lang.StringBuilder r4 = r4.append(r2)     // Catch: java.lang.Throwable -> L4a
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L4a
            com.samsung.android.emergencymode.Elog.d(r3, r4)     // Catch: java.lang.Throwable -> L4a
            if (r1 == 0) goto L68
            goto L46
        L68:
            return r0
        L69:
            if (r1 == 0) goto L6e
            r1.close()
        L6e:
            throw r2
        L6f:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.emergencymode.SemEmergencySettings.get(android.content.ContentResolver,"
                    + " java.lang.String):java.lang.String");
    }

    @Deprecated
    public static String getEmergencyNumber(ContentResolver resolver, String type) {
        Context context = ActivityThread.currentApplication().getApplicationContext();
        return getEmergencyNumber(context, resolver, type);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0037, code lost:

       if (r11 != 1) goto L16;
    */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0127, code lost:

       if ("China".equalsIgnoreCase(r0) != false) goto L57;
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:?, code lost:

       return "119";
    */
    /* JADX WARN: Code restructure failed: missing block: B:34:?, code lost:

       return "911";
    */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00ef, code lost:

       if ("China".equalsIgnoreCase(r0) != false) goto L57;
    */
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getEmergencyNumber(
            android.content.Context r20,
            android.content.ContentResolver r21,
            java.lang.String r22) {
        /*
            Method dump skipped, instructions count: 335
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.emergencymode.SemEmergencySettings.getEmergencyNumber(android.content.Context,"
                    + " android.content.ContentResolver, java.lang.String):java.lang.String");
    }

    private static String checkForMCC() {
        String[] arrIsReady;
        String mccmn = SemSystemProperties.get(TelephonyProperties.PROPERTY_ICC_OPERATOR_NUMERIC);
        String isReady = SemSystemProperties.get(TelephonyProperties.PROPERTY_SIM_STATE);
        Elog.d(TAG, "checkForMCC: gsm.sim.operator.numeric mccmn= " + mccmn + isReady);
        if (mccmn == null || TextUtils.isEmpty(mccmn)) {
            return null;
        }
        String[] arrMcc = mccmn.split(",");
        if (isReady == null) {
            arrIsReady = null;
        } else {
            arrIsReady = isReady.split(",");
        }
        int simCount = arrMcc.length;
        String[] mcc1 = new String[2];
        for (int i = 0; i < simCount; i++) {
            if (arrMcc[i] != null && arrMcc[i].length() > 2) {
                mcc1[i] = arrMcc[i].substring(0, 3);
            } else {
                mcc1[i] = null;
            }
        }
        int size_readystate = arrIsReady.length;
        String arrIsReadyValue = Arrays.toString(arrIsReady);
        if (size_readystate > 0 && arrIsReady[0].equals("READY")) {
            Elog.d(
                    TAG,
                    "checkForMCC: requested Country : mcc1[0] "
                            + mcc1[0]
                            + " sim ready = "
                            + arrIsReady[0]);
            return mcc1[0];
        }
        if (size_readystate > 1 && arrIsReady[1].equals("READY")) {
            Elog.d(
                    TAG,
                    "checkForMCC: requested Country : mcc1[1] "
                            + mcc1[1]
                            + " sim ready = "
                            + arrIsReady[1]);
            return mcc1[1];
        }
        if (simCount > 0 && mcc1[0] != null) {
            Elog.d(
                    TAG,
                    "checkForMCC: requested Country : mcc1[0] "
                            + mcc1[0]
                            + " sim ready = "
                            + arrIsReadyValue);
            return mcc1[0];
        }
        if (simCount > 1 && mcc1[1] != null) {
            Elog.d(
                    TAG,
                    "checkForMCC: requested Country : mcc1[1] "
                            + mcc1[1]
                            + " sim ready = "
                            + arrIsReadyValue);
            return mcc1[1];
        }
        Elog.d(
                TAG,
                "checkForMCC: requested Country : default cond. sim ready = " + arrIsReadyValue);
        return null;
    }

    private static int getSubId(Context context, int slotId) {
        SubscriptionInfo info;
        SubscriptionManager sm = SubscriptionManager.from(context);
        if (sm != null && (info = sm.getActiveSubscriptionInfoForSimSlotIndex(slotId)) != null) {
            return info.getSubscriptionId();
        }
        return 0;
    }

    private static String makeEmergencyNumber(Context context, String number, boolean isReady) {
        if (PhoneNumberUtils.isEmergencyNumber(number)) {
            Elog.d(TAG, "This is Emergency number");
            return number;
        }
        if (isReady && isPossibleNormalCall(context)) {
            Elog.d(TAG, "SIM Ready, not emergency number.");
            return number;
        }
        Elog.d(TAG, "SIM Ready = " + isReady + ", default emergency number.");
        return null;
    }

    private static boolean isPossibleNormalCall(Context context) {
        try {
            try {
                TelephonyManager telephonyManager =
                        (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager.getServiceState().getState() != 0) {
                    return false;
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (Throwable th) {
            return false;
        }
    }
}
