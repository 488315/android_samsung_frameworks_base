package com.android.wm.shell.compatui;

import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.systemui.biometrics.AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0;
import com.android.wm.shell.sysui.ShellCommandHandler;
import java.io.PrintWriter;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CompatUIShellCommandHandler implements ShellCommandHandler.ShellCommandActionHandler {
    public final CompatUIConfiguration mCompatUIConfiguration;
    public final ShellCommandHandler mShellCommandHandler;

    public CompatUIShellCommandHandler(ShellCommandHandler shellCommandHandler, CompatUIConfiguration compatUIConfiguration) {
        this.mShellCommandHandler = shellCommandHandler;
        this.mCompatUIConfiguration = compatUIConfiguration;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final boolean onShellCommand(PrintWriter printWriter, String[] strArr) {
        char c;
        char c2;
        if (strArr.length != 2) {
            CarrierTextController$$ExternalSyntheticOutline0.m(new StringBuilder("Invalid command: "), strArr[0], printWriter);
            return false;
        }
        String str = strArr[0];
        str.getClass();
        Boolean bool = null;
        CompatUIConfiguration compatUIConfiguration = this.mCompatUIConfiguration;
        if (str.equals("reachabilityEducationEnabled")) {
            String str2 = strArr[1];
            Objects.requireNonNull(compatUIConfiguration);
            str2.getClass();
            switch (str2.hashCode()) {
                case 48:
                    if (str2.equals("0")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 49:
                    if (str2.equals("1")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 3569038:
                    if (str2.equals("true")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 97196323:
                    if (str2.equals("false")) {
                        c = 3;
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
                case 3:
                    bool = Boolean.FALSE;
                    break;
                case 1:
                case 2:
                    bool = Boolean.TRUE;
                    break;
            }
            if (bool != null) {
                return true;
            }
            printWriter.println("Error: expected true, 1, false, 0.");
            return false;
        }
        if (!str.equals("restartDialogEnabled")) {
            CarrierTextController$$ExternalSyntheticOutline0.m(new StringBuilder("Invalid command: "), strArr[0], printWriter);
            return false;
        }
        String str3 = strArr[1];
        Objects.requireNonNull(compatUIConfiguration);
        str3.getClass();
        switch (str3.hashCode()) {
            case 48:
                if (str3.equals("0")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case 49:
                if (str3.equals("1")) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case 3569038:
                if (str3.equals("true")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 97196323:
                if (str3.equals("false")) {
                    c2 = 3;
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
            case 3:
                bool = Boolean.FALSE;
                break;
            case 1:
            case 2:
                bool = Boolean.TRUE;
                break;
        }
        if (bool == null) {
            printWriter.println("Error: expected true, 1, false, 0.");
            return false;
        }
        compatUIConfiguration.mIsRestartDialogOverrideEnabled = bool.booleanValue();
        return true;
    }

    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final void printShellCommandHelp(PrintWriter printWriter, String str) {
        AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0.m(printWriter, "    restartDialogEnabled [0|false|1|true]", "      Enable/Disable the restart education dialog for Size Compat Mode", "    reachabilityEducationEnabled [0|false|1|true]", "      Enable/Disable the restart education dialog for letterbox reachability");
        printWriter.println("      Disable the restart education dialog for letterbox reachability");
    }
}
