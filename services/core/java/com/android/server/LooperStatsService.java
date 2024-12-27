package com.android.server;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.net.util.NetworkConstants;
import android.os.Binder;
import android.os.Looper;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.KeyValueListParser;
import android.util.Slog;

import com.android.internal.os.AppIdToPackageMap;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.CachedDeviceState;
import com.android.internal.os.LooperStats;
import com.android.internal.util.DumpUtils;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class LooperStatsService extends Binder {
    public final Context mContext;
    public final LooperStats mStats;
    public boolean mEnabled = false;
    public boolean mTrackScreenInteractive = false;
    public boolean mIgnoreBatteryStatus = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public final LooperStatsService mService;
        public final SettingsObserver mSettingsObserver;
        public final LooperStats mStats;

        public Lifecycle(Context context) {
            super(context);
            LooperStats looperStats = new LooperStats(1000, NetworkConstants.ETHER_MTU);
            this.mStats = looperStats;
            LooperStatsService looperStatsService =
                    new LooperStatsService(getContext(), looperStats);
            this.mService = looperStatsService;
            this.mSettingsObserver = new SettingsObserver(looperStatsService);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            if (500 == i) {
                LooperStatsService.m67$$Nest$minitFromSettings(this.mService);
                getContext()
                        .getContentResolver()
                        .registerContentObserver(
                                Settings.Global.getUriFor("looper_stats"),
                                false,
                                this.mSettingsObserver,
                                0);
                this.mStats.setDeviceState(
                        (CachedDeviceState.Readonly)
                                getLocalService(CachedDeviceState.Readonly.class));
            }
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            publishLocalService(LooperStats.class, this.mStats);
            publishBinderService("looper_stats", this.mService);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LooperShellCommand extends ShellCommand {
        public LooperShellCommand() {}

        public final int onCommand(String str) {
            if ("enable".equals(str)) {
                LooperStatsService.this.setEnabled(true);
                return 0;
            }
            if ("disable".equals(str)) {
                LooperStatsService.this.setEnabled(false);
                return 0;
            }
            if ("reset".equals(str)) {
                LooperStatsService.this.mStats.reset();
                return 0;
            }
            if (!"sampling_interval".equals(str)) {
                return handleDefaultCommands(str);
            }
            int parseUnsignedInt = Integer.parseUnsignedInt(getNextArgRequired());
            LooperStatsService looperStatsService = LooperStatsService.this;
            if (parseUnsignedInt > 0) {
                looperStatsService.mStats.setSamplingInterval(parseUnsignedInt);
            } else {
                looperStatsService.getClass();
                Slog.w(
                        "LooperStatsService",
                        "Ignored invalid sampling interval (value must be positive): "
                                + parseUnsignedInt);
            }
            return 0;
        }

        public final void onHelp() {
            PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("looper_stats commands:");
            outPrintWriter.println("  enable: Enable collecting stats.");
            outPrintWriter.println("  disable: Disable collecting stats.");
            outPrintWriter.println("  sampling_interval: Change the sampling interval.");
            outPrintWriter.println("  reset: Reset stats.");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public final LooperStatsService mService;

        public SettingsObserver(LooperStatsService looperStatsService) {
            super(BackgroundThread.getHandler());
            this.mService = looperStatsService;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            LooperStatsService.m67$$Nest$minitFromSettings(this.mService);
        }
    }

    /* renamed from: -$$Nest$minitFromSettings, reason: not valid java name */
    public static void m67$$Nest$minitFromSettings(LooperStatsService looperStatsService) {
        looperStatsService.getClass();
        KeyValueListParser keyValueListParser = new KeyValueListParser(',');
        try {
            keyValueListParser.setString(
                    Settings.Global.getString(
                            looperStatsService.mContext.getContentResolver(), "looper_stats"));
        } catch (IllegalArgumentException e) {
            Slog.e("LooperStatsService", "Bad looper_stats settings", e);
        }
        int i = keyValueListParser.getInt("sampling_interval", 1000);
        if (i > 0) {
            looperStatsService.mStats.setSamplingInterval(i);
        } else {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i,
                    "Ignored invalid sampling interval (value must be positive): ",
                    "LooperStatsService");
        }
        boolean z = keyValueListParser.getBoolean("track_screen_state", false);
        if (looperStatsService.mTrackScreenInteractive != z) {
            looperStatsService.mTrackScreenInteractive = z;
            looperStatsService.mStats.reset();
        }
        boolean z2 = keyValueListParser.getBoolean("ignore_battery_status", false);
        if (looperStatsService.mIgnoreBatteryStatus != z2) {
            looperStatsService.mStats.setIgnoreBatteryStatus(z2);
            looperStatsService.mIgnoreBatteryStatus = z2;
            looperStatsService.mStats.reset();
        }
        looperStatsService.setEnabled(
                SystemProperties.getBoolean(
                        "debug.sys.looper_stats_enabled",
                        keyValueListParser.getBoolean("enabled", true)));
    }

    public LooperStatsService(Context context, LooperStats looperStats) {
        this.mContext = context;
        this.mStats = looperStats;
    }

    @Override // android.os.Binder
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "LooperStatsService", printWriter)) {
            AppIdToPackageMap snapshot = AppIdToPackageMap.getSnapshot();
            printWriter.print("Start time: ");
            printWriter.println(
                    DateFormat.format("yyyy-MM-dd HH:mm:ss", this.mStats.getStartTimeMillis()));
            printWriter.print("On battery time (ms): ");
            printWriter.println(this.mStats.getBatteryTimeMillis());
            List entries = this.mStats.getEntries();
            final int i = 0;
            final int i2 = 1;
            Comparator thenComparing =
                    Comparator.comparing(
                                    new Function() { // from class:
                                                     // com.android.server.LooperStatsService$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            LooperStats.ExportedEntry exportedEntry =
                                                    (LooperStats.ExportedEntry) obj;
                                            switch (i) {
                                                case 0:
                                                    return Integer.valueOf(
                                                            exportedEntry.workSourceUid);
                                                case 1:
                                                    return exportedEntry.threadName;
                                                case 2:
                                                    return exportedEntry.handlerClassName;
                                                default:
                                                    return exportedEntry.messageName;
                                            }
                                        }
                                    })
                            .thenComparing(
                                    new Function() { // from class:
                                                     // com.android.server.LooperStatsService$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            LooperStats.ExportedEntry exportedEntry =
                                                    (LooperStats.ExportedEntry) obj;
                                            switch (i2) {
                                                case 0:
                                                    return Integer.valueOf(
                                                            exportedEntry.workSourceUid);
                                                case 1:
                                                    return exportedEntry.threadName;
                                                case 2:
                                                    return exportedEntry.handlerClassName;
                                                default:
                                                    return exportedEntry.messageName;
                                            }
                                        }
                                    });
            final int i3 = 2;
            Comparator thenComparing2 =
                    thenComparing.thenComparing(
                            new Function() { // from class:
                                             // com.android.server.LooperStatsService$$ExternalSyntheticLambda0
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    LooperStats.ExportedEntry exportedEntry =
                                            (LooperStats.ExportedEntry) obj;
                                    switch (i3) {
                                        case 0:
                                            return Integer.valueOf(exportedEntry.workSourceUid);
                                        case 1:
                                            return exportedEntry.threadName;
                                        case 2:
                                            return exportedEntry.handlerClassName;
                                        default:
                                            return exportedEntry.messageName;
                                    }
                                }
                            });
            final int i4 = 3;
            entries.sort(
                    thenComparing2.thenComparing(
                            new Function() { // from class:
                                             // com.android.server.LooperStatsService$$ExternalSyntheticLambda0
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    LooperStats.ExportedEntry exportedEntry =
                                            (LooperStats.ExportedEntry) obj;
                                    switch (i4) {
                                        case 0:
                                            return Integer.valueOf(exportedEntry.workSourceUid);
                                        case 1:
                                            return exportedEntry.threadName;
                                        case 2:
                                            return exportedEntry.handlerClassName;
                                        default:
                                            return exportedEntry.messageName;
                                    }
                                }
                            }));
            printWriter.println(
                    String.join(
                            ",",
                            Arrays.asList(
                                    "work_source_uid",
                                    "thread_name",
                                    "handler_class",
                                    "message_name",
                                    "is_interactive",
                                    "message_count",
                                    "recorded_message_count",
                                    "total_latency_micros",
                                    "max_latency_micros",
                                    "total_cpu_micros",
                                    "max_cpu_micros",
                                    "recorded_delay_message_count",
                                    "total_delay_millis",
                                    "max_delay_millis",
                                    "exception_count")));
            Iterator it = entries.iterator();
            while (it.hasNext()) {
                LooperStats.ExportedEntry exportedEntry = (LooperStats.ExportedEntry) it.next();
                if (!exportedEntry.messageName.startsWith("__DEBUG_")) {
                    printWriter.printf(
                            "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                            snapshot.mapUid(exportedEntry.workSourceUid),
                            exportedEntry.threadName,
                            exportedEntry.handlerClassName,
                            exportedEntry.messageName,
                            Boolean.valueOf(exportedEntry.isInteractive),
                            Long.valueOf(exportedEntry.messageCount),
                            Long.valueOf(exportedEntry.recordedMessageCount),
                            Long.valueOf(exportedEntry.totalLatencyMicros),
                            Long.valueOf(exportedEntry.maxLatencyMicros),
                            Long.valueOf(exportedEntry.cpuUsageMicros),
                            Long.valueOf(exportedEntry.maxCpuUsageMicros),
                            Long.valueOf(exportedEntry.recordedDelayMessageCount),
                            Long.valueOf(exportedEntry.delayMillis),
                            Long.valueOf(exportedEntry.maxDelayMillis),
                            Long.valueOf(exportedEntry.exceptionCount));
                    it = it;
                }
            }
        }
    }

    public final void onShellCommand(
            FileDescriptor fileDescriptor,
            FileDescriptor fileDescriptor2,
            FileDescriptor fileDescriptor3,
            String[] strArr,
            ShellCallback shellCallback,
            ResultReceiver resultReceiver) {
        new LooperShellCommand()
                .exec(
                        this,
                        fileDescriptor,
                        fileDescriptor2,
                        fileDescriptor3,
                        strArr,
                        shellCallback,
                        resultReceiver);
    }

    public final void setEnabled(boolean z) {
        if (this.mEnabled != z) {
            this.mEnabled = z;
            this.mStats.reset();
            this.mStats.setAddDebugEntries(z);
            Looper.setObserver(z ? this.mStats : null);
        }
    }
}
