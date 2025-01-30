package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.notification.ZenModeConfig;
import android.telecom.TelecomManager;
import android.util.ArrayMap;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.p016qs.tiles.SRotationLockTile;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.privacy.PrivacyItem;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.privacy.PrivacyType;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.CastControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DataSaverController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.HotspotController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.statusbar.policy.LocationControllerImpl;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.statusbar.policy.SBluetoothController;
import com.android.systemui.statusbar.policy.SBluetoothControllerImpl;
import com.android.systemui.statusbar.policy.SensorPrivacyController;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.RingerModeTracker;
import com.android.systemui.util.RingerModeTrackerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.time.DateFormatUtil;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PhoneStatusBarPolicy implements SBluetoothController.SCallback, CommandQueue.Callbacks, RotationLockController.RotationLockControllerCallback, DataSaverController.Listener, ZenModeController.Callback, DeviceProvisionedController.DeviceProvisionedListener, KeyguardStateController.Callback, PrivacyItemController.Callback, LocationController.LocationChangeCallback, RecordingController.RecordingStateChangeCallback {
    public static final boolean DEBUG = Log.isLoggable("PhoneStatusBarPolicy", 3);
    public static final int LOCATION_STATUS_ICON_ID = PrivacyType.TYPE_LOCATION.getIconId();
    public final ActivityManager mActivityManager;
    public final AlarmManager mAlarmManager;
    public final BatteryController mBatteryController;
    public final SBluetoothController mBluetooth;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final CastController mCast;
    public final CommandQueue mCommandQueue;
    public final ConfigurationController mConfigurationController;
    public boolean mCurrentUserSetup;
    public final DataSaverController mDataSaver;
    public final DevicePolicyManager mDevicePolicyManager;
    public final int mDisplayId;
    public final Handler mHandler;
    public final HotspotController mHotspot;
    public final StatusBarIconController mIconController;
    public final KeyguardStateController mKeyguardStateController;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public final LocationController mLocationController;
    public final Executor mMainExecutor;
    public boolean mMuteVisible;
    public final NextAlarmController mNextAlarmController;
    public final PrivacyItemController mPrivacyItemController;
    public final PrivacyLogger mPrivacyLogger;
    public final DeviceProvisionedController mProvisionedController;
    public final RecordingController mRecordingController;
    public final Resources mResources;
    public final RingerModeTracker mRingerModeTracker;
    public final RotationLockController mRotationLockController;
    public final SensorPrivacyController mSensorPrivacyController;
    public final SettingsHelper mSettingsHelper;
    public final String mSlotAlarmClock;
    public final String mSlotBTTethering;
    public final String mSlotBluetooth;
    public final String mSlotBluetoothConnected;
    public final String mSlotCamera;
    public final String mSlotCast;
    public final String mSlotDataSaver;
    public final String mSlotHeadset;
    public final String mSlotHotspot;
    public final String mSlotLocation;
    public final String mSlotManagedProfile;
    public final String mSlotMicrophone;
    public final String mSlotMute;
    public final String mSlotPowerSave;
    public final String mSlotRotate;
    public final String mSlotScreenRecord;
    public final String mSlotSensorsOff;
    public final String mSlotTty;
    public final String mSlotVibrate;
    public final String mSlotZen;
    public final TelecomManager mTelecomManager;
    public final Executor mUiBgExecutor;
    public final UserInfoController mUserInfoController;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public boolean mVibrateVisible;
    public final ZenModeController mZenController;
    public boolean mManagedProfileIconVisible = false;
    public NotificationManager mNotificationManager = null;
    public final C30861 mProcessListener = new ActivityManager.SemProcessListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.1
        public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            ActivityManager.RunningTaskInfo runningTask = ActivityManagerWrapper.sInstance.getRunningTask();
            if (runningTask == null || runningTask.topActivityInfo == null) {
                return;
            }
            PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
            if (phoneStatusBarPolicy.mDisplayId == runningTask.displayId) {
                phoneStatusBarPolicy.updateManagedProfile();
            }
        }

        public final void onProcessDied(int i, int i2) {
        }
    };
    public final UserTracker.Callback mUserSwitchListener = new C30902();
    public final C30913 mHotspotCallback = new HotspotController.Callback() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.3
        @Override // com.android.systemui.statusbar.policy.HotspotController.Callback
        public final void onHotspotChanged(int i, boolean z) {
            PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
            ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotHotspot, z);
        }

        @Override // com.android.systemui.statusbar.policy.HotspotController.Callback
        public final void onHotspotPrepared() {
        }

        @Override // com.android.systemui.statusbar.policy.HotspotController.Callback
        public final void onUpdateConnectedDevices() {
        }
    };
    public final C30924 mCastCallback = new CastController.Callback() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.4
        @Override // com.android.systemui.statusbar.policy.CastController.Callback
        public final void onCastDevicesChanged() {
            boolean z;
            boolean z2;
            PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
            Iterator it = ((ArrayList) ((CastControllerImpl) phoneStatusBarPolicy.mCast).getCastDevices()).iterator();
            while (it.hasNext()) {
                int i = ((CastController.CastDevice) it.next()).state;
                if (i == 1 || i == 2) {
                    z = true;
                    break;
                }
            }
            z = false;
            Handler handler = phoneStatusBarPolicy.mHandler;
            RunnableC30968 runnableC30968 = phoneStatusBarPolicy.mRemoveCastIconRunnable;
            handler.removeCallbacks(runnableC30968);
            if (z) {
                RecordingController recordingController = phoneStatusBarPolicy.mRecordingController;
                synchronized (recordingController) {
                    z2 = recordingController.mIsRecording;
                }
                if (!z2) {
                    String string = phoneStatusBarPolicy.mResources.getString(R.string.accessibility_casting);
                    StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController;
                    String str = phoneStatusBarPolicy.mSlotCast;
                    statusBarIconControllerImpl.setIcon(string, str, R.drawable.stat_sys_cast);
                    statusBarIconControllerImpl.setIconVisibility(str, true);
                    return;
                }
            }
            handler.postDelayed(runnableC30968, 3000L);
        }
    };
    public final C30935 mNextAlarmCallback = new NextAlarmController.NextAlarmChangeCallback() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.5
        @Override // com.android.systemui.statusbar.policy.NextAlarmController.NextAlarmChangeCallback
        public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
            PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
            boolean z = PhoneStatusBarPolicy.DEBUG;
            phoneStatusBarPolicy.getClass();
            PhoneStatusBarPolicy.this.updateAlarm();
        }
    };
    public final C30946 mSensorPrivacyListener = new C30946();
    public final C30957 mIntentReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.7
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            action.getClass();
            switch (action) {
                case "android.intent.action.HEADSET_PLUG":
                    PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
                    boolean z = PhoneStatusBarPolicy.DEBUG;
                    phoneStatusBarPolicy.getClass();
                    boolean z2 = intent.getIntExtra("state", 0) != 0;
                    boolean z3 = intent.getIntExtra("microphone", 0) != 0;
                    String str = phoneStatusBarPolicy.mSlotHeadset;
                    StatusBarIconController statusBarIconController = phoneStatusBarPolicy.mIconController;
                    if (!z2) {
                        ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, false);
                        break;
                    } else {
                        StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) statusBarIconController;
                        statusBarIconControllerImpl.setIcon(phoneStatusBarPolicy.mResources.getString(z3 ? R.string.accessibility_status_bar_headset : R.string.accessibility_status_bar_headphones), str, z3 ? R.drawable.stat_sys_headset_mic : R.drawable.stat_sys_headset);
                        statusBarIconControllerImpl.setIconVisibility(str, true);
                        break;
                    }
                case "android.intent.action.MANAGED_PROFILE_UNAVAILABLE":
                case "android.intent.action.MANAGED_PROFILE_AVAILABLE":
                case "android.intent.action.MANAGED_PROFILE_REMOVED":
                    PhoneStatusBarPolicy phoneStatusBarPolicy2 = PhoneStatusBarPolicy.this;
                    boolean z4 = PhoneStatusBarPolicy.DEBUG;
                    phoneStatusBarPolicy2.updateManagedProfile();
                    break;
                case "android.intent.action.SIM_STATE_CHANGED":
                    intent.getBooleanExtra("rebroadcastOnUnlock", false);
                    break;
                case "android.intent.action.LOCALE_CHANGED":
                case "android.app.action.NOTIFICATION_POLICY_CHANGED":
                    PhoneStatusBarPolicy phoneStatusBarPolicy3 = PhoneStatusBarPolicy.this;
                    boolean z5 = PhoneStatusBarPolicy.DEBUG;
                    phoneStatusBarPolicy3.updateVolumeZen();
                    break;
                case "android.intent.action.TIMEZONE_CHANGED":
                case "android.intent.action.TIME_SET":
                case "android.intent.action.DATE_CHANGED":
                    PhoneStatusBarPolicy phoneStatusBarPolicy4 = PhoneStatusBarPolicy.this;
                    boolean z6 = PhoneStatusBarPolicy.DEBUG;
                    phoneStatusBarPolicy4.updateVolumeZen();
                    break;
                case "android.telecom.action.CURRENT_TTY_MODE_CHANGED":
                    PhoneStatusBarPolicy phoneStatusBarPolicy5 = PhoneStatusBarPolicy.this;
                    int intExtra = intent.getIntExtra("android.telecom.extra.CURRENT_TTY_MODE", 0);
                    boolean z7 = PhoneStatusBarPolicy.DEBUG;
                    phoneStatusBarPolicy5.updateTTY(intExtra);
                    break;
                case "com.android.systemui.action.dnd_off":
                    Bundle extras = intent.getExtras();
                    if (extras != null) {
                        String[] split = context.getPackageManager().getNameForUid(extras.getInt(NetworkAnalyticsConstants.DataPoints.UID)).split(":");
                        if (split.length != 0 && split[0].equals("android.uid.systemui")) {
                            PhoneStatusBarPolicy.this.mNotificationManager.setZenMode(0, null, SubRoom.EXTRA_VALUE_NOTIFICATION);
                            break;
                        }
                    }
                    break;
            }
        }
    };
    public final RunnableC30968 mRemoveCastIconRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.8
        @Override // java.lang.Runnable
        public final void run() {
            boolean z = PhoneStatusBarPolicy.DEBUG;
            PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
            ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotCast, false);
        }
    };
    public final C30979 mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.9
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onLocaleListChanged() {
            PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
            Resources resources = phoneStatusBarPolicy.mResources;
            String string = resources.getString(R.string.status_bar_alarm);
            StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController;
            statusBarIconControllerImpl.setIconContentDescription(string, phoneStatusBarPolicy.mSlotAlarmClock);
            statusBarIconControllerImpl.setIconContentDescription(resources.getString(R.string.accessibility_ringer_vibrate), phoneStatusBarPolicy.mSlotVibrate);
            statusBarIconControllerImpl.setIconContentDescription(resources.getString(R.string.accessibility_ringer_silent), phoneStatusBarPolicy.mSlotMute);
            statusBarIconControllerImpl.setIconContentDescription(resources.getString(R.string.accessibility_status_bar_hotspot), phoneStatusBarPolicy.mSlotHotspot);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mDevicePolicyManager.getResources().getString("SystemUi.STATUS_BAR_WORK_ICON_ACCESSIBILITY", new PhoneStatusBarPolicy$$ExternalSyntheticLambda2(phoneStatusBarPolicy)), phoneStatusBarPolicy.mSlotManagedProfile);
            statusBarIconControllerImpl.setIconContentDescription(resources.getString(R.string.accessibility_data_saver_on), phoneStatusBarPolicy.mSlotDataSaver);
            statusBarIconControllerImpl.setIconContentDescription(resources.getString(R.string.ongoing_privacy_chip_content_multiple_apps, resources.getString(PrivacyType.TYPE_MICROPHONE.getNameId())), phoneStatusBarPolicy.mSlotMicrophone);
            statusBarIconControllerImpl.setIconContentDescription(resources.getString(R.string.ongoing_privacy_chip_content_multiple_apps, resources.getString(PrivacyType.TYPE_CAMERA.getNameId())), phoneStatusBarPolicy.mSlotCamera);
            statusBarIconControllerImpl.setIconContentDescription(resources.getString(R.string.accessibility_location_active), phoneStatusBarPolicy.mSlotLocation);
            statusBarIconControllerImpl.setIconContentDescription(resources.getString(R.string.accessibility_sensors_off_active), phoneStatusBarPolicy.mSlotSensorsOff);
            statusBarIconControllerImpl.setIconContentDescription(resources.getString(R.string.status_bar_power_saving_description), phoneStatusBarPolicy.mSlotPowerSave);
            statusBarIconControllerImpl.setIconContentDescription(resources.getString(R.string.accessibility_quick_settings_bluetooth_on), phoneStatusBarPolicy.mSlotBluetooth);
            statusBarIconControllerImpl.setIconContentDescription(resources.getString(R.string.accessibility_bluetooth_connected), phoneStatusBarPolicy.mSlotBluetoothConnected);
        }
    };
    public final C308710 mBatteryStateChangeCallback = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.10
        @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
        public final void onPowerSaveChanged(boolean z) {
            PhoneStatusBarPolicy.m1718$$Nest$mupdatePowerSaveState(PhoneStatusBarPolicy.this, z);
        }
    };
    public final C308811 mOnChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.11
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            if (uri == null || !uri.equals(Settings.System.getUriFor("emergency_mode"))) {
                return;
            }
            PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
            PhoneStatusBarPolicy.m1718$$Nest$mupdatePowerSaveState(phoneStatusBarPolicy, ((BatteryControllerImpl) phoneStatusBarPolicy.mBatteryController).mPowerSave);
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$12 */
    public abstract /* synthetic */ class AbstractC308912 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$privacy$PrivacyType;

        static {
            int[] iArr = new int[PrivacyType.values().length];
            $SwitchMap$com$android$systemui$privacy$PrivacyType = iArr;
            try {
                iArr[PrivacyType.TYPE_CAMERA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$privacy$PrivacyType[PrivacyType.TYPE_LOCATION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$privacy$PrivacyType[PrivacyType.TYPE_MICROPHONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$2 */
    public final class C30902 implements UserTracker.Callback {
        public C30902() {
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            PhoneStatusBarPolicy.this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    PhoneStatusBarPolicy.C30902 c30902 = PhoneStatusBarPolicy.C30902.this;
                    PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
                    boolean z = PhoneStatusBarPolicy.DEBUG;
                    phoneStatusBarPolicy.updateAlarm();
                    PhoneStatusBarPolicy.this.updateManagedProfile();
                    PhoneStatusBarPolicy.this.onUserSetupChanged();
                }
            });
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanging(int i) {
            PhoneStatusBarPolicy.this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ((UserInfoControllerImpl) PhoneStatusBarPolicy.this.mUserInfoController).reloadUserInfo();
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$6 */
    public final class C30946 {
        public C30946() {
        }
    }

    /* renamed from: -$$Nest$mupdatePowerSaveState, reason: not valid java name */
    public static void m1718$$Nest$mupdatePowerSaveState(PhoneStatusBarPolicy phoneStatusBarPolicy, boolean z) {
        boolean isEmergencyMode = phoneStatusBarPolicy.mSettingsHelper.isEmergencyMode();
        boolean z2 = z && !isEmergencyMode;
        EmergencyButtonController$$ExternalSyntheticOutline0.m59m("updatePowerSaveState: isPowerSave=", z, ", isEmergencyMode=", isEmergencyMode, "PhoneStatusBarPolicy");
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotPowerSave, z2);
    }

    /* JADX WARN: Type inference failed for: r2v10, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$9] */
    /* JADX WARN: Type inference failed for: r2v11, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$10] */
    /* JADX WARN: Type inference failed for: r2v12, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$11] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$1] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$3] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$4] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$5] */
    /* JADX WARN: Type inference failed for: r2v8, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$7] */
    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$8] */
    public PhoneStatusBarPolicy(StatusBarIconController statusBarIconController, CommandQueue commandQueue, BroadcastDispatcher broadcastDispatcher, Executor executor, Executor executor2, Looper looper, Resources resources, CastController castController, HotspotController hotspotController, SBluetoothController sBluetoothController, NextAlarmController nextAlarmController, UserInfoController userInfoController, RotationLockController rotationLockController, DataSaverController dataSaverController, ZenModeController zenModeController, DeviceProvisionedController deviceProvisionedController, KeyguardStateController keyguardStateController, LocationController locationController, SensorPrivacyController sensorPrivacyController, AlarmManager alarmManager, UserManager userManager, UserTracker userTracker, DevicePolicyManager devicePolicyManager, RecordingController recordingController, TelecomManager telecomManager, int i, SharedPreferences sharedPreferences, DateFormatUtil dateFormatUtil, RingerModeTracker ringerModeTracker, PrivacyItemController privacyItemController, PrivacyLogger privacyLogger, ConfigurationController configurationController, BatteryController batteryController, KnoxStateMonitor knoxStateMonitor, ActivityManager activityManager, SettingsHelper settingsHelper) {
        this.mIconController = statusBarIconController;
        this.mCommandQueue = commandQueue;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mHandler = new Handler(looper);
        this.mResources = resources;
        this.mCast = castController;
        this.mHotspot = hotspotController;
        this.mBluetooth = sBluetoothController;
        this.mNextAlarmController = nextAlarmController;
        this.mAlarmManager = alarmManager;
        this.mUserInfoController = userInfoController;
        this.mUserManager = userManager;
        this.mUserTracker = userTracker;
        this.mDevicePolicyManager = devicePolicyManager;
        this.mRotationLockController = rotationLockController;
        this.mDataSaver = dataSaverController;
        this.mZenController = zenModeController;
        this.mProvisionedController = deviceProvisionedController;
        this.mKeyguardStateController = keyguardStateController;
        this.mLocationController = locationController;
        this.mPrivacyItemController = privacyItemController;
        this.mSensorPrivacyController = sensorPrivacyController;
        this.mRecordingController = recordingController;
        this.mMainExecutor = executor;
        this.mUiBgExecutor = executor2;
        this.mTelecomManager = telecomManager;
        this.mRingerModeTracker = ringerModeTracker;
        this.mPrivacyLogger = privacyLogger;
        this.mConfigurationController = configurationController;
        this.mBatteryController = batteryController;
        this.mSettingsHelper = settingsHelper;
        this.mSlotCast = resources.getString(17042913);
        this.mSlotHotspot = resources.getString(17042922);
        this.mSlotBluetooth = resources.getString(17042908);
        this.mSlotBluetoothConnected = resources.getString(17042909);
        this.mSlotTty = resources.getString(17042948);
        this.mSlotZen = resources.getString(17042952);
        this.mSlotMute = resources.getString(17042933);
        this.mSlotVibrate = resources.getString(17042949);
        this.mSlotAlarmClock = resources.getString(17042906);
        this.mSlotManagedProfile = resources.getString(17042928);
        this.mSlotRotate = resources.getString(17042941);
        this.mSlotHeadset = resources.getString(17042921);
        this.mSlotDataSaver = resources.getString(17042917);
        this.mSlotLocation = resources.getString(17042927);
        this.mSlotMicrophone = resources.getString(17042929);
        this.mSlotCamera = resources.getString(17042912);
        this.mSlotSensorsOff = resources.getString(17042944);
        this.mSlotScreenRecord = resources.getString(17042942);
        this.mSlotPowerSave = resources.getString(17042939);
        this.mSlotBTTethering = resources.getString(17042910);
        this.mDisplayId = i;
        this.mActivityManager = activityManager;
        this.mKnoxStateMonitor = knoxStateMonitor;
    }

    public final String getApplicationNameFromPackage(String str) {
        PackageManager packageManager = ((ZenModeControllerImpl) this.mZenController).mContext.getPackageManager();
        try {
            return packageManager.getApplicationLabel(packageManager.getApplicationInfo(str, 0)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
    public final void onBluetoothDevicesChanged() {
        updateBluetooth();
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
    public final void onBluetoothStateChange(boolean z) {
        updateBluetooth();
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public final void onConfigChanged$1() {
        updateVolumeZen();
    }

    @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
    public final void onCountdown(long j) {
        if (DEBUG) {
            Log.d("PhoneStatusBarPolicy", "screenrecord: countdown " + j);
        }
        int floorDiv = (int) Math.floorDiv(j + 500, 1000);
        String num = Integer.toString(floorDiv);
        int i = floorDiv != 1 ? floorDiv != 2 ? floorDiv != 3 ? R.drawable.stat_sys_screen_record : R.drawable.stat_sys_screen_record_3 : R.drawable.stat_sys_screen_record_2 : R.drawable.stat_sys_screen_record_1;
        StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) this.mIconController;
        String str = this.mSlotScreenRecord;
        statusBarIconControllerImpl.setIcon(num, str, i);
        statusBarIconControllerImpl.setIconVisibility(str, true);
        statusBarIconControllerImpl.setIconAccessibilityLiveRegion(2, str);
    }

    @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
    public final void onCountdownEnd() {
        if (DEBUG) {
            Log.d("PhoneStatusBarPolicy", "screenrecord: hiding icon during countdown");
        }
        Handler handler = this.mHandler;
        handler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
                ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotScreenRecord, false);
            }
        });
        handler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
                ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconAccessibilityLiveRegion(0, phoneStatusBarPolicy.mSlotScreenRecord);
            }
        });
    }

    @Override // com.android.systemui.statusbar.policy.DataSaverController.Listener
    public final void onDataSaverChanged(boolean z) {
        ((StatusBarIconControllerImpl) this.mIconController).setIconVisibility(this.mSlotDataSaver, z);
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public final void onKeyguardShowingChanged() {
        updateManagedProfile();
    }

    @Override // com.android.systemui.statusbar.policy.LocationController.LocationChangeCallback
    public final void onLocationActiveChanged() {
        if (this.mPrivacyItemController.privacyConfig.locationAvailable) {
            return;
        }
        boolean z = ((LocationControllerImpl) this.mLocationController).mAreActiveLocationRequests;
        String str = this.mSlotLocation;
        StatusBarIconController statusBarIconController = this.mIconController;
        if (z) {
            ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, true);
        } else {
            ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, false);
        }
    }

    @Override // com.android.systemui.privacy.PrivacyItemController.Callback
    public final void onPrivacyItemsChanged(List list) {
        Iterator it = list.iterator();
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        while (it.hasNext()) {
            PrivacyItem privacyItem = (PrivacyItem) it.next();
            if (privacyItem == null) {
                Log.e("PhoneStatusBarPolicy", "updatePrivacyItems - null item found");
                StringWriter stringWriter = new StringWriter();
                this.mPrivacyItemController.dump(new PrintWriter(stringWriter), null);
                throw new NullPointerException(stringWriter.toString());
            }
            int i = AbstractC308912.$SwitchMap$com$android$systemui$privacy$PrivacyType[privacyItem.privacyType.ordinal()];
            if (i == 1) {
                z = true;
            } else if (i == 2) {
                z3 = true;
            } else if (i == 3) {
                z2 = true;
            }
        }
        this.mPrivacyLogger.logStatusBarIconsVisible(z, z2, z3);
    }

    @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
    public final void onRecordingEnd() {
        if (DEBUG) {
            Log.d("PhoneStatusBarPolicy", "screenrecord: hiding icon");
        }
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
                ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotScreenRecord, false);
            }
        });
    }

    @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
    public final void onRecordingStart() {
        if (DEBUG) {
            Log.d("PhoneStatusBarPolicy", "screenrecord: showing icon");
        }
        ((StatusBarIconControllerImpl) this.mIconController).setIcon(this.mResources.getString(R.string.screenrecord_ongoing_screen_only), this.mSlotScreenRecord, R.drawable.stat_sys_screen_record);
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
                ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotScreenRecord, true);
            }
        });
    }

    @Override // com.android.systemui.statusbar.policy.RotationLockController.RotationLockControllerCallback
    public final void onRotationLockStateChanged(boolean z) {
        RotationLockController rotationLockController = this.mRotationLockController;
        Resources resources = this.mResources;
        boolean isCurrentOrientationLockPortrait = SRotationLockTile.isCurrentOrientationLockPortrait(rotationLockController, resources);
        String str = this.mSlotRotate;
        StatusBarIconController statusBarIconController = this.mIconController;
        if (!z) {
            ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, false);
            return;
        }
        if (isCurrentOrientationLockPortrait) {
            ((StatusBarIconControllerImpl) statusBarIconController).setIcon(resources.getString(R.string.accessibility_rotation_lock_on_portrait), str, R.drawable.stat_sys_rotate_portrait);
        } else {
            ((StatusBarIconControllerImpl) statusBarIconController).setIcon(resources.getString(R.string.accessibility_rotation_lock_on_landscape), str, R.drawable.stat_sys_rotate_landscape);
        }
        ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, true);
    }

    @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
    public final void onUserSetupChanged() {
        boolean isCurrentUserSetup = ((DeviceProvisionedControllerImpl) this.mProvisionedController).isCurrentUserSetup();
        if (this.mCurrentUserSetup == isCurrentUserSetup) {
            return;
        }
        this.mCurrentUserSetup = isCurrentUserSetup;
        updateAlarm();
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public final void onZenChanged(int i) {
        updateVolumeZen();
    }

    public final void updateAlarm() {
        List nextAlarmClocks = this.mAlarmManager.getNextAlarmClocks(-2);
        boolean z = false;
        boolean anyMatch = (nextAlarmClocks == null || nextAlarmClocks.isEmpty()) ? false : nextAlarmClocks.stream().anyMatch(new Predicate() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean equals;
                PhoneStatusBarPolicy.this.getClass();
                PendingIntent showIntent = ((AlarmManager.AlarmClockInfo) obj).getShowIntent();
                if (showIntent == null) {
                    return false;
                }
                String creatorPackage = showIntent.getCreatorPackage();
                if ("com.sec.android.app.clockpackage".equals(creatorPackage)) {
                    equals = showIntent.getIntent() != null ? !r3.getBooleanExtra("dontShowAlarmIcon", false) : true;
                } else {
                    equals = "com.google.android.deskclock".equals(creatorPackage);
                }
                return equals;
            }
        });
        if (this.mCurrentUserSetup && anyMatch) {
            z = true;
        }
        ((StatusBarIconControllerImpl) this.mIconController).setIconVisibility(this.mSlotAlarmClock, z);
    }

    public final void updateBluetooth() {
        boolean z;
        boolean z2 = false;
        SBluetoothController sBluetoothController = this.mBluetooth;
        if (sBluetoothController != null) {
            SBluetoothControllerImpl sBluetoothControllerImpl = (SBluetoothControllerImpl) sBluetoothController;
            boolean z3 = sBluetoothControllerImpl.mEnabled;
            z = true;
            if (!(sBluetoothControllerImpl.mConnectionState == 2)) {
                z = false;
                z2 = z3;
            }
        } else {
            z = false;
        }
        StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) this.mIconController;
        statusBarIconControllerImpl.setIconVisibility(this.mSlotBluetooth, z2);
        statusBarIconControllerImpl.setIconVisibility(this.mSlotBluetoothConnected, z);
    }

    public final void updateManagedProfile() {
        this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                final PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
                phoneStatusBarPolicy.getClass();
                try {
                    final int lastResumedActivityUserId = ActivityTaskManager.getService().getLastResumedActivityUserId();
                    final boolean z = false;
                    if (phoneStatusBarPolicy.mUserManager.isManagedProfile(lastResumedActivityUserId)) {
                        if (((KnoxStateMonitorImpl) phoneStatusBarPolicy.mKnoxStateMonitor).mContainerMonitor.mUserManager.getUserInfo(lastResumedActivityUserId).isManagedProfile() ? !SemPersonaManager.isAppSeparationUserId(lastResumedActivityUserId) : false) {
                            z = true;
                        }
                    }
                    final String string = phoneStatusBarPolicy.mDevicePolicyManager.getResources().getString("SystemUi.STATUS_BAR_WORK_ICON_ACCESSIBILITY", new PhoneStatusBarPolicy$$ExternalSyntheticLambda2(phoneStatusBarPolicy));
                    phoneStatusBarPolicy.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda5
                        /* JADX WARN: Removed duplicated region for block: B:13:0x003d  */
                        /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final void run() {
                            boolean z2;
                            PhoneStatusBarPolicy phoneStatusBarPolicy2 = PhoneStatusBarPolicy.this;
                            boolean z3 = z;
                            int i = lastResumedActivityUserId;
                            String str = string;
                            String str2 = phoneStatusBarPolicy2.mSlotManagedProfile;
                            StatusBarIconController statusBarIconController = phoneStatusBarPolicy2.mIconController;
                            if (z3) {
                                KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) phoneStatusBarPolicy2.mKeyguardStateController;
                                if (!keyguardStateControllerImpl.mShowing || keyguardStateControllerImpl.mOccluded) {
                                    ((KnoxStateMonitorImpl) phoneStatusBarPolicy2.mKnoxStateMonitor).mContainerMonitor.getClass();
                                    ((StatusBarIconControllerImpl) statusBarIconController).setIcon(str, str2, SemPersonaManager.isSecureFolderId(i) ? R.drawable.ic_notification_badge : R.drawable.stat_sys_managed_profile_status);
                                    z2 = true;
                                    if (phoneStatusBarPolicy2.mManagedProfileIconVisible == z2) {
                                        ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str2, z2);
                                        phoneStatusBarPolicy2.mManagedProfileIconVisible = z2;
                                        return;
                                    }
                                    return;
                                }
                            }
                            z2 = false;
                            if (phoneStatusBarPolicy2.mManagedProfileIconVisible == z2) {
                            }
                        }
                    });
                } catch (RemoteException e) {
                    Log.w("PhoneStatusBarPolicy", "updateManagedProfile: ", e);
                }
            }
        });
    }

    public final void updateTTY(int i) {
        boolean z = i != 0;
        String str = this.mSlotTty;
        StatusBarIconController statusBarIconController = this.mIconController;
        if (!z) {
            ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, false);
        } else {
            ((StatusBarIconControllerImpl) statusBarIconController).setIcon(this.mResources.getString(R.string.accessibility_tty_enabled), str, R.drawable.stat_sys_tty_mode);
            ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, true);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:74:0x028b, code lost:
    
        if (r2.intValue() == 0) goto L106;
     */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x029b  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x02a9  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0169  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateVolumeZen() {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        ArrayMap arrayMap;
        Uri uri;
        Uri uri2;
        Log.d("PhoneStatusBarPolicy", "updateVolumeZen: NOTI_DND_ONGOING_ALERT");
        Log.d("PhoneStatusBarPolicy", " updateVolumeZenTW");
        Log.d("PhoneStatusBarPolicy", "updateVolumeZenTW: NOTI_DND_ONGOING_ALERTNOTI_DND_ONGOING_ALERTNOTI_DND_ONGOING_ALERT");
        ZenModeController zenModeController = this.mZenController;
        ZenModeControllerImpl zenModeControllerImpl = (ZenModeControllerImpl) zenModeController;
        Context context = zenModeControllerImpl.mContext;
        if (this.mNotificationManager == null) {
            this.mNotificationManager = (NotificationManager) context.getSystemService(SubRoom.EXTRA_VALUE_NOTIFICATION);
        }
        ZenModeConfig zenModeConfig = zenModeController != null ? ((ZenModeControllerImpl) zenModeController).mConfig : null;
        int i = zenModeControllerImpl.mZenMode;
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("zen:", i, "PhoneStatusBarPolicy");
        if (i != 0) {
            PendingIntent activityAsUser = PendingIntent.getActivityAsUser(context, 0, new Intent().setComponent(new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$ZenModeSettingsActivity")).setAction("android.intent.action.MAIN").setFlags(268468224), 201326592, null, UserHandle.CURRENT);
            Intent intent = new Intent();
            intent.setAction("com.android.systemui.action.dnd_off");
            intent.putExtra(NetworkAnalyticsConstants.DataPoints.UID, context.getApplicationInfo().uid);
            PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, 335544320);
            Notification.Builder builder = new Notification.Builder(context, "ZEN_ONGOING");
            Resources resources = this.mResources;
            Notification.Builder addAction = builder.setContentTitle(resources.getString(R.string.noti_dnd_title)).setSmallIcon(R.drawable.stat_notify_dormant_mode).setOngoing(true).setShowWhen(false).setVisibility(1).addAction(0, resources.getString(R.string.noti_dnd_action_turn_off), broadcast);
            addAction.setContentIntent(activityAsUser);
            boolean z6 = (zenModeConfig == null || zenModeConfig.manualRule == null) ? false : true;
            boolean z7 = z6 && zenModeConfig.manualRule.conditionId == null;
            boolean z8 = z6 && (uri2 = zenModeConfig.manualRule.conditionId) != null && ZenModeConfig.isValidCountdownConditionId(uri2);
            boolean z9 = z6 && (uri = zenModeConfig.manualRule.conditionId) != null && ZenModeConfig.isValidCountdownToAlarmConditionId(uri);
            if (zenModeConfig != null && zenModeConfig.manualRule == null && (arrayMap = zenModeConfig.automaticRules) != null && !arrayMap.isEmpty()) {
                Iterator it = zenModeConfig.automaticRules.values().iterator();
                boolean z10 = false;
                while (it.hasNext()) {
                    if (((ZenModeConfig.ZenRule) it.next()).isAutomaticActive()) {
                        z10 = true;
                    }
                }
                if (z10) {
                    z4 = true;
                    z5 = (this.mNotificationManager.getNotificationPolicy().priorityCategories & 32) != 0;
                    if (!z7 || z8 || z4) {
                        StringBuilder sb = new StringBuilder();
                        if (z7) {
                            UserTracker userTracker = zenModeControllerImpl.mUserTracker;
                            if (z8 || z9) {
                                long tryParseCountdownConditionId = ZenModeConfig.tryParseCountdownConditionId(zenModeConfig.manualRule.conditionId);
                                sb.append(resources.getString(R.string.noti_dnd_message_until, ZenModeConfig.getFormattedTime(context, tryParseCountdownConditionId, ZenModeConfig.isToday(tryParseCountdownConditionId), ((UserTrackerImpl) userTracker).getUserId())));
                            } else if (z4) {
                                String description = ZenModeConfig.getDescription(context, true, zenModeConfig, false);
                                Iterator it2 = zenModeConfig.automaticRules.values().iterator();
                                while (true) {
                                    if (!it2.hasNext()) {
                                        break;
                                    }
                                    ZenModeConfig.ZenRule zenRule = (ZenModeConfig.ZenRule) it2.next();
                                    if (zenRule.isAutomaticActive() && description.equals(zenRule.name)) {
                                        if (ZenModeConfig.isValidScheduleConditionId(zenRule.conditionId)) {
                                            long nextChangeTime = ZenModeConfig.toScheduleCalendar(zenRule.conditionId).getNextChangeTime(System.currentTimeMillis());
                                            sb.append(resources.getString(R.string.noti_dnd_message_until, ZenModeConfig.getFormattedTime(context, nextChangeTime, ZenModeConfig.isToday(nextChangeTime), ((UserTrackerImpl) userTracker).getUserId())));
                                            break;
                                        }
                                        sb.append(getApplicationNameFromPackage(zenRule.pkg.equals(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG) ? "com.samsung.android.app.routines" : zenRule.pkg));
                                    }
                                }
                                if (sb.toString().isEmpty()) {
                                    sb.append(description);
                                } else {
                                    sb.append(" (");
                                    sb.append(description);
                                    sb.append(")");
                                }
                            }
                        } else {
                            String str = zenModeConfig.manualRule.enabler;
                            if (str == null) {
                                sb.append(resources.getString(R.string.zen_mode_forever));
                            } else {
                                sb.append(getApplicationNameFromPackage(str));
                            }
                        }
                        String sb2 = sb.toString();
                        addAction.setContentText(sb2);
                        if (!z5) {
                            Notification.InboxStyle inboxStyle = new Notification.InboxStyle();
                            inboxStyle.setBigContentTitle(resources.getString(R.string.noti_dnd_title));
                            inboxStyle.addLine(sb2);
                            inboxStyle.addLine(resources.getString(R.string.noti_dnd_message_alarm));
                            addAction.setStyle(inboxStyle);
                        }
                    } else if (!z5) {
                        addAction.setContentText(resources.getString(R.string.noti_dnd_message_alarm));
                    }
                    Notification build = addAction.build();
                    build.semPriority |= 10;
                    this.mNotificationManager.notifyAsUser("noti_DoNotDisturb", 1006831526, build, UserHandle.ALL);
                    Log.d("PhoneStatusBarPolicy", "ZenNotifier");
                }
            }
            z4 = false;
            if ((this.mNotificationManager.getNotificationPolicy().priorityCategories & 32) != 0) {
            }
            if (z7) {
            }
            StringBuilder sb3 = new StringBuilder();
            if (z7) {
            }
            String sb22 = sb3.toString();
            addAction.setContentText(sb22);
            if (!z5) {
            }
            Notification build2 = addAction.build();
            build2.semPriority |= 10;
            this.mNotificationManager.notifyAsUser("noti_DoNotDisturb", 1006831526, build2, UserHandle.ALL);
            Log.d("PhoneStatusBarPolicy", "ZenNotifier");
        } else {
            this.mNotificationManager.cancelAsUser("noti_DoNotDisturb", 1006831526, UserHandle.ALL);
            Log.d("PhoneStatusBarPolicy", "ZenCanceled");
        }
        Integer value = ((RingerModeTrackerImpl) this.mRingerModeTracker).ringerModeInternal.getValue();
        if (value != null) {
            z = true;
            if (value.intValue() == 1) {
                z2 = false;
                z3 = this.mVibrateVisible;
                StatusBarIconController statusBarIconController = this.mIconController;
                if (z != z3) {
                    ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(this.mSlotVibrate, z);
                    this.mVibrateVisible = z;
                }
                if (z2 != this.mMuteVisible) {
                    ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(this.mSlotMute, z2);
                    this.mMuteVisible = z2;
                }
                StringBuilder sb4 = new StringBuilder("updateZen- mVibrateVisible: ");
                sb4.append(this.mVibrateVisible);
                sb4.append(", mMuteVisible: ");
                NotificationListener$$ExternalSyntheticOutline0.m123m(sb4, this.mMuteVisible, "PhoneStatusBarPolicy");
            }
        }
        z = false;
        boolean z11 = z;
        z = false;
        z2 = z11;
        z3 = this.mVibrateVisible;
        StatusBarIconController statusBarIconController2 = this.mIconController;
        if (z != z3) {
        }
        if (z2 != this.mMuteVisible) {
        }
        StringBuilder sb42 = new StringBuilder("updateZen- mVibrateVisible: ");
        sb42.append(this.mVibrateVisible);
        sb42.append(", mMuteVisible: ");
        NotificationListener$$ExternalSyntheticOutline0.m123m(sb42, this.mMuteVisible, "PhoneStatusBarPolicy");
    }

    @Override // com.android.systemui.statusbar.policy.SBluetoothController.SCallback
    public final void onBluetoothScanStateChanged(boolean z) {
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void appTransitionStarting(int i, long j, long j2, boolean z) {
    }
}
