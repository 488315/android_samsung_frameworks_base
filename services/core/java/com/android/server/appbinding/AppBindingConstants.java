package com.android.server.appbinding;

import android.util.KeyValueListParser;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerConstants$$ExternalSyntheticOutline0;
import com.android.server.am.AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0;
import com.android.server.am.ProcessList$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppBindingConstants {
    public final double SERVICE_RECONNECT_BACKOFF_INCREASE;
    public final long SERVICE_RECONNECT_BACKOFF_SEC;
    public final long SERVICE_RECONNECT_MAX_BACKOFF_SEC;
    public final long SERVICE_STABLE_CONNECTION_THRESHOLD_SEC;
    public final int SMS_APP_BIND_FLAGS;
    public final boolean SMS_SERVICE_ENABLED;
    public final String sourceSettings;

    public AppBindingConstants(String str) {
        this.sourceSettings = str;
        KeyValueListParser keyValueListParser = new KeyValueListParser(',');
        try {
            keyValueListParser.setString(str);
        } catch (IllegalArgumentException unused) {
            BootReceiver$$ExternalSyntheticOutline0.m("Bad setting: ", str, "AppBindingService");
        }
        long j = keyValueListParser.getLong("service_reconnect_backoff_sec", 10L);
        double d = keyValueListParser.getFloat("service_reconnect_backoff_increase", 2.0f);
        long j2 =
                keyValueListParser.getLong(
                        "service_reconnect_max_backoff_sec", TimeUnit.HOURS.toSeconds(1L));
        boolean z = keyValueListParser.getBoolean("sms_service_enabled", true);
        int i = keyValueListParser.getInt("sms_app_bind_flags", 1140850688);
        long j3 =
                keyValueListParser.getLong(
                        "service_stable_connection_threshold_sec", TimeUnit.MINUTES.toSeconds(2L));
        long max = Math.max(5L, j);
        double max2 = Math.max(1.0d, d);
        long max3 = Math.max(max, j2);
        this.SERVICE_RECONNECT_BACKOFF_SEC = max;
        this.SERVICE_RECONNECT_BACKOFF_INCREASE = max2;
        this.SERVICE_RECONNECT_MAX_BACKOFF_SEC = max3;
        this.SERVICE_STABLE_CONNECTION_THRESHOLD_SEC = j3;
        this.SMS_SERVICE_ENABLED = z;
        this.SMS_APP_BIND_FLAGS = i;
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.print("  ");
        printWriter.print("Constants: ");
        ProcessList$$ExternalSyntheticOutline0.m(
                printWriter, this.sourceSettings, "  ", "  SERVICE_RECONNECT_BACKOFF_SEC: ");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.SERVICE_RECONNECT_BACKOFF_SEC,
                printWriter,
                "  ",
                "  SERVICE_RECONNECT_BACKOFF_INCREASE: ");
        printWriter.println(this.SERVICE_RECONNECT_BACKOFF_INCREASE);
        printWriter.print("  ");
        printWriter.print("  SERVICE_RECONNECT_MAX_BACKOFF_SEC: ");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.SERVICE_RECONNECT_MAX_BACKOFF_SEC,
                printWriter,
                "  ",
                "  SERVICE_STABLE_CONNECTION_THRESHOLD_SEC: ");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.SERVICE_STABLE_CONNECTION_THRESHOLD_SEC,
                printWriter,
                "  ",
                "  SMS_SERVICE_ENABLED: ");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter, "  ", "  SMS_APP_BIND_FLAGS: 0x", this.SMS_SERVICE_ENABLED);
        printWriter.println(Integer.toHexString(this.SMS_APP_BIND_FLAGS));
    }
}
