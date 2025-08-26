package com.android.systemui.statusbar.policy;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.PowerManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.pluginlock.component.PluginLockShortcutTask;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qp.flashlight.SubscreenFlashLightController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.util.NotificationChannels;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class SecFlashlightControllerImpl {
    public static final int[] FLASHLIGHT_VALUE = {1001, 1002, VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI, 1006, EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE};
    public final Uri CAMERA_FLASH_NOTIFICATION_URI;
    public final Uri FLASHLIGHT_BRIGHTNESS_URI;
    public final Uri FLASHLIGHT_SOS_URI;
    public final AccessibilityManager mAccessibilityManager;
    public final AnonymousClass5 mCameraDeviceStateCallback;
    public final CameraManager mCameraManager;
    public String mClientName;
    public final Context mContext;
    public final DisplayLifecycle mDisplayLifecycle;
    public final FlashlightControllerImpl mFlashlightController;
    public int mFlashlightLevel;
    public int mFlashlightTaskId;
    public final Handler mHandler;
    public boolean mIsCameraFlashNotiOn;
    public boolean mIsEnabled;
    public final AtomicBoolean mIsFlashlightTaskInStack;
    public boolean mIsLowBattery;
    public boolean mIsThermalRestricted;
    public final NotificationManager mNotiManager;
    public final PackageManager mPackageManager;
    public final AnonymousClass3 mReceiver;
    public final SecureSettings mSecureSettings;
    private final SettingsHelper.OnChangedCallback mSettingsCallback;
    private final SettingsHelper mSettingsHelper;
    public final SubscreenFlashLightController mSubscreenFlashlightController;
    public final AnonymousClass2 mTaskStackListener;
    public final AtomicBoolean mTorchPropertyInitted = new AtomicBoolean(false);
    public final AtomicBoolean mTorchStrengthControllable = new AtomicBoolean(false);
    public final Handler mUiHandler = new Handler(Looper.getMainLooper());
    public final UserTracker.Callback mUserChangedCallback;
    public final UserTracker mUserTracker;

    /* renamed from: com.android.systemui.statusbar.policy.SecFlashlightControllerImpl$2, reason: invalid class name */
    public class AnonymousClass2 implements TaskStackChangeListener {
        public AnonymousClass2() {
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public final void onTaskCreated(int i, ComponentName componentName) {
            Log.d("SecFlashlightController", "onTaskCreated: " + i + " " + componentName);
            if (componentName == null || !"com.android.cellbroadcastreceiver.CellBroadcastAlertDialog".equals(componentName.getClassName())) {
                return;
            }
            SecFlashlightControllerImpl secFlashlightControllerImpl = SecFlashlightControllerImpl.this;
            secFlashlightControllerImpl.mFlashlightTaskId = i;
            secFlashlightControllerImpl.mIsFlashlightTaskInStack.set(true);
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public final void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
            ComponentName componentName = runningTaskInfo.topActivity;
            String className = componentName != null ? componentName.getClassName() : "";
            Log.d("SecFlashlightController", "onTaskMovedToFront: " + "com.android.cellbroadcastreceiver.CellBroadcastAlertDialog".equals(className) + ", " + runningTaskInfo.taskId);
            boolean zEquals = "com.android.cellbroadcastreceiver.CellBroadcastAlertDialog".equals(className);
            SecFlashlightControllerImpl secFlashlightControllerImpl = SecFlashlightControllerImpl.this;
            if (zEquals) {
                secFlashlightControllerImpl.mFlashlightTaskId = runningTaskInfo.taskId;
                secFlashlightControllerImpl.mIsFlashlightTaskInStack.set(true);
            } else if (secFlashlightControllerImpl.mIsFlashlightTaskInStack.get()) {
                secFlashlightControllerImpl.mHandler.postDelayed(new SecFlashlightControllerImpl$$ExternalSyntheticLambda0(this, 2), 30000L);
            }
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public final void onTaskRemoved(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onTaskRemoved: ", "SecFlashlightController");
            SecFlashlightControllerImpl secFlashlightControllerImpl = SecFlashlightControllerImpl.this;
            if (secFlashlightControllerImpl.mFlashlightTaskId == i) {
                secFlashlightControllerImpl.mIsFlashlightTaskInStack.set(false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v0, types: [android.content.BroadcastReceiver, com.android.systemui.statusbar.policy.SecFlashlightControllerImpl$3] */
    /* JADX WARN: Type inference failed for: r13v0, types: [android.hardware.camera2.CameraManager$SemCameraDeviceStateCallback, com.android.systemui.statusbar.policy.SecFlashlightControllerImpl$5] */
    public SecFlashlightControllerImpl(CameraManager cameraManager, Context context, SecureSettings secureSettings, SettingsHelper settingsHelper, PackageManager packageManager, FlashlightControllerImpl flashlightControllerImpl) {
        this.mIsThermalRestricted = false;
        Uri uriFor = Settings.System.getUriFor(SettingsHelper.INDEX_FLASH_LIGHT_BRIGHTNESS_LEVEL);
        this.FLASHLIGHT_BRIGHTNESS_URI = uriFor;
        Uri uriFor2 = Settings.System.getUriFor(SettingsHelper.INDEX_FLASHLIGHT_SOS_ON);
        this.FLASHLIGHT_SOS_URI = uriFor2;
        Uri uriFor3 = Settings.System.getUriFor(SettingsHelper.INDEX_CAMERA_FLASH_NOTIFICATION);
        this.CAMERA_FLASH_NOTIFICATION_URI = uriFor3;
        Uri[] uriArr = {uriFor, uriFor2, uriFor3};
        this.mIsCameraFlashNotiOn = false;
        this.mIsFlashlightTaskInStack = new AtomicBoolean(false);
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        this.mTaskStackListener = anonymousClass2;
        ?? r11 = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.policy.SecFlashlightControllerImpl.3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                    SecFlashlightControllerImpl secFlashlightControllerImpl = SecFlashlightControllerImpl.this;
                    if (secFlashlightControllerImpl.mIsEnabled) {
                        secFlashlightControllerImpl.updateFlashlightNotification(true);
                    }
                }
            }
        };
        this.mReceiver = r11;
        SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.policy.SecFlashlightControllerImpl.4
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                Log.d("SecFlashlightController", "onChanged: " + uri);
                SecFlashlightControllerImpl secFlashlightControllerImpl = SecFlashlightControllerImpl.this;
                int i = 0;
                if (uri.equals(secFlashlightControllerImpl.FLASHLIGHT_BRIGHTNESS_URI)) {
                    int flashLightLevel = secFlashlightControllerImpl.mSettingsHelper.getFlashLightLevel();
                    secFlashlightControllerImpl.getClass();
                    while (true) {
                        if (i >= 5) {
                            i = 2;
                            break;
                        } else if (flashLightLevel == SecFlashlightControllerImpl.FLASHLIGHT_VALUE[i]) {
                            break;
                        } else {
                            i++;
                        }
                    }
                    secFlashlightControllerImpl.mFlashlightLevel = i;
                    RecyclerView$$ExternalSyntheticOutline0.m(secFlashlightControllerImpl.mFlashlightLevel, "SecFlashlightController", new StringBuilder("FlashlightLevel changed : "));
                    return;
                }
                if (!uri.equals(secFlashlightControllerImpl.FLASHLIGHT_SOS_URI)) {
                    if (uri.equals(secFlashlightControllerImpl.CAMERA_FLASH_NOTIFICATION_URI)) {
                        secFlashlightControllerImpl.mIsCameraFlashNotiOn = secFlashlightControllerImpl.mSettingsHelper.isCameraFlashNotificationOn();
                        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("onChanged: flash noti is "), secFlashlightControllerImpl.mIsCameraFlashNotiOn, "SecFlashlightController");
                        return;
                    }
                    return;
                }
                if (secFlashlightControllerImpl.mIsEnabled && secFlashlightControllerImpl.mSettingsHelper.isFlashLightSOSEnabled()) {
                    Log.d("SecFlashlightController", "Flashlight notification cancelled due to sos");
                    secFlashlightControllerImpl.updateFlashlightNotification(false);
                }
            }
        };
        this.mSettingsCallback = onChangedCallback;
        ?? r13 = new CameraManager.SemCameraDeviceStateCallback() { // from class: com.android.systemui.statusbar.policy.SecFlashlightControllerImpl.5
            public final void onCameraDeviceStateChanged(String str, int i, int i2, String str2) {
                if (i2 == 1) {
                    SecFlashlightControllerImpl.this.mClientName = str2;
                }
            }
        };
        this.mCameraDeviceStateCallback = r13;
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.policy.SecFlashlightControllerImpl.6
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                SecFlashlightControllerImpl secFlashlightControllerImpl = SecFlashlightControllerImpl.this;
                boolean z = secFlashlightControllerImpl.mSecureSettings.getIntForUser("flashlight_enabled", 0, i) == 1;
                KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("onUserChanged user: ", i, ", enabled: ", z, "SecFlashlightController");
                secFlashlightControllerImpl.mFlashlightController.setFlashlight(z);
            }
        };
        this.mUserChangedCallback = callback;
        this.mSettingsHelper = settingsHelper;
        this.mCameraManager = cameraManager;
        this.mContext = context;
        this.mSecureSettings = secureSettings;
        UserTracker userTracker = (UserTracker) Dependency.sDependency.getDependencyInner(UserTracker.class);
        this.mUserTracker = userTracker;
        synchronized (this) {
            if (this.mHandler == null) {
                HandlerThread handlerThread = new HandlerThread("SecFlashlightController", 10);
                handlerThread.start();
                this.mHandler = new Handler(handlerThread.getLooper());
            }
        }
        settingsHelper.registerCallback(onChangedCallback, uriArr);
        onChangedCallback.onChanged(uriFor);
        this.mFlashlightController = flashlightControllerImpl;
        PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
        cameraManager.registerSemCameraDeviceStateCallback(r13, this.mHandler);
        boolean z = powerManager.getCurrentThermalStatus() >= 5;
        if (this.mIsThermalRestricted != z) {
            this.mIsThermalRestricted = z;
            if (z) {
                flashlightControllerImpl.setFlashlight(false);
            }
            flashlightControllerImpl.dispatchListeners(2, !this.mIsThermalRestricted);
        }
        ((UserTrackerImpl) userTracker).addCallback(callback, context.getMainExecutor());
        powerManager.addThermalStatusListener(new PowerManager.OnThermalStatusChangedListener() { // from class: com.android.systemui.statusbar.policy.SecFlashlightControllerImpl.1
            @Override // android.os.PowerManager.OnThermalStatusChangedListener
            public final void onThermalStatusChanged(int i) {
                boolean z2 = i >= 5;
                SecFlashlightControllerImpl secFlashlightControllerImpl = SecFlashlightControllerImpl.this;
                if (secFlashlightControllerImpl.mIsThermalRestricted == z2) {
                    return;
                }
                secFlashlightControllerImpl.mIsThermalRestricted = z2;
                FlashlightControllerImpl flashlightControllerImpl2 = secFlashlightControllerImpl.mFlashlightController;
                if (z2) {
                    flashlightControllerImpl2.setFlashlight(false);
                }
                flashlightControllerImpl2.dispatchListeners(2, !secFlashlightControllerImpl.mIsThermalRestricted);
            }
        });
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        BroadcastDispatcher broadcastDispatcher = (BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class);
        this.mNotiManager = (NotificationManager) context.getSystemService(SubRoom.EXTRA_VALUE_NOTIFICATION);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        broadcastDispatcher.registerReceiver(intentFilter, r11);
        this.mPackageManager = packageManager;
        if (QpRune.QUICK_SUBSCREEN_SETTINGS) {
            this.mSubscreenFlashlightController = SubscreenFlashLightController.getInstance(context);
            this.mDisplayLifecycle = (DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class);
        }
        Log.d("SecFlashlightController", "updateTaskStackRegister: true");
        TaskStackChangeListeners.INSTANCE.registerTaskStackListener(anonymousClass2);
        this.mIsCameraFlashNotiOn = settingsHelper.isCameraFlashNotificationOn();
    }

    public final boolean isFlashlightBlinking() {
        return (this.mIsCameraFlashNotiOn && this.mAccessibilityManager.isCameraFlashNotificationRunning()) || this.mIsFlashlightTaskInStack.get();
    }

    public final synchronized boolean isThermalRestricted() {
        return this.mIsThermalRestricted;
    }

    public final boolean isTorchStrengthControllable() {
        FlashlightControllerImpl flashlightControllerImpl = this.mFlashlightController;
        if (flashlightControllerImpl.ensureCameraID() != null) {
            boolean z = true;
            if (!this.mTorchPropertyInitted.getAndSet(true)) {
                try {
                    if (((Integer) this.mCameraManager.getCameraCharacteristics(flashlightControllerImpl.ensureCameraID()).get(CameraCharacteristics.FLASH_INFO_STRENGTH_MAXIMUM_LEVEL)).intValue() <= 1) {
                        z = false;
                    }
                    this.mTorchStrengthControllable.set(z);
                } catch (CameraAccessException e) {
                    Log.e("SecFlashlightController", "Couldn't initialize torch property", e);
                    this.mTorchPropertyInitted.set(false);
                }
            }
        }
        return this.mTorchStrengthControllable.get();
    }

    public final void setFlashlightLevel(int i, boolean z) {
        if (isTorchStrengthControllable()) {
            try {
                String strEnsureCameraID = this.mFlashlightController.ensureCameraID();
                if (strEnsureCameraID != null) {
                    this.mCameraManager.turnOnTorchWithStrengthLevel(strEnsureCameraID, i);
                }
            } catch (CameraAccessException e) {
                Log.d("SecFlashlightController", "Couldn't set flashlight level", e);
            }
            if (z) {
                this.mSettingsHelper.setFlashLightLevel(FLASHLIGHT_VALUE[i - 1]);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r3v2, types: [android.content.pm.PackageManager] */
    public final void showUnavailableMessage() {
        ApplicationInfo applicationInfo;
        if (isFlashlightBlinking()) {
            Context context = this.mContext;
            showWarningMessage(context.getString(R.string.unable_to_turn_on_being_used_by_app, context.getString(R.string.flash_notification)));
            return;
        }
        String str = this.mClientName;
        if (str == null || str.contains("client.pid")) {
            showWarningMessage(this.mContext.getString(R.string.unable_to_turn_on_being_used_by_camera));
            return;
        }
        Context context2 = this.mContext;
        ?? applicationLabel = this.mClientName;
        try {
            applicationInfo = this.mPackageManager.getApplicationInfo(applicationLabel, 0);
        } catch (PackageManager.NameNotFoundException unused) {
            applicationInfo = null;
        }
        if (applicationInfo != null) {
            applicationLabel = this.mPackageManager.getApplicationLabel(applicationInfo);
        }
        showWarningMessage(context2.getString(R.string.unable_to_turn_on_being_used_by_app, (String) applicationLabel));
    }

    public final void showWarningMessage(CharSequence charSequence) {
        DisplayLifecycle displayLifecycle;
        Toast.makeText((!QpRune.QUICK_SUBSCREEN_PANEL || (displayLifecycle = this.mDisplayLifecycle) == null || displayLifecycle.mIsFolderOpened) ? this.mContext : ((SubscreenQsPanelController) Dependency.sDependency.getDependencyInner(SubscreenQsPanelController.class)).mContext, charSequence, 0).show();
    }

    public final void updateFlashlightNotification(boolean z) {
        if (this.mContext.getUserId() != 0) {
            return;
        }
        if (!z) {
            Log.d("SecFlashlightController", "cancelNotification!!!");
            this.mNotiManager.cancelAsUser(PluginLockShortcutTask.FLASH_LIGHT_TASK, 4660, UserHandle.ALL);
            return;
        }
        Log.d("SecFlashlightController", "notifyNotification!!!");
        if (this.mSettingsHelper.isEmergencyMode()) {
            Log.d("SecFlashlightController", "cancelNotification due to Emergency Mode!!!");
            this.mNotiManager.cancelAsUser(PluginLockShortcutTask.FLASH_LIGHT_TASK, 4660, UserHandle.ALL);
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.sec.android.systemui.action.FLASHLIGHT_OFF");
        PendingIntent broadcast = PendingIntent.getBroadcast(this.mContext, 0, intent, 201326592);
        String string = this.mContext.getResources().getString(R.string.flash_light_notification_title);
        Notification.Builder builder = new Notification.Builder(this.mContext, NotificationChannels.FLASHLIGHT_ONGOING);
        builder.setSmallIcon(R.drawable.stat_notify_flash_light).setVisibility(1).setContentTitle(string).setWhen(0L).setOngoing(true).addAction(0, this.mContext.getResources().getString(R.string.flash_light_notification_button), broadcast);
        this.mNotiManager.notifyAsUser(PluginLockShortcutTask.FLASH_LIGHT_TASK, 4660, builder.getNotification(), UserHandle.ALL);
    }
}
