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
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.media.NotificationPlayer;
import java.util.HashSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mCustomSdkMonitor;
        if (!(customSdkMonitor != null && customSdkMonitor.mChargerConnectionSoundEnabledState) && this.mSoundType == 1) {
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
        this.mRingerMode = 2;
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

    public final void playSound(int i, int i2) {
        Context context = this.mContext;
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("playSound : type = ", i, "PowerUiSound");
        try {
            Uri parse = Uri.parse(SoundPathFinder.getSoundPath(i, context));
            HashSet hashSet = new HashSet();
            if (i == 1 || i == 2) {
                hashSet.add("FAST_TRACK");
            }
            this.mNotificationPlayer.play(context, parse, false, new AudioAttributes.Builder().setInternalLegacyStreamType(i2).replaceTags(hashSet).build());
        } catch (NullPointerException e) {
            Log.w("PowerUiSound", "playSound : NPE occur", e);
        }
    }

    public abstract void playSoundAndVibration();

    public final void playVibration(int i, int i2, VibrationEffect.SemMagnitudeType semMagnitudeType) {
        Log.i("PowerUiSound", "playVibration : index = " + i);
        VibrationEffect semCreateHaptic = VibrationEffect.semCreateHaptic(HapticFeedbackConstants.semGetVibrationIndex(i), -1, semMagnitudeType);
        if (i2 != -1) {
            semCreateHaptic.semSetMagnitude(i2);
        }
        Vibrator vibrator = this.mVibrator;
        if (vibrator != null) {
            vibrator.vibrate(semCreateHaptic);
        } else {
            Log.e("PowerUiSound", "playVibration : Charging vibration setting is on but Vibrator is null");
        }
    }
}
