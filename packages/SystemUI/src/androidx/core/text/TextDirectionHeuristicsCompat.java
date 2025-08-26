package androidx.core.text;

import android.text.TextUtils;
import java.util.Locale;

/* loaded from: classes.dex */
public final class TextDirectionHeuristicsCompat {
    public static final TextDirectionHeuristicInternal FIRSTSTRONG_LTR;
    public static final TextDirectionHeuristicInternal FIRSTSTRONG_RTL;
    public static final TextDirectionHeuristicInternal LTR = new TextDirectionHeuristicInternal(null, false);
    public static final TextDirectionHeuristicInternal RTL = new TextDirectionHeuristicInternal(null, true);

    public class AnyStrong implements TextDirectionAlgorithm {
        public static final AnyStrong INSTANCE_RTL = new AnyStrong(true);
        public final boolean mLookForRtl;

        private AnyStrong(boolean z) {
            this.mLookForRtl = z;
        }

        @Override // androidx.core.text.TextDirectionHeuristicsCompat.TextDirectionAlgorithm
        public final int checkRtl(int i, CharSequence charSequence) {
            int i2 = 0;
            boolean z = false;
            while (true) {
                char c = 2;
                boolean z2 = this.mLookForRtl;
                if (i2 >= i) {
                    if (z) {
                        return z2 ? 1 : 0;
                    }
                    return 2;
                }
                byte directionality = Character.getDirectionality(charSequence.charAt(i2));
                TextDirectionHeuristicInternal textDirectionHeuristicInternal = TextDirectionHeuristicsCompat.LTR;
                if (directionality == 0) {
                    c = 1;
                } else if (directionality == 1 || directionality == 2) {
                    c = 0;
                }
                if (c != 0) {
                    if (c != 1) {
                        continue;
                        i2++;
                        z = z;
                    } else if (!z2) {
                        return 1;
                    }
                } else if (z2) {
                    return 0;
                }
                z = true;
                i2++;
                z = z;
            }
        }
    }

    public class FirstStrong implements TextDirectionAlgorithm {
        public static final FirstStrong INSTANCE = new FirstStrong();

        private FirstStrong() {
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x001e  */
        /* JADX WARN: Removed duplicated region for block: B:12:0x0020  */
        @Override // androidx.core.text.TextDirectionHeuristicsCompat.TextDirectionAlgorithm
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final int checkRtl(int i, CharSequence charSequence) {
            int i2 = 2;
            for (int i3 = 0; i3 < i && i2 == 2; i3++) {
                byte directionality = Character.getDirectionality(charSequence.charAt(i3));
                TextDirectionHeuristicInternal textDirectionHeuristicInternal = TextDirectionHeuristicsCompat.LTR;
                if (directionality == 0) {
                    i2 = 1;
                } else if (directionality != 1 && directionality != 2) {
                    switch (directionality) {
                        case 14:
                        case 15:
                            break;
                        case 16:
                        case 17:
                            break;
                        default:
                            i2 = 2;
                            break;
                    }
                } else {
                    i2 = 0;
                }
            }
            return i2;
        }
    }

    public interface TextDirectionAlgorithm {
        int checkRtl(int i, CharSequence charSequence);
    }

    public abstract class TextDirectionHeuristicImpl implements TextDirectionHeuristicCompat {
        public final TextDirectionAlgorithm mAlgorithm;

        public TextDirectionHeuristicImpl(TextDirectionAlgorithm textDirectionAlgorithm) {
            this.mAlgorithm = textDirectionAlgorithm;
        }

        public abstract boolean defaultIsRtl();

        public final boolean isRtl(int i, CharSequence charSequence) {
            if (charSequence == null || i < 0 || charSequence.length() - i < 0) {
                throw new IllegalArgumentException();
            }
            TextDirectionAlgorithm textDirectionAlgorithm = this.mAlgorithm;
            if (textDirectionAlgorithm == null) {
                return defaultIsRtl();
            }
            int iCheckRtl = textDirectionAlgorithm.checkRtl(i, charSequence);
            if (iCheckRtl == 0) {
                return true;
            }
            if (iCheckRtl != 1) {
                return defaultIsRtl();
            }
            return false;
        }
    }

    public class TextDirectionHeuristicInternal extends TextDirectionHeuristicImpl {
        public final boolean mDefaultIsRtl;

        public TextDirectionHeuristicInternal(TextDirectionAlgorithm textDirectionAlgorithm, boolean z) {
            super(textDirectionAlgorithm);
            this.mDefaultIsRtl = z;
        }

        @Override // androidx.core.text.TextDirectionHeuristicsCompat.TextDirectionHeuristicImpl
        public final boolean defaultIsRtl() {
            return this.mDefaultIsRtl;
        }
    }

    public class TextDirectionHeuristicLocale extends TextDirectionHeuristicImpl {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new TextDirectionHeuristicLocale();
        }

        public TextDirectionHeuristicLocale() {
            super(null);
        }

        @Override // androidx.core.text.TextDirectionHeuristicsCompat.TextDirectionHeuristicImpl
        public final boolean defaultIsRtl() {
            return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
        }
    }

    static {
        FirstStrong firstStrong = FirstStrong.INSTANCE;
        FIRSTSTRONG_LTR = new TextDirectionHeuristicInternal(firstStrong, false);
        FIRSTSTRONG_RTL = new TextDirectionHeuristicInternal(firstStrong, true);
        new TextDirectionHeuristicInternal(AnyStrong.INSTANCE_RTL, false);
        int i = TextDirectionHeuristicLocale.$r8$clinit;
    }

    private TextDirectionHeuristicsCompat() {
    }
}
