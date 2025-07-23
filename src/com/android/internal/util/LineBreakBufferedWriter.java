package com.android.internal.util;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class LineBreakBufferedWriter extends PrintWriter {
    private char[] buffer;
    private int bufferIndex;
    private final int bufferSize;
    private int lastNewline;
    private final String lineSeparator;

    public LineBreakBufferedWriter(Writer writer, int i) {
        this(writer, i, 16);
    }

    public LineBreakBufferedWriter(Writer writer, int i, int i2) {
        super(writer);
        this.lastNewline = -1;
        this.buffer = new char[Math.min(i2, i)];
        this.bufferIndex = 0;
        this.bufferSize = i;
        this.lineSeparator = System.getProperty("line.separator");
    }

    @Override // java.io.PrintWriter, java.io.Writer, java.io.Flushable
    public void flush() {
        writeBuffer(this.bufferIndex);
        this.bufferIndex = 0;
        super.flush();
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(int i) {
        int i2 = this.bufferIndex;
        char[] cArr = this.buffer;
        if (i2 < cArr.length) {
            char c = (char) i;
            cArr[i2] = c;
            int i3 = i2 + 1;
            this.bufferIndex = i3;
            if (c == '\n') {
                this.lastNewline = i3;
                return;
            }
            return;
        }
        write(new char[]{(char) i}, 0, 1);
    }

    @Override // java.io.PrintWriter
    public void println() {
        write(this.lineSeparator);
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(char[] cArr, int i, int i2) {
        while (true) {
            int i3 = this.bufferIndex;
            int i4 = i3 + i2;
            int i5 = this.bufferSize;
            if (i4 <= i5) {
                break;
            }
            int i6 = i5 - i3;
            int i7 = -1;
            for (int i8 = 0; i8 < i6; i8++) {
                if (cArr[i + i8] == '\n') {
                    if (this.bufferIndex + i8 >= this.bufferSize) {
                        break;
                    } else {
                        i7 = i8;
                    }
                }
            }
            if (i7 != -1) {
                appendToBuffer(cArr, i, i7);
                writeBuffer(this.bufferIndex);
                this.bufferIndex = 0;
                this.lastNewline = -1;
                int i9 = i7 + 1;
                i += i9;
                i2 -= i9;
            } else {
                int i10 = this.lastNewline;
                if (i10 != -1) {
                    writeBuffer(i10);
                    removeFromBuffer(this.lastNewline + 1);
                    this.lastNewline = -1;
                } else {
                    int i11 = this.bufferSize - this.bufferIndex;
                    appendToBuffer(cArr, i, i11);
                    writeBuffer(this.bufferIndex);
                    this.bufferIndex = 0;
                    i += i11;
                    i2 -= i11;
                }
            }
        }
        if (i2 > 0) {
            appendToBuffer(cArr, i, i2);
            for (int i12 = i2 - 1; i12 >= 0; i12--) {
                if (cArr[i + i12] == '\n') {
                    this.lastNewline = (this.bufferIndex - i2) + i12;
                    return;
                }
            }
        }
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(String str, int i, int i2) {
        while (true) {
            int i3 = this.bufferIndex;
            int i4 = i3 + i2;
            int i5 = this.bufferSize;
            if (i4 <= i5) {
                break;
            }
            int i6 = i5 - i3;
            int i7 = -1;
            for (int i8 = 0; i8 < i6; i8++) {
                if (str.charAt(i + i8) == '\n') {
                    if (this.bufferIndex + i8 >= this.bufferSize) {
                        break;
                    } else {
                        i7 = i8;
                    }
                }
            }
            if (i7 != -1) {
                appendToBuffer(str, i, i7);
                writeBuffer(this.bufferIndex);
                this.bufferIndex = 0;
                this.lastNewline = -1;
                int i9 = i7 + 1;
                i += i9;
                i2 -= i9;
            } else {
                int i10 = this.lastNewline;
                if (i10 != -1) {
                    writeBuffer(i10);
                    removeFromBuffer(this.lastNewline + 1);
                    this.lastNewline = -1;
                } else {
                    int i11 = this.bufferSize - this.bufferIndex;
                    appendToBuffer(str, i, i11);
                    writeBuffer(this.bufferIndex);
                    this.bufferIndex = 0;
                    i += i11;
                    i2 -= i11;
                }
            }
        }
        if (i2 > 0) {
            appendToBuffer(str, i, i2);
            for (int i12 = i2 - 1; i12 >= 0; i12--) {
                if (str.charAt(i + i12) == '\n') {
                    this.lastNewline = (this.bufferIndex - i2) + i12;
                    return;
                }
            }
        }
    }

    private void appendToBuffer(char[] cArr, int i, int i2) {
        int i3 = this.bufferIndex;
        if (i3 + i2 > this.buffer.length) {
            ensureCapacity(i3 + i2);
        }
        System.arraycopy(cArr, i, this.buffer, this.bufferIndex, i2);
        this.bufferIndex += i2;
    }

    private void appendToBuffer(String str, int i, int i2) {
        int i3 = this.bufferIndex;
        if (i3 + i2 > this.buffer.length) {
            ensureCapacity(i3 + i2);
        }
        str.getChars(i, i + i2, this.buffer, this.bufferIndex);
        this.bufferIndex += i2;
    }

    private void ensureCapacity(int i) {
        char[] cArr = this.buffer;
        int length = (cArr.length * 2) + 2;
        if (length >= i) {
            i = length;
        }
        this.buffer = Arrays.copyOf(cArr, i);
    }

    private void removeFromBuffer(int i) {
        int i2 = this.bufferIndex;
        int i3 = i2 - i;
        if (i3 > 0) {
            char[] cArr = this.buffer;
            System.arraycopy(cArr, i2 - i3, cArr, 0, i3);
            this.bufferIndex = i3;
            return;
        }
        this.bufferIndex = 0;
    }

    private void writeBuffer(int i) {
        if (i > 0) {
            super.write(this.buffer, 0, i);
        }
    }
}
