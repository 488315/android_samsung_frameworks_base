package android.security.net.config;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Pair;
import java.util.Set;

/* loaded from: classes3.dex */
public class ManifestConfigSource implements ConfigSource {
    private static final boolean DBG = false;
    private static final String LOG_TAG = "NetworkSecurityConfig";
    private final ApplicationInfo mApplicationInfo;
    private ConfigSource mConfigSource;
    private final Context mContext;
    private final Object mLock = new Object();

    public ManifestConfigSource(Context context) {
        this.mContext = context;
        this.mApplicationInfo = new ApplicationInfo(context.getApplicationInfo());
    }

    @Override // android.security.net.config.ConfigSource
    public Set<Pair<Domain, NetworkSecurityConfig>> getPerDomainConfigs() {
        return getConfigSource().getPerDomainConfigs();
    }

    @Override // android.security.net.config.ConfigSource
    public NetworkSecurityConfig getDefaultConfig() {
        return getConfigSource().getDefaultConfig();
    }

    private ConfigSource getConfigSource() {
        ConfigSource defaultConfigSource;
        synchronized (this.mLock) {
            ConfigSource configSource = this.mConfigSource;
            if (configSource != null) {
                return configSource;
            }
            int i = this.mApplicationInfo.networkSecurityConfigRes;
            if (i != 0) {
                int i2 = this.mApplicationInfo.flags;
                defaultConfigSource = new XmlConfigSource(this.mContext, i, this.mApplicationInfo);
            } else {
                defaultConfigSource = new DefaultConfigSource(((this.mApplicationInfo.flags & 134217728) == 0 || this.mApplicationInfo.isInstantApp()) ? false : true, this.mApplicationInfo);
            }
            this.mConfigSource = defaultConfigSource;
            return defaultConfigSource;
        }
    }

    private static final class DefaultConfigSource implements ConfigSource {
        private final NetworkSecurityConfig mDefaultConfig;

        @Override // android.security.net.config.ConfigSource
        public Set<Pair<Domain, NetworkSecurityConfig>> getPerDomainConfigs() {
            return null;
        }

        DefaultConfigSource(boolean z, ApplicationInfo applicationInfo) {
            this.mDefaultConfig = NetworkSecurityConfig.getDefaultBuilder(applicationInfo).setCleartextTrafficPermitted(z).build();
        }

        @Override // android.security.net.config.ConfigSource
        public NetworkSecurityConfig getDefaultConfig() {
            return this.mDefaultConfig;
        }
    }
}
