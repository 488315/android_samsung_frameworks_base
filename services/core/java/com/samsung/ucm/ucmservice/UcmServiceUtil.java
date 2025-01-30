package com.samsung.ucm.ucmservice;

import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/* loaded from: classes2.dex */
public abstract class UcmServiceUtil {
    public static int readIntFromFile(String str) {
        try {
            return Integer.parseInt(readStrFromFile(str));
        } catch (NullPointerException | NumberFormatException unused) {
            return 0;
        }
    }

    public static String readStrFromFile(String str) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(str));
            try {
                String readLine = bufferedReader.readLine();
                bufferedReader.close();
                return readLine;
            } catch (Throwable th) {
                try {
                    bufferedReader.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (FileNotFoundException unused) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean saveDataToFile(byte[] bArr, String str, String str2) {
        new File(str).mkdirs();
        return saveDataToFile(bArr, str + "/" + str2);
    }

    public static boolean saveDataToFile(byte[] bArr, String str) {
        File file = new File(str);
        if (file.exists() && !file.delete()) {
            Log.e("UcmServiceUtil", "failed to delete the existing file");
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                fileOutputStream.write(bArr);
                fileOutputStream.close();
                return true;
            } finally {
            }
        } catch (IOException e) {
            Log.e("UcmServiceUtil", "saveDataToFile. write. IOException.");
            e.printStackTrace();
            file.delete();
            return false;
        }
    }
}
