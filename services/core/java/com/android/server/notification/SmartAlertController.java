package com.android.server.notification;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.telephony.TelephonyManager;
import android.util.Slog;

import com.android.internal.os.BackgroundThread;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;

import com.samsung.android.gesture.SemMotionEventListener;
import com.samsung.android.gesture.SemMotionRecognitionEvent;
import com.samsung.android.gesture.SemMotionRecognitionManager;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import java.util.ArrayList;

public final class SmartAlertController {
    public final IntentFilter filter;
    public final Context mContext;
    public final Handler mHandler;
    public boolean mInCall;
    public final Vibrator mVibrator;
    public final PowerManager.WakeLock mWakeLock;
    public SemMotionRecognitionManager mSmartAlertMotionManager = null;
    public boolean mMotionEnabled = false;
    public boolean mMotionRegistered = false;
    public boolean mMissedEventExist = false;
    public final long[] mPickUpVibratePattern = {0, 75, 25, 75, 300};
    public boolean mScreenOn = false;
    public final AnonymousClass1 mSmartAlertMotionListener = new AnonymousClass1();
    public final AnonymousClass4 mIntentReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.server.notification.SmartAlertController.4
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (action.equals("android.intent.action.SCREEN_ON")) {
                        SmartAlertController.this.mScreenOn = true;
                        int intExtra = intent.getIntExtra("why", 0);
                        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                intExtra, "ACTION_SCREEN_ON, reason = ", "SmartAlertController");
                        SmartAlertController.this.unregisterListener(intExtra == 7);
                        return;
                    }
                    if (!action.equals("android.intent.action.SCREEN_OFF")) {
                        if (action.equals("android.intent.action.PHONE_STATE")) {
                            SmartAlertController.this.mInCall =
                                    TelephonyManager.EXTRA_STATE_OFFHOOK.equals(
                                            intent.getStringExtra(
                                                    LauncherConfigurationInternal
                                                            .KEY_STATE_BOOLEAN));
                        }
                    } else {
                        Slog.d("SmartAlertController", "ACTION_SCREEN_OFF");
                        SmartAlertController smartAlertController = SmartAlertController.this;
                        smartAlertController.mScreenOn = false;
                        if (smartAlertController.mMissedEventExist) {
                            smartAlertController.new AnonymousClass3().start();
                        }
                    }
                }
            };

    /* renamed from: com.android.server.notification.SmartAlertController$1, reason: invalid class name */
    public final class AnonymousClass1 implements SemMotionEventListener {
        public AnonymousClass1() {}

        public final void onMotionEvent(SemMotionRecognitionEvent semMotionRecognitionEvent) {
            if (semMotionRecognitionEvent.getMotion() != 67) {
                return;
            }
            Slog.d("SmartAlertController", "SmartAlert - SemMotionRecognitionEvent.SMART_ALERT");
            SmartAlertController.this.mWakeLock.acquire(1000L);
            new Handler()
                    .postDelayed(new SmartAlertController$$ExternalSyntheticLambda0(1, this), 500L);
        }
    }

    /* renamed from: com.android.server.notification.SmartAlertController$2, reason: invalid class name */
    public final class AnonymousClass2 implements Runnable {
        public final /* synthetic */ ArrayList val$notiList;

        public AnonymousClass2(ArrayList arrayList) {
            this.val$notiList = arrayList;
        }

        @Override // java.lang.Runnable
        public final void run() {
            int currentUser = ActivityManager.getCurrentUser();
            synchronized (this.val$notiList) {
                try {
                    SmartAlertController.this.mMissedEventExist = false;
                    int size = this.val$notiList.size();
                    int i = 0;
                    while (true) {
                        if (i >= size) {
                            break;
                        }
                        StatusBarNotification statusBarNotification =
                                ((NotificationRecord) this.val$notiList.get(i)).sbn;
                        if (statusBarNotification.getNotification() != null) {
                            if (statusBarNotification.getNotification().semMissedCount <= 0) {
                                continue;
                            } else {
                                if (statusBarNotification.getNotification().getBubbleMetadata()
                                        != null) {
                                    if (statusBarNotification.getNotification().getBubbleMetadata()
                                                    != null
                                            && !statusBarNotification
                                                    .getNotification()
                                                    .getBubbleMetadata()
                                                    .isNotificationSuppressed()) {}
                                }
                                if (currentUser == statusBarNotification.getUserId()) {
                                    Slog.d(
                                            "SmartAlertController",
                                            "SmartAlert - Found Missed Event");
                                    SmartAlertController.this.mMissedEventExist = true;
                                    break;
                                }
                            }
                        }
                        i++;
                    }
                    SmartAlertController smartAlertController = SmartAlertController.this;
                    if (!smartAlertController.mScreenOn && smartAlertController.mMissedEventExist) {
                        smartAlertController.new AnonymousClass3().start();
                    }
                    SmartAlertController smartAlertController2 = SmartAlertController.this;
                    if (!smartAlertController2.mMissedEventExist) {
                        smartAlertController2.unregisterListener(false);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* renamed from: com.android.server.notification.SmartAlertController$3, reason: invalid class name */
    public final class AnonymousClass3 extends Thread {
        public AnonymousClass3() {}

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            SmartAlertController smartAlertController = SmartAlertController.this;
            if (smartAlertController.mMotionRegistered || !smartAlertController.mMotionEnabled) {
                Slog.d(
                        "SmartAlertController",
                        "SmartAlert - already registered or Setting disabled");
                return;
            }
            SemMotionRecognitionManager semMotionRecognitionManager =
                    smartAlertController.mSmartAlertMotionManager;
            if (semMotionRecognitionManager == null) {
                Slog.d("SmartAlertController", "SmartAlert - mSmartAlertMotionManager is null");
                return;
            }
            semMotionRecognitionManager.registerListener(
                    smartAlertController.mSmartAlertMotionListener, 4);
            SmartAlertController.this.mMotionRegistered = true;
            Slog.d("SmartAlertController", "SmartAlert - registerListener");
        }
    }

    public final class SmartAlertSettingObserver extends ContentObserver {
        public SmartAlertSettingObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            boolean z2 =
                    Settings.System.getIntForUser(
                                    SmartAlertController.this.mContext.getContentResolver(),
                                    "motion_pick_up",
                                    1,
                                    -2)
                            != 0;
            SmartAlertController smartAlertController = SmartAlertController.this;
            smartAlertController.getClass();
            StringBuilder sb = new StringBuilder("setSmartAlertEnabled:");
            sb.append(z2);
            sb.append(" pre:");
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    "SmartAlertController", sb, smartAlertController.mMotionEnabled);
            if (z2 != smartAlertController.mMotionEnabled) {
                smartAlertController.mMotionEnabled = z2;
                AnonymousClass4 anonymousClass4 = smartAlertController.mIntentReceiver;
                if (z2) {
                    smartAlertController.mContext.registerReceiver(
                            anonymousClass4, smartAlertController.filter);
                    if (smartAlertController.mSmartAlertMotionManager == null) {
                        smartAlertController.mSmartAlertMotionManager =
                                (SemMotionRecognitionManager)
                                        smartAlertController.mContext.getSystemService(
                                                "motion_recognition");
                        return;
                    }
                    return;
                }
                smartAlertController.mContext.unregisterReceiver(anonymousClass4);
                if (smartAlertController.mMotionRegistered) {
                    smartAlertController.unregisterListener(false);
                }
                smartAlertController.mSmartAlertMotionManager = null;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.notification.SmartAlertController$4] */
    public SmartAlertController(Context context) {
        this.mContext = context;
        Handler handler = new Handler();
        this.mHandler = handler;
        this.mVibrator = (Vibrator) context.getSystemService("vibrator");
        this.mWakeLock =
                ((PowerManager) context.getSystemService("power")).newWakeLock(1, "SmartAlert");
        IntentFilter intentFilter = new IntentFilter();
        this.filter = intentFilter;
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        SmartAlertSettingObserver smartAlertSettingObserver =
                new SmartAlertSettingObserver(handler);
        context.getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("motion_pick_up"),
                        false,
                        smartAlertSettingObserver);
        smartAlertSettingObserver.onChange(false);
    }

    public final void unregisterListener(boolean z) {
        BackgroundThread.getHandler()
                .postDelayed(
                        new SmartAlertController$$ExternalSyntheticLambda0(0, this), z ? 500 : 0);
    }
}
