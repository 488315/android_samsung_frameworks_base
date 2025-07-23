package com.samsung.ucm.keystore;

import android.util.Log;
import com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Provider;
import java.security.Security;

/* loaded from: classes6.dex */
public class UcmKeyStoreHelper {
    private static final String TAG = "UcmKeyStoreHelper";

    private UcmKeyStoreHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static void addUcmProvider() {
        updateUcmProvider(true);
    }

    public static void updateUcmProvider(boolean z) {
        try {
            boolean z2 = false;
            int i = -1;
            int i2 = -1;
            for (Provider provider : Security.getProviders()) {
                i++;
                if (provider != null) {
                    if (BouncyCastleProvider.PROVIDER_NAME.equals(provider.getName())) {
                        i2 = i;
                        if (z2) {
                            break;
                        }
                    }
                    if (KnoxUcmKeyStoreProvider.PROVIDER_NAME.equals(provider.getName())) {
                        z2 = true;
                        if (i2 != -1) {
                            break;
                        }
                    } else {
                        continue;
                    }
                }
            }
            if (z2 || !z) {
                if (!z2 || z) {
                    return;
                }
                Security.removeProvider(KnoxUcmKeyStoreProvider.PROVIDER_NAME);
                return;
            }
            KnoxUcmKeyStoreProvider knoxUcmKeyStoreProvider = new KnoxUcmKeyStoreProvider();
            if (i2 != -1) {
                Security.insertProviderAt(knoxUcmKeyStoreProvider, i2 + 1);
            } else {
                Security.addProvider(knoxUcmKeyStoreProvider);
            }
        } catch (Exception e) {
            Log.e(TAG, "Unable to add KnoxUcmKeyStoreProvider");
            e.printStackTrace();
        }
    }
}
