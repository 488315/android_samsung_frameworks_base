package android.security;

import android.content.Context;
import android.content.pm.PackageManager;
import android.security.net.config.ApplicationConfig;
import android.security.net.config.ManifestConfigSource;

/* loaded from: classes3.dex */
public class NetworkSecurityPolicy {
    private static final NetworkSecurityPolicy INSTANCE = new NetworkSecurityPolicy();

    private NetworkSecurityPolicy() {
    }

    public static NetworkSecurityPolicy getInstance() {
        return INSTANCE;
    }

    public boolean isCleartextTrafficPermitted() {
        return libcore.net.NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted();
    }

    public boolean isCleartextTrafficPermitted(String str) {
        return libcore.net.NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted(str);
    }

    public void setCleartextTrafficPermitted(boolean z) {
        libcore.net.NetworkSecurityPolicy.setInstance(new OverlayNetworkSecurityPolicy(libcore.net.NetworkSecurityPolicy.getInstance(), z));
    }

    public boolean isCertificateTransparencyVerificationRequired(String str) {
        return libcore.net.NetworkSecurityPolicy.getInstance().isCertificateTransparencyVerificationRequired(str);
    }

    public void handleTrustStorageUpdate() {
        ApplicationConfig defaultInstance = ApplicationConfig.getDefaultInstance();
        if (defaultInstance != null) {
            defaultInstance.handleTrustStorageUpdate();
        }
    }

    public static ApplicationConfig getApplicationConfigForPackage(Context context, String str) throws PackageManager.NameNotFoundException {
        return new ApplicationConfig(new ManifestConfigSource(context.createPackageContext(str, 0)));
    }
}
