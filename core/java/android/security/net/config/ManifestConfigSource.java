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
        ConfigSource source;
        synchronized (this.mLock) {
            if (this.mConfigSource != null) {
                return this.mConfigSource;
            }
            int configResource = this.mApplicationInfo.networkSecurityConfigRes;
            boolean usesCleartextTraffic = true;
            if (configResource != 0) {
                if ((this.mApplicationInfo.flags & 2) != 0) {
                }
                source = new XmlConfigSource(this.mContext, configResource, this.mApplicationInfo);
            } else {
                if ((this.mApplicationInfo.flags & 134217728) == 0 || this.mApplicationInfo.isInstantApp()) {
                    usesCleartextTraffic = false;
                }
                source = new DefaultConfigSource(usesCleartextTraffic, this.mApplicationInfo);
            }
            this.mConfigSource = source;
            return this.mConfigSource;
        }
    }

    private static final class DefaultConfigSource implements ConfigSource {
        private final NetworkSecurityConfig mDefaultConfig;

        DefaultConfigSource(boolean usesCleartextTraffic, ApplicationInfo info) {
            this.mDefaultConfig = NetworkSecurityConfig.getDefaultBuilder(info).setCleartextTrafficPermitted(usesCleartextTraffic).build();
        }

        @Override // android.security.net.config.ConfigSource
        public NetworkSecurityConfig getDefaultConfig() {
            return this.mDefaultConfig;
        }

        @Override // android.security.net.config.ConfigSource
        public Set<Pair<Domain, NetworkSecurityConfig>> getPerDomainConfigs() {
            return null;
        }
    }
}
