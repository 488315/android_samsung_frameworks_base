package com.samsung.server.wallpaper;

import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.text.TextUtils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.wallpaperbackup.BnRFileHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import libcore.io.IoUtils;

/* loaded from: classes2.dex */
public class AssetFileManager {
    public static final boolean DEBUG_DUMP = Debug.semIsProductDev();

    public void migrateDirectory(int i) {
        File userSystemDirectory = Environment.getUserSystemDirectory(i);
        File file = new File(userSystemDirectory, "wallpaperassets");
        File file2 = new File(userSystemDirectory, "wallpaper_assets");
        if (!file.exists() || file2.exists()) {
            return;
        }
        Log.m132i("AssetFileManager", "migrateDirectory : migration result = " + file.renameTo(file2));
    }

    public boolean writeAssetFiles(int i, int i2, Bundle bundle, String str) {
        boolean z = false;
        if (bundle == null) {
            Log.m132i("AssetFileManager", "writeAssetFiles: bundle is null");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        File baseAssetDir = getBaseAssetDir(i, i2, true);
        if (!baseAssetDir.exists()) {
            baseAssetDir.mkdirs();
            Log.m132i("AssetFileManager", "writeAssetFiles: mkdir = " + baseAssetDir.getAbsolutePath());
        }
        Iterator<String> it = bundle.keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            }
            String next = it.next();
            Log.m132i("AssetFileManager", "writeAssetFiles: " + next);
            if (!writeToFile((ParcelFileDescriptor) bundle.getParcelable(next), new File(baseAssetDir, next), str)) {
                break;
            }
        }
        if (z) {
            removeAssetFiles(i, i2);
            File baseAssetDir2 = getBaseAssetDir(i, i2);
            if (baseAssetDir.exists() && !baseAssetDir2.exists() && baseAssetDir.renameTo(baseAssetDir2)) {
                Log.m132i("AssetFileManager", "rename success!!");
            }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v8 */
    public final boolean writeToFile(ParcelFileDescriptor parcelFileDescriptor, File file, String str) {
        ParcelFileDescriptor parcelFileDescriptor2;
        FileInputStream fileInputStream;
        AutoCloseable autoCloseable;
        ParcelFileDescriptor parcelFileDescriptor3;
        AutoCloseable autoCloseable2;
        InputStream decryptStream;
        FileOutputStream fileOutputStream;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        InputStream inputStream = null;
        try {
            if (TextUtils.isEmpty(str)) {
                decryptStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
                fileInputStream = null;
            } else {
                fileInputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
                try {
                    decryptStream = BnRFileHelper.decryptStream(fileInputStream, str);
                } catch (IOException e) {
                    e = e;
                    parcelFileDescriptor3 = null;
                    autoCloseable2 = null;
                    try {
                        Log.m135w("AssetFileManager", "Error writeToFile", e);
                        IoUtils.closeQuietly(inputStream);
                        IoUtils.closeQuietly(fileInputStream);
                        IoUtils.closeQuietly(autoCloseable2);
                        IoUtils.closeQuietly(parcelFileDescriptor3);
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        parcelFileDescriptor2 = parcelFileDescriptor3;
                        autoCloseable = autoCloseable2;
                        IoUtils.closeQuietly(inputStream);
                        IoUtils.closeQuietly(fileInputStream);
                        IoUtils.closeQuietly(autoCloseable);
                        IoUtils.closeQuietly(parcelFileDescriptor2);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    parcelFileDescriptor2 = null;
                    autoCloseable = null;
                    IoUtils.closeQuietly(inputStream);
                    IoUtils.closeQuietly(fileInputStream);
                    IoUtils.closeQuietly(autoCloseable);
                    IoUtils.closeQuietly(parcelFileDescriptor2);
                    throw th;
                }
            }
            try {
                parcelFileDescriptor2 = ParcelFileDescriptor.open(file, 1006632960);
                if (parcelFileDescriptor2 != null) {
                    try {
                        fileOutputStream = new FileOutputStream(file);
                        try {
                            FileUtils.copy(decryptStream, fileOutputStream);
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            inputStream = fileOutputStream;
                        } catch (IOException e2) {
                            e = e2;
                            inputStream = decryptStream;
                            IOException iOException = e;
                            parcelFileDescriptor3 = parcelFileDescriptor2;
                            e = iOException;
                            autoCloseable2 = fileOutputStream;
                            Log.m135w("AssetFileManager", "Error writeToFile", e);
                            IoUtils.closeQuietly(inputStream);
                            IoUtils.closeQuietly(fileInputStream);
                            IoUtils.closeQuietly(autoCloseable2);
                            IoUtils.closeQuietly(parcelFileDescriptor3);
                            return false;
                        } catch (Throwable th3) {
                            th = th3;
                            inputStream = decryptStream;
                            autoCloseable = fileOutputStream;
                            IoUtils.closeQuietly(inputStream);
                            IoUtils.closeQuietly(fileInputStream);
                            IoUtils.closeQuietly(autoCloseable);
                            IoUtils.closeQuietly(parcelFileDescriptor2);
                            throw th;
                        }
                    } catch (IOException e3) {
                        e = e3;
                        fileOutputStream = inputStream;
                    } catch (Throwable th4) {
                        th = th4;
                        fileOutputStream = inputStream;
                    }
                }
                Log.m132i("AssetFileManager", "writeToFile: file = " + file.getAbsolutePath() + " , complete (" + (SystemClock.elapsedRealtime() - elapsedRealtime) + " ms)");
                IoUtils.closeQuietly(decryptStream);
                IoUtils.closeQuietly(fileInputStream);
                IoUtils.closeQuietly(inputStream);
                IoUtils.closeQuietly(parcelFileDescriptor2);
                return true;
            } catch (IOException e4) {
                e = e4;
                parcelFileDescriptor3 = null;
                autoCloseable2 = null;
                inputStream = decryptStream;
            } catch (Throwable th5) {
                th = th5;
                parcelFileDescriptor2 = null;
                fileOutputStream = 0;
            }
        } catch (IOException e5) {
            e = e5;
            parcelFileDescriptor3 = null;
            fileInputStream = null;
            autoCloseable2 = null;
        } catch (Throwable th6) {
            th = th6;
            parcelFileDescriptor2 = null;
            fileInputStream = null;
            autoCloseable = null;
        }
    }

    public ParcelFileDescriptor getAssetFile(int i, int i2, String str) {
        File file = new File(getBaseAssetDir(i, i2), str);
        if (!file.exists()) {
            Log.m132i("AssetFileManager", "getAssetFile: " + str + " not exists! (which = " + i + ")");
            return null;
        }
        try {
            return ParcelFileDescriptor.open(file, 268435456);
        } catch (FileNotFoundException e) {
            Log.m135w("AssetFileManager", "Error getting wallpaper asset file", e);
            return null;
        }
    }

    public Bundle getAssets(int i, int i2) {
        File baseAssetDir = getBaseAssetDir(i, i2);
        if (baseAssetDir == null || !baseAssetDir.exists()) {
            Log.m132i("AssetFileManager", "getAssets: not exists! (which = " + i + ")");
            return null;
        }
        Bundle bundle = new Bundle();
        try {
            String[] list = baseAssetDir.list();
            if (list != null && list.length > 0) {
                for (int i3 = 0; i3 < list.length; i3++) {
                    bundle.putParcelable(list[i3], ParcelFileDescriptor.open(new File(baseAssetDir, list[i3]), 268435456));
                }
            }
        } catch (FileNotFoundException e) {
            Log.m135w("AssetFileManager", "getAssets: Error getting wallpaper asset file", e);
        }
        return bundle;
    }

    public void removeAssetFiles(int i, int i2) {
        File baseAssetDir = getBaseAssetDir(i, i2);
        Log.m132i("AssetFileManager", "removeAssetFiles: dir = " + baseAssetDir.getAbsolutePath());
        if (!baseAssetDir.exists()) {
            Log.m132i("AssetFileManager", "removeAssetFiles fail. dir not exists");
        } else {
            removeDirectory(baseAssetDir);
        }
    }

    public static void removeDirectory(File file) {
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            Log.m132i("AssetFileManager", "removeDirectory fail. files not exist");
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

    public void moveAssetFiles(int i, int i2, int i3) {
        File baseAssetDir = getBaseAssetDir(i, i3);
        File baseAssetDir2 = getBaseAssetDir(i2, i3);
        removeAssetFiles(i2, i3);
        if (baseAssetDir.renameTo(baseAssetDir2)) {
            return;
        }
        Log.m134w("AssetFileManager", "moveAssetFiles fail. " + baseAssetDir.getAbsolutePath() + " to " + baseAssetDir2.getAbsolutePath());
    }

    public static File getBaseAssetDir(int i, int i2) {
        return getBaseAssetDir(i, i2, false);
    }

    public static File getBaseAssetDir(int i, int i2, boolean z) {
        if (z) {
            return new File(Environment.getUserSystemDirectory(i2), "wallpaper_assets/" + i + "_temp");
        }
        return new File(Environment.getUserSystemDirectory(i2), "wallpaper_assets/" + i);
    }

    public void dump(PrintWriter printWriter, int i) {
        printWriter.println("[AssetFileManager]");
        printDir(printWriter, new File(Environment.getUserSystemDirectory(i), "wallpaper_assets"));
        printWriter.println();
    }

    public final void printDir(PrintWriter printWriter, File file) {
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return;
        }
        File file2 = null;
        for (File file3 : listFiles) {
            String name = file3.getName();
            if (file3.isFile()) {
                long length = file3.length() / 1024;
                printWriter.print("        name = ");
                printWriter.print(name);
                printWriter.print(", size = ");
                printWriter.print(length);
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
                printDir(printWriter, file3);
            }
        }
        if (!DEBUG_DUMP || file2 == null) {
            return;
        }
        printInfoFile(printWriter, file2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void printInfoFile(PrintWriter printWriter, File file) {
        FileInputStream fileInputStream;
        String readLine;
        printWriter.println("{AssetFileInfo}");
        StringBuffer stringBuffer = new StringBuffer();
        FileInputStream fileInputStream2 = null;
        FileInputStream fileInputStream3 = null;
        try {
            try {
                fileInputStream = new FileInputStream(file.getAbsolutePath());
            } catch (Throwable th) {
                th = th;
            }
        } catch (IOException e) {
            e = e;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            while (true) {
                readLine = bufferedReader.readLine();
                if (readLine == 0) {
                    break;
                }
                stringBuffer.append(readLine + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            }
            bufferedReader.close();
            fileInputStream.close();
            IoUtils.closeQuietly(fileInputStream);
            fileInputStream2 = readLine;
        } catch (IOException e2) {
            e = e2;
            fileInputStream3 = fileInputStream;
            e.printStackTrace();
            IoUtils.closeQuietly(fileInputStream3);
            fileInputStream2 = fileInputStream3;
            printWriter.println(stringBuffer);
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            IoUtils.closeQuietly(fileInputStream2);
            throw th;
        }
        printWriter.println(stringBuffer);
    }
}
