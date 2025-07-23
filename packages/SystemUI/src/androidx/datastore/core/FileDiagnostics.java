package androidx.datastore.core;

import java.io.File;
import java.io.IOException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FileDiagnostics {
    public static final FileDiagnostics INSTANCE = new FileDiagnostics();

    private FileDiagnostics() {
    }

    public static IOException attachFileSystemMessage(File file, IOException iOException) {
        StringBuilder sb = new StringBuilder("Inoperable file:");
        try {
            sb.append(" canonical[" + file.getCanonicalPath() + "] freeSpace[" + file.getFreeSpace() + ']');
        } catch (IOException unused) {
            sb.append(" failed to attach additional metadata");
        }
        return new IOException(sb.toString(), iOException);
    }

    public static IOException attachParentStacktrace(File file, IOException iOException) {
        File parentFile = file.getParentFile();
        return parentFile == null ? attachFileSystemMessage(file, iOException) : parentFile.exists() ? parentFile.isFile() ? parentFile.canRead() ? parentFile.canWrite() ? attachFileSystemMessage(file, iOException) : attachFileSystemMessage(file, iOException) : parentFile.canWrite() ? attachFileSystemMessage(file, iOException) : attachFileSystemMessage(file, iOException) : parentFile.canRead() ? parentFile.canWrite() ? attachFileSystemMessage(file, iOException) : attachFileSystemMessage(file, iOException) : parentFile.canWrite() ? attachFileSystemMessage(file, iOException) : attachFileSystemMessage(file, iOException) : attachFileSystemMessage(file, iOException);
    }
}
