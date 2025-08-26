package com.airbnb.lottie.parser.moshi;

import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.EOFException;
import java.io.IOException;
import kotlin.text.Charsets;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;

/* loaded from: classes.dex */
public final class JsonUtf8Reader extends JsonReader {
    public static final ByteString DOUBLE_QUOTE_OR_SLASH;
    public static final ByteString SINGLE_QUOTE_OR_SLASH;
    public static final ByteString UNQUOTED_STRING_TERMINALS;
    public final Buffer buffer;
    public int peeked = 0;
    public long peekedLong;
    public int peekedNumberLength;
    public String peekedString;
    public final BufferedSource source;

    static {
        ByteString.Companion.getClass();
        SINGLE_QUOTE_OR_SLASH = ByteString.Companion.encodeUtf8("'\\");
        DOUBLE_QUOTE_OR_SLASH = ByteString.Companion.encodeUtf8("\"\\");
        UNQUOTED_STRING_TERMINALS = ByteString.Companion.encodeUtf8("{}[]:, \n\t\r\f/\\;#=");
        ByteString.Companion.encodeUtf8("\n\r");
        ByteString.Companion.encodeUtf8("*/");
    }

    public JsonUtf8Reader(BufferedSource bufferedSource) {
        if (bufferedSource == null) {
            throw new NullPointerException("source == null");
        }
        this.source = bufferedSource;
        this.buffer = bufferedSource.buffer();
        pushScope(6);
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final void beginArray() {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 3) {
            pushScope(1);
            this.pathIndices[this.stackSize - 1] = 0;
            this.peeked = 0;
        } else {
            throw new JsonDataException("Expected BEGIN_ARRAY but was " + peek() + " at path " + getPath());
        }
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final void beginObject() {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 1) {
            pushScope(3);
            this.peeked = 0;
        } else {
            throw new JsonDataException("Expected BEGIN_OBJECT but was " + peek() + " at path " + getPath());
        }
    }

    public final void checkLenient() throws JsonEncodingException {
        syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        throw null;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        this.peeked = 0;
        this.scopes[0] = 8;
        this.stackSize = 1;
        Buffer buffer = this.buffer;
        buffer.skip(buffer.size);
        this.source.close();
    }

    /* JADX WARN: Code restructure failed: missing block: B:135:0x01a9, code lost:
    
        if (isLiteral(r9) != false) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x01ab, code lost:
    
        if (r1 != 2) goto L148;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x01ad, code lost:
    
        if (r4 == false) goto L148;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x01b3, code lost:
    
        if (r12 != Long.MIN_VALUE) goto L141;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x01b5, code lost:
    
        if (r7 == false) goto L148;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x01b9, code lost:
    
        if (r12 != r17) goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x01bb, code lost:
    
        if (r7 != false) goto L148;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x01bd, code lost:
    
        if (r7 == false) goto L146;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x01c0, code lost:
    
        r12 = -r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x01c1, code lost:
    
        r20.peekedLong = r12;
        r20.buffer.skip(r2);
        r9 = 16;
        r20.peeked = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x01ce, code lost:
    
        if (r1 == 2) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x01d1, code lost:
    
        if (r1 == 4) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x01d4, code lost:
    
        if (r1 != 7) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x01d6, code lost:
    
        r20.peekedNumberLength = r2;
        r9 = 17;
        r20.peeked = 17;
     */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0203 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0204  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0128 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0129  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int doPeek() throws JsonEncodingException, EOFException {
        String str;
        String str2;
        int i;
        long j;
        byte b;
        int i2;
        char c;
        int[] iArr = this.scopes;
        int i3 = this.stackSize - 1;
        int i4 = iArr[i3];
        if (i4 == 1) {
            iArr[i3] = 2;
        } else if (i4 == 2) {
            int iNextNonWhitespace = nextNonWhitespace(true);
            this.buffer.readByte();
            if (iNextNonWhitespace != 44) {
                if (iNextNonWhitespace == 59) {
                    checkLenient();
                    throw null;
                }
                if (iNextNonWhitespace == 93) {
                    this.peeked = 4;
                    return 4;
                }
                syntaxError("Unterminated array");
                throw null;
            }
        } else {
            if (i4 == 3 || i4 == 5) {
                iArr[i3] = 4;
                if (i4 == 5) {
                    int iNextNonWhitespace2 = nextNonWhitespace(true);
                    this.buffer.readByte();
                    if (iNextNonWhitespace2 != 44) {
                        if (iNextNonWhitespace2 == 59) {
                            checkLenient();
                            throw null;
                        }
                        if (iNextNonWhitespace2 == 125) {
                            this.peeked = 2;
                            return 2;
                        }
                        syntaxError("Unterminated object");
                        throw null;
                    }
                }
                int iNextNonWhitespace3 = nextNonWhitespace(true);
                if (iNextNonWhitespace3 == 34) {
                    this.buffer.readByte();
                    this.peeked = 13;
                    return 13;
                }
                if (iNextNonWhitespace3 == 39) {
                    this.buffer.readByte();
                    checkLenient();
                    throw null;
                }
                if (iNextNonWhitespace3 != 125) {
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
                iArr[i3] = 5;
                int iNextNonWhitespace4 = nextNonWhitespace(true);
                this.buffer.readByte();
                if (iNextNonWhitespace4 != 58) {
                    if (iNextNonWhitespace4 != 61) {
                        syntaxError("Expected ':'");
                        throw null;
                    }
                    checkLenient();
                    throw null;
                }
            } else if (i4 == 6) {
                iArr[i3] = 7;
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
        int iNextNonWhitespace5 = nextNonWhitespace(true);
        if (iNextNonWhitespace5 == 34) {
            this.buffer.readByte();
            this.peeked = 9;
            return 9;
        }
        if (iNextNonWhitespace5 == 39) {
            checkLenient();
            throw null;
        }
        if (iNextNonWhitespace5 != 44 && iNextNonWhitespace5 != 59) {
            if (iNextNonWhitespace5 == 91) {
                this.buffer.readByte();
                this.peeked = 3;
                return 3;
            }
            if (iNextNonWhitespace5 != 93) {
                if (iNextNonWhitespace5 == 123) {
                    this.buffer.readByte();
                    this.peeked = 1;
                    return 1;
                }
                long j2 = 0;
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
                    j = 0;
                    i = 0;
                    if (i == 0) {
                        return i;
                    }
                    boolean z = true;
                    long j3 = j;
                    char c2 = 0;
                    int i5 = 0;
                    boolean z2 = false;
                    while (true) {
                        int i6 = i5 + 1;
                        if (!this.source.request(i6)) {
                            break;
                        }
                        byte b3 = this.buffer.getByte(i5);
                        if (b3 != 43) {
                            if (b3 == 69 || b3 == 101) {
                                c = 6;
                                if (c2 != 2 && c2 != 4) {
                                    break;
                                }
                                c2 = 5;
                                i5 = i6;
                            } else if (b3 == 45) {
                                c = 6;
                                if (c2 == 0) {
                                    c2 = 1;
                                    z2 = true;
                                    i5 = i6;
                                } else {
                                    if (c2 != 5) {
                                        break;
                                    }
                                    c2 = c;
                                    i5 = i6;
                                }
                            } else if (b3 == 46) {
                                c = 6;
                                if (c2 != 2) {
                                    break;
                                }
                                c2 = 3;
                                i5 = i6;
                            } else {
                                if (b3 < 48 || b3 > 57) {
                                    break;
                                }
                                if (c2 == 1 || c2 == 0) {
                                    c = 6;
                                    j3 = -(b3 - 48);
                                    c2 = 2;
                                } else {
                                    if (c2 == 2) {
                                        if (j3 == j) {
                                            break;
                                        }
                                        long j4 = (10 * j3) - (b3 - 48);
                                        z &= j3 > -922337203685477580L || (j3 == -922337203685477580L && j4 < j3);
                                        j3 = j4;
                                    } else if (c2 == 3) {
                                        c2 = 4;
                                    } else {
                                        c = 6;
                                        if (c2 == 5 || c2 == 6) {
                                            c2 = 7;
                                        }
                                    }
                                    c = 6;
                                    i5 = i6;
                                }
                                i5 = i6;
                            }
                            if (i2 == 0) {
                                return i2;
                            }
                            if (isLiteral(this.buffer.getByte(j))) {
                                checkLenient();
                                throw null;
                            }
                            syntaxError("Expected value");
                            throw null;
                        }
                        c = 6;
                        if (c2 != 5) {
                            break;
                        }
                        c2 = c;
                        i5 = i6;
                    }
                    i2 = 0;
                    if (i2 == 0) {
                    }
                }
                int length = str.length();
                int i7 = 1;
                while (true) {
                    if (i7 < length) {
                        j = j2;
                        int i8 = i7 + 1;
                        if (!this.source.request(i8) || ((b = this.buffer.getByte(i7)) != str.charAt(i7) && b != str2.charAt(i7))) {
                            break;
                        }
                        i7 = i8;
                        j2 = j;
                    } else {
                        j = j2;
                        if (!this.source.request(length + 1) || !isLiteral(this.buffer.getByte(length))) {
                            this.buffer.skip(length);
                            this.peeked = i;
                        }
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

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final void endArray() {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek != 4) {
            throw new JsonDataException("Expected END_ARRAY but was " + peek() + " at path " + getPath());
        }
        int i = this.stackSize;
        this.stackSize = i - 1;
        int[] iArr = this.pathIndices;
        int i2 = i - 2;
        iArr[i2] = iArr[i2] + 1;
        this.peeked = 0;
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final void endObject() {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek != 2) {
            throw new JsonDataException("Expected END_OBJECT but was " + peek() + " at path " + getPath());
        }
        int i = this.stackSize;
        int i2 = i - 1;
        this.stackSize = i2;
        this.pathNames[i2] = null;
        int[] iArr = this.pathIndices;
        int i3 = i - 2;
        iArr[i3] = iArr[i3] + 1;
        this.peeked = 0;
    }

    public final int findName(String str, JsonReader.Options options) {
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

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final boolean hasNext() throws JsonEncodingException, EOFException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        return (iDoPeek == 2 || iDoPeek == 4 || iDoPeek == 18) ? false : true;
    }

    public final boolean isLiteral(int i) throws JsonEncodingException {
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

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final boolean nextBoolean() throws JsonEncodingException, EOFException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 5) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return true;
        }
        if (iDoPeek == 6) {
            this.peeked = 0;
            int[] iArr2 = this.pathIndices;
            int i2 = this.stackSize - 1;
            iArr2[i2] = iArr2[i2] + 1;
            return false;
        }
        throw new JsonDataException("Expected a boolean but was " + peek() + " at path " + getPath());
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final double nextDouble() {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 16) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return this.peekedLong;
        }
        if (iDoPeek == 17) {
            Buffer buffer = this.buffer;
            long j = this.peekedNumberLength;
            buffer.getClass();
            this.peekedString = buffer.readString(j, Charsets.UTF_8);
        } else if (iDoPeek == 9) {
            this.peekedString = nextQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (iDoPeek == 8) {
            this.peekedString = nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (iDoPeek == 10) {
            this.peekedString = nextUnquotedValue();
        } else if (iDoPeek != 11) {
            throw new JsonDataException("Expected a double but was " + peek() + " at path " + getPath());
        }
        this.peeked = 11;
        try {
            double d = Double.parseDouble(this.peekedString);
            if (Double.isNaN(d) || Double.isInfinite(d)) {
                throw new JsonEncodingException("JSON forbids NaN and infinities: " + d + " at path " + getPath());
            }
            this.peekedString = null;
            this.peeked = 0;
            int[] iArr2 = this.pathIndices;
            int i2 = this.stackSize - 1;
            iArr2[i2] = iArr2[i2] + 1;
            return d;
        } catch (NumberFormatException unused) {
            throw new JsonDataException("Expected a double but was " + this.peekedString + " at path " + getPath());
        }
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final int nextInt() {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 16) {
            long j = this.peekedLong;
            int i = (int) j;
            if (j == i) {
                this.peeked = 0;
                int[] iArr = this.pathIndices;
                int i2 = this.stackSize - 1;
                iArr[i2] = iArr[i2] + 1;
                return i;
            }
            throw new JsonDataException("Expected an int but was " + this.peekedLong + " at path " + getPath());
        }
        if (iDoPeek == 17) {
            Buffer buffer = this.buffer;
            long j2 = this.peekedNumberLength;
            buffer.getClass();
            this.peekedString = buffer.readString(j2, Charsets.UTF_8);
        } else if (iDoPeek == 9 || iDoPeek == 8) {
            String strNextQuotedValue = iDoPeek == 9 ? nextQuotedValue(DOUBLE_QUOTE_OR_SLASH) : nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
            this.peekedString = strNextQuotedValue;
            try {
                int i3 = Integer.parseInt(strNextQuotedValue);
                this.peeked = 0;
                int[] iArr2 = this.pathIndices;
                int i4 = this.stackSize - 1;
                iArr2[i4] = iArr2[i4] + 1;
                return i3;
            } catch (NumberFormatException unused) {
            }
        } else if (iDoPeek != 11) {
            throw new JsonDataException("Expected an int but was " + peek() + " at path " + getPath());
        }
        this.peeked = 11;
        try {
            double d = Double.parseDouble(this.peekedString);
            int i5 = (int) d;
            if (i5 != d) {
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

    public final String nextName() throws JsonEncodingException, EOFException {
        String strNextQuotedValue;
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 14) {
            strNextQuotedValue = nextUnquotedValue();
        } else if (iDoPeek == 13) {
            strNextQuotedValue = nextQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (iDoPeek == 12) {
            strNextQuotedValue = nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else {
            if (iDoPeek != 15) {
                throw new JsonDataException("Expected a name but was " + peek() + " at path " + getPath());
            }
            strNextQuotedValue = this.peekedString;
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = strNextQuotedValue;
        return strNextQuotedValue;
    }

    public final int nextNonWhitespace(boolean z) throws JsonEncodingException, EOFException {
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (!this.source.request(i2)) {
                if (z) {
                    throw new EOFException("End of input");
                }
                return -1;
            }
            long j = i;
            byte b = this.buffer.getByte(j);
            if (b != 10 && b != 32 && b != 13 && b != 9) {
                this.buffer.skip(j);
                if (b == 47) {
                    if (this.source.request(2L)) {
                        checkLenient();
                        throw null;
                    }
                } else if (b == 35) {
                    checkLenient();
                    throw null;
                }
                return b;
            }
            i = i2;
        }
    }

    public final String nextQuotedValue(ByteString byteString) throws JsonEncodingException, EOFException {
        StringBuilder sb = null;
        while (true) {
            long jIndexOfElement = this.source.indexOfElement(byteString);
            if (jIndexOfElement == -1) {
                syntaxError("Unterminated string");
                throw null;
            }
            if (this.buffer.getByte(jIndexOfElement) != 92) {
                if (sb == null) {
                    Buffer buffer = this.buffer;
                    buffer.getClass();
                    String string = buffer.readString(jIndexOfElement, Charsets.UTF_8);
                    this.buffer.readByte();
                    return string;
                }
                Buffer buffer2 = this.buffer;
                buffer2.getClass();
                sb.append(buffer2.readString(jIndexOfElement, Charsets.UTF_8));
                this.buffer.readByte();
                return sb.toString();
            }
            if (sb == null) {
                sb = new StringBuilder();
            }
            Buffer buffer3 = this.buffer;
            buffer3.getClass();
            sb.append(buffer3.readString(jIndexOfElement, Charsets.UTF_8));
            this.buffer.readByte();
            sb.append(readEscapeCharacter());
        }
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final String nextString() {
        String string;
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 10) {
            string = nextUnquotedValue();
        } else if (iDoPeek == 9) {
            string = nextQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (iDoPeek == 8) {
            string = nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (iDoPeek == 11) {
            string = this.peekedString;
            this.peekedString = null;
        } else if (iDoPeek == 16) {
            string = Long.toString(this.peekedLong);
        } else {
            if (iDoPeek != 17) {
                throw new JsonDataException("Expected a string but was " + peek() + " at path " + getPath());
            }
            Buffer buffer = this.buffer;
            long j = this.peekedNumberLength;
            buffer.getClass();
            string = buffer.readString(j, Charsets.UTF_8);
        }
        this.peeked = 0;
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        return string;
    }

    public final String nextUnquotedValue() {
        long jIndexOfElement = this.source.indexOfElement(UNQUOTED_STRING_TERMINALS);
        Buffer buffer = this.buffer;
        if (jIndexOfElement == -1) {
            return buffer.readString(buffer.size, Charsets.UTF_8);
        }
        buffer.getClass();
        return buffer.readString(jIndexOfElement, Charsets.UTF_8);
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final JsonReader.Token peek() throws JsonEncodingException, EOFException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        switch (iDoPeek) {
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

    public final char readEscapeCharacter() throws JsonEncodingException, EOFException {
        int i;
        if (!this.source.request(1L)) {
            syntaxError("Unterminated escape sequence");
            throw null;
        }
        byte b = this.buffer.readByte();
        if (b == 10 || b == 34 || b == 39 || b == 47 || b == 92) {
            return (char) b;
        }
        if (b == 98) {
            return '\b';
        }
        if (b == 102) {
            return '\f';
        }
        if (b == 110) {
            return '\n';
        }
        if (b == 114) {
            return '\r';
        }
        if (b == 116) {
            return '\t';
        }
        if (b != 117) {
            syntaxError("Invalid escape sequence: \\" + ((char) b));
            throw null;
        }
        if (!this.source.request(4L)) {
            throw new EOFException("Unterminated escape sequence at path " + getPath());
        }
        char c = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            byte b2 = this.buffer.getByte(i2);
            char c2 = (char) (c << 4);
            if (b2 >= 48 && b2 <= 57) {
                i = b2 - 48;
            } else if (b2 >= 97 && b2 <= 102) {
                i = b2 - 87;
            } else {
                if (b2 < 65 || b2 > 70) {
                    Buffer buffer = this.buffer;
                    buffer.getClass();
                    syntaxError("\\u".concat(buffer.readString(4L, Charsets.UTF_8)));
                    throw null;
                }
                i = b2 - 55;
            }
            c = (char) (i + c2);
        }
        this.buffer.skip(4L);
        return c;
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final int selectName(JsonReader.Options options) {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek < 12 || iDoPeek > 15) {
            return -1;
        }
        if (iDoPeek == 15) {
            return findName(this.peekedString, options);
        }
        int iSelect = this.source.select(options.doubleQuoteSuffix);
        if (iSelect != -1) {
            this.peeked = 0;
            this.pathNames[this.stackSize - 1] = options.strings[iSelect];
            return iSelect;
        }
        String str = this.pathNames[this.stackSize - 1];
        String strNextName = nextName();
        int iFindName = findName(strNextName, options);
        if (iFindName == -1) {
            this.peeked = 15;
            this.peekedString = strNextName;
            this.pathNames[this.stackSize - 1] = str;
        }
        return iFindName;
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final void skipName() {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 14) {
            long jIndexOfElement = this.source.indexOfElement(UNQUOTED_STRING_TERMINALS);
            Buffer buffer = this.buffer;
            if (jIndexOfElement == -1) {
                jIndexOfElement = buffer.size;
            }
            buffer.skip(jIndexOfElement);
        } else if (iDoPeek == 13) {
            skipQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (iDoPeek == 12) {
            skipQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (iDoPeek != 15) {
            throw new JsonDataException("Expected a name but was " + peek() + " at path " + getPath());
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = "null";
    }

    public final void skipQuotedValue(ByteString byteString) throws JsonEncodingException, EOFException {
        while (true) {
            long jIndexOfElement = this.source.indexOfElement(byteString);
            if (jIndexOfElement == -1) {
                syntaxError("Unterminated string");
                throw null;
            }
            if (this.buffer.getByte(jIndexOfElement) != 92) {
                this.buffer.skip(jIndexOfElement + 1);
                return;
            } else {
                this.buffer.skip(jIndexOfElement + 1);
                readEscapeCharacter();
            }
        }
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public final void skipValue() {
        int i = 0;
        do {
            int iDoPeek = this.peeked;
            if (iDoPeek == 0) {
                iDoPeek = doPeek();
            }
            if (iDoPeek == 3) {
                pushScope(1);
            } else if (iDoPeek == 1) {
                pushScope(3);
            } else {
                if (iDoPeek == 4) {
                    i--;
                    if (i < 0) {
                        throw new JsonDataException("Expected a value but was " + peek() + " at path " + getPath());
                    }
                    this.stackSize--;
                } else if (iDoPeek == 2) {
                    i--;
                    if (i < 0) {
                        throw new JsonDataException("Expected a value but was " + peek() + " at path " + getPath());
                    }
                    this.stackSize--;
                } else if (iDoPeek == 14 || iDoPeek == 10) {
                    long jIndexOfElement = this.source.indexOfElement(UNQUOTED_STRING_TERMINALS);
                    Buffer buffer = this.buffer;
                    if (jIndexOfElement == -1) {
                        jIndexOfElement = buffer.size;
                    }
                    buffer.skip(jIndexOfElement);
                } else if (iDoPeek == 9 || iDoPeek == 13) {
                    skipQuotedValue(DOUBLE_QUOTE_OR_SLASH);
                } else if (iDoPeek == 8 || iDoPeek == 12) {
                    skipQuotedValue(SINGLE_QUOTE_OR_SLASH);
                } else if (iDoPeek == 17) {
                    this.buffer.skip(this.peekedNumberLength);
                } else if (iDoPeek == 18) {
                    throw new JsonDataException("Expected a value but was " + peek() + " at path " + getPath());
                }
                this.peeked = 0;
            }
            i++;
            this.peeked = 0;
        } while (i != 0);
        int[] iArr = this.pathIndices;
        int i2 = this.stackSize - 1;
        iArr[i2] = iArr[i2] + 1;
        this.pathNames[i2] = "null";
    }

    public final String toString() {
        return "JsonReader(" + this.source + ")";
    }
}
