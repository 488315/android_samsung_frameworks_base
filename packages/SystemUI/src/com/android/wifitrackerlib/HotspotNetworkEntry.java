package com.android.wifitrackerlib;

import android.content.Context;
import android.icu.text.MessageFormat;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.sharedconnectivity.app.HotspotNetwork;
import android.net.wifi.sharedconnectivity.app.SharedConnectivityManager;
import android.os.Handler;
import android.text.BidiFormatter;
import android.util.Log;
import com.android.systemui.R;
import com.android.wifitrackerlib.StandardWifiEntry;
import com.android.wifitrackerlib.WifiEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class HotspotNetworkEntry extends WifiEntry {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mConnectionError;
    public final Context mContext;
    public HotspotNetwork mHotspotNetworkData;
    public HotspotNetworkEntryKey mKey;
    public int mLastStatus;
    public final SharedConnectivityManager mSharedConnectivityManager;

    public HotspotNetworkEntry(WifiTrackerInjector wifiTrackerInjector, Context context, Handler handler, WifiManager wifiManager, SharedConnectivityManager sharedConnectivityManager, HotspotNetwork hotspotNetwork) {
        super(wifiTrackerInjector, handler, wifiManager, false);
        this.mLastStatus = 0;
        this.mConnectionError = false;
        this.mContext = context;
        this.mSharedConnectivityManager = sharedConnectivityManager;
        this.mHotspotNetworkData = hotspotNetwork;
        this.mKey = new HotspotNetworkEntryKey(hotspotNetwork);
    }

    public static String getDeviceTypeId(int i) {
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? "UNKNOWN" : "VEHICLE" : "WATCH" : "COMPUTER" : "TABLET" : "PHONE";
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean canConnect() {
        return getConnectedState() == 0;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean canDisconnect() {
        return getConnectedState() != 0;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void connect(WifiEntry.ConnectCallback connectCallback) {
        this.mConnectCallback = connectCallback;
        SharedConnectivityManager sharedConnectivityManager = this.mSharedConnectivityManager;
        if (sharedConnectivityManager != null) {
            sharedConnectivityManager.connectHotspotNetwork(this.mHotspotNetworkData);
        } else {
            if (connectCallback != null) {
                this.mCallbackHandler.post(new HotspotNetworkEntry$$ExternalSyntheticLambda0(connectCallback, 2));
            }
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean connectionInfoMatches(WifiInfo wifiInfo) {
        HotspotNetworkEntryKey hotspotNetworkEntryKey = this.mKey;
        if (hotspotNetworkEntryKey.mIsVirtualEntry) {
            return false;
        }
        return Objects.equals(hotspotNetworkEntryKey.mScanResultKey, new StandardWifiEntry.ScanResultKey(WifiInfo.sanitizeSsid(wifiInfo.getSSID()), Collections.singletonList(Integer.valueOf(wifiInfo.getCurrentSecurityType()))));
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized int getConnectedState() {
        return super.getConnectedState();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getKey() {
        return this.mKey.toString();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final int getLevel() {
        if (getConnectedState() == 0) {
            return 4;
        }
        return super.getLevel();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getSecurityString() {
        if (this.mHotspotNetworkData == null) {
            return "";
        }
        return Utils.getSecurityString(this.mContext, new ArrayList(this.mHotspotNetworkData.getHotspotSecurityTypes()));
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getSsid() {
        StandardWifiEntry.ScanResultKey scanResultKey = this.mKey.mScanResultKey;
        if (scanResultKey == null) {
            return null;
        }
        return scanResultKey.mSsid;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getStandardString() {
        WifiInfo wifiInfo = this.mWifiInfo;
        if (wifiInfo == null) {
            return "";
        }
        return Utils.getStandardString(wifiInfo.getWifiStandard(), this.mContext);
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getSummary(boolean z) {
        if (this.mHotspotNetworkData == null) {
            return "";
        }
        if (this.mCalledConnect) {
            return this.mContext.getString(R.string.wifitrackerlib_hotspot_network_connecting);
        }
        if (!this.mConnectionError) {
            MessageFormat messageFormat = new MessageFormat(this.mContext.getString(R.string.wifitrackerlib_hotspot_network_summary_new));
            HashMap hashMap = new HashMap();
            hashMap.put("DEVICE_TYPE", getDeviceTypeId(this.mHotspotNetworkData.getNetworkProviderInfo().getDeviceType()));
            hashMap.put("NETWORK_NAME", this.mHotspotNetworkData.getNetworkName());
            return messageFormat.format(hashMap);
        }
        switch (this.mLastStatus) {
            case 3:
            case 4:
                return this.mContext.getString(R.string.wifitrackerlib_hotspot_network_summary_error_carrier_incomplete, BidiFormatter.getInstance().unicodeWrap(this.mHotspotNetworkData.getNetworkName()));
            case 5:
                return this.mContext.getString(R.string.wifitrackerlib_hotspot_network_summary_error_carrier_block, BidiFormatter.getInstance().unicodeWrap(this.mHotspotNetworkData.getNetworkName()));
            case 6:
            case 7:
            case 8:
            case 9:
                MessageFormat messageFormat2 = new MessageFormat(this.mContext.getString(R.string.wifitrackerlib_hotspot_network_summary_error_settings));
                HashMap hashMap2 = new HashMap();
                hashMap2.put("DEVICE_TYPE", getDeviceTypeId(this.mHotspotNetworkData.getNetworkProviderInfo().getDeviceType()));
                return messageFormat2.format(hashMap2);
            default:
                return this.mContext.getString(R.string.wifitrackerlib_hotspot_network_summary_error_generic);
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getTitle() {
        HotspotNetwork hotspotNetwork = this.mHotspotNetworkData;
        if (hotspotNetwork == null) {
            return "";
        }
        return hotspotNetwork.getNetworkProviderInfo().getDeviceName();
    }

    public final void onConnectionStatusChanged(int i) {
        this.mLastStatus = i;
        Handler handler = this.mCallbackHandler;
        switch (i) {
            case 1:
                this.mCalledConnect = true;
                this.mConnectionError = false;
                notifyOnUpdated();
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                handler.post(new HotspotNetworkEntry$$ExternalSyntheticLambda0(this, 0));
                this.mCalledConnect = false;
                this.mConnectionError = true;
                notifyOnUpdated();
                break;
            case 10:
                handler.post(new HotspotNetworkEntry$$ExternalSyntheticLambda0(this, 1));
                this.mCalledConnect = false;
                this.mConnectionError = false;
                notifyOnUpdated();
                break;
        }
    }

    public HotspotNetworkEntry(WifiTrackerInjector wifiTrackerInjector, Context context, Handler handler, WifiManager wifiManager, SharedConnectivityManager sharedConnectivityManager, HotspotNetworkEntryKey hotspotNetworkEntryKey) {
        super(wifiTrackerInjector, handler, wifiManager, false);
        this.mLastStatus = 0;
        this.mConnectionError = false;
        this.mContext = context;
        this.mSharedConnectivityManager = sharedConnectivityManager;
        this.mHotspotNetworkData = null;
        this.mKey = hotspotNetworkEntryKey;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class HotspotNetworkEntryKey {
        public final long mDeviceId;
        public final boolean mIsVirtualEntry;
        public final StandardWifiEntry.ScanResultKey mScanResultKey;

        public HotspotNetworkEntryKey(HotspotNetwork hotspotNetwork) {
            this.mDeviceId = hotspotNetwork.getDeviceId();
            if (hotspotNetwork.getHotspotSsid() == null || hotspotNetwork.getHotspotSecurityTypes() == null) {
                this.mIsVirtualEntry = true;
                this.mScanResultKey = null;
            } else {
                this.mIsVirtualEntry = false;
                this.mScanResultKey = new StandardWifiEntry.ScanResultKey(hotspotNetwork.getHotspotSsid(), new ArrayList(hotspotNetwork.getHotspotSecurityTypes()));
            }
        }

        public final String toString() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("IS_VIRTUAL_ENTRY_KEY", this.mIsVirtualEntry);
                jSONObject.put("DEVICE_ID_KEY", this.mDeviceId);
                StandardWifiEntry.ScanResultKey scanResultKey = this.mScanResultKey;
                if (scanResultKey != null) {
                    jSONObject.put("SCAN_RESULT_KEY", scanResultKey.toString());
                }
            } catch (JSONException e) {
                Log.wtf("HotspotNetworkEntry", "JSONException while converting HotspotNetworkEntryKey to string: " + e);
            }
            return "HotspotNetworkEntry:" + jSONObject.toString();
        }

        public HotspotNetworkEntryKey(String str) {
            this.mScanResultKey = null;
            if (!str.startsWith("HotspotNetworkEntry:")) {
                Log.e("HotspotNetworkEntry", "String key does not start with key prefix!");
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(str.substring(20));
                if (jSONObject.has("IS_VIRTUAL_ENTRY_KEY")) {
                    this.mIsVirtualEntry = jSONObject.getBoolean("IS_VIRTUAL_ENTRY_KEY");
                }
                if (jSONObject.has("DEVICE_ID_KEY")) {
                    this.mDeviceId = jSONObject.getLong("DEVICE_ID_KEY");
                }
                if (jSONObject.has("SCAN_RESULT_KEY")) {
                    this.mScanResultKey = new StandardWifiEntry.ScanResultKey(jSONObject.getString("SCAN_RESULT_KEY"));
                }
            } catch (JSONException e) {
                Log.e("HotspotNetworkEntry", "JSONException while converting HotspotNetworkEntryKey to string: " + e);
            }
        }
    }
}
