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
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.os.UserManager;
import android.service.notification.ZenModeConfig;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telecom.TelecomManager;
import android.util.ArrayMap;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.lifecycle.Observer;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractor;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.modes.shared.ModesUiIcons;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.privacy.PrivacyItem;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.privacy.PrivacyType;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.privacy.logging.PrivacyLogger$$ExternalSyntheticLambda0;
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
import com.android.systemui.statusbar.policy.CastDevice;
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
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.RingerModeTracker;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.util.time.DateFormatUtil;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
    public final PhoneStatusBarPolicyExt mExt;
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
    public NotificationManager mNotificationManager = null;
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
    public final AnonymousClass2 mZenControllerCallback = new ZenModeController.Callback() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.2
        @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
        public final void onConsolidatedPolicyChanged(NotificationManager.Policy policy) {
            boolean z = PhoneStatusBarPolicy.DEBUG;
            PhoneStatusBarPolicy.this.updateVolumeZen();
        }

        @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
        public final void onZenChanged(int i) {
            boolean z = PhoneStatusBarPolicy.DEBUG;
            PhoneStatusBarPolicy.this.updateVolumeZen();
        }
    };
    public final Set DND_MODE_PACKAGES = new HashSet(Arrays.asList(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG));
    public final UserTracker.Callback mUserSwitchListener = new AnonymousClass3();
    public final AnonymousClass4 mHotspotCallback = new HotspotController.Callback() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.4
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
    public final AnonymousClass5 mCastCallback = new CastController.Callback() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.5
        @Override // com.android.systemui.statusbar.policy.CastController.Callback
        public final void onCastDevicesChanged() {
            PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
            ArrayList arrayList = (ArrayList) ((CastControllerImpl) phoneStatusBarPolicy.mCast).getCastDevices();
            int size = arrayList.size();
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                Object obj = arrayList.get(i);
                i++;
                if (((CastDevice) obj).isCasting) {
                    z = true;
                    break;
                }
            }
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
    public final AnonymousClass6 mNextAlarmCallback = new NextAlarmController.NextAlarmChangeCallback() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.6
        @Override // com.android.systemui.statusbar.policy.NextAlarmController.NextAlarmChangeCallback
        public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
            boolean z = PhoneStatusBarPolicy.DEBUG;
            PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
            phoneStatusBarPolicy.getClass();
            phoneStatusBarPolicy.updateAlarm();
        }
    };
    public final AnonymousClass7 mSensorPrivacyListener = new AnonymousClass7();
    public final AnonymousClass8 mIntentReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.8
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
                    break;
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
                        break;
                    } else {
                        StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) statusBarIconController;
                        statusBarIconControllerImpl.setIcon(phoneStatusBarPolicy2.mResources.getString(z4 ? R.string.accessibility_status_bar_headset : R.string.accessibility_status_bar_headphones), str, z4 ? R.drawable.stat_sys_headset_mic : R.drawable.stat_sys_headset);
                        statusBarIconControllerImpl.setIconVisibility(str, true);
                        break;
                    }
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
                            PhoneStatusBarPolicy.this.mNotificationManager.setZenMode(0, null, SubRoom.EXTRA_VALUE_NOTIFICATION, true);
                            break;
                        }
                    }
                    break;
            }
        }
    };
    public final AnonymousClass9 mRemoveCastIconRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.9
        @Override // java.lang.Runnable
        public final void run() {
            boolean z = PhoneStatusBarPolicy.DEBUG;
            PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
            ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotCast, false);
        }
    };
    public final AnonymousClass10 mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.10
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onLocaleListChanged() {
            final PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
            String string = phoneStatusBarPolicy.mResources.getString(R.string.status_bar_alarm);
            StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController;
            statusBarIconControllerImpl.setIconContentDescription(string, phoneStatusBarPolicy.mSlotAlarmClock);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_ringer_vibrate), phoneStatusBarPolicy.mSlotVibrate);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_ringer_silent), phoneStatusBarPolicy.mSlotMute);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_status_bar_hotspot), phoneStatusBarPolicy.mSlotHotspot);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mDevicePolicyManager.getResources().getString("SystemUi.STATUS_BAR_WORK_ICON_ACCESSIBILITY", new Supplier() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return PhoneStatusBarPolicy.this.mResources.getString(R.string.accessibility_managed_profile);
                }
            }), phoneStatusBarPolicy.mSlotManagedProfile);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_data_saver_on), phoneStatusBarPolicy.mSlotDataSaver);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mResources.getString(R.string.ongoing_privacy_chip_content_multiple_apps, phoneStatusBarPolicy.mResources.getString(PrivacyType.TYPE_MICROPHONE.getNameId())), phoneStatusBarPolicy.mSlotMicrophone);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mResources.getString(R.string.ongoing_privacy_chip_content_multiple_apps, phoneStatusBarPolicy.mResources.getString(PrivacyType.TYPE_CAMERA.getNameId())), phoneStatusBarPolicy.mSlotCamera);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_location_active), phoneStatusBarPolicy.mSlotLocation);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_sensors_off_active), phoneStatusBarPolicy.mSlotSensorsOff);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_quick_settings_bluetooth_on), phoneStatusBarPolicy.mSlotBluetooth);
            statusBarIconControllerImpl.setIconContentDescription(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_bluetooth_connected), phoneStatusBarPolicy.mSlotBluetoothConnected);
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$11, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass11 {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$3, reason: invalid class name */
    class AnonymousClass3 implements UserTracker.Callback {
        public AnonymousClass3() {
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            PhoneStatusBarPolicy.this.mHandler.post(new PhoneStatusBarPolicy$3$$ExternalSyntheticLambda0(this, 0));
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanging(int i) {
            PhoneStatusBarPolicy.this.mHandler.post(new PhoneStatusBarPolicy$3$$ExternalSyntheticLambda0(this, 1));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$7, reason: invalid class name */
    public class AnonymousClass7 {
        public AnonymousClass7() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$8] */
    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$9] */
    /* JADX WARN: Type inference failed for: r0v12, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$10] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$2] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$4] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$5] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$6] */
    public PhoneStatusBarPolicy(StatusBarIconController statusBarIconController, CommandQueue commandQueue, BroadcastDispatcher broadcastDispatcher, Executor executor, Executor executor2, Looper looper, Resources resources, CastController castController, HotspotController hotspotController, SBluetoothController sBluetoothController, NextAlarmController nextAlarmController, UserInfoController userInfoController, RotationLockController rotationLockController, DataSaverController dataSaverController, ZenModeController zenModeController, DeviceProvisionedController deviceProvisionedController, KeyguardStateController keyguardStateController, LocationController locationController, SensorPrivacyController sensorPrivacyController, AlarmManager alarmManager, UserManager userManager, UserTracker userTracker, DevicePolicyManager devicePolicyManager, RecordingController recordingController, TelecomManager telecomManager, int i, SharedPreferences sharedPreferences, DateFormatUtil dateFormatUtil, RingerModeTracker ringerModeTracker, PrivacyItemController privacyItemController, PrivacyLogger privacyLogger, ConnectedDisplayInteractor connectedDisplayInteractor, ZenModeInteractor zenModeInteractor, JavaAdapter javaAdapter, ConfigurationController configurationController, PhoneStatusBarPolicyExt phoneStatusBarPolicyExt, KnoxStateMonitor knoxStateMonitor, ActivityManager activityManager) {
        this.mIconController = statusBarIconController;
        this.mCommandQueue = commandQueue;
        this.mConnectedDisplayInteractor = connectedDisplayInteractor;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mHandler = new Handler(looper);
        this.mResources = resources;
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
        this.mCast = castController;
        this.mRecordingController = recordingController;
        this.mMainExecutor = executor;
        this.mUiBgExecutor = executor2;
        this.mTelecomManager = telecomManager;
        this.mRingerModeTracker = ringerModeTracker;
        this.mPrivacyLogger = privacyLogger;
        this.mJavaAdapter = javaAdapter;
        this.mConfigurationController = configurationController;
        this.mExt = phoneStatusBarPolicyExt;
        this.mSlotCast = resources.getString(17043265);
        this.mSlotConnectedDisplay = resources.getString(17043268);
        this.mSlotHotspot = resources.getString(17043275);
        this.mSlotBluetooth = resources.getString(17043260);
        this.mSlotBluetoothConnected = resources.getString(17043261);
        this.mSlotTty = resources.getString(17043302);
        this.mSlotZen = resources.getString(17043306);
        this.mSlotMute = resources.getString(17043286);
        this.mSlotVibrate = resources.getString(17043303);
        this.mSlotAlarmClock = resources.getString(17043258);
        this.mSlotManagedProfile = resources.getString(17043281);
        this.mSlotRotate = resources.getString(17043294);
        this.mSlotHeadset = resources.getString(17043274);
        this.mSlotDataSaver = resources.getString(17043270);
        this.mSlotLocation = resources.getString(17043280);
        this.mSlotMicrophone = resources.getString(17043282);
        this.mSlotCamera = resources.getString(17043264);
        this.mSlotSensorsOff = resources.getString(17043297);
        this.mSlotScreenRecord = resources.getString(17043295);
        this.mSlotBTTethering = resources.getString(17043262);
        this.mDisplayId = i;
        this.mActivityManager = activityManager;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void appTransitionFinished(int i) {
        if (this.mDisplayId == i) {
            updateProfileIcon();
        }
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

    public final String getDndNowBarSummary(ZenModeConfig zenModeConfig, NotificationManager.Policy policy, boolean z) {
        String string;
        Uri uri;
        StringBuilder sb = new StringBuilder();
        ZenModeControllerImpl zenModeControllerImpl = (ZenModeControllerImpl) this.mZenController;
        Context context = zenModeControllerImpl.mContext;
        String str = "";
        if (zenModeConfig == null || policy == null) {
            return "";
        }
        ZenModeConfig.ZenRule zenRule = zenModeConfig.manualRule;
        if (zenRule != null && zenRule.conditionId == null) {
            str = this.mResources.getString(R.string.sec_noti_dnd_turned_on);
        } else if (zenRule == null || (uri = zenRule.conditionId) == null || !ZenModeConfig.isValidCountdownConditionId(uri)) {
            ArrayMap arrayMap = zenModeConfig.automaticRules;
            if (arrayMap != null && !arrayMap.isEmpty()) {
                String description = ZenModeConfig.getDescription(context, true, zenModeConfig, false);
                Iterator it = zenModeConfig.automaticRules.values().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ZenModeConfig.ZenRule zenRule2 = (ZenModeConfig.ZenRule) it.next();
                    if (zenRule2.isAutomaticActive() && description != null && description.equals(zenRule2.name)) {
                        if (ZenModeConfig.isValidScheduleConditionId(zenRule2.conditionId)) {
                            long nextChangeTime = ZenModeConfig.toScheduleCalendar(zenRule2.conditionId).getNextChangeTime(System.currentTimeMillis());
                            boolean isToday = ZenModeConfig.isToday(nextChangeTime);
                            CharSequence formattedTime = ZenModeConfig.getFormattedTime(context, nextChangeTime, isToday, ((UserTrackerImpl) zenModeControllerImpl.mUserTracker).getUserId());
                            string = isToday ? this.mResources.getString(R.string.sec_noti_dnd_on_until_today, formattedTime) : this.mResources.getString(R.string.sec_noti_dnd_on_until_tomorrow, formattedTime);
                        } else {
                            string = this.mResources.getString(R.string.sec_noti_dnd_turned_on);
                        }
                        str = string;
                        sb.append(zenRule2.pkg.equals("android") ? this.mResources.getString(R.string.sec_noti_dnd_turned_on_by_app_or_schedule, zenRule2.name) : this.mResources.getString(R.string.sec_noti_dnd_turned_on_by_app_and_schedule, getApplicationNameFromPackage(zenRule2.pkg.equals(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG) ? "com.samsung.android.app.routines" : zenRule2.pkg), zenRule2.name));
                        sb.append("\n");
                    }
                }
            }
        } else {
            long tryParseCountdownConditionId = ZenModeConfig.tryParseCountdownConditionId(zenModeConfig.manualRule.conditionId);
            boolean isToday2 = ZenModeConfig.isToday(tryParseCountdownConditionId);
            CharSequence formattedTime2 = ZenModeConfig.getFormattedTime(context, tryParseCountdownConditionId, ZenModeConfig.isToday(tryParseCountdownConditionId), ((UserTrackerImpl) zenModeControllerImpl.mUserTracker).getUserId());
            str = isToday2 ? this.mResources.getString(R.string.sec_noti_dnd_on_until_today, formattedTime2) : this.mResources.getString(R.string.sec_noti_dnd_on_until_tomorrow, formattedTime2);
            String str2 = zenModeConfig.manualRule.enabler;
            if (str2 != null) {
                sb.append(this.mResources.getString(R.string.sec_noti_dnd_turned_on_by_app_or_schedule, getApplicationNameFromPackage(str2)) + "\n");
            }
        }
        if (z) {
            return str;
        }
        int i = policy.priorityCategories;
        boolean z2 = ((i & 8) == 0 && (i & 16) == 0) ? false : true;
        boolean z3 = (i & 4) != 0 || (policy.getAppBypassDndList() != null && policy.getAppBypassDndList().size() > 0);
        boolean z4 = policy.getExceptionContacts() != null && policy.getExceptionContacts().size() > 0;
        if (z4 || (z2 && z3)) {
            sb.append(this.mResources.getString(R.string.sec_noti_dnd_alert_allowed_all));
        } else if (!z4) {
            if (z2) {
                sb.append(this.mResources.getString(R.string.sec_noti_dnd_alert_allow_calls));
            } else if (z3) {
                sb.append(this.mResources.getString(R.string.sec_noti_dnd_alert_allow_message_apps));
            } else {
                sb.append(this.mResources.getString(R.string.sec_noti_dnd_alert_muted));
            }
        }
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "\n");
        m.append(sb.toString());
        return m.toString();
    }

    public final void init() {
        boolean z;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.HEADSET_PLUG");
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        intentFilter.addAction("android.telecom.action.CURRENT_TTY_MODE_CHANGED");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
        KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m(intentFilter, "android.intent.action.PROFILE_REMOVED", "android.intent.action.PROFILE_ACCESSIBLE", "android.intent.action.PROFILE_INACCESSIBLE", "android.intent.action.LOCALE_CHANGED");
        KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m(intentFilter, "android.app.action.NOTIFICATION_POLICY_CHANGED", "com.android.systemui.action.dnd_off", "android.intent.action.TIME_SET", "android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.DATE_CHANGED");
        this.mBroadcastDispatcher.registerReceiverWithHandler(this.mIntentReceiver, intentFilter, this.mHandler, UserHandle.ALL);
        Observer observer = new Observer() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
                phoneStatusBarPolicy.mHandler.post(new PhoneStatusBarPolicy$$ExternalSyntheticLambda2(phoneStatusBarPolicy, 1));
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
        int i = ModesUiIcons.$r8$clinit;
        ((ZenModeControllerImpl) this.mZenController).addCallback(this.mZenControllerCallback);
        ((HotspotControllerImpl) this.mHotspot).addCallback(this.mHotspotCallback);
        ((NextAlarmControllerImpl) this.mNextAlarmController).addCallback(this.mNextAlarmCallback);
        ((DataSaverControllerImpl) this.mDataSaver).addCallback(this);
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).addCallback(this);
        this.mPrivacyItemController.addCallback(this);
        ((SensorPrivacyControllerImpl) this.mSensorPrivacyController).addCallback(this.mSensorPrivacyListener);
        ((LocationControllerImpl) this.mLocationController).addCallback(this);
        ((CastControllerImpl) this.mCast).addCallback(this.mCastCallback);
        this.mRecordingController.mListeners.add(this);
        this.mJavaAdapter.alwaysCollectFlow(((ConnectedDisplayInteractorImpl) this.mConnectedDisplayInteractor).connectedDisplayState, new Consumer() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda5
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
        if (DeviceState.isTestModeIndicatorGarden()) {
            final StatusBarIconControllerImpl statusBarIconControllerImpl11 = (StatusBarIconControllerImpl) this.mIconController;
            statusBarIconControllerImpl11.mStatusBarIconList.mViewOnlySlots.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    StatusBarIconControllerImpl statusBarIconControllerImpl12 = StatusBarIconControllerImpl.this;
                    String str2 = StatusBarIconControllerImpl.EXTERNAL_SLOT_SUFFIX;
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
        PhoneStatusBarPolicyExt phoneStatusBarPolicyExt = this.mExt;
        PhoneStatusBarPolicyExt$removeLocationIconRunnable$1 phoneStatusBarPolicyExt$removeLocationIconRunnable$1 = phoneStatusBarPolicyExt.removeLocationIconRunnable;
        Handler handler = phoneStatusBarPolicyExt.handler;
        boolean hasCallbacks = handler.hasCallbacks(phoneStatusBarPolicyExt$removeLocationIconRunnable$1);
        PhoneStatusBarPolicyExt$removeLocationIconRunnable$1 phoneStatusBarPolicyExt$removeLocationIconRunnable$12 = phoneStatusBarPolicyExt.removeLocationIconRunnable;
        if (hasCallbacks) {
            handler.removeCallbacks(phoneStatusBarPolicyExt$removeLocationIconRunnable$12);
        }
        PhoneStatusBarPolicyExt$turnOffTimeToEnsureLocationIconDisplay$1 phoneStatusBarPolicyExt$turnOffTimeToEnsureLocationIconDisplay$1 = phoneStatusBarPolicyExt.turnOffTimeToEnsureLocationIconDisplay;
        if (handler.hasCallbacks(phoneStatusBarPolicyExt$turnOffTimeToEnsureLocationIconDisplay$1)) {
            handler.removeCallbacks(phoneStatusBarPolicyExt$turnOffTimeToEnsureLocationIconDisplay$1);
        }
        if (((LocationControllerImpl) phoneStatusBarPolicyExt.locationController).mAreActiveLocationRequests) {
            ((StatusBarIconControllerImpl) phoneStatusBarPolicyExt.iconController).setIconVisibility(phoneStatusBarPolicyExt.SLOT_NAME_LOCATION, true);
            phoneStatusBarPolicyExt.isTimeToEnsureLocationIconDisplay = true;
            handler.postDelayed(phoneStatusBarPolicyExt$turnOffTimeToEnsureLocationIconDisplay$1, 1000L);
        } else {
            if (phoneStatusBarPolicyExt.isTimeToEnsureLocationIconDisplay) {
                handler.postDelayed(phoneStatusBarPolicyExt$removeLocationIconRunnable$12, 1000L);
            } else {
                phoneStatusBarPolicyExt$removeLocationIconRunnable$12.run();
            }
            phoneStatusBarPolicyExt.isTimeToEnsureLocationIconDisplay = false;
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
            int i = AnonymousClass11.$SwitchMap$com$android$systemui$privacy$PrivacyType[privacyItem.privacyType.ordinal()];
            if (i == 1) {
                z = true;
            } else if (i == 2) {
                z3 = true;
            } else if (i == 3) {
                z2 = true;
            }
        }
        PrivacyLogger privacyLogger = this.mPrivacyLogger;
        privacyLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda0 = new PrivacyLogger$$ExternalSyntheticLambda0(6);
        LogBuffer logBuffer = privacyLogger.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logMessageImpl.bool3 = z3;
        logBuffer.commit(obtain);
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

    public final void updateAlarm() {
        List nextAlarmClocks = this.mAlarmManager.getNextAlarmClocks(-2);
        boolean z = false;
        boolean anyMatch = (nextAlarmClocks == null || nextAlarmClocks.isEmpty()) ? false : nextAlarmClocks.stream().anyMatch(new Predicate() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean equals;
                PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
                boolean z2 = PhoneStatusBarPolicy.DEBUG;
                phoneStatusBarPolicy.getClass();
                PendingIntent showIntent = ((AlarmManager.AlarmClockInfo) obj).getShowIntent();
                if (showIntent != null) {
                    String creatorPackage = showIntent.getCreatorPackage();
                    if ("com.sec.android.app.clockpackage".equals(creatorPackage)) {
                        equals = showIntent.getIntent() != null ? !r3.getBooleanExtra("dontShowAlarmIcon", false) : true;
                    } else {
                        equals = "com.google.android.deskclock".equals(creatorPackage);
                    }
                    if (equals) {
                        return true;
                    }
                }
                return false;
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
        this.mUiBgExecutor.execute(new PhoneStatusBarPolicy$$ExternalSyntheticLambda2(this, 0));
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

    /* JADX WARN: Removed duplicated region for block: B:35:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x01d7  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01a9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateVolumeZen() {
        /*
            Method dump skipped, instructions count: 545
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.updateVolumeZen():void");
    }

    @Override // com.android.systemui.statusbar.policy.SBluetoothController.SCallback
    public final void onBluetoothScanStateChanged(boolean z) {
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void appTransitionStarting(int i, long j, long j2, boolean z) {
    }
}
