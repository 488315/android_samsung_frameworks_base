package com.android.server.om;

import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.security.FileIntegrity;

import libcore.io.IoUtils;

import com.samsung.android.os.ReliableWrite;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public final class ResilientAtomicFile implements Closeable {
    public final File mFile;
    public final OverlayManagerService mReadEventLogger;
    public final File mReserveCopy;
    public final File mTemporaryBackup;
    public FileOutputStream mMainOutStream = null;
    public FileInputStream mMainInStream = null;
    public FileOutputStream mReserveOutStream = null;
    public FileInputStream mReserveInStream = null;
    public File mCurrentFile = null;
    public FileInputStream mCurrentInStream = null;

    public ResilientAtomicFile(
            File file, File file2, File file3, OverlayManagerService overlayManagerService) {
        this.mFile = file;
        this.mTemporaryBackup = file2;
        this.mReserveCopy = file3;
        this.mReadEventLogger = overlayManagerService;
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
        if (this.mReadEventLogger != null) {
            Slog.e(
                    "ResilientAtomicFile",
                    "!@Error reading overlay manager settings, removing "
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

    public final void finishWrite(FileOutputStream fileOutputStream) {
        ParcelFileDescriptor dup;
        ParcelFileDescriptor dup2;
        FileOutputStream fileOutputStream2 = this.mMainOutStream;
        if (fileOutputStream2 != fileOutputStream) {
            throw new IllegalStateException("Invalid incoming stream.");
        }
        try {
            this.mMainOutStream = null;
            fileOutputStream2.flush();
            FileUtils.sync(fileOutputStream2);
            FileUtils.setPermissions(
                    fileOutputStream2.getFD(),
                    FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED,
                    -1,
                    -1);
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
                                    fileOutputStream3.getFD(),
                                    FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED,
                                    -1,
                                    -1);
                            fileOutputStream3.close();
                            try {
                                dup = ParcelFileDescriptor.dup(fileInputStream.getFD());
                                try {
                                    dup2 = ParcelFileDescriptor.dup(fileInputStream2.getFD());
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
                            } catch (IOException e) {
                                Slog.e(
                                        "ResilientAtomicFile",
                                        "Failed to verity-protect overlay manager settings",
                                        e);
                            }
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
                        } finally {
                        }
                    } finally {
                    }
                } finally {
                }
            } catch (IOException e2) {
                Slog.e(
                        "ResilientAtomicFile",
                        "Failed to write reserve copy overlay manager settings: "
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
        if (this.mTemporaryBackup.exists()) {
            try {
                this.mCurrentFile = this.mTemporaryBackup;
                this.mCurrentInStream = new FileInputStream(this.mCurrentFile);
                if (this.mReadEventLogger != null) {
                    Slog.i(
                            "ResilientAtomicFile",
                            "Need to read from backup overlay manager settings file");
                }
                if (this.mFile.exists()) {
                    Slog.w(
                            "ResilientAtomicFile",
                            "Cleaning up overlay manager settings file " + this.mFile);
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
            if (this.mReadEventLogger != null) {
                Slog.i(
                        "ResilientAtomicFile",
                        "Need to read from reserve copy overlay manager settings file");
            }
        }
        if (this.mCurrentInStream == null && this.mReadEventLogger != null) {
            Slog.i("ResilientAtomicFile", "No overlay manager settings file");
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
                Slog.w("ResilientAtomicFile", "Preserving older overlay manager settings backup");
            } else if (!this.mFile.renameTo(this.mTemporaryBackup)) {
                throw new IOException(
                        "Unable to backup overlay manager settings file, current changes will be"
                            + " lost at reboot");
            }
        }
        this.mReserveCopy.delete();
        this.mMainOutStream = new FileOutputStream(this.mFile);
        this.mMainInStream = new FileInputStream(this.mFile);
        this.mReserveOutStream = new FileOutputStream(this.mReserveCopy);
        this.mReserveInStream = new FileInputStream(this.mReserveCopy);
        ReliableWrite.setReliableWrite(this.mMainOutStream);
        ReliableWrite.setReliableWrite(this.mReserveOutStream);
        return this.mMainOutStream;
    }

    public final String toString() {
        return this.mFile.getPath();
    }
}
