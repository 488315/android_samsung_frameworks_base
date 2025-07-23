package com.samsung.android.wifi.p2p;

import android.net.wifi.ScanResult;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class SemWifiP2pDevice implements Parcelable {
    public static final Parcelable.Creator<SemWifiP2pDevice> CREATOR = new Parcelable.Creator<SemWifiP2pDevice>() { // from class: com.samsung.android.wifi.p2p.SemWifiP2pDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiP2pDevice createFromParcel(Parcel parcel) {
            SemWifiP2pDevice semWifiP2pDevice = new SemWifiP2pDevice();
            semWifiP2pDevice.mDeviceName = parcel.readString();
            semWifiP2pDevice.mDeviceAddress = parcel.readString();
            semWifiP2pDevice.mScreenSharingInfo = parcel.readInt();
            semWifiP2pDevice.mScreenSharingExtendedInfo = parcel.readInt();
            semWifiP2pDevice.mScreenSharingDi = parcel.readString();
            semWifiP2pDevice.mDeviceIconAttr = parcel.readInt();
            semWifiP2pDevice.mServiceData = parcel.readString();
            semWifiP2pDevice.mSupportFwInvite = parcel.readInt() != 0;
            semWifiP2pDevice.mStatus = parcel.readInt();
            return semWifiP2pDevice;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiP2pDevice[] newArray(int i) {
            return new SemWifiP2pDevice[i];
        }
    };
    public static final int DEVICE_TYPE_AV = 7;
    public static final int DEVICE_TYPE_MONITOR = 23;
    public static final int DEVICE_TYPE_PC = 4;
    public static final int DEVICE_TYPE_PHONE = 1;
    public static final int DEVICE_TYPE_REFRIGERATOR = 9;
    public static final int DEVICE_TYPE_SIGNAGE = 8;
    public static final int DEVICE_TYPE_SMARTHOME = 19;
    public static final int DEVICE_TYPE_SPEAKER = 22;
    public static final int DEVICE_TYPE_TABLET = 2;
    public static final int DEVICE_TYPE_TV = 6;
    public static final int DEVICE_TYPE_VST = 50;
    private static final int FILTER_DEVICE_TYPE = 65280;
    private static final int FILTER_ICON_INDEX = 255;
    private static final String TAG = "SemWifiP2pDevice";
    private String mDeviceAddress;
    private int mDeviceIconAttr;
    private String mDeviceName;
    private String mScreenSharingDi;
    private int mScreenSharingExtendedInfo;
    private int mScreenSharingInfo;
    private String mServiceData;
    private int mStatus;
    private boolean mSupportFwInvite;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SemWifiP2pDevice() {
        this.mScreenSharingInfo = 0;
        this.mScreenSharingExtendedInfo = 0;
        this.mScreenSharingDi = null;
        this.mDeviceIconAttr = 0;
        this.mServiceData = null;
        this.mSupportFwInvite = false;
        this.mStatus = 4;
    }

    public SemWifiP2pDevice(String str, String str2) {
        this.mScreenSharingInfo = 0;
        this.mScreenSharingExtendedInfo = 0;
        this.mScreenSharingDi = null;
        this.mDeviceIconAttr = 0;
        this.mServiceData = null;
        this.mSupportFwInvite = false;
        this.mStatus = 4;
        this.mDeviceAddress = str;
        this.mDeviceName = str2;
    }

    public SemWifiP2pDevice(String str, String str2, List<ScanResult.InformationElement> list) {
        this.mScreenSharingInfo = 0;
        this.mScreenSharingExtendedInfo = 0;
        this.mScreenSharingDi = null;
        this.mDeviceIconAttr = 0;
        this.mServiceData = null;
        this.mSupportFwInvite = false;
        this.mStatus = 4;
        this.mDeviceAddress = str;
        this.mDeviceName = str2;
        if (list == null || list.isEmpty()) {
            return;
        }
        SemP2pInformationElement semP2pInformationElement = new SemP2pInformationElement(list);
        this.mDeviceIconAttr = semP2pInformationElement.getSamsungDeviceType();
        this.mScreenSharingInfo = semP2pInformationElement.getScreenSharingInfo();
        this.mScreenSharingExtendedInfo = semP2pInformationElement.getScreenSharingExtendedInfo();
        this.mServiceData = semP2pInformationElement.getServiceData();
        this.mSupportFwInvite = semP2pInformationElement.isFwInviteSupported();
        this.mScreenSharingDi = semP2pInformationElement.getScreenSharingDi();
    }

    public SemWifiP2pDevice(String str, String str2, String str3) {
        this.mScreenSharingInfo = 0;
        this.mScreenSharingExtendedInfo = 0;
        this.mScreenSharingDi = null;
        this.mDeviceIconAttr = 0;
        this.mServiceData = null;
        this.mSupportFwInvite = false;
        this.mStatus = 4;
        this.mDeviceAddress = str;
        this.mDeviceName = str2;
        if (TextUtils.isEmpty(str3)) {
            return;
        }
        updateAdditionalInfo(str3);
    }

    private void updateAdditionalInfo(String str) {
        Matcher matcher = Pattern.compile(" ss_dev_info=0x([0-9a-fA-F]+)").matcher(str);
        Matcher matcher2 = Pattern.compile(" icon=0x([0-9a-fA-F]*)").matcher(str);
        Matcher matcher3 = Pattern.compile(" service=0x([0-9a-fA-F]*)").matcher(str);
        Matcher matcher4 = Pattern.compile(" ss_hashed_di=0x([0-9a-fA-F]*)").matcher(str);
        if (matcher.find()) {
            this.mScreenSharingInfo = parseHex(matcher.group(1));
        }
        if (matcher2.find()) {
            this.mDeviceIconAttr = parseHex(matcher2.group(1));
        }
        if (matcher3.find()) {
            this.mServiceData = matcher3.group(1);
        }
        if (matcher4.find()) {
            this.mScreenSharingDi = matcher4.group(1);
        }
        if (str.contains(" fw_invite")) {
            this.mSupportFwInvite = true;
        }
    }

    public String getDeviceAddress() {
        return this.mDeviceAddress;
    }

    public int getScreenSharingInfo() {
        return this.mScreenSharingInfo;
    }

    public int getScreenSharingExtendedInfo() {
        return this.mScreenSharingExtendedInfo;
    }

    public String getScreenSharingHashedDi() {
        return this.mScreenSharingDi;
    }

    public int getDeviceType() {
        return (this.mDeviceIconAttr & 65280) >> 8;
    }

    public int getDeviceIcon() {
        return this.mDeviceIconAttr & 255;
    }

    public String getServiceData() {
        return this.mServiceData;
    }

    public boolean isFwInviteSupported() {
        return this.mSupportFwInvite;
    }

    public void updateStatus(int i) {
        this.mStatus = i;
    }

    public String toString() {
        return "Device: " + this.mDeviceName + "\n deviceAddress: " + this.mDeviceAddress + "\n screenSharingInfo: " + this.mScreenSharingInfo + "\n screenSharingExtendedInfo: " + this.mScreenSharingExtendedInfo + "\n deviceIconAttr: " + this.mDeviceIconAttr + "\n serviceData: " + this.mServiceData + "\n supportFwInvite: " + this.mSupportFwInvite + "\n status: " + this.mStatus;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mDeviceName);
        parcel.writeString(this.mDeviceAddress);
        parcel.writeInt(this.mScreenSharingInfo);
        parcel.writeInt(this.mScreenSharingExtendedInfo);
        parcel.writeString(this.mScreenSharingDi);
        parcel.writeInt(this.mDeviceIconAttr);
        parcel.writeString(this.mServiceData);
        parcel.writeInt(this.mSupportFwInvite ? 1 : 0);
        parcel.writeInt(this.mStatus);
    }

    private int parseHex(String str) {
        if (str.startsWith("0x") || str.startsWith("0X")) {
            str = str.substring(2);
        }
        try {
            return Integer.parseInt(str, 16);
        } catch (NumberFormatException unused) {
            Log.e(TAG, "Failed to parse hex string " + str);
            return 0;
        }
    }
}
