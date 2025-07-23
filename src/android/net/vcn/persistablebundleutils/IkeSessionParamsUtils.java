package android.net.vcn.persistablebundleutils;

import android.net.InetAddresses;
import android.net.eap.EapSessionConfig;
import android.net.ipsec.ike.IkeSaProposal;
import android.net.ipsec.ike.IkeSessionParams;
import android.net.vcn.persistablebundleutils.IkeSessionParamsUtils;
import android.net.vcn.util.PersistableBundleUtils;
import android.os.PersistableBundle;
import android.system.OsConstants;
import android.util.ArraySet;
import android.util.Log;
import java.net.InetAddress;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.ToIntFunction;

/* loaded from: classes3.dex */
public final class IkeSessionParamsUtils {
    private static final String CONFIG_REQUESTS_KEY = "CONFIG_REQUESTS_KEY";
    private static final String DPD_DELAY_SEC_KEY = "DPD_DELAY_SEC_KEY";
    private static final String ENCAP_TYPE_KEY = "ENCAP_TYPE_KEY";
    private static final String HARD_LIFETIME_SEC_KEY = "HARD_LIFETIME_SEC_KEY";
    private static final Set<Integer> IKE_OPTIONS;
    private static final String IKE_OPTIONS_KEY = "IKE_OPTIONS_KEY";
    public static final int IKE_OPTION_AUTOMATIC_ADDRESS_FAMILY_SELECTION = 6;
    public static final int IKE_OPTION_AUTOMATIC_NATT_KEEPALIVES = 7;
    private static final String IP_VERSION_KEY = "IP_VERSION_KEY";
    private static final String LOCAL_AUTH_KEY = "LOCAL_AUTH_KEY";
    private static final String LOCAL_ID_KEY = "LOCAL_ID_KEY";
    private static final String NATT_KEEPALIVE_DELAY_SEC_KEY = "NATT_KEEPALIVE_DELAY_SEC_KEY";
    private static final String REMOTE_AUTH_KEY = "REMOTE_AUTH_KEY";
    private static final String REMOTE_ID_KEY = "REMOTE_ID_KEY";
    private static final String RETRANS_TIMEOUTS_KEY = "RETRANS_TIMEOUTS_KEY";
    private static final String SA_PROPOSALS_KEY = "SA_PROPOSALS_KEY";
    private static final String SERVER_HOST_NAME_KEY = "SERVER_HOST_NAME_KEY";
    private static final String SOFT_LIFETIME_SEC_KEY = "SOFT_LIFETIME_SEC_KEY";
    private static final String TAG = "IkeSessionParamsUtils";

    static {
        ArraySet arraySet = new ArraySet();
        IKE_OPTIONS = arraySet;
        arraySet.add(0);
        arraySet.add(1);
        arraySet.add(2);
        arraySet.add(3);
        arraySet.add(4);
        arraySet.add(5);
        arraySet.add(6);
        arraySet.add(7);
        arraySet.add(8);
    }

    public static boolean isIkeOptionValid(int i) {
        try {
            new IkeSessionParams.Builder().addIkeOption(i);
            return true;
        } catch (IllegalArgumentException unused) {
            Log.d(TAG, "Option not supported; discarding: " + i);
            return false;
        }
    }

    public static PersistableBundle toPersistableBundle(IkeSessionParams ikeSessionParams) {
        if (ikeSessionParams.getNetwork() != null || ikeSessionParams.getIke3gppExtension() != null) {
            throw new IllegalStateException("Cannot convert a IkeSessionParams with a caller configured network or with 3GPP extension enabled");
        }
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString(SERVER_HOST_NAME_KEY, ikeSessionParams.getServerHostname());
        persistableBundle.putPersistableBundle(SA_PROPOSALS_KEY, PersistableBundleUtils.fromList(ikeSessionParams.getSaProposals(), new PersistableBundleUtils.Serializer() { // from class: android.net.vcn.persistablebundleutils.IkeSessionParamsUtils$$ExternalSyntheticLambda2
            @Override // android.net.vcn.util.PersistableBundleUtils.Serializer
            public final PersistableBundle toPersistableBundle(Object obj) {
                return IkeSaProposalUtils.toPersistableBundle((IkeSaProposal) obj);
            }
        }));
        persistableBundle.putPersistableBundle(LOCAL_ID_KEY, IkeIdentificationUtils.toPersistableBundle(ikeSessionParams.getLocalIdentification()));
        persistableBundle.putPersistableBundle(REMOTE_ID_KEY, IkeIdentificationUtils.toPersistableBundle(ikeSessionParams.getRemoteIdentification()));
        persistableBundle.putPersistableBundle(LOCAL_AUTH_KEY, AuthConfigUtils.toPersistableBundle(ikeSessionParams.getLocalAuthConfig()));
        persistableBundle.putPersistableBundle(REMOTE_AUTH_KEY, AuthConfigUtils.toPersistableBundle(ikeSessionParams.getRemoteAuthConfig()));
        ArrayList arrayList = new ArrayList();
        Iterator it = ikeSessionParams.getConfigurationRequests().iterator();
        while (it.hasNext()) {
            arrayList.add(new ConfigRequest((IkeSessionParams.IkeConfigRequest) it.next()));
        }
        persistableBundle.putPersistableBundle(CONFIG_REQUESTS_KEY, PersistableBundleUtils.fromList(arrayList, new PersistableBundleUtils.Serializer() { // from class: android.net.vcn.persistablebundleutils.IkeSessionParamsUtils$$ExternalSyntheticLambda3
            @Override // android.net.vcn.util.PersistableBundleUtils.Serializer
            public final PersistableBundle toPersistableBundle(Object obj) {
                return ((IkeSessionParamsUtils.ConfigRequest) obj).toPersistableBundle();
            }
        }));
        persistableBundle.putIntArray(RETRANS_TIMEOUTS_KEY, ikeSessionParams.getRetransmissionTimeoutsMillis());
        persistableBundle.putInt(HARD_LIFETIME_SEC_KEY, ikeSessionParams.getHardLifetimeSeconds());
        persistableBundle.putInt(SOFT_LIFETIME_SEC_KEY, ikeSessionParams.getSoftLifetimeSeconds());
        persistableBundle.putInt(DPD_DELAY_SEC_KEY, ikeSessionParams.getDpdDelaySeconds());
        persistableBundle.putInt(NATT_KEEPALIVE_DELAY_SEC_KEY, ikeSessionParams.getNattKeepAliveDelaySeconds());
        persistableBundle.putInt(IP_VERSION_KEY, ikeSessionParams.getIpVersion());
        persistableBundle.putInt(ENCAP_TYPE_KEY, ikeSessionParams.getEncapType());
        ArrayList arrayList2 = new ArrayList();
        try {
            arrayList2.addAll(ikeSessionParams.getIkeOptions());
        } catch (Exception unused) {
            arrayList2.clear();
            for (Integer num : IKE_OPTIONS) {
                int intValue = num.intValue();
                if (isIkeOptionValid(intValue) && ikeSessionParams.hasIkeOption(intValue)) {
                    arrayList2.add(num);
                }
            }
        }
        persistableBundle.putIntArray(IKE_OPTIONS_KEY, arrayList2.stream().mapToInt(new ToIntFunction() { // from class: android.net.vcn.persistablebundleutils.IkeSessionParamsUtils$$ExternalSyntheticLambda4
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                int intValue2;
                intValue2 = ((Integer) obj).intValue();
                return intValue2;
            }
        }).toArray());
        return persistableBundle;
    }

    public static IkeSessionParams fromPersistableBundle(PersistableBundle persistableBundle) {
        Objects.requireNonNull(persistableBundle, "PersistableBundle is null");
        IkeSessionParams.Builder builder = new IkeSessionParams.Builder();
        builder.setServerHostname(persistableBundle.getString(SERVER_HOST_NAME_KEY));
        PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle(SA_PROPOSALS_KEY);
        Objects.requireNonNull(persistableBundle, "SA Proposals was null");
        Iterator it = PersistableBundleUtils.toList(persistableBundle2, new PersistableBundleUtils.Deserializer() { // from class: android.net.vcn.persistablebundleutils.IkeSessionParamsUtils$$ExternalSyntheticLambda0
            @Override // android.net.vcn.util.PersistableBundleUtils.Deserializer
            public final Object fromPersistableBundle(PersistableBundle persistableBundle3) {
                return IkeSaProposalUtils.fromPersistableBundle(persistableBundle3);
            }
        }).iterator();
        while (it.hasNext()) {
            builder.addSaProposal((IkeSaProposal) it.next());
        }
        builder.setLocalIdentification(IkeIdentificationUtils.fromPersistableBundle(persistableBundle.getPersistableBundle(LOCAL_ID_KEY)));
        builder.setRemoteIdentification(IkeIdentificationUtils.fromPersistableBundle(persistableBundle.getPersistableBundle(REMOTE_ID_KEY)));
        AuthConfigUtils.setBuilderByReadingPersistableBundle(persistableBundle.getPersistableBundle(LOCAL_AUTH_KEY), persistableBundle.getPersistableBundle(REMOTE_AUTH_KEY), builder);
        builder.setRetransmissionTimeoutsMillis(persistableBundle.getIntArray(RETRANS_TIMEOUTS_KEY));
        builder.setLifetimeSeconds(persistableBundle.getInt(HARD_LIFETIME_SEC_KEY), persistableBundle.getInt(SOFT_LIFETIME_SEC_KEY));
        builder.setDpdDelaySeconds(persistableBundle.getInt(DPD_DELAY_SEC_KEY));
        builder.setNattKeepAliveDelaySeconds(persistableBundle.getInt(NATT_KEEPALIVE_DELAY_SEC_KEY));
        builder.setIpVersion(persistableBundle.getInt(IP_VERSION_KEY));
        builder.setEncapType(persistableBundle.getInt(ENCAP_TYPE_KEY));
        PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(CONFIG_REQUESTS_KEY);
        Objects.requireNonNull(persistableBundle3, "Config request list was null");
        for (ConfigRequest configRequest : PersistableBundleUtils.toList(persistableBundle3, new PersistableBundleUtils.Deserializer() { // from class: android.net.vcn.persistablebundleutils.IkeSessionParamsUtils$$ExternalSyntheticLambda1
            @Override // android.net.vcn.util.PersistableBundleUtils.Deserializer
            public final Object fromPersistableBundle(PersistableBundle persistableBundle4) {
                return new IkeSessionParamsUtils.ConfigRequest(persistableBundle4);
            }
        })) {
            int i = configRequest.type;
            if (i != 1) {
                if (i == 2) {
                    if (configRequest.address == null) {
                        builder.addPcscfServerRequest(OsConstants.AF_INET6);
                    } else {
                        builder.addPcscfServerRequest(configRequest.address);
                    }
                } else {
                    throw new IllegalArgumentException("Unrecognized config request type: " + configRequest.type);
                }
            } else if (configRequest.address == null) {
                builder.addPcscfServerRequest(OsConstants.AF_INET);
            } else {
                builder.addPcscfServerRequest(configRequest.address);
            }
        }
        Iterator<Integer> it2 = IKE_OPTIONS.iterator();
        while (it2.hasNext()) {
            int intValue = it2.next().intValue();
            if (isIkeOptionValid(intValue)) {
                builder.removeIkeOption(intValue);
            }
        }
        for (int i2 : persistableBundle.getIntArray(IKE_OPTIONS_KEY)) {
            if (isIkeOptionValid(i2)) {
                builder.addIkeOption(i2);
            }
        }
        return builder.build();
    }

    private static final class AuthConfigUtils {
        private static final String AUTH_METHOD_KEY = "AUTH_METHOD_KEY";
        private static final int IKE_AUTH_METHOD_EAP = 3;
        private static final int IKE_AUTH_METHOD_PSK = 1;
        private static final int IKE_AUTH_METHOD_PUB_KEY_SIGNATURE = 2;

        private AuthConfigUtils() {
        }

        public static PersistableBundle toPersistableBundle(IkeSessionParams.IkeAuthConfig ikeAuthConfig) {
            if (ikeAuthConfig instanceof IkeSessionParams.IkeAuthPskConfig) {
                return IkeAuthPskConfigUtils.toPersistableBundle((IkeSessionParams.IkeAuthPskConfig) ikeAuthConfig, createPersistableBundle(1));
            }
            if (ikeAuthConfig instanceof IkeSessionParams.IkeAuthDigitalSignLocalConfig) {
                return IkeAuthDigitalSignConfigUtils.toPersistableBundle((IkeSessionParams.IkeAuthDigitalSignLocalConfig) ikeAuthConfig, createPersistableBundle(2));
            }
            if (ikeAuthConfig instanceof IkeSessionParams.IkeAuthDigitalSignRemoteConfig) {
                return IkeAuthDigitalSignConfigUtils.toPersistableBundle((IkeSessionParams.IkeAuthDigitalSignRemoteConfig) ikeAuthConfig, createPersistableBundle(2));
            }
            if (ikeAuthConfig instanceof IkeSessionParams.IkeAuthEapConfig) {
                return IkeAuthEapConfigUtils.toPersistableBundle((IkeSessionParams.IkeAuthEapConfig) ikeAuthConfig, createPersistableBundle(3));
            }
            throw new IllegalStateException("Invalid IkeAuthConfig subclass");
        }

        private static PersistableBundle createPersistableBundle(int i) {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putInt(AUTH_METHOD_KEY, i);
            return persistableBundle;
        }

        public static void setBuilderByReadingPersistableBundle(PersistableBundle persistableBundle, PersistableBundle persistableBundle2, IkeSessionParams.Builder builder) {
            Objects.requireNonNull(persistableBundle, "localAuthBundle was null");
            Objects.requireNonNull(persistableBundle2, "remoteAuthBundle was null");
            int i = persistableBundle.getInt(AUTH_METHOD_KEY);
            int i2 = persistableBundle2.getInt(AUTH_METHOD_KEY);
            if (i == 1) {
                if (i2 != 1) {
                    throw new IllegalArgumentException("Expect remote auth method to be PSK based, but was " + i2);
                }
                IkeAuthPskConfigUtils.setBuilderByReadingPersistableBundle(persistableBundle, persistableBundle2, builder);
                return;
            }
            if (i == 2) {
                if (i2 != 2) {
                    throw new IllegalArgumentException("Expect remote auth method to be digital signature based, but was " + i2);
                }
                IkeAuthDigitalSignConfigUtils.setBuilderByReadingPersistableBundle(persistableBundle, persistableBundle2, builder);
                return;
            }
            if (i != 3) {
                throw new IllegalArgumentException("Invalid EAP method type " + i);
            }
            if (i2 != 2) {
                throw new IllegalArgumentException("When using EAP for local authentication, expect remote auth method to be digital signature based, but was " + i2);
            }
            IkeAuthEapConfigUtils.setBuilderByReadingPersistableBundle(persistableBundle, persistableBundle2, builder);
        }
    }

    private static final class IkeAuthPskConfigUtils {
        private static final String PSK_KEY = "PSK_KEY";

        private IkeAuthPskConfigUtils() {
        }

        public static PersistableBundle toPersistableBundle(IkeSessionParams.IkeAuthPskConfig ikeAuthPskConfig, PersistableBundle persistableBundle) {
            persistableBundle.putPersistableBundle(PSK_KEY, PersistableBundleUtils.fromByteArray(ikeAuthPskConfig.getPsk()));
            return persistableBundle;
        }

        public static void setBuilderByReadingPersistableBundle(PersistableBundle persistableBundle, PersistableBundle persistableBundle2, IkeSessionParams.Builder builder) {
            Objects.requireNonNull(persistableBundle, "localAuthBundle was null");
            Objects.requireNonNull(persistableBundle2, "remoteAuthBundle was null");
            PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(PSK_KEY);
            PersistableBundle persistableBundle4 = persistableBundle2.getPersistableBundle(PSK_KEY);
            Objects.requireNonNull(persistableBundle, "Local PSK was null");
            Objects.requireNonNull(persistableBundle2, "Remote PSK was null");
            byte[] byteArray = PersistableBundleUtils.toByteArray(persistableBundle3);
            if (!Arrays.equals(byteArray, PersistableBundleUtils.toByteArray(persistableBundle4))) {
                throw new IllegalArgumentException("Local PSK and remote PSK are different");
            }
            builder.setAuthPsk(byteArray);
        }
    }

    private static class IkeAuthDigitalSignConfigUtils {
        private static final String END_CERT_KEY = "END_CERT_KEY";
        private static final String INTERMEDIATE_CERTS_KEY = "INTERMEDIATE_CERTS_KEY";
        private static final String PRIVATE_KEY_KEY = "PRIVATE_KEY_KEY";
        private static final String TRUST_CERT_KEY = "TRUST_CERT_KEY";

        private IkeAuthDigitalSignConfigUtils() {
        }

        public static PersistableBundle toPersistableBundle(IkeSessionParams.IkeAuthDigitalSignLocalConfig ikeAuthDigitalSignLocalConfig, PersistableBundle persistableBundle) {
            try {
                persistableBundle.putPersistableBundle(END_CERT_KEY, PersistableBundleUtils.fromByteArray(ikeAuthDigitalSignLocalConfig.getClientEndCertificate().getEncoded()));
                List<X509Certificate> intermediateCertificates = ikeAuthDigitalSignLocalConfig.getIntermediateCertificates();
                ArrayList arrayList = new ArrayList(intermediateCertificates.size());
                Iterator<X509Certificate> it = intermediateCertificates.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().getEncoded());
                }
                persistableBundle.putPersistableBundle(INTERMEDIATE_CERTS_KEY, PersistableBundleUtils.fromList(arrayList, new PersistableBundleUtils.Serializer() { // from class: android.net.vcn.persistablebundleutils.IkeSessionParamsUtils$IkeAuthDigitalSignConfigUtils$$ExternalSyntheticLambda1
                    @Override // android.net.vcn.util.PersistableBundleUtils.Serializer
                    public final PersistableBundle toPersistableBundle(Object obj) {
                        return PersistableBundleUtils.fromByteArray((byte[]) obj);
                    }
                }));
                persistableBundle.putPersistableBundle(PRIVATE_KEY_KEY, PersistableBundleUtils.fromByteArray(ikeAuthDigitalSignLocalConfig.getPrivateKey().getEncoded()));
                return persistableBundle;
            } catch (CertificateEncodingException unused) {
                throw new IllegalArgumentException("Fail to encode certificate");
            }
        }

        public static PersistableBundle toPersistableBundle(IkeSessionParams.IkeAuthDigitalSignRemoteConfig ikeAuthDigitalSignRemoteConfig, PersistableBundle persistableBundle) {
            try {
                X509Certificate remoteCaCert = ikeAuthDigitalSignRemoteConfig.getRemoteCaCert();
                if (remoteCaCert != null) {
                    persistableBundle.putPersistableBundle(TRUST_CERT_KEY, PersistableBundleUtils.fromByteArray(remoteCaCert.getEncoded()));
                }
                return persistableBundle;
            } catch (CertificateEncodingException unused) {
                throw new IllegalArgumentException("Fail to encode the certificate");
            }
        }

        public static void setBuilderByReadingPersistableBundle(PersistableBundle persistableBundle, PersistableBundle persistableBundle2, IkeSessionParams.Builder builder) {
            Objects.requireNonNull(persistableBundle, "localAuthBundle was null");
            Objects.requireNonNull(persistableBundle2, "remoteAuthBundle was null");
            PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(END_CERT_KEY);
            Objects.requireNonNull(persistableBundle3, "End cert was null");
            X509Certificate certificateFromByteArray = CertUtils.certificateFromByteArray(PersistableBundleUtils.toByteArray(persistableBundle3));
            PersistableBundle persistableBundle4 = persistableBundle.getPersistableBundle(INTERMEDIATE_CERTS_KEY);
            Objects.requireNonNull(persistableBundle4, "Intermediate certs was null");
            List list = PersistableBundleUtils.toList(persistableBundle4, new PersistableBundleUtils.Deserializer() { // from class: android.net.vcn.persistablebundleutils.IkeSessionParamsUtils$IkeAuthDigitalSignConfigUtils$$ExternalSyntheticLambda0
                @Override // android.net.vcn.util.PersistableBundleUtils.Deserializer
                public final Object fromPersistableBundle(PersistableBundle persistableBundle5) {
                    return PersistableBundleUtils.toByteArray(persistableBundle5);
                }
            });
            ArrayList arrayList = new ArrayList(list.size());
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(CertUtils.certificateFromByteArray((byte[]) it.next()));
            }
            PersistableBundle persistableBundle5 = persistableBundle.getPersistableBundle(PRIVATE_KEY_KEY);
            Objects.requireNonNull(persistableBundle5, "PrivateKey bundle was null");
            RSAPrivateKey privateKeyFromByteArray = CertUtils.privateKeyFromByteArray(PersistableBundleUtils.toByteArray(persistableBundle5));
            PersistableBundle persistableBundle6 = persistableBundle2.getPersistableBundle(TRUST_CERT_KEY);
            builder.setAuthDigitalSignature(persistableBundle6 != null ? CertUtils.certificateFromByteArray(PersistableBundleUtils.toByteArray(persistableBundle6)) : null, certificateFromByteArray, arrayList, privateKeyFromByteArray);
        }
    }

    private static final class IkeAuthEapConfigUtils {
        private static final String EAP_CONFIG_KEY = "EAP_CONFIG_KEY";

        private IkeAuthEapConfigUtils() {
        }

        public static PersistableBundle toPersistableBundle(IkeSessionParams.IkeAuthEapConfig ikeAuthEapConfig, PersistableBundle persistableBundle) {
            persistableBundle.putPersistableBundle(EAP_CONFIG_KEY, EapSessionConfigUtils.toPersistableBundle(ikeAuthEapConfig.getEapConfig()));
            return persistableBundle;
        }

        public static void setBuilderByReadingPersistableBundle(PersistableBundle persistableBundle, PersistableBundle persistableBundle2, IkeSessionParams.Builder builder) {
            PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(EAP_CONFIG_KEY);
            Objects.requireNonNull(persistableBundle3, "EAP Config was null");
            EapSessionConfig fromPersistableBundle = EapSessionConfigUtils.fromPersistableBundle(persistableBundle3);
            PersistableBundle persistableBundle4 = persistableBundle2.getPersistableBundle("TRUST_CERT_KEY");
            builder.setAuthEap(persistableBundle4 != null ? CertUtils.certificateFromByteArray(PersistableBundleUtils.toByteArray(persistableBundle4)) : null, fromPersistableBundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ConfigRequest {
        private static final String ADDRESS_KEY = "address";
        private static final int IPV4_P_CSCF_ADDRESS = 1;
        private static final int IPV6_P_CSCF_ADDRESS = 2;
        private static final String TYPE_KEY = "type";
        public final InetAddress address;
        public final int type;

        ConfigRequest(IkeSessionParams.IkeConfigRequest ikeConfigRequest) {
            if (ikeConfigRequest instanceof IkeSessionParams.ConfigRequestIpv4PcscfServer) {
                this.type = 1;
                this.address = ((IkeSessionParams.ConfigRequestIpv4PcscfServer) ikeConfigRequest).getAddress();
            } else {
                if (ikeConfigRequest instanceof IkeSessionParams.ConfigRequestIpv6PcscfServer) {
                    this.type = 2;
                    this.address = ((IkeSessionParams.ConfigRequestIpv6PcscfServer) ikeConfigRequest).getAddress();
                    return;
                }
                throw new IllegalStateException("Unknown TunnelModeChildConfigRequest");
            }
        }

        ConfigRequest(PersistableBundle persistableBundle) {
            Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
            this.type = persistableBundle.getInt("type");
            String string = persistableBundle.getString("address");
            if (string == null) {
                this.address = null;
            } else {
                this.address = InetAddresses.parseNumericAddress(string);
            }
        }

        public PersistableBundle toPersistableBundle() {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putInt("type", this.type);
            InetAddress inetAddress = this.address;
            if (inetAddress != null) {
                persistableBundle.putString("address", inetAddress.getHostAddress());
            }
            return persistableBundle;
        }
    }
}
