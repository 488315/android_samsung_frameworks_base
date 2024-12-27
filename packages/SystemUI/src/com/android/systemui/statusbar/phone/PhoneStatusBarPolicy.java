package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.telecom.TelecomManager;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.lifecycle.Observer;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractor;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.privacy.PrivacyItem;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.privacy.PrivacyType;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.phone.ui.StatusBarIconList;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.CastControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DataSaverController;
import com.android.systemui.statusbar.policy.DataSaverControllerImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.HotspotController;
import com.android.systemui.statusbar.policy.HotspotControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.statusbar.policy.LocationControllerImpl;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.NextAlarmControllerImpl;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.statusbar.policy.SBluetoothController;
import com.android.systemui.statusbar.policy.SBluetoothControllerImpl;
import com.android.systemui.statusbar.policy.SensorPrivacyController;
import com.android.systemui.statusbar.policy.SensorPrivacyControllerImpl;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.RingerModeTracker;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.util.time.DateFormatUtil;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class PhoneStatusBarPolicy implements SBluetoothController.SCallback, CommandQueue.Callbacks, RotationLockController.RotationLockControllerCallback, DataSaverController.Listener, ZenModeController.Callback, DeviceProvisionedController.DeviceProvisionedListener, KeyguardStateController.Callback, PrivacyItemController.Callback, LocationController.LocationChangeCallback, RecordingController.RecordingStateChangeCallback {
    public static final boolean DEBUG = Log.isLoggable("PhoneStatusBarPolicy", 3);
    public static final int LOCATION_STATUS_ICON_ID = PrivacyType.TYPE_LOCATION.getIconId();
    public final ActivityManager mActivityManager;
    public final AlarmManager mAlarmManager;
    public final SBluetoothController mBluetooth;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final CastController mCast;
    public final CommandQueue mCommandQueue;
    public final ConfigurationController mConfigurationController;
    public final ConnectedDisplayInteractor mConnectedDisplayInteractor;
    public boolean mCurrentUserSetup;
    public final DataSaverController mDataSaver;
    public final DevicePolicyManager mDevicePolicyManager;
    public final int mDisplayId;
    public final Handler mHandler;
    public final HotspotController mHotspot;
    public final StatusBarIconController mIconController;
    public final JavaAdapter mJavaAdapter;
    public final KeyguardStateController mKeyguardStateController;
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
    public final String mSlotAlarmClock;
    public final String mSlotBTTethering;
    public final String mSlotBluetooth;
    public final String mSlotBluetoothConnected;
    public final String mSlotCamera;
    public final String mSlotCast;
    public final String mSlotConnectedDisplay;
    public final String mSlotDataSaver;
    public final String mSlotHeadset;
    public final String mSlotHotspot;
    public final String mSlotLocation;
    public final String mSlotManagedProfile;
    public final String mSlotMicrophone;
    public final String mSlotMute;
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
    public boolean mZenVisible;
    public boolean mProfileIconVisible = false;
    public final AnonymousClass1 mProcessListener = new ActivityManager.SemProcessListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.1
        public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            ActivityManager.RunningTaskInfo runningTask = ActivityManagerWrapper.sInstance.getRunningTask();
            if (runningTask == null || runningTask.topActivityInfo == null) {
                return;
            }
            PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
            if (phoneStatusBarPolicy.mDisplayId == runningTask.displayId) {
                phoneStatusBarPolicy.updateProfileIcon();
            }
        }

        public final void onProcessDied(int i, int i2) {
        }
    };
    public final UserTracker.Callback mUserSwitchListener = new AnonymousClass2();
    public final AnonymousClass3 mHotspotCallback = new HotspotController.Callback() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.3
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
    public final AnonymousClass4 mCastCallback = new CastController.Callback() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.4
        @Override // com.android.systemui.statusbar.policy.CastController.Callback
        public final void onCastDevicesChanged() {
            boolean z;
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
            handler.removeCallbacks(phoneStatusBarPolicy.mRemoveCastIconRunnable);
            if (!z || phoneStatusBarPolicy.mRecordingController.isRecording()) {
                handler.postDelayed(phoneStatusBarPolicy.mRemoveCastIconRunnable, 3000L);
                return;
            }
            String string = phoneStatusBarPolicy.mResources.getString(R.string.accessibility_casting);
            StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController;
            String str = phoneStatusBarPolicy.mSlotCast;
            statusBarIconControllerImpl.setIcon(string, str, R.drawable.stat_sys_cast);
            statusBarIconControllerImpl.setIconVisibility(str, true);
        }
    };
    public final AnonymousClass5 mNextAlarmCallback = new NextAlarmController.NextAlarmChangeCallback() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.5
        @Override // com.android.systemui.statusbar.policy.NextAlarmController.NextAlarmChangeCallback
        public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
            boolean z = PhoneStatusBarPolicy.DEBUG;
            PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
            phoneStatusBarPolicy.getClass();
            phoneStatusBarPolicy.updateAlarm();
        }
    };
    public final AnonymousClass6 mSensorPrivacyListener = new AnonymousClass6();
    public final AnonymousClass7 mIntentReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.7
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            action.getClass();
            switch (action) {
                case "android.intent.action.PROFILE_ACCESSIBLE":
                case "android.intent.action.PROFILE_REMOVED":
                case "android.intent.action.MANAGED_PROFILE_UNAVAILABLE":
                case "android.intent.action.MANAGED_PROFILE_AVAILABLE":
                case "android.intent.action.PROFILE_INACCESSIBLE":
                    PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
                    boolean z = PhoneStatusBarPolicy.DEBUG;
                    phoneStatusBarPolicy.updateProfileIcon();
                    return;
                case "android.intent.action.HEADSET_PLUG":
                    PhoneStatusBarPolicy phoneStatusBarPolicy2 = PhoneStatusBarPolicy.this;
                    boolean z2 = PhoneStatusBarPolicy.DEBUG;
                    phoneStatusBarPolicy2.getClass();
                    boolean z3 = intent.getIntExtra("state", 0) != 0;
                    boolean z4 = intent.getIntExtra("microphone", 0) != 0;
                    String str = phoneStatusBarPolicy2.mSlotHeadset;
                    StatusBarIconController statusBarIconController = phoneStatusBarPolicy2.mIconController;
                    if (!z3) {
                        ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, false);
                        return;
                    }
                    StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) statusBarIconController;
                    statusBarIconControllerImpl.setIcon(phoneStatusBarPolicy2.mResources.getString(z4 ? R.string.accessibility_status_bar_headset : R.string.accessibility_status_bar_headphones), str, z4 ? R.drawable.stat_sys_headset_mic : R.drawable.stat_sys_headset);
                    statusBarIconControllerImpl.setIconVisibility(str, true);
                    return;
                case "android.intent.action.SIM_STATE_CHANGED":
                    intent.getBooleanExtra("rebroadcastOnUnlock", false);
                    return;
                case "android.intent.action.LOCALE_CHANGED":
                case "android.app.action.NOTIFICATION_POLICY_CHANGED":
                    PhoneStatusBarPolicy phoneStatusBarPolicy3 = PhoneStatusBarPolicy.this;
                    boolean z5 = PhoneStatusBarPolicy.DEBUG;
                    phoneStatusBarPolicy3.updateVolumeZen();
                    return;
                case "android.intent.action.TIMEZONE_CHANGED":
                case "android.intent.action.TIME_SET":
                case "android.intent.action.DATE_CHANGED":
                    PhoneStatusBarPolicy phoneStatusBarPolicy4 = PhoneStatusBarPolicy.this;
                    boolean z6 = PhoneStatusBarPolicy.DEBUG;
                    phoneStatusBarPolicy4.updateVolumeZen();
                    return;
                case "android.telecom.action.CURRENT_TTY_MODE_CHANGED":
                    PhoneStatusBarPolicy phoneStatusBarPolicy5 = PhoneStatusBarPolicy.this;
                    int intExtra = intent.getIntExtra("android.telecom.extra.CURRENT_TTY_MODE", 0);
                    boolean z7 = PhoneStatusBarPolicy.DEBUG;
                    phoneStatusBarPolicy5.updateTTY(intExtra);
                    return;
                case "com.android.systemui.action.dnd_off":
                    Bundle extras = intent.getExtras();
                    if (extras != null) {
                        String[] split = context.getPackageManager().getNameForUid(extras.getInt(NetworkAnalyticsConstants.DataPoints.UID)).split(":");
                        if (split.length == 0 || !split[0].equals("android.uid.systemui")) {
                            return;
                        }
                        PhoneStatusBarPolicy phoneStatusBarPolicy6 = PhoneStatusBarPolicy.this;
                        boolean z8 = PhoneStatusBarPolicy.DEBUG;
                        phoneStatusBarPolicy6.getClass();
                        throw null;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    public final AnonymousClass8 mRemoveCastIconRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.8
        @Override // java.lang.Runnable
        public final void run() {
            boolean z = PhoneStatusBarPolicy.DEBUG;
            PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
            ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotCast, false);
        }
    };
    public final AnonymousClass9 mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.9
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onLocaleListChanged() {
            PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
            String string = phoneStatusBarPolicy.mResources.getString(R.string.status_bar_alarm);
            StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController;
            statusBarIconControllerImpl.setIconContentDescription(string, phoneStatusBarPolicy.mSlotAlarmClock);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_ringer_vibrate), phoneStatusBarPolicy.mSlotVibrate);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_ringer_silent), phoneStatusBarPolicy.mSlotMute);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_status_bar_hotspot), phoneStatusBarPolicy.mSlotHotspot);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mDevicePolicyManager.getResources().getString("SystemUi.STATUS_BAR_WORK_ICON_ACCESSIBILITY", new PhoneStatusBarPolicy$$ExternalSyntheticLambda5(phoneStatusBarPolicy)), phoneStatusBarPolicy.mSlotManagedProfile);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_data_saver_on), phoneStatusBarPolicy.mSlotDataSaver);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mResources.getString(R.string.ongoing_privacy_chip_content_multiple_apps, phoneStatusBarPolicy.mResources.getString(PrivacyType.TYPE_MICROPHONE.getNameId())), phoneStatusBarPolicy.mSlotMicrophone);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mResources.getString(R.string.ongoing_privacy_chip_content_multiple_apps, phoneStatusBarPolicy.mResources.getString(PrivacyType.TYPE_CAMERA.getNameId())), phoneStatusBarPolicy.mSlotCamera);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_location_active), phoneStatusBarPolicy.mSlotLocation);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_sensors_off_active), phoneStatusBarPolicy.mSlotSensorsOff);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_quick_settings_bluetooth_on), phoneStatusBarPolicy.mSlotBluetooth);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_bluetooth_connected), phoneStatusBarPolicy.mSlotBluetoothConnected);
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$10, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass10 {
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$2, reason: invalid class name */
    class AnonymousClass2 implements UserTracker.Callback {
        public AnonymousClass2() {
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            PhoneStatusBarPolicy.this.mHandler.post(new PhoneStatusBarPolicy$2$$ExternalSyntheticLambda0(this, 1));
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanging(int i) {
            PhoneStatusBarPolicy.this.mHandler.post(new PhoneStatusBarPolicy$2$$ExternalSyntheticLambda0(this, 0));
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$6, reason: invalid class name */
    public final class AnonymousClass6 {
        public AnonymousClass6() {
        }
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$1] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$3] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$4] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$5] */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$7] */
    /* JADX WARN: Type inference failed for: r2v8, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$8] */
    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$9] */
    public PhoneStatusBarPolicy(StatusBarIconController statusBarIconController, CommandQueue commandQueue, BroadcastDispatcher broadcastDispatcher, Executor executor, Executor executor2, Looper looper, Resources resources, CastController castController, HotspotController hotspotController, SBluetoothController sBluetoothController, NextAlarmController nextAlarmController, UserInfoController userInfoController, RotationLockController rotationLockController, DataSaverController dataSaverController, ZenModeController zenModeController, DeviceProvisionedController deviceProvisionedController, KeyguardStateController keyguardStateController, LocationController locationController, SensorPrivacyController sensorPrivacyController, AlarmManager alarmManager, UserManager userManager, UserTracker userTracker, DevicePolicyManager devicePolicyManager, RecordingController recordingController, TelecomManager telecomManager, int i, SharedPreferences sharedPreferences, DateFormatUtil dateFormatUtil, RingerModeTracker ringerModeTracker, PrivacyItemController privacyItemController, PrivacyLogger privacyLogger, ConnectedDisplayInteractor connectedDisplayInteractor, JavaAdapter javaAdapter, ConfigurationController configurationController, KnoxStateMonitor knoxStateMonitor, ActivityManager activityManager) {
        this.mIconController = statusBarIconController;
        this.mCommandQueue = commandQueue;
        this.mConnectedDisplayInteractor = connectedDisplayInteractor;
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
        this.mJavaAdapter = javaAdapter;
        this.mConfigurationController = configurationController;
        this.mSlotCast = resources.getString(17043127);
        this.mSlotConnectedDisplay = resources.getString(17043130);
        this.mSlotHotspot = resources.getString(17043137);
        this.mSlotBluetooth = resources.getString(17043122);
        this.mSlotBluetoothConnected = resources.getString(17043123);
        this.mSlotTty = resources.getString(17043163);
        this.mSlotZen = resources.getString(17043167);
        this.mSlotMute = resources.getString(17043148);
        this.mSlotVibrate = resources.getString(17043164);
        this.mSlotAlarmClock = resources.getString(17043120);
        this.mSlotManagedProfile = resources.getString(17043143);
        this.mSlotRotate = resources.getString(17043156);
        this.mSlotHeadset = resources.getString(17043136);
        this.mSlotDataSaver = resources.getString(17043132);
        this.mSlotLocation = resources.getString(17043142);
        this.mSlotMicrophone = resources.getString(17043144);
        this.mSlotCamera = resources.getString(17043126);
        this.mSlotSensorsOff = resources.getString(17043159);
        this.mSlotScreenRecord = resources.getString(17043157);
        this.mSlotBTTethering = resources.getString(17043124);
        this.mDisplayId = i;
        this.mActivityManager = activityManager;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void appTransitionFinished(int i) {
        if (this.mDisplayId == i) {
            updateProfileIcon();
        }
    }

    public final void init() {
        boolean z;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.HEADSET_PLUG");
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        intentFilter.addAction("android.telecom.action.CURRENT_TTY_MODE_CHANGED");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
        intentFilter.addAction("android.intent.action.PROFILE_REMOVED");
        intentFilter.addAction("android.intent.action.PROFILE_ACCESSIBLE");
        intentFilter.addAction("android.intent.action.PROFILE_INACCESSIBLE");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        intentFilter.addAction("android.app.action.NOTIFICATION_POLICY_CHANGED");
        intentFilter.addAction("com.android.systemui.action.dnd_off");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.DATE_CHANGED");
        this.mBroadcastDispatcher.registerReceiverWithHandler(this.mIntentReceiver, intentFilter, this.mHandler, UserHandle.ALL);
        Observer observer = new Observer() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
                phoneStatusBarPolicy.mHandler.post(new PhoneStatusBarPolicy$$ExternalSyntheticLambda0(phoneStatusBarPolicy, 1));
            }
        };
        this.mRingerModeTracker.getRingerMode().observeForever(observer);
        this.mRingerModeTracker.getRingerModeInternal().observeForever(observer);
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserSwitchListener, this.mMainExecutor);
        TelecomManager telecomManager = this.mTelecomManager;
        if (telecomManager == null) {
            updateTTY(0);
        } else {
            updateTTY(telecomManager.getCurrentTtyMode());
        }
        StatusBarIconController statusBarIconController = this.mIconController;
        StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) statusBarIconController;
        statusBarIconControllerImpl.setIcon(this.mResources.getString(R.string.accessibility_quick_settings_bluetooth_on), this.mSlotBluetooth, R.drawable.sec_stat_sys_data_bluetooth);
        StatusBarIconController statusBarIconController2 = this.mIconController;
        StatusBarIconControllerImpl statusBarIconControllerImpl2 = (StatusBarIconControllerImpl) statusBarIconController2;
        statusBarIconControllerImpl2.setIcon(this.mResources.getString(R.string.accessibility_bluetooth_connected), this.mSlotBluetoothConnected, R.drawable.sec_stat_sys_data_bluetooth_connected);
        updateBluetooth();
        StatusBarIconController statusBarIconController3 = this.mIconController;
        StatusBarIconControllerImpl statusBarIconControllerImpl3 = (StatusBarIconControllerImpl) statusBarIconController3;
        statusBarIconControllerImpl3.setIcon(this.mResources.getString(R.string.status_bar_alarm), this.mSlotAlarmClock, R.drawable.stat_sys_alarm);
        ((StatusBarIconControllerImpl) this.mIconController).setIconVisibility(this.mSlotAlarmClock, false);
        ((StatusBarIconControllerImpl) this.mIconController).setIcon(null, this.mSlotZen, R.drawable.stat_sys_do_not_disturb_mode);
        ((StatusBarIconControllerImpl) this.mIconController).setIconVisibility(this.mSlotZen, false);
        StatusBarIconController statusBarIconController4 = this.mIconController;
        StatusBarIconControllerImpl statusBarIconControllerImpl4 = (StatusBarIconControllerImpl) statusBarIconController4;
        statusBarIconControllerImpl4.setIcon(this.mResources.getString(R.string.accessibility_ringer_vibrate), this.mSlotVibrate, R.drawable.sec_stat_sys_ringer_vibrate);
        ((StatusBarIconControllerImpl) this.mIconController).setIconVisibility(this.mSlotVibrate, false);
        StatusBarIconController statusBarIconController5 = this.mIconController;
        StatusBarIconControllerImpl statusBarIconControllerImpl5 = (StatusBarIconControllerImpl) statusBarIconController5;
        statusBarIconControllerImpl5.setIcon(this.mResources.getString(R.string.accessibility_ringer_silent), this.mSlotMute, R.drawable.sec_stat_sys_ringer_silent);
        ((StatusBarIconControllerImpl) this.mIconController).setIconVisibility(this.mSlotMute, false);
        updateVolumeZen();
        ((StatusBarIconControllerImpl) this.mIconController).setIcon(null, this.mSlotCast, R.drawable.stat_sys_cast);
        ((StatusBarIconControllerImpl) this.mIconController).setIconVisibility(this.mSlotCast, false);
        StatusBarIconController statusBarIconController6 = this.mIconController;
        StatusBarIconControllerImpl statusBarIconControllerImpl6 = (StatusBarIconControllerImpl) statusBarIconController6;
        statusBarIconControllerImpl6.setIcon(this.mResources.getString(R.string.connected_display_icon_desc), this.mSlotConnectedDisplay, R.drawable.sec_stat_sys_connected_display);
        ((StatusBarIconControllerImpl) this.mIconController).setIconVisibility(this.mSlotConnectedDisplay, false);
        StatusBarIconController statusBarIconController7 = this.mIconController;
        StatusBarIconControllerImpl statusBarIconControllerImpl7 = (StatusBarIconControllerImpl) statusBarIconController7;
        statusBarIconControllerImpl7.setIcon(this.mResources.getString(R.string.accessibility_status_bar_hotspot), this.mSlotHotspot, R.drawable.sec_stat_sys_hotspot);
        ((StatusBarIconControllerImpl) this.mIconController).setIconVisibility(this.mSlotHotspot, ((HotspotControllerImpl) this.mHotspot).isHotspotEnabled());
        updateProfileIcon();
        StatusBarIconController statusBarIconController8 = this.mIconController;
        StatusBarIconControllerImpl statusBarIconControllerImpl8 = (StatusBarIconControllerImpl) statusBarIconController8;
        statusBarIconControllerImpl8.setIcon(this.mResources.getString(R.string.accessibility_data_saver_on), this.mSlotDataSaver, R.drawable.sec_stat_sys_data_saver);
        ((StatusBarIconControllerImpl) this.mIconController).setIconVisibility(this.mSlotDataSaver, false);
        Resources resources = this.mResources;
        PrivacyType privacyType = PrivacyType.TYPE_MICROPHONE;
        String string = this.mResources.getString(R.string.ongoing_privacy_chip_content_multiple_apps, resources.getString(privacyType.getNameId()));
        ((StatusBarIconControllerImpl) this.mIconController).setIcon(string, this.mSlotMicrophone, privacyType.getIconId());
        ((StatusBarIconControllerImpl) this.mIconController).setIconVisibility(this.mSlotMicrophone, false);
        Resources resources2 = this.mResources;
        PrivacyType privacyType2 = PrivacyType.TYPE_CAMERA;
        String string2 = this.mResources.getString(R.string.ongoing_privacy_chip_content_multiple_apps, resources2.getString(privacyType2.getNameId()));
        ((StatusBarIconControllerImpl) this.mIconController).setIcon(string2, this.mSlotCamera, privacyType2.getIconId());
        ((StatusBarIconControllerImpl) this.mIconController).setIconVisibility(this.mSlotCamera, false);
        StatusBarIconController statusBarIconController9 = this.mIconController;
        StatusBarIconControllerImpl statusBarIconControllerImpl9 = (StatusBarIconControllerImpl) statusBarIconController9;
        statusBarIconControllerImpl9.setIcon(this.mResources.getString(R.string.accessibility_location_active), this.mSlotLocation, R.drawable.stat_sys_location);
        ((StatusBarIconControllerImpl) this.mIconController).setIconVisibility(this.mSlotLocation, false);
        StatusBarIconController statusBarIconController10 = this.mIconController;
        StatusBarIconControllerImpl statusBarIconControllerImpl10 = (StatusBarIconControllerImpl) statusBarIconController10;
        statusBarIconControllerImpl10.setIcon(this.mResources.getString(R.string.accessibility_sensors_off_active), this.mSlotSensorsOff, R.drawable.stat_sys_sensors_off);
        StatusBarIconController statusBarIconController11 = this.mIconController;
        String str = this.mSlotSensorsOff;
        SensorPrivacyControllerImpl sensorPrivacyControllerImpl = (SensorPrivacyControllerImpl) this.mSensorPrivacyController;
        synchronized (sensorPrivacyControllerImpl.mLock) {
            z = sensorPrivacyControllerImpl.mSensorPrivacyEnabled;
        }
        ((StatusBarIconControllerImpl) statusBarIconController11).setIconVisibility(str, z);
        ((StatusBarIconControllerImpl) this.mIconController).setIcon(null, this.mSlotScreenRecord, R.drawable.stat_sys_screen_record);
        ((StatusBarIconControllerImpl) this.mIconController).setIconVisibility(this.mSlotScreenRecord, false);
        ((StatusBarIconControllerImpl) this.mIconController).setIcon(null, this.mSlotBTTethering, R.drawable.stat_sys_tether_bluetooth);
        ((StatusBarIconControllerImpl) this.mIconController).setIconVisibility(this.mSlotBTTethering, false);
        this.mRotationLockController.addCallback(this);
        ((SBluetoothControllerImpl) this.mBluetooth).addCallback(this);
        ((DeviceProvisionedControllerImpl) this.mProvisionedController).addCallback(this);
        this.mCurrentUserSetup = ((DeviceProvisionedControllerImpl) this.mProvisionedController).isCurrentUserSetup();
        this.mCurrentUserSetup = ((DeviceProvisionedControllerImpl) this.mProvisionedController).isCurrentUserSetup();
        ((ZenModeControllerImpl) this.mZenController).addCallback(this);
        ((CastControllerImpl) this.mCast).addCallback(this.mCastCallback);
        ((HotspotControllerImpl) this.mHotspot).addCallback(this.mHotspotCallback);
        ((NextAlarmControllerImpl) this.mNextAlarmController).addCallback(this.mNextAlarmCallback);
        ((DataSaverControllerImpl) this.mDataSaver).addCallback(this);
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).addCallback(this);
        this.mPrivacyItemController.addCallback(this);
        ((SensorPrivacyControllerImpl) this.mSensorPrivacyController).addCallback(this.mSensorPrivacyListener);
        ((LocationControllerImpl) this.mLocationController).addCallback(this);
        this.mRecordingController.mListeners.add(this);
        this.mJavaAdapter.alwaysCollectFlow(((ConnectedDisplayInteractorImpl) this.mConnectedDisplayInteractor).connectedDisplayState, new Consumer() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
                ConnectedDisplayInteractor.State state = (ConnectedDisplayInteractor.State) obj;
                boolean z2 = PhoneStatusBarPolicy.DEBUG;
                phoneStatusBarPolicy.getClass();
                boolean z3 = state != ConnectedDisplayInteractor.State.DISCONNECTED;
                if (PhoneStatusBarPolicy.DEBUG) {
                    ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("connected_display: "), z3 ? "showing" : "hiding", " icon", "PhoneStatusBarPolicy");
                }
                ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotConnectedDisplay, z3);
            }
        });
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        this.mActivityManager.semRegisterProcessListener(this.mProcessListener);
        if (DeviceType.isEngOrUTBinary() && SystemProperties.getBoolean("debug.status_bar.show_icons", false)) {
            final StatusBarIconControllerImpl statusBarIconControllerImpl11 = (StatusBarIconControllerImpl) this.mIconController;
            statusBarIconControllerImpl11.mStatusBarIconList.mViewOnlySlots.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    StatusBarIconControllerImpl statusBarIconControllerImpl12 = StatusBarIconControllerImpl.this;
                    statusBarIconControllerImpl12.getClass();
                    statusBarIconControllerImpl12.setIconVisibility(((StatusBarIconList.Slot) obj).mName, true);
                }
            });
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
    public final void onConsolidatedPolicyChanged(NotificationManager.Policy policy) {
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
        handler.post(new PhoneStatusBarPolicy$$ExternalSyntheticLambda0(this, 3));
        handler.post(new PhoneStatusBarPolicy$$ExternalSyntheticLambda0(this, 4));
    }

    @Override // com.android.systemui.statusbar.policy.DataSaverController.Listener
    public final void onDataSaverChanged(boolean z) {
        ((StatusBarIconControllerImpl) this.mIconController).setIconVisibility(this.mSlotDataSaver, z);
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public final void onKeyguardShowingChanged() {
        updateProfileIcon();
    }

    @Override // com.android.systemui.statusbar.policy.LocationController.LocationChangeCallback
    public final void onLocationActiveChanged(boolean z) {
        if (this.mPrivacyItemController.privacyConfig.locationAvailable) {
            return;
        }
        boolean z2 = ((LocationControllerImpl) this.mLocationController).mAreActiveLocationRequests;
        String str = this.mSlotLocation;
        StatusBarIconController statusBarIconController = this.mIconController;
        if (z2) {
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
            int i = AnonymousClass10.$SwitchMap$com$android$systemui$privacy$PrivacyType[privacyItem.privacyType.ordinal()];
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
        this.mHandler.post(new PhoneStatusBarPolicy$$ExternalSyntheticLambda0(this, 2));
    }

    @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
    public final void onRecordingStart() {
        if (DEBUG) {
            Log.d("PhoneStatusBarPolicy", "screenrecord: showing icon");
        }
        ((StatusBarIconControllerImpl) this.mIconController).setIcon(this.mResources.getString(R.string.screenrecord_ongoing_screen_only), this.mSlotScreenRecord, R.drawable.stat_sys_screen_record);
        this.mHandler.post(new PhoneStatusBarPolicy$$ExternalSyntheticLambda0(this, 0));
    }

    @Override // com.android.systemui.statusbar.policy.RotationLockController.RotationLockControllerCallback
    public final void onRotationLockStateChanged(boolean z, boolean z2) {
        Resources resources = this.mResources;
        int rotationLockOrientation = this.mRotationLockController.getRotationLockOrientation();
        boolean z3 = rotationLockOrientation != 0 ? rotationLockOrientation != 2 : resources.getConfiguration().orientation != 2;
        String str = this.mSlotRotate;
        StatusBarIconController statusBarIconController = this.mIconController;
        if (!z) {
            ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, false);
            return;
        }
        if (z3) {
            ((StatusBarIconControllerImpl) statusBarIconController).setIcon(this.mResources.getString(R.string.accessibility_rotation_lock_on_portrait), str, R.drawable.stat_sys_rotate_portrait);
        } else {
            ((StatusBarIconControllerImpl) statusBarIconController).setIcon(this.mResources.getString(R.string.accessibility_rotation_lock_on_landscape), str, R.drawable.stat_sys_rotate_landscape);
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
                PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
                boolean z2 = PhoneStatusBarPolicy.DEBUG;
                phoneStatusBarPolicy.getClass();
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
        SBluetoothController sBluetoothController = this.mBluetooth;
        boolean z2 = false;
        if (sBluetoothController != null) {
            SBluetoothControllerImpl sBluetoothControllerImpl = (SBluetoothControllerImpl) sBluetoothController;
            boolean z3 = sBluetoothControllerImpl.mEnabled;
            if (sBluetoothControllerImpl.mConnectionState == 2) {
                z = true;
            } else {
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

    public final void updateProfileIcon() {
        this.mUiBgExecutor.execute(new PhoneStatusBarPolicy$$ExternalSyntheticLambda0(this, 5));
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

    /* JADX WARN: Removed duplicated region for block: B:18:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0085  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateVolumeZen() {
        /*
            r9 = this;
            java.lang.String r0 = "updateVolumeZen: NOTI_DND_ONGOING_ALERT"
            java.lang.String r1 = "PhoneStatusBarPolicy"
            android.util.Log.d(r1, r0)
            java.lang.String r0 = " updateVolumeZenTW"
            android.util.Log.d(r1, r0)
            java.lang.String r0 = "updateVolumeZenTW: NOTI_DND_ONGOING_ALERTNOTI_DND_ONGOING_ALERTNOTI_DND_ONGOING_ALERT"
            android.util.Log.d(r1, r0)
            com.android.systemui.statusbar.policy.ZenModeController r0 = r9.mZenController
            com.android.systemui.statusbar.policy.ZenModeControllerImpl r0 = (com.android.systemui.statusbar.policy.ZenModeControllerImpl) r0
            int r0 = r0.mZenMode
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L30
            android.content.res.Resources r0 = r9.mResources
            r4 = 2131954946(0x7f130d02, float:1.9546406E38)
            java.lang.String r0 = r0.getString(r4)
            java.lang.String r4 = "ZenNotifier"
            android.util.Log.d(r1, r4)
            r4 = 2131235612(0x7f08131c, float:1.8087423E38)
            r5 = r2
            goto L38
        L30:
            java.lang.String r0 = "ZenCanceled"
            android.util.Log.d(r1, r0)
            r0 = 0
            r4 = r3
            r5 = r4
        L38:
            if (r5 == 0) goto L43
            com.android.systemui.statusbar.phone.ui.StatusBarIconController r6 = r9.mIconController
            java.lang.String r7 = r9.mSlotZen
            com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl r6 = (com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl) r6
            r6.setIcon(r0, r7, r4)
        L43:
            boolean r0 = r9.mZenVisible
            if (r5 == r0) goto L52
            com.android.systemui.statusbar.phone.ui.StatusBarIconController r0 = r9.mIconController
            java.lang.String r4 = r9.mSlotZen
            com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl r0 = (com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl) r0
            r0.setIconVisibility(r4, r5)
            r9.mZenVisible = r5
        L52:
            com.android.systemui.util.RingerModeTracker r0 = r9.mRingerModeTracker
            androidx.lifecycle.LiveData r0 = r0.getRingerModeInternal()
            java.lang.Object r0 = r0.getValue()
            java.lang.Integer r0 = (java.lang.Integer) r0
            if (r0 == 0) goto L71
            int r4 = r0.intValue()
            if (r4 != r2) goto L67
            goto L72
        L67:
            int r0 = r0.intValue()
            if (r0 != 0) goto L71
            r8 = r3
            r3 = r2
            r2 = r8
            goto L72
        L71:
            r2 = r3
        L72:
            boolean r0 = r9.mVibrateVisible
            if (r2 == r0) goto L81
            com.android.systemui.statusbar.phone.ui.StatusBarIconController r0 = r9.mIconController
            java.lang.String r4 = r9.mSlotVibrate
            com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl r0 = (com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl) r0
            r0.setIconVisibility(r4, r2)
            r9.mVibrateVisible = r2
        L81:
            boolean r0 = r9.mMuteVisible
            if (r3 == r0) goto L90
            com.android.systemui.statusbar.phone.ui.StatusBarIconController r0 = r9.mIconController
            java.lang.String r2 = r9.mSlotMute
            com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl r0 = (com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl) r0
            r0.setIconVisibility(r2, r3)
            r9.mMuteVisible = r3
        L90:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "updateZen- mVibrateVisible: "
            r0.<init>(r2)
            boolean r2 = r9.mVibrateVisible
            r0.append(r2)
            java.lang.String r2 = ", mMuteVisible: "
            r0.append(r2)
            boolean r9 = r9.mMuteVisible
            com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(r0, r9, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.updateVolumeZen():void");
    }

    @Override // com.android.systemui.statusbar.policy.SBluetoothController.SCallback
    public final void onBluetoothScanStateChanged(boolean z) {
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void appTransitionStarting(long j, long j2, boolean z, int i) {
    }
}
