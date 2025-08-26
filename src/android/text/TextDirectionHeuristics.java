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

    /* JADX WARN: Multi-variable type inference failed */
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
    public static int isRtlCodePoint(int i) {
        byte directionality = Character.getDirectionality(i);
        if (directionality != -1) {
            if (directionality != 0) {
                return (directionality == 1 || directionality == 2) ? 0 : 2;
            }
            return 1;
        }
        if ((1424 > i || i > 2303) && ((64285 > i || i > 64975) && ((65008 > i || i > 65023) && ((65136 > i || i > 65279) && ((67584 > i || i > 69631) && (124928 > i || i > 126975)))))) {
            return ((8293 > i || i > 8297) && (65520 > i || i > 65528) && ((917504 > i || i > 921599) && ((64976 > i || i > 65007) && (i & Configuration.DENSITY_DPI_ANY) != 65534 && ((8352 > i || i > 8399) && (55296 > i || i > 57343))))) ? 1 : 2;
        }
        return 0;
    }

    private static abstract class TextDirectionHeuristicImpl implements TextDirectionHeuristic {
        private final TextDirectionAlgorithm mAlgorithm;

        protected abstract boolean defaultIsRtl();

        public TextDirectionHeuristicImpl(TextDirectionAlgorithm textDirectionAlgorithm) {
            this.mAlgorithm = textDirectionAlgorithm;
        }

        @Override // android.text.TextDirectionHeuristic
        public boolean isRtl(char[] cArr, int i, int i2) {
            return isRtl(CharBuffer.wrap(cArr), i, i2);
        }

        @Override // android.text.TextDirectionHeuristic
        public boolean isRtl(CharSequence charSequence, int i, int i2) {
            if (charSequence == null || i < 0 || i2 < 0 || charSequence.length() - i2 < i) {
                throw new IllegalArgumentException();
            }
            if (this.mAlgorithm == null) {
                return defaultIsRtl();
            }
            return doCheck(charSequence, i, i2);
        }

        private boolean doCheck(CharSequence charSequence, int i, int i2) {
            int iCheckRtl = this.mAlgorithm.checkRtl(charSequence, i, i2);
            if (iCheckRtl == 0) {
                return true;
            }
            if (iCheckRtl != 1) {
                return defaultIsRtl();
            }
            return false;
        }
    }

    private static class TextDirectionHeuristicInternal extends TextDirectionHeuristicImpl {
        private final boolean mDefaultIsRtl;

        private TextDirectionHeuristicInternal(TextDirectionAlgorithm textDirectionAlgorithm, boolean z) {
            super(textDirectionAlgorithm);
            this.mDefaultIsRtl = z;
        }

        @Override // android.text.TextDirectionHeuristics.TextDirectionHeuristicImpl
        protected boolean defaultIsRtl() {
            return this.mDefaultIsRtl;
        }
    }

    private static class FirstStrong implements TextDirectionAlgorithm {
        public static final FirstStrong INSTANCE = new FirstStrong();

        @Override // android.text.TextDirectionHeuristics.TextDirectionAlgorithm
        public int checkRtl(CharSequence charSequence, int i, int i2) {
            int i3 = i2 + i;
            int i4 = 0;
            int iIsRtlCodePoint = 2;
            while (i < i3 && iIsRtlCodePoint == 2) {
                int iCodePointAt = Character.codePointAt(charSequence, i);
                if (8294 <= iCodePointAt && iCodePointAt <= 8296) {
                    i4++;
                } else if (iCodePointAt == 8297) {
                    if (i4 > 0) {
                        i4--;
                    }
                } else if (i4 == 0) {
                    iIsRtlCodePoint = TextDirectionHeuristics.isRtlCodePoint(iCodePointAt);
                }
                i += Character.charCount(iCodePointAt);
            }
            return iIsRtlCodePoint;
        }

        private FirstStrong() {
        }
    }

    private static class AnyStrong implements TextDirectionAlgorithm {
        private final boolean mLookForRtl;
        public static final AnyStrong INSTANCE_RTL = new AnyStrong(true);
        public static final AnyStrong INSTANCE_LTR = new AnyStrong(false);

        @Override // android.text.TextDirectionHeuristics.TextDirectionAlgorithm
        public int checkRtl(CharSequence charSequence, int i, int i2) {
            int i3 = i2 + i;
            boolean z = false;
            int i4 = 0;
            while (i < i3) {
                int iCodePointAt = Character.codePointAt(charSequence, i);
                if (8294 <= iCodePointAt && iCodePointAt <= 8296) {
                    i4++;
                } else if (iCodePointAt == 8297) {
                    if (i4 > 0) {
                        i4--;
                    }
                } else if (i4 != 0) {
                    continue;
                } else {
                    int iIsRtlCodePoint = TextDirectionHeuristics.isRtlCodePoint(iCodePointAt);
                    if (iIsRtlCodePoint != 0) {
                        if (iIsRtlCodePoint != 1) {
                            continue;
                        } else if (!this.mLookForRtl) {
                            return 1;
                        }
                    } else if (this.mLookForRtl) {
                        return 0;
                    }
                    z = true;
                }
                i += Character.charCount(iCodePointAt);
                z = z;
            }
            if (z) {
                return this.mLookForRtl ? 1 : 0;
            }
            return 2;
        }

        private AnyStrong(boolean z) {
            this.mLookForRtl = z;
        }
    }

    private static class TextDirectionHeuristicLocale extends TextDirectionHeuristicImpl {
        public static final TextDirectionHeuristicLocale INSTANCE = new TextDirectionHeuristicLocale();

        public TextDirectionHeuristicLocale() {
            super(null);
        }

        @Override // android.text.TextDirectionHeuristics.TextDirectionHeuristicImpl
        protected boolean defaultIsRtl() {
            return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
        }
    }
}
