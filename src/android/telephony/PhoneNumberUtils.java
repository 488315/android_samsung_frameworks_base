package android.telephony;

import android.annotation.SystemApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.provider.Contacts;
import android.sysprop.TelephonyProperties;
import android.telecom.PhoneAccount;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TtsSpan;
import android.util.SparseIntArray;
import com.android.i18n.phonenumbers.MissingMetadataException;
import com.android.i18n.phonenumbers.NumberParseException;
import com.android.i18n.phonenumbers.PhoneNumberUtil;
import com.android.i18n.phonenumbers.Phonenumber;
import com.android.internal.R;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.Flags;
import com.android.internal.telephony.SemTelephonyUtils;
import com.android.internal.telephony.TelephonyFeatures;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class PhoneNumberUtils {
    private static final String BCD_CALLED_PARTY_EXTENDED = "*#abc";
    private static final String BCD_EF_ADN_EXTENDED = "*#,N;";
    public static final int BCD_EXTENDED_TYPE_CALLED_PARTY = 2;
    public static final int BCD_EXTENDED_TYPE_EF_ADN = 1;
    private static final int CCC_LENGTH;
    private static final String CLIR_OFF = "#31#";
    private static final String CLIR_ON = "*31#";
    private static final boolean[] COUNTRY_CALLING_CALL;
    private static final boolean DBG = false;
    public static final int FORMAT_JAPAN = 2;
    public static final int FORMAT_KOREA = 82;
    public static final int FORMAT_NANP = 1;
    public static final int FORMAT_UNKNOWN = 0;
    private static final SparseIntArray KEYPAD_MAP;
    private static final int KRNP_STATE_0505_START = 14;
    private static final int KRNP_STATE_AREA_SEOUL = 6;
    private static final int KRNP_STATE_EXCEPT_CASE_1 = 10;
    private static final int KRNP_STATE_EXCEPT_CASE_2 = 11;
    private static final int KRNP_STATE_NORMAL = 5;
    private static final int KRNP_STATE_PLUS = 9;
    private static final int KRNP_STATE_SHARP = 13;
    private static final int KRNP_STATE_SHARP_NINE = 8;
    private static final int KRNP_STATE_STAR = 12;
    private static final int KRNP_STATE_ZERO_START = 7;
    static final String LOG_TAG = "PhoneNumberUtils";
    private static final Uri MCC_OTA_URI;
    private static final String NANP_IDP_STRING = "011";
    private static final int NANP_LENGTH = 10;
    private static final int NANP_STATE_DASH = 4;
    private static final int NANP_STATE_DIGIT = 1;
    private static final int NANP_STATE_ONE = 3;
    private static final int NANP_STATE_PLUS = 2;
    private static final int OTALOOKUP_INDEX_AREA_CITY_CODE = 8;
    private static final int OTALOOKUP_INDEX_COUNTRY_CODE = 6;
    private static final int OTALOOKUP_INDEX_COUNTRY_NAME = 1;
    private static final int OTALOOKUP_INDEX_IDD = 3;
    private static final int OTALOOKUP_INDEX_MCC = 2;
    private static final int OTALOOKUP_INDEX_NANP = 5;
    private static final int OTALOOKUP_INDEX_NATIONAL_NUMBER_LENGTH = 9;
    private static final int OTALOOKUP_INDEX_NBPCD = 7;
    private static final int OTALOOKUP_INDEX_NDD = 4;
    public static final String OTA_COUNTRY_MCC_KEY = "otaCountryMccKey";
    private static final Uri OTA_COUNTRY_URI;
    public static final char PAUSE = ',';
    private static final char PLUS_SIGN_CHAR = '+';
    private static final String PLUS_SIGN_STRING = "+";
    private static final String PREFIX_WPS = "*272";
    private static final String PREFIX_WPS_CLIR_ACTIVATE = "*31#*272";
    private static final String PREFIX_WPS_CLIR_DEACTIVATE = "#31#*272";
    private static final Uri REF_COUNTRY_SHARED_PREF;
    public static final int TOA_International = 145;
    public static final int TOA_Unknown = 129;
    public static final char WAIT = ';';
    public static final char WILD = 'N';
    public static boolean isAssistedDialingNumber;
    private static boolean isCDMARegistered;
    private static boolean isGSMRegistered;
    private static boolean isNANPCountry;
    private static boolean isNetRoaming;
    private static boolean isOTANANPCountry;
    private static Cursor mCursor;
    private static Cursor mCursorCountry;
    private static int numberLength;
    private static String otaCountryCountryCode;
    private static String otaCountryIDDPrefix;
    private static String otaCountryMCC;
    private static String otaCountryNDDPrefix;
    private static String otaCountryName;
    private static String refCountryAreaCode;
    private static String refCountryCountryCode;
    private static String refCountryIDDPrefix;
    private static String refCountryMCC;
    private static String refCountryNDDPrefix;
    private static String refCountryName;
    private static int refCountryNationalNumberLength;
    private static String[] sConvertToEmergencyMap;
    private static final Pattern GLOBAL_PHONE_NUMBER_PATTERN = Pattern.compile("[\\+]?[0-9.-]+");
    private static int sMinMatch = 0;
    private static final String[] NANP_COUNTRIES = {"US", "CA", "AS", "AI", "AG", "BS", "BB", "BM", "VG", "KY", "DM", "DO", "GD", "GU", "JM", "PR", "MS", "MP", "KN", "LC", "VC", "TT", "TC", "VI"};
    private static final String KOREA_ISO_COUNTRY_CODE = "KR";
    private static final String JAPAN_ISO_COUNTRY_CODE = "JP";
    private static final String SINGAPORE_ISO_COUNTRY_CODE = "SG";
    private static final String[] COUNTRY_CODES_TO_FORMAT_NATIONALLY = {KOREA_ISO_COUNTRY_CODE, JAPAN_ISO_COUNTRY_CODE, SINGAPORE_ISO_COUNTRY_CODE, "TW"};

    @Retention(RetentionPolicy.SOURCE)
    public @interface BcdExtendType {
    }

    public static final boolean is12Key(char c) {
        return (c >= '0' && c <= '9') || c == '*' || c == '#';
    }

    public static final boolean isDialable(char c) {
        return (c >= '0' && c <= '9') || c == '*' || c == '#' || c == '+' || c == 'N';
    }

    public static boolean isISODigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static final boolean isNonSeparator(char c) {
        return (c >= '0' && c <= '9') || c == '*' || c == '#' || c == '+' || c == 'N' || c == ';' || c == ',';
    }

    private static boolean isPause(char c) {
        return c == 'p' || c == 'P';
    }

    public static final boolean isReallyDialable(char c) {
        return (c >= '0' && c <= '9') || c == '*' || c == '#' || c == '+';
    }

    public static final boolean isStartsPostDial(char c) {
        return c == ',' || c == ';';
    }

    private static boolean isToneWait(char c) {
        return c == 'w' || c == 'W';
    }

    private static boolean isTwoToNine(char c) {
        return c >= '2' && c <= '9';
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x000c A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static int minPositive(int i, int i2) {
        if (i < 0 || i2 < 0) {
            if (i < 0) {
                if (i2 >= 0) {
                    return i2;
                }
                return -1;
            }
            return i;
        }
        if (i < i2) {
            return i;
        }
    }

    private static int tryGetISODigit(char c) {
        if ('0' > c || c > '9') {
            return -1;
        }
        return c - '0';
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        KEYPAD_MAP = sparseIntArray;
        sparseIntArray.put(97, 50);
        sparseIntArray.put(98, 50);
        sparseIntArray.put(99, 50);
        sparseIntArray.put(65, 50);
        sparseIntArray.put(66, 50);
        sparseIntArray.put(67, 50);
        sparseIntArray.put(100, 51);
        sparseIntArray.put(101, 51);
        sparseIntArray.put(102, 51);
        sparseIntArray.put(68, 51);
        sparseIntArray.put(69, 51);
        sparseIntArray.put(70, 51);
        sparseIntArray.put(103, 52);
        sparseIntArray.put(104, 52);
        sparseIntArray.put(105, 52);
        sparseIntArray.put(71, 52);
        sparseIntArray.put(72, 52);
        sparseIntArray.put(73, 52);
        sparseIntArray.put(106, 53);
        sparseIntArray.put(107, 53);
        sparseIntArray.put(108, 53);
        sparseIntArray.put(74, 53);
        sparseIntArray.put(75, 53);
        sparseIntArray.put(76, 53);
        sparseIntArray.put(109, 54);
        sparseIntArray.put(110, 54);
        sparseIntArray.put(111, 54);
        sparseIntArray.put(77, 54);
        sparseIntArray.put(78, 54);
        sparseIntArray.put(79, 54);
        sparseIntArray.put(112, 55);
        sparseIntArray.put(113, 55);
        sparseIntArray.put(114, 55);
        sparseIntArray.put(115, 55);
        sparseIntArray.put(80, 55);
        sparseIntArray.put(81, 55);
        sparseIntArray.put(82, 55);
        sparseIntArray.put(83, 55);
        sparseIntArray.put(116, 56);
        sparseIntArray.put(117, 56);
        sparseIntArray.put(118, 56);
        sparseIntArray.put(84, 56);
        sparseIntArray.put(85, 56);
        sparseIntArray.put(86, 56);
        sparseIntArray.put(119, 57);
        sparseIntArray.put(120, 57);
        sparseIntArray.put(121, 57);
        sparseIntArray.put(122, 57);
        sparseIntArray.put(87, 57);
        sparseIntArray.put(88, 57);
        sparseIntArray.put(89, 57);
        sparseIntArray.put(90, 57);
        boolean[] zArr = {true, true, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, true, true, false, true, true, true, true, true, false, true, false, false, true, true, false, false, true, true, true, true, true, true, true, false, true, true, true, true, true, true, true, true, false, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, false, true, false, false, true, true, true, true, true, true, true, false, false, true, false};
        COUNTRY_CALLING_CALL = zArr;
        CCC_LENGTH = zArr.length;
        sConvertToEmergencyMap = null;
        refCountryName = "";
        refCountryIDDPrefix = "";
        refCountryNDDPrefix = "";
        refCountryCountryCode = "";
        refCountryMCC = "";
        isNANPCountry = false;
        refCountryAreaCode = "";
        isGSMRegistered = false;
        isCDMARegistered = false;
        otaCountryName = "";
        otaCountryMCC = "";
        otaCountryIDDPrefix = "";
        otaCountryNDDPrefix = "";
        otaCountryCountryCode = "";
        isOTANANPCountry = false;
        refCountryNationalNumberLength = 0;
        numberLength = 0;
        REF_COUNTRY_SHARED_PREF = Uri.parse("content://assisteddialing/refcountry");
        MCC_OTA_URI = Uri.parse("content://assisteddialing/mcc_otalookup");
        OTA_COUNTRY_URI = Uri.parse("content://assisteddialing/ota_country");
        isAssistedDialingNumber = false;
        isNetRoaming = false;
    }

    private static int getMinMatch() {
        if (sMinMatch == 0) {
            sMinMatch = Resources.getSystem().getInteger(R.integer.config_phonenumber_compare_min_match);
        }
        return sMinMatch;
    }

    public static int getMinMatchForTest() {
        return getMinMatch();
    }

    public static void setMinMatchForTest(int i) {
        sMinMatch = i;
    }

    private static boolean isSeparator(char c) {
        if (isDialable(c)) {
            return false;
        }
        if ('a' > c || c > 'z') {
            return 'A' > c || c > 'Z';
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:52:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r6v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getNumberFromIntent(Intent intent, Context context) throws Throwable {
        String scheme;
        String str;
        Throwable th;
        RuntimeException runtimeException;
        Cursor cursorQuery;
        Uri data = intent.getData();
        ?? r6 = 0;
        string = null;
        String string = null;
        if (data == null || (scheme = data.getScheme()) == null) {
            return null;
        }
        if (scheme.equals(PhoneAccount.SCHEME_TEL) || scheme.equals("sip")) {
            return data.getSchemeSpecificPart();
        }
        if (context == null) {
            return null;
        }
        intent.resolveType(context);
        String authority = data.getAuthority();
        if (Contacts.AUTHORITY.equals(authority)) {
            str = "number";
        } else {
            str = "com.android.contacts".equals(authority) ? "data1" : null;
        }
        try {
            try {
                cursorQuery = context.getContentResolver().query(data, new String[]{str}, null, null, null);
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.moveToFirst()) {
                            string = cursorQuery.getString(cursorQuery.getColumnIndex(str));
                        }
                    } catch (RuntimeException e) {
                        runtimeException = e;
                        com.android.telephony.Rlog.e(LOG_TAG, "Error getting phone number.", runtimeException);
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return null;
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return string;
            } catch (Throwable th2) {
                th = th2;
                r6 = context;
                if (r6 == 0) {
                    r6.close();
                    throw th;
                }
                throw th;
            }
        } catch (RuntimeException e2) {
            runtimeException = e2;
            cursorQuery = null;
        } catch (Throwable th3) {
            th = th3;
            if (r6 == 0) {
            }
        }
    }

    public static String extractNetworkPortion(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            int iDigit = Character.digit(cCharAt, 10);
            if (iDigit == -1) {
                if (cCharAt == '+') {
                    String string = sb.toString();
                    if (string.length() == 0 || string.equals(CLIR_ON) || string.equals(CLIR_OFF)) {
                        sb.append(cCharAt);
                    }
                } else if (isDialable(cCharAt)) {
                    sb.append(cCharAt);
                } else if (isStartsPostDial(cCharAt)) {
                    break;
                }
            } else {
                sb.append(iDigit);
            }
        }
        return sb.toString();
    }

    public static String extractNetworkPortionAlt(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder(length);
        boolean z = false;
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '+') {
                if (z) {
                    continue;
                } else {
                    z = true;
                }
            }
            if (isDialable(cCharAt)) {
                sb.append(cCharAt);
            } else if (isStartsPostDial(cCharAt)) {
                break;
            }
        }
        return sb.toString();
    }

    public static String stripSeparators(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            int iDigit = Character.digit(cCharAt, 10);
            if (iDigit != -1) {
                sb.append(iDigit);
            } else if (isNonSeparator(cCharAt)) {
                sb.append(cCharAt);
            }
        }
        return sb.toString();
    }

    public static String convertAndStrip(String str) {
        return stripSeparators(convertKeypadLettersToDigits(str));
    }

    public static String convertPreDial(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (isPause(cCharAt)) {
                cCharAt = ',';
            } else if (isToneWait(cCharAt)) {
                cCharAt = ';';
            }
            sb.append(cCharAt);
        }
        return sb.toString();
    }

    private static void log(String str) {
        com.android.telephony.Rlog.d(LOG_TAG, str);
    }

    private static int indexOfLastNetworkChar(String str) {
        int length = str.length();
        int iMinPositive = minPositive(str.indexOf(44), str.indexOf(59));
        return iMinPositive < 0 ? length - 1 : iMinPositive - 1;
    }

    public static String extractPostDialPortion(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int iIndexOfLastNetworkChar = indexOfLastNetworkChar(str) + 1; iIndexOfLastNetworkChar < length; iIndexOfLastNetworkChar++) {
            char cCharAt = str.charAt(iIndexOfLastNetworkChar);
            if (isNonSeparator(cCharAt)) {
                sb.append(cCharAt);
            }
        }
        return sb.toString();
    }

    @Deprecated
    public static boolean compare(String str, String str2) {
        return compare(str, str2, false);
    }

    @Deprecated
    public static boolean compare(Context context, String str, String str2) {
        return compare(str, str2, context.getResources().getBoolean(R.bool.config_use_strict_phone_number_comparation));
    }

    public static boolean compare(String str, String str2, boolean z) {
        return z ? compareStrictly(str, str2) : compareLoosely(str, str2);
    }

    public static boolean compareLoosely(String str, String str2) {
        boolean z;
        int integer = SemCscFeature.getInstance().getInteger("CscFeature_RIL_CallerIdMatchingDigit", 7);
        if (str == null || str2 == null) {
            return str == str2;
        }
        if (str.length() != 0 && str2.length() != 0) {
            int iIndexOfLastNetworkChar = indexOfLastNetworkChar(str);
            int iIndexOfLastNetworkChar2 = indexOfLastNetworkChar(str2);
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            while (iIndexOfLastNetworkChar >= 0 && iIndexOfLastNetworkChar2 >= 0) {
                char cCharAt = str.charAt(iIndexOfLastNetworkChar);
                if (isDialable(cCharAt)) {
                    z = false;
                } else {
                    iIndexOfLastNetworkChar--;
                    i++;
                    z = true;
                }
                char cCharAt2 = str2.charAt(iIndexOfLastNetworkChar2);
                if (!isDialable(cCharAt2)) {
                    iIndexOfLastNetworkChar2--;
                    i2++;
                    z = true;
                }
                if (!z) {
                    if (cCharAt2 != cCharAt && cCharAt != 'N' && cCharAt2 != 'N') {
                        break;
                    }
                    iIndexOfLastNetworkChar--;
                    iIndexOfLastNetworkChar2--;
                    i3++;
                }
            }
            if (i3 < integer) {
                int length = str.length() - i;
                return length == str2.length() - i2 && length == i3;
            }
            if (i3 >= integer && (iIndexOfLastNetworkChar < 0 || iIndexOfLastNetworkChar2 < 0)) {
                return true;
            }
            int i4 = iIndexOfLastNetworkChar + 1;
            if (matchIntlPrefix(str, i4) && matchIntlPrefix(str2, iIndexOfLastNetworkChar2 + 1)) {
                return true;
            }
            if (matchTrunkPrefix(str, i4) && matchIntlPrefixAndCC(str2, iIndexOfLastNetworkChar2 + 1)) {
                return true;
            }
            if (matchTrunkPrefix(str2, iIndexOfLastNetworkChar2 + 1) && matchIntlPrefixAndCC(str, i4)) {
                return true;
            }
        }
        return false;
    }

    public static boolean compareStrictly(String str, String str2) {
        return compareStrictly(str, str2, true);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:36:0x006f
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:225)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:195)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:62)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    public static boolean compareStrictly(java.lang.String r17, java.lang.String r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.PhoneNumberUtils.compareStrictly(java.lang.String, java.lang.String, boolean):boolean");
    }

    public static boolean semCompareStrictly(String str, String str2, boolean z) {
        return compareStrictly(str, str2, z);
    }

    public static String toCallerIDMinMatch(String str) {
        return internalGetStrippedReversed(extractNetworkPortionAlt(str), getMinMatch());
    }

    public static String semToCallerIDMinMatch(String str, int i) {
        String strExtractNetworkPortionAlt = extractNetworkPortionAlt(str);
        if (i <= 0) {
            i = getMinMatch();
        }
        return internalGetStrippedReversed(strExtractNetworkPortionAlt, i);
    }

    public static String getStrippedReversed(String str) {
        String strExtractNetworkPortionAlt = extractNetworkPortionAlt(str);
        if (strExtractNetworkPortionAlt == null) {
            return null;
        }
        return internalGetStrippedReversed(strExtractNetworkPortionAlt, strExtractNetworkPortionAlt.length());
    }

    private static String internalGetStrippedReversed(String str, int i) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(i);
        int length = str.length();
        for (int i2 = length - 1; i2 >= 0 && length - i2 <= i; i2--) {
            sb.append(str.charAt(i2));
        }
        return sb.toString();
    }

    public static String stringFromStringAndTOA(String str, int i) {
        if (str == null) {
            return null;
        }
        if (i != 145 || str.length() <= 0 || str.charAt(0) == '+') {
            return str;
        }
        return PLUS_SIGN_STRING + str;
    }

    public static int toaFromString(String str) {
        return (str == null || str.length() <= 0 || str.charAt(0) != '+') ? 129 : 145;
    }

    @Deprecated
    public static String calledPartyBCDToString(byte[] bArr, int i, int i2) {
        return calledPartyBCDToString(bArr, i, i2, 1);
    }

    public static String calledPartyBCDToString(byte[] bArr, int i, int i2, int i3) {
        StringBuilder sb;
        StringBuilder sb2 = new StringBuilder((i2 * 2) + 1);
        if (i2 < 2) {
            return "";
        }
        boolean z = (bArr[i] & 240) == 144;
        internalCalledPartyBCDFragmentToString(sb2, bArr, i + 1, i2 - 1, i3);
        if (z && sb2.length() == 0) {
            return "";
        }
        if (z) {
            String string = sb2.toString();
            Matcher matcher = Pattern.compile("(^[#*])(.*)([#*])(.*)(#)$").matcher(string);
            if (matcher.matches()) {
                if ("".equals(matcher.group(2))) {
                    sb = new StringBuilder();
                    sb.append(matcher.group(1));
                    sb.append(matcher.group(3));
                    sb.append(matcher.group(4));
                    sb.append(matcher.group(5));
                    sb.append(PLUS_SIGN_STRING);
                } else {
                    sb = new StringBuilder();
                    sb.append(matcher.group(1));
                    sb.append(matcher.group(2));
                    sb.append(matcher.group(3));
                    sb.append(PLUS_SIGN_STRING);
                    sb.append(matcher.group(4));
                    sb.append(matcher.group(5));
                }
            } else {
                Matcher matcher2 = Pattern.compile("(^[#*])(.*)([#*])(.*)").matcher(string);
                if (matcher2.matches()) {
                    sb = new StringBuilder();
                    sb.append(matcher2.group(1));
                    sb.append(matcher2.group(2));
                    sb.append(matcher2.group(3));
                    sb.append(PLUS_SIGN_STRING);
                    sb.append(matcher2.group(4));
                } else {
                    sb2 = new StringBuilder();
                    sb2.append(PLUS_SIGN_CHAR);
                    sb2.append(string);
                }
            }
            sb2 = sb;
        }
        return sb2.toString();
    }

    private static void internalCalledPartyBCDFragmentToString(StringBuilder sb, byte[] bArr, int i, int i2, int i3) {
        char cBcdToChar;
        char cBcdToChar2;
        int i4 = i;
        while (true) {
            int i5 = i2 + i;
            if (i4 >= i5 || (cBcdToChar = bcdToChar((byte) (bArr[i4] & 15), i3)) == 0) {
                return;
            }
            sb.append(cBcdToChar);
            byte b = (byte) ((bArr[i4] >> 4) & 15);
            if ((b == 15 && i4 + 1 == i5) || (cBcdToChar2 = bcdToChar(b, i3)) == 0) {
                return;
            }
            sb.append(cBcdToChar2);
            i4++;
        }
    }

    @Deprecated
    public static String calledPartyBCDFragmentToString(byte[] bArr, int i, int i2) {
        return calledPartyBCDFragmentToString(bArr, i, i2, 1);
    }

    public static String calledPartyBCDFragmentToString(byte[] bArr, int i, int i2, int i3) {
        StringBuilder sb = new StringBuilder(i2 * 2);
        internalCalledPartyBCDFragmentToString(sb, bArr, i, i2, i3);
        return sb.toString();
    }

    private static char bcdToChar(byte b, int i) {
        String str;
        int i2;
        if (b < 10) {
            return (char) (b + SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90);
        }
        if (1 == i) {
            str = BCD_EF_ADN_EXTENDED;
        } else {
            str = 2 == i ? BCD_CALLED_PARTY_EXTENDED : null;
        }
        if (str == null || (i2 = b - 10) >= str.length()) {
            return (char) 0;
        }
        return str.charAt(i2);
    }

    private static int charToBCD(char c, int i) {
        String str;
        if ('0' <= c && c <= '9') {
            return c - '0';
        }
        if (1 == i) {
            str = BCD_EF_ADN_EXTENDED;
        } else {
            str = 2 == i ? BCD_CALLED_PARTY_EXTENDED : null;
        }
        if (str == null || str.indexOf(c) == -1) {
            throw new RuntimeException("invalid char for BCD " + c);
        }
        return str.indexOf(c) + 10;
    }

    public static boolean isWellFormedSmsAddress(String str) {
        String strExtractNetworkPortion = extractNetworkPortion(str);
        return (strExtractNetworkPortion.equals(PLUS_SIGN_STRING) || TextUtils.isEmpty(strExtractNetworkPortion) || !isDialable(strExtractNetworkPortion)) ? false : true;
    }

    public static boolean isGlobalPhoneNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return GLOBAL_PHONE_NUMBER_PATTERN.matcher(str).matches();
    }

    private static boolean isDialable(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!isDialable(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isNonSeparator(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!isNonSeparator(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static byte[] networkPortionToCalledPartyBCD(String str) {
        return numberToCalledPartyBCDHelper(extractNetworkPortion(str), false, 1);
    }

    public static byte[] networkPortionToCalledPartyBCDWithLength(String str) {
        return numberToCalledPartyBCDHelper(extractNetworkPortion(str), true, 1);
    }

    @Deprecated
    public static byte[] numberToCalledPartyBCD(String str) {
        return numberToCalledPartyBCD(str, 1);
    }

    public static byte[] numberToCalledPartyBCD(String str, int i) {
        return numberToCalledPartyBCDHelper(str, false, i);
    }

    private static byte[] numberToCalledPartyBCDHelper(String str, boolean z, int i) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int length = str.length();
        char c = 0;
        boolean z2 = str.indexOf(43) != -1;
        int i2 = z2 ? length - 1 : length;
        if (i2 == 0) {
            return null;
        }
        int i3 = (i2 + 1) / 2;
        int i4 = z ? 2 : 1;
        int i5 = i3 + i4;
        byte[] bArr = new byte[i5];
        int i6 = 0;
        for (int i7 = 0; i7 < length; i7++) {
            char cCharAt = str.charAt(i7);
            if (cCharAt != '+') {
                int i8 = (i6 >> 1) + i4;
                bArr[i8] = (byte) (((byte) ((charToBCD(cCharAt, i) & 15) << ((i6 & 1) == 1 ? 4 : 0))) | bArr[i8]);
                i6++;
            }
        }
        if ((i6 & 1) == 1) {
            int i9 = i4 + (i6 >> 1);
            bArr[i9] = (byte) (bArr[i9] | 240);
        }
        if (z) {
            bArr[0] = (byte) (i5 - 1);
            c = 1;
        }
        bArr[c] = (byte) (z2 ? 145 : 129);
        return bArr;
    }

    @Deprecated
    public static String formatNumber(String str) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        formatNumber(spannableStringBuilder, getFormatTypeForLocale(Locale.getDefault()));
        return spannableStringBuilder.toString();
    }

    @Deprecated
    public static String formatNumber(String str, int i) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        formatNumber(spannableStringBuilder, i);
        return spannableStringBuilder.toString();
    }

    @Deprecated
    public static int getFormatTypeForLocale(Locale locale) {
        return getFormatTypeFromCountryCode(locale.getCountry());
    }

    @Deprecated
    public static void formatNumber(Editable editable, int i) {
        if (editable.length() > 2 && editable.charAt(0) == '+') {
            if (editable.charAt(1) == '1') {
                i = 1;
            } else if (editable.length() >= 3 && editable.charAt(1) == '8' && editable.charAt(2) == '1') {
                i = 2;
            } else {
                i = (TelephonyFeatures.isCountrySpecific(0, "KOR") && editable.length() >= 3 && editable.charAt(1) == '8' && editable.charAt(2) == '2') ? 82 : 0;
            }
        }
        if (i == 0) {
            removeDashes(editable);
            return;
        }
        if (i == 1) {
            formatNanpNumber(editable);
            return;
        }
        if (i == 2) {
            formatJapaneseNumber(editable);
        } else if (i == 82 && TelephonyFeatures.isCountrySpecific(0, "KOR")) {
            formatKRnpNumber(editable);
        }
    }

    @Deprecated
    public static void formatNanpNumber(Editable editable) {
        int i;
        int length = editable.length();
        if (length <= 15 && length > 5) {
            CharSequence charSequenceSubSequence = editable.subSequence(0, length);
            removeDashes(editable);
            int length2 = editable.length();
            int[] iArr = new int[3];
            int i2 = 0;
            int i3 = 0;
            char c = 1;
            for (int i4 = 0; i4 < length2; i4++) {
                char cCharAt = editable.charAt(i4);
                if (cCharAt != '+') {
                    if (cCharAt != '-') {
                        switch (cCharAt) {
                            case '1':
                                if (i2 == 0 || c == 2) {
                                    c = 3;
                                    continue;
                                }
                            case '0':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                if (c == 2) {
                                    editable.replace(0, length2, charSequenceSubSequence);
                                    return;
                                }
                                if (c == 3) {
                                    i = i3 + 1;
                                    iArr[i3] = i4;
                                } else {
                                    if (c != 4 && (i2 == 3 || i2 == 6)) {
                                        i = i3 + 1;
                                        iArr[i3] = i4;
                                    }
                                    i2++;
                                    c = 1;
                                    continue;
                                }
                                i3 = i;
                                i2++;
                                c = 1;
                                continue;
                        }
                        editable.replace(0, length2, charSequenceSubSequence);
                        return;
                    }
                    c = 4;
                } else {
                    if (i4 != 0) {
                        editable.replace(0, length2, charSequenceSubSequence);
                        return;
                    }
                    c = 2;
                }
            }
            if (i2 == 7) {
                i3--;
            }
            for (int i5 = 0; i5 < i3; i5++) {
                int i6 = iArr[i5] + i5;
                editable.replace(i6, i6, NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            }
            for (int length3 = editable.length(); length3 > 0; length3--) {
                int i7 = length3 - 1;
                if (editable.charAt(i7) != '-') {
                    return;
                }
                editable.delete(i7, length3);
            }
        }
    }

    @Deprecated
    public static void formatJapaneseNumber(Editable editable) {
        JapanesePhoneNumberFormatter.format(editable);
    }

    /* JADX WARN: Removed duplicated region for block: B:142:0x01b8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void formatKRnpNumber(Editable editable) {
        char c;
        int length = editable.length();
        if (editable.toString().replace(NativeLibraryHelper.CLEAR_ABI_OVERRIDE, "").length() > 12) {
            removeDashes(editable);
            return;
        }
        int i = 2;
        if (length < 2) {
            return;
        }
        String string = editable.toString();
        int i2 = 0;
        if (length < 6 && string.endsWith(NativeLibraryHelper.CLEAR_ABI_OVERRIDE)) {
            while (i2 < editable.length()) {
                if (editable.charAt(i2) == '-') {
                    editable.delete(i2, i2 + 1);
                } else {
                    i2++;
                }
            }
            return;
        }
        int i3 = 0;
        while (i3 < editable.length()) {
            if (editable.charAt(i3) == ' ' || editable.charAt(i3) == '/') {
                editable.delete(i3, i3 + 1);
            } else {
                i3++;
            }
        }
        if (length != editable.length()) {
            length = editable.length();
        }
        if (length < 1) {
            return;
        }
        if (editable.charAt(0) == '0') {
            if (length < 2) {
                return;
            }
            if (editable.charAt(1) == '2') {
                c = 6;
            } else if (length < 3) {
                return;
            } else {
                c = string.startsWith("050") ? (char) 14 : (char) 7;
            }
        } else if (editable.charAt(0) == '*') {
            if (length < 4) {
                return;
            }
            if (string.startsWith("*23#") || string.startsWith("*22#") || string.startsWith(CLIR_ON)) {
                if (length > 5) {
                    return;
                }
                c = '\n';
            } else if (!string.startsWith("*230#") || length > 6) {
                return;
            } else {
                c = 11;
            }
        } else if (editable.charAt(0) == '#') {
            if (length < 2) {
                return;
            }
            if (editable.charAt(1) != '9') {
                if (!string.startsWith(CLIR_OFF) || length > 5) {
                    return;
                }
                c = '\n';
            } else if (length > 3) {
                return;
            } else {
                c = '\b';
            }
        } else if (editable.charAt(0) == '+') {
            if (length < 6 || length > 14) {
                return;
            } else {
                c = '\t';
            }
        } else if (length < 5 || length > 14) {
            return;
        } else {
            c = 5;
        }
        CharSequence charSequenceSubSequence = editable.subSequence(0, length);
        int i4 = 0;
        while (i4 < editable.length()) {
            if (editable.charAt(i4) == '-') {
                editable.delete(i4, i4 + 1);
            } else {
                i4++;
            }
        }
        int length2 = editable.length();
        if (editable.toString().equals("3003003000")) {
            editable.replace(0, length2, "300-300-3000");
            return;
        }
        int[] iArr = new int[2];
        if (c != 14) {
            switch (c) {
                case 5:
                    if (length2 > 3) {
                        if (length2 <= 7) {
                            iArr[0] = 3;
                        } else if (length2 > 7) {
                            iArr[0] = 4;
                        }
                        i = 1;
                        break;
                    }
                    i = 0;
                    break;
                case 6:
                    if (length2 > 2) {
                        if (length2 > 6) {
                            if (length2 > 6 && length2 <= 9) {
                                iArr[0] = 2;
                                iArr[1] = length2 - 4;
                                break;
                            } else if (length2 <= 9) {
                                i = 0;
                                break;
                            } else {
                                iArr[0] = 2;
                                iArr[1] = 6;
                                break;
                            }
                        } else {
                            iArr[0] = 2;
                            i = 1;
                            break;
                        }
                    }
                case 7:
                    if (length2 > 3) {
                        if (length2 > 7) {
                            if (length2 > 7 && length2 <= 10) {
                                iArr[0] = 3;
                                iArr[1] = length2 - 4;
                                break;
                            } else if (length2 > 10) {
                                iArr[0] = 3;
                                iArr[1] = 7;
                                break;
                            }
                        } else {
                            iArr[0] = 3;
                            i = 1;
                            break;
                        }
                    }
                    break;
                case '\b':
                    if (length2 > 2) {
                        iArr[0] = 2;
                        i = 1;
                        break;
                    }
                    i = 0;
                    break;
                case '\t':
                    if (length2 > 8) {
                        if (length2 > 8) {
                            iArr[0] = 4;
                        }
                        i = 0;
                        break;
                    } else {
                        iArr[0] = length2 - 4;
                    }
                    i = 1;
                    break;
                case '\n':
                    if (length2 > 4) {
                        iArr[0] = 4;
                        i = 1;
                        break;
                    }
                    i = 0;
                    break;
                case 11:
                    if (length2 > 5) {
                        iArr[0] = 5;
                        i = 1;
                        break;
                    }
                    i = 0;
                    break;
                default:
                    editable.replace(0, length2, charSequenceSubSequence);
                    break;
            }
            return;
        }
        if (length2 > 4) {
            if (length2 <= 8) {
                iArr[0] = 4;
                i = 1;
            } else if (length2 > 8 && length2 <= 11) {
                iArr[0] = 4;
                iArr[1] = length2 - 4;
            } else if (length2 > 11) {
                iArr[0] = 4;
                iArr[1] = 8;
            }
        }
        if (i != 0) {
            while (i2 < i) {
                int i5 = iArr[i2] + i2;
                if (i5 >= 0 && i5 <= length2) {
                    editable.replace(i5, i5, NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                }
                i2++;
            }
        }
    }

    private static void removeDashes(Editable editable) {
        int i = 0;
        while (i < editable.length()) {
            if (editable.charAt(i) == '-') {
                editable.delete(i, i + 1);
            } else {
                i++;
            }
        }
    }

    public static String formatNumberToE164(String str, String str2) {
        if (str2 != null) {
            str2 = str2.toUpperCase(Locale.ROOT);
        }
        return formatNumberInternal(str, str2, PhoneNumberUtil.PhoneNumberFormat.E164);
    }

    public static String formatNumberToRFC3966(String str, String str2) {
        if (str2 != null) {
            str2 = str2.toUpperCase(Locale.ROOT);
        }
        return formatNumberInternal(str, str2, PhoneNumberUtil.PhoneNumberFormat.RFC3966);
    }

    private static String formatNumberInternal(String str, String str2, PhoneNumberUtil.PhoneNumberFormat phoneNumberFormat) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(str, str2);
            if (phoneNumberUtil.isValidNumber(phoneNumber)) {
                return phoneNumberUtil.format(phoneNumber, phoneNumberFormat);
            }
            return null;
        } catch (MissingMetadataException e) {
            com.android.telephony.Rlog.e(LOG_TAG, "formatNumberInternal: MissingMetadataException caught " + e);
            return null;
        } catch (NumberParseException unused) {
            return null;
        }
    }

    public static boolean isInternationalNumber(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !str.startsWith("#") && !str.startsWith("*")) {
            if (str2 != null) {
                str2 = str2.toUpperCase(Locale.ROOT);
            }
            PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
            try {
                return phoneNumberUtil.parseAndKeepRawInput(str, str2).getCountryCode() != phoneNumberUtil.getCountryCodeForRegion(str2);
            } catch (NumberParseException unused) {
            } catch (MissingMetadataException e) {
                com.android.telephony.Rlog.e(LOG_TAG, "isInternationalNumber: MissingMetadataException caught " + e);
            }
        }
        return false;
    }

    public static String formatNumber(String str, String str2) {
        if (str.startsWith("#") || str.startsWith("*")) {
            return str;
        }
        if (str2 != null) {
            str2 = str2.toUpperCase(Locale.ROOT);
        }
        com.android.telephony.Rlog.v(LOG_TAG, "formatNumber: defaultCountryIso: " + str2);
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        if (TelephonyFeatures.isCountrySpecific(0, "KOR")) {
            String networkCountryIso = TelephonyManager.getDefault().getNetworkCountryIso(0);
            Locale locale = Locale.getDefault();
            try {
                if (!str.startsWith(PLUS_SIGN_STRING)) {
                    if ("ko".equals(locale.getLanguage()) || (str.startsWith("050") && "kr".equals(networkCountryIso))) {
                        return formatNumber(str, getFormatTypeFromCountryCode(KOREA_ISO_COUNTRY_CODE));
                    }
                    try {
                        return phoneNumberUtil.formatInOriginalFormat(phoneNumberUtil.parseAndKeepRawInput(str, str2), str2);
                    } catch (MissingMetadataException e) {
                        com.android.telephony.Rlog.e(LOG_TAG, "formatNumber: MissingMetadataException caught " + e);
                    }
                } else {
                    try {
                        Phonenumber.PhoneNumber andKeepRawInput = phoneNumberUtil.parseAndKeepRawInput(str, str2);
                        if (KOREA_ISO_COUNTRY_CODE.equals(str2) && andKeepRawInput.getCountryCode() == phoneNumberUtil.getCountryCodeForRegion(KOREA_ISO_COUNTRY_CODE) && andKeepRawInput.getCountryCodeSource() == Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN) {
                            return phoneNumberUtil.format(andKeepRawInput, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
                        }
                        return phoneNumberUtil.formatInOriginalFormat(andKeepRawInput, str2);
                    } catch (MissingMetadataException e2) {
                        com.android.telephony.Rlog.e(LOG_TAG, "formatNumber: MissingMetadataException caught " + e2);
                    }
                }
            } catch (NumberParseException unused) {
            }
            return null;
        }
        try {
            Phonenumber.PhoneNumber andKeepRawInput2 = phoneNumberUtil.parseAndKeepRawInput(str, str2);
            String inOriginalFormat = phoneNumberUtil.formatInOriginalFormat(andKeepRawInput2, str2);
            if (inOriginalFormat != null) {
                return inOriginalFormat;
            }
            if (Flags.nationalCountryCodeFormattingForLocalCalls()) {
                if (Arrays.asList(COUNTRY_CODES_TO_FORMAT_NATIONALLY).contains(str2) && andKeepRawInput2.getCountryCode() == phoneNumberUtil.getCountryCodeForRegion(str2) && andKeepRawInput2.getCountryCodeSource() == Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN) {
                    return phoneNumberUtil.format(andKeepRawInput2, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
                }
                return phoneNumberUtil.formatInOriginalFormat(andKeepRawInput2, str2);
            }
            if (KOREA_ISO_COUNTRY_CODE.equalsIgnoreCase(str2) && andKeepRawInput2.getCountryCode() == phoneNumberUtil.getCountryCodeForRegion(KOREA_ISO_COUNTRY_CODE) && andKeepRawInput2.getCountryCodeSource() == Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN) {
                return phoneNumberUtil.format(andKeepRawInput2, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
            }
            if (JAPAN_ISO_COUNTRY_CODE.equalsIgnoreCase(str2) && andKeepRawInput2.getCountryCode() == phoneNumberUtil.getCountryCodeForRegion(JAPAN_ISO_COUNTRY_CODE) && andKeepRawInput2.getCountryCodeSource() == Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN) {
                return phoneNumberUtil.format(andKeepRawInput2, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
            }
            if (Flags.removeCountryCodeFromLocalSingaporeCalls() && SINGAPORE_ISO_COUNTRY_CODE.equalsIgnoreCase(str2) && andKeepRawInput2.getCountryCode() == phoneNumberUtil.getCountryCodeForRegion(SINGAPORE_ISO_COUNTRY_CODE) && andKeepRawInput2.getCountryCodeSource() == Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN) {
                return phoneNumberUtil.format(andKeepRawInput2, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
            }
            return phoneNumberUtil.formatInOriginalFormat(andKeepRawInput2, str2);
        } catch (NumberParseException unused2) {
            return null;
        } catch (MissingMetadataException e3) {
            com.android.telephony.Rlog.e(LOG_TAG, "formatNumber: MissingMetadataException caught " + e3);
            return null;
        }
    }

    public static String formatNumber(String str, String str2, String str3) {
        if (str3 != null) {
            str3 = str3.toUpperCase(Locale.ROOT);
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!isDialable(str.charAt(i))) {
                return str;
            }
        }
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        if (str2 != null && str2.length() >= 2 && str2.charAt(0) == '+') {
            try {
                String regionCodeForNumber = phoneNumberUtil.getRegionCodeForNumber(phoneNumberUtil.parse(str2, "ZZ"));
                if (!TextUtils.isEmpty(regionCodeForNumber)) {
                    if (normalizeNumber(str).indexOf(str2.substring(1)) <= 0) {
                        str3 = regionCodeForNumber;
                    }
                }
            } catch (MissingMetadataException e) {
                com.android.telephony.Rlog.e(LOG_TAG, "formatNumber: MissingMetadataException caught " + e);
            } catch (NumberParseException unused) {
            }
        }
        String number = formatNumber(str, str3);
        return number != null ? number : str;
    }

    public static String normalizeNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            int iDigit = Character.digit(cCharAt, 10);
            if (iDigit != -1) {
                sb.append(iDigit);
            } else if (sb.length() == 0 && cCharAt == '+') {
                sb.append(cCharAt);
            } else if (cCharAt == '*' || cCharAt == '#') {
                sb.append(cCharAt);
            } else if ((cCharAt >= 'a' && cCharAt <= 'z') || (cCharAt >= 'A' && cCharAt <= 'Z')) {
                return normalizeNumber(convertKeypadLettersToDigits(str));
            }
        }
        return sb.toString();
    }

    public static String replaceUnicodeDigits(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        for (char c : str.toCharArray()) {
            int iDigit = Character.digit(c, 10);
            if (iDigit != -1) {
                sb.append(iDigit);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @Deprecated
    public static boolean isEmergencyNumber(String str) {
        return isEmergencyNumber(getDefaultVoiceSubId(), str);
    }

    @Deprecated
    public static boolean isEmergencyNumber(int i, String str) {
        return isEmergencyNumberInternal(i, str);
    }

    public static boolean semIsEmergencyNumber(int i, String str) {
        return isEmergencyNumber(i, str);
    }

    private static boolean isEmergencyNumberInternal(int i, String str) {
        try {
            if (TelephonyFeatures.needToCheckEmergencyNumberForEachSlot(TelephonyManager.getDefault().getSlotIndex())) {
                return TelephonyManager.getDefault().isEmergencyNumber(i, str);
            }
            return TelephonyManager.getDefault().isEmergencyNumber(str);
        } catch (RuntimeException e) {
            com.android.telephony.Rlog.e(LOG_TAG, "isEmergencyNumberInternal: RuntimeException: " + e);
            return false;
        }
    }

    @Deprecated
    public static boolean isLocalEmergencyNumber(Context context, String str) {
        return isEmergencyNumberInternal(getDefaultVoiceSubId(), str);
    }

    public static boolean isVoiceMailNumber(String str) {
        return isVoiceMailNumber(SubscriptionManager.getDefaultSubscriptionId(), str);
    }

    public static boolean isVoiceMailNumber(int i, String str) {
        return isVoiceMailNumber(null, i, str);
    }

    @SystemApi
    public static boolean isVoiceMailNumber(Context context, int i, String str) {
        TelephonyManager telephonyManagerFrom;
        CarrierConfigManager carrierConfigManager;
        PersistableBundle configForSubId;
        try {
            if (context == null) {
                telephonyManagerFrom = TelephonyManager.getDefault();
            } else {
                telephonyManagerFrom = TelephonyManager.from(context);
            }
            String voiceMailNumber = telephonyManagerFrom.getVoiceMailNumber(i);
            String line1Number = telephonyManagerFrom.getLine1Number(i);
            String strExtractNetworkPortionAlt = extractNetworkPortionAlt(str);
            if (TextUtils.isEmpty(strExtractNetworkPortionAlt)) {
                return false;
            }
            if ((context == null || (carrierConfigManager = (CarrierConfigManager) context.getSystemService("carrier_config")) == null || (configForSubId = carrierConfigManager.getConfigForSubId(i)) == null) ? false : configForSubId.getBoolean(CarrierConfigManager.KEY_MDN_IS_ADDITIONAL_VOICEMAIL_NUMBER_BOOL)) {
                return compare(strExtractNetworkPortionAlt, voiceMailNumber) || compare(strExtractNetworkPortionAlt, line1Number);
            }
            return compare(strExtractNetworkPortionAlt, voiceMailNumber);
        } catch (SecurityException unused) {
            return false;
        }
    }

    public static String convertKeypadLettersToDigits(String str) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return str;
        }
        char[] charArray = str.toCharArray();
        for (int i = 0; i < length; i++) {
            char c = charArray[i];
            charArray[i] = (char) KEYPAD_MAP.get(c, c);
        }
        return new String(charArray);
    }

    public static String cdmaCheckAndProcessPlusCode(String str) {
        return cdmaCheckAndProcessPlusCode(str, 0, null);
    }

    public static String cdmaCheckAndProcessPlusCode(String str, int i, Context context) {
        String networkCountryIso;
        String simCountryIso;
        if (TextUtils.isEmpty(str) || !isReallyDialable(str.charAt(0)) || !isNonSeparator(str)) {
            return str;
        }
        if (context != null) {
            networkCountryIso = ((TelephonyManager) context.getSystemService(TelephonyManager.class)).getNetworkCountryIso(i);
            simCountryIso = TelephonyManager.getSimCountryIsoForPhone(i);
        } else {
            networkCountryIso = TelephonyManager.getDefault().getNetworkCountryIso();
            simCountryIso = TelephonyManager.getDefault().getSimCountryIso();
        }
        return (TextUtils.isEmpty(networkCountryIso) || TextUtils.isEmpty(simCountryIso)) ? str : cdmaCheckAndProcessPlusCodeByNumberFormat(str, getFormatTypeFromCountryCode(networkCountryIso), getFormatTypeFromCountryCode(simCountryIso), i, context);
    }

    public static String cdmaCheckAndProcessPlusCodeForSms(String str) {
        if (TextUtils.isEmpty(str) || !isReallyDialable(str.charAt(0)) || !isNonSeparator(str)) {
            return str;
        }
        String simCountryIso = TelephonyManager.getDefault().getSimCountryIso();
        if (TextUtils.isEmpty(simCountryIso)) {
            return str;
        }
        int formatTypeFromCountryCode = getFormatTypeFromCountryCode(simCountryIso);
        return cdmaCheckAndProcessPlusCodeByNumberFormat(str, formatTypeFromCountryCode, formatTypeFromCountryCode);
    }

    public static String cdmaCheckAndProcessPlusCodeByNumberFormat(String str, int i, int i2) {
        return cdmaCheckAndProcessPlusCodeByNumberFormat(str, i, i2, 0, null);
    }

    public static String cdmaCheckAndProcessPlusCodeByNumberFormat(String str, int i, int i2, int i3, Context context) {
        String strExtractNetworkPortionAlt;
        String strProcessPlusCode;
        boolean z = i == i2 && i == 1;
        if (str != null && str.lastIndexOf(PLUS_SIGN_STRING) != -1) {
            String str2 = null;
            String strSubstring = str;
            while (true) {
                if (z) {
                    strExtractNetworkPortionAlt = extractNetworkPortion(strSubstring);
                } else {
                    strExtractNetworkPortionAlt = extractNetworkPortionAlt(strSubstring);
                }
                strProcessPlusCode = processPlusCode(strExtractNetworkPortionAlt, z);
                if (TextUtils.isEmpty(strProcessPlusCode)) {
                    com.android.telephony.Rlog.e("checkAndProcessPlusCode: null newDialStr", strProcessPlusCode);
                    break;
                }
                if (str2 != null) {
                    strProcessPlusCode = str2.concat(strProcessPlusCode);
                }
                String strExtractPostDialPortion = extractPostDialPortion(strSubstring);
                if (!TextUtils.isEmpty(strExtractPostDialPortion)) {
                    int iFindDialableIndexFromPostDialStr = findDialableIndexFromPostDialStr(strExtractPostDialPortion);
                    if (iFindDialableIndexFromPostDialStr >= 1) {
                        strProcessPlusCode = appendPwCharBackToOrigDialStr(iFindDialableIndexFromPostDialStr, strProcessPlusCode, strExtractPostDialPortion);
                        strSubstring = strExtractPostDialPortion.substring(iFindDialableIndexFromPostDialStr);
                    } else {
                        if (iFindDialableIndexFromPostDialStr < 0) {
                            strExtractPostDialPortion = "";
                        }
                        com.android.telephony.Rlog.e("wrong postDialStr=", strExtractPostDialPortion);
                    }
                }
                if (TextUtils.isEmpty(strExtractPostDialPortion) || TextUtils.isEmpty(strSubstring)) {
                    break;
                }
                str2 = strProcessPlusCode;
            }
            return strProcessPlusCode;
        }
        return str;
    }

    public static CharSequence createTtsSpannable(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        Spannable spannableNewSpannable = Spannable.Factory.getInstance().newSpannable(charSequence);
        addTtsSpan(spannableNewSpannable, 0, spannableNewSpannable.length());
        return spannableNewSpannable;
    }

    public static void addTtsSpan(Spannable spannable, int i, int i2) {
        spannable.setSpan(createTtsSpan(spannable.subSequence(i, i2).toString()), i, i2, 33);
    }

    @Deprecated
    public static CharSequence ttsSpanAsPhoneNumber(CharSequence charSequence) {
        return createTtsSpannable(charSequence);
    }

    @Deprecated
    public static void ttsSpanAsPhoneNumber(Spannable spannable, int i, int i2) {
        addTtsSpan(spannable, i, i2);
    }

    public static TtsSpan createTtsSpan(String str) {
        Phonenumber.PhoneNumber phoneNumber = null;
        if (str == null) {
            return null;
        }
        try {
            phoneNumber = PhoneNumberUtil.getInstance().parse(str, (String) null);
        } catch (NumberParseException unused) {
        } catch (MissingMetadataException e) {
            com.android.telephony.Rlog.e(LOG_TAG, "createTtsSpan: MissingMetadataException caught " + e);
        }
        TtsSpan.TelephoneBuilder telephoneBuilder = new TtsSpan.TelephoneBuilder();
        if (phoneNumber == null) {
            telephoneBuilder.setNumberParts(splitAtNonNumerics(str));
        } else {
            if (phoneNumber.hasCountryCode()) {
                telephoneBuilder.setCountryCode(Integer.toString(phoneNumber.getCountryCode()));
            }
            telephoneBuilder.setNumberParts(Long.toString(phoneNumber.getNationalNumber()));
        }
        return telephoneBuilder.build();
    }

    private static String splitAtNonNumerics(CharSequence charSequence) {
        StringBuilder sb = new StringBuilder(charSequence.length());
        int i = 0;
        while (true) {
            Object objValueOf = " ";
            if (i < charSequence.length()) {
                if (is12Key(charSequence.charAt(i))) {
                    objValueOf = Character.valueOf(charSequence.charAt(i));
                }
                sb.append(objValueOf);
                i++;
            } else {
                return sb.toString().replaceAll(" +", " ").trim();
            }
        }
    }

    private static String getCurrentIdp(boolean z) {
        if (z) {
            return NANP_IDP_STRING;
        }
        return TelephonyProperties.operator_idp_string().orElse(PLUS_SIGN_STRING);
    }

    private static int getFormatTypeFromCountryCode(String str) {
        if (TelephonyFeatures.isCountrySpecific(0, "KOR") && KOREA_ISO_COUNTRY_CODE.compareToIgnoreCase(str) == 0) {
            return 82;
        }
        int length = NANP_COUNTRIES.length;
        for (int i = 0; i < length; i++) {
            if (NANP_COUNTRIES[i].compareToIgnoreCase(str) == 0) {
                return 1;
            }
        }
        return "jp".compareToIgnoreCase(str) == 0 ? 2 : 0;
    }

    public static boolean isNanp(String str) {
        if (str != null) {
            if (str.length() != 10 || !isTwoToNine(str.charAt(0)) || !isTwoToNine(str.charAt(3))) {
                return false;
            }
            for (int i = 1; i < 10; i++) {
                if (!isISODigit(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        com.android.telephony.Rlog.e("isNanp: null dialStr passed in", str);
        return false;
    }

    private static boolean isOneNanp(String str) {
        if (str != null) {
            return str.charAt(0) == '1' && isNanp(str.substring(1));
        }
        com.android.telephony.Rlog.e("isOneNanp: null dialStr passed in", str);
        return false;
    }

    @SystemApi
    public static boolean isUriNumber(String str) {
        if (str != null) {
            return str.contains("@") || str.contains("%40");
        }
        return false;
    }

    @SystemApi
    public static String getUsernameFromUriNumber(String str) {
        int iIndexOf = str.indexOf(64);
        if (iIndexOf < 0) {
            iIndexOf = str.indexOf("%40");
        }
        if (iIndexOf < 0) {
            com.android.telephony.Rlog.w(LOG_TAG, "getUsernameFromUriNumber: no delimiter found in SIP addr '" + SemTelephonyUtils.maskPii(str) + "'");
            iIndexOf = str.length();
        }
        return str.substring(0, iIndexOf);
    }

    public static Uri convertSipUriToTelUri(Uri uri) {
        if ("sip".equals(uri.getScheme())) {
            String[] strArrSplit = uri.getSchemeSpecificPart().split("[@;:]");
            if (strArrSplit.length != 0) {
                return Uri.fromParts(PhoneAccount.SCHEME_TEL, strArrSplit[0], null);
            }
        }
        return uri;
    }

    private static String processPlusCode(String str, boolean z) {
        if (str == null || str.charAt(0) != '+' || str.length() <= 1) {
            return str;
        }
        String strSubstring = str.substring(1);
        if (z && isOneNanp(strSubstring)) {
            log("processPlusCode - Remove the leading plus sign");
            return strSubstring;
        }
        String strReplaceFirst = str.replaceFirst("[+]", getCurrentIdp(z));
        log("processPlusCode - Replaces the plus sign with the default IDP. useNanp: " + z + ", current IDP: " + getCurrentIdp(z));
        return strReplaceFirst;
    }

    private static int findDialableIndexFromPostDialStr(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (isReallyDialable(str.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    private static String appendPwCharBackToOrigDialStr(int i, String str, String str2) {
        if (i == 1) {
            return str + str2.charAt(0);
        }
        return str.concat(str2.substring(0, i));
    }

    private static boolean matchIntlPrefix(String str, int i) {
        char c = 0;
        for (int i2 = 0; i2 < i; i2++) {
            char cCharAt = str.charAt(i2);
            if (c != 0) {
                if (c != 2) {
                    if (c != 4) {
                        if (isNonSeparator(cCharAt)) {
                            return false;
                        }
                    } else if (cCharAt == '1') {
                        c = 5;
                    } else if (isNonSeparator(cCharAt)) {
                        return false;
                    }
                } else if (cCharAt == '0') {
                    c = 3;
                } else if (cCharAt == '1') {
                    c = 4;
                } else if (isNonSeparator(cCharAt)) {
                    return false;
                }
            } else if (cCharAt == '+') {
                c = 1;
            } else if (cCharAt == '0') {
                c = 2;
            } else if (isNonSeparator(cCharAt)) {
                return false;
            }
        }
        return c == 1 || c == 3 || c == 5;
    }

    private static boolean matchIntlPrefixAndCC(String str, int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            char cCharAt = str.charAt(i3);
            switch (i2) {
                case 0:
                    if (cCharAt == '+') {
                        i2 = 1;
                        break;
                    } else if (cCharAt == '0') {
                        i2 = 2;
                        break;
                    } else {
                        if (isNonSeparator(cCharAt)) {
                            return false;
                        }
                        break;
                    }
                case 1:
                case 3:
                case 5:
                    if (isISODigit(cCharAt)) {
                        i2 = 6;
                        break;
                    } else {
                        if (isNonSeparator(cCharAt)) {
                            return false;
                        }
                        break;
                    }
                case 2:
                    if (cCharAt == '0') {
                        i2 = 3;
                        break;
                    } else if (cCharAt == '1') {
                        i2 = 4;
                        break;
                    } else {
                        if (isNonSeparator(cCharAt)) {
                            return false;
                        }
                        break;
                    }
                case 4:
                    if (cCharAt == '1') {
                        i2 = 5;
                        break;
                    } else {
                        if (isNonSeparator(cCharAt)) {
                            return false;
                        }
                        break;
                    }
                case 6:
                case 7:
                    if (isISODigit(cCharAt)) {
                        i2++;
                        break;
                    } else {
                        if (isNonSeparator(cCharAt)) {
                            return false;
                        }
                        break;
                    }
                default:
                    if (isNonSeparator(cCharAt)) {
                        return false;
                    }
                    break;
            }
        }
        return i2 == 6 || i2 == 7 || i2 == 8;
    }

    private static boolean matchTrunkPrefix(String str, int i) {
        boolean z = false;
        for (int i2 = 0; i2 < i; i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '0' && !z) {
                z = true;
            } else if (isNonSeparator(cCharAt)) {
                return false;
            }
        }
        return z;
    }

    private static boolean isCountryCallingCode(int i) {
        return i > 0 && i < CCC_LENGTH && COUNTRY_CALLING_CALL[i];
    }

    private static class CountryCallingCodeAndNewIndex {
        public final int countryCallingCode;
        public final int newIndex;

        public CountryCallingCodeAndNewIndex(int i, int i2) {
            this.countryCallingCode = i;
            this.newIndex = i2;
        }
    }

    private static CountryCallingCodeAndNewIndex tryGetCountryCallingCodeAndNewIndex(String str, boolean z) {
        int length = str.length();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = str.charAt(i3);
            switch (i) {
                case 0:
                    if (cCharAt == '+') {
                        i = 1;
                        break;
                    } else if (cCharAt == '0') {
                        i = 2;
                        break;
                    } else if (cCharAt == '1') {
                        if (!z) {
                            return null;
                        }
                        i = 8;
                        break;
                    } else {
                        if (isDialable(cCharAt)) {
                            return null;
                        }
                        break;
                    }
                case 1:
                case 3:
                case 5:
                case 6:
                case 7:
                    int iTryGetISODigit = tryGetISODigit(cCharAt);
                    if (iTryGetISODigit > 0) {
                        i2 = (i2 * 10) + iTryGetISODigit;
                        if (i2 >= 100 || isCountryCallingCode(i2)) {
                            return new CountryCallingCodeAndNewIndex(i2, i3 + 1);
                        }
                        if (i != 1 && i != 3 && i != 5) {
                            i++;
                            break;
                        } else {
                            i = 6;
                            break;
                        }
                    } else {
                        if (isDialable(cCharAt)) {
                            return null;
                        }
                        break;
                    }
                    break;
                case 2:
                    if (cCharAt == '0') {
                        i = 3;
                        break;
                    } else if (cCharAt == '1') {
                        i = 4;
                        break;
                    } else {
                        if (isDialable(cCharAt)) {
                            return null;
                        }
                        break;
                    }
                case 4:
                    if (cCharAt == '1') {
                        i = 5;
                        break;
                    } else {
                        if (isDialable(cCharAt)) {
                            return null;
                        }
                        break;
                    }
                case 8:
                    if (cCharAt == '6') {
                        i = 9;
                        break;
                    } else {
                        if (isDialable(cCharAt)) {
                            return null;
                        }
                        break;
                    }
                case 9:
                    if (cCharAt == '6') {
                        return new CountryCallingCodeAndNewIndex(66, i3 + 1);
                    }
                    return null;
                default:
                    return null;
            }
        }
        return null;
    }

    private static int tryGetTrunkPrefixOmittedIndex(String str, int i) {
        int length = str.length();
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (tryGetISODigit(cCharAt) >= 0) {
                return i + 1;
            }
            if (isDialable(cCharAt)) {
                return -1;
            }
            i++;
        }
        return -1;
    }

    private static boolean checkPrefixIsIgnorable(String str, int i, int i2) {
        boolean z = false;
        while (i2 >= i) {
            if (tryGetISODigit(str.charAt(i2)) >= 0) {
                if (z) {
                    return false;
                }
                z = true;
            } else if (isDialable(str.charAt(i2))) {
                return false;
            }
            i2--;
        }
        return true;
    }

    private static int getDefaultVoiceSubId() {
        return SubscriptionManager.getDefaultVoiceSubscriptionId();
    }

    public static String convertToEmergencyNumber(Context context, String str) {
        String[] strArrSplit;
        if (context != null && !TextUtils.isEmpty(str)) {
            String strNormalizeNumber = normalizeNumber(str);
            if (!isEmergencyNumber(strNormalizeNumber)) {
                if (sConvertToEmergencyMap == null) {
                    sConvertToEmergencyMap = context.getResources().getStringArray(R.array.config_convert_to_emergency_number_map);
                }
                String[] strArr = sConvertToEmergencyMap;
                if (strArr != null && strArr.length != 0) {
                    for (String str2 : strArr) {
                        String str3 = null;
                        String[] strArrSplit2 = !TextUtils.isEmpty(str2) ? str2.split(":") : null;
                        if (strArrSplit2 == null || strArrSplit2.length != 2) {
                            strArrSplit = null;
                        } else {
                            String str4 = strArrSplit2[1];
                            strArrSplit = TextUtils.isEmpty(strArrSplit2[0]) ? null : strArrSplit2[0].split(",");
                            str3 = str4;
                        }
                        if (!TextUtils.isEmpty(str3) && strArrSplit != null && strArrSplit.length != 0) {
                            for (String str5 : strArrSplit) {
                                if (!TextUtils.isEmpty(str5) && str5.equals(strNormalizeNumber)) {
                                    return str3;
                                }
                            }
                        }
                    }
                }
            }
        }
        return str;
    }

    public static boolean areSamePhoneNumber(String str, String str2, String str3) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        if (str3 != null) {
            str3 = str3.toUpperCase(Locale.ROOT);
        }
        try {
            Phonenumber.PhoneNumber andKeepRawInput = phoneNumberUtil.parseAndKeepRawInput(str, str3);
            Phonenumber.PhoneNumber andKeepRawInput2 = phoneNumberUtil.parseAndKeepRawInput(str2, str3);
            PhoneNumberUtil.MatchType matchTypeIsNumberMatch = phoneNumberUtil.isNumberMatch(andKeepRawInput, andKeepRawInput2);
            if (matchTypeIsNumberMatch == PhoneNumberUtil.MatchType.EXACT_MATCH || matchTypeIsNumberMatch == PhoneNumberUtil.MatchType.NSN_MATCH) {
                return true;
            }
            return matchTypeIsNumberMatch == PhoneNumberUtil.MatchType.SHORT_NSN_MATCH && andKeepRawInput.getNationalNumber() == andKeepRawInput2.getNationalNumber() && andKeepRawInput.getCountryCode() == andKeepRawInput2.getCountryCode();
        } catch (MissingMetadataException e) {
            com.android.telephony.Rlog.e(LOG_TAG, "areSamePhoneNumber: MissingMetadataException caught " + e);
            return false;
        } catch (NumberParseException unused) {
            return false;
        }
    }

    public static boolean isWpsCallNumber(String str) {
        if (str != null) {
            return str.startsWith(PREFIX_WPS) || str.startsWith(PREFIX_WPS_CLIR_ACTIVATE) || str.startsWith(PREFIX_WPS_CLIR_DEACTIVATE);
        }
        return false;
    }

    private static int charToBCD(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c == '*') {
            return 10;
        }
        if (c == '#') {
            return 11;
        }
        if (c == ',') {
            return 12;
        }
        if (c == 'N') {
            return 13;
        }
        if (c == ';') {
            return 14;
        }
        throw new RuntimeException("invalid char for BCD " + c);
    }

    private static byte[] DocomoNumberToCalledPartyBCDHelper(String str, boolean z) {
        int length = str.length();
        char c = 0;
        boolean z2 = str.indexOf(43) != -1;
        boolean z3 = str.indexOf(35) != -1;
        boolean z4 = str.indexOf(42) != -1;
        int i = z2 ? length - 1 : length;
        if (i == 0) {
            return null;
        }
        int i2 = (i + 1) / 2;
        int i3 = z ? 2 : 1;
        int i4 = i2 + i3;
        byte[] bArr = new byte[i4];
        int i5 = 0;
        for (int i6 = 0; i6 < length; i6++) {
            char cCharAt = str.charAt(i6);
            if (cCharAt != '+') {
                int i7 = (i5 >> 1) + i3;
                bArr[i7] = (byte) (bArr[i7] | ((byte) ((charToBCD(cCharAt) & 15) << ((i5 & 1) == 1 ? 4 : 0))));
                i5++;
            }
        }
        if ((i5 & 1) == 1) {
            int i8 = i3 + (i5 >> 1);
            bArr[i8] = (byte) (bArr[i8] | 240);
        }
        if (z) {
            bArr[0] = (byte) (i4 - 1);
            c = 1;
        }
        byte b = (byte) (z2 ? 145 : 129);
        bArr[c] = b;
        if (z3 || z4) {
            bArr[c] = (byte) (b & 240);
        }
        log("TOA: " + ((int) bArr[c]));
        return bArr;
    }

    public static byte[] docomoNetworkPortionToCalledPartyBCD(String str) {
        return DocomoNumberToCalledPartyBCDHelper(extractNetworkPortion(str), false);
    }

    private static boolean startWithCountryCode(String str, Context context) {
        if (str.length() == 12 && (str.startsWith("7") || str.startsWith("20") || str.startsWith("65") || str.startsWith("90"))) {
            log("length 12 - 7,20,65,90 is detected");
            return false;
        }
        if (str.length() >= 11) {
            if (str.startsWith("1")) {
                log("US country code is detected with more than 11 digits");
                return false;
            }
            Cursor cursorQuery = context.getContentResolver().query(MCC_OTA_URI, null, null, null, null);
            mCursorCountry = cursorQuery;
            if (cursorQuery != null) {
                cursorQuery.moveToFirst();
                while (!mCursorCountry.isAfterLast()) {
                    if (str.startsWith(mCursorCountry.getString(6))) {
                        log("contry code is detected");
                        mCursorCountry.close();
                        return true;
                    }
                    mCursorCountry.moveToNext();
                }
                mCursorCountry.close();
            }
        }
        return false;
    }

    public static String convertSMSDestinationAddress(String str, Context context, int i) {
        String strExtractNetworkPortion = extractNetworkPortion(str);
        int length = strExtractNetworkPortion.length();
        char cCharAt = strExtractNetworkPortion.charAt(0);
        if (!isISODigit(cCharAt) && '+' != cCharAt) {
            com.android.telephony.Rlog.d(LOG_TAG, "SMS Destination Number might be email address" + cCharAt);
            return strExtractNetworkPortion;
        }
        com.android.telephony.Rlog.d(LOG_TAG, "SMS Destination Number is OK " + cCharAt);
        try {
            int defaultSubscriptionId = SubscriptionManager.getDefaultSubscriptionId();
            if (!retrieveAssistedParams(defaultSubscriptionId, strExtractNetworkPortion, ((TelephonyManager) context.getSystemService("phone")).getLine1Number(defaultSubscriptionId), context)) {
                return strExtractNetworkPortion;
            }
            StringBuilder sb = new StringBuilder(128);
            boolean zStartsWith = strExtractNetworkPortion.startsWith(otaCountryIDDPrefix);
            boolean zStartsWith2 = strExtractNetworkPortion.startsWith(NANP_IDP_STRING);
            String strSubstring = strExtractNetworkPortion.substring(0, 5);
            StringBuilder sb2 = new StringBuilder("SMS Destination numberLength: ");
            sb2.append(length);
            sb2.append(" numberBeginsWithOTAIDDPrefix: ");
            sb2.append(zStartsWith);
            sb2.append(" numberBeginsWithNonUSIDDPrefix: ");
            sb2.append(!zStartsWith2);
            sb2.append(" otaCountryIDDPrefix: ");
            sb2.append(otaCountryIDDPrefix);
            sb2.append(" number : ");
            sb2.append(strSubstring);
            sb2.append("**********");
            com.android.telephony.Rlog.d(LOG_TAG, sb2.toString());
            if (i == 1) {
                isCDMARegistered = true;
                isGSMRegistered = false;
                isNetRoaming = false;
            } else if (i == 2) {
                isCDMARegistered = true;
                isGSMRegistered = false;
                isNetRoaming = true;
            } else if (i == 3) {
                isCDMARegistered = false;
                isGSMRegistered = true;
            }
            if (isCDMARegistered) {
                if (!isNetRoaming) {
                    com.android.telephony.Rlog.d(LOG_TAG, "Address Rule in VZW Network");
                    if (!isISODigit(cCharAt) || zStartsWith2 || length < 11 || ((length == 11 && '1' == cCharAt) || !startWithCountryCode(strExtractNetworkPortion, context))) {
                        return cdmaCheckAndProcessPlusCodeByNumberFormat(strExtractNetworkPortion, 1, 1);
                    }
                    sb.append(NANP_IDP_STRING);
                    sb.append(strExtractNetworkPortion);
                    return sb.toString();
                }
                com.android.telephony.Rlog.d(LOG_TAG, "Address Rule in CDMA Internatinal Roaming");
                if ((isISODigit(cCharAt) || '+' == cCharAt) && length >= 11 && (length != 11 || '1' != cCharAt)) {
                    String strSubstring2 = strExtractNetworkPortion.substring(length - 11);
                    String strSubstring3 = zStartsWith ? strExtractNetworkPortion.substring(otaCountryIDDPrefix.length(), strExtractNetworkPortion.length()) : null;
                    if (zStartsWith) {
                        int length2 = otaCountryIDDPrefix.length();
                        String strSubstring4 = strExtractNetworkPortion.substring(length2);
                        if (isOneNanp(strSubstring2) && strExtractNetworkPortion.length() == length2 + 11) {
                            sb.append(strSubstring4);
                        } else if (startWithCountryCode(strSubstring3, context)) {
                            com.android.telephony.Rlog.d(LOG_TAG, "Found Country Code after IDD");
                            sb.append(strExtractNetworkPortion);
                            sb.replace(0, length2, NANP_IDP_STRING);
                        } else {
                            com.android.telephony.Rlog.d(LOG_TAG, "No Condition");
                            sb.append(strExtractNetworkPortion);
                        }
                        return sb.toString();
                    }
                    if ('+' == cCharAt) {
                        String strSubstring5 = strExtractNetworkPortion.substring(1);
                        if (isOneNanp(strSubstring5) && strExtractNetworkPortion.length() == 12) {
                            sb.append(strSubstring5);
                        } else if (startWithCountryCode(strSubstring5, context)) {
                            sb.append(NANP_IDP_STRING);
                            sb.append(strSubstring5);
                        } else {
                            com.android.telephony.Rlog.d(LOG_TAG, "1NANP is not matched");
                            sb.append(strExtractNetworkPortion);
                        }
                        return sb.toString();
                    }
                    if (startWithCountryCode(strExtractNetworkPortion, context)) {
                        sb.append(NANP_IDP_STRING);
                        sb.append(strExtractNetworkPortion);
                        return sb.toString();
                    }
                }
            }
            if (isGSMRegistered) {
                com.android.telephony.Rlog.d(LOG_TAG, "Address Rule in GSM/UMTS");
                if ((isISODigit(cCharAt) || '+' == cCharAt) && length >= 11 && (length != 11 || '1' != cCharAt)) {
                    String strSubstring6 = strExtractNetworkPortion.substring(length - 11);
                    String strSubstring7 = zStartsWith ? strExtractNetworkPortion.substring(otaCountryIDDPrefix.length(), strExtractNetworkPortion.length()) : null;
                    if (zStartsWith) {
                        int length3 = otaCountryIDDPrefix.length();
                        if (isOneNanp(strExtractNetworkPortion.substring(length3)) && strExtractNetworkPortion.length() == length3 + 11) {
                            sb.append(strExtractNetworkPortion);
                            sb.replace(0, otaCountryIDDPrefix.length(), PLUS_SIGN_STRING);
                            return sb.toString();
                        }
                        if (startWithCountryCode(strSubstring7, context)) {
                            sb.append(strExtractNetworkPortion);
                            sb.replace(0, otaCountryIDDPrefix.length(), NANP_IDP_STRING);
                            return sb.toString();
                        }
                        com.android.telephony.Rlog.d(LOG_TAG, "No condition is matched in IDD");
                        sb.append(strExtractNetworkPortion);
                        return sb.toString();
                    }
                    if ('+' == cCharAt) {
                        String strSubstring8 = strExtractNetworkPortion.substring(1);
                        if (isOneNanp(strSubstring6) && strExtractNetworkPortion.length() == 12) {
                            sb.append(strExtractNetworkPortion);
                        } else if (startWithCountryCode(strSubstring8, context)) {
                            String strSubstring9 = strExtractNetworkPortion.substring(1);
                            sb.append(NANP_IDP_STRING);
                            sb.append(strSubstring9);
                        } else if (strSubstring8.startsWith(NANP_IDP_STRING)) {
                            sb.append(strSubstring8);
                        } else {
                            com.android.telephony.Rlog.d(LOG_TAG, "No condition is matched in '+'");
                            sb.append(strExtractNetworkPortion);
                        }
                        return sb.toString();
                    }
                    if (startWithCountryCode(strExtractNetworkPortion, context)) {
                        sb.append(NANP_IDP_STRING);
                        sb.append(strExtractNetworkPortion);
                        return sb.toString();
                    }
                }
            }
            com.android.telephony.Rlog.d(LOG_TAG, "Can't find any match in this number");
            return strExtractNetworkPortion;
        } catch (Exception e) {
            com.android.telephony.Rlog.d(LOG_TAG, "Cannot convert: " + e);
            return strExtractNetworkPortion;
        }
    }

    private static boolean retrieveAssistedParams(int i, String str, String str2, Context context) {
        String str3;
        if ("LRA".equals(TelephonyFeatures.getSubOperatorName(SubscriptionManager.getPhoneId(i)))) {
            adLog("Assisted Dial not supported");
            return false;
        }
        numberLength = extractNetworkPortionAlt(str).length();
        isNetRoaming = ((TelephonyManager) context.getSystemService("phone")).isNetworkRoaming(i);
        int currentPhoneType = ((TelephonyManager) context.getSystemService("phone")).getCurrentPhoneType(i);
        if (TextUtils.isEmpty(str2) || str2.length() < 3) {
            adLog("Wrong MDN");
            return false;
        }
        try {
            Cursor cursorQuery = context.getContentResolver().query(REF_COUNTRY_SHARED_PREF, null, null, null, null);
            mCursor = cursorQuery;
            if (cursorQuery == null) {
                adLog("Invalid Reference Country");
                return false;
            }
            cursorQuery.moveToFirst();
            refCountryName = mCursor.getString(1);
            String string = mCursor.getString(2);
            String str4 = "430";
            if (string.equals("310 to 316")) {
                string = "310";
            } else if (string.equals("430 to 431")) {
                string = "430";
            }
            refCountryMCC = string;
            refCountryIDDPrefix = mCursor.getString(3);
            refCountryNDDPrefix = mCursor.getString(4);
            isNANPCountry = mCursor.getString(5).equals("NANP");
            refCountryCountryCode = mCursor.getString(6);
            String string2 = mCursor.getString(8);
            refCountryAreaCode = string2;
            if (string2 != null) {
                str3 = str2;
            } else if (str2.length() >= 3) {
                str3 = str2;
                refCountryAreaCode = str3.substring(0, 3);
            } else {
                str3 = str2;
                adLog("Wrong MDN. Use default reference country area code");
                refCountryAreaCode = "123";
            }
            String string3 = mCursor.getString(9);
            if (str3.length() >= 3) {
                refCountryNationalNumberLength = str3.length();
            } else {
                refCountryNationalNumberLength = 10;
            }
            adLog("refCountryNationalNumberLength - MDN length: " + str3.length() + ", DB: " + string3);
            if (!TextUtils.isEmpty(string3)) {
                try {
                    refCountryNationalNumberLength = Integer.parseInt(string3);
                } catch (NumberFormatException unused) {
                    adLog("Can't parse the NationalNumberLength as integer");
                }
            }
            adLog("refCountryMCC: " + refCountryMCC);
            Cursor cursor = mCursor;
            if (cursor != null) {
                cursor.close();
            }
            isGSMRegistered = currentPhoneType == 1;
            isCDMARegistered = currentPhoneType == 2;
            try {
                Cursor otaCountry = getOtaCountry(i, context, true);
                mCursor = otaCountry;
                otaCountryMCC = null;
                if (otaCountry != null && otaCountry.moveToFirst()) {
                    otaCountryName = mCursor.getString(1);
                    otaCountryMCC = mCursor.getString(2);
                    otaCountryIDDPrefix = mCursor.getString(3);
                    String string4 = mCursor.getString(4);
                    otaCountryNDDPrefix = string4;
                    if (string4 == null) {
                        otaCountryNDDPrefix = "";
                    }
                    isOTANANPCountry = mCursor.getString(5).equals("NANP");
                    otaCountryCountryCode = mCursor.getString(6);
                    if (otaCountryMCC.equals("310 to 316")) {
                        str4 = "310";
                    } else if (!otaCountryMCC.equals("430 to 431")) {
                        str4 = otaCountryMCC;
                    }
                    otaCountryMCC = str4;
                }
                Cursor cursor2 = mCursor;
                if (cursor2 != null) {
                    cursor2.close();
                }
                if (otaCountryMCC == null) {
                    adLog("OTA country not found");
                    return false;
                }
                displayAssistedParams();
                return true;
            } finally {
                Cursor cursor3 = mCursor;
                if (cursor3 != null) {
                    cursor3.close();
                }
            }
        } finally {
            Cursor cursor4 = mCursor;
            if (cursor4 != null) {
                cursor4.close();
            }
        }
    }

    private static void displayAssistedParams() {
        adLog("refCountryName: (" + refCountryName + "), refCountryMCC: " + refCountryMCC + ", refCountryIDDPrefix: " + refCountryIDDPrefix + ", refCountryNDDPrefix: " + refCountryNDDPrefix + ", refCountryAreaCode: " + refCountryAreaCode + ", refCountryNationalNumberLength: " + refCountryNationalNumberLength + ", isNANPCountry: " + isNANPCountry + ", refCountryCountryCode: " + refCountryCountryCode + ", isGSMRegistered: " + isGSMRegistered + ", isCDMARegistered: " + isCDMARegistered);
        StringBuilder sb = new StringBuilder("isNetRoaming: ");
        sb.append(isNetRoaming);
        sb.append(", numberLength: ");
        sb.append(numberLength);
        sb.append(", otaCountryName: (");
        sb.append(otaCountryName);
        sb.append("), otaCountryMCC: ");
        sb.append(otaCountryMCC);
        sb.append(", otaCountryIDDPrefix: ");
        sb.append(otaCountryIDDPrefix);
        sb.append(", otaCountryNDDPrefix: ");
        sb.append(otaCountryNDDPrefix);
        sb.append(", isOTANANPCountry: ");
        sb.append(isOTANANPCountry);
        sb.append(", otaCountryCountryCode: ");
        sb.append(otaCountryCountryCode);
        adLog(sb.toString());
    }

    private static void adLog(String str) {
        com.android.telephony.Rlog.d("AssistedDialing", str);
    }

    private static Cursor getOtaCountry(int i, Context context, boolean z) {
        String string = PreferenceManager.getDefaultSharedPreferences(context).getString(OTA_COUNTRY_MCC_KEY, null);
        ContentResolver contentResolver = context.getContentResolver();
        if (!z || string == null) {
            return contentResolver.query(OTA_COUNTRY_URI.buildUpon().fragment(String.valueOf(i)).build(), null, null, null, null);
        }
        return contentResolver.query(MCC_OTA_URI, null, "mcc=?", new String[]{string}, null);
    }
}
