package com.android.settingslib.deviceinfo;

import android.content.Context;
import android.net.wifi.WifiManager;
import com.android.settingslib.core.lifecycle.Lifecycle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class AbstractWifiMacAddressPreferenceController extends AbstractConnectivityPreferenceController {
    public static final String[] CONNECTIVITY_INTENTS = {"android.net.conn.CONNECTIVITY_CHANGE", "android.net.wifi.LINK_CONFIGURATION_CHANGED", "android.net.wifi.STATE_CHANGE"};
    static final String KEY_WIFI_MAC_ADDRESS = "wifi_mac_address";
    static final int OFF = 0;

    /* renamed from: ON */
    static final int f219ON = 1;
    public final WifiManager mWifiManager;

    public AbstractWifiMacAddressPreferenceController(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
    }

    @Override // com.android.settingslib.deviceinfo.AbstractConnectivityPreferenceController
    public final String[] getConnectivityIntents() {
        return CONNECTIVITY_INTENTS;
    }

    @Override // com.android.settingslib.deviceinfo.AbstractConnectivityPreferenceController
    public final void updateConnectivity() {
        String[] factoryMacAddresses = this.mWifiManager.getFactoryMacAddresses();
        if (factoryMacAddresses == null || factoryMacAddresses.length <= 0) {
            return;
        }
        String str = factoryMacAddresses[0];
    }
}
