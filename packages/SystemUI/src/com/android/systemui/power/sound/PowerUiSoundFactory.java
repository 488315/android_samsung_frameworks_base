package com.android.systemui.power.sound;

import android.content.Context;

public final class PowerUiSoundFactory {
    public static PowerUiSound getPowerUiSound(int i, Context context) {
        return i != 3 ? i != 4 ? i != 5 ? i != 6 ? i != 7 ? new ChargingSound(context, i) : new UsbDamageCautionSound(context, i) : new WaterCautionSound(context, i) : new TemperatureLimitSound(context, i) : new BatteryCautionSound(context, i) : new LowBatterySound(context, i);
    }
}
