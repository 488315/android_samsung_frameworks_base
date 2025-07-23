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
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mPattern);
        protoOutputStream.write(1159641169922L, this.mType);
        protoOutputStream.end(start);
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
        char charAt = str.charAt(0);
        int i = 0;
        int i2 = 0;
        while (i < length && i2 < length2) {
            int i3 = i + 1;
            char charAt2 = i3 < length ? str.charAt(i3) : (char) 0;
            boolean z = charAt == '\\';
            if (z) {
                i3 = i + 2;
                char c = charAt2;
                charAt2 = i3 < length ? str.charAt(i3) : (char) 0;
                charAt = c;
            }
            if (charAt2 == '*') {
                if (z || charAt != '.') {
                    while (str2.charAt(i2) == charAt && (i2 = i2 + 1) < length2) {
                    }
                    int i4 = i3 + 1;
                    charAt = i4 < length ? str.charAt(i4) : (char) 0;
                    i = i4;
                } else {
                    if (i3 >= length - 1) {
                        return true;
                    }
                    int i5 = i3 + 1;
                    char charAt3 = str.charAt(i5);
                    if (charAt3 == '\\') {
                        i5 = i3 + 2;
                        charAt3 = i5 < length ? str.charAt(i5) : (char) 0;
                    }
                    while (str2.charAt(i2) != charAt3 && (i2 = i2 + 1) < length2) {
                    }
                    if (i2 == length2) {
                        return false;
                    }
                    int i6 = i5 + 1;
                    i2++;
                    i = i6;
                    charAt = i6 < length ? str.charAt(i6) : (char) 0;
                }
            } else {
                if (charAt != '.' && str2.charAt(i2) != charAt) {
                    return false;
                }
                i2++;
                i = i3;
                charAt = charAt2;
            }
        }
        if (i < length || i2 < length2) {
            return i == length + (-2) && str.charAt(i) == '.' && str.charAt(i + 1) == '*';
        }
        return true;
    }

    static synchronized int[] parseAndVerifyAdvancedPattern(String str) {
        int[] copyOf;
        int i;
        boolean z;
        int parseInt;
        int i2;
        int i3;
        synchronized (PatternMatcher.class) {
            int length = str.length();
            int i4 = 0;
            boolean z2 = false;
            int i5 = 0;
            boolean z3 = false;
            boolean z4 = false;
            while (i4 < length) {
                if (i5 > 2045) {
                    throw new IllegalArgumentException("Pattern is too large!");
                }
                char charAt = str.charAt(i4);
                if (charAt == '*') {
                    if (!z2) {
                        if (i5 != 0) {
                            int[] iArr = sParsedPatternScratch;
                            if (!isParsedModifier(iArr[i5 - 1])) {
                                i = i5 + 1;
                                iArr[i5] = -7;
                                z = false;
                                i5 = i;
                            }
                        }
                        throw new IllegalArgumentException("Modifier must follow a token.");
                    }
                    z = false;
                } else if (charAt != '+') {
                    if (charAt != '.') {
                        if (charAt != '{') {
                            if (charAt != '}') {
                                switch (charAt) {
                                    case '[':
                                        if (z2) {
                                            z = true;
                                            break;
                                        } else {
                                            int i6 = i4 + 1;
                                            if (str.charAt(i6) == '^') {
                                                i3 = i5 + 1;
                                                sParsedPatternScratch[i5] = -2;
                                                i4 = i6;
                                            } else {
                                                i3 = i5 + 1;
                                                sParsedPatternScratch[i5] = -1;
                                            }
                                            i5 = i3;
                                            i4++;
                                            z2 = true;
                                        }
                                    case '\\':
                                        i4++;
                                        if (i4 >= length) {
                                            throw new IllegalArgumentException("Escape found at end of pattern!");
                                        }
                                        charAt = str.charAt(i4);
                                        z = true;
                                        break;
                                    case ']':
                                        if (!z2) {
                                            z = true;
                                            break;
                                        } else {
                                            int[] iArr2 = sParsedPatternScratch;
                                            int i7 = iArr2[i5 - 1];
                                            if (i7 == -1 || i7 == -2) {
                                                throw new IllegalArgumentException("You must define characters in a set.");
                                            }
                                            iArr2[i5] = -3;
                                            z2 = false;
                                            z = false;
                                            i5++;
                                            z4 = false;
                                            break;
                                        }
                                    default:
                                        z = true;
                                        break;
                                }
                            } else if (z3) {
                                sParsedPatternScratch[i5] = -6;
                                z3 = false;
                                i5++;
                                z = false;
                            }
                        } else if (!z2) {
                            if (i5 != 0) {
                                int[] iArr3 = sParsedPatternScratch;
                                if (!isParsedModifier(iArr3[i5 - 1])) {
                                    iArr3[i5] = -5;
                                    i4++;
                                    i5++;
                                    z3 = true;
                                }
                            }
                            throw new IllegalArgumentException("Modifier must follow a token.");
                        }
                    } else if (!z2) {
                        i = i5 + 1;
                        sParsedPatternScratch[i5] = -4;
                        z = false;
                        i5 = i;
                    }
                    z = false;
                } else {
                    if (!z2) {
                        if (i5 != 0) {
                            int[] iArr4 = sParsedPatternScratch;
                            if (!isParsedModifier(iArr4[i5 - 1])) {
                                i = i5 + 1;
                                iArr4[i5] = -8;
                                z = false;
                                i5 = i;
                            }
                        }
                        throw new IllegalArgumentException("Modifier must follow a token.");
                    }
                    z = false;
                }
                if (z2) {
                    if (z4) {
                        sParsedPatternScratch[i5] = charAt;
                        z4 = false;
                        i5++;
                    } else {
                        int i8 = i4 + 2;
                        if (i8 < length) {
                            int i9 = i4 + 1;
                            if (str.charAt(i9) == '-' && str.charAt(i8) != ']') {
                                sParsedPatternScratch[i5] = charAt;
                                i5++;
                                i4 = i9;
                                z4 = true;
                            }
                        }
                        int[] iArr5 = sParsedPatternScratch;
                        int i10 = i5 + 1;
                        iArr5[i5] = charAt;
                        i5 += 2;
                        iArr5[i10] = charAt;
                    }
                } else if (z3) {
                    int indexOf = str.indexOf(125, i4);
                    if (indexOf < 0) {
                        throw new IllegalArgumentException("Range not ended with '}'");
                    }
                    String substring = str.substring(i4, indexOf);
                    int indexOf2 = substring.indexOf(44);
                    if (indexOf2 < 0) {
                        try {
                            parseInt = Integer.parseInt(substring);
                            i2 = parseInt;
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Range number format incorrect", e);
                        }
                    } else {
                        int parseInt2 = Integer.parseInt(substring.substring(0, indexOf2));
                        i2 = indexOf2 == substring.length() - 1 ? Integer.MAX_VALUE : Integer.parseInt(substring.substring(indexOf2 + 1));
                        parseInt = parseInt2;
                    }
                    if (parseInt > i2) {
                        throw new IllegalArgumentException("Range quantifier minimum is greater than maximum");
                    }
                    int[] iArr6 = sParsedPatternScratch;
                    int i11 = i5 + 1;
                    iArr6[i5] = parseInt;
                    i5 += 2;
                    iArr6[i11] = i2;
                    i4 = indexOf;
                } else if (z) {
                    sParsedPatternScratch[i5] = charAt;
                    i5++;
                }
                i4++;
            }
            if (z2) {
                throw new IllegalArgumentException("Set was not terminated!");
            }
            copyOf = Arrays.copyOf(sParsedPatternScratch, i5);
        }
        return copyOf;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x006a A[ADDED_TO_REGION, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static boolean matchAdvancedPattern(int[] r13, java.lang.String r14) {
        /*
            int r0 = r13.length
            int r3 = r14.length()
            r10 = 0
            r1 = r10
            r2 = r1
            r4 = r2
            r5 = r4
        La:
            r6 = 1
            if (r1 >= r0) goto L7b
            r7 = r13[r1]
            r8 = -4
            r11 = -1
            if (r7 == r8) goto L37
            r4 = -2
            if (r7 == r4) goto L1e
            if (r7 == r11) goto L1e
            int r4 = r1 + 1
            r8 = r1
            r9 = r5
            r1 = r10
            goto L3d
        L1e:
            if (r7 != r11) goto L22
            r4 = 2
            goto L23
        L22:
            r4 = 3
        L23:
            int r5 = r1 + 1
        L25:
            int r7 = r1 + 1
            if (r7 >= r0) goto L30
            r8 = r13[r7]
            r9 = -3
            if (r8 == r9) goto L30
            r1 = r7
            goto L25
        L30:
            int r7 = r1 + 2
            r9 = r1
            r1 = r4
            r8 = r5
            r4 = r7
            goto L3d
        L37:
            int r1 = r1 + 1
            r8 = r4
            r9 = r5
            r4 = r1
            r1 = r6
        L3d:
            if (r4 < r0) goto L42
        L3f:
            r12 = r4
            r5 = r6
            goto L68
        L42:
            r5 = r13[r4]
            r7 = -8
            r12 = 2147483647(0x7fffffff, float:NaN)
            if (r5 == r7) goto L63
            r7 = -7
            if (r5 == r7) goto L5f
            r7 = -5
            if (r5 == r7) goto L51
            goto L3f
        L51:
            int r5 = r4 + 1
            r6 = r13[r5]
            int r5 = r4 + 2
            r5 = r13[r5]
            int r4 = r4 + 4
            r12 = r6
            r6 = r5
            r5 = r12
            goto L67
        L5f:
            int r4 = r4 + 1
            r5 = r10
            goto L66
        L63:
            int r4 = r4 + 1
            r5 = r6
        L66:
            r6 = r12
        L67:
            r12 = r4
        L68:
            if (r5 <= r6) goto L6b
            return r10
        L6b:
            r7 = r13
            r4 = r1
            r1 = r14
            int r13 = matchChars(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            if (r13 != r11) goto L75
            return r10
        L75:
            int r2 = r2 + r13
            r13 = r7
            r4 = r8
            r5 = r9
            r1 = r12
            goto La
        L7b:
            if (r1 < r0) goto L80
            if (r2 < r3) goto L80
            return r6
        L80:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.PatternMatcher.matchAdvancedPattern(int[], java.lang.String):boolean");
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
                char charAt = str.charAt(i);
                if (charAt >= iArr[i4] && charAt <= iArr[i4 + 1]) {
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
            char charAt2 = str.charAt(i);
            if (charAt2 >= iArr[i4] && charAt2 <= iArr[i4 + 1]) {
                return false;
            }
            i4 += 2;
        }
        return true;
    }
}
