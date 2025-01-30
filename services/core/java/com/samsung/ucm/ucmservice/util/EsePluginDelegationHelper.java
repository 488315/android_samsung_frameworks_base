package com.samsung.ucm.ucmservice.util;

import android.text.TextUtils;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;

/* loaded from: classes2.dex */
public class EsePluginDelegationHelper {
    public String rootPath = "/efs/sec_efs/ucm/ese_plugin";

    public synchronized boolean saveToFile(String str, byte[] bArr) {
        Log.d("EsePluginDelegationHelper", "saveToFile() called");
        if (!checkWriteFileValidity(str)) {
            return false;
        }
        return writeDataToFile(str, bArr);
    }

    public boolean checkDirPath() {
        try {
            filesCreateDirectories(Paths.get(this.rootPath, new String[0]));
            return true;
        } catch (IOException e) {
            Log.e("EsePluginDelegationHelper", "checkDirPath() error during creating directory");
            e.printStackTrace();
            return false;
        }
    }

    public synchronized byte[] readFromFile(String str) {
        Log.d("EsePluginDelegationHelper", "readFromFile() called");
        try {
            try {
                return filesReadAllBytes(Paths.get(getFileFullPath(str), new String[0]));
            } catch (FileNotFoundException unused) {
                Log.e("EsePluginDelegationHelper", "readFromFile() " + str + " doesn't exist");
                return null;
            }
        } catch (IOException e) {
            Log.e("EsePluginDelegationHelper", "readFromFile() fail." + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e2) {
            Log.e("EsePluginDelegationHelper", "readFromFile() fail." + e2.getMessage());
            return null;
        }
    }

    public synchronized boolean deleteFile(String str) {
        Log.d("EsePluginDelegationHelper", "deleteFile() called");
        try {
            filesDeleteFile(Paths.get(getFileFullPath(str), new String[0]));
            return true;
        } catch (IOException e) {
            Log.e("EsePluginDelegationHelper", "deleteFile() fail." + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e2) {
            Log.e("EsePluginDelegationHelper", "deleteFile() fail." + e2.getMessage());
            return false;
        }
    }

    public final boolean checkWriteFileValidity(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.e("EsePluginDelegationHelper", "checkWriteFileValidity(). fileName is empty");
            return false;
        }
        if (checkDirPath()) {
            return true;
        }
        Log.e("EsePluginDelegationHelper", "checkWriteFileValidity(). fail to make dir");
        return false;
    }

    public final boolean writeDataToFile(String str, byte[] bArr) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(getFileFullPath(str));
            try {
                fileOutputStream.write(bArr);
                fileOutputStream.close();
                return true;
            } finally {
            }
        } catch (Exception e) {
            Log.e("EsePluginDelegationHelper", "writeDataToFile() error during calling write");
            e.printStackTrace();
            return false;
        }
    }

    public String getFileFullPath(String str) {
        return this.rootPath + "/" + str;
    }

    public void filesCreateDirectories(Path path) {
        Files.createDirectories(path, new FileAttribute[0]);
    }

    public byte[] filesReadAllBytes(Path path) {
        return Files.readAllBytes(path);
    }

    public boolean filesDeleteFile(Path path) {
        return Files.deleteIfExists(path);
    }
}
