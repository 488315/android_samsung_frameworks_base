package com.samsung.android.wifi.p2p;

import android.net.wifi.ScanResult;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SemWifiP2pDevice implements Parcelable {
    public static final Parcelable.Creator<SemWifiP2pDevice> CREATOR =
            new Parcelable.Creator<
                    SemWifiP2pDevice>() { // from class:
                                          // com.samsung.android.wifi.p2p.SemWifiP2pDevice.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemWifiP2pDevice createFromParcel(Parcel in) {
                    SemWifiP2pDevice device = new SemWifiP2pDevice();
                    device.mDeviceName = in.readString();
                    device.mDeviceAddress = in.readString();
                    device.mScreenSharingInfo = in.readInt();
                    device.mScreenSharingExtendedInfo = in.readInt();
                    device.mScreenSharingDi = in.readString();
                    device.mDeviceIconAttr = in.readInt();
                    device.mServiceData = in.readString();
                    device.mSupportFwInvite = in.readInt() != 0;
                    device.mStatus = in.readInt();
                    return device;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemWifiP2pDevice[] newArray(int size) {
                    return new SemWifiP2pDevice[size];
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

    private SemWifiP2pDevice() {
        this.mScreenSharingInfo = 0;
        this.mScreenSharingExtendedInfo = 0;
        this.mScreenSharingDi = null;
        this.mDeviceIconAttr = 0;
        this.mServiceData = null;
        this.mSupportFwInvite = false;
        this.mStatus = 4;
    }

    public SemWifiP2pDevice(String deviceAddress, String deviceName) {
        this.mScreenSharingInfo = 0;
        this.mScreenSharingExtendedInfo = 0;
        this.mScreenSharingDi = null;
        this.mDeviceIconAttr = 0;
        this.mServiceData = null;
        this.mSupportFwInvite = false;
        this.mStatus = 4;
        this.mDeviceAddress = deviceAddress;
        this.mDeviceName = deviceName;
    }

    public SemWifiP2pDevice(
            String deviceAddress,
            String deviceName,
            List<ScanResult.InformationElement> vendorElements) {
        this.mScreenSharingInfo = 0;
        this.mScreenSharingExtendedInfo = 0;
        this.mScreenSharingDi = null;
        this.mDeviceIconAttr = 0;
        this.mServiceData = null;
        this.mSupportFwInvite = false;
        this.mStatus = 4;
        this.mDeviceAddress = deviceAddress;
        this.mDeviceName = deviceName;
        if (vendorElements != null && !vendorElements.isEmpty()) {
            SemP2pInformationElement p2pIe = new SemP2pInformationElement(vendorElements);
            this.mDeviceIconAttr = p2pIe.getSamsungDeviceType();
            this.mScreenSharingInfo = p2pIe.getScreenSharingInfo();
            this.mScreenSharingExtendedInfo = p2pIe.getScreenSharingExtendedInfo();
            this.mServiceData = p2pIe.getServiceData();
            this.mSupportFwInvite = p2pIe.isFwInviteSupported();
            this.mScreenSharingDi = p2pIe.getScreenSharingDi();
        }
    }

    public SemWifiP2pDevice(String deviceAddress, String deviceName, String extInfo) {
        this.mScreenSharingInfo = 0;
        this.mScreenSharingExtendedInfo = 0;
        this.mScreenSharingDi = null;
        this.mDeviceIconAttr = 0;
        this.mServiceData = null;
        this.mSupportFwInvite = false;
        this.mStatus = 4;
        this.mDeviceAddress = deviceAddress;
        this.mDeviceName = deviceName;
        if (!TextUtils.isEmpty(extInfo)) {
            updateAdditionalInfo(extInfo);
        }
    }

    private void updateAdditionalInfo(String data) {
        Matcher matchForScreenSharing =
                Pattern.compile(" ss_dev_info=0x([0-9a-fA-F]+)").matcher(data);
        Matcher matchForIcon = Pattern.compile(" icon=0x([0-9a-fA-F]*)").matcher(data);
        Matcher matchForServiceData = Pattern.compile(" service=0x([0-9a-fA-F]*)").matcher(data);
        Matcher matchForScreenSharingDi =
                Pattern.compile(" ss_hashed_di=0x([0-9a-fA-F]*)").matcher(data);
        if (matchForScreenSharing.find()) {
            this.mScreenSharingInfo = parseHex(matchForScreenSharing.group(1));
        }
        if (matchForIcon.find()) {
            this.mDeviceIconAttr = parseHex(matchForIcon.group(1));
        }
        if (matchForServiceData.find()) {
            this.mServiceData = matchForServiceData.group(1);
        }
        if (matchForScreenSharingDi.find()) {
            this.mScreenSharingDi = matchForScreenSharingDi.group(1);
        }
        if (data.contains(" fw_invite")) {
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

    public void updateStatus(int status) {
        this.mStatus = status;
    }

    public String toString() {
        StringBuilder builder =
                new StringBuilder()
                        .append("Device: ")
                        .append(this.mDeviceName)
                        .append("\n deviceAddress: ")
                        .append(this.mDeviceAddress)
                        .append("\n screenSharingInfo: ")
                        .append(this.mScreenSharingInfo)
                        .append("\n screenSharingExtendedInfo: ")
                        .append(this.mScreenSharingExtendedInfo)
                        .append("\n deviceIconAttr: ")
                        .append(this.mDeviceIconAttr)
                        .append("\n serviceData: ")
                        .append(this.mServiceData)
                        .append("\n supportFwInvite: ")
                        .append(this.mSupportFwInvite)
                        .append("\n status: ")
                        .append(this.mStatus);
        return builder.toString();
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private int parseHex(String hexString) {
        if (hexString.startsWith("0x") || hexString.startsWith("0X")) {
            hexString = hexString.substring(2);
        }
        try {
            int num = Integer.parseInt(hexString, 16);
            return num;
        } catch (NumberFormatException e) {
            Log.e(TAG, "Failed to parse hex string " + hexString);
            return 0;
        }
    }
}
