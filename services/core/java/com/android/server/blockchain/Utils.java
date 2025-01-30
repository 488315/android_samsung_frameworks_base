package com.android.server.blockchain;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class Utils {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0069 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0075 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v3, types: [java.io.FileInputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static byte[] readFile(String str) {
        Throwable th;
        FileInputStream fileInputStream;
        byte[] bArr;
        Exception e;
        int length;
        File file = new File(str);
        StringBuilder sb = new StringBuilder();
        ?? r3 = "In readFile - Path ";
        sb.append("In readFile - Path ");
        sb.append(str);
        Log.d("com.android.server.blockchain.Utils", sb.toString());
        byte[] bArr2 = null;
        try {
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    Log.d("com.android.server.blockchain.Utils", "File Read - Length = " + file.length());
                    length = (int) file.length();
                    bArr = new byte[length];
                } catch (Exception e2) {
                    e = e2;
                    bArr = null;
                    e = e;
                    e.printStackTrace();
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException unused) {
                            Log.d("com.android.server.blockchain.Utils", "Error closing InputStream");
                        }
                    }
                    return bArr;
                }
                try {
                    if (fileInputStream.read(bArr) != length) {
                        Log.d("com.android.server.blockchain.Utils", "File Read Failed");
                    } else {
                        bArr2 = bArr;
                    }
                    try {
                        fileInputStream.close();
                        return bArr2;
                    } catch (IOException unused2) {
                        Log.d("com.android.server.blockchain.Utils", "Error closing InputStream");
                        return bArr2;
                    }
                } catch (Exception e3) {
                    e = e3;
                    e.printStackTrace();
                    if (fileInputStream != null) {
                    }
                    return bArr;
                }
            } catch (Throwable th2) {
                th = th2;
                if (r3 != 0) {
                    try {
                        r3.close();
                    } catch (IOException unused3) {
                        Log.d("com.android.server.blockchain.Utils", "Error closing InputStream");
                    }
                }
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            fileInputStream = null;
            bArr = null;
        } catch (Throwable th3) {
            r3 = 0;
            th = th3;
            if (r3 != 0) {
            }
            throw th;
        }
    }

    public static boolean sendSecureUIAbortIntent(Context context) {
        Log.d("com.android.server.blockchain.Utils", "sendSecureUIAbortIntent");
        Intent intent = new Intent();
        intent.setAction("com.qualcomm.qti.services.secureui.action.ACTION_SUI_ABORT_MSG");
        intent.setPackage("com.qualcomm.qti.services.secureui");
        context.sendBroadcastAsUser(intent, UserHandle.CURRENT);
        return true;
    }
}
