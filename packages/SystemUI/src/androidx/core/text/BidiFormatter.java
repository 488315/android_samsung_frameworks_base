package androidx.core.text;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import androidx.core.text.TextDirectionHeuristicsCompat;
import java.util.Locale;

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

    public class DirectionalityEstimator {
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
            char cCharAt;
            char cCharAt2;
            char cCharAt3 = this.text.charAt(this.charIndex - 1);
            this.lastChar = cCharAt3;
            if (Character.isLowSurrogate(cCharAt3)) {
                int iCodePointBefore = Character.codePointBefore(this.text, this.charIndex);
                this.charIndex -= Character.charCount(iCodePointBefore);
                return Character.getDirectionality(iCodePointBefore);
            }
            this.charIndex--;
            char c = this.lastChar;
            byte directionality = c < 1792 ? DIR_TYPE_CACHE[c] : Character.getDirectionality(c);
            if (this.isHtml) {
                char c2 = this.lastChar;
                if (c2 == '>') {
                    int i = this.charIndex;
                    while (true) {
                        int i2 = this.charIndex;
                        if (i2 <= 0) {
                            break;
                        }
                        CharSequence charSequence = this.text;
                        int i3 = i2 - 1;
                        this.charIndex = i3;
                        char cCharAt4 = charSequence.charAt(i3);
                        this.lastChar = cCharAt4;
                        if (cCharAt4 == '<') {
                            return (byte) 12;
                        }
                        if (cCharAt4 == '>') {
                            break;
                        }
                        if (cCharAt4 == '\"' || cCharAt4 == '\'') {
                            do {
                                int i4 = this.charIndex;
                                if (i4 > 0) {
                                    CharSequence charSequence2 = this.text;
                                    int i5 = i4 - 1;
                                    this.charIndex = i5;
                                    cCharAt2 = charSequence2.charAt(i5);
                                    this.lastChar = cCharAt2;
                                }
                            } while (cCharAt2 != cCharAt4);
                        }
                    }
                    this.charIndex = i;
                    this.lastChar = '>';
                    return (byte) 13;
                }
                if (c2 == ';') {
                    int i6 = this.charIndex;
                    do {
                        int i7 = this.charIndex;
                        if (i7 <= 0) {
                            break;
                        }
                        CharSequence charSequence3 = this.text;
                        int i8 = i7 - 1;
                        this.charIndex = i8;
                        cCharAt = charSequence3.charAt(i8);
                        this.lastChar = cCharAt;
                        if (cCharAt == '&') {
                            return (byte) 12;
                        }
                    } while (cCharAt != ';');
                    this.charIndex = i6;
                    this.lastChar = ';';
                    return (byte) 13;
                }
            }
            return directionality;
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

    /* JADX WARN: Code restructure failed: missing block: B:56:0x00d4, code lost:
    
        if (r14 != 0) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00d7, code lost:
    
        if (r2 == 0) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00d9, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00dc, code lost:
    
        if (r0.charIndex <= 0) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00e2, code lost:
    
        switch(r0.dirTypeBackward()) {
            case 14: goto L106;
            case 15: goto L106;
            case 16: goto L105;
            case 17: goto L105;
            case 18: goto L104;
            default: goto L110;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00e6, code lost:
    
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00e9, code lost:
    
        if (r14 != r3) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00eb, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00ec, code lost:
    
        r3 = r3 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00ef, code lost:
    
        if (r14 != r3) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00f2, code lost:
    
        return 0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int getEntryDir(CharSequence charSequence) {
        byte directionality;
        char cCharAt;
        char cCharAt2;
        DirectionalityEstimator directionalityEstimator = new DirectionalityEstimator(charSequence, false);
        directionalityEstimator.charIndex = 0;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int i4 = directionalityEstimator.charIndex;
            int i5 = directionalityEstimator.length;
            if (i4 < i5 && i == 0) {
                char cCharAt3 = directionalityEstimator.text.charAt(i4);
                directionalityEstimator.lastChar = cCharAt3;
                if (Character.isHighSurrogate(cCharAt3)) {
                    int iCodePointAt = Character.codePointAt(directionalityEstimator.text, directionalityEstimator.charIndex);
                    directionalityEstimator.charIndex = Character.charCount(iCodePointAt) + directionalityEstimator.charIndex;
                    directionality = Character.getDirectionality(iCodePointAt);
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
                                    CharSequence charSequence2 = directionalityEstimator.text;
                                    directionalityEstimator.charIndex = i7 + 1;
                                    char cCharAt4 = charSequence2.charAt(i7);
                                    directionalityEstimator.lastChar = cCharAt4;
                                    if (cCharAt4 != '>') {
                                        if (cCharAt4 == '\"' || cCharAt4 == '\'') {
                                            do {
                                                int i8 = directionalityEstimator.charIndex;
                                                if (i8 < i5) {
                                                    CharSequence charSequence3 = directionalityEstimator.text;
                                                    directionalityEstimator.charIndex = i8 + 1;
                                                    cCharAt2 = charSequence3.charAt(i8);
                                                    directionalityEstimator.lastChar = cCharAt2;
                                                }
                                            } while (cCharAt2 != cCharAt4);
                                        }
                                    }
                                } else {
                                    directionalityEstimator.charIndex = i6;
                                    directionalityEstimator.lastChar = '<';
                                    directionality = 13;
                                }
                            }
                            directionality = 12;
                        } else if (c2 == '&') {
                            do {
                                int i9 = directionalityEstimator.charIndex;
                                if (i9 < i5) {
                                    CharSequence charSequence4 = directionalityEstimator.text;
                                    directionalityEstimator.charIndex = i9 + 1;
                                    cCharAt = charSequence4.charAt(i9);
                                    directionalityEstimator.lastChar = cCharAt;
                                }
                                directionality = 12;
                            } while (cCharAt != ';');
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

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0034, code lost:
    
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
                byte bDirTypeBackward = directionalityEstimator.dirTypeBackward();
                if (bDirTypeBackward != 0) {
                    if (bDirTypeBackward == 1 || bDirTypeBackward == 2) {
                        if (i != 0) {
                            if (i2 == 0) {
                                break;
                            }
                        }
                    } else if (bDirTypeBackward != 9) {
                        switch (bDirTypeBackward) {
                            case 14:
                            case 15:
                                if (i2 == i) {
                                    return -1;
                                }
                                i--;
                                break;
                            case 16:
                            case 17:
                                if (i2 == i) {
                                    break;
                                }
                                i--;
                                break;
                            case 18:
                                i++;
                                break;
                            default:
                                if (i2 != 0) {
                                    break;
                                } else {
                                    break;
                                }
                        }
                    } else {
                        continue;
                    }
                } else {
                    if (i == 0) {
                        return -1;
                    }
                    if (i2 == 0) {
                        break;
                    }
                }
            }
            return 0;
        }
    }

    public static BidiFormatter getInstance() {
        Builder builder = new Builder();
        int i = builder.mFlags;
        return (i == 2 && builder.mTextDirectionHeuristicCompat == DEFAULT_TEXT_DIRECTION_HEURISTIC) ? builder.mIsRtlContext ? DEFAULT_RTL_INSTANCE : DEFAULT_LTR_INSTANCE : new BidiFormatter(builder.mIsRtlContext, i, builder.mTextDirectionHeuristicCompat);
    }

    public final CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        if (charSequence == null) {
            return null;
        }
        boolean zIsRtl = ((TextDirectionHeuristicsCompat.TextDirectionHeuristicImpl) textDirectionHeuristicCompat).isRtl(charSequence.length(), charSequence);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int i = this.mFlags & 2;
        String str = "";
        String str2 = RLM_STRING;
        String str3 = LRM_STRING;
        boolean z = this.mIsRtlContext;
        if (i != 0) {
            boolean zIsRtl2 = (zIsRtl ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR).isRtl(charSequence.length(), charSequence);
            spannableStringBuilder.append((CharSequence) ((z || !(zIsRtl2 || getEntryDir(charSequence) == 1)) ? (!z || (zIsRtl2 && getEntryDir(charSequence) != -1)) ? "" : str2 : str3));
        }
        if (zIsRtl != z) {
            spannableStringBuilder.append(zIsRtl ? (char) 8235 : (char) 8234);
            spannableStringBuilder.append(charSequence);
            spannableStringBuilder.append((char) 8236);
        } else {
            spannableStringBuilder.append(charSequence);
        }
        boolean zIsRtl3 = (zIsRtl ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR).isRtl(charSequence.length(), charSequence);
        if (!z && (zIsRtl3 || getExitDir(charSequence) == 1)) {
            str = str3;
        } else if (z && (!zIsRtl3 || getExitDir(charSequence) == -1)) {
            str = str2;
        }
        spannableStringBuilder.append((CharSequence) str);
        return spannableStringBuilder;
    }

    public final class Builder {
        public final int mFlags;
        public final boolean mIsRtlContext;
        public final TextDirectionHeuristicsCompat.TextDirectionHeuristicInternal mTextDirectionHeuristicCompat;

        public Builder() {
            Locale locale = Locale.getDefault();
            TextDirectionHeuristicsCompat.TextDirectionHeuristicInternal textDirectionHeuristicInternal = BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC;
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
            this.mIsRtlContext = TextUtils.getLayoutDirectionFromLocale(locale) == 1;
            this.mTextDirectionHeuristicCompat = BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC;
            this.mFlags = 2;
        }
    }
}
