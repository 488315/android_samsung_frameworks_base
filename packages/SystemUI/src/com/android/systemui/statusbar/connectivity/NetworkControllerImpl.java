package com.android.systemui.statusbar.connectivity;

import android.app.StatusBarManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.vcn.VcnUtils;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.CarrierConfigManager;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.SparseArray;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline0;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.MobileStatusTracker;
import com.android.settingslib.mobile.MobileStatusTracker$$ExternalSyntheticLambda1;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.settingslib.net.DataUsageController;
import com.android.settingslib.wifi.WifiStatusTracker;
import com.android.settingslib.wifi.WifiStatusTracker$$ExternalSyntheticLambda0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.qs.tiles.dialog.InternetDialogManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxyImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DataSaverControllerImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.systemui.util.SettingsHelper;
import com.android.wifitrackerlib.WifiPickerTracker;
import com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda11;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settingslib.wifi.WifiWarningDialogController;
import dalvik.annotation.optimization.NeverCompile;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import kotlin.Unit;

/* loaded from: classes3.dex */
public class NetworkControllerImpl extends BroadcastReceiver implements NetworkController, DemoMode, Dumpable {
    public final AccessPointControllerImpl mAccessPoints;
    public int mActiveMobileDataSubscription;
    public boolean mAirplaneMode;
    public final Executor mBgExecutor;
    public final Looper mBgLooper;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final CallbackHandler mCallbackHandler;
    public final NetworkControllerImpl$$ExternalSyntheticLambda2 mClearForceValidated;
    public MobileMappings.Config mConfig;
    public final BitSet mConnectedTransports;
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public List mCurrentSubscriptions;
    public final DataSaverControllerImpl mDataSaverController;
    public final DataUsageController mDataUsageController;
    public MobileSignalController mDefaultSignalController;
    public final DemoModeController mDemoModeController;
    public int mEmergencySource;
    final EthernetSignalController mEthernetSignalController;
    public boolean mForceCellularValidated;
    public final boolean mHasMobileDataFeature;
    public boolean mHasNoSubs;
    public final String[] mHistory;
    public int mHistoryIndex;
    public boolean mInetCondition;
    public final InternetDialogManager mInternetDialogManager;
    public boolean mIsEmergency;
    public NetworkCapabilities mLastDefaultNetworkCapabilities;
    ServiceState mLastServiceState;
    boolean mListening;
    public Locale mLocale;
    public final Object mLock;
    public final LogBuffer mLogBuffer;
    public final Handler mMainHandler;
    public final MobileSignalControllerFactory mMobileFactory;
    final SparseArray<MobileSignalController> mMobileSignalControllers;
    public boolean mNoDefaultNetwork;
    public boolean mNoNetworksAvailable;
    public final TelephonyManager mPhone;
    public final NetworkControllerImpl$$ExternalSyntheticLambda6 mPhoneStateListener;
    public final Handler mReceiverHandler;
    public final NetworkControllerImpl$$ExternalSyntheticLambda2 mRegisterListeners;
    public boolean mSimDetected;
    public final MobileStatusTracker.SubscriptionDefaults mSubDefaults;
    public SubListener mSubscriptionListener;
    public final SubscriptionManager mSubscriptionManager;
    public final TelephonyListenerManager mTelephonyListenerManager;
    public final UserTracker.Callback mUserChangedCallback;
    public boolean mUserSetup;
    public final UserTracker mUserTracker;
    public final BitSet mValidatedTransports;
    public final WifiWarningDialogController mWarningDialogController;
    public final WifiManager mWifiManager;
    final WifiSignalController mWifiSignalController;
    public static final boolean DEBUG = Log.isLoggable("NetworkController", 3);
    public static final boolean CHATTY = Log.isLoggable("NetworkControllerChat", 3);
    public static final SimpleDateFormat SSDF = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");

    /* renamed from: com.android.systemui.statusbar.connectivity.NetworkControllerImpl$1, reason: invalid class name */
    public class AnonymousClass1 implements ConfigurationController.ConfigurationListener {
        public AnonymousClass1() {
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onConfigChanged(Configuration configuration) {
            NetworkControllerImpl networkControllerImpl = NetworkControllerImpl.this;
            networkControllerImpl.mConfig = MobileMappings.Config.readConfig(networkControllerImpl.mContext);
            networkControllerImpl.mReceiverHandler.post(new NetworkControllerImpl$$ExternalSyntheticLambda1(this, 1));
        }
    }

    /* renamed from: com.android.systemui.statusbar.connectivity.NetworkControllerImpl$3, reason: invalid class name */
    public class AnonymousClass3 {
        public AnonymousClass3() {
        }

        public final void onMobileDataEnabled(boolean z) {
            NetworkControllerImpl networkControllerImpl = NetworkControllerImpl.this;
            networkControllerImpl.mCallbackHandler.setMobileDataEnabled(z);
            for (int i = 0; i < networkControllerImpl.mMobileSignalControllers.size(); i++) {
                MobileSignalController mobileSignalControllerValueAt = networkControllerImpl.mMobileSignalControllers.valueAt(i);
                mobileSignalControllerValueAt.checkDefaultData();
                mobileSignalControllerValueAt.notifyListenersIfNecessary();
            }
        }
    }

    /* renamed from: com.android.systemui.statusbar.connectivity.NetworkControllerImpl$7, reason: invalid class name */
    public class AnonymousClass7 extends AsyncTask {
        public final /* synthetic */ boolean val$enabled;

        public AnonymousClass7(boolean z) {
            this.val$enabled = z;
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            if (this.val$enabled) {
                WifiWarningDialogController wifiWarningDialogController = NetworkControllerImpl.this.mWarningDialogController;
                int i = Settings.System.getInt(wifiWarningDialogController.mContext.getContentResolver(), "wifi_sharing_lite_popup_status", 0);
                try {
                    if (!wifiWarningDialogController.mSemWifiManager.isWifiSharingEnabled() && (wifiWarningDialogController.mWifiManager.getWifiApState() == 13 || wifiWarningDialogController.mWifiManager.getWifiApState() == 12)) {
                        StatusBarManager statusBarManager = (StatusBarManager) wifiWarningDialogController.mContext.getSystemService("statusbar");
                        if (statusBarManager != null) {
                            statusBarManager.collapsePanels();
                        }
                        Intent intent = new Intent();
                        intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.samsung.android.settings.wifi.WifiWarning");
                        intent.setFlags(343932928);
                        intent.putExtra("req_type", 0);
                        intent.putExtra("extra_type", 1);
                        wifiWarningDialogController.mContext.startActivity(intent);
                    } else if (wifiWarningDialogController.mSemWifiManager.isWifiSharingLiteSupported() && wifiWarningDialogController.mSemWifiManager.isWifiSharingEnabled() && ((wifiWarningDialogController.mWifiManager.getWifiApState() == 13 || wifiWarningDialogController.mWifiManager.getWifiApState() == 12) && i == 0)) {
                        StatusBarManager statusBarManager2 = (StatusBarManager) wifiWarningDialogController.mContext.getSystemService("statusbar");
                        if (statusBarManager2 != null) {
                            statusBarManager2.collapsePanels();
                        }
                        Intent intent2 = new Intent();
                        intent2.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.samsung.android.settings.wifi.WifiWarning");
                        intent2.setFlags(343932928);
                        intent2.putExtra("req_type", 0);
                        intent2.putExtra("extra_type", 5);
                        wifiWarningDialogController.mContext.startActivity(intent2);
                    }
                    return null;
                } catch (ActivityNotFoundException unused) {
                    return null;
                }
            }
            NetworkControllerImpl.this.mWifiManager.setWifiEnabled(this.val$enabled);
            Context context = NetworkControllerImpl.this.mContext;
            if (context == null) {
                return null;
            }
            Settings.Secure.putInt(context.getContentResolver(), "wifiap_wifi_tile_clicked", 1);
            return null;
        }
    }

    public class SubListener extends SubscriptionManager.OnSubscriptionsChangedListener {
        public SubListener(Looper looper) {
            super(looper);
        }

        @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
        public final void onSubscriptionsChanged() {
            NetworkControllerImpl networkControllerImpl = NetworkControllerImpl.this;
            boolean z = NetworkControllerImpl.DEBUG;
            networkControllerImpl.updateMobileControllers();
        }
    }

    /* renamed from: -$$Nest$mgetProcessedTransportTypes, reason: not valid java name */
    public static int[] m2965$$Nest$mgetProcessedTransportTypes(NetworkControllerImpl networkControllerImpl, NetworkCapabilities networkCapabilities) {
        networkControllerImpl.getClass();
        int[] transportTypes = networkCapabilities.getTransportTypes();
        for (int i = 0; i < transportTypes.length; i++) {
            if (transportTypes[i] == 0 && VcnUtils.getWifiInfoFromVcnCaps(networkControllerImpl.mConnectivityManager, networkCapabilities) != null) {
                transportTypes[i] = 1;
                return transportTypes;
            }
        }
        return transportTypes;
    }

    public NetworkControllerImpl(Context context, Looper looper, Executor executor, SubscriptionManager subscriptionManager, CallbackHandler callbackHandler, DeviceProvisionedController deviceProvisionedController, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, ConnectivityManager connectivityManager, TelephonyManager telephonyManager, TelephonyListenerManager telephonyListenerManager, WifiManager wifiManager, AccessPointControllerImpl accessPointControllerImpl, StatusBarPipelineFlags statusBarPipelineFlags, DemoModeController demoModeController, CarrierConfigTracker carrierConfigTracker, WifiStatusTrackerFactory wifiStatusTrackerFactory, MobileSignalControllerFactory mobileSignalControllerFactory, Handler handler, InternetDialogManager internetDialogManager, DumpManager dumpManager, LogBuffer logBuffer) {
        this(context, connectivityManager, telephonyManager, telephonyListenerManager, wifiManager, subscriptionManager, MobileMappings.Config.readConfig(context), looper, executor, callbackHandler, accessPointControllerImpl, statusBarPipelineFlags, new DataUsageController(context), new MobileStatusTracker.SubscriptionDefaults(), deviceProvisionedController, broadcastDispatcher, userTracker, demoModeController, carrierConfigTracker, wifiStatusTrackerFactory, mobileSignalControllerFactory, handler, dumpManager, logBuffer);
        this.mReceiverHandler.post(this.mRegisterListeners);
        this.mInternetDialogManager = internetDialogManager;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        SignalCallback signalCallback = (SignalCallback) obj;
        signalCallback.setSubs(this.mCurrentSubscriptions);
        boolean z = this.mAirplaneMode;
        int[] iArr = TelephonyIcons.MOBILE_DATA_ACTIVITY_ICONS;
        signalCallback.setIsAirplaneMode(new IconState(z, R.drawable.stat_sys_airplane_mode, this.mContext.getString(R.string.accessibility_airplane_mode)));
        signalCallback.setNoSims(this.mHasNoSubs, this.mSimDetected);
        signalCallback.setConnectivityStatus(this.mNoDefaultNetwork, !this.mInetCondition, this.mNoNetworksAvailable);
        this.mWifiSignalController.notifyListeners(signalCallback);
        this.mEthernetSignalController.notifyListeners(signalCallback);
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            this.mMobileSignalControllers.valueAt(i).notifyListeners(signalCallback);
        }
        this.mCallbackHandler.obtainMessage(7, 1, 0, signalCallback).sendToTarget();
    }

    @Override // com.android.systemui.demomode.DemoMode
    public final List demoCommands() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("network");
        return arrayList;
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void dispatchDemoCommand(Bundle bundle, String str) {
        this.mDemoModeController.getClass();
    }

    public void doUpdateMobileControllers() {
        List<SubscriptionInfo> completeActiveSubscriptionInfoList = this.mSubscriptionManager.getCompleteActiveSubscriptionInfoList();
        if (completeActiveSubscriptionInfoList == null) {
            completeActiveSubscriptionInfoList = Collections.EMPTY_LIST;
        }
        if (completeActiveSubscriptionInfoList.size() == 2) {
            SubscriptionInfo subscriptionInfo = completeActiveSubscriptionInfoList.get(0);
            SubscriptionInfo subscriptionInfo2 = completeActiveSubscriptionInfoList.get(1);
            if (subscriptionInfo.getGroupUuid() != null && subscriptionInfo.getGroupUuid().equals(subscriptionInfo2.getGroupUuid()) && (subscriptionInfo.isOpportunistic() || subscriptionInfo2.isOpportunistic())) {
                if (CarrierConfigManager.getDefaultConfig().getBoolean("always_show_primary_signal_bar_in_opportunistic_network_boolean")) {
                    if (!subscriptionInfo.isOpportunistic()) {
                        subscriptionInfo = subscriptionInfo2;
                    }
                    completeActiveSubscriptionInfoList.remove(subscriptionInfo);
                } else {
                    if (subscriptionInfo.getSubscriptionId() == this.mActiveMobileDataSubscription) {
                        subscriptionInfo = subscriptionInfo2;
                    }
                    completeActiveSubscriptionInfoList.remove(subscriptionInfo);
                }
            }
        }
        if (hasCorrectMobileControllers(completeActiveSubscriptionInfoList)) {
            updateNoSims();
            return;
        }
        synchronized (this.mLock) {
            setCurrentSubscriptionsLocked(completeActiveSubscriptionInfoList);
        }
        updateNoSims();
        recalculateEmergency();
    }

    @Override // com.android.systemui.Dumpable
    @NeverCompile
    public final void dump(PrintWriter printWriter, String[] strArr) {
        String str;
        String[] strArr2;
        StringBuilder sbM = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "NetworkController state:", "  mUserSetup=");
        sbM.append(this.mUserSetup);
        printWriter.println(sbM.toString());
        printWriter.println("  - telephony ------");
        printWriter.print("  hasVoiceCallingFeature()=");
        int i = 0;
        printWriter.println(this.mPhone.getPhoneType() != 0);
        StringBuilder sbM2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mListening="), this.mListening, printWriter, "  mActiveMobileDataSubscription=");
        sbM2.append(this.mActiveMobileDataSubscription);
        printWriter.println(sbM2.toString());
        printWriter.println("  - connectivity ------");
        printWriter.print("  mConnectedTransports=");
        printWriter.println(this.mConnectedTransports);
        printWriter.print("  mValidatedTransports=");
        printWriter.println(this.mValidatedTransports);
        printWriter.print("  mInetCondition=");
        printWriter.println(this.mInetCondition);
        printWriter.print("  mAirplaneMode=");
        printWriter.println(this.mAirplaneMode);
        printWriter.print("  mLocale=");
        printWriter.println(this.mLocale);
        printWriter.print("  mLastServiceState=");
        printWriter.println(this.mLastServiceState);
        printWriter.print("  mIsEmergency=");
        printWriter.println(this.mIsEmergency);
        printWriter.print("  mEmergencySource=");
        int i2 = this.mEmergencySource;
        if (i2 > 300) {
            str = "ASSUMED_VOICE_CONTROLLER(" + (i2 - 200) + ")";
        } else if (i2 > 300) {
            str = "NO_SUB(" + (i2 - 300) + ")";
        } else if (i2 > 200) {
            str = "VOICE_CONTROLLER(" + (i2 - 200) + ")";
        } else if (i2 > 100) {
            str = "FIRST_CONTROLLER(" + (i2 - 100) + ")";
        } else {
            str = i2 == 0 ? "NO_CONTROLLERS" : "UNKNOWN_SOURCE";
        }
        printWriter.println(str);
        printWriter.println("  - DefaultNetworkCallback -----");
        int i3 = 0;
        for (int i4 = 0; i4 < 16; i4++) {
            if (this.mHistory[i4] != null) {
                i3++;
            }
        }
        for (int i5 = this.mHistoryIndex + 15; i5 >= (this.mHistoryIndex + 16) - i3; i5 += -1) {
            printWriter.println("  Previous NetworkCallback(" + ((this.mHistoryIndex + 16) - i5) + "): " + this.mHistory[i5 & 15]);
        }
        printWriter.println("  - config ------");
        for (int i6 = 0; i6 < this.mMobileSignalControllers.size(); i6++) {
            this.mMobileSignalControllers.valueAt(i6).dump(printWriter);
        }
        this.mWifiSignalController.dump(printWriter);
        this.mEthernetSignalController.dump(printWriter);
        AccessPointControllerImpl accessPointControllerImpl = this.mAccessPoints;
        accessPointControllerImpl.getClass();
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        indentingPrintWriter.println("AccessPointControllerImpl:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("Callbacks: " + Arrays.toString(accessPointControllerImpl.mCallbacks.toArray()));
        indentingPrintWriter.println("WifiPickerTracker: " + accessPointControllerImpl.mWifiPickerTracker.toString());
        if (accessPointControllerImpl.mWifiPickerTracker != null && !accessPointControllerImpl.mCallbacks.isEmpty()) {
            indentingPrintWriter.println("Connected: " + accessPointControllerImpl.mWifiPickerTracker.mConnectedWifiEntry);
            StringBuilder sb = new StringBuilder("Other wifi entries: ");
            WifiPickerTracker wifiPickerTracker = accessPointControllerImpl.mWifiPickerTracker;
            wifiPickerTracker.getClass();
            sb.append(Arrays.toString(((List) new ArrayList(wifiPickerTracker.mWifiEntries).stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda11(wifiPickerTracker, 2)).collect(Collectors.toList())).toArray()));
            indentingPrintWriter.println(sb.toString());
        } else if (accessPointControllerImpl.mWifiPickerTracker != null) {
            indentingPrintWriter.println("WifiPickerTracker not started, cannot get reliable entries");
        }
        indentingPrintWriter.decreaseIndent();
        CallbackHandler callbackHandler = this.mCallbackHandler;
        callbackHandler.getClass();
        printWriter.println("  - CallbackHandler -----");
        int i7 = 0;
        while (true) {
            strArr2 = callbackHandler.mHistory;
            if (i >= 64) {
                break;
            }
            if (strArr2[i] != null) {
                i7++;
            }
            i++;
        }
        for (int i8 = callbackHandler.mHistoryIndex + 63; i8 >= (callbackHandler.mHistoryIndex + 64) - i7; i8 += -1) {
            printWriter.println("  Previous Callback(" + ((callbackHandler.mHistoryIndex + 64) - i8) + "): " + strArr2[i8 & 63]);
        }
    }

    public final MobileSignalController getControllerWithSubId(int i) {
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            if (DEBUG) {
                Log.e("NetworkController", "No data sim selected");
            }
            return this.mDefaultSignalController;
        }
        if (this.mMobileSignalControllers.indexOfKey(i) >= 0) {
            return this.mMobileSignalControllers.get(i);
        }
        if (DEBUG) {
            ClockEventController$$ExternalSyntheticOutline0.m(i, "Cannot find controller for data sub: ", "NetworkController");
        }
        return this.mDefaultSignalController;
    }

    public final int getNumberSubscriptions() {
        return this.mMobileSignalControllers.size();
    }

    public void handleConfigurationChanged() {
        updateMobileControllers();
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            MobileSignalController mobileSignalControllerValueAt = this.mMobileSignalControllers.valueAt(i);
            mobileSignalControllerValueAt.mConfig = this.mConfig;
            Context context = mobileSignalControllerValueAt.mContext;
            int subscriptionId = mobileSignalControllerValueAt.mSubscriptionInfo.getSubscriptionId();
            CarrierConfigManager carrierConfigManager = (CarrierConfigManager) context.getSystemService(CarrierConfigManager.class);
            PersistableBundle configForSubId = carrierConfigManager != null ? carrierConfigManager.getConfigForSubId(subscriptionId) : null;
            mobileSignalControllerValueAt.mInflateSignalStrengths = configForSubId != null && configForSubId.getBoolean("inflate_signal_strength_bool", false);
            MobileMappings.Config config = mobileSignalControllerValueAt.mConfig;
            MobileMappingsProxyImpl mobileMappingsProxyImpl = (MobileMappingsProxyImpl) mobileSignalControllerValueAt.mMobileMappingsProxy;
            mobileMappingsProxyImpl.getClass();
            mobileSignalControllerValueAt.mNetworkToIconLookup = MobileMappings.mapIconSets(config);
            mobileSignalControllerValueAt.mDefaultIcons = mobileMappingsProxyImpl.getDefaultIcons(mobileSignalControllerValueAt.mConfig);
            mobileSignalControllerValueAt.updateTelephony();
        }
        refreshLocale();
    }

    public boolean hasCorrectMobileControllers(List<SubscriptionInfo> list) {
        if (list.size() != this.mMobileSignalControllers.size()) {
            return false;
        }
        Iterator<SubscriptionInfo> it = list.iterator();
        while (it.hasNext()) {
            if (this.mMobileSignalControllers.indexOfKey(it.next().getSubscriptionId()) < 0) {
                return false;
            }
        }
        return true;
    }

    public final boolean isPowerOffServiceState() {
        int i = 0;
        while (true) {
            if (i >= this.mMobileSignalControllers.size()) {
                return true;
            }
            MobileSignalController mobileSignalControllerValueAt = this.mMobileSignalControllers.valueAt(i);
            MobileState mobileState = (MobileState) mobileSignalControllerValueAt.mCurrentState;
            ServiceState serviceState = mobileState.serviceState;
            int state = serviceState != null ? serviceState.getState() : -1;
            ServiceState serviceState2 = mobileState.serviceState;
            boolean zCanCellularVoiceService = serviceState2 != null ? serviceState2.canCellularVoiceService() : false;
            ServiceState serviceState3 = mobileState.serviceState;
            boolean z = (serviceState3 != null ? serviceState3.getMobileDataRegState() : -1) == 0;
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(state, "isPowerOffServiceState,mCurrentState.getVoiceServiceState() = ", ",mCurrentState.airplaneMode = ");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, mobileState.airplaneMode, ",mCurrentState.canCellularVoiceService() = ", zCanCellularVoiceService, ",mCurrentState.getMobileDataRegState() = ");
            sbM.append(z);
            Log.d(mobileSignalControllerValueAt.mTag, sbM.toString());
            if (state != 3 && (!mobileState.airplaneMode || state != 0 || (zCanCellularVoiceService && z))) {
                break;
            }
            i++;
        }
        return false;
    }

    public boolean isUserSetup() {
        return this.mUserSetup;
    }

    public final void notifyAllListeners() {
        notifyListeners();
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            this.mMobileSignalControllers.valueAt(i).notifyListeners();
        }
        this.mWifiSignalController.notifyListeners();
        this.mEthernetSignalController.notifyListeners();
    }

    public final void notifyListeners() {
        CallbackHandler callbackHandler = this.mCallbackHandler;
        boolean z = this.mAirplaneMode;
        int[] iArr = TelephonyIcons.MOBILE_DATA_ACTIVITY_ICONS;
        callbackHandler.setIsAirplaneMode(new IconState(z, R.drawable.stat_sys_airplane_mode, this.mContext.getString(R.string.accessibility_airplane_mode)));
        this.mCallbackHandler.setNoSims(this.mHasNoSubs, this.mSimDetected);
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void onDemoModeFinished() {
        if (DEBUG) {
            Log.d("NetworkController", "Exiting demo mode");
        }
        updateMobileControllers();
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            MobileSignalController mobileSignalControllerValueAt = this.mMobileSignalControllers.valueAt(i);
            mobileSignalControllerValueAt.mCurrentState.copyFrom(mobileSignalControllerValueAt.mLastState);
        }
        WifiSignalController wifiSignalController = this.mWifiSignalController;
        wifiSignalController.mCurrentState.copyFrom(wifiSignalController.mLastState);
        this.mReceiverHandler.post(this.mRegisterListeners);
        notifyAllListeners();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0042  */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onReceive(Context context, final Intent intent) {
        int i;
        int i2;
        i = 2;
        i2 = 6;
        if (CHATTY) {
            Log.d("NetworkController", "onReceive: intent=" + intent);
        }
        String action = intent.getAction();
        LogBuffer logBuffer = this.mLogBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("NetworkController", LogLevel.INFO, new NetworkControllerImpl$$ExternalSyntheticLambda8(), null);
        ((LogMessageImpl) logMessageObtain).str1 = action;
        Unit unit = Unit.INSTANCE;
        logBuffer.commit(logMessageObtain);
        action.getClass();
        switch (action) {
            case "android.intent.action.SERVICE_STATE":
                this.mLastServiceState = ServiceState.newFromBundle(intent.getExtras());
                if (this.mMobileSignalControllers.size() == 0) {
                    recalculateEmergency();
                    return;
                }
                return;
            case "android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED":
                recalculateEmergency();
                return;
            case "android.net.conn.CONNECTIVITY_CHANGE":
                updateConnectivity();
                return;
            case "android.telephony.action.CARRIER_CONFIG_CHANGED":
                this.mConfig = MobileMappings.Config.readConfig(this.mContext);
                this.mReceiverHandler.post(new NetworkControllerImpl$$ExternalSyntheticLambda2(this, i2));
                return;
            case "android.intent.action.AIRPLANE_MODE":
                refreshLocale();
                updateAirplaneMode(false);
                return;
            case "android.telephony.action.SUBSCRIPTION_CARRIER_IDENTITY_CHANGED":
                int intExtra = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_ID", -1);
                if (!SubscriptionManager.isValidSubscriptionId(intExtra) || this.mMobileSignalControllers.indexOfKey(intExtra) < 0) {
                    return;
                }
                this.mMobileSignalControllers.get(intExtra).handleBroadcast(intent);
                return;
            case "android.intent.action.SIM_STATE_CHANGED":
                if (intent.getBooleanExtra("rebroadcastOnUnlock", false)) {
                    return;
                }
                updateMobileControllers();
                return;
            case "android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED":
                break;
            case "android.settings.panel.action.INTERNET_CONNECTIVITY":
                this.mMainHandler.post(new NetworkControllerImpl$$ExternalSyntheticLambda2(this, i));
                return;
            default:
                int intExtra2 = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                if (!SubscriptionManager.isValidSubscriptionId(intExtra2)) {
                    final WifiSignalController wifiSignalController = this.mWifiSignalController;
                    wifiSignalController.getClass();
                    wifiSignalController.doInBackground(new Runnable() { // from class: com.android.systemui.statusbar.connectivity.WifiSignalController$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            WifiSignalController wifiSignalController2 = wifiSignalController;
                            Intent intent2 = intent;
                            WifiStatusTracker wifiStatusTracker = wifiSignalController2.mWifiTracker;
                            if (wifiStatusTracker.mWifiManager != null && intent2.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                                wifiStatusTracker.updateWifiState();
                            }
                            wifiSignalController2.copyWifiStates();
                            wifiSignalController2.notifyListenersIfNecessary();
                        }
                    });
                    return;
                } else if (this.mMobileSignalControllers.indexOfKey(intExtra2) >= 0) {
                    this.mMobileSignalControllers.get(intExtra2).handleBroadcast(intent);
                    return;
                } else {
                    updateMobileControllers();
                    return;
                }
        }
        for (int i3 = 0; i3 < this.mMobileSignalControllers.size(); i3++) {
            this.mMobileSignalControllers.valueAt(i3).handleBroadcast(intent);
        }
        this.mConfig = MobileMappings.Config.readConfig(this.mContext);
        this.mReceiverHandler.post(new NetworkControllerImpl$$ExternalSyntheticLambda2(this, i2));
    }

    public final void pushConnectivityToSignals() {
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            MobileSignalController mobileSignalControllerValueAt = this.mMobileSignalControllers.valueAt(i);
            BitSet bitSet = this.mConnectedTransports;
            BitSet bitSet2 = this.mValidatedTransports;
            int i2 = mobileSignalControllerValueAt.mTransportType;
            boolean z = bitSet2.get(i2);
            MobileState mobileState = (MobileState) mobileSignalControllerValueAt.mCurrentState;
            boolean z2 = bitSet.get(i2);
            mobileState.isDefault = z2;
            mobileState.inetCondition = (z || !z2) ? 1 : 0;
            mobileSignalControllerValueAt.notifyListenersIfNecessary();
        }
        WifiSignalController wifiSignalController = this.mWifiSignalController;
        BitSet bitSet3 = this.mValidatedTransports;
        WifiState wifiState = (WifiState) wifiSignalController.mCurrentState;
        wifiState.inetCondition = bitSet3.get(wifiSignalController.mTransportType) ? 1 : 0;
        wifiState.isDefaultConnectionValidated = bitSet3.get(0) || bitSet3.get(1);
        wifiSignalController.notifyListenersIfNecessary();
        EthernetSignalController ethernetSignalController = this.mEthernetSignalController;
        BitSet bitSet4 = this.mConnectedTransports;
        BitSet bitSet5 = this.mValidatedTransports;
        int i3 = ethernetSignalController.mTransportType;
        boolean z3 = bitSet4.get(i3);
        ConnectivityState connectivityState = ethernetSignalController.mCurrentState;
        connectivityState.connected = z3;
        connectivityState.inetCondition = bitSet5.get(i3) ? 1 : 0;
        ethernetSignalController.notifyListenersIfNecessary();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16 */
    public final void recalculateEmergency() {
        ?? r2 = 1;
        r2 = 1;
        if (this.mMobileSignalControllers.size() == 0) {
            this.mEmergencySource = 0;
            ServiceState serviceState = this.mLastServiceState;
            if (serviceState == null || !serviceState.isEmergencyOnly()) {
                r2 = 0;
            }
        } else {
            this.mSubDefaults.getClass();
            int defaultVoiceSubscriptionId = SubscriptionManager.getDefaultVoiceSubscriptionId();
            if (!SubscriptionManager.isValidSubscriptionId(defaultVoiceSubscriptionId)) {
                for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
                    MobileSignalController mobileSignalControllerValueAt = this.mMobileSignalControllers.valueAt(i);
                    if (!((MobileState) mobileSignalControllerValueAt.mCurrentState).isEmergency) {
                        this.mEmergencySource = mobileSignalControllerValueAt.mSubscriptionInfo.getSubscriptionId() + 100;
                        if (DEBUG) {
                            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("Found emergency "), mobileSignalControllerValueAt.mTag, "NetworkController");
                        }
                        r2 = 0;
                    }
                }
            }
            if (this.mMobileSignalControllers.indexOfKey(defaultVoiceSubscriptionId) >= 0) {
                this.mEmergencySource = defaultVoiceSubscriptionId + 200;
                if (DEBUG) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m(defaultVoiceSubscriptionId, "Getting emergency from ", "NetworkController");
                }
                r2 = ((MobileState) this.mMobileSignalControllers.get(defaultVoiceSubscriptionId).mCurrentState).isEmergency;
            } else if (this.mMobileSignalControllers.size() == 1) {
                this.mEmergencySource = this.mMobileSignalControllers.keyAt(0) + 400;
                if (DEBUG) {
                    Log.d("NetworkController", "Getting assumed emergency from " + this.mMobileSignalControllers.keyAt(0));
                }
                r2 = ((MobileState) this.mMobileSignalControllers.valueAt(0).mCurrentState).isEmergency;
            } else {
                if (DEBUG) {
                    ClockEventController$$ExternalSyntheticOutline0.m(defaultVoiceSubscriptionId, "Cannot find controller for voice sub: ", "NetworkController");
                }
                this.mEmergencySource = defaultVoiceSubscriptionId + 300;
            }
        }
        this.mIsEmergency = r2;
        this.mCallbackHandler.obtainMessage(0, r2, 0).sendToTarget();
    }

    public final void refreshLocale() {
        Locale locale = this.mContext.getResources().getConfiguration().locale;
        if (locale.equals(this.mLocale)) {
            return;
        }
        this.mLocale = locale;
        WifiStatusTracker wifiStatusTracker = this.mWifiSignalController.mWifiTracker;
        wifiStatusTracker.updateStatusLabel();
        wifiStatusTracker.mMainThreadHandler.post(new WifiStatusTracker$$ExternalSyntheticLambda0(wifiStatusTracker, 0));
        notifyAllListeners();
    }

    public void registerListeners() {
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            this.mMobileSignalControllers.valueAt(i).registerListener();
        }
        if (this.mSubscriptionListener == null) {
            this.mSubscriptionListener = new SubListener(this.mBgLooper);
        }
        this.mSubscriptionManager.addOnSubscriptionsChangedListener(this.mSubscriptionListener);
        this.mTelephonyListenerManager.addActiveDataSubscriptionIdListener(this.mPhoneStateListener);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.telephony.action.CARRIER_CONFIG_CHANGED");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m(intentFilter, "android.intent.action.SERVICE_STATE", "android.intent.action.SIM_STATE_CHANGED", "android.settings.panel.action.INTERNET_CONNECTIVITY", "android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m(intentFilter, "android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED", "android.telephony.action.SERVICE_PROVIDERS_UPDATED", "android.telephony.action.SUBSCRIPTION_CARRIER_IDENTITY_CHANGED", "android.net.wifi.WIFI_STATE_CHANGED");
        this.mBroadcastDispatcher.registerReceiverWithHandler(this, intentFilter, this.mReceiverHandler);
        this.mListening = true;
        this.mReceiverHandler.post(new NetworkControllerImpl$$ExternalSyntheticLambda2(this, 1));
        Handler handler = this.mReceiverHandler;
        WifiSignalController wifiSignalController = this.mWifiSignalController;
        Objects.requireNonNull(wifiSignalController);
        handler.post(new NetworkControllerImpl$$ExternalSyntheticLambda1(wifiSignalController, 0));
        this.mReceiverHandler.post(new NetworkControllerImpl$$ExternalSyntheticLambda2(this, 0));
        updateMobileControllers();
        this.mReceiverHandler.post(new NetworkControllerImpl$$ExternalSyntheticLambda2(this, 3));
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.mCallbackHandler.obtainMessage(7, 0, 0, (SignalCallback) obj).sendToTarget();
    }

    public void setCurrentSubscriptionsLocked(List<SubscriptionInfo> list) {
        int i;
        int i2;
        SparseArray sparseArray;
        Collections.sort(list, new Comparator(this) { // from class: com.android.systemui.statusbar.connectivity.NetworkControllerImpl.8
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int simSlotIndex;
                int simSlotIndex2;
                SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
                SubscriptionInfo subscriptionInfo2 = (SubscriptionInfo) obj2;
                if (subscriptionInfo.getSimSlotIndex() == subscriptionInfo2.getSimSlotIndex()) {
                    simSlotIndex = subscriptionInfo.getSubscriptionId();
                    simSlotIndex2 = subscriptionInfo2.getSubscriptionId();
                } else {
                    simSlotIndex = subscriptionInfo.getSimSlotIndex();
                    simSlotIndex2 = subscriptionInfo2.getSimSlotIndex();
                }
                return simSlotIndex - simSlotIndex2;
            }
        });
        Locale locale = Locale.US;
        List list2 = this.mCurrentSubscriptions;
        Log.i("NetworkController", "Subscriptions changed: ".concat(String.format(locale, "old=%s, new=%s", list2 != null ? (List) list2.stream().map(new NetworkControllerImpl$$ExternalSyntheticLambda7()).collect(Collectors.toList()) : null, list != null ? (List) list.stream().map(new NetworkControllerImpl$$ExternalSyntheticLambda7()).collect(Collectors.toList()) : null)));
        this.mCurrentSubscriptions = list;
        SparseArray sparseArray2 = new SparseArray();
        boolean z = false;
        for (int i3 = 0; i3 < this.mMobileSignalControllers.size(); i3++) {
            sparseArray2.put(this.mMobileSignalControllers.keyAt(i3), this.mMobileSignalControllers.valueAt(i3));
        }
        this.mMobileSignalControllers.clear();
        int size = list.size();
        int i4 = 0;
        while (i4 < size) {
            int subscriptionId = list.get(i4).getSubscriptionId();
            if (sparseArray2.indexOfKey(subscriptionId) >= 0) {
                this.mMobileSignalControllers.put(subscriptionId, (MobileSignalController) sparseArray2.get(subscriptionId));
                sparseArray2.remove(subscriptionId);
                i = size;
                i2 = i4;
                sparseArray = sparseArray2;
            } else {
                MobileSignalControllerFactory mobileSignalControllerFactory = this.mMobileFactory;
                MobileMappings.Config config = this.mConfig;
                boolean z2 = this.mHasMobileDataFeature;
                TelephonyManager telephonyManagerCreateForSubscriptionId = this.mPhone.createForSubscriptionId(subscriptionId);
                SubscriptionInfo subscriptionInfo = list.get(i4);
                MobileStatusTracker.SubscriptionDefaults subscriptionDefaults = this.mSubDefaults;
                Looper looper = this.mReceiverHandler.getLooper();
                mobileSignalControllerFactory.getClass();
                i = size;
                i2 = i4;
                sparseArray = sparseArray2;
                MobileSignalController mobileSignalController = new MobileSignalController(mobileSignalControllerFactory.context, config, z2, telephonyManagerCreateForSubscriptionId, mobileSignalControllerFactory.callbackHandler, this, mobileSignalControllerFactory.mobileMappings, subscriptionInfo, subscriptionDefaults, looper, mobileSignalControllerFactory.carrierConfigTracker, new MobileStatusTrackerFactory(telephonyManagerCreateForSubscriptionId, looper, subscriptionInfo, subscriptionDefaults));
                ((MobileState) mobileSignalController.mCurrentState).userSetup = this.mUserSetup;
                mobileSignalController.notifyListenersIfNecessary();
                this.mMobileSignalControllers.put(subscriptionId, mobileSignalController);
                if (list.get(i2).getSimSlotIndex() == 0) {
                    this.mDefaultSignalController = mobileSignalController;
                }
                if (this.mListening) {
                    mobileSignalController.registerListener();
                }
            }
            i4 = i2 + 1;
            size = i;
            sparseArray2 = sparseArray;
            z = false;
        }
        SparseArray sparseArray3 = sparseArray2;
        if (this.mListening) {
            int i5 = 0;
            while (i5 < sparseArray3.size()) {
                SparseArray sparseArray4 = sparseArray3;
                int iKeyAt = sparseArray4.keyAt(i5);
                if (sparseArray4.get(iKeyAt) == this.mDefaultSignalController) {
                    this.mDefaultSignalController = null;
                }
                MobileSignalController mobileSignalController2 = (MobileSignalController) sparseArray4.get(iKeyAt);
                mobileSignalController2.mMobileStatusTracker.setListening(false);
                mobileSignalController2.mContext.getContentResolver().unregisterContentObserver(mobileSignalController2.mObserver);
                i5++;
                sparseArray3 = sparseArray4;
            }
        }
        this.mCallbackHandler.setSubs(list);
        notifyAllListeners();
        pushConnectivityToSignals();
        updateAirplaneMode(true);
    }

    public void setNoNetworksAvailable(boolean z) {
        this.mNoNetworksAvailable = z;
    }

    public final void updateAirplaneMode(boolean z) {
        boolean z2 = Settings.Global.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_AIRPLANE_MODE_ON, 0) == 1;
        if (z2 != this.mAirplaneMode || z) {
            this.mAirplaneMode = z2;
            for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
                MobileSignalController mobileSignalControllerValueAt = this.mMobileSignalControllers.valueAt(i);
                ((MobileState) mobileSignalControllerValueAt.mCurrentState).airplaneMode = this.mAirplaneMode;
                mobileSignalControllerValueAt.notifyListenersIfNecessary();
            }
            notifyListeners();
        }
    }

    public final void updateConnectivity() {
        this.mConnectedTransports.clear();
        this.mValidatedTransports.clear();
        NetworkCapabilities networkCapabilities = this.mLastDefaultNetworkCapabilities;
        boolean z = false;
        if (networkCapabilities != null) {
            for (int i : networkCapabilities.getTransportTypes()) {
                if (i == 0 || i == 1 || i == 3) {
                    if (i != 0 || VcnUtils.getWifiInfoFromVcnCaps(this.mConnectivityManager, this.mLastDefaultNetworkCapabilities) == null) {
                        this.mConnectedTransports.set(i);
                        if (this.mLastDefaultNetworkCapabilities.hasCapability(16)) {
                            this.mValidatedTransports.set(i);
                        }
                    } else {
                        this.mConnectedTransports.set(1);
                        if (this.mLastDefaultNetworkCapabilities.hasCapability(16)) {
                            this.mValidatedTransports.set(1);
                        }
                    }
                }
            }
        }
        if (this.mForceCellularValidated) {
            this.mValidatedTransports.set(0);
        }
        if (CHATTY) {
            Log.d("NetworkController", "updateConnectivity: mConnectedTransports=" + this.mConnectedTransports);
            Log.d("NetworkController", "updateConnectivity: mValidatedTransports=" + this.mValidatedTransports);
        }
        this.mInetCondition = this.mValidatedTransports.get(0) || this.mValidatedTransports.get(1) || this.mValidatedTransports.get(3);
        pushConnectivityToSignals();
        if (!this.mConnectedTransports.get(0) && !this.mConnectedTransports.get(1) && !this.mConnectedTransports.get(3)) {
            z = true;
        }
        this.mNoDefaultNetwork = z;
        this.mCallbackHandler.setConnectivityStatus(z, !this.mInetCondition, this.mNoNetworksAvailable);
    }

    public final void updateMobileControllers() {
        if (this.mListening) {
            doUpdateMobileControllers();
        }
    }

    public void updateNoSims() {
        boolean z = false;
        boolean z2 = this.mHasMobileDataFeature && this.mMobileSignalControllers.size() == 0;
        int activeModemCount = this.mPhone.getActiveModemCount();
        int i = 0;
        while (true) {
            if (i < activeModemCount) {
                int simState = this.mPhone.getSimState(i);
                if (simState != 1 && simState != 0) {
                    z = true;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        if (z2 == this.mHasNoSubs && z == this.mSimDetected) {
            return;
        }
        this.mHasNoSubs = z2;
        this.mSimDetected = z;
        this.mCallbackHandler.setNoSims(z2, z);
    }

    /* JADX WARN: Type inference failed for: r14v1, types: [com.android.systemui.statusbar.connectivity.NetworkControllerImpl$$ExternalSyntheticLambda6] */
    public NetworkControllerImpl(Context context, ConnectivityManager connectivityManager, TelephonyManager telephonyManager, TelephonyListenerManager telephonyListenerManager, WifiManager wifiManager, SubscriptionManager subscriptionManager, MobileMappings.Config config, Looper looper, Executor executor, CallbackHandler callbackHandler, AccessPointControllerImpl accessPointControllerImpl, StatusBarPipelineFlags statusBarPipelineFlags, DataUsageController dataUsageController, MobileStatusTracker.SubscriptionDefaults subscriptionDefaults, final DeviceProvisionedController deviceProvisionedController, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, DemoModeController demoModeController, CarrierConfigTracker carrierConfigTracker, WifiStatusTrackerFactory wifiStatusTrackerFactory, MobileSignalControllerFactory mobileSignalControllerFactory, Handler handler, DumpManager dumpManager, LogBuffer logBuffer) {
        this.mLock = new Object();
        this.mActiveMobileDataSubscription = -1;
        this.mMobileSignalControllers = new SparseArray<>();
        this.mConnectedTransports = new BitSet();
        this.mValidatedTransports = new BitSet();
        this.mAirplaneMode = false;
        this.mNoDefaultNetwork = false;
        int i = 1;
        this.mNoNetworksAvailable = true;
        this.mLocale = null;
        this.mCurrentSubscriptions = new ArrayList();
        this.mHistory = new String[16];
        new AnonymousClass1();
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.connectivity.NetworkControllerImpl.2
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i2, Context context2) {
                NetworkControllerImpl networkControllerImpl = NetworkControllerImpl.this;
                networkControllerImpl.mAccessPoints.mCurrentUser = i2;
                networkControllerImpl.updateConnectivity();
            }
        };
        this.mUserChangedCallback = callback;
        this.mClearForceValidated = new NetworkControllerImpl$$ExternalSyntheticLambda2(this, 4);
        this.mRegisterListeners = new NetworkControllerImpl$$ExternalSyntheticLambda2(this, 5);
        this.mContext = context;
        this.mTelephonyListenerManager = telephonyListenerManager;
        this.mConfig = config;
        this.mMainHandler = handler;
        Handler handler2 = new Handler(looper);
        this.mReceiverHandler = handler2;
        this.mBgLooper = looper;
        this.mBgExecutor = executor;
        this.mCallbackHandler = callbackHandler;
        this.mDataSaverController = new DataSaverControllerImpl(context);
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mMobileFactory = mobileSignalControllerFactory;
        this.mSubscriptionManager = subscriptionManager;
        this.mSubDefaults = subscriptionDefaults;
        this.mConnectivityManager = connectivityManager;
        boolean zIsDataCapable = telephonyManager.isDataCapable();
        this.mHasMobileDataFeature = zIsDataCapable;
        this.mDemoModeController = demoModeController;
        this.mLogBuffer = logBuffer;
        this.mPhone = telephonyManager;
        this.mWifiManager = wifiManager;
        this.mLocale = context.getResources().getConfiguration().locale;
        this.mAccessPoints = accessPointControllerImpl;
        this.mDataUsageController = dataUsageController;
        dataUsageController.getClass();
        dataUsageController.mCallback = new AnonymousClass3();
        this.mWifiSignalController = new WifiSignalController(context, zIsDataCapable, callbackHandler, this, wifiManager, wifiStatusTrackerFactory, handler2);
        this.mEthernetSignalController = new EthernetSignalController(context, callbackHandler, this);
        updateAirplaneMode(true);
        ((UserTrackerImpl) userTracker).addCallback(callback, new HandlerExecutor(handler));
        DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = (DeviceProvisionedControllerImpl) deviceProvisionedController;
        deviceProvisionedControllerImpl.addCallback(new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.statusbar.connectivity.NetworkControllerImpl.4
            @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
            public final void onUserSetupChanged() {
                boolean zIsCurrentUserSetup = ((DeviceProvisionedControllerImpl) deviceProvisionedController).isCurrentUserSetup();
                boolean z = NetworkControllerImpl.DEBUG;
                NetworkControllerImpl networkControllerImpl = NetworkControllerImpl.this;
                networkControllerImpl.mReceiverHandler.post(new NetworkControllerImpl$$ExternalSyntheticLambda12(networkControllerImpl, zIsCurrentUserSetup));
            }
        });
        handler2.post(new NetworkControllerImpl$$ExternalSyntheticLambda12(this, deviceProvisionedControllerImpl.isCurrentUserSetup()));
        WifiManager.ScanResultsCallback scanResultsCallback = new WifiManager.ScanResultsCallback() { // from class: com.android.systemui.statusbar.connectivity.NetworkControllerImpl.5
            @Override // android.net.wifi.WifiManager.ScanResultsCallback
            public final void onScanResultsAvailable() {
                NetworkControllerImpl networkControllerImpl = NetworkControllerImpl.this;
                networkControllerImpl.mNoNetworksAvailable = true;
                Iterator<ScanResult> it = networkControllerImpl.mWifiManager.getScanResults().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    } else if (!it.next().SSID.equals(((WifiState) NetworkControllerImpl.this.mWifiSignalController.mCurrentState).ssid)) {
                        NetworkControllerImpl.this.mNoNetworksAvailable = false;
                        break;
                    }
                }
                NetworkControllerImpl networkControllerImpl2 = NetworkControllerImpl.this;
                boolean z = networkControllerImpl2.mNoDefaultNetwork;
                if (z) {
                    networkControllerImpl2.mCallbackHandler.setConnectivityStatus(z, true ^ networkControllerImpl2.mInetCondition, networkControllerImpl2.mNoNetworksAvailable);
                }
            }
        };
        if (wifiManager != null) {
            wifiManager.registerScanResultsCallback(new MobileStatusTracker$$ExternalSyntheticLambda1(handler2), scanResultsCallback);
        }
        connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(i) { // from class: com.android.systemui.statusbar.connectivity.NetworkControllerImpl.6
            public Network mLastNetwork;
            public NetworkCapabilities mLastNetworkCapabilities;

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                NetworkCapabilities networkCapabilities2 = this.mLastNetworkCapabilities;
                boolean z = networkCapabilities2 != null && networkCapabilities2.hasCapability(16);
                boolean zHasCapability = networkCapabilities.hasCapability(16);
                if (network.equals(this.mLastNetwork) && zHasCapability == z) {
                    int[] iArrM2965$$Nest$mgetProcessedTransportTypes = NetworkControllerImpl.m2965$$Nest$mgetProcessedTransportTypes(NetworkControllerImpl.this, networkCapabilities);
                    Arrays.sort(iArrM2965$$Nest$mgetProcessedTransportTypes);
                    NetworkCapabilities networkCapabilities3 = this.mLastNetworkCapabilities;
                    int[] iArrM2965$$Nest$mgetProcessedTransportTypes2 = networkCapabilities3 != null ? NetworkControllerImpl.m2965$$Nest$mgetProcessedTransportTypes(NetworkControllerImpl.this, networkCapabilities3) : null;
                    if (iArrM2965$$Nest$mgetProcessedTransportTypes2 != null) {
                        Arrays.sort(iArrM2965$$Nest$mgetProcessedTransportTypes2);
                    }
                    if (Arrays.equals(iArrM2965$$Nest$mgetProcessedTransportTypes, iArrM2965$$Nest$mgetProcessedTransportTypes2)) {
                        return;
                    }
                }
                this.mLastNetwork = network;
                this.mLastNetworkCapabilities = networkCapabilities;
                NetworkControllerImpl.this.mLastDefaultNetworkCapabilities = networkCapabilities;
                String str = NetworkControllerImpl.SSDF.format(Long.valueOf(System.currentTimeMillis())) + ",onCapabilitiesChanged: network=" + network + ",networkCapabilities=" + networkCapabilities;
                NetworkControllerImpl networkControllerImpl = NetworkControllerImpl.this;
                String[] strArr = networkControllerImpl.mHistory;
                int i2 = networkControllerImpl.mHistoryIndex;
                strArr[i2] = str;
                networkControllerImpl.mHistoryIndex = (i2 + 1) % 16;
                networkControllerImpl.updateConnectivity();
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onLost(Network network) {
                this.mLastNetwork = null;
                this.mLastNetworkCapabilities = null;
                NetworkControllerImpl.this.mLastDefaultNetworkCapabilities = null;
                String str = NetworkControllerImpl.SSDF.format(Long.valueOf(System.currentTimeMillis())) + ",onLost: network=" + network;
                NetworkControllerImpl networkControllerImpl = NetworkControllerImpl.this;
                String[] strArr = networkControllerImpl.mHistory;
                int i2 = networkControllerImpl.mHistoryIndex;
                strArr[i2] = str;
                networkControllerImpl.mHistoryIndex = (i2 + 1) % 16;
                networkControllerImpl.updateConnectivity();
            }
        }, handler2);
        this.mPhoneStateListener = new TelephonyCallback.ActiveDataSubscriptionIdListener() { // from class: com.android.systemui.statusbar.connectivity.NetworkControllerImpl$$ExternalSyntheticLambda6
            @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
            public final void onActiveDataSubscriptionIdChanged(final int i2) {
                final NetworkControllerImpl networkControllerImpl = this.f$0;
                networkControllerImpl.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.connectivity.NetworkControllerImpl$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        NetworkControllerImpl networkControllerImpl2 = networkControllerImpl;
                        int i3 = i2;
                        int i4 = networkControllerImpl2.mActiveMobileDataSubscription;
                        if (networkControllerImpl2.mValidatedTransports.get(0)) {
                            SubscriptionInfo activeSubscriptionInfo = networkControllerImpl2.mSubscriptionManager.getActiveSubscriptionInfo(i4);
                            SubscriptionInfo activeSubscriptionInfo2 = networkControllerImpl2.mSubscriptionManager.getActiveSubscriptionInfo(i3);
                            if (activeSubscriptionInfo != null && activeSubscriptionInfo2 != null && activeSubscriptionInfo.getGroupUuid() != null && activeSubscriptionInfo.getGroupUuid().equals(activeSubscriptionInfo2.getGroupUuid())) {
                                if (NetworkControllerImpl.DEBUG) {
                                    Log.d("NetworkController", ": mForceCellularValidated to true.");
                                }
                                networkControllerImpl2.mForceCellularValidated = true;
                                networkControllerImpl2.mReceiverHandler.removeCallbacks(networkControllerImpl2.mClearForceValidated);
                                networkControllerImpl2.mReceiverHandler.postDelayed(networkControllerImpl2.mClearForceValidated, DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
                            }
                        }
                        networkControllerImpl2.mActiveMobileDataSubscription = i3;
                        networkControllerImpl2.doUpdateMobileControllers();
                    }
                });
            }
        };
        dumpManager.registerNormalDumpable("NetworkController", this);
        this.mWarningDialogController = new WifiWarningDialogController(context);
    }
}
