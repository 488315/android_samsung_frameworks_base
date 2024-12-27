package com.android.server.hdmi;

import android.hardware.hdmi.IHdmiControlCallback;
import android.hardware.hdmi.IHdmiControlService;
import android.net.util.NetworkConstants;
import android.os.ShellCommand;
import android.util.Slog;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerServiceShellCommand$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class HdmiControlShellCommand extends ShellCommand {
    public final IHdmiControlService.Stub mBinderService;
    public final CountDownLatch mLatch = new CountDownLatch(1);
    public final AtomicInteger mCecResult = new AtomicInteger();
    public final AnonymousClass1 mHdmiControlCallback =
            new IHdmiControlCallback
                    .Stub() { // from class: com.android.server.hdmi.HdmiControlShellCommand.1
                public final void onComplete(int i) {
                    PrintWriter outPrintWriter = HdmiControlShellCommand.this.getOutPrintWriter();
                    StringBuilder sb = new StringBuilder(" done (");
                    HdmiControlShellCommand.this.getClass();
                    ProxyManager$$ExternalSyntheticOutline0.m(
                            outPrintWriter,
                            i != 0
                                    ? i != 1
                                            ? i != 2
                                                    ? i != 3
                                                            ? i != 5
                                                                    ? i != 6
                                                                            ? i != 7
                                                                                    ? Integer
                                                                                            .toString(
                                                                                                    i)
                                                                                    : "Communication"
                                                                                          + " Failed"
                                                                            : "Incorrect mode"
                                                                    : "Exception"
                                                            : "Target not available"
                                                    : "Source not available"
                                            : "Timeout"
                                    : "Success",
                            ")",
                            sb);
                    HdmiControlShellCommand.this.mCecResult.set(i);
                    HdmiControlShellCommand.this.mLatch.countDown();
                }
            };

    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.server.hdmi.HdmiControlShellCommand$1] */
    public HdmiControlShellCommand(IHdmiControlService.Stub stub) {
        this.mBinderService = stub;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int handleShellCommand(String str) {
        char c;
        char c2;
        PrintWriter outPrintWriter = getOutPrintWriter();
        str.getClass();
        switch (str.hashCode()) {
            case -1962118964:
                if (str.equals("history_size")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -956246195:
                if (str.equals("onetouchplay")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -905786704:
                if (str.equals("setarc")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -905769923:
                if (str.equals("setsam")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -467124088:
                if (str.equals("setsystemaudiomode")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -25969966:
                if (str.equals("deviceselect")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 110379:
                if (str.equals("otp")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 621295875:
                if (str.equals("vendorcommand")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1322280018:
                if (str.equals("cec_setting")) {
                    c = '\b';
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
                if (1 > getRemainingArgsCount()) {
                    throw new IllegalArgumentException("Use 'set' or 'get' for the command action");
                }
                String nextArgRequired = getNextArgRequired();
                nextArgRequired.getClass();
                if (nextArgRequired.equals("get")) {
                    AccountManagerServiceShellCommand$$ExternalSyntheticOutline0.m(
                            outPrintWriter,
                            "CEC dumpsys message history size = ",
                            this.mBinderService.getMessageHistorySize());
                } else {
                    if (!nextArgRequired.equals("set")) {
                        throw new IllegalArgumentException(
                                "Unknown operation: ".concat(nextArgRequired));
                    }
                    String nextArgRequired2 = getNextArgRequired();
                    try {
                        int parseInt = Integer.parseInt(nextArgRequired2);
                        if (this.mBinderService.setMessageHistorySize(parseInt)) {
                            AccountManagerServiceShellCommand$$ExternalSyntheticOutline0.m(
                                    outPrintWriter,
                                    "Setting CEC dumpsys message history size to ",
                                    parseInt);
                        } else {
                            outPrintWriter.println(
                                    "Message history size not changed, was it lower than the"
                                        + " minimum size?");
                        }
                    } catch (NumberFormatException unused) {
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(
                                outPrintWriter,
                                "Cannot set CEC dumpsys message history size to ",
                                nextArgRequired2);
                        return 1;
                    }
                }
                return 0;
            case 1:
            case 6:
                outPrintWriter.print("Sending One Touch Play...");
                this.mBinderService.oneTouchPlay(this.mHdmiControlCallback);
                return (receiveCallback("One Touch Play") && this.mCecResult.get() == 0) ? 0 : 1;
            case 2:
                if (1 > getRemainingArgsCount()) {
                    throw new IllegalArgumentException(
                            "Please indicate if ARC mode should be turned \"on\" or \"off\".");
                }
                String nextArg = getNextArg();
                if (nextArg.equals("on")) {
                    outPrintWriter.println("Setting ARC mode on");
                    this.mBinderService.setArcMode(true);
                } else {
                    if (!nextArg.equals("off")) {
                        throw new IllegalArgumentException(
                                "Please indicate if ARC mode should be turned \"on\" or \"off\".");
                    }
                    outPrintWriter.println("Setting ARC mode off");
                    this.mBinderService.setArcMode(false);
                }
                return 0;
            case 3:
            case 4:
                if (1 > getRemainingArgsCount()) {
                    throw new IllegalArgumentException(
                            "Please indicate if System Audio Mode should be turned \"on\" or"
                                + " \"off\".");
                }
                String nextArg2 = getNextArg();
                if (nextArg2.equals("on")) {
                    outPrintWriter.println("Setting System Audio Mode on");
                    this.mBinderService.setSystemAudioMode(true, this.mHdmiControlCallback);
                } else {
                    if (!nextArg2.equals("off")) {
                        throw new IllegalArgumentException(
                                "Please indicate if System Audio Mode should be turned \"on\" or"
                                    + " \"off\".");
                    }
                    outPrintWriter.println("Setting System Audio Mode off");
                    this.mBinderService.setSystemAudioMode(false, this.mHdmiControlCallback);
                }
                return (receiveCallback("Set System Audio Mode") && this.mCecResult.get() == 0)
                        ? 0
                        : 1;
            case 5:
                if (getRemainingArgsCount() != 1) {
                    throw new IllegalArgumentException("Expected exactly 1 argument.");
                }
                int parseInt2 = Integer.parseInt(getNextArg());
                outPrintWriter.print("Sending Device Select...");
                this.mBinderService.deviceSelect(parseInt2, this.mHdmiControlCallback);
                return (receiveCallback("Device Select") && this.mCecResult.get() == 0) ? 0 : 1;
            case 7:
                if (6 > getRemainingArgsCount()) {
                    throw new IllegalArgumentException("Expected 3 arguments.");
                }
                String nextOption = getNextOption();
                String str2 = "";
                boolean z = false;
                int i = -1;
                int i2 = -1;
                while (nextOption != null) {
                    switch (nextOption.hashCode()) {
                        case -347347485:
                            if (nextOption.equals("--device_type")) {
                                c2 = 0;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case -234325394:
                            if (nextOption.equals("--destination")) {
                                c2 = 1;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 1492:
                            if (nextOption.equals("-a")) {
                                c2 = 2;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 1495:
                            if (nextOption.equals("-d")) {
                                c2 = 3;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case NetworkConstants.ETHER_MTU /* 1500 */:
                            if (nextOption.equals("-i")) {
                                c2 = 4;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 1511:
                            if (nextOption.equals("-t")) {
                                c2 = 5;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 1387195:
                            if (nextOption.equals("--id")) {
                                c2 = 6;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 1332872829:
                            if (nextOption.equals("--args")) {
                                c2 = 7;
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
                        case 5:
                            i = Integer.parseInt(getNextArgRequired());
                            break;
                        case 1:
                        case 3:
                            i2 = Integer.parseInt(getNextArgRequired());
                            break;
                        case 2:
                        case 7:
                            str2 = getNextArgRequired();
                            break;
                        case 4:
                        case 6:
                            z = Boolean.parseBoolean(getNextArgRequired());
                            break;
                        default:
                            throw new IllegalArgumentException(
                                    "Unknown argument: ".concat(nextOption));
                    }
                    nextOption = getNextArg();
                }
                String[] split = str2.split(":");
                int length = split.length;
                byte[] bArr = new byte[length];
                for (int i3 = 0; i3 < length; i3++) {
                    bArr[i3] = (byte) Integer.parseInt(split[i3], 16);
                }
                outPrintWriter.println("Sending <Vendor Command>");
                this.mBinderService.sendVendorCommand(i, i2, bArr, z);
                return 0;
            case '\b':
                if (getRemainingArgsCount() < 1) {
                    throw new IllegalArgumentException("Expected at least 1 argument (operation).");
                }
                String nextArgRequired3 = getNextArgRequired();
                nextArgRequired3.getClass();
                if (nextArgRequired3.equals("get")) {
                    String nextArgRequired4 = getNextArgRequired();
                    try {
                        outPrintWriter.println(
                                nextArgRequired4
                                        + " = "
                                        + this.mBinderService.getCecSettingStringValue(
                                                nextArgRequired4));
                    } catch (IllegalArgumentException unused2) {
                        outPrintWriter.println(
                                nextArgRequired4
                                        + " = "
                                        + this.mBinderService.getCecSettingIntValue(
                                                nextArgRequired4));
                    }
                } else {
                    if (!nextArgRequired3.equals("set")) {
                        throw new IllegalArgumentException(
                                "Unknown operation: ".concat(nextArgRequired3));
                    }
                    String nextArgRequired5 = getNextArgRequired();
                    String nextArgRequired6 = getNextArgRequired();
                    try {
                        this.mBinderService.setCecSettingStringValue(
                                nextArgRequired5, nextArgRequired6);
                        outPrintWriter.println(nextArgRequired5 + " = " + nextArgRequired6);
                    } catch (IllegalArgumentException unused3) {
                        int parseInt3 = Integer.parseInt(nextArgRequired6);
                        this.mBinderService.setCecSettingIntValue(nextArgRequired5, parseInt3);
                        StringBuilder sb = new StringBuilder();
                        sb.append(nextArgRequired5);
                        sb.append(" = ");
                        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                sb, parseInt3, outPrintWriter);
                    }
                }
                return 0;
            default:
                return handleDefaultCommands(str);
        }
    }

    public final int onCommand(String str) {
        if (str == null) {
            return handleDefaultCommands(str);
        }
        try {
            return handleShellCommand(str);
        } catch (Exception e) {
            PrintWriter errPrintWriter = this.getErrPrintWriter();
            StringBuilder m =
                    DumpUtils$$ExternalSyntheticOutline0.m(
                            "Caught error for command '", str, "': ");
            m.append(e.getMessage());
            errPrintWriter.println(m.toString());
            Slog.e(
                    "HdmiShellCommand",
                    "Error handling hdmi_control shell command: ".concat(str),
                    e);
            return 1;
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("HdmiControlManager (hdmi_control) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("      Print this help text.");
        outPrintWriter.println("  onetouchplay, otp");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      Send the \"One Touch Play\" feature from a source to the TV",
                "  vendorcommand --device_type <originating device type>",
                "                --destination <destination device>",
                "                --args <vendor specific arguments>");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "                [--id <true if vendor command should be sent with vendor id>]",
                "      Send a Vendor Command to the given target device",
                "  cec_setting get <setting name>",
                "      Get the current value of a CEC setting");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "  cec_setting set <setting name> <value>",
                "      Set the value of a CEC setting",
                "  setsystemaudiomode, setsam [on|off]",
                "      Sets the System Audio Mode feature on or off on TV devices");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "  setarc [on|off]",
                "      Sets the ARC feature on or off on TV devices",
                "  deviceselect <device id>",
                "      Switch to device with given id");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      The device's id is represented by its logical address.",
                "  history_size get",
                "      Gets the number of messages that can be stored in dumpsys history",
                "  history_size set <new_size>");
        outPrintWriter.println(
                "      Changes the number of messages that can be stored in dumpsys history to"
                    + " new_size");
    }

    public final boolean receiveCallback(String str) {
        try {
            if (this.mLatch.await(2000L, TimeUnit.MILLISECONDS)) {
                return true;
            }
            getErrPrintWriter().println(str.concat(" timed out."));
            return false;
        } catch (InterruptedException unused) {
            getErrPrintWriter().println("Caught InterruptedException");
            Thread.currentThread().interrupt();
            return true;
        }
    }
}
