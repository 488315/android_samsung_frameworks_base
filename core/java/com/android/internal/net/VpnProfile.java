package com.android.internal.net;

import android.net.Ikev2VpnProfile;
import android.net.ProxyInfo;
import android.net.Uri;
import android.net.ipsec.ike.IkeTunnelConnectionParams;
import android.net.vcn.persistablebundleutils.TunnelConnectionParamsUtils;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.security.keystore2.AndroidKeyStoreProvider;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.android.internal.util.HexDump;
import com.android.net.module.util.ProxyUtils;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

public final class VpnProfile implements Cloneable, Parcelable {
    private static final String ANDROID_BC_PROVIDER = "AndroidKeyStoreBCWorkaroundProvider";
    private static final String ENCODED_NULL_PROXY_INFO = "\u0000\u0000\u0000\u0000";
    static final String LIST_DELIMITER = ",";
    public static final int PROXY_MANUAL = 1;
    public static final int PROXY_NONE = 0;
    private static final String TAG = "VpnProfile";
    public static final int TYPE_IKEV2_FROM_IKE_TUN_CONN_PARAMS = 10;
    public static final int TYPE_IKEV2_IPSEC_EAP_TLS = 9;
    public static final int TYPE_IKEV2_IPSEC_PSK = 7;
    public static final int TYPE_IKEV2_IPSEC_RSA = 8;
    public static final int TYPE_IKEV2_IPSEC_USER_PASS = 6;
    public static final int TYPE_IPSEC_HYBRID_RSA = 5;
    public static final int TYPE_IPSEC_XAUTH_PSK = 3;
    public static final int TYPE_IPSEC_XAUTH_RSA = 4;
    public static final int TYPE_L2TP_IPSEC_PSK = 1;
    public static final int TYPE_L2TP_IPSEC_RSA = 2;
    public static final int TYPE_MAX = 10;
    public static final int TYPE_PPTP = 0;
    static final String VALUE_DELIMITER = "\u0000";
    private static final String VPN_KEYPAIR_PROVIDER = "AndroidKeyStore";
    private static final String VPN_SECRET_KEY = "VpnSecretKey";
    public String allCert;
    public boolean areAuthParamsInline;
    public final boolean automaticIpVersionSelectionEnabled;
    public final boolean automaticNattKeepaliveTimerEnabled;
    public String dnsServers;
    public final boolean excludeLocalRoutes;
    public final IkeTunnelConnectionParams ikeTunConnParams;
    public String ipsecCaCert;
    public String ipsecCacertValue;
    public String ipsecIdentifier;
    public String ipsecRemoteIdentifier;
    public String ipsecSecret;
    public String ipsecServerCert;
    public String ipsecServerCertValue;
    public String ipsecUserCert;
    public boolean isBypassable;
    public String isIpsecSecretIvParams;
    public boolean isMetered;
    public boolean isPFS;
    public String isPasswordIvParams;
    public final boolean isRestrictedToTestNetworks;
    public final String key;
    public String l2tpSecret;
    private List<String> mAllowedAlgorithms;
    public int maxMtu;
    public boolean mppe;
    public String name;
    public String ocspServerUrl;
    public String password;
    public ProxyInfo proxy;
    public final boolean requiresInternetValidation;
    public String routes;
    public transient boolean saveLogin;
    public String searchDomains;
    public String server;
    public int type;
    public String username;
    private static final String DEFAULT_ENCODING = StandardCharsets.UTF_8.name();
    public static final Parcelable.Creator<VpnProfile> CREATOR =
            new Parcelable.Creator<
                    VpnProfile>() { // from class: com.android.internal.net.VpnProfile.1
                @Override // android.os.Parcelable.Creator
                public VpnProfile createFromParcel(Parcel in) {
                    return new VpnProfile(in);
                }

                @Override // android.os.Parcelable.Creator
                public VpnProfile[] newArray(int size) {
                    return new VpnProfile[size];
                }
            };

    public VpnProfile(String key) {
        this(key, false, false, false, null);
    }

    public VpnProfile(String key, boolean isRestrictedToTestNetworks) {
        this(key, isRestrictedToTestNetworks, false, false, null);
    }

    public VpnProfile(
            String key,
            boolean isRestrictedToTestNetworks,
            boolean excludeLocalRoutes,
            boolean requiresInternetValidation,
            IkeTunnelConnectionParams ikeTunConnParams) {
        this(
                key,
                isRestrictedToTestNetworks,
                excludeLocalRoutes,
                requiresInternetValidation,
                ikeTunConnParams,
                false,
                false);
    }

    public VpnProfile(
            String key,
            boolean isRestrictedToTestNetworks,
            boolean excludeLocalRoutes,
            boolean requiresInternetValidation,
            IkeTunnelConnectionParams ikeTunConnParams,
            boolean automaticNattKeepaliveTimerEnabled,
            boolean automaticIpVersionSelectionEnabled) {
        this.name = "";
        this.type = 0;
        this.server = "";
        this.username = "";
        this.password = "";
        this.dnsServers = "";
        this.searchDomains = "";
        this.routes = "";
        this.mppe = true;
        this.l2tpSecret = "";
        this.ipsecIdentifier = "";
        this.ipsecSecret = "";
        this.ipsecUserCert = "";
        this.ipsecCaCert = "";
        this.ipsecServerCert = "";
        this.ocspServerUrl = "";
        this.isPFS = false;
        this.isPasswordIvParams = "";
        this.isIpsecSecretIvParams = "";
        this.proxy = null;
        this.mAllowedAlgorithms = new ArrayList();
        this.isBypassable = false;
        this.isMetered = false;
        this.maxMtu = 1360;
        this.areAuthParamsInline = false;
        this.ipsecRemoteIdentifier = "";
        this.ipsecCacertValue = "";
        this.ipsecServerCertValue = "";
        this.allCert = "";
        this.saveLogin = false;
        this.key = key;
        this.isRestrictedToTestNetworks = isRestrictedToTestNetworks;
        this.excludeLocalRoutes = excludeLocalRoutes;
        this.requiresInternetValidation = requiresInternetValidation;
        this.ikeTunConnParams = ikeTunConnParams;
        this.automaticNattKeepaliveTimerEnabled = automaticNattKeepaliveTimerEnabled;
        this.automaticIpVersionSelectionEnabled = automaticIpVersionSelectionEnabled;
    }

    public VpnProfile(Parcel in) {
        boolean z;
        boolean z2;
        this.name = "";
        this.type = 0;
        this.server = "";
        this.username = "";
        this.password = "";
        this.dnsServers = "";
        this.searchDomains = "";
        this.routes = "";
        this.mppe = true;
        this.l2tpSecret = "";
        this.ipsecIdentifier = "";
        this.ipsecSecret = "";
        this.ipsecUserCert = "";
        this.ipsecCaCert = "";
        this.ipsecServerCert = "";
        this.ocspServerUrl = "";
        this.isPFS = false;
        this.isPasswordIvParams = "";
        this.isIpsecSecretIvParams = "";
        this.proxy = null;
        this.mAllowedAlgorithms = new ArrayList();
        this.isBypassable = false;
        this.isMetered = false;
        this.maxMtu = 1360;
        this.areAuthParamsInline = false;
        this.ipsecRemoteIdentifier = "";
        this.ipsecCacertValue = "";
        this.ipsecServerCertValue = "";
        this.allCert = "";
        this.saveLogin = false;
        this.key = in.readString();
        this.name = in.readString();
        this.type = in.readInt();
        this.server = in.readString();
        this.username = in.readString();
        this.password = in.readString();
        this.dnsServers = in.readString();
        this.searchDomains = in.readString();
        this.routes = in.readString();
        if (in.readInt() == 0) {
            z = false;
        } else {
            z = true;
        }
        this.mppe = z;
        this.l2tpSecret = in.readString();
        this.ipsecIdentifier = in.readString();
        this.ipsecSecret = in.readString();
        this.ipsecUserCert = in.readString();
        this.ipsecCaCert = in.readString();
        this.ipsecServerCert = in.readString();
        if (in.readInt() == 0) {
            z2 = false;
        } else {
            z2 = true;
        }
        this.saveLogin = z2;
        this.proxy = (ProxyInfo) in.readParcelable(null, ProxyInfo.class);
        this.mAllowedAlgorithms = new ArrayList();
        in.readList(this.mAllowedAlgorithms, null, String.class);
        this.isBypassable = in.readBoolean();
        this.isMetered = in.readBoolean();
        this.ocspServerUrl = in.readString();
        this.isPFS = in.readInt() != 0;
        this.isPasswordIvParams = in.readString();
        this.isIpsecSecretIvParams = in.readString();
        this.maxMtu = in.readInt();
        this.areAuthParamsInline = in.readBoolean();
        this.isRestrictedToTestNetworks = in.readBoolean();
        this.excludeLocalRoutes = in.readBoolean();
        this.requiresInternetValidation = in.readBoolean();
        PersistableBundle bundle =
                (PersistableBundle)
                        in.readParcelable(
                                PersistableBundle.class.getClassLoader(), PersistableBundle.class);
        this.ikeTunConnParams =
                bundle != null ? TunnelConnectionParamsUtils.fromPersistableBundle(bundle) : null;
        this.automaticNattKeepaliveTimerEnabled = in.readBoolean();
        this.automaticIpVersionSelectionEnabled = in.readBoolean();
        this.ipsecRemoteIdentifier = in.readString();
        this.ipsecCacertValue = in.readString();
        this.ipsecServerCertValue = in.readString();
        this.allCert = in.readString();
    }

    public List<String> getAllowedAlgorithms() {
        return Collections.unmodifiableList(this.mAllowedAlgorithms);
    }

    public void setAllowedAlgorithms(List<String> allowedAlgorithms) {
        this.mAllowedAlgorithms = allowedAlgorithms;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.key);
        parcel.writeString(this.name);
        parcel.writeInt(this.type);
        parcel.writeString(this.server);
        parcel.writeString(this.username);
        parcel.writeString(this.password);
        parcel.writeString(this.dnsServers);
        parcel.writeString(this.searchDomains);
        parcel.writeString(this.routes);
        parcel.writeInt(this.mppe ? 1 : 0);
        parcel.writeString(this.l2tpSecret);
        parcel.writeString(this.ipsecIdentifier);
        parcel.writeString(this.ipsecSecret);
        parcel.writeString(this.ipsecUserCert);
        parcel.writeString(this.ipsecCaCert);
        parcel.writeString(this.ipsecServerCert);
        parcel.writeInt(this.saveLogin ? 1 : 0);
        parcel.writeParcelable(this.proxy, i);
        parcel.writeList(this.mAllowedAlgorithms);
        parcel.writeBoolean(this.isBypassable);
        parcel.writeBoolean(this.isMetered);
        parcel.writeString(this.ocspServerUrl);
        parcel.writeInt(this.isPFS ? 1 : 0);
        parcel.writeString(this.isPasswordIvParams);
        parcel.writeString(this.isIpsecSecretIvParams);
        parcel.writeInt(this.maxMtu);
        parcel.writeBoolean(this.areAuthParamsInline);
        parcel.writeBoolean(this.isRestrictedToTestNetworks);
        parcel.writeBoolean(this.excludeLocalRoutes);
        parcel.writeBoolean(this.requiresInternetValidation);
        parcel.writeParcelable(
                this.ikeTunConnParams == null
                        ? null
                        : TunnelConnectionParamsUtils.toPersistableBundle(this.ikeTunConnParams),
                i);
        parcel.writeBoolean(this.automaticNattKeepaliveTimerEnabled);
        parcel.writeBoolean(this.automaticIpVersionSelectionEnabled);
        parcel.writeString(this.ipsecRemoteIdentifier);
        parcel.writeString(this.ipsecCacertValue);
        parcel.writeString(this.ipsecServerCertValue);
        parcel.writeString(this.allCert);
    }

    public static VpnProfile decode(String key, byte[] value) {
        boolean isRestrictedToTestNetworks;
        boolean excludeLocalRoutes;
        boolean requiresInternetValidation;
        IkeTunnelConnectionParams tempIkeTunConnParams;
        boolean automaticNattKeepaliveTimerEnabled;
        boolean automaticIpVersionSelectionEnabled;
        boolean z;
        if (key == null) {
            return null;
        }
        try {
        } catch (Exception e) {
            e = e;
        }
        try {
            String[] values = new String(value, StandardCharsets.UTF_8).split(VALUE_DELIMITER, -1);
            if (values.length < 18) {
                return null;
            }
            if (values.length <= 23 || values.length >= 28) {
                if ((values.length <= 32 || values.length >= 34) && values.length <= 38) {
                    if (values.length >= 29) {
                        isRestrictedToTestNetworks = Boolean.parseBoolean(values[28]);
                    } else {
                        isRestrictedToTestNetworks = false;
                    }
                    if (values.length >= 30) {
                        excludeLocalRoutes = Boolean.parseBoolean(values[29]);
                    } else {
                        excludeLocalRoutes = false;
                    }
                    if (values.length >= 31) {
                        requiresInternetValidation = Boolean.parseBoolean(values[30]);
                    } else {
                        requiresInternetValidation = false;
                    }
                    if (values.length >= 32 && values[31].length() != 0) {
                        Parcel parcel = Parcel.obtain();
                        byte[] bytes = HexDump.hexStringToByteArray(values[31]);
                        parcel.unmarshall(bytes, 0, bytes.length);
                        parcel.setDataPosition(0);
                        PersistableBundle bundle =
                                (PersistableBundle)
                                        parcel.readValue(PersistableBundle.class.getClassLoader());
                        IkeTunnelConnectionParams tempIkeTunConnParams2 =
                                TunnelConnectionParamsUtils.fromPersistableBundle(bundle);
                        tempIkeTunConnParams = tempIkeTunConnParams2;
                    } else {
                        tempIkeTunConnParams = null;
                    }
                    if (values.length >= 34) {
                        boolean automaticNattKeepaliveTimerEnabled2 =
                                Boolean.parseBoolean(values[32]);
                        automaticNattKeepaliveTimerEnabled = automaticNattKeepaliveTimerEnabled2;
                        automaticIpVersionSelectionEnabled = Boolean.parseBoolean(values[33]);
                    } else {
                        automaticNattKeepaliveTimerEnabled = false;
                        automaticIpVersionSelectionEnabled = false;
                    }
                    VpnProfile profile =
                            new VpnProfile(
                                    key,
                                    isRestrictedToTestNetworks,
                                    excludeLocalRoutes,
                                    requiresInternetValidation,
                                    tempIkeTunConnParams,
                                    automaticNattKeepaliveTimerEnabled,
                                    automaticIpVersionSelectionEnabled);
                    profile.name = values[0];
                    profile.type = Integer.parseInt(values[1]);
                    if (profile.type >= 0 && profile.type <= 10) {
                        profile.server = values[2];
                        profile.username = values[3];
                        profile.password = values[4];
                        profile.dnsServers = values[5];
                        profile.searchDomains = values[6];
                        profile.routes = values[7];
                        profile.mppe = Boolean.parseBoolean(values[8]);
                        profile.l2tpSecret = values[9];
                        profile.ipsecIdentifier = values[10];
                        profile.ipsecSecret = values[11];
                        profile.ipsecUserCert = values[12];
                        profile.ipsecCaCert = values[13];
                        profile.ipsecServerCert = values.length > 14 ? values[14] : "";
                        profile.ocspServerUrl = values.length > 15 ? values[15] : "";
                        profile.isPFS =
                                values.length > 16
                                        ? Boolean.valueOf(values[16]).booleanValue()
                                        : false;
                        profile.isPasswordIvParams = values.length > 17 ? values[17] : "";
                        profile.isIpsecSecretIvParams = values.length > 18 ? values[18] : "";
                        if (values.length > 19) {
                            String host = values.length > 19 ? values[19] : "";
                            String port = values.length > 20 ? values[20] : "";
                            String exclList = values.length > 21 ? values[21] : "";
                            String pacFileUrl = values.length > 22 ? values[22] : "";
                            if (host.isEmpty() && port.isEmpty() && exclList.isEmpty()) {
                                if (!pacFileUrl.isEmpty()) {
                                    profile.proxy = ProxyInfo.buildPacProxy(Uri.parse(pacFileUrl));
                                }
                            }
                            profile.proxy =
                                    ProxyInfo.buildDirectProxy(
                                            host,
                                            port.isEmpty() ? 0 : Integer.parseInt(port),
                                            ProxyUtils.exclusionStringAsList(exclList));
                        }
                        if (values.length >= 28) {
                            profile.mAllowedAlgorithms = new ArrayList();
                            for (String algo : Arrays.asList(values[23].split(","))) {
                                profile.mAllowedAlgorithms.add(
                                        URLDecoder.decode(algo, DEFAULT_ENCODING));
                            }
                            profile.isBypassable = Boolean.parseBoolean(values[24]);
                            profile.isMetered = Boolean.parseBoolean(values[25]);
                            profile.maxMtu = Integer.parseInt(values[26]);
                            profile.areAuthParamsInline = Boolean.parseBoolean(values[27]);
                            profile.ipsecRemoteIdentifier = values.length > 34 ? values[34] : "";
                            profile.ipsecCacertValue = values.length > 35 ? values[35] : "";
                            profile.ipsecServerCertValue = values.length > 36 ? values[36] : "";
                            profile.allCert = values.length > 37 ? values[37] : "";
                        }
                        if (profile.username.isEmpty() && profile.password.isEmpty()) {
                            z = false;
                            profile.saveLogin = z;
                            if (profile.type == 3
                                    || !profile.ipsecUserCert.isEmpty()
                                    || profile.ipsecCaCert.isEmpty()) {
                                if (profile.type == 4 || profile.ipsecSecret.isEmpty()) {
                                    if (profile.type != 5 && !profile.ipsecUserCert.isEmpty()) {
                                        profile.type = 4;
                                    } else if (profile.type != 6
                                            && !profile.ipsecSecret.isEmpty()) {
                                        profile.type = 7;
                                    } else if (profile.type == 7
                                            && !profile.ipsecUserCert.isEmpty()) {
                                        profile.type = 8;
                                    }
                                } else {
                                    profile.type = 3;
                                }
                            } else {
                                profile.type = 5;
                            }
                            return profile;
                        }
                        z = true;
                        profile.saveLogin = z;
                        if (profile.type == 3) {}
                        if (profile.type == 4) {}
                        if (profile.type != 5) {}
                        if (profile.type != 6) {}
                        if (profile.type == 7) {
                            profile.type = 8;
                        }
                        return profile;
                    }
                    return null;
                }
                return null;
            }
            return null;
        } catch (Exception e2) {
            e = e2;
            Log.d(TAG, "Got exception in decode.", e);
            return null;
        }
    }

    public byte[] encode(boolean encryptRequired) {
        Log.i(TAG, "encode: encrypt=" + encryptRequired);
        if (encryptRequired) {
            encrypt(this);
        }
        return encode();
    }

    public byte[] encode() {
        String str;
        StringBuilder builder = new StringBuilder(this.name);
        builder.append(VALUE_DELIMITER).append(this.type);
        builder.append(VALUE_DELIMITER).append(this.server);
        builder.append(VALUE_DELIMITER).append(this.saveLogin ? this.username : "");
        builder.append(VALUE_DELIMITER).append(this.saveLogin ? this.password : "");
        builder.append(VALUE_DELIMITER).append(this.dnsServers);
        builder.append(VALUE_DELIMITER).append(this.searchDomains);
        builder.append(VALUE_DELIMITER).append(this.routes);
        builder.append(VALUE_DELIMITER).append(this.mppe);
        builder.append(VALUE_DELIMITER).append(this.l2tpSecret);
        builder.append(VALUE_DELIMITER).append(this.ipsecIdentifier);
        builder.append(VALUE_DELIMITER).append(this.ipsecSecret);
        builder.append(VALUE_DELIMITER).append(this.ipsecUserCert);
        builder.append(VALUE_DELIMITER).append(this.ipsecCaCert);
        builder.append(VALUE_DELIMITER).append(this.ipsecServerCert);
        builder.append(VALUE_DELIMITER).append(this.ocspServerUrl);
        builder.append(VALUE_DELIMITER).append(this.isPFS);
        builder.append(VALUE_DELIMITER).append(this.isPasswordIvParams);
        builder.append(VALUE_DELIMITER).append(this.isIpsecSecretIvParams);
        if (this.proxy != null) {
            builder.append(VALUE_DELIMITER)
                    .append(this.proxy.getHost() != null ? this.proxy.getHost() : "");
            builder.append(VALUE_DELIMITER).append(this.proxy.getPort());
            StringBuilder append = builder.append(VALUE_DELIMITER);
            if (ProxyUtils.exclusionListAsString(this.proxy.getExclusionList()) != null) {
                str = ProxyUtils.exclusionListAsString(this.proxy.getExclusionList());
            } else {
                str = "";
            }
            append.append(str);
            builder.append(VALUE_DELIMITER).append(this.proxy.getPacFileUrl().toString());
        } else {
            builder.append(ENCODED_NULL_PROXY_INFO);
        }
        List<String> encodedAlgoNames = new ArrayList<>();
        try {
            for (String algo : this.mAllowedAlgorithms) {
                encodedAlgoNames.add(URLEncoder.encode(algo, DEFAULT_ENCODING));
            }
            builder.append(VALUE_DELIMITER).append(String.join(",", encodedAlgoNames));
            builder.append(VALUE_DELIMITER).append(this.isBypassable);
            builder.append(VALUE_DELIMITER).append(this.isMetered);
            builder.append(VALUE_DELIMITER).append(this.maxMtu);
            builder.append(VALUE_DELIMITER).append(this.areAuthParamsInline);
            builder.append(VALUE_DELIMITER).append(this.isRestrictedToTestNetworks);
            builder.append(VALUE_DELIMITER).append(this.excludeLocalRoutes);
            builder.append(VALUE_DELIMITER).append(this.requiresInternetValidation);
            if (this.ikeTunConnParams != null) {
                PersistableBundle bundle =
                        TunnelConnectionParamsUtils.toPersistableBundle(this.ikeTunConnParams);
                Parcel parcel = Parcel.obtain();
                parcel.writeValue(bundle);
                byte[] bytes = parcel.marshall();
                builder.append(VALUE_DELIMITER).append(HexDump.toHexString(bytes));
            } else {
                builder.append(VALUE_DELIMITER).append("");
            }
            builder.append(VALUE_DELIMITER).append(this.automaticNattKeepaliveTimerEnabled);
            builder.append(VALUE_DELIMITER).append(this.automaticIpVersionSelectionEnabled);
            builder.append(VALUE_DELIMITER).append(this.ipsecRemoteIdentifier);
            builder.append(VALUE_DELIMITER).append(this.ipsecCacertValue);
            builder.append(VALUE_DELIMITER).append(this.ipsecServerCertValue);
            builder.append(VALUE_DELIMITER).append(this.allCert);
            return builder.toString().getBytes(StandardCharsets.UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Failed to encode algorithms.", e);
        }
    }

    public static boolean isLegacyType(int type) {
        switch (type) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return true;
            default:
                return false;
        }
    }

    private boolean isValidLockdownLegacyVpnProfile() {
        return isLegacyType(this.type)
                && isServerAddressNumeric()
                && hasDns()
                && areDnsAddressesNumeric();
    }

    private boolean isValidLockdownPlatformVpnProfile() {
        return Ikev2VpnProfile.isValidVpnProfile(this);
    }

    public boolean isValidLockdownProfile() {
        return isTypeValidForLockdown()
                && (isValidLockdownLegacyVpnProfile() || isValidLockdownPlatformVpnProfile());
    }

    public boolean isTypeValidForLockdown() {
        return this.type != 0;
    }

    public boolean isServerAddressNumeric() {
        try {
            InetAddress.parseNumericAddress(this.server);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean hasDns() {
        return !TextUtils.isEmpty(this.dnsServers);
    }

    public boolean areDnsAddressesNumeric() {
        try {
            for (String dnsServer : this.dnsServers.split(" +")) {
                InetAddress.parseNumericAddress(dnsServer);
            }
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(
                this.key,
                Integer.valueOf(this.type),
                this.server,
                this.username,
                this.password,
                this.dnsServers,
                this.searchDomains,
                this.routes,
                Boolean.valueOf(this.mppe),
                this.l2tpSecret,
                this.ipsecIdentifier,
                this.ipsecSecret,
                this.ipsecUserCert,
                this.ipsecCaCert,
                this.ipsecServerCert,
                this.proxy,
                this.mAllowedAlgorithms,
                Boolean.valueOf(this.isBypassable),
                Boolean.valueOf(this.isMetered),
                Integer.valueOf(this.maxMtu),
                Boolean.valueOf(this.areAuthParamsInline),
                Boolean.valueOf(this.isRestrictedToTestNetworks),
                Boolean.valueOf(this.excludeLocalRoutes),
                Boolean.valueOf(this.requiresInternetValidation),
                this.ikeTunConnParams,
                Boolean.valueOf(this.automaticNattKeepaliveTimerEnabled),
                Boolean.valueOf(this.automaticIpVersionSelectionEnabled),
                this.ipsecRemoteIdentifier,
                this.ipsecCacertValue,
                this.ipsecServerCertValue,
                this.allCert);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof VpnProfile)) {
            return false;
        }
        VpnProfile other = (VpnProfile) obj;
        return Objects.equals(this.key, other.key)
                && Objects.equals(this.name, other.name)
                && this.type == other.type
                && Objects.equals(this.server, other.server)
                && Objects.equals(this.username, other.username)
                && Objects.equals(this.password, other.password)
                && Objects.equals(this.dnsServers, other.dnsServers)
                && Objects.equals(this.searchDomains, other.searchDomains)
                && Objects.equals(this.routes, other.routes)
                && this.mppe == other.mppe
                && Objects.equals(this.l2tpSecret, other.l2tpSecret)
                && Objects.equals(this.ipsecIdentifier, other.ipsecIdentifier)
                && Objects.equals(this.ipsecSecret, other.ipsecSecret)
                && Objects.equals(this.ipsecUserCert, other.ipsecUserCert)
                && Objects.equals(this.ipsecCaCert, other.ipsecCaCert)
                && Objects.equals(this.ipsecServerCert, other.ipsecServerCert)
                && Objects.equals(this.proxy, other.proxy)
                && Objects.equals(this.mAllowedAlgorithms, other.mAllowedAlgorithms)
                && this.isBypassable == other.isBypassable
                && this.isMetered == other.isMetered
                && this.maxMtu == other.maxMtu
                && this.areAuthParamsInline == other.areAuthParamsInline
                && this.isRestrictedToTestNetworks == other.isRestrictedToTestNetworks
                && this.excludeLocalRoutes == other.excludeLocalRoutes
                && this.requiresInternetValidation == other.requiresInternetValidation
                && Objects.equals(this.ikeTunConnParams, other.ikeTunConnParams)
                && this.automaticNattKeepaliveTimerEnabled
                        == other.automaticNattKeepaliveTimerEnabled
                && this.automaticIpVersionSelectionEnabled
                        == other.automaticIpVersionSelectionEnabled
                && Objects.equals(this.ipsecRemoteIdentifier, other.ipsecRemoteIdentifier)
                && Objects.equals(this.ipsecCacertValue, other.ipsecCacertValue)
                && Objects.equals(this.ipsecServerCertValue, other.ipsecServerCertValue)
                && Objects.equals(this.allCert, other.allCert);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public VpnProfile m7741clone() {
        try {
            return (VpnProfile) super.clone();
        } catch (CloneNotSupportedException e) {
            Log.wtf(TAG, e);
            return null;
        }
    }

    private static String bytes2Hex(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuilder result = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            result.append("0123456789ABCDEF".charAt((bytes[i] >> 4) & 15))
                    .append("0123456789ABCDEF".charAt(bytes[i] & 15));
        }
        return result.toString();
    }

    private static byte[] hex2Bytes(String hex) {
        int len = hex.length() / 2;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = Integer.valueOf(hex.substring(i * 2, (i * 2) + 2), 16).byteValue();
        }
        return bytes;
    }

    private static String[] doEncrypt(Key secretKey, String plainValue) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(1, secretKey);
            byte[] plainBuffer = Base64.encode(plainValue.getBytes(StandardCharsets.UTF_8), 2);
            byte[] encryptedBuffer = cipher.doFinal(plainBuffer);
            String encryptedBase64 = Base64.encodeToString(encryptedBuffer, 2);
            IvParameterSpec ivParamSpec =
                    (IvParameterSpec)
                            cipher.getParameters().getParameterSpec(IvParameterSpec.class);
            byte[] ivBuffer = ivParamSpec.getIV();
            String ivHexStr = bytes2Hex(ivBuffer);
            String ivHexBase64 =
                    Base64.encodeToString(ivHexStr.getBytes(StandardCharsets.UTF_8), 2);
            return new String[] {encryptedBase64, ivHexBase64};
        } catch (NullPointerException
                | InvalidKeyException
                | NoSuchAlgorithmException
                | InvalidParameterSpecException
                | BadPaddingException
                | IllegalBlockSizeException
                | NoSuchPaddingException e) {
            Log.e(TAG, "Failed to encrypt: " + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    private static String doDecrypt(Key secretKey, String encryptedBase64, String ivHexBase64) {
        try {
            byte[] encryptedBuffer = Base64.decode(encryptedBase64, 2);
            String ivHexStr = new String(Base64.decode(ivHexBase64, 2), StandardCharsets.UTF_8);
            byte[] ivBuffer = hex2Bytes(ivHexStr);
            IvParameterSpec ivParamSpec = new IvParameterSpec(ivBuffer);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(2, secretKey, ivParamSpec);
            byte[] decryptedBuffer = cipher.doFinal(encryptedBuffer);
            return new String(Base64.decode(decryptedBuffer, 2), StandardCharsets.UTF_8).intern();
        } catch (IllegalArgumentException
                | NullPointerException
                | InvalidAlgorithmParameterException
                | InvalidKeyException
                | NoSuchAlgorithmException
                | BadPaddingException
                | IllegalBlockSizeException
                | NoSuchPaddingException e) {
            Log.e(TAG, "Failed to decrypt: " + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    private static Key getSecretKey(boolean generate) {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            if (generate && !keyStore.containsAlias(VPN_SECRET_KEY)) {
                try {
                    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore");
                    keyGenerator.init(
                            new KeyGenParameterSpec.Builder(VPN_SECRET_KEY, 3)
                                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                                    .build());
                    keyGenerator.generateKey();
                } catch (Exception e) {
                    Log.e(TAG, "Failed to create key: " + e.toString());
                    e.printStackTrace();
                }
            }
            Key secretKey = keyStore.getKey(VPN_SECRET_KEY, null);
            return secretKey;
        } catch (Exception e2) {
            Log.e(TAG, "Failed to get key: " + e2.toString());
            e2.printStackTrace();
            return null;
        }
    }

    private static void encrypt(VpnProfile profile) {
        Key secretKey;
        String[] ret;
        String[] ret2;
        if ((!profile.ipsecSecret.isEmpty() || !profile.password.isEmpty())
                && (secretKey = getSecretKey(true)) != null) {
            if (!profile.ipsecSecret.isEmpty()
                    && (ret2 = doEncrypt(secretKey, profile.ipsecSecret)) != null) {
                profile.ipsecSecret = ret2[0];
                profile.isIpsecSecretIvParams = ret2[1];
            }
            if (!profile.password.isEmpty()
                    && (ret = doEncrypt(secretKey, profile.password)) != null) {
                profile.password = ret[0];
                profile.isPasswordIvParams = ret[1];
            }
        }
    }

    public static void decrypt(VpnProfile profile) {
        String ret;
        String ret2;
        if (!profile.isIpsecSecretIvParams.isEmpty() || !profile.isPasswordIvParams.isEmpty()) {
            try {
                Key secretKey = getSecretKey(false);
                if (secretKey != null) {
                    boolean isSetBCProvider = false;
                    Provider expectedProvider = Security.getProvider(ANDROID_BC_PROVIDER);
                    if (expectedProvider == null) {
                        AndroidKeyStoreProvider.install();
                        isSetBCProvider = true;
                    }
                    if (!profile.ipsecSecret.isEmpty()
                            && (ret2 =
                                            doDecrypt(
                                                    secretKey,
                                                    profile.ipsecSecret,
                                                    profile.isIpsecSecretIvParams))
                                    != null) {
                        profile.ipsecSecret = ret2;
                    }
                    if (!profile.password.isEmpty()
                            && (ret =
                                            doDecrypt(
                                                    secretKey,
                                                    profile.password,
                                                    profile.isPasswordIvParams))
                                    != null) {
                        profile.password = ret;
                    }
                    if (isSetBCProvider) {
                        Security.removeProvider(ANDROID_BC_PROVIDER);
                        return;
                    }
                    return;
                }
                return;
            } catch (Exception e) {
                Log.e(TAG, "Error while decrypting profile: " + e);
                return;
            }
        }
        Log.i(TAG, "This profile was not encrypted:" + profile.name);
    }

    private static byte[] intToByteArray(int integer) {
        ByteBuffer buff = ByteBuffer.allocate(4);
        buff.putInt(integer);
        buff.order(ByteOrder.BIG_ENDIAN);
        return buff.array();
    }

    private static int byteArrayToInt(byte[] b) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (3 - i) * 8;
            value += (b[i] & 255) << shift;
        }
        return value;
    }
}
