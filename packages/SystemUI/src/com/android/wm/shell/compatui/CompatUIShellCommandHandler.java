package com.android.wm.shell.compatui;

import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.systemui.biometrics.AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0;
import com.android.wm.shell.sysui.ShellCommandHandler;
import java.io.PrintWriter;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class CompatUIShellCommandHandler implements ShellCommandHandler.ShellCommandActionHandler {
    public final CompatUIConfiguration mCompatUIConfiguration;
    public final ShellCommandHandler mShellCommandHandler;

    public CompatUIShellCommandHandler(ShellCommandHandler shellCommandHandler, CompatUIConfiguration compatUIConfiguration) {
        this.mShellCommandHandler = shellCommandHandler;
        this.mCompatUIConfiguration = compatUIConfiguration;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00a3  */
    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onShellCommand(PrintWriter printWriter, String[] strArr) {
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
            switch (str2) {
                case "0":
                case "false":
                    bool = Boolean.FALSE;
                    break;
                case "1":
                case "true":
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
        switch (str3) {
            case "0":
            case "false":
                bool = Boolean.FALSE;
                break;
            case "1":
            case "true":
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
