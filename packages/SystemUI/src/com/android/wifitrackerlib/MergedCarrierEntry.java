package com.android.wifitrackerlib;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import com.android.systemui.R;
import com.android.wifitrackerlib.WifiEntry;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class MergedCarrierEntry extends WifiEntry {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final String mKey;
    public final int mSubscriptionId;

    public MergedCarrierEntry(WifiTrackerInjector wifiTrackerInjector, Handler handler, WifiManager wifiManager, boolean z, int i) throws IllegalArgumentException {
        super(wifiTrackerInjector, handler, wifiManager, z);
        this.mSubscriptionId = i;
        this.mKey = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "MergedCarrierEntry:");
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean canConnect() {
        return getConnectedState() == 0;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final boolean canDisconnect() {
        return getConnectedState() == 2;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void connect(WifiEntry.ConnectCallback connectCallback) {
        connect$1(connectCallback);
    }

    public final synchronized void connect$1(WifiEntry.ConnectCallback connectCallback) {
        this.mConnectCallback = connectCallback;
        this.mWifiManager.startRestrictingAutoJoinToSubscriptionId(this.mSubscriptionId);
        if (this.mConnectCallback != null) {
            this.mCallbackHandler.post(new Runnable() { // from class: com.android.wifitrackerlib.MergedCarrierEntry$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MergedCarrierEntry mergedCarrierEntry = this.f$0;
                    int i = MergedCarrierEntry.$r8$clinit;
                    WifiEntry.ConnectCallback connectCallback2 = mergedCarrierEntry.mConnectCallback;
                    if (connectCallback2 != null) {
                        connectCallback2.onConnectResult(0);
                    }
                }
            });
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final boolean connectionInfoMatches(WifiInfo wifiInfo) {
        return wifiInfo.isCarrierMerged() && this.mSubscriptionId == wifiInfo.getSubscriptionId();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getKey() {
        return this.mKey;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getSsid() {
        WifiInfo wifiInfo = this.mWifiInfo;
        if (wifiInfo == null) {
            return null;
        }
        return WifiInfo.sanitizeSsid(wifiInfo.getSSID());
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getSummary(boolean z) {
        StringJoiner stringJoiner = new StringJoiner(this.mContext.getString(R.string.wifitrackerlib_summary_separator));
        String verboseLoggingDescription = Utils.getVerboseLoggingDescription(this, this.mSemFlags);
        if (!TextUtils.isEmpty(verboseLoggingDescription)) {
            stringJoiner.add(verboseLoggingDescription);
        }
        return stringJoiner.toString();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String toString() {
        StringJoiner stringJoiner = new StringJoiner("][", "[", "]");
        stringJoiner.add("SubId:" + this.mSubscriptionId);
        return super.toString() + stringJoiner;
    }
}
