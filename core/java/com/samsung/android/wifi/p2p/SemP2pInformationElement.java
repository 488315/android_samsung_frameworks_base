package com.samsung.android.wifi.p2p;

import android.net.wifi.ScanResult;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

/* loaded from: classes6.dex */
public class SemP2pInformationElement {
    private static final int DISCOVERY_ATTR_ICON = 0;
    private static final int DISCOVERY_ATTR_SERVICE_DATA = 3;
    private static final int FW_INVITE_OUI = 61453;
    private static final int P2P_DEVICE_DISCOVERY_OUI = 61455;
    private static final int SCREEN_SHARING_ATTR_DEV_INFO = 0;
    private static final int SCREEN_SHARING_ATTR_DI_HASH = 3;
    private static final int SCREEN_SHARING_ATTR_EXTENDED_INFO = 8;
    private static final int SCREEN_SHARING_OUI = 61452;
    private static final String TAG = "SemP2pInformationElement";
    private int mSamsungDeviceType = 0;
    private int mScreenSharingInfo = 0;
    private int mScreenSharingExtendedInfo = 0;
    private boolean mFwInviteSupported = false;
    private String mServiceData = null;
    private String mScreenSharingDi = null;

    public SemP2pInformationElement(List<ScanResult.InformationElement> vendorElements) {
        for (ScanResult.InformationElement ie : vendorElements) {
            ByteBuffer data = ie.getBytes().order(ByteOrder.BIG_ENDIAN);
            int oui = data.getInt();
            switch (oui) {
                case 61452:
                    parseScreenSharingIe(data);
                    break;
                case 61453:
                    parseFwInviteIe();
                    break;
                case 61455:
                    parseDiscoveryIe(data);
                    break;
            }
        }
    }

    public int getSamsungDeviceType() {
        return this.mSamsungDeviceType;
    }

    public boolean isFwInviteSupported() {
        return this.mFwInviteSupported;
    }

    public int getScreenSharingInfo() {
        return this.mScreenSharingInfo;
    }

    public int getScreenSharingExtendedInfo() {
        return this.mScreenSharingExtendedInfo;
    }

    public String getScreenSharingDi() {
        return this.mScreenSharingDi;
    }

    public String getServiceData() {
        return this.mServiceData;
    }

    private void parseDiscoveryIe(ByteBuffer data) {
        while (data.remaining() > 1) {
            int attrId = data.get();
            int attrLen = data.get();
            if (attrLen == 0 || attrLen > data.remaining()) {
                return;
            }
            byte[] attrBytes = new byte[attrLen];
            data.get(attrBytes);
            switch (attrId) {
                case 0:
                    this.mSamsungDeviceType = parseHex(byteArrayToHexString(attrBytes));
                    break;
                case 3:
                    this.mServiceData = byteArrayToHexString(attrBytes);
                    break;
            }
        }
    }

    private void parseScreenSharingIe(ByteBuffer data) {
        while (data.remaining() > 1) {
            int attrId = data.get();
            int attrLen = data.getShort();
            if (attrLen == 0 || attrLen > data.remaining()) {
                return;
            }
            byte[] attrBytes = new byte[attrLen];
            data.get(attrBytes);
            switch (attrId) {
                case 0:
                    this.mScreenSharingInfo = parseHex(byteArrayToHexString(attrBytes));
                    break;
                case 3:
                    this.mScreenSharingDi = byteArrayToHexString(attrBytes);
                    break;
                case 8:
                    this.mScreenSharingExtendedInfo = parseHex(byteArrayToHexString(attrBytes));
                    break;
            }
        }
    }

    private void parseFwInviteIe() {
        this.mFwInviteSupported = true;
    }

    private String byteArrayToHexString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", Integer.valueOf(b & 255)));
        }
        return sb.toString();
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
