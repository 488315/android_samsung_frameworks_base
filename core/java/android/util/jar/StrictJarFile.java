package android.util.jar;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;

import dalvik.system.CloseGuard;

import libcore.io.IoBridge;
import libcore.io.IoUtils;
import libcore.io.Streams;

import java.io.FileDescriptor;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;

public final class StrictJarFile {
    private boolean closed;
    private final FileDescriptor fd;
    private final CloseGuard guard;
    private final boolean isSigned;
    private final StrictJarManifest manifest;
    private final long nativeHandle;
    private final StrictJarVerifier verifier;

    private static native void nativeClose(long j);

    private static native ZipEntry nativeFindEntry(long j, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native ZipEntry nativeNextEntry(long j);

    private static native long nativeOpenJarFile(String str, int i) throws IOException;

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeStartIteration(long j, String str);

    public StrictJarFile(String fileName) throws IOException, SecurityException {
        this(fileName, true, true);
    }

    public StrictJarFile(FileDescriptor fd) throws IOException, SecurityException {
        this(fd, true, true);
    }

    public StrictJarFile(
            FileDescriptor fd, boolean verify, boolean signatureSchemeRollbackProtectionsEnforced)
            throws IOException, SecurityException {
        this(
                "[fd:" + fd.getInt$() + NavigationBarInflaterView.SIZE_MOD_END,
                fd,
                verify,
                signatureSchemeRollbackProtectionsEnforced);
    }

    public StrictJarFile(
            String fileName, boolean verify, boolean signatureSchemeRollbackProtectionsEnforced)
            throws IOException, SecurityException {
        this(
                fileName,
                IoBridge.open(fileName, OsConstants.O_RDONLY),
                verify,
                signatureSchemeRollbackProtectionsEnforced);
    }

    private StrictJarFile(
            String name,
            FileDescriptor fd,
            boolean verify,
            boolean signatureSchemeRollbackProtectionsEnforced)
            throws IOException, SecurityException {
        this.guard = CloseGuard.get();
        this.nativeHandle = nativeOpenJarFile(name, fd.getInt$());
        this.fd = fd;
        boolean z = false;
        try {
            if (verify) {
                HashMap<String, byte[]> metaEntries = getMetaEntries();
                this.manifest =
                        new StrictJarManifest(metaEntries.get("META-INF/MANIFEST.MF"), true);
                this.verifier =
                        new StrictJarVerifier(
                                name,
                                this.manifest,
                                metaEntries,
                                signatureSchemeRollbackProtectionsEnforced);
                Set<String> files = this.manifest.getEntries().keySet();
                for (String file : files) {
                    if (findEntry(file) == null) {
                        throw new SecurityException("File " + file + " in manifest does not exist");
                    }
                }
                if (this.verifier.readCertificates() && this.verifier.isSignedJar()) {
                    z = true;
                }
                this.isSigned = z;
            } else {
                this.isSigned = false;
                this.manifest = null;
                this.verifier = null;
            }
            this.guard.open("close");
        } catch (IOException | SecurityException e) {
            nativeClose(this.nativeHandle);
            IoUtils.closeQuietly(fd);
            this.closed = true;
            throw e;
        }
    }

    public StrictJarManifest getManifest() {
        return this.manifest;
    }

    public Iterator<ZipEntry> iterator() throws IOException {
        return new EntryIterator(this.nativeHandle, "");
    }

    public ZipEntry findEntry(String name) {
        return nativeFindEntry(this.nativeHandle, name);
    }

    public Certificate[][] getCertificateChains(ZipEntry ze) {
        if (this.isSigned) {
            return this.verifier.getCertificateChains(ze.getName());
        }
        return null;
    }

    @Deprecated
    public Certificate[] getCertificates(ZipEntry ze) {
        if (this.isSigned) {
            Certificate[][] certChains = this.verifier.getCertificateChains(ze.getName());
            int count = 0;
            for (Certificate[] certificateArr : certChains) {
                count += certificateArr.length;
            }
            Certificate[] certs = new Certificate[count];
            int i = 0;
            for (Certificate[] chain : certChains) {
                System.arraycopy(chain, 0, certs, i, chain.length);
                i += chain.length;
            }
            return certs;
        }
        return null;
    }

    public InputStream getInputStream(ZipEntry ze) {
        InputStream is = getZipInputStream(ze);
        if (this.isSigned) {
            StrictJarVerifier.VerifierEntry entry = this.verifier.initEntry(ze.getName());
            if (entry == null) {
                return is;
            }
            return new JarFileInputStream(is, ze.getSize(), entry);
        }
        return is;
    }

    public void close() throws IOException {
        if (!this.closed) {
            if (this.guard != null) {
                this.guard.close();
            }
            nativeClose(this.nativeHandle);
            IoUtils.closeQuietly(this.fd);
            this.closed = true;
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.guard != null) {
                this.guard.warnIfOpen();
            }
            close();
        } finally {
            super.finalize();
        }
    }

    private InputStream getZipInputStream(ZipEntry ze) {
        if (ze.getMethod() == 0) {
            return new FDStream(this.fd, ze.getDataOffset(), ze.getDataOffset() + ze.getSize());
        }
        FDStream wrapped =
                new FDStream(
                        this.fd, ze.getDataOffset(), ze.getDataOffset() + ze.getCompressedSize());
        int bufSize = Math.max(1024, (int) Math.min(ze.getSize(), 65535L));
        return new ZipInflaterInputStream(wrapped, new Inflater(true), bufSize, ze);
    }

    static final class EntryIterator implements Iterator<ZipEntry> {
        private final long iterationHandle;
        private ZipEntry nextEntry;

        EntryIterator(long nativeHandle, String prefix) throws IOException {
            this.iterationHandle = StrictJarFile.nativeStartIteration(nativeHandle, prefix);
        }

        @Override // java.util.Iterator
        public ZipEntry next() {
            if (this.nextEntry != null) {
                ZipEntry ze = this.nextEntry;
                this.nextEntry = null;
                return ze;
            }
            return StrictJarFile.nativeNextEntry(this.iterationHandle);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.nextEntry != null) {
                return true;
            }
            ZipEntry ze = StrictJarFile.nativeNextEntry(this.iterationHandle);
            if (ze == null) {
                return false;
            }
            this.nextEntry = ze;
            return true;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private HashMap<String, byte[]> getMetaEntries() throws IOException {
        HashMap<String, byte[]> metaEntries = new HashMap<>();
        Iterator<ZipEntry> entryIterator = new EntryIterator(this.nativeHandle, "META-INF/");
        while (entryIterator.hasNext()) {
            ZipEntry entry = entryIterator.next();
            metaEntries.put(entry.getName(), Streams.readFully(getInputStream(entry)));
        }
        return metaEntries;
    }

    static final class JarFileInputStream extends FilterInputStream {
        private long count;
        private boolean done;
        private final StrictJarVerifier.VerifierEntry entry;

        JarFileInputStream(InputStream is, long size, StrictJarVerifier.VerifierEntry e) {
            super(is);
            this.done = false;
            this.entry = e;
            this.count = size;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read() throws IOException {
            if (this.done) {
                return -1;
            }
            if (this.count > 0) {
                int r = super.read();
                if (r != -1) {
                    this.entry.write(r);
                    this.count--;
                } else {
                    this.count = 0L;
                }
                if (this.count == 0) {
                    this.done = true;
                    this.entry.verify();
                }
                return r;
            }
            this.done = true;
            this.entry.verify();
            return -1;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
            if (this.done) {
                return -1;
            }
            if (this.count > 0) {
                int r = super.read(buffer, byteOffset, byteCount);
                if (r != -1) {
                    int size = r;
                    if (this.count < size) {
                        size = (int) this.count;
                    }
                    this.entry.write(buffer, byteOffset, size);
                    this.count -= size;
                } else {
                    this.count = 0L;
                }
                if (this.count == 0) {
                    this.done = true;
                    this.entry.verify();
                }
                return r;
            }
            this.done = true;
            this.entry.verify();
            return -1;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int available() throws IOException {
            if (this.done) {
                return 0;
            }
            return super.available();
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public long skip(long byteCount) throws IOException {
            return Streams.skipByReading(this, byteCount);
        }
    }

    public static class ZipInflaterInputStream extends InflaterInputStream {
        private long bytesRead;
        private boolean closed;
        private final ZipEntry entry;

        public ZipInflaterInputStream(InputStream is, Inflater inf, int bsize, ZipEntry entry) {
            super(is, inf, bsize);
            this.bytesRead = 0L;
            this.entry = entry;
        }

        @Override // java.util.zip.InflaterInputStream, java.io.FilterInputStream,
                  // java.io.InputStream
        public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
            try {
                int i = super.read(buffer, byteOffset, byteCount);
                if (i != -1) {
                    this.bytesRead += i;
                } else if (this.entry.getSize() != this.bytesRead) {
                    throw new IOException(
                            "Size mismatch on inflated file: "
                                    + this.bytesRead
                                    + " vs "
                                    + this.entry.getSize());
                }
                return i;
            } catch (IOException e) {
                throw new IOException(
                        "Error reading data for "
                                + this.entry.getName()
                                + " near offset "
                                + this.bytesRead,
                        e);
            }
        }

        @Override // java.util.zip.InflaterInputStream, java.io.FilterInputStream,
                  // java.io.InputStream
        public int available() throws IOException {
            if (this.closed || super.available() == 0) {
                return 0;
            }
            return (int) (this.entry.getSize() - this.bytesRead);
        }

        @Override // java.util.zip.InflaterInputStream, java.io.FilterInputStream,
                  // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            super.close();
            this.closed = true;
        }
    }

    public static class FDStream extends InputStream {
        private long endOffset;
        private final FileDescriptor fd;
        private long offset;

        public FDStream(FileDescriptor fd, long initialOffset, long endOffset) {
            this.fd = fd;
            this.offset = initialOffset;
            this.endOffset = endOffset;
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            return this.offset < this.endOffset ? 1 : 0;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            return Streams.readSingleByte(this);
        }

        @Override // java.io.InputStream
        public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
            synchronized (this.fd) {
                long length = this.endOffset - this.offset;
                if (byteCount > length) {
                    byteCount = (int) length;
                }
                try {
                    Os.lseek(this.fd, this.offset, OsConstants.SEEK_SET);
                    int count = IoBridge.read(this.fd, buffer, byteOffset, byteCount);
                    if (count <= 0) {
                        return -1;
                    }
                    this.offset += count;
                    return count;
                } catch (ErrnoException e) {
                    throw new IOException(e);
                }
            }
        }

        @Override // java.io.InputStream
        public long skip(long byteCount) throws IOException {
            if (byteCount > this.endOffset - this.offset) {
                byteCount = this.endOffset - this.offset;
            }
            this.offset += byteCount;
            return byteCount;
        }
    }
}
