package com.android.server.locksettings.recoverablekeystore;

import java.security.KeyStore;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KeyStoreProxyImpl implements KeyStoreProxy {
    public final KeyStore mKeyStore;

    public KeyStoreProxyImpl(KeyStore keyStore) {
        this.mKeyStore = keyStore;
    }
}
