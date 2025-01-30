package com.android.server.notification;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.PowerManager;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.telephony.TelephonyManager;
import android.util.Slog;
import com.android.internal.os.BackgroundThread;
import com.samsung.android.gesture.SemMotionEventListener;
import com.samsung.android.gesture.SemMotionRecognitionEvent;
import com.samsung.android.gesture.SemMotionRecognitionManager;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class SmartAlertController {
  public IntentFilter filter;
  public Context mContext;
  public final Handler mHandler;
  public boolean mInCall;
  public final PowerManager mPM;
  public final SmartAlertSettingObserver mSmartAlertSettingObserver;
  public Vibrator mVibrator;
  public final PowerManager.WakeLock mWakeLock;
  public SemMotionRecognitionManager mSmartAlertMotionManager = null;
  public boolean mMotionEnabled = false;
  public boolean mMotionRegistered = false;
  public boolean mMissedEventExist = false;
  public long[] mPickUpVibratePattern = {0, 75, 25, 75, 300};
  public boolean mScreenOn = false;
  public SemMotionEventListener mSmartAlertMotionListener = new C20291();
  public final BroadcastReceiver mIntentReceiver =
      new BroadcastReceiver() { // from class:
                                // com.android.server.notification.SmartAlertController.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          String action = intent.getAction();
          if (action.equals("android.intent.action.SCREEN_ON")) {
            SmartAlertController.this.mScreenOn = true;
            int intExtra = intent.getIntExtra("why", 0);
            Slog.d("SmartAlertController", "ACTION_SCREEN_ON, reason = " + intExtra);
            SmartAlertController.this.unregisterListener(intExtra == 7);
            return;
          }
          if (action.equals("android.intent.action.SCREEN_OFF")) {
            Slog.d("SmartAlertController", "ACTION_SCREEN_OFF");
            SmartAlertController.this.mScreenOn = false;
            if (SmartAlertController.this.mMissedEventExist) {
              SmartAlertController.this.registerListener();
              return;
            }
            return;
          }
          if (action.equals("android.intent.action.PHONE_STATE")) {
            SmartAlertController.this.mInCall =
                TelephonyManager.EXTRA_STATE_OFFHOOK.equals(
                    intent.getStringExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN));
          }
        }
      };

  public SmartAlertController(Context context) {
    this.mContext = context;
    Handler handler = new Handler();
    this.mHandler = handler;
    this.mVibrator = (Vibrator) this.mContext.getSystemService("vibrator");
    PowerManager powerManager = (PowerManager) this.mContext.getSystemService("power");
    this.mPM = powerManager;
    this.mWakeLock = powerManager.newWakeLock(1, "SmartAlert");
    IntentFilter intentFilter = new IntentFilter();
    this.filter = intentFilter;
    intentFilter.addAction("android.intent.action.SCREEN_ON");
    this.filter.addAction("android.intent.action.SCREEN_OFF");
    this.filter.addAction("android.intent.action.PHONE_STATE");
    SmartAlertSettingObserver smartAlertSettingObserver = new SmartAlertSettingObserver(handler);
    this.mSmartAlertSettingObserver = smartAlertSettingObserver;
    this.mContext
        .getContentResolver()
        .registerContentObserver(
            Settings.System.getUriFor("motion_pick_up"), false, smartAlertSettingObserver);
    smartAlertSettingObserver.onChange(false);
  }

  /* renamed from: com.android.server.notification.SmartAlertController$1 */
  public class C20291 implements SemMotionEventListener {
    public C20291() {}

    public void onMotionEvent(SemMotionRecognitionEvent semMotionRecognitionEvent) {
      if (semMotionRecognitionEvent.getMotion() != 67) {
        return;
      }
      Slog.d("SmartAlertController", "SmartAlert - SemMotionRecognitionEvent.SMART_ALERT");
      SmartAlertController.this.mWakeLock.acquire(1000L);
      new Handler()
          .postDelayed(
              new Runnable() { // from class:
                               // com.android.server.notification.SmartAlertController$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  SmartAlertController.C20291.this.lambda$onMotionEvent$0();
                }
              },
              500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMotionEvent$0() {
      if (SmartAlertController.this.mInCall) {
        Slog.d("SmartAlertController", "SmartAlert - inCall, vibration will be returned");
      } else {
        Slog.d("SmartAlertController", "SmartAlert - vibrate");
        SmartAlertController.this.mVibrator.vibrate(
            1000,
            "android",
            VibrationEffect.createWaveform(SmartAlertController.this.mPickUpVibratePattern, -1),
            "SmartAlertController",
            new VibrationAttributes.Builder().setUsage(49).setFlags(1).build());
      }
    }
  }

  public final void setSmartAlertEnabled(boolean z) {
    Slog.d("SmartAlertController", "setSmartAlertEnabled:" + z + " pre:" + this.mMotionEnabled);
    if (z != this.mMotionEnabled) {
      this.mMotionEnabled = z;
      if (z) {
        this.mContext.registerReceiver(this.mIntentReceiver, this.filter);
        if (this.mSmartAlertMotionManager == null) {
          this.mSmartAlertMotionManager =
              (SemMotionRecognitionManager) this.mContext.getSystemService("motion_recognition");
          return;
        }
        return;
      }
      this.mContext.unregisterReceiver(this.mIntentReceiver);
      if (this.mMotionRegistered) {
        unregisterListener(false);
      }
      this.mSmartAlertMotionManager = null;
    }
  }

  public void checkMissedEvent(final ArrayList arrayList) {
    this.mHandler.post(
        new Runnable() { // from class: com.android.server.notification.SmartAlertController.2
          @Override // java.lang.Runnable
          public void run() {
            int currentUser = ActivityManager.getCurrentUser();
            synchronized (arrayList) {
              SmartAlertController.this.mMissedEventExist = false;
              int size = arrayList.size();
              int i = 0;
              while (true) {
                if (i >= size) {
                  break;
                }
                StatusBarNotification sbn = ((NotificationRecord) arrayList.get(i)).getSbn();
                if (sbn.getNotification() != null
                    && sbn.getNotification().semMissedCount > 0
                    && ((sbn.getNotification().getBubbleMetadata() == null
                            || (sbn.getNotification().getBubbleMetadata() != null
                                && !sbn.getNotification()
                                    .getBubbleMetadata()
                                    .isNotificationSuppressed()))
                        && currentUser == sbn.getUserId())) {
                  Slog.d("SmartAlertController", "SmartAlert - Found Missed Event");
                  SmartAlertController.this.mMissedEventExist = true;
                  break;
                }
                i++;
              }
              if (!SmartAlertController.this.mScreenOn
                  && SmartAlertController.this.mMissedEventExist) {
                SmartAlertController.this.registerListener();
              }
              if (!SmartAlertController.this.mMissedEventExist) {
                SmartAlertController.this.unregisterListener(false);
              }
            }
          }
        });
  }

  public void registerListener() {
    new Thread() { // from class: com.android.server.notification.SmartAlertController.3
      @Override // java.lang.Thread, java.lang.Runnable
      public void run() {
        if (!SmartAlertController.this.mMotionRegistered
            && SmartAlertController.this.mMotionEnabled) {
          if (SmartAlertController.this.mSmartAlertMotionManager != null) {
            SmartAlertController.this.mSmartAlertMotionManager.registerListener(
                SmartAlertController.this.mSmartAlertMotionListener, 4);
            SmartAlertController.this.mMotionRegistered = true;
            Slog.d("SmartAlertController", "SmartAlert - registerListener");
            return;
          }
          Slog.d("SmartAlertController", "SmartAlert - mSmartAlertMotionManager is null");
          return;
        }
        Slog.d("SmartAlertController", "SmartAlert - already registered or Setting disabled");
      }
    }.start();
  }

  public void unregisterListener(boolean z) {
    BackgroundThread.getHandler()
        .postDelayed(
            new Runnable() { // from class:
                             // com.android.server.notification.SmartAlertController$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                SmartAlertController.this.lambda$unregisterListener$0();
              }
            },
            z ? 500 : 0);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$unregisterListener$0() {
    SemMotionRecognitionManager semMotionRecognitionManager;
    if (!this.mMotionRegistered
        || (semMotionRecognitionManager = this.mSmartAlertMotionManager) == null) {
      return;
    }
    semMotionRecognitionManager.unregisterListener(this.mSmartAlertMotionListener);
    this.mMotionRegistered = false;
    Slog.d("SmartAlertController", "SmartAlert - unregisterListener");
  }

  public class SmartAlertSettingObserver extends ContentObserver {
    public SmartAlertSettingObserver(Handler handler) {
      super(handler);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
      update();
    }

    public void update() {
      SmartAlertController.this.setSmartAlertEnabled(
          Settings.System.getIntForUser(
                  SmartAlertController.this.mContext.getContentResolver(), "motion_pick_up", 1, -2)
              != 0);
    }
  }
}
