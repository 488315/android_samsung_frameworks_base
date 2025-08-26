package com.android.internal.os;

import android.os.FileUtils;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SyncFailedException;
import java.util.Arrays;

/* loaded from: classes5.dex */
public final class AtomicDirectory {
    private static final String LOG_TAG = "AtomicDirectory";
    private final File mBackupDirectory;
    private final File mBaseDirectory;
    private final ArrayMap<File, FileOutputStream> mOpenFiles = new ArrayMap<>();

    public void finishRead() {
    }

    public AtomicDirectory(File file) {
        Preconditions.checkNotNull(file, "baseDirectory cannot be null");
        this.mBaseDirectory = file;
        this.mBackupDirectory = new File(file.getPath() + "_bak");
    }

    public File getBackupDirectory() {
        return this.mBackupDirectory;
    }

    public File startRead() throws IOException, ErrnoException {
        restore();
        ensureBaseDirectory();
        return this.mBaseDirectory;
    }

    public File startWrite() throws IOException, ErrnoException {
        backup();
        ensureBaseDirectory();
        return this.mBaseDirectory;
    }

    public FileOutputStream openWrite(File file) throws IOException {
        if (file.isDirectory() || !file.getParentFile().equals(this.mBaseDirectory)) {
            throw new IllegalArgumentException("Must be a file in " + this.mBaseDirectory);
        }
        if (this.mOpenFiles.containsKey(file)) {
            throw new IllegalArgumentException("Already open file " + file.getAbsolutePath());
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        this.mOpenFiles.put(file, fileOutputStream);
        return fileOutputStream;
    }

    public void closeWrite(FileOutputStream fileOutputStream) throws SyncFailedException {
        int iIndexOfValue = this.mOpenFiles.indexOfValue(fileOutputStream);
        if (iIndexOfValue < 0) {
            throw new IllegalArgumentException("Unknown file stream " + fileOutputStream);
        }
        this.mOpenFiles.removeAt(iIndexOfValue);
        FileUtils.sync(fileOutputStream);
        FileUtils.closeQuietly(fileOutputStream);
    }

    public void failWrite(FileOutputStream fileOutputStream) {
        int iIndexOfValue = this.mOpenFiles.indexOfValue(fileOutputStream);
        if (iIndexOfValue < 0) {
            throw new IllegalArgumentException("Unknown file stream " + fileOutputStream);
        }
        this.mOpenFiles.removeAt(iIndexOfValue);
        FileUtils.closeQuietly(fileOutputStream);
    }

    public void finishWrite() throws ErrnoException {
        throwIfSomeFilesOpen();
        syncDirectory(this.mBaseDirectory);
        syncParentDirectory();
        deleteDirectory(this.mBackupDirectory);
        syncParentDirectory();
    }

    public void failWrite() throws ErrnoException {
        throwIfSomeFilesOpen();
        try {
            restore();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to restore in failWrite()", e);
        }
    }

    public boolean exists() {
        return this.mBaseDirectory.exists() || this.mBackupDirectory.exists();
    }

    public void delete() throws ErrnoException {
        boolean zDeleteDirectory = this.mBaseDirectory.exists() ? deleteDirectory(this.mBaseDirectory) : false;
        if (this.mBackupDirectory.exists()) {
            zDeleteDirectory |= deleteDirectory(this.mBackupDirectory);
        }
        if (zDeleteDirectory) {
            syncParentDirectory();
        }
    }

    private void ensureBaseDirectory() throws IOException, ErrnoException {
        if (this.mBaseDirectory.exists()) {
            return;
        }
        if (!this.mBaseDirectory.mkdirs()) {
            throw new IOException("Failed to create directory " + this.mBaseDirectory);
        }
        FileUtils.setPermissions(this.mBaseDirectory.getPath(), 505, -1, -1);
    }

    private void throwIfSomeFilesOpen() {
        if (this.mOpenFiles.isEmpty()) {
            return;
        }
        throw new IllegalStateException("Unclosed files: " + Arrays.toString(this.mOpenFiles.keySet().toArray()));
    }

    private void backup() throws IOException, ErrnoException {
        if (this.mBaseDirectory.exists()) {
            if (this.mBackupDirectory.exists()) {
                deleteDirectory(this.mBackupDirectory);
            }
            if (!this.mBaseDirectory.renameTo(this.mBackupDirectory)) {
                throw new IOException("Failed to backup " + this.mBaseDirectory + " to " + this.mBackupDirectory);
            }
            syncParentDirectory();
        }
    }

    private void restore() throws IOException, ErrnoException {
        if (this.mBackupDirectory.exists()) {
            if (this.mBaseDirectory.exists()) {
                deleteDirectory(this.mBaseDirectory);
            }
            if (!this.mBackupDirectory.renameTo(this.mBaseDirectory)) {
                throw new IOException("Failed to restore " + this.mBackupDirectory + " to " + this.mBaseDirectory);
            }
            syncParentDirectory();
        }
    }

    private static boolean deleteDirectory(File file) {
        return FileUtils.deleteContentsAndDir(file);
    }

    private void syncParentDirectory() throws ErrnoException {
        syncDirectory(this.mBaseDirectory.getParentFile());
    }

    private static void syncDirectory(File file) throws ErrnoException {
        FileDescriptor fileDescriptorOpen;
        String absolutePath = file.getAbsolutePath();
        try {
            try {
                fileDescriptorOpen = Os.open(absolutePath, OsConstants.O_RDONLY, 0);
                Os.fsync(fileDescriptorOpen);
            } catch (ErrnoException e) {
                Log.e(LOG_TAG, "Failed to open " + absolutePath, e);
            }
        } catch (ErrnoException e2) {
            Log.e(LOG_TAG, "Failed to fsync " + absolutePath, e2);
        } finally {
            FileUtils.closeQuietly(fileDescriptorOpen);
        }
    }
}
