package okio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.RandomAccessFile;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class JvmSystemFileSystem extends FileSystem {
    @Override // okio.FileSystem
    public void atomicMove(Path path, Path path2) {
        if (path.toFile().renameTo(path2.toFile())) {
            return;
        }
        throw new IOException("failed to move " + path + " to " + path2);
    }

    @Override // okio.FileSystem
    public final void createDirectory(Path path) {
        if (path.toFile().mkdir()) {
            return;
        }
        FileMetadata metadataOrNull = metadataOrNull(path);
        if (metadataOrNull == null || !metadataOrNull.isDirectory) {
            throw new IOException("failed to create directory: " + path);
        }
    }

    @Override // okio.FileSystem
    public final void delete(Path path) {
        if (Thread.interrupted()) {
            throw new InterruptedIOException("interrupted");
        }
        File file = path.toFile();
        if (file.delete() || !file.exists()) {
            return;
        }
        throw new IOException("failed to delete " + path);
    }

    @Override // okio.FileSystem
    public FileMetadata metadataOrNull(Path path) {
        File file = path.toFile();
        boolean isFile = file.isFile();
        boolean isDirectory = file.isDirectory();
        long lastModified = file.lastModified();
        long length = file.length();
        if (isFile || isDirectory || lastModified != 0 || length != 0 || file.exists()) {
            return new FileMetadata(isFile, isDirectory, null, Long.valueOf(length), null, Long.valueOf(lastModified), null, null, 128, null);
        }
        return null;
    }

    @Override // okio.FileSystem
    public final FileHandle openReadOnly(Path path) {
        return new JvmFileHandle(false, new RandomAccessFile(path.toFile(), "r"));
    }

    @Override // okio.FileSystem
    public final FileHandle openReadWrite(Path path) {
        return new JvmFileHandle(true, new RandomAccessFile(path.toFile(), "rw"));
    }

    @Override // okio.FileSystem
    public final Source source(Path path) {
        File file = path.toFile();
        int i = Okio__JvmOkioKt.$r8$clinit;
        return new InputStreamSource(new FileInputStream(file), Timeout.NONE);
    }

    public String toString() {
        return "JvmSystemFileSystem";
    }
}
