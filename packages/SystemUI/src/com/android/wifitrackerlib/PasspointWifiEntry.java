package com.android.wifitrackerlib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.os.BuildCompat;
import androidx.core.util.Preconditions;
import com.android.systemui.R;
import com.android.wifitrackerlib.WifiEntry;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.samsung.android.wifitrackerlib.WifiIssueDetectorUtil;
import com.sec.ims.extensions.WiFiManagerExt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PasspointWifiEntry extends WifiEntry implements WifiEntry.WifiEntryCallback {
    public final List mCurrentHomeScanResults;
    public final List mCurrentRoamingScanResults;
    public final String mFqdn;
    public final String mFriendlyName;
    public final String mKey;
    public int mMeteredOverride;
    public OsuWifiEntry mOsuWifiEntry;
    public PasspointConfiguration mPasspointConfig;
    public SemWifiManager mSemWifiManager;
    public boolean mShouldAutoOpenCaptivePortal;
    public final boolean mShowBandSummary;
    public long mSubscriptionExpirationTimeInMillis;
    public final List mTargetScanResults;
    public List mTargetSecurityTypes;
    public final String mUniqueId;
    public WifiConfiguration mWifiConfig;

    public PasspointWifiEntry(WifiTrackerInjector wifiTrackerInjector, Handler handler, PasspointConfiguration passpointConfiguration, WifiManager wifiManager, boolean z) throws IllegalArgumentException {
        super(wifiTrackerInjector, handler, wifiManager, z);
        boolean z2 = false;
        this.mShowBandSummary = false;
        this.mTargetScanResults = new ArrayList();
        this.mCurrentHomeScanResults = new ArrayList();
        this.mCurrentRoamingScanResults = new ArrayList();
        this.mTargetSecurityTypes = Arrays.asList(11, 12);
        this.mShouldAutoOpenCaptivePortal = false;
        this.mMeteredOverride = 0;
        Preconditions.checkNotNull(passpointConfiguration, "Cannot construct with null PasspointConfiguration!");
        this.mPasspointConfig = passpointConfiguration;
        String uniqueId = passpointConfiguration.getUniqueId();
        this.mUniqueId = uniqueId;
        this.mKey = uniqueIdToPasspointWifiEntryKey(uniqueId);
        String fqdn = passpointConfiguration.getHomeSp().getFqdn();
        this.mFqdn = fqdn;
        Preconditions.checkNotNull(fqdn, "Cannot construct with null PasspointConfiguration FQDN!");
        this.mFriendlyName = passpointConfiguration.getHomeSp().getFriendlyName();
        this.mSubscriptionExpirationTimeInMillis = passpointConfiguration.getSubscriptionExpirationTimeMillis();
        this.mMeteredOverride = this.mPasspointConfig.getMeteredOverride();
        if (SemWifiEntryFlags.isWifiDeveloperOptionOn(this.mContext) && SemWifiEntryFlags.isShowBandInfoOn(this.mContext)) {
            z2 = true;
        }
        this.mShowBandSummary = z2;
        this.mSemFlags.setIsSupportedWifi7(this.mContext);
        semUpdateFlags(this.mPasspointConfig);
    }

    public static String uniqueIdToPasspointWifiEntryKey(String str) {
        Preconditions.checkNotNull(str, "Cannot create key with null unique id!");
        return "PasspointWifiEntry:".concat(str);
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean canConnect() {
        boolean z = false;
        if (isExpired()) {
            OsuWifiEntry osuWifiEntry = this.mOsuWifiEntry;
            if (osuWifiEntry != null && osuWifiEntry.canConnect()) {
                z = true;
            }
            return z;
        }
        if (this.mScanResultLevel != -1 && getConnectedState() == 0 && this.mWifiConfig != null) {
            z = true;
        }
        return z;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final boolean canDisconnect() {
        return getConnectedState() == 2;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean canSetAutoJoinEnabled() {
        boolean z;
        if (this.mPasspointConfig == null) {
            z = this.mWifiConfig != null;
        }
        return z;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean canSignIn() {
        boolean z;
        NetworkCapabilities networkCapabilities;
        if (this.mNetwork != null && (networkCapabilities = this.mNetworkCapabilities) != null) {
            z = networkCapabilities.hasCapability(17);
        }
        return z;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void connect(WifiEntry.ConnectCallback connectCallback) {
        OsuWifiEntry osuWifiEntry;
        if (isExpired() && (osuWifiEntry = this.mOsuWifiEntry) != null) {
            osuWifiEntry.connect(connectCallback);
            return;
        }
        this.mShouldAutoOpenCaptivePortal = true;
        this.mConnectCallback = connectCallback;
        if (this.mWifiConfig == null) {
            new WifiEntry.ConnectActionListener().onFailure(0);
        }
        this.mWifiManager.stopRestrictingAutoJoinToSubscriptionId();
        new WifiIssueDetectorUtil(this.mContext).reportConnectNetwork(this.mWifiConfig);
        this.mWifiManager.connect(this.mWifiConfig, new WifiEntry.ConnectActionListener());
        if (this.mWifiConfig != null) {
            if (this.mSemWifiManager == null) {
                this.mSemWifiManager = (SemWifiManager) this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
            }
            SemWifiManager semWifiManager = this.mSemWifiManager;
            WifiConfiguration wifiConfiguration = this.mWifiConfig;
            semWifiManager.notifyConnect(wifiConfiguration.networkId, wifiConfiguration.getKey());
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final boolean connectionInfoMatches(WifiInfo wifiInfo) {
        if (!wifiInfo.isPasspointAp()) {
            return false;
        }
        int i = BuildCompat.$r8$clinit;
        return TextUtils.equals(this.mUniqueId, wifiInfo.getPasspointUniqueId());
    }

    public final String getBandSummary(int i, WifiInfo wifiInfo) {
        String bandString;
        if (i == 2) {
            setBand(wifiInfo.getFrequency());
        }
        synchronized (this) {
            bandString = Utils.getBandString(this.mBand, this.mContext);
        }
        return bandString;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized int getConnectedState() {
        OsuWifiEntry osuWifiEntry;
        return (isExpired() && super.getConnectedState() == 0 && (osuWifiEntry = this.mOsuWifiEntry) != null) ? osuWifiEntry.getConnectedState() : super.getConnectedState();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getKey() {
        return this.mKey;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getNetworkSelectionDescription() {
        return Utils.getNetworkSelectionDescription(this.mWifiConfig);
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getScanResultDescription() {
        return "";
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final int getSecurity() {
        return 3;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getSecurityString() {
        return this.mContext.getString(R.string.wifitrackerlib_wifi_security_short_eap);
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized List getSecurityTypes() {
        return new ArrayList(this.mTargetSecurityTypes);
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getSsid() {
        WifiInfo wifiInfo = this.mWifiInfo;
        if (wifiInfo != null) {
            return WifiInfo.sanitizeSsid(wifiInfo.getSSID());
        }
        WifiConfiguration wifiConfiguration = this.mWifiConfig;
        return wifiConfiguration != null ? WifiInfo.sanitizeSsid(wifiConfiguration.SSID) : null;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getStandardString() {
        WifiInfo wifiInfo = this.mWifiInfo;
        if (wifiInfo != null) {
            return Utils.getStandardString(wifiInfo.getWifiStandard(), this.mContext);
        }
        if (!this.mCurrentHomeScanResults.isEmpty()) {
            return Utils.getStandardString(((ScanResult) this.mCurrentHomeScanResults.get(0)).getWifiStandard(), this.mContext);
        }
        if (!this.mCurrentRoamingScanResults.isEmpty()) {
            return Utils.getStandardString(((ScanResult) this.mCurrentRoamingScanResults.get(0)).getWifiStandard(), this.mContext);
        }
        this.mTargetScanResults.clear();
        this.mTargetScanResults.addAll(this.mCurrentHomeScanResults);
        this.mTargetScanResults.addAll(this.mCurrentRoamingScanResults);
        return "";
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getSummary(boolean z) {
        StringJoiner stringJoiner;
        String disconnectedDescription;
        try {
            stringJoiner = new StringJoiner(this.mContext.getString(R.string.wifitrackerlib_summary_separator));
            int connectedState = getConnectedState();
            if (isExpired()) {
                if (this.mShowBandSummary) {
                    stringJoiner.add(getBandSummary(connectedState, this.mWifiInfo));
                }
                OsuWifiEntry osuWifiEntry = this.mOsuWifiEntry;
                if (osuWifiEntry != null) {
                    stringJoiner.add(osuWifiEntry.getSummary(z));
                } else {
                    stringJoiner.add(this.mContext.getString(R.string.wifitrackerlib_wifi_passpoint_expired));
                }
            } else {
                if (connectedState == 0) {
                    disconnectedDescription = Utils.getDisconnectedDescription(this.mContext, this.mWifiConfig, this.mSemFlags);
                } else if (connectedState != 1) {
                    disconnectedDescription = null;
                    if (connectedState != 2) {
                        Log.e("PasspointWifiEntry", "getConnectedState() returned unknown state: " + connectedState);
                    } else {
                        NetworkCapabilities networkCapabilities = this.mNetworkCapabilities;
                        if (networkCapabilities == null) {
                            Log.e("PasspointWifiEntry", "Tried to get CONNECTED description, but mNetworkCapabilities was unexpectedly null!");
                        } else {
                            disconnectedDescription = Utils.getConnectedDescription(this.mContext, this.mWifiInfo, this.mWifiConfig, networkCapabilities);
                        }
                    }
                } else {
                    Context context = this.mContext;
                    NetworkInfo networkInfo = this.mNetworkInfo;
                    List list = Utils.defaultSsidList;
                    if (context != null && networkInfo != null) {
                        String[] stringArray = context.getResources().getStringArray(R.array.wifitrackerlib_wifi_status);
                        int ordinal = NetworkInfo.DetailedState.CONNECTING.ordinal();
                        if (ordinal < stringArray.length) {
                            disconnectedDescription = stringArray[ordinal];
                        }
                    }
                    disconnectedDescription = "";
                }
                if (this.mShowBandSummary) {
                    stringJoiner.add(getBandSummary(connectedState, this.mWifiInfo));
                }
                if (!TextUtils.isEmpty(disconnectedDescription)) {
                    stringJoiner.add(disconnectedDescription);
                    if (isSubscription() || semIsEphemeral()) {
                        String verboseLoggingDescription = Utils.getVerboseLoggingDescription(this, this.mSemFlags);
                        if (!TextUtils.isEmpty(verboseLoggingDescription)) {
                            stringJoiner.add(verboseLoggingDescription);
                        }
                    }
                }
            }
            if (getConnectedState() == 2) {
                String warningDescription = Utils.getWarningDescription(this.mContext, this);
                if (!TextUtils.isEmpty(warningDescription)) {
                    stringJoiner.add(warningDescription);
                }
            }
            if (getConnectedState() == 0) {
                String autoConnectDescription = Utils.getAutoConnectDescription(this.mContext, this);
                if (this.mSemFlags.isCarrierNetwork) {
                    String carrierNetworkOffloadDescription = Utils.getCarrierNetworkOffloadDescription(this.mContext, this, this.mWifiManager);
                    if (!TextUtils.isEmpty(carrierNetworkOffloadDescription)) {
                        stringJoiner.add(carrierNetworkOffloadDescription);
                    } else if (!TextUtils.isEmpty(autoConnectDescription)) {
                        stringJoiner.add(autoConnectDescription);
                    }
                } else if (!TextUtils.isEmpty(autoConnectDescription)) {
                    stringJoiner.add(autoConnectDescription);
                }
            }
            String verboseLoggingDescription2 = Utils.getVerboseLoggingDescription(this, this.mSemFlags);
            if (!TextUtils.isEmpty(verboseLoggingDescription2)) {
                stringJoiner.add(verboseLoggingDescription2);
            }
        } catch (Throwable th) {
            throw th;
        }
        return stringJoiner.toString();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getTitle() {
        return this.mFriendlyName;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized WifiConfiguration getWifiConfiguration() {
        return this.mWifiConfig;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean isAutoJoinEnabled() {
        PasspointConfiguration passpointConfiguration = this.mPasspointConfig;
        if (passpointConfiguration != null) {
            return passpointConfiguration.isAutojoinEnabled();
        }
        WifiConfiguration wifiConfiguration = this.mWifiConfig;
        if (wifiConfiguration == null) {
            return true;
        }
        return wifiConfiguration.allowAutojoin;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean isExpired() {
        if (this.mSubscriptionExpirationTimeInMillis <= 0) {
            return false;
        }
        return System.currentTimeMillis() >= this.mSubscriptionExpirationTimeInMillis;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean isMetered() {
        boolean z;
        char c;
        WifiConfiguration wifiConfiguration;
        synchronized (this) {
            int i = this.mMeteredOverride;
            z = false;
            if (i == 1) {
                c = 1;
            } else {
                c = 2;
                if (i != 2) {
                    c = 0;
                }
            }
        }
        return z;
        if (c == 1 || ((wifiConfiguration = this.mWifiConfig) != null && wifiConfiguration.meteredHint)) {
            z = true;
        }
        return z;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean isSaved() {
        return this.mWifiConfig != null;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean isSubscription() {
        return this.mPasspointConfig != null;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean isSuggestion() {
        boolean z;
        WifiConfiguration wifiConfiguration = this.mWifiConfig;
        if (wifiConfiguration != null) {
            z = wifiConfiguration.fromWifiNetworkSuggestion;
        }
        return z;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void onNetworkCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onNetworkCapabilitiesChanged(network, networkCapabilities);
        if (canSignIn() && this.mShouldAutoOpenCaptivePortal) {
            this.mShouldAutoOpenCaptivePortal = false;
            if (canSignIn()) {
                ((ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class)).startCaptivePortalApp(this.mNetwork);
            }
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String toString() {
        StringJoiner stringJoiner = new StringJoiner("][", "[", "]");
        stringJoiner.add("FQDN:" + this.mFqdn);
        stringJoiner.add("FriendlyName:" + this.mFriendlyName);
        if (this.mPasspointConfig != null) {
            stringJoiner.add("UniqueId:" + this.mPasspointConfig.getUniqueId());
        } else if (this.mWifiConfig != null) {
            stringJoiner.add("UniqueId:" + this.mWifiConfig.getKey());
        }
        return super.toString() + stringJoiner;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void updateSecurityTypes() {
        int currentSecurityType;
        WifiInfo wifiInfo = this.mWifiInfo;
        if (wifiInfo == null || (currentSecurityType = wifiInfo.getCurrentSecurityType()) == -1) {
            return;
        }
        this.mTargetSecurityTypes = Collections.singletonList(Integer.valueOf(currentSecurityType));
    }

    public PasspointWifiEntry(WifiTrackerInjector wifiTrackerInjector, Context context, Handler handler, WifiConfiguration wifiConfiguration, WifiManager wifiManager, boolean z) throws IllegalArgumentException {
        super(wifiTrackerInjector, handler, wifiManager, z);
        boolean z2 = false;
        this.mShowBandSummary = false;
        this.mTargetScanResults = new ArrayList();
        this.mCurrentHomeScanResults = new ArrayList();
        this.mCurrentRoamingScanResults = new ArrayList();
        this.mTargetSecurityTypes = Arrays.asList(11, 12);
        this.mShouldAutoOpenCaptivePortal = false;
        this.mMeteredOverride = 0;
        Preconditions.checkNotNull(wifiConfiguration, "Cannot construct with null WifiConfiguration!");
        if (wifiConfiguration.isPasspoint()) {
            this.mWifiConfig = wifiConfiguration;
            String key = wifiConfiguration.getKey();
            this.mUniqueId = key;
            this.mKey = uniqueIdToPasspointWifiEntryKey(key);
            String str = wifiConfiguration.FQDN;
            this.mFqdn = str;
            Preconditions.checkNotNull(str, "Cannot construct with null WifiConfiguration FQDN!");
            WifiConfiguration wifiConfiguration2 = this.mWifiConfig;
            this.mFriendlyName = wifiConfiguration2.providerFriendlyName;
            if (wifiConfiguration2.carrierId != -1 && wifiConfiguration2.isEphemeral() && wifiConfiguration2.fromWifiNetworkSuggestion) {
                z2 = true;
            }
            this.mSemFlags.isCarrierNetwork = z2;
            return;
        }
        throw new IllegalArgumentException("Given WifiConfiguration is not for Passpoint!");
    }
}
