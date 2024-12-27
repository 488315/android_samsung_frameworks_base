package com.samsung.android.wifi;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.telecom.Logging.Session;
import android.text.TextUtils;
import android.text.format.DateFormat;

import com.samsung.android.audio.SoundTheme;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SemWifiApClientDetails implements Parcelable, Comparable<SemWifiApClientDetails> {
    private static final int MAX_DEVICE_NAME_LOG = 32;
    public static final String REGEX_MAC = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";
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
    public static String DEFAULT_CONNECTED_IP = "x.x.x.x";
    public static final Parcelable.Creator<SemWifiApClientDetails> CREATOR =
            new Parcelable.Creator<
                    SemWifiApClientDetails>() { // from class:
                                                // com.samsung.android.wifi.SemWifiApClientDetails.1
                @Override // android.os.Parcelable.Creator
                public SemWifiApClientDetails createFromParcel(Parcel source) {
                    return new SemWifiApClientDetails(source);
                }

                @Override // android.os.Parcelable.Creator
                public SemWifiApClientDetails[] newArray(int size) {
                    return new SemWifiApClientDetails[size];
                }
            };

    public enum DeviceNameType {
        DEFAULT(SoundTheme.Default),
        DHCP("DHCP"),
        NSD("NSD"),
        EDITED("Edited");

        private final String text;

        DeviceNameType(String text) {
            this.text = text;
        }
    }

    public enum DeviceIpType {
        UNKNOWN("Unknown"),
        STATIC("Static"),
        DYNAMIC("Dynamic");

        private final String text;

        DeviceIpType(String text) {
            this.text = text;
        }

        public static int getIpTypeInt(DeviceIpType ipType) {
            switch (ipType.ordinal()) {
                case 1:
                    return 1;
                case 2:
                    return 0;
                default:
                    return -1;
            }
        }

        public static DeviceIpType getIpTypeFromInt(int isStaticIp) {
            switch (isStaticIp) {
                case 0:
                    return DYNAMIC;
                case 1:
                    return STATIC;
                default:
                    return UNKNOWN;
            }
        }
    }

    public SemWifiApClientDetails(
            String mac,
            String name,
            String editedName,
            String nsdName,
            String ip,
            int deviceType,
            long dataLimit,
            long timeLimit,
            long currentDayUsedMobileDataUsage,
            long currentDayUsedTimeUsage,
            boolean isConnected,
            boolean isAutoHotspotClient,
            boolean isGuestClient) {
        this.mClientMac = mac;
        this.mClientName = name;
        this.mClientEditedName = editedName;
        this.mClientNsdName = nsdName;
        this.mClientIpAddress = ip;
        this.mClientDeviceType = deviceType;
        this.mClientDataLimitInBytes = dataLimit;
        this.mClientTimeLimitInMilliSec = timeLimit;
        this.mClientUsedMobileData = currentDayUsedMobileDataUsage;
        this.mClientCurrentDayUsedTotalTime = currentDayUsedTimeUsage;
        this.mClientIsConnected = isConnected;
        this.mClientIsDataPausedFromUi = false;
        this.mClientIsDataPauseByTimeLimit = false;
        this.mClientActiveSessionMobileData = 0L;
        this.mClientCurrentDayActiveSessionMobileData = 0L;
        this.mClientCurrentDayIntermediateTimeStamp = -1L;
        this.mClientRecentConnectionTimeStamp = System.currentTimeMillis();
        this.mClientLastElapsedTime = SystemClock.elapsedRealtime();
        this.mClientRealTimePackets = 0L;
        this.mClientRealTimeBytes = 0L;
        this.mClientIsGuestDevice = isGuestClient;
        this.mClientIsAutoHotspotDevice = isAutoHotspotClient;
        this.isCellularStream = false;
    }

    public SemWifiApClientDetails(SemWifiApClientDetails clientDetails) {
        this.mClientName = clientDetails.mClientName;
        this.mClientEditedName = clientDetails.mClientEditedName;
        this.mClientNsdName = clientDetails.mClientNsdName;
        this.mClientIpAddress = clientDetails.mClientIpAddress;
        this.mClientMac = clientDetails.mClientMac;
        this.mClientDeviceType = clientDetails.mClientDeviceType;
        this.mClientDataLimitInBytes = clientDetails.mClientDataLimitInBytes;
        this.mClientTimeLimitInMilliSec = clientDetails.mClientTimeLimitInMilliSec;
        this.mClientIsDataPausedFromUi = clientDetails.mClientIsDataPausedFromUi;
        this.mClientIsDataPauseByTimeLimit = clientDetails.mClientIsDataPauseByTimeLimit;
        this.mClientCurrentDayActiveSessionMobileData =
                clientDetails.mClientCurrentDayActiveSessionMobileData;
        this.mClientActiveSessionMobileData = clientDetails.mClientActiveSessionMobileData;
        this.mClientUsedMobileData = clientDetails.mClientUsedMobileData;
        this.mClientIsConnected = clientDetails.mClientIsConnected;
        this.mClientCurrentDayIntermediateTimeStamp =
                clientDetails.mClientCurrentDayIntermediateTimeStamp;
        this.mClientCurrentDayUsedTotalTime = clientDetails.mClientCurrentDayUsedTotalTime;
        this.mClientRecentConnectionTimeStamp = clientDetails.mClientRecentConnectionTimeStamp;
        this.mClientLastElapsedTime = clientDetails.mClientLastElapsedTime;
        this.isCellularStream = clientDetails.isCellularStream;
        this.mClientRealTimePackets = clientDetails.mClientRealTimePackets;
        this.mClientRealTimeBytes = clientDetails.mClientRealTimeBytes;
        this.mClientIsGuestDevice = clientDetails.mClientIsGuestDevice;
        this.mClientIsAutoHotspotDevice = clientDetails.mClientIsAutoHotspotDevice;
    }

    private SemWifiApClientDetails(Parcel in) {
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mClientName);
        dest.writeString(this.mClientEditedName);
        dest.writeString(this.mClientNsdName);
        dest.writeString(this.mClientIpAddress);
        dest.writeString(this.mClientMac);
        dest.writeInt(this.mClientDeviceType);
        dest.writeLong(this.mClientDataLimitInBytes);
        dest.writeLong(this.mClientTimeLimitInMilliSec);
        dest.writeBoolean(this.mClientIsConnected);
        dest.writeBoolean(this.mClientIsDataPausedFromUi);
        dest.writeBoolean(this.mClientIsDataPauseByTimeLimit);
        dest.writeLong(this.mClientActiveSessionMobileData);
        dest.writeLong(this.mClientUsedMobileData);
        dest.writeLong(this.mClientCurrentDayActiveSessionMobileData);
        dest.writeLong(this.mClientCurrentDayIntermediateTimeStamp);
        dest.writeLong(this.mClientCurrentDayUsedTotalTime);
        dest.writeLong(this.mClientRecentConnectionTimeStamp);
        dest.writeLong(this.mClientLastElapsedTime);
        dest.writeBoolean(this.isCellularStream);
        dest.writeLong(this.mClientRealTimePackets);
        dest.writeLong(this.mClientRealTimeBytes);
        dest.writeBoolean(this.mClientIsGuestDevice);
        dest.writeBoolean(this.mClientIsAutoHotspotDevice);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SemWifiApClientDetails other = (SemWifiApClientDetails) obj;
        return Objects.equals(this.mClientMac, other.mClientMac);
    }

    @Override // java.lang.Comparable
    public int compareTo(SemWifiApClientDetails o) {
        if (getClientTodayTotalMobileDataUsage() < o.getClientTodayTotalMobileDataUsage()) {
            return -1;
        }
        if (getClientTodayTotalMobileDataUsage() > o.getClientTodayTotalMobileDataUsage()) {
            return 1;
        }
        return 0;
    }

    private void readFromParcel(Parcel input) {
        this.mClientName = input.readString();
        this.mClientEditedName = input.readString();
        this.mClientNsdName = input.readString();
        this.mClientIpAddress = input.readString();
        this.mClientMac = input.readString();
        this.mClientDeviceType = input.readInt();
        this.mClientDataLimitInBytes = input.readLong();
        this.mClientTimeLimitInMilliSec = input.readLong();
        this.mClientIsConnected = input.readBoolean();
        this.mClientIsDataPausedFromUi = input.readBoolean();
        this.mClientIsDataPauseByTimeLimit = input.readBoolean();
        this.mClientActiveSessionMobileData = input.readLong();
        this.mClientUsedMobileData = input.readLong();
        this.mClientCurrentDayActiveSessionMobileData = input.readLong();
        this.mClientCurrentDayIntermediateTimeStamp = input.readLong();
        this.mClientCurrentDayUsedTotalTime = input.readLong();
        this.mClientRecentConnectionTimeStamp = input.readLong();
        this.mClientLastElapsedTime = input.readLong();
        this.isCellularStream = input.readBoolean();
        this.mClientRealTimePackets = input.readLong();
        this.mClientRealTimeBytes = input.readLong();
        this.mClientIsGuestDevice = input.readBoolean();
        this.mClientIsAutoHotspotDevice = input.readBoolean();
    }

    public String toString() {
        return "{name="
                + this.mClientName
                + ", editedName="
                + getTruncatedEditedNameForLog()
                + ", nsdName = "
                + this.mClientNsdName
                + ", IP='"
                + getTruncatedIpAddress(this.mClientIpAddress)
                + DateFormat.QUOTE
                + "  Type="
                + DeviceType.getDeviceTypeAsString(this.mClientDeviceType)
                + ", MAC="
                + getTruncatedMAC(this.mClientMac)
                + ", D.T.="
                + this.mClientDataLimitInBytes
                + ", T.L.="
                + this.mClientTimeLimitInMilliSec
                + ", isConn='"
                + this.mClientIsConnected
                + DateFormat.QUOTE
                + ", connDuration='"
                + getClientActiveSessionDuration()
                + DateFormat.QUOTE
                + ", pausedFromUi='"
                + this.mClientIsDataPausedFromUi
                + DateFormat.QUOTE
                + ", pausedByTimer="
                + this.mClientIsDataPauseByTimeLimit
                + "', activeSessionData="
                + this.mClientActiveSessionMobileData
                + "', todayData="
                + getClientTodayTotalMobileDataUsage()
                + "', todayTime="
                + getClientTodayTotalTime()
                + "', RtPackets='"
                + this.mClientRealTimePackets
                + DateFormat.QUOTE
                + ", RtBytes='"
                + this.mClientRealTimeBytes
                + DateFormat.QUOTE
                + ", guestSta='"
                + this.mClientIsGuestDevice
                + DateFormat.QUOTE
                + ", autoHotspotSta='"
                + this.mClientIsAutoHotspotDevice
                + DateFormat.QUOTE
                + "}\n";
    }

    private static String getTruncatedIpAddress(String ipAdd) {
        if (SemWifiManager.MHSDBG) {
            return ipAdd;
        }
        if (ipAdd == null) {
            return null;
        }
        StringBuffer trunIp = new StringBuffer();
        int dotCount = 0;
        int index = 0;
        while (index < ipAdd.length()
                && (ipAdd.charAt(index) != '.' || (dotCount = dotCount + 1) != 2)) {
            trunIp.append("*");
            index++;
        }
        while (index < ipAdd.length()) {
            trunIp.append(ipAdd.charAt(index));
            index++;
        }
        return trunIp.toString();
    }

    private static String getTruncatedMAC(String str) {
        if (SemWifiManager.MHSDBG) {
            return str;
        }
        if (str == null) {
            return null;
        }
        if (str.length() > 9) {
            return str.substring(9);
        }
        return str;
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
        return this.isCellularStream
                && this.mClientDataLimitInBytes > 0
                && getClientTodayTotalMobileDataUsage() >= this.mClientDataLimitInBytes;
    }

    public boolean isClientDataPauseByTimeLimit() {
        return this.mClientIsDataPauseByTimeLimit;
    }

    public boolean isClientInternetPaused() {
        return isClientDataPausedByDataLimit()
                || isClientDataPauseByTimeLimit()
                || isClientDataPausedByUser();
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

        public static String getDeviceTypeAsString(int deviceType) {
            switch (deviceType) {
                case 1:
                    return "MOBILE";
                case 2:
                    return "FOLD";
                case 3:
                    return "TABLET";
                case 4:
                    return "TV";
                case 5:
                    return "SPEAKER";
                case 6:
                    return "FLIP";
                case 7:
                    return "WATCH";
                case 8:
                    return "VST";
                case 9:
                    return "LAPTOP";
                default:
                    return "UNDEFINE";
            }
        }

        public static boolean isValidDeviceType(int deviceType) {
            switch (deviceType) {
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
    }

    public boolean isMacString(String stringToCheck) {
        Pattern pattern = Pattern.compile(REGEX_MAC);
        Matcher matcher = pattern.matcher(stringToCheck);
        if (matcher.find()) {
            return true;
        }
        return false;
    }
}
