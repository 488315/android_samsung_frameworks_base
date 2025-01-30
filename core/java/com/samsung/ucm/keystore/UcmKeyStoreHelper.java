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

    public static void updateUcmProvider(boolean isAdd) {
        boolean existUcmProv = false;
        int bcProviderIndex = -1;
        int index = -1;
        try {
            Provider[] list = Security.getProviders();
            for (Provider provider : list) {
                index++;
                if (provider != null) {
                    if (BouncyCastleProvider.PROVIDER_NAME.equals(provider.getName())) {
                        bcProviderIndex = index;
                        if (existUcmProv) {
                            break;
                        }
                    }
                    if (KnoxUcmKeyStoreProvider.PROVIDER_NAME.equals(provider.getName())) {
                        existUcmProv = true;
                        if (bcProviderIndex != -1) {
                            break;
                        }
                    } else {
                        continue;
                    }
                }
            }
            if (!existUcmProv && isAdd) {
                Provider ucmProvier = new KnoxUcmKeyStoreProvider();
                if (bcProviderIndex != -1) {
                    Security.insertProviderAt(ucmProvier, bcProviderIndex + 1);
                    return;
                } else {
                    Security.addProvider(ucmProvier);
                    return;
                }
            }
            if (existUcmProv && !isAdd) {
                Security.removeProvider(KnoxUcmKeyStoreProvider.PROVIDER_NAME);
            }
        } catch (Exception e) {
            Log.m96e(TAG, "Unable to add KnoxUcmKeyStoreProvider");
            e.printStackTrace();
        }
    }
}
