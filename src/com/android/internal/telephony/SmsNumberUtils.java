package com.android.internal.telephony;

import android.app.blob.XmlTags;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.PersistableBundle;
import android.os.SystemProperties;
import android.telephony.CarrierConfigManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.android.internal.telephony.HbpcdLookup;
import com.android.internal.telephony.util.TelephonyUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class SmsNumberUtils {
    private static int[] ALL_COUNTRY_CODES = null;
    private static final int CDMA_HOME_NETWORK = 1;
    private static final int CDMA_ROAMING_NETWORK = 2;
    private static final boolean DBG;
    private static final int GSM_UMTS_NETWORK = 0;
    private static HashMap<String, ArrayList<String>> IDDS_MAPS = null;
    private static int MAX_COUNTRY_CODES_LENGTH = 0;
    private static final int MIN_COUNTRY_AREA_LOCAL_LENGTH = 10;
    private static final int NANP_CC = 1;
    private static final String NANP_IDD = "011";
    private static final int NANP_LONG_LENGTH = 11;
    private static final int NANP_MEDIUM_LENGTH = 10;
    private static final String NANP_NDD = "1";
    private static final int NANP_SHORT_LENGTH = 7;
    private static final int NP_CC_AREA_LOCAL = 104;
    private static final int NP_HOMEIDD_CC_AREA_LOCAL = 101;
    private static final int NP_INTERNATIONAL_BEGIN = 100;
    private static final int NP_LOCALIDD_CC_AREA_LOCAL = 103;
    private static final int NP_NANP_AREA_LOCAL = 2;
    private static final int NP_NANP_BEGIN = 1;
    private static final int NP_NANP_LOCAL = 1;
    private static final int NP_NANP_LOCALIDD_CC_AREA_LOCAL = 5;
    private static final int NP_NANP_NBPCD_CC_AREA_LOCAL = 4;
    private static final int NP_NANP_NBPCD_HOMEIDD_CC_AREA_LOCAL = 6;
    private static final int NP_NANP_NDD_AREA_LOCAL = 3;
    private static final int NP_NBPCD_CC_AREA_LOCAL = 102;
    private static final int NP_NBPCD_HOMEIDD_CC_AREA_LOCAL = 100;
    private static final int NP_NONE = 0;
    private static final String PLUS_SIGN = "+";
    private static final String TAG = "SmsNumberUtils";

    private static boolean isTwoToNine(char c) {
        return c >= '2' && c <= '9';
    }

    static {
        DBG = SystemProperties.getInt("ro.debuggable", 0) == 1;
        ALL_COUNTRY_CODES = null;
        IDDS_MAPS = new HashMap<>();
    }

    private static class NumberEntry {
        public String IDD;
        public int countryCode;
        public String number;

        public NumberEntry(String str) {
            this.number = str;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0136 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static String formatNumber(Context context, String str, String str2, int i) {
        int iCheckInternationalNumberPlan;
        String strSubstring;
        if (str == null) {
            throw new IllegalArgumentException("number is null");
        }
        if (str2 == null || str2.trim().length() == 0) {
            throw new IllegalArgumentException("activeMcc is null or empty!");
        }
        String strExtractNetworkPortion = PhoneNumberUtils.extractNetworkPortion(str);
        if (strExtractNetworkPortion == null || strExtractNetworkPortion.length() == 0) {
            throw new IllegalArgumentException("Number is invalid!");
        }
        NumberEntry numberEntry = new NumberEntry(strExtractNetworkPortion);
        ArrayList<String> allIDDs = getAllIDDs(context, str2);
        int iCheckNANP = checkNANP(numberEntry, allIDDs);
        boolean z = DBG;
        if (z) {
            Log.d(TAG, "NANP type: " + getNumberPlanType(iCheckNANP));
        }
        if (iCheckNANP != 1 && iCheckNANP != 2 && iCheckNANP != 3) {
            if (iCheckNANP != 4) {
                if (iCheckNANP != 5) {
                    iCheckInternationalNumberPlan = checkInternationalNumberPlan(context, numberEntry, allIDDs, NANP_IDD);
                    if (z) {
                        Log.d(TAG, "International type: " + getNumberPlanType(iCheckInternationalNumberPlan));
                    }
                    switch (iCheckInternationalNumberPlan) {
                        case 100:
                            if (i != 0) {
                                strSubstring = null;
                                break;
                            } else {
                                strSubstring = strExtractNetworkPortion.substring(1);
                                break;
                            }
                        case 101:
                            strSubstring = strExtractNetworkPortion;
                            break;
                        case 102:
                            strSubstring = NANP_IDD + strExtractNetworkPortion.substring(1);
                            break;
                        case 103:
                            if (i == 0 || i == 2) {
                                strSubstring = NANP_IDD + strExtractNetworkPortion.substring(numberEntry.IDD != null ? numberEntry.IDD.length() : 0);
                                break;
                            }
                        case 104:
                            int i2 = numberEntry.countryCode;
                            if (!inExceptionListForNpCcAreaLocal(numberEntry) && strExtractNetworkPortion.length() >= 11 && i2 != 1) {
                                strSubstring = NANP_IDD + strExtractNetworkPortion;
                                break;
                            }
                            break;
                        default:
                            if (strExtractNetworkPortion.startsWith(PLUS_SIGN) && (i == 1 || i == 2)) {
                                if (strExtractNetworkPortion.startsWith("+011")) {
                                    strSubstring = strExtractNetworkPortion.substring(1);
                                    break;
                                } else {
                                    strSubstring = NANP_IDD + strExtractNetworkPortion.substring(1);
                                    break;
                                }
                            }
                            break;
                    }
                    if (strSubstring == null) {
                        return strSubstring;
                    }
                } else if (i != 1) {
                    if (i == 0) {
                        return PLUS_SIGN + strExtractNetworkPortion.substring(numberEntry.IDD != null ? numberEntry.IDD.length() : 0);
                    }
                    if (i == 2) {
                        return strExtractNetworkPortion.substring(numberEntry.IDD != null ? numberEntry.IDD.length() : 0);
                    }
                    iCheckInternationalNumberPlan = checkInternationalNumberPlan(context, numberEntry, allIDDs, NANP_IDD);
                    if (z) {
                    }
                    switch (iCheckInternationalNumberPlan) {
                    }
                    if (strSubstring == null) {
                    }
                }
            } else if (i == 1 || i == 2) {
                return strExtractNetworkPortion.substring(1);
            }
        }
        return strExtractNetworkPortion;
    }

    private static ArrayList<String> getAllIDDs(Context context, String str) {
        String str2;
        String[] strArr;
        ArrayList<String> arrayList = IDDS_MAPS.get(str);
        if (arrayList != null) {
            return arrayList;
        }
        ArrayList<String> arrayList2 = new ArrayList<>();
        String[] strArr2 = {HbpcdLookup.MccIdd.IDD, "MCC"};
        Cursor cursorQuery = null;
        if (str != null) {
            strArr = new String[]{str};
            str2 = "MCC=?";
        } else {
            str2 = null;
            strArr = null;
        }
        try {
            try {
                cursorQuery = context.getContentResolver().query(HbpcdLookup.MccIdd.CONTENT_URI, strArr2, str2, strArr, null);
                if (cursorQuery.getCount() > 0) {
                    while (cursorQuery.moveToNext()) {
                        String string = cursorQuery.getString(0);
                        if (!arrayList2.contains(string)) {
                            arrayList2.add(string);
                        }
                    }
                }
            } catch (SQLException e) {
                Log.e(TAG, "Can't access HbpcdLookup database", e);
                if (cursorQuery != null) {
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            IDDS_MAPS.put(str, arrayList2);
            if (DBG) {
                Log.d(TAG, "MCC = " + str + ", all IDDs = " + arrayList2);
            }
            return arrayList2;
        } finally {
        }
    }

    private static int checkNANP(NumberEntry numberEntry, ArrayList<String> arrayList) {
        String strSubstring;
        String str = numberEntry.number;
        if (str.length() == 7) {
            char cCharAt = str.charAt(0);
            if (cCharAt >= '2' && cCharAt <= '9') {
                for (int i = 1; i < 7; i++) {
                    if (PhoneNumberUtils.isISODigit(str.charAt(i))) {
                    }
                }
                return 1;
            }
        } else if (str.length() == 10) {
            if (isNANP(str)) {
                return 2;
            }
        } else if (str.length() == 11) {
            if (isNANP(str)) {
                return 3;
            }
        } else if (str.startsWith(PLUS_SIGN)) {
            String strSubstring2 = str.substring(1);
            if (strSubstring2.length() == 11) {
                if (isNANP(strSubstring2)) {
                    return 4;
                }
            } else if (strSubstring2.startsWith(NANP_IDD) && strSubstring2.length() == 14 && isNANP(strSubstring2.substring(3))) {
                return 6;
            }
        } else {
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (str.startsWith(next) && (strSubstring = str.substring(next.length())) != null && strSubstring.startsWith(String.valueOf(1)) && isNANP(strSubstring)) {
                    numberEntry.IDD = next;
                    return 5;
                }
            }
        }
        return 0;
    }

    private static boolean isNANP(String str) {
        if (str.length() == 10 || (str.length() == 11 && str.startsWith("1"))) {
            if (str.length() == 11) {
                str = str.substring(1);
            }
            if (isTwoToNine(str.charAt(0)) && isTwoToNine(str.charAt(3))) {
                for (int i = 1; i < 10; i++) {
                    if (!PhoneNumberUtils.isISODigit(str.charAt(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private static int checkInternationalNumberPlan(Context context, NumberEntry numberEntry, ArrayList<String> arrayList, String str) {
        int countryCode;
        int countryCode2;
        String str2 = numberEntry.number;
        if (str2.startsWith(PLUS_SIGN)) {
            String strSubstring = str2.substring(1);
            if (strSubstring.startsWith(str)) {
                int countryCode3 = getCountryCode(context, strSubstring.substring(str.length()));
                if (countryCode3 <= 0) {
                    return 0;
                }
                numberEntry.countryCode = countryCode3;
                return 100;
            }
            int countryCode4 = getCountryCode(context, strSubstring);
            if (countryCode4 <= 0) {
                return 0;
            }
            numberEntry.countryCode = countryCode4;
            return 102;
        }
        if (str2.startsWith(str)) {
            int countryCode5 = getCountryCode(context, str2.substring(str.length()));
            if (countryCode5 <= 0) {
                return 0;
            }
            numberEntry.countryCode = countryCode5;
            return 101;
        }
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (str2.startsWith(next) && (countryCode2 = getCountryCode(context, str2.substring(next.length()))) > 0) {
                numberEntry.countryCode = countryCode2;
                numberEntry.IDD = next;
                return 103;
            }
        }
        if (str2.startsWith("0") || (countryCode = getCountryCode(context, str2)) <= 0) {
            return 0;
        }
        numberEntry.countryCode = countryCode;
        return 104;
    }

    private static int getCountryCode(Context context, String str) {
        int[] allCountryCodes;
        if (str.length() < 10 || (allCountryCodes = getAllCountryCodes(context)) == null) {
            return -1;
        }
        int[] iArr = new int[MAX_COUNTRY_CODES_LENGTH];
        int i = 0;
        while (i < MAX_COUNTRY_CODES_LENGTH) {
            int i2 = i + 1;
            iArr[i] = Integer.parseInt(str.substring(0, i2));
            i = i2;
        }
        for (int i3 : allCountryCodes) {
            for (int i4 = 0; i4 < MAX_COUNTRY_CODES_LENGTH; i4++) {
                if (i3 == iArr[i4]) {
                    if (DBG) {
                        Log.d(TAG, "Country code = " + i3);
                    }
                    return i3;
                }
            }
        }
        return -1;
    }

    private static int[] getAllCountryCodes(Context context) {
        int[] iArr = ALL_COUNTRY_CODES;
        if (iArr != null) {
            return iArr;
        }
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = context.getContentResolver().query(HbpcdLookup.MccLookup.CONTENT_URI, new String[]{HbpcdLookup.MccLookup.COUNTRY_CODE}, null, null, null);
                if (cursorQuery.getCount() > 0) {
                    ALL_COUNTRY_CODES = new int[cursorQuery.getCount()];
                    int i = 0;
                    while (cursorQuery.moveToNext()) {
                        int i2 = cursorQuery.getInt(0);
                        int i3 = i + 1;
                        ALL_COUNTRY_CODES[i] = i2;
                        int length = String.valueOf(i2).trim().length();
                        if (length > MAX_COUNTRY_CODES_LENGTH) {
                            MAX_COUNTRY_CODES_LENGTH = length;
                        }
                        i = i3;
                    }
                }
            } catch (SQLException e) {
                Log.e(TAG, "Can't access HbpcdLookup database", e);
                if (cursorQuery != null) {
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return ALL_COUNTRY_CODES;
        } finally {
        }
    }

    private static boolean inExceptionListForNpCcAreaLocal(NumberEntry numberEntry) {
        int i = numberEntry.countryCode;
        if (numberEntry.number.length() == 12) {
            return i == 7 || i == 20 || i == 65 || i == 90;
        }
        return false;
    }

    private static String getNumberPlanType(int i) {
        if (i == 1) {
            return "NP_NANP_LOCAL";
        }
        if (i == 2) {
            return "NP_NANP_AREA_LOCAL";
        }
        if (i == 3) {
            return "NP_NANP_NDD_AREA_LOCAL";
        }
        if (i == 4) {
            return "NP_NANP_NBPCD_CC_AREA_LOCAL";
        }
        if (i == 5) {
            return "NP_NANP_LOCALIDD_CC_AREA_LOCAL";
        }
        if (i == 6) {
            return "NP_NANP_NBPCD_HOMEIDD_CC_AREA_LOCAL";
        }
        if (i == 100) {
            return "NP_NBPCD_HOMEIDD_CC_AREA_LOCAL";
        }
        if (i == 101) {
            return "NP_HOMEIDD_CC_AREA_LOCAL";
        }
        if (i == 102) {
            return "NP_NBPCD_CC_AREA_LOCAL";
        }
        if (i == 103) {
            return "NP_LOCALIDD_CC_AREA_LOCAL";
        }
        if (i == 104) {
            return "NP_CC_AREA_LOCAL";
        }
        return "Unknown type";
    }

    public static String filterDestAddr(Context context, int i, String str) {
        int networkType;
        String strSubstring;
        boolean z = DBG;
        if (z) {
            Log.d(TAG, "enter filterDestAddr. destAddr=\"" + pii(TAG, str) + "\"");
        }
        if (str == null || !PhoneNumberUtils.isGlobalPhoneNumber(str)) {
            Log.w(TAG, "destAddr" + pii(TAG, str) + " is not a global phone number! Nothing changed.");
            return str;
        }
        TelephonyManager telephonyManagerCreateForSubscriptionId = ((TelephonyManager) context.getSystemService("phone")).createForSubscriptionId(i);
        String networkOperator = telephonyManagerCreateForSubscriptionId.getNetworkOperator();
        String number = (!needToConvert(context, i) || (networkType = getNetworkType(telephonyManagerCreateForSubscriptionId)) == -1 || TextUtils.isEmpty(networkOperator) || (strSubstring = networkOperator.substring(0, 3)) == null || strSubstring.trim().length() <= 0) ? null : formatNumber(context, str, strSubstring, networkType);
        if (z) {
            Log.d(TAG, "destAddr is ".concat(number != null ? "formatted." : "not formatted."));
            StringBuilder sb = new StringBuilder("leave filterDestAddr, new destAddr=\"");
            sb.append(number != null ? pii(TAG, number) : pii(TAG, str));
            sb.append("\"");
            Log.d(TAG, sb.toString());
        }
        return number != null ? number : str;
    }

    private static int getNetworkType(TelephonyManager telephonyManager) {
        int phoneType = telephonyManager.getPhoneType();
        if (phoneType == 1) {
            return 0;
        }
        if (phoneType == 2) {
            return isInternationalRoaming(telephonyManager) ? 2 : 1;
        }
        if (DBG) {
            Log.w(TAG, "warning! unknown mPhoneType value=" + phoneType);
        }
        return -1;
    }

    private static boolean isInternationalRoaming(TelephonyManager telephonyManager) {
        String networkCountryIso = telephonyManager.getNetworkCountryIso();
        String simCountryIso = telephonyManager.getSimCountryIso();
        boolean z = (TextUtils.isEmpty(networkCountryIso) || TextUtils.isEmpty(simCountryIso) || simCountryIso.equals(networkCountryIso)) ? false : true;
        if (z) {
            if (XmlTags.ATTR_USER_ID.equals(simCountryIso)) {
                return !"vi".equals(networkCountryIso);
            }
            if ("vi".equals(simCountryIso)) {
                return !XmlTags.ATTR_USER_ID.equals(networkCountryIso);
            }
        }
        return z;
    }

    private static boolean needToConvert(Context context, int i) {
        PersistableBundle configForSubId;
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            CarrierConfigManager carrierConfigManager = (CarrierConfigManager) context.getSystemService("carrier_config");
            if (carrierConfigManager != null && (configForSubId = carrierConfigManager.getConfigForSubId(i)) != null) {
                return configForSubId.getBoolean(CarrierConfigManager.KEY_SMS_REQUIRES_DESTINATION_NUMBER_CONVERSION_BOOL);
            }
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            return false;
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    private static String pii(String str, Object obj) {
        String strValueOf = String.valueOf(obj);
        if (obj == null || TextUtils.isEmpty(strValueOf) || Log.isLoggable(str, 2)) {
            return strValueOf;
        }
        return NavigationBarInflaterView.SIZE_MOD_START + secureHash(strValueOf.getBytes()) + NavigationBarInflaterView.SIZE_MOD_END;
    }

    private static String secureHash(byte[] bArr) {
        if (TelephonyUtils.IS_USER) {
            return "****";
        }
        try {
            return Base64.encodeToString(MessageDigest.getInstance("SHA-1").digest(bArr), 11);
        } catch (NoSuchAlgorithmException unused) {
            return "####";
        }
    }
}
