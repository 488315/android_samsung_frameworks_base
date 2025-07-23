package com.android.systemui.qs.tiles.detail;

import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.SemStatusBarManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.R;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
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
import com.android.systemui.statusbar.policy.KeyguardStateController;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:28:0x011c, code lost:
    
        if (r4.ssid != null) goto L31;
     */
    /* JADX WARN: Removed duplicated region for block: B:39:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01ba  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0159  */
    @Override // com.android.systemui.plugins.qs.DetailAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View createDetailView(android.content.Context r10, android.view.View r11, android.view.ViewGroup r12) {
        /*
            Method dump skipped, instructions count: 473
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.detail.WifiDetailAdapter.createDetailView(android.content.Context, android.view.View, android.view.ViewGroup):android.view.View");
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
                    boolean isOpenNetwork = AccessPointControllerImpl.isOpenNetwork(wifiEntry3);
                    int[][] iArr = AccessPointControllerImpl.ICONS_WIFI7;
                    i = !isOpenNetwork ? iArr[level][1] : iArr[level][0];
                } else {
                    WifiInfo wifiInfo2 = wifiEntry3.mWifiInfo;
                    if (wifiInfo2 != null ? wifiEntry3.checkWifi6EStandard(wifiInfo2.getFrequency(), wifiInfo2.getWifiStandard()) : semWifiEntryFlags.has6EStandard) {
                        boolean isOpenNetwork2 = AccessPointControllerImpl.isOpenNetwork(wifiEntry3);
                        int[][] iArr2 = AccessPointControllerImpl.ICONS_WIFI6E;
                        i = !isOpenNetwork2 ? iArr2[level][1] : iArr2[level][0];
                    } else {
                        WifiInfo wifiInfo3 = wifiEntry3.mWifiInfo;
                        if (wifiInfo3 == null ? semWifiEntryFlags.wifiStandard >= 6 : wifiInfo3.getWifiStandard() == 6) {
                            boolean isOpenNetwork3 = AccessPointControllerImpl.isOpenNetwork(wifiEntry3);
                            int[][] iArr3 = AccessPointControllerImpl.ICONS_WIFI6;
                            i = !isOpenNetwork3 ? iArr3[level][1] : iArr3[level][0];
                        } else {
                            WifiInfo wifiInfo4 = wifiEntry3.mWifiInfo;
                            if (wifiInfo4 == null ? semWifiEntryFlags.wifiStandard >= 5 : wifiInfo4.getWifiStandard() == 5) {
                                boolean isOpenNetwork4 = AccessPointControllerImpl.isOpenNetwork(wifiEntry3);
                                int[][] iArr4 = AccessPointControllerImpl.ICONS_WIFI5;
                                i = !isOpenNetwork4 ? iArr4[level][1] : iArr4[level][0];
                            } else {
                                boolean isOpenNetwork5 = AccessPointControllerImpl.isOpenNetwork(wifiEntry3);
                                int[][] iArr5 = AccessPointControllerImpl.ICONS_WIFI;
                                i = !isOpenNetwork5 ? iArr5[level][1] : iArr5[level][0];
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
        boolean z2 = false;
        int i = 0;
        if (!z) {
            if ((Utils.SPF_SupportMobileApEnhanced || Utils.SPF_SupportMobileApEnhancedLite || Utils.SPF_SupportMobileApEnhancedWifiOnlyLite) && (obj instanceof SemWifiApBleScanResult) && !item.isDisabled) {
                SemWifiApBleScanResult semWifiApBleScanResult = (SemWifiApBleScanResult) obj;
                AccessPointControllerImpl accessPointControllerImpl = (AccessPointControllerImpl) accessPointController;
                accessPointControllerImpl.getClass();
                if (semWifiApBleScanResult.mBattery > 15) {
                    z2 = accessPointControllerImpl.mWifiPickerTracker.mSemWifiManager.connectToSmartMHS(semWifiApBleScanResult.mDevice, semWifiApBleScanResult.mMHSdeviceType, semWifiApBleScanResult.mhidden, semWifiApBleScanResult.mSecurity, semWifiApBleScanResult.mWifiMac, semWifiApBleScanResult.mUserName, semWifiApBleScanResult.version, semWifiApBleScanResult.isWifiProfileShareEnabled);
                }
                Log.d("AccessPointController.AutoHotspot", "triggerWifiApBleConnection() : bleDevice -> " + semWifiApBleScanResult.mSSID + " mBattery: " + semWifiApBleScanResult.mBattery + " ret: " + z2);
                if (z2) {
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
            boolean isSaved = wifiEntry.isSaved();
            AccessPointControllerImpl.AnonymousClass2 anonymousClass2 = accessPointControllerImpl2.mConnectCallback;
            if (isSaved) {
                wifiEntry.connect(anonymousClass2);
            } else if (AccessPointControllerImpl.isOpenNetwork(wifiEntry)) {
                wifiEntry.connect(anonymousClass2);
            } else {
                accessPointControllerImpl2.startSettings(wifiEntry);
            }
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.WifiDetailAdapter.1
                @Override // java.lang.Runnable
                public final void run() {
                    ((SQSTileImpl) WifiDetailAdapter.this.mWifiTile).mHandler.obtainMessage(103, 0, 0).sendToTarget();
                }
            }, 250L);
            return;
        }
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

    /* JADX WARN: Code restructure failed: missing block: B:15:0x003c, code lost:
    
        if (r2.semGetAllowWifi((android.content.ComponentName) null, android.app.ActivityManager.getCurrentUser()) == false) goto L36;
     */
    @Override // com.android.systemui.plugins.qs.DetailAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setToggleState(boolean r8) {
        /*
            r7 = this;
            java.lang.String r0 = "WifiDetailAdapter"
            boolean r1 = com.android.systemui.qs.tiles.detail.WifiDetailAdapter.DEBUG
            if (r1 == 0) goto Lc
            java.lang.String r2 = "setToggleState "
            com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0.m(r2, r0, r8)
        Lc:
            android.content.Context r2 = r7.mContext
            r3 = 153(0x99, float:2.14E-43)
            com.android.internal.logging.MetricsLogger.action(r2, r3, r8)
            boolean r2 = r7.mIsSatelliteModeOn
            if (r2 == 0) goto L18
            return
        L18:
            com.android.systemui.Dependency r2 = com.android.systemui.Dependency.sDependency
            java.lang.Class<com.android.systemui.knox.KnoxStateMonitor> r4 = com.android.systemui.knox.KnoxStateMonitor.class
            java.lang.Object r2 = r2.getDependencyInner(r4)
            com.android.systemui.knox.KnoxStateMonitor r2 = (com.android.systemui.knox.KnoxStateMonitor) r2
            com.android.systemui.knox.KnoxStateMonitorImpl r2 = (com.android.systemui.knox.KnoxStateMonitorImpl) r2
            boolean r2 = r2.isWifiTileBlocked()
            com.android.systemui.qs.tiles.WifiTile r4 = r7.mWifiTile
            if (r2 != 0) goto Lb1
            r4.getClass()
            android.app.admin.IDevicePolicyManager r2 = r4.mDevicePolicyManager     // Catch: android.os.RemoteException -> L3f
            if (r2 == 0) goto L3f
            int r5 = android.app.ActivityManager.getCurrentUser()     // Catch: android.os.RemoteException -> L3f
            r6 = 0
            boolean r2 = r2.semGetAllowWifi(r6, r5)     // Catch: android.os.RemoteException -> L3f
            if (r2 != 0) goto L3f
            goto Lb1
        L3f:
            com.android.systemui.statusbar.policy.KeyguardStateController r2 = r7.mKeyguardStateController
            com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r2 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r2
            boolean r2 = r2.mShowing
            if (r2 == 0) goto L80
            com.android.keyguard.KeyguardUpdateMonitor r2 = r7.mKeyguardUpdateMonitor
            boolean r5 = r2.isSecure()
            if (r5 == 0) goto L80
            int r5 = com.android.keyguard.KeyguardUpdateMonitor.getCurrentUser()
            boolean r2 = r2.getUserCanSkipBouncer(r5)
            if (r2 != 0) goto L80
            com.android.systemui.util.SettingsHelper r2 = r7.mSettingsHelper
            boolean r2 = r2.isLockFunctionsEnabled()
            if (r2 == 0) goto L80
            com.android.systemui.plugins.qs.QSTile$State r2 = r4.mState
            com.android.systemui.plugins.qs.QSTile$BooleanState r2 = (com.android.systemui.plugins.qs.QSTile.BooleanState) r2
            boolean r2 = r2.value
            r5 = 1
            if (r2 != r5) goto L80
            com.android.systemui.qs.tiles.detail.WifiDetailAdapter$$ExternalSyntheticLambda0 r8 = new com.android.systemui.qs.tiles.detail.WifiDetailAdapter$$ExternalSyntheticLambda0
            r8.<init>()
            com.android.systemui.plugins.ActivityStarter r0 = r7.mActivityStarter
            r0.postQSRunnableDismissingKeyguard(r8)
            java.lang.Boolean r7 = r7.getToggleState()
            boolean r7 = r7.booleanValue()
            r4.fireToggleStateChanged(r7)
            return
        L80:
            if (r1 == 0) goto L88
            java.lang.String r1 = "setToggleState fireToggleStateChanged"
            com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0.m(r1, r0, r8)
        L88:
            r4.fireToggleStateChanged(r8)
            android.content.Context r0 = r7.mContext
            com.android.internal.logging.MetricsLogger.action(r0, r3, r8)
            com.android.systemui.statusbar.connectivity.NetworkController r0 = r7.mController
            com.android.systemui.statusbar.connectivity.NetworkControllerImpl r0 = (com.android.systemui.statusbar.connectivity.NetworkControllerImpl) r0
            r0.getClass()
            com.android.systemui.statusbar.connectivity.NetworkControllerImpl$7 r1 = new com.android.systemui.statusbar.connectivity.NetworkControllerImpl$7
            r1.<init>(r8)
            r0 = 0
            java.lang.Void[] r0 = new java.lang.Void[r0]
            r1.execute(r0)
            com.android.systemui.qs.QSDetailItems r7 = r7.mAvailableItems
            if (r8 == 0) goto Laa
            r8 = 2131955932(0x7f1310dc, float:1.9548405E38)
            goto Lad
        Laa:
            r8 = 2131955929(0x7f1310d9, float:1.95484E38)
        Lad:
            r7.setEmptyState(r8)
            return
        Lb1:
            r4.showItPolicyToast()
            java.lang.Boolean r7 = r7.getToggleState()
            boolean r7 = r7.booleanValue()
            r4.fireToggleStateChanged(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.detail.WifiDetailAdapter.setToggleState(boolean):void");
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
