package com.android.server.timezonedetector;

import android.app.time.TimeZoneConfiguration;
import android.app.time.TimeZoneState;
import android.app.timezonedetector.ManualTimeZoneSuggestion;
import android.app.timezonedetector.TelephonyTimeZoneSuggestion;
import android.os.ShellCommand;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class TimeZoneDetectorShellCommand extends ShellCommand {
    public final TimeZoneDetectorService mInterface;

    public TimeZoneDetectorShellCommand(TimeZoneDetectorService timeZoneDetectorService) {
        this.mInterface = timeZoneDetectorService;
    }

    public int onCommand(String str) {
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str) {
        }
        return handleDefaultCommands(str);
    }

    public final int runIsAutoDetectionEnabled() {
        getOutPrintWriter().println(this.mInterface.getCapabilitiesAndConfig(-2).getConfiguration().isAutoDetectionEnabled());
        return 0;
    }

    public final int runIsTelephonyDetectionSupported() {
        getOutPrintWriter().println(this.mInterface.isTelephonyTimeZoneDetectionSupported());
        return 0;
    }

    public final int runIsGeoDetectionSupported() {
        getOutPrintWriter().println(this.mInterface.isGeoTimeZoneDetectionSupported());
        return 0;
    }

    public final int runIsGeoDetectionEnabled() {
        getOutPrintWriter().println(this.mInterface.getCapabilitiesAndConfig(-2).getConfiguration().isGeoDetectionEnabled());
        return 0;
    }

    public final int runSetAutoDetectionEnabled() {
        return !this.mInterface.updateConfiguration(-2, new TimeZoneConfiguration.Builder().setAutoDetectionEnabled(Boolean.parseBoolean(getNextArgRequired())).build()) ? 1 : 0;
    }

    public final int runSetGeoDetectionEnabled() {
        return !this.mInterface.updateConfiguration(-2, new TimeZoneConfiguration.Builder().setGeoDetectionEnabled(Boolean.parseBoolean(getNextArgRequired())).build()) ? 1 : 0;
    }

    public final int runHandleLocationEvent() {
        Supplier supplier = new Supplier() { // from class: com.android.server.timezonedetector.TimeZoneDetectorShellCommand$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                LocationAlgorithmEvent lambda$runHandleLocationEvent$0;
                lambda$runHandleLocationEvent$0 = TimeZoneDetectorShellCommand.this.lambda$runHandleLocationEvent$0();
                return lambda$runHandleLocationEvent$0;
            }
        };
        final TimeZoneDetectorService timeZoneDetectorService = this.mInterface;
        Objects.requireNonNull(timeZoneDetectorService);
        return runSingleArgMethod(supplier, new Consumer() { // from class: com.android.server.timezonedetector.TimeZoneDetectorShellCommand$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TimeZoneDetectorService.this.handleLocationAlgorithmEvent((LocationAlgorithmEvent) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LocationAlgorithmEvent lambda$runHandleLocationEvent$0() {
        return LocationAlgorithmEvent.parseCommandLineArg(this);
    }

    public final int runSuggestManualTimeZone() {
        Supplier supplier = new Supplier() { // from class: com.android.server.timezonedetector.TimeZoneDetectorShellCommand$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                ManualTimeZoneSuggestion lambda$runSuggestManualTimeZone$1;
                lambda$runSuggestManualTimeZone$1 = TimeZoneDetectorShellCommand.this.lambda$runSuggestManualTimeZone$1();
                return lambda$runSuggestManualTimeZone$1;
            }
        };
        final TimeZoneDetectorService timeZoneDetectorService = this.mInterface;
        Objects.requireNonNull(timeZoneDetectorService);
        return runSingleArgMethod(supplier, new Consumer() { // from class: com.android.server.timezonedetector.TimeZoneDetectorShellCommand$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TimeZoneDetectorService.this.suggestManualTimeZone((ManualTimeZoneSuggestion) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ManualTimeZoneSuggestion lambda$runSuggestManualTimeZone$1() {
        return ManualTimeZoneSuggestion.parseCommandLineArg(this);
    }

    public final int runSuggestTelephonyTimeZone() {
        Supplier supplier = new Supplier() { // from class: com.android.server.timezonedetector.TimeZoneDetectorShellCommand$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                TelephonyTimeZoneSuggestion lambda$runSuggestTelephonyTimeZone$2;
                lambda$runSuggestTelephonyTimeZone$2 = TimeZoneDetectorShellCommand.this.lambda$runSuggestTelephonyTimeZone$2();
                return lambda$runSuggestTelephonyTimeZone$2;
            }
        };
        final TimeZoneDetectorService timeZoneDetectorService = this.mInterface;
        Objects.requireNonNull(timeZoneDetectorService);
        return runSingleArgMethod(supplier, new Consumer() { // from class: com.android.server.timezonedetector.TimeZoneDetectorShellCommand$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TimeZoneDetectorService.this.suggestTelephonyTimeZone((TelephonyTimeZoneSuggestion) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ TelephonyTimeZoneSuggestion lambda$runSuggestTelephonyTimeZone$2() {
        return TelephonyTimeZoneSuggestion.parseCommandLineArg(this);
    }

    public final int runSingleArgMethod(Supplier supplier, Consumer consumer) {
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            Object obj = supplier.get();
            if (obj == null) {
                outPrintWriter.println("Error: arg not specified");
                return 1;
            }
            consumer.accept(obj);
            outPrintWriter.println("Arg " + obj + " injected.");
            return 0;
        } catch (RuntimeException e) {
            outPrintWriter.println(e);
            return 1;
        }
    }

    public final int runEnableTelephonyFallback() {
        this.mInterface.enableTelephonyFallback("Command line");
        return 0;
    }

    public final int runGetTimeZoneState() {
        getOutPrintWriter().println(this.mInterface.getTimeZoneState());
        return 0;
    }

    public final int runSetTimeZoneState() {
        this.mInterface.setTimeZoneState(TimeZoneState.parseCommandLineArgs(this));
        return 0;
    }

    public final int runConfirmTimeZone() {
        getOutPrintWriter().println(this.mInterface.confirmTimeZone(parseTimeZoneIdArg(this)));
        return 0;
    }

    public static String parseTimeZoneIdArg(ShellCommand shellCommand) {
        String str = null;
        while (true) {
            String nextArg = shellCommand.getNextArg();
            if (nextArg == null) {
                if (str != null) {
                    return str;
                }
                throw new IllegalArgumentException("No zoneId specified.");
            }
            if (nextArg.equals("--zone_id")) {
                str = shellCommand.getNextArgRequired();
            } else {
                throw new IllegalArgumentException("Unknown option: " + nextArg);
            }
        }
    }

    public final int runDumpMetrics() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        MetricsTimeZoneDetectorState generateMetricsState = this.mInterface.generateMetricsState();
        outPrintWriter.println("MetricsTimeZoneDetectorState:");
        outPrintWriter.println(generateMetricsState.toString());
        return 0;
    }

    public void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.printf("Time Zone Detector (%s) commands:\n", "time_zone_detector");
        outPrintWriter.printf("  help\n", new Object[0]);
        outPrintWriter.printf("    Print this help text.\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "is_auto_detection_enabled");
        outPrintWriter.printf("    Prints true/false according to the automatic time zone detection setting\n", new Object[0]);
        outPrintWriter.printf("  %s true|false\n", "set_auto_detection_enabled");
        outPrintWriter.printf("    Sets the automatic time zone detection setting.\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "is_telephony_detection_supported");
        outPrintWriter.printf("    Prints true/false according to whether telephony time zone detection is supported on this device.\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "is_geo_detection_supported");
        outPrintWriter.printf("    Prints true/false according to whether geolocation time zone detection is supported on this device.\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "is_geo_detection_enabled");
        outPrintWriter.printf("    Prints true/false according to the geolocation time zone detection setting.\n", new Object[0]);
        outPrintWriter.printf("  %s true|false\n", "set_geo_detection_enabled");
        outPrintWriter.printf("    Sets the geolocation time zone detection enabled setting.\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "enable_telephony_fallback");
        outPrintWriter.printf("    Signals that telephony time zone detection fall back can be used if geolocation detection is supported and enabled.\n)", new Object[0]);
        outPrintWriter.printf("    This is a temporary state until geolocation detection becomes \"certain\".\n", new Object[0]);
        outPrintWriter.printf("    To have an effect this requires that the telephony fallback feature is supported on the device, see below for device_config flags.\n", new Object[0]);
        outPrintWriter.printf("  %s <location event opts>\n", "handle_location_algorithm_event");
        outPrintWriter.printf("    Simulates an event from the location time zone detection algorithm.\n", new Object[0]);
        outPrintWriter.printf("  %s <manual suggestion opts>\n", "suggest_manual_time_zone");
        outPrintWriter.printf("    Suggests a time zone as if supplied by a user manually.\n", new Object[0]);
        outPrintWriter.printf("  %s <telephony suggestion opts>\n", "suggest_telephony_time_zone");
        outPrintWriter.printf("    Simulates a time zone suggestion from the telephony time zone detection algorithm.\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "get_time_zone_state");
        outPrintWriter.printf("    Returns the current time zone setting state.\n", new Object[0]);
        outPrintWriter.printf("  %s <time zone state options>\n", "set_time_zone_state_for_tests");
        outPrintWriter.printf("    Sets the current time zone state for tests.\n", new Object[0]);
        outPrintWriter.printf("  %s <--zone_id Olson ID>\n", "confirm_time_zone");
        outPrintWriter.printf("    Tries to confirms the time zone, raising the confidence.\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "dump_metrics");
        outPrintWriter.printf("    Dumps the service metrics to stdout for inspection.\n", new Object[0]);
        outPrintWriter.println();
        LocationAlgorithmEvent.printCommandLineOpts(outPrintWriter);
        outPrintWriter.println();
        ManualTimeZoneSuggestion.printCommandLineOpts(outPrintWriter);
        outPrintWriter.println();
        TelephonyTimeZoneSuggestion.printCommandLineOpts(outPrintWriter);
        outPrintWriter.println();
        TimeZoneState.printCommandLineOpts(outPrintWriter);
        outPrintWriter.println();
        outPrintWriter.printf("This service is also affected by the following device_config flags in the %s namespace:\n", "system_time");
        outPrintWriter.printf("  %s\n", "location_time_zone_detection_feature_supported");
        outPrintWriter.printf("    Only observed if the geolocation time zone detection feature is enabled in config.\n", new Object[0]);
        outPrintWriter.printf("    Set this to false to disable the feature.\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "location_time_zone_detection_run_in_background_enabled");
        outPrintWriter.printf("    Runs geolocation time zone detection even when it not enabled by the user. The result is not used to set the device's time zone [*]\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "location_time_zone_detection_setting_enabled_default");
        outPrintWriter.printf("    Only used if the device does not have an explicit 'geolocation time zone detection enabled' setting stored [*].\n", new Object[0]);
        outPrintWriter.printf("    The default is when unset is false.\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "location_time_zone_detection_setting_enabled_override");
        outPrintWriter.printf("    Used to override the device's 'geolocation time zone detection enabled' setting [*].\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "time_zone_detector_auto_detection_enabled_default");
        outPrintWriter.printf("    Used to set the automatic time zone detection enabled default, i.e. when the device's automatic time zone detection enabled setting hasn't been set explicitly. Intended for internal testers.", new Object[0]);
        outPrintWriter.printf("  %s\n", "time_zone_detector_telephony_fallback_supported");
        outPrintWriter.printf("    Used to enable / disable support for telephony detection fallback. Also see the %s command.\n", "enable_telephony_fallback");
        outPrintWriter.printf("  %s\n", "enhanced_metrics_collection_enabled");
        outPrintWriter.printf("    Used to increase the detail of metrics collected / reported.\n", new Object[0]);
        outPrintWriter.println();
        outPrintWriter.printf("[*] To be enabled, the user must still have location = on / auto time zone detection = on.\n", new Object[0]);
        outPrintWriter.println();
        outPrintWriter.printf("See \"adb shell cmd device_config\" for more information on setting flags.\n", new Object[0]);
        outPrintWriter.println();
        outPrintWriter.printf("Also see \"adb shell cmd %s help\" for lower-level location time zone commands / settings.\n", "location_time_zone_manager");
        outPrintWriter.println();
    }
}
