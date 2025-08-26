package com.samsung.android.location;

import android.content.Context;
import android.os.IBinder;
import android.util.Log;
import com.samsung.android.location.ISLocationManager;

/* loaded from: classes6.dex */
public class SLocationLoader {
    private static final String CLASS_SLocationService = "com.samsung.android.location.SLocationService";
    private static final String METHOD_systemReady = "systemReady";
    private static final String TAG = "SLocationLoader";

    private static Class getClassFromLib(Context context, String str) throws Throwable {
        return context.createPackageContext("com.samsung.android.location", 3).getClassLoader().loadClass(str);
    }

    public static IBinder getSLocationService(Context context) throws Throwable {
        Log.w(TAG, "getSLocationService start");
        Class classFromLib = getClassFromLib(context, CLASS_SLocationService);
        if (classFromLib != null) {
            IBinder iBinder = (IBinder) classFromLib.getConstructor(Context.class).newInstance(context);
            Log.w(TAG, "get newInstance");
            return iBinder;
        }
        Log.e(TAG, "sLocationServiceClass is null");
        return null;
    }

    public static void systemReady(Context context, IBinder iBinder) throws Throwable {
        Class classFromLib = getClassFromLib(context, CLASS_SLocationService);
        ISLocationManager iSLocationManagerAsInterface = ISLocationManager.Stub.asInterface(iBinder);
        if (classFromLib == null) {
            Log.e(TAG, "sLocationService is null");
            return;
        }
        Class[] clsArr = new Class[0];
        classFromLib.getDeclaredMethod(METHOD_systemReady, null).invoke(iSLocationManagerAsInterface, null);
        Log.w(TAG, "invoke systemReady");
    }
}
