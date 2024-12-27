package com.samsung.android.server.battery;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Handler;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.samsung.android.os.SemCompanionDeviceBatteryInfo;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class BluetoothDeviceBatteryManager {
    public DeviceBatteryInfoService mBatteryInfoServiceInternal;
    public Context mContext;
    public Handler mHandler;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    enum CHARGE_STATE {
        CHARGE_STATE_DISCHARGE("CHARGE_STATE_DISCHARGE"),
        CHARGE_STATE_WIRED_CHARGE("CHARGE_STATE_WIRED_CHARGE"),
        CHARGE_STATE_WIRELESS_CHARGE("CHARGE_STATE_WIRELESS_CHARGE");

        private final int index;

        CHARGE_STATE(String str) {
            this.index = r2;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ManufacturerData {
        public final int MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH;
        public final byte[] mDeviceId = new byte[2];
        public final byte[] mManufacturerRawData;
        public final int mManufacturerType;

        /* JADX WARN: Removed duplicated region for block: B:15:0x0075  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x00b7 A[Catch: Exception -> 0x008f, TryCatch #0 {Exception -> 0x008f, blocks: (B:12:0x006f, B:24:0x0086, B:27:0x0092, B:32:0x00a4, B:34:0x00ab, B:36:0x00af, B:37:0x00b7, B:39:0x00bc, B:41:0x00c3, B:42:0x00cb, B:19:0x007d, B:21:0x0081), top: B:11:0x006f, inners: #1 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public ManufacturerData(byte[] r14) {
            /*
                Method dump skipped, instructions count: 216
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.server.battery.BluetoothDeviceBatteryManager.ManufacturerData.<init>(byte[]):void");
        }
    }

    /* renamed from: -$$Nest$mhandleBatteryLevelChanged, reason: not valid java name */
    public static void m1222$$Nest$mhandleBatteryLevelChanged(BluetoothDeviceBatteryManager bluetoothDeviceBatteryManager, BluetoothDevice bluetoothDevice) {
        bluetoothDeviceBatteryManager.getClass();
        if (bluetoothDevice.getBatteryLevel() < 0) {
            bluetoothDeviceBatteryManager.mBatteryInfoServiceInternal.removeBatteryInfo(bluetoothDevice.getAddress());
            return;
        }
        if (!bluetoothDeviceBatteryManager.mBatteryInfoServiceInternal.containsBatteryInfo(bluetoothDevice.getAddress())) {
            int deviceType = getDeviceType(bluetoothDevice);
            if (deviceType != 1 && deviceType != 11) {
                NandswapManager$$ExternalSyntheticOutline0.m(deviceType, "type : ", "BluetoothDeviceBatteryManager");
                return;
            } else {
                SemCompanionDeviceBatteryInfo createBatteryInfo = createBatteryInfo(bluetoothDevice);
                bluetoothDeviceBatteryManager.mBatteryInfoServiceInternal.addBatteryInfo(createBatteryInfo.getAddress(), createBatteryInfo);
                return;
            }
        }
        SemCompanionDeviceBatteryInfo deviceBatteryInfo = bluetoothDeviceBatteryManager.mBatteryInfoServiceInternal.getDeviceBatteryInfo(bluetoothDevice.getAddress());
        if (deviceBatteryInfo.getDeviceType() != 1 && deviceBatteryInfo.getDeviceType() != 11) {
            Slog.e("BluetoothDeviceBatteryManager", "type : " + deviceBatteryInfo.getDeviceType());
        } else if (deviceBatteryInfo.getBatteryLevel() != bluetoothDevice.getBatteryLevel()) {
            deviceBatteryInfo.setBatteryLevel(bluetoothDevice.getBatteryLevel());
            bluetoothDeviceBatteryManager.mBatteryInfoServiceInternal.sendBroadcast("com.samsung.battery.ACTION_BATTERY_INFO_CHANGED", deviceBatteryInfo);
        }
    }

    /* renamed from: -$$Nest$mhandleDeviceNameChanged, reason: not valid java name */
    public static void m1223$$Nest$mhandleDeviceNameChanged(BluetoothDeviceBatteryManager bluetoothDeviceBatteryManager, BluetoothDevice bluetoothDevice) {
        if (bluetoothDeviceBatteryManager.mBatteryInfoServiceInternal.containsBatteryInfo(bluetoothDevice.getAddress())) {
            String alias = bluetoothDevice.getAlias();
            String address = bluetoothDevice.getAddress();
            SemCompanionDeviceBatteryInfo deviceBatteryInfo = bluetoothDeviceBatteryManager.mBatteryInfoServiceInternal.getDeviceBatteryInfo(address);
            Slog.i("BluetoothDeviceBatteryManager", "address : " + DeviceBatteryInfoUtil.getAddressForLog(address) + " / alias : " + alias);
            deviceBatteryInfo.setDeviceName(alias);
            bluetoothDeviceBatteryManager.mBatteryInfoServiceInternal.sendBroadcast("com.samsung.battery.ACTION_BATTERY_INFO_CHANGED", deviceBatteryInfo);
        }
    }

    /* renamed from: -$$Nest$mhandleMetaDataChanged, reason: not valid java name */
    public static void m1224$$Nest$mhandleMetaDataChanged(BluetoothDeviceBatteryManager bluetoothDeviceBatteryManager, BluetoothDevice bluetoothDevice, int i) {
        if (!bluetoothDeviceBatteryManager.mBatteryInfoServiceInternal.containsBatteryInfo(bluetoothDevice.getAddress())) {
            int deviceType = getDeviceType(bluetoothDevice);
            if (deviceType != 3) {
                NandswapManager$$ExternalSyntheticOutline0.m(deviceType, "type : ", "BluetoothDeviceBatteryManager");
                return;
            }
            SemCompanionDeviceBatteryInfo createBatteryInfo = createBatteryInfo(bluetoothDevice);
            setBudsBatteryLevel(createBatteryInfo, bluetoothDevice);
            setBudsBatteryStatus(createBatteryInfo, bluetoothDevice);
            Slog.i("BluetoothDeviceBatteryManager", "level : " + createBatteryInfo.getBatteryLevel());
            if (createBatteryInfo.getBatteryLevel() < 0 && bluetoothDevice.getBatteryLevel() >= 0) {
                createBatteryInfo.setBatteryLevel(bluetoothDevice.getBatteryLevel());
            }
            if (createBatteryInfo.getBatteryLevel() >= 0) {
                bluetoothDeviceBatteryManager.mBatteryInfoServiceInternal.addBatteryInfo(createBatteryInfo.getAddress(), createBatteryInfo);
                return;
            }
            return;
        }
        SemCompanionDeviceBatteryInfo deviceBatteryInfo = bluetoothDeviceBatteryManager.mBatteryInfoServiceInternal.getDeviceBatteryInfo(bluetoothDevice.getAddress());
        if (deviceBatteryInfo.getDeviceType() != 3) {
            Slog.e("BluetoothDeviceBatteryManager", "type : " + deviceBatteryInfo.getDeviceType());
            return;
        }
        if (i == 3) {
            r2 = setBudsBatteryLevel(deviceBatteryInfo, bluetoothDevice);
            if (setBudsBatteryStatus(deviceBatteryInfo, bluetoothDevice)) {
                r2 = true;
            }
        } else if (i == 1) {
            r2 = setBudsBatteryLevel(deviceBatteryInfo, bluetoothDevice);
        } else if (i == 2) {
            r2 = setBudsBatteryStatus(deviceBatteryInfo, bluetoothDevice);
        }
        Slog.i("BluetoothDeviceBatteryManager", "sendBroadcast : " + r2);
        Slog.i("BluetoothDeviceBatteryManager", "level : " + deviceBatteryInfo.getBatteryLevel());
        if (deviceBatteryInfo.getBatteryLevel() < 0) {
            bluetoothDeviceBatteryManager.mBatteryInfoServiceInternal.removeBatteryInfo(bluetoothDevice.getAddress());
        } else if (r2) {
            bluetoothDeviceBatteryManager.mBatteryInfoServiceInternal.sendBroadcast("com.samsung.battery.ACTION_BATTERY_INFO_CHANGED", deviceBatteryInfo);
        }
    }

    /* renamed from: -$$Nest$smprintDeviceInfo, reason: not valid java name */
    public static void m1225$$Nest$smprintDeviceInfo(BluetoothDevice bluetoothDevice) {
        Slog.i("BluetoothDeviceBatteryManager", "# Alias(" + bluetoothDevice.getAlias() + ") / Address(" + DeviceBatteryInfoUtil.getAddressForLog(bluetoothDevice.getAddress()) + ")");
        StringBuilder sb = new StringBuilder("# BatteryLevel : ");
        sb.append(bluetoothDevice.getBatteryLevel());
        Slog.i("BluetoothDeviceBatteryManager", sb.toString());
    }

    public static int convertChargeStateToStatus(CHARGE_STATE charge_state) {
        if (charge_state == CHARGE_STATE.CHARGE_STATE_DISCHARGE) {
            return 3;
        }
        return (charge_state == CHARGE_STATE.CHARGE_STATE_WIRED_CHARGE || charge_state == CHARGE_STATE.CHARGE_STATE_WIRELESS_CHARGE) ? 2 : 1;
    }

    public static SemCompanionDeviceBatteryInfo createBatteryInfo(BluetoothDevice bluetoothDevice) {
        SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo = new SemCompanionDeviceBatteryInfo();
        semCompanionDeviceBatteryInfo.setAddress(bluetoothDevice.getAddress());
        semCompanionDeviceBatteryInfo.setDeviceName(bluetoothDevice.getAlias());
        semCompanionDeviceBatteryInfo.setDeviceType(getDeviceType(bluetoothDevice));
        semCompanionDeviceBatteryInfo.setBatteryLevel(bluetoothDevice.getBatteryLevel());
        return semCompanionDeviceBatteryInfo;
    }

    public static CHARGE_STATE decodeChargeState(int i) {
        int i2 = (i & 240) >> 4;
        if ((i & 15) != 1) {
            return CHARGE_STATE.CHARGE_STATE_DISCHARGE;
        }
        CHARGE_STATE charge_state = CHARGE_STATE.CHARGE_STATE_WIRED_CHARGE;
        if (i2 == 2 || i2 != 3) {
            return charge_state;
        }
        Slog.i("BluetoothDeviceBatteryManager", "CHARGE_STATE_WIRELESS_CHARGE");
        return CHARGE_STATE.CHARGE_STATE_WIRELESS_CHARGE;
    }

    public static int getDeviceType(BluetoothDevice bluetoothDevice) {
        String sb;
        try {
            ManufacturerData manufacturerData = new ManufacturerData(bluetoothDevice.semGetManufacturerData());
            int i = manufacturerData.mManufacturerType;
            byte[] bArr = manufacturerData.mDeviceId;
            Slog.i("BluetoothDeviceBatteryManager", "ManufacturerType : " + i);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("deviceId : ");
            if (bArr == null) {
                sb = null;
            } else {
                StringBuilder sb3 = new StringBuilder(bArr.length * 2);
                for (byte b : bArr) {
                    sb3.append("0123456789abcdef".charAt((b & 240) >> 4));
                    sb3.append("0123456789abcdef".charAt(b & 15));
                }
                sb = sb3.toString();
            }
            sb2.append(sb);
            Slog.i("BluetoothDeviceBatteryManager", sb2.toString());
            if (bArr != null) {
                byte b2 = bArr[0];
                if (b2 == 1 && bArr[1] == 1) {
                    Slog.i("BluetoothDeviceBatteryManager", "Type R170");
                    return 11;
                }
                if (b2 == 1 || b2 == 2 || b2 == 3) {
                    Slog.i("BluetoothDeviceBatteryManager", "Type Buds");
                    return 3;
                }
            }
        } catch (Exception e) {
            BootReceiver$$ExternalSyntheticOutline0.m(e, "getDeviceType exception: ", "BluetoothDeviceBatteryManager");
        }
        return 1;
    }

    public static int parseMetaData(BluetoothDevice bluetoothDevice, int i) {
        byte[] bArr = new byte[2];
        for (int i2 = 0; i2 < 2; i2++) {
            bArr[i2] = (byte) (i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
            i >>= 8;
        }
        byte[] semGetMetadata = bluetoothDevice.semGetMetadata(bArr);
        if (semGetMetadata == null || semGetMetadata.length <= 3) {
            Slog.i("BluetoothDeviceBatteryManager", "parsedData error");
            return -1;
        }
        byte b = semGetMetadata[3];
        HermesService$3$$ExternalSyntheticOutline0.m(b, "parsedData : ", "BluetoothDeviceBatteryManager");
        return b;
    }

    public static boolean setBudsBatteryLevel(SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo, BluetoothDevice bluetoothDevice) {
        int i;
        int i2;
        int parseMetaData = parseMetaData(bluetoothDevice, SystemService.PHASE_DEVICE_SPECIFIC_SERVICES_READY);
        Slog.i("BluetoothDeviceBatteryManager", "# Left Level: " + parseMetaData);
        int parseMetaData2 = parseMetaData(bluetoothDevice, 521);
        Slog.i("BluetoothDeviceBatteryManager", "# Right Level: " + parseMetaData2);
        int parseMetaData3 = parseMetaData(bluetoothDevice, 522);
        HermesService$3$$ExternalSyntheticOutline0.m(parseMetaData3, "# Cradle Level: ", "BluetoothDeviceBatteryManager");
        if (parseMetaData < 0) {
            i = parseMetaData2;
            i2 = parseMetaData;
            parseMetaData = i;
        } else if (parseMetaData2 < 0) {
            i = parseMetaData2;
            i2 = parseMetaData;
        } else {
            int i3 = parseMetaData - parseMetaData2;
            int i4 = i3 > 0 ? parseMetaData2 : parseMetaData;
            if (Math.abs(i3) <= 15) {
                i2 = -1;
                i = -1;
            } else {
                i = parseMetaData2;
                i2 = parseMetaData;
            }
            parseMetaData = i4;
        }
        if (semCompanionDeviceBatteryInfo.getBatteryLevel() == parseMetaData && semCompanionDeviceBatteryInfo.getExtraBatteryLevelLeft() == i2 && semCompanionDeviceBatteryInfo.getExtraBatteryLevelRight() == i && semCompanionDeviceBatteryInfo.getExtraBatteryLevelCradle() == parseMetaData3) {
            return false;
        }
        semCompanionDeviceBatteryInfo.setBatteryLevel(parseMetaData);
        semCompanionDeviceBatteryInfo.setExtraBatteryLevelLeft(i2);
        semCompanionDeviceBatteryInfo.setExtraBatteryLevelRight(i);
        semCompanionDeviceBatteryInfo.setExtraBatteryLevelCradle(parseMetaData3);
        return true;
    }

    public static boolean setBudsBatteryStatus(SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo, BluetoothDevice bluetoothDevice) {
        int i;
        int i2;
        int i3;
        int parseMetaData = parseMetaData(bluetoothDevice, FrameworkStatsLog.DISPLAY_WAKE_REPORTED);
        HermesService$3$$ExternalSyntheticOutline0.m(parseMetaData, "# Charge Feature :", "BluetoothDeviceBatteryManager");
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
}
