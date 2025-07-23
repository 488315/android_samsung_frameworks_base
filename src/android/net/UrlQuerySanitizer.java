package android.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public class UrlQuerySanitizer {
    private boolean mAllowUnregisteredParamaters;
    private boolean mPreferFirstRepeatedParameter;
    private static final ValueSanitizer sAllIllegal = new IllegalCharacterValueSanitizer(0);
    private static final ValueSanitizer sAllButNulLegal = new IllegalCharacterValueSanitizer(1535);
    private static final ValueSanitizer sAllButWhitespaceLegal = new IllegalCharacterValueSanitizer(1532);
    private static final ValueSanitizer sURLLegal = new IllegalCharacterValueSanitizer(404);
    private static final ValueSanitizer sUrlAndSpaceLegal = new IllegalCharacterValueSanitizer(405);
    private static final ValueSanitizer sAmpLegal = new IllegalCharacterValueSanitizer(128);
    private static final ValueSanitizer sAmpAndSpaceLegal = new IllegalCharacterValueSanitizer(129);
    private static final ValueSanitizer sSpaceLegal = new IllegalCharacterValueSanitizer(1);
    private static final ValueSanitizer sAllButNulAndAngleBracketsLegal = new IllegalCharacterValueSanitizer(1439);
    private static final Pattern plusOrPercent = Pattern.compile("[+%]");
    private final HashMap<String, ValueSanitizer> mSanitizers = new HashMap<>();
    private final HashMap<String, String> mEntries = new HashMap<>();
    private final ArrayList<ParameterValuePair> mEntriesList = new ArrayList<>();
    private ValueSanitizer mUnregisteredParameterValueSanitizer = getAllIllegal();

    public interface ValueSanitizer {
        String sanitize(String str);
    }

    protected int decodeHexDigit(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return c - '7';
        }
        if (c < 'a' || c > 'f') {
            return -1;
        }
        return c - 'W';
    }

    public class ParameterValuePair {
        public String mParameter;
        public String mValue;

        public ParameterValuePair(UrlQuerySanitizer urlQuerySanitizer, String str, String str2) {
            this.mParameter = str;
            this.mValue = str2;
        }
    }

    public static class IllegalCharacterValueSanitizer implements ValueSanitizer {
        public static final int ALL_BUT_NUL_AND_ANGLE_BRACKETS_LEGAL = 1439;
        public static final int ALL_BUT_NUL_LEGAL = 1535;
        public static final int ALL_BUT_WHITESPACE_LEGAL = 1532;
        public static final int ALL_ILLEGAL = 0;
        public static final int ALL_OK = 2047;
        public static final int ALL_WHITESPACE_OK = 3;
        public static final int AMP_AND_SPACE_LEGAL = 129;
        public static final int AMP_LEGAL = 128;
        public static final int AMP_OK = 128;
        public static final int DQUOTE_OK = 8;
        public static final int GT_OK = 64;
        private static final String JAVASCRIPT_PREFIX = "javascript:";
        public static final int LT_OK = 32;
        private static final int MIN_SCRIPT_PREFIX_LENGTH = Math.min(11, 9);
        public static final int NON_7_BIT_ASCII_OK = 4;
        public static final int NUL_OK = 512;
        public static final int OTHER_WHITESPACE_OK = 2;
        public static final int PCT_OK = 256;
        public static final int SCRIPT_URL_OK = 1024;
        public static final int SPACE_LEGAL = 1;
        public static final int SPACE_OK = 1;
        public static final int SQUOTE_OK = 16;
        public static final int URL_AND_SPACE_LEGAL = 405;
        public static final int URL_LEGAL = 404;
        private static final String VBSCRIPT_PREFIX = "vbscript:";
        private int mFlags;

        private boolean isWhitespace(char c) {
            if (c == ' ') {
                return true;
            }
            switch (c) {
                case '\t':
                case '\n':
                case 11:
                case '\f':
                case '\r':
                    return true;
                default:
                    return false;
            }
        }

        public IllegalCharacterValueSanitizer(int i) {
            this.mFlags = i;
        }

        @Override // android.net.UrlQuerySanitizer.ValueSanitizer
        public String sanitize(String str) {
            if (str == null) {
                return null;
            }
            int length = str.length();
            if ((this.mFlags & 1024) == 0 && length >= MIN_SCRIPT_PREFIX_LENGTH) {
                String lowerCase = str.toLowerCase(Locale.ROOT);
                if (lowerCase.startsWith(JAVASCRIPT_PREFIX) || lowerCase.startsWith(VBSCRIPT_PREFIX)) {
                    return "";
                }
            }
            if ((this.mFlags & 3) == 0) {
                str = trimWhitespace(str);
                length = str.length();
            }
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                char charAt = str.charAt(i);
                if (!characterIsLegal(charAt)) {
                    charAt = (this.mFlags & 1) != 0 ? ' ' : '_';
                }
                sb.append(charAt);
            }
            return sb.toString();
        }

        private String trimWhitespace(String str) {
            int length = str.length() - 1;
            int i = 0;
            while (i <= length && isWhitespace(str.charAt(i))) {
                i++;
            }
            int i2 = length;
            while (i2 >= i && isWhitespace(str.charAt(i2))) {
                i2--;
            }
            return (i == 0 && i2 == length) ? str : str.substring(i, i2 + 1);
        }

        private boolean characterIsLegal(char c) {
            if (c == 0) {
                return (this.mFlags & 512) != 0;
            }
            if (c == ' ') {
                return (this.mFlags & 1) != 0;
            }
            if (c == '\"') {
                return (this.mFlags & 8) != 0;
            }
            if (c == '<') {
                return (this.mFlags & 32) != 0;
            }
            if (c == '>') {
                return (this.mFlags & 64) != 0;
            }
            switch (c) {
                case '\t':
                case '\n':
                case 11:
                case '\f':
                case '\r':
                    return (this.mFlags & 2) != 0;
                default:
                    switch (c) {
                        case '%':
                            return (this.mFlags & 256) != 0;
                        case '&':
                            return (this.mFlags & 128) != 0;
                        case '\'':
                            return (this.mFlags & 16) != 0;
                        default:
                            return (c >= ' ' && c < 127) || (c >= 128 && (this.mFlags & 4) != 0);
                    }
            }
        }
    }

    public ValueSanitizer getUnregisteredParameterValueSanitizer() {
        return this.mUnregisteredParameterValueSanitizer;
    }

    public void setUnregisteredParameterValueSanitizer(ValueSanitizer valueSanitizer) {
        this.mUnregisteredParameterValueSanitizer = valueSanitizer;
    }

    public static final ValueSanitizer getAllIllegal() {
        return sAllIllegal;
    }

    public static final ValueSanitizer getAllButNulLegal() {
        return sAllButNulLegal;
    }

    public static final ValueSanitizer getAllButWhitespaceLegal() {
        return sAllButWhitespaceLegal;
    }

    public static final ValueSanitizer getUrlLegal() {
        return sURLLegal;
    }

    public static final ValueSanitizer getUrlAndSpaceLegal() {
        return sUrlAndSpaceLegal;
    }

    public static final ValueSanitizer getAmpLegal() {
        return sAmpLegal;
    }

    public static final ValueSanitizer getAmpAndSpaceLegal() {
        return sAmpAndSpaceLegal;
    }

    public static final ValueSanitizer getSpaceLegal() {
        return sSpaceLegal;
    }

    public static final ValueSanitizer getAllButNulAndAngleBracketsLegal() {
        return sAllButNulAndAngleBracketsLegal;
    }

    public UrlQuerySanitizer() {
    }

    public UrlQuerySanitizer(String str) {
        setAllowUnregisteredParamaters(true);
        parseUrl(str);
    }

    public void parseUrl(String str) {
        String str2;
        int indexOf = str.indexOf(63);
        if (indexOf >= 0) {
            str2 = str.substring(indexOf + 1);
        } else {
            str2 = "";
        }
        parseQuery(str2);
    }

    public void parseQuery(String str) {
        clear();
        StringTokenizer stringTokenizer = new StringTokenizer(str, "&");
        while (stringTokenizer.hasMoreElements()) {
            String nextToken = stringTokenizer.nextToken();
            if (nextToken.length() > 0) {
                int indexOf = nextToken.indexOf(61);
                if (indexOf < 0) {
                    parseEntry(nextToken, "");
                } else {
                    parseEntry(nextToken.substring(0, indexOf), nextToken.substring(indexOf + 1));
                }
            }
        }
    }

    public Set<String> getParameterSet() {
        return this.mEntries.keySet();
    }

    public List<ParameterValuePair> getParameterList() {
        return this.mEntriesList;
    }

    public boolean hasParameter(String str) {
        return this.mEntries.containsKey(str);
    }

    public String getValue(String str) {
        return this.mEntries.get(str);
    }

    public void registerParameter(String str, ValueSanitizer valueSanitizer) {
        if (valueSanitizer == null) {
            this.mSanitizers.remove(str);
        }
        this.mSanitizers.put(str, valueSanitizer);
    }

    public void registerParameters(String[] strArr, ValueSanitizer valueSanitizer) {
        for (String str : strArr) {
            this.mSanitizers.put(str, valueSanitizer);
        }
    }

    public void setAllowUnregisteredParamaters(boolean z) {
        this.mAllowUnregisteredParamaters = z;
    }

    public boolean getAllowUnregisteredParamaters() {
        return this.mAllowUnregisteredParamaters;
    }

    public void setPreferFirstRepeatedParameter(boolean z) {
        this.mPreferFirstRepeatedParameter = z;
    }

    public boolean getPreferFirstRepeatedParameter() {
        return this.mPreferFirstRepeatedParameter;
    }

    protected void parseEntry(String str, String str2) {
        String unescape = unescape(str);
        ValueSanitizer effectiveValueSanitizer = getEffectiveValueSanitizer(unescape);
        if (effectiveValueSanitizer == null) {
            return;
        }
        addSanitizedEntry(unescape, effectiveValueSanitizer.sanitize(unescape(str2)));
    }

    protected void addSanitizedEntry(String str, String str2) {
        this.mEntriesList.add(new ParameterValuePair(this, str, str2));
        if (this.mPreferFirstRepeatedParameter && this.mEntries.containsKey(str)) {
            return;
        }
        this.mEntries.put(str, str2);
    }

    public ValueSanitizer getValueSanitizer(String str) {
        return this.mSanitizers.get(str);
    }

    public ValueSanitizer getEffectiveValueSanitizer(String str) {
        ValueSanitizer valueSanitizer = getValueSanitizer(str);
        return (valueSanitizer == null && this.mAllowUnregisteredParamaters) ? getUnregisteredParameterValueSanitizer() : valueSanitizer;
    }

    public String unescape(String str) {
        int i;
        Matcher matcher = plusOrPercent.matcher(str);
        if (!matcher.find()) {
            return str;
        }
        int start = matcher.start();
        int length = str.length();
        StringBuilder sb = new StringBuilder(length);
        sb.append(str.substring(0, start));
        while (start < length) {
            char charAt = str.charAt(start);
            if (charAt == '+') {
                charAt = ' ';
            } else if (charAt == '%' && (i = start + 2) < length) {
                char charAt2 = str.charAt(start + 1);
                char charAt3 = str.charAt(i);
                if (isHexDigit(charAt2) && isHexDigit(charAt3)) {
                    charAt = (char) ((decodeHexDigit(charAt2) * 16) + decodeHexDigit(charAt3));
                    start = i;
                }
            }
            sb.append(charAt);
            start++;
        }
        return sb.toString();
    }

    protected boolean isHexDigit(char c) {
        return decodeHexDigit(c) >= 0;
    }

    protected void clear() {
        this.mEntries.clear();
        this.mEntriesList.clear();
    }
}
