package com.android.systemui.qs.tiles;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.animation.ChangeSize$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.satellite.SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0;
import com.android.settingslib.wifi.WifiEnterpriseRestrictionUtils;
import com.android.systemui.CvOperator;
import com.android.systemui.CvRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.SysUIToast;
import com.android.systemui.animation.Expandable;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSDetailItems;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.DataSaverController;
import com.android.systemui.statusbar.policy.DataSaverControllerImpl;
import com.android.systemui.statusbar.policy.HotspotController;
import com.android.systemui.statusbar.policy.HotspotControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.SatelliteModeObserver$SatelliteModeCallback;
import com.android.systemui.statusbar.policy.SatelliteModeObserverHelper;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public final class HotspotTile extends SQSTileImpl {
    public final WifiManager mAOSPWifiManager;
    public final ActivityStarter mActivityStarter;
    public final DataSaverController mDataSaverController;
    public SystemUIDialog mDataSaverDialog;
    public final HotSpotDetailAdapter mDetailAdapter;
    public final HotspotController mHotspotController;
    public final QSTile.Icon mIcon;
    public boolean mIsSatelliteModeOn;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public boolean mListening;
    public final PanelInteractor mPanelInteractor;
    public final AnonymousClass1 mSatelliteModeCallback;
    public final SatelliteModeObserverHelper mSatelliteModeObserverHelper;
    public SemWifiManager mSemWifiManager;
    private final SettingsHelper mSettingsHelper;
    public final QSTile.BooleanState mStateBeforeClick;
    public final WifiManager mWifiManager;

    public final class CallbackInfo {
        public boolean isDataSaverEnabled;
        public boolean isHotspotEnabled;
        public int numConnectedDevices;

        public final String toString() {
            StringBuilder sb = new StringBuilder("CallbackInfo[isHotspotEnabled=");
            sb.append(this.isHotspotEnabled);
            sb.append(",numConnectedDevices=");
            sb.append(this.numConnectedDevices);
            sb.append(",isDataSaverEnabled=");
            return ChangeSize$$ExternalSyntheticOutline0.m(sb, this.isDataSaverEnabled, ']');
        }
    }

    public final class HotspotAndDataSaverCallbacks implements HotspotController.Callback, DataSaverController.Listener {
        public final CallbackInfo mCallbackInfo;

        public /* synthetic */ HotspotAndDataSaverCallbacks(HotspotTile hotspotTile, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.policy.DataSaverController.Listener
        public final void onDataSaverChanged(boolean z) {
            SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("onDataSaverChanged: ", "HotspotTile", z);
            CallbackInfo callbackInfo = this.mCallbackInfo;
            callbackInfo.isDataSaverEnabled = z;
            HotspotTile.this.refreshState(callbackInfo);
        }

        @Override // com.android.systemui.statusbar.policy.HotspotController.Callback
        public final void onHotspotAvailabilityChanged(boolean z) {
            SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("onHotspotAvailabilityChanged: ", "HotspotTile", z);
            if (z) {
                return;
            }
            Log.d("HotspotTile", "Tile removed. Hotspot no longer available");
            HotspotTile hotspotTile = HotspotTile.this;
            hotspotTile.mHost.removeTile(hotspotTile.mTileSpec);
        }

        @Override // com.android.systemui.statusbar.policy.HotspotController.Callback
        public final void onHotspotChanged(int i, boolean z) {
            SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("onHotspotChanged: ", "HotspotTile", z);
            CallbackInfo callbackInfo = this.mCallbackInfo;
            callbackInfo.isHotspotEnabled = z;
            callbackInfo.numConnectedDevices = i;
            HotspotTile.this.refreshState(callbackInfo);
        }

        @Override // com.android.systemui.statusbar.policy.HotspotController.Callback
        public final void onHotspotPrepared() {
            Log.d("HotspotTile", "onHotspotPrepared");
            Object obj = SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING;
            HotspotTile hotspotTile = HotspotTile.this;
            hotspotTile.refreshState(obj);
            if (hotspotTile.mDetailAdapter != null) {
                hotspotTile.fireScanStateChanged(true);
            }
        }

        @Override // com.android.systemui.statusbar.policy.HotspotController.Callback
        public final void onUpdateConnectedDevices() {
            Log.d("HotspotTile", "onUpdateConnectedDevices =true");
            HotspotTile hotspotTile = HotspotTile.this;
            HotSpotDetailAdapter hotSpotDetailAdapter = hotspotTile.mDetailAdapter;
            if (hotSpotDetailAdapter != null) {
                hotSpotDetailAdapter.updateConnectedDeviceList();
            }
            hotspotTile.refreshState(null);
        }

        private HotspotAndDataSaverCallbacks() {
            this.mCallbackInfo = new CallbackInfo();
        }
    }

    public HotspotTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, HotspotController hotspotController, DataSaverController dataSaverController, KnoxStateMonitor knoxStateMonitor, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, SatelliteModeObserverHelper satelliteModeObserverHelper, PanelInteractor panelInteractor) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mIcon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_mobile_hotspot);
        HotspotAndDataSaverCallbacks hotspotAndDataSaverCallbacks = new HotspotAndDataSaverCallbacks(this, 0);
        this.mStateBeforeClick = new QSTile.BooleanState();
        this.mIsSatelliteModeOn = false;
        this.mSatelliteModeCallback = new SatelliteModeObserver$SatelliteModeCallback() { // from class: com.android.systemui.qs.tiles.HotspotTile.1
            @Override // com.android.systemui.statusbar.policy.SatelliteModeObserver$SatelliteModeCallback
            public final void onSatelliteModeChanged(boolean z) {
                HotspotTile hotspotTile = HotspotTile.this;
                hotspotTile.mIsSatelliteModeOn = z;
                hotspotTile.refreshState(null);
            }
        };
        this.mHotspotController = hotspotController;
        this.mDataSaverController = dataSaverController;
        hotspotController.getClass();
        hotspotController.observe(((QSTileImpl) this).mLifecycle, hotspotAndDataSaverCallbacks);
        dataSaverController.getClass();
        dataSaverController.observe(((QSTileImpl) this).mLifecycle, hotspotAndDataSaverCallbacks);
        this.mKnoxStateMonitor = knoxStateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mActivityStarter = activityStarter;
        this.mDetailAdapter = new HotSpotDetailAdapter();
        this.mSettingsHelper = settingsHelper;
        this.mWifiManager = (WifiManager) this.mContext.getSystemService(ImsProfile.PDN_WIFI);
        this.mAOSPWifiManager = (WifiManager) this.mContext.getSystemService(ImsProfile.PDN_WIFI);
        this.mPanelInteractor = panelInteractor;
        this.mSatelliteModeObserverHelper = satelliteModeObserverHelper;
    }

    public static boolean isBlockedByOthers() {
        if (!CvRune.HOTSPOT_ENABLED_SPRINT_EXTENSION) {
            return false;
        }
        int i = SystemProperties.getInt("persist.sys.tether_data_wifi", -1);
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, " isBlockedByOthers : SPRINT_EXTENSION = ", "HotspotTile");
        return i != -1 && i <= 0;
    }

    public static boolean isSatelliteModeOn(Context context) {
        String string = Settings.Global.getString(context.getContentResolver(), "satellite_mode_radios");
        return string != null && string.contains(ImsProfile.PDN_WIFI) && Settings.Global.getInt(context.getContentResolver(), SettingsHelper.INDEX_STATUS_SATELLITE_MODE_ENABLED, 0) == 1;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final DetailAdapter getDetailAdapter() {
        StringBuilder sb = new StringBuilder("HotspotTile  getDetailAdapter: ");
        HotSpotDetailAdapter hotSpotDetailAdapter = this.mDetailAdapter;
        sb.append(hotSpotDetailAdapter);
        Log.d("HotspotTile", sb.toString());
        return hotSpotDetailAdapter;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        EdmMonitor edmMonitor;
        Log.i("HotspotTile", "getLongClickIntent");
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isWifiHotspotTileBlocked() || (((edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor) != null && edmMonitor.mUserManager.hasUserRestriction("no_config_tethering", UserHandle.of(KeyguardUpdateMonitor.getCurrentUser()))) || isBlockedByEASPolicy())) {
            showItPolicyToast();
            return null;
        }
        if (isWifiApBlocked()) {
            showItPolicyToast();
            return null;
        }
        if (isSatelliteModeOn(this.mContext)) {
            return null;
        }
        if (isDataSaverEnabled()) {
            showDataSaverToast();
            return null;
        }
        if (isAirplaneModeEnabled() || isSatModeEnabled() || isBlockedByOthers()) {
            return null;
        }
        Log.i("HotspotTile", "Launching Mobile Hotspot settings onLong click");
        return new Intent("android.settings.WIFI_AP_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 120;
    }

    public final SemWifiManager getSemWifiManager() {
        if (this.mSemWifiManager == null) {
            this.mSemWifiManager = (SemWifiManager) this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        }
        return this.mSemWifiManager;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getText(CvOperator.getHotspotStringID(R.string.quick_settings_mobile_hotspot_label));
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final Expandable expandable) {
        Log.i("HotspotTile", "handleClick");
        boolean z = ((QSTile.BooleanState) this.mState).value;
        if (getSemWifiManager() == null) {
            Log.e("HotspotTile", " handleClick SemWifiManager is null");
            return;
        }
        int[] softApBands = this.mSemWifiManager.getSoftApBands();
        if (!(!(softApBands != null && softApBands.length > 1) && this.mSemWifiManager.isWifiSharingSupported() && Settings.Secure.getInt(this.mContext.getContentResolver(), "wifi_ap_wifi_sharing", 0) == 1) && Settings.Secure.getInt(this.mContext.getContentResolver(), "wifiap_wifi_tile_clicked", 0) == 1) {
            Log.e("HotspotTile", " handleClick wifi tile is clicked return ");
            return;
        }
        Log.i("HotspotTile", "Checking WifiAp State");
        int wifiApState = this.mSemWifiManager.getWifiApState();
        if (!z && wifiApState != 11 && wifiApState != 14) {
            Log.i("HotspotTile", "return , wifiapstate");
            return;
        }
        if (z && wifiApState != 13) {
            KeyguardSecPatternView$$ExternalSyntheticOutline0.m(wifiApState, "return, wifiapstate", "HotspotTile");
            return;
        }
        if (!z) {
            if (isAirplaneModeEnabled()) {
                return;
            }
            if (((DataSaverControllerImpl) this.mDataSaverController).isDataSaverEnabled$1()) {
                showDataSaverToast();
                return;
            }
        }
        if (((KnoxStateMonitorImpl) this.mKnoxStateMonitor).isWifiHotspotTileBlocked() || isBlockedByEASPolicy() || !(DeviceState.isDataAllowed(this.mContext) || this.mSemWifiManager.isWifiSharingEnabled())) {
            showItPolicyToast();
            return;
        }
        if (isWifiApBlocked()) {
            showItPolicyToast();
            Log.d("HotspotTile", " handleClick  : isWifiApBlocked");
            return;
        }
        if (isSatelliteModeOn(this.mContext)) {
            Log.i("HotspotTile", " handleClick  : SatelliteModeOn");
            return;
        }
        if (isBlockedByOthers()) {
            Log.d("HotspotTile", " handleClick  : isBlockedByOthers");
            return;
        }
        if (isSatModeEnabled()) {
            Log.d("HotspotTile", " handleClick  : isSatModeEnabled");
            return;
        }
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z2 = keyguardStateControllerImpl.mShowing;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (z2 && keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && this.mSettingsHelper.isLockFunctionsEnabled()) {
            this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.HotspotTile$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    HotspotTile.this.handleClick(expandable);
                }
            });
            return;
        }
        Log.d("HotspotTile", "isShowing() = " + keyguardStateControllerImpl.mShowing + ", isSecure() = " + keyguardUpdateMonitor.isSecure() + ", canSkipBouncer() = " + keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) + ", isLockFunctionsEnabled() = " + this.mSettingsHelper.isLockFunctionsEnabled());
        ((QSTile.BooleanState) this.mState).copyTo(this.mStateBeforeClick);
        refreshState(z ? null : SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING);
        Log.d("HotspotTile", "setHotspotEnabled() is called in handleClick() " + z);
        setHotspotEnabled(z ^ true);
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_HOTSPOT_TILE);
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(Expandable expandable) {
        handleSecondaryClick(false);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        SatelliteModeObserverHelper satelliteModeObserverHelper = this.mSatelliteModeObserverHelper;
        AnonymousClass1 anonymousClass1 = this.mSatelliteModeCallback;
        if (z) {
            satelliteModeObserverHelper.addCallback(anonymousClass1);
        } else {
            satelliteModeObserverHelper.removeCallback(anonymousClass1);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        Log.i("HotspotTile", "handleUpdateState");
        boolean z = obj == SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING;
        if (WifiEnterpriseRestrictionUtils.hasUserRestrictionFromT(this.mHost.getUserContext(), "no_wifi_tethering")) {
            Log.w("WifiEntResUtils", "Wi-Fi Tethering isn't available due to user restriction.");
        }
        boolean z2 = z || this.mIsSatelliteModeOn;
        checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_config_tethering");
        if (obj instanceof CallbackInfo) {
            booleanState.value = ((CallbackInfo) obj).isHotspotEnabled;
        } else {
            HotspotControllerImpl hotspotControllerImpl = (HotspotControllerImpl) this.mHotspotController;
            booleanState.value = hotspotControllerImpl.isHotspotEnabled();
            hotspotControllerImpl.getNumConnectedDevices();
            ((DataSaverControllerImpl) this.mDataSaverController).isDataSaverEnabled$1();
        }
        booleanState.dualTarget = true;
        booleanState.label = getTileLabel();
        booleanState.isTransient = z2;
        boolean z3 = booleanState.value;
        if (z2 || isBlockedByOthers()) {
            booleanState.state = 0;
        } else {
            booleanState.state = booleanState.value ? 2 : 1;
        }
        booleanState.icon = this.mIcon;
        booleanState.secondaryLabel = "";
    }

    public final boolean isAirplaneModeEnabled() {
        if (Settings.Global.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_AIRPLANE_MODE_ON, 0) != 1) {
            return false;
        }
        SysUIToast.makeText(this.mContext, R.string.mobile_hotspot_toast_disable_airplne_mode, 0).show();
        return true;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        if (((HotspotControllerImpl) this.mHotspotController).isHotspotSupported()) {
            if (!this.mHost.shouldBeHiddenByKnox(this.mTileSpec)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isBlockedByEASPolicy() {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getApplicationContext().getSystemService("device_policy");
        return (devicePolicyManager == null || devicePolicyManager.semGetAllowInternetSharing(null)) ? false : true;
    }

    public final boolean isDataSaverEnabled() {
        DataSaverController dataSaverController = this.mDataSaverController;
        return dataSaverController != null && ((DataSaverControllerImpl) dataSaverController).isDataSaverEnabled$1();
    }

    public final boolean isSatModeEnabled() {
        ServiceState serviceState;
        return (SemCarrierFeature.getInstance().getBoolean(0, "CarrierFeature_Common_Support_Satellite", false, false) || SemCarrierFeature.getInstance().getBoolean(1, "CarrierFeature_Common_Support_Satellite", false, false)) && (serviceState = ((TelephonyManager) this.mContext.getSystemService("phone")).getServiceState()) != null && serviceState.isUsingNonTerrestrialNetwork();
    }

    public final boolean isWifiApBlocked() {
        Cursor query = this.mContext.getContentResolver().query(Uri.parse("content://com.sec.knox.provider/RestrictionPolicy4"), null, "isWifiTetheringEnabled", null, null);
        if (query == null) {
            return false;
        }
        try {
            query.moveToFirst();
            return query.getString(query.getColumnIndex("isWifiTetheringEnabled")).equals("false");
        } finally {
            query.close();
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0290, code lost:
    
        if (r4 == false) goto L150;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setHotspotEnabled(boolean r13) {
        /*
            Method dump skipped, instructions count: 837
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.HotspotTile.setHotspotEnabled(boolean):void");
    }

    public final void showDataSaverToast() {
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME");
        if (string == null || !"com.samsung.android.sm_cn".equals(string)) {
            SysUIToast.makeText(this.mContext, android.R.string.reboot_to_update_title, 0).show();
            return;
        }
        SystemUIDialog systemUIDialog = this.mDataSaverDialog;
        if (systemUIDialog == null || !systemUIDialog.isShowing()) {
            SystemUIDialog systemUIDialog2 = new SystemUIDialog(this.mContext, R.style.Theme_SystemUI_Dialog_Alert);
            this.mDataSaverDialog = systemUIDialog2;
            systemUIDialog2.setTitle(17043560);
            this.mDataSaverDialog.setMessage(17043557);
            this.mDataSaverDialog.setNegativeButton(17043559, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.HotspotTile.4
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent();
                    intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.SubSettings");
                    intent.putExtra(":settings:show_fragment", "com.samsung.android.settings.datausage.trafficmanager.ui.DataSaverSummaryCHN");
                    intent.addFlags(268468224);
                    HotspotTile.this.mContext.startActivity(intent);
                }
            });
            this.mDataSaverDialog.setPositiveButton(17043558, null);
            this.mDataSaverDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.tiles.HotspotTile.5
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    HotspotTile.this.refreshState(null);
                }
            });
            SystemUIDialog.setWindowOnTop(this.mDataSaverDialog, false);
            this.mDataSaverDialog.show();
            this.mPanelInteractor.collapsePanels();
        }
    }

    public final void handleSecondaryClick(boolean z) {
        EdmMonitor edmMonitor;
        Log.d("HotspotTile", "handleSecondaryClick");
        if (isAirplaneModeEnabled() || isBlockedByOthers()) {
            return;
        }
        KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) this.mKnoxStateMonitor;
        if (knoxStateMonitorImpl.isWifiHotspotTileBlocked() || (((edmMonitor = knoxStateMonitorImpl.mEdmMonitor) != null && edmMonitor.mUserManager.hasUserRestriction("no_config_tethering", UserHandle.of(KeyguardUpdateMonitor.getCurrentUser()))) || isBlockedByEASPolicy() || !(DeviceState.isDataAllowed(this.mContext) || this.mSemWifiManager.isWifiSharingEnabled()))) {
            showItPolicyToast();
            return;
        }
        if (isWifiApBlocked()) {
            showItPolicyToast();
            return;
        }
        if (isSatelliteModeOn(this.mContext) || isSatModeEnabled()) {
            return;
        }
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            if (keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && this.mSettingsHelper.isLockFunctionsEnabled()) {
                this.mActivityStarter.postQSRunnableDismissingKeyguard(new HotspotTile$$ExternalSyntheticLambda1(this, 0));
                return;
            }
        }
        if (z) {
            ((CentralSurfacesImpl) ((CentralSurfaces) Dependency.sDependency.getDependencyInner(CentralSurfaces.class))).openQSPanelWithDetail("Hotspot");
        } else {
            showDetail(true);
        }
    }

    public final class HotSpotDetailAdapter implements DetailAdapter, QSDetailItems.Callback {
        public int deviceCount = 0;
        public LinearLayout mApLayout;
        public List mConnectedDevices;
        public View mConnectedListContainer;
        public QSDetailItems mItems;
        public TextView mMobileApName;
        public String mPassWord;
        public LinearLayout mPassWordLayout;
        public TextView mPassword;
        public WifiManager mWifiManager;

        public HotSpotDetailAdapter() {
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
            StringBuilder sb = new StringBuilder("createDetailView convertView=");
            sb.append(view != null);
            sb.append(" mState.value ");
            HotspotTile hotspotTile = HotspotTile.this;
            ActionBarContextView$$ExternalSyntheticOutline0.m(sb, ((QSTile.BooleanState) hotspotTile.mState).value, "HotspotTile");
            if (this.mWifiManager == null) {
                this.mWifiManager = (WifiManager) hotspotTile.mContext.getSystemService(ImsProfile.PDN_WIFI);
            }
            View inflate = LayoutInflater.from(hotspotTile.mContext).inflate(R.layout.qs_detail_hotspot, viewGroup, false);
            this.mApLayout = (LinearLayout) inflate.findViewById(R.id.ap_layout);
            this.mPassWordLayout = (LinearLayout) inflate.findViewById(R.id.password_layout);
            this.mMobileApName = (TextView) inflate.findViewById(R.id.ap_name);
            this.mConnectedListContainer = inflate.findViewById(R.id.connected_list_container);
            this.mPassword = (TextView) inflate.findViewById(R.id.ap_password);
            ViewGroup viewGroup2 = (ViewGroup) inflate.findViewById(R.id.connected_devices);
            QSDetailItems convertOrInflate = QSDetailItems.convertOrInflate(context, viewGroup2);
            this.mItems = convertOrInflate;
            convertOrInflate.setTagSuffix("HotSpot");
            this.mItems.setCallback(this);
            viewGroup2.addView(this.mItems);
            Log.d("HotspotTile", "updateHotSpotApInfo");
            if (this.mWifiManager == null) {
                this.mWifiManager = (WifiManager) hotspotTile.mContext.getSystemService(ImsProfile.PDN_WIFI);
            }
            if (hotspotTile.getSemWifiManager() == null) {
                Log.e("HotspotTile", " updateHotSpotApInfo SemWifiManager is null");
            } else {
                SoftApConfiguration softApConfiguration = hotspotTile.mSemWifiManager.getSoftApConfiguration();
                String passphrase = softApConfiguration.getPassphrase();
                if (hotspotTile.DEBUG) {
                    Log.d("HotspotTile", "mobileAp Name = " + softApConfiguration.getSsid());
                }
                this.mMobileApName.setText(softApConfiguration.getSsid());
                if (TextUtils.isEmpty(passphrase)) {
                    this.mPassword.setText(R.string.assistance_app_setting_item_none);
                } else if (passphrase.equals("\tUSER#DEFINED#PWD#\n")) {
                    String string = Settings.Secure.getString(hotspotTile.mContext.getContentResolver(), "wifi_ap_random_password");
                    if (string == null || string.equals("")) {
                        StringBuilder sb2 = new StringBuilder();
                        Random random = new Random(SystemClock.uptimeMillis());
                        StringBuffer stringBuffer = new StringBuffer();
                        for (int i = 0; i < 4; i++) {
                            stringBuffer.append("abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26)));
                        }
                        sb2.append(stringBuffer.toString());
                        Random random2 = new Random(SystemClock.uptimeMillis() + 1);
                        int i2 = 10;
                        for (int i3 = 1; i3 < 3; i3++) {
                            i2 *= 10;
                        }
                        Locale locale = Locale.US;
                        sb2.append(String.format("%03d", Integer.valueOf(random2.nextInt(i2 - 1))));
                        Random random3 = new Random(SystemClock.uptimeMillis());
                        StringBuffer stringBuffer2 = new StringBuffer();
                        stringBuffer2.append("!@#$/^&*()".charAt(random3.nextInt(10)));
                        sb2.append(stringBuffer2.toString());
                        String sb3 = sb2.toString();
                        Settings.Secure.putString(hotspotTile.mContext.getContentResolver(), "wifi_ap_random_password", sb3);
                        this.mPassword.setText(sb3);
                    } else {
                        this.mPassword.setText(Settings.Secure.getString(hotspotTile.mContext.getContentResolver(), "wifi_ap_random_password"));
                    }
                } else {
                    this.mPassword.setText(passphrase);
                }
                this.mApLayout.setContentDescription(this.mMobileApName.getText().toString());
                this.mPassWord = ", " + this.mPassword.getText().toString();
                this.mPassWordLayout.setContentDescription(hotspotTile.mContext.getResources().getString(R.string.mobile_hotspot_detail_password) + this.mPassWord);
            }
            updateConnectedDeviceList();
            return inflate;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final int getMetricsCategory() {
            return VolteConstants.ErrorCode.CALL_CANCEL_TRANSFER_SUCCESS;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final Intent getSettingsIntent() {
            if (HotspotTile.this.isDataSaverEnabled()) {
                return null;
            }
            return new Intent("android.settings.WIFI_AP_SETTINGS");
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final CharSequence getTitle() {
            return HotspotTile.this.mContext.getString(CvOperator.getHotspotStringID(R.string.mobile_hotspot_detail_title));
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final boolean getToggleEnabled() {
            ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder(" getToggleEnabled - "), ((QSTile.BooleanState) HotspotTile.this.mState).isTransient, "HotspotTile");
            return !((QSTile.BooleanState) r3.mState).isTransient;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final Boolean getToggleState() {
            return Boolean.valueOf(((QSTile.BooleanState) HotspotTile.this.mState).value);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final void setToggleState(boolean z) {
            HotspotTile hotspotTile = HotspotTile.this;
            if (hotspotTile.getSemWifiManager() == null) {
                Log.e("HotspotTile", "getSemWifiManager is null");
                hotspotTile.fireToggleStateChanged(getToggleState().booleanValue());
                return;
            }
            int wifiApState = hotspotTile.getSemWifiManager().getWifiApState();
            Log.i("HotspotTile", "setToggleState:state," + z + ",apiState:" + wifiApState);
            if (z) {
                if (wifiApState != 11 && wifiApState != 14) {
                    hotspotTile.fireToggleStateChanged(getToggleState().booleanValue());
                    return;
                }
            } else if (wifiApState != 13 && wifiApState != 14) {
                hotspotTile.fireToggleStateChanged(getToggleState().booleanValue());
                return;
            }
            if (((KnoxStateMonitorImpl) hotspotTile.mKnoxStateMonitor).isWifiHotspotTileBlocked() || hotspotTile.isBlockedByEASPolicy()) {
                hotspotTile.showItPolicyToast();
                hotspotTile.fireToggleStateChanged(getToggleState().booleanValue());
                return;
            }
            if (HotspotTile.isSatelliteModeOn(hotspotTile.mContext)) {
                hotspotTile.fireToggleStateChanged(getToggleState().booleanValue());
                return;
            }
            if (hotspotTile.isWifiApBlocked()) {
                hotspotTile.showItPolicyToast();
                hotspotTile.fireToggleStateChanged(getToggleState().booleanValue());
                return;
            }
            if (hotspotTile.isDataSaverEnabled()) {
                hotspotTile.showDataSaverToast();
                hotspotTile.fireToggleStateChanged(getToggleState().booleanValue());
                return;
            }
            if (hotspotTile.isSatModeEnabled()) {
                hotspotTile.fireToggleStateChanged(getToggleState().booleanValue());
                return;
            }
            KeyguardUpdateMonitor keyguardUpdateMonitor = hotspotTile.mKeyguardUpdateMonitor;
            if (keyguardUpdateMonitor.isKeyguardVisible() && keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && hotspotTile.mSettingsHelper.isLockFunctionsEnabled()) {
                hotspotTile.mActivityStarter.postQSRunnableDismissingKeyguard(new HotspotTile$$ExternalSyntheticLambda1(this, 1));
                hotspotTile.fireToggleStateChanged(getToggleState().booleanValue());
                return;
            }
            Log.d("HotspotTile", "isShowing() = " + keyguardUpdateMonitor.isKeyguardVisible() + ", isSecure() = " + keyguardUpdateMonitor.isSecure() + ", canSkipBouncer() = " + keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) + ", isLockFunctionsEnabled() = " + hotspotTile.mSettingsHelper.isLockFunctionsEnabled());
            hotspotTile.refreshState(z ? SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING : null);
            hotspotTile.setHotspotEnabled(z);
            boolean z2 = ((QSTile.BooleanState) hotspotTile.mState).value && this.mConnectedDevices != null;
            View view = this.mConnectedListContainer;
            if (view != null) {
                view.setVisibility(z2 ? 0 : 8);
            }
        }

        public final void updateConnectedDeviceList() {
            HotspotTile hotspotTile = HotspotTile.this;
            if (hotspotTile.getSemWifiManager() == null) {
                Log.e("HotspotTile", " updateConnectedDeviceList SemWifiManager is null");
                return;
            }
            List wifiApStaListDetail = hotspotTile.mSemWifiManager.getWifiApStaListDetail();
            this.mConnectedDevices = wifiApStaListDetail;
            if (wifiApStaListDetail != null) {
                this.deviceCount = wifiApStaListDetail.size();
            }
            Log.d("HotspotTile", "updateItems");
            if (this.mItems != null) {
                int i = this.deviceCount;
                QSDetailItems.Item[] itemArr = new QSDetailItems.Item[i];
                if (i != 0) {
                    for (int i2 = 0; i2 < this.deviceCount; i2++) {
                        String[] split = ((String) this.mConnectedDevices.get(i2)).split("\n");
                        String str = split[2];
                        long parseLong = Long.parseLong(split[3]);
                        String format = DateFormat.getTimeFormat(hotspotTile.mContext).format(new Date(parseLong));
                        StringBuilder m = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(DateFormat.format(DateFormat.getBestDateTimePattern(Locale.getDefault(), "MMM dd"), new Date(parseLong)).toString());
                        m.append(hotspotTile.mContext.getString(R.string.comma));
                        m.append(" ");
                        m.append(format);
                        String sb = m.toString();
                        QSDetailItems.Item item = new QSDetailItems.Item();
                        if ("(null)".equals(str)) {
                            str = hotspotTile.mContext.getString(R.string.mobile_hotspot_detail_connected_device);
                        }
                        item.iconVisibility = false;
                        item.itemPaddingAboveBelow = hotspotTile.mContext.getResources().getDimensionPixelSize(R.dimen.wifi_ap_item_above_below_padding);
                        item.line1textSize = hotspotTile.mContext.getResources().getDimensionPixelSize(R.dimen.wifi_ap_item_title_text_size);
                        item.line2textSize = hotspotTile.mContext.getResources().getDimensionPixelSize(R.dimen.wifi_ap_item_summary_text_size);
                        item.line1 = str;
                        item.line2 = sb;
                        item.isClickable = false;
                        itemArr[i2] = item;
                    }
                }
                this.mItems.setItems(itemArr);
            }
            Log.d("HotspotTile", " updateConnectedDeviceList mConnectedDevices = " + this.mConnectedDevices);
            boolean z = ((QSTile.BooleanState) hotspotTile.mState).value && this.mConnectedDevices != null;
            View view = this.mConnectedListContainer;
            if (view != null) {
                view.setVisibility(z ? 0 : 8);
            }
        }

        @Override // com.android.systemui.qs.QSDetailItems.Callback
        public final void onDetailItemClick(QSDetailItems.Item item) {
        }
    }
}
