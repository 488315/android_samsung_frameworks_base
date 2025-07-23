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
        return IKeystoreAuthorization.Stub.asInterface(ServiceManager.checkService("android.security.authorization"));
    }

    public int addAuthToken(HardwareAuthToken hardwareAuthToken) {
        StrictMode.noteSlowCall("addAuthToken");
        try {
            getService().addAuthToken(hardwareAuthToken);
            return 0;
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, "Can not connect to keystore", e);
            return 4;
        } catch (ServiceSpecificException e2) {
            return e2.errorCode;
        }
    }

    public int addAuthToken(byte[] bArr) {
        return addAuthToken(AuthTokenUtils.toHardwareAuthToken(bArr));
    }

    public int onDeviceUnlocked(int i, byte[] bArr) {
        StrictMode.noteDiskWrite();
        try {
            getService().onDeviceUnlocked(i, bArr);
            return 0;
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, "Can not connect to keystore", e);
            return 4;
        } catch (ServiceSpecificException e2) {
            return e2.errorCode;
        }
    }

    public int onDeviceLocked(int i, long[] jArr, boolean z) {
        StrictMode.noteDiskWrite();
        try {
            getService().onDeviceLocked(i, jArr, z);
            return 0;
        } catch (RemoteException | NullPointerException e) {
            Log.w(TAG, "Can not connect to keystore", e);
            return 4;
        } catch (ServiceSpecificException e2) {
            return e2.errorCode;
        }
    }

    public long getLastAuthTime(long j, int[] iArr) {
        try {
            return getService().getLastAuthTime(j, iArr);
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
