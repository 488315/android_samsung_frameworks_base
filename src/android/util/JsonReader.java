package android.util;

import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.StringPool;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class JsonReader implements Closeable {
    private static final String FALSE = "false";
    private static final String TRUE = "true";
    private final Reader in;
    private String name;
    private boolean skipping;
    private JsonToken token;
    private String value;
    private int valueLength;
    private int valuePos;
    private final StringPool stringPool = new StringPool();
    private boolean lenient = false;
    private final char[] buffer = new char[1024];
    private int pos = 0;
    private int limit = 0;
    private int bufferStartLine = 1;
    private int bufferStartColumn = 1;
    private final List<JsonScope> stack = new ArrayList();

    public JsonReader(Reader reader) {
        push(JsonScope.EMPTY_DOCUMENT);
        this.skipping = false;
        if (reader == null) {
            throw new NullPointerException("in == null");
        }
        this.in = reader;
    }

    public void setLenient(boolean z) {
        this.lenient = z;
    }

    public boolean isLenient() {
        return this.lenient;
    }

    public void beginArray() throws IOException {
        expect(JsonToken.BEGIN_ARRAY);
    }

    public void endArray() throws IOException {
        expect(JsonToken.END_ARRAY);
    }

    public void beginObject() throws IOException {
        expect(JsonToken.BEGIN_OBJECT);
    }

    public void endObject() throws IOException {
        expect(JsonToken.END_OBJECT);
    }

    private void expect(JsonToken jsonToken) throws IOException {
        peek();
        if (this.token != jsonToken) {
            throw new IllegalStateException("Expected " + jsonToken + " but was " + peek());
        }
        advance();
    }

    public boolean hasNext() throws IOException {
        peek();
        return (this.token == JsonToken.END_OBJECT || this.token == JsonToken.END_ARRAY) ? false : true;
    }

    public JsonToken peek() throws IOException {
        JsonToken jsonToken = this.token;
        if (jsonToken != null) {
            return jsonToken;
        }
        switch (AnonymousClass1.$SwitchMap$android$util$JsonScope[peekStack().ordinal()]) {
            case 1:
                replaceTop(JsonScope.NONEMPTY_DOCUMENT);
                JsonToken nextValue = nextValue();
                if (this.lenient || this.token == JsonToken.BEGIN_ARRAY || this.token == JsonToken.BEGIN_OBJECT) {
                    return nextValue;
                }
                throw new IOException("Expected JSON document to start with '[' or '{' but was " + this.token);
            case 2:
                return nextInArray(true);
            case 3:
                return nextInArray(false);
            case 4:
                return nextInObject(true);
            case 5:
                return objectValue();
            case 6:
                return nextInObject(false);
            case 7:
                try {
                    JsonToken nextValue2 = nextValue();
                    if (this.lenient) {
                        return nextValue2;
                    }
                    throw syntaxError("Expected EOF");
                } catch (EOFException unused) {
                    JsonToken jsonToken2 = JsonToken.END_DOCUMENT;
                    this.token = jsonToken2;
                    return jsonToken2;
                }
            case 8:
                throw new IllegalStateException("JsonReader is closed");
            default:
                throw new AssertionError();
        }
    }

    /* renamed from: android.util.JsonReader$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$util$JsonScope;

        static {
            int[] iArr = new int[JsonScope.values().length];
            $SwitchMap$android$util$JsonScope = iArr;
            try {
                iArr[JsonScope.EMPTY_DOCUMENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$util$JsonScope[JsonScope.EMPTY_ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$util$JsonScope[JsonScope.NONEMPTY_ARRAY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$util$JsonScope[JsonScope.EMPTY_OBJECT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$android$util$JsonScope[JsonScope.DANGLING_NAME.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$android$util$JsonScope[JsonScope.NONEMPTY_OBJECT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$android$util$JsonScope[JsonScope.NONEMPTY_DOCUMENT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$android$util$JsonScope[JsonScope.CLOSED.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    private JsonToken advance() throws IOException {
        peek();
        JsonToken jsonToken = this.token;
        this.token = null;
        this.value = null;
        this.name = null;
        return jsonToken;
    }

    public String nextName() throws IOException {
        peek();
        if (this.token != JsonToken.NAME) {
            throw new IllegalStateException("Expected a name but was " + peek());
        }
        String str = this.name;
        advance();
        return str;
    }

    public String nextString() throws IOException {
        peek();
        if (this.token != JsonToken.STRING && this.token != JsonToken.NUMBER) {
            throw new IllegalStateException("Expected a string but was " + peek());
        }
        String str = this.value;
        advance();
        return str;
    }

    public boolean nextBoolean() throws IOException {
        peek();
        if (this.token != JsonToken.BOOLEAN) {
            throw new IllegalStateException("Expected a boolean but was " + this.token);
        }
        boolean z = this.value == "true";
        advance();
        return z;
    }

    public void nextNull() throws IOException {
        peek();
        if (this.token != JsonToken.NULL) {
            throw new IllegalStateException("Expected null but was " + this.token);
        }
        advance();
    }

    public double nextDouble() throws IOException {
        peek();
        if (this.token != JsonToken.STRING && this.token != JsonToken.NUMBER) {
            throw new IllegalStateException("Expected a double but was " + this.token);
        }
        double parseDouble = Double.parseDouble(this.value);
        advance();
        return parseDouble;
    }

    public long nextLong() throws IOException {
        long j;
        peek();
        if (this.token != JsonToken.STRING && this.token != JsonToken.NUMBER) {
            throw new IllegalStateException("Expected a long but was " + this.token);
        }
        try {
            j = Long.parseLong(this.value);
        } catch (NumberFormatException unused) {
            double parseDouble = Double.parseDouble(this.value);
            long j2 = (long) parseDouble;
            if (j2 != parseDouble) {
                throw new NumberFormatException(this.value);
            }
            j = j2;
        }
        advance();
        return j;
    }

    public int nextInt() throws IOException {
        int i;
        peek();
        if (this.token != JsonToken.STRING && this.token != JsonToken.NUMBER) {
            throw new IllegalStateException("Expected an int but was " + this.token);
        }
        try {
            i = Integer.parseInt(this.value);
        } catch (NumberFormatException unused) {
            double parseDouble = Double.parseDouble(this.value);
            int i2 = (int) parseDouble;
            if (i2 != parseDouble) {
                throw new NumberFormatException(this.value);
            }
            i = i2;
        }
        advance();
        return i;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.value = null;
        this.token = null;
        this.stack.clear();
        this.stack.add(JsonScope.CLOSED);
        this.in.close();
    }

    public void skipValue() throws IOException {
        this.skipping = true;
        try {
            if (!hasNext() || peek() == JsonToken.END_DOCUMENT) {
                throw new IllegalStateException("No element left to skip");
            }
            int i = 0;
            do {
                JsonToken advance = advance();
                if (advance != JsonToken.BEGIN_ARRAY && advance != JsonToken.BEGIN_OBJECT) {
                    if (advance == JsonToken.END_ARRAY || advance == JsonToken.END_OBJECT) {
                        i--;
                    }
                }
                i++;
            } while (i != 0);
        } finally {
            this.skipping = false;
        }
    }

    private JsonScope peekStack() {
        return this.stack.get(r1.size() - 1);
    }

    private JsonScope pop() {
        return this.stack.remove(r1.size() - 1);
    }

    private void push(JsonScope jsonScope) {
        this.stack.add(jsonScope);
    }

    private void replaceTop(JsonScope jsonScope) {
        this.stack.set(r1.size() - 1, jsonScope);
    }

    private JsonToken nextInArray(boolean z) throws IOException {
        if (z) {
            replaceTop(JsonScope.NONEMPTY_ARRAY);
        } else {
            int nextNonWhitespace = nextNonWhitespace();
            if (nextNonWhitespace != 44) {
                if (nextNonWhitespace != 59) {
                    if (nextNonWhitespace == 93) {
                        pop();
                        JsonToken jsonToken = JsonToken.END_ARRAY;
                        this.token = jsonToken;
                        return jsonToken;
                    }
                    throw syntaxError("Unterminated array");
                }
                checkLenient();
            }
        }
        int nextNonWhitespace2 = nextNonWhitespace();
        if (nextNonWhitespace2 != 44 && nextNonWhitespace2 != 59) {
            if (nextNonWhitespace2 != 93) {
                this.pos--;
                return nextValue();
            }
            if (z) {
                pop();
                JsonToken jsonToken2 = JsonToken.END_ARRAY;
                this.token = jsonToken2;
                return jsonToken2;
            }
        }
        checkLenient();
        this.pos--;
        this.value = PerfettoProtoLogImpl.NULL_STRING;
        JsonToken jsonToken3 = JsonToken.NULL;
        this.token = jsonToken3;
        return jsonToken3;
    }

    private JsonToken nextInObject(boolean z) throws IOException {
        if (z) {
            if (nextNonWhitespace() == 125) {
                pop();
                JsonToken jsonToken = JsonToken.END_OBJECT;
                this.token = jsonToken;
                return jsonToken;
            }
            this.pos--;
        } else {
            int nextNonWhitespace = nextNonWhitespace();
            if (nextNonWhitespace != 44 && nextNonWhitespace != 59) {
                if (nextNonWhitespace == 125) {
                    pop();
                    JsonToken jsonToken2 = JsonToken.END_OBJECT;
                    this.token = jsonToken2;
                    return jsonToken2;
                }
                throw syntaxError("Unterminated object");
            }
        }
        int nextNonWhitespace2 = nextNonWhitespace();
        if (nextNonWhitespace2 != 34) {
            if (nextNonWhitespace2 == 39) {
                checkLenient();
            } else {
                checkLenient();
                this.pos--;
                String nextLiteral = nextLiteral(false);
                this.name = nextLiteral;
                if (nextLiteral.isEmpty()) {
                    throw syntaxError("Expected name");
                }
                replaceTop(JsonScope.DANGLING_NAME);
                JsonToken jsonToken3 = JsonToken.NAME;
                this.token = jsonToken3;
                return jsonToken3;
            }
        }
        this.name = nextString((char) nextNonWhitespace2);
        replaceTop(JsonScope.DANGLING_NAME);
        JsonToken jsonToken32 = JsonToken.NAME;
        this.token = jsonToken32;
        return jsonToken32;
    }

    private JsonToken objectValue() throws IOException {
        int nextNonWhitespace = nextNonWhitespace();
        if (nextNonWhitespace != 58) {
            if (nextNonWhitespace == 61) {
                checkLenient();
                if (this.pos < this.limit || fillBuffer(1)) {
                    char[] cArr = this.buffer;
                    int i = this.pos;
                    if (cArr[i] == '>') {
                        this.pos = i + 1;
                    }
                }
            } else {
                throw syntaxError("Expected ':'");
            }
        }
        replaceTop(JsonScope.NONEMPTY_OBJECT);
        return nextValue();
    }

    private JsonToken nextValue() throws IOException {
        int nextNonWhitespace = nextNonWhitespace();
        if (nextNonWhitespace != 34) {
            if (nextNonWhitespace != 39) {
                if (nextNonWhitespace == 91) {
                    push(JsonScope.EMPTY_ARRAY);
                    JsonToken jsonToken = JsonToken.BEGIN_ARRAY;
                    this.token = jsonToken;
                    return jsonToken;
                }
                if (nextNonWhitespace == 123) {
                    push(JsonScope.EMPTY_OBJECT);
                    JsonToken jsonToken2 = JsonToken.BEGIN_OBJECT;
                    this.token = jsonToken2;
                    return jsonToken2;
                }
                this.pos--;
                return readLiteral();
            }
            checkLenient();
        }
        this.value = nextString((char) nextNonWhitespace);
        JsonToken jsonToken3 = JsonToken.STRING;
        this.token = jsonToken3;
        return jsonToken3;
    }

    private boolean fillBuffer(int i) throws IOException {
        int i2;
        int i3;
        int i4;
        int i5 = 0;
        while (true) {
            i2 = this.pos;
            if (i5 >= i2) {
                break;
            }
            if (this.buffer[i5] == '\n') {
                this.bufferStartLine++;
                this.bufferStartColumn = 1;
            } else {
                this.bufferStartColumn++;
            }
            i5++;
        }
        int i6 = this.limit;
        if (i6 != i2) {
            int i7 = i6 - i2;
            this.limit = i7;
            char[] cArr = this.buffer;
            System.arraycopy(cArr, i2, cArr, 0, i7);
        } else {
            this.limit = 0;
        }
        this.pos = 0;
        do {
            Reader reader = this.in;
            char[] cArr2 = this.buffer;
            int i8 = this.limit;
            int read = reader.read(cArr2, i8, cArr2.length - i8);
            if (read == -1) {
                return false;
            }
            i3 = this.limit + read;
            this.limit = i3;
            if (this.bufferStartLine == 1 && (i4 = this.bufferStartColumn) == 1 && i3 > 0 && this.buffer[0] == 65279) {
                this.pos++;
                this.bufferStartColumn = i4 - 1;
            }
        } while (i3 < i);
        return true;
    }

    private int getLineNumber() {
        int i = this.bufferStartLine;
        for (int i2 = 0; i2 < this.pos; i2++) {
            if (this.buffer[i2] == '\n') {
                i++;
            }
        }
        return i;
    }

    private int getColumnNumber() {
        int i = this.bufferStartColumn;
        for (int i2 = 0; i2 < this.pos; i2++) {
            i = this.buffer[i2] == '\n' ? 1 : i + 1;
        }
        return i;
    }

    private int nextNonWhitespace() throws IOException {
        char c;
        while (true) {
            if (this.pos < this.limit || fillBuffer(1)) {
                char[] cArr = this.buffer;
                int i = this.pos;
                int i2 = i + 1;
                this.pos = i2;
                c = cArr[i];
                if (c != '\t' && c != '\n' && c != '\r' && c != ' ') {
                    if (c != '#') {
                        if (c != '/' || (i2 == this.limit && !fillBuffer(1))) {
                            break;
                        }
                        checkLenient();
                        char[] cArr2 = this.buffer;
                        int i3 = this.pos;
                        char c2 = cArr2[i3];
                        if (c2 == '*') {
                            this.pos = i3 + 1;
                            if (!skipTo("*/")) {
                                throw syntaxError("Unterminated comment");
                            }
                            this.pos += 2;
                        } else {
                            if (c2 != '/') {
                                break;
                            }
                            this.pos = i3 + 1;
                            skipToEndOfLine();
                        }
                    } else {
                        checkLenient();
                        skipToEndOfLine();
                    }
                }
            } else {
                throw new EOFException("End of input");
            }
        }
        return c;
    }

    private void checkLenient() throws IOException {
        if (!this.lenient) {
            throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    private void skipToEndOfLine() throws IOException {
        char c;
        do {
            if (this.pos >= this.limit && !fillBuffer(1)) {
                return;
            }
            char[] cArr = this.buffer;
            int i = this.pos;
            this.pos = i + 1;
            c = cArr[i];
            if (c == '\r') {
                return;
            }
        } while (c != '\n');
    }

    private boolean skipTo(String str) throws IOException {
        while (true) {
            if (this.pos + str.length() > this.limit && !fillBuffer(str.length())) {
                return false;
            }
            for (int i = 0; i < str.length(); i++) {
                if (this.buffer[this.pos + i] != str.charAt(i)) {
                    break;
                }
            }
            return true;
            this.pos++;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x004f, code lost:
    
        if (r0 != null) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0051, code lost:
    
        r0 = new java.lang.StringBuilder();
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0056, code lost:
    
        r0.append(r6.buffer, r1, r6.pos - r1);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String nextString(char r7) throws java.io.IOException {
        /*
            r6 = this;
            r0 = 0
        L1:
            int r1 = r6.pos
        L3:
            int r2 = r6.pos
            int r3 = r6.limit
            r4 = 1
            if (r2 >= r3) goto L4f
            char[] r3 = r6.buffer
            int r5 = r2 + 1
            r6.pos = r5
            char r2 = r3[r2]
            if (r2 != r7) goto L31
            boolean r7 = r6.skipping
            if (r7 == 0) goto L1c
            java.lang.String r6 = "skipped!"
            return r6
        L1c:
            if (r0 != 0) goto L27
            com.android.internal.util.StringPool r6 = r6.stringPool
            int r5 = r5 - r1
            int r5 = r5 - r4
            java.lang.String r6 = r6.get(r3, r1, r5)
            return r6
        L27:
            int r5 = r5 - r1
            int r5 = r5 - r4
            r0.append(r3, r1, r5)
            java.lang.String r6 = r0.toString()
            return r6
        L31:
            r3 = 92
            if (r2 != r3) goto L3
            if (r0 != 0) goto L3c
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
        L3c:
            char[] r2 = r6.buffer
            int r3 = r6.pos
            int r3 = r3 - r1
            int r3 = r3 - r4
            r0.append(r2, r1, r3)
            char r1 = r6.readEscapeCharacter()
            r0.append(r1)
            int r1 = r6.pos
            goto L3
        L4f:
            if (r0 != 0) goto L56
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
        L56:
            char[] r2 = r6.buffer
            int r3 = r6.pos
            int r3 = r3 - r1
            r0.append(r2, r1, r3)
            boolean r1 = r6.fillBuffer(r4)
            if (r1 == 0) goto L65
            goto L1
        L65:
            java.lang.String r7 = "Unterminated string"
            java.io.IOException r6 = r6.syntaxError(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.JsonReader.nextString(char):java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:66:0x0050, code lost:
    
        checkLenient();
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:62:0x004a. Please report as an issue. */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String nextLiteral(boolean r8) throws java.io.IOException {
        /*
            r7 = this;
            r0 = -1
            r7.valuePos = r0
            r0 = 0
            r7.valueLength = r0
            r1 = 0
            r2 = r0
            r3 = r1
        L9:
            int r4 = r7.pos
            int r5 = r4 + r2
            int r6 = r7.limit
            if (r5 >= r6) goto L54
            char[] r5 = r7.buffer
            int r4 = r4 + r2
            char r4 = r5[r4]
            r5 = 9
            if (r4 == r5) goto L68
            r5 = 10
            if (r4 == r5) goto L68
            r5 = 12
            if (r4 == r5) goto L68
            r5 = 13
            if (r4 == r5) goto L68
            r5 = 32
            if (r4 == r5) goto L68
            r5 = 35
            if (r4 == r5) goto L50
            r5 = 44
            if (r4 == r5) goto L68
            r5 = 47
            if (r4 == r5) goto L50
            r5 = 61
            if (r4 == r5) goto L50
            r5 = 123(0x7b, float:1.72E-43)
            if (r4 == r5) goto L68
            r5 = 125(0x7d, float:1.75E-43)
            if (r4 == r5) goto L68
            r5 = 58
            if (r4 == r5) goto L68
            r5 = 59
            if (r4 == r5) goto L50
            switch(r4) {
                case 91: goto L68;
                case 92: goto L50;
                case 93: goto L68;
                default: goto L4d;
            }
        L4d:
            int r2 = r2 + 1
            goto L9
        L50:
            r7.checkLenient()
            goto L68
        L54:
            char[] r4 = r7.buffer
            int r4 = r4.length
            if (r2 >= r4) goto L6a
            int r4 = r2 + 1
            boolean r4 = r7.fillBuffer(r4)
            if (r4 == 0) goto L62
            goto L9
        L62:
            char[] r4 = r7.buffer
            int r5 = r7.limit
            r4[r5] = r0
        L68:
            r0 = r2
            goto L89
        L6a:
            if (r3 != 0) goto L71
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
        L71:
            char[] r4 = r7.buffer
            int r5 = r7.pos
            r3.append(r4, r5, r2)
            int r4 = r7.valueLength
            int r4 = r4 + r2
            r7.valueLength = r4
            int r4 = r7.pos
            int r4 = r4 + r2
            r7.pos = r4
            r2 = 1
            boolean r2 = r7.fillBuffer(r2)
            if (r2 != 0) goto Lbd
        L89:
            if (r8 == 0) goto L92
            if (r3 != 0) goto L92
            int r8 = r7.pos
            r7.valuePos = r8
            goto Lb2
        L92:
            boolean r8 = r7.skipping
            if (r8 == 0) goto L9a
            java.lang.String r1 = "skipped!"
            goto Lb2
        L9a:
            if (r3 != 0) goto La7
            com.android.internal.util.StringPool r8 = r7.stringPool
            char[] r1 = r7.buffer
            int r2 = r7.pos
            java.lang.String r1 = r8.get(r1, r2, r0)
            goto Lb2
        La7:
            char[] r8 = r7.buffer
            int r1 = r7.pos
            r3.append(r8, r1, r0)
            java.lang.String r1 = r3.toString()
        Lb2:
            int r8 = r7.valueLength
            int r8 = r8 + r0
            r7.valueLength = r8
            int r8 = r7.pos
            int r8 = r8 + r0
            r7.pos = r8
            return r1
        Lbd:
            r2 = r0
            goto L9
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.JsonReader.nextLiteral(boolean):java.lang.String");
    }

    public String toString() {
        return getClass().getSimpleName() + " near " + ((Object) getSnippet());
    }

    private char readEscapeCharacter() throws IOException {
        if (this.pos == this.limit && !fillBuffer(1)) {
            throw syntaxError("Unterminated escape sequence");
        }
        char[] cArr = this.buffer;
        int i = this.pos;
        this.pos = i + 1;
        char c = cArr[i];
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
            return c;
        }
        if (i + 5 > this.limit && !fillBuffer(4)) {
            throw syntaxError("Unterminated escape sequence");
        }
        String str = this.stringPool.get(this.buffer, this.pos, 4);
        this.pos += 4;
        return (char) Integer.parseInt(str, 16);
    }

    private JsonToken readLiteral() throws IOException {
        this.value = nextLiteral(true);
        if (this.valueLength == 0) {
            throw syntaxError("Expected literal value");
        }
        JsonToken decodeLiteral = decodeLiteral();
        this.token = decodeLiteral;
        if (decodeLiteral == JsonToken.STRING) {
            checkLenient();
        }
        return this.token;
    }

    private JsonToken decodeLiteral() throws IOException {
        char[] cArr;
        char c;
        char[] cArr2;
        char c2;
        char[] cArr3;
        char c3;
        int i = this.valuePos;
        if (i == -1) {
            return JsonToken.STRING;
        }
        int i2 = this.valueLength;
        if (i2 == 4 && (('n' == (c3 = (cArr3 = this.buffer)[i]) || 'N' == c3) && (('u' == cArr3[i + 1] || 'U' == cArr3[i + 1]) && (('l' == cArr3[i + 2] || 'L' == cArr3[i + 2]) && ('l' == cArr3[i + 3] || 'L' == cArr3[i + 3]))))) {
            this.value = PerfettoProtoLogImpl.NULL_STRING;
            return JsonToken.NULL;
        }
        if (i2 == 4 && (('t' == (c2 = (cArr2 = this.buffer)[i]) || 'T' == c2) && (('r' == cArr2[i + 1] || 'R' == cArr2[i + 1]) && (('u' == cArr2[i + 2] || 'U' == cArr2[i + 2]) && ('e' == cArr2[i + 3] || 'E' == cArr2[i + 3]))))) {
            this.value = "true";
            return JsonToken.BOOLEAN;
        }
        if (i2 == 5 && (('f' == (c = (cArr = this.buffer)[i]) || 'F' == c) && (('a' == cArr[i + 1] || 'A' == cArr[i + 1]) && (('l' == cArr[i + 2] || 'L' == cArr[i + 2]) && (('s' == cArr[i + 3] || 'S' == cArr[i + 3]) && ('e' == cArr[i + 4] || 'E' == cArr[i + 4])))))) {
            this.value = "false";
            return JsonToken.BOOLEAN;
        }
        this.value = this.stringPool.get(this.buffer, i, i2);
        return decodeNumber(this.buffer, this.valuePos, this.valueLength);
    }

    private JsonToken decodeNumber(char[] cArr, int i, int i2) {
        int i3;
        int i4;
        char c;
        char c2 = cArr[i];
        if (c2 == '-') {
            int i5 = i + 1;
            i3 = i5;
            c2 = cArr[i5];
        } else {
            i3 = i;
        }
        if (c2 == '0') {
            i4 = i3 + 1;
            c = cArr[i4];
        } else if (c2 >= '1' && c2 <= '9') {
            i4 = i3 + 1;
            c = cArr[i4];
            while (c >= '0' && c <= '9') {
                i4++;
                c = cArr[i4];
            }
        } else {
            return JsonToken.STRING;
        }
        if (c == '.') {
            i4++;
            c = cArr[i4];
            while (c >= '0' && c <= '9') {
                i4++;
                c = cArr[i4];
            }
        }
        if (c == 'e' || c == 'E') {
            int i6 = i4 + 1;
            char c3 = cArr[i6];
            if (c3 == '+' || c3 == '-') {
                i6 = i4 + 2;
                c3 = cArr[i6];
            }
            if (c3 >= '0' && c3 <= '9') {
                int i7 = i6 + 1;
                char c4 = cArr[i7];
                i4 = i7;
                while (c4 >= '0' && c4 <= '9') {
                    i4++;
                    c4 = cArr[i4];
                }
            } else {
                return JsonToken.STRING;
            }
        }
        if (i4 == i + i2) {
            return JsonToken.NUMBER;
        }
        return JsonToken.STRING;
    }

    private IOException syntaxError(String str) throws IOException {
        throw new MalformedJsonException(str + " at line " + getLineNumber() + " column " + getColumnNumber());
    }

    private CharSequence getSnippet() {
        StringBuilder sb = new StringBuilder();
        int min = Math.min(this.pos, 20);
        sb.append(this.buffer, this.pos - min, min);
        sb.append(this.buffer, this.pos, Math.min(this.limit - this.pos, 20));
        return sb;
    }
}
