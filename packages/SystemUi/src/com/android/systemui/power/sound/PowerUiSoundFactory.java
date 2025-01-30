package com.android.systemui.power.sound;

import android.content.Context;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PowerUiSoundFactory {
    public static PowerUiSound getPowerUiSound(int i, Context context) {
        return i != 3 ? i != 4 ? i != 5 ? i != 6 ? i != 7 ? new ChargingSound(context, i) : new UsbDamageCautionSound(context, i) : new WaterCautionSound(context, i) : new TemperatureLimitSound(context, i) : new BatteryCautionSound(context, i) : new LowBatterySound(context, i);
    }
}
