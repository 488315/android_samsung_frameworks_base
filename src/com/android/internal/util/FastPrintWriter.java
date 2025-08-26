package com.android.internal.util;

import android.util.Log;
import android.util.Printer;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

/* loaded from: classes4.dex */
public class FastPrintWriter extends PrintWriter {
    private final boolean mAutoFlush;
    private final int mBufferLen;
    private final ByteBuffer mBytes;
    private CharsetEncoder mCharset;
    private boolean mIoError;
    private final OutputStream mOutputStream;
    private int mPos;
    private final Printer mPrinter;
    private final String mSeparator;
    private final char[] mText;
    private final Writer mWriter;

    private static class DummyWriter extends Writer {
        private DummyWriter() {
        }

        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            throw new UnsupportedOperationException("Shouldn't be here");
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() throws IOException {
            close();
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i, int i2) throws IOException {
            close();
        }
    }

    public FastPrintWriter(OutputStream outputStream) {
        this(outputStream, false, 8192);
    }

    public FastPrintWriter(OutputStream outputStream, boolean z) {
        this(outputStream, z, 8192);
    }

    public FastPrintWriter(OutputStream outputStream, boolean z, int i) {
        super(new DummyWriter(), z);
        if (outputStream == null) {
            throw new NullPointerException("out is null");
        }
        this.mBufferLen = i;
        this.mText = new char[i];
        this.mBytes = ByteBuffer.allocate(i);
        this.mOutputStream = outputStream;
        this.mWriter = null;
        this.mPrinter = null;
        this.mAutoFlush = z;
        this.mSeparator = System.lineSeparator();
        initDefaultEncoder();
    }

    public FastPrintWriter(Writer writer) {
        this(writer, false, 8192);
    }

    public FastPrintWriter(Writer writer, boolean z) {
        this(writer, z, 8192);
    }

    public FastPrintWriter(Writer writer, boolean z, int i) {
        super(new DummyWriter(), z);
        if (writer == null) {
            throw new NullPointerException("wr is null");
        }
        this.mBufferLen = i;
        this.mText = new char[i];
        this.mBytes = null;
        this.mOutputStream = null;
        this.mWriter = writer;
        this.mPrinter = null;
        this.mAutoFlush = z;
        this.mSeparator = System.lineSeparator();
        initDefaultEncoder();
    }

    public FastPrintWriter(Printer printer) {
        this(printer, 512);
    }

    public FastPrintWriter(Printer printer, int i) {
        super((Writer) new DummyWriter(), true);
        if (printer == null) {
            throw new NullPointerException("pr is null");
        }
        this.mBufferLen = i;
        this.mText = new char[i];
        this.mBytes = null;
        this.mOutputStream = null;
        this.mWriter = null;
        this.mPrinter = printer;
        this.mAutoFlush = true;
        this.mSeparator = System.lineSeparator();
        initDefaultEncoder();
    }

    private final void initEncoder(String str) throws UnsupportedEncodingException {
        try {
            CharsetEncoder charsetEncoderNewEncoder = Charset.forName(str).newEncoder();
            this.mCharset = charsetEncoderNewEncoder;
            charsetEncoderNewEncoder.onMalformedInput(CodingErrorAction.REPLACE);
            this.mCharset.onUnmappableCharacter(CodingErrorAction.REPLACE);
        } catch (Exception unused) {
            throw new UnsupportedEncodingException(str);
        }
    }

    @Override // java.io.PrintWriter
    public boolean checkError() {
        boolean z;
        flush();
        synchronized (this.lock) {
            z = this.mIoError;
        }
        return z;
    }

    @Override // java.io.PrintWriter
    protected void clearError() {
        synchronized (this.lock) {
            this.mIoError = false;
        }
    }

    @Override // java.io.PrintWriter
    protected void setError() {
        synchronized (this.lock) {
            this.mIoError = true;
        }
    }

    private final void initDefaultEncoder() {
        CharsetEncoder charsetEncoderNewEncoder = Charset.defaultCharset().newEncoder();
        this.mCharset = charsetEncoderNewEncoder;
        charsetEncoderNewEncoder.onMalformedInput(CodingErrorAction.REPLACE);
        this.mCharset.onUnmappableCharacter(CodingErrorAction.REPLACE);
    }

    private void appendLocked(char c) throws IOException {
        int i = this.mPos;
        if (i >= this.mBufferLen - 1) {
            flushLocked();
            i = this.mPos;
        }
        this.mText[i] = c;
        this.mPos = i + 1;
    }

    private void appendLocked(String str, int i, int i2) throws IOException {
        int i3 = this.mBufferLen;
        if (i2 > i3) {
            int i4 = i2 + i;
            while (i < i4) {
                int i5 = i + i3;
                appendLocked(str, i, i5 < i4 ? i3 : i4 - i);
                i = i5;
            }
            return;
        }
        int i6 = this.mPos;
        if (i6 + i2 > i3) {
            flushLocked();
            i6 = this.mPos;
        }
        str.getChars(i, i + i2, this.mText, i6);
        this.mPos = i6 + i2;
    }

    private void appendLocked(char[] cArr, int i, int i2) throws IOException {
        int i3 = this.mBufferLen;
        if (i2 > i3) {
            int i4 = i2 + i;
            while (i < i4) {
                int i5 = i + i3;
                appendLocked(cArr, i, i5 < i4 ? i3 : i4 - i);
                i = i5;
            }
            return;
        }
        int i6 = this.mPos;
        if (i6 + i2 > i3) {
            flushLocked();
            i6 = this.mPos;
        }
        System.arraycopy(cArr, i, this.mText, i6, i2);
        this.mPos = i6 + i2;
    }

    private void flushBytesLocked() throws IOException {
        int iPosition;
        if (this.mIoError || (iPosition = this.mBytes.position()) <= 0) {
            return;
        }
        this.mBytes.flip();
        this.mOutputStream.write(this.mBytes.array(), 0, iPosition);
        this.mBytes.clear();
    }

    private void flushLocked() throws IOException {
        int i = this.mPos;
        if (i > 0) {
            if (this.mOutputStream != null) {
                CharBuffer charBufferWrap = CharBuffer.wrap(this.mText, 0, i);
                CoderResult coderResultEncode = this.mCharset.encode(charBufferWrap, this.mBytes, true);
                while (!this.mIoError) {
                    if (coderResultEncode.isError()) {
                        throw new IOException(coderResultEncode.toString());
                    }
                    if (!coderResultEncode.isOverflow()) {
                        break;
                    }
                    flushBytesLocked();
                    coderResultEncode = this.mCharset.encode(charBufferWrap, this.mBytes, true);
                }
                if (!this.mIoError) {
                    flushBytesLocked();
                    this.mOutputStream.flush();
                }
            } else {
                Writer writer = this.mWriter;
                if (writer != null) {
                    if (!this.mIoError) {
                        writer.write(this.mText, 0, i);
                        this.mWriter.flush();
                    }
                } else {
                    int length = this.mSeparator.length();
                    int i2 = this.mPos;
                    if (length >= i2) {
                        length = i2;
                    }
                    int i3 = 0;
                    while (i3 < length) {
                        char c = this.mText[(this.mPos - 1) - i3];
                        String str = this.mSeparator;
                        if (c != str.charAt((str.length() - 1) - i3)) {
                            break;
                        } else {
                            i3++;
                        }
                    }
                    if (i3 >= this.mPos) {
                        this.mPrinter.println("");
                    } else {
                        this.mPrinter.println(new String(this.mText, 0, this.mPos - i3));
                    }
                }
            }
            this.mPos = 0;
        }
    }

    @Override // java.io.PrintWriter, java.io.Writer, java.io.Flushable
    public void flush() {
        synchronized (this.lock) {
            try {
                flushLocked();
                if (!this.mIoError) {
                    OutputStream outputStream = this.mOutputStream;
                    if (outputStream != null) {
                        outputStream.flush();
                    } else {
                        Writer writer = this.mWriter;
                        if (writer != null) {
                            writer.flush();
                        }
                    }
                }
            } catch (IOException e) {
                Log.w("FastPrintWriter", "Write failure", e);
                setError();
            }
        }
    }

    @Override // java.io.PrintWriter, java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        synchronized (this.lock) {
            try {
                flushLocked();
                OutputStream outputStream = this.mOutputStream;
                if (outputStream != null) {
                    outputStream.close();
                } else {
                    Writer writer = this.mWriter;
                    if (writer != null) {
                        writer.close();
                    }
                }
            } catch (IOException e) {
                Log.w("FastPrintWriter", "Write failure", e);
                setError();
            }
        }
    }

    @Override // java.io.PrintWriter
    public void print(char[] cArr) {
        synchronized (this.lock) {
            try {
                appendLocked(cArr, 0, cArr.length);
            } catch (IOException e) {
                Log.w("FastPrintWriter", "Write failure", e);
                setError();
            }
        }
    }

    @Override // java.io.PrintWriter
    public void print(char c) {
        synchronized (this.lock) {
            try {
                appendLocked(c);
            } catch (IOException e) {
                Log.w("FastPrintWriter", "Write failure", e);
                setError();
            }
        }
    }

    @Override // java.io.PrintWriter
    public void print(String str) {
        if (str == null) {
            str = PerfettoProtoLogImpl.NULL_STRING;
        }
        synchronized (this.lock) {
            try {
                appendLocked(str, 0, str.length());
            } catch (IOException e) {
                Log.w("FastPrintWriter", "Write failure", e);
                setError();
            }
        }
    }

    @Override // java.io.PrintWriter
    public void print(int i) {
        if (i == 0) {
            print("0");
        } else {
            super.print(i);
        }
    }

    @Override // java.io.PrintWriter
    public void print(long j) {
        if (j == 0) {
            print("0");
        } else {
            super.print(j);
        }
    }

    @Override // java.io.PrintWriter
    public void println() {
        synchronized (this.lock) {
            try {
                String str = this.mSeparator;
                appendLocked(str, 0, str.length());
            } catch (IOException e) {
                Log.w("FastPrintWriter", "Write failure", e);
                setError();
            }
            if (this.mAutoFlush) {
                flushLocked();
            }
        }
    }

    @Override // java.io.PrintWriter
    public void println(int i) {
        if (i == 0) {
            println("0");
        } else {
            super.println(i);
        }
    }

    @Override // java.io.PrintWriter
    public void println(long j) {
        if (j == 0) {
            println("0");
        } else {
            super.println(j);
        }
    }

    @Override // java.io.PrintWriter
    public void println(char[] cArr) {
        print(cArr);
        println();
    }

    @Override // java.io.PrintWriter
    public void println(char c) {
        print(c);
        println();
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(char[] cArr, int i, int i2) {
        synchronized (this.lock) {
            try {
                appendLocked(cArr, i, i2);
            } catch (IOException e) {
                Log.w("FastPrintWriter", "Write failure", e);
                setError();
            }
        }
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(int i) {
        synchronized (this.lock) {
            try {
                appendLocked((char) i);
            } catch (IOException e) {
                Log.w("FastPrintWriter", "Write failure", e);
                setError();
            }
        }
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(String str) {
        synchronized (this.lock) {
            try {
                appendLocked(str, 0, str.length());
            } catch (IOException e) {
                Log.w("FastPrintWriter", "Write failure", e);
                setError();
            }
        }
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(String str, int i, int i2) {
        synchronized (this.lock) {
            try {
                appendLocked(str, i, i2);
            } catch (IOException e) {
                Log.w("FastPrintWriter", "Write failure", e);
                setError();
            }
        }
    }

    @Override // java.io.PrintWriter, java.io.Writer, java.lang.Appendable
    public PrintWriter append(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            charSequence = PerfettoProtoLogImpl.NULL_STRING;
        }
        String string = charSequence.subSequence(i, i2).toString();
        write(string, 0, string.length());
        return this;
    }
}
