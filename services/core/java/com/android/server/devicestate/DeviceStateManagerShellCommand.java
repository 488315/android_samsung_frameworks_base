package com.android.server.devicestate;

import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.devicestate.DeviceStateRequest;
import android.os.Binder;
import android.os.ShellCommand;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class DeviceStateManagerShellCommand extends ShellCommand {
    public static DeviceStateRequest sLastBaseStateRequest;
    public static DeviceStateRequest sLastRequest;
    public final DeviceStateManager mClient;
    public final DeviceStateManagerService mService;

    public DeviceStateManagerShellCommand(DeviceStateManagerService deviceStateManagerService) {
        this.mService = deviceStateManagerService;
        this.mClient =
                (DeviceStateManager)
                        deviceStateManagerService
                                .getContext()
                                .getSystemService(DeviceStateManager.class);
    }

    public final int onCommand(String str) {
        char c;
        long clearCallingIdentity;
        Optional optional;
        List supportedStatesLocked;
        List supportedStatesLocked2;
        final int i = 0;
        final int i2 = 1;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        PrintWriter outPrintWriter = getOutPrintWriter();
        switch (str.hashCode()) {
            case -1906524523:
                if (str.equals("base-state")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1422060175:
                if (str.equals("print-state")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1134192350:
                if (str.equals("print-states")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -295380803:
                if (str.equals("print-states-simple")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 109757585:
                if (str.equals(LauncherConfigurationInternal.KEY_STATE_BOOLEAN)) {
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
                String nextArg = getNextArg();
                if (nextArg == null) {
                    printAllStates(outPrintWriter);
                } else {
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        try {
                            if (!"reset".equals(nextArg)) {
                                DeviceStateRequest build =
                                        DeviceStateRequest.newBuilder(Integer.parseInt(nextArg))
                                                .build();
                                this.mClient.requestBaseStateOverride(
                                        build, (Executor) null, (DeviceStateRequest.Callback) null);
                                sLastBaseStateRequest = build;
                            } else if (sLastBaseStateRequest != null) {
                                this.mClient.cancelBaseStateOverride();
                                sLastBaseStateRequest = null;
                            }
                        } catch (NumberFormatException unused) {
                            getErrPrintWriter()
                                    .println("Error: requested state should be an integer");
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return -1;
                        } catch (IllegalArgumentException e) {
                            getErrPrintWriter().println("Error: " + e.getMessage());
                            getErrPrintWriter().println("-------------------");
                            getErrPrintWriter().println("Run:");
                            getErrPrintWriter().println("");
                            getErrPrintWriter().println("    print-states");
                            getErrPrintWriter().println("");
                            getErrPrintWriter()
                                    .println(
                                            "to get the list of currently supported device states");
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return -1;
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
                return 0;
            case 1:
                DeviceStateManagerService deviceStateManagerService = this.mService;
                synchronized (deviceStateManagerService.mLock) {
                    optional = deviceStateManagerService.mCommittedState;
                }
                if (optional.isPresent()) {
                    outPrintWriter.println(((DeviceState) optional.get()).getIdentifier());
                    return 0;
                }
                getErrPrintWriter().println("Error: device state not available.");
                return 1;
            case 2:
                DeviceStateManagerService deviceStateManagerService2 = this.mService;
                synchronized (deviceStateManagerService2.mLock) {
                    supportedStatesLocked = deviceStateManagerService2.getSupportedStatesLocked();
                }
                outPrintWriter.print("Supported states: [\n");
                int i3 = 0;
                while (true) {
                    ArrayList arrayList = (ArrayList) supportedStatesLocked;
                    if (i3 >= arrayList.size()) {
                        outPrintWriter.println("]");
                        return 0;
                    }
                    outPrintWriter.print("  " + arrayList.get(i3) + ",\n");
                    i3++;
                }
            case 3:
                DeviceStateManagerService deviceStateManagerService3 = this.mService;
                synchronized (deviceStateManagerService3.mLock) {
                    supportedStatesLocked2 = deviceStateManagerService3.getSupportedStatesLocked();
                }
                outPrintWriter.print(
                        (String)
                                supportedStatesLocked2.stream()
                                        .map(
                                                new Function() { // from class:
                                                                 // com.android.server.devicestate.DeviceStateManagerShellCommand$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        switch (i) {
                                                            case 0:
                                                                return Integer.valueOf(
                                                                        ((DeviceState) obj)
                                                                                .getIdentifier());
                                                            default:
                                                                return ((Integer) obj).toString();
                                                        }
                                                    }
                                                })
                                        .map(
                                                new Function() { // from class:
                                                                 // com.android.server.devicestate.DeviceStateManagerShellCommand$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        switch (i2) {
                                                            case 0:
                                                                return Integer.valueOf(
                                                                        ((DeviceState) obj)
                                                                                .getIdentifier());
                                                            default:
                                                                return ((Integer) obj).toString();
                                                        }
                                                    }
                                                })
                                        .collect(Collectors.joining(",")));
                return 0;
            case 4:
                String nextArg2 = getNextArg();
                if (nextArg2 == null) {
                    printAllStates(outPrintWriter);
                } else {
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        try {
                            if (!"reset".equals(nextArg2)) {
                                DeviceStateRequest build2 =
                                        DeviceStateRequest.newBuilder(Integer.parseInt(nextArg2))
                                                .build();
                                this.mClient.requestState(
                                        build2,
                                        (Executor) null,
                                        (DeviceStateRequest.Callback) null);
                                sLastRequest = build2;
                            } else if (sLastRequest != null) {
                                this.mClient.cancelStateRequest();
                                sLastRequest = null;
                            }
                        } catch (NumberFormatException unused2) {
                            getErrPrintWriter()
                                    .println("Error: requested state should be an integer");
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return -1;
                        } catch (IllegalArgumentException e2) {
                            getErrPrintWriter().println("Error: " + e2.getMessage());
                            getErrPrintWriter().println("-------------------");
                            getErrPrintWriter().println("Run:");
                            getErrPrintWriter().println("");
                            getErrPrintWriter().println("    print-states");
                            getErrPrintWriter().println("");
                            getErrPrintWriter()
                                    .println(
                                            "to get the list of currently supported device states");
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return -1;
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
                return 0;
            default:
                return handleDefaultCommands(str);
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Device state manager (device_state) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  state [reset|OVERRIDE_DEVICE_STATE]");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Return or override device state.",
                "  print-state",
                "    Return the current device state.",
                "  print-states");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Return list of currently supported device states.",
                "  print-states-simple",
                "    Return the currently supported device states in comma separated format.");
    }

    public final void printAllStates(PrintWriter printWriter) {
        Optional optional;
        Optional optional2;
        DeviceStateManagerService deviceStateManagerService = this.mService;
        synchronized (deviceStateManagerService.mLock) {
            optional = deviceStateManagerService.mCommittedState;
        }
        DeviceStateManagerService deviceStateManagerService2 = this.mService;
        synchronized (deviceStateManagerService2.mLock) {
            optional2 = deviceStateManagerService2.mBaseState;
        }
        Optional overrideState = this.mService.getOverrideState();
        StringBuilder sb = new StringBuilder("Committed state: ");
        sb.append(optional.isPresent() ? ((DeviceState) optional.get()).toString() : "(none)");
        printWriter.println(sb.toString());
        if (overrideState.isPresent()) {
            StringBuilder m =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            printWriter,
                            optional2.isPresent()
                                    ? ((DeviceState) optional2.get()).toString()
                                    : "(none)",
                            "Override state: ",
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                                    printWriter, "----------------------", "Base state: "));
            m.append(overrideState.get());
            printWriter.println(m.toString());
        }
    }
}
