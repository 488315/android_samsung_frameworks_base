package com.android.server.accessibility;

import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.display.DisplayManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.FeatureFlagUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FlashNotificationsController {
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
    public boolean mIsCameraFlashNotificationRunning;
    public boolean mIsCameraOpened;
    public boolean mIsScreenFlashNotificationEnabled;
    public boolean mIsTorchOn;
    public boolean mIsTorchTouched;
    public final Handler mMainHandler;
    public View mScreenFlashNotificationOverlayView;
    public volatile FlashNotificationThread mThread;
    final CameraManager.AvailabilityCallback mTorchAvailabilityCallback;
    public final AnonymousClass1 mTorchCallback;
    public final PowerManager.WakeLock mWakeLock;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class FlashBroadcastReceiver extends BroadcastReceiver {
        public FlashBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                if (UserHandle.myUserId() != ActivityManager.getCurrentUser()) {
                    return;
                }
                FlashNotificationsController flashNotificationsController = FlashNotificationsController.this;
                flashNotificationsController.mIsCameraFlashNotificationEnabled = Settings.System.getIntForUser(flashNotificationsController.mContext.getContentResolver(), FlashNotificationsController.SETTING_KEY_CAMERA_FLASH_NOTIFICATION, 0, -2) != 0;
                FlashNotificationsController flashNotificationsController2 = FlashNotificationsController.this;
                if (flashNotificationsController2.mIsCameraFlashNotificationEnabled) {
                    flashNotificationsController2.prepareForCameraFlashNotification();
                } else {
                    CameraManager cameraManager = flashNotificationsController2.mCameraManager;
                    if (cameraManager != null) {
                        cameraManager.unregisterTorchCallback(flashNotificationsController2.mTorchCallback);
                    }
                }
                FlashNotificationsController flashNotificationsController3 = FlashNotificationsController.this;
                flashNotificationsController3.mCameraManager = (CameraManager) flashNotificationsController3.mContext.getSystemService(CameraManager.class);
                FlashNotificationsController flashNotificationsController4 = FlashNotificationsController.this;
                flashNotificationsController4.mCameraManager.registerAvailabilityCallback(flashNotificationsController4.mTorchAvailabilityCallback, flashNotificationsController4.mCallbackHandler);
                return;
            }
            if (!FlashNotificationsController.ACTION_FLASH_NOTIFICATION_START_PREVIEW.equals(intent.getAction())) {
                if (FlashNotificationsController.ACTION_FLASH_NOTIFICATION_STOP_PREVIEW.equals(intent.getAction())) {
                    Log.i("FlashNotifController", "ACTION_FLASH_NOTIFICATION_STOP_PREVIEW");
                    FlashNotificationsController.this.stopFlashNotification("preview");
                    return;
                }
                return;
            }
            Log.i("FlashNotifController", "ACTION_FLASH_NOTIFICATION_START_PREVIEW");
            int intExtra = intent.getIntExtra(FlashNotificationsController.EXTRA_FLASH_NOTIFICATION_PREVIEW_COLOR, 0);
            int intExtra2 = intent.getIntExtra(FlashNotificationsController.EXTRA_FLASH_NOTIFICATION_PREVIEW_TYPE, 0);
            if (intExtra2 == 1) {
                FlashNotificationsController flashNotificationsController5 = FlashNotificationsController.this;
                flashNotificationsController5.requestStartFlashNotification(new FlashNotification(3, intExtra, flashNotificationsController5.mContext, "preview", "preview"));
            } else if (intExtra2 == 0) {
                FlashNotificationsController flashNotificationsController6 = FlashNotificationsController.this;
                flashNotificationsController6.requestStartFlashNotification(new FlashNotification(1, flashNotificationsController6.getScreenFlashColorPreference$2(), flashNotificationsController6.mContext, "preview", "preview"));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FlashContentObserver extends ContentObserver {
        public final Uri mCameraFlashNotificationUri;
        public final Uri mScreenFlashNotificationUri;

        public FlashContentObserver(Handler handler) {
            super(handler);
            this.mCameraFlashNotificationUri = Settings.System.getUriFor(FlashNotificationsController.SETTING_KEY_CAMERA_FLASH_NOTIFICATION);
            this.mScreenFlashNotificationUri = Settings.System.getUriFor(FlashNotificationsController.SETTING_KEY_SCREEN_FLASH_NOTIFICATION);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (!this.mCameraFlashNotificationUri.equals(uri)) {
                if (this.mScreenFlashNotificationUri.equals(uri)) {
                    FlashNotificationsController flashNotificationsController = FlashNotificationsController.this;
                    flashNotificationsController.mIsScreenFlashNotificationEnabled = Settings.System.getIntForUser(flashNotificationsController.mContext.getContentResolver(), FlashNotificationsController.SETTING_KEY_SCREEN_FLASH_NOTIFICATION, 0, -2) != 0;
                    return;
                }
                return;
            }
            FlashNotificationsController flashNotificationsController2 = FlashNotificationsController.this;
            flashNotificationsController2.mIsCameraFlashNotificationEnabled = Settings.System.getIntForUser(flashNotificationsController2.mContext.getContentResolver(), FlashNotificationsController.SETTING_KEY_CAMERA_FLASH_NOTIFICATION, 0, -2) != 0;
            FlashNotificationsController flashNotificationsController3 = FlashNotificationsController.this;
            if (flashNotificationsController3.mIsCameraFlashNotificationEnabled) {
                flashNotificationsController3.prepareForCameraFlashNotification();
                return;
            }
            flashNotificationsController3.mIsTorchOn = false;
            CameraManager cameraManager = flashNotificationsController3.mCameraManager;
            if (cameraManager != null) {
                cameraManager.unregisterTorchCallback(flashNotificationsController3.mTorchCallback);
            }
            FlashNotificationsController.this.mIsCameraFlashNotificationRunning = false;
            Log.i("FlashNotifController", "mIsCameraFlashNotificationRunning false in onChange");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FlashNotification {
        public final int mColor;
        public final IBinder.DeathRecipient mDeathRecipient;
        public final boolean mForceStartScreenFlash;
        public final int mNotiType;
        public final int mOffDuration;
        public final int mOnDuration;
        public int mRepeat;
        public final String mTag;
        public final IBinder mToken;
        public final int mType;

        public FlashNotification(int i, int i2, Context context, String str, String str2) {
            this(context, str, str2, i, i2, null, null);
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x0071  */
        /* JADX WARN: Removed duplicated region for block: B:40:0x00b1  */
        /* JADX WARN: Removed duplicated region for block: B:41:0x00b4  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public FlashNotification(android.content.Context r7, java.lang.String r8, java.lang.String r9, int r10, int r11, android.os.IBinder r12, com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticLambda1 r13) {
            /*
                r6 = this;
                r0 = r6
                r1 = r8
                r2 = r10
                r3 = r11
                r4 = r12
                r5 = r13
                r0.<init>(r1, r2, r3, r4, r5)
                java.lang.String r10 = "alarm"
                boolean r10 = r10.equals(r8)
                r11 = 3
                if (r10 != 0) goto Ld6
                java.lang.String r10 = "preview"
                boolean r8 = r10.equals(r8)
                if (r8 != 0) goto Ld6
                android.content.ContentResolver r8 = r7.getContentResolver()
                java.lang.String r10 = "screen_flash_notification_color_mode"
                r12 = 0
                r13 = -2
                int r8 = android.provider.Settings.System.getIntForUser(r8, r10, r12, r13)
                android.content.ContentResolver r10 = r7.getContentResolver()
                java.lang.String r0 = "camera_flash_notification_app_list"
                java.lang.String r10 = android.provider.Settings.Secure.getStringForUser(r10, r0, r13)
                boolean r0 = android.text.TextUtils.isEmpty(r10)
                r1 = 1
                r2 = 59
                if (r0 != 0) goto L59
                java.lang.String r0 = "all"
                boolean r0 = r0.equals(r10)
                if (r0 == 0) goto L45
                goto L59
            L45:
                java.lang.String r0 = java.lang.Character.toString(r2)
                java.lang.String[] r10 = r10.split(r0)
                int r0 = r10.length
                r3 = r12
            L4f:
                if (r3 >= r0) goto L5e
                r4 = r10[r3]
                boolean r4 = r4.equals(r9)
                if (r4 == 0) goto L5b
            L59:
                r10 = r1
                goto L5f
            L5b:
                int r3 = r3 + 1
                goto L4f
            L5e:
                r10 = r12
            L5f:
                android.content.ContentResolver r7 = r7.getContentResolver()
                java.lang.String r0 = "screen_flash_notification_color_apps"
                java.lang.String r7 = android.provider.Settings.Secure.getStringForUser(r7, r0, r13)
                boolean r13 = android.text.TextUtils.isEmpty(r7)
                r0 = 2
                if (r13 != 0) goto L9f
                java.lang.String r13 = java.lang.Character.toString(r2)
                java.lang.String[] r7 = r7.split(r13)
                int r13 = r7.length
                r2 = r12
            L7b:
                if (r2 >= r13) goto L9f
                r3 = r7[r2]
                r4 = 35
                java.lang.String r4 = java.lang.Character.toString(r4)
                java.lang.String[] r3 = r3.split(r4)
                r4 = r3[r12]
                boolean r4 = r9.equals(r4)
                if (r4 == 0) goto L9c
                r4 = r3[r0]
                java.lang.String r5 = "1"
                boolean r4 = r5.equals(r4)
                if (r4 == 0) goto L9c
                goto La0
            L9c:
                int r2 = r2 + 1
                goto L7b
            L9f:
                r3 = 0
            La0:
                if (r8 == 0) goto La7
                if (r3 == 0) goto La5
                goto La7
            La5:
                r7 = r12
                goto La8
            La7:
                r7 = r1
            La8:
                if (r10 == 0) goto Laf
                if (r7 == 0) goto Laf
                r6.mNotiType = r11
                goto Lbb
            Laf:
                if (r10 == 0) goto Lb4
                r6.mNotiType = r1
                goto Lbb
            Lb4:
                if (r7 == 0) goto Lb9
                r6.mNotiType = r0
                goto Lbb
            Lb9:
                r6.mNotiType = r12
            Lbb:
                if (r8 != r1) goto Ld8
                if (r7 == 0) goto Ld8
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                java.lang.String r8 = "#"
                r7.<init>(r8)
                r8 = r3[r1]
                r7.append(r8)
                java.lang.String r7 = r7.toString()
                int r7 = android.graphics.Color.parseColor(r7)
                r6.mColor = r7
                goto Ld8
            Ld6:
                r6.mNotiType = r11
            Ld8:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.FlashNotificationsController.FlashNotification.<init>(android.content.Context, java.lang.String, java.lang.String, int, int, android.os.IBinder, com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticLambda1):void");
        }

        public FlashNotification(String str, int i, int i2, IBinder iBinder, FlashNotificationsController$$ExternalSyntheticLambda1 flashNotificationsController$$ExternalSyntheticLambda1) {
            this.mType = i;
            this.mTag = str;
            this.mColor = i2;
            this.mToken = iBinder;
            this.mDeathRecipient = flashNotificationsController$$ExternalSyntheticLambda1;
            if (i == 2) {
                this.mOnDuration = 700;
                this.mOffDuration = 700;
                this.mRepeat = 0;
                this.mForceStartScreenFlash = false;
                return;
            }
            if (i != 3) {
                this.mOnDuration = 350;
                this.mOffDuration = FrameworkStatsLog.CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_MACRO_RAW_SR_MERGE;
                this.mRepeat = 2;
                this.mForceStartScreenFlash = false;
                return;
            }
            this.mOnDuration = 5000;
            this.mOffDuration = 1000;
            this.mRepeat = 1;
            this.mForceStartScreenFlash = true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FlashNotificationThread extends Thread {
        public final FlashNotification mFlashNotification;
        public int mColor = 0;
        public boolean mShouldDoScreenFlash = false;
        public boolean mShouldDoCameraFlash = false;
        public boolean mForceStop = false;

        public FlashNotificationThread(FlashNotification flashNotification) {
            this.mFlashNotification = flashNotification;
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

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            IBinder.DeathRecipient deathRecipient;
            Log.d("FlashNotifController", "run started: " + this.mFlashNotification.mTag);
            Process.setThreadPriority(-8);
            FlashNotification flashNotification = this.mFlashNotification;
            this.mColor = flashNotification.mColor;
            int i = flashNotification.mNotiType;
            this.mShouldDoScreenFlash = (i & 2) == 2;
            this.mShouldDoCameraFlash = (i & 1) == 1 && flashNotification.mType != 3;
            StringBuilder sb = new StringBuilder("mShouldDoScreenFlash: ");
            sb.append(this.mShouldDoScreenFlash);
            sb.append(", mShouldDoCameraFlash: ");
            RCPManagerService$$ExternalSyntheticOutline0.m("FlashNotifController", sb, this.mShouldDoCameraFlash);
            synchronized (this) {
                FlashNotificationsController.this.mWakeLock.acquire(300000L);
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
                try {
                    if (FlashNotificationsController.this.mThread == this) {
                        FlashNotificationsController.this.mThread = null;
                    }
                    if (!this.mForceStop) {
                        FlashNotification flashNotification2 = this.mFlashNotification;
                        IBinder iBinder = flashNotification2.mToken;
                        if (iBinder != null && (deathRecipient = flashNotification2.mDeathRecipient) != null) {
                            try {
                                iBinder.unlinkToDeath(deathRecipient, 0);
                            } catch (Exception unused2) {
                            }
                        }
                        FlashNotificationsController.this.mCurrentFlashNotification = null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            FlashNotificationsController.this.mIsCameraFlashNotificationRunning = false;
            Log.i("FlashNotifController", "mIsCameraFlashNotificationRunning false in thread run");
            VpnManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("run finished: "), this.mFlashNotification.mTag, "FlashNotifController");
        }

        public final void startFlashNotification() {
            int i;
            synchronized (this) {
                while (!this.mForceStop) {
                    try {
                        FlashNotification flashNotification = this.mFlashNotification;
                        if (flashNotification.mType != 2 && (i = flashNotification.mRepeat) >= 0) {
                            flashNotification.mRepeat = i - 1;
                            if (i == 0) {
                            }
                        }
                        if (this.mShouldDoScreenFlash) {
                            FlashNotificationsController.m134$$Nest$mdoScreenFlashNotificationOn(FlashNotificationsController.this, this.mColor, flashNotification.mForceStartScreenFlash);
                        }
                        if (this.mShouldDoCameraFlash) {
                            FlashNotificationsController.m133$$Nest$mdoCameraFlashNotificationOn(FlashNotificationsController.this);
                        }
                        delay(this.mFlashNotification.mOnDuration);
                        FlashNotificationsController.this.doScreenFlashNotificationOff();
                        FlashNotificationsController.this.doCameraFlashNotificationOff();
                        if (this.mForceStop) {
                            break;
                        } else {
                            delay(this.mFlashNotification.mOffDuration);
                        }
                    } finally {
                    }
                }
            }
        }
    }

    /* renamed from: -$$Nest$mdoCameraFlashNotificationOn, reason: not valid java name */
    public static void m133$$Nest$mdoCameraFlashNotificationOn(FlashNotificationsController flashNotificationsController) {
        if (flashNotificationsController.mIsCameraFlashNotificationEnabled && !flashNotificationsController.mIsTorchOn) {
            flashNotificationsController.doCameraFlashNotification(true);
        }
        StringBuilder sb = new StringBuilder("doCameraFlashNotificationOn: isCameraFlashNotificationEnabled=");
        sb.append(flashNotificationsController.mIsCameraFlashNotificationEnabled);
        sb.append(", isTorchOn=");
        sb.append(flashNotificationsController.mIsTorchOn);
        sb.append(", isTorchTouched=");
        FlashNotificationsController$$ExternalSyntheticOutline0.m("FlashNotifController", sb, flashNotificationsController.mIsTorchTouched);
    }

    /* renamed from: -$$Nest$mdoScreenFlashNotificationOn, reason: not valid java name */
    public static void m134$$Nest$mdoScreenFlashNotificationOn(FlashNotificationsController flashNotificationsController, int i, boolean z) {
        int i2 = flashNotificationsController.mDisplayState;
        boolean z2 = i2 == 3 || i2 == 4;
        if ((flashNotificationsController.mIsScreenFlashNotificationEnabled || z) && !z2) {
            flashNotificationsController.mMainHandler.sendMessage(PooledLambda.obtainMessage(new FlashNotificationsController$$ExternalSyntheticLambda4(1), flashNotificationsController, Integer.valueOf(i)));
        }
        StringBuilder sb = new StringBuilder("doScreenFlashNotificationOn: isScreenFlashNotificationEnabled=");
        BatteryService$$ExternalSyntheticOutline0.m(sb, flashNotificationsController.mIsScreenFlashNotificationEnabled, ", isDozeMode=", z2, ", color=");
        sb.append(Integer.toHexString(i));
        Log.i("FlashNotifController", sb.toString());
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public FlashNotificationsController(android.content.Context r4) {
        /*
            r3 = this;
            android.os.HandlerThread r0 = new android.os.HandlerThread
            java.lang.String r1 = "FlashNotificationThread"
            r0.<init>(r1)
            r0.start()
            android.os.Handler r0 = r0.getThreadHandler()
            android.os.HandlerThread r1 = new android.os.HandlerThread
            java.lang.String r2 = "FlashNotifController"
            r1.<init>(r2)
            r1.start()
            android.os.Handler r1 = r1.getThreadHandler()
            r3.<init>(r4, r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.FlashNotificationsController.<init>(android.content.Context):void");
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.server.accessibility.FlashNotificationsController$1] */
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
        this.mIsCameraFlashNotificationRunning = false;
        this.mTorchCallback = new CameraManager.TorchCallback() { // from class: com.android.server.accessibility.FlashNotificationsController.1
            @Override // android.hardware.camera2.CameraManager.TorchCallback
            public final void onTorchModeChanged(String str, boolean z) {
                String str2 = FlashNotificationsController.this.mCameraId;
                if (str2 == null || !str2.equals(str)) {
                    return;
                }
                FlashNotificationsController.this.mIsTorchOn = z;
                AccessibilityManagerService$$ExternalSyntheticOutline0.m("onTorchModeChanged, set mIsTorchOn=", "FlashNotifController", z);
            }
        };
        this.mTorchAvailabilityCallback = new CameraManager.AvailabilityCallback() { // from class: com.android.server.accessibility.FlashNotificationsController.2
            public final void onCameraClosed(String str) {
                String str2 = FlashNotificationsController.this.mCameraId;
                if (str2 == null || !str2.equals(str)) {
                    return;
                }
                FlashNotificationsController.this.mIsCameraOpened = false;
            }

            public final void onCameraOpened(String str, String str2) {
                String str3 = FlashNotificationsController.this.mCameraId;
                if (str3 == null || !str3.equals(str)) {
                    return;
                }
                FlashNotificationsController.this.mIsCameraOpened = true;
            }
        };
        new AudioManager.AudioPlaybackCallback() { // from class: com.android.server.accessibility.FlashNotificationsController.3
            @Override // android.media.AudioManager.AudioPlaybackCallback
            public final void onPlaybackConfigChanged(List list) {
                boolean anyMatch = list != null ? list.stream().anyMatch(new FlashNotificationsController$3$$ExternalSyntheticLambda0()) : false;
                if (FlashNotificationsController.this.mIsAlarming != anyMatch) {
                    AccessibilityManagerService$$ExternalSyntheticOutline0.m("alarm state changed: ", "FlashNotifController", anyMatch);
                    if (anyMatch) {
                        FlashNotificationsController flashNotificationsController = FlashNotificationsController.this;
                        flashNotificationsController.requestStartFlashNotification(new FlashNotification("alarm", 2, flashNotificationsController.getScreenFlashColorPreference$2(), (IBinder) null, (FlashNotificationsController$$ExternalSyntheticLambda1) null));
                    } else {
                        FlashNotificationsController.this.stopFlashNotification("alarm");
                    }
                    FlashNotificationsController.this.mIsAlarming = anyMatch;
                }
            }
        };
        this.mContext = context;
        Handler handler3 = new Handler(context.getMainLooper());
        this.mMainHandler = handler3;
        this.mFlashNotificationHandler = handler;
        this.mCallbackHandler = handler2;
        FlashContentObserver flashContentObserver = new FlashContentObserver(handler3);
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.registerContentObserver(flashContentObserver.mCameraFlashNotificationUri, false, flashContentObserver, -1);
        contentResolver.registerContentObserver(flashContentObserver.mScreenFlashNotificationUri, false, flashContentObserver, -1);
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
            public final void onDisplayAdded(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i) {
                Display display;
                DisplayManager displayManager2 = FlashNotificationsController.this.mDisplayManager;
                if (displayManager2 == null || (display = displayManager2.getDisplay(i)) == null) {
                    return;
                }
                FlashNotificationsController.this.mDisplayState = display.getState();
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayRemoved(int i) {
            }
        };
        if (displayManager != null) {
            displayManager.registerDisplayListener(displayListener, null);
        }
    }

    public static void fadeScreenNotificationOverlayViewMainThread(View view, boolean z) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "alpha", z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
        ofFloat.setInterpolator(new AccelerateInterpolator());
        ofFloat.setAutoCancel(true);
        ofFloat.setDuration(200L);
        ofFloat.start();
    }

    public final void doCameraFlashNotification(boolean z) {
        String str;
        VpnManagerService$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m("doCameraFlashNotification: ", " mCameraId : ", z), this.mCameraId, "FlashNotifController");
        CameraManager cameraManager = this.mCameraManager;
        if (cameraManager == null || (str = this.mCameraId) == null) {
            Log.e("FlashNotifController", "Can not use camera flash notification, please check CameraManager!");
            return;
        }
        try {
            cameraManager.setTorchMode(str, z);
            this.mIsTorchTouched = z;
        } catch (CameraAccessException e) {
            Log.e("FlashNotifController", "Failed to setTorchMode: " + e);
        }
    }

    public final void doCameraFlashNotificationOff() {
        if (this.mIsTorchTouched) {
            doCameraFlashNotification(false);
        }
        StringBuilder sb = new StringBuilder("doCameraFlashNotificationOff: isCameraFlashNotificationEnabled=");
        sb.append(this.mIsCameraFlashNotificationEnabled);
        sb.append(", isTorchOn=");
        sb.append(this.mIsTorchOn);
        sb.append(", isTorchTouched=");
        FlashNotificationsController$$ExternalSyntheticOutline0.m("FlashNotifController", sb, this.mIsTorchTouched);
    }

    public final void doScreenFlashNotificationOff() {
        Handler handler = this.mMainHandler;
        final int i = 0;
        handler.sendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i2 = i;
                FlashNotificationsController flashNotificationsController = (FlashNotificationsController) obj;
                flashNotificationsController.getClass();
                switch (i2) {
                    case 0:
                        Log.d("FlashNotifController", "fadeOutScreenNotificationOverlayViewMainThread");
                        View view = flashNotificationsController.mScreenFlashNotificationOverlayView;
                        if (view != null) {
                            FlashNotificationsController.fadeScreenNotificationOverlayViewMainThread(view, false);
                        }
                        View view2 = flashNotificationsController.mCoverScreenNotificationOverlayView;
                        if (view2 != null) {
                            FlashNotificationsController.fadeScreenNotificationOverlayViewMainThread(view2, false);
                            break;
                        }
                        break;
                    default:
                        Log.d("FlashNotifController", "hideScreenNotificationOverlayViewMainThread");
                        View view3 = flashNotificationsController.mScreenFlashNotificationOverlayView;
                        if (view3 != null) {
                            view3.setVisibility(8);
                            ((WindowManager) flashNotificationsController.mContext.getSystemService(WindowManager.class)).removeView(flashNotificationsController.mScreenFlashNotificationOverlayView);
                            flashNotificationsController.mScreenFlashNotificationOverlayView = null;
                        }
                        View view4 = flashNotificationsController.mCoverScreenNotificationOverlayView;
                        if (view4 != null) {
                            view4.setVisibility(8);
                            WindowManager coverDisplayWindowManager = flashNotificationsController.getCoverDisplayWindowManager();
                            if (coverDisplayWindowManager != null) {
                                coverDisplayWindowManager.removeView(flashNotificationsController.mCoverScreenNotificationOverlayView);
                            }
                            flashNotificationsController.mCoverScreenNotificationOverlayView = null;
                            break;
                        }
                        break;
                }
            }
        }, this));
        final int i2 = 1;
        handler.sendMessageDelayed(PooledLambda.obtainMessage(new Consumer() { // from class: com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i22 = i2;
                FlashNotificationsController flashNotificationsController = (FlashNotificationsController) obj;
                flashNotificationsController.getClass();
                switch (i22) {
                    case 0:
                        Log.d("FlashNotifController", "fadeOutScreenNotificationOverlayViewMainThread");
                        View view = flashNotificationsController.mScreenFlashNotificationOverlayView;
                        if (view != null) {
                            FlashNotificationsController.fadeScreenNotificationOverlayViewMainThread(view, false);
                        }
                        View view2 = flashNotificationsController.mCoverScreenNotificationOverlayView;
                        if (view2 != null) {
                            FlashNotificationsController.fadeScreenNotificationOverlayViewMainThread(view2, false);
                            break;
                        }
                        break;
                    default:
                        Log.d("FlashNotifController", "hideScreenNotificationOverlayViewMainThread");
                        View view3 = flashNotificationsController.mScreenFlashNotificationOverlayView;
                        if (view3 != null) {
                            view3.setVisibility(8);
                            ((WindowManager) flashNotificationsController.mContext.getSystemService(WindowManager.class)).removeView(flashNotificationsController.mScreenFlashNotificationOverlayView);
                            flashNotificationsController.mScreenFlashNotificationOverlayView = null;
                        }
                        View view4 = flashNotificationsController.mCoverScreenNotificationOverlayView;
                        if (view4 != null) {
                            view4.setVisibility(8);
                            WindowManager coverDisplayWindowManager = flashNotificationsController.getCoverDisplayWindowManager();
                            if (coverDisplayWindowManager != null) {
                                coverDisplayWindowManager.removeView(flashNotificationsController.mCoverScreenNotificationOverlayView);
                            }
                            flashNotificationsController.mCoverScreenNotificationOverlayView = null;
                            break;
                        }
                        break;
                }
            }
        }, this), 210L);
        FlashNotificationsController$$ExternalSyntheticOutline0.m("FlashNotifController", new StringBuilder("doScreenFlashNotificationOff: isScreenFlashNotificationEnabled="), this.mIsScreenFlashNotificationEnabled);
    }

    public final String getCameraId() {
        for (String str : this.mCameraManager.getCameraIdList()) {
            CameraCharacteristics cameraCharacteristics = this.mCameraManager.getCameraCharacteristics(str);
            Boolean bool = (Boolean) cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            Integer num = (Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
            if (bool != null && num != null && bool.booleanValue() && num.intValue() == 1) {
                DualAppManagerService$$ExternalSyntheticOutline0.m("Found valid camera, cameraId=", str, "FlashNotifController");
                return str;
            }
        }
        return null;
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
        if (display.getState() == 2) {
            return (WindowManager) this.mContext.createDisplayContext(display).getSystemService("window");
        }
        Log.d("FlashNotifController", "coverDisplay is not STATE_ON");
        return null;
    }

    public final int getScreenFlashColorPreference$2() {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), SETTING_KEY_SCREEN_FLASH_NOTIFICATION_COLOR, 1728052992, -2);
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

    public final FlashNotification removeFlashNotificationLocked(String str) {
        IBinder.DeathRecipient deathRecipient;
        IBinder.DeathRecipient deathRecipient2;
        ListIterator listIterator = this.mFlashNotifications.listIterator(0);
        while (listIterator.hasNext()) {
            FlashNotification flashNotification = (FlashNotification) listIterator.next();
            if (flashNotification != null) {
                String str2 = flashNotification.mTag;
                if (str2.equals(str)) {
                    listIterator.remove();
                    IBinder iBinder = flashNotification.mToken;
                    if (iBinder != null && (deathRecipient2 = flashNotification.mDeathRecipient) != null) {
                        try {
                            iBinder.unlinkToDeath(deathRecipient2, 0);
                        } catch (Exception unused) {
                        }
                    }
                    Log.i("FlashNotifController", "removeFlashNotificationLocked: tag=".concat(str2));
                    return flashNotification;
                }
            }
        }
        FlashNotification flashNotification2 = this.mCurrentFlashNotification;
        if (flashNotification2 == null || !flashNotification2.mTag.equals(str)) {
            return null;
        }
        FlashNotification flashNotification3 = this.mCurrentFlashNotification;
        IBinder iBinder2 = flashNotification3.mToken;
        if (iBinder2 != null && (deathRecipient = flashNotification3.mDeathRecipient) != null) {
            try {
                iBinder2.unlinkToDeath(deathRecipient, 0);
            } catch (Exception unused2) {
            }
        }
        return this.mCurrentFlashNotification;
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
        if (flashNotification.mType != 1 || !z) {
            startFlashNotification(flashNotification);
        } else {
            this.mMainHandler.sendMessageDelayed(PooledLambda.obtainMessage(new FlashNotificationsController$$ExternalSyntheticLambda4(0), this, flashNotification), 300L);
            Log.i("FlashNotifController", "give some delay for flash notification");
        }
    }

    public final void startFlashNotification(FlashNotification flashNotification) {
        int i = flashNotification.mType;
        String str = flashNotification.mTag;
        Log.i("FlashNotifController", "startFlashNotification: type=" + i + ", tag=" + str);
        boolean z = this.mIsCameraFlashNotificationEnabled;
        if (!z && !this.mIsScreenFlashNotificationEnabled && !flashNotification.mForceStartScreenFlash) {
            Log.d("FlashNotifController", "Flash notification is disabled");
            return;
        }
        if (this.mIsCameraOpened) {
            Log.d("FlashNotifController", "Since camera for torch is opened, block notification.");
            return;
        }
        if (z && this.mCameraId == null && (flashNotification.mNotiType & 1) == 1) {
            prepareForCameraFlashNotification();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mFlashNotifications) {
                try {
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
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void startFlashNotificationLocked(FlashNotification flashNotification) {
        StringBuilder sb = new StringBuilder("startFlashNotificationLocked: type=");
        sb.append(flashNotification.mType);
        sb.append(", tag=");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, flashNotification.mTag, "FlashNotifController");
        if (flashNotification.mNotiType == 0) {
            Log.i("FlashNotifController", "startFlashNotificationLocked: flash notification cannot be started.");
            return;
        }
        this.mCurrentFlashNotification = flashNotification;
        if (this.mIsCameraFlashNotificationEnabled) {
            this.mIsCameraFlashNotificationRunning = true;
            Log.i("FlashNotifController", "mIsCameraFlashNotificationRunning true in startFlashNotificationLocked");
        }
        this.mThread = new FlashNotificationThread(flashNotification);
        this.mFlashNotificationHandler.post(this.mThread);
    }

    public final void startNextFlashNotificationLocked() {
        Log.i("FlashNotifController", "startNextFlashNotificationLocked");
        if (this.mFlashNotifications.size() <= 0) {
            this.mCurrentFlashNotification = null;
        } else {
            startFlashNotificationLocked((FlashNotification) this.mFlashNotifications.getFirst());
        }
    }

    public final void stopFlashNotification(String str) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("stopFlashNotification: tag=", str, "FlashNotifController");
        synchronized (this.mFlashNotifications) {
            try {
                FlashNotification removeFlashNotificationLocked = removeFlashNotificationLocked(str);
                FlashNotification flashNotification = this.mCurrentFlashNotification;
                if (flashNotification != null && removeFlashNotificationLocked == flashNotification) {
                    stopFlashNotificationLocked();
                    startNextFlashNotificationLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopFlashNotificationLocked() {
        if (this.mThread != null) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(new StringBuilder("stopFlashNotificationLocked: tag="), this.mThread.mFlashNotification.mTag, "FlashNotifController");
            FlashNotificationThread flashNotificationThread = this.mThread;
            VpnManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("run canceled: "), flashNotificationThread.mFlashNotification.mTag, "FlashNotifController");
            synchronized (flashNotificationThread) {
                FlashNotificationsController.this.mThread.mForceStop = true;
                FlashNotificationsController.this.mThread.notify();
            }
            this.mThread = null;
        }
        doCameraFlashNotificationOff();
        doScreenFlashNotificationOff();
    }
}
