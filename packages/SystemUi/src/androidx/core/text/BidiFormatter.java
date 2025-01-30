package androidx.core.text;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import androidx.core.text.TextDirectionHeuristicsCompat;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BidiFormatter {
    public static final BidiFormatter DEFAULT_LTR_INSTANCE;
    public static final BidiFormatter DEFAULT_RTL_INSTANCE;
    public static final TextDirectionHeuristicsCompat.TextDirectionHeuristicInternal DEFAULT_TEXT_DIRECTION_HEURISTIC;
    public static final String LRM_STRING;
    public static final String RLM_STRING;
    public final TextDirectionHeuristicCompat mDefaultTextDirectionHeuristicCompat;
    public final int mFlags;
    public final boolean mIsRtlContext;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DirectionalityEstimator {
        public static final byte[] DIR_TYPE_CACHE = new byte[1792];
        public int charIndex;
        public final boolean isHtml;
        public char lastChar;
        public final int length;
        public final CharSequence text;

        static {
            for (int i = 0; i < 1792; i++) {
                DIR_TYPE_CACHE[i] = Character.getDirectionality(i);
            }
        }

        public DirectionalityEstimator(CharSequence charSequence, boolean z) {
            this.text = charSequence;
            this.isHtml = z;
            this.length = charSequence.length();
        }

        public final byte dirTypeBackward() {
            char charAt;
            char charAt2;
            int i = this.charIndex - 1;
            CharSequence charSequence = this.text;
            char charAt3 = charSequence.charAt(i);
            this.lastChar = charAt3;
            if (Character.isLowSurrogate(charAt3)) {
                int codePointBefore = Character.codePointBefore(charSequence, this.charIndex);
                this.charIndex -= Character.charCount(codePointBefore);
                return Character.getDirectionality(codePointBefore);
            }
            this.charIndex--;
            char c = this.lastChar;
            byte directionality = c < 1792 ? DIR_TYPE_CACHE[c] : Character.getDirectionality(c);
            if (!this.isHtml) {
                return directionality;
            }
            char c2 = this.lastChar;
            if (c2 != '>') {
                if (c2 != ';') {
                    return directionality;
                }
                int i2 = this.charIndex;
                do {
                    int i3 = this.charIndex;
                    if (i3 <= 0) {
                        break;
                    }
                    int i4 = i3 - 1;
                    this.charIndex = i4;
                    charAt = charSequence.charAt(i4);
                    this.lastChar = charAt;
                    if (charAt == '&') {
                        return (byte) 12;
                    }
                } while (charAt != ';');
                this.charIndex = i2;
                this.lastChar = ';';
                return (byte) 13;
            }
            int i5 = this.charIndex;
            while (true) {
                int i6 = this.charIndex;
                if (i6 <= 0) {
                    break;
                }
                int i7 = i6 - 1;
                this.charIndex = i7;
                char charAt4 = charSequence.charAt(i7);
                this.lastChar = charAt4;
                if (charAt4 == '<') {
                    break;
                }
                if (charAt4 == '>') {
                    break;
                }
                if (charAt4 == '\"' || charAt4 == '\'') {
                    do {
                        int i8 = this.charIndex;
                        if (i8 > 0) {
                            int i9 = i8 - 1;
                            this.charIndex = i9;
                            charAt2 = charSequence.charAt(i9);
                            this.lastChar = charAt2;
                        }
                    } while (charAt2 != charAt4);
                }
            }
            this.charIndex = i5;
            this.lastChar = '>';
            return (byte) 13;
        }
    }

    static {
        TextDirectionHeuristicsCompat.TextDirectionHeuristicInternal textDirectionHeuristicInternal = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
        DEFAULT_TEXT_DIRECTION_HEURISTIC = textDirectionHeuristicInternal;
        LRM_STRING = Character.toString((char) 8206);
        RLM_STRING = Character.toString((char) 8207);
        DEFAULT_LTR_INSTANCE = new BidiFormatter(false, 2, textDirectionHeuristicInternal);
        DEFAULT_RTL_INSTANCE = new BidiFormatter(true, 2, textDirectionHeuristicInternal);
    }

    public BidiFormatter(boolean z, int i, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        this.mIsRtlContext = z;
        this.mFlags = i;
        this.mDefaultTextDirectionHeuristicCompat = textDirectionHeuristicCompat;
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x00e5, code lost:
    
        if (r13 != r3) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:?, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:?, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:?, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x00cb, code lost:
    
        if (r13 != 0) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x00ce, code lost:
    
        if (r2 == 0) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x00ec, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x00d4, code lost:
    
        if (r0.charIndex <= 0) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x00da, code lost:
    
        switch(r0.dirTypeBackward()) {
            case 14: goto L107;
            case 15: goto L107;
            case 16: goto L106;
            case 17: goto L106;
            case 18: goto L105;
            default: goto L111;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x00de, code lost:
    
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x00e1, code lost:
    
        if (r13 != r3) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x00e9, code lost:
    
        r3 = r3 - 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int getEntryDir(CharSequence charSequence) {
        byte directionality;
        char charAt;
        char charAt2;
        DirectionalityEstimator directionalityEstimator = new DirectionalityEstimator(charSequence, false);
        directionalityEstimator.charIndex = 0;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int i4 = directionalityEstimator.charIndex;
            int i5 = directionalityEstimator.length;
            if (i4 < i5 && i == 0) {
                CharSequence charSequence2 = directionalityEstimator.text;
                char charAt3 = charSequence2.charAt(i4);
                directionalityEstimator.lastChar = charAt3;
                if (Character.isHighSurrogate(charAt3)) {
                    int codePointAt = Character.codePointAt(charSequence2, directionalityEstimator.charIndex);
                    directionalityEstimator.charIndex = Character.charCount(codePointAt) + directionalityEstimator.charIndex;
                    directionality = Character.getDirectionality(codePointAt);
                } else {
                    directionalityEstimator.charIndex++;
                    char c = directionalityEstimator.lastChar;
                    directionality = c < 1792 ? DirectionalityEstimator.DIR_TYPE_CACHE[c] : Character.getDirectionality(c);
                    if (directionalityEstimator.isHtml) {
                        char c2 = directionalityEstimator.lastChar;
                        if (c2 == '<') {
                            int i6 = directionalityEstimator.charIndex;
                            while (true) {
                                int i7 = directionalityEstimator.charIndex;
                                if (i7 < i5) {
                                    directionalityEstimator.charIndex = i7 + 1;
                                    char charAt4 = charSequence2.charAt(i7);
                                    directionalityEstimator.lastChar = charAt4;
                                    if (charAt4 != '>') {
                                        if (charAt4 == '\"' || charAt4 == '\'') {
                                            do {
                                                int i8 = directionalityEstimator.charIndex;
                                                if (i8 < i5) {
                                                    directionalityEstimator.charIndex = i8 + 1;
                                                    charAt2 = charSequence2.charAt(i8);
                                                    directionalityEstimator.lastChar = charAt2;
                                                }
                                            } while (charAt2 != charAt4);
                                        }
                                    }
                                } else {
                                    directionalityEstimator.charIndex = i6;
                                    directionalityEstimator.lastChar = '<';
                                    directionality = 13;
                                }
                            }
                        } else if (c2 == '&') {
                            do {
                                int i9 = directionalityEstimator.charIndex;
                                if (i9 < i5) {
                                    directionalityEstimator.charIndex = i9 + 1;
                                    charAt = charSequence2.charAt(i9);
                                    directionalityEstimator.lastChar = charAt;
                                }
                                directionality = 12;
                            } while (charAt != ';');
                            directionality = 12;
                        }
                    }
                }
                if (directionality != 0) {
                    if (directionality == 1 || directionality == 2) {
                        if (i3 == 0) {
                        }
                    } else if (directionality != 9) {
                        switch (directionality) {
                            case 14:
                            case 15:
                                i3++;
                                i2 = -1;
                                continue;
                            case 16:
                            case 17:
                                i3++;
                                i2 = 1;
                                continue;
                            case 18:
                                i3--;
                                i2 = 0;
                                continue;
                        }
                    }
                } else if (i3 == 0) {
                }
                i = i3;
            }
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0042, code lost:
    
        return 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int getExitDir(CharSequence charSequence) {
        DirectionalityEstimator directionalityEstimator = new DirectionalityEstimator(charSequence, false);
        directionalityEstimator.charIndex = directionalityEstimator.length;
        int i = 0;
        while (true) {
            int i2 = i;
            while (directionalityEstimator.charIndex > 0) {
                byte dirTypeBackward = directionalityEstimator.dirTypeBackward();
                if (dirTypeBackward != 0) {
                    if (dirTypeBackward == 1 || dirTypeBackward == 2) {
                        if (i2 != 0) {
                            if (i == 0) {
                                break;
                            }
                        }
                    } else if (dirTypeBackward != 9) {
                        switch (dirTypeBackward) {
                            case 14:
                            case 15:
                                if (i == i2) {
                                    break;
                                }
                                i2--;
                                break;
                            case 16:
                            case 17:
                                if (i == i2) {
                                    break;
                                }
                                i2--;
                                break;
                            case 18:
                                i2++;
                                break;
                            default:
                                if (i != 0) {
                                    break;
                                } else {
                                    break;
                                }
                                break;
                        }
                    } else {
                        continue;
                    }
                } else if (i2 != 0) {
                    if (i == 0) {
                        break;
                    }
                }
            }
            return 0;
            i = i2;
        }
        return -1;
    }

    public static BidiFormatter getInstance() {
        Builder builder = new Builder();
        int i = builder.mFlags;
        return (i == 2 && builder.mTextDirectionHeuristicCompat == DEFAULT_TEXT_DIRECTION_HEURISTIC) ? builder.mIsRtlContext ? DEFAULT_RTL_INSTANCE : DEFAULT_LTR_INSTANCE : new BidiFormatter(builder.mIsRtlContext, i, builder.mTextDirectionHeuristicCompat);
    }

    public final String unicodeWrap(String str) {
        if (str == null) {
            return null;
        }
        return ((SpannableStringBuilder) unicodeWrap(str, this.mDefaultTextDirectionHeuristicCompat)).toString();
    }

    public final CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        if (charSequence == null) {
            return null;
        }
        boolean isRtl = ((TextDirectionHeuristicsCompat.TextDirectionHeuristicImpl) textDirectionHeuristicCompat).isRtl(charSequence.length(), charSequence);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        boolean z = (this.mFlags & 2) != 0;
        String str = RLM_STRING;
        String str2 = LRM_STRING;
        boolean z2 = this.mIsRtlContext;
        if (z) {
            boolean isRtl2 = (isRtl ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR).isRtl(charSequence.length(), charSequence);
            spannableStringBuilder.append((CharSequence) ((z2 || !(isRtl2 || getEntryDir(charSequence) == 1)) ? (!z2 || (isRtl2 && getEntryDir(charSequence) != -1)) ? "" : str : str2));
        }
        if (isRtl != z2) {
            spannableStringBuilder.append(isRtl ? (char) 8235 : (char) 8234);
            spannableStringBuilder.append(charSequence);
            spannableStringBuilder.append((char) 8236);
        } else {
            spannableStringBuilder.append(charSequence);
        }
        boolean isRtl3 = (isRtl ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR).isRtl(charSequence.length(), charSequence);
        if (!z2 && (isRtl3 || getExitDir(charSequence) == 1)) {
            str = str2;
        } else if (!z2 || (isRtl3 && getExitDir(charSequence) != -1)) {
            str = "";
        }
        spannableStringBuilder.append((CharSequence) str);
        return spannableStringBuilder;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
        public int mFlags;
        public boolean mIsRtlContext;
        public TextDirectionHeuristicsCompat.TextDirectionHeuristicInternal mTextDirectionHeuristicCompat;

        public Builder() {
            Locale locale = Locale.getDefault();
            TextDirectionHeuristicsCompat.TextDirectionHeuristicInternal textDirectionHeuristicInternal = BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC;
            int i = TextUtilsCompat.$r8$clinit;
            this.mIsRtlContext = TextUtils.getLayoutDirectionFromLocale(locale) == 1;
            this.mTextDirectionHeuristicCompat = BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC;
            this.mFlags = 2;
        }

        public Builder(boolean z) {
            this.mIsRtlContext = z;
            this.mTextDirectionHeuristicCompat = BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC;
            this.mFlags = 2;
        }

        public Builder(Locale locale) {
            TextDirectionHeuristicsCompat.TextDirectionHeuristicInternal textDirectionHeuristicInternal = BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC;
            int i = TextUtilsCompat.$r8$clinit;
            this.mIsRtlContext = TextUtils.getLayoutDirectionFromLocale(locale) == 1;
            this.mTextDirectionHeuristicCompat = BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC;
            this.mFlags = 2;
        }
    }
}
