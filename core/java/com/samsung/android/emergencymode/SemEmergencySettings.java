package com.samsung.android.emergencymode;

import android.app.ActivityThread;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.p009os.SemSystemProperties;
import android.p009os.SystemProperties;
import android.telephony.PhoneNumberUtils;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.internal.telephony.TelephonyProperties;
import java.util.Arrays;

/* loaded from: classes5.dex */
public class SemEmergencySettings {
    private static final String TAG = "SemEmergencySettings";

    private SemEmergencySettings() {
    }

    public static void put(ContentResolver resolver, String pref, Object value) {
        if (resolver == null || pref == null || value == null) {
            return;
        }
        String selection = "pref='" + pref + "'";
        resolver.delete(SemEmergencyConstants.URI_PREFSETTINGS, selection, null);
        ContentValues values = new ContentValues();
        values.put(SemEmergencyConstants.PREF, pref);
        values.put("value", String.valueOf(value));
        resolver.insert(SemEmergencyConstants.URI_PREFSETTINGS, values);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x006a, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0067, code lost:
    
        if (r1 == null) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static String get(ContentResolver resolver, String pref) {
        if (resolver == null || pref == null) {
            return null;
        }
        String ret = null;
        Cursor cursor = null;
        try {
            try {
                String selection = "pref='" + pref + "'";
                cursor = resolver.query(SemEmergencyConstants.URI_PREFSETTINGS, null, selection, null, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    ret = cursor.getString(cursor.getColumnIndex("value"));
                    cursor.close();
                }
            } catch (Exception e) {
                Elog.m254d(TAG, "Exception " + e);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static int getInt(ContentResolver resolver, String pref, int defaultValue) {
        String ret = get(resolver, pref);
        try {
            return Integer.parseInt(ret);
        } catch (Exception e) {
            Elog.m254d(TAG, "Exception " + e);
            return defaultValue;
        }
    }

    public static boolean getBoolean(ContentResolver resolver, String pref, boolean defaultValue) {
        String ret = get(resolver, pref);
        if (ret == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(ret);
    }

    public static String getString(ContentResolver resolver, String pref, String defaultValue) {
        String ret = get(resolver, pref);
        if (ret == null) {
            return defaultValue;
        }
        return ret;
    }

    public static long getLong(ContentResolver resolver, String pref, long defaultValue) {
        String ret = get(resolver, pref);
        try {
            return Long.parseLong(ret);
        } catch (Exception e) {
            Elog.m254d(TAG, "Exception " + e);
            return defaultValue;
        }
    }

    public static double getDouble(ContentResolver resolver, String pref, double defaultValue) {
        String ret = get(resolver, pref);
        try {
            return Double.parseDouble(ret);
        } catch (Exception e) {
            Elog.m254d(TAG, "Exception " + e);
            return defaultValue;
        }
    }

    @Deprecated
    public static String getEmergencyNumber(ContentResolver resolver, String type) {
        Context context = ActivityThread.currentApplication().getApplicationContext();
        return getEmergencyNumber(context, resolver, type);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x003a, code lost:
    
        if (r11 != 1) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x012c, code lost:
    
        if ("China".equalsIgnoreCase(r0) != false) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:?, code lost:
    
        return "119";
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:?, code lost:
    
        return "911";
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00f4, code lost:
    
        if ("China".equalsIgnoreCase(r0) != false) goto L57;
     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getEmergencyNumber(Context context, ContentResolver resolver, String type) {
        String mcc;
        if (resolver == null || type == null || context == null) {
            return null;
        }
        String ret = null;
        Cursor cursor = null;
        TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
        int i = 0;
        boolean isReady = false;
        int mState = 1;
        String mccmnc = null;
        while (true) {
            try {
                try {
                    int mState2 = mTelephonyManager.getSimCount();
                    if (i >= mState2) {
                        break;
                    }
                    if (mccmnc != null) {
                        try {
                            if (!"".equals(mccmnc)) {
                            }
                        } catch (Exception e) {
                            e = e;
                            Elog.m254d(TAG, "Exception " + e);
                            if (cursor != null) {
                            }
                            if (0 == 0) {
                            }
                        } catch (Throwable th) {
                            th = th;
                            if (0 != 0) {
                                cursor.close();
                            }
                            if (0 == 0) {
                                Elog.m254d(TAG, "getEmergencyNumber not found emergency number!");
                                String countryCode = SystemProperties.get("ro.csc.country_code");
                                if ("China".equalsIgnoreCase(countryCode)) {
                                }
                            }
                            throw th;
                        }
                    }
                    mccmnc = mTelephonyManager.getNetworkOperator(getSubId(context, i));
                    mState = mTelephonyManager.getSimState(i);
                    if (mState == 5) {
                        isReady = true;
                    }
                    i++;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e2) {
                e = e2;
            } catch (Throwable th3) {
                th = th3;
            }
        }
        if (mccmnc == null || mccmnc.length() <= 3) {
            String mcc2 = checkForMCC();
            mcc = mcc2;
        } else {
            String mcc3 = mccmnc.substring(0, 3);
            mcc = mcc3;
        }
        Elog.m254d(TAG, "getEmergencyNumber requested Country : " + mcc + " sim ready = " + isReady);
        String selection = "mcc='" + mcc + "'";
        try {
            cursor = resolver.query(SemEmergencyConstants.URI_ECCLIST, null, selection, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                ret = cursor.getString(cursor.getColumnIndex(type));
            }
            if (cursor != null) {
                cursor.close();
            }
            if (ret != null) {
                return ret;
            }
            Elog.m254d(TAG, "getEmergencyNumber not found emergency number!");
            String countryCode2 = SystemProperties.get("ro.csc.country_code");
        } catch (Exception e3) {
            e = e3;
            Elog.m254d(TAG, "Exception " + e);
            if (cursor != null) {
                cursor.close();
            }
            if (0 == 0) {
                return null;
            }
            Elog.m254d(TAG, "getEmergencyNumber not found emergency number!");
            String countryCode3 = SystemProperties.get("ro.csc.country_code");
        }
    }

    private static String checkForMCC() {
        String[] arrIsReady;
        String mccmn = SemSystemProperties.get(TelephonyProperties.PROPERTY_ICC_OPERATOR_NUMERIC);
        String isReady = SemSystemProperties.get(TelephonyProperties.PROPERTY_SIM_STATE);
        Elog.m254d(TAG, "checkForMCC: gsm.sim.operator.numeric mccmn= " + mccmn + isReady);
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
            Elog.m254d(TAG, "checkForMCC: requested Country : mcc1[0] " + mcc1[0] + " sim ready = " + arrIsReady[0]);
            return mcc1[0];
        }
        if (size_readystate > 1 && arrIsReady[1].equals("READY")) {
            Elog.m254d(TAG, "checkForMCC: requested Country : mcc1[1] " + mcc1[1] + " sim ready = " + arrIsReady[1]);
            return mcc1[1];
        }
        if (simCount > 0 && mcc1[0] != null) {
            Elog.m254d(TAG, "checkForMCC: requested Country : mcc1[0] " + mcc1[0] + " sim ready = " + arrIsReadyValue);
            return mcc1[0];
        }
        if (simCount > 1 && mcc1[1] != null) {
            Elog.m254d(TAG, "checkForMCC: requested Country : mcc1[1] " + mcc1[1] + " sim ready = " + arrIsReadyValue);
            return mcc1[1];
        }
        Elog.m254d(TAG, "checkForMCC: requested Country : default cond. sim ready = " + arrIsReadyValue);
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
            Elog.m254d(TAG, "This is Emergency number");
            return number;
        }
        if (isReady && isPossibleNormalCall(context)) {
            Elog.m254d(TAG, "SIM Ready, not emergency number.");
            return number;
        }
        Elog.m254d(TAG, "SIM Ready = " + isReady + ", default emergency number.");
        return null;
    }

    private static boolean isPossibleNormalCall(Context context) {
        try {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
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
