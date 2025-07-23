package com.android.wifitrackerlib;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Handler;
import com.android.wifitrackerlib.StandardWifiEntry;
import com.android.wifitrackerlib.WifiEntry;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NetworkRequestEntry extends StandardWifiEntry {
    public NetworkRequestEntry(WifiTrackerInjector wifiTrackerInjector, Handler handler, StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey, WifiManager wifiManager, boolean z) throws IllegalArgumentException {
        super(wifiTrackerInjector, handler, standardWifiEntryKey, wifiManager, z);
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry, com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean canConnect() {
        return false;
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry, com.android.wifitrackerlib.WifiEntry
    public final boolean canSetAutoJoinEnabled() {
        return false;
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry
    public final boolean canSetMeteredChoice() {
        return false;
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry, com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean canShare() {
        return false;
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry, com.android.wifitrackerlib.WifiEntry
    public final synchronized void connect(WifiEntry.ConnectCallback connectCallback) {
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry
    public final synchronized int getMeteredChoice() {
        return 0;
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry, com.android.wifitrackerlib.WifiEntry
    public final synchronized WifiConfiguration getWifiConfiguration() {
        return null;
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry, com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean isAutoJoinEnabled() {
        return true;
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry, com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean isMetered() {
        return false;
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry, com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean isSaved() {
        return false;
    }

    @Override // com.android.wifitrackerlib.StandardWifiEntry, com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean isSuggestion() {
        return false;
    }
}
