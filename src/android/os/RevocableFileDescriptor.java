package android.os;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.os.storage.StorageManager;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Slog;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InterruptedIOException;
import libcore.io.IoUtils;

/* loaded from: classes3.dex */
public class RevocableFileDescriptor {
    private static final boolean DEBUG = true;
    private static final String TAG = "RevocableFileDescriptor";
    private final ProxyFileDescriptorCallback mCallback = new ProxyFileDescriptorCallback() { // from class: android.os.RevocableFileDescriptor.1
        private void checkRevoked() throws ErrnoException {
            if (RevocableFileDescriptor.this.mRevoked) {
                throw new ErrnoException(RevocableFileDescriptor.TAG, OsConstants.EPERM);
            }
        }

        @Override // android.os.ProxyFileDescriptorCallback
        public long onGetSize() throws ErrnoException {
            checkRevoked();
            return Os.fstat(RevocableFileDescriptor.this.mInner).st_size;
        }

        @Override // android.os.ProxyFileDescriptorCallback
        public int onRead(long j, int i, byte[] bArr) throws ErrnoException {
            byte[] bArr2;
            checkRevoked();
            int i2 = 0;
            while (i2 < i) {
                try {
                    bArr2 = bArr;
                } catch (InterruptedIOException e) {
                    e = e;
                    bArr2 = bArr;
                }
                try {
                    return i2 + Os.pread(RevocableFileDescriptor.this.mInner, bArr2, i2, i - i2, i2 + j);
                } catch (InterruptedIOException e2) {
                    e = e2;
                    i2 += e.bytesTransferred;
                    bArr = bArr2;
                }
            }
            return i2;
        }

        @Override // android.os.ProxyFileDescriptorCallback
        public int onWrite(long j, int i, byte[] bArr) throws ErrnoException {
            byte[] bArr2;
            checkRevoked();
            int i2 = 0;
            while (i2 < i) {
                try {
                    bArr2 = bArr;
                } catch (InterruptedIOException e) {
                    e = e;
                    bArr2 = bArr;
                }
                try {
                    return i2 + Os.pwrite(RevocableFileDescriptor.this.mInner, bArr2, i2, i - i2, i2 + j);
                } catch (InterruptedIOException e2) {
                    e = e2;
                    i2 += e.bytesTransferred;
                    bArr = bArr2;
                }
            }
            return i2;
        }

        @Override // android.os.ProxyFileDescriptorCallback
        public void onFsync() throws ErrnoException {
            Slog.v(RevocableFileDescriptor.TAG, "onFsync()");
            checkRevoked();
            Os.fsync(RevocableFileDescriptor.this.mInner);
        }

        @Override // android.os.ProxyFileDescriptorCallback
        public void onRelease() {
            Slog.v(RevocableFileDescriptor.TAG, "onRelease()");
            RevocableFileDescriptor.this.mRevoked = true;
            IoUtils.closeQuietly(RevocableFileDescriptor.this.mInner);
            if (RevocableFileDescriptor.this.mOnCloseListener != null) {
                RevocableFileDescriptor.this.mOnCloseListener.onClose(null);
            }
        }
    };
    private FileDescriptor mInner;
    private ParcelFileDescriptor.OnCloseListener mOnCloseListener;
    private ParcelFileDescriptor mOuter;
    private volatile boolean mRevoked;

    public RevocableFileDescriptor() {
    }

    public RevocableFileDescriptor(Context context, File file) throws IOException {
        try {
            init(context, Os.open(file.getAbsolutePath(), OsConstants.O_CREAT | OsConstants.O_RDWR, 448));
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public RevocableFileDescriptor(Context context, FileDescriptor fileDescriptor) throws IOException {
        init(context, fileDescriptor);
    }

    public RevocableFileDescriptor(Context context, FileDescriptor fileDescriptor, Handler handler) throws IOException {
        init(context, fileDescriptor, handler);
    }

    public void init(Context context, FileDescriptor fileDescriptor) throws IOException {
        init(context, fileDescriptor, null);
    }

    public void init(Context context, FileDescriptor fileDescriptor, Handler handler) throws IOException {
        this.mInner = fileDescriptor;
        StorageManager storageManager = (StorageManager) context.getSystemService(StorageManager.class);
        if (handler != null) {
            this.mOuter = storageManager.openProxyFileDescriptor(805306368, this.mCallback, handler);
        } else {
            this.mOuter = storageManager.openProxyFileDescriptor(805306368, this.mCallback);
        }
    }

    public ParcelFileDescriptor getRevocableFileDescriptor() {
        return this.mOuter;
    }

    public void revoke() {
        this.mRevoked = true;
        IoUtils.closeQuietly(this.mInner);
    }

    public void addOnCloseListener(ParcelFileDescriptor.OnCloseListener onCloseListener) {
        this.mOnCloseListener = onCloseListener;
    }

    public boolean isRevoked() {
        return this.mRevoked;
    }
}
