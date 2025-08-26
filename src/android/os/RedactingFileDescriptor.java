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
            public int onRead(long j, int i2, byte[] bArr) throws ErrnoException, InterruptedIOException {
                int iPread;
                AnonymousClass1 anonymousClass1 = this;
                int i3 = 0;
                while (i3 < i2) {
                    try {
                        iPread = Os.pread(RedactingFileDescriptor.this.mInner, bArr, i3, i2 - i3, i3 + j);
                    } catch (InterruptedIOException e) {
                        iPread = e.bytesTransferred;
                    }
                    if (iPread == 0) {
                        break;
                    }
                    i3 += iPread;
                }
                long[] jArr3 = RedactingFileDescriptor.this.mRedactRanges;
                int i4 = 0;
                while (i4 < jArr3.length) {
                    long jMax = Math.max(j, jArr3[i4]);
                    long jMin = Math.min(i2 + j, jArr3[i4 + 1]);
                    long j2 = jMax;
                    while (j2 < jMin) {
                        bArr[(int) (j2 - j)] = 0;
                        j2++;
                        i4 = i4;
                    }
                    int i5 = i4;
                    long[] jArr4 = RedactingFileDescriptor.this.mFreeOffsets;
                    int length = jArr4.length;
                    int i6 = 0;
                    while (i6 < length) {
                        int i7 = i3;
                        long j3 = jArr4[i6];
                        long[] jArr5 = jArr3;
                        long jMax2 = Math.max(j3, jMax);
                        for (long jMin2 = Math.min(j3 + 4, jMin); jMax2 < jMin2; jMin2 = jMin2) {
                            bArr[(int) (jMax2 - j)] = (byte) SemSdCardEncryption.STATUS_FREE.charAt((int) (jMax2 - j3));
                            jMax2++;
                        }
                        i6++;
                        i3 = i7;
                        jArr3 = jArr5;
                    }
                    i4 = i5 + 2;
                    anonymousClass1 = this;
                }
                return i3;
            }

            @Override // android.os.ProxyFileDescriptorCallback
            public int onWrite(long j, int i2, byte[] bArr) throws ErrnoException, InterruptedIOException {
                byte[] bArr2;
                int iPwrite;
                int i3 = 0;
                while (i3 < i2) {
                    try {
                        bArr2 = bArr;
                        try {
                            iPwrite = Os.pwrite(RedactingFileDescriptor.this.mInner, bArr2, i3, i2 - i3, i3 + j);
                        } catch (InterruptedIOException e) {
                            e = e;
                            iPwrite = e.bytesTransferred;
                            i3 += iPwrite;
                            bArr = bArr2;
                        }
                    } catch (InterruptedIOException e2) {
                        e = e2;
                        bArr2 = bArr;
                    }
                    if (iPwrite == 0) {
                        break;
                    }
                    i3 += iPwrite;
                    bArr = bArr2;
                }
                RedactingFileDescriptor redactingFileDescriptor = RedactingFileDescriptor.this;
                redactingFileDescriptor.mRedactRanges = RedactingFileDescriptor.removeRange(redactingFileDescriptor.mRedactRanges, j, i3 + j);
                return i3;
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

    /* JADX WARN: Removed duplicated region for block: B:19:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0085  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static long[] removeRange(long[] jArr, long j, long j2) {
        if (j == j2) {
            return jArr;
        }
        if (j > j2) {
            throw new IllegalArgumentException();
        }
        long[] jArrCopyOf = EmptyArray.LONG;
        for (int i = 0; i < jArr.length; i += 2) {
            long j3 = jArr[i];
            if (j > j3 || j2 < jArr[i + 1]) {
                if (j >= j3) {
                    int i2 = i + 1;
                    if (j2 <= jArr[i2]) {
                        jArrCopyOf = Arrays.copyOf(jArrCopyOf, jArrCopyOf.length + 4);
                        jArrCopyOf[jArrCopyOf.length - 4] = jArr[i];
                        jArrCopyOf[jArrCopyOf.length - 3] = j;
                        jArrCopyOf[jArrCopyOf.length - 2] = j2;
                        jArrCopyOf[jArrCopyOf.length - 1] = jArr[i2];
                    } else {
                        jArrCopyOf = Arrays.copyOf(jArrCopyOf, jArrCopyOf.length + 2);
                        long j4 = jArr[i];
                        if (j2 >= j4 && j2 <= jArr[i + 1]) {
                            jArrCopyOf[jArrCopyOf.length - 2] = Math.max(j4, j2);
                        } else {
                            jArrCopyOf[jArrCopyOf.length - 2] = j4;
                        }
                        if (j >= jArr[i]) {
                            long j5 = jArr[i + 1];
                            if (j <= j5) {
                                jArrCopyOf[jArrCopyOf.length - 1] = Math.min(j5, j);
                            } else {
                                jArrCopyOf[jArrCopyOf.length - 1] = jArr[i + 1];
                            }
                        }
                    }
                }
            }
        }
        return jArrCopyOf;
    }
}
