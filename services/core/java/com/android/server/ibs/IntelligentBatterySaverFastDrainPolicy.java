package com.android.server.ibs;

import android.app.AlarmManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.hardware.display.DisplayManager;
import android.media.AudioManager;
import android.net.TrafficStats;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Slog;

import com.android.internal.os.PowerProfile;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class IntelligentBatterySaverFastDrainPolicy {
    public final ArrayList mActionsLevel;
    public AlarmManager mAlarmManager;
    public final IntelligentBatterySaverFastDrainBatteryInfo mBatteryInfo;
    public boolean mCharging;
    public final Context mContext;
    public DisplayManager mDisplayManager;
    public LocalTime mEndTime;
    public int mFastDrainInternalState;
    public float mFastDropCurrent;
    public IntentFilter mFilter;
    public final IntelligentBatterySaverFastDrainHandler mHandler;
    public final IntelligentBatterySaverBatteryBigData mIBSBigData;
    public final IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0
            mInactiveTimeoutAlarmListener;
    public boolean mInited;
    public final IntelligentBatterySaverLogger mIntelligentBatterySaverLogger;
    public final IntelligentBatterySaverSurvey mIntelligentBatterySaverSurvey;
    public Sensor mMotionSensor;
    public final IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0
            mMotionTimeoutAlarmListener;
    public PowerProfile mPowerProfile;
    public IntelligentBatterySaverFastDrainReceiver mReceiver;
    public final IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0
            mSafeCheckTimeoutAlarmListener;
    public boolean mScreenOn;
    public SensorManager mSensorManager;
    public SharedPreferences mSharedPreferences;
    public LocalTime mStartTime;
    public int mSysState;
    public final IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0
            mTimeoutAlarmListener;
    public final IntelligentBatterySaverFastDrainTrafficStat mTrafiicStat;
    public final Object mActionsLock = new Object();
    public final MotionListener mMotionListener = new MotionListener();
    public float mEstimatedBatteryCapacity = 3300.0f;
    public long mEnterIBSTime = 0;
    public int mEnterIBSBatteryLevel = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ActionEntry {
        public IIntelligentBatterySaverFastDrainCallBack callBack;
        public String tag;

        public final boolean equals(Object obj) {
            if (!(obj instanceof ActionEntry)) {
                return super.equals(obj);
            }
            ActionEntry actionEntry = (ActionEntry) obj;
            return this.tag.equals(actionEntry.tag) && this.callBack == actionEntry.callBack;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface IIntelligentBatterySaverFastDrainCallBack {
        void cancelFastDrainRestriction();

        void doFastDrainRestriction();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IntelligentBatterySaverBatteryBigData {
        public boolean actionEnabled;
        public float drainHightCurrent;
        public boolean initialized;
        public float restrictedCurrent;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IntelligentBatterySaverFastDrainAction {
        public final AnonymousClass1 btCallBack;
        public final AnonymousClass1 gpsCallBack;
        public final Context mContext;
        public final AnonymousClass2 mobiledataCallBack;
        public final AnonymousClass2 wifiCallBack;

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy$IntelligentBatterySaverFastDrainAction$1] */
        /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy$IntelligentBatterySaverFastDrainAction$2] */
        /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy$IntelligentBatterySaverFastDrainAction$2] */
        /* JADX WARN: Type inference failed for: r1v4, types: [com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy$IntelligentBatterySaverFastDrainAction$1] */
        public IntelligentBatterySaverFastDrainAction(Context context) {
            this.mContext = context;
            final int i = 0;
            this.gpsCallBack =
                    new IIntelligentBatterySaverFastDrainCallBack(this) { // from class:
                        // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IntelligentBatterySaverFastDrainAction.1
                        public final /* synthetic */ IntelligentBatterySaverFastDrainAction this$1;

                        {
                            this.this$1 = this;
                        }

                        @Override // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IIntelligentBatterySaverFastDrainCallBack
                        public final void cancelFastDrainRestriction() {
                            int i2;
                            switch (i) {
                                case 0:
                                    IntelligentBatterySaverFastDrainAction
                                            intelligentBatterySaverFastDrainAction = this.this$1;
                                    IntelligentBatterySaverFastDrainPolicy
                                            intelligentBatterySaverFastDrainPolicy =
                                                    IntelligentBatterySaverFastDrainPolicy.this;
                                    if (intelligentBatterySaverFastDrainPolicy.mSharedPreferences
                                                    == null
                                            && intelligentBatterySaverFastDrainPolicy.mContext
                                                    != null) {
                                        Slog.d(
                                                "IntelligentBatterySaverFastDrainPolicy",
                                                "Create shared preferences when get state.");
                                        intelligentBatterySaverFastDrainPolicy.mSharedPreferences =
                                                intelligentBatterySaverFastDrainPolicy.mContext
                                                        .getSharedPreferences("oper_pref", 0);
                                    }
                                    SharedPreferences sharedPreferences =
                                            intelligentBatterySaverFastDrainPolicy
                                                    .mSharedPreferences;
                                    if (sharedPreferences == null) {
                                        i2 = 0;
                                    } else {
                                        i2 = sharedPreferences.getInt("disable_gps_by_ibs", 0);
                                        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                                i2,
                                                "getIntState type = disable_gps_by_ibs state = ",
                                                "IntelligentBatterySaverFastDrainPolicy");
                                    }
                                    if (i2 != 0) {
                                        if (1
                                                        != Settings.System.getInt(
                                                                intelligentBatterySaverFastDrainAction
                                                                        .mContext
                                                                        .getContentResolver(),
                                                                "display_night_theme_scheduled",
                                                                0)
                                                || 1
                                                        != Settings.System.getInt(
                                                                intelligentBatterySaverFastDrainAction
                                                                        .mContext
                                                                        .getContentResolver(),
                                                                "display_night_theme_scheduled_type",
                                                                2)) {
                                            Settings.Secure.putInt(
                                                    intelligentBatterySaverFastDrainAction.mContext
                                                            .getContentResolver(),
                                                    "location_mode",
                                                    i2);
                                            StringBuilder sb = new StringBuilder("gpsState = ");
                                            sb.append(i2);
                                            BootReceiver$$ExternalSyntheticOutline0.m(
                                                    sb,
                                                    " recover gps",
                                                    "IntelligentBatterySaverFastDrainAction");
                                            IntelligentBatterySaverFastDrainPolicy
                                                    intelligentBatterySaverFastDrainPolicy2 =
                                                            IntelligentBatterySaverFastDrainPolicy
                                                                    .this;
                                            intelligentBatterySaverFastDrainPolicy2
                                                    .mIntelligentBatterySaverLogger.add(
                                                    "recover gps");
                                            IntelligentBatterySaverFastDrainPolicy
                                                    .m580$$Nest$msaveIntState(
                                                            intelligentBatterySaverFastDrainPolicy2,
                                                            0);
                                            break;
                                        }
                                    }
                                    break;
                                default:
                                    IntelligentBatterySaverFastDrainAction
                                            intelligentBatterySaverFastDrainAction2 = this.this$1;
                                    BluetoothAdapter adapter =
                                            ((BluetoothManager)
                                                            intelligentBatterySaverFastDrainAction2
                                                                    .mContext.getSystemService(
                                                                    "bluetooth"))
                                                    .getAdapter();
                                    IntelligentBatterySaverFastDrainPolicy
                                            intelligentBatterySaverFastDrainPolicy3 =
                                                    IntelligentBatterySaverFastDrainPolicy.this;
                                    if (intelligentBatterySaverFastDrainPolicy3.getBooleanState(
                                                    "disable_bt_by_ibs")
                                            && adapter != null
                                            && !adapter.isEnabled()) {
                                        Slog.d(
                                                "IntelligentBatterySaverFastDrainAction",
                                                "Recover BT");
                                        intelligentBatterySaverFastDrainPolicy3
                                                .mIntelligentBatterySaverLogger.add("Recover BT");
                                        adapter.enable();
                                    }
                                    intelligentBatterySaverFastDrainPolicy3.saveBooleanState(
                                            "disable_bt_by_ibs", false);
                                    break;
                            }
                        }

                        @Override // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IIntelligentBatterySaverFastDrainCallBack
                        public final void doFastDrainRestriction() {
                            switch (i) {
                                case 0:
                                    IntelligentBatterySaverFastDrainAction
                                            intelligentBatterySaverFastDrainAction = this.this$1;
                                    int i2 =
                                            Settings.Secure.getInt(
                                                    intelligentBatterySaverFastDrainAction.mContext
                                                            .getContentResolver(),
                                                    "location_mode",
                                                    0);
                                    if (i2 != 0) {
                                        if (1
                                                        != Settings.System.getInt(
                                                                intelligentBatterySaverFastDrainAction
                                                                        .mContext
                                                                        .getContentResolver(),
                                                                "display_night_theme_scheduled",
                                                                0)
                                                || 1
                                                        != Settings.System.getInt(
                                                                intelligentBatterySaverFastDrainAction
                                                                        .mContext
                                                                        .getContentResolver(),
                                                                "display_night_theme_scheduled_type",
                                                                2)) {
                                            Settings.Secure.putInt(
                                                    intelligentBatterySaverFastDrainAction.mContext
                                                            .getContentResolver(),
                                                    "location_mode",
                                                    0);
                                            StringBuilder sb = new StringBuilder("gpsState = ");
                                            sb.append(i2);
                                            BootReceiver$$ExternalSyntheticOutline0.m(
                                                    sb,
                                                    " gps set disable",
                                                    "IntelligentBatterySaverFastDrainAction");
                                            IntelligentBatterySaverFastDrainPolicy
                                                    intelligentBatterySaverFastDrainPolicy =
                                                            IntelligentBatterySaverFastDrainPolicy
                                                                    .this;
                                            intelligentBatterySaverFastDrainPolicy
                                                    .mIntelligentBatterySaverLogger.add(
                                                    "gpsState = " + i2 + " gps set disable");
                                            IntelligentBatterySaverFastDrainPolicy
                                                    .m580$$Nest$msaveIntState(
                                                            intelligentBatterySaverFastDrainPolicy,
                                                            i2);
                                            break;
                                        }
                                    }
                                    break;
                                default:
                                    IntelligentBatterySaverFastDrainAction
                                            intelligentBatterySaverFastDrainAction2 = this.this$1;
                                    IntelligentBatterySaverFastDrainPolicy
                                            intelligentBatterySaverFastDrainPolicy2 =
                                                    IntelligentBatterySaverFastDrainPolicy.this;
                                    intelligentBatterySaverFastDrainPolicy2.getClass();
                                    BluetoothAdapter defaultAdapter =
                                            BluetoothAdapter.getDefaultAdapter();
                                    BluetoothManager bluetoothManager =
                                            (BluetoothManager)
                                                    intelligentBatterySaverFastDrainPolicy2.mContext
                                                            .getSystemService("bluetooth");
                                    boolean z = false;
                                    boolean z2 =
                                            defaultAdapter != null
                                                    && defaultAdapter.isEnabled()
                                                    && (defaultAdapter.getBondedDevices().stream()
                                                                    .anyMatch(
                                                                            new IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda5())
                                                            || bluetoothManager
                                                                                    .getConnectedDevices(
                                                                                            7)
                                                                                    .size()
                                                                            + bluetoothManager
                                                                                    .getConnectedDevices(
                                                                                            8)
                                                                                    .size()
                                                                    > 0);
                                    BluetoothAdapter adapter =
                                            ((BluetoothManager)
                                                            intelligentBatterySaverFastDrainAction2
                                                                    .mContext.getSystemService(
                                                                    "bluetooth"))
                                                    .getAdapter();
                                    if (adapter != null && adapter.isEnabled()) {
                                        z = true;
                                    }
                                    Slog.d(
                                            "IntelligentBatterySaverFastDrainAction",
                                            "Disable BT check btEnabled= "
                                                    + z
                                                    + ", btConnected = "
                                                    + z2);
                                    IntelligentBatterySaverFastDrainPolicy
                                            intelligentBatterySaverFastDrainPolicy3 =
                                                    IntelligentBatterySaverFastDrainPolicy.this;
                                    intelligentBatterySaverFastDrainPolicy3
                                            .mIntelligentBatterySaverLogger.add(
                                            "BlueTooth btEnabled= " + z + ", btConnected = " + z2);
                                    if (z && !z2) {
                                        Slog.d(
                                                "IntelligentBatterySaverFastDrainAction",
                                                "Disable BT");
                                        intelligentBatterySaverFastDrainPolicy3
                                                .mIntelligentBatterySaverLogger.add("Disable BT");
                                        adapter.disable();
                                        intelligentBatterySaverFastDrainPolicy3.saveBooleanState(
                                                "disable_bt_by_ibs", true);
                                        break;
                                    }
                                    break;
                            }
                        }
                    };
            this.wifiCallBack =
                    new IIntelligentBatterySaverFastDrainCallBack(this) { // from class:
                        // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IntelligentBatterySaverFastDrainAction.2
                        public final /* synthetic */ IntelligentBatterySaverFastDrainAction this$1;
                        public Object wifiManager;
                        public boolean wifiState = false;
                        public boolean wifiApState = true;

                        {
                            this.this$1 = this;
                        }

                        @Override // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IIntelligentBatterySaverFastDrainCallBack
                        public final void cancelFastDrainRestriction() {
                            switch (i) {
                                case 0:
                                    IntelligentBatterySaverFastDrainAction
                                            intelligentBatterySaverFastDrainAction = this.this$1;
                                    WifiManager wifiManager =
                                            (WifiManager)
                                                    intelligentBatterySaverFastDrainAction.mContext
                                                            .getSystemService("wifi");
                                    this.wifiManager = wifiManager;
                                    IntelligentBatterySaverFastDrainPolicy
                                            intelligentBatterySaverFastDrainPolicy =
                                                    IntelligentBatterySaverFastDrainPolicy.this;
                                    if (wifiManager != null) {
                                        this.wifiState = wifiManager.isWifiEnabled();
                                        Slog.d(
                                                "IntelligentBatterySaverFastDrainAction",
                                                "Recover wifi check wifiState = "
                                                        + this.wifiState
                                                        + ", operWifiState = "
                                                        + intelligentBatterySaverFastDrainPolicy
                                                                .getBooleanState(
                                                                        "disable_wifi_by_ibs"));
                                        if (!this.wifiState
                                                && intelligentBatterySaverFastDrainPolicy
                                                        .getBooleanState("disable_wifi_by_ibs")) {
                                            Slog.d(
                                                    "IntelligentBatterySaverFastDrainAction",
                                                    "recover wifi");
                                            intelligentBatterySaverFastDrainPolicy
                                                    .mIntelligentBatterySaverLogger.add(
                                                    "recover wifi");
                                            ((WifiManager) this.wifiManager).setWifiEnabled(true);
                                        }
                                    }
                                    intelligentBatterySaverFastDrainPolicy.saveBooleanState(
                                            "disable_wifi_by_ibs", false);
                                    break;
                                default:
                                    IntelligentBatterySaverFastDrainAction
                                            intelligentBatterySaverFastDrainAction2 = this.this$1;
                                    TelephonyManager from =
                                            TelephonyManager.from(
                                                    intelligentBatterySaverFastDrainAction2
                                                            .mContext);
                                    this.wifiManager = from;
                                    IntelligentBatterySaverFastDrainPolicy
                                            intelligentBatterySaverFastDrainPolicy2 =
                                                    IntelligentBatterySaverFastDrainPolicy.this;
                                    if (from != null) {
                                        this.wifiState = from.getDataEnabled();
                                        Slog.d(
                                                "IntelligentBatterySaverFastDrainAction",
                                                "Recover mobiledata check mobiledataState = "
                                                        + this.wifiState
                                                        + ", operMobileState = "
                                                        + intelligentBatterySaverFastDrainPolicy2
                                                                .getBooleanState(
                                                                        "disable_mobile_data_by_ibs"));
                                        if (!this.wifiState
                                                && intelligentBatterySaverFastDrainPolicy2
                                                        .getBooleanState(
                                                                "disable_mobile_data_by_ibs")) {
                                            Slog.d(
                                                    "IntelligentBatterySaverFastDrainAction",
                                                    "recover mobiledata");
                                            intelligentBatterySaverFastDrainPolicy2
                                                    .mIntelligentBatterySaverLogger.add(
                                                    "recover mobiledata");
                                            ((TelephonyManager) this.wifiManager)
                                                    .setDataEnabled(true);
                                        }
                                    }
                                    intelligentBatterySaverFastDrainPolicy2.saveBooleanState(
                                            "disable_mobile_data_by_ibs", false);
                                    break;
                            }
                        }

                        @Override // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IIntelligentBatterySaverFastDrainCallBack
                        public final void doFastDrainRestriction() {
                            switch (i) {
                                case 0:
                                    IntelligentBatterySaverFastDrainAction
                                            intelligentBatterySaverFastDrainAction = this.this$1;
                                    WifiManager wifiManager =
                                            (WifiManager)
                                                    intelligentBatterySaverFastDrainAction.mContext
                                                            .getSystemService("wifi");
                                    this.wifiManager = wifiManager;
                                    if (wifiManager != null) {
                                        this.wifiState = wifiManager.isWifiEnabled();
                                        this.wifiApState =
                                                ((WifiManager) this.wifiManager).isWifiApEnabled();
                                        StringBuilder sb =
                                                new StringBuilder(
                                                        "Disable wifi check wifiApState = ");
                                        sb.append(this.wifiApState);
                                        sb.append(", wifiState = ");
                                        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                                "IntelligentBatterySaverFastDrainAction",
                                                sb,
                                                this.wifiState);
                                        IntelligentBatterySaverFastDrainPolicy
                                                intelligentBatterySaverFastDrainPolicy =
                                                        IntelligentBatterySaverFastDrainPolicy.this;
                                        intelligentBatterySaverFastDrainPolicy
                                                .mIntelligentBatterySaverLogger.add(
                                                "wifi wifiState = "
                                                        + this.wifiState
                                                        + " wifiApState = "
                                                        + this.wifiApState);
                                        if (!this.wifiApState && this.wifiState) {
                                            Slog.d(
                                                    "IntelligentBatterySaverFastDrainAction",
                                                    "wifi set disable");
                                            intelligentBatterySaverFastDrainPolicy
                                                    .mIntelligentBatterySaverLogger.add(
                                                    "wifi set disable");
                                            ((WifiManager) this.wifiManager).setWifiEnabled(false);
                                            intelligentBatterySaverFastDrainPolicy.saveBooleanState(
                                                    "disable_wifi_by_ibs", true);
                                            break;
                                        }
                                    }
                                    break;
                                default:
                                    IntelligentBatterySaverFastDrainAction
                                            intelligentBatterySaverFastDrainAction2 = this.this$1;
                                    WifiManager wifiManager2 =
                                            (WifiManager)
                                                    intelligentBatterySaverFastDrainAction2.mContext
                                                            .getSystemService("wifi");
                                    if (wifiManager2 != null) {
                                        this.wifiApState = wifiManager2.isWifiApEnabled();
                                    }
                                    IntelligentBatterySaverFastDrainPolicy
                                            intelligentBatterySaverFastDrainPolicy2 =
                                                    IntelligentBatterySaverFastDrainPolicy.this;
                                    intelligentBatterySaverFastDrainPolicy2
                                            .mIntelligentBatterySaverLogger.add(
                                            "wifiApState = " + this.wifiApState);
                                    TelephonyManager from =
                                            TelephonyManager.from(
                                                    intelligentBatterySaverFastDrainAction2
                                                            .mContext);
                                    this.wifiManager = from;
                                    if (from != null) {
                                        this.wifiState = from.getDataEnabled();
                                        StringBuilder sb2 =
                                                new StringBuilder(
                                                        "Disable mobiledata check wifiApState = ");
                                        sb2.append(this.wifiApState);
                                        sb2.append(", mobiledataState = ");
                                        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                                "IntelligentBatterySaverFastDrainAction",
                                                sb2,
                                                this.wifiState);
                                        intelligentBatterySaverFastDrainPolicy2
                                                .mIntelligentBatterySaverLogger.add(
                                                "mobiledataState = " + this.wifiState);
                                        if (!this.wifiApState && this.wifiState) {
                                            Slog.d(
                                                    "IntelligentBatterySaverFastDrainAction",
                                                    "mobiledata set disable");
                                            intelligentBatterySaverFastDrainPolicy2
                                                    .mIntelligentBatterySaverLogger.add(
                                                    "mobiledata set disable");
                                            ((TelephonyManager) this.wifiManager)
                                                    .setDataEnabled(false);
                                            intelligentBatterySaverFastDrainPolicy2
                                                    .saveBooleanState(
                                                            "disable_mobile_data_by_ibs", true);
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                    };
            final int i2 = 1;
            this.mobiledataCallBack =
                    new IIntelligentBatterySaverFastDrainCallBack(this) { // from class:
                        // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IntelligentBatterySaverFastDrainAction.2
                        public final /* synthetic */ IntelligentBatterySaverFastDrainAction this$1;
                        public Object wifiManager;
                        public boolean wifiState = false;
                        public boolean wifiApState = true;

                        {
                            this.this$1 = this;
                        }

                        @Override // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IIntelligentBatterySaverFastDrainCallBack
                        public final void cancelFastDrainRestriction() {
                            switch (i2) {
                                case 0:
                                    IntelligentBatterySaverFastDrainAction
                                            intelligentBatterySaverFastDrainAction = this.this$1;
                                    WifiManager wifiManager =
                                            (WifiManager)
                                                    intelligentBatterySaverFastDrainAction.mContext
                                                            .getSystemService("wifi");
                                    this.wifiManager = wifiManager;
                                    IntelligentBatterySaverFastDrainPolicy
                                            intelligentBatterySaverFastDrainPolicy =
                                                    IntelligentBatterySaverFastDrainPolicy.this;
                                    if (wifiManager != null) {
                                        this.wifiState = wifiManager.isWifiEnabled();
                                        Slog.d(
                                                "IntelligentBatterySaverFastDrainAction",
                                                "Recover wifi check wifiState = "
                                                        + this.wifiState
                                                        + ", operWifiState = "
                                                        + intelligentBatterySaverFastDrainPolicy
                                                                .getBooleanState(
                                                                        "disable_wifi_by_ibs"));
                                        if (!this.wifiState
                                                && intelligentBatterySaverFastDrainPolicy
                                                        .getBooleanState("disable_wifi_by_ibs")) {
                                            Slog.d(
                                                    "IntelligentBatterySaverFastDrainAction",
                                                    "recover wifi");
                                            intelligentBatterySaverFastDrainPolicy
                                                    .mIntelligentBatterySaverLogger.add(
                                                    "recover wifi");
                                            ((WifiManager) this.wifiManager).setWifiEnabled(true);
                                        }
                                    }
                                    intelligentBatterySaverFastDrainPolicy.saveBooleanState(
                                            "disable_wifi_by_ibs", false);
                                    break;
                                default:
                                    IntelligentBatterySaverFastDrainAction
                                            intelligentBatterySaverFastDrainAction2 = this.this$1;
                                    TelephonyManager from =
                                            TelephonyManager.from(
                                                    intelligentBatterySaverFastDrainAction2
                                                            .mContext);
                                    this.wifiManager = from;
                                    IntelligentBatterySaverFastDrainPolicy
                                            intelligentBatterySaverFastDrainPolicy2 =
                                                    IntelligentBatterySaverFastDrainPolicy.this;
                                    if (from != null) {
                                        this.wifiState = from.getDataEnabled();
                                        Slog.d(
                                                "IntelligentBatterySaverFastDrainAction",
                                                "Recover mobiledata check mobiledataState = "
                                                        + this.wifiState
                                                        + ", operMobileState = "
                                                        + intelligentBatterySaverFastDrainPolicy2
                                                                .getBooleanState(
                                                                        "disable_mobile_data_by_ibs"));
                                        if (!this.wifiState
                                                && intelligentBatterySaverFastDrainPolicy2
                                                        .getBooleanState(
                                                                "disable_mobile_data_by_ibs")) {
                                            Slog.d(
                                                    "IntelligentBatterySaverFastDrainAction",
                                                    "recover mobiledata");
                                            intelligentBatterySaverFastDrainPolicy2
                                                    .mIntelligentBatterySaverLogger.add(
                                                    "recover mobiledata");
                                            ((TelephonyManager) this.wifiManager)
                                                    .setDataEnabled(true);
                                        }
                                    }
                                    intelligentBatterySaverFastDrainPolicy2.saveBooleanState(
                                            "disable_mobile_data_by_ibs", false);
                                    break;
                            }
                        }

                        @Override // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IIntelligentBatterySaverFastDrainCallBack
                        public final void doFastDrainRestriction() {
                            switch (i2) {
                                case 0:
                                    IntelligentBatterySaverFastDrainAction
                                            intelligentBatterySaverFastDrainAction = this.this$1;
                                    WifiManager wifiManager =
                                            (WifiManager)
                                                    intelligentBatterySaverFastDrainAction.mContext
                                                            .getSystemService("wifi");
                                    this.wifiManager = wifiManager;
                                    if (wifiManager != null) {
                                        this.wifiState = wifiManager.isWifiEnabled();
                                        this.wifiApState =
                                                ((WifiManager) this.wifiManager).isWifiApEnabled();
                                        StringBuilder sb =
                                                new StringBuilder(
                                                        "Disable wifi check wifiApState = ");
                                        sb.append(this.wifiApState);
                                        sb.append(", wifiState = ");
                                        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                                "IntelligentBatterySaverFastDrainAction",
                                                sb,
                                                this.wifiState);
                                        IntelligentBatterySaverFastDrainPolicy
                                                intelligentBatterySaverFastDrainPolicy =
                                                        IntelligentBatterySaverFastDrainPolicy.this;
                                        intelligentBatterySaverFastDrainPolicy
                                                .mIntelligentBatterySaverLogger.add(
                                                "wifi wifiState = "
                                                        + this.wifiState
                                                        + " wifiApState = "
                                                        + this.wifiApState);
                                        if (!this.wifiApState && this.wifiState) {
                                            Slog.d(
                                                    "IntelligentBatterySaverFastDrainAction",
                                                    "wifi set disable");
                                            intelligentBatterySaverFastDrainPolicy
                                                    .mIntelligentBatterySaverLogger.add(
                                                    "wifi set disable");
                                            ((WifiManager) this.wifiManager).setWifiEnabled(false);
                                            intelligentBatterySaverFastDrainPolicy.saveBooleanState(
                                                    "disable_wifi_by_ibs", true);
                                            break;
                                        }
                                    }
                                    break;
                                default:
                                    IntelligentBatterySaverFastDrainAction
                                            intelligentBatterySaverFastDrainAction2 = this.this$1;
                                    WifiManager wifiManager2 =
                                            (WifiManager)
                                                    intelligentBatterySaverFastDrainAction2.mContext
                                                            .getSystemService("wifi");
                                    if (wifiManager2 != null) {
                                        this.wifiApState = wifiManager2.isWifiApEnabled();
                                    }
                                    IntelligentBatterySaverFastDrainPolicy
                                            intelligentBatterySaverFastDrainPolicy2 =
                                                    IntelligentBatterySaverFastDrainPolicy.this;
                                    intelligentBatterySaverFastDrainPolicy2
                                            .mIntelligentBatterySaverLogger.add(
                                            "wifiApState = " + this.wifiApState);
                                    TelephonyManager from =
                                            TelephonyManager.from(
                                                    intelligentBatterySaverFastDrainAction2
                                                            .mContext);
                                    this.wifiManager = from;
                                    if (from != null) {
                                        this.wifiState = from.getDataEnabled();
                                        StringBuilder sb2 =
                                                new StringBuilder(
                                                        "Disable mobiledata check wifiApState = ");
                                        sb2.append(this.wifiApState);
                                        sb2.append(", mobiledataState = ");
                                        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                                "IntelligentBatterySaverFastDrainAction",
                                                sb2,
                                                this.wifiState);
                                        intelligentBatterySaverFastDrainPolicy2
                                                .mIntelligentBatterySaverLogger.add(
                                                "mobiledataState = " + this.wifiState);
                                        if (!this.wifiApState && this.wifiState) {
                                            Slog.d(
                                                    "IntelligentBatterySaverFastDrainAction",
                                                    "mobiledata set disable");
                                            intelligentBatterySaverFastDrainPolicy2
                                                    .mIntelligentBatterySaverLogger.add(
                                                    "mobiledata set disable");
                                            ((TelephonyManager) this.wifiManager)
                                                    .setDataEnabled(false);
                                            intelligentBatterySaverFastDrainPolicy2
                                                    .saveBooleanState(
                                                            "disable_mobile_data_by_ibs", true);
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                    };
            this.btCallBack =
                    new IIntelligentBatterySaverFastDrainCallBack(this) { // from class:
                        // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IntelligentBatterySaverFastDrainAction.1
                        public final /* synthetic */ IntelligentBatterySaverFastDrainAction this$1;

                        {
                            this.this$1 = this;
                        }

                        @Override // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IIntelligentBatterySaverFastDrainCallBack
                        public final void cancelFastDrainRestriction() {
                            int i22;
                            switch (i2) {
                                case 0:
                                    IntelligentBatterySaverFastDrainAction
                                            intelligentBatterySaverFastDrainAction = this.this$1;
                                    IntelligentBatterySaverFastDrainPolicy
                                            intelligentBatterySaverFastDrainPolicy =
                                                    IntelligentBatterySaverFastDrainPolicy.this;
                                    if (intelligentBatterySaverFastDrainPolicy.mSharedPreferences
                                                    == null
                                            && intelligentBatterySaverFastDrainPolicy.mContext
                                                    != null) {
                                        Slog.d(
                                                "IntelligentBatterySaverFastDrainPolicy",
                                                "Create shared preferences when get state.");
                                        intelligentBatterySaverFastDrainPolicy.mSharedPreferences =
                                                intelligentBatterySaverFastDrainPolicy.mContext
                                                        .getSharedPreferences("oper_pref", 0);
                                    }
                                    SharedPreferences sharedPreferences =
                                            intelligentBatterySaverFastDrainPolicy
                                                    .mSharedPreferences;
                                    if (sharedPreferences == null) {
                                        i22 = 0;
                                    } else {
                                        i22 = sharedPreferences.getInt("disable_gps_by_ibs", 0);
                                        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                                i22,
                                                "getIntState type = disable_gps_by_ibs state = ",
                                                "IntelligentBatterySaverFastDrainPolicy");
                                    }
                                    if (i22 != 0) {
                                        if (1
                                                        != Settings.System.getInt(
                                                                intelligentBatterySaverFastDrainAction
                                                                        .mContext
                                                                        .getContentResolver(),
                                                                "display_night_theme_scheduled",
                                                                0)
                                                || 1
                                                        != Settings.System.getInt(
                                                                intelligentBatterySaverFastDrainAction
                                                                        .mContext
                                                                        .getContentResolver(),
                                                                "display_night_theme_scheduled_type",
                                                                2)) {
                                            Settings.Secure.putInt(
                                                    intelligentBatterySaverFastDrainAction.mContext
                                                            .getContentResolver(),
                                                    "location_mode",
                                                    i22);
                                            StringBuilder sb = new StringBuilder("gpsState = ");
                                            sb.append(i22);
                                            BootReceiver$$ExternalSyntheticOutline0.m(
                                                    sb,
                                                    " recover gps",
                                                    "IntelligentBatterySaverFastDrainAction");
                                            IntelligentBatterySaverFastDrainPolicy
                                                    intelligentBatterySaverFastDrainPolicy2 =
                                                            IntelligentBatterySaverFastDrainPolicy
                                                                    .this;
                                            intelligentBatterySaverFastDrainPolicy2
                                                    .mIntelligentBatterySaverLogger.add(
                                                    "recover gps");
                                            IntelligentBatterySaverFastDrainPolicy
                                                    .m580$$Nest$msaveIntState(
                                                            intelligentBatterySaverFastDrainPolicy2,
                                                            0);
                                            break;
                                        }
                                    }
                                    break;
                                default:
                                    IntelligentBatterySaverFastDrainAction
                                            intelligentBatterySaverFastDrainAction2 = this.this$1;
                                    BluetoothAdapter adapter =
                                            ((BluetoothManager)
                                                            intelligentBatterySaverFastDrainAction2
                                                                    .mContext.getSystemService(
                                                                    "bluetooth"))
                                                    .getAdapter();
                                    IntelligentBatterySaverFastDrainPolicy
                                            intelligentBatterySaverFastDrainPolicy3 =
                                                    IntelligentBatterySaverFastDrainPolicy.this;
                                    if (intelligentBatterySaverFastDrainPolicy3.getBooleanState(
                                                    "disable_bt_by_ibs")
                                            && adapter != null
                                            && !adapter.isEnabled()) {
                                        Slog.d(
                                                "IntelligentBatterySaverFastDrainAction",
                                                "Recover BT");
                                        intelligentBatterySaverFastDrainPolicy3
                                                .mIntelligentBatterySaverLogger.add("Recover BT");
                                        adapter.enable();
                                    }
                                    intelligentBatterySaverFastDrainPolicy3.saveBooleanState(
                                            "disable_bt_by_ibs", false);
                                    break;
                            }
                        }

                        @Override // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy.IIntelligentBatterySaverFastDrainCallBack
                        public final void doFastDrainRestriction() {
                            switch (i2) {
                                case 0:
                                    IntelligentBatterySaverFastDrainAction
                                            intelligentBatterySaverFastDrainAction = this.this$1;
                                    int i22 =
                                            Settings.Secure.getInt(
                                                    intelligentBatterySaverFastDrainAction.mContext
                                                            .getContentResolver(),
                                                    "location_mode",
                                                    0);
                                    if (i22 != 0) {
                                        if (1
                                                        != Settings.System.getInt(
                                                                intelligentBatterySaverFastDrainAction
                                                                        .mContext
                                                                        .getContentResolver(),
                                                                "display_night_theme_scheduled",
                                                                0)
                                                || 1
                                                        != Settings.System.getInt(
                                                                intelligentBatterySaverFastDrainAction
                                                                        .mContext
                                                                        .getContentResolver(),
                                                                "display_night_theme_scheduled_type",
                                                                2)) {
                                            Settings.Secure.putInt(
                                                    intelligentBatterySaverFastDrainAction.mContext
                                                            .getContentResolver(),
                                                    "location_mode",
                                                    0);
                                            StringBuilder sb = new StringBuilder("gpsState = ");
                                            sb.append(i22);
                                            BootReceiver$$ExternalSyntheticOutline0.m(
                                                    sb,
                                                    " gps set disable",
                                                    "IntelligentBatterySaverFastDrainAction");
                                            IntelligentBatterySaverFastDrainPolicy
                                                    intelligentBatterySaverFastDrainPolicy =
                                                            IntelligentBatterySaverFastDrainPolicy
                                                                    .this;
                                            intelligentBatterySaverFastDrainPolicy
                                                    .mIntelligentBatterySaverLogger.add(
                                                    "gpsState = " + i22 + " gps set disable");
                                            IntelligentBatterySaverFastDrainPolicy
                                                    .m580$$Nest$msaveIntState(
                                                            intelligentBatterySaverFastDrainPolicy,
                                                            i22);
                                            break;
                                        }
                                    }
                                    break;
                                default:
                                    IntelligentBatterySaverFastDrainAction
                                            intelligentBatterySaverFastDrainAction2 = this.this$1;
                                    IntelligentBatterySaverFastDrainPolicy
                                            intelligentBatterySaverFastDrainPolicy2 =
                                                    IntelligentBatterySaverFastDrainPolicy.this;
                                    intelligentBatterySaverFastDrainPolicy2.getClass();
                                    BluetoothAdapter defaultAdapter =
                                            BluetoothAdapter.getDefaultAdapter();
                                    BluetoothManager bluetoothManager =
                                            (BluetoothManager)
                                                    intelligentBatterySaverFastDrainPolicy2.mContext
                                                            .getSystemService("bluetooth");
                                    boolean z = false;
                                    boolean z2 =
                                            defaultAdapter != null
                                                    && defaultAdapter.isEnabled()
                                                    && (defaultAdapter.getBondedDevices().stream()
                                                                    .anyMatch(
                                                                            new IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda5())
                                                            || bluetoothManager
                                                                                    .getConnectedDevices(
                                                                                            7)
                                                                                    .size()
                                                                            + bluetoothManager
                                                                                    .getConnectedDevices(
                                                                                            8)
                                                                                    .size()
                                                                    > 0);
                                    BluetoothAdapter adapter =
                                            ((BluetoothManager)
                                                            intelligentBatterySaverFastDrainAction2
                                                                    .mContext.getSystemService(
                                                                    "bluetooth"))
                                                    .getAdapter();
                                    if (adapter != null && adapter.isEnabled()) {
                                        z = true;
                                    }
                                    Slog.d(
                                            "IntelligentBatterySaverFastDrainAction",
                                            "Disable BT check btEnabled= "
                                                    + z
                                                    + ", btConnected = "
                                                    + z2);
                                    IntelligentBatterySaverFastDrainPolicy
                                            intelligentBatterySaverFastDrainPolicy3 =
                                                    IntelligentBatterySaverFastDrainPolicy.this;
                                    intelligentBatterySaverFastDrainPolicy3
                                            .mIntelligentBatterySaverLogger.add(
                                            "BlueTooth btEnabled= " + z + ", btConnected = " + z2);
                                    if (z && !z2) {
                                        Slog.d(
                                                "IntelligentBatterySaverFastDrainAction",
                                                "Disable BT");
                                        intelligentBatterySaverFastDrainPolicy3
                                                .mIntelligentBatterySaverLogger.add("Disable BT");
                                        adapter.disable();
                                        intelligentBatterySaverFastDrainPolicy3.saveBooleanState(
                                                "disable_bt_by_ibs", true);
                                        break;
                                    }
                                    break;
                            }
                        }
                    };
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IntelligentBatterySaverFastDrainBatteryInfo {
        public boolean initialized;
        public int level;
        public long startTime;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IntelligentBatterySaverFastDrainHandler extends Handler {
        public IntelligentBatterySaverFastDrainHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i;
            switch (message.what) {
                case 1:
                    IntelligentBatterySaverFastDrainPolicy intelligentBatterySaverFastDrainPolicy =
                            IntelligentBatterySaverFastDrainPolicy.this;
                    if (intelligentBatterySaverFastDrainPolicy.mInited) {
                        return;
                    }
                    intelligentBatterySaverFastDrainPolicy.initBroadcast(true);
                    intelligentBatterySaverFastDrainPolicy.initAlarm(true);
                    intelligentBatterySaverFastDrainPolicy.mInited = true;
                    return;
                case 2:
                    IntelligentBatterySaverFastDrainPolicy intelligentBatterySaverFastDrainPolicy2 =
                            IntelligentBatterySaverFastDrainPolicy.this;
                    if (intelligentBatterySaverFastDrainPolicy2.mInited) {
                        intelligentBatterySaverFastDrainPolicy2.initBroadcast(false);
                        intelligentBatterySaverFastDrainPolicy2.initAlarm(false);
                        intelligentBatterySaverFastDrainPolicy2.mInited = false;
                    }
                    IntelligentBatterySaverFastDrainPolicy.this.mFastDrainInternalState = 2;
                    Slog.d(
                            "IntelligentBatterySaverFastDrainPolicy",
                            "stop, move to off state, message");
                    IntelligentBatterySaverFastDrainPolicy.this.mIntelligentBatterySaverLogger.add(
                            "stop, move to off state, message");
                    IntelligentBatterySaverFastDrainPolicy.this.resetBatteryInfo();
                    return;
                case 3:
                    IntelligentBatterySaverFastDrainPolicy intelligentBatterySaverFastDrainPolicy3 =
                            IntelligentBatterySaverFastDrainPolicy.this;
                    if (intelligentBatterySaverFastDrainPolicy3.mFastDrainInternalState == 6) {
                        intelligentBatterySaverFastDrainPolicy3.mEnterIBSTime =
                                System.currentTimeMillis();
                        intelligentBatterySaverFastDrainPolicy3.mEnterIBSBatteryLevel =
                                intelligentBatterySaverFastDrainPolicy3.mBatteryInfo.level;
                        if (intelligentBatterySaverFastDrainPolicy3.testState(128)) {
                            synchronized (intelligentBatterySaverFastDrainPolicy3.mActionsLock) {
                                int i2 = 0;
                                for (int i3 = 1; i2 <= i3; i3 = 1) {
                                    Iterator it =
                                            ((ArrayList)
                                                            intelligentBatterySaverFastDrainPolicy3
                                                                    .mActionsLevel.get(i2))
                                                    .iterator();
                                    while (it.hasNext()) {
                                        ActionEntry actionEntry = (ActionEntry) it.next();
                                        try {
                                            Slog.d(
                                                    "IntelligentBatterySaverFastDrainPolicy",
                                                    " do fast drain restriction "
                                                            + actionEntry.tag);
                                            actionEntry.callBack.doFastDrainRestriction();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    i2++;
                                }
                                i = 1;
                                intelligentBatterySaverFastDrainPolicy3.saveBooleanState(
                                        "ibs_policy_activated", true);
                            }
                        } else {
                            Slog.d(
                                    "IntelligentBatterySaverFastDrainPolicy",
                                    "UI switch off disable the fast drain restriction.");
                            i = 1;
                        }
                        IntelligentBatterySaverFastDrainPolicy.this.mFastDrainInternalState = i;
                        Slog.d(
                                "IntelligentBatterySaverFastDrainPolicy",
                                "move to state on, message");
                        IntelligentBatterySaverFastDrainPolicy.this.mIntelligentBatterySaverLogger
                                .add("move to state on, message");
                        return;
                    }
                    return;
                case 4:
                    IntelligentBatterySaverFastDrainPolicy intelligentBatterySaverFastDrainPolicy4 =
                            IntelligentBatterySaverFastDrainPolicy.this;
                    if (intelligentBatterySaverFastDrainPolicy4.mFastDrainInternalState == 1) {
                        intelligentBatterySaverFastDrainPolicy4.exitFastDrainRestriction();
                        IntelligentBatterySaverFastDrainPolicy.this.mFastDrainInternalState = 2;
                        Slog.d(
                                "IntelligentBatterySaverFastDrainPolicy",
                                "move to off state, message");
                        IntelligentBatterySaverFastDrainPolicy.this.mIntelligentBatterySaverLogger
                                .add("move to off state, message");
                        return;
                    }
                    return;
                case 5:
                    if (!IntelligentBatterySaverFastDrainPolicy.this.testState(32)) {
                        IntelligentBatterySaverFastDrainPolicy
                                intelligentBatterySaverFastDrainPolicy5 =
                                        IntelligentBatterySaverFastDrainPolicy.this;
                        AudioManager audioManager =
                                (AudioManager)
                                        intelligentBatterySaverFastDrainPolicy5.mContext
                                                .getSystemService("audio");
                        boolean isMusicActive = audioManager.isMusicActive();
                        boolean semIsRecordActive = audioManager.semIsRecordActive(-1);
                        if (isMusicActive || semIsRecordActive) {
                            intelligentBatterySaverFastDrainPolicy5.reportClearState(32);
                        } else {
                            intelligentBatterySaverFastDrainPolicy5.reportSetState(32);
                        }
                    }
                    if (IntelligentBatterySaverFastDrainPolicy.this.testState(64)) {
                        return;
                    }
                    IntelligentBatterySaverFastDrainPolicy intelligentBatterySaverFastDrainPolicy6 =
                            IntelligentBatterySaverFastDrainPolicy.this;
                    IntelligentBatterySaverFastDrainTrafficStat
                            intelligentBatterySaverFastDrainTrafficStat =
                                    intelligentBatterySaverFastDrainPolicy6.mTrafiicStat;
                    if (!intelligentBatterySaverFastDrainTrafficStat.initialized) {
                        intelligentBatterySaverFastDrainTrafficStat.sampleTimeInSecs =
                                System.currentTimeMillis() / 1000;
                        intelligentBatterySaverFastDrainTrafficStat.txBytes =
                                TrafficStats.getTotalTxBytes();
                        intelligentBatterySaverFastDrainTrafficStat.rxBytes =
                                TrafficStats.getTotalRxBytes();
                        intelligentBatterySaverFastDrainTrafficStat.initialized = true;
                        return;
                    }
                    long currentTimeMillis = System.currentTimeMillis() / 1000;
                    long totalTxBytes = TrafficStats.getTotalTxBytes();
                    long totalRxBytes = TrafficStats.getTotalRxBytes();
                    long j =
                            (totalTxBytes + totalRxBytes)
                                    - (intelligentBatterySaverFastDrainTrafficStat.txBytes
                                            + intelligentBatterySaverFastDrainTrafficStat.rxBytes);
                    long j2 =
                            currentTimeMillis
                                    - intelligentBatterySaverFastDrainTrafficStat.sampleTimeInSecs;
                    if (j2 <= 0) {
                        return;
                    }
                    long j3 = j / j2;
                    StringBuilder m =
                            BatteryService$$ExternalSyntheticOutline0.m(
                                    "checkDownloadSafe speed: ,", currentTimeMillis, ",");
                    m.append(totalTxBytes);
                    BootReceiver$$ExternalSyntheticOutline0.m(m, ",", totalRxBytes, ",");
                    m.append(j);
                    BootReceiver$$ExternalSyntheticOutline0.m(m, ",", j2, ",");
                    BatteryService$$ExternalSyntheticOutline0.m(
                            m, j3, "IntelligentBatterySaverFastDrainPolicy");
                    if (j3 < 5000) {
                        intelligentBatterySaverFastDrainPolicy6.reportSetState(64);
                        intelligentBatterySaverFastDrainTrafficStat.initialized = false;
                        return;
                    } else {
                        intelligentBatterySaverFastDrainTrafficStat.sampleTimeInSecs =
                                currentTimeMillis;
                        intelligentBatterySaverFastDrainTrafficStat.txBytes = totalTxBytes;
                        intelligentBatterySaverFastDrainTrafficStat.rxBytes = totalRxBytes;
                        intelligentBatterySaverFastDrainPolicy6.reportClearState(64);
                        return;
                    }
                case 6:
                    IntelligentBatterySaverFastDrainPolicy intelligentBatterySaverFastDrainPolicy7 =
                            IntelligentBatterySaverFastDrainPolicy.this;
                    if (!intelligentBatterySaverFastDrainPolicy7.testState(1)
                            || intelligentBatterySaverFastDrainPolicy7.mFastDrainInternalState
                                    != 6) {
                        IntelligentBatterySaverFastDrainPolicy
                                intelligentBatterySaverFastDrainPolicy8 =
                                        IntelligentBatterySaverFastDrainPolicy.this;
                        if (!intelligentBatterySaverFastDrainPolicy8.testState(1)
                                || intelligentBatterySaverFastDrainPolicy8.mFastDrainInternalState
                                        != 1) {
                            return;
                        }
                    }
                    IntelligentBatterySaverFastDrainPolicy intelligentBatterySaverFastDrainPolicy9 =
                            IntelligentBatterySaverFastDrainPolicy.this;
                    int i4 = message.arg1;
                    int i5 = message.arg2;
                    IntelligentBatterySaverFastDrainBatteryInfo
                            intelligentBatterySaverFastDrainBatteryInfo =
                                    intelligentBatterySaverFastDrainPolicy9.mBatteryInfo;
                    boolean z = intelligentBatterySaverFastDrainBatteryInfo.initialized;
                    IntelligentBatterySaverLogger intelligentBatterySaverLogger =
                            intelligentBatterySaverFastDrainPolicy9.mIntelligentBatterySaverLogger;
                    if (!z) {
                        int i6 = (i4 * 100) / i5;
                        int i7 = intelligentBatterySaverFastDrainBatteryInfo.level;
                        if (i7 == -1) {
                            intelligentBatterySaverFastDrainBatteryInfo.level = i6;
                            return;
                        }
                        int i8 = i7 - i6;
                        if (i8 != 1) {
                            if (i8 >= 2) {
                                intelligentBatterySaverFastDrainBatteryInfo.level = -1;
                                Slog.w(
                                        "IntelligentBatterySaverFastDrainPolicy",
                                        "warning !!! battery drop is more than 2 level");
                                return;
                            }
                            return;
                        }
                        intelligentBatterySaverFastDrainBatteryInfo.startTime =
                                System.currentTimeMillis() / 1000;
                        intelligentBatterySaverFastDrainBatteryInfo.level = i6;
                        intelligentBatterySaverFastDrainBatteryInfo.initialized = true;
                        StringBuilder m2 =
                                ArrayUtils$$ExternalSyntheticOutline0.m(
                                        i4,
                                        i5,
                                        "checkBatteryInfo init level = ",
                                        " scale = ",
                                        " start time = ");
                        m2.append(intelligentBatterySaverFastDrainBatteryInfo.startTime);
                        Slog.d("IntelligentBatterySaverFastDrainPolicy", m2.toString());
                        StringBuilder sb = new StringBuilder("checkBatteryInfo init level = ");
                        ServiceKeeper$$ExternalSyntheticOutline0.m(
                                i4, i5, " scale = ", " start time = ", sb);
                        sb.append(intelligentBatterySaverFastDrainBatteryInfo.startTime);
                        intelligentBatterySaverLogger.add(sb.toString());
                        return;
                    }
                    long currentTimeMillis2 = System.currentTimeMillis() / 1000;
                    long j4 =
                            currentTimeMillis2
                                    - intelligentBatterySaverFastDrainBatteryInfo.startTime;
                    int i9 = (i4 * 100) / i5;
                    StringBuilder m3 =
                            ArrayUtils$$ExternalSyntheticOutline0.m(
                                    i4,
                                    i5,
                                    "checkBatteryInfo level = ",
                                    " scale = ",
                                    " timeDelta = ");
                    m3.append(j4);
                    Slog.d("IntelligentBatterySaverFastDrainPolicy", m3.toString());
                    StringBuilder sb2 = new StringBuilder("checkBatteryInfo level = ");
                    ServiceKeeper$$ExternalSyntheticOutline0.m(
                            i4, i5, " scale = ", " timeDelta = ", sb2);
                    sb2.append(j4);
                    intelligentBatterySaverLogger.add(sb2.toString());
                    int i10 = intelligentBatterySaverFastDrainBatteryInfo.level - i9;
                    if (i10 != 1) {
                        if (i10 >= 2) {
                            intelligentBatterySaverFastDrainPolicy9.resetBatteryInfo();
                            Slog.w(
                                    "IntelligentBatterySaverFastDrainPolicy",
                                    "warning !!! battery drop is more than 2 level");
                            return;
                        }
                        return;
                    }
                    intelligentBatterySaverFastDrainBatteryInfo.startTime = currentTimeMillis2;
                    intelligentBatterySaverFastDrainBatteryInfo.level = i9;
                    if (intelligentBatterySaverFastDrainPolicy9.mPowerProfile == null) {
                        intelligentBatterySaverFastDrainPolicy9.mPowerProfile =
                                new PowerProfile(intelligentBatterySaverFastDrainPolicy9.mContext);
                    }
                    float batteryCapacity =
                            (float)
                                    intelligentBatterySaverFastDrainPolicy9.mPowerProfile
                                            .getBatteryCapacity();
                    intelligentBatterySaverFastDrainPolicy9.mEstimatedBatteryCapacity =
                            batteryCapacity;
                    if (j4 >= 0 && j4 <= 60) {
                        intelligentBatterySaverFastDrainPolicy9.resetBatteryInfo();
                        return;
                    }
                    float f = ((batteryCapacity / 100.0f) * 3600.0f) / j4;
                    boolean z2 =
                            Settings.System.getInt(
                                            intelligentBatterySaverFastDrainPolicy9.mContext
                                                    .getContentResolver(),
                                            "aod_show_state",
                                            0)
                                    != 0;
                    if (z2) {
                        intelligentBatterySaverFastDrainPolicy9.mFastDropCurrent = 55.0f;
                    } else {
                        intelligentBatterySaverFastDrainPolicy9.mFastDropCurrent = 35.0f;
                    }
                    if (f < intelligentBatterySaverFastDrainPolicy9.mFastDropCurrent) {
                        Slog.d(
                                "IntelligentBatterySaverFastDrainPolicy",
                                "curCurrent = "
                                        + f
                                        + " < mFastDropCurrent = "
                                        + intelligentBatterySaverFastDrainPolicy9.mFastDropCurrent
                                        + " and isAodEnabled = "
                                        + z2);
                        StringBuilder sb3 = new StringBuilder("curCurrent = ");
                        sb3.append(f);
                        sb3.append(" < mFastDropCurrent = ");
                        sb3.append(intelligentBatterySaverFastDrainPolicy9.mFastDropCurrent);
                        intelligentBatterySaverLogger.add(sb3.toString());
                        return;
                    }
                    Slog.d(
                            "IntelligentBatterySaverFastDrainPolicy",
                            "curCurrent = "
                                    + f
                                    + " > mFastDropCurrent = "
                                    + intelligentBatterySaverFastDrainPolicy9.mFastDropCurrent
                                    + " and isAodEnabled = "
                                    + z2);
                    IntelligentBatterySaverBatteryBigData intelligentBatterySaverBatteryBigData =
                            intelligentBatterySaverFastDrainPolicy9.mIBSBigData;
                    if (!intelligentBatterySaverBatteryBigData.initialized) {
                        intelligentBatterySaverBatteryBigData.drainHightCurrent = f;
                        intelligentBatterySaverBatteryBigData.initialized = true;
                    }
                    intelligentBatterySaverLogger.add(
                            "curCurrent = "
                                    + f
                                    + " > mFastDropCurrent = "
                                    + intelligentBatterySaverFastDrainPolicy9.mFastDropCurrent);
                    return;
                case 7:
                default:
                    return;
                case 8:
                    int i11 = message.arg1;
                    IntelligentBatterySaverFastDrainPolicy
                            intelligentBatterySaverFastDrainPolicy10 =
                                    IntelligentBatterySaverFastDrainPolicy.this;
                    intelligentBatterySaverFastDrainPolicy10.setSysState(
                            i11 | intelligentBatterySaverFastDrainPolicy10.mSysState);
                    return;
                case 9:
                    int i12 = message.arg1;
                    IntelligentBatterySaverFastDrainPolicy
                            intelligentBatterySaverFastDrainPolicy11 =
                                    IntelligentBatterySaverFastDrainPolicy.this;
                    intelligentBatterySaverFastDrainPolicy11.setSysState(
                            (~i12) & intelligentBatterySaverFastDrainPolicy11.mSysState);
                    return;
                case 10:
                    IntelligentBatterySaverFastDrainPolicy
                            intelligentBatterySaverFastDrainPolicy12 =
                                    IntelligentBatterySaverFastDrainPolicy.this;
                    intelligentBatterySaverFastDrainPolicy12.getClass();
                    Slog.d("IntelligentBatterySaverFastDrainPolicy", "bootCompleted");
                    if (intelligentBatterySaverFastDrainPolicy12.getBooleanState(
                            "ibs_policy_activated")) {
                        synchronized (intelligentBatterySaverFastDrainPolicy12.mActionsLock) {
                            for (int i13 = 1; i13 >= 0; i13--) {
                                Iterator it2 =
                                        ((ArrayList)
                                                        intelligentBatterySaverFastDrainPolicy12
                                                                .mActionsLevel.get(i13))
                                                .iterator();
                                while (it2.hasNext()) {
                                    ActionEntry actionEntry2 = (ActionEntry) it2.next();
                                    try {
                                        Slog.d(
                                                "IntelligentBatterySaverFastDrainPolicy",
                                                " cancel fast drain restriction "
                                                        + actionEntry2.tag);
                                        actionEntry2.callBack.cancelFastDrainRestriction();
                                    } catch (Exception e2) {
                                        e2.printStackTrace();
                                    }
                                }
                            }
                        }
                        return;
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IntelligentBatterySaverFastDrainReceiver extends BroadcastReceiver {
        public IntelligentBatterySaverFastDrainReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0
                    intelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0;
            if (intent == null || intent.getAction() == null) {
                return;
            }
            String action = intent.getAction();
            action.getClass();
            switch (action) {
                case "android.intent.action.TIME_SET":
                    IntelligentBatterySaverFastDrainPolicy intelligentBatterySaverFastDrainPolicy =
                            IntelligentBatterySaverFastDrainPolicy.this;
                    AlarmManager alarmManager =
                            intelligentBatterySaverFastDrainPolicy.mAlarmManager;
                    if (alarmManager != null
                            && (intelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0 =
                                            intelligentBatterySaverFastDrainPolicy
                                                    .mTimeoutAlarmListener)
                                    != null) {
                        alarmManager.cancel(
                                intelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0);
                    }
                    IntelligentBatterySaverFastDrainPolicy.this.updateActivated();
                    break;
                case "android.intent.action.ACTION_SHUTDOWN":
                case "android.intent.action.REBOOT":
                    IntelligentBatterySaverFastDrainHandler
                            intelligentBatterySaverFastDrainHandler =
                                    IntelligentBatterySaverFastDrainPolicy.this.mHandler;
                    if (intelligentBatterySaverFastDrainHandler != null) {
                        intelligentBatterySaverFastDrainHandler.sendMessage(
                                intelligentBatterySaverFastDrainHandler.obtainMessage(4));
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IntelligentBatterySaverFastDrainTrafficStat {
        public boolean initialized;
        public long rxBytes;
        public long sampleTimeInSecs;
        public long txBytes;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalTime {
        public final int hourOfDay;
        public final int minute;
        public final int second;

        public LocalTime(int i, int i2, int i3) {
            if (i < 0 || i > 23) {
                throw new IllegalArgumentException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid hourOfDay: "));
            }
            if (i2 < 0 || i2 > 59) {
                throw new IllegalArgumentException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "Invalid minute: "));
            }
            if (i3 < 0 || i3 > 59) {
                throw new IllegalArgumentException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(i3, "Invalid second: "));
            }
            this.hourOfDay = i;
            this.minute = i2;
            this.second = i3;
        }

        public final Calendar getDateTimeAfter(Calendar calendar) {
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(1, calendar.get(1));
            calendar2.set(6, calendar.get(6));
            calendar2.set(11, this.hourOfDay);
            calendar2.set(12, this.minute);
            calendar2.set(13, this.second);
            calendar2.set(14, 0);
            if (calendar2.before(calendar)) {
                calendar2.add(5, 1);
            }
            return calendar2;
        }

        public final String toString() {
            return String.format(
                    Locale.US,
                    "%02d:%02d:%02d",
                    Integer.valueOf(this.hourOfDay),
                    Integer.valueOf(this.minute),
                    Integer.valueOf(this.second));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MotionListener extends TriggerEventListener implements SensorEventListener {
        public boolean active = false;

        public MotionListener() {}

        @Override // android.hardware.SensorEventListener
        public final void onAccuracyChanged(Sensor sensor, int i) {}

        @Override // android.hardware.SensorEventListener
        public final void onSensorChanged(SensorEvent sensorEvent) {
            synchronized (IntelligentBatterySaverFastDrainPolicy.this) {
                IntelligentBatterySaverFastDrainPolicy intelligentBatterySaverFastDrainPolicy =
                        IntelligentBatterySaverFastDrainPolicy.this;
                intelligentBatterySaverFastDrainPolicy.mSensorManager.unregisterListener(
                        this, intelligentBatterySaverFastDrainPolicy.mMotionSensor);
                this.active = false;
            }
        }

        @Override // android.hardware.TriggerEventListener
        public final void onTrigger(TriggerEvent triggerEvent) {
            synchronized (IntelligentBatterySaverFastDrainPolicy.this) {
                this.active = false;
                Slog.d(
                        "IntelligentBatterySaverFastDrainPolicy",
                        "onTrigger clear MOTION_STILL bit");
                IntelligentBatterySaverFastDrainPolicy.this.reportClearState(16);
            }
        }
    }

    /* renamed from: -$$Nest$mregisterIntelligentBatterySaverFastDrainAction, reason: not valid java name */
    public static void m579$$Nest$mregisterIntelligentBatterySaverFastDrainAction(
            IntelligentBatterySaverFastDrainPolicy intelligentBatterySaverFastDrainPolicy,
            String str,
            IIntelligentBatterySaverFastDrainCallBack iIntelligentBatterySaverFastDrainCallBack) {
        intelligentBatterySaverFastDrainPolicy.getClass();
        if (iIntelligentBatterySaverFastDrainCallBack == null) {
            Slog.d(
                    "IntelligentBatterySaverFastDrainPolicy",
                    " registerIntelligentBatterySaverFastDrainAction error : level  1, callBack "
                            + iIntelligentBatterySaverFastDrainCallBack);
        } else {
            ActionEntry actionEntry = new ActionEntry();
            actionEntry.tag = str;
            actionEntry.callBack = iIntelligentBatterySaverFastDrainCallBack;
            synchronized (intelligentBatterySaverFastDrainPolicy.mActionsLock) {
                ((ArrayList) intelligentBatterySaverFastDrainPolicy.mActionsLevel.get(1))
                        .add(actionEntry);
            }
        }
    }

    /* renamed from: -$$Nest$msaveIntState, reason: not valid java name */
    public static void m580$$Nest$msaveIntState(
            IntelligentBatterySaverFastDrainPolicy intelligentBatterySaverFastDrainPolicy, int i) {
        if (intelligentBatterySaverFastDrainPolicy.mSharedPreferences == null
                && intelligentBatterySaverFastDrainPolicy.mContext != null) {
            Slog.d(
                    "IntelligentBatterySaverFastDrainPolicy",
                    "Create shared preferences when save state.");
            intelligentBatterySaverFastDrainPolicy.mSharedPreferences =
                    intelligentBatterySaverFastDrainPolicy.mContext.getSharedPreferences(
                            "oper_pref", 0);
        }
        SharedPreferences sharedPreferences =
                intelligentBatterySaverFastDrainPolicy.mSharedPreferences;
        if (sharedPreferences != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i,
                    "saveIntState type = disable_gps_by_ibs state = ",
                    "IntelligentBatterySaverFastDrainPolicy");
            edit.putInt("disable_gps_by_ibs", i);
            edit.apply();
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0] */
    public IntelligentBatterySaverFastDrainPolicy(
            Context context,
            HandlerThread handlerThread,
            IntelligentBatterySaverLogger intelligentBatterySaverLogger,
            IntelligentBatterySaverSurvey intelligentBatterySaverSurvey) {
        final int i = 0;
        this.mTimeoutAlarmListener =
                new AlarmManager.OnAlarmListener(
                        this) { // from class:
                                // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0
                    public final /* synthetic */ IntelligentBatterySaverFastDrainPolicy f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.app.AlarmManager.OnAlarmListener
                    public final void onAlarm() {
                        boolean registerListener;
                        int i2 = i;
                        IntelligentBatterySaverFastDrainPolicy
                                intelligentBatterySaverFastDrainPolicy = this.f$0;
                        switch (i2) {
                            case 0:
                                intelligentBatterySaverFastDrainPolicy.updateActivated();
                                break;
                            case 1:
                                if (intelligentBatterySaverFastDrainPolicy.mFastDrainInternalState
                                        != 5) {
                                    Slog.e(
                                            "IntelligentBatterySaverFastDrainPolicy",
                                            " safe check alarm triggered but state is safe check!");
                                    break;
                                } else {
                                    intelligentBatterySaverFastDrainPolicy
                                            .mIntelligentBatterySaverLogger.add(
                                            "safe check alarm trigger");
                                    intelligentBatterySaverFastDrainPolicy
                                            .scheduleSaveCheckTimeoutAlarm();
                                    intelligentBatterySaverFastDrainPolicy.mHandler
                                            .sendEmptyMessage(5);
                                    break;
                                }
                            case 2:
                                if (intelligentBatterySaverFastDrainPolicy.mFastDrainInternalState
                                        != 4) {
                                    Slog.e(
                                            "IntelligentBatterySaverFastDrainPolicy",
                                            " motion alarm triggered but state is not motion!");
                                    break;
                                } else {
                                    intelligentBatterySaverFastDrainPolicy.mFastDrainInternalState =
                                            5;
                                    intelligentBatterySaverFastDrainPolicy
                                            .mIntelligentBatterySaverLogger.add(
                                            "move to safe check");
                                    intelligentBatterySaverFastDrainPolicy.mSysState &= -97;
                                    intelligentBatterySaverFastDrainPolicy
                                                    .mTrafiicStat
                                                    .initialized =
                                            false;
                                    intelligentBatterySaverFastDrainPolicy
                                            .scheduleSaveCheckTimeoutAlarm();
                                    intelligentBatterySaverFastDrainPolicy.mHandler
                                            .sendEmptyMessage(5);
                                    break;
                                }
                            default:
                                if (intelligentBatterySaverFastDrainPolicy.mFastDrainInternalState
                                        != 3) {
                                    Slog.e(
                                            "IntelligentBatterySaverFastDrainPolicy",
                                            " inactive alarm triggered but state is not inactive!");
                                    break;
                                } else {
                                    intelligentBatterySaverFastDrainPolicy.mFastDrainInternalState =
                                            4;
                                    intelligentBatterySaverFastDrainPolicy
                                            .mIntelligentBatterySaverLogger.add(
                                            "move to state motion");
                                    intelligentBatterySaverFastDrainPolicy.reportSetState(16);
                                    if (intelligentBatterySaverFastDrainPolicy.mMotionSensor
                                            != null) {
                                        IntelligentBatterySaverFastDrainPolicy.MotionListener
                                                motionListener =
                                                        intelligentBatterySaverFastDrainPolicy
                                                                .mMotionListener;
                                        if (!motionListener.active) {
                                            if (IntelligentBatterySaverFastDrainPolicy.this
                                                            .mMotionSensor.getReportingMode()
                                                    == 2) {
                                                IntelligentBatterySaverFastDrainPolicy
                                                        intelligentBatterySaverFastDrainPolicy2 =
                                                                IntelligentBatterySaverFastDrainPolicy
                                                                        .this;
                                                registerListener =
                                                        intelligentBatterySaverFastDrainPolicy2
                                                                .mSensorManager
                                                                .requestTriggerSensor(
                                                                        intelligentBatterySaverFastDrainPolicy2
                                                                                .mMotionListener,
                                                                        intelligentBatterySaverFastDrainPolicy2
                                                                                .mMotionSensor);
                                            } else {
                                                IntelligentBatterySaverFastDrainPolicy
                                                        intelligentBatterySaverFastDrainPolicy3 =
                                                                IntelligentBatterySaverFastDrainPolicy
                                                                        .this;
                                                registerListener =
                                                        intelligentBatterySaverFastDrainPolicy3
                                                                .mSensorManager.registerListener(
                                                                intelligentBatterySaverFastDrainPolicy3
                                                                        .mMotionListener,
                                                                intelligentBatterySaverFastDrainPolicy3
                                                                        .mMotionSensor,
                                                                3);
                                            }
                                            if (registerListener) {
                                                motionListener.active = true;
                                            } else {
                                                Slog.e(
                                                        "IntelligentBatterySaverFastDrainPolicy",
                                                        "Unable to register for "
                                                                + IntelligentBatterySaverFastDrainPolicy
                                                                        .this
                                                                        .mMotionSensor);
                                            }
                                        }
                                    }
                                    long elapsedRealtime = SystemClock.elapsedRealtime();
                                    if (intelligentBatterySaverFastDrainPolicy.mAlarmManager
                                            == null) {
                                        intelligentBatterySaverFastDrainPolicy.mAlarmManager =
                                                (AlarmManager)
                                                        intelligentBatterySaverFastDrainPolicy
                                                                .mContext.getSystemService("alarm");
                                    }
                                    intelligentBatterySaverFastDrainPolicy.mAlarmManager.set(
                                            3,
                                            elapsedRealtime,
                                            "IntelligentBatterySaver.motion",
                                            intelligentBatterySaverFastDrainPolicy
                                                    .mMotionTimeoutAlarmListener,
                                            intelligentBatterySaverFastDrainPolicy.mHandler);
                                    break;
                                }
                        }
                    }
                };
        final int i2 = 1;
        this.mSafeCheckTimeoutAlarmListener =
                new AlarmManager.OnAlarmListener(
                        this) { // from class:
                                // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0
                    public final /* synthetic */ IntelligentBatterySaverFastDrainPolicy f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.app.AlarmManager.OnAlarmListener
                    public final void onAlarm() {
                        boolean registerListener;
                        int i22 = i2;
                        IntelligentBatterySaverFastDrainPolicy
                                intelligentBatterySaverFastDrainPolicy = this.f$0;
                        switch (i22) {
                            case 0:
                                intelligentBatterySaverFastDrainPolicy.updateActivated();
                                break;
                            case 1:
                                if (intelligentBatterySaverFastDrainPolicy.mFastDrainInternalState
                                        != 5) {
                                    Slog.e(
                                            "IntelligentBatterySaverFastDrainPolicy",
                                            " safe check alarm triggered but state is safe check!");
                                    break;
                                } else {
                                    intelligentBatterySaverFastDrainPolicy
                                            .mIntelligentBatterySaverLogger.add(
                                            "safe check alarm trigger");
                                    intelligentBatterySaverFastDrainPolicy
                                            .scheduleSaveCheckTimeoutAlarm();
                                    intelligentBatterySaverFastDrainPolicy.mHandler
                                            .sendEmptyMessage(5);
                                    break;
                                }
                            case 2:
                                if (intelligentBatterySaverFastDrainPolicy.mFastDrainInternalState
                                        != 4) {
                                    Slog.e(
                                            "IntelligentBatterySaverFastDrainPolicy",
                                            " motion alarm triggered but state is not motion!");
                                    break;
                                } else {
                                    intelligentBatterySaverFastDrainPolicy.mFastDrainInternalState =
                                            5;
                                    intelligentBatterySaverFastDrainPolicy
                                            .mIntelligentBatterySaverLogger.add(
                                            "move to safe check");
                                    intelligentBatterySaverFastDrainPolicy.mSysState &= -97;
                                    intelligentBatterySaverFastDrainPolicy
                                                    .mTrafiicStat
                                                    .initialized =
                                            false;
                                    intelligentBatterySaverFastDrainPolicy
                                            .scheduleSaveCheckTimeoutAlarm();
                                    intelligentBatterySaverFastDrainPolicy.mHandler
                                            .sendEmptyMessage(5);
                                    break;
                                }
                            default:
                                if (intelligentBatterySaverFastDrainPolicy.mFastDrainInternalState
                                        != 3) {
                                    Slog.e(
                                            "IntelligentBatterySaverFastDrainPolicy",
                                            " inactive alarm triggered but state is not inactive!");
                                    break;
                                } else {
                                    intelligentBatterySaverFastDrainPolicy.mFastDrainInternalState =
                                            4;
                                    intelligentBatterySaverFastDrainPolicy
                                            .mIntelligentBatterySaverLogger.add(
                                            "move to state motion");
                                    intelligentBatterySaverFastDrainPolicy.reportSetState(16);
                                    if (intelligentBatterySaverFastDrainPolicy.mMotionSensor
                                            != null) {
                                        IntelligentBatterySaverFastDrainPolicy.MotionListener
                                                motionListener =
                                                        intelligentBatterySaverFastDrainPolicy
                                                                .mMotionListener;
                                        if (!motionListener.active) {
                                            if (IntelligentBatterySaverFastDrainPolicy.this
                                                            .mMotionSensor.getReportingMode()
                                                    == 2) {
                                                IntelligentBatterySaverFastDrainPolicy
                                                        intelligentBatterySaverFastDrainPolicy2 =
                                                                IntelligentBatterySaverFastDrainPolicy
                                                                        .this;
                                                registerListener =
                                                        intelligentBatterySaverFastDrainPolicy2
                                                                .mSensorManager
                                                                .requestTriggerSensor(
                                                                        intelligentBatterySaverFastDrainPolicy2
                                                                                .mMotionListener,
                                                                        intelligentBatterySaverFastDrainPolicy2
                                                                                .mMotionSensor);
                                            } else {
                                                IntelligentBatterySaverFastDrainPolicy
                                                        intelligentBatterySaverFastDrainPolicy3 =
                                                                IntelligentBatterySaverFastDrainPolicy
                                                                        .this;
                                                registerListener =
                                                        intelligentBatterySaverFastDrainPolicy3
                                                                .mSensorManager.registerListener(
                                                                intelligentBatterySaverFastDrainPolicy3
                                                                        .mMotionListener,
                                                                intelligentBatterySaverFastDrainPolicy3
                                                                        .mMotionSensor,
                                                                3);
                                            }
                                            if (registerListener) {
                                                motionListener.active = true;
                                            } else {
                                                Slog.e(
                                                        "IntelligentBatterySaverFastDrainPolicy",
                                                        "Unable to register for "
                                                                + IntelligentBatterySaverFastDrainPolicy
                                                                        .this
                                                                        .mMotionSensor);
                                            }
                                        }
                                    }
                                    long elapsedRealtime = SystemClock.elapsedRealtime();
                                    if (intelligentBatterySaverFastDrainPolicy.mAlarmManager
                                            == null) {
                                        intelligentBatterySaverFastDrainPolicy.mAlarmManager =
                                                (AlarmManager)
                                                        intelligentBatterySaverFastDrainPolicy
                                                                .mContext.getSystemService("alarm");
                                    }
                                    intelligentBatterySaverFastDrainPolicy.mAlarmManager.set(
                                            3,
                                            elapsedRealtime,
                                            "IntelligentBatterySaver.motion",
                                            intelligentBatterySaverFastDrainPolicy
                                                    .mMotionTimeoutAlarmListener,
                                            intelligentBatterySaverFastDrainPolicy.mHandler);
                                    break;
                                }
                        }
                    }
                };
        final int i3 = 2;
        this.mMotionTimeoutAlarmListener =
                new AlarmManager.OnAlarmListener(
                        this) { // from class:
                                // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0
                    public final /* synthetic */ IntelligentBatterySaverFastDrainPolicy f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.app.AlarmManager.OnAlarmListener
                    public final void onAlarm() {
                        boolean registerListener;
                        int i22 = i3;
                        IntelligentBatterySaverFastDrainPolicy
                                intelligentBatterySaverFastDrainPolicy = this.f$0;
                        switch (i22) {
                            case 0:
                                intelligentBatterySaverFastDrainPolicy.updateActivated();
                                break;
                            case 1:
                                if (intelligentBatterySaverFastDrainPolicy.mFastDrainInternalState
                                        != 5) {
                                    Slog.e(
                                            "IntelligentBatterySaverFastDrainPolicy",
                                            " safe check alarm triggered but state is safe check!");
                                    break;
                                } else {
                                    intelligentBatterySaverFastDrainPolicy
                                            .mIntelligentBatterySaverLogger.add(
                                            "safe check alarm trigger");
                                    intelligentBatterySaverFastDrainPolicy
                                            .scheduleSaveCheckTimeoutAlarm();
                                    intelligentBatterySaverFastDrainPolicy.mHandler
                                            .sendEmptyMessage(5);
                                    break;
                                }
                            case 2:
                                if (intelligentBatterySaverFastDrainPolicy.mFastDrainInternalState
                                        != 4) {
                                    Slog.e(
                                            "IntelligentBatterySaverFastDrainPolicy",
                                            " motion alarm triggered but state is not motion!");
                                    break;
                                } else {
                                    intelligentBatterySaverFastDrainPolicy.mFastDrainInternalState =
                                            5;
                                    intelligentBatterySaverFastDrainPolicy
                                            .mIntelligentBatterySaverLogger.add(
                                            "move to safe check");
                                    intelligentBatterySaverFastDrainPolicy.mSysState &= -97;
                                    intelligentBatterySaverFastDrainPolicy
                                                    .mTrafiicStat
                                                    .initialized =
                                            false;
                                    intelligentBatterySaverFastDrainPolicy
                                            .scheduleSaveCheckTimeoutAlarm();
                                    intelligentBatterySaverFastDrainPolicy.mHandler
                                            .sendEmptyMessage(5);
                                    break;
                                }
                            default:
                                if (intelligentBatterySaverFastDrainPolicy.mFastDrainInternalState
                                        != 3) {
                                    Slog.e(
                                            "IntelligentBatterySaverFastDrainPolicy",
                                            " inactive alarm triggered but state is not inactive!");
                                    break;
                                } else {
                                    intelligentBatterySaverFastDrainPolicy.mFastDrainInternalState =
                                            4;
                                    intelligentBatterySaverFastDrainPolicy
                                            .mIntelligentBatterySaverLogger.add(
                                            "move to state motion");
                                    intelligentBatterySaverFastDrainPolicy.reportSetState(16);
                                    if (intelligentBatterySaverFastDrainPolicy.mMotionSensor
                                            != null) {
                                        IntelligentBatterySaverFastDrainPolicy.MotionListener
                                                motionListener =
                                                        intelligentBatterySaverFastDrainPolicy
                                                                .mMotionListener;
                                        if (!motionListener.active) {
                                            if (IntelligentBatterySaverFastDrainPolicy.this
                                                            .mMotionSensor.getReportingMode()
                                                    == 2) {
                                                IntelligentBatterySaverFastDrainPolicy
                                                        intelligentBatterySaverFastDrainPolicy2 =
                                                                IntelligentBatterySaverFastDrainPolicy
                                                                        .this;
                                                registerListener =
                                                        intelligentBatterySaverFastDrainPolicy2
                                                                .mSensorManager
                                                                .requestTriggerSensor(
                                                                        intelligentBatterySaverFastDrainPolicy2
                                                                                .mMotionListener,
                                                                        intelligentBatterySaverFastDrainPolicy2
                                                                                .mMotionSensor);
                                            } else {
                                                IntelligentBatterySaverFastDrainPolicy
                                                        intelligentBatterySaverFastDrainPolicy3 =
                                                                IntelligentBatterySaverFastDrainPolicy
                                                                        .this;
                                                registerListener =
                                                        intelligentBatterySaverFastDrainPolicy3
                                                                .mSensorManager.registerListener(
                                                                intelligentBatterySaverFastDrainPolicy3
                                                                        .mMotionListener,
                                                                intelligentBatterySaverFastDrainPolicy3
                                                                        .mMotionSensor,
                                                                3);
                                            }
                                            if (registerListener) {
                                                motionListener.active = true;
                                            } else {
                                                Slog.e(
                                                        "IntelligentBatterySaverFastDrainPolicy",
                                                        "Unable to register for "
                                                                + IntelligentBatterySaverFastDrainPolicy
                                                                        .this
                                                                        .mMotionSensor);
                                            }
                                        }
                                    }
                                    long elapsedRealtime = SystemClock.elapsedRealtime();
                                    if (intelligentBatterySaverFastDrainPolicy.mAlarmManager
                                            == null) {
                                        intelligentBatterySaverFastDrainPolicy.mAlarmManager =
                                                (AlarmManager)
                                                        intelligentBatterySaverFastDrainPolicy
                                                                .mContext.getSystemService("alarm");
                                    }
                                    intelligentBatterySaverFastDrainPolicy.mAlarmManager.set(
                                            3,
                                            elapsedRealtime,
                                            "IntelligentBatterySaver.motion",
                                            intelligentBatterySaverFastDrainPolicy
                                                    .mMotionTimeoutAlarmListener,
                                            intelligentBatterySaverFastDrainPolicy.mHandler);
                                    break;
                                }
                        }
                    }
                };
        final int i4 = 3;
        this.mInactiveTimeoutAlarmListener =
                new AlarmManager.OnAlarmListener(
                        this) { // from class:
                                // com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0
                    public final /* synthetic */ IntelligentBatterySaverFastDrainPolicy f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.app.AlarmManager.OnAlarmListener
                    public final void onAlarm() {
                        boolean registerListener;
                        int i22 = i4;
                        IntelligentBatterySaverFastDrainPolicy
                                intelligentBatterySaverFastDrainPolicy = this.f$0;
                        switch (i22) {
                            case 0:
                                intelligentBatterySaverFastDrainPolicy.updateActivated();
                                break;
                            case 1:
                                if (intelligentBatterySaverFastDrainPolicy.mFastDrainInternalState
                                        != 5) {
                                    Slog.e(
                                            "IntelligentBatterySaverFastDrainPolicy",
                                            " safe check alarm triggered but state is safe check!");
                                    break;
                                } else {
                                    intelligentBatterySaverFastDrainPolicy
                                            .mIntelligentBatterySaverLogger.add(
                                            "safe check alarm trigger");
                                    intelligentBatterySaverFastDrainPolicy
                                            .scheduleSaveCheckTimeoutAlarm();
                                    intelligentBatterySaverFastDrainPolicy.mHandler
                                            .sendEmptyMessage(5);
                                    break;
                                }
                            case 2:
                                if (intelligentBatterySaverFastDrainPolicy.mFastDrainInternalState
                                        != 4) {
                                    Slog.e(
                                            "IntelligentBatterySaverFastDrainPolicy",
                                            " motion alarm triggered but state is not motion!");
                                    break;
                                } else {
                                    intelligentBatterySaverFastDrainPolicy.mFastDrainInternalState =
                                            5;
                                    intelligentBatterySaverFastDrainPolicy
                                            .mIntelligentBatterySaverLogger.add(
                                            "move to safe check");
                                    intelligentBatterySaverFastDrainPolicy.mSysState &= -97;
                                    intelligentBatterySaverFastDrainPolicy
                                                    .mTrafiicStat
                                                    .initialized =
                                            false;
                                    intelligentBatterySaverFastDrainPolicy
                                            .scheduleSaveCheckTimeoutAlarm();
                                    intelligentBatterySaverFastDrainPolicy.mHandler
                                            .sendEmptyMessage(5);
                                    break;
                                }
                            default:
                                if (intelligentBatterySaverFastDrainPolicy.mFastDrainInternalState
                                        != 3) {
                                    Slog.e(
                                            "IntelligentBatterySaverFastDrainPolicy",
                                            " inactive alarm triggered but state is not inactive!");
                                    break;
                                } else {
                                    intelligentBatterySaverFastDrainPolicy.mFastDrainInternalState =
                                            4;
                                    intelligentBatterySaverFastDrainPolicy
                                            .mIntelligentBatterySaverLogger.add(
                                            "move to state motion");
                                    intelligentBatterySaverFastDrainPolicy.reportSetState(16);
                                    if (intelligentBatterySaverFastDrainPolicy.mMotionSensor
                                            != null) {
                                        IntelligentBatterySaverFastDrainPolicy.MotionListener
                                                motionListener =
                                                        intelligentBatterySaverFastDrainPolicy
                                                                .mMotionListener;
                                        if (!motionListener.active) {
                                            if (IntelligentBatterySaverFastDrainPolicy.this
                                                            .mMotionSensor.getReportingMode()
                                                    == 2) {
                                                IntelligentBatterySaverFastDrainPolicy
                                                        intelligentBatterySaverFastDrainPolicy2 =
                                                                IntelligentBatterySaverFastDrainPolicy
                                                                        .this;
                                                registerListener =
                                                        intelligentBatterySaverFastDrainPolicy2
                                                                .mSensorManager
                                                                .requestTriggerSensor(
                                                                        intelligentBatterySaverFastDrainPolicy2
                                                                                .mMotionListener,
                                                                        intelligentBatterySaverFastDrainPolicy2
                                                                                .mMotionSensor);
                                            } else {
                                                IntelligentBatterySaverFastDrainPolicy
                                                        intelligentBatterySaverFastDrainPolicy3 =
                                                                IntelligentBatterySaverFastDrainPolicy
                                                                        .this;
                                                registerListener =
                                                        intelligentBatterySaverFastDrainPolicy3
                                                                .mSensorManager.registerListener(
                                                                intelligentBatterySaverFastDrainPolicy3
                                                                        .mMotionListener,
                                                                intelligentBatterySaverFastDrainPolicy3
                                                                        .mMotionSensor,
                                                                3);
                                            }
                                            if (registerListener) {
                                                motionListener.active = true;
                                            } else {
                                                Slog.e(
                                                        "IntelligentBatterySaverFastDrainPolicy",
                                                        "Unable to register for "
                                                                + IntelligentBatterySaverFastDrainPolicy
                                                                        .this
                                                                        .mMotionSensor);
                                            }
                                        }
                                    }
                                    long elapsedRealtime = SystemClock.elapsedRealtime();
                                    if (intelligentBatterySaverFastDrainPolicy.mAlarmManager
                                            == null) {
                                        intelligentBatterySaverFastDrainPolicy.mAlarmManager =
                                                (AlarmManager)
                                                        intelligentBatterySaverFastDrainPolicy
                                                                .mContext.getSystemService("alarm");
                                    }
                                    intelligentBatterySaverFastDrainPolicy.mAlarmManager.set(
                                            3,
                                            elapsedRealtime,
                                            "IntelligentBatterySaver.motion",
                                            intelligentBatterySaverFastDrainPolicy
                                                    .mMotionTimeoutAlarmListener,
                                            intelligentBatterySaverFastDrainPolicy.mHandler);
                                    break;
                                }
                        }
                    }
                };
        this.mContext = context;
        this.mIntelligentBatterySaverLogger = intelligentBatterySaverLogger;
        this.mIntelligentBatterySaverSurvey = intelligentBatterySaverSurvey;
        IntelligentBatterySaverFastDrainTrafficStat intelligentBatterySaverFastDrainTrafficStat =
                new IntelligentBatterySaverFastDrainTrafficStat();
        intelligentBatterySaverFastDrainTrafficStat.initialized = false;
        intelligentBatterySaverFastDrainTrafficStat.sampleTimeInSecs = 0L;
        intelligentBatterySaverFastDrainTrafficStat.txBytes = 0L;
        intelligentBatterySaverFastDrainTrafficStat.rxBytes = 0L;
        this.mTrafiicStat = intelligentBatterySaverFastDrainTrafficStat;
        IntelligentBatterySaverFastDrainBatteryInfo intelligentBatterySaverFastDrainBatteryInfo =
                new IntelligentBatterySaverFastDrainBatteryInfo();
        intelligentBatterySaverFastDrainBatteryInfo.initialized = false;
        intelligentBatterySaverFastDrainBatteryInfo.startTime = 0L;
        intelligentBatterySaverFastDrainBatteryInfo.level = -1;
        this.mBatteryInfo = intelligentBatterySaverFastDrainBatteryInfo;
        IntelligentBatterySaverBatteryBigData intelligentBatterySaverBatteryBigData =
                new IntelligentBatterySaverBatteryBigData();
        intelligentBatterySaverBatteryBigData.initialized = false;
        intelligentBatterySaverBatteryBigData.actionEnabled = false;
        intelligentBatterySaverBatteryBigData.drainHightCurrent =
                FullScreenMagnificationGestureHandler.MAX_SCALE;
        intelligentBatterySaverBatteryBigData.restrictedCurrent =
                FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.mIBSBigData = intelligentBatterySaverBatteryBigData;
        this.mInited = false;
        this.mStartTime = getCustomTime(23, 0);
        this.mEndTime = getCustomTime(7, 0);
        this.mScreenOn = true;
        this.mCharging = true;
        this.mSysState = 0;
        this.mFastDrainInternalState = 2;
        ArrayList arrayList = new ArrayList();
        this.mActionsLevel = arrayList;
        arrayList.add(new ArrayList());
        arrayList.add(new ArrayList());
        this.mHandler = new IntelligentBatterySaverFastDrainHandler(handlerThread.getLooper());
        IntelligentBatterySaverFastDrainAction intelligentBatterySaverFastDrainAction =
                new IntelligentBatterySaverFastDrainAction(context);
        m579$$Nest$mregisterIntelligentBatterySaverFastDrainAction(
                this, "GPS_IBSFastDrainAction", intelligentBatterySaverFastDrainAction.gpsCallBack);
        m579$$Nest$mregisterIntelligentBatterySaverFastDrainAction(
                this,
                "wifi_IBSFastDrainAction",
                intelligentBatterySaverFastDrainAction.wifiCallBack);
        m579$$Nest$mregisterIntelligentBatterySaverFastDrainAction(
                this,
                "mobiledata_IBSFastDrainAction",
                intelligentBatterySaverFastDrainAction.mobiledataCallBack);
        m579$$Nest$mregisterIntelligentBatterySaverFastDrainAction(
                this, "bt_IBSFastDrainAction", intelligentBatterySaverFastDrainAction.btCallBack);
    }

    public static LocalTime getCustomTime(int i, int i2) {
        int i3 = (i2 * 60) + (i * 3600);
        return new LocalTime(i3 / 3600, (i3 % 3600) / 60, i3 % 60);
    }

    public final void exitFastDrainRestriction() {
        long j = this.mEnterIBSTime / 1000;
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        int i = this.mBatteryInfo.level;
        long j2 = currentTimeMillis - j;
        if (j2 == 0) {
            j2 = 1;
        }
        float f =
                (((this.mEstimatedBatteryCapacity / 100.0f) * (this.mEnterIBSBatteryLevel - i))
                                * 3600.0f)
                        / j2;
        IntelligentBatterySaverBatteryBigData intelligentBatterySaverBatteryBigData =
                this.mIBSBigData;
        intelligentBatterySaverBatteryBigData.restrictedCurrent = f;
        intelligentBatterySaverBatteryBigData.actionEnabled = testState(128);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("IBSEN", intelligentBatterySaverBatteryBigData.actionEnabled);
            jSONObject.put("IBSDHC", intelligentBatterySaverBatteryBigData.drainHightCurrent);
            jSONObject.put("IBSRC", intelligentBatterySaverBatteryBigData.restrictedCurrent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jSONObject2 = jSONObject.toString();
        String m = DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(1, 1, jSONObject2);
        IntelligentBatterySaverSurvey intelligentBatterySaverSurvey =
                this.mIntelligentBatterySaverSurvey;
        if (intelligentBatterySaverSurvey.mBigdataEnable) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", "com.android.server.ibs");
            contentValues.put(LauncherConfigurationInternal.KEY_FEATURE_INT, "IBS");
            if (m != null) {
                contentValues.put("extra", m);
            }
            Intent intent = new Intent();
            intent.setAction(
                    "com.samsung.android.providers.context.log.action.USE_APP_FEATURE_SURVEY");
            intent.putExtra("data", contentValues);
            intent.setPackage("com.samsung.android.providers.context");
            intelligentBatterySaverSurvey.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
        }
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                "app_id = com.android.server.ibs, feature = IBS, extra = ",
                m,
                "IntelligentBatterySaverSurvey");
        IntelligentBatterySaverBatteryBigData intelligentBatterySaverBatteryBigData2 =
                this.mIBSBigData;
        intelligentBatterySaverBatteryBigData2.initialized = false;
        intelligentBatterySaverBatteryBigData2.actionEnabled = false;
        intelligentBatterySaverBatteryBigData2.drainHightCurrent =
                FullScreenMagnificationGestureHandler.MAX_SCALE;
        intelligentBatterySaverBatteryBigData2.restrictedCurrent =
                FullScreenMagnificationGestureHandler.MAX_SCALE;
        resetBatteryInfo();
        if (!testState(128)) {
            Slog.d(
                    "IntelligentBatterySaverFastDrainPolicy",
                    "UI switch off disable the cancel fast drain restriction.");
            return;
        }
        boolean z =
                (!((WifiManager) this.mContext.getSystemService("wifi")).isWifiEnabled()
                                && getBooleanState("disable_wifi_by_ibs"))
                        || (!TelephonyManager.from(this.mContext).isDataEnabled()
                                && getBooleanState("disable_mobile_data_by_ibs"));
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "needSendBroadCast need = ", "IntelligentBatterySaverFastDrainPolicy", z);
        if (z) {
            Intent intent2 = new Intent();
            intent2.setAction("com.samsung.android.sm.ACTION_FAST_BATTERY_DRAIN_DETECTED");
            intent2.putExtra("trigger_time", this.mEnterIBSTime);
            intent2.setClassName(
                    "com.samsung.android.sm_cn",
                    "com.samsung.android.sm.battery.receiver.FastBatteryDrainReceiver");
            this.mContext.sendBroadcast(intent2);
        }
        synchronized (this.mActionsLock) {
            for (int i2 = 1; i2 >= 0; i2--) {
                Iterator it = ((ArrayList) this.mActionsLevel.get(i2)).iterator();
                while (it.hasNext()) {
                    ActionEntry actionEntry = (ActionEntry) it.next();
                    try {
                        Slog.d(
                                "IntelligentBatterySaverFastDrainPolicy",
                                " cancel fast drain restriction " + actionEntry.tag);
                        actionEntry.callBack.cancelFastDrainRestriction();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
            saveBooleanState("ibs_policy_activated", false);
        }
    }

    public final boolean getBooleanState(String str) {
        if (this.mSharedPreferences == null && this.mContext != null) {
            Slog.d(
                    "IntelligentBatterySaverFastDrainPolicy",
                    "Create shared preferences when get state.");
            this.mSharedPreferences = this.mContext.getSharedPreferences("oper_pref", 0);
        }
        SharedPreferences sharedPreferences = this.mSharedPreferences;
        if (sharedPreferences == null) {
            return false;
        }
        boolean z = sharedPreferences.getBoolean(str, false);
        Slog.d(
                "IntelligentBatterySaverFastDrainPolicy",
                "getBooleanState type = " + str + " state = " + z);
        return z;
    }

    public final void initAlarm(boolean z) {
        IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0
                intelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0;
        if (z) {
            this.mAlarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
            updateActivated();
            return;
        }
        AlarmManager alarmManager = this.mAlarmManager;
        if (alarmManager == null
                || (intelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0 =
                                this.mTimeoutAlarmListener)
                        == null) {
            return;
        }
        alarmManager.cancel(intelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0);
    }

    public final void initBroadcast(boolean z) {
        if (!z) {
            this.mContext.unregisterReceiver(this.mReceiver);
            return;
        }
        this.mReceiver = new IntelligentBatterySaverFastDrainReceiver();
        IntentFilter intentFilter = new IntentFilter();
        this.mFilter = intentFilter;
        intentFilter.addAction("android.intent.action.TIME_SET");
        this.mFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        this.mFilter.addAction("android.intent.action.REBOOT");
        this.mContext.registerReceiver(this.mReceiver, this.mFilter, 2);
    }

    public final void moveToStateOFF(
            String str, AlarmManager.OnAlarmListener onAlarmListener, boolean z) {
        this.mFastDrainInternalState = 2;
        Slog.d("IntelligentBatterySaverFastDrainPolicy", str);
        this.mIntelligentBatterySaverLogger.add(str);
        if (this.mMotionSensor != null) {
            MotionListener motionListener = this.mMotionListener;
            if (motionListener.active) {
                if (IntelligentBatterySaverFastDrainPolicy.this.mMotionSensor.getReportingMode()
                        == 2) {
                    IntelligentBatterySaverFastDrainPolicy intelligentBatterySaverFastDrainPolicy =
                            IntelligentBatterySaverFastDrainPolicy.this;
                    intelligentBatterySaverFastDrainPolicy.mSensorManager.cancelTriggerSensor(
                            intelligentBatterySaverFastDrainPolicy.mMotionListener,
                            intelligentBatterySaverFastDrainPolicy.mMotionSensor);
                } else {
                    IntelligentBatterySaverFastDrainPolicy intelligentBatterySaverFastDrainPolicy2 =
                            IntelligentBatterySaverFastDrainPolicy.this;
                    intelligentBatterySaverFastDrainPolicy2.mSensorManager.unregisterListener(
                            intelligentBatterySaverFastDrainPolicy2.mMotionListener);
                }
                motionListener.active = false;
            }
        }
        AlarmManager alarmManager = this.mAlarmManager;
        if (alarmManager != null && onAlarmListener != null) {
            alarmManager.cancel(onAlarmListener);
        }
        if (z) {
            this.mSysState &= -97;
            this.mTrafiicStat.initialized = false;
        }
    }

    public final void reportClearState(int i) {
        IntelligentBatterySaverFastDrainHandler intelligentBatterySaverFastDrainHandler =
                this.mHandler;
        if (intelligentBatterySaverFastDrainHandler != null) {
            intelligentBatterySaverFastDrainHandler.sendMessage(
                    intelligentBatterySaverFastDrainHandler.obtainMessage(9, i, 0));
        }
    }

    public final void reportSetState(int i) {
        IntelligentBatterySaverFastDrainHandler intelligentBatterySaverFastDrainHandler =
                this.mHandler;
        if (intelligentBatterySaverFastDrainHandler != null) {
            intelligentBatterySaverFastDrainHandler.sendMessage(
                    intelligentBatterySaverFastDrainHandler.obtainMessage(8, i, 0));
        }
    }

    public final void resetBatteryInfo() {
        IntelligentBatterySaverFastDrainBatteryInfo intelligentBatterySaverFastDrainBatteryInfo =
                this.mBatteryInfo;
        intelligentBatterySaverFastDrainBatteryInfo.initialized = false;
        intelligentBatterySaverFastDrainBatteryInfo.level = -1;
        intelligentBatterySaverFastDrainBatteryInfo.startTime = 0L;
    }

    public final void saveBooleanState(String str, boolean z) {
        if (this.mSharedPreferences == null && this.mContext != null) {
            Slog.d(
                    "IntelligentBatterySaverFastDrainPolicy",
                    "Create shared preferences when save state.");
            this.mSharedPreferences = this.mContext.getSharedPreferences("oper_pref", 0);
        }
        SharedPreferences sharedPreferences = this.mSharedPreferences;
        if (sharedPreferences != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            Slog.d(
                    "IntelligentBatterySaverFastDrainPolicy",
                    "saveBooleanState type = " + str + " state = " + z);
            edit.putBoolean(str, z);
            edit.apply();
        }
    }

    public final void scheduleInactiveTimeoutAlarm(long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime() + j;
        if (this.mAlarmManager == null) {
            this.mAlarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        }
        this.mAlarmManager.set(
                3,
                elapsedRealtime,
                "IntelligentBatterySaver.inactive",
                this.mInactiveTimeoutAlarmListener,
                this.mHandler);
    }

    public final void scheduleSaveCheckTimeoutAlarm() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (this.mAlarmManager == null) {
            this.mAlarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        }
        this.mAlarmManager.set(
                3,
                elapsedRealtime,
                "IntelligentBatterySaver.safecheck",
                this.mSafeCheckTimeoutAlarmListener,
                this.mHandler);
    }

    public final void setIBSFastDrainPolicyEnable(boolean z) {
        IntelligentBatterySaverFastDrainHandler intelligentBatterySaverFastDrainHandler =
                this.mHandler;
        if (z) {
            reportSetState(1);
            Slog.d(
                    "IntelligentBatterySaverFastDrainPolicy",
                    "startIntelligentBatterySaverFastDrainPolicy");
            intelligentBatterySaverFastDrainHandler.sendEmptyMessage(1);
        } else {
            reportClearState(1);
            Slog.d(
                    "IntelligentBatterySaverFastDrainPolicy",
                    "stopIntelligentBatterySaverFastDrainPolicy");
            intelligentBatterySaverFastDrainHandler.sendEmptyMessage(2);
        }
    }

    public final void setSysState(int i) {
        int i2 = i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
        int i3 = this.mSysState;
        if (i2 != i3) {
            int i4 = i3 ^ i2;
            Slog.d(
                    "IntelligentBatterySaverFastDrainPolicy",
                    "Sys State changed : [old , new, changebits] : ["
                            + Integer.toBinaryString(this.mSysState)
                            + ","
                            + Integer.toBinaryString(i2)
                            + ","
                            + Integer.toBinaryString(i4)
                            + "]");
            this.mIntelligentBatterySaverLogger.add(
                    "Sys State changed : [old , new, changebits] : ["
                            + Integer.toBinaryString(this.mSysState)
                            + ","
                            + Integer.toBinaryString(i2)
                            + ","
                            + Integer.toBinaryString(i4)
                            + "]");
            this.mSysState = i2;
            updateFastDrainInternalState();
        }
    }

    public final boolean testState(int i) {
        return (this.mSysState & i) == i;
    }

    public final void updateActivated() {
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = this.mStartTime;
        localTime.getClass();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(1, calendar.get(1));
        calendar2.set(6, calendar.get(6));
        calendar2.set(11, localTime.hourOfDay);
        calendar2.set(12, localTime.minute);
        calendar2.set(13, localTime.second);
        calendar2.set(14, 0);
        if (calendar2.after(calendar)) {
            calendar2.add(5, -1);
        }
        boolean before = calendar.before(this.mEndTime.getDateTimeAfter(calendar2));
        Calendar dateTimeAfter =
                before
                        ? this.mEndTime.getDateTimeAfter(calendar)
                        : this.mStartTime.getDateTimeAfter(calendar);
        if (before) {
            reportSetState(2);
            Slog.d(
                    "IntelligentBatterySaverFastDrainPolicy",
                    "In Active Duration, set inactive alarm at "
                            + dateTimeAfter.get(11)
                            + ":"
                            + dateTimeAfter.get(12));
        } else {
            reportClearState(2);
            Slog.d(
                    "IntelligentBatterySaverFastDrainPolicy",
                    "Out Active Duration, set active alarm at "
                            + dateTimeAfter.get(11)
                            + ":"
                            + dateTimeAfter.get(12));
        }
        this.mAlarmManager.setExact(
                0,
                dateTimeAfter.getTimeInMillis(),
                "IntelligentBatterySaverFastDrainPolicy",
                this.mTimeoutAlarmListener,
                null);
    }

    public final void updateFastDrainInternalState() {
        int i = this.mFastDrainInternalState;
        IntelligentBatterySaverLogger intelligentBatterySaverLogger =
                this.mIntelligentBatterySaverLogger;
        switch (i) {
            case 1:
                if (!testState(15)) {
                    exitFastDrainRestriction();
                    moveToStateOFF("move to off state", null, true);
                    break;
                } else if (!testState(16)) {
                    exitFastDrainRestriction();
                    moveToStateOFF("move to off state, device move", null, true);
                    updateFastDrainInternalState();
                    break;
                }
                break;
            case 2:
                if (testState(15)) {
                    this.mFastDrainInternalState = 3;
                    intelligentBatterySaverLogger.add("move to inactive");
                    if (!((PowerManager) this.mContext.getSystemService("power"))
                            .isDeviceIdleMode()) {
                        scheduleInactiveTimeoutAlarm(600000L);
                        break;
                    } else {
                        scheduleInactiveTimeoutAlarm(0L);
                        break;
                    }
                }
                break;
            case 3:
                if (!testState(15)) {
                    moveToStateOFF(
                            "move to off state from inactive",
                            this.mInactiveTimeoutAlarmListener,
                            false);
                    break;
                } else {
                    Slog.e(
                            "IntelligentBatterySaverFastDrainPolicy",
                            " extra bits report in inactive state!");
                    break;
                }
            case 4:
                boolean testState = testState(15);
                IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0
                        intelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0 =
                                this.mMotionTimeoutAlarmListener;
                if (!testState) {
                    moveToStateOFF(
                            "move to off state from motion",
                            intelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0,
                            false);
                    break;
                } else if (!testState(16)) {
                    moveToStateOFF(
                            "move to off state from motion, device move",
                            intelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0,
                            false);
                    updateFastDrainInternalState();
                    break;
                }
                break;
            case 5:
                boolean testState2 = testState(15);
                IntelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda0
                        intelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda02 =
                                this.mSafeCheckTimeoutAlarmListener;
                if (!testState2) {
                    moveToStateOFF(
                            "move to off state from safe check",
                            intelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda02,
                            true);
                    break;
                } else if (!testState(16)) {
                    moveToStateOFF(
                            "move to off state from safe check, device move",
                            intelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda02,
                            true);
                    updateFastDrainInternalState();
                    break;
                } else if (testState(96)) {
                    AlarmManager alarmManager = this.mAlarmManager;
                    if (alarmManager != null
                            && intelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda02
                                    != null) {
                        alarmManager.cancel(
                                intelligentBatterySaverFastDrainPolicy$$ExternalSyntheticLambda02);
                    }
                    this.mFastDrainInternalState = 6;
                    intelligentBatterySaverLogger.add("move to battery check state");
                    intelligentBatterySaverLogger.add("triggerIBS");
                    IntelligentBatterySaverFastDrainHandler
                            intelligentBatterySaverFastDrainHandler = this.mHandler;
                    if (intelligentBatterySaverFastDrainHandler != null) {
                        intelligentBatterySaverFastDrainHandler.sendMessage(
                                intelligentBatterySaverFastDrainHandler.obtainMessage(3));
                        break;
                    }
                }
                break;
            case 6:
                if (!testState(15)) {
                    moveToStateOFF("move to off state", null, true);
                    resetBatteryInfo();
                    break;
                }
                break;
        }
    }
}
