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
import android.util.ArraySet;
import android.util.Log;
import androidx.core.util.Preconditions;
import com.android.systemui.R;
import com.android.wifitrackerlib.WifiEntry;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.samsung.android.wifitrackerlib.WifiIssueDetectorUtil;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PasspointWifiEntry extends WifiEntry implements WifiEntry.WifiEntryCallback {
    public final Context mContext;
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
    public List mTargetSecurityTypes;
    public WifiConfiguration mWifiConfig;

    public PasspointWifiEntry(WifiTrackerInjector wifiTrackerInjector, Context context, Handler handler, PasspointConfiguration passpointConfiguration, WifiManager wifiManager, boolean z) {
        super(handler, wifiManager, z);
        this.mCurrentHomeScanResults = new ArrayList();
        this.mCurrentRoamingScanResults = new ArrayList();
        this.mTargetSecurityTypes = Arrays.asList(11, 12);
        boolean z2 = false;
        this.mShouldAutoOpenCaptivePortal = false;
        this.mShowBandSummary = false;
        this.mMeteredOverride = 0;
        Preconditions.checkNotNull(passpointConfiguration, "Cannot construct with null PasspointConfiguration!");
        this.mContext = context;
        this.mPasspointConfig = passpointConfiguration;
        this.mKey = uniqueIdToPasspointWifiEntryKey(passpointConfiguration.getUniqueId());
        String fqdn = passpointConfiguration.getHomeSp().getFqdn();
        this.mFqdn = fqdn;
        Preconditions.checkNotNull(fqdn, "Cannot construct with null PasspointConfiguration FQDN!");
        this.mFriendlyName = passpointConfiguration.getHomeSp().getFriendlyName();
        this.mSubscriptionExpirationTimeInMillis = passpointConfiguration.getSubscriptionExpirationTimeMillis();
        this.mMeteredOverride = this.mPasspointConfig.getMeteredOverride();
        if (SemWifiEntryFlags.isWifiDeveloperOptionOn(context) && SemWifiEntryFlags.isShowBandInfoOn(context)) {
            z2 = true;
        }
        this.mShowBandSummary = z2;
        this.mSemFlags.getClass();
        int i = SemWifiUtils.$r8$clinit;
        TextUtils.isEmpty(((WifiManager) context.getSystemService(ImsProfile.PDN_WIFI)).getCountryCode());
        semUpdateFlags(this.mPasspointConfig);
    }

    public static String uniqueIdToPasspointWifiEntryKey(String str) {
        Preconditions.checkNotNull(str, "Cannot create key with null unique id!");
        return "PasspointWifiEntry:".concat(str);
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean canConnect() {
        if (!isExpired()) {
            return (this.mLevel == -1 || getConnectedState() != 0 || this.mWifiConfig == null) ? false : true;
        }
        OsuWifiEntry osuWifiEntry = this.mOsuWifiEntry;
        return osuWifiEntry != null && osuWifiEntry.canConnect();
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
        if (wifiInfo.isPasspointAp()) {
            return TextUtils.equals(wifiInfo.getPasspointFqdn(), this.mFqdn);
        }
        return false;
    }

    public final synchronized Set getAllUtf8Ssids() {
        ArraySet arraySet;
        arraySet = new ArraySet();
        Iterator it = ((ArrayList) this.mCurrentHomeScanResults).iterator();
        while (it.hasNext()) {
            arraySet.add(((ScanResult) it.next()).SSID);
        }
        Iterator it2 = ((ArrayList) this.mCurrentRoamingScanResults).iterator();
        while (it2.hasNext()) {
            arraySet.add(((ScanResult) it2.next()).SSID);
        }
        return arraySet;
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
        if (this.mCurrentRoamingScanResults.isEmpty()) {
            return "";
        }
        return Utils.getStandardString(((ScanResult) this.mCurrentRoamingScanResults.get(0)).getWifiStandard(), this.mContext);
    }

    /* JADX WARN: Code restructure failed: missing block: B:78:0x00d8, code lost:
    
        if (r8 != false) goto L57;
     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00ef A[Catch: all -> 0x0140, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x001c, B:8:0x0020, B:10:0x0024, B:11:0x002b, B:14:0x0034, B:15:0x0035, B:18:0x003a, B:19:0x003b, B:21:0x003c, B:23:0x0040, B:24:0x00e9, B:26:0x00ef, B:28:0x00fb, B:29:0x00fe, B:31:0x0104, B:33:0x0112, B:34:0x012b, B:36:0x0137, B:37:0x013a, B:40:0x0116, B:42:0x011c, B:44:0x0128, B:45:0x0049, B:50:0x005e, B:51:0x0091, B:53:0x0095, B:55:0x0099, B:56:0x00a0, B:59:0x00a9, B:60:0x00aa, B:63:0x00af, B:64:0x00b0, B:65:0x00b1, B:67:0x00b7, B:69:0x00c0, B:71:0x00c4, B:73:0x00ca, B:75:0x00ce, B:80:0x00da, B:82:0x00e6, B:83:0x0071, B:84:0x007e, B:85:0x0087, B:13:0x002c, B:58:0x00a1), top: B:3:0x0003, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0104 A[Catch: all -> 0x0140, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x001c, B:8:0x0020, B:10:0x0024, B:11:0x002b, B:14:0x0034, B:15:0x0035, B:18:0x003a, B:19:0x003b, B:21:0x003c, B:23:0x0040, B:24:0x00e9, B:26:0x00ef, B:28:0x00fb, B:29:0x00fe, B:31:0x0104, B:33:0x0112, B:34:0x012b, B:36:0x0137, B:37:0x013a, B:40:0x0116, B:42:0x011c, B:44:0x0128, B:45:0x0049, B:50:0x005e, B:51:0x0091, B:53:0x0095, B:55:0x0099, B:56:0x00a0, B:59:0x00a9, B:60:0x00aa, B:63:0x00af, B:64:0x00b0, B:65:0x00b1, B:67:0x00b7, B:69:0x00c0, B:71:0x00c4, B:73:0x00ca, B:75:0x00ce, B:80:0x00da, B:82:0x00e6, B:83:0x0071, B:84:0x007e, B:85:0x0087, B:13:0x002c, B:58:0x00a1), top: B:3:0x0003, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0137 A[Catch: all -> 0x0140, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x001c, B:8:0x0020, B:10:0x0024, B:11:0x002b, B:14:0x0034, B:15:0x0035, B:18:0x003a, B:19:0x003b, B:21:0x003c, B:23:0x0040, B:24:0x00e9, B:26:0x00ef, B:28:0x00fb, B:29:0x00fe, B:31:0x0104, B:33:0x0112, B:34:0x012b, B:36:0x0137, B:37:0x013a, B:40:0x0116, B:42:0x011c, B:44:0x0128, B:45:0x0049, B:50:0x005e, B:51:0x0091, B:53:0x0095, B:55:0x0099, B:56:0x00a0, B:59:0x00a9, B:60:0x00aa, B:63:0x00af, B:64:0x00b0, B:65:0x00b1, B:67:0x00b7, B:69:0x00c0, B:71:0x00c4, B:73:0x00ca, B:75:0x00ce, B:80:0x00da, B:82:0x00e6, B:83:0x0071, B:84:0x007e, B:85:0x0087, B:13:0x002c, B:58:0x00a1), top: B:3:0x0003, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0116 A[Catch: all -> 0x0140, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x001c, B:8:0x0020, B:10:0x0024, B:11:0x002b, B:14:0x0034, B:15:0x0035, B:18:0x003a, B:19:0x003b, B:21:0x003c, B:23:0x0040, B:24:0x00e9, B:26:0x00ef, B:28:0x00fb, B:29:0x00fe, B:31:0x0104, B:33:0x0112, B:34:0x012b, B:36:0x0137, B:37:0x013a, B:40:0x0116, B:42:0x011c, B:44:0x0128, B:45:0x0049, B:50:0x005e, B:51:0x0091, B:53:0x0095, B:55:0x0099, B:56:0x00a0, B:59:0x00a9, B:60:0x00aa, B:63:0x00af, B:64:0x00b0, B:65:0x00b1, B:67:0x00b7, B:69:0x00c0, B:71:0x00c4, B:73:0x00ca, B:75:0x00ce, B:80:0x00da, B:82:0x00e6, B:83:0x0071, B:84:0x007e, B:85:0x0087, B:13:0x002c, B:58:0x00a1), top: B:3:0x0003, inners: #1, #2 }] */
    @Override // com.android.wifitrackerlib.WifiEntry
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final synchronized String getSummary(boolean z) {
        StringJoiner stringJoiner;
        String disconnectedDescription;
        NetworkInfo networkInfo;
        String verboseLoggingDescription;
        stringJoiner = new StringJoiner(this.mContext.getString(R.string.wifitrackerlib_summary_separator));
        int connectedState = getConnectedState();
        if (isExpired()) {
            if (this.mShowBandSummary) {
                WifiInfo wifiInfo = this.mWifiInfo;
                if (connectedState == 2) {
                    setBand(wifiInfo.getFrequency());
                }
                synchronized (this) {
                    stringJoiner.add(Utils.getBandString(this.mBand, this.mContext));
                }
            }
            OsuWifiEntry osuWifiEntry = this.mOsuWifiEntry;
            if (osuWifiEntry != null) {
                stringJoiner.add(osuWifiEntry.getSummary(z));
            } else {
                stringJoiner.add(this.mContext.getString(R.string.wifitrackerlib_wifi_passpoint_expired));
            }
            if (getConnectedState() == 2) {
                String warningDescription = Utils.getWarningDescription(this.mContext, this);
                if (!TextUtils.isEmpty(warningDescription)) {
                    stringJoiner.add(warningDescription);
                }
            }
            if (!this.mSemFlags.isCarrierNetwork) {
                String carrierNetworkOffloadDescription = Utils.getCarrierNetworkOffloadDescription(this.mContext, this, this.mWifiManager);
                if (!TextUtils.isEmpty(carrierNetworkOffloadDescription)) {
                    stringJoiner.add(carrierNetworkOffloadDescription);
                }
            } else if (getConnectedState() == 0) {
                String autoConnectDescription = Utils.getAutoConnectDescription(this.mContext, this);
                if (!TextUtils.isEmpty(autoConnectDescription)) {
                    stringJoiner.add(autoConnectDescription);
                }
            }
            verboseLoggingDescription = Utils.getVerboseLoggingDescription(this, this.mSemFlags);
            if (!TextUtils.isEmpty(verboseLoggingDescription)) {
                stringJoiner.add(verboseLoggingDescription);
            }
        } else {
            boolean z2 = true;
            if (connectedState == 0) {
                disconnectedDescription = Utils.getDisconnectedDescription(this.mContext, this.mWifiConfig, this.mSemFlags);
            } else if (connectedState == 1) {
                disconnectedDescription = Utils.getConnectingDescription(this.mContext, this.mNetworkInfo);
            } else if (connectedState != 2) {
                Log.e("PasspointWifiEntry", "getConnectedState() returned unknown state: " + connectedState);
                disconnectedDescription = null;
            } else {
                disconnectedDescription = Utils.getConnectedDescription(this.mContext, this.mWifiInfo, this.mWifiConfig, this.mNetworkCapabilities);
            }
            if (this.mShowBandSummary) {
                WifiInfo wifiInfo2 = this.mWifiInfo;
                if (connectedState == 2) {
                    setBand(wifiInfo2.getFrequency());
                }
                synchronized (this) {
                    stringJoiner.add(Utils.getBandString(this.mBand, this.mContext));
                }
            }
            if (!TextUtils.isEmpty(disconnectedDescription)) {
                stringJoiner.add(disconnectedDescription);
                if (!isSubscription()) {
                    WifiInfo wifiInfo3 = this.mWifiInfo;
                    if (wifiInfo3 == null || !wifiInfo3.isEphemeral() || (networkInfo = this.mNetworkInfo) == null || networkInfo.getState() == NetworkInfo.State.DISCONNECTED) {
                        z2 = false;
                    }
                }
                String verboseLoggingDescription2 = Utils.getVerboseLoggingDescription(this, this.mSemFlags);
                if (!TextUtils.isEmpty(verboseLoggingDescription2)) {
                    stringJoiner.add(verboseLoggingDescription2);
                }
            }
            if (getConnectedState() == 2) {
            }
            if (!this.mSemFlags.isCarrierNetwork) {
            }
            verboseLoggingDescription = Utils.getVerboseLoggingDescription(this, this.mSemFlags);
            if (!TextUtils.isEmpty(verboseLoggingDescription)) {
            }
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

    public final synchronized boolean isExpired() {
        if (this.mSubscriptionExpirationTimeInMillis <= 0) {
            return false;
        }
        return System.currentTimeMillis() >= this.mSubscriptionExpirationTimeInMillis;
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

    public final synchronized void updatePasspointConfig(PasspointConfiguration passpointConfiguration) {
        this.mPasspointConfig = passpointConfiguration;
        if (passpointConfiguration != null) {
            this.mSubscriptionExpirationTimeInMillis = passpointConfiguration.getSubscriptionExpirationTimeMillis();
            this.mMeteredOverride = passpointConfiguration.getMeteredOverride();
        }
        semUpdateFlags(this.mPasspointConfig);
        notifyOnUpdated();
    }

    public final synchronized void updateScanResultInfo(WifiConfiguration wifiConfiguration, List list, List list2) {
        this.mWifiConfig = wifiConfiguration;
        ((ArrayList) this.mCurrentHomeScanResults).clear();
        ((ArrayList) this.mCurrentRoamingScanResults).clear();
        if (list != null) {
            ((ArrayList) this.mCurrentHomeScanResults).addAll(list);
        }
        if (list2 != null) {
            ((ArrayList) this.mCurrentRoamingScanResults).addAll(list2);
        }
        if (this.mWifiConfig != null) {
            SemWifiEntryFlags semWifiEntryFlags = this.mSemFlags;
            int i = 0;
            semWifiEntryFlags.hasVHTVSICapabilities = false;
            semWifiEntryFlags.wifiStandard = 0;
            semWifiEntryFlags.has6EStandard = false;
            semWifiEntryFlags.staCount = -1;
            updateSecurityTypes();
            ArrayList arrayList = new ArrayList();
            if (list != null && !list.isEmpty()) {
                arrayList.addAll(list);
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ScanResult scanResult = (ScanResult) it.next();
                    semUpdateFlags(scanResult);
                    int i2 = scanResult.frequency;
                    if (i < i2) {
                        setBand(i2);
                        this.mFrequency = scanResult.frequency;
                        i = i2;
                    }
                }
            } else if (list2 != null && !list2.isEmpty()) {
                arrayList.addAll(list2);
                Iterator it2 = list2.iterator();
                while (it2.hasNext()) {
                    ScanResult scanResult2 = (ScanResult) it2.next();
                    semUpdateFlags(scanResult2);
                    int i3 = scanResult2.frequency;
                    if (i < i3) {
                        setBand(i3);
                        this.mFrequency = i3;
                        i = i3;
                    }
                }
            }
            ScanResult bestScanResultByLevel = Utils.getBestScanResultByLevel(arrayList);
            if (bestScanResultByLevel != null) {
                this.mWifiConfig.SSID = "\"" + bestScanResultByLevel.SSID + "\"";
                updateBestRssi(bestScanResultByLevel);
                this.mBssid = bestScanResultByLevel.BSSID;
            }
            if (getConnectedState() == 0) {
                int calculateSignalLevel = bestScanResultByLevel != null ? SemWifiUtils.calculateSignalLevel(this.mRssi) : -1;
                this.mLevel = calculateSignalLevel;
                if (calculateSignalLevel == -1) {
                    this.mRssi = -127;
                }
            }
        } else {
            this.mLevel = -1;
        }
        notifyOnUpdated();
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

    public PasspointWifiEntry(WifiTrackerInjector wifiTrackerInjector, Context context, Handler handler, WifiConfiguration wifiConfiguration, WifiManager wifiManager, boolean z) {
        super(handler, wifiManager, z);
        this.mCurrentHomeScanResults = new ArrayList();
        this.mCurrentRoamingScanResults = new ArrayList();
        this.mTargetSecurityTypes = Arrays.asList(11, 12);
        boolean z2 = false;
        this.mShouldAutoOpenCaptivePortal = false;
        this.mShowBandSummary = false;
        this.mMeteredOverride = 0;
        Preconditions.checkNotNull(wifiConfiguration, "Cannot construct with null WifiConfiguration!");
        if (wifiConfiguration.isPasspoint()) {
            this.mContext = context;
            this.mWifiConfig = wifiConfiguration;
            this.mKey = uniqueIdToPasspointWifiEntryKey(wifiConfiguration.getKey());
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
