package com.samsung.android.emergencymode;

import android.app.ActivityThread;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
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

    private static String get(ContentResolver contentResolver, String str) throws Throwable {
        Throwable th;
        Exception exc;
        String str2;
        Cursor cursor = null;
        string = null;
        string = null;
        String string = null;
        cursor = null;
        if (contentResolver != null) {
            try {
                if (str != null) {
                    try {
                        Cursor cursorQuery = contentResolver.query(SemEmergencyConstants.URI_PREFSETTINGS, null, "pref='" + str + "'", null, null);
                        if (cursorQuery != null) {
                            try {
                                if (cursorQuery.getCount() > 0) {
                                    cursorQuery.moveToFirst();
                                    string = cursorQuery.getString(cursorQuery.getColumnIndex("value"));
                                    cursorQuery.close();
                                }
                            } catch (Exception e) {
                                exc = e;
                                String str3 = string;
                                cursor = cursorQuery;
                                str2 = str3;
                                Elog.d(TAG, "Exception " + exc);
                                if (cursor != null) {
                                    cursor.close();
                                }
                                return str2;
                            } catch (Throwable th2) {
                                th = th2;
                                cursor = cursorQuery;
                                if (cursor != null) {
                                    cursor.close();
                                    throw th;
                                }
                                throw th;
                            }
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return string;
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
    @Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getEmergencyNumber(Context context, ContentResolver contentResolver, String str) throws Throwable {
        Cursor cursorQuery;
        String strCheckForMCC;
        if (contentResolver == null || str == null || context == null) {
            return null;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        boolean z = false;
        int simState = 1;
        String networkOperator = null;
        String string = null;
        for (int i = 0; i < telephonyManager.getSimCount(); i++) {
            try {
                if (networkOperator == null || "".equals(networkOperator) || simState == 1) {
                    networkOperator = telephonyManager.getNetworkOperator(getSubId(context, i));
                }
                simState = telephonyManager.getSimState(i);
                if (simState == 5) {
                    z = true;
                }
            } catch (Exception e) {
                e = e;
                cursorQuery = null;
            } catch (Throwable th) {
                th = th;
                cursorQuery = null;
                if (cursorQuery != null) {
                }
                Elog.d(TAG, "getEmergencyNumber not found emergency number!");
                "China".equalsIgnoreCase(SystemProperties.get("ro.csc.country_code"));
                throw th;
            }
        }
        if (networkOperator != null && networkOperator.length() > 3) {
            strCheckForMCC = networkOperator.substring(0, 3);
        } else {
            strCheckForMCC = checkForMCC();
        }
        Elog.d(TAG, "getEmergencyNumber requested Country : " + strCheckForMCC + " sim ready = " + z);
        StringBuilder sb = new StringBuilder();
        sb.append("mcc='");
        sb.append(strCheckForMCC);
        sb.append("'");
        cursorQuery = contentResolver.query(SemEmergencyConstants.URI_ECCLIST, null, sb.toString(), null, null);
        if (cursorQuery != null) {
            try {
                try {
                    if (cursorQuery.getCount() > 0) {
                        cursorQuery.moveToFirst();
                        string = cursorQuery.getString(cursorQuery.getColumnIndex(str));
                    }
                } catch (Exception e2) {
                    e = e2;
                    Elog.d(TAG, "Exception " + e);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    Elog.d(TAG, "getEmergencyNumber not found emergency number!");
                    return "China".equalsIgnoreCase(SystemProperties.get("ro.csc.country_code")) ? "119" : "911";
                }
            } catch (Throwable th2) {
                th = th2;
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                Elog.d(TAG, "getEmergencyNumber not found emergency number!");
                "China".equalsIgnoreCase(SystemProperties.get("ro.csc.country_code"));
                throw th;
            }
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        if (string != null) {
            return string;
        }
        Elog.d(TAG, "getEmergencyNumber not found emergency number!");
        return "China".equalsIgnoreCase(SystemProperties.get("ro.csc.country_code")) ? "119" : "911";
    }

    private static String checkForMCC() {
        String str = SemSystemProperties.get(TelephonyProperties.PROPERTY_ICC_OPERATOR_NUMERIC);
        String str2 = SemSystemProperties.get(TelephonyProperties.PROPERTY_SIM_STATE);
        Elog.d(TAG, "checkForMCC: gsm.sim.operator.numeric mccmn= " + str + str2);
        if (str != null && !TextUtils.isEmpty(str)) {
            String[] strArrSplit = str.split(",");
            String[] strArrSplit2 = str2 != null ? str2.split(",") : null;
            int length = strArrSplit.length;
            String[] strArr = new String[2];
            for (int i = 0; i < length; i++) {
                String str3 = strArrSplit[i];
                if (str3 != null && str3.length() > 2) {
                    strArr[i] = strArrSplit[i].substring(0, 3);
                } else {
                    strArr[i] = null;
                }
            }
            int length2 = strArrSplit2.length;
            String string = Arrays.toString(strArrSplit2);
            if (length2 > 0 && strArrSplit2[0].equals("READY")) {
                Elog.d(TAG, "checkForMCC: requested Country : mcc1[0] " + strArr[0] + " sim ready = " + strArrSplit2[0]);
                return strArr[0];
            }
            if (length2 > 1 && strArrSplit2[1].equals("READY")) {
                Elog.d(TAG, "checkForMCC: requested Country : mcc1[1] " + strArr[1] + " sim ready = " + strArrSplit2[1]);
                return strArr[1];
            }
            if (length > 0 && strArr[0] != null) {
                Elog.d(TAG, "checkForMCC: requested Country : mcc1[0] " + strArr[0] + " sim ready = " + string);
                return strArr[0];
            }
            if (length > 1 && strArr[1] != null) {
                Elog.d(TAG, "checkForMCC: requested Country : mcc1[1] " + strArr[1] + " sim ready = " + string);
                return strArr[1];
            }
            Elog.d(TAG, "checkForMCC: requested Country : default cond. sim ready = " + string);
        }
        return null;
    }

    private static int getSubId(Context context, int i) {
        SubscriptionInfo activeSubscriptionInfoForSimSlotIndex;
        SubscriptionManager subscriptionManagerFrom = SubscriptionManager.from(context);
        if (subscriptionManagerFrom == null || (activeSubscriptionInfoForSimSlotIndex = subscriptionManagerFrom.getActiveSubscriptionInfoForSimSlotIndex(i)) == null) {
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
