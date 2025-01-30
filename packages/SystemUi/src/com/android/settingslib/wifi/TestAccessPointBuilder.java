package com.android.settingslib.wifi;

import android.content.Context;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Parcelable;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class TestAccessPointBuilder {
    private static final int MAX_RSSI = -55;
    private static final int MIN_RSSI = -100;
    Context mContext;
    private ArrayList<ScanResult> mScanResults;
    private ArrayList<TimestampedScoredNetwork> mScoredNetworkCache;
    private WifiConfiguration mWifiConfig;
    private WifiInfo mWifiInfo;
    private String mBssid = null;
    private int mSpeed = 0;
    private int mRssi = VideoPlayer.MEDIA_ERROR_SYSTEM;
    private int mNetworkId = -1;
    private String ssid = "TestSsid";
    private NetworkInfo mNetworkInfo = null;
    private String mFqdn = null;
    private String mProviderFriendlyName = null;
    private int mSecurity = 0;

    public TestAccessPointBuilder(Context context) {
        this.mContext = context;
    }

    public AccessPoint build() {
        WifiConfiguration wifiConfiguration;
        Bundle bundle = new Bundle();
        if (this.mNetworkId != -1) {
            wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.networkId = this.mNetworkId;
            wifiConfiguration.BSSID = this.mBssid;
        } else {
            wifiConfiguration = null;
        }
        bundle.putString("key_ssid", this.ssid);
        bundle.putParcelable("key_config", wifiConfiguration);
        bundle.putParcelable("key_networkinfo", this.mNetworkInfo);
        bundle.putParcelable("key_wifiinfo", this.mWifiInfo);
        String str = this.mFqdn;
        if (str != null) {
            bundle.putString("key_passpoint_unique_id", str);
        }
        String str2 = this.mProviderFriendlyName;
        if (str2 != null) {
            bundle.putString("key_provider_friendly_name", str2);
        }
        ArrayList<ScanResult> arrayList = this.mScanResults;
        if (arrayList != null) {
            bundle.putParcelableArray("key_scanresults", (Parcelable[]) arrayList.toArray(new Parcelable[arrayList.size()]));
        }
        ArrayList<TimestampedScoredNetwork> arrayList2 = this.mScoredNetworkCache;
        if (arrayList2 != null) {
            bundle.putParcelableArrayList("key_scorednetworkcache", arrayList2);
        }
        bundle.putInt("key_security", this.mSecurity);
        bundle.putInt("key_speed", this.mSpeed);
        AccessPoint accessPoint = new AccessPoint(this.mContext, bundle);
        accessPoint.setRssi(this.mRssi);
        return accessPoint;
    }

    public TestAccessPointBuilder setActive(boolean z) {
        if (z) {
            this.mNetworkInfo = new NetworkInfo(1, 1, "TestNetwork", "TestNetwork");
        } else {
            this.mNetworkInfo = null;
        }
        return this;
    }

    public TestAccessPointBuilder setBssid(String str) {
        this.mBssid = str;
        return this;
    }

    public TestAccessPointBuilder setFqdn(String str) {
        this.mFqdn = str;
        return this;
    }

    public TestAccessPointBuilder setLevel(int i) {
        int maxSignalLevel = ((WifiManager) this.mContext.getSystemService(WifiManager.class)).getMaxSignalLevel();
        if (i == 0) {
            this.mRssi = -100;
        } else if (i > maxSignalLevel) {
            this.mRssi = -55;
        } else {
            this.mRssi = (int) (((i * 45.0f) / maxSignalLevel) - 100.0f);
        }
        return this;
    }

    public TestAccessPointBuilder setNetworkId(int i) {
        this.mNetworkId = i;
        return this;
    }

    public TestAccessPointBuilder setNetworkInfo(NetworkInfo networkInfo) {
        this.mNetworkInfo = networkInfo;
        return this;
    }

    public TestAccessPointBuilder setProviderFriendlyName(String str) {
        this.mProviderFriendlyName = str;
        return this;
    }

    public TestAccessPointBuilder setReachable(boolean z) {
        if (!z) {
            this.mRssi = VideoPlayer.MEDIA_ERROR_SYSTEM;
        } else if (this.mRssi == Integer.MIN_VALUE) {
            this.mRssi = -100;
        }
        return this;
    }

    public TestAccessPointBuilder setRssi(int i) {
        this.mRssi = i;
        return this;
    }

    public TestAccessPointBuilder setSaved(boolean z) {
        if (z) {
            this.mNetworkId = 1;
        } else {
            this.mNetworkId = -1;
        }
        return this;
    }

    public TestAccessPointBuilder setScanResults(ArrayList<ScanResult> arrayList) {
        this.mScanResults = arrayList;
        return this;
    }

    public TestAccessPointBuilder setScoredNetworkCache(ArrayList<TimestampedScoredNetwork> arrayList) {
        this.mScoredNetworkCache = arrayList;
        return this;
    }

    public TestAccessPointBuilder setSecurity(int i) {
        this.mSecurity = i;
        return this;
    }

    public TestAccessPointBuilder setSpeed(int i) {
        this.mSpeed = i;
        return this;
    }

    public TestAccessPointBuilder setSsid(String str) {
        this.ssid = str;
        return this;
    }

    public TestAccessPointBuilder setWifiInfo(WifiInfo wifiInfo) {
        this.mWifiInfo = wifiInfo;
        return this;
    }
}
