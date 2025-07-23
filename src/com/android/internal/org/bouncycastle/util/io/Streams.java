package com.android.internal.org.bouncycastle.util.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes5.dex */
public final class Streams {
    private static int BUFFER_SIZE = 4096;

    public static void drain(InputStream inputStream) throws IOException {
        int i = BUFFER_SIZE;
        while (inputStream.read(new byte[i], 0, i) >= 0) {
        }
    }

    public static void pipeAll(InputStream inputStream, OutputStream outputStream) throws IOException {
        pipeAll(inputStream, outputStream, BUFFER_SIZE);
    }

    public static void pipeAll(InputStream inputStream, OutputStream outputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        while (true) {
            int read = inputStream.read(bArr, 0, i);
            if (read < 0) {
                return;
            } else {
                outputStream.write(bArr, 0, read);
            }
        }
    }

    public static long pipeAllLimited(InputStream inputStream, long j, OutputStream outputStream) throws IOException {
        int i = BUFFER_SIZE;
        byte[] bArr = new byte[i];
        long j2 = 0;
        while (true) {
            int read = inputStream.read(bArr, 0, i);
            if (read < 0) {
                return j2;
            }
            long j3 = read;
            if (j - j2 < j3) {
                throw new StreamOverflowException("Data Overflow");
            }
            j2 += j3;
            outputStream.write(bArr, 0, read);
        }
    }

    public static byte[] readAll(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pipeAll(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] readAllLimited(InputStream inputStream, int i) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pipeAllLimited(inputStream, i, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static int readFully(InputStream inputStream, byte[] bArr) throws IOException {
        return readFully(inputStream, bArr, 0, bArr.length);
    }

    public static int readFully(InputStream inputStream, byte[] bArr, int i, int i2) throws IOException {
        int i3 = 0;
        while (i3 < i2) {
            int read = inputStream.read(bArr, i + i3, i2 - i3);
            if (read < 0) {
                break;
            }
            i3 += read;
        }
        return i3;
    }

    public static void validateBufferArguments(byte[] bArr, int i, int i2) {
        bArr.getClass();
        int length = bArr.length - i;
        if ((length | i | i2 | (length - i2)) < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    public static void writeBufTo(ByteArrayOutputStream byteArrayOutputStream, OutputStream outputStream) throws IOException {
        byteArrayOutputStream.writeTo(outputStream);
    }
}
