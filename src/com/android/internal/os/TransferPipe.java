package com.android.internal.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Slog;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import libcore.io.IoUtils;

/* loaded from: classes5.dex */
public class TransferPipe implements Runnable, Closeable {
    static final boolean DEBUG = false;
    static final long DEFAULT_TIMEOUT = 5000;
    static final String TAG = "TransferPipe";
    String mBufferPrefix;
    boolean mComplete;
    long mEndTime;
    String mFailure;
    final ParcelFileDescriptor[] mFds;
    FileDescriptor mOutFd;
    final Thread mThread;

    interface Caller {
        void go(IInterface iInterface, FileDescriptor fileDescriptor, String str, String[] strArr) throws RemoteException;
    }

    public TransferPipe() throws IOException {
        this(null);
    }

    public TransferPipe(String str) throws IOException {
        this(str, TAG);
    }

    protected TransferPipe(String str, String str2) throws IOException {
        this.mThread = new Thread(this, str2);
        this.mFds = ParcelFileDescriptor.createPipe();
        this.mBufferPrefix = str;
    }

    ParcelFileDescriptor getReadFd() {
        return this.mFds[0];
    }

    public ParcelFileDescriptor getWriteFd() {
        return this.mFds[1];
    }

    public void setBufferPrefix(String str) {
        this.mBufferPrefix = str;
    }

    public static void dumpAsync(IBinder iBinder, FileDescriptor fileDescriptor, String[] strArr) throws IOException, RemoteException {
        goDump(iBinder, fileDescriptor, strArr);
    }

    public static byte[] dumpAsync(IBinder iBinder, String... strArr) throws IOException, RemoteException {
        ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
        try {
            dumpAsync(iBinder, createPipe[1].getFileDescriptor(), strArr);
            createPipe[1].close();
            createPipe[1] = null;
            byte[] bArr = new byte[4096];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                FileInputStream fileInputStream = new FileInputStream(createPipe[0].getFileDescriptor());
                while (true) {
                    try {
                        int read = fileInputStream.read(bArr);
                        if (read != -1) {
                            byteArrayOutputStream.write(bArr, 0, read);
                        } else {
                            fileInputStream.close();
                            byte[] byteArray = byteArrayOutputStream.toByteArray();
                            byteArrayOutputStream.close();
                            return byteArray;
                        }
                    } finally {
                    }
                }
            } finally {
            }
        } finally {
            createPipe[0].close();
            IoUtils.closeQuietly(createPipe[1]);
        }
    }

    static void go(Caller caller, IInterface iInterface, FileDescriptor fileDescriptor, String str, String[] strArr) throws IOException, RemoteException {
        go(caller, iInterface, fileDescriptor, str, strArr, 5000L);
    }

    static void go(Caller caller, IInterface iInterface, FileDescriptor fileDescriptor, String str, String[] strArr, long j) throws IOException, RemoteException {
        if (iInterface.asBinder() instanceof Binder) {
            try {
                caller.go(iInterface, fileDescriptor, str, strArr);
                return;
            } catch (RemoteException unused) {
                return;
            }
        }
        TransferPipe transferPipe = new TransferPipe();
        try {
            caller.go(iInterface, transferPipe.getWriteFd().getFileDescriptor(), str, strArr);
            transferPipe.go(fileDescriptor, j);
            transferPipe.close();
        } catch (Throwable th) {
            try {
                transferPipe.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    static void goDump(IBinder iBinder, FileDescriptor fileDescriptor, String[] strArr) throws IOException, RemoteException {
        goDump(iBinder, fileDescriptor, strArr, 5000L);
    }

    static void goDump(IBinder iBinder, FileDescriptor fileDescriptor, String[] strArr, long j) throws IOException, RemoteException {
        if (iBinder instanceof Binder) {
            try {
                iBinder.dump(fileDescriptor, strArr);
                return;
            } catch (RemoteException unused) {
                return;
            }
        }
        TransferPipe transferPipe = new TransferPipe();
        try {
            iBinder.dumpAsync(transferPipe.getWriteFd().getFileDescriptor(), strArr);
            transferPipe.go(fileDescriptor, j);
            transferPipe.close();
        } catch (Throwable th) {
            try {
                transferPipe.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public void go(FileDescriptor fileDescriptor) throws IOException {
        go(fileDescriptor, 5000L);
    }

    public void go(FileDescriptor fileDescriptor, long j) throws IOException {
        String str;
        try {
            synchronized (this) {
                this.mOutFd = fileDescriptor;
                this.mEndTime = SystemClock.uptimeMillis() + j;
                closeFd(1);
                this.mThread.start();
                while (true) {
                    str = this.mFailure;
                    if (str != null || this.mComplete) {
                        break;
                    }
                    long uptimeMillis = this.mEndTime - SystemClock.uptimeMillis();
                    if (uptimeMillis <= 0) {
                        this.mThread.interrupt();
                        throw new IOException("Timeout");
                    }
                    try {
                        wait(uptimeMillis);
                    } catch (InterruptedException unused) {
                    }
                }
                if (str != null) {
                    throw new IOException(this.mFailure);
                }
            }
        } finally {
            kill();
        }
    }

    void closeFd(int i) {
        ParcelFileDescriptor parcelFileDescriptor = this.mFds[i];
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.close();
            } catch (IOException unused) {
            }
            this.mFds[i] = null;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        kill();
    }

    public void kill() {
        synchronized (this) {
            closeFd(0);
            closeFd(1);
        }
    }

    protected OutputStream getNewOutputStream() {
        return new FileOutputStream(this.mOutFd);
    }

    @Override // java.lang.Runnable
    public void run() {
        byte[] bArr = new byte[1024];
        synchronized (this) {
            ParcelFileDescriptor readFd = getReadFd();
            if (readFd == null) {
                Slog.w(TAG, "Pipe has been closed...");
                return;
            }
            FileInputStream fileInputStream = new FileInputStream(readFd.getFileDescriptor());
            OutputStream newOutputStream = getNewOutputStream();
            String str = this.mBufferPrefix;
            byte[] bytes = str != null ? str.getBytes() : null;
            boolean z = true;
            while (true) {
                try {
                    int read = fileInputStream.read(bArr);
                    if (read <= 0) {
                        this.mThread.isInterrupted();
                        synchronized (this) {
                            this.mComplete = true;
                            notifyAll();
                        }
                        return;
                    }
                    if (bytes == null) {
                        newOutputStream.write(bArr, 0, read);
                    } else {
                        int i = 0;
                        int i2 = 0;
                        while (i < read) {
                            if (bArr[i] != 10) {
                                if (i > i2) {
                                    newOutputStream.write(bArr, i2, i - i2);
                                }
                                if (z) {
                                    newOutputStream.write(bytes);
                                    z = false;
                                }
                                int i3 = i;
                                do {
                                    i3++;
                                    if (i3 >= read) {
                                        break;
                                    }
                                } while (bArr[i3] != 10);
                                if (i3 < read) {
                                    i2 = i;
                                    i = i3;
                                    z = true;
                                } else {
                                    i2 = i;
                                    i = i3;
                                }
                            }
                            i++;
                        }
                        if (read > i2) {
                            newOutputStream.write(bArr, i2, read - i2);
                        }
                    }
                } catch (IOException e) {
                    synchronized (this) {
                        this.mFailure = e.toString();
                        notifyAll();
                        return;
                    }
                }
            }
        }
    }
}
