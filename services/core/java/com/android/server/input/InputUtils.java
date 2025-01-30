package com.android.server.input;

import android.util.Log;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/* loaded from: classes2.dex */
public abstract class InputUtils {
    public static boolean isPogoKeyboard(int i, int i2, String str) {
        return i == 1256 && i2 == 41013;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0079, code lost:
    
        if (r2 == null) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0042, code lost:
    
        if (r2 == null) goto L35;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int sysfsReadInt(String str, int i) {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        BufferedReader bufferedReader3 = null;
        BufferedReader bufferedReader4 = null;
        BufferedReader bufferedReader5 = null;
        BufferedReader bufferedReader6 = null;
        try {
            try {
                try {
                    bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(str)));
                } catch (Throwable th) {
                    th = th;
                }
            } catch (IOException unused) {
            } catch (NumberFormatException unused2) {
            } catch (Exception unused3) {
            }
        } catch (IOException unused4) {
        }
        try {
            String readLine = bufferedReader2.readLine();
            i = Integer.parseInt(readLine);
            bufferedReader2.close();
            bufferedReader3 = readLine;
        } catch (IOException unused5) {
            bufferedReader4 = bufferedReader2;
            Log.d("InputManager", "Failed to read int from " + str + ", reason: IOException");
            bufferedReader3 = bufferedReader4;
            bufferedReader = bufferedReader4;
        } catch (NumberFormatException unused6) {
            bufferedReader5 = bufferedReader2;
            Log.d("InputManager", "Failed to read int from " + str + ", reason: NumberFormatException");
            bufferedReader3 = bufferedReader5;
            if (bufferedReader5 != null) {
                bufferedReader = bufferedReader5;
                bufferedReader.close();
                bufferedReader3 = bufferedReader;
            }
            return i;
        } catch (Exception unused7) {
            bufferedReader6 = bufferedReader2;
            Log.d("InputManager", "Failed to read int from " + str + ", reason: Exception");
            bufferedReader3 = bufferedReader6;
            bufferedReader = bufferedReader6;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader3 = bufferedReader2;
            if (bufferedReader3 != null) {
                try {
                    bufferedReader3.close();
                } catch (IOException unused8) {
                }
            }
            throw th;
        }
        return i;
    }
}
