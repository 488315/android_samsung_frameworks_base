package com.samsung.server.wallpaper;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Debug;
import android.os.Environment;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import libcore.io.IoUtils;

public final class AssetFileManager {
    public static final boolean DEBUG_DUMP = Debug.semIsProductDev();

    public static File getBaseAssetDir(int i, int i2, boolean z) {
        return z ? new File(Environment.getUserSystemDirectory(i2), BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "wallpaper_assets/", "_temp")) : new File(Environment.getUserSystemDirectory(i2), VibrationParam$1$$ExternalSyntheticOutline0.m(i, "wallpaper_assets/"));
    }

    public static void moveAssetFiles(int i, int i2, int i3) {
        File baseAssetDir = getBaseAssetDir(i, i3, false);
        File baseAssetDir2 = getBaseAssetDir(i2, i3, false);
        removeAssetFiles(i2, i3);
        if (baseAssetDir.renameTo(baseAssetDir2)) {
            return;
        }
        Log.w("AssetFileManager", "moveAssetFiles fail. " + baseAssetDir.getAbsolutePath() + " to " + baseAssetDir2.getAbsolutePath());
    }

    public static void printDir(File file, PrintWriter printWriter) {
        IOException e;
        FileInputStream fileInputStream;
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return;
        }
        ?? length = listFiles.length;
        AutoCloseable autoCloseable = null;
        File file2 = null;
        for (File file3 : listFiles) {
            String name = file3.getName();
            if (file3.isFile()) {
                long length2 = file3.length() / 1024;
                printWriter.print("        name = ");
                printWriter.print(name);
                printWriter.print(", size = ");
                printWriter.print(length2);
                printWriter.println("KB");
                int lastIndexOf = name.lastIndexOf(".");
                String substring = name.substring(lastIndexOf + 1);
                if (lastIndexOf != -1 && "xml".equals(substring)) {
                    file2 = file3;
                }
            } else {
                File[] listFiles2 = file3.listFiles();
                if (listFiles2 != null) {
                    printWriter.print("    [which = ");
                    printWriter.print(name);
                    printWriter.print(", num = ");
                    printWriter.print(listFiles2.length);
                    printWriter.println("]");
                }
                printDir(file3, printWriter);
            }
        }
        if (!DEBUG_DUMP || file2 == null) {
            return;
        }
        printWriter.println("{AssetFileInfo}");
        StringBuffer stringBuffer = new StringBuffer();
        try {
            try {
                fileInputStream = new FileInputStream(file2.getAbsolutePath());
            } catch (IOException e2) {
                e = e2;
                fileInputStream = null;
            } catch (Throwable th) {
                th = th;
                IoUtils.closeQuietly(autoCloseable);
                throw th;
            }
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    stringBuffer.append(readLine + "\n");
                }
                bufferedReader.close();
                fileInputStream.close();
            } catch (IOException e3) {
                e = e3;
                e.printStackTrace();
                IoUtils.closeQuietly(fileInputStream);
                printWriter.println(stringBuffer);
            }
            IoUtils.closeQuietly(fileInputStream);
            printWriter.println(stringBuffer);
        } catch (Throwable th2) {
            th = th2;
            autoCloseable = length;
            IoUtils.closeQuietly(autoCloseable);
            throw th;
        }
    }

    public static void removeAssetFiles(int i, int i2) {
        File baseAssetDir = getBaseAssetDir(i, i2, false);
        Log.i("AssetFileManager", "removeAssetFiles: dir = " + baseAssetDir.getAbsolutePath());
        if (baseAssetDir.exists()) {
            removeDirectory(baseAssetDir);
        } else {
            Log.i("AssetFileManager", "removeAssetFiles fail. dir not exists");
        }
    }

    public static void removeDirectory(File file) {
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            Log.i("AssetFileManager", "removeDirectory fail. files not exist");
            return;
        }
        for (File file2 : listFiles) {
            if (file2.isFile()) {
                file2.delete();
            } else {
                removeDirectory(file2);
            }
        }
        file.delete();
    }
}
