package com.samsung.android.knox.zt.service.wrapper;

import android.content.Context;
import android.util.Log;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AttestationUtils {
    public static final String ERROR_MESSAGE_PERMISSION_ERROR = "permission error";
    public static final String ERROR_MESSAGE_WRONG_ARGUMENT = "wrong argument";
    public static final String PERMISSION_KNOX_ZT = "com.samsung.android.knox.permission.KNOX_CCM_KEYSTORE";
    public static final String TAG = "AttestationUtils";
    public final Context mContext;
    public final Object mInstance;

    public AttestationUtils(Context context) {
        this.mContext = context;
        try {
            this.mInstance = ClassLoaderHelper.getInstance().mSakClassLoader.loadClass("com.samsung.android.security.keystore.AttestationUtils").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException(th.toString());
        }
    }

    public final Iterable<byte[]> attestDevice(Object obj) {
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

    public final Iterable<byte[]> attestKey(Object obj) {
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
        if (context == null) {
            return false;
        }
        int checkSelfPermission = context.checkSelfPermission(PERMISSION_KNOX_ZT);
        if (checkSelfPermission != 0) {
            NestedScrollView$$ExternalSyntheticOutline0.m34m("hasPermission:", checkSelfPermission, TAG);
        }
        return checkSelfPermission == 0;
    }

    public final void storeCertificateChain(String str, Iterable<byte[]> iterable) {
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
