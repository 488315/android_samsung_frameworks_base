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
        public static final String KEY_SOFT_AP_INFO = "SoftApInfo ======>";
        public static final String KEY_SSID = "Ssid = ";
        public static final String KEY_STATE = "State =";
        public static final String KEY_TAG_NAME = "TagName = ";
        public static final String KEY_TIME = "Time = ";
        public static final String KEY_TIME_IN_MILLIS = "TimeInMillis = ";
        public static final String KEY_TIME_LIMIT_IN_MILLIS = "TimeLimitInMillis =";
        public static final String KEY_TIME_OUT = "TimeOut = ";
        public static final String KEY_UPSTREAM_TYPE_CHANGED = "Upstream type changed =";
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
        public static final String VALUE_HOTSPOT_CHANNEL_SWITCH = "Hotspot channel switch";
        public static final String VALUE_HOTSPOT_OFF = "Hotspot off";
        public static final String VALUE_HOTSPOT_ON = "Hotspot on";
        public static final String VALUE_LIMIT_REMOVED = "[Limit Removed]";
        public static final String VALUE_NORMAL = "Normal";
        public static final String VALUE_NO_UPSTREAM = "No upstream";
        public static final String VALUE_OTP = "Otp";
        public static final String VALUE_OVERALL_CLIENTS_SETTINGS_UPDATED = "Overall clients settings updated";
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

    public SemWifiManager(Context context, ISemWifiManager service, Looper ignore) {
        this.mContext = context;
        this.mService = service;
    }

    public void setMaxDtimInSuspendMode(boolean enable) {
        try {
            this.mService.setMaxDtimInSuspendMode(enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDtimInSuspendMode(int interval) {
        try {
            this.mService.setDtimInSuspendMode(interval);
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

    public boolean wifiApBleClientRole(boolean enable) {
        try {
            return this.mService.wifiApBleClientRole(enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean wifiApBleMhsRole(boolean enable) {
        try {
            return this.mService.wifiApBleMhsRole(enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean connectToSmartMHS(String addr, int type, int mhidden, int mSecurity, String mhs_mac, String user_name, int ver, boolean wifiprofileshare) {
        try {
            return this.mService.connectToSmartMHS(addr, type, mhidden, mSecurity, mhs_mac, user_name, ver, wifiprofileshare);
        } catch (RemoteException e) {
            return false;
        }
    }

    public void requestStopAutohotspotAdvertisement(boolean val) {
        try {
            this.mService.requestStopAutohotspotAdvertisement(val);
        } catch (RemoteException e) {
        }
    }

    public int getAdvancedAutohotspotConnectSettings() {
        try {
            int val = this.mService.getAdvancedAutohotspotConnectSettings();
            return val;
        } catch (RemoteException e) {
            return -1;
        }
    }

    public void setAutohotspotToastMessage(int noti) {
        try {
            this.mService.setAutohotspotToastMessage(noti);
        } catch (RemoteException e) {
        }
    }

    public void setAdvancedAutohotspotConnectSettings(int val) {
        try {
            this.mService.setAdvancedAutohotspotConnectSettings(val);
        } catch (RemoteException e) {
        }
    }

    public int getAdvancedAutohotspotLCDSettings() {
        try {
            int val = this.mService.getAdvancedAutohotspotLCDSettings();
            return val;
        } catch (RemoteException e) {
            return -1;
        }
    }

    public void setAdvancedAutohotspotLCDSettings(int val) {
        try {
            this.mService.setAdvancedAutohotspotLCDSettings(val);
        } catch (RemoteException e) {
        }
    }

    public void setWifiSettingsForegroundState(int val) {
        try {
            this.mService.setWifiSettingsForegroundState(val);
        } catch (RemoteException e) {
        }
    }

    public void clearAutoHotspotLists() {
        try {
            this.mService.clearAutoHotspotLists();
        } catch (RemoteException e) {
        }
    }

    public void setWifiApWarningActivityRunning(int val) {
        try {
            this.mService.setWifiApWarningActivityRunning(val);
        } catch (RemoteException e) {
        }
    }

    public int getWifiApWarningActivityRunningState() {
        try {
            int val = this.mService.getWifiApWarningActivityRunningState();
            return val;
        } catch (RemoteException e) {
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

    public boolean wifiApBleD2DClientRole(boolean enable) {
        try {
            return this.mService.wifiApBleD2DClientRole(enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean wifiApBleD2DMhsRole(boolean enable) {
        try {
            return this.mService.wifiApBleD2DMhsRole(enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean connectToSmartD2DClient(String bleaddr, String client_mac, SemWifiApSmartCallback callback) {
        try {
            SemWifiApSmartCallback.SemWifiApSmartCallbackProxy proxy = callback.getProxy();
            proxy.initProxy(this.mContext.getMainExecutor(), callback);
            return this.mService.connectToSmartD2DClient(bleaddr, client_mac, proxy);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getSmartD2DClientConnectedStatus(String mac) {
        try {
            return this.mService.getSmartD2DClientConnectedStatus(mac);
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

            void initProxy(Executor executor, SemWifiApSmartCallback callback) {
                synchronized (this.mLock) {
                    this.mExecutor = executor;
                    this.mCallback = callback;
                }
            }

            void cleanUpProxy() {
                synchronized (this.mLock) {
                    this.mExecutor = null;
                    this.mCallback = null;
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiApSmartCallback
            public void onStateChanged(final int state, final String mhsMac) throws RemoteException {
                Executor executor;
                final SemWifiApSmartCallback callback;
                Log.v(SemWifiManager.TAG, "SemWifiApSmartCallbackProxy: onStateChanged: state=" + state);
                synchronized (this.mLock) {
                    executor = this.mExecutor;
                    callback = this.mCallback;
                }
                if (callback == null || executor == null) {
                    return;
                }
                Binder.clearCallingIdentity();
                executor.execute(new Runnable() { // from class: com.samsung.android.wifi.SemWifiManager$SemWifiApSmartCallback$SemWifiApSmartCallbackProxy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemWifiManager.SemWifiApSmartCallback.this.onStateChanged(state, mhsMac);
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

            void initProxy(Executor executor, SemWifiApClientListUpdateCallback callback) {
                synchronized (this.mLock) {
                    this.mExecutor = executor;
                    this.mCallback = callback;
                }
            }

            void cleanUpProxy() {
                synchronized (this.mLock) {
                    this.mExecutor = null;
                    this.mCallback = null;
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiApClientListUpdateCallback
            public void onClientListUpdated(final List<SemWifiApClientDetails> clientsList, final long totalDataUsageInBytes) throws RemoteException {
                Executor executor;
                final SemWifiApClientListUpdateCallback callback;
                Log.v(SemWifiManager.TAG, "onClientListUpdated: " + clientsList.toString() + ", totalDatausage = " + totalDataUsageInBytes);
                synchronized (this.mLock) {
                    executor = this.mExecutor;
                    callback = this.mCallback;
                }
                if (callback == null || executor == null) {
                    return;
                }
                Binder.clearCallingIdentity();
                executor.execute(new Runnable() { // from class: com.samsung.android.wifi.SemWifiManager$SemWifiApClientListUpdateCallback$SemWifiApClientListUpdateCallbackProxy$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemWifiManager.SemWifiApClientListUpdateCallback.this.onClientListUpdated(clientsList, totalDataUsageInBytes);
                    }
                });
            }

            @Override // com.samsung.android.wifi.ISemWifiApClientListUpdateCallback
            public void onOverallDataLimitChanged(final long dataLimitInBytes) throws RemoteException {
                Executor executor;
                final SemWifiApClientListUpdateCallback callback;
                Log.v(SemWifiManager.TAG, "onOverallDataLimitChanged: datalimit = " + dataLimitInBytes);
                synchronized (this.mLock) {
                    executor = this.mExecutor;
                    callback = this.mCallback;
                }
                if (callback == null || executor == null) {
                    return;
                }
                Binder.clearCallingIdentity();
                executor.execute(new Runnable() { // from class: com.samsung.android.wifi.SemWifiManager$SemWifiApClientListUpdateCallback$SemWifiApClientListUpdateCallbackProxy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemWifiManager.SemWifiApClientListUpdateCallback.this.onOverallDataLimitChanged(dataLimitInBytes);
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

            void initProxy(Executor executor, SemWifiApClientUpdateCallback callback) {
                synchronized (this.mLock) {
                    this.mExecutor = executor;
                    this.mCallback = callback;
                }
            }

            void cleanUpProxy() {
                synchronized (this.mLock) {
                    this.mExecutor = null;
                    this.mCallback = null;
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiApClientUpdateCallback
            public void onClientUpdated(final SemWifiApClientDetails clientDetails) throws RemoteException {
                Executor executor;
                final SemWifiApClientUpdateCallback callback;
                Log.v(SemWifiManager.TAG, "onClientUpdated: " + clientDetails.toString());
                synchronized (this.mLock) {
                    executor = this.mExecutor;
                    callback = this.mCallback;
                }
                if (callback == null || executor == null) {
                    return;
                }
                Binder.clearCallingIdentity();
                executor.execute(new Runnable() { // from class: com.samsung.android.wifi.SemWifiManager$SemWifiApClientUpdateCallback$SemWifiApClientUpdateCallbackProxy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemWifiManager.SemWifiApClientUpdateCallback.this.onClientUpdated(clientDetails);
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

    public int setSmartMHSLocked(int state) {
        try {
            return this.mService.setSmartMHSLocked(state);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void isClientAcceptedWifiProfileSharing(boolean val) {
        try {
            this.mService.isClientAcceptedWifiProfileSharing(val);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerWifiApSmartCallback(SemWifiApSmartCallback callback, Executor executor) {
        if (callback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        if (executor == null) {
            throw new IllegalArgumentException("executor cannot be null");
        }
        Log.v(TAG, "registerWifiApSmartCallback: callback=" + callback + ", executor=" + executor);
        SemWifiApSmartCallback.SemWifiApSmartCallbackProxy proxy = callback.getProxy();
        proxy.initProxy(executor, callback);
        Binder binder = new Binder();
        try {
            this.mService.registerWifiApSmartCallback(binder, proxy, callback.hashCode());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterWifiApSmartCallback(SemWifiApSmartCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        Log.v(TAG, "unregisterWifiApSmartCallback: callback=" + callback + "callid : " + callback.hashCode());
        SemWifiApSmartCallback.SemWifiApSmartCallbackProxy proxy = callback.getProxy();
        try {
            try {
                this.mService.unregisterWifiApSmartCallback(callback.hashCode());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } finally {
            proxy.cleanUpProxy();
        }
    }

    public int getSmartApConnectedStatusFromScanResult(String clientMAC) {
        try {
            return this.mService.getSmartApConnectedStatusFromScanResult(clientMAC);
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

    public int startMcfClientMHSDiscovery(boolean enable) {
        try {
            return this.mService.startMcfClientMHSDiscovery(enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int startMcfMHSAdvertisement(boolean enable) {
        try {
            return this.mService.startMcfMHSAdvertisement(enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int connectToMcfMHS(String addr, int type, int mhidden, int mSecurity, String mhs_mac, String Username, int ver) {
        try {
            return this.mService.connectToMcfMHS(addr, type, mhidden, mSecurity, mhs_mac, Username, ver);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMcfConnectedStatus(String mhs_mac) {
        try {
            return this.mService.getMcfConnectedStatus(mhs_mac);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMcfConnectedStatusFromScanResult(String mac) {
        try {
            return this.mService.getMcfConnectedStatusFromScanResult(mac);
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

    public int autohotspotWifiScanConnect(String ssid, String password, String bssid, int hideSSID, int mhsFreq, int security) {
        try {
            return this.mService.autohotspotWifiScanConnect(ssid, password, bssid, hideSSID, mhsFreq, security);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiApClientMobileDataLimit(String mac, long val) {
        try {
            this.mService.setWifiApClientMobileDataLimit(mac, val);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiApClientTimeLimit(String mac, long val) {
        try {
            this.mService.setWifiApClientTimeLimit(mac, val);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiApClientDataPaused(String mac, boolean val) {
        try {
            this.mService.setWifiApClientDataPaused(mac, val);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiApClientEditedName(String mac, String val) {
        try {
            this.mService.setWifiApClientEditedName(mac, val);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public SemWifiApClientDetails getWifiApClientDetails(String mac) {
        try {
            return this.mService.getWifiApClientDetails(mac);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<SemWifiApClientDetails> getTopHotspotClientsToday(int topConnectedAndDisconnected, int maxListLength) {
        try {
            return this.mService.getTopHotspotClientsToday(topConnectedAndDisconnected, maxListLength);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getTopHotspotClientsTodayAsString(int topConnectedAndDisconnected, int maxListLength) {
        try {
            return this.mService.getTopHotspotClientsTodayAsString(topConnectedAndDisconnected, maxListLength);
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

    public void setWifiApDailyDataLimit(long bytes) {
        try {
            this.mService.setWifiApDailyDataLimit(bytes);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiApGuestPassword(String pwd) {
        try {
            this.mService.setWifiApGuestPassword(pwd);
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

    public void setWifiApGuestModeEnabled(boolean val) {
        try {
            this.mService.setWifiApGuestModeEnabled(val);
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

    public void setWifiApGuestModeIsolationEnabled(boolean val) {
        try {
            this.mService.setWifiApGuestModeIsolationEnabled(val);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getTotalAndTop3ClientsDataUsageBetweenGivenDates(long timestampInMilliSecsDate1, long timestampInMilliSecsDate2) {
        try {
            return this.mService.getTotalAndTop3ClientsDataUsageBetweenGivenDates(timestampInMilliSecsDate1, timestampInMilliSecsDate2);
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

    public void wifiApRestoreClientDataUsageSettingsInfo(String jsonString) {
        try {
            this.mService.wifiApRestoreClientDataUsageSettingsInfo(jsonString);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void wifiApRestoreDailyHotspotDataLimit(long bytes) {
        try {
            this.mService.wifiApRestoreDailyHotspotDataLimit(bytes);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerClientListDataUsageCallback(SemWifiApClientListUpdateCallback callback, Executor executor, int topConnectedAndDisconnected, int maxListLength) {
        if (callback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        if (executor == null) {
            throw new IllegalArgumentException("executor cannot be null");
        }
        Log.v(TAG, "registerClientListDataUsageCallback: callback=" + callback + ", executor=" + executor);
        SemWifiApClientListUpdateCallback.SemWifiApClientListUpdateCallbackProxy proxy = callback.getProxy();
        proxy.initProxy(executor, callback);
        Binder binder = new Binder();
        try {
            this.mService.registerClientListDataUsageCallback(binder, proxy, callback.hashCode(), topConnectedAndDisconnected, maxListLength);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterClientListDataUsageCallback(SemWifiApClientListUpdateCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        Log.v(TAG, "unregisterClientListDataUsageCallback: callback=" + callback + "callid : " + callback.hashCode());
        SemWifiApClientListUpdateCallback.SemWifiApClientListUpdateCallbackProxy proxy = callback.getProxy();
        try {
            try {
                this.mService.unregisterClientListDataUsageCallback(callback.hashCode());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } finally {
            proxy.cleanUpProxy();
        }
    }

    public void registerClientDataUsageCallback(SemWifiApClientUpdateCallback callback, Executor executor, String clientMac) {
        if (callback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        if (executor == null) {
            throw new IllegalArgumentException("executor cannot be null");
        }
        Log.v(TAG, "registerClientDataUsageCallback: callback=" + callback + ", executor=" + executor);
        SemWifiApClientUpdateCallback.SemWifiApClientUpdateCallbackProxy proxy = callback.getProxy();
        proxy.initProxy(executor, callback);
        Binder binder = new Binder();
        try {
            this.mService.registerClientDataUsageCallback(binder, proxy, callback.hashCode(), clientMac);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterClientDataUsageCallback(SemWifiApClientUpdateCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        Log.v(TAG, "unregisterClientDataUsageCallback: callback=" + callback + "callid : " + callback.hashCode());
        SemWifiApClientUpdateCallback.SemWifiApClientUpdateCallbackProxy proxy = callback.getProxy();
        try {
            try {
                this.mService.unregisterClientDataUsageCallback(callback.hashCode());
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

            void init(Executor executor, SemWifiApDataUsageListener listener) {
                synchronized (this.mLock) {
                    this.mExecutor = executor;
                    this.mListener = listener;
                }
            }

            void cleanUp() {
                synchronized (this.mLock) {
                    this.mExecutor = null;
                    this.mListener = null;
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiApDataUsageCallback
            public void onDataUsageChanged(final String value) {
                Executor executor;
                final SemWifiApDataUsageListener listener;
                Log.v(SemWifiManager.TAG, "onDataUsageChanged:" + value);
                synchronized (this.mLock) {
                    executor = this.mExecutor;
                    listener = this.mListener;
                }
                if (listener == null || executor == null) {
                    return;
                }
                Binder.clearCallingIdentity();
                executor.execute(new Runnable() { // from class: com.samsung.android.wifi.SemWifiManager$SemWifiApDataUsageListener$SemWifiApDataUsageClient$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemWifiManager.SemWifiApDataUsageListener.this.onDataUsageChanged(value);
                    }
                });
            }
        }
    }

    public void registerWifiApDataUsageListener(SemWifiApDataUsageListener listener, Executor executor) {
        if (listener == null) {
            throw new IllegalArgumentException("listener cannot be null");
        }
        if (executor == null) {
            throw new IllegalArgumentException("executor cannot be null");
        }
        Log.v(TAG, "registerApDataUsageChangedListener: listener=" + listener + ", executor=" + executor);
        SemWifiApDataUsageListener.SemWifiApDataUsageClient client = listener.getClient();
        client.init(executor, listener);
        Binder binder = new Binder();
        try {
            this.mService.registerWifiApDataUsageCallback(binder, client, listener.hashCode());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unRegisterWifiApDataUsageListener(SemWifiApDataUsageListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("listener cannot be null");
        }
        Log.v(TAG, "unRegisterWifiApDataUsageListener: listener=" + listener + "callid : " + listener.hashCode());
        SemWifiApDataUsageListener.SemWifiApDataUsageClient client = listener.getClient();
        try {
            try {
                this.mService.unRegisterWifiApDataUsageCallback(listener.hashCode());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } finally {
            client.cleanUp();
        }
    }

    public int getSmartApConnectedStatus(String mhs_mac) {
        try {
            return this.mService.getSmartApConnectedStatus(mhs_mac);
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

    public void launchWifiApWarningForMcfMHS(int wifiap_band, int wifiap_set_security, boolean wifiap_security) {
        try {
            this.mService.launchWifiApWarningForMcfMHS(wifiap_band, wifiap_set_security, wifiap_security);
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

    public boolean isWifiApGuestClient(String mac) {
        try {
            return this.mService.isWifiApGuestClient(mac);
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

    public void setArdkPowerSaveMode(boolean value) {
        try {
            this.mService.setArdkPowerSaveMode(value);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSoftApConfiguration(SoftApConfiguration config) {
        try {
            this.mService.setSoftApConfiguration(config);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setLocalOnlyHotspotEnabled(boolean enabled, String ssid, String password, int band) {
        Log.i(TAG, " setLocalOnlyHotspotEnabled : " + enabled);
        try {
            return this.mService.setLocalOnlyHotspotEnabled(enabled, ssid, password, band);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setWifiApEnabled(SoftApConfiguration softApConfig, boolean enabled) {
        Log.i(TAG, " setWifiApEnabled:" + enabled + " package:" + this.mContext.getPackageName());
        if (MHSDBG) {
            Log.e(TAG, Log.getStackTraceString(new Throwable()));
        }
        try {
            return this.mService.setWifiApEnabled(softApConfig, enabled);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void insertHotSpotEnabledHistory(String apiSignature) {
        String packageName = this.mContext.getPackageName();
        Log.i(TAG, apiSignature + " setwifiap packageName : " + packageName);
        if (MHSDBG) {
            Log.e(TAG, Log.getStackTraceString(new Throwable()));
        }
        Bundle args = new Bundle();
        Exception e = new Exception();
        StackTraceElement callerElement = e.getStackTrace()[3];
        CharSequence dateTime = DateFormat.format("yy/MM/dd kk:mm:ss ", System.currentTimeMillis());
        if (MHSDBG) {
            args.putString("extra_log", ((Object) dateTime) + apiSignature + " setwifiap " + packageName + NavigationBarInflaterView.SIZE_MOD_START + callerElement.getFileName() + ":" + callerElement.getMethodName() + "():" + callerElement.getLineNumber() + "]\n");
        } else {
            args.putString("extra_log", ((Object) dateTime) + apiSignature + " setwifiap " + packageName + NavigationBarInflaterView.SIZE_MOD_START + callerElement.getFileName() + ":" + callerElement.getLineNumber() + "]\n");
        }
        reportHotspotDumpLogs(args.getString("extra_log"));
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

    public String getVendorWlanDriverProp(String propName) {
        try {
            return this.mService.getVendorWlanDriverProp(propName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setVendorWlanDriverProp(String propName, String value) {
        try {
            return this.mService.setVendorWlanDriverProp(propName, value);
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

    public boolean setFactoryMacAddress(String data) {
        try {
            return this.mService.setFactoryMacAddress(data);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setFccChannelBackoffEnabled(String interfaceName, boolean enable) {
        try {
            this.mService.setFccChannelBackoffEnabled(interfaceName, enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setPsmInfo(String data) {
        try {
            return this.mService.setPsmInfo(data);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setAntInfo(String data) {
        try {
            return this.mService.setAntInfo(data);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setFrameburstInfo(String data) {
        try {
            return this.mService.setFrameburstInfo(data);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void triggerBackoffRoutine(boolean enable) {
        try {
            this.mService.triggerBackoffRoutine(enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void set5GmmWaveSarBackoffEnabled(boolean enable) {
        try {
            this.mService.set5GmmWaveSarBackoffEnabled(enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getWifiApMaxClient() {
        try {
            return this.mService.getWifiApMaxClient();
        } catch (RemoteException e) {
            Log.i(TAG, "getWifiApMaxClient() failed!");
            return 10;
        }
    }

    public boolean supportWifiAp5GBasedOnCountry() {
        try {
            return this.mService.supportWifiAp5GBasedOnCountry();
        } catch (RemoteException e) {
            Log.i(TAG, "supportWifiAp5GBasedOnCountry() failed!");
            return false;
        }
    }

    public boolean supportWifiAp6GBasedOnCountry() {
        try {
            return this.mService.supportWifiAp6GBasedOnCountry();
        } catch (RemoteException e) {
            Log.i(TAG, "supportWifiAp6GBasedOnCountry() failed!");
            return false;
        }
    }

    public boolean isWifiApConcurrentSupported() {
        try {
            return this.mService.isWifiSharingSupported();
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean isWifiSharingSupported() {
        try {
            return this.mService.isWifiSharingSupported();
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean isWifiSharingLiteSupported() {
        try {
            return this.mService.isWifiSharingLiteSupported();
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean setWifiSharingEnabled(boolean enable) {
        try {
            return this.mService.setWifiSharingEnabled(enable);
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean setWifiApConfigurationToDefault() {
        try {
            this.mService.setWifiApConfigurationToDefault();
            return true;
        } catch (RemoteException e) {
            return false;
        }
    }

    public String getWifiApStaList() {
        try {
            return this.mService.getWifiApStaList();
        } catch (RemoteException e) {
            return null;
        }
    }

    public String runIptablesRulesCommand(String cmd) {
        try {
            return this.mService.runIptablesRulesCommand(cmd);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getWifiApStaListDetail() {
        try {
            return this.mService.getWifiApStaListDetail();
        } catch (RemoteException e) {
            return null;
        }
    }

    public int getWifiApChannel() {
        try {
            return this.mService.getWifiApChannel();
        } catch (RemoteException e) {
            return -1;
        }
    }

    public String getStationInfo(String mac) {
        try {
            return this.mService.getStationInfo(mac);
        } catch (RemoteException e) {
            return null;
        }
    }

    public int getWifiApFreq() {
        try {
            return this.mService.getWifiApFreq();
        } catch (RemoteException e) {
            return -1;
        }
    }

    public void setAntMode(int mode) {
        try {
            this.mService.setAntMode(mode);
        } catch (RemoteException e) {
        }
    }

    public void setHotspotAntMode(int mode) {
        try {
            this.mService.setHotspotAntMode(mode);
        } catch (RemoteException e) {
        }
    }

    public int getHotspotAntMode() {
        try {
            return this.mService.getHotspotAntMode();
        } catch (RemoteException e) {
            return -1;
        }
    }

    public void setPowerSavingTime(int min) {
        try {
            this.mService.setPowerSavingTime(min);
        } catch (RemoteException e) {
        }
    }

    public JSONObject setMHSConfig(JSONObject jsonMIFI) {
        try {
            return new JSONObject(this.mService.setMHSConfig(jsonMIFI.toString()));
        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject getMHSConfig(JSONObject aJson) {
        try {
            return new JSONObject(this.mService.getMHSConfig(aJson.toString()));
        } catch (Exception e) {
            return null;
        }
    }

    public int manageWifiApMacAclList(String name, String mac, int add_or_delete, int allow_or_deny) {
        try {
            return this.mService.manageWifiApMacAclList(name, mac, add_or_delete, allow_or_deny);
        } catch (RemoteException e) {
            return -1;
        }
    }

    public List<String> readWifiApMacAclList(int allow_or_deny) {
        try {
            return this.mService.readWifiApMacAclList(allow_or_deny);
        } catch (RemoteException e) {
            return null;
        }
    }

    public void setWifiApMacAclMode(int mode) {
        try {
            this.mService.setWifiApMacAclMode(mode);
        } catch (RemoteException e) {
        }
    }

    public int getWifiApMacAclMode() {
        try {
            int retValue = this.mService.getWifiApMacAclMode();
            return retValue;
        } catch (RemoteException e) {
            return 0;
        }
    }

    public boolean isWifiApMacAclEnabled() {
        try {
            boolean retValue = this.mService.isWifiApMacAclEnabled();
            return retValue;
        } catch (RemoteException e) {
            return false;
        }
    }

    public void setWifiApMacAclEnable(boolean val) {
        try {
            this.mService.setWifiApMacAclEnable(val);
        } catch (RemoteException e) {
        }
    }

    public void setWifiApWpsPbc(boolean value) {
        try {
            this.mService.setWifiApWpsPbc(value);
        } catch (RemoteException e) {
        }
    }

    public boolean getWifiApWpsPbc() {
        try {
            return this.mService.getWifiApWpsPbc();
        } catch (RemoteException e) {
            return false;
        }
    }

    public void setWifiApIsolate(boolean value) {
        try {
            this.mService.setWifiApIsolate(value);
        } catch (RemoteException e) {
        }
    }

    public boolean getWifiApIsolate() {
        try {
            return this.mService.getWifiApIsolate();
        } catch (RemoteException e) {
            return false;
        }
    }

    public void updateHostapdMacList(int val) {
        try {
            this.mService.updateHostapdMacList(val);
        } catch (RemoteException e) {
        }
    }

    public String getWifiApInterfaceName() {
        try {
            return this.mService.getWifiApInterfaceName();
        } catch (RemoteException e) {
            return null;
        }
    }

    public List<String> getWifiApInterfaceNames() {
        try {
            return this.mService.getWifiApInterfaceNames();
        } catch (RemoteException e) {
            return new ArrayList();
        }
    }

    public boolean setProvisionSuccess(boolean set) {
        try {
            return this.mService.setProvisionSuccess(set);
        } catch (RemoteException e) {
            return false;
        }
    }

    public void setWifiApMaxClientToFramework(int num) {
        try {
            this.mService.setWifiApMaxClientToFramework(num);
        } catch (RemoteException e) {
        }
    }

    public int getWifiApMaxClientFromFramework() {
        try {
            return this.mService.getWifiApMaxClientFromFramework();
        } catch (RemoteException e) {
            return 10;
        }
    }

    public int getProvisionSuccess() {
        try {
            return this.mService.getProvisionSuccess();
        } catch (RemoteException e) {
            return 0;
        }
    }

    public boolean isWifiApEnabled() {
        try {
            return this.mService.isWifiApEnabled();
        } catch (RemoteException e) {
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

    public void setRVFmodeStatus(int mode) {
        try {
            this.mService.setRVFmodeStatus(mode);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void wifiApDisassocSta(String mac) {
        try {
            this.mService.wifiApDisassocSta(mac);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiApMaxClient(int num) {
        try {
            this.mService.setWifiApMaxClient(num);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportHotspotDumpLogs(String logs) {
        try {
            this.mService.reportHotspotDumpLogs(logs);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetSoftAp(Message msg) {
        try {
            this.mService.resetSoftAp(msg);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setVerboseLoggingEnabled(boolean enable) {
        try {
            this.mService.setVerboseLoggingEnabled(enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiDeveloperModeEnabled(boolean enable) {
        try {
            this.mService.setWifiDeveloperModeEnabled(enable);
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
        } catch (RemoteException e) {
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

    public void reportMHSBigData(String featureName, String params) {
        try {
            this.mService.reportBigData(featureName, params);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void blockFccChannelBackoff(boolean choice) {
        try {
            this.mService.blockFccChannelBackoff(choice);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void reportWifiOnOffEvent(boolean enabled, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            packageName = "unknown";
        }
        try {
            this.mService.addOrUpdateWifiControlHistory(packageName, enabled);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportRttStartRangingCallEvent(String packageName) {
        reportMHSBigData("RAPP", TextUtils.isEmpty(packageName) ? "unknown" : packageName);
    }

    public String getWifiEnableHistory() {
        try {
            return this.mService.getWifiEnableHistory();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean addOrUpdateNetwork(SemWifiConfiguration config) {
        if (config == null) {
            return false;
        }
        try {
            return this.mService.addOrUpdateNetwork(config);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeNetwork(String configKey) {
        if (TextUtils.isEmpty(configKey)) {
            return false;
        }
        try {
            return this.mService.removeNetwork(configKey);
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
            ParceledListSlice<SemWifiConfiguration> parceledList = this.mService.getConfiguredNetworks();
            if (parceledList == null) {
                return Collections.emptyList();
            }
            return parceledList.getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void allowAutojoinPasspoint(String fqdn, boolean allowAutojoin) {
        try {
            this.mService.allowAutojoinPasspoint(fqdn, allowAutojoin);
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

    public void reportIssue(int reportId, Bundle data) {
        if (data == null) {
            return;
        }
        try {
            this.mService.reportIssue(reportId, data);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getIssueDetectorDump(int maxCount) {
        try {
            return this.mService.getIssueDetectorDump(maxCount);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getSilentRoamingDump(int maxCount) {
        try {
            return this.mService.getSilentRoamingDump(maxCount);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startIssueMonitoring(Bundle settings) {
        if (settings == null) {
            return;
        }
        try {
            this.mService.startIssueMonitoring(settings);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getConnectivityLog(String category) {
        try {
            return this.mService.getConnectivityLog(category);
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

    public Map<String, Map<String, Integer>> getQoSScores(List<String> bssids) {
        try {
            return this.mService.getQoSScores(bssids);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBtmOptionUserEnabled(String configKey) {
        try {
            this.mService.setBtmOptionUserEnabled(configKey);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBtmOptionUserDisabled(String configKey) {
        try {
            this.mService.setBtmOptionUserDisabled(configKey);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerPasswordCallback(String bssid, ISemSharedPasswordCallback callback) {
        if (TextUtils.isEmpty(bssid) || callback == null) {
            throw new IllegalArgumentException("request AP's bssid or callback should not be empty");
        }
        try {
            this.mService.registerPasswordCallback(bssid, callback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterPasswordCallback(ISemSharedPasswordCallback callback) {
        try {
            this.mService.unregisterPasswordCallback(callback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestPassword(boolean showConfirm) {
        try {
            this.mService.requestPassword(showConfirm);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setUserConfirmForSharingPassword(boolean isAccept, String userData) {
        try {
            this.mService.setUserConfirmForSharingPassword(isAccept, userData);
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

    public void runAutoShareForCurrent(List<String> target) {
        try {
            this.mService.runAutoShareForCurrent(target);
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

    public boolean hasConfiguredNetworkLocations(String wifiConfigKey) {
        try {
            return this.mService.hasConfiguredNetworkLocations(wifiConfigKey);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTestSettings(int moduleId, Bundle settings) {
        if (settings == null) {
            throw new IllegalArgumentException("settings should not be null");
        }
        try {
            this.mService.setTestSettings(moduleId, settings);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAllowWifiScan(boolean enable) {
        try {
            this.mService.setAllowWifiScan(enable, this.mContext.getPackageName());
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

    public void setEasySetupScanSettings(List<String> ssids, PendingIntent pendingIntentForIdlePopup, PendingIntent pendingIntentForSettings, int minRssi) {
        if (ssids == null) {
            return;
        }
        try {
            SemEasySetupWifiScanSettings settings = new SemEasySetupWifiScanSettings();
            settings.ssidPatterns = ssids;
            settings.pendingIntentForIdlePopup = pendingIntentForIdlePopup;
            settings.pendingIntentForSettings = pendingIntentForSettings;
            settings.minRssi = minRssi;
            this.mService.setEasySetupScanSettings(this.mContext.getOpPackageName(), settings);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setEasySetupScanSettings(List<String> ssids, PendingIntent pendingIntentForIdlePopup, PendingIntent pendingIntentForSettings) {
        setEasySetupScanSettings(ssids, pendingIntentForIdlePopup, pendingIntentForSettings, -55);
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

    public void setKeepConnection(boolean keepConnection) {
        try {
            this.mService.setKeepConnectionAlways(keepConnection);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setKeepConnection(boolean keepConnection, boolean always) {
        try {
            this.mService.setKeepConnection(keepConnection, always);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setKeepConnectionBigData(int reason) {
        try {
            this.mService.setKeepConnectionBigData(reason);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeExcludedNetwork(int networkId) {
        try {
            this.mService.removeExcludedNetwork(networkId);
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

    public boolean setRoamTrigger(int roamTrigger) {
        try {
            return this.mService.setRoamTrigger(roamTrigger);
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

    public boolean setRoamDelta(int roamDelta) {
        try {
            return this.mService.setRoamDelta(roamDelta);
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

    public boolean setRoamScanPeriod(int roamScanPeriod) {
        try {
            return this.mService.setRoamScanPeriod(roamScanPeriod);
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

    public boolean setRoamBand(int band) {
        try {
            return this.mService.setRoamBand(band);
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

    public boolean setCountryRev(String countryRev) {
        try {
            return this.mService.setCountryRev(countryRev);
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

    public boolean setNCHOModeEnabled(boolean enable) {
        try {
            return this.mService.setNCHOModeEnabled(enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setRoamScanEnabled(boolean enable) {
        try {
            return this.mService.setRoamScanEnabled(enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setRoamScanChannels(String[] channels) {
        try {
            return this.mService.setRoamScanChannels(channels);
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

    public boolean setWesModeEnabled(boolean enable) {
        try {
            return this.mService.setWesModeEnabled(enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean sendVendorSpecificActionFrame(String bssid, int channel, int dwellTime, String frameBody) {
        try {
            return this.mService.sendVendorSpecificActionFrame(bssid, channel, dwellTime, frameBody);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean sendReassociationRequestFrame(String bssid, int channel) {
        try {
            return this.mService.sendReassociationRequestFrame(bssid, channel);
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

    public void setConnectivityCheckDisabled(boolean disabled) {
        try {
            this.mService.setConnectivityCheckDisabled(disabled);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setGripSensorMonitorEnabled(boolean enable) {
        try {
            this.mService.setGripSensorMonitorEnabled(enable);
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

    public void setConnectionAttemptInfo(int netId, boolean byUser, String configKey) {
        try {
            this.mService.setConnectionAttemptInfo(netId, byUser, configKey);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setConnectionAttemptInfo(int netId, boolean byUser) {
        try {
            this.mService.setConnectionAttemptInfo(netId, byUser, "");
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void restoreSemConfigurationsBackupData(String semconfig) {
        try {
            this.mService.restoreSemConfigurationsBackupData(semconfig);
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

    public void restoreIWCSettingsValue(int opType, int value) {
        try {
            this.mService.restoreIWCSettingsValue(opType, value);
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

    public void setIWCQTables(String qTables) {
        try {
            this.mService.setIWCQTables(qTables);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateIWCHintCard(long timestamp) {
        try {
            this.mService.updateIWCHintCard(timestamp);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setIWCMockAction(int action) {
        try {
            this.mService.setIWCMockAction(action);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setUploadModeEnabled(boolean enable) {
        try {
            return this.mService.setUploadModeEnabled(enable);
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

    public boolean disconnectApBlockAutojoin(boolean block) {
        try {
            return this.mService.disconnectApBlockAutojoin(block);
        } catch (RemoteException e) {
            return false;
        }
    }

    public void setImsCallEstablished(boolean isEstablished) {
        try {
            this.mService.setImsCallEstablished(isEstablished);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setOptimizerForceControlMode(int mode) {
        try {
            return this.mService.setOptimizerForceControlMode(mode);
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

    public void setTrafficPatternTestSettings(Bundle settings) {
        if (settings == null) {
            throw new IllegalArgumentException("settings should not be null");
        }
        try {
            this.mService.setTrafficPatternTestSettings(settings);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int setWifiUwbCoexEnabled(int uwbCh, boolean enable) {
        try {
            return this.mService.setWifiUwbCoexEnabled(uwbCh, enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setLatencyCritical(String ifaceName, int enable) {
        try {
            return this.mService.setLatencyCritical(ifaceName, enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setPktlogFilter(String ifaceName, String filter) {
        try {
            return this.mService.setPktlogFilter(ifaceName, filter);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removePktlogFilter(String ifaceName, String filter) {
        try {
            return this.mService.removePktlogFilter(ifaceName, filter);
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

    public int getRssi(String ifaceName) {
        try {
            return this.mService.getRssi(ifaceName);
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

    public void checkAppForWiFiOffloading(String packageName) {
        try {
            this.mService.checkAppForWiFiOffloading(packageName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTCRule(boolean enabled, String iface, int limit) {
        try {
            this.mService.setTCRule(enabled, iface, limit);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void externalTwtInterface(int cmdId, String cmdLine) {
        try {
            this.mService.externalTwtInterface(cmdId, cmdLine);
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

    public void resetCallbackCondition(int mode) {
        try {
            this.mService.resetCallbackCondition(mode);
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

    public void setTestMode(Boolean enabled) {
        try {
            this.mService.setTestMode(enabled.booleanValue());
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

    public void setSamsungMloCtrl(boolean enable) {
        try {
            this.mService.setSamsungMloCtrl(enable);
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

    public void setSamsungIwhCtrl(boolean enable) {
        try {
            this.mService.setSamsungIwhCtrl(enable);
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

    public boolean linkQosQuery(long payloadBytes, long desiredLatencyMs, long desiredThroughputMbps, int queryType, Long timeWindowMs) {
        try {
            return this.mService.linkQosQuery(payloadBytes, desiredLatencyMs, desiredThroughputMbps, queryType, timeWindowMs.longValue());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiAiServiceState(boolean enabled, int[] numClass, int[] numTimeStep) {
        try {
            this.mService.setWifiAiServiceState(enabled, numClass, numTimeStep);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiAiServiceNsdResult(int[] nsdResult, int[] l1ConvSerPredArr, int[] l2ConvSerPredArr, String[] convArr) {
        try {
            this.mService.setWifiAiServiceNsdResult(nsdResult, l1ConvSerPredArr, l2ConvSerPredArr, convArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiAiIwhTrainingResult(String gKey, int trScore, int numBssids, int mode) {
        try {
            this.mService.setWifiAiIwhTrainingResult(gKey, trScore, numBssids, mode);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWifiAiIwhInferenceResult(boolean[] ret) {
        try {
            this.mService.setWifiAiIwhInferenceResult(ret);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setIlaTrainingResult(double RssiResult, String bssidE) {
        try {
            this.mService.setIlaTrainingResult(RssiResult, bssidE);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void enableHotspotTsfInfo(boolean enable) {
        try {
            this.mService.enableHotspotTsfInfo(enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyConnect(int netId, String key) {
        try {
            this.mService.notifyConnect(netId, key);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getTcpMonitorDnsHistory(int count) {
        try {
            return this.mService.getTcpMonitorDnsHistory(count);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getTcpMonitorSocketForegroundHistory(int count) {
        try {
            return this.mService.getTcpMonitorSocketForegroundHistory(count);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getTcpMonitorAllSocketHistory(int count) {
        try {
            return this.mService.getTcpMonitorAllSocketHistory(count);
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

    public String getWifiUsabilityStatsEntry(int size) {
        try {
            return this.mService.getWifiUsabilityStatsEntry(size);
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

    public boolean setTdlsEnabled(boolean enable) {
        try {
            return this.mService.setTdlsEnabled(enable);
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

    public boolean setDcxoCalibrationData(String data) {
        try {
            return this.mService.setDcxoCalibrationData(data);
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

    public Bundle getWifiRouterInfo(String configKey) {
        try {
            return this.mService.getWifiRouterInfo(configKey);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getWifiRouterInfoBestEffort(String configKey) {
        try {
            return this.mService.getWifiRouterInfoBestEffort(configKey);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getWifiRouterInfoPresentable(String configKey) {
        try {
            return this.mService.getWifiRouterInfoPresentable(configKey);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle getWifiRouterInfoByBssid(String bssid) {
        try {
            return this.mService.getWifiRouterInfoByBssid(bssid);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getWifiRouterInfoBestEffortByBssid(String bssid) {
        try {
            return this.mService.getWifiRouterInfoBestEffortByBssid(bssid);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getWifiRouterInfoPresentableByBssid(String bssid) {
        try {
            return this.mService.getWifiRouterInfoPresentableByBssid(bssid);
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

    public long[] getNetworkUsageInfo(String configKey) {
        try {
            return this.mService.getNetworkUsageInfo(configKey);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getDailyUsageInfo(int daysAgo) {
        try {
            return this.mService.getDailyUsageInfo(daysAgo);
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

    public Map<String, Integer> setTasPolicy(int newTasPolicy, int windowSize) {
        try {
            return this.mService.setTasPolicy(newTasPolicy, windowSize);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Map<String, Integer> setTasPolicy(int newTasPolicy) {
        try {
            return this.mService.setTasPolicy(newTasPolicy, -1);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerTasPolicyChangedListener(TasPolicyListener listener) {
        try {
            TasPolicyListener.TasPolicyListenerProxy proxy = listener.getProxy();
            proxy.initProxy(this.mContext.getMainExecutor(), listener);
            this.mService.registerTasPolicyChangedListener(proxy);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterTasPolicyChangedListener(TasPolicyListener listener) {
        try {
            TasPolicyListener.TasPolicyListenerProxy proxy = listener.getProxy();
            this.mService.unregisterTasPolicyChangedListener(proxy);
            proxy.cleanUpProxy();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void enableTxPowerLogging(boolean enable, int index) {
        try {
            this.mService.enableTxPowerLogging(enable, index);
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

    public boolean isSwitchToMobileDataDefaultOff() {
        try {
            return this.mService.isSwitchToMobileDataDefaultOff();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setMhsAiServiceState(boolean enabled, int[] numClass, int[] numTimeStep) {
        try {
            this.mService.setMhsAiServiceState(enabled, numClass, numTimeStep);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setMhsAiServiceNsdResult(int[] predArr, String[] convoStrArr) {
        try {
            this.mService.setMhsAiServiceNsdResult(predArr, convoStrArr);
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

            void initProxy(Executor executor, TasPolicyListener listener) {
                synchronized (this.mLock) {
                    this.mExecutor = executor;
                    this.mListener = listener;
                }
            }

            void cleanUpProxy() {
                synchronized (this.mLock) {
                    this.mExecutor = null;
                    this.mListener = null;
                }
            }

            @Override // com.samsung.android.wifi.SemTasPolicyListener
            public void onTasPolicyChanged(final int newTasPolicy, final int windowSize) throws RemoteException {
                final TasPolicyListener listener;
                Executor executor;
                synchronized (this.mLock) {
                    listener = this.mListener;
                    executor = this.mExecutor;
                }
                if (listener == null || executor == null) {
                    return;
                }
                Binder.clearCallingIdentity();
                executor.execute(new Runnable() { // from class: com.samsung.android.wifi.SemWifiManager$TasPolicyListener$TasPolicyListenerProxy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemWifiManager.TasPolicyListener.this.onTasPolicyChanged(newTasPolicy, windowSize);
                    }
                });
            }
        }
    }

    public int startCapture(int captureFrameTypes) {
        try {
            return this.mService.startCapture(captureFrameTypes);
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

    public void setMcfMultiControlMode(boolean enable) {
        try {
            this.mService.setMcfMultiControlMode(enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class AbTestConfigUpdateObserver {
        public void onRegistered(AbTestConfigSubscription subscription) {
        }

        public void onUpdated(SemAbTestConfiguration config) {
        }
    }

    public class AbTestConfigSubscription implements AutoCloseable {
        private final String mModule;
        private final AbTestConfigUpdateObserver mObserver;
        private final CloseGuard mCloseGuard = new CloseGuard();
        private final Object mLock = new Object();
        private boolean mClosed = false;

        public AbTestConfigSubscription(AbTestConfigUpdateObserver observer, String module) {
            this.mObserver = observer;
            this.mModule = module;
            this.mCloseGuard.open("close");
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            try {
                try {
                    synchronized (this.mLock) {
                        if (!this.mClosed) {
                            this.mClosed = true;
                            SemWifiManager.this.unregisterAbTestConfigUpdateObserver(this.mObserver);
                            this.mCloseGuard.close();
                        }
                    }
                } catch (Exception e) {
                    Log.e(SemWifiManager.TAG, "Failed to unregister AbTestConfigUpdateObserver.");
                }
            } finally {
                Reference.reachabilityFence(this);
            }
        }

        protected void finalize() throws Throwable {
            try {
                if (this.mCloseGuard != null) {
                    this.mCloseGuard.warnIfOpen();
                }
                close();
            } finally {
                super.finalize();
            }
        }

        public String toString() {
            return "AbTestConfigSubscription{mObserver=" + this.mObserver + ", mModule='" + this.mModule + DateFormat.QUOTE + '}';
        }
    }

    public void registerAbTestConfigUpdateObserver(AbTestConfigUpdateObserver observer, String module) {
        if (observer == null) {
            throw new IllegalArgumentException("observer cannot be null");
        }
        Executor executor = this.mContext.getMainExecutor();
        synchronized (sSemAbTestConfigurationUpdateObserverMap) {
            AbTestConfigUpdateObserverProxy abtestConfigObserverProxy = new AbTestConfigUpdateObserverProxy(this, executor, observer, module);
            try {
                sSemAbTestConfigurationUpdateObserverMap.put(System.identityHashCode(observer), abtestConfigObserverProxy);
                this.mService.registerAbTestConfigUpdateObserver(abtestConfigObserverProxy, module);
                abtestConfigObserverProxy.registered();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterAbTestConfigUpdateObserver(AbTestConfigUpdateObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("observer cannot be null");
        }
        synchronized (sSemAbTestConfigurationUpdateObserverMap) {
            int callbackIdentifier = System.identityHashCode(observer);
            if (!sSemAbTestConfigurationUpdateObserverMap.contains(callbackIdentifier)) {
                Log.e(TAG, "Unknown external observer " + callbackIdentifier);
                return;
            }
            try {
                this.mService.unregisterAbTestConfigUpdateObserver(sSemAbTestConfigurationUpdateObserverMap.get(callbackIdentifier));
                sSemAbTestConfigurationUpdateObserverMap.remove(callbackIdentifier);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void reportAbTestResult(String module, String outputDim, String output) {
        try {
            this.mService.reportAbTestResult(module, outputDim, output);
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

    public SemAbTestConfiguration getAbTestConfiguredModule(String module) {
        try {
            return this.mService.getAbTestConfiguredModule(module);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean sendReassociationFrequencyRequestFrame(String bssid, int channel) {
        try {
            return this.mService.sendReassociationFrequencyRequestFrame(bssid, channel);
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

        AbTestConfigUpdateObserverProxy(SemWifiManager manager, Executor executor, AbTestConfigUpdateObserver observer, String module) {
            this.mWifiManager = new WeakReference<>(manager);
            this.mExecutor = executor;
            this.mObserver = observer;
            this.mModule = module;
        }

        public void registered() throws RemoteException {
            final SemWifiManager manager = this.mWifiManager.get();
            if (manager == null) {
                return;
            }
            this.mExecutor.execute(new Runnable() { // from class: com.samsung.android.wifi.SemWifiManager$AbTestConfigUpdateObserverProxy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SemWifiManager.AbTestConfigUpdateObserverProxy.this.lambda$registered$0(manager);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$registered$0(SemWifiManager manager) {
            AbTestConfigUpdateObserver abTestConfigUpdateObserver = this.mObserver;
            Objects.requireNonNull(manager);
            abTestConfigUpdateObserver.onRegistered(manager.new AbTestConfigSubscription(this.mObserver, this.mModule));
        }

        @Override // com.samsung.android.wifi.ISemAbTestConfigurationUpdateObserver
        public void notifyAbTestConfigUpdate(final SemAbTestConfiguration config) {
            this.mExecutor.execute(new Runnable() { // from class: com.samsung.android.wifi.SemWifiManager$AbTestConfigUpdateObserverProxy$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SemWifiManager.AbTestConfigUpdateObserverProxy.this.lambda$notifyAbTestConfigUpdate$1(config);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyAbTestConfigUpdate$1(SemAbTestConfiguration config) {
            this.mObserver.onUpdated(config);
        }
    }
}
