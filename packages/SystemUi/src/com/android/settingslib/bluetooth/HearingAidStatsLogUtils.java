package com.android.settingslib.bluetooth;

import android.util.Log;
import com.android.internal.util.FrameworkStatsLog;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class HearingAidStatsLogUtils {
    public static final HashMap sDeviceAddressToBondEntryMap = new HashMap();

    private HearingAidStatsLogUtils() {
    }

    public static HashMap<String, Integer> getDeviceAddressToBondEntryMap() {
        return sDeviceAddressToBondEntryMap;
    }

    public static void logHearingAidInfo(CachedBluetoothDevice cachedBluetoothDevice) {
        String address = cachedBluetoothDevice.getAddress();
        HashMap hashMap = sDeviceAddressToBondEntryMap;
        if (!hashMap.containsKey(address)) {
            Log.w("HearingAidStatsLogUtils", "The device address was not found. Hearing aid device info is not logged.");
            return;
        }
        int intValue = ((Integer) hashMap.getOrDefault(address, -1)).intValue();
        HearingAidInfo hearingAidInfo = cachedBluetoothDevice.mHearingAidInfo;
        FrameworkStatsLog.write(513, hearingAidInfo != null ? hearingAidInfo.mMode : -1, hearingAidInfo != null ? hearingAidInfo.mSide : -1, intValue);
        hashMap.remove(address);
    }
}
