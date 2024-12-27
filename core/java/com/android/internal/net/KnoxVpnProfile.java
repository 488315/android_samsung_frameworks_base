package com.android.internal.net;

import android.net.ProxyInfo;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.security.keystore2.AndroidKeyStoreProvider;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.android.net.module.util.ProxyUtils;

import java.net.InetAddress;
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

/* loaded from: classes5.dex */
public final class KnoxVpnProfile implements Parcelable {
    private static final String ANDROID_BC_PROVIDER = "AndroidKeyStoreBCWorkaroundProvider";
    public static final Parcelable.Creator<KnoxVpnProfile> CREATOR =
            new Parcelable.Creator<
                    KnoxVpnProfile>() { // from class: com.android.internal.net.KnoxVpnProfile.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public KnoxVpnProfile createFromParcel(Parcel in) {
                    return new KnoxVpnProfile(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public KnoxVpnProfile[] newArray(int size) {
                    return new KnoxVpnProfile[size];
                }
            };
    private static final String ENCODED_NULL_PROXY_INFO = "\u0000\u0000\u0000\u0000";
    static final String LIST_DELIMITER = ",";
    public static final int PROXY_MANUAL = 1;
    public static final int PROXY_NONE = 0;
    private static final String TAG = "KnoxVpnProfile";
    public static final int TYPE_IKEV2_IPSEC_PSK = 7;
    public static final int TYPE_IKEV2_IPSEC_RSA = 8;
    public static final int TYPE_IKEV2_IPSEC_USER_PASS = 6;
    public static final int TYPE_IPSEC_HYBRID_RSA = 5;
    public static final int TYPE_IPSEC_XAUTH_PSK = 3;
    public static final int TYPE_IPSEC_XAUTH_RSA = 4;
    public static final int TYPE_L2TP_IPSEC_PSK = 1;
    public static final int TYPE_L2TP_IPSEC_RSA = 2;
    public static final int TYPE_MAX = 8;
    public static final int TYPE_PPTP = 0;
    static final String VALUE_DELIMITER = "\u0000";
    private static final String VPN_KEYPAIR_PROVIDER = "AndroidKeyStore";
    private static final String VPN_SECRET_KEY = "VpnSecretKey";
    public boolean areAuthParamsInline;
    public String dnsServers;
    public String ipSecCACertValue;
    public String ipsecCaCert;
    public String ipsecIdentifier;
    public String ipsecSecret;
    public String ipsecServerCert;
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
    public String routes;
    public transient boolean saveLogin;
    public String searchDomains;
    public String server;
    public int type;
    public String username;

    public KnoxVpnProfile(String key) {
        this(key, false);
    }

    public KnoxVpnProfile(String key, boolean isRestrictedToTestNetworks) {
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
        this.ipSecCACertValue = "";
        this.saveLogin = false;
        this.key = key;
        this.isRestrictedToTestNetworks = isRestrictedToTestNetworks;
    }

    public KnoxVpnProfile(Parcel in) {
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
        this.ipSecCACertValue = "";
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
        this.proxy = (ProxyInfo) in.readParcelable(null);
        this.mAllowedAlgorithms = new ArrayList();
        in.readList(this.mAllowedAlgorithms, null);
        this.isBypassable = in.readBoolean();
        this.isMetered = in.readBoolean();
        this.ocspServerUrl = in.readString();
        this.isPFS = in.readInt() != 0;
        this.isPasswordIvParams = in.readString();
        this.isIpsecSecretIvParams = in.readString();
        this.maxMtu = in.readInt();
        this.areAuthParamsInline = in.readBoolean();
        this.isRestrictedToTestNetworks = in.readBoolean();
        this.ipSecCACertValue = in.readString();
    }

    public List<String> getAllowedAlgorithms() {
        return Collections.unmodifiableList(this.mAllowedAlgorithms);
    }

    public void setAllowedAlgorithms(List<String> allowedAlgorithms) {
        validateAllowedAlgorithms(allowedAlgorithms);
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
        parcel.writeString(this.ipSecCACertValue);
    }

    public static KnoxVpnProfile decode(String key, byte[] value) {
        boolean isRestrictedToTestNetworks;
        boolean z;
        if (key == null) {
            return null;
        }
        try {
            try {
                String[] values =
                        new String(value, StandardCharsets.UTF_8).split(VALUE_DELIMITER, -1);
                if ((values.length < 18 || values.length > 23)
                        && values.length != 28
                        && values.length != 29) {
                    return null;
                }
                if (values.length >= 29) {
                    isRestrictedToTestNetworks = Boolean.parseBoolean(values[28]);
                } else {
                    isRestrictedToTestNetworks = false;
                }
                KnoxVpnProfile profile = new KnoxVpnProfile(key, isRestrictedToTestNetworks);
                profile.name = values[0];
                profile.type = Integer.parseInt(values[1]);
                if (profile.type >= 0 && profile.type <= 8) {
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
                            values.length > 16 ? Boolean.valueOf(values[16]).booleanValue() : false;
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
                        profile.mAllowedAlgorithms = Arrays.asList(values[23].split(","));
                        profile.isBypassable = Boolean.parseBoolean(values[24]);
                        profile.isMetered = Boolean.parseBoolean(values[25]);
                        profile.maxMtu = Integer.parseInt(values[26]);
                        profile.areAuthParamsInline = Boolean.parseBoolean(values[27]);
                        profile.ipSecCACertValue = values.length > 28 ? values[28] : "";
                    }
                    if (profile.username.isEmpty() && profile.password.isEmpty()) {
                        z = false;
                        profile.saveLogin = z;
                        return profile;
                    }
                    z = true;
                    profile.saveLogin = z;
                    return profile;
                }
                return null;
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e2) {
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
            builder.append(VALUE_DELIMITER)
                    .append(
                            ProxyUtils.exclusionListAsString(this.proxy.getExclusionList()) != null
                                    ? ProxyUtils.exclusionListAsString(
                                            this.proxy.getExclusionList())
                                    : "");
            builder.append(VALUE_DELIMITER).append(this.proxy.getPacFileUrl().toString());
        } else {
            builder.append(ENCODED_NULL_PROXY_INFO);
        }
        builder.append(VALUE_DELIMITER).append(String.join(",", this.mAllowedAlgorithms));
        builder.append(VALUE_DELIMITER).append(this.isBypassable);
        builder.append(VALUE_DELIMITER).append(this.isMetered);
        builder.append(VALUE_DELIMITER).append(this.maxMtu);
        builder.append(VALUE_DELIMITER).append(this.areAuthParamsInline);
        builder.append(VALUE_DELIMITER).append(this.isRestrictedToTestNetworks);
        builder.append(VALUE_DELIMITER).append(this.ipSecCACertValue);
        return builder.toString().getBytes(StandardCharsets.UTF_8);
    }

    public static boolean isLegacyType(int type) {
        switch (type) {
            case 6:
            case 7:
            case 8:
                return false;
            default:
                return true;
        }
    }

    private boolean isValidLockdownLegacyVpnProfile() {
        return isLegacyType(this.type)
                && isServerAddressNumeric()
                && hasDns()
                && areDnsAddressesNumeric();
    }

    private boolean isValidLockdownPlatformVpnProfile() {
        return false;
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

    public static void validateAllowedAlgorithms(List<String> allowedAlgorithms) {
        for (String alg : allowedAlgorithms) {
            if (alg.contains(VALUE_DELIMITER) || alg.contains(",")) {
                throw new IllegalArgumentException(
                        "Algorithm contained illegal ('\u0000' or ',') character");
            }
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
                this.ipSecCACertValue);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof KnoxVpnProfile)) {
            return false;
        }
        KnoxVpnProfile other = (KnoxVpnProfile) obj;
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
                && Objects.equals(this.ipSecCACertValue, other.ipSecCACertValue)
                && this.isBypassable == other.isBypassable
                && this.isMetered == other.isMetered
                && this.maxMtu == other.maxMtu
                && this.areAuthParamsInline == other.areAuthParamsInline
                && this.isRestrictedToTestNetworks == other.isRestrictedToTestNetworks;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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

    private static void encrypt(KnoxVpnProfile profile) {
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

    public static void decrypt(KnoxVpnProfile profile) {
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
