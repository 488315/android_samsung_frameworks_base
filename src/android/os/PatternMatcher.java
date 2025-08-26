package android.os;

import android.os.Parcelable;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class PatternMatcher implements Parcelable {
    private static final int MAX_PATTERN_STORAGE = 2048;
    private static final int NO_MATCH = -1;
    private static final int PARSED_MODIFIER_ONE_OR_MORE = -8;
    private static final int PARSED_MODIFIER_RANGE_START = -5;
    private static final int PARSED_MODIFIER_RANGE_STOP = -6;
    private static final int PARSED_MODIFIER_ZERO_OR_MORE = -7;
    private static final int PARSED_TOKEN_CHAR_ANY = -4;
    private static final int PARSED_TOKEN_CHAR_SET_INVERSE_START = -2;
    private static final int PARSED_TOKEN_CHAR_SET_START = -1;
    private static final int PARSED_TOKEN_CHAR_SET_STOP = -3;
    public static final int PATTERN_ADVANCED_GLOB = 3;
    public static final int PATTERN_LITERAL = 0;
    public static final int PATTERN_PREFIX = 1;
    public static final int PATTERN_SIMPLE_GLOB = 2;
    public static final int PATTERN_SUFFIX = 4;
    private static final String TAG = "PatternMatcher";
    private static final int TOKEN_TYPE_ANY = 1;
    private static final int TOKEN_TYPE_INVERSE_SET = 3;
    private static final int TOKEN_TYPE_LITERAL = 0;
    private static final int TOKEN_TYPE_SET = 2;
    private final int[] mParsedPattern;
    private final String mPattern;
    private final int mType;
    private static final int[] sParsedPatternScratch = new int[2048];
    public static final Parcelable.Creator<PatternMatcher> CREATOR = new Parcelable.Creator<PatternMatcher>() { // from class: android.os.PatternMatcher.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PatternMatcher createFromParcel(Parcel parcel) {
            return new PatternMatcher(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PatternMatcher[] newArray(int i) {
            return new PatternMatcher[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface PatternType {
    }

    private static boolean isParsedModifier(int i) {
        return i == -8 || i == -7 || i == -6 || i == -5;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PatternMatcher(String str, int i) {
        this.mPattern = str;
        this.mType = i;
        if (i == 3) {
            this.mParsedPattern = parseAndVerifyAdvancedPattern(str);
        } else {
            this.mParsedPattern = null;
        }
    }

    public final String getPath() {
        return this.mPattern;
    }

    public final int getType() {
        return this.mType;
    }

    public boolean match(String str) {
        return matchPattern(str, this.mPattern, this.mParsedPattern, this.mType);
    }

    public String toString() {
        String str;
        int i = this.mType;
        if (i == 0) {
            str = "LITERAL: ";
        } else if (i == 1) {
            str = "PREFIX: ";
        } else if (i == 2) {
            str = "GLOB: ";
        } else if (i == 3) {
            str = "ADVANCED: ";
        } else {
            str = i != 4 ? "? " : "SUFFIX: ";
        }
        return "PatternMatcher{" + str + this.mPattern + "}";
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long jStart = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mPattern);
        protoOutputStream.write(1159641169922L, this.mType);
        protoOutputStream.end(jStart);
    }

    public boolean check() {
        try {
            if (this.mType == 3) {
                return Arrays.equals(this.mParsedPattern, parseAndVerifyAdvancedPattern(this.mPattern));
            }
            return true;
        } catch (IllegalArgumentException e) {
            Log.w(TAG, "Failed to verify advanced pattern: " + e.getMessage());
            return false;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPattern);
        parcel.writeInt(this.mType);
        parcel.writeIntArray(this.mParsedPattern);
    }

    public PatternMatcher(Parcel parcel) {
        this.mPattern = parcel.readString();
        this.mType = parcel.readInt();
        this.mParsedPattern = parcel.createIntArray();
    }

    static boolean matchPattern(String str, String str2, int[] iArr, int i) {
        if (str == null) {
            return false;
        }
        if (i == 0) {
            return str2.equals(str);
        }
        if (i == 1) {
            return str.startsWith(str2);
        }
        if (i == 2) {
            return matchGlobPattern(str2, str);
        }
        if (i == 3) {
            return matchAdvancedPattern(iArr, str);
        }
        if (i == 4) {
            return str.endsWith(str2);
        }
        return false;
    }

    static boolean matchGlobPattern(String str, String str2) {
        int length = str.length();
        if (length <= 0) {
            return str2.length() <= 0;
        }
        int length2 = str2.length();
        char cCharAt = str.charAt(0);
        int i = 0;
        int i2 = 0;
        while (i < length && i2 < length2) {
            int i3 = i + 1;
            char cCharAt2 = i3 < length ? str.charAt(i3) : (char) 0;
            boolean z = cCharAt == '\\';
            if (z) {
                i3 = i + 2;
                char c = cCharAt2;
                cCharAt2 = i3 < length ? str.charAt(i3) : (char) 0;
                cCharAt = c;
            }
            if (cCharAt2 == '*') {
                if (z || cCharAt != '.') {
                    while (str2.charAt(i2) == cCharAt && (i2 = i2 + 1) < length2) {
                    }
                    int i4 = i3 + 1;
                    cCharAt = i4 < length ? str.charAt(i4) : (char) 0;
                    i = i4;
                } else {
                    if (i3 >= length - 1) {
                        return true;
                    }
                    int i5 = i3 + 1;
                    char cCharAt3 = str.charAt(i5);
                    if (cCharAt3 == '\\') {
                        i5 = i3 + 2;
                        cCharAt3 = i5 < length ? str.charAt(i5) : (char) 0;
                    }
                    while (str2.charAt(i2) != cCharAt3 && (i2 = i2 + 1) < length2) {
                    }
                    if (i2 == length2) {
                        return false;
                    }
                    int i6 = i5 + 1;
                    i2++;
                    i = i6;
                    cCharAt = i6 < length ? str.charAt(i6) : (char) 0;
                }
            } else {
                if (cCharAt != '.' && str2.charAt(i2) != cCharAt) {
                    return false;
                }
                i2++;
                i = i3;
                cCharAt = cCharAt2;
            }
        }
        if (i < length || i2 < length2) {
            return i == length + (-2) && str.charAt(i) == '.' && str.charAt(i + 1) == '*';
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:77:0x012c A[Catch: all -> 0x01c0, TryCatch #0 {, blocks: (B:4:0x0003, B:8:0x0013, B:18:0x002e, B:69:0x0101, B:100:0x01a3, B:70:0x010b, B:72:0x010f, B:74:0x0119, B:76:0x0121, B:77:0x012c, B:79:0x013a, B:81:0x0140, B:83:0x014c, B:90:0x0174, B:91:0x0181, B:92:0x0188, B:84:0x0152, B:88:0x0167, B:94:0x018a, B:95:0x0191, B:96:0x0192, B:97:0x0199, B:99:0x019c, B:22:0x0037, B:25:0x0041, B:26:0x004c, B:27:0x0053, B:28:0x0054, B:30:0x0058, B:31:0x005d, B:32:0x0064, B:35:0x0068, B:37:0x0072, B:39:0x0080, B:38:0x007a, B:41:0x0086, B:44:0x0096, B:46:0x00a2, B:47:0x00ac, B:48:0x00b3, B:50:0x00b6, B:54:0x00c4, B:56:0x00d0, B:57:0x00d6, B:58:0x00dd, B:61:0x00e2, B:63:0x00ee, B:64:0x00f4, B:65:0x00fb, B:101:0x01a6, B:102:0x01ad, B:104:0x01b0, B:107:0x01b8, B:108:0x01bf), top: B:112:0x0003, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static synchronized int[] parseAndVerifyAdvancedPattern(String str) {
        int i;
        int i2;
        boolean z;
        int i3;
        int i4;
        int i5;
        int length = str.length();
        int i6 = 0;
        boolean z2 = false;
        i = 0;
        boolean z3 = false;
        boolean z4 = false;
        while (i6 < length) {
            if (i > 2045) {
                throw new IllegalArgumentException("Pattern is too large!");
            }
            char cCharAt = str.charAt(i6);
            if (cCharAt == '*') {
                if (!z2) {
                    if (i != 0) {
                        int[] iArr = sParsedPatternScratch;
                        if (!isParsedModifier(iArr[i - 1])) {
                            i2 = i + 1;
                            iArr[i] = -7;
                            z = false;
                            i = i2;
                        }
                    }
                    throw new IllegalArgumentException("Modifier must follow a token.");
                }
                z = false;
            } else if (cCharAt != '+') {
                if (cCharAt != '.') {
                    if (cCharAt != '{') {
                        if (cCharAt != '}') {
                            switch (cCharAt) {
                                case '[':
                                    if (z2) {
                                        z = true;
                                        break;
                                    } else {
                                        int i7 = i6 + 1;
                                        if (str.charAt(i7) == '^') {
                                            i5 = i + 1;
                                            sParsedPatternScratch[i] = -2;
                                            i6 = i7;
                                        } else {
                                            i5 = i + 1;
                                            sParsedPatternScratch[i] = -1;
                                        }
                                        i = i5;
                                        i6++;
                                        z2 = true;
                                    }
                                case '\\':
                                    i6++;
                                    if (i6 >= length) {
                                        throw new IllegalArgumentException("Escape found at end of pattern!");
                                    }
                                    cCharAt = str.charAt(i6);
                                    z = true;
                                    break;
                                case ']':
                                    if (!z2) {
                                        z = true;
                                        break;
                                    } else {
                                        int[] iArr2 = sParsedPatternScratch;
                                        int i8 = iArr2[i - 1];
                                        if (i8 == -1 || i8 == -2) {
                                            throw new IllegalArgumentException("You must define characters in a set.");
                                        }
                                        iArr2[i] = -3;
                                        z2 = false;
                                        z = false;
                                        i++;
                                        z4 = false;
                                        break;
                                    }
                                    break;
                                default:
                                    z = true;
                                    break;
                            }
                        } else if (z3) {
                            sParsedPatternScratch[i] = -6;
                            z3 = false;
                            i++;
                            z = false;
                        }
                    } else if (!z2) {
                        if (i != 0) {
                            int[] iArr3 = sParsedPatternScratch;
                            if (!isParsedModifier(iArr3[i - 1])) {
                                iArr3[i] = -5;
                                i6++;
                                i++;
                                z3 = true;
                            }
                        }
                        throw new IllegalArgumentException("Modifier must follow a token.");
                    }
                } else if (!z2) {
                    i2 = i + 1;
                    sParsedPatternScratch[i] = -4;
                    z = false;
                    i = i2;
                }
                z = false;
            } else {
                if (!z2) {
                    if (i != 0) {
                        int[] iArr4 = sParsedPatternScratch;
                        if (!isParsedModifier(iArr4[i - 1])) {
                            i2 = i + 1;
                            iArr4[i] = -8;
                            z = false;
                            i = i2;
                        }
                    }
                    throw new IllegalArgumentException("Modifier must follow a token.");
                }
                z = false;
            }
            if (z2) {
                if (z4) {
                    sParsedPatternScratch[i] = cCharAt;
                    z4 = false;
                    i++;
                } else {
                    int i9 = i6 + 2;
                    if (i9 < length) {
                        int i10 = i6 + 1;
                        if (str.charAt(i10) == '-' && str.charAt(i9) != ']') {
                            sParsedPatternScratch[i] = cCharAt;
                            i++;
                            i6 = i10;
                            z4 = true;
                        } else {
                            int[] iArr5 = sParsedPatternScratch;
                            int i11 = i + 1;
                            iArr5[i] = cCharAt;
                            i += 2;
                            iArr5[i11] = cCharAt;
                        }
                    }
                }
            } else if (z3) {
                int iIndexOf = str.indexOf(125, i6);
                if (iIndexOf < 0) {
                    throw new IllegalArgumentException("Range not ended with '}'");
                }
                String strSubstring = str.substring(i6, iIndexOf);
                int iIndexOf2 = strSubstring.indexOf(44);
                if (iIndexOf2 < 0) {
                    try {
                        i3 = Integer.parseInt(strSubstring);
                        i4 = i3;
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Range number format incorrect", e);
                    }
                } else {
                    int i12 = Integer.parseInt(strSubstring.substring(0, iIndexOf2));
                    i4 = iIndexOf2 == strSubstring.length() - 1 ? Integer.MAX_VALUE : Integer.parseInt(strSubstring.substring(iIndexOf2 + 1));
                    i3 = i12;
                }
                if (i3 > i4) {
                    throw new IllegalArgumentException("Range quantifier minimum is greater than maximum");
                }
                int[] iArr6 = sParsedPatternScratch;
                int i13 = i + 1;
                iArr6[i] = i3;
                i += 2;
                iArr6[i13] = i4;
                i6 = iIndexOf;
            } else if (z) {
                sParsedPatternScratch[i] = cCharAt;
                i++;
            }
            i6++;
        }
        if (z2) {
            throw new IllegalArgumentException("Set was not terminated!");
        }
        return Arrays.copyOf(sParsedPatternScratch, i);
    }

    static boolean matchAdvancedPattern(int[] iArr, String str) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int[] iArr2;
        int iMatchChars;
        int length = iArr.length;
        int length2 = str.length();
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        while (true) {
            int i12 = 1;
            if (i8 >= length) {
                return i8 >= length && i9 >= length2;
            }
            int i13 = iArr[i8];
            if (i13 == -4) {
                i = i10;
                i2 = i11;
                i3 = i8 + 1;
                i4 = 1;
            } else if (i13 == -2 || i13 == -1) {
                int i14 = i13 == -1 ? 2 : 3;
                int i15 = i8 + 1;
                while (true) {
                    int i16 = i8 + 1;
                    if (i16 >= length || iArr[i16] == -3) {
                        break;
                    }
                    i8 = i16;
                }
                int i17 = i8 + 2;
                i2 = i8;
                i4 = i14;
                i = i15;
                i3 = i17;
            } else {
                i3 = i8 + 1;
                i = i8;
                i2 = i11;
                i4 = 0;
            }
            if (i3 < length) {
                int i18 = iArr[i3];
                if (i18 == -8) {
                    i5 = i3 + 1;
                    i6 = 1;
                } else if (i18 != -7) {
                    if (i18 == -5) {
                        int i19 = iArr[i3 + 1];
                        int i20 = iArr[i3 + 2];
                        i5 = i3 + 4;
                        i12 = i20;
                        i6 = i19;
                        i7 = i5;
                    }
                    i7 = i3;
                    i6 = 1;
                } else {
                    i5 = i3 + 1;
                    i6 = 0;
                }
                i12 = Integer.MAX_VALUE;
                i7 = i5;
            } else {
                i7 = i3;
                i6 = 1;
            }
            if (i6 > i12 || (iMatchChars = matchChars(str, i9, length2, i4, i6, i12, (iArr2 = iArr), i, i2)) == -1) {
                return false;
            }
            i9 += iMatchChars;
            iArr = iArr2;
            i10 = i;
            i11 = i2;
            i8 = i7;
        }
    }

    private static int matchChars(String str, int i, int i2, int i3, int i4, int i5, int[] iArr, int i6, int i7) {
        int i8 = 0;
        while (i8 < i5 && matchChar(str, i + i8, i2, i3, iArr, i6, i7)) {
            i8++;
        }
        if (i8 < i4) {
            return -1;
        }
        return i8;
    }

    private static boolean matchChar(String str, int i, int i2, int i3, int[] iArr, int i4, int i5) {
        if (i >= i2) {
            return false;
        }
        if (i3 == 0) {
            return str.charAt(i) == iArr[i4];
        }
        if (i3 == 1) {
            return true;
        }
        if (i3 == 2) {
            while (i4 < i5) {
                char cCharAt = str.charAt(i);
                if (cCharAt >= iArr[i4] && cCharAt <= iArr[i4 + 1]) {
                    return true;
                }
                i4 += 2;
            }
            return false;
        }
        if (i3 != 3) {
            return false;
        }
        while (i4 < i5) {
            char cCharAt2 = str.charAt(i);
            if (cCharAt2 >= iArr[i4] && cCharAt2 <= iArr[i4 + 1]) {
                return false;
            }
            i4 += 2;
        }
        return true;
    }
}
