package android.telephony;

import android.annotation.SystemApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.provider.CallLog;
import android.sysprop.TelephonyProperties;
import android.telecom.PhoneAccount;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TtsSpan;
import android.util.SparseIntArray;

import com.android.i18n.phonenumbers.NumberParseException;
import com.android.i18n.phonenumbers.PhoneNumberUtil;
import com.android.i18n.phonenumbers.Phonenumber;
import com.android.internal.R;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.telephony.SemTelephonyUtils;
import com.android.internal.telephony.TelephonyFeatures;
import com.android.internal.telephony.flags.Flags;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static final String JAPAN_ISO_COUNTRY_CODE = "JP";
    private static final String KOREA_ISO_COUNTRY_CODE = "KR";
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
    private static final String SINGAPORE_ISO_COUNTRY_CODE = "SG";
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
    private static final String[] NANP_COUNTRIES = {
        "US", "CA", "AS", "AI", "AG", "BS", "BB", "BM", "VG", "KY", "DM", "DO", "GD", "GU", "JM",
        "PR", "MS", "MP", "KN", "LC", "VC", "TT", "TC", "VI"
    };
    private static final SparseIntArray KEYPAD_MAP = new SparseIntArray();

    @Retention(RetentionPolicy.SOURCE)
    public @interface BcdExtendType {}

    static {
        KEYPAD_MAP.put(97, 50);
        KEYPAD_MAP.put(98, 50);
        KEYPAD_MAP.put(99, 50);
        KEYPAD_MAP.put(65, 50);
        KEYPAD_MAP.put(66, 50);
        KEYPAD_MAP.put(67, 50);
        KEYPAD_MAP.put(100, 51);
        KEYPAD_MAP.put(101, 51);
        KEYPAD_MAP.put(102, 51);
        KEYPAD_MAP.put(68, 51);
        KEYPAD_MAP.put(69, 51);
        KEYPAD_MAP.put(70, 51);
        KEYPAD_MAP.put(103, 52);
        KEYPAD_MAP.put(104, 52);
        KEYPAD_MAP.put(105, 52);
        KEYPAD_MAP.put(71, 52);
        KEYPAD_MAP.put(72, 52);
        KEYPAD_MAP.put(73, 52);
        KEYPAD_MAP.put(106, 53);
        KEYPAD_MAP.put(107, 53);
        KEYPAD_MAP.put(108, 53);
        KEYPAD_MAP.put(74, 53);
        KEYPAD_MAP.put(75, 53);
        KEYPAD_MAP.put(76, 53);
        KEYPAD_MAP.put(109, 54);
        KEYPAD_MAP.put(110, 54);
        KEYPAD_MAP.put(111, 54);
        KEYPAD_MAP.put(77, 54);
        KEYPAD_MAP.put(78, 54);
        KEYPAD_MAP.put(79, 54);
        KEYPAD_MAP.put(112, 55);
        KEYPAD_MAP.put(113, 55);
        KEYPAD_MAP.put(114, 55);
        KEYPAD_MAP.put(115, 55);
        KEYPAD_MAP.put(80, 55);
        KEYPAD_MAP.put(81, 55);
        KEYPAD_MAP.put(82, 55);
        KEYPAD_MAP.put(83, 55);
        KEYPAD_MAP.put(116, 56);
        KEYPAD_MAP.put(117, 56);
        KEYPAD_MAP.put(118, 56);
        KEYPAD_MAP.put(84, 56);
        KEYPAD_MAP.put(85, 56);
        KEYPAD_MAP.put(86, 56);
        KEYPAD_MAP.put(119, 57);
        KEYPAD_MAP.put(120, 57);
        KEYPAD_MAP.put(121, 57);
        KEYPAD_MAP.put(122, 57);
        KEYPAD_MAP.put(87, 57);
        KEYPAD_MAP.put(88, 57);
        KEYPAD_MAP.put(89, 57);
        KEYPAD_MAP.put(90, 57);
        COUNTRY_CALLING_CALL =
                new boolean[] {
                    true, true, false, false, false, false, false, true, false, false, false, false,
                    false, false, false, false, false, false, false, false, true, false, false,
                    false, false, false, false, true, true, false, true, true, true, true, true,
                    false, true, false, false, true, true, false, false, true, true, true, true,
                    true, true, true, false, true, true, true, true, true, true, true, true, false,
                    true, true, true, true, true, true, true, false, false, false, false, false,
                    false, false, false, false, false, false, false, false, false, true, true, true,
                    true, false, true, false, false, true, true, true, true, true, true, true,
                    false, false, true, false
                };
        CCC_LENGTH = COUNTRY_CALLING_CALL.length;
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

    public static boolean isISODigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static final boolean is12Key(char c) {
        return (c >= '0' && c <= '9') || c == '*' || c == '#';
    }

    public static final boolean isDialable(char c) {
        return (c >= '0' && c <= '9') || c == '*' || c == '#' || c == '+' || c == 'N';
    }

    public static final boolean isReallyDialable(char c) {
        return (c >= '0' && c <= '9') || c == '*' || c == '#' || c == '+';
    }

    public static final boolean isNonSeparator(char c) {
        return (c >= '0' && c <= '9')
                || c == '*'
                || c == '#'
                || c == '+'
                || c == 'N'
                || c == ';'
                || c == ',';
    }

    public static final boolean isStartsPostDial(char c) {
        return c == ',' || c == ';';
    }

    private static boolean isPause(char c) {
        return c == 'p' || c == 'P';
    }

    private static boolean isToneWait(char c) {
        return c == 'w' || c == 'W';
    }

    private static int getMinMatch() {
        if (sMinMatch == 0) {
            sMinMatch =
                    Resources.getSystem()
                            .getInteger(R.integer.config_phonenumber_compare_min_match);
        }
        return sMinMatch;
    }

    public static int getMinMatchForTest() {
        return getMinMatch();
    }

    public static void setMinMatchForTest(int minMatch) {
        sMinMatch = minMatch;
    }

    private static boolean isSeparator(char ch) {
        return !isDialable(ch) && ('a' > ch || ch > 'z') && ('A' > ch || ch > 'Z');
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0081, code lost:

       return r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x007e, code lost:

       if (r12 == null) goto L37;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getNumberFromIntent(
            android.content.Intent r13, android.content.Context r14) {
        /*
            r0 = 0
            android.net.Uri r7 = r13.getData()
            r1 = 0
            if (r7 != 0) goto L9
            return r1
        L9:
            java.lang.String r8 = r7.getScheme()
            if (r8 != 0) goto L10
            return r1
        L10:
            java.lang.String r2 = "tel"
            boolean r2 = r8.equals(r2)
            if (r2 != 0) goto L88
            java.lang.String r2 = "sip"
            boolean r2 = r8.equals(r2)
            if (r2 == 0) goto L24
            goto L88
        L24:
            if (r14 != 0) goto L27
            return r1
        L27:
            java.lang.String r9 = r13.resolveType(r14)
            r1 = 0
            java.lang.String r10 = r7.getAuthority()
            java.lang.String r2 = "contacts"
            boolean r2 = r2.equals(r10)
            if (r2 == 0) goto L3d
            java.lang.String r1 = "number"
            r11 = r1
            goto L4a
        L3d:
            java.lang.String r2 = "com.android.contacts"
            boolean r2 = r2.equals(r10)
            if (r2 == 0) goto L49
            java.lang.String r1 = "data1"
            r11 = r1
            goto L4a
        L49:
            r11 = r1
        L4a:
            r12 = 0
            android.content.ContentResolver r1 = r14.getContentResolver()     // Catch: java.lang.Throwable -> L73 java.lang.RuntimeException -> L75
            java.lang.String[] r3 = new java.lang.String[]{r11}     // Catch: java.lang.Throwable -> L73 java.lang.RuntimeException -> L75
            r5 = 0
            r6 = 0
            r4 = 0
            r2 = r7
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L73 java.lang.RuntimeException -> L75
            r12 = r1
            if (r12 == 0) goto L6d
            boolean r1 = r12.moveToFirst()     // Catch: java.lang.Throwable -> L73 java.lang.RuntimeException -> L75
            if (r1 == 0) goto L6d
            int r1 = r12.getColumnIndex(r11)     // Catch: java.lang.Throwable -> L73 java.lang.RuntimeException -> L75
            java.lang.String r1 = r12.getString(r1)     // Catch: java.lang.Throwable -> L73 java.lang.RuntimeException -> L75
            r0 = r1
        L6d:
            if (r12 == 0) goto L81
        L6f:
            r12.close()
            goto L81
        L73:
            r1 = move-exception
            goto L82
        L75:
            r1 = move-exception
            java.lang.String r2 = "PhoneNumberUtils"
            java.lang.String r3 = "Error getting phone number."
            com.android.telephony.Rlog.e(r2, r3, r1)     // Catch: java.lang.Throwable -> L73
            if (r12 == 0) goto L81
            goto L6f
        L81:
            return r0
        L82:
            if (r12 == 0) goto L87
            r12.close()
        L87:
            throw r1
        L88:
            java.lang.String r1 = r7.getSchemeSpecificPart()
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.telephony.PhoneNumberUtils.getNumberFromIntent(android.content.Intent,"
                    + " android.content.Context):java.lang.String");
    }

    public static String extractNetworkPortion(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
        int len = phoneNumber.length();
        StringBuilder ret = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = phoneNumber.charAt(i);
            int digit = Character.digit(c, 10);
            if (digit != -1) {
                ret.append(digit);
            } else if (c != '+') {
                if (isDialable(c)) {
                    ret.append(c);
                } else if (isStartsPostDial(c)) {
                    break;
                }
            } else {
                String prefix = ret.toString();
                if (prefix.length() == 0 || prefix.equals(CLIR_ON) || prefix.equals(CLIR_OFF)) {
                    ret.append(c);
                }
            }
        }
        return ret.toString();
    }

    public static String extractNetworkPortionAlt(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
        int len = phoneNumber.length();
        StringBuilder ret = new StringBuilder(len);
        boolean haveSeenPlus = false;
        for (int i = 0; i < len; i++) {
            char c = phoneNumber.charAt(i);
            if (c == '+') {
                if (haveSeenPlus) {
                    continue;
                } else {
                    haveSeenPlus = true;
                }
            }
            if (isDialable(c)) {
                ret.append(c);
            } else if (isStartsPostDial(c)) {
                break;
            }
        }
        return ret.toString();
    }

    public static String stripSeparators(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
        int len = phoneNumber.length();
        StringBuilder ret = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = phoneNumber.charAt(i);
            int digit = Character.digit(c, 10);
            if (digit != -1) {
                ret.append(digit);
            } else if (isNonSeparator(c)) {
                ret.append(c);
            }
        }
        return ret.toString();
    }

    public static String convertAndStrip(String phoneNumber) {
        return stripSeparators(convertKeypadLettersToDigits(phoneNumber));
    }

    public static String convertPreDial(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
        int len = phoneNumber.length();
        StringBuilder ret = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = phoneNumber.charAt(i);
            if (isPause(c)) {
                c = ',';
            } else if (isToneWait(c)) {
                c = ';';
            }
            ret.append(c);
        }
        return ret.toString();
    }

    private static int minPositive(int a, int b) {
        if (a >= 0 && b >= 0) {
            return a < b ? a : b;
        }
        if (a >= 0) {
            return a;
        }
        if (b >= 0) {
            return b;
        }
        return -1;
    }

    private static void log(String msg) {
        com.android.telephony.Rlog.d(LOG_TAG, msg);
    }

    private static int indexOfLastNetworkChar(String a) {
        int origLength = a.length();
        int pIndex = a.indexOf(44);
        int wIndex = a.indexOf(59);
        int trimIndex = minPositive(pIndex, wIndex);
        if (trimIndex < 0) {
            return origLength - 1;
        }
        return trimIndex - 1;
    }

    public static String extractPostDialPortion(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
        StringBuilder ret = new StringBuilder();
        int trimIndex = indexOfLastNetworkChar(phoneNumber);
        int s = phoneNumber.length();
        for (int i = trimIndex + 1; i < s; i++) {
            char c = phoneNumber.charAt(i);
            if (isNonSeparator(c)) {
                ret.append(c);
            }
        }
        return ret.toString();
    }

    @Deprecated
    public static boolean compare(String a, String b) {
        return compare(a, b, false);
    }

    @Deprecated
    public static boolean compare(Context context, String a, String b) {
        boolean useStrict =
                context.getResources()
                        .getBoolean(R.bool.config_use_strict_phone_number_comparation);
        return compare(a, b, useStrict);
    }

    public static boolean compare(String a, String b, boolean useStrictComparation) {
        return useStrictComparation ? compareStrictly(a, b) : compareLoosely(a, b);
    }

    public static boolean compareLoosely(String a, String b) {
        int numNonDialableCharsInA = 0;
        int numNonDialableCharsInB = 0;
        int minMatch =
                SemCscFeature.getInstance().getInteger("CscFeature_RIL_CallerIdMatchingDigit", 7);
        if (a == null || b == null) {
            return a == b;
        }
        if (a.length() == 0 || b.length() == 0) {
            return false;
        }
        int ia = indexOfLastNetworkChar(a);
        int ib = indexOfLastNetworkChar(b);
        int matched = 0;
        while (ia >= 0 && ib >= 0) {
            boolean skipCmp = false;
            char ca = a.charAt(ia);
            if (!isDialable(ca)) {
                ia--;
                skipCmp = true;
                numNonDialableCharsInA++;
            }
            char cb = b.charAt(ib);
            if (!isDialable(cb)) {
                ib--;
                skipCmp = true;
                numNonDialableCharsInB++;
            }
            if (!skipCmp) {
                if (cb != ca && ca != 'N' && cb != 'N') {
                    break;
                }
                ia--;
                ib--;
                matched++;
            }
        }
        if (matched < minMatch) {
            int effectiveALen = a.length() - numNonDialableCharsInA;
            int effectiveBLen = b.length() - numNonDialableCharsInB;
            return effectiveALen == effectiveBLen && effectiveALen == matched;
        }
        if (matched >= minMatch && (ia < 0 || ib < 0)) {
            return true;
        }
        if (matchIntlPrefix(a, ia + 1) && matchIntlPrefix(b, ib + 1)) {
            return true;
        }
        if (matchTrunkPrefix(a, ia + 1) && matchIntlPrefixAndCC(b, ib + 1)) {
            return true;
        }
        return matchTrunkPrefix(b, ib + 1) && matchIntlPrefixAndCC(a, ia + 1);
    }

    public static boolean compareStrictly(String a, String b) {
        return compareStrictly(a, b, true);
    }

    public static boolean compareStrictly(String a, String b, boolean acceptInvalidCCCPrefix) {
        boolean z;
        if (a == null) {
            z = true;
        } else {
            if (b != null) {
                if (a.length() == 0 && b.length() == 0) {
                    return false;
                }
                int forwardIndexA = 0;
                int forwardIndexB = 0;
                CountryCallingCodeAndNewIndex cccA =
                        tryGetCountryCallingCodeAndNewIndex(a, acceptInvalidCCCPrefix);
                CountryCallingCodeAndNewIndex cccB =
                        tryGetCountryCallingCodeAndNewIndex(b, acceptInvalidCCCPrefix);
                boolean bothHasCountryCallingCode = false;
                boolean okToIgnorePrefix = true;
                boolean trunkPrefixIsOmittedA = false;
                boolean trunkPrefixIsOmittedB = false;
                if (cccA != null && cccB != null) {
                    if (cccA.countryCallingCode != cccB.countryCallingCode) {
                        return false;
                    }
                    okToIgnorePrefix = false;
                    bothHasCountryCallingCode = true;
                    forwardIndexA = cccA.newIndex;
                    forwardIndexB = cccB.newIndex;
                } else if (cccA == null && cccB == null) {
                    okToIgnorePrefix = false;
                } else {
                    if (cccA != null) {
                        forwardIndexA = cccA.newIndex;
                    } else {
                        int tmp = tryGetTrunkPrefixOmittedIndex(b, 0);
                        if (tmp >= 0) {
                            forwardIndexA = tmp;
                            trunkPrefixIsOmittedA = true;
                        }
                    }
                    if (cccB != null) {
                        forwardIndexB = cccB.newIndex;
                    } else {
                        int tmp2 = tryGetTrunkPrefixOmittedIndex(b, 0);
                        if (tmp2 >= 0) {
                            forwardIndexB = tmp2;
                            trunkPrefixIsOmittedB = true;
                        }
                    }
                }
                int backwardIndexA = a.length() - 1;
                int backwardIndexB = b.length() - 1;
                while (backwardIndexA >= forwardIndexA && backwardIndexB >= forwardIndexB) {
                    boolean skip_compare = false;
                    char chA = a.charAt(backwardIndexA);
                    char chB = b.charAt(backwardIndexB);
                    if (isSeparator(chA)) {
                        backwardIndexA--;
                        skip_compare = true;
                    }
                    if (isSeparator(chB)) {
                        backwardIndexB--;
                        skip_compare = true;
                    }
                    if (!skip_compare) {
                        if (chA != chB) {
                            return false;
                        }
                        backwardIndexA--;
                        backwardIndexB--;
                    }
                }
                if (okToIgnorePrefix) {
                    if ((trunkPrefixIsOmittedA && forwardIndexA <= backwardIndexA)
                            || !checkPrefixIsIgnorable(a, forwardIndexA, backwardIndexA)) {
                        if (acceptInvalidCCCPrefix) {
                            return compare(a, b, false);
                        }
                        return false;
                    }
                    if ((trunkPrefixIsOmittedB && forwardIndexB <= backwardIndexB)
                            || !checkPrefixIsIgnorable(b, forwardIndexA, backwardIndexB)) {
                        if (acceptInvalidCCCPrefix) {
                            return compare(a, b, false);
                        }
                        return false;
                    }
                    return true;
                }
                boolean maybeNamp = !bothHasCountryCallingCode;
                while (backwardIndexA >= forwardIndexA) {
                    char chA2 = a.charAt(backwardIndexA);
                    if (isDialable(chA2)) {
                        if (maybeNamp && tryGetISODigit(chA2) == 1) {
                            maybeNamp = false;
                        } else {
                            return false;
                        }
                    }
                    backwardIndexA--;
                }
                while (backwardIndexB >= forwardIndexB) {
                    char chB2 = b.charAt(backwardIndexB);
                    if (isDialable(chB2)) {
                        if (maybeNamp && tryGetISODigit(chB2) == 1) {
                            maybeNamp = false;
                        } else {
                            return false;
                        }
                    }
                    backwardIndexB--;
                }
                return true;
            }
            z = true;
        }
        if (a == b) {
            return z;
        }
        return false;
    }

    public static boolean semCompareStrictly(String a, String b, boolean acceptInvalidCCCPrefix) {
        return compareStrictly(a, b, acceptInvalidCCCPrefix);
    }

    public static String toCallerIDMinMatch(String phoneNumber) {
        String np = extractNetworkPortionAlt(phoneNumber);
        return internalGetStrippedReversed(np, getMinMatch());
    }

    public static String semToCallerIDMinMatch(String phoneNumber, int minMatchlen) {
        String np = extractNetworkPortionAlt(phoneNumber);
        return internalGetStrippedReversed(np, minMatchlen > 0 ? minMatchlen : getMinMatch());
    }

    public static String getStrippedReversed(String phoneNumber) {
        String np = extractNetworkPortionAlt(phoneNumber);
        if (np == null) {
            return null;
        }
        return internalGetStrippedReversed(np, np.length());
    }

    private static String internalGetStrippedReversed(String np, int numDigits) {
        if (np == null) {
            return null;
        }
        StringBuilder ret = new StringBuilder(numDigits);
        int length = np.length();
        for (int i = length - 1; i >= 0 && length - i <= numDigits; i--) {
            char c = np.charAt(i);
            ret.append(c);
        }
        return ret.toString();
    }

    public static String stringFromStringAndTOA(String s, int TOA) {
        if (s == null) {
            return null;
        }
        if (TOA == 145 && s.length() > 0 && s.charAt(0) != '+') {
            return PLUS_SIGN_STRING + s;
        }
        return s;
    }

    public static int toaFromString(String s) {
        if (s != null && s.length() > 0 && s.charAt(0) == '+') {
            return 145;
        }
        return 129;
    }

    @Deprecated
    public static String calledPartyBCDToString(byte[] bytes, int offset, int length) {
        return calledPartyBCDToString(bytes, offset, length, 1);
    }

    public static String calledPartyBCDToString(
            byte[] bytes, int offset, int length, int bcdExtType) {
        boolean prependPlus = false;
        StringBuilder ret = new StringBuilder((length * 2) + 1);
        if (length < 2) {
            return "";
        }
        if ((bytes[offset] & 240) == 144) {
            prependPlus = true;
        }
        internalCalledPartyBCDFragmentToString(ret, bytes, offset + 1, length - 1, bcdExtType);
        if (prependPlus && ret.length() == 0) {
            return "";
        }
        if (prependPlus) {
            String retString = ret.toString();
            Pattern p = Pattern.compile("(^[#*])(.*)([#*])(.*)(#)$");
            Matcher m = p.matcher(retString);
            if (m.matches()) {
                if ("".equals(m.group(2))) {
                    ret = new StringBuilder();
                    ret.append(m.group(1));
                    ret.append(m.group(3));
                    ret.append(m.group(4));
                    ret.append(m.group(5));
                    ret.append(PLUS_SIGN_STRING);
                } else {
                    ret = new StringBuilder();
                    ret.append(m.group(1));
                    ret.append(m.group(2));
                    ret.append(m.group(3));
                    ret.append(PLUS_SIGN_STRING);
                    ret.append(m.group(4));
                    ret.append(m.group(5));
                }
            } else {
                Pattern p2 = Pattern.compile("(^[#*])(.*)([#*])(.*)");
                Matcher m2 = p2.matcher(retString);
                if (m2.matches()) {
                    ret = new StringBuilder();
                    ret.append(m2.group(1));
                    ret.append(m2.group(2));
                    ret.append(m2.group(3));
                    ret.append(PLUS_SIGN_STRING);
                    ret.append(m2.group(4));
                } else {
                    ret = new StringBuilder();
                    ret.append(PLUS_SIGN_CHAR);
                    ret.append(retString);
                }
            }
        }
        return ret.toString();
    }

    private static void internalCalledPartyBCDFragmentToString(
            StringBuilder sb, byte[] bytes, int offset, int length, int bcdExtType) {
        char c;
        char c2;
        for (int i = offset;
                i < length + offset && (c = bcdToChar((byte) (bytes[i] & 15), bcdExtType)) != 0;
                i++) {
            sb.append(c);
            byte b = (byte) ((bytes[i] >> 4) & 15);
            if ((b == 15 && i + 1 == length + offset) || (c2 = bcdToChar(b, bcdExtType)) == 0) {
                return;
            }
            sb.append(c2);
        }
    }

    @Deprecated
    public static String calledPartyBCDFragmentToString(byte[] bytes, int offset, int length) {
        return calledPartyBCDFragmentToString(bytes, offset, length, 1);
    }

    public static String calledPartyBCDFragmentToString(
            byte[] bytes, int offset, int length, int bcdExtType) {
        StringBuilder ret = new StringBuilder(length * 2);
        internalCalledPartyBCDFragmentToString(ret, bytes, offset, length, bcdExtType);
        return ret.toString();
    }

    private static char bcdToChar(byte b, int bcdExtType) {
        if (b < 10) {
            return (char) (b + SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90);
        }
        String extended = null;
        if (1 == bcdExtType) {
            extended = BCD_EF_ADN_EXTENDED;
        } else if (2 == bcdExtType) {
            extended = BCD_CALLED_PARTY_EXTENDED;
        }
        if (extended == null || b - 10 >= extended.length()) {
            return (char) 0;
        }
        return extended.charAt(b - 10);
    }

    private static int charToBCD(char c, int bcdExtType) {
        if ('0' <= c && c <= '9') {
            return c - '0';
        }
        String extended = null;
        if (1 == bcdExtType) {
            extended = BCD_EF_ADN_EXTENDED;
        } else if (2 == bcdExtType) {
            extended = BCD_CALLED_PARTY_EXTENDED;
        }
        if (extended == null || extended.indexOf(c) == -1) {
            throw new RuntimeException("invalid char for BCD " + c);
        }
        return extended.indexOf(c) + 10;
    }

    public static boolean isWellFormedSmsAddress(String address) {
        String networkPortion = extractNetworkPortion(address);
        return (networkPortion.equals(PLUS_SIGN_STRING)
                        || TextUtils.isEmpty(networkPortion)
                        || !isDialable(networkPortion))
                ? false
                : true;
    }

    public static boolean isGlobalPhoneNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        }
        Matcher match = GLOBAL_PHONE_NUMBER_PATTERN.matcher(phoneNumber);
        return match.matches();
    }

    private static boolean isDialable(String address) {
        int count = address.length();
        for (int i = 0; i < count; i++) {
            if (!isDialable(address.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isNonSeparator(String address) {
        int count = address.length();
        for (int i = 0; i < count; i++) {
            if (!isNonSeparator(address.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static byte[] networkPortionToCalledPartyBCD(String s) {
        String networkPortion = extractNetworkPortion(s);
        return numberToCalledPartyBCDHelper(networkPortion, false, 1);
    }

    public static byte[] networkPortionToCalledPartyBCDWithLength(String s) {
        String networkPortion = extractNetworkPortion(s);
        return numberToCalledPartyBCDHelper(networkPortion, true, 1);
    }

    @Deprecated
    public static byte[] numberToCalledPartyBCD(String number) {
        return numberToCalledPartyBCD(number, 1);
    }

    public static byte[] numberToCalledPartyBCD(String number, int bcdExtType) {
        return numberToCalledPartyBCDHelper(number, false, bcdExtType);
    }

    private static byte[] numberToCalledPartyBCDHelper(
            String number, boolean includeLength, int bcdExtType) {
        int numberLenReal = number.length();
        int numberLenEffective = numberLenReal;
        char c = PLUS_SIGN_CHAR;
        boolean hasPlus = number.indexOf(43) != -1;
        if (hasPlus) {
            numberLenEffective--;
        }
        if (numberLenEffective == 0) {
            return null;
        }
        int resultLen = (numberLenEffective + 1) / 2;
        int extraBytes = includeLength ? 1 + 1 : 1;
        int resultLen2 = resultLen + extraBytes;
        byte[] result = new byte[resultLen2];
        int digitCount = 0;
        int i = 0;
        while (i < numberLenReal) {
            char c2 = number.charAt(i);
            if (c2 != c) {
                int shift = (digitCount & 1) == 1 ? 4 : 0;
                int i2 = (digitCount >> 1) + extraBytes;
                result[i2] =
                        (byte) (((byte) ((charToBCD(c2, bcdExtType) & 15) << shift)) | result[i2]);
                digitCount++;
            }
            i++;
            c = PLUS_SIGN_CHAR;
        }
        if ((digitCount & 1) == 1) {
            int i3 = (digitCount >> 1) + extraBytes;
            result[i3] = (byte) (result[i3] | 240);
        }
        int offset = 0;
        if (includeLength) {
            int offset2 = 0 + 1;
            result[0] = (byte) (resultLen2 - 1);
            offset = offset2;
        }
        result[offset] = (byte) (hasPlus ? 145 : 129);
        return result;
    }

    @Deprecated
    public static String formatNumber(String source) {
        SpannableStringBuilder text = new SpannableStringBuilder(source);
        formatNumber(text, getFormatTypeForLocale(Locale.getDefault()));
        return text.toString();
    }

    @Deprecated
    public static String formatNumber(String source, int defaultFormattingType) {
        SpannableStringBuilder text = new SpannableStringBuilder(source);
        formatNumber(text, defaultFormattingType);
        return text.toString();
    }

    @Deprecated
    public static int getFormatTypeForLocale(Locale locale) {
        String country = locale.getCountry();
        return getFormatTypeFromCountryCode(country);
    }

    @Deprecated
    public static void formatNumber(Editable text, int defaultFormattingType) {
        int formatType = defaultFormattingType;
        if (text.length() > 2 && text.charAt(0) == '+') {
            formatType =
                    text.charAt(1) == '1'
                            ? 1
                            : (text.length() >= 3 && text.charAt(1) == '8' && text.charAt(2) == '1')
                                    ? 2
                                    : (TelephonyFeatures.isCountrySpecific(0, "KOR")
                                                    && text.length() >= 3
                                                    && text.charAt(1) == '8'
                                                    && text.charAt(2) == '2')
                                            ? 82
                                            : 0;
        }
        switch (formatType) {
            case 0:
                removeDashes(text);
                break;
            case 1:
                formatNanpNumber(text);
                break;
            case 2:
                formatJapaneseNumber(text);
                break;
            case 82:
                if (TelephonyFeatures.isCountrySpecific(0, "KOR")) {
                    formatKRnpNumber(text);
                    break;
                }
                break;
        }
    }

    @Deprecated
    public static void formatNanpNumber(Editable text) {
        int length = text.length();
        if (length > "+1-nnn-nnn-nnnn".length() || length <= 5) {
            return;
        }
        CharSequence saved = text.subSequence(0, length);
        removeDashes(text);
        int length2 = text.length();
        int[] dashPositions = new int[3];
        int numDashes = 0;
        int state = 1;
        int numDigits = 0;
        for (int i = 0; i < length2; i++) {
            char c = text.charAt(i);
            switch (c) {
                case '+':
                    if (i == 0) {
                        state = 2;
                    } else {
                        text.replace(0, length2, saved);
                        break;
                    }
                case ',':
                case '.':
                case '/':
                default:
                    text.replace(0, length2, saved);
                    break;
                case '-':
                    state = 4;
                case '1':
                    if (numDigits == 0 || state == 2) {
                        state = 3;
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
                    if (state == 2) {
                        text.replace(0, length2, saved);
                        break;
                    } else {
                        if (state == 3) {
                            dashPositions[numDashes] = i;
                            numDashes++;
                        } else if (state != 4 && (numDigits == 3 || numDigits == 6)) {
                            dashPositions[numDashes] = i;
                            numDashes++;
                        }
                        state = 1;
                        numDigits++;
                    }
            }
            return;
        }
        if (numDigits == 7) {
            numDashes--;
        }
        for (int i2 = 0; i2 < numDashes; i2++) {
            int pos = dashPositions[i2];
            text.replace(pos + i2, pos + i2, NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        }
        for (int len = text.length(); len > 0 && text.charAt(len - 1) == '-'; len--) {
            text.delete(len - 1, len);
        }
    }

    @Deprecated
    public static void formatJapaneseNumber(Editable text) {
        JapanesePhoneNumberFormatter.format(text);
    }

    public static void formatKRnpNumber(Editable text) {
        int state;
        int length = text.length();
        int lengthRemovedDash =
                text.toString().replace(NativeLibraryHelper.CLEAR_ABI_OVERRIDE, "").length();
        if (lengthRemovedDash > 12) {
            removeDashes(text);
            return;
        }
        if (length < 2) {
            return;
        }
        String Digits = text.toString();
        if (length < 6 && Digits.endsWith(NativeLibraryHelper.CLEAR_ABI_OVERRIDE)) {
            int p = 0;
            while (p < text.length()) {
                if (text.charAt(p) == '-') {
                    text.delete(p, p + 1);
                } else {
                    p++;
                }
            }
            return;
        }
        int p2 = 0;
        while (p2 < text.length()) {
            if (text.charAt(p2) == ' ' || text.charAt(p2) == '/') {
                text.delete(p2, p2 + 1);
            } else {
                p2++;
            }
        }
        if (length != text.length()) {
            length = text.length();
        }
        if (length < 1) {
            return;
        }
        if (text.charAt(0) == '0') {
            if (length < 2) {
                return;
            }
            if (text.charAt(1) == '2') {
                state = 6;
            } else {
                if (length < 3) {
                    return;
                }
                if (Digits.startsWith("050")) {
                    state = 14;
                } else {
                    state = 7;
                }
            }
        } else if (text.charAt(0) == '*') {
            if (length < 4) {
                return;
            }
            if (Digits.startsWith("*23#")
                    || Digits.startsWith("*22#")
                    || Digits.startsWith(CLIR_ON)) {
                if (length > 5) {
                    return;
                } else {
                    state = 10;
                }
            } else if (!Digits.startsWith("*230#") || length > 6) {
                return;
            } else {
                state = 11;
            }
        } else if (text.charAt(0) == '#') {
            if (length < 2) {
                return;
            }
            if (text.charAt(1) == '9') {
                if (length > 3) {
                    return;
                } else {
                    state = 8;
                }
            } else if (!Digits.startsWith(CLIR_OFF) || length > 5) {
                return;
            } else {
                state = 10;
            }
        } else if (text.charAt(0) == '+') {
            if (length < 6 || length > 14) {
                return;
            } else {
                state = 9;
            }
        } else if (length < 5 || length > 14) {
            return;
        } else {
            state = 5;
        }
        CharSequence saved = text.subSequence(0, length);
        int p3 = 0;
        while (p3 < text.length()) {
            if (text.charAt(p3) == '-') {
                text.delete(p3, p3 + 1);
            } else {
                p3++;
            }
        }
        int length2 = text.length();
        if (text.toString().equals("3003003000")) {
            text.replace(0, length2, "300-300-3000");
            return;
        }
        int[] dashPositions = new int[2];
        int numDashes = 0;
        switch (state) {
            case 5:
                if (length2 <= 3) {
                    numDashes = 0;
                    break;
                } else if (length2 <= 7) {
                    dashPositions[0] = 3;
                    numDashes = 1;
                    break;
                } else if (length2 > 7) {
                    dashPositions[0] = 4;
                    numDashes = 1;
                    break;
                }
                break;
            case 6:
                if (length2 <= 2) {
                    numDashes = 0;
                    break;
                } else if (length2 > 6) {
                    if (length2 > 6 && length2 <= 9) {
                        dashPositions[0] = 2;
                        dashPositions[1] = length2 - 4;
                        numDashes = 2;
                        break;
                    } else if (length2 > 9) {
                        dashPositions[0] = 2;
                        dashPositions[1] = 6;
                        numDashes = 2;
                        break;
                    }
                } else {
                    dashPositions[0] = 2;
                    numDashes = 1;
                    break;
                }
                break;
            case 7:
                if (length2 <= 3) {
                    numDashes = 0;
                    break;
                } else if (length2 > 7) {
                    if (length2 > 7 && length2 <= 10) {
                        dashPositions[0] = 3;
                        dashPositions[1] = length2 - 4;
                        numDashes = 2;
                        break;
                    } else if (length2 > 10) {
                        dashPositions[0] = 3;
                        dashPositions[1] = 7;
                        numDashes = 2;
                        break;
                    }
                } else {
                    dashPositions[0] = 3;
                    numDashes = 1;
                    break;
                }
                break;
            case 8:
                if (length2 <= 2) {
                    numDashes = 0;
                    break;
                } else {
                    dashPositions[0] = 2;
                    numDashes = 1;
                    break;
                }
            case 9:
                if (length2 <= 8) {
                    dashPositions[0] = length2 - 4;
                    numDashes = 1;
                    break;
                } else if (length2 > 8) {
                    dashPositions[0] = 4;
                    numDashes = 1;
                    break;
                }
                break;
            case 10:
                if (length2 <= 4) {
                    numDashes = 0;
                    break;
                } else {
                    dashPositions[0] = 4;
                    numDashes = 1;
                    break;
                }
            case 11:
                if (length2 <= 5) {
                    numDashes = 0;
                    break;
                } else {
                    dashPositions[0] = 5;
                    numDashes = 1;
                    break;
                }
            case 12:
            case 13:
            default:
                text.replace(0, length2, saved);
                return;
            case 14:
                if (length2 <= 4) {
                    numDashes = 0;
                    break;
                } else if (length2 > 8) {
                    if (length2 > 8 && length2 <= 11) {
                        dashPositions[0] = 4;
                        dashPositions[1] = length2 - 4;
                        numDashes = 2;
                        break;
                    } else if (length2 > 11) {
                        dashPositions[0] = 4;
                        dashPositions[1] = 8;
                        numDashes = 2;
                        break;
                    }
                } else {
                    dashPositions[0] = 4;
                    numDashes = 1;
                    break;
                }
                break;
        }
        if (numDashes != 0) {
            for (int i = 0; i < numDashes; i++) {
                int pos = dashPositions[i];
                if (pos + i >= 0 && pos + i <= length2) {
                    text.replace(pos + i, pos + i, NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                }
            }
        }
    }

    private static void removeDashes(Editable text) {
        int p = 0;
        while (p < text.length()) {
            if (text.charAt(p) == '-') {
                text.delete(p, p + 1);
            } else {
                p++;
            }
        }
    }

    public static String formatNumberToE164(String phoneNumber, String defaultCountryIso) {
        if (defaultCountryIso != null) {
            defaultCountryIso = defaultCountryIso.toUpperCase(Locale.ROOT);
        }
        return formatNumberInternal(
                phoneNumber, defaultCountryIso, PhoneNumberUtil.PhoneNumberFormat.E164);
    }

    public static String formatNumberToRFC3966(String phoneNumber, String defaultCountryIso) {
        if (defaultCountryIso != null) {
            defaultCountryIso = defaultCountryIso.toUpperCase(Locale.ROOT);
        }
        return formatNumberInternal(
                phoneNumber, defaultCountryIso, PhoneNumberUtil.PhoneNumberFormat.RFC3966);
    }

    private static String formatNumberInternal(
            String rawPhoneNumber,
            String defaultCountryIso,
            PhoneNumberUtil.PhoneNumberFormat formatIdentifier) {
        PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber phoneNumber = util.parse(rawPhoneNumber, defaultCountryIso);
            if (util.isValidNumber(phoneNumber)) {
                return util.format(phoneNumber, formatIdentifier);
            }
            return null;
        } catch (NumberParseException e) {
            return null;
        }
    }

    public static boolean isInternationalNumber(String phoneNumber, String defaultCountryIso) {
        if (TextUtils.isEmpty(phoneNumber)
                || phoneNumber.startsWith("#")
                || phoneNumber.startsWith("*")) {
            return false;
        }
        if (defaultCountryIso != null) {
            defaultCountryIso = defaultCountryIso.toUpperCase(Locale.ROOT);
        }
        PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber pn = util.parseAndKeepRawInput(phoneNumber, defaultCountryIso);
            return pn.getCountryCode() != util.getCountryCodeForRegion(defaultCountryIso);
        } catch (NumberParseException e) {
            return false;
        }
    }

    public static String formatNumber(String phoneNumber, String defaultCountryIso) {
        String result;
        if (phoneNumber.startsWith("#") || phoneNumber.startsWith("*")) {
            return phoneNumber;
        }
        if (defaultCountryIso != null) {
            defaultCountryIso = defaultCountryIso.toUpperCase(Locale.ROOT);
        }
        PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String result2 = null;
        if (TelephonyFeatures.isCountrySpecific(0, "KOR")) {
            String networkCountryIso = TelephonyManager.getDefault().getNetworkCountryIso(0);
            Locale locale = Locale.getDefault();
            if (!phoneNumber.startsWith(PLUS_SIGN_STRING)) {
                if ("ko".equals(locale.getLanguage())
                        || (phoneNumber.startsWith("050") && "kr".equals(networkCountryIso))) {
                    return formatNumber(
                            phoneNumber, getFormatTypeFromCountryCode(KOREA_ISO_COUNTRY_CODE));
                }
                try {
                    return util.formatInOriginalFormat(
                            util.parseAndKeepRawInput(phoneNumber, defaultCountryIso),
                            defaultCountryIso);
                } catch (NumberParseException e) {
                    return null;
                }
            }
            try {
                Phonenumber.PhoneNumber pn =
                        util.parseAndKeepRawInput(phoneNumber, defaultCountryIso);
                if (KOREA_ISO_COUNTRY_CODE.equals(defaultCountryIso)
                        && pn.getCountryCode()
                                == util.getCountryCodeForRegion(KOREA_ISO_COUNTRY_CODE)
                        && pn.getCountryCodeSource()
                                == Phonenumber.PhoneNumber.CountryCodeSource
                                        .FROM_NUMBER_WITH_PLUS_SIGN) {
                    result2 = util.format(pn, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
                } else {
                    result2 = util.formatInOriginalFormat(pn, defaultCountryIso);
                }
                return result2;
            } catch (NumberParseException e2) {
                return result2;
            }
        }
        try {
            Phonenumber.PhoneNumber pn2 = util.parseAndKeepRawInput(phoneNumber, defaultCountryIso);
            result2 = util.formatInOriginalFormat(pn2, defaultCountryIso);
            if (result2 != null) {
                return result2;
            }
            if (KOREA_ISO_COUNTRY_CODE.equalsIgnoreCase(defaultCountryIso)
                    && pn2.getCountryCode() == util.getCountryCodeForRegion(KOREA_ISO_COUNTRY_CODE)
                    && pn2.getCountryCodeSource()
                            == Phonenumber.PhoneNumber.CountryCodeSource
                                    .FROM_NUMBER_WITH_PLUS_SIGN) {
                result = util.format(pn2, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
            } else if (JAPAN_ISO_COUNTRY_CODE.equalsIgnoreCase(defaultCountryIso)
                    && pn2.getCountryCode() == util.getCountryCodeForRegion(JAPAN_ISO_COUNTRY_CODE)
                    && pn2.getCountryCodeSource()
                            == Phonenumber.PhoneNumber.CountryCodeSource
                                    .FROM_NUMBER_WITH_PLUS_SIGN) {
                result = util.format(pn2, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
            } else if (Flags.removeCountryCodeFromLocalSingaporeCalls()
                    && SINGAPORE_ISO_COUNTRY_CODE.equalsIgnoreCase(defaultCountryIso)
                    && pn2.getCountryCode()
                            == util.getCountryCodeForRegion(SINGAPORE_ISO_COUNTRY_CODE)
                    && pn2.getCountryCodeSource()
                            == Phonenumber.PhoneNumber.CountryCodeSource
                                    .FROM_NUMBER_WITH_PLUS_SIGN) {
                result = util.format(pn2, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
            } else {
                result = util.formatInOriginalFormat(pn2, defaultCountryIso);
            }
            return result;
        } catch (NumberParseException e3) {
            return result2;
        }
    }

    public static String formatNumber(
            String phoneNumber, String phoneNumberE164, String defaultCountryIso) {
        if (defaultCountryIso != null) {
            defaultCountryIso = defaultCountryIso.toUpperCase(Locale.ROOT);
        }
        int len = phoneNumber.length();
        for (int i = 0; i < len; i++) {
            if (!isDialable(phoneNumber.charAt(i))) {
                return phoneNumber;
            }
        }
        PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        if (phoneNumberE164 != null
                && phoneNumberE164.length() >= 2
                && phoneNumberE164.charAt(0) == '+') {
            try {
                Phonenumber.PhoneNumber pn = util.parse(phoneNumberE164, "ZZ");
                String regionCode = util.getRegionCodeForNumber(pn);
                if (!TextUtils.isEmpty(regionCode)) {
                    if (normalizeNumber(phoneNumber).indexOf(phoneNumberE164.substring(1)) <= 0) {
                        defaultCountryIso = regionCode;
                    }
                }
            } catch (NumberParseException e) {
            }
        }
        String result = formatNumber(phoneNumber, defaultCountryIso);
        return result != null ? result : phoneNumber;
    }

    public static String normalizeNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int len = phoneNumber.length();
        for (int i = 0; i < len; i++) {
            char c = phoneNumber.charAt(i);
            int digit = Character.digit(c, 10);
            if (digit != -1) {
                sb.append(digit);
            } else if (sb.length() == 0 && c == '+') {
                sb.append(c);
            } else if (c == '*' || c == '#') {
                sb.append(c);
            } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                return normalizeNumber(convertKeypadLettersToDigits(phoneNumber));
            }
        }
        return sb.toString();
    }

    public static String replaceUnicodeDigits(String number) {
        StringBuilder normalizedDigits = new StringBuilder(number.length());
        for (char c : number.toCharArray()) {
            int digit = Character.digit(c, 10);
            if (digit != -1) {
                normalizedDigits.append(digit);
            } else {
                normalizedDigits.append(c);
            }
        }
        return normalizedDigits.toString();
    }

    @Deprecated
    public static boolean isEmergencyNumber(String number) {
        return isEmergencyNumber(getDefaultVoiceSubId(), number);
    }

    @Deprecated
    public static boolean isEmergencyNumber(int subId, String number) {
        return isEmergencyNumberInternal(subId, number);
    }

    public static boolean semIsEmergencyNumber(int subId, String number) {
        return isEmergencyNumber(subId, number);
    }

    private static boolean isEmergencyNumberInternal(int subId, String number) {
        try {
            int phoneId = TelephonyManager.getDefault().getSlotIndex();
            if (TelephonyFeatures.needToCheckEmergencyNumberForEachSlot(phoneId)) {
                return TelephonyManager.getDefault().isEmergencyNumber(subId, number);
            }
            return TelephonyManager.getDefault().isEmergencyNumber(number);
        } catch (RuntimeException ex) {
            com.android.telephony.Rlog.e(
                    LOG_TAG, "isEmergencyNumberInternal: RuntimeException: " + ex);
            return false;
        }
    }

    @Deprecated
    public static boolean isLocalEmergencyNumber(Context context, String number) {
        return isEmergencyNumberInternal(getDefaultVoiceSubId(), number);
    }

    public static boolean isVoiceMailNumber(String number) {
        return isVoiceMailNumber(SubscriptionManager.getDefaultSubscriptionId(), number);
    }

    public static boolean isVoiceMailNumber(int subId, String number) {
        return isVoiceMailNumber(null, subId, number);
    }

    @SystemApi
    public static boolean isVoiceMailNumber(Context context, int subId, String number) {
        TelephonyManager tm;
        CarrierConfigManager configManager;
        PersistableBundle b;
        try {
            if (context == null) {
                tm = TelephonyManager.getDefault();
            } else {
                tm = TelephonyManager.from(context);
            }
            String vmNumber = tm.getVoiceMailNumber(subId);
            String mdn = tm.getLine1Number(subId);
            String number2 = extractNetworkPortionAlt(number);
            if (TextUtils.isEmpty(number2)) {
                return false;
            }
            boolean compareWithMdn = false;
            if (context != null
                    && (configManager =
                                    (CarrierConfigManager)
                                            context.getSystemService("carrier_config"))
                            != null
                    && (b = configManager.getConfigForSubId(subId)) != null) {
                compareWithMdn =
                        b.getBoolean(
                                CarrierConfigManager.KEY_MDN_IS_ADDITIONAL_VOICEMAIL_NUMBER_BOOL);
            }
            if (compareWithMdn) {
                return compare(number2, vmNumber) || compare(number2, mdn);
            }
            return compare(number2, vmNumber);
        } catch (SecurityException e) {
            return false;
        }
    }

    public static String convertKeypadLettersToDigits(String input) {
        if (input == null) {
            return input;
        }
        int len = input.length();
        if (len == 0) {
            return input;
        }
        char[] out = input.toCharArray();
        for (int i = 0; i < len; i++) {
            char c = out[i];
            out[i] = (char) KEYPAD_MAP.get(c, c);
        }
        return new String(out);
    }

    public static String cdmaCheckAndProcessPlusCode(String dialStr) {
        return cdmaCheckAndProcessPlusCode(dialStr, 0, null);
    }

    public static String cdmaCheckAndProcessPlusCode(String dialStr, int phoneId, Context context) {
        String currIso;
        String defaultIso;
        if (!TextUtils.isEmpty(dialStr)
                && isReallyDialable(dialStr.charAt(0))
                && isNonSeparator(dialStr)) {
            if (context != null) {
                currIso =
                        ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                                .getNetworkCountryIso(phoneId);
                defaultIso = TelephonyManager.getSimCountryIsoForPhone(phoneId);
            } else {
                currIso = TelephonyManager.getDefault().getNetworkCountryIso();
                defaultIso = TelephonyManager.getDefault().getSimCountryIso();
            }
            if (!TextUtils.isEmpty(currIso) && !TextUtils.isEmpty(defaultIso)) {
                return cdmaCheckAndProcessPlusCodeByNumberFormat(
                        dialStr,
                        getFormatTypeFromCountryCode(currIso),
                        getFormatTypeFromCountryCode(defaultIso),
                        phoneId,
                        context);
            }
        }
        return dialStr;
    }

    public static String cdmaCheckAndProcessPlusCodeForSms(String dialStr) {
        if (!TextUtils.isEmpty(dialStr)
                && isReallyDialable(dialStr.charAt(0))
                && isNonSeparator(dialStr)) {
            String defaultIso = TelephonyManager.getDefault().getSimCountryIso();
            if (!TextUtils.isEmpty(defaultIso)) {
                int format = getFormatTypeFromCountryCode(defaultIso);
                return cdmaCheckAndProcessPlusCodeByNumberFormat(dialStr, format, format);
            }
        }
        return dialStr;
    }

    public static String cdmaCheckAndProcessPlusCodeByNumberFormat(
            String dialStr, int currFormat, int defaultFormat) {
        return cdmaCheckAndProcessPlusCodeByNumberFormat(
                dialStr, currFormat, defaultFormat, 0, null);
    }

    public static String cdmaCheckAndProcessPlusCodeByNumberFormat(
            String dialStr, int currFormat, int defaultFormat, int phoneId, Context context) {
        String networkDialStr;
        String networkDialStr2;
        String retStr = dialStr;
        boolean useNanp = currFormat == defaultFormat && currFormat == 1;
        if (context != null && TelephonyFeatures.getNtcFeature(phoneId, 1)) {
            log("Change useNanp for international dialing feature");
            useNanp = true;
        }
        if (dialStr != null && dialStr.lastIndexOf(PLUS_SIGN_STRING) != -1) {
            String tempDialStr = dialStr;
            retStr = null;
            do {
                if (useNanp) {
                    networkDialStr = extractNetworkPortion(tempDialStr);
                } else {
                    networkDialStr = extractNetworkPortionAlt(tempDialStr);
                }
                if (context != null && TelephonyFeatures.getNtcFeature(phoneId, 1)) {
                    networkDialStr2 = processPlusCodeForSpr(networkDialStr, phoneId, context);
                } else {
                    networkDialStr2 = processPlusCode(networkDialStr, useNanp);
                }
                if (!TextUtils.isEmpty(networkDialStr2)) {
                    if (retStr == null) {
                        retStr = networkDialStr2;
                    } else {
                        retStr = retStr.concat(networkDialStr2);
                    }
                    String postDialStr = extractPostDialPortion(tempDialStr);
                    if (!TextUtils.isEmpty(postDialStr)) {
                        int dialableIndex = findDialableIndexFromPostDialStr(postDialStr);
                        if (dialableIndex >= 1) {
                            retStr =
                                    appendPwCharBackToOrigDialStr(
                                            dialableIndex, retStr, postDialStr);
                            tempDialStr = postDialStr.substring(dialableIndex);
                        } else {
                            if (dialableIndex < 0) {
                                postDialStr = "";
                            }
                            com.android.telephony.Rlog.e("wrong postDialStr=", postDialStr);
                        }
                    }
                    if (TextUtils.isEmpty(postDialStr)) {
                        break;
                    }
                } else {
                    com.android.telephony.Rlog.e(
                            "checkAndProcessPlusCode: null newDialStr", networkDialStr2);
                    return dialStr;
                }
            } while (!TextUtils.isEmpty(tempDialStr));
        }
        return retStr;
    }

    public static CharSequence createTtsSpannable(CharSequence phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
        Spannable spannable = Spannable.Factory.getInstance().newSpannable(phoneNumber);
        addTtsSpan(spannable, 0, spannable.length());
        return spannable;
    }

    public static void addTtsSpan(Spannable s, int start, int endExclusive) {
        s.setSpan(
                createTtsSpan(s.subSequence(start, endExclusive).toString()),
                start,
                endExclusive,
                33);
    }

    @Deprecated
    public static CharSequence ttsSpanAsPhoneNumber(CharSequence phoneNumber) {
        return createTtsSpannable(phoneNumber);
    }

    @Deprecated
    public static void ttsSpanAsPhoneNumber(Spannable s, int start, int end) {
        addTtsSpan(s, start, end);
    }

    public static TtsSpan createTtsSpan(String phoneNumberString) {
        if (phoneNumberString == null) {
            return null;
        }
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            phoneNumber = phoneNumberUtil.parse(phoneNumberString, (String) null);
        } catch (NumberParseException e) {
        }
        TtsSpan.TelephoneBuilder builder = new TtsSpan.TelephoneBuilder();
        if (phoneNumber == null) {
            builder.setNumberParts(splitAtNonNumerics(phoneNumberString));
        } else {
            if (phoneNumber.hasCountryCode()) {
                builder.setCountryCode(Integer.toString(phoneNumber.getCountryCode()));
            }
            builder.setNumberParts(Long.toString(phoneNumber.getNationalNumber()));
        }
        return builder.build();
    }

    private static String splitAtNonNumerics(CharSequence number) {
        StringBuilder sb = new StringBuilder(number.length());
        int i = 0;
        while (true) {
            Object obj = " ";
            if (i < number.length()) {
                if (is12Key(number.charAt(i))) {
                    obj = Character.valueOf(number.charAt(i));
                }
                sb.append(obj);
                i++;
            } else {
                return sb.toString().replaceAll(" +", " ").trim();
            }
        }
    }

    private static String getCurrentIdp(boolean useNanp) {
        if (useNanp) {
            return NANP_IDP_STRING;
        }
        String ps = TelephonyProperties.operator_idp_string().orElse(PLUS_SIGN_STRING);
        return ps;
    }

    private static boolean isTwoToNine(char c) {
        if (c >= '2' && c <= '9') {
            return true;
        }
        return false;
    }

    private static int getFormatTypeFromCountryCode(String country) {
        if (TelephonyFeatures.isCountrySpecific(0, "KOR")
                && KOREA_ISO_COUNTRY_CODE.compareToIgnoreCase(country) == 0) {
            return 82;
        }
        int length = NANP_COUNTRIES.length;
        for (int i = 0; i < length; i++) {
            if (NANP_COUNTRIES[i].compareToIgnoreCase(country) == 0) {
                return 1;
            }
        }
        return "jp".compareToIgnoreCase(country) == 0 ? 2 : 0;
    }

    public static boolean isNanp(String dialStr) {
        if (dialStr != null) {
            if (dialStr.length() != 10
                    || !isTwoToNine(dialStr.charAt(0))
                    || !isTwoToNine(dialStr.charAt(3))) {
                return false;
            }
            for (int i = 1; i < 10; i++) {
                char c = dialStr.charAt(i);
                if (!isISODigit(c)) {
                    return false;
                }
            }
            return true;
        }
        com.android.telephony.Rlog.e("isNanp: null dialStr passed in", dialStr);
        return false;
    }

    private static boolean isOneNanp(String dialStr) {
        if (dialStr != null) {
            String newDialStr = dialStr.substring(1);
            if (dialStr.charAt(0) != '1' || !isNanp(newDialStr)) {
                return false;
            }
            return true;
        }
        com.android.telephony.Rlog.e("isOneNanp: null dialStr passed in", dialStr);
        return false;
    }

    @SystemApi
    public static boolean isUriNumber(String number) {
        return number != null && (number.contains("@") || number.contains("%40"));
    }

    @SystemApi
    public static String getUsernameFromUriNumber(String number) {
        int delimiterIndex = number.indexOf(64);
        if (delimiterIndex < 0) {
            delimiterIndex = number.indexOf("%40");
        }
        if (delimiterIndex < 0) {
            com.android.telephony.Rlog.w(
                    LOG_TAG,
                    "getUsernameFromUriNumber: no delimiter found in SIP addr '"
                            + SemTelephonyUtils.maskPii(number)
                            + "'");
            delimiterIndex = number.length();
        }
        return number.substring(0, delimiterIndex);
    }

    public static Uri convertSipUriToTelUri(Uri source) {
        String scheme = source.getScheme();
        if (!"sip".equals(scheme)) {
            return source;
        }
        String number = source.getSchemeSpecificPart();
        String[] numberParts = number.split("[@;:]");
        if (numberParts.length == 0) {
            return source;
        }
        String number2 = numberParts[0];
        return Uri.fromParts(PhoneAccount.SCHEME_TEL, number2, null);
    }

    private static String processPlusCode(String networkDialStr, boolean useNanp) {
        if (networkDialStr == null
                || networkDialStr.charAt(0) != '+'
                || networkDialStr.length() <= 1) {
            return networkDialStr;
        }
        String newStr = networkDialStr.substring(1);
        if (useNanp && isOneNanp(newStr)) {
            log("processPlusCode - Remove the leading plus sign");
            return newStr;
        }
        String retStr = networkDialStr.replaceFirst("[+]", getCurrentIdp(useNanp));
        log(
                "processPlusCode - Replaces the plus sign with the default IDP. useNanp: "
                        + useNanp
                        + ", current IDP: "
                        + getCurrentIdp(useNanp));
        return retStr;
    }

    private static String processPlusCodeForSpr(
            String networkDialStr, int phoneId, Context context) {
        if (networkDialStr == null
                || networkDialStr.charAt(0) != '+'
                || networkDialStr.length() <= 1) {
            return networkDialStr;
        }
        String newStr = networkDialStr.substring(1);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isUSDialingValue = sp.getBoolean("toggle_country_name", true);
        if (isOneNanp(newStr) && isUSDialingValue) {
            String iso =
                    ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                            .getNetworkCountryIso(phoneId);
            String simIso = TelephonyManager.getSimCountryIsoForPhone(phoneId);
            log("processPlusCodeForSpr - ISO: " + iso + ", SIM ISO: " + simIso);
            if (networkDialStr.length() <= 2
                    || getFormatTypeFromCountryCode(iso) != 1
                    || getFormatTypeFromCountryCode(simIso) != 1) {
                return networkDialStr;
            }
            String retStr = networkDialStr.substring(2);
            log("processPlusCodeForSpr - Remove the leading plus sign and 1");
            return retStr;
        }
        String nanpIDPString = sp.getString(CallLog.Calls.SEM_COUNTRY_CODE, NANP_IDP_STRING);
        String retStr2 = networkDialStr.replaceFirst("[+]", nanpIDPString);
        log(
                "processPlusCodeForSpr - Replaces the plus sign with the NANP IDP (NANP IDP: "
                        + nanpIDPString
                        + NavigationBarInflaterView.KEY_CODE_END);
        return retStr2;
    }

    private static int findDialableIndexFromPostDialStr(String postDialStr) {
        for (int index = 0; index < postDialStr.length(); index++) {
            char c = postDialStr.charAt(index);
            if (isReallyDialable(c)) {
                return index;
            }
        }
        return -1;
    }

    private static String appendPwCharBackToOrigDialStr(
            int dialableIndex, String origStr, String dialStr) {
        if (dialableIndex == 1) {
            StringBuilder ret = new StringBuilder(origStr);
            String retStr = ret.append(dialStr.charAt(0)).toString();
            return retStr;
        }
        String nonDigitStr = dialStr.substring(0, dialableIndex);
        String retStr2 = origStr.concat(nonDigitStr);
        return retStr2;
    }

    private static boolean matchIntlPrefix(String a, int len) {
        int state = 0;
        for (int i = 0; i < len; i++) {
            char c = a.charAt(i);
            switch (state) {
                case 0:
                    if (c == '+') {
                        state = 1;
                        break;
                    } else if (c == '0') {
                        state = 2;
                        break;
                    } else {
                        if (isNonSeparator(c)) {
                            return false;
                        }
                        break;
                    }
                case 1:
                case 3:
                default:
                    if (isNonSeparator(c)) {
                        return false;
                    }
                    break;
                case 2:
                    if (c == '0') {
                        state = 3;
                        break;
                    } else if (c == '1') {
                        state = 4;
                        break;
                    } else {
                        if (isNonSeparator(c)) {
                            return false;
                        }
                        break;
                    }
                case 4:
                    if (c == '1') {
                        state = 5;
                        break;
                    } else {
                        if (isNonSeparator(c)) {
                            return false;
                        }
                        break;
                    }
            }
        }
        return state == 1 || state == 3 || state == 5;
    }

    private static boolean matchIntlPrefixAndCC(String a, int len) {
        int state = 0;
        for (int i = 0; i < len; i++) {
            char c = a.charAt(i);
            switch (state) {
                case 0:
                    if (c == '+') {
                        state = 1;
                        break;
                    } else if (c == '0') {
                        state = 2;
                        break;
                    } else {
                        if (isNonSeparator(c)) {
                            return false;
                        }
                        break;
                    }
                case 1:
                case 3:
                case 5:
                    if (isISODigit(c)) {
                        state = 6;
                        break;
                    } else {
                        if (isNonSeparator(c)) {
                            return false;
                        }
                        break;
                    }
                case 2:
                    if (c == '0') {
                        state = 3;
                        break;
                    } else if (c == '1') {
                        state = 4;
                        break;
                    } else {
                        if (isNonSeparator(c)) {
                            return false;
                        }
                        break;
                    }
                case 4:
                    if (c == '1') {
                        state = 5;
                        break;
                    } else {
                        if (isNonSeparator(c)) {
                            return false;
                        }
                        break;
                    }
                case 6:
                case 7:
                    if (isISODigit(c)) {
                        state++;
                        break;
                    } else {
                        if (isNonSeparator(c)) {
                            return false;
                        }
                        break;
                    }
                default:
                    if (isNonSeparator(c)) {
                        return false;
                    }
                    break;
            }
        }
        return state == 6 || state == 7 || state == 8;
    }

    private static boolean matchTrunkPrefix(String a, int len) {
        boolean found = false;
        for (int i = 0; i < len; i++) {
            char c = a.charAt(i);
            if (c == '0' && !found) {
                found = true;
            } else if (isNonSeparator(c)) {
                return false;
            }
        }
        return found;
    }

    private static boolean isCountryCallingCode(int countryCallingCodeCandidate) {
        return countryCallingCodeCandidate > 0
                && countryCallingCodeCandidate < CCC_LENGTH
                && COUNTRY_CALLING_CALL[countryCallingCodeCandidate];
    }

    private static int tryGetISODigit(char ch) {
        if ('0' <= ch && ch <= '9') {
            return ch - '0';
        }
        return -1;
    }

    private static class CountryCallingCodeAndNewIndex {
        public final int countryCallingCode;
        public final int newIndex;

        public CountryCallingCodeAndNewIndex(int countryCode, int newIndex) {
            this.countryCallingCode = countryCode;
            this.newIndex = newIndex;
        }
    }

    private static CountryCallingCodeAndNewIndex tryGetCountryCallingCodeAndNewIndex(
            String str, boolean acceptThailandCase) {
        int state = 0;
        int ccc = 0;
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char ch = str.charAt(i);
            switch (state) {
                case 0:
                    if (ch == '+') {
                        state = 1;
                        break;
                    } else if (ch == '0') {
                        state = 2;
                        break;
                    } else if (ch == '1') {
                        if (!acceptThailandCase) {
                            return null;
                        }
                        state = 8;
                        break;
                    } else {
                        if (isDialable(ch)) {
                            return null;
                        }
                        break;
                    }
                case 1:
                case 3:
                case 5:
                case 6:
                case 7:
                    int ret = tryGetISODigit(ch);
                    if (ret > 0) {
                        ccc = (ccc * 10) + ret;
                        if (ccc >= 100 || isCountryCallingCode(ccc)) {
                            return new CountryCallingCodeAndNewIndex(ccc, i + 1);
                        }
                        if (state == 1 || state == 3 || state == 5) {
                            state = 6;
                            break;
                        } else {
                            state++;
                            break;
                        }
                    } else {
                        if (isDialable(ch)) {
                            return null;
                        }
                        break;
                    }
                    break;
                case 2:
                    if (ch == '0') {
                        state = 3;
                        break;
                    } else if (ch == '1') {
                        state = 4;
                        break;
                    } else {
                        if (isDialable(ch)) {
                            return null;
                        }
                        break;
                    }
                case 4:
                    if (ch == '1') {
                        state = 5;
                        break;
                    } else {
                        if (isDialable(ch)) {
                            return null;
                        }
                        break;
                    }
                case 8:
                    if (ch == '6') {
                        state = 9;
                        break;
                    } else {
                        if (isDialable(ch)) {
                            return null;
                        }
                        break;
                    }
                case 9:
                    if (ch == '6') {
                        return new CountryCallingCodeAndNewIndex(66, i + 1);
                    }
                    return null;
                default:
                    return null;
            }
        }
        return null;
    }

    private static int tryGetTrunkPrefixOmittedIndex(String str, int currentIndex) {
        int length = str.length();
        for (int i = currentIndex; i < length; i++) {
            char ch = str.charAt(i);
            if (tryGetISODigit(ch) >= 0) {
                return i + 1;
            }
            if (isDialable(ch)) {
                return -1;
            }
        }
        return -1;
    }

    private static boolean checkPrefixIsIgnorable(String str, int forwardIndex, int backwardIndex) {
        boolean trunk_prefix_was_read = false;
        while (backwardIndex >= forwardIndex) {
            if (tryGetISODigit(str.charAt(backwardIndex)) >= 0) {
                if (trunk_prefix_was_read) {
                    return false;
                }
                trunk_prefix_was_read = true;
            } else if (isDialable(str.charAt(backwardIndex))) {
                return false;
            }
            backwardIndex--;
        }
        return true;
    }

    private static int getDefaultVoiceSubId() {
        return SubscriptionManager.getDefaultVoiceSubscriptionId();
    }

    public static String convertToEmergencyNumber(Context context, String number) {
        if (context == null || TextUtils.isEmpty(number)) {
            return number;
        }
        String normalizedNumber = normalizeNumber(number);
        if (isEmergencyNumber(normalizedNumber)) {
            return number;
        }
        if (sConvertToEmergencyMap == null) {
            sConvertToEmergencyMap =
                    context.getResources()
                            .getStringArray(R.array.config_convert_to_emergency_number_map);
        }
        if (sConvertToEmergencyMap == null || sConvertToEmergencyMap.length == 0) {
            return number;
        }
        for (String convertMap : sConvertToEmergencyMap) {
            String[] entry = null;
            String[] filterNumbers = null;
            String convertedNumber = null;
            if (!TextUtils.isEmpty(convertMap)) {
                entry = convertMap.split(":");
            }
            if (entry != null && entry.length == 2) {
                convertedNumber = entry[1];
                if (!TextUtils.isEmpty(entry[0])) {
                    filterNumbers = entry[0].split(",");
                }
            }
            if (!TextUtils.isEmpty(convertedNumber)
                    && filterNumbers != null
                    && filterNumbers.length != 0) {
                for (String filterNumber : filterNumbers) {
                    if (!TextUtils.isEmpty(filterNumber) && filterNumber.equals(normalizedNumber)) {
                        return convertedNumber;
                    }
                }
            }
        }
        return number;
    }

    public static boolean areSamePhoneNumber(
            String number1, String number2, String defaultCountryIso) {
        PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        if (defaultCountryIso != null) {
            defaultCountryIso = defaultCountryIso.toUpperCase(Locale.ROOT);
        }
        try {
            Phonenumber.PhoneNumber n1 = util.parseAndKeepRawInput(number1, defaultCountryIso);
            Phonenumber.PhoneNumber n2 = util.parseAndKeepRawInput(number2, defaultCountryIso);
            PhoneNumberUtil.MatchType matchType = util.isNumberMatch(n1, n2);
            if (matchType == PhoneNumberUtil.MatchType.EXACT_MATCH
                    || matchType == PhoneNumberUtil.MatchType.NSN_MATCH) {
                return true;
            }
            return matchType == PhoneNumberUtil.MatchType.SHORT_NSN_MATCH
                    && n1.getNationalNumber() == n2.getNationalNumber()
                    && n1.getCountryCode() == n2.getCountryCode();
        } catch (NumberParseException e) {
            return false;
        }
    }

    public static boolean isWpsCallNumber(String number) {
        return number != null
                && (number.startsWith(PREFIX_WPS)
                        || number.startsWith(PREFIX_WPS_CLIR_ACTIVATE)
                        || number.startsWith(PREFIX_WPS_CLIR_DEACTIVATE));
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

    private static byte[] DocomoNumberToCalledPartyBCDHelper(String number, boolean includeLength) {
        int numberLenReal = number.length();
        int numberLenEffective = numberLenReal;
        char c = PLUS_SIGN_CHAR;
        boolean hasPlus = number.indexOf(43) != -1;
        boolean hasSharp = number.indexOf(35) != -1;
        boolean hasStar = number.indexOf(42) != -1;
        if (hasPlus) {
            numberLenEffective--;
        }
        if (numberLenEffective == 0) {
            return null;
        }
        int resultLen = (numberLenEffective + 1) / 2;
        int extraBytes = includeLength ? 1 + 1 : 1;
        int resultLen2 = resultLen + extraBytes;
        byte[] result = new byte[resultLen2];
        int digitCount = 0;
        int i = 0;
        while (i < numberLenReal) {
            char c2 = number.charAt(i);
            if (c2 != c) {
                int shift = (digitCount & 1) == 1 ? 4 : 0;
                int i2 = extraBytes + (digitCount >> 1);
                result[i2] = (byte) (result[i2] | ((byte) ((charToBCD(c2) & 15) << shift)));
                digitCount++;
            }
            i++;
            c = PLUS_SIGN_CHAR;
        }
        if ((digitCount & 1) == 1) {
            int i3 = (digitCount >> 1) + extraBytes;
            result[i3] = (byte) (result[i3] | 240);
        }
        int offset = 0;
        if (includeLength) {
            int offset2 = 0 + 1;
            result[0] = (byte) (resultLen2 - 1);
            offset = offset2;
        }
        result[offset] = (byte) (hasPlus ? 145 : 129);
        if (hasSharp || hasStar) {
            result[offset] = (byte) (result[offset] & 240);
        }
        log("TOA: " + ((int) result[offset]));
        return result;
    }

    public static byte[] docomoNetworkPortionToCalledPartyBCD(String s) {
        String networkPortion = extractNetworkPortion(s);
        return DocomoNumberToCalledPartyBCDHelper(networkPortion, false);
    }

    private static boolean startWithCountryCode(String number, Context context) {
        if (number.length() == 12
                && (number.startsWith("7")
                        || number.startsWith("20")
                        || number.startsWith("65")
                        || number.startsWith("90"))) {
            log("length 12 - 7,20,65,90 is detected");
            return false;
        }
        if (number.length() >= 11) {
            if (number.startsWith("1")) {
                log("US country code is detected with more than 11 digits");
                return false;
            }
            ContentResolver ContryCode = context.getContentResolver();
            mCursorCountry = ContryCode.query(MCC_OTA_URI, null, null, null, null);
            if (mCursorCountry != null) {
                mCursorCountry.moveToFirst();
                while (!mCursorCountry.isAfterLast()) {
                    if (number.startsWith(mCursorCountry.getString(6))) {
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

    /* JADX WARN: Code restructure failed: missing block: B:110:0x0131, code lost:

       if (r3 != 11) goto L55;
    */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x0133, code lost:

       if ('1' == r5) goto L54;
    */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x013c, code lost:

       r15 = r2.substring(r3 - 11);
    */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0146, code lost:

       if (r11 == false) goto L58;
    */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x0148, code lost:

       r19 = android.telephony.PhoneNumberUtils.otaCountryIDDPrefix;
       r4 = r2.substring(android.telephony.PhoneNumberUtils.otaCountryIDDPrefix.length(), r2.length());
    */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x015f, code lost:

       if (r11 == false) goto L72;
    */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x0161, code lost:

       r13 = android.telephony.PhoneNumberUtils.otaCountryIDDPrefix.length();
       r13 = r2.substring(r13);
    */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x0170, code lost:

       if (isOneNanp(r15) == false) goto L65;
    */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x017c, code lost:

       if (r2.length() != (r13 + 11)) goto L66;
    */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x017e, code lost:

       r10.append(r13);
    */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x01a5, code lost:

       return r10.toString();
    */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x018a, code lost:

       if (startWithCountryCode(r4, r22) == false) goto L69;
    */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x018c, code lost:

       com.android.telephony.Rlog.d(android.telephony.PhoneNumberUtils.LOG_TAG, "Found Country Code after IDD");
       r10.append(r2);
       r10.replace(0, r13, android.telephony.PhoneNumberUtils.NANP_IDP_STRING);
    */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x0199, code lost:

       com.android.telephony.Rlog.d(android.telephony.PhoneNumberUtils.LOG_TAG, "No Condition");
       r10.append(r2);
    */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x01ac, code lost:

       if ('+' != r5) goto L85;
    */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x01ae, code lost:

       r6 = r2.substring(1);
    */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x01b7, code lost:

       if (isOneNanp(r6) == false) goto L79;
    */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x01bf, code lost:

       if (r2.length() != 12) goto L79;
    */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x01c1, code lost:

       r10.append(r6);
    */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x01de, code lost:

       return r10.toString();
    */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x01c9, code lost:

       if (startWithCountryCode(r6, r22) == false) goto L82;
    */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x01cb, code lost:

       r10.append(android.telephony.PhoneNumberUtils.NANP_IDP_STRING);
       r10.append(r6);
    */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x01d2, code lost:

       com.android.telephony.Rlog.d(android.telephony.PhoneNumberUtils.LOG_TAG, "1NANP is not matched");
       r10.append(r2);
    */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x01e3, code lost:

       if (startWithCountryCode(r2, r22) == false) goto L91;
    */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x01e5, code lost:

       r10.append(android.telephony.PhoneNumberUtils.NANP_IDP_STRING);
       r10.append(r2);
    */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x01ef, code lost:

       return r10.toString();
    */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x015d, code lost:

       r4 = null;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String convertSMSDestinationAddress(
            java.lang.String r21, android.content.Context r22, int r23) {
        /*
            Method dump skipped, instructions count: 800
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.telephony.PhoneNumberUtils.convertSMSDestinationAddress(java.lang.String,"
                    + " android.content.Context, int):java.lang.String");
    }

    private static boolean retrieveAssistedParams(
            int subId, String phoneNumber, String mdn, Context context) {
        int phoneId = SubscriptionManager.getPhoneId(subId);
        if ("LRA".equals(TelephonyFeatures.getSubOperatorName(phoneId))) {
            adLog("Assisted Dial not supported");
            return false;
        }
        numberLength = extractNetworkPortionAlt(phoneNumber).length();
        isNetRoaming =
                ((TelephonyManager) context.getSystemService("phone")).isNetworkRoaming(subId);
        int phoneType =
                ((TelephonyManager) context.getSystemService("phone")).getCurrentPhoneType(subId);
        if (!TextUtils.isEmpty(mdn) && mdn.length() >= 3) {
            try {
                ContentResolver cr = context.getContentResolver();
                mCursor = cr.query(REF_COUNTRY_SHARED_PREF, null, null, null, null);
                if (mCursor == null) {
                    adLog("Invalid Reference Country");
                    return false;
                }
                mCursor.moveToFirst();
                refCountryName = mCursor.getString(1);
                String refmcc = mCursor.getString(2);
                String str = "430";
                refCountryMCC =
                        refmcc.equals("310 to 316")
                                ? "310"
                                : refmcc.equals("430 to 431") ? "430" : refmcc;
                refCountryIDDPrefix = mCursor.getString(3);
                refCountryNDDPrefix = mCursor.getString(4);
                isNANPCountry = mCursor.getString(5).equals("NANP");
                refCountryCountryCode = mCursor.getString(6);
                refCountryAreaCode = mCursor.getString(8);
                try {
                    if (refCountryAreaCode == null) {
                        if (mdn.length() >= 3) {
                            refCountryAreaCode = mdn.substring(0, 3);
                        } else {
                            adLog("Wrong MDN. Use default reference country area code");
                            refCountryAreaCode = "123";
                        }
                    }
                    String assistedDialingNnl = mCursor.getString(9);
                    if (mdn.length() >= 3) {
                        refCountryNationalNumberLength = mdn.length();
                    } else {
                        refCountryNationalNumberLength = 10;
                    }
                    adLog(
                            "refCountryNationalNumberLength - MDN length: "
                                    + mdn.length()
                                    + ", DB: "
                                    + assistedDialingNnl);
                    if (!TextUtils.isEmpty(assistedDialingNnl)) {
                        try {
                            refCountryNationalNumberLength = Integer.parseInt(assistedDialingNnl);
                        } catch (NumberFormatException e) {
                            adLog("Can't parse the NationalNumberLength as integer");
                        }
                    }
                    adLog("refCountryMCC: " + refCountryMCC);
                    if (mCursor != null) {
                        mCursor.close();
                    }
                    isGSMRegistered = phoneType == 1;
                    isCDMARegistered = phoneType == 2;
                    try {
                        mCursor = getOtaCountry(subId, context, true);
                        otaCountryMCC = null;
                        if (mCursor != null && mCursor.moveToFirst()) {
                            otaCountryName = mCursor.getString(1);
                            otaCountryMCC = mCursor.getString(2);
                            otaCountryIDDPrefix = mCursor.getString(3);
                            otaCountryNDDPrefix = mCursor.getString(4);
                            if (otaCountryNDDPrefix == null) {
                                otaCountryNDDPrefix = "";
                            }
                            isOTANANPCountry = mCursor.getString(5).equals("NANP");
                            otaCountryCountryCode = mCursor.getString(6);
                            if (otaCountryMCC.equals("310 to 316")) {
                                str = "310";
                            } else if (!otaCountryMCC.equals("430 to 431")) {
                                str = otaCountryMCC;
                            }
                            otaCountryMCC = str;
                        }
                        if (mCursor != null) {
                            mCursor.close();
                        }
                        if (otaCountryMCC == null) {
                            adLog("OTA country not found");
                            return false;
                        }
                        displayAssistedParams();
                        return true;
                    } finally {
                        if (mCursor != null) {
                            mCursor.close();
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    if (mCursor != null) {
                        mCursor.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        adLog("Wrong MDN");
        return false;
    }

    private static void displayAssistedParams() {
        adLog(
                "refCountryName: ("
                        + refCountryName
                        + "), refCountryMCC: "
                        + refCountryMCC
                        + ", refCountryIDDPrefix: "
                        + refCountryIDDPrefix
                        + ", refCountryNDDPrefix: "
                        + refCountryNDDPrefix
                        + ", refCountryAreaCode: "
                        + refCountryAreaCode
                        + ", refCountryNationalNumberLength: "
                        + refCountryNationalNumberLength
                        + ", isNANPCountry: "
                        + isNANPCountry
                        + ", refCountryCountryCode: "
                        + refCountryCountryCode
                        + ", isGSMRegistered: "
                        + isGSMRegistered
                        + ", isCDMARegistered: "
                        + isCDMARegistered);
        adLog(
                "isNetRoaming: "
                        + isNetRoaming
                        + ", numberLength: "
                        + numberLength
                        + ", otaCountryName: ("
                        + otaCountryName
                        + "), otaCountryMCC: "
                        + otaCountryMCC
                        + ", otaCountryIDDPrefix: "
                        + otaCountryIDDPrefix
                        + ", otaCountryNDDPrefix: "
                        + otaCountryNDDPrefix
                        + ", isOTANANPCountry: "
                        + isOTANANPCountry
                        + ", otaCountryCountryCode: "
                        + otaCountryCountryCode);
    }

    private static void adLog(String msg) {
        com.android.telephony.Rlog.d("AssistedDialing", msg);
    }

    private static Cursor getOtaCountry(int subId, Context context, boolean useSharedPreference) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String spOtaCountryMcc = sp.getString(OTA_COUNTRY_MCC_KEY, null);
        ContentResolver otacr = context.getContentResolver();
        if (!useSharedPreference || spOtaCountryMcc == null) {
            Cursor c =
                    otacr.query(
                            OTA_COUNTRY_URI.buildUpon().fragment(String.valueOf(subId)).build(),
                            null,
                            null,
                            null,
                            null);
            return c;
        }
        Cursor c2 = otacr.query(MCC_OTA_URI, null, "mcc=?", new String[] {spOtaCountryMcc}, null);
        return c2;
    }
}
