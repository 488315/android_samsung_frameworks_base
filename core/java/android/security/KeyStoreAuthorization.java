package android.security;

import android.hardware.security.keymint.HardwareAuthToken;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.StrictMode;
import android.security.authorization.IKeystoreAuthorization;
import android.util.Log;

/* loaded from: classes3.dex */
public class KeyStoreAuthorization {
    public static final int SYSTEM_ERROR = 4;
    private static final String TAG = "KeyStoreAuthorization";
    private static final KeyStoreAuthorization sInstance = new KeyStoreAuthorization();

    public static KeyStoreAuthorization getInstance() {
        return sInstance;
    }

    private IKeystoreAuthorization getService() {
        return IKeystoreAuthorization.Stub.asInterface(
                ServiceManager.checkService("android.security.authorization"));
    }

    public int addAuthToken(HardwareAuthToken authToken) {
        StrictMode.noteSlowCall("addAuthToken");
        try {
            getService().addAuthToken(authToken);
            return 0;
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, "Can not connect to keystore", e);
            return 4;
        } catch (ServiceSpecificException e2) {
            return e2.errorCode;
        }
    }

    public int addAuthToken(byte[] authToken) {
        return addAuthToken(AuthTokenUtils.toHardwareAuthToken(authToken));
    }

    public int onDeviceUnlocked(int userId, byte[] password) {
        StrictMode.noteDiskWrite();
        try {
            getService().onDeviceUnlocked(userId, password);
            return 0;
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, "Can not connect to keystore", e);
            return 4;
        } catch (ServiceSpecificException e2) {
            return e2.errorCode;
        }
    }

    public int onDeviceLocked(int userId, long[] unlockingSids, boolean weakUnlockEnabled) {
        StrictMode.noteDiskWrite();
        try {
            getService().onDeviceLocked(userId, unlockingSids, weakUnlockEnabled);
            return 0;
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, "Can not connect to keystore", e);
            return 4;
        } catch (ServiceSpecificException e2) {
            return e2.errorCode;
        }
    }

    public long getLastAuthTime(long userId, int[] authenticatorTypes) {
        try {
            return getService().getLastAuthTime(userId, authenticatorTypes);
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, "Error getting last auth time: " + e);
            return -1L;
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode != 6) {
                return -1L;
            }
            throw new UnsupportedOperationException();
        }
    }
}
