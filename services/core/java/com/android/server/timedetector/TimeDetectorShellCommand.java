package com.android.server.timedetector;

import android.app.time.ExternalTimeSuggestion;
import android.app.time.TimeConfiguration;
import android.app.time.TimeState;
import android.app.time.UnixEpochTime;
import android.app.timedetector.ManualTimeSuggestion;
import android.app.timedetector.TelephonyTimeSuggestion;
import android.app.timedetector.TimeSuggestionHelper;
import android.os.Binder;
import android.os.ShellCommand;

import com.android.server.location.gnss.TimeDetectorNetworkTimeHelper;
import com.android.server.timezonedetector.CallerIdentityInjector;

import java.io.PrintWriter;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TimeDetectorShellCommand extends ShellCommand {
    public final TimeDetectorService mInterface;

    public TimeDetectorShellCommand(TimeDetectorService timeDetectorService) {
        this.mInterface = timeDetectorService;
    }

    public final int onCommand(String str) {
        final int i = 4;
        final int i2 = 3;
        final int i3 = 2;
        final int i4 = 1;
        final int i5 = 0;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str) {
            case "suggest_telephony_time":
                Supplier supplier =
                        new Supplier(
                                this) { // from class:
                                        // com.android.server.timedetector.TimeDetectorShellCommand$$ExternalSyntheticLambda0
                            public final /* synthetic */ TimeDetectorShellCommand f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.Supplier
                            public final Object get() {
                                int i6 = i4;
                                TimeDetectorShellCommand timeDetectorShellCommand = this.f$0;
                                timeDetectorShellCommand.getClass();
                                switch (i6) {
                                    case 0:
                                        return NetworkTimeSuggestion.parseCommandLineArg(
                                                timeDetectorShellCommand);
                                    case 1:
                                        return TelephonyTimeSuggestion.parseCommandLineArg(
                                                timeDetectorShellCommand);
                                    case 2:
                                        return new GnssTimeSuggestion(
                                                TimeSuggestionHelper.handleParseCommandLineArg(
                                                        GnssTimeSuggestion.class,
                                                        timeDetectorShellCommand));
                                    case 3:
                                        return ExternalTimeSuggestion.parseCommandLineArg(
                                                timeDetectorShellCommand);
                                    default:
                                        return ManualTimeSuggestion.parseCommandLineArg(
                                                timeDetectorShellCommand);
                                }
                            }
                        };
                final TimeDetectorService timeDetectorService = this.mInterface;
                Objects.requireNonNull(timeDetectorService);
                return runSuggestTime(
                        supplier,
                        new Consumer() { // from class:
                                         // com.android.server.timedetector.TimeDetectorShellCommand$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i6 = i4;
                                TimeDetectorService timeDetectorService2 = timeDetectorService;
                                switch (i6) {
                                    case 0:
                                        NetworkTimeSuggestion networkTimeSuggestion =
                                                (NetworkTimeSuggestion) obj;
                                        timeDetectorService2.mContext.enforceCallingPermission(
                                                "android.permission.SET_TIME",
                                                "suggest network time");
                                        Objects.requireNonNull(networkTimeSuggestion);
                                        timeDetectorService2.mHandler.post(
                                                new TimeDetectorService$$ExternalSyntheticLambda0(
                                                        timeDetectorService2,
                                                        networkTimeSuggestion,
                                                        3));
                                        break;
                                    case 1:
                                        timeDetectorService2.suggestTelephonyTime(
                                                (TelephonyTimeSuggestion) obj);
                                        break;
                                    case 2:
                                        GnssTimeSuggestion gnssTimeSuggestion =
                                                (GnssTimeSuggestion) obj;
                                        timeDetectorService2.mContext.enforceCallingPermission(
                                                "android.permission.SET_TIME", "suggest gnss time");
                                        Objects.requireNonNull(gnssTimeSuggestion);
                                        timeDetectorService2.mHandler.post(
                                                new TimeDetectorService$$ExternalSyntheticLambda0(
                                                        timeDetectorService2,
                                                        gnssTimeSuggestion,
                                                        2));
                                        break;
                                    case 3:
                                        timeDetectorService2.suggestExternalTime(
                                                (ExternalTimeSuggestion) obj);
                                        break;
                                    default:
                                        timeDetectorService2.suggestManualTime(
                                                (ManualTimeSuggestion) obj);
                                        break;
                                }
                            }
                        });
            case "is_auto_detection_enabled":
                getOutPrintWriter()
                        .println(
                                this.mInterface
                                        .getCapabilitiesAndConfig()
                                        .getConfiguration()
                                        .isAutoDetectionEnabled());
                return 0;
            case "suggest_network_time":
                Supplier supplier2 =
                        new Supplier(
                                this) { // from class:
                                        // com.android.server.timedetector.TimeDetectorShellCommand$$ExternalSyntheticLambda0
                            public final /* synthetic */ TimeDetectorShellCommand f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.Supplier
                            public final Object get() {
                                int i6 = i5;
                                TimeDetectorShellCommand timeDetectorShellCommand = this.f$0;
                                timeDetectorShellCommand.getClass();
                                switch (i6) {
                                    case 0:
                                        return NetworkTimeSuggestion.parseCommandLineArg(
                                                timeDetectorShellCommand);
                                    case 1:
                                        return TelephonyTimeSuggestion.parseCommandLineArg(
                                                timeDetectorShellCommand);
                                    case 2:
                                        return new GnssTimeSuggestion(
                                                TimeSuggestionHelper.handleParseCommandLineArg(
                                                        GnssTimeSuggestion.class,
                                                        timeDetectorShellCommand));
                                    case 3:
                                        return ExternalTimeSuggestion.parseCommandLineArg(
                                                timeDetectorShellCommand);
                                    default:
                                        return ManualTimeSuggestion.parseCommandLineArg(
                                                timeDetectorShellCommand);
                                }
                            }
                        };
                final TimeDetectorService timeDetectorService2 = this.mInterface;
                Objects.requireNonNull(timeDetectorService2);
                return runSuggestTime(
                        supplier2,
                        new Consumer() { // from class:
                                         // com.android.server.timedetector.TimeDetectorShellCommand$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i6 = i5;
                                TimeDetectorService timeDetectorService22 = timeDetectorService2;
                                switch (i6) {
                                    case 0:
                                        NetworkTimeSuggestion networkTimeSuggestion =
                                                (NetworkTimeSuggestion) obj;
                                        timeDetectorService22.mContext.enforceCallingPermission(
                                                "android.permission.SET_TIME",
                                                "suggest network time");
                                        Objects.requireNonNull(networkTimeSuggestion);
                                        timeDetectorService22.mHandler.post(
                                                new TimeDetectorService$$ExternalSyntheticLambda0(
                                                        timeDetectorService22,
                                                        networkTimeSuggestion,
                                                        3));
                                        break;
                                    case 1:
                                        timeDetectorService22.suggestTelephonyTime(
                                                (TelephonyTimeSuggestion) obj);
                                        break;
                                    case 2:
                                        GnssTimeSuggestion gnssTimeSuggestion =
                                                (GnssTimeSuggestion) obj;
                                        timeDetectorService22.mContext.enforceCallingPermission(
                                                "android.permission.SET_TIME", "suggest gnss time");
                                        Objects.requireNonNull(gnssTimeSuggestion);
                                        timeDetectorService22.mHandler.post(
                                                new TimeDetectorService$$ExternalSyntheticLambda0(
                                                        timeDetectorService22,
                                                        gnssTimeSuggestion,
                                                        2));
                                        break;
                                    case 3:
                                        timeDetectorService22.suggestExternalTime(
                                                (ExternalTimeSuggestion) obj);
                                        break;
                                    default:
                                        timeDetectorService22.suggestManualTime(
                                                (ManualTimeSuggestion) obj);
                                        break;
                                }
                            }
                        });
            case "suggest_gnss_time":
                Supplier supplier3 =
                        new Supplier(
                                this) { // from class:
                                        // com.android.server.timedetector.TimeDetectorShellCommand$$ExternalSyntheticLambda0
                            public final /* synthetic */ TimeDetectorShellCommand f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.Supplier
                            public final Object get() {
                                int i6 = i3;
                                TimeDetectorShellCommand timeDetectorShellCommand = this.f$0;
                                timeDetectorShellCommand.getClass();
                                switch (i6) {
                                    case 0:
                                        return NetworkTimeSuggestion.parseCommandLineArg(
                                                timeDetectorShellCommand);
                                    case 1:
                                        return TelephonyTimeSuggestion.parseCommandLineArg(
                                                timeDetectorShellCommand);
                                    case 2:
                                        return new GnssTimeSuggestion(
                                                TimeSuggestionHelper.handleParseCommandLineArg(
                                                        GnssTimeSuggestion.class,
                                                        timeDetectorShellCommand));
                                    case 3:
                                        return ExternalTimeSuggestion.parseCommandLineArg(
                                                timeDetectorShellCommand);
                                    default:
                                        return ManualTimeSuggestion.parseCommandLineArg(
                                                timeDetectorShellCommand);
                                }
                            }
                        };
                final TimeDetectorService timeDetectorService3 = this.mInterface;
                Objects.requireNonNull(timeDetectorService3);
                return runSuggestTime(
                        supplier3,
                        new Consumer() { // from class:
                                         // com.android.server.timedetector.TimeDetectorShellCommand$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i6 = i3;
                                TimeDetectorService timeDetectorService22 = timeDetectorService3;
                                switch (i6) {
                                    case 0:
                                        NetworkTimeSuggestion networkTimeSuggestion =
                                                (NetworkTimeSuggestion) obj;
                                        timeDetectorService22.mContext.enforceCallingPermission(
                                                "android.permission.SET_TIME",
                                                "suggest network time");
                                        Objects.requireNonNull(networkTimeSuggestion);
                                        timeDetectorService22.mHandler.post(
                                                new TimeDetectorService$$ExternalSyntheticLambda0(
                                                        timeDetectorService22,
                                                        networkTimeSuggestion,
                                                        3));
                                        break;
                                    case 1:
                                        timeDetectorService22.suggestTelephonyTime(
                                                (TelephonyTimeSuggestion) obj);
                                        break;
                                    case 2:
                                        GnssTimeSuggestion gnssTimeSuggestion =
                                                (GnssTimeSuggestion) obj;
                                        timeDetectorService22.mContext.enforceCallingPermission(
                                                "android.permission.SET_TIME", "suggest gnss time");
                                        Objects.requireNonNull(gnssTimeSuggestion);
                                        timeDetectorService22.mHandler.post(
                                                new TimeDetectorService$$ExternalSyntheticLambda0(
                                                        timeDetectorService22,
                                                        gnssTimeSuggestion,
                                                        2));
                                        break;
                                    case 3:
                                        timeDetectorService22.suggestExternalTime(
                                                (ExternalTimeSuggestion) obj);
                                        break;
                                    default:
                                        timeDetectorService22.suggestManualTime(
                                                (ManualTimeSuggestion) obj);
                                        break;
                                }
                            }
                        });
            case "set_time_state_for_tests":
                TimeState parseCommandLineArgs = TimeState.parseCommandLineArgs(this);
                TimeDetectorService timeDetectorService4 = this.mInterface;
                timeDetectorService4.enforceManageTimeDetectorPermission();
                ((CallerIdentityInjector.Real) timeDetectorService4.mCallerIdentityInjector)
                        .getClass();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    ((TimeDetectorStrategyImpl) timeDetectorService4.mTimeDetectorStrategy)
                            .setTimeState(parseCommandLineArgs);
                    return 0;
                } finally {
                }
            case "get_time_state":
                getOutPrintWriter().println(this.mInterface.getTimeState());
                return 0;
            case "suggest_external_time":
                Supplier supplier4 =
                        new Supplier(
                                this) { // from class:
                                        // com.android.server.timedetector.TimeDetectorShellCommand$$ExternalSyntheticLambda0
                            public final /* synthetic */ TimeDetectorShellCommand f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.Supplier
                            public final Object get() {
                                int i6 = i2;
                                TimeDetectorShellCommand timeDetectorShellCommand = this.f$0;
                                timeDetectorShellCommand.getClass();
                                switch (i6) {
                                    case 0:
                                        return NetworkTimeSuggestion.parseCommandLineArg(
                                                timeDetectorShellCommand);
                                    case 1:
                                        return TelephonyTimeSuggestion.parseCommandLineArg(
                                                timeDetectorShellCommand);
                                    case 2:
                                        return new GnssTimeSuggestion(
                                                TimeSuggestionHelper.handleParseCommandLineArg(
                                                        GnssTimeSuggestion.class,
                                                        timeDetectorShellCommand));
                                    case 3:
                                        return ExternalTimeSuggestion.parseCommandLineArg(
                                                timeDetectorShellCommand);
                                    default:
                                        return ManualTimeSuggestion.parseCommandLineArg(
                                                timeDetectorShellCommand);
                                }
                            }
                        };
                final TimeDetectorService timeDetectorService5 = this.mInterface;
                Objects.requireNonNull(timeDetectorService5);
                return runSuggestTime(
                        supplier4,
                        new Consumer() { // from class:
                                         // com.android.server.timedetector.TimeDetectorShellCommand$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i6 = i2;
                                TimeDetectorService timeDetectorService22 = timeDetectorService5;
                                switch (i6) {
                                    case 0:
                                        NetworkTimeSuggestion networkTimeSuggestion =
                                                (NetworkTimeSuggestion) obj;
                                        timeDetectorService22.mContext.enforceCallingPermission(
                                                "android.permission.SET_TIME",
                                                "suggest network time");
                                        Objects.requireNonNull(networkTimeSuggestion);
                                        timeDetectorService22.mHandler.post(
                                                new TimeDetectorService$$ExternalSyntheticLambda0(
                                                        timeDetectorService22,
                                                        networkTimeSuggestion,
                                                        3));
                                        break;
                                    case 1:
                                        timeDetectorService22.suggestTelephonyTime(
                                                (TelephonyTimeSuggestion) obj);
                                        break;
                                    case 2:
                                        GnssTimeSuggestion gnssTimeSuggestion =
                                                (GnssTimeSuggestion) obj;
                                        timeDetectorService22.mContext.enforceCallingPermission(
                                                "android.permission.SET_TIME", "suggest gnss time");
                                        Objects.requireNonNull(gnssTimeSuggestion);
                                        timeDetectorService22.mHandler.post(
                                                new TimeDetectorService$$ExternalSyntheticLambda0(
                                                        timeDetectorService22,
                                                        gnssTimeSuggestion,
                                                        2));
                                        break;
                                    case 3:
                                        timeDetectorService22.suggestExternalTime(
                                                (ExternalTimeSuggestion) obj);
                                        break;
                                    default:
                                        timeDetectorService22.suggestManualTime(
                                                (ManualTimeSuggestion) obj);
                                        break;
                                }
                            }
                        });
            case "get_network_time":
                getOutPrintWriter()
                        .println(
                                ((TimeDetectorStrategyImpl) this.mInterface.mTimeDetectorStrategy)
                                        .getLatestNetworkSuggestion());
                return 0;
            case "clear_network_time":
                TimeDetectorService timeDetectorService6 = this.mInterface;
                timeDetectorService6.mContext.enforceCallingPermission(
                        "android.permission.SET_TIME", "suggest network time");
                ((CallerIdentityInjector.Real) timeDetectorService6.mCallerIdentityInjector)
                        .getClass();
                long clearCallingIdentity2 = Binder.clearCallingIdentity();
                try {
                    ((TimeDetectorStrategyImpl) timeDetectorService6.mTimeDetectorStrategy)
                            .clearLatestNetworkSuggestion();
                    return 0;
                } finally {
                }
            case "suggest_manual_time":
                Supplier supplier5 =
                        new Supplier(
                                this) { // from class:
                                        // com.android.server.timedetector.TimeDetectorShellCommand$$ExternalSyntheticLambda0
                            public final /* synthetic */ TimeDetectorShellCommand f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.Supplier
                            public final Object get() {
                                int i6 = i;
                                TimeDetectorShellCommand timeDetectorShellCommand = this.f$0;
                                timeDetectorShellCommand.getClass();
                                switch (i6) {
                                    case 0:
                                        return NetworkTimeSuggestion.parseCommandLineArg(
                                                timeDetectorShellCommand);
                                    case 1:
                                        return TelephonyTimeSuggestion.parseCommandLineArg(
                                                timeDetectorShellCommand);
                                    case 2:
                                        return new GnssTimeSuggestion(
                                                TimeSuggestionHelper.handleParseCommandLineArg(
                                                        GnssTimeSuggestion.class,
                                                        timeDetectorShellCommand));
                                    case 3:
                                        return ExternalTimeSuggestion.parseCommandLineArg(
                                                timeDetectorShellCommand);
                                    default:
                                        return ManualTimeSuggestion.parseCommandLineArg(
                                                timeDetectorShellCommand);
                                }
                            }
                        };
                final TimeDetectorService timeDetectorService7 = this.mInterface;
                Objects.requireNonNull(timeDetectorService7);
                return runSuggestTime(
                        supplier5,
                        new Consumer() { // from class:
                                         // com.android.server.timedetector.TimeDetectorShellCommand$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i6 = i;
                                TimeDetectorService timeDetectorService22 = timeDetectorService7;
                                switch (i6) {
                                    case 0:
                                        NetworkTimeSuggestion networkTimeSuggestion =
                                                (NetworkTimeSuggestion) obj;
                                        timeDetectorService22.mContext.enforceCallingPermission(
                                                "android.permission.SET_TIME",
                                                "suggest network time");
                                        Objects.requireNonNull(networkTimeSuggestion);
                                        timeDetectorService22.mHandler.post(
                                                new TimeDetectorService$$ExternalSyntheticLambda0(
                                                        timeDetectorService22,
                                                        networkTimeSuggestion,
                                                        3));
                                        break;
                                    case 1:
                                        timeDetectorService22.suggestTelephonyTime(
                                                (TelephonyTimeSuggestion) obj);
                                        break;
                                    case 2:
                                        GnssTimeSuggestion gnssTimeSuggestion =
                                                (GnssTimeSuggestion) obj;
                                        timeDetectorService22.mContext.enforceCallingPermission(
                                                "android.permission.SET_TIME", "suggest gnss time");
                                        Objects.requireNonNull(gnssTimeSuggestion);
                                        timeDetectorService22.mHandler.post(
                                                new TimeDetectorService$$ExternalSyntheticLambda0(
                                                        timeDetectorService22,
                                                        gnssTimeSuggestion,
                                                        2));
                                        break;
                                    case 3:
                                        timeDetectorService22.suggestExternalTime(
                                                (ExternalTimeSuggestion) obj);
                                        break;
                                    default:
                                        timeDetectorService22.suggestManualTime(
                                                (ManualTimeSuggestion) obj);
                                        break;
                                }
                            }
                        });
            case "clear_system_clock_network_time":
                TimeDetectorService timeDetectorService8 = this.mInterface;
                timeDetectorService8.mContext.enforceCallingPermission(
                        "android.permission.SET_TIME", "suggest network time");
                ((CallerIdentityInjector.Real) timeDetectorService8.mCallerIdentityInjector)
                        .getClass();
                long clearCallingIdentity3 = Binder.clearCallingIdentity();
                try {
                    boolean z = TimeDetectorNetworkTimeHelper.DEBUG;
                    ((TimeDetectorStrategyImpl) timeDetectorService8.mTimeDetectorStrategy)
                            .clearLatestNetworkSuggestion();
                    return 0;
                } finally {
                }
            case "set_system_clock_network_time":
                NetworkTimeSuggestion parseCommandLineArg =
                        NetworkTimeSuggestion.parseCommandLineArg(this);
                TimeDetectorService timeDetectorService9 = this.mInterface;
                UnixEpochTime unixEpochTime = parseCommandLineArg.mUnixEpochTime;
                timeDetectorService9.mContext.enforceCallingPermission(
                        "android.permission.SET_TIME", "suggest network time");
                boolean z2 = TimeDetectorNetworkTimeHelper.DEBUG;
                NetworkTimeSuggestion networkTimeSuggestion =
                        new NetworkTimeSuggestion(
                                unixEpochTime, parseCommandLineArg.mUncertaintyMillis);
                networkTimeSuggestion.addDebugInfo("Injected for tests");
                ((TimeDetectorStrategyImpl) timeDetectorService9.mTimeDetectorStrategy)
                        .suggestNetworkTime(networkTimeSuggestion);
                return 0;
            case "set_auto_detection_enabled":
                return !this.mInterface.updateConfiguration(
                                -2,
                                new TimeConfiguration.Builder()
                                        .setAutoDetectionEnabled(
                                                Boolean.parseBoolean(getNextArgRequired()))
                                        .build())
                        ? 1
                        : 0;
            case "confirm_time":
                getOutPrintWriter()
                        .println(
                                this.mInterface.confirmTime(
                                        UnixEpochTime.parseCommandLineArgs(this)));
                return 0;
            default:
                return handleDefaultCommands(str);
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.printf("Time Detector (%s) commands:\n", "time_detector");
        outPrintWriter.printf("  help\n", new Object[0]);
        outPrintWriter.printf("    Print this help text.\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "is_auto_detection_enabled");
        outPrintWriter.printf(
                "    Prints true/false according to the automatic time detection setting.\n",
                new Object[0]);
        outPrintWriter.printf("  %s true|false\n", "set_auto_detection_enabled");
        outPrintWriter.printf("    Sets the automatic time detection setting.\n", new Object[0]);
        outPrintWriter.println();
        outPrintWriter.printf("  %s <manual suggestion opts>\n", "suggest_manual_time");
        outPrintWriter.printf(
                "    Suggests a time as if via the \"manual\" origin.\n", new Object[0]);
        outPrintWriter.printf("  %s <telephony suggestion opts>\n", "suggest_telephony_time");
        outPrintWriter.printf(
                "    Suggests a time as if via the \"telephony\" origin.\n", new Object[0]);
        outPrintWriter.printf("  %s <network suggestion opts>\n", "suggest_network_time");
        outPrintWriter.printf(
                "    Suggests a time as if via the \"network\" origin.\n", new Object[0]);
        outPrintWriter.printf("  %s <gnss suggestion opts>\n", "suggest_gnss_time");
        outPrintWriter.printf(
                "    Suggests a time as if via the \"gnss\" origin.\n", new Object[0]);
        outPrintWriter.printf("  %s <external suggestion opts>\n", "suggest_external_time");
        outPrintWriter.printf(
                "    Suggests a time as if via the \"external\" origin.\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "get_time_state");
        outPrintWriter.printf("    Returns the current time setting state.\n", new Object[0]);
        outPrintWriter.printf("  %s <time state options>\n", "set_time_state_for_tests");
        outPrintWriter.printf("    Sets the current time state for tests.\n", new Object[0]);
        outPrintWriter.printf("  %s <unix epoch time options>\n", "confirm_time");
        outPrintWriter.printf(
                "    Tries to confirms the time, raising the confidence.\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "get_network_time");
        outPrintWriter.printf(
                "    Prints the network time information held by the detector.\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "clear_network_time");
        outPrintWriter.printf(
                "    Clears the network time information held by the detector.\n", new Object[0]);
        outPrintWriter.printf("  %s <network suggestion opts>\n", "set_system_clock_network_time");
        outPrintWriter.printf(
                "    Sets the network time information used for"
                    + " SystemClock.currentNetworkTimeClock().\n",
                new Object[0]);
        outPrintWriter.printf("  %s\n", "clear_system_clock_network_time");
        outPrintWriter.printf(
                "    Clears the network time information used for"
                    + " SystemClock.currentNetworkTimeClock().\n",
                new Object[0]);
        outPrintWriter.println();
        ManualTimeSuggestion.printCommandLineOpts(outPrintWriter);
        outPrintWriter.println();
        TelephonyTimeSuggestion.printCommandLineOpts(outPrintWriter);
        outPrintWriter.println();
        outPrintWriter.printf("%s suggestion options:\n", "Network");
        outPrintWriter.println(
                "  --elapsed_realtime <elapsed realtime millis> - the elapsed realtime millis when"
                    + " unix epoch time was read");
        outPrintWriter.println("  --unix_epoch_time <Unix epoch time millis>");
        outPrintWriter.println(
                "  --uncertainty_millis <Uncertainty millis> - a positive error bound (+/-)"
                    + " estimate for unix epoch time");
        outPrintWriter.println();
        outPrintWriter.println(
                "See " + NetworkTimeSuggestion.class.getName() + " for more information");
        outPrintWriter.println();
        TimeSuggestionHelper.handlePrintCommandLineOpts(
                outPrintWriter, "GNSS", GnssTimeSuggestion.class);
        outPrintWriter.println();
        ExternalTimeSuggestion.printCommandLineOpts(outPrintWriter);
        outPrintWriter.println();
        TimeState.printCommandLineOpts(outPrintWriter);
        outPrintWriter.println();
        UnixEpochTime.printCommandLineOpts(outPrintWriter);
        outPrintWriter.println();
        outPrintWriter.printf(
                "This service is also affected by the following device_config flags in the %s"
                    + " namespace:\n",
                "system_time");
        outPrintWriter.printf("  %s\n", "time_detector_lower_bound_millis_override");
        outPrintWriter.printf(
                "    The lower bound used to validate time suggestions when they are received.\n",
                new Object[0]);
        outPrintWriter.printf(
                "    Specified in milliseconds since the start of the Unix epoch.\n",
                new Object[0]);
        outPrintWriter.printf("  %s\n", "time_detector_origin_priorities_override");
        outPrintWriter.printf(
                "    A comma separated list of origins. See TimeDetectorStrategy for details.\n",
                new Object[0]);
        outPrintWriter.println();
        outPrintWriter.printf(
                "See \"adb shell cmd device_config\" for more information on setting flags.\n",
                new Object[0]);
        outPrintWriter.println();
    }

    public final int runSuggestTime(Supplier supplier, Consumer consumer) {
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            Object obj = supplier.get();
            if (obj == null) {
                outPrintWriter.println("Error: suggestion not specified");
                return 1;
            }
            consumer.accept(obj);
            outPrintWriter.println("Suggestion " + obj + " injected.");
            return 0;
        } catch (RuntimeException e) {
            outPrintWriter.println(e);
            return 1;
        }
    }
}
