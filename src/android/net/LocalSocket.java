package android.net;

import android.annotation.SystemApi;
import android.system.ErrnoException;
import android.system.Os;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes3.dex */
public class LocalSocket implements Closeable {
    public static final int SOCKET_DGRAM = 1;
    public static final int SOCKET_SEQPACKET = 3;
    public static final int SOCKET_STREAM = 2;
    static final int SOCKET_UNKNOWN = 0;
    private final LocalSocketImpl impl;
    private volatile boolean implCreated;
    private boolean isBound;
    private boolean isConnected;
    private LocalSocketAddress localAddress;
    private final int sockType;

    public LocalSocket() {
        this(2);
    }

    public LocalSocket(int i) {
        this(new LocalSocketImpl(), i);
    }

    private LocalSocket(LocalSocketImpl localSocketImpl, int i) {
        this.impl = localSocketImpl;
        this.sockType = i;
        this.isConnected = false;
        this.isBound = false;
    }

    private void checkConnected() throws ErrnoException {
        try {
            Os.getpeername(this.impl.getFileDescriptor());
            this.isConnected = true;
            this.isBound = true;
            this.implCreated = true;
        } catch (ErrnoException e) {
            throw new IllegalArgumentException("Not a connected socket", e);
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public LocalSocket(FileDescriptor fileDescriptor) throws ErrnoException {
        this(new LocalSocketImpl(fileDescriptor), 0);
        checkConnected();
    }

    static LocalSocket createLocalSocketForAccept(LocalSocketImpl localSocketImpl) throws ErrnoException {
        LocalSocket localSocket = new LocalSocket(localSocketImpl, 0);
        localSocket.checkConnected();
        return localSocket;
    }

    public String toString() {
        return super.toString() + " impl:" + this.impl;
    }

    private void implCreateIfNeeded() throws IOException {
        if (this.implCreated) {
            return;
        }
        synchronized (this) {
            if (!this.implCreated) {
                try {
                    this.impl.create(this.sockType);
                    this.implCreated = true;
                } catch (Throwable th) {
                    this.implCreated = true;
                    throw th;
                }
            }
        }
    }

    public void connect(LocalSocketAddress localSocketAddress) throws IOException {
        synchronized (this) {
            if (this.isConnected) {
                throw new IOException("already connected");
            }
            implCreateIfNeeded();
            this.impl.connect(localSocketAddress, 0);
            this.isConnected = true;
            this.isBound = true;
        }
    }

    public void bind(LocalSocketAddress localSocketAddress) throws IOException {
        implCreateIfNeeded();
        synchronized (this) {
            if (this.isBound) {
                throw new IOException("already bound");
            }
            this.localAddress = localSocketAddress;
            this.impl.bind(localSocketAddress);
            this.isBound = true;
        }
    }

    public LocalSocketAddress getLocalSocketAddress() {
        return this.localAddress;
    }

    public InputStream getInputStream() throws IOException {
        implCreateIfNeeded();
        return this.impl.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        implCreateIfNeeded();
        return this.impl.getOutputStream();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        implCreateIfNeeded();
        this.impl.close();
    }

    public void shutdownInput() throws IOException, ErrnoException {
        implCreateIfNeeded();
        this.impl.shutdownInput();
    }

    public void shutdownOutput() throws IOException, ErrnoException {
        implCreateIfNeeded();
        this.impl.shutdownOutput();
    }

    public void setReceiveBufferSize(int i) throws IOException, ErrnoException {
        this.impl.setOption(4098, Integer.valueOf(i));
    }

    public int getReceiveBufferSize() throws IOException {
        return ((Integer) this.impl.getOption(4098)).intValue();
    }

    public void setSoTimeout(int i) throws IOException, ErrnoException {
        this.impl.setOption(4102, Integer.valueOf(i));
    }

    public int getSoTimeout() throws IOException {
        return ((Integer) this.impl.getOption(4102)).intValue();
    }

    public void setSendBufferSize(int i) throws IOException, ErrnoException {
        this.impl.setOption(4097, Integer.valueOf(i));
    }

    public int getSendBufferSize() throws IOException {
        return ((Integer) this.impl.getOption(4097)).intValue();
    }

    public LocalSocketAddress getRemoteSocketAddress() {
        throw new UnsupportedOperationException();
    }

    public synchronized boolean isConnected() {
        return this.isConnected;
    }

    public boolean isClosed() {
        throw new UnsupportedOperationException();
    }

    public synchronized boolean isBound() {
        return this.isBound;
    }

    public boolean isOutputShutdown() {
        throw new UnsupportedOperationException();
    }

    public boolean isInputShutdown() {
        throw new UnsupportedOperationException();
    }

    public void connect(LocalSocketAddress localSocketAddress, int i) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void setFileDescriptorsForSend(FileDescriptor[] fileDescriptorArr) {
        this.impl.setFileDescriptorsForSend(fileDescriptorArr);
    }

    public FileDescriptor[] getAncillaryFileDescriptors() throws IOException {
        return this.impl.getAncillaryFileDescriptors();
    }

    public Credentials getPeerCredentials() throws IOException {
        return this.impl.getPeerCredentials();
    }

    public FileDescriptor getFileDescriptor() {
        return this.impl.getFileDescriptor();
    }
}
