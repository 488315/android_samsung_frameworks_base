package com.android.wifitrackerlib;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Handler;
import com.android.wifitrackerlib.StandardWifiEntry;
import com.android.wifitrackerlib.WifiEntry;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NetworkRequestEntry extends StandardWifiEntry {
    public NetworkRequestEntry(WifiTrackerInjector wifiTrackerInjector, Context context, Handler handler, StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey, WifiManager wifiManager, boolean z) {
        super(wifiTrackerInjector, context, handler, standardWifiEntryKey, wifiManager, z);
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

    @Override // com.android.wifitrackerlib.StandardWifiEntry
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
