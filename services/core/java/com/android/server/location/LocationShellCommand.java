package com.android.server.location;

import android.content.Context;
import android.location.Location;
import android.location.provider.ProviderProperties;
import android.os.SystemClock;
import android.os.UserHandle;
import com.android.modules.utils.BasicShellCommandHandler;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public class LocationShellCommand extends BasicShellCommandHandler {
    public final Context mContext;
    public final LocationManagerService mService;

    public LocationShellCommand(Context context, LocationManagerService locationManagerService) {
        this.mContext = context;
        Objects.requireNonNull(locationManagerService);
        this.mService = locationManagerService;
    }

    public int onCommand(String str) {
        if (str == null) {
            return handleDefaultCommands((String) null);
        }
        switch (str) {
            case "is-location-enabled":
                handleIsLocationEnabled();
                return 0;
            case "providers":
                return parseProvidersCommand(getNextArgRequired());
            case "is-automotive-gnss-suspended":
                handleIsAutomotiveGnssSuspended();
                return 0;
            case "set-automotive-gnss-suspended":
                handleSetAutomotiveGnssSuspended();
                return 0;
            case "set-adas-gnss-location-enabled":
                handleSetAdasGnssLocationEnabled();
                return 0;
            case "set-location-enabled":
                handleSetLocationEnabled();
                return 0;
            case "is-adas-gnss-location-enabled":
                handleIsAdasGnssLocationEnabled();
                return 0;
            default:
                return handleDefaultCommands(str);
        }
    }

    public final int parseProvidersCommand(String str) {
        str.hashCode();
        switch (str) {
            case "remove-test-provider":
                handleRemoveTestProvider();
                return 0;
            case "set-test-provider-location":
                handleSetTestProviderLocation();
                return 0;
            case "set-test-provider-enabled":
                handleSetTestProviderEnabled();
                return 0;
            case "add-test-provider":
                handleAddTestProvider();
                return 0;
            case "send-extra-command":
                handleSendExtraCommand();
                return 0;
            default:
                return handleDefaultCommands(str);
        }
    }

    public final void handleIsLocationEnabled() {
        int i = -3;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if ("--user".equals(nextOption)) {
                    i = UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    throw new IllegalArgumentException("Unknown option: " + nextOption);
                }
            } else {
                getOutPrintWriter().println(this.mService.isLocationEnabledForUser(i));
                return;
            }
        }
    }

    public final void handleSetLocationEnabled() {
        boolean parseBoolean = Boolean.parseBoolean(getNextArgRequired());
        int i = -3;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if ("--user".equals(nextOption)) {
                    i = UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    throw new IllegalArgumentException("Unknown option: " + nextOption);
                }
            } else {
                this.mService.setLocationEnabledForUser(parseBoolean, i);
                return;
            }
        }
    }

    public final void handleIsAdasGnssLocationEnabled() {
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            throw new IllegalStateException("command only recognized on automotive devices");
        }
        int i = -3;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if ("--user".equals(nextOption)) {
                    i = UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    throw new IllegalArgumentException("Unknown option: " + nextOption);
                }
            } else {
                getOutPrintWriter().println(this.mService.isAdasGnssLocationEnabledForUser(i));
                return;
            }
        }
    }

    public final void handleSetAdasGnssLocationEnabled() {
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            throw new IllegalStateException("command only recognized on automotive devices");
        }
        boolean parseBoolean = Boolean.parseBoolean(getNextArgRequired());
        int i = -3;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if ("--user".equals(nextOption)) {
                    i = UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    throw new IllegalArgumentException("Unknown option: " + nextOption);
                }
            } else {
                this.mService.setAdasGnssLocationEnabledForUser(parseBoolean, i);
                return;
            }
        }
    }

    public final void handleSetAutomotiveGnssSuspended() {
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            throw new IllegalStateException("command only recognized on automotive devices");
        }
        this.mService.setAutomotiveGnssSuspended(Boolean.parseBoolean(getNextArgRequired()));
    }

    public final void handleIsAutomotiveGnssSuspended() {
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            throw new IllegalStateException("command only recognized on automotive devices");
        }
        getOutPrintWriter().println(this.mService.isAutomotiveGnssSuspended());
    }

    public final void handleAddTestProvider() {
        String nextArgRequired = getNextArgRequired();
        List emptyList = Collections.emptyList();
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        boolean z7 = false;
        int i = 1;
        int i2 = 1;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption) {
                    case "--accuracy":
                        i2 = Integer.parseInt(getNextArgRequired());
                        break;
                    case "--requiresNetwork":
                        z = true;
                        break;
                    case "--extraAttributionTags":
                        emptyList = Arrays.asList(getNextArgRequired().split(","));
                        break;
                    case "--supportsBearing":
                        z7 = true;
                        break;
                    case "--supportsAltitude":
                        z5 = true;
                        break;
                    case "--requiresCell":
                        z3 = true;
                        break;
                    case "--hasMonetaryCost":
                        z4 = true;
                        break;
                    case "--requiresSatellite":
                        z2 = true;
                        break;
                    case "--powerRequirement":
                        i = Integer.parseInt(getNextArgRequired());
                        break;
                    case "--supportsSpeed":
                        z6 = true;
                        break;
                    default:
                        throw new IllegalArgumentException("Received unexpected option: " + nextOption);
                }
            } else {
                this.mService.addTestProvider(nextArgRequired, new ProviderProperties.Builder().setHasNetworkRequirement(z).setHasSatelliteRequirement(z2).setHasCellRequirement(z3).setHasMonetaryCost(z4).setHasAltitudeSupport(z5).setHasSpeedSupport(z6).setHasBearingSupport(z7).setPowerUsage(i).setAccuracy(i2).build(), emptyList, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
                return;
            }
        }
    }

    public final void handleRemoveTestProvider() {
        this.mService.removeTestProvider(getNextArgRequired(), this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
    }

    public final void handleSetTestProviderEnabled() {
        this.mService.setTestProviderEnabled(getNextArgRequired(), Boolean.parseBoolean(getNextArgRequired()), this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
    }

    public final void handleSetTestProviderLocation() {
        String nextArgRequired = getNextArgRequired();
        Location location = new Location(nextArgRequired);
        location.setAccuracy(100.0f);
        location.setTime(System.currentTimeMillis());
        location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        boolean z = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                if (!z) {
                    throw new IllegalArgumentException("Option \"--location\" is required");
                }
                this.mService.setTestProviderLocation(nextArgRequired, location, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
                return;
            }
            switch (nextOption) {
                case "--accuracy":
                    location.setAccuracy(Float.parseFloat(getNextArgRequired()));
                    break;
                case "--time":
                    location.setTime(Long.parseLong(getNextArgRequired()));
                    break;
                case "--location":
                    String[] split = getNextArgRequired().split(",");
                    if (split.length != 2) {
                        throw new IllegalArgumentException("Location argument must be in the form of \"<LATITUDE>,<LONGITUDE>\", not " + Arrays.toString(split));
                    }
                    location.setLatitude(Double.parseDouble(split[0]));
                    location.setLongitude(Double.parseDouble(split[1]));
                    z = true;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown option: " + nextOption);
            }
        }
    }

    public final void handleSendExtraCommand() {
        this.mService.sendExtraCommand(getNextArgRequired(), getNextArgRequired(), null);
    }

    public void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Location service commands:");
        outPrintWriter.println("  help or -h");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  is-location-enabled [--user <USER_ID>]");
        outPrintWriter.println("    Gets the master location switch enabled state. If no user is specified,");
        outPrintWriter.println("    the current user is assumed.");
        outPrintWriter.println("  set-location-enabled true|false [--user <USER_ID>]");
        outPrintWriter.println("    Sets the master location switch enabled state. If no user is specified,");
        outPrintWriter.println("    the current user is assumed.");
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            outPrintWriter.println("  is-adas-gnss-location-enabled [--user <USER_ID>]");
            outPrintWriter.println("    Gets the ADAS GNSS location enabled state. If no user is specified,");
            outPrintWriter.println("    the current user is assumed.");
            outPrintWriter.println("  set-adas-gnss-location-enabled true|false [--user <USER_ID>]");
            outPrintWriter.println("    Sets the ADAS GNSS location enabled state. If no user is specified,");
            outPrintWriter.println("    the current user is assumed.");
            outPrintWriter.println("  is-automotive-gnss-suspended");
            outPrintWriter.println("    Gets the automotive GNSS suspended state.");
            outPrintWriter.println("  set-automotive-gnss-suspended true|false");
            outPrintWriter.println("    Sets the automotive GNSS suspended state.");
        }
        outPrintWriter.println("  providers");
        outPrintWriter.println("    The providers command is followed by a subcommand, as listed below:");
        outPrintWriter.println();
        outPrintWriter.println("    add-test-provider <PROVIDER> [--requiresNetwork] [--requiresSatellite]");
        outPrintWriter.println("      [--requiresCell] [--hasMonetaryCost] [--supportsAltitude]");
        outPrintWriter.println("      [--supportsSpeed] [--supportsBearing]");
        outPrintWriter.println("      [--powerRequirement <POWER_REQUIREMENT>]");
        outPrintWriter.println("      [--extraAttributionTags <TAG>,<TAG>,...]");
        outPrintWriter.println("      Add the given test provider. Requires MOCK_LOCATION permissions which");
        outPrintWriter.println("      can be enabled by running \"adb shell appops set <uid>");
        outPrintWriter.println("      android:mock_location allow\". There are optional flags that can be");
        outPrintWriter.println("      used to configure the provider properties and additional arguments. If");
        outPrintWriter.println("      no flags are included, then default values will be used.");
        outPrintWriter.println("    remove-test-provider <PROVIDER>");
        outPrintWriter.println("      Remove the given test provider.");
        outPrintWriter.println("    set-test-provider-enabled <PROVIDER> true|false");
        outPrintWriter.println("      Sets the given test provider enabled state.");
        outPrintWriter.println("    set-test-provider-location <PROVIDER> --location <LATITUDE>,<LONGITUDE>");
        outPrintWriter.println("      [--accuracy <ACCURACY>] [--time <TIME>]");
        outPrintWriter.println("      Set location for given test provider. Accuracy and time are optional.");
        outPrintWriter.println("    send-extra-command <PROVIDER> <COMMAND>");
        outPrintWriter.println("      Sends the given extra command to the given provider.");
        outPrintWriter.println();
        outPrintWriter.println("      Common commands that may be supported by the gps provider, depending on");
        outPrintWriter.println("      hardware and software configurations:");
        outPrintWriter.println("        delete_aiding_data - requests deletion of any predictive aiding data");
        outPrintWriter.println("        force_time_injection - requests NTP time injection");
        outPrintWriter.println("        force_psds_injection - requests predictive aiding data injection");
        outPrintWriter.println("        request_power_stats - requests GNSS power stats update");
    }
}
