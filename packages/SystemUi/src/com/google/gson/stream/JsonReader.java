package com.google.gson.stream;

import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.google.gson.internal.JsonReaderInternalAccess;
import java.io.Closeable;
import java.io.EOFException;
import java.io.Reader;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class JsonReader implements Closeable {

    /* renamed from: in */
    public final Reader f466in;
    public int[] pathIndices;
    public String[] pathNames;
    public long peekedLong;
    public int peekedNumberLength;
    public String peekedString;
    public int[] stack;
    public boolean lenient = false;
    public final char[] buffer = new char[1024];
    public int pos = 0;
    public int limit = 0;
    public int lineNumber = 0;
    public int lineStart = 0;
    public int peeked = 0;
    public int stackSize = 0 + 1;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.google.gson.stream.JsonReader$1] */
    static {
        JsonReaderInternalAccess.INSTANCE = new JsonReaderInternalAccess() { // from class: com.google.gson.stream.JsonReader.1
        };
    }

    public JsonReader(Reader reader) {
        int[] iArr = new int[32];
        this.stack = iArr;
        iArr[0] = 6;
        this.pathNames = new String[32];
        this.pathIndices = new int[32];
        if (reader == null) {
            throw new NullPointerException("in == null");
        }
        this.f466in = reader;
    }

    public void beginArray() {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 3) {
            push(1);
            this.pathIndices[this.stackSize - 1] = 0;
            this.peeked = 0;
        } else {
            throw new IllegalStateException("Expected BEGIN_ARRAY but was " + peek() + locationString());
        }
    }

    public void beginObject() {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 1) {
            push(3);
            this.peeked = 0;
        } else {
            throw new IllegalStateException("Expected BEGIN_OBJECT but was " + peek() + locationString());
        }
    }

    public final void checkLenient() {
        if (this.lenient) {
            return;
        }
        syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        throw null;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.peeked = 0;
        this.stack[0] = 8;
        this.stackSize = 1;
        this.f466in.close();
    }

    /* JADX WARN: Code restructure failed: missing block: B:116:0x0223, code lost:
    
        r16 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x0229, code lost:
    
        if (isLiteral(r1) != false) goto L207;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x022b, code lost:
    
        r1 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x022c, code lost:
    
        if (r6 != 2) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x022e, code lost:
    
        if (r13 == false) goto L183;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0234, code lost:
    
        if (r14 != Long.MIN_VALUE) goto L176;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0236, code lost:
    
        if (r12 == 0) goto L183;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x023a, code lost:
    
        if (r14 != 0) goto L179;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x023c, code lost:
    
        if (r12 != 0) goto L183;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x023e, code lost:
    
        if (r12 == 0) goto L181;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0241, code lost:
    
        r14 = -r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0242, code lost:
    
        r19.peekedLong = r14;
        r19.pos += r16;
        r6 = 15;
        r19.peeked = 15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x024f, code lost:
    
        r1 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0250, code lost:
    
        if (r6 == r1) goto L189;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0253, code lost:
    
        if (r6 == 4) goto L189;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0256, code lost:
    
        if (r6 != 7) goto L207;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0258, code lost:
    
        r19.peekedNumberLength = r16;
        r6 = 16;
        r19.peeked = 16;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:186:0x02d9  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0182 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x028c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x028d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x00e8  */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v2, types: [boolean, int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int doPeek() {
        ?? r1;
        int i;
        int nextNonWhitespace;
        int i2;
        String str;
        String str2;
        int i3;
        char c;
        int i4;
        int i5;
        int i6;
        int i7;
        int[] iArr = this.stack;
        int i8 = this.stackSize;
        int i9 = iArr[i8 - 1];
        int i10 = 1;
        if (i9 == 1) {
            iArr[i8 - 1] = 2;
        } else if (i9 == 2) {
            int nextNonWhitespace2 = nextNonWhitespace(true);
            if (nextNonWhitespace2 != 44) {
                if (nextNonWhitespace2 != 59) {
                    if (nextNonWhitespace2 == 93) {
                        this.peeked = 4;
                        return 4;
                    }
                    syntaxError("Unterminated array");
                    throw null;
                }
                checkLenient();
            }
        } else {
            if (i9 == 3 || i9 == 5) {
                iArr[i8 - 1] = 4;
                if (i9 == 5) {
                    int nextNonWhitespace3 = nextNonWhitespace(true);
                    if (nextNonWhitespace3 != 44) {
                        if (nextNonWhitespace3 != 59) {
                            if (nextNonWhitespace3 == 125) {
                                this.peeked = 2;
                                return 2;
                            }
                            syntaxError("Unterminated object");
                            throw null;
                        }
                        checkLenient();
                    }
                    r1 = 1;
                } else {
                    r1 = 1;
                }
                int nextNonWhitespace4 = nextNonWhitespace(r1);
                if (nextNonWhitespace4 == 34) {
                    this.peeked = 13;
                    return 13;
                }
                if (nextNonWhitespace4 == 39) {
                    checkLenient();
                    this.peeked = 12;
                    return 12;
                }
                if (nextNonWhitespace4 == 125) {
                    if (i9 != 5) {
                        this.peeked = 2;
                        return 2;
                    }
                    syntaxError("Expected name");
                    throw null;
                }
                checkLenient();
                this.pos -= r1;
                if (isLiteral((char) nextNonWhitespace4)) {
                    this.peeked = 14;
                    return 14;
                }
                syntaxError("Expected name");
                throw null;
            }
            if (i9 == 4) {
                iArr[i8 - 1] = 5;
                int nextNonWhitespace5 = nextNonWhitespace(true);
                if (nextNonWhitespace5 != 58) {
                    if (nextNonWhitespace5 != 61) {
                        syntaxError("Expected ':'");
                        throw null;
                    }
                    checkLenient();
                    if (this.pos < this.limit || fillBuffer(1)) {
                        char[] cArr = this.buffer;
                        int i11 = this.pos;
                        if (cArr[i11] == '>') {
                            this.pos = i11 + 1;
                        }
                    }
                }
            } else {
                if (i9 == 6) {
                    if (this.lenient) {
                        nextNonWhitespace(true);
                        int i12 = this.pos - 1;
                        this.pos = i12;
                        if (i12 + 5 <= this.limit || fillBuffer(5)) {
                            int i13 = this.pos;
                            char[] cArr2 = this.buffer;
                            if (cArr2[i13] == ')' && cArr2[i13 + 1] == ']' && cArr2[i13 + 2] == '}' && cArr2[i13 + 3] == '\'' && cArr2[i13 + 4] == '\n') {
                                this.pos = i13 + 5;
                            }
                        }
                    }
                    this.stack[this.stackSize - 1] = 7;
                    i = 0;
                    nextNonWhitespace = nextNonWhitespace(true);
                    if (nextNonWhitespace == 34) {
                        this.peeked = 9;
                        return 9;
                    }
                    if (nextNonWhitespace == 39) {
                        checkLenient();
                        this.peeked = 8;
                        return 8;
                    }
                    if (nextNonWhitespace == 44 || nextNonWhitespace == 59) {
                        i2 = 1;
                    } else {
                        if (nextNonWhitespace == 91) {
                            this.peeked = 3;
                            return 3;
                        }
                        if (nextNonWhitespace == 93) {
                            i2 = 1;
                            if (i9 == 1) {
                                this.peeked = 4;
                                return 4;
                            }
                        } else {
                            if (nextNonWhitespace == 123) {
                                this.peeked = 1;
                                return 1;
                            }
                            int i14 = this.pos - 1;
                            this.pos = i14;
                            char c2 = this.buffer[i14];
                            if (c2 == 't' || c2 == 'T') {
                                str = "true";
                                str2 = "TRUE";
                                i3 = 5;
                            } else if (c2 == 'f' || c2 == 'F') {
                                str = "false";
                                str2 = "FALSE";
                                i3 = 6;
                            } else {
                                if (c2 == 'n' || c2 == 'N') {
                                    str = "null";
                                    str2 = "NULL";
                                    i3 = 7;
                                }
                                i3 = i;
                                if (i3 == 0) {
                                    return i3;
                                }
                                char[] cArr3 = this.buffer;
                                int i15 = this.pos;
                                int i16 = this.limit;
                                int i17 = i;
                                int i18 = i17;
                                int i19 = i18;
                                boolean z = true;
                                long j = 0;
                                while (true) {
                                    if (i15 + i18 == i16) {
                                        if (i18 == cArr3.length) {
                                            break;
                                        }
                                        if (!fillBuffer(i18 + 1)) {
                                            int i20 = i18;
                                            break;
                                        }
                                        int i21 = this.pos;
                                        i16 = this.limit;
                                        i15 = i21;
                                    }
                                    char c3 = cArr3[i15 + i18];
                                    if (c3 != '+') {
                                        if (c3 == 'E' || c3 == 'e') {
                                            i4 = i18;
                                            if (i17 != 2 && i17 != 4) {
                                                break;
                                            }
                                            i17 = 5;
                                            i18 = i4 + 1;
                                            i10 = 1;
                                        } else if (c3 == '-') {
                                            i4 = i18;
                                            i5 = 6;
                                            if (i17 == 0) {
                                                i17 = 1;
                                                i19 = 1;
                                                i18 = i4 + 1;
                                                i10 = 1;
                                            } else {
                                                if (i17 != 5) {
                                                    break;
                                                }
                                                i17 = i5;
                                                i18 = i4 + 1;
                                                i10 = 1;
                                            }
                                        } else if (c3 == '.') {
                                            i4 = i18;
                                            if (i17 != 2) {
                                                break;
                                            }
                                            i17 = 3;
                                            i18 = i4 + 1;
                                            i10 = 1;
                                        } else {
                                            if (c3 < '0' || c3 > '9') {
                                                break;
                                            }
                                            if (i17 == i10 || i17 == 0) {
                                                j = -(c3 - '0');
                                                i4 = i18;
                                                i17 = 2;
                                            } else {
                                                if (i17 != 2) {
                                                    i6 = i18;
                                                    if (i17 == 3) {
                                                        i17 = 4;
                                                    } else if (i17 == 5 || i17 == 6) {
                                                        i4 = i6;
                                                        i17 = 7;
                                                    } else {
                                                        i4 = i6;
                                                    }
                                                } else {
                                                    if (j == 0) {
                                                        break;
                                                    }
                                                    i6 = i18;
                                                    long j2 = (10 * j) - (c3 - '0');
                                                    z = (j > -922337203685477580L || (j == -922337203685477580L && j2 < j)) & z;
                                                    j = j2;
                                                }
                                                i4 = i6;
                                            }
                                            i18 = i4 + 1;
                                            i10 = 1;
                                        }
                                        if (i7 == 0) {
                                            return i7;
                                        }
                                        if (!isLiteral(this.buffer[this.pos])) {
                                            syntaxError("Expected value");
                                            throw null;
                                        }
                                        checkLenient();
                                        this.peeked = 10;
                                        return 10;
                                    }
                                    i4 = i18;
                                    i5 = 6;
                                    if (i17 != 5) {
                                        break;
                                    }
                                    i17 = i5;
                                    i18 = i4 + 1;
                                    i10 = 1;
                                }
                                i7 = 0;
                                if (i7 == 0) {
                                }
                            }
                            int length = str.length();
                            int i22 = 1;
                            while (true) {
                                if (i22 < length) {
                                    if ((this.pos + i22 >= this.limit && !fillBuffer(i22 + 1)) || ((c = this.buffer[this.pos + i22]) != str.charAt(i22) && c != str2.charAt(i22))) {
                                        break;
                                    }
                                    i22++;
                                } else if ((this.pos + length >= this.limit && !fillBuffer(length + 1)) || !isLiteral(this.buffer[this.pos + length])) {
                                    this.pos += length;
                                    this.peeked = i3;
                                }
                            }
                            i3 = i;
                            if (i3 == 0) {
                            }
                        }
                    }
                    if (i9 != i2 && i9 != 2) {
                        syntaxError("Unexpected value");
                        throw null;
                    }
                    checkLenient();
                    this.pos -= i2;
                    this.peeked = 7;
                    return 7;
                }
                if (i9 == 7) {
                    i = 0;
                    if (nextNonWhitespace(false) == -1) {
                        this.peeked = 17;
                        return 17;
                    }
                    checkLenient();
                    this.pos--;
                } else {
                    i = 0;
                    if (i9 == 8) {
                        throw new IllegalStateException("JsonReader is closed");
                    }
                }
                nextNonWhitespace = nextNonWhitespace(true);
                if (nextNonWhitespace == 34) {
                }
            }
        }
        i = 0;
        nextNonWhitespace = nextNonWhitespace(true);
        if (nextNonWhitespace == 34) {
        }
    }

    public void endArray() {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i != 4) {
            throw new IllegalStateException("Expected END_ARRAY but was " + peek() + locationString());
        }
        int i2 = this.stackSize - 1;
        this.stackSize = i2;
        int[] iArr = this.pathIndices;
        int i3 = i2 - 1;
        iArr[i3] = iArr[i3] + 1;
        this.peeked = 0;
    }

    public void endObject() {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i != 2) {
            throw new IllegalStateException("Expected END_OBJECT but was " + peek() + locationString());
        }
        int i2 = this.stackSize - 1;
        this.stackSize = i2;
        this.pathNames[i2] = null;
        int[] iArr = this.pathIndices;
        int i3 = i2 - 1;
        iArr[i3] = iArr[i3] + 1;
        this.peeked = 0;
    }

    public final boolean fillBuffer(int i) {
        int i2;
        int i3;
        char[] cArr = this.buffer;
        int i4 = this.lineStart;
        int i5 = this.pos;
        this.lineStart = i4 - i5;
        int i6 = this.limit;
        if (i6 != i5) {
            int i7 = i6 - i5;
            this.limit = i7;
            System.arraycopy(cArr, i5, cArr, 0, i7);
        } else {
            this.limit = 0;
        }
        this.pos = 0;
        do {
            Reader reader = this.f466in;
            int i8 = this.limit;
            int read = reader.read(cArr, i8, cArr.length - i8);
            if (read == -1) {
                return false;
            }
            i2 = this.limit + read;
            this.limit = i2;
            if (this.lineNumber == 0 && (i3 = this.lineStart) == 0 && i2 > 0 && cArr[0] == 65279) {
                this.pos++;
                this.lineStart = i3 + 1;
                i++;
            }
        } while (i2 < i);
        return true;
    }

    public final String getPath(boolean z) {
        StringBuilder sb = new StringBuilder("$");
        int i = 0;
        while (true) {
            int i2 = this.stackSize;
            if (i >= i2) {
                return sb.toString();
            }
            int i3 = this.stack[i];
            if (i3 == 1 || i3 == 2) {
                int i4 = this.pathIndices[i];
                if (z && i4 > 0 && i == i2 - 1) {
                    i4--;
                }
                sb.append('[');
                sb.append(i4);
                sb.append(']');
            } else if (i3 == 3 || i3 == 4 || i3 == 5) {
                sb.append('.');
                String str = this.pathNames[i];
                if (str != null) {
                    sb.append(str);
                }
            }
            i++;
        }
    }

    public String getPreviousPath() {
        return getPath(true);
    }

    public boolean hasNext() {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        return (i == 2 || i == 4 || i == 17) ? false : true;
    }

    public final boolean isLiteral(char c) {
        if (c == '\t' || c == '\n' || c == '\f' || c == '\r' || c == ' ') {
            return false;
        }
        if (c != '#') {
            if (c == ',') {
                return false;
            }
            if (c != '/' && c != '=') {
                if (c == '{' || c == '}' || c == ':') {
                    return false;
                }
                if (c != ';') {
                    switch (c) {
                        case '[':
                        case ']':
                            return false;
                        case '\\':
                            break;
                        default:
                            return true;
                    }
                }
            }
        }
        checkLenient();
        return false;
    }

    public final String locationString() {
        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m(" at line ", this.lineNumber + 1, " column ", (this.pos - this.lineStart) + 1, " path ");
        m45m.append(getPath());
        return m45m.toString();
    }

    public boolean nextBoolean() {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 5) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i2 = this.stackSize - 1;
            iArr[i2] = iArr[i2] + 1;
            return true;
        }
        if (i != 6) {
            throw new IllegalStateException("Expected a boolean but was " + peek() + locationString());
        }
        this.peeked = 0;
        int[] iArr2 = this.pathIndices;
        int i3 = this.stackSize - 1;
        iArr2[i3] = iArr2[i3] + 1;
        return false;
    }

    public double nextDouble() {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 15) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i2 = this.stackSize - 1;
            iArr[i2] = iArr[i2] + 1;
            return this.peekedLong;
        }
        if (i == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else if (i == 8 || i == 9) {
            this.peekedString = nextQuotedValue(i == 8 ? '\'' : '\"');
        } else if (i == 10) {
            this.peekedString = nextUnquotedValue();
        } else if (i != 11) {
            throw new IllegalStateException("Expected a double but was " + peek() + locationString());
        }
        this.peeked = 11;
        double parseDouble = Double.parseDouble(this.peekedString);
        if (!this.lenient && (Double.isNaN(parseDouble) || Double.isInfinite(parseDouble))) {
            throw new MalformedJsonException("JSON forbids NaN and infinities: " + parseDouble + locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        int[] iArr2 = this.pathIndices;
        int i3 = this.stackSize - 1;
        iArr2[i3] = iArr2[i3] + 1;
        return parseDouble;
    }

    public int nextInt() {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 15) {
            long j = this.peekedLong;
            int i2 = (int) j;
            if (j != i2) {
                throw new NumberFormatException("Expected an int but was " + this.peekedLong + locationString());
            }
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i3 = this.stackSize - 1;
            iArr[i3] = iArr[i3] + 1;
            return i2;
        }
        if (i == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else {
            if (i != 8 && i != 9 && i != 10) {
                throw new IllegalStateException("Expected an int but was " + peek() + locationString());
            }
            if (i == 10) {
                this.peekedString = nextUnquotedValue();
            } else {
                this.peekedString = nextQuotedValue(i == 8 ? '\'' : '\"');
            }
            try {
                int parseInt = Integer.parseInt(this.peekedString);
                this.peeked = 0;
                int[] iArr2 = this.pathIndices;
                int i4 = this.stackSize - 1;
                iArr2[i4] = iArr2[i4] + 1;
                return parseInt;
            } catch (NumberFormatException unused) {
            }
        }
        this.peeked = 11;
        double parseDouble = Double.parseDouble(this.peekedString);
        int i5 = (int) parseDouble;
        if (i5 != parseDouble) {
            throw new NumberFormatException("Expected an int but was " + this.peekedString + locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        int[] iArr3 = this.pathIndices;
        int i6 = this.stackSize - 1;
        iArr3[i6] = iArr3[i6] + 1;
        return i5;
    }

    public long nextLong() {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 15) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i2 = this.stackSize - 1;
            iArr[i2] = iArr[i2] + 1;
            return this.peekedLong;
        }
        if (i == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else {
            if (i != 8 && i != 9 && i != 10) {
                throw new IllegalStateException("Expected a long but was " + peek() + locationString());
            }
            if (i == 10) {
                this.peekedString = nextUnquotedValue();
            } else {
                this.peekedString = nextQuotedValue(i == 8 ? '\'' : '\"');
            }
            try {
                long parseLong = Long.parseLong(this.peekedString);
                this.peeked = 0;
                int[] iArr2 = this.pathIndices;
                int i3 = this.stackSize - 1;
                iArr2[i3] = iArr2[i3] + 1;
                return parseLong;
            } catch (NumberFormatException unused) {
            }
        }
        this.peeked = 11;
        double parseDouble = Double.parseDouble(this.peekedString);
        long j = (long) parseDouble;
        if (j != parseDouble) {
            throw new NumberFormatException("Expected a long but was " + this.peekedString + locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        int[] iArr3 = this.pathIndices;
        int i4 = this.stackSize - 1;
        iArr3[i4] = iArr3[i4] + 1;
        return j;
    }

    public String nextName() {
        String nextQuotedValue;
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 14) {
            nextQuotedValue = nextUnquotedValue();
        } else if (i == 12) {
            nextQuotedValue = nextQuotedValue('\'');
        } else {
            if (i != 13) {
                throw new IllegalStateException("Expected a name but was " + peek() + locationString());
            }
            nextQuotedValue = nextQuotedValue('\"');
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = nextQuotedValue;
        return nextQuotedValue;
    }

    public final int nextNonWhitespace(boolean z) {
        char[] cArr = this.buffer;
        int i = this.pos;
        int i2 = this.limit;
        while (true) {
            boolean z2 = true;
            if (i == i2) {
                this.pos = i;
                if (!fillBuffer(1)) {
                    if (!z) {
                        return -1;
                    }
                    throw new EOFException("End of input" + locationString());
                }
                i = this.pos;
                i2 = this.limit;
            }
            int i3 = i + 1;
            char c = cArr[i];
            if (c == '\n') {
                this.lineNumber++;
                this.lineStart = i3;
            } else if (c != ' ' && c != '\r' && c != '\t') {
                if (c == '/') {
                    this.pos = i3;
                    if (i3 == i2) {
                        this.pos = i3 - 1;
                        boolean fillBuffer = fillBuffer(2);
                        this.pos++;
                        if (!fillBuffer) {
                            return c;
                        }
                    }
                    checkLenient();
                    int i4 = this.pos;
                    char c2 = cArr[i4];
                    if (c2 == '*') {
                        this.pos = i4 + 1;
                        while (true) {
                            if (this.pos + 2 > this.limit && !fillBuffer(2)) {
                                z2 = false;
                                break;
                            }
                            char[] cArr2 = this.buffer;
                            int i5 = this.pos;
                            if (cArr2[i5] != '\n') {
                                for (int i6 = 0; i6 < 2; i6++) {
                                    if (this.buffer[this.pos + i6] != "*/".charAt(i6)) {
                                        break;
                                    }
                                }
                                break;
                            }
                            this.lineNumber++;
                            this.lineStart = i5 + 1;
                            this.pos++;
                        }
                        if (!z2) {
                            syntaxError("Unterminated comment");
                            throw null;
                        }
                        i = this.pos + 2;
                        i2 = this.limit;
                    } else {
                        if (c2 != '/') {
                            return c;
                        }
                        this.pos = i4 + 1;
                        skipToEndOfLine();
                        i = this.pos;
                        i2 = this.limit;
                    }
                } else {
                    if (c != '#') {
                        this.pos = i3;
                        return c;
                    }
                    this.pos = i3;
                    checkLenient();
                    skipToEndOfLine();
                    i = this.pos;
                    i2 = this.limit;
                }
            }
            i = i3;
        }
    }

    public void nextNull() {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i != 7) {
            throw new IllegalStateException("Expected null but was " + peek() + locationString());
        }
        this.peeked = 0;
        int[] iArr = this.pathIndices;
        int i2 = this.stackSize - 1;
        iArr[i2] = iArr[i2] + 1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x005c, code lost:
    
        if (r2 != null) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x005e, code lost:
    
        r2 = new java.lang.StringBuilder(java.lang.Math.max((r5 - r3) * 2, 16));
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x006c, code lost:
    
        r2.append(r0, r3, r5 - r3);
        r10.pos = r5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String nextQuotedValue(char c) {
        char[] cArr = this.buffer;
        StringBuilder sb = null;
        do {
            int i = this.pos;
            int i2 = this.limit;
            while (true) {
                int i3 = i;
                while (i3 < i2) {
                    int i4 = i3 + 1;
                    char c2 = cArr[i3];
                    if (c2 == c) {
                        this.pos = i4;
                        int i5 = (i4 - i) - 1;
                        if (sb == null) {
                            return new String(cArr, i, i5);
                        }
                        sb.append(cArr, i, i5);
                        return sb.toString();
                    }
                    if (c2 == '\\') {
                        this.pos = i4;
                        int i6 = (i4 - i) - 1;
                        if (sb == null) {
                            sb = new StringBuilder(Math.max((i6 + 1) * 2, 16));
                        }
                        sb.append(cArr, i, i6);
                        sb.append(readEscapeCharacter());
                        i = this.pos;
                        i2 = this.limit;
                    } else {
                        if (c2 == '\n') {
                            this.lineNumber++;
                            this.lineStart = i4;
                        }
                        i3 = i4;
                    }
                }
                break;
            }
        } while (fillBuffer(1));
        syntaxError("Unterminated string");
        throw null;
    }

    public String nextString() {
        String str;
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 10) {
            str = nextUnquotedValue();
        } else if (i == 8) {
            str = nextQuotedValue('\'');
        } else if (i == 9) {
            str = nextQuotedValue('\"');
        } else if (i == 11) {
            str = this.peekedString;
            this.peekedString = null;
        } else if (i == 15) {
            str = Long.toString(this.peekedLong);
        } else {
            if (i != 16) {
                throw new IllegalStateException("Expected a string but was " + peek() + locationString());
            }
            str = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        }
        this.peeked = 0;
        int[] iArr = this.pathIndices;
        int i2 = this.stackSize - 1;
        iArr[i2] = iArr[i2] + 1;
        return str;
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x004a, code lost:
    
        checkLenient();
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:54:0x0044. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x008a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String nextUnquotedValue() {
        String sb;
        StringBuilder sb2 = null;
        int i = 0;
        do {
            int i2 = 0;
            while (true) {
                int i3 = this.pos;
                if (i3 + i2 < this.limit) {
                    char c = this.buffer[i3 + i2];
                    if (c != '\t' && c != '\n' && c != '\f' && c != '\r' && c != ' ') {
                        if (c != '#') {
                            if (c != ',') {
                                if (c != '/' && c != '=') {
                                    if (c != '{' && c != '}' && c != ':') {
                                        if (c != ';') {
                                            switch (c) {
                                                case '[':
                                                case ']':
                                                    break;
                                                case '\\':
                                                    break;
                                                default:
                                                    i2++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (i2 >= this.buffer.length) {
                    if (sb2 == null) {
                        sb2 = new StringBuilder(Math.max(i2, 16));
                    }
                    sb2.append(this.buffer, this.pos, i2);
                    this.pos += i2;
                } else if (fillBuffer(i2 + 1)) {
                }
            }
            i = i2;
            if (sb2 != null) {
                sb = new String(this.buffer, this.pos, i);
            } else {
                sb2.append(this.buffer, this.pos, i);
                sb = sb2.toString();
            }
            this.pos += i;
            return sb;
        } while (fillBuffer(1));
        if (sb2 != null) {
        }
        this.pos += i;
        return sb;
    }

    public JsonToken peek() {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        switch (i) {
            case 1:
                return JsonToken.BEGIN_OBJECT;
            case 2:
                return JsonToken.END_OBJECT;
            case 3:
                return JsonToken.BEGIN_ARRAY;
            case 4:
                return JsonToken.END_ARRAY;
            case 5:
            case 6:
                return JsonToken.BOOLEAN;
            case 7:
                return JsonToken.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return JsonToken.STRING;
            case 12:
            case 13:
            case 14:
                return JsonToken.NAME;
            case 15:
            case 16:
                return JsonToken.NUMBER;
            case 17:
                return JsonToken.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    public final void push(int i) {
        int i2 = this.stackSize;
        int[] iArr = this.stack;
        if (i2 == iArr.length) {
            int i3 = i2 * 2;
            this.stack = Arrays.copyOf(iArr, i3);
            this.pathIndices = Arrays.copyOf(this.pathIndices, i3);
            this.pathNames = (String[]) Arrays.copyOf(this.pathNames, i3);
        }
        int[] iArr2 = this.stack;
        int i4 = this.stackSize;
        this.stackSize = i4 + 1;
        iArr2[i4] = i;
    }

    public final char readEscapeCharacter() {
        int i;
        int i2;
        if (this.pos == this.limit && !fillBuffer(1)) {
            syntaxError("Unterminated escape sequence");
            throw null;
        }
        char[] cArr = this.buffer;
        int i3 = this.pos;
        int i4 = i3 + 1;
        this.pos = i4;
        char c = cArr[i3];
        if (c == '\n') {
            this.lineNumber++;
            this.lineStart = i4;
        } else if (c != '\"' && c != '\'' && c != '/' && c != '\\') {
            if (c == 'b') {
                return '\b';
            }
            if (c == 'f') {
                return '\f';
            }
            if (c == 'n') {
                return '\n';
            }
            if (c == 'r') {
                return '\r';
            }
            if (c == 't') {
                return '\t';
            }
            if (c != 'u') {
                syntaxError("Invalid escape sequence");
                throw null;
            }
            if (i4 + 4 > this.limit && !fillBuffer(4)) {
                syntaxError("Unterminated escape sequence");
                throw null;
            }
            int i5 = this.pos;
            int i6 = i5 + 4;
            char c2 = 0;
            while (i5 < i6) {
                char c3 = this.buffer[i5];
                char c4 = (char) (c2 << 4);
                if (c3 < '0' || c3 > '9') {
                    if (c3 >= 'a' && c3 <= 'f') {
                        i = c3 - 'a';
                    } else {
                        if (c3 < 'A' || c3 > 'F') {
                            throw new NumberFormatException("\\u".concat(new String(this.buffer, this.pos, 4)));
                        }
                        i = c3 - 'A';
                    }
                    i2 = i + 10;
                } else {
                    i2 = c3 - '0';
                }
                c2 = (char) (i2 + c4);
                i5++;
            }
            this.pos += 4;
            return c2;
        }
        return c;
    }

    public final void skipQuotedValue(char c) {
        char[] cArr = this.buffer;
        do {
            int i = this.pos;
            int i2 = this.limit;
            while (i < i2) {
                int i3 = i + 1;
                char c2 = cArr[i];
                if (c2 == c) {
                    this.pos = i3;
                    return;
                }
                if (c2 == '\\') {
                    this.pos = i3;
                    readEscapeCharacter();
                    i = this.pos;
                    i2 = this.limit;
                } else {
                    if (c2 == '\n') {
                        this.lineNumber++;
                        this.lineStart = i3;
                    }
                    i = i3;
                }
            }
            this.pos = i;
        } while (fillBuffer(1));
        syntaxError("Unterminated string");
        throw null;
    }

    public final void skipToEndOfLine() {
        char c;
        do {
            if (this.pos >= this.limit && !fillBuffer(1)) {
                return;
            }
            char[] cArr = this.buffer;
            int i = this.pos;
            int i2 = i + 1;
            this.pos = i2;
            c = cArr[i];
            if (c == '\n') {
                this.lineNumber++;
                this.lineStart = i2;
                return;
            }
        } while (c != '\r');
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:65:0x009b. Please report as an issue. */
    public void skipValue() {
        int i = 0;
        do {
            int i2 = this.peeked;
            if (i2 == 0) {
                i2 = doPeek();
            }
            if (i2 == 3) {
                push(1);
            } else if (i2 == 1) {
                push(3);
            } else {
                if (i2 == 4) {
                    this.stackSize--;
                } else if (i2 == 2) {
                    this.stackSize--;
                } else {
                    if (i2 == 14 || i2 == 10) {
                        do {
                            int i3 = 0;
                            while (true) {
                                int i4 = this.pos + i3;
                                if (i4 < this.limit) {
                                    char c = this.buffer[i4];
                                    if (c != '\t' && c != '\n' && c != '\f' && c != '\r' && c != ' ') {
                                        if (c != '#') {
                                            if (c != ',') {
                                                if (c != '/' && c != '=') {
                                                    if (c != '{' && c != '}' && c != ':') {
                                                        if (c != ';') {
                                                            switch (c) {
                                                                case '[':
                                                                case ']':
                                                                    break;
                                                                case '\\':
                                                                    break;
                                                                default:
                                                                    i3++;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    this.pos = i4;
                                }
                            }
                            checkLenient();
                            this.pos += i3;
                        } while (fillBuffer(1));
                    } else if (i2 == 8 || i2 == 12) {
                        skipQuotedValue('\'');
                    } else if (i2 == 9 || i2 == 13) {
                        skipQuotedValue('\"');
                    } else if (i2 == 16) {
                        this.pos += this.peekedNumberLength;
                    }
                    this.peeked = 0;
                }
                i--;
                this.peeked = 0;
            }
            i++;
            this.peeked = 0;
        } while (i != 0);
        int[] iArr = this.pathIndices;
        int i5 = this.stackSize;
        int i6 = i5 - 1;
        iArr[i6] = iArr[i6] + 1;
        this.pathNames[i5 - 1] = "null";
    }

    public final void syntaxError(String str) {
        StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str);
        m18m.append(locationString());
        throw new MalformedJsonException(m18m.toString());
    }

    public String toString() {
        return getClass().getSimpleName() + locationString();
    }

    public String getPath() {
        return getPath(false);
    }
}
