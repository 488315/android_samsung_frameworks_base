package com.android.server.locksettings.recoverablekeystore;

import java.security.KeyStore;

public final class KeyStoreProxyImpl implements KeyStoreProxy {
    public final KeyStore mKeyStore;

    public KeyStoreProxyImpl(KeyStore keyStore) {
        this.mKeyStore = keyStore;
    }
}
