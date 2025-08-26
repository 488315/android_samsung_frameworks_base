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
import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.CertificateException;
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
    public static final Parcelable.Creator<KnoxVpnProfile> CREATOR = new Parcelable.Creator<KnoxVpnProfile>() { // from class: com.android.internal.net.KnoxVpnProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KnoxVpnProfile createFromParcel(Parcel parcel) {
            return new KnoxVpnProfile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KnoxVpnProfile[] newArray(int i) {
            return new KnoxVpnProfile[i];
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

    public static boolean isLegacyType(int i) {
        return (i == 6 || i == 7 || i == 8) ? false : true;
    }

    private boolean isValidLockdownPlatformVpnProfile() {
        return false;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public KnoxVpnProfile(String str) {
        this(str, false);
    }

    public KnoxVpnProfile(String str, boolean z) {
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
        this.key = str;
        this.isRestrictedToTestNetworks = z;
    }

    public KnoxVpnProfile(Parcel parcel) throws ClassNotFoundException, IOException {
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
        this.key = parcel.readString();
        this.name = parcel.readString();
        this.type = parcel.readInt();
        this.server = parcel.readString();
        this.username = parcel.readString();
        this.password = parcel.readString();
        this.dnsServers = parcel.readString();
        this.searchDomains = parcel.readString();
        this.routes = parcel.readString();
        this.mppe = parcel.readInt() != 0;
        this.l2tpSecret = parcel.readString();
        this.ipsecIdentifier = parcel.readString();
        this.ipsecSecret = parcel.readString();
        this.ipsecUserCert = parcel.readString();
        this.ipsecCaCert = parcel.readString();
        this.ipsecServerCert = parcel.readString();
        this.saveLogin = parcel.readInt() != 0;
        this.proxy = (ProxyInfo) parcel.readParcelable(null);
        ArrayList arrayList = new ArrayList();
        this.mAllowedAlgorithms = arrayList;
        parcel.readList(arrayList, null);
        this.isBypassable = parcel.readBoolean();
        this.isMetered = parcel.readBoolean();
        this.ocspServerUrl = parcel.readString();
        this.isPFS = parcel.readInt() != 0;
        this.isPasswordIvParams = parcel.readString();
        this.isIpsecSecretIvParams = parcel.readString();
        this.maxMtu = parcel.readInt();
        this.areAuthParamsInline = parcel.readBoolean();
        this.isRestrictedToTestNetworks = parcel.readBoolean();
        this.ipSecCACertValue = parcel.readString();
    }

    public List<String> getAllowedAlgorithms() {
        return Collections.unmodifiableList(this.mAllowedAlgorithms);
    }

    public void setAllowedAlgorithms(List<String> list) {
        validateAllowedAlgorithms(list);
        this.mAllowedAlgorithms = list;
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

    public static KnoxVpnProfile decode(String str, byte[] bArr) throws NumberFormatException {
        String[] strArrSplit;
        if (str == null) {
            return null;
        }
        try {
            strArrSplit = new String(bArr, StandardCharsets.UTF_8).split(VALUE_DELIMITER, -1);
        } catch (Exception unused) {
        }
        if ((strArrSplit.length < 18 || strArrSplit.length > 23) && strArrSplit.length != 28 && strArrSplit.length != 29) {
            return null;
        }
        KnoxVpnProfile knoxVpnProfile = new KnoxVpnProfile(str, strArrSplit.length >= 29 ? Boolean.parseBoolean(strArrSplit[28]) : false);
        knoxVpnProfile.name = strArrSplit[0];
        int i = Integer.parseInt(strArrSplit[1]);
        knoxVpnProfile.type = i;
        if (i >= 0 && i <= 8) {
            knoxVpnProfile.server = strArrSplit[2];
            knoxVpnProfile.username = strArrSplit[3];
            knoxVpnProfile.password = strArrSplit[4];
            knoxVpnProfile.dnsServers = strArrSplit[5];
            knoxVpnProfile.searchDomains = strArrSplit[6];
            knoxVpnProfile.routes = strArrSplit[7];
            knoxVpnProfile.mppe = Boolean.parseBoolean(strArrSplit[8]);
            knoxVpnProfile.l2tpSecret = strArrSplit[9];
            knoxVpnProfile.ipsecIdentifier = strArrSplit[10];
            knoxVpnProfile.ipsecSecret = strArrSplit[11];
            knoxVpnProfile.ipsecUserCert = strArrSplit[12];
            knoxVpnProfile.ipsecCaCert = strArrSplit[13];
            knoxVpnProfile.ipsecServerCert = strArrSplit.length > 14 ? strArrSplit[14] : "";
            knoxVpnProfile.ocspServerUrl = strArrSplit.length > 15 ? strArrSplit[15] : "";
            knoxVpnProfile.isPFS = strArrSplit.length > 16 ? Boolean.valueOf(strArrSplit[16]).booleanValue() : false;
            knoxVpnProfile.isPasswordIvParams = strArrSplit.length > 17 ? strArrSplit[17] : "";
            knoxVpnProfile.isIpsecSecretIvParams = strArrSplit.length > 18 ? strArrSplit[18] : "";
            if (strArrSplit.length > 19) {
                String str2 = strArrSplit.length > 19 ? strArrSplit[19] : "";
                String str3 = strArrSplit.length > 20 ? strArrSplit[20] : "";
                String str4 = strArrSplit.length > 21 ? strArrSplit[21] : "";
                String str5 = strArrSplit.length > 22 ? strArrSplit[22] : "";
                if (!str2.isEmpty() || !str3.isEmpty() || !str4.isEmpty()) {
                    knoxVpnProfile.proxy = ProxyInfo.buildDirectProxy(str2, str3.isEmpty() ? 0 : Integer.parseInt(str3), ProxyUtils.exclusionStringAsList(str4));
                } else if (!str5.isEmpty()) {
                    knoxVpnProfile.proxy = ProxyInfo.buildPacProxy(Uri.parse(str5));
                }
            }
            if (strArrSplit.length >= 28) {
                knoxVpnProfile.mAllowedAlgorithms = Arrays.asList(strArrSplit[23].split(","));
                knoxVpnProfile.isBypassable = Boolean.parseBoolean(strArrSplit[24]);
                knoxVpnProfile.isMetered = Boolean.parseBoolean(strArrSplit[25]);
                knoxVpnProfile.maxMtu = Integer.parseInt(strArrSplit[26]);
                knoxVpnProfile.areAuthParamsInline = Boolean.parseBoolean(strArrSplit[27]);
                knoxVpnProfile.ipSecCACertValue = strArrSplit.length > 28 ? strArrSplit[28] : "";
            }
            knoxVpnProfile.saveLogin = (knoxVpnProfile.username.isEmpty() && knoxVpnProfile.password.isEmpty()) ? false : true;
            return knoxVpnProfile;
        }
        return null;
    }

    public byte[] encode(boolean z) {
        Log.i(TAG, "encode: encrypt=" + z);
        if (z) {
            encrypt(this);
        }
        return encode();
    }

    public byte[] encode() {
        StringBuilder sb = new StringBuilder(this.name);
        sb.append(VALUE_DELIMITER);
        sb.append(this.type);
        sb.append(VALUE_DELIMITER);
        sb.append(this.server);
        sb.append(VALUE_DELIMITER);
        sb.append(this.saveLogin ? this.username : "");
        sb.append(VALUE_DELIMITER);
        sb.append(this.saveLogin ? this.password : "");
        sb.append(VALUE_DELIMITER);
        sb.append(this.dnsServers);
        sb.append(VALUE_DELIMITER);
        sb.append(this.searchDomains);
        sb.append(VALUE_DELIMITER);
        sb.append(this.routes);
        sb.append(VALUE_DELIMITER);
        sb.append(this.mppe);
        sb.append(VALUE_DELIMITER);
        sb.append(this.l2tpSecret);
        sb.append(VALUE_DELIMITER);
        sb.append(this.ipsecIdentifier);
        sb.append(VALUE_DELIMITER);
        sb.append(this.ipsecSecret);
        sb.append(VALUE_DELIMITER);
        sb.append(this.ipsecUserCert);
        sb.append(VALUE_DELIMITER);
        sb.append(this.ipsecCaCert);
        sb.append(VALUE_DELIMITER);
        sb.append(this.ipsecServerCert);
        sb.append(VALUE_DELIMITER);
        sb.append(this.ocspServerUrl);
        sb.append(VALUE_DELIMITER);
        sb.append(this.isPFS);
        sb.append(VALUE_DELIMITER);
        sb.append(this.isPasswordIvParams);
        sb.append(VALUE_DELIMITER);
        sb.append(this.isIpsecSecretIvParams);
        if (this.proxy != null) {
            sb.append(VALUE_DELIMITER);
            sb.append(this.proxy.getHost() != null ? this.proxy.getHost() : "");
            sb.append(VALUE_DELIMITER);
            sb.append(this.proxy.getPort());
            sb.append(VALUE_DELIMITER);
            sb.append(ProxyUtils.exclusionListAsString(this.proxy.getExclusionList()) != null ? ProxyUtils.exclusionListAsString(this.proxy.getExclusionList()) : "");
            sb.append(VALUE_DELIMITER);
            sb.append(this.proxy.getPacFileUrl().toString());
        } else {
            sb.append(ENCODED_NULL_PROXY_INFO);
        }
        sb.append(VALUE_DELIMITER);
        sb.append(String.join(",", this.mAllowedAlgorithms));
        sb.append(VALUE_DELIMITER);
        sb.append(this.isBypassable);
        sb.append(VALUE_DELIMITER);
        sb.append(this.isMetered);
        sb.append(VALUE_DELIMITER);
        sb.append(this.maxMtu);
        sb.append(VALUE_DELIMITER);
        sb.append(this.areAuthParamsInline);
        sb.append(VALUE_DELIMITER);
        sb.append(this.isRestrictedToTestNetworks);
        sb.append(VALUE_DELIMITER);
        sb.append(this.ipSecCACertValue);
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    private boolean isValidLockdownLegacyVpnProfile() {
        return isLegacyType(this.type) && isServerAddressNumeric() && hasDns() && areDnsAddressesNumeric();
    }

    public boolean isValidLockdownProfile() {
        if (isTypeValidForLockdown()) {
            return isValidLockdownLegacyVpnProfile() || isValidLockdownPlatformVpnProfile();
        }
        return false;
    }

    public boolean isTypeValidForLockdown() {
        return this.type != 0;
    }

    public boolean isServerAddressNumeric() {
        try {
            InetAddress.parseNumericAddress(this.server);
            return true;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    public boolean hasDns() {
        return !TextUtils.isEmpty(this.dnsServers);
    }

    public boolean areDnsAddressesNumeric() {
        try {
            for (String str : this.dnsServers.split(" +")) {
                InetAddress.parseNumericAddress(str);
            }
            return true;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    public static void validateAllowedAlgorithms(List<String> list) {
        for (String str : list) {
            if (str.contains(VALUE_DELIMITER) || str.contains(",")) {
                throw new IllegalArgumentException("Algorithm contained illegal ('\u0000' or ',') character");
            }
        }
    }

    public int hashCode() {
        return Objects.hash(this.key, Integer.valueOf(this.type), this.server, this.username, this.password, this.dnsServers, this.searchDomains, this.routes, Boolean.valueOf(this.mppe), this.l2tpSecret, this.ipsecIdentifier, this.ipsecSecret, this.ipsecUserCert, this.ipsecCaCert, this.ipsecServerCert, this.proxy, this.mAllowedAlgorithms, Boolean.valueOf(this.isBypassable), Boolean.valueOf(this.isMetered), Integer.valueOf(this.maxMtu), Boolean.valueOf(this.areAuthParamsInline), Boolean.valueOf(this.isRestrictedToTestNetworks), this.ipSecCACertValue);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof KnoxVpnProfile)) {
            return false;
        }
        KnoxVpnProfile knoxVpnProfile = (KnoxVpnProfile) obj;
        return Objects.equals(this.key, knoxVpnProfile.key) && Objects.equals(this.name, knoxVpnProfile.name) && this.type == knoxVpnProfile.type && Objects.equals(this.server, knoxVpnProfile.server) && Objects.equals(this.username, knoxVpnProfile.username) && Objects.equals(this.password, knoxVpnProfile.password) && Objects.equals(this.dnsServers, knoxVpnProfile.dnsServers) && Objects.equals(this.searchDomains, knoxVpnProfile.searchDomains) && Objects.equals(this.routes, knoxVpnProfile.routes) && this.mppe == knoxVpnProfile.mppe && Objects.equals(this.l2tpSecret, knoxVpnProfile.l2tpSecret) && Objects.equals(this.ipsecIdentifier, knoxVpnProfile.ipsecIdentifier) && Objects.equals(this.ipsecSecret, knoxVpnProfile.ipsecSecret) && Objects.equals(this.ipsecUserCert, knoxVpnProfile.ipsecUserCert) && Objects.equals(this.ipsecCaCert, knoxVpnProfile.ipsecCaCert) && Objects.equals(this.ipsecServerCert, knoxVpnProfile.ipsecServerCert) && Objects.equals(this.proxy, knoxVpnProfile.proxy) && Objects.equals(this.mAllowedAlgorithms, knoxVpnProfile.mAllowedAlgorithms) && Objects.equals(this.ipSecCACertValue, knoxVpnProfile.ipSecCACertValue) && this.isBypassable == knoxVpnProfile.isBypassable && this.isMetered == knoxVpnProfile.isMetered && this.maxMtu == knoxVpnProfile.maxMtu && this.areAuthParamsInline == knoxVpnProfile.areAuthParamsInline && this.isRestrictedToTestNetworks == knoxVpnProfile.isRestrictedToTestNetworks;
    }

    private static String bytes2Hex(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            sb.append("0123456789ABCDEF".charAt((bArr[i] >> 4) & 15));
            sb.append("0123456789ABCDEF".charAt(bArr[i] & 15));
        }
        return sb.toString();
    }

    private static byte[] hex2Bytes(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = Integer.valueOf(str.substring(i2, i2 + 2), 16).byteValue();
        }
        return bArr;
    }

    private static String[] doEncrypt(Key key, String str) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(1, key);
            return new String[]{Base64.encodeToString(cipher.doFinal(Base64.encode(str.getBytes(StandardCharsets.UTF_8), 2)), 2), Base64.encodeToString(bytes2Hex(((IvParameterSpec) cipher.getParameters().getParameterSpec(IvParameterSpec.class)).getIV()).getBytes(StandardCharsets.UTF_8), 2)};
        } catch (NullPointerException | InvalidKeyException | NoSuchAlgorithmException | InvalidParameterSpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            Log.e(TAG, "Failed to encrypt: " + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    private static String doDecrypt(Key key, String str, String str2) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            byte[] bArrDecode = Base64.decode(str, 2);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(hex2Bytes(new String(Base64.decode(str2, 2), StandardCharsets.UTF_8)));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(2, key, ivParameterSpec);
            return new String(Base64.decode(cipher.doFinal(bArrDecode), 2), StandardCharsets.UTF_8).intern();
        } catch (IllegalArgumentException | NullPointerException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            Log.e(TAG, "Failed to decrypt: " + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    private static Key getSecretKey(boolean z) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException, NoSuchProviderException, InvalidAlgorithmParameterException {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            if (z && !keyStore.containsAlias(VPN_SECRET_KEY)) {
                try {
                    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore");
                    keyGenerator.init(new KeyGenParameterSpec.Builder(VPN_SECRET_KEY, 3).setBlockModes(KeyProperties.BLOCK_MODE_CBC).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build());
                    keyGenerator.generateKey();
                } catch (Exception e) {
                    Log.e(TAG, "Failed to create key: " + e.toString());
                    e.printStackTrace();
                }
            }
            return keyStore.getKey(VPN_SECRET_KEY, null);
        } catch (Exception e2) {
            Log.e(TAG, "Failed to get key: " + e2.toString());
            e2.printStackTrace();
            return null;
        }
    }

    private static void encrypt(KnoxVpnProfile knoxVpnProfile) {
        Key secretKey;
        String[] strArrDoEncrypt;
        String[] strArrDoEncrypt2;
        if ((knoxVpnProfile.ipsecSecret.isEmpty() && knoxVpnProfile.password.isEmpty()) || (secretKey = getSecretKey(true)) == null) {
            return;
        }
        if (!knoxVpnProfile.ipsecSecret.isEmpty() && (strArrDoEncrypt2 = doEncrypt(secretKey, knoxVpnProfile.ipsecSecret)) != null) {
            knoxVpnProfile.ipsecSecret = strArrDoEncrypt2[0];
            knoxVpnProfile.isIpsecSecretIvParams = strArrDoEncrypt2[1];
        }
        if (knoxVpnProfile.password.isEmpty() || (strArrDoEncrypt = doEncrypt(secretKey, knoxVpnProfile.password)) == null) {
            return;
        }
        knoxVpnProfile.password = strArrDoEncrypt[0];
        knoxVpnProfile.isPasswordIvParams = strArrDoEncrypt[1];
    }

    public static void decrypt(KnoxVpnProfile knoxVpnProfile) {
        String strDoDecrypt;
        String strDoDecrypt2;
        if (!knoxVpnProfile.isIpsecSecretIvParams.isEmpty() || !knoxVpnProfile.isPasswordIvParams.isEmpty()) {
            boolean z = false;
            try {
                Key secretKey = getSecretKey(false);
                if (secretKey != null) {
                    if (Security.getProvider(ANDROID_BC_PROVIDER) == null) {
                        AndroidKeyStoreProvider.install();
                        z = true;
                    }
                    if (!knoxVpnProfile.ipsecSecret.isEmpty() && (strDoDecrypt2 = doDecrypt(secretKey, knoxVpnProfile.ipsecSecret, knoxVpnProfile.isIpsecSecretIvParams)) != null) {
                        knoxVpnProfile.ipsecSecret = strDoDecrypt2;
                    }
                    if (!knoxVpnProfile.password.isEmpty() && (strDoDecrypt = doDecrypt(secretKey, knoxVpnProfile.password, knoxVpnProfile.isPasswordIvParams)) != null) {
                        knoxVpnProfile.password = strDoDecrypt;
                    }
                    if (z) {
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
        Log.i(TAG, "This profile was not encrypted:" + knoxVpnProfile.name);
    }

    private static byte[] intToByteArray(int i) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
        byteBufferAllocate.putInt(i);
        byteBufferAllocate.order(ByteOrder.BIG_ENDIAN);
        return byteBufferAllocate.array();
    }

    private static int byteArrayToInt(byte[] bArr) {
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            i += (bArr[i2] & 255) << ((3 - i2) * 8);
        }
        return i;
    }
}
