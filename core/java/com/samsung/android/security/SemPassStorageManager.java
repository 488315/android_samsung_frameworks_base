package com.samsung.android.security;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.storage.IStorageManager;
import android.util.sysfwutil.Slog;

import com.samsung.android.media.AudioParameter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes6.dex */
public class SemPassStorageManager {
    public static final int RESULT_ERROR_DESTROY_STORAGE_FAILED = -7;
    public static final int RESULT_ERROR_EVICT_KEY_FAILED = -6;
    public static final int RESULT_ERROR_GENERATE_AND_PROTECT_KEY_FAILED = -10;
    public static final int RESULT_ERROR_GENERATE_SECRET_FAILED = -11;
    public static final int RESULT_ERROR_GET_SERVICE_EXCEPTION = -13;
    public static final int RESULT_ERROR_INSTALL_KEY_FAILED = -4;
    public static final int RESULT_ERROR_POLICY_NOT_FOUND = -2;
    public static final int RESULT_ERROR_PREPARE_DIR_FAILED = -8;
    public static final int RESULT_ERROR_PREPARE_DIR_WITH_POLICY_FAILED = -9;
    public static final int RESULT_ERROR_RETRIEVE_KEY_FAILED = -3;
    public static final int RESULT_ERROR_RETRIEVE_SECRET_FAILED = -5;
    public static final int RESULT_ERROR_STORAGE_MANAGER_SERVICE_EXCEPTION = -12;
    public static final int RESULT_ERROR_STORAGE_NO_EXIST = -1;
    public static final int RESULT_SUCCESS = 0;
    private static final String TAG = "SemPassStorageManager";
    private static boolean isPassSupport = true;
    private static SemPassStorageManager mSemPassStorageManager = null;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SemPassStorageCodeResult {}

    private SemPassStorageManager() {}

    public static SemPassStorageManager getInstance() {
        if (!isPassSupport) {
            return null;
        }
        if (mSemPassStorageManager == null) {
            mSemPassStorageManager = new SemPassStorageManager();
        }
        return mSemPassStorageManager;
    }

    public int create() {
        try {
            IBinder service = ServiceManager.getService(AudioParameter.VALUE_MOUNT);
            if (service == null) {
                return -13;
            }
            int result = IStorageManager.Stub.asInterface(service).createPassStorage();
            return result;
        } catch (RemoteException re) {
            Slog.wtf(TAG, re);
            return -13;
        }
    }

    public String getPath() {
        try {
            IBinder service = ServiceManager.getService(AudioParameter.VALUE_MOUNT);
            if (service == null) {
                return null;
            }
            String result = IStorageManager.Stub.asInterface(service).getPassStorage();
            return result;
        } catch (RemoteException re) {
            Slog.wtf(TAG, re);
            return null;
        }
    }

    public int release() {
        try {
            IBinder service = ServiceManager.getService(AudioParameter.VALUE_MOUNT);
            if (service == null) {
                return -13;
            }
            int result = IStorageManager.Stub.asInterface(service).destroyPassStorage();
            return result;
        } catch (RemoteException re) {
            Slog.wtf(TAG, re);
            return -13;
        }
    }

    public int unlock() {
        try {
            IBinder service = ServiceManager.getService(AudioParameter.VALUE_MOUNT);
            if (service == null) {
                return -13;
            }
            int result = IStorageManager.Stub.asInterface(service).unlockPassStorage();
            return result;
        } catch (RemoteException re) {
            Slog.wtf(TAG, re);
            return -13;
        }
    }

    public int lock() {
        try {
            IBinder service = ServiceManager.getService(AudioParameter.VALUE_MOUNT);
            if (service == null) {
                return -13;
            }
            int result = IStorageManager.Stub.asInterface(service).lockPassStorage();
            return result;
        } catch (RemoteException re) {
            Slog.wtf(TAG, re);
            return -13;
        }
    }

    public boolean isUnlocked() {
        try {
            IBinder service = ServiceManager.getService(AudioParameter.VALUE_MOUNT);
            if (service == null) {
                return false;
            }
            boolean result = IStorageManager.Stub.asInterface(service).isPassUnlocked();
            return result;
        } catch (RemoteException re) {
            Slog.wtf(TAG, re);
            return false;
        }
    }
}
