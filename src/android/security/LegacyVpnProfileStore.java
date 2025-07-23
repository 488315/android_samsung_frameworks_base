package android.security;

import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.StrictMode;
import android.security.legacykeystore.ILegacyKeystore;
import android.util.Log;

/* loaded from: classes3.dex */
public class LegacyVpnProfileStore {
    private static final String LEGACY_KEYSTORE_SERVICE_NAME = "android.security.legacykeystore";
    public static final int PROFILE_NOT_FOUND = 7;
    public static final int SYSTEM_ERROR = 4;
    private static final String TAG = "LegacyVpnProfileStore";

    private static ILegacyKeystore getService() {
        return ILegacyKeystore.Stub.asInterface(ServiceManager.checkService(LEGACY_KEYSTORE_SERVICE_NAME));
    }

    public static boolean put(String str, byte[] bArr) {
        StrictMode.noteDiskWrite();
        try {
            getService().put(str, -1, bArr);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Failed to put vpn profile.", e);
            return false;
        }
    }

    public static byte[] get(String str) {
        StrictMode.noteDiskRead();
        try {
            return getService().get(str, -1);
        } catch (ServiceSpecificException e) {
            if (e.errorCode == 7) {
                return null;
            }
            Log.e(TAG, "Failed to get vpn profile.", e);
            return null;
        } catch (Exception e2) {
            Log.e(TAG, "Failed to get vpn profile.", e2);
            return null;
        }
    }

    public static boolean remove(String str) {
        StrictMode.noteDiskWrite();
        try {
            getService().remove(str, -1);
            return true;
        } catch (ServiceSpecificException e) {
            if (e.errorCode == 7) {
                return false;
            }
            Log.e(TAG, "Failed to remove vpn profile.", e);
            return false;
        } catch (Exception e2) {
            Log.e(TAG, "Failed to remove vpn profile.", e2);
            return false;
        }
    }

    public static String[] list(String str) {
        StrictMode.noteDiskRead();
        try {
            String[] list = getService().list(str, -1);
            for (int i = 0; i < list.length; i++) {
                list[i] = list[i].substring(str.length());
            }
            return list;
        } catch (Exception e) {
            Log.e(TAG, "Failed to list vpn profiles.", e);
            return new String[0];
        }
    }
}
