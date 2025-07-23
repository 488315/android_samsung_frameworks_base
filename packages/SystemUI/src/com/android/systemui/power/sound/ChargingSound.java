package com.android.systemui.power.sound;

import android.media.AudioAttributes;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.VibrationEffect;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.PowerUiRune;
import com.samsung.android.media.SemSoundAssistantManager;
import java.util.HashSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ChargingSound extends PowerUiSound {
    public final AnonymousClass1 mChargingSoundVibrationHandler;

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.power.sound.ChargingSound$1] */
    public ChargingSound(PowerUiSoundBuilder powerUiSoundBuilder) {
        super(powerUiSoundBuilder);
        this.mChargingSoundVibrationHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.power.sound.ChargingSound.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i = message.what;
                ChargingSound chargingSound = ChargingSound.this;
                if (i == 1) {
                    chargingSound.playSound(1);
                    return;
                }
                if (i == 2) {
                    chargingSound.playSound(2);
                    return;
                }
                if (i == 3) {
                    chargingSound.playVibration(111, -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH);
                } else if (i != 4) {
                    Log.e("ChargingSound", "This case is abnormal!!");
                } else {
                    chargingSound.playVibration(112, -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH);
                }
            }
        };
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final boolean checkCondition() {
        if (this.mAudioManager == null) {
            return true;
        }
        Log.i("ChargingSound", "Check charging sound condition");
        int semGetCurrentDeviceType = this.mAudioManager.semGetCurrentDeviceType();
        if (!PowerUiRune.AUDIO_DISABLE_HEADSET_CHARGING_SOUND) {
            return true;
        }
        if (semGetCurrentDeviceType != 3 && semGetCurrentDeviceType != 4) {
            return true;
        }
        Log.w("ChargingSound", "Should skip charging sound headset noise model...");
        return false;
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final AudioAttributes getAudioAttribute() {
        HashSet hashSet = new HashSet();
        hashSet.add("FAST_TRACK");
        AudioAttributes.Builder replaceTags = new AudioAttributes.Builder().setInternalLegacyStreamType(1).replaceTags(hashSet);
        if (PowerUiRune.AUDIO_SUPPORT_SITUATION_EXTENSION) {
            replaceTags.semAddAudioTag("stv_charger_connection");
        }
        return replaceTags.build();
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final float getVolume() {
        if (PowerUiRune.AUDIO_SUPPORT_SITUATION_EXTENSION) {
            return 1.0f;
        }
        return this.mAudioManager.semGetSituationVolume(16, 0);
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final void playSoundAndVibration() {
        Log.i("ChargingSound", "playSoundAndVibration Charging sound");
        boolean z = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "charging_sounds_enabled", 1, -2) == 1;
        boolean z2 = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "charging_vibration_enabled", 1, -2) == 1;
        if (checkCommonCondition()) {
            AnonymousClass1 anonymousClass1 = this.mChargingSoundVibrationHandler;
            int i = this.mChargingType;
            int i2 = 4;
            if (z) {
                new SemSoundAssistantManager(this.mContext).setFastAudioOpenMode();
                anonymousClass1.sendMessageDelayed(anonymousClass1.obtainMessage((i == 3 || i == 4) ? 2 : 1), 100);
            }
            if (z2) {
                if (i != 3 && i != 4) {
                    i2 = 3;
                }
                anonymousClass1.sendMessageDelayed(anonymousClass1.obtainMessage(i2), 180);
            }
        }
    }
}
