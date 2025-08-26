package android.security.net.config;

import android.content.Context;
import android.media.MediaMetrics;
import android.util.Log;
import java.security.Provider;
import java.security.Security;
import libcore.net.NetworkSecurityPolicy;

/* loaded from: classes3.dex */
public final class NetworkSecurityConfigProvider extends Provider {
    private static final String LOG_TAG = "nsconfig";
    private static final String PREFIX = NetworkSecurityConfigProvider.class.getPackage().getName() + MediaMetrics.SEPARATOR;

    public NetworkSecurityConfigProvider() {
        super("AndroidNSSP", 1.0d, "Android Network Security Policy Provider");
        put("TrustManagerFactory.PKIX", PREFIX + "RootTrustManagerFactorySpi");
        put("Alg.Alias.TrustManagerFactory.X509", "PKIX");
    }

    public static void install(Context context) {
        ApplicationConfig applicationConfig = new ApplicationConfig(new ManifestConfigSource(context));
        ApplicationConfig.setDefaultInstance(applicationConfig);
        int iInsertProviderAt = Security.insertProviderAt(new NetworkSecurityConfigProvider(), 1);
        if (iInsertProviderAt != 1) {
            throw new RuntimeException("Failed to install provider as highest priority provider. Provider was installed at position " + iInsertProviderAt);
        }
        NetworkSecurityPolicy.setInstance(new ConfigNetworkSecurityPolicy(applicationConfig));
    }

    public static void handleNewApplication(Context context) {
        ApplicationConfig applicationConfig = new ApplicationConfig(new ManifestConfigSource(context));
        ApplicationConfig defaultInstance = ApplicationConfig.getDefaultInstance();
        String str = context.getApplicationInfo().processName;
        if (defaultInstance != null && defaultInstance.isCleartextTrafficPermitted() != applicationConfig.isCleartextTrafficPermitted()) {
            Log.w(LOG_TAG, str + ": New config does not match the previously set config.");
            if (defaultInstance.hasPerDomainConfigs() || applicationConfig.hasPerDomainConfigs()) {
                throw new RuntimeException("Found multiple conflicting per-domain rules");
            }
            if (defaultInstance.isCleartextTrafficPermitted()) {
                applicationConfig = defaultInstance;
            }
        }
        ApplicationConfig.setDefaultInstance(applicationConfig);
    }
}
