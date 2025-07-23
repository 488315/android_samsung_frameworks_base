package android.util.jar;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.jar.StrictJarVerifier;
import dalvik.system.CloseGuard;
import java.io.FileDescriptor;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import libcore.io.IoBridge;
import libcore.io.IoUtils;
import libcore.io.Streams;

/* loaded from: classes4.dex */
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

    public StrictJarFile(String str) throws IOException, SecurityException {
        this(str, true, true);
    }

    public StrictJarFile(FileDescriptor fileDescriptor) throws IOException, SecurityException {
        this(fileDescriptor, true, true);
    }

    public StrictJarFile(FileDescriptor fileDescriptor, boolean z, boolean z2) throws IOException, SecurityException {
        this("[fd:" + fileDescriptor.getInt$() + NavigationBarInflaterView.SIZE_MOD_END, fileDescriptor, z, z2);
    }

    public StrictJarFile(String str, boolean z, boolean z2) throws IOException, SecurityException {
        this(str, IoBridge.open(str, OsConstants.O_RDONLY), z, z2);
    }

    private StrictJarFile(String str, FileDescriptor fileDescriptor, boolean z, boolean z2) throws IOException, SecurityException {
        this.guard = CloseGuard.get();
        this.nativeHandle = nativeOpenJarFile(str, fileDescriptor.getInt$());
        this.fd = fileDescriptor;
        boolean z3 = false;
        try {
            if (z) {
                HashMap<String, byte[]> metaEntries = getMetaEntries();
                StrictJarManifest strictJarManifest = new StrictJarManifest(metaEntries.get("META-INF/MANIFEST.MF"), true);
                this.manifest = strictJarManifest;
                this.verifier = new StrictJarVerifier(str, strictJarManifest, metaEntries, z2);
                for (String str2 : strictJarManifest.getEntries().keySet()) {
                    if (findEntry(str2) == null) {
                        throw new SecurityException("File " + str2 + " in manifest does not exist");
                    }
                }
                if (this.verifier.readCertificates() && this.verifier.isSignedJar()) {
                    z3 = true;
                }
                this.isSigned = z3;
            } else {
                this.isSigned = false;
                this.manifest = null;
                this.verifier = null;
            }
            this.guard.open("close");
        } catch (IOException | SecurityException e) {
            nativeClose(this.nativeHandle);
            IoUtils.closeQuietly(fileDescriptor);
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

    public ZipEntry findEntry(String str) {
        return nativeFindEntry(this.nativeHandle, str);
    }

    public Certificate[][] getCertificateChains(ZipEntry zipEntry) {
        if (this.isSigned) {
            return this.verifier.getCertificateChains(zipEntry.getName());
        }
        return null;
    }

    @Deprecated
    public Certificate[] getCertificates(ZipEntry zipEntry) {
        if (!this.isSigned) {
            return null;
        }
        Certificate[][] certificateChains = this.verifier.getCertificateChains(zipEntry.getName());
        int i = 0;
        for (Certificate[] certificateArr : certificateChains) {
            i += certificateArr.length;
        }
        Certificate[] certificateArr2 = new Certificate[i];
        int i2 = 0;
        for (Certificate[] certificateArr3 : certificateChains) {
            System.arraycopy(certificateArr3, 0, certificateArr2, i2, certificateArr3.length);
            i2 += certificateArr3.length;
        }
        return certificateArr2;
    }

    public InputStream getInputStream(ZipEntry zipEntry) {
        StrictJarVerifier.VerifierEntry initEntry;
        InputStream zipInputStream = getZipInputStream(zipEntry);
        return (!this.isSigned || (initEntry = this.verifier.initEntry(zipEntry.getName())) == null) ? zipInputStream : new JarFileInputStream(zipInputStream, zipEntry.getSize(), initEntry);
    }

    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        CloseGuard closeGuard = this.guard;
        if (closeGuard != null) {
            closeGuard.close();
        }
        nativeClose(this.nativeHandle);
        IoUtils.closeQuietly(this.fd);
        this.closed = true;
    }

    protected void finalize() throws Throwable {
        try {
            CloseGuard closeGuard = this.guard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
            }
            close();
        } finally {
            super.finalize();
        }
    }

    private InputStream getZipInputStream(ZipEntry zipEntry) {
        if (zipEntry.getMethod() == 0) {
            return new FDStream(this.fd, zipEntry.getDataOffset(), zipEntry.getDataOffset() + zipEntry.getSize());
        }
        return new ZipInflaterInputStream(new FDStream(this.fd, zipEntry.getDataOffset(), zipEntry.getCompressedSize() + zipEntry.getDataOffset()), new Inflater(true), Math.max(1024, (int) Math.min(zipEntry.getSize(), 65535L)), zipEntry);
    }

    static final class EntryIterator implements Iterator<ZipEntry> {
        private final long iterationHandle;
        private ZipEntry nextEntry;

        EntryIterator(long j, String str) throws IOException {
            this.iterationHandle = StrictJarFile.nativeStartIteration(j, str);
        }

        @Override // java.util.Iterator
        public ZipEntry next() {
            ZipEntry zipEntry = this.nextEntry;
            if (zipEntry != null) {
                this.nextEntry = null;
                return zipEntry;
            }
            return StrictJarFile.nativeNextEntry(this.iterationHandle);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.nextEntry != null) {
                return true;
            }
            ZipEntry nativeNextEntry = StrictJarFile.nativeNextEntry(this.iterationHandle);
            if (nativeNextEntry == null) {
                return false;
            }
            this.nextEntry = nativeNextEntry;
            return true;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private HashMap<String, byte[]> getMetaEntries() throws IOException {
        HashMap<String, byte[]> hashMap = new HashMap<>();
        EntryIterator entryIterator = new EntryIterator(this.nativeHandle, "META-INF/");
        while (entryIterator.hasNext()) {
            ZipEntry next = entryIterator.next();
            hashMap.put(next.getName(), Streams.readFully(getInputStream(next)));
        }
        return hashMap;
    }

    static final class JarFileInputStream extends FilterInputStream {
        private long count;
        private boolean done;
        private final StrictJarVerifier.VerifierEntry entry;

        JarFileInputStream(InputStream inputStream, long j, StrictJarVerifier.VerifierEntry verifierEntry) {
            super(inputStream);
            this.done = false;
            this.entry = verifierEntry;
            this.count = j;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read() throws IOException {
            if (this.done) {
                return -1;
            }
            if (this.count > 0) {
                int read = super.read();
                if (read != -1) {
                    this.entry.write(read);
                    this.count--;
                } else {
                    this.count = 0L;
                }
                if (this.count == 0) {
                    this.done = true;
                    this.entry.verify();
                }
                return read;
            }
            this.done = true;
            this.entry.verify();
            return -1;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            if (this.done) {
                return -1;
            }
            if (this.count > 0) {
                int read = super.read(bArr, i, i2);
                if (read != -1) {
                    long j = this.count;
                    int i3 = j < ((long) read) ? (int) j : read;
                    this.entry.write(bArr, i, i3);
                    this.count -= i3;
                } else {
                    this.count = 0L;
                }
                if (this.count == 0) {
                    this.done = true;
                    this.entry.verify();
                }
                return read;
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
        public long skip(long j) throws IOException {
            return Streams.skipByReading(this, j);
        }
    }

    public static class ZipInflaterInputStream extends InflaterInputStream {
        private long bytesRead;
        private boolean closed;
        private final ZipEntry entry;

        public ZipInflaterInputStream(InputStream inputStream, Inflater inflater, int i, ZipEntry zipEntry) {
            super(inputStream, inflater, i);
            this.bytesRead = 0L;
            this.entry = zipEntry;
        }

        @Override // java.util.zip.InflaterInputStream, java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            try {
                int read = super.read(bArr, i, i2);
                if (read != -1) {
                    this.bytesRead += read;
                    return read;
                }
                if (this.entry.getSize() == this.bytesRead) {
                    return read;
                }
                throw new IOException("Size mismatch on inflated file: " + this.bytesRead + " vs " + this.entry.getSize());
            } catch (IOException e) {
                throw new IOException("Error reading data for " + this.entry.getName() + " near offset " + this.bytesRead, e);
            }
        }

        @Override // java.util.zip.InflaterInputStream, java.io.FilterInputStream, java.io.InputStream
        public int available() throws IOException {
            if (this.closed || super.available() == 0) {
                return 0;
            }
            return (int) (this.entry.getSize() - this.bytesRead);
        }

        @Override // java.util.zip.InflaterInputStream, java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            super.close();
            this.closed = true;
        }
    }

    public static class FDStream extends InputStream {
        private long endOffset;
        private final FileDescriptor fd;
        private long offset;

        public FDStream(FileDescriptor fileDescriptor, long j, long j2) {
            this.fd = fileDescriptor;
            this.offset = j;
            this.endOffset = j2;
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
        public int read(byte[] bArr, int i, int i2) throws IOException {
            synchronized (this.fd) {
                long j = this.endOffset;
                long j2 = this.offset;
                long j3 = j - j2;
                if (i2 > j3) {
                    i2 = (int) j3;
                }
                try {
                    Os.lseek(this.fd, j2, OsConstants.SEEK_SET);
                    int read = IoBridge.read(this.fd, bArr, i, i2);
                    if (read <= 0) {
                        return -1;
                    }
                    this.offset += read;
                    return read;
                } catch (ErrnoException e) {
                    throw new IOException(e);
                }
            }
        }

        @Override // java.io.InputStream
        public long skip(long j) throws IOException {
            long j2 = this.endOffset;
            long j3 = this.offset;
            if (j > j2 - j3) {
                j = j2 - j3;
            }
            this.offset = j3 + j;
            return j;
        }
    }
}
