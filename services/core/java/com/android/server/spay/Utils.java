package com.android.server.spay;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public abstract class Utils {
    public static ArrayList mRegisteredUid = new ArrayList();

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
        Log.d("com.android.server.spay.Utils", sb.toString());
        byte[] bArr2 = null;
        try {
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    Log.d("com.android.server.spay.Utils", "File Read - Length = " + file.length());
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
                            Log.d("com.android.server.spay.Utils", "Error closing InputStream");
                        }
                    }
                    return bArr;
                }
                try {
                    if (fileInputStream.read(bArr) != length) {
                        Log.d("com.android.server.spay.Utils", "File Read Failed");
                    } else {
                        bArr2 = bArr;
                    }
                    try {
                        fileInputStream.close();
                        return bArr2;
                    } catch (IOException unused2) {
                        Log.d("com.android.server.spay.Utils", "Error closing InputStream");
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
                        Log.d("com.android.server.spay.Utils", "Error closing InputStream");
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

    public static boolean deleteDirectory(File file) {
        File[] listFiles;
        if (file.exists() && (listFiles = file.listFiles()) != null) {
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isDirectory()) {
                    deleteDirectory(listFiles[i]);
                } else {
                    listFiles[i].delete();
                }
            }
        }
        return file.delete();
    }

    public static boolean backgroundWhitelist(Context context, String str) {
        return backgroundWhitelist(context, str, false);
    }

    public static boolean backgroundWhitelist(Context context, String str, boolean z) {
        Log.i("com.android.server.spay.Utils", "backgroundWhitelist called, fromInit: " + z);
        if (str == null || (!"com.samsung.android.spay".equals(str) && !"com.samsung.android.spayfw".equals(str) && !"com.samsung.android.spaymini".equals(str) && !"com.samsung.android.samsungpay.gear".equals(str) && !"com.samsung.android.rajaampat".equals(str))) {
            Log.e("com.android.server.spay.Utils", "backgroundWhitelist: bad packageName: " + str);
            return false;
        }
        if (!hasValidSignature(context, str)) {
            Log.e("com.android.server.spay.Utils", "backgroundWhitelist: bad signature or not found: " + str);
            return false;
        }
        try {
            int i = context.getPackageManager().getApplicationInfo(str, 0).uid;
            String num = Integer.toString(i);
            if (i < 1000 || num == null) {
                Log.e("com.android.server.spay.Utils", "backgroundWhitelist: bad uid: " + i + ", uidString: " + num);
                return false;
            }
            Log.d("com.android.server.spay.Utils", "mRegisteredUid: " + mRegisteredUid.toString() + ", uidString: " + num);
            if (mRegisteredUid.contains(num)) {
                Log.d("com.android.server.spay.Utils", "uid already added");
                return true;
            }
            ActivityManager.getService().backgroundAllowlistUid(i);
            if (!z) {
                mRegisteredUid.add(num);
            }
            Log.d("com.android.server.spay.Utils", "call backgroundWhitelistUid done");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("com.android.server.spay.Utils", "backgroundWhitelistUid exception " + e.toString());
            return false;
        }
    }

    public static boolean sendSecureUIAbortIntent(Context context) {
        Log.d("com.android.server.spay.Utils", "sendSecureUIAbortIntent");
        Intent intent = new Intent();
        intent.setAction("com.qualcomm.qti.services.secureui.action.ACTION_SUI_ABORT_MSG");
        intent.setPackage("com.qualcomm.qti.services.secureui");
        context.sendBroadcastAsUser(intent, UserHandle.CURRENT);
        return true;
    }

    public static boolean hasValidSignature(Context context, String str) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager.getPackageInfo("android", 64).signatures[0].equals(packageManager.getPackageInfo(str, 64).signatures[0])) {
                Log.d("com.android.server.spay.Utils", "hasValidSignature, VALID : " + str);
                return true;
            }
        } catch (PackageManager.NameNotFoundException | NullPointerException e) {
            Log.e("com.android.server.spay.Utils", "hasValidSignature, exception " + e.toString());
        }
        return false;
    }
}
