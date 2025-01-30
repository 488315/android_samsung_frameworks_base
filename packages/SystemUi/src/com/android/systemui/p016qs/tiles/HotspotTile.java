package com.android.systemui.p016qs.tiles;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.display.DisplayManager;
import android.hardware.display.SemWifiDisplayStatus;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.aware.WifiAwareManager;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.wifi.WifiEnterpriseRestrictionUtils;
import com.android.systemui.CvOperator;
import com.android.systemui.CvRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.SysUIToast;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.p016qs.QSDetailItems;
import com.android.systemui.p016qs.QSHost;
import com.android.systemui.p016qs.QsEventLogger;
import com.android.systemui.p016qs.logging.QSLogger;
import com.android.systemui.p016qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.p016qs.tileimpl.QSTileImpl;
import com.android.systemui.p016qs.tileimpl.SQSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p013qs.DetailAdapter;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.DataSaverController;
import com.android.systemui.statusbar.policy.DataSaverControllerImpl;
import com.android.systemui.statusbar.policy.HotspotController;
import com.android.systemui.statusbar.policy.HotspotControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.wifi.SemWifiApCust;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HotspotTile extends SQSTileImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final WifiManager mAOSPWifiManager;
    public final ActivityStarter mActivityStarter;
    public final DataSaverController mDataSaverController;
    public SystemUIDialog mDataSaverDialog;
    public final HotspotController mHotspotController;
    public final QSTile.Icon mIcon;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public boolean mListening;
    public final PanelInteractor mPanelInteractor;
    public SemWifiManager mSemWifiManager;
    public final SettingsHelper mSettingsHelper;
    public final QSTile.BooleanState mStateBeforeClick;
    public final WifiManager mWifiManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CallbackInfo {
        public boolean isDataSaverEnabled;
        public boolean isHotspotEnabled;
        public int numConnectedDevices;

        public final String toString() {
            return "CallbackInfo[isHotspotEnabled=" + this.isHotspotEnabled + ",numConnectedDevices=" + this.numConnectedDevices + ",isDataSaverEnabled=" + this.isDataSaverEnabled + ']';
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class HotspotAndDataSaverCallbacks implements HotspotController.Callback, DataSaverController.Listener {
        public final CallbackInfo mCallbackInfo;

        public /* synthetic */ HotspotAndDataSaverCallbacks(HotspotTile hotspotTile, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.policy.DataSaverController.Listener
        public final void onDataSaverChanged(boolean z) {
            ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("onDataSaverChanged: ", z, "HotspotTile");
            CallbackInfo callbackInfo = this.mCallbackInfo;
            callbackInfo.isDataSaverEnabled = z;
            int i = HotspotTile.$r8$clinit;
            HotspotTile.this.refreshState(callbackInfo);
        }

        @Override // com.android.systemui.statusbar.policy.HotspotController.Callback
        public final void onHotspotAvailabilityChanged(boolean z) {
            ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("onHotspotAvailabilityChanged: ", z, "HotspotTile");
            if (z) {
                return;
            }
            Log.d("HotspotTile", "Tile removed. Hotspot no longer available");
            int i = HotspotTile.$r8$clinit;
            HotspotTile hotspotTile = HotspotTile.this;
            hotspotTile.mHost.removeTile(hotspotTile.mTileSpec);
        }

        @Override // com.android.systemui.statusbar.policy.HotspotController.Callback
        public final void onHotspotChanged(int i, boolean z) {
            ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("onHotspotChanged: ", z, "HotspotTile");
            CallbackInfo callbackInfo = this.mCallbackInfo;
            callbackInfo.isHotspotEnabled = z;
            callbackInfo.numConnectedDevices = i;
            int i2 = HotspotTile.$r8$clinit;
            HotspotTile.this.refreshState(callbackInfo);
        }

        @Override // com.android.systemui.statusbar.policy.HotspotController.Callback
        public final void onHotspotPrepared() {
            Log.d("HotspotTile", "onHotspotPrepared");
            int i = HotspotTile.$r8$clinit;
            Object obj = SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING;
            HotspotTile hotspotTile = HotspotTile.this;
            hotspotTile.refreshState(obj);
            hotspotTile.getClass();
        }

        @Override // com.android.systemui.statusbar.policy.HotspotController.Callback
        public final void onUpdateConnectedDevices() {
            Log.d("HotspotTile", "onUpdateConnectedDevices =true");
            int i = HotspotTile.$r8$clinit;
            HotspotTile hotspotTile = HotspotTile.this;
            hotspotTile.getClass();
            hotspotTile.refreshState(null);
        }

        private HotspotAndDataSaverCallbacks() {
            this.mCallbackInfo = new CallbackInfo();
        }
    }

    public HotspotTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, HotspotController hotspotController, DataSaverController dataSaverController, KnoxStateMonitor knoxStateMonitor, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, PanelInteractor panelInteractor) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mIcon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_wifihotspot);
        HotspotAndDataSaverCallbacks hotspotAndDataSaverCallbacks = new HotspotAndDataSaverCallbacks(this, 0);
        this.mStateBeforeClick = new QSTile.BooleanState();
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
        this.mSettingsHelper = settingsHelper;
        this.mWifiManager = (WifiManager) this.mContext.getSystemService(ImsProfile.PDN_WIFI);
        this.mAOSPWifiManager = (WifiManager) this.mContext.getSystemService(ImsProfile.PDN_WIFI);
        this.mPanelInteractor = panelInteractor;
    }

    public static boolean isBlockedByOthers() {
        if (CvRune.HOTSPOT_ENABLED_SPRINT_EXTENSION) {
            int i = SystemProperties.getInt("persist.sys.tether_data_wifi", -1);
            ListPopupWindow$$ExternalSyntheticOutline0.m10m(" isBlockedByOthers : SPRINT_EXTENSION = ", i, "HotspotTile");
            if (i != -1 && i <= 0) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final DetailAdapter getDetailAdapter() {
        Log.d("HotspotTile", "HotspotTile  getDetailAdapter: null");
        return null;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        Log.i("HotspotTile", "getLongClickIntent");
        if (!((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isWifiHotspotTileBlocked()) {
            EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mEdmMonitor;
            if (!(edmMonitor != null && edmMonitor.mUserManager.hasUserRestriction("no_config_tethering", UserHandle.of(KeyguardUpdateMonitor.getCurrentUser()))) && !isBlockedByEASPolicy()) {
                if (isWifiApBlocked()) {
                    showItPolicyToast();
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
        }
        showItPolicyToast();
        return null;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final int getMetricsCategory() {
        return 120;
    }

    public final SemWifiManager getSemWifiManager() {
        if (this.mSemWifiManager == null) {
            this.mSemWifiManager = (SemWifiManager) this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        }
        return this.mSemWifiManager;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getText(CvOperator.getHotspotStringID(R.string.quick_settings_mobile_hotspot_label));
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleClick(final View view) {
        Log.i("HotspotTile", "handleClick");
        boolean z = ((QSTile.BooleanState) this.mState).value;
        if (getSemWifiManager() == null) {
            Log.e("HotspotTile", " handleClick SemWifiManager is null");
            return;
        }
        Log.i("HotspotTile", "Checking WifiAp State");
        int wifiApState = this.mSemWifiManager.getWifiApState();
        if (!z && wifiApState != 11 && wifiApState != 14) {
            Log.i("HotspotTile", "return , wifiapstate");
            return;
        }
        if (z && wifiApState != 13) {
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("return, wifiapstate", wifiApState, "HotspotTile");
            return;
        }
        if (!z) {
            if (isAirplaneModeEnabled()) {
                return;
            }
            if (((DataSaverControllerImpl) this.mDataSaverController).isDataSaverEnabled()) {
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
        SettingsHelper settingsHelper = this.mSettingsHelper;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (z2 && keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && settingsHelper.isLockFunctionsEnabled()) {
            this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.HotspotTile$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    HotspotTile.this.handleClick(view);
                }
            });
            return;
        }
        Log.d("HotspotTile", "isShowing() = " + keyguardStateControllerImpl.mShowing + ", isSecure() = " + keyguardUpdateMonitor.isSecure() + ", canSkipBouncer() = " + keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) + ", isLockFunctionsEnabled() = " + settingsHelper.isLockFunctionsEnabled());
        ((QSTile.BooleanState) this.mState).copyTo(this.mStateBeforeClick);
        refreshState(z ? null : SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING);
        Log.d("HotspotTile", "setHotspotEnabled() is called in handleClick() " + z);
        setHotspotEnabled(z ^ true);
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(View view) {
        handleSecondaryClick(false);
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        if (z) {
            refreshState(null);
        }
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        Log.i("HotspotTile", "handleUpdateState");
        boolean z = obj == SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING;
        if (WifiEnterpriseRestrictionUtils.hasUserRestrictionFromT(this.mHost.getUserContext(), "no_wifi_tethering")) {
            Log.w("WifiEntResUtils", "Wi-Fi Tethering isn't available due to user restriction.");
        }
        checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_config_tethering");
        if (obj instanceof CallbackInfo) {
            booleanState.value = ((CallbackInfo) obj).isHotspotEnabled;
        } else {
            HotspotControllerImpl hotspotControllerImpl = (HotspotControllerImpl) this.mHotspotController;
            booleanState.value = hotspotControllerImpl.isHotspotEnabled();
            hotspotControllerImpl.getNumConnectedDevices();
            ((DataSaverControllerImpl) this.mDataSaverController).isDataSaverEnabled();
        }
        booleanState.dualTarget = true;
        booleanState.label = getTileLabel();
        booleanState.isTransient = z;
        if (z || isBlockedByOthers()) {
            booleanState.state = 0;
        } else {
            booleanState.state = booleanState.value ? 2 : 1;
        }
        booleanState.icon = this.mIcon;
        booleanState.secondaryLabel = "";
    }

    public final boolean isAirplaneModeEnabled() {
        Context context = this.mContext;
        if (Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0) != 1) {
            return false;
        }
        SysUIToast.makeText(R.string.mobile_hotspot_toast_disable_airplne_mode, context, 0).show();
        return true;
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
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
        return dataSaverController != null && ((DataSaverControllerImpl) dataSaverController).isDataSaverEnabled();
    }

    public final boolean isSatModeEnabled() {
        ServiceState serviceState;
        return SemCarrierFeature.getInstance().getBoolean(0, "CarrierFeature_Common_Support_Satellite", false, false) && (serviceState = ((TelephonyManager) this.mContext.getSystemService("phone")).getServiceState()) != null && serviceState.isUsingNonTerrestrialNetwork();
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

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x022d, code lost:
    
        if (r5 == false) goto L135;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x017d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setHotspotEnabled(boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        int i;
        boolean z8;
        Context context = this.mContext;
        if (z) {
            WifiManager wifiManager = this.mWifiManager;
            int wifiState = wifiManager.getWifiState();
            SemWifiManager semWifiManager = (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
            this.mSemWifiManager = semWifiManager;
            if (semWifiManager != null) {
                if (semWifiManager.isOverAllMhsDataLimitReached()) {
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                    Network activeNetwork = connectivityManager.getActiveNetwork();
                    if (activeNetwork == null) {
                        Log.i("HotspotTile", "ActiveNetwork is Null");
                    } else {
                        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
                        if (networkCapabilities == null) {
                            Log.i("HotspotTile", "networkCapabilities is Null");
                        } else {
                            boolean hasTransport = networkCapabilities.hasTransport(0);
                            boolean hasCapability = networkCapabilities.hasCapability(12);
                            if (hasTransport && hasCapability) {
                                z7 = true;
                                if (!z7) {
                                    WifiInfo connectionInfo = this.mAOSPWifiManager.getConnectionInfo();
                                    if (connectionInfo == null || connectionInfo.getNetworkId() == -1) {
                                        i = -1;
                                    } else {
                                        Log.d("HotspotTile", "Wifi Frequency is " + connectionInfo.getFrequency());
                                        i = connectionInfo.getFrequency();
                                    }
                                    if (i == -1 && telephonyManager != null && telephonyManager.isDataEnabled()) {
                                        Log.i("HotspotTile", "Wi-Fi is not connected and mobile data is enabled");
                                        z7 = true;
                                    }
                                }
                                if (z7) {
                                    Log.i("HotspotTile", "Data limit is reached");
                                    z6 = true;
                                    if (!z6) {
                                        if (CvRune.HOTSPOT_CHECK_MHSDBG && SystemProperties.get("vendor.wifiap.provisioning.disable").equals("1")) {
                                            Log.d("HotspotTile", "Skip isProvisioningCheck");
                                        } else if (SemWifiApCust.isProvisioningNeeded()) {
                                            String[] stringArray = context.getResources().getStringArray(17236249);
                                            Log.i("HotspotTile", "Calling UTP apk");
                                            if (stringArray.length == 2) {
                                                z8 = true;
                                            }
                                        } else {
                                            Log.i("HotspotTile", " provisioning is not required for this operator");
                                        }
                                        z8 = false;
                                    }
                                    Log.d("HotspotTile", "enable hotspot for USA or SBM");
                                    if (this.mSemWifiManager.getWifiApWarningActivityRunningState() == 1) {
                                        Log.i("HotspotTile", "sending WIFIAP_WARNING_STOP_DIALOG ");
                                        Intent intent = new Intent();
                                        intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
                                        intent.setAction("com.samsung.android.settings.wifi.mobileap.wifiapwarning.finish");
                                        context.sendBroadcast(intent);
                                        try {
                                            Thread.sleep(200L);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    Intent intent2 = new Intent();
                                    intent2.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.samsung.android.settings.wifi.mobileap.WifiApWarning");
                                    intent2.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                                    intent2.setAction("com.samsung.android.settings.wifi.mobileap.wifiapwarning");
                                    intent2.putExtra("wifiap_warning_dialog_type", 5);
                                    context.startActivity(intent2);
                                    Log.d("HotspotTile", "launchWifiApWarning start for USA or SBM");
                                    this.mPanelInteractor.collapsePanels();
                                    return;
                                }
                            }
                        }
                    }
                    z7 = false;
                    if (!z7) {
                    }
                    if (z7) {
                    }
                }
                int[] softApBands = this.mSemWifiManager.getSoftApBands();
                if ((softApBands != null && softApBands.length > 1) && (wifiState == 2 || wifiState == 3)) {
                    Log.i("HotspotTile", "DualAP with Wi-Fi Enabled");
                } else {
                    SemWifiDisplayStatus semGetWifiDisplayStatus = ((DisplayManager) context.getSystemService("display")).semGetWifiDisplayStatus();
                    boolean isP2pConnected = this.mSemWifiManager.isP2pConnected();
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("isSmartViewEnabled:p2pstatus:", isP2pConnected, "HotspotTile");
                    if (semGetWifiDisplayStatus != null && semGetWifiDisplayStatus.getActiveDisplayState() == 2 && semGetWifiDisplayStatus.getConnectedState() == 0 && isP2pConnected) {
                        Log.d("HotspotTile", "isSmartViewEnabled:true");
                        z2 = true;
                    } else {
                        Log.d("HotspotTile", "isSmartViewEnabled:false");
                        z2 = false;
                    }
                    if (z2) {
                        Log.i("HotspotTile", "smartView Enabled");
                    } else {
                        SemWifiDisplayStatus semGetWifiDisplayStatus2 = ((DisplayManager) context.getSystemService("display")).semGetWifiDisplayStatus();
                        if (semGetWifiDisplayStatus2 != null && semGetWifiDisplayStatus2.getActiveDisplayState() == 2 && semGetWifiDisplayStatus2.getConnectedState() == 2) {
                            Log.d("HotspotTile", "isWirelessDexEnabled:true");
                            z3 = true;
                        } else {
                            Log.d("HotspotTile", "isWirelessDexEnabled:false");
                            z3 = false;
                        }
                        if (z3) {
                            Log.i("HotspotTile", "WirelessDex Enabled");
                        } else {
                            WifiAwareManager wifiAwareManager = context.getPackageManager().hasSystemFeature("android.hardware.wifi.aware") ? (WifiAwareManager) context.getSystemService("wifiaware") : null;
                            if (wifiAwareManager != null) {
                                try {
                                    z4 = ((Boolean) wifiAwareManager.getClass().getMethod("isPreEnabled", new Class[0]).invoke(wifiAwareManager, new Object[0])).booleanValue();
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                    z4 = false;
                                }
                                if (wifiAwareManager.isDeviceAttached() && !z4) {
                                    z5 = true;
                                    if (!z5) {
                                        Log.i("HotspotTile", "NAN Enabled");
                                    } else if (this.mSemWifiManager.isP2pConnected()) {
                                        Log.i("HotspotTile", "P2p Enabled");
                                    } else {
                                        if (wifiState != 1) {
                                            if (this.mSemWifiManager.isWifiSharingLiteSupported()) {
                                                Log.i("HotspotTile", "WifiSharingLite model");
                                            } else {
                                                if (!(Settings.Secure.getInt(context.getContentResolver(), "wifi_ap_wifi_sharing", 0) == 1)) {
                                                    Log.i("HotspotTile", "Wifi is not disabled and wifisharing is not enabled");
                                                }
                                            }
                                        }
                                        if (this.mSemWifiManager.isWifiSharingSupported() && !this.mSemWifiManager.isWifiSharingLiteSupported() && Settings.Secure.getInt(context.getContentResolver(), "wifi_ap_wifi_sharing", 10) == 1 && Settings.Secure.getInt(context.getContentResolver(), "wifi_ap_first_time_wifi_sharing_dialog", 0) == 0 && (wifiManager.getWifiState() == 2 || wifiManager.getWifiState() == 3)) {
                                            Log.i("HotspotTile", "Wi-Fi Sharing First dialog");
                                        }
                                    }
                                }
                            }
                            z5 = false;
                            if (!z5) {
                            }
                        }
                    }
                }
                z6 = true;
                if (!z6) {
                }
                Log.d("HotspotTile", "enable hotspot for USA or SBM");
                if (this.mSemWifiManager.getWifiApWarningActivityRunningState() == 1) {
                }
                Intent intent22 = new Intent();
                intent22.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.samsung.android.settings.wifi.mobileap.WifiApWarning");
                intent22.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                intent22.setAction("com.samsung.android.settings.wifi.mobileap.wifiapwarning");
                intent22.putExtra("wifiap_warning_dialog_type", 5);
                context.startActivity(intent22);
                Log.d("HotspotTile", "launchWifiApWarning start for USA or SBM");
                this.mPanelInteractor.collapsePanels();
                return;
            }
            Log.e("HotspotTile", " checkWhetherWifiApWarningNeedToLaunch mSemWifiManager is null");
            z6 = false;
            if (!z6) {
            }
            Log.d("HotspotTile", "enable hotspot for USA or SBM");
            if (this.mSemWifiManager.getWifiApWarningActivityRunningState() == 1) {
            }
            Intent intent222 = new Intent();
            intent222.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.samsung.android.settings.wifi.mobileap.WifiApWarning");
            intent222.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
            intent222.setAction("com.samsung.android.settings.wifi.mobileap.wifiapwarning");
            intent222.putExtra("wifiap_warning_dialog_type", 5);
            context.startActivity(intent222);
            Log.d("HotspotTile", "launchWifiApWarning start for USA or SBM");
            this.mPanelInteractor.collapsePanels();
            return;
        }
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("setHotspotEnabled -", z, "HotspotTile");
        if (z && "LGT".equals(CvRune.HOTSPOT_CONFIG_OP_BRANDING)) {
            SysUIToast.makeText(R.string.wifi_ap_warn_toast_lgt, context, 0).show();
        }
        fireToggleStateChanged(z);
        HotspotControllerImpl hotspotControllerImpl = (HotspotControllerImpl) this.mHotspotController;
        if (hotspotControllerImpl.mWaitingForTerminalState) {
            if (HotspotControllerImpl.DEBUG) {
                Log.i("HotspotController", "Ignoring setHotspotEnabled; waiting for terminal state.");
            }
        } else {
            if (!z) {
                SemWifiManager semWifiManager2 = hotspotControllerImpl.mSemWifiManager;
                if (semWifiManager2 != null) {
                    semWifiManager2.setWifiApEnabled((SoftApConfiguration) null, false);
                    return;
                }
                return;
            }
            hotspotControllerImpl.mWaitingForTerminalState = true;
            if (HotspotControllerImpl.DEBUG) {
                Log.d("HotspotController", "Starting tethering");
            }
            Log.d("HotspotController", "Starting SemWifiManager tethering");
            SemWifiManager semWifiManager3 = hotspotControllerImpl.mSemWifiManager;
            if (semWifiManager3 != null) {
                semWifiManager3.setWifiApEnabled((SoftApConfiguration) null, true);
            }
        }
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl
    public final boolean shouldAnnouncementBeDelayed() {
        return this.mStateBeforeClick.value == ((QSTile.BooleanState) this.mState).value;
    }

    public final void showDataSaverToast() {
        String string;
        SemCscFeature semCscFeature = SemCscFeature.getInstance();
        boolean z = (semCscFeature == null || (string = semCscFeature.getString("CscFeature_SmartManager_ConfigSubFeatures")) == null || !string.contains("trafficmanager")) ? false : true;
        Context context = this.mContext;
        if (!z) {
            SysUIToast.makeText(android.R.string.permlab_setAlarm, context, 0).show();
            return;
        }
        SystemUIDialog systemUIDialog = this.mDataSaverDialog;
        if (systemUIDialog == null || !systemUIDialog.isShowing()) {
            SystemUIDialog systemUIDialog2 = new SystemUIDialog(context, 2132018528);
            this.mDataSaverDialog = systemUIDialog2;
            systemUIDialog2.setTitle(17043338);
            this.mDataSaverDialog.setMessage(17043335);
            this.mDataSaverDialog.setNegativeButton(17043337, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.HotspotTile.3
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent();
                    intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.SubSettings");
                    intent.putExtra(":settings:show_fragment", "com.samsung.android.settings.datausage.trafficmanager.ui.DataSaverSummaryCHN");
                    intent.addFlags(268468224);
                    HotspotTile hotspotTile = HotspotTile.this;
                    int i2 = HotspotTile.$r8$clinit;
                    hotspotTile.mContext.startActivity(intent);
                }
            });
            this.mDataSaverDialog.setPositiveButton(17043336, null);
            this.mDataSaverDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.tiles.HotspotTile.4
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
        Log.d("HotspotTile", "handleSecondaryClick");
        if (isAirplaneModeEnabled() || isBlockedByOthers()) {
            return;
        }
        KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) this.mKnoxStateMonitor;
        if (!knoxStateMonitorImpl.isWifiHotspotTileBlocked()) {
            EdmMonitor edmMonitor = knoxStateMonitorImpl.mEdmMonitor;
            if (!(edmMonitor != null && edmMonitor.mUserManager.hasUserRestriction("no_config_tethering", UserHandle.of(KeyguardUpdateMonitor.getCurrentUser()))) && !isBlockedByEASPolicy() && (DeviceState.isDataAllowed(this.mContext) || this.mSemWifiManager.isWifiSharingEnabled())) {
                if (isWifiApBlocked()) {
                    showItPolicyToast();
                    return;
                }
                if (isSatModeEnabled()) {
                    return;
                }
                if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
                    KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
                    if (keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && this.mSettingsHelper.isLockFunctionsEnabled()) {
                        this.mActivityStarter.postQSRunnableDismissingKeyguard(new HotspotTile$$ExternalSyntheticLambda0(this, 0));
                        return;
                    }
                }
                if (z) {
                    ((CentralSurfacesImpl) ((CentralSurfaces) Dependency.get(CentralSurfaces.class))).openQSPanelWithDetail("Hotspot");
                    return;
                } else {
                    showDetail(true);
                    return;
                }
            }
        }
        showItPolicyToast();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class HotSpotDetailAdapter implements DetailAdapter, QSDetailItems.Callback {
        public static final /* synthetic */ int $r8$clinit = 0;
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

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
            StringBuilder sb = new StringBuilder("createDetailView convertView=");
            sb.append(view != null);
            sb.append(" mState.value ");
            int i = HotspotTile.$r8$clinit;
            HotspotTile hotspotTile = HotspotTile.this;
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, ((QSTile.BooleanState) hotspotTile.mState).value, "HotspotTile");
            WifiManager wifiManager = this.mWifiManager;
            Context context2 = hotspotTile.mContext;
            if (wifiManager == null) {
                this.mWifiManager = (WifiManager) context2.getSystemService(ImsProfile.PDN_WIFI);
            }
            View inflate = LayoutInflater.from(context2).inflate(R.layout.qs_detail_hotspot, viewGroup, false);
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
                this.mWifiManager = (WifiManager) context2.getSystemService(ImsProfile.PDN_WIFI);
            }
            char c = 3;
            if (hotspotTile.getSemWifiManager() == null) {
                Log.e("HotspotTile", " updateHotSpotApInfo SemWifiManager is null");
            } else {
                SoftApConfiguration softApConfiguration = hotspotTile.mSemWifiManager.getSoftApConfiguration();
                String passphrase = softApConfiguration.getPassphrase();
                if (QSTileImpl.DEBUG) {
                    Log.d("HotspotTile", "mobileAp Name = " + softApConfiguration.getSsid());
                }
                this.mMobileApName.setText(softApConfiguration.getSsid());
                if (passphrase == null || passphrase.length() == 0) {
                    this.mPassword.setText(R.string.assistance_app_setting_item_none);
                } else if (passphrase.equals("\tUSER#DEFINED#PWD#\n")) {
                    String string = Settings.Secure.getString(context2.getContentResolver(), "wifi_ap_random_password");
                    if (string == null || string.equals("")) {
                        StringBuilder sb2 = new StringBuilder();
                        Random random = new Random(SystemClock.uptimeMillis());
                        StringBuffer stringBuffer = new StringBuffer();
                        for (int i2 = 0; i2 < 4; i2++) {
                            stringBuffer.append("abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26)));
                        }
                        sb2.append(stringBuffer.toString());
                        Random random2 = new Random(SystemClock.uptimeMillis() + 1);
                        int i3 = 10;
                        for (int i4 = 1; i4 < 3; i4++) {
                            i3 *= 10;
                        }
                        sb2.append(String.format(String.format(Locale.US, "%%0%dd", 3), Integer.valueOf(random2.nextInt(i3 - 1))));
                        Random random3 = new Random(SystemClock.uptimeMillis());
                        StringBuffer stringBuffer2 = new StringBuffer();
                        stringBuffer2.append("!@#$/^&*()".charAt(random3.nextInt(10)));
                        sb2.append(stringBuffer2.toString());
                        String sb3 = sb2.toString();
                        Settings.Secure.putString(context2.getContentResolver(), "wifi_ap_random_password", sb3);
                        this.mPassword.setText(sb3);
                    } else {
                        this.mPassword.setText(Settings.Secure.getString(context2.getContentResolver(), "wifi_ap_random_password"));
                    }
                } else {
                    this.mPassword.setText(passphrase);
                }
                this.mApLayout.setContentDescription(this.mMobileApName.getText().toString());
                this.mPassWord = ", " + this.mPassword.getText().toString();
                this.mPassWordLayout.setContentDescription(context2.getResources().getString(R.string.mobile_hotspot_detail_password) + this.mPassWord);
            }
            int i5 = HotspotTile.$r8$clinit;
            if (hotspotTile.getSemWifiManager() == null) {
                Log.e("HotspotTile", " updateConnectedDeviceList SemWifiManager is null");
            } else {
                List wifiApStaListDetail = hotspotTile.mSemWifiManager.getWifiApStaListDetail();
                this.mConnectedDevices = wifiApStaListDetail;
                if (wifiApStaListDetail != null) {
                    this.deviceCount = wifiApStaListDetail.size();
                }
                Log.d("HotspotTile", "updateItems");
                if (this.mItems != null) {
                    int i6 = this.deviceCount;
                    QSDetailItems.Item[] itemArr = new QSDetailItems.Item[i6];
                    if (i6 != 0) {
                        int i7 = 0;
                        while (i7 < this.deviceCount) {
                            String[] split = ((String) this.mConnectedDevices.get(i7)).split("\n");
                            String str = split[2];
                            String str2 = split[c];
                            int i8 = HotspotTile.$r8$clinit;
                            Long valueOf = Long.valueOf(Long.parseLong(str2));
                            Context context3 = hotspotTile.mContext;
                            String format = DateFormat.getTimeFormat(context3).format(new Date(valueOf.longValue()));
                            StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(DateFormat.format(DateFormat.getBestDateTimePattern(Locale.getDefault(), "MMM dd"), new Date(valueOf.longValue())).toString());
                            m18m.append(context3.getString(R.string.comma));
                            m18m.append(" ");
                            m18m.append(format);
                            String sb4 = m18m.toString();
                            QSDetailItems.Item item = new QSDetailItems.Item();
                            if ("(null)".equals(str)) {
                                str = context3.getString(R.string.mobile_hotspot_detail_connected_device);
                            }
                            item.iconVisibility = false;
                            item.itemPaddingAboveBelow = context3.getResources().getDimensionPixelSize(R.dimen.wifi_ap_item_above_below_padding);
                            item.line1textSize = context3.getResources().getDimensionPixelSize(R.dimen.wifi_ap_item_title_text_size);
                            item.line2textSize = context3.getResources().getDimensionPixelSize(R.dimen.wifi_ap_item_summary_text_size);
                            item.line1 = str;
                            item.line2 = sb4;
                            item.isClickable = false;
                            itemArr[i7] = item;
                            i7++;
                            c = 3;
                        }
                    }
                    this.mItems.setItems(itemArr);
                }
                Log.d("HotspotTile", " updateConnectedDeviceList mConnectedDevices = " + this.mConnectedDevices);
                boolean z = ((QSTile.BooleanState) hotspotTile.mState).value && this.mConnectedDevices != null;
                View view2 = this.mConnectedListContainer;
                if (view2 != null) {
                    view2.setVisibility(z ? 0 : 8);
                }
            }
            return inflate;
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final int getMetricsCategory() {
            return VolteConstants.ErrorCode.CALL_CANCEL_TRANSFER_SUCCESS;
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final Intent getSettingsIntent() {
            int i = HotspotTile.$r8$clinit;
            if (HotspotTile.this.isDataSaverEnabled()) {
                return null;
            }
            return new Intent("android.settings.WIFI_AP_SETTINGS");
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final CharSequence getTitle() {
            int i = HotspotTile.$r8$clinit;
            return HotspotTile.this.mContext.getString(CvOperator.getHotspotStringID(R.string.mobile_hotspot_detail_title));
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final boolean getToggleEnabled() {
            StringBuilder sb = new StringBuilder(" getToggleEnabled - ");
            int i = HotspotTile.$r8$clinit;
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, ((QSTile.BooleanState) HotspotTile.this.mState).isTransient, "HotspotTile");
            return !((QSTile.BooleanState) r3.mState).isTransient;
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final Boolean getToggleState() {
            int i = HotspotTile.$r8$clinit;
            return Boolean.valueOf(((QSTile.BooleanState) HotspotTile.this.mState).value);
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final void setToggleState(boolean z) {
            int i = HotspotTile.$r8$clinit;
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
            boolean isKeyguardVisible = keyguardUpdateMonitor.isKeyguardVisible();
            SettingsHelper settingsHelper = hotspotTile.mSettingsHelper;
            if (isKeyguardVisible && keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && settingsHelper.isLockFunctionsEnabled()) {
                hotspotTile.mActivityStarter.postQSRunnableDismissingKeyguard(new HotspotTile$$ExternalSyntheticLambda0(this, 1));
                hotspotTile.fireToggleStateChanged(getToggleState().booleanValue());
                return;
            }
            Log.d("HotspotTile", "isShowing() = " + keyguardUpdateMonitor.isKeyguardVisible() + ", isSecure() = " + keyguardUpdateMonitor.isSecure() + ", canSkipBouncer() = " + keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) + ", isLockFunctionsEnabled() = " + settingsHelper.isLockFunctionsEnabled());
            hotspotTile.refreshState(z ? SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING : null);
            hotspotTile.setHotspotEnabled(z);
            boolean z2 = ((QSTile.BooleanState) hotspotTile.mState).value && this.mConnectedDevices != null;
            View view = this.mConnectedListContainer;
            if (view != null) {
                view.setVisibility(z2 ? 0 : 8);
            }
        }

        @Override // com.android.systemui.qs.QSDetailItems.Callback
        public final void onDetailItemClick(QSDetailItems.Item item) {
        }
    }
}
