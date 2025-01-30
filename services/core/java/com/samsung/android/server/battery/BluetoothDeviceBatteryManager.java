package com.samsung.android.server.battery;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Handler;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.location.gnss.hal.GnssNative;
import com.att.iqi.lib.metrics.hw.HwConstants;
import com.samsung.android.os.SemCompanionDeviceBatteryInfo;

/* loaded from: classes2.dex */
public class BluetoothDeviceBatteryManager {
    public DeviceBatteryInfoServiceInternal mBatteryInfoServiceInternal;
    public Context mContext;
    public Handler mHandler;

    enum CHARGE_STATE {
        CHARGE_STATE_DISCHARGE(0),
        CHARGE_STATE_WIRED_CHARGE(1),
        CHARGE_STATE_WIRELESS_CHARGE(2);

        private final int index;

        CHARGE_STATE(int i) {
            this.index = i;
        }
    }

    public BluetoothDeviceBatteryManager(Context context, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
    }

    public static String byteToString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append("0123456789abcdef".charAt((b & 240) >> 4));
            sb.append("0123456789abcdef".charAt(b & 15));
        }
        return sb.toString();
    }

    public static byte[] intToBytes(int i) {
        byte[] bArr = new byte[2];
        for (int i2 = 0; i2 < 2; i2++) {
            bArr[i2] = (byte) (i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
            i >>= 8;
        }
        return bArr;
    }

    public static int getDeviceType(BluetoothDevice bluetoothDevice) {
        try {
            ManufacturerData manufacturerData = new ManufacturerData(bluetoothDevice.semGetManufacturerData());
            int manufacturerType = manufacturerData.getManufacturerType();
            byte[] deviceId = manufacturerData.getDeviceId();
            Slog.i("BluetoothDeviceBatteryManager", "ManufacturerType : " + manufacturerType);
            Slog.i("BluetoothDeviceBatteryManager", "deviceId : " + byteToString(deviceId));
            if (deviceId != null) {
                byte b = deviceId[0];
                if (b == 1 && deviceId[1] == 1) {
                    Slog.i("BluetoothDeviceBatteryManager", "Type R170");
                    return 11;
                }
                if (b == 1 || b == 2 || b == 3) {
                    Slog.i("BluetoothDeviceBatteryManager", "Type Buds");
                    return 3;
                }
            }
        } catch (Exception e) {
            Slog.e("BluetoothDeviceBatteryManager", "getDeviceType exception: " + e);
        }
        return 1;
    }

    public static void printDeviceInfo(BluetoothDevice bluetoothDevice) {
        Slog.i("BluetoothDeviceBatteryManager", "# Alias(" + bluetoothDevice.getAlias() + ") / Address(" + DeviceBatteryInfoUtil.getAddressForLog(bluetoothDevice.getAddress()) + ")");
        StringBuilder sb = new StringBuilder();
        sb.append("# BatteryLevel : ");
        sb.append(bluetoothDevice.getBatteryLevel());
        Slog.i("BluetoothDeviceBatteryManager", sb.toString());
    }

    public final SemCompanionDeviceBatteryInfo createBatteryInfo(BluetoothDevice bluetoothDevice) {
        SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo = new SemCompanionDeviceBatteryInfo();
        semCompanionDeviceBatteryInfo.setAddress(bluetoothDevice.getAddress());
        semCompanionDeviceBatteryInfo.setDeviceName(bluetoothDevice.getAlias());
        semCompanionDeviceBatteryInfo.setDeviceType(getDeviceType(bluetoothDevice));
        semCompanionDeviceBatteryInfo.setBatteryLevel(bluetoothDevice.getBatteryLevel());
        return semCompanionDeviceBatteryInfo;
    }

    public final int convertChargeStateToStatus(CHARGE_STATE charge_state) {
        if (charge_state == CHARGE_STATE.CHARGE_STATE_DISCHARGE) {
            return 3;
        }
        return (charge_state == CHARGE_STATE.CHARGE_STATE_WIRED_CHARGE || charge_state == CHARGE_STATE.CHARGE_STATE_WIRELESS_CHARGE) ? 2 : 1;
    }

    public final boolean setBudsBatteryLevel(SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo, BluetoothDevice bluetoothDevice) {
        int i;
        int parseMetaData = parseMetaData(bluetoothDevice, SystemService.PHASE_DEVICE_SPECIFIC_SERVICES_READY);
        Slog.i("BluetoothDeviceBatteryManager", "# Left Level: " + parseMetaData);
        int parseMetaData2 = parseMetaData(bluetoothDevice, 521);
        Slog.i("BluetoothDeviceBatteryManager", "# Right Level: " + parseMetaData2);
        int parseMetaData3 = parseMetaData(bluetoothDevice, 522);
        Slog.i("BluetoothDeviceBatteryManager", "# Cradle Level: " + parseMetaData3);
        if (parseMetaData < 0) {
            i = parseMetaData;
            parseMetaData = parseMetaData2;
        } else if (parseMetaData2 < 0) {
            i = parseMetaData;
        } else {
            int i2 = parseMetaData - parseMetaData2;
            int i3 = i2 > 0 ? parseMetaData2 : parseMetaData;
            if (Math.abs(i2) <= 15) {
                i = -1;
                parseMetaData2 = -1;
            } else {
                i = parseMetaData;
            }
            parseMetaData = i3;
        }
        if (semCompanionDeviceBatteryInfo.getBatteryLevel() == parseMetaData && semCompanionDeviceBatteryInfo.getExtraBatteryLevelLeft() == i && semCompanionDeviceBatteryInfo.getExtraBatteryLevelRight() == parseMetaData2 && semCompanionDeviceBatteryInfo.getExtraBatteryLevelCradle() == parseMetaData3) {
            return false;
        }
        semCompanionDeviceBatteryInfo.setBatteryLevel(parseMetaData);
        semCompanionDeviceBatteryInfo.setExtraBatteryLevelLeft(i);
        semCompanionDeviceBatteryInfo.setExtraBatteryLevelRight(parseMetaData2);
        semCompanionDeviceBatteryInfo.setExtraBatteryLevelCradle(parseMetaData3);
        return true;
    }

    public final boolean setBudsBatteryStatus(SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo, BluetoothDevice bluetoothDevice) {
        int i;
        int i2;
        int i3;
        int parseMetaData = parseMetaData(bluetoothDevice, FrameworkStatsLog.DISPLAY_WAKE_REPORTED);
        Slog.i("BluetoothDeviceBatteryManager", "# Charge Feature :" + parseMetaData);
        if (parseMetaData != -1) {
            int parseMetaData2 = parseMetaData(bluetoothDevice, 552);
            if (parseMetaData2 >= 0) {
                CHARGE_STATE decodeChargeState = decodeChargeState(parseMetaData2);
                Slog.i("BluetoothDeviceBatteryManager", "# Left Charging: " + decodeChargeState);
                i2 = convertChargeStateToStatus(decodeChargeState);
            } else {
                i2 = 1;
            }
            int parseMetaData3 = parseMetaData(bluetoothDevice, 553);
            if (parseMetaData3 >= 0) {
                CHARGE_STATE decodeChargeState2 = decodeChargeState(parseMetaData3);
                Slog.i("BluetoothDeviceBatteryManager", "# Right Charging : " + decodeChargeState2);
                i3 = convertChargeStateToStatus(decodeChargeState2);
            } else {
                i3 = 1;
            }
            int parseMetaData4 = parseMetaData(bluetoothDevice, FrameworkStatsLog.PACKAGE_UNINSTALLATION_REPORTED);
            if (parseMetaData4 >= 0) {
                CHARGE_STATE decodeChargeState3 = decodeChargeState(parseMetaData4);
                Slog.i("BluetoothDeviceBatteryManager", "# Cradle Charging : " + decodeChargeState3);
                i = convertChargeStateToStatus(decodeChargeState3);
            } else {
                i = 1;
            }
        } else {
            i = 1;
            i2 = 1;
            i3 = 1;
        }
        if (semCompanionDeviceBatteryInfo.getBatteryStatus() == 1 && semCompanionDeviceBatteryInfo.getExtraBatteryStatusLeft() == i2 && semCompanionDeviceBatteryInfo.getExtraBatteryStatusRight() == i3 && semCompanionDeviceBatteryInfo.getExtraBatteryStatusCradle() == i) {
            return false;
        }
        semCompanionDeviceBatteryInfo.setBatteryStatus(1);
        semCompanionDeviceBatteryInfo.setExtraBatteryStatusLeft(i2);
        semCompanionDeviceBatteryInfo.setExtraBatteryStatusRight(i3);
        semCompanionDeviceBatteryInfo.setExtraBatteryStatusCradle(i);
        return true;
    }

    public final boolean setBudsBatteryInfo(SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo, BluetoothDevice bluetoothDevice) {
        boolean z = setBudsBatteryLevel(semCompanionDeviceBatteryInfo, bluetoothDevice);
        if (setBudsBatteryStatus(semCompanionDeviceBatteryInfo, bluetoothDevice)) {
            return true;
        }
        return z;
    }

    public final int parseMetaData(BluetoothDevice bluetoothDevice, int i) {
        byte[] semGetMetadata = bluetoothDevice.semGetMetadata(intToBytes(i));
        if (semGetMetadata != null && semGetMetadata.length > 3) {
            byte b = semGetMetadata[3];
            Slog.i("BluetoothDeviceBatteryManager", "parsedData : " + ((int) b));
            return b;
        }
        Slog.i("BluetoothDeviceBatteryManager", "parsedData error");
        return -1;
    }

    public final CHARGE_STATE decodeChargeState(int i) {
        int i2 = (i & 240) >> 4;
        if ((i & 15) != 1) {
            return CHARGE_STATE.CHARGE_STATE_DISCHARGE;
        }
        if (i2 == 2) {
            return CHARGE_STATE.CHARGE_STATE_WIRED_CHARGE;
        }
        if (i2 == 3) {
            Slog.i("BluetoothDeviceBatteryManager", "CHARGE_STATE_WIRELESS_CHARGE");
            return CHARGE_STATE.CHARGE_STATE_WIRELESS_CHARGE;
        }
        return CHARGE_STATE.CHARGE_STATE_WIRED_CHARGE;
    }

    public final void handleDeviceNameChanged(BluetoothDevice bluetoothDevice) {
        if (this.mBatteryInfoServiceInternal.containsBatteryInfo(bluetoothDevice.getAddress())) {
            String alias = bluetoothDevice.getAlias();
            String address = bluetoothDevice.getAddress();
            SemCompanionDeviceBatteryInfo deviceBatteryInfo = this.mBatteryInfoServiceInternal.getDeviceBatteryInfo(address);
            Slog.i("BluetoothDeviceBatteryManager", "address : " + DeviceBatteryInfoUtil.getAddressForLog(address) + " / alias : " + alias);
            deviceBatteryInfo.setDeviceName(alias);
            this.mBatteryInfoServiceInternal.sendBroadcast("com.samsung.battery.ACTION_BATTERY_INFO_CHANGED", deviceBatteryInfo);
        }
    }

    public final void handleBatteryLevelChanged(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice.getBatteryLevel() >= 0) {
            if (this.mBatteryInfoServiceInternal.containsBatteryInfo(bluetoothDevice.getAddress())) {
                SemCompanionDeviceBatteryInfo deviceBatteryInfo = this.mBatteryInfoServiceInternal.getDeviceBatteryInfo(bluetoothDevice.getAddress());
                if (deviceBatteryInfo.getDeviceType() != 1 && deviceBatteryInfo.getDeviceType() != 11) {
                    Slog.e("BluetoothDeviceBatteryManager", "type : " + deviceBatteryInfo.getDeviceType());
                    return;
                }
                if (deviceBatteryInfo.getBatteryLevel() != bluetoothDevice.getBatteryLevel()) {
                    deviceBatteryInfo.setBatteryLevel(bluetoothDevice.getBatteryLevel());
                    this.mBatteryInfoServiceInternal.sendBroadcast("com.samsung.battery.ACTION_BATTERY_INFO_CHANGED", deviceBatteryInfo);
                    return;
                }
                return;
            }
            int deviceType = getDeviceType(bluetoothDevice);
            if (deviceType != 1 && deviceType != 11) {
                Slog.e("BluetoothDeviceBatteryManager", "type : " + deviceType);
                return;
            }
            SemCompanionDeviceBatteryInfo createBatteryInfo = createBatteryInfo(bluetoothDevice);
            this.mBatteryInfoServiceInternal.addBatteryInfo(createBatteryInfo.getAddress(), createBatteryInfo);
            return;
        }
        this.mBatteryInfoServiceInternal.removeBatteryInfo(bluetoothDevice.getAddress());
    }

    public final int getChangeType(byte[] bArr) {
        boolean z;
        boolean z2;
        if (bArr == null || bArr.length < 4) {
            Slog.e("BluetoothDeviceBatteryManager", "parseSupportedFeatures :: DataPacket is too short.");
            return 0;
        }
        if ((((bArr[0] & 255) | ((bArr[1] & 255) << 8)) & GnssNative.GNSS_AIDING_TYPE_ALL) != 256) {
            int i = 0;
            z = false;
            z2 = false;
            while (i < bArr.length) {
                try {
                    int i2 = ((bArr[i] & 255) | ((bArr[i + 1] & 255) << 8)) & GnssNative.GNSS_AIDING_TYPE_ALL;
                    i += (bArr[i + 2] & 255) + 3;
                    switch (i2) {
                        case SystemService.PHASE_DEVICE_SPECIFIC_SERVICES_READY /* 520 */:
                        case 521:
                        case 522:
                            z = true;
                            break;
                        default:
                            switch (i2) {
                                case 552:
                                case 553:
                                case FrameworkStatsLog.PACKAGE_UNINSTALLATION_REPORTED /* 554 */:
                                    z2 = true;
                                    break;
                            }
                    }
                } catch (Exception e) {
                    Slog.e("BluetoothDeviceBatteryManager", "getChangeType exception: " + e);
                }
            }
        } else {
            z = false;
            z2 = false;
        }
        if (z && z2) {
            return 3;
        }
        if (z) {
            return 1;
        }
        return z2 ? 2 : 0;
    }

    public final void handleMetaDataChanged(BluetoothDevice bluetoothDevice, int i) {
        boolean budsBatteryStatus;
        if (this.mBatteryInfoServiceInternal.containsBatteryInfo(bluetoothDevice.getAddress())) {
            SemCompanionDeviceBatteryInfo deviceBatteryInfo = this.mBatteryInfoServiceInternal.getDeviceBatteryInfo(bluetoothDevice.getAddress());
            if (deviceBatteryInfo.getDeviceType() != 3) {
                Slog.e("BluetoothDeviceBatteryManager", "type : " + deviceBatteryInfo.getDeviceType());
                return;
            }
            if (i == 3) {
                budsBatteryStatus = setBudsBatteryInfo(deviceBatteryInfo, bluetoothDevice);
            } else if (i == 1) {
                budsBatteryStatus = setBudsBatteryLevel(deviceBatteryInfo, bluetoothDevice);
            } else {
                budsBatteryStatus = i == 2 ? setBudsBatteryStatus(deviceBatteryInfo, bluetoothDevice) : false;
            }
            Slog.i("BluetoothDeviceBatteryManager", "sendBroadcast : " + budsBatteryStatus);
            Slog.i("BluetoothDeviceBatteryManager", "level : " + deviceBatteryInfo.getBatteryLevel());
            if (deviceBatteryInfo.getBatteryLevel() < 0) {
                this.mBatteryInfoServiceInternal.removeBatteryInfo(bluetoothDevice.getAddress());
                return;
            } else {
                if (budsBatteryStatus) {
                    this.mBatteryInfoServiceInternal.sendBroadcast("com.samsung.battery.ACTION_BATTERY_INFO_CHANGED", deviceBatteryInfo);
                    return;
                }
                return;
            }
        }
        int deviceType = getDeviceType(bluetoothDevice);
        if (deviceType != 3) {
            Slog.e("BluetoothDeviceBatteryManager", "type : " + deviceType);
            return;
        }
        SemCompanionDeviceBatteryInfo createBatteryInfo = createBatteryInfo(bluetoothDevice);
        setBudsBatteryInfo(createBatteryInfo, bluetoothDevice);
        Slog.i("BluetoothDeviceBatteryManager", "level : " + createBatteryInfo.getBatteryLevel());
        if (createBatteryInfo.getBatteryLevel() < 0 && bluetoothDevice.getBatteryLevel() >= 0) {
            createBatteryInfo.setBatteryLevel(bluetoothDevice.getBatteryLevel());
        }
        if (createBatteryInfo.getBatteryLevel() >= 0) {
            this.mBatteryInfoServiceInternal.addBatteryInfo(createBatteryInfo.getAddress(), createBatteryInfo);
        }
    }

    public void systemServicesReady() {
        this.mBatteryInfoServiceInternal = (DeviceBatteryInfoServiceInternal) LocalServices.getService(DeviceBatteryInfoServiceInternal.class);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.device.action.BATTERY_LEVEL_CHANGED");
        intentFilter.addAction("com.samsung.bluetooth.device.action.META_DATA_CHANGED");
        intentFilter.addAction("com.samsung.bluetooth.device.action.SMEP_CONNECTION_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.device.action.ALIAS_CHANGED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.samsung.android.server.battery.BluetoothDeviceBatteryManager.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                try {
                    String action = intent.getAction();
                    Slog.i("BluetoothDeviceBatteryManager", "action: " + action);
                    if ("android.bluetooth.device.action.BATTERY_LEVEL_CHANGED".equals(action)) {
                        BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                        if (bluetoothDevice == null) {
                            Slog.e("BluetoothDeviceBatteryManager", "bluetoothDevice is null");
                            return;
                        } else {
                            BluetoothDeviceBatteryManager.printDeviceInfo(bluetoothDevice);
                            BluetoothDeviceBatteryManager.this.handleBatteryLevelChanged(bluetoothDevice);
                            return;
                        }
                    }
                    if ("com.samsung.bluetooth.device.action.META_DATA_CHANGED".equals(action)) {
                        BluetoothDevice bluetoothDevice2 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                        if (bluetoothDevice2 == null) {
                            Slog.e("BluetoothDeviceBatteryManager", "bluetoothDevice is null");
                            return;
                        }
                        BluetoothDeviceBatteryManager.printDeviceInfo(bluetoothDevice2);
                        int changeType = BluetoothDeviceBatteryManager.this.getChangeType(intent.getByteArrayExtra("com.samsung.bluetooth.device.extra.META_DATA"));
                        Slog.i("BluetoothDeviceBatteryManager", "# changeType: " + changeType);
                        if (changeType != 0) {
                            BluetoothDeviceBatteryManager.this.handleMetaDataChanged(bluetoothDevice2, changeType);
                            return;
                        }
                        return;
                    }
                    if ("com.samsung.bluetooth.device.action.SMEP_CONNECTION_STATE_CHANGED".equals(action)) {
                        int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
                        if (intExtra == 2) {
                            BluetoothDevice bluetoothDevice3 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                            if (bluetoothDevice3 == null) {
                                Slog.e("BluetoothDeviceBatteryManager", "bluetoothDevice is null");
                                return;
                            } else {
                                BluetoothDeviceBatteryManager.this.handleMetaDataChanged(bluetoothDevice3, 3);
                                return;
                            }
                        }
                        Slog.i("BluetoothDeviceBatteryManager", "state: " + intExtra);
                        return;
                    }
                    if ("android.bluetooth.device.action.ALIAS_CHANGED".equals(action)) {
                        BluetoothDevice bluetoothDevice4 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                        if (bluetoothDevice4 == null) {
                            Slog.e("BluetoothDeviceBatteryManager", "bluetoothDevice is null");
                            return;
                        } else {
                            BluetoothDeviceBatteryManager.this.handleDeviceNameChanged(bluetoothDevice4);
                            return;
                        }
                    }
                    if ("android.bluetooth.device.action.ACL_CONNECTED".equals(action)) {
                        BluetoothDevice bluetoothDevice5 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                        if (bluetoothDevice5 == null) {
                            Slog.e("BluetoothDeviceBatteryManager", "bluetoothDevice is null");
                            return;
                        } else {
                            BluetoothDeviceBatteryManager.printDeviceInfo(bluetoothDevice5);
                            return;
                        }
                    }
                    if ("android.bluetooth.device.action.ACL_DISCONNECTED".equals(action)) {
                        BluetoothDevice bluetoothDevice6 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                        if (bluetoothDevice6 == null) {
                            Slog.e("BluetoothDeviceBatteryManager", "bluetoothDevice is null");
                            return;
                        }
                        BluetoothDeviceBatteryManager.printDeviceInfo(bluetoothDevice6);
                        String address = bluetoothDevice6.getAddress();
                        if (BluetoothDeviceBatteryManager.this.mBatteryInfoServiceInternal.containsBatteryInfo(address)) {
                            SemCompanionDeviceBatteryInfo deviceBatteryInfo = BluetoothDeviceBatteryManager.this.mBatteryInfoServiceInternal.getDeviceBatteryInfo(address);
                            if (deviceBatteryInfo.getDeviceType() != 5 && deviceBatteryInfo.getDeviceType() != 12) {
                                if (deviceBatteryInfo.getDeviceType() == 3) {
                                    int intExtra2 = intent.getIntExtra("android.bluetooth.device.extra.TRANSPORT", Integer.MIN_VALUE);
                                    Slog.i("BluetoothDeviceBatteryManager", "transport: " + intExtra2);
                                    if (intExtra2 == 1) {
                                        BluetoothDeviceBatteryManager.this.mBatteryInfoServiceInternal.removeBatteryInfo(address);
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                            Slog.i("BluetoothDeviceBatteryManager", "Remove spen battery info");
                            BluetoothDeviceBatteryManager.this.mBatteryInfoServiceInternal.removeBatteryInfo(address);
                        }
                    }
                } catch (Exception e) {
                    Slog.e("BluetoothDeviceBatteryManager", "exception occurred : " + e);
                }
            }
        }, intentFilter, null, this.mHandler);
    }

    public class ManufacturerData {
        public int MANUFACTURER_OFFSET_OLD_SERVICE_ID = 5;
        public int MANUFACTURER_OFFSET_OLD_DEVICE_ID = 7;
        public int MANUFACTURER_OFFSET_OLD_DEVICE_TYPE = 10;
        public int MANUFACTURER_OFFSET_OLD_SAMSUNG_GALAXY_WATCH_DEVICE_TYPE = 13;
        public int MANUFACTURER_OFFSET_SS_SERVICE_ID = 5;
        public int MANUFACTURER_OFFSET_SS_ASSOCIATED_SERVICE_ID = 7;
        public int MANUFACTURER_OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA = 31;
        public int MANUFACTURER_OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA_DEVICE_ID = 1;
        public int MANUFACTURER_OFFSET_SS_LE_FEATURES = 8;
        public int MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH = 8;
        public int MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_DEVICE_ID = 1;
        public int MANUFACTURER_LENGTH_SS_LE_PACKET_NUMBER = 1;
        public int MANUFACTURER_LENGTH_SS_LE_PROXIMITY = 2;
        public int MANUFACTURER_LENGTH_SS_LE_DEVICE = 6;
        public int MANUFACTURER_LENGTH_SS_LE_CONNECTIVITY = 18;
        public int MANUFACTURER_LENGTH_SS_LE_ASSOCIATED_SERVICE_DATA = 5;
        public int mManufacturerType = 0;
        public byte[] mManufacturerRawData = null;
        public byte[] mDeviceId = new byte[2];

        public ManufacturerData(byte[] bArr) {
            setManufacturerRawData(bArr);
            setManufacturerType(bArr);
            setDeviceId(bArr);
        }

        public byte[] getDeviceId() {
            return this.mDeviceId;
        }

        public final void setDeviceId(byte[] bArr) {
            try {
                int i = this.mManufacturerType;
                if (i == 1) {
                    int length = bArr.length;
                    int i2 = this.MANUFACTURER_OFFSET_OLD_SAMSUNG_GALAXY_WATCH_DEVICE_TYPE;
                    if (length >= i2 + 2 && (bArr[this.MANUFACTURER_OFFSET_OLD_DEVICE_TYPE] & 255) == 255) {
                        setDeviceId(bArr, i2);
                    } else {
                        setDeviceId(bArr, this.MANUFACTURER_OFFSET_OLD_DEVICE_ID);
                    }
                } else if (i != 2) {
                    if (i == 3 && isSupportFeature(HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED)) {
                        setDeviceId(bArr, this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH + this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_DEVICE_ID);
                    }
                } else {
                    int i3 = this.MANUFACTURER_OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA;
                    int i4 = bArr[i3] & 255;
                    if (i4 > 0 && bArr.length > i4 + i3) {
                        setDeviceId(bArr, i3 + this.MANUFACTURER_OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA_DEVICE_ID);
                    }
                }
            } catch (Exception e) {
                Slog.e("BluetoothDeviceBatteryManager", "setDeviceId exception: " + e);
            }
        }

        public final void setManufacturerRawData(byte[] bArr) {
            this.mManufacturerRawData = bArr;
        }

        public int getManufacturerType() {
            return this.mManufacturerType;
        }

        public final void setManufacturerType(byte[] bArr) {
            int i;
            if (bArr == null || bArr.length < 9) {
                this.mManufacturerType = 0;
                return;
            }
            int i2 = this.MANUFACTURER_OFFSET_OLD_SERVICE_ID;
            if (bArr[i2] == 0 && bArr[i2 + 1] == 2) {
                this.mManufacturerType = 1;
                return;
            }
            byte b = bArr[this.MANUFACTURER_OFFSET_SS_SERVICE_ID];
            if (b == 9 && bArr[this.MANUFACTURER_OFFSET_SS_ASSOCIATED_SERVICE_ID] == 0) {
                this.mManufacturerType = 2;
                return;
            }
            if (b == 9 && bArr[this.MANUFACTURER_OFFSET_SS_ASSOCIATED_SERVICE_ID] == 2) {
                this.mManufacturerType = 3;
                int i3 = this.MANUFACTURER_OFFSET_SS_LE_FEATURES;
                byte b2 = bArr[i3];
                int i4 = i3 + 1;
                for (int i5 = 0; i5 < 5; i5++) {
                    byte b3 = (byte) (((byte) (1 << i5)) & b2);
                    if (b3 == 1) {
                        i = this.MANUFACTURER_LENGTH_SS_LE_PACKET_NUMBER;
                    } else if (b3 == 2) {
                        i = this.MANUFACTURER_LENGTH_SS_LE_PROXIMITY;
                    } else if (b3 == 4) {
                        i = this.MANUFACTURER_LENGTH_SS_LE_DEVICE;
                    } else if (b3 == 8) {
                        i = this.MANUFACTURER_LENGTH_SS_LE_CONNECTIVITY;
                    } else if (b3 == 16) {
                        this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH = i4;
                        i = bArr[i4] + 1;
                        this.MANUFACTURER_LENGTH_SS_LE_ASSOCIATED_SERVICE_DATA = i;
                    }
                    i4 += i;
                }
                return;
            }
            this.mManufacturerType = 0;
        }

        public final boolean isSupportFeature(byte b) {
            byte[] bArr;
            try {
                if (this.mManufacturerType == 3 && (bArr = this.mManufacturerRawData) != null) {
                    return (bArr[this.MANUFACTURER_OFFSET_SS_LE_FEATURES] & b) == b;
                }
                return false;
            } catch (Exception e) {
                Slog.e("BluetoothDeviceBatteryManager", "isSupportFeature exception: " + e);
                return false;
            }
        }

        public final void setDeviceId(byte[] bArr, int i) {
            System.arraycopy(bArr, i, this.mDeviceId, 0, 2);
        }
    }
}
