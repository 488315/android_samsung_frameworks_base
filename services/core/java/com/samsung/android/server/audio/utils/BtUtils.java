package com.samsung.android.server.audio.utils;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioSystem;
import android.os.ParcelUuid;
import android.util.Log;
import com.android.internal.util.ArrayUtils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.utils.EventLogger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class BtUtils {
    public static final ParcelUuid SAP_UUID = ParcelUuid.fromString("a49eb41e-cb06-495c-9f4f-bb80a90cdf00");
    public static long sBtAppPackageListVersion = -1;
    public static List sBtAppPackageList = new ArrayList();
    public static ArrayList sBtAppUidList = new ArrayList();
    public static int sSetParamCnt = 0;
    public static final EventLogger sAuracastLogger = new EventLogger(50, "BLE broadcast app setParameters histories");

    public static byte[] intToBytes(int i) {
        return new byte[]{(byte) i, (byte) (i >> 8)};
    }

    public static void setBtVolumeMonitor(boolean z) {
        Log.i("AS.BtUtils", "setBtVolumeMonitor state = " + z);
        AudioSystem.setParameters("l_bt_type_headset=" + (z ? "true" : "false"));
    }

    public static void setBtVolumeMonitor(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return;
        }
        Log.i("AS.BtUtils", "setBtVolumeMonitor deviceType = " + bluetoothDevice.semGetAudioType());
        if (bluetoothDevice.semGetAudioType() == 2) {
            AudioSystem.setParameters("l_bt_type_headset=true");
        } else {
            AudioSystem.setParameters("l_bt_type_headset=false");
        }
    }

    public static boolean isBudsWearingOff(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return false;
        }
        try {
            byte[] semGetMetadata = bluetoothDevice.semGetMetadata(intToBytes(518));
            byte[] semGetMetadata2 = bluetoothDevice.semGetMetadata(intToBytes(519));
            if (semGetMetadata == null || semGetMetadata2 == null || semGetMetadata[3] == 1) {
                return false;
            }
            return semGetMetadata2[3] != 1;
        } catch (Exception e) {
            Log.w("AS.BtUtils", "failed to get buds wearing status: " + e.getMessage());
            return false;
        }
    }

    public static boolean isSamsungWatch(BluetoothDevice bluetoothDevice, BluetoothHeadset bluetoothHeadset) {
        return isSamsungWatchType(bluetoothDevice, bluetoothHeadset) || isSamsungWatchUuid(bluetoothDevice);
    }

    public static boolean isSamsungWatchType(BluetoothDevice bluetoothDevice, BluetoothHeadset bluetoothHeadset) {
        if (bluetoothDevice == null || bluetoothHeadset == null) {
            return false;
        }
        return "WA".equals(bluetoothHeadset.getSamsungHandsfreeDeviceType(bluetoothDevice));
    }

    public static boolean isSamsungWatchUuid(BluetoothDevice bluetoothDevice) {
        return bluetoothDevice != null && ArrayUtils.contains(bluetoothDevice.getUuids(), SAP_UUID);
    }

    public static void sendAuracastAppListToNative(int i, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("l_auracast_app_key;");
        if (i == 0) {
            sb.append("uid_reset;");
        }
        sb.append("uid_list=" + str);
        AudioSystem.setParameters(sb.toString());
        Log.i("AS.BtUtils", "sendAuracastAppListToNative " + sb.toString());
        sAuracastLogger.enqueue(new EventLogger.StringEvent(sb.toString()));
        sSetParamCnt = sSetParamCnt + 1;
    }

    public static void updateBtAppList(Context context, List list, long j) {
        int i;
        String str;
        int i2;
        Log.i("AS.BtUtils", "updateBtAppList BT package list DB version = " + j + " size = " + list.size());
        sBtAppPackageList = list;
        sBtAppUidList.clear();
        PackageManager packageManager = context.getPackageManager();
        sSetParamCnt = 0;
        if (packageManager != null) {
            loop0: while (true) {
                i = 0;
                str = "";
                for (String str2 : sBtAppPackageList) {
                    try {
                        i2 = packageManager.getPackageUid(str2, PackageManager.PackageInfoFlags.of(0L));
                    } catch (PackageManager.NameNotFoundException unused) {
                        i2 = 0;
                    }
                    if (i2 != 0) {
                        sBtAppUidList.add(Integer.valueOf(i2));
                        Log.i("AS.BtUtils", "updateBtAppList add uid = " + i2 + " packageName = " + str2);
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        sb.append(i2);
                        sb.append(",");
                        str = sb.toString();
                        i++;
                    }
                    if (i == 100) {
                        break;
                    }
                }
                sendAuracastAppListToNative(sSetParamCnt, str);
            }
            if (i != 0 && i < 100) {
                sendAuracastAppListToNative(sSetParamCnt, str);
            }
            sBtAppPackageListVersion = j;
            Log.i("AS.BtUtils", "updateBtAppList done");
            sAuracastLogger.enqueue(new EventLogger.StringEvent("updateBtAppList BT list updated version = " + sBtAppPackageListVersion + " package list size = " + list.size()));
            return;
        }
        Log.i("AS.BtUtils", "updateBtAppList PackageManager is null");
    }

    public static void checkAndUpdateAuracastApp(int i, String str, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("l_auracast_app_key;");
        if (i2 == 1) {
            Iterator it = sBtAppPackageList.iterator();
            while (it.hasNext()) {
                if (((String) it.next()).equals(str)) {
                    sBtAppUidList.add(Integer.valueOf(i));
                    sb.append("uid_add=" + i);
                    AudioSystem.setParameters(sb.toString());
                    sAuracastLogger.enqueue(new EventLogger.StringEvent(sb.toString() + " " + str + " by installed"));
                }
            }
            return;
        }
        if (i2 == 0) {
            Iterator it2 = sBtAppUidList.iterator();
            while (it2.hasNext()) {
                if (((Integer) it2.next()).intValue() == i) {
                    sBtAppUidList.remove(Integer.valueOf(i));
                    sb.append("uid_remove=" + i);
                    AudioSystem.setParameters(sb.toString());
                    sAuracastLogger.enqueue(new EventLogger.StringEvent(sb.toString() + " " + str));
                    return;
                }
            }
        }
    }

    public static EventLogger getAuracastAppLogger() {
        return sAuracastLogger;
    }

    public static String getAuracastUids() {
        ArrayList arrayList = sBtAppUidList;
        String str = "";
        if (arrayList == null) {
            return "";
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            str = str + ((Integer) it.next()).intValue() + KnoxVpnFirewallHelper.DELIMITER;
        }
        return str;
    }

    public static void recoveryAuracastAppListToNative() {
        int i;
        String str;
        Log.i("AS.BtUtils", "recoveryAuracastAppListToNative sBtAppUidList size = " + sBtAppUidList.size());
        sAuracastLogger.enqueue(new EventLogger.StringEvent("AudioServer died recoveryAuracastAppList start ! sBtAppUidList size = " + sBtAppUidList.size()));
        sSetParamCnt = 0;
        Iterator it = sBtAppUidList.iterator();
        loop0: while (true) {
            i = 0;
            str = "";
            while (it.hasNext()) {
                str = str + ((Integer) it.next()).intValue() + KnoxVpnFirewallHelper.DELIMITER;
                i++;
                if (i == 100) {
                    break;
                }
            }
            sendAuracastAppListToNative(sSetParamCnt, str);
        }
        if (i != 0 && i < 100) {
            sendAuracastAppListToNative(sSetParamCnt, str);
        }
        sAuracastLogger.enqueue(new EventLogger.StringEvent("AudioServer died recoveryAuracastAppList done !"));
    }

    public static void setAuracast(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("l_auracast_enable=");
        sb.append(z ? "true" : "false");
        AudioSystem.setParameters(sb.toString());
        sAuracastLogger.enqueue(new EventLogger.StringEvent(sb.toString()));
    }
}
