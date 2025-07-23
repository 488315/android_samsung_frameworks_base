package android.net;

import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructLinger;
import android.system.StructTimeval;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes3.dex */
class LocalSocketImpl {
    private FileDescriptor fd;
    private SocketInputStream fis;
    private SocketOutputStream fos;
    FileDescriptor[] inboundFileDescriptors;
    private boolean mFdCreatedInternally;
    FileDescriptor[] outboundFileDescriptors;
    private Object readMonitor = new Object();
    private Object writeMonitor = new Object();

    private native void bindLocal(FileDescriptor fileDescriptor, String str, int i) throws IOException;

    private native void connectLocal(FileDescriptor fileDescriptor, String str, int i) throws IOException;

    private native Credentials getPeerCredentials_native(FileDescriptor fileDescriptor) throws IOException;

    /* JADX INFO: Access modifiers changed from: private */
    public native int read_native(FileDescriptor fileDescriptor) throws IOException;

    /* JADX INFO: Access modifiers changed from: private */
    public native int readba_native(byte[] bArr, int i, int i2, FileDescriptor fileDescriptor) throws IOException;

    /* JADX INFO: Access modifiers changed from: private */
    public native void write_native(int i, FileDescriptor fileDescriptor) throws IOException;

    /* JADX INFO: Access modifiers changed from: private */
    public native void writeba_native(byte[] bArr, int i, int i2, FileDescriptor fileDescriptor) throws IOException;

    public LocalSocketAddress getSockAddress() throws IOException {
        return null;
    }

    protected boolean supportsUrgentData() {
        return false;
    }

    class SocketInputStream extends InputStream {
        SocketInputStream() {
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            FileDescriptor fileDescriptor = LocalSocketImpl.this.fd;
            if (fileDescriptor == null) {
                throw new IOException("socket closed");
            }
            try {
                return Os.ioctlInt(fileDescriptor, OsConstants.FIONREAD);
            } catch (ErrnoException e) {
                throw e.rethrowAsIOException();
            }
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            LocalSocketImpl.this.close();
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            int read_native;
            synchronized (LocalSocketImpl.this.readMonitor) {
                FileDescriptor fileDescriptor = LocalSocketImpl.this.fd;
                if (fileDescriptor == null) {
                    throw new IOException("socket closed");
                }
                read_native = LocalSocketImpl.this.read_native(fileDescriptor);
            }
            return read_native;
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr) throws IOException {
            return read(bArr, 0, bArr.length);
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            int readba_native;
            synchronized (LocalSocketImpl.this.readMonitor) {
                FileDescriptor fileDescriptor = LocalSocketImpl.this.fd;
                if (fileDescriptor == null) {
                    throw new IOException("socket closed");
                }
                if (i < 0 || i2 < 0 || i + i2 > bArr.length) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                readba_native = LocalSocketImpl.this.readba_native(bArr, i, i2, fileDescriptor);
            }
            return readba_native;
        }
    }

    class SocketOutputStream extends OutputStream {
        SocketOutputStream() {
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            LocalSocketImpl.this.close();
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) throws IOException {
            write(bArr, 0, bArr.length);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            synchronized (LocalSocketImpl.this.writeMonitor) {
                FileDescriptor fileDescriptor = LocalSocketImpl.this.fd;
                if (fileDescriptor == null) {
                    throw new IOException("socket closed");
                }
                if (i < 0 || i2 < 0 || i + i2 > bArr.length) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                LocalSocketImpl.this.writeba_native(bArr, i, i2, fileDescriptor);
            }
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            synchronized (LocalSocketImpl.this.writeMonitor) {
                FileDescriptor fileDescriptor = LocalSocketImpl.this.fd;
                if (fileDescriptor == null) {
                    throw new IOException("socket closed");
                }
                LocalSocketImpl.this.write_native(i, fileDescriptor);
            }
        }
    }

    LocalSocketImpl() {
    }

    LocalSocketImpl(FileDescriptor fileDescriptor) {
        this.fd = fileDescriptor;
    }

    public String toString() {
        return super.toString() + " fd:" + this.fd;
    }

    public void create(int i) throws IOException {
        int i2;
        if (this.fd != null) {
            throw new IOException("LocalSocketImpl already has an fd");
        }
        if (i == 1) {
            i2 = OsConstants.SOCK_DGRAM;
        } else if (i == 2) {
            i2 = OsConstants.SOCK_STREAM;
        } else if (i == 3) {
            i2 = OsConstants.SOCK_SEQPACKET;
        } else {
            throw new IllegalStateException("unknown sockType");
        }
        try {
            this.fd = Os.socket(OsConstants.AF_UNIX, i2, 0);
            this.mFdCreatedInternally = true;
        } catch (ErrnoException e) {
            e.rethrowAsIOException();
        }
    }

    public void close() throws IOException {
        synchronized (this) {
            FileDescriptor fileDescriptor = this.fd;
            if (fileDescriptor == null || !this.mFdCreatedInternally) {
                this.fd = null;
                return;
            }
            try {
                Os.close(fileDescriptor);
            } catch (ErrnoException e) {
                e.rethrowAsIOException();
            }
            this.fd = null;
        }
    }

    protected void connect(LocalSocketAddress localSocketAddress, int i) throws IOException {
        FileDescriptor fileDescriptor = this.fd;
        if (fileDescriptor == null) {
            throw new IOException("socket not created");
        }
        connectLocal(fileDescriptor, localSocketAddress.getName(), localSocketAddress.getNamespace().getId());
    }

    public void bind(LocalSocketAddress localSocketAddress) throws IOException {
        FileDescriptor fileDescriptor = this.fd;
        if (fileDescriptor == null) {
            throw new IOException("socket not created");
        }
        bindLocal(fileDescriptor, localSocketAddress.getName(), localSocketAddress.getNamespace().getId());
    }

    protected void listen(int i) throws IOException {
        FileDescriptor fileDescriptor = this.fd;
        if (fileDescriptor == null) {
            throw new IOException("socket not created");
        }
        try {
            Os.listen(fileDescriptor, i);
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    protected void accept(LocalSocketImpl localSocketImpl) throws IOException {
        FileDescriptor fileDescriptor = this.fd;
        if (fileDescriptor == null) {
            throw new IOException("socket not created");
        }
        try {
            localSocketImpl.fd = Os.accept(fileDescriptor, null);
            localSocketImpl.mFdCreatedInternally = true;
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    protected InputStream getInputStream() throws IOException {
        SocketInputStream socketInputStream;
        if (this.fd == null) {
            throw new IOException("socket not created");
        }
        synchronized (this) {
            if (this.fis == null) {
                this.fis = new SocketInputStream();
            }
            socketInputStream = this.fis;
        }
        return socketInputStream;
    }

    protected OutputStream getOutputStream() throws IOException {
        SocketOutputStream socketOutputStream;
        if (this.fd == null) {
            throw new IOException("socket not created");
        }
        synchronized (this) {
            if (this.fos == null) {
                this.fos = new SocketOutputStream();
            }
            socketOutputStream = this.fos;
        }
        return socketOutputStream;
    }

    protected int available() throws IOException {
        return getInputStream().available();
    }

    protected void shutdownInput() throws IOException {
        FileDescriptor fileDescriptor = this.fd;
        if (fileDescriptor == null) {
            throw new IOException("socket not created");
        }
        try {
            Os.shutdown(fileDescriptor, OsConstants.SHUT_RD);
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    protected void shutdownOutput() throws IOException {
        FileDescriptor fileDescriptor = this.fd;
        if (fileDescriptor == null) {
            throw new IOException("socket not created");
        }
        try {
            Os.shutdown(fileDescriptor, OsConstants.SHUT_WR);
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    protected FileDescriptor getFileDescriptor() {
        return this.fd;
    }

    protected void sendUrgentData(int i) throws IOException {
        throw new RuntimeException("not impled");
    }

    public Object getOption(int i) throws IOException {
        FileDescriptor fileDescriptor = this.fd;
        if (fileDescriptor == null) {
            throw new IOException("socket not created");
        }
        try {
            if (i != 1) {
                if (i != 4) {
                    if (i == 128) {
                        StructLinger structLinger = Os.getsockoptLinger(fileDescriptor, OsConstants.SOL_SOCKET, OsConstants.SO_LINGER);
                        if (!structLinger.isOn()) {
                            return -1;
                        }
                        return Integer.valueOf(structLinger.l_linger);
                    }
                    if (i == 4102) {
                        return Integer.valueOf((int) Os.getsockoptTimeval(fileDescriptor, OsConstants.SOL_SOCKET, OsConstants.SO_SNDTIMEO).toMillis());
                    }
                    if (i != 4097 && i != 4098) {
                        throw new IOException("Unknown option: " + i);
                    }
                }
                return Integer.valueOf(Os.getsockoptInt(this.fd, OsConstants.SOL_SOCKET, javaSoToOsOpt(i)));
            }
            return Integer.valueOf(Os.getsockoptInt(fileDescriptor, OsConstants.IPPROTO_TCP, OsConstants.TCP_NODELAY));
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v4, types: [int] */
    /* JADX WARN: Type inference failed for: r1v9 */
    public void setOption(int i, Object obj) throws IOException {
        int i2;
        ?? r1;
        if (this.fd == null) {
            throw new IOException("socket not created");
        }
        if (obj instanceof Integer) {
            i2 = ((Integer) obj).intValue();
            r1 = -1;
        } else if (obj instanceof Boolean) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            i2 = 0;
            r1 = booleanValue;
        } else {
            throw new IOException("bad value: " + obj);
        }
        try {
            if (i != 1) {
                if (i != 4) {
                    if (i == 128) {
                        Os.setsockoptLinger(this.fd, OsConstants.SOL_SOCKET, OsConstants.SO_LINGER, new StructLinger((int) r1, i2));
                        return;
                    } else if (i == 4102) {
                        StructTimeval fromMillis = StructTimeval.fromMillis(i2);
                        Os.setsockoptTimeval(this.fd, OsConstants.SOL_SOCKET, OsConstants.SO_RCVTIMEO, fromMillis);
                        Os.setsockoptTimeval(this.fd, OsConstants.SOL_SOCKET, OsConstants.SO_SNDTIMEO, fromMillis);
                        return;
                    } else if (i != 4097 && i != 4098) {
                        throw new IOException("Unknown option: " + i);
                    }
                }
                Os.setsockoptInt(this.fd, OsConstants.SOL_SOCKET, javaSoToOsOpt(i), i2);
                return;
            }
            Os.setsockoptInt(this.fd, OsConstants.IPPROTO_TCP, OsConstants.TCP_NODELAY, i2);
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public void setFileDescriptorsForSend(FileDescriptor[] fileDescriptorArr) {
        synchronized (this.writeMonitor) {
            this.outboundFileDescriptors = fileDescriptorArr;
        }
    }

    public FileDescriptor[] getAncillaryFileDescriptors() throws IOException {
        FileDescriptor[] fileDescriptorArr;
        synchronized (this.readMonitor) {
            fileDescriptorArr = this.inboundFileDescriptors;
            this.inboundFileDescriptors = null;
        }
        return fileDescriptorArr;
    }

    public Credentials getPeerCredentials() throws IOException {
        return getPeerCredentials_native(this.fd);
    }

    protected void finalize() throws IOException {
        close();
    }

    private static int javaSoToOsOpt(int i) {
        if (i == 4) {
            return OsConstants.SO_REUSEADDR;
        }
        if (i == 4097) {
            return OsConstants.SO_SNDBUF;
        }
        if (i == 4098) {
            return OsConstants.SO_RCVBUF;
        }
        throw new UnsupportedOperationException("Unknown option: " + i);
    }
}
