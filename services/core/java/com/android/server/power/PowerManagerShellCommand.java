package com.android.server.power;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ShellCommand;
import android.util.SparseArray;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;

import com.att.iqi.lib.BuildConfig;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public final class PowerManagerShellCommand extends ShellCommand {
    public final Context mContext;
    public final SparseArray mProxWakelocks = new SparseArray();
    public final PowerManagerService.BinderService mService;

    public PowerManagerShellCommand(
            Context context, PowerManagerService.BinderService binderService) {
        this.mContext = context;
        this.mService = binderService;
    }

    public final int onCommand(String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            boolean z = true;
            switch (str.hashCode()) {
                case -977473428:
                    if (str.equals("set-face-down-detector")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -531688203:
                    if (str.equals("set-adaptive-power-saver-enabled")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 584761923:
                    if (str.equals("list-ambient-display-suppression-tokens")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 774730613:
                    if (str.equals("suppress-ambient-display")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1032507032:
                    if (str.equals("set-fixed-performance-mode-enabled")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1369181230:
                    if (str.equals("set-mode")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1369273846:
                    if (str.equals("set-prox")) {
                        c = 5;
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
                    this.mService.setAdaptivePowerSaveEnabled(
                            Boolean.parseBoolean(getNextArgRequired()));
                    return 0;
                case 1:
                    PrintWriter outPrintWriter2 = getOutPrintWriter();
                    try {
                        int parseInt = Integer.parseInt(getNextArgRequired());
                        PowerManagerService.BinderService binderService = this.mService;
                        if (parseInt != 1) {
                            z = false;
                        }
                        binderService.setPowerSaveModeEnabled(z);
                        return 0;
                    } catch (RuntimeException e) {
                        outPrintWriter2.println("Error: " + e.toString());
                        return -1;
                    }
                case 2:
                    boolean powerModeChecked =
                            this.mService.setPowerModeChecked(
                                    3, Boolean.parseBoolean(getNextArgRequired()));
                    if (!powerModeChecked) {
                        PrintWriter errPrintWriter = getErrPrintWriter();
                        errPrintWriter.println("Failed to set FIXED_PERFORMANCE mode");
                        errPrintWriter.println(
                                "This is likely because Power HAL AIDL is not implemented on this"
                                    + " device");
                    }
                    return powerModeChecked ? 0 : -1;
                case 3:
                    PrintWriter outPrintWriter3 = getOutPrintWriter();
                    try {
                        this.mService.suppressAmbientDisplay(
                                getNextArgRequired(), Boolean.parseBoolean(getNextArgRequired()));
                        return 0;
                    } catch (RuntimeException e2) {
                        outPrintWriter3.println("Error: " + e2.toString());
                        return -1;
                    }
                case 4:
                    runListAmbientDisplaySuppressionTokens();
                    return 0;
                case 5:
                    return runSetProx();
                case 6:
                    try {
                        PowerManagerService.BinderService binderService2 = this.mService;
                        Boolean.parseBoolean(getNextArgRequired());
                        binderService2.getClass();
                        Binder.restoreCallingIdentity(Binder.clearCallingIdentity());
                        return 0;
                    } catch (Exception e3) {
                        getOutPrintWriter().println("Error: " + e3);
                        return -1;
                    }
                default:
                    return handleDefaultCommands(str);
            }
        } catch (RemoteException e4) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    "Remote exception: ", e4, outPrintWriter);
            return -1;
        }
        UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                "Remote exception: ", e4, outPrintWriter);
        return -1;
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Power manager (power) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "  set-adaptive-power-saver-enabled [true|false]",
                "    enables or disables adaptive power saver.",
                "  set-mode MODE",
                "    sets the power mode of the device to MODE.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    1 turns low power mode on and 0 turns low power mode off.",
                "  set-fixed-performance-mode-enabled [true|false]",
                "    enables or disables fixed performance mode",
                "    note: this will affect system performance and should only be used");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "          during development",
                "  suppress-ambient-display <token> [true|false]",
                "    suppresses the current ambient display configuration and disables",
                "    ambient display");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "  list-ambient-display-suppression-tokens",
                "    prints the tokens used to suppress ambient display",
                "  set-prox [list|acquire|release] (-d <display_id>)",
                "    Acquires the proximity sensor wakelock. Wakelock is associated with");
        outPrintWriter.println(
                "    a specific display if specified. 'list' lists wakelocks previously");
        outPrintWriter.println("    created by set-prox including their held status.");
        outPrintWriter.println("  set-face-down-detector [true|false]");
        outPrintWriter.println("    sets whether we use face down detector timeouts or not");
        outPrintWriter.println();
        Intent.printIntentArgsHelp(outPrintWriter, "");
    }

    public final void runListAmbientDisplaySuppressionTokens() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        PowerManagerService.BinderService binderService = this.mService;
        binderService.getClass();
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List suppressionTokens =
                    PowerManagerService.this.mAmbientDisplaySuppressionController
                            .getSuppressionTokens(callingUid);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (((ArrayList) suppressionTokens).isEmpty()) {
                outPrintWriter.println("none");
            } else {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        outPrintWriter, "[", String.join(", ", suppressionTokens), "]");
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runSetProx() {
        char c;
        boolean z;
        PrintWriter outPrintWriter = getOutPrintWriter();
        String lowerCase = getNextArgRequired().toLowerCase();
        lowerCase.getClass();
        int i = -1;
        switch (lowerCase.hashCode()) {
            case -1164222250:
                if (lowerCase.equals("acquire")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3322014:
                if (lowerCase.equals("list")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1090594823:
                if (lowerCase.equals(BuildConfig.BUILD_TYPE)) {
                    c = 2;
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
                z = true;
                break;
            case 1:
                outPrintWriter.println("Wakelocks:");
                outPrintWriter.println(this.mProxWakelocks);
                return 0;
            case 2:
                z = false;
                break;
            default:
                outPrintWriter.println("Error: Allowed options are 'list' 'enable' and 'disable'.");
                return -1;
        }
        if ("-d".equals(getNextArg())) {
            String nextArg = getNextArg();
            int parseInt = Integer.parseInt(nextArg);
            if (parseInt < 0) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        outPrintWriter,
                        "Error: Specified displayId (",
                        nextArg,
                        ") must a non-negative int.");
                return -1;
            }
            i = parseInt;
        }
        int i2 = 1 + i;
        PowerManager.WakeLock wakeLock = (PowerManager.WakeLock) this.mProxWakelocks.get(i2);
        if (wakeLock == null) {
            wakeLock =
                    ((PowerManager) this.mContext.getSystemService(PowerManager.class))
                            .newWakeLock(
                                    32,
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            i, "PowerManagerShellCommand[", "]"),
                                    i);
            this.mProxWakelocks.put(i2, wakeLock);
        }
        if (z) {
            wakeLock.acquire();
        } else {
            wakeLock.release();
        }
        outPrintWriter.println(wakeLock);
        return 0;
    }
}
