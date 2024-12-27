package com.android.server.backup.utils;

import android.util.Slog;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class RandomAccessFileUtils {
    public static void writeBoolean(File file, boolean z) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
            try {
                randomAccessFile.writeBoolean(z);
                randomAccessFile.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.w("BackupManagerService", "Error writing file:" + file.getAbsolutePath(), e);
        }
    }
}
