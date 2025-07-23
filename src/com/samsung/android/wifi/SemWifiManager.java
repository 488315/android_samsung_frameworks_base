package com.samsung.android.wifi;

import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Binder;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.CloseGuard;
import android.util.Log;
import android.util.SparseArray;
import com.samsung.android.wifi.ISemAbTestConfigurationUpdateObserver;
import com.samsung.android.wifi.ISemWifiApClientListUpdateCallback;
import com.samsung.android.wifi.ISemWifiApClientUpdateCallback;
import com.samsung.android.wifi.ISemWifiApDataUsageCallback;
import com.samsung.android.wifi.ISemWifiApSmartCallback;
import com.samsung.android.wifi.SemTasPolicyListener;
import com.samsung.android.wifi.SemWifiManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class SemWifiManager {
    public static final String ACTION_AUTO_WIFI_BUBBLE_TIP = "com.samsung.android.wifi.ACTION_AUTO_WIFI_BUBBLE_TIP";
    public static final String ACTION_AUTO_WIFI_SCAN_STATE_CHANGED = "com.samsung.android.wifi.AUTO_WIFI_SCAN_STATE_CHANGED";
    public static final String ACTION_DIAGNOSIS_RESULT_AVAILABLE = "com.samsung.android.net.wifi.wifiguider.DIAGNOSIS_RESULT_AVAILABLE";
    public static final String ACTION_ISSUE_DETECTED = "com.samsung.android.net.wifi.ISSUE_DETECTED";
    public static final String ACTION_NETWORK_CONNECT_FAILED = "com.samsung.android.net.wifi.NETWORK_CONNECT_FAILED";

    @Deprecated(forRemoval = false, since = "16.0")
    public static final String ACTION_WIFI_AP_STATE_CHANGED = "android.net.wifi.WIFI_AP_STATE_CHANGED";

    @Deprecated(forRemoval = false, since = "16.0")
    public static final String ACTION_WIFI_AP_STA_STATE_CHANGED = "com.samsung.android.net.wifi.WIFI_AP_STA_STATE_CHANGED";
    public static final int BAND_2GHZ = 2;
    public static final int BAND_5GHZ = 5;
    public static final int BAND_6GHZ = 6;
    public static final int BASE_ASSOC_REJECT_REASON = 10000;
    public static final int BUSY = 2;
    public static final int DEFAULT_PROVISION_VALUE = 0;
    public static final int ERROR = 0;
    public static final int ERROR_AUTH_FAILURE_EAP_CA_CERTIFICATION = 4;
    public static final int ERROR_AUTH_FAILURE_EAP_DOMAIN_MISMATCH = 5;
    public static final int ERROR_AUTH_FAILURE_EAP_FAILURE = 3;
    public static final int ERROR_AUTH_FAILURE_NONE = 0;
    public static final int ERROR_AUTH_FAILURE_TIMEOUT = 1;
    public static final int ERROR_AUTH_FAILURE_WRONG_PSWD = 2;
    public static final int ERROR_DHCP = 20000;
    public static final String EXTRA_AUTO_WIFI_SCAN_AVAILABLE = "autoWifiScanAvailable";
    public static final String EXTRA_BIGDATA_FEATURE = "bigdataFeature";
    public static final String EXTRA_BSSID = "bssid";
    public static final String EXTRA_CALLED_DIALOG = "called_dialog";
    public static final String EXTRA_CATEGORY_ID = "categoryId";
    public static final String EXTRA_NET_ID = "networkId";
    public static final String EXTRA_PATTERN_ID = "patternId";
    public static final String EXTRA_REASON_CODE = "reason_code";

    @Deprecated(forRemoval = false, since = "16.0")
    public static final String EXTRA_WIFI_AP_STATE = "wifi_state";

    @Deprecated(forRemoval = false, since = "16.0")
    public static final String EXTRA_WIFI_AP_STA_COUNT = "STA_COUNT";
    public static final int HOTSPOT_MODE_MIMO = 1;
    public static final int HOTSPOT_MODE_SISO = 2;
    public static final int INTERWORKING_DISABLED_BY_DEVICE = 2;
    public static final int INTERWORKING_DISABLED_BY_USER = 0;
    public static final int INTERWORKING_ENABLED_BY_DEVICE = 3;
    public static final int INTERWORKING_ENABLED_BY_USER = 1;
    public static final int INTERWORKING_INVALID_VALUE = -1;
    public static final int IN_PROGRESS = 1;
    public static final String KEY_GEO_LOCATION_LATITUDE = "latitude";
    public static final String KEY_GEO_LOCATION_LONGITUDE = "longitude";
    private static final int MAX_CLIENT = 10;
    public static final int OPTIMIZER_MODE_DEFAULT = 0;
    public static final int OPTIMIZER_MODE_FORCE_DISABLE = 2;
    public static final int OPTIMIZER_MODE_FORCE_ENABLE = 1;
    public static final int PROVISION_FAILED = 2;
    public static final int PROVISION_SUCCESS = 1;
    public static final int STATUS_WIFI_UWB_COEX_ERROR_INVALID = 2;
    public static final int STATUS_WIFI_UWB_COEX_ERROR_REJECT = 1;
    public static final int STATUS_WIFI_UWB_COEX_SUCCESS = 0;
    private static final String TAG = "SemWifiManager";
    public static final int TAS_POLICY_HIGH = 2;
    public static final int TAS_POLICY_LOW = 0;
    public static final int TAS_POLICY_MID = 1;
    public static final int TAS_POLICY_UNDER_HIGH = 5;
    public static final int TAS_POLICY_UNDER_LOW = 3;
    public static final int TAS_POLICY_UNDER_MID = 4;
    public static final int TAS_POLICY_UNKNOWN = -1;
    public static final int TEST_MODULE_ID_AUTO_WIFI = 1;
    public static final int TEST_MODULE_ID_BACK_OFF_CONTROLLER = 6;
    public static final int TEST_MODULE_ID_HAL_MONKEY_TEST = 7;
    public static final int TEST_MODULE_ID_MAX = 8;
    public static final int TEST_MODULE_ID_QOS_PROFILE_SHARE = 2;
    public static final int TEST_MODULE_ID_SCPM_MONITOR = 4;
    public static final int TEST_MODULE_ID_SILENT_ROAMING_TEST = 5;
    public static final int TEST_MODULE_ID_WLAN_AUTO_TEST = 3;
    public static final String WIFI_AP_DRIVER_STATE_HANGED = "com.samsung.android.net.wifi.WIFI_AP_DRIVER_STATE_HANGED";

    @Deprecated(forRemoval = false, since = "16.0")
    public static final int WIFI_AP_STATE_DISABLED = 11;

    @Deprecated(forRemoval = false, since = "16.0")
    public static final int WIFI_AP_STATE_DISABLING = 10;

    @Deprecated(forRemoval = false, since = "16.0")
    public static final int WIFI_AP_STATE_ENABLED = 13;

    @Deprecated(forRemoval = false, since = "16.0")
    public static final int WIFI_AP_STATE_ENABLING = 12;

    @Deprecated(forRemoval = false, since = "16.0")
    public static final int WIFI_AP_STATE_FAILED = 14;
    public static final String WIFI_AP_STA_DHCPACK_EVENT = "com.samsung.android.net.wifi.WIFI_AP_STA_DHCPACK_EVENT";
    public static final String WIFI_CONNECTIVITY_HIDE_ICON_ACTION = "com.sec.android.WIFI_ICON_HIDE_ACTION";
    public static final String WIFI_CONNECTIVITY_TEST_REPORT_ACTION = "com.sec.android.WIFI_CONNECTIVITY_ACTION";
    public static final String WIFI_DIALOG_CANCEL_ACTION = "com.samsung.android.net.wifi.WIFI_DIALOG_CANCEL_ACTION";
    public static final int WIFI_DIALOG_ENABLING_HOTSPOT = 2;
    public static final String WIFI_TCP_MONITOR_ACTION_SETTINGS = "com.samsung.android.net.wifi.WIFI_TCP_MONITOR_ACTION_SETTINGS";
    public static final String WIFI_TCP_MONITOR_ACTION_USE_MOBILE_DATA = "com.samsung.android.net.wifi.TCP_MONITOR_ACTION_USE_MOBILE_DATA";
    public static final String WIFI_TCP_MONITOR_DELETE_NOTIFICATION = "com.samsung.android.net.wifi.WIFI_TCP_MONITOR_DELETE_NOTIFICATION";
    public static final String WIFI_TCP_MONITOR_SWITCHABLE_APP_LIST_CHANGED = "com.samsung.android.net.wifi.WIFI_TCP_MONITOR_SWITCHABLE_APP_LIST_CHANGED";
    public static final String WIFI_WCM_CONFIGURATION_CHANGED = "com.sec.android.WIFI_WCM_CONFIGURATION_CHANGED";
    public static final int WIFI_WCM_ICON_INVALID_FORCED = 0;
    public static final int WIFI_WCM_ICON_NOT_FORCED = -1;
    public static final int WIFI_WCM_ICON_VALID_FORCED = 1;
    public static final String WIFI_WCM_STATE_CHANGED_ACTION = "com.sec.android.WIFI_WCM_STATE_CHANGED_ACTION";
    private final Context mContext;
    private final ISemWifiManager mService;
    public static final boolean MHSDBG = SemWifiApCust.DBG;
    private static final SparseArray<ISemAbTestConfigurationUpdateObserver> sSemAbTestConfigurationUpdateObserverMap = new SparseArray<>();

    public static class AbTestConfigUpdateObserver {
        public void onRegistered(AbTestConfigSubscription abTestConfigSubscription) {
        }

        public void onUpdated(SemAbTestConfiguration semAbTestConfiguration) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface BandType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HotspotAntennaMode {
    }

    public static class IWC {
        public static final int BNR_ENABLE_SWITCH_TO_MOBILE_AFTER_RESTORE = 4;
        public static final int BNR_RESTORE_LEARNING_FIELD = 3;
        public static final int BNR_SKIP_BACKUP_VALUE = 5;
        public static final int BNR_SWITCH_TO_MOBILE = 1;
        public static final int BNR_SWITCH_TO_MOBILE_AGG = 2;

        @Retention(RetentionPolicy.SOURCE)
        public @interface IwcSettingType {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OptimizerMode {
    }

    public static class SemWifiApLogger {
        public static final String HOTSPOT_LAB_PATH = "/data/misc/wifi_hostapd/hotspotLabs.txt";
        public static final String KEY_BAND = "Band = ";
        public static final String KEY_BSSID = "Bssid =";
        public static final String KEY_CALLING_PACKAGE_NAME = "CallingPackage = ";
        public static final String KEY_CHANNEL = "Channel = ";
        public static final String KEY_CLOUD_BACKUP = "CloudBackUp = ";
        public static final String KEY_CLOUD_RESTORE = "CloudRestore = ";
        public static final String KEY_CONNECTION_TYPE = "Connection type =";
        public static final String KEY_DATA_LIMIT_IN_BYTES = "DataLimitInBytes =";
        public static final String KEY_DATE_TIME = "DateTime = ";
        public static final String KEY_DEVICE_TYPE = "Device type =";
        public static final String KEY_DHCP_NAME = "DHCP name =";
        public static final String KEY_EVENT = "Event =";
        public static final String KEY_FREQ = "Freq =";
        public static final String KEY_IFACE = "iface =";
        public static final String KEY_INFO_DETAILS = "Info details =";
        public static final String KEY_INTERFACE = "Interface =";
        public static final String KEY_IP = "Ip =";
        public static final String KEY_IP_TYPE = "Ip type =";
        public static final String KEY_IS_HIDDEN = "IsHidden = ";
        public static final String KEY_IS_PMF_ENABLED = "IsPmfEnabled = ";
        public static final String KEY_IS_POWER_SAVING_ENABLED = "IsPowerSavingEnabled = ";
        public static final String KEY_IS_WIFI_6_SUPPORTED_ENABLED = "IsWifi6SupportedEnabled = ";
        public static final String KEY_IS_WIFI_SHARING_ENABLED = "IsWifiSharingEnabled = ";
        public static final String KEY_LOG_TYPE = "LogType = ";
        public static final String KEY_LOG_VERSION = "LogVersion = ";
        public static final String KEY_MAC = "Mac =";
        public static final String KEY_MAC_TYPE = "MacType = ";
        public static final String KEY_MAX_CLIENT = "Max client = ";
        public static final String KEY_MODE = "Mode =";
        public static final String KEY_MORE_INFO = "More Info = ";
        public static final String KEY_NAME = "Name =";
        public static final String KEY_NAME_TYPE = "Name type =";
        public static final String KEY_NSD_NAME = "NSD name =";
        public static final String KEY_PASSPHRASE = "PassPhrase = ";
        public static final String KEY_PAUSE_SHARING = "PauseSharing =";
        public static final String KEY_PROVISIONING_SUCCESS_STATE = "Provisioning success state =";
        public static final String KEY_REASON = "Reason = ";
        public static final String KEY_SECURITY_TYPE = "SecurityType = ";
        public static final String KEY_SMART_SWITCH_BACKUP = "SmartSwitchBackUp = ";
        public static final String KEY_SMART_SWITCH_RESTORE = "SmartSwitchRestore = ";
        public static final String KEY_SOFT_AP_INFO = "SoftApInfo ======>";
        public static final String KEY_SSID = "Ssid = ";
        public static final String KEY_STATE = "State =";
        public static final String KEY_TAG_NAME = "TagName = ";
        public static final String KEY_TIME = "Time = ";
        public static final String KEY_TIME_IN_MILLIS = "TimeInMillis = ";
        public static final String KEY_TIME_LIMIT_IN_MILLIS = "TimeLimitInMillis =";
        public static final String KEY_TIME_OUT = "TimeOut = ";
        public static final String KEY_UPSTREAM_TYPE_CHANGED = "Upstream type changed =";
        public static final String KEY_VALUE = "Value =";
        public static final String KEY_VERSION = "Version =";
        public static final String PATTERN_SEPARATOR = ",, ";
        public static final String PATTERN_SEPARATOR_EVENT_CONTENTS = "===>>>";
        public static final String SETTINGS_SECURE_KEY_CLOUD_BACKUP_RESTORING = "wifi_ap_settings_cloud_backup_restoring";
        public static final String SETTINGS_SECURE_KEY_SMART_SWITCH_RESTORING = "wifi_ap_settings_smart_switch_restoring";
        public static final String TAG_D = "[D]";
        public static final String TAG_E = "[E]";
        public static final String TAG_I = "[I]";
        public static final String TAG_WIFI_AP_LAB_CHANNEL_SWITCH_EVENT = "#tag_wifi_ap_lab_channel_switch_event#";
        public static final String TAG_WIFI_AP_LAB_CLIENT_EVENT = "#tag_wifi_ap_lab_client_event#";
        public static final String TAG_WIFI_AP_LAB_CONFIG_EVENT = "#tag_wifi_ap_lab_config_event#";
        public static final String TAG_WIFI_AP_LAB_HOTSPOT_CONNECTION_EVENT = "#tag_wifi_ap_lab_hotspot_connection_event#";
        public static final String TAG_WIFI_AP_LAB_HOTSPOT_SPEED_EVENT = "#tag_wifi_ap_lab_hotspot_speed_event#";
        public static final String VALUE_AUTO_HOTSPOT = "Auto Hotspot";
        public static final String VALUE_CELLULAR = "Cellular";
        public static final String VALUE_CLIENT_DISCONNECTED = "Client disconnected";
        public static final String VALUE_CLOUD_BACKUP = "Cloud BackUp";
        public static final String VALUE_CLOUD_RESTORE = "Cloud Restore";
        public static final String VALUE_HOTSPOT_CHANNEL_SWITCH = "Hotspot channel switch";
        public static final String VALUE_HOTSPOT_OFF = "Hotspot off";
        public static final String VALUE_HOTSPOT_ON = "Hotspot on";
        public static final String VALUE_LIMIT_REMOVED = "[Limit Removed]";
        public static final String VALUE_NORMAL = "Normal";
        public static final String VALUE_NO_UPSTREAM = "No upstream";
        public static final String VALUE_OTP = "Otp";
        public static final String VALUE_OVERALL_CLIENTS_SETTINGS_UPDATED = "Overall clients settings updated";
        public static final String VALUE_SMART_SWITCH_BACKUP = "SmartSwitch BackUp";
        public static final String VALUE_SMART_SWITCH_RESTORE = "SmartSwitch Restore";
        public static final String VALUE_WIFI = "Wi-Fi";
        public static final String VALUE_WIFI_CONNECTED = "Wi-Fi network connected";
        public static final String VALUE_WIFI_DISCONNECTED = "Wi-Fi network disconnected";
        public static final String VALUE_WIFI_OFF = "Wi-Fi off";
        public static final String VALUE_WIFI_ON = "Wi-Fi on";
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TasPolicy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TestModuleId {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface WifiUwbCoexStatusCode {
    }

    public SemWifiManager(Context context, ISemWifiManager iSemWifiManager, Looper looper) {
        this.mContext = context;
        this.mService = iSemWifiManager;
    }

    public void setMaxDtimInSuspendMode(boolean z) {
        try {
            this.mService.setMaxDtimInSuspendMode(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDtimInSuspendMode(int i) {
        try {
            this.mService.setDtimInSuspendMode(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void logWifiAp(String str, String str2, String str3) {
        try {
            this.mService.logWifiAp(str, str2, str3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getMHSClientTrafficDetails() {
        try {
            return this.mService.getMHSClientTrafficDetails();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getNRTTrafficbandwidth() {
        try {
            return this.mService.getNRTTrafficbandwidth();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long[] getDataConsumedValues() {
        try {
            return this.mService.getDataConsumedValues();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetTotalPriorityDataConsumedValues() {
        try {
            this.mService.resetTotalPriorityDataConsumedValues();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<SemWifiApBleScanResult> getWifiApBleScanDetail() {
        try {
            return this.mService.getWifiApBleScanDetail();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean wifiApBleClientRole(boolean z) {
        try {
            return this.mService.wifiApBleClientRole(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean wifiApBleMhsRole(boolean z) {
        try {
            return this.mService.wifiApBleMhsRole(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean connectToSmartMHS(String str, int i, int i2, int i3, String str2, String str3, int i4, boolean z) {
        try {
            return this.mService.connectToSmartMHS(str, i, i2, i3, str2, str3, i4, z);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void requestStopAutohotspotAdvertisement(boolean z) {
        try {
            this.mService.requestStopAutohotspotAdvertisement(z);
        } catch (RemoteException unused) {
        }
    }

    public int getAdvancedAutohotspotConnectSettings() {
        try {
            return this.mService.getAdvancedAutohotspotConnectSettings();
        } catch (RemoteException unused) {
            return -1;
        }
    }

    public void setAutohotspotToastMessage(int i) {
        try {
            this.mService.setAutohotspotToastMessage(i);
        } catch (RemoteException unused) {
        }
    }

    public void setAdvancedAutohotspotConnectSettings(int i) {
        try {
            this.mService.setAdvancedAutohotspotConnectSettings(i);
        } catch (RemoteException unused) {
        }
    }

    public int getAdvancedAutohotspotLCDSettings() {
        try {
            return this.mService.getAdvancedAutohotspotLCDSettings();
        } catch (RemoteException unused) {
            return -1;
        }
    }

    public void setAdvancedAutohotspotLCDSettings(int i) {
        try {
            this.mService.setAdvancedAutohotspotLCDSettings(i);
        } catch (RemoteException unused) {
        }
    }

    public void setWifiSettingsForegroundState(int i) {
        try {
            this.mService.setWifiSettingsForegroundState(i);
        } catch (RemoteException unused) {
        }
    }

    public void clearAutoHotspotLists() {
        try {
            this.mService.clearAutoHotspotLists();
        } catch (RemoteException unused) {
        }
    }

    public void setWifiApWarningActivityRunning(int i) {
        try {
            this.mService.setWifiApWarningActivityRunning(i);
        } catch (RemoteException unused) {
        }
    }

    public int getWifiApWarningActivityRunningState() {
        try {
            return this.mService.getWifiApWarningActivityRunningState();
        } catch (RemoteException unused) {
            return 0;
        }
    }

    public List<SemWifiApBleScanResult> getWifiApBleD2DScanDetail() {
        try {
            return this.mService.getWifiApBleD2DScanDetail();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean wifiApBleD2DClientRole(boolean z) {
        try {
            return this.mService.wifiApBleD2DClientRole(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean wifiApBleD2DMhsRole(boolean z) {
        try {
            return this.mService.wifiApBleD2DMhsRole(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean connectToSmartD2DClient(String str, String str2, SemWifiApSmartCallback semWifiApSmartCallback) {
        try {
            SemWifiApSmartCallback.SemWifiApSmartCallbackProxy proxy = semWifiApSmartCallback.getProxy();
            proxy.initProxy(this.mContext.getMainExecutor(), semWifiApSmartCallback);
            return this.mService.connectToSmartD2DClient(str, str2, proxy);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getSmartD2DClientConnectedStatus(String str) {
        try {
            return this.mService.getSmartD2DClientConnectedStatus(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static abstract class SemWifiApSmartCallback {
        private final SemWifiApSmartCallbackProxy mSemWifiApSmartCallbackProxy = new SemWifiApSmartCallbackProxy();

        public abstract void onStateChanged(int i, String str);

        SemWifiApSmartCallbackProxy getProxy() {
            return this.mSemWifiApSmartCallbackProxy;
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class SemWifiApSmartCallbackProxy extends ISemWifiApSmartCallback.Stub {
            private final Object mLock = new Object();
            private Executor mExecutor = null;
            private SemWifiApSmartCallback mCallback = null;

            SemWifiApSmartCallbackProxy() {
            }

            void initProxy(Executor executor, SemWifiApSmartCallback semWifiApSmartCallback) {
                synchronized (this.mLock) {
                    this.mExecutor = executor;
                    this.mCallback = semWifiApSmartCallback;
                }
            }

            void cleanUpProxy() {
                synchronized (this.mLock) {
                    this.mExecutor = null;
                    this.mCallback = null;
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiApSmartCallback
            public void onStateChanged(final int i, final String str) throws RemoteException {
                Executor executor;
                final SemWifiApSmartCallback semWifiApSmartCallback;
                Log.v(SemWifiManager.TAG, "SemWifiApSmartCallbackProxy: onStateChanged: state=" + i);
                synchronized (this.mLock) {
                    executor = this.mExecutor;
                    semWifiApSmartCallback = this.mCallback;
                }
                if (semWifiApSmartCallback == null || executor == null) {
                    return;
                }
                Binder.clearCallingIdentity();
                executor.execute(new Runnable() { // from class: com.samsung.android.wifi.SemWifiManager$SemWifiApSmartCallback$SemWifiApSmartCallbackProxy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemWifiManager.SemWifiApSmartCallback.this.onStateChanged(i, str);
                    }
                });
            }
        }
    }

    public static abstract class SemWifiApClientListUpdateCallback {
        private final SemWifiApClientListUpdateCallbackProxy mSemWifiApClientListUpdateCallbackProxy = new SemWifiApClientListUpdateCallbackProxy();

        public abstract void onClientListUpdated(List<SemWifiApClientDetails> list, long j);

        public abstract void onOverallDataLimitChanged(long j);

        SemWifiApClientListUpdateCallbackProxy getProxy() {
            return this.mSemWifiApClientListUpdateCallbackProxy;
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class SemWifiApClientListUpdateCallbackProxy extends ISemWifiApClientListUpdateCallback.Stub {
            private final Object mLock = new Object();
            private Executor mExecutor = null;
            private SemWifiApClientListUpdateCallback mCallback = null;

            SemWifiApClientListUpdateCallbackProxy() {
            }

            void initProxy(Executor executor, SemWifiApClientListUpdateCallback semWifiApClientListUpdateCallback) {
                synchronized (this.mLock) {
                    this.mExecutor = executor;
                    this.mCallback = semWifiApClientListUpdateCallback;
                }
            }

            void cleanUpProxy() {
                synchronized (this.mLock) {
                    this.mExecutor = null;
                    this.mCallback = null;
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiApClientListUpdateCallback
            public void onClientListUpdated(final List<SemWifiApClientDetails> list, final long j) throws RemoteException {
                Executor executor;
                final SemWifiApClientListUpdateCallback semWifiApClientListUpdateCallback;
                Log.v(SemWifiManager.TAG, "onClientListUpdated: " + list.toString() + ", totalDatausage = " + j);
                synchronized (this.mLock) {
                    executor = this.mExecutor;
                    semWifiApClientListUpdateCallback = this.mCallback;
                }
                if (semWifiApClientListUpdateCallback == null || executor == null) {
                    return;
                }
                Binder.clearCallingIdentity();
                executor.execute(new Runnable() { // from class: com.samsung.android.wifi.SemWifiManager$SemWifiApClientListUpdateCallback$SemWifiApClientListUpdateCallbackProxy$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemWifiManager.SemWifiApClientListUpdateCallback.this.onClientListUpdated(list, j);
                    }
                });
            }

            @Override // com.samsung.android.wifi.ISemWifiApClientListUpdateCallback
            public void onOverallDataLimitChanged(final long j) throws RemoteException {
                Executor executor;
                final SemWifiApClientListUpdateCallback semWifiApClientListUpdateCallback;
                Log.v(SemWifiManager.TAG, "onOverallDataLimitChanged: datalimit = " + j);
                synchronized (this.mLock) {
                    executor = this.mExecutor;
                    semWifiApClientListUpdateCallback = this.mCallback;
                }
                if (semWifiApClientListUpdateCallback == null || executor == null) {
                    return;
                }
                Binder.clearCallingIdentity();
                executor.execute(new Runnable() { // from class: com.samsung.android.wifi.SemWifiManager$SemWifiApClientListUpdateCallback$SemWifiApClientListUpdateCallbackProxy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemWifiManager.SemWifiApClientListUpdateCallback.this.onOverallDataLimitChanged(j);
                    }
                });
            }
        }
    }

    public static abstract class SemWifiApClientUpdateCallback {
        private final SemWifiApClientUpdateCallbackProxy mSemWifiApClientUpdateCallbackProxy = new SemWifiApClientUpdateCallbackProxy();

        public abstract void onClientUpdated(SemWifiApClientDetails semWifiApClientDetails);

        SemWifiApClientUpdateCallbackProxy getProxy() {
            return this.mSemWifiApClientUpdateCallbackProxy;
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class SemWifiApClientUpdateCallbackProxy extends ISemWifiApClientUpdateCallback.Stub {
            private final Object mLock = new Object();
            private Executor mExecutor = null;
            private SemWifiApClientUpdateCallback mCallback = null;

            SemWifiApClientUpdateCallbackProxy() {
            }

            void initProxy(Executor executor, SemWifiApClientUpdateCallback semWifiApClientUpdateCallback) {
                synchronized (this.mLock) {
                    this.mExecutor = executor;
                    this.mCallback = semWifiApClientUpdateCallback;
                }
            }

            void cleanUpProxy() {
                synchronized (this.mLock) {
                    this.mExecutor = null;
                    this.mCallback = null;
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiApClientUpdateCallback
            public void onClientUpdated(final SemWifiApClientDetails semWifiApClientDetails) throws RemoteException {
                Executor executor;
                final SemWifiApClientUpdateCallback semWifiApClientUpdateCallback;
                Log.v(SemWifiManager.TAG, "onClientUpdated: " + semWifiApClientDetails.toString());
                synchronized (this.mLock) {
                    executor = this.mExecutor;
                    semWifiApClientUpdateCallback = this.mCallback;
                }
                if (semWifiApClientUpdateCallback == null || executor == null) {
                    return;
                }
                Binder.clearCallingIdentity();
                executor.execute(new Runnable() { // from class: com.samsung.android.wifi.SemWifiManager$SemWifiApClientUpdateCallback$SemWifiApClientUpdateCallbackProxy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemWifiManager.SemWifiApClientUpdateCallback.this.onClientUpdated(semWifiApClientDetails);
                    }
                });
            }
        }
    }

    public int getSmartMHSLockStatus() {
        try {
            return this.mService.getSmartMHSLockStatus();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int canSmartMHSLocked() {
        try {
            return this.mService.canSmartMHSLocked();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int setSmartMHSLocked(int i) {
        try {
            return this.mService.setSmartMHSLocked(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void isClientAcceptedWifiProfileSharing(boolean z) {
        try {
            this.mService.isClientAcceptedWifiProfileSharing(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerWifiApSmartCallback(SemWifiApSmartCallback semWifiApSmartCallback, Executor executor) {
        if (semWifiApSmartCallback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        if (executor == null) {
            throw new IllegalArgumentException("executor cannot be null");
        }
        Log.v(TAG, "registerWifiApSmartCallback: callback=" + semWifiApSmartCallback + ", executor=" + executor);
        SemWifiApSmartCallback.SemWifiApSmartCallbackProxy proxy = semWifiApSmartCallback.getProxy();
        proxy.initProxy(executor, semWifiApSmartCallback);
        try {
            this.mService.registerWifiApSmartCallback(new Binder(), proxy, semWifiApSmartCallback.hashCode());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterWifiApSmartCallback(SemWifiApSmartCallback semWifiApSmartCallback) {
        if (semWifiApSmartCallback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        Log.v(TAG, "unregisterWifiApSmartCallback: callback=" + semWifiApSmartCallback + "callid : " + semWifiApSmartCallback.hashCode());
        SemWifiApSmartCallback.SemWifiApSmartCallbackProxy proxy = semWifiApSmartCallback.getProxy();
        try {
            try {
                this.mService.unregisterWifiApSmartCallback(semWifiApSmartCallback.hashCode());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } finally {
            proxy.cleanUpProxy();
        }
    }

    public int getSmartApConnectedStatusFromScanResult(String str) {
        try {
            return this.mService.getSmartApConnectedStatusFromScanResult(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isMCFClientAutohotspotSupported() {
        try {
            return this.mService.isMCFClientAutohotspotSupported();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<SemWifiApBleScanResult> getMcfScanDetail() {
        try {
            return this.mService.getMcfScanDetail();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int startMcfClientMHSDiscovery(boolean z) {
        try {
            return this.mService.startMcfClientMHSDiscovery(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int startMcfMHSAdvertisement(boolean z) {
        try {
            return this.mService.startMcfMHSAdvertisement(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int connectToMcfMHS(String str, int i, int i2, int i3, String str2, String str3, int i4) {
        try {
            return this.mService.connectToMcfMHS(str, i, i2, i3, str2, str3, i4);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMcfConnectedStatus(String str) {
        try {
            return this.mService.getMcfConnectedStatus(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMcfConnectedStatusFromScanResult(String str) {
        try {
            return this.mService.getMcfConnectedStatusFromScanResult(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getWifiApHostapdFreq() {
        try {
            return this.mService.getWifiApHostapdFreq();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getWifiApHostapdSecurtiy() {
        try {
            return this.mService.getWifiApHostapdSecurtiy();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int autohotspotWifiScanConnect(String str, String str2, String str3, int i, int i2, int i3) {
        try {
            return this.mService.autohotspotWifiScanConnect(str, str2, str3, i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiApClientMobileDataLimit(String str, long j) {
        try {
            this.mService.setWifiApClientMobileDataLimit(str, j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiApClientTimeLimit(String str, long j) {
        try {
            this.mService.setWifiApClientTimeLimit(str, j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiApClientDataPaused(String str, boolean z) {
        try {
            this.mService.setWifiApClientDataPaused(str, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiApClientEditedName(String str, String str2) {
        try {
            this.mService.setWifiApClientEditedName(str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public SemWifiApClientDetails getWifiApClientDetails(String str) {
        try {
            return this.mService.getWifiApClientDetails(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<SemWifiApClientDetails> getTopHotspotClientsToday(int i, int i2) {
        try {
            return this.mService.getTopHotspotClientsToday(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getTopHotspotClientsTodayAsString(int i, int i2) {
        try {
            return this.mService.getTopHotspotClientsTodayAsString(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getWifiApTodaysTotalDataUsage() {
        try {
            return this.mService.getWifiApTodaysTotalDataUsage();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getWifiApDailyDataLimit() {
        try {
            return this.mService.getWifiApDailyDataLimit();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiApDailyDataLimit(long j) {
        try {
            this.mService.setWifiApDailyDataLimit(j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiApGuestPassword(String str) {
        try {
            this.mService.setWifiApGuestPassword(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getWifiApGuestPassword() {
        try {
            return this.mService.getWifiApGuestPassword();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isWifiApGuestModeEnabled() {
        try {
            return this.mService.isWifiApGuestModeEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiApGuestModeEnabled(boolean z) {
        try {
            this.mService.setWifiApGuestModeEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isWifiApGuestModeIsolationEnabled() {
        try {
            return this.mService.isWifiApGuestModeIsolationEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiApGuestModeIsolationEnabled(boolean z) {
        try {
            this.mService.setWifiApGuestModeIsolationEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getTotalAndTop3ClientsDataUsageBetweenGivenDates(long j, long j2) {
        try {
            return this.mService.getTotalAndTop3ClientsDataUsageBetweenGivenDates(j, j2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getMonthlyDataUsage() {
        try {
            return this.mService.getMonthlyDataUsage();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isOverAllMhsDataLimitReached() {
        try {
            return this.mService.isOverAllMhsDataLimitReached();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isOverAllMhsDataLimitSet() {
        try {
            return this.mService.isOverAllMhsDataLimitSet();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String wifiApBackUpClientDataUsageSettingsInfo() {
        try {
            return this.mService.wifiApBackUpClientDataUsageSettingsInfo();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void wifiApRestoreClientDataUsageSettingsInfo(String str) {
        try {
            this.mService.wifiApRestoreClientDataUsageSettingsInfo(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void wifiApRestoreDailyHotspotDataLimit(long j) {
        try {
            this.mService.wifiApRestoreDailyHotspotDataLimit(j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerClientListDataUsageCallback(SemWifiApClientListUpdateCallback semWifiApClientListUpdateCallback, Executor executor, int i, int i2) {
        if (semWifiApClientListUpdateCallback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        if (executor == null) {
            throw new IllegalArgumentException("executor cannot be null");
        }
        Log.v(TAG, "registerClientListDataUsageCallback: callback=" + semWifiApClientListUpdateCallback + ", executor=" + executor);
        SemWifiApClientListUpdateCallback.SemWifiApClientListUpdateCallbackProxy proxy = semWifiApClientListUpdateCallback.getProxy();
        proxy.initProxy(executor, semWifiApClientListUpdateCallback);
        try {
            this.mService.registerClientListDataUsageCallback(new Binder(), proxy, semWifiApClientListUpdateCallback.hashCode(), i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterClientListDataUsageCallback(SemWifiApClientListUpdateCallback semWifiApClientListUpdateCallback) {
        if (semWifiApClientListUpdateCallback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        Log.v(TAG, "unregisterClientListDataUsageCallback: callback=" + semWifiApClientListUpdateCallback + "callid : " + semWifiApClientListUpdateCallback.hashCode());
        SemWifiApClientListUpdateCallback.SemWifiApClientListUpdateCallbackProxy proxy = semWifiApClientListUpdateCallback.getProxy();
        try {
            try {
                this.mService.unregisterClientListDataUsageCallback(semWifiApClientListUpdateCallback.hashCode());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } finally {
            proxy.cleanUpProxy();
        }
    }

    public void registerClientDataUsageCallback(SemWifiApClientUpdateCallback semWifiApClientUpdateCallback, Executor executor, String str) {
        if (semWifiApClientUpdateCallback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        if (executor == null) {
            throw new IllegalArgumentException("executor cannot be null");
        }
        Log.v(TAG, "registerClientDataUsageCallback: callback=" + semWifiApClientUpdateCallback + ", executor=" + executor);
        SemWifiApClientUpdateCallback.SemWifiApClientUpdateCallbackProxy proxy = semWifiApClientUpdateCallback.getProxy();
        proxy.initProxy(executor, semWifiApClientUpdateCallback);
        try {
            this.mService.registerClientDataUsageCallback(new Binder(), proxy, semWifiApClientUpdateCallback.hashCode(), str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterClientDataUsageCallback(SemWifiApClientUpdateCallback semWifiApClientUpdateCallback) {
        if (semWifiApClientUpdateCallback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        Log.v(TAG, "unregisterClientDataUsageCallback: callback=" + semWifiApClientUpdateCallback + "callid : " + semWifiApClientUpdateCallback.hashCode());
        SemWifiApClientUpdateCallback.SemWifiApClientUpdateCallbackProxy proxy = semWifiApClientUpdateCallback.getProxy();
        try {
            try {
                this.mService.unregisterClientDataUsageCallback(semWifiApClientUpdateCallback.hashCode());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } finally {
            proxy.cleanUpProxy();
        }
    }

    public static abstract class SemWifiApDataUsageListener {
        private final SemWifiApDataUsageClient mSemWifiApDataUsageClient = new SemWifiApDataUsageClient();

        public abstract void onDataUsageChanged(String str);

        SemWifiApDataUsageClient getClient() {
            return this.mSemWifiApDataUsageClient;
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class SemWifiApDataUsageClient extends ISemWifiApDataUsageCallback.Stub {
            private final Object mLock = new Object();
            private Executor mExecutor = null;
            private SemWifiApDataUsageListener mListener = null;

            SemWifiApDataUsageClient() {
            }

            void init(Executor executor, SemWifiApDataUsageListener semWifiApDataUsageListener) {
                synchronized (this.mLock) {
                    this.mExecutor = executor;
                    this.mListener = semWifiApDataUsageListener;
                }
            }

            void cleanUp() {
                synchronized (this.mLock) {
                    this.mExecutor = null;
                    this.mListener = null;
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiApDataUsageCallback
            public void onDataUsageChanged(final String str) {
                Executor executor;
                final SemWifiApDataUsageListener semWifiApDataUsageListener;
                Log.v(SemWifiManager.TAG, "onDataUsageChanged:" + str);
                synchronized (this.mLock) {
                    executor = this.mExecutor;
                    semWifiApDataUsageListener = this.mListener;
                }
                if (semWifiApDataUsageListener == null || executor == null) {
                    return;
                }
                Binder.clearCallingIdentity();
                executor.execute(new Runnable() { // from class: com.samsung.android.wifi.SemWifiManager$SemWifiApDataUsageListener$SemWifiApDataUsageClient$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemWifiManager.SemWifiApDataUsageListener.this.onDataUsageChanged(str);
                    }
                });
            }
        }
    }

    public void registerWifiApDataUsageListener(SemWifiApDataUsageListener semWifiApDataUsageListener, Executor executor) {
        if (semWifiApDataUsageListener == null) {
            throw new IllegalArgumentException("listener cannot be null");
        }
        if (executor == null) {
            throw new IllegalArgumentException("executor cannot be null");
        }
        Log.v(TAG, "registerApDataUsageChangedListener: listener=" + semWifiApDataUsageListener + ", executor=" + executor);
        SemWifiApDataUsageListener.SemWifiApDataUsageClient client = semWifiApDataUsageListener.getClient();
        client.init(executor, semWifiApDataUsageListener);
        try {
            this.mService.registerWifiApDataUsageCallback(new Binder(), client, semWifiApDataUsageListener.hashCode());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unRegisterWifiApDataUsageListener(SemWifiApDataUsageListener semWifiApDataUsageListener) {
        if (semWifiApDataUsageListener == null) {
            throw new IllegalArgumentException("listener cannot be null");
        }
        Log.v(TAG, "unRegisterWifiApDataUsageListener: listener=" + semWifiApDataUsageListener + "callid : " + semWifiApDataUsageListener.hashCode());
        SemWifiApDataUsageListener.SemWifiApDataUsageClient client = semWifiApDataUsageListener.getClient();
        try {
            try {
                this.mService.unRegisterWifiApDataUsageCallback(semWifiApDataUsageListener.hashCode());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } finally {
            client.cleanUp();
        }
    }

    public int getSmartApConnectedStatus(String str) {
        try {
            return this.mService.getSmartApConnectedStatus(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUsingNonTerrestrialNetwork() {
        try {
            return this.mService.isUsingNonTerrestrialNetwork();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getWifiApState() {
        try {
            return this.mService.getWifiApState();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated(forRemoval = false, since = "16.0")
    public boolean isWifiSharingEnabled() {
        try {
            return this.mService.isWifiSharingEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public SoftApConfiguration getSoftApConfiguration() {
        try {
            return this.mService.getSoftApConfiguration();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getSoftApBands() {
        try {
            return this.mService.getSoftApBands();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getSoftApSecurityType() {
        try {
            return this.mService.getSoftApSecurityType();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int isDataSaverEnabled() {
        try {
            return this.mService.isDataSaverEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int isSoftap11axEnabled() {
        try {
            return this.mService.isSoftap11axEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int isSoftAp6ENetwork() {
        try {
            return this.mService.isSoftAp6ENetwork();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isNeededToShowWifiApDatalimitReachedDialog() {
        try {
            return this.mService.isNeededToShowWifiApDatalimitReachedDialog();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void launchWifiApWarningForMcfMHS(int i, int i2, boolean z) {
        try {
            this.mService.launchWifiApWarningForMcfMHS(i, i2, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getSoftApUpStreamNetworkType() {
        try {
            return this.mService.getSoftApUpStreamNetworkType();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getMHSMacFromInterface() {
        try {
            return this.mService.getMHSMacFromInterface();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getSoftApFreq() {
        try {
            return this.mService.getSoftApFreq();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getWifiMACAddress() {
        try {
            return this.mService.getWifiMACAddress();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canAutoHotspotBeEnabled() {
        try {
            return this.mService.canAutoHotspotBeEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isWifiApGuestClient(String str) {
        try {
            return this.mService.isWifiApGuestClient(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSAFamilySupportedBasedOnCountry() {
        try {
            return this.mService.isSAFamilySupportedBasedOnCountry();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isP2pConnected() {
        try {
            return this.mService.isP2pConnected();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getWifiSupportedFeatureSet() {
        try {
            return this.mService.getWifiSupportedFeatureSet();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isWifiApEnabledWithDualBand() {
        try {
            return this.mService.isWifiApEnabledWithDualBand();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setArdkPowerSaveMode(boolean z) {
        try {
            this.mService.setArdkPowerSaveMode(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSoftApConfiguration(SoftApConfiguration softApConfiguration) {
        try {
            this.mService.setSoftApConfiguration(softApConfiguration);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setLocalOnlyHotspotEnabled(boolean z, String str, String str2, int i) {
        Log.i(TAG, " setLocalOnlyHotspotEnabled : " + z);
        try {
            return this.mService.setLocalOnlyHotspotEnabled(z, str, str2, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setWifiApEnabled(SoftApConfiguration softApConfiguration, boolean z) {
        Log.i(TAG, " setWifiApEnabled:" + z + " package:" + this.mContext.getPackageName());
        if (MHSDBG) {
            Log.e(TAG, Log.getStackTraceString(new Throwable()));
        }
        try {
            return this.mService.setWifiApEnabled(softApConfiguration, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void insertHotSpotEnabledHistory(String str) {
        String packageName = this.mContext.getPackageName();
        Log.i(TAG, str + " setwifiap packageName : " + packageName);
        boolean z = MHSDBG;
        if (z) {
            Log.e(TAG, Log.getStackTraceString(new Throwable()));
        }
        Bundle bundle = new Bundle();
        StackTraceElement stackTraceElement = new Exception().getStackTrace()[3];
        CharSequence format = DateFormat.format("yy/MM/dd kk:mm:ss ", System.currentTimeMillis());
        if (z) {
            bundle.putString("extra_log", ((Object) format) + str + " setwifiap " + packageName + NavigationBarInflaterView.SIZE_MOD_START + stackTraceElement.getFileName() + ":" + stackTraceElement.getMethodName() + "():" + stackTraceElement.getLineNumber() + "]\n");
        } else {
            bundle.putString("extra_log", ((Object) format) + str + " setwifiap " + packageName + NavigationBarInflaterView.SIZE_MOD_START + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + "]\n");
        }
        reportHotspotDumpLogs(bundle.getString("extra_log"));
    }

    public String getAntInfo() {
        try {
            return this.mService.getAntInfo();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getFrameburstInfo() {
        try {
            return this.mService.getFrameburstInfo();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getPsmInfo() {
        try {
            return this.mService.getPsmInfo();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getVendorWlanDriverProp(String str) {
        try {
            return this.mService.getVendorWlanDriverProp(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setVendorWlanDriverProp(String str, String str2) {
        try {
            return this.mService.setVendorWlanDriverProp(str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeFactoryMacAddress() {
        try {
            return this.mService.removeFactoryMacAddress();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setFactoryMacAddress(String str) {
        try {
            return this.mService.setFactoryMacAddress(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setFccChannelBackoffEnabled(String str, boolean z) {
        try {
            this.mService.setFccChannelBackoffEnabled(str, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setPsmInfo(String str) {
        try {
            return this.mService.setPsmInfo(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setAntInfo(String str) {
        try {
            return this.mService.setAntInfo(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setFrameburstInfo(String str) {
        try {
            return this.mService.setFrameburstInfo(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void triggerBackoffRoutine(boolean z) {
        try {
            this.mService.triggerBackoffRoutine(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void set5GmmWaveSarBackoffEnabled(boolean z) {
        try {
            this.mService.set5GmmWaveSarBackoffEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getWifiApMaxClient() {
        try {
            return this.mService.getWifiApMaxClient();
        } catch (RemoteException unused) {
            Log.i(TAG, "getWifiApMaxClient() failed!");
            return 10;
        }
    }

    public boolean supportWifiAp5GBasedOnCountry() {
        try {
            return this.mService.supportWifiAp5GBasedOnCountry();
        } catch (RemoteException unused) {
            Log.i(TAG, "supportWifiAp5GBasedOnCountry() failed!");
            return false;
        }
    }

    public boolean supportWifiAp6GBasedOnCountry() {
        try {
            return this.mService.supportWifiAp6GBasedOnCountry();
        } catch (RemoteException unused) {
            Log.i(TAG, "supportWifiAp6GBasedOnCountry() failed!");
            return false;
        }
    }

    public boolean isWifiApConcurrentSupported() {
        try {
            return this.mService.isWifiSharingSupported();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean isWifiSharingSupported() {
        try {
            return this.mService.isWifiSharingSupported();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean isThisSoftApFeatureSupported(int i) {
        try {
            return this.mService.isThisSoftApFeatureSupported(i);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean isWifiSharingLiteSupported() {
        try {
            return this.mService.isWifiSharingLiteSupported();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setWifiSharingEnabled(boolean z) {
        try {
            return this.mService.setWifiSharingEnabled(z);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setWifiApConfigurationToDefault() {
        try {
            this.mService.setWifiApConfigurationToDefault();
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public String getWifiApStaList() {
        try {
            return this.mService.getWifiApStaList();
        } catch (RemoteException unused) {
            return null;
        }
    }

    public String runIptablesRulesCommand(String str) {
        try {
            return this.mService.runIptablesRulesCommand(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getWifiApStaListDetail() {
        try {
            return this.mService.getWifiApStaListDetail();
        } catch (RemoteException unused) {
            return null;
        }
    }

    public int getWifiApChannel() {
        try {
            return this.mService.getWifiApChannel();
        } catch (RemoteException unused) {
            return -1;
        }
    }

    public String getStationInfo(String str) {
        try {
            return this.mService.getStationInfo(str);
        } catch (RemoteException unused) {
            return null;
        }
    }

    public int getWifiApFreq() {
        try {
            return this.mService.getWifiApFreq();
        } catch (RemoteException unused) {
            return -1;
        }
    }

    public void setAntMode(int i) {
        try {
            this.mService.setAntMode(i);
        } catch (RemoteException unused) {
        }
    }

    public void setHotspotAntMode(int i) {
        try {
            this.mService.setHotspotAntMode(i);
        } catch (RemoteException unused) {
        }
    }

    public int getHotspotAntMode() {
        try {
            return this.mService.getHotspotAntMode();
        } catch (RemoteException unused) {
            return -1;
        }
    }

    public void setPowerSavingTime(int i) {
        try {
            this.mService.setPowerSavingTime(i);
        } catch (RemoteException unused) {
        }
    }

    public JSONObject setMHSConfig(JSONObject jSONObject) {
        try {
            return new JSONObject(this.mService.setMHSConfig(jSONObject.toString()));
        } catch (Exception unused) {
            return null;
        }
    }

    public JSONObject getMHSConfig(JSONObject jSONObject) {
        try {
            return new JSONObject(this.mService.getMHSConfig(jSONObject.toString()));
        } catch (Exception unused) {
            return null;
        }
    }

    public int manageWifiApMacAclList(String str, String str2, int i, int i2) {
        try {
            return this.mService.manageWifiApMacAclList(str, str2, i, i2);
        } catch (RemoteException unused) {
            return -1;
        }
    }

    public List<String> readWifiApMacAclList(int i) {
        try {
            return this.mService.readWifiApMacAclList(i);
        } catch (RemoteException unused) {
            return null;
        }
    }

    public void setWifiApMacAclMode(int i) {
        try {
            this.mService.setWifiApMacAclMode(i);
        } catch (RemoteException unused) {
        }
    }

    public int getWifiApMacAclMode() {
        try {
            return this.mService.getWifiApMacAclMode();
        } catch (RemoteException unused) {
            return 0;
        }
    }

    public boolean isWifiApMacAclEnabled() {
        try {
            return this.mService.isWifiApMacAclEnabled();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void setWifiApMacAclEnable(boolean z) {
        try {
            this.mService.setWifiApMacAclEnable(z);
        } catch (RemoteException unused) {
        }
    }

    public void setWifiApWpsPbc(boolean z) {
        try {
            this.mService.setWifiApWpsPbc(z);
        } catch (RemoteException unused) {
        }
    }

    public boolean getWifiApWpsPbc() {
        try {
            return this.mService.getWifiApWpsPbc();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void setWifiApIsolate(boolean z) {
        try {
            this.mService.setWifiApIsolate(z);
        } catch (RemoteException unused) {
        }
    }

    public boolean getWifiApIsolate() {
        try {
            return this.mService.getWifiApIsolate();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void updateHostapdMacList(int i) {
        try {
            this.mService.updateHostapdMacList(i);
        } catch (RemoteException unused) {
        }
    }

    public String getWifiApInterfaceName() {
        try {
            return this.mService.getWifiApInterfaceName();
        } catch (RemoteException unused) {
            return null;
        }
    }

    public List<String> getWifiApInterfaceNames() {
        try {
            return this.mService.getWifiApInterfaceNames();
        } catch (RemoteException unused) {
            return new ArrayList();
        }
    }

    public boolean setProvisionSuccess(boolean z) {
        try {
            return this.mService.setProvisionSuccess(z);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void setWifiApMaxClientToFramework(int i) {
        try {
            this.mService.setWifiApMaxClientToFramework(i);
        } catch (RemoteException unused) {
        }
    }

    public int getWifiApMaxClientFromFramework() {
        try {
            return this.mService.getWifiApMaxClientFromFramework();
        } catch (RemoteException unused) {
            return 10;
        }
    }

    public int getProvisionSuccess() {
        try {
            return this.mService.getProvisionSuccess();
        } catch (RemoteException unused) {
            return 0;
        }
    }

    public boolean isWifiApEnabled() {
        try {
            return this.mService.isWifiApEnabled();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public int getWifiApConnectedStationCount() {
        try {
            return this.mService.getWifiApConnectedStationCount();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getWifiApLOHSState() {
        try {
            return this.mService.getWifiApLOHSState();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getIndoorStatus() {
        try {
            return this.mService.getIndoorStatus();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRVFModeStatus() {
        try {
            return this.mService.getRVFModeStatus();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setRVFmodeStatus(int i) {
        try {
            this.mService.setRVFmodeStatus(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void wifiApDisassocSta(String str) {
        try {
            this.mService.wifiApDisassocSta(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiApMaxClient(int i) {
        try {
            this.mService.setWifiApMaxClient(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportHotspotDumpLogs(String str) {
        try {
            this.mService.reportHotspotDumpLogs(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetSoftAp(Message message) {
        try {
            this.mService.resetSoftAp(message);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setVerboseLoggingEnabled(boolean z) {
        try {
            this.mService.setVerboseLoggingEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiDeveloperModeEnabled(boolean z) {
        try {
            this.mService.setWifiDeveloperModeEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isWifiDeveloperModeEnabled() {
        try {
            return this.mService.isWifiDeveloperModeEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isWifiApWpa3Supported() {
        try {
            return this.mService.isWifiApWpa3Supported();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public String getWifiFirmwareVersion() {
        try {
            return this.mService.getWifiFirmwareVersion();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getWifiCid() {
        try {
            return this.mService.getWifiCid();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getWifiVersions() {
        try {
            return this.mService.getWifiVersions();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getFactoryMacAddress() {
        try {
            return this.mService.getFactoryMacAddress();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportMHSBigData(String str, String str2) {
        try {
            this.mService.reportBigData(str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void blockFccChannelBackoff(boolean z) {
        try {
            this.mService.blockFccChannelBackoff(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void reportWifiOnOffEvent(boolean z, String str) {
        if (TextUtils.isEmpty(str)) {
            str = "unknown";
        }
        try {
            this.mService.addOrUpdateWifiControlHistory(str, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportRttStartRangingCallEvent(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "unknown";
        }
        reportMHSBigData("RAPP", str);
    }

    public String getWifiEnableHistory() {
        try {
            return this.mService.getWifiEnableHistory();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean addOrUpdateNetwork(SemWifiConfiguration semWifiConfiguration) {
        if (semWifiConfiguration == null) {
            return false;
        }
        try {
            return this.mService.addOrUpdateNetwork(semWifiConfiguration);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeNetwork(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            return this.mService.removeNetwork(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void factoryReset() {
        try {
            this.mService.factoryReset();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetDeveloperOptionsSettings() {
        try {
            this.mService.resetDeveloperOptionsSettings();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<SemWifiConfiguration> getConfiguredNetworks() {
        try {
            ParceledListSlice configuredNetworks = this.mService.getConfiguredNetworks();
            if (configuredNetworks == null) {
                return Collections.EMPTY_LIST;
            }
            return configuredNetworks.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void allowAutojoinPasspoint(String str, boolean z) {
        try {
            this.mService.allowAutojoinPasspoint(str, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<PasspointConfiguration> getPasspointConfigurations() {
        try {
            return this.mService.getPasspointConfigurations();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportIssue(int i, Bundle bundle) {
        if (bundle == null) {
            return;
        }
        try {
            this.mService.reportIssue(i, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getIssueDetectorDump(int i) {
        try {
            return this.mService.getIssueDetectorDump(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getSilentRoamingDump(int i) {
        try {
            return this.mService.getSilentRoamingDump(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startIssueMonitoring(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        try {
            this.mService.startIssueMonitoring(bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getConnectivityLog(String str) {
        try {
            return this.mService.getConnectivityLog(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateGuiderFeature(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        try {
            this.mService.updateGuiderFeature(bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getDiagnosisResults() {
        try {
            return this.mService.getDiagnosisResults();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Map<String, Map<String, Integer>> getQoSScores(List<String> list) {
        try {
            return this.mService.getQoSScores(list);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBtmOptionUserEnabled(String str) {
        try {
            this.mService.setBtmOptionUserEnabled(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBtmOptionUserDisabled(String str) {
        try {
            this.mService.setBtmOptionUserDisabled(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerPasswordCallback(String str, ISemSharedPasswordCallback iSemSharedPasswordCallback) {
        if (TextUtils.isEmpty(str) || iSemSharedPasswordCallback == null) {
            throw new IllegalArgumentException("request AP's bssid or callback should not be empty");
        }
        try {
            this.mService.registerPasswordCallback(str, iSemSharedPasswordCallback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterPasswordCallback(ISemSharedPasswordCallback iSemSharedPasswordCallback) {
        try {
            this.mService.unregisterPasswordCallback(iSemSharedPasswordCallback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestPassword(boolean z) {
        try {
            this.mService.requestPassword(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setUserConfirmForSharingPassword(boolean z, String str) {
        try {
            this.mService.setUserConfirmForSharingPassword(z, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSupportedQoSProvider() {
        try {
            return this.mService.isSupportedQoSProvider();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSupportedProfileRequest() {
        try {
            return this.mService.isSupportedProfileRequest();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getProfileShareDump() {
        try {
            return this.mService.getProfileShareDump();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getAutoShareDump() {
        try {
            return this.mService.getAutoShareDump();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void runAutoShareForCurrent(List<String> list) {
        try {
            this.mService.runAutoShareForCurrent(list);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSupportedAutoWifi() {
        try {
            return this.mService.isSupportedAutoWifi();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean getAutoWifiDefaultValue() {
        try {
            return this.mService.getAutoWifiDefaultValue();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shouldShowAutoWifiBubbleTip() {
        try {
            return this.mService.shouldShowAutoWifiBubbleTip();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAvailableAutoWifiScan() {
        try {
            return this.mService.isAvailableAutoWifiScan();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getAutoWifiDump() {
        try {
            return this.mService.getAutoWifiDump();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Map<String, Map<String, Double>> getConfiguredNetworkLocations() {
        try {
            return this.mService.getConfiguredNetworkLocations();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasConfiguredNetworkLocations(String str) {
        try {
            return this.mService.hasConfiguredNetworkLocations(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTestSettings(int i, Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException("settings should not be null");
        }
        try {
            this.mService.setTestSettings(i, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAllowWifiScan(boolean z) {
        try {
            this.mService.setAllowWifiScan(z, this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isScanningEnabled() {
        try {
            return this.mService.isScanningEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean startScan() {
        try {
            return this.mService.startScan(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setEasySetupScanSettings(List<String> list, PendingIntent pendingIntent, PendingIntent pendingIntent2, int i) {
        if (list == null) {
            return;
        }
        try {
            SemEasySetupWifiScanSettings semEasySetupWifiScanSettings = new SemEasySetupWifiScanSettings();
            semEasySetupWifiScanSettings.ssidPatterns = list;
            semEasySetupWifiScanSettings.pendingIntentForIdlePopup = pendingIntent;
            semEasySetupWifiScanSettings.pendingIntentForSettings = pendingIntent2;
            semEasySetupWifiScanSettings.minRssi = i;
            this.mService.setEasySetupScanSettings(this.mContext.getOpPackageName(), semEasySetupWifiScanSettings);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setEasySetupScanSettings(List<String> list, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        setEasySetupScanSettings(list, pendingIntent, pendingIntent2, -55);
    }

    public Map<String, SemEasySetupWifiScanSettings> getEasySetupScanSettings() {
        try {
            return this.mService.getEasySetupScanSettings();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disableRandomMac() {
        try {
            this.mService.disableRandomMac();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getWcmEverQualityTested() {
        try {
            return this.mService.getWcmEverQualityTested();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getWifiIconVisibility() {
        try {
            return this.mService.getWifiIconVisibility();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getCurrentStatusMode() {
        try {
            return this.mService.getCurrentStatusMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getValidState() {
        try {
            return this.mService.getValidState();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setKeepConnection(boolean z) {
        try {
            this.mService.setKeepConnectionAlways(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setKeepConnection(boolean z, boolean z2) {
        try {
            this.mService.setKeepConnection(z, z2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setKeepConnectionBigData(int i) {
        try {
            this.mService.setKeepConnectionBigData(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeExcludedNetwork(int i) {
        try {
            this.mService.removeExcludedNetwork(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getChannelUtilization() {
        try {
            return this.mService.getChannelUtilization();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Map<Integer, Integer> getChannelUtilizationExtended() {
        try {
            return this.mService.getChannelUtilizationExtended();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setRoamTrigger(int i) {
        try {
            return this.mService.setRoamTrigger(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRoamTrigger() {
        try {
            return this.mService.getRoamTrigger();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setRoamDelta(int i) {
        try {
            return this.mService.setRoamDelta(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRoamDelta() {
        try {
            return this.mService.getRoamDelta();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setRoamScanPeriod(int i) {
        try {
            return this.mService.setRoamScanPeriod(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRoamScanPeriod() {
        try {
            return this.mService.getRoamScanPeriod();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setRoamBand(int i) {
        try {
            return this.mService.setRoamBand(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRoamBand() {
        try {
            return this.mService.getRoamBand();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setCountryRev(String str) {
        try {
            return this.mService.setCountryRev(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getCountryRev() {
        try {
            return this.mService.getCountryRev();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getCountryCode() {
        try {
            return this.mService.getCountryCode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getWifi7DisabledCountry() {
        try {
            return this.mService.getWifi7DisabledCountry();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isNCHOModeEnabled() {
        try {
            return this.mService.isNCHOModeEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setNCHOModeEnabled(boolean z) {
        try {
            return this.mService.setNCHOModeEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setRoamScanEnabled(boolean z) {
        try {
            return this.mService.setRoamScanEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setRoamScanChannels(String[] strArr) {
        try {
            return this.mService.setRoamScanChannels(strArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isWesModeEnabled() {
        try {
            return this.mService.isWesModeEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setWesModeEnabled(boolean z) {
        try {
            return this.mService.setWesModeEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean sendVendorSpecificActionFrame(String str, int i, int i2, String str2) {
        try {
            return this.mService.sendVendorSpecificActionFrame(str, i, i2, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean sendReassociationRequestFrame(String str, int i) {
        try {
            return this.mService.sendReassociationRequestFrame(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyReachabilityLost() {
        try {
            this.mService.notifyReachabilityLost();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setConnectivityCheckDisabled(boolean z) {
        try {
            this.mService.setConnectivityCheckDisabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setGripSensorMonitorEnabled(boolean z) {
        try {
            this.mService.setGripSensorMonitorEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isGripSensorMonitorEnabled() {
        try {
            return this.mService.isGripSensorMonitorEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setConnectionAttemptInfo(int i, boolean z, String str) {
        try {
            this.mService.setConnectionAttemptInfo(i, z, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setConnectionAttemptInfo(int i, boolean z) {
        try {
            this.mService.setConnectionAttemptInfo(i, z, "");
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void restoreSemConfigurationsBackupData(String str) {
        try {
            this.mService.restoreSemConfigurationsBackupData(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String retrieveSemWifiConfigsBackupData() {
        try {
            return this.mService.retrieveSemWifiConfigsBackupData();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void restoreIWCSettingsValue(int i, int i2) {
        try {
            this.mService.restoreIWCSettingsValue(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getIWCQTables() {
        try {
            return this.mService.getIWCQTables();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setIWCQTables(String str) {
        try {
            this.mService.setIWCQTables(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateIWCHintCard(long j) {
        try {
            this.mService.updateIWCHintCard(j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setIWCMockAction(int i) {
        try {
            this.mService.setIWCMockAction(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setUploadModeEnabled(boolean z) {
        try {
            return this.mService.setUploadModeEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUploadModeEnabled() {
        try {
            return this.mService.isUploadModeEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean disconnectApBlockAutojoin(boolean z) {
        try {
            return this.mService.disconnectApBlockAutojoin(z);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void setImsCallEstablished(boolean z) {
        try {
            this.mService.setImsCallEstablished(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setOptimizerForceControlMode(int i) {
        try {
            return this.mService.setOptimizerForceControlMode(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getOptimizerForceControlMode() {
        try {
            return this.mService.getOptimizerForceControlMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getOptimizerState() {
        try {
            return this.mService.getOptimizerState();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getServiceDetectionResult() {
        try {
            return this.mService.getServiceDetectionResult();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTrafficPatternTestSettings(Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException("settings should not be null");
        }
        try {
            this.mService.setTrafficPatternTestSettings(bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int setWifiUwbCoexEnabled(int i, boolean z) {
        try {
            return this.mService.setWifiUwbCoexEnabled(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setLatencyCritical(String str, int i) {
        try {
            return this.mService.setLatencyCritical(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setPktlogFilter(String str, String str2) {
        try {
            return this.mService.setPktlogFilter(str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removePktlogFilter(String str, String str2) {
        try {
            return this.mService.removePktlogFilter(str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean saveFwDump() {
        try {
            return this.mService.saveFwDump();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRssi(String str) {
        try {
            return this.mService.getRssi(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startTimerForWifiOffload() {
        try {
            this.mService.startTimerForWifiOffload();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void checkAppForWiFiOffloading(String str) {
        try {
            this.mService.checkAppForWiFiOffloading(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTCRule(boolean z, String str, int i) {
        try {
            this.mService.setTCRule(z, str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void externalTwtInterface(int i, String str) {
        try {
            this.mService.externalTwtInterface(i, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getTWTParams() {
        try {
            return this.mService.getTWTParams();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Map<String, Boolean> getCtlFeatureState() {
        try {
            return this.mService.getCtlFeatureState();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetCallbackCondition(int i) {
        try {
            this.mService.resetCallbackCondition(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetComebackCondition() {
        try {
            this.mService.resetComebackCondition();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTestMode(Boolean bool) {
        try {
            this.mService.setTestMode(bool.booleanValue());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getCurrentL2TransitionMode() {
        try {
            return this.mService.getCurrentL2TransitionMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getL2TransitionLog() {
        try {
            return this.mService.getL2TransitionLog();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getNumberOfDataInEachRssiLevel() {
        try {
            return this.mService.getNumberOfDataInEachRssiLevel();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean iwhIntendedDisconnection() {
        try {
            return this.mService.iwhIntendedDisconnection();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getIwhState() {
        try {
            return this.mService.getIwhState();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getIccState() {
        try {
            return this.mService.getIccState();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSamsungMloCtrl(boolean z) {
        try {
            this.mService.setSamsungMloCtrl(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean getSamsungMloCtrl() {
        try {
            return this.mService.getSamsungMloCtrl();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSamsungIwhCtrl(boolean z) {
        try {
            this.mService.setSamsungIwhCtrl(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean getSamsungIwhCtrl() {
        try {
            return this.mService.getSamsungIwhCtrl();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean linkQosQuery(long j, long j2, long j3, int i, Long l) {
        try {
            return this.mService.linkQosQuery(j, j2, j3, i, l.longValue());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiAiServiceState(boolean z, int[] iArr, int[] iArr2) {
        try {
            this.mService.setWifiAiServiceState(z, iArr, iArr2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiAiServiceNsdResult(int[] iArr, int[] iArr2, int[] iArr3, String[] strArr) {
        try {
            this.mService.setWifiAiServiceNsdResult(iArr, iArr2, iArr3, strArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiAiIwhTrainingResult(String str, int i, int i2, int i3) {
        try {
            this.mService.setWifiAiIwhTrainingResult(str, i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiAiIwhInferenceResult(boolean[] zArr) {
        try {
            this.mService.setWifiAiIwhInferenceResult(zArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiAiIccTrainingResult(String str, int i, int i2, int i3) {
        try {
            this.mService.setWifiAiIccTrainingResult(str, i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiAiIccInferenceResult(boolean[] zArr) {
        try {
            this.mService.setWifiAiIccInferenceResult(zArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiAiIccInferenceResult2(float[] fArr) {
        try {
            this.mService.setWifiAiIccInferenceResult2(fArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiAiIccInferenceConfidence(float[] fArr) {
        try {
            this.mService.setWifiAiIccInferenceConfidence(fArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setIlaTrainingResult(double d, String str) {
        try {
            this.mService.setIlaTrainingResult(d, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setInsInferenceResult(int i, float f, float f2, float f3, float f4, String str) {
        try {
            this.mService.setInsInferenceResult(i, f, f2, f3, f4, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void enableHotspotTsfInfo(boolean z) {
        try {
            this.mService.enableHotspotTsfInfo(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyConnect(int i, String str) {
        try {
            this.mService.notifyConnect(i, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getTcpMonitorDnsHistory(int i) {
        try {
            return this.mService.getTcpMonitorDnsHistory(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getTcpMonitorSocketForegroundHistory(int i) {
        try {
            return this.mService.getTcpMonitorSocketForegroundHistory(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getTcpMonitorAllSocketHistory(int i) {
        try {
            return this.mService.getTcpMonitorAllSocketHistory(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isIndividualAppSupported() {
        try {
            return this.mService.isIndividualAppSupported();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getWifiUsabilityStatsEntry(int i) {
        try {
            return this.mService.getWifiUsabilityStatsEntry(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAvailableTdls() {
        try {
            return this.mService.isAvailableTdls();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isWiderBandwidthTdlsSupported() {
        try {
            return this.mService.isWiderBandwidthTdlsSupported();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setTdlsEnabled(boolean z) {
        try {
            return this.mService.setTdlsEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMaxTdlsSession() {
        try {
            return this.mService.getMaxTdlsSession();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getNumOfTdlsSession() {
        try {
            return this.mService.getNumOfTdlsSession();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getWifiStaInfo() {
        try {
            return this.mService.getWifiStaInfo();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getTasMode() {
        try {
            return this.mService.getTasMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getTxPower() {
        try {
            return this.mService.getTxPower();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getNumOfWifiAnt() {
        try {
            return this.mService.getNumOfWifiAnt();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setDcxoCalibrationData(String str) {
        try {
            return this.mService.setDcxoCalibrationData(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getDcxoCalibrationData() {
        try {
            return this.mService.getDcxoCalibrationData();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle getCurrentWifiRouterInfo() {
        try {
            return this.mService.getCurrentWifiRouterInfo();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle getWifiRouterInfo(String str) {
        try {
            return this.mService.getWifiRouterInfo(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getWifiRouterInfoBestEffort(String str) {
        try {
            return this.mService.getWifiRouterInfoBestEffort(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getWifiRouterInfoPresentable(String str) {
        try {
            return this.mService.getWifiRouterInfoPresentable(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle getWifiRouterInfoByBssid(String str) {
        try {
            return this.mService.getWifiRouterInfoByBssid(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getWifiRouterInfoBestEffortByBssid(String str) {
        try {
            return this.mService.getWifiRouterInfoBestEffortByBssid(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getWifiRouterInfoPresentableByBssid(String str) {
        try {
            return this.mService.getWifiRouterInfoPresentableByBssid(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Map<String, Long> getNetworkLastUpdatedTimeMap() {
        try {
            return this.mService.getNetworkLastUpdatedTimeMap();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getCurrentStateAndEnterTime() {
        try {
            return this.mService.getCurrentStateAndEnterTime();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long[] getNetworkUsageInfo(String str) {
        try {
            return this.mService.getNetworkUsageInfo(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getDailyUsageInfo(int i) {
        try {
            return this.mService.getDailyUsageInfo(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Map<String, Integer> getTasAverage() {
        try {
            return this.mService.getTasAverage();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Map<String, Integer> setTasPolicy(int i, int i2) {
        try {
            return this.mService.setTasPolicy(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Map<String, Integer> setTasPolicy(int i) {
        try {
            return this.mService.setTasPolicy(i, -1);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerTasPolicyChangedListener(TasPolicyListener tasPolicyListener) {
        try {
            TasPolicyListener.TasPolicyListenerProxy proxy = tasPolicyListener.getProxy();
            proxy.initProxy(this.mContext.getMainExecutor(), tasPolicyListener);
            this.mService.registerTasPolicyChangedListener(proxy);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterTasPolicyChangedListener(TasPolicyListener tasPolicyListener) {
        try {
            TasPolicyListener.TasPolicyListenerProxy proxy = tasPolicyListener.getProxy();
            this.mService.unregisterTasPolicyChangedListener(proxy);
            proxy.cleanUpProxy();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void enableTxPowerLogging(boolean z, int i) {
        try {
            this.mService.enableTxPowerLogging(z, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getDynamicFeatureStatus() {
        try {
            return this.mService.getDynamicFeatureStatus();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean checkUnauthorizedRro() {
        try {
            return this.mService.checkUnauthorizedRro();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> checkAndGetUnauthorizedRro() {
        try {
            return this.mService.checkAndGetUnauthorizedRro();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean checkUnauthorizedRroWithoutToast() {
        try {
            return this.mService.checkUnauthorizedRroWithoutToast();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> checkAndGetUnauthorizedRroWithoutToast() {
        try {
            return this.mService.checkAndGetUnauthorizedRroWithoutToast();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSwitchToMobileDataDefaultOff() {
        try {
            return this.mService.isSwitchToMobileDataDefaultOff();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setMhsAiServiceState(boolean z, int[] iArr, int[] iArr2) {
        try {
            this.mService.setMhsAiServiceState(z, iArr, iArr2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setMhsAiServiceNsdResult(int[] iArr, String[] strArr) {
        try {
            this.mService.setMhsAiServiceNsdResult(iArr, strArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static abstract class TasPolicyListener {
        private final TasPolicyListenerProxy mProxy = new TasPolicyListenerProxy();

        public abstract void onTasPolicyChanged(int i, int i2);

        TasPolicyListenerProxy getProxy() {
            return this.mProxy;
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class TasPolicyListenerProxy extends SemTasPolicyListener.Stub {
            private TasPolicyListener mListener;
            private final Object mLock = new Object();
            private Executor mExecutor = null;

            TasPolicyListenerProxy() {
            }

            void initProxy(Executor executor, TasPolicyListener tasPolicyListener) {
                synchronized (this.mLock) {
                    this.mExecutor = executor;
                    this.mListener = tasPolicyListener;
                }
            }

            void cleanUpProxy() {
                synchronized (this.mLock) {
                    this.mExecutor = null;
                    this.mListener = null;
                }
            }

            @Override // com.samsung.android.wifi.SemTasPolicyListener
            public void onTasPolicyChanged(final int i, final int i2) throws RemoteException {
                final TasPolicyListener tasPolicyListener;
                Executor executor;
                synchronized (this.mLock) {
                    tasPolicyListener = this.mListener;
                    executor = this.mExecutor;
                }
                if (tasPolicyListener == null || executor == null) {
                    return;
                }
                Binder.clearCallingIdentity();
                executor.execute(new Runnable() { // from class: com.samsung.android.wifi.SemWifiManager$TasPolicyListener$TasPolicyListenerProxy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemWifiManager.TasPolicyListener.this.onTasPolicyChanged(i, i2);
                    }
                });
            }
        }
    }

    public int startCapture(int i) {
        try {
            return this.mService.startCapture(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int stopCapture() {
        try {
            return this.mService.stopCapture();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int isCaptureRunning() {
        try {
            return this.mService.isCaptureRunning();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean getIsPacketCaptureSupportedByDriver() {
        try {
            return this.mService.getIsPacketCaptureSupportedByDriver();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setMcfMultiControlMode(boolean z) {
        try {
            this.mService.setMcfMultiControlMode(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public class AbTestConfigSubscription implements AutoCloseable {
        private final CloseGuard mCloseGuard;
        private boolean mClosed;
        private final Object mLock;
        private final String mModule;
        private final AbTestConfigUpdateObserver mObserver;

        public AbTestConfigSubscription(AbTestConfigUpdateObserver abTestConfigUpdateObserver, String str) {
            CloseGuard closeGuard = new CloseGuard();
            this.mCloseGuard = closeGuard;
            this.mLock = new Object();
            this.mClosed = false;
            this.mObserver = abTestConfigUpdateObserver;
            this.mModule = str;
            closeGuard.open("close");
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            try {
                synchronized (this.mLock) {
                    if (!this.mClosed) {
                        this.mClosed = true;
                        SemWifiManager.this.unregisterAbTestConfigUpdateObserver(this.mObserver);
                        this.mCloseGuard.close();
                    }
                }
            } catch (Exception unused) {
                Log.e(SemWifiManager.TAG, "Failed to unregister AbTestConfigUpdateObserver.");
            } finally {
                Reference.reachabilityFence(this);
            }
        }

        protected void finalize() throws Throwable {
            try {
                CloseGuard closeGuard = this.mCloseGuard;
                if (closeGuard != null) {
                    closeGuard.warnIfOpen();
                }
                close();
            } finally {
                super.finalize();
            }
        }

        public String toString() {
            return "AbTestConfigSubscription{mObserver=" + this.mObserver + ", mModule='" + this.mModule + "'}";
        }
    }

    public void registerAbTestConfigUpdateObserver(AbTestConfigUpdateObserver abTestConfigUpdateObserver, String str) {
        if (abTestConfigUpdateObserver == null) {
            throw new IllegalArgumentException("observer cannot be null");
        }
        Executor mainExecutor = this.mContext.getMainExecutor();
        SparseArray<ISemAbTestConfigurationUpdateObserver> sparseArray = sSemAbTestConfigurationUpdateObserverMap;
        synchronized (sparseArray) {
            AbTestConfigUpdateObserverProxy abTestConfigUpdateObserverProxy = new AbTestConfigUpdateObserverProxy(this, mainExecutor, abTestConfigUpdateObserver, str);
            try {
                sparseArray.put(System.identityHashCode(abTestConfigUpdateObserver), abTestConfigUpdateObserverProxy);
                this.mService.registerAbTestConfigUpdateObserver(abTestConfigUpdateObserverProxy, str);
                abTestConfigUpdateObserverProxy.registered();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterAbTestConfigUpdateObserver(AbTestConfigUpdateObserver abTestConfigUpdateObserver) {
        if (abTestConfigUpdateObserver == null) {
            throw new IllegalArgumentException("observer cannot be null");
        }
        SparseArray<ISemAbTestConfigurationUpdateObserver> sparseArray = sSemAbTestConfigurationUpdateObserverMap;
        synchronized (sparseArray) {
            int identityHashCode = System.identityHashCode(abTestConfigUpdateObserver);
            if (!sparseArray.contains(identityHashCode)) {
                Log.e(TAG, "Unknown external observer " + identityHashCode);
            } else {
                try {
                    this.mService.unregisterAbTestConfigUpdateObserver(sparseArray.get(identityHashCode));
                    sparseArray.remove(identityHashCode);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void reportAbTestResult(String str, String str2, String str3) {
        try {
            this.mService.reportAbTestResult(str, str2, str3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<SemAbTestConfiguration> getAbTestConfigs() {
        try {
            return this.mService.getAbTestConfigs();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public SemAbTestConfiguration getAbTestConfiguredModule(String str) {
        try {
            return this.mService.getAbTestConfiguredModule(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean sendReassociationFrequencyRequestFrame(String str, int i) {
        try {
            return this.mService.sendReassociationFrequencyRequestFrame(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class AbTestConfigUpdateObserverProxy extends ISemAbTestConfigurationUpdateObserver.Stub {
        private final Executor mExecutor;
        private final String mModule;
        private final AbTestConfigUpdateObserver mObserver;
        private final WeakReference<SemWifiManager> mWifiManager;

        AbTestConfigUpdateObserverProxy(SemWifiManager semWifiManager, Executor executor, AbTestConfigUpdateObserver abTestConfigUpdateObserver, String str) {
            this.mWifiManager = new WeakReference<>(semWifiManager);
            this.mExecutor = executor;
            this.mObserver = abTestConfigUpdateObserver;
            this.mModule = str;
        }

        public void registered() throws RemoteException {
            final SemWifiManager semWifiManager = this.mWifiManager.get();
            if (semWifiManager == null) {
                return;
            }
            this.mExecutor.execute(new Runnable() { // from class: com.samsung.android.wifi.SemWifiManager$AbTestConfigUpdateObserverProxy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SemWifiManager.AbTestConfigUpdateObserverProxy.this.lambda$registered$0(semWifiManager);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$registered$0(SemWifiManager semWifiManager) {
            AbTestConfigUpdateObserver abTestConfigUpdateObserver = this.mObserver;
            Objects.requireNonNull(semWifiManager);
            abTestConfigUpdateObserver.onRegistered(semWifiManager.new AbTestConfigSubscription(this.mObserver, this.mModule));
        }

        @Override // com.samsung.android.wifi.ISemAbTestConfigurationUpdateObserver
        public void notifyAbTestConfigUpdate(final SemAbTestConfiguration semAbTestConfiguration) {
            this.mExecutor.execute(new Runnable() { // from class: com.samsung.android.wifi.SemWifiManager$AbTestConfigUpdateObserverProxy$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SemWifiManager.AbTestConfigUpdateObserverProxy.this.lambda$notifyAbTestConfigUpdate$1(semAbTestConfiguration);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyAbTestConfigUpdate$1(SemAbTestConfiguration semAbTestConfiguration) {
            this.mObserver.onUpdated(semAbTestConfiguration);
        }
    }

    public boolean setWifiSharingMenuState(boolean z) {
        try {
            return this.mService.setWifiSharingMenuState(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getCandidateNetworkScores() {
        try {
            return this.mService.getCandidateNetworkScores();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getLastSelectedTimeStampForSilentRoaming() {
        try {
            return this.mService.getLastSelectedTimeStampForSilentRoaming();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setLastSelectedTimeStampForSilentRoaming() {
        try {
            this.mService.setLastSelectedTimeStampForSilentRoaming();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getLastSelectedNetworkIdForSilentRoaming() {
        try {
            return this.mService.getLastSelectedNetworkIdForSilentRoaming();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setLastSelectedNetworkIdForSilentRoaming(int i) {
        try {
            this.mService.setLastSelectedNetworkIdForSilentRoaming(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
