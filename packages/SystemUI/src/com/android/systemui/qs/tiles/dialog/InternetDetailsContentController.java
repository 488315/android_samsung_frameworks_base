package com.android.systemui.qs.tiles.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.sharedconnectivity.app.HotspotNetwork;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.telephony.CarrierConfigManager;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.PhoneNumberUtils;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;
import android.text.BidiFormatter;
import android.text.TextDirectionHeuristics;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.Utils;
import com.android.settingslib.graph.SignalDrawable;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.settingslib.wifi.WifiUtils;
import com.android.settingslib.wifi.dpp.WifiDppIntentHelper;
import com.android.systemui.R;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.tiles.dialog.InternetDetailsContentController;
import com.android.systemui.statusbar.connectivity.AccessPointController;
import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.toast.SystemUIToast;
import com.android.systemui.toast.ToastFactory;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.wifitrackerlib.HotspotNetworkEntry;
import com.android.wifitrackerlib.MergedCarrierEntry;
import com.android.wifitrackerlib.WifiEntry;
import com.samsung.android.knox.net.wifi.WifiPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes2.dex */
public class InternetDetailsContentController implements AccessPointController.AccessPointCallback {
    static final long SHORT_DURATION_TIMEOUT = 4000;
    static final float TOAST_PARAMS_HORIZONTAL_WEIGHT = 1.0f;
    static final float TOAST_PARAMS_VERTICAL_WEIGHT = 1.0f;
    public final AccessPointController mAccessPointController;
    protected ActivityStarter mActivityStarter;
    public final BroadcastDispatcher mBroadcastDispatcher;
    InternetDialogCallback mCallback;
    protected boolean mCanConfigWifi;
    public final CarrierConfigTracker mCarrierConfigTracker;
    protected boolean mCarrierNetworkChangeMode;
    protected ConnectedWifiInternetMonitor mConnectedWifiInternetMonitor;
    public final IntentFilter mConnectionStateFilter;
    public final ConnectivityManager mConnectivityManager;
    public final DataConnectivityListener mConnectivityManagerNetworkCallback;
    public final Context mContext;
    public final DialogTransitionAnimator mDialogTransitionAnimator;
    public final Executor mExecutor;
    public final FeatureFlags mFeatureFlags;
    public final GlobalSettings mGlobalSettings;
    public final Handler mHandler;
    public boolean mHasActiveSubIdOnDds;
    public boolean mHasWifiEntries;
    protected KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LocationController mLocationController;
    protected SubscriptionManager.OnSubscriptionsChangedListener mOnSubscriptionsChangedListener;
    public final SignalDrawable mSecondarySignalDrawable;
    public final SignalDrawable mSignalDrawable;
    public final SubscriptionManager mSubscriptionManager;
    public TelephonyManager mTelephonyManager;
    public final ToastFactory mToastFactory;
    protected WifiUtils.InternetIconInjector mWifiIconInjector;
    public final WifiManager mWifiManager;
    public final WifiStateWorker mWifiStateWorker;
    public final WindowManager mWindowManager;
    public final Handler mWorkerHandler;
    public static final Drawable EMPTY_DRAWABLE = new ColorDrawable(0);
    public static final int SUBTITLE_TEXT_WIFI_IS_OFF = R.string.wifi_is_off;
    public static final int SUBTITLE_TEXT_TAP_A_NETWORK_TO_CONNECT = R.string.tap_a_network_to_connect;
    public static final int SUBTITLE_TEXT_UNLOCK_TO_VIEW_NETWORKS = R.string.unlock_to_view_networks;
    public static final int SUBTITLE_TEXT_SEARCHING_FOR_NETWORKS = R.string.wifi_empty_list_wifi_on;
    public static final int SUBTITLE_TEXT_NON_CARRIER_NETWORK_UNAVAILABLE = R.string.non_carrier_network_unavailable;
    public static final int SUBTITLE_TEXT_ALL_CARRIER_NETWORK_UNAVAILABLE = R.string.all_network_unavailable;
    public static final boolean DEBUG = Log.isLoggable("InternetDetailsContentController", 3);
    public static final TelephonyDisplayInfo DEFAULT_TELEPHONY_DISPLAY_INFO = new TelephonyDisplayInfo(0, 0, false, false, false);
    final Map<Integer, TelephonyDisplayInfo> mSubIdTelephonyDisplayInfoMap = new HashMap();
    final Map<Integer, TelephonyManager> mSubIdTelephonyManagerMap = new HashMap();
    final Map<Integer, TelephonyCallback> mSubIdTelephonyCallbackMap = new HashMap();
    public MobileMappings.Config mConfig = null;
    public int mDefaultDataSubId = -1;
    public boolean mIsMobileDataEnabled = false;
    Map<Integer, ServiceState> mSubIdServiceState = new HashMap();
    protected boolean mHasEthernet = false;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onRefreshCarrierInfo(Intent intent) {
            InternetDialogCallback internetDialogCallback = InternetDetailsContentController.this.mCallback;
            if (internetDialogCallback != null) {
                internetDialogCallback.onRefreshCarrierInfo();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onSimStateChanged(int i, int i2, int i3) {
            InternetDialogCallback internetDialogCallback = InternetDetailsContentController.this.mCallback;
            if (internetDialogCallback != null) {
                internetDialogCallback.onSimStateChanged();
            }
        }
    };
    public final AnonymousClass2 mConnectionStateReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.2
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!"android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action)) {
                if ("android.net.wifi.supplicant.CONNECTION_CHANGE".equals(action)) {
                    InternetDetailsContentController.m2926$$Nest$mupdateListener(InternetDetailsContentController.this);
                }
            } else {
                if (InternetDetailsContentController.DEBUG) {
                    Log.d("InternetDetailsContentController", "ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
                }
                InternetDetailsContentController.this.mConfig = MobileMappings.Config.readConfig(context);
                InternetDetailsContentController.this.refreshHasActiveSubIdOnDds();
                InternetDetailsContentController.m2926$$Nest$mupdateListener(InternetDetailsContentController.this);
            }
        }
    };

    /* renamed from: com.android.systemui.qs.tiles.dialog.InternetDetailsContentController$1DisplayInfo, reason: invalid class name */
    public class C1DisplayInfo {
        public final CharSequence originalName;
        public final SubscriptionInfo subscriptionInfo;
        public CharSequence uniqueName;

        public C1DisplayInfo(InternetDetailsContentController internetDetailsContentController, SubscriptionInfo subscriptionInfo, CharSequence charSequence) {
            this.subscriptionInfo = subscriptionInfo;
            this.originalName = charSequence;
        }
    }

    public class ConnectedWifiInternetMonitor implements WifiEntry.WifiEntryCallback {
        public WifiEntry mWifiEntry;

        public ConnectedWifiInternetMonitor() {
        }

        @Override // com.android.wifitrackerlib.WifiEntry.WifiEntryCallback
        public final void onUpdated() {
            WifiEntry wifiEntry = this.mWifiEntry;
            if (wifiEntry == null) {
                return;
            }
            if (wifiEntry.getConnectedState() != 2) {
                WifiEntry wifiEntry2 = this.mWifiEntry;
                if (wifiEntry2 == null) {
                    return;
                }
                synchronized (wifiEntry2) {
                    wifiEntry2.mListener = null;
                }
                this.mWifiEntry = null;
                return;
            }
            if (wifiEntry.isDefaultNetwork() && wifiEntry.hasInternetAccess()) {
                WifiEntry wifiEntry3 = this.mWifiEntry;
                if (wifiEntry3 != null) {
                    synchronized (wifiEntry3) {
                        wifiEntry3.mListener = null;
                    }
                    this.mWifiEntry = null;
                }
                Drawable drawable = InternetDetailsContentController.EMPTY_DRAWABLE;
                InternetDetailsContentController.this.scanWifiAccessPoints();
            }
        }
    }

    public class DataConnectivityListener extends ConnectivityManager.NetworkCallback {
        public /* synthetic */ DataConnectivityListener(InternetDetailsContentController internetDetailsContentController, int i) {
            this();
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            InternetDetailsContentController.this.mHasEthernet = networkCapabilities.hasTransport(3);
            InternetDetailsContentController internetDetailsContentController = InternetDetailsContentController.this;
            if (internetDetailsContentController.mCanConfigWifi && (internetDetailsContentController.mHasEthernet || networkCapabilities.hasTransport(1))) {
                InternetDetailsContentController.this.scanWifiAccessPoints();
            }
            InternetDialogCallback internetDialogCallback = InternetDetailsContentController.this.mCallback;
            if (internetDialogCallback != null) {
                internetDialogCallback.onCapabilitiesChanged();
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            InternetDetailsContentController internetDetailsContentController = InternetDetailsContentController.this;
            internetDetailsContentController.mHasEthernet = false;
            InternetDialogCallback internetDialogCallback = internetDetailsContentController.mCallback;
            if (internetDialogCallback != null) {
                internetDialogCallback.onLost();
            }
        }

        private DataConnectivityListener() {
        }
    }

    public interface InternetDialogCallback {
        void dismissDialog();

        void onAccessPointsChanged(List list, WifiEntry wifiEntry, boolean z);

        void onCapabilitiesChanged();

        void onCarrierNetworkChange();

        void onDataConnectionStateChanged();

        void onDisplayInfoChanged();

        void onLost();

        void onRefreshCarrierInfo();

        void onServiceStateChanged();

        void onSignalStrengthsChanged();

        void onSimStateChanged();

        void onSubscriptionsChanged(int i);

        void onUserMobileDataStateChanged();

        void onWifiScan(boolean z);
    }

    public class InternetOnSubscriptionChangedListener extends SubscriptionManager.OnSubscriptionsChangedListener {
        public InternetOnSubscriptionChangedListener() {
        }

        @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
        public final void onSubscriptionsChanged() {
            InternetDetailsContentController internetDetailsContentController = InternetDetailsContentController.this;
            Drawable drawable = InternetDetailsContentController.EMPTY_DRAWABLE;
            internetDetailsContentController.refreshHasActiveSubIdOnDds();
            InternetDetailsContentController.m2926$$Nest$mupdateListener(InternetDetailsContentController.this);
        }
    }

    public class InternetTelephonyCallback extends TelephonyCallback implements TelephonyCallback.DataEnabledListener, TelephonyCallback.DataConnectionStateListener, TelephonyCallback.DisplayInfoListener, TelephonyCallback.ServiceStateListener, TelephonyCallback.SignalStrengthsListener, TelephonyCallback.UserMobileDataStateListener, TelephonyCallback.CarrierNetworkListener {
        public final int mSubId;

        public /* synthetic */ InternetTelephonyCallback(InternetDetailsContentController internetDetailsContentController, int i, int i2) {
            this(i);
        }

        @Override // android.telephony.TelephonyCallback.CarrierNetworkListener
        public final void onCarrierNetworkChange(boolean z) {
            InternetDetailsContentController internetDetailsContentController = InternetDetailsContentController.this;
            internetDetailsContentController.mCarrierNetworkChangeMode = z;
            InternetDialogCallback internetDialogCallback = internetDetailsContentController.mCallback;
            if (internetDialogCallback != null) {
                internetDialogCallback.onCarrierNetworkChange();
            }
        }

        @Override // android.telephony.TelephonyCallback.DataConnectionStateListener
        public final void onDataConnectionStateChanged(int i, int i2) {
            InternetDialogCallback internetDialogCallback = InternetDetailsContentController.this.mCallback;
            if (internetDialogCallback != null) {
                internetDialogCallback.onDataConnectionStateChanged();
            }
        }

        public final void onDataEnabledChanged(boolean z, int i) {
            int i2 = this.mSubId;
            InternetDetailsContentController internetDetailsContentController = InternetDetailsContentController.this;
            if (i2 == internetDetailsContentController.mDefaultDataSubId) {
                internetDetailsContentController.mIsMobileDataEnabled = z;
            }
        }

        @Override // android.telephony.TelephonyCallback.DisplayInfoListener
        public final void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) {
            InternetDetailsContentController.this.mSubIdTelephonyDisplayInfoMap.put(Integer.valueOf(this.mSubId), telephonyDisplayInfo);
            InternetDialogCallback internetDialogCallback = InternetDetailsContentController.this.mCallback;
            if (internetDialogCallback != null) {
                internetDialogCallback.onDisplayInfoChanged();
            }
        }

        @Override // android.telephony.TelephonyCallback.ServiceStateListener
        public final void onServiceStateChanged(ServiceState serviceState) {
            InternetDialogCallback internetDialogCallback = InternetDetailsContentController.this.mCallback;
            if (internetDialogCallback != null) {
                internetDialogCallback.onServiceStateChanged();
            }
            InternetDetailsContentController.this.mSubIdServiceState.put(Integer.valueOf(this.mSubId), serviceState);
        }

        @Override // android.telephony.TelephonyCallback.SignalStrengthsListener
        public final void onSignalStrengthsChanged(SignalStrength signalStrength) {
            InternetDialogCallback internetDialogCallback = InternetDetailsContentController.this.mCallback;
            if (internetDialogCallback != null) {
                internetDialogCallback.onSignalStrengthsChanged();
            }
        }

        @Override // android.telephony.TelephonyCallback.UserMobileDataStateListener
        public final void onUserMobileDataStateChanged(boolean z) {
            InternetDialogCallback internetDialogCallback = InternetDetailsContentController.this.mCallback;
            if (internetDialogCallback != null) {
                internetDialogCallback.onUserMobileDataStateChanged();
            }
        }

        private InternetTelephonyCallback(int i) {
            this.mSubId = i;
        }
    }

    public class WifiEntryConnectCallback implements WifiEntry.ConnectCallback {
        public final ActivityStarter mActivityStarter;
        public final InternetDetailsContentController mInternetDetailsContentController;
        public final WifiEntry mWifiEntry;

        public WifiEntryConnectCallback(ActivityStarter activityStarter, WifiEntry wifiEntry, InternetDetailsContentController internetDetailsContentController) {
            this.mActivityStarter = activityStarter;
            this.mWifiEntry = wifiEntry;
            this.mInternetDetailsContentController = internetDetailsContentController;
        }

        @Override // com.android.wifitrackerlib.WifiEntry.ConnectCallback
        public final void onConnectResult(int i) throws Resources.NotFoundException {
            boolean z = InternetDetailsContentController.DEBUG;
            if (z) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onConnectResult ", "InternetDetailsContentController");
            }
            if (i != 1) {
                if (i == 2) {
                    this.mInternetDetailsContentController.makeOverlayToast(R.string.wifi_failed_connect_message);
                    return;
                } else {
                    if (z) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "connect failure reason=", "InternetDetailsContentController");
                        return;
                    }
                    return;
                }
            }
            String key = this.mWifiEntry.getKey();
            WifiUtils.Companion.getClass();
            Intent intent = new Intent(WifiUtils.ACTION_WIFI_DIALOG);
            intent.putExtra(WifiUtils.EXTRA_CHOSEN_WIFI_ENTRY_KEY, key);
            intent.putExtra(WifiUtils.EXTRA_CONNECT_FOR_CALLER, true);
            intent.addFlags(268435456);
            this.mActivityStarter.startActivity(intent, false);
        }
    }

    /* renamed from: -$$Nest$mupdateListener, reason: not valid java name */
    public static void m2926$$Nest$mupdateListener(InternetDetailsContentController internetDetailsContentController) {
        internetDetailsContentController.getClass();
        int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
        int i = internetDetailsContentController.mDefaultDataSubId;
        int defaultDataSubscriptionId2 = SubscriptionManager.getDefaultDataSubscriptionId();
        boolean z = DEBUG;
        if (i == defaultDataSubscriptionId2) {
            if (z) {
                Log.d("InternetDetailsContentController", "DDS: no change");
                return;
            }
            return;
        }
        if (z) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(defaultDataSubscriptionId, "DDS: defaultDataSubId:", "InternetDetailsContentController");
        }
        if (SubscriptionManager.isUsableSubscriptionId(defaultDataSubscriptionId)) {
            TelephonyCallback telephonyCallback = internetDetailsContentController.mSubIdTelephonyCallbackMap.get(Integer.valueOf(internetDetailsContentController.mDefaultDataSubId));
            if (telephonyCallback != null) {
                internetDetailsContentController.mTelephonyManager.unregisterTelephonyCallback(telephonyCallback);
            } else if (z) {
                Log.e("InternetDetailsContentController", "Unexpected null telephony call back for Sub " + internetDetailsContentController.mDefaultDataSubId);
            }
            internetDetailsContentController.mSubIdTelephonyCallbackMap.remove(Integer.valueOf(internetDetailsContentController.mDefaultDataSubId));
            internetDetailsContentController.mSubIdTelephonyDisplayInfoMap.remove(Integer.valueOf(internetDetailsContentController.mDefaultDataSubId));
            internetDetailsContentController.mSubIdTelephonyManagerMap.remove(Integer.valueOf(internetDetailsContentController.mDefaultDataSubId));
            internetDetailsContentController.mTelephonyManager = internetDetailsContentController.mTelephonyManager.createForSubscriptionId(defaultDataSubscriptionId);
            internetDetailsContentController.mSubIdTelephonyManagerMap.put(Integer.valueOf(defaultDataSubscriptionId), internetDetailsContentController.mTelephonyManager);
            internetDetailsContentController.registerInternetTelephonyCallback(internetDetailsContentController.mTelephonyManager, defaultDataSubscriptionId);
            internetDetailsContentController.mCallback.onSubscriptionsChanged(defaultDataSubscriptionId);
        }
        internetDetailsContentController.mDefaultDataSubId = defaultDataSubscriptionId;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.qs.tiles.dialog.InternetDetailsContentController$2] */
    public InternetDetailsContentController(Context context, UiEventLogger uiEventLogger, ActivityStarter activityStarter, AccessPointController accessPointController, SubscriptionManager subscriptionManager, TelephonyManager telephonyManager, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Executor executor, BroadcastDispatcher broadcastDispatcher, KeyguardUpdateMonitor keyguardUpdateMonitor, GlobalSettings globalSettings, KeyguardStateController keyguardStateController, WindowManager windowManager, ToastFactory toastFactory, Handler handler2, CarrierConfigTracker carrierConfigTracker, LocationController locationController, DialogTransitionAnimator dialogTransitionAnimator, WifiStateWorker wifiStateWorker, FeatureFlags featureFlags) {
        int i = 0;
        if (DEBUG) {
            Log.d("InternetDetailsContentController", "Init InternetDetailsContentController");
        }
        this.mHandler = handler;
        this.mWorkerHandler = handler2;
        this.mExecutor = executor;
        this.mContext = context;
        this.mGlobalSettings = globalSettings;
        this.mWifiManager = wifiManager;
        this.mTelephonyManager = telephonyManager;
        this.mConnectivityManager = connectivityManager;
        this.mSubscriptionManager = subscriptionManager;
        this.mCarrierConfigTracker = carrierConfigTracker;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        IntentFilter intentFilter = new IntentFilter();
        this.mConnectionStateFilter = intentFilter;
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        intentFilter.addAction("android.net.wifi.supplicant.CONNECTION_CHANGE");
        this.mActivityStarter = activityStarter;
        this.mAccessPointController = accessPointController;
        this.mWifiIconInjector = new WifiUtils.InternetIconInjector(context);
        this.mConnectivityManagerNetworkCallback = new DataConnectivityListener(this, i);
        this.mWindowManager = windowManager;
        this.mToastFactory = toastFactory;
        this.mSignalDrawable = new SignalDrawable(context);
        this.mSecondarySignalDrawable = new SignalDrawable(context);
        this.mLocationController = locationController;
        this.mDialogTransitionAnimator = dialogTransitionAnimator;
        this.mConnectedWifiInternetMonitor = new ConnectedWifiInternetMonitor();
        this.mWifiStateWorker = wifiStateWorker;
        this.mFeatureFlags = featureFlags;
    }

    public final boolean activeNetworkIsCellular() {
        ConnectivityManager connectivityManager = this.mConnectivityManager;
        if (connectivityManager == null) {
            if (DEBUG) {
                Log.d("InternetDetailsContentController", "ConnectivityManager is null, can not check active network.");
            }
            return false;
        }
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null) {
            Log.d("InternetDetailsContentController", "getActiveNetwork is null.");
            return false;
        }
        NetworkCapabilities networkCapabilities = this.mConnectivityManager.getNetworkCapabilities(activeNetwork);
        if (networkCapabilities == null) {
            return false;
        }
        return networkCapabilities.hasTransport(0);
    }

    public final int getActiveAutoSwitchNonDdsSubId() {
        int activeDataSubscriptionId;
        SubscriptionInfo activeSubscriptionInfo;
        if (!((FeatureFlagsClassicRelease) this.mFeatureFlags).isEnabled(Flags.QS_SECONDARY_DATA_SUB_INFO) || (activeDataSubscriptionId = SubscriptionManager.getActiveDataSubscriptionId()) == -1 || this.mDefaultDataSubId == activeDataSubscriptionId || (activeSubscriptionInfo = this.mSubscriptionManager.getActiveSubscriptionInfo(SubscriptionManager.getActiveDataSubscriptionId())) == null || activeSubscriptionInfo.getSubscriptionId() == this.mDefaultDataSubId || activeSubscriptionInfo.isOpportunistic()) {
            return -1;
        }
        int subscriptionId = activeSubscriptionInfo.getSubscriptionId();
        if (this.mSubIdTelephonyManagerMap.get(Integer.valueOf(subscriptionId)) == null) {
            TelephonyManager telephonyManagerCreateForSubscriptionId = this.mTelephonyManager.createForSubscriptionId(subscriptionId);
            registerInternetTelephonyCallback(telephonyManagerCreateForSubscriptionId, subscriptionId);
            this.mSubIdTelephonyManagerMap.put(Integer.valueOf(subscriptionId), telephonyManagerCreateForSubscriptionId);
        }
        return subscriptionId;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0054  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Intent getConfiguratorQrCodeGeneratorIntentOrNull(WifiEntry wifiEntry) {
        WifiConfiguration wifiConfiguration;
        String str;
        String str2;
        if (!((FeatureFlagsClassicRelease) this.mFeatureFlags).isEnabled(Flags.SHARE_WIFI_QS_BUTTON) || wifiEntry == null || this.mWifiManager == null || !wifiEntry.canShare() || (wifiConfiguration = wifiEntry.getWifiConfiguration()) == null) {
            return null;
        }
        Intent intent = new Intent();
        intent.setAction("android.settings.WIFI_DPP_CONFIGURATOR_AUTH_QR_CODE_GENERATOR");
        intent.addFlags(335544320);
        WifiManager wifiManager = this.mWifiManager;
        String strRemoveFirstAndLastDoubleQuotes = WifiDppIntentHelper.removeFirstAndLastDoubleQuotes(wifiConfiguration.SSID);
        if (wifiConfiguration.allowedKeyManagement.get(8)) {
            str = WifiPolicy.SECURITY_TYPE_SAE;
        } else if (!wifiConfiguration.allowedKeyManagement.get(9)) {
            str = (wifiConfiguration.allowedKeyManagement.get(1) || wifiConfiguration.allowedKeyManagement.get(4)) ? "WPA" : wifiConfiguration.wepKeys[0] == null ? "nopass" : "WEP";
        }
        Iterator it = wifiManager.getPrivilegedConfiguredNetworks().iterator();
        while (true) {
            if (!it.hasNext()) {
                str2 = wifiConfiguration.preSharedKey;
                break;
            }
            WifiConfiguration wifiConfiguration2 = (WifiConfiguration) it.next();
            if (wifiConfiguration2.networkId == wifiConfiguration.networkId) {
                str2 = (wifiConfiguration.allowedKeyManagement.get(0) && wifiConfiguration.allowedAuthAlgorithms.get(1)) ? wifiConfiguration2.wepKeys[wifiConfiguration2.wepTxKeyIndex] : wifiConfiguration2.preSharedKey;
            }
        }
        String strRemoveFirstAndLastDoubleQuotes2 = WifiDppIntentHelper.removeFirstAndLastDoubleQuotes(str2);
        if (!TextUtils.isEmpty(strRemoveFirstAndLastDoubleQuotes)) {
            intent.putExtra("ssid", strRemoveFirstAndLastDoubleQuotes);
        }
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra("security", str);
        }
        if (!TextUtils.isEmpty(strRemoveFirstAndLastDoubleQuotes2)) {
            intent.putExtra("preSharedKey", strRemoveFirstAndLastDoubleQuotes2);
        }
        intent.putExtra("hiddenSsid", wifiConfiguration.hiddenSSID);
        return intent;
    }

    public final CharSequence getDialogTitleText() {
        return isAirplaneModeEnabled() ? this.mContext.getText(R.string.airplane_mode) : this.mContext.getText(R.string.quick_settings_internet_label);
    }

    public final String getMobileNetworkSummary(int i) throws Resources.NotFoundException {
        Context context = this.mContext;
        MobileMappings.Config config = this.mConfig;
        TelephonyDisplayInfo orDefault = this.mSubIdTelephonyDisplayInfoMap.getOrDefault(Integer.valueOf(i), DEFAULT_TELEPHONY_DISPLAY_INFO);
        String string = orDefault.getOverrideNetworkType() == 0 ? Integer.toString(orDefault.getNetworkType()) : MobileMappings.toDisplayIconKey(orDefault.getOverrideNetworkType());
        MobileMappings.mapIconSets(config);
        String string2 = "";
        if (((HashMap) MobileMappings.mapIconSets(config)).get(string) != null) {
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = (SignalIcon$MobileIconGroup) ((HashMap) MobileMappings.mapIconSets(config)).get(string);
            Objects.requireNonNull(signalIcon$MobileIconGroup);
            int i2 = isCarrierNetworkActive() ? TelephonyIcons.CARRIER_MERGED_WIFI.dataContentDescription : this.mCarrierNetworkChangeMode ? TelephonyIcons.CARRIER_NETWORK_CHANGE.dataContentDescription : signalIcon$MobileIconGroup.dataContentDescription;
            if (i2 != 0) {
                string2 = SubscriptionManager.getResourcesForSubId(context, i).getString(i2);
            }
        } else if (DEBUG) {
            Log.d("InternetDetailsContentController", "The description of network type is empty.");
        }
        Context context2 = this.mContext;
        if (!this.mIsMobileDataEnabled) {
            return context2.getString(R.string.mobile_data_off_summary);
        }
        boolean z = i == this.mDefaultDataSubId;
        boolean z2 = getActiveAutoSwitchNonDdsSubId() != -1;
        if (activeNetworkIsCellular() || isCarrierNetworkActive()) {
            return context2.getString(R.string.preference_summary_default_combination, context2.getString(z ? z2 ? R.string.mobile_data_poor_connection : R.string.mobile_data_connection_active : R.string.mobile_data_temp_connection_active), string2);
        }
        return !isDataStateInService(i) ? context2.getString(R.string.mobile_data_no_connection) : string2;
    }

    public final CharSequence getMobileNetworkTitle(int i) {
        final Context context = this.mContext;
        Supplier supplier = new Supplier() { // from class: com.android.systemui.qs.tiles.dialog.InternetDetailsContentController$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                InternetDetailsContentController internetDetailsContentController = this.f$0;
                return internetDetailsContentController.mKeyguardUpdateMonitor.getFilteredSubscriptionInfo().stream().filter(new InternetDetailsContentController$$ExternalSyntheticLambda10()).map(new InternetDetailsContentController$$ExternalSyntheticLambda5(internetDetailsContentController, 1));
            }
        };
        final HashSet hashSet = new HashSet();
        final int i2 = 0;
        Stream streamFilter = ((Stream) supplier.get()).filter(new Predicate() { // from class: com.android.systemui.qs.tiles.dialog.InternetDetailsContentController$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                int i3 = i2;
                Set set = hashSet;
                InternetDetailsContentController.C1DisplayInfo c1DisplayInfo = (InternetDetailsContentController.C1DisplayInfo) obj;
                switch (i3) {
                    case 0:
                        Drawable drawable = InternetDetailsContentController.EMPTY_DRAWABLE;
                        return !set.add(c1DisplayInfo.originalName);
                    default:
                        Drawable drawable2 = InternetDetailsContentController.EMPTY_DRAWABLE;
                        return !set.add(c1DisplayInfo.uniqueName);
                }
            }
        });
        final int i3 = 0;
        final Set set = (Set) streamFilter.map(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDetailsContentController$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                InternetDetailsContentController.C1DisplayInfo c1DisplayInfo = (InternetDetailsContentController.C1DisplayInfo) obj;
                switch (i3) {
                    case 0:
                        Drawable drawable = InternetDetailsContentController.EMPTY_DRAWABLE;
                        return c1DisplayInfo.originalName;
                    case 1:
                        Drawable drawable2 = InternetDetailsContentController.EMPTY_DRAWABLE;
                        return c1DisplayInfo.uniqueName;
                    case 2:
                        Drawable drawable3 = InternetDetailsContentController.EMPTY_DRAWABLE;
                        return Integer.valueOf(c1DisplayInfo.subscriptionInfo.getSubscriptionId());
                    default:
                        Drawable drawable4 = InternetDetailsContentController.EMPTY_DRAWABLE;
                        return c1DisplayInfo.uniqueName;
                }
            }
        }).collect(Collectors.toSet());
        hashSet.clear();
        final int i4 = 1;
        final int i5 = 1;
        Stream map = ((Stream) supplier.get()).map(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDetailsContentController$$ExternalSyntheticLambda9
            /* JADX WARN: Removed duplicated region for block: B:9:0x0033  */
            @Override // java.util.function.Function
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object apply(Object obj) {
                String number;
                Set set2 = set;
                Context context2 = context;
                InternetDetailsContentController.C1DisplayInfo c1DisplayInfo = (InternetDetailsContentController.C1DisplayInfo) obj;
                Drawable drawable = InternetDetailsContentController.EMPTY_DRAWABLE;
                if (!set2.contains(c1DisplayInfo.originalName)) {
                    c1DisplayInfo.uniqueName = c1DisplayInfo.originalName;
                    return c1DisplayInfo;
                }
                SubscriptionInfo subscriptionInfo = c1DisplayInfo.subscriptionInfo;
                if (subscriptionInfo != null) {
                    String line1Number = ((TelephonyManager) context2.getSystemService(TelephonyManager.class)).createForSubscriptionId(subscriptionInfo.getSubscriptionId()).getLine1Number();
                    number = !TextUtils.isEmpty(line1Number) ? PhoneNumberUtils.formatNumber(line1Number) : null;
                }
                String strUnicodeWrap = BidiFormatter.getInstance().unicodeWrap(number, TextDirectionHeuristics.LTR);
                if (strUnicodeWrap == null) {
                    strUnicodeWrap = "";
                } else if (strUnicodeWrap.length() > 4) {
                    strUnicodeWrap = strUnicodeWrap.substring(strUnicodeWrap.length() - 4);
                }
                if (TextUtils.isEmpty(strUnicodeWrap)) {
                    c1DisplayInfo.uniqueName = c1DisplayInfo.originalName;
                    return c1DisplayInfo;
                }
                c1DisplayInfo.uniqueName = ((Object) c1DisplayInfo.originalName) + " " + strUnicodeWrap;
                return c1DisplayInfo;
            }
        }).map(new InternetDetailsContentController$$ExternalSyntheticLambda5((Set) ((Stream) supplier.get()).map(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDetailsContentController$$ExternalSyntheticLambda9
            /* JADX WARN: Removed duplicated region for block: B:9:0x0033  */
            @Override // java.util.function.Function
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object apply(Object obj) {
                String number;
                Set set2 = set;
                Context context2 = context;
                InternetDetailsContentController.C1DisplayInfo c1DisplayInfo = (InternetDetailsContentController.C1DisplayInfo) obj;
                Drawable drawable = InternetDetailsContentController.EMPTY_DRAWABLE;
                if (!set2.contains(c1DisplayInfo.originalName)) {
                    c1DisplayInfo.uniqueName = c1DisplayInfo.originalName;
                    return c1DisplayInfo;
                }
                SubscriptionInfo subscriptionInfo = c1DisplayInfo.subscriptionInfo;
                if (subscriptionInfo != null) {
                    String line1Number = ((TelephonyManager) context2.getSystemService(TelephonyManager.class)).createForSubscriptionId(subscriptionInfo.getSubscriptionId()).getLine1Number();
                    number = !TextUtils.isEmpty(line1Number) ? PhoneNumberUtils.formatNumber(line1Number) : null;
                }
                String strUnicodeWrap = BidiFormatter.getInstance().unicodeWrap(number, TextDirectionHeuristics.LTR);
                if (strUnicodeWrap == null) {
                    strUnicodeWrap = "";
                } else if (strUnicodeWrap.length() > 4) {
                    strUnicodeWrap = strUnicodeWrap.substring(strUnicodeWrap.length() - 4);
                }
                if (TextUtils.isEmpty(strUnicodeWrap)) {
                    c1DisplayInfo.uniqueName = c1DisplayInfo.originalName;
                    return c1DisplayInfo;
                }
                c1DisplayInfo.uniqueName = ((Object) c1DisplayInfo.originalName) + " " + strUnicodeWrap;
                return c1DisplayInfo;
            }
        }).filter(new Predicate() { // from class: com.android.systemui.qs.tiles.dialog.InternetDetailsContentController$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                int i32 = i4;
                Set set2 = hashSet;
                InternetDetailsContentController.C1DisplayInfo c1DisplayInfo = (InternetDetailsContentController.C1DisplayInfo) obj;
                switch (i32) {
                    case 0:
                        Drawable drawable = InternetDetailsContentController.EMPTY_DRAWABLE;
                        return !set2.add(c1DisplayInfo.originalName);
                    default:
                        Drawable drawable2 = InternetDetailsContentController.EMPTY_DRAWABLE;
                        return !set2.add(c1DisplayInfo.uniqueName);
                }
            }
        }).map(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDetailsContentController$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                InternetDetailsContentController.C1DisplayInfo c1DisplayInfo = (InternetDetailsContentController.C1DisplayInfo) obj;
                switch (i5) {
                    case 0:
                        Drawable drawable = InternetDetailsContentController.EMPTY_DRAWABLE;
                        return c1DisplayInfo.originalName;
                    case 1:
                        Drawable drawable2 = InternetDetailsContentController.EMPTY_DRAWABLE;
                        return c1DisplayInfo.uniqueName;
                    case 2:
                        Drawable drawable3 = InternetDetailsContentController.EMPTY_DRAWABLE;
                        return Integer.valueOf(c1DisplayInfo.subscriptionInfo.getSubscriptionId());
                    default:
                        Drawable drawable4 = InternetDetailsContentController.EMPTY_DRAWABLE;
                        return c1DisplayInfo.uniqueName;
                }
            }
        }).collect(Collectors.toSet()), 0));
        final int i6 = 2;
        final int i7 = 3;
        return (CharSequence) ((Map) map.collect(Collectors.toMap(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDetailsContentController$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                InternetDetailsContentController.C1DisplayInfo c1DisplayInfo = (InternetDetailsContentController.C1DisplayInfo) obj;
                switch (i6) {
                    case 0:
                        Drawable drawable = InternetDetailsContentController.EMPTY_DRAWABLE;
                        return c1DisplayInfo.originalName;
                    case 1:
                        Drawable drawable2 = InternetDetailsContentController.EMPTY_DRAWABLE;
                        return c1DisplayInfo.uniqueName;
                    case 2:
                        Drawable drawable3 = InternetDetailsContentController.EMPTY_DRAWABLE;
                        return Integer.valueOf(c1DisplayInfo.subscriptionInfo.getSubscriptionId());
                    default:
                        Drawable drawable4 = InternetDetailsContentController.EMPTY_DRAWABLE;
                        return c1DisplayInfo.uniqueName;
                }
            }
        }, new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDetailsContentController$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                InternetDetailsContentController.C1DisplayInfo c1DisplayInfo = (InternetDetailsContentController.C1DisplayInfo) obj;
                switch (i7) {
                    case 0:
                        Drawable drawable = InternetDetailsContentController.EMPTY_DRAWABLE;
                        return c1DisplayInfo.originalName;
                    case 1:
                        Drawable drawable2 = InternetDetailsContentController.EMPTY_DRAWABLE;
                        return c1DisplayInfo.uniqueName;
                    case 2:
                        Drawable drawable3 = InternetDetailsContentController.EMPTY_DRAWABLE;
                        return Integer.valueOf(c1DisplayInfo.subscriptionInfo.getSubscriptionId());
                    default:
                        Drawable drawable4 = InternetDetailsContentController.EMPTY_DRAWABLE;
                        return c1DisplayInfo.uniqueName;
                }
            }
        }))).getOrDefault(Integer.valueOf(i), "");
    }

    public Intent getSettingsIntent() {
        return new Intent("android.settings.NETWORK_PROVIDER_SETTINGS").addFlags(268435456);
    }

    public final Drawable getSignalStrengthDrawable(int i) {
        Drawable drawable = this.mContext.getDrawable(R.drawable.ic_signal_strength_zero_bar_no_internet);
        try {
            if (this.mTelephonyManager == null) {
                if (!DEBUG) {
                    return drawable;
                }
                Log.d("InternetDetailsContentController", "TelephonyManager is null");
                return drawable;
            }
            boolean zIsCarrierNetworkActive = isCarrierNetworkActive();
            if (isDataStateInService(i) || isVoiceStateInService(i) || zIsCarrierNetworkActive) {
                AtomicReference atomicReference = new AtomicReference();
                atomicReference.set(getSignalStrengthDrawableWithLevel(i, zIsCarrierNetworkActive));
                drawable = (Drawable) atomicReference.get();
            }
            int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(this.mContext, android.R.attr.textColorTertiary, 0);
            if (activeNetworkIsCellular() || zIsCarrierNetworkActive) {
                colorAttrDefaultColor = this.mContext.getColor(R.color.connected_network_primary_color);
            }
            drawable.setTint(colorAttrDefaultColor);
            return drawable;
        } catch (Throwable th) {
            th.printStackTrace();
            return drawable;
        }
    }

    public final Drawable getSignalStrengthDrawableWithLevel(int i, boolean z) throws Resources.NotFoundException {
        int state;
        int level;
        SignalStrength signalStrength = this.mSubIdTelephonyManagerMap.getOrDefault(Integer.valueOf(i), this.mTelephonyManager).getSignalStrength();
        int level2 = signalStrength == null ? 0 : signalStrength.getLevel();
        int i2 = 5;
        if (z) {
            MergedCarrierEntry mergedCarrierEntry = ((AccessPointControllerImpl) this.mAccessPointController).getMergedCarrierEntry();
            level2 = (mergedCarrierEntry != null && (level = mergedCarrierEntry.getLevel()) >= 0) ? level : 0;
        } else if (this.mSubscriptionManager != null) {
            CarrierConfigManager carrierConfigManager = (CarrierConfigManager) this.mContext.getSystemService(CarrierConfigManager.class);
            PersistableBundle configForSubId = carrierConfigManager != null ? carrierConfigManager.getConfigForSubId(i) : null;
            if (configForSubId != null && configForSubId.getBoolean("inflate_signal_strength_bool", false)) {
                level2++;
                i2 = 6;
            }
        }
        Context context = this.mContext;
        boolean z2 = !this.mIsMobileDataEnabled;
        boolean z3 = i == this.mDefaultDataSubId;
        if (this.mCarrierNetworkChangeMode) {
            int i3 = SignalDrawable.$r8$clinit;
            state = (i2 << 8) | 196608;
        } else {
            state = SignalDrawable.getState(level2, i2, z2);
        }
        SignalDrawable signalDrawable = this.mSecondarySignalDrawable;
        SignalDrawable signalDrawable2 = this.mSignalDrawable;
        if (z3) {
            signalDrawable2.setLevel(state);
        } else {
            signalDrawable.setLevel(state);
        }
        Drawable[] drawableArr = new Drawable[2];
        drawableArr[0] = EMPTY_DRAWABLE;
        if (z3) {
            signalDrawable = signalDrawable2;
        }
        drawableArr[1] = signalDrawable;
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.signal_strength_icon_size);
        LayerDrawable layerDrawable = new LayerDrawable(drawableArr);
        layerDrawable.setLayerGravity(0, 51);
        layerDrawable.setLayerGravity(1, 85);
        layerDrawable.setLayerSize(1, dimensionPixelSize, dimensionPixelSize);
        layerDrawable.setTintList(Utils.getColorAttr(android.R.attr.textColorTertiary, context));
        return layerDrawable;
    }

    public final CharSequence getSubtitleText(boolean z) {
        boolean z2 = this.mCanConfigWifi;
        boolean z3 = DEBUG;
        if (z2 && !this.mWifiStateWorker.isWifiEnabled()) {
            if (z3) {
                Log.d("InternetDetailsContentController", "Wi-Fi off.");
            }
            return this.mContext.getText(SUBTITLE_TEXT_WIFI_IS_OFF);
        }
        if (isDeviceLocked()) {
            if (z3) {
                Log.d("InternetDetailsContentController", "The device is locked.");
            }
            return this.mContext.getText(SUBTITLE_TEXT_UNLOCK_TO_VIEW_NETWORKS);
        }
        if (this.mHasWifiEntries) {
            if (this.mCanConfigWifi) {
                return this.mContext.getText(SUBTITLE_TEXT_TAP_A_NETWORK_TO_CONNECT);
            }
            return null;
        }
        if (this.mCanConfigWifi && z) {
            return this.mContext.getText(SUBTITLE_TEXT_SEARCHING_FOR_NETWORKS);
        }
        boolean zIsCarrierNetworkActive = isCarrierNetworkActive();
        int i = SUBTITLE_TEXT_NON_CARRIER_NETWORK_UNAVAILABLE;
        if (zIsCarrierNetworkActive) {
            return this.mContext.getText(i);
        }
        if (z3) {
            Log.d("InternetDetailsContentController", "No Wi-Fi item.");
        }
        boolean z4 = false;
        boolean z5 = getActiveAutoSwitchNonDdsSubId() != -1;
        if (!isAirplaneModeEnabled() && this.mTelephonyManager != null) {
            z4 = this.mHasActiveSubIdOnDds;
        }
        int i2 = SUBTITLE_TEXT_ALL_CARRIER_NETWORK_UNAVAILABLE;
        if (!z4 || (!isVoiceStateInService(this.mDefaultDataSubId) && !isDataStateInService(this.mDefaultDataSubId) && !z5)) {
            if (z3) {
                Log.d("InternetDetailsContentController", "No carrier or service is out of service.");
            }
            return this.mContext.getText(i2);
        }
        if (this.mCanConfigWifi && !this.mIsMobileDataEnabled) {
            if (z3) {
                Log.d("InternetDetailsContentController", "Mobile data off");
            }
            return this.mContext.getText(i);
        }
        if (activeNetworkIsCellular()) {
            if (this.mCanConfigWifi) {
                return this.mContext.getText(i);
            }
            return null;
        }
        if (z3) {
            Log.d("InternetDetailsContentController", "No carrier data.");
        }
        return this.mContext.getText(i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Drawable getWifiDrawable(WifiEntry wifiEntry) {
        int i;
        int length = 0;
        if (!(wifiEntry instanceof HotspotNetworkEntry)) {
            if (wifiEntry.getLevel() == -1) {
                return null;
            }
            WifiUtils.InternetIconInjector internetIconInjector = this.mWifiIconInjector;
            boolean zShouldShowXLevelIcon = wifiEntry.shouldShowXLevelIcon();
            int level = wifiEntry.getLevel();
            Context context = internetIconInjector.context;
            WifiUtils.Companion.getClass();
            if (level < 0) {
                ClockEventController$$ExternalSyntheticOutline0.m(level, "Wi-Fi level is out of range! level:", "WifiUtils");
            } else {
                int[] iArr = WifiUtils.WIFI_PIE;
                if (level >= iArr.length) {
                    ClockEventController$$ExternalSyntheticOutline0.m(level, "Wi-Fi level is out of range! level:", "WifiUtils");
                    length = iArr.length - 1;
                } else {
                    length = level;
                }
            }
            return context.getDrawable(zShouldShowXLevelIcon ? WifiUtils.NO_INTERNET_WIFI_PIE[length] : WifiUtils.WIFI_PIE[length]);
        }
        HotspotNetworkEntry hotspotNetworkEntry = (HotspotNetworkEntry) wifiEntry;
        synchronized (hotspotNetworkEntry) {
            HotspotNetwork hotspotNetwork = hotspotNetworkEntry.mHotspotNetworkData;
            if (hotspotNetwork != null) {
                length = hotspotNetwork.getNetworkProviderInfo().getDeviceType();
            }
        }
        Context context2 = this.mContext;
        WifiUtils.Companion.getClass();
        if (length == 1) {
            i = R.drawable.ic_hotspot_phone;
        } else if (length == 2) {
            i = R.drawable.ic_hotspot_tablet;
        } else if (length == 3) {
            i = R.drawable.ic_hotspot_laptop;
        } else if (length == 4) {
            i = R.drawable.ic_hotspot_watch;
        } else if (length == 5) {
            i = R.drawable.ic_hotspot_auto;
        }
        return context2.getDrawable(i);
    }

    public final boolean isAirplaneModeEnabled() {
        return this.mGlobalSettings.getInt(SettingsHelper.INDEX_AIRPLANE_MODE_ON, 0) != 0;
    }

    public final boolean isCarrierNetworkActive() {
        MergedCarrierEntry mergedCarrierEntry = ((AccessPointControllerImpl) this.mAccessPointController).getMergedCarrierEntry();
        return mergedCarrierEntry != null && mergedCarrierEntry.isDefaultNetwork();
    }

    public final boolean isDataStateInService(int i) {
        ServiceState orDefault = this.mSubIdServiceState.getOrDefault(Integer.valueOf(i), new ServiceState());
        NetworkRegistrationInfo networkRegistrationInfo = orDefault == null ? null : orDefault.getNetworkRegistrationInfo(2, 1);
        if (networkRegistrationInfo == null) {
            return false;
        }
        return networkRegistrationInfo.isRegistered();
    }

    public final boolean isDeviceLocked() {
        return !this.mKeyguardStateController.isUnlocked();
    }

    public final boolean isVoiceStateInService(int i) {
        if (this.mTelephonyManager != null) {
            ServiceState orDefault = this.mSubIdServiceState.getOrDefault(Integer.valueOf(i), new ServiceState());
            return orDefault != null && orDefault.getState() == 0;
        }
        if (DEBUG) {
            Log.d("InternetDetailsContentController", "TelephonyManager is null, can not detect voice state.");
        }
        return false;
    }

    public final void launchWifiDetailsSetting(View view, String str) {
        Intent intent;
        if (TextUtils.isEmpty(str)) {
            if (DEBUG) {
                Log.d("InternetDetailsContentController", "connected entry's key is empty");
            }
            intent = null;
        } else {
            WifiUtils.Companion.getClass();
            Intent intent2 = new Intent("android.settings.WIFI_DETAILS_SETTINGS");
            Bundle bundle = new Bundle();
            bundle.putString("key_chosen_wifientry_key", str);
            intent2.putExtra(":settings:show_fragment_args", bundle);
            intent = intent2;
        }
        if (intent != null) {
            startActivity(intent, view);
        }
    }

    public final void makeOverlayToast(int i) throws Resources.NotFoundException {
        Resources resources = this.mContext.getResources();
        Context context = this.mContext;
        final SystemUIToast systemUIToastCreateToast = this.mToastFactory.createToast(context, context, resources.getString(i), this.mContext.getPackageName(), UserHandle.myUserId(), resources.getConfiguration().orientation);
        final View view = systemUIToastCreateToast.mToastView;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.height = -2;
        layoutParams.width = -2;
        layoutParams.format = -3;
        layoutParams.type = 2017;
        layoutParams.flags = 152;
        layoutParams.y = systemUIToastCreateToast.getYOffset().intValue();
        int absoluteGravity = Gravity.getAbsoluteGravity(systemUIToastCreateToast.getGravity().intValue(), resources.getConfiguration().getLayoutDirection());
        layoutParams.gravity = absoluteGravity;
        if ((absoluteGravity & 7) == 7) {
            layoutParams.horizontalWeight = 1.0f;
        }
        if ((absoluteGravity & 112) == 112) {
            layoutParams.verticalWeight = 1.0f;
        }
        this.mWindowManager.addView(view, layoutParams);
        Animator animator = systemUIToastCreateToast.mInAnimator;
        Log.i("InternetDetailsContentController", "makeOverlayToast inAnimator = " + animator + ",toastView = " + view);
        if (animator != null) {
            animator.start();
        }
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.3
            @Override // java.lang.Runnable
            public final void run() {
                Animator animator2 = systemUIToastCreateToast.mOutAnimator;
                Log.i("InternetDetailsContentController", "makeOverlayToast outAnimator = " + animator2 + ", toastView = " + view);
                if (animator2 != null) {
                    animator2.start();
                    animator2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.3.1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator3) {
                            AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                            InternetDetailsContentController.this.mWindowManager.removeViewImmediate(view);
                        }
                    });
                } else if (view != null) {
                    Log.i("InternetDetailsContentController", "makeOverlayToast: " + Debug.getCallers(5));
                    InternetDetailsContentController.this.mWindowManager.removeViewImmediate(view);
                }
            }
        }, SHORT_DURATION_TIMEOUT);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v1, types: [com.android.systemui.qs.tiles.dialog.InternetDetailsContentController$InternetDialogCallback] */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v2, types: [com.android.wifitrackerlib.WifiEntry] */
    /* JADX WARN: Type inference failed for: r11v3 */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.wifitrackerlib.WifiEntry, java.lang.Object] */
    @Override // com.android.systemui.statusbar.connectivity.AccessPointController.AccessPointCallback
    public final void onAccessPointsChanged(List list) {
        ?? r11;
        if (this.mCanConfigWifi) {
            int size = list == null ? 0 : list.size();
            boolean z = size > 3;
            ArrayList arrayList = null;
            if (size > 0) {
                ArrayList arrayList2 = new ArrayList();
                if (z) {
                    size = 3;
                }
                ConnectedWifiInternetMonitor connectedWifiInternetMonitor = this.mConnectedWifiInternetMonitor;
                WifiEntry wifiEntry = connectedWifiInternetMonitor.mWifiEntry;
                if (wifiEntry != null) {
                    synchronized (wifiEntry) {
                        wifiEntry.mListener = null;
                    }
                    connectedWifiInternetMonitor.mWifiEntry = null;
                }
                for (int i = 0; i < size; i++) {
                    ?? r3 = (WifiEntry) list.get(i);
                    ConnectedWifiInternetMonitor connectedWifiInternetMonitor2 = this.mConnectedWifiInternetMonitor;
                    if (r3 == 0) {
                        connectedWifiInternetMonitor2.getClass();
                    } else if (connectedWifiInternetMonitor2.mWifiEntry == null && r3.getConnectedState() == 2 && (!r3.isDefaultNetwork() || !r3.hasInternetAccess())) {
                        connectedWifiInternetMonitor2.mWifiEntry = r3;
                        synchronized (r3) {
                            r3.mListener = connectedWifiInternetMonitor2;
                        }
                    }
                    if (arrayList == null && r3.isDefaultNetwork() && r3.hasInternetAccess()) {
                        arrayList = r3;
                    } else {
                        arrayList2.add(r3);
                    }
                }
                this.mHasWifiEntries = true;
                r11 = arrayList;
                arrayList = arrayList2;
            } else {
                this.mHasWifiEntries = false;
                r11 = 0;
            }
            ?? r10 = this.mCallback;
            if (r10 != 0) {
                r10.onAccessPointsChanged(arrayList, r11, z);
            }
        }
    }

    @Override // com.android.systemui.statusbar.connectivity.AccessPointController.AccessPointCallback
    public final void onWifiScan(boolean z) {
        if (!this.mWifiStateWorker.isWifiEnabled() || isDeviceLocked()) {
            this.mCallback.onWifiScan(false);
        } else {
            this.mCallback.onWifiScan(z);
        }
    }

    public final void refreshHasActiveSubIdOnDds() {
        if (this.mSubscriptionManager == null) {
            this.mHasActiveSubIdOnDds = false;
            Log.e("InternetDetailsContentController", "SubscriptionManager is null, set mHasActiveSubId = false");
            return;
        }
        int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
        if (defaultDataSubscriptionId == -1) {
            this.mHasActiveSubIdOnDds = false;
            Log.d("InternetDetailsContentController", "DDS is INVALID_SUBSCRIPTION_ID");
            return;
        }
        SubscriptionInfo activeSubscriptionInfo = this.mSubscriptionManager.getActiveSubscriptionInfo(defaultDataSubscriptionId);
        if (activeSubscriptionInfo == null) {
            this.mHasActiveSubIdOnDds = false;
            Log.e("InternetDetailsContentController", "Can't get DDS subscriptionInfo");
        } else if (activeSubscriptionInfo.isOnlyNonTerrestrialNetwork()) {
            this.mHasActiveSubIdOnDds = false;
            Log.d("InternetDetailsContentController", "This is NTN, so do not show mobile data");
        } else {
            this.mHasActiveSubIdOnDds = (activeSubscriptionInfo.isEmbedded() && activeSubscriptionInfo.getProfileClass() == 1) ? false : true;
            KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(new StringBuilder("mHasActiveSubId:"), this.mHasActiveSubIdOnDds, "InternetDetailsContentController");
        }
    }

    public final void registerInternetTelephonyCallback(TelephonyManager telephonyManager, int i) {
        if (this.mSubIdTelephonyCallbackMap.containsKey(Integer.valueOf(i))) {
            return;
        }
        InternetTelephonyCallback internetTelephonyCallback = new InternetTelephonyCallback(this, i, 0);
        this.mSubIdTelephonyCallbackMap.put(Integer.valueOf(i), internetTelephonyCallback);
        telephonyManager.registerTelephonyCallback(this.mExecutor, internetTelephonyCallback);
    }

    public final void scanWifiAccessPoints() {
        if (this.mCanConfigWifi) {
            ((AccessPointControllerImpl) this.mAccessPointController).scanForAccessPoints();
        }
    }

    public final void setMobileDataEnabled(Context context, final int i, final boolean z) {
        TelephonyManager telephonyManager = this.mTelephonyManager;
        boolean z2 = DEBUG;
        if (telephonyManager == null) {
            if (z2) {
                Log.d("InternetDetailsContentController", "TelephonyManager is null, can not set mobile data.");
            }
        } else if (this.mSubscriptionManager == null) {
            if (z2) {
                Log.d("InternetDetailsContentController", "SubscriptionManager is null, can not set mobile data.");
            }
        } else {
            telephonyManager.setDataEnabledForReason(0, z);
            this.mWorkerHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDetailsContentController$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    InternetDetailsContentController internetDetailsContentController = this.f$0;
                    int i2 = i;
                    boolean z3 = z;
                    if (internetDetailsContentController.mCarrierConfigTracker.getCarrierProvisionsWifiMergedNetworksBool(i2)) {
                        return;
                    }
                    MergedCarrierEntry mergedCarrierEntry = ((AccessPointControllerImpl) internetDetailsContentController.mAccessPointController).getMergedCarrierEntry();
                    if (mergedCarrierEntry == null) {
                        if (InternetDetailsContentController.DEBUG) {
                            Log.d("InternetDetailsContentController", "MergedCarrierEntry is null, can not set the status.");
                        }
                    } else {
                        mergedCarrierEntry.mWifiManager.setCarrierNetworkOffloadEnabled(mergedCarrierEntry.mSubscriptionId, true, z3);
                        if (z3) {
                            return;
                        }
                        mergedCarrierEntry.mWifiManager.stopRestrictingAutoJoinToSubscriptionId();
                        mergedCarrierEntry.mWifiManager.startScan();
                    }
                }
            });
        }
    }

    public final void startActivity(Intent intent, View view) {
        InternetDialogCallback internetDialogCallback;
        DialogTransitionAnimator dialogTransitionAnimator = this.mDialogTransitionAnimator;
        dialogTransitionAnimator.getClass();
        DialogTransitionAnimator.AnonymousClass1 anonymousClass1CreateActivityTransitionController$default = DialogTransitionAnimator.createActivityTransitionController$default(dialogTransitionAnimator, view);
        if (anonymousClass1CreateActivityTransitionController$default == null && (internetDialogCallback = this.mCallback) != null) {
            internetDialogCallback.dismissDialog();
        }
        this.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0, anonymousClass1CreateActivityTransitionController$default);
    }

    @Override // com.android.systemui.statusbar.connectivity.AccessPointController.AccessPointCallback
    public final void onSettingsActivityTriggered(Intent intent) {
    }
}
