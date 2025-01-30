package com.android.server.notification;

import android.app.INotificationPlayerOnCompletionListener;
import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.media.IRingtonePlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Slog;
import com.samsung.android.gesture.SemMotionEventListener;
import com.samsung.android.gesture.SemMotionRecognitionEvent;
import com.samsung.android.gesture.SemMotionRecognitionManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public class EasyMuteController {
  public AudioManager mAudioManager;
  public Context mContext;
  public final EasyMuteSettingObserver mEasyMuteSettingObserver;
  public final Handler mHandler;
  public boolean mIsRegister;
  public Method mMethodRingtonePlayer;
  public boolean mMotionOn;
  public IBinder mNotificationPlayerBinder;
  public boolean mOverTurnOn;
  public final String TAG = "EasyMuteController";
  public final String RINGTONE_PLAYER = "android.media.IRingtonePlayer";
  public SemMotionRecognitionManager mEasyMuteMotionManager = null;
  public SemMotionEventListener mMotionListener =
      new SemMotionEventListener() { // from class:
                                     // com.android.server.notification.EasyMuteController.1
        public void onMotionEvent(SemMotionRecognitionEvent semMotionRecognitionEvent) {
          if (semMotionRecognitionEvent.getMotion() != 10) {
            return;
          }
          Slog.i("EasyMuteController", "SemMotionRecognitionEvent.FLIP_SCREEN_DOWN");
          try {
            try {
              IRingtonePlayer ringtonePlayer =
                  EasyMuteController.this.mAudioManager.getRingtonePlayer();
              if (ringtonePlayer != null) {
                ringtonePlayer.stopAsync();
              }
              if (!EasyMuteController.this.mIsRegister) {
                return;
              }
            } catch (RemoteException e) {
              Slog.e("EasyMuteController", "Remote exception", e);
              if (!EasyMuteController.this.mIsRegister) {
                return;
              }
            }
            EasyMuteController.this.unregisterListener();
          } catch (Throwable th) {
            if (EasyMuteController.this.mIsRegister) {
              EasyMuteController.this.unregisterListener();
            }
            throw th;
          }
        }
      };

  public EasyMuteController(Context context) {
    this.mContext = context;
    Handler handler = new Handler();
    this.mHandler = handler;
    this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
    EasyMuteSettingObserver easyMuteSettingObserver = new EasyMuteSettingObserver(handler);
    this.mEasyMuteSettingObserver = easyMuteSettingObserver;
    this.mContext
        .getContentResolver()
        .registerContentObserver(
            Settings.System.getUriFor("master_motion"), false, easyMuteSettingObserver);
    this.mContext
        .getContentResolver()
        .registerContentObserver(
            Settings.System.getUriFor("motion_overturn"), false, easyMuteSettingObserver);
    easyMuteSettingObserver.onChange(false);
    try {
      this.mMethodRingtonePlayer =
          Class.forName("android.media.IRingtonePlayer")
              .getMethod("setOnCompletionListener", INotificationPlayerOnCompletionListener.class);
    } catch (ClassNotFoundException unused) {
      Slog.w("EasyMuteController", "ClassNotFoundException");
      this.mMethodRingtonePlayer = null;
    } catch (NoSuchMethodException unused2) {
      Slog.w("EasyMuteController", "NoSuchMethodException");
      this.mMethodRingtonePlayer = null;
    }
  }

  public final void setEasyMuteEnabled(boolean z, boolean z2) {
    Slog.d("EasyMuteController", "EasyMute updated 1." + z + " 2." + z2);
    if (this.mMotionOn != z || this.mOverTurnOn != z2) {
      this.mMotionOn = z;
      this.mOverTurnOn = z2;
      if (z && z2) {
        if (this.mEasyMuteMotionManager == null) {
          this.mEasyMuteMotionManager =
              (SemMotionRecognitionManager) this.mContext.getSystemService("motion_recognition");
          return;
        } else {
          Slog.d("EasyMuteController", "setEasyMuteEnabled mEasyMuteMotionManager in not null");
          return;
        }
      }
      Slog.d("EasyMuteController", "setEasyMuteEnabled setting off");
      if (this.mIsRegister) {
        unregisterListener();
      }
      this.mEasyMuteMotionManager = null;
      return;
    }
    Slog.d("EasyMuteController", "setEasyMuteEnabled no setting changed");
  }

  public boolean isEnable() {
    return this.mMotionOn && this.mOverTurnOn;
  }

  public void registerListener() {
    if (isEnable() && !this.mIsRegister) {
      SemMotionRecognitionManager semMotionRecognitionManager = this.mEasyMuteMotionManager;
      if (semMotionRecognitionManager != null) {
        semMotionRecognitionManager.registerListener(this.mMotionListener, 1);
        this.mIsRegister = true;
        Slog.i("EasyMuteController", "Reg. OverTurn");
        if (this.mNotificationPlayerBinder == null) {
          Object[] objArr = {
            new INotificationPlayerOnCompletionListener
                .Stub() { // from class: com.android.server.notification.EasyMuteController.2
              public void onCompletion() {
                Slog.i("EasyMuteController", "onCompletion");
                if (EasyMuteController.this.mIsRegister) {
                  EasyMuteController.this.unregisterListener();
                }
              }
            }
          };
          try {
            IRingtonePlayer ringtonePlayer = this.mAudioManager.getRingtonePlayer();
            Method method = this.mMethodRingtonePlayer;
            if (method != null) {
              IBinder iBinder = (IBinder) method.invoke(ringtonePlayer, objArr);
              this.mNotificationPlayerBinder = iBinder;
              iBinder.linkToDeath(
                  new IBinder
                      .DeathRecipient() { // from class:
                                          // com.android.server.notification.EasyMuteController.3
                    @Override // android.os.IBinder.DeathRecipient
                    public void binderDied() {
                      Slog.i("EasyMuteController", "binderDied()");
                      EasyMuteController.this.mNotificationPlayerBinder.unlinkToDeath(this, 0);
                      EasyMuteController.this.mNotificationPlayerBinder = null;
                      if (EasyMuteController.this.mIsRegister) {
                        EasyMuteController.this.unregisterListener();
                      }
                    }
                  },
                  0);
              return;
            }
            return;
          } catch (RemoteException unused) {
            Slog.w("EasyMuteController", "RemoteException");
            return;
          } catch (IllegalAccessException unused2) {
            Slog.w("EasyMuteController", "IllegalAccessException");
            this.mNotificationPlayerBinder = null;
            if (this.mIsRegister) {
              unregisterListener();
              return;
            }
            return;
          } catch (InvocationTargetException unused3) {
            Slog.w("EasyMuteController", "InvocationTargetException");
            this.mNotificationPlayerBinder = null;
            if (this.mIsRegister) {
              unregisterListener();
              return;
            }
            return;
          }
        }
        return;
      }
      Slog.d("EasyMuteController", "Register failed. mEasyMuteMotionManager is null");
      return;
    }
    Slog.d("EasyMuteController", "Register failed. already registered or setting not eanbled");
  }

  public void unregisterListener() {
    if (this.mIsRegister) {
      SemMotionRecognitionManager semMotionRecognitionManager = this.mEasyMuteMotionManager;
      if (semMotionRecognitionManager != null) {
        semMotionRecognitionManager.unregisterListener(this.mMotionListener);
        this.mIsRegister = false;
        Slog.i("EasyMuteController", "UnReg. OverTurn");
        return;
      }
      Slog.i("EasyMuteController", "UnRegister failed. mEasyMuteMotionManager is null");
      return;
    }
    Slog.i("EasyMuteController", "UnRegister failed. no registered");
  }

  public class EasyMuteSettingObserver extends ContentObserver {
    public EasyMuteSettingObserver(Handler handler) {
      super(handler);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
      update();
    }

    public void update() {
      boolean z = false;
      boolean z2 =
          Settings.System.getIntForUser(
                  EasyMuteController.this.mContext.getContentResolver(), "master_motion", 0, -2)
              != 0;
      if (z2
          && Settings.System.getIntForUser(
                  EasyMuteController.this.mContext.getContentResolver(), "motion_overturn", 0, -2)
              != 0) {
        z = true;
      }
      EasyMuteController.this.setEasyMuteEnabled(z2, z);
    }
  }
}
