package android.net;

import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;

/* loaded from: classes3.dex */
public class LocalServerSocket implements Closeable {
    private static final int LISTEN_BACKLOG = 50;
    private final LocalSocketImpl impl;
    private final LocalSocketAddress localAddress;

    public LocalServerSocket(String str) throws IOException {
        LocalSocketImpl localSocketImpl = new LocalSocketImpl();
        this.impl = localSocketImpl;
        localSocketImpl.create(2);
        LocalSocketAddress localSocketAddress = new LocalSocketAddress(str);
        this.localAddress = localSocketAddress;
        localSocketImpl.bind(localSocketAddress);
        localSocketImpl.listen(50);
    }

    public LocalServerSocket(FileDescriptor fileDescriptor) throws IOException {
        LocalSocketImpl localSocketImpl = new LocalSocketImpl(fileDescriptor);
        this.impl = localSocketImpl;
        localSocketImpl.listen(50);
        this.localAddress = localSocketImpl.getSockAddress();
    }

    public LocalSocketAddress getLocalSocketAddress() {
        return this.localAddress;
    }

    public LocalSocket accept() throws IOException {
        LocalSocketImpl localSocketImpl = new LocalSocketImpl();
        this.impl.accept(localSocketImpl);
        return LocalSocket.createLocalSocketForAccept(localSocketImpl);
    }

    public FileDescriptor getFileDescriptor() {
        return this.impl.getFileDescriptor();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.impl.close();
    }
}
