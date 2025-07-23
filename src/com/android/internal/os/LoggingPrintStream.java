package com.android.internal.os;

import android.provider.Telephony;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.Formatter;
import java.util.Locale;

/* loaded from: classes5.dex */
public abstract class LoggingPrintStream extends PrintStream {
    private final StringBuilder builder;
    private CharBuffer decodedChars;
    private CharsetDecoder decoder;
    private ByteBuffer encodedBytes;
    private final Formatter formatter;

    @Override // java.io.PrintStream
    public boolean checkError() {
        return false;
    }

    @Override // java.io.PrintStream, java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    protected abstract void log(String str);

    @Override // java.io.PrintStream
    protected void setError() {
    }

    protected LoggingPrintStream() {
        super(new OutputStream() { // from class: com.android.internal.os.LoggingPrintStream.1
            @Override // java.io.OutputStream
            public void write(int i) throws IOException {
                throw new AssertionError();
            }
        });
        StringBuilder sb = new StringBuilder();
        this.builder = sb;
        this.formatter = new Formatter(sb, (Locale) null);
    }

    @Override // java.io.PrintStream, java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
    public synchronized void flush() {
        flush(true);
    }

    private void flush(boolean z) {
        int length = this.builder.length();
        int i = 0;
        while (i < length) {
            int indexOf = this.builder.indexOf(ShaderAssembler.NEWLINE, i);
            if (indexOf == -1) {
                break;
            }
            log(this.builder.substring(i, indexOf));
            i = indexOf + 1;
        }
        if (z) {
            if (i < length) {
                log(this.builder.substring(i));
            }
            this.builder.setLength(0);
            return;
        }
        this.builder.delete(0, i);
    }

    @Override // java.io.PrintStream, java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i) {
        write(new byte[]{(byte) i}, 0, 1);
    }

    @Override // java.io.PrintStream, java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr) {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.PrintStream, java.io.FilterOutputStream, java.io.OutputStream
    public synchronized void write(byte[] bArr, int i, int i2) {
        CoderResult decode;
        if (this.decoder == null) {
            this.encodedBytes = ByteBuffer.allocate(80);
            this.decodedChars = CharBuffer.allocate(80);
            this.decoder = Charset.defaultCharset().newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
        }
        int i3 = i2 + i;
        while (i < i3) {
            int min = Math.min(this.encodedBytes.remaining(), i3 - i);
            this.encodedBytes.put(bArr, i, min);
            i += min;
            this.encodedBytes.flip();
            do {
                decode = this.decoder.decode(this.encodedBytes, this.decodedChars, false);
                this.decodedChars.flip();
                this.builder.append((CharSequence) this.decodedChars);
                this.decodedChars.clear();
            } while (decode.isOverflow());
            this.encodedBytes.compact();
        }
        flush(false);
    }

    @Override // java.io.PrintStream
    public PrintStream format(String str, Object... objArr) {
        return format(Locale.getDefault(), str, objArr);
    }

    @Override // java.io.PrintStream
    public PrintStream printf(String str, Object... objArr) {
        return format(str, objArr);
    }

    @Override // java.io.PrintStream
    public PrintStream printf(Locale locale, String str, Object... objArr) {
        return format(locale, str, objArr);
    }

    @Override // java.io.PrintStream
    public synchronized PrintStream format(Locale locale, String str, Object... objArr) {
        if (str == null) {
            throw new NullPointerException(Telephony.CellBroadcasts.MESSAGE_FORMAT);
        }
        this.formatter.format(locale, str, objArr);
        flush(false);
        return this;
    }

    @Override // java.io.PrintStream
    public synchronized void print(char[] cArr) {
        this.builder.append(cArr);
        flush(false);
    }

    @Override // java.io.PrintStream
    public synchronized void print(char c) {
        this.builder.append(c);
        if (c == '\n') {
            flush(false);
        }
    }

    @Override // java.io.PrintStream
    public synchronized void print(double d) {
        this.builder.append(d);
    }

    @Override // java.io.PrintStream
    public synchronized void print(float f) {
        this.builder.append(f);
    }

    @Override // java.io.PrintStream
    public synchronized void print(int i) {
        this.builder.append(i);
    }

    @Override // java.io.PrintStream
    public synchronized void print(long j) {
        this.builder.append(j);
    }

    @Override // java.io.PrintStream
    public synchronized void print(Object obj) {
        this.builder.append(obj);
        flush(false);
    }

    @Override // java.io.PrintStream
    public synchronized void print(String str) {
        this.builder.append(str);
        flush(false);
    }

    @Override // java.io.PrintStream
    public synchronized void print(boolean z) {
        this.builder.append(z);
    }

    @Override // java.io.PrintStream
    public synchronized void println() {
        flush(true);
    }

    @Override // java.io.PrintStream
    public synchronized void println(char[] cArr) {
        this.builder.append(cArr);
        flush(true);
    }

    @Override // java.io.PrintStream
    public synchronized void println(char c) {
        this.builder.append(c);
        flush(true);
    }

    @Override // java.io.PrintStream
    public synchronized void println(double d) {
        this.builder.append(d);
        flush(true);
    }

    @Override // java.io.PrintStream
    public synchronized void println(float f) {
        this.builder.append(f);
        flush(true);
    }

    @Override // java.io.PrintStream
    public synchronized void println(int i) {
        this.builder.append(i);
        flush(true);
    }

    @Override // java.io.PrintStream
    public synchronized void println(long j) {
        this.builder.append(j);
        flush(true);
    }

    @Override // java.io.PrintStream
    public synchronized void println(Object obj) {
        this.builder.append(obj);
        flush(true);
    }

    @Override // java.io.PrintStream
    public synchronized void println(String str) {
        if (this.builder.length() == 0 && str != null) {
            int length = str.length();
            int i = 0;
            while (i < length) {
                int indexOf = str.indexOf(10, i);
                if (indexOf == -1) {
                    break;
                }
                log(str.substring(i, indexOf));
                i = indexOf + 1;
            }
            if (i < length) {
                log(str.substring(i));
            }
        } else {
            this.builder.append(str);
            flush(true);
        }
    }

    @Override // java.io.PrintStream
    public synchronized void println(boolean z) {
        this.builder.append(z);
        flush(true);
    }

    @Override // java.io.PrintStream, java.lang.Appendable
    public synchronized PrintStream append(char c) {
        print(c);
        return this;
    }

    @Override // java.io.PrintStream, java.lang.Appendable
    public synchronized PrintStream append(CharSequence charSequence) {
        this.builder.append(charSequence);
        flush(false);
        return this;
    }

    @Override // java.io.PrintStream, java.lang.Appendable
    public synchronized PrintStream append(CharSequence charSequence, int i, int i2) {
        this.builder.append(charSequence, i, i2);
        flush(false);
        return this;
    }
}
