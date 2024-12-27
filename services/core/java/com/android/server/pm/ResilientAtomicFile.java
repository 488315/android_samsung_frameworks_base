package com.android.server.pm;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.util.Slog;

import com.android.server.ProfileService$1$$ExternalSyntheticOutline0;
import com.android.server.security.FileIntegrity;

import libcore.io.IoUtils;

import com.samsung.android.os.ReliableWrite;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public final class ResilientAtomicFile implements Closeable {
    public final String mDebugName;
    public final File mFile;
    public final int mFileMode;
    public final ReadEventLogger mReadEventLogger;
    public final File mReserveCopy;
    public final File mTemporaryBackup;
    public FileOutputStream mMainOutStream = null;
    public FileInputStream mMainInStream = null;
    public FileOutputStream mReserveOutStream = null;
    public FileInputStream mReserveInStream = null;
    public File mCurrentFile = null;
    public FileInputStream mCurrentInStream = null;

    public interface ReadEventLogger {
        void logEvent(int i, String str);
    }

    public ResilientAtomicFile(
            File file, File file2, File file3, int i, String str, ReadEventLogger readEventLogger) {
        this.mFile = file;
        this.mTemporaryBackup = file2;
        this.mReserveCopy = file3;
        this.mFileMode = i;
        this.mDebugName = str;
        this.mReadEventLogger = readEventLogger;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        IoUtils.closeQuietly(this.mMainOutStream);
        IoUtils.closeQuietly(this.mMainInStream);
        IoUtils.closeQuietly(this.mReserveOutStream);
        IoUtils.closeQuietly(this.mReserveInStream);
        IoUtils.closeQuietly(this.mCurrentInStream);
        this.mMainOutStream = null;
        this.mMainInStream = null;
        this.mReserveOutStream = null;
        this.mReserveInStream = null;
        this.mCurrentInStream = null;
        this.mCurrentFile = null;
    }

    public final void failRead(FileInputStream fileInputStream, Exception exc) {
        if (this.mCurrentInStream != fileInputStream) {
            throw new IllegalStateException("Invalid incoming stream.");
        }
        this.mCurrentInStream = null;
        IoUtils.closeQuietly(fileInputStream);
        ReadEventLogger readEventLogger = this.mReadEventLogger;
        if (readEventLogger != null) {
            readEventLogger.logEvent(
                    6,
                    "!@Error reading "
                            + this.mDebugName
                            + ", removing "
                            + this.mCurrentFile
                            + '\n'
                            + Log.getStackTraceString(exc));
        }
        if (this.mCurrentFile.delete()) {
            this.mCurrentFile = null;
        } else {
            throw new IllegalStateException("Failed to remove " + this.mCurrentFile);
        }
    }

    public final void failWrite(FileOutputStream fileOutputStream) {
        if (this.mMainOutStream != fileOutputStream) {
            throw new IllegalStateException("Invalid incoming stream.");
        }
        close();
        if (!this.mFile.exists() || this.mFile.delete()) {
            return;
        }
        Slog.i("ResilientAtomicFile", "Failed to clean up mangled file: " + this.mFile);
    }

    public final void finishWrite(FileOutputStream fileOutputStream) {
        ParcelFileDescriptor dup;
        FileOutputStream fileOutputStream2 = this.mMainOutStream;
        if (fileOutputStream2 != fileOutputStream) {
            throw new IllegalStateException("Invalid incoming stream.");
        }
        try {
            this.mMainOutStream = null;
            fileOutputStream2.flush();
            FileUtils.sync(fileOutputStream2);
            FileUtils.setPermissions(fileOutputStream2.getFD(), this.mFileMode, -1, -1);
            fileOutputStream2.close();
            this.mTemporaryBackup.delete();
            try {
                FileInputStream fileInputStream = this.mMainInStream;
                try {
                    FileInputStream fileInputStream2 = this.mReserveInStream;
                    try {
                        this.mMainInStream = null;
                        this.mReserveInStream = null;
                        FileOutputStream fileOutputStream3 = this.mReserveOutStream;
                        try {
                            this.mReserveOutStream = null;
                            FileUtils.copy(fileInputStream, fileOutputStream3);
                            fileOutputStream3.flush();
                            FileUtils.sync(fileOutputStream3);
                            FileUtils.setPermissions(
                                    fileOutputStream3.getFD(), this.mFileMode, -1, -1);
                            fileOutputStream3.close();
                            try {
                                dup = ParcelFileDescriptor.dup(fileInputStream.getFD());
                            } catch (IOException e) {
                                Slog.e(
                                        "ResilientAtomicFile",
                                        "Failed to verity-protect " + this.mDebugName,
                                        e);
                            }
                            try {
                                ParcelFileDescriptor dup2 =
                                        ParcelFileDescriptor.dup(fileInputStream2.getFD());
                                try {
                                    FileIntegrity.setUpFsVerity(dup);
                                    FileIntegrity.setUpFsVerity(dup2);
                                    if (dup2 != null) {
                                        dup2.close();
                                    }
                                    if (dup != null) {
                                        dup.close();
                                    }
                                    if (fileInputStream2 != null) {
                                        fileInputStream2.close();
                                    }
                                    if (fileInputStream != null) {
                                        fileInputStream.close();
                                    }
                                } finally {
                                }
                            } catch (Throwable th) {
                                if (dup != null) {
                                    try {
                                        dup.close();
                                    } catch (Throwable th2) {
                                        th.addSuppressed(th2);
                                    }
                                }
                                throw th;
                            }
                        } finally {
                        }
                    } finally {
                    }
                } finally {
                }
            } catch (IOException e2) {
                Slog.e(
                        "ResilientAtomicFile",
                        "Failed to write reserve copy "
                                + this.mDebugName
                                + ": "
                                + this.mReserveCopy,
                        e2);
            }
        } catch (Throwable th3) {
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                }
            }
            throw th3;
        }
    }

    public final FileInputStream openRead() {
        ReadEventLogger readEventLogger;
        if (this.mTemporaryBackup.exists()) {
            try {
                this.mCurrentFile = this.mTemporaryBackup;
                this.mCurrentInStream = new FileInputStream(this.mCurrentFile);
                ReadEventLogger readEventLogger2 = this.mReadEventLogger;
                if (readEventLogger2 != null) {
                    readEventLogger2.logEvent(
                            4, "Need to read from backup " + this.mDebugName + " file");
                }
                if (this.mFile.exists()) {
                    Slog.w(
                            "ResilientAtomicFile",
                            "Cleaning up " + this.mDebugName + " file " + this.mFile);
                    this.mFile.delete();
                }
                this.mReserveCopy.delete();
            } catch (IOException unused) {
            }
        }
        FileInputStream fileInputStream = this.mCurrentInStream;
        if (fileInputStream != null) {
            return fileInputStream;
        }
        if (this.mFile.exists()) {
            this.mCurrentFile = this.mFile;
            this.mCurrentInStream = new FileInputStream(this.mCurrentFile);
        } else if (this.mReserveCopy.exists()) {
            this.mCurrentFile = this.mReserveCopy;
            this.mCurrentInStream = new FileInputStream(this.mCurrentFile);
            ReadEventLogger readEventLogger3 = this.mReadEventLogger;
            if (readEventLogger3 != null) {
                readEventLogger3.logEvent(
                        4, "Need to read from reserve copy " + this.mDebugName + " file");
            }
        }
        if (this.mCurrentInStream == null && (readEventLogger = this.mReadEventLogger) != null) {
            readEventLogger.logEvent(4, "No " + this.mDebugName + " file");
        }
        return this.mCurrentInStream;
    }

    public final FileOutputStream startWrite() {
        if (this.mMainOutStream != null) {
            throw new IllegalStateException("Duplicate startWrite call?");
        }
        new File(this.mFile.getParent()).mkdirs();
        if (this.mFile.exists()) {
            if (this.mTemporaryBackup.exists()) {
                this.mFile.delete();
                ProfileService$1$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Preserving older "),
                        this.mDebugName,
                        " backup",
                        "ResilientAtomicFile");
            } else if (!this.mFile.renameTo(this.mTemporaryBackup)) {
                throw new IOException(
                        AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                                new StringBuilder("Unable to backup "),
                                this.mDebugName,
                                " file, current changes will be lost at reboot"));
            }
        }
        this.mReserveCopy.delete();
        try {
            this.mMainOutStream = new FileOutputStream(this.mFile);
            this.mMainInStream = new FileInputStream(this.mFile);
            this.mReserveOutStream = new FileOutputStream(this.mReserveCopy);
            this.mReserveInStream = new FileInputStream(this.mReserveCopy);
            ReliableWrite.setReliableWrite(this.mMainOutStream);
            ReliableWrite.setReliableWrite(this.mReserveOutStream);
            return this.mMainOutStream;
        } catch (IOException e) {
            close();
            throw e;
        }
    }

    public final String toString() {
        return this.mFile.getPath();
    }
}
