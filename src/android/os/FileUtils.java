package android.os;

import android.app.AppGlobals;
import android.app.Application;
import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.app.backup.FullBackup;
import android.app.jank.AppJankStats;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.hardware.gnss.GnssSignalType;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import android.os.BatteryStats;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.telecom.Logging.Session;
import android.text.TextUtils;
import android.util.DataUnit;
import android.util.EmptyArray;
import android.util.Log;
import android.util.Slog;
import android.webkit.MimeTypeMap;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.SizedInputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import libcore.io.IoUtils;

/* loaded from: classes3.dex */
public final class FileUtils {
    private static final long COPY_CHECKPOINT_BYTES = 524288;
    public static final int S_IRGRP = 32;
    public static final int S_IROTH = 4;
    public static final int S_IRUSR = 256;
    public static final int S_IRWXG = 56;
    public static final int S_IRWXO = 7;
    public static final int S_IRWXU = 448;
    public static final int S_IWGRP = 16;
    public static final int S_IWOTH = 2;
    public static final int S_IWUSR = 128;
    public static final int S_IXGRP = 8;
    public static final int S_IXOTH = 1;
    public static final int S_IXUSR = 64;
    private static final String TAG = "FileUtils";
    private static boolean sEnableCopyOptimizations = shouldEnableCopyOptimizations();
    private static volatile int sMediaProviderAppId = -1;
    private static volatile int sSecMediaProviderAppId = -1;

    public interface ProgressListener {
        void onProgress(long j);
    }

    private static boolean isValidExtFilenameChar(char c) {
        return (c == 0 || c == '/') ? false : true;
    }

    private static boolean isValidFatFilenameChar(char c) {
        return ((c >= 0 && c <= 31) || c == '\"' || c == '*' || c == '/' || c == ':' || c == '<' || c == '\\' || c == '|' || c == 127 || c == '>' || c == '?') ? false : true;
    }

    public static long roundStorageSize(long j) {
        long j2 = 1;
        long j3 = 1;
        while (true) {
            long j4 = j2 * j3;
            if (j4 >= j) {
                return j4;
            }
            j2 <<= 1;
            if (j2 > 512) {
                j3 *= 1024;
                j2 = 1;
            }
        }
    }

    private static boolean shouldEnableCopyOptimizations() {
        return true;
    }

    private static boolean shouldEnableCopyOptimizations$ravenwood() {
        return false;
    }

    private FileUtils() {
    }

    private static class NoImagePreloadHolder {
        public static final Pattern SAFE_FILENAME_PATTERN = Pattern.compile("[\\w%+,./=_-]+");

        private NoImagePreloadHolder() {
        }
    }

    public static int setPermissions(File file, int i, int i2, int i3) {
        return setPermissions(file.getAbsolutePath(), i, i2, i3);
    }

    public static int setPermissions(String str, int i, int i2, int i3) {
        try {
            Os.chmod(str, i);
            if (i2 < 0 && i3 < 0) {
                return 0;
            }
            try {
                Os.chown(str, i2, i3);
                return 0;
            } catch (ErrnoException e) {
                Slog.w(TAG, "Failed to chown(" + str + "): " + e);
                return e.errno;
            }
        } catch (ErrnoException e2) {
            Slog.w(TAG, "Failed to chmod(" + str + "): " + e2);
            return e2.errno;
        }
    }

    public static int setPermissions(FileDescriptor fileDescriptor, int i, int i2, int i3) {
        try {
            Os.fchmod(fileDescriptor, i);
            if (i2 < 0 && i3 < 0) {
                return 0;
            }
            try {
                Os.fchown(fileDescriptor, i2, i3);
                return 0;
            } catch (ErrnoException e) {
                Slog.w(TAG, "Failed to fchown(): " + e);
                return e.errno;
            }
        } catch (ErrnoException e2) {
            Slog.w(TAG, "Failed to fchmod(): " + e2);
            return e2.errno;
        }
    }

    public static void copyPermissions(File file, File file2) throws IOException {
        try {
            StructStat stat = Os.stat(file.getAbsolutePath());
            Os.chmod(file2.getAbsolutePath(), stat.st_mode);
            Os.chown(file2.getAbsolutePath(), stat.st_uid, stat.st_gid);
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    @Deprecated
    public static int getUid(String str) {
        try {
            return Os.stat(str).st_uid;
        } catch (ErrnoException unused) {
            return -1;
        }
    }

    public static boolean sync(FileOutputStream fileOutputStream) {
        if (fileOutputStream == null) {
            return true;
        }
        try {
            fileOutputStream.getFD().sync();
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    @Deprecated
    public static boolean copyFile(File file, File file2) {
        try {
            copyFileOrThrow(file, file2);
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    @Deprecated
    public static void copyFileOrThrow(File file, File file2) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            copyToFileOrThrow(fileInputStream, file2);
            fileInputStream.close();
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Deprecated
    public static boolean copyToFile(InputStream inputStream, File file) {
        try {
            copyToFileOrThrow(inputStream, file);
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    @Deprecated
    public static void copyToFileOrThrow(InputStream inputStream, File file) throws IOException {
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            copy(inputStream, fileOutputStream);
            sync(fileOutputStream);
            fileOutputStream.close();
        } catch (Throwable th) {
            try {
                fileOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static long copy(File file, File file2) throws IOException {
        return copy(file, file2, (CancellationSignal) null, (Executor) null, (ProgressListener) null);
    }

    public static long copy(File file, File file2, CancellationSignal cancellationSignal, Executor executor, ProgressListener progressListener) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            try {
                long copy = copy(fileInputStream, fileOutputStream, cancellationSignal, executor, progressListener);
                fileOutputStream.close();
                fileInputStream.close();
                return copy;
            } finally {
            }
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        return copy(inputStream, outputStream, (CancellationSignal) null, (Executor) null, (ProgressListener) null);
    }

    public static long copy(InputStream inputStream, OutputStream outputStream, CancellationSignal cancellationSignal, Executor executor, ProgressListener progressListener) throws IOException {
        if (sEnableCopyOptimizations && (inputStream instanceof FileInputStream) && (outputStream instanceof FileOutputStream)) {
            return copy(((FileInputStream) inputStream).getFD(), ((FileOutputStream) outputStream).getFD(), cancellationSignal, executor, progressListener);
        }
        return copyInternalUserspace(inputStream, outputStream, cancellationSignal, executor, progressListener);
    }

    public static long copy(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2) throws IOException {
        return copy(fileDescriptor, fileDescriptor2, (CancellationSignal) null, (Executor) null, (ProgressListener) null);
    }

    public static long copy(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, CancellationSignal cancellationSignal, Executor executor, ProgressListener progressListener) throws IOException {
        return copy(fileDescriptor, fileDescriptor2, Long.MAX_VALUE, cancellationSignal, executor, progressListener);
    }

    public static long copy(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, long j, CancellationSignal cancellationSignal, Executor executor, ProgressListener progressListener) throws IOException {
        if (sEnableCopyOptimizations) {
            try {
                StructStat fstat = Os.fstat(fileDescriptor);
                StructStat fstat2 = Os.fstat(fileDescriptor2);
                if (OsConstants.S_ISREG(fstat.st_mode) && OsConstants.S_ISREG(fstat2.st_mode)) {
                    try {
                        return copyInternalSendfile(fileDescriptor, fileDescriptor2, j, cancellationSignal, executor, progressListener);
                    } catch (ErrnoException e) {
                        if (e.errno != OsConstants.EINVAL && e.errno != OsConstants.ENOSYS) {
                            throw e;
                        }
                        return copyInternalUserspace(fileDescriptor, fileDescriptor2, j, cancellationSignal, executor, progressListener);
                    }
                }
                if (!OsConstants.S_ISFIFO(fstat.st_mode) && !OsConstants.S_ISFIFO(fstat2.st_mode)) {
                    if (OsConstants.S_ISSOCK(fstat.st_mode) || OsConstants.S_ISSOCK(fstat2.st_mode)) {
                        return copyInternalSpliceSocket(fileDescriptor, fileDescriptor2, j, cancellationSignal, executor, progressListener);
                    }
                }
                return copyInternalSplice(fileDescriptor, fileDescriptor2, j, cancellationSignal, executor, progressListener);
            } catch (ErrnoException e2) {
                throw e2.rethrowAsIOException();
            }
        }
        return copyInternalUserspace(fileDescriptor, fileDescriptor2, j, cancellationSignal, executor, progressListener);
    }

    public static long copyInternalSplice(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, long j, CancellationSignal cancellationSignal, Executor executor, final ProgressListener progressListener) throws ErrnoException {
        long j2 = j;
        final long j3 = 0;
        long j4 = 0;
        while (true) {
            long splice = Os.splice(fileDescriptor, null, fileDescriptor2, null, Math.min(j2, 524288L), OsConstants.SPLICE_F_MOVE | OsConstants.SPLICE_F_MORE);
            if (splice == 0) {
                break;
            }
            j3 += splice;
            j4 += splice;
            j2 -= splice;
            if (j4 >= 524288) {
                if (cancellationSignal != null) {
                    cancellationSignal.throwIfCanceled();
                }
                if (executor != null && progressListener != null) {
                    executor.execute(new Runnable() { // from class: android.os.FileUtils$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            FileUtils.ProgressListener.this.onProgress(j3);
                        }
                    });
                }
                j4 = 0;
            }
        }
        if (executor != null && progressListener != null) {
            executor.execute(new Runnable() { // from class: android.os.FileUtils$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    FileUtils.ProgressListener.this.onProgress(j3);
                }
            });
        }
        return j3;
    }

    public static long copyInternalSpliceSocket(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, long j, CancellationSignal cancellationSignal, Executor executor, final ProgressListener progressListener) throws ErrnoException {
        FileDescriptor[] fileDescriptorArr;
        FileDescriptor[] pipe = Os.pipe();
        long j2 = 0;
        long j3 = j;
        long j4 = 0;
        final long j5 = 0;
        long j6 = 0;
        while (true) {
            if (j3 <= j2 && j4 <= j2) {
                fileDescriptorArr = pipe;
                break;
            }
            long j7 = j2;
            fileDescriptorArr = pipe;
            long j8 = j6;
            if (j3 > j2) {
                long splice = Os.splice(fileDescriptor, null, fileDescriptorArr[1], null, Math.min(j3, 524288L), OsConstants.SPLICE_F_MOVE | OsConstants.SPLICE_F_MORE);
                if (splice < j7) {
                    Slog.e(TAG, "splice error, fdIn --> pipe, copy size:" + j + ", copied:" + j5 + ", read:" + (j - j3) + ", in pipe:" + j4);
                    break;
                }
                if (splice == j7) {
                    Slog.w(TAG, "Reached the end of the input file. The size to be copied exceeds the actual size, copy size:" + j + ", copied:" + j5 + ", read:" + (j - j3) + ", in pipe:" + j4);
                    j3 = j7;
                } else {
                    j4 += splice;
                    j3 -= splice;
                }
            }
            if (j4 > j7) {
                long splice2 = Os.splice(fileDescriptorArr[0], null, fileDescriptor2, null, Math.min(j4, 524288L), OsConstants.SPLICE_F_MOVE | OsConstants.SPLICE_F_MORE);
                if (splice2 <= j7) {
                    Slog.e(TAG, "splice error, pipe --> fdOut, copy size:" + j + ", copied:" + j5 + ", read:" + (j - j3) + ", in pipe: " + j4);
                    Os.close(fileDescriptorArr[0]);
                    Os.close(fileDescriptorArr[1]);
                    throw new ErrnoException("splice, pipe --> fdOut", OsConstants.EIO);
                }
                j5 += splice2;
                j4 -= splice2;
                j6 = j8 + splice2;
            } else {
                j6 = j8;
            }
            if (j6 >= 524288) {
                if (cancellationSignal != null) {
                    cancellationSignal.throwIfCanceled();
                }
                if (executor != null && progressListener != null) {
                    executor.execute(new Runnable() { // from class: android.os.FileUtils$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            FileUtils.ProgressListener.this.onProgress(j5);
                        }
                    });
                }
                j2 = j7;
                j6 = j2;
            } else {
                j2 = j7;
            }
            pipe = fileDescriptorArr;
        }
        if (executor != null && progressListener != null) {
            executor.execute(new Runnable() { // from class: android.os.FileUtils$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    FileUtils.ProgressListener.this.onProgress(j5);
                }
            });
        }
        Os.close(fileDescriptorArr[0]);
        Os.close(fileDescriptorArr[1]);
        return j5;
    }

    public static long copyInternalSendfile(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, long j, CancellationSignal cancellationSignal, Executor executor, final ProgressListener progressListener) throws ErrnoException {
        long j2 = j;
        final long j3 = 0;
        long j4 = 0;
        while (true) {
            long sendfile = Os.sendfile(fileDescriptor2, fileDescriptor, null, Math.min(j2, 524288L));
            if (sendfile == 0) {
                break;
            }
            j3 += sendfile;
            j4 += sendfile;
            j2 -= sendfile;
            if (j4 >= 524288) {
                if (cancellationSignal != null) {
                    cancellationSignal.throwIfCanceled();
                }
                if (executor != null && progressListener != null) {
                    executor.execute(new Runnable() { // from class: android.os.FileUtils$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            FileUtils.ProgressListener.this.onProgress(j3);
                        }
                    });
                }
                j4 = 0;
            }
        }
        if (executor != null && progressListener != null) {
            executor.execute(new Runnable() { // from class: android.os.FileUtils$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    FileUtils.ProgressListener.this.onProgress(j3);
                }
            });
        }
        return j3;
    }

    @Deprecated
    public static long copyInternalUserspace(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, ProgressListener progressListener, CancellationSignal cancellationSignal, long j) throws IOException {
        return copyInternalUserspace(fileDescriptor, fileDescriptor2, j, cancellationSignal, new PendingIntent$$ExternalSyntheticLambda0(), progressListener);
    }

    public static long copyInternalUserspace(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, long j, CancellationSignal cancellationSignal, Executor executor, ProgressListener progressListener) throws IOException {
        if (j != Long.MAX_VALUE) {
            return copyInternalUserspace(new SizedInputStream(new FileInputStream(fileDescriptor), j), new FileOutputStream(fileDescriptor2), cancellationSignal, executor, progressListener);
        }
        return copyInternalUserspace(new FileInputStream(fileDescriptor), new FileOutputStream(fileDescriptor2), cancellationSignal, executor, progressListener);
    }

    public static long copyInternalUserspace(InputStream inputStream, OutputStream outputStream, CancellationSignal cancellationSignal, Executor executor, final ProgressListener progressListener) throws IOException {
        byte[] bArr = new byte[8192];
        final long j = 0;
        long j2 = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                break;
            }
            outputStream.write(bArr, 0, read);
            long j3 = read;
            j += j3;
            j2 += j3;
            if (j2 >= 524288) {
                if (cancellationSignal != null) {
                    cancellationSignal.throwIfCanceled();
                }
                if (executor != null && progressListener != null) {
                    executor.execute(new Runnable() { // from class: android.os.FileUtils$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            FileUtils.ProgressListener.this.onProgress(j);
                        }
                    });
                }
                j2 = 0;
            }
        }
        if (executor != null && progressListener != null) {
            executor.execute(new Runnable() { // from class: android.os.FileUtils$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    FileUtils.ProgressListener.this.onProgress(j);
                }
            });
        }
        return j;
    }

    public static boolean isFilenameSafe(File file) {
        return NoImagePreloadHolder.SAFE_FILENAME_PATTERN.matcher(file.getPath()).matches();
    }

    public static String readTextFile(File file, int i, String str) throws IOException {
        int read;
        boolean z;
        int read2;
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        try {
            long length = file.length();
            String str2 = "";
            if (i > 0 || (length > 0 && i == 0)) {
                if (length > 0 && (i == 0 || length < i)) {
                    i = (int) length;
                }
                byte[] bArr = new byte[i + 1];
                int read3 = bufferedInputStream.read(bArr);
                if (read3 > 0) {
                    if (read3 <= i) {
                        str2 = new String(bArr, 0, read3);
                    } else if (str == null) {
                        str2 = new String(bArr, 0, i);
                    } else {
                        str2 = new String(bArr, 0, i) + str;
                    }
                }
            } else if (i < 0) {
                byte[] bArr2 = null;
                byte[] bArr3 = null;
                boolean z2 = false;
                while (true) {
                    z = true;
                    if (bArr2 != null) {
                        z2 = true;
                    }
                    if (bArr2 == null) {
                        bArr2 = new byte[-i];
                    }
                    read2 = bufferedInputStream.read(bArr2);
                    if (read2 != bArr2.length) {
                        break;
                    }
                    byte[] bArr4 = bArr3;
                    bArr3 = bArr2;
                    bArr2 = bArr4;
                }
                if (bArr3 != null || read2 > 0) {
                    if (bArr3 == null) {
                        str2 = new String(bArr2, 0, read2);
                    } else {
                        if (read2 > 0) {
                            System.arraycopy(bArr3, read2, bArr3, 0, bArr3.length - read2);
                            System.arraycopy(bArr2, 0, bArr3, bArr3.length - read2, read2);
                        } else {
                            z = z2;
                        }
                        if (str != null && z) {
                            str2 = str + new String(bArr3);
                        }
                        str2 = new String(bArr3);
                    }
                }
            } else {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr5 = new byte[1024];
                do {
                    read = bufferedInputStream.read(bArr5);
                    if (read > 0) {
                        byteArrayOutputStream.write(bArr5, 0, read);
                    }
                } while (read == 1024);
                str2 = byteArrayOutputStream.toString();
            }
            return str2;
        } finally {
            bufferedInputStream.close();
            fileInputStream.close();
        }
    }

    public static void stringToFile(File file, String str) throws IOException {
        stringToFile(file.getAbsolutePath(), str);
    }

    public static void bytesToFile(String str, byte[] bArr) throws IOException {
        if (str.startsWith("/proc/")) {
            int allowThreadDiskWritesMask = StrictMode.allowThreadDiskWritesMask();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(str);
                try {
                    fileOutputStream.write(bArr);
                    fileOutputStream.close();
                    return;
                } finally {
                }
            } finally {
                StrictMode.setThreadPolicyMask(allowThreadDiskWritesMask);
            }
        }
        FileOutputStream fileOutputStream2 = new FileOutputStream(str);
        try {
            fileOutputStream2.write(bArr);
            fileOutputStream2.close();
        } catch (Throwable th) {
            try {
                fileOutputStream2.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static void stringToFile(String str, String str2) throws IOException {
        bytesToFile(str, str2.getBytes(StandardCharsets.UTF_8));
    }

    @Deprecated
    public static long checksumCrc32(File file) throws FileNotFoundException, IOException {
        CheckedInputStream checkedInputStream;
        CRC32 crc32 = new CRC32();
        CheckedInputStream checkedInputStream2 = null;
        try {
            checkedInputStream = new CheckedInputStream(new FileInputStream(file), crc32);
        } catch (Throwable th) {
            th = th;
        }
        try {
            while (checkedInputStream.read(new byte[128]) >= 0) {
            }
            long value = crc32.getValue();
            try {
                checkedInputStream.close();
            } catch (IOException unused) {
            }
            return value;
        } catch (Throwable th2) {
            th = th2;
            checkedInputStream2 = checkedInputStream;
            if (checkedInputStream2 != null) {
                try {
                    checkedInputStream2.close();
                } catch (IOException unused2) {
                }
            }
            throw th;
        }
    }

    public static byte[] digest(File file, String str) throws IOException, NoSuchAlgorithmException {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            byte[] digest = digest(fileInputStream, str);
            fileInputStream.close();
            return digest;
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static byte[] digest(InputStream inputStream, String str) throws IOException, NoSuchAlgorithmException {
        return digestInternalUserspace(inputStream, str);
    }

    public static byte[] digest(FileDescriptor fileDescriptor, String str) throws IOException, NoSuchAlgorithmException {
        return digestInternalUserspace(new FileInputStream(fileDescriptor), str);
    }

    private static byte[] digestInternalUserspace(InputStream inputStream, String str) throws IOException, NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(str);
        DigestInputStream digestInputStream = new DigestInputStream(inputStream, messageDigest);
        try {
            do {
            } while (digestInputStream.read(new byte[8192]) != -1);
            digestInputStream.close();
            return messageDigest.digest();
        } catch (Throwable th) {
            try {
                digestInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static boolean deleteOlderFiles(File file, int i, long j) {
        if (i < 0 || j < 0) {
            throw new IllegalArgumentException("Constraints must be positive or 0");
        }
        File[] listFiles = file.listFiles();
        boolean z = false;
        if (listFiles == null) {
            return false;
        }
        Arrays.sort(listFiles, new Comparator<File>() { // from class: android.os.FileUtils.1
            @Override // java.util.Comparator
            public int compare(File file2, File file3) {
                return Long.compare(file3.lastModified(), file2.lastModified());
            }
        });
        while (i < listFiles.length) {
            File file2 = listFiles[i];
            if (System.currentTimeMillis() - file2.lastModified() > j && file2.delete()) {
                Log.d(TAG, "Deleted old file " + file2);
                z = true;
            }
            i++;
        }
        return z;
    }

    public static boolean contains(File[] fileArr, File file) {
        for (File file2 : fileArr) {
            if (contains(file2, file)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(Collection<File> collection, File file) {
        Iterator<File> it = collection.iterator();
        while (it.hasNext()) {
            if (contains(it.next(), file)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(File file, File file2) {
        if (file == null || file2 == null) {
            return false;
        }
        return contains(file.getAbsolutePath(), file2.getAbsolutePath());
    }

    public static boolean contains(String str, String str2) {
        if (str.equals(str2)) {
            return true;
        }
        if (!str.endsWith("/")) {
            str = str + "/";
        }
        return str2.startsWith(str);
    }

    public static boolean deleteContentsAndDir(File file) {
        if (deleteContents(file)) {
            return file.delete();
        }
        return false;
    }

    public static boolean deleteContents(File file) {
        File[] listFiles = file.listFiles();
        boolean z = true;
        if (listFiles != null) {
            for (File file2 : listFiles) {
                if (file2.isDirectory()) {
                    z &= deleteContents(file2);
                }
                if (!file2.delete()) {
                    Log.w(TAG, "Failed to delete " + file2);
                    z = false;
                }
            }
        }
        return z;
    }

    public static boolean isValidExtFilename(String str) {
        return str != null && str.equals(buildValidExtFilename(str));
    }

    public static String buildValidExtFilename(String str) {
        if (TextUtils.isEmpty(str) || MediaMetrics.SEPARATOR.equals(str) || "..".equals(str)) {
            return "(invalid)";
        }
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (isValidExtFilenameChar(charAt)) {
                sb.append(charAt);
            } else {
                sb.append('_');
            }
        }
        trimFilename(sb, 255);
        return sb.toString();
    }

    public static boolean isValidFatFilename(String str) {
        return str != null && str.equals(buildValidFatFilename(str));
    }

    public static String buildValidFatFilename(String str) {
        if (TextUtils.isEmpty(str) || MediaMetrics.SEPARATOR.equals(str) || "..".equals(str)) {
            return "(invalid)";
        }
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (isValidFatFilenameChar(charAt)) {
                sb.append(charAt);
            } else {
                sb.append('_');
            }
        }
        trimFilename(sb, 255);
        return sb.toString();
    }

    public static String trimFilename(String str, int i) {
        StringBuilder sb = new StringBuilder(str);
        trimFilename(sb, i);
        return sb.toString();
    }

    private static void trimFilename(StringBuilder sb, int i) {
        byte[] bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
        if (bytes.length > i) {
            int i2 = i - 3;
            while (bytes.length > i2) {
                sb.deleteCharAt(sb.length() / 2);
                bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
            }
            sb.insert(sb.length() / 2, Session.TRUNCATE_STRING);
        }
    }

    public static String rewriteAfterRename(File file, File file2, String str) {
        File rewriteAfterRename;
        if (str == null || (rewriteAfterRename = rewriteAfterRename(file, file2, new File(str))) == null) {
            return null;
        }
        return rewriteAfterRename.getAbsolutePath();
    }

    public static String[] rewriteAfterRename(File file, File file2, String[] strArr) {
        if (strArr == null) {
            return null;
        }
        String[] strArr2 = new String[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            strArr2[i] = rewriteAfterRename(file, file2, strArr[i]);
        }
        return strArr2;
    }

    public static File rewriteAfterRename(File file, File file2, File file3) {
        if (file3 == null || file == null || file2 == null || !contains(file, file3)) {
            return null;
        }
        return new File(file2, file3.getAbsolutePath().substring(file.getAbsolutePath().length()));
    }

    private static File buildUniqueFileWithExtension(File file, String str, String str2) throws FileNotFoundException {
        File buildFile = buildFile(file, str, str2);
        int i = 0;
        while (buildFile.exists()) {
            int i2 = i + 1;
            if (i >= 32) {
                throw new FileNotFoundException("Failed to create unique file");
            }
            i = i2;
            buildFile = buildFile(file, str + " (" + i2 + NavigationBarInflaterView.KEY_CODE_END, str2);
        }
        return buildFile;
    }

    public static File buildUniqueFile(File file, String str, String str2) throws FileNotFoundException {
        String[] splitFileName = splitFileName(str, str2);
        return buildUniqueFileWithExtension(file, splitFileName[0], splitFileName[1]);
    }

    public static File buildNonUniqueFile(File file, String str, String str2) {
        String[] splitFileName = splitFileName(str, str2);
        return buildFile(file, splitFileName[0], splitFileName[1]);
    }

    public static File buildUniqueFile(File file, String str) throws FileNotFoundException {
        String str2;
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf >= 0) {
            String substring = str.substring(0, lastIndexOf);
            str2 = str.substring(lastIndexOf + 1);
            str = substring;
        } else {
            str2 = null;
        }
        return buildUniqueFileWithExtension(file, str, str2);
    }

    public static String[] splitFileName(String str, String str2) {
        String str3;
        String str4;
        String str5;
        if (!DocumentsContract.Document.MIME_TYPE_DIR.equals(str)) {
            int lastIndexOf = str2.lastIndexOf(46);
            if (lastIndexOf >= 0) {
                str3 = str2.substring(0, lastIndexOf);
                str4 = str2.substring(lastIndexOf + 1);
                str5 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(str4.toLowerCase());
            } else {
                str3 = str2;
                str4 = null;
                str5 = null;
            }
            if (str5 == null) {
                str5 = "application/octet-stream";
            }
            r1 = "application/octet-stream".equals(str) ? null : MimeTypeMap.getSingleton().getExtensionFromMimeType(str);
            if (Objects.equals(str, str5) || Objects.equals(str4, r1)) {
                r1 = str4;
                str2 = str3;
            }
        }
        if (r1 == null) {
            r1 = "";
        }
        return new String[]{str2, r1};
    }

    private static File buildFile(File file, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return new File(file, str);
        }
        return new File(file, str + MediaMetrics.SEPARATOR + str2);
    }

    public static String[] listOrEmpty(File file) {
        if (file != null) {
            return ArrayUtils.defeatNullable(file.list());
        }
        return EmptyArray.STRING;
    }

    public static File[] listFilesOrEmpty(File file) {
        if (file != null) {
            return ArrayUtils.defeatNullable(file.listFiles());
        }
        return ArrayUtils.EMPTY_FILE;
    }

    public static File[] listFilesOrEmpty(File file, FilenameFilter filenameFilter) {
        if (file != null) {
            return ArrayUtils.defeatNullable(file.listFiles(filenameFilter));
        }
        return ArrayUtils.EMPTY_FILE;
    }

    public static File newFileOrNull(String str) {
        if (str != null) {
            return new File(str);
        }
        return null;
    }

    public static File createDir(File file, String str) {
        File file2 = new File(file, str);
        if (createDir(file2)) {
            return file2;
        }
        return null;
    }

    public static boolean createDir(File file) {
        if (file.mkdir()) {
            return true;
        }
        if (file.exists()) {
            return file.isDirectory();
        }
        return false;
    }

    private static long toBytes(long j, String str) {
        String upperCase = str.toUpperCase();
        if (GnssSignalType.CODE_TYPE_B.equals(upperCase)) {
            return j;
        }
        if ("K".equals(upperCase) || "KB".equals(upperCase)) {
            return DataUnit.KILOBYTES.toBytes(j);
        }
        if (GnssSignalType.CODE_TYPE_M.equals(upperCase) || "MB".equals(upperCase)) {
            return DataUnit.MEGABYTES.toBytes(j);
        }
        if ("G".equals(upperCase) || "GB".equals(upperCase)) {
            return DataUnit.GIGABYTES.toBytes(j);
        }
        if ("KI".equals(upperCase) || "KIB".equals(upperCase)) {
            return DataUnit.KIBIBYTES.toBytes(j);
        }
        if ("MI".equals(upperCase) || "MIB".equals(upperCase)) {
            return DataUnit.MEBIBYTES.toBytes(j);
        }
        if ("GI".equals(upperCase) || "GIB".equals(upperCase)) {
            return DataUnit.GIBIBYTES.toBytes(j);
        }
        return Long.MIN_VALUE;
    }

    public static long parseSize(String str) {
        if (str == null || str.isBlank()) {
            return Long.MIN_VALUE;
        }
        String trim = str.trim();
        char charAt = trim.charAt(0);
        int i = 1;
        if (charAt == '-' || charAt == '+') {
            int i2 = charAt == '-' ? -1 : 1;
            trim = trim.substring(1);
            i = i2;
        }
        int i3 = 0;
        while (i3 < trim.length() && Character.isDigit(trim.charAt(i3))) {
            i3++;
        }
        if (i3 == 0 || i3 == trim.length()) {
            return Long.MIN_VALUE;
        }
        return toBytes(i * Long.valueOf(trim.substring(0, i3)).longValue(), trim.substring(i3).trim());
    }

    @Deprecated
    public static void closeQuietly(AutoCloseable autoCloseable) {
        IoUtils.closeQuietly(autoCloseable);
    }

    @Deprecated
    public static void closeQuietly(FileDescriptor fileDescriptor) {
        IoUtils.closeQuietly(fileDescriptor);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x006f A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int translateModeStringToPosix(java.lang.String r5) {
        /*
            r0 = 0
        L1:
            int r1 = r5.length()
            java.lang.String r2 = "Bad mode: "
            r3 = 116(0x74, float:1.63E-43)
            r4 = 97
            if (r0 >= r1) goto L33
            char r1 = r5.charAt(r0)
            if (r1 == r4) goto L30
            r4 = 114(0x72, float:1.6E-43)
            if (r1 == r4) goto L30
            if (r1 == r3) goto L30
            r3 = 119(0x77, float:1.67E-43)
            if (r1 != r3) goto L1e
            goto L30
        L1e:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>(r2)
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            r0.<init>(r5)
            throw r0
        L30:
            int r0 = r0 + 1
            goto L1
        L33:
            java.lang.String r0 = "rw"
            boolean r0 = r5.startsWith(r0)
            if (r0 == 0) goto L42
            int r0 = android.system.OsConstants.O_RDWR
            int r1 = android.system.OsConstants.O_CREAT
        L40:
            r0 = r0 | r1
            goto L5b
        L42:
            java.lang.String r0 = "w"
            boolean r0 = r5.startsWith(r0)
            if (r0 == 0) goto L50
            int r0 = android.system.OsConstants.O_WRONLY
            int r1 = android.system.OsConstants.O_CREAT
            goto L40
        L50:
            java.lang.String r0 = "r"
            boolean r0 = r5.startsWith(r0)
            if (r0 == 0) goto L70
            int r0 = android.system.OsConstants.O_RDONLY
        L5b:
            int r1 = r5.indexOf(r3)
            r2 = -1
            if (r1 == r2) goto L65
            int r1 = android.system.OsConstants.O_TRUNC
            r0 = r0 | r1
        L65:
            int r5 = r5.indexOf(r4)
            if (r5 == r2) goto L6f
            int r5 = android.system.OsConstants.O_APPEND
            r5 = r5 | r0
            return r5
        L6f:
            return r0
        L70:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>(r2)
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            r0.<init>(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.FileUtils.translateModeStringToPosix(java.lang.String):int");
    }

    public static String translateModePosixToString(int i) {
        String str;
        if ((OsConstants.O_ACCMODE & i) == OsConstants.O_RDWR) {
            str = "rw";
        } else if ((OsConstants.O_ACCMODE & i) == OsConstants.O_WRONLY) {
            str = "w";
        } else if ((OsConstants.O_ACCMODE & i) == OsConstants.O_RDONLY) {
            str = "r";
        } else {
            throw new IllegalArgumentException("Bad mode: " + i);
        }
        if ((OsConstants.O_TRUNC & i) == OsConstants.O_TRUNC) {
            str = str.concat("t");
        }
        if ((i & OsConstants.O_APPEND) != OsConstants.O_APPEND) {
            return str;
        }
        return str + FullBackup.APK_TREE_TOKEN;
    }

    public static int translateModePosixToPfd(int i) {
        int i2;
        if ((OsConstants.O_ACCMODE & i) == OsConstants.O_RDWR) {
            i2 = 805306368;
        } else if ((OsConstants.O_ACCMODE & i) == OsConstants.O_WRONLY) {
            i2 = 536870912;
        } else {
            if ((OsConstants.O_ACCMODE & i) != OsConstants.O_RDONLY) {
                throw new IllegalArgumentException("Bad mode: " + i);
            }
            i2 = 268435456;
        }
        if ((OsConstants.O_CREAT & i) == OsConstants.O_CREAT) {
            i2 |= 134217728;
        }
        if ((OsConstants.O_TRUNC & i) == OsConstants.O_TRUNC) {
            i2 |= 67108864;
        }
        return (i & OsConstants.O_APPEND) == OsConstants.O_APPEND ? 33554432 | i2 : i2;
    }

    public static int translateModePfdToPosix(int i) {
        int i2;
        if ((i & 805306368) == 805306368) {
            i2 = OsConstants.O_RDWR;
        } else if ((i & 536870912) == 536870912) {
            i2 = OsConstants.O_WRONLY;
        } else if ((i & 268435456) == 268435456) {
            i2 = OsConstants.O_RDONLY;
        } else {
            throw new IllegalArgumentException("Bad mode: " + i);
        }
        if ((i & 134217728) == 134217728) {
            i2 |= OsConstants.O_CREAT;
        }
        if ((i & 67108864) == 67108864) {
            i2 |= OsConstants.O_TRUNC;
        }
        return (i & 33554432) == 33554432 ? OsConstants.O_APPEND | i2 : i2;
    }

    public static int translateModeAccessToPosix(int i) {
        if (i == OsConstants.F_OK) {
            return OsConstants.O_RDONLY;
        }
        if (((OsConstants.R_OK | OsConstants.W_OK) & i) == (OsConstants.R_OK | OsConstants.W_OK)) {
            return OsConstants.O_RDWR;
        }
        if ((OsConstants.R_OK & i) == OsConstants.R_OK) {
            return OsConstants.O_RDONLY;
        }
        if ((OsConstants.W_OK & i) == OsConstants.W_OK) {
            return OsConstants.O_WRONLY;
        }
        throw new IllegalArgumentException("Bad mode: " + i);
    }

    public static ParcelFileDescriptor convertToModernFd(FileDescriptor fileDescriptor) {
        Application initialApplication = AppGlobals.getInitialApplication();
        int appId = UserHandle.getAppId(Process.myUid());
        if (appId != getMediaProviderAppId(initialApplication) && appId != getSecMediaProviderAppId(initialApplication)) {
            try {
                ParcelFileDescriptor dup = ParcelFileDescriptor.dup(fileDescriptor);
                try {
                    ParcelFileDescriptor originalMediaFormatFileDescriptor = MediaStore.getOriginalMediaFormatFileDescriptor(initialApplication, dup);
                    if (dup != null) {
                        dup.close();
                    }
                    return originalMediaFormatFileDescriptor;
                } finally {
                }
            } catch (Exception unused) {
            }
        }
        return null;
    }

    private static int getSecMediaProviderAppId(Context context) {
        if (sSecMediaProviderAppId != -1) {
            return sSecMediaProviderAppId;
        }
        ProviderInfo resolveContentProvider = context.getPackageManager().resolveContentProvider("secmedia", BatteryStats.HistoryItem.MOST_INTERESTING_STATES);
        if (resolveContentProvider == null) {
            return -1;
        }
        sSecMediaProviderAppId = UserHandle.getAppId(resolveContentProvider.applicationInfo.uid);
        return sSecMediaProviderAppId;
    }

    private static int getMediaProviderAppId(Context context) {
        if (sMediaProviderAppId != -1) {
            return sMediaProviderAppId;
        }
        context.getPackageManager();
        ProviderInfo resolveContentProvider = context.getPackageManager().resolveContentProvider(AppJankStats.WIDGET_CATEGORY_MEDIA, BatteryStats.HistoryItem.MOST_INTERESTING_STATES);
        if (resolveContentProvider == null) {
            return -1;
        }
        sMediaProviderAppId = UserHandle.getAppId(resolveContentProvider.applicationInfo.uid);
        return sMediaProviderAppId;
    }

    public static class MemoryPipe extends Thread implements AutoCloseable {
        private final byte[] data;
        private final FileDescriptor[] pipe;
        private final boolean sink;

        private MemoryPipe(byte[] bArr, boolean z) throws IOException {
            try {
                this.pipe = Os.pipe();
                this.data = bArr;
                this.sink = z;
            } catch (ErrnoException e) {
                throw e.rethrowAsIOException();
            }
        }

        private MemoryPipe startInternal() {
            super.start();
            return this;
        }

        public static MemoryPipe createSource(byte[] bArr) throws IOException {
            return new MemoryPipe(bArr, false).startInternal();
        }

        public static MemoryPipe createSink(byte[] bArr) throws IOException {
            return new MemoryPipe(bArr, true).startInternal();
        }

        public FileDescriptor getFD() {
            boolean z = this.sink;
            FileDescriptor[] fileDescriptorArr = this.pipe;
            return z ? fileDescriptorArr[1] : fileDescriptorArr[0];
        }

        public FileDescriptor getInternalFD() {
            boolean z = this.sink;
            FileDescriptor[] fileDescriptorArr = this.pipe;
            return z ? fileDescriptorArr[0] : fileDescriptorArr[1];
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            int write;
            FileDescriptor internalFD = getInternalFD();
            int i = 0;
            while (true) {
                try {
                    byte[] bArr = this.data;
                    if (i >= bArr.length) {
                        break;
                    }
                    if (this.sink) {
                        write = Os.read(internalFD, bArr, i, bArr.length - i);
                    } else {
                        write = Os.write(internalFD, bArr, i, bArr.length - i);
                    }
                    i += write;
                } catch (ErrnoException | IOException unused) {
                    if (this.sink) {
                        SystemClock.sleep(TimeUnit.SECONDS.toMillis(1L));
                    }
                    IoUtils.closeQuietly(internalFD);
                    return;
                } catch (Throwable th) {
                    if (this.sink) {
                        SystemClock.sleep(TimeUnit.SECONDS.toMillis(1L));
                    }
                    IoUtils.closeQuietly(internalFD);
                    throw th;
                }
            }
            if (this.sink) {
                SystemClock.sleep(TimeUnit.SECONDS.toMillis(1L));
            }
            IoUtils.closeQuietly(internalFD);
        }

        @Override // java.lang.AutoCloseable
        public void close() throws Exception {
            IoUtils.closeQuietly(getFD());
        }
    }
}
