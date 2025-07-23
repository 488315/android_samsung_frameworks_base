package com.samsung.android.knox.zt.service.wrapper;

import android.content.Context;
import android.util.Log;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class AttestationUtils {
    public static final String ERROR_MESSAGE_PERMISSION_ERROR = "permission error";
    public static final String ERROR_MESSAGE_WRONG_ARGUMENT = "wrong argument";
    public static final String PERMISSION_KNOX_ZT = "com.samsung.android.knox.permission.KNOX_CCM_KEYSTORE";
    public static final String TAG = "AttestationUtils";
    public final Context mContext;
    public final Object mInstance;

    public AttestationUtils(Context context) {
        this.mContext = context;
        try {
            Class[] clsArr = new Class[0];
            this.mInstance = ClassLoaderHelper.getInstance().getSakClassLoader().loadClass("com.samsung.android.security.keystore.AttestationUtils").getDeclaredConstructor(null).newInstance(null);
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException(th.toString());
        }
    }

    public Iterable<byte[]> attestDevice(Object obj) {
        if (!hasPermission()) {
            throw new SecurityException(ERROR_MESSAGE_PERMISSION_ERROR);
        }
        if (obj == null) {
            Log.e(TAG, "Mandatory argument is missing!");
            throw new IllegalArgumentException(ERROR_MESSAGE_WRONG_ARGUMENT);
        }
        try {
            return (Iterable) this.mInstance.getClass().getMethod("attestDevice", obj.getClass()).invoke(this.mInstance, obj);
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException(th.toString());
        }
    }

    public Iterable<byte[]> attestKey(Object obj) {
        if (!hasPermission()) {
            throw new SecurityException(ERROR_MESSAGE_PERMISSION_ERROR);
        }
        if (obj == null) {
            Log.e(TAG, "Mandatory argument is missing!");
            throw new IllegalArgumentException(ERROR_MESSAGE_WRONG_ARGUMENT);
        }
        try {
            return (Iterable) this.mInstance.getClass().getMethod("attestKey", obj.getClass()).invoke(this.mInstance, obj);
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException(th.toString());
        }
    }

    public final boolean hasPermission() {
        Context context = this.mContext;
        if (context != null) {
            int checkSelfPermission = context.checkSelfPermission(PERMISSION_KNOX_ZT);
            if (checkSelfPermission != 0) {
                ClockEventController$$ExternalSyntheticOutline0.m(checkSelfPermission, "hasPermission:", TAG);
            }
            if (checkSelfPermission == 0) {
                return true;
            }
        }
        return false;
    }

    public void storeCertificateChain(String str, Iterable<byte[]> iterable) {
        if (!hasPermission()) {
            throw new SecurityException(ERROR_MESSAGE_PERMISSION_ERROR);
        }
        if (str == null || iterable == null) {
            Log.e(TAG, "Mandatory argument is missing!");
            throw new IllegalArgumentException(ERROR_MESSAGE_WRONG_ARGUMENT);
        }
        try {
            this.mInstance.getClass().getMethod("storeCertificateChain", String.class, Iterable.class).invoke(this.mInstance, str, iterable);
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException(th.toString());
        }
    }
}
