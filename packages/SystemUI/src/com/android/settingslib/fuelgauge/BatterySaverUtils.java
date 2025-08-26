package com.android.settingslib.fuelgauge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.KeyValueListParser;
import android.util.Slog;

/* loaded from: classes.dex */
public class BatterySaverUtils {

    public class Parameters {
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
        intent.setFlags(268435456);
        intent.setPackage("com.android.systemui");
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
    }
}
