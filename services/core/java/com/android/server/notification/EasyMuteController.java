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

import java.lang.reflect.Method;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class EasyMuteController {
    public final AudioManager mAudioManager;
    public final Context mContext;
    public boolean mIsRegister;
    public final Method mMethodRingtonePlayer;
    public boolean mMotionOn;
    public IBinder mNotificationPlayerBinder;
    public boolean mOverTurnOn;
    public SemMotionRecognitionManager mEasyMuteMotionManager = null;
    public final AnonymousClass1 mMotionListener =
            new SemMotionEventListener() { // from class:
                                           // com.android.server.notification.EasyMuteController.1
                public final void onMotionEvent(
                        SemMotionRecognitionEvent semMotionRecognitionEvent) {
                    EasyMuteController easyMuteController;
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
                            easyMuteController = EasyMuteController.this;
                            if (!easyMuteController.mIsRegister) {
                                return;
                            }
                        } catch (RemoteException e) {
                            Slog.e("EasyMuteController", "Remote exception", e);
                            easyMuteController = EasyMuteController.this;
                            if (!easyMuteController.mIsRegister) {
                                return;
                            }
                        }
                        easyMuteController.unregisterListener();
                    } catch (Throwable th) {
                        EasyMuteController easyMuteController2 = EasyMuteController.this;
                        if (easyMuteController2.mIsRegister) {
                            easyMuteController2.unregisterListener();
                        }
                        throw th;
                    }
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EasyMuteSettingObserver extends ContentObserver {
        public EasyMuteSettingObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            boolean z2 = false;
            boolean z3 =
                    Settings.System.getIntForUser(
                                    EasyMuteController.this.mContext.getContentResolver(),
                                    "master_motion",
                                    0,
                                    -2)
                            != 0;
            if (z3
                    && Settings.System.getIntForUser(
                                    EasyMuteController.this.mContext.getContentResolver(),
                                    "motion_overturn",
                                    0,
                                    -2)
                            != 0) {
                z2 = true;
            }
            EasyMuteController easyMuteController = EasyMuteController.this;
            easyMuteController.getClass();
            Slog.d("EasyMuteController", "EasyMute updated 1." + z3 + " 2." + z2);
            if (easyMuteController.mMotionOn == z3 && easyMuteController.mOverTurnOn == z2) {
                Slog.d("EasyMuteController", "setEasyMuteEnabled no setting changed");
                return;
            }
            easyMuteController.mMotionOn = z3;
            easyMuteController.mOverTurnOn = z2;
            if (z3 && z2) {
                if (easyMuteController.mEasyMuteMotionManager == null) {
                    easyMuteController.mEasyMuteMotionManager =
                            (SemMotionRecognitionManager)
                                    easyMuteController.mContext.getSystemService(
                                            "motion_recognition");
                    return;
                } else {
                    Slog.d(
                            "EasyMuteController",
                            "setEasyMuteEnabled mEasyMuteMotionManager in not null");
                    return;
                }
            }
            Slog.d("EasyMuteController", "setEasyMuteEnabled setting off");
            if (easyMuteController.mIsRegister) {
                easyMuteController.unregisterListener();
            }
            easyMuteController.mEasyMuteMotionManager = null;
        }
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.server.notification.EasyMuteController$1] */
    public EasyMuteController(Context context) {
        this.mContext = context;
        Handler handler = new Handler();
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        EasyMuteSettingObserver easyMuteSettingObserver = new EasyMuteSettingObserver(handler);
        context.getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("master_motion"), false, easyMuteSettingObserver);
        context.getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("motion_overturn"),
                        false,
                        easyMuteSettingObserver);
        easyMuteSettingObserver.onChange(false);
        try {
            this.mMethodRingtonePlayer =
                    Class.forName("android.media.IRingtonePlayer")
                            .getMethod(
                                    "setOnCompletionListener",
                                    INotificationPlayerOnCompletionListener.class);
        } catch (ClassNotFoundException unused) {
            Slog.w("EasyMuteController", "ClassNotFoundException");
            this.mMethodRingtonePlayer = null;
        } catch (NoSuchMethodException unused2) {
            Slog.w("EasyMuteController", "NoSuchMethodException");
            this.mMethodRingtonePlayer = null;
        }
    }

    public final void unregisterListener() {
        if (!this.mIsRegister) {
            Slog.i("EasyMuteController", "UnRegister failed. no registered");
            return;
        }
        SemMotionRecognitionManager semMotionRecognitionManager = this.mEasyMuteMotionManager;
        if (semMotionRecognitionManager == null) {
            Slog.i("EasyMuteController", "UnRegister failed. mEasyMuteMotionManager is null");
            return;
        }
        semMotionRecognitionManager.unregisterListener(this.mMotionListener);
        this.mIsRegister = false;
        Slog.i("EasyMuteController", "UnReg. OverTurn");
    }
}
