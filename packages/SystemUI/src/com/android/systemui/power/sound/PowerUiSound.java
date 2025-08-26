package com.android.systemui.power.sound;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.net.Uri;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.media.NotificationPlayer;
import com.android.systemui.statusbar.phone.SecStatusBarAudioManagerHelper;

/* loaded from: classes2.dex */
public abstract class PowerUiSound {
    public final AudioManager mAudioManager;
    public final int mChargingType;
    public final Context mContext;
    public final boolean mIsInCall;
    public final NotificationPlayer mNotificationPlayer;
    public int mRingerMode;
    public final int mSoundType;
    public final Vibrator mVibrator;

    public PowerUiSound(PowerUiSoundBuilder powerUiSoundBuilder) {
        this.mContext = powerUiSoundBuilder.context;
        this.mSoundType = powerUiSoundBuilder.soundType;
        this.mNotificationPlayer = powerUiSoundBuilder.notificationPlayer;
        this.mVibrator = powerUiSoundBuilder.vibrator;
        this.mIsInCall = powerUiSoundBuilder.isInCall;
        this.mChargingType = powerUiSoundBuilder.chargingType;
        this.mAudioManager = powerUiSoundBuilder.audioManager;
    }

    public final boolean checkCommonCondition() {
        int mode;
        CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mCustomSdkMonitor;
        if ((customSdkMonitor == null || !customSdkMonitor.mChargerConnectionSoundEnabledState) && this.mSoundType == 1) {
            Log.d("PowerUiSound", "checkCommonCondition : Knox Custom disabled SOUND_TYPE_CHARGER_CONNECTION");
            return false;
        }
        AudioManager audioManager = this.mAudioManager;
        if (audioManager != null) {
            mode = audioManager.getMode();
            if (this.mAudioManager.semIsRecordActive(-1) && 3 != mode) {
                Log.d("PowerUiSound", "checkCommonCondition : recording so doesn't play sound");
                return false;
            }
        } else {
            mode = 0;
        }
        this.mRingerMode = SecStatusBarAudioManagerHelper.getInstance(this.mContext).getRingerMode(false);
        boolean z = Settings.System.getIntForUser(this.mContext.getContentResolver(), "alertoncall_mode", 1, -2) == 1;
        if (this.mIsInCall || mode == 3) {
            if (!z) {
                Log.d("PowerUiSound", "checkCommonCondition : calling and doesn't notify during calls");
                return false;
            }
            this.mRingerMode = 1;
        }
        return checkCondition();
    }

    public abstract boolean checkCondition();

    public abstract AudioAttributes getAudioAttribute();

    public float getVolume() {
        return 1.0f;
    }

    public final void playSound(int i) {
        try {
            Uri uri = Uri.parse(SoundPathFinder.getSoundPath(i, this.mContext));
            this.mNotificationPlayer.play(this.mContext, uri, false, getAudioAttribute(), getVolume());
            ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "playSound : type = ", "PowerUiSound");
        } catch (NullPointerException e) {
            Log.w("PowerUiSound", "playSound : NPE occur", e);
        }
    }

    public abstract void playSoundAndVibration();

    public final void playVibration(int i, int i2, VibrationEffect.SemMagnitudeType semMagnitudeType) {
        VibrationEffect vibrationEffectSemCreateHaptic = VibrationEffect.semCreateHaptic(HapticFeedbackConstants.semGetVibrationIndex(i), -1, semMagnitudeType);
        if (i2 != -1) {
            vibrationEffectSemCreateHaptic.semSetMagnitude(i2);
        }
        Vibrator vibrator = this.mVibrator;
        if (vibrator == null) {
            Log.e("PowerUiSound", "playVibration : Charging vibration setting is on but Vibrator is null");
            return;
        }
        vibrator.vibrate(vibrationEffectSemCreateHaptic);
        Log.i("PowerUiSound", "playVibration : index = " + i);
    }
}
