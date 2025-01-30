package com.android.server.accessibility;

import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.display.DisplayManager;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.FeatureFlagUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.accessibility.FlashNotificationsController;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.display.DisplayPowerController2;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class FlashNotificationsController {
    static final String ACTION_FLASH_NOTIFICATION_START_PREVIEW = "com.android.internal.intent.action.FLASH_NOTIFICATION_START_PREVIEW";
    static final String ACTION_FLASH_NOTIFICATION_STOP_PREVIEW = "com.android.internal.intent.action.FLASH_NOTIFICATION_STOP_PREVIEW";
    static final int ALL_FLASH_NOTIFICATION_TYPE = 3;
    static final String CAMERA_FLASH_NOTIFICATION_ALL_APPS = "all";
    static final int CAMERA_FLASH_NOTIFICATION_TYPE = 1;
    static final String EXTRA_FLASH_NOTIFICATION_PREVIEW_COLOR = "com.android.internal.intent.extra.FLASH_NOTIFICATION_PREVIEW_COLOR";
    static final String EXTRA_FLASH_NOTIFICATION_PREVIEW_TYPE = "com.android.internal.intent.extra.FLASH_NOTIFICATION_PREVIEW_TYPE";
    static final int NONE_FLASH_NOTIFICATIONS_TYPE = 0;
    static final int PREVIEW_TYPE_LONG = 1;
    static final int PREVIEW_TYPE_SHORT = 0;
    static final char SCREEN_FLASH_NOTIFICATION_COLOR_APPS_ITEM_SEPARATOR = '#';
    static final char SCREEN_FLASH_NOTIFICATION_COLOR_APPS_LIST_SEPARATOR = ';';
    static final int SCREEN_FLASH_NOTIFICATION_MODE_ALL_APPS = 0;
    static final int SCREEN_FLASH_NOTIFICATION_MODE_CUSTOM = 1;
    static final int SCREEN_FLASH_NOTIFICATION_TYPE = 2;
    static final String SETTING_KEY_CAMERA_FLASH_NOTIFICATION = "camera_flash_notification";
    static final String SETTING_KEY_CAMERA_FLASH_NOTIFICATION_APP_LIST = "camera_flash_notification_app_list";
    static final String SETTING_KEY_SCREEN_FLASH_NOTIFICATION = "screen_flash_notification";
    static final String SETTING_KEY_SCREEN_FLASH_NOTIFICATION_COLOR = "screen_flash_notification_color_global";
    static final String SETTING_KEY_SCREEN_FLASH_NOTIFICATION_COLOR_APPS = "screen_flash_notification_color_apps";
    static final String SETTING_KEY_SCREEN_FLASH_NOTIFICATION_COLOR_MODE = "screen_flash_notification_color_mode";
    public final AudioManager.AudioPlaybackCallback mAudioPlaybackCallback;
    public final Handler mCallbackHandler;
    public String mCameraId;
    public CameraManager mCameraManager;
    public final Context mContext;
    public View mCoverScreenNotificationOverlayView;
    public FlashNotification mCurrentFlashNotification;
    public final DisplayManager mDisplayManager;
    public int mDisplayState;
    final FlashBroadcastReceiver mFlashBroadcastReceiver;
    public final Handler mFlashNotificationHandler;
    public final LinkedList mFlashNotifications;
    public boolean mIsAlarming;
    public boolean mIsCameraFlashNotificationEnabled;
    public boolean mIsCameraOpened;
    public boolean mIsScreenFlashNotificationEnabled;
    public boolean mIsTorchOn;
    public boolean mIsTorchTouched;
    public final Handler mMainHandler;
    public View mScreenFlashNotificationOverlayView;
    public volatile FlashNotificationThread mThread;
    final CameraManager.AvailabilityCallback mTorchAvailabilityCallback;
    public final CameraManager.TorchCallback mTorchCallback;
    public final PowerManager.WakeLock mWakeLock;

    /* renamed from: com.android.server.accessibility.FlashNotificationsController$3 */
    public class C03993 extends AudioManager.AudioPlaybackCallback {
        public C03993() {
        }

        @Override // android.media.AudioManager.AudioPlaybackCallback
        public void onPlaybackConfigChanged(List list) {
            boolean anyMatch = list != null ? list.stream().anyMatch(new Predicate() { // from class: com.android.server.accessibility.FlashNotificationsController$3$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$onPlaybackConfigChanged$0;
                    lambda$onPlaybackConfigChanged$0 = FlashNotificationsController.C03993.lambda$onPlaybackConfigChanged$0((AudioPlaybackConfiguration) obj);
                    return lambda$onPlaybackConfigChanged$0;
                }
            }) : false;
            if (FlashNotificationsController.this.mIsAlarming != anyMatch) {
                Log.d("FlashNotifController", "alarm state changed: " + anyMatch);
                if (anyMatch) {
                    FlashNotificationsController.this.startFlashNotificationSequenceForAlarm();
                } else {
                    FlashNotificationsController.this.stopFlashNotificationSequenceForAlarm();
                }
                FlashNotificationsController.this.mIsAlarming = anyMatch;
            }
        }

        public static /* synthetic */ boolean lambda$onPlaybackConfigChanged$0(AudioPlaybackConfiguration audioPlaybackConfiguration) {
            return audioPlaybackConfiguration.isActive() && audioPlaybackConfiguration.getAudioAttributes().getUsage() == 4;
        }
    }

    public FlashNotificationsController(Context context) {
        this(context, getStartedHandler("FlashNotificationThread"), getStartedHandler("FlashNotifController"));
    }

    public FlashNotificationsController(Context context, Handler handler, Handler handler2) {
        this.mFlashNotifications = new LinkedList();
        this.mIsTorchTouched = false;
        this.mIsTorchOn = false;
        this.mIsCameraFlashNotificationEnabled = false;
        this.mIsScreenFlashNotificationEnabled = false;
        this.mIsAlarming = false;
        this.mDisplayState = 1;
        this.mIsCameraOpened = false;
        this.mCameraId = null;
        this.mTorchCallback = new CameraManager.TorchCallback() { // from class: com.android.server.accessibility.FlashNotificationsController.1
            @Override // android.hardware.camera2.CameraManager.TorchCallback
            public void onTorchModeChanged(String str, boolean z) {
                if (FlashNotificationsController.this.mCameraId == null || !FlashNotificationsController.this.mCameraId.equals(str)) {
                    return;
                }
                FlashNotificationsController.this.mIsTorchOn = z;
                Log.d("FlashNotifController", "onTorchModeChanged, set mIsTorchOn=" + z);
            }
        };
        this.mTorchAvailabilityCallback = new CameraManager.AvailabilityCallback() { // from class: com.android.server.accessibility.FlashNotificationsController.2
            public void onCameraOpened(String str, String str2) {
                if (FlashNotificationsController.this.mCameraId == null || !FlashNotificationsController.this.mCameraId.equals(str)) {
                    return;
                }
                FlashNotificationsController.this.mIsCameraOpened = true;
            }

            public void onCameraClosed(String str) {
                if (FlashNotificationsController.this.mCameraId == null || !FlashNotificationsController.this.mCameraId.equals(str)) {
                    return;
                }
                FlashNotificationsController.this.mIsCameraOpened = false;
            }
        };
        this.mAudioPlaybackCallback = new C03993();
        this.mContext = context;
        Handler handler3 = new Handler(context.getMainLooper());
        this.mMainHandler = handler3;
        this.mFlashNotificationHandler = handler;
        this.mCallbackHandler = handler2;
        new FlashContentObserver(handler3).register(context.getContentResolver());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction(ACTION_FLASH_NOTIFICATION_START_PREVIEW);
        intentFilter.addAction(ACTION_FLASH_NOTIFICATION_STOP_PREVIEW);
        FlashBroadcastReceiver flashBroadcastReceiver = new FlashBroadcastReceiver();
        this.mFlashBroadcastReceiver = flashBroadcastReceiver;
        context.registerReceiver(flashBroadcastReceiver, intentFilter, 4);
        this.mWakeLock = ((PowerManager) context.getSystemService(PowerManager.class)).newWakeLock(1, "a11y:FlashNotificationsController");
        DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        this.mDisplayManager = displayManager;
        DisplayManager.DisplayListener displayListener = new DisplayManager.DisplayListener() { // from class: com.android.server.accessibility.FlashNotificationsController.4
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int i) {
                Display display;
                if (FlashNotificationsController.this.mDisplayManager == null || (display = FlashNotificationsController.this.mDisplayManager.getDisplay(i)) == null) {
                    return;
                }
                FlashNotificationsController.this.mDisplayState = display.getState();
            }
        };
        if (displayManager != null) {
            displayManager.registerDisplayListener(displayListener, null);
        }
    }

    public static Handler getStartedHandler(String str) {
        HandlerThread handlerThread = new HandlerThread(str);
        handlerThread.start();
        return handlerThread.getThreadHandler();
    }

    public boolean startFlashNotificationSequence(final String str, int i, IBinder iBinder) {
        FlashNotification flashNotification = new FlashNotification(this.mContext, str, str, 2, getScreenFlashColorPreference(i, str), iBinder, new IBinder.DeathRecipient() { // from class: com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticLambda0
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                FlashNotificationsController.this.lambda$startFlashNotificationSequence$0(str);
            }
        });
        if (!flashNotification.tryLinkToDeath()) {
            return false;
        }
        requestStartFlashNotification(flashNotification);
        return true;
    }

    public boolean stopFlashNotificationSequence(String str) {
        lambda$startFlashNotificationSequence$0(str);
        return true;
    }

    public boolean startFlashNotificationEvent(String str, int i, String str2) {
        requestStartFlashNotification(new FlashNotification(this.mContext, str, str2, 1, getScreenFlashColorPreference(i, str2)));
        return true;
    }

    public final void startFlashNotificationShortPreview() {
        requestStartFlashNotification(new FlashNotification(this.mContext, "preview", "preview", 1, getScreenFlashColorPreference(4)));
    }

    public final void startFlashNotificationLongPreview(int i) {
        requestStartFlashNotification(new FlashNotification(this.mContext, "preview", "preview", 3, i));
    }

    public final void stopFlashNotificationLongPreview() {
        lambda$startFlashNotificationSequence$0("preview");
    }

    public final void startFlashNotificationSequenceForAlarm() {
        requestStartFlashNotification(new FlashNotification("alarm", 2, getScreenFlashColorPreference(2)));
    }

    public final void stopFlashNotificationSequenceForAlarm() {
        lambda$startFlashNotificationSequence$0("alarm");
    }

    public final void requestStartFlashNotification(FlashNotification flashNotification) {
        Log.d("FlashNotifController", "requestStartFlashNotification");
        boolean z = false;
        if (SystemProperties.getInt("service.camera.running", 0) == 1) {
            return;
        }
        boolean isEnabled = FeatureFlagUtils.isEnabled(this.mContext, "settings_flash_notifications");
        this.mIsCameraFlashNotificationEnabled = isEnabled && Settings.System.getIntForUser(this.mContext.getContentResolver(), SETTING_KEY_CAMERA_FLASH_NOTIFICATION, 0, -2) != 0;
        if (isEnabled && Settings.System.getIntForUser(this.mContext.getContentResolver(), SETTING_KEY_SCREEN_FLASH_NOTIFICATION, 0, -2) != 0) {
            z = true;
        }
        this.mIsScreenFlashNotificationEnabled = z;
        if (flashNotification.mType == 1 && this.mIsScreenFlashNotificationEnabled) {
            this.mMainHandler.sendMessageDelayed(PooledLambda.obtainMessage(new BiConsumer() { // from class: com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((FlashNotificationsController) obj).startFlashNotification((FlashNotificationsController.FlashNotification) obj2);
                }
            }, this, flashNotification), 300L);
            Log.i("FlashNotifController", "give some delay for flash notification");
        } else {
            startFlashNotification(flashNotification);
        }
    }

    /* renamed from: stopFlashNotification, reason: merged with bridge method [inline-methods] */
    public final void lambda$startFlashNotificationSequence$0(String str) {
        Log.i("FlashNotifController", "stopFlashNotification: tag=" + str);
        synchronized (this.mFlashNotifications) {
            FlashNotification removeFlashNotificationLocked = removeFlashNotificationLocked(str);
            FlashNotification flashNotification = this.mCurrentFlashNotification;
            if (flashNotification != null && removeFlashNotificationLocked == flashNotification) {
                stopFlashNotificationLocked();
                startNextFlashNotificationLocked();
            }
        }
    }

    public final void prepareForCameraFlashNotification() {
        CameraManager cameraManager = (CameraManager) this.mContext.getSystemService(CameraManager.class);
        this.mCameraManager = cameraManager;
        if (cameraManager != null) {
            try {
                this.mCameraId = getCameraId();
            } catch (CameraAccessException e) {
                Log.e("FlashNotifController", "CameraAccessException", e);
            }
            this.mCameraManager.registerTorchCallback(this.mTorchCallback, (Handler) null);
        }
    }

    public final String getCameraId() {
        for (String str : this.mCameraManager.getCameraIdList()) {
            CameraCharacteristics cameraCharacteristics = this.mCameraManager.getCameraCharacteristics(str);
            Boolean bool = (Boolean) cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            Integer num = (Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
            if (bool != null && num != null && bool.booleanValue() && num.intValue() == 1) {
                Log.d("FlashNotifController", "Found valid camera, cameraId=" + str);
                return str;
            }
        }
        return null;
    }

    public final void showScreenNotificationOverlayView(int i) {
        this.mMainHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((FlashNotificationsController) obj).showScreenNotificationOverlayViewMainThread(((Integer) obj2).intValue());
            }
        }, this, Integer.valueOf(i)));
    }

    public final void hideScreenNotificationOverlayView() {
        this.mMainHandler.sendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FlashNotificationsController) obj).fadeOutScreenNotificationOverlayViewMainThread();
            }
        }, this));
        this.mMainHandler.sendMessageDelayed(PooledLambda.obtainMessage(new Consumer() { // from class: com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FlashNotificationsController) obj).hideScreenNotificationOverlayViewMainThread();
            }
        }, this), 210L);
    }

    public final void showScreenNotificationOverlayViewMainThread(int i) {
        Log.d("FlashNotifController", "showScreenNotificationOverlayViewMainThread");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2015, FrameworkStatsLog.f87x84af90cd, -3);
        layoutParams.privateFlags |= 16;
        layoutParams.layoutInDisplayCutoutMode = 1;
        layoutParams.inputFeatures |= 1;
        if (this.mScreenFlashNotificationOverlayView == null) {
            this.mScreenFlashNotificationOverlayView = getScreenNotificationOverlayView(i);
            ((WindowManager) this.mContext.getSystemService(WindowManager.class)).addView(this.mScreenFlashNotificationOverlayView, layoutParams);
            fadeScreenNotificationOverlayViewMainThread(this.mScreenFlashNotificationOverlayView, true);
        }
        WindowManager coverDisplayWindowManager = getCoverDisplayWindowManager();
        if (coverDisplayWindowManager == null || this.mCoverScreenNotificationOverlayView != null) {
            return;
        }
        View screenNotificationOverlayView = getScreenNotificationOverlayView(i);
        this.mCoverScreenNotificationOverlayView = screenNotificationOverlayView;
        coverDisplayWindowManager.addView(screenNotificationOverlayView, layoutParams);
        fadeScreenNotificationOverlayViewMainThread(this.mCoverScreenNotificationOverlayView, true);
    }

    public final WindowManager getCoverDisplayWindowManager() {
        Display display;
        Display[] displays = ((DisplayManager) this.mContext.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        int length = displays.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                display = null;
                break;
            }
            display = displays[i];
            if (display.getDisplayId() == 1) {
                break;
            }
            i++;
        }
        if (display == null) {
            Log.d("FlashNotifController", "coverDisplay is not found.");
            return null;
        }
        if (display.getState() != 2) {
            Log.d("FlashNotifController", "coverDisplay is not STATE_ON");
            return null;
        }
        return (WindowManager) this.mContext.createDisplayContext(display).getSystemService("window");
    }

    public final void fadeOutScreenNotificationOverlayViewMainThread() {
        Log.d("FlashNotifController", "fadeOutScreenNotificationOverlayViewMainThread");
        View view = this.mScreenFlashNotificationOverlayView;
        if (view != null) {
            fadeScreenNotificationOverlayViewMainThread(view, false);
        }
        View view2 = this.mCoverScreenNotificationOverlayView;
        if (view2 != null) {
            fadeScreenNotificationOverlayViewMainThread(view2, false);
        }
    }

    public final void fadeScreenNotificationOverlayViewMainThread(View view, boolean z) {
        float[] fArr = new float[2];
        float f = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        fArr[0] = z ? 0.0f : 1.0f;
        if (z) {
            f = 1.0f;
        }
        fArr[1] = f;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "alpha", fArr);
        ofFloat.setInterpolator(new AccelerateInterpolator());
        ofFloat.setAutoCancel(true);
        ofFloat.setDuration(200L);
        ofFloat.start();
    }

    public final void hideScreenNotificationOverlayViewMainThread() {
        Log.d("FlashNotifController", "hideScreenNotificationOverlayViewMainThread");
        View view = this.mScreenFlashNotificationOverlayView;
        if (view != null) {
            view.setVisibility(8);
            ((WindowManager) this.mContext.getSystemService(WindowManager.class)).removeView(this.mScreenFlashNotificationOverlayView);
            this.mScreenFlashNotificationOverlayView = null;
        }
        View view2 = this.mCoverScreenNotificationOverlayView;
        if (view2 != null) {
            view2.setVisibility(8);
            WindowManager coverDisplayWindowManager = getCoverDisplayWindowManager();
            if (coverDisplayWindowManager != null) {
                coverDisplayWindowManager.removeView(this.mCoverScreenNotificationOverlayView);
            }
            this.mCoverScreenNotificationOverlayView = null;
        }
    }

    public final View getScreenNotificationOverlayView(int i) {
        FrameLayout frameLayout = new FrameLayout(this.mContext);
        frameLayout.setBackgroundColor(i);
        frameLayout.setAlpha(DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        return frameLayout;
    }

    public final int getScreenFlashColorPreference(int i, String str) {
        return getScreenFlashColorPreference();
    }

    public final int getScreenFlashColorPreference(int i) {
        return getScreenFlashColorPreference();
    }

    public final int getScreenFlashColorPreference() {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), SETTING_KEY_SCREEN_FLASH_NOTIFICATION_COLOR, 1728052992, -2);
    }

    public final void startFlashNotification(FlashNotification flashNotification) {
        int i = flashNotification.mType;
        String str = flashNotification.mTag;
        Log.i("FlashNotifController", "startFlashNotification: type=" + i + ", tag=" + str);
        if (!this.mIsCameraFlashNotificationEnabled && !this.mIsScreenFlashNotificationEnabled && !flashNotification.mForceStartScreenFlash) {
            Log.d("FlashNotifController", "Flash notification is disabled");
            return;
        }
        if (this.mIsCameraOpened) {
            Log.d("FlashNotifController", "Since camera for torch is opened, block notification.");
            return;
        }
        if (this.mIsCameraFlashNotificationEnabled && this.mCameraId == null && (flashNotification.mNotiType & 1) == 1) {
            prepareForCameraFlashNotification();
        }
        if (this.mIsCameraFlashNotificationEnabled) {
            SystemProperties.set("service.cameraflashnoti.running", "1");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mFlashNotifications) {
                if (i == 1 || i == 3) {
                    if (this.mCurrentFlashNotification != null) {
                        Log.i("FlashNotifController", "Default type of flash notification can not work because previous flash notification is working");
                    } else {
                        startFlashNotificationLocked(flashNotification);
                    }
                } else if (i == 2) {
                    if (this.mCurrentFlashNotification != null) {
                        removeFlashNotificationLocked(str);
                        stopFlashNotificationLocked();
                    }
                    this.mFlashNotifications.addFirst(flashNotification);
                    startNextFlashNotificationLocked();
                } else {
                    Log.e("FlashNotifController", "Unavailable flash notification type");
                    SystemProperties.set("service.cameraflashnoti.running", "0");
                    Log.i("FlashNotifController", "set service.cameraflashnoti.running to 0 cause of Unavailable noti type");
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final FlashNotification removeFlashNotificationLocked(String str) {
        ListIterator listIterator = this.mFlashNotifications.listIterator(0);
        while (listIterator.hasNext()) {
            FlashNotification flashNotification = (FlashNotification) listIterator.next();
            if (flashNotification != null && flashNotification.mTag.equals(str)) {
                listIterator.remove();
                flashNotification.tryUnlinkToDeath();
                Log.i("FlashNotifController", "removeFlashNotificationLocked: tag=" + flashNotification.mTag);
                return flashNotification;
            }
        }
        FlashNotification flashNotification2 = this.mCurrentFlashNotification;
        if (flashNotification2 == null || !flashNotification2.mTag.equals(str)) {
            return null;
        }
        this.mCurrentFlashNotification.tryUnlinkToDeath();
        return this.mCurrentFlashNotification;
    }

    public final void stopFlashNotificationLocked() {
        if (this.mThread != null) {
            Log.i("FlashNotifController", "stopFlashNotificationLocked: tag=" + this.mThread.mFlashNotification.mTag);
            this.mThread.cancel();
            this.mThread = null;
        }
        doCameraFlashNotificationOff();
        doScreenFlashNotificationOff();
    }

    public final void startNextFlashNotificationLocked() {
        Log.i("FlashNotifController", "startNextFlashNotificationLocked");
        if (this.mFlashNotifications.size() <= 0) {
            this.mCurrentFlashNotification = null;
            SystemProperties.set("service.cameraflashnoti.running", "0");
            Log.i("FlashNotifController", "set service.cameraflashnoti.running to 0 in startNextFlashNotificationLocked");
            return;
        }
        startFlashNotificationLocked((FlashNotification) this.mFlashNotifications.getFirst());
    }

    public final void startFlashNotificationLocked(FlashNotification flashNotification) {
        Log.i("FlashNotifController", "startFlashNotificationLocked: type=" + flashNotification.mType + ", tag=" + flashNotification.mTag);
        if (flashNotification.mNotiType == 0) {
            Log.i("FlashNotifController", "startFlashNotificationLocked: flash notification cannot be started.");
            SystemProperties.set("service.cameraflashnoti.running", "0");
            Log.i("FlashNotifController", "set service.cameraflashnoti.running to 0 in startFlashNotificationLocked");
        } else {
            this.mCurrentFlashNotification = flashNotification;
            this.mThread = new FlashNotificationThread(flashNotification);
            this.mFlashNotificationHandler.post(this.mThread);
        }
    }

    public final boolean isDozeMode() {
        int i = this.mDisplayState;
        return i == 3 || i == 4;
    }

    public final void doCameraFlashNotificationOn() {
        if (this.mIsCameraFlashNotificationEnabled && !this.mIsTorchOn) {
            doCameraFlashNotification(true);
        }
        Log.i("FlashNotifController", "doCameraFlashNotificationOn: isCameraFlashNotificationEnabled=" + this.mIsCameraFlashNotificationEnabled + ", isTorchOn=" + this.mIsTorchOn + ", isTorchTouched=" + this.mIsTorchTouched);
    }

    public final void doCameraFlashNotificationOff() {
        if (this.mIsTorchTouched) {
            doCameraFlashNotification(false);
        }
        Log.i("FlashNotifController", "doCameraFlashNotificationOff: isCameraFlashNotificationEnabled=" + this.mIsCameraFlashNotificationEnabled + ", isTorchOn=" + this.mIsTorchOn + ", isTorchTouched=" + this.mIsTorchTouched);
    }

    public final void doScreenFlashNotificationOn(int i, boolean z) {
        boolean isDozeMode = isDozeMode();
        if ((this.mIsScreenFlashNotificationEnabled || z) && !isDozeMode) {
            showScreenNotificationOverlayView(i);
        }
        Log.i("FlashNotifController", "doScreenFlashNotificationOn: isScreenFlashNotificationEnabled=" + this.mIsScreenFlashNotificationEnabled + ", isDozeMode=" + isDozeMode + ", color=" + Integer.toHexString(i));
    }

    public final void doScreenFlashNotificationOff() {
        hideScreenNotificationOverlayView();
        Log.i("FlashNotifController", "doScreenFlashNotificationOff: isScreenFlashNotificationEnabled=" + this.mIsScreenFlashNotificationEnabled);
    }

    public final void doCameraFlashNotification(boolean z) {
        String str;
        Log.d("FlashNotifController", "doCameraFlashNotification: " + z + " mCameraId : " + this.mCameraId);
        CameraManager cameraManager = this.mCameraManager;
        if (cameraManager != null && (str = this.mCameraId) != null) {
            try {
                cameraManager.setTorchMode(str, z);
                this.mIsTorchTouched = z;
                return;
            } catch (CameraAccessException e) {
                Log.e("FlashNotifController", "Failed to setTorchMode: " + e);
                return;
            }
        }
        Log.e("FlashNotifController", "Can not use camera flash notification, please check CameraManager!");
    }

    public class FlashNotification {
        public int mColor;
        public final IBinder.DeathRecipient mDeathRecipient;
        public final boolean mForceStartScreenFlash;
        public int mNotiType;
        public final int mOffDuration;
        public final int mOnDuration;
        public int mRepeat;
        public final String mTag;
        public final IBinder mToken;
        public final int mType;

        public FlashNotification(String str, int i, int i2) {
            this(str, i, i2, (IBinder) null, (IBinder.DeathRecipient) null);
        }

        public FlashNotification(String str, int i, int i2, IBinder iBinder, IBinder.DeathRecipient deathRecipient) {
            this.mType = i;
            this.mTag = str;
            this.mColor = i2;
            this.mToken = iBinder;
            this.mDeathRecipient = deathRecipient;
            if (i == 2) {
                this.mOnDuration = 700;
                this.mOffDuration = 700;
                this.mRepeat = 0;
                this.mForceStartScreenFlash = false;
                return;
            }
            if (i == 3) {
                this.mOnDuration = 5000;
                this.mOffDuration = 1000;
                this.mRepeat = 1;
                this.mForceStartScreenFlash = true;
                return;
            }
            this.mOnDuration = 350;
            this.mOffDuration = FrameworkStatsLog.f446x58c6c32;
            this.mRepeat = 2;
            this.mForceStartScreenFlash = false;
        }

        public FlashNotification(Context context, String str, String str2, int i, int i2) {
            this(context, str, str2, i, i2, null, null);
        }

        public FlashNotification(Context context, String str, String str2, int i, int i2, IBinder iBinder, IBinder.DeathRecipient deathRecipient) {
            this(str, i, i2, iBinder, deathRecipient);
            if (!"alarm".equals(this.mTag) && !"preview".equals(this.mTag)) {
                int intForUser = Settings.System.getIntForUser(context.getContentResolver(), FlashNotificationsController.SETTING_KEY_SCREEN_FLASH_NOTIFICATION_COLOR_MODE, 0, -2);
                boolean isCameraFlashAvailableForPackage = isCameraFlashAvailableForPackage(context, str2);
                String[] screenFlashColorElements = getScreenFlashColorElements(context, str2);
                boolean z = intForUser == 0 || screenFlashColorElements != null;
                if (isCameraFlashAvailableForPackage && z) {
                    this.mNotiType = 3;
                } else if (isCameraFlashAvailableForPackage) {
                    this.mNotiType = 1;
                } else if (z) {
                    this.mNotiType = 2;
                } else {
                    this.mNotiType = 0;
                }
                if (intForUser == 1 && z) {
                    this.mColor = Color.parseColor(FlashNotificationsController.SCREEN_FLASH_NOTIFICATION_COLOR_APPS_ITEM_SEPARATOR + screenFlashColorElements[1]);
                    return;
                }
                return;
            }
            this.mNotiType = 3;
        }

        public boolean tryLinkToDeath() {
            IBinder.DeathRecipient deathRecipient;
            IBinder iBinder = this.mToken;
            if (iBinder != null && (deathRecipient = this.mDeathRecipient) != null) {
                try {
                    iBinder.linkToDeath(deathRecipient, 0);
                    return true;
                } catch (RemoteException e) {
                    Log.e("FlashNotifController", "RemoteException", e);
                }
            }
            return false;
        }

        public boolean tryUnlinkToDeath() {
            IBinder.DeathRecipient deathRecipient;
            IBinder iBinder = this.mToken;
            if (iBinder != null && (deathRecipient = this.mDeathRecipient) != null) {
                try {
                    iBinder.unlinkToDeath(deathRecipient, 0);
                    return true;
                } catch (Exception unused) {
                }
            }
            return false;
        }

        public final boolean isCameraFlashAvailableForPackage(Context context, String str) {
            String stringForUser = Settings.Secure.getStringForUser(context.getContentResolver(), FlashNotificationsController.SETTING_KEY_CAMERA_FLASH_NOTIFICATION_APP_LIST, -2);
            if (TextUtils.isEmpty(stringForUser) || FlashNotificationsController.CAMERA_FLASH_NOTIFICATION_ALL_APPS.equals(stringForUser)) {
                return true;
            }
            for (String str2 : stringForUser.split(Character.toString(FlashNotificationsController.SCREEN_FLASH_NOTIFICATION_COLOR_APPS_LIST_SEPARATOR))) {
                if (str2.equals(str)) {
                    return true;
                }
            }
            return false;
        }

        public final String[] getScreenFlashColorElements(Context context, String str) {
            String stringForUser = Settings.Secure.getStringForUser(context.getContentResolver(), FlashNotificationsController.SETTING_KEY_SCREEN_FLASH_NOTIFICATION_COLOR_APPS, -2);
            if (TextUtils.isEmpty(stringForUser)) {
                return null;
            }
            for (String str2 : stringForUser.split(Character.toString(FlashNotificationsController.SCREEN_FLASH_NOTIFICATION_COLOR_APPS_LIST_SEPARATOR))) {
                String[] split = str2.split(Character.toString(FlashNotificationsController.SCREEN_FLASH_NOTIFICATION_COLOR_APPS_ITEM_SEPARATOR));
                if (str.equals(split[0]) && "1".equals(split[2])) {
                    return split;
                }
            }
            return null;
        }
    }

    public class FlashNotificationThread extends Thread {
        public int mColor;
        public final FlashNotification mFlashNotification;
        public boolean mForceStop;
        public boolean mShouldDoCameraFlash;
        public boolean mShouldDoScreenFlash;

        public FlashNotificationThread(FlashNotification flashNotification) {
            this.mColor = 0;
            this.mShouldDoScreenFlash = false;
            this.mShouldDoCameraFlash = false;
            this.mFlashNotification = flashNotification;
            this.mForceStop = false;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Log.d("FlashNotifController", "run started: " + this.mFlashNotification.mTag);
            Process.setThreadPriority(-8);
            this.mColor = this.mFlashNotification.mColor;
            int i = this.mFlashNotification.mNotiType;
            boolean z = false;
            this.mShouldDoScreenFlash = (i & 2) == 2;
            if ((i & 1) == 1 && this.mFlashNotification.mType != 3) {
                z = true;
            }
            this.mShouldDoCameraFlash = z;
            Log.d("FlashNotifController", "mShouldDoScreenFlash: " + this.mShouldDoScreenFlash + ", mShouldDoCameraFlash: " + this.mShouldDoCameraFlash);
            synchronized (this) {
                FlashNotificationsController.this.mWakeLock.acquire(BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
                try {
                    startFlashNotification();
                } finally {
                    FlashNotificationsController.this.doScreenFlashNotificationOff();
                    FlashNotificationsController.this.doCameraFlashNotificationOff();
                    try {
                        FlashNotificationsController.this.mWakeLock.release();
                    } catch (RuntimeException unused) {
                        Log.e("FlashNotifController", "Error while releasing FlashNotificationsController wakelock (already released by the system?)");
                    }
                }
            }
            synchronized (FlashNotificationsController.this.mFlashNotifications) {
                if (FlashNotificationsController.this.mThread == this) {
                    FlashNotificationsController.this.mThread = null;
                }
                if (!this.mForceStop) {
                    this.mFlashNotification.tryUnlinkToDeath();
                    FlashNotificationsController.this.mCurrentFlashNotification = null;
                }
            }
            SystemProperties.set("service.cameraflashnoti.running", "0");
            Log.i("FlashNotifController", "set service.cameraflashnoti.running to 0 in run");
            Log.d("FlashNotifController", "run finished: " + this.mFlashNotification.mTag);
        }

        public final void startFlashNotification() {
            synchronized (this) {
                while (!this.mForceStop) {
                    if (this.mFlashNotification.mType != 2 && this.mFlashNotification.mRepeat >= 0) {
                        FlashNotification flashNotification = this.mFlashNotification;
                        int i = flashNotification.mRepeat;
                        flashNotification.mRepeat = i - 1;
                        if (i == 0) {
                            break;
                        }
                    }
                    if (this.mShouldDoScreenFlash) {
                        FlashNotificationsController.this.doScreenFlashNotificationOn(this.mColor, this.mFlashNotification.mForceStartScreenFlash);
                    }
                    if (this.mShouldDoCameraFlash) {
                        FlashNotificationsController.this.doCameraFlashNotificationOn();
                    }
                    delay(this.mFlashNotification.mOnDuration);
                    FlashNotificationsController.this.doScreenFlashNotificationOff();
                    FlashNotificationsController.this.doCameraFlashNotificationOff();
                    if (this.mForceStop) {
                        break;
                    } else {
                        delay(this.mFlashNotification.mOffDuration);
                    }
                }
            }
        }

        public void cancel() {
            Log.d("FlashNotifController", "run canceled: " + this.mFlashNotification.mTag);
            synchronized (this) {
                FlashNotificationsController.this.mThread.mForceStop = true;
                FlashNotificationsController.this.mThread.notify();
            }
        }

        public final void delay(long j) {
            if (j > 0) {
                long uptimeMillis = SystemClock.uptimeMillis() + j;
                do {
                    try {
                        wait(j);
                    } catch (InterruptedException unused) {
                    }
                    if (this.mForceStop) {
                        return;
                    } else {
                        j = uptimeMillis - SystemClock.uptimeMillis();
                    }
                } while (j > 0);
            }
        }
    }

    class FlashBroadcastReceiver extends BroadcastReceiver {
        public FlashBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                if (UserHandle.myUserId() != ActivityManager.getCurrentUser()) {
                    return;
                }
                FlashNotificationsController flashNotificationsController = FlashNotificationsController.this;
                flashNotificationsController.mIsCameraFlashNotificationEnabled = Settings.System.getIntForUser(flashNotificationsController.mContext.getContentResolver(), FlashNotificationsController.SETTING_KEY_CAMERA_FLASH_NOTIFICATION, 0, -2) != 0;
                if (FlashNotificationsController.this.mIsCameraFlashNotificationEnabled) {
                    FlashNotificationsController.this.prepareForCameraFlashNotification();
                } else if (FlashNotificationsController.this.mCameraManager != null) {
                    FlashNotificationsController.this.mCameraManager.unregisterTorchCallback(FlashNotificationsController.this.mTorchCallback);
                }
                FlashNotificationsController flashNotificationsController2 = FlashNotificationsController.this;
                flashNotificationsController2.mCameraManager = (CameraManager) flashNotificationsController2.mContext.getSystemService(CameraManager.class);
                CameraManager cameraManager = FlashNotificationsController.this.mCameraManager;
                FlashNotificationsController flashNotificationsController3 = FlashNotificationsController.this;
                cameraManager.registerAvailabilityCallback(flashNotificationsController3.mTorchAvailabilityCallback, flashNotificationsController3.mCallbackHandler);
                return;
            }
            if (FlashNotificationsController.ACTION_FLASH_NOTIFICATION_START_PREVIEW.equals(intent.getAction())) {
                Log.i("FlashNotifController", "ACTION_FLASH_NOTIFICATION_START_PREVIEW");
                int intExtra = intent.getIntExtra(FlashNotificationsController.EXTRA_FLASH_NOTIFICATION_PREVIEW_COLOR, 0);
                int intExtra2 = intent.getIntExtra(FlashNotificationsController.EXTRA_FLASH_NOTIFICATION_PREVIEW_TYPE, 0);
                if (intExtra2 == 1) {
                    FlashNotificationsController.this.startFlashNotificationLongPreview(intExtra);
                    return;
                } else {
                    if (intExtra2 == 0) {
                        FlashNotificationsController.this.startFlashNotificationShortPreview();
                        return;
                    }
                    return;
                }
            }
            if (FlashNotificationsController.ACTION_FLASH_NOTIFICATION_STOP_PREVIEW.equals(intent.getAction())) {
                Log.i("FlashNotifController", "ACTION_FLASH_NOTIFICATION_STOP_PREVIEW");
                FlashNotificationsController.this.stopFlashNotificationLongPreview();
            }
        }
    }

    public final class FlashContentObserver extends ContentObserver {
        public final Uri mCameraFlashNotificationUri;
        public final Uri mScreenFlashNotificationUri;

        public FlashContentObserver(Handler handler) {
            super(handler);
            this.mCameraFlashNotificationUri = Settings.System.getUriFor(FlashNotificationsController.SETTING_KEY_CAMERA_FLASH_NOTIFICATION);
            this.mScreenFlashNotificationUri = Settings.System.getUriFor(FlashNotificationsController.SETTING_KEY_SCREEN_FLASH_NOTIFICATION);
        }

        public void register(ContentResolver contentResolver) {
            contentResolver.registerContentObserver(this.mCameraFlashNotificationUri, false, this, -1);
            contentResolver.registerContentObserver(this.mScreenFlashNotificationUri, false, this, -1);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            if (this.mCameraFlashNotificationUri.equals(uri)) {
                FlashNotificationsController flashNotificationsController = FlashNotificationsController.this;
                flashNotificationsController.mIsCameraFlashNotificationEnabled = Settings.System.getIntForUser(flashNotificationsController.mContext.getContentResolver(), FlashNotificationsController.SETTING_KEY_CAMERA_FLASH_NOTIFICATION, 0, -2) != 0;
                if (FlashNotificationsController.this.mIsCameraFlashNotificationEnabled) {
                    FlashNotificationsController.this.prepareForCameraFlashNotification();
                    return;
                }
                FlashNotificationsController.this.mIsTorchOn = false;
                if (FlashNotificationsController.this.mCameraManager != null) {
                    FlashNotificationsController.this.mCameraManager.unregisterTorchCallback(FlashNotificationsController.this.mTorchCallback);
                }
                SystemProperties.set("service.cameraflashnoti.running", "0");
                Log.i("FlashNotifController", "set service.cameraflashnoti.running to 0 in onChange");
                return;
            }
            if (this.mScreenFlashNotificationUri.equals(uri)) {
                FlashNotificationsController flashNotificationsController2 = FlashNotificationsController.this;
                flashNotificationsController2.mIsScreenFlashNotificationEnabled = Settings.System.getIntForUser(flashNotificationsController2.mContext.getContentResolver(), FlashNotificationsController.SETTING_KEY_SCREEN_FLASH_NOTIFICATION, 0, -2) != 0;
            }
        }
    }
}
