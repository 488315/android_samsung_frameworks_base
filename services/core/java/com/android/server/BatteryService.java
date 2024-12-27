package com.android.server;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.BroadcastOptions;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;
import android.hardware.health.HealthInfo;
import android.hardware.input.InputManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.BatteryManagerInternal;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.FileUtils;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBatteryPropertiesRegistrar;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SemHqmManager;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UEventObserver;
import android.os.UserHandle;
import android.provider.Settings;
import android.sysprop.PowerProperties;
import android.telephony.TelephonyManager;
import android.util.proto.ProtoOutputStream;
import com.android.internal.app.IBatteryStats;
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService;
import com.android.server.am.BatteryStatsService;
import com.android.server.battery.BattFeatures;
import com.android.server.battery.BattHqmManager;
import com.android.server.battery.BattLogBuffer;
import com.android.server.battery.BattUtils;
import com.android.server.battery.BatteryLogger;
import com.android.server.battery.batteryInfo.AsocData$$ExternalSyntheticLambda1;
import com.android.server.battery.batteryInfo.BattInfoManager;
import com.android.server.battery.sleepcharging.SleepChargingManager;
import com.android.server.battery.sleepcharging.SleepChargingManager.AnonymousClass1;
import com.android.server.battery.sleepcharging.SleepChargingTimeController;
import com.android.server.health.HealthServiceWrapper;
import com.android.server.health.Utils;
import com.android.server.lights.LightsManager;
import com.android.server.lights.LightsService;
import com.android.server.power.PowerManagerUtil;
import com.android.server.power.Slog;
import com.att.iqi.lib.IQIManager;
import com.att.iqi.lib.metrics.hw.HW12;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knoxguard.service.utils.Constants;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import org.json.JSONObject;
import vendor.samsung.hardware.health.SehHealthInfo;

public final class BatteryService extends SystemService {
    public static final boolean FEATURE_SUPPORTED_DAILY_BOARD;
    public static final boolean FEATURE_WIRELESS_FAST_CHARGER_CONTROL;
    public boolean isVideoCall;
    public final ActivityManagerInternal mActivityManagerInternal;
    public boolean mActivityManagerReady;
    public final int mAdaptiveFastChargingOffset;
    public boolean mAdaptiveFastChargingSettingsEnable;
    public final AnonymousClass15 mAdaptiveFastChargingSettingsObserver;
    public final String mAfcDisableSysFs;
    public final AnonymousClass2 mAudioModeChangeReceiver;
    public BattInfoManager mBattInfoManager;
    public final AnonymousClass5 mBattSlateModeControlReceiver;
    public final int[] mBatteryChangedConditionCounts;
    public final Bundle mBatteryChangedOptions;
    public boolean mBatteryInputSuspended;
    public boolean mBatteryLevelCritical;
    public boolean mBatteryLevelLow;
    public final ArrayDeque mBatteryLevelsEventQueue;
    public long mBatteryMaxCurrent;
    public long mBatteryMaxTemp;
    public final Bundle mBatteryOptions;
    public final IBatteryStats mBatteryStats;
    public BinderService mBinderService;
    public boolean mBootCompleted;
    public final AnonymousClass2 mBootCompletedReceiver;
    public final AnonymousClass1 mCallHandler;
    public final HandlerThread mCallHandlerThread;
    public int mChargeStartLevel;
    public long mChargeStartTime;
    public final CopyOnWriteArraySet mChargingPolicyChangeListeners;
    public final Context mContext;
    public final int mCriticalBatteryLevel;
    public Calendar mCurrentCalendar;
    public final AnonymousClass2 mDexReceiver;
    public int mDischargeStartLevel;
    public long mDischargeStartTime;
    public final boolean mEnableIqi;
    public final AnonymousClass2 mFastWirelessAutoModeReceiver;
    public boolean mFullCapacityEnable;
    public final AnonymousClass15 mFullCapacityEnableSettingsObserver;
    public final Handler mHandler;
    public final Handler mHandlerForBatteryInfoBackUp;
    public HealthInfo mHealthInfo;
    public HealthServiceWrapper mHealthServiceWrapper;
    public final AnonymousClass2 mHiccupControlReceiver;
    public final AnonymousClass2 mIntentReceiver;
    public int mInvalidCharger;
    public boolean mIsFirstIntentSended;
    public boolean mIsHiccupPopupShowing;
    public boolean mIsSkipActionBatteryChanged;
    public boolean mIsUnlockedBootCompleted;
    public boolean mIsWirelessTxSupported;
    public int mLastBatteryChargeType;
    public int mLastBatteryCurrentEvent;
    public int mLastBatteryCurrentNow;
    public int mLastBatteryCycleCount;
    public int mLastBatteryEvent;
    public boolean mLastBatteryEventWaterInConnector;
    public int mLastBatteryHealth;
    public int mLastBatteryHighVoltageCharger;
    public int mLastBatteryLevel;
    public long mLastBatteryLevelChangedSentMs;
    public boolean mLastBatteryLevelCritical;
    public int mLastBatteryOnline;
    public boolean mLastBatteryPowerSharingOnline;
    public boolean mLastBatteryPresent;
    public int mLastBatteryStatus;
    public int mLastBatteryTemperature;
    public int mLastBatteryVoltage;
    public int mLastChargingPolicy;
    public int mLastCharingState;
    public int mLastDeterioration;
    public int mLastInvalidCharger;
    public int mLastLowBatteryWarningLevel;
    public int mLastMaxChargingCurrent;
    public int mLastMaxChargingVoltage;
    public int mLastPlugType;
    public int mLastSecPlugTypeSummary;
    public final SehHealthInfo mLastSehHealthInfo;
    public boolean mLastTxEventRxConnected;
    public boolean mLastTxEventTxEnabled;
    public int mLastWcTxId;
    public boolean mLastWirelessBackPackChargingStatus;
    public boolean mLastWirelessChargingStatus;
    public boolean mLastWirelessPinDetected;
    public int mLastWirelessPowerSharingExternelEvent;
    public int mLastWirelessPowerSharingTxEvent;
    public boolean mLastchargerPogoOnline;
    public int mLatestWirelessChargingMode;
    public final Led mLed;
    public boolean mLedChargingSettingsEnable;
    public boolean mLedLowBatterySettingsEnable;
    public final AnonymousClass15 mLedSettingsObserver;
    public boolean mLifeExtender;
    public final AnonymousClass15 mLifeExtenderSettingsObserver;
    public final Object mLock;
    public final Object mLockBatteryInfoBackUp;
    public int mLongBatteryRetryCnt;
    public int mLowBatteryCloseWarningLevel;
    public int mLowBatteryWarningLevel;
    public int mLtcHighSocDuration;
    public int mLtcHighThreshold;
    public int mLtcReleaseThreshold;
    public String mManufactureDate;
    public int mMaximumProtectionThreshold;
    public AnonymousClass15 mMaximumProtectionThresholdObserver;
    public final MetricsLogger mMetricsLogger;
    public boolean mNotifyWirelessEnabled;
    public boolean mPassThroughSettingsEnable;
    public final AnonymousClass15 mPassThroughSettingsObserver;
    public int mPlugType;
    public int mPogoCondition;
    public int mPogoDockState;
    public final PowerManagerInternal mPowerManagerInternal;
    public final Bundle mPowerOptions;
    public int mProtectBatteryMode;
    public int mProtectionThreshold;
    public int mRefreshRateModeSetting;
    public final AnonymousClass15 mRefreshRateModeSettingsObserver;
    public final AnonymousClass2 mRequestOtgChargeBlockReceiver;
    public String mRfCalDate;
    public final AnonymousClass16 mSaveBatteryMaxCurrentRunnable;
    public final AnonymousClass16 mSaveBatteryMaxTempRunnable;
    public long mSavedBatteryMaxCurrent;
    public long mSavedBatteryMaxTemp;
    public int mSavedDiffWeek;
    public boolean mScreenOn;
    public int mSecPlugTypeSummary;
    public SehHealthInfo mSehHealthInfo;
    public SemInputDeviceManager mSemInputDeviceManager;
    public boolean mSentLowBatteryBroadcast;
    public int mSequence;
    public final boolean mShutdownIfNoPower;
    public final AnonymousClass1 mSkipActionBatteryChangedHandler;
    public AnonymousClass2 mSleepChargingDismissReceiver;
    public AnonymousClass1 mSleepChargingHandler;
    public SleepChargingManager mSleepChargingManager;
    public final int mSuperFastChargingOffset;
    public boolean mSuperFastChargingSettingsEnable;
    public final AnonymousClass15 mSuperFastChargingSettingsObserver;
    public final IBinder mToken;
    public int mTxBatteryLimit;
    public final AnonymousClass15 mTxBatteryLimitSettingsObserver;
    public final AnonymousClass16 mUpdateBatteryUsageExtenderRunnable;
    public final AnonymousClass16 mUpdateBatteryUsageFullCapacityEnableRunnable;
    public boolean mUpdatesStopped;
    public boolean mWasUsedWirelessFastChargerPreviously;
    public final AnonymousClass15 mWcParamInfoSettingsObserver;
    public final int mWcParamOffset;
    public int mWcTxId;
    public final int mWirelessFastChargingOffset;
    public boolean mWirelessFastChargingSettingsEnable;
    public final AnonymousClass15 mWirelessFastChargingSettingsObserver;
    public final AnonymousClass2 mWirelessPowerSharingReceiver;
    public final TelephonyManager tm;
    public static final String TAG_SS = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("[SS]", "BatteryService");
    public static final String[] DUMPSYS_ARGS = {"--checkin", "--unplugged"};
    public static final String[] ADAPTIVE_FAST_CHARGING_DISABLE_SYSFS_PATHS = {"/sys/class/sec/switch/afc_disable", "sys/class/sec/afc/afc_disable"};
    public static final boolean FEATURE_HICCUP_CONTROL = isFileSupported("/sys/class/sec/switch/hiccup");
    public static final String PACKAGE_DEVICE_CARE = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME");
    public static final String ACTION_ENTER_DESK_MODE = "com.samsung.android.desktopmode.action.ENTER_DESKTOP_MODE";
    public static final String ACTION_EXIT_DESK_MODE = "com.samsung.android.desktopmode.action.EXIT_DESKTOP_MODE";

    /* renamed from: com.android.server.BatteryService$1, reason: invalid class name */
    public final class AnonymousClass1 extends Handler {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ BatteryService this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ AnonymousClass1(BatteryService batteryService, Looper looper, int i) {
            super(looper);
            this.$r8$classId = i;
            this.this$0 = batteryService;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            String str;
            String str2;
            String str3;
            switch (this.$r8$classId) {
                case 0:
                    String str4 = BatteryService.TAG_SS;
                    Slog.i(str4, "[bs_handleMessage]msg:" + message.what);
                    BatteryService batteryService = this.this$0;
                    if (batteryService.mProtectBatteryMode != 4) {
                        Slog.e(str4, "[bs_handleMessage]Currently, Not Battery Adaptive Protect Mode");
                        return;
                    }
                    int i = message.what;
                    if (i != 1) {
                        str = "";
                        if (i == 2) {
                            BatteryService.m43$$Nest$msetSleepCharging(batteryService, false);
                            r1 = message.arg1 == 1;
                            str2 = "off";
                        } else {
                            if (i != 3) {
                                str3 = "";
                                Intent intent = new Intent("com.samsung.server.BatteryService.action.ACTION_SLEEP_CHARGING");
                                intent.setFlags(16777216);
                                intent.putExtra("sleep_charging_event", str);
                                intent.putExtra("sleep_charging_finish_time", str3);
                                intent.putExtra("is_sleep_charging_complete_success", r1);
                                StringBuilder m = InitialConfiguration$$ExternalSyntheticOutline0.m("[bs_handleMessage]extraEvent:", str, " ,extraSleepChargingFinishTime:", str3, " ,isSleepChargingCompleteSuccess:");
                                m.append(r1);
                                Slog.i(str4, m.toString());
                                BatteryService.m44$$Nest$smsendBroadcastToExplicitPackage(batteryService.mContext, intent, Constants.SYSTEMUI_PACKAGE_NAME);
                                BatteryService.m44$$Nest$smsendBroadcastToExplicitPackage(batteryService.mContext, intent, BatteryService.PACKAGE_DEVICE_CARE);
                                return;
                            }
                            str = (String) message.obj;
                            str2 = "update";
                        }
                    } else {
                        BatteryService.m43$$Nest$msetSleepCharging(batteryService, true);
                        str = (String) message.obj;
                        str2 = "on";
                    }
                    String str5 = str;
                    str = str2;
                    str3 = str5;
                    Intent intent2 = new Intent("com.samsung.server.BatteryService.action.ACTION_SLEEP_CHARGING");
                    intent2.setFlags(16777216);
                    intent2.putExtra("sleep_charging_event", str);
                    intent2.putExtra("sleep_charging_finish_time", str3);
                    intent2.putExtra("is_sleep_charging_complete_success", r1);
                    StringBuilder m2 = InitialConfiguration$$ExternalSyntheticOutline0.m("[bs_handleMessage]extraEvent:", str, " ,extraSleepChargingFinishTime:", str3, " ,isSleepChargingCompleteSuccess:");
                    m2.append(r1);
                    Slog.i(str4, m2.toString());
                    BatteryService.m44$$Nest$smsendBroadcastToExplicitPackage(batteryService.mContext, intent2, Constants.SYSTEMUI_PACKAGE_NAME);
                    BatteryService.m44$$Nest$smsendBroadcastToExplicitPackage(batteryService.mContext, intent2, BatteryService.PACKAGE_DEVICE_CARE);
                    return;
                case 1:
                    int i2 = message.what;
                    BatteryService batteryService2 = this.this$0;
                    if (i2 != 0) {
                        if (i2 == 1) {
                            BatteryService.m42$$Nest$msetCallWirelessPowerSharingExternelEvent(batteryService2, false);
                            return;
                        } else if (i2 != 2) {
                            return;
                        }
                    }
                    BatteryService.m42$$Nest$msetCallWirelessPowerSharingExternelEvent(batteryService2, true);
                    return;
                default:
                    if (message.what != 1) {
                        return;
                    }
                    String str6 = BatteryService.TAG_SS;
                    Slog.d("BatteryService", "SkipActionBatteryChangedHandler : MSG_RECOVER_SEND_ACTION_BATTERY_CHANGED");
                    this.this$0.mSkipActionBatteryChangedHandler.removeCallbacksAndMessages(null);
                    synchronized (this.this$0.mLock) {
                        try {
                            BatteryService batteryService3 = this.this$0;
                            if (batteryService3.mIsSkipActionBatteryChanged) {
                                batteryService3.sendBatteryChangedIntentLocked();
                                this.this$0.mIsSkipActionBatteryChanged = false;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return;
            }
        }
    }

    /* renamed from: com.android.server.BatteryService$15, reason: invalid class name */
    public final class AnonymousClass15 extends ContentObserver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ BatteryService this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass15(BatteryService batteryService, int i) {
            super(new Handler());
            this.$r8$classId = i;
            switch (i) {
                case 2:
                    this.this$0 = batteryService;
                    super(new Handler());
                    break;
                case 3:
                    this.this$0 = batteryService;
                    super(new Handler());
                    break;
                case 4:
                    this.this$0 = batteryService;
                    super(new Handler());
                    break;
                case 5:
                    this.this$0 = batteryService;
                    super(new Handler());
                    break;
                case 6:
                    this.this$0 = batteryService;
                    super(new Handler());
                    break;
                case 7:
                    this.this$0 = batteryService;
                    super(new Handler());
                    break;
                case 8:
                    this.this$0 = batteryService;
                    super(new Handler());
                    break;
                case 9:
                    this.this$0 = batteryService;
                    super(new Handler());
                    break;
                case 10:
                    this.this$0 = batteryService;
                    super(new Handler());
                    break;
                case 11:
                    this.this$0 = batteryService;
                    super(new Handler());
                    break;
                default:
                    this.this$0 = batteryService;
                    break;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass15(BatteryService batteryService, Handler handler) {
            super(handler);
            this.$r8$classId = 0;
            this.this$0 = batteryService;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this.this$0.mLock) {
                        this.this$0.updateBatteryWarningLevelLocked();
                    }
                    return;
                case 1:
                    super.onChange(z);
                    this.this$0.mHandler.post(new AnonymousClass31(2, this));
                    return;
                case 2:
                    super.onChange(z);
                    this.this$0.mHandler.post(new AnonymousClass31(4, this));
                    return;
                case 3:
                    super.onChange(z);
                    synchronized (this.this$0.mLock) {
                        ContentResolver contentResolver = this.this$0.mContext.getContentResolver();
                        boolean z2 = true;
                        this.this$0.mLedChargingSettingsEnable = Settings.System.getIntForUser(contentResolver, "led_indicator_charing", 1, -2) == 1;
                        BatteryService batteryService = this.this$0;
                        if (Settings.System.getIntForUser(contentResolver, "led_indicator_low_battery", 1, -2) != 1) {
                            z2 = false;
                        }
                        batteryService.mLedLowBatterySettingsEnable = z2;
                        String str = BatteryService.TAG_SS;
                        Slog.d("BatteryService", "Led Charging Settings = " + this.this$0.mLedChargingSettingsEnable);
                        Slog.d("BatteryService", "Led Low Battery Settings = " + this.this$0.mLedLowBatterySettingsEnable);
                        this.this$0.mLed.updateLightsLocked();
                    }
                    return;
                case 4:
                    super.onChange(z);
                    this.this$0.mHandler.post(new AnonymousClass31(5, this));
                    return;
                case 5:
                    super.onChange(z);
                    this.this$0.mHandler.post(new AnonymousClass31(6, this));
                    return;
                case 6:
                    super.onChange(z);
                    this.this$0.mHandler.post(new AnonymousClass31(7, this));
                    return;
                case 7:
                    super.onChange(z);
                    this.this$0.mHandler.post(new AnonymousClass31(8, this));
                    return;
                case 8:
                    super.onChange(z);
                    this.this$0.mHandler.post(new AnonymousClass31(9, this));
                    return;
                case 9:
                    super.onChange(z);
                    this.this$0.mHandler.post(new AnonymousClass31(10, this));
                    return;
                case 10:
                    super.onChange(z);
                    this.this$0.mHandler.post(new AnonymousClass31(11, this));
                    return;
                default:
                    super.onChange(z);
                    this.this$0.mHandler.post(new AnonymousClass31(12, this));
                    return;
            }
        }
    }

    /* renamed from: com.android.server.BatteryService$16, reason: invalid class name */
    public final class AnonymousClass16 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ BatteryService this$0;

        public /* synthetic */ AnonymousClass16(BatteryService batteryService, int i) {
            this.$r8$classId = i;
            this.this$0 = batteryService;
        }

        private final void run$com$android$server$BatteryService$36() {
            BatteryService batteryService;
            long j;
            synchronized (this.this$0.mLock) {
                batteryService = this.this$0;
                j = batteryService.mBatteryMaxCurrent;
            }
            synchronized (batteryService.mLockBatteryInfoBackUp) {
                try {
                    BatteryService batteryService2 = this.this$0;
                    if (batteryService2.mSavedBatteryMaxCurrent < 0) {
                        long readNodeAsLong = BattUtils.readNodeAsLong("/efs/FactoryApp/max_current");
                        if (readNodeAsLong < 0) {
                            readNodeAsLong = -1;
                            BattUtils.writeNode(-1L, "/efs/FactoryApp/max_current");
                        }
                        batteryService2.mSavedBatteryMaxCurrent = readNodeAsLong;
                    }
                    BatteryService batteryService3 = this.this$0;
                    if (batteryService3.mSavedBatteryMaxCurrent < j) {
                        batteryService3.mSavedBatteryMaxCurrent = j;
                        BattUtils.writeNode(j, "/efs/FactoryApp/max_current");
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x004a A[Catch: all -> 0x0033, TryCatch #0 {all -> 0x0033, blocks: (B:4:0x0005, B:6:0x0016, B:7:0x001b, B:11:0x0028, B:14:0x004a, B:16:0x005e, B:17:0x0066, B:24:0x0039), top: B:3:0x0005 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private final void run$com$android$server$BatteryService$37() {
            /*
                r7 = this;
                com.android.server.BatteryService r0 = r7.this$0
                java.lang.Object r0 = r0.mLockBatteryInfoBackUp
                monitor-enter(r0)
                com.android.server.BatteryService r1 = r7.this$0     // Catch: java.lang.Throwable -> L33
                java.lang.String r2 = "/efs/FactoryApp/batt_discharge_level"
                r1.getClass()     // Catch: java.lang.Throwable -> L33
                long r3 = com.android.server.battery.BattUtils.readNodeAsLong(r2)     // Catch: java.lang.Throwable -> L33
                r5 = 0
                int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r1 > 0) goto L1b
                r3 = 1
                com.android.server.battery.BattUtils.writeNode(r3, r2)     // Catch: java.lang.Throwable -> L33
            L1b:
                com.android.server.BatteryService r7 = r7.this$0     // Catch: java.lang.Throwable -> L33
                boolean r7 = r7.mLifeExtender     // Catch: java.lang.Throwable -> L33
                r1 = 1000000(0xf4240, double:4.940656E-318)
                if (r7 == 0) goto L35
                int r7 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
                if (r7 >= 0) goto L45
                long r1 = r1 + r3
                java.lang.String r7 = com.android.server.BatteryService.TAG_SS     // Catch: java.lang.Throwable -> L33
                java.lang.String r7 = "BatteryService"
                java.lang.String r5 = "!@ + 10000 cycle"
                com.android.server.power.Slog.d(r7, r5)     // Catch: java.lang.Throwable -> L33
                goto L46
            L33:
                r7 = move-exception
                goto L68
            L35:
                int r7 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
                if (r7 < 0) goto L45
                long r1 = r3 - r1
                java.lang.String r7 = com.android.server.BatteryService.TAG_SS     // Catch: java.lang.Throwable -> L33
                java.lang.String r7 = "BatteryService"
                java.lang.String r5 = "!@ - 10000 cycle"
                com.android.server.power.Slog.d(r7, r5)     // Catch: java.lang.Throwable -> L33
                goto L46
            L45:
                r1 = r3
            L46:
                int r7 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
                if (r7 == 0) goto L66
                java.lang.String r7 = "/efs/FactoryApp/batt_discharge_level"
                com.android.server.battery.BattUtils.writeNode(r1, r7)     // Catch: java.lang.Throwable -> L33
                java.lang.String r7 = "/efs/FactoryApp/batt_discharge_level"
                r3 = 1007(0x3ef, float:1.411E-42)
                r4 = 432(0x1b0, float:6.05E-43)
                r5 = 1000(0x3e8, float:1.401E-42)
                android.os.FileUtils.setPermissions(r7, r4, r5, r3)     // Catch: java.lang.Throwable -> L33
                boolean r7 = com.android.server.battery.BattFeatures.FEATURE_SAVE_BATTERY_CYCLE     // Catch: java.lang.Throwable -> L33
                if (r7 == 0) goto L66
                java.lang.String r7 = "/sys/class/power_supply/battery/battery_cycle"
                r3 = 100
                long r1 = r1 / r3
                com.android.server.battery.BattUtils.writeNode(r1, r7)     // Catch: java.lang.Throwable -> L33
            L66:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L33
                return
            L68:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L33
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.BatteryService.AnonymousClass16.run$com$android$server$BatteryService$37():void");
        }

        /* JADX WARN: Removed duplicated region for block: B:73:0x0220  */
        /* JADX WARN: Removed duplicated region for block: B:84:0x0258  */
        /* JADX WARN: Removed duplicated region for block: B:93:0x01ce  */
        /* JADX WARN: Removed duplicated region for block: B:95:0x020a  */
        /* JADX WARN: Removed duplicated region for block: B:97:0x01d4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 660
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.BatteryService.AnonymousClass16.run():void");
        }
    }

    /* renamed from: com.android.server.BatteryService$17, reason: invalid class name */
    public final class AnonymousClass17 implements Runnable {
        @Override // java.lang.Runnable
        public final void run() {
            IQIManager iQIManager = IQIManager.getInstance();
            if (iQIManager == null || !iQIManager.shouldSubmitMetric(HW12.ID)) {
                return;
            }
            HW12 hw12 = new HW12();
            hw12.setCause((byte) 0);
            hw12.setProcessor((byte) 0);
            iQIManager.submitMetric(hw12);
        }
    }

    /* renamed from: com.android.server.BatteryService$2, reason: invalid class name */
    public final class AnonymousClass2 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ BatteryService this$0;

        public /* synthetic */ AnonymousClass2(BatteryService batteryService, int i) {
            this.$r8$classId = i;
            this.this$0 = batteryService;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            final int i = 2;
            final int i2 = 1;
            final int i3 = 0;
            switch (this.$r8$classId) {
                case 0:
                    String action = intent.getAction();
                    Slog.i(BatteryService.TAG_SS, "[SleepChargingDismissReceiver_onReceive]action:" + action);
                    final SleepChargingManager sleepChargingManager = this.this$0.mSleepChargingManager;
                    if (sleepChargingManager != null) {
                        android.util.Slog.i("[SS]SleepChargingManager", "[updateDismiss]");
                        sleepChargingManager.mHandler.post(new Runnable() { // from class: com.android.server.battery.sleepcharging.SleepChargingManager.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                sendEmptyMessage(12);
                            }
                        });
                        break;
                    }
                    break;
                case 1:
                    if (intent.getAction().equals("android.samsung.media.action.AUDIO_MODE")) {
                        int intExtra = intent.getIntExtra("android.samsung.media.extra.AUDIO_MODE", 0);
                        String str = BatteryService.TAG_SS;
                        Slog.d("BatteryService", "audio_mode : " + intExtra);
                        this.this$0.mHandler.post(new AnonymousClass34(this, intExtra));
                        break;
                    }
                    break;
                case 2:
                    if (intent.getAction().equals("com.samsung.android.sm.ACTION_FAST_WIRELESS_CHARGING_CONTROL")) {
                        this.this$0.mHandler.post(new BatteryService$6$1(this, intent.getBooleanExtra("write", false)));
                        break;
                    }
                    break;
                case 3:
                    String action2 = intent.getAction();
                    String str2 = BatteryService.TAG_SS;
                    Slog.i(str2, "[mBootCompletedReceiver]action:" + action2);
                    if ("android.intent.action.BOOT_COMPLETED".equals(action2)) {
                        BatteryService batteryService = this.this$0;
                        batteryService.mIsUnlockedBootCompleted = true;
                        if (batteryService.mProtectBatteryMode == 4) {
                            batteryService.mHandler.post(new AnonymousClass31(i2, this));
                        } else {
                            Slog.e(str2, "Currently, Not Battery Adaptive Protect Mode");
                        }
                        this.this$0.mHandler.postDelayed(new BatteryService$$ExternalSyntheticLambda6(2, this), 20000L);
                        break;
                    }
                    break;
                case 4:
                    String action3 = intent.getAction();
                    if (!action3.equals("android.intent.action.SCREEN_ON")) {
                        if (!action3.equals("android.intent.action.SCREEN_OFF")) {
                            if (!action3.equals("android.intent.action.USER_SWITCHED")) {
                                if (action3.equals("android.intent.action.ACTION_SHUTDOWN")) {
                                    Slog.i("[SS][BattInfo]", "shutdown event");
                                    this.this$0.mBattInfoManager.processDischargingLevel(r8.mLastBatteryLevel, r8.mHealthInfo.batteryLevel, true);
                                    BattInfoManager battInfoManager = this.this$0.mBattInfoManager;
                                    if (battInfoManager.mSupportsFullStatusUsage) {
                                        battInfoManager.processFullStatusUsage(r8.mHealthInfo.batteryLevel, true);
                                    }
                                    Context context2 = this.this$0.mContext;
                                    long currentNetworkTimeMillis = BattUtils.getCurrentNetworkTimeMillis();
                                    android.util.Slog.d("[SS]BattFunctions", "[saveShutdownTimeForLongestPowerOffDuration]shutdownTime:" + currentNetworkTimeMillis);
                                    if (currentNetworkTimeMillis < 1577804400000L) {
                                        android.util.Slog.e("[SS]BattFunctions", "[saveShutdownTimeForLongestPowerOffDuration]wrong shutdownTime");
                                    } else {
                                        BattUtils.saveSharedPreferencesAsLong(context2, currentNetworkTimeMillis);
                                    }
                                    if (this.this$0.mEnableIqi) {
                                        SystemProperties.set("persist.sys.shutdown_received", "true");
                                        break;
                                    }
                                }
                            } else {
                                this.this$0.mHandler.post(new Runnable(this) { // from class: com.android.server.BatteryService$4$1
                                    public final /* synthetic */ BatteryService.AnonymousClass2 this$1;

                                    {
                                        this.this$1 = this;
                                    }

                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        switch (i) {
                                            case 0:
                                                synchronized (this.this$1.this$0.mLock) {
                                                    this.this$1.this$0.mScreenOn = true;
                                                    if (PowerManagerUtil.SEC_FEATURE_BATTERY_NOTIFY_SCREEN_STATE) {
                                                        BattUtils.writeNode(1L, "/sys/class/power_supply/battery/lcd");
                                                    }
                                                    this.this$1.this$0.mLed.updateLightsLocked();
                                                }
                                                return;
                                            case 1:
                                                synchronized (this.this$1.this$0.mLock) {
                                                    this.this$1.this$0.mScreenOn = false;
                                                    if (PowerManagerUtil.SEC_FEATURE_BATTERY_NOTIFY_SCREEN_STATE) {
                                                        BattUtils.writeNode(0L, "/sys/class/power_supply/battery/lcd");
                                                    }
                                                    this.this$1.this$0.mLed.updateLightsLocked();
                                                }
                                                return;
                                            default:
                                                ContentResolver contentResolver = this.this$1.this$0.mContext.getContentResolver();
                                                synchronized (this.this$1.this$0.mLock) {
                                                    this.this$1.this$0.mLedChargingSettingsEnable = Settings.System.getIntForUser(contentResolver, "led_indicator_charing", 1, -2) == 1;
                                                    this.this$1.this$0.mLedLowBatterySettingsEnable = Settings.System.getIntForUser(contentResolver, "led_indicator_low_battery", 1, -2) == 1;
                                                    this.this$1.this$0.mWasUsedWirelessFastChargerPreviously = Settings.System.getIntForUser(contentResolver, "show_wireless_charger_menu", 0, -2) == 1;
                                                    String str3 = BatteryService.TAG_SS;
                                                    Slog.d("BatteryService", "ACTION_USER_SWITCHED: Led Charging: " + this.this$1.this$0.mLedChargingSettingsEnable + " Led Low Battery:" + this.this$1.this$0.mLedLowBatterySettingsEnable + " wfc: " + this.this$1.this$0.mWasUsedWirelessFastChargerPreviously);
                                                }
                                                synchronized (this.this$1.this$0.mLock) {
                                                    try {
                                                        this.this$1.this$0.mLed.updateLightsLocked();
                                                        if (PowerManagerUtil.SEC_FEATURE_USE_AFC) {
                                                            this.this$1.this$0.updateAdaptiveFastChargingSetting(contentResolver);
                                                        }
                                                    } finally {
                                                    }
                                                }
                                                return;
                                        }
                                    }
                                });
                                break;
                            }
                        } else {
                            this.this$0.mHandler.post(new Runnable(this) { // from class: com.android.server.BatteryService$4$1
                                public final /* synthetic */ BatteryService.AnonymousClass2 this$1;

                                {
                                    this.this$1 = this;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i2) {
                                        case 0:
                                            synchronized (this.this$1.this$0.mLock) {
                                                this.this$1.this$0.mScreenOn = true;
                                                if (PowerManagerUtil.SEC_FEATURE_BATTERY_NOTIFY_SCREEN_STATE) {
                                                    BattUtils.writeNode(1L, "/sys/class/power_supply/battery/lcd");
                                                }
                                                this.this$1.this$0.mLed.updateLightsLocked();
                                            }
                                            return;
                                        case 1:
                                            synchronized (this.this$1.this$0.mLock) {
                                                this.this$1.this$0.mScreenOn = false;
                                                if (PowerManagerUtil.SEC_FEATURE_BATTERY_NOTIFY_SCREEN_STATE) {
                                                    BattUtils.writeNode(0L, "/sys/class/power_supply/battery/lcd");
                                                }
                                                this.this$1.this$0.mLed.updateLightsLocked();
                                            }
                                            return;
                                        default:
                                            ContentResolver contentResolver = this.this$1.this$0.mContext.getContentResolver();
                                            synchronized (this.this$1.this$0.mLock) {
                                                this.this$1.this$0.mLedChargingSettingsEnable = Settings.System.getIntForUser(contentResolver, "led_indicator_charing", 1, -2) == 1;
                                                this.this$1.this$0.mLedLowBatterySettingsEnable = Settings.System.getIntForUser(contentResolver, "led_indicator_low_battery", 1, -2) == 1;
                                                this.this$1.this$0.mWasUsedWirelessFastChargerPreviously = Settings.System.getIntForUser(contentResolver, "show_wireless_charger_menu", 0, -2) == 1;
                                                String str3 = BatteryService.TAG_SS;
                                                Slog.d("BatteryService", "ACTION_USER_SWITCHED: Led Charging: " + this.this$1.this$0.mLedChargingSettingsEnable + " Led Low Battery:" + this.this$1.this$0.mLedLowBatterySettingsEnable + " wfc: " + this.this$1.this$0.mWasUsedWirelessFastChargerPreviously);
                                            }
                                            synchronized (this.this$1.this$0.mLock) {
                                                try {
                                                    this.this$1.this$0.mLed.updateLightsLocked();
                                                    if (PowerManagerUtil.SEC_FEATURE_USE_AFC) {
                                                        this.this$1.this$0.updateAdaptiveFastChargingSetting(contentResolver);
                                                    }
                                                } finally {
                                                }
                                            }
                                            return;
                                    }
                                }
                            });
                            break;
                        }
                    } else {
                        this.this$0.mHandler.post(new Runnable(this) { // from class: com.android.server.BatteryService$4$1
                            public final /* synthetic */ BatteryService.AnonymousClass2 this$1;

                            {
                                this.this$1 = this;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (i3) {
                                    case 0:
                                        synchronized (this.this$1.this$0.mLock) {
                                            this.this$1.this$0.mScreenOn = true;
                                            if (PowerManagerUtil.SEC_FEATURE_BATTERY_NOTIFY_SCREEN_STATE) {
                                                BattUtils.writeNode(1L, "/sys/class/power_supply/battery/lcd");
                                            }
                                            this.this$1.this$0.mLed.updateLightsLocked();
                                        }
                                        return;
                                    case 1:
                                        synchronized (this.this$1.this$0.mLock) {
                                            this.this$1.this$0.mScreenOn = false;
                                            if (PowerManagerUtil.SEC_FEATURE_BATTERY_NOTIFY_SCREEN_STATE) {
                                                BattUtils.writeNode(0L, "/sys/class/power_supply/battery/lcd");
                                            }
                                            this.this$1.this$0.mLed.updateLightsLocked();
                                        }
                                        return;
                                    default:
                                        ContentResolver contentResolver = this.this$1.this$0.mContext.getContentResolver();
                                        synchronized (this.this$1.this$0.mLock) {
                                            this.this$1.this$0.mLedChargingSettingsEnable = Settings.System.getIntForUser(contentResolver, "led_indicator_charing", 1, -2) == 1;
                                            this.this$1.this$0.mLedLowBatterySettingsEnable = Settings.System.getIntForUser(contentResolver, "led_indicator_low_battery", 1, -2) == 1;
                                            this.this$1.this$0.mWasUsedWirelessFastChargerPreviously = Settings.System.getIntForUser(contentResolver, "show_wireless_charger_menu", 0, -2) == 1;
                                            String str3 = BatteryService.TAG_SS;
                                            Slog.d("BatteryService", "ACTION_USER_SWITCHED: Led Charging: " + this.this$1.this$0.mLedChargingSettingsEnable + " Led Low Battery:" + this.this$1.this$0.mLedLowBatterySettingsEnable + " wfc: " + this.this$1.this$0.mWasUsedWirelessFastChargerPreviously);
                                        }
                                        synchronized (this.this$1.this$0.mLock) {
                                            try {
                                                this.this$1.this$0.mLed.updateLightsLocked();
                                                if (PowerManagerUtil.SEC_FEATURE_USE_AFC) {
                                                    this.this$1.this$0.updateAdaptiveFastChargingSetting(contentResolver);
                                                }
                                            } finally {
                                            }
                                        }
                                        return;
                                }
                            }
                        });
                        break;
                    }
                    break;
                case 5:
                    if (intent.getAction().equals("android.intent.action.REQUEST_OTG_CHARGE_BLOCK")) {
                        this.this$0.mHandler.post(new BatteryService$6$1(this, intent.getBooleanExtra("OTG_CHARGE_BLOCK", false), (byte) 0));
                        break;
                    }
                    break;
                case 6:
                    String action4 = intent.getAction();
                    if (!action4.equals("com.samsung.systemui.power.action.WATER_POPUP_DISMISSED")) {
                        if (action4.equals("com.samsung.systemui.power.action.USB_DAMAGE_POPUP_SHOW")) {
                            this.this$0.mHandler.post(new Runnable(this) { // from class: com.android.server.BatteryService$7$1
                                public final /* synthetic */ BatteryService.AnonymousClass2 this$1;

                                {
                                    this.this$1 = this;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i2) {
                                        case 0:
                                            this.this$1.this$0.getClass();
                                            if (BattUtils.writeNode("/sys/class/sec/switch/hiccup", "DISABLE")) {
                                                String str3 = BatteryService.TAG_SS;
                                                Slog.d("BatteryService", "success to disable hiccup");
                                            } else {
                                                String str4 = BatteryService.TAG_SS;
                                                Slog.d("BatteryService", "fail to disable hiccup");
                                            }
                                            this.this$1.this$0.mIsHiccupPopupShowing = false;
                                            break;
                                        default:
                                            String str5 = BatteryService.TAG_SS;
                                            Slog.d("BatteryService", "notify the misc event");
                                            this.this$1.this$0.getClass();
                                            BattUtils.writeNode(8192L, "/sys/class/power_supply/battery/batt_misc_event");
                                            this.this$1.this$0.mIsHiccupPopupShowing = true;
                                            break;
                                    }
                                }
                            });
                            break;
                        }
                    } else {
                        this.this$0.mHandler.post(new Runnable(this) { // from class: com.android.server.BatteryService$7$1
                            public final /* synthetic */ BatteryService.AnonymousClass2 this$1;

                            {
                                this.this$1 = this;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (i3) {
                                    case 0:
                                        this.this$1.this$0.getClass();
                                        if (BattUtils.writeNode("/sys/class/sec/switch/hiccup", "DISABLE")) {
                                            String str3 = BatteryService.TAG_SS;
                                            Slog.d("BatteryService", "success to disable hiccup");
                                        } else {
                                            String str4 = BatteryService.TAG_SS;
                                            Slog.d("BatteryService", "fail to disable hiccup");
                                        }
                                        this.this$1.this$0.mIsHiccupPopupShowing = false;
                                        break;
                                    default:
                                        String str5 = BatteryService.TAG_SS;
                                        Slog.d("BatteryService", "notify the misc event");
                                        this.this$1.this$0.getClass();
                                        BattUtils.writeNode(8192L, "/sys/class/power_supply/battery/batt_misc_event");
                                        this.this$1.this$0.mIsHiccupPopupShowing = true;
                                        break;
                                }
                            }
                        });
                        break;
                    }
                    break;
                case 7:
                    if (intent.getAction().equals("com.samsung.android.sm.ACTION_WIRELESS_POWER_SHARING")) {
                        boolean booleanExtra = intent.getBooleanExtra("enable", false);
                        BatteryService batteryService2 = this.this$0;
                        if (batteryService2.mLastTxEventTxEnabled != booleanExtra) {
                            batteryService2.mHandler.post(new BatteryService$6$1(this, booleanExtra, (char) 0));
                            break;
                        }
                    }
                    break;
                default:
                    String action5 = intent.getAction();
                    if (!action5.equals(BatteryService.ACTION_ENTER_DESK_MODE)) {
                        if (action5.equals(BatteryService.ACTION_EXIT_DESK_MODE)) {
                            this.this$0.mHandler.post(new Runnable(this) { // from class: com.android.server.BatteryService$9$1
                                public final /* synthetic */ BatteryService.AnonymousClass2 this$1;

                                {
                                    this.this$1 = this;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i2) {
                                        case 0:
                                            String str3 = BatteryService.TAG_SS;
                                            Slog.d("BatteryService", "Dex Start");
                                            this.this$1.this$0.setWirelessPowerSharingExternelEventInternal(2, 2);
                                            break;
                                        default:
                                            String str4 = BatteryService.TAG_SS;
                                            Slog.d("BatteryService", "Dex Exit");
                                            this.this$1.this$0.setWirelessPowerSharingExternelEventInternal(2, 0);
                                            break;
                                    }
                                }
                            });
                            break;
                        }
                    } else {
                        this.this$0.mHandler.post(new Runnable(this) { // from class: com.android.server.BatteryService$9$1
                            public final /* synthetic */ BatteryService.AnonymousClass2 this$1;

                            {
                                this.this$1 = this;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (i3) {
                                    case 0:
                                        String str3 = BatteryService.TAG_SS;
                                        Slog.d("BatteryService", "Dex Start");
                                        this.this$1.this$0.setWirelessPowerSharingExternelEventInternal(2, 2);
                                        break;
                                    default:
                                        String str4 = BatteryService.TAG_SS;
                                        Slog.d("BatteryService", "Dex Exit");
                                        this.this$1.this$0.setWirelessPowerSharingExternelEventInternal(2, 0);
                                        break;
                                }
                            }
                        });
                        break;
                    }
                    break;
            }
        }
    }

    /* renamed from: com.android.server.BatteryService$22, reason: invalid class name */
    public final class AnonymousClass22 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ BatteryService this$0;
        public final /* synthetic */ Intent val$statusIntent;

        public /* synthetic */ AnonymousClass22(BatteryService batteryService, Intent intent, int i) {
            this.$r8$classId = i;
            this.this$0 = batteryService;
            this.val$statusIntent = intent;
        }

        @Override // java.lang.Runnable
        public final void run() {
            String str = null;
            switch (this.$r8$classId) {
                case 0:
                    BatteryService batteryService = this.this$0;
                    batteryService.mContext.sendBroadcastAsUser(this.val$statusIntent, UserHandle.ALL, null, batteryService.mPowerOptions);
                    BattLogBuffer.addLog(1, this.val$statusIntent.getAction());
                    if (BatteryService.FEATURE_SUPPORTED_DAILY_BOARD) {
                        Intent intent = (Intent) this.val$statusIntent.clone();
                        intent.addFlags(32);
                        BatteryService.m44$$Nest$smsendBroadcastToExplicitPackage(this.this$0.mContext, intent, "com.samsung.android.homemode");
                    }
                    String str2 = SystemProperties.get("ro.csc.sales_code");
                    if ("VZW".equals(str2) || "VPP".equals(str2)) {
                        try {
                            if ((this.this$0.mContext.getPackageManager().getPackageInfo("com.verizon.mips.services", PackageManager.PackageInfoFlags.of(0L)).applicationInfo.flags & 129) != 0) {
                                BatteryService.m44$$Nest$smsendBroadcastToExplicitPackage(this.this$0.mContext, this.val$statusIntent, "com.verizon.mips.services");
                            }
                        } catch (Exception e) {
                            String str3 = BatteryService.TAG_SS;
                            Slog.v("BatteryService", "getMVSPackageName exception : " + e);
                        }
                    }
                    BatteryService batteryService2 = this.this$0;
                    batteryService2.getClass();
                    String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SECURITY_CONFIG_DEVICEMONITOR_PACKAGE_NAME", "com.samsung.android.sm.devicesecurity");
                    try {
                        batteryService2.mContext.getPackageManager().getPackageInfo(string, 128);
                        str = string;
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                    if (str != null) {
                        BatteryService.m44$$Nest$smsendBroadcastToExplicitPackage(this.this$0.mContext, this.val$statusIntent, str);
                        break;
                    }
                    break;
                case 1:
                    BatteryService batteryService3 = this.this$0;
                    batteryService3.mContext.sendBroadcastAsUser(this.val$statusIntent, UserHandle.ALL, null, batteryService3.mPowerOptions);
                    BattLogBuffer.addLog(1, this.val$statusIntent.getAction());
                    if (BatteryService.FEATURE_SUPPORTED_DAILY_BOARD) {
                        Intent intent2 = (Intent) this.val$statusIntent.clone();
                        intent2.addFlags(32);
                        BatteryService.m44$$Nest$smsendBroadcastToExplicitPackage(this.this$0.mContext, intent2, "com.samsung.android.homemode");
                    }
                    String str4 = SystemProperties.get("ro.csc.sales_code");
                    if ("VZW".equals(str4) || "VPP".equals(str4)) {
                        try {
                            if ((this.this$0.mContext.getPackageManager().getPackageInfo("com.verizon.mips.services", PackageManager.PackageInfoFlags.of(0L)).applicationInfo.flags & 129) != 0) {
                                BatteryService.m44$$Nest$smsendBroadcastToExplicitPackage(this.this$0.mContext, this.val$statusIntent, "com.verizon.mips.services");
                            }
                        } catch (Exception e2) {
                            String str5 = BatteryService.TAG_SS;
                            Slog.v("BatteryService", "getMVSPackageName exception : " + e2);
                        }
                    }
                    BatteryService batteryService4 = this.this$0;
                    batteryService4.getClass();
                    String string2 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SECURITY_CONFIG_DEVICEMONITOR_PACKAGE_NAME", "com.samsung.android.sm.devicesecurity");
                    try {
                        batteryService4.mContext.getPackageManager().getPackageInfo(string2, 128);
                        str = string2;
                    } catch (PackageManager.NameNotFoundException unused2) {
                    }
                    if (str != null) {
                        BatteryService.m44$$Nest$smsendBroadcastToExplicitPackage(this.this$0.mContext, this.val$statusIntent, str);
                        break;
                    }
                    break;
                case 2:
                    BatteryService batteryService5 = this.this$0;
                    batteryService5.mContext.sendBroadcastAsUser(this.val$statusIntent, UserHandle.ALL, null, batteryService5.mBatteryOptions);
                    BattLogBuffer.addLog(1, this.val$statusIntent.getAction());
                    break;
                case 3:
                    BatteryService batteryService6 = this.this$0;
                    batteryService6.mContext.sendBroadcastAsUser(this.val$statusIntent, UserHandle.ALL, null, batteryService6.mBatteryOptions);
                    BattLogBuffer.addLog(1, this.val$statusIntent.getAction());
                    break;
                case 4:
                    String str6 = "Sending ACTION_DOCK_EVENT. dock_state:" + this.this$0.mPogoDockState + ", pogo_plugged:" + this.this$0.mPogoCondition;
                    String str7 = BatteryService.TAG_SS;
                    Slog.d("BatteryService", str6);
                    ActivityManager.broadcastStickyIntent(this.val$statusIntent, -1);
                    BattLogBuffer.addLog(1, str6);
                    break;
                default:
                    String str8 = BatteryService.TAG_SS;
                    StringBuilder sb = new StringBuilder("Sending ACTION_SEC_BATTERY_EVENT: misc_event:0x");
                    BatteryService$$ExternalSyntheticOutline0.m(this.this$0.mSehHealthInfo.batteryEvent, sb, ", sec_plug_type:");
                    sb.append(this.this$0.mSecPlugTypeSummary);
                    sb.append(", online:");
                    sb.append(this.this$0.mSehHealthInfo.batteryOnline);
                    sb.append(", charge_type:");
                    sb.append(this.this$0.mSehHealthInfo.batteryChargeType);
                    sb.append(", ps:");
                    sb.append(this.this$0.mSehHealthInfo.batteryPowerSharingOnline);
                    sb.append(", hvc:");
                    sb.append(this.this$0.mSehHealthInfo.batteryHighVoltageCharger != 0);
                    sb.append(", charger_type:");
                    sb.append(this.this$0.mSehHealthInfo.batteryHighVoltageCharger);
                    sb.append(", pogo_plugged:");
                    sb.append(this.this$0.mPogoCondition);
                    sb.append(", current_event:0x");
                    BatteryService$$ExternalSyntheticOutline0.m(this.this$0.mSehHealthInfo.batteryCurrentEvent, sb, ", wc_tx_id:");
                    BatteryService$$ExternalSyntheticOutline0.m(sb, this.this$0.mWcTxId, "BatteryService");
                    ActivityManager.broadcastStickyIntent(this.val$statusIntent, -1);
                    break;
            }
        }
    }

    /* renamed from: com.android.server.BatteryService$27, reason: invalid class name */
    public final class AnonymousClass27 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ boolean val$batteryWaterInConnector;
        public final /* synthetic */ Intent val$intent;

        public /* synthetic */ AnonymousClass27(int i, Intent intent, boolean z) {
            this.$r8$classId = i;
            this.val$batteryWaterInConnector = z;
            this.val$intent = intent;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    String str = "Sending ACTION_SEC_BATTERY_WATER_IN_CONNECTOR. water : " + this.val$batteryWaterInConnector;
                    String str2 = BatteryService.TAG_SS;
                    Slog.d("BatteryService", str);
                    ActivityManager.broadcastStickyIntent(this.val$intent, -1);
                    BattLogBuffer.addLog(1, str);
                    break;
                default:
                    String str3 = "Sending ACTION_WIRELESS_POWER_SHARING_ENABLED. enabled : " + this.val$batteryWaterInConnector;
                    String str4 = BatteryService.TAG_SS;
                    Slog.d("BatteryService", str3);
                    ActivityManager.broadcastStickyIntent(this.val$intent, -1);
                    BattLogBuffer.addLog(1, str3);
                    break;
            }
        }
    }

    /* renamed from: com.android.server.BatteryService$31, reason: invalid class name */
    public final class AnonymousClass31 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object val$intent;

        public /* synthetic */ AnonymousClass31(int i, Object obj) {
            this.$r8$classId = i;
            this.val$intent = obj;
        }

        private final void run$com$android$server$BatteryService$MaximumProtectionThresholdObserver$1() {
            synchronized (((AnonymousClass15) this.val$intent).this$0.mLock) {
                ContentResolver contentResolver = ((AnonymousClass15) this.val$intent).this$0.mContext.getContentResolver();
                BatteryService batteryService = ((AnonymousClass15) this.val$intent).this$0;
                int i = batteryService.mMaximumProtectionThreshold;
                batteryService.mMaximumProtectionThreshold = Settings.Global.getInt(contentResolver, "battery_protection_threshold", Settings.Global.BATTERY_PROTECTION_THRESHOLD_DEFAULT_VALUE);
                String str = i + " => " + ((AnonymousClass15) this.val$intent).this$0.mMaximumProtectionThreshold;
                String str2 = BatteryService.TAG_SS;
                Slog.i(str2, "MaximumProtectionThreshold Changed: " + str);
                BattLogBuffer.addLog(1, str);
                BatteryService batteryService2 = ((AnonymousClass15) this.val$intent).this$0;
                if (batteryService2.mProtectBatteryMode == 1) {
                    batteryService2.writeProtectBatteryValues();
                } else {
                    String str3 = "fail -protectionThreshold cannot be delivered when not MaximumMode:" + ((AnonymousClass15) this.val$intent).this$0.mProtectBatteryMode;
                    Slog.e(str2, str3);
                    BattLogBuffer.addLog(1, str3);
                }
            }
        }

        private final void run$com$android$server$BatteryService$PassThroughSettingsObserver$1() {
            synchronized (((AnonymousClass15) this.val$intent).this$0.mLock) {
                ((AnonymousClass15) this.val$intent).this$0.mPassThroughSettingsEnable = Settings.System.getIntForUser(((AnonymousClass15) this.val$intent).this$0.mContext.getContentResolver(), "pass_through", 0, -2) == 1;
                String str = BatteryService.TAG_SS;
                Slog.d("BatteryService", "PassThrough Settings = " + ((AnonymousClass15) this.val$intent).this$0.mPassThroughSettingsEnable);
                BatteryService.setPassThrough(((AnonymousClass15) this.val$intent).this$0.mPassThroughSettingsEnable);
            }
        }

        private final void run$com$android$server$BatteryService$RefreshRateModeSettingsObserver$1() {
            synchronized (((AnonymousClass15) this.val$intent).this$0.mLock) {
                ((AnonymousClass15) this.val$intent).this$0.mRefreshRateModeSetting = Settings.Secure.getIntForUser(((AnonymousClass15) this.val$intent).this$0.mContext.getContentResolver(), "refresh_rate_mode", 0, -2);
                String str = BatteryService.TAG_SS;
                Slog.d("BatteryService", "RefreshRateMode Setting = " + ((AnonymousClass15) this.val$intent).this$0.mRefreshRateModeSetting);
                if (((AnonymousClass15) this.val$intent).this$0.mRefreshRateModeSetting == 0) {
                    BattUtils.writeNode(5L, "/sys/class/power_supply/battery/batt_hv_wireless_pad_ctrl");
                } else {
                    BattUtils.writeNode(6L, "/sys/class/power_supply/battery/batt_hv_wireless_pad_ctrl");
                }
            }
        }

        private final void run$com$android$server$BatteryService$SuperFastChargingSettingsObserver$1() {
            synchronized (((AnonymousClass15) this.val$intent).this$0.mLock) {
                ContentResolver contentResolver = ((AnonymousClass15) this.val$intent).this$0.mContext.getContentResolver();
                BatteryService batteryService = ((AnonymousClass15) this.val$intent).this$0;
                boolean z = true;
                if (Settings.System.getIntForUser(contentResolver, "super_fast_charging", 1, -2) != 1) {
                    z = false;
                }
                batteryService.mSuperFastChargingSettingsEnable = z;
                String str = BatteryService.TAG_SS;
                Slog.d("BatteryService", "SuperFastCharging Settings = " + ((AnonymousClass15) this.val$intent).this$0.mSuperFastChargingSettingsEnable);
                BatteryService batteryService2 = ((AnonymousClass15) this.val$intent).this$0;
                batteryService2.setSuperFastCharging(batteryService2.mSuperFastChargingSettingsEnable);
            }
        }

        private final void run$com$android$server$BatteryService$TxBatteryLimitSettingsObserver$1() {
            synchronized (((AnonymousClass15) this.val$intent).this$0.mLock) {
                ((AnonymousClass15) this.val$intent).this$0.mTxBatteryLimit = Settings.System.getIntForUser(((AnonymousClass15) this.val$intent).this$0.mContext.getContentResolver(), "tx_battery_limit", 30, -2);
                String str = BatteryService.TAG_SS;
                Slog.d("BatteryService", "Tx Battery Limit Settings = " + ((AnonymousClass15) this.val$intent).this$0.mTxBatteryLimit);
                BattUtils.writeNode((long) ((AnonymousClass15) this.val$intent).this$0.mTxBatteryLimit, "/sys/class/power_supply/battery/wc_tx_stop_capacity");
            }
        }

        private final void run$com$android$server$BatteryService$WcParamInfoSettingsObserver$1() {
            synchronized (((AnonymousClass15) this.val$intent).this$0.mLock) {
                try {
                    String readNode = BattUtils.readNode("/sys/class/power_supply/battery/wc_param_info", true);
                    int intForUser = Settings.System.getIntForUser(((AnonymousClass15) this.val$intent).this$0.mContext.getContentResolver(), "wireless_wc_write", 0, -2);
                    if (((AnonymousClass15) this.val$intent).this$0.mWcParamOffset != -1 && !readNode.isEmpty() && intForUser == 1) {
                        String str = BatteryService.TAG_SS;
                        Slog.d("BatteryService", "wireless_wc_write onchanged");
                        BatteryService batteryService = ((AnonymousClass15) this.val$intent).this$0;
                        batteryService.mHealthServiceWrapper.sehWriteEnableToParam(-2, "wc param", true);
                        Settings.System.putInt(batteryService.mContext.getContentResolver(), "wireless_wc_write", 0);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // java.lang.Runnable
        public final void run() {
            final int i = 1;
            switch (this.$r8$classId) {
                case 0:
                    String str = BatteryService.TAG_SS;
                    Slog.d("BatteryService", "Sending RESPONSE_OTG_CHARGE_BLOCK.");
                    ActivityManager.broadcastStickyIntent((Intent) this.val$intent, -1);
                    BattLogBuffer.addLog(1, ((Intent) this.val$intent).getAction());
                    return;
                case 1:
                    BatteryService.m40$$Nest$mactivateSleepChargingManager(((AnonymousClass2) this.val$intent).this$0, true);
                    return;
                case 2:
                    synchronized (((AnonymousClass15) this.val$intent).this$0.mLock) {
                        ((AnonymousClass15) this.val$intent).this$0.mAdaptiveFastChargingSettingsEnable = Settings.System.getIntForUser(((AnonymousClass15) this.val$intent).this$0.mContext.getContentResolver(), "adaptive_fast_charging", 1, -2) == 1;
                        String str2 = BatteryService.TAG_SS;
                        Slog.d("BatteryService", "AdaptiveFastCharging Settings = " + ((AnonymousClass15) this.val$intent).this$0.mAdaptiveFastChargingSettingsEnable);
                        BatteryService batteryService = ((AnonymousClass15) this.val$intent).this$0;
                        batteryService.setAdaptiveFastCharging(batteryService.mAdaptiveFastChargingSettingsEnable);
                    }
                    return;
                case 3:
                    long readNodeAsLong = BattUtils.readNodeAsLong("/efs/FactoryApp/longest_power_off_duration");
                    BattCallbackImpl battCallbackImpl = BattHqmManager.mBattCallback;
                    BattHqmManager.updateMap("longest_power_off_duration", String.valueOf(readNodeAsLong));
                    BattHqmManager.updateMap("batt_first_use_date", BattUtils.readNode("/efs/FactoryApp/batt_beginning_date", true));
                    String str3 = BatteryService.TAG_SS;
                    Slog.d("BatteryService", "[onHqmEventOccured]IS_SHIP_BUILD:true");
                    Context context = BatteryService.this.mContext;
                    try {
                        String replace = new JSONObject(BattHqmManager.mMapForBSHL).toString().replace("{", "").replace("}", "");
                        Slog.i("[SS]BattHqmManager", "[uploadToHqm]customDataset:" + replace);
                        SemHqmManager semHqmManager = (SemHqmManager) context.getSystemService("HqmManagerService");
                        if (semHqmManager == null) {
                            Slog.e("[SS]BattHqmManager", "[uploadToHqm]fail - semHqmManager null");
                        } else {
                            semHqmManager.sendHWParamToHQM(0, "Battery", "BSHL", "ph", "0.0", "sec", "", replace, "");
                        }
                        return;
                    } catch (Exception e) {
                        Slog.e("[SS]BattHqmManager", "[uploadToHqm]Exception");
                        e.printStackTrace();
                        return;
                    }
                case 4:
                    synchronized (((AnonymousClass15) this.val$intent).this$0.mLock) {
                        try {
                            ContentResolver contentResolver = ((AnonymousClass15) this.val$intent).this$0.mContext.getContentResolver();
                            if (((AnonymousClass15) this.val$intent).this$0.mLifeExtender) {
                                String str4 = BatteryService.TAG_SS;
                                Slog.d("BatteryService", "!@battery life extender used before!");
                                BatteryService batteryService2 = ((AnonymousClass15) this.val$intent).this$0;
                                batteryService2.mLifeExtender = false;
                                batteryService2.mHandlerForBatteryInfoBackUp.post(batteryService2.mUpdateBatteryUsageExtenderRunnable);
                                Settings.System.putIntForUser(contentResolver, "protect_battery", 0, -2);
                            }
                            if (BattFeatures.SUPPORT_ECO_BATTERY) {
                                BatteryService batteryService3 = ((AnonymousClass15) this.val$intent).this$0;
                                int i2 = batteryService3.mProtectBatteryMode;
                                batteryService3.mProtectBatteryMode = Settings.Global.getInt(contentResolver, "protect_battery", 0);
                                String str5 = i2 + " => " + ((AnonymousClass15) this.val$intent).this$0.mProtectBatteryMode;
                                String str6 = BatteryService.TAG_SS;
                                Slog.i(str6, "Battery Protect Mode Changed: " + str5);
                                BatteryLogger.writeToFile("/data/log/battery_service/sleep_charging_history", "Battery Protect Mode Changed", str5);
                                ((AnonymousClass15) this.val$intent).this$0.writeProtectBatteryValues();
                                BatteryService batteryService4 = ((AnonymousClass15) this.val$intent).this$0;
                                if (!batteryService4.mIsUnlockedBootCompleted) {
                                    Slog.w(str6, "Battery Protect Mode Changed before UnlockedBootCompleted => ignored");
                                } else if (batteryService4.mProtectBatteryMode == 4) {
                                    Handler handler = batteryService4.mHandler;
                                    final int i3 = r1 ? 1 : 0;
                                    handler.post(new Runnable(this) { // from class: com.android.server.BatteryService$FullCapacityEnableSettingsObserver$1$1
                                        public final /* synthetic */ BatteryService.AnonymousClass31 this$2;

                                        {
                                            this.this$2 = this;
                                        }

                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            switch (i3) {
                                                case 0:
                                                    BatteryService.m40$$Nest$mactivateSleepChargingManager(((BatteryService.AnonymousClass15) this.this$2.val$intent).this$0, true);
                                                    break;
                                                default:
                                                    BatteryService.m40$$Nest$mactivateSleepChargingManager(((BatteryService.AnonymousClass15) this.this$2.val$intent).this$0, false);
                                                    break;
                                            }
                                        }
                                    });
                                } else {
                                    batteryService4.mHandler.post(new Runnable(this) { // from class: com.android.server.BatteryService$FullCapacityEnableSettingsObserver$1$1
                                        public final /* synthetic */ BatteryService.AnonymousClass31 this$2;

                                        {
                                            this.this$2 = this;
                                        }

                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            switch (i) {
                                                case 0:
                                                    BatteryService.m40$$Nest$mactivateSleepChargingManager(((BatteryService.AnonymousClass15) this.this$2.val$intent).this$0, true);
                                                    break;
                                                default:
                                                    BatteryService.m40$$Nest$mactivateSleepChargingManager(((BatteryService.AnonymousClass15) this.this$2.val$intent).this$0, false);
                                                    break;
                                            }
                                        }
                                    });
                                }
                            } else {
                                ((AnonymousClass15) this.val$intent).this$0.mFullCapacityEnable = Settings.Global.getInt(contentResolver, "protect_battery", 0) == 1;
                                Slog.d(BatteryService.TAG_SS, "!@mFullCapacityEnable Settings = " + ((AnonymousClass15) this.val$intent).this$0.mFullCapacityEnable);
                                BatteryService batteryService5 = ((AnonymousClass15) this.val$intent).this$0;
                                batteryService5.mHandlerForBatteryInfoBackUp.post(batteryService5.mUpdateBatteryUsageFullCapacityEnableRunnable);
                            }
                        } finally {
                        }
                    }
                    return;
                case 5:
                    synchronized (((AnonymousClass15) this.val$intent).this$0.mLock) {
                        ((AnonymousClass15) this.val$intent).this$0.mLifeExtender = Settings.System.getIntForUser(((AnonymousClass15) this.val$intent).this$0.mContext.getContentResolver(), "protect_battery", 0, -2) == 1;
                        String str7 = BatteryService.TAG_SS;
                        Slog.d("BatteryService", "!@mLifeExtender Settings changed = " + ((AnonymousClass15) this.val$intent).this$0.mLifeExtender);
                        BatteryService batteryService6 = ((AnonymousClass15) this.val$intent).this$0;
                        batteryService6.mHandlerForBatteryInfoBackUp.post(batteryService6.mUpdateBatteryUsageExtenderRunnable);
                    }
                    return;
                case 6:
                    run$com$android$server$BatteryService$MaximumProtectionThresholdObserver$1();
                    return;
                case 7:
                    run$com$android$server$BatteryService$PassThroughSettingsObserver$1();
                    return;
                case 8:
                    run$com$android$server$BatteryService$RefreshRateModeSettingsObserver$1();
                    return;
                case 9:
                    run$com$android$server$BatteryService$SuperFastChargingSettingsObserver$1();
                    return;
                case 10:
                    run$com$android$server$BatteryService$TxBatteryLimitSettingsObserver$1();
                    return;
                case 11:
                    run$com$android$server$BatteryService$WcParamInfoSettingsObserver$1();
                    return;
                default:
                    synchronized (((AnonymousClass15) this.val$intent).this$0.mLock) {
                        ((AnonymousClass15) this.val$intent).this$0.mWirelessFastChargingSettingsEnable = Settings.System.getIntForUser(((AnonymousClass15) this.val$intent).this$0.mContext.getContentResolver(), "wireless_fast_charging", 1, -2) == 1;
                        String str8 = BatteryService.TAG_SS;
                        Slog.d("BatteryService", "WirelessFastCharging Settings = " + ((AnonymousClass15) this.val$intent).this$0.mWirelessFastChargingSettingsEnable);
                        BatteryService batteryService7 = ((AnonymousClass15) this.val$intent).this$0;
                        batteryService7.setWirelessFastCharging(batteryService7.mWirelessFastChargingSettingsEnable);
                    }
                    return;
            }
        }
    }

    /* renamed from: com.android.server.BatteryService$34, reason: invalid class name */
    public final class AnonymousClass34 implements Runnable {
        public final /* synthetic */ int $r8$classId = 0;
        public final /* synthetic */ Object val$intent;
        public final /* synthetic */ int val$wirelessPowerSharingTxEvent;

        public AnonymousClass34(int i, Intent intent) {
            this.val$wirelessPowerSharingTxEvent = i;
            this.val$intent = intent;
        }

        public AnonymousClass34(AnonymousClass2 anonymousClass2, int i) {
            this.val$intent = anonymousClass2;
            this.val$wirelessPowerSharingTxEvent = i;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    String m = AudioChannelMask$$ExternalSyntheticOutline0.m(new StringBuilder("tx_event:0x"), this.val$wirelessPowerSharingTxEvent);
                    String str = BatteryService.TAG_SS;
                    Slog.d("BatteryService", m);
                    ActivityManager.broadcastStickyIntent((Intent) this.val$intent, -1);
                    Intent intent = (Intent) ((Intent) this.val$intent).clone();
                    intent.setPackage(BatteryService.PACKAGE_DEVICE_CARE);
                    ActivityManager.broadcastStickyIntent(intent, -1);
                    BattLogBuffer.addLog(1, m);
                    break;
                default:
                    int i = this.val$wirelessPowerSharingTxEvent;
                    if (i != 2) {
                        if (i != 0) {
                            if (i == 3) {
                                ((AnonymousClass2) this.val$intent).this$0.mCallHandler.removeMessages(0);
                                ((AnonymousClass2) this.val$intent).this$0.mCallHandler.sendMessage(((AnonymousClass2) this.val$intent).this$0.mCallHandler.obtainMessage(2));
                                break;
                            }
                        } else {
                            ((AnonymousClass2) this.val$intent).this$0.mCallHandler.removeMessages(0);
                            ((AnonymousClass2) this.val$intent).this$0.mCallHandler.sendMessage(((AnonymousClass2) this.val$intent).this$0.mCallHandler.obtainMessage(1));
                            break;
                        }
                    } else {
                        ((AnonymousClass2) this.val$intent).this$0.mCallHandler.sendMessageDelayed(((AnonymousClass2) this.val$intent).this$0.mCallHandler.obtainMessage(0), 500L);
                        break;
                    }
                    break;
            }
        }
    }

    /* renamed from: com.android.server.BatteryService$5, reason: invalid class name */
    public final class AnonymousClass5 extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.sec.intent.action.BATT_SLATE_MODE_CHANGE")) {
                BattUtils.writeNode(intent.getIntExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 0), "/sys/class/power_supply/battery/batt_slate_mode");
            }
        }
    }

    public final class BattCallbackImpl {
        public BattCallbackImpl() {
        }
    }

    public final class BatteryPropertiesRegistrar extends IBatteryPropertiesRegistrar.Stub {
        public BatteryPropertiesRegistrar() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:4:0x001d, code lost:
        
            if (com.android.internal.hidden_from_bootclasspath.android.os.Flags.stateOfHealthPublic() != false) goto L9;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int getProperty(int r5, android.os.BatteryProperty r6) {
            /*
                r4 = this;
                java.lang.String r0 = com.android.server.BatteryService.TAG_SS
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r1 = "[getProperty]id:"
                r0.<init>(r1)
                r0.append(r5)
                java.lang.String r0 = r0.toString()
                java.lang.String r1 = "BatteryService_BatteryPropertiesRegistrar"
                com.android.server.power.Slog.i(r1, r0)
                switch(r5) {
                    case 7: goto L20;
                    case 8: goto L20;
                    case 9: goto L20;
                    case 10: goto L19;
                    case 11: goto L20;
                    case 12: goto L20;
                    default: goto L18;
                }
            L18:
                goto L2a
            L19:
                boolean r0 = com.android.internal.hidden_from_bootclasspath.android.os.Flags.stateOfHealthPublic()
                if (r0 == 0) goto L20
                goto L2a
            L20:
                com.android.server.BatteryService r0 = com.android.server.BatteryService.this
                android.content.Context r0 = r0.mContext
                java.lang.String r2 = "android.permission.BATTERY_STATS"
                r3 = 0
                r0.enforceCallingPermission(r2, r3)
            L2a:
                if (r6 == 0) goto L4d
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r2 = "[getProperty]prop.long:"
                r0.<init>(r2)
                long r2 = r6.getLong()
                r0.append(r2)
                java.lang.String r2 = " ,prop.string:"
                r0.append(r2)
                java.lang.String r2 = r6.getString()
                r0.append(r2)
                java.lang.String r0 = r0.toString()
                com.android.server.power.Slog.i(r1, r0)
            L4d:
                com.android.server.BatteryService r4 = com.android.server.BatteryService.this
                com.android.server.health.HealthServiceWrapper r4 = r4.mHealthServiceWrapper
                int r4 = r4.getProperty(r5, r6)
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.BatteryService.BatteryPropertiesRegistrar.getProperty(int, android.os.BatteryProperty):int");
        }

        public final void scheduleUpdate() {
            BatteryService.this.mHealthServiceWrapper.scheduleUpdate();
        }

        public final boolean semGetValueAsBoolean(int i) {
            boolean z = false;
            if (i != 106) {
                String str = BatteryService.TAG_SS;
                Slog.e("BatteryService", "[semGetValueAsBoolean]Not Matched id");
            } else {
                final boolean[] icAuthenticationResults = BatteryService.this.mBattInfoManager.getIcAuthenticationResults();
                if (icAuthenticationResults != null) {
                    z = IntStream.range(0, icAuthenticationResults.length).allMatch(new IntPredicate() { // from class: com.android.server.BatteryService$BatteryPropertiesRegistrar$$ExternalSyntheticLambda1
                        @Override // java.util.function.IntPredicate
                        public final boolean test(int i2) {
                            return icAuthenticationResults[i2];
                        }
                    });
                }
            }
            String str2 = BatteryService.TAG_SS;
            Slog.i("BatteryService", "[semGetValueAsBoolean]id:" + i + " ,result:" + z);
            return z;
        }

        public final long[] semGetValuesAsLong(int i) {
            long j = -1;
            long[] jArr = null;
            switch (i) {
                case 103:
                    jArr = BatteryService.this.mBattInfoManager.getDischargeLevel();
                    break;
                case 104:
                    jArr = BatteryService.this.mBattInfoManager.getFullStatusUsage();
                    break;
                case 105:
                    jArr = BatteryService.this.mBattInfoManager.getAsocValue();
                    break;
                case 107:
                    BattInfoManager battInfoManager = BatteryService.this.mBattInfoManager;
                    if (!battInfoManager.mInitFinished) {
                        android.util.Slog.w("[SS][BattInfo]BattInfoManager", "[getBsohValues]InitFinished False");
                        break;
                    } else {
                        ArrayList arrayList = new ArrayList();
                        int i2 = battInfoManager.mBatteryType;
                        if (i2 == 2) {
                            arrayList.add(Long.valueOf(BattUtils.readNodeAsLong("/sys/class/power_supply/sbp-fg/battery_state_of_health")));
                        } else if (i2 != 4) {
                            arrayList.add(Long.valueOf((long) BattUtils.readNodeAsDouble()));
                        } else {
                            arrayList.add(-1L);
                            arrayList.add(Long.valueOf(BattUtils.readNodeAsLong("/sys/class/power_supply/sbp-fg/battery_state_of_health")));
                            arrayList.add(Long.valueOf(BattUtils.readNodeAsLong("/sys/class/power_supply/sbp-fg-2/battery_state_of_health")));
                        }
                        jArr = arrayList.stream().mapToLong(new AsocData$$ExternalSyntheticLambda1()).toArray();
                        break;
                    }
                case 108:
                    try {
                        j = Long.parseLong(BattUtils.getValueFromJson(XmlUtils$$ExternalSyntheticOutline0.m("{", BattUtils.readNode("/sys/class/power_supply/battery/cisd_data_json", false), "}")));
                    } catch (NumberFormatException unused) {
                        android.util.Slog.e("[SS]BattUtils", "[getValueFromJsonAsLong]NumberFormatException");
                    }
                    jArr = new long[]{j};
                    break;
            }
            String str = BatteryService.TAG_SS;
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "[semGetValuesAsLong]id:", " ,result:");
            m.append(Arrays.toString(jArr));
            Slog.d("BatteryService", m.toString());
            return jArr;
        }

        public final String[] semGetValuesAsString(int i) {
            String[] strArr;
            if (i != 101) {
                if (i == 102) {
                    BattInfoManager battInfoManager = BatteryService.this.mBattInfoManager;
                    if (battInfoManager.mInitFinished) {
                        strArr = (String[]) battInfoManager.mFirstUseDateData.readEfsValues();
                    } else {
                        android.util.Slog.w("[SS][BattInfo]BattInfoManager", "[getFirstUseDate]InitFinished False");
                    }
                }
                strArr = null;
            } else {
                BattInfoManager battInfoManager2 = BatteryService.this.mBattInfoManager;
                if (battInfoManager2.mInitFinished) {
                    strArr = (String[]) battInfoManager2.mQrData.readEfsValues();
                } else {
                    android.util.Slog.w("[SS][BattInfo]BattInfoManager", "[getQr]InitFinished False");
                    strArr = null;
                }
            }
            String str = BatteryService.TAG_SS;
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "[semGetValuesAsString]id:", " ,result:");
            m.append(Arrays.toString(strArr));
            Slog.d("BatteryService", m.toString());
            return null;
        }
    }

    public final class BinderService extends Binder {
        public BinderService() {
        }

        @Override // android.os.Binder
        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            Context context = BatteryService.this.mContext;
            String str = BatteryService.TAG_SS;
            if (DumpUtils.checkDumpPermission(context, "BatteryService", printWriter)) {
                if (strArr.length > 0) {
                    int i = 0;
                    if ("--proto".equals(strArr[0])) {
                        BatteryService batteryService = BatteryService.this;
                        batteryService.getClass();
                        ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
                        synchronized (batteryService.mLock) {
                            protoOutputStream.write(1133871366145L, batteryService.mUpdatesStopped);
                            HealthInfo healthInfo = batteryService.mHealthInfo;
                            if (healthInfo.chargerAcOnline) {
                                i = 1;
                            } else if (healthInfo.chargerUsbOnline) {
                                i = 2;
                            } else if (healthInfo.chargerWirelessOnline) {
                                i = 4;
                            } else if (healthInfo.chargerDockOnline) {
                                i = 8;
                            }
                            protoOutputStream.write(1159641169922L, i);
                            protoOutputStream.write(1120986464259L, batteryService.mHealthInfo.maxChargingCurrentMicroamps);
                            protoOutputStream.write(1120986464260L, batteryService.mHealthInfo.maxChargingVoltageMicrovolts);
                            protoOutputStream.write(1120986464261L, batteryService.mHealthInfo.batteryChargeCounterUah);
                            protoOutputStream.write(1159641169926L, batteryService.mHealthInfo.batteryStatus);
                            protoOutputStream.write(1159641169927L, batteryService.mHealthInfo.batteryHealth);
                            protoOutputStream.write(1133871366152L, batteryService.mHealthInfo.batteryPresent);
                            protoOutputStream.write(1120986464265L, batteryService.mHealthInfo.batteryLevel);
                            protoOutputStream.write(1120986464266L, 100);
                            protoOutputStream.write(1120986464267L, batteryService.mHealthInfo.batteryVoltageMillivolts);
                            protoOutputStream.write(1120986464268L, batteryService.mHealthInfo.batteryTemperatureTenthsCelsius);
                            protoOutputStream.write(1138166333453L, batteryService.mHealthInfo.batteryTechnology);
                        }
                        protoOutputStream.flush();
                        return;
                    }
                }
                BatteryService.m41$$Nest$mdumpInternal(BatteryService.this, fileDescriptor, printWriter, strArr);
            }
        }

        public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            BatteryService.this.new Shell().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }
    }

    public final class Led {
        public final LightsService.LightImpl mBatteryLight;
        public int mLedStatus = 0;

        public Led(Context context, LightsManager lightsManager) {
            this.mBatteryLight = lightsManager.getLight(3);
            context.getResources().getInteger(R.integer.config_satellite_stay_at_listening_from_sending_millis);
            context.getResources().getInteger(R.integer.config_screenBrightnessDark);
            context.getResources().getInteger(R.integer.config_satellite_modem_image_switching_duration_millis);
            context.getResources().getInteger(R.integer.config_satellite_stay_at_listening_from_receiving_millis);
            context.getResources().getInteger(R.integer.config_satellite_nb_iot_inactivity_timeout_millis);
            context.getResources().getInteger(R.integer.config_screenBrightnessDim);
            BatteryService.this.getClass();
            context.getResources().getInteger(R.integer.config_screenBrightnessCapForWearBedtimeMode);
        }

        public final void updateLightsLocked() {
            LightsService.LightImpl lightImpl = this.mBatteryLight;
            if (lightImpl != null) {
                BatteryService batteryService = BatteryService.this;
                if (batteryService.mBootCompleted) {
                    HealthInfo healthInfo = batteryService.mHealthInfo;
                    int i = healthInfo.batteryLevel;
                    int i2 = healthInfo.batteryStatus;
                    int i3 = healthInfo.batteryHealth;
                    if (4 == i2 && ((3 == i3 || 6 == i3 || 7 == i3 || 8 == i3) && batteryService.mLedChargingSettingsEnable)) {
                        if (11 == this.mLedStatus) {
                            String str = BatteryService.TAG_SS;
                            Slog.d("BatteryService", "stay LED for not charging");
                            return;
                        } else {
                            lightImpl.setFlashing(0, 11, 0, 0);
                            this.mLedStatus = 11;
                            String str2 = BatteryService.TAG_SS;
                            Slog.d("BatteryService", "turn on LED for not charging");
                            return;
                        }
                    }
                    if (2 == i2 && !batteryService.mScreenOn && batteryService.mLedChargingSettingsEnable) {
                        if (10 == this.mLedStatus) {
                            String str3 = BatteryService.TAG_SS;
                            Slog.d("BatteryService", "stay LED for charging");
                            return;
                        } else {
                            lightImpl.setFlashing(0, 10, 0, 0);
                            this.mLedStatus = 10;
                            String str4 = BatteryService.TAG_SS;
                            Slog.d("BatteryService", "turn on LED for charging");
                            return;
                        }
                    }
                    if (5 == i2 && !batteryService.mScreenOn && batteryService.mLedChargingSettingsEnable) {
                        if (14 == this.mLedStatus) {
                            String str5 = BatteryService.TAG_SS;
                            Slog.d("BatteryService", "stay LED for fully charged");
                            return;
                        } else {
                            lightImpl.setFlashing(0, 14, 0, 0);
                            this.mLedStatus = 14;
                            String str6 = BatteryService.TAG_SS;
                            Slog.d("BatteryService", "turn on LED for fully charged");
                            return;
                        }
                    }
                    if (i > batteryService.mLowBatteryWarningLevel || batteryService.mScreenOn || !batteryService.mLedLowBatterySettingsEnable) {
                        if (this.mLedStatus != 0) {
                            lightImpl.turnOff();
                            this.mLedStatus = 0;
                            String str7 = BatteryService.TAG_SS;
                            Slog.d("BatteryService", "turn off LED");
                            return;
                        }
                        return;
                    }
                    if (13 == this.mLedStatus) {
                        String str8 = BatteryService.TAG_SS;
                        Slog.d("BatteryService", "stay LED for low battery");
                    } else {
                        lightImpl.setFlashing(0, 13, 0, 0);
                        this.mLedStatus = 13;
                        String str9 = BatteryService.TAG_SS;
                        Slog.d("BatteryService", "turn on LED for low battery");
                    }
                }
            }
        }
    }

    public final class LocalService extends BatteryManagerInternal {
        public LocalService() {
        }

        public final int getBatteryChargeCounter() {
            int i;
            synchronized (BatteryService.this.mLock) {
                i = BatteryService.this.mHealthInfo.batteryChargeCounterUah;
            }
            return i;
        }

        public final int getBatteryFullCharge() {
            int i;
            synchronized (BatteryService.this.mLock) {
                i = BatteryService.this.mHealthInfo.batteryFullChargeUah;
            }
            return i;
        }

        public final int getBatteryHealth() {
            int i;
            synchronized (BatteryService.this.mLock) {
                i = BatteryService.this.mHealthInfo.batteryHealth;
            }
            return i;
        }

        public final int getBatteryLevel() {
            int i;
            synchronized (BatteryService.this.mLock) {
                i = BatteryService.this.mHealthInfo.batteryLevel;
            }
            return i;
        }

        public final boolean getBatteryLevelCritical() {
            boolean z;
            synchronized (BatteryService.this.mLock) {
                z = BatteryService.this.mBatteryLevelCritical;
            }
            return z;
        }

        public final boolean getBatteryLevelLow() {
            boolean z;
            synchronized (BatteryService.this.mLock) {
                z = BatteryService.this.mBatteryLevelLow;
            }
            return z;
        }

        public final int getBatteryLevelRaw() {
            return BattUtils.readNodeAsInt("/sys/class/power_supply/battery/batt_read_raw_soc");
        }

        public final int getBatteryOnline() {
            return BatteryService.this.mSehHealthInfo.batteryOnline;
        }

        public final int getBatteryStateOfHealth() {
            int readNodeAsDouble = (int) BattUtils.readNodeAsDouble();
            return readNodeAsDouble < 0 ? BattUtils.readNodeAsInt("/efs/FactoryApp/asoc") : readNodeAsDouble;
        }

        public final int getChargingPolicy() {
            int i;
            synchronized (BatteryService.this.mLock) {
                i = BatteryService.this.mLastChargingPolicy;
            }
            return i;
        }

        public final int getInvalidCharger() {
            int i;
            synchronized (BatteryService.this.mLock) {
                i = BatteryService.this.mInvalidCharger;
            }
            return i;
        }

        public final int getPlugType() {
            int i;
            synchronized (BatteryService.this.mLock) {
                i = BatteryService.this.mPlugType;
            }
            return i;
        }

        public final boolean isPowered(int i) {
            boolean isPoweredLocked;
            synchronized (BatteryService.this.mLock) {
                isPoweredLocked = BatteryService.this.isPoweredLocked(i);
            }
            return isPoweredLocked;
        }

        public final void registerChargingPolicyChangeListener(BatteryManagerInternal.ChargingPolicyChangeListener chargingPolicyChangeListener) {
            BatteryService.this.mChargingPolicyChangeListeners.add(chargingPolicyChangeListener);
        }

        public final void resetBattery(boolean z) {
            BatteryService.this.resetBattery(null, z);
        }

        public final void setBatteryLevel(int i, boolean z) {
            BatteryService batteryService = BatteryService.this;
            if (!batteryService.mUpdatesStopped) {
                Utils.copySehV1Battery(batteryService.mLastSehHealthInfo, batteryService.mSehHealthInfo);
            }
            batteryService.mHealthInfo.batteryLevel = i;
            batteryService.mUpdatesStopped = true;
            Binder.withCleanCallingIdentity(new BatteryService$$ExternalSyntheticLambda13(batteryService, z, 1));
        }

        public final void setChargerAcOnline(boolean z, boolean z2) {
            BatteryService batteryService = BatteryService.this;
            if (!batteryService.mUpdatesStopped) {
                Utils.copySehV1Battery(batteryService.mLastSehHealthInfo, batteryService.mSehHealthInfo);
            }
            batteryService.mHealthInfo.chargerAcOnline = z;
            batteryService.mUpdatesStopped = true;
            Binder.withCleanCallingIdentity(new BatteryService$$ExternalSyntheticLambda13(batteryService, z2, 0));
        }

        public final void setWirelessPowerSharingExternelEvent(int i, int i2) {
            String str = BatteryService.TAG_SS;
            Slog.i("BatteryService", "setWirelessPowerSharingExternelEvent packageNum: " + i + " value: " + i2);
            BatteryService.this.setWirelessPowerSharingExternelEventInternal(i, i2);
        }

        public final void suspendBatteryInput() {
            BatteryService batteryService = BatteryService.this;
            batteryService.getClass();
            if (!Build.IS_DEBUGGABLE) {
                throw new SecurityException("battery suspend_input is only supported on debuggable builds");
            }
            PowerProperties.battery_input_suspended(Boolean.TRUE);
            batteryService.mBatteryInputSuspended = true;
        }

        public final void unplugBattery(boolean z) {
            BatteryService.this.unplugBattery(null, z);
        }
    }

    public final class Shell extends ShellCommand {
        public Shell() {
        }

        /* JADX WARN: Removed duplicated region for block: B:44:0x0235  */
        /* JADX WARN: Removed duplicated region for block: B:47:0x023e A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:52:0x0237  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int onCommand(java.lang.String r25) {
            /*
                Method dump skipped, instructions count: 1202
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.BatteryService.Shell.onCommand(java.lang.String):int");
        }

        public final void onHelp() {
            PrintWriter outPrintWriter = getOutPrintWriter();
            String str = BatteryService.TAG_SS;
            outPrintWriter.println("Battery service (battery) commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Print this help text.");
            String str2 = Flags.batteryServiceSupportCurrentAdbCommand() ? "ac|usb|wireless|dock|status|level|temp|present|counter|invalid|current_now|current_average" : "ac|usb|wireless|dock|status|level|temp|present|counter|invalid";
            outPrintWriter.println("  get [-f] [" + str2 + "]");
            outPrintWriter.println("  set [-f] [" + str2 + "] <value>");
            outPrintWriter.println("    Force a battery property value, freezing battery state.");
            BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    -f: force a battery change broadcast be sent, prints new sequence.", "  unplug [-f]", "    Force battery unplugged, freezing battery state.", "    -f: force a battery change broadcast be sent, prints new sequence.");
            BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "  reset [-f]", "    Unfreeze battery state, returning to current hardware values.", "    -f: force a battery change broadcast be sent, prints new sequence.");
            if (Build.IS_DEBUGGABLE) {
                outPrintWriter.println("  suspend_input");
                outPrintWriter.println("    Suspend charging even if plugged in. ");
            }
        }
    }

    /* renamed from: -$$Nest$mactivateSleepChargingManager, reason: not valid java name */
    public static void m40$$Nest$mactivateSleepChargingManager(BatteryService batteryService, boolean z) {
        batteryService.getClass();
        String str = TAG_SS;
        Slog.i(str, "[activateSleepChargingManager]activate:" + z);
        if (z) {
            if (batteryService.mSleepChargingManager != null) {
                Slog.e(str, "[activateSleepChargingManager]activated multiple times => ignored");
                return;
            }
            Slog.d(str, "[createSleepChargingHandler]");
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(batteryService, Looper.myLooper(), 0);
            batteryService.mSleepChargingHandler = anonymousClass1;
            SleepChargingManager sleepChargingManager = new SleepChargingManager(batteryService.mContext, anonymousClass1, batteryService.mProtectionThreshold);
            batteryService.mSleepChargingManager = sleepChargingManager;
            HealthInfo healthInfo = batteryService.mHealthInfo;
            if (healthInfo != null) {
                sleepChargingManager.mHandler.post(sleepChargingManager.new AnonymousClass1(batteryService.mPlugType, healthInfo.batteryLevel, healthInfo.batteryChargeTimeToFullNowSeconds));
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.samsung.android.sm.ACTION_OPTIMIZED_CHARGING_NOTI_DISMISSED");
            Slog.d(str, "[createSleepChargingDismissReceiver]");
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(batteryService, 0);
            batteryService.mSleepChargingDismissReceiver = anonymousClass2;
            batteryService.mContext.registerReceiver(anonymousClass2, intentFilter, "com.samsung.permission.WRITE_SM_DATA", null, 2);
            return;
        }
        AnonymousClass2 anonymousClass22 = batteryService.mSleepChargingDismissReceiver;
        if (anonymousClass22 != null) {
            batteryService.mContext.unregisterReceiver(anonymousClass22);
            batteryService.mSleepChargingDismissReceiver = null;
        }
        SleepChargingManager sleepChargingManager2 = batteryService.mSleepChargingManager;
        if (sleepChargingManager2 != null) {
            android.util.Slog.i("[SS]SleepChargingManager", "[end]");
            BatteryLogger.writeToFile("/data/log/battery_service/sleep_charging_history", "SleepChargingManager end", "");
            BattLogBuffer.addLog(2, "[Deactivated]");
            SleepChargingTimeController sleepChargingTimeController = sleepChargingManager2.mSleepChargingTimeController;
            sleepChargingTimeController.getClass();
            android.util.Slog.i("[SS]SleepChargingTimeController", "[end]");
            sleepChargingTimeController.mIsEndCalled = true;
            sleepChargingTimeController.activateTimeReachedReceiver(false);
            sleepChargingTimeController.reset(true);
            SleepChargingManager.AnonymousClass3 anonymousClass3 = sleepChargingManager2.mHandler;
            if (anonymousClass3 != null) {
                anonymousClass3.removeCallbacksAndMessages(null);
            }
            HandlerThread handlerThread = sleepChargingManager2.mWorkerThread;
            if (handlerThread != null) {
                handlerThread.quit();
                sleepChargingManager2.mWorkerThread = null;
            }
            batteryService.mSleepChargingManager = null;
        }
        AnonymousClass1 anonymousClass12 = batteryService.mSleepChargingHandler;
        if (anonymousClass12 != null) {
            anonymousClass12.removeCallbacksAndMessages(null);
            batteryService.mSleepChargingHandler = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x02c1 A[Catch: all -> 0x0104, TryCatch #0 {all -> 0x0104, blocks: (B:66:0x00da, B:68:0x00dd, B:6:0x0109, B:8:0x0112, B:9:0x0117, B:11:0x02c1, B:12:0x02d6, B:14:0x02dc, B:15:0x02f1, B:17:0x037a, B:18:0x03c3, B:20:0x0439, B:22:0x04af, B:23:0x04be, B:24:0x04c3, B:26:0x0511, B:27:0x0525, B:29:0x0539, B:31:0x055c, B:32:0x0571, B:34:0x0592, B:35:0x05b3, B:36:0x05d6, B:62:0x05ae, B:63:0x056c, B:64:0x051b, B:71:0x00e9), top: B:65:0x00da }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x02dc A[Catch: all -> 0x0104, TryCatch #0 {all -> 0x0104, blocks: (B:66:0x00da, B:68:0x00dd, B:6:0x0109, B:8:0x0112, B:9:0x0117, B:11:0x02c1, B:12:0x02d6, B:14:0x02dc, B:15:0x02f1, B:17:0x037a, B:18:0x03c3, B:20:0x0439, B:22:0x04af, B:23:0x04be, B:24:0x04c3, B:26:0x0511, B:27:0x0525, B:29:0x0539, B:31:0x055c, B:32:0x0571, B:34:0x0592, B:35:0x05b3, B:36:0x05d6, B:62:0x05ae, B:63:0x056c, B:64:0x051b, B:71:0x00e9), top: B:65:0x00da }] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x037a A[Catch: all -> 0x0104, TryCatch #0 {all -> 0x0104, blocks: (B:66:0x00da, B:68:0x00dd, B:6:0x0109, B:8:0x0112, B:9:0x0117, B:11:0x02c1, B:12:0x02d6, B:14:0x02dc, B:15:0x02f1, B:17:0x037a, B:18:0x03c3, B:20:0x0439, B:22:0x04af, B:23:0x04be, B:24:0x04c3, B:26:0x0511, B:27:0x0525, B:29:0x0539, B:31:0x055c, B:32:0x0571, B:34:0x0592, B:35:0x05b3, B:36:0x05d6, B:62:0x05ae, B:63:0x056c, B:64:0x051b, B:71:0x00e9), top: B:65:0x00da }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0439 A[Catch: all -> 0x0104, TryCatch #0 {all -> 0x0104, blocks: (B:66:0x00da, B:68:0x00dd, B:6:0x0109, B:8:0x0112, B:9:0x0117, B:11:0x02c1, B:12:0x02d6, B:14:0x02dc, B:15:0x02f1, B:17:0x037a, B:18:0x03c3, B:20:0x0439, B:22:0x04af, B:23:0x04be, B:24:0x04c3, B:26:0x0511, B:27:0x0525, B:29:0x0539, B:31:0x055c, B:32:0x0571, B:34:0x0592, B:35:0x05b3, B:36:0x05d6, B:62:0x05ae, B:63:0x056c, B:64:0x051b, B:71:0x00e9), top: B:65:0x00da }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0511 A[Catch: all -> 0x0104, TryCatch #0 {all -> 0x0104, blocks: (B:66:0x00da, B:68:0x00dd, B:6:0x0109, B:8:0x0112, B:9:0x0117, B:11:0x02c1, B:12:0x02d6, B:14:0x02dc, B:15:0x02f1, B:17:0x037a, B:18:0x03c3, B:20:0x0439, B:22:0x04af, B:23:0x04be, B:24:0x04c3, B:26:0x0511, B:27:0x0525, B:29:0x0539, B:31:0x055c, B:32:0x0571, B:34:0x0592, B:35:0x05b3, B:36:0x05d6, B:62:0x05ae, B:63:0x056c, B:64:0x051b, B:71:0x00e9), top: B:65:0x00da }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0539 A[Catch: all -> 0x0104, TryCatch #0 {all -> 0x0104, blocks: (B:66:0x00da, B:68:0x00dd, B:6:0x0109, B:8:0x0112, B:9:0x0117, B:11:0x02c1, B:12:0x02d6, B:14:0x02dc, B:15:0x02f1, B:17:0x037a, B:18:0x03c3, B:20:0x0439, B:22:0x04af, B:23:0x04be, B:24:0x04c3, B:26:0x0511, B:27:0x0525, B:29:0x0539, B:31:0x055c, B:32:0x0571, B:34:0x0592, B:35:0x05b3, B:36:0x05d6, B:62:0x05ae, B:63:0x056c, B:64:0x051b, B:71:0x00e9), top: B:65:0x00da }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0592 A[Catch: all -> 0x0104, TryCatch #0 {all -> 0x0104, blocks: (B:66:0x00da, B:68:0x00dd, B:6:0x0109, B:8:0x0112, B:9:0x0117, B:11:0x02c1, B:12:0x02d6, B:14:0x02dc, B:15:0x02f1, B:17:0x037a, B:18:0x03c3, B:20:0x0439, B:22:0x04af, B:23:0x04be, B:24:0x04c3, B:26:0x0511, B:27:0x0525, B:29:0x0539, B:31:0x055c, B:32:0x0571, B:34:0x0592, B:35:0x05b3, B:36:0x05d6, B:62:0x05ae, B:63:0x056c, B:64:0x051b, B:71:0x00e9), top: B:65:0x00da }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x05ae A[Catch: all -> 0x0104, TryCatch #0 {all -> 0x0104, blocks: (B:66:0x00da, B:68:0x00dd, B:6:0x0109, B:8:0x0112, B:9:0x0117, B:11:0x02c1, B:12:0x02d6, B:14:0x02dc, B:15:0x02f1, B:17:0x037a, B:18:0x03c3, B:20:0x0439, B:22:0x04af, B:23:0x04be, B:24:0x04c3, B:26:0x0511, B:27:0x0525, B:29:0x0539, B:31:0x055c, B:32:0x0571, B:34:0x0592, B:35:0x05b3, B:36:0x05d6, B:62:0x05ae, B:63:0x056c, B:64:0x051b, B:71:0x00e9), top: B:65:0x00da }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x056c A[Catch: all -> 0x0104, TryCatch #0 {all -> 0x0104, blocks: (B:66:0x00da, B:68:0x00dd, B:6:0x0109, B:8:0x0112, B:9:0x0117, B:11:0x02c1, B:12:0x02d6, B:14:0x02dc, B:15:0x02f1, B:17:0x037a, B:18:0x03c3, B:20:0x0439, B:22:0x04af, B:23:0x04be, B:24:0x04c3, B:26:0x0511, B:27:0x0525, B:29:0x0539, B:31:0x055c, B:32:0x0571, B:34:0x0592, B:35:0x05b3, B:36:0x05d6, B:62:0x05ae, B:63:0x056c, B:64:0x051b, B:71:0x00e9), top: B:65:0x00da }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x051b A[Catch: all -> 0x0104, TryCatch #0 {all -> 0x0104, blocks: (B:66:0x00da, B:68:0x00dd, B:6:0x0109, B:8:0x0112, B:9:0x0117, B:11:0x02c1, B:12:0x02d6, B:14:0x02dc, B:15:0x02f1, B:17:0x037a, B:18:0x03c3, B:20:0x0439, B:22:0x04af, B:23:0x04be, B:24:0x04c3, B:26:0x0511, B:27:0x0525, B:29:0x0539, B:31:0x055c, B:32:0x0571, B:34:0x0592, B:35:0x05b3, B:36:0x05d6, B:62:0x05ae, B:63:0x056c, B:64:0x051b, B:71:0x00e9), top: B:65:0x00da }] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0112 A[Catch: all -> 0x0104, TryCatch #0 {all -> 0x0104, blocks: (B:66:0x00da, B:68:0x00dd, B:6:0x0109, B:8:0x0112, B:9:0x0117, B:11:0x02c1, B:12:0x02d6, B:14:0x02dc, B:15:0x02f1, B:17:0x037a, B:18:0x03c3, B:20:0x0439, B:22:0x04af, B:23:0x04be, B:24:0x04c3, B:26:0x0511, B:27:0x0525, B:29:0x0539, B:31:0x055c, B:32:0x0571, B:34:0x0592, B:35:0x05b3, B:36:0x05d6, B:62:0x05ae, B:63:0x056c, B:64:0x051b, B:71:0x00e9), top: B:65:0x00da }] */
    /* renamed from: -$$Nest$mdumpInternal, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m41$$Nest$mdumpInternal(com.android.server.BatteryService r64, java.io.FileDescriptor r65, java.io.PrintWriter r66, java.lang.String[] r67) {
        /*
            Method dump skipped, instructions count: 1586
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.BatteryService.m41$$Nest$mdumpInternal(com.android.server.BatteryService, java.io.FileDescriptor, java.io.PrintWriter, java.lang.String[]):void");
    }

    /* renamed from: -$$Nest$msetCallWirelessPowerSharingExternelEvent, reason: not valid java name */
    public static void m42$$Nest$msetCallWirelessPowerSharingExternelEvent(BatteryService batteryService, boolean z) {
        if (!z) {
            Slog.d("BatteryService", "call end, isVideoCall: " + batteryService.isVideoCall);
            if (batteryService.isVideoCall) {
                batteryService.setWirelessPowerSharingExternelEventInternal(1, 0);
            } else {
                batteryService.setWirelessPowerSharingExternelEventInternal(4, 0);
            }
            batteryService.isVideoCall = false;
            return;
        }
        TelephonyManager telephonyManager = batteryService.tm;
        if (telephonyManager != null) {
            batteryService.isVideoCall = telephonyManager.semIsVideoCall();
            Slog.d("BatteryService", "isVideoCall: " + batteryService.isVideoCall);
        }
        Slog.d("BatteryService", "call start, isVideoCall: " + batteryService.isVideoCall);
        if (batteryService.isVideoCall) {
            batteryService.setWirelessPowerSharingExternelEventInternal(1, 1);
        } else {
            batteryService.setWirelessPowerSharingExternelEventInternal(4, 4);
        }
    }

    /* renamed from: -$$Nest$msetSleepCharging, reason: not valid java name */
    public static void m43$$Nest$msetSleepCharging(BatteryService batteryService, boolean z) {
        batteryService.getClass();
        Slog.i(TAG_SS, "[setSleepCharging]on:" + z);
        if (!z) {
            BattUtils.writeNode(100L, "/sys/class/power_supply/battery/batt_full_capacity");
            return;
        }
        BattUtils.writeNode("/sys/class/power_supply/battery/batt_full_capacity", batteryService.mProtectionThreshold + " SLEEP");
    }

    /* renamed from: -$$Nest$smsendBroadcastToExplicitPackage, reason: not valid java name */
    public static void m44$$Nest$smsendBroadcastToExplicitPackage(Context context, Intent intent, String str) {
        Slog.d("BatteryService", "sendBroadcastToExplicitPackage: " + intent + " -> " + str);
        Intent intent2 = (Intent) intent.clone();
        intent2.setPackage(str);
        context.sendBroadcastAsUser(intent2, UserHandle.ALL);
    }

    static {
        String[] split;
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_DAILYBOARD");
        boolean z = false;
        if (string != null && (split = string.split(",")) != null) {
            int length = split.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (split[i].equalsIgnoreCase("TA")) {
                    z = true;
                    break;
                }
                i++;
            }
        }
        FEATURE_SUPPORTED_DAILY_BOARD = z;
        FEATURE_WIRELESS_FAST_CHARGER_CONTROL = isFileSupported("/sys/class/power_supply/battery/batt_hv_wireless_pad_ctrl");
    }

    public BatteryService(Context context) {
        super(context);
        BroadcastReceiver broadcastReceiver;
        long j;
        long j2;
        long j3;
        this.mIsHiccupPopupShowing = false;
        this.isVideoCall = false;
        this.mLock = new Object();
        this.mLockBatteryInfoBackUp = new Object();
        this.mLastSehHealthInfo = new SehHealthInfo();
        this.mSequence = 1;
        this.mLastPlugType = -1;
        this.mLastSecPlugTypeSummary = -1;
        this.mSentLowBatteryBroadcast = false;
        this.mChargingPolicyChangeListeners = new CopyOnWriteArraySet();
        this.mBatteryChangedOptions = BroadcastOptions.makeBasic().setDeliveryGroupPolicy(1).setDeferralPolicy(2).toBundle();
        this.mPowerOptions = BroadcastOptions.makeBasic().setDeliveryGroupPolicy(1).setDeliveryGroupMatchingKey("android", "android.intent.action.ACTION_POWER_CONNECTED").setDeferralPolicy(2).toBundle();
        this.mBatteryOptions = BroadcastOptions.makeBasic().setDeliveryGroupPolicy(1).setDeliveryGroupMatchingKey("android", "android.intent.action.BATTERY_OKAY").setDeferralPolicy(2).toBundle();
        this.mBootCompleted = false;
        this.mIsUnlockedBootCompleted = false;
        this.mScreenOn = true;
        this.mLedChargingSettingsEnable = true;
        this.mLedLowBatterySettingsEnable = true;
        this.mAdaptiveFastChargingSettingsEnable = true;
        this.mSuperFastChargingSettingsEnable = true;
        this.mPassThroughSettingsEnable = false;
        this.mPogoDockState = 0;
        this.mPogoCondition = 0;
        this.mLastBatteryEventWaterInConnector = false;
        this.mLastTxEventTxEnabled = true;
        this.mLastTxEventRxConnected = false;
        this.mIsWirelessTxSupported = false;
        this.mActivityManagerReady = false;
        this.mSavedBatteryMaxTemp = -1L;
        this.mSavedBatteryMaxCurrent = -1L;
        this.mBatteryMaxTemp = -1L;
        this.mBatteryMaxCurrent = -1L;
        this.mLongBatteryRetryCnt = 0;
        this.mSavedDiffWeek = -1;
        this.mLifeExtender = false;
        this.mFullCapacityEnable = false;
        this.mLastDeterioration = 0;
        this.mIsFirstIntentSended = false;
        this.mLastWirelessPinDetected = false;
        this.mNotifyWirelessEnabled = false;
        this.mSemInputDeviceManager = null;
        this.mLatestWirelessChargingMode = 0;
        this.mWcTxId = 0;
        this.mLastWcTxId = -1;
        int i = Settings.Global.BATTERY_PROTECTION_THRESHOLD_DEFAULT_VALUE;
        this.mProtectionThreshold = i;
        this.mMaximumProtectionThreshold = i;
        this.mBatteryChangedConditionCounts = new int[22];
        BattCallbackImpl battCallbackImpl = new BattCallbackImpl();
        this.mIsSkipActionBatteryChanged = false;
        this.mEnableIqi = false;
        BroadcastReceiver anonymousClass2 = new AnonymousClass2(this, 3);
        BroadcastReceiver anonymousClass22 = new AnonymousClass2(this, 4);
        BroadcastReceiver anonymousClass5 = new AnonymousClass5();
        BroadcastReceiver anonymousClass23 = new AnonymousClass2(this, 5);
        BroadcastReceiver anonymousClass24 = new AnonymousClass2(this, 6);
        BroadcastReceiver anonymousClass25 = new AnonymousClass2(this, 7);
        BroadcastReceiver anonymousClass26 = new AnonymousClass2(this, 8);
        BroadcastReceiver anonymousClass27 = new AnonymousClass2(this, 1);
        BroadcastReceiver anonymousClass28 = new AnonymousClass2(this, 2);
        this.mWirelessFastChargingSettingsEnable = true;
        this.mWasUsedWirelessFastChargerPreviously = false;
        this.mRefreshRateModeSetting = 0;
        this.mSaveBatteryMaxTempRunnable = new AnonymousClass16(this, 4);
        this.mSaveBatteryMaxCurrentRunnable = new AnonymousClass16(this, 5);
        this.mUpdateBatteryUsageExtenderRunnable = new AnonymousClass16(this, 6);
        this.mUpdateBatteryUsageFullCapacityEnableRunnable = new AnonymousClass16(this, 7);
        this.mContext = context;
        this.mHandler = new Handler(true);
        this.mHandlerForBatteryInfoBackUp = new Handler(true);
        this.mLed = new Led(context, (LightsManager) getLocalService(LightsManager.class));
        this.mBatteryStats = BatteryStatsService.getService();
        this.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mPowerManagerInternal = (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        this.mToken = new Binder();
        this.mCriticalBatteryLevel = context.getResources().getInteger(R.integer.config_defaultRefreshRate);
        int integer = context.getResources().getInteger(R.integer.config_nightDisplayColorTemperatureMin);
        this.mLowBatteryWarningLevel = integer;
        this.mLowBatteryCloseWarningLevel = context.getResources().getInteger(R.integer.config_nightDisplayColorTemperatureMax) + integer;
        context.getResources().getInteger(R.integer.device_idle_idle_pending_to_ms);
        this.mShutdownIfNoPower = context.getResources().getBoolean(R.bool.config_speed_up_audio_on_mt_calls);
        context.getResources().getString(R.string.config_systemUi);
        this.mBatteryLevelsEventQueue = new ArrayDeque();
        this.mMetricsLogger = new MetricsLogger();
        BatteryLogger.renameForBackupIfExeedsSize("/data/log/battery_service/sleep_charging_history");
        BatteryLogger.renameForBackupIfExeedsSize("/data/log/battery_service/battery_service_main_history");
        this.mLedSettingsObserver = new AnonymousClass15(this, 3);
        this.mAdaptiveFastChargingSettingsObserver = new AnonymousClass15(this, 1);
        if (PowerManagerUtil.SEC_FEATURE_USE_SFC) {
            this.mSuperFastChargingSettingsObserver = new AnonymousClass15(this, 8);
        }
        if (PowerManagerUtil.SEC_FLOATING_FEATURE_BATTERY_SUPPORT_PASS_THROUGH) {
            this.mPassThroughSettingsObserver = new AnonymousClass15(this, 6);
        }
        boolean z = PowerManagerUtil.SEC_FEATURE_USE_WIRELESS_AFC;
        if (z) {
            this.mWirelessFastChargingSettingsObserver = new AnonymousClass15(this, 11);
            if (BattFeatures.SEC_FEATURE_WA_LCD_FLICKERING_WITH_VRR) {
                this.mRefreshRateModeSettingsObserver = new AnonymousClass15(this, 7);
            }
        }
        if (PowerManagerUtil.SEC_FEATURE_BATTERY_LIFE_EXTENDER) {
            this.mLifeExtenderSettingsObserver = new AnonymousClass15(this, 4);
        }
        if (PowerManagerUtil.SEC_FEATURE_BATTERY_FULL_CAPACITY || BattFeatures.SUPPORT_ECO_BATTERY) {
            this.mFullCapacityEnableSettingsObserver = new AnonymousClass15(this, 2);
        }
        boolean z2 = PowerManagerUtil.SEC_FEATURE_USE_WIRELESS_POWER_SHARING;
        if (z2) {
            broadcastReceiver = anonymousClass28;
            this.mTxBatteryLimitSettingsObserver = new AnonymousClass15(this, 9);
        } else {
            broadcastReceiver = anonymousClass28;
        }
        context.registerReceiver(anonymousClass2, new IntentFilter("android.intent.action.BOOT_COMPLETED"), 2);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        context.registerReceiver(anonymousClass22, intentFilter, 2);
        this.mBattInfoManager = new BattInfoManager(context);
        long readNodeAsLong = BattUtils.readNodeAsLong("/efs/FactoryApp/max_current");
        if (readNodeAsLong < 0) {
            j = -1;
            BattUtils.writeNode(-1L, "/efs/FactoryApp/max_current");
            j2 = -1;
        } else {
            j = -1;
            j2 = readNodeAsLong;
        }
        this.mSavedBatteryMaxCurrent = j2;
        long readNodeAsLong2 = BattUtils.readNodeAsLong("/efs/FactoryApp/max_temp");
        if (readNodeAsLong2 < 0) {
            BattUtils.writeNode(j, "/efs/FactoryApp/max_temp");
            j3 = j;
        } else {
            j3 = readNodeAsLong2;
        }
        this.mSavedBatteryMaxTemp = j3;
        if (BattHqmManager.mHqmEventReceiver != null) {
            Slog.w("[SS]BattHqmManager", "[hqmEventReceiver_onReceive]warn - mHqmEventReceiver already registered");
        } else {
            BattHqmManager.AnonymousClass1 anonymousClass1 = new BattHqmManager.AnonymousClass1();
            BattHqmManager.mHqmEventReceiver = anonymousClass1;
            context.registerReceiver(anonymousClass1, new IntentFilter("com.sec.android.intent.action.HQM_UPDATE_REQ"), 2);
        }
        BattHqmManager.mBattCallback = battCallbackImpl;
        BroadcastReceiver broadcastReceiver2 = broadcastReceiver;
        context.registerReceiver(anonymousClass23, BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.REQUEST_OTG_CHARGE_BLOCK"), "com.sec.permission.OTG_CHARGE_BLOCK", null, 2);
        IntentFilter m = BatteryService$$ExternalSyntheticOutline0.m("com.sec.intent.action.BATT_SLATE_MODE_CHANGE");
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(anonymousClass5, userHandle, m, "com.sec.permission.OTG_CHARGE_BLOCK", null, 2);
        if (FEATURE_HICCUP_CONTROL) {
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("com.samsung.systemui.power.action.WATER_POPUP_DISMISSED");
            intentFilter2.addAction("com.samsung.systemui.power.action.USB_DAMAGE_POPUP_SHOW");
            context.registerReceiver(anonymousClass24, intentFilter2, 2);
        }
        if (z2) {
            context.registerReceiverAsUser(anonymousClass25, userHandle, BatteryService$$ExternalSyntheticOutline0.m("com.samsung.android.sm.ACTION_WIRELESS_POWER_SHARING"), "com.samsung.android.permission.wirelesspowersharing", null, 2);
            if (PowerManagerUtil.SEC_FEATURE_DEX_DUAL_VIEW) {
                IntentFilter intentFilter3 = new IntentFilter();
                intentFilter3.addAction(ACTION_ENTER_DESK_MODE);
                intentFilter3.addAction(ACTION_EXIT_DESK_MODE);
                context.registerReceiverAsUser(anonymousClass26, userHandle, intentFilter3, null, null, 2);
            }
        }
        if (z) {
            context.registerReceiverAsUser(anonymousClass27, userHandle, BatteryService$$ExternalSyntheticOutline0.m("android.samsung.media.action.AUDIO_MODE"), null, null, 2);
            this.tm = (TelephonyManager) context.getSystemService("phone");
            HandlerThread handlerThread = new HandlerThread("CallThread");
            this.mCallHandlerThread = handlerThread;
            handlerThread.start();
            this.mCallHandler = new AnonymousClass1(this, this.mCallHandlerThread.getLooper(), 1);
        }
        if (PowerManagerUtil.SEC_FEATURE_SUPPORT_WIRELESS_NIGHT_MODE) {
            context.registerReceiverAsUser(broadcastReceiver2, userHandle, BatteryService$$ExternalSyntheticOutline0.m("com.samsung.android.sm.ACTION_FAST_WIRELESS_CHARGING_CONTROL"), "com.samsung.android.permission.FAST_WIRELESS_CHARGING_CONTROL", null, 2);
        }
        if (BatteryService$$ExternalSyntheticOutline0.m45m("/sys/devices/virtual/switch/invalid_charger/state")) {
            new UEventObserver() { // from class: com.android.server.BatteryService.13
                public final void onUEvent(UEventObserver.UEvent uEvent) {
                    boolean equals = "1".equals(uEvent.get("SWITCH_STATE"));
                    synchronized (BatteryService.this.mLock) {
                        BatteryService batteryService = BatteryService.this;
                        if (batteryService.mInvalidCharger != equals) {
                            batteryService.mInvalidCharger = equals ? 1 : 0;
                        }
                    }
                }
            }.startObserving("DEVPATH=/devices/virtual/switch/invalid_charger");
        }
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null && packageManager.hasSystemFeature("att.devicehealth.support")) {
            this.mEnableIqi = true;
        }
        if (PowerManagerUtil.SEC_FEATURE_USE_AFC) {
            String[] strArr = ADAPTIVE_FAST_CHARGING_DISABLE_SYSFS_PATHS;
            String str = isFileSupported(strArr[0]) ? strArr[0] : strArr[1];
            this.mAfcDisableSysFs = str;
            Slog.d("BatteryService", "!@ mAfcDisableSysFs : " + str);
        }
        int i2 = SystemProperties.getInt("ro.boot.cm.param.offset", -1);
        this.mWirelessFastChargingOffset = i2;
        this.mAdaptiveFastChargingOffset = i2 != -1 ? i2 + 1 : -1;
        this.mSuperFastChargingOffset = SystemProperties.getInt("ro.boot.pd.param.offset", -1);
        int i3 = SystemProperties.getInt("ro.boot.wc.param.offset", -1);
        this.mWcParamOffset = i3;
        String readNode = BattUtils.readNode("/sys/class/power_supply/battery/wc_param_info", true);
        if (i3 != -1 && !readNode.isEmpty()) {
            this.mWcParamInfoSettingsObserver = new AnonymousClass15(this, 10);
        }
        this.mBatteryInputSuspended = ((Boolean) PowerProperties.battery_input_suspended().orElse(Boolean.FALSE)).booleanValue();
        BatteryLogger.writeToFile("/data/log/battery_service/sleep_charging_history", "BatteryService Created", "");
        this.mSkipActionBatteryChangedHandler = new AnonymousClass1(this, Looper.myLooper(), 2);
    }

    public static boolean isFileSupported(String str) {
        if (BatteryService$$ExternalSyntheticOutline0.m45m(str)) {
            return true;
        }
        Slog.d("BatteryService", str + " is not found");
        return false;
    }

    public static int parseOptions(Shell shell) {
        int i = 0;
        while (true) {
            String nextOption = shell.getNextOption();
            if (nextOption == null) {
                return i;
            }
            if ("-f".equals(nextOption)) {
                i = 1;
            }
        }
    }

    public static int plugType(SehHealthInfo sehHealthInfo) {
        HealthInfo healthInfo = sehHealthInfo.aospHealthInfo;
        if (healthInfo.chargerAcOnline) {
            return 1;
        }
        if (healthInfo.chargerWirelessOnline) {
            return 4;
        }
        if (healthInfo.chargerUsbOnline) {
            return 2;
        }
        if (sehHealthInfo.chargerPogoOnline) {
            return 1;
        }
        return healthInfo.chargerDockOnline ? 8 : 0;
    }

    public static void setPassThrough(boolean z) {
        if (!BattUtils.writeNode("/sys/class/power_supply/battery/pass_through", z)) {
            Slog.d("BatteryService", "fail to set PassThrough sysfs");
            return;
        }
        Slog.d("BatteryService", "success to set PassThrough sysfs as " + z);
    }

    public final boolean isPoweredLocked(int i) {
        HealthInfo healthInfo = this.mHealthInfo;
        if (healthInfo.batteryStatus == 1) {
            return true;
        }
        int i2 = i & 1;
        if (i2 != 0 && healthInfo.chargerAcOnline) {
            return true;
        }
        if ((i & 2) != 0 && healthInfo.chargerUsbOnline) {
            return true;
        }
        if ((i & 4) != 0 && healthInfo.chargerWirelessOnline) {
            return true;
        }
        if ((i & 8) == 0 || !healthInfo.chargerDockOnline) {
            return i2 != 0 && this.mSehHealthInfo.chargerPogoOnline;
        }
        return true;
    }

    public final void logBatteryStatsLocked() {
        DropBoxManager dropBoxManager;
        File file;
        StringBuilder sb;
        FileOutputStream fileOutputStream;
        IBinder service = ServiceManager.getService("batterystats");
        if (service == null || (dropBoxManager = (DropBoxManager) this.mContext.getSystemService("dropbox")) == null || !dropBoxManager.isTagEnabled("BATTERY_DISCHARGE_INFO")) {
            return;
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                file = new File("/data/system/batterystats.dump");
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (RemoteException e) {
                    e = e;
                } catch (IOException e2) {
                    e = e2;
                }
                try {
                    service.dump(fileOutputStream.getFD(), DUMPSYS_ARGS);
                    FileUtils.sync(fileOutputStream);
                    dropBoxManager.addFile("BATTERY_DISCHARGE_INFO", file, 2);
                    try {
                        fileOutputStream.close();
                    } catch (IOException unused) {
                        Slog.e("BatteryService", "failed to close dumpsys output stream");
                    }
                } catch (RemoteException e3) {
                    e = e3;
                    fileOutputStream2 = fileOutputStream;
                    Slog.e("BatteryService", "failed to dump battery service", e);
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException unused2) {
                            Slog.e("BatteryService", "failed to close dumpsys output stream");
                        }
                    }
                    if (file == null || file.delete()) {
                        return;
                    }
                    sb = new StringBuilder("failed to delete temporary dumpsys file: ");
                    sb.append(file.getAbsolutePath());
                    Slog.e("BatteryService", sb.toString());
                } catch (IOException e4) {
                    e = e4;
                    fileOutputStream2 = fileOutputStream;
                    Slog.e("BatteryService", "failed to write dumpsys file", e);
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException unused3) {
                            Slog.e("BatteryService", "failed to close dumpsys output stream");
                        }
                    }
                    if (file == null || file.delete()) {
                        return;
                    }
                    sb = new StringBuilder("failed to delete temporary dumpsys file: ");
                    sb.append(file.getAbsolutePath());
                    Slog.e("BatteryService", sb.toString());
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream2 = fileOutputStream;
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException unused4) {
                            Slog.e("BatteryService", "failed to close dumpsys output stream");
                        }
                    }
                    if (file == null) {
                        throw th;
                    }
                    if (file.delete()) {
                        throw th;
                    }
                    Slog.e("BatteryService", "failed to delete temporary dumpsys file: " + file.getAbsolutePath());
                    throw th;
                }
            } catch (RemoteException e5) {
                e = e5;
                file = null;
            } catch (IOException e6) {
                e = e6;
                file = null;
            } catch (Throwable th2) {
                th = th2;
                file = null;
            }
            if (file.delete()) {
                return;
            }
            sb = new StringBuilder("failed to delete temporary dumpsys file: ");
            sb.append(file.getAbsolutePath());
            Slog.e("BatteryService", sb.toString());
        } catch (Throwable th3) {
            th = th3;
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        int i2;
        Slog.d("BatteryService", "[onBootPhase]phase:" + i);
        if (i == 550) {
            synchronized (this.mLock) {
                try {
                    this.mActivityManagerReady = true;
                    AnonymousClass15 anonymousClass15 = new AnonymousClass15(this, this.mHandler);
                    ContentResolver contentResolver = this.mContext.getContentResolver();
                    contentResolver.registerContentObserver(Settings.Global.getUriFor("low_power_trigger_level"), false, anonymousClass15, -1);
                    updateBatteryWarningLevelLocked();
                    registerContentObserver(contentResolver);
                    SemInputDeviceManager semInputDeviceManager = (SemInputDeviceManager) this.mContext.getSystemService("SemInputDeviceManagerService");
                    this.mSemInputDeviceManager = semInputDeviceManager;
                    if (semInputDeviceManager != null && (i2 = this.mLatestWirelessChargingMode) != 0) {
                        semInputDeviceManager.setWirelessChargingMode(11, i2);
                        this.mSemInputDeviceManager.setWirelessChargingMode(1, this.mLatestWirelessChargingMode);
                        Slog.d("BatteryService", "setWirelessChargingMode(All): " + this.mLatestWirelessChargingMode);
                    }
                } finally {
                }
            }
            return;
        }
        if (i == 1000) {
            ContentResolver contentResolver2 = this.mContext.getContentResolver();
            this.mHandler.post(new AnonymousClass16(this, 0));
            synchronized (this.mLock) {
                sendDeteriorationIntentLocked(true);
            }
            if (this.mEnableIqi) {
                boolean z = SystemProperties.getBoolean("persist.sys.shutdown_received", true);
                SystemProperties.set("persist.sys.shutdown_received", "false");
                if (!z) {
                    this.mHandler.postDelayed(new AnonymousClass17(), 50000L);
                }
            }
            if (FEATURE_HICCUP_CONTROL && this.mIsHiccupPopupShowing) {
                int i3 = this.mLastBatteryEvent;
                if ((i3 & 32) != 0 || (i3 & 8192) != 0) {
                    Slog.d("BatteryService", "bootCompleted and HiccupPopup");
                    BattUtils.writeNode(this.mLastBatteryEvent, "/sys/class/power_supply/battery/batt_misc_event");
                }
            }
            if (PowerManagerUtil.SEC_FEATURE_USE_WIRELESS_POWER_SHARING) {
                InputManager inputManager = InputManager.getInstance();
                this.mIsWirelessTxSupported = (inputManager == null || (inputManager.semCheckInputFeature() & 16) == 0) ? false : true;
            }
            if (PowerManagerUtil.SEC_FEATURE_BATTERY_LIFE_EXTENDER) {
                this.mLifeExtender = Settings.System.getIntForUser(contentResolver2, "protect_battery", 0, -2) == 1;
                Slog.d("BatteryService", "!@mLifeExtender Settings = " + this.mLifeExtender + " mLifeExtenderSettingsObserver register");
                contentResolver2.registerContentObserver(Settings.System.getUriFor("protect_battery"), false, this.mLifeExtenderSettingsObserver, -1);
                this.mHandlerForBatteryInfoBackUp.post(this.mUpdateBatteryUsageExtenderRunnable);
            }
        }
    }

    /* JADX WARN: Finally extract failed */
    @Override // com.android.server.SystemService
    public final void onStart() {
        Trace.traceBegin(524288L, "HealthInitWrapper");
        try {
            try {
                try {
                    this.mHealthServiceWrapper = HealthServiceWrapper.create(new BatteryService$$ExternalSyntheticLambda11(this));
                    Trace.traceEnd(524288L);
                    Trace.traceBegin(524288L, "HealthInitWaitUpdate");
                    long uptimeMillis = SystemClock.uptimeMillis();
                    synchronized (this.mLock) {
                        while (this.mHealthInfo == null) {
                            Slog.i("BatteryService", "health: Waited " + (SystemClock.uptimeMillis() - uptimeMillis) + "ms for callbacks. Waiting another 1000 ms...");
                            try {
                                this.mLock.wait(1000L);
                            } catch (InterruptedException unused) {
                                Slog.i("BatteryService", "health: InterruptedException when waiting for update.  Continuing...");
                            }
                        }
                    }
                    Slog.i("BatteryService", "health: Waited " + (SystemClock.uptimeMillis() - uptimeMillis) + "ms and received the update.");
                    Trace.traceEnd(524288L);
                    BinderService binderService = new BinderService();
                    this.mBinderService = binderService;
                    publishBinderService("battery", binderService);
                    publishBinderService("batteryproperties", new BatteryPropertiesRegistrar());
                    publishLocalService(BatteryManagerInternal.class, new LocalService());
                    if (isFileSupported("/efs/FactoryApp/batt_after_manufactured")) {
                        String readNode = BattUtils.readNode("/efs/FactoryApp/batt_after_manufactured", true);
                        try {
                            if (!readNode.isEmpty() && Integer.parseInt(readNode) >= 0) {
                                Slog.d("BatteryService", "!@[LLB] Write weeklyDiff EFS ->  Sys : ".concat(readNode));
                                BattUtils.writeNode(Integer.parseInt(readNode), "/sys/class/power_supply/battery/batt_after_manufactured");
                                this.mSavedDiffWeek = Integer.parseInt(readNode);
                            }
                        } catch (NumberFormatException e) {
                            Slog.d("BatteryService", "!@[LLB] can not change. value: " + readNode + ", e: " + e);
                            return;
                        }
                    } else {
                        BattUtils.writeNode(0L, "/efs/FactoryApp/batt_after_manufactured");
                        BattUtils.writeNode(0L, "/sys/class/power_supply/battery/batt_after_manufactured");
                        this.mSavedDiffWeek = 0;
                    }
                    this.mHandler.post(new AnonymousClass16(this, 1));
                } catch (NoSuchElementException e2) {
                    Slog.e("BatteryService", "health: cannot register callback. (no supported health HAL service)");
                    throw e2;
                }
            } catch (RemoteException e3) {
                Slog.e("BatteryService", "health: cannot register callback. (RemoteException)");
                throw e3.rethrowFromSystemServer();
            }
        } catch (Throwable th) {
            Trace.traceEnd(524288L);
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x00c6, code lost:
    
        if (r2 == 1) goto L44;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0a7b  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0aab  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0aba  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0ac1  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x0ad1  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0ae4  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0af6  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0b05  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0b12  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0b19  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0b26  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0b2d  */
    /* JADX WARN: Removed duplicated region for block: B:185:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0b28  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0b14  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0ab5  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x03b1  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x03ba  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x03c3  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x03cc  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x03d5  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0404  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x040d  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x0418  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x0423  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x042e  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x0439  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x0444  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x044f  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x0458  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x0463  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x046e  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0479  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x0482  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x048d  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x04f0  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x04fe A[EDGE_INSN: B:267:0x04fe->B:268:0x04fe BREAK  A[LOOP:0: B:258:0x04ea->B:264:0x04fc], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:270:0x051b  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x05f0  */
    /* JADX WARN: Removed duplicated region for block: B:285:0x0600  */
    /* JADX WARN: Removed duplicated region for block: B:293:0x065b  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x067c  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x0695  */
    /* JADX WARN: Removed duplicated region for block: B:312:0x06cf  */
    /* JADX WARN: Removed duplicated region for block: B:319:0x0704  */
    /* JADX WARN: Removed duplicated region for block: B:326:0x074f  */
    /* JADX WARN: Removed duplicated region for block: B:329:0x0756  */
    /* JADX WARN: Removed duplicated region for block: B:331:0x075b  */
    /* JADX WARN: Removed duplicated region for block: B:344:0x07bf  */
    /* JADX WARN: Removed duplicated region for block: B:354:0x07ec  */
    /* JADX WARN: Removed duplicated region for block: B:357:0x07f7  */
    /* JADX WARN: Removed duplicated region for block: B:375:0x08a4  */
    /* JADX WARN: Removed duplicated region for block: B:384:0x08d5  */
    /* JADX WARN: Removed duplicated region for block: B:389:0x09a3  */
    /* JADX WARN: Removed duplicated region for block: B:393:0x09b3  */
    /* JADX WARN: Removed duplicated region for block: B:404:0x09c2  */
    /* JADX WARN: Removed duplicated region for block: B:407:0x09ff  */
    /* JADX WARN: Removed duplicated region for block: B:411:0x0977  */
    /* JADX WARN: Removed duplicated region for block: B:413:0x097e  */
    /* JADX WARN: Removed duplicated region for block: B:420:0x0866  */
    /* JADX WARN: Removed duplicated region for block: B:422:0x0868  */
    /* JADX WARN: Removed duplicated region for block: B:425:0x07e1  */
    /* JADX WARN: Removed duplicated region for block: B:428:0x0793  */
    /* JADX WARN: Removed duplicated region for block: B:431:0x0758  */
    /* JADX WARN: Removed duplicated region for block: B:432:0x0751  */
    /* JADX WARN: Removed duplicated region for block: B:435:0x072c  */
    /* JADX WARN: Removed duplicated region for block: B:441:0x06a9  */
    /* JADX WARN: Removed duplicated region for block: B:464:0x0491  */
    /* JADX WARN: Removed duplicated region for block: B:465:0x0484  */
    /* JADX WARN: Removed duplicated region for block: B:466:0x047b  */
    /* JADX WARN: Removed duplicated region for block: B:467:0x0470  */
    /* JADX WARN: Removed duplicated region for block: B:468:0x0465  */
    /* JADX WARN: Removed duplicated region for block: B:469:0x045a  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:470:0x0451  */
    /* JADX WARN: Removed duplicated region for block: B:471:0x0446  */
    /* JADX WARN: Removed duplicated region for block: B:472:0x043b  */
    /* JADX WARN: Removed duplicated region for block: B:473:0x0430  */
    /* JADX WARN: Removed duplicated region for block: B:474:0x0425  */
    /* JADX WARN: Removed duplicated region for block: B:475:0x041a  */
    /* JADX WARN: Removed duplicated region for block: B:476:0x040f  */
    /* JADX WARN: Removed duplicated region for block: B:477:0x0406  */
    /* JADX WARN: Removed duplicated region for block: B:480:0x03d7  */
    /* JADX WARN: Removed duplicated region for block: B:481:0x03ce  */
    /* JADX WARN: Removed duplicated region for block: B:482:0x03c5  */
    /* JADX WARN: Removed duplicated region for block: B:483:0x03bc  */
    /* JADX WARN: Removed duplicated region for block: B:484:0x03b3  */
    /* JADX WARN: Removed duplicated region for block: B:485:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:489:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x02aa  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x02e0  */
    /* JADX WARN: Type inference failed for: r8v120 */
    /* JADX WARN: Type inference failed for: r8v121, types: [android.os.Handler, android.os.HandlerThread, com.android.server.battery.batteryInfo.FirstUseDateData$DateChangedReceiver, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v122 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void processValuesLocked(boolean r36) {
        /*
            Method dump skipped, instructions count: 2911
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.BatteryService.processValuesLocked(boolean):void");
    }

    public final void registerContentObserver(ContentResolver contentResolver) {
        this.mLedChargingSettingsEnable = Settings.System.getIntForUser(contentResolver, "led_indicator_charing", 1, -2) == 1;
        this.mLedLowBatterySettingsEnable = Settings.System.getIntForUser(contentResolver, "led_indicator_low_battery", 1, -2) == 1;
        Slog.d("BatteryService", "!@Led Charging Settings = " + this.mLedChargingSettingsEnable);
        Slog.d("BatteryService", "!@Led Low Battery Settings = " + this.mLedLowBatterySettingsEnable);
        Uri uriFor = Settings.System.getUriFor("led_indicator_charing");
        AnonymousClass15 anonymousClass15 = this.mLedSettingsObserver;
        contentResolver.registerContentObserver(uriFor, false, anonymousClass15, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("led_indicator_low_battery"), false, anonymousClass15, -1);
        if (PowerManagerUtil.SEC_FEATURE_USE_AFC) {
            updateAdaptiveFastChargingSetting(contentResolver);
            contentResolver.registerContentObserver(Settings.System.getUriFor("adaptive_fast_charging"), false, this.mAdaptiveFastChargingSettingsObserver, -1);
        }
        if (PowerManagerUtil.SEC_FEATURE_USE_SFC) {
            this.mSuperFastChargingSettingsEnable = Settings.System.getIntForUser(contentResolver, "super_fast_charging", 1, -2) == 1;
            Slog.d("BatteryService", "!@SuperFastCharging Settings = " + this.mSuperFastChargingSettingsEnable);
            setSuperFastCharging(this.mSuperFastChargingSettingsEnable);
            contentResolver.registerContentObserver(Settings.System.getUriFor("super_fast_charging"), false, this.mSuperFastChargingSettingsObserver, -1);
        }
        if (PowerManagerUtil.SEC_FEATURE_USE_WIRELESS_AFC) {
            this.mWasUsedWirelessFastChargerPreviously = Settings.System.getIntForUser(contentResolver, "show_wireless_charger_menu", 0, -2) == 1;
            this.mWirelessFastChargingSettingsEnable = Settings.System.getIntForUser(contentResolver, "wireless_fast_charging", 1, -2) == 1;
            Slog.d("BatteryService", "!@WirelessFastCharging Settings = " + this.mWirelessFastChargingSettingsEnable);
            setWirelessFastCharging(this.mWirelessFastChargingSettingsEnable);
            contentResolver.registerContentObserver(Settings.System.getUriFor("wireless_fast_charging"), false, this.mWirelessFastChargingSettingsObserver, -1);
            if (BattFeatures.SEC_FEATURE_WA_LCD_FLICKERING_WITH_VRR) {
                this.mRefreshRateModeSetting = Settings.Secure.getIntForUser(contentResolver, "refresh_rate_mode", 0, -2);
                BatteryService$$ExternalSyntheticOutline0.m(new StringBuilder("!@RefreshRateMode Setting = "), this.mRefreshRateModeSetting, "BatteryService");
                if (this.mRefreshRateModeSetting == 0) {
                    BattUtils.writeNode(5L, "/sys/class/power_supply/battery/batt_hv_wireless_pad_ctrl");
                } else {
                    BattUtils.writeNode(6L, "/sys/class/power_supply/battery/batt_hv_wireless_pad_ctrl");
                }
                contentResolver.registerContentObserver(Settings.Secure.getUriFor("refresh_rate_mode"), false, this.mRefreshRateModeSettingsObserver, -1);
            }
        }
        if (PowerManagerUtil.SEC_FLOATING_FEATURE_BATTERY_SUPPORT_PASS_THROUGH) {
            this.mPassThroughSettingsEnable = Settings.System.getIntForUser(contentResolver, "pass_through", 0, -2) == 1;
            Slog.d("BatteryService", "!@mPassThroughSettingsEnable Settings = " + this.mPassThroughSettingsEnable);
            setPassThrough(this.mPassThroughSettingsEnable);
            contentResolver.registerContentObserver(Settings.System.getUriFor("pass_through"), false, this.mPassThroughSettingsObserver, -1);
        }
        boolean z = BattFeatures.SUPPORT_ECO_BATTERY;
        AnonymousClass15 anonymousClass152 = this.mFullCapacityEnableSettingsObserver;
        if (z) {
            contentResolver.registerContentObserver(Settings.Global.getUriFor("protect_battery"), false, anonymousClass152, -1);
            ContentResolver contentResolver2 = this.mContext.getContentResolver();
            this.mProtectBatteryMode = Settings.Global.getInt(contentResolver2, "protect_battery", 0);
            Slog.d(TAG_SS, "mProtectBatteryMode:" + this.mProtectBatteryMode);
            int i = Settings.Global.BATTERY_PROTECTION_THRESHOLD_DEFAULT_VALUE;
            this.mProtectionThreshold = i;
            BatteryService$$ExternalSyntheticOutline0.m(new StringBuilder("mProtectionThreshold:"), this.mProtectionThreshold, "BatteryService");
            this.mMaximumProtectionThreshold = Settings.Global.getInt(contentResolver2, "battery_protection_threshold", i);
            Slog.d("BatteryService", "mMaximumProtectionThreshold:" + this.mMaximumProtectionThreshold);
            this.mMaximumProtectionThresholdObserver = new AnonymousClass15(this, 5);
            contentResolver.registerContentObserver(Settings.Global.getUriFor("battery_protection_threshold"), false, this.mMaximumProtectionThresholdObserver, -1);
            this.mLtcHighThreshold = 95;
            BatteryService$$ExternalSyntheticOutline0.m(new StringBuilder("mLtcHighThreshold:"), this.mLtcHighThreshold, "BatteryService");
            BattUtils.writeNode(this.mLtcHighThreshold, "/efs/Battery/batt_ltc_highsoc_threshold");
            this.mLtcHighSocDuration = FrameworkStatsLog.SETTING_SNAPSHOT;
            BatteryService$$ExternalSyntheticOutline0.m(new StringBuilder("mLtcHighSocDuration:"), this.mLtcHighSocDuration, "BatteryService");
            BattUtils.writeNode(this.mLtcHighSocDuration, "/efs/Battery/batt_ltc_highsoc_duration");
            this.mLtcReleaseThreshold = 75;
            BatteryService$$ExternalSyntheticOutline0.m(new StringBuilder("mLtcReleaseThreshold:"), this.mLtcReleaseThreshold, "BatteryService");
            BattUtils.writeNode(this.mLtcReleaseThreshold, "/efs/Battery/batt_ltc_release_threshold");
            writeProtectBatteryValues();
        } else if (PowerManagerUtil.SEC_FEATURE_BATTERY_FULL_CAPACITY) {
            this.mLifeExtender = Settings.System.getIntForUser(contentResolver, "protect_battery", 0, -2) == 1;
            Handler handler = this.mHandlerForBatteryInfoBackUp;
            handler.post(this.mUpdateBatteryUsageExtenderRunnable);
            this.mFullCapacityEnable = Settings.Global.getInt(contentResolver, "protect_battery", 0) == 1;
            Slog.d("BatteryService", "!@mFullCapacityEnable Settings = " + this.mFullCapacityEnable);
            handler.post(this.mUpdateBatteryUsageFullCapacityEnableRunnable);
            contentResolver.registerContentObserver(Settings.Global.getUriFor("protect_battery"), false, anonymousClass152, -1);
        }
        if (PowerManagerUtil.SEC_FEATURE_USE_WIRELESS_POWER_SHARING) {
            this.mTxBatteryLimit = Settings.System.getIntForUser(contentResolver, "tx_battery_limit", 30, -2);
            BatteryService$$ExternalSyntheticOutline0.m(new StringBuilder("!@Tx Battery Limit Settings = "), this.mTxBatteryLimit, "BatteryService");
            BattUtils.writeNode(this.mTxBatteryLimit, "/sys/class/power_supply/battery/wc_tx_stop_capacity");
            contentResolver.registerContentObserver(Settings.System.getUriFor("tx_battery_limit"), false, this.mTxBatteryLimitSettingsObserver, -1);
        }
        String readNode = BattUtils.readNode("/sys/class/power_supply/battery/wc_param_info", true);
        if (this.mWcParamOffset == -1 || readNode.isEmpty()) {
            return;
        }
        Settings.System.putInt(this.mContext.getContentResolver(), "wireless_wc_write", 0);
        contentResolver.registerContentObserver(Settings.System.getUriFor("wireless_wc_write"), false, this.mWcParamInfoSettingsObserver, -1);
        this.mHealthServiceWrapper.sehWriteEnableToParam(-2, "wc param", true);
        Settings.System.putInt(this.mContext.getContentResolver(), "wireless_wc_write", 0);
    }

    public final void resetBattery(PrintWriter printWriter, boolean z) {
        if (this.mUpdatesStopped) {
            this.mUpdatesStopped = false;
            Utils.copySehV1Battery(this.mSehHealthInfo, this.mLastSehHealthInfo);
            Binder.withCleanCallingIdentity(new BatteryService$$ExternalSyntheticLambda9(this, z, printWriter, 1));
        }
        if (this.mBatteryInputSuspended) {
            PowerProperties.battery_input_suspended(Boolean.FALSE);
            this.mBatteryInputSuspended = false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x00ca, code lost:
    
        if (r13.mHealthInfo.batteryLevel >= 100) goto L41;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void sendBatteryChangedIntentLocked() {
        /*
            Method dump skipped, instructions count: 775
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.BatteryService.sendBatteryChangedIntentLocked():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void sendDeteriorationIntentLocked(boolean r17) {
        /*
            Method dump skipped, instructions count: 267
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.BatteryService.sendDeteriorationIntentLocked(boolean):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v8 */
    public final void sendWirelessPowerSharingIntentLocked() {
        SemInputDeviceManager semInputDeviceManager;
        synchronized (this.mLock) {
            try {
                int i = this.mSehHealthInfo.wirelessPowerSharingTxEvent;
                final boolean z = true;
                ?? r4 = (i & 1) != 0 ? 1 : 0;
                if (this.mLastTxEventTxEnabled != r4) {
                    Intent intent = new Intent("com.samsung.server.BatteryService.action.WIRELESS_POWER_SHARING_ENABLED");
                    intent.putExtra("enabled", (boolean) r4);
                    intent.addFlags(268435456);
                    intent.setPackage(PACKAGE_DEVICE_CARE);
                    this.mHandler.post(new AnonymousClass27(1, intent, r4));
                    this.mLastTxEventTxEnabled = r4;
                    this.mLatestWirelessChargingMode = r4;
                    SemInputDeviceManager semInputDeviceManager2 = this.mSemInputDeviceManager;
                    if (semInputDeviceManager2 != null) {
                        semInputDeviceManager2.setWirelessChargingMode(11, (int) r4);
                        Slog.d("BatteryService", "setWirelessChargingMode(DEVID_SPEN): " + this.mLatestWirelessChargingMode);
                    }
                    if (this.mIsWirelessTxSupported && this.mBootCompleted && (semInputDeviceManager = this.mSemInputDeviceManager) != null) {
                        semInputDeviceManager.setWirelessChargingMode(1, this.mLatestWirelessChargingMode);
                        Slog.d("BatteryService", "setWirelessChargingMode(TSP): " + this.mLatestWirelessChargingMode);
                    }
                }
                if ((i & 2) == 0) {
                    z = false;
                }
                if (this.mLastTxEventRxConnected != z) {
                    final Intent intent2 = new Intent("com.samsung.server.BatteryService.action.WIRELESS_POWER_SHARING_CONNECTED");
                    intent2.putExtra("connected", z);
                    intent2.addFlags(268435456);
                    intent2.setPackage(PACKAGE_DEVICE_CARE);
                    this.mHandler.post(new Runnable() { // from class: com.android.server.BatteryService.33
                        @Override // java.lang.Runnable
                        public final void run() {
                            String str = "Sending ACTION_WIRELESS_POWER_SHARING_CONNECTED. connected : " + z;
                            String str2 = BatteryService.TAG_SS;
                            Slog.d("BatteryService", str);
                            BatteryService.this.mContext.sendBroadcastAsUser(intent2, UserHandle.ALL);
                            BattLogBuffer.addLog(1, str);
                            try {
                                if (z) {
                                    BatteryService.this.mBatteryStats.noteStartTxPowerSharing();
                                } else {
                                    BatteryService.this.mBatteryStats.noteStopTxPowerSharing();
                                }
                            } catch (RemoteException unused) {
                                String str3 = BatteryService.TAG_SS;
                                Slog.e("BatteryService", "Failed to note battery stats in BatteryService");
                            }
                        }
                    });
                    this.mLastTxEventRxConnected = z;
                }
                if (i != this.mLastWirelessPowerSharingTxEvent) {
                    Intent intent3 = new Intent("com.samsung.server.BatteryService.action.WIRELESS_POWER_SHARING_TX_EVENT");
                    intent3.putExtra("tx_event", i);
                    intent3.addFlags(268435456);
                    this.mHandler.post(new AnonymousClass34(i, intent3));
                    this.mLastWirelessPowerSharingTxEvent = i;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setAdaptiveFastCharging(boolean z) {
        this.mHealthServiceWrapper.sehWriteEnableToParam(this.mAdaptiveFastChargingOffset, "afc", !z);
        if (!BattUtils.writeNode(this.mAfcDisableSysFs, !z)) {
            Slog.d("BatteryService", "fail to set AFC sysfs");
            return;
        }
        Slog.d("BatteryService", "success to set AFC sysfs as " + z);
    }

    public final void setSuperFastCharging(boolean z) {
        this.mHealthServiceWrapper.sehWriteEnableToParam(this.mSuperFastChargingOffset, "sfc", !z);
        if (!BattUtils.writeNode("/sys/class/power_supply/battery/pd_disable", !z)) {
            Slog.d("BatteryService", "fail to set SFC sysfs");
            return;
        }
        Slog.d("BatteryService", "success to set SFC sysfs as " + z);
    }

    public final void setWirelessChargingState(boolean z) {
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("wirelessChargingState: ", ", notifyWirelessEnabled: ", z);
        m.append(this.mNotifyWirelessEnabled);
        Slog.d("BatteryService", m.toString());
        if (z && !this.mNotifyWirelessEnabled) {
            Slog.d("BatteryService", "notify wireless on");
            this.mLatestWirelessChargingMode = 1;
            this.mNotifyWirelessEnabled = true;
            BattUtils.writeNode("/sys/class/sec/switch/wireless", true);
        } else if (!z && this.mNotifyWirelessEnabled && !this.mLastWirelessChargingStatus && !this.mLastWirelessPinDetected) {
            Slog.d("BatteryService", "notify wireless off");
            this.mLatestWirelessChargingMode = 0;
            this.mNotifyWirelessEnabled = false;
            BattUtils.writeNode("/sys/class/sec/switch/wireless", "0");
        }
        SemInputDeviceManager semInputDeviceManager = this.mSemInputDeviceManager;
        if (semInputDeviceManager != null) {
            semInputDeviceManager.setWirelessChargingMode(11, this.mLatestWirelessChargingMode);
            this.mSemInputDeviceManager.setWirelessChargingMode(1, this.mLatestWirelessChargingMode);
            BatteryService$$ExternalSyntheticOutline0.m(new StringBuilder("setWirelessChargingMode(All): "), this.mLatestWirelessChargingMode, "BatteryService");
        }
    }

    public final void setWirelessFastCharging(boolean z) {
        int i = this.mWirelessFastChargingOffset;
        if (i != -1) {
            this.mHealthServiceWrapper.sehWriteEnableToParam(i, "wfc", !z);
        }
        if (z) {
            BattUtils.writeNode(2L, "/sys/class/power_supply/battery/batt_hv_wireless_pad_ctrl");
        } else {
            BattUtils.writeNode(1L, "/sys/class/power_supply/battery/batt_hv_wireless_pad_ctrl");
        }
    }

    public final void setWirelessPowerSharingExternelEventInternal(int i, int i2) {
        synchronized (this.mLock) {
            try {
                Slog.i("BatteryService", "setWirelessPowerSharingExternelEventInternal packageNum: " + i + " value: " + i2);
                int i3 = this.mLastWirelessPowerSharingExternelEvent;
                int i4 = ((~i) & i3) | i2;
                if (i4 != i3) {
                    BattUtils.writeNode(i4, "/sys/class/power_supply/battery/ext_event");
                    this.mLastWirelessPowerSharingExternelEvent = i4;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unplugBattery(PrintWriter printWriter, boolean z) {
        if (!this.mUpdatesStopped) {
            Utils.copySehV1Battery(this.mLastSehHealthInfo, this.mSehHealthInfo);
        }
        HealthInfo healthInfo = this.mHealthInfo;
        healthInfo.chargerAcOnline = false;
        healthInfo.chargerUsbOnline = false;
        healthInfo.chargerWirelessOnline = false;
        healthInfo.chargerDockOnline = false;
        this.mUpdatesStopped = true;
        Binder.withCleanCallingIdentity(new BatteryService$$ExternalSyntheticLambda9(this, z, printWriter, 0));
    }

    public final void updateAdaptiveFastChargingSetting(ContentResolver contentResolver) {
        this.mAdaptiveFastChargingSettingsEnable = Settings.System.getIntForUser(contentResolver, "adaptive_fast_charging", 1, -2) == 1;
        Slog.d("BatteryService", "!@AdaptiveFastCharging Settings = " + this.mAdaptiveFastChargingSettingsEnable);
        setAdaptiveFastCharging(this.mAdaptiveFastChargingSettingsEnable);
    }

    public final void updateBatteryWarningLevelLocked() {
        this.mContext.getContentResolver();
        this.mContext.getResources().getInteger(R.integer.config_nightDisplayColorTemperatureMin);
        int i = this.mLowBatteryWarningLevel;
        this.mLastLowBatteryWarningLevel = i;
        int i2 = this.mCriticalBatteryLevel;
        if (i < i2) {
            this.mLowBatteryWarningLevel = i2;
        }
        this.mLowBatteryCloseWarningLevel = this.mContext.getResources().getInteger(R.integer.config_nightDisplayColorTemperatureMax) + this.mLowBatteryWarningLevel;
        processValuesLocked(true);
    }

    public final void writeProtectBatteryValues() {
        Slog.i(TAG_SS, "writeProtectBatteryValues:" + this.mProtectBatteryMode);
        int i = this.mProtectBatteryMode;
        if (i == 0) {
            BattUtils.writeNode(100L, "/sys/class/power_supply/battery/batt_full_capacity");
            BattUtils.writeNode(100L, "/efs/Battery/batt_full_capacity");
            BattUtils.writeNode(0L, "/sys/class/power_supply/battery/batt_soc_rechg");
            BattUtils.writeNode(0L, "/efs/Battery/batt_soc_rechg");
            return;
        }
        if (i == 1) {
            BattUtils.writeNode("/sys/class/power_supply/battery/batt_full_capacity", this.mMaximumProtectionThreshold + " OPTION");
            BattUtils.writeNode((long) this.mMaximumProtectionThreshold, "/efs/Battery/batt_full_capacity");
            BattUtils.writeNode(0L, "/sys/class/power_supply/battery/batt_soc_rechg");
            BattUtils.writeNode(0L, "/efs/Battery/batt_soc_rechg");
            return;
        }
        if (i == 2) {
            BattUtils.writeNode("/sys/class/power_supply/battery/batt_full_capacity", this.mProtectionThreshold + " HIGHSOC");
            BattUtils.writeNode(0L, "/sys/class/power_supply/battery/batt_soc_rechg");
            BattUtils.writeNode(0L, "/efs/Battery/batt_soc_rechg");
            return;
        }
        if (i == 3 || i == 4) {
            BattUtils.writeNode(100L, "/sys/class/power_supply/battery/batt_full_capacity");
            BattUtils.writeNode(100L, "/efs/Battery/batt_full_capacity");
            BattUtils.writeNode(1L, "/sys/class/power_supply/battery/batt_soc_rechg");
            BattUtils.writeNode(1L, "/efs/Battery/batt_soc_rechg");
        }
    }
}
