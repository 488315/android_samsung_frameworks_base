package org.apache.http.conn.ssl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.security.auth.x500.X500Principal;

@Deprecated
/* loaded from: classes6.dex */
final class AndroidDistinguishedNameParser {
    private int beg;
    private char[] chars;
    private int cur;

    /* renamed from: dn */
    private final String f3118dn;
    private int end;
    private final int length;
    private int pos;

    public AndroidDistinguishedNameParser(X500Principal principal) {
        String name = principal.getName("RFC2253");
        this.f3118dn = name;
        this.length = name.length();
    }

    private String nextAT() {
        int i;
        int i2;
        int i3;
        int i4;
        char c;
        int i5;
        int i6;
        char c2;
        char c3;
        while (true) {
            i = this.pos;
            i2 = this.length;
            if (i >= i2 || this.chars[i] != ' ') {
                break;
            }
            this.pos = i + 1;
        }
        if (i == i2) {
            return null;
        }
        this.beg = i;
        this.pos = i + 1;
        while (true) {
            i3 = this.pos;
            i4 = this.length;
            if (i3 >= i4 || (c3 = this.chars[i3]) == '=' || c3 == ' ') {
                break;
            }
            this.pos = i3 + 1;
        }
        if (i3 >= i4) {
            throw new IllegalStateException("Unexpected end of DN: " + this.f3118dn);
        }
        this.end = i3;
        if (this.chars[i3] == ' ') {
            while (true) {
                i5 = this.pos;
                i6 = this.length;
                if (i5 >= i6 || (c2 = this.chars[i5]) == '=' || c2 != ' ') {
                    break;
                }
                this.pos = i5 + 1;
            }
            if (this.chars[i5] != '=' || i5 == i6) {
                throw new IllegalStateException("Unexpected end of DN: " + this.f3118dn);
            }
        }
        this.pos++;
        while (true) {
            int i7 = this.pos;
            if (i7 >= this.length || this.chars[i7] != ' ') {
                break;
            }
            this.pos = i7 + 1;
        }
        int i8 = this.end;
        int i9 = this.beg;
        if (i8 - i9 > 4) {
            char[] cArr = this.chars;
            if (cArr[i9 + 3] == '.' && (((c = cArr[i9]) == 'O' || c == 'o') && ((cArr[i9 + 1] == 'I' || cArr[i9 + 1] == 'i') && (cArr[i9 + 2] == 'D' || cArr[i9 + 2] == 'd')))) {
                this.beg = i9 + 4;
            }
        }
        char[] cArr2 = this.chars;
        int i10 = this.beg;
        return new String(cArr2, i10, i8 - i10);
    }

    private String quotedAV() {
        int i = this.pos + 1;
        this.pos = i;
        this.beg = i;
        this.end = i;
        while (true) {
            int i2 = this.pos;
            if (i2 == this.length) {
                throw new IllegalStateException("Unexpected end of DN: " + this.f3118dn);
            }
            char[] cArr = this.chars;
            char c = cArr[i2];
            if (c == '\"') {
                this.pos = i2 + 1;
                while (true) {
                    int i3 = this.pos;
                    if (i3 >= this.length || this.chars[i3] != ' ') {
                        break;
                    }
                    this.pos = i3 + 1;
                }
                char[] cArr2 = this.chars;
                int i4 = this.beg;
                return new String(cArr2, i4, this.end - i4);
            }
            if (c == '\\') {
                cArr[this.end] = getEscaped();
            } else {
                cArr[this.end] = c;
            }
            this.pos++;
            this.end++;
        }
    }

    private String hexAV() {
        int i;
        char[] cArr;
        char c;
        int i2 = this.pos;
        if (i2 + 4 >= this.length) {
            throw new IllegalStateException("Unexpected end of DN: " + this.f3118dn);
        }
        this.beg = i2;
        this.pos = i2 + 1;
        while (true) {
            i = this.pos;
            if (i == this.length || (c = (cArr = this.chars)[i]) == '+' || c == ',' || c == ';') {
                break;
            }
            if (c == ' ') {
                this.end = i;
                this.pos = i + 1;
                while (true) {
                    int i3 = this.pos;
                    if (i3 >= this.length || this.chars[i3] != ' ') {
                        break;
                    }
                    this.pos = i3 + 1;
                }
            } else {
                if (c >= 'A' && c <= 'F') {
                    cArr[i] = (char) (c + ' ');
                }
                this.pos = i + 1;
            }
        }
        this.end = i;
        int i4 = this.end;
        int i5 = this.beg;
        int hexLen = i4 - i5;
        if (hexLen < 5 || (hexLen & 1) == 0) {
            throw new IllegalStateException("Unexpected end of DN: " + this.f3118dn);
        }
        byte[] encoded = new byte[hexLen / 2];
        int p = i5 + 1;
        for (int i6 = 0; i6 < encoded.length; i6++) {
            encoded[i6] = (byte) getByte(p);
            p += 2;
        }
        return new String(this.chars, this.beg, hexLen);
    }

    private String escapedAV() {
        int i;
        int i2;
        char c;
        int i3 = this.pos;
        this.beg = i3;
        this.end = i3;
        while (true) {
            int i4 = this.pos;
            if (i4 >= this.length) {
                char[] cArr = this.chars;
                int i5 = this.beg;
                return new String(cArr, i5, this.end - i5);
            }
            char[] cArr2 = this.chars;
            char c2 = cArr2[i4];
            switch (c2) {
                case ' ':
                    int i6 = this.end;
                    this.cur = i6;
                    this.pos = i4 + 1;
                    this.end = i6 + 1;
                    cArr2[i6] = ' ';
                    while (true) {
                        i = this.pos;
                        i2 = this.length;
                        if (i < i2) {
                            char[] cArr3 = this.chars;
                            if (cArr3[i] == ' ') {
                                int i7 = this.end;
                                this.end = i7 + 1;
                                cArr3[i7] = ' ';
                                this.pos = i + 1;
                            }
                        }
                    }
                    if (i != i2 && (c = this.chars[i]) != ',' && c != '+' && c != ';') {
                        break;
                    }
                    break;
                case '+':
                case ',':
                case ';':
                    int i8 = this.beg;
                    return new String(cArr2, i8, this.end - i8);
                case '\\':
                    int i9 = this.end;
                    this.end = i9 + 1;
                    cArr2[i9] = getEscaped();
                    this.pos++;
                    break;
                default:
                    int i10 = this.end;
                    this.end = i10 + 1;
                    cArr2[i10] = c2;
                    this.pos = i4 + 1;
                    break;
            }
        }
        char[] cArr4 = this.chars;
        int i11 = this.beg;
        return new String(cArr4, i11, this.cur - i11);
    }

    private char getEscaped() {
        int i = this.pos + 1;
        this.pos = i;
        if (i == this.length) {
            throw new IllegalStateException("Unexpected end of DN: " + this.f3118dn);
        }
        char c = this.chars[i];
        switch (c) {
            case ' ':
            case '\"':
            case '#':
            case '%':
            case '*':
            case '+':
            case ',':
            case ';':
            case '<':
            case '=':
            case '>':
            case '\\':
            case '_':
                return c;
            default:
                return getUTF8();
        }
    }

    private char getUTF8() {
        int count;
        int res;
        int res2 = getByte(this.pos);
        this.pos++;
        if (res2 < 128) {
            return (char) res2;
        }
        if (res2 < 192 || res2 > 247) {
            return '?';
        }
        if (res2 <= 223) {
            count = 1;
            res = res2 & 31;
        } else if (res2 <= 239) {
            count = 2;
            res = res2 & 15;
        } else {
            count = 3;
            res = res2 & 7;
        }
        for (int i = 0; i < count; i++) {
            int i2 = this.pos + 1;
            this.pos = i2;
            if (i2 == this.length || this.chars[i2] != '\\') {
                return '?';
            }
            int i3 = i2 + 1;
            this.pos = i3;
            int b = getByte(i3);
            this.pos++;
            if ((b & 192) != 128) {
                return '?';
            }
            res = (res << 6) + (b & 63);
        }
        return (char) res;
    }

    private int getByte(int position) {
        int b1;
        int b2;
        if (position + 1 >= this.length) {
            throw new IllegalStateException("Malformed DN: " + this.f3118dn);
        }
        char[] cArr = this.chars;
        char c = cArr[position];
        if (c >= '0' && c <= '9') {
            b1 = c - '0';
        } else if (c >= 'a' && c <= 'f') {
            b1 = c - 'W';
        } else if (c >= 'A' && c <= 'F') {
            b1 = c - '7';
        } else {
            throw new IllegalStateException("Malformed DN: " + this.f3118dn);
        }
        char c2 = cArr[position + 1];
        if (c2 >= '0' && c2 <= '9') {
            b2 = c2 - '0';
        } else if (c2 >= 'a' && c2 <= 'f') {
            b2 = c2 - 'W';
        } else if (c2 >= 'A' && c2 <= 'F') {
            b2 = c2 - '7';
        } else {
            throw new IllegalStateException("Malformed DN: " + this.f3118dn);
        }
        return (b1 << 4) + b2;
    }

    public String findMostSpecific(String attributeType) {
        this.pos = 0;
        this.beg = 0;
        this.end = 0;
        this.cur = 0;
        this.chars = this.f3118dn.toCharArray();
        String attType = nextAT();
        if (attType == null) {
            return null;
        }
        do {
            String attValue = "";
            int i = this.pos;
            if (i == this.length) {
                return null;
            }
            switch (this.chars[i]) {
                case '\"':
                    attValue = quotedAV();
                    break;
                case '#':
                    attValue = hexAV();
                    break;
                case '+':
                case ',':
                case ';':
                    break;
                default:
                    attValue = escapedAV();
                    break;
            }
            if (attributeType.equalsIgnoreCase(attType)) {
                return attValue;
            }
            int i2 = this.pos;
            if (i2 >= this.length) {
                return null;
            }
            char c = this.chars[i2];
            if (c != ',' && c != ';' && c != '+') {
                throw new IllegalStateException("Malformed DN: " + this.f3118dn);
            }
            this.pos = i2 + 1;
            attType = nextAT();
        } while (attType != null);
        throw new IllegalStateException("Malformed DN: " + this.f3118dn);
    }

    public List<String> getAllMostSpecificFirst(String attributeType) {
        this.pos = 0;
        this.beg = 0;
        this.end = 0;
        this.cur = 0;
        this.chars = this.f3118dn.toCharArray();
        List<String> result = Collections.emptyList();
        String attType = nextAT();
        if (attType == null) {
            return result;
        }
        do {
            int i = this.pos;
            if (i < this.length) {
                String attValue = "";
                switch (this.chars[i]) {
                    case '\"':
                        attValue = quotedAV();
                        break;
                    case '#':
                        attValue = hexAV();
                        break;
                    case '+':
                    case ',':
                    case ';':
                        break;
                    default:
                        attValue = escapedAV();
                        break;
                }
                if (attributeType.equalsIgnoreCase(attType)) {
                    if (result.isEmpty()) {
                        result = new ArrayList();
                    }
                    result.add(attValue);
                }
                int i2 = this.pos;
                if (i2 < this.length) {
                    char c = this.chars[i2];
                    if (c != ',' && c != ';' && c != '+') {
                        throw new IllegalStateException("Malformed DN: " + this.f3118dn);
                    }
                    this.pos = i2 + 1;
                    attType = nextAT();
                }
            }
            return result;
        } while (attType != null);
        throw new IllegalStateException("Malformed DN: " + this.f3118dn);
    }
}
