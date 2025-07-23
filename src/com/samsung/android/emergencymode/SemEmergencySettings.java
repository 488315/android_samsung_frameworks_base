package com.samsung.android.emergencymode;

import android.app.ActivityThread;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.SemSystemProperties;
import android.telephony.PhoneNumberUtils;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.internal.telephony.TelephonyProperties;
import java.util.Arrays;

/* loaded from: classes6.dex */
public class SemEmergencySettings {
    private static final String TAG = "SemEmergencySettings";

    private SemEmergencySettings() {
    }

    private static String get(ContentResolver contentResolver, String str) {
        Throwable th;
        Exception exc;
        String str2;
        Cursor cursor = null;
        r2 = null;
        r2 = null;
        String str3 = null;
        Cursor cursor2 = null;
        if (contentResolver != null) {
            try {
                if (str != null) {
                    try {
                        Cursor query = contentResolver.query(SemEmergencyConstants.URI_PREFSETTINGS, null, "pref='" + str + "'", null, null);
                        if (query != null) {
                            try {
                                if (query.getCount() > 0) {
                                    query.moveToFirst();
                                    str3 = query.getString(query.getColumnIndex("value"));
                                    query.close();
                                }
                            } catch (Exception e) {
                                exc = e;
                                String str4 = str3;
                                cursor2 = query;
                                str2 = str4;
                                Elog.d(TAG, "Exception " + exc);
                                if (cursor2 != null) {
                                    cursor2.close();
                                }
                                return str2;
                            } catch (Throwable th2) {
                                th = th2;
                                cursor = query;
                                if (cursor != null) {
                                    cursor.close();
                                    throw th;
                                }
                                throw th;
                            }
                        }
                        if (query != null) {
                            query.close();
                        }
                        return str3;
                    } catch (Exception e2) {
                        exc = e2;
                        str2 = null;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }
        return null;
    }

    @Deprecated
    public static String getEmergencyNumber(ContentResolver contentResolver, String str) {
        return getEmergencyNumber(ActivityThread.currentApplication().getApplicationContext(), contentResolver, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x0101  */
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getEmergencyNumber(android.content.Context r17, android.content.ContentResolver r18, java.lang.String r19) {
        /*
            Method dump skipped, instructions count: 274
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.emergencymode.SemEmergencySettings.getEmergencyNumber(android.content.Context, android.content.ContentResolver, java.lang.String):java.lang.String");
    }

    private static String checkForMCC() {
        String str = SemSystemProperties.get(TelephonyProperties.PROPERTY_ICC_OPERATOR_NUMERIC);
        String str2 = SemSystemProperties.get(TelephonyProperties.PROPERTY_SIM_STATE);
        Elog.d(TAG, "checkForMCC: gsm.sim.operator.numeric mccmn= " + str + str2);
        if (str != null && !TextUtils.isEmpty(str)) {
            String[] split = str.split(",");
            String[] split2 = str2 != null ? str2.split(",") : null;
            int length = split.length;
            String[] strArr = new String[2];
            for (int i = 0; i < length; i++) {
                String str3 = split[i];
                if (str3 != null && str3.length() > 2) {
                    strArr[i] = split[i].substring(0, 3);
                } else {
                    strArr[i] = null;
                }
            }
            int length2 = split2.length;
            String arrays = Arrays.toString(split2);
            if (length2 > 0 && split2[0].equals("READY")) {
                Elog.d(TAG, "checkForMCC: requested Country : mcc1[0] " + strArr[0] + " sim ready = " + split2[0]);
                return strArr[0];
            }
            if (length2 > 1 && split2[1].equals("READY")) {
                Elog.d(TAG, "checkForMCC: requested Country : mcc1[1] " + strArr[1] + " sim ready = " + split2[1]);
                return strArr[1];
            }
            if (length > 0 && strArr[0] != null) {
                Elog.d(TAG, "checkForMCC: requested Country : mcc1[0] " + strArr[0] + " sim ready = " + arrays);
                return strArr[0];
            }
            if (length > 1 && strArr[1] != null) {
                Elog.d(TAG, "checkForMCC: requested Country : mcc1[1] " + strArr[1] + " sim ready = " + arrays);
                return strArr[1];
            }
            Elog.d(TAG, "checkForMCC: requested Country : default cond. sim ready = " + arrays);
        }
        return null;
    }

    private static int getSubId(Context context, int i) {
        SubscriptionInfo activeSubscriptionInfoForSimSlotIndex;
        SubscriptionManager from = SubscriptionManager.from(context);
        if (from == null || (activeSubscriptionInfoForSimSlotIndex = from.getActiveSubscriptionInfoForSimSlotIndex(i)) == null) {
            return 0;
        }
        return activeSubscriptionInfoForSimSlotIndex.getSubscriptionId();
    }

    private static String makeEmergencyNumber(Context context, String str, boolean z) {
        if (PhoneNumberUtils.isEmergencyNumber(str)) {
            Elog.d(TAG, "This is Emergency number");
            return str;
        }
        if (z && isPossibleNormalCall(context)) {
            Elog.d(TAG, "SIM Ready, not emergency number.");
            return str;
        }
        Elog.d(TAG, "SIM Ready = " + z + ", default emergency number.");
        return null;
    }

    private static boolean isPossibleNormalCall(Context context) {
        try {
            try {
                if (((TelephonyManager) context.getSystemService("phone")).getServiceState().getState() == 0) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        } catch (Throwable unused) {
            return false;
        }
    }
}
