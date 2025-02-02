package com.airbnb.lottie.parser.moshi;

import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.EOFException;
import java.io.IOException;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;

/* loaded from: classes.dex */
final class JsonUtf8Reader extends JsonReader {
    private final Buffer buffer;
    private int peeked = 0;
    private long peekedLong;
    private int peekedNumberLength;
    private String peekedString;
    private final BufferedSource source;
    private static final ByteString SINGLE_QUOTE_OR_SLASH = ByteString.encodeUtf8("'\\");
    private static final ByteString DOUBLE_QUOTE_OR_SLASH = ByteString.encodeUtf8("\"\\");
    private static final ByteString UNQUOTED_STRING_TERMINALS = ByteString.encodeUtf8("{}[]:, \n\t\r\f/\\;#=");

    static {
        ByteString.encodeUtf8("\n\r");
        ByteString.encodeUtf8("*/");
    }

    JsonUtf8Reader(BufferedSource bufferedSource) {
        this.source = bufferedSource;
        this.buffer = bufferedSource.buffer();
        pushScope(6);
    }

    private void checkLenient() throws IOException {
        syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        throw null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x01c9, code lost:
    
        if (r5 == r2) goto L155;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x01cc, code lost:
    
        if (r5 == 4) goto L155;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x01cf, code lost:
    
        if (r5 != 7) goto L173;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x01d1, code lost:
    
        r16.peekedNumberLength = r1;
        r8 = 17;
        r16.peeked = 17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x01a0, code lost:
    
        if (isLiteral(r2) != false) goto L173;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01a2, code lost:
    
        r2 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x01a3, code lost:
    
        if (r5 != r2) goto L150;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x01a5, code lost:
    
        if (r7 == false) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01ab, code lost:
    
        if (r8 != Long.MIN_VALUE) goto L142;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x01ad, code lost:
    
        if (r10 == false) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x01b3, code lost:
    
        if (r8 != 0) goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x01b5, code lost:
    
        if (r10 != false) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x01b7, code lost:
    
        if (r10 == false) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x01ba, code lost:
    
        r8 = -r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x01bb, code lost:
    
        r16.peekedLong = r8;
        r16.buffer.skip(r1);
        r8 = 16;
        r16.peeked = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x01c8, code lost:
    
        r2 = 2;
     */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0120 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0203 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0204  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int doPeek() throws IOException {
        String str;
        String str2;
        int i;
        byte b;
        int i2;
        char c;
        int[] iArr = this.scopes;
        int i3 = this.stackSize;
        int i4 = iArr[i3 - 1];
        char c2 = 2;
        if (i4 == 1) {
            iArr[i3 - 1] = 2;
        } else if (i4 == 2) {
            int nextNonWhitespace = nextNonWhitespace(true);
            this.buffer.readByte();
            if (nextNonWhitespace != 44) {
                if (nextNonWhitespace == 59) {
                    checkLenient();
                    throw null;
                }
                if (nextNonWhitespace == 93) {
                    this.peeked = 4;
                    return 4;
                }
                syntaxError("Unterminated array");
                throw null;
            }
        } else {
            if (i4 == 3 || i4 == 5) {
                iArr[i3 - 1] = 4;
                if (i4 == 5) {
                    int nextNonWhitespace2 = nextNonWhitespace(true);
                    this.buffer.readByte();
                    if (nextNonWhitespace2 != 44) {
                        if (nextNonWhitespace2 == 59) {
                            checkLenient();
                            throw null;
                        }
                        if (nextNonWhitespace2 == 125) {
                            this.peeked = 2;
                            return 2;
                        }
                        syntaxError("Unterminated object");
                        throw null;
                    }
                }
                int nextNonWhitespace3 = nextNonWhitespace(true);
                if (nextNonWhitespace3 == 34) {
                    this.buffer.readByte();
                    this.peeked = 13;
                    return 13;
                }
                if (nextNonWhitespace3 == 39) {
                    this.buffer.readByte();
                    checkLenient();
                    throw null;
                }
                if (nextNonWhitespace3 != 125) {
                    checkLenient();
                    throw null;
                }
                if (i4 == 5) {
                    syntaxError("Expected name");
                    throw null;
                }
                this.buffer.readByte();
                this.peeked = 2;
                return 2;
            }
            if (i4 == 4) {
                iArr[i3 - 1] = 5;
                int nextNonWhitespace4 = nextNonWhitespace(true);
                this.buffer.readByte();
                if (nextNonWhitespace4 != 58) {
                    if (nextNonWhitespace4 != 61) {
                        syntaxError("Expected ':'");
                        throw null;
                    }
                    checkLenient();
                    throw null;
                }
            } else if (i4 == 6) {
                iArr[i3 - 1] = 7;
            } else {
                if (i4 == 7) {
                    if (nextNonWhitespace(false) == -1) {
                        this.peeked = 18;
                        return 18;
                    }
                    checkLenient();
                    throw null;
                }
                if (i4 == 8) {
                    throw new IllegalStateException("JsonReader is closed");
                }
            }
        }
        int nextNonWhitespace5 = nextNonWhitespace(true);
        if (nextNonWhitespace5 == 34) {
            this.buffer.readByte();
            this.peeked = 9;
            return 9;
        }
        if (nextNonWhitespace5 == 39) {
            checkLenient();
            throw null;
        }
        if (nextNonWhitespace5 != 44 && nextNonWhitespace5 != 59) {
            if (nextNonWhitespace5 == 91) {
                this.buffer.readByte();
                this.peeked = 3;
                return 3;
            }
            if (nextNonWhitespace5 != 93) {
                if (nextNonWhitespace5 == 123) {
                    this.buffer.readByte();
                    this.peeked = 1;
                    return 1;
                }
                byte b2 = this.buffer.getByte(0L);
                if (b2 == 116 || b2 == 84) {
                    str = "true";
                    str2 = "TRUE";
                    i = 5;
                } else if (b2 == 102 || b2 == 70) {
                    str = "false";
                    str2 = "FALSE";
                    i = 6;
                } else if (b2 == 110 || b2 == 78) {
                    str = "null";
                    str2 = "NULL";
                    i = 7;
                } else {
                    i = 0;
                    if (i == 0) {
                        return i;
                    }
                    long j = 0;
                    boolean z = true;
                    int i5 = 0;
                    char c3 = 0;
                    boolean z2 = false;
                    while (true) {
                        int i6 = i5 + 1;
                        if (!this.source.request(i6)) {
                            char c4 = c2;
                            break;
                        }
                        byte b3 = this.buffer.getByte(i5);
                        if (b3 != 43) {
                            if (b3 == 69 || b3 == 101) {
                                if (c3 != 2 && c3 != 4) {
                                    break;
                                }
                                c3 = 5;
                                i5 = i6;
                                c2 = 2;
                            } else if (b3 == 45) {
                                c = 6;
                                if (c3 == 0) {
                                    c3 = 1;
                                    z2 = true;
                                    i5 = i6;
                                    c2 = 2;
                                } else {
                                    if (c3 != 5) {
                                        break;
                                    }
                                    c3 = c;
                                    i5 = i6;
                                    c2 = 2;
                                }
                            } else if (b3 == 46) {
                                if (c3 != 2) {
                                    break;
                                }
                                c3 = 3;
                                i5 = i6;
                                c2 = 2;
                            } else {
                                if (b3 < 48 || b3 > 57) {
                                    break;
                                }
                                if (c3 == 1 || c3 == 0) {
                                    j = -(b3 - 48);
                                    c3 = 2;
                                } else if (c3 == 2) {
                                    if (j == 0) {
                                        break;
                                    }
                                    long j2 = (10 * j) - (b3 - 48);
                                    z &= j > -922337203685477580L || (j == -922337203685477580L && j2 < j);
                                    j = j2;
                                } else if (c3 == 3) {
                                    c3 = 4;
                                } else if (c3 == 5 || c3 == 6) {
                                    c3 = 7;
                                }
                                i5 = i6;
                                c2 = 2;
                            }
                            if (i2 == 0) {
                                return i2;
                            }
                            if (isLiteral(this.buffer.getByte(0L))) {
                                checkLenient();
                                throw null;
                            }
                            syntaxError("Expected value");
                            throw null;
                        }
                        c = 6;
                        if (c3 != 5) {
                            break;
                        }
                        c3 = c;
                        i5 = i6;
                        c2 = 2;
                    }
                    i2 = 0;
                    if (i2 == 0) {
                    }
                }
                int length = str.length();
                int i7 = 1;
                while (true) {
                    if (i7 < length) {
                        int i8 = i7 + 1;
                        if (!this.source.request(i8) || ((b = this.buffer.getByte(i7)) != str.charAt(i7) && b != str2.charAt(i7))) {
                            break;
                        }
                        i7 = i8;
                    } else if (!this.source.request(length + 1) || !isLiteral(this.buffer.getByte(length))) {
                        this.buffer.skip(length);
                        this.peeked = i;
                    }
                }
                i = 0;
                if (i == 0) {
                }
            } else if (i4 == 1) {
                this.buffer.readByte();
                this.peeked = 4;
                return 4;
            }
        }
        if (i4 == 1 || i4 == 2) {
            checkLenient();
            throw null;
        }
        syntaxError("Unexpected value");
        throw null;
    }

    private int findName(String str, JsonReader.Options options) {
        int length = options.strings.length;
        for (int i = 0; i < length; i++) {
            if (str.equals(options.strings[i])) {
                this.peeked = 0;
                this.pathNames[this.stackSize - 1] = str;
                return i;
            }
        }
        return -1;
    }

    private boolean isLiteral(int i) throws IOException {
        if (i == 9 || i == 10 || i == 12 || i == 13 || i == 32) {
            return false;
        }
        if (i != 35) {
            if (i == 44) {
                return false;
            }
            if (i != 47 && i != 61) {
                if (i == 123 || i == 125 || i == 58) {
                    return false;
                }
                if (i != 59) {
                    switch (i) {
                        case 91:
                        case 93:
                            return false;
                        case 92:
                            break;
                        default:
                            return true;
                    }
                }
            }
        }
        checkLenient();
        throw null;
    }

    private int nextNonWhitespace(boolean z) throws IOException {
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (!this.source.request(i2)) {
                if (z) {
                    throw new EOFException("End of input");
                }
                return -1;
            }
            byte b = this.buffer.getByte(i);
            if (b != 10 && b != 32 && b != 13 && b != 9) {
                this.buffer.skip(i2 - 1);
                if (b == 47) {
                    if (!this.source.request(2L)) {
                        return b;
                    }
                    checkLenient();
                    throw null;
                }
                if (b != 35) {
                    return b;
                }
                checkLenient();
                throw null;
            }
            i = i2;
        }
    }

    private String nextQuotedValue(ByteString byteString) throws IOException {
        StringBuilder sb = null;
        while (true) {
            long indexOfElement = this.source.indexOfElement(byteString);
            if (indexOfElement == -1) {
                syntaxError("Unterminated string");
                throw null;
            }
            if (this.buffer.getByte(indexOfElement) != 92) {
                if (sb == null) {
                    String readUtf8 = this.buffer.readUtf8(indexOfElement);
                    this.buffer.readByte();
                    return readUtf8;
                }
                sb.append(this.buffer.readUtf8(indexOfElement));
                this.buffer.readByte();
                return sb.toString();
            }
            if (sb == null) {
                sb = new StringBuilder();
            }
            sb.append(this.buffer.readUtf8(indexOfElement));
            this.buffer.readByte();
            sb.append(readEscapeCharacter());
        }
    }

    private String nextUnquotedValue() throws IOException {
        long indexOfElement = this.source.indexOfElement(UNQUOTED_STRING_TERMINALS);
        Buffer buffer = this.buffer;
        return indexOfElement != -1 ? buffer.readUtf8(indexOfElement) : buffer.readUtf8();
    }

    private char readEscapeCharacter() throws IOException {
        int i;
        int i2;
        if (!this.source.request(1L)) {
            syntaxError("Unterminated escape sequence");
            throw null;
        }
        byte readByte = this.buffer.readByte();
        if (readByte == 10 || readByte == 34 || readByte == 39 || readByte == 47 || readByte == 92) {
            return (char) readByte;
        }
        if (readByte == 98) {
            return '\b';
        }
        if (readByte == 102) {
            return '\f';
        }
        if (readByte == 110) {
            return '\n';
        }
        if (readByte == 114) {
            return '\r';
        }
        if (readByte == 116) {
            return '\t';
        }
        if (readByte != 117) {
            syntaxError("Invalid escape sequence: \\" + ((char) readByte));
            throw null;
        }
        if (!this.source.request(4L)) {
            throw new EOFException("Unterminated escape sequence at path " + getPath());
        }
        char c = 0;
        for (int i3 = 0; i3 < 4; i3++) {
            byte b = this.buffer.getByte(i3);
            char c2 = (char) (c << 4);
            if (b < 48 || b > 57) {
                if (b >= 97 && b <= 102) {
                    i = b - 97;
                } else {
                    if (b < 65 || b > 70) {
                        syntaxError("\\u".concat(this.buffer.readUtf8(4L)));
                        throw null;
                    }
                    i = b - 65;
                }
                i2 = i + 10;
            } else {
                i2 = b - 48;
            }
            c = (char) (i2 + c2);
        }
        this.buffer.skip(4L);
        return c;
    }

    private void skipQuotedValue(ByteString byteString) throws IOException {
        while (true) {
            long indexOfElement = this.source.indexOfElement(byteString);
            if (indexOfElement == -1) {
                syntaxError("Unterminated string");
                throw null;
            }
            if (this.buffer.getByte(indexOfElement) != 92) {
                this.buffer.skip(indexOfElement + 1);
                return;
            } else {
                this.buffer.skip(indexOfElement + 1);
                readEscapeCharacter();
            }
        }
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final void beginArray() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 3) {
            pushScope(1);
            this.pathIndices[this.stackSize - 1] = 0;
            this.peeked = 0;
        } else {
            throw new JsonDataException("Expected BEGIN_ARRAY but was " + peek() + " at path " + getPath());
        }
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final void beginObject() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 1) {
            pushScope(3);
            this.peeked = 0;
        } else {
            throw new JsonDataException("Expected BEGIN_OBJECT but was " + peek() + " at path " + getPath());
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        this.peeked = 0;
        this.scopes[0] = 8;
        this.stackSize = 1;
        this.buffer.clear();
        this.source.close();
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final void endArray() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i != 4) {
            throw new JsonDataException("Expected END_ARRAY but was " + peek() + " at path " + getPath());
        }
        int i2 = this.stackSize - 1;
        this.stackSize = i2;
        int[] iArr = this.pathIndices;
        int i3 = i2 - 1;
        iArr[i3] = iArr[i3] + 1;
        this.peeked = 0;
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final void endObject() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i != 2) {
            throw new JsonDataException("Expected END_OBJECT but was " + peek() + " at path " + getPath());
        }
        int i2 = this.stackSize - 1;
        this.stackSize = i2;
        this.pathNames[i2] = null;
        int[] iArr = this.pathIndices;
        int i3 = i2 - 1;
        iArr[i3] = iArr[i3] + 1;
        this.peeked = 0;
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final boolean hasNext() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        return (i == 2 || i == 4 || i == 18) ? false : true;
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final boolean nextBoolean() throws IOException {
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
        if (i == 6) {
            this.peeked = 0;
            int[] iArr2 = this.pathIndices;
            int i3 = this.stackSize - 1;
            iArr2[i3] = iArr2[i3] + 1;
            return false;
        }
        throw new JsonDataException("Expected a boolean but was " + peek() + " at path " + getPath());
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final double nextDouble() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 16) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i2 = this.stackSize - 1;
            iArr[i2] = iArr[i2] + 1;
            return this.peekedLong;
        }
        if (i == 17) {
            this.peekedString = this.buffer.readUtf8(this.peekedNumberLength);
        } else if (i == 9) {
            this.peekedString = nextQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (i == 8) {
            this.peekedString = nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (i == 10) {
            this.peekedString = nextUnquotedValue();
        } else if (i != 11) {
            throw new JsonDataException("Expected a double but was " + peek() + " at path " + getPath());
        }
        this.peeked = 11;
        try {
            double parseDouble = Double.parseDouble(this.peekedString);
            if (Double.isNaN(parseDouble) || Double.isInfinite(parseDouble)) {
                throw new JsonEncodingException("JSON forbids NaN and infinities: " + parseDouble + " at path " + getPath());
            }
            this.peekedString = null;
            this.peeked = 0;
            int[] iArr2 = this.pathIndices;
            int i3 = this.stackSize - 1;
            iArr2[i3] = iArr2[i3] + 1;
            return parseDouble;
        } catch (NumberFormatException unused) {
            throw new JsonDataException("Expected a double but was " + this.peekedString + " at path " + getPath());
        }
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final int nextInt() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 16) {
            long j = this.peekedLong;
            int i2 = (int) j;
            if (j == i2) {
                this.peeked = 0;
                int[] iArr = this.pathIndices;
                int i3 = this.stackSize - 1;
                iArr[i3] = iArr[i3] + 1;
                return i2;
            }
            throw new JsonDataException("Expected an int but was " + this.peekedLong + " at path " + getPath());
        }
        if (i == 17) {
            this.peekedString = this.buffer.readUtf8(this.peekedNumberLength);
        } else if (i == 9 || i == 8) {
            String nextQuotedValue = i == 9 ? nextQuotedValue(DOUBLE_QUOTE_OR_SLASH) : nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
            this.peekedString = nextQuotedValue;
            try {
                int parseInt = Integer.parseInt(nextQuotedValue);
                this.peeked = 0;
                int[] iArr2 = this.pathIndices;
                int i4 = this.stackSize - 1;
                iArr2[i4] = iArr2[i4] + 1;
                return parseInt;
            } catch (NumberFormatException unused) {
            }
        } else if (i != 11) {
            throw new JsonDataException("Expected an int but was " + peek() + " at path " + getPath());
        }
        this.peeked = 11;
        try {
            double parseDouble = Double.parseDouble(this.peekedString);
            int i5 = (int) parseDouble;
            if (i5 != parseDouble) {
                throw new JsonDataException("Expected an int but was " + this.peekedString + " at path " + getPath());
            }
            this.peekedString = null;
            this.peeked = 0;
            int[] iArr3 = this.pathIndices;
            int i6 = this.stackSize - 1;
            iArr3[i6] = iArr3[i6] + 1;
            return i5;
        } catch (NumberFormatException unused2) {
            throw new JsonDataException("Expected an int but was " + this.peekedString + " at path " + getPath());
        }
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final String nextName() throws IOException {
        String str;
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 14) {
            str = nextUnquotedValue();
        } else if (i == 13) {
            str = nextQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (i == 12) {
            str = nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else {
            if (i != 15) {
                throw new JsonDataException("Expected a name but was " + peek() + " at path " + getPath());
            }
            str = this.peekedString;
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = str;
        return str;
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final String nextString() throws IOException {
        String readUtf8;
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 10) {
            readUtf8 = nextUnquotedValue();
        } else if (i == 9) {
            readUtf8 = nextQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (i == 8) {
            readUtf8 = nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (i == 11) {
            readUtf8 = this.peekedString;
            this.peekedString = null;
        } else if (i == 16) {
            readUtf8 = Long.toString(this.peekedLong);
        } else {
            if (i != 17) {
                throw new JsonDataException("Expected a string but was " + peek() + " at path " + getPath());
            }
            readUtf8 = this.buffer.readUtf8(this.peekedNumberLength);
        }
        this.peeked = 0;
        int[] iArr = this.pathIndices;
        int i2 = this.stackSize - 1;
        iArr[i2] = iArr[i2] + 1;
        return readUtf8;
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final JsonReader.Token peek() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        switch (i) {
            case 1:
                return JsonReader.Token.BEGIN_OBJECT;
            case 2:
                return JsonReader.Token.END_OBJECT;
            case 3:
                return JsonReader.Token.BEGIN_ARRAY;
            case 4:
                return JsonReader.Token.END_ARRAY;
            case 5:
            case 6:
                return JsonReader.Token.BOOLEAN;
            case 7:
                return JsonReader.Token.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return JsonReader.Token.STRING;
            case 12:
            case 13:
            case 14:
            case 15:
                return JsonReader.Token.NAME;
            case 16:
            case 17:
                return JsonReader.Token.NUMBER;
            case 18:
                return JsonReader.Token.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final int selectName(JsonReader.Options options) throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i < 12 || i > 15) {
            return -1;
        }
        if (i == 15) {
            return findName(this.peekedString, options);
        }
        int select = this.source.select(options.doubleQuoteSuffix);
        if (select != -1) {
            this.peeked = 0;
            this.pathNames[this.stackSize - 1] = options.strings[select];
            return select;
        }
        String str = this.pathNames[this.stackSize - 1];
        String nextName = nextName();
        int findName = findName(nextName, options);
        if (findName == -1) {
            this.peeked = 15;
            this.peekedString = nextName;
            this.pathNames[this.stackSize - 1] = str;
        }
        return findName;
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final void skipName() throws IOException {
        int i = this.peeked;
        if (i == 0) {
            i = doPeek();
        }
        if (i == 14) {
            long indexOfElement = this.source.indexOfElement(UNQUOTED_STRING_TERMINALS);
            Buffer buffer = this.buffer;
            if (indexOfElement == -1) {
                indexOfElement = buffer.size();
            }
            buffer.skip(indexOfElement);
        } else if (i == 13) {
            skipQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (i == 12) {
            skipQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (i != 15) {
            throw new JsonDataException("Expected a name but was " + peek() + " at path " + getPath());
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = "null";
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final void skipValue() throws IOException {
        int i = 0;
        do {
            int i2 = this.peeked;
            if (i2 == 0) {
                i2 = doPeek();
            }
            if (i2 == 3) {
                pushScope(1);
            } else if (i2 == 1) {
                pushScope(3);
            } else {
                if (i2 == 4) {
                    i--;
                    if (i < 0) {
                        throw new JsonDataException("Expected a value but was " + peek() + " at path " + getPath());
                    }
                    this.stackSize--;
                } else if (i2 == 2) {
                    i--;
                    if (i < 0) {
                        throw new JsonDataException("Expected a value but was " + peek() + " at path " + getPath());
                    }
                    this.stackSize--;
                } else if (i2 == 14 || i2 == 10) {
                    long indexOfElement = this.source.indexOfElement(UNQUOTED_STRING_TERMINALS);
                    Buffer buffer = this.buffer;
                    if (indexOfElement == -1) {
                        indexOfElement = buffer.size();
                    }
                    buffer.skip(indexOfElement);
                } else if (i2 == 9 || i2 == 13) {
                    skipQuotedValue(DOUBLE_QUOTE_OR_SLASH);
                } else if (i2 == 8 || i2 == 12) {
                    skipQuotedValue(SINGLE_QUOTE_OR_SLASH);
                } else if (i2 == 17) {
                    this.buffer.skip(this.peekedNumberLength);
                } else if (i2 == 18) {
                    throw new JsonDataException("Expected a value but was " + peek() + " at path " + getPath());
                }
                this.peeked = 0;
            }
            i++;
            this.peeked = 0;
        } while (i != 0);
        int[] iArr = this.pathIndices;
        int i3 = this.stackSize;
        int i4 = i3 - 1;
        iArr[i4] = iArr[i4] + 1;
        this.pathNames[i3 - 1] = "null";
    }

    public final String toString() {
        return "JsonReader(" + this.source + ")";
    }
}
