package android.security.net.config;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactorySpi;

/* loaded from: classes3.dex */
public class RootTrustManagerFactorySpi extends TrustManagerFactorySpi {
    private ApplicationConfig mApplicationConfig;
    private NetworkSecurityConfig mConfig;

    @Override // javax.net.ssl.TrustManagerFactorySpi
    public void engineInit(ManagerFactoryParameters managerFactoryParameters) throws InvalidAlgorithmParameterException {
        if (!(managerFactoryParameters instanceof ApplicationConfigParameters)) {
            throw new InvalidAlgorithmParameterException("Unsupported spec: " + managerFactoryParameters + ". Only " + ApplicationConfigParameters.class.getName() + " supported");
        }
        this.mApplicationConfig = ((ApplicationConfigParameters) managerFactoryParameters).config;
    }

    @Override // javax.net.ssl.TrustManagerFactorySpi
    public void engineInit(KeyStore keyStore) throws KeyStoreException {
        if (keyStore != null) {
            this.mApplicationConfig = new ApplicationConfig(new KeyStoreConfigSource(keyStore));
        } else {
            this.mApplicationConfig = ApplicationConfig.getDefaultInstance();
        }
    }

    @Override // javax.net.ssl.TrustManagerFactorySpi
    public TrustManager[] engineGetTrustManagers() {
        ApplicationConfig applicationConfig = this.mApplicationConfig;
        if (applicationConfig == null) {
            throw new IllegalStateException("TrustManagerFactory not initialized");
        }
        return new TrustManager[]{applicationConfig.getTrustManager()};
    }

    public static final class ApplicationConfigParameters implements ManagerFactoryParameters {
        public final ApplicationConfig config;

        public ApplicationConfigParameters(ApplicationConfig applicationConfig) {
            this.config = applicationConfig;
        }
    }
}
