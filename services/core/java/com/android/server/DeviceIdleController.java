package com.android.server;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AlarmManager;
import android.app.BroadcastOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.net.ConnectivityManager;
import android.net.INetworkPolicyManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.BatteryManagerInternal;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IDeviceIdleController;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.PowerSaveState;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.telephony.emergency.EmergencyNumber;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.MutableLong;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.TimeUtils;
import android.util.Xml;
import com.android.internal.app.IBatteryStats;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.RingBuffer;
import com.android.internal.util.jobs.ArrayUtils;
import com.android.internal.util.jobs.DumpUtils;
import com.android.internal.util.jobs.FastXmlSerializer;
import com.android.internal.util.jobs.XmlUtils;
import com.android.modules.expresslog.Counter;
import com.android.server.AnyMotionDetector;
import com.android.server.DeviceIdleInternal;
import com.android.server.PowerAllowlistInternal;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.clipboard.ClipboardService;
import com.android.server.deviceidle.ConstraintController;
import com.android.server.deviceidle.DeviceIdleConstraintTracker;
import com.android.server.deviceidle.IDeviceIdleConstraint;
import com.android.server.deviceidle.TvConstraintController;
import com.android.server.display.DisplayPowerController2;
import com.android.server.net.NetworkPolicyManagerInternal;
import com.android.server.am.BatteryStatsService;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public class DeviceIdleController extends SystemService implements AnyMotionDetector.DeviceIdleCallback {
    static final int LIGHT_STATE_ACTIVE = 0;
    static final int LIGHT_STATE_IDLE = 4;
    static final int LIGHT_STATE_IDLE_MAINTENANCE = 6;
    static final int LIGHT_STATE_INACTIVE = 1;
    static final int LIGHT_STATE_OVERRIDE = 7;
    static final int LIGHT_STATE_WAITING_FOR_NETWORK = 5;
    static final float MIN_PRE_IDLE_FACTOR_CHANGE = 0.05f;
    static final long MIN_STATE_STEP_ALARM_CHANGE = 60000;
    static final int MSG_REPORT_STATIONARY_STATUS = 7;
    static final int MSG_RESET_PRE_IDLE_TIMEOUT_FACTOR = 12;
    static final int MSG_UPDATE_PRE_IDLE_TIMEOUT_FACTOR = 11;
    static final int SET_IDLE_FACTOR_RESULT_IGNORED = 0;
    static final int SET_IDLE_FACTOR_RESULT_INVALID = 3;
    static final int SET_IDLE_FACTOR_RESULT_NOT_SUPPORT = 2;
    static final int SET_IDLE_FACTOR_RESULT_OK = 1;
    static final int SET_IDLE_FACTOR_RESULT_UNINIT = -1;
    static final int STATE_ACTIVE = 0;
    static final int STATE_IDLE = 5;
    static final int STATE_IDLE_MAINTENANCE = 6;
    static final int STATE_IDLE_PENDING = 2;
    static final int STATE_INACTIVE = 1;
    static final int STATE_LOCATING = 4;
    static final int STATE_QUICK_DOZE_DELAY = 7;
    static final int STATE_SENSING = 3;
    public int mActiveIdleOpCount;
    public PowerManager.WakeLock mActiveIdleWakeLock;
    public int mActiveReason;
    public AlarmManager mAlarmManager;
    public boolean mAlarmsActive;
    public AnyMotionDetector mAnyMotionDetector;
    public final AppStateTrackerImpl mAppStateTracker;
    public IBatteryStats mBatteryStats;
    public BinderService mBinderService;
    public boolean mCharging;
    public final AtomicFile mConfigFile;
    public Constants mConstants;
    public ConstraintController mConstraintController;
    public final ArrayMap mConstraints;
    public long mCurLightIdleBudget;
    final AlarmManager.OnAlarmListener mDeepAlarmListener;
    public final BatteryLevel mDeepBatteryLevel;
    public boolean mDeepEnabled;
    public final EmergencyCallListener mEmergencyCallListener;
    public final int[] mEventCmds;
    public final String[] mEventReasons;
    public final long[] mEventTimes;
    public boolean mForceIdle;
    public final LocationListener mGenericLocationListener;
    public PowerManager.WakeLock mGoingIdleWakeLock;
    public final LocationListener mGpsLocationListener;
    public final MyHandler mHandler;
    public boolean mHasFusedLocation;
    public boolean mHasGps;
    public Intent mIdleIntent;
    public Bundle mIdleIntentOptions;
    public long mIdleStartTime;
    public final IIntentReceiver mIdleStartedDoneReceiver;
    public long mInactiveTimeout;
    public final Injector mInjector;
    public final BroadcastReceiver mInteractivityReceiver;
    public final boolean mIsLocationPrefetchEnabled;
    public boolean mJobsActive;
    public Location mLastGenericLocation;
    public Location mLastGpsLocation;
    public long mLastMotionEventElapsed;
    public float mLastPreIdleFactor;
    public final AlarmManager.OnAlarmListener mLightAlarmListener;
    public final BatteryLevel mLightBatteryLevel;
    public boolean mLightEnabled;
    public Intent mLightIdleIntent;
    public Bundle mLightIdleIntentOptions;
    public int mLightState;
    public ActivityManagerInternal mLocalActivityManager;
    public ActivityTaskManagerInternal mLocalActivityTaskManager;
    public AlarmManagerInternal mLocalAlarmManager;
    public BatteryManagerInternal mLocalBatteryManager;
    public PowerManagerInternal mLocalPowerManager;
    public DeviceIdleInternal mLocalService;
    public boolean mLocated;
    public boolean mLocating;
    public LocationRequest mLocationRequest;
    public long mMaintenanceStartTime;
    final MotionListener mMotionListener;
    public final AlarmManager.OnAlarmListener mMotionRegistrationAlarmListener;
    public Sensor mMotionSensor;
    public final AlarmManager.OnAlarmListener mMotionTimeoutAlarmListener;
    public boolean mNetworkConnected;
    public INetworkPolicyManager mNetworkPolicyManager;
    public NetworkPolicyManagerInternal mNetworkPolicyManagerInternal;
    public long mNextAlarmTime;
    public long mNextIdleDelay;
    public long mNextIdlePendingDelay;
    public long mNextLightAlarmTime;
    public long mNextLightIdleDelay;
    public long mNextLightIdleDelayFlex;
    public long mNextSensingTimeoutAlarmTime;
    public boolean mNotMoving;
    public int mNumBlockingConstraints;
    public PackageManagerInternal mPackageManagerInternal;
    public PowerManager mPowerManager;
    public Bundle mPowerSaveTempWhilelistChangedOptions;
    public Intent mPowerSaveTempWhitelistChangedIntent;
    public int[] mPowerSaveWhitelistAllAppIdArray;
    public final SparseBooleanArray mPowerSaveWhitelistAllAppIds;
    public final ArrayMap mPowerSaveWhitelistApps;
    public final ArrayMap mPowerSaveWhitelistAppsExceptIdle;
    public Intent mPowerSaveWhitelistChangedIntent;
    public Bundle mPowerSaveWhitelistChangedOptions;
    public int[] mPowerSaveWhitelistExceptIdleAppIdArray;
    public final SparseBooleanArray mPowerSaveWhitelistExceptIdleAppIds;
    public final SparseBooleanArray mPowerSaveWhitelistSystemAppIds;
    public final SparseBooleanArray mPowerSaveWhitelistSystemAppIdsExceptIdle;
    public int[] mPowerSaveWhitelistUserAppIdArray;
    public final SparseBooleanArray mPowerSaveWhitelistUserAppIds;
    public final ArrayMap mPowerSaveWhitelistUserApps;
    public final ArraySet mPowerSaveWhitelistUserAppsExceptIdle;
    public float mPreIdleFactor;
    public boolean mQuickDozeActivated;
    public boolean mQuickDozeActivatedWhileIdling;
    public final BroadcastReceiver mReceiver;
    public ArrayMap mRemovedFromSystemWhitelistApps;
    public boolean mScreenLocked;
    public ActivityTaskManagerInternal.ScreenObserver mScreenObserver;
    public boolean mScreenOn;
    public final AlarmManager.OnAlarmListener mSensingTimeoutAlarmListener;
    public SensorManager mSensorManager;
    public int mState;
    public final ArraySet mStationaryListeners;
    public final ArraySet mTempAllowlistChangeListeners;
    public int[] mTempWhitelistAppIdArray;
    public final SparseArray mTempWhitelistAppIdEndTimes;
    public final boolean mUseMotionSensor;
    public final RingBuffer mUserWhitelistHistoryBuffer;

    public final long getDuration(long j, long j2) {
        if (j <= 0 || j2 <= 0 || j2 <= j) {
            return 0L;
        }
        return j2 - j;
    }

    public static String stateToString(int i) {
        switch (i) {
            case 0:
                return "ACTIVE";
            case 1:
                return "INACTIVE";
            case 2:
                return "IDLE_PENDING";
            case 3:
                return "SENSING";
            case 4:
                return "LOCATING";
            case 5:
                return "IDLE";
            case 6:
                return "IDLE_MAINTENANCE";
            case 7:
                return "QUICK_DOZE_DELAY";
            default:
                return Integer.toString(i);
        }
    }

    public static String lightStateToString(int i) {
        return i != 0 ? i != 1 ? i != 4 ? i != 5 ? i != 6 ? i != 7 ? Integer.toString(i) : "OVERRIDE" : "IDLE_MAINTENANCE" : "WAITING_FOR_NETWORK" : "IDLE" : "INACTIVE" : "ACTIVE";
    }

    public class BatteryLevel {
        public float curr;
        public long currTime;
        public float prev;
        public long prevTime;

        public BatteryLevel(float f, float f2, long j, long j2) {
            this.prev = f;
            this.curr = f2;
            this.prevTime = j;
            this.currTime = j2;
        }

        public void reset(float f, long j) {
            this.prev = f;
            this.curr = f;
            this.prevTime = j;
            this.currTime = j;
        }

        public void updatePrev() {
            this.prev = this.curr;
            this.prevTime = this.currTime;
        }
    }

    public final void addEvent(int i, String str) {
        int[] iArr = this.mEventCmds;
        if (iArr[0] != i) {
            System.arraycopy(iArr, 0, iArr, 1, 99);
            long[] jArr = this.mEventTimes;
            System.arraycopy(jArr, 0, jArr, 1, 99);
            String[] strArr = this.mEventReasons;
            System.arraycopy(strArr, 0, strArr, 1, 99);
            this.mEventCmds[0] = i;
            this.mEventTimes[0] = SystemClock.elapsedRealtime();
            this.mEventReasons[0] = str;
        }
    }

    public final String getProcNameByPid(int i) {
        String str = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(String.format("/proc/%d/cmdline", Integer.valueOf(i)), Charset.defaultCharset()));
            try {
                str = bufferedReader.readLine();
                bufferedReader.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.e("DeviceIdleController", "IO errors occurred.", e);
        }
        return str != null ? str.trim() : "unknown";
    }

    public class BinderCaller {
        public final int pid;
        public final String procName;

        public BinderCaller(int i) {
            this.pid = i;
            this.procName = DeviceIdleController.this.getProcNameByPid(i);
        }
    }

    public class TargetPkg {
        public final String pkgName;
        public final int uid;

        public TargetPkg(int i, String str) {
            this.uid = i;
            this.pkgName = str;
        }
    }

    public class UserWhitelistHistoryInfo {
        public final BinderCaller caller;
        public final TargetPkg target;
        public final long time = System.currentTimeMillis();
        public final int type;

        public UserWhitelistHistoryInfo(int i, BinderCaller binderCaller, TargetPkg targetPkg) {
            this.type = i;
            this.caller = binderCaller;
            this.target = targetPkg;
        }

        public String toString() {
            Date date = new Date(this.time);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            StringBuilder sb = new StringBuilder();
            sb.append("[" + simpleDateFormat.format(date) + "]");
            sb.append(this.type < 3 ? "[add   ] " : "[remove] ");
            sb.append(this.target.pkgName + "(uid " + this.target.uid + ") by ");
            int i = this.type;
            if (i != 0) {
                if (i == 1) {
                    sb.append("deviceidle.xml");
                } else {
                    if (i != 2) {
                        if (i != 3) {
                            if (i != 4) {
                                if (i == 5) {
                                    sb.append("package removed");
                                } else {
                                    Slog.e("DeviceIdleController", "Unknown type of user whitelist");
                                }
                            }
                        }
                    }
                    sb.append("dumpsys");
                }
                return sb.toString();
            }
            if (this.caller != null) {
                sb.append(this.caller.procName + "(pid " + this.caller.pid + ")");
            }
            return sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        synchronized (this) {
            stepLightIdleStateLocked("s:alarm");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        synchronized (this) {
            if (this.mStationaryListeners.size() > 0) {
                startMonitoringMotionLocked();
                scheduleMotionTimeoutAlarmLocked();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2() {
        synchronized (this) {
            if (!isStationaryLocked()) {
                Slog.w("DeviceIdleController", "motion timeout went off and device isn't stationary");
            } else {
                postStationaryStatusUpdated();
            }
        }
    }

    public final void postStationaryStatus(DeviceIdleInternal.StationaryListener stationaryListener) {
        this.mHandler.obtainMessage(7, stationaryListener).sendToTarget();
    }

    public final void postStationaryStatusUpdated() {
        this.mHandler.sendEmptyMessage(7);
    }

    public final boolean isStationaryLocked() {
        long elapsedRealtime = this.mInjector.getElapsedRealtime();
        MotionListener motionListener = this.mMotionListener;
        return motionListener.active && elapsedRealtime - Math.max(motionListener.activatedTimeElapsed, this.mLastMotionEventElapsed) >= this.mConstants.MOTION_INACTIVE_TIMEOUT;
    }

    public void registerStationaryListener(DeviceIdleInternal.StationaryListener stationaryListener) {
        synchronized (this) {
            if (this.mStationaryListeners.add(stationaryListener)) {
                postStationaryStatus(stationaryListener);
                if (this.mMotionListener.active) {
                    if (!isStationaryLocked() && this.mStationaryListeners.size() == 1) {
                        scheduleMotionTimeoutAlarmLocked();
                    }
                } else {
                    startMonitoringMotionLocked();
                    scheduleMotionTimeoutAlarmLocked();
                }
            }
        }
    }

    public final void unregisterStationaryListener(DeviceIdleInternal.StationaryListener stationaryListener) {
        int i;
        synchronized (this) {
            if (this.mStationaryListeners.remove(stationaryListener) && this.mStationaryListeners.size() == 0 && ((i = this.mState) == 0 || i == 1 || this.mQuickDozeActivated)) {
                maybeStopMonitoringMotionLocked();
            }
        }
    }

    public final void registerTempAllowlistChangeListener(PowerAllowlistInternal.TempAllowlistChangeListener tempAllowlistChangeListener) {
        synchronized (this) {
            this.mTempAllowlistChangeListeners.add(tempAllowlistChangeListener);
        }
    }

    public final void unregisterTempAllowlistChangeListener(PowerAllowlistInternal.TempAllowlistChangeListener tempAllowlistChangeListener) {
        synchronized (this) {
            this.mTempAllowlistChangeListeners.remove(tempAllowlistChangeListener);
        }
    }

    final class MotionListener extends TriggerEventListener implements SensorEventListener {
        public long activatedTimeElapsed;
        public boolean active = false;

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        public MotionListener() {
        }

        @Override // android.hardware.TriggerEventListener
        public void onTrigger(TriggerEvent triggerEvent) {
            synchronized (DeviceIdleController.this) {
                this.active = false;
                DeviceIdleController.this.motionLocked();
            }
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            synchronized (DeviceIdleController.this) {
                DeviceIdleController.this.mSensorManager.unregisterListener(this, DeviceIdleController.this.mMotionSensor);
                this.active = false;
                DeviceIdleController.this.motionLocked();
            }
        }

        public boolean registerLocked() {
            boolean registerListener;
            if (DeviceIdleController.this.mMotionSensor.getReportingMode() == 2) {
                SensorManager sensorManager = DeviceIdleController.this.mSensorManager;
                DeviceIdleController deviceIdleController = DeviceIdleController.this;
                registerListener = sensorManager.requestTriggerSensor(deviceIdleController.mMotionListener, deviceIdleController.mMotionSensor);
            } else {
                SensorManager sensorManager2 = DeviceIdleController.this.mSensorManager;
                DeviceIdleController deviceIdleController2 = DeviceIdleController.this;
                registerListener = sensorManager2.registerListener(deviceIdleController2.mMotionListener, deviceIdleController2.mMotionSensor, 3);
            }
            if (registerListener) {
                this.active = true;
                this.activatedTimeElapsed = DeviceIdleController.this.mInjector.getElapsedRealtime();
            } else {
                Slog.e("DeviceIdleController", "Unable to register for " + DeviceIdleController.this.mMotionSensor);
            }
            return registerListener;
        }

        public void unregisterLocked() {
            if (DeviceIdleController.this.mMotionSensor.getReportingMode() == 2) {
                SensorManager sensorManager = DeviceIdleController.this.mSensorManager;
                DeviceIdleController deviceIdleController = DeviceIdleController.this;
                sensorManager.cancelTriggerSensor(deviceIdleController.mMotionListener, deviceIdleController.mMotionSensor);
            } else {
                DeviceIdleController.this.mSensorManager.unregisterListener(DeviceIdleController.this.mMotionListener);
            }
            this.active = false;
        }
    }

    public final class Constants implements DeviceConfig.OnPropertiesChangedListener {
        public long IDLE_AFTER_INACTIVE_TIMEOUT;
        public long INACTIVE_TIMEOUT;
        public final boolean mSmallBatteryDevice;
        public long mDefaultFlexTimeShort = 60000;
        public long mDefaultLightIdleAfterInactiveTimeout = 240000;
        public long mDefaultLightIdleTimeout = BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
        public long mDefaultLightIdleTimeoutInitialFlex = 60000;
        public long mDefaultLightIdleTimeoutMaxFlex = 900000;
        public float mDefaultLightIdleFactor = 2.0f;
        public long mDefaultLightMaxIdleTimeout = 900000;
        public long mDefaultLightIdleMaintenanceMinBudget = 60000;
        public long mDefaultLightIdleMaintenanceMaxBudget = BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
        public long mDefaultMinLightMaintenanceTime = 5000;
        public long mDefaultMinDeepMaintenanceTime = 30000;
        public long mDefaultInactiveTimeout = 1800000;
        public long mDefaultSensingTimeout = 240000;
        public long mDefaultLocatingTimeout = 30000;
        public float mDefaultLocationAccuracy = 20.0f;
        public long mDefaultMotionInactiveTimeout = 600000;
        public long mDefaultMotionInactiveTimeoutFlex = 60000;
        public long mDefaultIdleAfterInactiveTimeout = 1800000;
        public long mDefaultIdlePendingTimeout = BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
        public long mDefaultMaxIdlePendingTimeout = 600000;
        public float mDefaultIdlePendingFactor = 2.0f;
        public long mDefaultQuickDozeDelayTimeout = 60000;
        public long mDefaultIdleTimeout = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
        public long mDefaultMaxIdleTimeout = 21600000;
        public float mDefaultIdleFactor = 2.0f;
        public long mDefaultMinTimeToAlarm = 1800000;
        public long mDefaultMaxTempAppAllowlistDurationMs = BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
        public long mDefaultMmsTempAppAllowlistDurationMs = 60000;
        public long mDefaultSmsTempAppAllowlistDurationMs = 20000;
        public long mDefaultNotificationAllowlistDurationMs = 30000;
        public boolean mDefaultWaitForUnlock = true;
        public float mDefaultPreIdleFactorLong = 1.67f;
        public float mDefaultPreIdleFactorShort = 0.33f;
        public boolean mDefaultUseWindowAlarms = true;
        public long FLEX_TIME_SHORT = 60000;
        public long LIGHT_IDLE_AFTER_INACTIVE_TIMEOUT = 240000;
        public long LIGHT_IDLE_TIMEOUT = BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
        public long LIGHT_IDLE_TIMEOUT_INITIAL_FLEX = 60000;
        public long LIGHT_IDLE_TIMEOUT_MAX_FLEX = 900000;
        public float LIGHT_IDLE_FACTOR = 2.0f;
        public long LIGHT_MAX_IDLE_TIMEOUT = 900000;
        public long LIGHT_IDLE_MAINTENANCE_MIN_BUDGET = 60000;
        public long LIGHT_IDLE_MAINTENANCE_MAX_BUDGET = BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
        public long MIN_LIGHT_MAINTENANCE_TIME = 5000;
        public long MIN_DEEP_MAINTENANCE_TIME = 30000;
        public long SENSING_TIMEOUT = 240000;
        public long LOCATING_TIMEOUT = 30000;
        public float LOCATION_ACCURACY = 20.0f;
        public long MOTION_INACTIVE_TIMEOUT = 600000;
        public long MOTION_INACTIVE_TIMEOUT_FLEX = 60000;
        public long IDLE_PENDING_TIMEOUT = BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
        public long MAX_IDLE_PENDING_TIMEOUT = 600000;
        public float IDLE_PENDING_FACTOR = 2.0f;
        public long QUICK_DOZE_DELAY_TIMEOUT = 60000;
        public long IDLE_TIMEOUT = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
        public long MAX_IDLE_TIMEOUT = 21600000;
        public float IDLE_FACTOR = 2.0f;
        public long MIN_TIME_TO_ALARM = 1800000;
        public long MAX_TEMP_APP_ALLOWLIST_DURATION_MS = BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
        public long MMS_TEMP_APP_ALLOWLIST_DURATION_MS = 60000;
        public long SMS_TEMP_APP_ALLOWLIST_DURATION_MS = 20000;
        public long NOTIFICATION_ALLOWLIST_DURATION_MS = 30000;
        public float PRE_IDLE_FACTOR_LONG = 1.67f;
        public float PRE_IDLE_FACTOR_SHORT = 0.33f;
        public boolean WAIT_FOR_UNLOCK = true;
        public boolean USE_WINDOW_ALARMS = true;

        public final long getTimeout(long j, long j2) {
            return j;
        }

        public Constants() {
            this.INACTIVE_TIMEOUT = 1800000L;
            this.IDLE_AFTER_INACTIVE_TIMEOUT = 1800000L;
            initDefault();
            boolean isSmallBatteryDevice = ActivityManager.isSmallBatteryDevice();
            this.mSmallBatteryDevice = isSmallBatteryDevice;
            if (isSmallBatteryDevice) {
                this.INACTIVE_TIMEOUT = 900000L;
                this.IDLE_AFTER_INACTIVE_TIMEOUT = 900000L;
            }
            DeviceConfig.addOnPropertiesChangedListener("device_idle", AppSchedulingModuleThread.getExecutor(), this);
            onPropertiesChanged(DeviceConfig.getProperties("device_idle", new String[0]));
        }

        public final void initDefault() {
            Resources resources = DeviceIdleController.this.getContext().getResources();
            this.mDefaultFlexTimeShort = getTimeout(resources.getInteger(R.integer.device_idle_light_idle_flex_linear_increase_factor_ms), this.mDefaultFlexTimeShort);
            this.mDefaultLightIdleAfterInactiveTimeout = getTimeout(resources.getInteger(R.integer.device_idle_light_max_idle_to_ms), this.mDefaultLightIdleAfterInactiveTimeout);
            this.mDefaultLightIdleTimeout = getTimeout(resources.getInteger(R.integer.device_idle_min_deep_maintenance_time_ms), this.mDefaultLightIdleTimeout);
            this.mDefaultLightIdleTimeoutInitialFlex = getTimeout(resources.getInteger(R.integer.device_idle_max_idle_to_ms), this.mDefaultLightIdleTimeoutInitialFlex);
            this.mDefaultLightIdleTimeoutMaxFlex = getTimeout(resources.getInteger(R.integer.device_idle_max_temp_app_allowlist_duration_ms), this.mDefaultLightIdleTimeoutMaxFlex);
            this.mDefaultLightIdleFactor = resources.getFloat(R.integer.device_idle_locating_to_ms);
            this.mDefaultLightMaxIdleTimeout = getTimeout(resources.getInteger(R.integer.device_idle_min_light_maintenance_time_ms), this.mDefaultLightMaxIdleTimeout);
            this.mDefaultLightIdleMaintenanceMinBudget = getTimeout(resources.getInteger(R.integer.device_idle_max_idle_pending_to_ms), this.mDefaultLightIdleMaintenanceMinBudget);
            this.mDefaultLightIdleMaintenanceMaxBudget = getTimeout(resources.getInteger(R.integer.device_idle_location_accuracy), this.mDefaultLightIdleMaintenanceMaxBudget);
            this.mDefaultMinLightMaintenanceTime = getTimeout(resources.getInteger(R.integer.device_idle_sensing_to_ms), this.mDefaultMinLightMaintenanceTime);
            this.mDefaultMinDeepMaintenanceTime = getTimeout(resources.getInteger(R.integer.device_idle_quick_doze_delay_to_ms), this.mDefaultMinDeepMaintenanceTime);
            this.mDefaultInactiveTimeout = getTimeout(resources.getInteger(R.integer.device_idle_light_idle_to_ms), this.mDefaultInactiveTimeout);
            this.mDefaultSensingTimeout = getTimeout(resources.getInteger(R.integer.leanback_setup_alpha_activity_in_bkg_delay), this.mDefaultSensingTimeout);
            this.mDefaultLocatingTimeout = getTimeout(resources.getInteger(R.integer.device_idle_min_time_to_alarm_ms), this.mDefaultLocatingTimeout);
            this.mDefaultLocationAccuracy = resources.getFloat(R.integer.device_idle_mms_temp_app_allowlist_duration_ms);
            this.mDefaultMotionInactiveTimeout = getTimeout(resources.getInteger(R.integer.kg_carousel_angle), this.mDefaultMotionInactiveTimeout);
            this.mDefaultMotionInactiveTimeoutFlex = getTimeout(resources.getInteger(R.integer.dock_enter_exit_duration), this.mDefaultMotionInactiveTimeoutFlex);
            this.mDefaultIdleAfterInactiveTimeout = getTimeout(resources.getInteger(R.integer.device_idle_light_idle_linear_increase_factor_ms), this.mDefaultIdleAfterInactiveTimeout);
            this.mDefaultIdlePendingTimeout = getTimeout(resources.getInteger(R.integer.device_idle_light_idle_to_init_flex_ms), this.mDefaultIdlePendingTimeout);
            this.mDefaultMaxIdlePendingTimeout = getTimeout(resources.getInteger(R.integer.device_idle_motion_inactive_to_flex_ms), this.mDefaultMaxIdlePendingTimeout);
            this.mDefaultIdlePendingFactor = resources.getFloat(R.integer.device_idle_light_idle_maintenance_min_budget_ms);
            this.mDefaultQuickDozeDelayTimeout = getTimeout(resources.getInteger(R.integer.kg_widget_region_weight), this.mDefaultQuickDozeDelayTimeout);
            this.mDefaultIdleTimeout = getTimeout(resources.getInteger(R.integer.device_idle_light_idle_to_max_flex_ms), this.mDefaultIdleTimeout);
            this.mDefaultMaxIdleTimeout = getTimeout(resources.getInteger(R.integer.device_idle_motion_inactive_to_ms), this.mDefaultMaxIdleTimeout);
            this.mDefaultIdleFactor = resources.getFloat(R.integer.device_idle_light_idle_maintenance_max_budget_ms);
            this.mDefaultMinTimeToAlarm = getTimeout(resources.getInteger(R.integer.device_idle_sms_temp_app_allowlist_duration_ms), this.mDefaultMinTimeToAlarm);
            this.mDefaultMaxTempAppAllowlistDurationMs = resources.getInteger(R.integer.device_idle_notification_allowlist_duration_ms);
            this.mDefaultMmsTempAppAllowlistDurationMs = resources.getInteger(R.integer.disabled_alpha_animation_duration);
            this.mDefaultSmsTempAppAllowlistDurationMs = resources.getInteger(R.integer.leanback_setup_alpha_activity_in_bkg_duration);
            this.mDefaultNotificationAllowlistDurationMs = resources.getInteger(R.integer.kg_glowpad_rotation_offset);
            this.mDefaultWaitForUnlock = resources.getBoolean(R.bool.config_useAutoSuspend);
            this.mDefaultPreIdleFactorLong = resources.getFloat(R.integer.kg_security_flipper_weight);
            this.mDefaultPreIdleFactorShort = resources.getFloat(R.integer.kg_selector_gravity);
            boolean z = resources.getBoolean(R.bool.config_useAttentionLight);
            this.mDefaultUseWindowAlarms = z;
            this.FLEX_TIME_SHORT = this.mDefaultFlexTimeShort;
            this.LIGHT_IDLE_AFTER_INACTIVE_TIMEOUT = this.mDefaultLightIdleAfterInactiveTimeout;
            this.LIGHT_IDLE_TIMEOUT = this.mDefaultLightIdleTimeout;
            this.LIGHT_IDLE_TIMEOUT_INITIAL_FLEX = this.mDefaultLightIdleTimeoutInitialFlex;
            this.LIGHT_IDLE_TIMEOUT_MAX_FLEX = this.mDefaultLightIdleTimeoutMaxFlex;
            this.LIGHT_IDLE_FACTOR = this.mDefaultLightIdleFactor;
            this.LIGHT_MAX_IDLE_TIMEOUT = this.mDefaultLightMaxIdleTimeout;
            this.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET = this.mDefaultLightIdleMaintenanceMinBudget;
            this.LIGHT_IDLE_MAINTENANCE_MAX_BUDGET = this.mDefaultLightIdleMaintenanceMaxBudget;
            this.MIN_LIGHT_MAINTENANCE_TIME = this.mDefaultMinLightMaintenanceTime;
            this.MIN_DEEP_MAINTENANCE_TIME = this.mDefaultMinDeepMaintenanceTime;
            this.INACTIVE_TIMEOUT = this.mDefaultInactiveTimeout;
            this.SENSING_TIMEOUT = this.mDefaultSensingTimeout;
            this.LOCATING_TIMEOUT = this.mDefaultLocatingTimeout;
            this.LOCATION_ACCURACY = this.mDefaultLocationAccuracy;
            this.MOTION_INACTIVE_TIMEOUT = this.mDefaultMotionInactiveTimeout;
            this.MOTION_INACTIVE_TIMEOUT_FLEX = this.mDefaultMotionInactiveTimeoutFlex;
            this.IDLE_AFTER_INACTIVE_TIMEOUT = this.mDefaultIdleAfterInactiveTimeout;
            this.IDLE_PENDING_TIMEOUT = this.mDefaultIdlePendingTimeout;
            this.MAX_IDLE_PENDING_TIMEOUT = this.mDefaultMaxIdlePendingTimeout;
            this.IDLE_PENDING_FACTOR = this.mDefaultIdlePendingFactor;
            this.QUICK_DOZE_DELAY_TIMEOUT = this.mDefaultQuickDozeDelayTimeout;
            this.IDLE_TIMEOUT = this.mDefaultIdleTimeout;
            this.MAX_IDLE_TIMEOUT = this.mDefaultMaxIdleTimeout;
            this.IDLE_FACTOR = this.mDefaultIdleFactor;
            this.MIN_TIME_TO_ALARM = this.mDefaultMinTimeToAlarm;
            this.MAX_TEMP_APP_ALLOWLIST_DURATION_MS = this.mDefaultMaxTempAppAllowlistDurationMs;
            this.MMS_TEMP_APP_ALLOWLIST_DURATION_MS = this.mDefaultMmsTempAppAllowlistDurationMs;
            this.SMS_TEMP_APP_ALLOWLIST_DURATION_MS = this.mDefaultSmsTempAppAllowlistDurationMs;
            this.NOTIFICATION_ALLOWLIST_DURATION_MS = this.mDefaultNotificationAllowlistDurationMs;
            this.WAIT_FOR_UNLOCK = this.mDefaultWaitForUnlock;
            this.PRE_IDLE_FACTOR_LONG = this.mDefaultPreIdleFactorLong;
            this.PRE_IDLE_FACTOR_SHORT = this.mDefaultPreIdleFactorShort;
            this.USE_WINDOW_ALARMS = z;
        }

        public void onPropertiesChanged(DeviceConfig.Properties properties) {
            char c;
            synchronized (DeviceIdleController.this) {
                for (String str : properties.getKeyset()) {
                    if (str != null) {
                        switch (str.hashCode()) {
                            case -1781086459:
                                if (str.equals("notification_allowlist_duration_ms")) {
                                    c = 29;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1102128050:
                                if (str.equals("light_idle_maintenance_max_budget")) {
                                    c = '\b';
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1067343247:
                                if (str.equals("light_idle_factor")) {
                                    c = 5;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -986742087:
                                if (str.equals("use_window_alarms")) {
                                    c = '!';
                                    break;
                                }
                                c = 65535;
                                break;
                            case -919175870:
                                if (str.equals("light_max_idle_to")) {
                                    c = 6;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -564968069:
                                if (str.equals("pre_idle_factor_short")) {
                                    c = ' ';
                                    break;
                                }
                                c = 65535;
                                break;
                            case -547781361:
                                if (str.equals("min_light_maintenance_time")) {
                                    c = '\t';
                                    break;
                                }
                                c = 65535;
                                break;
                            case -492261706:
                                if (str.equals("sms_temp_app_allowlist_duration_ms")) {
                                    c = 28;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -318123838:
                                if (str.equals("idle_pending_factor")) {
                                    c = 20;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -173192557:
                                if (str.equals("max_idle_pending_to")) {
                                    c = 19;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -80111214:
                                if (str.equals("min_time_to_alarm")) {
                                    c = 25;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 134792310:
                                if (str.equals("light_idle_to_initial_flex")) {
                                    c = 3;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 197367965:
                                if (str.equals("light_idle_to")) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 361511631:
                                if (str.equals("inactive_to")) {
                                    c = 11;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 370224338:
                                if (str.equals("motion_inactive_to_flex")) {
                                    c = 16;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 415987654:
                                if (str.equals("motion_inactive_to")) {
                                    c = 15;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 551187755:
                                if (str.equals("locating_to")) {
                                    c = '\r';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 866187779:
                                if (str.equals("light_after_inactive_to")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 891348287:
                                if (str.equals("min_deep_maintenance_time")) {
                                    c = '\n';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 918455627:
                                if (str.equals("max_temp_app_allowlist_duration_ms")) {
                                    c = 26;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1001374852:
                                if (str.equals("wait_for_unlock")) {
                                    c = 30;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1228499357:
                                if (str.equals("pre_idle_factor_long")) {
                                    c = 31;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1280980182:
                                if (str.equals("light_max_idle_to_flex")) {
                                    c = 4;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1350761616:
                                if (str.equals("flex_time_short")) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1369871264:
                                if (str.equals("light_idle_maintenance_min_budget")) {
                                    c = 7;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1383403841:
                                if (str.equals("idle_after_inactive_to")) {
                                    c = 17;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1536604751:
                                if (str.equals("sensing_to")) {
                                    c = '\f';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1547108378:
                                if (str.equals("idle_factor")) {
                                    c = 24;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1563458830:
                                if (str.equals("quick_doze_delay_to")) {
                                    c = 21;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1664365254:
                                if (str.equals("idle_to")) {
                                    c = 22;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1679398766:
                                if (str.equals("idle_pending_to")) {
                                    c = 18;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1695275755:
                                if (str.equals("max_idle_to")) {
                                    c = 23;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1930831427:
                                if (str.equals("location_accuracy")) {
                                    c = 14;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1944720892:
                                if (str.equals("mms_temp_app_allowlist_duration_ms")) {
                                    c = 27;
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        long j = 900000;
                        switch (c) {
                            case 0:
                                this.FLEX_TIME_SHORT = properties.getLong("flex_time_short", this.mDefaultFlexTimeShort);
                                break;
                            case 1:
                                this.LIGHT_IDLE_AFTER_INACTIVE_TIMEOUT = properties.getLong("light_after_inactive_to", this.mDefaultLightIdleAfterInactiveTimeout);
                                break;
                            case 2:
                                this.LIGHT_IDLE_TIMEOUT = properties.getLong("light_idle_to", this.mDefaultLightIdleTimeout);
                                break;
                            case 3:
                                this.LIGHT_IDLE_TIMEOUT_INITIAL_FLEX = properties.getLong("light_idle_to_initial_flex", this.mDefaultLightIdleTimeoutInitialFlex);
                                break;
                            case 4:
                                this.LIGHT_IDLE_TIMEOUT_MAX_FLEX = properties.getLong("light_max_idle_to_flex", this.mDefaultLightIdleTimeoutMaxFlex);
                                break;
                            case 5:
                                this.LIGHT_IDLE_FACTOR = Math.max(1.0f, properties.getFloat("light_idle_factor", this.mDefaultLightIdleFactor));
                                break;
                            case 6:
                                this.LIGHT_MAX_IDLE_TIMEOUT = properties.getLong("light_max_idle_to", this.mDefaultLightMaxIdleTimeout);
                                break;
                            case 7:
                                this.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET = properties.getLong("light_idle_maintenance_min_budget", this.mDefaultLightIdleMaintenanceMinBudget);
                                break;
                            case '\b':
                                this.LIGHT_IDLE_MAINTENANCE_MAX_BUDGET = properties.getLong("light_idle_maintenance_max_budget", this.mDefaultLightIdleMaintenanceMaxBudget);
                                break;
                            case '\t':
                                this.MIN_LIGHT_MAINTENANCE_TIME = properties.getLong("min_light_maintenance_time", this.mDefaultMinLightMaintenanceTime);
                                break;
                            case '\n':
                                this.MIN_DEEP_MAINTENANCE_TIME = properties.getLong("min_deep_maintenance_time", this.mDefaultMinDeepMaintenanceTime);
                                break;
                            case 11:
                                if (!this.mSmallBatteryDevice) {
                                    j = this.mDefaultInactiveTimeout;
                                }
                                this.INACTIVE_TIMEOUT = properties.getLong("inactive_to", j);
                                break;
                            case '\f':
                                this.SENSING_TIMEOUT = properties.getLong("sensing_to", this.mDefaultSensingTimeout);
                                break;
                            case '\r':
                                this.LOCATING_TIMEOUT = properties.getLong("locating_to", this.mDefaultLocatingTimeout);
                                break;
                            case 14:
                                this.LOCATION_ACCURACY = properties.getFloat("location_accuracy", this.mDefaultLocationAccuracy);
                                break;
                            case 15:
                                this.MOTION_INACTIVE_TIMEOUT = properties.getLong("motion_inactive_to", this.mDefaultMotionInactiveTimeout);
                                break;
                            case 16:
                                this.MOTION_INACTIVE_TIMEOUT_FLEX = properties.getLong("motion_inactive_to_flex", this.mDefaultMotionInactiveTimeoutFlex);
                                break;
                            case 17:
                                if (!this.mSmallBatteryDevice) {
                                    j = this.mDefaultIdleAfterInactiveTimeout;
                                }
                                this.IDLE_AFTER_INACTIVE_TIMEOUT = properties.getLong("idle_after_inactive_to", j);
                                break;
                            case 18:
                                this.IDLE_PENDING_TIMEOUT = properties.getLong("idle_pending_to", this.mDefaultIdlePendingTimeout);
                                break;
                            case 19:
                                this.MAX_IDLE_PENDING_TIMEOUT = properties.getLong("max_idle_pending_to", this.mDefaultMaxIdlePendingTimeout);
                                break;
                            case 20:
                                this.IDLE_PENDING_FACTOR = properties.getFloat("idle_pending_factor", this.mDefaultIdlePendingFactor);
                                break;
                            case 21:
                                this.QUICK_DOZE_DELAY_TIMEOUT = properties.getLong("quick_doze_delay_to", this.mDefaultQuickDozeDelayTimeout);
                                break;
                            case 22:
                                this.IDLE_TIMEOUT = properties.getLong("idle_to", this.mDefaultIdleTimeout);
                                break;
                            case 23:
                                this.MAX_IDLE_TIMEOUT = properties.getLong("max_idle_to", this.mDefaultMaxIdleTimeout);
                                break;
                            case 24:
                                this.IDLE_FACTOR = properties.getFloat("idle_factor", this.mDefaultIdleFactor);
                                break;
                            case 25:
                                this.MIN_TIME_TO_ALARM = properties.getLong("min_time_to_alarm", this.mDefaultMinTimeToAlarm);
                                break;
                            case 26:
                                this.MAX_TEMP_APP_ALLOWLIST_DURATION_MS = properties.getLong("max_temp_app_allowlist_duration_ms", this.mDefaultMaxTempAppAllowlistDurationMs);
                                break;
                            case 27:
                                this.MMS_TEMP_APP_ALLOWLIST_DURATION_MS = properties.getLong("mms_temp_app_allowlist_duration_ms", this.mDefaultMmsTempAppAllowlistDurationMs);
                                break;
                            case 28:
                                this.SMS_TEMP_APP_ALLOWLIST_DURATION_MS = properties.getLong("sms_temp_app_allowlist_duration_ms", this.mDefaultSmsTempAppAllowlistDurationMs);
                                break;
                            case 29:
                                this.NOTIFICATION_ALLOWLIST_DURATION_MS = properties.getLong("notification_allowlist_duration_ms", this.mDefaultNotificationAllowlistDurationMs);
                                break;
                            case 30:
                                this.WAIT_FOR_UNLOCK = properties.getBoolean("wait_for_unlock", this.mDefaultWaitForUnlock);
                                break;
                            case 31:
                                this.PRE_IDLE_FACTOR_LONG = properties.getFloat("pre_idle_factor_long", this.mDefaultPreIdleFactorLong);
                                break;
                            case ' ':
                                this.PRE_IDLE_FACTOR_SHORT = properties.getFloat("pre_idle_factor_short", this.mDefaultPreIdleFactorShort);
                                break;
                            case '!':
                                this.USE_WINDOW_ALARMS = properties.getBoolean("use_window_alarms", this.mDefaultUseWindowAlarms);
                                break;
                            default:
                                Slog.e("DeviceIdleController", "Unknown configuration key: " + str);
                                break;
                        }
                    }
                }
            }
        }

        public void dump(PrintWriter printWriter) {
            printWriter.println("  Settings:");
            printWriter.print("    ");
            printWriter.print("flex_time_short");
            printWriter.print("=");
            TimeUtils.formatDuration(this.FLEX_TIME_SHORT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("light_after_inactive_to");
            printWriter.print("=");
            TimeUtils.formatDuration(this.LIGHT_IDLE_AFTER_INACTIVE_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("light_idle_to");
            printWriter.print("=");
            TimeUtils.formatDuration(this.LIGHT_IDLE_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("light_idle_to_initial_flex");
            printWriter.print("=");
            TimeUtils.formatDuration(this.LIGHT_IDLE_TIMEOUT_INITIAL_FLEX, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("light_max_idle_to_flex");
            printWriter.print("=");
            TimeUtils.formatDuration(this.LIGHT_IDLE_TIMEOUT_MAX_FLEX, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("light_idle_factor");
            printWriter.print("=");
            printWriter.print(this.LIGHT_IDLE_FACTOR);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("light_max_idle_to");
            printWriter.print("=");
            TimeUtils.formatDuration(this.LIGHT_MAX_IDLE_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("light_idle_maintenance_min_budget");
            printWriter.print("=");
            TimeUtils.formatDuration(this.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("light_idle_maintenance_max_budget");
            printWriter.print("=");
            TimeUtils.formatDuration(this.LIGHT_IDLE_MAINTENANCE_MAX_BUDGET, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("min_light_maintenance_time");
            printWriter.print("=");
            TimeUtils.formatDuration(this.MIN_LIGHT_MAINTENANCE_TIME, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("min_deep_maintenance_time");
            printWriter.print("=");
            TimeUtils.formatDuration(this.MIN_DEEP_MAINTENANCE_TIME, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("inactive_to");
            printWriter.print("=");
            TimeUtils.formatDuration(this.INACTIVE_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("sensing_to");
            printWriter.print("=");
            TimeUtils.formatDuration(this.SENSING_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("locating_to");
            printWriter.print("=");
            TimeUtils.formatDuration(this.LOCATING_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("location_accuracy");
            printWriter.print("=");
            printWriter.print(this.LOCATION_ACCURACY);
            printWriter.print("m");
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("motion_inactive_to");
            printWriter.print("=");
            TimeUtils.formatDuration(this.MOTION_INACTIVE_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("motion_inactive_to_flex");
            printWriter.print("=");
            TimeUtils.formatDuration(this.MOTION_INACTIVE_TIMEOUT_FLEX, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("idle_after_inactive_to");
            printWriter.print("=");
            TimeUtils.formatDuration(this.IDLE_AFTER_INACTIVE_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("idle_pending_to");
            printWriter.print("=");
            TimeUtils.formatDuration(this.IDLE_PENDING_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("max_idle_pending_to");
            printWriter.print("=");
            TimeUtils.formatDuration(this.MAX_IDLE_PENDING_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("idle_pending_factor");
            printWriter.print("=");
            printWriter.println(this.IDLE_PENDING_FACTOR);
            printWriter.print("    ");
            printWriter.print("quick_doze_delay_to");
            printWriter.print("=");
            TimeUtils.formatDuration(this.QUICK_DOZE_DELAY_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("idle_to");
            printWriter.print("=");
            TimeUtils.formatDuration(this.IDLE_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("max_idle_to");
            printWriter.print("=");
            TimeUtils.formatDuration(this.MAX_IDLE_TIMEOUT, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("idle_factor");
            printWriter.print("=");
            printWriter.println(this.IDLE_FACTOR);
            printWriter.print("    ");
            printWriter.print("min_time_to_alarm");
            printWriter.print("=");
            TimeUtils.formatDuration(this.MIN_TIME_TO_ALARM, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("max_temp_app_allowlist_duration_ms");
            printWriter.print("=");
            TimeUtils.formatDuration(this.MAX_TEMP_APP_ALLOWLIST_DURATION_MS, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("mms_temp_app_allowlist_duration_ms");
            printWriter.print("=");
            TimeUtils.formatDuration(this.MMS_TEMP_APP_ALLOWLIST_DURATION_MS, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("sms_temp_app_allowlist_duration_ms");
            printWriter.print("=");
            TimeUtils.formatDuration(this.SMS_TEMP_APP_ALLOWLIST_DURATION_MS, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("notification_allowlist_duration_ms");
            printWriter.print("=");
            TimeUtils.formatDuration(this.NOTIFICATION_ALLOWLIST_DURATION_MS, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("wait_for_unlock");
            printWriter.print("=");
            printWriter.println(this.WAIT_FOR_UNLOCK);
            printWriter.print("    ");
            printWriter.print("pre_idle_factor_long");
            printWriter.print("=");
            printWriter.println(this.PRE_IDLE_FACTOR_LONG);
            printWriter.print("    ");
            printWriter.print("pre_idle_factor_short");
            printWriter.print("=");
            printWriter.println(this.PRE_IDLE_FACTOR_SHORT);
            printWriter.print("    ");
            printWriter.print("use_window_alarms");
            printWriter.print("=");
            printWriter.println(this.USE_WINDOW_ALARMS);
        }
    }

    @Override // com.android.server.AnyMotionDetector.DeviceIdleCallback
    public void onAnyMotionResult(int i) {
        synchronized (this) {
            if (i != -1) {
                try {
                    cancelSensingTimeoutAlarmLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (i != 1 && i != -1) {
                if (i == 0) {
                    int i2 = this.mState;
                    if (i2 == 3) {
                        this.mNotMoving = true;
                        stepIdleStateLocked("s:stationary");
                    } else if (i2 == 4) {
                        this.mNotMoving = true;
                        if (this.mLocated) {
                            stepIdleStateLocked("s:stationary");
                        }
                    }
                }
            }
            handleMotionDetectedLocked(this.mConstants.INACTIVE_TIMEOUT, "non_stationary");
        }
    }

    public final class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            boolean deviceIdleMode;
            boolean lightDeviceIdleMode;
            boolean isStationaryLocked;
            DeviceIdleInternal.StationaryListener[] stationaryListenerArr;
            PowerAllowlistInternal.TempAllowlistChangeListener[] tempAllowlistChangeListenerArr;
            int i = 0;
            switch (message.what) {
                case 1:
                    DeviceIdleController.this.handleWriteConfigFile();
                    return;
                case 2:
                case 3:
                    EventLogTags.writeDeviceIdleOnStart();
                    if (message.what == 2) {
                        deviceIdleMode = DeviceIdleController.this.mLocalPowerManager.setDeviceIdleMode(true);
                        lightDeviceIdleMode = DeviceIdleController.this.mLocalPowerManager.setLightDeviceIdleMode(false);
                    } else {
                        deviceIdleMode = DeviceIdleController.this.mLocalPowerManager.setDeviceIdleMode(false);
                        lightDeviceIdleMode = DeviceIdleController.this.mLocalPowerManager.setLightDeviceIdleMode(true);
                    }
                    try {
                        DeviceIdleController.this.mNetworkPolicyManager.setDeviceIdleMode(true);
                        DeviceIdleController.this.mBatteryStats.noteDeviceIdleMode(message.what == 2 ? 2 : 1, (String) null, Process.myUid());
                    } catch (RemoteException unused) {
                    }
                    if (deviceIdleMode) {
                        DeviceIdleController.this.getContext().sendBroadcastAsUser(DeviceIdleController.this.mIdleIntent, UserHandle.ALL, null, DeviceIdleController.this.mIdleIntentOptions);
                    }
                    if (lightDeviceIdleMode) {
                        DeviceIdleController.this.getContext().sendBroadcastAsUser(DeviceIdleController.this.mLightIdleIntent, UserHandle.ALL, null, DeviceIdleController.this.mLightIdleIntentOptions);
                    }
                    EventLogTags.writeDeviceIdleOnComplete();
                    DeviceIdleController.this.mGoingIdleWakeLock.release();
                    return;
                case 4:
                    EventLogTags.writeDeviceIdleOffStart("unknown");
                    boolean deviceIdleMode2 = DeviceIdleController.this.mLocalPowerManager.setDeviceIdleMode(false);
                    boolean lightDeviceIdleMode2 = DeviceIdleController.this.mLocalPowerManager.setLightDeviceIdleMode(false);
                    try {
                        DeviceIdleController.this.mNetworkPolicyManager.setDeviceIdleMode(false);
                        DeviceIdleController.this.mBatteryStats.noteDeviceIdleMode(0, (String) null, Process.myUid());
                    } catch (RemoteException unused2) {
                    }
                    if (deviceIdleMode2) {
                        DeviceIdleController.this.incActiveIdleOps();
                        DeviceIdleController.this.mLocalActivityManager.broadcastIntentWithCallback(DeviceIdleController.this.mIdleIntent, DeviceIdleController.this.mIdleStartedDoneReceiver, (String[]) null, -1, (int[]) null, (BiFunction) null, DeviceIdleController.this.mIdleIntentOptions);
                    }
                    if (lightDeviceIdleMode2) {
                        DeviceIdleController.this.incActiveIdleOps();
                        DeviceIdleController.this.mLocalActivityManager.broadcastIntentWithCallback(DeviceIdleController.this.mLightIdleIntent, DeviceIdleController.this.mIdleStartedDoneReceiver, (String[]) null, -1, (int[]) null, (BiFunction) null, DeviceIdleController.this.mLightIdleIntentOptions);
                    }
                    DeviceIdleController.this.decActiveIdleOps();
                    EventLogTags.writeDeviceIdleOffComplete();
                    return;
                case 5:
                    String str = (String) message.obj;
                    int i2 = message.arg1;
                    EventLogTags.writeDeviceIdleOffStart(str != null ? str : "unknown");
                    boolean deviceIdleMode3 = DeviceIdleController.this.mLocalPowerManager.setDeviceIdleMode(false);
                    boolean lightDeviceIdleMode3 = DeviceIdleController.this.mLocalPowerManager.setLightDeviceIdleMode(false);
                    try {
                        DeviceIdleController.this.mNetworkPolicyManager.setDeviceIdleMode(false);
                        DeviceIdleController.this.mBatteryStats.noteDeviceIdleMode(0, str, i2);
                    } catch (RemoteException unused3) {
                    }
                    if (deviceIdleMode3) {
                        DeviceIdleController.this.getContext().sendBroadcastAsUser(DeviceIdleController.this.mIdleIntent, UserHandle.ALL, null, DeviceIdleController.this.mIdleIntentOptions);
                    }
                    if (lightDeviceIdleMode3) {
                        DeviceIdleController.this.getContext().sendBroadcastAsUser(DeviceIdleController.this.mLightIdleIntent, UserHandle.ALL, null, DeviceIdleController.this.mLightIdleIntentOptions);
                    }
                    EventLogTags.writeDeviceIdleOffComplete();
                    return;
                case 6:
                    DeviceIdleController.this.checkTempAppWhitelistTimeout(message.arg1);
                    return;
                case 7:
                    DeviceIdleInternal.StationaryListener stationaryListener = (DeviceIdleInternal.StationaryListener) message.obj;
                    synchronized (DeviceIdleController.this) {
                        isStationaryLocked = DeviceIdleController.this.isStationaryLocked();
                        Slog.i("DeviceIdleController", "isStationary = " + isStationaryLocked);
                        stationaryListenerArr = stationaryListener == null ? (DeviceIdleInternal.StationaryListener[]) DeviceIdleController.this.mStationaryListeners.toArray(new DeviceIdleInternal.StationaryListener[DeviceIdleController.this.mStationaryListeners.size()]) : null;
                    }
                    if (stationaryListenerArr != null) {
                        int length = stationaryListenerArr.length;
                        while (i < length) {
                            stationaryListenerArr[i].onDeviceStationaryChanged(isStationaryLocked);
                            i++;
                        }
                    }
                    if (stationaryListener != null) {
                        stationaryListener.onDeviceStationaryChanged(isStationaryLocked);
                        return;
                    }
                    return;
                case 8:
                    DeviceIdleController.this.decActiveIdleOps();
                    return;
                case 9:
                default:
                    return;
                case 10:
                    IDeviceIdleConstraint iDeviceIdleConstraint = (IDeviceIdleConstraint) message.obj;
                    if ((message.arg1 != 1 ? 0 : 1) != 0) {
                        iDeviceIdleConstraint.startMonitoring();
                        return;
                    } else {
                        iDeviceIdleConstraint.stopMonitoring();
                        return;
                    }
                case 11:
                    DeviceIdleController.this.updatePreIdleFactor();
                    return;
                case 12:
                    DeviceIdleController.this.updatePreIdleFactor();
                    DeviceIdleController.this.maybeDoImmediateMaintenance("idle factor");
                    return;
                case 13:
                    int i3 = message.arg1;
                    int i4 = message.arg2 != 1 ? 0 : 1;
                    synchronized (DeviceIdleController.this) {
                        tempAllowlistChangeListenerArr = (PowerAllowlistInternal.TempAllowlistChangeListener[]) DeviceIdleController.this.mTempAllowlistChangeListeners.toArray(new PowerAllowlistInternal.TempAllowlistChangeListener[DeviceIdleController.this.mTempAllowlistChangeListeners.size()]);
                    }
                    int length2 = tempAllowlistChangeListenerArr.length;
                    while (i < length2) {
                        PowerAllowlistInternal.TempAllowlistChangeListener tempAllowlistChangeListener = tempAllowlistChangeListenerArr[i];
                        if (i4 != 0) {
                            tempAllowlistChangeListener.onAppAdded(i3);
                        } else {
                            tempAllowlistChangeListener.onAppRemoved(i3);
                        }
                        i++;
                    }
                    return;
                case 14:
                    DeviceIdleController.this.mNetworkPolicyManagerInternal.onTempPowerSaveWhitelistChange(message.arg1, true, message.arg2, (String) message.obj);
                    return;
                case 15:
                    DeviceIdleController.this.mNetworkPolicyManagerInternal.onTempPowerSaveWhitelistChange(message.arg1, false, 0, null);
                    return;
            }
        }
    }

    public final class BinderService extends IDeviceIdleController.Stub {
        public BinderService() {
        }

        public void addPowerSaveWhitelistApp(String str) {
            addPowerSaveWhitelistApps(Collections.singletonList(str));
        }

        public int addPowerSaveWhitelistApps(List list) {
            Slog.i("DeviceIdleController", "addPowerSaveWhitelistApps(name = " + list + ")");
            BinderCaller binderCaller = DeviceIdleController.this.new BinderCaller(Binder.getCallingPid());
            DeviceIdleController.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return DeviceIdleController.this.addPowerSaveWhitelistAppsInternal(list, 0, binderCaller);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void removePowerSaveWhitelistApp(String str) {
            Slog.i("DeviceIdleController", "removePowerSaveWhitelistApp(name = " + str + ")");
            BinderCaller binderCaller = DeviceIdleController.this.new BinderCaller(Binder.getCallingPid());
            DeviceIdleController.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!DeviceIdleController.this.removePowerSaveWhitelistAppInternal(str, 3, binderCaller) && DeviceIdleController.this.mPowerSaveWhitelistAppsExceptIdle.containsKey(str)) {
                    throw new UnsupportedOperationException("Cannot remove system whitelisted app");
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void removeSystemPowerWhitelistApp(String str) {
            DeviceIdleController.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DeviceIdleController.this.removeSystemPowerWhitelistAppInternal(str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void restoreSystemPowerWhitelistApp(String str) {
            DeviceIdleController.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DeviceIdleController.this.restoreSystemPowerWhitelistAppInternal(str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public String[] getRemovedSystemPowerWhitelistApps() {
            return DeviceIdleController.this.getRemovedSystemPowerWhitelistAppsInternal(Binder.getCallingUid(), UserHandle.getCallingUserId());
        }

        public String[] getSystemPowerWhitelistExceptIdle() {
            return DeviceIdleController.this.getSystemPowerWhitelistExceptIdleInternal(Binder.getCallingUid(), UserHandle.getCallingUserId());
        }

        public String[] getSystemPowerWhitelist() {
            return DeviceIdleController.this.getSystemPowerWhitelistInternal(Binder.getCallingUid(), UserHandle.getCallingUserId());
        }

        public String[] getUserPowerWhitelist() {
            return DeviceIdleController.this.getUserPowerWhitelistInternal(Binder.getCallingUid(), UserHandle.getCallingUserId());
        }

        public String[] getFullPowerWhitelistExceptIdle() {
            return DeviceIdleController.this.getFullPowerWhitelistExceptIdleInternal(Binder.getCallingUid(), UserHandle.getCallingUserId());
        }

        public String[] getFullPowerWhitelist() {
            return DeviceIdleController.this.getFullPowerWhitelistInternal(Binder.getCallingUid(), UserHandle.getCallingUserId());
        }

        public int[] getAppIdWhitelistExceptIdle() {
            return DeviceIdleController.this.getAppIdWhitelistExceptIdleInternal();
        }

        public int[] getAppIdWhitelist() {
            return DeviceIdleController.this.getAppIdWhitelistInternal();
        }

        public int[] getAppIdUserWhitelist() {
            return DeviceIdleController.this.getAppIdUserWhitelistInternal();
        }

        public int[] getAppIdTempWhitelist() {
            return DeviceIdleController.this.getAppIdTempWhitelistInternal();
        }

        public boolean isPowerSaveWhitelistExceptIdleApp(String str) {
            if (DeviceIdleController.this.mPackageManagerInternal.filterAppAccess(str, Binder.getCallingUid(), UserHandle.getCallingUserId())) {
                return false;
            }
            return DeviceIdleController.this.isPowerSaveWhitelistExceptIdleAppInternal(str);
        }

        public boolean isPowerSaveWhitelistApp(String str) {
            if (DeviceIdleController.this.mPackageManagerInternal.filterAppAccess(str, Binder.getCallingUid(), UserHandle.getCallingUserId())) {
                return false;
            }
            return DeviceIdleController.this.isPowerSaveWhitelistAppInternal(str);
        }

        public long whitelistAppTemporarily(String str, int i, int i2, String str2) {
            long max = Math.max(10000L, DeviceIdleController.this.mConstants.MAX_TEMP_APP_ALLOWLIST_DURATION_MS / 2);
            DeviceIdleController.this.addPowerSaveTempAllowlistAppChecked(str, max, i, i2, str2);
            return max;
        }

        public void addPowerSaveTempWhitelistApp(String str, long j, int i, int i2, String str2) {
            DeviceIdleController.this.addPowerSaveTempAllowlistAppChecked(str, j, i, i2, str2);
        }

        public long addPowerSaveTempWhitelistAppForMms(String str, int i, int i2, String str2) {
            long j = DeviceIdleController.this.mConstants.MMS_TEMP_APP_ALLOWLIST_DURATION_MS;
            DeviceIdleController.this.addPowerSaveTempAllowlistAppChecked(str, j, i, i2, str2);
            return j;
        }

        public long addPowerSaveTempWhitelistAppForSms(String str, int i, int i2, String str2) {
            long j = DeviceIdleController.this.mConstants.SMS_TEMP_APP_ALLOWLIST_DURATION_MS;
            DeviceIdleController.this.addPowerSaveTempAllowlistAppChecked(str, j, i, i2, str2);
            return j;
        }

        public void exitIdle(String str) {
            DeviceIdleController.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DeviceIdleController.this.exitIdleInternal(str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int setPreIdleTimeoutMode(int i) {
            DeviceIdleController.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return DeviceIdleController.this.setPreIdleTimeoutMode(i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void resetPreIdleTimeoutMode() {
            DeviceIdleController.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DeviceIdleController.this.resetPreIdleTimeoutMode();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            DeviceIdleController.this.dump(fileDescriptor, printWriter, strArr);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            DeviceIdleController.this.new Shell().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }
    }

    public class LocalService implements DeviceIdleInternal {
        public LocalService() {
        }

        public void onConstraintStateChanged(IDeviceIdleConstraint iDeviceIdleConstraint, boolean z) {
            synchronized (DeviceIdleController.this) {
                DeviceIdleController.this.onConstraintStateChangedLocked(iDeviceIdleConstraint, z);
            }
        }

        public void registerDeviceIdleConstraint(IDeviceIdleConstraint iDeviceIdleConstraint, String str, int i) {
            DeviceIdleController.this.registerDeviceIdleConstraintInternal(iDeviceIdleConstraint, str, i);
        }

        public void unregisterDeviceIdleConstraint(IDeviceIdleConstraint iDeviceIdleConstraint) {
            DeviceIdleController.this.unregisterDeviceIdleConstraintInternal(iDeviceIdleConstraint);
        }

        public void exitIdle(String str) {
            DeviceIdleController.this.exitIdleInternal(str);
        }

        public void addPowerSaveTempWhitelistApp(int i, String str, long j, int i2, boolean z, int i3, String str2) {
            DeviceIdleController.this.addPowerSaveTempAllowlistAppInternal(i, str, j, 0, i2, z, i3, str2);
        }

        public void addPowerSaveTempWhitelistApp(int i, String str, long j, int i2, int i3, boolean z, int i4, String str2) {
            DeviceIdleController.this.addPowerSaveTempAllowlistAppInternal(i, str, j, i2, i3, z, i4, str2);
        }

        public void addPowerSaveTempWhitelistAppDirect(int i, long j, int i2, boolean z, int i3, String str, int i4) {
            DeviceIdleController.this.addPowerSaveTempWhitelistAppDirectInternal(i4, i, j, i2, z, i3, str);
        }

        public long getNotificationAllowlistDuration() {
            return DeviceIdleController.this.mConstants.NOTIFICATION_ALLOWLIST_DURATION_MS;
        }

        public void setJobsActive(boolean z) {
            DeviceIdleController.this.setJobsActive(z);
        }

        public void setAlarmsActive(boolean z) {
            DeviceIdleController.this.setAlarmsActive(z);
        }

        public boolean isAppOnWhitelist(int i) {
            return DeviceIdleController.this.isAppOnWhitelistInternal(i);
        }

        public int[] getPowerSaveWhitelistUserAppIds() {
            return DeviceIdleController.this.getPowerSaveWhitelistUserAppIds();
        }

        public int[] getPowerSaveTempWhitelistAppIds() {
            return DeviceIdleController.this.getAppIdTempWhitelistInternal();
        }

        public void registerStationaryListener(DeviceIdleInternal.StationaryListener stationaryListener) {
            DeviceIdleController.this.registerStationaryListener(stationaryListener);
        }

        public void unregisterStationaryListener(DeviceIdleInternal.StationaryListener stationaryListener) {
            DeviceIdleController.this.unregisterStationaryListener(stationaryListener);
        }

        public int getTempAllowListType(int i, int i2) {
            return DeviceIdleController.this.getTempAllowListType(i, i2);
        }
    }

    public class LocalPowerAllowlistService implements PowerAllowlistInternal {
        public LocalPowerAllowlistService() {
        }

        public void registerTempAllowlistChangeListener(PowerAllowlistInternal.TempAllowlistChangeListener tempAllowlistChangeListener) {
            DeviceIdleController.this.registerTempAllowlistChangeListener(tempAllowlistChangeListener);
        }

        public void unregisterTempAllowlistChangeListener(PowerAllowlistInternal.TempAllowlistChangeListener tempAllowlistChangeListener) {
            DeviceIdleController.this.unregisterTempAllowlistChangeListener(tempAllowlistChangeListener);
        }
    }

    public class EmergencyCallListener extends TelephonyCallback implements TelephonyCallback.OutgoingEmergencyCallListener, TelephonyCallback.CallStateListener {
        public volatile boolean mIsEmergencyCallActive;

        public EmergencyCallListener() {
        }

        public void onOutgoingEmergencyCall(EmergencyNumber emergencyNumber, int i) {
            this.mIsEmergencyCallActive = true;
            synchronized (DeviceIdleController.this) {
                DeviceIdleController.this.mActiveReason = 8;
                DeviceIdleController.this.becomeActiveLocked("emergency call", Process.myUid());
            }
        }

        @Override // android.telephony.TelephonyCallback.CallStateListener
        public void onCallStateChanged(int i) {
            if (i == 0 && this.mIsEmergencyCallActive) {
                this.mIsEmergencyCallActive = false;
                synchronized (DeviceIdleController.this) {
                    DeviceIdleController.this.becomeInactiveIfAppropriateLocked();
                }
            }
        }

        public boolean isEmergencyCallActive() {
            return this.mIsEmergencyCallActive;
        }
    }

    public class Injector {
        public ConnectivityManager mConnectivityManager;
        public Constants mConstants;
        public final Context mContext;
        public LocationManager mLocationManager;

        public Injector(Context context) {
            this.mContext = context.createAttributionContext("DeviceIdleController");
        }

        public AlarmManager getAlarmManager() {
            return (AlarmManager) this.mContext.getSystemService(AlarmManager.class);
        }

        public AnyMotionDetector getAnyMotionDetector(Handler handler, SensorManager sensorManager, AnyMotionDetector.DeviceIdleCallback deviceIdleCallback, float f) {
            return new AnyMotionDetector(getPowerManager(), handler, sensorManager, deviceIdleCallback, f);
        }

        public AppStateTrackerImpl getAppStateTracker(Context context, Looper looper) {
            return new AppStateTrackerImpl(context, looper);
        }

        public ConnectivityManager getConnectivityManager() {
            if (this.mConnectivityManager == null) {
                this.mConnectivityManager = (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class);
            }
            return this.mConnectivityManager;
        }

        public Constants getConstants(DeviceIdleController deviceIdleController) {
            if (this.mConstants == null) {
                Objects.requireNonNull(deviceIdleController);
                this.mConstants = deviceIdleController.new Constants();
            }
            return this.mConstants;
        }

        public long getElapsedRealtime() {
            return SystemClock.elapsedRealtime();
        }

        public LocationManager getLocationManager() {
            if (this.mLocationManager == null) {
                this.mLocationManager = (LocationManager) this.mContext.getSystemService(LocationManager.class);
            }
            return this.mLocationManager;
        }

        public MyHandler getHandler(DeviceIdleController deviceIdleController) {
            Objects.requireNonNull(deviceIdleController);
            return deviceIdleController.new MyHandler(AppSchedulingModuleThread.getHandler().getLooper());
        }

        public Sensor getMotionSensor() {
            SensorManager sensorManager = getSensorManager();
            int integer = this.mContext.getResources().getInteger(R.integer.config_burnInProtectionMinVerticalOffset);
            Sensor defaultSensor = integer > 0 ? sensorManager.getDefaultSensor(integer, true) : null;
            if (defaultSensor == null && this.mContext.getResources().getBoolean(R.bool.config_attachNavBarToAppDuringTransition)) {
                defaultSensor = sensorManager.getDefaultSensor(26, true);
            }
            return defaultSensor == null ? sensorManager.getDefaultSensor(17, true) : defaultSensor;
        }

        public PowerManager getPowerManager() {
            return (PowerManager) this.mContext.getSystemService(PowerManager.class);
        }

        public SensorManager getSensorManager() {
            return (SensorManager) this.mContext.getSystemService(SensorManager.class);
        }

        public TelephonyManager getTelephonyManager() {
            return (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
        }

        public ConstraintController getConstraintController(Handler handler, DeviceIdleInternal deviceIdleInternal) {
            if (this.mContext.getPackageManager().hasSystemFeature("android.software.leanback_only")) {
                return new TvConstraintController(this.mContext, handler);
            }
            return null;
        }

        public boolean isLocationPrefetchEnabled() {
            return this.mContext.getResources().getBoolean(R.bool.config_audio_ringer_mode_affects_alarm_stream);
        }

        public boolean useMotionSensor() {
            return this.mContext.getResources().getBoolean(R.bool.config_autoBrightnessResetAmbientLuxAfterWarmUp);
        }
    }

    public DeviceIdleController(Context context, Injector injector) {
        super(context);
        this.mNumBlockingConstraints = 0;
        this.mConstraints = new ArrayMap();
        this.mLightBatteryLevel = new BatteryLevel(-1.0f, -1.0f, 0L, 0L);
        this.mDeepBatteryLevel = new BatteryLevel(-1.0f, -1.0f, 0L, 0L);
        this.mPowerSaveWhitelistAppsExceptIdle = new ArrayMap();
        this.mPowerSaveWhitelistUserAppsExceptIdle = new ArraySet();
        this.mPowerSaveWhitelistApps = new ArrayMap();
        this.mPowerSaveWhitelistUserApps = new ArrayMap();
        this.mPowerSaveWhitelistSystemAppIdsExceptIdle = new SparseBooleanArray();
        this.mPowerSaveWhitelistSystemAppIds = new SparseBooleanArray();
        this.mPowerSaveWhitelistExceptIdleAppIds = new SparseBooleanArray();
        this.mPowerSaveWhitelistExceptIdleAppIdArray = new int[0];
        this.mPowerSaveWhitelistAllAppIds = new SparseBooleanArray();
        this.mPowerSaveWhitelistAllAppIdArray = new int[0];
        this.mPowerSaveWhitelistUserAppIds = new SparseBooleanArray();
        this.mPowerSaveWhitelistUserAppIdArray = new int[0];
        this.mTempWhitelistAppIdEndTimes = new SparseArray();
        this.mTempWhitelistAppIdArray = new int[0];
        this.mRemovedFromSystemWhitelistApps = new ArrayMap();
        this.mStationaryListeners = new ArraySet();
        this.mTempAllowlistChangeListeners = new ArraySet();
        this.mEventCmds = new int[100];
        this.mEventTimes = new long[100];
        this.mEventReasons = new String[100];
        this.mUserWhitelistHistoryBuffer = new RingBuffer(UserWhitelistHistoryInfo.class, 100);
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.server.DeviceIdleController.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                boolean z;
                Uri data;
                String schemeSpecificPart;
                String action = intent.getAction();
                action.hashCode();
                z = true;
                switch (action) {
                    case "android.intent.action.BATTERY_CHANGED":
                        boolean booleanExtra = intent.getBooleanExtra("present", true);
                        boolean z2 = intent.getIntExtra("plugged", 0) != 0;
                        synchronized (DeviceIdleController.this) {
                            DeviceIdleController deviceIdleController = DeviceIdleController.this;
                            if (!booleanExtra || !z2) {
                                z = false;
                            }
                            deviceIdleController.updateChargingLocked(z);
                        }
                        return;
                    case "android.net.conn.CONNECTIVITY_CHANGE":
                        DeviceIdleController.this.updateConnectivityState(intent);
                        return;
                    case "android.intent.action.PACKAGE_REMOVED":
                        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false) || (data = intent.getData()) == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null) {
                            return;
                        }
                        DeviceIdleController.this.removePowerSaveWhitelistAppInternal(schemeSpecificPart, 5, null);
                        return;
                    default:
                        return;
                }
            }
        };
        this.mLightAlarmListener = new AlarmManager.OnAlarmListener() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda0
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                DeviceIdleController.this.lambda$new$0();
            }
        };
        this.mMotionRegistrationAlarmListener = new AlarmManager.OnAlarmListener() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda1
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                DeviceIdleController.this.lambda$new$1();
            }
        };
        this.mMotionTimeoutAlarmListener = new AlarmManager.OnAlarmListener() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda2
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                DeviceIdleController.this.lambda$new$2();
            }
        };
        this.mSensingTimeoutAlarmListener = new AlarmManager.OnAlarmListener() { // from class: com.android.server.DeviceIdleController.2
            @Override // android.app.AlarmManager.OnAlarmListener
            public void onAlarm() {
                synchronized (DeviceIdleController.this) {
                    if (DeviceIdleController.this.mState == 3) {
                        DeviceIdleController.this.becomeInactiveIfAppropriateLocked();
                    }
                }
            }
        };
        this.mDeepAlarmListener = new AlarmManager.OnAlarmListener() { // from class: com.android.server.DeviceIdleController.3
            @Override // android.app.AlarmManager.OnAlarmListener
            public void onAlarm() {
                synchronized (DeviceIdleController.this) {
                    DeviceIdleController.this.stepIdleStateLocked("s:alarm");
                }
            }
        };
        this.mIdleStartedDoneReceiver = new IIntentReceiver.Stub() { // from class: com.android.server.DeviceIdleController.4
            public void performReceive(Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, int i2) {
                if ("android.os.action.DEVICE_IDLE_MODE_CHANGED".equals(intent.getAction())) {
                    DeviceIdleController deviceIdleController = DeviceIdleController.this;
                    deviceIdleController.mHandler.sendEmptyMessageDelayed(8, deviceIdleController.mConstants.MIN_DEEP_MAINTENANCE_TIME);
                } else {
                    DeviceIdleController deviceIdleController2 = DeviceIdleController.this;
                    deviceIdleController2.mHandler.sendEmptyMessageDelayed(8, deviceIdleController2.mConstants.MIN_LIGHT_MAINTENANCE_TIME);
                }
            }
        };
        this.mInteractivityReceiver = new BroadcastReceiver() { // from class: com.android.server.DeviceIdleController.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                synchronized (DeviceIdleController.this) {
                    DeviceIdleController.this.updateInteractivityLocked();
                }
            }
        };
        this.mEmergencyCallListener = new EmergencyCallListener();
        this.mMotionListener = new MotionListener();
        this.mGenericLocationListener = new LocationListener() { // from class: com.android.server.DeviceIdleController.6
            @Override // android.location.LocationListener
            public void onProviderDisabled(String str) {
            }

            @Override // android.location.LocationListener
            public void onProviderEnabled(String str) {
            }

            @Override // android.location.LocationListener
            public void onStatusChanged(String str, int i, Bundle bundle) {
            }

            @Override // android.location.LocationListener
            public void onLocationChanged(Location location) {
                synchronized (DeviceIdleController.this) {
                    DeviceIdleController.this.receivedGenericLocationLocked(location);
                }
            }
        };
        this.mGpsLocationListener = new LocationListener() { // from class: com.android.server.DeviceIdleController.7
            @Override // android.location.LocationListener
            public void onProviderDisabled(String str) {
            }

            @Override // android.location.LocationListener
            public void onProviderEnabled(String str) {
            }

            @Override // android.location.LocationListener
            public void onStatusChanged(String str, int i, Bundle bundle) {
            }

            @Override // android.location.LocationListener
            public void onLocationChanged(Location location) {
                synchronized (DeviceIdleController.this) {
                    DeviceIdleController.this.receivedGpsLocationLocked(location);
                }
            }
        };
        this.mScreenObserver = new ActivityTaskManagerInternal.ScreenObserver() { // from class: com.android.server.DeviceIdleController.8
            @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
            public void onAwakeStateChanged(boolean z) {
            }

            @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
            public void onKeyguardStateChanged(boolean z) {
                synchronized (DeviceIdleController.this) {
                    DeviceIdleController.this.keyguardShowingLocked(z);
                }
            }
        };
        this.mInjector = injector;
        this.mConfigFile = new AtomicFile(new File(getSystemDir(), "deviceidle.xml"));
        this.mHandler = injector.getHandler(this);
        AppStateTrackerImpl appStateTracker = injector.getAppStateTracker(context, AppSchedulingModuleThread.get().getLooper());
        this.mAppStateTracker = appStateTracker;
        LocalServices.addService(AppStateTracker.class, appStateTracker);
        this.mIsLocationPrefetchEnabled = injector.isLocationPrefetchEnabled();
        this.mUseMotionSensor = injector.useMotionSensor();
    }

    public DeviceIdleController(Context context) {
        this(context, new Injector(context));
    }

    public boolean isAppOnWhitelistInternal(int i) {
        boolean z;
        synchronized (this) {
            z = Arrays.binarySearch(this.mPowerSaveWhitelistAllAppIdArray, i) >= 0;
        }
        return z;
    }

    public int[] getPowerSaveWhitelistUserAppIds() {
        int[] iArr;
        synchronized (this) {
            iArr = this.mPowerSaveWhitelistUserAppIdArray;
        }
        return iArr;
    }

    public static File getSystemDir() {
        return new File(Environment.getDataDirectory(), "system");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10, types: [android.os.IBinder, com.android.server.DeviceIdleController$BinderService] */
    @Override // com.android.server.SystemService
    public void onStart() {
        PackageManager packageManager = getContext().getPackageManager();
        synchronized (this) {
            boolean z = getContext().getResources().getBoolean(R.bool.config_dozeAlwaysOnDisplayAvailable);
            this.mDeepEnabled = z;
            this.mLightEnabled = z;
            SystemConfig systemConfig = SystemConfig.getInstance();
            ArraySet allowInPowerSaveExceptIdle = systemConfig.getAllowInPowerSaveExceptIdle();
            for (int i = 0; i < allowInPowerSaveExceptIdle.size(); i++) {
                try {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo((String) allowInPowerSaveExceptIdle.valueAt(i), 1048576);
                    int appId = UserHandle.getAppId(applicationInfo.uid);
                    this.mPowerSaveWhitelistAppsExceptIdle.put(applicationInfo.packageName, Integer.valueOf(appId));
                    this.mPowerSaveWhitelistSystemAppIdsExceptIdle.put(appId, true);
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
            ArraySet allowInPowerSave = systemConfig.getAllowInPowerSave();
            for (int i2 = 0; i2 < allowInPowerSave.size(); i2++) {
                try {
                    ApplicationInfo applicationInfo2 = packageManager.getApplicationInfo((String) allowInPowerSave.valueAt(i2), 1048576);
                    int appId2 = UserHandle.getAppId(applicationInfo2.uid);
                    this.mPowerSaveWhitelistAppsExceptIdle.put(applicationInfo2.packageName, Integer.valueOf(appId2));
                    this.mPowerSaveWhitelistSystemAppIdsExceptIdle.put(appId2, true);
                    this.mPowerSaveWhitelistApps.put(applicationInfo2.packageName, Integer.valueOf(appId2));
                    this.mPowerSaveWhitelistSystemAppIds.put(appId2, true);
                } catch (PackageManager.NameNotFoundException unused2) {
                }
            }
            this.mConstants = this.mInjector.getConstants(this);
            readConfigFileLocked();
            updateWhitelistAppIdsLocked();
            this.mNetworkConnected = true;
            this.mScreenOn = true;
            this.mScreenLocked = false;
            this.mCharging = true;
            this.mActiveReason = 0;
            moveToStateLocked(0, "boot");
            moveToLightStateLocked(0, "boot");
            this.mInactiveTimeout = this.mConstants.INACTIVE_TIMEOUT;
            this.mPreIdleFactor = 1.0f;
            this.mLastPreIdleFactor = 1.0f;
        }
        ?? binderService = new BinderService();
        this.mBinderService = binderService;
        publishBinderService("deviceidle", binderService);
        LocalService localService = new LocalService();
        this.mLocalService = localService;
        publishLocalService(DeviceIdleInternal.class, localService);
        publishLocalService(PowerAllowlistInternal.class, new LocalPowerAllowlistService());
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i != 500) {
            if (i == 1000) {
                resetBatteryLevel();
                return;
            }
            return;
        }
        synchronized (this) {
            this.mAlarmManager = this.mInjector.getAlarmManager();
            this.mLocalAlarmManager = (AlarmManagerInternal) getLocalService(AlarmManagerInternal.class);
            this.mBatteryStats = BatteryStatsService.getService();
            this.mLocalBatteryManager = (BatteryManagerInternal) getLocalService(BatteryManagerInternal.class);
            this.mLocalActivityManager = (ActivityManagerInternal) getLocalService(ActivityManagerInternal.class);
            this.mLocalActivityTaskManager = (ActivityTaskManagerInternal) getLocalService(ActivityTaskManagerInternal.class);
            this.mPackageManagerInternal = (PackageManagerInternal) getLocalService(PackageManagerInternal.class);
            this.mLocalPowerManager = (PowerManagerInternal) getLocalService(PowerManagerInternal.class);
            PowerManager powerManager = this.mInjector.getPowerManager();
            this.mPowerManager = powerManager;
            PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "deviceidle_maint");
            this.mActiveIdleWakeLock = newWakeLock;
            newWakeLock.setReferenceCounted(false);
            PowerManager.WakeLock newWakeLock2 = this.mPowerManager.newWakeLock(1, "deviceidle_going_idle");
            this.mGoingIdleWakeLock = newWakeLock2;
            newWakeLock2.setReferenceCounted(true);
            this.mNetworkPolicyManager = INetworkPolicyManager.Stub.asInterface(ServiceManager.getService("netpolicy"));
            this.mNetworkPolicyManagerInternal = (NetworkPolicyManagerInternal) getLocalService(NetworkPolicyManagerInternal.class);
            this.mSensorManager = this.mInjector.getSensorManager();
            if (this.mUseMotionSensor) {
                this.mMotionSensor = this.mInjector.getMotionSensor();
            }
            if (this.mIsLocationPrefetchEnabled) {
                this.mLocationRequest = new LocationRequest.Builder(0L).setQuality(100).setMaxUpdates(1).build();
            }
            ConstraintController constraintController = this.mInjector.getConstraintController(this.mHandler, (DeviceIdleInternal) getLocalService(LocalService.class));
            this.mConstraintController = constraintController;
            if (constraintController != null) {
                constraintController.start();
            }
            this.mAnyMotionDetector = this.mInjector.getAnyMotionDetector(this.mHandler, this.mSensorManager, this, getContext().getResources().getInteger(R.integer.config_cameraLaunchGestureSensorType) / 100.0f);
            this.mAppStateTracker.onSystemServicesReady();
            Bundle bundle = BroadcastOptions.makeBasic().setDeliveryGroupPolicy(1).setDeferralPolicy(2).toBundle();
            Intent intent = new Intent("android.os.action.DEVICE_IDLE_MODE_CHANGED");
            this.mIdleIntent = intent;
            intent.addFlags(1342177280);
            Intent intent2 = new Intent("android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED");
            this.mLightIdleIntent = intent2;
            intent2.addFlags(1342177280);
            this.mLightIdleIntentOptions = bundle;
            this.mIdleIntentOptions = bundle;
            Intent intent3 = new Intent("android.os.action.POWER_SAVE_WHITELIST_CHANGED");
            this.mPowerSaveWhitelistChangedIntent = intent3;
            intent3.addFlags(1073741824);
            Intent intent4 = new Intent("android.os.action.POWER_SAVE_TEMP_WHITELIST_CHANGED");
            this.mPowerSaveTempWhitelistChangedIntent = intent4;
            intent4.addFlags(1073741824);
            this.mPowerSaveWhitelistChangedOptions = bundle;
            this.mPowerSaveTempWhilelistChangedOptions = bundle;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
            getContext().registerReceiver(this.mReceiver, intentFilter);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter2.addDataScheme("package");
            getContext().registerReceiver(this.mReceiver, intentFilter2);
            IntentFilter intentFilter3 = new IntentFilter();
            intentFilter3.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            getContext().registerReceiver(this.mReceiver, intentFilter3);
            IntentFilter intentFilter4 = new IntentFilter();
            intentFilter4.addAction("android.intent.action.SCREEN_OFF");
            intentFilter4.addAction("android.intent.action.SCREEN_ON");
            getContext().registerReceiver(this.mInteractivityReceiver, intentFilter4);
            this.mLocalActivityManager.setDeviceIdleAllowlist(this.mPowerSaveWhitelistAllAppIdArray, this.mPowerSaveWhitelistExceptIdleAppIdArray);
            this.mLocalPowerManager.setDeviceIdleWhitelist(this.mPowerSaveWhitelistAllAppIdArray);
            this.mLocalPowerManager.registerLowPowerModeObserver(15, new Consumer() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DeviceIdleController.this.lambda$onBootPhase$3((PowerSaveState) obj);
                }
            });
            updateQuickDozeFlagLocked(this.mLocalPowerManager.getLowPowerState(15).batterySaverEnabled);
            this.mLocalActivityTaskManager.registerScreenObserver(this.mScreenObserver);
            this.mInjector.getTelephonyManager().registerTelephonyCallback(AppSchedulingModuleThread.getExecutor(), this.mEmergencyCallListener);
            passWhiteListsToForceAppStandbyTrackerLocked();
            updateInteractivityLocked();
        }
        updateConnectivityState(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootPhase$3(PowerSaveState powerSaveState) {
        synchronized (this) {
            updateQuickDozeFlagLocked(powerSaveState.batterySaverEnabled);
        }
    }

    public boolean hasMotionSensor() {
        return this.mUseMotionSensor && this.mMotionSensor != null;
    }

    public final void registerDeviceIdleConstraintInternal(IDeviceIdleConstraint iDeviceIdleConstraint, String str, int i) {
        int i2;
        if (i == 0) {
            i2 = 0;
        } else {
            if (i != 1) {
                Slog.wtf("DeviceIdleController", "Registering device-idle constraint with invalid type: " + i);
                return;
            }
            i2 = 3;
        }
        synchronized (this) {
            if (this.mConstraints.containsKey(iDeviceIdleConstraint)) {
                Slog.e("DeviceIdleController", "Re-registering device-idle constraint: " + iDeviceIdleConstraint + ".");
                return;
            }
            this.mConstraints.put(iDeviceIdleConstraint, new DeviceIdleConstraintTracker(str, i2));
            updateActiveConstraintsLocked();
        }
    }

    public final void unregisterDeviceIdleConstraintInternal(IDeviceIdleConstraint iDeviceIdleConstraint) {
        synchronized (this) {
            onConstraintStateChangedLocked(iDeviceIdleConstraint, false);
            setConstraintMonitoringLocked(iDeviceIdleConstraint, false);
            this.mConstraints.remove(iDeviceIdleConstraint);
        }
    }

    public final void onConstraintStateChangedLocked(IDeviceIdleConstraint iDeviceIdleConstraint, boolean z) {
        DeviceIdleConstraintTracker deviceIdleConstraintTracker = (DeviceIdleConstraintTracker) this.mConstraints.get(iDeviceIdleConstraint);
        if (deviceIdleConstraintTracker == null) {
            Slog.e("DeviceIdleController", "device-idle constraint " + iDeviceIdleConstraint + " has not been registered.");
            return;
        }
        if (z == deviceIdleConstraintTracker.active || !deviceIdleConstraintTracker.monitoring) {
            return;
        }
        deviceIdleConstraintTracker.active = z;
        int i = this.mNumBlockingConstraints + (z ? 1 : -1);
        this.mNumBlockingConstraints = i;
        if (i == 0) {
            if (this.mState == 0) {
                becomeInactiveIfAppropriateLocked();
                return;
            }
            long j = this.mNextAlarmTime;
            if (j == 0 || j < SystemClock.elapsedRealtime()) {
                stepIdleStateLocked("s:" + deviceIdleConstraintTracker.name);
            }
        }
    }

    public final void setConstraintMonitoringLocked(IDeviceIdleConstraint iDeviceIdleConstraint, boolean z) {
        DeviceIdleConstraintTracker deviceIdleConstraintTracker = (DeviceIdleConstraintTracker) this.mConstraints.get(iDeviceIdleConstraint);
        if (deviceIdleConstraintTracker.monitoring != z) {
            deviceIdleConstraintTracker.monitoring = z;
            updateActiveConstraintsLocked();
            this.mHandler.obtainMessage(10, z ? 1 : 0, -1, iDeviceIdleConstraint).sendToTarget();
        }
    }

    public final void updateActiveConstraintsLocked() {
        this.mNumBlockingConstraints = 0;
        for (int i = 0; i < this.mConstraints.size(); i++) {
            IDeviceIdleConstraint iDeviceIdleConstraint = (IDeviceIdleConstraint) this.mConstraints.keyAt(i);
            DeviceIdleConstraintTracker deviceIdleConstraintTracker = (DeviceIdleConstraintTracker) this.mConstraints.valueAt(i);
            boolean z = deviceIdleConstraintTracker.minState == this.mState;
            if (z != deviceIdleConstraintTracker.monitoring) {
                setConstraintMonitoringLocked(iDeviceIdleConstraint, z);
                deviceIdleConstraintTracker.active = z;
            }
            if (deviceIdleConstraintTracker.monitoring && deviceIdleConstraintTracker.active) {
                this.mNumBlockingConstraints++;
            }
        }
    }

    public final int addPowerSaveWhitelistAppsInternal(List list, int i, BinderCaller binderCaller) {
        int i2;
        synchronized (this) {
            int i3 = 0;
            i2 = 0;
            for (int size = list.size() - 1; size >= 0; size--) {
                String str = (String) list.get(size);
                if (str != null) {
                    try {
                        int appId = UserHandle.getAppId(getContext().getPackageManager().getApplicationInfo(str, 4194304).uid);
                        if (this.mPowerSaveWhitelistUserApps.put(str, Integer.valueOf(appId)) == null) {
                            this.mUserWhitelistHistoryBuffer.append(new UserWhitelistHistoryInfo(i, binderCaller, new TargetPkg(appId, str)));
                            i3++;
                            Counter.logIncrement("battery.value_app_added_to_power_allowlist");
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                        Slog.e("DeviceIdleController", "Tried to add unknown package to power save whitelist: " + str);
                    }
                }
                i2++;
            }
            if (i3 > 0) {
                reportPowerSaveWhitelistChangedLocked();
                updateWhitelistAppIdsLocked();
                writeConfigFileLocked();
            }
        }
        return list.size() - i2;
    }

    public boolean removePowerSaveWhitelistAppInternal(String str, int i, BinderCaller binderCaller) {
        synchronized (this) {
            int i2 = 0;
            if (this.mPowerSaveWhitelistUserApps.remove(str) == null) {
                return false;
            }
            try {
                i2 = UserHandle.getAppId(getContext().getPackageManager().getApplicationInfo(str, 4194304).uid);
            } catch (PackageManager.NameNotFoundException unused) {
                Slog.e("DeviceIdleController", "Tried to get uid from package name but failed");
            }
            this.mUserWhitelistHistoryBuffer.append(new UserWhitelistHistoryInfo(i, binderCaller, new TargetPkg(i2, str)));
            reportPowerSaveWhitelistChangedLocked();
            updateWhitelistAppIdsLocked();
            writeConfigFileLocked();
            Counter.logIncrement("battery.value_app_removed_from_power_allowlist");
            return true;
        }
    }

    public boolean getPowerSaveWhitelistAppInternal(String str) {
        boolean containsKey;
        synchronized (this) {
            containsKey = this.mPowerSaveWhitelistUserApps.containsKey(str);
        }
        return containsKey;
    }

    public void resetSystemPowerWhitelistInternal() {
        synchronized (this) {
            this.mPowerSaveWhitelistApps.putAll(this.mRemovedFromSystemWhitelistApps);
            this.mRemovedFromSystemWhitelistApps.clear();
            reportPowerSaveWhitelistChangedLocked();
            updateWhitelistAppIdsLocked();
            writeConfigFileLocked();
        }
    }

    public boolean restoreSystemPowerWhitelistAppInternal(String str) {
        synchronized (this) {
            if (!this.mRemovedFromSystemWhitelistApps.containsKey(str)) {
                return false;
            }
            this.mPowerSaveWhitelistApps.put(str, (Integer) this.mRemovedFromSystemWhitelistApps.remove(str));
            reportPowerSaveWhitelistChangedLocked();
            updateWhitelistAppIdsLocked();
            writeConfigFileLocked();
            return true;
        }
    }

    public boolean removeSystemPowerWhitelistAppInternal(String str) {
        synchronized (this) {
            if (!this.mPowerSaveWhitelistApps.containsKey(str)) {
                return false;
            }
            this.mRemovedFromSystemWhitelistApps.put(str, (Integer) this.mPowerSaveWhitelistApps.remove(str));
            reportPowerSaveWhitelistChangedLocked();
            updateWhitelistAppIdsLocked();
            writeConfigFileLocked();
            return true;
        }
    }

    public boolean addPowerSaveWhitelistExceptIdleInternal(String str) {
        synchronized (this) {
            try {
                try {
                    if (this.mPowerSaveWhitelistAppsExceptIdle.put(str, Integer.valueOf(UserHandle.getAppId(getContext().getPackageManager().getApplicationInfo(str, 4194304).uid))) == null) {
                        this.mPowerSaveWhitelistUserAppsExceptIdle.add(str);
                        reportPowerSaveWhitelistChangedLocked();
                        this.mPowerSaveWhitelistExceptIdleAppIdArray = buildAppIdArray(this.mPowerSaveWhitelistAppsExceptIdle, this.mPowerSaveWhitelistUserApps, this.mPowerSaveWhitelistExceptIdleAppIds);
                        passWhiteListsToForceAppStandbyTrackerLocked();
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    public void resetPowerSaveWhitelistExceptIdleInternal() {
        synchronized (this) {
            if (this.mPowerSaveWhitelistAppsExceptIdle.removeAll(this.mPowerSaveWhitelistUserAppsExceptIdle)) {
                reportPowerSaveWhitelistChangedLocked();
                this.mPowerSaveWhitelistExceptIdleAppIdArray = buildAppIdArray(this.mPowerSaveWhitelistAppsExceptIdle, this.mPowerSaveWhitelistUserApps, this.mPowerSaveWhitelistExceptIdleAppIds);
                this.mPowerSaveWhitelistUserAppsExceptIdle.clear();
                passWhiteListsToForceAppStandbyTrackerLocked();
            }
        }
    }

    public boolean getPowerSaveWhitelistExceptIdleInternal(String str) {
        boolean containsKey;
        synchronized (this) {
            containsKey = this.mPowerSaveWhitelistAppsExceptIdle.containsKey(str);
        }
        return containsKey;
    }

    public final String[] getSystemPowerWhitelistExceptIdleInternal(final int i, final int i2) {
        String[] strArr;
        synchronized (this) {
            int size = this.mPowerSaveWhitelistAppsExceptIdle.size();
            strArr = new String[size];
            for (int i3 = 0; i3 < size; i3++) {
                strArr[i3] = (String) this.mPowerSaveWhitelistAppsExceptIdle.keyAt(i3);
            }
        }
        return (String[]) ArrayUtils.filter(strArr, new IntFunction() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda8
            @Override // java.util.function.IntFunction
            public final Object apply(int i4) {
                String[] lambda$getSystemPowerWhitelistExceptIdleInternal$4;
                lambda$getSystemPowerWhitelistExceptIdleInternal$4 = DeviceIdleController.lambda$getSystemPowerWhitelistExceptIdleInternal$4(i4);
                return lambda$getSystemPowerWhitelistExceptIdleInternal$4;
            }
        }, new Predicate() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getSystemPowerWhitelistExceptIdleInternal$5;
                lambda$getSystemPowerWhitelistExceptIdleInternal$5 = DeviceIdleController.this.lambda$getSystemPowerWhitelistExceptIdleInternal$5(i, i2, (String) obj);
                return lambda$getSystemPowerWhitelistExceptIdleInternal$5;
            }
        });
    }

    public static /* synthetic */ String[] lambda$getSystemPowerWhitelistExceptIdleInternal$4(int i) {
        return new String[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getSystemPowerWhitelistExceptIdleInternal$5(int i, int i2, String str) {
        return !this.mPackageManagerInternal.filterAppAccess(str, i, i2);
    }

    public final String[] getSystemPowerWhitelistInternal(final int i, final int i2) {
        String[] strArr;
        synchronized (this) {
            int size = this.mPowerSaveWhitelistApps.size();
            strArr = new String[size];
            for (int i3 = 0; i3 < size; i3++) {
                strArr[i3] = (String) this.mPowerSaveWhitelistApps.keyAt(i3);
            }
        }
        return (String[]) ArrayUtils.filter(strArr, new IntFunction() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda4
            @Override // java.util.function.IntFunction
            public final Object apply(int i4) {
                String[] lambda$getSystemPowerWhitelistInternal$6;
                lambda$getSystemPowerWhitelistInternal$6 = DeviceIdleController.lambda$getSystemPowerWhitelistInternal$6(i4);
                return lambda$getSystemPowerWhitelistInternal$6;
            }
        }, new Predicate() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getSystemPowerWhitelistInternal$7;
                lambda$getSystemPowerWhitelistInternal$7 = DeviceIdleController.this.lambda$getSystemPowerWhitelistInternal$7(i, i2, (String) obj);
                return lambda$getSystemPowerWhitelistInternal$7;
            }
        });
    }

    public static /* synthetic */ String[] lambda$getSystemPowerWhitelistInternal$6(int i) {
        return new String[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getSystemPowerWhitelistInternal$7(int i, int i2, String str) {
        return !this.mPackageManagerInternal.filterAppAccess(str, i, i2);
    }

    public final String[] getRemovedSystemPowerWhitelistAppsInternal(final int i, final int i2) {
        String[] strArr;
        synchronized (this) {
            int size = this.mRemovedFromSystemWhitelistApps.size();
            strArr = new String[size];
            for (int i3 = 0; i3 < size; i3++) {
                strArr[i3] = (String) this.mRemovedFromSystemWhitelistApps.keyAt(i3);
            }
        }
        return (String[]) ArrayUtils.filter(strArr, new IntFunction() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda10
            @Override // java.util.function.IntFunction
            public final Object apply(int i4) {
                String[] lambda$getRemovedSystemPowerWhitelistAppsInternal$8;
                lambda$getRemovedSystemPowerWhitelistAppsInternal$8 = DeviceIdleController.lambda$getRemovedSystemPowerWhitelistAppsInternal$8(i4);
                return lambda$getRemovedSystemPowerWhitelistAppsInternal$8;
            }
        }, new Predicate() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getRemovedSystemPowerWhitelistAppsInternal$9;
                lambda$getRemovedSystemPowerWhitelistAppsInternal$9 = DeviceIdleController.this.lambda$getRemovedSystemPowerWhitelistAppsInternal$9(i, i2, (String) obj);
                return lambda$getRemovedSystemPowerWhitelistAppsInternal$9;
            }
        });
    }

    public static /* synthetic */ String[] lambda$getRemovedSystemPowerWhitelistAppsInternal$8(int i) {
        return new String[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getRemovedSystemPowerWhitelistAppsInternal$9(int i, int i2, String str) {
        return !this.mPackageManagerInternal.filterAppAccess(str, i, i2);
    }

    public final String[] getUserPowerWhitelistInternal(final int i, final int i2) {
        String[] strArr;
        synchronized (this) {
            strArr = new String[this.mPowerSaveWhitelistUserApps.size()];
            for (int i3 = 0; i3 < this.mPowerSaveWhitelistUserApps.size(); i3++) {
                strArr[i3] = (String) this.mPowerSaveWhitelistUserApps.keyAt(i3);
            }
        }
        return (String[]) ArrayUtils.filter(strArr, new IntFunction() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda12
            @Override // java.util.function.IntFunction
            public final Object apply(int i4) {
                String[] lambda$getUserPowerWhitelistInternal$10;
                lambda$getUserPowerWhitelistInternal$10 = DeviceIdleController.lambda$getUserPowerWhitelistInternal$10(i4);
                return lambda$getUserPowerWhitelistInternal$10;
            }
        }, new Predicate() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getUserPowerWhitelistInternal$11;
                lambda$getUserPowerWhitelistInternal$11 = DeviceIdleController.this.lambda$getUserPowerWhitelistInternal$11(i, i2, (String) obj);
                return lambda$getUserPowerWhitelistInternal$11;
            }
        });
    }

    public static /* synthetic */ String[] lambda$getUserPowerWhitelistInternal$10(int i) {
        return new String[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getUserPowerWhitelistInternal$11(int i, int i2, String str) {
        return !this.mPackageManagerInternal.filterAppAccess(str, i, i2);
    }

    public final String[] getFullPowerWhitelistExceptIdleInternal(final int i, final int i2) {
        String[] strArr;
        synchronized (this) {
            strArr = new String[this.mPowerSaveWhitelistAppsExceptIdle.size() + this.mPowerSaveWhitelistUserApps.size()];
            int i3 = 0;
            for (int i4 = 0; i4 < this.mPowerSaveWhitelistAppsExceptIdle.size(); i4++) {
                strArr[i3] = (String) this.mPowerSaveWhitelistAppsExceptIdle.keyAt(i4);
                i3++;
            }
            for (int i5 = 0; i5 < this.mPowerSaveWhitelistUserApps.size(); i5++) {
                strArr[i3] = (String) this.mPowerSaveWhitelistUserApps.keyAt(i5);
                i3++;
            }
        }
        return (String[]) ArrayUtils.filter(strArr, new IntFunction() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda14
            @Override // java.util.function.IntFunction
            public final Object apply(int i6) {
                String[] lambda$getFullPowerWhitelistExceptIdleInternal$12;
                lambda$getFullPowerWhitelistExceptIdleInternal$12 = DeviceIdleController.lambda$getFullPowerWhitelistExceptIdleInternal$12(i6);
                return lambda$getFullPowerWhitelistExceptIdleInternal$12;
            }
        }, new Predicate() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getFullPowerWhitelistExceptIdleInternal$13;
                lambda$getFullPowerWhitelistExceptIdleInternal$13 = DeviceIdleController.this.lambda$getFullPowerWhitelistExceptIdleInternal$13(i, i2, (String) obj);
                return lambda$getFullPowerWhitelistExceptIdleInternal$13;
            }
        });
    }

    public static /* synthetic */ String[] lambda$getFullPowerWhitelistExceptIdleInternal$12(int i) {
        return new String[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getFullPowerWhitelistExceptIdleInternal$13(int i, int i2, String str) {
        return !this.mPackageManagerInternal.filterAppAccess(str, i, i2);
    }

    public final String[] getFullPowerWhitelistInternal(final int i, final int i2) {
        String[] strArr;
        synchronized (this) {
            strArr = new String[this.mPowerSaveWhitelistApps.size() + this.mPowerSaveWhitelistUserApps.size()];
            int i3 = 0;
            for (int i4 = 0; i4 < this.mPowerSaveWhitelistApps.size(); i4++) {
                strArr[i3] = (String) this.mPowerSaveWhitelistApps.keyAt(i4);
                i3++;
            }
            for (int i5 = 0; i5 < this.mPowerSaveWhitelistUserApps.size(); i5++) {
                strArr[i3] = (String) this.mPowerSaveWhitelistUserApps.keyAt(i5);
                i3++;
            }
        }
        return (String[]) ArrayUtils.filter(strArr, new IntFunction() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda6
            @Override // java.util.function.IntFunction
            public final Object apply(int i6) {
                String[] lambda$getFullPowerWhitelistInternal$14;
                lambda$getFullPowerWhitelistInternal$14 = DeviceIdleController.lambda$getFullPowerWhitelistInternal$14(i6);
                return lambda$getFullPowerWhitelistInternal$14;
            }
        }, new Predicate() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getFullPowerWhitelistInternal$15;
                lambda$getFullPowerWhitelistInternal$15 = DeviceIdleController.this.lambda$getFullPowerWhitelistInternal$15(i, i2, (String) obj);
                return lambda$getFullPowerWhitelistInternal$15;
            }
        });
    }

    public static /* synthetic */ String[] lambda$getFullPowerWhitelistInternal$14(int i) {
        return new String[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getFullPowerWhitelistInternal$15(int i, int i2, String str) {
        return !this.mPackageManagerInternal.filterAppAccess(str, i, i2);
    }

    public boolean isPowerSaveWhitelistExceptIdleAppInternal(String str) {
        boolean z;
        synchronized (this) {
            z = this.mPowerSaveWhitelistAppsExceptIdle.containsKey(str) || this.mPowerSaveWhitelistUserApps.containsKey(str);
        }
        return z;
    }

    public boolean isPowerSaveWhitelistAppInternal(String str) {
        boolean z;
        synchronized (this) {
            z = this.mPowerSaveWhitelistApps.containsKey(str) || this.mPowerSaveWhitelistUserApps.containsKey(str);
        }
        return z;
    }

    public int[] getAppIdWhitelistExceptIdleInternal() {
        int[] iArr;
        synchronized (this) {
            iArr = this.mPowerSaveWhitelistExceptIdleAppIdArray;
        }
        return iArr;
    }

    public int[] getAppIdWhitelistInternal() {
        int[] iArr;
        synchronized (this) {
            iArr = this.mPowerSaveWhitelistAllAppIdArray;
        }
        return iArr;
    }

    public int[] getAppIdUserWhitelistInternal() {
        int[] iArr;
        synchronized (this) {
            iArr = this.mPowerSaveWhitelistUserAppIdArray;
        }
        return iArr;
    }

    public int[] getAppIdTempWhitelistInternal() {
        int[] iArr;
        synchronized (this) {
            iArr = this.mTempWhitelistAppIdArray;
        }
        return iArr;
    }

    public final int getTempAllowListType(int i, int i2) {
        if (i != -1) {
            return i != 102 ? i2 : this.mLocalActivityManager.getPushMessagingOverQuotaBehavior();
        }
        return -1;
    }

    public void addPowerSaveTempAllowlistAppChecked(String str, long j, int i, int i2, String str2) {
        getContext().enforceCallingOrSelfPermission("android.permission.CHANGE_DEVICE_IDLE_TEMP_WHITELIST", "No permission to change device idle whitelist");
        int callingUid = Binder.getCallingUid();
        int handleIncomingUser = ActivityManager.getService().handleIncomingUser(Binder.getCallingPid(), callingUid, i, false, false, "addPowerSaveTempWhitelistApp", (String) null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int tempAllowListType = getTempAllowListType(i2, 0);
            if (tempAllowListType != -1) {
                addPowerSaveTempAllowlistAppInternal(callingUid, str, j, tempAllowListType, handleIncomingUser, true, i2, str2);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void removePowerSaveTempAllowlistAppChecked(String str, int i) {
        getContext().enforceCallingOrSelfPermission("android.permission.CHANGE_DEVICE_IDLE_TEMP_WHITELIST", "No permission to change device idle whitelist");
        int handleIncomingUser = ActivityManager.getService().handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, false, "removePowerSaveTempWhitelistApp", (String) null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            removePowerSaveTempAllowlistAppInternal(str, handleIncomingUser);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void addPowerSaveTempAllowlistAppInternal(int i, String str, long j, int i2, int i3, boolean z, int i4, String str2) {
        try {
            addPowerSaveTempWhitelistAppDirectInternal(i, getContext().getPackageManager().getPackageUidAsUser(str, i3), j, i2, z, i4, str2);
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    public void addPowerSaveTempWhitelistAppDirectInternal(int i, int i2, long j, int i3, boolean z, int i4, String str) {
        boolean z2;
        boolean z3;
        int i5;
        int i6;
        String str2;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        int appId = UserHandle.getAppId(i2);
        synchronized (this) {
            long min = Math.min(j, this.mConstants.MAX_TEMP_APP_ALLOWLIST_DURATION_MS);
            Pair pair = (Pair) this.mTempWhitelistAppIdEndTimes.get(appId);
            z2 = false;
            boolean z4 = pair == null;
            if (z4) {
                pair = new Pair(new MutableLong(0L), str);
                this.mTempWhitelistAppIdEndTimes.put(appId, pair);
            }
            ((MutableLong) pair.first).value = elapsedRealtime + min;
            if (z4) {
                try {
                    this.mBatteryStats.noteEvent(32785, str, i2);
                } catch (RemoteException unused) {
                }
                postTempActiveTimeoutMessage(i2, min);
                updateTempWhitelistAppIdsLocked(i2, true, min, i3, i4, str, i);
                if (z) {
                    z2 = true;
                } else {
                    this.mHandler.obtainMessage(14, appId, i4, str).sendToTarget();
                }
                reportTempWhitelistChangedLocked(i2, true);
            } else {
                ActivityManagerInternal activityManagerInternal = this.mLocalActivityManager;
                if (activityManagerInternal != null) {
                    z3 = true;
                    i6 = appId;
                    str2 = str;
                    i5 = i4;
                    activityManagerInternal.updateDeviceIdleTempAllowlist((int[]) null, i2, true, min, i3, i4, str, i);
                }
            }
            z3 = true;
            i6 = appId;
            str2 = str;
            i5 = i4;
        }
        if (z2) {
            this.mNetworkPolicyManagerInternal.onTempPowerSaveWhitelistChange(i6, z3, i5, str2);
        }
    }

    public final void removePowerSaveTempAllowlistAppInternal(String str, int i) {
        try {
            removePowerSaveTempWhitelistAppDirectInternal(getContext().getPackageManager().getPackageUidAsUser(str, i));
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    public final void removePowerSaveTempWhitelistAppDirectInternal(int i) {
        int appId = UserHandle.getAppId(i);
        synchronized (this) {
            int indexOfKey = this.mTempWhitelistAppIdEndTimes.indexOfKey(appId);
            if (indexOfKey < 0) {
                return;
            }
            String str = (String) ((Pair) this.mTempWhitelistAppIdEndTimes.valueAt(indexOfKey)).second;
            this.mTempWhitelistAppIdEndTimes.removeAt(indexOfKey);
            onAppRemovedFromTempWhitelistLocked(i, str);
        }
    }

    public final void postTempActiveTimeoutMessage(int i, long j) {
        MyHandler myHandler = this.mHandler;
        myHandler.sendMessageDelayed(myHandler.obtainMessage(6, i, 0), j);
    }

    public void checkTempAppWhitelistTimeout(int i) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        int appId = UserHandle.getAppId(i);
        synchronized (this) {
            Pair pair = (Pair) this.mTempWhitelistAppIdEndTimes.get(appId);
            if (pair == null) {
                return;
            }
            Object obj = pair.first;
            if (elapsedRealtime >= ((MutableLong) obj).value) {
                this.mTempWhitelistAppIdEndTimes.delete(appId);
                onAppRemovedFromTempWhitelistLocked(i, (String) pair.second);
            } else {
                postTempActiveTimeoutMessage(i, ((MutableLong) obj).value - elapsedRealtime);
            }
        }
    }

    public final void onAppRemovedFromTempWhitelistLocked(int i, String str) {
        int appId = UserHandle.getAppId(i);
        updateTempWhitelistAppIdsLocked(i, false, 0L, 0, 0, str, -1);
        this.mHandler.obtainMessage(15, appId, 0).sendToTarget();
        reportTempWhitelistChangedLocked(i, false);
        try {
            this.mBatteryStats.noteEvent(16401, str, appId);
        } catch (RemoteException unused) {
        }
    }

    public void exitIdleInternal(String str) {
        synchronized (this) {
            this.mActiveReason = 5;
            becomeActiveLocked(str, Binder.getCallingUid());
        }
    }

    public boolean isNetworkConnected() {
        boolean z;
        synchronized (this) {
            z = this.mNetworkConnected;
        }
        return z;
    }

    public void updateConnectivityState(Intent intent) {
        ConnectivityManager connectivityManager;
        synchronized (this) {
            connectivityManager = this.mInjector.getConnectivityManager();
        }
        if (connectivityManager == null) {
            return;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        synchronized (this) {
            boolean z = false;
            if (activeNetworkInfo != null) {
                if (intent == null) {
                    z = activeNetworkInfo.isConnected();
                } else {
                    if (activeNetworkInfo.getType() != intent.getIntExtra("networkType", -1)) {
                        return;
                    } else {
                        z = !intent.getBooleanExtra("noConnectivity", false);
                    }
                }
            }
            if (z != this.mNetworkConnected) {
                this.mNetworkConnected = z;
                if (z && this.mLightState == 5) {
                    stepLightIdleStateLocked("network");
                }
            }
        }
    }

    public boolean isScreenOn() {
        boolean z;
        synchronized (this) {
            z = this.mScreenOn;
        }
        return z;
    }

    public void updateInteractivityLocked() {
        boolean isInteractive = this.mPowerManager.isInteractive();
        if (!isInteractive && this.mScreenOn) {
            this.mScreenOn = false;
            if (this.mForceIdle) {
                return;
            }
            becomeInactiveIfAppropriateLocked();
            return;
        }
        if (isInteractive) {
            this.mScreenOn = true;
            if (this.mForceIdle) {
                return;
            }
            if (this.mScreenLocked && this.mConstants.WAIT_FOR_UNLOCK) {
                return;
            }
            this.mActiveReason = 2;
            becomeActiveLocked(KnoxCustomManagerService.SCREEN, Process.myUid());
        }
    }

    public boolean isCharging() {
        boolean z;
        synchronized (this) {
            z = this.mCharging;
        }
        return z;
    }

    public void updateChargingLocked(boolean z) {
        if (!z && this.mCharging) {
            this.mCharging = false;
            if (this.mForceIdle) {
                return;
            }
            becomeInactiveIfAppropriateLocked();
            return;
        }
        if (z) {
            this.mCharging = z;
            if (this.mForceIdle) {
                return;
            }
            this.mActiveReason = 3;
            becomeActiveLocked("charging", Process.myUid());
        }
    }

    public boolean isQuickDozeEnabled() {
        boolean z;
        synchronized (this) {
            z = this.mQuickDozeActivated;
        }
        return z;
    }

    public void updateQuickDozeFlagLocked(boolean z) {
        int i;
        this.mQuickDozeActivated = z;
        this.mQuickDozeActivatedWhileIdling = z && ((i = this.mState) == 5 || i == 6);
        if (z) {
            becomeInactiveIfAppropriateLocked();
        }
    }

    public boolean isKeyguardShowing() {
        boolean z;
        synchronized (this) {
            z = this.mScreenLocked;
        }
        return z;
    }

    public void keyguardShowingLocked(boolean z) {
        if (this.mScreenLocked != z) {
            this.mScreenLocked = z;
            if (!this.mScreenOn || this.mForceIdle || z) {
                return;
            }
            this.mActiveReason = 4;
            becomeActiveLocked("unlocked", Process.myUid());
        }
    }

    public void scheduleReportActiveLocked(String str, int i) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(5, i, 0, str));
    }

    public void becomeActiveLocked(String str, int i) {
        becomeActiveLocked(str, i, this.mConstants.INACTIVE_TIMEOUT, true);
    }

    public final void becomeActiveLocked(String str, int i, long j, boolean z) {
        if (this.mState == 0 && this.mLightState == 0) {
            return;
        }
        moveToStateLocked(0, str);
        this.mInactiveTimeout = j;
        resetIdleManagementLocked();
        if (this.mLightState != 6) {
            this.mMaintenanceStartTime = 0L;
        }
        if (z) {
            moveToLightStateLocked(0, str);
            resetLightIdleManagementLocked();
            scheduleReportActiveLocked(str, i);
            addEvent(1, str);
        }
        resetBatteryLevel();
    }

    public void setDeepEnabledForTest(boolean z) {
        synchronized (this) {
            this.mDeepEnabled = z;
        }
    }

    public void setLightEnabledForTest(boolean z) {
        synchronized (this) {
            this.mLightEnabled = z;
        }
    }

    public final void verifyAlarmStateLocked() {
        if (this.mState == 0 && this.mNextAlarmTime != 0) {
            Slog.wtf("DeviceIdleController", "mState=ACTIVE but mNextAlarmTime=" + this.mNextAlarmTime);
        }
        if (this.mState != 5 && this.mLocalAlarmManager.isIdling()) {
            Slog.wtf("DeviceIdleController", "mState=" + stateToString(this.mState) + " but AlarmManager is idling");
        }
        if (this.mState == 5 && !this.mLocalAlarmManager.isIdling()) {
            Slog.wtf("DeviceIdleController", "mState=IDLE but AlarmManager is not idling");
        }
        if (this.mLightState != 0 || this.mNextLightAlarmTime == 0) {
            return;
        }
        Slog.wtf("DeviceIdleController", "mLightState=ACTIVE but mNextLightAlarmTime is " + TimeUtils.formatDuration(this.mNextLightAlarmTime - SystemClock.elapsedRealtime()) + " from now");
    }

    public void becomeInactiveIfAppropriateLocked() {
        verifyAlarmStateLocked();
        boolean z = this.mScreenOn && !(this.mConstants.WAIT_FOR_UNLOCK && this.mScreenLocked);
        boolean isEmergencyCallActive = this.mEmergencyCallListener.isEmergencyCallActive();
        if (this.mForceIdle || !(this.mCharging || z || isEmergencyCallActive)) {
            if (this.mDeepEnabled) {
                if (this.mQuickDozeActivated) {
                    int i = this.mState;
                    if (i == 7 || i == 5 || i == 6) {
                        return;
                    }
                    moveToStateLocked(7, "no activity");
                    resetIdleManagementLocked();
                    if (isUpcomingAlarmClock()) {
                        scheduleAlarmLocked((this.mAlarmManager.getNextWakeFromIdleTime() - this.mInjector.getElapsedRealtime()) + this.mConstants.QUICK_DOZE_DELAY_TIMEOUT);
                    } else {
                        scheduleAlarmLocked(this.mConstants.QUICK_DOZE_DELAY_TIMEOUT);
                    }
                } else if (this.mState == 0) {
                    moveToStateLocked(1, "no activity");
                    resetIdleManagementLocked();
                    long j = this.mInactiveTimeout;
                    if (shouldUseIdleTimeoutFactorLocked()) {
                        j = (long) (this.mPreIdleFactor * j);
                    }
                    if (isUpcomingAlarmClock()) {
                        scheduleAlarmLocked((this.mAlarmManager.getNextWakeFromIdleTime() - this.mInjector.getElapsedRealtime()) + j);
                    } else {
                        scheduleAlarmLocked(j);
                    }
                }
            }
            if (this.mLightState == 0 && this.mLightEnabled) {
                moveToLightStateLocked(1, "no activity");
                resetLightIdleManagementLocked();
                Constants constants = this.mConstants;
                scheduleLightAlarmLocked(constants.LIGHT_IDLE_AFTER_INACTIVE_TIMEOUT, constants.FLEX_TIME_SHORT, true);
            }
        }
    }

    public final void resetBatteryLevel() {
        Slog.i("DeviceIdleController", "resetBatteryLevel");
        float batteryLevelRaw = getBatteryLevelRaw();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mLightBatteryLevel.reset(batteryLevelRaw, elapsedRealtime);
        this.mDeepBatteryLevel.reset(batteryLevelRaw, elapsedRealtime);
    }

    public final void resetIdleManagementLocked() {
        this.mNextIdlePendingDelay = 0L;
        this.mNextIdleDelay = 0L;
        this.mIdleStartTime = 0L;
        this.mQuickDozeActivatedWhileIdling = false;
        cancelAlarmLocked();
        cancelSensingTimeoutAlarmLocked();
        cancelLocatingLocked();
        maybeStopMonitoringMotionLocked();
        this.mAnyMotionDetector.stop();
        updateActiveConstraintsLocked();
    }

    public final void resetLightIdleManagementLocked() {
        Constants constants = this.mConstants;
        this.mNextLightIdleDelay = constants.LIGHT_IDLE_TIMEOUT;
        this.mMaintenanceStartTime = 0L;
        this.mNextLightIdleDelayFlex = constants.LIGHT_IDLE_TIMEOUT_INITIAL_FLEX;
        this.mCurLightIdleBudget = constants.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET;
        cancelLightAlarmLocked();
    }

    public void exitForceIdleLocked() {
        if (this.mForceIdle) {
            this.mForceIdle = false;
            if (this.mScreenOn || this.mCharging) {
                this.mActiveReason = 6;
                becomeActiveLocked("exit-force", Process.myUid());
            }
        }
    }

    public void setLightStateForTest(int i) {
        synchronized (this) {
            this.mLightBatteryLevel.curr = getBatteryLevelRaw();
            this.mLightBatteryLevel.currTime = SystemClock.elapsedRealtime();
            Slog.i("DeviceIdleController", String.format("[LIGHT] %s to %s, battery=%s(%s/%d)", lightStateToString(this.mLightState), lightStateToString(i), floatToString(this.mLightBatteryLevel.curr), floatToString(getBatteryLevelDiff(this.mLightBatteryLevel.prev, this.mLightBatteryLevel.curr)), Long.valueOf(getDuration(this.mLightBatteryLevel.prevTime, this.mLightBatteryLevel.currTime))));
            this.mLightBatteryLevel.updatePrev();
            this.mLightState = i;
        }
    }

    public int getLightState() {
        int i;
        synchronized (this) {
            i = this.mLightState;
        }
        return i;
    }

    public void stepLightIdleStateLocked(String str) {
        int i = this.mLightState;
        if (i == 0 || i == 7) {
            return;
        }
        EventLogTags.writeDeviceIdleLightStep();
        if (this.mEmergencyCallListener.isEmergencyCallActive()) {
            Slog.wtf("DeviceIdleController", "stepLightIdleStateLocked called when emergency call is active");
            if (this.mLightState != 0) {
                this.mActiveReason = 8;
                becomeActiveLocked("emergency", Process.myUid());
                return;
            }
            return;
        }
        int i2 = this.mLightState;
        if (i2 == 1) {
            Constants constants = this.mConstants;
            this.mCurLightIdleBudget = constants.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET;
            this.mNextLightIdleDelay = constants.LIGHT_IDLE_TIMEOUT;
            this.mNextLightIdleDelayFlex = constants.LIGHT_IDLE_TIMEOUT_INITIAL_FLEX;
            this.mMaintenanceStartTime = 0L;
        } else {
            if (i2 == 4 || i2 == 5) {
                if (this.mNetworkConnected || i2 == 5) {
                    this.mActiveIdleOpCount = 1;
                    this.mActiveIdleWakeLock.acquire();
                    this.mMaintenanceStartTime = SystemClock.elapsedRealtime();
                    long j = this.mCurLightIdleBudget;
                    Constants constants2 = this.mConstants;
                    long j2 = constants2.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET;
                    if (j < j2) {
                        this.mCurLightIdleBudget = j2;
                    } else {
                        long j3 = constants2.LIGHT_IDLE_MAINTENANCE_MAX_BUDGET;
                        if (j > j3) {
                            this.mCurLightIdleBudget = j3;
                        }
                    }
                    scheduleLightAlarmLocked(this.mCurLightIdleBudget, constants2.FLEX_TIME_SHORT, true);
                    moveToLightStateLocked(6, str);
                    addEvent(3, null);
                    this.mHandler.sendEmptyMessage(4);
                    return;
                }
                scheduleLightAlarmLocked(this.mNextLightIdleDelay, this.mNextLightIdleDelayFlex / 2, true);
                moveToLightStateLocked(5, str);
                return;
            }
            if (i2 != 6) {
                return;
            }
        }
        if (this.mMaintenanceStartTime != 0) {
            long elapsedRealtime = SystemClock.elapsedRealtime() - this.mMaintenanceStartTime;
            long j4 = this.mConstants.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET;
            if (elapsedRealtime < j4) {
                this.mCurLightIdleBudget += j4 - elapsedRealtime;
            } else {
                this.mCurLightIdleBudget -= elapsedRealtime - j4;
            }
        }
        this.mMaintenanceStartTime = 0L;
        scheduleLightAlarmLocked(this.mNextLightIdleDelay, this.mNextLightIdleDelayFlex, false);
        Constants constants3 = this.mConstants;
        this.mNextLightIdleDelay = Math.min(constants3.LIGHT_MAX_IDLE_TIMEOUT, (long) (this.mNextLightIdleDelay * constants3.LIGHT_IDLE_FACTOR));
        Constants constants4 = this.mConstants;
        this.mNextLightIdleDelayFlex = Math.min(constants4.LIGHT_IDLE_TIMEOUT_MAX_FLEX, (long) (this.mNextLightIdleDelayFlex * constants4.LIGHT_IDLE_FACTOR));
        moveToLightStateLocked(4, str);
        addEvent(2, null);
        this.mGoingIdleWakeLock.acquire();
        this.mHandler.sendEmptyMessage(3);
    }

    public int getState() {
        int i;
        synchronized (this) {
            i = this.mState;
        }
        return i;
    }

    public final boolean isUpcomingAlarmClock() {
        return this.mInjector.getElapsedRealtime() + this.mConstants.MIN_TIME_TO_ALARM >= this.mAlarmManager.getNextWakeFromIdleTime();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x016b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void stepIdleStateLocked(String str) {
        long min;
        long j;
        EventLogTags.writeDeviceIdleStep();
        if (this.mEmergencyCallListener.isEmergencyCallActive()) {
            Slog.wtf("DeviceIdleController", "stepIdleStateLocked called when emergency call is active");
            if (this.mState != 0) {
                this.mActiveReason = 8;
                becomeActiveLocked("emergency", Process.myUid());
            }
            return;
        }
        if (isUpcomingAlarmClock()) {
            if (this.mState != 0) {
                this.mActiveReason = 7;
                becomeActiveLocked("alarm", Process.myUid());
                becomeInactiveIfAppropriateLocked();
                return;
            }
            return;
        }
        if (this.mNumBlockingConstraints == 0 || this.mForceIdle) {
            switch (this.mState) {
                case 1:
                    startMonitoringMotionLocked();
                    long j2 = this.mConstants.IDLE_AFTER_INACTIVE_TIMEOUT;
                    if (shouldUseIdleTimeoutFactorLocked()) {
                        j2 = (long) (this.mPreIdleFactor * j2);
                    }
                    scheduleAlarmLocked(j2);
                    moveToStateLocked(2, str);
                    break;
                case 2:
                    cancelLocatingLocked();
                    this.mLocated = false;
                    this.mLastGenericLocation = null;
                    this.mLastGpsLocation = null;
                    moveToStateLocked(3, str);
                    if (this.mUseMotionSensor && this.mAnyMotionDetector.hasSensor()) {
                        scheduleSensingTimeoutAlarmLocked(this.mConstants.SENSING_TIMEOUT);
                        this.mNotMoving = false;
                        this.mAnyMotionDetector.checkForAnyMotion();
                        break;
                    } else if (this.mNumBlockingConstraints != 0) {
                        cancelAlarmLocked();
                        break;
                    } else {
                        this.mNotMoving = true;
                        cancelSensingTimeoutAlarmLocked();
                        moveToStateLocked(4, str);
                        if (!this.mIsLocationPrefetchEnabled) {
                            scheduleAlarmLocked(this.mConstants.LOCATING_TIMEOUT);
                            LocationManager locationManager = this.mInjector.getLocationManager();
                            if (locationManager != null && locationManager.getProvider("fused") != null) {
                                locationManager.requestLocationUpdates("fused", this.mLocationRequest, AppSchedulingModuleThread.getExecutor(), this.mGenericLocationListener);
                                this.mLocating = true;
                            } else {
                                this.mHasFusedLocation = false;
                            }
                            if (locationManager != null && locationManager.getProvider("gps") != null) {
                                this.mHasGps = true;
                                locationManager.requestLocationUpdates("gps", 1000L, 5.0f, this.mGpsLocationListener, this.mHandler.getLooper());
                                this.mLocating = true;
                            } else {
                                this.mHasGps = false;
                            }
                            if (this.mLocating) {
                            }
                        } else {
                            this.mLocating = false;
                        }
                        cancelAlarmLocked();
                        cancelLocatingLocked();
                        this.mAnyMotionDetector.stop();
                        Constants constants = this.mConstants;
                        this.mNextIdlePendingDelay = constants.IDLE_PENDING_TIMEOUT;
                        this.mNextIdleDelay = constants.IDLE_TIMEOUT;
                        moveToStateLocked(5, str);
                        scheduleAlarmLocked(this.mNextIdleDelay);
                        this.mNextIdleDelay = (long) (this.mNextIdleDelay * this.mConstants.IDLE_FACTOR);
                        this.mIdleStartTime = SystemClock.elapsedRealtime();
                        min = Math.min(this.mNextIdleDelay, this.mConstants.MAX_IDLE_TIMEOUT);
                        this.mNextIdleDelay = min;
                        j = this.mConstants.IDLE_TIMEOUT;
                        if (min < j) {
                            this.mNextIdleDelay = j;
                        }
                        if (this.mLightState != 7) {
                            moveToLightStateLocked(7, "deep");
                            cancelLightAlarmLocked();
                        }
                        addEvent(4, null);
                        this.mGoingIdleWakeLock.acquire();
                        this.mHandler.sendEmptyMessage(2);
                        break;
                    }
                    break;
                case 3:
                    cancelSensingTimeoutAlarmLocked();
                    moveToStateLocked(4, str);
                    if (!this.mIsLocationPrefetchEnabled) {
                    }
                    cancelAlarmLocked();
                    cancelLocatingLocked();
                    this.mAnyMotionDetector.stop();
                    Constants constants2 = this.mConstants;
                    this.mNextIdlePendingDelay = constants2.IDLE_PENDING_TIMEOUT;
                    this.mNextIdleDelay = constants2.IDLE_TIMEOUT;
                    moveToStateLocked(5, str);
                    scheduleAlarmLocked(this.mNextIdleDelay);
                    this.mNextIdleDelay = (long) (this.mNextIdleDelay * this.mConstants.IDLE_FACTOR);
                    this.mIdleStartTime = SystemClock.elapsedRealtime();
                    min = Math.min(this.mNextIdleDelay, this.mConstants.MAX_IDLE_TIMEOUT);
                    this.mNextIdleDelay = min;
                    j = this.mConstants.IDLE_TIMEOUT;
                    if (min < j) {
                    }
                    if (this.mLightState != 7) {
                    }
                    addEvent(4, null);
                    this.mGoingIdleWakeLock.acquire();
                    this.mHandler.sendEmptyMessage(2);
                    break;
                case 4:
                    cancelAlarmLocked();
                    cancelLocatingLocked();
                    this.mAnyMotionDetector.stop();
                    Constants constants22 = this.mConstants;
                    this.mNextIdlePendingDelay = constants22.IDLE_PENDING_TIMEOUT;
                    this.mNextIdleDelay = constants22.IDLE_TIMEOUT;
                    moveToStateLocked(5, str);
                    scheduleAlarmLocked(this.mNextIdleDelay);
                    this.mNextIdleDelay = (long) (this.mNextIdleDelay * this.mConstants.IDLE_FACTOR);
                    this.mIdleStartTime = SystemClock.elapsedRealtime();
                    min = Math.min(this.mNextIdleDelay, this.mConstants.MAX_IDLE_TIMEOUT);
                    this.mNextIdleDelay = min;
                    j = this.mConstants.IDLE_TIMEOUT;
                    if (min < j) {
                    }
                    if (this.mLightState != 7) {
                    }
                    addEvent(4, null);
                    this.mGoingIdleWakeLock.acquire();
                    this.mHandler.sendEmptyMessage(2);
                    break;
                case 5:
                    this.mActiveIdleOpCount = 1;
                    this.mActiveIdleWakeLock.acquire();
                    moveToStateLocked(6, str);
                    scheduleAlarmLocked(this.mNextIdlePendingDelay);
                    this.mMaintenanceStartTime = SystemClock.elapsedRealtime();
                    Constants constants3 = this.mConstants;
                    long min2 = Math.min(constants3.MAX_IDLE_PENDING_TIMEOUT, (long) (this.mNextIdlePendingDelay * constants3.IDLE_PENDING_FACTOR));
                    this.mNextIdlePendingDelay = min2;
                    long j3 = this.mConstants.IDLE_PENDING_TIMEOUT;
                    if (min2 < j3) {
                        this.mNextIdlePendingDelay = j3;
                    }
                    addEvent(5, null);
                    this.mHandler.sendEmptyMessage(4);
                    break;
                case 6:
                    moveToStateLocked(5, str);
                    scheduleAlarmLocked(this.mNextIdleDelay);
                    this.mNextIdleDelay = (long) (this.mNextIdleDelay * this.mConstants.IDLE_FACTOR);
                    this.mIdleStartTime = SystemClock.elapsedRealtime();
                    min = Math.min(this.mNextIdleDelay, this.mConstants.MAX_IDLE_TIMEOUT);
                    this.mNextIdleDelay = min;
                    j = this.mConstants.IDLE_TIMEOUT;
                    if (min < j) {
                    }
                    if (this.mLightState != 7) {
                    }
                    addEvent(4, null);
                    this.mGoingIdleWakeLock.acquire();
                    this.mHandler.sendEmptyMessage(2);
                    break;
                case 7:
                    Constants constants222 = this.mConstants;
                    this.mNextIdlePendingDelay = constants222.IDLE_PENDING_TIMEOUT;
                    this.mNextIdleDelay = constants222.IDLE_TIMEOUT;
                    moveToStateLocked(5, str);
                    scheduleAlarmLocked(this.mNextIdleDelay);
                    this.mNextIdleDelay = (long) (this.mNextIdleDelay * this.mConstants.IDLE_FACTOR);
                    this.mIdleStartTime = SystemClock.elapsedRealtime();
                    min = Math.min(this.mNextIdleDelay, this.mConstants.MAX_IDLE_TIMEOUT);
                    this.mNextIdleDelay = min;
                    j = this.mConstants.IDLE_TIMEOUT;
                    if (min < j) {
                    }
                    if (this.mLightState != 7) {
                    }
                    addEvent(4, null);
                    this.mGoingIdleWakeLock.acquire();
                    this.mHandler.sendEmptyMessage(2);
                    break;
            }
        }
    }

    public final void moveToLightStateLocked(int i, String str) {
        this.mLightBatteryLevel.curr = getBatteryLevelRaw();
        this.mLightBatteryLevel.currTime = SystemClock.elapsedRealtime();
        Slog.i("DeviceIdleController", String.format("[LIGHT] %s to %s, reason=%s, battery=%s(%s/%d)", lightStateToString(this.mLightState), lightStateToString(i), str, floatToString(this.mLightBatteryLevel.curr), floatToString(getBatteryLevelDiff(this.mLightBatteryLevel.prev, this.mLightBatteryLevel.curr)), Long.valueOf(getDuration(this.mLightBatteryLevel.prevTime, this.mLightBatteryLevel.currTime))));
        this.mLightBatteryLevel.updatePrev();
        this.mLightState = i;
        EventLogTags.writeDeviceIdleLight(i, str);
        Trace.traceCounter(524288L, "DozeLightState", i);
    }

    public final void moveToStateLocked(int i, String str) {
        this.mDeepBatteryLevel.curr = getBatteryLevelRaw();
        this.mDeepBatteryLevel.currTime = SystemClock.elapsedRealtime();
        Slog.i("DeviceIdleController", String.format("[DEEP] %s to %s, reason=%s, battery=%s(%s/%d)", stateToString(this.mState), stateToString(i), str, floatToString(this.mDeepBatteryLevel.curr), floatToString(getBatteryLevelDiff(this.mDeepBatteryLevel.prev, this.mDeepBatteryLevel.curr)), Long.valueOf(getDuration(this.mDeepBatteryLevel.prevTime, this.mDeepBatteryLevel.currTime))));
        this.mDeepBatteryLevel.updatePrev();
        this.mState = i;
        EventLogTags.writeDeviceIdle(i, str);
        Trace.traceCounter(524288L, "DozeDeepState", i);
        updateActiveConstraintsLocked();
    }

    public float getBatteryLevelRaw() {
        BatteryManagerInternal batteryManagerInternal = this.mLocalBatteryManager;
        if (batteryManagerInternal != null) {
            int batteryLevelRaw = batteryManagerInternal.getBatteryLevelRaw();
            if (batteryLevelRaw >= 0 && batteryLevelRaw <= 10000) {
                return batteryLevelRaw / 100.0f;
            }
            Slog.w("DeviceIdleController", "getBatteryLevelRaw() : batteryLevelInt is invalid : " + batteryLevelRaw);
        } else {
            Slog.w("DeviceIdleController", "getBatteryLevelRaw() : mLocalBatteryManager is null");
        }
        return -1.0f;
    }

    public final float getBatteryLevelDiff(float f, float f2) {
        if (f < DisplayPowerController2.RATE_FROM_DOZE_TO_ON || f2 < DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            Slog.w("DeviceIdleController", "getBatteryLevelDiff() : curr(" + f2 + ") or prev(" + f + ") is invalid");
            return -1.0f;
        }
        if (f >= f2) {
            return f - f2;
        }
        Slog.w("DeviceIdleController", "getBatteryLevelDiff() : curr(" + f2 + ") is bigger than prev(" + f + ")");
        return -1.0f;
    }

    public final String floatToString(float f) {
        return f >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON ? String.format("%.2f", Float.valueOf(f)) : "invalid";
    }

    public void incActiveIdleOps() {
        synchronized (this) {
            this.mActiveIdleOpCount++;
        }
    }

    public void decActiveIdleOps() {
        synchronized (this) {
            int i = this.mActiveIdleOpCount - 1;
            this.mActiveIdleOpCount = i;
            if (i <= 0) {
                exitMaintenanceEarlyIfNeededLocked();
                this.mActiveIdleWakeLock.release();
            }
        }
    }

    public void setActiveIdleOpsForTest(int i) {
        synchronized (this) {
            this.mActiveIdleOpCount = i;
        }
    }

    public void setJobsActive(boolean z) {
        synchronized (this) {
            this.mJobsActive = z;
            if (!z) {
                exitMaintenanceEarlyIfNeededLocked();
            }
        }
    }

    public void setAlarmsActive(boolean z) {
        synchronized (this) {
            this.mAlarmsActive = z;
            if (!z) {
                exitMaintenanceEarlyIfNeededLocked();
            }
        }
    }

    public int setPreIdleTimeoutMode(int i) {
        return setPreIdleTimeoutFactor(getPreIdleTimeoutByMode(i));
    }

    public float getPreIdleTimeoutByMode(int i) {
        if (i == 0) {
            return 1.0f;
        }
        if (i == 1) {
            return this.mConstants.PRE_IDLE_FACTOR_LONG;
        }
        if (i == 2) {
            return this.mConstants.PRE_IDLE_FACTOR_SHORT;
        }
        Slog.w("DeviceIdleController", "Invalid time out factor mode: " + i);
        return 1.0f;
    }

    public float getPreIdleTimeoutFactor() {
        float f;
        synchronized (this) {
            f = this.mPreIdleFactor;
        }
        return f;
    }

    public int setPreIdleTimeoutFactor(float f) {
        synchronized (this) {
            if (!this.mDeepEnabled) {
                return 2;
            }
            if (f <= MIN_PRE_IDLE_FACTOR_CHANGE) {
                return 3;
            }
            if (Math.abs(f - this.mPreIdleFactor) < MIN_PRE_IDLE_FACTOR_CHANGE) {
                return 0;
            }
            this.mLastPreIdleFactor = this.mPreIdleFactor;
            this.mPreIdleFactor = f;
            postUpdatePreIdleFactor();
            return 1;
        }
    }

    public void resetPreIdleTimeoutMode() {
        synchronized (this) {
            this.mLastPreIdleFactor = this.mPreIdleFactor;
            this.mPreIdleFactor = 1.0f;
        }
        postResetPreIdleTimeoutFactor();
    }

    public final void postUpdatePreIdleFactor() {
        this.mHandler.sendEmptyMessage(11);
    }

    public final void postResetPreIdleTimeoutFactor() {
        this.mHandler.sendEmptyMessage(12);
    }

    public final void updatePreIdleFactor() {
        synchronized (this) {
            if (shouldUseIdleTimeoutFactorLocked()) {
                int i = this.mState;
                if (i == 1 || i == 2) {
                    long j = this.mNextAlarmTime;
                    if (j == 0) {
                        return;
                    }
                    long elapsedRealtime = j - SystemClock.elapsedRealtime();
                    if (elapsedRealtime < 60000) {
                        return;
                    }
                    long j2 = (long) ((elapsedRealtime / this.mLastPreIdleFactor) * this.mPreIdleFactor);
                    if (Math.abs(elapsedRealtime - j2) < 60000) {
                    } else {
                        scheduleAlarmLocked(j2);
                    }
                }
            }
        }
    }

    public final void maybeDoImmediateMaintenance(String str) {
        synchronized (this) {
            if (this.mState == 5 && SystemClock.elapsedRealtime() - this.mIdleStartTime > this.mConstants.IDLE_TIMEOUT) {
                stepIdleStateLocked(str);
            }
        }
    }

    public final boolean shouldUseIdleTimeoutFactorLocked() {
        return this.mActiveReason != 1;
    }

    public void setIdleStartTimeForTest(long j) {
        synchronized (this) {
            this.mIdleStartTime = j;
            maybeDoImmediateMaintenance("testing");
        }
    }

    public long getNextAlarmTime() {
        long j;
        synchronized (this) {
            j = this.mNextAlarmTime;
        }
        return j;
    }

    public boolean isEmergencyCallActive() {
        return this.mEmergencyCallListener.isEmergencyCallActive();
    }

    public boolean isOpsInactiveLocked() {
        return (this.mActiveIdleOpCount > 0 || this.mJobsActive || this.mAlarmsActive) ? false : true;
    }

    public void exitMaintenanceEarlyIfNeededLocked() {
        if ((this.mState == 6 || this.mLightState == 6) && isOpsInactiveLocked()) {
            SystemClock.elapsedRealtime();
            if (this.mState == 6) {
                stepIdleStateLocked("s:early");
            } else {
                stepLightIdleStateLocked("s:early");
            }
        }
    }

    public void motionLocked() {
        this.mLastMotionEventElapsed = this.mInjector.getElapsedRealtime();
        handleMotionDetectedLocked(this.mConstants.MOTION_INACTIVE_TIMEOUT, "motion");
    }

    public void handleMotionDetectedLocked(long j, String str) {
        if (this.mStationaryListeners.size() > 0) {
            postStationaryStatusUpdated();
            cancelMotionTimeoutAlarmLocked();
            scheduleMotionRegistrationAlarmLocked();
        }
        if (!this.mQuickDozeActivated || this.mQuickDozeActivatedWhileIdling) {
            maybeStopMonitoringMotionLocked();
            boolean z = this.mState != 0 || this.mLightState == 7;
            becomeActiveLocked(str, Process.myUid(), j, this.mLightState == 7);
            if (z) {
                becomeInactiveIfAppropriateLocked();
            }
        }
    }

    public void receivedGenericLocationLocked(Location location) {
        if (this.mState != 4) {
            cancelLocatingLocked();
            return;
        }
        this.mLastGenericLocation = new Location(location);
        if (location.getAccuracy() <= this.mConstants.LOCATION_ACCURACY || !this.mHasGps) {
            this.mLocated = true;
            if (this.mNotMoving) {
                stepIdleStateLocked("s:location");
            }
        }
    }

    public void receivedGpsLocationLocked(Location location) {
        if (this.mState != 4) {
            cancelLocatingLocked();
            return;
        }
        this.mLastGpsLocation = new Location(location);
        if (location.getAccuracy() > this.mConstants.LOCATION_ACCURACY) {
            return;
        }
        this.mLocated = true;
        if (this.mNotMoving) {
            stepIdleStateLocked("s:gps");
        }
    }

    public void startMonitoringMotionLocked() {
        if (this.mMotionSensor != null) {
            MotionListener motionListener = this.mMotionListener;
            if (motionListener.active) {
                return;
            }
            motionListener.registerLocked();
        }
    }

    public final void maybeStopMonitoringMotionLocked() {
        if (this.mMotionSensor == null || this.mStationaryListeners.size() != 0) {
            return;
        }
        MotionListener motionListener = this.mMotionListener;
        if (motionListener.active) {
            motionListener.unregisterLocked();
            cancelMotionTimeoutAlarmLocked();
        }
        cancelMotionRegistrationAlarmLocked();
    }

    public void cancelAlarmLocked() {
        if (this.mNextAlarmTime != 0) {
            this.mNextAlarmTime = 0L;
            this.mAlarmManager.cancel(this.mDeepAlarmListener);
        }
    }

    public final void cancelLightAlarmLocked() {
        if (this.mNextLightAlarmTime != 0) {
            this.mNextLightAlarmTime = 0L;
            this.mAlarmManager.cancel(this.mLightAlarmListener);
        }
    }

    public void cancelLocatingLocked() {
        if (this.mLocating) {
            LocationManager locationManager = this.mInjector.getLocationManager();
            locationManager.removeUpdates(this.mGenericLocationListener);
            locationManager.removeUpdates(this.mGpsLocationListener);
            this.mLocating = false;
        }
    }

    public final void cancelMotionTimeoutAlarmLocked() {
        this.mAlarmManager.cancel(this.mMotionTimeoutAlarmListener);
    }

    public final void cancelMotionRegistrationAlarmLocked() {
        this.mAlarmManager.cancel(this.mMotionRegistrationAlarmListener);
    }

    public void cancelSensingTimeoutAlarmLocked() {
        if (this.mNextSensingTimeoutAlarmTime != 0) {
            this.mNextSensingTimeoutAlarmTime = 0L;
            this.mAlarmManager.cancel(this.mSensingTimeoutAlarmListener);
        }
    }

    public void scheduleAlarmLocked(long j) {
        int i;
        if (!this.mUseMotionSensor || this.mMotionSensor != null || (i = this.mState) == 7 || i == 5 || i == 6) {
            long elapsedRealtime = SystemClock.elapsedRealtime() + j;
            this.mNextAlarmTime = elapsedRealtime;
            int i2 = this.mState;
            if (i2 == 5) {
                this.mAlarmManager.setIdleUntil(2, elapsedRealtime, "DeviceIdleController.deep.maintenance", this.mDeepAlarmListener, this.mHandler);
                return;
            }
            if (i2 == 4) {
                this.mAlarmManager.setExact(2, elapsedRealtime, "DeviceIdleController.deep.locating", this.mDeepAlarmListener, this.mHandler);
                return;
            }
            Constants constants = this.mConstants;
            if (constants.USE_WINDOW_ALARMS) {
                this.mAlarmManager.setWindow(2, elapsedRealtime, constants.FLEX_TIME_SHORT, "DeviceIdleController.deep.progression", this.mDeepAlarmListener, this.mHandler);
            } else {
                this.mAlarmManager.set(2, elapsedRealtime, "DeviceIdleController.deep.progression", this.mDeepAlarmListener, this.mHandler);
            }
        }
    }

    public void scheduleLightAlarmLocked(long j, long j2, boolean z) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("scheduleLightAlarmLocked(");
        sb.append(j);
        if (this.mConstants.USE_WINDOW_ALARMS) {
            str = "/" + j2;
        } else {
            str = "";
        }
        sb.append(str);
        sb.append(", wakeup=");
        sb.append(z);
        sb.append(")");
        Slog.d("DeviceIdleController", sb.toString());
        long elapsedRealtime = j + this.mInjector.getElapsedRealtime();
        this.mNextLightAlarmTime = elapsedRealtime;
        if (this.mConstants.USE_WINDOW_ALARMS) {
            this.mAlarmManager.setWindow(z ? 2 : 3, elapsedRealtime, j2, "DeviceIdleController.light", this.mLightAlarmListener, this.mHandler);
        } else {
            this.mAlarmManager.set(z ? 2 : 3, elapsedRealtime, "DeviceIdleController.light", this.mLightAlarmListener, this.mHandler);
        }
    }

    public long getNextLightAlarmTimeForTesting() {
        long j;
        synchronized (this) {
            j = this.mNextLightAlarmTime;
        }
        return j;
    }

    public final void scheduleMotionRegistrationAlarmLocked() {
        long elapsedRealtime = this.mInjector.getElapsedRealtime();
        Constants constants = this.mConstants;
        long j = elapsedRealtime + (constants.MOTION_INACTIVE_TIMEOUT / 2);
        if (constants.USE_WINDOW_ALARMS) {
            this.mAlarmManager.setWindow(2, j, constants.MOTION_INACTIVE_TIMEOUT_FLEX, "DeviceIdleController.motion_registration", this.mMotionRegistrationAlarmListener, this.mHandler);
        } else {
            this.mAlarmManager.set(2, j, "DeviceIdleController.motion_registration", this.mMotionRegistrationAlarmListener, this.mHandler);
        }
    }

    public final void scheduleMotionTimeoutAlarmLocked() {
        long elapsedRealtime = this.mInjector.getElapsedRealtime();
        Constants constants = this.mConstants;
        long j = elapsedRealtime + constants.MOTION_INACTIVE_TIMEOUT;
        if (constants.USE_WINDOW_ALARMS) {
            this.mAlarmManager.setWindow(2, j, constants.MOTION_INACTIVE_TIMEOUT_FLEX, "DeviceIdleController.motion", this.mMotionTimeoutAlarmListener, this.mHandler);
        } else {
            this.mAlarmManager.set(2, j, "DeviceIdleController.motion", this.mMotionTimeoutAlarmListener, this.mHandler);
        }
    }

    public void scheduleSensingTimeoutAlarmLocked(long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime() + j;
        this.mNextSensingTimeoutAlarmTime = elapsedRealtime;
        Constants constants = this.mConstants;
        if (constants.USE_WINDOW_ALARMS) {
            this.mAlarmManager.setWindow(2, elapsedRealtime, constants.FLEX_TIME_SHORT, "DeviceIdleController.sensing", this.mSensingTimeoutAlarmListener, this.mHandler);
        } else {
            this.mAlarmManager.set(2, elapsedRealtime, "DeviceIdleController.sensing", this.mSensingTimeoutAlarmListener, this.mHandler);
        }
    }

    public static int[] buildAppIdArray(ArrayMap arrayMap, ArrayMap arrayMap2, SparseBooleanArray sparseBooleanArray) {
        sparseBooleanArray.clear();
        if (arrayMap != null) {
            for (int i = 0; i < arrayMap.size(); i++) {
                sparseBooleanArray.put(((Integer) arrayMap.valueAt(i)).intValue(), true);
            }
        }
        if (arrayMap2 != null) {
            for (int i2 = 0; i2 < arrayMap2.size(); i2++) {
                sparseBooleanArray.put(((Integer) arrayMap2.valueAt(i2)).intValue(), true);
            }
        }
        int size = sparseBooleanArray.size();
        int[] iArr = new int[size];
        for (int i3 = 0; i3 < size; i3++) {
            iArr[i3] = sparseBooleanArray.keyAt(i3);
        }
        return iArr;
    }

    public final void updateWhitelistAppIdsLocked() {
        this.mPowerSaveWhitelistExceptIdleAppIdArray = buildAppIdArray(this.mPowerSaveWhitelistAppsExceptIdle, this.mPowerSaveWhitelistUserApps, this.mPowerSaveWhitelistExceptIdleAppIds);
        this.mPowerSaveWhitelistAllAppIdArray = buildAppIdArray(this.mPowerSaveWhitelistApps, this.mPowerSaveWhitelistUserApps, this.mPowerSaveWhitelistAllAppIds);
        this.mPowerSaveWhitelistUserAppIdArray = buildAppIdArray(null, this.mPowerSaveWhitelistUserApps, this.mPowerSaveWhitelistUserAppIds);
        ActivityManagerInternal activityManagerInternal = this.mLocalActivityManager;
        if (activityManagerInternal != null) {
            activityManagerInternal.setDeviceIdleAllowlist(this.mPowerSaveWhitelistAllAppIdArray, this.mPowerSaveWhitelistExceptIdleAppIdArray);
        }
        PowerManagerInternal powerManagerInternal = this.mLocalPowerManager;
        if (powerManagerInternal != null) {
            powerManagerInternal.setDeviceIdleWhitelist(this.mPowerSaveWhitelistAllAppIdArray);
        }
        passWhiteListsToForceAppStandbyTrackerLocked();
    }

    public final void updateTempWhitelistAppIdsLocked(int i, boolean z, long j, int i2, int i3, String str, int i4) {
        int size = this.mTempWhitelistAppIdEndTimes.size();
        if (this.mTempWhitelistAppIdArray.length != size) {
            this.mTempWhitelistAppIdArray = new int[size];
        }
        for (int i5 = 0; i5 < size; i5++) {
            this.mTempWhitelistAppIdArray[i5] = this.mTempWhitelistAppIdEndTimes.keyAt(i5);
        }
        ActivityManagerInternal activityManagerInternal = this.mLocalActivityManager;
        if (activityManagerInternal != null) {
            activityManagerInternal.updateDeviceIdleTempAllowlist(this.mTempWhitelistAppIdArray, i, z, j, i2, i3, str, i4);
        }
        PowerManagerInternal powerManagerInternal = this.mLocalPowerManager;
        if (powerManagerInternal != null) {
            powerManagerInternal.setDeviceIdleTempWhitelist(this.mTempWhitelistAppIdArray);
        }
        passWhiteListsToForceAppStandbyTrackerLocked();
    }

    public final void reportPowerSaveWhitelistChangedLocked() {
        getContext().sendBroadcastAsUser(this.mPowerSaveWhitelistChangedIntent, UserHandle.SYSTEM, null, this.mPowerSaveWhitelistChangedOptions);
    }

    public final void reportTempWhitelistChangedLocked(int i, boolean z) {
        this.mHandler.obtainMessage(13, i, z ? 1 : 0).sendToTarget();
        getContext().sendBroadcastAsUser(this.mPowerSaveTempWhitelistChangedIntent, UserHandle.SYSTEM, null, this.mPowerSaveTempWhilelistChangedOptions);
    }

    public final void passWhiteListsToForceAppStandbyTrackerLocked() {
        this.mAppStateTracker.setPowerSaveExemptionListAppIds(this.mPowerSaveWhitelistExceptIdleAppIdArray, this.mPowerSaveWhitelistUserAppIdArray, this.mTempWhitelistAppIdArray);
    }

    public void readConfigFileLocked() {
        this.mPowerSaveWhitelistUserApps.clear();
        try {
            FileInputStream openRead = this.mConfigFile.openRead();
            try {
                XmlPullParser newPullParser = Xml.newPullParser();
                newPullParser.setInput(openRead, StandardCharsets.UTF_8.name());
                readConfigFileLocked(newPullParser);
            } catch (XmlPullParserException unused) {
            } catch (Throwable th) {
                try {
                    openRead.close();
                } catch (IOException unused2) {
                }
                throw th;
            }
            try {
                openRead.close();
            } catch (IOException unused3) {
            }
        } catch (FileNotFoundException unused4) {
            Slog.e("DeviceIdleController", "There is no " + this.mConfigFile.getBaseFile());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0099 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0061 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void readConfigFileLocked(XmlPullParser xmlPullParser) {
        int next;
        char c;
        PackageManager packageManager = getContext().getPackageManager();
        do {
            try {
                next = xmlPullParser.next();
                if (next == 2) {
                    break;
                }
            } catch (IOException e) {
                Slog.w("DeviceIdleController", "Failed parsing config " + e);
                return;
            } catch (IllegalStateException e2) {
                Slog.w("DeviceIdleController", "Failed parsing config " + e2);
                return;
            } catch (IndexOutOfBoundsException e3) {
                Slog.w("DeviceIdleController", "Failed parsing config " + e3);
                return;
            } catch (NullPointerException e4) {
                Slog.w("DeviceIdleController", "Failed parsing config " + e4);
                return;
            } catch (NumberFormatException e5) {
                Slog.w("DeviceIdleController", "Failed parsing config " + e5);
                return;
            } catch (XmlPullParserException e6) {
                Slog.w("DeviceIdleController", "Failed parsing config " + e6);
                return;
            }
        } while (next != 1);
        if (next != 2) {
            throw new IllegalStateException("no start tag found");
        }
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next2 = xmlPullParser.next();
            if (next2 == 1) {
                return;
            }
            if (next2 == 3 && xmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next2 != 3 && next2 != 4) {
                String name = xmlPullParser.getName();
                int hashCode = name.hashCode();
                if (hashCode != 3797) {
                    if (hashCode == 111376009 && name.equals("un-wl")) {
                        c = 1;
                        if (c != 0) {
                            String attributeValue = xmlPullParser.getAttributeValue(null, "n");
                            if (attributeValue != null) {
                                try {
                                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(attributeValue, 4194304);
                                    String str = applicationInfo.packageName;
                                    int appId = UserHandle.getAppId(applicationInfo.uid);
                                    this.mPowerSaveWhitelistUserApps.put(str, Integer.valueOf(appId));
                                    this.mUserWhitelistHistoryBuffer.append(new UserWhitelistHistoryInfo(1, null, new TargetPkg(appId, str)));
                                } catch (PackageManager.NameNotFoundException unused) {
                                }
                            }
                        } else if (c == 1) {
                            String attributeValue2 = xmlPullParser.getAttributeValue(null, "n");
                            if (this.mPowerSaveWhitelistApps.containsKey(attributeValue2)) {
                                this.mRemovedFromSystemWhitelistApps.put(attributeValue2, (Integer) this.mPowerSaveWhitelistApps.remove(attributeValue2));
                            }
                        } else {
                            Slog.w("DeviceIdleController", "Unknown element under <config>: " + xmlPullParser.getName());
                            XmlUtils.skipCurrentTag(xmlPullParser);
                        }
                    }
                    c = 65535;
                    if (c != 0) {
                    }
                } else {
                    if (name.equals("wl")) {
                        c = 0;
                        if (c != 0) {
                        }
                    }
                    c = 65535;
                    if (c != 0) {
                    }
                }
            }
        }
    }

    public void writeConfigFileLocked() {
        this.mHandler.removeMessages(1);
        this.mHandler.sendEmptyMessageDelayed(1, 5000L);
    }

    public void handleWriteConfigFile() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            synchronized (this) {
                XmlSerializer fastXmlSerializer = new FastXmlSerializer();
                fastXmlSerializer.setOutput(byteArrayOutputStream, StandardCharsets.UTF_8.name());
                writeConfigFileLocked(fastXmlSerializer);
            }
        } catch (IOException unused) {
        }
        synchronized (this.mConfigFile) {
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = this.mConfigFile.startWrite();
                byteArrayOutputStream.writeTo(fileOutputStream);
                this.mConfigFile.finishWrite(fileOutputStream);
            } catch (IOException e) {
                Slog.w("DeviceIdleController", "Error writing config file", e);
                this.mConfigFile.failWrite(fileOutputStream);
            }
        }
    }

    public void writeConfigFileLocked(XmlSerializer xmlSerializer) {
        xmlSerializer.startDocument(null, Boolean.TRUE);
        xmlSerializer.startTag(null, "config");
        for (int i = 0; i < this.mPowerSaveWhitelistUserApps.size(); i++) {
            String str = (String) this.mPowerSaveWhitelistUserApps.keyAt(i);
            xmlSerializer.startTag(null, "wl");
            xmlSerializer.attribute(null, "n", str);
            xmlSerializer.endTag(null, "wl");
        }
        for (int i2 = 0; i2 < this.mRemovedFromSystemWhitelistApps.size(); i2++) {
            xmlSerializer.startTag(null, "un-wl");
            xmlSerializer.attribute(null, "n", (String) this.mRemovedFromSystemWhitelistApps.keyAt(i2));
            xmlSerializer.endTag(null, "un-wl");
        }
        xmlSerializer.endTag(null, "config");
        xmlSerializer.endDocument();
    }

    public static void dumpHelp(PrintWriter printWriter) {
        printWriter.println("Device idle controller (deviceidle) commands:");
        printWriter.println("  help");
        printWriter.println("    Print this help text.");
        printWriter.println("  step [light|deep]");
        printWriter.println("    Immediately step to next state, without waiting for alarm.");
        printWriter.println("  force-idle [light|deep]");
        printWriter.println("    Force directly into idle mode, regardless of other device state.");
        printWriter.println("  force-inactive");
        printWriter.println("    Force to be inactive, ready to freely step idle states.");
        printWriter.println("  unforce");
        printWriter.println("    Resume normal functioning after force-idle or force-inactive.");
        printWriter.println("  get [light|deep|force|screen|charging|network]");
        printWriter.println("    Retrieve the current given state.");
        printWriter.println("  disable [light|deep|all]");
        printWriter.println("    Completely disable device idle mode.");
        printWriter.println("  enable [light|deep|all]");
        printWriter.println("    Re-enable device idle mode after it had previously been disabled.");
        printWriter.println("  enabled [light|deep|all]");
        printWriter.println("    Print 1 if device idle mode is currently enabled, else 0.");
        printWriter.println("  whitelist");
        printWriter.println("    Print currently whitelisted apps.");
        printWriter.println("  whitelist [package ...]");
        printWriter.println("    Add (prefix with +) or remove (prefix with -) packages.");
        printWriter.println("  sys-whitelist [package ...|reset]");
        printWriter.println("    Prefix the package with '-' to remove it from the system whitelist or '+' to put it back in the system whitelist.");
        printWriter.println("    Note that only packages that were earlier removed from the system whitelist can be added back.");
        printWriter.println("    reset will reset the whitelist to the original state");
        printWriter.println("    Prints the system whitelist if no arguments are specified");
        printWriter.println("  except-idle-whitelist [package ...|reset]");
        printWriter.println("    Prefix the package with '+' to add it to whitelist or '=' to check if it is already whitelisted");
        printWriter.println("    [reset] will reset the whitelist to it's original state");
        printWriter.println("    Note that unlike <whitelist> cmd, changes made using this won't be persisted across boots");
        printWriter.println("  tempwhitelist");
        printWriter.println("    Print packages that are temporarily whitelisted.");
        printWriter.println("  tempwhitelist [-u USER] [-d DURATION] [-r] [package]");
        printWriter.println("    Temporarily place package in whitelist for DURATION milliseconds.");
        printWriter.println("    If no DURATION is specified, 10 seconds is used");
        printWriter.println("    If [-r] option is used, then the package is removed from temp whitelist and any [-d] is ignored");
        printWriter.println("  motion");
        printWriter.println("    Simulate a motion event to bring the device out of deep doze");
        printWriter.println("  pre-idle-factor [0|1|2]");
        printWriter.println("    Set a new factor to idle time before step to idle(inactive_to and idle_after_inactive_to)");
        printWriter.println("  reset-pre-idle-factor");
        printWriter.println("    Reset factor to idle time to default");
    }

    public class Shell extends ShellCommand {
        public int userId = 0;

        public Shell() {
        }

        public int onCommand(String str) {
            return DeviceIdleController.this.onShellCommand(this, str);
        }

        public void onHelp() {
            DeviceIdleController.dumpHelp(getOutPrintWriter());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:208:0x033e A[Catch: all -> 0x039c, TryCatch #28 {all -> 0x039c, blocks: (B:234:0x0319, B:236:0x0321, B:208:0x033e, B:210:0x0347, B:214:0x0360, B:217:0x036d, B:220:0x0382, B:228:0x034f, B:230:0x0353, B:203:0x032c, B:205:0x0330), top: B:233:0x0319, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:214:0x0360 A[Catch: all -> 0x039c, TryCatch #28 {all -> 0x039c, blocks: (B:234:0x0319, B:236:0x0321, B:208:0x033e, B:210:0x0347, B:214:0x0360, B:217:0x036d, B:220:0x0382, B:228:0x034f, B:230:0x0353, B:203:0x032c, B:205:0x0330), top: B:233:0x0319, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:220:0x0382 A[Catch: all -> 0x039c, TRY_LEAVE, TryCatch #28 {all -> 0x039c, blocks: (B:234:0x0319, B:236:0x0321, B:208:0x033e, B:210:0x0347, B:214:0x0360, B:217:0x036d, B:220:0x0382, B:228:0x034f, B:230:0x0353, B:203:0x032c, B:205:0x0330), top: B:233:0x0319, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:230:0x0353 A[Catch: all -> 0x039c, TryCatch #28 {all -> 0x039c, blocks: (B:234:0x0319, B:236:0x0321, B:208:0x033e, B:210:0x0347, B:214:0x0360, B:217:0x036d, B:220:0x0382, B:228:0x034f, B:230:0x0353, B:203:0x032c, B:205:0x0330), top: B:233:0x0319, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:231:0x035c  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x03e7 A[Catch: all -> 0x0428, TryCatch #5 {all -> 0x0428, blocks: (B:278:0x03c2, B:280:0x03ca, B:256:0x03e7, B:258:0x03f0, B:262:0x0409, B:264:0x040e, B:272:0x03f8, B:274:0x03fc, B:251:0x03d5, B:253:0x03d9), top: B:277:0x03c2, outer: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:262:0x0409 A[Catch: all -> 0x0428, TryCatch #5 {all -> 0x0428, blocks: (B:278:0x03c2, B:280:0x03ca, B:256:0x03e7, B:258:0x03f0, B:262:0x0409, B:264:0x040e, B:272:0x03f8, B:274:0x03fc, B:251:0x03d5, B:253:0x03d9), top: B:277:0x03c2, outer: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:264:0x040e A[Catch: all -> 0x0428, TRY_LEAVE, TryCatch #5 {all -> 0x0428, blocks: (B:278:0x03c2, B:280:0x03ca, B:256:0x03e7, B:258:0x03f0, B:262:0x0409, B:264:0x040e, B:272:0x03f8, B:274:0x03fc, B:251:0x03d5, B:253:0x03d9), top: B:277:0x03c2, outer: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:274:0x03fc A[Catch: all -> 0x0428, TryCatch #5 {all -> 0x0428, blocks: (B:278:0x03c2, B:280:0x03ca, B:256:0x03e7, B:258:0x03f0, B:262:0x0409, B:264:0x040e, B:272:0x03f8, B:274:0x03fc, B:251:0x03d5, B:253:0x03d9), top: B:277:0x03c2, outer: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:275:0x0405  */
    /* JADX WARN: Removed duplicated region for block: B:592:0x0921 A[Catch: all -> 0x0946, NumberFormatException -> 0x0949, TRY_LEAVE, TryCatch #15 {all -> 0x0946, blocks: (B:586:0x08e7, B:588:0x08ed, B:590:0x08f7, B:592:0x0921, B:599:0x0910, B:601:0x0918, B:603:0x0949), top: B:585:0x08e7, outer: #6, inners: #13 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int onShellCommand(Shell shell, String str) {
        long clearCallingIdentity;
        String nextArg;
        char c;
        char c2;
        char c3;
        char c4;
        PrintWriter outPrintWriter = shell.getOutPrintWriter();
        if ("step".equals(str)) {
            getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            synchronized (this) {
                clearCallingIdentity = Binder.clearCallingIdentity();
                String nextArg2 = shell.getNextArg();
                if (nextArg2 != null) {
                    try {
                        if (!"deep".equals(nextArg2)) {
                            if ("light".equals(nextArg2)) {
                                stepLightIdleStateLocked("s:shell");
                                outPrintWriter.print("Stepped to light: ");
                                outPrintWriter.println(lightStateToString(this.mLightState));
                            } else {
                                outPrintWriter.println("Unknown idle mode: " + nextArg2);
                            }
                        }
                    } finally {
                    }
                }
                stepIdleStateLocked("s:shell");
                outPrintWriter.print("Stepped to deep: ");
                outPrintWriter.println(stateToString(this.mState));
            }
        } else {
            char c5 = 1;
            if ("force-active".equals(str)) {
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                synchronized (this) {
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        this.mForceIdle = true;
                        becomeActiveLocked("force-active", Process.myUid());
                        outPrintWriter.print("Light state: ");
                        outPrintWriter.print(lightStateToString(this.mLightState));
                        outPrintWriter.print(", deep state: ");
                        outPrintWriter.println(stateToString(this.mState));
                    } finally {
                    }
                }
            } else {
                int i = -1;
                if ("force-idle".equals(str)) {
                    getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                    synchronized (this) {
                        clearCallingIdentity = Binder.clearCallingIdentity();
                        String nextArg3 = shell.getNextArg();
                        if (nextArg3 != null) {
                            try {
                                if (!"deep".equals(nextArg3)) {
                                    if ("light".equals(nextArg3)) {
                                        this.mForceIdle = true;
                                        becomeInactiveIfAppropriateLocked();
                                        int i2 = this.mLightState;
                                        while (i2 != 4) {
                                            stepLightIdleStateLocked("s:shell");
                                            int i3 = this.mLightState;
                                            if (i2 == i3) {
                                                outPrintWriter.print("Unable to go light idle; stopped at ");
                                                outPrintWriter.println(lightStateToString(this.mLightState));
                                                exitForceIdleLocked();
                                                return -1;
                                            }
                                            i2 = i3;
                                        }
                                        outPrintWriter.println("Now forced in to light idle mode");
                                    } else {
                                        outPrintWriter.println("Unknown idle mode: " + nextArg3);
                                    }
                                }
                            } finally {
                            }
                        }
                        if (!this.mDeepEnabled) {
                            outPrintWriter.println("Unable to go deep idle; not enabled");
                            return -1;
                        }
                        this.mForceIdle = true;
                        becomeInactiveIfAppropriateLocked();
                        int i4 = this.mState;
                        while (i4 != 5) {
                            stepIdleStateLocked("s:shell");
                            int i5 = this.mState;
                            if (i4 == i5) {
                                outPrintWriter.print("Unable to go deep idle; stopped at ");
                                outPrintWriter.println(stateToString(this.mState));
                                exitForceIdleLocked();
                                return -1;
                            }
                            i4 = i5;
                        }
                        outPrintWriter.println("Now forced in to deep idle mode");
                    }
                } else if ("force-inactive".equals(str)) {
                    getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                    synchronized (this) {
                        clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            this.mForceIdle = true;
                            becomeInactiveIfAppropriateLocked();
                            outPrintWriter.print("Light state: ");
                            outPrintWriter.print(lightStateToString(this.mLightState));
                            outPrintWriter.print(", deep state: ");
                            outPrintWriter.println(stateToString(this.mState));
                        } finally {
                        }
                    }
                } else if ("unforce".equals(str)) {
                    getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                    synchronized (this) {
                        clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            exitForceIdleLocked();
                            outPrintWriter.print("Light state: ");
                            outPrintWriter.print(lightStateToString(this.mLightState));
                            outPrintWriter.print(", deep state: ");
                            outPrintWriter.println(stateToString(this.mState));
                        } finally {
                        }
                    }
                } else {
                    int i6 = 2;
                    if ("get".equals(str)) {
                        getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                        synchronized (this) {
                            String nextArg4 = shell.getNextArg();
                            if (nextArg4 != null) {
                                clearCallingIdentity = Binder.clearCallingIdentity();
                                try {
                                    switch (nextArg4.hashCode()) {
                                        case -907689876:
                                            if (nextArg4.equals(KnoxCustomManagerService.SCREEN)) {
                                                c5 = 4;
                                                break;
                                            }
                                            c5 = 65535;
                                            break;
                                        case 3079404:
                                            if (nextArg4.equals("deep")) {
                                                break;
                                            }
                                            c5 = 65535;
                                            break;
                                        case 97618667:
                                            if (nextArg4.equals("force")) {
                                                c5 = 2;
                                                break;
                                            }
                                            c5 = 65535;
                                            break;
                                        case 102970646:
                                            if (nextArg4.equals("light")) {
                                                c5 = 0;
                                                break;
                                            }
                                            c5 = 65535;
                                            break;
                                        case 107947501:
                                            if (nextArg4.equals("quick")) {
                                                c5 = 3;
                                                break;
                                            }
                                            c5 = 65535;
                                            break;
                                        case 1436115569:
                                            if (nextArg4.equals("charging")) {
                                                c5 = 5;
                                                break;
                                            }
                                            c5 = 65535;
                                            break;
                                        case 1843485230:
                                            if (nextArg4.equals("network")) {
                                                c5 = 6;
                                                break;
                                            }
                                            c5 = 65535;
                                            break;
                                        default:
                                            c5 = 65535;
                                            break;
                                    }
                                    switch (c5) {
                                        case 0:
                                            outPrintWriter.println(lightStateToString(this.mLightState));
                                            break;
                                        case 1:
                                            outPrintWriter.println(stateToString(this.mState));
                                            break;
                                        case 2:
                                            outPrintWriter.println(this.mForceIdle);
                                            break;
                                        case 3:
                                            outPrintWriter.println(this.mQuickDozeActivated);
                                            break;
                                        case 4:
                                            outPrintWriter.println(this.mScreenOn);
                                            break;
                                        case 5:
                                            outPrintWriter.println(this.mCharging);
                                            break;
                                        case 6:
                                            outPrintWriter.println(this.mNetworkConnected);
                                            break;
                                        default:
                                            outPrintWriter.println("Unknown get option: " + nextArg4);
                                            break;
                                    }
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                } finally {
                                }
                            } else {
                                outPrintWriter.println("Argument required");
                            }
                        }
                    } else if ("disable".equals(str)) {
                        getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                        synchronized (this) {
                            clearCallingIdentity = Binder.clearCallingIdentity();
                            String nextArg5 = shell.getNextArg();
                            if (nextArg5 != null) {
                                try {
                                    if (!"deep".equals(nextArg5) && !"all".equals(nextArg5)) {
                                        c3 = 0;
                                        c4 = c3;
                                        if (nextArg5 != null || "light".equals(nextArg5) || "all".equals(nextArg5)) {
                                            if (this.mLightEnabled) {
                                                this.mLightEnabled = false;
                                                outPrintWriter.println("Light idle mode disabled");
                                                c4 = 1;
                                                if (c5 != 0) {
                                                    this.mActiveReason = 6;
                                                    StringBuilder sb = new StringBuilder();
                                                    sb.append(nextArg5 == null ? "all" : nextArg5);
                                                    sb.append("-disabled");
                                                    becomeActiveLocked(sb.toString(), Process.myUid());
                                                }
                                                if (c4 == 0) {
                                                    outPrintWriter.println("Unknown idle mode: " + nextArg5);
                                                }
                                            } else {
                                                c4 = 1;
                                            }
                                        }
                                        c5 = c3;
                                        if (c5 != 0) {
                                        }
                                        if (c4 == 0) {
                                        }
                                    }
                                } finally {
                                }
                            }
                            if (this.mDeepEnabled) {
                                this.mDeepEnabled = false;
                                outPrintWriter.println("Deep idle mode disabled");
                                c3 = 1;
                                c4 = c3;
                                if (nextArg5 != null) {
                                }
                                if (this.mLightEnabled) {
                                }
                            } else {
                                c4 = 1;
                                c3 = 0;
                                if (nextArg5 != null) {
                                }
                                if (this.mLightEnabled) {
                                }
                            }
                        }
                    } else if ("enable".equals(str)) {
                        getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                        synchronized (this) {
                            clearCallingIdentity = Binder.clearCallingIdentity();
                            String nextArg6 = shell.getNextArg();
                            if (nextArg6 != null) {
                                try {
                                    if (!"deep".equals(nextArg6) && !"all".equals(nextArg6)) {
                                        c = 0;
                                        c2 = c;
                                        if (nextArg6 != null || "light".equals(nextArg6) || "all".equals(nextArg6)) {
                                            if (this.mLightEnabled) {
                                                c2 = 1;
                                            } else {
                                                this.mLightEnabled = true;
                                                outPrintWriter.println("Light idle mode enable");
                                                c2 = 1;
                                                if (c5 != 0) {
                                                    becomeInactiveIfAppropriateLocked();
                                                }
                                                if (c2 == 0) {
                                                    outPrintWriter.println("Unknown idle mode: " + nextArg6);
                                                }
                                            }
                                        }
                                        c5 = c;
                                        if (c5 != 0) {
                                        }
                                        if (c2 == 0) {
                                        }
                                    }
                                } finally {
                                }
                            }
                            if (this.mDeepEnabled) {
                                c2 = 1;
                                c = 0;
                                if (nextArg6 != null) {
                                }
                                if (this.mLightEnabled) {
                                }
                            } else {
                                this.mDeepEnabled = true;
                                outPrintWriter.println("Deep idle mode enabled");
                                c = 1;
                                c2 = c;
                                if (nextArg6 != null) {
                                }
                                if (this.mLightEnabled) {
                                }
                            }
                        }
                    } else if ("enabled".equals(str)) {
                        synchronized (this) {
                            String nextArg7 = shell.getNextArg();
                            if (nextArg7 != null && !"all".equals(nextArg7)) {
                                if ("deep".equals(nextArg7)) {
                                    outPrintWriter.println(this.mDeepEnabled ? "1" : 0);
                                } else if ("light".equals(nextArg7)) {
                                    outPrintWriter.println(this.mLightEnabled ? "1" : 0);
                                } else {
                                    outPrintWriter.println("Unknown idle mode: " + nextArg7);
                                }
                            }
                            if (this.mDeepEnabled && this.mLightEnabled) {
                                r6 = "1";
                            }
                            outPrintWriter.println(r6);
                        }
                    } else if ("whitelist".equals(str)) {
                        String nextArg8 = shell.getNextArg();
                        if (nextArg8 != null) {
                            getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                            clearCallingIdentity = Binder.clearCallingIdentity();
                            while (nextArg8.length() >= 1 && (nextArg8.charAt(0) == '-' || nextArg8.charAt(0) == '+' || nextArg8.charAt(0) == '=')) {
                                try {
                                    char charAt = nextArg8.charAt(0);
                                    String substring = nextArg8.substring(1);
                                    if (charAt == '+') {
                                        if (addPowerSaveWhitelistAppsInternal(Collections.singletonList(substring), i6, null) == 1) {
                                            outPrintWriter.println("Added: " + substring);
                                        } else {
                                            outPrintWriter.println("Unknown package: " + substring);
                                        }
                                    } else if (charAt != '-') {
                                        outPrintWriter.println(getPowerSaveWhitelistAppInternal(substring));
                                    } else if (removePowerSaveWhitelistAppInternal(substring, 4, null)) {
                                        outPrintWriter.println("Removed: " + substring);
                                    }
                                    nextArg8 = shell.getNextArg();
                                    if (nextArg8 != null) {
                                        i6 = 2;
                                    }
                                } finally {
                                }
                            }
                            outPrintWriter.println("Package must be prefixed with +, -, or =: " + nextArg8);
                            return -1;
                        }
                        if (!DumpUtils.checkDumpPermission(getContext(), "DeviceIdleController", outPrintWriter)) {
                            return -1;
                        }
                        synchronized (this) {
                            for (int i7 = 0; i7 < this.mPowerSaveWhitelistAppsExceptIdle.size(); i7++) {
                                outPrintWriter.print("system-excidle,");
                                outPrintWriter.print((String) this.mPowerSaveWhitelistAppsExceptIdle.keyAt(i7));
                                outPrintWriter.print(",");
                                outPrintWriter.println(this.mPowerSaveWhitelistAppsExceptIdle.valueAt(i7));
                            }
                            for (int i8 = 0; i8 < this.mPowerSaveWhitelistApps.size(); i8++) {
                                outPrintWriter.print("system,");
                                outPrintWriter.print((String) this.mPowerSaveWhitelistApps.keyAt(i8));
                                outPrintWriter.print(",");
                                outPrintWriter.println(this.mPowerSaveWhitelistApps.valueAt(i8));
                            }
                            for (int i9 = 0; i9 < this.mPowerSaveWhitelistUserApps.size(); i9++) {
                                outPrintWriter.print("user,");
                                outPrintWriter.print((String) this.mPowerSaveWhitelistUserApps.keyAt(i9));
                                outPrintWriter.print(",");
                                outPrintWriter.println(this.mPowerSaveWhitelistUserApps.valueAt(i9));
                            }
                        }
                    } else if ("whitelist-history".equals(str)) {
                        synchronized (this) {
                            int size = this.mUserWhitelistHistoryBuffer.size();
                            outPrintWriter.println("- history count : " + size);
                            UserWhitelistHistoryInfo[] userWhitelistHistoryInfoArr = (UserWhitelistHistoryInfo[]) this.mUserWhitelistHistoryBuffer.toArray();
                            for (int i10 = 0; i10 < size; i10++) {
                                outPrintWriter.println(userWhitelistHistoryInfoArr[i10].toString());
                            }
                        }
                    } else if ("tempwhitelist".equals(str)) {
                        long j = 10000;
                        boolean z = false;
                        while (true) {
                            String nextOption = shell.getNextOption();
                            if (nextOption == null) {
                                String nextArg9 = shell.getNextArg();
                                if (nextArg9 != null) {
                                    try {
                                        if (z) {
                                            removePowerSaveTempAllowlistAppChecked(nextArg9, shell.userId);
                                        } else {
                                            addPowerSaveTempAllowlistAppChecked(nextArg9, j, shell.userId, FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_SHELL, "shell");
                                        }
                                    } catch (Exception e) {
                                        outPrintWriter.println("Failed: " + e);
                                        return -1;
                                    }
                                } else {
                                    if (z) {
                                        outPrintWriter.println("[-r] requires a package name");
                                        return -1;
                                    }
                                    if (!DumpUtils.checkDumpPermission(getContext(), "DeviceIdleController", outPrintWriter)) {
                                        return -1;
                                    }
                                    dumpTempWhitelistSchedule(outPrintWriter, false);
                                }
                            } else if ("-u".equals(nextOption)) {
                                String nextArg10 = shell.getNextArg();
                                if (nextArg10 == null) {
                                    outPrintWriter.println("-u requires a user number");
                                    return -1;
                                }
                                shell.userId = Integer.parseInt(nextArg10);
                            } else if ("-d".equals(nextOption)) {
                                String nextArg11 = shell.getNextArg();
                                if (nextArg11 == null) {
                                    outPrintWriter.println("-d requires a duration");
                                    return -1;
                                }
                                j = Long.parseLong(nextArg11);
                            } else if ("-r".equals(nextOption)) {
                                z = true;
                            }
                        }
                    } else if ("except-idle-whitelist".equals(str)) {
                        getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                        clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            String nextArg12 = shell.getNextArg();
                            if (nextArg12 == null) {
                                outPrintWriter.println("No arguments given");
                                return -1;
                            }
                            if (!"reset".equals(nextArg12)) {
                                while (nextArg12.length() >= 1 && (nextArg12.charAt(0) == '-' || nextArg12.charAt(0) == '+' || nextArg12.charAt(0) == '=')) {
                                    char charAt2 = nextArg12.charAt(0);
                                    String substring2 = nextArg12.substring(1);
                                    if (charAt2 != '+') {
                                        if (charAt2 != '=') {
                                            outPrintWriter.println("Unknown argument: " + nextArg12);
                                            return -1;
                                        }
                                        outPrintWriter.println(getPowerSaveWhitelistExceptIdleInternal(substring2));
                                    } else if (addPowerSaveWhitelistExceptIdleInternal(substring2)) {
                                        outPrintWriter.println("Added: " + substring2);
                                    } else {
                                        outPrintWriter.println("Unknown package: " + substring2);
                                    }
                                    nextArg12 = shell.getNextArg();
                                    if (nextArg12 == null) {
                                    }
                                }
                                outPrintWriter.println("Package must be prefixed with +, -, or =: " + nextArg12);
                                return -1;
                            }
                            resetPowerSaveWhitelistExceptIdleInternal();
                        } finally {
                        }
                    } else if ("sys-whitelist".equals(str)) {
                        String nextArg13 = shell.getNextArg();
                        if (nextArg13 != null) {
                            getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                            clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                if (!"reset".equals(nextArg13)) {
                                    while (nextArg13.length() >= 1 && (nextArg13.charAt(0) == '-' || nextArg13.charAt(0) == '+')) {
                                        char charAt3 = nextArg13.charAt(0);
                                        String substring3 = nextArg13.substring(1);
                                        if (charAt3 != '+') {
                                            if (charAt3 == '-' && removeSystemPowerWhitelistAppInternal(substring3)) {
                                                outPrintWriter.println("Removed " + substring3);
                                            }
                                        } else if (restoreSystemPowerWhitelistAppInternal(substring3)) {
                                            outPrintWriter.println("Restored " + substring3);
                                        }
                                        nextArg13 = shell.getNextArg();
                                        if (nextArg13 == null) {
                                        }
                                    }
                                    outPrintWriter.println("Package must be prefixed with + or - " + nextArg13);
                                    return -1;
                                }
                                resetSystemPowerWhitelistInternal();
                            } finally {
                            }
                        } else {
                            if (!DumpUtils.checkDumpPermission(getContext(), "DeviceIdleController", outPrintWriter)) {
                                return -1;
                            }
                            synchronized (this) {
                                for (int i11 = 0; i11 < this.mPowerSaveWhitelistApps.size(); i11++) {
                                    outPrintWriter.print((String) this.mPowerSaveWhitelistApps.keyAt(i11));
                                    outPrintWriter.print(",");
                                    outPrintWriter.println(this.mPowerSaveWhitelistApps.valueAt(i11));
                                }
                            }
                        }
                    } else if ("motion".equals(str)) {
                        getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                        synchronized (this) {
                            clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                motionLocked();
                                outPrintWriter.print("Light state: ");
                                outPrintWriter.print(lightStateToString(this.mLightState));
                                outPrintWriter.print(", deep state: ");
                                outPrintWriter.println(stateToString(this.mState));
                            } finally {
                            }
                        }
                    } else if ("pre-idle-factor".equals(str)) {
                        getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                        synchronized (this) {
                            clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                try {
                                    nextArg = shell.getNextArg();
                                } catch (NumberFormatException unused) {
                                    outPrintWriter.println("Unknown idle timeout factor,(error code: " + i + ")");
                                }
                                if (nextArg != null) {
                                    int parseInt = Integer.parseInt(nextArg);
                                    i = setPreIdleTimeoutMode(parseInt);
                                    if (i == 1) {
                                        outPrintWriter.println("pre-idle-factor: " + parseInt);
                                    } else if (i == 2) {
                                        outPrintWriter.println("Deep idle not supported");
                                    } else if (i == 0) {
                                        outPrintWriter.println("Idle timeout factor not changed");
                                    }
                                    if (c5 == 0) {
                                        outPrintWriter.println("Unknown idle timeout factor: " + nextArg + ",(error code: " + i + ")");
                                    }
                                }
                                c5 = 0;
                                if (c5 == 0) {
                                }
                            } finally {
                            }
                        }
                    } else {
                        if (!"reset-pre-idle-factor".equals(str)) {
                            return shell.handleDefaultCommands(str);
                        }
                        getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                        synchronized (this) {
                            clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                resetPreIdleTimeoutMode();
                            } finally {
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(getContext(), "DeviceIdleController", printWriter)) {
            if (strArr != null) {
                int i = 0;
                int i2 = 0;
                while (i < strArr.length) {
                    String str = strArr[i];
                    if ("-h".equals(str)) {
                        dumpHelp(printWriter);
                        return;
                    }
                    if ("-u".equals(str)) {
                        i++;
                        if (i < strArr.length) {
                            i2 = Integer.parseInt(strArr[i]);
                        }
                    } else if (!"-a".equals(str)) {
                        if (str.length() > 0 && str.charAt(0) == '-') {
                            printWriter.println("Unknown option: " + str);
                            return;
                        }
                        Shell shell = new Shell();
                        shell.userId = i2;
                        String[] strArr2 = new String[strArr.length - i];
                        System.arraycopy(strArr, i, strArr2, 0, strArr.length - i);
                        shell.exec(this.mBinderService, (FileDescriptor) null, fileDescriptor, (FileDescriptor) null, strArr2, (ShellCallback) null, new ResultReceiver(null));
                        return;
                    }
                    i++;
                }
            }
            synchronized (this) {
                this.mConstants.dump(printWriter);
                if (this.mEventCmds[0] != 0) {
                    printWriter.println("  Idling history:");
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    for (int i3 = 99; i3 >= 0; i3--) {
                        int i4 = this.mEventCmds[i3];
                        if (i4 != 0) {
                            String str2 = i4 != 1 ? i4 != 2 ? i4 != 3 ? i4 != 4 ? i4 != 5 ? "         ??" : " deep-maint" : "  deep-idle" : "light-maint" : " light-idle" : "     normal";
                            printWriter.print("    ");
                            printWriter.print(str2);
                            printWriter.print(": ");
                            TimeUtils.formatDuration(this.mEventTimes[i3], elapsedRealtime, printWriter);
                            if (this.mEventReasons[i3] != null) {
                                printWriter.print(" (");
                                printWriter.print(this.mEventReasons[i3]);
                                printWriter.print(")");
                            }
                            printWriter.println();
                        }
                    }
                }
                int size = this.mPowerSaveWhitelistAppsExceptIdle.size();
                if (size > 0) {
                    printWriter.println("  Whitelist (except idle) system apps:");
                    for (int i5 = 0; i5 < size; i5++) {
                        printWriter.print("    ");
                        printWriter.println((String) this.mPowerSaveWhitelistAppsExceptIdle.keyAt(i5));
                    }
                }
                int size2 = this.mPowerSaveWhitelistApps.size();
                if (size2 > 0) {
                    printWriter.println("  Whitelist system apps:");
                    for (int i6 = 0; i6 < size2; i6++) {
                        printWriter.print("    ");
                        printWriter.println((String) this.mPowerSaveWhitelistApps.keyAt(i6));
                    }
                }
                int size3 = this.mRemovedFromSystemWhitelistApps.size();
                if (size3 > 0) {
                    printWriter.println("  Removed from whitelist system apps:");
                    for (int i7 = 0; i7 < size3; i7++) {
                        printWriter.print("    ");
                        printWriter.println((String) this.mRemovedFromSystemWhitelistApps.keyAt(i7));
                    }
                }
                int size4 = this.mPowerSaveWhitelistUserApps.size();
                if (size4 > 0) {
                    printWriter.println("  Whitelist user apps:");
                    for (int i8 = 0; i8 < size4; i8++) {
                        printWriter.print("    ");
                        printWriter.println((String) this.mPowerSaveWhitelistUserApps.keyAt(i8));
                    }
                }
                int size5 = this.mPowerSaveWhitelistExceptIdleAppIds.size();
                if (size5 > 0) {
                    printWriter.println("  Whitelist (except idle) all app ids:");
                    for (int i9 = 0; i9 < size5; i9++) {
                        printWriter.print("    ");
                        printWriter.print(this.mPowerSaveWhitelistExceptIdleAppIds.keyAt(i9));
                        printWriter.println();
                    }
                }
                int size6 = this.mPowerSaveWhitelistUserAppIds.size();
                if (size6 > 0) {
                    printWriter.println("  Whitelist user app ids:");
                    for (int i10 = 0; i10 < size6; i10++) {
                        printWriter.print("    ");
                        printWriter.print(this.mPowerSaveWhitelistUserAppIds.keyAt(i10));
                        printWriter.println();
                    }
                }
                int size7 = this.mPowerSaveWhitelistAllAppIds.size();
                if (size7 > 0) {
                    printWriter.println("  Whitelist all app ids:");
                    for (int i11 = 0; i11 < size7; i11++) {
                        printWriter.print("    ");
                        printWriter.print(this.mPowerSaveWhitelistAllAppIds.keyAt(i11));
                        printWriter.println();
                    }
                }
                int size8 = this.mUserWhitelistHistoryBuffer.size();
                if (size8 > 0) {
                    printWriter.println("  Whitelist History for User App:");
                    printWriter.println("  - history count : " + size8);
                    UserWhitelistHistoryInfo[] userWhitelistHistoryInfoArr = (UserWhitelistHistoryInfo[]) this.mUserWhitelistHistoryBuffer.toArray();
                    for (int i12 = 0; i12 < size8; i12++) {
                        printWriter.print("    ");
                        printWriter.print(userWhitelistHistoryInfoArr[i12].toString());
                        printWriter.println();
                    }
                }
                dumpTempWhitelistSchedule(printWriter, true);
                int[] iArr = this.mTempWhitelistAppIdArray;
                int length = iArr != null ? iArr.length : 0;
                if (length > 0) {
                    printWriter.println("  Temp whitelist app ids:");
                    for (int i13 = 0; i13 < length; i13++) {
                        printWriter.print("    ");
                        printWriter.print(this.mTempWhitelistAppIdArray[i13]);
                        printWriter.println();
                    }
                }
                printWriter.print("  mLightEnabled=");
                printWriter.print(this.mLightEnabled);
                printWriter.print("  mDeepEnabled=");
                printWriter.println(this.mDeepEnabled);
                printWriter.print("  mForceIdle=");
                printWriter.println(this.mForceIdle);
                printWriter.print("  mUseMotionSensor=");
                printWriter.print(this.mUseMotionSensor);
                if (this.mUseMotionSensor) {
                    printWriter.print(" mMotionSensor=");
                    printWriter.println(this.mMotionSensor);
                } else {
                    printWriter.println();
                }
                printWriter.print("  mScreenOn=");
                printWriter.println(this.mScreenOn);
                printWriter.print("  mScreenLocked=");
                printWriter.println(this.mScreenLocked);
                printWriter.print("  mNetworkConnected=");
                printWriter.println(this.mNetworkConnected);
                printWriter.print("  mCharging=");
                printWriter.println(this.mCharging);
                printWriter.print("  activeEmergencyCall=");
                printWriter.println(this.mEmergencyCallListener.isEmergencyCallActive());
                if (this.mConstraints.size() != 0) {
                    printWriter.println("  mConstraints={");
                    for (int i14 = 0; i14 < this.mConstraints.size(); i14++) {
                        DeviceIdleConstraintTracker deviceIdleConstraintTracker = (DeviceIdleConstraintTracker) this.mConstraints.valueAt(i14);
                        printWriter.print("    \"");
                        printWriter.print(deviceIdleConstraintTracker.name);
                        printWriter.print("\"=");
                        if (deviceIdleConstraintTracker.minState == this.mState) {
                            printWriter.println(deviceIdleConstraintTracker.active);
                        } else {
                            printWriter.print("ignored <mMinState=");
                            printWriter.print(stateToString(deviceIdleConstraintTracker.minState));
                            printWriter.println(">");
                        }
                    }
                    printWriter.println("  }");
                }
                if (this.mUseMotionSensor || this.mStationaryListeners.size() > 0) {
                    printWriter.print("  mMotionActive=");
                    printWriter.println(this.mMotionListener.active);
                    printWriter.print("  mNotMoving=");
                    printWriter.println(this.mNotMoving);
                    printWriter.print("  mMotionListener.activatedTimeElapsed=");
                    printWriter.println(this.mMotionListener.activatedTimeElapsed);
                    printWriter.print("  mLastMotionEventElapsed=");
                    printWriter.println(this.mLastMotionEventElapsed);
                    printWriter.print("  ");
                    printWriter.print(this.mStationaryListeners.size());
                    printWriter.println(" stationary listeners registered");
                }
                if (this.mIsLocationPrefetchEnabled) {
                    printWriter.print("  mLocating=");
                    printWriter.print(this.mLocating);
                    printWriter.print(" mHasGps=");
                    printWriter.print(this.mHasGps);
                    printWriter.print(" mHasFused=");
                    printWriter.print(this.mHasFusedLocation);
                    printWriter.print(" mLocated=");
                    printWriter.println(this.mLocated);
                    if (this.mLastGenericLocation != null) {
                        printWriter.print("  mLastGenericLocation=");
                        printWriter.println(this.mLastGenericLocation);
                    }
                    if (this.mLastGpsLocation != null) {
                        printWriter.print("  mLastGpsLocation=");
                        printWriter.println(this.mLastGpsLocation);
                    }
                } else {
                    printWriter.println("  Location prefetching disabled");
                }
                printWriter.print("  mState=");
                printWriter.print(stateToString(this.mState));
                printWriter.print(" mLightState=");
                printWriter.println(lightStateToString(this.mLightState));
                printWriter.print("  mInactiveTimeout=");
                TimeUtils.formatDuration(this.mInactiveTimeout, printWriter);
                printWriter.println();
                if (this.mActiveIdleOpCount != 0) {
                    printWriter.print("  mActiveIdleOpCount=");
                    printWriter.println(this.mActiveIdleOpCount);
                }
                if (this.mNextAlarmTime != 0) {
                    printWriter.print("  mNextAlarmTime=");
                    TimeUtils.formatDuration(this.mNextAlarmTime, SystemClock.elapsedRealtime(), printWriter);
                    printWriter.println();
                }
                if (this.mNextIdlePendingDelay != 0) {
                    printWriter.print("  mNextIdlePendingDelay=");
                    TimeUtils.formatDuration(this.mNextIdlePendingDelay, printWriter);
                    printWriter.println();
                }
                if (this.mNextIdleDelay != 0) {
                    printWriter.print("  mNextIdleDelay=");
                    TimeUtils.formatDuration(this.mNextIdleDelay, printWriter);
                    printWriter.println();
                }
                if (this.mNextLightIdleDelay != 0) {
                    printWriter.print("  mNextLightIdleDelay=");
                    TimeUtils.formatDuration(this.mNextLightIdleDelay, printWriter);
                    if (this.mConstants.USE_WINDOW_ALARMS) {
                        printWriter.print(" (flex=");
                        TimeUtils.formatDuration(this.mNextLightIdleDelayFlex, printWriter);
                        printWriter.println(")");
                    } else {
                        printWriter.println();
                    }
                }
                if (this.mNextLightAlarmTime != 0) {
                    printWriter.print("  mNextLightAlarmTime=");
                    TimeUtils.formatDuration(this.mNextLightAlarmTime, SystemClock.elapsedRealtime(), printWriter);
                    printWriter.println();
                }
                if (this.mCurLightIdleBudget != 0) {
                    printWriter.print("  mCurLightIdleBudget=");
                    TimeUtils.formatDuration(this.mCurLightIdleBudget, printWriter);
                    printWriter.println();
                }
                if (this.mMaintenanceStartTime != 0) {
                    printWriter.print("  mMaintenanceStartTime=");
                    TimeUtils.formatDuration(this.mMaintenanceStartTime, SystemClock.elapsedRealtime(), printWriter);
                    printWriter.println();
                }
                if (this.mJobsActive) {
                    printWriter.print("  mJobsActive=");
                    printWriter.println(this.mJobsActive);
                }
                if (this.mAlarmsActive) {
                    printWriter.print("  mAlarmsActive=");
                    printWriter.println(this.mAlarmsActive);
                }
                if (Math.abs(this.mPreIdleFactor - 1.0f) > MIN_PRE_IDLE_FACTOR_CHANGE) {
                    printWriter.print("  mPreIdleFactor=");
                    printWriter.println(this.mPreIdleFactor);
                }
            }
        }
    }

    public void dumpTempWhitelistSchedule(PrintWriter printWriter, boolean z) {
        String str;
        int size = this.mTempWhitelistAppIdEndTimes.size();
        if (size > 0) {
            if (z) {
                printWriter.println("  Temp whitelist schedule:");
                str = "    ";
            } else {
                str = "";
            }
            long elapsedRealtime = SystemClock.elapsedRealtime();
            for (int i = 0; i < size; i++) {
                printWriter.print(str);
                printWriter.print("UID=");
                printWriter.print(this.mTempWhitelistAppIdEndTimes.keyAt(i));
                printWriter.print(": ");
                Pair pair = (Pair) this.mTempWhitelistAppIdEndTimes.valueAt(i);
                TimeUtils.formatDuration(((MutableLong) pair.first).value, elapsedRealtime, printWriter);
                printWriter.print(" - ");
                printWriter.println((String) pair.second);
            }
        }
    }
}
