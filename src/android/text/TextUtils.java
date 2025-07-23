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
            int readInt = parcel.readInt();
            String readString8 = parcel.readString8();
            if (readString8 == null) {
                return null;
            }
            if (readInt == 1) {
                return readString8;
            }
            SpannableString spannableString = new SpannableString(readString8);
            while (true) {
                int readInt2 = parcel.readInt();
                if (readInt2 == 0) {
                    return spannableString;
                }
                switch (readInt2) {
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
                        throw new RuntimeException("bogus span encoding " + readInt2);
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
        char[] obtain = obtain(500);
        while (i < i2) {
            int i3 = i + 500;
            if (i3 > i2) {
                i3 = i2;
            }
            getChars(charSequence, i, i3, obtain, 0);
            int i4 = i3 - i;
            for (int i5 = 0; i5 < i4; i5++) {
                if (obtain[i5] == c) {
                    recycle(obtain);
                    return i5 + i;
                }
            }
            i = i3;
        }
        recycle(obtain);
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
        char[] obtain = obtain(500);
        while (i < i3) {
            int i4 = i3 - 500;
            if (i4 < i) {
                i4 = i;
            }
            getChars(charSequence, i4, i3, obtain, 0);
            for (int i5 = (i3 - i4) - 1; i5 >= 0; i5--) {
                if (obtain[i5] == c) {
                    recycle(obtain);
                    return i5 + i4;
                }
            }
            i3 = i4;
        }
        recycle(obtain);
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
        char charAt = charSequence2.charAt(0);
        while (true) {
            int indexOf = indexOf(charSequence, charAt, i);
            if (indexOf > i2 - length || indexOf < 0) {
                return -1;
            }
            if (regionMatches(charSequence, indexOf, charSequence2, 0, length)) {
                return indexOf;
            }
            i = indexOf + 1;
        }
    }

    public static boolean regionMatches(CharSequence charSequence, int i, CharSequence charSequence2, int i2, int i3) {
        int i4 = i3 * 2;
        if (i4 < i3) {
            throw new IndexOutOfBoundsException();
        }
        char[] obtain = obtain(i4);
        boolean z = false;
        getChars(charSequence, i, i + i3, obtain, 0);
        getChars(charSequence2, i2, i2 + i3, obtain, i3);
        int i5 = 0;
        while (true) {
            if (i5 >= i3) {
                z = true;
                break;
            }
            if (obtain[i5] != obtain[i5 + i3]) {
                break;
            }
            i5++;
        }
        recycle(obtain);
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
        char[] obtain = obtain(i3);
        getChars(charSequence, i, i2, obtain, 0);
        String str = new String(obtain, 0, i3);
        recycle(obtain);
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
            char charAt = str.charAt(i2);
            if (charAt < 128) {
                i3++;
            } else if (charAt < 2048) {
                i3 += 2;
            } else if (charAt < 55296 || charAt > 57343 || str.codePointAt(i2) < 65536) {
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
            int indexOf = this.mString.indexOf(this.mDelimiter, this.mPosition);
            if (indexOf == -1) {
                indexOf = this.mLength;
            }
            String substring = this.mString.substring(this.mPosition, indexOf);
            this.mPosition = indexOf + 1;
            return substring;
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
            int indexOf = indexOf(spannableStringBuilder, strArr[i]);
            if (indexOf >= 0) {
                String str = strArr[i];
                spannableStringBuilder.setSpan(str, indexOf, str.length() + indexOf, 33);
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
        int i = 0;
        while (i < spannableStringBuilder.length()) {
            try {
                if (spannableStringBuilder.charAt(i) == '^') {
                    int i2 = i + 1;
                    char charAt = spannableStringBuilder.charAt(i2);
                    if (charAt == '^') {
                        spannableStringBuilder.delete(i2, i + 2);
                        i = i2;
                    } else if (Character.isDigit(charAt)) {
                        int numericValue = Character.getNumericValue(charAt);
                        int i3 = numericValue - 1;
                        if (i3 < 0) {
                            throw new IllegalArgumentException("template requests value ^" + numericValue);
                        }
                        if (i3 >= charSequenceArr.length) {
                            throw new IllegalArgumentException("template requests value ^" + numericValue + "; only " + charSequenceArr.length + " provided");
                        }
                        spannableStringBuilder.replace(i, i + 2, charSequenceArr[i3]);
                        i += charSequenceArr[i3].length();
                    }
                }
                i++;
            } catch (IndexOutOfBoundsException unused) {
            }
        }
        return spannableStringBuilder;
    }

    public static int getOffsetBefore(CharSequence charSequence, int i) {
        char charAt;
        if (i == 0 || i == 1) {
            return 0;
        }
        char charAt2 = charSequence.charAt(i - 1);
        int i2 = (charAt2 < 56320 || charAt2 > 57343 || (charAt = charSequence.charAt(i + (-2))) < 55296 || charAt > 56319) ? i - 1 : i - 2;
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
        char charAt = charSequence.charAt(i);
        if (charAt < 55296 || charAt > 56319) {
            i2 = i + 1;
        } else {
            i2 = i + 1;
            char charAt2 = charSequence.charAt(i2);
            if (charAt2 >= 56320 && charAt2 <= 57343) {
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

    /* JADX WARN: Removed duplicated region for block: B:16:0x0066 A[Catch: all -> 0x00f8, TryCatch #0 {all -> 0x00f8, blocks: (B:3:0x0006, B:6:0x001d, B:11:0x0026, B:16:0x0066, B:17:0x0069, B:19:0x0071, B:20:0x0076, B:23:0x007e, B:25:0x0084, B:28:0x0092, B:30:0x009a, B:35:0x00a7, B:45:0x00c6, B:49:0x00e4, B:54:0x0034, B:56:0x0038, B:57:0x0041, B:59:0x0046, B:62:0x004b, B:63:0x005f), top: B:2:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0071 A[Catch: all -> 0x00f8, TryCatch #0 {all -> 0x00f8, blocks: (B:3:0x0006, B:6:0x001d, B:11:0x0026, B:16:0x0066, B:17:0x0069, B:19:0x0071, B:20:0x0076, B:23:0x007e, B:25:0x0084, B:28:0x0092, B:30:0x009a, B:35:0x00a7, B:45:0x00c6, B:49:0x00e4, B:54:0x0034, B:56:0x0038, B:57:0x0041, B:59:0x0046, B:62:0x004b, B:63:0x005f), top: B:2:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0075  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.CharSequence ellipsize(java.lang.CharSequence r7, android.text.TextPaint r8, float r9, android.text.TextUtils.TruncateAt r10, boolean r11, android.text.TextUtils.EllipsizeCallback r12, android.text.TextDirectionHeuristic r13, java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 256
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.TextUtils.ellipsize(java.lang.CharSequence, android.text.TextPaint, float, android.text.TextUtils$TruncateAt, boolean, android.text.TextUtils$EllipsizeCallback, android.text.TextDirectionHeuristic, java.lang.String):java.lang.CharSequence");
    }

    public static CharSequence listEllipsize(Context context, List<CharSequence> list, String str, TextPaint textPaint, float f, int i) {
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
    public static CharSequence commaEllipsize(CharSequence charSequence, TextPaint textPaint, float f, String str, String str2, TextDirectionHeuristic textDirectionHeuristic) {
        char c;
        char[] cArr;
        int i;
        String str3;
        MeasuredParagraph measuredParagraph = null;
        MeasuredParagraph measuredParagraph2 = null;
        try {
            int length = charSequence.length();
            measuredParagraph = MeasuredParagraph.buildForMeasurement(textPaint, charSequence, 0, length, textDirectionHeuristic, null);
            if (measuredParagraph.getWholeWidth() <= f) {
                if (measuredParagraph != null) {
                    measuredParagraph.recycle();
                }
                return charSequence;
            }
            char[] chars = measuredParagraph.getChars();
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
            float[] rawArray = measuredParagraph.getWidths().getRawArray();
            int i6 = 0;
            int i7 = 0;
            MeasuredParagraph measuredParagraph3 = null;
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
                        MeasuredParagraph buildForMeasurement = MeasuredParagraph.buildForMeasurement(textPaint, str5, 0, str3.length(), textDirectionHeuristic, measuredParagraph3);
                        try {
                            if (i + buildForMeasurement.getWholeWidth() <= f) {
                                i11 = i7 + 1;
                                str4 = str5;
                            }
                            measuredParagraph3 = buildForMeasurement;
                            i5 = i10;
                            i6 = i11;
                        } catch (Throwable th) {
                            th = th;
                            measuredParagraph2 = buildForMeasurement;
                            if (measuredParagraph != null) {
                                measuredParagraph.recycle();
                            }
                            if (measuredParagraph2 != null) {
                                measuredParagraph2.recycle();
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
                    measuredParagraph2 = measuredParagraph3;
                }
            }
            int i12 = i6;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str4);
            spannableStringBuilder.insert(0, charSequence, 0, i12);
            if (measuredParagraph != null) {
                measuredParagraph.recycle();
            }
            if (measuredParagraph3 != null) {
                measuredParagraph3.recycle();
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
            char charAt = str.charAt(i);
            if (charAt == '\"') {
                sb.append("&quot;");
            } else if (charAt == '<') {
                sb.append("&lt;");
            } else if (charAt == '>') {
                sb.append("&gt;");
            } else if (charAt == '&') {
                sb.append("&amp;");
            } else if (charAt == '\'') {
                sb.append("&#39;");
            } else {
                sb.append(charAt);
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
        int i = 0;
        while (i < length) {
            int codePointAt = Character.codePointAt(charSequence, i);
            int type = Character.getType(codePointAt);
            if (type != 15 && type != 16 && type != 19 && type != 0 && type != 13 && type != 14 && type != 12) {
                return true;
            }
            i += Character.charCount(codePointAt);
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
        int i = 0;
        while (i < length) {
            int codePointAt = Character.codePointAt(charSequence, i);
            if (!Character.isDigit(codePointAt)) {
                return false;
            }
            i += Character.charCount(codePointAt);
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
        char charAt;
        char charAt2;
        if (i < 0) {
            return 0;
        }
        int i3 = (i2 & 4096) != 0 ? 4096 : 0;
        if ((i2 & 24576) != 0) {
            while (i > 0 && ((charAt2 = charSequence.charAt(i - 1)) == '\"' || charAt2 == '\'' || Character.getType(charAt2) == 21)) {
                i--;
            }
            int i4 = i;
            while (i4 > 0) {
                char charAt3 = charSequence.charAt(i4 - 1);
                if (charAt3 != ' ' && charAt3 != '\t') {
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
                    char charAt4 = charSequence.charAt(i4 - 1);
                    if (charAt4 != '\"' && charAt4 != '\'' && Character.getType(charAt4) != 22) {
                        break;
                    }
                    i4--;
                }
                if (i4 > 0 && ((charAt = charSequence.charAt(i4 - 1)) == '.' || charAt == '?' || charAt == '!')) {
                    if (charAt == '.') {
                        for (int i5 = i4 - 2; i5 >= 0; i5--) {
                            char charAt5 = charSequence.charAt(i5);
                            if (charAt5 != '.') {
                                if (!Character.isLetter(charAt5)) {
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
            int i = -1;
            while (true) {
                i = str.indexOf(str2, i + 1);
                if (i == -1) {
                    break;
                }
                if (i <= 0 || str.charAt(i - 1) == c) {
                    int length2 = str2.length() + i;
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

    public static String formatSimple(String str, Object... objArr) {
        int i;
        String str2;
        StringBuilder sb = new StringBuilder(str);
        int i2 = 0;
        int i3 = 0;
        while (i2 < sb.length()) {
            if (sb.charAt(i2) == '%') {
                char charAt = sb.charAt(i2 + 1);
                int i4 = 2;
                char c = 0;
                int i5 = 0;
                while (true) {
                    if ('0' <= charAt && charAt <= '9') {
                        if (c == 0) {
                            c = charAt == '0' ? '0' : ' ';
                        }
                        i5 = (i5 * 10) + Character.digit(charAt, 10);
                        i4++;
                        charAt = sb.charAt((i2 + i4) - 1);
                    }
                }
                if (charAt != '%') {
                    if (charAt != 'f' && charAt != 's') {
                        if (charAt != 'x') {
                            switch (charAt) {
                                case 'b':
                                    if (i3 == objArr.length) {
                                        throw new IllegalArgumentException("Too few arguments");
                                    }
                                    i = i3 + 1;
                                    Object obj = objArr[i3];
                                    if (obj instanceof Boolean) {
                                        str2 = Boolean.toString(((Boolean) obj).booleanValue());
                                        break;
                                    } else {
                                        str2 = Boolean.toString(obj != null);
                                        break;
                                    }
                                case 'c':
                                case 'd':
                                    break;
                                default:
                                    throw new IllegalArgumentException("Unsupported format code " + charAt);
                            }
                        } else {
                            if (i3 == objArr.length) {
                                throw new IllegalArgumentException("Too few arguments");
                            }
                            i = i3 + 1;
                            Object obj2 = objArr[i3];
                            if (obj2 instanceof Integer) {
                                str2 = Integer.toHexString(((Integer) obj2).intValue());
                            } else if (obj2 instanceof Long) {
                                str2 = Long.toHexString(((Long) obj2).longValue());
                            } else {
                                throw new IllegalArgumentException("Unsupported hex type " + obj2.getClass());
                            }
                        }
                    }
                    if (i3 == objArr.length) {
                        throw new IllegalArgumentException("Too few arguments");
                    }
                    i = i3 + 1;
                    str2 = String.valueOf(objArr[i3]);
                } else {
                    i = i3;
                    str2 = "%";
                }
                sb.replace(i2, i4 + i2, str2);
                int i6 = (c == '0' && str2.charAt(0) == '-') ? 1 : 0;
                for (int length = str2.length(); length < i5; length++) {
                    sb.insert(i2 + i6, c);
                }
                i2 += Math.max(str2.length(), i5);
                i3 = i;
            } else {
                i2++;
            }
        }
        if (i3 != objArr.length) {
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
            int codePointAt = stringWithRemovedChars.codePointAt(i5);
            int type = Character.getType(codePointAt);
            int charCount = Character.charCount(codePointAt);
            boolean isNewline = isNewline(codePointAt);
            if (z2 && isNewline) {
                stringWithRemovedChars.removeAllCharAfter(i5);
                break;
            }
            if (z3 && isNewline) {
                stringWithRemovedChars.removeRange(i5, i5 + charCount);
            } else if (type == 15 && !isNewline) {
                stringWithRemovedChars.removeRange(i5, i5 + charCount);
            } else if (z4 && !isWhitespace(codePointAt)) {
                if (i3 == -1) {
                    i3 = i5;
                }
                i4 = i5 + charCount;
            }
            i5 += charCount;
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
            String stringWithRemovedChars2 = stringWithRemovedChars.toString();
            if (isEmpty(stringWithRemovedChars2) || stringWithRemovedChars2.length() <= i6) {
                return stringWithRemovedChars2;
            }
            return ((String) trimToSize(stringWithRemovedChars2, i6)) + getEllipsisString(TruncateAt.END);
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
        int indexOfWordPrefix;
        int i;
        int length = charSequence.length();
        if (length != 0 && cArr != null) {
            float[] fArr = new float[length];
            char[] cArr2 = new char[length];
            int i2 = 0;
            while (i2 < cArr.length && !isCombinedCode(cArr[i2])) {
                i2++;
            }
            if (i2 != cArr.length && (indexOfWordPrefix = indexOfWordPrefix(charSequence, cArr)) >= 0 && indexOfWordPrefix < length) {
                getChars(charSequence, 0, length, cArr2, 0);
                textPaint.getTextRunAdvances(cArr2, 0, length, 0, length, false, fArr, 0);
                if (isIndianChar(cArr[i2])) {
                    i = indexOfWordPrefix;
                    while (i > 0 && isHalant(cArr2[i - 1])) {
                        i -= 2;
                    }
                    if (i < 0) {
                        return null;
                    }
                } else {
                    while (indexOfWordPrefix > 0 && fArr[indexOfWordPrefix] == 0.0f) {
                        indexOfWordPrefix--;
                    }
                    i = indexOfWordPrefix;
                }
                int length2 = indexOfWordPrefix + cArr.length;
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
