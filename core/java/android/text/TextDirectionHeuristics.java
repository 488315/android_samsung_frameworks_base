package android.text;

import android.content.res.Configuration;
import java.nio.CharBuffer;
import java.util.Locale;

/* loaded from: classes4.dex */
public class TextDirectionHeuristics {
    public static final TextDirectionHeuristic ANYRTL_LTR;
    public static final TextDirectionHeuristic FIRSTSTRONG_LTR;
    public static final TextDirectionHeuristic FIRSTSTRONG_RTL;
    public static final TextDirectionHeuristic LOCALE = TextDirectionHeuristicLocale.INSTANCE;
    public static final TextDirectionHeuristic LTR;
    public static final TextDirectionHeuristic RTL;
    private static final int STATE_FALSE = 1;
    private static final int STATE_TRUE = 0;
    private static final int STATE_UNKNOWN = 2;

    private interface TextDirectionAlgorithm {
        int checkRtl(CharSequence charSequence, int i, int i2);
    }

    static {
        boolean z = false;
        LTR = new TextDirectionHeuristicInternal(null, z);
        boolean z2 = true;
        RTL = new TextDirectionHeuristicInternal(0 == true ? 1 : 0, z2);
        FIRSTSTRONG_LTR = new TextDirectionHeuristicInternal(FirstStrong.INSTANCE, z);
        FIRSTSTRONG_RTL = new TextDirectionHeuristicInternal(FirstStrong.INSTANCE, z2);
        ANYRTL_LTR = new TextDirectionHeuristicInternal(AnyStrong.INSTANCE_RTL, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int isRtlCodePoint(int codePoint) {
        switch (Character.getDirectionality(codePoint)) {
            case -1:
                if ((1424 > codePoint || codePoint > 2303) && ((64285 > codePoint || codePoint > 64975) && ((65008 > codePoint || codePoint > 65023) && ((65136 > codePoint || codePoint > 65279) && ((67584 > codePoint || codePoint > 69631) && (124928 > codePoint || codePoint > 126975)))))) {
                    return ((8293 > codePoint || codePoint > 8297) && (65520 > codePoint || codePoint > 65528) && ((917504 > codePoint || codePoint > 921599) && ((64976 > codePoint || codePoint > 65007) && (codePoint & Configuration.DENSITY_DPI_ANY) != 65534 && ((8352 > codePoint || codePoint > 8399) && (55296 > codePoint || codePoint > 57343))))) ? 1 : 2;
                }
                return 0;
            case 0:
                return 1;
            case 1:
            case 2:
                return 0;
            default:
                return 2;
        }
    }

    private static abstract class TextDirectionHeuristicImpl implements TextDirectionHeuristic {
        private final TextDirectionAlgorithm mAlgorithm;

        protected abstract boolean defaultIsRtl();

        public TextDirectionHeuristicImpl(TextDirectionAlgorithm algorithm) {
            this.mAlgorithm = algorithm;
        }

        @Override // android.text.TextDirectionHeuristic
        public boolean isRtl(char[] array, int start, int count) {
            return isRtl(CharBuffer.wrap(array), start, count);
        }

        @Override // android.text.TextDirectionHeuristic
        public boolean isRtl(CharSequence cs, int start, int count) {
            if (cs == null || start < 0 || count < 0 || cs.length() - count < start) {
                throw new IllegalArgumentException();
            }
            if (this.mAlgorithm == null) {
                return defaultIsRtl();
            }
            return doCheck(cs, start, count);
        }

        private boolean doCheck(CharSequence cs, int start, int count) {
            switch (this.mAlgorithm.checkRtl(cs, start, count)) {
                case 0:
                    return true;
                case 1:
                    return false;
                default:
                    return defaultIsRtl();
            }
        }
    }

    private static class TextDirectionHeuristicInternal extends TextDirectionHeuristicImpl {
        private final boolean mDefaultIsRtl;

        private TextDirectionHeuristicInternal(TextDirectionAlgorithm algorithm, boolean defaultIsRtl) {
            super(algorithm);
            this.mDefaultIsRtl = defaultIsRtl;
        }

        @Override // android.text.TextDirectionHeuristics.TextDirectionHeuristicImpl
        protected boolean defaultIsRtl() {
            return this.mDefaultIsRtl;
        }
    }

    private static class FirstStrong implements TextDirectionAlgorithm {
        public static final FirstStrong INSTANCE = new FirstStrong();

        @Override // android.text.TextDirectionHeuristics.TextDirectionAlgorithm
        public int checkRtl(CharSequence cs, int start, int count) {
            int result = 2;
            int openIsolateCount = 0;
            int i = start;
            int end = start + count;
            while (i < end && result == 2) {
                int cp = Character.codePointAt(cs, i);
                if (8294 <= cp && cp <= 8296) {
                    openIsolateCount++;
                } else if (cp == 8297) {
                    if (openIsolateCount > 0) {
                        openIsolateCount--;
                    }
                } else if (openIsolateCount == 0) {
                    result = TextDirectionHeuristics.isRtlCodePoint(cp);
                }
                i += Character.charCount(cp);
            }
            return result;
        }

        private FirstStrong() {
        }
    }

    private static class AnyStrong implements TextDirectionAlgorithm {
        private final boolean mLookForRtl;
        public static final AnyStrong INSTANCE_RTL = new AnyStrong(true);
        public static final AnyStrong INSTANCE_LTR = new AnyStrong(false);

        /* JADX WARN: Code restructure failed: missing block: B:32:0x0039, code lost:
        
            continue;
         */
        @Override // android.text.TextDirectionHeuristics.TextDirectionAlgorithm
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int checkRtl(java.lang.CharSequence r7, int r8, int r9) {
            /*
                r6 = this;
                r0 = 0
                r1 = 0
                r2 = r8
                int r3 = r8 + r9
            L5:
                if (r2 >= r3) goto L3f
                int r4 = java.lang.Character.codePointAt(r7, r2)
                r5 = 8294(0x2066, float:1.1622E-41)
                if (r5 > r4) goto L16
                r5 = 8296(0x2068, float:1.1625E-41)
                if (r4 > r5) goto L16
                int r1 = r1 + 1
                goto L39
            L16:
                r5 = 8297(0x2069, float:1.1627E-41)
                if (r4 != r5) goto L1f
                if (r1 <= 0) goto L39
                int r1 = r1 + (-1)
                goto L39
            L1f:
                if (r1 != 0) goto L39
                int r5 = android.text.TextDirectionHeuristics.m5175$$Nest$smisRtlCodePoint(r4)
                switch(r5) {
                    case 0: goto L31;
                    case 1: goto L29;
                    default: goto L28;
                }
            L28:
                goto L39
            L29:
                boolean r5 = r6.mLookForRtl
                if (r5 != 0) goto L2f
                r5 = 1
                return r5
            L2f:
                r0 = 1
                goto L39
            L31:
                boolean r5 = r6.mLookForRtl
                if (r5 == 0) goto L37
                r5 = 0
                return r5
            L37:
                r0 = 1
            L39:
                int r5 = java.lang.Character.charCount(r4)
                int r2 = r2 + r5
                goto L5
            L3f:
                if (r0 == 0) goto L44
                boolean r2 = r6.mLookForRtl
                return r2
            L44:
                r2 = 2
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.text.TextDirectionHeuristics.AnyStrong.checkRtl(java.lang.CharSequence, int, int):int");
        }

        private AnyStrong(boolean lookForRtl) {
            this.mLookForRtl = lookForRtl;
        }
    }

    private static class TextDirectionHeuristicLocale extends TextDirectionHeuristicImpl {
        public static final TextDirectionHeuristicLocale INSTANCE = new TextDirectionHeuristicLocale();

        public TextDirectionHeuristicLocale() {
            super(null);
        }

        @Override // android.text.TextDirectionHeuristics.TextDirectionHeuristicImpl
        protected boolean defaultIsRtl() {
            int dir = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault());
            return dir == 1;
        }
    }
}
