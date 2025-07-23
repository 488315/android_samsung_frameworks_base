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
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

/* loaded from: classes5.dex */
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
    public String ikeCipherSuites;
    public int ikeRekeyTime;
    public final IkeTunnelConnectionParams ikeTunConnParams;
    public String ipsecCaCert;
    public String ipsecCacertValue;
    public String ipsecCipherSuites;
    public String ipsecIdentifier;
    public int ipsecRekeyTime;
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
    public static final Parcelable.Creator<VpnProfile> CREATOR = new Parcelable.Creator<VpnProfile>() { // from class: com.android.internal.net.VpnProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VpnProfile createFromParcel(Parcel parcel) {
            return new VpnProfile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VpnProfile[] newArray(int i) {
            return new VpnProfile[i];
        }
    };

    public static boolean isLegacyType(int i) {
        return i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public VpnProfile(String str) {
        this(str, false, false, false, null);
    }

    public VpnProfile(String str, boolean z) {
        this(str, z, false, false, null);
    }

    public VpnProfile(String str, boolean z, boolean z2, boolean z3, IkeTunnelConnectionParams ikeTunnelConnectionParams) {
        this(str, z, z2, z3, ikeTunnelConnectionParams, false, false);
    }

    public VpnProfile(String str, boolean z, boolean z2, boolean z3, IkeTunnelConnectionParams ikeTunnelConnectionParams, boolean z4, boolean z5) {
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
        this.ikeCipherSuites = "";
        this.ipsecCipherSuites = "";
        this.ipsecRekeyTime = -1;
        this.ikeRekeyTime = -1;
        this.saveLogin = false;
        this.key = str;
        this.isRestrictedToTestNetworks = z;
        this.excludeLocalRoutes = z2;
        this.requiresInternetValidation = z3;
        this.ikeTunConnParams = ikeTunnelConnectionParams;
        this.automaticNattKeepaliveTimerEnabled = z4;
        this.automaticIpVersionSelectionEnabled = z5;
    }

    public VpnProfile(Parcel parcel) {
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
        this.ikeCipherSuites = "";
        this.ipsecCipherSuites = "";
        this.ipsecRekeyTime = -1;
        this.ikeRekeyTime = -1;
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
        this.proxy = (ProxyInfo) parcel.readParcelable(null, ProxyInfo.class);
        ArrayList arrayList = new ArrayList();
        this.mAllowedAlgorithms = arrayList;
        parcel.readList(arrayList, null, String.class);
        this.isBypassable = parcel.readBoolean();
        this.isMetered = parcel.readBoolean();
        this.ocspServerUrl = parcel.readString();
        this.isPFS = parcel.readInt() != 0;
        this.isPasswordIvParams = parcel.readString();
        this.isIpsecSecretIvParams = parcel.readString();
        this.maxMtu = parcel.readInt();
        this.areAuthParamsInline = parcel.readBoolean();
        this.isRestrictedToTestNetworks = parcel.readBoolean();
        this.excludeLocalRoutes = parcel.readBoolean();
        this.requiresInternetValidation = parcel.readBoolean();
        PersistableBundle persistableBundle = (PersistableBundle) parcel.readParcelable(PersistableBundle.class.getClassLoader(), PersistableBundle.class);
        this.ikeTunConnParams = persistableBundle != null ? TunnelConnectionParamsUtils.fromPersistableBundle(persistableBundle) : null;
        this.automaticNattKeepaliveTimerEnabled = parcel.readBoolean();
        this.automaticIpVersionSelectionEnabled = parcel.readBoolean();
        this.ipsecRemoteIdentifier = parcel.readString();
        this.ipsecCacertValue = parcel.readString();
        this.ipsecServerCertValue = parcel.readString();
        this.allCert = parcel.readString();
        this.ikeCipherSuites = parcel.readString();
        this.ipsecCipherSuites = parcel.readString();
        this.ipsecRekeyTime = parcel.readInt();
        this.ikeRekeyTime = parcel.readInt();
    }

    public List<String> getAllowedAlgorithms() {
        return Collections.unmodifiableList(this.mAllowedAlgorithms);
    }

    public void setAllowedAlgorithms(List<String> list) {
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
        parcel.writeBoolean(this.excludeLocalRoutes);
        parcel.writeBoolean(this.requiresInternetValidation);
        IkeTunnelConnectionParams ikeTunnelConnectionParams = this.ikeTunConnParams;
        parcel.writeParcelable(ikeTunnelConnectionParams == null ? null : TunnelConnectionParamsUtils.toPersistableBundle(ikeTunnelConnectionParams), i);
        parcel.writeBoolean(this.automaticNattKeepaliveTimerEnabled);
        parcel.writeBoolean(this.automaticIpVersionSelectionEnabled);
        parcel.writeString(this.ipsecRemoteIdentifier);
        parcel.writeString(this.ipsecCacertValue);
        parcel.writeString(this.ipsecServerCertValue);
        parcel.writeString(this.allCert);
        parcel.writeString(this.ikeCipherSuites);
        parcel.writeString(this.ipsecCipherSuites);
        parcel.writeInt(this.ipsecRekeyTime);
        parcel.writeInt(this.ikeRekeyTime);
    }

    public static VpnProfile decode(String str, byte[] bArr) {
        VpnProfile vpnProfile;
        IkeTunnelConnectionParams ikeTunnelConnectionParams;
        boolean z;
        boolean z2;
        char c;
        boolean z3;
        if (str == null) {
            return null;
        }
        try {
            String[] split = new String(bArr, StandardCharsets.UTF_8).split(VALUE_DELIMITER, -1);
            if (split.length >= 18 && ((split.length <= 23 || split.length >= 28) && ((split.length <= 32 || split.length >= 34) && ((split.length <= 38 || split.length >= 42) && split.length <= 42)))) {
                boolean parseBoolean = split.length >= 29 ? Boolean.parseBoolean(split[28]) : false;
                boolean parseBoolean2 = split.length >= 30 ? Boolean.parseBoolean(split[29]) : false;
                boolean parseBoolean3 = split.length >= 31 ? Boolean.parseBoolean(split[30]) : false;
                if (split.length < 32 || split[31].length() == 0) {
                    ikeTunnelConnectionParams = null;
                } else {
                    Parcel obtain = Parcel.obtain();
                    byte[] hexStringToByteArray = HexDump.hexStringToByteArray(split[31]);
                    obtain.unmarshall(hexStringToByteArray, 0, hexStringToByteArray.length);
                    obtain.setDataPosition(0);
                    ikeTunnelConnectionParams = TunnelConnectionParamsUtils.fromPersistableBundle((PersistableBundle) obtain.readValue(PersistableBundle.class.getClassLoader()));
                }
                if (split.length >= 34) {
                    z = Boolean.parseBoolean(split[32]);
                    z2 = Boolean.parseBoolean(split[33]);
                } else {
                    z = false;
                    z2 = false;
                }
                VpnProfile vpnProfile2 = new VpnProfile(str, parseBoolean, parseBoolean2, parseBoolean3, ikeTunnelConnectionParams, z, z2);
                vpnProfile2.name = split[0];
                int parseInt = Integer.parseInt(split[1]);
                vpnProfile2.type = parseInt;
                if (parseInt >= 0 && parseInt <= 10) {
                    vpnProfile2.server = split[2];
                    vpnProfile2.username = split[3];
                    vpnProfile2.password = split[4];
                    vpnProfile2.dnsServers = split[5];
                    vpnProfile2.searchDomains = split[6];
                    vpnProfile = null;
                    try {
                        vpnProfile2.routes = split[7];
                        vpnProfile2.mppe = Boolean.parseBoolean(split[8]);
                        vpnProfile2.l2tpSecret = split[9];
                        vpnProfile2.ipsecIdentifier = split[10];
                        vpnProfile2.ipsecSecret = split[11];
                        vpnProfile2.ipsecUserCert = split[12];
                        vpnProfile2.ipsecCaCert = split[13];
                        vpnProfile2.ipsecServerCert = split.length > 14 ? split[14] : "";
                        vpnProfile2.ocspServerUrl = split.length > 15 ? split[15] : "";
                        vpnProfile2.isPFS = split.length > 16 ? Boolean.valueOf(split[16]).booleanValue() : false;
                        vpnProfile2.isPasswordIvParams = split.length > 17 ? split[17] : "";
                        vpnProfile2.isIpsecSecretIvParams = split.length > 18 ? split[18] : "";
                        if (split.length > 19) {
                            String str2 = split.length > 19 ? split[19] : "";
                            String str3 = split.length > 20 ? split[20] : "";
                            c = 23;
                            String str4 = split.length > 21 ? split[21] : "";
                            String str5 = split.length > 22 ? split[22] : "";
                            if (str2.isEmpty() && str3.isEmpty() && str4.isEmpty()) {
                                if (!str5.isEmpty()) {
                                    vpnProfile2.proxy = ProxyInfo.buildPacProxy(Uri.parse(str5));
                                }
                            }
                            vpnProfile2.proxy = ProxyInfo.buildDirectProxy(str2, str3.isEmpty() ? 0 : Integer.parseInt(str3), ProxyUtils.exclusionStringAsList(str4));
                        } else {
                            c = 23;
                        }
                        if (split.length >= 28) {
                            vpnProfile2.mAllowedAlgorithms = new ArrayList();
                            Iterator it = Arrays.asList(split[c].split(",")).iterator();
                            while (it.hasNext()) {
                                vpnProfile2.mAllowedAlgorithms.add(URLDecoder.decode((String) it.next(), DEFAULT_ENCODING));
                            }
                            vpnProfile2.isBypassable = Boolean.parseBoolean(split[24]);
                            vpnProfile2.isMetered = Boolean.parseBoolean(split[25]);
                            vpnProfile2.maxMtu = Integer.parseInt(split[26]);
                            vpnProfile2.areAuthParamsInline = Boolean.parseBoolean(split[27]);
                            vpnProfile2.ipsecRemoteIdentifier = split.length > 34 ? split[34] : "";
                            vpnProfile2.ipsecCacertValue = split.length > 35 ? split[35] : "";
                            vpnProfile2.ipsecServerCertValue = split.length > 36 ? split[36] : "";
                            vpnProfile2.allCert = split.length > 37 ? split[37] : "";
                            vpnProfile2.ikeCipherSuites = split.length > 38 ? split[38] : "";
                            vpnProfile2.ipsecCipherSuites = split.length > 39 ? split[39] : "";
                            vpnProfile2.ipsecRekeyTime = split.length > 40 ? Integer.parseInt(split[40]) : 0;
                            vpnProfile2.ikeRekeyTime = split.length > 41 ? Integer.parseInt(split[41]) : 0;
                        }
                        if (vpnProfile2.username.isEmpty() && vpnProfile2.password.isEmpty()) {
                            z3 = false;
                            vpnProfile2.saveLogin = z3;
                            if (vpnProfile2.type != 3 && vpnProfile2.ipsecUserCert.isEmpty() && !vpnProfile2.ipsecCaCert.isEmpty()) {
                                vpnProfile2.type = 5;
                                return vpnProfile2;
                            }
                            if (vpnProfile2.type != 4 && !vpnProfile2.ipsecSecret.isEmpty()) {
                                vpnProfile2.type = 3;
                                return vpnProfile2;
                            }
                            if (vpnProfile2.type != 5 && !vpnProfile2.ipsecUserCert.isEmpty()) {
                                vpnProfile2.type = 4;
                                return vpnProfile2;
                            }
                            if (vpnProfile2.type != 6 && !vpnProfile2.ipsecSecret.isEmpty()) {
                                vpnProfile2.type = 7;
                                return vpnProfile2;
                            }
                            if (vpnProfile2.type == 7 && !vpnProfile2.ipsecUserCert.isEmpty()) {
                                vpnProfile2.type = 8;
                            }
                            return vpnProfile2;
                        }
                        z3 = true;
                        vpnProfile2.saveLogin = z3;
                        if (vpnProfile2.type != 3) {
                        }
                        if (vpnProfile2.type != 4) {
                        }
                        if (vpnProfile2.type != 5) {
                        }
                        if (vpnProfile2.type != 6) {
                        }
                        if (vpnProfile2.type == 7) {
                            vpnProfile2.type = 8;
                        }
                        return vpnProfile2;
                    } catch (Exception e) {
                        e = e;
                        Log.d(TAG, "Got exception in decode.", e);
                        return vpnProfile;
                    }
                }
                return null;
            }
            return null;
        } catch (Exception e2) {
            e = e2;
            vpnProfile = null;
        }
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
        ArrayList arrayList = new ArrayList();
        try {
            Iterator<String> it = this.mAllowedAlgorithms.iterator();
            while (it.hasNext()) {
                arrayList.add(URLEncoder.encode(it.next(), DEFAULT_ENCODING));
            }
            sb.append(VALUE_DELIMITER);
            sb.append(String.join(",", arrayList));
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
            sb.append(this.excludeLocalRoutes);
            sb.append(VALUE_DELIMITER);
            sb.append(this.requiresInternetValidation);
            IkeTunnelConnectionParams ikeTunnelConnectionParams = this.ikeTunConnParams;
            if (ikeTunnelConnectionParams != null) {
                PersistableBundle persistableBundle = TunnelConnectionParamsUtils.toPersistableBundle(ikeTunnelConnectionParams);
                Parcel obtain = Parcel.obtain();
                obtain.writeValue(persistableBundle);
                byte[] marshall = obtain.marshall();
                sb.append(VALUE_DELIMITER);
                sb.append(HexDump.toHexString(marshall));
            } else {
                sb.append(VALUE_DELIMITER);
            }
            sb.append(VALUE_DELIMITER);
            sb.append(this.automaticNattKeepaliveTimerEnabled);
            sb.append(VALUE_DELIMITER);
            sb.append(this.automaticIpVersionSelectionEnabled);
            sb.append(VALUE_DELIMITER);
            sb.append(this.ipsecRemoteIdentifier);
            sb.append(VALUE_DELIMITER);
            sb.append(this.ipsecCacertValue);
            sb.append(VALUE_DELIMITER);
            sb.append(this.ipsecServerCertValue);
            sb.append(VALUE_DELIMITER);
            sb.append(this.allCert);
            sb.append(VALUE_DELIMITER);
            sb.append(this.ikeCipherSuites);
            sb.append(VALUE_DELIMITER);
            sb.append(this.ipsecCipherSuites);
            sb.append(VALUE_DELIMITER);
            sb.append(this.ipsecRekeyTime);
            sb.append(VALUE_DELIMITER);
            sb.append(this.ikeRekeyTime);
            return sb.toString().getBytes(StandardCharsets.UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Failed to encode algorithms.", e);
        }
    }

    private boolean isValidLockdownLegacyVpnProfile() {
        return isLegacyType(this.type) && isServerAddressNumeric() && hasDns() && areDnsAddressesNumeric();
    }

    private boolean isValidLockdownPlatformVpnProfile() {
        return Ikev2VpnProfile.isValidVpnProfile(this);
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

    public int hashCode() {
        return Objects.hash(this.key, Integer.valueOf(this.type), this.server, this.username, this.password, this.dnsServers, this.searchDomains, this.routes, Boolean.valueOf(this.mppe), this.l2tpSecret, this.ipsecIdentifier, this.ipsecSecret, this.ipsecUserCert, this.ipsecCaCert, this.ipsecServerCert, this.proxy, this.mAllowedAlgorithms, Boolean.valueOf(this.isBypassable), Boolean.valueOf(this.isMetered), Integer.valueOf(this.maxMtu), Boolean.valueOf(this.areAuthParamsInline), Boolean.valueOf(this.isRestrictedToTestNetworks), Boolean.valueOf(this.excludeLocalRoutes), Boolean.valueOf(this.requiresInternetValidation), this.ikeTunConnParams, Boolean.valueOf(this.automaticNattKeepaliveTimerEnabled), Boolean.valueOf(this.automaticIpVersionSelectionEnabled), this.ipsecRemoteIdentifier, this.ipsecCacertValue, this.ipsecServerCertValue, this.allCert, this.ikeCipherSuites, this.ipsecCipherSuites, Integer.valueOf(this.ipsecRekeyTime), Integer.valueOf(this.ikeRekeyTime));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof VpnProfile)) {
            return false;
        }
        VpnProfile vpnProfile = (VpnProfile) obj;
        return Objects.equals(this.key, vpnProfile.key) && Objects.equals(this.name, vpnProfile.name) && this.type == vpnProfile.type && Objects.equals(this.server, vpnProfile.server) && Objects.equals(this.username, vpnProfile.username) && Objects.equals(this.password, vpnProfile.password) && Objects.equals(this.dnsServers, vpnProfile.dnsServers) && Objects.equals(this.searchDomains, vpnProfile.searchDomains) && Objects.equals(this.routes, vpnProfile.routes) && this.mppe == vpnProfile.mppe && Objects.equals(this.l2tpSecret, vpnProfile.l2tpSecret) && Objects.equals(this.ipsecIdentifier, vpnProfile.ipsecIdentifier) && Objects.equals(this.ipsecSecret, vpnProfile.ipsecSecret) && Objects.equals(this.ipsecUserCert, vpnProfile.ipsecUserCert) && Objects.equals(this.ipsecCaCert, vpnProfile.ipsecCaCert) && Objects.equals(this.ipsecServerCert, vpnProfile.ipsecServerCert) && Objects.equals(this.proxy, vpnProfile.proxy) && Objects.equals(this.mAllowedAlgorithms, vpnProfile.mAllowedAlgorithms) && this.isBypassable == vpnProfile.isBypassable && this.isMetered == vpnProfile.isMetered && this.maxMtu == vpnProfile.maxMtu && this.areAuthParamsInline == vpnProfile.areAuthParamsInline && this.isRestrictedToTestNetworks == vpnProfile.isRestrictedToTestNetworks && this.excludeLocalRoutes == vpnProfile.excludeLocalRoutes && this.requiresInternetValidation == vpnProfile.requiresInternetValidation && Objects.equals(this.ikeTunConnParams, vpnProfile.ikeTunConnParams) && this.automaticNattKeepaliveTimerEnabled == vpnProfile.automaticNattKeepaliveTimerEnabled && this.automaticIpVersionSelectionEnabled == vpnProfile.automaticIpVersionSelectionEnabled && Objects.equals(this.ipsecRemoteIdentifier, vpnProfile.ipsecRemoteIdentifier) && Objects.equals(this.ipsecCacertValue, vpnProfile.ipsecCacertValue) && Objects.equals(this.ipsecServerCertValue, vpnProfile.ipsecServerCertValue) && Objects.equals(this.allCert, vpnProfile.allCert) && Objects.equals(this.ikeCipherSuites, vpnProfile.ikeCipherSuites) && Objects.equals(this.ipsecCipherSuites, vpnProfile.ipsecCipherSuites) && this.ipsecRekeyTime == vpnProfile.ipsecRekeyTime && this.ikeRekeyTime == vpnProfile.ikeRekeyTime;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public VpnProfile m8082clone() {
        try {
            return (VpnProfile) super.clone();
        } catch (CloneNotSupportedException e) {
            Log.wtf(TAG, e);
            return null;
        }
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

    private static String[] doEncrypt(Key key, String str) {
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

    private static String doDecrypt(Key key, String str, String str2) {
        try {
            byte[] decode = Base64.decode(str, 2);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(hex2Bytes(new String(Base64.decode(str2, 2), StandardCharsets.UTF_8)));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(2, key, ivParameterSpec);
            return new String(Base64.decode(cipher.doFinal(decode), 2), StandardCharsets.UTF_8).intern();
        } catch (IllegalArgumentException | NullPointerException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            Log.e(TAG, "Failed to decrypt: " + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    private static Key getSecretKey(boolean z) {
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

    private static void encrypt(VpnProfile vpnProfile) {
        Key secretKey;
        String[] doEncrypt;
        String[] doEncrypt2;
        if ((vpnProfile.ipsecSecret.isEmpty() && vpnProfile.password.isEmpty()) || (secretKey = getSecretKey(true)) == null) {
            return;
        }
        if (!vpnProfile.ipsecSecret.isEmpty() && (doEncrypt2 = doEncrypt(secretKey, vpnProfile.ipsecSecret)) != null) {
            vpnProfile.ipsecSecret = doEncrypt2[0];
            vpnProfile.isIpsecSecretIvParams = doEncrypt2[1];
        }
        if (vpnProfile.password.isEmpty() || (doEncrypt = doEncrypt(secretKey, vpnProfile.password)) == null) {
            return;
        }
        vpnProfile.password = doEncrypt[0];
        vpnProfile.isPasswordIvParams = doEncrypt[1];
    }

    public static void decrypt(VpnProfile vpnProfile) {
        String doDecrypt;
        String doDecrypt2;
        if (!vpnProfile.isIpsecSecretIvParams.isEmpty() || !vpnProfile.isPasswordIvParams.isEmpty()) {
            boolean z = false;
            try {
                Key secretKey = getSecretKey(false);
                if (secretKey != null) {
                    if (Security.getProvider(ANDROID_BC_PROVIDER) == null) {
                        AndroidKeyStoreProvider.install();
                        z = true;
                    }
                    if (!vpnProfile.ipsecSecret.isEmpty() && (doDecrypt2 = doDecrypt(secretKey, vpnProfile.ipsecSecret, vpnProfile.isIpsecSecretIvParams)) != null) {
                        vpnProfile.ipsecSecret = doDecrypt2;
                    }
                    if (!vpnProfile.password.isEmpty() && (doDecrypt = doDecrypt(secretKey, vpnProfile.password, vpnProfile.isPasswordIvParams)) != null) {
                        vpnProfile.password = doDecrypt;
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
        Log.i(TAG, "This profile was not encrypted:" + vpnProfile.name);
    }

    private static byte[] intToByteArray(int i) {
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.putInt(i);
        allocate.order(ByteOrder.BIG_ENDIAN);
        return allocate.array();
    }

    private static int byteArrayToInt(byte[] bArr) {
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            i += (bArr[i2] & 255) << ((3 - i2) * 8);
        }
        return i;
    }
}
