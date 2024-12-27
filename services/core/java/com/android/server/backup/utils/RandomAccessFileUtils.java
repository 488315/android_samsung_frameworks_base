package com.android.server.backup.utils;

import android.util.Slog;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

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
