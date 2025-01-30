package com.android.internal.p029os;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.ParcelFileDescriptor;
import android.p009os.RemoteException;
import android.p009os.SystemClock;
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
        /* renamed from: go */
        void m211go(IInterface iInterface, FileDescriptor fileDescriptor, String str, String[] strArr) throws RemoteException;
    }

    public TransferPipe() throws IOException {
        this(null);
    }

    public TransferPipe(String bufferPrefix) throws IOException {
        this(bufferPrefix, TAG);
    }

    protected TransferPipe(String bufferPrefix, String threadName) throws IOException {
        this.mThread = new Thread(this, threadName);
        this.mFds = ParcelFileDescriptor.createPipe();
        this.mBufferPrefix = bufferPrefix;
    }

    ParcelFileDescriptor getReadFd() {
        return this.mFds[0];
    }

    public ParcelFileDescriptor getWriteFd() {
        return this.mFds[1];
    }

    public void setBufferPrefix(String prefix) {
        this.mBufferPrefix = prefix;
    }

    public static void dumpAsync(IBinder binder, FileDescriptor out, String[] args) throws IOException, RemoteException {
        goDump(binder, out, args);
    }

    public static byte[] dumpAsync(IBinder binder, String... args) throws IOException, RemoteException {
        ParcelFileDescriptor[] pipe = ParcelFileDescriptor.createPipe();
        try {
            dumpAsync(binder, pipe[1].getFileDescriptor(), args);
            pipe[1].close();
            pipe[1] = null;
            byte[] buffer = new byte[4096];
            ByteArrayOutputStream combinedBuffer = new ByteArrayOutputStream();
            try {
                FileInputStream is = new FileInputStream(pipe[0].getFileDescriptor());
                while (true) {
                    try {
                        int numRead = is.read(buffer);
                        if (numRead != -1) {
                            combinedBuffer.write(buffer, 0, numRead);
                        } else {
                            is.close();
                            byte[] byteArray = combinedBuffer.toByteArray();
                            combinedBuffer.close();
                            return byteArray;
                        }
                    } finally {
                    }
                }
            } finally {
            }
        } finally {
            pipe[0].close();
            IoUtils.closeQuietly(pipe[1]);
        }
    }

    /* renamed from: go */
    static void m207go(Caller caller, IInterface iface, FileDescriptor out, String prefix, String[] args) throws IOException, RemoteException {
        m208go(caller, iface, out, prefix, args, 5000L);
    }

    /* renamed from: go */
    static void m208go(Caller caller, IInterface iface, FileDescriptor out, String prefix, String[] args, long timeout) throws IOException, RemoteException {
        if (iface.asBinder() instanceof Binder) {
            try {
                caller.m211go(iface, out, prefix, args);
                return;
            } catch (RemoteException e) {
                return;
            }
        }
        TransferPipe tp = new TransferPipe();
        try {
            caller.m211go(iface, tp.getWriteFd().getFileDescriptor(), prefix, args);
            tp.m210go(out, timeout);
            tp.close();
        } catch (Throwable th) {
            try {
                tp.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    static void goDump(IBinder binder, FileDescriptor out, String[] args) throws IOException, RemoteException {
        goDump(binder, out, args, 5000L);
    }

    static void goDump(IBinder binder, FileDescriptor out, String[] args, long timeout) throws IOException, RemoteException {
        if (binder instanceof Binder) {
            try {
                binder.dump(out, args);
                return;
            } catch (RemoteException e) {
                return;
            }
        }
        TransferPipe tp = new TransferPipe();
        try {
            binder.dumpAsync(tp.getWriteFd().getFileDescriptor(), args);
            tp.m210go(out, timeout);
            tp.close();
        } catch (Throwable th) {
            try {
                tp.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    /* renamed from: go */
    public void m209go(FileDescriptor out) throws IOException {
        m210go(out, 5000L);
    }

    /* renamed from: go */
    public void m210go(FileDescriptor out, long timeout) throws IOException {
        String str;
        try {
            synchronized (this) {
                this.mOutFd = out;
                this.mEndTime = SystemClock.uptimeMillis() + timeout;
                closeFd(1);
                this.mThread.start();
                while (true) {
                    str = this.mFailure;
                    if (str != null || this.mComplete) {
                        break;
                    }
                    long waitTime = this.mEndTime - SystemClock.uptimeMillis();
                    if (waitTime <= 0) {
                        this.mThread.interrupt();
                        throw new IOException("Timeout");
                    }
                    try {
                        wait(waitTime);
                    } catch (InterruptedException e) {
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

    void closeFd(int num) {
        ParcelFileDescriptor parcelFileDescriptor = this.mFds[num];
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.close();
            } catch (IOException e) {
            }
            this.mFds[num] = null;
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
        byte[] buffer = new byte[1024];
        synchronized (this) {
            ParcelFileDescriptor readFd = getReadFd();
            if (readFd == null) {
                Slog.m121w(TAG, "Pipe has been closed...");
                return;
            }
            FileInputStream fis = new FileInputStream(readFd.getFileDescriptor());
            OutputStream fos = getNewOutputStream();
            byte[] bufferPrefix = null;
            boolean needPrefix = true;
            String str = this.mBufferPrefix;
            if (str != null) {
                bufferPrefix = str.getBytes();
            }
            while (true) {
                try {
                    int size = fis.read(buffer);
                    if (size > 0) {
                        if (bufferPrefix == null) {
                            fos.write(buffer, 0, size);
                        } else {
                            int start = 0;
                            int i = 0;
                            while (i < size) {
                                if (buffer[i] != 10) {
                                    if (i > start) {
                                        fos.write(buffer, start, i - start);
                                    }
                                    start = i;
                                    if (needPrefix) {
                                        fos.write(bufferPrefix);
                                        needPrefix = false;
                                    }
                                    do {
                                        i++;
                                        if (i >= size) {
                                            break;
                                        }
                                    } while (buffer[i] != 10);
                                    if (i < size) {
                                        needPrefix = true;
                                    }
                                }
                                i++;
                            }
                            if (size > start) {
                                fos.write(buffer, start, size - start);
                            }
                        }
                    } else {
                        this.mThread.isInterrupted();
                        synchronized (this) {
                            this.mComplete = true;
                            notifyAll();
                        }
                        return;
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
