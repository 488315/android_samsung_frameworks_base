package com.android.server.enterprise.adapterlayer;

import android.content.AttributionSource;
import android.content.Context;
import android.content.ContextParams;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Message;
import android.permission.PermissionManager;
import android.util.Log;
import com.samsung.android.wifi.SemWifiManager;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public class WifiManagerAdapter {
    public static Context mContext;
    public static WifiManagerAdapter mInstance;
    public static SemWifiManager mSemWifiManager;
    public static WifiManager mWifiManager;

    public static synchronized WifiManagerAdapter getInstance(Context context) {
        WifiManagerAdapter wifiManagerAdapter;
        synchronized (WifiManagerAdapter.class) {
            if (mInstance == null) {
                mInstance = new WifiManagerAdapter();
                mContext = context;
                mWifiManager = (WifiManager) context.getSystemService("wifi");
                mSemWifiManager = (SemWifiManager) context.getSystemService("sem_wifi");
            }
            wifiManagerAdapter = mInstance;
        }
        return wifiManagerAdapter;
    }

    public void setSoftApConfiguration(SoftApConfiguration softApConfiguration) {
        mSemWifiManager.setSoftApConfiguration(softApConfiguration);
    }

    public SoftApConfiguration getSoftApConfiguration() {
        return mSemWifiManager.getSoftApConfiguration();
    }

    public int getWifiApState() {
        return mSemWifiManager.getWifiApState();
    }

    public void resetSoftAp() {
        mSemWifiManager.resetSoftAp(new Message());
    }

    public boolean forget(WifiConfiguration wifiConfiguration) {
        try {
            return mWifiManager.removeNetwork(wifiConfiguration.networkId);
        } catch (Exception unused) {
            Log.e("WifiManagerAdapter", "forget - failed to get wifi service");
            return false;
        }
    }

    public int save(WifiConfiguration wifiConfiguration, int i, String str) {
        if (wifiConfiguration == null) {
            return -1;
        }
        try {
            WifiManager retrieveWifiManagerObjectWithAttributionSource = retrieveWifiManagerObjectWithAttributionSource(mContext, i, str);
            if (wifiConfiguration.networkId == -1) {
                return retrieveWifiManagerObjectWithAttributionSource.addNetwork(wifiConfiguration);
            }
            return retrieveWifiManagerObjectWithAttributionSource.updateNetwork(wifiConfiguration);
        } catch (Exception e) {
            Log.e("WifiManagerAdapter", "save - " + e.getMessage());
            return -1;
        }
    }

    public static WifiManager retrieveWifiManagerObjectWithAttributionSource(Context context, int i, String str) {
        if (i != 1000) {
            context = mContext.createContext(new ContextParams.Builder().setNextAttributionSource(((PermissionManager) mContext.getSystemService(PermissionManager.class)).registerAttributionSource(new AttributionSource.Builder(i).setPackageName(str).build())).build());
        }
        return (WifiManager) context.getSystemService(WifiManager.class);
    }

    public void connect(int i) {
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

    public List getConfiguredNetworks() {
        try {
            List privilegedConfiguredNetworks = mWifiManager.getPrivilegedConfiguredNetworks();
            return privilegedConfiguredNetworks == null ? Collections.emptyList() : privilegedConfiguredNetworks;
        } catch (Exception e) {
            Log.e("WifiManagerAdapter", "getConfiguredNetworks - failed to get networks " + e.getMessage());
            return null;
        }
    }

    public void enableNetwork(int i, boolean z) {
        try {
            mWifiManager.enableNetwork(i, z);
        } catch (Exception e) {
            Log.e("WifiManagerAdapter", "Failed to enable network " + e.getMessage());
        }
    }
}
