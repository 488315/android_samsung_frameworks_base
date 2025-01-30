package com.android.settingslib.net;

import android.content.Context;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SignalStrengthUtil {
    public static boolean shouldInflateSignalStrength(int i, Context context) {
        CarrierConfigManager carrierConfigManager = (CarrierConfigManager) context.getSystemService(CarrierConfigManager.class);
        PersistableBundle configForSubId = carrierConfigManager != null ? carrierConfigManager.getConfigForSubId(i) : null;
        return configForSubId != null && configForSubId.getBoolean("inflate_signal_strength_bool", false);
    }
}
