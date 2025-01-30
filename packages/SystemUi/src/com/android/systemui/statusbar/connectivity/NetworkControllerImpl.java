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
import android.net.vcn.VcnTransportInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
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
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.MobileStatusTracker;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.settingslib.net.DataUsageController;
import com.android.settingslib.net.SignalStrengthUtil;
import com.android.settingslib.wifi.WifiStatusTracker;
import com.android.settingslib.wifi.WifiStatusTracker$$ExternalSyntheticLambda0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyboard.KeyboardUI$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.qs.tiles.dialog.InternetDialogFactory;
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
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settingslib.wifi.WifiWarningDialogController;
import com.samsung.android.wifi.SemWifiManager;
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
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NetworkControllerImpl extends BroadcastReceiver implements NetworkController, DemoMode, Dumpable {
    public final AccessPointControllerImpl mAccessPoints;
    public int mActiveMobileDataSubscription;
    public boolean mAirplaneMode;
    public final Executor mBgExecutor;
    public final Looper mBgLooper;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final CallbackHandler mCallbackHandler;
    public final NetworkControllerImpl$$ExternalSyntheticLambda0 mClearForceValidated;
    public MobileMappings.Config mConfig;
    public final BitSet mConnectedTransports;
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
    public final InternetDialogFactory mInternetDialogFactory;
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
    public final NetworkControllerImpl$$ExternalSyntheticLambda5 mPhoneStateListener;
    public final Handler mReceiverHandler;
    public final NetworkControllerImpl$$ExternalSyntheticLambda0 mRegisterListeners;
    public boolean mSimDetected;
    public final MobileStatusTracker.SubscriptionDefaults mSubDefaults;
    public SubListener mSubscriptionListener;
    public final SubscriptionManager mSubscriptionManager;
    public final TelephonyListenerManager mTelephonyListenerManager;
    public final UserTracker.Callback mUserChangedCallback;
    public boolean mUserSetup;
    public final BitSet mValidatedTransports;
    public final WifiWarningDialogController mWarningDialogController;
    public final WifiManager mWifiManager;
    final WifiSignalController mWifiSignalController;
    public static final boolean DEBUG = Log.isLoggable("NetworkController", 3);
    public static final boolean CHATTY = Log.isLoggable("NetworkControllerChat", 3);
    public static final SimpleDateFormat SSDF = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.connectivity.NetworkControllerImpl$1 */
    public final class C26211 implements ConfigurationController.ConfigurationListener {
        public C26211() {
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onConfigChanged(Configuration configuration) {
            NetworkControllerImpl networkControllerImpl = NetworkControllerImpl.this;
            networkControllerImpl.mConfig = MobileMappings.Config.readConfig(networkControllerImpl.mContext);
            networkControllerImpl.mReceiverHandler.post(new NetworkControllerImpl$$ExternalSyntheticLambda1(this, 1));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.connectivity.NetworkControllerImpl$3 */
    public final class C26233 {
        public C26233() {
        }

        public final void onMobileDataEnabled(boolean z) {
            NetworkControllerImpl networkControllerImpl = NetworkControllerImpl.this;
            networkControllerImpl.mCallbackHandler.setMobileDataEnabled(z);
            for (int i = 0; i < networkControllerImpl.mMobileSignalControllers.size(); i++) {
                MobileSignalController valueAt = networkControllerImpl.mMobileSignalControllers.valueAt(i);
                valueAt.checkDefaultData();
                valueAt.notifyListenersIfNecessary();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.connectivity.NetworkControllerImpl$7 */
    public final class AsyncTaskC26277 extends AsyncTask {
        public final /* synthetic */ boolean val$enabled;

        public AsyncTaskC26277(boolean z) {
            this.val$enabled = z;
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x004a, code lost:
        
            if ((r1.getWifiApState() == 13 || r1.getWifiApState() == 12) == false) goto L14;
         */
        /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object doInBackground(Object[] objArr) {
            if (this.val$enabled) {
                WifiWarningDialogController wifiWarningDialogController = NetworkControllerImpl.this.mWarningDialogController;
                Context context = wifiWarningDialogController.mContext;
                boolean z = false;
                int i = Settings.System.getInt(context.getContentResolver(), "wifi_sharing_lite_popup_status", 0);
                SemWifiManager semWifiManager = wifiWarningDialogController.mSemWifiManager;
                boolean isWifiSharingEnabled = semWifiManager.isWifiSharingEnabled();
                Context context2 = wifiWarningDialogController.mContext;
                WifiManager wifiManager = wifiWarningDialogController.mWifiManager;
                try {
                    if (!isWifiSharingEnabled) {
                    }
                } catch (ActivityNotFoundException unused) {
                }
                if (!semWifiManager.isWifiApEnabledWithDualBand()) {
                    isWifiSharingEnabled = true;
                    if (semWifiManager.isWifiSharingLiteSupported() && semWifiManager.isWifiSharingEnabled()) {
                        if ((wifiManager.getWifiApState() == 13 || wifiManager.getWifiApState() == 12) && i == 0) {
                            StatusBarManager statusBarManager = (StatusBarManager) context2.getSystemService("statusbar");
                            if (statusBarManager != null) {
                                statusBarManager.collapsePanels();
                            }
                            Intent intent = new Intent();
                            intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.samsung.android.settings.wifi.WifiWarning");
                            intent.setFlags(343932928);
                            intent.putExtra("req_type", 0);
                            intent.putExtra("extra_type", 5);
                            context.startActivity(intent);
                            z = isWifiSharingEnabled;
                        }
                    }
                    if (z) {
                        return null;
                    }
                }
                StatusBarManager statusBarManager2 = (StatusBarManager) context2.getSystemService("statusbar");
                if (statusBarManager2 != null) {
                    statusBarManager2.collapsePanels();
                }
                Intent intent2 = new Intent();
                intent2.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.samsung.android.settings.wifi.WifiWarning");
                intent2.setFlags(343932928);
                intent2.putExtra("req_type", 0);
                isWifiSharingEnabled = true;
                intent2.putExtra("extra_type", 1);
                context.startActivity(intent2);
                z = isWifiSharingEnabled;
                if (z) {
                }
            }
            NetworkControllerImpl.this.mWifiManager.setWifiEnabled(this.val$enabled);
            return null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SubListener extends SubscriptionManager.OnSubscriptionsChangedListener {
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
    public static int[] m1705$$Nest$mgetProcessedTransportTypes(NetworkControllerImpl networkControllerImpl, NetworkCapabilities networkCapabilities) {
        networkControllerImpl.getClass();
        int[] transportTypes = networkCapabilities.getTransportTypes();
        int i = 0;
        while (true) {
            if (i >= transportTypes.length) {
                break;
            }
            if (transportTypes[i] == 0) {
                if (((networkCapabilities.getTransportInfo() == null || !(networkCapabilities.getTransportInfo() instanceof VcnTransportInfo)) ? null : networkCapabilities.getTransportInfo().getWifiInfo()) != null) {
                    transportTypes[i] = 1;
                    break;
                }
            }
            i++;
        }
        return transportTypes;
    }

    public NetworkControllerImpl(Context context, Looper looper, Executor executor, SubscriptionManager subscriptionManager, CallbackHandler callbackHandler, DeviceProvisionedController deviceProvisionedController, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, ConnectivityManager connectivityManager, TelephonyManager telephonyManager, TelephonyListenerManager telephonyListenerManager, WifiManager wifiManager, AccessPointControllerImpl accessPointControllerImpl, StatusBarPipelineFlags statusBarPipelineFlags, DemoModeController demoModeController, CarrierConfigTracker carrierConfigTracker, WifiStatusTrackerFactory wifiStatusTrackerFactory, MobileSignalControllerFactory mobileSignalControllerFactory, Handler handler, InternetDialogFactory internetDialogFactory, DumpManager dumpManager, LogBuffer logBuffer) {
        this(context, connectivityManager, telephonyManager, telephonyListenerManager, wifiManager, subscriptionManager, MobileMappings.Config.readConfig(context), looper, executor, callbackHandler, accessPointControllerImpl, statusBarPipelineFlags, new DataUsageController(context), new MobileStatusTracker.SubscriptionDefaults(), deviceProvisionedController, broadcastDispatcher, userTracker, demoModeController, carrierConfigTracker, wifiStatusTrackerFactory, mobileSignalControllerFactory, handler, dumpManager, logBuffer);
        this.mReceiverHandler.post(this.mRegisterListeners);
        this.mInternetDialogFactory = internetDialogFactory;
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
            completeActiveSubscriptionInfoList = Collections.emptyList();
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
    public final void dump(PrintWriter printWriter, String[] strArr) {
        String str;
        StringBuilder m75m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "NetworkController state:", "  mUserSetup=");
        m75m.append(this.mUserSetup);
        printWriter.println(m75m.toString());
        printWriter.println("  - telephony ------");
        printWriter.print("  hasVoiceCallingFeature()=");
        printWriter.println(this.mPhone.getPhoneType() != 0);
        StringBuilder m64m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("  mListening="), this.mListening, printWriter, "  mActiveMobileDataSubscription=");
        m64m.append(this.mActiveMobileDataSubscription);
        printWriter.println(m64m.toString());
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
        int i = this.mEmergencySource;
        if (i > 300) {
            str = "ASSUMED_VOICE_CONTROLLER(" + (i - 200) + ")";
        } else if (i > 300) {
            str = "NO_SUB(" + (i - 300) + ")";
        } else if (i > 200) {
            str = "VOICE_CONTROLLER(" + (i - 200) + ")";
        } else if (i > 100) {
            str = "FIRST_CONTROLLER(" + (i - 100) + ")";
        } else {
            str = i == 0 ? "NO_CONTROLLERS" : "UNKNOWN_SOURCE";
        }
        printWriter.println(str);
        printWriter.println("  - DefaultNetworkCallback -----");
        int i2 = 0;
        for (int i3 = 0; i3 < 16; i3++) {
            if (this.mHistory[i3] != null) {
                i2++;
            }
        }
        int i4 = this.mHistoryIndex + 16;
        while (true) {
            i4--;
            if (i4 < (this.mHistoryIndex + 16) - i2) {
                break;
            }
            StringBuilder sb = new StringBuilder("  Previous NetworkCallback(");
            sb.append((this.mHistoryIndex + 16) - i4);
            sb.append("): ");
            KeyboardUI$$ExternalSyntheticOutline0.m134m(sb, this.mHistory[i4 & 15], printWriter);
        }
        printWriter.println("  - config ------");
        for (int i5 = 0; i5 < this.mMobileSignalControllers.size(); i5++) {
            this.mMobileSignalControllers.valueAt(i5).dump(printWriter);
        }
        this.mWifiSignalController.dump(printWriter);
        this.mEthernetSignalController.dump(printWriter);
        AccessPointControllerImpl accessPointControllerImpl = this.mAccessPoints;
        accessPointControllerImpl.getClass();
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        indentingPrintWriter.println("AccessPointControllerImpl:");
        indentingPrintWriter.increaseIndent();
        StringBuilder sb2 = new StringBuilder("Callbacks: ");
        ArrayList arrayList = accessPointControllerImpl.mCallbacks;
        sb2.append(Arrays.toString(arrayList.toArray()));
        indentingPrintWriter.println(sb2.toString());
        indentingPrintWriter.println("WifiPickerTracker: " + accessPointControllerImpl.mWifiPickerTracker.toString());
        if (accessPointControllerImpl.mWifiPickerTracker != null && !arrayList.isEmpty()) {
            indentingPrintWriter.println("Connected: " + accessPointControllerImpl.mWifiPickerTracker.getConnectedWifiEntry());
            indentingPrintWriter.println("Other wifi entries: " + Arrays.toString(accessPointControllerImpl.mWifiPickerTracker.getWifiEntries().toArray()));
        } else if (accessPointControllerImpl.mWifiPickerTracker != null) {
            indentingPrintWriter.println("WifiPickerTracker not started, cannot get reliable entries");
        }
        indentingPrintWriter.decreaseIndent();
        CallbackHandler callbackHandler = this.mCallbackHandler;
        callbackHandler.getClass();
        printWriter.println("  - CallbackHandler -----");
        int i6 = 0;
        for (int i7 = 0; i7 < 64; i7++) {
            if (callbackHandler.mHistory[i7] != null) {
                i6++;
            }
        }
        int i8 = callbackHandler.mHistoryIndex + 64;
        while (true) {
            i8--;
            if (i8 < (callbackHandler.mHistoryIndex + 64) - i6) {
                return;
            }
            StringBuilder sb3 = new StringBuilder("  Previous Callback(");
            sb3.append((callbackHandler.mHistoryIndex + 64) - i8);
            sb3.append("): ");
            KeyboardUI$$ExternalSyntheticOutline0.m134m(sb3, callbackHandler.mHistory[i8 & 63], printWriter);
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
            NestedScrollView$$ExternalSyntheticOutline0.m34m("Cannot find controller for data sub: ", i, "NetworkController");
        }
        return this.mDefaultSignalController;
    }

    public void handleConfigurationChanged() {
        updateMobileControllers();
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            MobileSignalController valueAt = this.mMobileSignalControllers.valueAt(i);
            valueAt.mConfig = this.mConfig;
            valueAt.mInflateSignalStrengths = SignalStrengthUtil.shouldInflateSignalStrength(valueAt.mSubscriptionInfo.getSubscriptionId(), valueAt.mContext);
            MobileMappings.Config config = valueAt.mConfig;
            ((MobileMappingsProxyImpl) valueAt.mMobileMappingsProxy).getClass();
            valueAt.mNetworkToIconLookup = MobileMappings.mapIconSets(config);
            valueAt.mDefaultIcons = !valueAt.mConfig.showAtLeast3G ? TelephonyIcons.f221G : TelephonyIcons.THREE_G;
            valueAt.updateTelephony();
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
            boolean z = true;
            if (i >= this.mMobileSignalControllers.size()) {
                return true;
            }
            MobileSignalController valueAt = this.mMobileSignalControllers.valueAt(i);
            MobileState mobileState = (MobileState) valueAt.mCurrentState;
            ServiceState serviceState = mobileState.serviceState;
            int state = serviceState != null ? serviceState.getState() : -1;
            ServiceState serviceState2 = mobileState.serviceState;
            boolean z2 = serviceState2 != null && serviceState2.canCellularVoiceService();
            ServiceState serviceState3 = mobileState.serviceState;
            boolean z3 = (serviceState3 != null ? serviceState3.getMobileDataRegState() : -1) == 0;
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("isPowerOffServiceState,mCurrentState.getVoiceServiceState() = ", state, ",mCurrentState.airplaneMode = ");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m1m, mobileState.airplaneMode, ",mCurrentState.canCellularVoiceService() = ", z2, ",mCurrentState.getMobileDataRegState() = ");
            m1m.append(z3);
            Log.d(valueAt.mTag, m1m.toString());
            if (state != 3 && (!mobileState.airplaneMode || state != 0 || (z2 && z3))) {
                z = false;
            }
            if (!z) {
                return false;
            }
            i++;
        }
    }

    public boolean isUserSetup() {
        return this.mUserSetup;
    }

    public final void notifyAllListeners() {
        notifyListeners();
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            MobileSignalController valueAt = this.mMobileSignalControllers.valueAt(i);
            valueAt.notifyListeners(valueAt.mCallbackHandler);
        }
        WifiSignalController wifiSignalController = this.mWifiSignalController;
        wifiSignalController.notifyListeners(wifiSignalController.mCallbackHandler);
        EthernetSignalController ethernetSignalController = this.mEthernetSignalController;
        ethernetSignalController.notifyListeners(ethernetSignalController.mCallbackHandler);
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
            MobileSignalController valueAt = this.mMobileSignalControllers.valueAt(i);
            valueAt.mCurrentState.copyFrom(valueAt.mLastState);
        }
        WifiSignalController wifiSignalController = this.mWifiSignalController;
        wifiSignalController.mCurrentState.copyFrom(wifiSignalController.mLastState);
        this.mReceiverHandler.post(this.mRegisterListeners);
        notifyAllListeners();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, final Intent intent) {
        char c;
        if (CHATTY) {
            Log.d("NetworkController", "onReceive: intent=" + intent);
        }
        final String action = intent.getAction();
        this.mLogBuffer.log("NetworkController", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.connectivity.NetworkControllerImpl$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ((LogMessage) obj).setStr1(action);
                return Unit.INSTANCE;
            }
        }, new NetworkControllerImpl$$ExternalSyntheticLambda3());
        action.getClass();
        int i = 5;
        int i2 = 4;
        int i3 = 3;
        switch (action.hashCode()) {
            case -2104353374:
                if (action.equals("android.intent.action.SERVICE_STATE")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1465084191:
                if (action.equals("android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1172645946:
                if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1138588223:
                if (action.equals("android.telephony.action.CARRIER_CONFIG_CHANGED")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1076576821:
                if (action.equals("android.intent.action.AIRPLANE_MODE")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -372321735:
                if (action.equals("android.telephony.action.SUBSCRIPTION_CARRIER_IDENTITY_CHANGED")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -229777127:
                if (action.equals("android.intent.action.SIM_STATE_CHANGED")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -25388475:
                if (action.equals("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 464243859:
                if (action.equals("android.settings.panel.action.INTERNET_CONNECTIVITY")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mLastServiceState = ServiceState.newFromBundle(intent.getExtras());
                if (this.mMobileSignalControllers.size() == 0) {
                    recalculateEmergency();
                    return;
                }
                return;
            case 1:
                recalculateEmergency();
                return;
            case 2:
                updateConnectivity();
                return;
            case 3:
                this.mConfig = MobileMappings.Config.readConfig(this.mContext);
                this.mReceiverHandler.post(new NetworkControllerImpl$$ExternalSyntheticLambda0(this, i2));
                return;
            case 4:
                refreshLocale();
                updateAirplaneMode(false);
                return;
            case 5:
                int intExtra = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_ID", -1);
                if (!SubscriptionManager.isValidSubscriptionId(intExtra) || this.mMobileSignalControllers.indexOfKey(intExtra) < 0) {
                    return;
                }
                this.mMobileSignalControllers.get(intExtra).handleBroadcast(intent);
                return;
            case 6:
                if (intent.getBooleanExtra("rebroadcastOnUnlock", false)) {
                    return;
                }
                updateMobileControllers();
                return;
            case 7:
                break;
            case '\b':
                this.mMainHandler.post(new NetworkControllerImpl$$ExternalSyntheticLambda0(this, i));
                return;
            default:
                int intExtra2 = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                if (!SubscriptionManager.isValidSubscriptionId(intExtra2)) {
                    final WifiSignalController wifiSignalController = this.mWifiSignalController;
                    wifiSignalController.getClass();
                    wifiSignalController.doInBackground(new Runnable() { // from class: com.android.systemui.statusbar.connectivity.WifiSignalController$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            WifiSignalController wifiSignalController2 = WifiSignalController.this;
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
        for (int i4 = 0; i4 < this.mMobileSignalControllers.size(); i4++) {
            this.mMobileSignalControllers.valueAt(i4).handleBroadcast(intent);
        }
        this.mConfig = MobileMappings.Config.readConfig(this.mContext);
        this.mReceiverHandler.post(new NetworkControllerImpl$$ExternalSyntheticLambda0(this, i3));
    }

    public final void pushConnectivityToSignals() {
        int i = 0;
        while (true) {
            int i2 = 1;
            if (i >= this.mMobileSignalControllers.size()) {
                break;
            }
            MobileSignalController valueAt = this.mMobileSignalControllers.valueAt(i);
            BitSet bitSet = this.mConnectedTransports;
            BitSet bitSet2 = this.mValidatedTransports;
            int i3 = valueAt.mTransportType;
            boolean z = bitSet2.get(i3);
            MobileState mobileState = (MobileState) valueAt.mCurrentState;
            boolean z2 = bitSet.get(i3);
            mobileState.isDefault = z2;
            if (!z && z2) {
                i2 = 0;
            }
            mobileState.inetCondition = i2;
            valueAt.notifyListenersIfNecessary();
            i++;
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
        int i4 = ethernetSignalController.mTransportType;
        boolean z3 = bitSet4.get(i4);
        ConnectivityState connectivityState = ethernetSignalController.mCurrentState;
        connectivityState.connected = z3;
        connectivityState.inetCondition = bitSet5.get(i4) ? 1 : 0;
        ethernetSignalController.notifyListenersIfNecessary();
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0014, code lost:
    
        if (r0.isEmergencyOnly() != false) goto L37;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void recalculateEmergency() {
        ?? r1 = 1;
        r1 = 1;
        if (this.mMobileSignalControllers.size() == 0) {
            this.mEmergencySource = 0;
            ServiceState serviceState = this.mLastServiceState;
            if (serviceState != null) {
            }
            r1 = 0;
        } else {
            this.mSubDefaults.getClass();
            int defaultVoiceSubscriptionId = SubscriptionManager.getDefaultVoiceSubscriptionId();
            if (!SubscriptionManager.isValidSubscriptionId(defaultVoiceSubscriptionId)) {
                for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
                    MobileSignalController valueAt = this.mMobileSignalControllers.valueAt(i);
                    if (!((MobileState) valueAt.mCurrentState).isEmergency) {
                        this.mEmergencySource = valueAt.mSubscriptionInfo.getSubscriptionId() + 100;
                        if (DEBUG) {
                            ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("Found emergency "), valueAt.mTag, "NetworkController");
                        }
                        r1 = 0;
                    }
                }
            }
            if (this.mMobileSignalControllers.indexOfKey(defaultVoiceSubscriptionId) >= 0) {
                this.mEmergencySource = defaultVoiceSubscriptionId + 200;
                if (DEBUG) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m10m("Getting emergency from ", defaultVoiceSubscriptionId, "NetworkController");
                }
                r1 = ((MobileState) this.mMobileSignalControllers.get(defaultVoiceSubscriptionId).mCurrentState).isEmergency;
            } else if (this.mMobileSignalControllers.size() == 1) {
                this.mEmergencySource = this.mMobileSignalControllers.keyAt(0) + 400;
                if (DEBUG) {
                    Log.d("NetworkController", "Getting assumed emergency from " + this.mMobileSignalControllers.keyAt(0));
                }
                r1 = ((MobileState) this.mMobileSignalControllers.valueAt(0).mCurrentState).isEmergency;
            } else {
                if (DEBUG) {
                    NestedScrollView$$ExternalSyntheticOutline0.m34m("Cannot find controller for voice sub: ", defaultVoiceSubscriptionId, "NetworkController");
                }
                this.mEmergencySource = defaultVoiceSubscriptionId + 300;
            }
        }
        this.mIsEmergency = r1;
        this.mCallbackHandler.obtainMessage(0, r1, 0).sendToTarget();
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
        int i = 0;
        for (int i2 = 0; i2 < this.mMobileSignalControllers.size(); i2++) {
            this.mMobileSignalControllers.valueAt(i2).registerListener();
        }
        if (this.mSubscriptionListener == null) {
            this.mSubscriptionListener = new SubListener(this.mBgLooper);
        }
        this.mSubscriptionManager.addOnSubscriptionsChangedListener(this.mSubscriptionListener);
        this.mTelephonyListenerManager.addActiveDataSubscriptionIdListener(this.mPhoneStateListener);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.telephony.action.CARRIER_CONFIG_CHANGED");
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        intentFilter.addAction("android.intent.action.SERVICE_STATE");
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        intentFilter.addAction("android.settings.panel.action.INTERNET_CONNECTIVITY");
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED");
        intentFilter.addAction("android.telephony.action.SERVICE_PROVIDERS_UPDATED");
        intentFilter.addAction("android.telephony.action.SUBSCRIPTION_CARRIER_IDENTITY_CHANGED");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        this.mBroadcastDispatcher.registerReceiverWithHandler(this, intentFilter, this.mReceiverHandler);
        this.mListening = true;
        this.mReceiverHandler.post(new NetworkControllerImpl$$ExternalSyntheticLambda0(this, i));
        Handler handler = this.mReceiverHandler;
        WifiSignalController wifiSignalController = this.mWifiSignalController;
        Objects.requireNonNull(wifiSignalController);
        handler.post(new NetworkControllerImpl$$ExternalSyntheticLambda1(wifiSignalController, i));
        this.mReceiverHandler.post(new NetworkControllerImpl$$ExternalSyntheticLambda0(this, 1));
        updateMobileControllers();
        this.mReceiverHandler.post(new NetworkControllerImpl$$ExternalSyntheticLambda0(this, 2));
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.mCallbackHandler.obtainMessage(7, 0, 0, (SignalCallback) obj).sendToTarget();
    }

    public void setCurrentSubscriptionsLocked(List<SubscriptionInfo> list) {
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
        Log.i("NetworkController", String.format(locale, "Subscriptions changed: %s", String.format(locale, "old=%s, new=%s", list2 != null ? (List) list2.stream().map(new NetworkControllerImpl$$ExternalSyntheticLambda4()).collect(Collectors.toList()) : null, list != null ? (List) list.stream().map(new NetworkControllerImpl$$ExternalSyntheticLambda4()).collect(Collectors.toList()) : null)));
        this.mCurrentSubscriptions = list;
        SparseArray sparseArray = new SparseArray();
        for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
            sparseArray.put(this.mMobileSignalControllers.keyAt(i), this.mMobileSignalControllers.valueAt(i));
        }
        this.mMobileSignalControllers.clear();
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            int subscriptionId = list.get(i2).getSubscriptionId();
            if (sparseArray.indexOfKey(subscriptionId) >= 0) {
                this.mMobileSignalControllers.put(subscriptionId, (MobileSignalController) sparseArray.get(subscriptionId));
                sparseArray.remove(subscriptionId);
            } else {
                MobileSignalController createMobileSignalController = this.mMobileFactory.createMobileSignalController(this.mConfig, this.mHasMobileDataFeature, this.mPhone.createForSubscriptionId(subscriptionId), this, list.get(i2), this.mSubDefaults, this.mReceiverHandler.getLooper());
                ((MobileState) createMobileSignalController.mCurrentState).userSetup = this.mUserSetup;
                createMobileSignalController.notifyListenersIfNecessary();
                this.mMobileSignalControllers.put(subscriptionId, createMobileSignalController);
                if (list.get(i2).getSimSlotIndex() == 0) {
                    this.mDefaultSignalController = createMobileSignalController;
                }
                if (this.mListening) {
                    createMobileSignalController.registerListener();
                }
            }
        }
        if (this.mListening) {
            for (int i3 = 0; i3 < sparseArray.size(); i3++) {
                int keyAt = sparseArray.keyAt(i3);
                if (sparseArray.get(keyAt) == this.mDefaultSignalController) {
                    this.mDefaultSignalController = null;
                }
                MobileSignalController mobileSignalController = (MobileSignalController) sparseArray.get(keyAt);
                mobileSignalController.mMobileStatusTracker.setListening(false);
                mobileSignalController.mContext.getContentResolver().unregisterContentObserver(mobileSignalController.mObserver);
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
        boolean z2 = Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0) == 1;
        if (z2 != this.mAirplaneMode || z) {
            this.mAirplaneMode = z2;
            for (int i = 0; i < this.mMobileSignalControllers.size(); i++) {
                MobileSignalController valueAt = this.mMobileSignalControllers.valueAt(i);
                ((MobileState) valueAt.mCurrentState).airplaneMode = this.mAirplaneMode;
                valueAt.notifyListenersIfNecessary();
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
                    if (i == 0) {
                        NetworkCapabilities networkCapabilities2 = this.mLastDefaultNetworkCapabilities;
                        if (((networkCapabilities2.getTransportInfo() == null || !(networkCapabilities2.getTransportInfo() instanceof VcnTransportInfo)) ? null : networkCapabilities2.getTransportInfo().getWifiInfo()) != null) {
                            this.mConnectedTransports.set(1);
                            if (this.mLastDefaultNetworkCapabilities.hasCapability(16)) {
                                this.mValidatedTransports.set(1);
                            }
                        }
                    }
                    this.mConnectedTransports.set(i);
                    if (this.mLastDefaultNetworkCapabilities.hasCapability(16)) {
                        this.mValidatedTransports.set(i);
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

    /* JADX WARN: Type inference failed for: r0v21, types: [com.android.systemui.statusbar.connectivity.NetworkControllerImpl$$ExternalSyntheticLambda5] */
    public NetworkControllerImpl(Context context, ConnectivityManager connectivityManager, TelephonyManager telephonyManager, TelephonyListenerManager telephonyListenerManager, WifiManager wifiManager, SubscriptionManager subscriptionManager, MobileMappings.Config config, Looper looper, Executor executor, CallbackHandler callbackHandler, AccessPointControllerImpl accessPointControllerImpl, StatusBarPipelineFlags statusBarPipelineFlags, DataUsageController dataUsageController, MobileStatusTracker.SubscriptionDefaults subscriptionDefaults, final DeviceProvisionedController deviceProvisionedController, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, DemoModeController demoModeController, CarrierConfigTracker carrierConfigTracker, WifiStatusTrackerFactory wifiStatusTrackerFactory, MobileSignalControllerFactory mobileSignalControllerFactory, Handler handler, DumpManager dumpManager, LogBuffer logBuffer) {
        this.mLock = new Object();
        this.mActiveMobileDataSubscription = -1;
        this.mMobileSignalControllers = new SparseArray<>();
        this.mConnectedTransports = new BitSet();
        this.mValidatedTransports = new BitSet();
        this.mAirplaneMode = false;
        this.mNoDefaultNetwork = false;
        this.mNoNetworksAvailable = true;
        this.mLocale = null;
        this.mCurrentSubscriptions = new ArrayList();
        this.mHistory = new String[16];
        new C26211();
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.connectivity.NetworkControllerImpl.2
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                NetworkControllerImpl networkControllerImpl = NetworkControllerImpl.this;
                networkControllerImpl.mAccessPoints.mCurrentUser = i;
                networkControllerImpl.updateConnectivity();
            }
        };
        this.mUserChangedCallback = callback;
        this.mClearForceValidated = new NetworkControllerImpl$$ExternalSyntheticLambda0(this, 6);
        this.mRegisterListeners = new NetworkControllerImpl$$ExternalSyntheticLambda0(this, 7);
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
        boolean isDataCapable = telephonyManager.isDataCapable();
        this.mHasMobileDataFeature = isDataCapable;
        this.mDemoModeController = demoModeController;
        this.mLogBuffer = logBuffer;
        this.mPhone = telephonyManager;
        this.mWifiManager = wifiManager;
        this.mLocale = context.getResources().getConfiguration().locale;
        this.mAccessPoints = accessPointControllerImpl;
        this.mDataUsageController = dataUsageController;
        dataUsageController.getClass();
        dataUsageController.mCallback = new C26233();
        this.mWifiSignalController = new WifiSignalController(context, isDataCapable, callbackHandler, this, wifiManager, wifiStatusTrackerFactory, handler2);
        this.mEthernetSignalController = new EthernetSignalController(context, callbackHandler, this);
        updateAirplaneMode(true);
        ((UserTrackerImpl) userTracker).addCallback(callback, new HandlerExecutor(handler));
        DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = (DeviceProvisionedControllerImpl) deviceProvisionedController;
        deviceProvisionedControllerImpl.addCallback(new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.statusbar.connectivity.NetworkControllerImpl.4
            @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
            public final void onUserSetupChanged() {
                boolean isCurrentUserSetup = ((DeviceProvisionedControllerImpl) deviceProvisionedController).isCurrentUserSetup();
                boolean z = NetworkControllerImpl.DEBUG;
                NetworkControllerImpl networkControllerImpl = NetworkControllerImpl.this;
                networkControllerImpl.mReceiverHandler.post(new NetworkControllerImpl$$ExternalSyntheticLambda7(networkControllerImpl, isCurrentUserSetup));
            }
        });
        handler2.post(new NetworkControllerImpl$$ExternalSyntheticLambda7(this, deviceProvisionedControllerImpl.isCurrentUserSetup()));
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
            wifiManager.registerScanResultsCallback(new MediaRoute2Provider$$ExternalSyntheticLambda0(handler2), scanResultsCallback);
        }
        connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(1) { // from class: com.android.systemui.statusbar.connectivity.NetworkControllerImpl.6
            public Network mLastNetwork;
            public NetworkCapabilities mLastNetworkCapabilities;

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                NetworkCapabilities networkCapabilities2 = this.mLastNetworkCapabilities;
                boolean z = networkCapabilities2 != null && networkCapabilities2.hasCapability(16);
                boolean hasCapability = networkCapabilities.hasCapability(16);
                if (network.equals(this.mLastNetwork) && hasCapability == z) {
                    int[] m1705$$Nest$mgetProcessedTransportTypes = NetworkControllerImpl.m1705$$Nest$mgetProcessedTransportTypes(NetworkControllerImpl.this, networkCapabilities);
                    Arrays.sort(m1705$$Nest$mgetProcessedTransportTypes);
                    NetworkCapabilities networkCapabilities3 = this.mLastNetworkCapabilities;
                    int[] m1705$$Nest$mgetProcessedTransportTypes2 = networkCapabilities3 != null ? NetworkControllerImpl.m1705$$Nest$mgetProcessedTransportTypes(NetworkControllerImpl.this, networkCapabilities3) : null;
                    if (m1705$$Nest$mgetProcessedTransportTypes2 != null) {
                        Arrays.sort(m1705$$Nest$mgetProcessedTransportTypes2);
                    }
                    if (Arrays.equals(m1705$$Nest$mgetProcessedTransportTypes, m1705$$Nest$mgetProcessedTransportTypes2)) {
                        return;
                    }
                }
                this.mLastNetwork = network;
                this.mLastNetworkCapabilities = networkCapabilities;
                NetworkControllerImpl.this.mLastDefaultNetworkCapabilities = networkCapabilities;
                String str = NetworkControllerImpl.SSDF.format(Long.valueOf(System.currentTimeMillis())) + ",onCapabilitiesChanged: network=" + network + ",networkCapabilities=" + networkCapabilities;
                NetworkControllerImpl networkControllerImpl = NetworkControllerImpl.this;
                String[] strArr = networkControllerImpl.mHistory;
                int i = networkControllerImpl.mHistoryIndex;
                strArr[i] = str;
                networkControllerImpl.mHistoryIndex = (i + 1) % 16;
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
                int i = networkControllerImpl.mHistoryIndex;
                strArr[i] = str;
                networkControllerImpl.mHistoryIndex = (i + 1) % 16;
                networkControllerImpl.updateConnectivity();
            }
        }, handler2);
        this.mPhoneStateListener = new TelephonyCallback.ActiveDataSubscriptionIdListener() { // from class: com.android.systemui.statusbar.connectivity.NetworkControllerImpl$$ExternalSyntheticLambda5
            @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
            public final void onActiveDataSubscriptionIdChanged(final int i) {
                final NetworkControllerImpl networkControllerImpl = NetworkControllerImpl.this;
                networkControllerImpl.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.connectivity.NetworkControllerImpl$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        NetworkControllerImpl networkControllerImpl2 = NetworkControllerImpl.this;
                        int i2 = i;
                        int i3 = networkControllerImpl2.mActiveMobileDataSubscription;
                        boolean z = false;
                        if (networkControllerImpl2.mValidatedTransports.get(0)) {
                            SubscriptionInfo activeSubscriptionInfo = networkControllerImpl2.mSubscriptionManager.getActiveSubscriptionInfo(i3);
                            SubscriptionInfo activeSubscriptionInfo2 = networkControllerImpl2.mSubscriptionManager.getActiveSubscriptionInfo(i2);
                            if ((activeSubscriptionInfo == null || activeSubscriptionInfo2 == null || activeSubscriptionInfo.getGroupUuid() == null || !activeSubscriptionInfo.getGroupUuid().equals(activeSubscriptionInfo2.getGroupUuid())) ? false : true) {
                                z = true;
                            }
                        }
                        if (z) {
                            if (NetworkControllerImpl.DEBUG) {
                                Log.d("NetworkController", ": mForceCellularValidated to true.");
                            }
                            networkControllerImpl2.mForceCellularValidated = true;
                            networkControllerImpl2.mReceiverHandler.removeCallbacks(networkControllerImpl2.mClearForceValidated);
                            networkControllerImpl2.mReceiverHandler.postDelayed(networkControllerImpl2.mClearForceValidated, 2000L);
                        }
                        networkControllerImpl2.mActiveMobileDataSubscription = i2;
                        networkControllerImpl2.doUpdateMobileControllers();
                    }
                });
            }
        };
        demoModeController.addCallback((DemoMode) this);
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "NetworkController", this);
        this.mWarningDialogController = new WifiWarningDialogController(context);
    }
}
