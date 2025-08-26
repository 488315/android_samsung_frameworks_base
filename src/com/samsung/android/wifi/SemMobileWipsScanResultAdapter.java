package com.samsung.android.wifi;

import android.net.MacAddress;
import android.net.wifi.MloLink;
import android.os.Parcel;
import android.os.Parcelable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SemMobileWipsScanResultAdapter implements Parcelable {
    public static final int CHANNEL_WIDTH_160MHZ = 3;
    public static final int CHANNEL_WIDTH_20MHZ = 0;
    public static final int CHANNEL_WIDTH_40MHZ = 1;
    public static final int CHANNEL_WIDTH_80MHZ = 2;
    public static final int CHANNEL_WIDTH_80MHZ_PLUS_MHZ = 4;
    public static final int CIPHER_CCMP = 3;
    public static final int CIPHER_GCMP_256 = 4;
    public static final int CIPHER_NONE = 0;
    public static final int CIPHER_NO_GROUP_ADDRESSED = 1;
    public static final int CIPHER_SMS4 = 5;
    public static final int CIPHER_TKIP = 2;
    public static final Parcelable.Creator<SemMobileWipsScanResultAdapter> CREATOR = new Parcelable.Creator<SemMobileWipsScanResultAdapter>() { // from class: com.samsung.android.wifi.SemMobileWipsScanResultAdapter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemMobileWipsScanResultAdapter createFromParcel(Parcel parcel) {
            SemMobileWipsScanResultAdapter semMobileWipsScanResultAdapter = new SemMobileWipsScanResultAdapter(parcel.readInt() == 1 ? SemMobileWipsWifiSsid.CREATOR.createFromParcel(parcel) : null, parcel.readString(), parcel.readString(), parcel.readLong(), parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readLong(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), false);
            semMobileWipsScanResultAdapter.mWifiStandard = parcel.readInt();
            semMobileWipsScanResultAdapter.seen = parcel.readLong();
            semMobileWipsScanResultAdapter.untrusted = parcel.readInt() != 0;
            semMobileWipsScanResultAdapter.numUsage = parcel.readInt();
            semMobileWipsScanResultAdapter.venueName = parcel.readString();
            semMobileWipsScanResultAdapter.operatorFriendlyName = parcel.readString();
            semMobileWipsScanResultAdapter.flags = parcel.readLong();
            semMobileWipsScanResultAdapter.informationElements = (InformationElement[]) parcel.createTypedArray(InformationElement.CREATOR);
            int i = parcel.readInt();
            if (i != 0) {
                semMobileWipsScanResultAdapter.anqpLines = new ArrayList();
                for (int i2 = 0; i2 < i; i2++) {
                    semMobileWipsScanResultAdapter.anqpLines.add(parcel.readString());
                }
            }
            int i3 = parcel.readInt();
            if (i3 != 0) {
                semMobileWipsScanResultAdapter.anqpElements = new AnqpInformationElement[i3];
                for (int i4 = 0; i4 < i3; i4++) {
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    byte[] bArr = new byte[parcel.readInt()];
                    parcel.readByteArray(bArr);
                    semMobileWipsScanResultAdapter.anqpElements[i4] = new AnqpInformationElement(i5, i6, bArr);
                }
            }
            int i7 = parcel.readInt();
            if (i7 != 0) {
                semMobileWipsScanResultAdapter.radioChainInfos = new RadioChainInfo[i7];
                for (int i8 = 0; i8 < i7; i8++) {
                    semMobileWipsScanResultAdapter.radioChainInfos[i8] = new RadioChainInfo();
                    semMobileWipsScanResultAdapter.radioChainInfos[i8].id = parcel.readInt();
                    semMobileWipsScanResultAdapter.radioChainInfos[i8].level = parcel.readInt();
                }
            }
            semMobileWipsScanResultAdapter.ifaceName = parcel.readString();
            semMobileWipsScanResultAdapter.mApMldMacAddress = (MacAddress) parcel.readParcelable(MacAddress.class.getClassLoader());
            semMobileWipsScanResultAdapter.mApMloLinkId = parcel.readInt();
            semMobileWipsScanResultAdapter.mAffiliatedMloLinks = parcel.createTypedArrayList(MloLink.CREATOR);
            return semMobileWipsScanResultAdapter;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemMobileWipsScanResultAdapter[] newArray(int i) {
            return new SemMobileWipsScanResultAdapter[i];
        }
    };
    public static final long FLAG_80211mc_RESPONDER = 2;
    public static final long FLAG_PASSPOINT_NETWORK = 1;
    public static final int KEY_MGMT_EAP = 2;
    public static final int KEY_MGMT_EAP_SHA256 = 6;
    public static final int KEY_MGMT_EAP_SUITE_B_192 = 10;
    public static final int KEY_MGMT_FILS_SHA256 = 15;
    public static final int KEY_MGMT_FILS_SHA384 = 16;
    public static final int KEY_MGMT_FT_EAP = 4;
    public static final int KEY_MGMT_FT_PSK = 3;
    public static final int KEY_MGMT_FT_SAE = 11;
    public static final int KEY_MGMT_NONE = 0;
    public static final int KEY_MGMT_OSEN = 7;
    public static final int KEY_MGMT_OWE = 9;
    public static final int KEY_MGMT_OWE_TRANSITION = 12;
    public static final int KEY_MGMT_PSK = 1;
    public static final int KEY_MGMT_PSK_SHA256 = 5;
    public static final int KEY_MGMT_SAE = 8;
    public static final int KEY_MGMT_WAPI_CERT = 14;
    public static final int KEY_MGMT_WAPI_PSK = 13;
    public static final int PROTOCOL_NONE = 0;
    public static final int PROTOCOL_OSEN = 3;
    public static final int PROTOCOL_RSN = 2;
    public static final int PROTOCOL_WAPI = 4;
    public static final int PROTOCOL_WPA = 1;
    private static final String TAG = "SemMobileWipsScanResultAdapter";
    public static final int UNSPECIFIED = -1;
    public static final int WIFI_STANDARD_11AC = 5;
    public static final int WIFI_STANDARD_11AX = 6;
    public static final int WIFI_STANDARD_11N = 4;
    public static final int WIFI_STANDARD_LEGACY = 1;
    public static final int WIFI_STANDARD_UNKNOWN = 0;
    public String BSSID;
    public String SSID;
    public int anqpDomainId;
    public AnqpInformationElement[] anqpElements;
    public List<String> anqpLines;
    public String capabilities;
    public int centerFreq0;
    public int centerFreq1;
    public int channelWidth;
    public int distanceCm;
    public int distanceSdCm;
    public long flags;
    public int frequency;
    public long hessid;
    public String ifaceName;
    public InformationElement[] informationElements;
    public boolean is80211McRTTResponder;
    public int level;
    private List<MloLink> mAffiliatedMloLinks;
    private MacAddress mApMldMacAddress;
    private int mApMloLinkId;
    private int mWifiStandard;
    public int numUsage;
    public CharSequence operatorFriendlyName;
    public RadioChainInfo[] radioChainInfos;
    public long seen;
    public long timestamp;
    public boolean untrusted;
    public CharSequence venueName;
    public SemMobileWipsWifiSsid wifiSsid;

    public static boolean is24GHz(int i) {
        return i > 2400 && i < 2500;
    }

    public static boolean is5GHz(int i) {
        return i > 4900 && i < 5900;
    }

    public static boolean is6GHz(int i) {
        return i > 5925 && i < 7125;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemMobileWipsScanResultAdapter(SemMobileWipsWifiSsid semMobileWipsWifiSsid, String str, long j, int i, byte[] bArr, String str2, int i2, int i3, long j2) {
        this.mApMloLinkId = -1;
        this.mAffiliatedMloLinks = Collections.EMPTY_LIST;
        this.wifiSsid = semMobileWipsWifiSsid;
        this.SSID = semMobileWipsWifiSsid != null ? semMobileWipsWifiSsid.toString() : "<unknown ssid>";
        this.BSSID = str;
        this.hessid = j;
        this.anqpDomainId = i;
        if (bArr != null) {
            this.anqpElements = new AnqpInformationElement[]{new AnqpInformationElement(5271450, 8, bArr)};
        }
        this.capabilities = str2;
        this.level = i2;
        this.frequency = i3;
        this.timestamp = j2;
        this.distanceCm = -1;
        this.distanceSdCm = -1;
        this.channelWidth = -1;
        this.centerFreq0 = -1;
        this.centerFreq1 = -1;
        this.flags = 0L;
        this.radioChainInfos = null;
        this.mApMldMacAddress = null;
    }

    public SemMobileWipsScanResultAdapter(SemMobileWipsWifiSsid semMobileWipsWifiSsid, String str, String str2, int i, int i2, long j, int i3, int i4) {
        this.mApMloLinkId = -1;
        this.mAffiliatedMloLinks = Collections.EMPTY_LIST;
        this.wifiSsid = semMobileWipsWifiSsid;
        this.SSID = semMobileWipsWifiSsid != null ? semMobileWipsWifiSsid.toString() : "<unknown ssid>";
        this.BSSID = str;
        this.capabilities = str2;
        this.level = i;
        this.frequency = i2;
        this.timestamp = j;
        this.distanceCm = i3;
        this.distanceSdCm = i4;
        this.channelWidth = -1;
        this.centerFreq0 = -1;
        this.centerFreq1 = -1;
        this.flags = 0L;
        this.radioChainInfos = null;
        this.mApMldMacAddress = null;
    }

    public SemMobileWipsScanResultAdapter(String str, String str2, long j, int i, String str3, int i2, int i3, long j2, int i4, int i5, int i6, int i7, int i8, boolean z) {
        this.mApMloLinkId = -1;
        this.mAffiliatedMloLinks = Collections.EMPTY_LIST;
        this.SSID = str;
        this.BSSID = str2;
        this.hessid = j;
        this.anqpDomainId = i;
        this.capabilities = str3;
        this.level = i2;
        this.frequency = i3;
        this.timestamp = j2;
        this.distanceCm = i4;
        this.distanceSdCm = i5;
        this.channelWidth = i6;
        this.centerFreq0 = i7;
        this.centerFreq1 = i8;
        if (z) {
            this.flags = 2L;
        } else {
            this.flags = 0L;
        }
        this.radioChainInfos = null;
        this.mApMldMacAddress = null;
    }

    public SemMobileWipsScanResultAdapter(SemMobileWipsWifiSsid semMobileWipsWifiSsid, String str, String str2, long j, int i, String str3, int i2, int i3, long j2, int i4, int i5, int i6, int i7, int i8, boolean z) {
        this(str, str2, j, i, str3, i2, i3, j2, i4, i5, i6, i7, i8, z);
        this.wifiSsid = semMobileWipsWifiSsid;
    }

    public SemMobileWipsScanResultAdapter(SemMobileWipsScanResultAdapter semMobileWipsScanResultAdapter) {
        this.mApMloLinkId = -1;
        this.mAffiliatedMloLinks = Collections.EMPTY_LIST;
        if (semMobileWipsScanResultAdapter != null) {
            this.wifiSsid = semMobileWipsScanResultAdapter.wifiSsid;
            this.SSID = semMobileWipsScanResultAdapter.SSID;
            this.BSSID = semMobileWipsScanResultAdapter.BSSID;
            this.hessid = semMobileWipsScanResultAdapter.hessid;
            this.anqpDomainId = semMobileWipsScanResultAdapter.anqpDomainId;
            this.informationElements = semMobileWipsScanResultAdapter.informationElements;
            this.anqpElements = semMobileWipsScanResultAdapter.anqpElements;
            this.capabilities = semMobileWipsScanResultAdapter.capabilities;
            this.level = semMobileWipsScanResultAdapter.level;
            this.frequency = semMobileWipsScanResultAdapter.frequency;
            this.channelWidth = semMobileWipsScanResultAdapter.channelWidth;
            this.centerFreq0 = semMobileWipsScanResultAdapter.centerFreq0;
            this.centerFreq1 = semMobileWipsScanResultAdapter.centerFreq1;
            this.timestamp = semMobileWipsScanResultAdapter.timestamp;
            this.distanceCm = semMobileWipsScanResultAdapter.distanceCm;
            this.distanceSdCm = semMobileWipsScanResultAdapter.distanceSdCm;
            this.seen = semMobileWipsScanResultAdapter.seen;
            this.untrusted = semMobileWipsScanResultAdapter.untrusted;
            this.numUsage = semMobileWipsScanResultAdapter.numUsage;
            this.venueName = semMobileWipsScanResultAdapter.venueName;
            this.operatorFriendlyName = semMobileWipsScanResultAdapter.operatorFriendlyName;
            this.flags = semMobileWipsScanResultAdapter.flags;
            this.radioChainInfos = semMobileWipsScanResultAdapter.radioChainInfos;
            this.mWifiStandard = semMobileWipsScanResultAdapter.mWifiStandard;
            this.ifaceName = semMobileWipsScanResultAdapter.ifaceName;
            this.mApMldMacAddress = semMobileWipsScanResultAdapter.mApMldMacAddress;
            this.mApMloLinkId = semMobileWipsScanResultAdapter.mApMloLinkId;
            this.mAffiliatedMloLinks = semMobileWipsScanResultAdapter.mAffiliatedMloLinks != null ? new ArrayList<>(semMobileWipsScanResultAdapter.mAffiliatedMloLinks) : Collections.EMPTY_LIST;
        }
    }

    public SemMobileWipsScanResultAdapter() {
        this.mApMloLinkId = -1;
        this.mAffiliatedMloLinks = Collections.EMPTY_LIST;
    }

    private static String wifiStandardToString(int i) {
        if (i == 0) {
            return "unknown";
        }
        if (i == 1) {
            return "legacy";
        }
        if (i == 4) {
            return "11n";
        }
        if (i == 5) {
            return "11ac";
        }
        if (i != 6) {
            return null;
        }
        return "11ax";
    }

    public int getWifiStandard() {
        return this.mWifiStandard;
    }

    public void setWifiStandard(int i) {
        this.mWifiStandard = i;
    }

    public void setFlag(long j) {
        this.flags = j | this.flags;
    }

    public void clearFlag(long j) {
        this.flags = (~j) & this.flags;
    }

    public boolean is80211mcResponder() {
        return (this.flags & 2) != 0;
    }

    public boolean isPasspointNetwork() {
        return (this.flags & 1) != 0;
    }

    public boolean is24GHz() {
        return is24GHz(this.frequency);
    }

    public boolean is5GHz() {
        return is5GHz(this.frequency);
    }

    public boolean is6GHz() {
        return is6GHz(this.frequency);
    }

    public List<InformationElement> getInformationElements() {
        return Collections.unmodifiableList(Arrays.asList(this.informationElements));
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("SSID: ");
        Object obj = this.wifiSsid;
        if (obj == null) {
            obj = "<unknown ssid>";
        }
        StringBuffer stringBufferAppend = stringBuffer.append(obj).append(", BSSID: ");
        String str = this.BSSID;
        if (str == null) {
            str = "<none>";
        }
        StringBuffer stringBufferAppend2 = stringBufferAppend.append(str).append(", capabilities: ");
        String str2 = this.capabilities;
        stringBufferAppend2.append(str2 != null ? str2 : "<none>").append(", level: ").append(this.level).append(", frequency: ").append(this.frequency).append(", timestamp: ").append(this.timestamp);
        StringBuffer stringBufferAppend3 = stringBuffer.append(", distance: ");
        int i = this.distanceCm;
        stringBufferAppend3.append(i != -1 ? Integer.valueOf(i) : "?").append("(cm), distanceSd: ");
        int i2 = this.distanceSdCm;
        stringBuffer.append(i2 != -1 ? Integer.valueOf(i2) : "?").append("(cm), passpoint: ");
        stringBuffer.append((this.flags & 1) != 0 ? "yes" : "no");
        stringBuffer.append(", ChannelBandwidth: ").append(this.channelWidth);
        stringBuffer.append(", centerFreq0: ").append(this.centerFreq0);
        stringBuffer.append(", centerFreq1: ").append(this.centerFreq1);
        stringBuffer.append(", standard: ").append(wifiStandardToString(this.mWifiStandard));
        stringBuffer.append(", 80211mcResponder: ");
        stringBuffer.append((this.flags & 2) != 0 ? "is supported" : "is not supported");
        stringBuffer.append(", Radio Chain Infos: ").append(Arrays.toString(this.radioChainInfos));
        stringBuffer.append(", interface name: ").append(this.ifaceName);
        if (this.mApMldMacAddress != null) {
            StringBuffer stringBufferAppend4 = stringBuffer.append(", MLO Info:  AP MLD MAC Address: ").append(this.mApMldMacAddress.toString()).append(", AP MLO Link-Id: ");
            int i3 = this.mApMloLinkId;
            stringBufferAppend4.append(i3 == -1 ? "Unspecified" : Integer.valueOf(i3)).append(", AP MLO Affiliated Links: ").append(this.mAffiliatedMloLinks);
        }
        return stringBuffer.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 0;
        if (this.wifiSsid != null) {
            parcel.writeInt(1);
            this.wifiSsid.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.SSID);
        parcel.writeString(this.BSSID);
        parcel.writeLong(this.hessid);
        parcel.writeInt(this.anqpDomainId);
        parcel.writeString(this.capabilities);
        parcel.writeInt(this.level);
        parcel.writeInt(this.frequency);
        parcel.writeLong(this.timestamp);
        parcel.writeInt(this.distanceCm);
        parcel.writeInt(this.distanceSdCm);
        parcel.writeInt(this.channelWidth);
        parcel.writeInt(this.centerFreq0);
        parcel.writeInt(this.centerFreq1);
        parcel.writeInt(this.mWifiStandard);
        parcel.writeLong(this.seen);
        parcel.writeInt(this.untrusted ? 1 : 0);
        parcel.writeInt(this.numUsage);
        CharSequence charSequence = this.venueName;
        parcel.writeString(charSequence != null ? charSequence.toString() : "");
        CharSequence charSequence2 = this.operatorFriendlyName;
        parcel.writeString(charSequence2 != null ? charSequence2.toString() : "");
        parcel.writeLong(this.flags);
        parcel.writeTypedArray(this.informationElements, i);
        List<String> list = this.anqpLines;
        if (list != null) {
            parcel.writeInt(list.size());
            for (int i3 = 0; i3 < this.anqpLines.size(); i3++) {
                parcel.writeString(this.anqpLines.get(i3));
            }
        } else {
            parcel.writeInt(0);
        }
        AnqpInformationElement[] anqpInformationElementArr = this.anqpElements;
        if (anqpInformationElementArr != null) {
            parcel.writeInt(anqpInformationElementArr.length);
            for (AnqpInformationElement anqpInformationElement : this.anqpElements) {
                parcel.writeInt(anqpInformationElement.getVendorId());
                parcel.writeInt(anqpInformationElement.getElementId());
                parcel.writeInt(anqpInformationElement.getPayload().length);
                parcel.writeByteArray(anqpInformationElement.getPayload());
            }
        } else {
            parcel.writeInt(0);
        }
        RadioChainInfo[] radioChainInfoArr = this.radioChainInfos;
        if (radioChainInfoArr != null) {
            parcel.writeInt(radioChainInfoArr.length);
            while (true) {
                RadioChainInfo[] radioChainInfoArr2 = this.radioChainInfos;
                if (i2 >= radioChainInfoArr2.length) {
                    break;
                }
                parcel.writeInt(radioChainInfoArr2[i2].id);
                parcel.writeInt(this.radioChainInfos[i2].level);
                i2++;
            }
        } else {
            parcel.writeInt(0);
        }
        String str = this.ifaceName;
        parcel.writeString(str != null ? str.toString() : "");
        parcel.writeParcelable(this.mApMldMacAddress, i);
        parcel.writeInt(this.mApMloLinkId);
        parcel.writeTypedList(this.mAffiliatedMloLinks);
    }

    public static class RadioChainInfo {
        public int id;
        public int level;

        public String toString() {
            return "RadioChainInfo: id=" + this.id + ", level=" + this.level;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RadioChainInfo)) {
                return false;
            }
            RadioChainInfo radioChainInfo = (RadioChainInfo) obj;
            return this.id == radioChainInfo.id && this.level == radioChainInfo.level;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.id), Integer.valueOf(this.level));
        }
    }

    public static class AnqpInformationElement {
        public static final int ANQP_3GPP_NETWORK = 264;
        public static final int ANQP_CAPABILITY_LIST = 257;
        public static final int ANQP_CIVIC_LOC = 266;
        public static final int ANQP_DOM_NAME = 268;
        public static final int ANQP_EMERGENCY_ALERT = 269;
        public static final int ANQP_EMERGENCY_NAI = 271;
        public static final int ANQP_EMERGENCY_NUMBER = 259;
        public static final int ANQP_GEO_LOC = 265;
        public static final int ANQP_IP_ADDR_AVAILABILITY = 262;
        public static final int ANQP_LOC_URI = 267;
        public static final int ANQP_NAI_REALM = 263;
        public static final int ANQP_NEIGHBOR_REPORT = 272;
        public static final int ANQP_NWK_AUTH_TYPE = 260;
        public static final int ANQP_QUERY_LIST = 256;
        public static final int ANQP_ROAMING_CONSORTIUM = 261;
        public static final int ANQP_TDLS_CAP = 270;
        public static final int ANQP_VENDOR_SPEC = 56797;
        public static final int ANQP_VENUE_NAME = 258;
        public static final int HOTSPOT20_VENDOR_ID = 5271450;
        public static final int HS_CAPABILITY_LIST = 2;
        public static final int HS_CONN_CAPABILITY = 5;
        public static final int HS_FRIENDLY_NAME = 3;
        public static final int HS_ICON_FILE = 11;
        public static final int HS_ICON_REQUEST = 10;
        public static final int HS_NAI_HOME_REALM_QUERY = 6;
        public static final int HS_OPERATING_CLASS = 7;
        public static final int HS_OSU_PROVIDERS = 8;
        public static final int HS_QUERY_LIST = 1;
        public static final int HS_WAN_METRICS = 4;
        private final int mElementId;
        private final byte[] mPayload;
        private final int mVendorId;

        public AnqpInformationElement(int i, int i2, byte[] bArr) {
            this.mVendorId = i;
            this.mElementId = i2;
            this.mPayload = bArr;
        }

        public int getVendorId() {
            return this.mVendorId;
        }

        public int getElementId() {
            return this.mElementId;
        }

        public byte[] getPayload() {
            return this.mPayload;
        }
    }

    public static class InformationElement implements Parcelable {
        public static final Parcelable.Creator<InformationElement> CREATOR = new Parcelable.Creator<InformationElement>() { // from class: com.samsung.android.wifi.SemMobileWipsScanResultAdapter.InformationElement.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public InformationElement createFromParcel(Parcel parcel) {
                InformationElement informationElement = new InformationElement();
                informationElement.id = parcel.readInt();
                informationElement.idExt = parcel.readInt();
                informationElement.bytes = parcel.createByteArray();
                return informationElement;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public InformationElement[] newArray(int i) {
                return new InformationElement[i];
            }
        };
        public static final int EID_BSS_LOAD = 11;
        public static final int EID_ERP = 42;
        public static final int EID_EXTENDED_CAPS = 127;
        public static final int EID_EXTENDED_SUPPORTED_RATES = 50;
        public static final int EID_EXTENSION_PRESENT = 255;
        public static final int EID_EXT_HE_CAPABILITIES = 35;
        public static final int EID_EXT_HE_OPERATION = 36;
        public static final int EID_HT_CAPABILITIES = 45;
        public static final int EID_HT_OPERATION = 61;
        public static final int EID_INTERWORKING = 107;
        public static final int EID_ROAMING_CONSORTIUM = 111;
        public static final int EID_RSN = 48;
        public static final int EID_SSID = 0;
        public static final int EID_SUPPORTED_RATES = 1;
        public static final int EID_TIM = 5;
        public static final int EID_VHT_CAPABILITIES = 191;
        public static final int EID_VHT_OPERATION = 192;
        public static final int EID_VSA = 221;
        public byte[] bytes;
        public int id;
        public int idExt;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public InformationElement() {
        }

        public InformationElement(InformationElement informationElement) {
            this.id = informationElement.id;
            this.idExt = informationElement.idExt;
            this.bytes = (byte[]) informationElement.bytes.clone();
        }

        public int getId() {
            return this.id;
        }

        public int getIdExt() {
            return this.idExt;
        }

        public ByteBuffer getBytes() {
            return ByteBuffer.wrap(this.bytes).asReadOnlyBuffer();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.id);
            parcel.writeInt(this.idExt);
            parcel.writeByteArray(this.bytes);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof InformationElement)) {
                return false;
            }
            InformationElement informationElement = (InformationElement) obj;
            return this.id == informationElement.id && this.idExt == informationElement.idExt && Arrays.equals(this.bytes, informationElement.bytes);
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.id), Integer.valueOf(this.idExt), Integer.valueOf(Arrays.hashCode(this.bytes)));
        }
    }
}
