package android.net;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.ipsec.ike.IkeDerAsn1DnIdentification;
import android.net.ipsec.ike.IkeFqdnIdentification;
import android.net.ipsec.ike.IkeIdentification;
import android.net.ipsec.ike.IkeIpv4AddrIdentification;
import android.net.ipsec.ike.IkeIpv6AddrIdentification;
import android.net.ipsec.ike.IkeKeyIdIdentification;
import android.net.ipsec.ike.IkeRfc822AddrIdentification;
import android.net.ipsec.ike.IkeSessionParams;
import android.net.ipsec.ike.IkeTunnelConnectionParams;
import android.util.Log;
import com.android.internal.net.VpnProfile;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class Ikev2VpnProfile extends PlatformVpnProfile {
    private static final String ANDROID_KEYSTORE_PROVIDER = "AndroidKeyStore";
    public static final List<String> DEFAULT_ALGORITHMS;
    private static final String EMPTY_CERT = "";
    private static final String MISSING_PARAM_MSG_TMPL = "Required parameter was not provided: %s";
    public static final String PREFIX_INLINE = "INLINE:";
    public static final String PREFIX_KEYSTORE_ALIAS = "KEYSTORE_ALIAS:";
    private static final String TAG = "Ikev2VpnProfile";
    private final List<String> mAllowedAlgorithms;
    private final boolean mAutomaticIpVersionSelectionEnabled;
    private final boolean mAutomaticNattKeepaliveTimerEnabled;
    private final IkeTunnelConnectionParams mIkeTunConnParams;
    private final boolean mIsBypassable;
    private final boolean mIsMetered;
    private final boolean mIsRestrictedToTestNetworks;
    private final int mMaxMtu;
    private final String mPassword;
    private final byte[] mPresharedKey;
    private final ProxyInfo mProxyInfo;
    private final PrivateKey mRsaPrivateKey;
    private final String mServerAddr;
    private final X509Certificate mServerRootCaCert;
    private final X509Certificate mUserCert;
    private final String mUserIdentity;
    private final String mUsername;

    private static void addAlgorithmIfSupported(List<String> list, String str) {
        if (IpSecAlgorithm.getSupportedAlgorithms().contains(str)) {
            list.add(str);
        }
    }

    static {
        ArrayList arrayList = new ArrayList();
        addAlgorithmIfSupported(arrayList, "cbc(aes)");
        addAlgorithmIfSupported(arrayList, "rfc3686(ctr(aes))");
        addAlgorithmIfSupported(arrayList, "hmac(sha256)");
        addAlgorithmIfSupported(arrayList, "hmac(sha384)");
        addAlgorithmIfSupported(arrayList, "hmac(sha512)");
        addAlgorithmIfSupported(arrayList, "xcbc(aes)");
        addAlgorithmIfSupported(arrayList, "cmac(aes)");
        addAlgorithmIfSupported(arrayList, "rfc4106(gcm(aes))");
        addAlgorithmIfSupported(arrayList, "rfc7539esp(chacha20,poly1305)");
        DEFAULT_ALGORITHMS = Collections.unmodifiableList(arrayList);
    }

    private Ikev2VpnProfile(int i, String str, String str2, byte[] bArr, X509Certificate x509Certificate, String str3, String str4, PrivateKey privateKey, X509Certificate x509Certificate2, ProxyInfo proxyInfo, List<String> list, boolean z, boolean z2, int i2, boolean z3, boolean z4, boolean z5, IkeTunnelConnectionParams ikeTunnelConnectionParams, boolean z6, boolean z7) {
        super(i, z4, z5);
        checkNotNull(list, MISSING_PARAM_MSG_TMPL, "Allowed Algorithms");
        this.mServerAddr = str;
        this.mUserIdentity = str2;
        this.mPresharedKey = bArr == null ? null : Arrays.copyOf(bArr, bArr.length);
        this.mServerRootCaCert = x509Certificate;
        this.mUsername = str3;
        this.mPassword = str4;
        this.mRsaPrivateKey = privateKey;
        this.mUserCert = x509Certificate2;
        this.mProxyInfo = proxyInfo != null ? new ProxyInfo(proxyInfo) : null;
        this.mAllowedAlgorithms = Collections.unmodifiableList(new ArrayList(list));
        if (z4 && !z) {
            throw new IllegalArgumentException("Vpn must be bypassable if excludeLocalRoutes is set");
        }
        this.mIsBypassable = z;
        this.mIsMetered = z2;
        this.mMaxMtu = i2;
        this.mIsRestrictedToTestNetworks = z3;
        this.mIkeTunConnParams = ikeTunnelConnectionParams;
        this.mAutomaticNattKeepaliveTimerEnabled = z6;
        this.mAutomaticIpVersionSelectionEnabled = z7;
        validate();
    }

    private void validate() {
        if (this.mMaxMtu < 1280) {
            throw new IllegalArgumentException("Max MTU must be at least1280");
        }
        if (this.mIkeTunConnParams != null) {
            return;
        }
        Preconditions.checkStringNotEmpty(this.mServerAddr, MISSING_PARAM_MSG_TMPL, "Server Address");
        Preconditions.checkStringNotEmpty(this.mUserIdentity, MISSING_PARAM_MSG_TMPL, "User Identity");
        int i = this.mType;
        if (i == 6) {
            checkNotNull(this.mUsername, MISSING_PARAM_MSG_TMPL, "Username");
            checkNotNull(this.mPassword, MISSING_PARAM_MSG_TMPL, "Password");
            X509Certificate x509Certificate = this.mServerRootCaCert;
            if (x509Certificate != null) {
                checkCert(x509Certificate);
            }
        } else if (i == 7) {
            checkNotNull(this.mPresharedKey, MISSING_PARAM_MSG_TMPL, "Preshared Key");
        } else if (i == 8) {
            checkNotNull(this.mUserCert, MISSING_PARAM_MSG_TMPL, "User cert");
            checkNotNull(this.mRsaPrivateKey, MISSING_PARAM_MSG_TMPL, "RSA Private key");
            checkCert(this.mUserCert);
            X509Certificate x509Certificate2 = this.mServerRootCaCert;
            if (x509Certificate2 != null) {
                checkCert(x509Certificate2);
            }
        } else {
            throw new IllegalArgumentException("Invalid auth method set");
        }
        validateAllowedAlgorithms(this.mAllowedAlgorithms);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void validateAllowedAlgorithms(List<String> list) {
        if (list.contains("hmac(md5)") || list.contains("hmac(sha1)")) {
            throw new IllegalArgumentException("Algorithm not supported for IKEv2 VPN profiles");
        }
        if (!hasAeadAlgorithms(list) && !hasNormalModeAlgorithms(list)) {
            throw new IllegalArgumentException("Algorithm set missing support for Auth, Crypt or both");
        }
    }

    public static boolean hasAeadAlgorithms(List<String> list) {
        return list.contains("rfc4106(gcm(aes))");
    }

    public static boolean hasNormalModeAlgorithms(List<String> list) {
        return list.contains("cbc(aes)") && (list.contains("hmac(sha256)") || list.contains("hmac(sha384)") || list.contains("hmac(sha512)"));
    }

    public String getServerAddr() {
        IkeTunnelConnectionParams ikeTunnelConnectionParams = this.mIkeTunConnParams;
        return ikeTunnelConnectionParams == null ? this.mServerAddr : ikeTunnelConnectionParams.getIkeSessionParams().getServerHostname();
    }

    public String getUserIdentity() {
        IkeTunnelConnectionParams ikeTunnelConnectionParams = this.mIkeTunConnParams;
        return ikeTunnelConnectionParams == null ? this.mUserIdentity : getUserIdentityFromIkeSession(ikeTunnelConnectionParams.getIkeSessionParams());
    }

    public byte[] getPresharedKey() {
        byte[] bArr;
        if (this.mIkeTunConnParams == null && (bArr = this.mPresharedKey) != null) {
            return Arrays.copyOf(bArr, bArr.length);
        }
        return null;
    }

    public X509Certificate getServerRootCaCert() {
        if (this.mIkeTunConnParams != null) {
            return null;
        }
        return this.mServerRootCaCert;
    }

    public String getUsername() {
        if (this.mIkeTunConnParams != null) {
            return null;
        }
        return this.mUsername;
    }

    public String getPassword() {
        if (this.mIkeTunConnParams != null) {
            return null;
        }
        return this.mPassword;
    }

    public PrivateKey getRsaPrivateKey() {
        if (this.mIkeTunConnParams != null) {
            return null;
        }
        return this.mRsaPrivateKey;
    }

    public X509Certificate getUserCert() {
        if (this.mIkeTunConnParams != null) {
            return null;
        }
        return this.mUserCert;
    }

    public ProxyInfo getProxyInfo() {
        return this.mProxyInfo;
    }

    public List<String> getAllowedAlgorithms() {
        return this.mIkeTunConnParams != null ? new ArrayList() : this.mAllowedAlgorithms;
    }

    public boolean isBypassable() {
        return this.mIsBypassable;
    }

    public boolean isMetered() {
        return this.mIsMetered;
    }

    public int getMaxMtu() {
        return this.mMaxMtu;
    }

    public IkeTunnelConnectionParams getIkeTunnelConnectionParams() {
        return this.mIkeTunConnParams;
    }

    public boolean isRestrictedToTestNetworks() {
        return this.mIsRestrictedToTestNetworks;
    }

    public boolean isAutomaticNattKeepaliveTimerEnabled() {
        return this.mAutomaticNattKeepaliveTimerEnabled;
    }

    public boolean isAutomaticIpVersionSelectionEnabled() {
        return this.mAutomaticIpVersionSelectionEnabled;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mType), this.mServerAddr, this.mUserIdentity, Integer.valueOf(Arrays.hashCode(this.mPresharedKey)), this.mServerRootCaCert, this.mUsername, this.mPassword, this.mRsaPrivateKey, this.mUserCert, this.mProxyInfo, this.mAllowedAlgorithms, Boolean.valueOf(this.mIsBypassable), Boolean.valueOf(this.mIsMetered), Integer.valueOf(this.mMaxMtu), Boolean.valueOf(this.mIsRestrictedToTestNetworks), Boolean.valueOf(this.mExcludeLocalRoutes), Boolean.valueOf(this.mRequiresInternetValidation), this.mIkeTunConnParams, Boolean.valueOf(this.mAutomaticNattKeepaliveTimerEnabled), Boolean.valueOf(this.mAutomaticIpVersionSelectionEnabled));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Ikev2VpnProfile)) {
            return false;
        }
        Ikev2VpnProfile ikev2VpnProfile = (Ikev2VpnProfile) obj;
        return this.mType == ikev2VpnProfile.mType && Objects.equals(this.mServerAddr, ikev2VpnProfile.mServerAddr) && Objects.equals(this.mUserIdentity, ikev2VpnProfile.mUserIdentity) && Arrays.equals(this.mPresharedKey, ikev2VpnProfile.mPresharedKey) && Objects.equals(this.mServerRootCaCert, ikev2VpnProfile.mServerRootCaCert) && Objects.equals(this.mUsername, ikev2VpnProfile.mUsername) && Objects.equals(this.mPassword, ikev2VpnProfile.mPassword) && Objects.equals(this.mRsaPrivateKey, ikev2VpnProfile.mRsaPrivateKey) && Objects.equals(this.mUserCert, ikev2VpnProfile.mUserCert) && Objects.equals(this.mProxyInfo, ikev2VpnProfile.mProxyInfo) && Objects.equals(this.mAllowedAlgorithms, ikev2VpnProfile.mAllowedAlgorithms) && this.mIsBypassable == ikev2VpnProfile.mIsBypassable && this.mIsMetered == ikev2VpnProfile.mIsMetered && this.mMaxMtu == ikev2VpnProfile.mMaxMtu && this.mIsRestrictedToTestNetworks == ikev2VpnProfile.mIsRestrictedToTestNetworks && this.mExcludeLocalRoutes == ikev2VpnProfile.mExcludeLocalRoutes && this.mRequiresInternetValidation == ikev2VpnProfile.mRequiresInternetValidation && Objects.equals(this.mIkeTunConnParams, ikev2VpnProfile.mIkeTunConnParams) && this.mAutomaticNattKeepaliveTimerEnabled == ikev2VpnProfile.mAutomaticNattKeepaliveTimerEnabled && this.mAutomaticIpVersionSelectionEnabled == ikev2VpnProfile.mAutomaticIpVersionSelectionEnabled;
    }

    @Override // android.net.PlatformVpnProfile
    public VpnProfile toVpnProfile() throws IOException, GeneralSecurityException {
        VpnProfile vpnProfile = new VpnProfile("", this.mIsRestrictedToTestNetworks, this.mExcludeLocalRoutes, this.mRequiresInternetValidation, this.mIkeTunConnParams, this.mAutomaticNattKeepaliveTimerEnabled, this.mAutomaticIpVersionSelectionEnabled);
        vpnProfile.proxy = this.mProxyInfo;
        vpnProfile.isBypassable = this.mIsBypassable;
        vpnProfile.isMetered = this.mIsMetered;
        vpnProfile.maxMtu = this.mMaxMtu;
        vpnProfile.areAuthParamsInline = true;
        vpnProfile.saveLogin = true;
        if (this.mIkeTunConnParams != null) {
            vpnProfile.type = 10;
            return vpnProfile;
        }
        vpnProfile.type = this.mType;
        vpnProfile.server = getServerAddr();
        vpnProfile.ipsecIdentifier = getUserIdentity();
        vpnProfile.setAllowedAlgorithms(this.mAllowedAlgorithms);
        int i = this.mType;
        if (i == 6) {
            vpnProfile.username = this.mUsername;
            vpnProfile.password = this.mPassword;
            X509Certificate x509Certificate = this.mServerRootCaCert;
            vpnProfile.ipsecCaCert = x509Certificate != null ? certificateToPemString(x509Certificate) : "";
            return vpnProfile;
        }
        if (i == 7) {
            vpnProfile.ipsecSecret = encodeForIpsecSecret(this.mPresharedKey);
            return vpnProfile;
        }
        if (i == 8) {
            vpnProfile.ipsecUserCert = certificateToPemString(this.mUserCert);
            vpnProfile.ipsecSecret = PREFIX_INLINE + encodeForIpsecSecret(this.mRsaPrivateKey.getEncoded());
            X509Certificate x509Certificate2 = this.mServerRootCaCert;
            vpnProfile.ipsecCaCert = x509Certificate2 != null ? certificateToPemString(x509Certificate2) : "";
            return vpnProfile;
        }
        throw new IllegalArgumentException("Invalid auth method set");
    }

    private static PrivateKey getPrivateKeyFromAndroidKeystore(String str) {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            Key key = keyStore.getKey(str, null);
            if (!(key instanceof PrivateKey)) {
                throw new IllegalStateException("Unexpected key type returned from android keystore.");
            }
            return (PrivateKey) key;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load key from android keystore.", e);
        }
    }

    public static Ikev2VpnProfile fromVpnProfile(VpnProfile vpnProfile) throws GeneralSecurityException {
        Builder builder;
        PrivateKey privateKey;
        if (vpnProfile.ikeTunConnParams == null) {
            builder = new Builder(vpnProfile.server, vpnProfile.ipsecIdentifier);
            builder.setAllowedAlgorithms(vpnProfile.getAllowedAlgorithms());
            int i = vpnProfile.type;
            if (i == 6) {
                builder.setAuthUsernamePassword(vpnProfile.username, vpnProfile.password, certificateFromPemString(vpnProfile.ipsecCaCert));
            } else if (i == 7) {
                builder.setAuthPsk(decodeFromIpsecSecret(vpnProfile.ipsecSecret));
            } else if (i == 8) {
                if (vpnProfile.ipsecSecret.startsWith(PREFIX_KEYSTORE_ALIAS)) {
                    privateKey = getPrivateKeyFromAndroidKeystore(vpnProfile.ipsecSecret.substring(15));
                } else if (vpnProfile.ipsecSecret.startsWith(PREFIX_INLINE)) {
                    privateKey = getPrivateKey(vpnProfile.ipsecSecret.substring(7));
                } else {
                    throw new IllegalArgumentException("Invalid RSA private key prefix");
                }
                builder.setAuthDigitalSignature(certificateFromPemString(vpnProfile.ipsecUserCert), privateKey, certificateFromPemString(vpnProfile.ipsecCaCert));
            } else {
                throw new IllegalArgumentException("Invalid auth method set");
            }
        } else {
            builder = new Builder(vpnProfile.ikeTunConnParams);
        }
        builder.setProxy(vpnProfile.proxy);
        builder.setBypassable(vpnProfile.isBypassable);
        builder.setMetered(vpnProfile.isMetered);
        builder.setMaxMtu(vpnProfile.maxMtu);
        if (vpnProfile.isRestrictedToTestNetworks) {
            builder.restrictToTestNetworks();
        }
        if (vpnProfile.excludeLocalRoutes && !vpnProfile.isBypassable) {
            Log.w(TAG, "ExcludeLocalRoutes should only be set in the bypassable VPN");
        }
        builder.setLocalRoutesExcluded(vpnProfile.excludeLocalRoutes && vpnProfile.isBypassable);
        builder.setRequiresInternetValidation(vpnProfile.requiresInternetValidation);
        builder.setAutomaticNattKeepaliveTimerEnabled(vpnProfile.automaticNattKeepaliveTimerEnabled);
        builder.setAutomaticIpVersionSelectionEnabled(vpnProfile.automaticIpVersionSelectionEnabled);
        return builder.build();
    }

    public static boolean isValidVpnProfile(VpnProfile vpnProfile) {
        if (vpnProfile.server.isEmpty()) {
            return false;
        }
        switch (vpnProfile.type) {
            case 6:
                if (vpnProfile.username.isEmpty() || vpnProfile.password.isEmpty()) {
                }
                break;
            case 7:
                if (vpnProfile.ipsecSecret.isEmpty()) {
                }
                break;
            case 8:
            case 9:
                if (vpnProfile.ipsecSecret.isEmpty() || vpnProfile.ipsecUserCert.isEmpty()) {
                }
                break;
        }
        return false;
    }

    public static String certificateToPemString(X509Certificate x509Certificate) throws IOException, CertificateEncodingException {
        if (x509Certificate == null) {
            return "";
        }
        return new String(android.security.Credentials.convertToPem(x509Certificate), StandardCharsets.US_ASCII);
    }

    private static X509Certificate certificateFromPemString(String str) throws CertificateException {
        if (str == null || "".equals(str)) {
            return null;
        }
        try {
            List<X509Certificate> convertFromPem = android.security.Credentials.convertFromPem(str.getBytes(StandardCharsets.US_ASCII));
            if (convertFromPem.isEmpty()) {
                return null;
            }
            return convertFromPem.get(0);
        } catch (IOException e) {
            throw new CertificateException(e);
        }
    }

    public static String encodeForIpsecSecret(byte[] bArr) {
        checkNotNull(bArr, MISSING_PARAM_MSG_TMPL, "secret");
        return Base64.getEncoder().encodeToString(bArr);
    }

    private static byte[] decodeFromIpsecSecret(String str) {
        checkNotNull(str, MISSING_PARAM_MSG_TMPL, "encoded");
        return Base64.getDecoder().decode(str);
    }

    private static PrivateKey getPrivateKey(String str) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decodeFromIpsecSecret(str)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkCert(X509Certificate x509Certificate) {
        try {
            certificateToPemString(x509Certificate);
        } catch (IOException | GeneralSecurityException unused) {
            throw new IllegalArgumentException("Certificate could not be encoded");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> T checkNotNull(T t, String str, Object... objArr) {
        return (T) Objects.requireNonNull(t, String.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkBuilderSetter(boolean z, String str) {
        if (z) {
            throw new IllegalArgumentException(str + " can't be set with IkeTunnelConnectionParams builder");
        }
    }

    private static String getUserIdentityFromIkeSession(IkeSessionParams ikeSessionParams) {
        IkeIdentification localIdentification = ikeSessionParams.getLocalIdentification();
        if (localIdentification instanceof IkeKeyIdIdentification) {
            return "@#".concat(new String(((IkeKeyIdIdentification) localIdentification).keyId));
        }
        if (localIdentification instanceof IkeRfc822AddrIdentification) {
            return "@@" + ((IkeRfc822AddrIdentification) localIdentification).rfc822Name;
        }
        if (localIdentification instanceof IkeFqdnIdentification) {
            return "@" + ((IkeFqdnIdentification) localIdentification).fqdn;
        }
        if (localIdentification instanceof IkeIpv4AddrIdentification) {
            return ((IkeIpv4AddrIdentification) localIdentification).ipv4Address.getHostAddress();
        }
        if (localIdentification instanceof IkeIpv6AddrIdentification) {
            return ((IkeIpv6AddrIdentification) localIdentification).ipv6Address.getHostAddress();
        }
        if (localIdentification instanceof IkeDerAsn1DnIdentification) {
            throw new IllegalArgumentException("Unspported ASN.1 encoded identities");
        }
        throw new IllegalArgumentException("Unknown IkeIdentification to get user identity");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("IkeV2VpnProfile [");
        sb.append(" MaxMtu=" + this.mMaxMtu);
        if (this.mIsBypassable) {
            sb.append(" Bypassable");
        }
        if (this.mRequiresInternetValidation) {
            sb.append(" RequiresInternetValidation");
        }
        if (this.mIsRestrictedToTestNetworks) {
            sb.append(" RestrictedToTestNetworks");
        }
        if (this.mAutomaticNattKeepaliveTimerEnabled) {
            sb.append(" AutomaticNattKeepaliveTimerEnabled");
        }
        if (this.mAutomaticIpVersionSelectionEnabled) {
            sb.append(" AutomaticIpVersionSelectionEnabled");
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    public static final class Builder {
        private final IkeTunnelConnectionParams mIkeTunConnParams;
        private String mPassword;
        private byte[] mPresharedKey;
        private ProxyInfo mProxyInfo;
        private PrivateKey mRsaPrivateKey;
        private final String mServerAddr;
        private X509Certificate mServerRootCaCert;
        private X509Certificate mUserCert;
        private final String mUserIdentity;
        private String mUsername;
        private int mType = -1;
        private List<String> mAllowedAlgorithms = Ikev2VpnProfile.DEFAULT_ALGORITHMS;
        private boolean mRequiresInternetValidation = false;
        private boolean mIsBypassable = false;
        private boolean mIsMetered = true;
        private int mMaxMtu = 1360;
        private boolean mIsRestrictedToTestNetworks = false;
        private boolean mExcludeLocalRoutes = false;
        private boolean mAutomaticNattKeepaliveTimerEnabled = false;
        private boolean mAutomaticIpVersionSelectionEnabled = false;

        public Builder(String str, String str2) {
            Ikev2VpnProfile.checkNotNull(str, Ikev2VpnProfile.MISSING_PARAM_MSG_TMPL, "serverAddr");
            Ikev2VpnProfile.checkNotNull(str2, Ikev2VpnProfile.MISSING_PARAM_MSG_TMPL, "identity");
            this.mServerAddr = str;
            this.mUserIdentity = str2;
            this.mIkeTunConnParams = null;
        }

        public Builder(IkeTunnelConnectionParams ikeTunnelConnectionParams) {
            Ikev2VpnProfile.checkNotNull(ikeTunnelConnectionParams, Ikev2VpnProfile.MISSING_PARAM_MSG_TMPL, "ikeTunConnParams");
            this.mIkeTunConnParams = ikeTunnelConnectionParams;
            this.mServerAddr = null;
            this.mUserIdentity = null;
        }

        private void resetAuthParams() {
            this.mPresharedKey = null;
            this.mServerRootCaCert = null;
            this.mUsername = null;
            this.mPassword = null;
            this.mRsaPrivateKey = null;
            this.mUserCert = null;
        }

        public Builder setAuthUsernamePassword(String str, String str2, X509Certificate x509Certificate) {
            Ikev2VpnProfile.checkNotNull(str, Ikev2VpnProfile.MISSING_PARAM_MSG_TMPL, "user");
            Ikev2VpnProfile.checkNotNull(str2, Ikev2VpnProfile.MISSING_PARAM_MSG_TMPL, "pass");
            Ikev2VpnProfile.checkBuilderSetter(this.mIkeTunConnParams != null, "authUsernamePassword");
            if (x509Certificate != null) {
                Ikev2VpnProfile.checkCert(x509Certificate);
            }
            resetAuthParams();
            this.mUsername = str;
            this.mPassword = str2;
            this.mServerRootCaCert = x509Certificate;
            this.mType = 6;
            return this;
        }

        public Builder setAuthDigitalSignature(X509Certificate x509Certificate, PrivateKey privateKey, X509Certificate x509Certificate2) {
            Ikev2VpnProfile.checkNotNull(x509Certificate, Ikev2VpnProfile.MISSING_PARAM_MSG_TMPL, "userCert");
            Ikev2VpnProfile.checkNotNull(privateKey, Ikev2VpnProfile.MISSING_PARAM_MSG_TMPL, "key");
            Ikev2VpnProfile.checkBuilderSetter(this.mIkeTunConnParams != null, "authDigitalSignature");
            Ikev2VpnProfile.checkCert(x509Certificate);
            if (x509Certificate2 != null) {
                Ikev2VpnProfile.checkCert(x509Certificate2);
            }
            resetAuthParams();
            this.mUserCert = x509Certificate;
            this.mRsaPrivateKey = privateKey;
            this.mServerRootCaCert = x509Certificate2;
            this.mType = 8;
            return this;
        }

        public Builder setAuthPsk(byte[] bArr) {
            Ikev2VpnProfile.checkNotNull(bArr, Ikev2VpnProfile.MISSING_PARAM_MSG_TMPL, "psk");
            Ikev2VpnProfile.checkBuilderSetter(this.mIkeTunConnParams != null, "authPsk");
            resetAuthParams();
            this.mPresharedKey = bArr;
            this.mType = 7;
            return this;
        }

        public Builder setBypassable(boolean z) {
            this.mIsBypassable = z;
            return this;
        }

        public Builder setProxy(ProxyInfo proxyInfo) {
            this.mProxyInfo = proxyInfo;
            return this;
        }

        public Builder setMaxMtu(int i) {
            if (i < 1280) {
                throw new IllegalArgumentException("Max MTU must be at least 1280");
            }
            this.mMaxMtu = i;
            return this;
        }

        public Builder setRequiresInternetValidation(boolean z) {
            this.mRequiresInternetValidation = z;
            return this;
        }

        public Builder setMetered(boolean z) {
            this.mIsMetered = z;
            return this;
        }

        public Builder setAllowedAlgorithms(List<String> list) {
            Ikev2VpnProfile.checkNotNull(list, Ikev2VpnProfile.MISSING_PARAM_MSG_TMPL, "algorithmNames");
            Ikev2VpnProfile.checkBuilderSetter(this.mIkeTunConnParams != null, "algorithmNames");
            Ikev2VpnProfile.validateAllowedAlgorithms(list);
            this.mAllowedAlgorithms = list;
            return this;
        }

        public Builder restrictToTestNetworks() {
            this.mIsRestrictedToTestNetworks = true;
            return this;
        }

        public Builder setAutomaticNattKeepaliveTimerEnabled(boolean z) {
            this.mAutomaticNattKeepaliveTimerEnabled = z;
            return this;
        }

        public Builder setAutomaticIpVersionSelectionEnabled(boolean z) {
            this.mAutomaticIpVersionSelectionEnabled = z;
            return this;
        }

        public Builder setLocalRoutesExcluded(boolean z) {
            this.mExcludeLocalRoutes = z;
            return this;
        }

        public Ikev2VpnProfile build() {
            return new Ikev2VpnProfile(this.mType, this.mServerAddr, this.mUserIdentity, this.mPresharedKey, this.mServerRootCaCert, this.mUsername, this.mPassword, this.mRsaPrivateKey, this.mUserCert, this.mProxyInfo, this.mAllowedAlgorithms, this.mIsBypassable, this.mIsMetered, this.mMaxMtu, this.mIsRestrictedToTestNetworks, this.mExcludeLocalRoutes, this.mRequiresInternetValidation, this.mIkeTunConnParams, this.mAutomaticNattKeepaliveTimerEnabled, this.mAutomaticIpVersionSelectionEnabled);
        }
    }
}
