package com.samsung.android.location;

import android.content.Context;
import android.p009os.IBinder;
import android.util.Log;
import com.samsung.android.location.ISLocationManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/* loaded from: classes5.dex */
public class SLocationLoader {
    private static final String CLASS_SLocationService = "com.samsung.android.location.SLocationService";
    private static final String METHOD_systemReady = "systemReady";
    private static final String TAG = "SLocationLoader";

    private static Class getClassFromLib(Context context, String name) throws Throwable {
        Context apkContext = context.createPackageContext("com.samsung.android.location", 3);
        return apkContext.getClassLoader().loadClass(name);
    }

    public static IBinder getSLocationService(Context context) throws Throwable {
        Log.m102w(TAG, "getSLocationService start");
        Class sLocationServiceClass = getClassFromLib(context, CLASS_SLocationService);
        if (sLocationServiceClass != null) {
            Constructor constructor = sLocationServiceClass.getConstructor(Context.class);
            Object[] arglist = {context};
            IBinder sLocationService = (IBinder) constructor.newInstance(arglist);
            Log.m102w(TAG, "get newInstance");
            return sLocationService;
        }
        Log.m96e(TAG, "sLocationServiceClass is null");
        return null;
    }

    public static void systemReady(Context context, IBinder b) throws Throwable {
        Class sLocationService = getClassFromLib(context, CLASS_SLocationService);
        ISLocationManager service = ISLocationManager.Stub.asInterface(b);
        if (sLocationService == null) {
            Log.m96e(TAG, "sLocationService is null");
            return;
        }
        Method systemReady = sLocationService.getDeclaredMethod(METHOD_systemReady, new Class[0]);
        systemReady.invoke(service, new Object[0]);
        Log.m102w(TAG, "invoke systemReady");
    }
}
