package com.samsung.android.wifi.p2p;

import android.net.wifi.ScanResult;
import android.util.Log;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Iterator;
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

    public SemP2pInformationElement(List<ScanResult.InformationElement> list) {
        Iterator<ScanResult.InformationElement> it = list.iterator();
        while (it.hasNext()) {
            ByteBuffer order = it.next().getBytes().order(ByteOrder.BIG_ENDIAN);
            switch (order.getInt()) {
                case 61452:
                    parseScreenSharingIe(order);
                    break;
                case 61453:
                    parseFwInviteIe();
                    break;
                case 61455:
                    parseDiscoveryIe(order);
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

    private void parseDiscoveryIe(ByteBuffer byteBuffer) {
        while (byteBuffer.remaining() > 1) {
            byte b = byteBuffer.get();
            int i = byteBuffer.get();
            if (i == 0 || i > byteBuffer.remaining()) {
                return;
            }
            byte[] bArr = new byte[i];
            byteBuffer.get(bArr);
            if (b == 0) {
                this.mSamsungDeviceType = parseHex(byteArrayToHexString(bArr));
            } else if (b == 3) {
                this.mServiceData = byteArrayToHexString(bArr);
            }
        }
    }

    private void parseScreenSharingIe(ByteBuffer byteBuffer) {
        while (byteBuffer.remaining() > 1) {
            byte b = byteBuffer.get();
            int i = byteBuffer.getShort();
            if (i == 0 || i > byteBuffer.remaining()) {
                return;
            }
            byte[] bArr = new byte[i];
            byteBuffer.get(bArr);
            if (b == 0) {
                this.mScreenSharingInfo = parseHex(byteArrayToHexString(bArr));
            } else if (b == 3) {
                this.mScreenSharingDi = byteArrayToHexString(bArr);
            } else if (b == 8) {
                this.mScreenSharingExtendedInfo = parseHex(byteArrayToHexString(bArr));
            }
        }
    }

    private void parseFwInviteIe() {
        this.mFwInviteSupported = true;
    }

    private String byteArrayToHexString(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%02x", Integer.valueOf(b & 255)));
        }
        return sb.toString();
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
