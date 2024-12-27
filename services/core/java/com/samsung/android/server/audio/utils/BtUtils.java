package com.samsung.android.server.audio.utils;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioSystem;
import android.os.ParcelUuid;
import android.util.Log;

import com.android.internal.util.ArrayUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.utils.EventLogger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class BtUtils {
    public static final ParcelUuid SAP_UUID =
            ParcelUuid.fromString("a49eb41e-cb06-495c-9f4f-bb80a90cdf00");
    public static long sBtAppPackageListVersion = -1;
    public static List sBtAppPackageList = new ArrayList();
    public static final ArrayList sBtAppUidList = new ArrayList();
    public static int sSetParamCnt = 0;
    public static final EventLogger sAuracastLogger =
            new EventLogger(50, "BLE broadcast app setParameters histories");

    public static void checkAndUpdateAuracastApp(int i, int i2, String str) {
        StringBuilder sb = new StringBuilder("l_auracast_app_key;");
        EventLogger eventLogger = sAuracastLogger;
        if (i2 == 1) {
            Iterator it = sBtAppPackageList.iterator();
            while (it.hasNext()) {
                if (((String) it.next()).equals(str)) {
                    sBtAppUidList.add(Integer.valueOf(i));
                    sb.append("uid_add=" + i);
                    AudioSystem.setParameters(sb.toString());
                    eventLogger.enqueue(
                            new EventLogger.StringEvent(
                                    sb.toString() + " " + str + " by installed"));
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
                    eventLogger.enqueue(new EventLogger.StringEvent(sb.toString() + " " + str));
                    return;
                }
            }
        }
    }

    public static boolean isSamsungWatch(
            BluetoothDevice bluetoothDevice, BluetoothHeadset bluetoothHeadset) {
        if (!((bluetoothDevice == null || bluetoothHeadset == null)
                ? false
                : "WA".equals(bluetoothHeadset.getSamsungHandsfreeDeviceType(bluetoothDevice)))) {
            if (!(bluetoothDevice != null
                    && ArrayUtils.contains(bluetoothDevice.getUuids(), SAP_UUID))) {
                return false;
            }
        }
        return true;
    }

    public static void sendAuracastAppListToNative(int i, String str) {
        StringBuilder sb = new StringBuilder("l_auracast_app_key;");
        if (i == 0) {
            sb.append("uid_reset;");
        }
        sb.append("uid_list=" + str);
        AudioSystem.setParameters(sb.toString());
        Log.i("AS.BtUtils", "sendAuracastAppListToNative " + sb.toString());
        sAuracastLogger.enqueue(new EventLogger.StringEvent(sb.toString()));
        sSetParamCnt = sSetParamCnt + 1;
    }

    public static void setAuracast(boolean z) {
        String str = z ? "true" : "false";
        AudioSystem.setParameters("l_auracast_enable=".concat(str));
        sAuracastLogger.enqueue(new EventLogger.StringEvent("l_auracast_enable=".concat(str)));
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

    public static void setBtVolumeMonitor(boolean z) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "setBtVolumeMonitor state = ", "AS.BtUtils", z);
        AudioSystem.setParameters("l_bt_type_headset=".concat(z ? "true" : "false"));
    }

    public static void updateBtAppList(Context context, List list, long j) {
        int i;
        String str;
        int i2;
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        "updateBtAppList BT package list DB version = ", j, " size = ");
        m.append(list.size());
        Log.i("AS.BtUtils", m.toString());
        sBtAppPackageList = list;
        sBtAppUidList.clear();
        PackageManager packageManager = context.getPackageManager();
        sSetParamCnt = 0;
        if (packageManager == null) {
            Log.i("AS.BtUtils", "updateBtAppList PackageManager is null");
            return;
        }
        loop0:
        while (true) {
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
                    Log.i(
                            "AS.BtUtils",
                            "updateBtAppList add uid = " + i2 + " packageName = " + str2);
                    str = str + i2 + ",";
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
        sAuracastLogger.enqueue(
                new EventLogger.StringEvent(
                        "updateBtAppList BT list updated version = "
                                + sBtAppPackageListVersion
                                + " package list size = "
                                + list.size()));
    }
}
