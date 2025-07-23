package android.util;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.FileUtils;
import com.samsung.android.os.ReliableWrite;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Consumer;
import libcore.io.IoUtils;

/* loaded from: classes4.dex */
public class AtomicFile {
    private static final String LOG_TAG = "AtomicFile";
    private final File mBaseName;
    private SystemConfigFileCommitEventLogger mCommitEventLogger;
    private final File mLegacyBackupName;
    private final File mNewName;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AtomicFile(File file) {
        this(file, (SystemConfigFileCommitEventLogger) null);
    }

    public AtomicFile(File file, String str) {
        this(file, new SystemConfigFileCommitEventLogger(str));
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public AtomicFile(File file, SystemConfigFileCommitEventLogger systemConfigFileCommitEventLogger) {
        this.mBaseName = file;
        this.mNewName = new File(file.getPath() + ".new");
        this.mLegacyBackupName = new File(file.getPath() + ".bak");
        this.mCommitEventLogger = systemConfigFileCommitEventLogger;
    }

    public File getBaseFile() {
        return this.mBaseName;
    }

    public void delete() {
        this.mBaseName.delete();
        this.mNewName.delete();
        this.mLegacyBackupName.delete();
    }

    public FileOutputStream startWrite() throws IOException {
        return startWrite(0L);
    }

    @Deprecated
    public FileOutputStream startWrite(long j) throws IOException {
        SystemConfigFileCommitEventLogger systemConfigFileCommitEventLogger = this.mCommitEventLogger;
        if (systemConfigFileCommitEventLogger != null) {
            systemConfigFileCommitEventLogger.setStartTime(j);
            this.mCommitEventLogger.onStartWrite();
        }
        if (this.mLegacyBackupName.exists()) {
            rename(this.mLegacyBackupName, this.mBaseName);
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.mNewName);
            ReliableWrite.setReliableWrite(fileOutputStream);
            return fileOutputStream;
        } catch (FileNotFoundException unused) {
            File parentFile = this.mNewName.getParentFile();
            if (!parentFile.mkdirs()) {
                throw new IOException("Failed to create directory for " + this.mNewName);
            }
            FileUtils.setPermissions(parentFile.getPath(), 505, -1, -1);
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(this.mNewName);
                ReliableWrite.setReliableWrite(fileOutputStream2);
                return fileOutputStream2;
            } catch (FileNotFoundException e) {
                throw new IOException("Failed to create new file " + this.mNewName, e);
            }
        }
    }

    public void finishWrite(FileOutputStream fileOutputStream) {
        if (fileOutputStream == null) {
            return;
        }
        if (!FileUtils.sync(fileOutputStream)) {
            Log.e(LOG_TAG, "Failed to sync file output stream");
        }
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to close file output stream", e);
        }
        rename(this.mNewName, this.mBaseName);
        SystemConfigFileCommitEventLogger systemConfigFileCommitEventLogger = this.mCommitEventLogger;
        if (systemConfigFileCommitEventLogger != null) {
            systemConfigFileCommitEventLogger.onFinishWrite();
        }
    }

    public void failWrite(FileOutputStream fileOutputStream) {
        if (fileOutputStream == null) {
            return;
        }
        if (!FileUtils.sync(fileOutputStream)) {
            Log.e(LOG_TAG, "Failed to sync file output stream");
        }
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to close file output stream", e);
        }
        if (this.mNewName.delete()) {
            return;
        }
        Log.e(LOG_TAG, "Failed to delete new file " + this.mNewName);
    }

    @Deprecated
    public void truncate() throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.mBaseName);
            FileUtils.sync(fileOutputStream);
            fileOutputStream.close();
        } catch (FileNotFoundException unused) {
            throw new IOException("Couldn't append " + this.mBaseName);
        } catch (IOException unused2) {
        }
    }

    @Deprecated
    public FileOutputStream openAppend() throws IOException {
        try {
            return new FileOutputStream(this.mBaseName, true);
        } catch (FileNotFoundException unused) {
            throw new IOException("Couldn't append " + this.mBaseName);
        }
    }

    public FileInputStream openRead() throws FileNotFoundException {
        if (this.mLegacyBackupName.exists()) {
            rename(this.mLegacyBackupName, this.mBaseName);
        }
        if (this.mNewName.exists() && this.mBaseName.exists() && !this.mNewName.delete()) {
            Log.e(LOG_TAG, "Failed to delete outdated new file " + this.mNewName);
        }
        return new FileInputStream(this.mBaseName);
    }

    public boolean exists() {
        return this.mBaseName.exists() || this.mLegacyBackupName.exists();
    }

    public long getLastModifiedTime() {
        if (this.mLegacyBackupName.exists()) {
            return this.mLegacyBackupName.lastModified();
        }
        return this.mBaseName.lastModified();
    }

    public byte[] readFully() throws IOException {
        FileInputStream openRead = openRead();
        try {
            byte[] bArr = new byte[openRead.available()];
            int i = 0;
            while (true) {
                int read = openRead.read(bArr, i, bArr.length - i);
                if (read <= 0) {
                    return bArr;
                }
                i += read;
                int available = openRead.available();
                if (available > bArr.length - i) {
                    byte[] bArr2 = new byte[available + i];
                    System.arraycopy(bArr, 0, bArr2, 0, i);
                    bArr = bArr2;
                }
            }
        } finally {
            openRead.close();
        }
    }

    public void write(Consumer<FileOutputStream> consumer) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = startWrite();
            try {
                consumer.accept(fileOutputStream);
                finishWrite(fileOutputStream);
            } catch (Throwable th) {
                th = th;
                try {
                    failWrite(fileOutputStream);
                    throw ExceptionUtils.propagate(th);
                } finally {
                    IoUtils.closeQuietly(fileOutputStream);
                }
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
        }
    }

    public String toString() {
        return "AtomicFile[" + this.mBaseName + NavigationBarInflaterView.SIZE_MOD_END;
    }

    private static void rename(File file, File file2) {
        if (file2.isDirectory() && !file2.delete()) {
            Log.e(LOG_TAG, "Failed to delete file which is a directory " + file2);
        }
        if (file.renameTo(file2)) {
            return;
        }
        Log.e(LOG_TAG, "Failed to rename " + file + " to " + file2);
    }
}
