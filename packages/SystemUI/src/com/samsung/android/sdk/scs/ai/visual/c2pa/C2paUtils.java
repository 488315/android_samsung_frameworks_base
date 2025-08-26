package com.samsung.android.sdk.scs.ai.visual.c2pa;

import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.File;
import java.io.IOException;

/* loaded from: classes4.dex */
public class C2paUtils {
    private static final String TAG = "C2paUtils";

    public static String getFileExtension(String str) {
        File file = new File(str);
        if (!file.exists() || !file.canRead()) {
            Log.e(TAG, "getFileExtension: File does not exist or cannot be read/written:" + str);
            return null;
        }
        String name = file.getName();
        int iLastIndexOf = name.lastIndexOf(46);
        if (iLastIndexOf >= 0 && iLastIndexOf < name.length() - 1) {
            return name.substring(iLastIndexOf + 1);
        }
        Log.e(TAG, "getFileExtension: Unable to find extension".concat(name));
        return null;
    }

    public static ParcelFileDescriptor getParcelFileDescriptor(String str) {
        try {
            File file = new File(str);
            if (file.exists() && file.canRead() && file.canWrite()) {
                return ParcelFileDescriptor.open(file, 805306368);
            }
            Log.e(TAG, "File does not exist or cannot be read/written: " + str);
            return null;
        } catch (IOException unused) {
            Log.e(TAG, "IOException Occurred in getParcelFileDescriptor: " + str);
            return null;
        } catch (SecurityException unused2) {
            Log.e(TAG, "SecurityException Occurred in getParcelFileDescriptor: " + str);
            return null;
        }
    }
}
