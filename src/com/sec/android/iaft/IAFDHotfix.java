package com.sec.android.iaft;

import android.content.Context;
import android.util.Log;
import dalvik.system.InMemoryDexClassLoader;
import java.io.File;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class IAFDHotfix {
    private static final String TAG = "IAFT_IAFDHotfix";

    public static boolean hotfix(Context context, int i, String str) {
        if (context == null) {
            Log.i(TAG, "context is null");
            return false;
        }
        try {
            File file = new File(context.getDataDir().getAbsolutePath() + "/iafd/dex/", "iafdrepair_" + Integer.toString(i) + "_dex.bin.enc");
            if (!file.exists()) {
                Log.i(TAG, file.toString() + " not found.");
                return false;
            }
            byte[] decryptFileToBytes = IAFDRSAUtils.decryptFileToBytes(file.toString(), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwaCLv6RvwU8gyFSbynkiPI1Yjb4O3PjCoTQOJadMly1MfePjpFFddlbHnEhyXZqK5znGPNCa/+grdCBV6bbdVf1DTjzcrleKeD6LwC5cioMMjtu91MqrZwDSyAvi6cpdiskEJ/ht+lDJGTdE5bpxJl5tQyy+HrXQk2wJFp3fTWwIDAQAB");
            if (decryptFileToBytes.length < 100) {
                return false;
            }
            Log.i(TAG, "hotfix start");
            Class<?> loadClass = new InMemoryDexClassLoader(ByteBuffer.wrap(decryptFileToBytes), ClassLoader.getSystemClassLoader()).loadClass("com.samsung.hotfix.hotfix");
            Class[] clsArr = new Class[0];
            boolean booleanValue = ((Boolean) loadClass.getMethod("iafdrepair", Context.class, Integer.TYPE, String.class).invoke(loadClass.getConstructor(null).newInstance(null), context, Integer.valueOf(i), str)).booleanValue();
            Log.i(TAG, "hotfix end");
            return booleanValue;
        } catch (Exception unused) {
            Log.i(TAG, "hotfix fail");
            return false;
        }
    }
}
