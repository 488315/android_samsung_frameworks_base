package com.android.server.backup.utils;

import android.os.ParcelFileDescriptor;
import android.util.Slog;

import com.android.server.backup.Flags;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.OutputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class FullBackupUtils {
    public static void routeSocketDataToOutput(
            ParcelFileDescriptor parcelFileDescriptor, OutputStream outputStream) {
        DataInputStream dataInputStream =
                new DataInputStream(new FileInputStream(parcelFileDescriptor.getFileDescriptor()));
        int i =
                Flags.enableMaxSizeWritesToPipes()
                        ? EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT
                        : 32768;
        byte[] bArr = new byte[i];
        while (true) {
            int readInt = dataInputStream.readInt();
            if (readInt <= 0) {
                return;
            }
            while (readInt > 0) {
                int read = dataInputStream.read(bArr, 0, readInt > i ? i : readInt);
                if (read < 0) {
                    Slog.e(
                            "BackupManagerService",
                            "Unexpectedly reached end of file while reading data");
                    throw new EOFException();
                }
                outputStream.write(bArr, 0, read);
                readInt -= read;
            }
        }
    }
}
