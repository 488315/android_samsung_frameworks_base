package com.sec.android.iaft;

import android.content.Context;
import android.util.Log;
import dalvik.system.InMemoryDexClassLoader;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class IAFDHotfix {
    private static final String TAG = "IAFT_IAFDHotfix";

    public static boolean hotfix(Context context, int expType, String pkgName) {
        boolean result = false;
        if (context == null) {
            Log.i(TAG, "context is null");
            return false;
        }
        try {
            String fileName = "iafdrepair_" + Integer.toString(expType) + "_dex.bin.enc";
            String path = context.getDataDir().getAbsolutePath() + "/iafd/dex/";
            File dexLocation = new File(path, fileName);
            if (!dexLocation.exists()) {
                Log.i(TAG, dexLocation.toString() + " not found.");
                return false;
            }
            byte[] dexBytes = IAFDRSAUtils.decryptFileToBytes(dexLocation.toString(), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwaCLv6RvwU8gyFSbynkiPI1Yjb4O3PjCoTQOJadMly1MfePjpFFddlbHnEhyXZqK5znGPNCa/+grdCBV6bbdVf1DTjzcrleKeD6LwC5cioMMjtu91MqrZwDSyAvi6cpdiskEJ/ht+lDJGTdE5bpxJl5tQyy+HrXQk2wJFp3fTWwIDAQAB");
            if (dexBytes.length < 100) {
                return false;
            }
            Log.i(TAG, "hotfix start");
            ByteBuffer byteBuf = ByteBuffer.wrap(dexBytes);
            ClassLoader loader = new InMemoryDexClassLoader(byteBuf, ClassLoader.getSystemClassLoader());
            Class hotfixClass = loader.loadClass("com.samsung.hotfix.hotfix");
            Method iafdrepairMethod = hotfixClass.getMethod("iafdrepair", Context.class, Integer.TYPE, String.class);
            Constructor<?> mc = hotfixClass.getConstructor(new Class[0]);
            Object obj = mc.newInstance(new Object[0]);
            try {
                result = ((Boolean) iafdrepairMethod.invoke(obj, context, Integer.valueOf(expType), pkgName)).booleanValue();
                Log.i(TAG, "hotfix end");
                return result;
            } catch (Exception e) {
                Log.i(TAG, "hotfix fail");
                return result;
            }
        } catch (Exception e2) {
        }
    }
}
