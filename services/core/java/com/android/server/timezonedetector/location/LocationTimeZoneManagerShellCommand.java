package com.android.server.timezonedetector.location;

import android.app.time.LocationTimeZoneAlgorithmStatus;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.ShellCommand;
import android.util.IndentingPrintWriter;
import android.util.proto.ProtoOutputStream;

import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.server.timezonedetector.GeolocationTimeZoneSuggestion;
import com.android.server.timezonedetector.LocationAlgorithmEvent;
import com.android.server.timezonedetector.ServiceConfigAccessorImpl;
import com.android.server.timezonedetector.ServiceConfigAccessorImpl$$ExternalSyntheticLambda1;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knoxguard.service.utils.Constants;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class LocationTimeZoneManagerShellCommand extends ShellCommand {
    public final LocationTimeZoneManagerService mService;

    public LocationTimeZoneManagerShellCommand(
            LocationTimeZoneManagerService locationTimeZoneManagerService) {
        this.mService = locationTimeZoneManagerService;
    }

    public static void writeProviderStates(
            DualDumpOutputStream dualDumpOutputStream, List list, String str, long j) {
        int i;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            LocationTimeZoneProvider.ProviderState providerState =
                    (LocationTimeZoneProvider.ProviderState) it.next();
            long start = dualDumpOutputStream.start(str, j);
            int i2 = providerState.stateEnum;
            switch (i2) {
                case 0:
                    i = 0;
                    break;
                case 1:
                    i = 1;
                    break;
                case 2:
                    i = 2;
                    break;
                case 3:
                    i = 3;
                    break;
                case 4:
                    i = 4;
                    break;
                case 5:
                    i = 5;
                    break;
                case 6:
                    i = 6;
                    break;
                default:
                    throw new IllegalArgumentException(
                            VibrationParam$1$$ExternalSyntheticOutline0.m(
                                    i2, "Unknown stateEnum="));
            }
            dualDumpOutputStream.write(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 1159641169921L, i);
            dualDumpOutputStream.end(start);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int onCommand(String str) {
        char c;
        char c2;
        int i;
        int i2 = 1;
        int i3 = 2;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str.hashCode()) {
            case -385184143:
                if (str.equals("start_with_test_providers")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3540994:
                if (str.equals("stop")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 109757538:
                if (str.equals("start")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 248094771:
                if (str.equals("clear_recorded_provider_states")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 943200902:
                if (str.equals("dump_state")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                final String nextArgRequired = getNextArgRequired();
                if (nextArgRequired.equals("@null")) {
                    nextArgRequired = null;
                }
                String nextArgRequired2 = getNextArgRequired();
                final String str2 = nextArgRequired2.equals("@null") ? null : nextArgRequired2;
                final boolean parseBoolean = Boolean.parseBoolean(getNextArgRequired());
                try {
                    final LocationTimeZoneManagerService locationTimeZoneManagerService =
                            this.mService;
                    locationTimeZoneManagerService.mContext.enforceCallingPermission(
                            "android.permission.MANAGE_TIME_AND_ZONE_DETECTION",
                            "manage time and time zone detection");
                    if (nextArgRequired == null && str2 == null) {
                        throw new IllegalArgumentException(
                                "One or both test package names must be provided.");
                    }
                    locationTimeZoneManagerService.mThreadingDomain.postAndWait(
                            new Runnable() { // from class:
                                             // com.android.server.timezonedetector.location.LocationTimeZoneManagerService$$ExternalSyntheticLambda4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    LocationTimeZoneManagerService locationTimeZoneManagerService2 =
                                            LocationTimeZoneManagerService.this;
                                    String str3 = nextArgRequired;
                                    String str4 = str2;
                                    boolean z = parseBoolean;
                                    synchronized (locationTimeZoneManagerService2.mSharedLock) {
                                        try {
                                            locationTimeZoneManagerService2.stopOnDomainThread();
                                            ServiceConfigAccessorImpl serviceConfigAccessorImpl =
                                                    (ServiceConfigAccessorImpl)
                                                            locationTimeZoneManagerService2
                                                                    .mServiceConfigAccessor;
                                            synchronized (serviceConfigAccessorImpl) {
                                                try {
                                                    serviceConfigAccessorImpl
                                                                    .mTestPrimaryLocationTimeZoneProviderPackageName =
                                                            str3;
                                                    serviceConfigAccessorImpl
                                                                    .mTestPrimaryLocationTimeZoneProviderMode =
                                                            str3 == null ? "disabled" : "enabled";
                                                    serviceConfigAccessorImpl
                                                            .mContext
                                                            .getMainThreadHandler()
                                                            .post(
                                                                    new ServiceConfigAccessorImpl$$ExternalSyntheticLambda1(
                                                                            serviceConfigAccessorImpl));
                                                } finally {
                                                }
                                            }
                                            ServiceConfigAccessorImpl serviceConfigAccessorImpl2 =
                                                    (ServiceConfigAccessorImpl)
                                                            locationTimeZoneManagerService2
                                                                    .mServiceConfigAccessor;
                                            synchronized (serviceConfigAccessorImpl2) {
                                                try {
                                                    serviceConfigAccessorImpl2
                                                                    .mTestSecondaryLocationTimeZoneProviderPackageName =
                                                            str4;
                                                    serviceConfigAccessorImpl2
                                                                    .mTestSecondaryLocationTimeZoneProviderMode =
                                                            str4 == null ? "disabled" : "enabled";
                                                    serviceConfigAccessorImpl2
                                                            .mContext
                                                            .getMainThreadHandler()
                                                            .post(
                                                                    new ServiceConfigAccessorImpl$$ExternalSyntheticLambda1(
                                                                            serviceConfigAccessorImpl2));
                                                } finally {
                                                }
                                            }
                                            ServiceConfigAccessorImpl serviceConfigAccessorImpl3 =
                                                    (ServiceConfigAccessorImpl)
                                                            locationTimeZoneManagerService2
                                                                    .mServiceConfigAccessor;
                                            synchronized (serviceConfigAccessorImpl3) {
                                                serviceConfigAccessorImpl3
                                                                .mRecordStateChangesForTests =
                                                        z;
                                            }
                                            locationTimeZoneManagerService2.startOnDomainThread();
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                            },
                            LocationTimeZoneManagerService.BLOCKING_OP_WAIT_DURATION_MILLIS);
                    getOutPrintWriter().println("Service started (test mode)");
                    return 0;
                } catch (RuntimeException e) {
                    reportError(e);
                    return 1;
                }
            case 1:
                try {
                    LocationTimeZoneManagerService locationTimeZoneManagerService2 = this.mService;
                    locationTimeZoneManagerService2.mContext.enforceCallingPermission(
                            "android.permission.MANAGE_TIME_AND_ZONE_DETECTION",
                            "manage time and time zone detection");
                    locationTimeZoneManagerService2.mThreadingDomain.postAndWait(
                            new LocationTimeZoneManagerService$$ExternalSyntheticLambda1(
                                    locationTimeZoneManagerService2, 0),
                            LocationTimeZoneManagerService.BLOCKING_OP_WAIT_DURATION_MILLIS);
                    getOutPrintWriter().println("Service stopped");
                    return 0;
                } catch (RuntimeException e2) {
                    reportError(e2);
                    return 1;
                }
            case 2:
                try {
                    LocationTimeZoneManagerService locationTimeZoneManagerService3 = this.mService;
                    locationTimeZoneManagerService3.mContext.enforceCallingPermission(
                            "android.permission.MANAGE_TIME_AND_ZONE_DETECTION",
                            "manage time and time zone detection");
                    locationTimeZoneManagerService3.mThreadingDomain.postAndWait(
                            new LocationTimeZoneManagerService$$ExternalSyntheticLambda1(
                                    locationTimeZoneManagerService3, i2),
                            LocationTimeZoneManagerService.BLOCKING_OP_WAIT_DURATION_MILLIS);
                    getOutPrintWriter().println("Service started");
                    return 0;
                } catch (RuntimeException e3) {
                    reportError(e3);
                    return 1;
                }
            case 3:
                try {
                    LocationTimeZoneManagerService locationTimeZoneManagerService4 = this.mService;
                    locationTimeZoneManagerService4.mContext.enforceCallingPermission(
                            "android.permission.MANAGE_TIME_AND_ZONE_DETECTION",
                            "manage time and time zone detection");
                    locationTimeZoneManagerService4.mThreadingDomain.postAndWait(
                            new LocationTimeZoneManagerService$$ExternalSyntheticLambda1(
                                    locationTimeZoneManagerService4, i3),
                            LocationTimeZoneManagerService.BLOCKING_OP_WAIT_DURATION_MILLIS);
                    return 0;
                } catch (IllegalStateException e4) {
                    reportError(e4);
                    return 2;
                }
            case 4:
                try {
                    final LocationTimeZoneManagerService locationTimeZoneManagerService5 =
                            this.mService;
                    locationTimeZoneManagerService5.mContext.enforceCallingPermission(
                            "android.permission.MANAGE_TIME_AND_ZONE_DETECTION",
                            "manage time and time zone detection");
                    try {
                        LocationTimeZoneManagerServiceState locationTimeZoneManagerServiceState =
                                (LocationTimeZoneManagerServiceState)
                                        locationTimeZoneManagerService5.mThreadingDomain
                                                .postAndWait(
                                                        new Callable() { // from class:
                                                                         // com.android.server.timezonedetector.location.LocationTimeZoneManagerService$$ExternalSyntheticLambda0
                                                            @Override // java.util.concurrent.Callable
                                                            public final Object call() {
                                                                LocationTimeZoneManagerService
                                                                        locationTimeZoneManagerService6 =
                                                                                LocationTimeZoneManagerService
                                                                                        .this;
                                                                synchronized (
                                                                        locationTimeZoneManagerService6
                                                                                .mSharedLock) {
                                                                    try {
                                                                        LocationTimeZoneProviderController
                                                                                locationTimeZoneProviderController =
                                                                                        locationTimeZoneManagerService6
                                                                                                .mLocationTimeZoneProviderController;
                                                                        if (locationTimeZoneProviderController
                                                                                == null) {
                                                                            return null;
                                                                        }
                                                                        return locationTimeZoneProviderController
                                                                                .getStateForTests();
                                                                    } finally {
                                                                    }
                                                                }
                                                            }
                                                        },
                                                        LocationTimeZoneManagerService
                                                                .BLOCKING_OP_WAIT_DURATION_MILLIS);
                        if (locationTimeZoneManagerServiceState != null) {
                            DualDumpOutputStream dualDumpOutputStream =
                                    "--proto".equals(getNextOption())
                                            ? new DualDumpOutputStream(
                                                    new ProtoOutputStream(getOutFileDescriptor()))
                                            : new DualDumpOutputStream(
                                                    new IndentingPrintWriter(
                                                            getOutPrintWriter(), "  "));
                            LocationAlgorithmEvent locationAlgorithmEvent =
                                    locationTimeZoneManagerServiceState.mLastEvent;
                            if (locationAlgorithmEvent != null) {
                                long start =
                                        dualDumpOutputStream.start("last_event", 1146756268033L);
                                LocationTimeZoneAlgorithmStatus locationTimeZoneAlgorithmStatus =
                                        locationAlgorithmEvent.mAlgorithmStatus;
                                long start2 =
                                        dualDumpOutputStream.start(
                                                "algorithm_status", 1146756268035L);
                                int status = locationTimeZoneAlgorithmStatus.getStatus();
                                if (status == 0) {
                                    i = 0;
                                } else if (status == 1) {
                                    i = 1;
                                } else if (status == 2) {
                                    i = 2;
                                } else {
                                    if (status != 3) {
                                        throw new IllegalArgumentException(
                                                VibrationParam$1$$ExternalSyntheticOutline0.m(
                                                        status, "Unknown statusEnum="));
                                    }
                                    i = 3;
                                }
                                dualDumpOutputStream.write(
                                        Constants.JSON_CLIENT_DATA_STATUS, 1159641169921L, i);
                                dualDumpOutputStream.end(start2);
                                GeolocationTimeZoneSuggestion geolocationTimeZoneSuggestion =
                                        locationAlgorithmEvent.mSuggestion;
                                if (geolocationTimeZoneSuggestion != null) {
                                    long start3 =
                                            dualDumpOutputStream.start(
                                                    "suggestion", 1146756268033L);
                                    Iterator it = geolocationTimeZoneSuggestion.mZoneIds.iterator();
                                    while (it.hasNext()) {
                                        dualDumpOutputStream.write(
                                                "zone_ids", 2237677961217L, (String) it.next());
                                    }
                                    dualDumpOutputStream.end(start3);
                                }
                                ArrayList arrayList = locationAlgorithmEvent.mDebugInfo;
                                Iterator it2 =
                                        (arrayList == null
                                                        ? Collections.emptyList()
                                                        : Collections.unmodifiableList(arrayList))
                                                .iterator();
                                while (it2.hasNext()) {
                                    dualDumpOutputStream.write(
                                            "debug_info", 2237677961218L, (String) it2.next());
                                }
                                dualDumpOutputStream.end(start);
                            }
                            for (String str3 :
                                    locationTimeZoneManagerServiceState.mControllerStates) {
                                int i4 = 7;
                                switch (str3.hashCode()) {
                                    case -1166336595:
                                        if (str3.equals("STOPPED")) {
                                            c2 = 1;
                                            break;
                                        }
                                        c2 = 65535;
                                        break;
                                    case -468307734:
                                        if (str3.equals("PROVIDERS_INITIALIZING")) {
                                            c2 = 0;
                                            break;
                                        }
                                        c2 = 65535;
                                        break;
                                    case 433141802:
                                        if (str3.equals("UNKNOWN")) {
                                            c2 = 7;
                                            break;
                                        }
                                        c2 = 65535;
                                        break;
                                    case 478389753:
                                        if (str3.equals("DESTROYED")) {
                                            c2 = 6;
                                            break;
                                        }
                                        c2 = 65535;
                                        break;
                                    case 872357833:
                                        if (str3.equals("UNCERTAIN")) {
                                            c2 = 3;
                                            break;
                                        }
                                        c2 = 65535;
                                        break;
                                    case 1386911874:
                                        if (str3.equals("CERTAIN")) {
                                            c2 = 4;
                                            break;
                                        }
                                        c2 = 65535;
                                        break;
                                    case 1917201485:
                                        if (str3.equals("INITIALIZING")) {
                                            c2 = 2;
                                            break;
                                        }
                                        c2 = 65535;
                                        break;
                                    case 2066319421:
                                        if (str3.equals("FAILED")) {
                                            c2 = 5;
                                            break;
                                        }
                                        c2 = 65535;
                                        break;
                                    default:
                                        c2 = 65535;
                                        break;
                                }
                                switch (c2) {
                                    case 0:
                                        i4 = 1;
                                        break;
                                    case 1:
                                        i4 = 2;
                                        break;
                                    case 2:
                                        i4 = 3;
                                        break;
                                    case 3:
                                        i4 = 4;
                                        break;
                                    case 4:
                                        i4 = 5;
                                        break;
                                    case 5:
                                        i4 = 6;
                                        break;
                                    case 6:
                                        break;
                                    default:
                                        i4 = 0;
                                        break;
                                }
                                dualDumpOutputStream.write("controller_states", 2259152797700L, i4);
                            }
                            writeProviderStates(
                                    dualDumpOutputStream,
                                    Collections.unmodifiableList(
                                            locationTimeZoneManagerServiceState
                                                    .mPrimaryProviderStates),
                                    "primary_provider_states",
                                    2246267895810L);
                            writeProviderStates(
                                    dualDumpOutputStream,
                                    Collections.unmodifiableList(
                                            locationTimeZoneManagerServiceState
                                                    .mSecondaryProviderStates),
                                    "secondary_provider_states",
                                    2246267895811L);
                            dualDumpOutputStream.flush();
                        }
                        return 0;
                    } catch (Exception e5) {
                        throw new RuntimeException(e5);
                    }
                } catch (RuntimeException e6) {
                    reportError(e6);
                    return 1;
                }
            default:
                return handleDefaultCommands(str);
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.printf(
                "Location Time Zone Manager (%s) commands for tests:\n",
                "location_time_zone_manager");
        outPrintWriter.printf("  help\n", new Object[0]);
        outPrintWriter.printf("    Print this help text.\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "start");
        outPrintWriter.printf(
                "    Starts the service, creating location time zone providers.\n", new Object[0]);
        outPrintWriter.printf(
                "  %s <primary package name|%2$s> <secondary package name|%2$s> <record states>\n",
                "start_with_test_providers", "@null");
        outPrintWriter.printf(
                "    Starts the service with test provider packages configured / provider"
                    + " permission checks disabled.\n",
                new Object[0]);
        outPrintWriter.printf(
                "    <record states> - true|false, determines whether state recording is"
                    + " enabled.\n",
                new Object[0]);
        outPrintWriter.printf(
                "    See %s and %s.\n", "dump_state", "clear_recorded_provider_states");
        outPrintWriter.printf("  %s\n", "stop");
        outPrintWriter.printf(
                "    Stops the service, destroying location time zone providers.\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "clear_recorded_provider_states");
        outPrintWriter.printf(
                "    Clears recorded provider state. See also %s and %s.\n",
                "start_with_test_providers", "dump_state");
        outPrintWriter.printf(
                "    Note: This is only intended for use during testing.\n", new Object[0]);
        outPrintWriter.printf("  %s [%s]\n", "dump_state", "--proto");
        outPrintWriter.printf(
                "    Dumps service state for tests as text or binary proto form.\n", new Object[0]);
        outPrintWriter.printf(
                "    See the LocationTimeZoneManagerServiceStateProto definition for details.\n",
                new Object[0]);
        outPrintWriter.println();
        outPrintWriter.printf(
                "This service is also affected by the following device_config flags in the %s"
                    + " namespace:\n",
                "system_time");
        outPrintWriter.printf("  %s\n", "primary_location_time_zone_provider_mode_override");
        outPrintWriter.printf(
                "    Overrides the mode of the primary provider. Values=%s|%s\n",
                "disabled", "enabled");
        outPrintWriter.printf("  %s\n", "secondary_location_time_zone_provider_mode_override");
        outPrintWriter.printf(
                "    Overrides the mode of the secondary provider. Values=%s|%s\n",
                "disabled", "enabled");
        outPrintWriter.printf("  %s\n", "location_time_zone_detection_uncertainty_delay_millis");
        outPrintWriter.printf(
                "    Sets the amount of time the service waits when uncertain before making an"
                    + " 'uncertain' suggestion to the time zone detector.\n",
                new Object[0]);
        outPrintWriter.printf("  %s\n", "ltzp_init_timeout_millis");
        outPrintWriter.printf(
                "    Sets the initialization time passed to the providers.\n", new Object[0]);
        outPrintWriter.printf("  %s\n", "ltzp_init_timeout_fuzz_millis");
        outPrintWriter.printf(
                "    Sets the amount of extra time added to the providers' initialization time.\n",
                new Object[0]);
        outPrintWriter.printf("  %s\n", "ltzp_event_filtering_age_threshold_millis");
        outPrintWriter.printf(
                "    Sets the amount of time that must pass between equivalent LTZP events before"
                    + " they will be reported to the system server.\n",
                new Object[0]);
        outPrintWriter.println();
        outPrintWriter.printf(
                "Typically, use '%s' to stop the service before setting individual flags and '%s'"
                    + " after to restart it.\n",
                "stop", "start");
        outPrintWriter.println();
        outPrintWriter.printf(
                "See \"adb shell cmd device_config\" for more information on setting flags.\n",
                new Object[0]);
        outPrintWriter.println();
        outPrintWriter.printf(
                "Also see \"adb shell cmd %s help\" for higher-level location time zone commands /"
                    + " settings.\n",
                "time_zone_detector");
        outPrintWriter.println();
    }

    public final void reportError(Throwable th) {
        PrintWriter errPrintWriter = getErrPrintWriter();
        errPrintWriter.println("Error: ");
        th.printStackTrace(errPrintWriter);
    }
}
