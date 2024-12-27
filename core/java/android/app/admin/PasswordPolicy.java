package android.app.admin;

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
        if (this.quality == 0) {
            return new PasswordMetrics(-1);
        }
        if (this.quality == 32768 || this.quality == 65536) {
            return new PasswordMetrics(1);
        }
        if (this.quality == 131072 || this.quality == 196608) {
            PasswordMetrics result = new PasswordMetrics(3);
            result.length = this.length;
            if (this.quality == 196608) {
                result.seqLength = 3;
            }
            return result;
        }
        PasswordMetrics result2 = new PasswordMetrics(4);
        result2.length = this.length;
        if (this.quality == 262144) {
            result2.nonNumeric = 1;
        } else if (this.quality == 327680) {
            result2.numeric = 1;
            result2.nonNumeric = 1;
        } else if (this.quality == 393216) {
            result2.numeric = this.numeric;
            result2.letters = this.letters;
            result2.upperCase = this.upperCase;
            result2.lowerCase = this.lowerCase;
            result2.nonLetter = this.nonLetter;
            result2.symbols = this.symbols;
        }
        return result2;
    }
}
