package com.android.server.timezonedetector;

import android.app.time.LocationTimeZoneAlgorithmStatus;
import android.app.time.TimeZoneConfiguration;
import android.app.time.TimeZoneState;
import android.app.timezonedetector.ManualTimeZoneSuggestion;
import android.app.timezonedetector.TelephonyTimeZoneSuggestion;
import android.os.Binder;
import android.os.ShellCommand;
import android.os.SystemClock;
import com.android.server.timezonedetector.CallerIdentityInjector;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class TimeZoneDetectorShellCommand extends ShellCommand {
    public final TimeZoneDetectorService mInterface;

    public TimeZoneDetectorShellCommand(TimeZoneDetectorService timeZoneDetectorService) {
        this.mInterface = timeZoneDetectorService;
    }

    public final int onCommand(String str) {
        boolean z;
        boolean z2;
        final int i = 2;
        final int i2 = 0;
        final int i3 = 1;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str) {
            case "is_telephony_detection_supported":
                PrintWriter outPrintWriter = getOutPrintWriter();
                TimeZoneDetectorService timeZoneDetectorService = this.mInterface;
                timeZoneDetectorService.enforceManageTimeZoneDetectorPermission();
                TimeZoneDetectorStrategyImpl timeZoneDetectorStrategyImpl = (TimeZoneDetectorStrategyImpl) timeZoneDetectorService.mTimeZoneDetectorStrategy;
                synchronized (timeZoneDetectorStrategyImpl) {
                    z = timeZoneDetectorStrategyImpl.mCurrentConfigurationInternal.mTelephonyDetectionSupported;
                }
                outPrintWriter.println(z);
                return 0;
            case "suggest_manual_time_zone":
                Supplier supplier = new Supplier(this) { // from class: com.android.server.timezonedetector.TimeZoneDetectorShellCommand$$ExternalSyntheticLambda0
                    public final /* synthetic */ TimeZoneDetectorShellCommand f$0;

                    {
                        this.f$0 = this;
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference failed for: r5v10, types: [java.util.List] */
                    /* JADX WARN: Type inference failed for: r5v12 */
                    /* JADX WARN: Type inference failed for: r5v8, types: [java.util.ArrayList] */
                    /* JADX WARN: Type inference failed for: r5v9, types: [java.util.List] */
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        ?? arrayList;
                        int i4 = i;
                        TimeZoneDetectorShellCommand timeZoneDetectorShellCommand = this.f$0;
                        switch (i4) {
                            case 0:
                                GeolocationTimeZoneSuggestion geolocationTimeZoneSuggestion = null;
                                LocationTimeZoneAlgorithmStatus locationTimeZoneAlgorithmStatus = null;
                                String str2 = null;
                                while (true) {
                                    String nextArg = timeZoneDetectorShellCommand.getNextArg();
                                    if (nextArg == null) {
                                        if (locationTimeZoneAlgorithmStatus == null) {
                                            throw new IllegalArgumentException("Missing --status");
                                        }
                                        if (str2 != null) {
                                            if ("UNCERTAIN".equals(str2)) {
                                                arrayList = 0;
                                            } else if ("EMPTY".equals(str2)) {
                                                arrayList = Collections.emptyList();
                                            } else {
                                                arrayList = new ArrayList();
                                                StringTokenizer stringTokenizer = new StringTokenizer(str2, ",");
                                                while (stringTokenizer.hasMoreTokens()) {
                                                    arrayList.add(stringTokenizer.nextToken());
                                                }
                                            }
                                            long elapsedRealtime = SystemClock.elapsedRealtime();
                                            geolocationTimeZoneSuggestion = arrayList == 0 ? new GeolocationTimeZoneSuggestion(elapsedRealtime, null) : new GeolocationTimeZoneSuggestion(elapsedRealtime, arrayList);
                                        }
                                        LocationAlgorithmEvent locationAlgorithmEvent = new LocationAlgorithmEvent(locationTimeZoneAlgorithmStatus, geolocationTimeZoneSuggestion);
                                        locationAlgorithmEvent.addDebugInfo("Command line injection");
                                        return locationAlgorithmEvent;
                                    }
                                    if (nextArg.equals("--suggestion")) {
                                        str2 = timeZoneDetectorShellCommand.getNextArgRequired();
                                    } else {
                                        if (!nextArg.equals("--status")) {
                                            throw new IllegalArgumentException("Unknown option: ".concat(nextArg));
                                        }
                                        locationTimeZoneAlgorithmStatus = LocationTimeZoneAlgorithmStatus.parseCommandlineArg(timeZoneDetectorShellCommand.getNextArgRequired());
                                    }
                                }
                            case 1:
                                timeZoneDetectorShellCommand.getClass();
                                return TelephonyTimeZoneSuggestion.parseCommandLineArg(timeZoneDetectorShellCommand);
                            default:
                                timeZoneDetectorShellCommand.getClass();
                                return ManualTimeZoneSuggestion.parseCommandLineArg(timeZoneDetectorShellCommand);
                        }
                    }
                };
                final TimeZoneDetectorService timeZoneDetectorService2 = this.mInterface;
                Objects.requireNonNull(timeZoneDetectorService2);
                return runSingleArgMethod(supplier, new Consumer() { // from class: com.android.server.timezonedetector.TimeZoneDetectorShellCommand$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i4 = i;
                        TimeZoneDetectorService timeZoneDetectorService3 = timeZoneDetectorService2;
                        switch (i4) {
                            case 0:
                                LocationAlgorithmEvent locationAlgorithmEvent = (LocationAlgorithmEvent) obj;
                                timeZoneDetectorService3.mContext.enforceCallingPermission("android.permission.SET_TIME_ZONE", "suggest geolocation time zone");
                                Objects.requireNonNull(locationAlgorithmEvent);
                                timeZoneDetectorService3.mHandler.post(new TimeZoneDetectorService$$ExternalSyntheticLambda0(timeZoneDetectorService3, locationAlgorithmEvent, 1));
                                break;
                            case 1:
                                timeZoneDetectorService3.suggestTelephonyTimeZone((TelephonyTimeZoneSuggestion) obj);
                                break;
                            default:
                                timeZoneDetectorService3.suggestManualTimeZone((ManualTimeZoneSuggestion) obj);
                                break;
                        }
                    }
                });
            case "get_time_zone_state":
                getOutPrintWriter().println(this.mInterface.getTimeZoneState());
                return 0;
            case "set_time_zone_state_for_tests":
                TimeZoneState parseCommandLineArgs = TimeZoneState.parseCommandLineArgs(this);
                TimeZoneDetectorService timeZoneDetectorService3 = this.mInterface;
                timeZoneDetectorService3.enforceManageTimeZoneDetectorPermission();
                ((CallerIdentityInjector.Real) timeZoneDetectorService3.mCallerIdentityInjector).getClass();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    TimeZoneDetectorStrategyImpl timeZoneDetectorStrategyImpl2 = (TimeZoneDetectorStrategyImpl) timeZoneDetectorService3.mTimeZoneDetectorStrategy;
                    timeZoneDetectorStrategyImpl2.getClass();
                    Objects.requireNonNull(parseCommandLineArgs);
                    timeZoneDetectorStrategyImpl2.mEnvironment.setDeviceTimeZoneAndConfidence(parseCommandLineArgs.getUserShouldConfirmId() ? 0 : 100, parseCommandLineArgs.getId(), "setTimeZoneState()");
                    return 0;
                } finally {
                    ((CallerIdentityInjector.Real) timeZoneDetectorService3.mCallerIdentityInjector).getClass();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            case "is_auto_detection_enabled":
                getOutPrintWriter().println(this.mInterface.getCapabilitiesAndConfig(-2).getConfiguration().isAutoDetectionEnabled());
                return 0;
            case "dump_metrics":
                PrintWriter outPrintWriter2 = getOutPrintWriter();
                TimeZoneDetectorService timeZoneDetectorService4 = this.mInterface;
                timeZoneDetectorService4.enforceManageTimeZoneDetectorPermission();
                MetricsTimeZoneDetectorState generateMetricsState = ((TimeZoneDetectorStrategyImpl) timeZoneDetectorService4.mTimeZoneDetectorStrategy).generateMetricsState();
                outPrintWriter2.println("MetricsTimeZoneDetectorState:");
                outPrintWriter2.println(generateMetricsState.toString());
                return 0;
            case "set_geo_detection_enabled":
                return !this.mInterface.updateConfiguration(-2, new TimeZoneConfiguration.Builder().setGeoDetectionEnabled(Boolean.parseBoolean(getNextArgRequired())).build()) ? 1 : 0;
            case "confirm_time_zone":
                String str2 = null;
                while (true) {
                    String nextArg = getNextArg();
                    if (nextArg == null) {
                        if (str2 == null) {
                            throw new IllegalArgumentException("No zoneId specified.");
                        }
                        getOutPrintWriter().println(this.mInterface.confirmTimeZone(str2));
                        return 0;
                    }
                    if (!nextArg.equals("--zone_id")) {
                        throw new IllegalArgumentException("Unknown option: ".concat(nextArg));
                    }
                    str2 = getNextArgRequired();
                }
            case "is_geo_detection_enabled":
                getOutPrintWriter().println(this.mInterface.getCapabilitiesAndConfig(-2).getConfiguration().isGeoDetectionEnabled());
                return 0;
            case "suggest_telephony_time_zone":
                Supplier supplier2 = new Supplier(this) { // from class: com.android.server.timezonedetector.TimeZoneDetectorShellCommand$$ExternalSyntheticLambda0
                    public final /* synthetic */ TimeZoneDetectorShellCommand f$0;

                    {
                        this.f$0 = this;
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference failed for: r5v10, types: [java.util.List] */
                    /* JADX WARN: Type inference failed for: r5v12 */
                    /* JADX WARN: Type inference failed for: r5v8, types: [java.util.ArrayList] */
                    /* JADX WARN: Type inference failed for: r5v9, types: [java.util.List] */
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        ?? arrayList;
                        int i4 = i3;
                        TimeZoneDetectorShellCommand timeZoneDetectorShellCommand = this.f$0;
                        switch (i4) {
                            case 0:
                                GeolocationTimeZoneSuggestion geolocationTimeZoneSuggestion = null;
                                LocationTimeZoneAlgorithmStatus locationTimeZoneAlgorithmStatus = null;
                                String str22 = null;
                                while (true) {
                                    String nextArg2 = timeZoneDetectorShellCommand.getNextArg();
                                    if (nextArg2 == null) {
                                        if (locationTimeZoneAlgorithmStatus == null) {
                                            throw new IllegalArgumentException("Missing --status");
                                        }
                                        if (str22 != null) {
                                            if ("UNCERTAIN".equals(str22)) {
                                                arrayList = 0;
                                            } else if ("EMPTY".equals(str22)) {
                                                arrayList = Collections.emptyList();
                                            } else {
                                                arrayList = new ArrayList();
                                                StringTokenizer stringTokenizer = new StringTokenizer(str22, ",");
                                                while (stringTokenizer.hasMoreTokens()) {
                                                    arrayList.add(stringTokenizer.nextToken());
                                                }
                                            }
                                            long elapsedRealtime = SystemClock.elapsedRealtime();
                                            geolocationTimeZoneSuggestion = arrayList == 0 ? new GeolocationTimeZoneSuggestion(elapsedRealtime, null) : new GeolocationTimeZoneSuggestion(elapsedRealtime, arrayList);
                                        }
                                        LocationAlgorithmEvent locationAlgorithmEvent = new LocationAlgorithmEvent(locationTimeZoneAlgorithmStatus, geolocationTimeZoneSuggestion);
                                        locationAlgorithmEvent.addDebugInfo("Command line injection");
                                        return locationAlgorithmEvent;
                                    }
                                    if (nextArg2.equals("--suggestion")) {
                                        str22 = timeZoneDetectorShellCommand.getNextArgRequired();
                                    } else {
                                        if (!nextArg2.equals("--status")) {
                                            throw new IllegalArgumentException("Unknown option: ".concat(nextArg2));
                                        }
                                        locationTimeZoneAlgorithmStatus = LocationTimeZoneAlgorithmStatus.parseCommandlineArg(timeZoneDetectorShellCommand.getNextArgRequired());
                                    }
                                }
                            case 1:
                                timeZoneDetectorShellCommand.getClass();
                                return TelephonyTimeZoneSuggestion.parseCommandLineArg(timeZoneDetectorShellCommand);
                            default:
                                timeZoneDetectorShellCommand.getClass();
                                return ManualTimeZoneSuggestion.parseCommandLineArg(timeZoneDetectorShellCommand);
                        }
                    }
                };
                final TimeZoneDetectorService timeZoneDetectorService5 = this.mInterface;
                Objects.requireNonNull(timeZoneDetectorService5);
                return runSingleArgMethod(supplier2, new Consumer() { // from class: com.android.server.timezonedetector.TimeZoneDetectorShellCommand$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i4 = i3;
                        TimeZoneDetectorService timeZoneDetectorService32 = timeZoneDetectorService5;
                        switch (i4) {
                            case 0:
                                LocationAlgorithmEvent locationAlgorithmEvent = (LocationAlgorithmEvent) obj;
                                timeZoneDetectorService32.mContext.enforceCallingPermission("android.permission.SET_TIME_ZONE", "suggest geolocation time zone");
                                Objects.requireNonNull(locationAlgorithmEvent);
                                timeZoneDetectorService32.mHandler.post(new TimeZoneDetectorService$$ExternalSyntheticLambda0(timeZoneDetectorService32, locationAlgorithmEvent, 1));
                                break;
                            case 1:
                                timeZoneDetectorService32.suggestTelephonyTimeZone((TelephonyTimeZoneSuggestion) obj);
                                break;
                            default:
                                timeZoneDetectorService32.suggestManualTimeZone((ManualTimeZoneSuggestion) obj);
                                break;
                        }
                    }
                });
            case "enable_telephony_fallback":
                TimeZoneDetectorService timeZoneDetectorService6 = this.mInterface;
                timeZoneDetectorService6.enforceManageTimeZoneDetectorPermission();
                ((TimeZoneDetectorStrategyImpl) timeZoneDetectorService6.mTimeZoneDetectorStrategy).enableTelephonyTimeZoneFallback("Command line");
                return 0;
            case "is_geo_detection_supported":
                PrintWriter outPrintWriter3 = getOutPrintWriter();
                TimeZoneDetectorService timeZoneDetectorService7 = this.mInterface;
                timeZoneDetectorService7.enforceManageTimeZoneDetectorPermission();
                TimeZoneDetectorStrategyImpl timeZoneDetectorStrategyImpl3 = (TimeZoneDetectorStrategyImpl) timeZoneDetectorService7.mTimeZoneDetectorStrategy;
                synchronized (timeZoneDetectorStrategyImpl3) {
                    z2 = timeZoneDetectorStrategyImpl3.mCurrentConfigurationInternal.mGeoDetectionSupported;
                }
                outPrintWriter3.println(z2);
                return 0;
            case "handle_location_algorithm_event":
                Supplier supplier3 = new Supplier(this) { // from class: com.android.server.timezonedetector.TimeZoneDetectorShellCommand$$ExternalSyntheticLambda0
                    public final /* synthetic */ TimeZoneDetectorShellCommand f$0;

                    {
                        this.f$0 = this;
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference failed for: r5v10, types: [java.util.List] */
                    /* JADX WARN: Type inference failed for: r5v12 */
                    /* JADX WARN: Type inference failed for: r5v8, types: [java.util.ArrayList] */
                    /* JADX WARN: Type inference failed for: r5v9, types: [java.util.List] */
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        ?? arrayList;
                        int i4 = i2;
                        TimeZoneDetectorShellCommand timeZoneDetectorShellCommand = this.f$0;
                        switch (i4) {
                            case 0:
                                GeolocationTimeZoneSuggestion geolocationTimeZoneSuggestion = null;
                                LocationTimeZoneAlgorithmStatus locationTimeZoneAlgorithmStatus = null;
                                String str22 = null;
                                while (true) {
                                    String nextArg2 = timeZoneDetectorShellCommand.getNextArg();
                                    if (nextArg2 == null) {
                                        if (locationTimeZoneAlgorithmStatus == null) {
                                            throw new IllegalArgumentException("Missing --status");
                                        }
                                        if (str22 != null) {
                                            if ("UNCERTAIN".equals(str22)) {
                                                arrayList = 0;
                                            } else if ("EMPTY".equals(str22)) {
                                                arrayList = Collections.emptyList();
                                            } else {
                                                arrayList = new ArrayList();
                                                StringTokenizer stringTokenizer = new StringTokenizer(str22, ",");
                                                while (stringTokenizer.hasMoreTokens()) {
                                                    arrayList.add(stringTokenizer.nextToken());
                                                }
                                            }
                                            long elapsedRealtime = SystemClock.elapsedRealtime();
                                            geolocationTimeZoneSuggestion = arrayList == 0 ? new GeolocationTimeZoneSuggestion(elapsedRealtime, null) : new GeolocationTimeZoneSuggestion(elapsedRealtime, arrayList);
                                        }
                                        LocationAlgorithmEvent locationAlgorithmEvent = new LocationAlgorithmEvent(locationTimeZoneAlgorithmStatus, geolocationTimeZoneSuggestion);
                                        locationAlgorithmEvent.addDebugInfo("Command line injection");
                                        return locationAlgorithmEvent;
                                    }
                                    if (nextArg2.equals("--suggestion")) {
                                        str22 = timeZoneDetectorShellCommand.getNextArgRequired();
                                    } else {
                                        if (!nextArg2.equals("--status")) {
                                            throw new IllegalArgumentException("Unknown option: ".concat(nextArg2));
                                        }
                                        locationTimeZoneAlgorithmStatus = LocationTimeZoneAlgorithmStatus.parseCommandlineArg(timeZoneDetectorShellCommand.getNextArgRequired());
                                    }
                                }
                            case 1:
                                timeZoneDetectorShellCommand.getClass();
                                return TelephonyTimeZoneSuggestion.parseCommandLineArg(timeZoneDetectorShellCommand);
                            default:
                                timeZoneDetectorShellCommand.getClass();
                                return ManualTimeZoneSuggestion.parseCommandLineArg(timeZoneDetectorShellCommand);
                        }
                    }
                };
                final TimeZoneDetectorService timeZoneDetectorService8 = this.mInterface;
                Objects.requireNonNull(timeZoneDetectorService8);
                return runSingleArgMethod(supplier3, new Consumer() { // from class: com.android.server.timezonedetector.TimeZoneDetectorShellCommand$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i4 = i2;
                        TimeZoneDetectorService timeZoneDetectorService32 = timeZoneDetectorService8;
                        switch (i4) {
                            case 0:
                                LocationAlgorithmEvent locationAlgorithmEvent = (LocationAlgorithmEvent) obj;
                                timeZoneDetectorService32.mContext.enforceCallingPermission("android.permission.SET_TIME_ZONE", "suggest geolocation time zone");
                                Objects.requireNonNull(locationAlgorithmEvent);
                                timeZoneDetectorService32.mHandler.post(new TimeZoneDetectorService$$ExternalSyntheticLambda0(timeZoneDetectorService32, locationAlgorithmEvent, 1));
                                break;
                            case 1:
                                timeZoneDetectorService32.suggestTelephonyTimeZone((TelephonyTimeZoneSuggestion) obj);
                                break;
                            default:
                                timeZoneDetectorService32.suggestManualTimeZone((ManualTimeZoneSuggestion) obj);
                                break;
                        }
                    }
                });
            case "set_auto_detection_enabled":
                return !this.mInterface.updateConfiguration(-2, new TimeZoneConfiguration.Builder().setAutoDetectionEnabled(Boolean.parseBoolean(getNextArgRequired())).build()) ? 1 : 0;
            default:
                return handleDefaultCommands(str);
        }
    }

    public final void onHelp() {
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
        outPrintWriter.println("Location algorithm event options:");
        outPrintWriter.println("  --status {LocationTimeZoneAlgorithmStatus toString() format}");
        outPrintWriter.println("  [--suggestion {UNCERTAIN|EMPTY|<Olson ID>+}]");
        outPrintWriter.println();
        outPrintWriter.println("See " + LocationAlgorithmEvent.class.getName() + " for more information");
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
}
