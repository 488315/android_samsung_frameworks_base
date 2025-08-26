package com.android.systemui.power.sound;

import android.content.Context;
import android.media.AudioManager;
import android.os.Vibrator;
import com.android.systemui.media.NotificationPlayer;

/* loaded from: classes2.dex */
public class PowerUiSoundBuilder {
    public AudioManager audioManager;
    public int chargingType;
    public final Context context;
    public boolean isInCall;
    public NotificationPlayer notificationPlayer;
    public final int soundType;
    public Vibrator vibrator;

    public PowerUiSoundBuilder(Context context, int i) {
        this.context = context;
        this.soundType = i;
    }

    public final PowerUiSound build() {
        int i = this.soundType;
        return i != 3 ? i != 4 ? i != 5 ? i != 6 ? i != 7 ? new ChargingSound(this) : new UsbDamageCautionSound(this) : new WaterCautionSound(this) : new TemperatureLimitSound(this) : new BatteryCautionSound(this) : new LowBatterySound(this);
    }
}
