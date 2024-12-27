package com.android.server.enterprise.adapterlayer;

import android.content.AttributionSource;
import android.content.Context;
import android.content.ContextParams;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.permission.PermissionManager;
import android.util.Log;

import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.wifi.SemWifiManager;

import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WifiManagerAdapter {
    public static Context mContext;
    public static WifiManagerAdapter mInstance;
    public static SemWifiManager mSemWifiManager;
    public static WifiManager mWifiManager;

    public static void connect(int i) {
        try {
            mWifiManager.connect(i, null);
            SemWifiManager semWifiManager = (SemWifiManager) mContext.getSystemService("sem_wifi");
            if (semWifiManager != null) {
                semWifiManager.setConnectionAttemptInfo(i, false);
            }
        } catch (IllegalArgumentException e) {
            Log.e("WifiManagerAdapter", "Failed to connect to network " + e.getMessage());
        }
    }

    public static List getConfiguredNetworks() {
        try {
            List privilegedConfiguredNetworks = mWifiManager.getPrivilegedConfiguredNetworks();
            return privilegedConfiguredNetworks == null
                    ? Collections.emptyList()
                    : privilegedConfiguredNetworks;
        } catch (Exception e) {
            RCPManagerService$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("getConfiguredNetworks - failed to get networks "),
                    "WifiManagerAdapter");
            return null;
        }
    }

    public static WifiManager retrieveWifiManagerObjectWithAttributionSource(
            Context context, String str, int i) {
        if (i != 1000) {
            context =
                    mContext.createContext(
                            new ContextParams.Builder()
                                    .setNextAttributionSource(
                                            ((PermissionManager)
                                                            mContext.getSystemService(
                                                                    PermissionManager.class))
                                                    .registerAttributionSource(
                                                            new AttributionSource.Builder(i)
                                                                    .setPackageName(str)
                                                                    .build()))
                                    .build());
        }
        return (WifiManager) context.getSystemService(WifiManager.class);
    }

    public static int save(WifiConfiguration wifiConfiguration, int i, String str) {
        try {
            WifiManager retrieveWifiManagerObjectWithAttributionSource =
                    retrieveWifiManagerObjectWithAttributionSource(mContext, str, i);
            return wifiConfiguration.networkId == -1
                    ? retrieveWifiManagerObjectWithAttributionSource.addNetwork(wifiConfiguration)
                    : retrieveWifiManagerObjectWithAttributionSource.updateNetwork(
                            wifiConfiguration);
        } catch (Exception e) {
            RCPManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("save - "), "WifiManagerAdapter");
            return -1;
        }
    }
}
