package android.security.net.config;

import android.security.net.config.NetworkSecurityConfig;
import android.util.Pair;
import java.security.KeyStore;
import java.util.Set;

/* loaded from: classes3.dex */
class KeyStoreConfigSource implements ConfigSource {
    private final NetworkSecurityConfig mConfig;

    @Override // android.security.net.config.ConfigSource
    public Set<Pair<Domain, NetworkSecurityConfig>> getPerDomainConfigs() {
        return null;
    }

    public KeyStoreConfigSource(KeyStore keyStore) {
        this.mConfig = new NetworkSecurityConfig.Builder().addCertificatesEntryRef(new CertificatesEntryRef(new KeyStoreCertificateSource(keyStore), false, false)).build();
    }

    @Override // android.security.net.config.ConfigSource
    public NetworkSecurityConfig getDefaultConfig() {
        return this.mConfig;
    }
}
