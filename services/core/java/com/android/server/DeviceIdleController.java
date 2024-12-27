package com.android.server;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AlarmManager;
import android.app.BroadcastOptions;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
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
import android.os.WearModeManagerInternal;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.telephony.emergency.EmergencyNumber;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.EventLog;
import android.util.Log;
import android.util.MutableLong;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.TimeUtils;
import android.util.Xml;
import com.android.internal.app.IBatteryStats;
import com.android.internal.util.RingBuffer;
import com.android.internal.util.jobs.ArrayUtils;
import com.android.internal.util.jobs.DumpUtils;
import com.android.internal.util.jobs.FastXmlSerializer;
import com.android.modules.expresslog.Counter;
import com.android.server.AnyMotionDetector;
import com.android.server.AppStateTrackerImpl;
import com.android.server.DeviceIdleInternal;
import com.android.server.PowerAllowlistInternal;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.alarm.AlarmManagerService;
import com.android.server.am.BatteryStatsService;
import com.android.server.clipboard.ClipboardService;
import com.android.server.deviceidle.DeviceIdleConstraintTracker;
import com.android.server.deviceidle.Flags;
import com.android.server.deviceidle.IDeviceIdleConstraint;
import com.android.server.deviceidle.TvConstraintController;
import com.android.server.net.NetworkPolicyManagerService;
import com.android.server.utils.UserSettingDeviceConfigMediator$SettingsOverridesIndividualMediator;
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
import java.util.function.BiFunction;
import java.util.function.Consumer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class DeviceIdleController extends SystemService implements AnyMotionDetector.DeviceIdleCallback {
    public static final boolean DEBUG = Log.isLoggable("DeviceIdleController", 3);
    static final int LIGHT_STATE_ACTIVE = 0;
    static final int LIGHT_STATE_IDLE = 4;
    static final int LIGHT_STATE_IDLE_MAINTENANCE = 6;
    static final int LIGHT_STATE_INACTIVE = 1;
    static final int LIGHT_STATE_OVERRIDE = 7;
    static final int LIGHT_STATE_WAITING_FOR_NETWORK = 5;
    static final int MSG_REPORT_STATIONARY_STATUS = 7;
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
    public AlarmManager mAlarmManager;
    public boolean mAlarmsActive;
    public final RingBuffer mAllowlistHistoryInfo;
    public AnyMotionDetector mAnyMotionDetector;
    public final AppStateTrackerImpl mAppStateTracker;
    public boolean mBatterySaverEnabled;
    public IBatteryStats mBatteryStats;
    public BinderService mBinderService;
    public boolean mCharging;
    public final AtomicFile mConfigFile;
    public Constants mConstants;
    public TvConstraintController mConstraintController;
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
    public boolean mForceModeManagerOffBodyState;
    public boolean mForceModeManagerQuickDozeRequest;
    public String mForceType;
    public final AnonymousClass6 mGenericLocationListener;
    public PowerManager.WakeLock mGoingIdleWakeLock;
    public final AnonymousClass6 mGpsLocationListener;
    public final MyHandler mHandler;
    public boolean mHasFusedLocation;
    public boolean mHasGps;
    public Intent mIdleIntent;
    public Bundle mIdleIntentOptions;
    public final AnonymousClass4 mIdleStartedDoneReceiver;
    public long mInactiveTimeout;
    public final Injector mInjector;
    public final AnonymousClass1 mInteractivityReceiver;
    public final boolean mIsLocationPrefetchEnabled;
    public boolean mIsOffBody;
    public boolean mJobsActive;
    public Location mLastGenericLocation;
    public Location mLastGpsLocation;
    public long mLastMotionEventElapsed;
    public final DeviceIdleController$$ExternalSyntheticLambda8 mLightAlarmListener;
    public final BatteryLevel mLightBatteryLevel;
    public boolean mLightEnabled;
    public Intent mLightIdleIntent;
    public Bundle mLightIdleIntentOptions;
    public int mLightState;
    public ActivityManagerInternal mLocalActivityManager;
    public ActivityTaskManagerInternal mLocalActivityTaskManager;
    public AlarmManagerService.LocalService mLocalAlarmManager;
    public BatteryManagerInternal mLocalBatteryManager;
    public PowerManagerInternal mLocalPowerManager;
    public LocalService mLocalService;
    public boolean mLocated;
    public boolean mLocating;
    public LocationRequest mLocationRequest;
    public long mMaintenanceStartTime;
    final ModeManagerOffBodyStateConsumer mModeManagerOffBodyStateConsumer;
    final ModeManagerQuickDozeRequestConsumer mModeManagerQuickDozeRequestConsumer;
    public boolean mModeManagerRequestedQuickDoze;
    final MotionListener mMotionListener;
    public final DeviceIdleController$$ExternalSyntheticLambda8 mMotionRegistrationAlarmListener;
    public Sensor mMotionSensor;
    public final DeviceIdleController$$ExternalSyntheticLambda8 mMotionTimeoutAlarmListener;
    public boolean mNetworkConnected;
    public INetworkPolicyManager mNetworkPolicyManager;
    public NetworkPolicyManagerService.NetworkPolicyManagerInternalImpl mNetworkPolicyManagerInternal;
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
    public final ArraySet mPowerSaveWhitelistPrintErrorApps;
    public final ArraySet mPowerSaveWhitelistReviewedApps;
    public final SparseBooleanArray mPowerSaveWhitelistSystemAppIds;
    public final SparseBooleanArray mPowerSaveWhitelistSystemAppIdsExceptIdle;
    public int[] mPowerSaveWhitelistUserAppIdArray;
    public final SparseBooleanArray mPowerSaveWhitelistUserAppIds;
    public final ArrayMap mPowerSaveWhitelistUserApps;
    public final ArraySet mPowerSaveWhitelistUserAppsExceptIdle;
    public boolean mQuickDozeActivated;
    public boolean mQuickDozeActivatedWhileIdling;
    public final AnonymousClass1 mReceiver;
    public final ArrayMap mRemovedFromSystemWhitelistApps;
    public boolean mScreenLocked;
    public final AnonymousClass8 mScreenObserver;
    public boolean mScreenOn;
    public final AnonymousClass2 mSensingTimeoutAlarmListener;
    public SensorManager mSensorManager;
    public int mState;
    public final ArraySet mStationaryListeners;
    public final ArraySet mTempAllowlistChangeListeners;
    public int[] mTempWhitelistAppIdArray;
    public final SparseArray mTempWhitelistAppIdEndTimes;
    public final boolean mUseMotionSensor;

    public final class AllowlistHistoryInfo {
        public final TargetPkg caller;
        public final TargetPkg target;
        public final long time = System.currentTimeMillis();
        public final int type;

        public AllowlistHistoryInfo(int i, TargetPkg targetPkg, TargetPkg targetPkg2) {
            this.type = i;
            this.caller = targetPkg;
            this.target = targetPkg2;
        }

        public final String toString() {
            Date date = new Date(this.time);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            StringBuilder sb = new StringBuilder();
            sb.append("[" + simpleDateFormat.format(date) + "]");
            StringBuilder sb2 = new StringBuilder();
            TargetPkg targetPkg = this.target;
            sb2.append(targetPkg.pkgName);
            sb2.append("(appid=");
            String m = AmFmBandRange$$ExternalSyntheticOutline0.m(targetPkg.uid, sb2, ") by ");
            int i = this.type;
            String str = "null";
            TargetPkg targetPkg2 = this.caller;
            switch (i) {
                case 0:
                    sb.append("[add    ] " + m);
                    if (targetPkg2 != null) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(targetPkg2.pkgName);
                        sb3.append("(pid ");
                        str = AmFmBandRange$$ExternalSyntheticOutline0.m(targetPkg2.uid, sb3, ")");
                    }
                    sb.append(str);
                    break;
                case 1:
                    sb.append("[add    ] " + m + "deviceidle.xml");
                    break;
                case 2:
                    sb.append("[add    ] " + m + "dumpsys");
                    break;
                case 3:
                    sb.append("[remove ] " + m);
                    if (targetPkg2 != null) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(targetPkg2.pkgName);
                        sb4.append("(pid ");
                        str = AmFmBandRange$$ExternalSyntheticOutline0.m(targetPkg2.uid, sb4, ")");
                    }
                    sb.append(str);
                    break;
                case 4:
                    sb.append("[remove ] " + m + "dumpsys");
                    break;
                case 5:
                    sb.append("[remove ] " + m + "package removed");
                    break;
                case 6:
                    sb.append("[restore] " + m);
                    if (targetPkg2 != null) {
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(targetPkg2.pkgName);
                        sb5.append("(pid ");
                        str = AmFmBandRange$$ExternalSyntheticOutline0.m(targetPkg2.uid, sb5, ")");
                    }
                    sb.append(str);
                    break;
                case 7:
                    sb.append("[restore] " + m + "dumpsys");
                    break;
                case 8:
                    sb.append("[removes] " + m);
                    if (targetPkg2 != null) {
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append(targetPkg2.pkgName);
                        sb6.append("(pid ");
                        str = AmFmBandRange$$ExternalSyntheticOutline0.m(targetPkg2.uid, sb6, ")");
                    }
                    sb.append(str);
                    break;
                case 9:
                    sb.append("[removes] " + m + "dumpsys");
                    break;
                case 10:
                    sb.append("[removes] " + m + "deviceidle.xml");
                    break;
                default:
                    Slog.e("DeviceIdleController", "Unknown type of allowlist");
                    break;
            }
            return sb.toString();
        }
    }

    public final class BatteryLevel {
        public float prev = -1.0f;
        public float curr = -1.0f;
        public long prevTime = 0;
        public long currTime = 0;
    }

    public final class BinderService extends IDeviceIdleController.Stub {
        public BinderService() {
        }

        public final void addPowerSaveTempWhitelistApp(String str, long j, int i, int i2, String str2) {
            DeviceIdleController.this.addPowerSaveTempAllowlistAppChecked(i, i2, str, str2, j);
        }

        public final long addPowerSaveTempWhitelistAppForMms(String str, int i, int i2, String str2) {
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            long j = deviceIdleController.mConstants.MMS_TEMP_APP_ALLOWLIST_DURATION_MS;
            deviceIdleController.addPowerSaveTempAllowlistAppChecked(i, i2, str, str2, j);
            return j;
        }

        public final long addPowerSaveTempWhitelistAppForSms(String str, int i, int i2, String str2) {
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            long j = deviceIdleController.mConstants.SMS_TEMP_APP_ALLOWLIST_DURATION_MS;
            deviceIdleController.addPowerSaveTempAllowlistAppChecked(i, i2, str, str2, j);
            return j;
        }

        public final void addPowerSaveWhitelistApp(String str) {
            if (DeviceIdleController.DEBUG) {
                BootReceiver$$ExternalSyntheticOutline0.m58m("addPowerSaveWhitelistApp(name = ", str, ")", "DeviceIdleController");
            }
            addPowerSaveWhitelistApps(Collections.singletonList(str));
        }

        public final int addPowerSaveWhitelistApps(List list) {
            Slog.i("DeviceIdleController", "addPowerSaveWhitelistApps(name = " + list + ")");
            DeviceIdleController.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            TargetPkg targetPkg = new TargetPkg(DeviceIdleController.this, Binder.getCallingPid());
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return DeviceIdleController.this.addPowerSaveWhitelistAppsInternal(list, 0, targetPkg);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            if (DumpUtils.checkDumpPermission(deviceIdleController.getContext(), "DeviceIdleController", printWriter)) {
                if (strArr != null) {
                    int i = 0;
                    int i2 = 0;
                    while (i < strArr.length) {
                        String str = strArr[i];
                        if ("-h".equals(str)) {
                            DeviceIdleController.dumpHelp(printWriter);
                            return;
                        }
                        if ("-u".equals(str)) {
                            i++;
                            if (i < strArr.length) {
                                i2 = Integer.parseInt(strArr[i]);
                            }
                        } else if (!"-a".equals(str)) {
                            if (str.length() > 0 && str.charAt(0) == '-') {
                                printWriter.println("Unknown option: ".concat(str));
                                return;
                            }
                            Shell shell = deviceIdleController.new Shell();
                            shell.userId = i2;
                            String[] strArr2 = new String[strArr.length - i];
                            System.arraycopy(strArr, i, strArr2, 0, strArr.length - i);
                            shell.exec(deviceIdleController.mBinderService, (FileDescriptor) null, fileDescriptor, (FileDescriptor) null, strArr2, (ShellCallback) null, new ResultReceiver(null));
                            return;
                        }
                        i++;
                    }
                }
                synchronized (deviceIdleController) {
                    try {
                        deviceIdleController.mConstants.dump(printWriter);
                        if (deviceIdleController.mEventCmds[0] != 0) {
                            printWriter.println("  Idling history:");
                            long elapsedRealtime = SystemClock.elapsedRealtime();
                            for (int i3 = 99; i3 >= 0; i3--) {
                                int i4 = deviceIdleController.mEventCmds[i3];
                                if (i4 != 0) {
                                    String str2 = i4 != 1 ? i4 != 2 ? i4 != 3 ? i4 != 4 ? i4 != 5 ? "         ??" : " deep-maint" : "  deep-idle" : "light-maint" : " light-idle" : "     normal";
                                    printWriter.print("    ");
                                    printWriter.print(str2);
                                    printWriter.print(": ");
                                    TimeUtils.formatDuration(deviceIdleController.mEventTimes[i3], elapsedRealtime, printWriter);
                                    if (deviceIdleController.mEventReasons[i3] != null) {
                                        printWriter.print(" (");
                                        printWriter.print(deviceIdleController.mEventReasons[i3]);
                                        printWriter.print(")");
                                    }
                                    printWriter.println();
                                }
                            }
                        }
                        int size = deviceIdleController.mPowerSaveWhitelistAppsExceptIdle.size();
                        if (size > 0) {
                            printWriter.println("  Whitelist (except idle) system apps:");
                            for (int i5 = 0; i5 < size; i5++) {
                                printWriter.print("    ");
                                printWriter.println((String) deviceIdleController.mPowerSaveWhitelistAppsExceptIdle.keyAt(i5));
                            }
                        }
                        int size2 = deviceIdleController.mPowerSaveWhitelistApps.size();
                        if (size2 > 0) {
                            printWriter.println("  Whitelist system apps:");
                            for (int i6 = 0; i6 < size2; i6++) {
                                printWriter.print("    ");
                                printWriter.println((String) deviceIdleController.mPowerSaveWhitelistApps.keyAt(i6));
                            }
                        }
                        int size3 = deviceIdleController.mRemovedFromSystemWhitelistApps.size();
                        if (size3 > 0) {
                            printWriter.println("  Removed from whitelist system apps:");
                            for (int i7 = 0; i7 < size3; i7++) {
                                printWriter.print("    ");
                                printWriter.println((String) deviceIdleController.mRemovedFromSystemWhitelistApps.keyAt(i7));
                            }
                        }
                        int size4 = deviceIdleController.mPowerSaveWhitelistUserApps.size();
                        if (size4 > 0) {
                            printWriter.println("  Whitelist user apps:");
                            for (int i8 = 0; i8 < size4; i8++) {
                                printWriter.print("    ");
                                printWriter.println((String) deviceIdleController.mPowerSaveWhitelistUserApps.keyAt(i8));
                            }
                        }
                        int size5 = deviceIdleController.mPowerSaveWhitelistExceptIdleAppIds.size();
                        if (size5 > 0) {
                            printWriter.println("  Whitelist (except idle) all app ids:");
                            for (int i9 = 0; i9 < size5; i9++) {
                                printWriter.print("    ");
                                printWriter.print(deviceIdleController.mPowerSaveWhitelistExceptIdleAppIds.keyAt(i9));
                                printWriter.println();
                            }
                        }
                        int size6 = deviceIdleController.mPowerSaveWhitelistUserAppIds.size();
                        if (size6 > 0) {
                            printWriter.println("  Whitelist user app ids:");
                            for (int i10 = 0; i10 < size6; i10++) {
                                printWriter.print("    ");
                                printWriter.print(deviceIdleController.mPowerSaveWhitelistUserAppIds.keyAt(i10));
                                printWriter.println();
                            }
                        }
                        int size7 = deviceIdleController.mPowerSaveWhitelistAllAppIds.size();
                        if (size7 > 0) {
                            printWriter.println("  Whitelist all app ids:");
                            for (int i11 = 0; i11 < size7; i11++) {
                                printWriter.print("    ");
                                printWriter.print(deviceIdleController.mPowerSaveWhitelistAllAppIds.keyAt(i11));
                                printWriter.println();
                            }
                        }
                        int size8 = deviceIdleController.mAllowlistHistoryInfo.size();
                        if (size8 > 0) {
                            printWriter.println("  Allowlist History:");
                            printWriter.println("  - history count : " + size8);
                            AllowlistHistoryInfo[] allowlistHistoryInfoArr = (AllowlistHistoryInfo[]) deviceIdleController.mAllowlistHistoryInfo.toArray();
                            for (int i12 = 0; i12 < size8; i12++) {
                                printWriter.print("    ");
                                printWriter.print(allowlistHistoryInfoArr[i12].toString());
                                printWriter.println();
                            }
                        }
                        deviceIdleController.dumpTempWhitelistScheduleLocked(printWriter, true);
                        int[] iArr = deviceIdleController.mTempWhitelistAppIdArray;
                        int length = iArr != null ? iArr.length : 0;
                        if (length > 0) {
                            printWriter.println("  Temp whitelist app ids:");
                            for (int i13 = 0; i13 < length; i13++) {
                                printWriter.print("    ");
                                printWriter.print(deviceIdleController.mTempWhitelistAppIdArray[i13]);
                                printWriter.println();
                            }
                        }
                        printWriter.println("  Doze Allowlist Gatekeeper: false");
                        printWriter.print("  mLightEnabled=");
                        printWriter.print(deviceIdleController.mLightEnabled);
                        printWriter.print("  mDeepEnabled=");
                        printWriter.println(deviceIdleController.mDeepEnabled);
                        printWriter.print("  mForceIdle=");
                        printWriter.println(deviceIdleController.mForceIdle);
                        if (deviceIdleController.mForceIdle) {
                            printWriter.print(" mForceType=");
                            printWriter.println(deviceIdleController.mForceType);
                        }
                        printWriter.print("  mUseMotionSensor=");
                        printWriter.print(deviceIdleController.mUseMotionSensor);
                        if (deviceIdleController.mUseMotionSensor) {
                            printWriter.print(" mMotionSensor=");
                            printWriter.println(deviceIdleController.mMotionSensor);
                        } else {
                            printWriter.println();
                        }
                        printWriter.print("  mScreenOn=");
                        printWriter.println(deviceIdleController.mScreenOn);
                        printWriter.print("  mScreenLocked=");
                        printWriter.println(deviceIdleController.mScreenLocked);
                        printWriter.print("  mNetworkConnected=");
                        printWriter.println(deviceIdleController.mNetworkConnected);
                        printWriter.print("  mCharging=");
                        printWriter.println(deviceIdleController.mCharging);
                        printWriter.print("  activeEmergencyCall=");
                        printWriter.println(deviceIdleController.mEmergencyCallListener.mIsEmergencyCallActive);
                        if (deviceIdleController.mConstraints.size() != 0) {
                            printWriter.println("  mConstraints={");
                            for (int i14 = 0; i14 < deviceIdleController.mConstraints.size(); i14++) {
                                DeviceIdleConstraintTracker deviceIdleConstraintTracker = (DeviceIdleConstraintTracker) deviceIdleController.mConstraints.valueAt(i14);
                                printWriter.print("    \"");
                                printWriter.print(deviceIdleConstraintTracker.name);
                                printWriter.print("\"=");
                                if (deviceIdleConstraintTracker.minState == deviceIdleController.mState) {
                                    printWriter.println(deviceIdleConstraintTracker.active);
                                } else {
                                    printWriter.print("ignored <mMinState=");
                                    printWriter.print(DeviceIdleController.stateToString(deviceIdleConstraintTracker.minState));
                                    printWriter.println(">");
                                }
                            }
                            printWriter.println("  }");
                        }
                        if (deviceIdleController.mUseMotionSensor || deviceIdleController.mStationaryListeners.size() > 0) {
                            printWriter.print("  mMotionActive=");
                            printWriter.println(deviceIdleController.mMotionListener.active);
                            printWriter.print("  mNotMoving=");
                            printWriter.println(deviceIdleController.mNotMoving);
                            printWriter.print("  mMotionListener.activatedTimeElapsed=");
                            printWriter.println(deviceIdleController.mMotionListener.activatedTimeElapsed);
                            printWriter.print("  mLastMotionEventElapsed=");
                            printWriter.println(deviceIdleController.mLastMotionEventElapsed);
                            printWriter.print("  ");
                            printWriter.print(deviceIdleController.mStationaryListeners.size());
                            printWriter.println(" stationary listeners registered");
                        }
                        if (deviceIdleController.mIsLocationPrefetchEnabled) {
                            printWriter.print("  mLocating=");
                            printWriter.print(deviceIdleController.mLocating);
                            printWriter.print(" mHasGps=");
                            printWriter.print(deviceIdleController.mHasGps);
                            printWriter.print(" mHasFused=");
                            printWriter.print(deviceIdleController.mHasFusedLocation);
                            printWriter.print(" mLocated=");
                            printWriter.println(deviceIdleController.mLocated);
                            if (deviceIdleController.mLastGenericLocation != null) {
                                printWriter.print("  mLastGenericLocation=");
                                printWriter.println(deviceIdleController.mLastGenericLocation);
                            }
                            if (deviceIdleController.mLastGpsLocation != null) {
                                printWriter.print("  mLastGpsLocation=");
                                printWriter.println(deviceIdleController.mLastGpsLocation);
                            }
                        } else {
                            printWriter.println("  Location prefetching disabled");
                        }
                        printWriter.print("  mState=");
                        printWriter.print(DeviceIdleController.stateToString(deviceIdleController.mState));
                        printWriter.print(" mLightState=");
                        printWriter.println(DeviceIdleController.lightStateToString(deviceIdleController.mLightState));
                        printWriter.print("  mInactiveTimeout=");
                        TimeUtils.formatDuration(deviceIdleController.mInactiveTimeout, printWriter);
                        printWriter.println();
                        if (deviceIdleController.mActiveIdleOpCount != 0) {
                            printWriter.print("  mActiveIdleOpCount=");
                            printWriter.println(deviceIdleController.mActiveIdleOpCount);
                        }
                        if (deviceIdleController.mNextAlarmTime != 0) {
                            printWriter.print("  mNextAlarmTime=");
                            TimeUtils.formatDuration(deviceIdleController.mNextAlarmTime, SystemClock.elapsedRealtime(), printWriter);
                            printWriter.println();
                        }
                        if (deviceIdleController.mNextIdlePendingDelay != 0) {
                            printWriter.print("  mNextIdlePendingDelay=");
                            TimeUtils.formatDuration(deviceIdleController.mNextIdlePendingDelay, printWriter);
                            printWriter.println();
                        }
                        if (deviceIdleController.mNextIdleDelay != 0) {
                            printWriter.print("  mNextIdleDelay=");
                            TimeUtils.formatDuration(deviceIdleController.mNextIdleDelay, printWriter);
                            printWriter.println();
                        }
                        if (deviceIdleController.mNextLightIdleDelay != 0) {
                            printWriter.print("  mNextLightIdleDelay=");
                            TimeUtils.formatDuration(deviceIdleController.mNextLightIdleDelay, printWriter);
                            if (deviceIdleController.mConstants.USE_WINDOW_ALARMS) {
                                printWriter.print(" (flex=");
                                TimeUtils.formatDuration(deviceIdleController.mNextLightIdleDelayFlex, printWriter);
                                printWriter.println(")");
                            } else {
                                printWriter.println();
                            }
                        }
                        if (deviceIdleController.mNextLightAlarmTime != 0) {
                            printWriter.print("  mNextLightAlarmTime=");
                            TimeUtils.formatDuration(deviceIdleController.mNextLightAlarmTime, SystemClock.elapsedRealtime(), printWriter);
                            printWriter.println();
                        }
                        if (deviceIdleController.mCurLightIdleBudget != 0) {
                            printWriter.print("  mCurLightIdleBudget=");
                            TimeUtils.formatDuration(deviceIdleController.mCurLightIdleBudget, printWriter);
                            printWriter.println();
                        }
                        if (deviceIdleController.mMaintenanceStartTime != 0) {
                            printWriter.print("  mMaintenanceStartTime=");
                            TimeUtils.formatDuration(deviceIdleController.mMaintenanceStartTime, SystemClock.elapsedRealtime(), printWriter);
                            printWriter.println();
                        }
                        if (deviceIdleController.mJobsActive) {
                            printWriter.print("  mJobsActive=");
                            printWriter.println(deviceIdleController.mJobsActive);
                        }
                        if (deviceIdleController.mAlarmsActive) {
                            printWriter.print("  mAlarmsActive=");
                            printWriter.println(deviceIdleController.mAlarmsActive);
                        }
                        if (deviceIdleController.mConstants.USE_MODE_MANAGER) {
                            printWriter.print("  mModeManagerRequestedQuickDoze=");
                            printWriter.println(deviceIdleController.mModeManagerRequestedQuickDoze);
                            printWriter.print("  mIsOffBody=");
                            printWriter.println(deviceIdleController.mIsOffBody);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public final void exitIdle(String str) {
            exitIdle_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DeviceIdleController deviceIdleController = DeviceIdleController.this;
                synchronized (deviceIdleController) {
                    deviceIdleController.becomeActiveLocked(Binder.getCallingUid(), str);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int[] getAppIdTempWhitelist() {
            int[] iArr;
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            synchronized (deviceIdleController) {
                iArr = deviceIdleController.mTempWhitelistAppIdArray;
            }
            return iArr;
        }

        public final int[] getAppIdUserWhitelist() {
            int[] iArr;
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            synchronized (deviceIdleController) {
                iArr = deviceIdleController.mPowerSaveWhitelistUserAppIdArray;
            }
            return iArr;
        }

        public final int[] getAppIdWhitelist() {
            int[] iArr;
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            synchronized (deviceIdleController) {
                iArr = deviceIdleController.mPowerSaveWhitelistAllAppIdArray;
            }
            return iArr;
        }

        public final int[] getAppIdWhitelistExceptIdle() {
            int[] iArr;
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            synchronized (deviceIdleController) {
                iArr = deviceIdleController.mPowerSaveWhitelistExceptIdleAppIdArray;
            }
            return iArr;
        }

        public final String[] getFullPowerWhitelist() {
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            return (String[]) ArrayUtils.filter(deviceIdleController.getFullPowerWhitelistInternalUnchecked(), new DeviceIdleController$$ExternalSyntheticLambda14(5), new DeviceIdleController$$ExternalSyntheticLambda15(deviceIdleController, Binder.getCallingUid(), UserHandle.getCallingUserId(), 5));
        }

        public final String[] getFullPowerWhitelistExceptIdle() {
            String[] strArr;
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            int callingUid = Binder.getCallingUid();
            int callingUserId = UserHandle.getCallingUserId();
            synchronized (deviceIdleController) {
                try {
                    strArr = new String[deviceIdleController.mPowerSaveWhitelistAppsExceptIdle.size() + deviceIdleController.mPowerSaveWhitelistUserApps.size()];
                    int i = 0;
                    for (int i2 = 0; i2 < deviceIdleController.mPowerSaveWhitelistAppsExceptIdle.size(); i2++) {
                        strArr[i] = (String) deviceIdleController.mPowerSaveWhitelistAppsExceptIdle.keyAt(i2);
                        i++;
                    }
                    for (int i3 = 0; i3 < deviceIdleController.mPowerSaveWhitelistUserApps.size(); i3++) {
                        strArr[i] = (String) deviceIdleController.mPowerSaveWhitelistUserApps.keyAt(i3);
                        i++;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return (String[]) ArrayUtils.filter(strArr, new DeviceIdleController$$ExternalSyntheticLambda14(1), new DeviceIdleController$$ExternalSyntheticLambda15(deviceIdleController, callingUid, callingUserId, 1));
        }

        public final String[] getRemovedSystemPowerWhitelistApps() {
            String[] strArr;
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            int callingUid = Binder.getCallingUid();
            int callingUserId = UserHandle.getCallingUserId();
            synchronized (deviceIdleController) {
                try {
                    int size = deviceIdleController.mRemovedFromSystemWhitelistApps.size();
                    strArr = new String[size];
                    for (int i = 0; i < size; i++) {
                        strArr[i] = (String) deviceIdleController.mRemovedFromSystemWhitelistApps.keyAt(i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return (String[]) ArrayUtils.filter(strArr, new DeviceIdleController$$ExternalSyntheticLambda14(2), new DeviceIdleController$$ExternalSyntheticLambda15(deviceIdleController, callingUid, callingUserId, 2));
        }

        public final String[] getSystemPowerWhitelist() {
            String[] strArr;
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            int callingUid = Binder.getCallingUid();
            int callingUserId = UserHandle.getCallingUserId();
            synchronized (deviceIdleController) {
                try {
                    int size = deviceIdleController.mPowerSaveWhitelistApps.size();
                    strArr = new String[size];
                    for (int i = 0; i < size; i++) {
                        strArr[i] = (String) deviceIdleController.mPowerSaveWhitelistApps.keyAt(i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return (String[]) ArrayUtils.filter(strArr, new DeviceIdleController$$ExternalSyntheticLambda14(3), new DeviceIdleController$$ExternalSyntheticLambda15(deviceIdleController, callingUid, callingUserId, 3));
        }

        public final String[] getSystemPowerWhitelistExceptIdle() {
            String[] strArr;
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            int callingUid = Binder.getCallingUid();
            int callingUserId = UserHandle.getCallingUserId();
            synchronized (deviceIdleController) {
                try {
                    int size = deviceIdleController.mPowerSaveWhitelistAppsExceptIdle.size();
                    strArr = new String[size];
                    for (int i = 0; i < size; i++) {
                        strArr[i] = (String) deviceIdleController.mPowerSaveWhitelistAppsExceptIdle.keyAt(i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return (String[]) ArrayUtils.filter(strArr, new DeviceIdleController$$ExternalSyntheticLambda14(0), new DeviceIdleController$$ExternalSyntheticLambda15(deviceIdleController, callingUid, callingUserId, 0));
        }

        public final String[] getUserPowerWhitelist() {
            String[] strArr;
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            int callingUid = Binder.getCallingUid();
            int callingUserId = UserHandle.getCallingUserId();
            synchronized (deviceIdleController) {
                try {
                    strArr = new String[deviceIdleController.mPowerSaveWhitelistUserApps.size()];
                    for (int i = 0; i < deviceIdleController.mPowerSaveWhitelistUserApps.size(); i++) {
                        strArr[i] = (String) deviceIdleController.mPowerSaveWhitelistUserApps.keyAt(i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return (String[]) ArrayUtils.filter(strArr, new DeviceIdleController$$ExternalSyntheticLambda14(4), new DeviceIdleController$$ExternalSyntheticLambda15(deviceIdleController, callingUid, callingUserId, 4));
        }

        public final boolean isPowerSaveWhitelistApp(String str) {
            boolean z = true;
            if (DeviceIdleController.this.mPackageManagerInternal.filterAppAccess(Binder.getCallingUid(), UserHandle.getCallingUserId(), str, true)) {
                return false;
            }
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            synchronized (deviceIdleController) {
                try {
                    if (!deviceIdleController.mPowerSaveWhitelistApps.containsKey(str) && !deviceIdleController.mPowerSaveWhitelistUserApps.containsKey(str)) {
                        z = false;
                    }
                } finally {
                }
            }
            return z;
        }

        public final boolean isPowerSaveWhitelistExceptIdleApp(String str) {
            boolean z = true;
            if (DeviceIdleController.this.mPackageManagerInternal.filterAppAccess(Binder.getCallingUid(), UserHandle.getCallingUserId(), str, true)) {
                return false;
            }
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            synchronized (deviceIdleController) {
                try {
                    if (!deviceIdleController.mPowerSaveWhitelistAppsExceptIdle.containsKey(str) && !deviceIdleController.mPowerSaveWhitelistUserApps.containsKey(str)) {
                        z = false;
                    }
                } finally {
                }
            }
            return z;
        }

        public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            DeviceIdleController.this.new Shell().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public final void removePowerSaveWhitelistApp(String str) {
            BootReceiver$$ExternalSyntheticOutline0.m58m("removePowerSaveWhitelistApp(name = ", str, ")", "DeviceIdleController");
            DeviceIdleController.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            TargetPkg targetPkg = new TargetPkg(DeviceIdleController.this, Binder.getCallingPid());
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!DeviceIdleController.this.removePowerSaveWhitelistAppInternal(str, 3, targetPkg) && DeviceIdleController.this.mPowerSaveWhitelistAppsExceptIdle.containsKey(str)) {
                    throw new UnsupportedOperationException("Cannot remove system whitelisted app");
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void removeSystemPowerWhitelistApp(String str) {
            if (DeviceIdleController.DEBUG) {
                DeviceIdleController$$ExternalSyntheticOutline0.m("removeAppFromSystemWhitelist(name = ", str, ")", "DeviceIdleController");
            }
            DeviceIdleController.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            TargetPkg targetPkg = new TargetPkg(DeviceIdleController.this, Binder.getCallingPid());
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DeviceIdleController.this.removeSystemPowerWhitelistAppInternal(str, 8, targetPkg);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void restoreSystemPowerWhitelistApp(String str) {
            if (DeviceIdleController.DEBUG) {
                DeviceIdleController$$ExternalSyntheticOutline0.m("restoreAppToSystemWhitelist(name = ", str, ")", "DeviceIdleController");
            }
            DeviceIdleController.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            TargetPkg targetPkg = new TargetPkg(DeviceIdleController.this, Binder.getCallingPid());
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DeviceIdleController.this.restoreSystemPowerWhitelistAppInternal(str, 6, targetPkg);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final long whitelistAppTemporarily(String str, int i, int i2, String str2) {
            long max = Math.max(10000L, DeviceIdleController.this.mConstants.MAX_TEMP_APP_ALLOWLIST_DURATION_MS / 2);
            DeviceIdleController.this.addPowerSaveTempAllowlistAppChecked(i, i2, str, str2, max);
            return max;
        }
    }

    public final class Constants extends ContentObserver implements DeviceConfig.OnPropertiesChangedListener {
        public long DNA_REQUEST_VERSION;
        public long FLEX_TIME_SHORT;
        public long IDLE_AFTER_INACTIVE_TIMEOUT;
        public float IDLE_FACTOR;
        public float IDLE_PENDING_FACTOR;
        public long IDLE_PENDING_TIMEOUT;
        public long IDLE_TIMEOUT;
        public long INACTIVE_TIMEOUT;
        public long LIGHT_IDLE_AFTER_INACTIVE_TIMEOUT;
        public float LIGHT_IDLE_FACTOR;
        public long LIGHT_IDLE_FLEX_LINEAR_INCREASE_FACTOR_MS;
        public boolean LIGHT_IDLE_INCREASE_LINEARLY;
        public long LIGHT_IDLE_LINEAR_INCREASE_FACTOR_MS;
        public long LIGHT_IDLE_MAINTENANCE_MAX_BUDGET;
        public long LIGHT_IDLE_MAINTENANCE_MIN_BUDGET;
        public long LIGHT_IDLE_TIMEOUT;
        public long LIGHT_IDLE_TIMEOUT_INITIAL_FLEX;
        public long LIGHT_IDLE_TIMEOUT_MAX_FLEX;
        public long LIGHT_MAX_IDLE_TIMEOUT;
        public long LOCATING_TIMEOUT;
        public float LOCATION_ACCURACY;
        public long MAX_IDLE_PENDING_TIMEOUT;
        public long MAX_IDLE_TIMEOUT;
        public long MAX_TEMP_APP_ALLOWLIST_DURATION_MS;
        public long MIN_DEEP_MAINTENANCE_TIME;
        public long MIN_LIGHT_MAINTENANCE_TIME;
        public long MIN_TIME_TO_ALARM;
        public long MMS_TEMP_APP_ALLOWLIST_DURATION_MS;
        public long MOTION_INACTIVE_TIMEOUT;
        public long MOTION_INACTIVE_TIMEOUT_FLEX;
        public long NOTIFICATION_ALLOWLIST_DURATION_MS;
        public long QUICK_DOZE_DELAY_TIMEOUT;
        public long SENSING_TIMEOUT;
        public boolean SKIP_LOCATING;
        public boolean SKIP_SENSING;
        public long SMS_TEMP_APP_ALLOWLIST_DURATION_MS;
        public boolean USE_MODE_MANAGER;
        public boolean USE_WINDOW_ALARMS;
        public boolean WAIT_FOR_UNLOCK;
        public final long mDefaultDnaRequestVersion;
        public final long mDefaultFlexTimeShort;
        public final long mDefaultIdleAfterInactiveTimeout;
        public final float mDefaultIdleFactor;
        public final float mDefaultIdlePendingFactor;
        public final long mDefaultIdlePendingTimeout;
        public final long mDefaultIdleTimeout;
        public final long mDefaultInactiveTimeout;
        public final long mDefaultLightIdleAfterInactiveTimeout;
        public final float mDefaultLightIdleFactor;
        public final long mDefaultLightIdleFlexLinearIncreaseFactorMs;
        public final boolean mDefaultLightIdleIncreaseLinearly;
        public final long mDefaultLightIdleLinearIncreaseFactorMs;
        public final long mDefaultLightIdleMaintenanceMaxBudget;
        public final long mDefaultLightIdleMaintenanceMinBudget;
        public final long mDefaultLightIdleTimeout;
        public final long mDefaultLightIdleTimeoutInitialFlex;
        public final long mDefaultLightIdleTimeoutMaxFlex;
        public final long mDefaultLightMaxIdleTimeout;
        public final long mDefaultLocatingTimeout;
        public final float mDefaultLocationAccuracy;
        public final long mDefaultMaxIdlePendingTimeout;
        public final long mDefaultMaxIdleTimeout;
        public final long mDefaultMaxTempAppAllowlistDurationMs;
        public final long mDefaultMinDeepMaintenanceTime;
        public final long mDefaultMinLightMaintenanceTime;
        public final long mDefaultMinTimeToAlarm;
        public final long mDefaultMmsTempAppAllowlistDurationMs;
        public final long mDefaultMotionInactiveTimeout;
        public final long mDefaultMotionInactiveTimeoutFlex;
        public final long mDefaultNotificationAllowlistDurationMs;
        public final long mDefaultQuickDozeDelayTimeout;
        public final long mDefaultSensingTimeout;
        public final boolean mDefaultSkipLocating;
        public final boolean mDefaultSkipSensing;
        public final long mDefaultSmsTempAppAllowlistDurationMs;
        public final boolean mDefaultUseModeManager;
        public final boolean mDefaultUseWindowAlarms;
        public final boolean mDefaultWaitForUnlock;
        public final ContentResolver mResolver;
        public final boolean mSmallBatteryDevice;
        public final UserSettingDeviceConfigMediator$SettingsOverridesIndividualMediator mUserSettingDeviceConfigMediator;

        public Constants(Handler handler, ContentResolver contentResolver) {
            super(handler);
            this.mDefaultFlexTimeShort = 60000L;
            this.mDefaultLightIdleAfterInactiveTimeout = 240000L;
            this.mDefaultLightIdleTimeout = 300000L;
            this.mDefaultLightIdleTimeoutInitialFlex = 60000L;
            this.mDefaultLightIdleTimeoutMaxFlex = 900000L;
            this.mDefaultLightIdleFactor = 2.0f;
            this.mDefaultLightIdleLinearIncreaseFactorMs = 300000L;
            this.mDefaultLightIdleFlexLinearIncreaseFactorMs = 60000L;
            this.mDefaultLightMaxIdleTimeout = 900000L;
            this.mDefaultLightIdleMaintenanceMinBudget = 60000L;
            this.mDefaultLightIdleMaintenanceMaxBudget = 300000L;
            this.mDefaultMinLightMaintenanceTime = 5000L;
            this.mDefaultMinDeepMaintenanceTime = 30000L;
            this.mDefaultInactiveTimeout = 1800000L;
            this.mDefaultSensingTimeout = 240000L;
            this.mDefaultLocatingTimeout = 30000L;
            this.mDefaultLocationAccuracy = 20.0f;
            this.mDefaultMotionInactiveTimeout = 600000L;
            this.mDefaultMotionInactiveTimeoutFlex = 60000L;
            this.mDefaultIdleAfterInactiveTimeout = 1800000L;
            this.mDefaultIdlePendingTimeout = 300000L;
            this.mDefaultMaxIdlePendingTimeout = 600000L;
            this.mDefaultIdlePendingFactor = 2.0f;
            this.mDefaultQuickDozeDelayTimeout = 60000L;
            this.mDefaultIdleTimeout = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
            this.mDefaultMaxIdleTimeout = 21600000L;
            this.mDefaultIdleFactor = 2.0f;
            this.mDefaultMinTimeToAlarm = 1800000L;
            this.mDefaultMaxTempAppAllowlistDurationMs = 300000L;
            this.mDefaultMmsTempAppAllowlistDurationMs = 60000L;
            this.mDefaultSmsTempAppAllowlistDurationMs = 20000L;
            this.mDefaultNotificationAllowlistDurationMs = 30000L;
            this.mDefaultWaitForUnlock = true;
            this.mDefaultUseWindowAlarms = true;
            this.mDefaultUseModeManager = false;
            this.mDefaultSkipSensing = false;
            this.mDefaultSkipLocating = false;
            this.mDefaultDnaRequestVersion = 0L;
            this.FLEX_TIME_SHORT = 60000L;
            this.LIGHT_IDLE_AFTER_INACTIVE_TIMEOUT = 240000L;
            this.LIGHT_IDLE_TIMEOUT = 300000L;
            this.LIGHT_IDLE_TIMEOUT_INITIAL_FLEX = 60000L;
            this.LIGHT_IDLE_TIMEOUT_MAX_FLEX = 900000L;
            this.LIGHT_IDLE_FACTOR = 2.0f;
            this.LIGHT_IDLE_INCREASE_LINEARLY = this.mDefaultLightIdleIncreaseLinearly;
            this.LIGHT_IDLE_LINEAR_INCREASE_FACTOR_MS = 300000L;
            this.LIGHT_IDLE_FLEX_LINEAR_INCREASE_FACTOR_MS = 60000L;
            this.LIGHT_MAX_IDLE_TIMEOUT = 900000L;
            this.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET = 60000L;
            this.LIGHT_IDLE_MAINTENANCE_MAX_BUDGET = 300000L;
            this.MIN_LIGHT_MAINTENANCE_TIME = 5000L;
            this.MIN_DEEP_MAINTENANCE_TIME = 30000L;
            this.INACTIVE_TIMEOUT = 1800000L;
            this.SENSING_TIMEOUT = 240000L;
            this.LOCATING_TIMEOUT = 30000L;
            this.LOCATION_ACCURACY = 20.0f;
            this.MOTION_INACTIVE_TIMEOUT = 600000L;
            this.MOTION_INACTIVE_TIMEOUT_FLEX = 60000L;
            this.IDLE_AFTER_INACTIVE_TIMEOUT = 1800000L;
            this.IDLE_PENDING_TIMEOUT = 300000L;
            this.MAX_IDLE_PENDING_TIMEOUT = 600000L;
            this.IDLE_PENDING_FACTOR = 2.0f;
            this.QUICK_DOZE_DELAY_TIMEOUT = 60000L;
            this.IDLE_TIMEOUT = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
            this.MAX_IDLE_TIMEOUT = 21600000L;
            this.IDLE_FACTOR = 2.0f;
            this.MIN_TIME_TO_ALARM = 1800000L;
            this.MAX_TEMP_APP_ALLOWLIST_DURATION_MS = 300000L;
            this.MMS_TEMP_APP_ALLOWLIST_DURATION_MS = 60000L;
            this.SMS_TEMP_APP_ALLOWLIST_DURATION_MS = 20000L;
            this.NOTIFICATION_ALLOWLIST_DURATION_MS = 30000L;
            this.WAIT_FOR_UNLOCK = true;
            this.USE_WINDOW_ALARMS = true;
            this.USE_MODE_MANAGER = false;
            this.SKIP_SENSING = false;
            this.SKIP_LOCATING = false;
            this.DNA_REQUEST_VERSION = 0L;
            UserSettingDeviceConfigMediator$SettingsOverridesIndividualMediator userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator = new UserSettingDeviceConfigMediator$SettingsOverridesIndividualMediator();
            this.mUserSettingDeviceConfigMediator = userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator;
            this.mResolver = contentResolver;
            Resources resources = DeviceIdleController.this.getContext().getResources();
            this.mDefaultFlexTimeShort = resources.getInteger(R.integer.preference_screen_header_scrollbarStyle);
            this.mDefaultLightIdleAfterInactiveTimeout = resources.getInteger(17695119);
            this.mDefaultLightIdleTimeout = resources.getInteger(17695127);
            this.mDefaultLightIdleTimeoutInitialFlex = resources.getInteger(17695125);
            this.mDefaultLightIdleTimeoutMaxFlex = resources.getInteger(17695126);
            this.mDefaultLightIdleFactor = resources.getFloat(17695120);
            this.mDefaultLightIdleIncreaseLinearly = resources.getBoolean(R.bool.skip_restoring_network_selection);
            this.mDefaultLightIdleLinearIncreaseFactorMs = resources.getInteger(17695122);
            this.mDefaultLightIdleFlexLinearIncreaseFactorMs = resources.getInteger(17695121);
            this.mDefaultLightMaxIdleTimeout = resources.getInteger(17695128);
            this.mDefaultLightIdleMaintenanceMinBudget = resources.getInteger(17695124);
            this.mDefaultLightIdleMaintenanceMaxBudget = resources.getInteger(17695123);
            this.mDefaultMinLightMaintenanceTime = resources.getInteger(17695135);
            this.mDefaultMinDeepMaintenanceTime = resources.getInteger(17695134);
            this.mDefaultInactiveTimeout = resources.getInteger(R.integer.timepicker_title_visibility);
            this.mDefaultSensingTimeout = resources.getInteger(17695142);
            this.mDefaultLocatingTimeout = resources.getInteger(17695129);
            this.mDefaultLocationAccuracy = resources.getFloat(17695130);
            this.mDefaultMotionInactiveTimeout = resources.getInteger(17695139);
            this.mDefaultMotionInactiveTimeoutFlex = resources.getInteger(17695138);
            this.mDefaultIdleAfterInactiveTimeout = resources.getInteger(R.integer.preferences_left_pane_weight);
            this.mDefaultIdlePendingTimeout = resources.getInteger(R.integer.time_picker_mode);
            this.mDefaultMaxIdlePendingTimeout = resources.getInteger(17695131);
            this.mDefaultIdlePendingFactor = resources.getFloat(R.integer.thumbnail_width_tv);
            this.mDefaultQuickDozeDelayTimeout = resources.getInteger(17695141);
            this.mDefaultIdleTimeout = resources.getInteger(R.integer.time_picker_mode_material);
            this.mDefaultMaxIdleTimeout = resources.getInteger(17695132);
            this.mDefaultIdleFactor = resources.getFloat(R.integer.preferences_right_pane_weight);
            this.mDefaultMinTimeToAlarm = resources.getInteger(17695136);
            this.mDefaultMaxTempAppAllowlistDurationMs = resources.getInteger(17695133);
            this.mDefaultMmsTempAppAllowlistDurationMs = resources.getInteger(17695137);
            this.mDefaultSmsTempAppAllowlistDurationMs = resources.getInteger(17695143);
            this.mDefaultNotificationAllowlistDurationMs = resources.getInteger(17695140);
            this.mDefaultWaitForUnlock = resources.getBoolean(17892003);
            this.mDefaultUseWindowAlarms = resources.getBoolean(17892002);
            this.mDefaultUseModeManager = resources.getBoolean(17892001);
            this.mDefaultSkipSensing = resources.getBoolean(R.bool.use_lock_pattern_drawable);
            this.mDefaultSkipLocating = resources.getBoolean(R.bool.split_action_bar_is_narrow);
            long integer = resources.getInteger(R.integer.preference_fragment_scrollbarStyle);
            this.mDefaultDnaRequestVersion = integer;
            this.FLEX_TIME_SHORT = this.mDefaultFlexTimeShort;
            this.LIGHT_IDLE_AFTER_INACTIVE_TIMEOUT = this.mDefaultLightIdleAfterInactiveTimeout;
            this.LIGHT_IDLE_TIMEOUT = this.mDefaultLightIdleTimeout;
            this.LIGHT_IDLE_TIMEOUT_INITIAL_FLEX = this.mDefaultLightIdleTimeoutInitialFlex;
            this.LIGHT_IDLE_TIMEOUT_MAX_FLEX = this.mDefaultLightIdleTimeoutMaxFlex;
            this.LIGHT_IDLE_FACTOR = this.mDefaultLightIdleFactor;
            this.LIGHT_IDLE_INCREASE_LINEARLY = this.mDefaultLightIdleIncreaseLinearly;
            this.LIGHT_IDLE_LINEAR_INCREASE_FACTOR_MS = this.mDefaultLightIdleLinearIncreaseFactorMs;
            this.LIGHT_IDLE_FLEX_LINEAR_INCREASE_FACTOR_MS = this.mDefaultLightIdleFlexLinearIncreaseFactorMs;
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
            this.USE_WINDOW_ALARMS = this.mDefaultUseWindowAlarms;
            this.USE_MODE_MANAGER = this.mDefaultUseModeManager;
            this.SKIP_SENSING = this.mDefaultSkipSensing;
            this.SKIP_LOCATING = this.mDefaultSkipLocating;
            this.DNA_REQUEST_VERSION = integer;
            boolean isSmallBatteryDevice = ActivityManager.isSmallBatteryDevice();
            this.mSmallBatteryDevice = isSmallBatteryDevice;
            if (isSmallBatteryDevice) {
                this.INACTIVE_TIMEOUT = 60000L;
                this.IDLE_AFTER_INACTIVE_TIMEOUT = 60000L;
            }
            DeviceConfig.addOnPropertiesChangedListener("device_idle", AppSchedulingModuleThread.getExecutor(), this);
            contentResolver.registerContentObserver(Settings.Global.getUriFor("device_idle_constants"), false, this);
            updateSettingsConstantLocked();
            userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.mProperties = DeviceConfig.getProperties("device_idle", new String[0]);
            updateConstantsLocked();
        }

        public final void dump(PrintWriter printWriter) {
            printWriter.println("  Settings:");
            printWriter.print("    ");
            printWriter.print("flex_time_short");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.FLEX_TIME_SHORT, printWriter, "    ", "light_after_inactive_to");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.LIGHT_IDLE_AFTER_INACTIVE_TIMEOUT, printWriter, "    ", "light_idle_to");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.LIGHT_IDLE_TIMEOUT, printWriter, "    ", "light_idle_to_initial_flex");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.LIGHT_IDLE_TIMEOUT_INITIAL_FLEX, printWriter, "    ", "light_max_idle_to_flex");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.LIGHT_IDLE_TIMEOUT_MAX_FLEX, printWriter, "    ", "light_idle_factor");
            printWriter.print("=");
            printWriter.print(this.LIGHT_IDLE_FACTOR);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("light_idle_increase_linearly");
            printWriter.print("=");
            printWriter.print(this.LIGHT_IDLE_INCREASE_LINEARLY);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("light_idle_linear_increase_factor_ms");
            printWriter.print("=");
            printWriter.print(this.LIGHT_IDLE_LINEAR_INCREASE_FACTOR_MS);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("light_idle_flex_linear_increase_factor_ms");
            printWriter.print("=");
            printWriter.print(this.LIGHT_IDLE_FLEX_LINEAR_INCREASE_FACTOR_MS);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("light_max_idle_to");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.LIGHT_MAX_IDLE_TIMEOUT, printWriter, "    ", "light_idle_maintenance_min_budget");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET, printWriter, "    ", "light_idle_maintenance_max_budget");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.LIGHT_IDLE_MAINTENANCE_MAX_BUDGET, printWriter, "    ", "min_light_maintenance_time");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.MIN_LIGHT_MAINTENANCE_TIME, printWriter, "    ", "min_deep_maintenance_time");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.MIN_DEEP_MAINTENANCE_TIME, printWriter, "    ", "inactive_to");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.INACTIVE_TIMEOUT, printWriter, "    ", "sensing_to");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.SENSING_TIMEOUT, printWriter, "    ", "locating_to");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.LOCATING_TIMEOUT, printWriter, "    ", "location_accuracy");
            printWriter.print("=");
            printWriter.print(this.LOCATION_ACCURACY);
            printWriter.print("m");
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("motion_inactive_to");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.MOTION_INACTIVE_TIMEOUT, printWriter, "    ", "motion_inactive_to_flex");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.MOTION_INACTIVE_TIMEOUT_FLEX, printWriter, "    ", "idle_after_inactive_to");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.IDLE_AFTER_INACTIVE_TIMEOUT, printWriter, "    ", "idle_pending_to");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.IDLE_PENDING_TIMEOUT, printWriter, "    ", "max_idle_pending_to");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.MAX_IDLE_PENDING_TIMEOUT, printWriter, "    ", "idle_pending_factor");
            printWriter.print("=");
            printWriter.println(this.IDLE_PENDING_FACTOR);
            printWriter.print("    ");
            printWriter.print("quick_doze_delay_to");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.QUICK_DOZE_DELAY_TIMEOUT, printWriter, "    ", "idle_to");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.IDLE_TIMEOUT, printWriter, "    ", "max_idle_to");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.MAX_IDLE_TIMEOUT, printWriter, "    ", "idle_factor");
            printWriter.print("=");
            printWriter.println(this.IDLE_FACTOR);
            printWriter.print("    ");
            printWriter.print("min_time_to_alarm");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.MIN_TIME_TO_ALARM, printWriter, "    ", "max_temp_app_allowlist_duration_ms");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.MAX_TEMP_APP_ALLOWLIST_DURATION_MS, printWriter, "    ", "mms_temp_app_allowlist_duration_ms");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.MMS_TEMP_APP_ALLOWLIST_DURATION_MS, printWriter, "    ", "sms_temp_app_allowlist_duration_ms");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.SMS_TEMP_APP_ALLOWLIST_DURATION_MS, printWriter, "    ", "notification_allowlist_duration_ms");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.NOTIFICATION_ALLOWLIST_DURATION_MS, printWriter, "    ", "wait_for_unlock");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(printWriter, this.WAIT_FOR_UNLOCK, "    ", "use_window_alarms", "=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(printWriter, this.USE_WINDOW_ALARMS, "    ", "use_mode_manager", "=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(printWriter, this.USE_MODE_MANAGER, "    ", "skip_sensing", "=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(printWriter, this.SKIP_SENSING, "    ", "skip_locating", "=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(printWriter, this.SKIP_LOCATING, "    ", "dna_request_version", "=");
            printWriter.println(this.DNA_REQUEST_VERSION);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            synchronized (DeviceIdleController.this) {
                updateSettingsConstantLocked();
                updateConstantsLocked();
            }
        }

        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            synchronized (DeviceIdleController.this) {
                this.mUserSettingDeviceConfigMediator.mProperties = properties;
                updateConstantsLocked();
            }
        }

        public final void updateConstantsLocked() {
            if (this.mSmallBatteryDevice) {
                return;
            }
            this.FLEX_TIME_SHORT = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultFlexTimeShort, "flex_time_short");
            this.LIGHT_IDLE_AFTER_INACTIVE_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultLightIdleAfterInactiveTimeout, "light_after_inactive_to");
            this.LIGHT_IDLE_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultLightIdleTimeout, "light_idle_to");
            this.LIGHT_IDLE_TIMEOUT_INITIAL_FLEX = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultLightIdleTimeoutInitialFlex, "light_idle_to_initial_flex");
            this.LIGHT_IDLE_TIMEOUT_MAX_FLEX = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultLightIdleTimeoutMaxFlex, "light_max_idle_to_flex");
            this.LIGHT_IDLE_FACTOR = Math.max(1.0f, this.mUserSettingDeviceConfigMediator.getFloat(this.mDefaultLightIdleFactor, "light_idle_factor"));
            this.LIGHT_IDLE_INCREASE_LINEARLY = this.mUserSettingDeviceConfigMediator.getBoolean("light_idle_increase_linearly", this.mDefaultLightIdleIncreaseLinearly);
            this.LIGHT_IDLE_LINEAR_INCREASE_FACTOR_MS = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultLightIdleLinearIncreaseFactorMs, "light_idle_linear_increase_factor_ms");
            this.LIGHT_IDLE_FLEX_LINEAR_INCREASE_FACTOR_MS = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultLightIdleFlexLinearIncreaseFactorMs, "light_idle_flex_linear_increase_factor_ms");
            this.LIGHT_MAX_IDLE_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultLightMaxIdleTimeout, "light_max_idle_to");
            this.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultLightIdleMaintenanceMinBudget, "light_idle_maintenance_min_budget");
            this.LIGHT_IDLE_MAINTENANCE_MAX_BUDGET = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultLightIdleMaintenanceMaxBudget, "light_idle_maintenance_max_budget");
            this.MIN_LIGHT_MAINTENANCE_TIME = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultMinLightMaintenanceTime, "min_light_maintenance_time");
            this.MIN_DEEP_MAINTENANCE_TIME = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultMinDeepMaintenanceTime, "min_deep_maintenance_time");
            this.INACTIVE_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(this.mSmallBatteryDevice ? 60000L : this.mDefaultInactiveTimeout, "inactive_to");
            this.SENSING_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultSensingTimeout, "sensing_to");
            this.LOCATING_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultLocatingTimeout, "locating_to");
            this.LOCATION_ACCURACY = this.mUserSettingDeviceConfigMediator.getFloat(this.mDefaultLocationAccuracy, "location_accuracy");
            this.MOTION_INACTIVE_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultMotionInactiveTimeout, "motion_inactive_to");
            this.MOTION_INACTIVE_TIMEOUT_FLEX = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultMotionInactiveTimeoutFlex, "motion_inactive_to_flex");
            this.IDLE_AFTER_INACTIVE_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(this.mSmallBatteryDevice ? 60000L : this.mDefaultIdleAfterInactiveTimeout, "idle_after_inactive_to");
            this.IDLE_PENDING_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultIdlePendingTimeout, "idle_pending_to");
            this.MAX_IDLE_PENDING_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultMaxIdlePendingTimeout, "max_idle_pending_to");
            this.IDLE_PENDING_FACTOR = this.mUserSettingDeviceConfigMediator.getFloat(this.mDefaultIdlePendingFactor, "idle_pending_factor");
            this.QUICK_DOZE_DELAY_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultQuickDozeDelayTimeout, "quick_doze_delay_to");
            this.IDLE_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultIdleTimeout, "idle_to");
            this.MAX_IDLE_TIMEOUT = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultMaxIdleTimeout, "max_idle_to");
            this.IDLE_FACTOR = this.mUserSettingDeviceConfigMediator.getFloat(this.mDefaultIdleFactor, "idle_factor");
            this.MIN_TIME_TO_ALARM = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultMinTimeToAlarm, "min_time_to_alarm");
            this.MAX_TEMP_APP_ALLOWLIST_DURATION_MS = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultMaxTempAppAllowlistDurationMs, "max_temp_app_allowlist_duration_ms");
            this.MMS_TEMP_APP_ALLOWLIST_DURATION_MS = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultMmsTempAppAllowlistDurationMs, "mms_temp_app_allowlist_duration_ms");
            this.SMS_TEMP_APP_ALLOWLIST_DURATION_MS = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultSmsTempAppAllowlistDurationMs, "sms_temp_app_allowlist_duration_ms");
            this.NOTIFICATION_ALLOWLIST_DURATION_MS = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultNotificationAllowlistDurationMs, "notification_allowlist_duration_ms");
            this.WAIT_FOR_UNLOCK = this.mUserSettingDeviceConfigMediator.getBoolean("wait_for_unlock", this.mDefaultWaitForUnlock);
            this.USE_WINDOW_ALARMS = this.mUserSettingDeviceConfigMediator.getBoolean("use_window_alarms", this.mDefaultUseWindowAlarms);
            this.USE_MODE_MANAGER = this.mUserSettingDeviceConfigMediator.getBoolean("use_mode_manager", this.mDefaultUseModeManager);
            this.SKIP_SENSING = this.mUserSettingDeviceConfigMediator.getBoolean("skip_sensing", this.mDefaultSkipSensing);
            this.SKIP_LOCATING = this.mUserSettingDeviceConfigMediator.getBoolean("skip_locating", this.mDefaultSkipLocating);
            this.DNA_REQUEST_VERSION = this.mUserSettingDeviceConfigMediator.getLong(this.mDefaultDnaRequestVersion, "dna_request_version");
        }

        public final void updateSettingsConstantLocked() {
            try {
                UserSettingDeviceConfigMediator$SettingsOverridesIndividualMediator userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator = this.mUserSettingDeviceConfigMediator;
                userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.mSettingsParser.setString(Settings.Global.getString(this.mResolver, "device_idle_constants"));
            } catch (IllegalArgumentException e) {
                Slog.e("DeviceIdleController", "Bad device idle settings", e);
            }
        }
    }

    public final class EmergencyCallListener extends TelephonyCallback implements TelephonyCallback.OutgoingEmergencyCallListener, TelephonyCallback.CallStateListener {
        public volatile boolean mIsEmergencyCallActive;

        public EmergencyCallListener() {
        }

        @Override // android.telephony.TelephonyCallback.CallStateListener
        public final void onCallStateChanged(int i) {
            if (DeviceIdleController.DEBUG) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "onCallStateChanged(): state is ", "DeviceIdleController");
            }
            if (i == 0 && this.mIsEmergencyCallActive) {
                this.mIsEmergencyCallActive = false;
                synchronized (DeviceIdleController.this) {
                    DeviceIdleController.this.becomeInactiveIfAppropriateLocked();
                }
            }
        }

        public final void onOutgoingEmergencyCall(EmergencyNumber emergencyNumber, int i) {
            this.mIsEmergencyCallActive = true;
            if (DeviceIdleController.DEBUG) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "onOutgoingEmergencyCall(): subId = ", "DeviceIdleController");
            }
            synchronized (DeviceIdleController.this) {
                DeviceIdleController.this.getClass();
                DeviceIdleController.this.becomeActiveLocked(Process.myUid(), "emergency call");
            }
        }
    }

    public final class Injector {
        public ConnectivityManager mConnectivityManager;
        public Constants mConstants;
        public final Context mContext;
        public LocationManager mLocationManager;

        public Injector(Context context) {
            this.mContext = context.createAttributionContext("DeviceIdleController");
        }
    }

    public final class LocalPowerAllowlistService implements PowerAllowlistInternal {
        public LocalPowerAllowlistService() {
        }

        public final void registerTempAllowlistChangeListener(PowerAllowlistInternal.TempAllowlistChangeListener tempAllowlistChangeListener) {
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            synchronized (deviceIdleController) {
                deviceIdleController.mTempAllowlistChangeListeners.add(tempAllowlistChangeListener);
            }
        }

        public final void unregisterTempAllowlistChangeListener(PowerAllowlistInternal.TempAllowlistChangeListener tempAllowlistChangeListener) {
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            synchronized (deviceIdleController) {
                deviceIdleController.mTempAllowlistChangeListeners.remove(tempAllowlistChangeListener);
            }
        }
    }

    public final class LocalService implements DeviceIdleInternal {
        public LocalService() {
        }

        public final void addPowerSaveTempWhitelistApp(int i, String str, long j, int i2, int i3, boolean z, int i4, String str2) {
            DeviceIdleController.this.addPowerSaveTempAllowlistAppInternal(i, str, j, i2, i3, z, i4, str2);
        }

        public final void addPowerSaveTempWhitelistApp(int i, String str, long j, int i2, boolean z, int i3, String str2) {
            DeviceIdleController.this.addPowerSaveTempAllowlistAppInternal(i, str, j, 0, i2, z, i3, str2);
        }

        public final void addPowerSaveTempWhitelistAppDirect(int i, long j, int i2, boolean z, int i3, String str, int i4) {
            DeviceIdleController.this.addPowerSaveTempWhitelistAppDirectInternal(i4, j, i, z, i2, str, i3);
        }

        public final void exitIdle(String str) {
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            synchronized (deviceIdleController) {
                deviceIdleController.becomeActiveLocked(Binder.getCallingUid(), str);
            }
        }

        public final String[] getFullPowerWhitelistExceptIdle() {
            return DeviceIdleController.this.getFullPowerWhitelistInternalUnchecked();
        }

        public final long getNotificationAllowlistDuration() {
            return DeviceIdleController.this.mConstants.NOTIFICATION_ALLOWLIST_DURATION_MS;
        }

        public final int[] getPowerSaveTempWhitelistAppIds() {
            int[] iArr;
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            synchronized (deviceIdleController) {
                iArr = deviceIdleController.mTempWhitelistAppIdArray;
            }
            return iArr;
        }

        public final int[] getPowerSaveWhitelistUserAppIds() {
            int[] iArr;
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            synchronized (deviceIdleController) {
                iArr = deviceIdleController.mPowerSaveWhitelistUserAppIdArray;
            }
            return iArr;
        }

        public final int getTempAllowListType(int i, int i2) {
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            deviceIdleController.getClass();
            if (i != -1) {
                return i != 102 ? i2 : deviceIdleController.mLocalActivityManager.getPushMessagingOverQuotaBehavior();
            }
            return -1;
        }

        public final boolean isAppOnWhitelist(int i) {
            boolean z;
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            synchronized (deviceIdleController) {
                z = Arrays.binarySearch(deviceIdleController.mPowerSaveWhitelistAllAppIdArray, i) >= 0;
            }
            return z;
        }

        public final void onConstraintStateChanged(IDeviceIdleConstraint iDeviceIdleConstraint, boolean z) {
            synchronized (DeviceIdleController.this) {
                DeviceIdleController.this.onConstraintStateChangedLocked(iDeviceIdleConstraint, z);
            }
        }

        public final void registerDeviceIdleConstraint(IDeviceIdleConstraint iDeviceIdleConstraint, String str, int i) {
            int i2;
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            deviceIdleController.getClass();
            if (i == 0) {
                i2 = 0;
            } else {
                if (i != 1) {
                    Slog.wtf("DeviceIdleController", "Registering device-idle constraint with invalid type: " + i);
                    return;
                }
                i2 = 3;
            }
            synchronized (deviceIdleController) {
                try {
                    if (deviceIdleController.mConstraints.containsKey(iDeviceIdleConstraint)) {
                        Slog.e("DeviceIdleController", "Re-registering device-idle constraint: " + iDeviceIdleConstraint + ".");
                    } else {
                        deviceIdleController.mConstraints.put(iDeviceIdleConstraint, new DeviceIdleConstraintTracker(str, i2));
                        deviceIdleController.updateActiveConstraintsLocked();
                    }
                } finally {
                }
            }
        }

        public final void registerStationaryListener(DeviceIdleInternal.StationaryListener stationaryListener) {
            DeviceIdleController.this.registerStationaryListener(stationaryListener);
        }

        public final void setAlarmsActive(boolean z) {
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            synchronized (deviceIdleController) {
                try {
                    deviceIdleController.mAlarmsActive = z;
                    if (!z) {
                        deviceIdleController.exitMaintenanceEarlyIfNeededLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setJobsActive(boolean z) {
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            synchronized (deviceIdleController) {
                try {
                    deviceIdleController.mJobsActive = z;
                    if (!z) {
                        deviceIdleController.exitMaintenanceEarlyIfNeededLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void unregisterDeviceIdleConstraint(IDeviceIdleConstraint iDeviceIdleConstraint) {
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            synchronized (deviceIdleController) {
                deviceIdleController.onConstraintStateChangedLocked(iDeviceIdleConstraint, false);
                deviceIdleController.setConstraintMonitoringLocked(iDeviceIdleConstraint, false);
                deviceIdleController.mConstraints.remove(iDeviceIdleConstraint);
            }
        }

        public final void unregisterStationaryListener(DeviceIdleInternal.StationaryListener stationaryListener) {
            DeviceIdleController deviceIdleController = DeviceIdleController.this;
            synchronized (deviceIdleController) {
                try {
                    if (deviceIdleController.mStationaryListeners.remove(stationaryListener)) {
                        if (deviceIdleController.mStationaryListeners.size() == 0) {
                            int i = deviceIdleController.mState;
                            if (i != 0) {
                                if (i != 1) {
                                    if (deviceIdleController.mQuickDozeActivated) {
                                    }
                                }
                            }
                            deviceIdleController.maybeStopMonitoringMotionLocked();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    class ModeManagerOffBodyStateConsumer implements Consumer {
        /* renamed from: -$$Nest$monModeManagerOffBodyChangedLocked, reason: not valid java name */
        public static void m61$$Nest$monModeManagerOffBodyChangedLocked(ModeManagerOffBodyStateConsumer modeManagerOffBodyStateConsumer) {
            DeviceIdleController.m60$$Nest$mmaybeBecomeActiveOnModeManagerEventsLocked(DeviceIdleController.this);
        }

        public ModeManagerOffBodyStateConsumer() {
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            Boolean bool = (Boolean) obj;
            Slog.i("DeviceIdleController", "Offbody event from mode manager: " + bool);
            synchronized (DeviceIdleController.this) {
                try {
                    DeviceIdleController deviceIdleController = DeviceIdleController.this;
                    if (!deviceIdleController.mForceModeManagerOffBodyState && deviceIdleController.mIsOffBody != bool.booleanValue()) {
                        DeviceIdleController.this.mIsOffBody = bool.booleanValue();
                        DeviceIdleController.m60$$Nest$mmaybeBecomeActiveOnModeManagerEventsLocked(DeviceIdleController.this);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    class ModeManagerQuickDozeRequestConsumer implements Consumer {
        /* renamed from: -$$Nest$monModeManagerRequestChangedLocked, reason: not valid java name */
        public static void m62$$Nest$monModeManagerRequestChangedLocked(ModeManagerQuickDozeRequestConsumer modeManagerQuickDozeRequestConsumer) {
            DeviceIdleController.m60$$Nest$mmaybeBecomeActiveOnModeManagerEventsLocked(DeviceIdleController.this);
            DeviceIdleController.this.updateQuickDozeFlagLocked();
        }

        public ModeManagerQuickDozeRequestConsumer() {
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            Boolean bool = (Boolean) obj;
            Slog.i("DeviceIdleController", "Mode manager quick doze request: " + bool);
            synchronized (DeviceIdleController.this) {
                try {
                    DeviceIdleController deviceIdleController = DeviceIdleController.this;
                    if (!deviceIdleController.mForceModeManagerQuickDozeRequest && deviceIdleController.mModeManagerRequestedQuickDoze != bool.booleanValue()) {
                        DeviceIdleController.this.mModeManagerRequestedQuickDoze = bool.booleanValue();
                        DeviceIdleController.m60$$Nest$mmaybeBecomeActiveOnModeManagerEventsLocked(DeviceIdleController.this);
                        DeviceIdleController.this.updateQuickDozeFlagLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    final class MotionListener extends TriggerEventListener implements SensorEventListener {
        public long activatedTimeElapsed;
        public boolean active = false;

        public MotionListener() {
        }

        @Override // android.hardware.SensorEventListener
        public final void onAccuracyChanged(Sensor sensor, int i) {
        }

        @Override // android.hardware.SensorEventListener
        public final void onSensorChanged(SensorEvent sensorEvent) {
            synchronized (DeviceIdleController.this) {
                DeviceIdleController deviceIdleController = DeviceIdleController.this;
                deviceIdleController.mSensorManager.unregisterListener(this, deviceIdleController.mMotionSensor);
                this.active = false;
                DeviceIdleController.this.motionLocked();
            }
        }

        @Override // android.hardware.TriggerEventListener
        public final void onTrigger(TriggerEvent triggerEvent) {
            synchronized (DeviceIdleController.this) {
                this.active = false;
                DeviceIdleController.this.motionLocked();
            }
        }
    }

    public final class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean deviceIdleMode;
            boolean lightDeviceIdleMode;
            boolean isStationaryLocked;
            PowerAllowlistInternal.TempAllowlistChangeListener[] tempAllowlistChangeListenerArr;
            boolean z = DeviceIdleController.DEBUG;
            if (z) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("handleMessage("), message.what, ")", "DeviceIdleController");
            }
            int i = 0;
            DeviceIdleInternal.StationaryListener[] stationaryListenerArr = null;
            switch (message.what) {
                case 1:
                    DeviceIdleController.this.handleWriteConfigFile();
                    return;
                case 2:
                case 3:
                    EventLog.writeEvent(34003, new Object[0]);
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
                        Context context = DeviceIdleController.this.getContext();
                        DeviceIdleController deviceIdleController = DeviceIdleController.this;
                        context.sendBroadcastAsUser(deviceIdleController.mIdleIntent, UserHandle.ALL, null, deviceIdleController.mIdleIntentOptions);
                    }
                    if (lightDeviceIdleMode) {
                        Context context2 = DeviceIdleController.this.getContext();
                        DeviceIdleController deviceIdleController2 = DeviceIdleController.this;
                        context2.sendBroadcastAsUser(deviceIdleController2.mLightIdleIntent, UserHandle.ALL, null, deviceIdleController2.mLightIdleIntentOptions);
                    }
                    EventLog.writeEvent(34005, new Object[0]);
                    DeviceIdleController.this.mGoingIdleWakeLock.release();
                    return;
                case 4:
                    EventLog.writeEvent(34006, "unknown");
                    boolean deviceIdleMode2 = DeviceIdleController.this.mLocalPowerManager.setDeviceIdleMode(false);
                    boolean lightDeviceIdleMode2 = DeviceIdleController.this.mLocalPowerManager.setLightDeviceIdleMode(false);
                    try {
                        DeviceIdleController.this.mNetworkPolicyManager.setDeviceIdleMode(false);
                        DeviceIdleController.this.mBatteryStats.noteDeviceIdleMode(0, (String) null, Process.myUid());
                    } catch (RemoteException unused2) {
                    }
                    if (deviceIdleMode2) {
                        DeviceIdleController deviceIdleController3 = DeviceIdleController.this;
                        synchronized (deviceIdleController3) {
                            deviceIdleController3.mActiveIdleOpCount++;
                        }
                        DeviceIdleController deviceIdleController4 = DeviceIdleController.this;
                        deviceIdleController4.mLocalActivityManager.broadcastIntentWithCallback(deviceIdleController4.mIdleIntent, deviceIdleController4.mIdleStartedDoneReceiver, (String[]) null, -1, (int[]) null, (BiFunction) null, deviceIdleController4.mIdleIntentOptions);
                    }
                    if (lightDeviceIdleMode2) {
                        DeviceIdleController deviceIdleController5 = DeviceIdleController.this;
                        synchronized (deviceIdleController5) {
                            deviceIdleController5.mActiveIdleOpCount++;
                        }
                        DeviceIdleController deviceIdleController6 = DeviceIdleController.this;
                        deviceIdleController6.mLocalActivityManager.broadcastIntentWithCallback(deviceIdleController6.mLightIdleIntent, deviceIdleController6.mIdleStartedDoneReceiver, (String[]) null, -1, (int[]) null, (BiFunction) null, deviceIdleController6.mLightIdleIntentOptions);
                    }
                    DeviceIdleController.this.decActiveIdleOps();
                    EventLog.writeEvent(34008, new Object[0]);
                    return;
                case 5:
                    String str = (String) message.obj;
                    int i2 = message.arg1;
                    EventLog.writeEvent(34006, str != null ? str : "unknown");
                    boolean deviceIdleMode3 = DeviceIdleController.this.mLocalPowerManager.setDeviceIdleMode(false);
                    boolean lightDeviceIdleMode3 = DeviceIdleController.this.mLocalPowerManager.setLightDeviceIdleMode(false);
                    try {
                        DeviceIdleController.this.mNetworkPolicyManager.setDeviceIdleMode(false);
                        DeviceIdleController.this.mBatteryStats.noteDeviceIdleMode(0, str, i2);
                    } catch (RemoteException unused3) {
                    }
                    if (deviceIdleMode3) {
                        Context context3 = DeviceIdleController.this.getContext();
                        DeviceIdleController deviceIdleController7 = DeviceIdleController.this;
                        context3.sendBroadcastAsUser(deviceIdleController7.mIdleIntent, UserHandle.ALL, null, deviceIdleController7.mIdleIntentOptions);
                    }
                    if (lightDeviceIdleMode3) {
                        Context context4 = DeviceIdleController.this.getContext();
                        DeviceIdleController deviceIdleController8 = DeviceIdleController.this;
                        context4.sendBroadcastAsUser(deviceIdleController8.mLightIdleIntent, UserHandle.ALL, null, deviceIdleController8.mLightIdleIntentOptions);
                    }
                    EventLog.writeEvent(34008, new Object[0]);
                    return;
                case 6:
                    int i3 = message.arg1;
                    DeviceIdleController deviceIdleController9 = DeviceIdleController.this;
                    deviceIdleController9.getClass();
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    int appId = UserHandle.getAppId(i3);
                    if (z) {
                        Slog.d("DeviceIdleController", "checkTempAppWhitelistTimeout: uid=" + i3 + ", timeNow=" + elapsedRealtime);
                    }
                    synchronized (deviceIdleController9) {
                        try {
                            Pair pair = (Pair) deviceIdleController9.mTempWhitelistAppIdEndTimes.get(appId);
                            if (pair == null) {
                                return;
                            }
                            if (elapsedRealtime >= ((MutableLong) pair.first).value) {
                                deviceIdleController9.mTempWhitelistAppIdEndTimes.delete(appId);
                                deviceIdleController9.onAppRemovedFromTempWhitelistLocked(i3, (String) pair.second);
                            } else {
                                if (z) {
                                    Slog.d("DeviceIdleController", "Time to remove uid " + i3 + ": " + ((MutableLong) pair.first).value);
                                }
                                deviceIdleController9.postTempActiveTimeoutMessage(i3, ((MutableLong) pair.first).value - elapsedRealtime);
                            }
                            return;
                        } finally {
                        }
                    }
                case 7:
                    DeviceIdleInternal.StationaryListener stationaryListener = (DeviceIdleInternal.StationaryListener) message.obj;
                    synchronized (DeviceIdleController.this) {
                        try {
                            isStationaryLocked = DeviceIdleController.this.isStationaryLocked();
                            Slog.i("DeviceIdleController", "isStationary = " + isStationaryLocked);
                            if (stationaryListener == null) {
                                ArraySet arraySet = DeviceIdleController.this.mStationaryListeners;
                                stationaryListenerArr = (DeviceIdleInternal.StationaryListener[]) arraySet.toArray(new DeviceIdleInternal.StationaryListener[arraySet.size()]);
                            }
                        } finally {
                        }
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
                case 11:
                case 12:
                default:
                    return;
                case 10:
                    IDeviceIdleConstraint iDeviceIdleConstraint = (IDeviceIdleConstraint) message.obj;
                    if (message.arg1 == 1) {
                        iDeviceIdleConstraint.startMonitoring();
                        return;
                    } else {
                        iDeviceIdleConstraint.stopMonitoring();
                        return;
                    }
                case 13:
                    int i4 = message.arg1;
                    int i5 = message.arg2 != 1 ? 0 : 1;
                    synchronized (DeviceIdleController.this) {
                        ArraySet arraySet2 = DeviceIdleController.this.mTempAllowlistChangeListeners;
                        tempAllowlistChangeListenerArr = (PowerAllowlistInternal.TempAllowlistChangeListener[]) arraySet2.toArray(new PowerAllowlistInternal.TempAllowlistChangeListener[arraySet2.size()]);
                    }
                    int length2 = tempAllowlistChangeListenerArr.length;
                    while (i < length2) {
                        PowerAllowlistInternal.TempAllowlistChangeListener tempAllowlistChangeListener = tempAllowlistChangeListenerArr[i];
                        if (i5 != 0) {
                            tempAllowlistChangeListener.onAppAdded(i4);
                        } else {
                            tempAllowlistChangeListener.onAppRemoved(i4);
                        }
                        i++;
                    }
                    return;
                case 14:
                    DeviceIdleController.this.mNetworkPolicyManagerInternal.onTempPowerSaveWhitelistChange(message.arg1, message.arg2, (String) message.obj, true);
                    return;
                case 15:
                    DeviceIdleController.this.mNetworkPolicyManagerInternal.onTempPowerSaveWhitelistChange(message.arg1, 0, null, false);
                    return;
            }
        }
    }

    public final class Shell extends ShellCommand {
        public int userId = 0;

        public Shell() {
        }

        public final int onCommand(String str) {
            return DeviceIdleController.this.onShellCommand(this, str);
        }

        public final void onHelp() {
            DeviceIdleController.dumpHelp(getOutPrintWriter());
        }
    }

    public final class TargetPkg {
        public final String pkgName;
        public final int uid;

        public TargetPkg(int i, String str) {
            this.uid = i;
            this.pkgName = str;
        }

        public TargetPkg(DeviceIdleController deviceIdleController, int i) {
            this.uid = i;
            boolean z = DeviceIdleController.DEBUG;
            deviceIdleController.getClass();
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
            this.pkgName = str != null ? str.trim() : "unknown";
        }
    }

    /* renamed from: -$$Nest$mmaybeBecomeActiveOnModeManagerEventsLocked, reason: not valid java name */
    public static void m60$$Nest$mmaybeBecomeActiveOnModeManagerEventsLocked(DeviceIdleController deviceIdleController) {
        synchronized (deviceIdleController) {
            try {
                if (deviceIdleController.mQuickDozeActivated) {
                    return;
                }
                if (!deviceIdleController.mIsOffBody && !deviceIdleController.mForceIdle) {
                    deviceIdleController.becomeActiveLocked(Process.myUid(), "on_body");
                }
            } finally {
            }
        }
    }

    public DeviceIdleController(Context context) {
        this(context, new Injector(context));
    }

    public DeviceIdleController(Context context, Injector injector) {
        super(context);
        this.mNumBlockingConstraints = 0;
        this.mConstraints = new ArrayMap();
        this.mLightBatteryLevel = new BatteryLevel();
        this.mDeepBatteryLevel = new BatteryLevel();
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
        this.mPowerSaveWhitelistReviewedApps = new ArraySet();
        new ArraySet();
        this.mPowerSaveWhitelistPrintErrorApps = new ArraySet();
        this.mStationaryListeners = new ArraySet();
        this.mTempAllowlistChangeListeners = new ArraySet();
        this.mEventCmds = new int[100];
        this.mEventTimes = new long[100];
        this.mEventReasons = new String[100];
        this.mAllowlistHistoryInfo = new RingBuffer(AllowlistHistoryInfo.class, 100);
        final int i = 0;
        this.mReceiver = new BroadcastReceiver(this) { // from class: com.android.server.DeviceIdleController.1
            public final /* synthetic */ DeviceIdleController this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                Uri data;
                String schemeSpecificPart;
                boolean z = true;
                switch (i) {
                    case 0:
                        String action = intent.getAction();
                        action.getClass();
                        switch (action) {
                            case "android.intent.action.BATTERY_CHANGED":
                                boolean booleanExtra = intent.getBooleanExtra("present", true);
                                boolean z2 = intent.getIntExtra("plugged", 0) != 0;
                                synchronized (this.this$0) {
                                    DeviceIdleController deviceIdleController = this.this$0;
                                    if (!booleanExtra || !z2) {
                                        z = false;
                                    }
                                    deviceIdleController.updateChargingLocked(z);
                                }
                                return;
                            case "android.net.conn.CONNECTIVITY_CHANGE":
                                this.this$0.updateConnectivityState(intent);
                                return;
                            case "android.intent.action.PACKAGE_REMOVED":
                                if (intent.getBooleanExtra("android.intent.extra.REPLACING", false) || (data = intent.getData()) == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null) {
                                    return;
                                }
                                this.this$0.removePowerSaveWhitelistAppInternal(schemeSpecificPart, 5, null);
                                return;
                            default:
                                return;
                        }
                    default:
                        synchronized (this.this$0) {
                            this.this$0.updateInteractivityLocked();
                        }
                        return;
                }
            }
        };
        this.mLightAlarmListener = new AlarmManager.OnAlarmListener(this) { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda8
            public final /* synthetic */ DeviceIdleController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                switch (i) {
                    case 0:
                        DeviceIdleController deviceIdleController = this.f$0;
                        if (DeviceIdleController.DEBUG) {
                            deviceIdleController.getClass();
                            Slog.d("DeviceIdleController", "Light progression alarm fired");
                        }
                        synchronized (deviceIdleController) {
                            deviceIdleController.stepLightIdleStateLocked("s:alarm");
                        }
                        return;
                    case 1:
                        DeviceIdleController deviceIdleController2 = this.f$0;
                        synchronized (deviceIdleController2) {
                            try {
                                if (deviceIdleController2.isStationaryLocked()) {
                                    deviceIdleController2.mHandler.sendEmptyMessage(7);
                                    return;
                                } else {
                                    Slog.w("DeviceIdleController", "motion timeout went off and device isn't stationary");
                                    return;
                                }
                            } finally {
                            }
                        }
                    default:
                        DeviceIdleController deviceIdleController3 = this.f$0;
                        synchronized (deviceIdleController3) {
                            try {
                                if (deviceIdleController3.mStationaryListeners.size() > 0) {
                                    deviceIdleController3.startMonitoringMotionLocked();
                                    deviceIdleController3.scheduleMotionTimeoutAlarmLocked();
                                }
                            } finally {
                            }
                        }
                        return;
                }
            }
        };
        final int i2 = 2;
        this.mMotionRegistrationAlarmListener = new AlarmManager.OnAlarmListener(this) { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda8
            public final /* synthetic */ DeviceIdleController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                switch (i2) {
                    case 0:
                        DeviceIdleController deviceIdleController = this.f$0;
                        if (DeviceIdleController.DEBUG) {
                            deviceIdleController.getClass();
                            Slog.d("DeviceIdleController", "Light progression alarm fired");
                        }
                        synchronized (deviceIdleController) {
                            deviceIdleController.stepLightIdleStateLocked("s:alarm");
                        }
                        return;
                    case 1:
                        DeviceIdleController deviceIdleController2 = this.f$0;
                        synchronized (deviceIdleController2) {
                            try {
                                if (deviceIdleController2.isStationaryLocked()) {
                                    deviceIdleController2.mHandler.sendEmptyMessage(7);
                                    return;
                                } else {
                                    Slog.w("DeviceIdleController", "motion timeout went off and device isn't stationary");
                                    return;
                                }
                            } finally {
                            }
                        }
                    default:
                        DeviceIdleController deviceIdleController3 = this.f$0;
                        synchronized (deviceIdleController3) {
                            try {
                                if (deviceIdleController3.mStationaryListeners.size() > 0) {
                                    deviceIdleController3.startMonitoringMotionLocked();
                                    deviceIdleController3.scheduleMotionTimeoutAlarmLocked();
                                }
                            } finally {
                            }
                        }
                        return;
                }
            }
        };
        final int i3 = 1;
        this.mMotionTimeoutAlarmListener = new AlarmManager.OnAlarmListener(this) { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda8
            public final /* synthetic */ DeviceIdleController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                switch (i3) {
                    case 0:
                        DeviceIdleController deviceIdleController = this.f$0;
                        if (DeviceIdleController.DEBUG) {
                            deviceIdleController.getClass();
                            Slog.d("DeviceIdleController", "Light progression alarm fired");
                        }
                        synchronized (deviceIdleController) {
                            deviceIdleController.stepLightIdleStateLocked("s:alarm");
                        }
                        return;
                    case 1:
                        DeviceIdleController deviceIdleController2 = this.f$0;
                        synchronized (deviceIdleController2) {
                            try {
                                if (deviceIdleController2.isStationaryLocked()) {
                                    deviceIdleController2.mHandler.sendEmptyMessage(7);
                                    return;
                                } else {
                                    Slog.w("DeviceIdleController", "motion timeout went off and device isn't stationary");
                                    return;
                                }
                            } finally {
                            }
                        }
                    default:
                        DeviceIdleController deviceIdleController3 = this.f$0;
                        synchronized (deviceIdleController3) {
                            try {
                                if (deviceIdleController3.mStationaryListeners.size() > 0) {
                                    deviceIdleController3.startMonitoringMotionLocked();
                                    deviceIdleController3.scheduleMotionTimeoutAlarmLocked();
                                }
                            } finally {
                            }
                        }
                        return;
                }
            }
        };
        final int i4 = 0;
        this.mSensingTimeoutAlarmListener = new AlarmManager.OnAlarmListener(this) { // from class: com.android.server.DeviceIdleController.2
            public final /* synthetic */ DeviceIdleController this$0;

            {
                this.this$0 = this;
            }

            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                switch (i4) {
                    case 0:
                        synchronized (this.this$0) {
                            try {
                                DeviceIdleController deviceIdleController = this.this$0;
                                if (deviceIdleController.mState == 3) {
                                    deviceIdleController.becomeInactiveIfAppropriateLocked();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        return;
                    default:
                        synchronized (this.this$0) {
                            this.this$0.stepIdleStateLocked("s:alarm");
                        }
                        return;
                }
            }
        };
        final int i5 = 1;
        this.mDeepAlarmListener = new AlarmManager.OnAlarmListener(this) { // from class: com.android.server.DeviceIdleController.2
            public final /* synthetic */ DeviceIdleController this$0;

            {
                this.this$0 = this;
            }

            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                switch (i5) {
                    case 0:
                        synchronized (this.this$0) {
                            try {
                                DeviceIdleController deviceIdleController = this.this$0;
                                if (deviceIdleController.mState == 3) {
                                    deviceIdleController.becomeInactiveIfAppropriateLocked();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        return;
                    default:
                        synchronized (this.this$0) {
                            this.this$0.stepIdleStateLocked("s:alarm");
                        }
                        return;
                }
            }
        };
        this.mIdleStartedDoneReceiver = new IIntentReceiver.Stub() { // from class: com.android.server.DeviceIdleController.4
            public final void performReceive(Intent intent, int i6, String str, Bundle bundle, boolean z, boolean z2, int i7) {
                if ("android.os.action.DEVICE_IDLE_MODE_CHANGED".equals(intent.getAction())) {
                    DeviceIdleController deviceIdleController = DeviceIdleController.this;
                    deviceIdleController.mHandler.sendEmptyMessageDelayed(8, deviceIdleController.mConstants.MIN_DEEP_MAINTENANCE_TIME);
                } else {
                    DeviceIdleController deviceIdleController2 = DeviceIdleController.this;
                    deviceIdleController2.mHandler.sendEmptyMessageDelayed(8, deviceIdleController2.mConstants.MIN_LIGHT_MAINTENANCE_TIME);
                }
            }
        };
        this.mInteractivityReceiver = new BroadcastReceiver(this) { // from class: com.android.server.DeviceIdleController.1
            public final /* synthetic */ DeviceIdleController this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                Uri data;
                String schemeSpecificPart;
                boolean z = true;
                switch (i5) {
                    case 0:
                        String action = intent.getAction();
                        action.getClass();
                        switch (action) {
                            case "android.intent.action.BATTERY_CHANGED":
                                boolean booleanExtra = intent.getBooleanExtra("present", true);
                                boolean z2 = intent.getIntExtra("plugged", 0) != 0;
                                synchronized (this.this$0) {
                                    DeviceIdleController deviceIdleController = this.this$0;
                                    if (!booleanExtra || !z2) {
                                        z = false;
                                    }
                                    deviceIdleController.updateChargingLocked(z);
                                }
                                return;
                            case "android.net.conn.CONNECTIVITY_CHANGE":
                                this.this$0.updateConnectivityState(intent);
                                return;
                            case "android.intent.action.PACKAGE_REMOVED":
                                if (intent.getBooleanExtra("android.intent.extra.REPLACING", false) || (data = intent.getData()) == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null) {
                                    return;
                                }
                                this.this$0.removePowerSaveWhitelistAppInternal(schemeSpecificPart, 5, null);
                                return;
                            default:
                                return;
                        }
                    default:
                        synchronized (this.this$0) {
                            this.this$0.updateInteractivityLocked();
                        }
                        return;
                }
            }
        };
        this.mEmergencyCallListener = new EmergencyCallListener();
        this.mModeManagerQuickDozeRequestConsumer = new ModeManagerQuickDozeRequestConsumer();
        this.mModeManagerOffBodyStateConsumer = new ModeManagerOffBodyStateConsumer();
        this.mMotionListener = new MotionListener();
        final int i6 = 0;
        this.mGenericLocationListener = new LocationListener(this) { // from class: com.android.server.DeviceIdleController.6
            public final /* synthetic */ DeviceIdleController this$0;

            {
                this.this$0 = this;
            }

            private final void onProviderDisabled$com$android$server$DeviceIdleController$6(String str) {
            }

            private final void onProviderDisabled$com$android$server$DeviceIdleController$7(String str) {
            }

            private final void onProviderEnabled$com$android$server$DeviceIdleController$6(String str) {
            }

            private final void onProviderEnabled$com$android$server$DeviceIdleController$7(String str) {
            }

            private final void onStatusChanged$com$android$server$DeviceIdleController$6(int i7, String str, Bundle bundle) {
            }

            private final void onStatusChanged$com$android$server$DeviceIdleController$7(int i7, String str, Bundle bundle) {
            }

            @Override // android.location.LocationListener
            public final void onLocationChanged(Location location) {
                switch (i6) {
                    case 0:
                        synchronized (this.this$0) {
                            this.this$0.receivedGenericLocationLocked(location);
                        }
                        return;
                    default:
                        synchronized (this.this$0) {
                            this.this$0.receivedGpsLocationLocked(location);
                        }
                        return;
                }
            }

            @Override // android.location.LocationListener
            public final void onProviderDisabled(String str) {
                int i7 = i6;
            }

            @Override // android.location.LocationListener
            public final void onProviderEnabled(String str) {
                int i7 = i6;
            }

            @Override // android.location.LocationListener
            public final void onStatusChanged(String str, int i7, Bundle bundle) {
                int i8 = i6;
            }
        };
        final int i7 = 1;
        this.mGpsLocationListener = new LocationListener(this) { // from class: com.android.server.DeviceIdleController.6
            public final /* synthetic */ DeviceIdleController this$0;

            {
                this.this$0 = this;
            }

            private final void onProviderDisabled$com$android$server$DeviceIdleController$6(String str) {
            }

            private final void onProviderDisabled$com$android$server$DeviceIdleController$7(String str) {
            }

            private final void onProviderEnabled$com$android$server$DeviceIdleController$6(String str) {
            }

            private final void onProviderEnabled$com$android$server$DeviceIdleController$7(String str) {
            }

            private final void onStatusChanged$com$android$server$DeviceIdleController$6(int i72, String str, Bundle bundle) {
            }

            private final void onStatusChanged$com$android$server$DeviceIdleController$7(int i72, String str, Bundle bundle) {
            }

            @Override // android.location.LocationListener
            public final void onLocationChanged(Location location) {
                switch (i7) {
                    case 0:
                        synchronized (this.this$0) {
                            this.this$0.receivedGenericLocationLocked(location);
                        }
                        return;
                    default:
                        synchronized (this.this$0) {
                            this.this$0.receivedGpsLocationLocked(location);
                        }
                        return;
                }
            }

            @Override // android.location.LocationListener
            public final void onProviderDisabled(String str) {
                int i72 = i7;
            }

            @Override // android.location.LocationListener
            public final void onProviderEnabled(String str) {
                int i72 = i7;
            }

            @Override // android.location.LocationListener
            public final void onStatusChanged(String str, int i72, Bundle bundle) {
                int i8 = i7;
            }
        };
        this.mScreenObserver = new ActivityTaskManagerInternal.ScreenObserver() { // from class: com.android.server.DeviceIdleController.8
            @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
            public final void onAwakeStateChanged(boolean z) {
            }

            @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
            public final void onKeyguardStateChanged(boolean z) {
                synchronized (DeviceIdleController.this) {
                    DeviceIdleController.this.keyguardShowingLocked(z);
                }
            }
        };
        this.mInjector = injector;
        this.mConfigFile = new AtomicFile(new File(new File(Environment.getDataDirectory(), "system"), "deviceidle.xml"));
        injector.getClass();
        this.mHandler = new MyHandler(AppSchedulingModuleThread.getHandler().getLooper());
        AppStateTrackerImpl appStateTrackerImpl = new AppStateTrackerImpl(context, AppSchedulingModuleThread.get().getLooper());
        this.mAppStateTracker = appStateTrackerImpl;
        LocalServices.addService(AppStateTracker.class, appStateTrackerImpl);
        Flags.removeIdleLocation();
        this.mIsLocationPrefetchEnabled = injector.mContext.getResources().getBoolean(R.bool.config_auto_attach_data_on_creation);
        this.mUseMotionSensor = injector.mContext.getResources().getBoolean(R.bool.config_automatic_brightness_available);
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

    public static void dumpHelp(PrintWriter printWriter) {
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "Device idle controller (deviceidle) commands:", "  help", "    Print this help text.", "  step [light|deep]");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "    Immediately step to next state, without waiting for alarm.", "  force-idle [light|deep]", "    Force directly into idle mode, regardless of other device state.", "  force-inactive");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "    Force to be inactive, ready to freely step idle states.", "  unforce", "    Resume normal functioning after force-idle or force-inactive or force-modemanager-quickdoze.", "  get [light|deep|force|screen|charging|network|offbody|forceoffbody]");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "    Retrieve the current given state.", "  disable [light|deep|all]", "    Completely disable device idle mode.", "  enable [light|deep|all]");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "    Re-enable device idle mode after it had previously been disabled.", "  enabled [light|deep|all]", "    Print 1 if device idle mode is currently enabled, else 0.", "  whitelist");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "    Print currently whitelisted apps.", "  whitelist [package ...]", "    Add (prefix with +) or remove (prefix with -) packages.", "  sys-whitelist [package ...|reset]");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "    Prefix the package with '-' to remove it from the system whitelist or '+' to put it back in the system whitelist.", "    Note that only packages that were earlier removed from the system whitelist can be added back.", "    reset will reset the whitelist to the original state", "    Prints the system whitelist if no arguments are specified");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  except-idle-whitelist [package ...|reset]", "    Prefix the package with '+' to add it to whitelist or '=' to check if it is already whitelisted", "    [reset] will reset the whitelist to it's original state", "    Note that unlike <whitelist> cmd, changes made using this won't be persisted across boots");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  tempwhitelist", "    Print packages that are temporarily whitelisted.", "  tempwhitelist [-u USER] [-d DURATION] [-r] [package]", "    Temporarily place package in whitelist for DURATION milliseconds.");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "    If no DURATION is specified, 10 seconds is used", "    If [-r] option is used, then the package is removed from temp whitelist and any [-d] is ignored", "  motion", "    Simulate a motion event to bring the device out of deep doze");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "  force-modemanager-quickdoze [true|false]", "    Simulate mode manager request to enable (true) or disable (false) quick doze. Mode manager changes will be ignored until unforce is called.", "  force-modemanager-offbody [true|false]", "    Force mode manager offbody state, this can be used to simulate device being off-body (true) or on-body (false). Mode manager changes will be ignored until unforce is called.");
    }

    public static String floatToString(float f) {
        return f >= FullScreenMagnificationGestureHandler.MAX_SCALE ? String.format("%.2f", Float.valueOf(f)) : "invalid";
    }

    public static float getBatteryLevelDiff(float f, float f2) {
        if (f < FullScreenMagnificationGestureHandler.MAX_SCALE || f2 < FullScreenMagnificationGestureHandler.MAX_SCALE) {
            Slog.w("DeviceIdleController", "getBatteryLevelDiff() : curr(" + f2 + ") or prev(" + f + ") is invalid");
            return -1.0f;
        }
        if (f >= f2) {
            return f - f2;
        }
        Slog.w("DeviceIdleController", "getBatteryLevelDiff() : curr(" + f2 + ") is bigger than prev(" + f + ")");
        return -1.0f;
    }

    public static long getDuration(long j, long j2) {
        if (j <= 0 || j2 <= 0 || j2 <= j) {
            return 0L;
        }
        return j2 - j;
    }

    public static String lightStateToString(int i) {
        return i != 0 ? i != 1 ? i != 4 ? i != 5 ? i != 6 ? i != 7 ? Integer.toString(i) : "OVERRIDE" : "IDLE_MAINTENANCE" : "WAITING_FOR_NETWORK" : "IDLE" : "INACTIVE" : "ACTIVE";
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

    public final void addEvent(int i, String str) {
        int[] iArr = this.mEventCmds;
        if (iArr[0] != i) {
            System.arraycopy(iArr, 0, iArr, 1, 99);
            long[] jArr = this.mEventTimes;
            System.arraycopy(jArr, 0, jArr, 1, 99);
            String[] strArr = this.mEventReasons;
            System.arraycopy(strArr, 0, strArr, 1, 99);
            iArr[0] = i;
            jArr[0] = SystemClock.elapsedRealtime();
            strArr[0] = str;
        }
    }

    public final void addPowerSaveTempAllowlistAppChecked(int i, int i2, String str, String str2, long j) {
        int i3;
        getContext().enforceCallingOrSelfPermission("android.permission.CHANGE_DEVICE_IDLE_TEMP_WHITELIST", "No permission to change device idle whitelist");
        int callingUid = Binder.getCallingUid();
        int handleIncomingUser = ActivityManager.getService().handleIncomingUser(Binder.getCallingPid(), callingUid, i, false, false, "addPowerSaveTempWhitelistApp", (String) null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (i2 == -1) {
            i3 = -1;
        } else if (i2 != 102) {
            i3 = 0;
        } else {
            try {
                i3 = this.mLocalActivityManager.getPushMessagingOverQuotaBehavior();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        if (i3 != -1) {
            addPowerSaveTempAllowlistAppInternal(callingUid, str, j, i3, handleIncomingUser, true, i2, str2);
        }
    }

    public final void addPowerSaveTempAllowlistAppInternal(int i, String str, long j, int i2, int i3, boolean z, int i4, String str2) {
        try {
            addPowerSaveTempWhitelistAppDirectInternal(i, j, getContext().getPackageManager().getPackageUidAsUser(str, i3), z, i2, str2, i4);
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    public final void addPowerSaveTempWhitelistAppDirectInternal(int i, long j, int i2, boolean z, int i3, String str, int i4) {
        boolean z2;
        boolean z3;
        int i5;
        boolean z4;
        String str2;
        int i6;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        int appId = UserHandle.getAppId(i2);
        synchronized (this) {
            try {
                long min = Math.min(j, this.mConstants.MAX_TEMP_APP_ALLOWLIST_DURATION_MS);
                Pair pair = (Pair) this.mTempWhitelistAppIdEndTimes.get(appId);
                z2 = false;
                boolean z5 = pair == null;
                if (z5) {
                    pair = new Pair(new MutableLong(0L), str);
                    appId = appId;
                    this.mTempWhitelistAppIdEndTimes.put(appId, pair);
                }
                ((MutableLong) pair.first).value = elapsedRealtime + min;
                if (DEBUG) {
                    Slog.d("DeviceIdleController", "Adding AppId " + appId + " to temp whitelist. New entry: " + z5);
                }
                if (z5) {
                    try {
                        this.mBatteryStats.noteEvent(32785, str, i2);
                    } catch (RemoteException unused) {
                    }
                    postTempActiveTimeoutMessage(i2, min);
                    updateTempWhitelistAppIdsLocked(i2, min, i3, true, i4, str, i);
                    if (z) {
                        z2 = true;
                    } else {
                        this.mHandler.obtainMessage(14, appId, i4, str).sendToTarget();
                    }
                    z3 = true;
                    this.mHandler.obtainMessage(13, i2, 1).sendToTarget();
                    getContext().sendBroadcastAsUser(this.mPowerSaveTempWhitelistChangedIntent, UserHandle.SYSTEM, null, this.mPowerSaveTempWhilelistChangedOptions);
                } else {
                    z3 = true;
                    ActivityManagerInternal activityManagerInternal = this.mLocalActivityManager;
                    if (activityManagerInternal != null) {
                        z4 = true;
                        i5 = appId;
                        i6 = i4;
                        str2 = str;
                        activityManagerInternal.updateDeviceIdleTempAllowlist((int[]) null, i2, true, min, i3, i4, str, i);
                    }
                }
                z4 = z3;
                i5 = appId;
                i6 = i4;
                str2 = str;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z2) {
            this.mNetworkPolicyManagerInternal.onTempPowerSaveWhitelistChange(i5, i6, str2, z4);
        }
    }

    public final int addPowerSaveWhitelistAppsInternal(List list, int i, TargetPkg targetPkg) {
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
                            this.mAllowlistHistoryInfo.append(new AllowlistHistoryInfo(i, targetPkg, new TargetPkg(appId, str)));
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

    public final boolean addPowerSaveWhitelistExceptIdleInternal(String str) {
        synchronized (this) {
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
        }
        return true;
    }

    public final void becomeActiveLocked(int i, String str) {
        becomeActiveLocked(str, i, this.mConstants.INACTIVE_TIMEOUT, true);
    }

    public final void becomeActiveLocked(String str, int i, long j, boolean z) {
        if (DEBUG) {
            Slog.i("DeviceIdleController", "becomeActiveLocked, reason=" + str + ", changeLightIdle=" + z);
        }
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
            Constants constants = this.mConstants;
            this.mNextLightIdleDelay = constants.LIGHT_IDLE_TIMEOUT;
            this.mMaintenanceStartTime = 0L;
            this.mNextLightIdleDelayFlex = constants.LIGHT_IDLE_TIMEOUT_INITIAL_FLEX;
            this.mCurLightIdleBudget = constants.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET;
            if (this.mNextLightAlarmTime != 0) {
                this.mNextLightAlarmTime = 0L;
                this.mAlarmManager.cancel(this.mLightAlarmListener);
            }
            scheduleReportActiveLocked(str, i);
            addEvent(1, str);
        }
        Slog.i("DeviceIdleController", "resetBatteryLevel");
        float batteryLevelRaw = getBatteryLevelRaw();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        BatteryLevel batteryLevel = this.mLightBatteryLevel;
        batteryLevel.prev = batteryLevelRaw;
        batteryLevel.curr = batteryLevelRaw;
        batteryLevel.prevTime = elapsedRealtime;
        batteryLevel.currTime = elapsedRealtime;
        BatteryLevel batteryLevel2 = this.mDeepBatteryLevel;
        batteryLevel2.prev = batteryLevelRaw;
        batteryLevel2.curr = batteryLevelRaw;
        batteryLevel2.prevTime = elapsedRealtime;
        batteryLevel2.currTime = elapsedRealtime;
    }

    public final void becomeInactiveIfAppropriateLocked() {
        String str;
        boolean z;
        boolean z2;
        if (this.mState == 0 && this.mNextAlarmTime != 0) {
            Slog.wtf("DeviceIdleController", "mState=ACTIVE but mNextAlarmTime=" + this.mNextAlarmTime);
        }
        boolean z3 = false;
        if (this.mState != 5) {
            AlarmManagerService alarmManagerService = AlarmManagerService.this;
            synchronized (alarmManagerService.mLock) {
                z2 = alarmManagerService.mPendingIdleUntil != null;
            }
            if (z2) {
                Slog.wtf("DeviceIdleController", "mState=" + stateToString(this.mState) + " but AlarmManager is idling");
            }
        }
        if (this.mState == 5) {
            AlarmManagerService alarmManagerService2 = AlarmManagerService.this;
            synchronized (alarmManagerService2.mLock) {
                z = alarmManagerService2.mPendingIdleUntil != null;
            }
            if (!z) {
                Slog.wtf("DeviceIdleController", "mState=IDLE but AlarmManager is not idling");
            }
        }
        if (this.mLightState == 0 && this.mNextLightAlarmTime != 0) {
            Slog.wtf("DeviceIdleController", "mLightState=ACTIVE but mNextLightAlarmTime is " + TimeUtils.formatDuration(this.mNextLightAlarmTime - SystemClock.elapsedRealtime()) + " from now");
        }
        if (this.mScreenOn && (!this.mConstants.WAIT_FOR_UNLOCK || !this.mScreenLocked)) {
            z3 = true;
        }
        boolean z4 = this.mEmergencyCallListener.mIsEmergencyCallActive;
        if (DEBUG) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("becomeInactiveIfAppropriateLocked(): isScreenBlockingInactive=", " (mScreenOn=", z3);
            m.append(this.mScreenOn);
            m.append(", WAIT_FOR_UNLOCK=");
            m.append(this.mConstants.WAIT_FOR_UNLOCK);
            m.append(", mScreenLocked=");
            m.append(this.mScreenLocked);
            m.append(") mCharging=");
            BatteryService$$ExternalSyntheticOutline0.m(m, this.mCharging, " emergencyCall=", z4, " mForceIdle=");
            m.append(this.mForceIdle);
            if (this.mForceIdle) {
                str = " mForceType=" + this.mForceType;
            } else {
                str = "";
            }
            BootReceiver$$ExternalSyntheticOutline0.m(m, str, "DeviceIdleController");
        }
        boolean z5 = this.mForceIdle;
        if (z5 || !(this.mCharging || z3 || z4)) {
            if (z5 && "active".equals(this.mForceType)) {
                Slog.i("DeviceIdleController", "Don't become Inactive in force-active mode");
                return;
            }
            if (this.mDeepEnabled) {
                if (this.mQuickDozeActivated) {
                    int i = this.mState;
                    if (i == 7 || i == 5 || i == 6) {
                        return;
                    }
                    moveToStateLocked(7, "no activity");
                    resetIdleManagementLocked();
                    if (isUpcomingAlarmClock()) {
                        long nextWakeFromIdleTime = this.mAlarmManager.getNextWakeFromIdleTime();
                        this.mInjector.getClass();
                        scheduleAlarmLocked((nextWakeFromIdleTime - SystemClock.elapsedRealtime()) + this.mConstants.QUICK_DOZE_DELAY_TIMEOUT);
                    } else {
                        scheduleAlarmLocked(this.mConstants.QUICK_DOZE_DELAY_TIMEOUT);
                    }
                } else if (this.mState == 0) {
                    moveToStateLocked(1, "no activity");
                    resetIdleManagementLocked();
                    long j = this.mInactiveTimeout;
                    if (isUpcomingAlarmClock()) {
                        long nextWakeFromIdleTime2 = this.mAlarmManager.getNextWakeFromIdleTime();
                        this.mInjector.getClass();
                        scheduleAlarmLocked((nextWakeFromIdleTime2 - SystemClock.elapsedRealtime()) + j);
                    } else {
                        scheduleAlarmLocked(j);
                    }
                }
            }
            if (this.mLightState == 0 && this.mLightEnabled) {
                moveToLightStateLocked(1, "no activity");
                Constants constants = this.mConstants;
                this.mNextLightIdleDelay = constants.LIGHT_IDLE_TIMEOUT;
                this.mMaintenanceStartTime = 0L;
                this.mNextLightIdleDelayFlex = constants.LIGHT_IDLE_TIMEOUT_INITIAL_FLEX;
                this.mCurLightIdleBudget = constants.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET;
                if (this.mNextLightAlarmTime != 0) {
                    this.mNextLightAlarmTime = 0L;
                    this.mAlarmManager.cancel(this.mLightAlarmListener);
                }
                scheduleLightAlarmLocked(this.mConstants.LIGHT_IDLE_AFTER_INACTIVE_TIMEOUT, 1000L, true);
            }
        }
    }

    public final void cancelAlarmLocked() {
        if (this.mNextAlarmTime != 0) {
            this.mNextAlarmTime = 0L;
            this.mAlarmManager.cancel(this.mDeepAlarmListener);
        }
    }

    public final void cancelLocatingLocked() {
        if (this.mLocating) {
            Injector injector = this.mInjector;
            if (injector.mLocationManager == null) {
                injector.mLocationManager = (LocationManager) injector.mContext.getSystemService(LocationManager.class);
            }
            LocationManager locationManager = injector.mLocationManager;
            locationManager.removeUpdates(this.mGenericLocationListener);
            locationManager.removeUpdates(this.mGpsLocationListener);
            this.mLocating = false;
        }
    }

    public final void cancelSensingTimeoutAlarmLocked() {
        if (this.mNextSensingTimeoutAlarmTime != 0) {
            this.mNextSensingTimeoutAlarmTime = 0L;
            this.mAlarmManager.cancel(this.mSensingTimeoutAlarmListener);
        }
    }

    public final void decActiveIdleOps() {
        synchronized (this) {
            try {
                int i = this.mActiveIdleOpCount - 1;
                this.mActiveIdleOpCount = i;
                if (i <= 0) {
                    exitMaintenanceEarlyIfNeededLocked();
                    this.mActiveIdleWakeLock.release();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dumpTempWhitelistScheduleLocked(PrintWriter printWriter, boolean z) {
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

    public final void exitForceIdleLocked() {
        if (this.mForceIdle) {
            this.mForceIdle = false;
            this.mForceType = "";
            if (this.mScreenOn || this.mCharging) {
                becomeActiveLocked(Process.myUid(), "exit-force");
            }
        }
    }

    public final void exitMaintenanceEarlyIfNeededLocked() {
        if ((this.mState == 6 || this.mLightState == 6) && this.mActiveIdleOpCount <= 0 && !this.mJobsActive && !this.mAlarmsActive) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (DEBUG) {
                StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m("Exit: start=");
                TimeUtils.formatDuration(this.mMaintenanceStartTime, m);
                m.append(" now=");
                TimeUtils.formatDuration(elapsedRealtime, m);
                Slog.d("DeviceIdleController", m.toString());
            }
            if (this.mState == 6) {
                stepIdleStateLocked("s:early");
            } else {
                stepLightIdleStateLocked("s:early");
            }
        }
    }

    public final float getBatteryLevelRaw() {
        BatteryManagerInternal batteryManagerInternal = this.mLocalBatteryManager;
        if (batteryManagerInternal != null) {
            int batteryLevelRaw = batteryManagerInternal.getBatteryLevelRaw();
            if (batteryLevelRaw >= 0 && batteryLevelRaw <= 10000) {
                return batteryLevelRaw / 100.0f;
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(batteryLevelRaw, "getBatteryLevelRaw() : batteryLevelInt is invalid : ", "DeviceIdleController");
        } else {
            Slog.w("DeviceIdleController", "getBatteryLevelRaw() : mLocalBatteryManager is null");
        }
        return -1.0f;
    }

    public final String[] getFullPowerWhitelistInternalUnchecked() {
        String[] strArr;
        synchronized (this) {
            try {
                strArr = new String[this.mPowerSaveWhitelistApps.size() + this.mPowerSaveWhitelistUserApps.size()];
                int i = 0;
                for (int i2 = 0; i2 < this.mPowerSaveWhitelistApps.size(); i2++) {
                    strArr[i] = (String) this.mPowerSaveWhitelistApps.keyAt(i2);
                    i++;
                }
                for (int i3 = 0; i3 < this.mPowerSaveWhitelistUserApps.size(); i3++) {
                    strArr[i] = (String) this.mPowerSaveWhitelistUserApps.keyAt(i3);
                    i++;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return strArr;
    }

    public int getLightState() {
        int i;
        synchronized (this) {
            i = this.mLightState;
        }
        return i;
    }

    public long getNextAlarmTime() {
        long j;
        synchronized (this) {
            j = this.mNextAlarmTime;
        }
        return j;
    }

    public long getNextLightAlarmTimeForTesting() {
        long j;
        synchronized (this) {
            j = this.mNextLightAlarmTime;
        }
        return j;
    }

    public final boolean getPowerSaveWhitelistAppInternal(String str) {
        boolean containsKey;
        synchronized (this) {
            containsKey = this.mPowerSaveWhitelistUserApps.containsKey(str);
        }
        return containsKey;
    }

    public final boolean getPowerSaveWhitelistExceptIdleInternal(String str) {
        boolean containsKey;
        synchronized (this) {
            containsKey = this.mPowerSaveWhitelistAppsExceptIdle.containsKey(str);
        }
        return containsKey;
    }

    public int getState() {
        int i;
        synchronized (this) {
            i = this.mState;
        }
        return i;
    }

    public final void handleMotionDetectedLocked(long j, String str) {
        if (this.mStationaryListeners.size() > 0) {
            this.mHandler.sendEmptyMessage(7);
            Slog.i("DeviceIdleController", "cancelMotionTimeoutAlarmLocked()");
            this.mAlarmManager.cancel(this.mMotionTimeoutAlarmListener);
            if (DEBUG) {
                Slog.d("DeviceIdleController", "scheduleMotionRegistrationAlarmLocked");
            }
            this.mInjector.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            Constants constants = this.mConstants;
            long j2 = (constants.MOTION_INACTIVE_TIMEOUT / 2) + elapsedRealtime;
            if (constants.USE_WINDOW_ALARMS) {
                this.mAlarmManager.setWindow(2, j2, constants.MOTION_INACTIVE_TIMEOUT_FLEX, "DeviceIdleController.motion_registration", this.mMotionRegistrationAlarmListener, this.mHandler);
            } else {
                this.mAlarmManager.set(2, j2, "DeviceIdleController.motion_registration", this.mMotionRegistrationAlarmListener, this.mHandler);
            }
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

    public final void handleWriteConfigFile() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            synchronized (this) {
                FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
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

    public boolean hasMotionSensor() {
        return this.mUseMotionSensor && this.mMotionSensor != null;
    }

    public boolean isCharging() {
        boolean z;
        synchronized (this) {
            z = this.mCharging;
        }
        return z;
    }

    public boolean isEmergencyCallActive() {
        return this.mEmergencyCallListener.mIsEmergencyCallActive;
    }

    public boolean isKeyguardShowing() {
        boolean z;
        synchronized (this) {
            z = this.mScreenLocked;
        }
        return z;
    }

    public boolean isNetworkConnected() {
        boolean z;
        synchronized (this) {
            z = this.mNetworkConnected;
        }
        return z;
    }

    public boolean isQuickDozeEnabled() {
        boolean z;
        synchronized (this) {
            z = this.mQuickDozeActivated;
        }
        return z;
    }

    public boolean isScreenOn() {
        boolean z;
        synchronized (this) {
            z = this.mScreenOn;
        }
        return z;
    }

    public final boolean isStationaryLocked() {
        this.mInjector.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        MotionListener motionListener = this.mMotionListener;
        return motionListener.active && elapsedRealtime - Math.max(motionListener.activatedTimeElapsed, this.mLastMotionEventElapsed) >= this.mConstants.MOTION_INACTIVE_TIMEOUT;
    }

    public final boolean isUpcomingAlarmClock() {
        this.mInjector.getClass();
        return SystemClock.elapsedRealtime() + this.mConstants.MIN_TIME_TO_ALARM >= this.mAlarmManager.getNextWakeFromIdleTime();
    }

    public void keyguardShowingLocked(boolean z) {
        if (DEBUG) {
            Slog.i("DeviceIdleController", "keyguardShowing=" + z);
        }
        if (this.mScreenLocked != z) {
            this.mScreenLocked = z;
            if (!this.mScreenOn || this.mForceIdle || z) {
                return;
            }
            becomeActiveLocked(Process.myUid(), "unlocked");
        }
    }

    public final void maybeStopMonitoringMotionLocked() {
        if (DEBUG) {
            Slog.d("DeviceIdleController", "maybeStopMonitoringMotionLocked()");
        }
        if (this.mMotionSensor == null || this.mStationaryListeners.size() != 0) {
            return;
        }
        MotionListener motionListener = this.mMotionListener;
        if (motionListener.active) {
            if (DeviceIdleController.this.mMotionSensor.getReportingMode() == 2) {
                DeviceIdleController deviceIdleController = DeviceIdleController.this;
                deviceIdleController.mSensorManager.cancelTriggerSensor(deviceIdleController.mMotionListener, deviceIdleController.mMotionSensor);
            } else {
                DeviceIdleController deviceIdleController2 = DeviceIdleController.this;
                deviceIdleController2.mSensorManager.unregisterListener(deviceIdleController2.mMotionListener);
            }
            motionListener.active = false;
            Slog.i("DeviceIdleController", "cancelMotionTimeoutAlarmLocked()");
            this.mAlarmManager.cancel(this.mMotionTimeoutAlarmListener);
        }
        Slog.i("DeviceIdleController", "cancelMotionRegistrationAlarmLocked()");
        this.mAlarmManager.cancel(this.mMotionRegistrationAlarmListener);
    }

    public final void motionLocked() {
        if (DEBUG) {
            Slog.d("DeviceIdleController", "motionLocked()");
        }
        this.mInjector.getClass();
        this.mLastMotionEventElapsed = SystemClock.elapsedRealtime();
        handleMotionDetectedLocked(this.mConstants.MOTION_INACTIVE_TIMEOUT, "motion");
    }

    public final void moveToLightStateLocked(int i, String str) {
        float batteryLevelRaw = getBatteryLevelRaw();
        BatteryLevel batteryLevel = this.mLightBatteryLevel;
        batteryLevel.curr = batteryLevelRaw;
        batteryLevel.currTime = SystemClock.elapsedRealtime();
        Slog.i("DeviceIdleController", String.format("[LIGHT] %s to %s, reason=%s, battery=%s(%s/%d)", lightStateToString(this.mLightState), lightStateToString(i), str, floatToString(batteryLevel.curr), floatToString(getBatteryLevelDiff(batteryLevel.prev, batteryLevel.curr)), Long.valueOf(getDuration(batteryLevel.prevTime, batteryLevel.currTime))));
        batteryLevel.prev = batteryLevel.curr;
        batteryLevel.prevTime = batteryLevel.currTime;
        this.mLightState = i;
        EventLog.writeEvent(34009, Integer.valueOf(i), str);
        Trace.traceCounter(524288L, "DozeLightState", i);
    }

    public final void moveToStateLocked(int i, String str) {
        float batteryLevelRaw = getBatteryLevelRaw();
        BatteryLevel batteryLevel = this.mDeepBatteryLevel;
        batteryLevel.curr = batteryLevelRaw;
        batteryLevel.currTime = SystemClock.elapsedRealtime();
        Slog.i("DeviceIdleController", String.format("[DEEP] %s to %s, reason=%s, battery=%s(%s/%d)", stateToString(this.mState), stateToString(i), str, floatToString(batteryLevel.curr), floatToString(getBatteryLevelDiff(batteryLevel.prev, batteryLevel.curr)), Long.valueOf(getDuration(batteryLevel.prevTime, batteryLevel.currTime))));
        batteryLevel.prev = batteryLevel.curr;
        batteryLevel.prevTime = batteryLevel.currTime;
        this.mState = i;
        EventLog.writeEvent(34000, Integer.valueOf(i), str);
        Trace.traceCounter(524288L, "DozeDeepState", i);
        updateActiveConstraintsLocked();
    }

    public final void onAnyMotionResult(int i) {
        if (DEBUG) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "onAnyMotionResult(", ")", "DeviceIdleController");
        }
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

    public final void onAppRemovedFromTempWhitelistLocked(int i, String str) {
        if (DEBUG) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "Removing uid ", " from temp whitelist", "DeviceIdleController");
        }
        int appId = UserHandle.getAppId(i);
        updateTempWhitelistAppIdsLocked(i, 0L, 0, false, 0, str, -1);
        MyHandler myHandler = this.mHandler;
        myHandler.obtainMessage(15, appId, 0).sendToTarget();
        myHandler.obtainMessage(13, i, 0).sendToTarget();
        getContext().sendBroadcastAsUser(this.mPowerSaveTempWhitelistChangedIntent, UserHandle.SYSTEM, null, this.mPowerSaveTempWhilelistChangedOptions);
        try {
            this.mBatteryStats.noteEvent(16401, str, appId);
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        WearModeManagerInternal wearModeManagerInternal;
        if (i != 500) {
            if (i == 1000) {
                Slog.i("DeviceIdleController", "resetBatteryLevel");
                float batteryLevelRaw = getBatteryLevelRaw();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                BatteryLevel batteryLevel = this.mLightBatteryLevel;
                batteryLevel.prev = batteryLevelRaw;
                batteryLevel.curr = batteryLevelRaw;
                batteryLevel.prevTime = elapsedRealtime;
                batteryLevel.currTime = elapsedRealtime;
                BatteryLevel batteryLevel2 = this.mDeepBatteryLevel;
                batteryLevel2.prev = batteryLevelRaw;
                batteryLevel2.curr = batteryLevelRaw;
                batteryLevel2.prevTime = elapsedRealtime;
                batteryLevel2.currTime = elapsedRealtime;
                return;
            }
            return;
        }
        synchronized (this) {
            try {
                this.mAlarmManager = (AlarmManager) this.mInjector.mContext.getSystemService(AlarmManager.class);
                this.mLocalAlarmManager = (AlarmManagerService.LocalService) getLocalService(AlarmManagerService.LocalService.class);
                this.mBatteryStats = BatteryStatsService.getService();
                this.mLocalBatteryManager = (BatteryManagerInternal) getLocalService(BatteryManagerInternal.class);
                this.mLocalActivityManager = (ActivityManagerInternal) getLocalService(ActivityManagerInternal.class);
                this.mLocalActivityTaskManager = (ActivityTaskManagerInternal) getLocalService(ActivityTaskManagerInternal.class);
                this.mPackageManagerInternal = (PackageManagerInternal) getLocalService(PackageManagerInternal.class);
                this.mLocalPowerManager = (PowerManagerInternal) getLocalService(PowerManagerInternal.class);
                PowerManager powerManager = (PowerManager) this.mInjector.mContext.getSystemService(PowerManager.class);
                this.mPowerManager = powerManager;
                PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "deviceidle_maint");
                this.mActiveIdleWakeLock = newWakeLock;
                newWakeLock.setReferenceCounted(false);
                PowerManager.WakeLock newWakeLock2 = this.mPowerManager.newWakeLock(1, "deviceidle_going_idle");
                this.mGoingIdleWakeLock = newWakeLock2;
                newWakeLock2.setReferenceCounted(true);
                this.mNetworkPolicyManager = INetworkPolicyManager.Stub.asInterface(ServiceManager.getService("netpolicy"));
                this.mNetworkPolicyManagerInternal = (NetworkPolicyManagerService.NetworkPolicyManagerInternalImpl) getLocalService(NetworkPolicyManagerService.NetworkPolicyManagerInternalImpl.class);
                this.mSensorManager = (SensorManager) this.mInjector.mContext.getSystemService(SensorManager.class);
                if (this.mUseMotionSensor) {
                    Injector injector = this.mInjector;
                    SensorManager sensorManager = (SensorManager) injector.mContext.getSystemService(SensorManager.class);
                    int integer = injector.mContext.getResources().getInteger(R.integer.config_carDockKeepsScreenOn);
                    Sensor defaultSensor = integer > 0 ? sensorManager.getDefaultSensor(integer, true) : null;
                    if (defaultSensor == null && injector.mContext.getResources().getBoolean(R.bool.config_autoResetAirplaneMode)) {
                        defaultSensor = sensorManager.getDefaultSensor(26, true);
                    }
                    if (defaultSensor == null) {
                        defaultSensor = sensorManager.getDefaultSensor(17, true);
                    }
                    this.mMotionSensor = defaultSensor;
                }
                if (this.mIsLocationPrefetchEnabled) {
                    this.mLocationRequest = new LocationRequest.Builder(0L).setQuality(100).setMaxUpdates(1).build();
                }
                Injector injector2 = this.mInjector;
                MyHandler myHandler = this.mHandler;
                TvConstraintController tvConstraintController = injector2.mContext.getPackageManager().hasSystemFeature("android.software.leanback_only") ? new TvConstraintController(injector2.mContext, myHandler) : null;
                if (tvConstraintController != null) {
                    tvConstraintController.start();
                }
                Injector injector3 = this.mInjector;
                MyHandler myHandler2 = this.mHandler;
                SensorManager sensorManager2 = this.mSensorManager;
                injector3.getClass();
                this.mAnyMotionDetector = new AnyMotionDetector((PowerManager) injector3.mContext.getSystemService(PowerManager.class), myHandler2, sensorManager2, this, getContext().getResources().getInteger(R.integer.config_carDockRotation) / 100.0f);
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
                if (this.mConstants.USE_MODE_MANAGER && (wearModeManagerInternal = (WearModeManagerInternal) LocalServices.getService(WearModeManagerInternal.class)) != null) {
                    wearModeManagerInternal.addActiveStateChangeListener("quick_doze_request", AppSchedulingModuleThread.getExecutor(), this.mModeManagerQuickDozeRequestConsumer);
                    wearModeManagerInternal.addActiveStateChangeListener("off_body", AppSchedulingModuleThread.getExecutor(), this.mModeManagerOffBodyStateConsumer);
                }
                this.mLocalPowerManager.registerLowPowerModeObserver(15, new Consumer() { // from class: com.android.server.DeviceIdleController$$ExternalSyntheticLambda13
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DeviceIdleController deviceIdleController = DeviceIdleController.this;
                        PowerSaveState powerSaveState = (PowerSaveState) obj;
                        synchronized (deviceIdleController) {
                            deviceIdleController.mBatterySaverEnabled = powerSaveState.batterySaverEnabled;
                            deviceIdleController.updateQuickDozeFlagLocked();
                        }
                    }
                });
                this.mBatterySaverEnabled = this.mLocalPowerManager.getLowPowerState(15).batterySaverEnabled;
                updateQuickDozeFlagLocked();
                this.mLocalActivityTaskManager.registerScreenObserver(this.mScreenObserver);
                ((TelephonyManager) this.mInjector.mContext.getSystemService(TelephonyManager.class)).registerTelephonyCallback(AppSchedulingModuleThread.getExecutor(), this.mEmergencyCallListener);
                passWhiteListsToForceAppStandbyTrackerLocked();
                updateInteractivityLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
        updateConnectivityState(null);
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

    /* JADX WARN: Code restructure failed: missing block: B:212:0x038e, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x039e, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:308:0x04cb, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:311:0x04d3, code lost:
    
        throw r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int onShellCommand(com.android.server.DeviceIdleController.Shell r24, java.lang.String r25) {
        /*
            Method dump skipped, instructions count: 2772
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.DeviceIdleController.onShellCommand(com.android.server.DeviceIdleController$Shell, java.lang.String):int");
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        PackageManager packageManager = getContext().getPackageManager();
        synchronized (this) {
            boolean z = getContext().getResources().getBoolean(R.bool.config_enableDefaultHdrConversionPassthrough);
            this.mDeepEnabled = z;
            this.mLightEnabled = z;
            SystemConfig systemConfig = SystemConfig.getInstance();
            ArraySet arraySet = systemConfig.mAllowInPowerSaveExceptIdle;
            for (int i = 0; i < arraySet.size(); i++) {
                try {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo((String) arraySet.valueAt(i), 1048576);
                    int appId = UserHandle.getAppId(applicationInfo.uid);
                    this.mPowerSaveWhitelistAppsExceptIdle.put(applicationInfo.packageName, Integer.valueOf(appId));
                    this.mPowerSaveWhitelistSystemAppIdsExceptIdle.put(appId, true);
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
            ArraySet arraySet2 = systemConfig.mAllowInPowerSave;
            for (int i2 = 0; i2 < arraySet2.size(); i2++) {
                try {
                    ApplicationInfo applicationInfo2 = packageManager.getApplicationInfo((String) arraySet2.valueAt(i2), 1048576);
                    int appId2 = UserHandle.getAppId(applicationInfo2.uid);
                    this.mPowerSaveWhitelistAppsExceptIdle.put(applicationInfo2.packageName, Integer.valueOf(appId2));
                    this.mPowerSaveWhitelistSystemAppIdsExceptIdle.put(appId2, true);
                    this.mPowerSaveWhitelistApps.put(applicationInfo2.packageName, Integer.valueOf(appId2));
                    this.mPowerSaveWhitelistSystemAppIds.put(appId2, true);
                } catch (PackageManager.NameNotFoundException unused2) {
                }
            }
            Injector injector = this.mInjector;
            MyHandler myHandler = this.mHandler;
            ContentResolver contentResolver = getContext().getContentResolver();
            if (injector.mConstants == null) {
                injector.mConstants = new Constants(myHandler, contentResolver);
            }
            this.mConstants = injector.mConstants;
            readConfigFileLocked();
            updateWhitelistAppIdsLocked();
            this.mNetworkConnected = true;
            this.mScreenOn = true;
            this.mScreenLocked = false;
            this.mCharging = true;
            moveToStateLocked(0, "boot");
            moveToLightStateLocked(0, "boot");
            this.mInactiveTimeout = this.mConstants.INACTIVE_TIMEOUT;
        }
        ?? binderService = new BinderService();
        this.mBinderService = binderService;
        publishBinderService("deviceidle", binderService);
        publishLocalService(DeviceIdleInternal.class, new LocalService());
        publishLocalService(PowerAllowlistInternal.class, new LocalPowerAllowlistService());
    }

    public final void passWhiteListsToForceAppStandbyTrackerLocked() {
        AppStateTrackerImpl appStateTrackerImpl = this.mAppStateTracker;
        int[] iArr = this.mPowerSaveWhitelistExceptIdleAppIdArray;
        int[] iArr2 = this.mPowerSaveWhitelistUserAppIdArray;
        int[] iArr3 = this.mTempWhitelistAppIdArray;
        synchronized (appStateTrackerImpl.mLock) {
            try {
                int[] iArr4 = appStateTrackerImpl.mPowerExemptAllAppIds;
                int[] iArr5 = appStateTrackerImpl.mTempExemptAppIds;
                appStateTrackerImpl.mPowerExemptAllAppIds = iArr;
                appStateTrackerImpl.mTempExemptAppIds = iArr3;
                appStateTrackerImpl.mPowerExemptUserAppIds = iArr2;
                if (AppStateTrackerImpl.isAnyAppIdUnexempt(iArr4, iArr)) {
                    AppStateTrackerImpl.MyHandler myHandler = appStateTrackerImpl.mHandler;
                    myHandler.removeMessages(4);
                    myHandler.obtainMessage(4).sendToTarget();
                } else if (!Arrays.equals(iArr4, appStateTrackerImpl.mPowerExemptAllAppIds)) {
                    AppStateTrackerImpl.MyHandler myHandler2 = appStateTrackerImpl.mHandler;
                    myHandler2.removeMessages(5);
                    myHandler2.obtainMessage(5).sendToTarget();
                }
                if (!Arrays.equals(iArr5, appStateTrackerImpl.mTempExemptAppIds)) {
                    AppStateTrackerImpl.MyHandler myHandler3 = appStateTrackerImpl.mHandler;
                    myHandler3.removeMessages(6);
                    myHandler3.obtainMessage(6).sendToTarget();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void postTempActiveTimeoutMessage(int i, long j) {
        if (DEBUG) {
            Slog.d("DeviceIdleController", "postTempActiveTimeoutMessage: uid=" + i + ", delay=" + j);
        }
        MyHandler myHandler = this.mHandler;
        myHandler.sendMessageDelayed(myHandler.obtainMessage(6, i, 0), j);
    }

    public final void readConfigFileLocked() {
        if (DEBUG) {
            Slog.d("DeviceIdleController", "Reading config from " + this.mConfigFile.getBaseFile());
        }
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
            openRead.close();
        } catch (FileNotFoundException | IOException unused3) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0042, code lost:
    
        if (r6 != 4) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0045, code lost:
    
        r6 = r12.getName();
        r7 = r6.hashCode();
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0050, code lost:
    
        if (r7 == 3797) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0055, code lost:
    
        if (r7 == 111376009) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x005a, code lost:
    
        if (r7 == 1039211927) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x007e, code lost:
    
        r6 = 65535;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0083, code lost:
    
        if (r6 == 0) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00f5, code lost:
    
        r6 = r12.getAttributeValue(null, "n");
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00f9, code lost:
    
        if (r6 == null) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00fd, code lost:
    
        r6 = r2.getApplicationInfo(r6, 4194304);
        r7 = r6.packageName;
        r6 = android.os.UserHandle.getAppId(r6.uid);
        r11.mPowerSaveWhitelistUserApps.put(r7, java.lang.Integer.valueOf(r6));
        r11.mAllowlistHistoryInfo.append(new com.android.server.DeviceIdleController.AllowlistHistoryInfo(1, null, new com.android.server.DeviceIdleController.TargetPkg(r6, r7)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0085, code lost:
    
        if (r6 == 1) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00c9, code lost:
    
        r6 = r12.getAttributeValue(null, "n");
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00d3, code lost:
    
        if (r11.mPowerSaveWhitelistApps.containsKey(r6) == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00d5, code lost:
    
        r11.mRemovedFromSystemWhitelistApps.put(r6, (java.lang.Integer) r11.mPowerSaveWhitelistApps.remove(r6));
        r11.mAllowlistHistoryInfo.append(new com.android.server.DeviceIdleController.AllowlistHistoryInfo(10, null, new com.android.server.DeviceIdleController.TargetPkg(-1, r6)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0087, code lost:
    
        if (r6 == 2) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00a6, code lost:
    
        r6 = r12.getAttributeValue(null, "package");
        r7 = r12.getAttributeValue(null, "reason");
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00b4, code lost:
    
        if (r6 != null) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00bb, code lost:
    
        r11.mPowerSaveWhitelistReviewedApps.add(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00c0, code lost:
    
        if (r7 != null) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00c2, code lost:
    
        r11.mPowerSaveWhitelistPrintErrorApps.add(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00b6, code lost:
    
        com.android.internal.util.jobs.XmlUtils.skipCurrentTag(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0089, code lost:
    
        android.util.Slog.w("DeviceIdleController", "Unknown element under <config>: " + r12.getName());
        com.android.internal.util.jobs.XmlUtils.skipCurrentTag(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0064, code lost:
    
        if (r6.equals("reviewed-in-power-save") == false) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0066, code lost:
    
        r6 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x006f, code lost:
    
        if (r6.equals("un-wl") == false) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0071, code lost:
    
        r6 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x007a, code lost:
    
        if (r6.equals("wl") == false) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x007c, code lost:
    
        r6 = 0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void readConfigFileLocked(org.xmlpull.v1.XmlPullParser r12) {
        /*
            Method dump skipped, instructions count: 384
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.DeviceIdleController.readConfigFileLocked(org.xmlpull.v1.XmlPullParser):void");
    }

    public final void receivedGenericLocationLocked(Location location) {
        if (this.mState != 4) {
            cancelLocatingLocked();
            return;
        }
        if (DEBUG) {
            Slog.d("DeviceIdleController", "Generic location: " + location);
        }
        this.mLastGenericLocation = new Location(location);
        if (location.getAccuracy() <= this.mConstants.LOCATION_ACCURACY || !this.mHasGps) {
            this.mLocated = true;
            if (this.mNotMoving) {
                stepIdleStateLocked("s:location");
            }
        }
    }

    public final void receivedGpsLocationLocked(Location location) {
        if (this.mState != 4) {
            cancelLocatingLocked();
            return;
        }
        if (DEBUG) {
            Slog.d("DeviceIdleController", "GPS location: " + location);
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

    public void registerStationaryListener(DeviceIdleInternal.StationaryListener stationaryListener) {
        synchronized (this) {
            try {
                if (this.mStationaryListeners.add(stationaryListener)) {
                    this.mHandler.obtainMessage(7, stationaryListener).sendToTarget();
                    if (!this.mMotionListener.active) {
                        startMonitoringMotionLocked();
                        scheduleMotionTimeoutAlarmLocked();
                    } else if (!isStationaryLocked() && this.mStationaryListeners.size() == 1) {
                        scheduleMotionTimeoutAlarmLocked();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removePowerSaveTempAllowlistAppChecked(int i, String str) {
        getContext().enforceCallingOrSelfPermission("android.permission.CHANGE_DEVICE_IDLE_TEMP_WHITELIST", "No permission to change device idle whitelist");
        int handleIncomingUser = ActivityManager.getService().handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, false, "removePowerSaveTempWhitelistApp", (String) null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            removePowerSaveTempWhitelistAppDirectInternal(getContext().getPackageManager().getPackageUidAsUser(str, handleIncomingUser));
        } catch (PackageManager.NameNotFoundException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void removePowerSaveTempWhitelistAppDirectInternal(int i) {
        int appId = UserHandle.getAppId(i);
        synchronized (this) {
            try {
                int indexOfKey = this.mTempWhitelistAppIdEndTimes.indexOfKey(appId);
                if (indexOfKey < 0) {
                    return;
                }
                String str = (String) ((Pair) this.mTempWhitelistAppIdEndTimes.valueAt(indexOfKey)).second;
                this.mTempWhitelistAppIdEndTimes.removeAt(indexOfKey);
                onAppRemovedFromTempWhitelistLocked(i, str);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean removePowerSaveWhitelistAppInternal(String str, int i, TargetPkg targetPkg) {
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
            this.mAllowlistHistoryInfo.append(new AllowlistHistoryInfo(i, targetPkg, new TargetPkg(i2, str)));
            reportPowerSaveWhitelistChangedLocked();
            updateWhitelistAppIdsLocked();
            writeConfigFileLocked();
            Counter.logIncrement("battery.value_app_removed_from_power_allowlist");
            return true;
        }
    }

    public final boolean removeSystemPowerWhitelistAppInternal(String str, int i, TargetPkg targetPkg) {
        synchronized (this) {
            try {
                int i2 = 0;
                if (!this.mPowerSaveWhitelistApps.containsKey(str)) {
                    return false;
                }
                this.mRemovedFromSystemWhitelistApps.put(str, (Integer) this.mPowerSaveWhitelistApps.remove(str));
                try {
                    i2 = UserHandle.getAppId(getContext().getPackageManager().getApplicationInfo(str, 1048576).uid);
                } catch (PackageManager.NameNotFoundException unused) {
                    Slog.e("DeviceIdleController", "Tried to get uid from package name but failed");
                }
                this.mAllowlistHistoryInfo.append(new AllowlistHistoryInfo(i, targetPkg, new TargetPkg(i2, str)));
                reportPowerSaveWhitelistChangedLocked();
                updateWhitelistAppIdsLocked();
                writeConfigFileLocked();
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void reportPowerSaveWhitelistChangedLocked() {
        getContext().sendBroadcastAsUser(this.mPowerSaveWhitelistChangedIntent, UserHandle.SYSTEM, null, this.mPowerSaveWhitelistChangedOptions);
    }

    public final void resetIdleManagementLocked() {
        this.mNextIdlePendingDelay = 0L;
        this.mNextIdleDelay = 0L;
        this.mQuickDozeActivatedWhileIdling = false;
        cancelAlarmLocked();
        cancelSensingTimeoutAlarmLocked();
        cancelLocatingLocked();
        maybeStopMonitoringMotionLocked();
        this.mAnyMotionDetector.stop();
        updateActiveConstraintsLocked();
    }

    public final void resetPowerSaveWhitelistExceptIdleInternal() {
        synchronized (this) {
            try {
                if (this.mPowerSaveWhitelistAppsExceptIdle.removeAll(this.mPowerSaveWhitelistUserAppsExceptIdle)) {
                    reportPowerSaveWhitelistChangedLocked();
                    this.mPowerSaveWhitelistExceptIdleAppIdArray = buildAppIdArray(this.mPowerSaveWhitelistAppsExceptIdle, this.mPowerSaveWhitelistUserApps, this.mPowerSaveWhitelistExceptIdleAppIds);
                    this.mPowerSaveWhitelistUserAppsExceptIdle.clear();
                    passWhiteListsToForceAppStandbyTrackerLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void resetSystemPowerWhitelistInternal() {
        synchronized (this) {
            this.mPowerSaveWhitelistApps.putAll(this.mRemovedFromSystemWhitelistApps);
            this.mRemovedFromSystemWhitelistApps.clear();
            reportPowerSaveWhitelistChangedLocked();
            updateWhitelistAppIdsLocked();
            writeConfigFileLocked();
        }
    }

    public final boolean restoreSystemPowerWhitelistAppInternal(String str, int i, TargetPkg targetPkg) {
        synchronized (this) {
            try {
                int i2 = 0;
                if (!this.mRemovedFromSystemWhitelistApps.containsKey(str)) {
                    return false;
                }
                this.mPowerSaveWhitelistApps.put(str, (Integer) this.mRemovedFromSystemWhitelistApps.remove(str));
                try {
                    i2 = UserHandle.getAppId(getContext().getPackageManager().getApplicationInfo(str, 1048576).uid);
                } catch (PackageManager.NameNotFoundException unused) {
                    Slog.e("DeviceIdleController", "Tried to get uid from package name but failed");
                }
                this.mAllowlistHistoryInfo.append(new AllowlistHistoryInfo(i, targetPkg, new TargetPkg(i2, str)));
                reportPowerSaveWhitelistChangedLocked();
                updateWhitelistAppIdsLocked();
                writeConfigFileLocked();
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void scheduleAlarmLocked(long r19) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            boolean r3 = com.android.server.DeviceIdleController.DEBUG
            java.lang.String r4 = "scheduleAlarmLocked("
            java.lang.String r5 = "DeviceIdleController"
            if (r3 == 0) goto L28
            java.lang.String r3 = ", "
            java.lang.StringBuilder r3 = com.android.server.BatteryService$$ExternalSyntheticOutline0.m(r4, r1, r3)
            int r6 = r0.mState
            java.lang.String r6 = stateToString(r6)
            r3.append(r6)
            java.lang.String r6 = ")"
            r3.append(r6)
            java.lang.String r3 = r3.toString()
            android.util.Slog.d(r5, r3)
        L28:
            boolean r3 = r0.mUseMotionSensor
            r6 = 5
            if (r3 == 0) goto L3c
            android.hardware.Sensor r3 = r0.mMotionSensor
            if (r3 != 0) goto L3c
            int r3 = r0.mState
            r7 = 7
            if (r3 == r7) goto L3c
            if (r3 == r6) goto L3c
            r7 = 6
            if (r3 == r7) goto L3c
            return
        L3c:
            long r7 = android.os.SystemClock.elapsedRealtime()
            long r11 = r7 + r1
            r0.mNextAlarmTime = r11
            int r3 = r0.mState
            if (r3 != r6) goto L55
            android.app.AlarmManager r9 = r0.mAlarmManager
            android.app.AlarmManager$OnAlarmListener r14 = r0.mDeepAlarmListener
            com.android.server.DeviceIdleController$MyHandler r15 = r0.mHandler
            r10 = 2
            java.lang.String r13 = "DeviceIdleController.deep.maintenance"
            r9.setIdleUntil(r10, r11, r13, r14, r15)
            goto L8b
        L55:
            r6 = 4
            if (r3 != r6) goto L65
            android.app.AlarmManager r9 = r0.mAlarmManager
            android.app.AlarmManager$OnAlarmListener r14 = r0.mDeepAlarmListener
            com.android.server.DeviceIdleController$MyHandler r15 = r0.mHandler
            r10 = 2
            java.lang.String r13 = "DeviceIdleController.deep.idle"
            r9.setExact(r10, r11, r13, r14, r15)
            goto L8b
        L65:
            com.android.server.DeviceIdleController$Constants r3 = r0.mConstants
            boolean r6 = r3.USE_WINDOW_ALARMS
            if (r6 == 0) goto L7f
            android.app.AlarmManager r9 = r0.mAlarmManager
            long r13 = r3.FLEX_TIME_SHORT
            android.app.AlarmManager$OnAlarmListener r3 = r0.mDeepAlarmListener
            com.android.server.DeviceIdleController$MyHandler r6 = r0.mHandler
            r10 = 2
            java.lang.String r15 = "DeviceIdleController.deep.progression"
            r16 = r3
            r17 = r6
            r9.setWindow(r10, r11, r13, r15, r16, r17)
            r3 = 1
            goto L8c
        L7f:
            android.app.AlarmManager r9 = r0.mAlarmManager
            android.app.AlarmManager$OnAlarmListener r14 = r0.mDeepAlarmListener
            com.android.server.DeviceIdleController$MyHandler r15 = r0.mHandler
            r10 = 2
            java.lang.String r13 = "DeviceIdleController.deep.progression"
            r9.set(r10, r11, r13, r14, r15)
        L8b:
            r3 = 0
        L8c:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>(r4)
            r6.append(r1)
            if (r3 == 0) goto La9
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "/"
            r1.<init>(r2)
            com.android.server.DeviceIdleController$Constants r0 = r0.mConstants
            long r2 = r0.FLEX_TIME_SHORT
            r1.append(r2)
            java.lang.String r0 = r1.toString()
            goto Lab
        La9:
            java.lang.String r0 = ""
        Lab:
            java.lang.String r1 = ", wakeup=true)"
            com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m(r6, r0, r1, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.DeviceIdleController.scheduleAlarmLocked(long):void");
    }

    public final void scheduleLightAlarmLocked(long j, long j2, boolean z) {
        StringBuilder sb = new StringBuilder("scheduleLightAlarmLocked(");
        sb.append(j);
        sb.append(this.mConstants.USE_WINDOW_ALARMS ? DeviceIdleController$$ExternalSyntheticOutline0.m(j2, "/") : "");
        sb.append(", wakeup=");
        sb.append(z);
        sb.append(")");
        Slog.d("DeviceIdleController", sb.toString());
        this.mInjector.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime() + j;
        this.mNextLightAlarmTime = elapsedRealtime;
        if (this.mConstants.USE_WINDOW_ALARMS) {
            this.mAlarmManager.setWindow(z ? 2 : 3, elapsedRealtime, j2, "DeviceIdleController.light", this.mLightAlarmListener, this.mHandler);
        } else {
            this.mAlarmManager.set(z ? 2 : 3, elapsedRealtime, "DeviceIdleController.light", this.mLightAlarmListener, this.mHandler);
        }
    }

    public final void scheduleMotionTimeoutAlarmLocked() {
        if (DEBUG) {
            Slog.d("DeviceIdleController", "scheduleMotionAlarmLocked");
        }
        this.mInjector.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Constants constants = this.mConstants;
        long j = elapsedRealtime + constants.MOTION_INACTIVE_TIMEOUT;
        if (constants.USE_WINDOW_ALARMS) {
            this.mAlarmManager.setWindow(2, j, constants.MOTION_INACTIVE_TIMEOUT_FLEX, "DeviceIdleController.motion", this.mMotionTimeoutAlarmListener, this.mHandler);
        } else {
            this.mAlarmManager.set(2, j, "DeviceIdleController.motion", this.mMotionTimeoutAlarmListener, this.mHandler);
        }
    }

    public void scheduleReportActiveLocked(String str, int i) {
        MyHandler myHandler = this.mHandler;
        myHandler.sendMessage(myHandler.obtainMessage(5, i, 0, str));
    }

    public void setActiveIdleOpsForTest(int i) {
        synchronized (this) {
            this.mActiveIdleOpCount = i;
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

    public void setLightStateForTest(int i) {
        synchronized (this) {
            this.mLightBatteryLevel.curr = getBatteryLevelRaw();
            this.mLightBatteryLevel.currTime = SystemClock.elapsedRealtime();
            String lightStateToString = lightStateToString(this.mLightState);
            String lightStateToString2 = lightStateToString(i);
            String floatToString = floatToString(this.mLightBatteryLevel.curr);
            BatteryLevel batteryLevel = this.mLightBatteryLevel;
            String floatToString2 = floatToString(getBatteryLevelDiff(batteryLevel.prev, batteryLevel.curr));
            BatteryLevel batteryLevel2 = this.mLightBatteryLevel;
            Slog.i("DeviceIdleController", String.format("[LIGHT] %s to %s, battery=%s(%s/%d)", lightStateToString, lightStateToString2, floatToString, floatToString2, Long.valueOf(getDuration(batteryLevel2.prevTime, batteryLevel2.currTime))));
            BatteryLevel batteryLevel3 = this.mLightBatteryLevel;
            batteryLevel3.prev = batteryLevel3.curr;
            batteryLevel3.prevTime = batteryLevel3.currTime;
            this.mLightState = i;
        }
    }

    public final void startMonitoringMotionLocked() {
        boolean registerListener;
        Slog.d("DeviceIdleController", "startMonitoringMotionLocked()");
        if (this.mMotionSensor != null) {
            MotionListener motionListener = this.mMotionListener;
            if (motionListener.active) {
                return;
            }
            if (DeviceIdleController.this.mMotionSensor.getReportingMode() == 2) {
                DeviceIdleController deviceIdleController = DeviceIdleController.this;
                registerListener = deviceIdleController.mSensorManager.requestTriggerSensor(deviceIdleController.mMotionListener, deviceIdleController.mMotionSensor);
            } else {
                DeviceIdleController deviceIdleController2 = DeviceIdleController.this;
                registerListener = deviceIdleController2.mSensorManager.registerListener(deviceIdleController2.mMotionListener, deviceIdleController2.mMotionSensor, 3);
            }
            if (registerListener) {
                motionListener.active = true;
                DeviceIdleController.this.mInjector.getClass();
                motionListener.activatedTimeElapsed = SystemClock.elapsedRealtime();
            } else {
                Slog.e("DeviceIdleController", "Unable to register for " + DeviceIdleController.this.mMotionSensor);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x00a4, code lost:
    
        if (r5 == false) goto L35;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void stepIdleStateLocked(java.lang.String r21) {
        /*
            Method dump skipped, instructions count: 794
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.DeviceIdleController.stepIdleStateLocked(java.lang.String):void");
    }

    public void stepLightIdleStateLocked(String str) {
        int i = this.mLightState;
        if (i == 0 || i == 7) {
            return;
        }
        if (DEBUG) {
            Slog.d("DeviceIdleController", "stepLightIdleStateLocked: mLightState=" + lightStateToString(this.mLightState));
        }
        EventLog.writeEvent(34010, new Object[0]);
        if (this.mEmergencyCallListener.mIsEmergencyCallActive) {
            Slog.wtf("DeviceIdleController", "stepLightIdleStateLocked called when emergency call is active");
            if (this.mLightState != 0) {
                becomeActiveLocked(Process.myUid(), "emergency");
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
                if (!this.mNetworkConnected && i2 != 5) {
                    scheduleLightAlarmLocked(this.mNextLightIdleDelay, this.mNextLightIdleDelayFlex / 2, true);
                    moveToLightStateLocked(5, str);
                    return;
                }
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
            if (i2 != 6) {
                return;
            }
        }
        if (this.mMaintenanceStartTime != 0) {
            long elapsedRealtime = SystemClock.elapsedRealtime() - this.mMaintenanceStartTime;
            long j4 = this.mConstants.LIGHT_IDLE_MAINTENANCE_MIN_BUDGET;
            if (elapsedRealtime < j4) {
                this.mCurLightIdleBudget = (j4 - elapsedRealtime) + this.mCurLightIdleBudget;
            } else {
                this.mCurLightIdleBudget -= elapsedRealtime - j4;
            }
        }
        this.mMaintenanceStartTime = 0L;
        scheduleLightAlarmLocked(this.mNextLightIdleDelay, this.mNextLightIdleDelayFlex, false);
        Constants constants3 = this.mConstants;
        if (constants3.LIGHT_IDLE_INCREASE_LINEARLY) {
            this.mNextLightIdleDelay = Math.min(constants3.LIGHT_MAX_IDLE_TIMEOUT, this.mNextLightIdleDelay + constants3.LIGHT_IDLE_LINEAR_INCREASE_FACTOR_MS);
            Constants constants4 = this.mConstants;
            this.mNextLightIdleDelayFlex = Math.min(constants4.LIGHT_IDLE_TIMEOUT_MAX_FLEX, this.mNextLightIdleDelayFlex + constants4.LIGHT_IDLE_FLEX_LINEAR_INCREASE_FACTOR_MS);
        } else {
            this.mNextLightIdleDelay = Math.min(constants3.LIGHT_MAX_IDLE_TIMEOUT, (long) (this.mNextLightIdleDelay * constants3.LIGHT_IDLE_FACTOR));
            Constants constants5 = this.mConstants;
            this.mNextLightIdleDelayFlex = Math.min(constants5.LIGHT_IDLE_TIMEOUT_MAX_FLEX, (long) (this.mNextLightIdleDelayFlex * constants5.LIGHT_IDLE_FACTOR));
        }
        moveToLightStateLocked(4, str);
        addEvent(2, null);
        this.mGoingIdleWakeLock.acquire();
        this.mHandler.sendEmptyMessage(3);
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

    public final void updateChargingLocked(boolean z) {
        if (DEBUG) {
            Slog.i("DeviceIdleController", "updateChargingLocked: charging=" + z);
        }
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
            becomeActiveLocked(Process.myUid(), "charging");
        }
    }

    public final void updateConnectivityState(Intent intent) {
        ConnectivityManager connectivityManager;
        synchronized (this) {
            Injector injector = this.mInjector;
            if (injector.mConnectivityManager == null) {
                injector.mConnectivityManager = (ConnectivityManager) injector.mContext.getSystemService(ConnectivityManager.class);
            }
            connectivityManager = injector.mConnectivityManager;
        }
        if (connectivityManager == null) {
            return;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        synchronized (this) {
            boolean z = false;
            try {
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateInteractivityLocked() {
        boolean isInteractive = this.mPowerManager.isInteractive();
        if (DEBUG) {
            DeviceIdleController$$ExternalSyntheticOutline0.m("updateInteractivityLocked: screenOn=", "DeviceIdleController", isInteractive);
        }
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
            becomeActiveLocked(Process.myUid(), KnoxCustomManagerService.SCREEN);
        }
    }

    public final void updateQuickDozeFlagLocked() {
        if (this.mConstants.USE_MODE_MANAGER) {
            updateQuickDozeFlagLocked(this.mModeManagerRequestedQuickDoze || this.mBatterySaverEnabled);
        } else {
            updateQuickDozeFlagLocked(this.mBatterySaverEnabled);
        }
    }

    public void updateQuickDozeFlagLocked(boolean z) {
        int i;
        if (DEBUG) {
            Slog.i("DeviceIdleController", "updateQuickDozeFlagLocked: enabled=" + z);
        }
        this.mQuickDozeActivated = z;
        this.mQuickDozeActivatedWhileIdling = z && ((i = this.mState) == 5 || i == 6);
        if (z) {
            becomeInactiveIfAppropriateLocked();
        }
    }

    public final void updateTempWhitelistAppIdsLocked(int i, long j, int i2, boolean z, int i3, String str, int i4) {
        int size = this.mTempWhitelistAppIdEndTimes.size();
        if (this.mTempWhitelistAppIdArray.length != size) {
            this.mTempWhitelistAppIdArray = new int[size];
        }
        for (int i5 = 0; i5 < size; i5++) {
            this.mTempWhitelistAppIdArray[i5] = this.mTempWhitelistAppIdEndTimes.keyAt(i5);
        }
        ActivityManagerInternal activityManagerInternal = this.mLocalActivityManager;
        boolean z2 = DEBUG;
        if (activityManagerInternal != null) {
            if (z2) {
                Slog.d("DeviceIdleController", "Setting activity manager temp whitelist to " + Arrays.toString(this.mTempWhitelistAppIdArray));
            }
            this.mLocalActivityManager.updateDeviceIdleTempAllowlist(this.mTempWhitelistAppIdArray, i, z, j, i2, i3, str, i4);
        }
        if (this.mLocalPowerManager != null) {
            if (z2) {
                Slog.d("DeviceIdleController", "Setting wakelock temp whitelist to " + Arrays.toString(this.mTempWhitelistAppIdArray));
            }
            this.mLocalPowerManager.setDeviceIdleTempWhitelist(this.mTempWhitelistAppIdArray);
        }
        passWhiteListsToForceAppStandbyTrackerLocked();
    }

    public final void updateWhitelistAppIdsLocked() {
        this.mPowerSaveWhitelistExceptIdleAppIdArray = buildAppIdArray(this.mPowerSaveWhitelistAppsExceptIdle, this.mPowerSaveWhitelistUserApps, this.mPowerSaveWhitelistExceptIdleAppIds);
        this.mPowerSaveWhitelistAllAppIdArray = buildAppIdArray(this.mPowerSaveWhitelistApps, this.mPowerSaveWhitelistUserApps, this.mPowerSaveWhitelistAllAppIds);
        this.mPowerSaveWhitelistUserAppIdArray = buildAppIdArray(null, this.mPowerSaveWhitelistUserApps, this.mPowerSaveWhitelistUserAppIds);
        ActivityManagerInternal activityManagerInternal = this.mLocalActivityManager;
        if (activityManagerInternal != null) {
            activityManagerInternal.setDeviceIdleAllowlist(this.mPowerSaveWhitelistAllAppIdArray, this.mPowerSaveWhitelistExceptIdleAppIdArray);
        }
        if (this.mLocalPowerManager != null) {
            if (DEBUG) {
                Slog.d("DeviceIdleController", "Setting wakelock whitelist to " + Arrays.toString(this.mPowerSaveWhitelistAllAppIdArray));
            }
            this.mLocalPowerManager.setDeviceIdleWhitelist(this.mPowerSaveWhitelistAllAppIdArray);
        }
        passWhiteListsToForceAppStandbyTrackerLocked();
    }

    public final void writeConfigFileLocked() {
        MyHandler myHandler = this.mHandler;
        myHandler.removeMessages(1);
        myHandler.sendEmptyMessageDelayed(1, 5000L);
    }

    public final void writeConfigFileLocked(FastXmlSerializer fastXmlSerializer) {
        fastXmlSerializer.startDocument(null, Boolean.TRUE);
        fastXmlSerializer.startTag(null, "config");
        for (int i = 0; i < this.mPowerSaveWhitelistUserApps.size(); i++) {
            String str = (String) this.mPowerSaveWhitelistUserApps.keyAt(i);
            fastXmlSerializer.startTag(null, "wl");
            fastXmlSerializer.attribute(null, "n", str);
            fastXmlSerializer.endTag(null, "wl");
        }
        for (int i2 = 0; i2 < this.mRemovedFromSystemWhitelistApps.size(); i2++) {
            fastXmlSerializer.startTag(null, "un-wl");
            fastXmlSerializer.attribute(null, "n", (String) this.mRemovedFromSystemWhitelistApps.keyAt(i2));
            fastXmlSerializer.endTag(null, "un-wl");
        }
        fastXmlSerializer.endTag(null, "config");
        fastXmlSerializer.endDocument();
    }
}
