package android.security.net.config;

import android.util.Pair;
import com.android.internal.hidden_from_bootclasspath.android.security.Flags;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes3.dex */
public final class ApplicationConfig {
    private static ApplicationConfig sInstance;
    private static Object sLock = new Object();
    private ConfigSource mConfigSource;
    private Set<Pair<Domain, NetworkSecurityConfig>> mConfigs;
    private NetworkSecurityConfig mDefaultConfig;
    private X509TrustManager mTrustManager;
    private final Object mLock = new Object();
    private boolean mInitialized = false;

    public ApplicationConfig(ConfigSource configSource) {
        this.mConfigSource = configSource;
    }

    public boolean hasPerDomainConfigs() {
        ensureInitialized();
        Set<Pair<Domain, NetworkSecurityConfig>> set = this.mConfigs;
        return (set == null || set.isEmpty()) ? false : true;
    }

    public NetworkSecurityConfig getConfigForHostname(String str) {
        ensureInitialized();
        if (str == null || str.isEmpty() || this.mConfigs == null) {
            return this.mDefaultConfig;
        }
        if (str.charAt(0) == '.') {
            throw new IllegalArgumentException("hostname must not begin with a .");
        }
        String lowerCase = str.toLowerCase(Locale.US);
        if (lowerCase.charAt(lowerCase.length() - 1) == '.') {
            lowerCase = lowerCase.substring(0, lowerCase.length() - 1);
        }
        Pair<Domain, NetworkSecurityConfig> pair = null;
        for (Pair<Domain, NetworkSecurityConfig> pair2 : this.mConfigs) {
            Domain domain = pair2.first;
            NetworkSecurityConfig networkSecurityConfig = pair2.second;
            if (domain.hostname.equals(lowerCase)) {
                return networkSecurityConfig;
            }
            if (domain.subdomainsIncluded && lowerCase.endsWith(domain.hostname) && lowerCase.charAt((lowerCase.length() - domain.hostname.length()) - 1) == '.' && (pair == null || domain.hostname.length() > pair.first.hostname.length())) {
                pair = pair2;
            }
        }
        if (pair != null) {
            return pair.second;
        }
        return this.mDefaultConfig;
    }

    public X509TrustManager getTrustManager() {
        ensureInitialized();
        return this.mTrustManager;
    }

    public boolean isCleartextTrafficPermitted() {
        ensureInitialized();
        Set<Pair<Domain, NetworkSecurityConfig>> set = this.mConfigs;
        if (set != null) {
            Iterator<Pair<Domain, NetworkSecurityConfig>> it = set.iterator();
            while (it.hasNext()) {
                if (!it.next().second.isCleartextTrafficPermitted()) {
                    return false;
                }
            }
        }
        return this.mDefaultConfig.isCleartextTrafficPermitted();
    }

    public boolean isCleartextTrafficPermitted(String str) {
        return getConfigForHostname(str).isCleartextTrafficPermitted();
    }

    public boolean isCertificateTransparencyVerificationRequired(String str) {
        if (Flags.certificateTransparencyConfiguration()) {
            return getConfigForHostname(str).isCertificateTransparencyVerificationRequired();
        }
        return false;
    }

    public void handleTrustStorageUpdate() {
        synchronized (this.mLock) {
            if (this.mInitialized) {
                this.mDefaultConfig.handleTrustStorageUpdate();
                if (this.mConfigs != null) {
                    HashSet hashSet = new HashSet(this.mConfigs.size());
                    for (Pair<Domain, NetworkSecurityConfig> pair : this.mConfigs) {
                        if (hashSet.add(pair.second)) {
                            pair.second.handleTrustStorageUpdate();
                        }
                    }
                }
            }
        }
    }

    private void ensureInitialized() {
        synchronized (this.mLock) {
            if (this.mInitialized) {
                return;
            }
            this.mConfigs = this.mConfigSource.getPerDomainConfigs();
            this.mDefaultConfig = this.mConfigSource.getDefaultConfig();
            this.mConfigSource = null;
            this.mTrustManager = new RootTrustManager(this);
            this.mInitialized = true;
        }
    }

    public static void setDefaultInstance(ApplicationConfig applicationConfig) {
        synchronized (sLock) {
            sInstance = applicationConfig;
        }
    }

    public static ApplicationConfig getDefaultInstance() {
        ApplicationConfig applicationConfig;
        synchronized (sLock) {
            applicationConfig = sInstance;
        }
        return applicationConfig;
    }
}
