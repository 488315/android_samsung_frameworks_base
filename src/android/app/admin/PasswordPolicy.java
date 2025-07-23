package android.app.admin;

/* loaded from: classes.dex */
public class PasswordPolicy {
    public static final int DEF_MINIMUM_LENGTH = 0;
    public static final int DEF_MINIMUM_LETTERS = 1;
    public static final int DEF_MINIMUM_LOWER_CASE = 0;
    public static final int DEF_MINIMUM_NON_LETTER = 0;
    public static final int DEF_MINIMUM_NUMERIC = 1;
    public static final int DEF_MINIMUM_SYMBOLS = 1;
    public static final int DEF_MINIMUM_UPPER_CASE = 0;
    public int quality = 0;
    public int length = 0;
    public int letters = 1;
    public int upperCase = 0;
    public int lowerCase = 0;
    public int numeric = 1;
    public int symbols = 1;
    public int nonLetter = 0;

    public PasswordMetrics getMinMetrics() {
        int i = this.quality;
        if (i == 0) {
            return new PasswordMetrics(-1);
        }
        if (i == 32768 || i == 65536) {
            return new PasswordMetrics(1);
        }
        if (i == 131072 || i == 196608) {
            PasswordMetrics passwordMetrics = new PasswordMetrics(3);
            passwordMetrics.length = this.length;
            if (this.quality == 196608) {
                passwordMetrics.seqLength = 3;
            }
            return passwordMetrics;
        }
        PasswordMetrics passwordMetrics2 = new PasswordMetrics(4);
        passwordMetrics2.length = this.length;
        int i2 = this.quality;
        if (i2 == 262144) {
            passwordMetrics2.nonNumeric = 1;
            return passwordMetrics2;
        }
        if (i2 == 327680) {
            passwordMetrics2.numeric = 1;
            passwordMetrics2.nonNumeric = 1;
            return passwordMetrics2;
        }
        if (i2 == 393216) {
            passwordMetrics2.numeric = this.numeric;
            passwordMetrics2.letters = this.letters;
            passwordMetrics2.upperCase = this.upperCase;
            passwordMetrics2.lowerCase = this.lowerCase;
            passwordMetrics2.nonLetter = this.nonLetter;
            passwordMetrics2.symbols = this.symbols;
        }
        return passwordMetrics2;
    }
}
