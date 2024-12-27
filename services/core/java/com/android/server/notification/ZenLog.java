package com.android.server.notification;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.os.Build;
import android.service.notification.ZenModeConfig;
import android.service.notification.ZenModeDiff;
import android.util.LocalLog;

import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class ZenLog {
    public static final LocalLog INTERCEPTION_EVENTS;
    public static final LocalLog STATE_CHANGES;

    static {
        int i = Build.IS_DEBUGGABLE ? 200 : 100;
        STATE_CHANGES = new LocalLog(i);
        INTERCEPTION_EVENTS = new LocalLog(i);
    }

    public static void append(int i, String str) {
        if (i == 1 || i == 12 || i == 20 || i == 19 || i == 18 || i == 21) {
            LocalLog localLog = INTERCEPTION_EVENTS;
            synchronized (localLog) {
                localLog.log(typeToString(i) + ": " + str);
            }
            return;
        }
        LocalLog localLog2 = STATE_CHANGES;
        synchronized (localLog2) {
            localLog2.log(typeToString(i) + ": " + str);
        }
    }

    public static String componentListToString(List list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            ComponentName componentName = (ComponentName) list.get(i);
            sb.append(componentName != null ? componentName.toShortString() : null);
        }
        return sb.toString();
    }

    public static void dump(PrintWriter printWriter) {
        LocalLog localLog = INTERCEPTION_EVENTS;
        synchronized (localLog) {
            printWriter.printf("    Interception Events:\n", new Object[0]);
            localLog.dump("    ", printWriter);
        }
        LocalLog localLog2 = STATE_CHANGES;
        synchronized (localLog2) {
            printWriter.printf("    State Changes:\n", new Object[0]);
            localLog2.dump("    ", printWriter);
        }
    }

    public static String ringerModeToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? "unknown" : "normal" : "vibrate" : "silent";
    }

    public static void traceConfig(
            String str,
            ComponentName componentName,
            ZenModeConfig zenModeConfig,
            ZenModeConfig zenModeConfig2,
            int i) {
        ZenModeDiff.ConfigDiff configDiff =
                new ZenModeDiff.ConfigDiff(zenModeConfig, zenModeConfig2);
        if (!configDiff.hasDiff()) {
            append(11, str + " no changes");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" - ");
        sb.append(componentName);
        sb.append(" : ");
        sb.append(i);
        sb.append(",\n");
        sb.append(zenModeConfig2 != null ? zenModeConfig2.toString() : null);
        sb.append(",\n");
        sb.append(configDiff);
        append(11, sb.toString());
    }

    public static void traceMatchesCallFilter(int i, String str, boolean z) {
        append(18, "result=" + z + ", reason=" + str + ", calling uid=" + i);
    }

    public static void traceNotIntercepted(NotificationRecord notificationRecord, String str) {
        append(12, notificationRecord.sbn.getKey() + "," + str);
    }

    public static void traceSetNotificationPolicy(
            String str, int i, NotificationManager.Policy policy) {
        StringBuilder m =
                StorageManagerService$$ExternalSyntheticOutline0.m(
                        i, "pkg=", str, " targetSdk=", " NotificationPolicy=");
        m.append(policy.toString());
        append(16, m.toString());
    }

    public static void traceSetZenMode(int i, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                i != 0
                        ? i != 1
                                ? i != 2 ? i != 3 ? "unknown" : "alarms" : "no_interruptions"
                                : "important_interruptions"
                        : "off");
        sb.append(",");
        sb.append(str);
        append(6, sb.toString());
    }

    public static String typeToString(int i) {
        if (i == 1) {
            return "intercepted";
        }
        if (i == 6) {
            return "set_zen_mode";
        }
        if (i == 3) {
            return "set_ringer_mode_external";
        }
        if (i == 4) {
            return "set_ringer_mode_internal";
        }
        switch (i) {
            case 9:
                return "subscribe";
            case 10:
                return "unsubscribe";
            case 11:
                return "config";
            case 12:
                return "not_intercepted";
            case 13:
                return "disable_effects";
            case 14:
                return "suppressor_changed";
            case 15:
                return "listener_hints_changed";
            case 16:
                return "set_notification_policy";
            case 17:
                return "set_consolidated_policy";
            case 18:
                return "matches_call_filter";
            case 19:
                return "record_caller";
            case 20:
                return "check_repeat_caller";
            case 21:
                return "alert_on_updated_intercept";
            case 22:
                return "matches_contact";
            default:
                return "unknown";
        }
    }
}
