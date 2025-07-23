package android.security;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.StrictMode;
import android.security.maintenance.IKeystoreMaintenance;
import android.system.keystore2.KeyDescriptor;
import android.util.Log;

/* loaded from: classes3.dex */
public class AndroidKeyStoreMaintenance {
    public static final int INVALID_ARGUMENT = 20;
    public static final int KEY_NOT_FOUND = 7;
    public static final int PERMISSION_DENIED = 6;
    public static final int SYSTEM_ERROR = 4;
    private static final String TAG = "AndroidKeyStoreMaintenance";

    private static IKeystoreMaintenance getService() {
        return IKeystoreMaintenance.Stub.asInterface(ServiceManager.checkService("android.security.maintenance"));
    }

    public static int onUserAdded(int i) {
        StrictMode.noteDiskWrite();
        try {
            getService().onUserAdded(i);
            return 0;
        } catch (ServiceSpecificException e) {
            Log.e(TAG, "onUserAdded failed", e);
            return e.errorCode;
        } catch (Exception e2) {
            Log.e(TAG, "Can not connect to keystore", e2);
            return 4;
        }
    }

    public static int initUserSuperKeys(int i, byte[] bArr, boolean z) {
        StrictMode.noteDiskWrite();
        try {
            getService().initUserSuperKeys(i, bArr, z);
            return 0;
        } catch (ServiceSpecificException e) {
            Log.e(TAG, "initUserSuperKeys failed", e);
            return e.errorCode;
        } catch (Exception e2) {
            Log.e(TAG, "Can not connect to keystore", e2);
            return 4;
        }
    }

    public static int onUserRemoved(int i) {
        StrictMode.noteDiskWrite();
        try {
            getService().onUserRemoved(i);
            return 0;
        } catch (ServiceSpecificException e) {
            Log.e(TAG, "onUserRemoved failed", e);
            return e.errorCode;
        } catch (Exception e2) {
            Log.e(TAG, "Can not connect to keystore", e2);
            return 4;
        }
    }

    public static int onUserLskfRemoved(int i) {
        StrictMode.noteDiskWrite();
        try {
            getService().onUserLskfRemoved(i);
            return 0;
        } catch (ServiceSpecificException e) {
            Log.e(TAG, "onUserLskfRemoved failed", e);
            return e.errorCode;
        } catch (Exception e2) {
            Log.e(TAG, "Can not connect to keystore", e2);
            return 4;
        }
    }

    public static int clearNamespace(int i, long j) {
        StrictMode.noteDiskWrite();
        try {
            getService().clearNamespace(i, j);
            return 0;
        } catch (ServiceSpecificException e) {
            Log.e(TAG, "clearNamespace failed", e);
            return e.errorCode;
        } catch (Exception e2) {
            Log.e(TAG, "Can not connect to keystore", e2);
            return 4;
        }
    }

    public static int migrateKeyNamespace(KeyDescriptor keyDescriptor, KeyDescriptor keyDescriptor2) {
        StrictMode.noteDiskWrite();
        try {
            getService().migrateKeyNamespace(keyDescriptor, keyDescriptor2);
            return 0;
        } catch (ServiceSpecificException e) {
            Log.e(TAG, "migrateKeyNamespace failed", e);
            return e.errorCode;
        } catch (Exception e2) {
            Log.e(TAG, "Can not connect to keystore", e2);
            return 4;
        }
    }

    public static long[] getAllAppUidsAffectedBySid(int i, long j) throws KeyStoreException {
        StrictMode.noteDiskWrite();
        try {
            return getService().getAppUidsAffectedBySid(i, j);
        } catch (RemoteException | NullPointerException unused) {
            throw new KeyStoreException(4, "Failure to connect to Keystore while trying to get apps affected by SID.");
        } catch (ServiceSpecificException e) {
            throw new KeyStoreException(e.errorCode, "Keystore error while trying to get apps affected by SID.");
        }
    }

    public static void deleteAllKeys() throws KeyStoreException {
        StrictMode.noteDiskWrite();
        try {
            getService().deleteAllKeys();
        } catch (RemoteException | NullPointerException unused) {
            throw new KeyStoreException(4, "Failure to connect to Keystore while trying to delete all keys.");
        } catch (ServiceSpecificException e) {
            throw new KeyStoreException(e.errorCode, "Keystore error while trying to delete all keys.");
        }
    }
}
