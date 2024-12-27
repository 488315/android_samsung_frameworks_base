package com.android.server.enterprise.vpn.knoxvpn;

import android.os.Binder;
import android.security.LegacyVpnProfileStore;
import android.util.Log;

import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KnoxVpnCredentialHandler {
    public static KnoxVpnCredentialHandler mInstance;

    public static void deleteCredentialsFromKeystore(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                LegacyVpnProfileStore.remove(str);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(
                        "KnoxVpnCredentialHandler",
                        "Exception occured while trying to delete the info from keystore");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static synchronized KnoxVpnCredentialHandler getInstance() {
        KnoxVpnCredentialHandler knoxVpnCredentialHandler;
        synchronized (KnoxVpnCredentialHandler.class) {
            try {
                if (mInstance == null) {
                    mInstance = new KnoxVpnCredentialHandler();
                }
                knoxVpnCredentialHandler = mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return knoxVpnCredentialHandler;
    }

    public static String retrieveCredentialsFromKeystore(String str) {
        Log.d(
                "KnoxVpnCredentialHandler",
                "retrieveCredentialsFromKeystore alias retrieved is " + str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str2 = null;
        try {
            try {
                byte[] bArr = LegacyVpnProfileStore.get(str);
                if (bArr != null) {
                    str2 = new String(bArr);
                }
            } catch (Exception e) {
                Log.d(
                        "KnoxVpnCredentialHandler",
                        "Exception occured while trying to retrieve the info from keystore");
                e.printStackTrace();
            }
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "retrieveCredentialsFromKeystore key retrieved is ",
                    str2,
                    "KnoxVpnCredentialHandler");
            return str2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean storeCredentialsInKeystore(String str, String str2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            byte[] bytes = str2.getBytes();
            boolean z = false;
            if (bytes != null) {
                try {
                    z = LegacyVpnProfileStore.put(str, bytes);
                } catch (Exception e) {
                    Log.e(
                            "KnoxVpnCredentialHandler",
                            "Exception occured while trying to store the info inside keystore");
                    e.printStackTrace();
                }
                Log.d(
                        "KnoxVpnCredentialHandler",
                        "storeCredentialsInKeystore status key is "
                                + z
                                + " for alias "
                                + str
                                + " for key "
                                + str2);
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
