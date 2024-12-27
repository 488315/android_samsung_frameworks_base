package com.samsung.android.localeoverlaymanager;

import android.system.ErrnoException;
import android.system.Os;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SevenZFileCopier {
    /* JADX WARN: Code restructure failed: missing block: B:24:0x005f, code lost:

       android.util.Log.i("SevenZFileCopier", "doCopy- fileName: " + r2.name + " fileSize: " + r2.size);
       writeEntryToFile(r12, r0, r2);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized void copyFile(
            android.content.res.AssetManager r9,
            java.lang.String r10,
            java.lang.String r11,
            java.io.File r12) {
        /*
            Method dump skipped, instructions count: 302
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.localeoverlaymanager.SevenZFileCopier.copyFile(android.content.res.AssetManager,"
                    + " java.lang.String, java.lang.String, java.io.File):void");
    }

    public final synchronized void writeEntryToFile(
            File file, SevenZFile sevenZFile, SevenZArchiveEntry sevenZArchiveEntry) {
        byte[] bArr = new byte[EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION];
        int i = (int) sevenZArchiveEntry.size;
        int read =
                sevenZFile.read(
                        Math.min(
                                i,
                                EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION),
                        bArr);
        if (read == -1) {
            throw new IOException("Failed to read file " + sevenZArchiveEntry.name);
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            do {
                try {
                    fileOutputStream.write(bArr, 0, read);
                    fileOutputStream.flush();
                    i -= read;
                    read =
                            sevenZFile.read(
                                    Math.min(
                                            i,
                                            EndpointMonitorConst
                                                    .FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION),
                                    bArr);
                } catch (Throwable th) {
                    try {
                        fileOutputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } while (i > 0);
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
