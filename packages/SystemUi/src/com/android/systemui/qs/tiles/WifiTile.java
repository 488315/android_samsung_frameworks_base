package com.android.systemui.qs.tiles;

import android.app.ActivityManager;
import android.app.admin.IDevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.CvOperator;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.qs.QSDetailItems;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.connectivity.AccessPointController;
import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl;
import com.android.systemui.statusbar.connectivity.IconState;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl.AsyncTaskC26277;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.connectivity.WifiIndicators;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.wifitrackerlib.StandardWifiEntry;
import com.android.wifitrackerlib.Utils;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiPickerTracker;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.wifi.SemWifiApBleScanResult;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WifiTile extends SQSTileImpl {
    public static final Intent WIFI_SETTINGS = new Intent("android.settings.WIFI_SETTINGS");
    public final AccessPointController mAccessPointController;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final NetworkController mController;
    public final WifiDetailAdapter mDetailAdapter;
    public boolean mDetailListening;
    public final IDevicePolicyManager mDevicePolicyManager;
    public final QSTileImpl.AnimationIcon mDisable;
    public final DisplayLifecycle mDisplayLifecycle;
    public final QSTileImpl.AnimationIcon mEnable;
    public boolean mExpectDisabled;
    public final C22991 mFoldStateChangedListener;
    public final Handler mHandler;
    public boolean mIsHavingConvertView;
    public boolean mIsTransientEnabled;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final PanelInteractor mPanelInteractor;
    public final WifiTileReceiver mReceiver;
    public final SettingsHelper mSettingsHelper;
    public final WifiSignalCallback mSignalCallback;
    public final QSTile.SignalState mStateBeforeClick;
    public final SubscreenQsPanelController mSubscreenQsPanelController;
    public WifiTileReceiver mSubscreenWifiTileReceiver;
    public boolean mWifiConnected;
    public final WifiManager mWifiManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CallbackInfo {
        public boolean activityIn;
        public boolean activityOut;
        public boolean connected;
        public boolean enabled;
        public int inetCondition;
        public boolean isTransient;
        public String ssid;
        public String statusLabel;
        public String wifiSignalContentDescription;
        public int wifiSignalIconId;

        public final String toString() {
            StringBuilder sb = new StringBuilder("CallbackInfo[enabled=");
            sb.append(this.enabled);
            sb.append(",connected=");
            sb.append(this.connected);
            sb.append(",wifiSignalIconId=");
            sb.append(this.wifiSignalIconId);
            sb.append(",ssid=");
            sb.append(this.ssid);
            sb.append(",activityIn=");
            sb.append(this.activityIn);
            sb.append(",activityOut=");
            sb.append(this.activityOut);
            sb.append(",wifiSignalContentDescription=");
            sb.append(this.wifiSignalContentDescription);
            sb.append(",isTransient=");
            sb.append(this.isTransient);
            sb.append(",statusLabel=");
            sb.append(this.statusLabel);
            sb.append(",inetCondition=");
            return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.inetCondition, ",wifiTestReported=false]");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WifiDetailAdapter implements DetailAdapter, AccessPointController.AccessPointCallback, AccessPointController.WifiApBleStateChangeCallback, QSDetailItems.Callback {
        public static final /* synthetic */ int $r8$clinit = 0;
        public WifiEntry[] mAccessPoints;
        public ViewGroup mAvailable;
        public QSDetailItems mAvailableItems;
        public View mConnectedNetworksTitle;
        public ViewGroup mConntected;
        public ViewGroup mHotspotLive;
        public QSDetailItems mHotspotLiveItems;
        public QSDetailItems mItems;

        public WifiDetailAdapter() {
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
            Intent intent = WifiTile.WIFI_SETTINGS;
            WifiTile wifiTile = WifiTile.this;
            String str = wifiTile.TAG;
            StringBuilder sb = new StringBuilder("createDetailView convertView=");
            boolean z = false;
            sb.append(view != null);
            sb.append(" State : ");
            sb.append(((QSTile.SignalState) wifiTile.mState).value);
            sb.append(" enabled : ");
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, wifiTile.mSignalCallback.mInfo.enabled, str);
            this.mAccessPoints = null;
            if (!wifiTile.mIsHavingConvertView) {
                view = null;
            }
            if (view == null) {
                view = LayoutInflater.from(wifiTile.mContext).inflate(R.layout.qs_detail_wifi, viewGroup, false);
                ViewGroup viewGroup2 = (ViewGroup) view.findViewById(R.id.current_network);
                this.mConntected = viewGroup2;
                this.mConnectedNetworksTitle = viewGroup2.findViewById(R.id.connected_networks_title);
                QSDetailItems convertOrInflate = QSDetailItems.convertOrInflate(context, this.mConntected);
                this.mItems = convertOrInflate;
                this.mConntected.addView(convertOrInflate);
                this.mItems.setTagSuffix("Wifi");
                ViewGroup viewGroup3 = (ViewGroup) view.findViewById(R.id.hotspot_live_networks);
                this.mHotspotLive = viewGroup3;
                ((TextView) viewGroup3.findViewById(R.id.hotspot_live_networks_title)).setText(CvOperator.getHotspotStringID(R.string.sec_wifi_hotspot_live_preference_category_title));
                QSDetailItems convertOrInflate2 = QSDetailItems.convertOrInflate(context, this.mHotspotLive);
                this.mHotspotLiveItems = convertOrInflate2;
                convertOrInflate2.setTagSuffix("Hotspot.Available");
                this.mHotspotLive.addView(this.mHotspotLiveItems);
                ViewGroup viewGroup4 = (ViewGroup) view.findViewById(R.id.available_networks);
                this.mAvailable = viewGroup4;
                QSDetailItems convertOrInflate3 = QSDetailItems.convertOrInflate(context, this.mAvailable);
                this.mAvailableItems = convertOrInflate3;
                convertOrInflate3.setTagSuffix("Wifi.Available");
                this.mAvailable.addView(this.mAvailableItems);
                wifiTile.mIsHavingConvertView = true;
            }
            QSDetailItems qSDetailItems = this.mAvailableItems;
            boolean z2 = ((QSTile.SignalState) wifiTile.mState).value;
            if (z2 && (this.mItems.getItemCount() > 0 || wifiTile.mWifiConnected)) {
                z = true;
            }
            StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("isConnectedVisible = ", z, ",getItemCount() = ");
            m49m.append(this.mItems.getItemCount());
            m49m.append(",mWifiConnected = ");
            m49m.append(wifiTile.mWifiConnected);
            String sb2 = m49m.toString();
            String str2 = wifiTile.TAG;
            Log.d(str2, sb2);
            qSDetailItems.setEmptyState(z2 ? z ? R.string.quick_settings_wifi_detail_scanning_text : R.string.quick_settings_wifi_detail_turningon_text : R.string.quick_settings_wifi_detail_off_text);
            this.mItems.setCallback(this);
            SemWifiManager semWifiManager = ((AccessPointControllerImpl) wifiTile.mAccessPointController).mSemWifiManager;
            if (semWifiManager != null) {
                semWifiManager.setWifiSettingsForegroundState(1);
            }
            Log.d(str2, "adding wififoreground");
            this.mHotspotLiveItems.setCallback(this);
            this.mAvailableItems.setCallback(this);
            wifiTile.fireScanStateChanged(((QSTile.SignalState) wifiTile.mState).value);
            this.mConntected.setVisibility(8);
            this.mConnectedNetworksTitle.setVisibility(8);
            setItemsVisible(((QSTile.SignalState) wifiTile.mState).value);
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
            Intent intent = WifiTile.WIFI_SETTINGS;
            return WifiTile.this.mContext.getString(R.string.quick_settings_sec_wifi_label).trim();
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final boolean getToggleEnabled() {
            Intent intent = WifiTile.WIFI_SETTINGS;
            return ((QSTile.SignalState) WifiTile.this.mState).state != 0;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final Boolean getToggleState() {
            Intent intent = WifiTile.WIFI_SETTINGS;
            return Boolean.valueOf(((QSTile.SignalState) WifiTile.this.mState).value);
        }

        /* JADX WARN: Removed duplicated region for block: B:40:0x007c  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x00a4  */
        @Override // com.android.systemui.statusbar.connectivity.AccessPointController.AccessPointCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onAccessPointsChanged(List list) {
            int i;
            boolean z;
            WifiInfo wifiInfo;
            WifiEntry[] wifiEntryArr = (WifiEntry[]) list.toArray(new WifiEntry[list.size()]);
            this.mAccessPoints = wifiEntryArr;
            int length = wifiEntryArr.length;
            int i2 = 0;
            int i3 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                WifiEntry wifiEntry = wifiEntryArr[i2];
                Intent intent = WifiTile.WIFI_SETTINGS;
                if ((wifiEntry == null || wifiEntry.mLevel == -1) ? false : true) {
                    i3++;
                }
                i2++;
            }
            WifiEntry[] wifiEntryArr2 = this.mAccessPoints;
            if (i3 != wifiEntryArr2.length) {
                this.mAccessPoints = new WifiEntry[i3];
                int length2 = wifiEntryArr2.length;
                int i4 = 0;
                for (int i5 = 0; i5 < length2; i5++) {
                    WifiEntry wifiEntry2 = wifiEntryArr2[i5];
                    Intent intent2 = WifiTile.WIFI_SETTINGS;
                    if ((wifiEntry2 == null || wifiEntry2.mLevel == -1) ? false : true) {
                        this.mAccessPoints[i4] = wifiEntry2;
                        i4++;
                    }
                }
            }
            if (this.mItems == null) {
                return;
            }
            WifiEntry[] wifiEntryArr3 = this.mAccessPoints;
            if (wifiEntryArr3 == null || wifiEntryArr3.length <= 0) {
                WifiTile wifiTile = WifiTile.this;
                if (wifiTile.mSignalCallback.mInfo.enabled) {
                    wifiTile.fireScanStateChanged(true);
                    if (WifiTile.this.mSignalCallback.mInfo.enabled) {
                        this.mItems.setItems(null);
                        this.mAvailableItems.setItems(null);
                        this.mConntected.setVisibility(8);
                        this.mAvailable.findViewById(R.id.available_networks_group).setVisibility(8);
                        this.mHotspotLiveItems.setItems(null);
                        this.mHotspotLive.setVisibility(8);
                        return;
                    }
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    if (this.mAccessPoints != null) {
                        int i6 = 0;
                        while (true) {
                            WifiEntry[] wifiEntryArr4 = this.mAccessPoints;
                            if (i6 >= wifiEntryArr4.length) {
                                break;
                            }
                            WifiEntry wifiEntry3 = wifiEntryArr4[i6];
                            QSDetailItems.Item item = new QSDetailItems.Item();
                            item.tag = wifiEntry3;
                            ((AccessPointControllerImpl) WifiTile.this.mAccessPointController).getClass();
                            int i7 = wifiEntry3.mLevel;
                            if (i7 < 0) {
                                i7 = 0;
                            }
                            if (i7 > 4) {
                                i7 = 4;
                            }
                            wifiEntry3.mSemFlags.getClass();
                            WifiInfo wifiInfo2 = wifiEntry3.mWifiInfo;
                            if (wifiInfo2 != null ? wifiEntry3.checkWifi6EStandard(wifiInfo2.getFrequency(), wifiInfo2.getWifiStandard()) : wifiEntry3.mSemFlags.has6EStandard) {
                                i = !AccessPointControllerImpl.isOpenNetwork(wifiEntry3) ? AccessPointControllerImpl.ICONS_WIFI6E[i7][1] : AccessPointControllerImpl.ICONS_WIFI6E[i7][0];
                            } else {
                                WifiInfo wifiInfo3 = wifiEntry3.mWifiInfo;
                                if (wifiInfo3 == null ? wifiEntry3.mSemFlags.wifiStandard >= 6 : wifiInfo3.getWifiStandard() == 6) {
                                    i = !AccessPointControllerImpl.isOpenNetwork(wifiEntry3) ? AccessPointControllerImpl.ICONS_WIFI6[i7][1] : AccessPointControllerImpl.ICONS_WIFI6[i7][0];
                                } else {
                                    if (wifiEntry3 instanceof StandardWifiEntry) {
                                        StandardWifiEntry standardWifiEntry = (StandardWifiEntry) wifiEntry3;
                                        synchronized (standardWifiEntry) {
                                            if (standardWifiEntry.mSemFlags.hasVHTVSICapabilities && (wifiInfo = standardWifiEntry.mWifiInfo) != null) {
                                                if (TextUtils.isEmpty(wifiInfo.getBSSID())) {
                                                    Log.e("StandardWifiEntryStandardWifiEntry:", "connected network's WifiInfo.getBSSID is null");
                                                } else {
                                                    standardWifiEntry.semUpdateFlags(Utils.getBestScanResultByLevel(standardWifiEntry.mTargetScanResults));
                                                }
                                            }
                                            z = standardWifiEntry.mSemFlags.hasVHTVSICapabilities;
                                        }
                                        if (z) {
                                            i = !AccessPointControllerImpl.isOpenNetwork(wifiEntry3) ? AccessPointControllerImpl.ICONS_GIGA[i7][1] : AccessPointControllerImpl.ICONS_GIGA[i7][0];
                                        }
                                    }
                                    i = !AccessPointControllerImpl.isOpenNetwork(wifiEntry3) ? AccessPointControllerImpl.ICONS_WIFI[i7][1] : AccessPointControllerImpl.ICONS_WIFI[i7][0];
                                }
                            }
                            item.iconResId = i;
                            item.line1 = wifiEntry3.getTitle();
                            item.line2 = wifiEntry3.getSummary(true);
                            if (wifiEntry3.getConnectedState() == 2) {
                                item.isActive = true;
                                arrayList.add(item);
                            } else {
                                arrayList2.add(item);
                            }
                            i6++;
                        }
                    }
                    updateHotspotItems();
                    this.mItems.setItems((QSDetailItems.Item[]) arrayList.toArray(new QSDetailItems.Item[arrayList.size()]));
                    this.mAvailableItems.setItems((QSDetailItems.Item[]) arrayList2.toArray(new QSDetailItems.Item[arrayList2.size()]));
                    int i8 = arrayList.size() == 0 ? 8 : 0;
                    this.mConntected.setVisibility(i8);
                    this.mConnectedNetworksTitle.setVisibility(i8);
                    this.mAvailableItems.post(new Runnable() { // from class: com.android.systemui.qs.tiles.WifiTile.WifiDetailAdapter.3
                        @Override // java.lang.Runnable
                        public final void run() {
                            WifiDetailAdapter.this.mAvailable.findViewById(R.id.available_networks_group).setVisibility(WifiDetailAdapter.this.mAvailableItems.getItemCount() > 0 ? 0 : 8);
                        }
                    });
                    return;
                }
            }
            WifiTile.this.fireScanStateChanged(false);
            if (WifiTile.this.mSignalCallback.mInfo.enabled) {
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:29:0x00bc  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x00c3  */
        @Override // com.android.systemui.qs.QSDetailItems.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onDetailItemClick(QSDetailItems.Item item) {
            Object obj;
            if (item == null || (obj = item.tag) == null) {
                return;
            }
            boolean z = obj instanceof WifiEntry;
            WifiTile wifiTile = WifiTile.this;
            if (!z) {
                if (!(obj instanceof SemWifiApBleScanResult) || item.isDisabled) {
                    return;
                }
                SemWifiApBleScanResult semWifiApBleScanResult = (SemWifiApBleScanResult) obj;
                AccessPointControllerImpl accessPointControllerImpl = (AccessPointControllerImpl) wifiTile.mAccessPointController;
                accessPointControllerImpl.getClass();
                r2 = semWifiApBleScanResult.mBattery > 15 ? accessPointControllerImpl.mWifiPickerTracker.mSemWifiManager.connectToSmartMHS(semWifiApBleScanResult.mDevice, semWifiApBleScanResult.mMHSdeviceType, semWifiApBleScanResult.mhidden, semWifiApBleScanResult.mSecurity, semWifiApBleScanResult.mWifiMac, semWifiApBleScanResult.mUserName, semWifiApBleScanResult.version, semWifiApBleScanResult.isWifiProfileShareEnabled) : false;
                Log.d("AccessPointController.AutoHotspot", "triggerWifiApBleConnection() : bleDevice -> " + semWifiApBleScanResult.mSSID + " mBattery: " + semWifiApBleScanResult.mBattery + " ret: " + r2);
                String str = wifiTile.TAG;
                if (r2) {
                    Log.d(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, ".AutoHotspot"), "onDetailItemClick() - Triggering updateHotspotItems for connecting with apBLE.mWifiMac-> " + semWifiApBleScanResult.mWifiMac);
                    updateHotspotItems();
                    return;
                }
                Log.d(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, ".AutoHotspot"), "onDetailItemClick() - Triggering updateHotspotItems for connection time out with apBLE.mWifiMac-> " + semWifiApBleScanResult.mWifiMac);
                updateHotspotItems();
                return;
            }
            WifiEntry wifiEntry = (WifiEntry) obj;
            if (wifiEntry.getConnectedState() != 0) {
                if (wifiEntry.getConnectedState() == 2) {
                    ((AccessPointControllerImpl) wifiTile.mAccessPointController).startSettings(wifiEntry);
                    return;
                }
                return;
            }
            AccessPointControllerImpl accessPointControllerImpl2 = (AccessPointControllerImpl) wifiTile.mAccessPointController;
            accessPointControllerImpl2.getClass();
            if (AccessPointControllerImpl.DEBUG) {
                if (wifiEntry.getWifiConfiguration() != null) {
                    RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("connect networkId="), wifiEntry.getWifiConfiguration().networkId, "AccessPointController");
                } else {
                    Log.d("AccessPointController", "connect to unsaved network " + wifiEntry.getTitle());
                }
            }
            if (wifiEntry.mSemFlags.isOpenRoamingNetwork) {
                String string = Settings.Global.getString(accessPointControllerImpl2.mWifiPickerTrackerFactory.mContext.getContentResolver(), "sem_wifi_allowed_oauth_provider");
                if (!(string != null && string.contains("[cisco]"))) {
                    Intent intent = new Intent("android.settings.WIFI_SETTINGS");
                    intent.putExtra("wifi_start_connect_ssid", "wifi_start_openroaming");
                    intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                    Iterator it = accessPointControllerImpl2.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((AccessPointController.AccessPointCallback) it.next()).onSettingsActivityTriggered(intent);
                    }
                    r2 = true;
                    if (r2) {
                        wifiTile.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.WifiTile.WifiDetailAdapter.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                ((SQSTileImpl) WifiTile.this).mHandler.obtainMessage(103, 0, 0).sendToTarget();
                            }
                        }, 250L);
                        return;
                    } else {
                        wifiTile.mPanelInteractor.forceCollapsePanels();
                        return;
                    }
                }
            }
            boolean isSaved = wifiEntry.isSaved();
            AccessPointControllerImpl.C26172 c26172 = accessPointControllerImpl2.mConnectCallback;
            if (isSaved) {
                wifiEntry.connect(c26172);
            } else if (AccessPointControllerImpl.isOpenNetwork(wifiEntry)) {
                wifiEntry.connect(c26172);
            } else {
                accessPointControllerImpl2.startSettings(wifiEntry);
                r2 = true;
            }
            if (r2) {
            }
        }

        @Override // com.android.systemui.statusbar.connectivity.AccessPointController.AccessPointCallback
        public final void onSettingsActivityTriggered(Intent intent) {
            Intent intent2 = WifiTile.WIFI_SETTINGS;
            WifiTile.this.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0);
        }

        public final void setItemsVisible(boolean z) {
            Intent intent = WifiTile.WIFI_SETTINGS;
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m(" setItemsVisible : ", z, WifiTile.this.TAG);
            QSDetailItems qSDetailItems = this.mItems;
            if (qSDetailItems == null || z) {
                return;
            }
            qSDetailItems.setItems(null);
            this.mAvailableItems.setItems(null);
            this.mItems.post(new Runnable() { // from class: com.android.systemui.qs.tiles.WifiTile.WifiDetailAdapter.2
                @Override // java.lang.Runnable
                public final void run() {
                    WifiDetailAdapter.this.mConntected.setVisibility(8);
                    WifiDetailAdapter.this.mAvailable.findViewById(R.id.available_networks_group).setVisibility(8);
                }
            });
            this.mHotspotLiveItems.setItems(null);
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x003f  */
        @Override // com.android.systemui.plugins.qs.DetailAdapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void setToggleState(boolean z) {
            boolean z2;
            IDevicePolicyManager iDevicePolicyManager;
            Intent intent = WifiTile.WIFI_SETTINGS;
            boolean z3 = QSTileImpl.DEBUG;
            WifiTile wifiTile = WifiTile.this;
            if (z3) {
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("setToggleState ", z, wifiTile.TAG);
            }
            MetricsLogger.action(wifiTile.mContext, 153, z);
            if (!((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isWifiTileBlocked()) {
                Object obj = null;
                int i = 1;
                try {
                    iDevicePolicyManager = wifiTile.mDevicePolicyManager;
                } catch (RemoteException unused) {
                }
                if (iDevicePolicyManager != null) {
                    if (!iDevicePolicyManager.semGetAllowWifi((ComponentName) null, ActivityManager.getCurrentUser())) {
                        z2 = true;
                        if (!z2) {
                            if (((KeyguardStateControllerImpl) wifiTile.mKeyguardStateController).mShowing) {
                                KeyguardUpdateMonitor keyguardUpdateMonitor = wifiTile.mKeyguardUpdateMonitor;
                                if (keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && wifiTile.mSettingsHelper.isLockFunctionsEnabled() && ((QSTile.SignalState) wifiTile.mState).value) {
                                    wifiTile.mActivityStarter.postQSRunnableDismissingKeyguard(new WifiTile$$ExternalSyntheticLambda1(this, i));
                                    wifiTile.fireToggleStateChanged(getToggleState().booleanValue());
                                    return;
                                }
                            }
                            ((QSTile.SignalState) wifiTile.mState).copyTo(wifiTile.mStateBeforeClick);
                            if (z) {
                                Intent intent2 = WifiTile.WIFI_SETTINGS;
                                obj = SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING;
                            }
                            wifiTile.refreshState(obj);
                            if (QSTileImpl.DEBUG) {
                                Log.d(wifiTile.TAG, AbstractC0866xb1ce8deb.m86m("setToggleState fireToggleStateChanged", z));
                            }
                            wifiTile.fireToggleStateChanged(z);
                            MetricsLogger.action(wifiTile.mContext, 153, z);
                            NetworkControllerImpl networkControllerImpl = (NetworkControllerImpl) wifiTile.mController;
                            networkControllerImpl.getClass();
                            networkControllerImpl.new AsyncTaskC26277(z).execute(new Void[0]);
                            this.mAvailableItems.setEmptyState(z ? R.string.quick_settings_wifi_detail_turningon_text : R.string.quick_settings_wifi_detail_off_text);
                            return;
                        }
                    }
                }
                z2 = false;
                if (!z2) {
                }
            }
            wifiTile.showItPolicyToast();
            wifiTile.fireToggleStateChanged(getToggleState().booleanValue());
        }

        public final void updateHotspotItems() {
            ArrayList arrayList;
            int i;
            boolean z = UserHandle.myUserId() == 0;
            if (!z) {
                ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("isPrimaryUser: ", z, ".AutoHotspot");
                return;
            }
            ArrayList arrayList2 = new ArrayList();
            WifiPickerTracker wifiPickerTracker = ((AccessPointControllerImpl) WifiTile.this.mAccessPointController).mWifiPickerTracker;
            synchronized (wifiPickerTracker.mLockAutoHotspot) {
                Log.d("WifiPickerTracker", "getAutoHotspotEntries() : mBleAccessPoints " + wifiPickerTracker.mAutoHotspotEntries);
                arrayList = new ArrayList(wifiPickerTracker.mAutoHotspotEntries);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                SemWifiApBleScanResult semWifiApBleScanResult = (SemWifiApBleScanResult) it.next();
                QSDetailItems.Item item = new QSDetailItems.Item();
                item.tag = semWifiApBleScanResult;
                ((AccessPointControllerImpl) WifiTile.this.mAccessPointController).getClass();
                int i2 = semWifiApBleScanResult.mBLERssi;
                char c = i2 >= -60 ? (char) 4 : i2 >= -70 ? (char) 3 : i2 >= -80 ? (char) 2 : i2 >= -90 ? (char) 1 : (char) 0;
                int i3 = semWifiApBleScanResult.mNetworkType;
                int i4 = SemWifiApBleScanResult.MHS_WIFI_6_NETWORK;
                int[][] iArr = AccessPointControllerImpl.ICONS_WIFI6;
                if (i3 == i4) {
                    i = semWifiApBleScanResult.mSecurity == 1 ? iArr[c][1] : iArr[c][0];
                } else {
                    int i5 = SemWifiApBleScanResult.MHS_WIFI_6E_NETWORK;
                    if (i3 == i5) {
                        int i6 = semWifiApBleScanResult.mSecurity;
                        int[][] iArr2 = AccessPointControllerImpl.ICONS_WIFI6E;
                        i = i6 == 1 ? iArr2[c][1] : iArr2[c][0];
                    } else if (i3 == i5) {
                        i = semWifiApBleScanResult.mSecurity == 1 ? iArr[c][1] : iArr[c][0];
                    } else {
                        int i7 = semWifiApBleScanResult.mSecurity;
                        int[][] iArr3 = AccessPointControllerImpl.ICONS_WIFI;
                        i = i7 == 1 ? iArr3[c][1] : iArr3[c][0];
                    }
                }
                item.iconResId = i;
                item.line1 = semWifiApBleScanResult.mSSID;
                if (WifiTile.this.mWifiManager != null && semWifiApBleScanResult.mWifiMac != null) {
                    ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("updateHotspotItems() - status getting from res.mWifiMac->"), semWifiApBleScanResult.mWifiMac, AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder(), WifiTile.this.TAG, ".AutoHotspot"));
                    AccessPointController accessPointController = WifiTile.this.mAccessPointController;
                    String str = semWifiApBleScanResult.mWifiMac;
                    SemWifiManager semWifiManager = ((AccessPointControllerImpl) accessPointController).mSemWifiManager;
                    int smartApConnectedStatusFromScanResult = semWifiManager != null ? semWifiManager.getSmartApConnectedStatusFromScanResult(str) : 0;
                    ExifInterface$$ExternalSyntheticOutline0.m35m(AbstractC0000x2c234b15.m1m("updateHotspotItems() - ConnectedStatus-> ", smartApConnectedStatusFromScanResult, " res.mWifiMac-> "), semWifiApBleScanResult.mWifiMac, AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder(), WifiTile.this.TAG, ".AutoHotspot"));
                    if (smartApConnectedStatusFromScanResult == 3) {
                        ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("updateHotspotItems() - This mac is connected (do nothing) res.mWifiMac-> "), semWifiApBleScanResult.mWifiMac, AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder(), WifiTile.this.TAG, ".AutoHotspot"));
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append(semWifiApBleScanResult.mUserName);
                        if (semWifiApBleScanResult.mBattery <= 15) {
                            item.isDisabled = true;
                            item.isClickable = false;
                            sb.append(WifiTile.this.mContext.getString(R.string.comma));
                            sb.append(" ");
                            sb.append(WifiTile.this.mContext.getString(R.string.hotspot_live_ap_low_battery_summary));
                        } else if (semWifiApBleScanResult.isDataSaverEnabled) {
                            item.isDisabled = true;
                            item.isClickable = false;
                            sb.append(WifiTile.this.mContext.getString(R.string.comma));
                            sb.append(" ");
                            sb.append(WifiTile.this.mContext.getString(R.string.wifi_ap_mobile_hotspot_dialog_data_saver_is_on));
                        } else if (semWifiApBleScanResult.isMobileDataLimitReached) {
                            item.isDisabled = true;
                            item.isClickable = false;
                            sb.append(WifiTile.this.mContext.getString(R.string.comma));
                            sb.append(" ");
                            sb.append(WifiTile.this.mContext.getString(R.string.wifi_ap_data_limit_reached));
                        } else if (semWifiApBleScanResult.isNotValidNetwork) {
                            sb.append(WifiTile.this.mContext.getString(R.string.comma));
                            sb.append(" ");
                            sb.append(WifiTile.this.mContext.getString(R.string.smart_tethering_internet_not_available));
                        } else if (smartApConnectedStatusFromScanResult == 1 || smartApConnectedStatusFromScanResult == 2) {
                            sb.append(WifiTile.this.mContext.getString(R.string.comma));
                            sb.append(" ");
                            sb.append(WifiTile.this.mContext.getString(R.string.smart_tethering_ap_connecting_summary));
                        } else if (smartApConnectedStatusFromScanResult < 0) {
                            sb.append(WifiTile.this.mContext.getString(R.string.comma));
                            sb.append(" ");
                            sb.append(WifiTile.this.mContext.getString(R.string.smart_tethering_ap_connection_failed_summary));
                            item.isDisabled = true;
                        }
                        Log.d(AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder(), WifiTile.this.TAG, ".AutoHotspot"), "item.isDisabled : " + item.isDisabled + " item.isClickable : " + item.isClickable);
                        item.line2 = sb.toString();
                        arrayList2.add(item);
                    }
                }
            }
            QSDetailItems qSDetailItems = this.mHotspotLiveItems;
            if (qSDetailItems != null) {
                qSDetailItems.setItems((QSDetailItems.Item[]) arrayList2.toArray(new QSDetailItems.Item[arrayList2.size()]));
                this.mHotspotLiveItems.post(new Runnable() { // from class: com.android.systemui.qs.tiles.WifiTile.WifiDetailAdapter.4
                    @Override // java.lang.Runnable
                    public final void run() {
                        WifiDetailAdapter.this.mHotspotLive.setVisibility(WifiDetailAdapter.this.mHotspotLiveItems.getItemCount() > 0 ? 0 : 8);
                    }
                });
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WifiSignalCallback implements SignalCallback {
        public final CallbackInfo mInfo = new CallbackInfo();

        public WifiSignalCallback() {
        }

        @Override // com.android.systemui.statusbar.connectivity.SignalCallback
        public final void setWifiIndicators(WifiIndicators wifiIndicators) {
            Intent intent = WifiTile.WIFI_SETTINGS;
            boolean z = QSTileImpl.DEBUG;
            WifiTile wifiTile = WifiTile.this;
            if (z) {
                ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("onWifiSignalChanged enabled="), wifiIndicators.enabled, wifiTile.TAG);
            }
            if (z) {
                ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("isTransient ="), wifiIndicators.isTransient, wifiTile.TAG);
            }
            IconState iconState = wifiIndicators.qsIcon;
            if (iconState == null) {
                return;
            }
            boolean z2 = wifiIndicators.enabled;
            CallbackInfo callbackInfo = this.mInfo;
            callbackInfo.enabled = z2;
            callbackInfo.connected = iconState.visible;
            callbackInfo.wifiSignalIconId = iconState.icon;
            callbackInfo.ssid = wifiIndicators.description;
            callbackInfo.activityIn = wifiIndicators.activityIn;
            callbackInfo.activityOut = wifiIndicators.activityOut;
            callbackInfo.wifiSignalContentDescription = iconState.contentDescription;
            callbackInfo.isTransient = wifiIndicators.isTransient;
            callbackInfo.statusLabel = wifiIndicators.statusLabel;
            callbackInfo.inetCondition = wifiIndicators.inetCondition;
            wifiTile.refreshState(null);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WifiTileReceiver extends BroadcastReceiver {
        public WifiTileReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.samsung.android.server.wifi.softap.smarttethering.collapseQuickPanel")) {
                Intent intent2 = WifiTile.WIFI_SETTINGS;
                if (QSTileImpl.DEBUG) {
                    Log.d(WifiTile.this.TAG + ".AutoHotspot", "BT Paring dialog shown. Collapsing QuickPanel");
                }
                WifiTile wifiTile = WifiTile.this;
                if (!((KeyguardStateControllerImpl) wifiTile.mKeyguardStateController).mShowing) {
                    wifiTile.mPanelInteractor.forceCollapsePanels();
                }
            }
            if (intent.getAction().equals("WIFI_STATE_CHANGE")) {
                WifiTile.this.handleClick(null);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0, types: [com.android.systemui.qs.tiles.WifiTile$1, java.lang.Object] */
    public WifiTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, NetworkController networkController, AccessPointController accessPointController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, SettingsHelper settingsHelper, PanelInteractor panelInteractor, DisplayLifecycle displayLifecycle, BroadcastDispatcher broadcastDispatcher) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mEnable = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_wifi_on, R.drawable.quick_panel_icon_wifi_on_013);
        this.mDisable = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_wifi_off, R.drawable.quick_panel_icon_wifi_on_009);
        WifiSignalCallback wifiSignalCallback = new WifiSignalCallback();
        this.mSignalCallback = wifiSignalCallback;
        QSTile.SignalState signalState = new QSTile.SignalState();
        this.mStateBeforeClick = signalState;
        this.mSubscreenQsPanelController = null;
        this.mIsTransientEnabled = false;
        this.mWifiConnected = false;
        ?? r8 = new DisplayLifecycle.Observer() { // from class: com.android.systemui.qs.tiles.WifiTile.1
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                if (QpRune.QUICK_PANEL_SUBSCREEN) {
                    return;
                }
                WifiTile wifiTile = WifiTile.this;
                if (z) {
                    wifiTile.mSubscreenQsPanelController.getInstance(1).registerReceiver(false);
                } else {
                    wifiTile.mSubscreenQsPanelController.getInstance(1).unRegisterReceiver(false);
                }
            }
        };
        this.mFoldStateChangedListener = r8;
        this.mHandler = handler;
        this.mController = networkController;
        this.mAccessPointController = accessPointController;
        this.mDetailAdapter = new WifiDetailAdapter();
        networkController.observe(((QSTileImpl) this).mLifecycle, wifiSignalCallback);
        signalState.spec = ImsProfile.PDN_WIFI;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        this.mDevicePolicyManager = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        this.mWifiManager = (WifiManager) this.mContext.getSystemService(ImsProfile.PDN_WIFI);
        this.mSettingsHelper = settingsHelper;
        this.mIsHavingConvertView = false;
        this.mPanelInteractor = panelInteractor;
        if (QpRune.QUICK_SETTINGS_SUBSCREEN) {
            this.mDisplayLifecycle = displayLifecycle;
            this.mSubscreenQsPanelController = (SubscreenQsPanelController) Dependency.get(SubscreenQsPanelController.class);
            if (displayLifecycle != 0) {
                displayLifecycle.addObserver(r8);
            }
        } else {
            this.mFoldStateChangedListener = null;
        }
        if (QpRune.QUICK_PANEL_SUBSCREEN) {
            this.mBroadcastDispatcher = broadcastDispatcher;
            if (this.mSubscreenWifiTileReceiver == null && broadcastDispatcher != null) {
                WifiTileReceiver wifiTileReceiver = new WifiTileReceiver();
                this.mSubscreenWifiTileReceiver = wifiTileReceiver;
                broadcastDispatcher.registerReceiver(wifiTileReceiver, new IntentFilter("WIFI_STATE_CHANGE"), null, UserHandle.ALL, 2, "com.samsung.systemui.permission.WIFI_STATE_CHANGE");
            }
        }
        WifiTileReceiver wifiTileReceiver2 = new WifiTileReceiver();
        this.mReceiver = wifiTileReceiver2;
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).registerReceiver(new IntentFilter("com.samsung.android.server.wifi.softap.smarttethering.collapseQuickPanel"), wifiTileReceiver2);
    }

    public static String removeDoubleQuotes(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length <= 1 || str.charAt(0) != '\"') {
            return str;
        }
        int i = length - 1;
        if (str.charAt(i) != '\"') {
            return str;
        }
        try {
            String replaceAll = str.substring(1, i).replaceAll("\\s+$", "");
            if (replaceAll.length() <= 0) {
                return null;
            }
            return replaceAll;
        } catch (NullPointerException unused) {
            return null;
        }
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final DetailAdapter getDetailAdapter() {
        return this.mDetailAdapter;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isWifiTileBlocked()) {
            showItPolicyToast();
        }
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 126;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_sec_wifi_label).trim();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final View view) {
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isWifiTileBlocked()) {
            if (QpRune.QUICK_PANEL_SUBSCREEN) {
                showItPolicyToastOnSubScreen(getSubScreenContext());
                return;
            } else {
                showItPolicyToast();
                return;
            }
        }
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z = keyguardStateControllerImpl.mShowing;
        DisplayLifecycle displayLifecycle = this.mDisplayLifecycle;
        ActivityStarter activityStarter = this.mActivityStarter;
        SettingsHelper settingsHelper = this.mSettingsHelper;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (z && keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && settingsHelper.isLockFunctionsEnabled() && ((QSTile.SignalState) this.mState).value) {
            if (!QpRune.QUICK_PANEL_SUBSCREEN || displayLifecycle == null || displayLifecycle.mIsFolderOpened) {
                activityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.WifiTile$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        WifiTile.this.handleClick(view);
                    }
                });
                return;
            } else {
                ((SubscreenUtil) Dependency.get(SubscreenUtil.class)).showLockscreenOnCoverScreen(this.mContext, "WIFI_STATE_CHANGE");
                return;
            }
        }
        StringBuilder sb = new StringBuilder("isShowing() = ");
        sb.append(keyguardStateControllerImpl.mShowing);
        sb.append(", isSecure() = ");
        sb.append(keyguardUpdateMonitor.isSecure());
        sb.append(", canSkipBouncer() = ");
        sb.append(!keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()));
        sb.append(", isLockFunctionsEnabled() = ");
        sb.append(settingsHelper.isLockFunctionsEnabled());
        String sb2 = sb.toString();
        String str = this.TAG;
        Log.d(str, sb2);
        int i = 0;
        if (!((AccessPointControllerImpl) this.mAccessPointController).canConfigWifi()) {
            activityStarter.postStartActivityDismissingKeyguard(new Intent("android.settings.WIFI_SETTINGS"), 0);
            return;
        }
        QSTile.SignalState signalState = (QSTile.SignalState) this.mState;
        if (signalState.state == 0) {
            Log.d(str, "handleClick pass enabling or disabling ");
            return;
        }
        signalState.copyTo(this.mStateBeforeClick);
        QSTile.SignalState signalState2 = (QSTile.SignalState) this.mState;
        if (!signalState2.value && signalState2.state == 2) {
            signalState2.value = this.mSignalCallback.mInfo.enabled;
            Log.d(str, "handleClick refresh value ");
        }
        boolean z2 = ((QSTile.SignalState) this.mState).value;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("handleClick ", z2, str);
        refreshState(z2 ? null : SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING);
        NetworkControllerImpl networkControllerImpl = (NetworkControllerImpl) this.mController;
        networkControllerImpl.getClass();
        networkControllerImpl.new AsyncTaskC26277(!z2).execute(new Void[0]);
        this.mExpectDisabled = z2;
        if (z2) {
            this.mHandler.postDelayed(new WifiTile$$ExternalSyntheticLambda1(this, i), 350L);
        }
        if (!QpRune.QUICK_PANEL_SUBSCREEN || displayLifecycle == null || displayLifecycle.mIsFolderOpened) {
            return;
        }
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPBE2015");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        WifiTileReceiver wifiTileReceiver;
        BroadcastDispatcher broadcastDispatcher;
        super.handleDestroy();
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).unregisterReceiver(this.mReceiver);
        SemWifiManager semWifiManager = ((AccessPointControllerImpl) this.mAccessPointController).mSemWifiManager;
        if (semWifiManager != null) {
            semWifiManager.setWifiSettingsForegroundState(0);
        }
        String str = this.TAG;
        Log.d(str, "removing wififoreground");
        try {
            this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.WifiTile.2
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayLifecycle displayLifecycle;
                    WifiTile wifiTile = WifiTile.this;
                    C22991 c22991 = wifiTile.mFoldStateChangedListener;
                    if (c22991 == null || (displayLifecycle = wifiTile.mDisplayLifecycle) == null) {
                        return;
                    }
                    displayLifecycle.removeObserver(c22991);
                }
            });
        } catch (Exception e) {
            AbstractC0000x2c234b15.m3m("destroy exception:", Log.getStackTraceString(e), str);
        }
        if (!QpRune.QUICK_PANEL_SUBSCREEN || (wifiTileReceiver = this.mSubscreenWifiTileReceiver) == null || (broadcastDispatcher = this.mBroadcastDispatcher) == null) {
            return;
        }
        broadcastDispatcher.unregisterReceiver(wifiTileReceiver);
        this.mSubscreenWifiTileReceiver = null;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(View view) {
        if (!((AccessPointControllerImpl) this.mAccessPointController).canConfigWifi()) {
            this.mActivityStarter.postStartActivityDismissingKeyguard(new Intent("android.settings.WIFI_SETTINGS"), 0);
            return;
        }
        showDetail(true);
        if (this.mWifiManager != null) {
            Message message = new Message();
            message.what = 74;
            Bundle bundle = new Bundle();
            bundle.putBoolean("enable", false);
            bundle.putBoolean("lock", true);
            message.obj = bundle;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0114  */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleUpdateState(QSTile.State state, Object obj) {
        String removeDoubleQuotes;
        QSTile.SignalState signalState = (QSTile.SignalState) state;
        boolean z = QSTileImpl.DEBUG;
        String str = this.TAG;
        if (z) {
            Log.d(str, "handleUpdateState arg=" + obj);
        }
        CallbackInfo callbackInfo = this.mSignalCallback.mInfo;
        boolean z2 = callbackInfo.enabled;
        Context context = this.mContext;
        context.getResources();
        if (this.mExpectDisabled) {
            if (z2) {
                return;
            } else {
                this.mExpectDisabled = false;
            }
        }
        boolean z3 = obj == SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING;
        boolean z4 = z2 && callbackInfo.wifiSignalIconId > 0 && callbackInfo.ssid != null;
        boolean z5 = callbackInfo.wifiSignalIconId > 0 && callbackInfo.ssid == null;
        if (signalState.value != z2) {
            this.mDetailAdapter.setItemsVisible(z2);
            fireToggleStateChanged(z2);
        }
        boolean z6 = z3 || callbackInfo.isTransient;
        StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("handleUpdateState isTransient=", z6, " transientEnabling =", z3, " cb.isTransient=");
        m69m.append(callbackInfo.isTransient);
        m69m.append(" state.state = ");
        m69m.append(signalState.state);
        m69m.append(" mStateBeforeClick.value =");
        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(m69m, this.mStateBeforeClick.value, " enabled =", z2, str);
        signalState.dualTarget = true;
        signalState.value = z2;
        signalState.activityIn = z2 && callbackInfo.activityIn;
        signalState.activityOut = z2 && callbackInfo.activityOut;
        QSTileImpl.AnimationIcon animationIcon = this.mDisable;
        if (z6 && !this.mIsTransientEnabled) {
            signalState.icon = animationIcon;
            signalState.state = 0;
            signalState.label = getTileLabel();
            this.mIsTransientEnabled = true;
        } else if (z2) {
            signalState.state = 2;
            this.mWifiConnected = z4;
            if (z4) {
                signalState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_wifi_on_013);
                if (((AccessPointControllerImpl) this.mAccessPointController).canConfigWifi()) {
                    removeDoubleQuotes = removeDoubleQuotes(callbackInfo.ssid);
                    StringBuffer stringBuffer = new StringBuffer();
                    String string = context.getString(!signalState.value ? R.string.accessibility_desc_on : R.string.accessibility_desc_off);
                    if (z4) {
                        stringBuffer.append(signalState.label);
                        stringBuffer.append(",");
                        stringBuffer.append(string);
                        stringBuffer.append(",");
                    } else {
                        stringBuffer.append(getTileLabel());
                        stringBuffer.append(",");
                        stringBuffer.append(string);
                        stringBuffer.append(",");
                        stringBuffer.append(removeDoubleQuotes(callbackInfo.ssid));
                    }
                    signalState.contentDescription = stringBuffer.toString();
                    signalState.secondaryLabel = removeDoubleQuotes;
                }
            } else {
                QSTileImpl.AnimationIcon animationIcon2 = this.mEnable;
                if (z5) {
                    signalState.icon = animationIcon2;
                    signalState.label = getTileLabel();
                } else {
                    signalState.icon = animationIcon2;
                    signalState.label = getTileLabel();
                }
            }
        } else {
            signalState.state = 1;
            signalState.icon = animationIcon;
            signalState.label = getTileLabel();
            this.mIsTransientEnabled = false;
        }
        removeDoubleQuotes = "";
        StringBuffer stringBuffer2 = new StringBuffer();
        String string2 = context.getString(!signalState.value ? R.string.accessibility_desc_on : R.string.accessibility_desc_off);
        if (z4) {
        }
        signalState.contentDescription = stringBuffer2.toString();
        signalState.secondaryLabel = removeDoubleQuotes;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final boolean isAvailable() {
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.wifi")) {
            if (!this.mHost.shouldBeHiddenByKnox(this.mTileSpec)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.SignalState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final void setDetailListening(boolean z) {
        if (this.mDetailListening == z) {
            return;
        }
        this.mDetailListening = z;
        WifiDetailAdapter wifiDetailAdapter = this.mDetailAdapter;
        AccessPointController accessPointController = this.mAccessPointController;
        if (z) {
            AccessPointControllerImpl accessPointControllerImpl = (AccessPointControllerImpl) accessPointController;
            accessPointControllerImpl.addAccessPointCallback(wifiDetailAdapter);
            ArrayList arrayList = accessPointControllerImpl.mWifiApBleCallbacks;
            arrayList.add(wifiDetailAdapter);
            if (arrayList.size() == 1) {
                accessPointControllerImpl.mSemWifiManager.registerWifiApSmartCallback(accessPointControllerImpl.mSemWifiApSmartCallback, accessPointControllerImpl.mWifiPickerTrackerFactory.mContext.getMainExecutor());
                return;
            }
            return;
        }
        AccessPointControllerImpl accessPointControllerImpl2 = (AccessPointControllerImpl) accessPointController;
        accessPointControllerImpl2.removeAccessPointCallback(wifiDetailAdapter);
        ArrayList arrayList2 = accessPointControllerImpl2.mWifiApBleCallbacks;
        arrayList2.remove(wifiDetailAdapter);
        if (arrayList2.size() == 0) {
            accessPointControllerImpl2.mSemWifiManager.unregisterWifiApSmartCallback(accessPointControllerImpl2.mSemWifiApSmartCallback);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl
    public final boolean shouldAnnouncementBeDelayed() {
        return this.mStateBeforeClick.value == ((QSTile.SignalState) this.mState).value;
    }
}
