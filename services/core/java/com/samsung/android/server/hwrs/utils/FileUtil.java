package com.samsung.android.server.hwrs.utils;

import android.util.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class FileUtil {
    public static void deleteFile(String str) {
        Log.d("[HWRS_SYS]FileUtil", "deleteFile: ".concat(str));
        try {
            Files.deleteIfExists(Paths.get(str, new String[0]));
        } catch (IOException unused) {
            throw new StorageServiceException("deleteFile failed");
        }
    }
}
