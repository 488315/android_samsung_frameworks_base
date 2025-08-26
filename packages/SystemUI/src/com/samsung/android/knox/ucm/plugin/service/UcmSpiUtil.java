package com.samsung.android.knox.ucm.plugin.service;

import android.util.Log;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;

/* loaded from: classes4.dex */
public class UcmSpiUtil {
    public static final String TAG = "UcmSpiUtil";

    public static Object getSpi(String str, Class cls, String str2, Provider provider) throws NoSuchAlgorithmException {
        Object obj = null;
        if (provider != null && cls != null) {
            try {
                Provider.Service service = provider.getService(str, str2);
                if (service == null) {
                    Log.e(TAG, "failed to find service [" + provider.getName() + "], [" + str + "], [" + str2 + "]");
                    return null;
                }
                Object objNewInstance = service.newInstance(null);
                try {
                    if (objNewInstance == null) {
                        Log.e(TAG, "newInstance is null");
                        return null;
                    }
                    if (cls.isAssignableFrom(objNewInstance.getClass())) {
                        return objNewInstance;
                    }
                    Log.e(TAG, "failed to find subclass");
                    return null;
                } catch (NoSuchAlgorithmException e) {
                    obj = objNewInstance;
                    e = e;
                    Log.e(TAG, "NoSuchAlgorithmException");
                    e.printStackTrace();
                    return obj;
                }
            } catch (NoSuchAlgorithmException e2) {
                e = e2;
            }
        }
        return obj;
    }
}
