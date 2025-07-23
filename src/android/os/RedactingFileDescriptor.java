package android.os;

import android.content.Context;
import android.os.storage.StorageManager;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Slog;
import com.samsung.android.security.SemSdCardEncryption;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Arrays;
import libcore.io.IoUtils;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
public class RedactingFileDescriptor {
    private static final boolean DEBUG = true;
    private static final String TAG = "RedactingFileDescriptor";
    private final ProxyFileDescriptorCallback mCallback;
    private volatile long[] mFreeOffsets;
    private FileDescriptor mInner;
    private ParcelFileDescriptor mOuter;
    private volatile long[] mRedactRanges;

    private RedactingFileDescriptor(Context context, File file, int i, long[] jArr, long[] jArr2) throws IOException {
        this.mInner = null;
        this.mOuter = null;
        ProxyFileDescriptorCallback proxyFileDescriptorCallback = new ProxyFileDescriptorCallback() { // from class: android.os.RedactingFileDescriptor.1
            @Override // android.os.ProxyFileDescriptorCallback
            public long onGetSize() throws ErrnoException {
                return Os.fstat(RedactingFileDescriptor.this.mInner).st_size;
            }

            @Override // android.os.ProxyFileDescriptorCallback
            public int onRead(long j, int i2, byte[] bArr) throws ErrnoException {
                int i3;
                AnonymousClass1 anonymousClass1 = this;
                int i4 = 0;
                while (i4 < i2) {
                    try {
                        i3 = Os.pread(RedactingFileDescriptor.this.mInner, bArr, i4, i2 - i4, i4 + j);
                    } catch (InterruptedIOException e) {
                        i3 = e.bytesTransferred;
                    }
                    if (i3 == 0) {
                        break;
                    }
                    i4 += i3;
                }
                long[] jArr3 = RedactingFileDescriptor.this.mRedactRanges;
                int i5 = 0;
                while (i5 < jArr3.length) {
                    long max = Math.max(j, jArr3[i5]);
                    long min = Math.min(i2 + j, jArr3[i5 + 1]);
                    long j2 = max;
                    while (j2 < min) {
                        bArr[(int) (j2 - j)] = 0;
                        j2++;
                        i5 = i5;
                    }
                    int i6 = i5;
                    long[] jArr4 = RedactingFileDescriptor.this.mFreeOffsets;
                    int length = jArr4.length;
                    int i7 = 0;
                    while (i7 < length) {
                        int i8 = i4;
                        long j3 = jArr4[i7];
                        long[] jArr5 = jArr3;
                        long max2 = Math.max(j3, max);
                        for (long min2 = Math.min(j3 + 4, min); max2 < min2; min2 = min2) {
                            bArr[(int) (max2 - j)] = (byte) SemSdCardEncryption.STATUS_FREE.charAt((int) (max2 - j3));
                            max2++;
                        }
                        i7++;
                        i4 = i8;
                        jArr3 = jArr5;
                    }
                    i5 = i6 + 2;
                    anonymousClass1 = this;
                }
                return i4;
            }

            @Override // android.os.ProxyFileDescriptorCallback
            public int onWrite(long j, int i2, byte[] bArr) throws ErrnoException {
                byte[] bArr2;
                int i3;
                int i4 = 0;
                while (i4 < i2) {
                    try {
                        bArr2 = bArr;
                    } catch (InterruptedIOException e) {
                        e = e;
                        bArr2 = bArr;
                    }
                    try {
                        i3 = Os.pwrite(RedactingFileDescriptor.this.mInner, bArr2, i4, i2 - i4, i4 + j);
                    } catch (InterruptedIOException e2) {
                        e = e2;
                        i3 = e.bytesTransferred;
                        i4 += i3;
                        bArr = bArr2;
                    }
                    if (i3 == 0) {
                        break;
                    }
                    i4 += i3;
                    bArr = bArr2;
                }
                RedactingFileDescriptor redactingFileDescriptor = RedactingFileDescriptor.this;
                redactingFileDescriptor.mRedactRanges = RedactingFileDescriptor.removeRange(redactingFileDescriptor.mRedactRanges, j, i4 + j);
                return i4;
            }

            @Override // android.os.ProxyFileDescriptorCallback
            public void onFsync() throws ErrnoException {
                Os.fsync(RedactingFileDescriptor.this.mInner);
            }

            @Override // android.os.ProxyFileDescriptorCallback
            public void onRelease() {
                Slog.v(RedactingFileDescriptor.TAG, "onRelease()");
                IoUtils.closeQuietly(RedactingFileDescriptor.this.mInner);
            }
        };
        this.mCallback = proxyFileDescriptorCallback;
        this.mRedactRanges = checkRangesArgument(jArr);
        this.mFreeOffsets = jArr2;
        try {
            try {
                this.mInner = Os.open(file.getAbsolutePath(), FileUtils.translateModePfdToPosix(i), 0);
                this.mOuter = ((StorageManager) context.getSystemService(StorageManager.class)).openProxyFileDescriptor(i, proxyFileDescriptorCallback);
            } catch (ErrnoException e) {
                throw e.rethrowAsIOException();
            }
        } catch (IOException e2) {
            IoUtils.closeQuietly(this.mInner);
            IoUtils.closeQuietly(this.mOuter);
            throw e2;
        }
    }

    private static long[] checkRangesArgument(long[] jArr) {
        if (jArr.length % 2 != 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < jArr.length - 1; i += 2) {
            if (jArr[i] > jArr[i + 1]) {
                throw new IllegalArgumentException();
            }
        }
        return jArr;
    }

    public static ParcelFileDescriptor open(Context context, File file, int i, long[] jArr, long[] jArr2) throws IOException {
        return new RedactingFileDescriptor(context, file, i, jArr, jArr2).mOuter;
    }

    public static long[] removeRange(long[] jArr, long j, long j2) {
        if (j == j2) {
            return jArr;
        }
        if (j > j2) {
            throw new IllegalArgumentException();
        }
        long[] jArr2 = EmptyArray.LONG;
        for (int i = 0; i < jArr.length; i += 2) {
            long j3 = jArr[i];
            if (j > j3 || j2 < jArr[i + 1]) {
                if (j >= j3) {
                    int i2 = i + 1;
                    if (j2 <= jArr[i2]) {
                        jArr2 = Arrays.copyOf(jArr2, jArr2.length + 4);
                        jArr2[jArr2.length - 4] = jArr[i];
                        jArr2[jArr2.length - 3] = j;
                        jArr2[jArr2.length - 2] = j2;
                        jArr2[jArr2.length - 1] = jArr[i2];
                    }
                }
                jArr2 = Arrays.copyOf(jArr2, jArr2.length + 2);
                long j4 = jArr[i];
                if (j2 >= j4 && j2 <= jArr[i + 1]) {
                    jArr2[jArr2.length - 2] = Math.max(j4, j2);
                } else {
                    jArr2[jArr2.length - 2] = j4;
                }
                if (j >= jArr[i]) {
                    long j5 = jArr[i + 1];
                    if (j <= j5) {
                        jArr2[jArr2.length - 1] = Math.min(j5, j);
                    }
                }
                jArr2[jArr2.length - 1] = jArr[i + 1];
            }
        }
        return jArr2;
    }
}
