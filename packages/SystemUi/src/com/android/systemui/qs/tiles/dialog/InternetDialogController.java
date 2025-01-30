package com.android.systemui.qs.tiles.dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.CarrierConfigManager;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.Utils;
import com.android.settingslib.graph.SignalDrawable;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.net.SignalStrengthUtil;
import com.android.settingslib.wifi.WifiUtils;
import com.android.systemui.R;
import com.android.systemui.SysUIToast;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.DialogLaunchAnimator$createActivityLaunchController$1;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.connectivity.AccessPointController;
import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.toast.ToastFactory;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.wifitrackerlib.MergedCarrierEntry;
import com.android.wifitrackerlib.WifiEntry;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class InternetDialogController implements AccessPointController.AccessPointCallback {
    static final long SHORT_DURATION_TIMEOUT = 4000;
    static final float TOAST_PARAMS_HORIZONTAL_WEIGHT = 1.0f;
    static final float TOAST_PARAMS_VERTICAL_WEIGHT = 1.0f;
    public final AccessPointController mAccessPointController;
    protected ActivityStarter mActivityStarter;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public InternetDialogCallback mCallback;
    protected boolean mCanConfigWifi;
    public final CarrierConfigTracker mCarrierConfigTracker;
    protected boolean mCarrierNetworkChangeMode;
    protected ConnectedWifiInternetMonitor mConnectedWifiInternetMonitor;
    public final IntentFilter mConnectionStateFilter;
    public final ConnectivityManager mConnectivityManager;
    public final DataConnectivityListener mConnectivityManagerNetworkCallback;
    public final Context mContext;
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    public final Executor mExecutor;
    public final FeatureFlags mFeatureFlags;
    public final GlobalSettings mGlobalSettings;
    public final Handler mHandler;
    public boolean mHasWifiEntries;
    protected KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LocationController mLocationController;
    protected SubscriptionManager.OnSubscriptionsChangedListener mOnSubscriptionsChangedListener;
    public final SignalDrawable mSecondarySignalDrawable;
    public final SettingsHelper mSettingsHelper;
    public final SignalDrawable mSignalDrawable;
    public final SubscriptionManager mSubscriptionManager;
    public TelephonyManager mTelephonyManager;
    protected WifiUtils.InternetIconInjector mWifiIconInjector;
    public final WifiManager mWifiManager;
    public final WifiStateWorker mWifiStateWorker;
    public final Handler mWorkerHandler;
    public static final Drawable EMPTY_DRAWABLE = new ColorDrawable(0);
    public static final int SUBTITLE_TEXT_WIFI_IS_OFF = R.string.wifi_is_off;
    public static final int SUBTITLE_TEXT_TAP_A_NETWORK_TO_CONNECT = R.string.tap_a_network_to_connect;
    public static final int SUBTITLE_TEXT_UNLOCK_TO_VIEW_NETWORKS = R.string.unlock_to_view_networks;
    public static final int SUBTITLE_TEXT_SEARCHING_FOR_NETWORKS = R.string.wifi_empty_list_wifi_on;
    public static final int SUBTITLE_TEXT_NON_CARRIER_NETWORK_UNAVAILABLE = R.string.non_carrier_network_unavailable;
    public static final int SUBTITLE_TEXT_ALL_CARRIER_NETWORK_UNAVAILABLE = R.string.all_network_unavailable;
    public static final boolean DEBUG = Log.isLoggable("InternetDialogController", 3);
    public static final TelephonyDisplayInfo DEFAULT_TELEPHONY_DISPLAY_INFO = new TelephonyDisplayInfo(0, 0, false);
    final Map<Integer, TelephonyDisplayInfo> mSubIdTelephonyDisplayInfoMap = new HashMap();
    final Map<Integer, TelephonyManager> mSubIdTelephonyManagerMap = new HashMap();
    final Map<Integer, TelephonyCallback> mSubIdTelephonyCallbackMap = new HashMap();
    public MobileMappings.Config mConfig = null;
    public int mDefaultDataSubId = -1;
    protected boolean mHasEthernet = false;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onRefreshCarrierInfo(Intent intent) {
            InternetDialog internetDialog = (InternetDialog) InternetDialogController.this.mCallback;
            internetDialog.mHandler.post(new InternetDialog$$ExternalSyntheticLambda0(internetDialog, 0));
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onSimStateChanged(int i, int i2, int i3) {
            InternetDialog internetDialog = (InternetDialog) InternetDialogController.this.mCallback;
            internetDialog.mHandler.post(new InternetDialog$$ExternalSyntheticLambda0(internetDialog, 4));
        }
    };
    public final C23062 mConnectionStateReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController.2
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!"android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action)) {
                if ("android.net.wifi.supplicant.CONNECTION_CHANGE".equals(action)) {
                    InternetDialogController.m1631$$Nest$mupdateListener(InternetDialogController.this);
                }
            } else {
                if (InternetDialogController.DEBUG) {
                    Log.d("InternetDialogController", "ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
                }
                InternetDialogController.this.mConfig = MobileMappings.Config.readConfig(context);
                InternetDialogController.m1631$$Nest$mupdateListener(InternetDialogController.this);
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialogController$1DisplayInfo, reason: invalid class name */
    public final class C1DisplayInfo {
        public final CharSequence originalName;
        public final SubscriptionInfo subscriptionInfo;
        public CharSequence uniqueName;

        public C1DisplayInfo(InternetDialogController internetDialogController, SubscriptionInfo subscriptionInfo, CharSequence charSequence) {
            this.subscriptionInfo = subscriptionInfo;
            this.originalName = charSequence;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            if (wifiEntry.mIsDefaultNetwork && wifiEntry.hasInternetAccess()) {
                WifiEntry wifiEntry3 = this.mWifiEntry;
                if (wifiEntry3 != null) {
                    synchronized (wifiEntry3) {
                        wifiEntry3.mListener = null;
                    }
                    this.mWifiEntry = null;
                }
                Drawable drawable = InternetDialogController.EMPTY_DRAWABLE;
                InternetDialogController internetDialogController = InternetDialogController.this;
                if (internetDialogController.mCanConfigWifi) {
                    ((AccessPointControllerImpl) internetDialogController.mAccessPointController).scanForAccessPoints();
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DataConnectivityListener extends ConnectivityManager.NetworkCallback {
        public /* synthetic */ DataConnectivityListener(InternetDialogController internetDialogController, int i) {
            this();
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            InternetDialogController.this.mHasEthernet = networkCapabilities.hasTransport(3);
            InternetDialogController internetDialogController = InternetDialogController.this;
            if (internetDialogController.mCanConfigWifi && (internetDialogController.mHasEthernet || networkCapabilities.hasTransport(1))) {
                InternetDialogController internetDialogController2 = InternetDialogController.this;
                if (internetDialogController2.mCanConfigWifi) {
                    ((AccessPointControllerImpl) internetDialogController2.mAccessPointController).scanForAccessPoints();
                }
            }
            InternetDialog internetDialog = (InternetDialog) InternetDialogController.this.mCallback;
            internetDialog.mHandler.post(new InternetDialog$$ExternalSyntheticLambda0(internetDialog, 5));
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            InternetDialogController internetDialogController = InternetDialogController.this;
            internetDialogController.mHasEthernet = false;
            InternetDialog internetDialog = (InternetDialog) internetDialogController.mCallback;
            internetDialog.mHandler.post(new InternetDialog$$ExternalSyntheticLambda0(internetDialog, 1));
        }

        private DataConnectivityListener() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface InternetDialogCallback {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class InternetOnSubscriptionChangedListener extends SubscriptionManager.OnSubscriptionsChangedListener {
        public InternetOnSubscriptionChangedListener() {
        }

        @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
        public final void onSubscriptionsChanged() {
            InternetDialogController.m1631$$Nest$mupdateListener(InternetDialogController.this);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class InternetTelephonyCallback extends TelephonyCallback implements TelephonyCallback.DataConnectionStateListener, TelephonyCallback.DisplayInfoListener, TelephonyCallback.ServiceStateListener, TelephonyCallback.SignalStrengthsListener, TelephonyCallback.UserMobileDataStateListener, TelephonyCallback.CarrierNetworkListener {
        public final int mSubId;

        public /* synthetic */ InternetTelephonyCallback(InternetDialogController internetDialogController, int i, int i2) {
            this(i);
        }

        @Override // android.telephony.TelephonyCallback.CarrierNetworkListener
        public final void onCarrierNetworkChange(boolean z) {
            InternetDialogController internetDialogController = InternetDialogController.this;
            internetDialogController.mCarrierNetworkChangeMode = z;
            InternetDialog internetDialog = (InternetDialog) internetDialogController.mCallback;
            internetDialog.mHandler.post(new InternetDialog$$ExternalSyntheticLambda0(internetDialog, 8));
        }

        @Override // android.telephony.TelephonyCallback.DataConnectionStateListener
        public final void onDataConnectionStateChanged(int i, int i2) {
            InternetDialog internetDialog = (InternetDialog) InternetDialogController.this.mCallback;
            internetDialog.mHandler.post(new InternetDialog$$ExternalSyntheticLambda0(internetDialog, 12));
        }

        @Override // android.telephony.TelephonyCallback.DisplayInfoListener
        public final void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) {
            InternetDialogController.this.mSubIdTelephonyDisplayInfoMap.put(Integer.valueOf(this.mSubId), telephonyDisplayInfo);
            InternetDialog internetDialog = (InternetDialog) InternetDialogController.this.mCallback;
            internetDialog.mHandler.post(new InternetDialog$$ExternalSyntheticLambda0(internetDialog, 11));
        }

        @Override // android.telephony.TelephonyCallback.ServiceStateListener
        public final void onServiceStateChanged(ServiceState serviceState) {
            InternetDialog internetDialog = (InternetDialog) InternetDialogController.this.mCallback;
            internetDialog.mHandler.post(new InternetDialog$$ExternalSyntheticLambda0(internetDialog, 10));
        }

        @Override // android.telephony.TelephonyCallback.SignalStrengthsListener
        public final void onSignalStrengthsChanged(SignalStrength signalStrength) {
            InternetDialog internetDialog = (InternetDialog) InternetDialogController.this.mCallback;
            internetDialog.mHandler.post(new InternetDialog$$ExternalSyntheticLambda0(internetDialog, 9));
        }

        @Override // android.telephony.TelephonyCallback.UserMobileDataStateListener
        public final void onUserMobileDataStateChanged(boolean z) {
            InternetDialog internetDialog = (InternetDialog) InternetDialogController.this.mCallback;
            internetDialog.mHandler.post(new InternetDialog$$ExternalSyntheticLambda0(internetDialog, 13));
        }

        private InternetTelephonyCallback(int i) {
            this.mSubId = i;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WifiEntryConnectCallback implements WifiEntry.ConnectCallback {
        public final ActivityStarter mActivityStarter;
        public final InternetDialogController mInternetDialogController;
        public final WifiEntry mWifiEntry;

        public WifiEntryConnectCallback(ActivityStarter activityStarter, WifiEntry wifiEntry, InternetDialogController internetDialogController) {
            this.mActivityStarter = activityStarter;
            this.mWifiEntry = wifiEntry;
            this.mInternetDialogController = internetDialogController;
        }

        @Override // com.android.wifitrackerlib.WifiEntry.ConnectCallback
        public final void onConnectResult(int i) {
            boolean z = InternetDialogController.DEBUG;
            if (z) {
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("onConnectResult ", i, "InternetDialogController");
            }
            if (i == 1) {
                String key = this.mWifiEntry.getKey();
                Intent intent = new Intent("com.android.settings.WIFI_DIALOG");
                intent.putExtra("key_chosen_wifientry_key", key);
                intent.putExtra("connect_for_caller", true);
                intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                this.mActivityStarter.startActivity(intent, false);
                return;
            }
            if (i == 2) {
                SysUIToast.makeText(R.string.wifi_failed_connect_message, this.mInternetDialogController.mContext, 0).show();
            } else if (z) {
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("connect failure reason=", i, "InternetDialogController");
            }
        }
    }

    /* renamed from: -$$Nest$mupdateListener, reason: not valid java name */
    public static void m1631$$Nest$mupdateListener(InternetDialogController internetDialogController) {
        int defaultDataSubscriptionId = internetDialogController.getDefaultDataSubscriptionId();
        int i = internetDialogController.mDefaultDataSubId;
        int defaultDataSubscriptionId2 = internetDialogController.getDefaultDataSubscriptionId();
        boolean z = DEBUG;
        if (i == defaultDataSubscriptionId2) {
            if (z) {
                Log.d("InternetDialogController", "DDS: no change");
                return;
            }
            return;
        }
        if (z) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("DDS: defaultDataSubId:", defaultDataSubscriptionId, "InternetDialogController");
        }
        if (SubscriptionManager.isUsableSubscriptionId(defaultDataSubscriptionId)) {
            TelephonyCallback telephonyCallback = internetDialogController.mSubIdTelephonyCallbackMap.get(Integer.valueOf(internetDialogController.mDefaultDataSubId));
            if (telephonyCallback != null) {
                internetDialogController.mTelephonyManager.unregisterTelephonyCallback(telephonyCallback);
            } else if (z) {
                Log.e("InternetDialogController", "Unexpected null telephony call back for Sub " + internetDialogController.mDefaultDataSubId);
            }
            internetDialogController.mSubIdTelephonyCallbackMap.remove(Integer.valueOf(internetDialogController.mDefaultDataSubId));
            internetDialogController.mSubIdTelephonyDisplayInfoMap.remove(Integer.valueOf(internetDialogController.mDefaultDataSubId));
            internetDialogController.mSubIdTelephonyManagerMap.remove(Integer.valueOf(internetDialogController.mDefaultDataSubId));
            internetDialogController.mTelephonyManager = internetDialogController.mTelephonyManager.createForSubscriptionId(defaultDataSubscriptionId);
            internetDialogController.mSubIdTelephonyManagerMap.put(Integer.valueOf(defaultDataSubscriptionId), internetDialogController.mTelephonyManager);
            InternetTelephonyCallback internetTelephonyCallback = new InternetTelephonyCallback(internetDialogController, defaultDataSubscriptionId, 0);
            internetDialogController.mSubIdTelephonyCallbackMap.put(Integer.valueOf(defaultDataSubscriptionId), internetTelephonyCallback);
            TelephonyManager telephonyManager = internetDialogController.mTelephonyManager;
            Handler handler = internetDialogController.mHandler;
            Objects.requireNonNull(handler);
            telephonyManager.registerTelephonyCallback(new MediaRoute2Provider$$ExternalSyntheticLambda0(handler), internetTelephonyCallback);
            InternetDialog internetDialog = (InternetDialog) internetDialogController.mCallback;
            internetDialog.mDefaultDataSubId = defaultDataSubscriptionId;
            internetDialog.mTelephonyManager = internetDialog.mTelephonyManager.createForSubscriptionId(defaultDataSubscriptionId);
            internetDialog.mHandler.post(new InternetDialog$$ExternalSyntheticLambda0(internetDialog, 6));
        }
        internetDialogController.mDefaultDataSubId = defaultDataSubscriptionId;
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.qs.tiles.dialog.InternetDialogController$2] */
    public InternetDialogController(Context context, UiEventLogger uiEventLogger, ActivityStarter activityStarter, AccessPointController accessPointController, SubscriptionManager subscriptionManager, TelephonyManager telephonyManager, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Executor executor, BroadcastDispatcher broadcastDispatcher, KeyguardUpdateMonitor keyguardUpdateMonitor, GlobalSettings globalSettings, KeyguardStateController keyguardStateController, WindowManager windowManager, ToastFactory toastFactory, Handler handler2, CarrierConfigTracker carrierConfigTracker, LocationController locationController, DialogLaunchAnimator dialogLaunchAnimator, WifiStateWorker wifiStateWorker, SettingsHelper settingsHelper, FeatureFlags featureFlags) {
        int i = 0;
        if (DEBUG) {
            Log.d("InternetDialogController", "Init InternetDialogController");
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
        this.mSignalDrawable = new SignalDrawable(context);
        this.mSecondarySignalDrawable = new SignalDrawable(context);
        this.mLocationController = locationController;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        this.mConnectedWifiInternetMonitor = new ConnectedWifiInternetMonitor();
        this.mWifiStateWorker = wifiStateWorker;
        this.mFeatureFlags = featureFlags;
        this.mSettingsHelper = settingsHelper;
    }

    public final boolean activeNetworkIsCellular() {
        NetworkCapabilities networkCapabilities;
        ConnectivityManager connectivityManager = this.mConnectivityManager;
        if (connectivityManager == null) {
            if (DEBUG) {
                Log.d("InternetDialogController", "ConnectivityManager is null, can not check active network.");
            }
            return false;
        }
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null || (networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)) == null) {
            return false;
        }
        return networkCapabilities.hasTransport(0);
    }

    public final int getActiveAutoSwitchNonDdsSubId() {
        SubscriptionInfo activeSubscriptionInfo;
        if (!((FeatureFlagsRelease) this.mFeatureFlags).isEnabled(Flags.QS_SECONDARY_DATA_SUB_INFO) || (activeSubscriptionInfo = this.mSubscriptionManager.getActiveSubscriptionInfo(SubscriptionManager.getActiveDataSubscriptionId())) == null || activeSubscriptionInfo.getSubscriptionId() == this.mDefaultDataSubId || activeSubscriptionInfo.isOpportunistic()) {
            return -1;
        }
        int subscriptionId = activeSubscriptionInfo.getSubscriptionId();
        if (this.mSubIdTelephonyManagerMap.get(Integer.valueOf(subscriptionId)) == null) {
            TelephonyManager createForSubscriptionId = this.mTelephonyManager.createForSubscriptionId(subscriptionId);
            InternetTelephonyCallback internetTelephonyCallback = new InternetTelephonyCallback(this, subscriptionId, 0);
            createForSubscriptionId.registerTelephonyCallback(this.mExecutor, internetTelephonyCallback);
            this.mSubIdTelephonyCallbackMap.put(Integer.valueOf(subscriptionId), internetTelephonyCallback);
            this.mSubIdTelephonyManagerMap.put(Integer.valueOf(subscriptionId), createForSubscriptionId);
        }
        return subscriptionId;
    }

    public int getDefaultDataSubscriptionId() {
        return SubscriptionManager.getDefaultDataSubscriptionId();
    }

    public Intent getSettingsIntent() {
        return new Intent("android.settings.NETWORK_PROVIDER_SETTINGS").addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
    }

    public final Drawable getSignalStrengthDrawableWithLevel(int i, boolean z) {
        int i2;
        int i3;
        SignalStrength signalStrength = this.mSubIdTelephonyManagerMap.getOrDefault(Integer.valueOf(i), this.mTelephonyManager).getSignalStrength();
        int level = signalStrength == null ? 0 : signalStrength.getLevel();
        Context context = this.mContext;
        int i4 = 5;
        if (z) {
            MergedCarrierEntry mergedCarrierEntry = ((AccessPointControllerImpl) this.mAccessPointController).getMergedCarrierEntry();
            level = (mergedCarrierEntry != null && (i3 = mergedCarrierEntry.mLevel) >= 0) ? i3 : 0;
        } else if (this.mSubscriptionManager != null && SignalStrengthUtil.shouldInflateSignalStrength(i, context)) {
            level++;
            i4 = 6;
        }
        boolean z2 = !isMobileDataEnabled();
        boolean z3 = i == this.mDefaultDataSubId;
        if (this.mCarrierNetworkChangeMode) {
            int i5 = SignalDrawable.$r8$clinit;
            i2 = (i4 << 8) | 196608;
        } else {
            int i6 = SignalDrawable.$r8$clinit;
            i2 = ((z2 ? 2 : 0) << 16) | (i4 << 8) | level;
        }
        SignalDrawable signalDrawable = this.mSignalDrawable;
        SignalDrawable signalDrawable2 = this.mSecondarySignalDrawable;
        if (z3) {
            signalDrawable.setLevel(i2);
        } else {
            signalDrawable2.setLevel(i2);
        }
        Drawable[] drawableArr = new Drawable[2];
        drawableArr[0] = EMPTY_DRAWABLE;
        if (!z3) {
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

    public final boolean hasActiveSubId() {
        SubscriptionManager subscriptionManager = this.mSubscriptionManager;
        if (subscriptionManager != null) {
            return (isAirplaneModeEnabled() || this.mTelephonyManager == null || subscriptionManager.getActiveSubscriptionIdList().length <= 0) ? false : true;
        }
        if (DEBUG) {
            Log.d("InternetDialogController", "SubscriptionManager is null, can not check carrier.");
        }
        return false;
    }

    public boolean isAirplaneModeEnabled() {
        return this.mGlobalSettings.getInt("airplane_mode_on", 0) != 0;
    }

    public final boolean isCarrierNetworkActive() {
        MergedCarrierEntry mergedCarrierEntry = ((AccessPointControllerImpl) this.mAccessPointController).getMergedCarrierEntry();
        return mergedCarrierEntry != null && mergedCarrierEntry.mIsDefaultNetwork;
    }

    public final boolean isDataStateInService(int i) {
        ServiceState serviceState = this.mSubIdTelephonyManagerMap.getOrDefault(Integer.valueOf(i), this.mTelephonyManager).getServiceState();
        NetworkRegistrationInfo networkRegistrationInfo = serviceState == null ? null : serviceState.getNetworkRegistrationInfo(2, 1);
        if (networkRegistrationInfo == null) {
            return false;
        }
        return networkRegistrationInfo.isRegistered();
    }

    public final boolean isMobileDataEnabled() {
        TelephonyManager telephonyManager = this.mTelephonyManager;
        return telephonyManager != null && telephonyManager.isDataEnabled();
    }

    public final boolean isVoiceStateInService(int i) {
        if (this.mTelephonyManager != null) {
            ServiceState serviceState = this.mSubIdTelephonyManagerMap.getOrDefault(Integer.valueOf(i), this.mTelephonyManager).getServiceState();
            return serviceState != null && serviceState.getState() == 0;
        }
        if (DEBUG) {
            Log.d("InternetDialogController", "TelephonyManager is null, can not detect voice state.");
        }
        return false;
    }

    public final boolean isWifiEnabled() {
        int i = this.mWifiStateWorker.mWifiState;
        return i == 3 || i == 2;
    }

    public final void launchWifiDetailsSetting(View view, String str) {
        Intent intent;
        if (TextUtils.isEmpty(str)) {
            if (DEBUG) {
                Log.d("InternetDialogController", "connected entry's key is empty");
            }
            intent = null;
        } else {
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

    @Override // com.android.systemui.statusbar.connectivity.AccessPointController.AccessPointCallback
    public final void onAccessPointsChanged(List list) {
        final WifiEntry wifiEntry;
        final ArrayList arrayList;
        if (this.mCanConfigWifi) {
            int size = list == null ? 0 : list.size();
            final boolean z = size > 3;
            WifiEntry wifiEntry2 = null;
            if (size > 0) {
                ArrayList arrayList2 = new ArrayList();
                if (z) {
                    size = 3;
                }
                ConnectedWifiInternetMonitor connectedWifiInternetMonitor = this.mConnectedWifiInternetMonitor;
                WifiEntry wifiEntry3 = connectedWifiInternetMonitor.mWifiEntry;
                if (wifiEntry3 != null) {
                    synchronized (wifiEntry3) {
                        wifiEntry3.mListener = null;
                    }
                    connectedWifiInternetMonitor.mWifiEntry = null;
                }
                for (int i = 0; i < size; i++) {
                    WifiEntry wifiEntry4 = (WifiEntry) list.get(i);
                    ConnectedWifiInternetMonitor connectedWifiInternetMonitor2 = this.mConnectedWifiInternetMonitor;
                    if (wifiEntry4 == null) {
                        connectedWifiInternetMonitor2.getClass();
                    } else if (connectedWifiInternetMonitor2.mWifiEntry == null && wifiEntry4.getConnectedState() == 2 && (!wifiEntry4.mIsDefaultNetwork || !wifiEntry4.hasInternetAccess())) {
                        connectedWifiInternetMonitor2.mWifiEntry = wifiEntry4;
                        synchronized (wifiEntry4) {
                            wifiEntry4.mListener = connectedWifiInternetMonitor2;
                        }
                    }
                    if (wifiEntry2 == null && wifiEntry4.mIsDefaultNetwork && wifiEntry4.hasInternetAccess()) {
                        wifiEntry2 = wifiEntry4;
                    } else {
                        arrayList2.add(wifiEntry4);
                    }
                }
                this.mHasWifiEntries = true;
                wifiEntry = wifiEntry2;
                arrayList = arrayList2;
            } else {
                this.mHasWifiEntries = false;
                wifiEntry = null;
                arrayList = null;
            }
            final InternetDialog internetDialog = (InternetDialog) this.mCallback;
            final boolean z2 = internetDialog.mMobileNetworkLayout.getVisibility() == 0 && internetDialog.mInternetDialogController.isAirplaneModeEnabled();
            internetDialog.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    InternetDialog internetDialog2 = InternetDialog.this;
                    WifiEntry wifiEntry5 = wifiEntry;
                    List list2 = arrayList;
                    boolean z3 = z;
                    boolean z4 = z2;
                    internetDialog2.mConnectedWifiEntry = wifiEntry5;
                    internetDialog2.mWifiEntriesCount = list2 == null ? 0 : list2.size();
                    internetDialog2.mHasMoreWifiEntries = z3;
                    internetDialog2.updateDialog(z4);
                    InternetAdapter internetAdapter = internetDialog2.mAdapter;
                    int i2 = internetDialog2.mWifiEntriesCount;
                    internetAdapter.mWifiEntries = list2;
                    int i3 = internetAdapter.mMaxEntriesCount;
                    if (i2 >= i3) {
                        i2 = i3;
                    }
                    internetAdapter.mWifiEntriesCount = i2;
                    internetAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    public final void setMobileDataEnabled(final int i, Context context, final boolean z) {
        TelephonyManager telephonyManager = this.mTelephonyManager;
        boolean z2 = DEBUG;
        if (telephonyManager == null) {
            if (z2) {
                Log.d("InternetDialogController", "TelephonyManager is null, can not set mobile data.");
            }
        } else if (this.mSubscriptionManager == null) {
            if (z2) {
                Log.d("InternetDialogController", "SubscriptionManager is null, can not set mobile data.");
            }
        } else {
            telephonyManager.setDataEnabledForReason(0, z);
            this.mWorkerHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    boolean z3;
                    InternetDialogController internetDialogController = InternetDialogController.this;
                    int i2 = i;
                    boolean z4 = z;
                    CarrierConfigTracker carrierConfigTracker = internetDialogController.mCarrierConfigTracker;
                    synchronized (carrierConfigTracker.mCarrierProvisionsWifiMergedNetworks) {
                        if (carrierConfigTracker.mCarrierProvisionsWifiMergedNetworks.indexOfKey(i2) >= 0) {
                            z3 = carrierConfigTracker.mCarrierProvisionsWifiMergedNetworks.get(i2);
                        } else {
                            if (!carrierConfigTracker.mDefaultCarrierProvisionsWifiMergedNetworksLoaded) {
                                carrierConfigTracker.mDefaultCarrierProvisionsWifiMergedNetworks = CarrierConfigManager.getDefaultConfig().getBoolean("carrier_provisions_wifi_merged_networks_bool");
                                carrierConfigTracker.mDefaultCarrierProvisionsWifiMergedNetworksLoaded = true;
                            }
                            z3 = carrierConfigTracker.mDefaultCarrierProvisionsWifiMergedNetworks;
                        }
                    }
                    if (z3) {
                        return;
                    }
                    MergedCarrierEntry mergedCarrierEntry = ((AccessPointControllerImpl) internetDialogController.mAccessPointController).getMergedCarrierEntry();
                    if (mergedCarrierEntry == null) {
                        if (InternetDialogController.DEBUG) {
                            Log.d("InternetDialogController", "MergedCarrierEntry is null, can not set the status.");
                            return;
                        }
                        return;
                    }
                    int i3 = mergedCarrierEntry.mSubscriptionId;
                    WifiManager wifiManager = mergedCarrierEntry.mWifiManager;
                    wifiManager.setCarrierNetworkOffloadEnabled(i3, true, z4);
                    if (z4) {
                        return;
                    }
                    wifiManager.stopRestrictingAutoJoinToSubscriptionId();
                    wifiManager.startScan();
                }
            });
        }
    }

    public final void startActivity(Intent intent, View view) {
        DialogLaunchAnimator dialogLaunchAnimator = this.mDialogLaunchAnimator;
        dialogLaunchAnimator.getClass();
        DialogLaunchAnimator$createActivityLaunchController$1 createActivityLaunchController$default = DialogLaunchAnimator.createActivityLaunchController$default(dialogLaunchAnimator, view);
        if (createActivityLaunchController$default == null) {
            InternetDialog internetDialog = (InternetDialog) this.mCallback;
            if (InternetDialog.DEBUG) {
                internetDialog.getClass();
                Log.d("InternetDialog", "dismissDialog");
            }
            internetDialog.mInternetDialogFactory.getClass();
            if (InternetDialogFactoryKt.DEBUG) {
                Log.d("InternetDialogFactory", "destroyDialog");
            }
            InternetDialogFactory.internetDialog = null;
            internetDialog.dismiss();
        }
        this.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0, createActivityLaunchController$default);
    }

    @Override // com.android.systemui.statusbar.connectivity.AccessPointController.AccessPointCallback
    public final void onSettingsActivityTriggered(Intent intent) {
    }
}
