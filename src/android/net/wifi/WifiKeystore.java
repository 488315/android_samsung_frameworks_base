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
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Log.i(TAG, "put blob. alias=" + str + ", primaryDb=" + sPrimaryDbName);
                if (WifiBlobStore.supplicantCanAccessBlobstore()) {
                    return WifiBlobStore.getInstance().put(str, bArr);
                }
                WifiBlobStore.getLegacyKeystore().put(str, 1010, bArr);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (Exception e) {
                Log.e(TAG, "Failed to put blob.", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @SystemApi
    public static byte[] get(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
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
                } catch (ServiceSpecificException e) {
                    if (e.errorCode != 7) {
                        Log.e(TAG, "Failed to get blob.", e);
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return new byte[0];
                }
            } catch (Exception e2) {
                Log.e(TAG, "Failed to get blob.", e2);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return new byte[0];
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0071 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    @android.annotation.SystemApi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean remove(java.lang.String r9) {
        /*
            java.lang.String r0 = "Failed to remove blob."
            java.lang.String r1 = "WifiKeystore"
            java.lang.String r2 = "remove blob. alias="
            long r3 = android.os.Binder.clearCallingIdentity()
            r5 = 1
            r6 = 0
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42 android.os.ServiceSpecificException -> L48
            r7.<init>(r2)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42 android.os.ServiceSpecificException -> L48
            r7.append(r9)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42 android.os.ServiceSpecificException -> L48
            java.lang.String r2 = ", primaryDb="
            r7.append(r2)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42 android.os.ServiceSpecificException -> L48
            java.lang.String r2 = android.net.wifi.WifiKeystore.sPrimaryDbName     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42 android.os.ServiceSpecificException -> L48
            r7.append(r2)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42 android.os.ServiceSpecificException -> L48
            java.lang.String r2 = r7.toString()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42 android.os.ServiceSpecificException -> L48
            android.util.Log.i(r1, r2)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42 android.os.ServiceSpecificException -> L48
            android.net.wifi.WifiBlobStore r2 = android.net.wifi.WifiBlobStore.getInstance()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42 android.os.ServiceSpecificException -> L48
            boolean r2 = r2.remove(r9)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42 android.os.ServiceSpecificException -> L48
            android.security.legacykeystore.ILegacyKeystore r7 = android.net.wifi.WifiBlobStore.getLegacyKeystore()     // Catch: java.lang.Exception -> L3c android.os.ServiceSpecificException -> L3e java.lang.Throwable -> L40
            r8 = 1010(0x3f2, float:1.415E-42)
            r7.remove(r9, r8)     // Catch: java.lang.Exception -> L3c android.os.ServiceSpecificException -> L3e java.lang.Throwable -> L40
            android.os.Binder.restoreCallingIdentity(r3)
            r9 = r5
            goto L56
        L3c:
            r9 = move-exception
            goto L44
        L3e:
            r9 = move-exception
            goto L4a
        L40:
            r9 = move-exception
            goto L76
        L42:
            r9 = move-exception
            r2 = r6
        L44:
            android.util.Log.e(r1, r0, r9)     // Catch: java.lang.Throwable -> L40
            goto L52
        L48:
            r9 = move-exception
            r2 = r6
        L4a:
            int r7 = r9.errorCode     // Catch: java.lang.Throwable -> L40
            r8 = 7
            if (r7 == r8) goto L52
            android.util.Log.e(r1, r0, r9)     // Catch: java.lang.Throwable -> L40
        L52:
            android.os.Binder.restoreCallingIdentity(r3)
            r9 = r6
        L56:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r3 = "Removal status: wifiBlobStore="
            r0.<init>(r3)
            r0.append(r2)
            java.lang.String r3 = ", legacyKeystore="
            r0.append(r3)
            r0.append(r9)
            java.lang.String r0 = r0.toString()
            android.util.Log.i(r1, r0)
            if (r2 != 0) goto L75
            if (r9 == 0) goto L74
            goto L75
        L74:
            r5 = r6
        L75:
            return r5
        L76:
            android.os.Binder.restoreCallingIdentity(r3)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: android.net.wifi.WifiKeystore.remove(java.lang.String):boolean");
    }

    @SystemApi
    public static String[] list(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
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
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return new String[0];
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
