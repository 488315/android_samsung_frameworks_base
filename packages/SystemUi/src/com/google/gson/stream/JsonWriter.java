package com.google.gson.stream;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class JsonWriter implements Closeable, Flushable {
    public static final String[] HTML_SAFE_REPLACEMENT_CHARS;
    public String deferredName;
    public boolean htmlSafe;
    public String indent;
    public boolean lenient;
    public final Writer out;
    public String separator;
    public boolean serializeNulls;
    public int[] stack;
    public int stackSize;
    public static final Pattern VALID_JSON_NUMBER_PATTERN = Pattern.compile("-?(?:0|[1-9][0-9]*)(?:\\.[0-9]+)?(?:[eE][-+]?[0-9]+)?");
    public static final String[] REPLACEMENT_CHARS = new String[128];

    static {
        for (int i = 0; i <= 31; i++) {
            REPLACEMENT_CHARS[i] = String.format("\\u%04x", Integer.valueOf(i));
        }
        String[] strArr = REPLACEMENT_CHARS;
        strArr[34] = "\\\"";
        strArr[92] = "\\\\";
        strArr[9] = "\\t";
        strArr[8] = "\\b";
        strArr[10] = "\\n";
        strArr[13] = "\\r";
        strArr[12] = "\\f";
        String[] strArr2 = (String[]) strArr.clone();
        HTML_SAFE_REPLACEMENT_CHARS = strArr2;
        strArr2[60] = "\\u003c";
        strArr2[62] = "\\u003e";
        strArr2[38] = "\\u0026";
        strArr2[61] = "\\u003d";
        strArr2[39] = "\\u0027";
    }

    public JsonWriter(Writer writer) {
        int[] iArr = new int[32];
        this.stack = iArr;
        this.stackSize = 0;
        if (iArr.length == 0) {
            this.stack = Arrays.copyOf(iArr, 0 * 2);
        }
        int[] iArr2 = this.stack;
        int i = this.stackSize;
        this.stackSize = i + 1;
        iArr2[i] = 6;
        this.separator = ":";
        this.serializeNulls = true;
        if (writer == null) {
            throw new NullPointerException("out == null");
        }
        this.out = writer;
    }

    public final void beforeValue() {
        int peek = peek();
        if (peek == 1) {
            this.stack[this.stackSize - 1] = 2;
            newline();
            return;
        }
        if (peek == 2) {
            this.out.append(',');
            newline();
        } else {
            if (peek == 4) {
                this.out.append((CharSequence) this.separator);
                this.stack[this.stackSize - 1] = 5;
                return;
            }
            if (peek != 6) {
                if (peek != 7) {
                    throw new IllegalStateException("Nesting problem.");
                }
                if (!this.lenient) {
                    throw new IllegalStateException("JSON must have only one top-level value.");
                }
            }
            this.stack[this.stackSize - 1] = 7;
        }
    }

    public void beginArray() {
        writeDeferredName();
        beforeValue();
        int i = this.stackSize;
        int[] iArr = this.stack;
        if (i == iArr.length) {
            this.stack = Arrays.copyOf(iArr, i * 2);
        }
        int[] iArr2 = this.stack;
        int i2 = this.stackSize;
        this.stackSize = i2 + 1;
        iArr2[i2] = 1;
        this.out.write(91);
    }

    public void beginObject() {
        writeDeferredName();
        beforeValue();
        int i = this.stackSize;
        int[] iArr = this.stack;
        if (i == iArr.length) {
            this.stack = Arrays.copyOf(iArr, i * 2);
        }
        int[] iArr2 = this.stack;
        int i2 = this.stackSize;
        this.stackSize = i2 + 1;
        iArr2[i2] = 3;
        this.out.write(123);
    }

    public final void close(int i, int i2, char c) {
        int peek = peek();
        if (peek != i2 && peek != i) {
            throw new IllegalStateException("Nesting problem.");
        }
        if (this.deferredName != null) {
            throw new IllegalStateException("Dangling name: " + this.deferredName);
        }
        this.stackSize--;
        if (peek == i2) {
            newline();
        }
        this.out.write(c);
    }

    public void endArray() {
        close(1, 2, ']');
    }

    public void endObject() {
        close(3, 5, '}');
    }

    public void flush() {
        if (this.stackSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.out.flush();
    }

    public void name(String str) {
        if (str == null) {
            throw new NullPointerException("name == null");
        }
        if (this.deferredName != null) {
            throw new IllegalStateException();
        }
        if (this.stackSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.deferredName = str;
    }

    public final void newline() {
        if (this.indent == null) {
            return;
        }
        this.out.write(10);
        int i = this.stackSize;
        for (int i2 = 1; i2 < i; i2++) {
            this.out.write(this.indent);
        }
    }

    public JsonWriter nullValue() {
        if (this.deferredName != null) {
            if (!this.serializeNulls) {
                this.deferredName = null;
                return this;
            }
            writeDeferredName();
        }
        beforeValue();
        this.out.write("null");
        return this;
    }

    public final int peek() {
        int i = this.stackSize;
        if (i != 0) {
            return this.stack[i - 1];
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void string(String str) {
        int i;
        String str2;
        String[] strArr = this.htmlSafe ? HTML_SAFE_REPLACEMENT_CHARS : REPLACEMENT_CHARS;
        this.out.write(34);
        int length = str.length();
        int i2 = 0;
        while (i < length) {
            char charAt = str.charAt(i);
            if (charAt < 128) {
                str2 = strArr[charAt];
                i = str2 == null ? i + 1 : 0;
                if (i2 < i) {
                    this.out.write(str, i2, i - i2);
                }
                this.out.write(str2);
                i2 = i + 1;
            } else {
                if (charAt == 8232) {
                    str2 = "\\u2028";
                } else if (charAt == 8233) {
                    str2 = "\\u2029";
                }
                if (i2 < i) {
                }
                this.out.write(str2);
                i2 = i + 1;
            }
        }
        if (i2 < length) {
            this.out.write(str, i2, length - i2);
        }
        this.out.write(34);
    }

    public void value(String str) {
        if (str == null) {
            nullValue();
            return;
        }
        writeDeferredName();
        beforeValue();
        string(str);
    }

    public final void writeDeferredName() {
        if (this.deferredName != null) {
            int peek = peek();
            if (peek == 5) {
                this.out.write(44);
            } else if (peek != 3) {
                throw new IllegalStateException("Nesting problem.");
            }
            newline();
            this.stack[this.stackSize - 1] = 4;
            string(this.deferredName);
            this.deferredName = null;
        }
    }

    public void value(boolean z) {
        writeDeferredName();
        beforeValue();
        this.out.write(z ? "true" : "false");
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.out.close();
        int i = this.stackSize;
        if (i <= 1 && (i != 1 || this.stack[i - 1] == 7)) {
            this.stackSize = 0;
            return;
        }
        throw new IOException("Incomplete document");
    }

    public void value(Boolean bool) {
        if (bool == null) {
            nullValue();
            return;
        }
        writeDeferredName();
        beforeValue();
        this.out.write(bool.booleanValue() ? "true" : "false");
    }

    public void value(long j) {
        writeDeferredName();
        beforeValue();
        this.out.write(Long.toString(j));
    }

    public void value(Number number) {
        if (number == null) {
            nullValue();
            return;
        }
        writeDeferredName();
        String obj = number.toString();
        if (!obj.equals("-Infinity") && !obj.equals("Infinity") && !obj.equals("NaN")) {
            Class<?> cls = number.getClass();
            if (!(cls == Integer.class || cls == Long.class || cls == Double.class || cls == Float.class || cls == Byte.class || cls == Short.class || cls == BigDecimal.class || cls == BigInteger.class || cls == AtomicInteger.class || cls == AtomicLong.class) && !VALID_JSON_NUMBER_PATTERN.matcher(obj).matches()) {
                throw new IllegalArgumentException("String created by " + cls + " is not a valid JSON number: " + obj);
            }
        } else if (!this.lenient) {
            throw new IllegalArgumentException("Numeric values must be finite, but was ".concat(obj));
        }
        beforeValue();
        this.out.append((CharSequence) obj);
    }
}
