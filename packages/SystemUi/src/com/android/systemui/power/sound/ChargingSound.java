package com.android.systemui.power.sound;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.VibrationEffect;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.PowerUiRune;
import com.samsung.android.media.SemSoundAssistantManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ChargingSound extends PowerUiSound {
    public final HandlerC19811 mChargingSoundVibrationHandler;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.power.sound.ChargingSound$1] */
    public ChargingSound(Context context, int i) {
        super(context, i);
        this.mChargingSoundVibrationHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.power.sound.ChargingSound.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i2 = message.what;
                if (i2 == 1) {
                    ChargingSound.this.playSound(1, 1);
                    return;
                }
                if (i2 == 2) {
                    ChargingSound.this.playSound(2, 1);
                    return;
                }
                if (i2 == 3) {
                    ChargingSound.this.playVibration(111, -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH);
                } else if (i2 != 4) {
                    Log.e("PowerUiSound.Charging", "This case is abnormal!!");
                } else {
                    ChargingSound.this.playVibration(112, -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH);
                }
            }
        };
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final boolean checkCondition() {
        if (this.mAudioManager == null) {
            return true;
        }
        Log.i("PowerUiSound.Charging", "Check charging sound condition");
        int semGetCurrentDeviceType = this.mAudioManager.semGetCurrentDeviceType();
        if (!PowerUiRune.AUDIO_DISABLE_HEADSET_CHARGING_SOUND) {
            return true;
        }
        if (semGetCurrentDeviceType != 3 && semGetCurrentDeviceType != 4) {
            return true;
        }
        Log.w("PowerUiSound.Charging", "Should skip charging sound headset noise model...");
        return false;
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final void playSoundAndVibration() {
        Log.i("PowerUiSound.Charging", "playSoundAndVibration Charging sound");
        Context context = this.mContext;
        boolean z = true;
        boolean z2 = Settings.Secure.getIntForUser(context.getContentResolver(), "charging_sounds_enabled", 1, -2) == 1;
        boolean z3 = Settings.Secure.getIntForUser(context.getContentResolver(), "charging_vibration_enabled", 1, -2) == 1;
        if (checkCommonCondition()) {
            HandlerC19811 handlerC19811 = this.mChargingSoundVibrationHandler;
            if (z2) {
                new SemSoundAssistantManager(context).setFastAudioOpenMode();
                int i = this.mChargingType;
                handlerC19811.sendMessageDelayed(handlerC19811.obtainMessage(i == 3 || i == 4 ? 2 : 1), 100);
            }
            if (z3) {
                int i2 = this.mChargingType;
                if (i2 != 3 && i2 != 4) {
                    z = false;
                }
                handlerC19811.sendMessageDelayed(handlerC19811.obtainMessage(z ? 4 : 3), 180);
            }
        }
    }
}
