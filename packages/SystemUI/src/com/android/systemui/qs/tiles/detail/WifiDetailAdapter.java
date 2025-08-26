package com.android.systemui.qs.tiles.detail;

import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.SemStatusBarManager;
import android.app.admin.IDevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.CvOperator;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSDetailItems;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.qs.tiles.WifiTile;
import com.android.systemui.statusbar.connectivity.AccessPointController;
import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl.AnonymousClass7;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.Utils;
import com.android.wifitrackerlib.HotspotNetworkEntry;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiPickerTracker;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.wifi.SemWifiApBleScanResult;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class WifiDetailAdapter implements DetailAdapter, AccessPointController.AccessPointCallback, AccessPointController.WifiApBleStateChangeCallback, QSDetailItems.Callback {
    public static final boolean DEBUG = Log.isLoggable("WifiDetailAdapter", 3);
    public final AccessPointController mAccessPointController;
    public WifiEntry[] mAccessPoints;
    public final ActivityStarter mActivityStarter;
    public ViewGroup mAvailable;
    public QSDetailItems mAvailableItems;
    public ViewGroup mConnected;
    public View mConnectedNetworksTitle;
    public final Context mContext;
    public final NetworkController mController;
    public final Handler mHandler;
    public ViewGroup mHotspotLive;
    public QSDetailItems mHotspotLiveItems;
    public WifiTile.CallbackInfo mInfo;
    public ViewGroup mInstantHotspot;
    public QSDetailItems mInstantHotspotItems;
    public QSDetailItems mItems;
    public final KeyguardManager mKeyguardManager;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    private SettingsHelper mSettingsHelper;
    public final SemStatusBarManager mStatusBarManager;
    public final WifiManager mWifiManager;
    public final WifiTile mWifiTile;
    public final ArrayList instantHotspotList = new ArrayList();
    public boolean mIsSatelliteModeOn = false;
    public boolean mIsHavingConvertView = false;

    public WifiDetailAdapter(Context context, Handler handler, ActivityStarter activityStarter, AccessPointController accessPointController, PanelInteractor panelInteractor, NetworkController networkController, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, WifiTile wifiTile) {
        this.mContext = context;
        this.mHandler = handler;
        this.mActivityStarter = activityStarter;
        this.mAccessPointController = accessPointController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        this.mWifiTile = wifiTile;
        this.mController = networkController;
        this.mSettingsHelper = settingsHelper;
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        this.mStatusBarManager = (SemStatusBarManager) context.getSystemService("sem_statusbar");
        this.mKeyguardManager = (KeyguardManager) context.getSystemService("keyguard");
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0120  */
    @Override // com.android.systemui.plugins.qs.DetailAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
        boolean z;
        StringBuilder sb = new StringBuilder("createDetailView convertView=");
        sb.append(view != null);
        sb.append(" State : ");
        WifiTile wifiTile = this.mWifiTile;
        sb.append(((QSTile.BooleanState) wifiTile.mState).value);
        sb.append(" enabled : ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, this.mInfo.enabled, "WifiDetailAdapter");
        this.mAccessPoints = null;
        if (!this.mIsHavingConvertView) {
            view = null;
        }
        if (view == null) {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.qs_detail_wifi, viewGroup, false);
            ViewGroup viewGroup2 = (ViewGroup) view.findViewById(R.id.current_network);
            this.mConnected = viewGroup2;
            this.mConnectedNetworksTitle = viewGroup2.findViewById(R.id.connected_networks_title);
            QSDetailItems qSDetailItemsConvertOrInflate = QSDetailItems.convertOrInflate(context, this.mConnected);
            this.mItems = qSDetailItemsConvertOrInflate;
            this.mConnected.addView(qSDetailItemsConvertOrInflate);
            this.mItems.setTagSuffix("Wifi");
            if (Utils.SPF_SupportMobileApEnhanced || Utils.SPF_SupportMobileApEnhancedLite || Utils.SPF_SupportMobileApEnhancedWifiOnlyLite) {
                ViewGroup viewGroup3 = (ViewGroup) view.findViewById(R.id.hotspot_live_networks);
                this.mHotspotLive = viewGroup3;
                ((TextView) viewGroup3.findViewById(R.id.hotspot_live_networks_title)).setText(CvOperator.getHotspotStringID(R.string.sec_wifi_hotspot_live_preference_category_title));
                QSDetailItems qSDetailItemsConvertOrInflate2 = QSDetailItems.convertOrInflate(context, this.mHotspotLive);
                this.mHotspotLiveItems = qSDetailItemsConvertOrInflate2;
                qSDetailItemsConvertOrInflate2.setTagSuffix("Hotspot.Available");
                this.mHotspotLive.addView(this.mHotspotLiveItems);
            }
            if (Utils.SPF_SupportInstantHotspot) {
                ViewGroup viewGroup4 = (ViewGroup) view.findViewById(R.id.instant_hotspot_networks);
                this.mInstantHotspot = viewGroup4;
                ((TextView) viewGroup4.findViewById(R.id.instant_hotspot_networks_title)).setText("Instant Hotspot");
                QSDetailItems qSDetailItemsConvertOrInflate3 = QSDetailItems.convertOrInflate(context, this.mInstantHotspot);
                this.mInstantHotspotItems = qSDetailItemsConvertOrInflate3;
                qSDetailItemsConvertOrInflate3.setTagSuffix("InstantHotspot.Available");
                this.mInstantHotspot.addView(this.mInstantHotspotItems);
            }
            ViewGroup viewGroup5 = (ViewGroup) view.findViewById(R.id.available_networks);
            this.mAvailable = viewGroup5;
            QSDetailItems qSDetailItemsConvertOrInflate4 = QSDetailItems.convertOrInflate(context, viewGroup5);
            this.mAvailableItems = qSDetailItemsConvertOrInflate4;
            qSDetailItemsConvertOrInflate4.setTagSuffix("Wifi.Available");
            this.mAvailable.addView(this.mAvailableItems);
            this.mIsHavingConvertView = true;
        }
        QSDetailItems qSDetailItems = this.mAvailableItems;
        boolean z2 = ((QSTile.BooleanState) wifiTile.mState).value;
        if (z2) {
            if (this.mItems.mAdapter.getCount() <= 0) {
                WifiTile.CallbackInfo callbackInfo = this.mInfo;
                if (!callbackInfo.enabled || callbackInfo.wifiSignalIconId <= 0 || callbackInfo.ssid == null) {
                }
            }
            z = true;
        } else {
            z = false;
        }
        StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("isConnectedVisible = ", ",getItemCount() = ", z);
        sbM.append(this.mItems.mAdapter.getCount());
        sbM.append(",mWifiConnected = ");
        WifiTile.CallbackInfo callbackInfo2 = this.mInfo;
        ActionBarContextView$$ExternalSyntheticOutline0.m(sbM, callbackInfo2.enabled && callbackInfo2.wifiSignalIconId > 0 && callbackInfo2.ssid != null, "WifiDetailAdapter");
        qSDetailItems.setEmptyState(z2 ? z ? R.string.quick_settings_wifi_detail_scanning_text : R.string.quick_settings_wifi_scanning_text : R.string.quick_settings_wifi_detail_off_text);
        if (Utils.SPF_SupportInstantHotspot) {
            this.mInstantHotspotItems.setCallback(this);
        }
        this.mItems.setCallback(this);
        boolean z3 = Utils.SPF_SupportMobileApEnhanced;
        AccessPointController accessPointController = this.mAccessPointController;
        if (z3 || Utils.SPF_SupportMobileApEnhancedLite || Utils.SPF_SupportMobileApEnhancedWifiOnlyLite) {
            SemWifiManager semWifiManager = ((AccessPointControllerImpl) accessPointController).mSemWifiManager;
            if (semWifiManager != null) {
                semWifiManager.setWifiSettingsForegroundState(1);
            }
            Log.d("WifiDetailAdapter", "adding wififoreground");
            this.mHotspotLiveItems.setCallback(this);
        }
        this.mAvailableItems.setCallback(this);
        this.mItems.setItems(null);
        SemWifiEntryFlags.isWepAllowed = -1;
        ((AccessPointControllerImpl) accessPointController).scanForAccessPoints();
        wifiTile.fireScanStateChanged(((QSTile.BooleanState) wifiTile.mState).value);
        this.mConnected.setVisibility(this.mItems.mAdapter.getCount() > 0 ? 0 : 8);
        this.mConnectedNetworksTitle.setVisibility(this.mItems.mAdapter.getCount() <= 0 ? 8 : 0);
        setItemsVisible(((QSTile.BooleanState) wifiTile.mState).value);
        return view;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final int getMetricsCategory() {
        return 152;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final Intent getSettingsIntent() {
        return WifiTile.WIFI_SETTINGS;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final CharSequence getTitle() {
        return this.mContext.getString(R.string.quick_settings_sec_wifi_label).trim();
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final boolean getToggleEnabled() {
        return (((QSTile.BooleanState) this.mWifiTile.mState).state == 0 || this.mIsSatelliteModeOn) ? false : true;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final Boolean getToggleState() {
        return Boolean.valueOf(((QSTile.BooleanState) this.mWifiTile.mState).value);
    }

    @Override // com.android.systemui.statusbar.connectivity.AccessPointController.AccessPointCallback
    public final void onAccessPointsChanged(List list) {
        int i;
        WifiInfo wifiInfo;
        WifiEntry[] wifiEntryArr = (WifiEntry[]) list.toArray(new WifiEntry[list.size()]);
        this.mAccessPoints = wifiEntryArr;
        int i2 = 0;
        for (WifiEntry wifiEntry : wifiEntryArr) {
            if (wifiEntry != null && wifiEntry.getLevel() != -1) {
                i2++;
            }
        }
        WifiEntry[] wifiEntryArr2 = this.mAccessPoints;
        if (i2 != wifiEntryArr2.length) {
            this.mAccessPoints = new WifiEntry[i2];
            int i3 = 0;
            for (WifiEntry wifiEntry2 : wifiEntryArr2) {
                if (wifiEntry2 != null && wifiEntry2.getLevel() != -1) {
                    this.mAccessPoints[i3] = wifiEntry2;
                    i3++;
                }
            }
        }
        if (this.mItems == null) {
            return;
        }
        WifiEntry[] wifiEntryArr3 = this.mAccessPoints;
        WifiTile wifiTile = this.mWifiTile;
        if ((wifiEntryArr3 == null || wifiEntryArr3.length <= 0) && this.mInfo.enabled) {
            wifiTile.fireScanStateChanged(true);
        } else {
            wifiTile.fireScanStateChanged(false);
        }
        if (!this.mInfo.enabled) {
            this.mItems.setItems(null);
            this.mAvailableItems.setItems(null);
            this.mConnected.setVisibility(8);
            this.mAvailable.findViewById(R.id.available_networks_group).setVisibility(8);
            if (Utils.SPF_SupportMobileApEnhanced || Utils.SPF_SupportMobileApEnhancedLite || Utils.SPF_SupportMobileApEnhancedWifiOnlyLite) {
                this.mHotspotLiveItems.setItems(null);
                this.mHotspotLive.setVisibility(8);
            }
            if (Utils.SPF_SupportInstantHotspot) {
                this.mInstantHotspotItems.setItems(null);
                this.mInstantHotspot.setVisibility(8);
                return;
            }
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        this.instantHotspotList.clear();
        if (this.mAccessPoints != null) {
            int i4 = 0;
            while (true) {
                WifiEntry[] wifiEntryArr4 = this.mAccessPoints;
                if (i4 >= wifiEntryArr4.length) {
                    break;
                }
                WifiEntry wifiEntry3 = wifiEntryArr4[i4];
                QSDetailItems.Item item = new QSDetailItems.Item();
                int security = wifiEntry3.getSecurity();
                item.tag = wifiEntry3;
                AccessPointControllerImpl accessPointControllerImpl = (AccessPointControllerImpl) this.mAccessPointController;
                accessPointControllerImpl.getClass();
                int level = wifiEntry3.getLevel();
                if (level < 0) {
                    level = 0;
                }
                if (level > 4) {
                    level = 4;
                }
                SemWifiEntryFlags semWifiEntryFlags = wifiEntry3.mSemFlags;
                if (semWifiEntryFlags.isSupportedWifi7 && ((wifiInfo = wifiEntry3.mWifiInfo) == null ? semWifiEntryFlags.wifiStandard >= 8 : wifiInfo.getWifiStandard() == 8)) {
                    boolean zIsOpenNetwork = AccessPointControllerImpl.isOpenNetwork(wifiEntry3);
                    int[][] iArr = AccessPointControllerImpl.ICONS_WIFI7;
                    i = !zIsOpenNetwork ? iArr[level][1] : iArr[level][0];
                } else {
                    WifiInfo wifiInfo2 = wifiEntry3.mWifiInfo;
                    if (wifiInfo2 != null ? wifiEntry3.checkWifi6EStandard(wifiInfo2.getFrequency(), wifiInfo2.getWifiStandard()) : semWifiEntryFlags.has6EStandard) {
                        boolean zIsOpenNetwork2 = AccessPointControllerImpl.isOpenNetwork(wifiEntry3);
                        int[][] iArr2 = AccessPointControllerImpl.ICONS_WIFI6E;
                        i = !zIsOpenNetwork2 ? iArr2[level][1] : iArr2[level][0];
                    } else {
                        WifiInfo wifiInfo3 = wifiEntry3.mWifiInfo;
                        if (wifiInfo3 == null ? semWifiEntryFlags.wifiStandard >= 6 : wifiInfo3.getWifiStandard() == 6) {
                            boolean zIsOpenNetwork3 = AccessPointControllerImpl.isOpenNetwork(wifiEntry3);
                            int[][] iArr3 = AccessPointControllerImpl.ICONS_WIFI6;
                            i = !zIsOpenNetwork3 ? iArr3[level][1] : iArr3[level][0];
                        } else {
                            WifiInfo wifiInfo4 = wifiEntry3.mWifiInfo;
                            if (wifiInfo4 == null ? semWifiEntryFlags.wifiStandard >= 5 : wifiInfo4.getWifiStandard() == 5) {
                                boolean zIsOpenNetwork4 = AccessPointControllerImpl.isOpenNetwork(wifiEntry3);
                                int[][] iArr4 = AccessPointControllerImpl.ICONS_WIFI5;
                                i = !zIsOpenNetwork4 ? iArr4[level][1] : iArr4[level][0];
                            } else {
                                boolean zIsOpenNetwork5 = AccessPointControllerImpl.isOpenNetwork(wifiEntry3);
                                int[][] iArr5 = AccessPointControllerImpl.ICONS_WIFI;
                                i = !zIsOpenNetwork5 ? iArr5[level][1] : iArr5[level][0];
                            }
                        }
                    }
                }
                item.iconResId = i;
                item.line1 = wifiEntry3.getTitle();
                StringBuilder sb = new StringBuilder();
                if (!SemWifiEntryFlags.isWepAllowed(this.mContext) && security == 1) {
                    sb.append(this.mContext.getResources().getString(R.string.wifi_wep_networks_blocked_summary));
                } else if (Settings.Secure.getInt(this.mContext.getContentResolver(), "rampart_blocked_unsecure_wifi_autojoin", 0) == 1 && ((security == 0 || security == 1 || security == 4) && wifiEntry3.getConnectedState() == 0 && !wifiEntry3.isAutoJoinEnabled())) {
                    sb.append(this.mContext.getResources().getString(R.string.wifi_auto_blocker_blocked_summary));
                } else {
                    sb.append(wifiEntry3.getSummary(true));
                }
                item.line2 = sb.toString();
                if (wifiEntry3.getConnectedState() == 2) {
                    if (wifiEntry3 instanceof HotspotNetworkEntry) {
                        Log.d("WifiDetailAdapter.InstantHotspot", "updating signal strength");
                        item.iconResId = accessPointControllerImpl.getInstantHotspotIcon((HotspotNetworkEntry) wifiEntry3);
                    }
                    item.isActive = true;
                    arrayList.add(item);
                } else if (wifiEntry3 instanceof HotspotNetworkEntry) {
                    Log.d("WifiDetailAdapter.InstantHotspot", "device:" + wifiEntry3.getTitle());
                    if (Utils.SPF_SupportInstantHotspot) {
                        item.iconResId = accessPointControllerImpl.getInstantHotspotIcon((HotspotNetworkEntry) wifiEntry3);
                        this.instantHotspotList.add(item);
                    }
                } else {
                    arrayList2.add(item);
                }
                i4++;
            }
        }
        updateHotspotItems();
        boolean z = UserHandle.myUserId() == 0;
        QSDetailItems qSDetailItems = this.mInstantHotspotItems;
        if (qSDetailItems != null && Utils.SPF_SupportInstantHotspot && z) {
            ArrayList arrayList3 = this.instantHotspotList;
            qSDetailItems.setItems((QSDetailItems.Item[]) arrayList3.toArray(new QSDetailItems.Item[arrayList3.size()]));
            this.mInstantHotspotItems.post(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.WifiDetailAdapter.5
                @Override // java.lang.Runnable
                public final void run() {
                    WifiDetailAdapter.this.mInstantHotspot.setVisibility(WifiDetailAdapter.this.mInstantHotspotItems.mAdapter.getCount() > 0 ? 0 : 8);
                }
            });
        }
        this.mItems.setItems((QSDetailItems.Item[]) arrayList.toArray(new QSDetailItems.Item[arrayList.size()]));
        this.mAvailableItems.setItems((QSDetailItems.Item[]) arrayList2.toArray(new QSDetailItems.Item[arrayList2.size()]));
        int i5 = arrayList.size() == 0 ? 8 : 0;
        this.mConnected.setVisibility(i5);
        this.mConnectedNetworksTitle.setVisibility(i5);
        this.mAvailableItems.post(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.WifiDetailAdapter.3
            @Override // java.lang.Runnable
            public final void run() {
                WifiDetailAdapter.this.mAvailable.findViewById(R.id.available_networks_group).setVisibility(WifiDetailAdapter.this.mAvailableItems.mAdapter.getCount() > 0 ? 0 : 8);
            }
        });
    }

    @Override // com.android.systemui.qs.QSDetailItems.Callback
    public final void onDetailItemClick(QSDetailItems.Item item) {
        Object obj;
        KeyguardManager keyguardManager;
        String string;
        if (item == null || (obj = item.tag) == null) {
            return;
        }
        boolean z = obj instanceof WifiEntry;
        AccessPointController accessPointController = this.mAccessPointController;
        boolean zConnectToSmartMHS = false;
        int i = 0;
        if (!z) {
            if ((Utils.SPF_SupportMobileApEnhanced || Utils.SPF_SupportMobileApEnhancedLite || Utils.SPF_SupportMobileApEnhancedWifiOnlyLite) && (obj instanceof SemWifiApBleScanResult) && !item.isDisabled) {
                SemWifiApBleScanResult semWifiApBleScanResult = (SemWifiApBleScanResult) obj;
                AccessPointControllerImpl accessPointControllerImpl = (AccessPointControllerImpl) accessPointController;
                accessPointControllerImpl.getClass();
                if (semWifiApBleScanResult.mBattery > 15) {
                    zConnectToSmartMHS = accessPointControllerImpl.mWifiPickerTracker.mSemWifiManager.connectToSmartMHS(semWifiApBleScanResult.mDevice, semWifiApBleScanResult.mMHSdeviceType, semWifiApBleScanResult.mhidden, semWifiApBleScanResult.mSecurity, semWifiApBleScanResult.mWifiMac, semWifiApBleScanResult.mUserName, semWifiApBleScanResult.version, semWifiApBleScanResult.isWifiProfileShareEnabled);
                }
                Log.d("AccessPointController.AutoHotspot", "triggerWifiApBleConnection() : bleDevice -> " + semWifiApBleScanResult.mSSID + " mBattery: " + semWifiApBleScanResult.mBattery + " ret: " + zConnectToSmartMHS);
                if (zConnectToSmartMHS) {
                    Log.d("WifiDetailAdapter.AutoHotspot", "onDetailItemClick() - Triggering updateHotspotItems for connecting with apBLE.mWifiMac-> " + semWifiApBleScanResult.mWifiMac);
                    updateHotspotItems();
                    return;
                }
                Log.d("WifiDetailAdapter.AutoHotspot", "onDetailItemClick() - Triggering updateHotspotItems for connection time out with apBLE.mWifiMac-> " + semWifiApBleScanResult.mWifiMac);
                updateHotspotItems();
                return;
            }
            return;
        }
        WifiEntry wifiEntry = (WifiEntry) obj;
        int security = wifiEntry.getSecurity();
        if (!SemWifiEntryFlags.isWepAllowed(this.mContext) && security == 1) {
            String ssid = wifiEntry.getSsid();
            Intent intent = new Intent();
            intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.samsung.android.settings.wifi.WifiWarning");
            intent.setFlags(343932928);
            intent.putExtra("req_type", 0);
            intent.putExtra("extra_type", 9);
            intent.putExtra("ssid", ssid);
            try {
                this.mContext.startActivity(intent);
                return;
            } catch (ActivityNotFoundException unused) {
                return;
            }
        }
        if (wifiEntry.getConnectedState() != 0) {
            if (wifiEntry.getConnectedState() == 2) {
                ((AccessPointControllerImpl) accessPointController).startSettings(wifiEntry);
                return;
            }
            return;
        }
        AccessPointControllerImpl accessPointControllerImpl2 = (AccessPointControllerImpl) accessPointController;
        accessPointControllerImpl2.getClass();
        if (AccessPointControllerImpl.DEBUG) {
            if (wifiEntry.getWifiConfiguration() != null) {
                RecyclerView$$ExternalSyntheticOutline0.m(wifiEntry.getWifiConfiguration().networkId, "AccessPointController", new StringBuilder("connect networkId="));
            } else {
                Log.d("AccessPointController", "connect to unsaved network " + wifiEntry.getTitle());
            }
        }
        if (!wifiEntry.mSemFlags.isOpenRoamingNetwork || ((string = Settings.Global.getString(accessPointControllerImpl2.mWifiPickerTrackerFactory.context.getContentResolver(), "sem_wifi_allowed_oauth_provider")) != null && string.contains("[cisco]"))) {
            boolean zIsSaved = wifiEntry.isSaved();
            AccessPointControllerImpl.AnonymousClass2 anonymousClass2 = accessPointControllerImpl2.mConnectCallback;
            if (zIsSaved || AccessPointControllerImpl.isOpenNetwork(wifiEntry)) {
                wifiEntry.connect(anonymousClass2);
                this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.WifiDetailAdapter.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((SQSTileImpl) WifiDetailAdapter.this.mWifiTile).mHandler.obtainMessage(103, 0, 0).sendToTarget();
                    }
                }, 250L);
                return;
            }
            accessPointControllerImpl2.startSettings(wifiEntry);
        } else {
            Intent intent2 = new Intent("android.settings.WIFI_SETTINGS");
            intent2.putExtra("wifi_start_connect_ssid", "wifi_start_openroaming");
            intent2.addFlags(268435456);
            ArrayList arrayList = accessPointControllerImpl2.mCallbacks;
            int size = arrayList.size();
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
                ((AccessPointController.AccessPointCallback) obj2).onSettingsActivityTriggered(intent2);
            }
        }
        SemStatusBarManager semStatusBarManager = this.mStatusBarManager;
        if (semStatusBarManager == null || !semStatusBarManager.isPanelExpanded() || (keyguardManager = this.mKeyguardManager) == null || keyguardManager.isKeyguardLocked()) {
            return;
        }
        this.mStatusBarManager.collapsePanels();
    }

    @Override // com.android.systemui.statusbar.connectivity.AccessPointController.AccessPointCallback
    public final void onSettingsActivityTriggered(Intent intent) {
        KeyguardManager keyguardManager = this.mKeyguardManager;
        if (keyguardManager == null || !keyguardManager.isKeyguardLocked()) {
            this.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0);
            return;
        }
        PendingIntent activity = PendingIntent.getActivity(this.mContext, 0, intent, 201326592);
        Intent intent2 = new Intent();
        intent2.putExtra("afterKeyguardGone", true);
        this.mKeyguardManager.semSetPendingIntentAfterUnlock(activity, intent2);
    }

    public final void setItemsVisible(boolean z) {
        EmergencyButtonController$$ExternalSyntheticOutline0.m(" setItemsVisible : ", "WifiDetailAdapter", z);
        QSDetailItems qSDetailItems = this.mItems;
        if (qSDetailItems == null || z) {
            return;
        }
        qSDetailItems.setItems(null);
        this.mAvailableItems.setItems(null);
        this.mItems.post(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.WifiDetailAdapter.2
            @Override // java.lang.Runnable
            public final void run() {
                WifiDetailAdapter.this.mConnected.setVisibility(8);
                WifiDetailAdapter.this.mAvailable.findViewById(R.id.available_networks_group).setVisibility(8);
            }
        });
        if (Utils.SPF_SupportMobileApEnhanced || Utils.SPF_SupportMobileApEnhancedLite || Utils.SPF_SupportMobileApEnhancedWifiOnlyLite) {
            this.mHotspotLiveItems.setItems(null);
        }
        if (Utils.SPF_SupportInstantHotspot) {
            this.mInstantHotspotItems.setItems(null);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x003c, code lost:
    
        if (r2.semGetAllowWifi((android.content.ComponentName) null, android.app.ActivityManager.getCurrentUser()) == false) goto L36;
     */
    @Override // com.android.systemui.plugins.qs.DetailAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setToggleState(boolean z) {
        boolean z2 = DEBUG;
        if (z2) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("setToggleState ", "WifiDetailAdapter", z);
        }
        MetricsLogger.action(this.mContext, 153, z);
        if (this.mIsSatelliteModeOn) {
            return;
        }
        boolean zIsWifiTileBlocked = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isWifiTileBlocked();
        WifiTile wifiTile = this.mWifiTile;
        if (!zIsWifiTileBlocked) {
            wifiTile.getClass();
            try {
                IDevicePolicyManager iDevicePolicyManager = wifiTile.mDevicePolicyManager;
                if (iDevicePolicyManager != null) {
                }
            } catch (RemoteException unused) {
            }
            if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
                if (keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && this.mSettingsHelper.isLockFunctionsEnabled() && ((QSTile.BooleanState) wifiTile.mState).value) {
                    this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.WifiDetailAdapter$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            WifiDetailAdapter wifiDetailAdapter = this.f$0;
                            boolean z3 = WifiDetailAdapter.DEBUG;
                            wifiDetailAdapter.setToggleState(!wifiDetailAdapter.getToggleState().booleanValue());
                        }
                    });
                    wifiTile.fireToggleStateChanged(getToggleState().booleanValue());
                    return;
                }
            }
            if (z2) {
                EmergencyButtonController$$ExternalSyntheticOutline0.m("setToggleState fireToggleStateChanged", "WifiDetailAdapter", z);
            }
            wifiTile.fireToggleStateChanged(z);
            MetricsLogger.action(this.mContext, 153, z);
            NetworkControllerImpl networkControllerImpl = (NetworkControllerImpl) this.mController;
            networkControllerImpl.getClass();
            networkControllerImpl.new AnonymousClass7(z).execute(new Void[0]);
            this.mAvailableItems.setEmptyState(z ? R.string.quick_settings_wifi_scanning_text : R.string.quick_settings_wifi_detail_off_text);
            return;
        }
        wifiTile.showItPolicyToast();
        wifiTile.fireToggleStateChanged(getToggleState().booleanValue());
    }

    public final void updateHotspotItems() {
        ArrayList arrayList;
        int i;
        boolean z = UserHandle.myUserId() == 0;
        if (!z || (!Utils.SPF_SupportMobileApEnhanced && !Utils.SPF_SupportMobileApEnhancedLite && !Utils.SPF_SupportMobileApEnhancedWifiOnlyLite)) {
            AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("isPrimaryUser: ", ".AutoHotspot", z);
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        WifiPickerTracker wifiPickerTracker = ((AccessPointControllerImpl) this.mAccessPointController).mWifiPickerTracker;
        synchronized (wifiPickerTracker.mLockAutoHotspot) {
            Log.d("WifiPickerTracker", "getAutoHotspotEntries() : mBleAccessPoints " + wifiPickerTracker.mAutoHotspotEntries);
            arrayList = new ArrayList(wifiPickerTracker.mAutoHotspotEntries);
        }
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            SemWifiApBleScanResult semWifiApBleScanResult = (SemWifiApBleScanResult) obj;
            QSDetailItems.Item item = new QSDetailItems.Item();
            item.tag = semWifiApBleScanResult;
            ((AccessPointControllerImpl) this.mAccessPointController).getClass();
            int i3 = semWifiApBleScanResult.mBLERssi;
            char c = i3 >= -60 ? (char) 4 : i3 >= -70 ? (char) 3 : i3 >= -80 ? (char) 2 : i3 >= -90 ? (char) 1 : (char) 0;
            int i4 = semWifiApBleScanResult.mNetworkType;
            int i5 = SemWifiApBleScanResult.MHS_WIFI_6_NETWORK;
            int[][] iArr = AccessPointControllerImpl.ICONS_WIFI6;
            if (i4 == i5) {
                i = semWifiApBleScanResult.mSecurity == 1 ? iArr[c][1] : iArr[c][0];
            } else {
                int i6 = SemWifiApBleScanResult.MHS_WIFI_6E_NETWORK;
                if (i4 == i6) {
                    int i7 = semWifiApBleScanResult.mSecurity;
                    int[][] iArr2 = AccessPointControllerImpl.ICONS_WIFI6E;
                    i = i7 == 1 ? iArr2[c][1] : iArr2[c][0];
                } else if (i4 == i6) {
                    i = semWifiApBleScanResult.mSecurity == 1 ? iArr[c][1] : iArr[c][0];
                } else {
                    int i8 = semWifiApBleScanResult.mSecurity;
                    int[][] iArr3 = AccessPointControllerImpl.ICONS_WIFI;
                    i = i8 == 1 ? iArr3[c][1] : iArr3[c][0];
                }
            }
            item.iconResId = i;
            item.line1 = semWifiApBleScanResult.mSSID;
            if (this.mWifiManager != null && semWifiApBleScanResult.mWifiMac != null) {
                ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("updateHotspotItems() - status getting from res.mWifiMac->"), semWifiApBleScanResult.mWifiMac, "WifiDetailAdapter.AutoHotspot");
                AccessPointController accessPointController = this.mAccessPointController;
                String str = semWifiApBleScanResult.mWifiMac;
                SemWifiManager semWifiManager = ((AccessPointControllerImpl) accessPointController).mSemWifiManager;
                int smartApConnectedStatusFromScanResult = semWifiManager != null ? semWifiManager.getSmartApConnectedStatusFromScanResult(str) : 0;
                ExifInterface$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(smartApConnectedStatusFromScanResult, "updateHotspotItems() - ConnectedStatus-> ", " res.mWifiMac-> "), semWifiApBleScanResult.mWifiMac, "WifiDetailAdapter.AutoHotspot");
                if (smartApConnectedStatusFromScanResult == 3) {
                    ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("updateHotspotItems() - This mac is connected (do nothing) res.mWifiMac-> "), semWifiApBleScanResult.mWifiMac, "WifiDetailAdapter.AutoHotspot");
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(semWifiApBleScanResult.mUserName);
                    if (semWifiApBleScanResult.mBattery <= 15) {
                        item.isDisabled = true;
                        item.isClickable = false;
                        sb.append(this.mContext.getString(R.string.comma));
                        sb.append(" ");
                        sb.append(this.mContext.getString(R.string.hotspot_live_ap_low_battery_summary));
                    } else if (semWifiApBleScanResult.isDataSaverEnabled) {
                        item.isDisabled = true;
                        item.isClickable = false;
                        sb.append(this.mContext.getString(R.string.comma));
                        sb.append(" ");
                        sb.append(this.mContext.getString(R.string.wifi_ap_mobile_hotspot_dialog_data_saver_is_on));
                    } else if (semWifiApBleScanResult.isMobileDataLimitReached) {
                        item.isDisabled = true;
                        item.isClickable = false;
                        sb.append(this.mContext.getString(R.string.comma));
                        sb.append(" ");
                        sb.append(this.mContext.getString(R.string.wifi_ap_data_limit_reached));
                    } else if (semWifiApBleScanResult.isNotValidNetwork) {
                        sb.append(this.mContext.getString(R.string.comma));
                        sb.append(" ");
                        sb.append(this.mContext.getString(R.string.smart_tethering_internet_not_available));
                    } else if (smartApConnectedStatusFromScanResult == 1 || smartApConnectedStatusFromScanResult == 2) {
                        sb.append(this.mContext.getString(R.string.comma));
                        sb.append(" ");
                        sb.append(this.mContext.getString(R.string.smart_tethering_ap_connecting_summary));
                    } else if (smartApConnectedStatusFromScanResult < 0) {
                        sb.append(this.mContext.getString(R.string.comma));
                        sb.append(" ");
                        sb.append(this.mContext.getString(R.string.smart_tethering_ap_connection_failed_summary));
                        item.isDisabled = true;
                    }
                    Log.d("WifiDetailAdapter.AutoHotspot", "item.isDisabled : " + item.isDisabled + " item.isClickable : " + item.isClickable);
                    item.line2 = sb.toString();
                    arrayList2.add(item);
                }
            }
        }
        QSDetailItems qSDetailItems = this.mHotspotLiveItems;
        if (qSDetailItems != null) {
            qSDetailItems.setItems((QSDetailItems.Item[]) arrayList2.toArray(new QSDetailItems.Item[arrayList2.size()]));
            this.mHotspotLiveItems.post(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.WifiDetailAdapter.4
                @Override // java.lang.Runnable
                public final void run() {
                    WifiDetailAdapter.this.mHotspotLive.setVisibility(WifiDetailAdapter.this.mHotspotLiveItems.mAdapter.getCount() > 0 ? 0 : 8);
                }
            });
        }
    }

    @Override // com.android.systemui.statusbar.connectivity.AccessPointController.AccessPointCallback
    public final void onWifiScan(boolean z) {
    }
}
