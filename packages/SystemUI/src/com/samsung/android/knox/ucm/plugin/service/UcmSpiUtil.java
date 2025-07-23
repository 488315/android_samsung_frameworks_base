package com.samsung.android.knox.ucm.plugin.service;

import android.util.Log;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class UcmSpiUtil {
    public static final String TAG = "UcmSpiUtil";

    public static Object getSpi(String str, Class cls, String str2, Provider provider) {
        Object obj = null;
        if (provider != null && cls != null) {
            try {
                Provider.Service service = provider.getService(str, str2);
                if (service == null) {
                    Log.e(TAG, "failed to find service [" + provider.getName() + "], [" + str + "], [" + str2 + "]");
                    return null;
                }
                Object newInstance = service.newInstance(null);
                try {
                    if (newInstance == null) {
                        Log.e(TAG, "newInstance is null");
                        return null;
                    }
                    if (cls.isAssignableFrom(newInstance.getClass())) {
                        return newInstance;
                    }
                    Log.e(TAG, "failed to find subclass");
                    return null;
                } catch (NoSuchAlgorithmException e) {
                    obj = newInstance;
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
