package com.samsung.android.knox.net.vpn;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class VpnAdminProfile implements Parcelable {
    public static final Parcelable.Creator<VpnAdminProfile> CREATOR = new Parcelable.Creator<VpnAdminProfile>() { // from class: com.samsung.android.knox.net.vpn.VpnAdminProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final VpnAdminProfile[] newArray(int i) {
            return new VpnAdminProfile[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final VpnAdminProfile createFromParcel(Parcel parcel) {
            return new VpnAdminProfile(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final VpnAdminProfile[] newArray(int i) {
            return new VpnAdminProfile[i];
        }
    };
    public static final String VPN_TYPE_IPSEC_HYBRID_RSA = "IPSEC_HYBRID_RSA";
    public static final String VPN_TYPE_IPSEC_IKEV2_PSK = "IPSEC_IKEV2_PSK";
    public static final String VPN_TYPE_IPSEC_IKEV2_RSA = "IPSEC_IKEV2_RSA";
    public static final String VPN_TYPE_IPSEC_XAUTH_PSK = "IPSEC_XAUTH_PSK";
    public static final String VPN_TYPE_IPSEC_XAUTH_RSA = "IPSEC_XAUTH_RSA";
    public static final String VPN_TYPE_L2TP = "L2TP";
    public static final String VPN_TYPE_L2TP_IPSEC_CRT = "L2TP_IPSEC";
    public static final String VPN_TYPE_L2TP_IPSEC_PSK = "L2TP_IPSEC_PSK";
    public static final String VPN_TYPE_PPTP = "PPTP";
    public List<String> dnsServers;
    public boolean enableL2TPSecret;
    public boolean enablePPTPEncryption;
    public List<String> forwardRoutes;
    public String ipsecCaCertificate;
    public String ipsecIdentifier;
    public String ipsecPreSharedKey;
    public String ipsecUserCertificate;
    public String l2tpSecret;
    public String ocspServerUrl;
    public String profileName;
    public List<String> searchDomains;
    public String serverName;
    public String userName;
    public String userPassword;
    public String vpnType;

    public /* synthetic */ VpnAdminProfile(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.profileName);
        parcel.writeString(this.serverName);
        parcel.writeString(this.vpnType);
        parcel.writeString(this.userName);
        parcel.writeString(this.userPassword);
        if (this.enablePPTPEncryption) {
            parcel.writeByte((byte) 1);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.enableL2TPSecret) {
            parcel.writeByte((byte) 1);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeString(this.l2tpSecret);
        parcel.writeString(this.ipsecPreSharedKey);
        parcel.writeString(this.ipsecCaCertificate);
        parcel.writeString(this.ipsecUserCertificate);
        parcel.writeStringList(this.dnsServers);
        parcel.writeStringList(this.searchDomains);
        parcel.writeStringList(this.forwardRoutes);
        parcel.writeString(this.ipsecIdentifier);
        parcel.writeString(this.ocspServerUrl);
    }

    public VpnAdminProfile() {
        this.profileName = null;
        this.serverName = null;
        this.vpnType = null;
        this.userName = "";
        this.userPassword = "";
        this.enablePPTPEncryption = false;
        this.enableL2TPSecret = false;
        this.l2tpSecret = "";
        this.ipsecPreSharedKey = "";
        this.ipsecCaCertificate = "";
        this.ipsecUserCertificate = "";
        this.dnsServers = new ArrayList();
        this.searchDomains = new ArrayList();
        this.forwardRoutes = new ArrayList();
        this.ipsecIdentifier = "";
        this.ocspServerUrl = "";
    }

    private VpnAdminProfile(Parcel parcel) {
        this.profileName = null;
        this.serverName = null;
        this.vpnType = null;
        this.userName = "";
        this.userPassword = "";
        this.enablePPTPEncryption = false;
        this.enableL2TPSecret = false;
        this.l2tpSecret = "";
        this.ipsecPreSharedKey = "";
        this.ipsecCaCertificate = "";
        this.ipsecUserCertificate = "";
        this.dnsServers = new ArrayList();
        this.searchDomains = new ArrayList();
        this.forwardRoutes = new ArrayList();
        this.ipsecIdentifier = "";
        this.ocspServerUrl = "";
        this.profileName = parcel.readString();
        this.serverName = parcel.readString();
        this.vpnType = parcel.readString();
        this.userName = parcel.readString();
        this.userPassword = parcel.readString();
        this.enablePPTPEncryption = parcel.readByte() == 1;
        this.enableL2TPSecret = parcel.readByte() == 1;
        this.l2tpSecret = parcel.readString();
        this.ipsecPreSharedKey = parcel.readString();
        this.ipsecCaCertificate = parcel.readString();
        this.ipsecUserCertificate = parcel.readString();
        parcel.readStringList(this.dnsServers);
        parcel.readStringList(this.searchDomains);
        parcel.readStringList(this.forwardRoutes);
        this.ipsecIdentifier = parcel.readString();
        this.ocspServerUrl = parcel.readString();
    }
}
