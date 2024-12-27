package android.net.wifi;

import android.annotation.SystemApi;
import android.os.Binder;
import android.os.ServiceSpecificException;
import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SystemApi
public final class WifiKeystore {
    private static final String TAG = "WifiKeystore";
    private static final String sPrimaryDbName;

    static {
        sPrimaryDbName =
                WifiBlobStore.supplicantCanAccessBlobstore() ? "WifiBlobstore" : "LegacyKeystore";
    }

    WifiKeystore() {}

    @SystemApi
    public static boolean put(String alias, byte[] blob) {
        long identity = Binder.clearCallingIdentity();
        try {
            try {
                Log.i(TAG, "put blob. alias=" + alias + ", primaryDb=" + sPrimaryDbName);
                if (WifiBlobStore.supplicantCanAccessBlobstore()) {
                    return WifiBlobStore.getInstance().put(alias, blob);
                }
                WifiBlobStore.getLegacyKeystore().put(alias, 1010, blob);
                Binder.restoreCallingIdentity(identity);
                return true;
            } catch (Exception e) {
                Log.e(TAG, "Failed to put blob.", e);
                Binder.restoreCallingIdentity(identity);
                return false;
            }
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    @SystemApi
    public static byte[] get(String alias) {
        long identity = Binder.clearCallingIdentity();
        try {
            try {
                Log.i(TAG, "get blob. alias=" + alias + ", primaryDb=" + sPrimaryDbName);
                byte[] blob = WifiBlobStore.getInstance().get(alias);
                if (blob != null) {
                    return blob;
                }
                Log.i(TAG, "Searching for blob in Legacy Keystore");
                return WifiBlobStore.getLegacyKeystore().get(alias, 1010);
            } catch (ServiceSpecificException e) {
                if (e.errorCode != 7) {
                    Log.e(TAG, "Failed to get blob.", e);
                }
                Binder.restoreCallingIdentity(identity);
                return new byte[0];
            } catch (Exception e2) {
                Log.e(TAG, "Failed to get blob.", e2);
                Binder.restoreCallingIdentity(identity);
                return new byte[0];
            }
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    @SystemApi
    public static boolean remove(String alias) {
        boolean blobStoreSuccess = false;
        boolean legacyKsSuccess = false;
        long identity = Binder.clearCallingIdentity();
        try {
            try {
                try {
                    Log.i(TAG, "remove blob. alias=" + alias + ", primaryDb=" + sPrimaryDbName);
                    blobStoreSuccess = WifiBlobStore.getInstance().remove(alias);
                    WifiBlobStore.getLegacyKeystore().remove(alias, 1010);
                    legacyKsSuccess = true;
                } catch (Exception e) {
                    Log.e(TAG, "Failed to remove blob.", e);
                }
            } catch (ServiceSpecificException e2) {
                if (e2.errorCode != 7) {
                    Log.e(TAG, "Failed to remove blob.", e2);
                }
            }
            Binder.restoreCallingIdentity(identity);
            Log.i(
                    TAG,
                    "Removal status: wifiBlobStore="
                            + blobStoreSuccess
                            + ", legacyKeystore="
                            + legacyKsSuccess);
            return blobStoreSuccess || legacyKsSuccess;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(identity);
            throw th;
        }
    }

    @SystemApi
    public static String[] list(String prefix) {
        long identity = Binder.clearCallingIdentity();
        try {
            try {
                String[] blobStoreAliases = WifiBlobStore.getInstance().list(prefix);
                String[] legacyAliases = WifiBlobStore.getLegacyKeystore().list(prefix, 1010);
                for (int i = 0; i < legacyAliases.length; i++) {
                    legacyAliases[i] = legacyAliases[i].substring(prefix.length());
                }
                Set<String> uniqueAliases = new HashSet<>();
                uniqueAliases.addAll(Arrays.asList(blobStoreAliases));
                uniqueAliases.addAll(Arrays.asList(legacyAliases));
                String[] uniqueAliasArray = new String[uniqueAliases.size()];
                return (String[]) uniqueAliases.toArray(uniqueAliasArray);
            } catch (Exception e) {
                Log.e(TAG, "Failed to list blobs.", e);
                Binder.restoreCallingIdentity(identity);
                return new String[0];
            }
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }
}
