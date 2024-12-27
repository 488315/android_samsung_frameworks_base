package com.android.systemui.power.sound;

import android.content.Context;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PowerUiSoundFactory {
    public static PowerUiSound getPowerUiSound(int i, Context context) {
        return i != 3 ? i != 4 ? i != 5 ? i != 6 ? i != 7 ? new ChargingSound(context, i) : new UsbDamageCautionSound(context, i) : new WaterCautionSound(context, i) : new TemperatureLimitSound(context, i) : new BatteryCautionSound(context, i) : new LowBatterySound(context, i);
    }
}
