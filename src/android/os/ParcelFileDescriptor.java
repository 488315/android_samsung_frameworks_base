package android.os;

import android.os.MessageQueue;
import android.os.Parcelable;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.util.CloseGuard;
import android.util.Log;
import android.util.Slog;
import dalvik.system.VMRuntime;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UncheckedIOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.nio.ByteOrder;
import libcore.io.IoUtils;
import libcore.io.Memory;

/* loaded from: classes3.dex */
public class ParcelFileDescriptor implements Parcelable, Closeable {
    public static final Parcelable.Creator<ParcelFileDescriptor> CREATOR = new Parcelable.Creator<ParcelFileDescriptor>() { // from class: android.os.ParcelFileDescriptor.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelFileDescriptor createFromParcel(Parcel parcel) {
            return new ParcelFileDescriptor(parcel.readRawFileDescriptor(), parcel.readInt() != 0 ? parcel.readRawFileDescriptor() : null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelFileDescriptor[] newArray(int i) {
            return new ParcelFileDescriptor[i];
        }
    };
    private static final int MAX_STATUS = 1024;
    public static final int MODE_APPEND = 33554432;
    public static final int MODE_CREATE = 134217728;
    public static final int MODE_READ_ONLY = 268435456;
    public static final int MODE_READ_WRITE = 805306368;
    public static final int MODE_TRUNCATE = 67108864;

    @Deprecated
    public static final int MODE_WORLD_READABLE = 1;

    @Deprecated
    public static final int MODE_WORLD_WRITEABLE = 2;
    public static final int MODE_WRITE_ONLY = 536870912;
    private static final String TAG = "ParcelFileDescriptor";
    private volatile boolean mClosed;
    private FileDescriptor mCommFd;
    private final FileDescriptor mFd;
    private final CloseGuard mGuard;
    private Status mStatus;
    private byte[] mStatusBuf;
    private final ParcelFileDescriptor mWrapped;

    public interface OnCloseListener {
        void onClose(IOException iOException);
    }

    public void releaseResources() {
    }

    public ParcelFileDescriptor(ParcelFileDescriptor parcelFileDescriptor) {
        this.mGuard = CloseGuard.get();
        this.mWrapped = parcelFileDescriptor;
        this.mFd = null;
        this.mCommFd = null;
        this.mClosed = true;
    }

    public ParcelFileDescriptor(FileDescriptor fileDescriptor) {
        this(fileDescriptor, null);
    }

    public ParcelFileDescriptor(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2) {
        CloseGuard closeGuard = CloseGuard.get();
        this.mGuard = closeGuard;
        if (fileDescriptor == null) {
            throw new NullPointerException("FileDescriptor must not be null");
        }
        this.mWrapped = null;
        this.mFd = fileDescriptor;
        IoUtils.setFdOwner(fileDescriptor, this);
        this.mCommFd = fileDescriptor2;
        if (fileDescriptor2 != null) {
            IoUtils.setFdOwner(fileDescriptor2, this);
        }
        closeGuard.open("close");
    }

    public static ParcelFileDescriptor open(File file, int i) throws FileNotFoundException {
        FileDescriptor openInternal = openInternal(file, i);
        if (openInternal == null) {
            return null;
        }
        return new ParcelFileDescriptor(openInternal);
    }

    public static ParcelFileDescriptor open(File file, int i, Handler handler, OnCloseListener onCloseListener) throws IOException {
        if (handler == null) {
            throw new IllegalArgumentException("Handler must not be null");
        }
        if (onCloseListener == null) {
            throw new IllegalArgumentException("Listener must not be null");
        }
        FileDescriptor openInternal = openInternal(file, i);
        if (openInternal == null) {
            return null;
        }
        return fromFd(openInternal, handler, onCloseListener);
    }

    public static ParcelFileDescriptor wrap(ParcelFileDescriptor parcelFileDescriptor, Handler handler, OnCloseListener onCloseListener) throws IOException {
        FileDescriptor fileDescriptor = new FileDescriptor();
        fileDescriptor.setInt$(parcelFileDescriptor.detachFd());
        return fromFd(fileDescriptor, handler, onCloseListener);
    }

    public static ParcelFileDescriptor fromFd(FileDescriptor fileDescriptor, Handler handler, final OnCloseListener onCloseListener) throws IOException {
        if (handler == null) {
            throw new IllegalArgumentException("Handler must not be null");
        }
        if (onCloseListener == null) {
            throw new IllegalArgumentException("Listener must not be null");
        }
        FileDescriptor[] createCommSocketPair = createCommSocketPair();
        ParcelFileDescriptor parcelFileDescriptor = new ParcelFileDescriptor(fileDescriptor, createCommSocketPair[0]);
        final MessageQueue queue = handler.getLooper().getQueue();
        queue.addOnFileDescriptorEventListener(createCommSocketPair[1], 1, new MessageQueue.OnFileDescriptorEventListener() { // from class: android.os.ParcelFileDescriptor.1
            @Override // android.os.MessageQueue.OnFileDescriptorEventListener
            public int onFileDescriptorEvents(FileDescriptor fileDescriptor2, int i) {
                Status status;
                if ((i & 1) != 0) {
                    status = ParcelFileDescriptor.readCommStatus(fileDescriptor2, new byte[1024]);
                } else {
                    status = (i & 4) != 0 ? new Status(-2) : null;
                }
                if (status == null) {
                    return 1;
                }
                MessageQueue.this.removeOnFileDescriptorEventListener(fileDescriptor2);
                ParcelFileDescriptor.closeInternal(fileDescriptor2);
                onCloseListener.onClose(status.asIOException());
                return 0;
            }
        });
        return parcelFileDescriptor;
    }

    private static FileDescriptor openInternal(File file, int i) throws FileNotFoundException {
        if ((536870912 & i) != 0 && (33554432 & i) == 0 && (67108864 & i) == 0 && (268435456 & i) == 0 && file != null && file.exists()) {
            Slog.wtfQuiet(TAG, "ParcelFileDescriptor.open is called with w without t or a or r, which will have a different behavior beginning in Android Q.\nMode: " + i + "\nFilename: " + file.getPath());
        }
        int translateModePfdToPosix = FileUtils.translateModePfdToPosix(i) | ifAtLeastQ(OsConstants.O_CLOEXEC);
        int i2 = OsConstants.S_IRWXU | OsConstants.S_IRWXG;
        if ((i & 1) != 0) {
            i2 |= OsConstants.S_IROTH;
        }
        if ((i & 2) != 0) {
            i2 |= OsConstants.S_IWOTH;
        }
        try {
            return Os.open(file.getPath(), translateModePfdToPosix, i2);
        } catch (ErrnoException e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void closeInternal(FileDescriptor fileDescriptor) {
        IoUtils.closeQuietly(fileDescriptor);
    }

    public static ParcelFileDescriptor dup(FileDescriptor fileDescriptor) throws IOException {
        try {
            FileDescriptor fileDescriptor2 = new FileDescriptor();
            fileDescriptor2.setInt$(Os.fcntlInt(fileDescriptor, isAtLeastQ() ? OsConstants.F_DUPFD_CLOEXEC : OsConstants.F_DUPFD, 0));
            return new ParcelFileDescriptor(fileDescriptor2);
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public ParcelFileDescriptor dup() throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor.dup();
        }
        return dup(getFileDescriptor());
    }

    public static ParcelFileDescriptor fromFd(int i) throws IOException {
        FileDescriptor fileDescriptor = new FileDescriptor();
        fileDescriptor.setInt$(i);
        try {
            FileDescriptor fileDescriptor2 = new FileDescriptor();
            fileDescriptor2.setInt$(Os.fcntlInt(fileDescriptor, isAtLeastQ() ? OsConstants.F_DUPFD_CLOEXEC : OsConstants.F_DUPFD, 0));
            return new ParcelFileDescriptor(fileDescriptor2);
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public static ParcelFileDescriptor adoptFd(int i) {
        FileDescriptor fileDescriptor = new FileDescriptor();
        fileDescriptor.setInt$(i);
        return new ParcelFileDescriptor(fileDescriptor);
    }

    public static ParcelFileDescriptor fromSocket(Socket socket) {
        FileDescriptor fileDescriptor$ = socket.getFileDescriptor$();
        if (fileDescriptor$ == null) {
            return null;
        }
        try {
            return dup(fileDescriptor$);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static ParcelFileDescriptor fromDatagramSocket(DatagramSocket datagramSocket) {
        FileDescriptor fileDescriptor$ = datagramSocket.getFileDescriptor$();
        if (fileDescriptor$ == null) {
            return null;
        }
        try {
            return dup(fileDescriptor$);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static ParcelFileDescriptor[] createPipe() throws IOException {
        try {
            FileDescriptor[] pipe2 = Os.pipe2(ifAtLeastQ(OsConstants.O_CLOEXEC));
            return new ParcelFileDescriptor[]{new ParcelFileDescriptor(pipe2[0]), new ParcelFileDescriptor(pipe2[1])};
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public static ParcelFileDescriptor[] createReliablePipe() throws IOException {
        try {
            FileDescriptor[] createCommSocketPair = createCommSocketPair();
            FileDescriptor[] pipe2 = Os.pipe2(ifAtLeastQ(OsConstants.O_CLOEXEC));
            return new ParcelFileDescriptor[]{new ParcelFileDescriptor(pipe2[0], createCommSocketPair[0]), new ParcelFileDescriptor(pipe2[1], createCommSocketPair[1])};
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public static ParcelFileDescriptor[] createSocketPair() throws IOException {
        return createSocketPair(OsConstants.SOCK_STREAM);
    }

    public static ParcelFileDescriptor[] createSocketPair(int i) throws IOException {
        try {
            FileDescriptor fileDescriptor = new FileDescriptor();
            FileDescriptor fileDescriptor2 = new FileDescriptor();
            Os.socketpair(OsConstants.AF_UNIX, i | ifAtLeastQ(OsConstants.SOCK_CLOEXEC), 0, fileDescriptor, fileDescriptor2);
            return new ParcelFileDescriptor[]{new ParcelFileDescriptor(fileDescriptor), new ParcelFileDescriptor(fileDescriptor2)};
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public static ParcelFileDescriptor[] createReliableSocketPair() throws IOException {
        return createReliableSocketPair(OsConstants.SOCK_STREAM);
    }

    public static ParcelFileDescriptor[] createReliableSocketPair(int i) throws IOException {
        try {
            FileDescriptor[] createCommSocketPair = createCommSocketPair();
            FileDescriptor fileDescriptor = new FileDescriptor();
            FileDescriptor fileDescriptor2 = new FileDescriptor();
            Os.socketpair(OsConstants.AF_UNIX, i | ifAtLeastQ(OsConstants.SOCK_CLOEXEC), 0, fileDescriptor, fileDescriptor2);
            return new ParcelFileDescriptor[]{new ParcelFileDescriptor(fileDescriptor, createCommSocketPair[0]), new ParcelFileDescriptor(fileDescriptor2, createCommSocketPair[1])};
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    private static FileDescriptor[] createCommSocketPair() throws IOException {
        try {
            FileDescriptor fileDescriptor = new FileDescriptor();
            FileDescriptor fileDescriptor2 = new FileDescriptor();
            Os.socketpair(OsConstants.AF_UNIX, OsConstants.SOCK_SEQPACKET | ifAtLeastQ(OsConstants.SOCK_CLOEXEC), 0, fileDescriptor, fileDescriptor2);
            IoUtils.setBlocking(fileDescriptor, false);
            IoUtils.setBlocking(fileDescriptor2, false);
            return new FileDescriptor[]{fileDescriptor, fileDescriptor2};
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    @Deprecated
    public static ParcelFileDescriptor fromData(byte[] bArr, String str) throws IOException {
        if (bArr == null) {
            return null;
        }
        MemoryFile memoryFile = new MemoryFile(str, bArr.length);
        try {
            if (bArr.length > 0) {
                memoryFile.writeBytes(bArr, 0, 0, bArr.length);
            }
            memoryFile.deactivate();
            FileDescriptor fileDescriptor = memoryFile.getFileDescriptor();
            return fileDescriptor != null ? dup(fileDescriptor) : null;
        } finally {
            memoryFile.close();
        }
    }

    public static int parseMode(String str) {
        return FileUtils.translateModePosixToPfd(FileUtils.translateModeStringToPosix(str));
    }

    public static File getFile(FileDescriptor fileDescriptor) throws IOException {
        try {
            String readlink = Os.readlink("/proc/self/fd/" + fileDescriptor.getInt$());
            if (!OsConstants.S_ISREG(Os.stat(readlink).st_mode) && !OsConstants.S_ISCHR(Os.stat(readlink).st_mode)) {
                throw new IOException("Not a regular file or character device: " + readlink);
            }
            return new File(readlink);
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public FileDescriptor getFileDescriptor() {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor.getFileDescriptor();
        }
        return this.mFd;
    }

    public long getStatSize() {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor.getStatSize();
        }
        try {
            StructStat fstat = Os.fstat(this.mFd);
            if (!OsConstants.S_ISREG(fstat.st_mode) && !OsConstants.S_ISLNK(fstat.st_mode)) {
                return -1L;
            }
            return fstat.st_size;
        } catch (ErrnoException e) {
            Log.w(TAG, "fstat() failed: " + e);
            return -1L;
        }
    }

    public long seekTo(long j) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor.seekTo(j);
        }
        try {
            return Os.lseek(this.mFd, j, OsConstants.SEEK_SET);
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public int getFd() {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor.getFd();
        }
        if (this.mClosed) {
            throw new IllegalStateException("Already closed");
        }
        return this.mFd.getInt$();
    }

    public int detachFd() {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor.detachFd();
        }
        if (this.mClosed) {
            throw new IllegalStateException("Already closed");
        }
        int acquireRawFd = IoUtils.acquireRawFd(this.mFd);
        writeCommStatusAndClose(2, null);
        this.mClosed = true;
        this.mGuard.close();
        releaseResources();
        return acquireRawFd;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.close();
                return;
            } finally {
                releaseResources();
            }
        }
        closeWithStatus(0, null);
    }

    public void closeWithError(String str) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.closeWithError(str);
            } finally {
                releaseResources();
            }
        } else {
            if (str == null) {
                throw new IllegalArgumentException("Message must not be null");
            }
            closeWithStatus(1, str);
        }
    }

    private void closeWithStatus(int i, String str) {
        if (this.mClosed) {
            return;
        }
        this.mClosed = true;
        CloseGuard closeGuard = this.mGuard;
        if (closeGuard != null) {
            closeGuard.close();
        }
        writeCommStatusAndClose(i, str);
        closeInternal(this.mFd);
        releaseResources();
    }

    private byte[] getOrCreateStatusBuffer() {
        if (this.mStatusBuf == null) {
            this.mStatusBuf = new byte[1024];
        }
        return this.mStatusBuf;
    }

    private void writeCommStatusAndClose(int i, String str) {
        if (this.mCommFd == null) {
            if (str != null) {
                Log.w(TAG, "Unable to inform peer: " + str);
                return;
            }
            return;
        }
        if (i == 2) {
            Log.w(TAG, "Peer expected signal when closed; unable to deliver after detach");
        }
        if (i == -1) {
            return;
        }
        try {
            Status readCommStatus = readCommStatus(this.mCommFd, getOrCreateStatusBuffer());
            this.mStatus = readCommStatus;
            if (readCommStatus != null) {
                return;
            }
            try {
                try {
                    byte[] orCreateStatusBuffer = getOrCreateStatusBuffer();
                    Memory.pokeInt(orCreateStatusBuffer, 0, i, ByteOrder.BIG_ENDIAN);
                    int i2 = 4;
                    if (str != null) {
                        byte[] bytes = str.getBytes();
                        int min = Math.min(bytes.length, orCreateStatusBuffer.length - 4);
                        System.arraycopy(bytes, 0, orCreateStatusBuffer, 4, min);
                        i2 = 4 + min;
                    }
                    Os.write(this.mCommFd, orCreateStatusBuffer, 0, i2);
                } catch (ErrnoException e) {
                    Log.w(TAG, "Failed to report status: " + e);
                }
            } catch (InterruptedIOException e2) {
                Log.w(TAG, "Failed to report status: " + e2);
            }
        } finally {
            closeInternal(this.mCommFd);
            this.mCommFd = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Status readCommStatus(FileDescriptor fileDescriptor, byte[] bArr) {
        try {
            int read = Os.read(fileDescriptor, bArr, 0, bArr.length);
            if (read == 0) {
                return new Status(-2);
            }
            int peekInt = Memory.peekInt(bArr, 0, ByteOrder.BIG_ENDIAN);
            if (peekInt == 1) {
                return new Status(peekInt, new String(bArr, 4, read - 4));
            }
            return new Status(peekInt);
        } catch (ErrnoException e) {
            if (e.errno == OsConstants.EAGAIN) {
                return null;
            }
            Log.d(TAG, "Failed to read status; assuming dead: " + e);
            return new Status(-2);
        } catch (InterruptedIOException e2) {
            Log.d(TAG, "Failed to read status; assuming dead: " + e2);
            return new Status(-2);
        }
    }

    public boolean canDetectErrors() {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor.canDetectErrors();
        }
        return this.mCommFd != null;
    }

    public void checkError() throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            parcelFileDescriptor.checkError();
            return;
        }
        if (this.mStatus == null) {
            FileDescriptor fileDescriptor = this.mCommFd;
            if (fileDescriptor == null) {
                Log.w(TAG, "Peer didn't provide a comm channel; unable to check for errors");
                return;
            }
            this.mStatus = readCommStatus(fileDescriptor, getOrCreateStatusBuffer());
        }
        Status status = this.mStatus;
        if (status != null && status.status != 0) {
            throw this.mStatus.asIOException();
        }
    }

    public static class AutoCloseInputStream extends FileInputStream {
        private final ParcelFileDescriptor mPfd;

        public AutoCloseInputStream(ParcelFileDescriptor parcelFileDescriptor) {
            super(parcelFileDescriptor.getFileDescriptor());
            this.mPfd = parcelFileDescriptor;
        }

        @Override // java.io.FileInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            try {
                super.close();
            } finally {
                this.mPfd.close();
            }
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public int read() throws IOException {
            int read = super.read();
            if (read == -1 && this.mPfd.canDetectErrors()) {
                this.mPfd.checkError();
            }
            return read;
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public int read(byte[] bArr) throws IOException {
            int read = super.read(bArr);
            if (read == -1 && this.mPfd.canDetectErrors()) {
                this.mPfd.checkError();
            }
            return read;
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            int read = super.read(bArr, i, i2);
            if (read == -1 && this.mPfd.canDetectErrors()) {
                this.mPfd.checkError();
            }
            return read;
        }
    }

    public static class KumihoInputStream extends AutoCloseInputStream {
        public KumihoInputStream(ParcelFileDescriptor parcelFileDescriptor) throws IOException {
            super(parcelFileDescriptor);
        }
    }

    public static class AutoCloseOutputStream extends FileOutputStream {
        private final ParcelFileDescriptor mPfd;

        public AutoCloseOutputStream(ParcelFileDescriptor parcelFileDescriptor) {
            super(parcelFileDescriptor.getFileDescriptor());
            this.mPfd = parcelFileDescriptor;
        }

        @Override // java.io.FileOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            try {
                super.close();
            } finally {
                this.mPfd.close();
            }
        }
    }

    public String toString() {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor.toString();
        }
        return "{ParcelFileDescriptor: " + this.mFd + "}";
    }

    protected void finalize() throws Throwable {
        if (this.mWrapped != null) {
            releaseResources();
        }
        CloseGuard closeGuard = this.mGuard;
        if (closeGuard != null) {
            closeGuard.warnIfOpen();
        }
        try {
            if (!this.mClosed) {
                closeWithStatus(3, null);
            }
        } finally {
            super.finalize();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor.describeContents();
        }
        return 1;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.writeToParcel(parcel, i);
                return;
            } finally {
                releaseResources();
            }
        }
        if (this.mCommFd != null) {
            parcel.writeInt(1);
            parcel.writeFileDescriptor(this.mFd);
            parcel.writeFileDescriptor(this.mCommFd);
        } else {
            parcel.writeInt(0);
            parcel.writeFileDescriptor(this.mFd);
        }
        if ((i & 1) == 0 || this.mClosed) {
            return;
        }
        closeWithStatus(-1, null);
    }

    public static class FileDescriptorDetachedException extends IOException {
        private static final long serialVersionUID = 955542466045L;

        public FileDescriptorDetachedException() {
            super("Remote side is detached");
        }
    }

    private static class Status {
        public static final int DEAD = -2;
        public static final int DETACHED = 2;
        public static final int ERROR = 1;
        public static final int LEAKED = 3;
        public static final int OK = 0;
        public static final int SILENCE = -1;
        public final String msg;
        public final int status;

        public Status(int i) {
            this(i, null);
        }

        public Status(int i, String str) {
            this.status = i;
            this.msg = str;
        }

        public IOException asIOException() {
            int i = this.status;
            if (i == -2) {
                return new IOException("Remote side is dead");
            }
            if (i == 0) {
                return null;
            }
            if (i == 1) {
                return new IOException("Remote error: " + this.msg);
            }
            if (i == 2) {
                return new FileDescriptorDetachedException();
            }
            if (i == 3) {
                return new IOException("Remote side was leaked");
            }
            return new IOException("Unknown status: " + this.status);
        }

        public String toString() {
            return "{" + this.status + ": " + this.msg + "}";
        }
    }

    private static boolean isAtLeastQ() {
        return VMRuntime.getRuntime().getTargetSdkVersion() >= 29;
    }

    private static int ifAtLeastQ(int i) {
        if (isAtLeastQ()) {
            return i;
        }
        return 0;
    }
}
