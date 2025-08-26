package android.net.wifi;

import android.annotation.SystemApi;
import android.os.Binder;
import android.os.ServiceSpecificException;
import android.util.Log;
import java.util.Arrays;
import java.util.HashSet;

@SystemApi
/* loaded from: classes3.dex */
public final class WifiKeystore {
    private static final String TAG = "WifiKeystore";
    private static final String sPrimaryDbName;

    static {
        sPrimaryDbName = WifiBlobStore.supplicantCanAccessBlobstore() ? "WifiBlobstore" : "LegacyKeystore";
    }

    WifiKeystore() {
    }

    @SystemApi
    public static boolean put(String str, byte[] bArr) {
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Log.i(TAG, "put blob. alias=" + str + ", primaryDb=" + sPrimaryDbName);
                if (WifiBlobStore.supplicantCanAccessBlobstore()) {
                    return WifiBlobStore.getInstance().put(str, bArr);
                }
                WifiBlobStore.getLegacyKeystore().put(str, 1010, bArr);
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                return true;
            } catch (Exception e) {
                Log.e(TAG, "Failed to put blob.", e);
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                return false;
            }
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    @SystemApi
    public static byte[] get(String str) {
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                try {
                    Log.i(TAG, "get blob. alias=" + str + ", primaryDb=" + sPrimaryDbName);
                    byte[] bArr = WifiBlobStore.getInstance().get(str);
                    if (bArr != null) {
                        return bArr;
                    }
                    Log.i(TAG, "Searching for blob in Legacy Keystore");
                    return WifiBlobStore.getLegacyKeystore().get(str, 1010);
                } catch (Exception e) {
                    Log.e(TAG, "Failed to get blob.", e);
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                    return new byte[0];
                }
            } catch (ServiceSpecificException e2) {
                if (e2.errorCode != 7) {
                    Log.e(TAG, "Failed to get blob.", e2);
                }
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                return new byte[0];
            }
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0071 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    @SystemApi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean remove(String str) {
        boolean zRemove;
        boolean z;
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Log.i(TAG, "remove blob. alias=" + str + ", primaryDb=" + sPrimaryDbName);
                zRemove = WifiBlobStore.getInstance().remove(str);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                throw th;
            }
        } catch (ServiceSpecificException e) {
            e = e;
            zRemove = false;
        } catch (Exception e2) {
            e = e2;
            zRemove = false;
        }
        try {
            WifiBlobStore.getLegacyKeystore().remove(str, 1010);
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            z = true;
        } catch (ServiceSpecificException e3) {
            e = e3;
            if (e.errorCode != 7) {
                Log.e(TAG, "Failed to remove blob.", e);
            }
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            z = false;
            Log.i(TAG, "Removal status: wifiBlobStore=" + zRemove + ", legacyKeystore=" + z);
            return !zRemove ? true : true;
        } catch (Exception e4) {
            e = e4;
            Log.e(TAG, "Failed to remove blob.", e);
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            z = false;
            Log.i(TAG, "Removal status: wifiBlobStore=" + zRemove + ", legacyKeystore=" + z);
            if (!zRemove) {
            }
        }
        Log.i(TAG, "Removal status: wifiBlobStore=" + zRemove + ", legacyKeystore=" + z);
        if (!zRemove && !z) {
            return false;
        }
    }

    @SystemApi
    public static String[] list(String str) {
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                String[] list = WifiBlobStore.getInstance().list(str);
                String[] list2 = WifiBlobStore.getLegacyKeystore().list(str, 1010);
                for (int i = 0; i < list2.length; i++) {
                    list2[i] = list2[i].substring(str.length());
                }
                HashSet hashSet = new HashSet();
                hashSet.addAll(Arrays.asList(list));
                hashSet.addAll(Arrays.asList(list2));
                return (String[]) hashSet.toArray(new String[hashSet.size()]);
            } catch (Exception e) {
                Log.e(TAG, "Failed to list blobs.", e);
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                return new String[0];
            }
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }
}
