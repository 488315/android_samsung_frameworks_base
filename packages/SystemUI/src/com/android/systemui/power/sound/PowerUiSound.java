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
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.media.NotificationPlayer;
import com.android.systemui.statusbar.phone.SecStatusBarAudioManagerHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class PowerUiSound {
    public AudioManager mAudioManager;
    public int mChargingType;
    public final Context mContext;
    public boolean mIsInCall;
    public NotificationPlayer mNotificationPlayer;
    public int mRingerMode;
    public final int mSoundType;
    public Vibrator mVibrator;

    public PowerUiSound(Context context, int i) {
        this.mContext = context;
        this.mSoundType = i;
    }

    public final boolean checkCommonCondition() {
        int i;
        CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mCustomSdkMonitor;
        if ((customSdkMonitor == null || !customSdkMonitor.mChargerConnectionSoundEnabledState) && this.mSoundType == 1) {
            Log.d("PowerUiSound", "checkCommonCondition : Knox Custom disabled SOUND_TYPE_CHARGER_CONNECTION");
            return false;
        }
        AudioManager audioManager = this.mAudioManager;
        if (audioManager != null) {
            i = audioManager.getMode();
            if (this.mAudioManager.semIsRecordActive(-1) && 3 != i) {
                Log.d("PowerUiSound", "checkCommonCondition : recording so doesn't play sound");
                return false;
            }
        } else {
            i = 0;
        }
        this.mRingerMode = SecStatusBarAudioManagerHelper.getInstance(this.mContext).getRingerMode(false);
        boolean z = Settings.System.getIntForUser(this.mContext.getContentResolver(), "alertoncall_mode", 1, -2) == 1;
        if (this.mIsInCall || i == 3) {
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
            this.mNotificationPlayer.play(this.mContext, Uri.parse(SoundPathFinder.getSoundPath(i, this.mContext)), false, getAudioAttribute(), getVolume());
            KeyguardSecPatternView$$ExternalSyntheticOutline0.m(i, "playSound : type = ", "PowerUiSound");
        } catch (NullPointerException e) {
            Log.w("PowerUiSound", "playSound : NPE occur", e);
        }
    }

    public abstract void playSoundAndVibration();

    public final void playVibration(int i, int i2, VibrationEffect.SemMagnitudeType semMagnitudeType) {
        VibrationEffect semCreateHaptic = VibrationEffect.semCreateHaptic(HapticFeedbackConstants.semGetVibrationIndex(i), -1, semMagnitudeType);
        if (i2 != -1) {
            semCreateHaptic.semSetMagnitude(i2);
        }
        Vibrator vibrator = this.mVibrator;
        if (vibrator == null) {
            Log.e("PowerUiSound", "playVibration : Charging vibration setting is on but Vibrator is null");
            return;
        }
        vibrator.vibrate(semCreateHaptic);
        Log.i("PowerUiSound", "playVibration : index = " + i);
    }
}
