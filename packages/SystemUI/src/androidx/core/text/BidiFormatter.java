package androidx.core.text;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import androidx.core.text.TextDirectionHeuristicsCompat;
import java.util.Locale;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* JADX WARN: Code restructure failed: missing block: B:36:0x007e, code lost:
        
            r6.charIndex = r0;
            r6.lastChar = '>';
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x0082, code lost:
        
            return 13;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final byte dirTypeBackward() {
            /*
                r6 = this;
                java.lang.CharSequence r0 = r6.text
                int r1 = r6.charIndex
                int r1 = r1 + (-1)
                char r0 = r0.charAt(r1)
                r6.lastChar = r0
                boolean r0 = java.lang.Character.isLowSurrogate(r0)
                if (r0 == 0) goto L28
                java.lang.CharSequence r0 = r6.text
                int r1 = r6.charIndex
                int r0 = java.lang.Character.codePointBefore(r0, r1)
                int r1 = r6.charIndex
                int r2 = java.lang.Character.charCount(r0)
                int r1 = r1 - r2
                r6.charIndex = r1
                byte r6 = java.lang.Character.getDirectionality(r0)
                return r6
            L28:
                int r0 = r6.charIndex
                int r0 = r0 + (-1)
                r6.charIndex = r0
                char r0 = r6.lastChar
                r1 = 1792(0x700, float:2.511E-42)
                if (r0 >= r1) goto L39
                byte[] r1 = androidx.core.text.BidiFormatter.DirectionalityEstimator.DIR_TYPE_CACHE
                r0 = r1[r0]
                goto L3d
            L39:
                byte r0 = java.lang.Character.getDirectionality(r0)
            L3d:
                boolean r1 = r6.isHtml
                if (r1 == 0) goto La7
                char r1 = r6.lastChar
                r2 = 13
                r3 = 62
                if (r1 != r3) goto L83
                int r0 = r6.charIndex
            L4b:
                int r1 = r6.charIndex
                if (r1 <= 0) goto L7e
                java.lang.CharSequence r4 = r6.text
                int r1 = r1 + (-1)
                r6.charIndex = r1
                char r1 = r4.charAt(r1)
                r6.lastChar = r1
                r4 = 60
                if (r1 != r4) goto L60
                goto L9d
            L60:
                if (r1 != r3) goto L63
                goto L7e
            L63:
                r4 = 34
                if (r1 == r4) goto L6b
                r4 = 39
                if (r1 != r4) goto L4b
            L6b:
                int r4 = r6.charIndex
                if (r4 <= 0) goto L4b
                java.lang.CharSequence r5 = r6.text
                int r4 = r4 + (-1)
                r6.charIndex = r4
                char r4 = r5.charAt(r4)
                r6.lastChar = r4
                if (r4 == r1) goto L4b
                goto L6b
            L7e:
                r6.charIndex = r0
                r6.lastChar = r3
                return r2
            L83:
                r3 = 59
                if (r1 != r3) goto La7
                int r0 = r6.charIndex
            L89:
                int r1 = r6.charIndex
                if (r1 <= 0) goto La2
                java.lang.CharSequence r4 = r6.text
                int r1 = r1 + (-1)
                r6.charIndex = r1
                char r1 = r4.charAt(r1)
                r6.lastChar = r1
                r4 = 38
                if (r1 != r4) goto La0
            L9d:
                r6 = 12
                return r6
            La0:
                if (r1 != r3) goto L89
            La2:
                r6.charIndex = r0
                r6.lastChar = r3
                return r2
            La7:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.text.BidiFormatter.DirectionalityEstimator.dirTypeBackward():byte");
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

    /* JADX WARN: Code restructure failed: missing block: B:100:0x00ef, code lost:
    
        if (r14 != r3) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x00f2, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00eb, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x00d4, code lost:
    
        if (r14 != 0) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x00d7, code lost:
    
        if (r2 == 0) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x00d9, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x00dc, code lost:
    
        if (r0.charIndex <= 0) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x00e2, code lost:
    
        switch(r0.dirTypeBackward()) {
            case 14: goto L105;
            case 15: goto L105;
            case 16: goto L104;
            case 17: goto L104;
            case 18: goto L103;
            default: goto L110;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x00e6, code lost:
    
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x00e9, code lost:
    
        if (r14 != r3) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x00ec, code lost:
    
        r3 = r3 - 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getEntryDir(java.lang.CharSequence r14) {
        /*
            Method dump skipped, instructions count: 272
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.text.BidiFormatter.getEntryDir(java.lang.CharSequence):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0034, code lost:
    
        return 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getExitDir(java.lang.CharSequence r6) {
        /*
            androidx.core.text.BidiFormatter$DirectionalityEstimator r0 = new androidx.core.text.BidiFormatter$DirectionalityEstimator
            r1 = 0
            r0.<init>(r6, r1)
            int r6 = r0.length
            r0.charIndex = r6
            r6 = r1
        Lb:
            r2 = r6
        Lc:
            int r3 = r0.charIndex
            if (r3 <= 0) goto L3f
            byte r3 = r0.dirTypeBackward()
            if (r3 == 0) goto L38
            r4 = 1
            if (r3 == r4) goto L32
            r5 = 2
            if (r3 == r5) goto L32
            r5 = 9
            if (r3 == r5) goto Lc
            switch(r3) {
                case 14: goto L2f;
                case 15: goto L2f;
                case 16: goto L29;
                case 17: goto L29;
                case 18: goto L26;
                default: goto L23;
            }
        L23:
            if (r2 != 0) goto Lc
            goto L3e
        L26:
            int r6 = r6 + 1
            goto Lc
        L29:
            if (r2 != r6) goto L2c
            goto L34
        L2c:
            int r6 = r6 + (-1)
            goto Lc
        L2f:
            if (r2 != r6) goto L2c
            goto L3a
        L32:
            if (r6 != 0) goto L35
        L34:
            return r4
        L35:
            if (r2 != 0) goto Lc
            goto L3e
        L38:
            if (r6 != 0) goto L3c
        L3a:
            r6 = -1
            return r6
        L3c:
            if (r2 != 0) goto Lc
        L3e:
            goto Lb
        L3f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.text.BidiFormatter.getExitDir(java.lang.CharSequence):int");
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
        boolean isRtl = ((TextDirectionHeuristicsCompat.TextDirectionHeuristicImpl) textDirectionHeuristicCompat).isRtl(charSequence.length(), charSequence);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int i = this.mFlags & 2;
        String str = "";
        String str2 = RLM_STRING;
        String str3 = LRM_STRING;
        boolean z = this.mIsRtlContext;
        if (i != 0) {
            boolean isRtl2 = (isRtl ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR).isRtl(charSequence.length(), charSequence);
            spannableStringBuilder.append((CharSequence) ((z || !(isRtl2 || getEntryDir(charSequence) == 1)) ? (!z || (isRtl2 && getEntryDir(charSequence) != -1)) ? "" : str2 : str3));
        }
        if (isRtl != z) {
            spannableStringBuilder.append(isRtl ? (char) 8235 : (char) 8234);
            spannableStringBuilder.append(charSequence);
            spannableStringBuilder.append((char) 8236);
        } else {
            spannableStringBuilder.append(charSequence);
        }
        boolean isRtl3 = (isRtl ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR).isRtl(charSequence.length(), charSequence);
        if (!z && (isRtl3 || getExitDir(charSequence) == 1)) {
            str = str3;
        } else if (z && (!isRtl3 || getExitDir(charSequence) == -1)) {
            str = str2;
        }
        spannableStringBuilder.append((CharSequence) str);
        return spannableStringBuilder;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
