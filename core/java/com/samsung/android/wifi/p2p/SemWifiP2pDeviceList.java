package com.samsung.android.wifi.p2p;

import android.text.TextUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes6.dex */
public class SemWifiP2pDeviceList {
    private final ConcurrentHashMap<String, SemWifiP2pDevice> mDevices = new ConcurrentHashMap<>();

    private boolean isInvalidDevice(SemWifiP2pDevice device) {
        return device == null || TextUtils.isEmpty(device.getDeviceAddress());
    }

    public boolean clear() {
        if (this.mDevices.isEmpty()) {
            return false;
        }
        this.mDevices.clear();
        return true;
    }

    public void update(SemWifiP2pDevice device) {
        if (isInvalidDevice(device)) {
            return;
        }
        this.mDevices.put(device.getDeviceAddress(), device);
    }

    public void updateStatus(String deviceAddress, int status) {
        SemWifiP2pDevice d;
        if (!TextUtils.isEmpty(deviceAddress) && (d = this.mDevices.get(deviceAddress)) != null) {
            d.updateStatus(status);
        }
    }

    public SemWifiP2pDevice get(String deviceAddress) {
        if (TextUtils.isEmpty(deviceAddress)) {
            return null;
        }
        return this.mDevices.get(deviceAddress);
    }

    public boolean remove(SemWifiP2pDevice device) {
        return (isInvalidDevice(device) || this.mDevices.remove(device.getDeviceAddress()) == null)
                ? false
                : true;
    }

    public SemWifiP2pDevice remove(String deviceAddress) {
        if (TextUtils.isEmpty(deviceAddress)) {
            return null;
        }
        return this.mDevices.remove(deviceAddress);
    }

    public boolean remove(SemWifiP2pDeviceList list) {
        boolean ret = false;
        for (SemWifiP2pDevice d : list.mDevices.values()) {
            if (remove(d)) {
                ret = true;
            }
        }
        return ret;
    }

    public Collection<SemWifiP2pDevice> getDeviceList() {
        return Collections.unmodifiableCollection(this.mDevices.values());
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (SemWifiP2pDevice device : this.mDevices.values()) {
            builder.append("\n").append(device);
        }
        return builder.toString();
    }
}
