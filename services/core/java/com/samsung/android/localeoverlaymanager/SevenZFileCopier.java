package com.samsung.android.localeoverlaymanager;

import android.content.res.AssetManager;
import android.os.FileUtils;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.compress.utils.SeekableInMemoryByteChannel;

/* loaded from: classes2.dex */
public class SevenZFileCopier implements CompressedAssetCopier {
    public static final String TAG = "SevenZFileCopier";

    /* JADX WARN: Code restructure failed: missing block: B:19:0x004b, code lost:
    
        android.util.Log.i(com.samsung.android.localeoverlaymanager.SevenZFileCopier.TAG, "doCopy- fileName: " + r3.getName() + " fileSize: " + r3.getSize());
        writeEntryToFile(r11, r0, r3);
     */
    @Override // com.samsung.android.localeoverlaymanager.CompressedAssetCopier
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized void copyFile(AssetManager assetManager, String str, String str2, File file) {
        try {
            try {
                InputStream open = assetManager.open(str + ".7z");
                try {
                    byte[] byteArray = IOUtils.toByteArray(open);
                    if (byteArray != null && byteArray.length > 0) {
                        try {
                            SeekableInMemoryByteChannel seekableInMemoryByteChannel = new SeekableInMemoryByteChannel(byteArray);
                            try {
                                SevenZFile sevenZFile = new SevenZFile(seekableInMemoryByteChannel);
                                int i = 0;
                                while (true) {
                                    SevenZArchiveEntry nextEntry = sevenZFile.getNextEntry();
                                    if (nextEntry != null) {
                                        if (nextEntry.getName().equals(str2 + ".apk")) {
                                            break;
                                        }
                                    }
                                    if (nextEntry == null || (i = i + 1) >= 200) {
                                        break;
                                    }
                                }
                                seekableInMemoryByteChannel.close();
                            } finally {
                            }
                        } finally {
                            if (open != null) {
                                try {
                                    open.close();
                                } catch (IOException e) {
                                    Log.e(TAG, "Failed to close input stream " + str + " error :" + e);
                                }
                            }
                        }
                    }
                    FileUtils.setPermissions(file, 509, -1, 1000);
                } catch (IOException e2) {
                    throw new IOException("Failed to generate byte array for file" + str + " error :" + e2);
                }
            } catch (IOException e3) {
                throw new IOException("AssetManager failed to open " + str + ".7z error :" + e3);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void writeEntryToFile(File file, SevenZFile sevenZFile, SevenZArchiveEntry sevenZArchiveEntry) {
        byte[] bArr = new byte[16384];
        int size = (int) sevenZArchiveEntry.getSize();
        int read = sevenZFile.read(bArr, 0, Math.min(size, 16384));
        if (read == -1) {
            throw new IOException("Failed to read file " + sevenZArchiveEntry.getName());
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            do {
                try {
                    fileOutputStream.write(bArr, 0, read);
                    fileOutputStream.flush();
                    size -= read;
                    read = sevenZFile.read(bArr, 0, Math.min(size, 16384));
                } catch (Throwable th) {
                    try {
                        fileOutputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } while (size > 0);
            FileDescriptor fd = fileOutputStream.getFD();
            if (fd != null) {
                try {
                    Os.fsync(fd);
                    try {
                        Os.close(fd);
                    } catch (ErrnoException e) {
                        throw new IOException(e);
                    }
                } catch (ErrnoException e2) {
                    throw new IOException(e2);
                }
            }
            fileOutputStream.close();
        } catch (IOException e3) {
            throw new IOException("Failed to open destinationFile " + file + " error :" + e3);
        }
    }
}
