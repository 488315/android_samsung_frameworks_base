package com.samsung.android.wifi.p2p;

import android.text.TextUtils;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes6.dex */
public class SemWifiP2pDeviceList {
    private final ConcurrentHashMap<String, SemWifiP2pDevice> mDevices = new ConcurrentHashMap<>();

    private boolean isInvalidDevice(SemWifiP2pDevice semWifiP2pDevice) {
        return semWifiP2pDevice == null || TextUtils.isEmpty(semWifiP2pDevice.getDeviceAddress());
    }

    public boolean clear() {
        if (this.mDevices.isEmpty()) {
            return false;
        }
        this.mDevices.clear();
        return true;
    }

    public void update(SemWifiP2pDevice semWifiP2pDevice) {
        if (isInvalidDevice(semWifiP2pDevice)) {
            return;
        }
        this.mDevices.put(semWifiP2pDevice.getDeviceAddress(), semWifiP2pDevice);
    }

    public void updateStatus(String str, int i) {
        SemWifiP2pDevice semWifiP2pDevice;
        if (TextUtils.isEmpty(str) || (semWifiP2pDevice = this.mDevices.get(str)) == null) {
            return;
        }
        semWifiP2pDevice.updateStatus(i);
    }

    public SemWifiP2pDevice get(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return this.mDevices.get(str);
    }

    public boolean remove(SemWifiP2pDevice semWifiP2pDevice) {
        return (isInvalidDevice(semWifiP2pDevice) || this.mDevices.remove(semWifiP2pDevice.getDeviceAddress()) == null) ? false : true;
    }

    public SemWifiP2pDevice remove(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return this.mDevices.remove(str);
    }

    public boolean remove(SemWifiP2pDeviceList semWifiP2pDeviceList) {
        Iterator<SemWifiP2pDevice> it = semWifiP2pDeviceList.mDevices.values().iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (remove(it.next())) {
                z = true;
            }
        }
        return z;
    }

    public Collection<SemWifiP2pDevice> getDeviceList() {
        return Collections.unmodifiableCollection(this.mDevices.values());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (SemWifiP2pDevice semWifiP2pDevice : this.mDevices.values()) {
            sb.append(ShaderAssembler.NEWLINE);
            sb.append(semWifiP2pDevice);
        }
        return sb.toString();
    }
}
