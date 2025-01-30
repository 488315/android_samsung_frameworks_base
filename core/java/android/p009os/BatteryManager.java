package android.p009os;

import android.annotation.SystemApi;
import android.content.Context;
import com.android.internal.app.IBatteryStats;
import com.samsung.android.p042os.SemCompanionDeviceBatteryInfo;

/* loaded from: classes3.dex */
public class BatteryManager {
  public static final String ACTION_CHARGING = "android.os.action.CHARGING";
  public static final String ACTION_DISCHARGING = "android.os.action.DISCHARGING";
  public static final String ACTION_POPUP_BATTERY_DETERIORATION =
      "com.samsung.server.BatteryService.action.ACTION_POPUP_BATTERY_DETERIORATION";
  public static final String ACTION_SEC_BATTERY_EVENT =
      "com.samsung.server.BatteryService.action.SEC_BATTERY_EVENT";
  public static final String ACTION_SEC_BATTERY_WATER_IN_CONNECTOR =
      "com.samsung.server.BatteryService.action.SEC_BATTERY_WATER_IN_CONNECTOR";
  public static final String ACTION_SLEEP_CHARGING =
      "com.samsung.server.BatteryService.action.ACTION_SLEEP_CHARGING";
  public static final String ACTION_WIRELESS_POWER_SHARING_CONNECTED =
      "com.samsung.server.BatteryService.action.WIRELESS_POWER_SHARING_CONNECTED";
  public static final String ACTION_WIRELESS_POWER_SHARING_ENABLED =
      "com.samsung.server.BatteryService.action.WIRELESS_POWER_SHARING_ENABLED";
  public static final String ACTION_WIRELESS_POWER_SHARING_TX_EVENT =
      "com.samsung.server.BatteryService.action.WIRELESS_POWER_SHARING_TX_EVENT";
  public static final int BATTERY_CHARGER_TYPE_FAST = 1;
  public static final int BATTERY_CHARGER_TYPE_FAST_12V = 2;
  public static final int BATTERY_CHARGER_TYPE_FAST_25W = 3;
  public static final int BATTERY_CHARGER_TYPE_FAST_45W = 4;
  public static final int BATTERY_CHARGER_TYPE_NORMAL = 0;
  public static final int BATTERY_DETERIORATION_AGED = 3;
  public static final int BATTERY_DETERIORATION_BAD = 15;
  public static final int BATTERY_DETERIORATION_GOOD = 1;
  public static final int BATTERY_DETERIORATION_NORMAL = 2;
  public static final int BATTERY_DETERIORATION_UNKNOWN = 0;
  public static final int BATTERY_HEALTH_COLD = 7;
  public static final int BATTERY_HEALTH_DEAD = 4;
  public static final int BATTERY_HEALTH_GOOD = 2;
  public static final int BATTERY_HEALTH_OVERHEAT = 3;
  public static final int BATTERY_HEALTH_OVER_VOLTAGE = 5;
  public static final int BATTERY_HEALTH_UNKNOWN = 1;
  public static final int BATTERY_HEALTH_UNSPECIFIED_FAILURE = 6;
  public static final int BATTERY_MISC_EVENT_ABNORMAL_PAD = 2097152;
  public static final int BATTERY_MISC_EVENT_DETERIORATION = 983040;
  public static final int BATTERY_MISC_EVENT_DIRECT_POWER_MODE = 16384;
  public static final int BATTERY_MISC_EVENT_FULL_CAPACITY = 16777216;
  public static final int BATTERY_MISC_EVENT_HICCUP_ENABLED = 32;
  public static final int BATTERY_MISC_EVENT_OVERHEAT_LIMIT = 1048576;
  public static final int BATTERY_MISC_EVENT_RECHARGE = 8;
  public static final int BATTERY_MISC_EVENT_TEMP_HICCUP_TYPE = 8192;
  public static final int BATTERY_MISC_EVENT_TIMEOUT_OPEN_TYPE = 4;
  public static final int BATTERY_MISC_EVENT_WATER_IN_CONNECTOR = 1;
  public static final int BATTERY_MISC_EVENT_WATER_IN_POGO = 16;
  public static final int BATTERY_MISC_EVENT_WIRELESS_AUTH_FAIL = 2048;
  public static final int BATTERY_MISC_EVENT_WIRELESS_AUTH_RECEIVED = 1024;
  public static final int BATTERY_MISC_EVENT_WIRELESS_AUTH_START = 512;
  public static final int BATTERY_MISC_EVENT_WIRELESS_BACKPACK = 2;
  public static final int BATTERY_MISC_EVENT_WIRELESS_DET_LEVEL = 64;
  public static final int BATTERY_MISC_EVENT_WIRELESS_FOD = 256;
  public static final int BATTERY_MISC_EVENT_WIRELESS_MISALIGN = 4194304;
  public static final int BATTERY_ONLINE_CABLE_SILENT_TYPE = 99;
  public static final int BATTERY_ONLINE_FAST_WIRELESS_CHARGER = 100;
  public static final int BATTERY_ONLINE_INCOMPATIBLE_CHARGER = 0;
  public static final int BATTERY_ONLINE_NONE = 1;
  public static final int BATTERY_ONLINE_POGO = 23;
  public static final int BATTERY_ONLINE_TA = 3;
  public static final int BATTERY_ONLINE_USB = 4;
  public static final int BATTERY_ONLINE_WATER_IN_CONNECTOR = 101;
  public static final int BATTERY_ONLINE_WIRELESS_CHARGER = 10;
  public static final int BATTERY_PLUGGED_AC = 1;
  public static final int BATTERY_PLUGGED_ANY = 15;
  public static final int BATTERY_PLUGGED_DOCK = 8;
  public static final int BATTERY_PLUGGED_OTG = 65536;
  public static final int BATTERY_PLUGGED_USB = 2;
  public static final int BATTERY_PLUGGED_WIRELESS = 4;
  public static final int BATTERY_PROPERTY_CAPACITY = 4;
  public static final int BATTERY_PROPERTY_CHARGE_COUNTER = 1;

  @SystemApi public static final int BATTERY_PROPERTY_CHARGING_POLICY = 9;
  public static final int BATTERY_PROPERTY_CURRENT_AVERAGE = 3;
  public static final int BATTERY_PROPERTY_CURRENT_NOW = 2;
  public static final int BATTERY_PROPERTY_ENERGY_COUNTER = 5;

  @SystemApi public static final int BATTERY_PROPERTY_FIRST_USAGE_DATE = 8;

  @SystemApi public static final int BATTERY_PROPERTY_MANUFACTURING_DATE = 7;

  @SystemApi public static final int BATTERY_PROPERTY_STATE_OF_HEALTH = 10;
  public static final int BATTERY_PROPERTY_STATUS = 6;
  public static final int BATTERY_STATUS_CHARGING = 2;
  public static final int BATTERY_STATUS_DISCHARGING = 3;
  public static final int BATTERY_STATUS_FULL = 5;
  public static final int BATTERY_STATUS_NOT_CHARGING = 4;
  public static final int BATTERY_STATUS_UNKNOWN = 1;
  public static final int BATTERY_TX_EVENT_WIRELESS_POWER_SHARING_RX_CHG_SWITCH = 32;
  public static final int BATTERY_TX_EVENT_WIRELESS_POWER_SHARING_RX_CONNECTED = 2;
  public static final int BATTERY_TX_EVENT_WIRELESS_POWER_SHARING_RX_CS100 = 64;
  public static final int BATTERY_TX_EVENT_WIRELESS_POWER_SHARING_RX_UNSAFE_TEMP = 16;
  public static final int BATTERY_TX_EVENT_WIRELESS_POWER_SHARING_TX_5V_TA = 65536;
  public static final int BATTERY_TX_EVENT_WIRELESS_POWER_SHARING_TX_CAMERA_ON = 2048;
  public static final int BATTERY_TX_EVENT_WIRELESS_POWER_SHARING_TX_CRITICAL_EOC = 1024;
  public static final int BATTERY_TX_EVENT_WIRELESS_POWER_SHARING_TX_ENABLED = 1;
  public static final int BATTERY_TX_EVENT_WIRELESS_POWER_SHARING_TX_ETC = 16384;
  public static final int BATTERY_TX_EVENT_WIRELESS_POWER_SHARING_TX_FOD = 4;
  public static final int BATTERY_TX_EVENT_WIRELESS_POWER_SHARING_TX_HIGH_TEMP = 8;
  public static final int BATTERY_TX_EVENT_WIRELESS_POWER_SHARING_TX_LOW_TEMP = 256;
  public static final int BATTERY_TX_EVENT_WIRELESS_POWER_SHARING_TX_MISALIGN = 8192;
  public static final int BATTERY_TX_EVENT_WIRELESS_POWER_SHARING_TX_OCP = 4096;
  public static final int BATTERY_TX_EVENT_WIRELESS_POWER_SHARING_TX_OTG_ON = 128;
  public static final int BATTERY_TX_EVENT_WIRELESS_POWER_SHARING_TX_RETRY = 32768;
  public static final int BATTERY_TX_EVENT_WIRELESS_POWER_SHARING_TX_SOC_DRAIN = 512;
  public static final int BATTERY_WIRELESS_POWER_SHARING_EXTERNEL_EVENT_CALL = 4;
  public static final int BATTERY_WIRELESS_POWER_SHARING_EXTERNEL_EVENT_CAMERA = 1;
  public static final int BATTERY_WIRELESS_POWER_SHARING_EXTERNEL_EVENT_DEX = 2;

  @SystemApi public static final int CHARGING_POLICY_ADAPTIVE_AC = 3;

  @SystemApi public static final int CHARGING_POLICY_ADAPTIVE_AON = 2;

  @SystemApi public static final int CHARGING_POLICY_ADAPTIVE_LONGLIFE = 4;

  @SystemApi public static final int CHARGING_POLICY_DEFAULT = 1;
  public static final String EXTRA_BATTERY_LOW = "battery_low";
  public static final String EXTRA_CAPACITY = "capacity";
  public static final String EXTRA_CHARGER_TYPE = "charger_type";
  public static final String EXTRA_CHARGE_COUNTER = "charge_counter";
  public static final String EXTRA_CHARGE_TYPE = "charge_type";
  public static final String EXTRA_CHARGING_STATUS = "android.os.extra.CHARGING_STATUS";
  public static final String EXTRA_CURRENT_AVG = "current_avg";
  public static final String EXTRA_CURRENT_NOW = "current_now";
  public static final String EXTRA_CYCLE_COUNT = "android.os.extra.CYCLE_COUNT";
  public static final String EXTRA_DETERIORATION = "deterioration";

  @SystemApi public static final String EXTRA_EVENTS = "android.os.extra.EVENTS";

  @SystemApi public static final String EXTRA_EVENT_TIMESTAMP = "android.os.extra.EVENT_TIMESTAMP";
  public static final String EXTRA_HEALTH = "health";
  public static final String EXTRA_HIGHVOLTAGE_CHARGER = "hv_charger";
  public static final String EXTRA_ICON_SMALL = "icon-small";
  public static final String EXTRA_INVALID_CHARGER = "invalid_charger";
  public static final String EXTRA_IS_SLEEP_CHARGING_COMPLETE_SUCCESS =
      "is_sleep_charging_complete_success";
  public static final String EXTRA_LEVEL = "level";
  public static final String EXTRA_MAX_CHARGING_CURRENT = "max_charging_current";
  public static final String EXTRA_MAX_CHARGING_VOLTAGE = "max_charging_voltage";
  public static final String EXTRA_MISC_EVENT = "misc_event";
  public static final String EXTRA_ONLINE = "online";
  public static final String EXTRA_OTG_CHARGE_BLOCK_ENABLE = "OTG_CHARGE_BLOCK";
  public static final String EXTRA_PLUGGED = "plugged";
  public static final String EXTRA_POGO_CONDITION = "pogo_plugged";
  public static final String EXTRA_POWER_SHARING = "power_sharing";
  public static final String EXTRA_POWER_SHARING_ENABLE = "power_sharing_enable";
  public static final String EXTRA_PRESENT = "present";
  public static final String EXTRA_RX_CONNECTED = "connected";
  public static final String EXTRA_SCALE = "scale";
  public static final String EXTRA_SEC_CURRENT_EVENT = "current_event";
  public static final String EXTRA_SEC_PLUG_TYPE_SUMMARY = "sec_plug_type";
  public static final String EXTRA_SELF_DISCHARGING = "self_discharging";
  public static final String EXTRA_SEQUENCE = "seq";
  public static final String EXTRA_SLEEP_CHARGING_EVENT = "sleep_charging_event";
  public static final String EXTRA_SLEEP_CHARGING_EXPECTED_FULL_CHARGE_TIME =
      "sleep_charging_expected_full_charge_time";
  public static final String EXTRA_SLEEP_CHARGING_FINISH_TIME = "sleep_charging_finish_time";
  public static final String EXTRA_STATUS = "status";
  public static final String EXTRA_TECHNOLOGY = "technology";
  public static final String EXTRA_TEMPERATURE = "temperature";
  public static final String EXTRA_TX_ENABLED = "enabled";
  public static final String EXTRA_TX_EVENT = "tx_event";
  public static final String EXTRA_VOLTAGE = "voltage";
  public static final String EXTRA_WATER = "water";
  public static final String EXTRA_WC_TX_ID = "wc_tx_id";
  public static final String SEM_ACTION_BATTERY_INFO_ADDED =
      "com.samsung.battery.ACTION_BATTERY_INFO_ADDED";
  public static final String SEM_ACTION_BATTERY_INFO_CHANGED =
      "com.samsung.battery.ACTION_BATTERY_INFO_CHANGED";
  public static final String SEM_ACTION_BATTERY_INFO_REMOVED =
      "com.samsung.battery.ACTION_BATTERY_INFO_REMOVED";
  public static final int SEM_BATTERY_INFO_DEVICE_TYPE_BUDS = 3;
  public static final int SEM_BATTERY_INFO_DEVICE_TYPE_BUDS_OLD = 11;
  public static final int SEM_BATTERY_INFO_DEVICE_TYPE_FIT = 6;
  public static final int SEM_BATTERY_INFO_DEVICE_TYPE_PHONE = 2;
  public static final int SEM_BATTERY_INFO_DEVICE_TYPE_RING = 7;
  public static final int SEM_BATTERY_INFO_DEVICE_TYPE_SPEN = 5;
  public static final int SEM_BATTERY_INFO_DEVICE_TYPE_SPEN_EXTERNAL = 12;
  public static final int SEM_BATTERY_INFO_DEVICE_TYPE_UNKNOWN = 1;
  public static final int SEM_BATTERY_INFO_DEVICE_TYPE_WATCH = 4;
  public static final String SEM_EXTRA_BATTERY_INFO = "com.samsung.battery.EXTRA_BATTERY_INFO";
  private final IBatteryPropertiesRegistrar mBatteryPropertiesRegistrar;
  private final IBatteryStats mBatteryStats;
  private final Context mContext;

  public BatteryManager() {
    this.mContext = null;
    this.mBatteryStats = IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats"));
    this.mBatteryPropertiesRegistrar =
        IBatteryPropertiesRegistrar.Stub.asInterface(
            ServiceManager.getService("batteryproperties"));
  }

  public BatteryManager(
      Context context,
      IBatteryStats batteryStats,
      IBatteryPropertiesRegistrar batteryPropertiesRegistrar) {
    this.mContext = context;
    this.mBatteryStats = batteryStats;
    this.mBatteryPropertiesRegistrar = batteryPropertiesRegistrar;
  }

  public boolean isCharging() {
    try {
      return this.mBatteryStats.isCharging();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  private long queryProperty(int id) {
    if (this.mBatteryPropertiesRegistrar == null) {
      return Long.MIN_VALUE;
    }
    try {
      BatteryProperty prop = new BatteryProperty();
      if (this.mBatteryPropertiesRegistrar.getProperty(id, prop) == 0) {
        long ret = prop.getLong();
        return ret;
      }
      return Long.MIN_VALUE;
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public int getIntProperty(int id) {
    Context context;
    long value = queryProperty(id);
    if (value == Long.MIN_VALUE
        && (context = this.mContext) != null
        && context.getApplicationInfo().targetSdkVersion >= 28) {
      return Integer.MIN_VALUE;
    }
    return (int) value;
  }

  public long getLongProperty(int id) {
    return queryProperty(id);
  }

  public static boolean isPlugWired(int plugType) {
    return plugType == 2 || plugType == 1;
  }

  public long computeChargeTimeRemaining() {
    try {
      return this.mBatteryStats.computeChargeTimeRemaining();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @SystemApi
  public boolean setChargingStateUpdateDelayMillis(int delayMillis) {
    try {
      return this.mBatteryStats.setChargingStateUpdateDelayMillis(delayMillis);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public SemCompanionDeviceBatteryInfo[] semGetDeviceBatteryInfos() throws SecurityException {
    try {
      SemCompanionDeviceBatteryInfo[] parcelers = this.mBatteryStats.getDeviceBatteryInfos();
      return parcelers;
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public SemCompanionDeviceBatteryInfo semGetDeviceBatteryInfo(String address)
      throws SecurityException {
    try {
      SemCompanionDeviceBatteryInfo batteryInfo = this.mBatteryStats.getDeviceBatteryInfo(address);
      return batteryInfo;
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void semRegisterDeviceBatteryInfoChanged(String packageName) throws SecurityException {
    try {
      this.mBatteryStats.registerDeviceBatteryInfoChanged(packageName);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void semUnRegisterDeviceBatteryInfoChanged(String packageName) throws SecurityException {
    try {
      this.mBatteryStats.unRegisterDeviceBatteryInfoChanged(packageName);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void semSetDeviceBatteryInfo(String address, SemCompanionDeviceBatteryInfo info)
      throws SecurityException {
    try {
      this.mBatteryStats.setDeviceBatteryInfo(address, info);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void semUnsetDeviceBatteryInfo(String address) throws SecurityException {
    try {
      this.mBatteryStats.unsetDeviceBatteryInfo(address);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }
}
