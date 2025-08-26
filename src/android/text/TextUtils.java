package android.text;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.icu.lang.UCharacter;
import android.icu.text.CaseMap;
import android.icu.text.Edits;
import android.icu.util.ULocale;
import android.os.Parcel;
import android.os.Parcelable;
import android.sysprop.DisplayProperties;
import android.telecom.Logging.Session;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AccessibilityClickableSpan;
import android.text.style.AccessibilityReplacementSpan;
import android.text.style.AccessibilityURLSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.CharacterStyle;
import android.text.style.EasyEditSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineBackgroundSpan;
import android.text.style.LineBreakConfigSpan;
import android.text.style.LineHeightSpan;
import android.text.style.LocaleSpan;
import android.text.style.NoWritingToolsSpan;
import android.text.style.ParagraphStyle;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ReplacementSpan;
import android.text.style.ScaleXSpan;
import android.text.style.SpellCheckSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuggestionRangeSpan;
import android.text.style.SuggestionSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TtsSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.text.style.UpdateAppearance;
import android.util.EmptyArray;
import android.util.Log;
import android.util.Printer;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Array;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class TextUtils {
    public static final int ABSOLUTE_SIZE_SPAN = 16;
    public static final int ACCESSIBILITY_CLICKABLE_SPAN = 25;
    public static final int ACCESSIBILITY_REPLACEMENT_SPAN = 29;
    public static final int ACCESSIBILITY_URL_SPAN = 26;
    public static final int ALIGNMENT_SPAN = 1;
    public static final int ANNOTATION = 18;
    public static final int BACKGROUND_COLOR_SPAN = 12;
    public static final int BULLET_SPAN = 8;
    public static final int CAP_MODE_CHARACTERS = 4096;
    public static final int CAP_MODE_SENTENCES = 16384;
    public static final int CAP_MODE_WORDS = 8192;
    public static final int EASY_EDIT_SPAN = 22;
    static final char ELLIPSIS_FILLER = 65279;
    private static final String ELLIPSIS_NORMAL = "…";
    private static final String ELLIPSIS_TWO_DOTS = "‥";
    public static final int FIRST_SPAN = 1;
    public static final int FOREGROUND_COLOR_SPAN = 2;
    public static final int LAST_SPAN = 31;
    public static final int LEADING_MARGIN_SPAN = 10;
    public static final int LINE_BACKGROUND_SPAN = 27;
    public static final int LINE_BREAK_CONFIG_SPAN = 30;
    public static final int LINE_FEED_CODE_POINT = 10;
    public static final int LINE_HEIGHT_SPAN = 28;
    public static final int LOCALE_SPAN = 23;
    private static final int NBSP_CODE_POINT = 160;
    public static final int NO_WRITING_TOOLS_SPAN = 31;
    private static final int PARCEL_SAFE_TEXT_LENGTH = 100000;
    public static final int QUOTE_SPAN = 9;
    public static final int RELATIVE_SIZE_SPAN = 3;
    public static final int SAFE_STRING_FLAG_FIRST_LINE = 4;
    public static final int SAFE_STRING_FLAG_SINGLE_LINE = 2;
    public static final int SAFE_STRING_FLAG_TRIM = 1;
    public static final int SCALE_X_SPAN = 4;
    public static final int SPELL_CHECK_SPAN = 20;
    public static final int STRIKETHROUGH_SPAN = 5;
    public static final int STYLE_SPAN = 7;
    public static final int SUBSCRIPT_SPAN = 15;
    public static final int SUGGESTION_RANGE_SPAN = 21;
    public static final int SUGGESTION_SPAN = 19;
    public static final int SUPERSCRIPT_SPAN = 14;
    private static final String TAG = "TextUtils";
    public static final int TEXT_APPEARANCE_SPAN = 17;
    public static final int TTS_SPAN = 24;
    public static final int TYPEFACE_SPAN = 13;
    public static final int UNDERLINE_SPAN = 6;
    public static final int URL_SPAN = 11;
    public static final Parcelable.Creator<CharSequence> CHAR_SEQUENCE_CREATOR = new Parcelable.Creator<CharSequence>() { // from class: android.text.TextUtils.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CharSequence createFromParcel(Parcel parcel) {
            Object standard;
            int i = parcel.readInt();
            String string8 = parcel.readString8();
            if (string8 == null) {
                return null;
            }
            if (i == 1) {
                return string8;
            }
            SpannableString spannableString = new SpannableString(string8);
            while (true) {
                int i2 = parcel.readInt();
                if (i2 == 0) {
                    return spannableString;
                }
                switch (i2) {
                    case 1:
                        standard = new AlignmentSpan.Standard(parcel);
                        break;
                    case 2:
                        standard = new ForegroundColorSpan(parcel);
                        break;
                    case 3:
                        standard = new RelativeSizeSpan(parcel);
                        break;
                    case 4:
                        standard = new ScaleXSpan(parcel);
                        break;
                    case 5:
                        standard = new StrikethroughSpan(parcel);
                        break;
                    case 6:
                        standard = new UnderlineSpan(parcel);
                        break;
                    case 7:
                        standard = new StyleSpan(parcel);
                        break;
                    case 8:
                        standard = new BulletSpan(parcel);
                        break;
                    case 9:
                        standard = new QuoteSpan(parcel);
                        break;
                    case 10:
                        standard = new LeadingMarginSpan.Standard(parcel);
                        break;
                    case 11:
                        standard = new URLSpan(parcel);
                        break;
                    case 12:
                        standard = new BackgroundColorSpan(parcel);
                        break;
                    case 13:
                        standard = new TypefaceSpan(parcel);
                        break;
                    case 14:
                        standard = new SuperscriptSpan(parcel);
                        break;
                    case 15:
                        standard = new SubscriptSpan(parcel);
                        break;
                    case 16:
                        standard = new AbsoluteSizeSpan(parcel);
                        break;
                    case 17:
                        standard = new TextAppearanceSpan(parcel);
                        break;
                    case 18:
                        standard = new Annotation(parcel);
                        break;
                    case 19:
                        standard = new SuggestionSpan(parcel);
                        break;
                    case 20:
                        standard = new SpellCheckSpan(parcel);
                        break;
                    case 21:
                        standard = new SuggestionRangeSpan(parcel);
                        break;
                    case 22:
                        standard = new EasyEditSpan(parcel);
                        break;
                    case 23:
                        standard = new LocaleSpan(parcel);
                        break;
                    case 24:
                        standard = new TtsSpan(parcel);
                        break;
                    case 25:
                        standard = new AccessibilityClickableSpan(parcel);
                        break;
                    case 26:
                        standard = new AccessibilityURLSpan(parcel);
                        break;
                    case 27:
                        standard = new LineBackgroundSpan.Standard(parcel);
                        break;
                    case 28:
                        standard = new LineHeightSpan.Standard(parcel);
                        break;
                    case 29:
                        standard = new AccessibilityReplacementSpan(parcel);
                        break;
                    case 30:
                        standard = LineBreakConfigSpan.CREATOR.createFromParcel(parcel);
                        break;
                    case 31:
                        standard = NoWritingToolsSpan.CREATOR.createFromParcel(parcel);
                        break;
                    default:
                        throw new RuntimeException("bogus span encoding " + i2);
                }
                TextUtils.readSpan(parcel, spannableString, standard);
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CharSequence[] newArray(int i) {
            return new CharSequence[i];
        }
    };
    private static Object sLock = new Object();
    private static char[] sTemp = null;

    public interface EllipsizeCallback {
        void ellipsized(int i, int i2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SafeStringFlags {
    }

    public interface StringSplitter extends Iterable<String> {
        void setString(String str);
    }

    public enum TruncateAt {
        START,
        MIDDLE,
        END,
        MARQUEE,
        END_SMALL,
        SEM_KEYWORD
    }

    static boolean couldAffectRtl(char c) {
        if ((1424 <= c && c <= 2303) || c == 8206 || c == 8207) {
            return true;
        }
        if (8234 <= c && c <= 8238) {
            return true;
        }
        if (8294 <= c && c <= 8297) {
            return true;
        }
        if (55296 <= c && c <= 57343) {
            return true;
        }
        if (64285 > c || c > 65023) {
            return 65136 <= c && c <= 65278;
        }
        return true;
    }

    public static boolean isArabicChar(char c) {
        if (c >= 1536 && c <= 1791) {
            return true;
        }
        if (c < 64256 || c > 65023) {
            return c >= 65136 && c <= 65278;
        }
        return true;
    }

    public static boolean isHalant(char c) {
        return c == 2381 || c == 2509 || c == 2637 || c == 2765 || c == 3021 || c == 3149 || c == 3277 || c == 3405 || c == 3551 || c == 2893;
    }

    public static boolean isIndianChar(char c) {
        return c >= 2304 && c < 3583;
    }

    public static boolean isKhmerChar(char c) {
        return c >= 6016 && c <= 6137;
    }

    public static boolean isKhmerCoengSign(char c) {
        return c == 6098;
    }

    public static boolean isKhmerVowel(char c) {
        return c >= 6070 && c <= 6099;
    }

    public static boolean isLaoChar(char c) {
        if (c < 3713 || c > 3805) {
            return c >= 57345 && c <= 57368;
        }
        return true;
    }

    public static boolean isLaoVowel(char c) {
        if (c == 3761) {
            return true;
        }
        if (c < 3764 || c > 3772) {
            return c >= 3784 && c <= 3789;
        }
        return true;
    }

    public static boolean isMyanmarChar(char c) {
        return c >= 4096 && c <= 4247;
    }

    public static boolean isPrintableAscii(char c) {
        return (' ' <= c && c <= '~') || c == '\r' || c == '\n';
    }

    public static boolean isThaiChar(char c) {
        return c >= 3585 && c < 3675;
    }

    public static boolean isThaiVowel(char c) {
        if (c == 3633) {
            return true;
        }
        if (c < 3635 || c > 3642) {
            return c >= 3655 && c <= 3662;
        }
        return true;
    }

    public static long packRangeInLong(int i, int i2) {
        return i2 | (i << 32);
    }

    public static boolean semNeedMoreWidth(char c) {
        return c == 2305 || c == 2754 || c == 2817 || c == 2847 || c == 2893 || c == 2369 || c == 2370 || c == 2497 || c == 2498 || c == 2914 || c == 2915;
    }

    public static int unpackRangeEndFromLong(long j) {
        return (int) (j & 4294967295L);
    }

    public static int unpackRangeStartFromLong(long j) {
        return (int) (j >>> 32);
    }

    public static String getEllipsisString(TruncateAt truncateAt) {
        return truncateAt == TruncateAt.END_SMALL ? ELLIPSIS_TWO_DOTS : ELLIPSIS_NORMAL;
    }

    private TextUtils() {
    }

    public static void getChars(CharSequence charSequence, int i, int i2, char[] cArr, int i3) {
        Class<?> cls = charSequence.getClass();
        if (cls == String.class) {
            ((String) charSequence).getChars(i, i2, cArr, i3);
            return;
        }
        if (cls == StringBuffer.class) {
            ((StringBuffer) charSequence).getChars(i, i2, cArr, i3);
            return;
        }
        if (cls == StringBuilder.class) {
            ((StringBuilder) charSequence).getChars(i, i2, cArr, i3);
            return;
        }
        if (charSequence instanceof GetChars) {
            ((GetChars) charSequence).getChars(i, i2, cArr, i3);
            return;
        }
        while (i < i2) {
            cArr[i3] = charSequence.charAt(i);
            i++;
            i3++;
        }
    }

    public static int indexOf(CharSequence charSequence, char c) {
        return indexOf(charSequence, c, 0);
    }

    public static int indexOf(CharSequence charSequence, char c, int i) {
        if (charSequence.getClass() == String.class) {
            return ((String) charSequence).indexOf(c, i);
        }
        return indexOf(charSequence, c, i, charSequence.length());
    }

    public static int indexOf(CharSequence charSequence, char c, int i, int i2) {
        Class<?> cls = charSequence.getClass();
        if (!(charSequence instanceof GetChars) && cls != StringBuffer.class && cls != StringBuilder.class && cls != String.class) {
            while (i < i2) {
                if (charSequence.charAt(i) == c) {
                    return i;
                }
                i++;
            }
            return -1;
        }
        char[] cArrObtain = obtain(500);
        while (i < i2) {
            int i3 = i + 500;
            if (i3 > i2) {
                i3 = i2;
            }
            getChars(charSequence, i, i3, cArrObtain, 0);
            int i4 = i3 - i;
            for (int i5 = 0; i5 < i4; i5++) {
                if (cArrObtain[i5] == c) {
                    recycle(cArrObtain);
                    return i5 + i;
                }
            }
            i = i3;
        }
        recycle(cArrObtain);
        return -1;
    }

    public static int lastIndexOf(CharSequence charSequence, char c) {
        return lastIndexOf(charSequence, c, charSequence.length() - 1);
    }

    public static int lastIndexOf(CharSequence charSequence, char c, int i) {
        if (charSequence.getClass() == String.class) {
            return ((String) charSequence).lastIndexOf(c, i);
        }
        return lastIndexOf(charSequence, c, 0, i);
    }

    public static int lastIndexOf(CharSequence charSequence, char c, int i, int i2) {
        if (i2 < 0) {
            return -1;
        }
        if (i2 >= charSequence.length()) {
            i2 = charSequence.length() - 1;
        }
        int i3 = i2 + 1;
        Class<?> cls = charSequence.getClass();
        if (!(charSequence instanceof GetChars) && cls != StringBuffer.class && cls != StringBuilder.class && cls != String.class) {
            while (i2 >= i) {
                if (charSequence.charAt(i2) == c) {
                    return i2;
                }
                i2--;
            }
            return -1;
        }
        char[] cArrObtain = obtain(500);
        while (i < i3) {
            int i4 = i3 - 500;
            if (i4 < i) {
                i4 = i;
            }
            getChars(charSequence, i4, i3, cArrObtain, 0);
            for (int i5 = (i3 - i4) - 1; i5 >= 0; i5--) {
                if (cArrObtain[i5] == c) {
                    recycle(cArrObtain);
                    return i5 + i4;
                }
            }
            i3 = i4;
        }
        recycle(cArrObtain);
        return -1;
    }

    public static int indexOf(CharSequence charSequence, CharSequence charSequence2) {
        return indexOf(charSequence, charSequence2, 0, charSequence.length());
    }

    public static int indexOf(CharSequence charSequence, CharSequence charSequence2, int i) {
        return indexOf(charSequence, charSequence2, i, charSequence.length());
    }

    public static int indexOf(CharSequence charSequence, CharSequence charSequence2, int i, int i2) {
        int length = charSequence2.length();
        if (length == 0) {
            return i;
        }
        char cCharAt = charSequence2.charAt(0);
        while (true) {
            int iIndexOf = indexOf(charSequence, cCharAt, i);
            if (iIndexOf > i2 - length || iIndexOf < 0) {
                return -1;
            }
            if (regionMatches(charSequence, iIndexOf, charSequence2, 0, length)) {
                return iIndexOf;
            }
            i = iIndexOf + 1;
        }
    }

    public static boolean regionMatches(CharSequence charSequence, int i, CharSequence charSequence2, int i2, int i3) {
        int i4 = i3 * 2;
        if (i4 < i3) {
            throw new IndexOutOfBoundsException();
        }
        char[] cArrObtain = obtain(i4);
        boolean z = false;
        getChars(charSequence, i, i + i3, cArrObtain, 0);
        getChars(charSequence2, i2, i2 + i3, cArrObtain, i3);
        int i5 = 0;
        while (true) {
            if (i5 >= i3) {
                z = true;
                break;
            }
            if (cArrObtain[i5] != cArrObtain[i5 + i3]) {
                break;
            }
            i5++;
        }
        recycle(cArrObtain);
        return z;
    }

    public static String substring(CharSequence charSequence, int i, int i2) {
        if (charSequence instanceof String) {
            return ((String) charSequence).substring(i, i2);
        }
        if (charSequence instanceof StringBuilder) {
            return ((StringBuilder) charSequence).substring(i, i2);
        }
        if (charSequence instanceof StringBuffer) {
            return ((StringBuffer) charSequence).substring(i, i2);
        }
        int i3 = i2 - i;
        char[] cArrObtain = obtain(i3);
        getChars(charSequence, i, i2, cArrObtain, 0);
        String str = new String(cArrObtain, 0, i3);
        recycle(cArrObtain);
        return str;
    }

    public static String truncateStringForUtf8Storage(String str, int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException();
        }
        int length = str.length();
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            char cCharAt = str.charAt(i2);
            if (cCharAt < 128) {
                i3++;
            } else if (cCharAt < 2048) {
                i3 += 2;
            } else if (cCharAt < 55296 || cCharAt > 57343 || str.codePointAt(i2) < 65536) {
                i3 += 3;
            } else {
                i3 += 4;
                i2 += i3 > i ? 0 : 1;
            }
            if (i3 > i) {
                return str.substring(0, i2);
            }
            i2++;
        }
        return str;
    }

    public static String join(CharSequence charSequence, Object[] objArr) {
        int length = objArr.length;
        if (length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(objArr[0]);
        for (int i = 1; i < length; i++) {
            sb.append(charSequence);
            sb.append(objArr[i]);
        }
        return sb.toString();
    }

    public static String join(CharSequence charSequence, Iterable iterable) {
        Iterator it = iterable.iterator();
        if (!it.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(it.next());
        while (it.hasNext()) {
            sb.append(charSequence);
            sb.append(it.next());
        }
        return sb.toString();
    }

    public static String[] split(String str, String str2) {
        if (str.length() == 0) {
            return EmptyArray.STRING;
        }
        return str.split(str2, -1);
    }

    public static String[] split(String str, Pattern pattern) {
        if (str.length() == 0) {
            return EmptyArray.STRING;
        }
        return pattern.split(str, -1);
    }

    public static class SimpleStringSplitter implements StringSplitter, Iterator<String> {
        private char mDelimiter;
        private int mLength;
        private int mPosition;
        private String mString;

        @Override // java.lang.Iterable
        public Iterator<String> iterator() {
            return this;
        }

        public SimpleStringSplitter(char c) {
            this.mDelimiter = c;
        }

        @Override // android.text.TextUtils.StringSplitter
        public void setString(String str) {
            this.mString = str;
            this.mPosition = 0;
            this.mLength = str.length();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.mPosition < this.mLength;
        }

        @Override // java.util.Iterator
        public String next() {
            int iIndexOf = this.mString.indexOf(this.mDelimiter, this.mPosition);
            if (iIndexOf == -1) {
                iIndexOf = this.mLength;
            }
            String strSubstring = this.mString.substring(this.mPosition, iIndexOf);
            this.mPosition = iIndexOf + 1;
            return strSubstring;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static CharSequence stringOrSpannedString(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        if (charSequence instanceof SpannedString) {
            return charSequence;
        }
        if (charSequence instanceof Spanned) {
            return new SpannedString(charSequence);
        }
        return charSequence.toString();
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static String nullIfEmpty(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return str;
    }

    public static String emptyIfNull(String str) {
        return str == null ? "" : str;
    }

    public static String firstNotEmpty(String str, String str2) {
        return !isEmpty(str) ? str : (String) Preconditions.checkStringNotEmpty(str2);
    }

    public static int length(String str) {
        if (str != null) {
            return str.length();
        }
        return 0;
    }

    public static String safeIntern(String str) {
        if (str != null) {
            return str.intern();
        }
        return null;
    }

    public static int getTrimmedLength(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        while (i < length && charSequence.charAt(i) <= ' ') {
            i++;
        }
        while (length > i && charSequence.charAt(length - 1) <= ' ') {
            length--;
        }
        return length - i;
    }

    public static boolean equals(CharSequence charSequence, CharSequence charSequence2) {
        int length;
        if (charSequence == charSequence2) {
            return true;
        }
        if (charSequence == null || charSequence2 == null || (length = charSequence.length()) != charSequence2.length()) {
            return false;
        }
        if ((charSequence instanceof String) && (charSequence2 instanceof String)) {
            return charSequence.equals(charSequence2);
        }
        for (int i = 0; i < length; i++) {
            if (charSequence.charAt(i) != charSequence2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    public static CharSequence getReverse(CharSequence charSequence, int i, int i2) {
        return new Reverser(charSequence, i, i2);
    }

    private static class Reverser implements CharSequence, GetChars {
        private int mEnd;
        private CharSequence mSource;
        private int mStart;

        public Reverser(CharSequence charSequence, int i, int i2) {
            this.mSource = charSequence;
            this.mStart = i;
            this.mEnd = i2;
        }

        @Override // java.lang.CharSequence
        public int length() {
            return this.mEnd - this.mStart;
        }

        @Override // java.lang.CharSequence
        public CharSequence subSequence(int i, int i2) {
            char[] cArr = new char[i2 - i];
            getChars(i, i2, cArr, 0);
            return new String(cArr);
        }

        @Override // java.lang.CharSequence
        public String toString() {
            return subSequence(0, length()).toString();
        }

        @Override // java.lang.CharSequence
        public char charAt(int i) {
            return (char) UCharacter.getMirror(this.mSource.charAt((this.mEnd - 1) - i));
        }

        @Override // android.text.GetChars
        public void getChars(int i, int i2, char[] cArr, int i3) {
            CharSequence charSequence = this.mSource;
            int i4 = this.mStart;
            TextUtils.getChars(charSequence, i + i4, i4 + i2, cArr, i3);
            int i5 = i2 - i;
            AndroidCharacter.mirror(cArr, 0, i5);
            int i6 = i5 / 2;
            for (int i7 = 0; i7 < i6; i7++) {
                int i8 = i3 + i7;
                char c = cArr[i8];
                int i9 = ((i3 + i5) - i7) - 1;
                cArr[i8] = cArr[i9];
                cArr[i9] = c;
            }
        }
    }

    public static void writeToParcel(CharSequence charSequence, Parcel parcel, int i) {
        if (charSequence instanceof Spanned) {
            parcel.writeInt(0);
            parcel.writeString8(charSequence.toString());
            Spanned spanned = (Spanned) charSequence;
            for (Object obj : spanned.getSpans(0, charSequence.length(), Object.class)) {
                Object underlying = obj instanceof CharacterStyle ? ((CharacterStyle) obj).getUnderlying() : obj;
                if (underlying instanceof ParcelableSpan) {
                    ParcelableSpan parcelableSpan = (ParcelableSpan) underlying;
                    int spanTypeIdInternal = parcelableSpan.getSpanTypeIdInternal();
                    if (spanTypeIdInternal < 1 || spanTypeIdInternal > 31) {
                        Log.e(TAG, "External class \"" + parcelableSpan.getClass().getSimpleName() + "\" is attempting to use the frameworks-only ParcelableSpan interface");
                    } else {
                        parcel.writeInt(spanTypeIdInternal);
                        parcelableSpan.writeToParcelInternal(parcel, i);
                        writeWhere(parcel, spanned, obj);
                    }
                }
            }
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(1);
        if (charSequence != null) {
            parcel.writeString8(charSequence.toString());
        } else {
            parcel.writeString8(null);
        }
    }

    private static void writeWhere(Parcel parcel, Spanned spanned, Object obj) {
        parcel.writeInt(spanned.getSpanStart(obj));
        parcel.writeInt(spanned.getSpanEnd(obj));
        parcel.writeInt(spanned.getSpanFlags(obj));
    }

    public static void dumpSpans(CharSequence charSequence, Printer printer, String str) {
        if (charSequence instanceof Spanned) {
            Spanned spanned = (Spanned) charSequence;
            for (Object obj : spanned.getSpans(0, charSequence.length(), Object.class)) {
                printer.println(str + ((Object) charSequence.subSequence(spanned.getSpanStart(obj), spanned.getSpanEnd(obj))) + ": " + Integer.toHexString(System.identityHashCode(obj)) + " " + obj.getClass().getCanonicalName() + " (" + spanned.getSpanStart(obj) + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + spanned.getSpanEnd(obj) + ") fl=#" + spanned.getSpanFlags(obj));
            }
            return;
        }
        printer.println(str + ((Object) charSequence) + ": (no spans)");
    }

    public static CharSequence replace(CharSequence charSequence, String[] strArr, CharSequence[] charSequenceArr) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        for (int i = 0; i < strArr.length; i++) {
            int iIndexOf = indexOf(spannableStringBuilder, strArr[i]);
            if (iIndexOf >= 0) {
                String str = strArr[i];
                spannableStringBuilder.setSpan(str, iIndexOf, str.length() + iIndexOf, 33);
            }
        }
        for (int i2 = 0; i2 < strArr.length; i2++) {
            int spanStart = spannableStringBuilder.getSpanStart(strArr[i2]);
            int spanEnd = spannableStringBuilder.getSpanEnd(strArr[i2]);
            if (spanStart >= 0) {
                spannableStringBuilder.replace(spanStart, spanEnd, charSequenceArr[i2]);
            }
        }
        return spannableStringBuilder;
    }

    public static CharSequence expandTemplate(CharSequence charSequence, CharSequence... charSequenceArr) {
        if (charSequenceArr.length > 9) {
            throw new IllegalArgumentException("max of 9 values are supported");
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        int length = 0;
        while (length < spannableStringBuilder.length()) {
            try {
                if (spannableStringBuilder.charAt(length) == '^') {
                    int i = length + 1;
                    char cCharAt = spannableStringBuilder.charAt(i);
                    if (cCharAt == '^') {
                        spannableStringBuilder.delete(i, length + 2);
                        length = i;
                    } else if (Character.isDigit(cCharAt)) {
                        int numericValue = Character.getNumericValue(cCharAt);
                        int i2 = numericValue - 1;
                        if (i2 < 0) {
                            throw new IllegalArgumentException("template requests value ^" + numericValue);
                        }
                        if (i2 >= charSequenceArr.length) {
                            throw new IllegalArgumentException("template requests value ^" + numericValue + "; only " + charSequenceArr.length + " provided");
                        }
                        spannableStringBuilder.replace(length, length + 2, charSequenceArr[i2]);
                        length += charSequenceArr[i2].length();
                    }
                }
                length++;
            } catch (IndexOutOfBoundsException unused) {
            }
        }
        return spannableStringBuilder;
    }

    public static int getOffsetBefore(CharSequence charSequence, int i) {
        char cCharAt;
        if (i == 0 || i == 1) {
            return 0;
        }
        char cCharAt2 = charSequence.charAt(i - 1);
        int i2 = (cCharAt2 < 56320 || cCharAt2 > 57343 || (cCharAt = charSequence.charAt(i + (-2))) < 55296 || cCharAt > 56319) ? i - 1 : i - 2;
        if (charSequence instanceof Spanned) {
            Spanned spanned = (Spanned) charSequence;
            ReplacementSpan[] replacementSpanArr = (ReplacementSpan[]) spanned.getSpans(i2, i2, ReplacementSpan.class);
            for (int i3 = 0; i3 < replacementSpanArr.length; i3++) {
                int spanStart = spanned.getSpanStart(replacementSpanArr[i3]);
                int spanEnd = spanned.getSpanEnd(replacementSpanArr[i3]);
                if (spanStart < i2 && spanEnd > i2) {
                    i2 = spanStart;
                }
            }
        }
        return i2;
    }

    public static int getOffsetAfter(CharSequence charSequence, int i) {
        int i2;
        int length = charSequence.length();
        if (i == length || i == length - 1) {
            return length;
        }
        char cCharAt = charSequence.charAt(i);
        if (cCharAt < 55296 || cCharAt > 56319) {
            i2 = i + 1;
        } else {
            i2 = i + 1;
            char cCharAt2 = charSequence.charAt(i2);
            if (cCharAt2 >= 56320 && cCharAt2 <= 57343) {
                i2 = i + 2;
            }
        }
        if (charSequence instanceof Spanned) {
            Spanned spanned = (Spanned) charSequence;
            ReplacementSpan[] replacementSpanArr = (ReplacementSpan[]) spanned.getSpans(i2, i2, ReplacementSpan.class);
            for (int i3 = 0; i3 < replacementSpanArr.length; i3++) {
                int spanStart = spanned.getSpanStart(replacementSpanArr[i3]);
                int spanEnd = spanned.getSpanEnd(replacementSpanArr[i3]);
                if (spanStart < i2 && spanEnd > i2) {
                    i2 = spanEnd;
                }
            }
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void readSpan(Parcel parcel, Spannable spannable, Object obj) {
        spannable.setSpan(obj, parcel.readInt(), parcel.readInt(), parcel.readInt());
    }

    public static void copySpansFrom(Spanned spanned, int i, int i2, Class cls, Spannable spannable, int i3) {
        if (cls == null) {
            cls = Object.class;
        }
        Object[] spans = spanned.getSpans(i, i2, cls);
        for (int i4 = 0; i4 < spans.length; i4++) {
            int spanStart = spanned.getSpanStart(spans[i4]);
            int spanEnd = spanned.getSpanEnd(spans[i4]);
            int spanFlags = spanned.getSpanFlags(spans[i4]);
            if (spanStart < i) {
                spanStart = i;
            }
            if (spanEnd > i2) {
                spanEnd = i2;
            }
            spannable.setSpan(spans[i4], (spanStart - i) + i3, (spanEnd - i) + i3, spanFlags);
        }
    }

    public static CharSequence toUpperCase(Locale locale, CharSequence charSequence, boolean z) {
        int upperMapToDest;
        int upperMapToDest2;
        Edits edits = new Edits();
        if (!z) {
            StringBuilder sb = (StringBuilder) CaseMap.toUpper().apply(locale, charSequence, new StringBuilder(), edits);
            if (edits.hasChanges()) {
                return sb;
            }
        } else {
            SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) CaseMap.toUpper().apply(locale, charSequence, new SpannableStringBuilder(), edits);
            if (edits.hasChanges()) {
                Edits.Iterator fineIterator = edits.getFineIterator();
                int length = charSequence.length();
                Spanned spanned = (Spanned) charSequence;
                for (Object obj : spanned.getSpans(0, length, Object.class)) {
                    int spanStart = spanned.getSpanStart(obj);
                    int spanEnd = spanned.getSpanEnd(obj);
                    int spanFlags = spanned.getSpanFlags(obj);
                    if (spanStart == length) {
                        upperMapToDest = spannableStringBuilder.length();
                    } else {
                        upperMapToDest = toUpperMapToDest(fineIterator, spanStart);
                    }
                    if (spanEnd == length) {
                        upperMapToDest2 = spannableStringBuilder.length();
                    } else {
                        upperMapToDest2 = toUpperMapToDest(fineIterator, spanEnd);
                    }
                    spannableStringBuilder.setSpan(obj, upperMapToDest, upperMapToDest2, spanFlags);
                }
                return spannableStringBuilder;
            }
        }
        return charSequence;
    }

    private static int toUpperMapToDest(Edits.Iterator iterator, int i) {
        iterator.findSourceIndex(i);
        if (i == iterator.sourceIndex()) {
            return iterator.destinationIndex();
        }
        if (iterator.hasChange()) {
            return iterator.destinationIndex() + iterator.newLength();
        }
        return iterator.destinationIndex() + (i - iterator.sourceIndex());
    }

    public static CharSequence ellipsize(CharSequence charSequence, TextPaint textPaint, float f, TruncateAt truncateAt) {
        return ellipsize(charSequence, textPaint, f, truncateAt, false, null);
    }

    public static CharSequence ellipsize(CharSequence charSequence, TextPaint textPaint, float f, TruncateAt truncateAt, boolean z, EllipsizeCallback ellipsizeCallback) {
        return ellipsize(charSequence, textPaint, f, truncateAt, z, ellipsizeCallback, TextDirectionHeuristics.FIRSTSTRONG_LTR, getEllipsisString(truncateAt));
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0066 A[Catch: all -> 0x00f8, TryCatch #0 {all -> 0x00f8, blocks: (B:3:0x0006, B:6:0x001d, B:10:0x0026, B:25:0x0066, B:26:0x0069, B:28:0x0071, B:30:0x0076, B:33:0x007e, B:35:0x0084, B:37:0x0092, B:38:0x009a, B:43:0x00a7, B:53:0x00c6, B:57:0x00e4, B:14:0x0034, B:16:0x0038, B:17:0x0041, B:19:0x0046, B:22:0x004b, B:23:0x005f), top: B:66:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0071 A[Catch: all -> 0x00f8, TryCatch #0 {all -> 0x00f8, blocks: (B:3:0x0006, B:6:0x001d, B:10:0x0026, B:25:0x0066, B:26:0x0069, B:28:0x0071, B:30:0x0076, B:33:0x007e, B:35:0x0084, B:37:0x0092, B:38:0x009a, B:43:0x00a7, B:53:0x00c6, B:57:0x00e4, B:14:0x0034, B:16:0x0038, B:17:0x0041, B:19:0x0046, B:22:0x004b, B:23:0x005f), top: B:66:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00ba  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static CharSequence ellipsize(CharSequence charSequence, TextPaint textPaint, float f, TruncateAt truncateAt, boolean z, EllipsizeCallback ellipsizeCallback, TextDirectionHeuristic textDirectionHeuristic, String str) {
        int iBreakText;
        int iBreakText2;
        int length = charSequence.length();
        try {
            MeasuredParagraph measuredParagraphBuildForMeasurement = MeasuredParagraph.buildForMeasurement(textPaint, charSequence, 0, charSequence.length(), textDirectionHeuristic, null);
            if (measuredParagraphBuildForMeasurement.getWholeWidth() <= f) {
                if (ellipsizeCallback != null) {
                    ellipsizeCallback.ellipsized(0, 0);
                }
                if (measuredParagraphBuildForMeasurement != null) {
                    measuredParagraphBuildForMeasurement.recycle();
                }
                return charSequence;
            }
            float fMeasureText = f - textPaint.measureText(str);
            if (fMeasureText >= 0.0f) {
                if (truncateAt == TruncateAt.START) {
                    iBreakText2 = length - measuredParagraphBuildForMeasurement.breakText(length, false, fMeasureText);
                    iBreakText = 0;
                } else {
                    if (truncateAt != TruncateAt.END && truncateAt != TruncateAt.END_SMALL) {
                        iBreakText2 = length - measuredParagraphBuildForMeasurement.breakText(length, false, fMeasureText / 2.0f);
                        iBreakText = measuredParagraphBuildForMeasurement.breakText(iBreakText2, true, fMeasureText - measuredParagraphBuildForMeasurement.measure(iBreakText2, length));
                    }
                    iBreakText = measuredParagraphBuildForMeasurement.breakText(length, true, fMeasureText);
                }
                if (ellipsizeCallback != null) {
                    ellipsizeCallback.ellipsized(iBreakText, iBreakText2);
                }
                char[] chars = measuredParagraphBuildForMeasurement.getChars();
                Spanned spanned = !(charSequence instanceof Spanned) ? (Spanned) charSequence : null;
                int i = iBreakText2 - iBreakText;
                int i2 = length - i;
                if (!z) {
                    if (i2 > 0 && i >= str.length()) {
                        str.getChars(0, str.length(), chars, iBreakText);
                        iBreakText += str.length();
                    }
                    while (iBreakText < iBreakText2) {
                        chars[iBreakText] = ELLIPSIS_FILLER;
                        iBreakText++;
                    }
                    String str2 = new String(chars, 0, length);
                    if (spanned == null) {
                        if (measuredParagraphBuildForMeasurement != null) {
                            measuredParagraphBuildForMeasurement.recycle();
                        }
                        return str2;
                    }
                    SpannableString spannableString = new SpannableString(str2);
                    copySpansFrom(spanned, 0, length, Object.class, spannableString, 0);
                    if (measuredParagraphBuildForMeasurement != null) {
                        measuredParagraphBuildForMeasurement.recycle();
                    }
                    return spannableString;
                }
                if (i2 == 0) {
                    if (measuredParagraphBuildForMeasurement != null) {
                        measuredParagraphBuildForMeasurement.recycle();
                    }
                    return "";
                }
                if (spanned != null) {
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                    spannableStringBuilder.append(charSequence, 0, iBreakText);
                    spannableStringBuilder.append((CharSequence) str);
                    spannableStringBuilder.append(charSequence, iBreakText2, length);
                    if (measuredParagraphBuildForMeasurement != null) {
                        measuredParagraphBuildForMeasurement.recycle();
                    }
                    return spannableStringBuilder;
                }
                StringBuilder sb = new StringBuilder(i2 + str.length());
                sb.append(chars, 0, iBreakText);
                sb.append(str);
                sb.append(chars, iBreakText2, length - iBreakText2);
                String string = sb.toString();
                if (measuredParagraphBuildForMeasurement != null) {
                    measuredParagraphBuildForMeasurement.recycle();
                }
                return string;
            }
            iBreakText = 0;
            iBreakText2 = length;
            if (ellipsizeCallback != null) {
            }
            char[] chars2 = measuredParagraphBuildForMeasurement.getChars();
            if (!(charSequence instanceof Spanned)) {
            }
            int i3 = iBreakText2 - iBreakText;
            int i22 = length - i3;
            if (!z) {
            }
        } finally {
        }
    }

    public static CharSequence listEllipsize(Context context, List<CharSequence> list, String str, TextPaint textPaint, float f, int i) throws Resources.NotFoundException {
        int size;
        Resources resources;
        BidiFormatter bidiFormatter;
        String quantityString;
        if (list == null || (size = list.size()) == 0) {
            return "";
        }
        if (context == null) {
            bidiFormatter = BidiFormatter.getInstance();
            resources = null;
        } else {
            resources = context.getResources();
            bidiFormatter = BidiFormatter.getInstance(resources.getConfiguration().getLocales().get(0));
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int[] iArr = new int[size];
        for (int i2 = 0; i2 < size; i2++) {
            spannableStringBuilder.append(bidiFormatter.unicodeWrap(list.get(i2)));
            if (i2 != size - 1) {
                spannableStringBuilder.append((CharSequence) str);
            }
            iArr[i2] = spannableStringBuilder.length();
        }
        for (int i3 = size - 1; i3 >= 0; i3--) {
            spannableStringBuilder.delete(iArr[i3], spannableStringBuilder.length());
            int i4 = (size - i3) - 1;
            if (i4 > 0) {
                if (resources == null) {
                    quantityString = ELLIPSIS_NORMAL;
                } else {
                    quantityString = resources.getQuantityString(i, i4, Integer.valueOf(i4));
                }
                spannableStringBuilder.append(bidiFormatter.unicodeWrap((CharSequence) quantityString));
            }
            if (textPaint.measureText(spannableStringBuilder, 0, spannableStringBuilder.length()) <= f) {
                return spannableStringBuilder;
            }
        }
        return "";
    }

    @Deprecated
    public static CharSequence commaEllipsize(CharSequence charSequence, TextPaint textPaint, float f, String str, String str2) {
        return commaEllipsize(charSequence, textPaint, f, str, str2, TextDirectionHeuristics.FIRSTSTRONG_LTR);
    }

    @Deprecated
    public static CharSequence commaEllipsize(CharSequence charSequence, TextPaint textPaint, float f, String str, String str2, TextDirectionHeuristic textDirectionHeuristic) throws Throwable {
        char c;
        char[] cArr;
        int i;
        String str3;
        MeasuredParagraph measuredParagraphBuildForMeasurement = null;
        MeasuredParagraph measuredParagraph = null;
        try {
            int length = charSequence.length();
            measuredParagraphBuildForMeasurement = MeasuredParagraph.buildForMeasurement(textPaint, charSequence, 0, length, textDirectionHeuristic, null);
            if (measuredParagraphBuildForMeasurement.getWholeWidth() <= f) {
                if (measuredParagraphBuildForMeasurement != null) {
                    measuredParagraphBuildForMeasurement.recycle();
                }
                return charSequence;
            }
            char[] chars = measuredParagraphBuildForMeasurement.getChars();
            int i2 = 0;
            int i3 = 0;
            while (true) {
                c = ',';
                if (i2 >= length) {
                    break;
                }
                if (chars[i2] == ',') {
                    i3++;
                }
                i2++;
            }
            int i4 = 1;
            int i5 = i3 + 1;
            float[] rawArray = measuredParagraphBuildForMeasurement.getWidths().getRawArray();
            int i6 = 0;
            int i7 = 0;
            MeasuredParagraph measuredParagraph2 = null;
            String str4 = "";
            int i8 = 0;
            while (i7 < length) {
                try {
                    int i9 = (int) (i8 + rawArray[i7]);
                    if (chars[i7] == c) {
                        int i10 = i5 - 1;
                        if (i10 == i4) {
                            str3 = " " + str;
                        } else {
                            str3 = " " + String.format(str2, Integer.valueOf(i10));
                        }
                        cArr = chars;
                        int i11 = i6;
                        i = i9;
                        String str5 = str3;
                        MeasuredParagraph measuredParagraphBuildForMeasurement2 = MeasuredParagraph.buildForMeasurement(textPaint, str5, 0, str3.length(), textDirectionHeuristic, measuredParagraph2);
                        try {
                            if (i + measuredParagraphBuildForMeasurement2.getWholeWidth() <= f) {
                                i11 = i7 + 1;
                                str4 = str5;
                            }
                            measuredParagraph2 = measuredParagraphBuildForMeasurement2;
                            i5 = i10;
                            i6 = i11;
                        } catch (Throwable th) {
                            th = th;
                            measuredParagraph = measuredParagraphBuildForMeasurement2;
                            if (measuredParagraphBuildForMeasurement != null) {
                                measuredParagraphBuildForMeasurement.recycle();
                            }
                            if (measuredParagraph != null) {
                                measuredParagraph.recycle();
                            }
                            throw th;
                        }
                    } else {
                        cArr = chars;
                        i = i9;
                    }
                    i7++;
                    i8 = i;
                    chars = cArr;
                    i4 = 1;
                    c = ',';
                } catch (Throwable th2) {
                    th = th2;
                    measuredParagraph = measuredParagraph2;
                }
            }
            int i12 = i6;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str4);
            spannableStringBuilder.insert(0, charSequence, 0, i12);
            if (measuredParagraphBuildForMeasurement != null) {
                measuredParagraphBuildForMeasurement.recycle();
            }
            if (measuredParagraph2 != null) {
                measuredParagraph2.recycle();
            }
            return spannableStringBuilder;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    static boolean doesNotNeedBidi(char[] cArr, int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            if (couldAffectRtl(cArr[i])) {
                return false;
            }
            i++;
        }
        return true;
    }

    static char[] obtain(int i) {
        char[] cArr;
        synchronized (sLock) {
            cArr = sTemp;
            sTemp = null;
        }
        return (cArr == null || cArr.length < i) ? ArrayUtils.newUnpaddedCharArray(i) : cArr;
    }

    static void recycle(char[] cArr) {
        if (cArr.length > 1000) {
            return;
        }
        synchronized (sLock) {
            sTemp = cArr;
        }
    }

    public static String htmlEncode(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\"') {
                sb.append("&quot;");
            } else if (cCharAt == '<') {
                sb.append("&lt;");
            } else if (cCharAt == '>') {
                sb.append("&gt;");
            } else if (cCharAt == '&') {
                sb.append("&amp;");
            } else if (cCharAt == '\'') {
                sb.append("&#39;");
            } else {
                sb.append(cCharAt);
            }
        }
        return sb.toString();
    }

    public static CharSequence concat(CharSequence... charSequenceArr) {
        if (charSequenceArr.length == 0) {
            return "";
        }
        int i = 0;
        if (charSequenceArr.length == 1) {
            return charSequenceArr[0];
        }
        for (CharSequence charSequence : charSequenceArr) {
            if (charSequence instanceof Spanned) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                int length = charSequenceArr.length;
                while (i < length) {
                    CharSequence charSequence2 = charSequenceArr[i];
                    if (charSequence2 == null) {
                        charSequence2 = PerfettoProtoLogImpl.NULL_STRING;
                    }
                    spannableStringBuilder.append(charSequence2);
                    i++;
                }
                return new SpannedString(spannableStringBuilder);
            }
        }
        StringBuilder sb = new StringBuilder();
        int length2 = charSequenceArr.length;
        while (i < length2) {
            sb.append(charSequenceArr[i]);
            i++;
        }
        return sb.toString();
    }

    public static boolean isGraphic(CharSequence charSequence) {
        int length = charSequence.length();
        int iCharCount = 0;
        while (iCharCount < length) {
            int iCodePointAt = Character.codePointAt(charSequence, iCharCount);
            int type = Character.getType(iCodePointAt);
            if (type != 15 && type != 16 && type != 19 && type != 0 && type != 13 && type != 14 && type != 12) {
                return true;
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        return false;
    }

    @Deprecated
    public static boolean isGraphic(char c) {
        int type = Character.getType(c);
        return (type == 15 || type == 16 || type == 19 || type == 0 || type == 13 || type == 14 || type == 12) ? false : true;
    }

    public static boolean isDigitsOnly(CharSequence charSequence) {
        int length = charSequence.length();
        int iCharCount = 0;
        while (iCharCount < length) {
            int iCodePointAt = Character.codePointAt(charSequence, iCharCount);
            if (!Character.isDigit(iCodePointAt)) {
                return false;
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        return true;
    }

    public static boolean isPrintableAsciiOnly(CharSequence charSequence) {
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (!isPrintableAscii(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int getCapsMode(CharSequence charSequence, int i, int i2) {
        char cCharAt;
        char cCharAt2;
        if (i < 0) {
            return 0;
        }
        int i3 = (i2 & 4096) != 0 ? 4096 : 0;
        if ((i2 & 24576) != 0) {
            while (i > 0 && ((cCharAt2 = charSequence.charAt(i - 1)) == '\"' || cCharAt2 == '\'' || Character.getType(cCharAt2) == 21)) {
                i--;
            }
            int i4 = i;
            while (i4 > 0) {
                char cCharAt3 = charSequence.charAt(i4 - 1);
                if (cCharAt3 != ' ' && cCharAt3 != '\t') {
                    break;
                }
                i4--;
            }
            if (i4 == 0 || charSequence.charAt(i4 - 1) == '\n') {
                return i3 | 8192;
            }
            if ((i2 & 16384) == 0) {
                if (i != i4) {
                    return i3 | 8192;
                }
            } else if (i != i4) {
                while (i4 > 0) {
                    char cCharAt4 = charSequence.charAt(i4 - 1);
                    if (cCharAt4 != '\"' && cCharAt4 != '\'' && Character.getType(cCharAt4) != 22) {
                        break;
                    }
                    i4--;
                }
                if (i4 > 0 && ((cCharAt = charSequence.charAt(i4 - 1)) == '.' || cCharAt == '?' || cCharAt == '!')) {
                    if (cCharAt == '.') {
                        for (int i5 = i4 - 2; i5 >= 0; i5--) {
                            char cCharAt5 = charSequence.charAt(i5);
                            if (cCharAt5 != '.') {
                                if (!Character.isLetter(cCharAt5)) {
                                    break;
                                }
                            }
                        }
                    }
                    return i3 | 16384;
                }
            }
        }
        return i3;
    }

    public static boolean delimitedStringContains(String str, char c, String str2) {
        if (!isEmpty(str) && !isEmpty(str2)) {
            int length = str.length();
            int iIndexOf = -1;
            while (true) {
                iIndexOf = str.indexOf(str2, iIndexOf + 1);
                if (iIndexOf == -1) {
                    break;
                }
                if (iIndexOf <= 0 || str.charAt(iIndexOf - 1) == c) {
                    int length2 = str2.length() + iIndexOf;
                    if (length2 == length || str.charAt(length2) == c) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static <T> T[] removeEmptySpans(T[] tArr, Spanned spanned, Class<T> cls) {
        Object[] objArr = null;
        int i = 0;
        for (int i2 = 0; i2 < tArr.length; i2++) {
            T t = tArr[i2];
            if (spanned.getSpanStart(t) == spanned.getSpanEnd(t)) {
                if (objArr == null) {
                    objArr = (Object[]) Array.newInstance((Class<?>) cls, tArr.length - 1);
                    System.arraycopy(tArr, 0, objArr, 0, i2);
                    i = i2;
                }
            } else if (objArr != null) {
                objArr[i] = t;
                i++;
            }
        }
        if (objArr == null) {
            return tArr;
        }
        T[] tArr2 = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, i));
        System.arraycopy(objArr, 0, tArr2, 0, i);
        return tArr2;
    }

    public static int getLayoutDirectionFromLocale(Locale locale) {
        return ((locale == null || locale.equals(Locale.ROOT) || !ULocale.forLocale(locale).isRightToLeft()) && !DisplayProperties.debug_force_rtl().orElse(false).booleanValue()) ? 0 : 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00d4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String formatSimple(String str, Object... objArr) {
        int i;
        String strValueOf;
        StringBuilder sb = new StringBuilder(str);
        int iMax = 0;
        int i2 = 0;
        while (iMax < sb.length()) {
            if (sb.charAt(iMax) == '%') {
                char cCharAt = sb.charAt(iMax + 1);
                int i3 = 2;
                char c = 0;
                int iDigit = 0;
                while (true) {
                    if ('0' <= cCharAt && cCharAt <= '9') {
                        if (c == 0) {
                            c = cCharAt == '0' ? '0' : ' ';
                        }
                        iDigit = (iDigit * 10) + Character.digit(cCharAt, 10);
                        i3++;
                        cCharAt = sb.charAt((iMax + i3) - 1);
                    }
                }
                if (cCharAt == '%') {
                    i = i2;
                    strValueOf = "%";
                } else if (cCharAt == 'f' || cCharAt == 's') {
                    if (i2 == objArr.length) {
                        throw new IllegalArgumentException("Too few arguments");
                    }
                    i = i2 + 1;
                    strValueOf = String.valueOf(objArr[i2]);
                } else if (cCharAt != 'x') {
                    switch (cCharAt) {
                        case 'b':
                            if (i2 == objArr.length) {
                                throw new IllegalArgumentException("Too few arguments");
                            }
                            i = i2 + 1;
                            Object obj = objArr[i2];
                            if (obj instanceof Boolean) {
                                strValueOf = Boolean.toString(((Boolean) obj).booleanValue());
                                break;
                            } else {
                                strValueOf = Boolean.toString(obj != null);
                                break;
                            }
                        case 'c':
                        case 'd':
                            break;
                        default:
                            throw new IllegalArgumentException("Unsupported format code " + cCharAt);
                    }
                } else {
                    if (i2 == objArr.length) {
                        throw new IllegalArgumentException("Too few arguments");
                    }
                    i = i2 + 1;
                    Object obj2 = objArr[i2];
                    if (obj2 instanceof Integer) {
                        strValueOf = Integer.toHexString(((Integer) obj2).intValue());
                    } else if (obj2 instanceof Long) {
                        strValueOf = Long.toHexString(((Long) obj2).longValue());
                    } else {
                        throw new IllegalArgumentException("Unsupported hex type " + obj2.getClass());
                    }
                }
                sb.replace(iMax, i3 + iMax, strValueOf);
                int i4 = (c == '0' && strValueOf.charAt(0) == '-') ? 1 : 0;
                for (int length = strValueOf.length(); length < iDigit; length++) {
                    sb.insert(iMax + i4, c);
                }
                iMax += Math.max(strValueOf.length(), iDigit);
                i2 = i;
            } else {
                iMax++;
            }
        }
        if (i2 != objArr.length) {
            throw new IllegalArgumentException("Too many arguments");
        }
        return sb.toString();
    }

    public static boolean hasStyleSpan(Spanned spanned) {
        Preconditions.checkArgument(spanned != null);
        Class[] clsArr = {CharacterStyle.class, ParagraphStyle.class, UpdateAppearance.class};
        for (int i = 0; i < 3; i++) {
            if (spanned.nextSpanTransition(-1, spanned.length(), clsArr[i]) < spanned.length()) {
                return true;
            }
        }
        return false;
    }

    public static CharSequence trimNoCopySpans(CharSequence charSequence) {
        return (charSequence == null || !(charSequence instanceof Spanned)) ? charSequence : new SpannableStringBuilder(charSequence);
    }

    public static void wrap(StringBuilder sb, String str, String str2) {
        sb.insert(0, str);
        sb.append(str2);
    }

    public static <T extends CharSequence> T trimToParcelableSize(T t) {
        return (T) trimToSize(t, 100000);
    }

    public static <T extends CharSequence> T trimToSize(T t, int i) {
        Preconditions.checkArgument(i > 0);
        if (isEmpty(t) || t.length() <= i) {
            return t;
        }
        int i2 = i - 1;
        if (Character.isHighSurrogate(t.charAt(i2)) && Character.isLowSurrogate(t.charAt(i))) {
            i = i2;
        }
        return (T) t.subSequence(0, i);
    }

    public static <T extends CharSequence> T trimToLengthWithEllipsis(T t, int i) {
        T t2 = (T) trimToSize(t, i);
        if (t == null || t2.length() >= t.length()) {
            return t2;
        }
        return t2.toString() + Session.TRUNCATE_STRING;
    }

    public static boolean isNewline(int i) {
        int type = Character.getType(i);
        return type == 14 || type == 13 || i == 10;
    }

    public static boolean isWhitespace(int i) {
        return Character.isWhitespace(i) || i == 160;
    }

    public static boolean isWhitespaceExceptNewline(int i) {
        return isWhitespace(i) && !isNewline(i);
    }

    public static boolean isPunctuation(int i) {
        int type = Character.getType(i);
        return type == 23 || type == 20 || type == 22 || type == 30 || type == 29 || type == 24 || type == 21;
    }

    public static String withoutPrefix(String str, String str2) {
        return (str == null || str2 == null || !str2.startsWith(str)) ? str2 : str2.substring(str.length());
    }

    public static CharSequence makeSafeForPresentation(String str, int i, float f, int i2) {
        boolean z = true;
        boolean z2 = (i2 & 4) != 0;
        boolean z3 = (i2 & 2) != 0;
        boolean z4 = (i2 & 1) != 0;
        Preconditions.checkNotNull(str);
        Preconditions.checkArgumentNonnegative(i);
        Preconditions.checkArgumentNonNegative(f, "ellipsizeDip");
        Preconditions.checkFlagsArgument(i2, 7);
        if (z2 && z3) {
            z = false;
        }
        Preconditions.checkArgument(z, "Cannot set SAFE_STRING_FLAG_SINGLE_LINE and SAFE_STRING_FLAG_FIRST_LINE at thesame time");
        if (i > 0) {
            str = str.substring(0, Math.min(str.length(), i));
        }
        StringWithRemovedChars stringWithRemovedChars = new StringWithRemovedChars(Html.fromHtml(str).toString());
        int length = stringWithRemovedChars.length();
        int i3 = -1;
        int i4 = -1;
        int i5 = 0;
        while (true) {
            if (i5 >= length) {
                break;
            }
            int iCodePointAt = stringWithRemovedChars.codePointAt(i5);
            int type = Character.getType(iCodePointAt);
            int iCharCount = Character.charCount(iCodePointAt);
            boolean zIsNewline = isNewline(iCodePointAt);
            if (z2 && zIsNewline) {
                stringWithRemovedChars.removeAllCharAfter(i5);
                break;
            }
            if (z3 && zIsNewline) {
                stringWithRemovedChars.removeRange(i5, i5 + iCharCount);
            } else if (type == 15 && !zIsNewline) {
                stringWithRemovedChars.removeRange(i5, i5 + iCharCount);
            } else if (z4 && !isWhitespace(iCodePointAt)) {
                if (i3 == -1) {
                    i3 = i5;
                }
                i4 = i5 + iCharCount;
            }
            i5 += iCharCount;
        }
        if (z4) {
            if (i3 == -1) {
                stringWithRemovedChars.removeAllCharAfter(0);
            } else {
                if (i3 > 0) {
                    stringWithRemovedChars.removeAllCharBefore(i3);
                }
                if (i4 < length) {
                    stringWithRemovedChars.removeAllCharAfter(i4);
                }
            }
        }
        if (f == 0.0f) {
            return stringWithRemovedChars.toString();
        }
        if (Typeface.getSystemFontMap().isEmpty()) {
            int i6 = (int) ((f + 0.5f) / 23.94f);
            String string = stringWithRemovedChars.toString();
            if (isEmpty(string) || string.length() <= i6) {
                return string;
            }
            return ((String) trimToSize(string, i6)) + getEllipsisString(TruncateAt.END);
        }
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(42.0f);
        return ellipsize(stringWithRemovedChars.toString(), textPaint, f, TruncateAt.END);
    }

    private static class StringWithRemovedChars {
        private final String mOriginal;
        private BitSet mRemovedChars;

        StringWithRemovedChars(String str) {
            this.mOriginal = str;
        }

        void removeRange(int i, int i2) {
            if (this.mRemovedChars == null) {
                this.mRemovedChars = new BitSet(this.mOriginal.length());
            }
            this.mRemovedChars.set(i, i2);
        }

        void removeAllCharBefore(int i) {
            if (this.mRemovedChars == null) {
                this.mRemovedChars = new BitSet(this.mOriginal.length());
            }
            this.mRemovedChars.set(0, i);
        }

        void removeAllCharAfter(int i) {
            if (this.mRemovedChars == null) {
                this.mRemovedChars = new BitSet(this.mOriginal.length());
            }
            this.mRemovedChars.set(i, this.mOriginal.length());
        }

        public String toString() {
            if (this.mRemovedChars == null) {
                return this.mOriginal;
            }
            StringBuilder sb = new StringBuilder(this.mOriginal.length());
            for (int i = 0; i < this.mOriginal.length(); i++) {
                if (!this.mRemovedChars.get(i)) {
                    sb.append(this.mOriginal.charAt(i));
                }
            }
            return sb.toString();
        }

        int length() {
            return this.mOriginal.length();
        }

        int codePointAt(int i) {
            return this.mOriginal.codePointAt(i);
        }
    }

    public static boolean isCombinedCode(char c) {
        return Character.isSurrogate(c) || isIndianChar(c) || isThaiChar(c) || isKhmerChar(c) || isMyanmarChar(c) || isLaoChar(c);
    }

    public static int indexOfWordPrefix(CharSequence charSequence, char[] cArr) {
        int length = charSequence.length();
        int length2 = cArr.length;
        if (length2 != 0 && length >= length2) {
            for (int i = 0; i < length && i + length2 <= length; i++) {
                int i2 = 0;
                while (i2 < length2 && Character.toUpperCase(charSequence.charAt(i + i2)) == Character.toUpperCase(cArr[i2])) {
                    i2++;
                }
                if (i2 == length2) {
                    return i;
                }
            }
            return -1;
        }
        return -1;
    }

    public static char[] semGetPrefixCharForSpan(TextPaint textPaint, CharSequence charSequence, char[] cArr) {
        int iIndexOfWordPrefix;
        int i;
        int length = charSequence.length();
        if (length != 0 && cArr != null) {
            float[] fArr = new float[length];
            char[] cArr2 = new char[length];
            int i2 = 0;
            while (i2 < cArr.length && !isCombinedCode(cArr[i2])) {
                i2++;
            }
            if (i2 != cArr.length && (iIndexOfWordPrefix = indexOfWordPrefix(charSequence, cArr)) >= 0 && iIndexOfWordPrefix < length) {
                getChars(charSequence, 0, length, cArr2, 0);
                textPaint.getTextRunAdvances(cArr2, 0, length, 0, length, false, fArr, 0);
                if (isIndianChar(cArr[i2])) {
                    i = iIndexOfWordPrefix;
                    while (i > 0 && isHalant(cArr2[i - 1])) {
                        i -= 2;
                    }
                    if (i < 0) {
                        return null;
                    }
                } else {
                    while (iIndexOfWordPrefix > 0 && fArr[iIndexOfWordPrefix] == 0.0f) {
                        iIndexOfWordPrefix--;
                    }
                    i = iIndexOfWordPrefix;
                }
                int length2 = iIndexOfWordPrefix + cArr.length;
                while (length2 < length && (fArr[length2] == 0.0f || isHalant(cArr2[length2 - 1]))) {
                    length2++;
                }
                int i3 = length2 - i;
                char[] cArr3 = new char[i3];
                for (int i4 = 0; i4 < i3; i4++) {
                    cArr3[i4] = cArr2[i + i4];
                }
                return cArr3;
            }
        }
        return null;
    }

    private static char[] hidden_semGetPrefixCharForSpan(TextPaint textPaint, CharSequence charSequence, char[] cArr) {
        return semGetPrefixCharForSpan(textPaint, charSequence, cArr);
    }
}
