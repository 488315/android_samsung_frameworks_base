package com.android.server.location;

import android.content.Context;
import android.location.Location;
import android.location.provider.ProviderProperties;
import android.os.SystemClock;
import android.os.UserHandle;

import com.android.modules.utils.BasicShellCommandHandler;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class LocationShellCommand extends BasicShellCommandHandler {
    public final Context mContext;
    public final LocationManagerService mService;

    public LocationShellCommand(Context context, LocationManagerService locationManagerService) {
        this.mContext = context;
        Objects.requireNonNull(locationManagerService);
        this.mService = locationManagerService;
    }

    public final int onCommand(String str) {
        char c;
        char c2;
        char c3;
        char c4;
        if (str == null) {
            return handleDefaultCommands((String) null);
        }
        int i = -3;
        switch (str.hashCode()) {
            case -1064420500:
                if (str.equals("is-location-enabled")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -547571550:
                if (str.equals("providers")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -444268534:
                if (str.equals("is-automotive-gnss-suspended")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -361391806:
                if (str.equals("set-automotive-gnss-suspended")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -84945726:
                if (str.equals("set-adas-gnss-location-enabled")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1546249012:
                if (str.equals("set-location-enabled")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1640843002:
                if (str.equals("is-adas-gnss-location-enabled")) {
                    c = 6;
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
                break;
            case 1:
                String nextArgRequired = getNextArgRequired();
                nextArgRequired.getClass();
                switch (nextArgRequired.hashCode()) {
                    case -1669563581:
                        if (nextArgRequired.equals("remove-test-provider")) {
                            c2 = 0;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1650104991:
                        if (nextArgRequired.equals("set-test-provider-location")) {
                            c2 = 1;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -61579243:
                        if (nextArgRequired.equals("set-test-provider-enabled")) {
                            c2 = 2;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 11404448:
                        if (nextArgRequired.equals("add-test-provider")) {
                            c2 = 3;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 2036447497:
                        if (nextArgRequired.equals("send-extra-command")) {
                            c2 = 4;
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
                        this.mService.removeTestProvider(
                                getNextArgRequired(),
                                this.mContext.getOpPackageName(),
                                this.mContext.getAttributionTag());
                        return 0;
                    case 1:
                        String nextArgRequired2 = getNextArgRequired();
                        Location location = new Location(nextArgRequired2);
                        location.setAccuracy(100.0f);
                        location.setTime(System.currentTimeMillis());
                        location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
                        boolean z = false;
                        while (true) {
                            String nextOption = getNextOption();
                            if (nextOption != null) {
                                switch (nextOption.hashCode()) {
                                    case -2115952999:
                                        if (nextOption.equals("--accuracy")) {
                                            c3 = 0;
                                            break;
                                        }
                                        c3 = 65535;
                                        break;
                                    case 1333430381:
                                        if (nextOption.equals("--time")) {
                                            c3 = 1;
                                            break;
                                        }
                                        c3 = 65535;
                                        break;
                                    case 1916798293:
                                        if (nextOption.equals("--location")) {
                                            c3 = 2;
                                            break;
                                        }
                                        c3 = 65535;
                                        break;
                                    default:
                                        c3 = 65535;
                                        break;
                                }
                                switch (c3) {
                                    case 0:
                                        location.setAccuracy(
                                                Float.parseFloat(getNextArgRequired()));
                                        break;
                                    case 1:
                                        location.setTime(Long.parseLong(getNextArgRequired()));
                                        break;
                                    case 2:
                                        String[] split = getNextArgRequired().split(",");
                                        if (split.length != 2) {
                                            throw new IllegalArgumentException(
                                                    "Location argument must be in the form of"
                                                        + " \"<LATITUDE>,<LONGITUDE>\", not "
                                                            + Arrays.toString(split));
                                        }
                                        location.setLatitude(Double.parseDouble(split[0]));
                                        location.setLongitude(Double.parseDouble(split[1]));
                                        z = true;
                                        break;
                                    default:
                                        throw new IllegalArgumentException(
                                                "Unknown option: ".concat(nextOption));
                                }
                            } else {
                                if (!z) {
                                    throw new IllegalArgumentException(
                                            "Option \"--location\" is required");
                                }
                                this.mService.setTestProviderLocation(
                                        nextArgRequired2,
                                        location,
                                        this.mContext.getOpPackageName(),
                                        this.mContext.getAttributionTag());
                                break;
                            }
                        }
                    case 2:
                        this.mService.setTestProviderEnabled(
                                getNextArgRequired(),
                                Boolean.parseBoolean(getNextArgRequired()),
                                this.mContext.getOpPackageName(),
                                this.mContext.getAttributionTag());
                        return 0;
                    case 3:
                        String nextArgRequired3 = getNextArgRequired();
                        List emptyList = Collections.emptyList();
                        boolean z2 = false;
                        boolean z3 = false;
                        boolean z4 = false;
                        boolean z5 = false;
                        boolean z6 = false;
                        boolean z7 = false;
                        boolean z8 = false;
                        int i2 = 1;
                        int i3 = 1;
                        while (true) {
                            String nextOption2 = getNextOption();
                            if (nextOption2 == null) {
                                this.mService.addTestProvider(
                                        nextArgRequired3,
                                        new ProviderProperties.Builder()
                                                .setHasNetworkRequirement(z2)
                                                .setHasSatelliteRequirement(z5)
                                                .setHasCellRequirement(z6)
                                                .setHasMonetaryCost(z7)
                                                .setHasAltitudeSupport(z8)
                                                .setHasSpeedSupport(z3)
                                                .setHasBearingSupport(z4)
                                                .setPowerUsage(i2)
                                                .setAccuracy(i3)
                                                .build(),
                                        emptyList,
                                        this.mContext.getOpPackageName(),
                                        this.mContext.getAttributionTag());
                            } else {
                                switch (nextOption2.hashCode()) {
                                    case -2115952999:
                                        if (nextOption2.equals("--accuracy")) {
                                            c4 = 0;
                                            break;
                                        }
                                        c4 = 65535;
                                        break;
                                    case -1786843904:
                                        if (nextOption2.equals("--requiresNetwork")) {
                                            c4 = 1;
                                            break;
                                        }
                                        c4 = 65535;
                                        break;
                                    case -1474799448:
                                        if (nextOption2.equals("--extraAttributionTags")) {
                                            c4 = 2;
                                            break;
                                        }
                                        c4 = 65535;
                                        break;
                                    case -1446936854:
                                        if (nextOption2.equals("--supportsBearing")) {
                                            c4 = 3;
                                            break;
                                        }
                                        c4 = 65535;
                                        break;
                                    case -1194644762:
                                        if (nextOption2.equals("--supportsAltitude")) {
                                            c4 = 4;
                                            break;
                                        }
                                        c4 = 65535;
                                        break;
                                    case 1086076880:
                                        if (nextOption2.equals("--requiresCell")) {
                                            c4 = 5;
                                            break;
                                        }
                                        c4 = 65535;
                                        break;
                                    case 1279633236:
                                        if (nextOption2.equals("--hasMonetaryCost")) {
                                            c4 = 6;
                                            break;
                                        }
                                        c4 = 65535;
                                        break;
                                    case 1483009933:
                                        if (nextOption2.equals("--requiresSatellite")) {
                                            c4 = 7;
                                            break;
                                        }
                                        c4 = 65535;
                                        break;
                                    case 1601002398:
                                        if (nextOption2.equals("--powerRequirement")) {
                                            c4 = '\b';
                                            break;
                                        }
                                        c4 = 65535;
                                        break;
                                    case 2048042627:
                                        if (nextOption2.equals("--supportsSpeed")) {
                                            c4 = '\t';
                                            break;
                                        }
                                        c4 = 65535;
                                        break;
                                    default:
                                        c4 = 65535;
                                        break;
                                }
                                switch (c4) {
                                    case 0:
                                        i3 = Integer.parseInt(getNextArgRequired());
                                        break;
                                    case 1:
                                        z2 = true;
                                        break;
                                    case 2:
                                        emptyList = Arrays.asList(getNextArgRequired().split(","));
                                        break;
                                    case 3:
                                        z4 = true;
                                        break;
                                    case 4:
                                        z8 = true;
                                        break;
                                    case 5:
                                        z6 = true;
                                        break;
                                    case 6:
                                        z7 = true;
                                        break;
                                    case 7:
                                        z5 = true;
                                        break;
                                    case '\b':
                                        i2 = Integer.parseInt(getNextArgRequired());
                                        break;
                                    case '\t':
                                        z3 = true;
                                        break;
                                    default:
                                        throw new IllegalArgumentException(
                                                "Received unexpected option: ".concat(nextOption2));
                                }
                            }
                        }
                        return 0;
                    case 4:
                        this.mService.sendExtraCommand(
                                getNextArgRequired(), getNextArgRequired(), null);
                        return 0;
                    default:
                        return handleDefaultCommands(nextArgRequired);
                }
            case 2:
                if (!this.mContext
                        .getPackageManager()
                        .hasSystemFeature("android.hardware.type.automotive")) {
                    throw new IllegalStateException(
                            "command only recognized on automotive devices");
                }
                getOutPrintWriter().println(this.mService.isAutomotiveGnssSuspended());
                return 0;
            case 3:
                if (!this.mContext
                        .getPackageManager()
                        .hasSystemFeature("android.hardware.type.automotive")) {
                    throw new IllegalStateException(
                            "command only recognized on automotive devices");
                }
                this.mService.setAutomotiveGnssSuspended(
                        Boolean.parseBoolean(getNextArgRequired()));
                return 0;
            case 4:
                if (!this.mContext
                        .getPackageManager()
                        .hasSystemFeature("android.hardware.type.automotive")) {
                    throw new IllegalStateException(
                            "command only recognized on automotive devices");
                }
                boolean parseBoolean = Boolean.parseBoolean(getNextArgRequired());
                while (true) {
                    String nextOption3 = getNextOption();
                    if (nextOption3 == null) {
                        this.mService.setAdasGnssLocationEnabledForUser(parseBoolean, i);
                        return 0;
                    }
                    if (!"--user".equals(nextOption3)) {
                        throw new IllegalArgumentException("Unknown option: ".concat(nextOption3));
                    }
                    i = UserHandle.parseUserArg(getNextArgRequired());
                }
            case 5:
                boolean parseBoolean2 = Boolean.parseBoolean(getNextArgRequired());
                while (true) {
                    String nextOption4 = getNextOption();
                    if (nextOption4 == null) {
                        this.mService.setLocationEnabledForUser(parseBoolean2, i);
                        return 0;
                    }
                    if (!"--user".equals(nextOption4)) {
                        throw new IllegalArgumentException("Unknown option: ".concat(nextOption4));
                    }
                    i = UserHandle.parseUserArg(getNextArgRequired());
                }
            case 6:
                if (!this.mContext
                        .getPackageManager()
                        .hasSystemFeature("android.hardware.type.automotive")) {
                    throw new IllegalStateException(
                            "command only recognized on automotive devices");
                }
                while (true) {
                    String nextOption5 = getNextOption();
                    if (nextOption5 == null) {
                        getOutPrintWriter()
                                .println(this.mService.isAdasGnssLocationEnabledForUser(i));
                        return 0;
                    }
                    if (!"--user".equals(nextOption5)) {
                        throw new IllegalArgumentException("Unknown option: ".concat(nextOption5));
                    }
                    i = UserHandle.parseUserArg(getNextArgRequired());
                }
            default:
                return handleDefaultCommands(str);
        }
        while (true) {
            String nextOption6 = getNextOption();
            if (nextOption6 == null) {
                getOutPrintWriter().println(this.mService.isLocationEnabledForUser(i));
                return 0;
            }
            if (!"--user".equals(nextOption6)) {
                throw new IllegalArgumentException("Unknown option: ".concat(nextOption6));
            }
            i = UserHandle.parseUserArg(getNextArgRequired());
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Location service commands:");
        outPrintWriter.println("  help or -h");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  is-location-enabled [--user <USER_ID>]");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Gets the master location switch enabled state. If no user is specified,",
                "    the current user is assumed.",
                "  set-location-enabled true|false [--user <USER_ID>]",
                "    Sets the master location switch enabled state. If no user is specified,");
        outPrintWriter.println("    the current user is assumed.");
        if (this.mContext
                .getPackageManager()
                .hasSystemFeature("android.hardware.type.automotive")) {
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "  is-adas-gnss-location-enabled [--user <USER_ID>]",
                    "    Gets the ADAS GNSS location enabled state. If no user is specified,",
                    "    the current user is assumed.",
                    "  set-adas-gnss-location-enabled true|false [--user <USER_ID>]");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "    Sets the ADAS GNSS location enabled state. If no user is specified,",
                    "    the current user is assumed.",
                    "  is-automotive-gnss-suspended",
                    "    Gets the automotive GNSS suspended state.");
            outPrintWriter.println("  set-automotive-gnss-suspended true|false");
            outPrintWriter.println("    Sets the automotive GNSS suspended state.");
        }
        outPrintWriter.println("  providers");
        outPrintWriter.println(
                "    The providers command is followed by a subcommand, as listed below:");
        outPrintWriter.println();
        outPrintWriter.println(
                "    add-test-provider <PROVIDER> [--requiresNetwork] [--requiresSatellite]");
        outPrintWriter.println("      [--requiresCell] [--hasMonetaryCost] [--supportsAltitude]");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      [--supportsSpeed] [--supportsBearing]",
                "      [--powerRequirement <POWER_REQUIREMENT>]",
                "      [--extraAttributionTags <TAG>,<TAG>,...]",
                "      Add the given test provider. Requires MOCK_LOCATION permissions which");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      can be enabled by running \"adb shell appops set <uid>",
                "      android:mock_location allow\". There are optional flags that can be",
                "      used to configure the provider properties and additional arguments. If",
                "      no flags are included, then default values will be used.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    remove-test-provider <PROVIDER>",
                "      Remove the given test provider.",
                "    set-test-provider-enabled <PROVIDER> true|false",
                "      Sets the given test provider enabled state.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    set-test-provider-location <PROVIDER> --location <LATITUDE>,<LONGITUDE>",
                "      [--accuracy <ACCURACY>] [--time <TIME>]",
                "      Set location for given test provider. Accuracy and time are optional.",
                "    send-extra-command <PROVIDER> <COMMAND>");
        outPrintWriter.println("      Sends the given extra command to the given provider.");
        outPrintWriter.println();
        outPrintWriter.println(
                "      Common commands that may be supported by the gps provider, depending on");
        outPrintWriter.println("      hardware and software configurations:");
        outPrintWriter.println(
                "        delete_aiding_data - requests deletion of any predictive aiding data");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "        force_time_injection - requests NTP time injection",
                "        force_psds_injection - requests predictive aiding data injection",
                "        request_power_stats - requests GNSS power stats update");
    }
}
