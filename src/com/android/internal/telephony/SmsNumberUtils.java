package com.android.internal.telephony;

import android.app.blob.XmlTags;
import android.content.Context;
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

    private static String formatNumber(Context context, String str, String str2, int i) {
        String substring;
        if (str == null) {
            throw new IllegalArgumentException("number is null");
        }
        if (str2 == null || str2.trim().length() == 0) {
            throw new IllegalArgumentException("activeMcc is null or empty!");
        }
        String extractNetworkPortion = PhoneNumberUtils.extractNetworkPortion(str);
        if (extractNetworkPortion == null || extractNetworkPortion.length() == 0) {
            throw new IllegalArgumentException("Number is invalid!");
        }
        NumberEntry numberEntry = new NumberEntry(extractNetworkPortion);
        ArrayList<String> allIDDs = getAllIDDs(context, str2);
        int checkNANP = checkNANP(numberEntry, allIDDs);
        boolean z = DBG;
        if (z) {
            Log.d(TAG, "NANP type: " + getNumberPlanType(checkNANP));
        }
        if (checkNANP != 1 && checkNANP != 2 && checkNANP != 3) {
            if (checkNANP != 4) {
                if (checkNANP == 5) {
                    if (i != 1) {
                        if (i == 0) {
                            return PLUS_SIGN + extractNetworkPortion.substring(numberEntry.IDD != null ? numberEntry.IDD.length() : 0);
                        }
                        if (i == 2) {
                            return extractNetworkPortion.substring(numberEntry.IDD != null ? numberEntry.IDD.length() : 0);
                        }
                    }
                }
                int checkInternationalNumberPlan = checkInternationalNumberPlan(context, numberEntry, allIDDs, NANP_IDD);
                if (z) {
                    Log.d(TAG, "International type: " + getNumberPlanType(checkInternationalNumberPlan));
                }
                switch (checkInternationalNumberPlan) {
                    case 100:
                        if (i == 0) {
                            substring = extractNetworkPortion.substring(1);
                            break;
                        }
                        substring = null;
                        break;
                    case 101:
                        substring = extractNetworkPortion;
                        break;
                    case 102:
                        substring = NANP_IDD + extractNetworkPortion.substring(1);
                        break;
                    case 103:
                        if (i == 0 || i == 2) {
                            substring = NANP_IDD + extractNetworkPortion.substring(numberEntry.IDD != null ? numberEntry.IDD.length() : 0);
                            break;
                        }
                        substring = null;
                        break;
                    case 104:
                        int i2 = numberEntry.countryCode;
                        if (!inExceptionListForNpCcAreaLocal(numberEntry) && extractNetworkPortion.length() >= 11 && i2 != 1) {
                            substring = NANP_IDD + extractNetworkPortion;
                            break;
                        }
                        substring = null;
                        break;
                    default:
                        if (extractNetworkPortion.startsWith(PLUS_SIGN) && (i == 1 || i == 2)) {
                            if (extractNetworkPortion.startsWith("+011")) {
                                substring = extractNetworkPortion.substring(1);
                                break;
                            } else {
                                substring = NANP_IDD + extractNetworkPortion.substring(1);
                                break;
                            }
                        }
                        substring = null;
                        break;
                }
                if (substring != null) {
                    return substring;
                }
            } else if (i == 1 || i == 2) {
                return extractNetworkPortion.substring(1);
            }
        }
        return extractNetworkPortion;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x004f, code lost:
    
        if (r3 != null) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0061, code lost:
    
        com.android.internal.telephony.SmsNumberUtils.IDDS_MAPS.put(r11, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0068, code lost:
    
        if (com.android.internal.telephony.SmsNumberUtils.DBG == false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x006a, code lost:
    
        android.util.Log.d(com.android.internal.telephony.SmsNumberUtils.TAG, "MCC = " + r11 + ", all IDDs = " + r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0083, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x005e, code lost:
    
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x005c, code lost:
    
        if (r3 == null) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.util.ArrayList<java.lang.String> getAllIDDs(android.content.Context r10, java.lang.String r11) {
        /*
            java.lang.String r1 = "SmsNumberUtils"
            java.util.HashMap<java.lang.String, java.util.ArrayList<java.lang.String>> r0 = com.android.internal.telephony.SmsNumberUtils.IDDS_MAPS
            java.lang.Object r0 = r0.get(r11)
            java.util.ArrayList r0 = (java.util.ArrayList) r0
            if (r0 == 0) goto Ld
            return r0
        Ld:
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.lang.String r0 = "IDD"
            java.lang.String r3 = "MCC"
            java.lang.String[] r6 = new java.lang.String[]{r0, r3}
            r0 = 0
            r3 = 0
            if (r11 == 0) goto L28
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]
            r4[r0] = r11
            java.lang.String r5 = "MCC=?"
            r8 = r4
            r7 = r5
            goto L2a
        L28:
            r7 = r3
            r8 = r7
        L2a:
            android.content.ContentResolver r4 = r10.getContentResolver()     // Catch: java.lang.Throwable -> L52 android.database.SQLException -> L55
            android.net.Uri r5 = com.android.internal.telephony.HbpcdLookup.MccIdd.CONTENT_URI     // Catch: java.lang.Throwable -> L52 android.database.SQLException -> L55
            r9 = 0
            android.database.Cursor r3 = r4.query(r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L52 android.database.SQLException -> L55
            int r10 = r3.getCount()     // Catch: java.lang.Throwable -> L52 android.database.SQLException -> L55
            if (r10 <= 0) goto L4f
        L3b:
            boolean r10 = r3.moveToNext()     // Catch: java.lang.Throwable -> L52 android.database.SQLException -> L55
            if (r10 == 0) goto L4f
            java.lang.String r10 = r3.getString(r0)     // Catch: java.lang.Throwable -> L52 android.database.SQLException -> L55
            boolean r4 = r2.contains(r10)     // Catch: java.lang.Throwable -> L52 android.database.SQLException -> L55
            if (r4 != 0) goto L3b
            r2.add(r10)     // Catch: java.lang.Throwable -> L52 android.database.SQLException -> L55
            goto L3b
        L4f:
            if (r3 == 0) goto L61
            goto L5e
        L52:
            r0 = move-exception
            r10 = r0
            goto L84
        L55:
            r0 = move-exception
            r10 = r0
            java.lang.String r0 = "Can't access HbpcdLookup database"
            android.util.Log.e(r1, r0, r10)     // Catch: java.lang.Throwable -> L52
            if (r3 == 0) goto L61
        L5e:
            r3.close()
        L61:
            java.util.HashMap<java.lang.String, java.util.ArrayList<java.lang.String>> r10 = com.android.internal.telephony.SmsNumberUtils.IDDS_MAPS
            r10.put(r11, r2)
            boolean r10 = com.android.internal.telephony.SmsNumberUtils.DBG
            if (r10 == 0) goto L83
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r0 = "MCC = "
            r10.<init>(r0)
            r10.append(r11)
            java.lang.String r11 = ", all IDDs = "
            r10.append(r11)
            r10.append(r2)
            java.lang.String r10 = r10.toString()
            android.util.Log.d(r1, r10)
        L83:
            return r2
        L84:
            if (r3 == 0) goto L89
            r3.close()
        L89:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.SmsNumberUtils.getAllIDDs(android.content.Context, java.lang.String):java.util.ArrayList");
    }

    private static int checkNANP(NumberEntry numberEntry, ArrayList<String> arrayList) {
        String substring;
        String str = numberEntry.number;
        if (str.length() == 7) {
            char charAt = str.charAt(0);
            if (charAt >= '2' && charAt <= '9') {
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
            String substring2 = str.substring(1);
            if (substring2.length() == 11) {
                if (isNANP(substring2)) {
                    return 4;
                }
            } else if (substring2.startsWith(NANP_IDD) && substring2.length() == 14 && isNANP(substring2.substring(3))) {
                return 6;
            }
        } else {
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (str.startsWith(next) && (substring = str.substring(next.length())) != null && substring.startsWith(String.valueOf(1)) && isNANP(substring)) {
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
            String substring = str2.substring(1);
            if (substring.startsWith(str)) {
                int countryCode3 = getCountryCode(context, substring.substring(str.length()));
                if (countryCode3 <= 0) {
                    return 0;
                }
                numberEntry.countryCode = countryCode3;
                return 100;
            }
            int countryCode4 = getCountryCode(context, substring);
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

    /* JADX WARN: Code restructure failed: missing block: B:20:0x004d, code lost:
    
        if (r1 != null) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0063, code lost:
    
        return com.android.internal.telephony.SmsNumberUtils.ALL_COUNTRY_CODES;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x005e, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x005c, code lost:
    
        if (r1 == null) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int[] getAllCountryCodes(android.content.Context r8) {
        /*
            int[] r0 = com.android.internal.telephony.SmsNumberUtils.ALL_COUNTRY_CODES
            if (r0 == 0) goto L5
            return r0
        L5:
            r1 = 0
            java.lang.String r0 = "Country_Code"
            java.lang.String[] r4 = new java.lang.String[]{r0}     // Catch: java.lang.Throwable -> L50 android.database.SQLException -> L53
            android.content.ContentResolver r2 = r8.getContentResolver()     // Catch: java.lang.Throwable -> L50 android.database.SQLException -> L53
            android.net.Uri r3 = com.android.internal.telephony.HbpcdLookup.MccLookup.CONTENT_URI     // Catch: java.lang.Throwable -> L50 android.database.SQLException -> L53
            r6 = 0
            r7 = 0
            r5 = 0
            android.database.Cursor r1 = r2.query(r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L50 android.database.SQLException -> L53
            int r8 = r1.getCount()     // Catch: java.lang.Throwable -> L50 android.database.SQLException -> L53
            if (r8 <= 0) goto L4d
            int r8 = r1.getCount()     // Catch: java.lang.Throwable -> L50 android.database.SQLException -> L53
            int[] r8 = new int[r8]     // Catch: java.lang.Throwable -> L50 android.database.SQLException -> L53
            com.android.internal.telephony.SmsNumberUtils.ALL_COUNTRY_CODES = r8     // Catch: java.lang.Throwable -> L50 android.database.SQLException -> L53
            r8 = 0
            r0 = r8
        L29:
            boolean r2 = r1.moveToNext()     // Catch: java.lang.Throwable -> L50 android.database.SQLException -> L53
            if (r2 == 0) goto L4d
            int r2 = r1.getInt(r8)     // Catch: java.lang.Throwable -> L50 android.database.SQLException -> L53
            int[] r3 = com.android.internal.telephony.SmsNumberUtils.ALL_COUNTRY_CODES     // Catch: java.lang.Throwable -> L50 android.database.SQLException -> L53
            int r4 = r0 + 1
            r3[r0] = r2     // Catch: java.lang.Throwable -> L50 android.database.SQLException -> L53
            java.lang.String r0 = java.lang.String.valueOf(r2)     // Catch: java.lang.Throwable -> L50 android.database.SQLException -> L53
            java.lang.String r0 = r0.trim()     // Catch: java.lang.Throwable -> L50 android.database.SQLException -> L53
            int r0 = r0.length()     // Catch: java.lang.Throwable -> L50 android.database.SQLException -> L53
            int r2 = com.android.internal.telephony.SmsNumberUtils.MAX_COUNTRY_CODES_LENGTH     // Catch: java.lang.Throwable -> L50 android.database.SQLException -> L53
            if (r0 <= r2) goto L4b
            com.android.internal.telephony.SmsNumberUtils.MAX_COUNTRY_CODES_LENGTH = r0     // Catch: java.lang.Throwable -> L50 android.database.SQLException -> L53
        L4b:
            r0 = r4
            goto L29
        L4d:
            if (r1 == 0) goto L61
            goto L5e
        L50:
            r0 = move-exception
            r8 = r0
            goto L64
        L53:
            r0 = move-exception
            r8 = r0
            java.lang.String r0 = "SmsNumberUtils"
            java.lang.String r2 = "Can't access HbpcdLookup database"
            android.util.Log.e(r0, r2, r8)     // Catch: java.lang.Throwable -> L50
            if (r1 == 0) goto L61
        L5e:
            r1.close()
        L61:
            int[] r8 = com.android.internal.telephony.SmsNumberUtils.ALL_COUNTRY_CODES
            return r8
        L64:
            if (r1 == 0) goto L69
            r1.close()
        L69:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.SmsNumberUtils.getAllCountryCodes(android.content.Context):int[]");
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
        String substring;
        boolean z = DBG;
        if (z) {
            Log.d(TAG, "enter filterDestAddr. destAddr=\"" + pii(TAG, str) + "\"");
        }
        if (str == null || !PhoneNumberUtils.isGlobalPhoneNumber(str)) {
            Log.w(TAG, "destAddr" + pii(TAG, str) + " is not a global phone number! Nothing changed.");
            return str;
        }
        TelephonyManager createForSubscriptionId = ((TelephonyManager) context.getSystemService("phone")).createForSubscriptionId(i);
        String networkOperator = createForSubscriptionId.getNetworkOperator();
        String formatNumber = (!needToConvert(context, i) || (networkType = getNetworkType(createForSubscriptionId)) == -1 || TextUtils.isEmpty(networkOperator) || (substring = networkOperator.substring(0, 3)) == null || substring.trim().length() <= 0) ? null : formatNumber(context, str, substring, networkType);
        if (z) {
            Log.d(TAG, "destAddr is ".concat(formatNumber != null ? "formatted." : "not formatted."));
            StringBuilder sb = new StringBuilder("leave filterDestAddr, new destAddr=\"");
            sb.append(formatNumber != null ? pii(TAG, formatNumber) : pii(TAG, str));
            sb.append("\"");
            Log.d(TAG, sb.toString());
        }
        return formatNumber != null ? formatNumber : str;
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
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            CarrierConfigManager carrierConfigManager = (CarrierConfigManager) context.getSystemService("carrier_config");
            if (carrierConfigManager != null && (configForSubId = carrierConfigManager.getConfigForSubId(i)) != null) {
                return configForSubId.getBoolean(CarrierConfigManager.KEY_SMS_REQUIRES_DESTINATION_NUMBER_CONVERSION_BOOL);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static String pii(String str, Object obj) {
        String valueOf = String.valueOf(obj);
        if (obj == null || TextUtils.isEmpty(valueOf) || Log.isLoggable(str, 2)) {
            return valueOf;
        }
        return NavigationBarInflaterView.SIZE_MOD_START + secureHash(valueOf.getBytes()) + NavigationBarInflaterView.SIZE_MOD_END;
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
