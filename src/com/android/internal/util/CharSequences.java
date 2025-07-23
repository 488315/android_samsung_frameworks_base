package com.android.internal.util;

/* loaded from: classes4.dex */
public class CharSequences {
    public static CharSequence forAsciiBytes(final byte[] bArr) {
        return new CharSequence() { // from class: com.android.internal.util.CharSequences.1
            @Override // java.lang.CharSequence
            public char charAt(int i) {
                return (char) bArr[i];
            }

            @Override // java.lang.CharSequence
            public int length() {
                return bArr.length;
            }

            @Override // java.lang.CharSequence
            public CharSequence subSequence(int i, int i2) {
                return CharSequences.forAsciiBytes(bArr, i, i2);
            }

            @Override // java.lang.CharSequence
            public String toString() {
                return new String(bArr);
            }
        };
    }

    public static CharSequence forAsciiBytes(final byte[] bArr, final int i, final int i2) {
        validate(i, i2, bArr.length);
        return new CharSequence() { // from class: com.android.internal.util.CharSequences.2
            @Override // java.lang.CharSequence
            public char charAt(int i3) {
                return (char) bArr[i3 + i];
            }

            @Override // java.lang.CharSequence
            public int length() {
                return i2 - i;
            }

            @Override // java.lang.CharSequence
            public CharSequence subSequence(int i3, int i4) {
                int i5 = i;
                int i6 = i3 - i5;
                int i7 = i4 - i5;
                CharSequences.validate(i6, i7, length());
                return CharSequences.forAsciiBytes(bArr, i6, i7);
            }

            @Override // java.lang.CharSequence
            public String toString() {
                return new String(bArr, i, length());
            }
        };
    }

    static void validate(int i, int i2, int i3) {
        if (i < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (i2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (i2 > i3) {
            throw new IndexOutOfBoundsException();
        }
        if (i > i2) {
            throw new IndexOutOfBoundsException();
        }
    }

    public static boolean equals(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence.length() != charSequence2.length()) {
            return false;
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (charSequence.charAt(i) != charSequence2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static int compareToIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        int length = charSequence.length();
        int length2 = charSequence2.length();
        int i = length < length2 ? length : length2;
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            int i4 = i2 + 1;
            int i5 = i3 + 1;
            int lowerCase = Character.toLowerCase(charSequence.charAt(i2)) - Character.toLowerCase(charSequence2.charAt(i3));
            if (lowerCase != 0) {
                return lowerCase;
            }
            i2 = i4;
            i3 = i5;
        }
        return length - length2;
    }
}
