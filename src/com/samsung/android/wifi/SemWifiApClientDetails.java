package com.samsung.android.wifi;

import android.media.AudioSystem;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.telecom.Logging.Session;
import android.text.TextUtils;
import com.samsung.android.audio.SoundTheme;
import com.samsung.android.lock.LsConstants;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.util.Objects;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class SemWifiApClientDetails implements Parcelable, Comparable<SemWifiApClientDetails> {
    public static final Parcelable.Creator<SemWifiApClientDetails> CREATOR = new Parcelable.Creator<SemWifiApClientDetails>() { // from class: com.samsung.android.wifi.SemWifiApClientDetails.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiApClientDetails createFromParcel(Parcel parcel) {
            return new SemWifiApClientDetails(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiApClientDetails[] newArray(int i) {
            return new SemWifiApClientDetails[i];
        }
    };
    public static final String DEFAULT_CONNECTED_IP = "x.x.x.x";
    private static final int MAX_DEVICE_NAME_LOG = 32;
    public static final String REGEX_MAC = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";
    public static final String UNKNOWN = "unknown";
    protected boolean isCellularStream;
    protected long mClientActiveSessionMobileData;
    protected long mClientCurrentDayActiveSessionMobileData;
    protected long mClientCurrentDayIntermediateTimeStamp;
    protected long mClientCurrentDayUsedTotalTime;
    protected long mClientDataLimitInBytes;
    protected int mClientDeviceType;
    protected String mClientEditedName;
    protected String mClientIpAddress;
    protected boolean mClientIsAutoHotspotDevice;
    protected boolean mClientIsConnected;
    protected boolean mClientIsDataPauseByTimeLimit;
    protected boolean mClientIsDataPausedFromUi;
    protected boolean mClientIsGuestDevice;
    protected long mClientLastElapsedTime;
    private String mClientMac;
    protected String mClientName;
    protected String mClientNsdName;
    protected long mClientRealTimeBytes;
    protected long mClientRealTimePackets;
    protected long mClientRecentConnectionTimeStamp;
    protected long mClientTimeLimitInMilliSec;
    protected long mClientUsedMobileData;
    protected String mDhcpDeviceType;
    protected String mDhcpManufacture;
    protected String mDhcpOsType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public enum DeviceNameType {
        DEFAULT(SoundTheme.Default),
        DHCP("DHCP"),
        NSD("NSD"),
        EDITED("Edited");

        private final String text;

        DeviceNameType(String str) {
            this.text = str;
        }
    }

    public enum DeviceIpType {
        UNKNOWN(LsConstants.TAG_UNKNOWN),
        STATIC("Static"),
        DYNAMIC("Dynamic");

        private final String text;

        DeviceIpType(String str) {
            this.text = str;
        }

        public static int getIpTypeInt(DeviceIpType deviceIpType) {
            int ordinal = deviceIpType.ordinal();
            if (ordinal != 1) {
                return ordinal != 2 ? -1 : 0;
            }
            return 1;
        }

        public static DeviceIpType getIpTypeFromInt(int i) {
            if (i == 0) {
                return DYNAMIC;
            }
            if (i == 1) {
                return STATIC;
            }
            return UNKNOWN;
        }
    }

    public SemWifiApClientDetails(String str, String str2, String str3, String str4, String str5, int i, long j, long j2, long j3, long j4, boolean z, boolean z2, boolean z3, String str6, String str7, String str8) {
        this.mDhcpOsType = "unknown";
        this.mDhcpDeviceType = "unknown";
        this.mDhcpManufacture = "unknown";
        this.mClientMac = str;
        this.mClientName = str2;
        this.mClientEditedName = str3;
        this.mClientNsdName = str4;
        this.mClientIpAddress = str5;
        this.mClientDeviceType = i;
        this.mClientDataLimitInBytes = j;
        this.mClientTimeLimitInMilliSec = j2;
        this.mClientUsedMobileData = j3;
        this.mClientCurrentDayUsedTotalTime = j4;
        this.mClientIsConnected = z;
        this.mClientIsDataPausedFromUi = false;
        this.mClientIsDataPauseByTimeLimit = false;
        this.mClientActiveSessionMobileData = 0L;
        this.mClientCurrentDayActiveSessionMobileData = 0L;
        this.mClientCurrentDayIntermediateTimeStamp = -1L;
        this.mClientRecentConnectionTimeStamp = System.currentTimeMillis();
        this.mClientLastElapsedTime = SystemClock.elapsedRealtime();
        this.mClientRealTimePackets = 0L;
        this.mClientRealTimeBytes = 0L;
        this.mClientIsGuestDevice = z3;
        this.mClientIsAutoHotspotDevice = z2;
        this.isCellularStream = false;
        if (!TextUtils.isEmpty(str6)) {
            this.mDhcpOsType = str6;
        }
        if (!TextUtils.isEmpty(str7)) {
            this.mDhcpDeviceType = str7;
        }
        if (TextUtils.isEmpty(str8)) {
            return;
        }
        this.mDhcpManufacture = str8;
    }

    public SemWifiApClientDetails(SemWifiApClientDetails semWifiApClientDetails) {
        this.mDhcpOsType = "unknown";
        this.mDhcpDeviceType = "unknown";
        this.mDhcpManufacture = "unknown";
        this.mClientName = semWifiApClientDetails.mClientName;
        this.mClientEditedName = semWifiApClientDetails.mClientEditedName;
        this.mClientNsdName = semWifiApClientDetails.mClientNsdName;
        this.mClientIpAddress = semWifiApClientDetails.mClientIpAddress;
        this.mClientMac = semWifiApClientDetails.mClientMac;
        this.mClientDeviceType = semWifiApClientDetails.mClientDeviceType;
        this.mClientDataLimitInBytes = semWifiApClientDetails.mClientDataLimitInBytes;
        this.mClientTimeLimitInMilliSec = semWifiApClientDetails.mClientTimeLimitInMilliSec;
        this.mClientIsDataPausedFromUi = semWifiApClientDetails.mClientIsDataPausedFromUi;
        this.mClientIsDataPauseByTimeLimit = semWifiApClientDetails.mClientIsDataPauseByTimeLimit;
        this.mClientCurrentDayActiveSessionMobileData = semWifiApClientDetails.mClientCurrentDayActiveSessionMobileData;
        this.mClientActiveSessionMobileData = semWifiApClientDetails.mClientActiveSessionMobileData;
        this.mClientUsedMobileData = semWifiApClientDetails.mClientUsedMobileData;
        this.mClientIsConnected = semWifiApClientDetails.mClientIsConnected;
        this.mClientCurrentDayIntermediateTimeStamp = semWifiApClientDetails.mClientCurrentDayIntermediateTimeStamp;
        this.mClientCurrentDayUsedTotalTime = semWifiApClientDetails.mClientCurrentDayUsedTotalTime;
        this.mClientRecentConnectionTimeStamp = semWifiApClientDetails.mClientRecentConnectionTimeStamp;
        this.mClientLastElapsedTime = semWifiApClientDetails.mClientLastElapsedTime;
        this.isCellularStream = semWifiApClientDetails.isCellularStream;
        this.mClientRealTimePackets = semWifiApClientDetails.mClientRealTimePackets;
        this.mClientRealTimeBytes = semWifiApClientDetails.mClientRealTimeBytes;
        this.mClientIsGuestDevice = semWifiApClientDetails.mClientIsGuestDevice;
        this.mClientIsAutoHotspotDevice = semWifiApClientDetails.mClientIsAutoHotspotDevice;
        this.mDhcpOsType = semWifiApClientDetails.mDhcpOsType;
        this.mDhcpDeviceType = semWifiApClientDetails.mDhcpDeviceType;
        this.mDhcpManufacture = semWifiApClientDetails.mDhcpManufacture;
    }

    private SemWifiApClientDetails(Parcel parcel) {
        this.mDhcpOsType = "unknown";
        this.mDhcpDeviceType = "unknown";
        this.mDhcpManufacture = "unknown";
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mClientName);
        parcel.writeString(this.mClientEditedName);
        parcel.writeString(this.mClientNsdName);
        parcel.writeString(this.mClientIpAddress);
        parcel.writeString(this.mClientMac);
        parcel.writeInt(this.mClientDeviceType);
        parcel.writeLong(this.mClientDataLimitInBytes);
        parcel.writeLong(this.mClientTimeLimitInMilliSec);
        parcel.writeBoolean(this.mClientIsConnected);
        parcel.writeBoolean(this.mClientIsDataPausedFromUi);
        parcel.writeBoolean(this.mClientIsDataPauseByTimeLimit);
        parcel.writeLong(this.mClientActiveSessionMobileData);
        parcel.writeLong(this.mClientUsedMobileData);
        parcel.writeLong(this.mClientCurrentDayActiveSessionMobileData);
        parcel.writeLong(this.mClientCurrentDayIntermediateTimeStamp);
        parcel.writeLong(this.mClientCurrentDayUsedTotalTime);
        parcel.writeLong(this.mClientRecentConnectionTimeStamp);
        parcel.writeLong(this.mClientLastElapsedTime);
        parcel.writeBoolean(this.isCellularStream);
        parcel.writeLong(this.mClientRealTimePackets);
        parcel.writeLong(this.mClientRealTimeBytes);
        parcel.writeBoolean(this.mClientIsGuestDevice);
        parcel.writeBoolean(this.mClientIsAutoHotspotDevice);
        parcel.writeString(this.mDhcpOsType);
        parcel.writeString(this.mDhcpDeviceType);
        parcel.writeString(this.mDhcpManufacture);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mClientMac, ((SemWifiApClientDetails) obj).mClientMac);
    }

    @Override // java.lang.Comparable
    public int compareTo(SemWifiApClientDetails semWifiApClientDetails) {
        if (getClientTodayTotalMobileDataUsage() < semWifiApClientDetails.getClientTodayTotalMobileDataUsage()) {
            return -1;
        }
        return getClientTodayTotalMobileDataUsage() > semWifiApClientDetails.getClientTodayTotalMobileDataUsage() ? 1 : 0;
    }

    private void readFromParcel(Parcel parcel) {
        this.mClientName = parcel.readString();
        this.mClientEditedName = parcel.readString();
        this.mClientNsdName = parcel.readString();
        this.mClientIpAddress = parcel.readString();
        this.mClientMac = parcel.readString();
        this.mClientDeviceType = parcel.readInt();
        this.mClientDataLimitInBytes = parcel.readLong();
        this.mClientTimeLimitInMilliSec = parcel.readLong();
        this.mClientIsConnected = parcel.readBoolean();
        this.mClientIsDataPausedFromUi = parcel.readBoolean();
        this.mClientIsDataPauseByTimeLimit = parcel.readBoolean();
        this.mClientActiveSessionMobileData = parcel.readLong();
        this.mClientUsedMobileData = parcel.readLong();
        this.mClientCurrentDayActiveSessionMobileData = parcel.readLong();
        this.mClientCurrentDayIntermediateTimeStamp = parcel.readLong();
        this.mClientCurrentDayUsedTotalTime = parcel.readLong();
        this.mClientRecentConnectionTimeStamp = parcel.readLong();
        this.mClientLastElapsedTime = parcel.readLong();
        this.isCellularStream = parcel.readBoolean();
        this.mClientRealTimePackets = parcel.readLong();
        this.mClientRealTimeBytes = parcel.readLong();
        this.mClientIsGuestDevice = parcel.readBoolean();
        this.mClientIsAutoHotspotDevice = parcel.readBoolean();
        this.mDhcpOsType = parcel.readString();
        this.mDhcpDeviceType = parcel.readString();
        this.mDhcpManufacture = parcel.readString();
    }

    public String toString() {
        return "{name=" + this.mClientName + ", editedName=" + getTruncatedEditedNameForLog() + ", nsdName = " + this.mClientNsdName + ", IP='" + getTruncatedIpAddress(this.mClientIpAddress) + "'  Type=" + DeviceType.getDeviceTypeAsString(this.mClientDeviceType) + ", MAC=" + getTruncatedMAC(this.mClientMac) + ", D.T.=" + this.mClientDataLimitInBytes + ", T.L.=" + this.mClientTimeLimitInMilliSec + ", isConn='" + this.mClientIsConnected + "', connDuration='" + getClientActiveSessionDuration() + "', pausedFromUi='" + this.mClientIsDataPausedFromUi + "', pausedByTimer=" + this.mClientIsDataPauseByTimeLimit + "', activeSessionData=" + this.mClientActiveSessionMobileData + "', todayData=" + getClientTodayTotalMobileDataUsage() + "', todayTime=" + getClientTodayTotalTime() + "', RtPackets='" + this.mClientRealTimePackets + "', RtBytes='" + this.mClientRealTimeBytes + "', guestSta='" + this.mClientIsGuestDevice + "', os='" + this.mDhcpOsType + "', dhcp_device_type='" + this.mDhcpDeviceType + "', manufacture='" + this.mDhcpManufacture + "'}\n";
    }

    private static String getTruncatedIpAddress(String str) {
        if (SemWifiManager.MHSDBG) {
            return str;
        }
        if (str == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        int i2 = 0;
        while (i < str.length() && (str.charAt(i) != '.' || (i2 = i2 + 1) != 2)) {
            stringBuffer.append("*");
            i++;
        }
        while (i < str.length()) {
            stringBuffer.append(str.charAt(i));
            i++;
        }
        return stringBuffer.toString();
    }

    private static String getTruncatedMAC(String str) {
        if (SemWifiManager.MHSDBG) {
            return str;
        }
        if (str == null) {
            return null;
        }
        return str.length() > 9 ? str.substring(9) : str;
    }

    public boolean isDeviceGuestClient() {
        return this.mClientIsGuestDevice;
    }

    public boolean isDeviceAutoHotspotClient() {
        return this.mClientIsAutoHotspotDevice;
    }

    public DeviceNameType getClientDeviceNameType() {
        if (!TextUtils.isEmpty(this.mClientEditedName)) {
            return DeviceNameType.EDITED;
        }
        if (!TextUtils.isEmpty(this.mClientNsdName)) {
            return DeviceNameType.NSD;
        }
        if (isMacString(this.mClientName)) {
            return DeviceNameType.DEFAULT;
        }
        return DeviceNameType.DHCP;
    }

    public String getClientDeviceName() {
        if (!TextUtils.isEmpty(this.mClientEditedName)) {
            return this.mClientEditedName;
        }
        if (!TextUtils.isEmpty(this.mClientNsdName)) {
            return this.mClientNsdName;
        }
        return this.mClientName;
    }

    public String getClientIpAddress() {
        return this.mClientIpAddress;
    }

    public String getClientMacAddress() {
        return this.mClientMac;
    }

    public int getDeviceType() {
        return this.mClientDeviceType;
    }

    public boolean isClientConnected() {
        return this.mClientIsConnected;
    }

    public long getClientDataLimit() {
        return this.mClientDataLimitInBytes;
    }

    public long getClientTimeLimit() {
        return this.mClientTimeLimitInMilliSec;
    }

    public long getClientActiveSessionMobileDataConsumed() {
        return this.mClientActiveSessionMobileData;
    }

    public long getClientTodayTotalMobileDataUsage() {
        return this.mClientCurrentDayActiveSessionMobileData + this.mClientUsedMobileData;
    }

    public boolean isClientDataPausedByUser() {
        return this.mClientIsDataPausedFromUi;
    }

    public boolean isClientDataPausedByDataLimit() {
        return this.isCellularStream && this.mClientDataLimitInBytes > 0 && getClientTodayTotalMobileDataUsage() >= this.mClientDataLimitInBytes;
    }

    public boolean isClientDataPauseByTimeLimit() {
        return this.mClientIsDataPauseByTimeLimit;
    }

    public boolean isClientInternetPaused() {
        return isClientDataPausedByDataLimit() || isClientDataPauseByTimeLimit() || isClientDataPausedByUser();
    }

    private long getClientTodayCounterTotalTime() {
        if (this.mClientCurrentDayIntermediateTimeStamp != -1) {
            return SystemClock.elapsedRealtime() - this.mClientCurrentDayIntermediateTimeStamp;
        }
        return 0L;
    }

    public long getClientTodayTotalTime() {
        return getClientTodayCounterTotalTime() + this.mClientCurrentDayUsedTotalTime;
    }

    public String getTruncatedEditedNameForLog() {
        if (this.mClientEditedName.length() > 32) {
            return this.mClientEditedName.substring(0, 32) + Session.TRUNCATE_STRING;
        }
        return this.mClientEditedName;
    }

    public long getClientRecentConnectionTimeStamp() {
        return this.mClientRecentConnectionTimeStamp;
    }

    public long getClientActiveSessionDuration() {
        if (this.mClientLastElapsedTime != -1) {
            return SystemClock.elapsedRealtime() - this.mClientLastElapsedTime;
        }
        return 0L;
    }

    public String getClientOsTypeFromDhcpFingerprint() {
        if (TextUtils.isEmpty(this.mClientNsdName)) {
            return !TextUtils.isEmpty(this.mDhcpOsType) ? this.mDhcpOsType : "unknown";
        }
        return "android";
    }

    public String getClientDeviceTypeFromDhcpFingerPrint() {
        if (TextUtils.isEmpty(this.mClientNsdName)) {
            return !TextUtils.isEmpty(this.mDhcpDeviceType) ? this.mDhcpDeviceType : "unknown";
        }
        return DeviceType.getDeviceTypeAsString(getDeviceType());
    }

    public String getClientManufactureFromDhcpFingerprint() {
        if (TextUtils.isEmpty(this.mClientNsdName)) {
            return !TextUtils.isEmpty(this.mDhcpManufacture) ? this.mDhcpManufacture : "unknown";
        }
        return "samsung";
    }

    public static final class DeviceType {
        public static final int DEVICE_TYPE_FLIP = 6;
        public static final int DEVICE_TYPE_FOLD = 2;
        public static final int DEVICE_TYPE_LAPTOP = 9;
        public static final int DEVICE_TYPE_MOBILE = 1;
        public static final int DEVICE_TYPE_SPEAKER = 5;
        public static final int DEVICE_TYPE_TABLET = 3;
        public static final int DEVICE_TYPE_TV = 4;
        public static final int DEVICE_TYPE_UNDEFINE = 0;
        public static final int DEVICE_TYPE_VST = 8;
        public static final int DEVICE_TYPE_WATCH = 7;

        public static boolean isValidDeviceType(int i) {
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    return true;
                default:
                    return false;
            }
        }

        public static String getDeviceTypeAsString(int i) {
            switch (i) {
                case 1:
                    return "phone";
                case 2:
                    return "fold";
                case 3:
                    return BnRConstants.DEVICETYPE_TABLET;
                case 4:
                    return "tv";
                case 5:
                    return AudioSystem.DEVICE_OUT_SPEAKER_NAME;
                case 6:
                    return "flip";
                case 7:
                    return "watch";
                case 8:
                    return "vst";
                case 9:
                    return "laptop";
                default:
                    return "unknown";
            }
        }
    }

    public boolean isMacString(String str) {
        return Pattern.compile(REGEX_MAC).matcher(str).find();
    }
}
