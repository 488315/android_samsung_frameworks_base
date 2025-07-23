package android.net.vcn.persistablebundleutils;

import android.net.eap.EapSessionConfig;
import android.net.vcn.util.PersistableBundleUtils;
import android.os.PersistableBundle;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class EapSessionConfigUtils {
    private static final String EAP_AKA_CONFIG_KEY = "EAP_AKA_CONFIG_KEY";
    private static final String EAP_AKA_PRIME_CONFIG_KEY = "EAP_AKA_PRIME_CONFIG_KEY";
    private static final String EAP_ID_KEY = "EAP_ID_KEY";
    private static final String EAP_MSCHAP_V2_CONFIG_KEY = "EAP_MSCHAP_V2_CONFIG_KEY";
    private static final String EAP_SIM_CONFIG_KEY = "EAP_SIM_CONFIG_KEY";
    private static final String EAP_TTLS_CONFIG_KEY = "EAP_TTLS_CONFIG_KEY";

    public static PersistableBundle toPersistableBundle(EapSessionConfig eapSessionConfig) {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putPersistableBundle(EAP_ID_KEY, PersistableBundleUtils.fromByteArray(eapSessionConfig.getEapIdentity()));
        if (eapSessionConfig.getEapSimConfig() != null) {
            persistableBundle.putPersistableBundle(EAP_SIM_CONFIG_KEY, EapSimConfigUtils.toPersistableBundle(eapSessionConfig.getEapSimConfig()));
        }
        if (eapSessionConfig.getEapTtlsConfig() != null) {
            persistableBundle.putPersistableBundle(EAP_TTLS_CONFIG_KEY, EapTtlsConfigUtils.toPersistableBundle(eapSessionConfig.getEapTtlsConfig()));
        }
        if (eapSessionConfig.getEapAkaConfig() != null) {
            persistableBundle.putPersistableBundle(EAP_AKA_CONFIG_KEY, EapAkaConfigUtils.toPersistableBundle(eapSessionConfig.getEapAkaConfig()));
        }
        if (eapSessionConfig.getEapMsChapV2Config() != null) {
            persistableBundle.putPersistableBundle(EAP_MSCHAP_V2_CONFIG_KEY, EapMsChapV2ConfigUtils.toPersistableBundle(eapSessionConfig.getEapMsChapV2Config()));
        }
        if (eapSessionConfig.getEapAkaPrimeConfig() != null) {
            persistableBundle.putPersistableBundle(EAP_AKA_PRIME_CONFIG_KEY, EapAkaPrimeConfigUtils.toPersistableBundle(eapSessionConfig.getEapAkaPrimeConfig()));
        }
        return persistableBundle;
    }

    public static EapSessionConfig fromPersistableBundle(PersistableBundle persistableBundle) {
        Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
        EapSessionConfig.Builder builder = new EapSessionConfig.Builder();
        PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle(EAP_ID_KEY);
        Objects.requireNonNull(persistableBundle2, "EAP ID was null");
        builder.setEapIdentity(PersistableBundleUtils.toByteArray(persistableBundle2));
        PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(EAP_SIM_CONFIG_KEY);
        if (persistableBundle3 != null) {
            EapSimConfigUtils.setBuilderByReadingPersistableBundle(persistableBundle3, builder);
        }
        PersistableBundle persistableBundle4 = persistableBundle.getPersistableBundle(EAP_TTLS_CONFIG_KEY);
        if (persistableBundle4 != null) {
            EapTtlsConfigUtils.setBuilderByReadingPersistableBundle(persistableBundle4, builder);
        }
        PersistableBundle persistableBundle5 = persistableBundle.getPersistableBundle(EAP_AKA_CONFIG_KEY);
        if (persistableBundle5 != null) {
            EapAkaConfigUtils.setBuilderByReadingPersistableBundle(persistableBundle5, builder);
        }
        PersistableBundle persistableBundle6 = persistableBundle.getPersistableBundle(EAP_MSCHAP_V2_CONFIG_KEY);
        if (persistableBundle6 != null) {
            EapMsChapV2ConfigUtils.setBuilderByReadingPersistableBundle(persistableBundle6, builder);
        }
        PersistableBundle persistableBundle7 = persistableBundle.getPersistableBundle(EAP_AKA_PRIME_CONFIG_KEY);
        if (persistableBundle7 != null) {
            EapAkaPrimeConfigUtils.setBuilderByReadingPersistableBundle(persistableBundle7, builder);
        }
        return builder.build();
    }

    private static class EapMethodConfigUtils {
        private static final String METHOD_TYPE = "METHOD_TYPE";

        private EapMethodConfigUtils() {
        }

        public static PersistableBundle toPersistableBundle(EapSessionConfig.EapMethodConfig eapMethodConfig) {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putInt(METHOD_TYPE, eapMethodConfig.getMethodType());
            return persistableBundle;
        }
    }

    private static class EapUiccConfigUtils extends EapMethodConfigUtils {
        static final String APP_TYPE_KEY = "APP_TYPE_KEY";
        static final String SUB_ID_KEY = "SUB_ID_KEY";

        private EapUiccConfigUtils() {
            super();
        }

        protected static PersistableBundle toPersistableBundle(EapSessionConfig.EapUiccConfig eapUiccConfig) {
            PersistableBundle persistableBundle = EapMethodConfigUtils.toPersistableBundle(eapUiccConfig);
            persistableBundle.putInt(SUB_ID_KEY, eapUiccConfig.getSubId());
            persistableBundle.putInt(APP_TYPE_KEY, eapUiccConfig.getAppType());
            return persistableBundle;
        }
    }

    private static final class EapSimConfigUtils extends EapUiccConfigUtils {
        private EapSimConfigUtils() {
            super();
        }

        public static PersistableBundle toPersistableBundle(EapSessionConfig.EapSimConfig eapSimConfig) {
            return EapUiccConfigUtils.toPersistableBundle((EapSessionConfig.EapUiccConfig) eapSimConfig);
        }

        public static void setBuilderByReadingPersistableBundle(PersistableBundle persistableBundle, EapSessionConfig.Builder builder) {
            Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
            builder.setEapSimConfig(persistableBundle.getInt("SUB_ID_KEY"), persistableBundle.getInt("APP_TYPE_KEY"));
        }
    }

    private static class EapAkaConfigUtils extends EapUiccConfigUtils {
        private EapAkaConfigUtils() {
            super();
        }

        public static PersistableBundle toPersistableBundle(EapSessionConfig.EapAkaConfig eapAkaConfig) {
            return EapUiccConfigUtils.toPersistableBundle((EapSessionConfig.EapUiccConfig) eapAkaConfig);
        }

        public static void setBuilderByReadingPersistableBundle(PersistableBundle persistableBundle, EapSessionConfig.Builder builder) {
            Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
            builder.setEapAkaConfig(persistableBundle.getInt("SUB_ID_KEY"), persistableBundle.getInt("APP_TYPE_KEY"));
        }
    }

    private static final class EapAkaPrimeConfigUtils extends EapAkaConfigUtils {
        private static final String ALL_MISMATCHED_NETWORK_KEY = "ALL_MISMATCHED_NETWORK_KEY";
        private static final String NETWORK_NAME_KEY = "NETWORK_NAME_KEY";

        private EapAkaPrimeConfigUtils() {
            super();
        }

        public static PersistableBundle toPersistableBundle(EapSessionConfig.EapAkaPrimeConfig eapAkaPrimeConfig) {
            PersistableBundle persistableBundle = EapUiccConfigUtils.toPersistableBundle((EapSessionConfig.EapUiccConfig) eapAkaPrimeConfig);
            persistableBundle.putString(NETWORK_NAME_KEY, eapAkaPrimeConfig.getNetworkName());
            persistableBundle.putBoolean(ALL_MISMATCHED_NETWORK_KEY, eapAkaPrimeConfig.allowsMismatchedNetworkNames());
            return persistableBundle;
        }

        public static void setBuilderByReadingPersistableBundle(PersistableBundle persistableBundle, EapSessionConfig.Builder builder) {
            Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
            builder.setEapAkaPrimeConfig(persistableBundle.getInt("SUB_ID_KEY"), persistableBundle.getInt("APP_TYPE_KEY"), persistableBundle.getString(NETWORK_NAME_KEY), persistableBundle.getBoolean(ALL_MISMATCHED_NETWORK_KEY));
        }
    }

    private static final class EapMsChapV2ConfigUtils extends EapMethodConfigUtils {
        private static final String PASSWORD_KEY = "PASSWORD_KEY";
        private static final String USERNAME_KEY = "USERNAME_KEY";

        private EapMsChapV2ConfigUtils() {
            super();
        }

        public static PersistableBundle toPersistableBundle(EapSessionConfig.EapMsChapV2Config eapMsChapV2Config) {
            PersistableBundle persistableBundle = EapMethodConfigUtils.toPersistableBundle(eapMsChapV2Config);
            persistableBundle.putString(USERNAME_KEY, eapMsChapV2Config.getUsername());
            persistableBundle.putString(PASSWORD_KEY, eapMsChapV2Config.getPassword());
            return persistableBundle;
        }

        public static void setBuilderByReadingPersistableBundle(PersistableBundle persistableBundle, EapSessionConfig.Builder builder) {
            Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
            builder.setEapMsChapV2Config(persistableBundle.getString(USERNAME_KEY), persistableBundle.getString(PASSWORD_KEY));
        }
    }

    private static final class EapTtlsConfigUtils extends EapMethodConfigUtils {
        private static final String EAP_SESSION_CONFIG_KEY = "EAP_SESSION_CONFIG_KEY";
        private static final String TRUST_CERT_KEY = "TRUST_CERT_KEY";

        private EapTtlsConfigUtils() {
            super();
        }

        public static PersistableBundle toPersistableBundle(EapSessionConfig.EapTtlsConfig eapTtlsConfig) {
            PersistableBundle persistableBundle = EapMethodConfigUtils.toPersistableBundle(eapTtlsConfig);
            try {
                if (eapTtlsConfig.getServerCaCert() != null) {
                    persistableBundle.putPersistableBundle(TRUST_CERT_KEY, PersistableBundleUtils.fromByteArray(eapTtlsConfig.getServerCaCert().getEncoded()));
                }
                persistableBundle.putPersistableBundle(EAP_SESSION_CONFIG_KEY, EapSessionConfigUtils.toPersistableBundle(eapTtlsConfig.getInnerEapSessionConfig()));
                return persistableBundle;
            } catch (CertificateEncodingException unused) {
                throw new IllegalStateException("Fail to encode the certificate");
            }
        }

        public static void setBuilderByReadingPersistableBundle(PersistableBundle persistableBundle, EapSessionConfig.Builder builder) {
            Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
            PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle(TRUST_CERT_KEY);
            X509Certificate certificateFromByteArray = persistableBundle2 != null ? CertUtils.certificateFromByteArray(PersistableBundleUtils.toByteArray(persistableBundle2)) : null;
            PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(EAP_SESSION_CONFIG_KEY);
            Objects.requireNonNull(persistableBundle3, "Inner EAP Session Config was null");
            builder.setEapTtlsConfig(certificateFromByteArray, EapSessionConfigUtils.fromPersistableBundle(persistableBundle3));
        }
    }
}
