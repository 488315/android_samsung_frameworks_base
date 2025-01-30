package com.android.settingslib.fuelgauge;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.KeyValueListParser;
import android.util.Slog;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BatterySaverUtils {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Parameters {
        public final int endNth;
        public final int startNth;

        public Parameters(Context context) {
            String string = Settings.Global.getString(context.getContentResolver(), "low_power_mode_suggestion_params");
            KeyValueListParser keyValueListParser = new KeyValueListParser(',');
            try {
                keyValueListParser.setString(string);
            } catch (IllegalArgumentException unused) {
                Slog.wtf("BatterySaverUtils", "Bad constants: " + string);
            }
            this.startNth = keyValueListParser.getInt("start_nth", 4);
            this.endNth = keyValueListParser.getInt("end_nth", 8);
        }
    }

    private BatterySaverUtils() {
    }

    public static void sendSystemUiBroadcast(Context context, String str, Bundle bundle) {
        Intent intent = new Intent(str);
        intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        intent.setPackage("com.android.systemui");
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
    }

    public static synchronized void setPowerSaveMode(int i, Context context, boolean z, boolean z2) {
        boolean z3;
        synchronized (BatterySaverUtils.class) {
            ContentResolver contentResolver = context.getContentResolver();
            Bundle bundle = new Bundle(1);
            bundle.putBoolean("extra_confirm_only", false);
            if (z && z2) {
                if (Settings.Secure.getInt(context.getContentResolver(), "low_power_warning_acknowledged", 0) == 0 || Settings.Secure.getInt(context.getContentResolver(), "extra_low_power_warning_acknowledged", 0) == 0) {
                    sendSystemUiBroadcast(context, "PNW.startSaverConfirmation", bundle);
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (z3) {
                    return;
                }
            }
            if (z && !z2) {
                Settings.Secure.putIntForUser(context.getContentResolver(), "low_power_warning_acknowledged", 1, -2);
                Settings.Secure.putIntForUser(context.getContentResolver(), "extra_low_power_warning_acknowledged", 1, -2);
            }
            if (((PowerManager) context.getSystemService(PowerManager.class)).setPowerSaveModeEnabled(z)) {
                if (z) {
                    int i2 = Settings.Secure.getInt(contentResolver, "low_power_manual_activation_count", 0) + 1;
                    Settings.Secure.putInt(contentResolver, "low_power_manual_activation_count", i2);
                    Parameters parameters = new Parameters(context);
                    if (i2 >= parameters.startNth && i2 <= parameters.endNth && Settings.Global.getInt(contentResolver, "low_power_trigger_level", 0) == 0 && Settings.Secure.getInt(contentResolver, "suppress_auto_battery_saver_suggestion", 0) == 0) {
                        sendSystemUiBroadcast(context, "PNW.autoSaverSuggestion", bundle);
                    }
                }
                Bundle bundle2 = new Bundle(2);
                bundle2.putInt("extra_power_save_mode_manual_enabled_reason", i);
                bundle2.putBoolean("extra_power_save_mode_manual_enabled", z);
                sendSystemUiBroadcast(context, "com.android.settingslib.fuelgauge.ACTION_SAVER_STATE_MANUAL_UPDATE", bundle2);
            }
        }
    }
}
